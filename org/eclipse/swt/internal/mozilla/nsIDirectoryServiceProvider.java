package org.eclipse.swt.internal.mozilla;

public class nsIDirectoryServiceProvider extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 1;
  public static final String NS_IDIRECTORYSERVICEPROVIDER_IID_STR = "bbf8cab0-d43a-11d3-8cc2-00609792278c";
  public static final nsID NS_IDIRECTORYSERVICEPROVIDER_IID = new nsID("bbf8cab0-d43a-11d3-8cc2-00609792278c");

  public nsIDirectoryServiceProvider(int paramInt)
  {
    super(paramInt);
  }

  public int GetFile(byte[] paramArrayOfByte, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramArrayOfByte, paramArrayOfInt1, paramArrayOfInt2);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIDirectoryServiceProvider
 * JD-Core Version:    0.6.2
 */