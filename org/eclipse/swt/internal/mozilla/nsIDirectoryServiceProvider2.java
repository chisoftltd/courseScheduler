package org.eclipse.swt.internal.mozilla;

public class nsIDirectoryServiceProvider2 extends nsIDirectoryServiceProvider
{
  static final int LAST_METHOD_ID = nsIDirectoryServiceProvider.LAST_METHOD_ID + 1;
  public static final String NS_IDIRECTORYSERVICEPROVIDER2_IID_STRING = "2f977d4b-5485-11d4-87e2-0010a4e75ef2";
  public static final nsID NS_IDIRECTORYSERVICEPROVIDER2_IID = new nsID("2f977d4b-5485-11d4-87e2-0010a4e75ef2");

  public nsIDirectoryServiceProvider2(int paramInt)
  {
    super(paramInt);
  }

  public int GetFiles(byte[] paramArrayOfByte, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsIDirectoryServiceProvider.LAST_METHOD_ID + 1, getAddress(), paramArrayOfByte, paramArrayOfInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIDirectoryServiceProvider2
 * JD-Core Version:    0.6.2
 */