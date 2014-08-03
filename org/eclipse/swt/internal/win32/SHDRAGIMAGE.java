package org.eclipse.swt.internal.win32;

public final class SHDRAGIMAGE
{
  public SIZE sizeDragImage = new SIZE();
  public POINT ptOffset = new POINT();
  public int hbmpDragImage;
  public int crColorKey;
  public static final int sizeof = OS.SHDRAGIMAGE_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.SHDRAGIMAGE
 * JD-Core Version:    0.6.2
 */