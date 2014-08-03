package org.eclipse.swt.internal.mozilla;

public class nsIWebBrowser extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 7;
  public static final String NS_IWEBBROWSER_IID_STR = "69e5df00-7b8b-11d3-af61-00a024ffc08c";
  public static final nsID NS_IWEBBROWSER_IID = new nsID("69e5df00-7b8b-11d3-af61-00a024ffc08c");

  public nsIWebBrowser(int paramInt)
  {
    super(paramInt);
  }

  public int AddWebBrowserListener(int paramInt, nsID paramnsID)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramInt, paramnsID);
  }

  public int RemoveWebBrowserListener(int paramInt, nsID paramnsID)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress(), paramInt, paramnsID);
  }

  public int GetContainerWindow(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 3, getAddress(), paramArrayOfInt);
  }

  public int SetContainerWindow(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 4, getAddress(), paramInt);
  }

  public int GetParentURIContentListener(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 5, getAddress(), paramArrayOfInt);
  }

  public int SetParentURIContentListener(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 6, getAddress(), paramInt);
  }

  public int GetContentDOMWindow(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 7, getAddress(), paramArrayOfInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIWebBrowser
 * JD-Core Version:    0.6.2
 */