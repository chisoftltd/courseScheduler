package org.eclipse.swt.internal.mozilla;

public class nsICancelable extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 1;
  public static final String NS_ICANCELABLE_IID_STR = "d94ac0a0-bb18-46b8-844e-84159064b0bd";
  public static final nsID NS_ICANCELABLE_IID = new nsID("d94ac0a0-bb18-46b8-844e-84159064b0bd");

  public nsICancelable(int paramInt)
  {
    super(paramInt);
  }

  public int Cancel(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsICancelable
 * JD-Core Version:    0.6.2
 */