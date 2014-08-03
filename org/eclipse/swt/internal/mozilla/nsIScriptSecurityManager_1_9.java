package org.eclipse.swt.internal.mozilla;

public class nsIScriptSecurityManager_1_9 extends nsIXPCSecurityManager
{
  static final int LAST_METHOD_ID = nsIXPCSecurityManager.LAST_METHOD_ID + 26;
  public static final String NS_ISCRIPTSECURITYMANAGER_IID_STR = "3fffd8e8-3fea-442e-a0ed-2ba81ae197d5";
  public static final nsID NS_ISCRIPTSECURITYMANAGER_IID = new nsID("3fffd8e8-3fea-442e-a0ed-2ba81ae197d5");
  public static final int STANDARD = 0;
  public static final int LOAD_IS_AUTOMATIC_DOCUMENT_REPLACEMENT = 1;
  public static final int ALLOW_CHROME = 2;
  public static final int DISALLOW_INHERIT_PRINCIPAL = 4;
  public static final int DISALLOW_SCRIPT_OR_DATA = 4;
  public static final int DISALLOW_SCRIPT = 8;

  public nsIScriptSecurityManager_1_9(int paramInt)
  {
    super(paramInt);
  }

  public int CheckLoadURIFromScript(int paramInt1, int paramInt2)
  {
    return XPCOM.VtblCall(nsIXPCSecurityManager.LAST_METHOD_ID + 3, getAddress(), paramInt1, paramInt2);
  }

  public int CheckLoadURIWithPrincipal(int paramInt1, int paramInt2, int paramInt3)
  {
    return XPCOM.VtblCall(nsIXPCSecurityManager.LAST_METHOD_ID + 4, getAddress(), paramInt1, paramInt2, paramInt3);
  }

  public int CheckLoadURI(int paramInt1, int paramInt2, int paramInt3)
  {
    return XPCOM.VtblCall(nsIXPCSecurityManager.LAST_METHOD_ID + 5, getAddress(), paramInt1, paramInt2, paramInt3);
  }

  public int CheckLoadURIStrWithPrincipal(int paramInt1, int paramInt2, int paramInt3)
  {
    return XPCOM.VtblCall(nsIXPCSecurityManager.LAST_METHOD_ID + 6, getAddress(), paramInt1, paramInt2, paramInt3);
  }

  public int CheckLoadURIStr(int paramInt1, int paramInt2, int paramInt3)
  {
    return XPCOM.VtblCall(nsIXPCSecurityManager.LAST_METHOD_ID + 7, getAddress(), paramInt1, paramInt2, paramInt3);
  }

  public int CheckFunctionAccess(int paramInt1, int paramInt2, int paramInt3)
  {
    return XPCOM.VtblCall(nsIXPCSecurityManager.LAST_METHOD_ID + 8, getAddress(), paramInt1, paramInt2, paramInt3);
  }

  public int CanExecuteScripts(int paramInt1, int paramInt2, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsIXPCSecurityManager.LAST_METHOD_ID + 9, getAddress(), paramInt1, paramInt2, paramArrayOfInt);
  }

  public int GetSubjectPrincipal(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsIXPCSecurityManager.LAST_METHOD_ID + 10, getAddress(), paramArrayOfInt);
  }

  public int GetSystemPrincipal(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsIXPCSecurityManager.LAST_METHOD_ID + 11, getAddress(), paramArrayOfInt);
  }

  public int GetCodebasePrincipal(int paramInt, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsIXPCSecurityManager.LAST_METHOD_ID + 13, getAddress(), paramInt, paramArrayOfInt);
  }

  public int IsCapabilityEnabled(byte[] paramArrayOfByte, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsIXPCSecurityManager.LAST_METHOD_ID + 15, getAddress(), paramArrayOfByte, paramArrayOfInt);
  }

  public int EnableCapability(byte[] paramArrayOfByte)
  {
    return XPCOM.VtblCall(nsIXPCSecurityManager.LAST_METHOD_ID + 16, getAddress(), paramArrayOfByte);
  }

  public int RevertCapability(byte[] paramArrayOfByte)
  {
    return XPCOM.VtblCall(nsIXPCSecurityManager.LAST_METHOD_ID + 17, getAddress(), paramArrayOfByte);
  }

  public int DisableCapability(byte[] paramArrayOfByte)
  {
    return XPCOM.VtblCall(nsIXPCSecurityManager.LAST_METHOD_ID + 18, getAddress(), paramArrayOfByte);
  }

  public int GetObjectPrincipal(int paramInt1, int paramInt2, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsIXPCSecurityManager.LAST_METHOD_ID + 20, getAddress(), paramInt1, paramInt2, paramArrayOfInt);
  }

  public int SubjectPrincipalIsSystem(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsIXPCSecurityManager.LAST_METHOD_ID + 21, getAddress(), paramArrayOfInt);
  }

  public int CheckSameOrigin(int paramInt1, int paramInt2)
  {
    return XPCOM.VtblCall(nsIXPCSecurityManager.LAST_METHOD_ID + 22, getAddress(), paramInt1, paramInt2);
  }

  public int CheckSameOriginURI(int paramInt1, int paramInt2, int paramInt3)
  {
    return XPCOM.VtblCall(nsIXPCSecurityManager.LAST_METHOD_ID + 23, getAddress(), paramInt1, paramInt2, paramInt3);
  }

  public int GetPrincipalFromContext(int paramInt, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsIXPCSecurityManager.LAST_METHOD_ID + 24, getAddress(), paramInt, paramArrayOfInt);
  }

  public int GetChannelPrincipal(int paramInt, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsIXPCSecurityManager.LAST_METHOD_ID + 25, getAddress(), paramInt, paramArrayOfInt);
  }

  public int IsSystemPrincipal(int paramInt, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsIXPCSecurityManager.LAST_METHOD_ID + 26, getAddress(), paramInt, paramArrayOfInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIScriptSecurityManager_1_9
 * JD-Core Version:    0.6.2
 */