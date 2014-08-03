package org.eclipse.swt.internal.mozilla;

public class nsICookieService extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 5;
  public static final String NS_ICOOKIESERVICE_IID_STR = "011c3190-1434-11d6-a618-0010a401eb10";
  public static final nsID NS_ICOOKIESERVICE_IID = new nsID("011c3190-1434-11d6-a618-0010a401eb10");

  public nsICookieService(int paramInt)
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

  public int GetCookieIconIsVisible(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 5, getAddress(), paramArrayOfInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsICookieService
 * JD-Core Version:    0.6.2
 */