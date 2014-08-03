package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.win32.MSG;
import org.eclipse.swt.internal.win32.RECT;

public class IOleInPlaceActiveObject extends IOleWindow
{
  public IOleInPlaceActiveObject(int paramInt)
  {
    super(paramInt);
  }

  public int TranslateAccelerator(MSG paramMSG)
  {
    return COM.VtblCall(5, this.address, paramMSG);
  }

  public void OnFrameWindowActivate(boolean paramBoolean)
  {
    COM.VtblCall(6, getAddress(), paramBoolean);
  }

  public void OnDocWindowActivate(boolean paramBoolean)
  {
    COM.VtblCall(7, getAddress(), paramBoolean);
  }

  public int ResizeBorder(RECT paramRECT, int paramInt, boolean paramBoolean)
  {
    return COM.VtblCall(8, this.address, paramRECT, paramInt, paramBoolean);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.ole.win32.IOleInPlaceActiveObject
 * JD-Core Version:    0.6.2
 */