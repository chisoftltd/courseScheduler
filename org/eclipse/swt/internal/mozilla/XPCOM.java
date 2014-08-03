package org.eclipse.swt.internal.mozilla;

import org.eclipse.swt.internal.C;
import org.eclipse.swt.internal.Lock;

public class XPCOM extends C
{
  public static final String MOZILLA_FIVE_HOME = "MOZILLA_FIVE_HOME";
  public static final String MOZILLA_PLUGIN_PATH = "MOZ_PLUGIN_PATH";
  public static final String CONTENT_MAYBETEXT = "application/x-vnd.mozilla.maybe-text";
  public static final String CONTENT_MULTIPART = "multipart/x-mixed-replace";
  public static final String DOMEVENT_FOCUS = "focus";
  public static final String DOMEVENT_UNLOAD = "unload";
  public static final String DOMEVENT_MOUSEDOWN = "mousedown";
  public static final String DOMEVENT_MOUSEUP = "mouseup";
  public static final String DOMEVENT_MOUSEMOVE = "mousemove";
  public static final String DOMEVENT_MOUSEDRAG = "draggesture";
  public static final String DOMEVENT_MOUSEWHEEL = "DOMMouseScroll";
  public static final String DOMEVENT_MOUSEOVER = "mouseover";
  public static final String DOMEVENT_MOUSEOUT = "mouseout";
  public static final String DOMEVENT_KEYDOWN = "keydown";
  public static final String DOMEVENT_KEYPRESS = "keypress";
  public static final String DOMEVENT_KEYUP = "keyup";
  public static final nsID EXTERNAL_CID = new nsID("f2c59ad0-bd76-11dd-ad8b-0800200c9a66");
  public static final nsID NS_APPSHELL_CID = new nsID("2d96b3df-c051-11d1-a827-0040959a28c9");
  public static final nsID NS_CATEGORYMANAGER_CID = new nsID("16d222a6-1dd2-11b2-b693-f38b02c021b2");
  public static final nsID NS_DOWNLOAD_CID = new nsID("e3fa9D0a-1dd1-11b2-bdef-8c720b597445");
  public static final nsID NS_FILEPICKER_CID = new nsID("54ae32f8-1dd2-11b2-a209-df7c505370f8");
  public static final nsID NS_HELPERAPPLAUNCHERDIALOG_CID = new nsID("f68578eb-6ec2-4169-ae19-8c6243f0abe1");
  public static final nsID NS_INPUTSTREAMCHANNEL_CID = new nsID("6ddb050c-0d04-11d4-986e-00c04fa0cf4a");
  public static final nsID NS_IOSERVICE_CID = new nsID("9ac9e770-18bc-11d3-9337-00104ba0fd40");
  public static final nsID NS_LOADGROUP_CID = new nsID("e1c61582-2a84-11d3-8cce-0060b0fc14a3");
  public static final nsID NS_PROMPTSERVICE_CID = new nsID("a2112d6a-0e28-421f-b46a-25c0b308cbd0");
  public static final String EXTERNAL_CONTRACTID = "@eclipse.org/external;1";
  public static final String NS_CERTOVERRIDE_CONTRACTID = "@mozilla.org/security/certoverride;1";
  public static final String NS_CERTIFICATEDIALOGS_CONTRACTID = "@mozilla.org/nsCertificateDialogs;1";
  public static final String NS_CONTEXTSTACK_CONTRACTID = "@mozilla.org/js/xpc/ContextStack;1";
  public static final String NS_COOKIEMANAGER_CONTRACTID = "@mozilla.org/cookiemanager;1";
  public static final String NS_COOKIESERVICE_CONTRACTID = "@mozilla.org/cookieService;1";
  public static final String NS_DIRECTORYSERVICE_CONTRACTID = "@mozilla.org/file/directory_service;1";
  public static final String NS_DOMSERIALIZER_CONTRACTID = "@mozilla.org/xmlextras/xmlserializer;1";
  public static final String NS_DOWNLOAD_CONTRACTID = "@mozilla.org/download;1";
  public static final String NS_FILEPICKER_CONTRACTID = "@mozilla.org/filepicker;1";
  public static final String NS_HELPERAPPLAUNCHERDIALOG_CONTRACTID = "@mozilla.org/helperapplauncherdialog;1";
  public static final String NS_MEMORY_CONTRACTID = "@mozilla.org/xpcom/memory-service;1";
  public static final String NS_MIMEINPUTSTREAM_CONTRACTID = "@mozilla.org/network/mime-input-stream;1";
  public static final String NS_SCRIPTSECURITYMANAGER_CONTRACTID = "@mozilla.org/scriptsecuritymanager;1";
  public static final String NS_OBSERVER_CONTRACTID = "@mozilla.org/observer-service;1";
  public static final String NS_PREFLOCALIZEDSTRING_CONTRACTID = "@mozilla.org/pref-localizedstring;1";
  public static final String NS_PREFSERVICE_CONTRACTID = "@mozilla.org/preferences-service;1";
  public static final String NS_PROMPTSERVICE_CONTRACTID = "@mozilla.org/embedcomp/prompt-service;1";
  public static final String NS_TRANSFER_CONTRACTID = "@mozilla.org/transfer;1";
  public static final String NS_VARIANT_CONTRACTID = "@mozilla.org/variant;1";
  public static final String NS_WEBNAVIGATIONINFO_CONTRACTID = "@mozilla.org/webnavigation-info;1";
  public static final String NS_WINDOWWATCHER_CONTRACTID = "@mozilla.org/embedcomp/window-watcher;1";
  public static final String NS_APP_APPLICATION_REGISTRY_DIR = "AppRegD";
  public static final String NS_APP_CACHE_PARENT_DIR = "cachePDir";
  public static final String NS_APP_HISTORY_50_FILE = "UHist";
  public static final String NS_APP_LOCALSTORE_50_FILE = "LclSt";
  public static final String NS_APP_PLUGINS_DIR_LIST = "APluginsDL";
  public static final String NS_APP_PREF_DEFAULTS_50_DIR = "PrfDef";
  public static final String NS_APP_PREFS_50_DIR = "PrefD";
  public static final String NS_APP_PREFS_50_FILE = "PrefF";
  public static final String NS_APP_USER_CHROME_DIR = "UChrm";
  public static final String NS_APP_USER_MIMETYPES_50_FILE = "UMimTyp";
  public static final String NS_APP_USER_PROFILE_50_DIR = "ProfD";
  public static final String NS_GRE_COMPONENT_DIR = "GreComsD";
  public static final String NS_GRE_DIR = "GreD";
  public static final String NS_OS_CURRENT_PROCESS_DIR = "CurProcD";
  public static final String NS_OS_HOME_DIR = "Home";
  public static final String NS_OS_TEMP_DIR = "TmpD";
  public static final String NS_XPCOM_COMPONENT_DIR = "ComsD";
  public static final String NS_XPCOM_CURRENT_PROCESS_DIR = "XCurProcD";
  public static final String NS_XPCOM_INIT_CURRENT_PROCESS_DIR = "MozBinD";
  public static final int NS_OK = 0;
  public static final int NS_COMFALSE = 1;
  public static final int NS_BINDING_ABORTED = -2142568446;
  public static final int NS_ERROR_BASE = -1041039360;
  public static final int NS_ERROR_NOT_INITIALIZED = -1041039359;
  public static final int NS_ERROR_ALREADY_INITIALIZED = -1041039358;
  public static final int NS_ERROR_NOT_IMPLEMENTED = -2147467263;
  public static final int NS_NOINTERFACE = -2147467262;
  public static final int NS_ERROR_NO_INTERFACE = -2147467262;
  public static final int NS_ERROR_INVALID_POINTER = -2147467261;
  public static final int NS_ERROR_NULL_POINTER = -2147467261;
  public static final int NS_ERROR_ABORT = -2147467260;
  public static final int NS_ERROR_FAILURE = -2147467259;
  public static final int NS_ERROR_UNEXPECTED = -2147418113;
  public static final int NS_ERROR_OUT_OF_MEMORY = -2147024882;
  public static final int NS_ERROR_ILLEGAL_VALUE = -2147024809;
  public static final int NS_ERROR_INVALID_ARG = -2147024809;
  public static final int NS_ERROR_NO_AGGREGATION = -2147221232;
  public static final int NS_ERROR_NOT_AVAILABLE = -2147221231;
  public static final int NS_ERROR_FACTORY_NOT_REGISTERED = -2147221164;
  public static final int NS_ERROR_FACTORY_REGISTER_AGAIN = -2147221163;
  public static final int NS_ERROR_FACTORY_NOT_LOADED = -2147221000;
  public static final int NS_ERROR_FACTORY_NO_SIGNATURE_SUPPORT = -1041039103;
  public static final int NS_ERROR_FACTORY_EXISTS = -1041039104;
  public static final int NS_ERROR_HTMLPARSER_UNRESOLVEDDTD = -2142370829;
  public static final int NS_ERROR_FILE_NOT_FOUND = -2142109678;
  public static final int NS_ERROR_FILE_UNRECOGNIZED_PATH = -2142109695;

