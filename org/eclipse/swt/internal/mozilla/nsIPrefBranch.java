package org.eclipse.swt.internal.mozilla;

public class nsIPrefBranch extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 18;
  public static final String NS_IPREFBRANCH_IID_STR = "56c35506-f14b-11d3-99d3-ddbfac2ccf65";
  public static final nsID NS_IPREFBRANCH_IID = new nsID("56c35506-f14b-11d3-99d3-ddbfac2ccf65");
  public static final int PREF_INVALID = 0;
  public static final int PREF_STRING = 32;
  public static final int PREF_INT = 64;
  public static final int PREF_BOOL = 128;

  public nsIPrefBranch(int paramInt)
  {
    super(paramInt);
  }

  public int GetRoot(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramArrayOfInt);
  }

  public int GetPrefType(byte[] paramArrayOfByte, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress(), paramArrayOfByte, paramArrayOfInt);
  }

  public int GetBoolPref(byte[] paramArrayOfByte, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 3, getAddress(), paramArrayOfByte, paramArrayOfInt);
  }

  public int SetBoolPref(byte[] paramArrayOfByte, int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 4, getAddress(), paramArrayOfByte, paramInt);
  }

  public int GetCharPref(byte[] paramArrayOfByte, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 5, getAddress(), paramArrayOfByte, paramArrayOfInt);
  }

  public int SetCharPref(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 6, getAddress(), paramArrayOfByte1, paramArrayOfByte2);
  }

  public int GetIntPref(byte[] paramArrayOfByte, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 7, getAddress(), paramArrayOfByte, paramArrayOfInt);
  }

  public int SetIntPref(byte[] paramArrayOfByte, int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 8, getAddress(), paramArrayOfByte, paramInt);
  }

  public int GetComplexValue(byte[] paramArrayOfByte, nsID paramnsID, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 9, getAddress(), paramArrayOfByte, paramnsID, paramArrayOfInt);
  }

  public int SetComplexValue(byte[] paramArrayOfByte, nsID paramnsID, int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 10, getAddress(), paramArrayOfByte, paramnsID, paramInt);
  }

  public int ClearUserPref(byte[] paramArrayOfByte)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 11, getAddress(), paramArrayOfByte);
  }

  public int LockPref(byte[] paramArrayOfByte)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 12, getAddress(), paramArrayOfByte);
  }

  public int PrefHasUserValue(byte[] paramArrayOfByte, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 13, getAddress(), paramArrayOfByte, paramArrayOfInt);
  }

  public int PrefIsLocked(byte[] paramArrayOfByte, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 14, getAddress(), paramArrayOfByte, paramArrayOfInt);
  }

  public int UnlockPref(byte[] paramArrayOfByte)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 15, getAddress(), paramArrayOfByte);
  }

  public int DeleteBranch(byte[] paramArrayOfByte)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 16, getAddress(), paramArrayOfByte);
  }

  public int GetChildList(byte[] paramArrayOfByte, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 17, getAddress(), paramArrayOfByte, paramArrayOfInt1, paramArrayOfInt2);
  }

  public int ResetBranch(byte[] paramArrayOfByte)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 18, getAddress(), paramArrayOfByte);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIPrefBranch
 * JD-Core Version:    0.6.2
 */