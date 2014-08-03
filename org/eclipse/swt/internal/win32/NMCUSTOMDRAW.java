package org.eclipse.swt.internal.win32;

public class NMCUSTOMDRAW extends NMHDR
{
  public int dwDrawStage;
  public int hdc;
  public int left;
  public int top;
  public int right;
  public int bottom;
  public int dwItemSpec;
  public int uItemState;
  public int lItemlParam;
  public static final int sizeof = OS.NMCUSTOMDRAW_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.NMCUSTOMDRAW
 * JD-Core Version:    0.6.2
 */