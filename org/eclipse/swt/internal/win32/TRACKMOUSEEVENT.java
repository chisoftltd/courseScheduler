package org.eclipse.swt.internal.win32;

public class TRACKMOUSEEVENT
{
  public int cbSize;
  public int dwFlags;
  public int hwndTrack;
  public int dwHoverTime;
  public static final int sizeof = OS.TRACKMOUSEEVENT_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.TRACKMOUSEEVENT
 * JD-Core Version:    0.6.2
 */