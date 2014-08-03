package org.eclipse.swt.internal.win32;

public class MCHITTESTINFO
{
  public int cbSize;
  public POINT pt = new POINT();
  public int uHit;
  public SYSTEMTIME st = new SYSTEMTIME();
  public static final int sizeof = OS.MCHITTESTINFO_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.MCHITTESTINFO
 * JD-Core Version:    0.6.2
 */