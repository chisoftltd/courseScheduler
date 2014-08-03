package org.eclipse.swt.internal.mozilla;

public class nsIProgressDialog_1_8 extends nsIDownload_1_8
{
  static final int LAST_METHOD_ID = nsIDownload_1_8.LAST_METHOD_ID + 7;
  public static final String NS_IPROGRESSDIALOG_IID_STR = "20e790a2-76c6-462d-851a-22ab6cbbe48b";
  public static final nsID NS_IPROGRESSDIALOG_IID = new nsID("20e790a2-76c6-462d-851a-22ab6cbbe48b");

  public nsIProgressDialog_1_8(int paramInt)
  {
    super(paramInt);
  }

  public int Open(int paramInt)
  {
    return XPCOM.VtblCall(nsIDownload_1_8.LAST_METHOD_ID + 1, getAddress(), paramInt);
  }

  public int GetCancelDownloadOnClose(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsIDownload_1_8.LAST_METHOD_ID + 2, getAddress(), paramArrayOfInt);
  }

  public int SetCancelDownloadOnClose(int paramInt)
  {
    return XPCOM.VtblCall(nsIDownload_1_8.LAST_METHOD_ID + 3, getAddress(), paramInt);
  }

  public int GetObserver(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsIDownload_1_8.LAST_METHOD_ID + 4, getAddress(), paramArrayOfInt);
  }

  public int SetObserver(int paramInt)
  {
    return XPCOM.VtblCall(nsIDownload_1_8.LAST_METHOD_ID + 5, getAddress(), paramInt);
  }

  public int GetDialog(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsIDownload_1_8.LAST_METHOD_ID + 6, getAddress(), paramArrayOfInt);
  }

  public int SetDialog(int paramInt)
  {
    return XPCOM.VtblCall(nsIDownload_1_8.LAST_METHOD_ID + 7, getAddress(), paramInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIProgressDialog_1_8
 * JD-Core Version:    0.6.2
 */