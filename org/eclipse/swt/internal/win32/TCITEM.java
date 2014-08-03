package org.eclipse.swt.internal.win32;

public class TCITEM
{
  public int mask;
  public int dwState;
  public int dwStateMask;
  public int pszText;
  public int cchTextMax;
  public int iImage;
  public int lParam;
  public static final int sizeof = OS.TCITEM_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.TCITEM
 * JD-Core Version:    0.6.2
 */