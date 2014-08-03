package org.eclipse.swt.internal.win32;

public class SYSTEMTIME
{
  public short wYear;
  public short wMonth;
  public short wDayOfWeek;
  public short wDay;
  public short wHour;
  public short wMinute;
  public short wSecond;
  public short wMilliseconds;
  public static final int sizeof = OS.SYSTEMTIME_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.SYSTEMTIME
 * JD-Core Version:    0.6.2
 */