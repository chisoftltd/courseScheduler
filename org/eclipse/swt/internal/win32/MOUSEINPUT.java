package org.eclipse.swt.internal.win32;

public class MOUSEINPUT
{
  public int dx;
  public int dy;
  public int mouseData;
  public int dwFlags;
  public int time;
  public int dwExtraInfo;
  public static final int sizeof = OS.MOUSEINPUT_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.MOUSEINPUT
 * JD-Core Version:    0.6.2
 */