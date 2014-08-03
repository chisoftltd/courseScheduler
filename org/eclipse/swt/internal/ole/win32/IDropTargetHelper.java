package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.win32.POINT;

public class IDropTargetHelper extends IUnknown
{
  public IDropTargetHelper(int paramInt)
  {
    super(paramInt);
  }

  public int DragEnter(int paramInt1, int paramInt2, POINT paramPOINT, int paramInt3)
  {
    return COM.VtblCall(3, this.address, paramInt1, paramInt2, paramPOINT, paramInt3);
  }

  public int DragLeave()
  {
    return COM.VtblCall(4, this.address);
  }

  public int DragOver(POINT paramPOINT, int paramInt)
  {
    return COM.VtblCall(5, this.address, paramPOINT, paramInt);
  }

  public int Drop(int paramInt1, POINT paramPOINT, int paramInt2)
  {
    return COM.VtblCall(6, this.address, paramInt1, paramPOINT, paramInt2);
  }

  public int Show(boolean paramBoolean)
  {
    return COM.VtblCall(7, this.address, paramBoolean);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.ole.win32.IDropTargetHelper
 * JD-Core Version:    0.6.2
 */