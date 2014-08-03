package org.eclipse.swt.internal.mozilla;

public class nsIHelperAppLauncher extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 9;
  public static final String NS_IHELPERAPPLAUNCHER_IID_STR = "9503d0fe-4c9d-11d4-98d0-001083010e9b";
  public static final nsID NS_IHELPERAPPLAUNCHER_IID = new nsID("9503d0fe-4c9d-11d4-98d0-001083010e9b");

  public nsIHelperAppLauncher(int paramInt)
  {
    super(paramInt);
  }

  public int GetMIMEInfo(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramArrayOfInt);
  }

  public int GetSource(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress(), paramArrayOfInt);
  }

  public int GetSuggestedFileName(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 3, getAddress(), paramArrayOfInt);
  }

  public int SaveToDisk(int paramInt1, int paramInt2)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 4, getAddress(), paramInt1, paramInt2);
  }

  public int LaunchWithApplication(int paramInt1, int paramInt2)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 5, getAddress(), paramInt1, paramInt2);
  }

  public int Cancel()
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 6, getAddress());
  }

  public int SetWebProgressListener(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 7, getAddress(), paramInt);
  }

  public int CloseProgressWindow()
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 8, getAddress());
  }

  public int GetDownloadInfo(int[] paramArrayOfInt1, long[] paramArrayOfLong, int[] paramArrayOfInt2)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 9, getAddress(), paramArrayOfInt1, paramArrayOfLong, paramArrayOfInt2);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIHelperAppLauncher
 * JD-Core Version:    0.6.2
 */