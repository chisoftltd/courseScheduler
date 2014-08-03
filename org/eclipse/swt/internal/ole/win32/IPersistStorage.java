package org.eclipse.swt.internal.ole.win32;

public class IPersistStorage extends IPersist
{
  public IPersistStorage(int paramInt)
  {
    super(paramInt);
  }

  public int IsDirty()
  {
    return COM.VtblCall(4, this.address);
  }

  public int InitNew(int paramInt)
  {
    return COM.VtblCall(5, this.address, paramInt);
  }

  public int Load(int paramInt)
  {
    return COM.VtblCall(6, this.address, paramInt);
  }

  public int Save(int paramInt, boolean paramBoolean)
  {
    return COM.VtblCall(7, this.address, paramInt, paramBoolean);
  }

  public int SaveCompleted(int paramInt)
  {
    return COM.VtblCall(8, this.address, paramInt);
  }

  public int HandsOffStorage()
  {
    return COM.VtblCall(9, this.address);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.ole.win32.IPersistStorage
 * JD-Core Version:    0.6.2
 */