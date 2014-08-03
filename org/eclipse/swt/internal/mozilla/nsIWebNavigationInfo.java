package org.eclipse.swt.internal.mozilla;

public class nsIWebNavigationInfo extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 1;
  public static final String NS_IWEBNAVIGATIONINFO_IID_STR = "62a93afb-93a1-465c-84c8-0432264229de";
  public static final nsID NS_IWEBNAVIGATIONINFO_IID = new nsID("62a93afb-93a1-465c-84c8-0432264229de");
  public static final int UNSUPPORTED = 0;
  public static final int IMAGE = 1;
  public static final int PLUGIN = 2;
  public static final int OTHER = 32768;

  public nsIWebNavigationInfo(int paramInt)
  {
    super(paramInt);
  }

  public int IsTypeSupported(int paramInt1, int paramInt2, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramInt1, paramInt2, paramArrayOfInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIWebNavigationInfo
 * JD-Core Version:    0.6.2
 */