package org.eclipse.swt.internal.image;

import java.io.IOException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.ImageLoaderEvent;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;

public final class JPEGFileFormat extends FileFormat
{
  int restartInterval;
  JPEGFrameHeader frameHeader;
  int imageWidth;
  int imageHeight;
  int interleavedMcuCols;
  int interleavedMcuRows;
  int maxV;
  int maxH;
  boolean progressive;
  int samplePrecision;
  int nComponents;
  int[][] frameComponents;
  int[] componentIds;
  byte[][] imageComponents;
  int[] dataUnit;
  int[][][] dataUnits;
  int[] precedingDCs;
  JPEGScanHeader scanHeader;
  byte[] dataBuffer;
  int currentBitCount;
  int bufferCurrentPosition;
  int restartsToGo;
  int nextRestartNumber;
  JPEGHuffmanTable[] acHuffmanTables;
  JPEGHuffmanTable[] dcHuffmanTables;
  int[][] quantizationTables;
  int currentByte;
  int encoderQFactor = 75;
  int eobrun = 0;
  public static final int DCTSIZE = 8;
  public static final int DCTSIZESQR = 64;
  public static final int FIX_0_899976223 = 7373;
  public static final int FIX_1_961570560 = 16069;
  public static final int FIX_2_053119869 = 16819;
  public static final int FIX_0_298631336 = 2446;
  public static final int FIX_1_847759065 = 15137;
  public static final int FIX_1_175875602 = 9633;
  public static final int FIX_3_072711026 = 25172;
  public static final int FIX_0_765366865 = 6270;
  public static final int FIX_2_562915447 = 20995;
  public static final int FIX_0_541196100 = 4433;
  public static final int FIX_0_390180644 = 3196;
  public static final int FIX_1_501321110 = 12299;
  public static final int APP0 = 65504;
  public static final int APP15 = 65519;
  public static final int COM = 65534;
  public static final int DAC = 65484;
  public static final int DHP = 65502;
  public static final int DHT = 65476;
  public static final int DNL = 65500;
  public static final int DRI = 65501;
  public static final int DQT = 65499;
  public static final int EOI = 65497;
  public static final int EXP = 65503;
  public static final int JPG = 65480;
  public static final int JPG0 = 65520;
  public static final int JPG13 = 65533;
  public static final int RST0 = 65488;
  public static final int RST1 = 65489;
  public static final int RST2 = 65490;
  public static final int RST3 = 65491;
  public static final int RST4 = 65492;
  public static final int RST5 = 65493;
  public static final int RST6 = 65494;
  public static final int RST7 = 65495;
  public static final int SOF0 = 65472;
  public static final int SOF1 = 65473;
  public static final int SOF2 = 65474;
  public static final int SOF3 = 65475;
  public static final int SOF5 = 65477;
  public static final int SOF6 = 65478;
  public static final int SOF7 = 65479;
  public static final int SOF9 = 65481;
  public static final int SOF10 = 65482;
  public static final int SOF11 = 65483;
  public static final int SOF13 = 65485;
  public static final int SOF14 = 65486;
  public static final int SOF15 = 65487;
  public static final int SOI = 65496;
  public static final int SOS = 65498;
  public static final int TEM = 65281;
  public static final int TQI = 0;
  public static final int HI = 1;
  public static final int VI = 2;
  public static final int CW = 3;
  public static final int CH = 4;
  public static final int DC = 0;
  public static final int AC = 1;
  public static final int ID_Y = 0;
  public static final int ID_CB = 1;
  public static final int ID_CR = 2;
  public static final RGB[] RGB16 = { new RGB(0, 0, 0), new RGB(128, 0, 0), new RGB(0, 128, 0), new RGB(128, 128, 0), new RGB(0, 0, 128), new RGB(128, 0, 128), new RGB(0, 128, 128), new RGB(192, 192, 192), new RGB(128, 128, 128), new RGB(255, 0, 0), new RGB(0, 255, 0), new RGB(255, 255, 0), new RGB(0, 0, 255), new RGB(255, 0, 255), new RGB(0, 255, 255), new RGB(255, 255, 255) };
  public static final int[] ExtendTest = { 0, 1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 262144 };
  public static final int[] ExtendOffset = { 0, -1, -3, -7, -15, -31, -63, -127, -255, -511, -1023, -2047, -4095, -8191, -16383, -32767, -65535, -131071, -262143 };
  public static final int[] ZigZag8x8 = { 0, 1, 8, 16, 9, 2, 3, 10, 17, 24, 32, 25, 18, 11, 4, 5, 12, 19, 26, 33, 40, 48, 41, 34, 27, 20, 13, 6, 7, 14, 21, 28, 35, 42, 49, 56, 57, 50, 43, 36, 29, 22, 15, 23, 30, 37, 44, 51, 58, 59, 52, 45, 38, 31, 39, 46, 53, 60, 61, 54, 47, 55, 62, 63 };
  public static final int[] CrRTable;
  public static final int[] CbBTable;
  public static final int[] CrGTable;
  public static final int[] CbGTable;
  public static final int[] RYTable;
  public static final int[] GYTable;
  public static final int[] BYTable;
  public static final int[] RCbTable;
  public static final int[] GCbTable;
  public static final int[] BCbTable;
  public static final int[] RCrTable;
  public static final int[] GCrTable;
  public static final int[] BCrTable;
  public static final int[] NBitsTable = arrayOfInt13;

  static
  {
    int[] arrayOfInt1 = new int[256];
    int[] arrayOfInt2 = new int[256];
    int[] arrayOfInt3 = new int[256];
    int[] arrayOfInt4 = new int[256];
    int[] arrayOfInt5 = new int[256];
    int[] arrayOfInt6 = new int[256];
    int[] arrayOfInt7 = new int[256];
    int[] arrayOfInt8 = new int[256];
    for (int i = 0; i < 256; i++)
    {
      arrayOfInt1[i] = (i * 19595);
      arrayOfInt2[i] = (i * 38470);
      arrayOfInt3[i] = (i * 7471 + 32768);
      arrayOfInt4[i] = (i * -11059);
      arrayOfInt5[i] = (i * -21709);
      arrayOfInt6[i] = (i * 32768 + 8388608);
      arrayOfInt7[i] = (i * -27439);
      arrayOfInt8[i] = (i * -5329);
    }
    RYTable = arrayOfInt1;
    GYTable = arrayOfInt2;
    BYTable = arrayOfInt3;
    RCbTable = arrayOfInt4;
    GCbTable = arrayOfInt5;
    BCbTable = arrayOfInt6;
    RCrTable = arrayOfInt6;
    GCrTable = arrayOfInt7;
    BCrTable = arrayOfInt8;
    int[] arrayOfInt9 = new int[256];
    int[] arrayOfInt10 = new int[256];
    int[] arrayOfInt11 = new int[256];
    int[] arrayOfInt12 = new int[256];
    for (int j = 0; j < 256; j++)
    {
      k = 2 * j - 255;
      arrayOfInt9[j] = (45941 * k + 32768 >> 16);
      arrayOfInt10[j] = (58065 * k + 32768 >> 16);
      arrayOfInt11[j] = (-23401 * k);
      arrayOfInt12[j] = (-11277 * k + 32768);
    }
    CrRTable = arrayOfInt9;
    CbBTable = arrayOfInt10;
    CrGTable = arrayOfInt11;
    CbGTable = arrayOfInt12;
    j = 1;
    int k = 2;
    int[] arrayOfInt13 = new int[2048];
    arrayOfInt13[0] = 0;
    for (int m = 1; m < arrayOfInt13.length; m++)
    {
      if (m >= k)
      {
        j++;
        k *= 2;
      }
      arrayOfInt13[m] = j;
    }
  }

  void compress(ImageData paramImageData, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3)
  {
    int i = paramImageData.width;
    int j = paramImageData.height;
    int k = this.maxV * this.maxH;
    this.imageComponents = new byte[this.nComponents][];
    for (int m = 0; m < this.nComponents; m++)
    {
      arrayOfInt = this.frameComponents[this.componentIds[m]];
      this.imageComponents[m] = new byte[arrayOfInt[3] * arrayOfInt[4]];
    }
    int[] arrayOfInt = this.frameComponents[this.componentIds[0]];
    int n;
    int i1;
    for (m = 0; m < j; m++)
    {
      n = m * i;
      i1 = m * arrayOfInt[3];
      System.arraycopy(paramArrayOfByte1, n, this.imageComponents[0], i1, i);
    }
    arrayOfInt = this.frameComponents[this.componentIds[1]];
    int i2;
    int i3;
    int i4;
    int i5;
    for (m = 0; m < j / this.maxV; m++)
    {
      n = m * arrayOfInt[3];
      for (i1 = 0; i1 < i / this.maxH; i1++)
      {
        i2 = 0;
        for (i3 = 0; i3 < this.maxV; i3++)
        {
          i4 = (m * this.maxV + i3) * i + i1 * this.maxH;
          for (i5 = 0; i5 < this.maxH; i5++)
            i2 += (paramArrayOfByte2[(i4 + i5)] & 0xFF);
        }
        this.imageComponents[1][(n + i1)] = ((byte)(i2 / k));
      }
    }
    arrayOfInt = this.frameComponents[this.componentIds[2]];
    for (m = 0; m < j / this.maxV; m++)
    {
      n = m * arrayOfInt[3];
      for (i1 = 0; i1 < i / this.maxH; i1++)
      {
        i2 = 0;
        for (i3 = 0; i3 < this.maxV; i3++)
        {
          i4 = (m * this.maxV + i3) * i + i1 * this.maxH;
          for (i5 = 0; i5 < this.maxH; i5++)
            i2 += (paramArrayOfByte3[(i4 + i5)] & 0xFF);
        }
        this.imageComponents[2][(n + i1)] = ((byte)(i2 / k));
      }
    }
    for (m = 0; m < this.nComponents; m++)
    {
      byte[] arrayOfByte = this.imageComponents[m];
      arrayOfInt = this.frameComponents[this.componentIds[m]];
      i1 = arrayOfInt[1];
      i2 = arrayOfInt[2];
      i3 = arrayOfInt[3];
      i4 = arrayOfInt[4];
      i5 = i / (this.maxH / i1);
      int i6 = j / (this.maxV / i2);
      int i7;
      int i8;
      int i9;
      if (i5 < i3)
      {
        i7 = i3 - i5;
        for (i8 = 0; i8 < i6; i8++)
        {
          i9 = (i8 + 1) * i3 - i7;
          int i10 = arrayOfByte[0] & 0xFF;
          for (int i11 = 0; i11 < i7; i11++)
            arrayOfByte[(i9 + i11)] = ((byte)i10);
        }
      }
      if (i6 < i4)
      {
        i7 = i6 > 0 ? (i6 - 1) * i3 : 1;
        for (i8 = i6 > 0 ? i6 : 1; i8 <= i4; i8++)
        {
          i9 = (i8 - 1) * i3;
          System.arraycopy(arrayOfByte, i7, arrayOfByte, i9, i3);
        }
      }
    }
  }

