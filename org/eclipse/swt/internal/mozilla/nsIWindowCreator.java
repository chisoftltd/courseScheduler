package org.eclipse.swt.internal.mozilla;

public class nsIWindowCreator extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 1;
  public static final String NS_IWINDOWCREATOR_IID_STR = "30465632-a777-44cc-90f9-8145475ef999";
  public static final nsID NS_IWINDOWCREATOR_IID = new nsID("30465632-a777-44cc-90f9-8145475ef999");

  public nsIWindowCreator(int paramInt)
  {
    super(paramInt);
  }

  public int CreateChromeWindow(int paramInt1, int paramInt2, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramInt1, paramInt2, paramArrayOfInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIWindowCreator
 * JD-Core Version:    0.6.2
 */