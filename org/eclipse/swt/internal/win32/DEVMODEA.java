package org.eclipse.swt.internal.win32;

public class DEVMODEA extends DEVMODE
{
  public byte[] dmDeviceName = new byte[32];
  public byte[] dmFormName = new byte[32];
  public static final int sizeof = OS.DEVMODEA_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.DEVMODEA
 * JD-Core Version:    0.6.2
 */