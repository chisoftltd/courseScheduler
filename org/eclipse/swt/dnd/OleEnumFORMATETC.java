package org.eclipse.swt.dnd;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.COMObject;
import org.eclipse.swt.internal.ole.win32.FORMATETC;
import org.eclipse.swt.internal.ole.win32.GUID;
import org.eclipse.swt.internal.win32.OS;

final class OleEnumFORMATETC
{
  private COMObject iUnknown;
  private COMObject iEnumFORMATETC;
  private int refCount;
  private int index;
  private FORMATETC[] formats;

  OleEnumFORMATETC()
  {
    createCOMInterfaces();
  }

  int AddRef()
  {
    this.refCount += 1;
    return this.refCount;
  }

  private void createCOMInterfaces()
  {
    this.iUnknown = new COMObject(new int[] { 2 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return OleEnumFORMATETC.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return OleEnumFORMATETC.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return OleEnumFORMATETC.this.Release();
      }
    };
    this.iEnumFORMATETC = new COMObject(new int[] { 2, 0, 0, 3, 1, 0, 1 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return OleEnumFORMATETC.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return OleEnumFORMATETC.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return OleEnumFORMATETC.this.Release();
      }

      public int method3(int[] paramAnonymousArrayOfInt)
      {
        return OleEnumFORMATETC.this.Next(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }

      public int method4(int[] paramAnonymousArrayOfInt)
      {
        return OleEnumFORMATETC.this.Skip(paramAnonymousArrayOfInt[0]);
      }

      public int method5(int[] paramAnonymousArrayOfInt)
      {
        return OleEnumFORMATETC.this.Reset();
      }
    };
  }

  private void disposeCOMInterfaces()
  {
    if (this.iUnknown != null)
      this.iUnknown.dispose();
    this.iUnknown = null;
    if (this.iEnumFORMATETC != null)
      this.iEnumFORMATETC.dispose();
    this.iEnumFORMATETC = null;
  }

  int getAddress()
  {
    return this.iEnumFORMATETC.getAddress();
  }

  private FORMATETC[] getNextItems(int paramInt)
  {
    if ((this.formats == null) || (paramInt < 1))
      return null;
    int i = this.index + paramInt - 1;
    if (i > this.formats.length - 1)
      i = this.formats.length - 1;
    if (this.index > i)
      return null;
    FORMATETC[] arrayOfFORMATETC = new FORMATETC[i - this.index + 1];
    for (int j = 0; j < arrayOfFORMATETC.length; j++)
    {
      arrayOfFORMATETC[j] = this.formats[this.index];
      this.index += 1;
    }
    return arrayOfFORMATETC;
  }

  private int Next(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt2 == 0)
      return -2147024809;
    if ((paramInt3 == 0) && (paramInt1 != 1))
      return -2147024809;
    FORMATETC[] arrayOfFORMATETC = getNextItems(paramInt1);
    if (arrayOfFORMATETC != null)
    {
      for (int i = 0; i < arrayOfFORMATETC.length; i++)
        COM.MoveMemory(paramInt2 + i * FORMATETC.sizeof, arrayOfFORMATETC[i], FORMATETC.sizeof);
      if (paramInt3 != 0)
        COM.MoveMemory(paramInt3, new int[] { arrayOfFORMATETC.length }, 4);
      if (arrayOfFORMATETC.length == paramInt1)
        return 0;
    }
    else
    {
      if (paramInt3 != 0)
        COM.MoveMemory(paramInt3, new int[1], 4);
      COM.MoveMemory(paramInt2, new FORMATETC(), FORMATETC.sizeof);
    }
    return 1;
  }

  private int QueryInterface(int paramInt1, int paramInt2)
  {
    if ((paramInt1 == 0) || (paramInt2 == 0))
      return -2147467262;
    GUID localGUID = new GUID();
    COM.MoveMemory(localGUID, paramInt1, GUID.sizeof);
    if (COM.IsEqualGUID(localGUID, COM.IIDIUnknown))
    {
      COM.MoveMemory(paramInt2, new int[] { this.iUnknown.getAddress() }, OS.PTR_SIZEOF);
      AddRef();
      return 0;
    }
    if (COM.IsEqualGUID(localGUID, COM.IIDIEnumFORMATETC))
    {
      COM.MoveMemory(paramInt2, new int[] { this.iEnumFORMATETC.getAddress() }, OS.PTR_SIZEOF);
      AddRef();
      return 0;
    }
    COM.MoveMemory(paramInt2, new int[1], OS.PTR_SIZEOF);
    return -2147467262;
  }

  int Release()
  {
    this.refCount -= 1;
    if (this.refCount == 0)
    {
      disposeCOMInterfaces();
      if (COM.FreeUnusedLibraries)
        COM.CoFreeUnusedLibraries();
    }
    return this.refCount;
  }

  private int Reset()
  {
    this.index = 0;
    return 0;
  }

  void setFormats(FORMATETC[] paramArrayOfFORMATETC)
  {
    this.formats = paramArrayOfFORMATETC;
    this.index = 0;
  }

  private int Skip(int paramInt)
  {
    if (paramInt < 1)
      return -2147024809;
    this.index += paramInt;
    if (this.index > this.formats.length - 1)
    {
      this.index = (this.formats.length - 1);
      return 1;
    }
    return 0;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.dnd.OleEnumFORMATETC
 * JD-Core Version:    0.6.2
 */