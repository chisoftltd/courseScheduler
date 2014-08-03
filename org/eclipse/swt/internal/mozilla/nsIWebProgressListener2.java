package org.eclipse.swt.internal.mozilla;

public class nsIWebProgressListener2 extends nsIWebProgressListener
{
  static final int LAST_METHOD_ID = nsIWebProgressListener.LAST_METHOD_ID + 1;
  public static final String NS_IWEBPROGRESSLISTENER2_IID_STR = "3f24610d-1e1f-4151-9d2e-239884742324";
  public static final nsID NS_IWEBPROGRESSLISTENER2_IID = new nsID("3f24610d-1e1f-4151-9d2e-239884742324");

  public nsIWebProgressListener2(int paramInt)
  {
    super(paramInt);
  }

  public int OnProgressChange64(int paramInt1, int paramInt2, long paramLong1, long paramLong2, long paramLong3, long paramLong4)
  {
    return XPCOM.VtblCall(nsIWebProgressListener.LAST_METHOD_ID + 1, getAddress(), paramInt1, paramInt2, paramLong1, paramLong2, paramLong3, paramLong4);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIWebProgressListener2
 * JD-Core Version:    0.6.2
 */