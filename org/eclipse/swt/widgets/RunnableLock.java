package org.eclipse.swt.widgets;

class RunnableLock
{
  Runnable runnable;
  Thread thread;
  Throwable throwable;

  RunnableLock(Runnable paramRunnable)
  {
    this.runnable = paramRunnable;
  }

  boolean done()
  {
    return (this.runnable == null) || (this.throwable != null);
  }

  void run()
  {
    if (this.runnable != null)
      this.runnable.run();
    this.runnable = null;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.RunnableLock
 * JD-Core Version:    0.6.2
 */