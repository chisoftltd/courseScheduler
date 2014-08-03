package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.internal.win32.SHDRAGIMAGE;

public class IDragSourceHelper extends IUnknown
{
  public IDragSourceHelper(int paramInt)
  {
    super(paramInt);
  }

  public int InitializeFromBitmap(SHDRAGIMAGE paramSHDRAGIMAGE, int paramInt)
  {
    return COM.VtblCall(3, this.address, paramSHDRAGIMAGE, paramInt);
  }

  public int InitializeFromWindow(int paramInt1, POINT paramPOINT, int paramInt2)
  {
    return COM.VtblCall(4, this.address, paramInt1, paramPOINT, paramInt2);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.ole.win32.IDragSourceHelper
 * JD-Core Version:    0.6.2
 */