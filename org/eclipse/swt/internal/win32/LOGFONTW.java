package org.eclipse.swt.internal.win32;

public class LOGFONTW extends LOGFONT
{
  public char[] lfFaceName = new char[32];
  public static final int sizeof = OS.LOGFONTW_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.LOGFONTW
 * JD-Core Version:    0.6.2
 */