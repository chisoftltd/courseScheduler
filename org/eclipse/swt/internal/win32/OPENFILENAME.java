package org.eclipse.swt.internal.win32;

public class OPENFILENAME
{
  public int lStructSize;
  public int hwndOwner;
  public int hInstance;
  public int lpstrFilter;
  public int lpstrCustomFilter;
  public int nMaxCustFilter;
  public int nFilterIndex;
  public int lpstrFile;
  public int nMaxFile;
  public int lpstrFileTitle;
  public int nMaxFileTitle;
  public int lpstrInitialDir;
  public int lpstrTitle;
  public int Flags;
  public short nFileOffset;
  public short nFileExtension;
  public int lpstrDefExt;
  public int lCustData;
  public int lpfnHook;
  public int lpTemplateName;
  public int pvReserved;
  public int dwReserved;
  public int FlagsEx;
  public static final int sizeof = (!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(5, 0)) ? OS.OPENFILENAME_sizeof() : 76;
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.OPENFILENAME
 * JD-Core Version:    0.6.2
 */