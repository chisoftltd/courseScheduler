package org.eclipse.swt.internal.mozilla;

public class nsIX509Cert extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 27;
  public static final String NS_IX509CERT_IID_STR = "f0980f60-ee3d-11d4-998b-00b0d02354a0";
  public static final nsID NS_IX509CERT_IID = new nsID("f0980f60-ee3d-11d4-998b-00b0d02354a0");
  public static final int UNKNOWN_CERT = 0;
  public static final int CA_CERT = 1;
  public static final int USER_CERT = 2;
  public static final int EMAIL_CERT = 4;
  public static final int SERVER_CERT = 8;
  public static final int VERIFIED_OK = 0;
  public static final int NOT_VERIFIED_UNKNOWN = 1;
  public static final int CERT_REVOKED = 2;
  public static final int CERT_EXPIRED = 4;
  public static final int CERT_NOT_TRUSTED = 8;
  public static final int ISSUER_NOT_TRUSTED = 16;
  public static final int ISSUER_UNKNOWN = 32;
  public static final int INVALID_CA = 64;
  public static final int USAGE_NOT_ALLOWED = 128;
  public static final int CERT_USAGE_SSLClient = 0;
  public static final int CERT_USAGE_SSLServer = 1;
  public static final int CERT_USAGE_SSLServerWithStepUp = 2;
  public static final int CERT_USAGE_SSLCA = 3;
  public static final int CERT_USAGE_EmailSigner = 4;
  public static final int CERT_USAGE_EmailRecipient = 5;
  public static final int CERT_USAGE_ObjectSigner = 6;
  public static final int CERT_USAGE_UserCertImport = 7;
  public static final int CERT_USAGE_VerifyCA = 8;
  public static final int CERT_USAGE_ProtectedObjectSigner = 9;
  public static final int CERT_USAGE_StatusResponder = 10;
  public static final int CERT_USAGE_AnyCA = 11;

  public nsIX509Cert(int paramInt)
  {
    super(paramInt);
  }

  public int GetNickname(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramInt);
  }

  public int GetEmailAddress(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress(), paramInt);
  }

  public int GetEmailAddresses(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 3, getAddress(), paramArrayOfInt1, paramArrayOfInt2);
  }

  public int ContainsEmailAddress(int paramInt, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 4, getAddress(), paramInt, paramArrayOfInt);
  }

  public int GetSubjectName(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 5, getAddress(), paramInt);
  }

  public int GetCommonName(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 6, getAddress(), paramInt);
  }

  public int GetOrganization(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 7, getAddress(), paramInt);
  }

  public int GetOrganizationalUnit(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 8, getAddress(), paramInt);
  }

  public int GetSha1Fingerprint(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 9, getAddress(), paramInt);
  }

  public int GetMd5Fingerprint(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 10, getAddress(), paramInt);
  }

  public int GetTokenName(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 11, getAddress(), paramInt);
  }

  public int GetIssuerName(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 12, getAddress(), paramInt);
  }

  public int GetSerialNumber(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 13, getAddress(), paramInt);
  }

  public int GetIssuerCommonName(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 14, getAddress(), paramInt);
  }

  public int GetIssuerOrganization(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 15, getAddress(), paramInt);
  }

  public int GetIssuerOrganizationUnit(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 16, getAddress(), paramInt);
  }

  public int GetIssuer(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 17, getAddress(), paramArrayOfInt);
  }

  public int GetValidity(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 18, getAddress(), paramArrayOfInt);
  }

  public int GetDbKey(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 19, getAddress(), paramArrayOfInt);
  }

  public int GetWindowTitle(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 20, getAddress(), paramArrayOfInt);
  }

  public int GetChain(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 21, getAddress(), paramArrayOfInt);
  }

  public int GetUsagesArray(int paramInt, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 22, getAddress(), paramInt, paramArrayOfInt1, paramArrayOfInt2, paramArrayOfInt3);
  }

  public int GetUsagesString(int paramInt1, int[] paramArrayOfInt, int paramInt2)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 23, getAddress(), paramInt1, paramArrayOfInt, paramInt2);
  }

  public int VerifyForUsage(int paramInt, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 24, getAddress(), paramInt, paramArrayOfInt);
  }

  public int GetASN1Structure(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 25, getAddress(), paramArrayOfInt);
  }

  public int GetRawDER(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 26, getAddress(), paramArrayOfInt1, paramArrayOfInt2);
  }

  public int Equals(int paramInt, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 27, getAddress(), paramInt, paramArrayOfInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIX509Cert
 * JD-Core Version:    0.6.2
 */