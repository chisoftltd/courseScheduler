package org.eclipse.swt.internal.mozilla;

public class nsIWeakReference extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 1;
  public static final String NS_IWEAKREFERENCE_IID_STR = "9188bc85-f92e-11d2-81ef-0060083a0bcf";
  public static final nsID NS_IWEAKREFERENCE_IID = new nsID("9188bc85-f92e-11d2-81ef-0060083a0bcf");

  public nsIWeakReference(int paramInt)
  {
    super(paramInt);
  }

  public int QueryReferent(nsID paramnsID, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramnsID, paramArrayOfInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIWeakReference
 * JD-Core Version:    0.6.2
 */