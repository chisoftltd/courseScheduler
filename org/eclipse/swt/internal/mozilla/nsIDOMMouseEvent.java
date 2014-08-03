package org.eclipse.swt.internal.mozilla;

public class nsIDOMMouseEvent extends nsIDOMUIEvent
{
  static final int LAST_METHOD_ID = nsIDOMUIEvent.LAST_METHOD_ID + 11;
  public static final String NS_IDOMMOUSEEVENT_IID_STR = "ff751edc-8b02-aae7-0010-8301838a3123";
  public static final nsID NS_IDOMMOUSEEVENT_IID = new nsID("ff751edc-8b02-aae7-0010-8301838a3123");

  public nsIDOMMouseEvent(int paramInt)
  {
    super(paramInt);
  }

  public int GetScreenX(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsIDOMUIEvent.LAST_METHOD_ID + 1, getAddress(), paramArrayOfInt);
  }

  public int GetScreenY(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsIDOMUIEvent.LAST_METHOD_ID + 2, getAddress(), paramArrayOfInt);
  }

  public int GetClientX(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsIDOMUIEvent.LAST_METHOD_ID + 3, getAddress(), paramArrayOfInt);
  }

  public int GetClientY(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsIDOMUIEvent.LAST_METHOD_ID + 4, getAddress(), paramArrayOfInt);
  }

  public int GetCtrlKey(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsIDOMUIEvent.LAST_METHOD_ID + 5, getAddress(), paramArrayOfInt);
  }

  public int GetShiftKey(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsIDOMUIEvent.LAST_METHOD_ID + 6, getAddress(), paramArrayOfInt);
  }

  public int GetAltKey(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsIDOMUIEvent.LAST_METHOD_ID + 7, getAddress(), paramArrayOfInt);
  }

  public int GetMetaKey(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsIDOMUIEvent.LAST_METHOD_ID + 8, getAddress(), paramArrayOfInt);
  }

  public int GetButton(short[] paramArrayOfShort)
  {
    return XPCOM.VtblCall(nsIDOMUIEvent.LAST_METHOD_ID + 9, getAddress(), paramArrayOfShort);
  }

  public int GetRelatedTarget(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsIDOMUIEvent.LAST_METHOD_ID + 10, getAddress(), paramArrayOfInt);
  }

  public int InitMouseEvent(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11, int paramInt12, int paramInt13, short paramShort, int paramInt14)
  {
    return XPCOM.VtblCall(nsIDOMUIEvent.LAST_METHOD_ID + 11, getAddress(), paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramInt9, paramInt10, paramInt11, paramInt12, paramInt13, paramShort, paramInt14);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIDOMMouseEvent
 * JD-Core Version:    0.6.2
 */