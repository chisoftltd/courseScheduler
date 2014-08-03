package org.eclipse.swt.internal.mozilla;

public class nsICookieManager extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 3;
  public static final String NS_ICOOKIEMANAGER_IID_STR = "aaab6710-0f2c-11d5-a53b-0010a401eb10";
  public static final nsID NS_ICOOKIEMANAGER_IID = new nsID("aaab6710-0f2c-11d5-a53b-0010a401eb10");

  public nsICookieManager(int paramInt)
  {
    super(paramInt);
  }

  public int RemoveAll()
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress());
  }

  public int GetEnumerator(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress(), paramArrayOfInt);
  }

  public int Remove(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 3, getAddress(), paramInt1, paramInt2, paramInt3, paramInt4);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsICookieManager
 * JD-Core Version:    0.6.2
 */