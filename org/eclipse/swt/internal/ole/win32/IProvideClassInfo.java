package org.eclipse.swt.internal.ole.win32;

public class IProvideClassInfo extends IUnknown
{
  public IProvideClassInfo(int paramInt)
  {
    super(paramInt);
  }

  public int GetClassInfo(int[] paramArrayOfInt)
  {
    return COM.VtblCall(3, this.address, paramArrayOfInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.ole.win32.IProvideClassInfo
 * JD-Core Version:    0.6.2
 */