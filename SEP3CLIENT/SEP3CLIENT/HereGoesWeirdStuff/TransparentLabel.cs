using System;
using System.Windows.Forms;

namespace Client.HereGoesWeirdStuff
{
    class TransparentLabel : Label
    {
        public TransparentLabel()
        {
            SetStyle(ControlStyles.AllPaintingInWmPaint | ControlStyles.SupportsTransparentBackColor, true);
            base.BackColor = System.Drawing.Color.FromArgb(0, 0, 0, 0);
        }

        public override string Text {
            get => base.Text;
            set {
                if (base.InvokeRequired)
                {
                    base.Invoke(new Action(() => base.Text = value));
                    return;
                }
                base.Text = value;
            }
        }
    }
}
