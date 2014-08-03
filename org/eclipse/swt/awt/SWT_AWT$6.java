package org.eclipse.swt.awt;

import java.awt.Frame;

class SWT_AWT$6
  implements Runnable
{
  final SWT_AWT.5 this$1;
  private final Frame val$frame;

  SWT_AWT$6(SWT_AWT.5 param5, Frame paramFrame)
  {
    this.this$1 = param5;
    this.val$frame = paramFrame;
  }

  public void run()
  {
    try
    {
      this.val$frame.dispose();
    }
    catch (Throwable localThrowable)
    {
    }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.awt.SWT_AWT.6
 * JD-Core Version:    0.6.2
 */