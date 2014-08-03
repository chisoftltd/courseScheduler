package org.eclipse.swt.internal.mozilla;

public class nsIPrefLocalizedString extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 4;
  public static final String NS_IPREFLOCALIZEDSTRING_IID_STR = "ae419e24-1dd1-11b2-b39a-d3e5e7073802";
  public static final nsID NS_IPREFLOCALIZEDSTRING_IID = new nsID("ae419e24-1dd1-11b2-b39a-d3e5e7073802");

  public nsIPrefLocalizedString(int paramInt)
  {
    super(paramInt);
  }

  public int GetData(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramArrayOfInt);
  }

  public int SetData(char[] paramArrayOfChar)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress(), paramArrayOfChar);
  }

  public int ToString(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 3, getAddress(), paramArrayOfInt);
  }

  public int SetDataWithLength(int paramInt, char[] paramArrayOfChar)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 4, getAddress(), paramInt, paramArrayOfChar);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIPrefLocalizedString
 * JD-Core Version:    0.6.2
 */