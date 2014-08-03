package org.eclipse.swt.browser;

import org.eclipse.swt.SWT;

public class BrowserFunction
{
  Browser browser;
  String name;
  String functionString;
  int index;
  boolean isEvaluate;

  public BrowserFunction(Browser paramBrowser, String paramString)
  {
    this(paramBrowser, paramString, true);
  }

  BrowserFunction(Browser paramBrowser, String paramString, boolean paramBoolean)
  {
    if (paramBrowser == null)
      SWT.error(4);
    if (paramString == null)
      SWT.error(4);
    if (paramBrowser.isDisposed())
      SWT.error(24);
    paramBrowser.checkWidget();
    this.browser = paramBrowser;
    this.name = paramString;
    if (paramBoolean)
      paramBrowser.webBrowser.createFunction(this);
  }

  public void dispose()
  {
    dispose(true);
  }

  void dispose(boolean paramBoolean)
  {
    if (this.index < 0)
      return;
    if (paramBoolean)
      this.browser.webBrowser.destroyFunction(this);
    this.browser = null;
    this.name = (this.functionString = null);
    this.index = -1;
  }

  public Object function(Object[] paramArrayOfObject)
  {
    if (this.index < 0)
      SWT.error(49);
    this.browser.checkWidget();
    return null;
  }

  public Browser getBrowser()
  {
    if (this.index < 0)
      SWT.error(49);
    this.browser.checkWidget();
    return this.browser;
  }

  public String getName()
  {
    if (this.index < 0)
      SWT.error(49);
    this.browser.checkWidget();
    return this.name;
  }

  public boolean isDisposed()
  {
    return this.index < 0;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.browser.BrowserFunction
 * JD-Core Version:    0.6.2
 */