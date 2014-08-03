package org.eclipse.swt.internal.win32;

public class SCROLLBARINFO
{
  public int cbSize;
  public RECT rcScrollBar = new RECT();
  public int dxyLineButton;
  public int xyThumbTop;
  public int xyThumbBottom;
  public int reserved;
  public int[] rgstate = new int[6];
  public static final int sizeof = OS.SCROLLBARINFO_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.SCROLLBARINFO
 * JD-Core Version:    0.6.2
 */