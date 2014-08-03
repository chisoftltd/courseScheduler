package org.eclipse.swt.internal.ole.win32;

public class IOleControl extends IUnknown
{
  public IOleControl(int paramInt)
  {
    super(paramInt);
  }

  public int GetControlInfo(CONTROLINFO paramCONTROLINFO)
  {
    return COM.VtblCall(3, this.address, paramCONTROLINFO);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.ole.win32.IOleControl
 * JD-Core Version:    0.6.2
 */