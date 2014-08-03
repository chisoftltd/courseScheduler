package org.eclipse.swt.internal.mozilla;

public class nsIDOMEventTarget extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 3;
  public static final String NS_IDOMEVENTTARGET_IID_STR = "1c773b30-d1cf-11d2-bd95-00805f8ae3f4";
  public static final nsID NS_IDOMEVENTTARGET_IID = new nsID("1c773b30-d1cf-11d2-bd95-00805f8ae3f4");

  public nsIDOMEventTarget(int paramInt)
  {
    super(paramInt);
  }

  public int AddEventListener(int paramInt1, int paramInt2, int paramInt3)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramInt1, paramInt2, paramInt3);
  }

  public int RemoveEventListener(int paramInt1, int paramInt2, int paramInt3)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress(), paramInt1, paramInt2, paramInt3);
  }

  public int DispatchEvent(int paramInt, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 3, getAddress(), paramInt, paramArrayOfInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIDOMEventTarget
 * JD-Core Version:    0.6.2
 */