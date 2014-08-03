package org.eclipse.swt.internal.ole.win32;

public class IPersist extends IUnknown
{
  public IPersist(int paramInt)
  {
    super(paramInt);
  }

  public int GetClassID(GUID paramGUID)
  {
    return COM.VtblCall(3, this.address, paramGUID);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.ole.win32.IPersist
 * JD-Core Version:    0.6.2
 */