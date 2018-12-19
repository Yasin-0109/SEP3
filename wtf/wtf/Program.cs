using System;
using System.Diagnostics;
using System.Windows.Forms;
using FreshFitness.API;

namespace Client
{
    static class Program
    {
        private static bool internalClose = false;
        private static Form currentForm;
        private static ApiClient apiClient;

        public enum Forms
        {
            Login,
            UserLoggedIn
        }

        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            Application.ThreadException += Application_ThreadException;
            AppDomain.CurrentDomain.UnhandledException += CurrentDomain_UnhandledException;

            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);

            LogAPI.init();
            apiClient = new ApiClient();

            currentForm = new Login();
            currentForm.Show();

            Application.Run();
        }

        private static void CurrentDomain_UnhandledException(object sender, UnhandledExceptionEventArgs e)
        {
            LogAPI.AddLog(LogAPI.LogLevel.ERROR, "----------------------------------------");
            LogAPI.AddLog(LogAPI.LogLevel.ERROR, "Unhandled Thread Exception");
            LogAPI.AddLog(LogAPI.LogLevel.ERROR, e.ExceptionObject.ToString());
            LogAPI.AddLog(LogAPI.LogLevel.ERROR, e.ToString());
            LogAPI.AddLog(LogAPI.LogLevel.ERROR, "----------------------------------------");

            MessageBox.Show($"I've got a Thread exception!\n\nApplication will close now!", "FreshFitness Client - Thread Exception", MessageBoxButtons.OK, MessageBoxIcon.Error);
            if (Application.OpenForms.Count == 0)
            {
                Environment.Exit(1);
            }
            else
            {
                Application.Exit();
            }
        }

        private static void Application_ThreadException(object sender, System.Threading.ThreadExceptionEventArgs e)
        {
            LogAPI.AddLog(LogAPI.LogLevel.ERROR, "----------------------------------------");
            LogAPI.AddLog(LogAPI.LogLevel.ERROR, "Unhandled UI Exception");
            LogAPI.AddLog(LogAPI.LogLevel.ERROR, e.Exception.Source);
            LogAPI.AddLog(LogAPI.LogLevel.ERROR, e.Exception.Message);
            LogAPI.AddLog(LogAPI.LogLevel.ERROR, e.Exception.ToString());

            try
            {
                var st = new StackTrace(e.Exception, true);
                var frame = st.GetFrame(0);

                LogAPI.AddLog(LogAPI.LogLevel.ERROR, $"Location: {frame.GetFileName()}:{frame.GetFileLineNumber()}");
            }
            catch { }

            LogAPI.AddLog(LogAPI.LogLevel.ERROR, "----------------------------------------");

            MessageBox.Show($"I've got a UI Thread exception!\nError: {e.Exception.Message}\n\nApplication will close now!", "FreshFitness Client - UI Thread Exception", MessageBoxButtons.OK, MessageBoxIcon.Error);
            if (Application.OpenForms.Count == 0)
            {
                Environment.Exit(1);
            } else {
                Application.Exit();
            }
        }

        public static void switchWindow(Forms form)
        {
            internalClose = true;
            if (currentForm.InvokeRequired)
            {
                currentForm.BeginInvoke((MethodInvoker)delegate ()
                {
                    currentForm.Hide();
                    currentForm.Dispose();
                    currentForm.Close();
                });
            } else {
                currentForm.Hide();
                currentForm.Dispose();
                currentForm.Close();
            }

            switch(form)
            {
                case Forms.Login:
                    {
                        currentForm = new Login();
                        break;
                    }
                case Forms.UserLoggedIn:
                    {
                        currentForm = new UserLoggedIn();
                        break;
                    }
            }

            internalClose = false;

            currentForm.Show();
        }

        public static void closeApp()
        {
            Application.Exit();
            Environment.Exit(0);
        }

        public static bool isInternalClose()
        {
            return internalClose;
        }

        public static ApiClient getApiClient()
        {
            return apiClient;
        }

        public static void setApiClient(ApiClient client)
        {
            apiClient = client;
        }
    }
}
