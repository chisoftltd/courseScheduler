package org.eclipse.swt.internal.mozilla;

public class nsIPromptService extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 9;
  public static final String NS_IPROMPTSERVICE_IID_STR = "1630c61a-325e-49ca-8759-a31b16c47aa5";
  public static final nsID NS_IPROMPTSERVICE_IID = new nsID("1630c61a-325e-49ca-8759-a31b16c47aa5");
  public static final int BUTTON_POS_0 = 1;
  public static final int BUTTON_POS_1 = 256;
  public static final int BUTTON_POS_2 = 65536;
  public static final int BUTTON_TITLE_OK = 1;
  public static final int BUTTON_TITLE_CANCEL = 2;
  public static final int BUTTON_TITLE_YES = 3;
  public static final int BUTTON_TITLE_NO = 4;
  public static final int BUTTON_TITLE_SAVE = 5;
  public static final int BUTTON_TITLE_DONT_SAVE = 6;
  public static final int BUTTON_TITLE_REVERT = 7;
  public static final int BUTTON_TITLE_IS_STRING = 127;
  public static final int BUTTON_POS_0_DEFAULT = 0;
  public static final int BUTTON_POS_1_DEFAULT = 16777216;
  public static final int BUTTON_POS_2_DEFAULT = 33554432;
  public static final int BUTTON_DELAY_ENABLE = 67108864;
  public static final int STD_OK_CANCEL_BUTTONS = 513;
  public static final int STD_YES_NO_BUTTONS = 1027;

  public nsIPromptService(int paramInt)
  {
    super(paramInt);
  }

  public int Alert(int paramInt, char[] paramArrayOfChar1, char[] paramArrayOfChar2)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramInt, paramArrayOfChar1, paramArrayOfChar2);
  }

  public int AlertCheck(int paramInt, char[] paramArrayOfChar1, char[] paramArrayOfChar2, char[] paramArrayOfChar3, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress(), paramInt, paramArrayOfChar1, paramArrayOfChar2, paramArrayOfChar3, paramArrayOfInt);
  }

  public int Confirm(int paramInt, char[] paramArrayOfChar1, char[] paramArrayOfChar2, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 3, getAddress(), paramInt, paramArrayOfChar1, paramArrayOfChar2, paramArrayOfInt);
  }

  public int ConfirmCheck(int paramInt, char[] paramArrayOfChar1, char[] paramArrayOfChar2, char[] paramArrayOfChar3, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 4, getAddress(), paramInt, paramArrayOfChar1, paramArrayOfChar2, paramArrayOfChar3, paramArrayOfInt1, paramArrayOfInt2);
  }

  public int ConfirmEx(int paramInt1, char[] paramArrayOfChar1, char[] paramArrayOfChar2, int paramInt2, char[] paramArrayOfChar3, char[] paramArrayOfChar4, char[] paramArrayOfChar5, char[] paramArrayOfChar6, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 5, getAddress(), paramInt1, paramArrayOfChar1, paramArrayOfChar2, paramInt2, paramArrayOfChar3, paramArrayOfChar4, paramArrayOfChar5, paramArrayOfChar6, paramArrayOfInt1, paramArrayOfInt2);
  }

  public int Prompt(int paramInt, char[] paramArrayOfChar1, char[] paramArrayOfChar2, int[] paramArrayOfInt1, char[] paramArrayOfChar3, int[] paramArrayOfInt2, int[] paramArrayOfInt3)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 6, getAddress(), paramInt, paramArrayOfChar1, paramArrayOfChar2, paramArrayOfInt1, paramArrayOfChar3, paramArrayOfInt2, paramArrayOfInt3);
  }

  public int PromptUsernameAndPassword(int paramInt, char[] paramArrayOfChar1, char[] paramArrayOfChar2, int[] paramArrayOfInt1, int[] paramArrayOfInt2, char[] paramArrayOfChar3, int[] paramArrayOfInt3, int[] paramArrayOfInt4)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 7, getAddress(), paramInt, paramArrayOfChar1, paramArrayOfChar2, paramArrayOfInt1, paramArrayOfInt2, paramArrayOfChar3, paramArrayOfInt3, paramArrayOfInt4);
  }

  public int PromptPassword(int paramInt, char[] paramArrayOfChar1, char[] paramArrayOfChar2, int[] paramArrayOfInt1, char[] paramArrayOfChar3, int[] paramArrayOfInt2, int[] paramArrayOfInt3)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 8, getAddress(), paramInt, paramArrayOfChar1, paramArrayOfChar2, paramArrayOfInt1, paramArrayOfChar3, paramArrayOfInt2, paramArrayOfInt3);
  }

  public int Select(int paramInt1, char[] paramArrayOfChar1, char[] paramArrayOfChar2, int paramInt2, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 9, getAddress(), paramInt1, paramArrayOfChar1, paramArrayOfChar2, paramInt2, paramArrayOfInt1, paramArrayOfInt2, paramArrayOfInt3);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIPromptService
 * JD-Core Version:    0.6.2
 */