package org.eclipse.swt.internal.image;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.internal.Compatibility;

final class PngEncoder
{
  static final byte[] SIGNATURE = { -119, 80, 78, 71, 13, 10, 26, 10 };
  static final byte[] TAG_IHDR = { 73, 72, 68, 82 };
  static final byte[] TAG_PLTE = { 80, 76, 84, 69 };
  static final byte[] TAG_TRNS = { 116, 82, 78, 83 };
  static final byte[] TAG_IDAT = { 73, 68, 65, 84 };
  static final byte[] TAG_IEND = { 73, 69, 78, 68 };
  ByteArrayOutputStream bytes = new ByteArrayOutputStream(1024);
  PngChunk chunk;
  ImageLoader loader;
  ImageData data;
  int transparencyType;
  int width;
  int height;
  int bitDepth;
  int colorType;
  int compressionMethod = 0;
  int filterMethod = 0;
  int interlaceMethod = 0;

  public PngEncoder(ImageLoader paramImageLoader)
  {
    this.loader = paramImageLoader;
    this.data = paramImageLoader.data[0];
    this.transparencyType = this.data.getTransparencyType();
    this.width = this.data.width;
    this.height = this.data.height;
    this.bitDepth = 8;
    this.colorType = 2;
    if (this.data.palette.isDirect)
    {
      if (this.transparencyType == 1)
        this.colorType = 6;
    }
    else
      this.colorType = 3;
    if ((this.colorType != 2) && (this.colorType != 3) && (this.colorType != 6))
      SWT.error(40);
  }

  void writeShort(ByteArrayOutputStream paramByteArrayOutputStream, int paramInt)
  {
    int i = (byte)(paramInt >> 8 & 0xFF);
    int j = (byte)(paramInt & 0xFF);
    byte[] arrayOfByte = { i, j };
    paramByteArrayOutputStream.write(arrayOfByte, 0, 2);
  }

  void writeInt(ByteArrayOutputStream paramByteArrayOutputStream, int paramInt)
  {
    int i = (byte)(paramInt >> 24 & 0xFF);
    int j = (byte)(paramInt >> 16 & 0xFF);
    int k = (byte)(paramInt >> 8 & 0xFF);
    int m = (byte)(paramInt & 0xFF);
    byte[] arrayOfByte = { i, j, k, m };
    paramByteArrayOutputStream.write(arrayOfByte, 0, 4);
  }

  void writeChunk(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    int i = paramArrayOfByte2 != null ? paramArrayOfByte2.length : 0;
    this.chunk = new PngChunk(i);
    writeInt(this.bytes, i);
    this.bytes.write(paramArrayOfByte1, 0, 4);
    this.chunk.setType(paramArrayOfByte1);
    if (i != 0)
    {
      this.bytes.write(paramArrayOfByte2, 0, i);
      this.chunk.setData(paramArrayOfByte2);
    }
    else
    {
      this.chunk.setCRC(this.chunk.computeCRC());
    }
    writeInt(this.bytes, this.chunk.getCRC());
  }

  void writeSignature()
  {
    this.bytes.write(SIGNATURE, 0, 8);
  }

