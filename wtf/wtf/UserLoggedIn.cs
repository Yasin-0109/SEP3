using System;
using System.Collections.Generic;
using System.Globalization;
using System.Threading.Tasks;
using System.Windows.Forms;
using Client.API;
using Newtonsoft.Json;
using RestSharp;
using FreshFitness.API;
using FreshFitness.API.models;
using FreshFitness.HereGoesWeirdStuff;
using FreshFitness.Properties;

namespace Client
{
    public partial class UserLoggedIn : Form
    {
        private UserLoggedIn instance;
        private bool closing = false;

        private User currentUser;

        private List<Activity> allActivities;
        private List<Activity> userActivities;

        private List<Workout> userWorkouts;
        private List<User> users;
        private int currentEditWorkout = -1;
        private int currentEditUser = -1;
        private int currentEditActivity = -1;


        public UserLoggedIn()
        {
            InitializeComponent();
            this.Icon = FreshFitness.Properties.Resources.icon;

            instance = this;

            // Default values
            dateTimePicker2.Value = DateTime.Now;
            dateTimePicker3.Value = DateTime.Now;

            // Both date and time
            dateTimePicker5.Format = DateTimePickerFormat.Custom;
            dateTimePicker5.CustomFormat = "MM/dd/yyyy hh:mm:ss";
            dateTimePicker5.Value = DateTime.Now;
            dateTimePicker6.Value = DateTime.Now;
            dateTimePicker6.Format = DateTimePickerFormat.Custom;
            dateTimePicker6.CustomFormat = "MM/dd/yyyy hh:mm:ss";

            dataGridView1.AutoGenerateColumns = false;
            dataGridView2.AutoGenerateColumns = false;
            dataGridView3.AutoGenerateColumns = false;
            dataGridView4.AutoGenerateColumns = false;

            label6.Text = $"Workout Log - {dateTimePicker2.Value:MMMM yyyy}";

            monthCalendar1.SetDate(DateTime.Now);

            // Manage users
            tabControl1.TabPages.Remove(tabPage4);

            // Manage activities
            tabControl1.TabPages.Remove(tabPage5);
        }

