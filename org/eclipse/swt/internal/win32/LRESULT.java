package org.eclipse.swt.internal.win32;

public class LRESULT
{
  public int value;
  public static final LRESULT ONE = new LRESULT(1);
  public static final LRESULT ZERO = new LRESULT(0);

  public LRESULT(int paramInt)
  {
    this.value = paramInt;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.LRESULT
 * JD-Core Version:    0.6.2
 */