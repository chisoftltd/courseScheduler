package org.eclipse.swt.internal.mozilla;

public class nsIWebBrowserStream extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 3;
  public static final String NS_IWEBBROWSERSTREAM_IID_STR = "86d02f0e-219b-4cfc-9c88-bd98d2cce0b8";
  public static final nsID NS_IWEBBROWSERSTREAM_IID = new nsID("86d02f0e-219b-4cfc-9c88-bd98d2cce0b8");

  public nsIWebBrowserStream(int paramInt)
  {
    super(paramInt);
  }

  public int OpenStream(int paramInt1, int paramInt2)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramInt1, paramInt2);
  }

  public int AppendToStream(int paramInt1, int paramInt2)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress(), paramInt1, paramInt2);
  }

  public int CloseStream()
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 3, getAddress());
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIWebBrowserStream
 * JD-Core Version:    0.6.2
 */