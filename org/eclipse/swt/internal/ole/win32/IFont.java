package org.eclipse.swt.internal.ole.win32;

public class IFont extends IUnknown
{
  public IFont(int paramInt)
  {
    super(paramInt);
  }

  public int get_hFont(int[] paramArrayOfInt)
  {
    return COM.VtblCall(3, this.address, paramArrayOfInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.ole.win32.IFont
 * JD-Core Version:    0.6.2
 */