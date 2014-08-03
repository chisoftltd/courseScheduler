package org.eclipse.swt.internal.mozilla;

public class nsIServiceManager extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 4;
  public static final String NS_ISERVICEMANAGER_IID_STR = "8bb35ed9-e332-462d-9155-4a002ab5c958";
  public static final nsID NS_ISERVICEMANAGER_IID = new nsID("8bb35ed9-e332-462d-9155-4a002ab5c958");

  public nsIServiceManager(int paramInt)
  {
    super(paramInt);
  }

  public int GetService(nsID paramnsID1, nsID paramnsID2, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramnsID1, paramnsID2, paramArrayOfInt);
  }

  public int GetServiceByContractID(byte[] paramArrayOfByte, nsID paramnsID, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress(), paramArrayOfByte, paramnsID, paramArrayOfInt);
  }

  public int IsServiceInstantiated(nsID paramnsID1, nsID paramnsID2, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 3, getAddress(), paramnsID1, paramnsID2, paramArrayOfInt);
  }

  public int IsServiceInstantiatedByContractID(byte[] paramArrayOfByte, nsID paramnsID, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 4, getAddress(), paramArrayOfByte, paramnsID, paramArrayOfInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIServiceManager
 * JD-Core Version:    0.6.2
 */