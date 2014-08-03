package org.eclipse.swt.internal.ole.win32;

public class IEnum extends IUnknown
{
  public IEnum(int paramInt)
  {
    super(paramInt);
  }

  public int Clone(int[] paramArrayOfInt)
  {
    return COM.VtblCall(6, this.address, paramArrayOfInt);
  }

  public int Next(int paramInt1, int paramInt2, int[] paramArrayOfInt)
  {
    return COM.VtblCall(3, this.address, paramInt1, paramInt2, paramArrayOfInt);
  }

  public int Reset()
  {
    return COM.VtblCall(5, this.address);
  }

  public int Skip(int paramInt)
  {
    return COM.VtblCall(4, this.address, paramInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.ole.win32.IEnum
 * JD-Core Version:    0.6.2
 */