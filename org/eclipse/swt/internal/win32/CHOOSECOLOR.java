package org.eclipse.swt.internal.win32;

public class CHOOSECOLOR
{
  public int lStructSize;
  public int hwndOwner;
  public int hInstance;
  public int rgbResult;
  public int lpCustColors;
  public int Flags;
  public int lCustData;
  public int lpfnHook;
  public int lpTemplateName;
  public static final int sizeof = OS.CHOOSECOLOR_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.CHOOSECOLOR
 * JD-Core Version:    0.6.2
 */