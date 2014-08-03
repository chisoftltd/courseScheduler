package org.eclipse.swt.internal.win32;

public class NMTOOLBAR extends NMHDR
{
  public int iItem;
  public int iBitmap;
  public int idCommand;
  public byte fsState;
  public byte fsStyle;
  public int dwData;
  public int iString;
  public int cchText;
  public int pszText;
  public int left;
  public int top;
  public int right;
  public int bottom;
  public static final int sizeof = OS.NMTOOLBAR_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.NMTOOLBAR
 * JD-Core Version:    0.6.2
 */