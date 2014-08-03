package org.eclipse.swt.internal.mozilla;

public class nsIDOMSerializer_1_7 extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 2;
  public static final String NS_IDOMSERIALIZER_IID_STR = "9fd4ba15-e67c-4c98-b52c-7715f62c9196";
  public static final nsID NS_IDOMSERIALIZER_IID = new nsID("9fd4ba15-e67c-4c98-b52c-7715f62c9196");

  public nsIDOMSerializer_1_7(int paramInt)
  {
    super(paramInt);
  }

  public int SerializeToString(int paramInt1, int paramInt2)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramInt1, paramInt2);
  }

  public int SerializeToStream(int paramInt1, int paramInt2, int paramInt3)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress(), paramInt1, paramInt2, paramInt3);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIDOMSerializer_1_7
 * JD-Core Version:    0.6.2
 */