  void convert4BitRGBToYCbCr(ImageData paramImageData)
  {
    RGB[] arrayOfRGB = paramImageData.getRGBs();
    int i = arrayOfRGB.length;
    byte[] arrayOfByte1 = new byte[i];
    byte[] arrayOfByte2 = new byte[i];
    byte[] arrayOfByte3 = new byte[i];
    int j = paramImageData.width;
    int k = paramImageData.height;
    for (int m = 0; m < i; m++)
    {
      localObject = arrayOfRGB[m];
      int n = ((RGB)localObject).red;
      int i1 = ((RGB)localObject).green;
      int i2 = ((RGB)localObject).blue;
      i3 = RYTable[n] + GYTable[i1] + BYTable[i2];
      arrayOfByte1[m] = ((byte)(i3 >> 16));
      if ((i3 < 0) && ((i3 & 0xFFFF) != 0))
      {
        int tmp119_117 = m;
        byte[] tmp119_115 = arrayOfByte1;
        tmp119_115[tmp119_117] = ((byte)(tmp119_115[tmp119_117] - 1));
      }
      i3 = RCbTable[n] + GCbTable[i1] + BCbTable[i2];
      arrayOfByte2[m] = ((byte)(i3 >> 16));
      if ((i3 < 0) && ((i3 & 0xFFFF) != 0))
      {
        int tmp176_174 = m;
        byte[] tmp176_172 = arrayOfByte2;
        tmp176_172[tmp176_174] = ((byte)(tmp176_172[tmp176_174] - 1));
      }
      i3 = RCrTable[n] + GCrTable[i1] + BCrTable[i2];
      arrayOfByte3[m] = ((byte)(i3 >> 16));
      if ((i3 < 0) && ((i3 & 0xFFFF) != 0))
      {
        int tmp233_231 = m;
        byte[] tmp233_229 = arrayOfByte3;
        tmp233_229[tmp233_231] = ((byte)(tmp233_229[tmp233_231] - 1));
      }
    }
    m = j * k;
    Object localObject = new byte[m];
    byte[] arrayOfByte4 = new byte[m];
    byte[] arrayOfByte5 = new byte[m];
    byte[] arrayOfByte6 = paramImageData.data;
    int i3 = paramImageData.bytesPerLine;
    int i4 = j >> 1;
    for (int i5 = 0; i5 < k; i5++)
      for (int i6 = 0; i6 < i4; i6++)
      {
        int i7 = i5 * i3 + i6;
        int i8 = i5 * j + i6 * 2;
        int i9 = arrayOfByte6[i7] & 0xFF;
        int i10 = i9 >> 4;
        i9 &= 15;
        localObject[i8] = arrayOfByte1[i10];
        arrayOfByte4[i8] = arrayOfByte2[i10];
        arrayOfByte5[i8] = arrayOfByte3[i10];
        localObject[(i8 + 1)] = arrayOfByte1[i9];
        arrayOfByte4[(i8 + 1)] = arrayOfByte2[i9];
        arrayOfByte5[(i8 + 1)] = arrayOfByte3[i9];
      }
    compress(paramImageData, (byte[])localObject, arrayOfByte4, arrayOfByte5);
  }

  void convert8BitRGBToYCbCr(ImageData paramImageData)
  {
    RGB[] arrayOfRGB = paramImageData.getRGBs();
    int i = arrayOfRGB.length;
    byte[] arrayOfByte1 = new byte[i];
    byte[] arrayOfByte2 = new byte[i];
    byte[] arrayOfByte3 = new byte[i];
    int j = paramImageData.width;
    RGB localRGB1 = paramImageData.height;
    for (RGB localRGB2 = 0; localRGB2 < i; localRGB2++)
    {
      localRGB3 = arrayOfRGB[localRGB2];
      k = localRGB3.red;
      m = localRGB3.green;
      int n = localRGB3.blue;
      int i1 = RYTable[k] + GYTable[m] + BYTable[n];
      arrayOfByte1[localRGB2] = ((byte)(i1 >> 16));
      if ((i1 < 0) && ((i1 & 0xFFFF) != 0))
      {
        RGB tmp119_117 = localRGB2;
        byte[] tmp119_115 = arrayOfByte1;
        tmp119_115[tmp119_117] = ((byte)(tmp119_115[tmp119_117] - 1));
      }
      i1 = RCbTable[k] + GCbTable[m] + BCbTable[n];
      arrayOfByte2[localRGB2] = ((byte)(i1 >> 16));
      if ((i1 < 0) && ((i1 & 0xFFFF) != 0))
      {
        RGB tmp176_174 = localRGB2;
        byte[] tmp176_172 = arrayOfByte2;
        tmp176_172[tmp176_174] = ((byte)(tmp176_172[tmp176_174] - 1));
      }
      i1 = RCrTable[k] + GCrTable[m] + BCrTable[n];
      arrayOfByte3[localRGB2] = ((byte)(i1 >> 16));
      if ((i1 < 0) && ((i1 & 0xFFFF) != 0))
      {
        RGB tmp233_231 = localRGB2;
        byte[] tmp233_229 = arrayOfByte3;
        tmp233_229[tmp233_231] = ((byte)(tmp233_229[tmp233_231] - 1));
      }
    }
    localRGB2 = paramImageData.width;
    RGB localRGB3 = localRGB1;
    int k = j + 3 >> 2 << 2;
    int m = localRGB2 * localRGB3;
    byte[] arrayOfByte4 = new byte[m];
    byte[] arrayOfByte5 = new byte[m];
    byte[] arrayOfByte6 = new byte[m];
    byte[] arrayOfByte7 = paramImageData.data;
    for (RGB localRGB4 = 0; localRGB4 < localRGB1; localRGB4++)
    {
      int i2 = localRGB4 * k;
      int i3 = localRGB4 * localRGB2;
      for (int i4 = 0; i4 < j; i4++)
      {
        int i5 = arrayOfByte7[(i2 + i4)] & 0xFF;
        int i6 = i3 + i4;
        arrayOfByte4[i6] = arrayOfByte1[i5];
        arrayOfByte5[i6] = arrayOfByte2[i5];
        arrayOfByte6[i6] = arrayOfByte3[i5];
      }
    }
    compress(paramImageData, arrayOfByte4, arrayOfByte5, arrayOfByte6);
  }

  byte[] convertCMYKToRGB()
  {
    return new byte[0];
  }

  void convertImageToYCbCr(ImageData paramImageData)
  {
    switch (paramImageData.depth)
    {
    case 4:
      convert4BitRGBToYCbCr(paramImageData);
      return;
    case 8:
      convert8BitRGBToYCbCr(paramImageData);
      return;
    case 16:
    case 24:
    case 32:
      convertMultiRGBToYCbCr(paramImageData);
      return;
    }
    SWT.error(38);
  }

