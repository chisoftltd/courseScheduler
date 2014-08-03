package org.eclipse.swt.internal.win32;

public class TF_DISPLAYATTRIBUTE
{
  public TF_DA_COLOR crText = new TF_DA_COLOR();
  public TF_DA_COLOR crBk = new TF_DA_COLOR();
  public int lsStyle;
  public boolean fBoldLine;
  public TF_DA_COLOR crLine = new TF_DA_COLOR();
  public int bAttr;
  public static final int sizeof = OS.TF_DISPLAYATTRIBUTE_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.TF_DISPLAYATTRIBUTE
 * JD-Core Version:    0.6.2
 */