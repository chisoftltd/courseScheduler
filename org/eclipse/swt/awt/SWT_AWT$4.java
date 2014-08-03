package org.eclipse.swt.awt;

import java.awt.Frame;
import java.awt.event.WindowEvent;

class SWT_AWT$4
  implements Runnable
{
  final SWT_AWT.2 this$1;
  private final Frame val$frame;

  SWT_AWT$4(SWT_AWT.2 param2, Frame paramFrame)
  {
    this.this$1 = param2;
    this.val$frame = paramFrame;
  }

  public void run()
  {
    this.val$frame.dispatchEvent(new WindowEvent(this.val$frame, 203));
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.awt.SWT_AWT.4
 * JD-Core Version:    0.6.2
 */