package org.eclipse.swt.internal.ole.win32;

public class IServiceProvider extends IUnknown
{
  public IServiceProvider(int paramInt)
  {
    super(paramInt);
  }

  public int QueryService(GUID paramGUID1, GUID paramGUID2, int[] paramArrayOfInt)
  {
    return COM.VtblCall(3, this.address, paramGUID1, paramGUID2, paramArrayOfInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.ole.win32.IServiceProvider
 * JD-Core Version:    0.6.2
 */