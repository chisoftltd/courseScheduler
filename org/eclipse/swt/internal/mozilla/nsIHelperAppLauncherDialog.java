package org.eclipse.swt.internal.mozilla;

public class nsIHelperAppLauncherDialog extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 3;
  public static final String NS_IHELPERAPPLAUNCHERDIALOG_IID_STR = "d7ebddf0-4c84-11d4-807a-00600811a9c3";
  public static final nsID NS_IHELPERAPPLAUNCHERDIALOG_IID = new nsID("d7ebddf0-4c84-11d4-807a-00600811a9c3");

  public nsIHelperAppLauncherDialog(int paramInt)
  {
    super(paramInt);
  }

  public int Show(int paramInt1, int paramInt2)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramInt1, paramInt2);
  }

  public int PromptForSaveToFile(int paramInt, char[] paramArrayOfChar1, char[] paramArrayOfChar2, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress(), paramInt, paramArrayOfChar1, paramArrayOfChar2, paramArrayOfInt);
  }

  public int ShowProgressDialog(int paramInt1, int paramInt2)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 3, getAddress(), paramInt1, paramInt2);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIHelperAppLauncherDialog
 * JD-Core Version:    0.6.2
 */