package org.eclipse.swt.internal.ole.win32;

public class IClassFactory2 extends IUnknown
{
  public IClassFactory2(int paramInt)
  {
    super(paramInt);
  }

  public int CreateInstanceLic(int paramInt1, int paramInt2, GUID paramGUID, int paramInt3, int[] paramArrayOfInt)
  {
    return COM.VtblCall(7, this.address, paramInt1, paramInt2, paramGUID, paramInt3, paramArrayOfInt);
  }

  public int GetLicInfo(LICINFO paramLICINFO)
  {
    return COM.VtblCall(5, this.address, paramLICINFO);
  }

  public int RequestLicKey(int paramInt, int[] paramArrayOfInt)
  {
    return COM.VtblCall(6, this.address, paramInt, paramArrayOfInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.ole.win32.IClassFactory2
 * JD-Core Version:    0.6.2
 */