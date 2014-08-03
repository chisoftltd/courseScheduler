package org.eclipse.swt.internal.win32;

public class BITMAPINFOHEADER
{
  public int biSize;
  public int biWidth;
  public int biHeight;
  public short biPlanes;
  public short biBitCount;
  public int biCompression;
  public int biSizeImage;
  public int biXPelsPerMeter;
  public int biYPelsPerMeter;
  public int biClrUsed;
  public int biClrImportant;
  public static final int sizeof = OS.BITMAPINFOHEADER_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.BITMAPINFOHEADER
 * JD-Core Version:    0.6.2
 */