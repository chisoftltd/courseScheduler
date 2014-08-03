package org.eclipse.swt.internal.mozilla;

public class nsIClassInfo extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 8;
  public static final String NS_ICLASSINFO_IID_STR = "986c11d0-f340-11d4-9075-0010a4e73d9a";
  public static final nsID NS_ICLASSINFO_IID = new nsID("986c11d0-f340-11d4-9075-0010a4e73d9a");
  public static final int SINGLETON = 1;
  public static final int THREADSAFE = 2;
  public static final int MAIN_THREAD_ONLY = 4;
  public static final int DOM_OBJECT = 8;
  public static final int PLUGIN_OBJECT = 16;
  public static final int EAGER_CLASSINFO = 32;
  public static final int CONTENT_NODE = 64;

  public nsIClassInfo(int paramInt)
  {
    super(paramInt);
  }

  public int GetInterfaces(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramArrayOfInt1, paramArrayOfInt2);
  }

  public int GetHelperForLanguage(int paramInt, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress(), paramInt, paramArrayOfInt);
  }

  public int GetContractID(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 3, getAddress(), paramArrayOfInt);
  }

  public int GetClassDescription(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 4, getAddress(), paramArrayOfInt);
  }

  public int GetClassID(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 5, getAddress(), paramInt);
  }

  public int GetImplementationLanguage(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 6, getAddress(), paramArrayOfInt);
  }

  public int GetFlags(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 7, getAddress(), paramArrayOfInt);
  }

  public int GetClassIDNoAlloc(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 8, getAddress(), paramInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIClassInfo
 * JD-Core Version:    0.6.2
 */