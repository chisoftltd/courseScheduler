package org.eclipse.swt.internal.win32;

public class NMTTDISPINFOA extends NMTTDISPINFO
{
  public byte[] szText = new byte[80];
  public static final int sizeof = OS.NMTTDISPINFOA_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.NMTTDISPINFOA
 * JD-Core Version:    0.6.2
 */