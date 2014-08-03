package org.eclipse.swt.internal.mozilla;

public class nsIWindowCreator2 extends nsIWindowCreator
{
  static final int LAST_METHOD_ID = nsIWindowCreator.LAST_METHOD_ID + 1;
  public static final String NS_IWINDOWCREATOR2_IID_STR = "f673ec81-a4b0-11d6-964b-eb5a2bf216fc";
  public static final nsID NS_IWINDOWCREATOR2_IID = new nsID("f673ec81-a4b0-11d6-964b-eb5a2bf216fc");
  public static final int PARENT_IS_LOADING_OR_RUNNING_TIMEOUT = 1;

  public nsIWindowCreator2(int paramInt)
  {
    super(paramInt);
  }

  public int CreateChromeWindow2(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    return XPCOM.VtblCall(nsIWindowCreator.LAST_METHOD_ID + 1, getAddress(), paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfInt1, paramArrayOfInt2);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIWindowCreator2
 * JD-Core Version:    0.6.2
 */