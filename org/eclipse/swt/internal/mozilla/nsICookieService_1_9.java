package org.eclipse.swt.internal.mozilla;

public class nsICookieService_1_9 extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 4;
  public static final String NS_ICOOKIESERVICE_IID_STR = "2aaa897a-293c-4d2b-a657-8c9b7136996d";
  public static final nsID NS_ICOOKIESERVICE_IID = new nsID("2aaa897a-293c-4d2b-a657-8c9b7136996d");

  public nsICookieService_1_9(int paramInt)
  {
    super(paramInt);
  }

  public int GetCookieString(int paramInt1, int paramInt2, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramInt1, paramInt2, paramArrayOfInt);
  }

  public int GetCookieStringFromHttp(int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress(), paramInt1, paramInt2, paramInt3, paramArrayOfInt);
  }

  public int SetCookieString(int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 3, getAddress(), paramInt1, paramInt2, paramArrayOfByte, paramInt3);
  }

  public int SetCookieStringFromHttp(int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt4)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 4, getAddress(), paramInt1, paramInt2, paramInt3, paramArrayOfByte1, paramArrayOfByte2, paramInt4);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsICookieService_1_9
 * JD-Core Version:    0.6.2
 */