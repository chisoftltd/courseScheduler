package org.eclipse.swt.internal.mozilla;

public class nsIIOService extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 10;
  public static final String NS_IIOSERVICE_IID_STR = "bddeda3f-9020-4d12-8c70-984ee9f7935e";
  public static final nsID NS_IIOSERVICE_IID = new nsID("bddeda3f-9020-4d12-8c70-984ee9f7935e");

  public nsIIOService(int paramInt)
  {
    super(paramInt);
  }

  public int GetProtocolHandler(byte[] paramArrayOfByte, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramArrayOfByte, paramArrayOfInt);
  }

  public int GetProtocolFlags(byte[] paramArrayOfByte, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress(), paramArrayOfByte, paramArrayOfInt);
  }

  public int NewURI(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 3, getAddress(), paramInt1, paramArrayOfByte, paramInt2, paramArrayOfInt);
  }

  public int NewFileURI(int paramInt, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 4, getAddress(), paramInt, paramArrayOfInt);
  }

  public int NewChannelFromURI(int paramInt, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 5, getAddress(), paramInt, paramArrayOfInt);
  }

  public int NewChannel(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 6, getAddress(), paramInt1, paramArrayOfByte, paramInt2, paramArrayOfInt);
  }

  public int GetOffline(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 7, getAddress(), paramArrayOfInt);
  }

  public int SetOffline(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 8, getAddress(), paramInt);
  }

  public int AllowPort(int paramInt, byte[] paramArrayOfByte, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 9, getAddress(), paramInt, paramArrayOfByte, paramArrayOfInt);
  }

  public int ExtractScheme(int paramInt1, int paramInt2)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 10, getAddress(), paramInt1, paramInt2);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIIOService
 * JD-Core Version:    0.6.2
 */