  void convertMultiRGBToYCbCr(ImageData paramImageData)
  {
    int i = paramImageData.width;
    int j = paramImageData.height;
    int k = i * j;
    byte[] arrayOfByte1 = new byte[k];
    byte[] arrayOfByte2 = new byte[k];
    byte[] arrayOfByte3 = new byte[k];
    PaletteData localPaletteData = paramImageData.palette;
    int[] arrayOfInt = new int[i];
    int m;
    int n;
    int i1;
    int i2;
    int i3;
    int i5;
    int i6;
    int i7;
    if (localPaletteData.isDirect)
    {
      m = localPaletteData.redMask;
      n = localPaletteData.greenMask;
      i1 = localPaletteData.blueMask;
      i2 = localPaletteData.redShift;
      i3 = localPaletteData.greenShift;
      int i4 = localPaletteData.blueShift;
      for (i5 = 0; i5 < j; i5++)
      {
        paramImageData.getPixels(0, i5, i, arrayOfInt, 0);
        i6 = i5 * i;
        for (i7 = 0; i7 < i; i7++)
        {
          int i8 = arrayOfInt[i7];
          int i9 = i6 + i7;
          int i10 = i8 & m;
          i10 = i2 < 0 ? i10 >>> -i2 : i10 << i2;
          int i11 = i8 & n;
          i11 = i3 < 0 ? i11 >>> -i3 : i11 << i3;
          int i12 = i8 & i1;
          i12 = i4 < 0 ? i12 >>> -i4 : i12 << i4;
          arrayOfByte1[i9] = ((byte)(RYTable[i10] + GYTable[i11] + BYTable[i12] >> 16));
          arrayOfByte2[i9] = ((byte)(RCbTable[i10] + GCbTable[i11] + BCbTable[i12] >> 16));
          arrayOfByte3[i9] = ((byte)(RCrTable[i10] + GCrTable[i11] + BCrTable[i12] >> 16));
        }
      }
    }
    else
    {
      for (m = 0; m < j; m++)
      {
        paramImageData.getPixels(0, m, i, arrayOfInt, 0);
        n = m * i;
        for (i1 = 0; i1 < i; i1++)
        {
          i2 = arrayOfInt[i1];
          i3 = n + i1;
          RGB localRGB = localPaletteData.getRGB(i2);
          i5 = localRGB.red;
          i6 = localRGB.green;
          i7 = localRGB.blue;
          arrayOfByte1[i3] = ((byte)(RYTable[i5] + GYTable[i6] + BYTable[i7] >> 16));
          arrayOfByte2[i3] = ((byte)(RCbTable[i5] + GCbTable[i6] + BCbTable[i7] >> 16));
          arrayOfByte3[i3] = ((byte)(RCrTable[i5] + GCrTable[i6] + BCrTable[i7] >> 16));
        }
      }
    }
    compress(paramImageData, arrayOfByte1, arrayOfByte2, arrayOfByte3);
  }

  byte[] convertYToRGB()
  {
    int i = this.frameComponents[this.componentIds[0]][3];
    int j = ((this.imageWidth * 8 + 7) / 8 + 3) / 4 * 4;
    byte[] arrayOfByte1 = new byte[j * this.imageHeight];
    byte[] arrayOfByte2 = this.imageComponents[0];
    int k = 0;
    for (int m = 0; m < this.imageHeight; m++)
    {
      int n = m * i;
      for (int i1 = 0; i1 < j; i1++)
      {
        int i2 = arrayOfByte2[n] & 0xFF;
        if (i2 < 0)
          i2 = 0;
        else if (i2 > 255)
          i2 = 255;
        if (i1 >= this.imageWidth)
          i2 = 0;
        arrayOfByte1[k] = ((byte)i2);
        n++;
        k++;
      }
    }
    return arrayOfByte1;
  }

  byte[] convertYCbCrToRGB()
  {
    int i = this.imageWidth * this.imageHeight * this.nComponents;
    byte[] arrayOfByte1 = new byte[i];
    int j = 0;
    expandImageComponents();
    byte[] arrayOfByte2 = this.imageComponents[0];
    byte[] arrayOfByte3 = this.imageComponents[1];
    byte[] arrayOfByte4 = this.imageComponents[2];
    int k = this.frameComponents[this.componentIds[0]][3];
    for (int m = 0; m < this.imageHeight; m++)
    {
      int n = m * k;
      for (int i1 = 0; i1 < this.imageWidth; i1++)
      {
        int i2 = arrayOfByte2[n] & 0xFF;
        int i3 = arrayOfByte3[n] & 0xFF;
        int i4 = arrayOfByte4[n] & 0xFF;
        int i5 = i2 + CrRTable[i4];
        int i6 = i2 + (CbGTable[i3] + CrGTable[i4] >> 16);
        int i7 = i2 + CbBTable[i3];
        if (i5 < 0)
          i5 = 0;
        else if (i5 > 255)
          i5 = 255;
        if (i6 < 0)
          i6 = 0;
        else if (i6 > 255)
          i6 = 255;
        if (i7 < 0)
          i7 = 0;
        else if (i7 > 255)
          i7 = 255;
        arrayOfByte1[j] = ((byte)i7);
        arrayOfByte1[(j + 1)] = ((byte)i6);
        arrayOfByte1[(j + 2)] = ((byte)i5);
        j += 3;
        n++;
      }
    }
    return arrayOfByte1;
  }

  void decodeACCoefficients(int[] paramArrayOfInt, int paramInt)
  {
    int[] arrayOfInt = this.scanHeader.componentParameters[this.componentIds[paramInt]];
    JPEGHuffmanTable localJPEGHuffmanTable = this.acHuffmanTables[arrayOfInt[1]];
    int i = 1;
    while (i < 64)
    {
      int j = decodeUsingTable(localJPEGHuffmanTable);
      int k = j >> 4;
      int m = j & 0xF;
      if (m == 0)
      {
        if (k != 15)
          break;
        i += 16;
      }
      else
      {
        i += k;
        int n = receive(m);
        paramArrayOfInt[ZigZag8x8[i]] = extendBy(n, m);
        i++;
      }
    }
  }

