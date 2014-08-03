package org.eclipse.swt.internal.win32;

public class NMLISTVIEW extends NMHDR
{
  public int iItem;
  public int iSubItem;
  public int uNewState;
  public int uOldState;
  public int uChanged;
  public int x;
  public int y;
  public int lParam;
  public static int sizeof = OS.NMLISTVIEW_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.NMLISTVIEW
 * JD-Core Version:    0.6.2
 */