        private void UserLoggedIn_Shown(object sender, EventArgs e)
        {
            var request = new RestRequest(Endpoint.STATUS.ToString(), Method.GET);
            Program.getApiClient().getClient().ExecuteAsync<result>(request, response =>
            {
                if (response.IsSuccessful)
                {
                    transparentLabel1.Text = $"API Status: Online ({response.Data.message})";

                    // Checker if API is available
                    Task.Run(async () =>
                    {
                        while (true)
                        {
                            var req = new RestRequest(Endpoint.STATUS.ToString(), Method.GET);
                            var res = Program.getApiClient().getClient().Execute<result>(req);

                            if (res.IsSuccessful)
                            {
                                AutoInvoke.Text(transparentLabel1, $"API Status: Online ({res.Data.message})");

                                if (!res.Data.status)
                                {
                                    MessageBox.Show("Our systems are currently under maintenance.\nApplication will close now.\n\nTry again later!", "Maintenance", MessageBoxButtons.OK, MessageBoxIcon.Information);
                                    Program.closeApp();
                                    break;
                                }

                                Status status = JsonConvert.DeserializeObject<Status>(res.Data.data);
                                if (!status.loggedIn && !closing)
                                {
                                    MessageBox.Show("You've been logged out!", "Logged out", MessageBoxButtons.OK, MessageBoxIcon.Information);
                                    this.Invoke((MethodInvoker)delegate { Program.switchWindow(Program.Forms.Login); });
                                    break;
                                }
                            }
                            else
                            {
                                MessageBox.Show("Couldn't get in touch with our servers.\nApplication will close now.\n\nTry again later!", "Picky-wicky *boom*", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                                Program.closeApp();
                                break;
                            }

                            await Task.Delay(10000);
                        }
                    });

                    // Let's start getting data in other thread
                    Task.Run(() => loadData());
                }
                else
                {
                    transparentLabel1.Text = "API Status: Offline";
                    MessageBox.Show("API not available. Try again later!");
                    Program.closeApp();
                }
            });
        }

        private void loadData()
        {
            // Global data!
            Global.refreshUserActivities();

            // Other things
            var request = new RestRequest(Endpoint.UserInfo.ToString(), Method.GET);
            Program.getApiClient().getClient().ExecuteAsync<result>(request, response =>
            {
                if (response.IsSuccessful && response.Data.status)
                {
                    currentUser = JsonConvert.DeserializeObject<User>(response.Data.data);

                    var Sreq = new RestRequest(Endpoint.UserSubscriptionInfo.ToString(), Method.GET);
                    var Sres = Program.getApiClient().getClient().Execute<result>(Sreq);
                    if (Sres.IsSuccessful && Sres.Data.status)
                    {
                        Subscription sub = JsonConvert.DeserializeObject<Subscription>(Sres.Data.data);
                        switch (sub.subscriptionType.id)
                        {
                            case 1: // Regular
                                {
                                    AutoInvoke.setProperty(pictureBox2, "Image", Resources.Badge_1);
                                    break;
                                }
                            case 2: // Premium
                                {
                                    AutoInvoke.setProperty(pictureBox2, "Image", Resources.Badge_2);
                                    break;
                                }
                        }
                    }

                    if (currentUser.userRole.id == Helpers.getInstructorRoleId())
                    {
                        AutoInvoke.List(true, tabControl1, tabControl1.TabPages, tabPage5);
                    }

                    if (currentUser.userRole.id == Helpers.getAdminRoleId())
                    {
                        AutoInvoke.List(true, tabControl1, tabControl1.TabPages, tabPage4);
                        AutoInvoke.List(true, tabControl1, tabControl1.TabPages, tabPage5);
                    }

                    // Profile Tab
                    AutoInvoke.Text(label30, currentUser.firstName + " " + currentUser.lastName);
                    AutoInvoke.Text(label31, currentUser.email);
                    AutoInvoke.Text(label32, currentUser.phoneNumber.ToString());

                    DateTime date = DateTime.Parse(currentUser.dateOfBirth);
                    AutoInvoke.Text(label33, date.ToLongDateString());

                    // Workouts Tab
                    var wTreq = new RestRequest(Endpoint.UserWorkoutsTypes.ToString(), Method.GET);
                    Program.getApiClient().getClient().ExecuteAsync<result>(wTreq, wTresponse =>
                    {
                        if (wTresponse.IsSuccessful && wTresponse.Data.status)
                        {
                            List<WorkoutType> wT = JsonConvert.DeserializeObject<List<WorkoutType>>(wTresponse.Data.data);
                            workouts_wType.DisplayMember = "type";
                            workouts_wType.ValueMember = "id";
                            workouts_wType.DataSource = wT;
                        }
                        else
                        {
                            // TODO: Error while getting workouts types
                        }
                    });

                    refreshUserWorkouts();

                    // Activities Tab
                    refreshUserActivities();

                    // Manage users
                    if (currentUser.userRole.id == Helpers.getAdminRoleId())
                    {
                        Global.refreshUserRoles();
                        Global.refreshSubscriptionTypes();
                        Global.refreshSubscriptions();

                        List<SubscriptionType> subscriptionTypes = new List<SubscriptionType>();
                        SubscriptionType def = new SubscriptionType();
                        def.id = -1;
                        def.type = "All";

                        subscriptionTypes.Add(def); // Add `All` option
                        subscriptionTypes.AddRange(Global.getSubscriptionTypes()); // Add rest subscription types

                        comboBox2.DisplayMember = "type";
                        comboBox2.ValueMember = "id";
                        comboBox2.DataSource = Global.getSubscriptionTypes();

                        comboBox3.DisplayMember = "type";
                        comboBox3.ValueMember = "id";
                        comboBox3.DataSource = subscriptionTypes;

                        comboBox4.DisplayMember = "name";
                        comboBox4.ValueMember = "id";
                        comboBox4.DataSource = Global.getUserRoles();

                        refreshUsers();
                    }

                    // Manage activities
                    if (currentUser.userRole.id == Helpers.getInstructorRoleId() ||
                        currentUser.userRole.id == Helpers.getAdminRoleId())
                    {
                        comboBox6.DisplayMember = "getName";
                        comboBox6.ValueMember = "id";

                        if (currentUser.userRole.id == Helpers.getInstructorRoleId())
                        {
                            List<User> user = new List<User>();
                            user.Add(currentUser);
                            comboBox6.DataSource = user;
                        }
                        else
                        {
                            comboBox6.DataSource = users.FindAll(m => m.userRole.id == Helpers.getInstructorRoleId());
                        }

                        refreshAllActivities();
                    }
                }
                else
                {
                    // TODO: Error while getting user info
                }
            });
        }

        private void refreshUserWorkouts()
        {
            var wLreq = new RestRequest(Endpoint.UserWorkouts.ToString(), Method.GET);
            Program.getApiClient().getClient().ExecuteAsync<result>(wLreq, wLresponse =>
            {
                if (wLresponse.IsSuccessful && wLresponse.Data.status)
                {
                    userWorkouts = JsonConvert.DeserializeObject<List<Workout>>(wLresponse.Data.data);

                    List<Workout> currentWorkouts = new List<Workout>();
                    foreach (Workout workout in userWorkouts)
                    {
                        if (CultureInfo.CurrentCulture.Calendar.GetWeekOfYear(workout.dateTime, CalendarWeekRule.FirstFourDayWeek, DayOfWeek.Monday) ==
                        CultureInfo.CurrentCulture.Calendar.GetWeekOfYear(dateTimePicker2.Value, CalendarWeekRule.FirstFourDayWeek, DayOfWeek.Monday))
                        {
                            currentWorkouts.Add(workout);
                        }
                    }

                    if (this.InvokeRequired)
                    {
                        this.BeginInvoke((MethodInvoker)delegate ()
                        {
                            dataGridView1.DataSource = currentWorkouts;
                        });
                        return;
                    }

                    dataGridView1.DataSource = currentWorkouts;
                }
                else
                {
                    // TODO: Error while getting workouts types
                }
            });
        }

        private void refreshUserActivities()
        {
            var Areq = new RestRequest(Endpoint.UserActivities.ToString(), Method.GET);
            Program.getApiClient().getClient().ExecuteAsync<result>(Areq, Aresponse =>
            {
                if (Aresponse.IsSuccessful && Aresponse.Data.status)
                {
                    userActivities = JsonConvert.DeserializeObject<List<Activity>>(Aresponse.Data.data);
                    refreshVisibleActivities();
                }
                else
                {
                    // TODO: Error while getting user activities
                }
            });
        }

        private void refreshVisibleActivities()
        {
            List<Activity> currentActivities = new List<Activity>();
            foreach (Activity activity in userActivities)
            {
                if (activity.sTDateTime.Year == monthCalendar1.SelectionRange.Start.Date.Year &&
                    activity.sTDateTime.Month == monthCalendar1.SelectionRange.Start.Date.Month &&
                    activity.sTDateTime.Day == monthCalendar1.SelectionRange.Start.Date.Day)
                {
                    currentActivities.Add(activity);
                }
            }

            if (this.InvokeRequired)
            {
                this.BeginInvoke((MethodInvoker)delegate ()
                {
                    dataGridView1.DataSource = currentActivities;
                });
                return;
            }

            dataGridView2.DataSource = currentActivities;
        }

        private void refreshUsers()
        {
            var aUIreq = new RestRequest(Endpoint.AdminUsersList.ToString(), Method.GET);
            var response = Program.getApiClient().getClient().Execute<result>(aUIreq);
            if (response.IsSuccessful && response.Data.status)
            {
                users = JsonConvert.DeserializeObject<List<User>>(response.Data.data);
                AutoInvoke.setProperty(dataGridView3, "DataSource", users);
            }
        }

        private void refreshAllActivities()
        {
            RestRequest req;
            if (currentUser.userRole.id == Helpers.getInstructorRoleId())
            {
                req = new RestRequest(Endpoint.InstructorActivitiesList.ToString(), Method.GET);
            }
            else
            {
                req = new RestRequest(Endpoint.AdminActivitiesList.ToString(), Method.GET);
            }

            Program.getApiClient().getClient().ExecuteAsync<result>(req, response =>
            {
                if (response.IsSuccessful && response.Data.status)
                {
                    allActivities = JsonConvert.DeserializeObject<List<Activity>>(response.Data.data);
                    refreshVisibleAllActivities();
                }
                else
                {
                    // TODO: Error while getting all activities
                }
            });
        }

        private void refreshVisibleAllActivities()
        {
            if (textBox12.Text.Length > 0)
            {
                AutoInvoke.setProperty(dataGridView4, "DataSource", allActivities.FindAll(m =>
                m.name.ToLower().Contains(textBox12.Text.ToLower())));
            }
            else
            {
                AutoInvoke.setProperty(dataGridView4, "DataSource", allActivities);
            }
        }


        private void UserLoggedIn_FormClosing(object sender, FormClosingEventArgs e)
        {
            if (!Program.isInternalClose())
            {
                var req = new RestRequest(Endpoint.LOGOUT.ToString(), Method.POST);
                Program.getApiClient().getClient().Post(req);
                Program.closeApp();
            }
        }

        private void dateTimePicker2_ValueChanged(object sender, EventArgs e)
        {
            if (userWorkouts != null && currentEditWorkout == -1)
            {
                label6.Text = $"Workout Log - {dateTimePicker2.Value:MMMM yyyy}";

                List<Workout> currentWorkouts = new List<Workout>();
                foreach (Workout workout in userWorkouts)
                {
                    if (CultureInfo.CurrentCulture.Calendar.GetWeekOfYear(workout.dateTime, CalendarWeekRule.FirstFourDayWeek, DayOfWeek.Monday) ==
                    CultureInfo.CurrentCulture.Calendar.GetWeekOfYear(dateTimePicker2.Value, CalendarWeekRule.FirstFourDayWeek, DayOfWeek.Monday))
                    {
                        currentWorkouts.Add(workout);
                    }
                }

                dataGridView1.DataSource = currentWorkouts;
            }
        }

        private void dataGridView1_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            if (dataGridView1.CurrentCell.OwningRow.DataBoundItem != null)
            {
                label22.Visible = true;
                Workout w = (Workout)dataGridView1.CurrentCell.OwningRow.DataBoundItem;

                // Set data
                currentEditWorkout = w.id;
                workouts_wType.SelectedValue = w.type.id;
                numericUpDown1.Value = w.numberOfWorkouts;
                dateTimePicker2.Value = w.dateTime;
            }
        }

