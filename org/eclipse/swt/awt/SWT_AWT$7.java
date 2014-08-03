package org.eclipse.swt.awt;

import java.awt.Frame;
import java.awt.event.FocusEvent;
import java.awt.event.WindowEvent;
import java.lang.reflect.Method;
import org.eclipse.swt.internal.Library;

class SWT_AWT$7
  implements Runnable
{
  final SWT_AWT.5 this$1;
  private final Frame val$frame;

  SWT_AWT$7(SWT_AWT.5 param5, Frame paramFrame)
  {
    this.this$1 = param5;
    this.val$frame = paramFrame;
  }

  public void run()
  {
    if (Library.JAVA_VERSION < Library.JAVA_VERSION(1, 4, 0))
    {
      this.val$frame.dispatchEvent(new WindowEvent(this.val$frame, 205));
      this.val$frame.dispatchEvent(new FocusEvent(this.val$frame, 1004));
    }
    else if (Library.JAVA_VERSION < Library.JAVA_VERSION(1, 5, 0))
    {
      this.val$frame.dispatchEvent(new WindowEvent(this.val$frame, 205));
      this.val$frame.dispatchEvent(new WindowEvent(this.val$frame, 207));
    }
    else
    {
      if (this.val$frame.isActive())
        return;
      try
      {
        Class localClass = this.val$frame.getClass();
        Method localMethod = localClass.getMethod("synthesizeWindowActivation", new Class[] { Boolean.TYPE });
        if (localMethod != null)
          localMethod.invoke(this.val$frame, new Object[] { new Boolean(true) });
      }
      catch (Throwable localThrowable)
      {
      }
    }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.awt.SWT_AWT.7
 * JD-Core Version:    0.6.2
 */