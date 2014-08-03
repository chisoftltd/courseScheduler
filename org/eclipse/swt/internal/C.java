package org.eclipse.swt.internal;

public class C extends Platform
{
  public static final int PTR_SIZEOF = PTR_sizeof();

  static
  {
    if (("Linux".equals(System.getProperty("os.name"))) && ("motif".equals("win32")))
      try
      {
        Library.loadLibrary("libXm.so.2", false);
      }
      catch (Throwable localThrowable)
      {
      }
    Library.loadLibrary("swt");
  }

  public static final native void free(int paramInt);

  public static final native int getenv(byte[] paramArrayOfByte);

  public static final native int malloc(int paramInt);

  public static final native void memmove(int paramInt1, byte[] paramArrayOfByte, int paramInt2);

  public static final native void memmove(int paramInt1, char[] paramArrayOfChar, int paramInt2);

  public static final native void memmove(int paramInt1, double[] paramArrayOfDouble, int paramInt2);

  public static final native void memmove(int paramInt1, float[] paramArrayOfFloat, int paramInt2);

  public static final native void memmove(int paramInt1, int[] paramArrayOfInt, int paramInt2);

  public static final native void memmove(int paramInt1, long[] paramArrayOfLong, int paramInt2);

  public static final native void memmove(int paramInt1, short[] paramArrayOfShort, int paramInt2);

  public static final native void memmove(byte[] paramArrayOfByte, char[] paramArrayOfChar, int paramInt);

  public static final native void memmove(byte[] paramArrayOfByte, int paramInt1, int paramInt2);

  public static final native void memmove(int paramInt1, int paramInt2, int paramInt3);

  public static final native void memmove(char[] paramArrayOfChar, int paramInt1, int paramInt2);

  public static final native void memmove(double[] paramArrayOfDouble, int paramInt1, int paramInt2);

  public static final native void memmove(float[] paramArrayOfFloat, int paramInt1, int paramInt2);

  public static final native void memmove(int[] paramArrayOfInt, byte[] paramArrayOfByte, int paramInt);

  public static final native void memmove(short[] paramArrayOfShort, int paramInt1, int paramInt2);

  public static final native void memmove(int[] paramArrayOfInt, int paramInt1, int paramInt2);

  public static final native void memmove(long[] paramArrayOfLong, int paramInt1, int paramInt2);

  public static final native int memset(int paramInt1, int paramInt2, int paramInt3);

  public static final native int PTR_sizeof();

  public static final native int strlen(int paramInt);
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.C
 * JD-Core Version:    0.6.2
 */