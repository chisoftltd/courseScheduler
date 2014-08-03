package org.eclipse.swt.internal.win32;

public class SHFILEINFO
{
  public int hIcon;
  public int iIcon;
  public int dwAttributes;
  public static int sizeof = OS.IsUnicode ? OS.SHFILEINFOW_sizeof() : OS.SHFILEINFOA_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.SHFILEINFO
 * JD-Core Version:    0.6.2
 */