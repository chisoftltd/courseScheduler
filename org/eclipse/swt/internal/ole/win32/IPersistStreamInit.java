package org.eclipse.swt.internal.ole.win32;

public class IPersistStreamInit extends IPersist
{
  public IPersistStreamInit(int paramInt)
  {
    super(paramInt);
  }

  public int Load(int paramInt)
  {
    return COM.VtblCall(5, this.address, paramInt);
  }

  public int InitNew()
  {
    return COM.VtblCall(8, this.address);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.ole.win32.IPersistStreamInit
 * JD-Core Version:    0.6.2
 */