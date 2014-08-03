package org.eclipse.swt.internal.mozilla;

public class nsIURIContentListener extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 8;
  public static final String NS_IURICONTENTLISTENER_IID_STR = "94928ab3-8b63-11d3-989d-001083010e9b";
  public static final nsID NS_IURICONTENTLISTENER_IID = new nsID("94928ab3-8b63-11d3-989d-001083010e9b");

  public nsIURIContentListener(int paramInt)
  {
    super(paramInt);
  }

  public int OnStartURIOpen(int paramInt, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramInt, paramArrayOfInt);
  }

  public int DoContent(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress(), paramArrayOfByte, paramInt1, paramInt2, paramArrayOfInt1, paramArrayOfInt2);
  }

  public int IsPreferred(byte[] paramArrayOfByte, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 3, getAddress(), paramArrayOfByte, paramArrayOfInt1, paramArrayOfInt2);
  }

  public int CanHandleContent(byte[] paramArrayOfByte, int paramInt, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 4, getAddress(), paramArrayOfByte, paramInt, paramArrayOfInt1, paramArrayOfInt2);
  }

  public int GetLoadCookie(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 5, getAddress(), paramArrayOfInt);
  }

  public int SetLoadCookie(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 6, getAddress(), paramInt);
  }

  public int GetParentContentListener(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 7, getAddress(), paramArrayOfInt);
  }

  public int SetParentContentListener(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 8, getAddress(), paramInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIURIContentListener
 * JD-Core Version:    0.6.2
 */