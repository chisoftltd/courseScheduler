package org.eclipse.swt.internal.win32;

public class NMLVFINDITEM extends NMHDR
{
  public int iStart;
  public int flags;
  public int psz;
  public int lParam;
  public int x;
  public int y;
  public int vkDirection;
  public static final int sizeof = OS.NMLVFINDITEM_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.NMLVFINDITEM
 * JD-Core Version:    0.6.2
 */