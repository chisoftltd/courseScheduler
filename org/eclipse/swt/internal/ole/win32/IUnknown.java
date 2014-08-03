package org.eclipse.swt.internal.ole.win32;

public class IUnknown
{
  int address;

  public IUnknown(int paramInt)
  {
    this.address = paramInt;
  }

  public int AddRef()
  {
    return COM.VtblCall(1, this.address);
  }

  public int getAddress()
  {
    return this.address;
  }

  public int QueryInterface(GUID paramGUID, int[] paramArrayOfInt)
  {
    return COM.VtblCall(0, this.address, paramGUID, paramArrayOfInt);
  }

  public int Release()
  {
    return COM.VtblCall(2, this.address);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.ole.win32.IUnknown
 * JD-Core Version:    0.6.2
 */