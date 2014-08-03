package org.eclipse.swt.internal.ole.win32;

public class IOleLink extends IUnknown
{
  public IOleLink(int paramInt)
  {
    super(paramInt);
  }

  public int BindIfRunning()
  {
    return COM.VtblCall(10, this.address);
  }

  public int GetSourceMoniker(int[] paramArrayOfInt)
  {
    return COM.VtblCall(6, this.address, paramArrayOfInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.ole.win32.IOleLink
 * JD-Core Version:    0.6.2
 */