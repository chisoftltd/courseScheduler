package org.eclipse.swt.internal.mozilla;

public class nsIJSContextStack extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 4;
  public static final String NS_IJSCONTEXTSTACK_IID_STR = "c67d8270-3189-11d3-9885-006008962422";
  public static final nsID NS_IJSCONTEXTSTACK_IID = new nsID("c67d8270-3189-11d3-9885-006008962422");

  public nsIJSContextStack(int paramInt)
  {
    super(paramInt);
  }

  public int GetCount(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramArrayOfInt);
  }

  public int Peek(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress(), paramArrayOfInt);
  }

  public int Pop(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 3, getAddress(), paramArrayOfInt);
  }

  public int Push(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 4, getAddress(), paramInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIJSContextStack
 * JD-Core Version:    0.6.2
 */