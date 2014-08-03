package org.eclipse.swt.internal.image;

import java.io.IOException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.PaletteData;

public final class WinICOFileFormat extends FileFormat
{
  byte[] bitInvertData(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    for (int i = paramInt1; i < paramInt2; i++)
      paramArrayOfByte[i] = ((byte)(255 - paramArrayOfByte[(i - paramInt1)]));
    return paramArrayOfByte;
  }

  static final byte[] convertPad(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    if (paramInt4 == paramInt5)
      return paramArrayOfByte;
    int i = (paramInt1 * paramInt3 + 7) / 8;
    int j = (i + (paramInt4 - 1)) / paramInt4 * paramInt4;
    int k = (i + (paramInt5 - 1)) / paramInt5 * paramInt5;
    byte[] arrayOfByte = new byte[paramInt2 * k];
    int m = 0;
    int n = 0;
    for (int i1 = 0; i1 < paramInt2; i1++)
    {
      System.arraycopy(paramArrayOfByte, m, arrayOfByte, n, k);
      m += j;
      n += k;
    }
    return arrayOfByte;
  }

  int iconSize(ImageData paramImageData)
  {
    int i = (paramImageData.width * paramImageData.depth + 31) / 32 * 4;
    int j = (paramImageData.width + 31) / 32 * 4;
    int k = (i + j) * paramImageData.height;
    int m = paramImageData.palette.colors != null ? paramImageData.palette.colors.length * 4 : 0;
    return 40 + m + k;
  }

