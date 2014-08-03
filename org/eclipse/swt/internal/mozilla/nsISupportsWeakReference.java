package org.eclipse.swt.internal.mozilla;

public class nsISupportsWeakReference extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 1;
  public static final String NS_ISUPPORTSWEAKREFERENCE_IID_STR = "9188bc86-f92e-11d2-81ef-0060083a0bcf";
  public static final nsID NS_ISUPPORTSWEAKREFERENCE_IID = new nsID("9188bc86-f92e-11d2-81ef-0060083a0bcf");

  public nsISupportsWeakReference(int paramInt)
  {
    super(paramInt);
  }

  public int GetWeakReference(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramArrayOfInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsISupportsWeakReference
 * JD-Core Version:    0.6.2
 */