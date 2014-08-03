package org.eclipse.swt.internal.win32;

public class NMLVODSTATECHANGE extends NMHDR
{
  public int iFrom;
  public int iTo;
  public int uNewState;
  public int uOldState;
  public static final int sizeof = OS.NMLVODSTATECHANGE_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.NMLVODSTATECHANGE
 * JD-Core Version:    0.6.2
 */