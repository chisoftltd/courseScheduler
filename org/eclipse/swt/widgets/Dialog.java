package org.eclipse.swt.widgets;

import org.eclipse.swt.SWT;

public abstract class Dialog
{
  int style;
  Shell parent;
  String title;

  public Dialog(Shell paramShell)
  {
    this(paramShell, 32768);
  }

  public Dialog(Shell paramShell, int paramInt)
  {
    checkParent(paramShell);
    this.parent = paramShell;
    this.style = paramInt;
    this.title = "";
  }

  protected void checkSubclass()
  {
    if (!Display.isValidClass(getClass()))
      error(43);
  }

  void checkParent(Shell paramShell)
  {
    if (paramShell == null)
      error(4);
    paramShell.checkWidget();
  }

  static int checkStyle(Shell paramShell, int paramInt)
  {
    int i = 229376;
    if ((paramInt & 0x10000000) != 0)
    {
      paramInt &= -268435457;
      if ((paramInt & i) == 0)
        paramInt |= (paramShell == null ? 65536 : 32768);
    }
    if ((paramInt & i) == 0)
      paramInt |= 65536;
    paramInt &= -134217729;
    if (((paramInt & 0x6000000) == 0) && (paramShell != null))
    {
      if ((paramShell.style & 0x2000000) != 0)
        paramInt |= 33554432;
      if ((paramShell.style & 0x4000000) != 0)
        paramInt |= 67108864;
    }
    return Widget.checkBits(paramInt, 33554432, 67108864, 0, 0, 0, 0);
  }

  void error(int paramInt)
  {
    SWT.error(paramInt);
  }

  public Shell getParent()
  {
    return this.parent;
  }

  public int getStyle()
  {
    return this.style;
  }

  public String getText()
  {
    return this.title;
  }

  public void setText(String paramString)
  {
    if (paramString == null)
      error(4);
    this.title = paramString;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.Dialog
 * JD-Core Version:    0.6.2
 */