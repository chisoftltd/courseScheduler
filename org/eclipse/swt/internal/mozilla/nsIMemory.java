package org.eclipse.swt.internal.mozilla;

public class nsIMemory extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 5;
  public static final String NS_IMEMORY_IID_STR = "59e7e77a-38e4-11d4-8cf5-0060b0fc14a3";
  public static final nsID NS_IMEMORY_IID = new nsID("59e7e77a-38e4-11d4-8cf5-0060b0fc14a3");

  public nsIMemory(int paramInt)
  {
    super(paramInt);
  }

  public int Alloc(int paramInt)
  {
    return XPCOM.nsIMemory_Alloc(getAddress(), paramInt);
  }

  public int Realloc(int paramInt1, int paramInt2)
  {
    return XPCOM.nsIMemory_Realloc(getAddress(), paramInt1, paramInt2);
  }

  public int Free(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 3, getAddress(), paramInt);
  }

  public int HeapMinimize(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 4, getAddress(), paramInt);
  }

  public int IsLowMemory(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 5, getAddress(), paramArrayOfInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIMemory
 * JD-Core Version:    0.6.2
 */