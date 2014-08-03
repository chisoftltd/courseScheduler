package org.eclipse.swt.internal.mozilla;

public class nsIHelperAppLauncher_1_8 extends nsICancelable
{
  static final int LAST_METHOD_ID = nsICancelable.LAST_METHOD_ID + 9;
  public static final String NS_IHELPERAPPLAUNCHER_IID_STR = "99a0882d-2ff9-4659-9952-9ac531ba5592";
  public static final nsID NS_IHELPERAPPLAUNCHER_IID = new nsID("99a0882d-2ff9-4659-9952-9ac531ba5592");

  public nsIHelperAppLauncher_1_8(int paramInt)
  {
    super(paramInt);
  }

  public int GetMIMEInfo(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsICancelable.LAST_METHOD_ID + 1, getAddress(), paramArrayOfInt);
  }

  public int GetSource(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsICancelable.LAST_METHOD_ID + 2, getAddress(), paramArrayOfInt);
  }

  public int GetSuggestedFileName(int paramInt)
  {
    return XPCOM.VtblCall(nsICancelable.LAST_METHOD_ID + 3, getAddress(), paramInt);
  }

  public int SaveToDisk(int paramInt1, int paramInt2)
  {
    return XPCOM.VtblCall(nsICancelable.LAST_METHOD_ID + 4, getAddress(), paramInt1, paramInt2);
  }

  public int LaunchWithApplication(int paramInt1, int paramInt2)
  {
    return XPCOM.VtblCall(nsICancelable.LAST_METHOD_ID + 5, getAddress(), paramInt1, paramInt2);
  }

  public int SetWebProgressListener(int paramInt)
  {
    return XPCOM.VtblCall(nsICancelable.LAST_METHOD_ID + 6, getAddress(), paramInt);
  }

  public int CloseProgressWindow()
  {
    return XPCOM.VtblCall(nsICancelable.LAST_METHOD_ID + 7, getAddress());
  }

  public int GetTargetFile(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsICancelable.LAST_METHOD_ID + 8, getAddress(), paramArrayOfInt);
  }

  public int GetTimeDownloadStarted(int paramInt)
  {
    return XPCOM.VtblCall(nsICancelable.LAST_METHOD_ID + 9, getAddress(), paramInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIHelperAppLauncher_1_8
 * JD-Core Version:    0.6.2
 */