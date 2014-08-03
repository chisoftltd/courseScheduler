package org.eclipse.swt.internal.win32;

public class OFNOTIFY extends NMHDR
{
  public int lpOFN;
  public int pszFile;
  public static int sizeof = OS.OFNOTIFY_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.OFNOTIFY
 * JD-Core Version:    0.6.2
 */