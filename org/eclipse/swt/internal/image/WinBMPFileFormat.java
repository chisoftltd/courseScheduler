package org.eclipse.swt.internal.image;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;

public final class WinBMPFileFormat extends FileFormat
{
  static final int BMPFileHeaderSize = 14;
  static final int BMPHeaderFixedSize = 40;
  int importantColors;
  Point pelsPerMeter = new Point(0, 0);

  int compress(int paramInt1, byte[] paramArrayOfByte1, int paramInt2, int paramInt3, byte[] paramArrayOfByte2, boolean paramBoolean)
  {
    if (paramInt1 == 1)
      return compressRLE8Data(paramArrayOfByte1, paramInt2, paramInt3, paramArrayOfByte2, paramBoolean);
    if (paramInt1 == 2)
      return compressRLE4Data(paramArrayOfByte1, paramInt2, paramInt3, paramArrayOfByte2, paramBoolean);
    SWT.error(40);
    return 0;
  }

  int compressRLE4Data(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, boolean paramBoolean)
  {
    int i = paramInt1;
    int j = paramInt1 + paramInt2;
    int k = 0;
    int m = 0;
    while (i < j)
    {
      int n = j - i - 1;
      if (n > 127)
        n = 127;
      for (int i2 = 0; i2 < n; i2++)
        if (paramArrayOfByte1[(i + i2)] == paramArrayOfByte1[(i + i2 + 1)])
          break;
      if ((i2 < 127) && (i2 == n))
        i2++;
      switch (i2)
      {
      case 0:
        break;
      case 1:
        paramArrayOfByte2[k] = 2;
        k++;
        paramArrayOfByte2[k] = paramArrayOfByte1[i];
        k++;
        i++;
        m += 2;
        break;
      default:
        paramArrayOfByte2[k] = 0;
        k++;
        paramArrayOfByte2[k] = ((byte)(i2 + i2));
        k++;
        for (int i1 = i2; i1 > 0; i1--)
        {
          paramArrayOfByte2[k] = paramArrayOfByte1[i];
          k++;
          i++;
        }
        m += 2 + i2;
        if ((i2 & 0x1) != 0)
        {
          paramArrayOfByte2[k] = 0;
          k++;
          m++;
        }
        break;
      }
      n = j - i;
      if (n > 0)
      {
        if (n > 127)
          n = 127;
        int i3 = paramArrayOfByte1[i];
        for (i2 = 1; i2 < n; i2++)
          if (paramArrayOfByte1[(i + i2)] != i3)
            break;
        paramArrayOfByte2[k] = ((byte)(i2 + i2));
        k++;
        paramArrayOfByte2[k] = i3;
        k++;
        i += i2;
        m += 2;
      }
    }
    paramArrayOfByte2[k] = 0;
    k++;
    if (paramBoolean)
    {
      paramArrayOfByte2[k] = 1;
      k++;
    }
    else
    {
      paramArrayOfByte2[k] = 0;
      k++;
    }
    m += 2;
    return m;
  }

  int compressRLE8Data(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, boolean paramBoolean)
  {
    int i = paramInt1;
    int j = paramInt1 + paramInt2;
    int k = 0;
    int m = 0;
    while (i < j)
    {
      int n = j - i - 1;
      if (n > 254)
        n = 254;
      for (int i2 = 0; i2 < n; i2++)
        if (paramArrayOfByte1[(i + i2)] == paramArrayOfByte1[(i + i2 + 1)])
          break;
      if (i2 == n)
        i2++;
      switch (i2)
      {
      case 0:
        break;
      case 2:
        paramArrayOfByte2[k] = 1;
        k++;
        paramArrayOfByte2[k] = paramArrayOfByte1[i];
        k++;
        i++;
        m += 2;
      case 1:
        paramArrayOfByte2[k] = 1;
        k++;
        paramArrayOfByte2[k] = paramArrayOfByte1[i];
        k++;
        i++;
        m += 2;
        break;
      default:
        paramArrayOfByte2[k] = 0;
        k++;
        paramArrayOfByte2[k] = ((byte)i2);
        k++;
        for (int i1 = i2; i1 > 0; i1--)
        {
          paramArrayOfByte2[k] = paramArrayOfByte1[i];
          k++;
          i++;
        }
        m += 2 + i2;
        if ((i2 & 0x1) != 0)
        {
          paramArrayOfByte2[k] = 0;
          k++;
          m++;
        }
        break;
      }
      n = j - i;
      if (n > 0)
      {
        if (n > 255)
          n = 255;
        int i3 = paramArrayOfByte1[i];
        for (i2 = 1; i2 < n; i2++)
          if (paramArrayOfByte1[(i + i2)] != i3)
            break;
        paramArrayOfByte2[k] = ((byte)i2);
        k++;
        paramArrayOfByte2[k] = i3;
        k++;
        i += i2;
        m += 2;
      }
    }
    paramArrayOfByte2[k] = 0;
    k++;
    if (paramBoolean)
    {
      paramArrayOfByte2[k] = 1;
      k++;
    }
    else
    {
      paramArrayOfByte2[k] = 0;
      k++;
    }
    m += 2;
    return m;
  }

