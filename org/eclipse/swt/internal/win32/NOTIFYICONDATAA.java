package org.eclipse.swt.internal.win32;

public class NOTIFYICONDATAA extends NOTIFYICONDATA
{
  public byte[] szTip = new byte[''];
  public byte[] szInfo = new byte[256];
  public byte[] szInfoTitle = new byte[64];
  public static final int sizeof = OS.NOTIFYICONDATAA_V2_SIZE;
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.NOTIFYICONDATAA
 * JD-Core Version:    0.6.2
 */