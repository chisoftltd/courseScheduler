package org.eclipse.swt.internal.ole.win32;

public class IDragSourceHelper2 extends IDragSourceHelper
{
  public IDragSourceHelper2(int paramInt)
  {
    super(paramInt);
  }

  public int SetFlags(int paramInt)
  {
    return COM.VtblCall(5, this.address, paramInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.ole.win32.IDragSourceHelper2
 * JD-Core Version:    0.6.2
 */