  public static final native int nsDynamicFunctionLoad_sizeof();

  public static final native void memmove(int paramInt1, nsDynamicFunctionLoad paramnsDynamicFunctionLoad, int paramInt2);

  public static final native void memmove(nsID paramnsID, int paramInt1, int paramInt2);

  public static final native void memmove(int paramInt1, nsID paramnsID, int paramInt2);

  public static final native int strlen_PRUnichar(int paramInt);

  public static final native int _JS_EvaluateUCScriptForPrincipals(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, int paramInt3, char[] paramArrayOfChar, int paramInt4, byte[] paramArrayOfByte2, int paramInt5, int[] paramArrayOfInt);

  public static final int JS_EvaluateUCScriptForPrincipals(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, int paramInt3, char[] paramArrayOfChar, int paramInt4, byte[] paramArrayOfByte2, int paramInt5, int[] paramArrayOfInt)
  {
    lock.lock();
    try
    {
      int i = _JS_EvaluateUCScriptForPrincipals(paramArrayOfByte1, paramInt1, paramInt2, paramInt3, paramArrayOfChar, paramInt4, paramArrayOfByte2, paramInt5, paramArrayOfInt);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  public static final native boolean _NS_Free(byte[] paramArrayOfByte, int paramInt);

  public static final boolean NS_Free(byte[] paramArrayOfByte, int paramInt)
  {
    lock.lock();
    try
    {
      boolean bool = _NS_Free(paramArrayOfByte, paramInt);
      return bool;
    }
    finally
    {
      lock.unlock();
    }
  }

  public static final native int _NS_GetComponentManager(int[] paramArrayOfInt);

  public static final int NS_GetComponentManager(int[] paramArrayOfInt)
  {
    lock.lock();
    try
    {
      int i = _NS_GetComponentManager(paramArrayOfInt);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  public static final native int _NS_GetServiceManager(int[] paramArrayOfInt);

  public static final int NS_GetServiceManager(int[] paramArrayOfInt)
  {
    lock.lock();
    try
    {
      int i = _NS_GetServiceManager(paramArrayOfInt);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  public static final native int _NS_InitXPCOM2(int paramInt1, int paramInt2, int paramInt3);

  public static final int NS_InitXPCOM2(int paramInt1, int paramInt2, int paramInt3)
  {
    lock.lock();
    try
    {
      int i = _NS_InitXPCOM2(paramInt1, paramInt2, paramInt3);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  public static final native int _NS_NewLocalFile(int paramInt1, int paramInt2, int[] paramArrayOfInt);

  public static final int NS_NewLocalFile(int paramInt1, int paramInt2, int[] paramArrayOfInt)
  {
    lock.lock();
    try
    {
      int i = _NS_NewLocalFile(paramInt1, paramInt2, paramArrayOfInt);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  public static final native int _nsEmbedCString_new();

  public static final int nsEmbedCString_new()
  {
    lock.lock();
    try
    {
      int i = _nsEmbedCString_new();
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  public static final native int _nsEmbedCString_new(byte[] paramArrayOfByte, int paramInt);

  public static final int nsEmbedCString_new(byte[] paramArrayOfByte, int paramInt)
  {
    lock.lock();
    try
    {
      int i = _nsEmbedCString_new(paramArrayOfByte, paramInt);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  public static final native int _nsEmbedCString_new(int paramInt1, int paramInt2);

  public static final int nsEmbedCString_new(int paramInt1, int paramInt2)
  {
    lock.lock();
    try
    {
      int i = _nsEmbedCString_new(paramInt1, paramInt2);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  public static final native void _nsEmbedCString_delete(int paramInt);

  // ERROR //
  public static final void nsEmbedCString_delete(int paramInt)
  {
    // Byte code:
    //   0: getstatic 302	org/eclipse/swt/internal/mozilla/XPCOM:lock	Lorg/eclipse/swt/internal/Lock;
    //   3: invokevirtual 306	org/eclipse/swt/internal/Lock:lock	()I
    //   6: pop
    //   7: iload_0
    //   8: invokestatic 352	org/eclipse/swt/internal/mozilla/XPCOM:_nsEmbedCString_delete	(I)V
    //   11: goto +18 -> 29
    //   14: astore_2
    //   15: jsr +5 -> 20
    //   18: aload_2
    //   19: athrow
    //   20: astore_1
    //   21: getstatic 302	org/eclipse/swt/internal/mozilla/XPCOM:lock	Lorg/eclipse/swt/internal/Lock;
    //   24: invokevirtual 312	org/eclipse/swt/internal/Lock:unlock	()V
    //   27: ret 1
    //   29: jsr -9 -> 20
    //   32: return
    //
    // Exception table:
    //   from	to	target	type
    //   7	14	14	finally
    //   29	32	14	finally
  }

  public static final native int _nsEmbedCString_Length(int paramInt);

  public static final int nsEmbedCString_Length(int paramInt)
  {
    lock.lock();
    try
    {
      int i = _nsEmbedCString_Length(paramInt);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  public static final native int _nsIScriptGlobalObject_EnsureScriptEnvironment(int paramInt1, int paramInt2);

  public static final int nsIScriptGlobalObject_EnsureScriptEnvironment(int paramInt1, int paramInt2)
  {
    lock.lock();
    try
    {
      int i = _nsIScriptGlobalObject_EnsureScriptEnvironment(paramInt1, paramInt2);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  public static final native int _nsIScriptGlobalObject_GetScriptGlobal(int paramInt1, int paramInt2);

  public static final int nsIScriptGlobalObject_GetScriptGlobal(int paramInt1, int paramInt2)
  {
    lock.lock();
    try
    {
      int i = _nsIScriptGlobalObject_GetScriptGlobal(paramInt1, paramInt2);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  public static final native int _nsIScriptGlobalObject_GetScriptContext(int paramInt1, int paramInt2);

  public static final int nsIScriptGlobalObject_GetScriptContext(int paramInt1, int paramInt2)
  {
    lock.lock();
    try
    {
      int i = _nsIScriptGlobalObject_GetScriptContext(paramInt1, paramInt2);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  public static final native int _nsIScriptContext_GetNativeContext(int paramInt);

  public static final int nsIScriptContext_GetNativeContext(int paramInt)
  {
    lock.lock();
    try
    {
      int i = _nsIScriptContext_GetNativeContext(paramInt);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  public static final native int _nsEmbedCString_get(int paramInt);

  public static final int nsEmbedCString_get(int paramInt)
  {
    lock.lock();
    try
    {
      int i = _nsEmbedCString_get(paramInt);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  public static final native void _nsID_delete(int paramInt);

  // ERROR //
  public static final void nsID_delete(int paramInt)
  {
    // Byte code:
    //   0: getstatic 302	org/eclipse/swt/internal/mozilla/XPCOM:lock	Lorg/eclipse/swt/internal/Lock;
    //   3: invokevirtual 306	org/eclipse/swt/internal/Lock:lock	()I
    //   6: pop
    //   7: iload_0
    //   8: invokestatic 380	org/eclipse/swt/internal/mozilla/XPCOM:_nsID_delete	(I)V
    //   11: goto +18 -> 29
    //   14: astore_2
    //   15: jsr +5 -> 20
    //   18: aload_2
    //   19: athrow
    //   20: astore_1
    //   21: getstatic 302	org/eclipse/swt/internal/mozilla/XPCOM:lock	Lorg/eclipse/swt/internal/Lock;
    //   24: invokevirtual 312	org/eclipse/swt/internal/Lock:unlock	()V
    //   27: ret 1
    //   29: jsr -9 -> 20
    //   32: return
    //
    // Exception table:
    //   from	to	target	type
    //   7	14	14	finally
    //   29	32	14	finally
  }

  public static final native int _nsID_new();

  public static final int nsID_new()
  {
    lock.lock();
    try
    {
      int i = _nsID_new();
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  public static final native int _nsID_Equals(int paramInt1, int paramInt2);

  public static final int nsID_Equals(int paramInt1, int paramInt2)
  {
    lock.lock();
    try
    {
      int i = _nsID_Equals(paramInt1, paramInt2);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  public static final native int _nsEmbedString_new();

  public static final int nsEmbedString_new()
  {
    lock.lock();
    try
    {
      int i = _nsEmbedString_new();
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  public static final native int _nsEmbedString_new(char[] paramArrayOfChar);

  public static final int nsEmbedString_new(char[] paramArrayOfChar)
  {
    lock.lock();
    try
    {
      int i = _nsEmbedString_new(paramArrayOfChar);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  public static final native void _nsEmbedString_delete(int paramInt);

  // ERROR //
  public static final void nsEmbedString_delete(int paramInt)
  {
    // Byte code:
    //   0: getstatic 302	org/eclipse/swt/internal/mozilla/XPCOM:lock	Lorg/eclipse/swt/internal/Lock;
    //   3: invokevirtual 306	org/eclipse/swt/internal/Lock:lock	()I
    //   6: pop
    //   7: iload_0
    //   8: invokestatic 399	org/eclipse/swt/internal/mozilla/XPCOM:_nsEmbedString_delete	(I)V
    //   11: goto +18 -> 29
    //   14: astore_2
    //   15: jsr +5 -> 20
    //   18: aload_2
    //   19: athrow
    //   20: astore_1
    //   21: getstatic 302	org/eclipse/swt/internal/mozilla/XPCOM:lock	Lorg/eclipse/swt/internal/Lock;
    //   24: invokevirtual 312	org/eclipse/swt/internal/Lock:unlock	()V
    //   27: ret 1
    //   29: jsr -9 -> 20
    //   32: return
    //
    // Exception table:
    //   from	to	target	type
    //   7	14	14	finally
    //   29	32	14	finally
  }

  public static final native int _nsEmbedString_Length(int paramInt);

  public static final int nsEmbedString_Length(int paramInt)
  {
    lock.lock();
    try
    {
      int i = _nsEmbedString_Length(paramInt);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  public static final native int _nsEmbedString_get(int paramInt);

  public static final int nsEmbedString_get(int paramInt)
  {
    lock.lock();
    try
    {
      int i = _nsEmbedString_get(paramInt);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  public static final native int _nsIMemory_Alloc(int paramInt1, int paramInt2);

  public static final int nsIMemory_Alloc(int paramInt1, int paramInt2)
  {
    lock.lock();
    try
    {
      int i = _nsIMemory_Alloc(paramInt1, paramInt2);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  public static final native int _nsIMemory_Realloc(int paramInt1, int paramInt2, int paramInt3);

  public static final int nsIMemory_Realloc(int paramInt1, int paramInt2, int paramInt3)
  {
    lock.lock();
    try
    {
      int i = _nsIMemory_Realloc(paramInt1, paramInt2, paramInt3);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  public static final native int _XPCOMGlueLoadXULFunctions(int paramInt);

  public static final int XPCOMGlueLoadXULFunctions(int paramInt)
  {
    lock.lock();
    try
    {
      int i = _XPCOMGlueLoadXULFunctions(paramInt);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  public static final native int _XPCOMGlueStartup(byte[] paramArrayOfByte);

  public static final int XPCOMGlueStartup(byte[] paramArrayOfByte)
  {
    lock.lock();
    try
    {
      int i = _XPCOMGlueStartup(paramArrayOfByte);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  public static final native int _XPCOMGlueShutdown();

  public static final int XPCOMGlueShutdown()
  {
    lock.lock();
    try
    {
      int i = _XPCOMGlueShutdown();
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  public static final native int _Call(int paramInt);

  public static final int Call(int paramInt)
  {
    lock.lock();
    try
    {
      int i = _Call(paramInt);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  public static final native int _Call(int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, int paramInt4, int paramInt5, int[] paramArrayOfInt);

  public static final int Call(int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, int paramInt4, int paramInt5, int[] paramArrayOfInt)
  {
    lock.lock();
    try
    {
      int i = _Call(paramInt1, paramInt2, paramInt3, paramArrayOfByte, paramInt4, paramInt5, paramArrayOfInt);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  public static final native int _Call(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);

  public static final int Call(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    lock.lock();
    try
    {
      int i = _Call(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2);

  static final int VtblCall(int paramInt1, int paramInt2)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, byte[] paramArrayOfByte);

  static final int VtblCall(int paramInt1, int paramInt2, byte[] paramArrayOfByte)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramArrayOfByte);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, char[] paramArrayOfChar);

  static final int VtblCall(int paramInt1, int paramInt2, char[] paramArrayOfChar)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramArrayOfChar);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, double paramDouble);

  static final int VtblCall(int paramInt1, int paramInt2, double paramDouble)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramDouble);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, float paramFloat);

  static final int VtblCall(int paramInt1, int paramInt2, float paramFloat)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramFloat);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, float[] paramArrayOfFloat);

  static final int VtblCall(int paramInt1, int paramInt2, float[] paramArrayOfFloat)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramArrayOfFloat);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int[] paramArrayOfInt);

  static final int VtblCall(int paramInt1, int paramInt2, int[] paramArrayOfInt)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramArrayOfInt);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long[] paramArrayOfLong);

  static final int VtblCall(int paramInt1, int paramInt2, long[] paramArrayOfLong)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramArrayOfLong);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, short[] paramArrayOfShort);

  static final int VtblCall(int paramInt1, int paramInt2, short[] paramArrayOfShort)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramArrayOfShort);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramInt4);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong, int paramInt3);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong, int paramInt3)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong, paramInt3);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong1, paramLong2);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramArrayOfInt);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong, long[] paramArrayOfLong);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong, long[] paramArrayOfLong)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong, paramArrayOfLong);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong, int[] paramArrayOfInt);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong, int[] paramArrayOfInt)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong, paramArrayOfInt);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, byte[] paramArrayOfByte, int[] paramArrayOfInt);

  static final int VtblCall(int paramInt1, int paramInt2, byte[] paramArrayOfByte, int[] paramArrayOfInt)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramArrayOfByte, paramArrayOfInt);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, byte[] paramArrayOfByte, long[] paramArrayOfLong);

  static final int VtblCall(int paramInt1, int paramInt2, byte[] paramArrayOfByte, long[] paramArrayOfLong)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramArrayOfByte, paramArrayOfLong);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, byte[] paramArrayOfByte, long paramLong);

  static final int VtblCall(int paramInt1, int paramInt2, byte[] paramArrayOfByte, long paramLong)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramArrayOfByte, paramLong);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3);

  static final int VtblCall(int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramArrayOfByte, paramInt3);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, nsID paramnsID);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, nsID paramnsID)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramnsID);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong, nsID paramnsID);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong, nsID paramnsID)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong, paramnsID);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, char[] paramArrayOfChar);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, char[] paramArrayOfChar)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramArrayOfChar);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int[] paramArrayOfInt1, int[] paramArrayOfInt2);

  static final int VtblCall(int paramInt1, int paramInt2, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramArrayOfInt1, paramArrayOfInt2);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int[] paramArrayOfInt, long[] paramArrayOfLong);

  static final int VtblCall(int paramInt1, int paramInt2, int[] paramArrayOfInt, long[] paramArrayOfLong)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramArrayOfInt, paramArrayOfLong);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, nsID paramnsID, int paramInt3);

  static final int VtblCall(int paramInt1, int paramInt2, nsID paramnsID, int paramInt3)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramnsID, paramInt3);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, nsID paramnsID, long paramLong);

  static final int VtblCall(int paramInt1, int paramInt2, nsID paramnsID, long paramLong)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramnsID, paramLong);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, nsID paramnsID, int[] paramArrayOfInt);

  static final int VtblCall(int paramInt1, int paramInt2, nsID paramnsID, int[] paramArrayOfInt)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramnsID, paramArrayOfInt);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, nsID paramnsID, long[] paramArrayOfLong);

  static final int VtblCall(int paramInt1, int paramInt2, nsID paramnsID, long[] paramArrayOfLong)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramnsID, paramArrayOfLong);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, char[] paramArrayOfChar1, char[] paramArrayOfChar2);

