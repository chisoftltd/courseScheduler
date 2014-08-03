package org.eclipse.swt.internal.mozilla;

public class nsITransfer extends nsIWebProgressListener2
{
  static final int LAST_METHOD_ID = nsIWebProgressListener2.LAST_METHOD_ID + 1;
  public static final String NS_ITRANSFER_IID_STR = "23c51569-e9a1-4a92-adeb-3723db82ef7c";
  public static final nsID NS_ITRANSFER_IID = new nsID("23c51569-e9a1-4a92-adeb-3723db82ef7c");

  public nsITransfer(int paramInt)
  {
    super(paramInt);
  }

  public int Init(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong, int paramInt5, int paramInt6)
  {
    return XPCOM.VtblCall(nsIWebProgressListener2.LAST_METHOD_ID + 1, getAddress(), paramInt1, paramInt2, paramInt3, paramInt4, paramLong, paramInt5, paramInt6);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsITransfer
 * JD-Core Version:    0.6.2
 */