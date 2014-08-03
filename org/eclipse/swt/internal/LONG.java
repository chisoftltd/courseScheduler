package org.eclipse.swt.internal;

public class LONG
{
  public int value;

  public LONG(int paramInt)
  {
    this.value = paramInt;
  }

  public boolean equals(Object paramObject)
  {
    if (paramObject == this)
      return true;
    if (!(paramObject instanceof LONG))
      return false;
    LONG localLONG = (LONG)paramObject;
    return localLONG.value == this.value;
  }

  public int hashCode()
  {
    return this.value;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.LONG
 * JD-Core Version:    0.6.2
 */