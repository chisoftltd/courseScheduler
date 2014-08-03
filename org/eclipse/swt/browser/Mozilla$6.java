package org.eclipse.swt.browser;

class Mozilla$6
  implements Runnable
{
  final Mozilla.5 this$1;

  Mozilla$6(Mozilla.5 param5)
  {
    this.this$1 = param5;
  }

  public void run()
  {
    if (Mozilla.5.access$0(this.this$1).browser.isDisposed())
      return;
    Mozilla.5.access$0(this.this$1).onResize();
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.browser.Mozilla.6
 * JD-Core Version:    0.6.2
 */