package org.eclipse.swt.internal.image;

import java.io.IOException;
import java.io.InputStream;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.ImageLoaderEvent;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.internal.Compatibility;

public final class PNGFileFormat extends FileFormat
{
  static final int SIGNATURE_LENGTH = 8;
  static final int PRIME = 65521;
  PngIhdrChunk headerChunk;
  PngPlteChunk paletteChunk;
  ImageData imageData;
  byte[] data;
  byte[] alphaPalette;
  byte headerByte1;
  byte headerByte2;
  int adler;

  void readSignature()
    throws IOException
  {
    byte[] arrayOfByte = new byte[8];
    this.inputStream.read(arrayOfByte);
  }

  ImageData[] loadFromByteStream()
  {
    try
    {
      readSignature();
      PngChunkReader localPngChunkReader = new PngChunkReader(this.inputStream);
      this.headerChunk = localPngChunkReader.getIhdrChunk();
      int i = this.headerChunk.getWidth();
      int j = this.headerChunk.getHeight();
      if ((i <= 0) || (j <= 0))
        SWT.error(40);
      int k = getAlignedBytesPerRow() * j;
      this.data = new byte[k];
      this.imageData = ImageData.internal_new(i, j, this.headerChunk.getSwtBitsPerPixel(), new PaletteData(0, 0, 0), 4, this.data, 0, null, null, -1, -1, 5, 0, 0, 0, 0);
      if (this.headerChunk.usesDirectColor())
        this.imageData.palette = this.headerChunk.getPaletteData();
      while (localPngChunkReader.hasMoreChunks())
        readNextChunk(localPngChunkReader);
      return new ImageData[] { this.imageData };
    }
    catch (IOException localIOException)
    {
      SWT.error(40);
    }
    return null;
  }

  void readNextChunk(PngChunkReader paramPngChunkReader)
    throws IOException
  {
    PngChunk localPngChunk = paramPngChunkReader.readNextChunk();
    switch (localPngChunk.getChunkType())
    {
    case 3:
      break;
    case 1:
      if (!this.headerChunk.usesDirectColor())
      {
        this.paletteChunk = ((PngPlteChunk)localPngChunk);
        this.imageData.palette = this.paletteChunk.getPaletteData();
      }
      break;
    case 5:
      PngTrnsChunk localPngTrnsChunk = (PngTrnsChunk)localPngChunk;
      if (localPngTrnsChunk.getTransparencyType(this.headerChunk) == 0)
      {
        this.imageData.transparentPixel = localPngTrnsChunk.getSwtTransparentPixel(this.headerChunk);
      }
      else
      {
        this.alphaPalette = localPngTrnsChunk.getAlphaValues(this.headerChunk, this.paletteChunk);
        int i = 0;
        int j = -1;
        for (int k = 0; k < this.alphaPalette.length; k++)
          if ((this.alphaPalette[k] & 0xFF) != 255)
          {
            i++;
            j = k;
          }
        if (i == 0)
        {
          this.alphaPalette = null;
        }
        else if ((i == 1) && (this.alphaPalette[j] == 0))
        {
          this.alphaPalette = null;
          this.imageData.transparentPixel = j;
        }
      }
      break;
    case 2:
      if (paramPngChunkReader.readPixelData())
      {
        SWT.error(40);
      }
      else
      {
        PngIdatChunk localPngIdatChunk = (PngIdatChunk)localPngChunk;
        readPixelData(localPngIdatChunk, paramPngChunkReader);
      }
      break;
    case 4:
    default:
      if (localPngChunk.isCritical())
        SWT.error(20);
      break;
    }
  }

  void unloadIntoByteStream(ImageLoader paramImageLoader)
  {
    PngEncoder localPngEncoder = new PngEncoder(paramImageLoader);
    localPngEncoder.encode(this.outputStream);
  }

