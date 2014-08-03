package org.eclipse.swt.internal.mozilla;

public class nsIEmbeddingSiteWindow extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 8;
  public static final String NS_IEMBEDDINGSITEWINDOW_IID_STR = "3e5432cd-9568-4bd1-8cbe-d50aba110743";
  public static final nsID NS_IEMBEDDINGSITEWINDOW_IID = new nsID("3e5432cd-9568-4bd1-8cbe-d50aba110743");
  public static final int DIM_FLAGS_POSITION = 1;
  public static final int DIM_FLAGS_SIZE_INNER = 2;
  public static final int DIM_FLAGS_SIZE_OUTER = 4;

  public nsIEmbeddingSiteWindow(int paramInt)
  {
    super(paramInt);
  }

  public int SetDimensions(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
  }

  public int GetDimensions(int paramInt, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3, int[] paramArrayOfInt4)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress(), paramInt, paramArrayOfInt1, paramArrayOfInt2, paramArrayOfInt3, paramArrayOfInt4);
  }

  public int SetFocus()
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 3, getAddress());
  }

  public int GetVisibility(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 4, getAddress(), paramArrayOfInt);
  }

  public int SetVisibility(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 5, getAddress(), paramInt);
  }

  public int GetTitle(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 6, getAddress(), paramArrayOfInt);
  }

  public int SetTitle(char[] paramArrayOfChar)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 7, getAddress(), paramArrayOfChar);
  }

  public int GetSiteWindow(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 8, getAddress(), paramArrayOfInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIEmbeddingSiteWindow
 * JD-Core Version:    0.6.2
 */