package org.eclipse.swt.internal.win32;

public abstract class LOGFONT
{
  public int lfHeight;
  public int lfWidth;
  public int lfEscapement;
  public int lfOrientation;
  public int lfWeight;
  public byte lfItalic;
  public byte lfUnderline;
  public byte lfStrikeOut;
  public byte lfCharSet;
  public byte lfOutPrecision;
  public byte lfClipPrecision;
  public byte lfQuality;
  public byte lfPitchAndFamily;
  public static final int sizeof = OS.IsUnicode ? OS.LOGFONTW_sizeof() : OS.LOGFONTA_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.LOGFONT
 * JD-Core Version:    0.6.2
 */