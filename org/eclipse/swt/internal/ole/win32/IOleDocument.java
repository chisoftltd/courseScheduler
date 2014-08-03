package org.eclipse.swt.internal.ole.win32;

public class IOleDocument extends IUnknown
{
  public IOleDocument(int paramInt)
  {
    super(paramInt);
  }

  public int CreateView(int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt)
  {
    return COM.VtblCall(3, this.address, paramInt1, paramInt2, paramInt3, paramArrayOfInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.ole.win32.IOleDocument
 * JD-Core Version:    0.6.2
 */