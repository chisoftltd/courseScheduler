package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.win32.SIZE;

public class IViewObject2 extends IUnknown
{
  public IViewObject2(int paramInt)
  {
    super(paramInt);
  }

  public int GetExtent(int paramInt1, int paramInt2, DVTARGETDEVICE paramDVTARGETDEVICE, SIZE paramSIZE)
  {
    return COM.VtblCall(9, this.address, paramInt1, paramInt2, paramDVTARGETDEVICE, paramSIZE);
  }

  public int SetAdvise(int paramInt1, int paramInt2, int paramInt3)
  {
    return COM.VtblCall(7, this.address, paramInt1, paramInt2, paramInt3);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.ole.win32.IViewObject2
 * JD-Core Version:    0.6.2
 */