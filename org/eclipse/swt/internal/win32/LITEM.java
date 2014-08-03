package org.eclipse.swt.internal.win32;

public class LITEM
{
  public int mask;
  public int iLink;
  public int state;
  public int stateMask;
  public char[] szID = new char[48];
  public char[] szUrl = new char[2084];
  public static final int sizeof = OS.LITEM_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.LITEM
 * JD-Core Version:    0.6.2
 */