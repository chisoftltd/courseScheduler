package org.eclipse.swt.internal.win32;

public abstract class DEVMODE
{
  public short dmSpecVersion;
  public short dmDriverVersion;
  public short dmSize;
  public short dmDriverExtra;
  public int dmFields;
  public short dmOrientation;
  public short dmPaperSize;
  public short dmPaperLength;
  public short dmPaperWidth;
  public short dmScale;
  public short dmCopies;
  public short dmDefaultSource;
  public short dmPrintQuality;
  public short dmColor;
  public short dmDuplex;
  public short dmYResolution;
  public short dmTTOption;
  public short dmCollate;
  public short dmLogPixels;
  public int dmBitsPerPel;
  public int dmPelsWidth;
  public int dmPelsHeight;
  public int dmNup;
  public int dmDisplayFrequency;
  public int dmICMMethod;
  public int dmICMIntent;
  public int dmMediaType;
  public int dmDitherType;
  public int dmReserved1;
  public int dmReserved2;
  public int dmPanningWidth;
  public int dmPanningHeight;
  public static final int sizeof = OS.IsUnicode ? OS.DEVMODEW_sizeof() : OS.DEVMODEA_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.DEVMODE
 * JD-Core Version:    0.6.2
 */