  void writeHeader()
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(13);
    writeInt(localByteArrayOutputStream, this.width);
    writeInt(localByteArrayOutputStream, this.height);
    localByteArrayOutputStream.write(this.bitDepth);
    localByteArrayOutputStream.write(this.colorType);
    localByteArrayOutputStream.write(this.compressionMethod);
    localByteArrayOutputStream.write(this.filterMethod);
    localByteArrayOutputStream.write(this.interlaceMethod);
    writeChunk(TAG_IHDR, localByteArrayOutputStream.toByteArray());
  }

  void writePalette()
  {
    RGB[] arrayOfRGB = this.data.palette.getRGBs();
    if (arrayOfRGB.length > 256)
      SWT.error(40);
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(arrayOfRGB.length);
    for (int i = 0; i < arrayOfRGB.length; i++)
    {
      localByteArrayOutputStream.write((byte)arrayOfRGB[i].red);
      localByteArrayOutputStream.write((byte)arrayOfRGB[i].green);
      localByteArrayOutputStream.write((byte)arrayOfRGB[i].blue);
    }
    writeChunk(TAG_PLTE, localByteArrayOutputStream.toByteArray());
  }

  void writeTransparency()
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    int k;
    int m;
    switch (this.transparencyType)
    {
    case 1:
      byte[] arrayOfByte1 = new byte[this.data.palette.getRGBs().length];
      for (k = 0; k < this.height; k++)
        for (m = 0; m < this.width; m++)
        {
          int i = this.data.getPixel(m, k);
          int j = this.data.getAlpha(m, k);
          arrayOfByte1[i] = ((byte)j);
        }
      localByteArrayOutputStream.write(arrayOfByte1, 0, arrayOfByte1.length);
      break;
    case 4:
      k = this.data.transparentPixel;
      int n;
      if (this.colorType == 2)
      {
        m = this.data.palette.redMask;
        n = this.data.palette.redShift;
        int i1 = this.data.palette.greenMask;
        int i2 = this.data.palette.greenShift;
        int i3 = this.data.palette.blueShift;
        int i4 = this.data.palette.blueMask;
        int i5 = k & m;
        i5 = n < 0 ? i5 >>> -n : i5 << n;
        int i6 = k & i1;
        i6 = i2 < 0 ? i6 >>> -i2 : i6 << i2;
        int i7 = k & i4;
        i7 = i3 < 0 ? i7 >>> -i3 : i7 << i3;
        writeShort(localByteArrayOutputStream, i5);
        writeShort(localByteArrayOutputStream, i6);
        writeShort(localByteArrayOutputStream, i7);
      }
      if (this.colorType == 3)
      {
        byte[] arrayOfByte2 = new byte[k + 1];
        for (n = 0; n < k; n++)
          arrayOfByte2[n] = -1;
        arrayOfByte2[k] = 0;
        localByteArrayOutputStream.write(arrayOfByte2, 0, arrayOfByte2.length);
      }
      break;
    case 2:
    case 3:
    }
    writeChunk(TAG_TRNS, localByteArrayOutputStream.toByteArray());
  }

  void writeImageData()
    throws IOException
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(1024);
    Object localObject1 = Compatibility.newDeflaterOutputStream(localByteArrayOutputStream);
    if (localObject1 == null)
      localObject1 = localByteArrayOutputStream;
    int j;
    Object localObject3;
    if (this.colorType == 3)
    {
      localObject2 = new byte[this.width];
      for (int i = 0; i < this.height; i++)
      {
        j = 0;
        ((OutputStream)localObject1).write(j);
        this.data.getPixels(0, i, this.width, (byte[])localObject2, 0);
        ((OutputStream)localObject1).write((byte[])localObject2);
      }
    }
    else
    {
      localObject2 = new int[this.width];
      localObject3 = (byte[])null;
      if (this.colorType == 6)
        localObject3 = new byte[this.width];
      j = this.data.palette.redMask;
      int k = this.data.palette.redShift;
      int m = this.data.palette.greenMask;
      int n = this.data.palette.greenShift;
      int i1 = this.data.palette.blueShift;
      int i2 = this.data.palette.blueMask;
      byte[] arrayOfByte = new byte[this.width * (this.colorType == 6 ? 4 : 3)];
      for (int i3 = 0; i3 < this.height; i3++)
      {
        int i4 = 0;
        ((OutputStream)localObject1).write(i4);
        this.data.getPixels(0, i3, this.width, (int[])localObject2, 0);
        if (this.colorType == 6)
          this.data.getAlphas(0, i3, this.width, (byte[])localObject3, 0);
        int i5 = 0;
        for (int i6 = 0; i6 < localObject2.length; i6++)
        {
          int i7 = localObject2[i6];
          int i8 = i7 & j;
          arrayOfByte[(i5++)] = ((byte)(k < 0 ? i8 >>> -k : i8 << k));
          int i9 = i7 & m;
          arrayOfByte[(i5++)] = ((byte)(n < 0 ? i9 >>> -n : i9 << n));
          int i10 = i7 & i2;
          arrayOfByte[(i5++)] = ((byte)(i1 < 0 ? i10 >>> -i1 : i10 << i1));
          if (this.colorType == 6)
            arrayOfByte[(i5++)] = localObject3[i6];
        }
        ((OutputStream)localObject1).write(arrayOfByte);
      }
    }
    ((OutputStream)localObject1).flush();
    ((OutputStream)localObject1).close();
    Object localObject2 = localByteArrayOutputStream.toByteArray();
    if (localObject1 == localByteArrayOutputStream)
    {
      localObject3 = new PngDeflater();
      localObject2 = ((PngDeflater)localObject3).deflate((byte[])localObject2);
    }
    writeChunk(TAG_IDAT, (byte[])localObject2);
  }

  void writeEnd()
  {
    writeChunk(TAG_IEND, null);
  }

  public void encode(LEDataOutputStream paramLEDataOutputStream)
  {
    try
    {
      writeSignature();
      writeHeader();
      if (this.colorType == 3)
        writePalette();
      int i = this.transparencyType == 1 ? 1 : 0;
      int j = this.transparencyType == 4 ? 1 : 0;
      int k = (this.colorType == 2) && (j != 0) ? 1 : 0;
      int m = (this.colorType == 3) && ((i != 0) || (j != 0)) ? 1 : 0;
      if ((k != 0) || (m != 0))
        writeTransparency();
      writeImageData();
      writeEnd();
      paramLEDataOutputStream.write(this.bytes.toByteArray());
    }
    catch (IOException localIOException)
    {
      SWT.error(39, localIOException);
    }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.image.PngEncoder
 * JD-Core Version:    0.6.2
 */