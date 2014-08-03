package org.eclipse.swt.internal.ole.win32;

public class IProvideClassInfo2 extends IProvideClassInfo
{
  public IProvideClassInfo2(int paramInt)
  {
    super(paramInt);
  }

  public int GetGUID(int paramInt, GUID paramGUID)
  {
    return COM.VtblCall(4, this.address, paramInt, paramGUID);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.ole.win32.IProvideClassInfo2
 * JD-Core Version:    0.6.2
 */