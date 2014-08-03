package org.eclipse.swt.internal.mozilla;

public class nsISecurityCheckedComponent extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 4;
  public static final String NS_ISECURITYCHECKEDCOMPONENT_IID_STR = "0dad9e8c-a12d-4dcb-9a6f-7d09839356e1";
  public static final nsID NS_ISECURITYCHECKEDCOMPONENT_IID = new nsID("0dad9e8c-a12d-4dcb-9a6f-7d09839356e1");

  public nsISecurityCheckedComponent(int paramInt)
  {
    super(paramInt);
  }

  public int CanCreateWrapper(int paramInt, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramInt, paramArrayOfInt);
  }

  public int CanCallMethod(int paramInt, char[] paramArrayOfChar, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress(), paramInt, paramArrayOfChar, paramArrayOfInt);
  }

  public int CanGetProperty(int paramInt, char[] paramArrayOfChar, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 3, getAddress(), paramInt, paramArrayOfChar, paramArrayOfInt);
  }

  public int CanSetProperty(int paramInt, char[] paramArrayOfChar, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 4, getAddress(), paramInt, paramArrayOfChar, paramArrayOfInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsISecurityCheckedComponent
 * JD-Core Version:    0.6.2
 */