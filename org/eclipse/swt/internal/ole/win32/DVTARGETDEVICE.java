package org.eclipse.swt.internal.ole.win32;

public final class DVTARGETDEVICE
{
  public int tdSize;
  public short tdDriverNameOffset;
  public short tdDeviceNameOffset;
  public short tdPortNameOffset;
  public short tdExtDevmodeOffset;
  public byte[] tdData = new byte[1];
  public static final int sizeof = COM.DVTARGETDEVICE_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.ole.win32.DVTARGETDEVICE
 * JD-Core Version:    0.6.2
 */