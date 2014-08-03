package org.eclipse.swt.internal.win32;

public class SCROLLINFO
{
  public int cbSize;
  public int fMask;
  public int nMin;
  public int nMax;
  public int nPage;
  public int nPos;
  public int nTrackPos;
  public static final int sizeof = OS.SCROLLINFO_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.SCROLLINFO
 * JD-Core Version:    0.6.2
 */