package org.eclipse.swt.internal.win32;

public class PROCESS_INFORMATION
{
  public int hProcess;
  public int hThread;
  public int dwProcessId;
  public int dwThreadId;
  public static int sizeof = OS.PROCESS_INFORMATION_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.PROCESS_INFORMATION
 * JD-Core Version:    0.6.2
 */