  void convertPixelsToBGR(ImageData paramImageData, byte[] paramArrayOfByte)
  {
    byte[] arrayOfByte = paramImageData.data;
    PaletteData localPaletteData = paramImageData.palette;
    for (int i = 0; i < paramImageData.height; i++)
    {
      int k = 0;
      int m = i;
      int n = paramImageData.depth / 8;
      int j = i * paramImageData.bytesPerLine;
      for (int i1 = 0; i1 < paramImageData.width; i1++)
      {
        int i2 = 0;
        switch (paramImageData.depth)
        {
        case 32:
          i2 = (arrayOfByte[j] & 0xFF) << 24 | (arrayOfByte[(j + 1)] & 0xFF) << 16 | (arrayOfByte[(j + 2)] & 0xFF) << 8 | arrayOfByte[(j + 3)] & 0xFF;
          break;
        case 24:
          i2 = (arrayOfByte[j] & 0xFF) << 16 | (arrayOfByte[(j + 1)] & 0xFF) << 8 | arrayOfByte[(j + 2)] & 0xFF;
          break;
        case 16:
          i2 = (arrayOfByte[(j + 1)] & 0xFF) << 8 | arrayOfByte[j] & 0xFF;
          break;
        default:
          SWT.error(38);
        }
        int i3;
        int i4;
        int i5;
        if (paramImageData.depth == 16)
        {
          i3 = i2 & localPaletteData.redMask;
          i3 = localPaletteData.redShift < 0 ? i3 >>> -localPaletteData.redShift : i3 << localPaletteData.redShift;
          i4 = i2 & localPaletteData.greenMask;
          i4 = localPaletteData.greenShift < 0 ? i4 >>> -localPaletteData.greenShift : i4 << localPaletteData.greenShift;
          i4 &= 248;
          i5 = i2 & localPaletteData.blueMask;
          i5 = localPaletteData.blueShift < 0 ? i5 >>> -localPaletteData.blueShift : i5 << localPaletteData.blueShift;
          int i6 = i3 << 7 | i4 << 2 | i5 >> 3;
          paramArrayOfByte[j] = ((byte)(i6 & 0xFF));
          paramArrayOfByte[(j + 1)] = ((byte)(i6 >> 8 & 0xFF));
        }
        else
        {
          i3 = i2 & localPaletteData.blueMask;
          paramArrayOfByte[j] = ((byte)(localPaletteData.blueShift < 0 ? i3 >>> -localPaletteData.blueShift : i3 << localPaletteData.blueShift));
          i4 = i2 & localPaletteData.greenMask;
          paramArrayOfByte[(j + 1)] = ((byte)(localPaletteData.greenShift < 0 ? i4 >>> -localPaletteData.greenShift : i4 << localPaletteData.greenShift));
          i5 = i2 & localPaletteData.redMask;
          paramArrayOfByte[(j + 2)] = ((byte)(localPaletteData.redShift < 0 ? i5 >>> -localPaletteData.redShift : i5 << localPaletteData.redShift));
          if (n == 4)
            paramArrayOfByte[(j + 3)] = 0;
        }
        k++;
        if (k >= paramImageData.width)
        {
          m++;
          j = m * paramImageData.bytesPerLine;
          k = 0;
        }
        else
        {
          j += n;
        }
      }
    }
  }

