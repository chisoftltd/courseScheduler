package org.eclipse.swt.internal.mozilla;

public class nsIDOMSerializer extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 2;
  public static final String NS_IDOMSERIALIZER_IID_STR = "a6cf9123-15b3-11d2-932e-00805f8add32";
  public static final nsID NS_IDOMSERIALIZER_IID = new nsID("a6cf9123-15b3-11d2-932e-00805f8add32");

  public nsIDOMSerializer(int paramInt)
  {
    super(paramInt);
  }

  public int SerializeToString(int paramInt, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramInt, paramArrayOfInt);
  }

  public int SerializeToStream(int paramInt1, int paramInt2, byte[] paramArrayOfByte)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress(), paramInt1, paramInt2, paramArrayOfByte);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIDOMSerializer
 * JD-Core Version:    0.6.2
 */