package org.eclipse.swt.internal.mozilla;

public class nsIDownload extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 13;
  public static final String NS_IDOWNLOAD_IID_STR = "06cb92f2-1dd2-11b2-95f2-96dfdfb804a1";
  public static final nsID NS_IDOWNLOAD_IID = new nsID("06cb92f2-1dd2-11b2-95f2-96dfdfb804a1");

  public nsIDownload(int paramInt)
  {
    super(paramInt);
  }

  public int Init(int paramInt1, int paramInt2, char[] paramArrayOfChar, int paramInt3, long paramLong, int paramInt4)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramInt1, paramInt2, paramArrayOfChar, paramInt3, paramLong, paramInt4);
  }

  public int GetSource(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress(), paramArrayOfInt);
  }

  public int GetTarget(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 3, getAddress(), paramArrayOfInt);
  }

  public int GetPersist(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 4, getAddress(), paramArrayOfInt);
  }

  public int GetPercentComplete(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 5, getAddress(), paramArrayOfInt);
  }

  public int GetDisplayName(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 6, getAddress(), paramArrayOfInt);
  }

  public int SetDisplayName(char[] paramArrayOfChar)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 7, getAddress(), paramArrayOfChar);
  }

  public int GetStartTime(long[] paramArrayOfLong)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 8, getAddress(), paramArrayOfLong);
  }

  public int GetMIMEInfo(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 9, getAddress(), paramArrayOfInt);
  }

  public int GetListener(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 10, getAddress(), paramArrayOfInt);
  }

  public int SetListener(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 11, getAddress(), paramInt);
  }

  public int GetObserver(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 12, getAddress(), paramArrayOfInt);
  }

  public int SetObserver(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 13, getAddress(), paramInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIDownload
 * JD-Core Version:    0.6.2
 */