package org.eclipse.swt.internal.win32;

public class NMTVITEMCHANGE extends NMHDR
{
  public int uChanged;
  public int hItem;
  public int uStateNew;
  public int uStateOld;
  public int lParam;
  public static int sizeof = OS.NMTVITEMCHANGE_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.NMTVITEMCHANGE
 * JD-Core Version:    0.6.2
 */