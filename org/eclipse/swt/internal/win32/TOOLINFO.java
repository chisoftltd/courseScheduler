package org.eclipse.swt.internal.win32;

public class TOOLINFO
{
  public int cbSize;
  public int uFlags;
  public int hwnd;
  public int uId;
  public int left;
  public int top;
  public int right;
  public int bottom;
  public int hinst;
  public int lpszText;
  public int lParam;
  public int lpReserved;
  public static int sizeof = (!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(5, 1)) ? OS.TOOLINFO_sizeof() : 44;
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.TOOLINFO
 * JD-Core Version:    0.6.2
 */