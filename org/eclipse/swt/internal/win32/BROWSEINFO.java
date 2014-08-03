package org.eclipse.swt.internal.win32;

public class BROWSEINFO
{
  public int hwndOwner;
  public int pidlRoot;
  public int pszDisplayName;
  public int lpszTitle;
  public int ulFlags;
  public int lpfn;
  public int lParam;
  public int iImage;
  public static final int sizeof = OS.BROWSEINFO_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.BROWSEINFO
 * JD-Core Version:    0.6.2
 */