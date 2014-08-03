package org.eclipse.swt.internal.win32;

public class SHFILEINFOW extends SHFILEINFO
{
  public char[] szDisplayName = new char[260];
  public char[] szTypeName = new char[80];
  public static int sizeof = OS.SHFILEINFOW_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.SHFILEINFOW
 * JD-Core Version:    0.6.2
 */