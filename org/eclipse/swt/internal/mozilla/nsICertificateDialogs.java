package org.eclipse.swt.internal.mozilla;

public class nsICertificateDialogs extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 6;
  public static final String NS_ICERTIFICATEDIALOGS_IID_STR = "a03ca940-09be-11d5-ac5d-000064657374";
  public static final nsID NS_ICERTIFICATEDIALOGS_IID = new nsID("a03ca940-09be-11d5-ac5d-000064657374");

  public nsICertificateDialogs(int paramInt)
  {
    super(paramInt);
  }

  public int ConfirmDownloadCACert(int paramInt1, int paramInt2, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramInt1, paramInt2, paramArrayOfInt1, paramArrayOfInt2);
  }

  public int NotifyCACertExists(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress(), paramInt);
  }

  public int SetPKCS12FilePassword(int paramInt1, int paramInt2, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 3, getAddress(), paramInt1, paramInt2, paramArrayOfInt);
  }

  public int GetPKCS12FilePassword(int paramInt1, int paramInt2, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 4, getAddress(), paramInt1, paramInt2, paramArrayOfInt);
  }

  public int ViewCert(int paramInt1, int paramInt2)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 5, getAddress(), paramInt1, paramInt2);
  }

  public int CrlImportStatusDialog(int paramInt1, int paramInt2)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 6, getAddress(), paramInt1, paramInt2);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsICertificateDialogs
 * JD-Core Version:    0.6.2
 */