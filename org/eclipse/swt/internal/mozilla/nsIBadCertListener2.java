package org.eclipse.swt.internal.mozilla;

public class nsIBadCertListener2 extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 1;
  public static final String NS_IBADCERTLISTENER2_IID_STR = "2c3d268c-ad82-49f3-99aa-e9ffddd7a0dc";
  public static final nsID NS_IBADCERTLISTENER2_IID = new nsID("2c3d268c-ad82-49f3-99aa-e9ffddd7a0dc");

  public nsIBadCertListener2(int paramInt)
  {
    super(paramInt);
  }

  public int NotifyCertProblem(int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramInt1, paramInt2, paramInt3, paramArrayOfInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIBadCertListener2
 * JD-Core Version:    0.6.2
 */