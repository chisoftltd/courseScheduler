package org.eclipse.swt.internal.ole.win32;

public class IConnectionPointContainer extends IUnknown
{
  public IConnectionPointContainer(int paramInt)
  {
    super(paramInt);
  }

  public int FindConnectionPoint(GUID paramGUID, int[] paramArrayOfInt)
  {
    return COM.VtblCall(4, this.address, paramGUID, paramArrayOfInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.ole.win32.IConnectionPointContainer
 * JD-Core Version:    0.6.2
 */