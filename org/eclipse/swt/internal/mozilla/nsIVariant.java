package org.eclipse.swt.internal.mozilla;

public class nsIVariant extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 26;
  public static final String NS_IVARIANT_IID_STR = "6c9eb060-8c6a-11d5-90f3-0010a4e73d9a";
  public static final nsID NS_IVARIANT_IID = new nsID("6c9eb060-8c6a-11d5-90f3-0010a4e73d9a");

  public nsIVariant(int paramInt)
  {
    super(paramInt);
  }

  public int GetDataType(short[] paramArrayOfShort)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramArrayOfShort);
  }

  public int GetAsInt8(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress(), paramInt);
  }

  public int GetAsInt16(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 3, getAddress(), paramInt);
  }

  public int GetAsInt32(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 4, getAddress(), paramArrayOfInt);
  }

  public int GetAsInt64(long[] paramArrayOfLong)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 5, getAddress(), paramArrayOfLong);
  }

  public int GetAsUint8(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 6, getAddress(), paramInt);
  }

  public int GetAsUint16(short[] paramArrayOfShort)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 7, getAddress(), paramArrayOfShort);
  }

  public int GetAsUint32(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 8, getAddress(), paramArrayOfInt);
  }

  public int GetAsUint64(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 9, getAddress(), paramInt);
  }

  public int GetAsFloat(float[] paramArrayOfFloat)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 10, getAddress(), paramArrayOfFloat);
  }

  public int GetAsDouble(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 11, getAddress(), paramInt);
  }

  public int GetAsBool(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 12, getAddress(), paramArrayOfInt);
  }

  public int GetAsChar(byte[] paramArrayOfByte)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 13, getAddress(), paramArrayOfByte);
  }

  public int GetAsWChar(char[] paramArrayOfChar)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 14, getAddress(), paramArrayOfChar);
  }

  public int GetAsID(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 15, getAddress(), paramInt);
  }

  public int GetAsAString(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 16, getAddress(), paramInt);
  }

  public int GetAsDOMString(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 17, getAddress(), paramInt);
  }

  public int GetAsACString(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 18, getAddress(), paramInt);
  }

  public int GetAsAUTF8String(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 19, getAddress(), paramInt);
  }

  public int GetAsString(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 20, getAddress(), paramArrayOfInt);
  }

  public int GetAsWString(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 21, getAddress(), paramArrayOfInt);
  }

  public int GetAsISupports(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 22, getAddress(), paramArrayOfInt);
  }

  public int GetAsInterface(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 23, getAddress(), paramArrayOfInt1, paramArrayOfInt2);
  }

  public int GetAsArray(short[] paramArrayOfShort, int paramInt, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 24, getAddress(), paramArrayOfShort, paramInt, paramArrayOfInt1, paramArrayOfInt2);
  }

  public int GetAsStringWithSize(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 25, getAddress(), paramArrayOfInt1, paramArrayOfInt2);
  }

  public int GetAsWStringWithSize(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 26, getAddress(), paramArrayOfInt1, paramArrayOfInt2);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIVariant
 * JD-Core Version:    0.6.2
 */