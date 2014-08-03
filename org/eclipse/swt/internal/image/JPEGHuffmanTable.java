package org.eclipse.swt.internal.image;

final class JPEGHuffmanTable extends JPEGVariableSizeSegment
{
  JPEGHuffmanTable[] allTables;
  int tableClass;
  int tableIdentifier;
  int[] dhMaxCodes;
  int[] dhMinCodes;
  int[] dhValPtrs;
  int[] dhValues;
  int[] ehCodes;
  byte[] ehCodeLengths;
  static byte[] DCLuminanceTable = { -1, -60, 0, 31, 0, 0, 1, 5, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };
  static byte[] DCChrominanceTable = { -1, -60, 0, 31, 1, 0, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };
  static byte[] ACLuminanceTable = { -1, -60, 0, -75, 16, 0, 2, 1, 3, 3, 2, 4, 3, 5, 5, 4, 4, 0, 0, 1, 125, 1, 2, 3, 0, 4, 17, 5, 18, 33, 49, 65, 6, 19, 81, 97, 7, 34, 113, 20, 50, -127, -111, -95, 8, 35, 66, -79, -63, 21, 82, -47, -16, 36, 51, 98, 114, -126, 9, 10, 22, 23, 24, 25, 26, 37, 38, 39, 40, 41, 42, 52, 53, 54, 55, 56, 57, 58, 67, 68, 69, 70, 71, 72, 73, 74, 83, 84, 85, 86, 87, 88, 89, 90, 99, 100, 101, 102, 103, 104, 105, 106, 115, 116, 117, 118, 119, 120, 121, 122, -125, -124, -123, -122, -121, -120, -119, -118, -110, -109, -108, -107, -106, -105, -104, -103, -102, -94, -93, -92, -91, -90, -89, -88, -87, -86, -78, -77, -76, -75, -74, -73, -72, -71, -70, -62, -61, -60, -59, -58, -57, -56, -55, -54, -46, -45, -44, -43, -42, -41, -40, -39, -38, -31, -30, -29, -28, -27, -26, -25, -24, -23, -22, -15, -14, -13, -12, -11, -10, -9, -8, -7, -6 };
  static byte[] ACChrominanceTable = { -1, -60, 0, -75, 17, 0, 2, 1, 2, 4, 4, 3, 4, 7, 5, 4, 4, 0, 1, 2, 119, 0, 1, 2, 3, 17, 4, 5, 33, 49, 6, 18, 65, 81, 7, 97, 113, 19, 34, 50, -127, 8, 20, 66, -111, -95, -79, -63, 9, 35, 51, 82, -16, 21, 98, 114, -47, 10, 22, 36, 52, -31, 37, -15, 23, 24, 25, 26, 38, 39, 40, 41, 42, 53, 54, 55, 56, 57, 58, 67, 68, 69, 70, 71, 72, 73, 74, 83, 84, 85, 86, 87, 88, 89, 90, 99, 100, 101, 102, 103, 104, 105, 106, 115, 116, 117, 118, 119, 120, 121, 122, -126, -125, -124, -123, -122, -121, -120, -119, -118, -110, -109, -108, -107, -106, -105, -104, -103, -102, -94, -93, -92, -91, -90, -89, -88, -87, -86, -78, -77, -76, -75, -74, -73, -72, -71, -70, -62, -61, -60, -59, -58, -57, -56, -55, -54, -46, -45, -44, -43, -42, -41, -40, -39, -38, -30, -29, -28, -27, -26, -25, -24, -23, -22, -14, -13, -12, -11, -10, -9, -8, -7, -6 };

  public JPEGHuffmanTable(byte[] paramArrayOfByte)
  {
    super(paramArrayOfByte);
  }

  public JPEGHuffmanTable(LEDataInputStream paramLEDataInputStream)
  {
    super(paramLEDataInputStream);
    initialize();
  }

  public JPEGHuffmanTable[] getAllTables()
  {
    return this.allTables;
  }

  public static JPEGHuffmanTable getDefaultACChrominanceTable()
  {
    JPEGHuffmanTable localJPEGHuffmanTable = new JPEGHuffmanTable(ACChrominanceTable);
    localJPEGHuffmanTable.initialize();
    return localJPEGHuffmanTable;
  }

  public static JPEGHuffmanTable getDefaultACLuminanceTable()
  {
    JPEGHuffmanTable localJPEGHuffmanTable = new JPEGHuffmanTable(ACLuminanceTable);
    localJPEGHuffmanTable.initialize();
    return localJPEGHuffmanTable;
  }

  public static JPEGHuffmanTable getDefaultDCChrominanceTable()
  {
    JPEGHuffmanTable localJPEGHuffmanTable = new JPEGHuffmanTable(DCChrominanceTable);
    localJPEGHuffmanTable.initialize();
    return localJPEGHuffmanTable;
  }

  public static JPEGHuffmanTable getDefaultDCLuminanceTable()
  {
    JPEGHuffmanTable localJPEGHuffmanTable = new JPEGHuffmanTable(DCLuminanceTable);
    localJPEGHuffmanTable.initialize();
    return localJPEGHuffmanTable;
  }

