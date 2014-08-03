package org.eclipse.swt.internal.win32;

public class NMLVDISPINFO extends NMHDR
{
  public int mask;
  public int iItem;
  public int iSubItem;
  public int state;
  public int stateMask;
  public int pszText;
  public int cchTextMax;
  public int iImage;
  public int lParam;
  public int iIndent;
  public int iGroupId;
  public int cColumns;
  public int puColumns;
  public static final int sizeof = (!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(5, 1)) ? OS.NMLVDISPINFO_sizeof() : 52;
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.NMLVDISPINFO
 * JD-Core Version:    0.6.2
 */