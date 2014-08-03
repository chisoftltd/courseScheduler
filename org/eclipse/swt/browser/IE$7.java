package org.eclipse.swt.browser;

class IE$7
  implements Runnable
{
  final IE.6 this$1;

  IE$7(IE.6 param6)
  {
    this.this$1 = param6;
  }

  public void run()
  {
    if ((IE.6.access$0(this.this$1).browser.isDisposed()) || (IE.6.access$0(this.this$1).html == null))
      return;
    IE.6.access$0(this.this$1).setHTML(IE.6.access$0(this.this$1).html);
    IE.6.access$0(this.this$1).html = null;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.browser.IE.7
 * JD-Core Version:    0.6.2
 */