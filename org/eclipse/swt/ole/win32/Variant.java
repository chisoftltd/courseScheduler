package org.eclipse.swt.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.IDispatch;
import org.eclipse.swt.internal.ole.win32.IUnknown;
import org.eclipse.swt.internal.ole.win32.VARIANT;
import org.eclipse.swt.internal.win32.OS;

public final class Variant
{
  public static final int sizeof = VARIANT.sizeof;
  private short type;
  private boolean booleanData;
  private byte byteData;
  private short shortData;
  private char charData;
  private int intData;
  private long longData;
  private float floatData;
  private double doubleData;
  private String stringData;
  private int byRefPtr;
  private IDispatch dispatchData;
  private IUnknown unknownData;

  public static void win32_copy(int paramInt, Variant paramVariant)
  {
    paramVariant.getData(paramInt);
  }

  public static Variant win32_new(int paramInt)
  {
    Variant localVariant = new Variant();
    localVariant.setData(paramInt);
    return localVariant;
  }

  public Variant()
  {
    this.type = 0;
  }

  public Variant(float paramFloat)
  {
    this.type = 4;
    this.floatData = paramFloat;
  }

  public Variant(double paramDouble)
  {
    this.type = 5;
    this.doubleData = paramDouble;
  }

  public Variant(int paramInt)
  {
    this.type = 3;
    this.intData = paramInt;
  }

  public Variant(int paramInt, short paramShort)
  {
    this.type = paramShort;
    this.byRefPtr = paramInt;
  }

  public Variant(OleAutomation paramOleAutomation)
  {
    this.type = 9;
    this.dispatchData = new IDispatch(paramOleAutomation.getAddress());
  }

  public Variant(IDispatch paramIDispatch)
  {
    this.type = 9;
    this.dispatchData = paramIDispatch;
  }

  public Variant(IUnknown paramIUnknown)
  {
    this.type = 13;
    this.unknownData = paramIUnknown;
  }

  public Variant(long paramLong)
  {
    this.type = 20;
    this.longData = paramLong;
  }

  public Variant(String paramString)
  {
    this.type = 8;
    this.stringData = paramString;
  }

  public Variant(short paramShort)
  {
    this.type = 2;
    this.shortData = paramShort;
  }

  public Variant(boolean paramBoolean)
  {
    this.type = 11;
    this.booleanData = paramBoolean;
  }

  public void dispose()
  {
    if ((this.type & 0x4000) == 16384)
      return;
    switch (this.type)
    {
    case 9:
      this.dispatchData.Release();
      break;
    case 13:
      this.unknownData.Release();
    case 10:
    case 11:
    case 12:
    }
  }

  public OleAutomation getAutomation()
  {
    if (this.type == 0)
      OLE.error(1010, -1);
    if (this.type == 9)
      return new OleAutomation(this.dispatchData);
    int i = OS.GlobalAlloc(64, sizeof);
    int j = OS.GlobalAlloc(64, sizeof);
    try
    {
      getData(i);
      int k = COM.VariantChangeType(j, i, (short)0, (short)9);
      if (k != 0)
        OLE.error(1010, k);
      Variant localVariant = new Variant();
      localVariant.setData(j);
      OleAutomation localOleAutomation = localVariant.getAutomation();
      return localOleAutomation;
    }
    finally
    {
      COM.VariantClear(i);
      OS.GlobalFree(i);
      COM.VariantClear(j);
      OS.GlobalFree(j);
    }
  }

  public IDispatch getDispatch()
  {
    if (this.type == 0)
      OLE.error(1010, -1);
    if (this.type == 9)
      return this.dispatchData;
    int i = OS.GlobalAlloc(64, sizeof);
    int j = OS.GlobalAlloc(64, sizeof);
    try
    {
      getData(i);
      int k = COM.VariantChangeType(j, i, (short)0, (short)9);
      if (k != 0)
        OLE.error(1010, k);
      Variant localVariant = new Variant();
      localVariant.setData(j);
      IDispatch localIDispatch = localVariant.getDispatch();
      return localIDispatch;
    }
    finally
    {
      COM.VariantClear(i);
      OS.GlobalFree(i);
      COM.VariantClear(j);
      OS.GlobalFree(j);
    }
  }

