package org.eclipse.swt.internal.mozilla;

public class nsIXPCSecurityManager extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 4;
  public static final String NS_IXPCSECURITYMANAGER_IID_STR = "31431440-f1ce-11d2-985a-006008962422";
  public static final nsID NS_IXPCSECURITYMANAGER_IID = new nsID("31431440-f1ce-11d2-985a-006008962422");
  public static final int HOOK_CREATE_WRAPPER = 1;
  public static final int HOOK_CREATE_INSTANCE = 2;
  public static final int HOOK_GET_SERVICE = 4;
  public static final int HOOK_CALL_METHOD = 8;
  public static final int HOOK_GET_PROPERTY = 16;
  public static final int HOOK_SET_PROPERTY = 32;
  public static final int HOOK_ALL = 63;
  public static final int ACCESS_CALL_METHOD = 0;
  public static final int ACCESS_GET_PROPERTY = 1;
  public static final int ACCESS_SET_PROPERTY = 2;

  public nsIXPCSecurityManager(int paramInt)
  {
    super(paramInt);
  }

  public int CanCreateWrapper(int paramInt1, nsID paramnsID, int paramInt2, int paramInt3, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramInt1, paramnsID, paramInt2, paramInt3, paramArrayOfInt);
  }

  public int CanCreateInstance(int paramInt, nsID paramnsID)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress(), paramInt, paramnsID);
  }

  public int CanGetService(int paramInt, nsID paramnsID)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 3, getAddress(), paramInt, paramnsID);
  }

  public int CanAccess(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 4, getAddress(), paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramArrayOfInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIXPCSecurityManager
 * JD-Core Version:    0.6.2
 */