package org.eclipse.swt.internal.win32;

public class LOGFONTA extends LOGFONT
{
  public byte[] lfFaceName = new byte[32];
  public static final int sizeof = OS.LOGFONTA_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.LOGFONTA
 * JD-Core Version:    0.6.2
 */