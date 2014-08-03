package org.eclipse.swt.internal.mozilla;

public class nsIMIMEInputStream extends nsIInputStream
{
  static final int LAST_METHOD_ID = nsIInputStream.LAST_METHOD_ID + 4;
  public static final String NS_IMIMEINPUTSTREAM_IID_STR = "dcbce63c-1dd1-11b2-b94d-91f6d49a3161";
  public static final nsID NS_IMIMEINPUTSTREAM_IID = new nsID("dcbce63c-1dd1-11b2-b94d-91f6d49a3161");

  public nsIMIMEInputStream(int paramInt)
  {
    super(paramInt);
  }

  public int GetAddContentLength(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsIInputStream.LAST_METHOD_ID + 1, getAddress(), paramArrayOfInt);
  }

  public int SetAddContentLength(int paramInt)
  {
    return XPCOM.VtblCall(nsIInputStream.LAST_METHOD_ID + 2, getAddress(), paramInt);
  }

  public int AddHeader(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    return XPCOM.VtblCall(nsIInputStream.LAST_METHOD_ID + 3, getAddress(), paramArrayOfByte1, paramArrayOfByte2);
  }

  public int SetData(int paramInt)
  {
    return XPCOM.VtblCall(nsIInputStream.LAST_METHOD_ID + 4, getAddress(), paramInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIMIMEInputStream
 * JD-Core Version:    0.6.2
 */