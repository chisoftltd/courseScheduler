package org.eclipse.swt.internal.ole.win32;

public class IDispatchEx extends IDispatch
{
  public IDispatchEx(int paramInt)
  {
    super(paramInt);
  }

  public int GetDispID(int paramInt1, int paramInt2, int[] paramArrayOfInt)
  {
    return COM.VtblCall(7, this.address, paramInt1, paramInt2, paramArrayOfInt);
  }

  public int InvokeEx(int paramInt1, int paramInt2, int paramInt3, DISPPARAMS paramDISPPARAMS, int paramInt4, EXCEPINFO paramEXCEPINFO, int paramInt5)
  {
    return COM.VtblCall(8, this.address, paramInt1, paramInt2, paramInt3, paramDISPPARAMS, paramInt4, paramEXCEPINFO, paramInt5);
  }

  public int DeleteMemberByName(int paramInt1, int paramInt2)
  {
    return COM.VtblCall(9, this.address, paramInt1, paramInt2);
  }

  public int DeleteMemberByDispID(int paramInt)
  {
    return COM.VtblCall(10, this.address, paramInt);
  }

  public int GetMemberProperties(int paramInt1, int paramInt2, int[] paramArrayOfInt)
  {
    return COM.VtblCall(11, this.address, paramInt1, paramInt2, paramArrayOfInt);
  }

  public int GetMemberName(int paramInt, int[] paramArrayOfInt)
  {
    return COM.VtblCall(12, this.address, paramInt, paramArrayOfInt);
  }

  public int GetNextDispID(int paramInt1, int paramInt2, int[] paramArrayOfInt)
  {
    return COM.VtblCall(13, this.address, paramInt1, paramInt2, paramArrayOfInt);
  }

  public int GetNameSpaceParent(int[] paramArrayOfInt)
  {
    return COM.VtblCall(14, this.address, paramArrayOfInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.ole.win32.IDispatchEx
 * JD-Core Version:    0.6.2
 */