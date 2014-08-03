package org.eclipse.swt.awt;

import java.awt.Canvas;
import java.awt.Dimension;
import org.eclipse.swt.widgets.Shell;

class SWT_AWT$12
  implements Runnable
{
  final SWT_AWT.11 this$1;
  private final Shell val$shell;
  private final Canvas val$parent;

  SWT_AWT$12(SWT_AWT.11 param11, Shell paramShell, Canvas paramCanvas)
  {
    this.this$1 = param11;
    this.val$shell = paramShell;
    this.val$parent = paramCanvas;
  }

  public void run()
  {
    if (this.val$shell.isDisposed())
      return;
    Dimension localDimension = this.val$parent.getSize();
    this.val$shell.setSize(localDimension.width, localDimension.height);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.awt.SWT_AWT.12
 * JD-Core Version:    0.6.2
 */