package org.eclipse.swt.internal.mozilla;

public class nsIWebBrowserFocus extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 8;
  public static final String NS_IWEBBROWSERFOCUS_IID_STR = "9c5d3c58-1dd1-11b2-a1c9-f3699284657a";
  public static final nsID NS_IWEBBROWSERFOCUS_IID = new nsID("9c5d3c58-1dd1-11b2-a1c9-f3699284657a");

  public nsIWebBrowserFocus(int paramInt)
  {
    super(paramInt);
  }

  public int Activate()
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress());
  }

  public int Deactivate()
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress());
  }

  public int SetFocusAtFirstElement()
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 3, getAddress());
  }

  public int SetFocusAtLastElement()
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 4, getAddress());
  }

  public int GetFocusedWindow(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 5, getAddress(), paramArrayOfInt);
  }

  public int SetFocusedWindow(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 6, getAddress(), paramInt);
  }

  public int GetFocusedElement(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 7, getAddress(), paramArrayOfInt);
  }

  public int SetFocusedElement(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 8, getAddress(), paramInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIWebBrowserFocus
 * JD-Core Version:    0.6.2
 */