  public int[] getDhMaxCodes()
  {
    return this.dhMaxCodes;
  }

  public int[] getDhMinCodes()
  {
    return this.dhMinCodes;
  }

  public int[] getDhValPtrs()
  {
    return this.dhValPtrs;
  }

  public int[] getDhValues()
  {
    return this.dhValues;
  }

  public int getTableClass()
  {
    return this.tableClass;
  }

  public int getTableIdentifier()
  {
    return this.tableIdentifier;
  }

  void initialize()
  {
    int i = getSegmentLength() - 2;
    int j = 4;
    int[] arrayOfInt1 = new int[16];
    JPEGHuffmanTable[] arrayOfJPEGHuffmanTable = new JPEGHuffmanTable[8];
    for (int k = 0; i > 0; k++)
    {
      int m = (this.reference[j] & 0xFF) >> 4;
      int n = this.reference[j] & 0xF;
      j++;
      int i1 = 0;
      for (int i2 = 0; i2 < arrayOfInt1.length; i2++)
      {
        i3 = this.reference[(j + i2)] & 0xFF;
        arrayOfInt1[i2] = i3;
        i1 += i3;
      }
      j += 16;
      i -= 17;
      int[] arrayOfInt2 = new int[i1];
      for (int i3 = 0; i3 < i1; i3++)
        arrayOfInt2[i3] = (this.reference[(j + i3)] & 0xFF);
      j += i1;
      i -= i1;
      Object localObject1 = new int[50];
      int i4 = 0;
      for (int i5 = 0; i5 < 16; i5++)
        for (i6 = 0; i6 < arrayOfInt1[i5]; i6++)
        {
          if (i4 >= localObject1.length)
          {
            int[] arrayOfInt3 = new int[localObject1.length + 50];
            System.arraycopy(localObject1, 0, arrayOfInt3, 0, localObject1.length);
            localObject1 = arrayOfInt3;
          }
          localObject1[i4] = (i5 + 1);
          i4++;
        }
      if (i4 < localObject1.length)
      {
        localObject2 = new int[i4];
        System.arraycopy(localObject1, 0, localObject2, 0, i4);
        localObject1 = localObject2;
      }
      Object localObject2 = new int[50];
      int i6 = 0;
      int i7 = 1;
      int i8 = 0;
      int i9 = localObject1[0];
      int i10 = 0;
      while (i10 < i4)
      {
        while ((i10 < i4) && (localObject1[i10] == i9))
        {
          if (i6 >= localObject2.length)
          {
            arrayOfInt4 = new int[localObject2.length + 50];
            System.arraycopy(localObject2, 0, arrayOfInt4, 0, localObject2.length);
            localObject2 = arrayOfInt4;
          }
          localObject2[i6] = i8;
          i6++;
          i8++;
          i10++;
        }
        i8 *= 2;
        i9++;
      }
      if (i6 < localObject2.length)
      {
        arrayOfInt4 = new int[i6];
        System.arraycopy(localObject2, 0, arrayOfInt4, 0, i6);
        localObject2 = arrayOfInt4;
      }
      i7 = 0;
      int[] arrayOfInt4 = new int[16];
      int[] arrayOfInt5 = new int[16];
      int[] arrayOfInt6 = new int[16];
      for (int i11 = 0; i11 < 16; i11++)
      {
        int i12 = arrayOfInt1[i11];
        if (i12 == 0)
        {
          arrayOfInt4[i11] = -1;
        }
        else
        {
          arrayOfInt6[i11] = i7;
          arrayOfInt5[i11] = localObject2[i7];
          i7 += i12;
          arrayOfInt4[i11] = localObject2[(i7 - 1)];
        }
      }
      int[] arrayOfInt7 = new int[256];
      byte[] arrayOfByte = new byte[256];
      for (int i13 = 0; i13 < i6; i13++)
      {
        arrayOfInt7[arrayOfInt2[i13]] = localObject2[i13];
        arrayOfByte[arrayOfInt2[i13]] = ((byte)localObject1[i13]);
      }
      JPEGHuffmanTable localJPEGHuffmanTable = new JPEGHuffmanTable(this.reference);
      localJPEGHuffmanTable.tableClass = m;
      localJPEGHuffmanTable.tableIdentifier = n;
      localJPEGHuffmanTable.dhValues = arrayOfInt2;
      localJPEGHuffmanTable.dhMinCodes = arrayOfInt5;
      localJPEGHuffmanTable.dhMaxCodes = arrayOfInt4;
      localJPEGHuffmanTable.dhValPtrs = arrayOfInt6;
      localJPEGHuffmanTable.ehCodes = arrayOfInt7;
      localJPEGHuffmanTable.ehCodeLengths = arrayOfByte;
      arrayOfJPEGHuffmanTable[k] = localJPEGHuffmanTable;
    }
    this.allTables = new JPEGHuffmanTable[k];
    System.arraycopy(arrayOfJPEGHuffmanTable, 0, this.allTables, 0, k);
  }

  public int signature()
  {
    return 65476;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.image.JPEGHuffmanTable
 * JD-Core Version:    0.6.2
 */