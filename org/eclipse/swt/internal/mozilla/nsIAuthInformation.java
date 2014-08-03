package org.eclipse.swt.internal.mozilla;

public class nsIAuthInformation extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 9;
  public static final String NS_IAUTHINFORMATION_IID_STR = "0d73639c-2a92-4518-9f92-28f71fea5f20";
  public static final nsID NS_IAUTHINFORMATION_IID = new nsID("0d73639c-2a92-4518-9f92-28f71fea5f20");
  public static final int AUTH_HOST = 1;
  public static final int AUTH_PROXY = 2;
  public static final int NEED_DOMAIN = 4;
  public static final int ONLY_PASSWORD = 8;

  public nsIAuthInformation(int paramInt)
  {
    super(paramInt);
  }

  public int GetFlags(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramArrayOfInt);
  }

  public int GetRealm(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress(), paramInt);
  }

  public int GetAuthenticationScheme(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 3, getAddress(), paramInt);
  }

  public int GetUsername(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 4, getAddress(), paramInt);
  }

  public int SetUsername(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 5, getAddress(), paramInt);
  }

  public int GetPassword(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 6, getAddress(), paramInt);
  }

  public int SetPassword(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 7, getAddress(), paramInt);
  }

  public int GetDomain(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 8, getAddress(), paramInt);
  }

  public int SetDomain(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 9, getAddress(), paramInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIAuthInformation
 * JD-Core Version:    0.6.2
 */