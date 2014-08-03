package org.eclipse.swt.internal.win32;

public class CHOOSEFONT
{
  public int lStructSize;
  public int hwndOwner;
  public int hDC;
  public int lpLogFont;
  public int iPointSize;
  public int Flags;
  public int rgbColors;
  public int lCustData;
  public int lpfnHook;
  public int lpTemplateName;
  public int hInstance;
  public int lpszStyle;
  public short nFontType;
  public int nSizeMin;
  public int nSizeMax;
  public static final int sizeof = OS.CHOOSEFONT_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.CHOOSEFONT
 * JD-Core Version:    0.6.2
 */