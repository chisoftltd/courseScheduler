package org.eclipse.swt.internal.mozilla;

public class nsISimpleEnumerator extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 2;
  public static final String NS_ISIMPLEENUMERATOR_IID_STR = "d1899240-f9d2-11d2-bdd6-000064657374";
  public static final nsID NS_ISIMPLEENUMERATOR_IID = new nsID("d1899240-f9d2-11d2-bdd6-000064657374");

  public nsISimpleEnumerator(int paramInt)
  {
    super(paramInt);
  }

  public int HasMoreElements(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramArrayOfInt);
  }

  public int GetNext(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress(), paramArrayOfInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsISimpleEnumerator
 * JD-Core Version:    0.6.2
 */