package org.eclipse.swt.internal.mozilla;

public class nsIFactory extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 2;
  public static final String NS_IFACTORY_IID_STR = "00000001-0000-0000-c000-000000000046";
  public static final nsID NS_IFACTORY_IID = new nsID("00000001-0000-0000-c000-000000000046");

  public nsIFactory(int paramInt)
  {
    super(paramInt);
  }

  public int CreateInstance(int paramInt, nsID paramnsID, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramInt, paramnsID, paramArrayOfInt);
  }

  public int LockFactory(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress(), paramInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIFactory
 * JD-Core Version:    0.6.2
 */