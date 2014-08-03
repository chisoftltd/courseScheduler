package org.eclipse.swt.browser;

import org.eclipse.swt.widgets.Display;

class PromptDialog$3
  implements Runnable
{
  final PromptDialog.2 this$1;
  private final Browser val$browser;

  PromptDialog$3(PromptDialog.2 param2, Browser paramBrowser)
  {
    this.this$1 = param2;
    this.val$browser = paramBrowser;
  }

  public void run()
  {
    this.val$browser.getDisplay().timerExec(1000, this);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.browser.PromptDialog.3
 * JD-Core Version:    0.6.2
 */