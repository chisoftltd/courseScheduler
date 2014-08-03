package org.eclipse.swt.internal.mozilla;

public class nsIDirectoryService extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 3;
  public static final String NS_IDIRECTORYSERVICE_IID_STR = "57a66a60-d43a-11d3-8cc2-00609792278c";
  public static final nsID NS_IDIRECTORYSERVICE_IID = new nsID("57a66a60-d43a-11d3-8cc2-00609792278c");

  public nsIDirectoryService(int paramInt)
  {
    super(paramInt);
  }

  public int Init()
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress());
  }

  public int RegisterProvider(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress(), paramInt);
  }

  public int UnregisterProvider(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 3, getAddress(), paramInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIDirectoryService
 * JD-Core Version:    0.6.2
 */