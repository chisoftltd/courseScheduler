package org.eclipse.swt.internal.win32;

public abstract class NOTIFYICONDATA
{
  public int cbSize;
  public int hWnd;
  public int uID;
  public int uFlags;
  public int uCallbackMessage;
  public int hIcon;
  public int dwState;
  public int dwStateMask;
  public int uVersion;
  public int dwInfoFlags;
  public static final int sizeof = OS.NOTIFYICONDATA_V2_SIZE;
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.NOTIFYICONDATA
 * JD-Core Version:    0.6.2
 */