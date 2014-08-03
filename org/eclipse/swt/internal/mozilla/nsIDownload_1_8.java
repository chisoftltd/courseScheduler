package org.eclipse.swt.internal.mozilla;

public class nsIDownload_1_8 extends nsITransfer
{
  static final int LAST_METHOD_ID = nsITransfer.LAST_METHOD_ID + 10;
  public static final String NS_IDOWNLOAD_IID_STR = "9e1fd9f2-9727-4926-85cd-f16c375bba6d";
  public static final nsID NS_IDOWNLOAD_IID = new nsID("9e1fd9f2-9727-4926-85cd-f16c375bba6d");

  public nsIDownload_1_8(int paramInt)
  {
    super(paramInt);
  }

  public int GetTargetFile(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsITransfer.LAST_METHOD_ID + 1, getAddress(), paramArrayOfInt);
  }

  public int GetPercentComplete(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsITransfer.LAST_METHOD_ID + 2, getAddress(), paramArrayOfInt);
  }

  public int GetAmountTransferred(int paramInt)
  {
    return XPCOM.VtblCall(nsITransfer.LAST_METHOD_ID + 3, getAddress(), paramInt);
  }

  public int GetSize(int paramInt)
  {
    return XPCOM.VtblCall(nsITransfer.LAST_METHOD_ID + 4, getAddress(), paramInt);
  }

  public int GetSource(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsITransfer.LAST_METHOD_ID + 5, getAddress(), paramArrayOfInt);
  }

  public int GetTarget(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsITransfer.LAST_METHOD_ID + 6, getAddress(), paramArrayOfInt);
  }

  public int GetCancelable(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsITransfer.LAST_METHOD_ID + 7, getAddress(), paramArrayOfInt);
  }

  public int GetDisplayName(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsITransfer.LAST_METHOD_ID + 8, getAddress(), paramArrayOfInt);
  }

  public int GetStartTime(long[] paramArrayOfLong)
  {
    return XPCOM.VtblCall(nsITransfer.LAST_METHOD_ID + 9, getAddress(), paramArrayOfLong);
  }

  public int GetMIMEInfo(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsITransfer.LAST_METHOD_ID + 10, getAddress(), paramArrayOfInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIDownload_1_8
 * JD-Core Version:    0.6.2
 */