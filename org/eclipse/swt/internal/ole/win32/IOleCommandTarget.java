package org.eclipse.swt.internal.ole.win32;

public class IOleCommandTarget extends IUnknown
{
  public IOleCommandTarget(int paramInt)
  {
    super(paramInt);
  }

  public int Exec(GUID paramGUID, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return COM.VtblCall(4, this.address, paramGUID, paramInt1, paramInt2, paramInt3, paramInt4);
  }

  public int QueryStatus(GUID paramGUID, int paramInt, OLECMD paramOLECMD, OLECMDTEXT paramOLECMDTEXT)
  {
    if (paramInt > 1)
      return -2147024809;
    return COM.VtblCall(3, this.address, paramGUID, paramInt, paramOLECMD, paramOLECMDTEXT);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.ole.win32.IOleCommandTarget
 * JD-Core Version:    0.6.2
 */