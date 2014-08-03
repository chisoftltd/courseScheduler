package org.eclipse.swt.internal.mozilla;

public class nsIWebNavigation extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 13;
  public static final String NS_IWEBNAVIGATION_IID_STR = "f5d9e7b0-d930-11d3-b057-00a024ffc08c";
  public static final nsID NS_IWEBNAVIGATION_IID = new nsID("f5d9e7b0-d930-11d3-b057-00a024ffc08c");
  public static final int LOAD_FLAGS_MASK = 65535;
  public static final int LOAD_FLAGS_NONE = 0;
  public static final int LOAD_FLAGS_IS_REFRESH = 16;
  public static final int LOAD_FLAGS_IS_LINK = 32;
  public static final int LOAD_FLAGS_BYPASS_HISTORY = 64;
  public static final int LOAD_FLAGS_REPLACE_HISTORY = 128;
  public static final int LOAD_FLAGS_BYPASS_CACHE = 256;
  public static final int LOAD_FLAGS_BYPASS_PROXY = 512;
  public static final int LOAD_FLAGS_CHARSET_CHANGE = 1024;
  public static final int STOP_NETWORK = 1;
  public static final int STOP_CONTENT = 2;
  public static final int STOP_ALL = 3;

  public nsIWebNavigation(int paramInt)
  {
    super(paramInt);
  }

  public int GetCanGoBack(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramArrayOfInt);
  }

  public int GetCanGoForward(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress(), paramArrayOfInt);
  }

  public int GoBack()
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 3, getAddress());
  }

  public int GoForward()
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 4, getAddress());
  }

  public int GotoIndex(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 5, getAddress(), paramInt);
  }

  public int LoadURI(char[] paramArrayOfChar, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 6, getAddress(), paramArrayOfChar, paramInt1, paramInt2, paramInt3, paramInt4);
  }

  public int Reload(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 7, getAddress(), paramInt);
  }

  public int Stop(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 8, getAddress(), paramInt);
  }

  public int GetDocument(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 9, getAddress(), paramArrayOfInt);
  }

  public int GetCurrentURI(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 10, getAddress(), paramArrayOfInt);
  }

  public int GetReferringURI(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 11, getAddress(), paramArrayOfInt);
  }

  public int GetSessionHistory(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 12, getAddress(), paramArrayOfInt);
  }

  public int SetSessionHistory(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 13, getAddress(), paramInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIWebNavigation
 * JD-Core Version:    0.6.2
 */