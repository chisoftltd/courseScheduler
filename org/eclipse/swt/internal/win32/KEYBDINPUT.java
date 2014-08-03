package org.eclipse.swt.internal.win32;

public class KEYBDINPUT
{
  public short wVk;
  public short wScan;
  public int dwFlags;
  public int time;
  public int dwExtraInfo;
  public static final int sizeof = OS.KEYBDINPUT_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.KEYBDINPUT
 * JD-Core Version:    0.6.2
 */