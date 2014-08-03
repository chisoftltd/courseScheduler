package org.eclipse.swt.internal.mozilla;

public class nsIDOMEvent extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 10;
  public static final String NS_IDOMEVENT_IID_STR = "a66b7b80-ff46-bd97-0080-5f8ae38add32";
  public static final nsID NS_IDOMEVENT_IID = new nsID("a66b7b80-ff46-bd97-0080-5f8ae38add32");
  public static final int CAPTURING_PHASE = 1;
  public static final int AT_TARGET = 2;
  public static final int BUBBLING_PHASE = 3;

  public nsIDOMEvent(int paramInt)
  {
    super(paramInt);
  }

  public int GetType(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramInt);
  }

  public int GetTarget(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress(), paramArrayOfInt);
  }

  public int GetCurrentTarget(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 3, getAddress(), paramArrayOfInt);
  }

  public int GetEventPhase(short[] paramArrayOfShort)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 4, getAddress(), paramArrayOfShort);
  }

  public int GetBubbles(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 5, getAddress(), paramArrayOfInt);
  }

  public int GetCancelable(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 6, getAddress(), paramArrayOfInt);
  }

  public int GetTimeStamp(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 7, getAddress(), paramArrayOfInt);
  }

  public int StopPropagation()
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 8, getAddress());
  }

  public int PreventDefault()
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 9, getAddress());
  }

  public int InitEvent(int paramInt1, int paramInt2, int paramInt3)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 10, getAddress(), paramInt1, paramInt2, paramInt3);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIDOMEvent
 * JD-Core Version:    0.6.2
 */