  void decompressData(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt1, int paramInt2)
  {
    if (paramInt2 == 1)
    {
      if (decompressRLE8Data(paramArrayOfByte1, paramArrayOfByte1.length, paramInt1, paramArrayOfByte2, paramArrayOfByte2.length) <= 0)
        SWT.error(40);
      return;
    }
    if (paramInt2 == 2)
    {
      if (decompressRLE4Data(paramArrayOfByte1, paramArrayOfByte1.length, paramInt1, paramArrayOfByte2, paramArrayOfByte2.length) <= 0)
        SWT.error(40);
      return;
    }
    SWT.error(40);
  }

  int decompressRLE4Data(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3)
  {
    int i = 0;
    int j = paramInt1;
    int k = 0;
    int m = paramInt3;
    int n = 0;
    int i1 = 0;
    while (i < j)
    {
      int i2 = paramArrayOfByte1[i] & 0xFF;
      i++;
      int i3;
      if (i2 == 0)
      {
        i2 = paramArrayOfByte1[i] & 0xFF;
        i++;
        switch (i2)
        {
        case 0:
          i1++;
          n = 0;
          k = i1 * paramInt2;
          if (k <= m)
            continue;
          return -1;
        case 1:
          return 1;
        case 2:
          n += (paramArrayOfByte1[i] & 0xFF);
          i++;
          i1 += (paramArrayOfByte1[i] & 0xFF);
          i++;
          k = i1 * paramInt2 + n / 2;
          if (k <= m)
            continue;
          return -1;
        default:
          if ((i2 & 0x1) != 0)
            return -1;
          n += i2;
          i2 /= 2;
          if (i2 > j - i)
            return -1;
          if (i2 > m - k)
            return -1;
          for (i3 = 0; i3 < i2; i3++)
          {
            paramArrayOfByte2[k] = paramArrayOfByte1[i];
            k++;
            i++;
          }
          if ((i & 0x1) == 0)
            continue;
          i++;
          break;
        }
      }
      else
      {
        if ((i2 & 0x1) != 0)
          return -1;
        n += i2;
        i2 /= 2;
        i3 = paramArrayOfByte1[i];
        i++;
        if (i2 > m - k)
          return -1;
        for (int i4 = 0; i4 < i2; i4++)
        {
          paramArrayOfByte2[k] = i3;
          k++;
        }
      }
    }
    return 1;
  }

  int decompressRLE8Data(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3)
  {
    int i = 0;
    int j = paramInt1;
    int k = 0;
    int m = paramInt3;
    int n = 0;
    int i1 = 0;
    while (i < j)
    {
      int i2 = paramArrayOfByte1[i] & 0xFF;
      i++;
      int i3;
      if (i2 == 0)
      {
        i2 = paramArrayOfByte1[i] & 0xFF;
        i++;
        switch (i2)
        {
        case 0:
          i1++;
          n = 0;
          k = i1 * paramInt2;
          if (k <= m)
            continue;
          return -1;
        case 1:
          return 1;
        case 2:
          n += (paramArrayOfByte1[i] & 0xFF);
          i++;
          i1 += (paramArrayOfByte1[i] & 0xFF);
          i++;
          k = i1 * paramInt2 + n;
          if (k <= m)
            continue;
          return -1;
        default:
          if (i2 > j - i)
            return -1;
          if (i2 > m - k)
            return -1;
          for (i3 = 0; i3 < i2; i3++)
          {
            paramArrayOfByte2[k] = paramArrayOfByte1[i];
            k++;
            i++;
          }
          if ((i & 0x1) != 0)
            i++;
          n += i2;
          break;
        }
      }
      else
      {
        i3 = paramArrayOfByte1[i];
        i++;
        if (i2 > m - k)
          return -1;
        for (int i4 = 0; i4 < i2; i4++)
        {
          paramArrayOfByte2[k] = i3;
          k++;
        }
        n += i2;
      }
    }
    return 1;
  }

