package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.win32.OS;

public class ITypeInfo extends IUnknown
{
  public ITypeInfo(int paramInt)
  {
    super(paramInt);
  }

  public int GetDocumentation(int paramInt, String[] paramArrayOfString1, String[] paramArrayOfString2, int[] paramArrayOfInt, String[] paramArrayOfString3)
  {
    int[] arrayOfInt1 = (int[])null;
    if (paramArrayOfString1 != null)
      arrayOfInt1 = new int[1];
    int[] arrayOfInt2 = (int[])null;
    if (paramArrayOfString2 != null)
      arrayOfInt2 = new int[1];
    int[] arrayOfInt3 = (int[])null;
    if (paramArrayOfString3 != null)
      arrayOfInt3 = new int[1];
    int i = COM.VtblCall(12, this.address, paramInt, arrayOfInt1, arrayOfInt2, paramArrayOfInt, arrayOfInt3);
    int j;
    char[] arrayOfChar;
    int k;
    if ((paramArrayOfString1 != null) && (arrayOfInt1[0] != 0))
    {
      j = COM.SysStringByteLen(arrayOfInt1[0]);
      if (j > 0)
      {
        arrayOfChar = new char[(j + 1) / 2];
        COM.MoveMemory(arrayOfChar, arrayOfInt1[0], j);
        paramArrayOfString1[0] = new String(arrayOfChar);
        k = paramArrayOfString1[0].indexOf("");
        if (k > 0)
          paramArrayOfString1[0] = paramArrayOfString1[0].substring(0, k);
      }
      COM.SysFreeString(arrayOfInt1[0]);
    }
    if ((paramArrayOfString2 != null) && (arrayOfInt2[0] != 0))
    {
      j = COM.SysStringByteLen(arrayOfInt2[0]);
      if (j > 0)
      {
        arrayOfChar = new char[(j + 1) / 2];
        COM.MoveMemory(arrayOfChar, arrayOfInt2[0], j);
        paramArrayOfString2[0] = new String(arrayOfChar);
        k = paramArrayOfString2[0].indexOf("");
        if (k > 0)
          paramArrayOfString2[0] = paramArrayOfString2[0].substring(0, k);
      }
      COM.SysFreeString(arrayOfInt2[0]);
    }
    if ((paramArrayOfString3 != null) && (arrayOfInt3[0] != 0))
    {
      j = COM.SysStringByteLen(arrayOfInt3[0]);
      if (j > 0)
      {
        arrayOfChar = new char[(j + 1) / 2];
        COM.MoveMemory(arrayOfChar, arrayOfInt3[0], j);
        paramArrayOfString3[0] = new String(arrayOfChar);
        k = paramArrayOfString3[0].indexOf("");
        if (k > 0)
          paramArrayOfString3[0] = paramArrayOfString3[0].substring(0, k);
      }
      COM.SysFreeString(arrayOfInt3[0]);
    }
    return i;
  }

  public int GetFuncDesc(int paramInt, int[] paramArrayOfInt)
  {
    return COM.VtblCall(5, this.address, paramInt, paramArrayOfInt);
  }

  public int GetIDsOfNames(String[] paramArrayOfString, int paramInt, int[] paramArrayOfInt)
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
      int i2 = COM.VtblCall(10, this.address, k, paramInt, paramArrayOfInt);
      return i2;
    }
    finally
    {
      for (int i3 = 0; i3 < arrayOfInt.length; i3++)
        OS.HeapFree(j, 0, arrayOfInt[i3]);
      OS.HeapFree(j, 0, k);
    }
  }

  public int GetImplTypeFlags(int paramInt, int[] paramArrayOfInt)
  {
    return COM.VtblCall(9, this.address, paramInt, paramArrayOfInt);
  }

  public int GetNames(int paramInt1, String[] paramArrayOfString, int paramInt2, int[] paramArrayOfInt)
  {
    int i = paramArrayOfString.length;
    int[] arrayOfInt = new int[i];
    int j = COM.VtblCall(7, this.address, paramInt1, arrayOfInt, i, paramArrayOfInt);
    if (j == 0)
      for (int k = 0; k < paramArrayOfInt[0]; k++)
      {
        int m = COM.SysStringByteLen(arrayOfInt[k]);
        if (m > 0)
        {
          char[] arrayOfChar = new char[(m + 1) / 2];
          COM.MoveMemory(arrayOfChar, arrayOfInt[k], m);
          paramArrayOfString[k] = new String(arrayOfChar);
          int n = paramArrayOfString[k].indexOf("");
          if (n > 0)
            paramArrayOfString[k] = paramArrayOfString[k].substring(0, n);
        }
        COM.SysFreeString(arrayOfInt[k]);
      }
    return j;
  }

  public int GetRefTypeInfo(int paramInt, int[] paramArrayOfInt)
  {
    return COM.VtblCall(14, this.address, paramInt, paramArrayOfInt);
  }

  public int GetRefTypeOfImplType(int paramInt, int[] paramArrayOfInt)
  {
    return COM.VtblCall(8, this.address, paramInt, paramArrayOfInt);
  }

  public int GetTypeAttr(int[] paramArrayOfInt)
  {
    return COM.VtblCall(3, this.address, paramArrayOfInt);
  }

  public int GetVarDesc(int paramInt, int[] paramArrayOfInt)
  {
    return COM.VtblCall(6, this.address, paramInt, paramArrayOfInt);
  }

  public int ReleaseFuncDesc(int paramInt)
  {
    return COM.VtblCall(20, this.address, paramInt);
  }

  public int ReleaseTypeAttr(int paramInt)
  {
    return COM.VtblCall(19, this.address, paramInt);
  }

  public int ReleaseVarDesc(int paramInt)
  {
    return COM.VtblCall(21, this.address, paramInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.ole.win32.ITypeInfo
 * JD-Core Version:    0.6.2
 */