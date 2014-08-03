package org.eclipse.swt.internal.mozilla;

public class nsIWebBrowserChrome extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 10;
  public static final String NS_IWEBBROWSERCHROME_IID_STR = "ba434c60-9d52-11d3-afb0-00a024ffc08c";
  public static final nsID NS_IWEBBROWSERCHROME_IID = new nsID("ba434c60-9d52-11d3-afb0-00a024ffc08c");
  public static final int STATUS_SCRIPT = 1;
  public static final int STATUS_SCRIPT_DEFAULT = 2;
  public static final int STATUS_LINK = 3;
  public static final int CHROME_DEFAULT = 1;
  public static final int CHROME_WINDOW_BORDERS = 2;
  public static final int CHROME_WINDOW_CLOSE = 4;
  public static final int CHROME_WINDOW_RESIZE = 8;
  public static final int CHROME_MENUBAR = 16;
  public static final int CHROME_TOOLBAR = 32;
  public static final int CHROME_LOCATIONBAR = 64;
  public static final int CHROME_STATUSBAR = 128;
  public static final int CHROME_PERSONAL_TOOLBAR = 256;
  public static final int CHROME_SCROLLBARS = 512;
  public static final int CHROME_TITLEBAR = 1024;
  public static final int CHROME_EXTRA = 2048;
  public static final int CHROME_WITH_SIZE = 4096;
  public static final int CHROME_WITH_POSITION = 8192;
  public static final int CHROME_WINDOW_MIN = 16384;
  public static final int CHROME_WINDOW_POPUP = 32768;
  public static final int CHROME_WINDOW_RAISED = 33554432;
  public static final int CHROME_WINDOW_LOWERED = 67108864;
  public static final int CHROME_CENTER_SCREEN = 134217728;
  public static final int CHROME_DEPENDENT = 268435456;
  public static final int CHROME_MODAL = 536870912;
  public static final int CHROME_OPENAS_DIALOG = 1073741824;
  public static final int CHROME_OPENAS_CHROME = -2147483648;
  public static final int CHROME_ALL = 4094;

  public nsIWebBrowserChrome(int paramInt)
  {
    super(paramInt);
  }

  public int SetStatus(int paramInt, char[] paramArrayOfChar)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramInt, paramArrayOfChar);
  }

  public int GetWebBrowser(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress(), paramArrayOfInt);
  }

  public int SetWebBrowser(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 3, getAddress(), paramInt);
  }

  public int GetChromeFlags(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 4, getAddress(), paramArrayOfInt);
  }

  public int SetChromeFlags(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 5, getAddress(), paramInt);
  }

  public int DestroyBrowserWindow()
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 6, getAddress());
  }

  public int SizeBrowserTo(int paramInt1, int paramInt2)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 7, getAddress(), paramInt1, paramInt2);
  }

  public int ShowAsModal()
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 8, getAddress());
  }

  public int IsWindowModal(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 9, getAddress(), paramArrayOfInt);
  }

  public int ExitModalEventLoop(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 10, getAddress(), paramInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIWebBrowserChrome
 * JD-Core Version:    0.6.2
 */