package org.eclipse.swt.internal.mozilla;

public class nsIPromptService2 extends nsIPromptService
{
  static final int LAST_METHOD_ID = nsIPromptService.LAST_METHOD_ID + 2;
  public static final String NS_IPROMPTSERVICE2_IID_STR = "cf86d196-dbee-4482-9dfa-3477aa128319";
  public static final nsID NS_IPROMPTSERVICE2_IID = new nsID("cf86d196-dbee-4482-9dfa-3477aa128319");

  public nsIPromptService2(int paramInt)
  {
    super(paramInt);
  }

  public int PromptAuth(int paramInt1, int paramInt2, int paramInt3, int paramInt4, char[] paramArrayOfChar, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    return XPCOM.VtblCall(nsIPromptService.LAST_METHOD_ID + 1, getAddress(), paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfChar, paramArrayOfInt1, paramArrayOfInt2);
  }

  public int AsyncPromptAuth(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, char[] paramArrayOfChar, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    return XPCOM.VtblCall(nsIPromptService.LAST_METHOD_ID + 2, getAddress(), paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramArrayOfChar, paramArrayOfInt1, paramArrayOfInt2);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIPromptService2
 * JD-Core Version:    0.6.2
 */