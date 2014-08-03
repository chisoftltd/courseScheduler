package org.eclipse.swt.internal.win32;

public class SHACTIVATEINFO
{
  public int cbSize;
  public int hwndLastFocus;
  public int fSipUp;
  public int fSipOnDeactivation;
  public int fActive;
  public int fReserved;
  public static final int sizeof = OS.SHACTIVATEINFO_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.SHACTIVATEINFO
 * JD-Core Version:    0.6.2
 */