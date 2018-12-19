using System;
using System.Reflection;
using System.Windows.Forms;

namespace FreshFitness.HereGoesWeirdStuff
{
    class AutoInvoke
    {
        public static void Text(Control c, String text)
        {
            if (c.InvokeRequired)
            {
                c.BeginInvoke((MethodInvoker)delegate ()
                {
                    c.Text = text;
                });
                return;
            }

            c.Text = text;
        }

        public static object getProperty(Control c, string propertyName)
        {
            PropertyInfo pInfo = c.GetType().GetProperty(propertyName);
            if (c.InvokeRequired)
            {
                return c.Invoke(new Func<object>(() => getProperty(c, propertyName)));
            }
            return pInfo.GetValue(c);
        }

        public static void setProperty(Control c, string propertyName, dynamic value)
        {
            PropertyInfo pInfo = c.GetType().GetProperty(propertyName);
            if (c.InvokeRequired)
            {
                c.BeginInvoke((MethodInvoker)delegate ()
                {
                    pInfo.SetValue(c, value);
                });
                return;
            }
            pInfo.SetValue(c, value);
        }

        public static void List(bool add, Control c, dynamic list, dynamic value)
        {
            if (c.InvokeRequired)
            {
                c.BeginInvoke((MethodInvoker)delegate ()
                {
                    if (add)
                    {
                        list.Add(value);
                    }
                    else
                    {
                        list.Remove(value);
                    }
                });
                return;
            }

            if (add)
            {
                list.Add(value);
            }
            else
            {
                list.Remove(value);
            }
        }

    }
}
