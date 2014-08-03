package org.eclipse.swt.internal.win32;

public class NMREBARCHILDSIZE extends NMHDR
{
  public int uBand;
  public int wID;
  public int rcChild_left;
  public int rcChild_top;
  public int rcChild_right;
  public int rcChild_bottom;
  public int rcBand_left;
  public int rcBand_top;
  public int rcBand_right;
  public int rcBand_bottom;
  public static final int sizeof = OS.NMREBARCHILDSIZE_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.NMREBARCHILDSIZE
 * JD-Core Version:    0.6.2
 */