        private void button17_Click(object sender, EventArgs e)
        {
            button17.Enabled = false;
            button17.Text = "Saving...";
            if (label22.Visible) // Editing
            {
                if (currentEditWorkout != -1) // Save changes to that
                {
                    Workout w = userWorkouts.Find(m => m.id == currentEditWorkout);

                    var req = new RestRequest(string.Format(Endpoint.UserWorkoutEdit.ToString(), currentEditWorkout), Method.PUT);

                    if (w.type.id != (int)workouts_wType.SelectedValue)
                    {
                        req.AddQueryParameter("workoutTypeId", workouts_wType.SelectedValue.ToString());
                    }

                    if (w.numberOfWorkouts != numericUpDown1.Value)
                    {
                        req.AddQueryParameter("numberOfWorkouts", numericUpDown1.Value.ToString());
                    }

                    if (!w.dateTime.Equals(dateTimePicker2.Value))
                    {
                        req.AddQueryParameter("date", dateTimePicker2.Value.ToString("yyyy'-'MM'-'dd' 'hh':'mm':'ss'.'fff"));
                    }

                    Program.getApiClient().getClient().ExecuteAsync<result>(req, response =>
                    {
                        if (!response.IsSuccessful || !response.Data.status)
                        {
                            // TODO: Error while editing workout
                        }

                        MessageBox.Show(response.Data.message);

                        if (this.InvokeRequired)
                        {
                            this.BeginInvoke((MethodInvoker)delegate ()
                            {
                                button17.Text = "Save";
                                button17.Enabled = true;
                                workouts_wType.SelectedIndex = -1;
                                numericUpDown1.Value = 0;

                                currentEditWorkout = -1;
                                refreshUserWorkouts();

                                label22.Visible = false;
                                dataGridView1.ClearSelection();
                            });
                            return;
                        }

                        button17.Text = "Save";
                        button17.Enabled = true;
                        workouts_wType.SelectedIndex = -1;
                        numericUpDown1.Value = 0;

                        currentEditWorkout = -1;
                        refreshUserWorkouts();

                        label22.Visible = false;
                        dataGridView1.ClearSelection();
                    });
                }
            }
            else // Adding
            {
                if (workouts_wType.SelectedValue == null)
                {
                    MessageBox.Show("Please pick a workout type first!", "StaleFitness :V", MessageBoxButtons.OK, MessageBoxIcon.Information);
                    if (this.InvokeRequired)
                    {
                        this.BeginInvoke((MethodInvoker)delegate ()
                        {
                            button17.Text = "Save";
                            button17.Enabled = true;
                        });
                    }
                    else
                    {
                        button17.Text = "Save";
                        button17.Enabled = true;
                    }
                    return;
                }

                var req = new RestRequest(Endpoint.UserWorkoutAdd.ToString(), Method.POST);
                req.AddQueryParameter("workoutTypeId", workouts_wType.SelectedValue.ToString());
                req.AddQueryParameter("numberOfWorkouts", numericUpDown1.Value.ToString());
                req.AddQueryParameter("date", dateTimePicker2.Value.ToString("yyyy'-'MM'-'dd' 'hh':'mm':'ss'.'fff"));
                Program.getApiClient().getClient().ExecuteAsync<result>(req, response =>
                {
                    if (response.IsSuccessful && response.Data.status)
                    {
                        if (this.InvokeRequired)
                        {
                            this.BeginInvoke((MethodInvoker)delegate ()
                            {
                                workouts_wType.SelectedIndex = -1;
                                numericUpDown1.Value = 0;
                            });
                        }
                        else
                        {
                            workouts_wType.SelectedIndex = -1;
                            numericUpDown1.Value = 0;
                        }
                    }
                    else
                    {
                        // TODO: Error while adding workout
                    }

                    if (this.InvokeRequired)
                    {
                        this.BeginInvoke((MethodInvoker)delegate ()
                        {
                            button17.Text = "Save";
                            button17.Enabled = true;
                        });
                    }
                    else
                    {
                        button17.Text = "Save";
                        button17.Enabled = true;
                    }

                    MessageBox.Show(response.Data.message);
                    refreshUserWorkouts();
                });

            }
        }

