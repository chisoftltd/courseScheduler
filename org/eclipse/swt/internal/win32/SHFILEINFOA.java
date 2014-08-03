package org.eclipse.swt.internal.win32;

public class SHFILEINFOA extends SHFILEINFO
{
  public byte[] szDisplayName = new byte[260];
  public byte[] szTypeName = new byte[80];
  public static int sizeof = OS.SHFILEINFOA_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.SHFILEINFOA
 * JD-Core Version:    0.6.2
 */