  public boolean getBoolean()
  {
    if (this.type == 0)
      OLE.error(1010, -1);
    if (this.type == 11)
      return this.booleanData;
    int i = OS.GlobalAlloc(64, sizeof);
    int j = OS.GlobalAlloc(64, sizeof);
    try
    {
      getData(i);
      int k = COM.VariantChangeType(j, i, (short)0, (short)11);
      if (k != 0)
        OLE.error(1010, k);
      Variant localVariant = new Variant();
      localVariant.setData(j);
      boolean bool = localVariant.getBoolean();
      return bool;
    }
    finally
    {
      COM.VariantClear(i);
      OS.GlobalFree(i);
      COM.VariantClear(j);
      OS.GlobalFree(j);
    }
  }

  public int getByRef()
  {
    if (this.type == 0)
      OLE.error(1010, -1);
    if ((this.type & 0x4000) == 16384)
      return this.byRefPtr;
    return 0;
  }

  public byte getByte()
  {
    if (this.type == 0)
      OLE.error(1010, -1);
    if (this.type == 16)
      return this.byteData;
    int i = OS.GlobalAlloc(64, sizeof);
    int j = OS.GlobalAlloc(64, sizeof);
    try
    {
      getData(i);
      int k = COM.VariantChangeType(j, i, (short)0, (short)16);
      if (k != 0)
        OLE.error(1010, k);
      Variant localVariant = new Variant();
      localVariant.setData(j);
      byte b = localVariant.getByte();
      return b;
    }
    finally
    {
      COM.VariantClear(i);
      OS.GlobalFree(i);
      COM.VariantClear(j);
      OS.GlobalFree(j);
    }
  }

  public char getChar()
  {
    if (this.type == 0)
      OLE.error(1010, -1);
    if (this.type == 18)
      return this.charData;
    int i = OS.GlobalAlloc(64, sizeof);
    int j = OS.GlobalAlloc(64, sizeof);
    try
    {
      getData(i);
      int k = COM.VariantChangeType(j, i, (short)0, (short)18);
      if (k != 0)
        OLE.error(1010, k);
      Variant localVariant = new Variant();
      localVariant.setData(j);
      char c = localVariant.getChar();
      return c;
    }
    finally
    {
      COM.VariantClear(i);
      OS.GlobalFree(i);
      COM.VariantClear(j);
      OS.GlobalFree(j);
    }
  }

  void getData(int paramInt)
  {
    if (paramInt == 0)
      OLE.error(1007);
    COM.VariantInit(paramInt);
    if ((this.type & 0x4000) == 16384)
    {
      COM.MoveMemory(paramInt, new short[] { this.type }, 2);
      COM.MoveMemory(paramInt + 8, new int[] { this.byRefPtr }, OS.PTR_SIZEOF);
      return;
    }
    switch (this.type)
    {
    case 0:
    case 1:
      COM.MoveMemory(paramInt, new short[] { this.type }, 2);
      break;
    case 11:
      COM.MoveMemory(paramInt, new short[] { this.type }, 2);
      COM.MoveMemory(paramInt + 8, new short[] { this.booleanData ? -1 : 0 }, 2);
      break;
    case 16:
      COM.MoveMemory(paramInt, new short[] { this.type }, 2);
      COM.MoveMemory(paramInt + 8, new byte[] { this.byteData }, 1);
      break;
    case 2:
      COM.MoveMemory(paramInt, new short[] { this.type }, 2);
      COM.MoveMemory(paramInt + 8, new short[] { this.shortData }, 2);
      break;
    case 18:
      COM.MoveMemory(paramInt, new short[] { this.type }, 2);
      COM.MoveMemory(paramInt + 8, new char[] { this.charData }, 2);
      break;
    case 3:
      COM.MoveMemory(paramInt, new short[] { this.type }, 2);
      COM.MoveMemory(paramInt + 8, new int[] { this.intData }, 4);
      break;
    case 20:
      COM.MoveMemory(paramInt, new short[] { this.type }, 2);
      COM.MoveMemory(paramInt + 8, new long[] { this.longData }, 8);
      break;
    case 4:
      COM.MoveMemory(paramInt, new short[] { this.type }, 2);
      COM.MoveMemory(paramInt + 8, new float[] { this.floatData }, 4);
      break;
    case 5:
      COM.MoveMemory(paramInt, new short[] { this.type }, 2);
      COM.MoveMemory(paramInt + 8, new double[] { this.doubleData }, 8);
      break;
    case 9:
      this.dispatchData.AddRef();
      COM.MoveMemory(paramInt, new short[] { this.type }, 2);
      COM.MoveMemory(paramInt + 8, new int[] { this.dispatchData.getAddress() }, OS.PTR_SIZEOF);
      break;
    case 13:
      this.unknownData.AddRef();
      COM.MoveMemory(paramInt, new short[] { this.type }, 2);
      COM.MoveMemory(paramInt + 8, new int[] { this.unknownData.getAddress() }, OS.PTR_SIZEOF);
      break;
    case 8:
      COM.MoveMemory(paramInt, new short[] { this.type }, 2);
      char[] arrayOfChar = (this.stringData + "").toCharArray();
      int i = COM.SysAllocString(arrayOfChar);
      COM.MoveMemory(paramInt + 8, new int[] { i }, OS.PTR_SIZEOF);
      break;
    case 6:
    case 7:
    case 10:
    case 12:
    case 14:
    case 15:
    case 17:
    case 19:
    default:
      OLE.error(20);
    }
  }

