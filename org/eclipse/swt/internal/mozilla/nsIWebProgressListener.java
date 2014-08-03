package org.eclipse.swt.internal.mozilla;

public class nsIWebProgressListener extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 5;
  public static final String NS_IWEBPROGRESSLISTENER_IID_STR = "570f39d1-efd0-11d3-b093-00a024ffc08c";
  public static final nsID NS_IWEBPROGRESSLISTENER_IID = new nsID("570f39d1-efd0-11d3-b093-00a024ffc08c");
  public static final int STATE_START = 1;
  public static final int STATE_REDIRECTING = 2;
  public static final int STATE_TRANSFERRING = 4;
  public static final int STATE_NEGOTIATING = 8;
  public static final int STATE_STOP = 16;
  public static final int STATE_IS_REQUEST = 65536;
  public static final int STATE_IS_DOCUMENT = 131072;
  public static final int STATE_IS_NETWORK = 262144;
  public static final int STATE_IS_WINDOW = 524288;
  public static final int STATE_IS_INSECURE = 4;
  public static final int STATE_IS_BROKEN = 1;
  public static final int STATE_IS_SECURE = 2;
  public static final int STATE_SECURE_HIGH = 262144;
  public static final int STATE_SECURE_MED = 65536;
  public static final int STATE_SECURE_LOW = 131072;

  public nsIWebProgressListener(int paramInt)
  {
    super(paramInt);
  }

  public int OnStateChange(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramInt1, paramInt2, paramInt3, paramInt4);
  }

  public int OnProgressChange(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress(), paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
  }

  public int OnLocationChange(int paramInt1, int paramInt2, int paramInt3)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 3, getAddress(), paramInt1, paramInt2, paramInt3);
  }

  public int OnStatusChange(int paramInt1, int paramInt2, int paramInt3, char[] paramArrayOfChar)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 4, getAddress(), paramInt1, paramInt2, paramInt3, paramArrayOfChar);
  }

  public int OnSecurityChange(int paramInt1, int paramInt2, int paramInt3)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 5, getAddress(), paramInt1, paramInt2, paramInt3);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIWebProgressListener
 * JD-Core Version:    0.6.2
 */