        private void dataGridView1_DataBindingComplete(object sender, DataGridViewBindingCompleteEventArgs e)
        {
            dataGridView1.ClearSelection();
            button4.Enabled = false;
            button5.Enabled = false;
        }

        private void monthCalendar1_DateChanged(object sender, DateRangeEventArgs e)
        {
            if (userActivities != null)
            {
                refreshVisibleActivities();
            }
        }

        private void dataGridView2_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            if (dataGridView2.CurrentCell.OwningRow.DataBoundItem != null)
            {
                Activity a = (Activity)dataGridView2.CurrentCell.OwningRow.DataBoundItem;
                if (a.isUserIn)
                {
                    button4.Enabled = false;
                    button5.Enabled = true;
                }
                else
                {
                    button4.Enabled = true;
                    button5.Enabled = false;
                }
            }
        }

        private void dataGridView2_DataBindingComplete(object sender, DataGridViewBindingCompleteEventArgs e)
        {
            dataGridView2.ClearSelection();
        }

        private void button5_Click(object sender, EventArgs e)
        {
            if (dataGridView2.CurrentCell != null)
            {
                Activity a = (Activity)dataGridView2.CurrentCell.OwningRow.DataBoundItem;
                Global.delUserActivity(a.id);
                button5.Enabled = false;

                refreshVisibleActivities();
            }
        }

