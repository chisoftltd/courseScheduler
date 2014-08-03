package org.eclipse.swt.internal.win32;

public class EXTLOGPEN
{
  public int elpPenStyle;
  public int elpWidth;
  public int elpBrushStyle;
  public int elpColor;
  public int elpHatch;
  public int elpNumEntries;
  public int[] elpStyleEntry = new int[1];
  public static final int sizeof = OS.EXTLOGPEN_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.EXTLOGPEN
 * JD-Core Version:    0.6.2
 */