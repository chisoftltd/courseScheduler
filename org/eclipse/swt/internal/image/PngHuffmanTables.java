package org.eclipse.swt.internal.image;

import java.io.IOException;

public class PngHuffmanTables
{
  PngHuffmanTable literalTable;
  PngHuffmanTable distanceTable;
  static PngHuffmanTable FixedLiteralTable;
  static PngHuffmanTable FixedDistanceTable;
  static final int LiteralTableSize = 288;
  static final int[] FixedLiteralLengths = { 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 8, 8, 8, 8, 8, 8, 8, 8 };
  static final int DistanceTableSize = 32;
  static final int[] FixedDistanceLengths = { 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5 };
  static final int LengthCodeTableSize = 19;
  static final int[] LengthCodeOrder = { 16, 17, 18, 0, 8, 7, 9, 6, 10, 5, 11, 4, 12, 3, 13, 2, 14, 1, 15 };

  static PngHuffmanTables getDynamicTables(PngDecodingDataStream paramPngDecodingDataStream)
    throws IOException
  {
    return new PngHuffmanTables(paramPngDecodingDataStream);
  }

  static PngHuffmanTables getFixedTables()
  {
    return new PngHuffmanTables();
  }

  private PngHuffmanTable getFixedLiteralTable()
  {
    if (FixedLiteralTable == null)
      FixedLiteralTable = new PngHuffmanTable(FixedLiteralLengths);
    return FixedLiteralTable;
  }

  private PngHuffmanTable getFixedDistanceTable()
  {
    if (FixedDistanceTable == null)
      FixedDistanceTable = new PngHuffmanTable(FixedDistanceLengths);
    return FixedDistanceTable;
  }

  private PngHuffmanTables()
  {
    this.literalTable = getFixedLiteralTable();
    this.distanceTable = getFixedDistanceTable();
  }

  private PngHuffmanTables(PngDecodingDataStream paramPngDecodingDataStream)
    throws IOException
  {
    int i = 257 + paramPngDecodingDataStream.getNextIdatBits(5);
    int j = 1 + paramPngDecodingDataStream.getNextIdatBits(5);
    int k = 4 + paramPngDecodingDataStream.getNextIdatBits(4);
    if (k > 19)
      paramPngDecodingDataStream.error();
    int[] arrayOfInt1 = new int[19];
    for (int m = 0; m < k; m++)
      arrayOfInt1[LengthCodeOrder[m]] = paramPngDecodingDataStream.getNextIdatBits(3);
    PngHuffmanTable localPngHuffmanTable = new PngHuffmanTable(arrayOfInt1);
    int[] arrayOfInt2 = readLengths(paramPngDecodingDataStream, i, localPngHuffmanTable, 288);
    int[] arrayOfInt3 = readLengths(paramPngDecodingDataStream, j, localPngHuffmanTable, 32);
    this.literalTable = new PngHuffmanTable(arrayOfInt2);
    this.distanceTable = new PngHuffmanTable(arrayOfInt3);
  }

  private int[] readLengths(PngDecodingDataStream paramPngDecodingDataStream, int paramInt1, PngHuffmanTable paramPngHuffmanTable, int paramInt2)
    throws IOException
  {
    int[] arrayOfInt = new int[paramInt2];
    int i = 0;
    while (i < paramInt1)
    {
      int j = paramPngHuffmanTable.getNextValue(paramPngDecodingDataStream);
      if (j < 16)
      {
        arrayOfInt[i] = j;
        i++;
      }
      else
      {
        int k;
        int m;
        if (j == 16)
        {
          k = paramPngDecodingDataStream.getNextIdatBits(2) + 3;
          for (m = 0; m < k; m++)
          {
            arrayOfInt[i] = arrayOfInt[(i - 1)];
            i++;
          }
        }
        else if (j == 17)
        {
          k = paramPngDecodingDataStream.getNextIdatBits(3) + 3;
          for (m = 0; m < k; m++)
          {
            arrayOfInt[i] = 0;
            i++;
          }
        }
        else if (j == 18)
        {
          k = paramPngDecodingDataStream.getNextIdatBits(7) + 11;
          for (m = 0; m < k; m++)
          {
            arrayOfInt[i] = 0;
            i++;
          }
        }
        else
        {
          paramPngDecodingDataStream.error();
        }
      }
    }
    return arrayOfInt;
  }

  int getNextLiteralValue(PngDecodingDataStream paramPngDecodingDataStream)
    throws IOException
  {
    return this.literalTable.getNextValue(paramPngDecodingDataStream);
  }

  int getNextDistanceValue(PngDecodingDataStream paramPngDecodingDataStream)
    throws IOException
  {
    return this.distanceTable.getNextValue(paramPngDecodingDataStream);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.image.PngHuffmanTables
 * JD-Core Version:    0.6.2
 */