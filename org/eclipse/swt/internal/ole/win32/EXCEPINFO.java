package org.eclipse.swt.internal.ole.win32;

public final class EXCEPINFO
{
  public short wCode;
  public short wReserved;
  public int bstrSource;
  public int bstrDescription;
  public int bstrHelpFile;
  public int dwHelpContext;
  public int pvReserved;
  public int pfnDeferredFillIn;
  public int scode;
  public static final int sizeof = COM.EXCEPINFO_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.ole.win32.EXCEPINFO
 * JD-Core Version:    0.6.2
 */