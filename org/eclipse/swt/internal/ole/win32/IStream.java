package org.eclipse.swt.internal.ole.win32;

public class IStream extends IUnknown
{
  public IStream(int paramInt)
  {
    super(paramInt);
  }

  public int Clone(int[] paramArrayOfInt)
  {
    return COM.VtblCall(13, this.address, paramArrayOfInt);
  }

  public int Commit(int paramInt)
  {
    return COM.VtblCall(8, this.address, paramInt);
  }

  public int Read(int paramInt1, int paramInt2, int[] paramArrayOfInt)
  {
    return COM.VtblCall(3, this.address, paramInt1, paramInt2, paramArrayOfInt);
  }

  public int Revert()
  {
    return COM.VtblCall(9, this.address);
  }

  public int Write(int paramInt1, int paramInt2, int[] paramArrayOfInt)
  {
    return COM.VtblCall(4, this.address, paramInt1, paramInt2, paramArrayOfInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.ole.win32.IStream
 * JD-Core Version:    0.6.2
 */