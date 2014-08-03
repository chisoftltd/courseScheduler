package org.eclipse.swt.internal.win32;

public abstract class NMTTDISPINFO extends NMHDR
{
  public int lpszText;
  public int hinst;
  public int uFlags;
  public int lParam;
  public static final int sizeof = OS.IsUnicode ? OS.NMTTDISPINFOW_sizeof() : OS.NMTTDISPINFOA_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.NMTTDISPINFO
 * JD-Core Version:    0.6.2
 */