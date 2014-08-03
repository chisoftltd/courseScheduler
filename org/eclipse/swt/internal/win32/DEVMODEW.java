package org.eclipse.swt.internal.win32;

public class DEVMODEW extends DEVMODE
{
  public char[] dmDeviceName = new char[32];
  public char[] dmFormName = new char[32];
  public static final int sizeof = OS.DEVMODEW_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.DEVMODEW
 * JD-Core Version:    0.6.2
 */