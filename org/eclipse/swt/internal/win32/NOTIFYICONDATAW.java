package org.eclipse.swt.internal.win32;

public class NOTIFYICONDATAW extends NOTIFYICONDATA
{
  public char[] szTip = new char[''];
  public char[] szInfo = new char[256];
  public char[] szInfoTitle = new char[64];
  public static final int sizeof = OS.NOTIFYICONDATAW_V2_SIZE;
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.NOTIFYICONDATAW
 * JD-Core Version:    0.6.2
 */