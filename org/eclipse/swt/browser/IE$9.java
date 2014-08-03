package org.eclipse.swt.browser;

class IE$9
  implements Runnable
{
  final IE.6 this$1;

  IE$9(IE.6 param6)
  {
    this.this$1 = param6;
  }

  public void run()
  {
    if (IE.6.access$0(this.this$1).browser.isDisposed())
      return;
    WindowEvent localWindowEvent = new WindowEvent(IE.6.access$0(this.this$1).browser);
    localWindowEvent.display = IE.6.access$0(this.this$1).browser.getDisplay();
    localWindowEvent.widget = IE.6.access$0(this.this$1).browser;
    for (int i = 0; i < IE.6.access$0(this.this$1).closeWindowListeners.length; i++)
      IE.6.access$0(this.this$1).closeWindowListeners[i].close(localWindowEvent);
    IE.6.access$0(this.this$1).browser.dispose();
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.browser.IE.9
 * JD-Core Version:    0.6.2
 */