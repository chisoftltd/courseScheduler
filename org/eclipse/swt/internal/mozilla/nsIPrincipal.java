package org.eclipse.swt.internal.mozilla;

public class nsIPrincipal extends nsISerializable
{
  static final int LAST_METHOD_ID = nsISerializable.LAST_METHOD_ID + 23;
  public static final String NS_IPRINCIPAL_IID_STR = "b8268b9a-2403-44ed-81e3-614075c92034";
  public static final nsID NS_IPRINCIPAL_IID = new nsID("b8268b9a-2403-44ed-81e3-614075c92034");
  public static final int ENABLE_DENIED = 1;
  public static final int ENABLE_UNKNOWN = 2;
  public static final int ENABLE_WITH_USER_PERMISSION = 3;
  public static final int ENABLE_GRANTED = 4;

  public nsIPrincipal(int paramInt)
  {
    super(paramInt);
  }

  public int GetPreferences(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3, int[] paramArrayOfInt4, int[] paramArrayOfInt5, int[] paramArrayOfInt6)
  {
    return XPCOM.VtblCall(nsISerializable.LAST_METHOD_ID + 1, getAddress(), paramArrayOfInt1, paramArrayOfInt2, paramArrayOfInt3, paramArrayOfInt4, paramArrayOfInt5, paramArrayOfInt6);
  }

  public int Equals(int paramInt, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISerializable.LAST_METHOD_ID + 2, getAddress(), paramInt, paramArrayOfInt);
  }

  public int GetHashValue(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISerializable.LAST_METHOD_ID + 3, getAddress(), paramArrayOfInt);
  }

  public int GetJSPrincipals(int paramInt, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISerializable.LAST_METHOD_ID + 4, getAddress(), paramInt, paramArrayOfInt);
  }

  public int GetSecurityPolicy(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISerializable.LAST_METHOD_ID + 5, getAddress(), paramArrayOfInt);
  }

  public int SetSecurityPolicy(int paramInt)
  {
    return XPCOM.VtblCall(nsISerializable.LAST_METHOD_ID + 6, getAddress(), paramInt);
  }

  public int CanEnableCapability(byte[] paramArrayOfByte, int paramInt)
  {
    return XPCOM.VtblCall(nsISerializable.LAST_METHOD_ID + 7, getAddress(), paramArrayOfByte, paramInt);
  }

  public int SetCanEnableCapability(byte[] paramArrayOfByte, short paramShort)
  {
    return XPCOM.VtblCall(nsISerializable.LAST_METHOD_ID + 8, getAddress(), paramArrayOfByte, paramShort);
  }

  public int IsCapabilityEnabled(byte[] paramArrayOfByte, int paramInt, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISerializable.LAST_METHOD_ID + 9, getAddress(), paramArrayOfByte, paramInt, paramArrayOfInt);
  }

  public int EnableCapability(byte[] paramArrayOfByte, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISerializable.LAST_METHOD_ID + 10, getAddress(), paramArrayOfByte, paramArrayOfInt);
  }

  public int RevertCapability(byte[] paramArrayOfByte, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISerializable.LAST_METHOD_ID + 11, getAddress(), paramArrayOfByte, paramArrayOfInt);
  }

  public int DisableCapability(byte[] paramArrayOfByte, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISerializable.LAST_METHOD_ID + 12, getAddress(), paramArrayOfByte, paramArrayOfInt);
  }

  public int GetURI(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISerializable.LAST_METHOD_ID + 13, getAddress(), paramArrayOfInt);
  }

  public int GetDomain(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISerializable.LAST_METHOD_ID + 14, getAddress(), paramArrayOfInt);
  }

  public int SetDomain(int paramInt)
  {
    return XPCOM.VtblCall(nsISerializable.LAST_METHOD_ID + 15, getAddress(), paramInt);
  }

  public int GetOrigin(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISerializable.LAST_METHOD_ID + 16, getAddress(), paramArrayOfInt);
  }

  public int GetHasCertificate(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISerializable.LAST_METHOD_ID + 17, getAddress(), paramArrayOfInt);
  }

  public int GetFingerprint(int paramInt)
  {
    return XPCOM.VtblCall(nsISerializable.LAST_METHOD_ID + 18, getAddress(), paramInt);
  }

  public int GetPrettyName(int paramInt)
  {
    return XPCOM.VtblCall(nsISerializable.LAST_METHOD_ID + 19, getAddress(), paramInt);
  }

  public int Subsumes(int paramInt, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISerializable.LAST_METHOD_ID + 20, getAddress(), paramInt, paramArrayOfInt);
  }

  public int CheckMayLoad(int paramInt1, int paramInt2)
  {
    return XPCOM.VtblCall(nsISerializable.LAST_METHOD_ID + 21, getAddress(), paramInt1, paramInt2);
  }

  public int GetSubjectName(int paramInt)
  {
    return XPCOM.VtblCall(nsISerializable.LAST_METHOD_ID + 22, getAddress(), paramInt);
  }

  public int GetCertificate(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISerializable.LAST_METHOD_ID + 23, getAddress(), paramArrayOfInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIPrincipal
 * JD-Core Version:    0.6.2
 */