        private void button4_Click(object sender, EventArgs e)
        {
            if (dataGridView2.CurrentCell != null)
            {
                Activity a = (Activity)dataGridView2.CurrentCell.OwningRow.DataBoundItem;
                Global.addUserActivity(a.id);
                button5.Enabled = false;

                refreshVisibleActivities();
            }
        }

        private void comboBox3_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (comboBox3.SelectedValue != null)
            {
                if ((int)comboBox3.SelectedValue == -1) // All option
                {
                    if (textBox9.Text.Length > 0)
                    {
                        AutoInvoke.setProperty(dataGridView3, "DataSource", users.FindAll(m =>
                        m.getName.ToLower().Contains(textBox9.Text.ToLower())));
                    }
                    else
                    {
                        dataGridView3.DataSource = users;
                    }
                }
                else
                {
                    if (textBox9.Text.Length > 0)
                    {
                        dataGridView3.DataSource = users.FindAll(m => 
                        m.getSubscriptionTypeId == (int)comboBox3.SelectedValue &&
                        m.getName.ToLower().Contains(textBox9.Text.ToLower()));
                    }
                    else
                    {
                        dataGridView3.DataSource = users.FindAll(m => m.getSubscriptionTypeId == (int)comboBox3.SelectedValue);
                    }
                }
            }
        }

        private void button9_Click(object sender, EventArgs e)
        {
            if (textBox8.Text.Trim().Length == 0 ||
                textBox7.Text.Trim().Length == 0 ||
                textBox6.Text.Trim().Length == 0 ||
                textBox10.Text.Trim().Length == 0)
            {
                MessageBox.Show("Please fill all fields first!");
                return;
            }

            button9.Enabled = false;
            button9.Text = "Saving...";
            if (label21.Visible) // Editing
            {
                if (currentEditUser != -1) // Save changes to that
                {
                    User u = users.Find(m => m.id == currentEditUser);

                    var req = new RestRequest(string.Format(Endpoint.AdminUsersEdit.ToString(), currentEditUser), Method.PUT);
                    if (u.userRole.id != (int)comboBox4.SelectedValue)
                    {
                        req.AddQueryParameter("userRoleId", comboBox4.SelectedValue.ToString());
                    }

                    if (u.firstName != textBox8.Text)
                    {
                        req.AddQueryParameter("firstName", textBox8.Text);
                    }

                    if (u.lastName != textBox7.Text)
                    {
                        req.AddQueryParameter("lastName", textBox7.Text);
                    }

                    if (!u.dateOfBirthDT.Equals(dateTimePicker3.Value))
                    {
                        req.AddQueryParameter("dateOfBirth", dateTimePicker3.Value.ToString("yyyy'-'MM'-'dd' 'hh':'mm':'ss'.'fff"));
                    }

                    if (u.email != textBox10.Text)
                    {
                        req.AddQueryParameter("email", textBox10.Text);
                    }

                    if (u.password != textBox4.Text && textBox4.Text.Length != 0)
                    {
                        req.AddQueryParameter("password", textBox4.Text);
                    }

                    if (u.phoneNumber.ToString() != textBox6.Text)
                    {
                        req.AddQueryParameter("phoneNumber", textBox6.Text);
                    }

                    Program.getApiClient().getClient().ExecuteAsync<result>(req, response =>
                    {
                        if (!response.IsSuccessful || !response.Data.status)
                        {
                            // TODO: Error while editing user
                        }

                        var nST = AutoInvoke.getProperty(comboBox2, "SelectedValue");
                        if (u.getSubscriptionTypeId != (int)nST)
                        {
                            var req2 = new RestRequest(string.Format(Endpoint.AdminSubscriptionsEdit.ToString(), u.getSubscriptionId), Method.PUT);
                            req2.AddQueryParameter("subscriptionType", nST.ToString());
                            var response2 = Program.getApiClient().getClient().Execute<result>(req2);

                            if (!response2.IsSuccessful || !response2.Data.status)
                            {
                                MessageBox.Show(response.Data.message);
                            }

                            Global.refreshSubscriptions();
                        }
                        
                        MessageBox.Show(response.Data.message);

                        currentEditUser = -1;
                        AutoInvoke.setProperty(label21, "Visible", false);

                        AutoInvoke.Text(textBox8, "");
                        AutoInvoke.Text(textBox7, "");
                        AutoInvoke.Text(textBox6, "");
                        AutoInvoke.Text(textBox10, "");
                        AutoInvoke.Text(textBox4, "");
                        AutoInvoke.setProperty(dateTimePicker3, "Value", DateTime.Now);

                        dataGridView3.ClearSelection();

                        AutoInvoke.Text(button9, "Save");
                        AutoInvoke.setProperty(button9, "Enabled", true);

                        refreshUsers();
                    });
                }
            }
            else // Adding
            {
                var req = new RestRequest(Endpoint.AdminUsersAdd.ToString(), Method.POST);
                // userRoleId, firstName, lastName,
                // dateOfBirth, email, password, phoneNumber
                req.AddQueryParameter("userRoleId", comboBox4.SelectedValue.ToString());
                req.AddQueryParameter("firstName", textBox8.Text);
                req.AddQueryParameter("lastName", textBox7.Text);
                req.AddQueryParameter("dateOfBirth", dateTimePicker3.Value.ToString("yyyy'-'MM'-'dd' 'hh':'mm':'ss'.'fff"));
                req.AddQueryParameter("email", textBox10.Text);
                req.AddQueryParameter("password", textBox4.Text);
                req.AddQueryParameter("phoneNumber", textBox6.Text);

                Program.getApiClient().getClient().ExecuteAsync<result>(req, response =>
                {
                    if (response.IsSuccessful && response.Data.status)
                    {
                        // Adding Subscription
                        var req2 = new RestRequest(Endpoint.AdminUsersGetByEmail.ToString(), Method.POST);
                        req2.AddQueryParameter("email", (string)AutoInvoke.getProperty(textBox10, "Text"));
                        var res2 = Program.getApiClient().getClient().Execute<result>(req2);
                        if (!res2.IsSuccessful || !res2.Data.status)
                        {
                            // TODO: Error while getting added user
                            MessageBox.Show(res2.Data.message);
                        }
                        else
                        {
                            User newU = JsonConvert.DeserializeObject<User>(res2.Data.data);

                            var req3 = new RestRequest(Endpoint.AdminSubscriptionsAdd.ToString(), Method.POST);
                            // userId, validFrom, [validTo], subscriptionType
                            req3.AddQueryParameter("userId", newU.id.ToString());
                            req3.AddQueryParameter("validFrom", DateTime.Now.ToString("yyyy'-'MM'-'dd' 'hh':'mm':'ss'.'fff"));
                            req3.AddQueryParameter("subscriptionType", AutoInvoke.getProperty(comboBox2, "SelectedValue").ToString());

                            var res3 = Program.getApiClient().getClient().Execute<result>(req3);

                            if (!res3.IsSuccessful || !res3.Data.status)
                            {
                                MessageBox.Show(response.Data.message);
                            }

                            Global.refreshSubscriptions();
                        }

                        AutoInvoke.Text(textBox8, "");
                        AutoInvoke.Text(textBox7, "");
                        AutoInvoke.Text(textBox6, "");
                        AutoInvoke.Text(textBox10, "");
                        AutoInvoke.Text(textBox4, "");
                        AutoInvoke.setProperty(dateTimePicker3, "Value", DateTime.Now);

                        refreshUsers();
                    }
                    else
                    {
                        // TODO: Error while adding user
                    }

                    // Done
                    AutoInvoke.Text(button9, "Save");
                    AutoInvoke.setProperty(button9, "Enabled", true);

                    MessageBox.Show(response.Data.message);
                });
            }
        }

        private void dataGridView3_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            if (dataGridView3.CurrentCell.OwningRow.DataBoundItem != null)
            {
                label21.Visible = true;
                button12.Enabled = true;
                comboBox3.Enabled = false;
                User u = (User)dataGridView3.CurrentCell.OwningRow.DataBoundItem;

                // Set data
                currentEditUser = u.id;
                textBox8.Text = u.firstName;
                textBox7.Text = u.lastName;
                textBox6.Text = u.phoneNumber.ToString();
                textBox4.Text = "";
                textBox10.Text = u.email;
                dateTimePicker3.Value = u.dateOfBirthDT;
                if (u.getSubscriptionTypeId != -1)
                {
                    comboBox2.SelectedValue = u.getSubscriptionTypeId;
                }

                comboBox4.SelectedValue = u.userRole.id;
            }
        }

        private void button12_Click(object sender, EventArgs e)
        {
            if (dataGridView3.CurrentCell != null)
            {
                User u = (User)dataGridView3.CurrentCell.OwningRow.DataBoundItem;
                if (u != null)
                {
                    var req = new RestRequest(string.Format(Endpoint.AdminUsersDelete.ToString(), currentEditUser), Method.DELETE);
                    Program.getApiClient().getClient().ExecuteAsync<result>(req, response =>
                    {
                        if (response.IsSuccessful && response.Data.status)
                        {
                            AutoInvoke.Text(textBox8, "");
                            AutoInvoke.Text(textBox7, "");
                            AutoInvoke.Text(textBox6, "");
                            AutoInvoke.Text(textBox10, "");
                            AutoInvoke.Text(textBox4, "");
                            AutoInvoke.setProperty(dateTimePicker3, "Value", DateTime.Now);
                        }
                        else
                        {
                            // TODO: Error while deleting user
                        }

                        // Done
                        MessageBox.Show(response.Data.message);

                        AutoInvoke.setProperty(label21, "Visible", false);

                        refreshUsers();
                    });
                }
            }
        }

        private void dataGridView3_DataBindingComplete(object sender, DataGridViewBindingCompleteEventArgs e)
        {
            dataGridView3.ClearSelection();
            button12.Enabled = false;
            comboBox3.Enabled = true;
        }

        private void comboBox3_EnabledChanged(object sender, EventArgs e)
        {
            if (comboBox3.Enabled && comboBox3.SelectedValue != null && (int)comboBox3.SelectedValue != -1)
            {
                AutoInvoke.setProperty(dataGridView3, "DataSource", users.FindAll(m =>
                m.getSubscriptionTypeId == (int)comboBox3.SelectedValue));
            }
        }

        private void textBox9_TextChanged(object sender, EventArgs e)
        {
            if (comboBox3.SelectedValue != null && (int)comboBox3.SelectedValue != -1)
            {
                AutoInvoke.setProperty(dataGridView3, "DataSource", users.FindAll(m =>
                m.getSubscriptionTypeId == (int)comboBox3.SelectedValue &&
                m.getName.ToLower().Contains(textBox9.Text.ToLower())));
            }
            else
            {
                AutoInvoke.setProperty(dataGridView3, "DataSource", users.FindAll(m =>
                m.getName.ToLower().Contains(textBox9.Text.ToLower())));
            }
        }

        private void dataGridView4_DataBindingComplete(object sender, DataGridViewBindingCompleteEventArgs e)
        {
            dataGridView4.ClearSelection();
            button1.Enabled = false;
        }

        private void dataGridView4_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            if (dataGridView4.CurrentCell.OwningRow.DataBoundItem != null)
            {
                label28.Visible = true;
                button1.Enabled = true;

                Activity a = (Activity)dataGridView4.CurrentCell.OwningRow.DataBoundItem;

                // Set data
                currentEditActivity = a.id;
                textBox17.Text = a.name;
                dateTimePicker5.Value = a.sTDateTime;
                dateTimePicker6.Value = a.eTDateTime;
                
                if (currentUser.userRole.id == Helpers.getAdminRoleId())
                {
                    comboBox6.SelectedValue = a.instructorId;
                }
            }
        }

        private void textBox12_TextChanged(object sender, EventArgs e)
        {
            AutoInvoke.setProperty(dataGridView4, "DataSource", allActivities.FindAll(m =>
                m.name.ToLower().Contains(textBox12.Text.ToLower())));
        }

        private void button1_Click(object sender, EventArgs e)
        {
            if (dataGridView4.CurrentCell != null)
            {
                Activity a = (Activity)dataGridView4.CurrentCell.OwningRow.DataBoundItem;
                if (a != null)
                {
                    RestRequest req;
                    if (currentUser.userRole.id == Helpers.getInstructorRoleId())
                    {
                        req = new RestRequest(string.Format(Endpoint.InstructorActivitiesDelete.ToString(), currentEditActivity), Method.DELETE);
                    }
                    else
                    {
                        req = new RestRequest(string.Format(Endpoint.AdminActivitiesDelete.ToString(), currentEditActivity), Method.DELETE);
                    }

                    Program.getApiClient().getClient().ExecuteAsync<result>(req, response =>
                    {
                        if (response.IsSuccessful && response.Data.status)
                        {
                            AutoInvoke.Text(textBox17, "");
                            AutoInvoke.setProperty(dateTimePicker5, "Value", DateTime.Now);
                            AutoInvoke.setProperty(dateTimePicker6, "Value", DateTime.Now);
                        }
                        else
                        {
                            // TODO: Error while deleting activity
                        }

                        // Done
                        MessageBox.Show(response.Data.message);

                        AutoInvoke.setProperty(label28, "Visible", false);

                        refreshUserActivities();
                        refreshAllActivities();
                    });

                }
            }
        }

        private void button13_Click(object sender, EventArgs e)
        {
            if (textBox17.Text.Trim().Length == 0)
            {
                MessageBox.Show("Don't forget to set name of activity!");
                return;
            }

            button13.Enabled = false;
            button13.Text = "Saving...";
            if (label28.Visible) // Editing
            {
                if (currentEditActivity != -1) // Save changes to that
                {
                    Activity a = allActivities.Find(m => m.id == currentEditActivity);

                    RestRequest req;
                    if (currentUser.userRole.id == Helpers.getInstructorRoleId())
                    {
                        req = new RestRequest(string.Format(Endpoint.InstructorActivitiesEdit.ToString(), currentEditActivity), Method.PUT);
                    }
                    else
                    {
                        req = new RestRequest(string.Format(Endpoint.AdminActivitiesEdit.ToString(), currentEditActivity), Method.PUT);
                    }

                    if (a.name != textBox17.Text)
                    {
                        req.AddQueryParameter("name", textBox17.Text);
                    }

                    if (a.sTDateTime != dateTimePicker5.Value)
                    {
                        req.AddQueryParameter("startTime", dateTimePicker5.Value.ToString("yyyy'-'MM'-'dd' 'hh':'mm':'ss'.'fff"));
                    }

                    if (a.eTDateTime != dateTimePicker6.Value)
                    {
                        req.AddQueryParameter("endTime", dateTimePicker6.Value.ToString("yyyy'-'MM'-'dd' 'hh':'mm':'ss'.'fff"));
                    }

                    if (currentUser.userRole.id == Helpers.getAdminRoleId())
                    {
                        if (a.instructorId != (int)comboBox6.SelectedValue)
                        {
                            req.AddQueryParameter("instructorId", comboBox6.SelectedValue.ToString());
                        }
                    }
                    
                    Program.getApiClient().getClient().ExecuteAsync<result>(req, response =>
                    {
                        if (!response.IsSuccessful || !response.Data.status)
                        {
                            // TODO: Error while editing activity
                        }

                        MessageBox.Show(response.Data.message);
                        
                        currentEditActivity = -1;
                        AutoInvoke.setProperty(label28, "Visible", false);

                        AutoInvoke.Text(textBox17, "");
                        AutoInvoke.setProperty(dateTimePicker5, "Value", DateTime.Now);
                        AutoInvoke.setProperty(dateTimePicker6, "Value", DateTime.Now);

                        dataGridView4.ClearSelection();

                        AutoInvoke.Text(button13, "Save");
                        AutoInvoke.setProperty(button13, "Enabled", true);

                        refreshAllActivities();
                        refreshUserActivities(); // Refresh them too so we'll see them in Activity tab
                    });
                }
            }
            else // Adding
            {
                RestRequest req;
                if (currentUser.userRole.id == Helpers.getInstructorRoleId())
                {
                    req = new RestRequest(Endpoint.InstructorActivitiesAdd.ToString(), Method.POST);
                }
                else
                {
                    req = new RestRequest(Endpoint.AdminActivitiesAdd.ToString(), Method.POST);
                }
                // name, startTime, endTime
                // instructorId <- only if admin

                req.AddQueryParameter("name", textBox17.Text);
                req.AddQueryParameter("startTime", dateTimePicker5.Value.ToString("yyyy'-'MM'-'dd' 'hh':'mm':'ss'.'fff"));
                req.AddQueryParameter("endTime", dateTimePicker6.Value.ToString("yyyy'-'MM'-'dd' 'hh':'mm':'ss'.'fff"));

                if (currentUser.userRole.id == Helpers.getAdminRoleId())
                {
                    req.AddQueryParameter("instructorId", comboBox6.SelectedValue.ToString());
                }

                Program.getApiClient().getClient().ExecuteAsync<result>(req, response =>
                {
                    if (response.IsSuccessful && response.Data.status)
                    {
                        AutoInvoke.Text(textBox17, "");
                        AutoInvoke.setProperty(dateTimePicker5, "Value", DateTime.Now);
                        AutoInvoke.setProperty(dateTimePicker6, "Value", DateTime.Now);

                        refreshAllActivities();
                        refreshUserActivities(); // Refresh them too so we'll see them in Activity tab
                    }
                    else
                    {
                        // TODO: Error while adding activity
                    }

                    // Done
                    AutoInvoke.Text(button13, "Save");
                    AutoInvoke.setProperty(button13, "Enabled", true);

                    MessageBox.Show(response.Data.message);
                });
            }
        }

        private void tabControl1_SelectedIndexChanged(object sender, EventArgs e)
        {
            this.Text = $"Fresh Fitness - {tabControl1.SelectedTab.Text}";
        }
    }

}
