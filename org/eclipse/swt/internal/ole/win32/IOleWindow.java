package org.eclipse.swt.internal.ole.win32;

public class IOleWindow extends IUnknown
{
  public IOleWindow(int paramInt)
  {
    super(paramInt);
  }

  public int GetWindow(int[] paramArrayOfInt)
  {
    return COM.VtblCall(3, this.address, paramArrayOfInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.ole.win32.IOleWindow
 * JD-Core Version:    0.6.2
 */