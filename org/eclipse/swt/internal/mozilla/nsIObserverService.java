package org.eclipse.swt.internal.mozilla;

public class nsIObserverService extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 4;
  public static final String NS_IOBSERVERSERVICE_IID_STR = "d07f5192-e3d1-11d2-8acd-00105a1b8860";
  public static final nsID NS_IOBSERVERSERVICE_IID = new nsID("d07f5192-e3d1-11d2-8acd-00105a1b8860");

  public nsIObserverService(int paramInt)
  {
    super(paramInt);
  }

  public int AddObserver(int paramInt1, byte[] paramArrayOfByte, int paramInt2)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramInt1, paramArrayOfByte, paramInt2);
  }

  public int RemoveObserver(int paramInt, byte[] paramArrayOfByte)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress(), paramInt, paramArrayOfByte);
  }

  public int NotifyObservers(int paramInt, byte[] paramArrayOfByte, char[] paramArrayOfChar)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 3, getAddress(), paramInt, paramArrayOfByte, paramArrayOfChar);
  }

  public int EnumerateObservers(byte[] paramArrayOfByte, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 4, getAddress(), paramArrayOfByte, paramArrayOfInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIObserverService
 * JD-Core Version:    0.6.2
 */