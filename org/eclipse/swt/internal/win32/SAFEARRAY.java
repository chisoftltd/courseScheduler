package org.eclipse.swt.internal.win32;

public class SAFEARRAY
{
  public short cDims;
  public short fFeatures;
  public int cbElements;
  public int cLocks;
  public int pvData;
  public SAFEARRAYBOUND rgsabound;
  public static final int sizeof = OS.SAFEARRAY_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.SAFEARRAY
 * JD-Core Version:    0.6.2
 */