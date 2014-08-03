package org.eclipse.swt.internal.ole.win32;

public class IConnectionPoint extends IUnknown
{
  public IConnectionPoint(int paramInt)
  {
    super(paramInt);
  }

  public int Advise(int paramInt, int[] paramArrayOfInt)
  {
    return COM.VtblCall(5, this.address, paramInt, paramArrayOfInt);
  }

  public int Unadvise(int paramInt)
  {
    return COM.VtblCall(6, this.address, paramInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.ole.win32.IConnectionPoint
 * JD-Core Version:    0.6.2
 */