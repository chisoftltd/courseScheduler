package org.eclipse.swt.internal.win32;

public class PAINTSTRUCT
{
  public int hdc;
  public boolean fErase;
  public int left;
  public int top;
  public int right;
  public int bottom;
  public boolean fRestore;
  public boolean fIncUpdate;
  public byte[] rgbReserved = new byte[32];
  public static final int sizeof = OS.PAINTSTRUCT_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.PAINTSTRUCT
 * JD-Core Version:    0.6.2
 */