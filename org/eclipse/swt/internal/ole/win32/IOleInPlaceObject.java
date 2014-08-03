package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.win32.RECT;

public class IOleInPlaceObject extends IOleWindow
{
  public IOleInPlaceObject(int paramInt)
  {
    super(paramInt);
  }

  public int InPlaceDeactivate()
  {
    return COM.VtblCall(5, this.address);
  }

  public int UIDeactivate()
  {
    return COM.VtblCall(6, this.address);
  }

  public int SetObjectRects(RECT paramRECT1, RECT paramRECT2)
  {
    return COM.VtblCall(7, this.address, paramRECT1, paramRECT2);
  }

  public int ReactivateAndUndo()
  {
    return COM.VtblCall(8, this.address);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.ole.win32.IOleInPlaceObject
 * JD-Core Version:    0.6.2
 */