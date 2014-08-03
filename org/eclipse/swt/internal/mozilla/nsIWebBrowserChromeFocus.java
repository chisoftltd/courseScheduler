package org.eclipse.swt.internal.mozilla;

public class nsIWebBrowserChromeFocus extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 2;
  public static final String NS_IWEBBROWSERCHROMEFOCUS_IID_STR = "d2206418-1dd1-11b2-8e55-acddcd2bcfb8";
  public static final nsID NS_IWEBBROWSERCHROMEFOCUS_IID = new nsID("d2206418-1dd1-11b2-8e55-acddcd2bcfb8");

  public nsIWebBrowserChromeFocus(int paramInt)
  {
    super(paramInt);
  }

  public int FocusNextElement()
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress());
  }

  public int FocusPrevElement()
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress());
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIWebBrowserChromeFocus
 * JD-Core Version:    0.6.2
 */