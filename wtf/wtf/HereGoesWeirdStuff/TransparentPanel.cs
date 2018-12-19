using System.Windows.Forms;

namespace Client.HereGoesWeirdStuff
{
    class TransparentPanel : Panel
    {
        public TransparentPanel()
        {
            this.SetStyle(ControlStyles.AllPaintingInWmPaint, true);
            this.SetStyle(ControlStyles.UserPaint, true);
            this.SetStyle(ControlStyles.OptimizedDoubleBuffer, true);
            this.DoubleBuffered = true;
        }

        protected override void OnScroll(ScrollEventArgs e)
        {
            this.Invalidate();
            base.OnScroll(e);
        }

        protected override CreateParams CreateParams
        {
            get
            {
                CreateParams cp = base.CreateParams;
                cp.ExStyle |= 0x02000000; // WS_CLIPCHILDREN
                return cp;
            }
        }

    }
}
