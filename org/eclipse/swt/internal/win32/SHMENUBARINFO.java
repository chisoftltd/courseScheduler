package org.eclipse.swt.internal.win32;

public class SHMENUBARINFO
{
  public int cbSize;
  public int hwndParent;
  public int dwFlags;
  public int nToolBarId;
  public int hInstRes;
  public int nBmpId;
  public int cBmpImages;
  public int hwndMB;
  public static final int sizeof = OS.IsSP ? 36 : OS.SHMENUBARINFO_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.SHMENUBARINFO
 * JD-Core Version:    0.6.2
 */