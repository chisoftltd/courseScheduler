package org.eclipse.swt.internal.mozilla;

public class nsIContextMenuListener extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 1;
  public static final String NS_ICONTEXTMENULISTENER_IID_STR = "3478b6b0-3875-11d4-94ef-0020183bf181";
  public static final nsID NS_ICONTEXTMENULISTENER_IID = new nsID("3478b6b0-3875-11d4-94ef-0020183bf181");
  public static final int CONTEXT_NONE = 0;
  public static final int CONTEXT_LINK = 1;
  public static final int CONTEXT_IMAGE = 2;
  public static final int CONTEXT_DOCUMENT = 4;
  public static final int CONTEXT_TEXT = 8;
  public static final int CONTEXT_INPUT = 16;

  public nsIContextMenuListener(int paramInt)
  {
    super(paramInt);
  }

  public int OnShowContextMenu(int paramInt1, int paramInt2, int paramInt3)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramInt1, paramInt2, paramInt3);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIContextMenuListener
 * JD-Core Version:    0.6.2
 */