package org.eclipse.swt.internal.ole.win32;

public class ISpecifyPropertyPages extends IUnknown
{
  public ISpecifyPropertyPages(int paramInt)
  {
    super(paramInt);
  }

  public int GetPages(CAUUID paramCAUUID)
  {
    return COM.VtblCall(3, this.address, paramCAUUID);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.ole.win32.ISpecifyPropertyPages
 * JD-Core Version:    0.6.2
 */