package org.eclipse.swt.internal.mozilla;

public class nsIWindowWatcher extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 11;
  public static final String NS_IWINDOWWATCHER_IID_STR = "002286a8-494b-43b3-8ddd-49e3fc50622b";
  public static final nsID NS_IWINDOWWATCHER_IID = new nsID("002286a8-494b-43b3-8ddd-49e3fc50622b");

  public nsIWindowWatcher(int paramInt)
  {
    super(paramInt);
  }

  public int OpenWindow(int paramInt1, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, int paramInt2, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramInt1, paramArrayOfByte1, paramArrayOfByte2, paramArrayOfByte3, paramInt2, paramArrayOfInt);
  }

  public int RegisterNotification(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress(), paramInt);
  }

  public int UnregisterNotification(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 3, getAddress(), paramInt);
  }

  public int GetWindowEnumerator(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 4, getAddress(), paramArrayOfInt);
  }

  public int GetNewPrompter(int paramInt, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 5, getAddress(), paramInt, paramArrayOfInt);
  }

  public int GetNewAuthPrompter(int paramInt, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 6, getAddress(), paramInt, paramArrayOfInt);
  }

  public int SetWindowCreator(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 7, getAddress(), paramInt);
  }

  public int GetChromeForWindow(int paramInt, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 8, getAddress(), paramInt, paramArrayOfInt);
  }

  public int GetWindowByName(char[] paramArrayOfChar, int paramInt, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 9, getAddress(), paramArrayOfChar, paramInt, paramArrayOfInt);
  }

  public int GetActiveWindow(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 10, getAddress(), paramArrayOfInt);
  }

  public int SetActiveWindow(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 11, getAddress(), paramInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIWindowWatcher
 * JD-Core Version:    0.6.2
 */