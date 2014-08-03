package org.eclipse.swt.internal.mozilla;

public class nsIProperties extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 5;
  public static final String NS_IPROPERTIES_IID_STR = "78650582-4e93-4b60-8e85-26ebd3eb14ca";
  public static final nsID NS_IPROPERTIES_IID = new nsID("78650582-4e93-4b60-8e85-26ebd3eb14ca");

  public nsIProperties(int paramInt)
  {
    super(paramInt);
  }

  public int Get(byte[] paramArrayOfByte, nsID paramnsID, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramArrayOfByte, paramnsID, paramArrayOfInt);
  }

  public int Set(byte[] paramArrayOfByte, int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress(), paramArrayOfByte, paramInt);
  }

  public int Has(byte[] paramArrayOfByte, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 3, getAddress(), paramArrayOfByte, paramArrayOfInt);
  }

  public int Undefine(byte[] paramArrayOfByte)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 4, getAddress(), paramArrayOfByte);
  }

  public int GetKeys(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 5, getAddress(), paramArrayOfInt1, paramArrayOfInt2);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIProperties
 * JD-Core Version:    0.6.2
 */