  boolean isFileFormat(LEDataInputStream paramLEDataInputStream)
  {
    try
    {
      byte[] arrayOfByte = new byte[8];
      paramLEDataInputStream.read(arrayOfByte);
      paramLEDataInputStream.unread(arrayOfByte);
      if ((arrayOfByte[0] & 0xFF) != 137)
        return false;
      if ((arrayOfByte[1] & 0xFF) != 80)
        return false;
      if ((arrayOfByte[2] & 0xFF) != 78)
        return false;
      if ((arrayOfByte[3] & 0xFF) != 71)
        return false;
      if ((arrayOfByte[4] & 0xFF) != 13)
        return false;
      if ((arrayOfByte[5] & 0xFF) != 10)
        return false;
      if ((arrayOfByte[6] & 0xFF) != 26)
        return false;
      return (arrayOfByte[7] & 0xFF) == 10;
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  byte[] validateBitDepth(byte[] paramArrayOfByte)
  {
    if (this.headerChunk.getBitDepth() > 8)
    {
      byte[] arrayOfByte = new byte[paramArrayOfByte.length / 2];
      compress16BitDepthTo8BitDepth(paramArrayOfByte, 0, arrayOfByte, 0, arrayOfByte.length);
      return arrayOfByte;
    }
    return paramArrayOfByte;
  }

  void setPixelData(byte[] paramArrayOfByte, ImageData paramImageData)
  {
    int i;
    int j;
    int m;
    int i1;
    byte[] arrayOfByte3;
    byte[] arrayOfByte4;
    int i2;
    int i3;
    int i4;
    int i5;
    int i6;
    switch (this.headerChunk.getColorType())
    {
    case 4:
      i = paramImageData.width;
      j = paramImageData.height;
      m = paramImageData.bytesPerLine;
      i1 = getAlignedBytesPerRow();
      if (this.headerChunk.getBitDepth() > 8)
        i1 /= 2;
      arrayOfByte3 = new byte[m * j];
      arrayOfByte4 = new byte[i * j];
      for (i2 = 0; i2 < j; i2++)
      {
        i3 = i1 * i2;
        i4 = m * i2;
        i5 = i * i2;
        for (i6 = 0; i6 < i; i6++)
        {
          int i7 = paramArrayOfByte[i3];
          int i8 = paramArrayOfByte[(i3 + 1)];
          arrayOfByte3[(i4 + 0)] = i7;
          arrayOfByte3[(i4 + 1)] = i7;
          arrayOfByte3[(i4 + 2)] = i7;
          arrayOfByte4[i5] = i8;
          i3 += 2;
          i4 += 3;
          i5++;
        }
      }
      paramImageData.data = arrayOfByte3;
      paramImageData.alphaData = arrayOfByte4;
      break;
    case 6:
      i = paramImageData.width;
      j = paramImageData.height;
      m = paramImageData.bytesPerLine;
      i1 = getAlignedBytesPerRow();
      if (this.headerChunk.getBitDepth() > 8)
        i1 /= 2;
      arrayOfByte3 = new byte[m * j];
      arrayOfByte4 = new byte[i * j];
      for (i2 = 0; i2 < j; i2++)
      {
        i3 = i1 * i2;
        i4 = m * i2;
        i5 = i * i2;
        for (i6 = 0; i6 < i; i6++)
        {
          arrayOfByte3[(i4 + 0)] = paramArrayOfByte[(i3 + 0)];
          arrayOfByte3[(i4 + 1)] = paramArrayOfByte[(i3 + 1)];
          arrayOfByte3[(i4 + 2)] = paramArrayOfByte[(i3 + 2)];
          arrayOfByte4[i5] = paramArrayOfByte[(i3 + 3)];
          i3 += 4;
          i4 += 3;
          i5++;
        }
      }
      paramImageData.data = arrayOfByte3;
      paramImageData.alphaData = arrayOfByte4;
      break;
    case 3:
      paramImageData.data = paramArrayOfByte;
      if (this.alphaPalette != null)
      {
        i = paramImageData.width * paramImageData.height;
        byte[] arrayOfByte1 = new byte[i];
        byte[] arrayOfByte2 = new byte[i];
        paramImageData.getPixels(0, 0, i, arrayOfByte2, 0);
        for (i1 = 0; i1 < arrayOfByte2.length; i1++)
          arrayOfByte1[i1] = this.alphaPalette[(arrayOfByte2[i1] & 0xFF)];
        paramImageData.alphaData = arrayOfByte1;
      }
      break;
    case 2:
    case 5:
    default:
      i = paramImageData.height;
      int k = paramImageData.bytesPerLine;
      int n = getAlignedBytesPerRow();
      if (this.headerChunk.getBitDepth() > 8)
        n /= 2;
      if (k != n)
        for (i1 = 0; i1 < i; i1++)
          System.arraycopy(paramArrayOfByte, i1 * n, paramImageData.data, i1 * k, n);
      else
        paramImageData.data = paramArrayOfByte;
      break;
    }
  }

  void setImageDataValues(byte[] paramArrayOfByte, ImageData paramImageData)
  {
    byte[] arrayOfByte = validateBitDepth(paramArrayOfByte);
    setPixelData(arrayOfByte, paramImageData);
  }

  void readPixelData(PngIdatChunk paramPngIdatChunk, PngChunkReader paramPngChunkReader)
    throws IOException
  {
    Object localObject = new PngInputStream(paramPngIdatChunk, paramPngChunkReader);
    int i = System.getProperty("org.eclipse.swt.internal.image.PNGFileFormat_3.2") != null ? 1 : 0;
    InputStream localInputStream = i != 0 ? null : Compatibility.newInflaterInputStream((InputStream)localObject);
    if (localInputStream != null)
      localObject = localInputStream;
    else
      localObject = new PngDecodingDataStream((InputStream)localObject);
    int j = this.headerChunk.getInterlaceMethod();
    if (j == 0)
      readNonInterlacedImage((InputStream)localObject);
    else
      readInterlacedImage((InputStream)localObject);
    while (((InputStream)localObject).available() > 0)
      ((InputStream)localObject).read();
    ((InputStream)localObject).close();
  }

  int getAlignedBytesPerRow()
  {
    return (getBytesPerRow(this.headerChunk.getWidth()) + 3) / 4 * 4;
  }

  int getBytesPerRow()
  {
    return getBytesPerRow(this.headerChunk.getWidth());
  }

  int getBytesPerPixel()
  {
    int i = this.headerChunk.getBitsPerPixel();
    return (i + 7) / 8;
  }

  int getBytesPerRow(int paramInt)
  {
    int i = this.headerChunk.getBitsPerPixel();
    int j = i * paramInt;
    int k = 8;
    return (j + (k - 1)) / k;
  }

  void readInterlaceFrame(InputStream paramInputStream, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
    throws IOException
  {
    int i = this.headerChunk.getWidth();
    int j = getAlignedBytesPerRow();
    int k = this.headerChunk.getHeight();
    if ((paramInt3 >= k) || (paramInt4 >= i))
      return;
    int m = (i - paramInt4 + paramInt2 - 1) / paramInt2;
    int n = getBytesPerRow(m);
    byte[] arrayOfByte1 = new byte[n];
    byte[] arrayOfByte2 = new byte[n];
    byte[] arrayOfByte3 = arrayOfByte1;
    byte[] arrayOfByte4 = arrayOfByte2;
    int i1 = paramInt3;
    while (i1 < k)
    {
      int i2 = (byte)paramInputStream.read();
      int i3 = 0;
      while (i3 != n)
        i3 += paramInputStream.read(arrayOfByte3, i3, n - i3);
      filterRow(arrayOfByte3, arrayOfByte4, i2);
      int i4;
      int i5;
      int i6;
      int i7;
      if (this.headerChunk.getBitDepth() >= 8)
      {
        i4 = getBytesPerPixel();
        i5 = i1 * j + paramInt4 * i4;
        i6 = 0;
        while (i6 < arrayOfByte3.length)
        {
          for (i7 = 0; i7 < i4; i7++)
            this.data[(i5 + i7)] = arrayOfByte3[(i6 + i7)];
          i5 += paramInt2 * i4;
          i6 += i4;
        }
      }
      else
      {
        i4 = this.headerChunk.getBitDepth();
        i5 = 8 / i4;
        i6 = paramInt4;
        i7 = i1 * j;
        int i8 = 0;
        for (int i9 = 0; i9 < i4; i9++)
        {
          i8 <<= 1;
          i8 |= 1;
        }
        i9 = 8 - i4;
        for (int i10 = 0; i10 < arrayOfByte3.length; i10++)
        {
          int i11 = i9;
          while (i11 >= 0)
          {
            if (i6 < i)
            {
              int i12 = i7 + i6 * i4 / 8;
              int i13 = arrayOfByte3[i10] >> i11 & i8;
              int i14 = i9 - i4 * (i6 % i5);
              int tmp363_361 = i12;
              byte[] tmp363_358 = this.data;
              tmp363_358[tmp363_361] = ((byte)(tmp363_358[tmp363_361] | i13 << i14));
            }
            i6 += paramInt2;
            i11 -= i4;
          }
        }
      }
      arrayOfByte3 = arrayOfByte3 == arrayOfByte1 ? arrayOfByte2 : arrayOfByte1;
      arrayOfByte4 = arrayOfByte4 == arrayOfByte1 ? arrayOfByte2 : arrayOfByte1;
      i1 += paramInt1;
    }
    setImageDataValues(this.data, this.imageData);
    fireInterlacedFrameEvent(paramInt5);
  }

  void readInterlacedImage(InputStream paramInputStream)
    throws IOException
  {
    readInterlaceFrame(paramInputStream, 8, 8, 0, 0, 0);
    readInterlaceFrame(paramInputStream, 8, 8, 0, 4, 1);
    readInterlaceFrame(paramInputStream, 8, 4, 4, 0, 2);
    readInterlaceFrame(paramInputStream, 4, 4, 0, 2, 3);
    readInterlaceFrame(paramInputStream, 4, 2, 2, 0, 4);
    readInterlaceFrame(paramInputStream, 2, 2, 0, 1, 5);
    readInterlaceFrame(paramInputStream, 2, 1, 1, 0, 6);
  }

  void fireInterlacedFrameEvent(int paramInt)
  {
    if (this.loader.hasListeners())
    {
      ImageData localImageData = (ImageData)this.imageData.clone();
      boolean bool = paramInt == 6;
      this.loader.notifyListeners(new ImageLoaderEvent(this.loader, localImageData, paramInt, bool));
    }
  }

  void readNonInterlacedImage(InputStream paramInputStream)
    throws IOException
  {
    int i = 0;
    int j = getAlignedBytesPerRow();
    int k = getBytesPerRow();
    byte[] arrayOfByte1 = new byte[k];
    byte[] arrayOfByte2 = new byte[k];
    byte[] arrayOfByte3 = arrayOfByte1;
    byte[] arrayOfByte4 = arrayOfByte2;
    int m = this.headerChunk.getHeight();
    for (int n = 0; n < m; n++)
    {
      int i1 = (byte)paramInputStream.read();
      int i2 = 0;
      while (i2 != k)
        i2 += paramInputStream.read(arrayOfByte3, i2, k - i2);
      filterRow(arrayOfByte3, arrayOfByte4, i1);
      System.arraycopy(arrayOfByte3, 0, this.data, i, k);
      i += j;
      arrayOfByte3 = arrayOfByte3 == arrayOfByte1 ? arrayOfByte2 : arrayOfByte1;
      arrayOfByte4 = arrayOfByte4 == arrayOfByte1 ? arrayOfByte2 : arrayOfByte1;
    }
    setImageDataValues(this.data, this.imageData);
  }

  static void compress16BitDepthTo8BitDepth(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2, int paramInt3)
  {
    for (int i = 0; i < paramInt3; i++)
    {
      int j = paramInt1 + 2 * i;
      int k = paramInt2 + i;
      int m = paramArrayOfByte1[j];
      paramArrayOfByte2[k] = m;
    }
  }

  static int compress16BitDepthTo8BitDepth(int paramInt)
  {
    return paramInt >> 8;
  }

  void filterRow(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt)
  {
    int i = this.headerChunk.getFilterByteOffset();
    int j;
    int k;
    int m;
    int n;
    switch (paramInt)
    {
    case 0:
      break;
    case 1:
      for (j = i; j < paramArrayOfByte1.length; j++)
      {
        k = paramArrayOfByte1[j] & 0xFF;
        m = paramArrayOfByte1[(j - i)] & 0xFF;
        paramArrayOfByte1[j] = ((byte)(k + m & 0xFF));
      }
      break;
    case 2:
      for (j = 0; j < paramArrayOfByte1.length; j++)
      {
        k = paramArrayOfByte1[j] & 0xFF;
        m = paramArrayOfByte2[j] & 0xFF;
        paramArrayOfByte1[j] = ((byte)(k + m & 0xFF));
      }
      break;
    case 3:
      for (j = 0; j < paramArrayOfByte1.length; j++)
      {
        k = j < i ? 0 : paramArrayOfByte1[(j - i)] & 0xFF;
        m = paramArrayOfByte2[j] & 0xFF;
        n = paramArrayOfByte1[j] & 0xFF;
        paramArrayOfByte1[j] = ((byte)(n + (k + m) / 2 & 0xFF));
      }
      break;
    case 4:
      for (j = 0; j < paramArrayOfByte1.length; j++)
      {
        k = j < i ? 0 : paramArrayOfByte1[(j - i)] & 0xFF;
        m = j < i ? 0 : paramArrayOfByte2[(j - i)] & 0xFF;
        n = paramArrayOfByte2[j] & 0xFF;
        int i1 = Math.abs(n - m);
        int i2 = Math.abs(k - m);
        int i3 = Math.abs(k - m + n - m);
        int i4 = 0;
        if ((i1 <= i2) && (i1 <= i3))
          i4 = k;
        else if (i2 <= i3)
          i4 = n;
        else
          i4 = m;
        int i5 = paramArrayOfByte1[j] & 0xFF;
        paramArrayOfByte1[j] = ((byte)(i5 + i4 & 0xFF));
      }
    }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.image.PNGFileFormat
 * JD-Core Version:    0.6.2
 */