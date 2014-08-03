package org.eclipse.swt.internal.win32;

public class EXTLOGFONTW
{
  public LOGFONTW elfLogFont = new LOGFONTW();
  public char[] elfFullName = new char[64];
  public char[] elfStyle = new char[32];
  public int elfVersion;
  public int elfStyleSize;
  public int elfMatch;
  public int elfReserved;
  public byte[] elfVendorId = new byte[4];
  public int elfCulture;
  public PANOSE elfPanose = new PANOSE();
  public static final int sizeof = OS.EXTLOGFONTW_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.EXTLOGFONTW
 * JD-Core Version:    0.6.2
 */