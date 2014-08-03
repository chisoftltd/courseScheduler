package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.win32.OS;

public class IDispatch extends IUnknown
{
  public IDispatch(int paramInt)
  {
    super(paramInt);
  }

  public int GetIDsOfNames(GUID paramGUID, String[] paramArrayOfString, int paramInt1, int paramInt2, int[] paramArrayOfInt)
  {
    int i = paramArrayOfString.length;
    int j = OS.GetProcessHeap();
    int k = OS.HeapAlloc(j, 8, i * OS.PTR_SIZEOF);
    int[] arrayOfInt = new int[i];
    try
    {
      for (int m = 0; m < i; m++)
      {
        int n = paramArrayOfString[m].length();
        char[] arrayOfChar = new char[n + 1];
        paramArrayOfString[m].getChars(0, n, arrayOfChar, 0);
        int i1 = OS.HeapAlloc(j, 8, arrayOfChar.length * 2);
        OS.MoveMemory(i1, arrayOfChar, arrayOfChar.length * 2);
        COM.MoveMemory(k + OS.PTR_SIZEOF * m, new int[] { i1 }, OS.PTR_SIZEOF);
        arrayOfInt[m] = i1;
      }
      int i2 = COM.VtblCall(5, this.address, new GUID(), k, paramInt1, paramInt2, paramArrayOfInt);
      return i2;
    }
    finally
    {
      for (int i3 = 0; i3 < arrayOfInt.length; i3++)
        OS.HeapFree(j, 0, arrayOfInt[i3]);
      OS.HeapFree(j, 0, k);
    }
  }

  public int GetTypeInfo(int paramInt1, int paramInt2, int[] paramArrayOfInt)
  {
    return COM.VtblCall(4, this.address, paramInt1, paramInt2, paramArrayOfInt);
  }

  public int GetTypeInfoCount(int[] paramArrayOfInt)
  {
    return COM.VtblCall(3, this.address, paramArrayOfInt);
  }

  public int Invoke(int paramInt1, GUID paramGUID, int paramInt2, int paramInt3, DISPPARAMS paramDISPPARAMS, int paramInt4, EXCEPINFO paramEXCEPINFO, int[] paramArrayOfInt)
  {
    return COM.VtblCall(6, this.address, paramInt1, paramGUID, paramInt2, paramInt3, paramDISPPARAMS, paramInt4, paramEXCEPINFO, paramArrayOfInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.ole.win32.IDispatch
 * JD-Core Version:    0.6.2
 */