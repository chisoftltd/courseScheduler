package org.eclipse.swt.internal.win32;

public abstract class OSVERSIONINFO
{
  public int dwOSVersionInfoSize;
  public int dwMajorVersion;
  public int dwMinorVersion;
  public int dwBuildNumber;
  public int dwPlatformId;
  public static int sizeof = OS.IsUnicode ? OS.OSVERSIONINFOW_sizeof() : OS.OSVERSIONINFOA_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.OSVERSIONINFO
 * JD-Core Version:    0.6.2
 */