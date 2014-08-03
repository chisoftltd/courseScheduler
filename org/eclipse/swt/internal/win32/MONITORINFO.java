package org.eclipse.swt.internal.win32;

public class MONITORINFO
{
  public int cbSize;
  public int rcMonitor_left;
  public int rcMonitor_top;
  public int rcMonitor_right;
  public int rcMonitor_bottom;
  public int rcWork_left;
  public int rcWork_top;
  public int rcWork_right;
  public int rcWork_bottom;
  public int dwFlags;
  public static final int sizeof = OS.MONITORINFO_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.MONITORINFO
 * JD-Core Version:    0.6.2
 */