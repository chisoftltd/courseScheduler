package org.eclipse.swt.internal.mozilla;

public class nsIDOMWindowCollection extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 3;
  public static final String NS_IDOMWINDOWCOLLECTION_IID_STR = "a6cf906f-15b3-11d2-932e-00805f8add32";
  public static final nsID NS_IDOMWINDOWCOLLECTION_IID = new nsID("a6cf906f-15b3-11d2-932e-00805f8add32");

  public nsIDOMWindowCollection(int paramInt)
  {
    super(paramInt);
  }

  public int GetLength(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramArrayOfInt);
  }

  public int Item(int paramInt, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress(), paramInt, paramArrayOfInt);
  }

  public int NamedItem(int paramInt, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 3, getAddress(), paramInt, paramArrayOfInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIDOMWindowCollection
 * JD-Core Version:    0.6.2
 */