package org.eclipse.swt.internal.mozilla;

public class nsIInputStream extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 5;
  public static final String NS_IINPUTSTREAM_IID_STR = "fa9c7f6c-61b3-11d4-9877-00c04fa0cf4a";
  public static final nsID NS_IINPUTSTREAM_IID = new nsID("fa9c7f6c-61b3-11d4-9877-00c04fa0cf4a");

  public nsIInputStream(int paramInt)
  {
    super(paramInt);
  }

  public int Close()
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress());
  }

  public int Available(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress(), paramArrayOfInt);
  }

  public int Read(byte[] paramArrayOfByte, int paramInt, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 3, getAddress(), paramArrayOfByte, paramInt, paramArrayOfInt);
  }

  public int ReadSegments(int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 4, getAddress(), paramInt1, paramInt2, paramInt3, paramArrayOfInt);
  }

  public int IsNonBlocking(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 5, getAddress(), paramArrayOfInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIInputStream
 * JD-Core Version:    0.6.2
 */