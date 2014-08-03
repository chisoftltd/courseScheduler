package org.eclipse.swt.internal.mozilla;

public class nsICookie extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 9;
  public static final String NS_ICOOKIE_IID_STR = "e9fcb9a4-d376-458f-b720-e65e7df593bc";
  public static final nsID NS_ICOOKIE_IID = new nsID("e9fcb9a4-d376-458f-b720-e65e7df593bc");
  public static final int STATUS_UNKNOWN = 0;
  public static final int STATUS_ACCEPTED = 1;
  public static final int STATUS_DOWNGRADED = 2;
  public static final int STATUS_FLAGGED = 3;
  public static final int STATUS_REJECTED = 4;
  public static final int POLICY_UNKNOWN = 0;
  public static final int POLICY_NONE = 1;
  public static final int POLICY_NO_CONSENT = 2;
  public static final int POLICY_IMPLICIT_CONSENT = 3;
  public static final int POLICY_EXPLICIT_CONSENT = 4;
  public static final int POLICY_NO_II = 5;

  public nsICookie(int paramInt)
  {
    super(paramInt);
  }

  public int GetName(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramInt);
  }

  public int GetValue(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress(), paramInt);
  }

  public int GetIsDomain(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 3, getAddress(), paramArrayOfInt);
  }

  public int GetHost(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 4, getAddress(), paramInt);
  }

  public int GetPath(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 5, getAddress(), paramInt);
  }

  public int GetIsSecure(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 6, getAddress(), paramArrayOfInt);
  }

  public int GetExpires(long[] paramArrayOfLong)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 7, getAddress(), paramArrayOfLong);
  }

  public int GetStatus(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 8, getAddress(), paramInt);
  }

  public int GetPolicy(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 9, getAddress(), paramInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsICookie
 * JD-Core Version:    0.6.2
 */