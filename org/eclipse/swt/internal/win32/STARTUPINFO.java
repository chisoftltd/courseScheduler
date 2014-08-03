package org.eclipse.swt.internal.win32;

public class STARTUPINFO
{
  public int cb;
  public int lpReserved;
  public int lpDesktop;
  public int lpTitle;
  public int dwX;
  public int dwY;
  public int dwXSize;
  public int dwYSize;
  public int dwXCountChars;
  public int dwYCountChars;
  public int dwFillAttribute;
  public int dwFlags;
  public short wShowWindow;
  public short cbReserved2;
  public int lpReserved2;
  public int hStdInput;
  public int hStdOutput;
  public int hStdError;
  public static int sizeof = OS.STARTUPINFO_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.STARTUPINFO
 * JD-Core Version:    0.6.2
 */