package org.eclipse.swt.awt;

import java.awt.Frame;
import org.eclipse.swt.graphics.Rectangle;

class SWT_AWT$10
  implements Runnable
{
  final SWT_AWT.9 this$1;
  private final Frame val$frame;
  private final Rectangle val$clientArea;

  SWT_AWT$10(SWT_AWT.9 param9, Frame paramFrame, Rectangle paramRectangle)
  {
    this.this$1 = param9;
    this.val$frame = paramFrame;
    this.val$clientArea = paramRectangle;
  }

  public void run()
  {
    this.val$frame.setSize(this.val$clientArea.width, this.val$clientArea.height);
    this.val$frame.validate();
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.awt.SWT_AWT.10
 * JD-Core Version:    0.6.2
 */