  public double getDouble()
  {
    if (this.type == 0)
      OLE.error(1010, -1);
    if (this.type == 5)
      return this.doubleData;
    int i = OS.GlobalAlloc(64, sizeof);
    int j = OS.GlobalAlloc(64, sizeof);
    try
    {
      getData(i);
      int k = COM.VariantChangeType(j, i, (short)0, (short)5);
      if (k != 0)
        OLE.error(1010, k);
      Variant localVariant = new Variant();
      localVariant.setData(j);
      double d = localVariant.getDouble();
      return d;
    }
    finally
    {
      COM.VariantClear(i);
      OS.GlobalFree(i);
      COM.VariantClear(j);
      OS.GlobalFree(j);
    }
  }

  public float getFloat()
  {
    if (this.type == 0)
      OLE.error(1010, -1);
    if (this.type == 4)
      return this.floatData;
    int i = OS.GlobalAlloc(64, sizeof);
    int j = OS.GlobalAlloc(64, sizeof);
    try
    {
      getData(i);
      int k = COM.VariantChangeType(j, i, (short)0, (short)4);
      if (k != 0)
        OLE.error(1010, k);
      Variant localVariant = new Variant();
      localVariant.setData(j);
      float f = localVariant.getFloat();
      return f;
    }
    finally
    {
      COM.VariantClear(i);
      OS.GlobalFree(i);
      COM.VariantClear(j);
      OS.GlobalFree(j);
    }
  }

  public int getInt()
  {
    if (this.type == 0)
      OLE.error(1010, -1);
    if (this.type == 3)
      return this.intData;
    int i = OS.GlobalAlloc(64, sizeof);
    int j = OS.GlobalAlloc(64, sizeof);
    try
    {
      getData(i);
      int k = COM.VariantChangeType(j, i, (short)0, (short)3);
      if (k != 0)
        OLE.error(1010, k);
      Variant localVariant = new Variant();
      localVariant.setData(j);
      int m = localVariant.getInt();
      return m;
    }
    finally
    {
      COM.VariantClear(i);
      OS.GlobalFree(i);
      COM.VariantClear(j);
      OS.GlobalFree(j);
    }
  }

  public long getLong()
  {
    if (this.type == 0)
      OLE.error(1010, -1);
    if (this.type == 20)
      return this.longData;
    int i = OS.GlobalAlloc(64, sizeof);
    int j = OS.GlobalAlloc(64, sizeof);
    try
    {
      getData(i);
      int k = COM.VariantChangeType(j, i, (short)0, (short)20);
      if (k != 0)
        OLE.error(1010, k);
      Variant localVariant = new Variant();
      localVariant.setData(j);
      long l = localVariant.getLong();
      return l;
    }
    finally
    {
      COM.VariantClear(i);
      OS.GlobalFree(i);
      COM.VariantClear(j);
      OS.GlobalFree(j);
    }
  }

  public short getShort()
  {
    if (this.type == 0)
      OLE.error(1010, -1);
    if (this.type == 2)
      return this.shortData;
    int i = OS.GlobalAlloc(64, sizeof);
    int j = OS.GlobalAlloc(64, sizeof);
    try
    {
      getData(i);
      int k = COM.VariantChangeType(j, i, (short)0, (short)2);
      if (k != 0)
        OLE.error(1010, k);
      Variant localVariant = new Variant();
      localVariant.setData(j);
      short s = localVariant.getShort();
      return s;
    }
    finally
    {
      COM.VariantClear(i);
      OS.GlobalFree(i);
      COM.VariantClear(j);
      OS.GlobalFree(j);
    }
  }

