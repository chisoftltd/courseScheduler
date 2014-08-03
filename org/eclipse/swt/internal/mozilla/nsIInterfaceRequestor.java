package org.eclipse.swt.internal.mozilla;

public class nsIInterfaceRequestor extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 1;
  public static final String NS_IINTERFACEREQUESTOR_IID_STR = "033a1470-8b2a-11d3-af88-00a024ffc08c";
  public static final nsID NS_IINTERFACEREQUESTOR_IID = new nsID("033a1470-8b2a-11d3-af88-00a024ffc08c");

  public nsIInterfaceRequestor(int paramInt)
  {
    super(paramInt);
  }

  public int GetInterface(nsID paramnsID, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramnsID, paramArrayOfInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIInterfaceRequestor
 * JD-Core Version:    0.6.2
 */