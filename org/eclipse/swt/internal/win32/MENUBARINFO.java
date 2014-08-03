package org.eclipse.swt.internal.win32;

public class MENUBARINFO
{
  public int cbSize;
  public int left;
  public int top;
  public int right;
  public int bottom;
  public int hMenu;
  public int hwndMenu;
  public boolean fBarFocused;
  public boolean fFocused;
  public static final int sizeof = OS.MENUBARINFO_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.MENUBARINFO
 * JD-Core Version:    0.6.2
 */