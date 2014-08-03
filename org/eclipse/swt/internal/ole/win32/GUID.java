package org.eclipse.swt.internal.ole.win32;

public final class GUID
{
  public int Data1;
  public short Data2;
  public short Data3;
  public byte[] Data4 = new byte[8];
  public static final int sizeof = COM.GUID_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.ole.win32.GUID
 * JD-Core Version:    0.6.2
 */