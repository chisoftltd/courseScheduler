package org.eclipse.swt.internal.win32;

public class NMTTDISPINFOW extends NMTTDISPINFO
{
  public char[] szText = new char[80];
  public static final int sizeof = OS.NMTTDISPINFOW_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.NMTTDISPINFOW
 * JD-Core Version:    0.6.2
 */