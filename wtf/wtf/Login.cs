using System;
using System.Windows.Forms;
using Client.API;
using RestSharp;

namespace Client
{
    public partial class Login : Form
    {

        public Login()
        {
            InitializeComponent();
            this.Icon = FreshFitness.Properties.Resources.icon;
        }

        private void Login_Shown(object sender, EventArgs e)
        {
            var request = new RestRequest(Endpoint.STATUS.ToString(), Method.GET);
            Program.getApiClient().getClient().ExecuteAsync<FreshFitness.API.models.result>(request, response =>
            {
                if (response.IsSuccessful)
                {
                    transparentLabel1.Text = $"API Status: Online ({response.Data.message})";
                    enableLogin(true);

                    if (!response.Data.status)
                    {
                        MessageBox.Show("Our systems are currently under maintenance.\nApplication will close now.\n\nTry again later!", "Maintenance", MessageBoxButtons.OK, MessageBoxIcon.Information);
                        Program.closeApp();
                    }
                }
                else
                {
                    transparentLabel1.Text = "API Status: Offline";

                    MessageBox.Show("Couldn't get in touch with our servers.\nApplication will close now.\n\nTry again later!", "Picky-wicky *boom*", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                    Program.closeApp();
                }
            });
            
        }

        private void enableLogin(bool enable)
        {
            if (textBox1.InvokeRequired) textBox1.Invoke(new Action(() => textBox1.Enabled = enable));
            else textBox1.Enabled = enable;

            if (textBox2.InvokeRequired) textBox2.Invoke(new Action(() => textBox2.Enabled = enable));
            else textBox2.Enabled = enable;

            if (button1.InvokeRequired) textBox2.Invoke(new Action(() => button1.Enabled = enable));
            else button1.Enabled = enable;
        }

        private void button1_Click(object sender, EventArgs e)
        {
            var prev = button1.Text;
            if (textBox1.Text.Length == 0 || textBox2.Text.Length == 0)
            {
                MessageBox.Show("Your login fields cannot be empty :V", "Info", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                return;
            }

            button1.Enabled = false;
            button1.Text = "Logging in...";

            // Log in
            var request = new RestRequest(Endpoint.LOGIN.ToString(), Method.POST);
            request.AddQueryParameter("email", textBox1.Text);
            request.AddQueryParameter("password", textBox2.Text);
            Program.getApiClient().getClient().ExecuteAsync<FreshFitness.API.models.result>(request, response =>
            {
                if (response.IsSuccessful && response.Data.status)
                {
                    enableLogin(false);
                    if (button2.InvokeRequired)
                    {
                        button2.BeginInvoke((MethodInvoker)delegate ()
                        {
                            button2.Enabled = true;
                            button2.Visible = true;
                        });
                    } else
                    {
                        button2.Enabled = true;
                        button2.Visible = true;
                    }
                }
                else
                {
                    MessageBox.Show($"Not logged in!\n{response.Data.message}");
                }
            });

            button1.Text = prev;
            button1.Enabled = true;
        }

        private void Login_FormClosing(object sender, FormClosingEventArgs e)
        {
            if (!Program.isInternalClose())
            {
                var req = new RestRequest(Endpoint.LOGOUT.ToString(), Method.POST);
                Program.getApiClient().getClient().Post(req);
                Program.closeApp();
            }
        }

        private void button2_Click(object sender, EventArgs e)
        {
            this.Invoke((MethodInvoker)delegate { Program.switchWindow(Program.Forms.UserLoggedIn); });
        }

        private void textBox2_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (e.KeyChar == 13)
            {
                button1.PerformClick();
            }
        }
    }
}
