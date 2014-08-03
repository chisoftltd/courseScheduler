package org.eclipse.swt.internal.mozilla;

public class nsIX509CertValidity extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 8;
  public static final String NS_IX509CERTVALIDITY_IID_STR = "e701dfd8-1dd1-11b2-a172-ffa6cc6156ad";
  public static final nsID NS_IX509CERTVALIDITY_IID = new nsID("e701dfd8-1dd1-11b2-a172-ffa6cc6156ad");

  public nsIX509CertValidity(int paramInt)
  {
    super(paramInt);
  }

  public int GetNotBefore(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramInt);
  }

  public int GetNotBeforeLocalTime(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress(), paramInt);
  }

  public int GetNotBeforeLocalDay(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 3, getAddress(), paramInt);
  }

  public int GetNotBeforeGMT(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 4, getAddress(), paramInt);
  }

  public int GetNotAfter(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 5, getAddress(), paramInt);
  }

  public int GetNotAfterLocalTime(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 6, getAddress(), paramInt);
  }

  public int GetNotAfterLocalDay(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 7, getAddress(), paramInt);
  }

  public int GetNotAfterGMT(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 8, getAddress(), paramInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIX509CertValidity
 * JD-Core Version:    0.6.2
 */