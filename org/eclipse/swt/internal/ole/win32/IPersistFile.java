package org.eclipse.swt.internal.ole.win32;

public class IPersistFile extends IPersist
{
  public IPersistFile(int paramInt)
  {
    super(paramInt);
  }

  public int IsDirty()
  {
    return COM.VtblCall(4, this.address);
  }

  public int Load(int paramInt1, int paramInt2)
  {
    return COM.VtblCall(5, this.address, paramInt1, paramInt2);
  }

  public int Save(int paramInt, boolean paramBoolean)
  {
    return COM.VtblCall(6, this.address, paramInt, paramBoolean);
  }

  public int SaveCompleted(int paramInt)
  {
    return COM.VtblCall(7, this.address, paramInt);
  }

  public int GetCurFile(int[] paramArrayOfInt)
  {
    return COM.VtblCall(8, this.address, paramArrayOfInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.ole.win32.IPersistFile
 * JD-Core Version:    0.6.2
 */