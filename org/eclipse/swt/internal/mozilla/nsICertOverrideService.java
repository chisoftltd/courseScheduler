package org.eclipse.swt.internal.mozilla;

public class nsICertOverrideService extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 6;
  public static final String NS_ICERTOVERRIDESERVICE_IID_STR = "31738d2a-77d3-4359-84c9-4be2f38fb8c5";
  public static final nsID NS_ICERTOVERRIDESERVICE_IID = new nsID("31738d2a-77d3-4359-84c9-4be2f38fb8c5");
  public static final int ERROR_UNTRUSTED = 1;
  public static final int ERROR_MISMATCH = 2;
  public static final int ERROR_TIME = 4;

  public nsICertOverrideService(int paramInt)
  {
    super(paramInt);
  }

  public int RememberValidityOverride(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
  }

  public int HasMatchingOverride(int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress(), paramInt1, paramInt2, paramInt3, paramArrayOfInt1, paramArrayOfInt2, paramArrayOfInt3);
  }

  public int GetValidityOverride(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 3, getAddress(), paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfInt1, paramArrayOfInt2, paramArrayOfInt3);
  }

  public int ClearValidityOverride(int paramInt1, int paramInt2)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 4, getAddress(), paramInt1, paramInt2);
  }

  public int GetAllOverrideHostsWithPorts(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 5, getAddress(), paramArrayOfInt1, paramArrayOfInt2);
  }

  public int IsCertUsedForOverrides(int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 6, getAddress(), paramInt1, paramInt2, paramInt3, paramArrayOfInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsICertOverrideService
 * JD-Core Version:    0.6.2
 */