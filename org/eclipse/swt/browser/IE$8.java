package org.eclipse.swt.browser;

class IE$8
  implements Runnable
{
  final IE.6 this$1;
  private final String val$url;
  private final String val$host;

  IE$8(IE.6 param6, String paramString1, String paramString2)
  {
    this.this$1 = param6;
    this.val$url = paramString1;
    this.val$host = paramString2;
  }

  public void run()
  {
    if (IE.6.access$0(this.this$1).browser.isDisposed())
      return;
    if (this.val$url.endsWith("\\"))
      IE.6.access$0(this.this$1).uncRedirect = this.val$url.substring(0, this.val$url.length() - 1);
    else
      IE.6.access$0(this.this$1).uncRedirect = this.val$url;
    IE.6.access$0(this.this$1).navigate(this.val$host, null, null, true);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.browser.IE.8
 * JD-Core Version:    0.6.2
 */