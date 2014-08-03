package org.eclipse.swt.internal.win32;

public class WNDCLASS
{
  public int style;
  public int lpfnWndProc;
  public int cbClsExtra;
  public int cbWndExtra;
  public int hInstance;
  public int hIcon;
  public int hCursor;
  public int hbrBackground;
  public int lpszMenuName;
  public int lpszClassName;
  public static final int sizeof = OS.WNDCLASS_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.WNDCLASS
 * JD-Core Version:    0.6.2
 */