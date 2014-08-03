package org.eclipse.swt.internal.win32;

public class PRINTDLG
{
  public int lStructSize;
  public int hwndOwner;
  public int hDevMode;
  public int hDevNames;
  public int hDC;
  public int Flags;
  public short nFromPage;
  public short nToPage;
  public short nMinPage;
  public short nMaxPage;
  public short nCopies;
  public int hInstance;
  public int lCustData;
  public int lpfnPrintHook;
  public int lpfnSetupHook;
  public int lpPrintTemplateName;
  public int lpSetupTemplateName;
  public int hPrintTemplate;
  public int hSetupTemplate;
  public static final int sizeof = OS.PRINTDLG_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.PRINTDLG
 * JD-Core Version:    0.6.2
 */