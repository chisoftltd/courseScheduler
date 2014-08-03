package org.eclipse.swt.internal.mozilla;

public class nsIProgressDialog extends nsIDownload
{
  static final int LAST_METHOD_ID = nsIDownload.LAST_METHOD_ID + 5;
  public static final String NS_IPROGRESSDIALOG_IID_STR = "88a478b3-af65-440a-94dc-ed9b154d2990";
  public static final nsID NS_IPROGRESSDIALOG_IID = new nsID("88a478b3-af65-440a-94dc-ed9b154d2990");

  public nsIProgressDialog(int paramInt)
  {
    super(paramInt);
  }

  public int Open(int paramInt)
  {
    return XPCOM.VtblCall(nsIDownload.LAST_METHOD_ID + 1, getAddress(), paramInt);
  }

  public int GetCancelDownloadOnClose(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsIDownload.LAST_METHOD_ID + 2, getAddress(), paramArrayOfInt);
  }

  public int SetCancelDownloadOnClose(int paramInt)
  {
    return XPCOM.VtblCall(nsIDownload.LAST_METHOD_ID + 3, getAddress(), paramInt);
  }

  public int GetDialog(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsIDownload.LAST_METHOD_ID + 4, getAddress(), paramArrayOfInt);
  }

  public int SetDialog(int paramInt)
  {
    return XPCOM.VtblCall(nsIDownload.LAST_METHOD_ID + 5, getAddress(), paramInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIProgressDialog
 * JD-Core Version:    0.6.2
 */