  boolean isFileFormat(LEDataInputStream paramLEDataInputStream)
  {
    try
    {
      byte[] arrayOfByte = new byte[4];
      paramLEDataInputStream.read(arrayOfByte);
      paramLEDataInputStream.unread(arrayOfByte);
      return (arrayOfByte[0] == 0) && (arrayOfByte[1] == 0) && (arrayOfByte[2] == 1) && (arrayOfByte[3] == 0);
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  boolean isValidIcon(ImageData paramImageData)
  {
    switch (paramImageData.depth)
    {
    case 1:
    case 4:
    case 8:
      if (paramImageData.palette.isDirect)
        return false;
      int i = paramImageData.palette.colors.length;
      return (i == 2) || (i == 16) || (i == 32) || (i == 256);
    case 24:
    case 32:
      return paramImageData.palette.isDirect;
    }
    return false;
  }

  int loadFileHeader(LEDataInputStream paramLEDataInputStream)
  {
    int[] arrayOfInt = new int[3];
    try
    {
      arrayOfInt[0] = paramLEDataInputStream.readShort();
      arrayOfInt[1] = paramLEDataInputStream.readShort();
      arrayOfInt[2] = paramLEDataInputStream.readShort();
    }
    catch (IOException localIOException)
    {
      SWT.error(39, localIOException);
    }
    if ((arrayOfInt[0] != 0) || (arrayOfInt[1] != 1))
      SWT.error(40);
    int i = arrayOfInt[2];
    if (i <= 0)
      SWT.error(40);
    return i;
  }

  int loadFileHeader(LEDataInputStream paramLEDataInputStream, boolean paramBoolean)
  {
    int[] arrayOfInt = new int[3];
    try
    {
      if (paramBoolean)
      {
        arrayOfInt[0] = paramLEDataInputStream.readShort();
        arrayOfInt[1] = paramLEDataInputStream.readShort();
      }
      else
      {
        arrayOfInt[0] = 0;
        arrayOfInt[1] = 1;
      }
      arrayOfInt[2] = paramLEDataInputStream.readShort();
    }
    catch (IOException localIOException)
    {
      SWT.error(39, localIOException);
    }
    if ((arrayOfInt[0] != 0) || (arrayOfInt[1] != 1))
      SWT.error(40);
    int i = arrayOfInt[2];
    if (i <= 0)
      SWT.error(40);
    return i;
  }

  ImageData[] loadFromByteStream()
  {
    int i = loadFileHeader(this.inputStream);
    int[][] arrayOfInt = loadIconHeaders(i);
    ImageData[] arrayOfImageData = new ImageData[arrayOfInt.length];
    for (int j = 0; j < arrayOfImageData.length; j++)
      arrayOfImageData[j] = loadIcon(arrayOfInt[j]);
    return arrayOfImageData;
  }

  ImageData loadIcon(int[] paramArrayOfInt)
  {
    byte[] arrayOfByte1 = loadInfoHeader(paramArrayOfInt);
    WinBMPFileFormat localWinBMPFileFormat = new WinBMPFileFormat();
    localWinBMPFileFormat.inputStream = this.inputStream;
    PaletteData localPaletteData = localWinBMPFileFormat.loadPalette(arrayOfByte1);
    byte[] arrayOfByte2 = localWinBMPFileFormat.loadData(arrayOfByte1);
    int i = arrayOfByte1[4] & 0xFF | (arrayOfByte1[5] & 0xFF) << 8 | (arrayOfByte1[6] & 0xFF) << 16 | (arrayOfByte1[7] & 0xFF) << 24;
    int j = arrayOfByte1[8] & 0xFF | (arrayOfByte1[9] & 0xFF) << 8 | (arrayOfByte1[10] & 0xFF) << 16 | (arrayOfByte1[11] & 0xFF) << 24;
    if (j < 0)
      j = -j;
    int k = arrayOfByte1[14] & 0xFF | (arrayOfByte1[15] & 0xFF) << 8;
    arrayOfByte1[14] = 1;
    arrayOfByte1[15] = 0;
    byte[] arrayOfByte3 = localWinBMPFileFormat.loadData(arrayOfByte1);
    arrayOfByte3 = convertPad(arrayOfByte3, i, j, 1, 4, 2);
    bitInvertData(arrayOfByte3, 0, arrayOfByte3.length);
    return ImageData.internal_new(i, j, k, localPaletteData, 4, arrayOfByte2, 2, arrayOfByte3, null, -1, -1, 3, 0, 0, 0, 0);
  }

  int[][] loadIconHeaders(int paramInt)
  {
    int[][] arrayOfInt = new int[paramInt][7];
    try
    {
      for (int i = 0; i < paramInt; i++)
      {
        arrayOfInt[i][0] = this.inputStream.read();
        arrayOfInt[i][1] = this.inputStream.read();
        arrayOfInt[i][2] = this.inputStream.readShort();
        arrayOfInt[i][3] = this.inputStream.readShort();
        arrayOfInt[i][4] = this.inputStream.readShort();
        arrayOfInt[i][5] = this.inputStream.readInt();
        arrayOfInt[i][6] = this.inputStream.readInt();
      }
    }
    catch (IOException localIOException)
    {
      SWT.error(39, localIOException);
    }
    return arrayOfInt;
  }

  byte[] loadInfoHeader(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    int j = paramArrayOfInt[1];
    int k = paramArrayOfInt[2];
    if (k == 0)
      k = 256;
    if ((k != 2) && (k != 8) && (k != 16) && (k != 32) && (k != 256))
      SWT.error(40);
    if (this.inputStream.getPosition() < paramArrayOfInt[6])
      try
      {
        this.inputStream.skip(paramArrayOfInt[6] - this.inputStream.getPosition());
      }
      catch (IOException localIOException1)
      {
        SWT.error(39, localIOException1);
        return null;
      }
    byte[] arrayOfByte = new byte[40];
    try
    {
      this.inputStream.read(arrayOfByte);
    }
    catch (IOException localIOException2)
    {
      SWT.error(39, localIOException2);
    }
    if ((arrayOfByte[12] & 0xFF | (arrayOfByte[13] & 0xFF) << 8) != 1)
      SWT.error(40);
    int m = arrayOfByte[4] & 0xFF | (arrayOfByte[5] & 0xFF) << 8 | (arrayOfByte[6] & 0xFF) << 16 | (arrayOfByte[7] & 0xFF) << 24;
    int n = arrayOfByte[8] & 0xFF | (arrayOfByte[9] & 0xFF) << 8 | (arrayOfByte[10] & 0xFF) << 16 | (arrayOfByte[11] & 0xFF) << 24;
    int i1 = arrayOfByte[14] & 0xFF | (arrayOfByte[15] & 0xFF) << 8;
    if ((j == n) && (i1 == 1))
      j /= 2;
    if ((i != m) || (j * 2 != n) || ((i1 != 1) && (i1 != 4) && (i1 != 8) && (i1 != 24) && (i1 != 32)))
      SWT.error(40);
    arrayOfByte[8] = ((byte)(j & 0xFF));
    arrayOfByte[9] = ((byte)(j >> 8 & 0xFF));
    arrayOfByte[10] = ((byte)(j >> 16 & 0xFF));
    arrayOfByte[11] = ((byte)(j >> 24 & 0xFF));
    return arrayOfByte;
  }

  void unloadIcon(ImageData paramImageData)
  {
    int i = ((paramImageData.width * paramImageData.depth + 31) / 32 * 4 + (paramImageData.width + 31) / 32 * 4) * paramImageData.height;
    try
    {
      this.outputStream.writeInt(40);
      this.outputStream.writeInt(paramImageData.width);
      this.outputStream.writeInt(paramImageData.height * 2);
      this.outputStream.writeShort(1);
      this.outputStream.writeShort((short)paramImageData.depth);
      this.outputStream.writeInt(0);
      this.outputStream.writeInt(i);
      this.outputStream.writeInt(0);
      this.outputStream.writeInt(0);
      this.outputStream.writeInt(paramImageData.palette.colors != null ? paramImageData.palette.colors.length : 0);
      this.outputStream.writeInt(0);
    }
    catch (IOException localIOException1)
    {
      SWT.error(39, localIOException1);
    }
    byte[] arrayOfByte = WinBMPFileFormat.paletteToBytes(paramImageData.palette);
    try
    {
      this.outputStream.write(arrayOfByte);
    }
    catch (IOException localIOException2)
    {
      SWT.error(39, localIOException2);
    }
    unloadShapeData(paramImageData);
    unloadMaskData(paramImageData);
  }

  void unloadIconHeader(ImageData paramImageData)
  {
    int i = 16;
    int j = i + 6;
    int k = iconSize(paramImageData);
    try
    {
      this.outputStream.write(paramImageData.width);
      this.outputStream.write(paramImageData.height);
      this.outputStream.writeShort(paramImageData.palette.colors != null ? paramImageData.palette.colors.length : 0);
      this.outputStream.writeShort(0);
      this.outputStream.writeShort(0);
      this.outputStream.writeInt(k);
      this.outputStream.writeInt(j);
    }
    catch (IOException localIOException)
    {
      SWT.error(39, localIOException);
    }
  }

  void unloadIntoByteStream(ImageLoader paramImageLoader)
  {
    ImageData localImageData = paramImageLoader.data[0];
    if (!isValidIcon(localImageData))
      SWT.error(40);
    try
    {
      this.outputStream.writeShort(0);
      this.outputStream.writeShort(1);
      this.outputStream.writeShort(1);
    }
    catch (IOException localIOException)
    {
      SWT.error(39, localIOException);
    }
    unloadIconHeader(localImageData);
    unloadIcon(localImageData);
  }

  void unloadMaskData(ImageData paramImageData)
  {
    ImageData localImageData = paramImageData.getTransparencyMask();
    int i = (paramImageData.width + 7) / 8;
    int j = localImageData.scanlinePad;
    int k = (i + j - 1) / j * j;
    int m = (i + 3) / 4 * 4;
    byte[] arrayOfByte1 = new byte[m];
    int n = (paramImageData.height - 1) * k;
    byte[] arrayOfByte2 = localImageData.data;
    try
    {
      for (int i1 = 0; i1 < paramImageData.height; i1++)
      {
        System.arraycopy(arrayOfByte2, n, arrayOfByte1, 0, i);
        bitInvertData(arrayOfByte1, 0, i);
        this.outputStream.write(arrayOfByte1, 0, m);
        n -= k;
      }
    }
    catch (IOException localIOException)
    {
      SWT.error(39, localIOException);
    }
  }

  void unloadShapeData(ImageData paramImageData)
  {
    int i = (paramImageData.width * paramImageData.depth + 7) / 8;
    int j = paramImageData.scanlinePad;
    int k = (i + j - 1) / j * j;
    int m = (i + 3) / 4 * 4;
    byte[] arrayOfByte1 = new byte[m];
    int n = (paramImageData.height - 1) * k;
    byte[] arrayOfByte2 = paramImageData.data;
    try
    {
      for (int i1 = 0; i1 < paramImageData.height; i1++)
      {
        System.arraycopy(arrayOfByte2, n, arrayOfByte1, 0, i);
        this.outputStream.write(arrayOfByte1, 0, m);
        n -= k;
      }
    }
    catch (IOException localIOException)
    {
      SWT.error(39, localIOException);
    }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.image.WinICOFileFormat
 * JD-Core Version:    0.6.2
 */