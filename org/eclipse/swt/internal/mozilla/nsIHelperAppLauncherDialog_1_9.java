package org.eclipse.swt.internal.mozilla;

public class nsIHelperAppLauncherDialog_1_9 extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 2;
  public static final String NS_IHELPERAPPLAUNCHERDIALOG_IID_STR = "f3704fdc-8ae6-4eba-a3c3-f02958ac0649";
  public static final nsID NS_IHELPERAPPLAUNCHERDIALOG_IID = new nsID("f3704fdc-8ae6-4eba-a3c3-f02958ac0649");
  public static final int REASON_CANTHANDLE = 0;
  public static final int REASON_SERVERREQUEST = 1;
  public static final int REASON_TYPESNIFFED = 2;

  public nsIHelperAppLauncherDialog_1_9(int paramInt)
  {
    super(paramInt);
  }

  public int Show(int paramInt1, int paramInt2, int paramInt3)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramInt1, paramInt2, paramInt3);
  }

  public int PromptForSaveToFile(int paramInt1, int paramInt2, char[] paramArrayOfChar1, char[] paramArrayOfChar2, int paramInt3, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress(), paramInt1, paramInt2, paramArrayOfChar1, paramArrayOfChar2, paramInt3, paramArrayOfInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIHelperAppLauncherDialog_1_9
 * JD-Core Version:    0.6.2
 */