package org.eclipse.swt.internal.mozilla;

public class nsIPrefService extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 6;
  public static final String NS_IPREFSERVICE_IID_STR = "decb9cc7-c08f-4ea5-be91-a8fc637ce2d2";
  public static final nsID NS_IPREFSERVICE_IID = new nsID("decb9cc7-c08f-4ea5-be91-a8fc637ce2d2");

  public nsIPrefService(int paramInt)
  {
    super(paramInt);
  }

  public int ReadUserPrefs(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramInt);
  }

  public int ResetPrefs()
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress());
  }

  public int ResetUserPrefs()
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 3, getAddress());
  }

  public int SavePrefFile(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 4, getAddress(), paramInt);
  }

  public int GetBranch(byte[] paramArrayOfByte, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 5, getAddress(), paramArrayOfByte, paramArrayOfInt);
  }

  public int GetDefaultBranch(byte[] paramArrayOfByte, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 6, getAddress(), paramArrayOfByte, paramArrayOfInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIPrefService
 * JD-Core Version:    0.6.2
 */