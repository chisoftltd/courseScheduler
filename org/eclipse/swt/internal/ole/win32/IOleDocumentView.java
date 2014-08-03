package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.win32.RECT;

public class IOleDocumentView extends IUnknown
{
  public IOleDocumentView(int paramInt)
  {
    super(paramInt);
  }

  public int SetInPlaceSite(int paramInt)
  {
    return COM.VtblCall(3, this.address, paramInt);
  }

  public int SetRect(RECT paramRECT)
  {
    return COM.VtblCall(6, this.address, paramRECT);
  }

  public int Show(int paramInt)
  {
    return COM.VtblCall(9, this.address, paramInt);
  }

  public int UIActivate(int paramInt)
  {
    return COM.VtblCall(10, this.address, paramInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.ole.win32.IOleDocumentView
 * JD-Core Version:    0.6.2
 */