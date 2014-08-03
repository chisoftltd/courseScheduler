package org.eclipse.swt.internal.mozilla;

public class nsITooltipListener extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 2;
  public static final String NS_ITOOLTIPLISTENER_IID_STR = "44b78386-1dd2-11b2-9ad2-e4eee2ca1916";
  public static final nsID NS_ITOOLTIPLISTENER_IID = new nsID("44b78386-1dd2-11b2-9ad2-e4eee2ca1916");

  public nsITooltipListener(int paramInt)
  {
    super(paramInt);
  }

  public int OnShowTooltip(int paramInt1, int paramInt2, char[] paramArrayOfChar)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramInt1, paramInt2, paramArrayOfChar);
  }

  public int OnHideTooltip()
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress());
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsITooltipListener
 * JD-Core Version:    0.6.2
 */