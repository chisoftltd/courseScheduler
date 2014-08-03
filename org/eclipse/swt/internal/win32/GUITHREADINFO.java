package org.eclipse.swt.internal.win32;

public class GUITHREADINFO
{
  public int cbSize;
  public int flags;
  public int hwndActive;
  public int hwndFocus;
  public int hwndCapture;
  public int hwndMenuOwner;
  public int hwndMoveSize;
  public int hwndCaret;
  public int left;
  public int top;
  public int right;
  public int bottom;
  public static int sizeof = OS.GUITHREADINFO_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.GUITHREADINFO
 * JD-Core Version:    0.6.2
 */