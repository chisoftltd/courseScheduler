package org.eclipse.swt.internal.win32;

public class NMTVDISPINFO extends NMHDR
{
  public int mask;
  public int hItem;
  public int state;
  public int stateMask;
  public int pszText;
  public int cchTextMax;
  public int iImage;
  public int iSelectedImage;
  public int cChildren;
  public int lParam;
  public static final int sizeof = OS.NMTVDISPINFO_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.NMTVDISPINFO
 * JD-Core Version:    0.6.2
 */