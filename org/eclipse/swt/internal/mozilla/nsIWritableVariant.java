package org.eclipse.swt.internal.mozilla;

public class nsIWritableVariant extends nsIVariant
{
  static final int LAST_METHOD_ID = nsIVariant.LAST_METHOD_ID + 31;
  public static final String NS_IWRITABLEVARIANT_IID_STR = "5586a590-8c82-11d5-90f3-0010a4e73d9a";
  public static final nsID NS_IWRITABLEVARIANT_IID = new nsID("5586a590-8c82-11d5-90f3-0010a4e73d9a");

  public nsIWritableVariant(int paramInt)
  {
    super(paramInt);
  }

  public int GetWritable(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsIVariant.LAST_METHOD_ID + 1, getAddress(), paramArrayOfInt);
  }

  public int SetWritable(int paramInt)
  {
    return XPCOM.VtblCall(nsIVariant.LAST_METHOD_ID + 2, getAddress(), paramInt);
  }

  public int SetAsInt32(int paramInt)
  {
    return XPCOM.VtblCall(nsIVariant.LAST_METHOD_ID + 5, getAddress(), paramInt);
  }

  public int SetAsInt64(long paramLong)
  {
    return XPCOM.VtblCall(nsIVariant.LAST_METHOD_ID + 6, getAddress(), paramLong);
  }

  public int SetAsUint16(short paramShort)
  {
    return XPCOM.VtblCall(nsIVariant.LAST_METHOD_ID + 8, getAddress(), paramShort);
  }

  public int SetAsUint32(int paramInt)
  {
    return XPCOM.VtblCall(nsIVariant.LAST_METHOD_ID + 9, getAddress(), paramInt);
  }

  public int SetAsFloat(float paramFloat)
  {
    return XPCOM.VtblCall(nsIVariant.LAST_METHOD_ID + 11, getAddress(), paramFloat);
  }

  public int SetAsDouble(double paramDouble)
  {
    return XPCOM.VtblCall(nsIVariant.LAST_METHOD_ID + 12, getAddress(), paramDouble);
  }

  public int SetAsBool(int paramInt)
  {
    return XPCOM.VtblCall(nsIVariant.LAST_METHOD_ID + 13, getAddress(), paramInt);
  }

  public int SetAsID(int paramInt)
  {
    return XPCOM.VtblCall(nsIVariant.LAST_METHOD_ID + 16, getAddress(), paramInt);
  }

  public int SetAsAString(int paramInt)
  {
    return XPCOM.VtblCall(nsIVariant.LAST_METHOD_ID + 17, getAddress(), paramInt);
  }

  public int SetAsDOMString(int paramInt)
  {
    return XPCOM.VtblCall(nsIVariant.LAST_METHOD_ID + 18, getAddress(), paramInt);
  }

  public int SetAsACString(int paramInt)
  {
    return XPCOM.VtblCall(nsIVariant.LAST_METHOD_ID + 19, getAddress(), paramInt);
  }

  public int SetAsAUTF8String(int paramInt)
  {
    return XPCOM.VtblCall(nsIVariant.LAST_METHOD_ID + 20, getAddress(), paramInt);
  }

  public int SetAsString(byte[] paramArrayOfByte)
  {
    return XPCOM.VtblCall(nsIVariant.LAST_METHOD_ID + 21, getAddress(), paramArrayOfByte);
  }

  public int SetAsWString(char[] paramArrayOfChar)
  {
    return XPCOM.VtblCall(nsIVariant.LAST_METHOD_ID + 22, getAddress(), paramArrayOfChar);
  }

  public int SetAsISupports(int paramInt)
  {
    return XPCOM.VtblCall(nsIVariant.LAST_METHOD_ID + 23, getAddress(), paramInt);
  }

  public int SetAsInterface(nsID paramnsID, int paramInt)
  {
    return XPCOM.VtblCall(nsIVariant.LAST_METHOD_ID + 24, getAddress(), paramnsID, paramInt);
  }

  public int SetAsArray(short paramShort, int paramInt1, int paramInt2, int paramInt3)
  {
    return XPCOM.VtblCall(nsIVariant.LAST_METHOD_ID + 25, getAddress(), paramShort, paramInt1, paramInt2, paramInt3);
  }

  public int SetAsStringWithSize(int paramInt, byte[] paramArrayOfByte)
  {
    return XPCOM.VtblCall(nsIVariant.LAST_METHOD_ID + 26, getAddress(), paramInt, paramArrayOfByte);
  }

  public int SetAsWStringWithSize(int paramInt, char[] paramArrayOfChar)
  {
    return XPCOM.VtblCall(nsIVariant.LAST_METHOD_ID + 27, getAddress(), paramInt, paramArrayOfChar);
  }

  public int SetAsVoid()
  {
    return XPCOM.VtblCall(nsIVariant.LAST_METHOD_ID + 28, getAddress());
  }

  public int SetAsEmpty()
  {
    return XPCOM.VtblCall(nsIVariant.LAST_METHOD_ID + 29, getAddress());
  }

  public int SetAsEmptyArray()
  {
    return XPCOM.VtblCall(nsIVariant.LAST_METHOD_ID + 30, getAddress());
  }

  public int SetFromVariant(int paramInt)
  {
    return XPCOM.VtblCall(nsIVariant.LAST_METHOD_ID + 31, getAddress(), paramInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIWritableVariant
 * JD-Core Version:    0.6.2
 */