package org.eclipse.swt.internal.win32;

public class TBBUTTONINFO
{
  public int cbSize;
  public int dwMask;
  public int idCommand;
  public int iImage;
  public byte fsState;
  public byte fsStyle;
  public short cx;
  public int lParam;
  public int pszText;
  public int cchText;
  public static final int sizeof = OS.TBBUTTONINFO_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.TBBUTTONINFO
 * JD-Core Version:    0.6.2
 */