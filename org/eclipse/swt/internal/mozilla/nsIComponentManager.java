package org.eclipse.swt.internal.mozilla;

public class nsIComponentManager extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 4;
  public static final String NS_ICOMPONENTMANAGER_IID_STR = "a88e5a60-205a-4bb1-94e1-2628daf51eae";
  public static final nsID NS_ICOMPONENTMANAGER_IID = new nsID("a88e5a60-205a-4bb1-94e1-2628daf51eae");

  public nsIComponentManager(int paramInt)
  {
    super(paramInt);
  }

  public int GetClassObject(nsID paramnsID1, nsID paramnsID2, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramnsID1, paramnsID2, paramArrayOfInt);
  }

  public int GetClassObjectByContractID(byte[] paramArrayOfByte, nsID paramnsID, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress(), paramArrayOfByte, paramnsID, paramArrayOfInt);
  }

  public int CreateInstance(nsID paramnsID1, int paramInt, nsID paramnsID2, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 3, getAddress(), paramnsID1, paramInt, paramnsID2, paramArrayOfInt);
  }

  public int CreateInstanceByContractID(byte[] paramArrayOfByte, int paramInt, nsID paramnsID, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 4, getAddress(), paramArrayOfByte, paramInt, paramnsID, paramArrayOfInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIComponentManager
 * JD-Core Version:    0.6.2
 */