  public String getString()
  {
    if (this.type == 0)
      OLE.error(1010, -1);
    if (this.type == 8)
      return this.stringData;
    int i = OS.GlobalAlloc(64, sizeof);
    int j = OS.GlobalAlloc(64, sizeof);
    try
    {
      getData(i);
      int k = COM.VariantChangeType(j, i, (short)0, (short)8);
      if (k != 0)
        OLE.error(1010, k);
      Variant localVariant = new Variant();
      localVariant.setData(j);
      String str = localVariant.getString();
      return str;
    }
    finally
    {
      COM.VariantClear(i);
      OS.GlobalFree(i);
      COM.VariantClear(j);
      OS.GlobalFree(j);
    }
  }

  public short getType()
  {
    return this.type;
  }

  public IUnknown getUnknown()
  {
    if (this.type == 0)
      OLE.error(1010, -1);
    if (this.type == 13)
      return this.unknownData;
    int i = OS.GlobalAlloc(64, sizeof);
    int j = OS.GlobalAlloc(64, sizeof);
    try
    {
      getData(i);
      int k = COM.VariantChangeType(j, i, (short)0, (short)13);
      if (k != 0)
        OLE.error(1010, k);
      Variant localVariant = new Variant();
      localVariant.setData(j);
      IUnknown localIUnknown = localVariant.getUnknown();
      return localIUnknown;
    }
    finally
    {
      COM.VariantClear(i);
      OS.GlobalFree(i);
      COM.VariantClear(j);
      OS.GlobalFree(j);
    }
  }

  public void setByRef(boolean paramBoolean)
  {
    if (((this.type & 0x4000) == 0) || ((this.type & 0xB) == 0))
      OLE.error(1010);
    COM.MoveMemory(this.byRefPtr, new short[] { paramBoolean ? -1 : 0 }, 2);
  }

  public void setByRef(float paramFloat)
  {
    if (((this.type & 0x4000) == 0) || ((this.type & 0x4) == 0))
      OLE.error(1010);
    COM.MoveMemory(this.byRefPtr, new float[] { paramFloat }, 4);
  }

  public void setByRef(int paramInt)
  {
    if (((this.type & 0x4000) == 0) || ((this.type & 0x3) == 0))
      OLE.error(1010);
    COM.MoveMemory(this.byRefPtr, new int[] { paramInt }, OS.PTR_SIZEOF);
  }

  public void setByRef(short paramShort)
  {
    if (((this.type & 0x4000) == 0) || ((this.type & 0x2) == 0))
      OLE.error(1010);
    COM.MoveMemory(this.byRefPtr, new short[] { paramShort }, 2);
  }

