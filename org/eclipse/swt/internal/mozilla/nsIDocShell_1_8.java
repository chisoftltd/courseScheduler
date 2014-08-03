package org.eclipse.swt.internal.mozilla;

public class nsIDocShell_1_8 extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 61;
  public static final String NS_IDOCSHELL_IID_STR = "9f0c7461-b9a4-47f6-b88c-421dce1bce66";
  public static final nsID NS_IDOCSHELL_IID = new nsID("9f0c7461-b9a4-47f6-b88c-421dce1bce66");
  public static final int INTERNAL_LOAD_FLAGS_NONE = 0;
  public static final int INTERNAL_LOAD_FLAGS_INHERIT_OWNER = 1;
  public static final int INTERNAL_LOAD_FLAGS_DONT_SEND_REFERRER = 2;
  public static final int ENUMERATE_FORWARDS = 0;
  public static final int ENUMERATE_BACKWARDS = 1;
  public static final int APP_TYPE_UNKNOWN = 0;
  public static final int APP_TYPE_MAIL = 1;
  public static final int APP_TYPE_EDITOR = 2;
  public static final int BUSY_FLAGS_NONE = 0;
  public static final int BUSY_FLAGS_BUSY = 1;
  public static final int BUSY_FLAGS_BEFORE_PAGE_LOAD = 2;
  public static final int BUSY_FLAGS_PAGE_LOADING = 4;
  public static final int LOAD_CMD_NORMAL = 1;
  public static final int LOAD_CMD_RELOAD = 2;
  public static final int LOAD_CMD_HISTORY = 4;

  public nsIDocShell_1_8(int paramInt)
  {
    super(paramInt);
  }

  public int LoadURI(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramInt1, paramInt2, paramInt3, paramInt4);
  }

  public int LoadStream(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress(), paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
  }

  public int InternalLoad(int paramInt1, int paramInt2, int paramInt3, int paramInt4, char[] paramArrayOfChar, byte[] paramArrayOfByte, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 3, getAddress(), paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfChar, paramArrayOfByte, paramInt5, paramInt6, paramInt7, paramInt8, paramInt9, paramArrayOfInt1, paramArrayOfInt2);
  }

  public int CreateLoadInfo(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 4, getAddress(), paramArrayOfInt);
  }

  public int PrepareForNewContentModel()
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 5, getAddress());
  }

  public int SetCurrentURI(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 6, getAddress(), paramInt);
  }

  public int FirePageHideNotification(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 7, getAddress(), paramInt);
  }

  public int GetPresContext(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 8, getAddress(), paramArrayOfInt);
  }

  public int GetPresShell(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 9, getAddress(), paramArrayOfInt);
  }

  public int GetEldestPresShell(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 10, getAddress(), paramArrayOfInt);
  }

  public int GetContentViewer(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 11, getAddress(), paramArrayOfInt);
  }

  public int GetChromeEventHandler(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 12, getAddress(), paramArrayOfInt);
  }

  public int SetChromeEventHandler(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 13, getAddress(), paramInt);
  }

  public int GetDocumentCharsetInfo(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 14, getAddress(), paramArrayOfInt);
  }

  public int SetDocumentCharsetInfo(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 15, getAddress(), paramInt);
  }

  public int GetAllowPlugins(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 16, getAddress(), paramArrayOfInt);
  }

  public int SetAllowPlugins(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 17, getAddress(), paramInt);
  }

  public int GetAllowJavascript(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 18, getAddress(), paramArrayOfInt);
  }

  public int SetAllowJavascript(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 19, getAddress(), paramInt);
  }

  public int GetAllowMetaRedirects(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 20, getAddress(), paramArrayOfInt);
  }

  public int SetAllowMetaRedirects(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 21, getAddress(), paramInt);
  }

  public int GetAllowSubframes(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 22, getAddress(), paramArrayOfInt);
  }

  public int SetAllowSubframes(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 23, getAddress(), paramInt);
  }

  public int GetAllowImages(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 24, getAddress(), paramArrayOfInt);
  }

  public int SetAllowImages(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 25, getAddress(), paramInt);
  }

  public int GetDocShellEnumerator(int paramInt1, int paramInt2, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 26, getAddress(), paramInt1, paramInt2, paramArrayOfInt);
  }

  public int GetAppType(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 27, getAddress(), paramArrayOfInt);
  }

  public int SetAppType(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 28, getAddress(), paramInt);
  }

  public int GetAllowAuth(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 29, getAddress(), paramArrayOfInt);
  }

  public int SetAllowAuth(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 30, getAddress(), paramInt);
  }

  public int GetZoom(float[] paramArrayOfFloat)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 31, getAddress(), paramArrayOfFloat);
  }

  public int SetZoom(float paramFloat)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 32, getAddress(), paramFloat);
  }

  public int GetMarginWidth(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 33, getAddress(), paramArrayOfInt);
  }

  public int SetMarginWidth(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 34, getAddress(), paramInt);
  }

  public int GetMarginHeight(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 35, getAddress(), paramArrayOfInt);
  }

  public int SetMarginHeight(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 36, getAddress(), paramInt);
  }

  public int GetHasFocus(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 37, getAddress(), paramArrayOfInt);
  }

  public int SetHasFocus(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 38, getAddress(), paramInt);
  }

  public int GetCanvasHasFocus(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 39, getAddress(), paramArrayOfInt);
  }

  public int SetCanvasHasFocus(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 40, getAddress(), paramInt);
  }

  public int TabToTreeOwner(int paramInt, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 41, getAddress(), paramInt, paramArrayOfInt);
  }

  public int GetBusyFlags(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 42, getAddress(), paramArrayOfInt);
  }

  public int GetLoadType(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 43, getAddress(), paramArrayOfInt);
  }

  public int SetLoadType(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 44, getAddress(), paramInt);
  }

  public int IsBeingDestroyed(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 45, getAddress(), paramArrayOfInt);
  }

  public int GetIsExecutingOnLoadHandler(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 46, getAddress(), paramArrayOfInt);
  }

  public int GetLayoutHistoryState(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 47, getAddress(), paramArrayOfInt);
  }

  public int SetLayoutHistoryState(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 48, getAddress(), paramInt);
  }

  public int GetShouldSaveLayoutState(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 49, getAddress(), paramArrayOfInt);
  }

  public int GetSecurityUI(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 50, getAddress(), paramArrayOfInt);
  }

  public int SetSecurityUI(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 51, getAddress(), paramInt);
  }

  public int SuspendRefreshURIs()
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 52, getAddress());
  }

  public int ResumeRefreshURIs()
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 53, getAddress());
  }

  public int BeginRestore(int paramInt1, int paramInt2)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 54, getAddress(), paramInt1, paramInt2);
  }

  public int FinishRestore()
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 55, getAddress());
  }

  public int GetRestoringDocument(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 56, getAddress(), paramArrayOfInt);
  }

  public int GetUseErrorPages(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 57, getAddress(), paramArrayOfInt);
  }

  public int SetUseErrorPages(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 58, getAddress(), paramInt);
  }

  public int GetPreviousTransIndex(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 59, getAddress(), paramArrayOfInt);
  }

  public int GetLoadedTransIndex(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 60, getAddress(), paramArrayOfInt);
  }

  public int HistoryPurged(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 61, getAddress(), paramInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIDocShell_1_8
 * JD-Core Version:    0.6.2
 */