  boolean isFileFormat(LEDataInputStream paramLEDataInputStream)
  {
    try
    {
      byte[] arrayOfByte = new byte[18];
      paramLEDataInputStream.read(arrayOfByte);
      paramLEDataInputStream.unread(arrayOfByte);
      int i = arrayOfByte[14] & 0xFF | (arrayOfByte[15] & 0xFF) << 8 | (arrayOfByte[16] & 0xFF) << 16 | (arrayOfByte[17] & 0xFF) << 24;
      return (arrayOfByte[0] == 66) && (arrayOfByte[1] == 77) && (i >= 40);
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  boolean isPaletteBMP(PaletteData paramPaletteData, int paramInt)
  {
    switch (paramInt)
    {
    case 32:
      return (paramPaletteData.redMask == 65280) && (paramPaletteData.greenMask == 16711680) && (paramPaletteData.blueMask == -16777216);
    case 24:
      return (paramPaletteData.redMask == 255) && (paramPaletteData.greenMask == 65280) && (paramPaletteData.blueMask == 16711680);
    case 16:
      return (paramPaletteData.redMask == 31744) && (paramPaletteData.greenMask == 992) && (paramPaletteData.blueMask == 31);
    }
    return true;
  }

  byte[] loadData(byte[] paramArrayOfByte)
  {
    int i = paramArrayOfByte[4] & 0xFF | (paramArrayOfByte[5] & 0xFF) << 8 | (paramArrayOfByte[6] & 0xFF) << 16 | (paramArrayOfByte[7] & 0xFF) << 24;
    int j = paramArrayOfByte[8] & 0xFF | (paramArrayOfByte[9] & 0xFF) << 8 | (paramArrayOfByte[10] & 0xFF) << 16 | (paramArrayOfByte[11] & 0xFF) << 24;
    int k = paramArrayOfByte[14] & 0xFF | (paramArrayOfByte[15] & 0xFF) << 8;
    int m = (i * k + 7) / 8;
    m = (m + 3) / 4 * 4;
    byte[] arrayOfByte = loadData(paramArrayOfByte, m);
    flipScanLines(arrayOfByte, m, j);
    return arrayOfByte;
  }

  byte[] loadData(byte[] paramArrayOfByte, int paramInt)
  {
    int i = paramArrayOfByte[8] & 0xFF | (paramArrayOfByte[9] & 0xFF) << 8 | (paramArrayOfByte[10] & 0xFF) << 16 | (paramArrayOfByte[11] & 0xFF) << 24;
    if (i < 0)
      i = -i;
    int j = i * paramInt;
    byte[] arrayOfByte1 = new byte[j];
    int k = paramArrayOfByte[16] & 0xFF | (paramArrayOfByte[17] & 0xFF) << 8 | (paramArrayOfByte[18] & 0xFF) << 16 | (paramArrayOfByte[19] & 0xFF) << 24;
    if ((k == 0) || (k == 3))
    {
      try
      {
        if (this.inputStream.read(arrayOfByte1) == j)
          break label248;
        SWT.error(40);
      }
      catch (IOException localIOException1)
      {
        SWT.error(39, localIOException1);
      }
    }
    else
    {
      int m = paramArrayOfByte[20] & 0xFF | (paramArrayOfByte[21] & 0xFF) << 8 | (paramArrayOfByte[22] & 0xFF) << 16 | (paramArrayOfByte[23] & 0xFF) << 24;
      byte[] arrayOfByte2 = new byte[m];
      try
      {
        if (this.inputStream.read(arrayOfByte2) != m)
          SWT.error(40);
      }
      catch (IOException localIOException2)
      {
        SWT.error(39, localIOException2);
      }
      decompressData(arrayOfByte2, arrayOfByte1, paramInt, k);
    }
    label248: return arrayOfByte1;
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
    byte[] arrayOfByte1 = new byte[40];
    try
    {
      this.inputStream.read(arrayOfByte1);
    }
    catch (Exception localException)
    {
      SWT.error(39, localException);
    }
    int i = arrayOfByte1[4] & 0xFF | (arrayOfByte1[5] & 0xFF) << 8 | (arrayOfByte1[6] & 0xFF) << 16 | (arrayOfByte1[7] & 0xFF) << 24;
    int j = arrayOfByte1[8] & 0xFF | (arrayOfByte1[9] & 0xFF) << 8 | (arrayOfByte1[10] & 0xFF) << 16 | (arrayOfByte1[11] & 0xFF) << 24;
    if (j < 0)
      j = -j;
    int k = arrayOfByte1[14] & 0xFF | (arrayOfByte1[15] & 0xFF) << 8;
    this.compression = (arrayOfByte1[16] & 0xFF | (arrayOfByte1[17] & 0xFF) << 8 | (arrayOfByte1[18] & 0xFF) << 16 | (arrayOfByte1[19] & 0xFF) << 24);
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
    this.importantColors = (arrayOfByte1[36] & 0xFF | (arrayOfByte1[37] & 0xFF) << 8 | (arrayOfByte1[38] & 0xFF) << 16 | (arrayOfByte1[39] & 0xFF) << 24);
    int m = arrayOfByte1[24] & 0xFF | (arrayOfByte1[25] & 0xFF) << 8 | (arrayOfByte1[26] & 0xFF) << 16 | (arrayOfByte1[27] & 0xFF) << 24;
    int n = arrayOfByte1[28] & 0xFF | (arrayOfByte1[29] & 0xFF) << 8 | (arrayOfByte1[30] & 0xFF) << 16 | (arrayOfByte1[31] & 0xFF) << 24;
    this.pelsPerMeter = new Point(m, n);
    int i1 = (this.compression == 1) || (this.compression == 2) ? 1 : 0;
    return new ImageData[] { ImageData.internal_new(i, j, k, localPaletteData, 4, arrayOfByte2, 0, null, null, -1, -1, i1, 0, 0, 0, 0) };
  }

  PaletteData loadPalette(byte[] paramArrayOfByte)
  {
    int i = paramArrayOfByte[14] & 0xFF | (paramArrayOfByte[15] & 0xFF) << 8;
    if (i <= 8)
    {
      int j = paramArrayOfByte[32] & 0xFF | (paramArrayOfByte[33] & 0xFF) << 8 | (paramArrayOfByte[34] & 0xFF) << 16 | (paramArrayOfByte[35] & 0xFF) << 24;
      if (j == 0)
        j = 1 << i;
      else if (j > 256)
        j = 256;
      byte[] arrayOfByte = new byte[j * 4];
      try
      {
        if (this.inputStream.read(arrayOfByte) != arrayOfByte.length)
          SWT.error(40);
      }
      catch (IOException localIOException3)
      {
        SWT.error(39, localIOException3);
      }
      return paletteFromBytes(arrayOfByte, j);
    }
    if (i == 16)
    {
      if (this.compression == 3)
        try
        {
          return new PaletteData(this.inputStream.readInt(), this.inputStream.readInt(), this.inputStream.readInt());
        }
        catch (IOException localIOException1)
        {
          SWT.error(39, localIOException1);
        }
      return new PaletteData(31744, 992, 31);
    }
    if (i == 24)
      return new PaletteData(255, 65280, 16711680);
    if (this.compression == 3)
      try
      {
        return new PaletteData(this.inputStream.readInt(), this.inputStream.readInt(), this.inputStream.readInt());
      }
      catch (IOException localIOException2)
      {
        SWT.error(39, localIOException2);
      }
    return new PaletteData(65280, 16711680, -16777216);
  }

  PaletteData paletteFromBytes(byte[] paramArrayOfByte, int paramInt)
  {
    int i = 0;
    RGB[] arrayOfRGB = new RGB[paramInt];
    for (int j = 0; j < paramInt; j++)
    {
      arrayOfRGB[j] = new RGB(paramArrayOfByte[(i + 2)] & 0xFF, paramArrayOfByte[(i + 1)] & 0xFF, paramArrayOfByte[i] & 0xFF);
      i += 4;
    }
    return new PaletteData(arrayOfRGB);
  }

  static byte[] paletteToBytes(PaletteData paramPaletteData)
  {
    int i = paramPaletteData.colors.length < 256 ? paramPaletteData.colors.length : paramPaletteData.colors == null ? 0 : 256;
    byte[] arrayOfByte = new byte[i * 4];
    int j = 0;
    for (int k = 0; k < i; k++)
    {
      RGB localRGB = paramPaletteData.colors[k];
      arrayOfByte[j] = ((byte)localRGB.blue);
      arrayOfByte[(j + 1)] = ((byte)localRGB.green);
      arrayOfByte[(j + 2)] = ((byte)localRGB.red);
      j += 4;
    }
    return arrayOfByte;
  }

  int unloadData(ImageData paramImageData, byte[] paramArrayOfByte, OutputStream paramOutputStream, int paramInt)
  {
    int i = 0;
    try
    {
      if (paramInt == 0)
        return unloadDataNoCompression(paramImageData, paramArrayOfByte, paramOutputStream);
      int j = (paramImageData.width * paramImageData.depth + 7) / 8;
      int k = (j + 3) / 4 * 4;
      int m = paramImageData.bytesPerLine;
      byte[] arrayOfByte1 = new byte[k * 2];
      int n = m * (paramImageData.height - 1);
      if (paramArrayOfByte == null)
        paramArrayOfByte = paramImageData.data;
      i = 0;
      byte[] arrayOfByte2 = new byte[32768];
      int i1 = 0;
      for (int i2 = paramImageData.height - 1; i2 >= 0; i2--)
      {
        int i3 = compress(paramInt, paramArrayOfByte, n, j, arrayOfByte1, i2 == 0);
        if (i1 + i3 > arrayOfByte2.length)
        {
          paramOutputStream.write(arrayOfByte2, 0, i1);
          i1 = 0;
        }
        System.arraycopy(arrayOfByte1, 0, arrayOfByte2, i1, i3);
        i1 += i3;
        i += i3;
        n -= m;
      }
      if (i1 > 0)
        paramOutputStream.write(arrayOfByte2, 0, i1);
    }
    catch (IOException localIOException)
    {
      SWT.error(39, localIOException);
    }
    return i;
  }

  int unloadDataNoCompression(ImageData paramImageData, byte[] paramArrayOfByte, OutputStream paramOutputStream)
  {
    int i = 0;
    try
    {
      int j = (paramImageData.width * paramImageData.depth + 7) / 8;
      i = (j + 3) / 4 * 4;
      int k = 32678 / i;
      byte[] arrayOfByte = new byte[k * i];
      if (paramArrayOfByte == null)
        paramArrayOfByte = paramImageData.data;
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
              arrayOfByte[(i3 + i5 + 1)] = paramArrayOfByte[(n + i5 + 1)];
              arrayOfByte[(i3 + i5)] = paramArrayOfByte[(n + i5)];
            }
            i3 += i;
            n -= m;
          }
          paramOutputStream.write(arrayOfByte, 0, i3);
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
            System.arraycopy(paramArrayOfByte, n, arrayOfByte, i4, j);
            i4 += i;
            n -= m;
          }
          paramOutputStream.write(arrayOfByte, 0, i4);
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
    int j = this.compression;
    if ((j != 0) && ((j != 1) || (localImageData.depth != 8)) && ((j != 2) || (localImageData.depth != 4)))
      SWT.error(40);
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
    int k = 54;
    int[] arrayOfInt = new int[5];
    arrayOfInt[0] = 19778;
    arrayOfInt[1] = 0;
    arrayOfInt[2] = 0;
    arrayOfInt[3] = 0;
    arrayOfInt[4] = k;
    if (arrayOfByte1 != null)
      arrayOfInt[4] += arrayOfByte1.length;
    byte[] arrayOfByte2 = (byte[])null;
    if ((localPaletteData.isDirect) && (!isPaletteBMP(localPaletteData, localImageData.depth)))
    {
      arrayOfByte2 = new byte[localImageData.data.length];
      convertPixelsToBGR(localImageData, arrayOfByte2);
    }
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    unloadData(localImageData, arrayOfByte2, localByteArrayOutputStream, j);
    byte[] arrayOfByte3 = localByteArrayOutputStream.toByteArray();
    arrayOfInt[1] = (arrayOfInt[4] + arrayOfByte3.length);
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
      this.outputStream.writeInt(40);
      this.outputStream.writeInt(localImageData.width);
      this.outputStream.writeInt(localImageData.height);
      this.outputStream.writeShort(1);
      this.outputStream.writeShort((short)localImageData.depth);
      this.outputStream.writeInt(j);
      this.outputStream.writeInt(arrayOfByte3.length);
      this.outputStream.writeInt(this.pelsPerMeter.x);
      this.outputStream.writeInt(this.pelsPerMeter.y);
      this.outputStream.writeInt(i);
      this.outputStream.writeInt(this.importantColors);
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
      this.outputStream.write(arrayOfByte3);
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
 * Qualified Name:     org.eclipse.swt.internal.image.WinBMPFileFormat
 * JD-Core Version:    0.6.2
 */