  void setData(int paramInt)
  {
    if (paramInt == 0)
      OLE.error(5);
    short[] arrayOfShort1 = new short[1];
    COM.MoveMemory(arrayOfShort1, paramInt, 2);
    this.type = arrayOfShort1[0];
    Object localObject;
    if ((this.type & 0x4000) == 16384)
    {
      localObject = new int[1];
      OS.MoveMemory((int[])localObject, paramInt + 8, OS.PTR_SIZEOF);
      this.byRefPtr = localObject[0];
      return;
    }
    int[] arrayOfInt2;
    switch (this.type)
    {
    case 0:
    case 1:
      break;
    case 11:
      localObject = new short[1];
      COM.MoveMemory((short[])localObject, paramInt + 8, 2);
      this.booleanData = (localObject[0] != 0);
      break;
    case 16:
      byte[] arrayOfByte = new byte[1];
      COM.MoveMemory(arrayOfByte, paramInt + 8, 1);
      this.byteData = arrayOfByte[0];
      break;
    case 2:
      short[] arrayOfShort2 = new short[1];
      COM.MoveMemory(arrayOfShort2, paramInt + 8, 2);
      this.shortData = arrayOfShort2[0];
      break;
    case 18:
      char[] arrayOfChar1 = new char[1];
      COM.MoveMemory(arrayOfChar1, paramInt + 8, 2);
      this.charData = arrayOfChar1[0];
      break;
    case 3:
      int[] arrayOfInt1 = new int[1];
      OS.MoveMemory(arrayOfInt1, paramInt + 8, 4);
      this.intData = arrayOfInt1[0];
      break;
    case 20:
      long[] arrayOfLong = new long[1];
      OS.MoveMemory(arrayOfLong, paramInt + 8, 8);
      this.longData = arrayOfLong[0];
      break;
    case 4:
      float[] arrayOfFloat = new float[1];
      COM.MoveMemory(arrayOfFloat, paramInt + 8, 4);
      this.floatData = arrayOfFloat[0];
      break;
    case 5:
      double[] arrayOfDouble = new double[1];
      COM.MoveMemory(arrayOfDouble, paramInt + 8, 8);
      this.doubleData = arrayOfDouble[0];
      break;
    case 9:
      arrayOfInt2 = new int[1];
      OS.MoveMemory(arrayOfInt2, paramInt + 8, OS.PTR_SIZEOF);
      if (arrayOfInt2[0] == 0)
      {
        this.type = 0;
      }
      else
      {
        this.dispatchData = new IDispatch(arrayOfInt2[0]);
        this.dispatchData.AddRef();
      }
      break;
    case 13:
      arrayOfInt2 = new int[1];
      OS.MoveMemory(arrayOfInt2, paramInt + 8, OS.PTR_SIZEOF);
      if (arrayOfInt2[0] == 0)
      {
        this.type = 0;
      }
      else
      {
        this.unknownData = new IUnknown(arrayOfInt2[0]);
        this.unknownData.AddRef();
      }
      break;
    case 8:
      arrayOfInt2 = new int[1];
      OS.MoveMemory(arrayOfInt2, paramInt + 8, OS.PTR_SIZEOF);
      if (arrayOfInt2[0] == 0)
      {
        this.type = 0;
      }
      else
      {
        int i = COM.SysStringByteLen(arrayOfInt2[0]);
        if (i > 0)
        {
          char[] arrayOfChar2 = new char[(i + 1) / 2];
          COM.MoveMemory(arrayOfChar2, arrayOfInt2[0], i);
          this.stringData = new String(arrayOfChar2);
        }
        else
        {
          this.stringData = "";
        }
      }
      break;
    case 6:
    case 7:
    case 10:
    case 12:
    case 14:
    case 15:
    case 17:
    case 19:
    default:
      int j = OS.GlobalAlloc(64, sizeof);
      if (COM.VariantChangeType(j, paramInt, (short)0, (short)4) == 0)
        setData(j);
      else if (COM.VariantChangeType(j, paramInt, (short)0, (short)3) == 0)
        setData(j);
      else if (COM.VariantChangeType(j, paramInt, (short)0, (short)8) == 0)
        setData(j);
      COM.VariantClear(j);
      OS.GlobalFree(j);
    }
  }

  public String toString()
  {
    switch (this.type)
    {
    case 11:
      return "VT_BOOL{" + this.booleanData + "}";
    case 16:
      return "VT_I1{" + this.byteData + "}";
    case 2:
      return "VT_I2{" + this.shortData + "}";
    case 18:
      return "VT_UI2{" + this.charData + "}";
    case 3:
      return "VT_I4{" + this.intData + "}";
    case 20:
      return "VT_I8{" + this.longData + "}";
    case 4:
      return "VT_R4{" + this.floatData + "}";
    case 5:
      return "VT_R8{" + this.doubleData + "}";
    case 8:
      return "VT_BSTR{" + this.stringData + "}";
    case 9:
      return "VT_DISPATCH{" + (this.dispatchData == null ? 0 : this.dispatchData.getAddress()) + "}";
    case 13:
      return "VT_UNKNOWN{" + (this.unknownData == null ? 0 : this.unknownData.getAddress()) + "}";
    case 0:
      return "VT_EMPTY";
    case 1:
      return "VT_NULL";
    case 6:
    case 7:
    case 10:
    case 12:
    case 14:
    case 15:
    case 17:
    case 19:
    }
    if ((this.type & 0x4000) != 0)
      return "VT_BYREF|" + (this.type & 0xFFFFBFFF) + "{" + this.byRefPtr + "}";
    return "Unsupported Type " + this.type;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.ole.win32.Variant
 * JD-Core Version:    0.6.2
 */