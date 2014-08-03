package org.eclipse.swt.internal.mozilla;

public class nsICategoryManager extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 6;
  public static final String NS_ICATEGORYMANAGER_IID_STR = "3275b2cd-af6d-429a-80d7-f0c5120342ac";
  public static final nsID NS_ICATEGORYMANAGER_IID = new nsID("3275b2cd-af6d-429a-80d7-f0c5120342ac");

  public nsICategoryManager(int paramInt)
  {
    super(paramInt);
  }

  public int GetCategoryEntry(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramArrayOfByte1, paramArrayOfByte2, paramArrayOfInt);
  }

  public int AddCategoryEntry(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, int paramInt1, int paramInt2, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress(), paramArrayOfByte1, paramArrayOfByte2, paramArrayOfByte3, paramInt1, paramInt2, paramArrayOfInt);
  }

  public int DeleteCategoryEntry(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 3, getAddress(), paramArrayOfByte1, paramArrayOfByte2, paramInt);
  }

  public int DeleteCategory(byte[] paramArrayOfByte)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 4, getAddress(), paramArrayOfByte);
  }

  public int EnumerateCategory(byte[] paramArrayOfByte, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 5, getAddress(), paramArrayOfByte, paramArrayOfInt);
  }

  public int EnumerateCategories(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 6, getAddress(), paramArrayOfInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsICategoryManager
 * JD-Core Version:    0.6.2
 */