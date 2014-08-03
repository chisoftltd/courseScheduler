package org.eclipse.swt.internal.mozilla;

public class nsIRequest extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 10;
  public static final String NS_IREQUEST_IID_STR = "ef6bfbd2-fd46-48d8-96b7-9f8f0fd387fe";
  public static final nsID NS_IREQUEST_IID = new nsID("ef6bfbd2-fd46-48d8-96b7-9f8f0fd387fe");
  public static final int LOAD_NORMAL = 0;
  public static final int LOAD_BACKGROUND = 1;
  public static final int INHIBIT_CACHING = 128;
  public static final int INHIBIT_PERSISTENT_CACHING = 256;
  public static final int LOAD_BYPASS_CACHE = 512;
  public static final int LOAD_FROM_CACHE = 1024;
  public static final int VALIDATE_ALWAYS = 2048;
  public static final int VALIDATE_NEVER = 4096;
  public static final int VALIDATE_ONCE_PER_SESSION = 8192;

  public nsIRequest(int paramInt)
  {
    super(paramInt);
  }

  public int GetName(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramInt);
  }

  public int IsPending(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress(), paramArrayOfInt);
  }

  public int GetStatus(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 3, getAddress(), paramArrayOfInt);
  }

  public int Cancel(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 4, getAddress(), paramInt);
  }

  public int Suspend()
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 5, getAddress());
  }

  public int Resume()
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 6, getAddress());
  }

  public int GetLoadGroup(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 7, getAddress(), paramArrayOfInt);
  }

  public int SetLoadGroup(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 8, getAddress(), paramInt);
  }

  public int GetLoadFlags(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 9, getAddress(), paramArrayOfInt);
  }

  public int SetLoadFlags(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 10, getAddress(), paramInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIRequest
 * JD-Core Version:    0.6.2
 */