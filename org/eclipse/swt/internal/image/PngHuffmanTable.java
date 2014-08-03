package org.eclipse.swt.internal.image;

import java.io.IOException;

public class PngHuffmanTable
{
  CodeLengthInfo[] codeLengthInfo;
  int[] codeValues;
  static final int MAX_CODE_LENGTH = 15;
  static final int BAD_CODE = 268435455;
  static final int[] incs = { 1391376, 463792, 198768, 86961, 33936, 13776, 4592, 1968, 861, 336, 112, 48, 21, 7, 3, 1 };

  PngHuffmanTable(int[] paramArrayOfInt)
  {
    initialize(paramArrayOfInt);
    generateTable(paramArrayOfInt);
  }

  private void initialize(int[] paramArrayOfInt)
  {
    this.codeValues = new int[paramArrayOfInt.length];
    for (int i = 0; i < this.codeValues.length; i++)
      this.codeValues[i] = i;
    this.codeLengthInfo = new CodeLengthInfo[15];
    for (i = 0; i < 15; i++)
    {
      this.codeLengthInfo[i] = new CodeLengthInfo();
      this.codeLengthInfo[i].length = i;
      this.codeLengthInfo[i].baseIndex = 0;
      this.codeLengthInfo[i].min = 268435455;
      this.codeLengthInfo[i].max = -1;
    }
  }

  private void generateTable(int[] paramArrayOfInt)
  {
    for (int j = 0; j < 16; j++)
    {
      k = incs[j];
      for (m = k; m < paramArrayOfInt.length; m++)
      {
        n = paramArrayOfInt[m];
        int i = this.codeValues[m];
        i1 = m;
        while ((i1 >= k) && ((paramArrayOfInt[(i1 - k)] > n) || ((paramArrayOfInt[(i1 - k)] == n) && (this.codeValues[(i1 - k)] > i))))
        {
          paramArrayOfInt[i1] = paramArrayOfInt[(i1 - k)];
          this.codeValues[i1] = this.codeValues[(i1 - k)];
          i1 -= k;
        }
        paramArrayOfInt[i1] = n;
        this.codeValues[i1] = i;
      }
    }
    int[] arrayOfInt = new int[paramArrayOfInt.length];
    int k = 0;
    int m = 0;
    for (int n = 0; n < paramArrayOfInt.length; n++)
    {
      k++;
      while (k != paramArrayOfInt[n])
        m <<= 1;
      if (k != 0)
      {
        arrayOfInt[n] = m;
        m++;
      }
    }
    n = 0;
    for (int i1 = 0; i1 < paramArrayOfInt.length; i1++)
    {
      if (n != paramArrayOfInt[i1])
      {
        n = paramArrayOfInt[i1];
        this.codeLengthInfo[(n - 1)].baseIndex = i1;
        this.codeLengthInfo[(n - 1)].min = arrayOfInt[i1];
      }
      if (n != 0)
        this.codeLengthInfo[(n - 1)].max = arrayOfInt[i1];
    }
  }

  int getNextValue(PngDecodingDataStream paramPngDecodingDataStream)
    throws IOException
  {
    int i = paramPngDecodingDataStream.getNextIdatBit();
    for (int j = 0; (j < 15) && (i > this.codeLengthInfo[j].max); j++)
      i = i << 1 | paramPngDecodingDataStream.getNextIdatBit();
    if (j >= 15)
      paramPngDecodingDataStream.error();
    int k = i - this.codeLengthInfo[j].min;
    int m = this.codeLengthInfo[j].baseIndex + k;
    return this.codeValues[m];
  }

  static class CodeLengthInfo
  {
    int length;
    int max;
    int min;
    int baseIndex;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.image.PngHuffmanTable
 * JD-Core Version:    0.6.2
 */