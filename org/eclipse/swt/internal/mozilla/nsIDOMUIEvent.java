package org.eclipse.swt.internal.mozilla;

public class nsIDOMUIEvent extends nsIDOMEvent
{
  static final int LAST_METHOD_ID = nsIDOMEvent.LAST_METHOD_ID + 3;
  public static final String NS_IDOMUIEVENT_IID_STR = "a6cf90c3-15b3-11d2-932e-00805f8add32";
  public static final nsID NS_IDOMUIEVENT_IID = new nsID("a6cf90c3-15b3-11d2-932e-00805f8add32");

  public nsIDOMUIEvent(int paramInt)
  {
    super(paramInt);
  }

  public int GetView(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsIDOMEvent.LAST_METHOD_ID + 1, getAddress(), paramArrayOfInt);
  }

  public int GetDetail(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsIDOMEvent.LAST_METHOD_ID + 2, getAddress(), paramArrayOfInt);
  }

  public int InitUIEvent(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    return XPCOM.VtblCall(nsIDOMEvent.LAST_METHOD_ID + 3, getAddress(), paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIDOMUIEvent
 * JD-Core Version:    0.6.2
 */