  void decodeACFirstCoefficients(int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.eobrun > 0)
    {
      this.eobrun -= 1;
      return;
    }
    int[] arrayOfInt = this.scanHeader.componentParameters[this.componentIds[paramInt1]];
    JPEGHuffmanTable localJPEGHuffmanTable = this.acHuffmanTables[arrayOfInt[1]];
    int i = paramInt2;
    while (i <= paramInt3)
    {
      int j = decodeUsingTable(localJPEGHuffmanTable);
      int k = j >> 4;
      int m = j & 0xF;
      if (m == 0)
      {
        if (k == 15)
        {
          i += 16;
        }
        else
        {
          this.eobrun = ((1 << k) + receive(k) - 1);
          break;
        }
      }
      else
      {
        i += k;
        int n = receive(m);
        paramArrayOfInt[ZigZag8x8[i]] = (extendBy(n, m) << paramInt4);
        i++;
      }
    }
  }

  void decodeACRefineCoefficients(int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int[] arrayOfInt = this.scanHeader.componentParameters[this.componentIds[paramInt1]];
    JPEGHuffmanTable localJPEGHuffmanTable = this.acHuffmanTables[arrayOfInt[1]];
    int i = paramInt2;
    while (i <= paramInt3)
    {
      int j;
      if (this.eobrun > 0)
      {
        while (i <= paramInt3)
        {
          j = ZigZag8x8[i];
          if (paramArrayOfInt[j] != 0)
            paramArrayOfInt[j] = refineAC(paramArrayOfInt[j], paramInt4);
          i++;
        }
        this.eobrun -= 1;
      }
      else
      {
        j = decodeUsingTable(localJPEGHuffmanTable);
        int k = j >> 4;
        int m = j & 0xF;
        int n;
        int i1;
        if (m == 0)
        {
          if (k == 15)
          {
            n = 0;
            do
            {
              i1 = ZigZag8x8[i];
              if (paramArrayOfInt[i1] != 0)
                paramArrayOfInt[i1] = refineAC(paramArrayOfInt[i1], paramInt4);
              else
                n++;
              i++;
              if (n >= 16)
                break;
            }
            while (i <= paramInt3);
          }
          else
          {
            this.eobrun = ((1 << k) + receive(k));
          }
        }
        else
        {
          n = receive(m);
          i1 = 0;
          for (int i2 = ZigZag8x8[i]; ((i1 < k) || (paramArrayOfInt[i2] != 0)) && (i <= paramInt3); i2 = ZigZag8x8[i])
          {
            if (paramArrayOfInt[i2] != 0)
              paramArrayOfInt[i2] = refineAC(paramArrayOfInt[i2], paramInt4);
            else
              i1++;
            i++;
          }
          if (n != 0)
            paramArrayOfInt[i2] = (1 << paramInt4);
          else
            paramArrayOfInt[i2] = (-1 << paramInt4);
          i++;
        }
      }
    }
  }

  int refineAC(int paramInt1, int paramInt2)
  {
    int i;
    if (paramInt1 > 0)
    {
      i = nextBit();
      if (i != 0)
        paramInt1 += (1 << paramInt2);
    }
    else if (paramInt1 < 0)
    {
      i = nextBit();
      if (i != 0)
        paramInt1 += (-1 << paramInt2);
    }
    return paramInt1;
  }

  void decodeDCCoefficient(int[] paramArrayOfInt, int paramInt1, boolean paramBoolean, int paramInt2)
  {
    int[] arrayOfInt = this.scanHeader.componentParameters[this.componentIds[paramInt1]];
    JPEGHuffmanTable localJPEGHuffmanTable = this.dcHuffmanTables[arrayOfInt[0]];
    int i = 0;
    int j;
    if ((this.progressive) && (!paramBoolean))
    {
      j = nextBit();
      i = paramArrayOfInt[0] + (j << paramInt2);
    }
    else
    {
      i = this.precedingDCs[paramInt1];
      j = decodeUsingTable(localJPEGHuffmanTable);
      if (j != 0)
      {
        int k = receive(j);
        int m = extendBy(k, j);
        i += m;
        this.precedingDCs[paramInt1] = i;
      }
      if (this.progressive)
        i <<= paramInt2;
    }
    paramArrayOfInt[0] = i;
  }

  void dequantize(int[] paramArrayOfInt, int paramInt)
  {
    int[] arrayOfInt = this.quantizationTables[this.frameComponents[this.componentIds[paramInt]][0]];
    for (int i = 0; i < paramArrayOfInt.length; i++)
    {
      int j = ZigZag8x8[i];
      paramArrayOfInt[j] *= arrayOfInt[i];
    }
  }

  byte[] decodeImageComponents()
  {
    if (this.nComponents == 3)
      return convertYCbCrToRGB();
    if (this.nComponents == 4)
      return convertCMYKToRGB();
    return convertYToRGB();
  }

  void decodeMCUAtXAndY(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean, int paramInt4, int paramInt5, int paramInt6)
  {
    for (int i = 0; i < paramInt3; i++)
    {
      for (int j = i; this.scanHeader.componentParameters[this.componentIds[j]] == null; j++);
      int[] arrayOfInt1 = this.frameComponents[this.componentIds[j]];
      int k = arrayOfInt1[1];
      int m = arrayOfInt1[2];
      if (paramInt3 == 1)
      {
        k = 1;
        m = 1;
      }
      int n = arrayOfInt1[3];
      for (int i1 = 0; i1 < m; i1++)
        for (int i2 = 0; i2 < k; i2++)
        {
          int i3;
          if (this.progressive)
          {
            i3 = (paramInt2 * m + i1) * n + paramInt1 * k + i2;
            this.dataUnit = this.dataUnits[j][i3];
            if (this.dataUnit == null)
            {
              this.dataUnit = new int[64];
              this.dataUnits[j][i3] = this.dataUnit;
            }
          }
          else
          {
            for (i3 = 0; i3 < this.dataUnit.length; i3++)
              this.dataUnit[i3] = 0;
          }
          if ((!this.progressive) || (this.scanHeader.isDCProgressiveScan()))
            decodeDCCoefficient(this.dataUnit, j, paramBoolean, paramInt6);
          if (!this.progressive)
          {
            decodeACCoefficients(this.dataUnit, j);
          }
          else
          {
            if (this.scanHeader.isACProgressiveScan())
              if (paramBoolean)
                decodeACFirstCoefficients(this.dataUnit, j, paramInt4, paramInt5, paramInt6);
              else
                decodeACRefineCoefficients(this.dataUnit, j, paramInt4, paramInt5, paramInt6);
            if (this.loader.hasListeners())
            {
              int[] arrayOfInt2 = this.dataUnit;
              this.dataUnit = new int[64];
              System.arraycopy(arrayOfInt2, 0, this.dataUnit, 0, 64);
            }
          }
          if ((!this.progressive) || ((this.progressive) && (this.loader.hasListeners())))
          {
            dequantize(this.dataUnit, j);
            inverseDCT(this.dataUnit);
            storeData(this.dataUnit, j, paramInt1, paramInt2, k, i2, m, i1);
          }
        }
    }
  }

  void decodeScan()
  {
    if ((this.progressive) && (!this.scanHeader.verifyProgressiveScan()))
      SWT.error(40);
    int i = this.scanHeader.getNumberOfImageComponents();
    int j = this.interleavedMcuRows;
    int k = this.interleavedMcuCols;
    int i3;
    if (i == 1)
    {
      for (bool = false; this.scanHeader.componentParameters[this.componentIds[bool]] == null; bool++);
      int[] arrayOfInt = this.frameComponents[this.componentIds[bool]];
      n = arrayOfInt[1];
      i1 = arrayOfInt[2];
      i2 = 8 * this.maxH / n;
      i3 = 8 * this.maxV / i1;
      k = (this.imageWidth + i2 - 1) / i2;
      j = (this.imageHeight + i3 - 1) / i3;
    }
    boolean bool = this.scanHeader.isFirstScan();
    int m = this.scanHeader.getStartOfSpectralSelection();
    int n = this.scanHeader.getEndOfSpectralSelection();
    int i1 = this.scanHeader.getApproxBitPositionLow();
    this.restartsToGo = this.restartInterval;
    this.nextRestartNumber = 0;
    for (int i2 = 0; i2 < j; i2++)
      for (i3 = 0; i3 < k; i3++)
      {
        if (this.restartInterval != 0)
        {
          if (this.restartsToGo == 0)
            processRestartInterval();
          this.restartsToGo -= 1;
        }
        decodeMCUAtXAndY(i3, i2, i, bool, m, n, i1);
      }
  }

  int decodeUsingTable(JPEGHuffmanTable paramJPEGHuffmanTable)
  {
    int i = 0;
    int[] arrayOfInt1 = paramJPEGHuffmanTable.getDhMaxCodes();
    int[] arrayOfInt2 = paramJPEGHuffmanTable.getDhMinCodes();
    int[] arrayOfInt3 = paramJPEGHuffmanTable.getDhValPtrs();
    int[] arrayOfInt4 = paramJPEGHuffmanTable.getDhValues();
    int j = nextBit();
    while (j > arrayOfInt1[i])
    {
      j = j * 2 + nextBit();
      i++;
    }
    int k = arrayOfInt3[i] + j - arrayOfInt2[i];
    return arrayOfInt4[k];
  }

  void emit(int paramInt1, int paramInt2)
  {
    if (paramInt2 == 0)
      SWT.error(40);
    int[] arrayOfInt = { 1, 3, 7, 15, 31, 63, 127, 255, 511, 1023, 2047, 4095, 8191, 16383, 32767, 65535, 131125 };
    int i = (paramInt1 & arrayOfInt[(paramInt2 - 1)]) << 24 - paramInt2 - this.currentBitCount;
    byte[] arrayOfByte = new byte[4];
    arrayOfByte[0] = ((byte)(i & 0xFF));
    arrayOfByte[1] = ((byte)(i >> 8 & 0xFF));
    arrayOfByte[2] = ((byte)(i >> 16 & 0xFF));
    arrayOfByte[3] = ((byte)(i >> 24 & 0xFF));
    int j = paramInt2 - (8 - this.currentBitCount);
    if (j < 0)
      j = -j;
    if (j >> 3 > 0)
    {
      this.currentByte += arrayOfByte[2];
      emitByte((byte)this.currentByte);
      emitByte(arrayOfByte[1]);
      this.currentByte = arrayOfByte[0];
      this.currentBitCount += paramInt2 - 16;
    }
    else
    {
      this.currentBitCount += paramInt2;
      if (this.currentBitCount >= 8)
      {
        this.currentByte += arrayOfByte[2];
        emitByte((byte)this.currentByte);
        this.currentByte = arrayOfByte[1];
        this.currentBitCount -= 8;
      }
      else
      {
        this.currentByte += arrayOfByte[2];
      }
    }
  }

  void emitByte(byte paramByte)
  {
    if (this.bufferCurrentPosition >= 512)
      resetOutputBuffer();
    this.dataBuffer[this.bufferCurrentPosition] = paramByte;
    this.bufferCurrentPosition += 1;
    if (paramByte == -1)
      emitByte((byte)0);
  }

  void encodeACCoefficients(int[] paramArrayOfInt, int paramInt)
  {
    int[] arrayOfInt1 = this.scanHeader.componentParameters[paramInt];
    JPEGHuffmanTable localJPEGHuffmanTable = this.acHuffmanTables[arrayOfInt1[1]];
    int[] arrayOfInt2 = localJPEGHuffmanTable.ehCodes;
    byte[] arrayOfByte = localJPEGHuffmanTable.ehCodeLengths;
    int i = 0;
    int j = 1;
    while (j < 64)
    {
      j++;
      int k = paramArrayOfInt[ZigZag8x8[(j - 1)]];
      if (k == 0)
      {
        if (j == 64)
          emit(arrayOfInt2[0], arrayOfByte[0] & 0xFF);
        else
          i++;
      }
      else
      {
        while (i > 15)
        {
          emit(arrayOfInt2['ð'], arrayOfByte['ð'] & 0xFF);
          i -= 16;
        }
        int m;
        int n;
        if (k < 0)
        {
          m = k;
          if (m < 0)
            m = -m;
          n = NBitsTable[m];
          int i1 = i * 16 + n;
          emit(arrayOfInt2[i1], arrayOfByte[i1] & 0xFF);
          emit(16777215 - m, n);
        }
        else
        {
          m = NBitsTable[k];
          n = i * 16 + m;
          emit(arrayOfInt2[n], arrayOfByte[n] & 0xFF);
          emit(k, m);
        }
        i = 0;
      }
    }
  }

  void encodeDCCoefficients(int[] paramArrayOfInt, int paramInt)
  {
    int[] arrayOfInt = this.scanHeader.componentParameters[paramInt];
    JPEGHuffmanTable localJPEGHuffmanTable = this.dcHuffmanTables[arrayOfInt[0]];
    int i = this.precedingDCs[paramInt];
    int j = paramArrayOfInt[0];
    int k = j - i;
    this.precedingDCs[paramInt] = j;
    int m;
    if (k < 0)
    {
      m = 0 - k;
      int n = NBitsTable[m];
      emit(localJPEGHuffmanTable.ehCodes[n], localJPEGHuffmanTable.ehCodeLengths[n]);
      emit(16777215 - m, n);
    }
    else
    {
      m = NBitsTable[k];
      emit(localJPEGHuffmanTable.ehCodes[m], localJPEGHuffmanTable.ehCodeLengths[m]);
      if (m != 0)
        emit(k, m);
    }
  }

  void encodeMCUAtXAndY(int paramInt1, int paramInt2)
  {
    int i = this.scanHeader.getNumberOfImageComponents();
    this.dataUnit = new int[64];
    for (int j = 0; j < i; j++)
    {
      int[] arrayOfInt = this.frameComponents[this.componentIds[j]];
      int k = arrayOfInt[1];
      int m = arrayOfInt[2];
      for (int n = 0; n < m; n++)
        for (int i1 = 0; i1 < k; i1++)
        {
          extractData(this.dataUnit, j, paramInt1, paramInt2, i1, n);
          forwardDCT(this.dataUnit);
          quantizeData(this.dataUnit, j);
          encodeDCCoefficients(this.dataUnit, j);
          encodeACCoefficients(this.dataUnit, j);
        }
    }
  }

  void encodeScan()
  {
    for (int i = 0; i < this.interleavedMcuRows; i++)
      for (int j = 0; j < this.interleavedMcuCols; j++)
        encodeMCUAtXAndY(j, i);
    if (this.currentBitCount != 0)
      emitByte((byte)this.currentByte);
    resetOutputBuffer();
  }

  void expandImageComponents()
  {
    for (int i = 0; i < this.nComponents; i++)
    {
      int[] arrayOfInt = this.frameComponents[this.componentIds[i]];
      int j = arrayOfInt[1];
      int k = arrayOfInt[2];
      int m = this.maxH / j;
      int n = this.maxV / k;
      if (m * n > 1)
      {
        byte[] arrayOfByte = this.imageComponents[i];
        int i1 = arrayOfInt[3];
        int i2 = arrayOfInt[4];
        int i3 = i1 * m;
        int i4 = i2 * n;
        ImageData localImageData1 = new ImageData(i1, i2, 8, new PaletteData(RGB16), 4, arrayOfByte);
        ImageData localImageData2 = localImageData1.scaledTo(i3, i4);
        this.imageComponents[i] = localImageData2.data;
      }
    }
  }

  int extendBy(int paramInt1, int paramInt2)
  {
    if (paramInt1 < ExtendTest[paramInt2])
      return paramInt1 + ExtendOffset[paramInt2];
    return paramInt1;
  }

  void extractData(int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    byte[] arrayOfByte = this.imageComponents[paramInt1];
    int[] arrayOfInt = this.frameComponents[this.componentIds[paramInt1]];
    int i = arrayOfInt[1];
    int j = arrayOfInt[2];
    int k = arrayOfInt[3];
    int m = (paramInt3 * j + paramInt5) * k * 8 + (paramInt2 * i + paramInt4) * 8;
    int n = 0;
    for (int i1 = 0; i1 < 8; i1++)
    {
      for (int i2 = 0; i2 < 8; i2++)
      {
        paramArrayOfInt[n] = ((arrayOfByte[(m + i2)] & 0xFF) - 128);
        n++;
      }
      m += k;
    }
  }

  void forwardDCT(int[] paramArrayOfInt)
  {
    int j;
    int k;
    int m;
    int n;
    int i1;
    int i2;
    int i3;
    int i4;
    int i5;
    int i6;
    int i7;
    int i8;
    int i9;
    int i10;
    int i11;
    int i12;
    int i13;
    int i14;
    int i15;
    for (int i = 0; i < 8; i++)
    {
      j = i * 8;
      k = paramArrayOfInt[j] + paramArrayOfInt[(j + 7)];
      m = paramArrayOfInt[j] - paramArrayOfInt[(j + 7)];
      n = paramArrayOfInt[(j + 1)] + paramArrayOfInt[(j + 6)];
      i1 = paramArrayOfInt[(j + 1)] - paramArrayOfInt[(j + 6)];
      i2 = paramArrayOfInt[(j + 2)] + paramArrayOfInt[(j + 5)];
      i3 = paramArrayOfInt[(j + 2)] - paramArrayOfInt[(j + 5)];
      i4 = paramArrayOfInt[(j + 3)] + paramArrayOfInt[(j + 4)];
      i5 = paramArrayOfInt[(j + 3)] - paramArrayOfInt[(j + 4)];
      i6 = k + i4;
      i7 = k - i4;
      i8 = n + i2;
      i9 = n - i2;
      paramArrayOfInt[j] = ((i6 + i8) * 4);
      paramArrayOfInt[(j + 4)] = ((i6 - i8) * 4);
      i10 = (i9 + i7) * 4433;
      i11 = i10 + i7 * 6270 + 1024;
      paramArrayOfInt[(j + 2)] = (i11 >> 11);
      if ((i11 < 0) && ((i11 & 0x7FF) != 0))
        paramArrayOfInt[(j + 2)] -= 1;
      i11 = i10 + i9 * -15137 + 1024;
      paramArrayOfInt[(j + 6)] = (i11 >> 11);
      if ((i11 < 0) && ((i11 & 0x7FF) != 0))
        paramArrayOfInt[(j + 6)] -= 1;
      i10 = i5 + m;
      i12 = i3 + i1;
      i13 = i5 + i1;
      i14 = i3 + m;
      i15 = (i13 + i14) * 9633;
      i5 *= 2446;
      i3 *= 16819;
      i1 *= 25172;
      m *= 12299;
      i10 *= -7373;
      i12 *= -20995;
      i13 *= -16069;
      i14 *= -3196;
      i13 += i15;
      i14 += i15;
      i11 = i5 + i10 + i13 + 1024;
      paramArrayOfInt[(j + 7)] = (i11 >> 11);
      if ((i11 < 0) && ((i11 & 0x7FF) != 0))
        paramArrayOfInt[(j + 7)] -= 1;
      i11 = i3 + i12 + i14 + 1024;
      paramArrayOfInt[(j + 5)] = (i11 >> 11);
      if ((i11 < 0) && ((i11 & 0x7FF) != 0))
        paramArrayOfInt[(j + 5)] -= 1;
      i11 = i1 + i12 + i13 + 1024;
      paramArrayOfInt[(j + 3)] = (i11 >> 11);
      if ((i11 < 0) && ((i11 & 0x7FF) != 0))
        paramArrayOfInt[(j + 3)] -= 1;
      i11 = m + i10 + i14 + 1024;
      paramArrayOfInt[(j + 1)] = (i11 >> 11);
      if ((i11 < 0) && ((i11 & 0x7FF) != 0))
        paramArrayOfInt[(j + 1)] -= 1;
    }
    for (i = 0; i < 8; i++)
    {
      j = i;
      k = i + 8;
      m = i + 16;
      n = i + 24;
      i1 = i + 32;
      i2 = i + 40;
      i3 = i + 48;
      i4 = i + 56;
      i5 = paramArrayOfInt[j] + paramArrayOfInt[i4];
      i6 = paramArrayOfInt[j] - paramArrayOfInt[i4];
      i7 = paramArrayOfInt[k] + paramArrayOfInt[i3];
      i8 = paramArrayOfInt[k] - paramArrayOfInt[i3];
      i9 = paramArrayOfInt[m] + paramArrayOfInt[i2];
      i10 = paramArrayOfInt[m] - paramArrayOfInt[i2];
      i11 = paramArrayOfInt[n] + paramArrayOfInt[i1];
      i12 = paramArrayOfInt[n] - paramArrayOfInt[i1];
      i13 = i5 + i11;
      i14 = i5 - i11;
      i15 = i7 + i9;
      int i16 = i7 - i9;
      int i17 = i13 + i15 + 16;
      paramArrayOfInt[j] = (i17 >> 5);
      if ((i17 < 0) && ((i17 & 0x1F) != 0))
        paramArrayOfInt[j] -= 1;
      i17 = i13 - i15 + 16;
      paramArrayOfInt[i1] = (i17 >> 5);
      if ((i17 < 0) && ((i17 & 0x1F) != 0))
        paramArrayOfInt[i1] -= 1;
      int i18 = (i16 + i14) * 4433;
      i17 = i18 + i14 * 6270 + 131072;
      paramArrayOfInt[m] = (i17 >> 18);
      if ((i17 < 0) && ((i17 & 0x3FFFF) != 0))
        paramArrayOfInt[m] -= 1;
      i17 = i18 + i16 * -15137 + 131072;
      paramArrayOfInt[i3] = (i17 >> 18);
      if ((i17 < 0) && ((i17 & 0x3FFFF) != 0))
        paramArrayOfInt[i3] -= 1;
      i18 = i12 + i6;
      int i19 = i10 + i8;
      int i20 = i12 + i8;
      int i21 = i10 + i6;
      int i22 = (i20 + i21) * 9633;
      i12 *= 2446;
      i10 *= 16819;
      i8 *= 25172;
      i6 *= 12299;
      i18 *= -7373;
      i19 *= -20995;
      i20 *= -16069;
      i21 *= -3196;
      i20 += i22;
      i21 += i22;
      i17 = i12 + i18 + i20 + 131072;
      paramArrayOfInt[i4] = (i17 >> 18);
      if ((i17 < 0) && ((i17 & 0x3FFFF) != 0))
        paramArrayOfInt[i4] -= 1;
      i17 = i10 + i19 + i21 + 131072;
      paramArrayOfInt[i2] = (i17 >> 18);
      if ((i17 < 0) && ((i17 & 0x3FFFF) != 0))
        paramArrayOfInt[i2] -= 1;
      i17 = i8 + i19 + i20 + 131072;
      paramArrayOfInt[n] = (i17 >> 18);
      if ((i17 < 0) && ((i17 & 0x3FFFF) != 0))
        paramArrayOfInt[n] -= 1;
      i17 = i6 + i18 + i21 + 131072;
      paramArrayOfInt[k] = (i17 >> 18);
      if ((i17 < 0) && ((i17 & 0x3FFFF) != 0))
        paramArrayOfInt[k] -= 1;
    }
  }

  void getAPP0()
  {
    JPEGAppn localJPEGAppn = new JPEGAppn(this.inputStream);
    if (!localJPEGAppn.verify())
      SWT.error(40);
  }

  void getCOM()
  {
    new JPEGComment(this.inputStream);
  }

  void getDAC()
  {
    new JPEGArithmeticConditioningTable(this.inputStream);
  }

  void getDHT()
  {
    JPEGHuffmanTable localJPEGHuffmanTable1 = new JPEGHuffmanTable(this.inputStream);
    if (!localJPEGHuffmanTable1.verify())
      SWT.error(40);
    if (this.acHuffmanTables == null)
      this.acHuffmanTables = new JPEGHuffmanTable[4];
    if (this.dcHuffmanTables == null)
      this.dcHuffmanTables = new JPEGHuffmanTable[4];
    JPEGHuffmanTable[] arrayOfJPEGHuffmanTable = localJPEGHuffmanTable1.getAllTables();
    for (int i = 0; i < arrayOfJPEGHuffmanTable.length; i++)
    {
      JPEGHuffmanTable localJPEGHuffmanTable2 = arrayOfJPEGHuffmanTable[i];
      if (localJPEGHuffmanTable2.getTableClass() == 0)
        this.dcHuffmanTables[localJPEGHuffmanTable2.getTableIdentifier()] = localJPEGHuffmanTable2;
      else
        this.acHuffmanTables[localJPEGHuffmanTable2.getTableIdentifier()] = localJPEGHuffmanTable2;
    }
  }

  void getDNL()
  {
    new JPEGRestartInterval(this.inputStream);
  }

  void getDQT()
  {
    JPEGQuantizationTable localJPEGQuantizationTable = new JPEGQuantizationTable(this.inputStream);
    Object localObject = this.quantizationTables;
    if (localObject == null)
      localObject = new int[4][];
    int[] arrayOfInt = localJPEGQuantizationTable.getQuantizationTablesKeys();
    int[][] arrayOfInt1 = localJPEGQuantizationTable.getQuantizationTablesValues();
    for (int i = 0; i < arrayOfInt.length; i++)
    {
      int j = arrayOfInt[i];
      localObject[j] = arrayOfInt1[i];
    }
    this.quantizationTables = ((int[][])localObject);
  }

  void getDRI()
  {
    JPEGRestartInterval localJPEGRestartInterval = new JPEGRestartInterval(this.inputStream);
    if (!localJPEGRestartInterval.verify())
      SWT.error(40);
    this.restartInterval = localJPEGRestartInterval.getRestartInterval();
  }

  void inverseDCT(int[] paramArrayOfInt)
  {
    int j;
    int k;
    int m;
    int n;
    int i1;
    int i2;
    int i3;
    int i4;
    int i5;
    int i6;
    int i7;
    int i8;
    int i9;
    int i10;
    for (int i = 0; i < 8; i++)
    {
      j = i * 8;
      if (isZeroInRow(paramArrayOfInt, j))
      {
        k = paramArrayOfInt[j] << 2;
        for (m = j + 7; m >= j; m--)
          paramArrayOfInt[m] = k;
      }
      else
      {
        k = paramArrayOfInt[(j + 2)];
        m = paramArrayOfInt[(j + 6)];
        n = (k + m) * 4433;
        i1 = n + m * -15137;
        i2 = n + k * 6270;
        i3 = paramArrayOfInt[j] + paramArrayOfInt[(j + 4)] << 13;
        i4 = paramArrayOfInt[j] - paramArrayOfInt[(j + 4)] << 13;
        i5 = i3 + i2;
        i6 = i3 - i2;
        i7 = i4 + i1;
        i8 = i4 - i1;
        i3 = paramArrayOfInt[(j + 7)];
        i4 = paramArrayOfInt[(j + 5)];
        i1 = paramArrayOfInt[(j + 3)];
        i2 = paramArrayOfInt[(j + 1)];
        n = i3 + i2;
        k = i4 + i1;
        m = i3 + i1;
        i9 = i4 + i2;
        i10 = (m + i9) * 9633;
        i3 *= 2446;
        i4 *= 16819;
        i1 *= 25172;
        i2 *= 12299;
        n *= -7373;
        k *= -20995;
        m *= -16069;
        i9 *= -3196;
        m += i10;
        i9 += i10;
        i3 += n + m;
        i4 += k + i9;
        i1 += k + m;
        i2 += n + i9;
        paramArrayOfInt[j] = (i5 + i2 + 1024 >> 11);
        paramArrayOfInt[(j + 7)] = (i5 - i2 + 1024 >> 11);
        paramArrayOfInt[(j + 1)] = (i7 + i1 + 1024 >> 11);
        paramArrayOfInt[(j + 6)] = (i7 - i1 + 1024 >> 11);
        paramArrayOfInt[(j + 2)] = (i8 + i4 + 1024 >> 11);
        paramArrayOfInt[(j + 5)] = (i8 - i4 + 1024 >> 11);
        paramArrayOfInt[(j + 3)] = (i6 + i3 + 1024 >> 11);
        paramArrayOfInt[(j + 4)] = (i6 - i3 + 1024 >> 11);
      }
    }
    for (i = 0; i < 8; i++)
    {
      j = i;
      k = i + 8;
      m = i + 16;
      n = i + 24;
      i1 = i + 32;
      i2 = i + 40;
      i3 = i + 48;
      i4 = i + 56;
      if (isZeroInColumn(paramArrayOfInt, i))
      {
        i5 = paramArrayOfInt[j] + 16 >> 5;
        paramArrayOfInt[j] = i5;
        paramArrayOfInt[k] = i5;
        paramArrayOfInt[m] = i5;
        paramArrayOfInt[n] = i5;
        paramArrayOfInt[i1] = i5;
        paramArrayOfInt[i2] = i5;
        paramArrayOfInt[i3] = i5;
        paramArrayOfInt[i4] = i5;
      }
      else
      {
        i5 = paramArrayOfInt[j];
        i6 = paramArrayOfInt[m];
        i7 = paramArrayOfInt[i3];
        i8 = paramArrayOfInt[i1];
        i9 = (i6 + i7) * 4433;
        i10 = i9 + i7 * -15137;
        int i11 = i9 + i6 * 6270;
        int i12 = i5 + i8 << 13;
        int i13 = i5 - i8 << 13;
        int i14 = i12 + i11;
        int i15 = i12 - i11;
        int i16 = i13 + i10;
        int i17 = i13 - i10;
        i12 = paramArrayOfInt[i4];
        i13 = paramArrayOfInt[i2];
        i10 = paramArrayOfInt[n];
        i11 = paramArrayOfInt[k];
        i9 = i12 + i11;
        i6 = i13 + i10;
        i7 = i12 + i10;
        i8 = i13 + i11;
        i5 = (i7 + i8) * 9633;
        i12 *= 2446;
        i13 *= 16819;
        i10 *= 25172;
        i11 *= 12299;
        i9 *= -7373;
        i6 *= -20995;
        i7 *= -16069;
        i8 *= -3196;
        i7 += i5;
        i8 += i5;
        i12 += i9 + i7;
        i13 += i6 + i8;
        i10 += i6 + i7;
        i11 += i9 + i8;
        paramArrayOfInt[j] = (i14 + i11 + 131072 >> 18);
        paramArrayOfInt[i4] = (i14 - i11 + 131072 >> 18);
        paramArrayOfInt[k] = (i16 + i10 + 131072 >> 18);
        paramArrayOfInt[i3] = (i16 - i10 + 131072 >> 18);
        paramArrayOfInt[m] = (i17 + i13 + 131072 >> 18);
        paramArrayOfInt[i2] = (i17 - i13 + 131072 >> 18);
        paramArrayOfInt[n] = (i15 + i12 + 131072 >> 18);
        paramArrayOfInt[i1] = (i15 - i12 + 131072 >> 18);
      }
    }
  }

  boolean isFileFormat(LEDataInputStream paramLEDataInputStream)
  {
    try
    {
      JPEGStartOfImage localJPEGStartOfImage = new JPEGStartOfImage(paramLEDataInputStream);
      paramLEDataInputStream.unread(localJPEGStartOfImage.reference);
      return localJPEGStartOfImage.verify();
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  boolean isZeroInColumn(int[] paramArrayOfInt, int paramInt)
  {
    return (paramArrayOfInt[(paramInt + 8)] == 0) && (paramArrayOfInt[(paramInt + 16)] == 0) && (paramArrayOfInt[(paramInt + 24)] == 0) && (paramArrayOfInt[(paramInt + 32)] == 0) && (paramArrayOfInt[(paramInt + 40)] == 0) && (paramArrayOfInt[(paramInt + 48)] == 0) && (paramArrayOfInt[(paramInt + 56)] == 0);
  }

  boolean isZeroInRow(int[] paramArrayOfInt, int paramInt)
  {
    return (paramArrayOfInt[(paramInt + 1)] == 0) && (paramArrayOfInt[(paramInt + 2)] == 0) && (paramArrayOfInt[(paramInt + 3)] == 0) && (paramArrayOfInt[(paramInt + 4)] == 0) && (paramArrayOfInt[(paramInt + 5)] == 0) && (paramArrayOfInt[(paramInt + 6)] == 0) && (paramArrayOfInt[(paramInt + 7)] == 0);
  }

  ImageData[] loadFromByteStream()
  {
    if (System.getProperty("org.eclipse.swt.internal.image.JPEGFileFormat_3.2") == null)
      return JPEGDecoder.loadFromByteStream(this.inputStream, this.loader);
    JPEGStartOfImage localJPEGStartOfImage = new JPEGStartOfImage(this.inputStream);
    if (!localJPEGStartOfImage.verify())
      SWT.error(40);
    this.restartInterval = 0;
    processTables();
    this.frameHeader = new JPEGFrameHeader(this.inputStream);
    if (!this.frameHeader.verify())
      SWT.error(40);
    this.imageWidth = this.frameHeader.getSamplesPerLine();
    this.imageHeight = this.frameHeader.getNumberOfLines();
    this.maxH = this.frameHeader.getMaxHFactor();
    this.maxV = this.frameHeader.getMaxVFactor();
    int i = this.maxH * 8;
    int j = this.maxV * 8;
    this.interleavedMcuCols = ((this.imageWidth + i - 1) / i);
    this.interleavedMcuRows = ((this.imageHeight + j - 1) / j);
    this.progressive = this.frameHeader.isProgressive();
    this.samplePrecision = this.frameHeader.getSamplePrecision();
    this.nComponents = this.frameHeader.getNumberOfImageComponents();
    this.frameComponents = this.frameHeader.componentParameters;
    this.componentIds = this.frameHeader.componentIdentifiers;
    this.imageComponents = new byte[this.nComponents][];
    if (this.progressive)
      this.dataUnits = new int[this.nComponents][][];
    else
      this.dataUnit = new int[64];
    for (int k = 0; k < this.nComponents; k++)
    {
      int[] arrayOfInt1 = this.frameComponents[this.componentIds[k]];
      int n = arrayOfInt1[3] * arrayOfInt1[4];
      this.imageComponents[k] = new byte[n];
      if (this.progressive)
        this.dataUnits[k] = new int[n][];
    }
    processTables();
    this.scanHeader = new JPEGScanHeader(this.inputStream);
    if (!this.scanHeader.verify())
      SWT.error(40);
    k = 0;
    int m = 0;
    int i1;
    while (m == 0)
    {
      resetInputBuffer();
      this.precedingDCs = new int[4];
      decodeScan();
      if ((this.progressive) && (this.loader.hasListeners()))
      {
        ImageData localImageData1 = createImageData();
        this.loader.notifyListeners(new ImageLoaderEvent(this.loader, localImageData1, k, false));
        k++;
      }
      i1 = 512 - this.bufferCurrentPosition - 1;
      if (i1 > 0)
      {
        localObject = new byte[i1];
        System.arraycopy(this.dataBuffer, this.bufferCurrentPosition + 1, localObject, 0, i1);
        try
        {
          this.inputStream.unread((byte[])localObject);
        }
        catch (IOException localIOException)
        {
          SWT.error(39, localIOException);
        }
      }
      Object localObject = processTables();
      if ((localObject == null) || (((JPEGSegment)localObject).getSegmentMarker() == 65497))
      {
        m = 1;
      }
      else
      {
        this.scanHeader = new JPEGScanHeader(this.inputStream);
        if (!this.scanHeader.verify())
          SWT.error(40);
      }
    }
    if (this.progressive)
    {
      for (i1 = 0; i1 < this.interleavedMcuRows; i1++)
        for (int i2 = 0; i2 < this.interleavedMcuCols; i2++)
          for (int i3 = 0; i3 < this.nComponents; i3++)
          {
            int[] arrayOfInt2 = this.frameComponents[this.componentIds[i3]];
            int i4 = arrayOfInt2[1];
            int i5 = arrayOfInt2[2];
            int i6 = arrayOfInt2[3];
            for (int i7 = 0; i7 < i5; i7++)
              for (int i8 = 0; i8 < i4; i8++)
              {
                int i9 = (i1 * i5 + i7) * i6 + i2 * i4 + i8;
                this.dataUnit = this.dataUnits[i3][i9];
                dequantize(this.dataUnit, i3);
                inverseDCT(this.dataUnit);
                storeData(this.dataUnit, i3, i2, i1, i4, i8, i5, i7);
              }
          }
      this.dataUnits = null;
    }
    ImageData localImageData2 = createImageData();
    if ((this.progressive) && (this.loader.hasListeners()))
      this.loader.notifyListeners(new ImageLoaderEvent(this.loader, localImageData2, k, true));
    return new ImageData[] { localImageData2 };
  }

  ImageData createImageData()
  {
    return ImageData.internal_new(this.imageWidth, this.imageHeight, this.nComponents * this.samplePrecision, setUpPalette(), this.nComponents == 1 ? 4 : 1, decodeImageComponents(), 0, null, null, -1, -1, 4, 0, 0, 0, 0);
  }

  int nextBit()
  {
    if (this.currentBitCount != 0)
    {
      this.currentBitCount -= 1;
      this.currentByte *= 2;
      if (this.currentByte > 255)
      {
        this.currentByte -= 256;
        return 1;
      }
      return 0;
    }
    this.bufferCurrentPosition += 1;
    if (this.bufferCurrentPosition >= 512)
    {
      resetInputBuffer();
      this.bufferCurrentPosition = 0;
    }
    this.currentByte = (this.dataBuffer[this.bufferCurrentPosition] & 0xFF);
    this.currentBitCount = 8;
    int i;
    if (this.bufferCurrentPosition == 511)
    {
      resetInputBuffer();
      this.currentBitCount = 8;
      i = this.dataBuffer[0];
    }
    else
    {
      i = this.dataBuffer[(this.bufferCurrentPosition + 1)];
    }
    if (this.currentByte == 255)
    {
      if (i == 0)
      {
        this.bufferCurrentPosition += 1;
        this.currentBitCount -= 1;
        this.currentByte *= 2;
        if (this.currentByte > 255)
        {
          this.currentByte -= 256;
          return 1;
        }
        return 0;
      }
      if ((i & 0xFF) + 65280 == 65500)
      {
        getDNL();
        return 0;
      }
      SWT.error(40);
      return 0;
    }
    this.currentBitCount -= 1;
    this.currentByte *= 2;
    if (this.currentByte > 255)
    {
      this.currentByte -= 256;
      return 1;
    }
    return 0;
  }

  void processRestartInterval()
  {
    do
    {
      this.bufferCurrentPosition += 1;
      if (this.bufferCurrentPosition > 511)
      {
        resetInputBuffer();
        this.bufferCurrentPosition = 0;
      }
      this.currentByte = (this.dataBuffer[this.bufferCurrentPosition] & 0xFF);
    }
    while (this.currentByte != 255);
    while (this.currentByte == 255)
    {
      this.bufferCurrentPosition += 1;
      if (this.bufferCurrentPosition > 511)
      {
        resetInputBuffer();
        this.bufferCurrentPosition = 0;
      }
      this.currentByte = (this.dataBuffer[this.bufferCurrentPosition] & 0xFF);
    }
    if (this.currentByte != (65488 + this.nextRestartNumber & 0xFF))
      SWT.error(40);
    this.bufferCurrentPosition += 1;
    if (this.bufferCurrentPosition > 511)
    {
      resetInputBuffer();
      this.bufferCurrentPosition = 0;
    }
    this.currentByte = (this.dataBuffer[this.bufferCurrentPosition] & 0xFF);
    this.currentBitCount = 8;
    this.restartsToGo = this.restartInterval;
    this.nextRestartNumber = (this.nextRestartNumber + 1 & 0x7);
    this.precedingDCs = new int[4];
    this.eobrun = 0;
  }

  JPEGSegment processTables()
  {
    while (true)
    {
      JPEGSegment localJPEGSegment = seekUnspecifiedMarker(this.inputStream);
      if (localJPEGSegment == null)
        return null;
      JPEGFrameHeader localJPEGFrameHeader = new JPEGFrameHeader(localJPEGSegment.reference);
      if (localJPEGFrameHeader.verify())
        return localJPEGSegment;
      int i = localJPEGSegment.getSegmentMarker();
      switch (i)
      {
      case 65496:
        SWT.error(40);
      case 65497:
      case 65498:
        return localJPEGSegment;
      case 65499:
        getDQT();
        break;
      case 65476:
        getDHT();
        break;
      case 65484:
        getDAC();
        break;
      case 65501:
        getDRI();
        break;
      case 65504:
        getAPP0();
        break;
      case 65534:
        getCOM();
        break;
      }
      skipSegmentFrom(this.inputStream);
    }
  }

  void quantizeData(int[] paramArrayOfInt, int paramInt)
  {
    int[] arrayOfInt = this.quantizationTables[this.frameComponents[this.componentIds[paramInt]][0]];
    for (int i = 0; i < paramArrayOfInt.length; i++)
    {
      int j = ZigZag8x8[i];
      int k = paramArrayOfInt[j];
      int m = k < 0 ? 0 - k : k;
      int n = arrayOfInt[i];
      int i1 = n >> 1;
      m += i1;
      if (m < n)
      {
        paramArrayOfInt[j] = 0;
      }
      else
      {
        m /= n;
        if (k >= 0)
          paramArrayOfInt[j] = m;
        else
          paramArrayOfInt[j] = (0 - m);
      }
    }
  }

  int receive(int paramInt)
  {
    int i = 0;
    for (int j = 0; j < paramInt; j++)
      i = i * 2 + nextBit();
    return i;
  }

  void resetInputBuffer()
  {
    if (this.dataBuffer == null)
      this.dataBuffer = new byte[512];
    try
    {
      this.inputStream.read(this.dataBuffer);
    }
    catch (IOException localIOException)
    {
      SWT.error(39, localIOException);
    }
    this.currentBitCount = 0;
    this.bufferCurrentPosition = -1;
  }

  void resetOutputBuffer()
  {
    if (this.dataBuffer == null)
      this.dataBuffer = new byte[512];
    else
      try
      {
        this.outputStream.write(this.dataBuffer, 0, this.bufferCurrentPosition);
      }
      catch (IOException localIOException)
      {
        SWT.error(39, localIOException);
      }
    this.bufferCurrentPosition = 0;
  }

  static JPEGSegment seekUnspecifiedMarker(LEDataInputStream paramLEDataInputStream)
  {
    byte[] arrayOfByte = new byte[2];
    try
    {
      do
      {
        do
          if (paramLEDataInputStream.read(arrayOfByte, 0, 1) != 1)
            return null;
        while (arrayOfByte[0] != -1);
        if (paramLEDataInputStream.read(arrayOfByte, 1, 1) != 1)
          return null;
      }
      while ((arrayOfByte[1] == -1) || (arrayOfByte[1] == 0));
      paramLEDataInputStream.unread(arrayOfByte);
      return new JPEGSegment(arrayOfByte);
    }
    catch (IOException localIOException)
    {
      SWT.error(39, localIOException);
    }
    return null;
  }

  PaletteData setUpPalette()
  {
    if (this.nComponents == 1)
    {
      RGB[] arrayOfRGB = new RGB[256];
      for (int i = 0; i < 256; i++)
        arrayOfRGB[i] = new RGB(i, i, i);
      return new PaletteData(arrayOfRGB);
    }
    return new PaletteData(255, 65280, 16711680);
  }

  static void skipSegmentFrom(LEDataInputStream paramLEDataInputStream)
  {
    try
    {
      byte[] arrayOfByte = new byte[4];
      JPEGSegment localJPEGSegment = new JPEGSegment(arrayOfByte);
      if (paramLEDataInputStream.read(arrayOfByte) != arrayOfByte.length)
        SWT.error(40);
      if ((arrayOfByte[0] != -1) || (arrayOfByte[1] == 0) || (arrayOfByte[1] == -1))
        SWT.error(40);
      int i = localJPEGSegment.getSegmentLength() - 2;
      paramLEDataInputStream.skip(i);
    }
    catch (Exception localException)
    {
      SWT.error(39, localException);
    }
  }

  void storeData(int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7)
  {
    byte[] arrayOfByte = this.imageComponents[paramInt1];
    int[] arrayOfInt = this.frameComponents[this.componentIds[paramInt1]];
    int i = arrayOfInt[3];
    int j = (paramInt3 * paramInt6 + paramInt7) * i * 8 + (paramInt2 * paramInt4 + paramInt5) * 8;
    int k = 0;
    for (int m = 0; m < 8; m++)
    {
      for (int n = 0; n < 8; n++)
      {
        int i1 = paramArrayOfInt[k] + 128;
        if (i1 < 0)
          i1 = 0;
        else if (i1 > 255)
          i1 = 255;
        arrayOfByte[(j + n)] = ((byte)i1);
        k++;
      }
      j += i;
    }
  }

  void unloadIntoByteStream(ImageLoader paramImageLoader)
  {
    ImageData localImageData = paramImageLoader.data[0];
    if (!new JPEGStartOfImage().writeToStream(this.outputStream))
      SWT.error(39);
    JPEGAppn localJPEGAppn = new JPEGAppn(new byte[] { -1, -32, 0, 16, 74, 70, 73, 70, 0, 1, 1, 0, 0, 1, 0, 1 });
    if (!localJPEGAppn.writeToStream(this.outputStream))
      SWT.error(39);
    this.quantizationTables = new int[4][];
    JPEGQuantizationTable localJPEGQuantizationTable1 = JPEGQuantizationTable.defaultChrominanceTable();
    localJPEGQuantizationTable1.scaleBy(this.encoderQFactor);
    int[] arrayOfInt = localJPEGQuantizationTable1.getQuantizationTablesKeys();
    int[][] arrayOfInt1 = localJPEGQuantizationTable1.getQuantizationTablesValues();
    for (int i = 0; i < arrayOfInt.length; i++)
      this.quantizationTables[arrayOfInt[i]] = arrayOfInt1[i];
    JPEGQuantizationTable localJPEGQuantizationTable2 = JPEGQuantizationTable.defaultLuminanceTable();
    localJPEGQuantizationTable2.scaleBy(this.encoderQFactor);
    arrayOfInt = localJPEGQuantizationTable2.getQuantizationTablesKeys();
    arrayOfInt1 = localJPEGQuantizationTable2.getQuantizationTablesValues();
    for (int j = 0; j < arrayOfInt.length; j++)
      this.quantizationTables[arrayOfInt[j]] = arrayOfInt1[j];
    if (!localJPEGQuantizationTable2.writeToStream(this.outputStream))
      SWT.error(39);
    if (!localJPEGQuantizationTable1.writeToStream(this.outputStream))
      SWT.error(39);
    int[][] arrayOfInt2;
    int[][] arrayOfInt3;
    int k;
    int m;
    if (localImageData.depth == 1)
    {
      j = 11;
      arrayOfInt2 = new int[1][];
      arrayOfInt2[0] = { 1, 1, 1 };
      arrayOfInt3 = new int[1][];
      arrayOfInt3[0] = new int[2];
      k = 8;
      this.nComponents = 1;
      m = 1;
    }
    else
    {
      j = 17;
      arrayOfInt2 = new int[3][];
      arrayOfInt2[0] = { 0, 2, 2 };
      arrayOfInt2[1] = { 1, 1, 1 };
      arrayOfInt2[2] = { 1, 1, 1 };
      arrayOfInt3 = new int[3][];
      arrayOfInt3[0] = new int[2];
      arrayOfInt3[1] = { 1, 1 };
      arrayOfInt3[2] = { 1, 1 };
      k = 12;
      this.nComponents = 3;
      m = 8;
    }
    this.imageWidth = localImageData.width;
    this.imageHeight = localImageData.height;
    this.frameHeader = new JPEGFrameHeader(new byte[19]);
    this.frameHeader.setSegmentMarker(65472);
    this.frameHeader.setSegmentLength(j);
    this.frameHeader.setSamplePrecision(m);
    this.frameHeader.setSamplesPerLine(this.imageWidth);
    this.frameHeader.setNumberOfLines(this.imageHeight);
    this.frameHeader.setNumberOfImageComponents(this.nComponents);
    this.frameHeader.componentParameters = arrayOfInt2;
    this.frameHeader.componentIdentifiers = new int[] { 0, 1, 2 };
    this.frameHeader.initializeContents();
    if (!this.frameHeader.writeToStream(this.outputStream))
      SWT.error(39);
    this.frameComponents = arrayOfInt2;
    this.componentIds = this.frameHeader.componentIdentifiers;
    this.maxH = this.frameHeader.getMaxHFactor();
    this.maxV = this.frameHeader.getMaxVFactor();
    int n = this.maxH * 8;
    int i1 = this.maxV * 8;
    this.interleavedMcuCols = ((this.imageWidth + n - 1) / n);
    this.interleavedMcuRows = ((this.imageHeight + i1 - 1) / i1);
    this.acHuffmanTables = new JPEGHuffmanTable[4];
    this.dcHuffmanTables = new JPEGHuffmanTable[4];
    JPEGHuffmanTable[] arrayOfJPEGHuffmanTable1 = { JPEGHuffmanTable.getDefaultDCLuminanceTable(), JPEGHuffmanTable.getDefaultDCChrominanceTable(), JPEGHuffmanTable.getDefaultACLuminanceTable(), JPEGHuffmanTable.getDefaultACChrominanceTable() };
    for (int i2 = 0; i2 < arrayOfJPEGHuffmanTable1.length; i2++)
    {
      JPEGHuffmanTable localJPEGHuffmanTable1 = arrayOfJPEGHuffmanTable1[i2];
      if (!localJPEGHuffmanTable1.writeToStream(this.outputStream))
        SWT.error(39);
      JPEGHuffmanTable[] arrayOfJPEGHuffmanTable2 = localJPEGHuffmanTable1.getAllTables();
      for (int i3 = 0; i3 < arrayOfJPEGHuffmanTable2.length; i3++)
      {
        JPEGHuffmanTable localJPEGHuffmanTable2 = arrayOfJPEGHuffmanTable2[i3];
        if (localJPEGHuffmanTable2.getTableClass() == 0)
          this.dcHuffmanTables[localJPEGHuffmanTable2.getTableIdentifier()] = localJPEGHuffmanTable2;
        else
          this.acHuffmanTables[localJPEGHuffmanTable2.getTableIdentifier()] = localJPEGHuffmanTable2;
      }
    }
    this.precedingDCs = new int[4];
    this.scanHeader = new JPEGScanHeader(new byte[14]);
    this.scanHeader.setSegmentMarker(65498);
    this.scanHeader.setSegmentLength(k);
    this.scanHeader.setNumberOfImageComponents(this.nComponents);
    this.scanHeader.setStartOfSpectralSelection(0);
    this.scanHeader.setEndOfSpectralSelection(63);
    this.scanHeader.componentParameters = arrayOfInt3;
    this.scanHeader.initializeContents();
    if (!this.scanHeader.writeToStream(this.outputStream))
      SWT.error(39);
    convertImageToYCbCr(localImageData);
    resetOutputBuffer();
    this.currentByte = 0;
    this.currentBitCount = 0;
    encodeScan();
    if (!new JPEGEndOfImage().writeToStream(this.outputStream))
      SWT.error(39);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.image.JPEGFileFormat
 * JD-Core Version:    0.6.2
 */