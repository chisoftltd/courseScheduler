package org.eclipse.swt.internal.mozilla;

public class nsIBaseWindow extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 24;
  public static final String NS_IBASEWINDOW_IID_STR = "046bc8a0-8015-11d3-af70-00a024ffc08c";
  public static final nsID NS_IBASEWINDOW_IID = new nsID("046bc8a0-8015-11d3-af70-00a024ffc08c");

  public nsIBaseWindow(int paramInt)
  {
    super(paramInt);
  }

  public int InitWindow(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
  }

  public int Create()
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress());
  }

  public int Destroy()
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 3, getAddress());
  }

  public int SetPosition(int paramInt1, int paramInt2)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 4, getAddress(), paramInt1, paramInt2);
  }

  public int GetPosition(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 5, getAddress(), paramArrayOfInt1, paramArrayOfInt2);
  }

  public int SetSize(int paramInt1, int paramInt2, int paramInt3)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 6, getAddress(), paramInt1, paramInt2, paramInt3);
  }

  public int GetSize(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 7, getAddress(), paramArrayOfInt1, paramArrayOfInt2);
  }

  public int SetPositionAndSize(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 8, getAddress(), paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
  }

  public int GetPositionAndSize(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3, int[] paramArrayOfInt4)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 9, getAddress(), paramArrayOfInt1, paramArrayOfInt2, paramArrayOfInt3, paramArrayOfInt4);
  }

  public int Repaint(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 10, getAddress(), paramInt);
  }

  public int GetParentWidget(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 11, getAddress(), paramArrayOfInt);
  }

  public int SetParentWidget(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 12, getAddress(), paramInt);
  }

  public int GetParentNativeWindow(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 13, getAddress(), paramArrayOfInt);
  }

  public int SetParentNativeWindow(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 14, getAddress(), paramInt);
  }

  public int GetVisibility(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 15, getAddress(), paramArrayOfInt);
  }

  public int SetVisibility(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 16, getAddress(), paramInt);
  }

  public int GetEnabled(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 17, getAddress(), paramArrayOfInt);
  }

  public int SetEnabled(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 18, getAddress(), paramInt);
  }

  public int GetBlurSuppression(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 19, getAddress(), paramArrayOfInt);
  }

  public int SetBlurSuppression(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 20, getAddress(), paramInt);
  }

  public int GetMainWidget(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 21, getAddress(), paramArrayOfInt);
  }

  public int SetFocus()
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 22, getAddress());
  }

  public int GetTitle(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 23, getAddress(), paramArrayOfInt);
  }

  public int SetTitle(char[] paramArrayOfChar)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 24, getAddress(), paramArrayOfChar);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIBaseWindow
 * JD-Core Version:    0.6.2
 */