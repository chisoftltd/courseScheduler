package org.eclipse.swt.internal.image;

final class JPEGQuantizationTable extends JPEGVariableSizeSegment
{
  public static byte[] DefaultLuminanceQTable = { -1, -37, 0, 67, 0, 16, 11, 10, 16, 24, 40, 51, 61, 12, 12, 14, 19, 26, 58, 60, 55, 14, 13, 16, 24, 40, 57, 69, 56, 14, 17, 22, 29, 51, 87, 80, 62, 18, 22, 37, 56, 68, 109, 103, 77, 24, 35, 55, 64, 81, 104, 113, 92, 49, 64, 78, 87, 103, 121, 120, 101, 72, 92, 95, 98, 112, 100, 103, 99 };
  public static byte[] DefaultChrominanceQTable = { -1, -37, 0, 67, 1, 17, 18, 24, 47, 99, 99, 99, 99, 18, 21, 26, 66, 99, 99, 99, 99, 24, 26, 56, 99, 99, 99, 99, 99, 47, 66, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99 };

  public JPEGQuantizationTable(byte[] paramArrayOfByte)
  {
    super(paramArrayOfByte);
  }

  public JPEGQuantizationTable(LEDataInputStream paramLEDataInputStream)
  {
    super(paramLEDataInputStream);
  }

  public static JPEGQuantizationTable defaultChrominanceTable()
  {
    byte[] arrayOfByte = new byte[DefaultChrominanceQTable.length];
    System.arraycopy(DefaultChrominanceQTable, 0, arrayOfByte, 0, arrayOfByte.length);
    return new JPEGQuantizationTable(arrayOfByte);
  }

  public static JPEGQuantizationTable defaultLuminanceTable()
  {
    byte[] arrayOfByte = new byte[DefaultLuminanceQTable.length];
    System.arraycopy(DefaultLuminanceQTable, 0, arrayOfByte, 0, arrayOfByte.length);
    return new JPEGQuantizationTable(arrayOfByte);
  }

  public int[] getQuantizationTablesKeys()
  {
    Object localObject = new int[4];
    int i = 0;
    int j = getSegmentLength() - 2;
    int k = 4;
    while (j > 64)
    {
      int m = this.reference[k] & 0xF;
      int n = (this.reference[k] & 0xFF) >> 4;
      if (n == 0)
      {
        k += 65;
        j -= 65;
      }
      else
      {
        k += 129;
        j -= 129;
      }
      if (i >= localObject.length)
      {
        int[] arrayOfInt2 = new int[localObject.length + 4];
        System.arraycopy(localObject, 0, arrayOfInt2, 0, localObject.length);
        localObject = arrayOfInt2;
      }
      localObject[i] = m;
      i++;
    }
    int[] arrayOfInt1 = new int[i];
    System.arraycopy(localObject, 0, arrayOfInt1, 0, i);
    return arrayOfInt1;
  }

  public int[][] getQuantizationTablesValues()
  {
    Object localObject1 = new int[4][];
    int i = 0;
    int j = getSegmentLength() - 2;
    int k = 4;
    while (j > 64)
    {
      localObject2 = new int[64];
      int m = (this.reference[k] & 0xFF) >> 4;
      int n;
      if (m == 0)
      {
        for (n = 0; n < localObject2.length; n++)
          localObject2[n] = (this.reference[(k + n + 1)] & 0xFF);
        k += 65;
        j -= 65;
      }
      else
      {
        for (n = 0; n < localObject2.length; n++)
        {
          int i1 = (n - 1) * 2;
          localObject2[n] = ((this.reference[(k + i1 + 1)] & 0xFF) * 256 + (this.reference[(k + i1 + 2)] & 0xFF));
        }
        k += 129;
        j -= 129;
      }
      if (i >= localObject1.length)
      {
        int[][] arrayOfInt = new int[localObject1.length + 4][];
        System.arraycopy(localObject1, 0, arrayOfInt, 0, localObject1.length);
        localObject1 = arrayOfInt;
      }
      localObject1[i] = localObject2;
      i++;
    }
    Object localObject2 = new int[i][];
    System.arraycopy(localObject1, 0, localObject2, 0, i);
    return localObject2;
  }

  public void scaleBy(int paramInt)
  {
    int i = paramInt;
    if (i <= 0)
      i = 1;
    if (i > 100)
      i = 100;
    if (i < 50)
      i = 5000 / i;
    else
      i = 200 - i * 2;
    int j = getSegmentLength() - 2;
    int k = 4;
    while (j > 64)
    {
      int m = (this.reference[k] & 0xFF) >> 4;
      int n;
      int i1;
      if (m == 0)
      {
        for (n = k + 1; n <= k + 64; n++)
        {
          i1 = ((this.reference[n] & 0xFF) * i + 50) / 100;
          if (i1 <= 0)
            i1 = 1;
          if (i1 > 255)
            i1 = 255;
          this.reference[n] = ((byte)i1);
        }
        k += 65;
        j -= 65;
      }
      else
      {
        for (n = k + 1; n <= k + 128; n += 2)
        {
          i1 = (((this.reference[n] & 0xFF) * 256 + (this.reference[(n + 1)] & 0xFF)) * i + 50) / 100;
          if (i1 <= 0)
            i1 = 1;
          if (i1 > 32767)
            i1 = 32767;
          this.reference[n] = ((byte)(i1 >> 8));
          this.reference[(n + 1)] = ((byte)(i1 & 0xFF));
        }
        k += 129;
        j -= 129;
      }
    }
  }

  public int signature()
  {
    return 65499;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.image.JPEGQuantizationTable
 * JD-Core Version:    0.6.2
 */