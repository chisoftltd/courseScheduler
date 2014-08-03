package org.eclipse.swt.internal.mozilla;

public class nsIAppShell extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 8;
  public static final String NS_IAPPSHELL_IID_STR = "a0757c31-eeac-11d1-9ec1-00aa002fb821";
  public static final nsID NS_IAPPSHELL_IID = new nsID("a0757c31-eeac-11d1-9ec1-00aa002fb821");

  public nsIAppShell(int paramInt)
  {
    super(paramInt);
  }

  public int Create(int paramInt, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramInt, paramArrayOfInt);
  }

  public int Run()
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress());
  }

  public int Spinup()
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 3, getAddress());
  }

  public int Spindown()
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 4, getAddress());
  }

  public int ListenToEventQueue(int paramInt1, int paramInt2)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 5, getAddress(), paramInt1, paramInt2);
  }

  public int GetNativeEvent(int paramInt, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 6, getAddress(), paramInt, paramArrayOfInt);
  }

  public int DispatchNativeEvent(int paramInt1, int paramInt2)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 7, getAddress(), paramInt1, paramInt2);
  }

  public int Exit()
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 8, getAddress());
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIAppShell
 * JD-Core Version:    0.6.2
 */