  static final int VtblCall(int paramInt1, int paramInt2, char[] paramArrayOfChar1, char[] paramArrayOfChar2)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramArrayOfChar1, paramArrayOfChar2);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);

  static final int VtblCall(int paramInt1, int paramInt2, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramArrayOfByte1, paramArrayOfByte2);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramArrayOfByte);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong, byte[] paramArrayOfByte);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong, byte[] paramArrayOfByte)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong, paramArrayOfByte);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, char[] paramArrayOfChar, int paramInt4);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, char[] paramArrayOfChar, int paramInt4)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramArrayOfChar, paramInt4);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, char[] paramArrayOfChar, int[] paramArrayOfInt);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, char[] paramArrayOfChar, int[] paramArrayOfInt)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramArrayOfChar, paramArrayOfInt);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong, char[] paramArrayOfChar, long[] paramArrayOfLong);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong, char[] paramArrayOfChar, long[] paramArrayOfLong)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong, paramArrayOfChar, paramArrayOfLong);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong, char[] paramArrayOfChar, int paramInt3);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong, char[] paramArrayOfChar, int paramInt3)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong, paramArrayOfChar, paramInt3);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3);

  static final int VtblCall(int paramInt1, int paramInt2, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramArrayOfInt1, paramArrayOfInt2, paramArrayOfInt3);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long[] paramArrayOfLong1, long[] paramArrayOfLong2);

  static final int VtblCall(int paramInt1, int paramInt2, long[] paramArrayOfLong1, long[] paramArrayOfLong2)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramArrayOfLong1, paramArrayOfLong2);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt, int paramInt4);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt, int paramInt4)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramArrayOfInt, paramInt4);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt, long paramLong);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt, long paramLong)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramArrayOfInt, paramLong);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long[] paramArrayOfLong1, long[] paramArrayOfLong2, long[] paramArrayOfLong3);

  static final int VtblCall(int paramInt1, int paramInt2, long[] paramArrayOfLong1, long[] paramArrayOfLong2, long[] paramArrayOfLong3)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramArrayOfLong1, paramArrayOfLong2, paramArrayOfLong3);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, short paramShort, int paramInt3, int paramInt4, int paramInt5);

  static final int VtblCall(int paramInt1, int paramInt2, short paramShort, int paramInt3, int paramInt4, int paramInt5)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramShort, paramInt3, paramInt4, paramInt5);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, short paramShort, long paramLong1, int paramInt3, long paramLong2);

  static final int VtblCall(int paramInt1, int paramInt2, short paramShort, long paramLong1, int paramInt3, long paramLong2)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramShort, paramLong1, paramInt3, paramLong2);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int[] paramArrayOfInt1, long[] paramArrayOfLong, int[] paramArrayOfInt2);

  static final int VtblCall(int paramInt1, int paramInt2, int[] paramArrayOfInt1, long[] paramArrayOfLong, int[] paramArrayOfInt2)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramArrayOfInt1, paramArrayOfLong, paramArrayOfInt2);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int[] paramArrayOfInt, long[] paramArrayOfLong1, long[] paramArrayOfLong2);

  static final int VtblCall(int paramInt1, int paramInt2, int[] paramArrayOfInt, long[] paramArrayOfLong1, long[] paramArrayOfLong2)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramArrayOfInt, paramArrayOfLong1, paramArrayOfLong2);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3, int[] paramArrayOfInt);

  static final int VtblCall(int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3, int[] paramArrayOfInt)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramArrayOfByte, paramInt3, paramArrayOfInt);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, byte[] paramArrayOfByte, long paramLong, int[] paramArrayOfInt);

  static final int VtblCall(int paramInt1, int paramInt2, byte[] paramArrayOfByte, long paramLong, int[] paramArrayOfInt)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramArrayOfByte, paramLong, paramArrayOfInt);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, byte[] paramArrayOfByte, nsID paramnsID, long[] paramArrayOfLong);

  static final int VtblCall(int paramInt1, int paramInt2, byte[] paramArrayOfByte, nsID paramnsID, long[] paramArrayOfLong)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramArrayOfByte, paramnsID, paramArrayOfLong);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, byte[] paramArrayOfByte, nsID paramnsID, int[] paramArrayOfInt);

  static final int VtblCall(int paramInt1, int paramInt2, byte[] paramArrayOfByte, nsID paramnsID, int[] paramArrayOfInt)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramArrayOfByte, paramnsID, paramArrayOfInt);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, char[] paramArrayOfChar, int paramInt3, int[] paramArrayOfInt);

  static final int VtblCall(int paramInt1, int paramInt2, char[] paramArrayOfChar, int paramInt3, int[] paramArrayOfInt)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramArrayOfChar, paramInt3, paramArrayOfInt);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, char[] paramArrayOfChar, long paramLong, long[] paramArrayOfLong);

  static final int VtblCall(int paramInt1, int paramInt2, char[] paramArrayOfChar, long paramLong, long[] paramArrayOfLong)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramArrayOfChar, paramLong, paramArrayOfLong);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, char[] paramArrayOfChar1, char[] paramArrayOfChar2);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, char[] paramArrayOfChar1, char[] paramArrayOfChar2)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramArrayOfChar1, paramArrayOfChar2);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong, char[] paramArrayOfChar1, char[] paramArrayOfChar2);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong, char[] paramArrayOfChar1, char[] paramArrayOfChar2)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong, paramArrayOfChar1, paramArrayOfChar2);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, long paramLong3);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, long paramLong3)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong1, paramLong2, paramLong3);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, int paramInt3);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, int paramInt3)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong1, paramLong2, paramInt3);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, long paramLong1, long paramLong2);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, long paramLong1, long paramLong2)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramLong1, paramLong2);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long[] paramArrayOfLong);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long[] paramArrayOfLong)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfLong);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfInt);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, long[] paramArrayOfLong);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, long[] paramArrayOfLong)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong1, paramLong2, paramArrayOfLong);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong, int paramInt3, long[] paramArrayOfLong);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong, int paramInt3, long[] paramArrayOfLong)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong, paramInt3, paramArrayOfLong);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, int[] paramArrayOfInt);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, int[] paramArrayOfInt)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong1, paramLong2, paramArrayOfInt);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, nsID paramnsID, int[] paramArrayOfInt);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, nsID paramnsID, int[] paramArrayOfInt)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramnsID, paramArrayOfInt);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong, nsID paramnsID, long[] paramArrayOfLong);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong, nsID paramnsID, long[] paramArrayOfLong)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong, paramnsID, paramArrayOfLong);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, char[] paramArrayOfChar);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, char[] paramArrayOfChar)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfChar);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, nsID paramnsID1, nsID paramnsID2, long[] paramArrayOfLong);

  static final int VtblCall(int paramInt1, int paramInt2, nsID paramnsID1, nsID paramnsID2, long[] paramArrayOfLong)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramnsID1, paramnsID2, paramArrayOfLong);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, nsID paramnsID1, nsID paramnsID2, int[] paramArrayOfInt);

  static final int VtblCall(int paramInt1, int paramInt2, nsID paramnsID1, nsID paramnsID2, int[] paramArrayOfInt)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramnsID1, paramnsID2, paramArrayOfInt);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int[] paramArrayOfInt);

  static final int VtblCall(int paramInt1, int paramInt2, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int[] paramArrayOfInt)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramArrayOfByte1, paramArrayOfByte2, paramArrayOfInt);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, long[] paramArrayOfLong);

  static final int VtblCall(int paramInt1, int paramInt2, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, long[] paramArrayOfLong)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramArrayOfByte1, paramArrayOfByte2, paramArrayOfLong);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, byte[] paramArrayOfByte, int[] paramArrayOfInt1, int[] paramArrayOfInt2);

  static final int VtblCall(int paramInt1, int paramInt2, byte[] paramArrayOfByte, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramArrayOfByte, paramArrayOfInt1, paramArrayOfInt2);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, byte[] paramArrayOfByte, int[] paramArrayOfInt, long[] paramArrayOfLong);

  static final int VtblCall(int paramInt1, int paramInt2, byte[] paramArrayOfByte, int[] paramArrayOfInt, long[] paramArrayOfLong)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramArrayOfByte, paramArrayOfInt, paramArrayOfLong);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, byte[] paramArrayOfByte, long[] paramArrayOfLong, int[] paramArrayOfInt);

  static final int VtblCall(int paramInt1, int paramInt2, byte[] paramArrayOfByte, long[] paramArrayOfLong, int[] paramArrayOfInt)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramArrayOfByte, paramArrayOfLong, paramArrayOfInt);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, byte[] paramArrayOfByte, nsID paramnsID, int paramInt3);

  static final int VtblCall(int paramInt1, int paramInt2, byte[] paramArrayOfByte, nsID paramnsID, int paramInt3)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramArrayOfByte, paramnsID, paramInt3);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, byte[] paramArrayOfByte, nsID paramnsID, long paramLong);

  static final int VtblCall(int paramInt1, int paramInt2, byte[] paramArrayOfByte, nsID paramnsID, long paramLong)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramArrayOfByte, paramnsID, paramLong);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, char[] paramArrayOfChar);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, char[] paramArrayOfChar)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramArrayOfByte, paramArrayOfChar);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong, byte[] paramArrayOfByte, char[] paramArrayOfChar);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong, byte[] paramArrayOfByte, char[] paramArrayOfChar)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong, paramArrayOfByte, paramArrayOfChar);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, byte[] paramArrayOfByte);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, byte[] paramArrayOfByte)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfByte);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, byte[] paramArrayOfByte);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, byte[] paramArrayOfByte)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong1, paramLong2, paramArrayOfByte);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt3);

  static final int VtblCall(int paramInt1, int paramInt2, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt3)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramArrayOfByte1, paramArrayOfByte2, paramInt3);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, int[] paramArrayOfInt);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, int[] paramArrayOfInt)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramArrayOfByte, paramArrayOfInt);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong1, byte[] paramArrayOfByte, long paramLong2);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong1, byte[] paramArrayOfByte, long paramLong2)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong1, paramArrayOfByte, paramLong2);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, int paramInt4);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, int paramInt4)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramArrayOfByte, paramInt4);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong, byte[] paramArrayOfByte, int paramInt3);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong, byte[] paramArrayOfByte, int paramInt3)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong, paramArrayOfByte, paramInt3);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, short paramShort);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, short paramShort)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramArrayOfByte, paramShort);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong, byte[] paramArrayOfByte, short paramShort);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong, byte[] paramArrayOfByte, short paramShort)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong, paramArrayOfByte, paramShort);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3, nsID paramnsID, int[] paramArrayOfInt);

  static final int VtblCall(int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3, nsID paramnsID, int[] paramArrayOfInt)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramArrayOfByte, paramInt3, paramnsID, paramArrayOfInt);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, byte[] paramArrayOfByte, long paramLong, nsID paramnsID, long[] paramArrayOfLong);

  static final int VtblCall(int paramInt1, int paramInt2, byte[] paramArrayOfByte, long paramLong, nsID paramnsID, long[] paramArrayOfLong)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramArrayOfByte, paramLong, paramnsID, paramArrayOfLong);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, int paramInt4, int[] paramArrayOfInt);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, int paramInt4, int[] paramArrayOfInt)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramArrayOfByte, paramInt4, paramArrayOfInt);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong1, byte[] paramArrayOfByte, long paramLong2, long[] paramArrayOfLong);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong1, byte[] paramArrayOfByte, long paramLong2, long[] paramArrayOfLong)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong1, paramArrayOfByte, paramLong2, paramArrayOfLong);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, char[] paramArrayOfChar1, char[] paramArrayOfChar2, int[] paramArrayOfInt);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, char[] paramArrayOfChar1, char[] paramArrayOfChar2, int[] paramArrayOfInt)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramArrayOfChar1, paramArrayOfChar2, paramArrayOfInt);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong, char[] paramArrayOfChar1, char[] paramArrayOfChar2, long[] paramArrayOfLong);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong, char[] paramArrayOfChar1, char[] paramArrayOfChar2, long[] paramArrayOfLong)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong, paramArrayOfChar1, paramArrayOfChar2, paramArrayOfLong);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong, char[] paramArrayOfChar1, char[] paramArrayOfChar2, int[] paramArrayOfInt);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong, char[] paramArrayOfChar1, char[] paramArrayOfChar2, int[] paramArrayOfInt)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong, paramArrayOfChar1, paramArrayOfChar2, paramArrayOfInt);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, char[] paramArrayOfChar);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, char[] paramArrayOfChar)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramArrayOfChar);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, int paramInt3, char[] paramArrayOfChar);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, int paramInt3, char[] paramArrayOfChar)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong1, paramLong2, paramInt3, paramArrayOfChar);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, int paramInt3, int paramInt4);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, int paramInt3, int paramInt4)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong1, paramLong2, paramInt3, paramInt4);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, long paramLong3, int paramInt3);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, long paramLong3, int paramInt3)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong1, paramLong2, paramLong3, paramInt3);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int[] paramArrayOfInt);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int[] paramArrayOfInt)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramArrayOfInt);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, long paramLong3, int[] paramArrayOfInt);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, long paramLong3, int[] paramArrayOfInt)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong1, paramLong2, paramLong3, paramArrayOfInt);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, long paramLong3, long[] paramArrayOfLong);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, long paramLong3, long[] paramArrayOfLong)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong1, paramLong2, paramLong3, paramArrayOfLong);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, int paramInt3, int[] paramArrayOfInt);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, int paramInt3, int[] paramArrayOfInt)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong1, paramLong2, paramInt3, paramArrayOfInt);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3, int[] paramArrayOfInt4);

  static final int VtblCall(int paramInt1, int paramInt2, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3, int[] paramArrayOfInt4)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramArrayOfInt1, paramArrayOfInt2, paramArrayOfInt3, paramArrayOfInt4);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramArrayOfInt1, paramArrayOfInt2, paramArrayOfInt3);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt1, int[] paramArrayOfInt2, long[] paramArrayOfLong);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt1, int[] paramArrayOfInt2, long[] paramArrayOfLong)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramArrayOfInt1, paramArrayOfInt2, paramArrayOfLong);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, nsID paramnsID, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt3);

  static final int VtblCall(int paramInt1, int paramInt2, nsID paramnsID, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt3)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramnsID, paramArrayOfByte1, paramArrayOfByte2, paramInt3);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, nsID paramnsID, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, long paramLong);

  static final int VtblCall(int paramInt1, int paramInt2, nsID paramnsID, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, long paramLong)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramnsID, paramArrayOfByte1, paramArrayOfByte2, paramLong);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, nsID paramnsID1, int paramInt3, nsID paramnsID2, int[] paramArrayOfInt);

  static final int VtblCall(int paramInt1, int paramInt2, nsID paramnsID1, int paramInt3, nsID paramnsID2, int[] paramArrayOfInt)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramnsID1, paramInt3, paramnsID2, paramArrayOfInt);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, nsID paramnsID1, long paramLong, nsID paramnsID2, long[] paramArrayOfLong);

  static final int VtblCall(int paramInt1, int paramInt2, nsID paramnsID1, long paramLong, nsID paramnsID2, long[] paramArrayOfLong)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramnsID1, paramLong, paramnsID2, paramArrayOfLong);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3, int[] paramArrayOfInt1, int[] paramArrayOfInt2);

  static final int VtblCall(int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramArrayOfByte, paramInt3, paramArrayOfInt1, paramArrayOfInt2);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3, long[] paramArrayOfLong, int[] paramArrayOfInt);

  static final int VtblCall(int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3, long[] paramArrayOfLong, int[] paramArrayOfInt)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramArrayOfByte, paramInt3, paramArrayOfLong, paramArrayOfInt);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfByte1, paramArrayOfByte2);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong1, paramLong2, paramArrayOfByte1, paramArrayOfByte2);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, byte[] paramArrayOfByte, int paramInt5);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, byte[] paramArrayOfByte, int paramInt5)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfByte, paramInt5);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, byte[] paramArrayOfByte, long paramLong3);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, byte[] paramArrayOfByte, long paramLong3)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong1, paramLong2, paramArrayOfByte, paramLong3);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, short[] paramArrayOfShort, int paramInt3, int[] paramArrayOfInt1, int[] paramArrayOfInt2);

  static final int VtblCall(int paramInt1, int paramInt2, short[] paramArrayOfShort, int paramInt3, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramArrayOfShort, paramInt3, paramArrayOfInt1, paramArrayOfInt2);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, short[] paramArrayOfShort, long paramLong, int[] paramArrayOfInt, long[] paramArrayOfLong);

  static final int VtblCall(int paramInt1, int paramInt2, short[] paramArrayOfShort, long paramLong, int[] paramArrayOfInt, long[] paramArrayOfLong)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramArrayOfShort, paramLong, paramArrayOfInt, paramArrayOfLong);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong, int paramInt3, int paramInt4, int[] paramArrayOfInt);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong, int paramInt3, int paramInt4, int[] paramArrayOfInt)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong, paramInt3, paramInt4, paramArrayOfInt);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt1, int[] paramArrayOfInt2);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfInt1, paramArrayOfInt2);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, int[] paramArrayOfInt1, int[] paramArrayOfInt2);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong1, paramLong2, paramArrayOfInt1, paramArrayOfInt2);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, char[] paramArrayOfChar, int paramInt3, int paramInt4, int paramInt5, int paramInt6);

  static final int VtblCall(int paramInt1, int paramInt2, char[] paramArrayOfChar, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramArrayOfChar, paramInt3, paramInt4, paramInt5, paramInt6);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, char[] paramArrayOfChar, int paramInt3, long paramLong1, long paramLong2, long paramLong3);

  static final int VtblCall(int paramInt1, int paramInt2, char[] paramArrayOfChar, int paramInt3, long paramLong1, long paramLong2, long paramLong3)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramArrayOfChar, paramInt3, paramLong1, paramLong2, paramLong3);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong1, paramLong2, paramLong3, paramLong4, paramLong5);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3, int[] paramArrayOfInt4);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3, int[] paramArrayOfInt4)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramArrayOfInt1, paramArrayOfInt2, paramArrayOfInt3, paramArrayOfInt4);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, char[] paramArrayOfChar1, char[] paramArrayOfChar2, char[] paramArrayOfChar3, int[] paramArrayOfInt);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, char[] paramArrayOfChar1, char[] paramArrayOfChar2, char[] paramArrayOfChar3, int[] paramArrayOfInt)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramArrayOfChar1, paramArrayOfChar2, paramArrayOfChar3, paramArrayOfInt);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong, char[] paramArrayOfChar1, char[] paramArrayOfChar2, char[] paramArrayOfChar3, int[] paramArrayOfInt);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong, char[] paramArrayOfChar1, char[] paramArrayOfChar2, char[] paramArrayOfChar3, int[] paramArrayOfInt)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong, paramArrayOfChar1, paramArrayOfChar2, paramArrayOfChar3, paramArrayOfInt);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3, int paramInt4, int[] paramArrayOfInt1, int[] paramArrayOfInt2);

  static final int VtblCall(int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3, int paramInt4, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramArrayOfByte, paramInt3, paramInt4, paramArrayOfInt1, paramArrayOfInt2);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3, long paramLong, long[] paramArrayOfLong, int[] paramArrayOfInt);

  static final int VtblCall(int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3, long paramLong, long[] paramArrayOfLong, int[] paramArrayOfInt)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramArrayOfByte, paramInt3, paramLong, paramArrayOfLong, paramArrayOfInt);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, byte[] paramArrayOfByte, int paramInt5, int paramInt6);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, byte[] paramArrayOfByte, int paramInt5, int paramInt6)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfByte, paramInt5, paramInt6);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, byte[] paramArrayOfByte, long paramLong3, int paramInt3);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, byte[] paramArrayOfByte, long paramLong3, int paramInt3)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong1, paramLong2, paramArrayOfByte, paramLong3, paramInt3);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, nsID paramnsID, int paramInt4, int paramInt5, int[] paramArrayOfInt);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, nsID paramnsID, int paramInt4, int paramInt5, int[] paramArrayOfInt)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramnsID, paramInt4, paramInt5, paramArrayOfInt);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong1, nsID paramnsID, long paramLong2, long paramLong3, long[] paramArrayOfLong);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong1, nsID paramnsID, long paramLong2, long paramLong3, long[] paramArrayOfLong)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong1, paramnsID, paramLong2, paramLong3, paramArrayOfLong);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong1, int paramInt3, long paramLong2, int paramInt4, int paramInt5);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong1, int paramInt3, long paramLong2, int paramInt4, int paramInt5)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong1, paramInt3, paramLong2, paramInt4, paramInt5);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, int paramInt4, int[] paramArrayOfInt);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, int paramInt4, int[] paramArrayOfInt)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramArrayOfByte1, paramArrayOfByte2, paramArrayOfByte3, paramInt4, paramArrayOfInt);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong1, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, long paramLong2, long[] paramArrayOfLong);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong1, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, long paramLong2, long[] paramArrayOfLong)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong1, paramArrayOfByte1, paramArrayOfByte2, paramArrayOfByte3, paramLong2, paramArrayOfLong);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, char[] paramArrayOfChar, int paramInt5, long paramLong, int paramInt6);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, char[] paramArrayOfChar, int paramInt5, long paramLong, int paramInt6)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfChar, paramInt5, paramLong, paramInt6);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, char[] paramArrayOfChar, int paramInt3, long paramLong3, int paramInt4);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, char[] paramArrayOfChar, int paramInt3, long paramLong3, int paramInt4)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong1, paramLong2, paramArrayOfChar, paramInt3, paramLong3, paramInt4);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, char[] paramArrayOfChar, long paramLong1, long paramLong2, long paramLong3);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, char[] paramArrayOfChar, long paramLong1, long paramLong2, long paramLong3)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfChar, paramLong1, paramLong2, paramLong3);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, char[] paramArrayOfChar, long paramLong3, long paramLong4, long paramLong5);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, char[] paramArrayOfChar, long paramLong3, long paramLong4, long paramLong5)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong1, paramLong2, paramArrayOfChar, paramLong3, paramLong4, paramLong5);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3, int[] paramArrayOfInt4, int[] paramArrayOfInt5, int[] paramArrayOfInt6);

  static final int VtblCall(int paramInt1, int paramInt2, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3, int[] paramArrayOfInt4, int[] paramArrayOfInt5, int[] paramArrayOfInt6)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramArrayOfInt1, paramArrayOfInt2, paramArrayOfInt3, paramArrayOfInt4, paramArrayOfInt5, paramArrayOfInt6);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long[] paramArrayOfLong1, long[] paramArrayOfLong2, long[] paramArrayOfLong3, long[] paramArrayOfLong4, long[] paramArrayOfLong5, int[] paramArrayOfInt);

  static final int VtblCall(int paramInt1, int paramInt2, long[] paramArrayOfLong1, long[] paramArrayOfLong2, long[] paramArrayOfLong3, long[] paramArrayOfLong4, long[] paramArrayOfLong5, int[] paramArrayOfInt)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramArrayOfLong1, paramArrayOfLong2, paramArrayOfLong3, paramArrayOfLong4, paramArrayOfLong5, paramArrayOfInt);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong1, paramLong2, paramInt3, paramInt4, paramInt5, paramInt6);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int[] paramArrayOfInt);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int[] paramArrayOfInt)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramArrayOfInt);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long[] paramArrayOfLong);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long[] paramArrayOfLong)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong1, paramLong2, paramLong3, paramLong4, paramLong5, paramArrayOfLong);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, nsID paramnsID, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt3, byte[] paramArrayOfByte3, byte[] paramArrayOfByte4);

  static final int VtblCall(int paramInt1, int paramInt2, nsID paramnsID, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt3, byte[] paramArrayOfByte3, byte[] paramArrayOfByte4)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramnsID, paramArrayOfByte1, paramArrayOfByte2, paramInt3, paramArrayOfByte3, paramArrayOfByte4);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, nsID paramnsID, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, long paramLong, byte[] paramArrayOfByte3, byte[] paramArrayOfByte4);

  static final int VtblCall(int paramInt1, int paramInt2, nsID paramnsID, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, long paramLong, byte[] paramArrayOfByte3, byte[] paramArrayOfByte4)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramnsID, paramArrayOfByte1, paramArrayOfByte2, paramLong, paramArrayOfByte3, paramArrayOfByte4);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong1, long paramLong2, long paramLong3, long paramLong4);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong1, long paramLong2, long paramLong3, long paramLong4)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramInt4, paramLong1, paramLong2, paramLong3, paramLong4);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong1, paramLong2, paramLong3, paramLong4, paramLong5, paramLong6);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, char[] paramArrayOfChar1, char[] paramArrayOfChar2, char[] paramArrayOfChar3, int[] paramArrayOfInt1, int[] paramArrayOfInt2);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, char[] paramArrayOfChar1, char[] paramArrayOfChar2, char[] paramArrayOfChar3, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramArrayOfChar1, paramArrayOfChar2, paramArrayOfChar3, paramArrayOfInt1, paramArrayOfInt2);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong, char[] paramArrayOfChar1, char[] paramArrayOfChar2, char[] paramArrayOfChar3, int[] paramArrayOfInt1, int[] paramArrayOfInt2);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong, char[] paramArrayOfChar1, char[] paramArrayOfChar2, char[] paramArrayOfChar3, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong, paramArrayOfChar1, paramArrayOfChar2, paramArrayOfChar3, paramArrayOfInt1, paramArrayOfInt2);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, int paramInt3, int paramInt4, int[] paramArrayOfInt);

  static final int VtblCall(int paramInt1, int paramInt2, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, int paramInt3, int paramInt4, int[] paramArrayOfInt)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramArrayOfByte1, paramArrayOfByte2, paramArrayOfByte3, paramInt3, paramInt4, paramArrayOfInt);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, int paramInt3, int paramInt4, long[] paramArrayOfLong);

  static final int VtblCall(int paramInt1, int paramInt2, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, int paramInt3, int paramInt4, long[] paramArrayOfLong)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramArrayOfByte1, paramArrayOfByte2, paramArrayOfByte3, paramInt3, paramInt4, paramArrayOfLong);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int[] paramArrayOfInt1, int[] paramArrayOfInt2);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramArrayOfInt1, paramArrayOfInt2);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong1, int paramInt3, int paramInt4, long paramLong2, int[] paramArrayOfInt, long[] paramArrayOfLong);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong1, int paramInt3, int paramInt4, long paramLong2, int[] paramArrayOfInt, long[] paramArrayOfLong)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong1, paramInt3, paramInt4, paramLong2, paramArrayOfInt, paramArrayOfLong);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, char[] paramArrayOfChar1, char[] paramArrayOfChar2, int paramInt5, int[] paramArrayOfInt);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, char[] paramArrayOfChar1, char[] paramArrayOfChar2, int paramInt5, int[] paramArrayOfInt)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfChar1, paramArrayOfChar2, paramInt5, paramArrayOfInt);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, char[] paramArrayOfChar1, char[] paramArrayOfChar2, int paramInt3, long[] paramArrayOfLong);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, char[] paramArrayOfChar1, char[] paramArrayOfChar2, int paramInt3, long[] paramArrayOfLong)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong1, paramLong2, paramArrayOfChar1, paramArrayOfChar2, paramInt3, paramArrayOfLong);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt6);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt6)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramArrayOfByte1, paramArrayOfByte2, paramInt6);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, long paramLong3, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, long paramLong4);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, long paramLong3, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, long paramLong4)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong1, paramLong2, paramLong3, paramArrayOfByte1, paramArrayOfByte2, paramLong4);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramArrayOfInt1, paramArrayOfInt2, paramArrayOfInt3);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong1, int paramInt3, long paramLong2, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong1, int paramInt3, long paramLong2, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong1, paramInt3, paramLong2, paramArrayOfInt1, paramArrayOfInt2, paramArrayOfInt3);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, char[] paramArrayOfChar1, char[] paramArrayOfChar2, int paramInt4, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, char[] paramArrayOfChar1, char[] paramArrayOfChar2, int paramInt4, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramArrayOfChar1, paramArrayOfChar2, paramInt4, paramArrayOfInt1, paramArrayOfInt2, paramArrayOfInt3);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong, char[] paramArrayOfChar1, char[] paramArrayOfChar2, int paramInt3, long[] paramArrayOfLong, int[] paramArrayOfInt1, int[] paramArrayOfInt2);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong, char[] paramArrayOfChar1, char[] paramArrayOfChar2, int paramInt3, long[] paramArrayOfLong, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong, paramArrayOfChar1, paramArrayOfChar2, paramInt3, paramArrayOfLong, paramArrayOfInt1, paramArrayOfInt2);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, char[] paramArrayOfChar1, char[] paramArrayOfChar2, int[] paramArrayOfInt1, char[] paramArrayOfChar3, int[] paramArrayOfInt2, int[] paramArrayOfInt3);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, char[] paramArrayOfChar1, char[] paramArrayOfChar2, int[] paramArrayOfInt1, char[] paramArrayOfChar3, int[] paramArrayOfInt2, int[] paramArrayOfInt3)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramArrayOfChar1, paramArrayOfChar2, paramArrayOfInt1, paramArrayOfChar3, paramArrayOfInt2, paramArrayOfInt3);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong, char[] paramArrayOfChar1, char[] paramArrayOfChar2, long[] paramArrayOfLong, char[] paramArrayOfChar3, int[] paramArrayOfInt1, int[] paramArrayOfInt2);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong, char[] paramArrayOfChar1, char[] paramArrayOfChar2, long[] paramArrayOfLong, char[] paramArrayOfChar3, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong, paramArrayOfChar1, paramArrayOfChar2, paramArrayOfLong, paramArrayOfChar3, paramArrayOfInt1, paramArrayOfInt2);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, char[] paramArrayOfChar, int[] paramArrayOfInt1, int[] paramArrayOfInt2);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, char[] paramArrayOfChar, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramArrayOfChar, paramArrayOfInt1, paramArrayOfInt2);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, int paramInt3, long paramLong3, char[] paramArrayOfChar, int[] paramArrayOfInt1, int[] paramArrayOfInt2);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, int paramInt3, long paramLong3, char[] paramArrayOfChar, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong1, paramLong2, paramInt3, paramLong3, paramArrayOfChar, paramArrayOfInt1, paramArrayOfInt2);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, long paramLong, int paramInt7, int paramInt8);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, long paramLong, int paramInt7, int paramInt8)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramLong, paramInt7, paramInt8);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong1, paramLong2, paramLong3, paramLong4, paramLong5, paramLong6, paramLong7);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramArrayOfInt1, paramArrayOfInt2, paramArrayOfInt3);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong1, int paramInt3, long paramLong2, long paramLong3, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong1, int paramInt3, long paramLong2, long paramLong3, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong1, paramInt3, paramLong2, paramLong3, paramArrayOfInt1, paramArrayOfInt2, paramArrayOfInt3);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, char[] paramArrayOfChar1, char[] paramArrayOfChar2, int[] paramArrayOfInt1, int[] paramArrayOfInt2, char[] paramArrayOfChar3, int[] paramArrayOfInt3, int[] paramArrayOfInt4);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, char[] paramArrayOfChar1, char[] paramArrayOfChar2, int[] paramArrayOfInt1, int[] paramArrayOfInt2, char[] paramArrayOfChar3, int[] paramArrayOfInt3, int[] paramArrayOfInt4)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramArrayOfChar1, paramArrayOfChar2, paramArrayOfInt1, paramArrayOfInt2, paramArrayOfChar3, paramArrayOfInt3, paramArrayOfInt4);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong, char[] paramArrayOfChar1, char[] paramArrayOfChar2, long[] paramArrayOfLong1, long[] paramArrayOfLong2, char[] paramArrayOfChar3, int[] paramArrayOfInt1, int[] paramArrayOfInt2);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong, char[] paramArrayOfChar1, char[] paramArrayOfChar2, long[] paramArrayOfLong1, long[] paramArrayOfLong2, char[] paramArrayOfChar3, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong, paramArrayOfChar1, paramArrayOfChar2, paramArrayOfLong1, paramArrayOfLong2, paramArrayOfChar3, paramArrayOfInt1, paramArrayOfInt2);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int[] paramArrayOfInt);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int[] paramArrayOfInt)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramInt9, paramArrayOfInt);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long[] paramArrayOfLong);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long[] paramArrayOfLong)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramLong1, paramLong2, paramLong3, paramLong4, paramLong5, paramLong6, paramArrayOfLong);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, char[] paramArrayOfChar, int[] paramArrayOfInt1, int[] paramArrayOfInt2);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, char[] paramArrayOfChar, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramArrayOfChar, paramArrayOfInt1, paramArrayOfInt2);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, long paramLong3, long paramLong4, int paramInt3, long paramLong5, char[] paramArrayOfChar, int[] paramArrayOfInt, long[] paramArrayOfLong);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, long paramLong3, long paramLong4, int paramInt3, long paramLong5, char[] paramArrayOfChar, int[] paramArrayOfInt, long[] paramArrayOfLong)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong1, paramLong2, paramLong3, paramLong4, paramInt3, paramLong5, paramArrayOfChar, paramArrayOfInt, paramArrayOfLong);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11, int paramInt12);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11, int paramInt12)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramInt9, paramInt10, paramInt11, paramInt12);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong1, int paramInt3, int paramInt4, long paramLong2, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong1, int paramInt3, int paramInt4, long paramLong2, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong1, paramInt3, paramInt4, paramLong2, paramInt5, paramInt6, paramInt7, paramInt8, paramInt9, paramInt10);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, char[] paramArrayOfChar1, char[] paramArrayOfChar2, int paramInt4, char[] paramArrayOfChar3, char[] paramArrayOfChar4, char[] paramArrayOfChar5, char[] paramArrayOfChar6, int[] paramArrayOfInt1, int[] paramArrayOfInt2);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, char[] paramArrayOfChar1, char[] paramArrayOfChar2, int paramInt4, char[] paramArrayOfChar3, char[] paramArrayOfChar4, char[] paramArrayOfChar5, char[] paramArrayOfChar6, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramArrayOfChar1, paramArrayOfChar2, paramInt4, paramArrayOfChar3, paramArrayOfChar4, paramArrayOfChar5, paramArrayOfChar6, paramArrayOfInt1, paramArrayOfInt2);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong, char[] paramArrayOfChar1, char[] paramArrayOfChar2, int paramInt3, char[] paramArrayOfChar3, char[] paramArrayOfChar4, char[] paramArrayOfChar5, char[] paramArrayOfChar6, int[] paramArrayOfInt1, int[] paramArrayOfInt2);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong, char[] paramArrayOfChar1, char[] paramArrayOfChar2, int paramInt3, char[] paramArrayOfChar3, char[] paramArrayOfChar4, char[] paramArrayOfChar5, char[] paramArrayOfChar6, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong, paramArrayOfChar1, paramArrayOfChar2, paramInt3, paramArrayOfChar3, paramArrayOfChar4, paramArrayOfChar5, paramArrayOfChar6, paramArrayOfInt1, paramArrayOfInt2);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, char[] paramArrayOfChar, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11, int[] paramArrayOfInt1, int[] paramArrayOfInt2);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, char[] paramArrayOfChar, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramArrayOfChar, paramInt7, paramInt8, paramInt9, paramInt10, paramInt11, paramArrayOfInt1, paramArrayOfInt2);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, long paramLong3, int paramInt3, char[] paramArrayOfChar, long paramLong4, long paramLong5, int paramInt4, long paramLong6, int paramInt5, long[] paramArrayOfLong1, long[] paramArrayOfLong2);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, long paramLong3, int paramInt3, char[] paramArrayOfChar, long paramLong4, long paramLong5, int paramInt4, long paramLong6, int paramInt5, long[] paramArrayOfLong1, long[] paramArrayOfLong2)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong1, paramLong2, paramLong3, paramInt3, paramArrayOfChar, paramLong4, paramLong5, paramInt4, paramLong6, paramInt5, paramArrayOfLong1, paramArrayOfLong2);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, char[] paramArrayOfChar, byte[] paramArrayOfByte, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11, int[] paramArrayOfInt1, int[] paramArrayOfInt2);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, char[] paramArrayOfChar, byte[] paramArrayOfByte, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramArrayOfChar, paramArrayOfByte, paramInt7, paramInt8, paramInt9, paramInt10, paramInt11, paramArrayOfInt1, paramArrayOfInt2);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, long paramLong3, int paramInt3, char[] paramArrayOfChar, byte[] paramArrayOfByte, long paramLong4, long paramLong5, int paramInt4, long paramLong6, int paramInt5, long[] paramArrayOfLong1, long[] paramArrayOfLong2);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, long paramLong3, int paramInt3, char[] paramArrayOfChar, byte[] paramArrayOfByte, long paramLong4, long paramLong5, int paramInt4, long paramLong6, int paramInt5, long[] paramArrayOfLong1, long[] paramArrayOfLong2)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong1, paramLong2, paramLong3, paramInt3, paramArrayOfChar, paramArrayOfByte, paramLong4, paramLong5, paramInt4, paramLong6, paramInt5, paramArrayOfLong1, paramArrayOfLong2);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11, int paramInt12, int paramInt13, int paramInt14, int paramInt15, short paramShort, int paramInt16);

  static final int VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11, int paramInt12, int paramInt13, int paramInt14, int paramInt15, short paramShort, int paramInt16)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramInt9, paramInt10, paramInt11, paramInt12, paramInt13, paramInt14, paramInt15, paramShort, paramInt16);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  static final native int _VtblCall(int paramInt1, int paramInt2, long paramLong1, int paramInt3, int paramInt4, long paramLong2, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11, int paramInt12, int paramInt13, short paramShort, long paramLong3);

  static final int VtblCall(int paramInt1, int paramInt2, long paramLong1, int paramInt3, int paramInt4, long paramLong2, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11, int paramInt12, int paramInt13, short paramShort, long paramLong3)
  {
    lock.lock();
    try
    {
      int i = _VtblCall(paramInt1, paramInt2, paramLong1, paramInt3, paramInt4, paramLong2, paramInt5, paramInt6, paramInt7, paramInt8, paramInt9, paramInt10, paramInt11, paramInt12, paramInt13, paramShort, paramLong3);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.XPCOM
 * JD-Core Version:    0.6.2
 */