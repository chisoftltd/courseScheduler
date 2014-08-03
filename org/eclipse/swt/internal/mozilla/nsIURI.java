package org.eclipse.swt.internal.mozilla;

public class nsIURI extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 26;
  public static final String NS_IURI_IID_STR = "07a22cc0-0ce5-11d3-9331-00104ba0fd40";
  public static final nsID NS_IURI_IID = new nsID("07a22cc0-0ce5-11d3-9331-00104ba0fd40");

  public nsIURI(int paramInt)
  {
    super(paramInt);
  }

  public int GetSpec(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramInt);
  }

  public int SetSpec(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress(), paramInt);
  }

  public int GetPrePath(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 3, getAddress(), paramInt);
  }

  public int GetScheme(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 4, getAddress(), paramInt);
  }

  public int SetScheme(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 5, getAddress(), paramInt);
  }

  public int GetUserPass(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 6, getAddress(), paramInt);
  }

  public int SetUserPass(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 7, getAddress(), paramInt);
  }

  public int GetUsername(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 8, getAddress(), paramInt);
  }

  public int SetUsername(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 9, getAddress(), paramInt);
  }

  public int GetPassword(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 10, getAddress(), paramInt);
  }

  public int SetPassword(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 11, getAddress(), paramInt);
  }

  public int GetHostPort(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 12, getAddress(), paramInt);
  }

  public int SetHostPort(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 13, getAddress(), paramInt);
  }

  public int GetHost(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 14, getAddress(), paramInt);
  }

  public int SetHost(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 15, getAddress(), paramInt);
  }

  public int GetPort(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 16, getAddress(), paramArrayOfInt);
  }

  public int SetPort(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 17, getAddress(), paramInt);
  }

  public int GetPath(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 18, getAddress(), paramInt);
  }

  public int SetPath(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 19, getAddress(), paramInt);
  }

  public int Equals(int paramInt, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 20, getAddress(), paramInt, paramArrayOfInt);
  }

  public int SchemeIs(byte[] paramArrayOfByte, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 21, getAddress(), paramArrayOfByte, paramArrayOfInt);
  }

  public int Clone(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 22, getAddress(), paramArrayOfInt);
  }

  public int Resolve(int paramInt1, int paramInt2)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 23, getAddress(), paramInt1, paramInt2);
  }

  public int GetAsciiSpec(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 24, getAddress(), paramInt);
  }

  public int GetAsciiHost(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 25, getAddress(), paramInt);
  }

  public int GetOriginCharset(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 26, getAddress(), paramInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIURI
 * JD-Core Version:    0.6.2
 */