package org.eclipse.swt.internal.mozilla;

public class nsIWebProgress extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 4;
  public static final String NS_IWEBPROGRESS_IID_STR = "570f39d0-efd0-11d3-b093-00a024ffc08c";
  public static final nsID NS_IWEBPROGRESS_IID = new nsID("570f39d0-efd0-11d3-b093-00a024ffc08c");
  public static final int NOTIFY_STATE_REQUEST = 1;
  public static final int NOTIFY_STATE_DOCUMENT = 2;
  public static final int NOTIFY_STATE_NETWORK = 4;
  public static final int NOTIFY_STATE_WINDOW = 8;
  public static final int NOTIFY_STATE_ALL = 15;
  public static final int NOTIFY_PROGRESS = 16;
  public static final int NOTIFY_STATUS = 32;
  public static final int NOTIFY_SECURITY = 64;
  public static final int NOTIFY_LOCATION = 128;
  public static final int NOTIFY_ALL = 255;

  public nsIWebProgress(int paramInt)
  {
    super(paramInt);
  }

  public int AddProgressListener(int paramInt1, int paramInt2)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramInt1, paramInt2);
  }

  public int RemoveProgressListener(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress(), paramInt);
  }

  public int GetDOMWindow(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 3, getAddress(), paramArrayOfInt);
  }

  public int GetIsLoadingDocument(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 4, getAddress(), paramArrayOfInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIWebProgress
 * JD-Core Version:    0.6.2
 */