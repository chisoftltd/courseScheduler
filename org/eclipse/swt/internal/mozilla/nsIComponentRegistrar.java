package org.eclipse.swt.internal.mozilla;

public class nsIComponentRegistrar extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 12;
  public static final String NS_ICOMPONENTREGISTRAR_IID_STR = "2417cbfe-65ad-48a6-b4b6-eb84db174392";
  public static final nsID NS_ICOMPONENTREGISTRAR_IID = new nsID("2417cbfe-65ad-48a6-b4b6-eb84db174392");

  public nsIComponentRegistrar(int paramInt)
  {
    super(paramInt);
  }

  public int AutoRegister(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramInt);
  }

  public int AutoUnregister(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress(), paramInt);
  }

  public int RegisterFactory(nsID paramnsID, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 3, getAddress(), paramnsID, paramArrayOfByte1, paramArrayOfByte2, paramInt);
  }

  public int UnregisterFactory(nsID paramnsID, int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 4, getAddress(), paramnsID, paramInt);
  }

  public int RegisterFactoryLocation(nsID paramnsID, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt, byte[] paramArrayOfByte3, byte[] paramArrayOfByte4)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 5, getAddress(), paramnsID, paramArrayOfByte1, paramArrayOfByte2, paramInt, paramArrayOfByte3, paramArrayOfByte4);
  }

  public int UnregisterFactoryLocation(nsID paramnsID, int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 6, getAddress(), paramnsID, paramInt);
  }

  public int IsCIDRegistered(nsID paramnsID, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 7, getAddress(), paramnsID, paramArrayOfInt);
  }

  public int IsContractIDRegistered(byte[] paramArrayOfByte, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 8, getAddress(), paramArrayOfByte, paramArrayOfInt);
  }

  public int EnumerateCIDs(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 9, getAddress(), paramArrayOfInt);
  }

  public int EnumerateContractIDs(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 10, getAddress(), paramArrayOfInt);
  }

  public int CIDToContractID(nsID paramnsID, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 11, getAddress(), paramnsID, paramArrayOfInt);
  }

  public int ContractIDToCID(byte[] paramArrayOfByte, int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 12, getAddress(), paramArrayOfByte, paramInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIComponentRegistrar
 * JD-Core Version:    0.6.2
 */