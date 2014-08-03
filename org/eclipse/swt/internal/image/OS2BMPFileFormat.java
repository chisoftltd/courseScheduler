package org.eclipse.swt.internal.image;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;

public final class OS2BMPFileFormat extends FileFormat
{
  static final int BMPFileHeaderSize = 14;
  static final int BMPHeaderFixedSize = 12;
  int width;
  int height;
  int bitCount;

  boolean isFileFormat(LEDataInputStream paramLEDataInputStream)
  {
    try
    {
      byte[] arrayOfByte = new byte[18];
      paramLEDataInputStream.read(arrayOfByte);
      paramLEDataInputStream.unread(arrayOfByte);
      int i = arrayOfByte[14] & 0xFF | (arrayOfByte[15] & 0xFF) << 8 | (arrayOfByte[16] & 0xFF) << 16 | (arrayOfByte[17] & 0xFF) << 24;
      return (arrayOfByte[0] == 66) && (arrayOfByte[1] == 77) && (i == 12);
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  byte[] loadData(byte[] paramArrayOfByte)
  {
    int i = (this.width * this.bitCount + 7) / 8;
    i = (i + 3) / 4 * 4;
    byte[] arrayOfByte = loadData(paramArrayOfByte, i);
    flipScanLines(arrayOfByte, i, this.height);
    return arrayOfByte;
  }

  byte[] loadData(byte[] paramArrayOfByte, int paramInt)
  {
    int i = this.height * paramInt;
    byte[] arrayOfByte = new byte[i];
    try
    {
      if (this.inputStream.read(arrayOfByte) != i)
        SWT.error(40);
    }
    catch (IOException localIOException)
    {
      SWT.error(39, localIOException);
    }
    return arrayOfByte;
  }

  int[] loadFileHeader()
  {
    int[] arrayOfInt = new int[5];
    try
    {
      arrayOfInt[0] = this.inputStream.readShort();
      arrayOfInt[1] = this.inputStream.readInt();
      arrayOfInt[2] = this.inputStream.readShort();
      arrayOfInt[3] = this.inputStream.readShort();
      arrayOfInt[4] = this.inputStream.readInt();
    }
    catch (IOException localIOException)
    {
      SWT.error(39, localIOException);
    }
    if (arrayOfInt[0] != 19778)
      SWT.error(40);
    return arrayOfInt;
  }

  ImageData[] loadFromByteStream()
  {
    int[] arrayOfInt = loadFileHeader();
    byte[] arrayOfByte1 = new byte[12];
    try
    {
      this.inputStream.read(arrayOfByte1);
    }
    catch (Exception localException)
    {
      SWT.error(39, localException);
    }
    this.width = (arrayOfByte1[4] & 0xFF | (arrayOfByte1[5] & 0xFF) << 8);
    this.height = (arrayOfByte1[6] & 0xFF | (arrayOfByte1[7] & 0xFF) << 8);
    this.bitCount = (arrayOfByte1[10] & 0xFF | (arrayOfByte1[11] & 0xFF) << 8);
    PaletteData localPaletteData = loadPalette(arrayOfByte1);
    if (this.inputStream.getPosition() < arrayOfInt[4])
      try
      {
        this.inputStream.skip(arrayOfInt[4] - this.inputStream.getPosition());
      }
      catch (IOException localIOException)
      {
        SWT.error(39, localIOException);
      }
    byte[] arrayOfByte2 = loadData(arrayOfByte1);
    int i = 7;
    return new ImageData[] { ImageData.internal_new(this.width, this.height, this.bitCount, localPaletteData, 4, arrayOfByte2, 0, null, null, -1, -1, i, 0, 0, 0, 0) };
  }

  PaletteData loadPalette(byte[] paramArrayOfByte)
  {
    if (this.bitCount <= 8)
    {
      int i = 1 << this.bitCount;
      byte[] arrayOfByte = new byte[i * 3];
      try
      {
        if (this.inputStream.read(arrayOfByte) != arrayOfByte.length)
          SWT.error(40);
      }
      catch (IOException localIOException)
      {
        SWT.error(39, localIOException);
      }
      return paletteFromBytes(arrayOfByte, i);
    }
    if (this.bitCount == 16)
      return new PaletteData(31744, 992, 31);
    if (this.bitCount == 24)
      return new PaletteData(255, 65280, 16711680);
    return new PaletteData(65280, 16711680, -16777216);
  }

  PaletteData paletteFromBytes(byte[] paramArrayOfByte, int paramInt)
  {
    int i = 0;
    RGB[] arrayOfRGB = new RGB[paramInt];
    for (int j = 0; j < paramInt; j++)
    {
      arrayOfRGB[j] = new RGB(paramArrayOfByte[(i + 2)] & 0xFF, paramArrayOfByte[(i + 1)] & 0xFF, paramArrayOfByte[i] & 0xFF);
      i += 3;
    }
    return new PaletteData(arrayOfRGB);
  }

  static byte[] paletteToBytes(PaletteData paramPaletteData)
  {
    int i = paramPaletteData.colors.length < 256 ? paramPaletteData.colors.length : paramPaletteData.colors == null ? 0 : 256;
    byte[] arrayOfByte = new byte[i * 3];
    int j = 0;
    for (int k = 0; k < i; k++)
    {
      RGB localRGB = paramPaletteData.colors[k];
      arrayOfByte[j] = ((byte)localRGB.blue);
      arrayOfByte[(j + 1)] = ((byte)localRGB.green);
      arrayOfByte[(j + 2)] = ((byte)localRGB.red);
      j += 3;
    }
    return arrayOfByte;
  }

  int unloadData(ImageData paramImageData, OutputStream paramOutputStream)
  {
    int i = 0;
    try
    {
      int j = (paramImageData.width * paramImageData.depth + 7) / 8;
      i = (j + 3) / 4 * 4;
      int k = 32678 / i;
      byte[] arrayOfByte1 = new byte[k * i];
      byte[] arrayOfByte2 = paramImageData.data;
      int m = paramImageData.bytesPerLine;
      int n = m * (paramImageData.height - 1);
      int i1;
      int i2;
      int i3;
      int i4;
      int i5;
      if (paramImageData.depth == 16)
      {
        i1 = 0;
        while (i1 < paramImageData.height)
        {
          i2 = paramImageData.height - i1;
          if (k < i2)
            i2 = k;
          i3 = 0;
          for (i4 = 0; i4 < i2; i4++)
          {
            for (i5 = 0; i5 < j; i5 += 2)
            {
              arrayOfByte1[(i3 + i5 + 1)] = arrayOfByte2[(n + i5 + 1)];
              arrayOfByte1[(i3 + i5)] = arrayOfByte2[(n + i5)];
            }
            i3 += i;
            n -= m;
          }
          paramOutputStream.write(arrayOfByte1, 0, i3);
          i1 += k;
        }
      }
      else
      {
        i1 = 0;
        while (i1 < paramImageData.height)
        {
          i2 = paramImageData.height - i1;
          i3 = i2 < k ? i2 : k;
          i4 = 0;
          for (i5 = 0; i5 < i3; i5++)
          {
            System.arraycopy(arrayOfByte2, n, arrayOfByte1, i4, j);
            i4 += i;
            n -= m;
          }
          paramOutputStream.write(arrayOfByte1, 0, i4);
          i1 += k;
        }
      }
    }
    catch (IOException localIOException)
    {
      SWT.error(39, localIOException);
    }
    return i * paramImageData.height;
  }

  void unloadIntoByteStream(ImageLoader paramImageLoader)
  {
    ImageData localImageData = paramImageLoader.data[0];
    if ((localImageData.depth != 1) && (localImageData.depth != 4) && (localImageData.depth != 8) && (localImageData.depth != 16) && (localImageData.depth != 24) && (localImageData.depth != 32))
      SWT.error(38);
    PaletteData localPaletteData = localImageData.palette;
    int i;
    byte[] arrayOfByte1;
    if ((localImageData.depth == 16) || (localImageData.depth == 24) || (localImageData.depth == 32))
    {
      if (!localPaletteData.isDirect)
        SWT.error(40);
      i = 0;
      arrayOfByte1 = (byte[])null;
    }
    else
    {
      if (localPaletteData.isDirect)
        SWT.error(40);
      i = localPaletteData.colors.length;
      arrayOfByte1 = paletteToBytes(localPaletteData);
    }
    int j = 26;
    int[] arrayOfInt = new int[5];
    arrayOfInt[0] = 19778;
    arrayOfInt[1] = 0;
    arrayOfInt[2] = 0;
    arrayOfInt[3] = 0;
    arrayOfInt[4] = j;
    if (arrayOfByte1 != null)
      arrayOfInt[4] += arrayOfByte1.length;
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    unloadData(localImageData, localByteArrayOutputStream);
    byte[] arrayOfByte2 = localByteArrayOutputStream.toByteArray();
    arrayOfInt[1] = (arrayOfInt[4] + arrayOfByte2.length);
    try
    {
      this.outputStream.writeShort(arrayOfInt[0]);
      this.outputStream.writeInt(arrayOfInt[1]);
      this.outputStream.writeShort(arrayOfInt[2]);
      this.outputStream.writeShort(arrayOfInt[3]);
      this.outputStream.writeInt(arrayOfInt[4]);
    }
    catch (IOException localIOException1)
    {
      SWT.error(39, localIOException1);
    }
    try
    {
      this.outputStream.writeInt(12);
      this.outputStream.writeShort(localImageData.width);
      this.outputStream.writeShort(localImageData.height);
      this.outputStream.writeShort(1);
      this.outputStream.writeShort((short)localImageData.depth);
    }
    catch (IOException localIOException2)
    {
      SWT.error(39, localIOException2);
    }
    if (i > 0)
      try
      {
        this.outputStream.write(arrayOfByte1);
      }
      catch (IOException localIOException3)
      {
        SWT.error(39, localIOException3);
      }
    try
    {
      this.outputStream.write(arrayOfByte2);
    }
    catch (IOException localIOException4)
    {
      SWT.error(39, localIOException4);
    }
  }

  void flipScanLines(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    int i = 0;
    int j = (paramInt2 - 1) * paramInt1;
    for (int k = 0; k < paramInt2 / 2; k++)
    {
      for (int m = 0; m < paramInt1; m++)
      {
        int n = paramArrayOfByte[(m + i)];
        paramArrayOfByte[(m + i)] = paramArrayOfByte[(m + j)];
        paramArrayOfByte[(m + j)] = n;
      }
      i += paramInt1;
      j -= paramInt1;
    }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.image.OS2BMPFileFormat
 * JD-Core Version:    0.6.2
 */