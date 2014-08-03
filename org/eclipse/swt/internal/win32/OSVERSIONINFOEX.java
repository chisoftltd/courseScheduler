package org.eclipse.swt.internal.win32;

public abstract class OSVERSIONINFOEX extends OSVERSIONINFO
{
  public short wServicePackMajor;
  public short wServicePackMinor;
  public short wSuiteMask;
  public byte wProductType;
  public byte wReserved;
  public static int sizeof = OS.IsUnicode ? OS.OSVERSIONINFOEXW_sizeof() : OS.OSVERSIONINFOEXA_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.OSVERSIONINFOEX
 * JD-Core Version:    0.6.2
 */