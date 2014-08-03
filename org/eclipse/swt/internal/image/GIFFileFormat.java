package org.eclipse.swt.internal.image;

import java.io.IOException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.ImageLoaderEvent;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;

public final class GIFFileFormat extends FileFormat
{
  String signature;
  int screenWidth;
  int screenHeight;
  int backgroundPixel;
  int bitsPerPixel;
  int defaultDepth;
  int disposalMethod = 0;
  int delayTime = 0;
  int transparentPixel = -1;
  int repeatCount = 1;
  static final int GIF_APPLICATION_EXTENSION_BLOCK_ID = 255;
  static final int GIF_GRAPHICS_CONTROL_BLOCK_ID = 249;
  static final int GIF_PLAIN_TEXT_BLOCK_ID = 1;
  static final int GIF_COMMENT_BLOCK_ID = 254;
  static final int GIF_EXTENSION_BLOCK_ID = 33;
  static final int GIF_IMAGE_BLOCK_ID = 44;
  static final int GIF_TRAILER_ID = 59;
  static final byte[] GIF89a = { 71, 73, 70, 56, 57, 97 };
  static final byte[] NETSCAPE2_0 = { 78, 69, 84, 83, 67, 65, 80, 69, 50, 46, 48 };

  static PaletteData grayRamp(int paramInt)
  {
    int i = paramInt - 1;
    RGB[] arrayOfRGB = new RGB[paramInt];
    for (int j = 0; j < paramInt; j++)
    {
      int k = (byte)(j * 3 * 256 / i);
      arrayOfRGB[j] = new RGB(k, k, k);
    }
    return new PaletteData(arrayOfRGB);
  }

  boolean isFileFormat(LEDataInputStream paramLEDataInputStream)
  {
    try
    {
      byte[] arrayOfByte = new byte[3];
      paramLEDataInputStream.read(arrayOfByte);
      paramLEDataInputStream.unread(arrayOfByte);
      return (arrayOfByte[0] == 71) && (arrayOfByte[1] == 73) && (arrayOfByte[2] == 70);
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  ImageData[] loadFromByteStream()
  {
    byte[] arrayOfByte1 = new byte[3];
    byte[] arrayOfByte2 = new byte[3];
    byte[] arrayOfByte3 = new byte[7];
    try
    {
      this.inputStream.read(arrayOfByte1);
      if ((arrayOfByte1[0] != 71) || (arrayOfByte1[1] != 73) || (arrayOfByte1[2] != 70))
        SWT.error(40);
      this.inputStream.read(arrayOfByte2);
      this.inputStream.read(arrayOfByte3);
    }
    catch (IOException localIOException1)
    {
      SWT.error(39, localIOException1);
    }
    this.screenWidth = (arrayOfByte3[0] & 0xFF | (arrayOfByte3[1] & 0xFF) << 8);
    this.loader.logicalScreenWidth = this.screenWidth;
    this.screenHeight = (arrayOfByte3[2] & 0xFF | (arrayOfByte3[3] & 0xFF) << 8);
    this.loader.logicalScreenHeight = this.screenHeight;
    int i = arrayOfByte3[4];
    this.backgroundPixel = (arrayOfByte3[5] & 0xFF);
    this.bitsPerPixel = ((i >> 4 & 0x7) + 1);
    this.defaultDepth = ((i & 0x7) + 1);
    PaletteData localPaletteData = null;
    if ((i & 0x80) != 0)
    {
      localPaletteData = readPalette(1 << this.defaultDepth);
    }
    else
    {
      this.backgroundPixel = -1;
      this.defaultDepth = this.bitsPerPixel;
    }
    this.loader.backgroundPixel = this.backgroundPixel;
    getExtensions();
    int j = readID();
    ImageData[] arrayOfImageData1 = new ImageData[0];
    while (j == 44)
    {
      ImageData localImageData = readImageBlock(localPaletteData);
      if (this.loader.hasListeners())
        this.loader.notifyListeners(new ImageLoaderEvent(this.loader, localImageData, 3, true));
      ImageData[] arrayOfImageData2 = arrayOfImageData1;
      arrayOfImageData1 = new ImageData[arrayOfImageData2.length + 1];
      System.arraycopy(arrayOfImageData2, 0, arrayOfImageData1, 0, arrayOfImageData2.length);
      arrayOfImageData1[(arrayOfImageData1.length - 1)] = localImageData;
      try
      {
        j = this.inputStream.read();
        if (j > 0)
          this.inputStream.unread(new byte[] { (byte)j });
      }
      catch (IOException localIOException2)
      {
        SWT.error(39, localIOException2);
      }
      getExtensions();
      j = readID();
    }
    return arrayOfImageData1;
  }

  int readID()
  {
    try
    {
      return this.inputStream.read();
    }
    catch (IOException localIOException)
    {
      SWT.error(39, localIOException);
    }
    return -1;
  }

  void getExtensions()
  {
    for (int i = readID(); (i != 44) && (i != 59) && (i > 0); i = readID())
      if (i == 33)
        readExtension();
      else
        SWT.error(40);
    if ((i == 44) || (i == 59))
      try
      {
        this.inputStream.unread(new byte[] { (byte)i });
      }
      catch (IOException localIOException)
      {
        SWT.error(39, localIOException);
      }
  }

  byte[] readExtension()
  {
    int i = readID();
    if (i == 254)
      return readCommentExtension();
    if (i == 1)
      return readPlainTextExtension();
    if (i == 249)
      return readGraphicsControlExtension();
    if (i == 255)
      return readApplicationExtension();
    try
    {
      int j = this.inputStream.read();
      if (j < 0)
        SWT.error(40);
      byte[] arrayOfByte = new byte[j];
      this.inputStream.read(arrayOfByte, 0, j);
      return arrayOfByte;
    }
    catch (IOException localIOException)
    {
      SWT.error(39, localIOException);
    }
    return null;
  }

  byte[] readCommentExtension()
  {
    try
    {
      byte[] arrayOfByte1 = new byte[0];
      byte[] arrayOfByte2 = new byte['ÿ'];
      for (int i = this.inputStream.read(); (i > 0) && (this.inputStream.read(arrayOfByte2, 0, i) != -1); i = this.inputStream.read())
      {
        byte[] arrayOfByte3 = arrayOfByte1;
        arrayOfByte1 = new byte[arrayOfByte3.length + i];
        System.arraycopy(arrayOfByte3, 0, arrayOfByte1, 0, arrayOfByte3.length);
        System.arraycopy(arrayOfByte2, 0, arrayOfByte1, arrayOfByte3.length, i);
      }
      return arrayOfByte1;
    }
    catch (Exception localException)
    {
      SWT.error(39, localException);
    }
    return null;
  }

  byte[] readPlainTextExtension()
  {
    try
    {
      this.inputStream.read();
      byte[] arrayOfByte1 = new byte[12];
      this.inputStream.read(arrayOfByte1);
      byte[] arrayOfByte2 = new byte[0];
      byte[] arrayOfByte3 = new byte['ÿ'];
      for (int i = this.inputStream.read(); (i > 0) && (this.inputStream.read(arrayOfByte3, 0, i) != -1); i = this.inputStream.read())
      {
        byte[] arrayOfByte4 = arrayOfByte2;
        arrayOfByte2 = new byte[arrayOfByte4.length + i];
        System.arraycopy(arrayOfByte4, 0, arrayOfByte2, 0, arrayOfByte4.length);
        System.arraycopy(arrayOfByte3, 0, arrayOfByte2, arrayOfByte4.length, i);
      }
      return arrayOfByte2;
    }
    catch (Exception localException)
    {
      SWT.error(39, localException);
    }
    return null;
  }

  byte[] readGraphicsControlExtension()
  {
    try
    {
      this.inputStream.read();
      byte[] arrayOfByte = new byte[4];
      this.inputStream.read(arrayOfByte);
      int i = arrayOfByte[0];
      this.disposalMethod = (i >> 2 & 0x7);
      this.delayTime = (arrayOfByte[1] & 0xFF | (arrayOfByte[2] & 0xFF) << 8);
      if ((i & 0x1) != 0)
        this.transparentPixel = (arrayOfByte[3] & 0xFF);
      else
        this.transparentPixel = -1;
      this.inputStream.read();
      return arrayOfByte;
    }
    catch (Exception localException)
    {
      SWT.error(39, localException);
    }
    return null;
  }

  byte[] readApplicationExtension()
  {
    try
    {
      int i = this.inputStream.read();
      byte[] arrayOfByte1 = new byte[i];
      this.inputStream.read(arrayOfByte1);
      byte[] arrayOfByte2 = new byte[0];
      byte[] arrayOfByte3 = new byte['ÿ'];
      for (int j = this.inputStream.read(); (j > 0) && (this.inputStream.read(arrayOfByte3, 0, j) != -1); j = this.inputStream.read())
      {
        byte[] arrayOfByte4 = arrayOfByte2;
        arrayOfByte2 = new byte[arrayOfByte4.length + j];
        System.arraycopy(arrayOfByte4, 0, arrayOfByte2, 0, arrayOfByte4.length);
        System.arraycopy(arrayOfByte3, 0, arrayOfByte2, arrayOfByte4.length, j);
      }
      int k = (i > 7) && (arrayOfByte1[0] == 78) && (arrayOfByte1[1] == 69) && (arrayOfByte1[2] == 84) && (arrayOfByte1[3] == 83) && (arrayOfByte1[4] == 67) && (arrayOfByte1[5] == 65) && (arrayOfByte1[6] == 80) && (arrayOfByte1[7] == 69) ? 1 : 0;
      int m = (i > 10) && (arrayOfByte1[8] == 50) && (arrayOfByte1[9] == 46) && (arrayOfByte1[10] == 48) ? 1 : 0;
      if ((k != 0) && (m != 0) && (arrayOfByte2[0] == 1))
      {
        this.repeatCount = (arrayOfByte2[1] & 0xFF | (arrayOfByte2[2] & 0xFF) << 8);
        this.loader.repeatCount = this.repeatCount;
      }
      return arrayOfByte2;
    }
    catch (Exception localException)
    {
      SWT.error(39, localException);
    }
    return null;
  }

  ImageData readImageBlock(PaletteData paramPaletteData)
  {
    byte[] arrayOfByte = new byte[9];
    try
    {
      this.inputStream.read(arrayOfByte);
    }
    catch (IOException localIOException1)
    {
      SWT.error(39, localIOException1);
    }
    int j = arrayOfByte[0] & 0xFF | (arrayOfByte[1] & 0xFF) << 8;
    int k = arrayOfByte[2] & 0xFF | (arrayOfByte[3] & 0xFF) << 8;
    int m = arrayOfByte[4] & 0xFF | (arrayOfByte[5] & 0xFF) << 8;
    int n = arrayOfByte[6] & 0xFF | (arrayOfByte[7] & 0xFF) << 8;
    int i1 = arrayOfByte[8];
    boolean bool = (i1 & 0x40) != 0;
    int i;
    PaletteData localPaletteData;
    if ((i1 & 0x80) != 0)
    {
      i = (i1 & 0x7) + 1;
      localPaletteData = readPalette(1 << i);
    }
    else
    {
      i = this.defaultDepth;
      localPaletteData = paramPaletteData;
    }
    if (this.transparentPixel > 1 << i)
      this.transparentPixel = -1;
    if ((i != 1) && (i != 4) && (i != 8))
      if (i < 4)
        i = 4;
      else
        i = 8;
    if (localPaletteData == null)
      localPaletteData = grayRamp(1 << i);
    int i2 = -1;
    try
    {
      i2 = this.inputStream.read();
    }
    catch (IOException localIOException2)
    {
      SWT.error(39, localIOException2);
    }
    if (i2 < 0)
      SWT.error(40);
    ImageData localImageData = ImageData.internal_new(m, n, i, localPaletteData, 4, null, 0, null, null, -1, this.transparentPixel, 2, j, k, this.disposalMethod, this.delayTime);
    LZWCodec localLZWCodec = new LZWCodec();
    localLZWCodec.decode(this.inputStream, this.loader, localImageData, bool, i2);
    return localImageData;
  }

  PaletteData readPalette(int paramInt)
  {
    byte[] arrayOfByte = new byte[paramInt * 3];
    try
    {
      if (this.inputStream.read(arrayOfByte) != arrayOfByte.length)
        SWT.error(40);
    }
    catch (IOException localIOException)
    {
      SWT.error(39, localIOException);
    }
    RGB[] arrayOfRGB = new RGB[paramInt];
    for (int i = 0; i < paramInt; i++)
      arrayOfRGB[i] = new RGB(arrayOfByte[(i * 3)] & 0xFF, arrayOfByte[(i * 3 + 1)] & 0xFF, arrayOfByte[(i * 3 + 2)] & 0xFF);
    return new PaletteData(arrayOfRGB);
  }

  void unloadIntoByteStream(ImageLoader paramImageLoader)
  {
    ImageData[] arrayOfImageData = paramImageLoader.data;
    int i = arrayOfImageData.length;
    int j = i > 1 ? 1 : 0;
    ImageData localImageData = arrayOfImageData[0];
    int k = j != 0 ? paramImageLoader.logicalScreenWidth : localImageData.width;
    int m = j != 0 ? paramImageLoader.logicalScreenHeight : localImageData.height;
    int n = paramImageLoader.backgroundPixel;
    int i1 = localImageData.depth;
    PaletteData localPaletteData = localImageData.palette;
    RGB[] arrayOfRGB1 = localPaletteData.getRGBs();
    int i2 = 1;
    if ((i1 != 1) && (i1 != 4) && (i1 != 8))
      SWT.error(38);
    int i6;
    for (int i3 = 0; i3 < i; i3++)
    {
      if (arrayOfImageData[i3].palette.isDirect)
        SWT.error(40);
      if (j != 0)
      {
        if ((arrayOfImageData[i3].height > m) || (arrayOfImageData[i3].width > k) || (arrayOfImageData[i3].depth != i1))
          SWT.error(40);
        if (i2 == 1)
        {
          RGB[] arrayOfRGB2 = arrayOfImageData[i3].palette.getRGBs();
          if (arrayOfRGB2.length != arrayOfRGB1.length)
            i2 = 0;
          else
            for (i6 = 0; i6 < arrayOfRGB1.length; i6++)
              if ((arrayOfRGB2[i6].red != arrayOfRGB1[i6].red) || (arrayOfRGB2[i6].green != arrayOfRGB1[i6].green) || (arrayOfRGB2[i6].blue != arrayOfRGB1[i6].blue))
                i2 = 0;
        }
      }
    }
    try
    {
      this.outputStream.write(GIF89a);
      i3 = i2 * 128 + (i1 - 1) * 16 + i1 - 1;
      this.outputStream.writeShort((short)k);
      this.outputStream.writeShort((short)m);
      this.outputStream.write(i3);
      this.outputStream.write(n);
      this.outputStream.write(0);
    }
    catch (IOException localIOException1)
    {
      SWT.error(39, localIOException1);
    }
    if (i2 == 1)
      writePalette(localPaletteData, i1);
    if (j != 0)
    {
      i4 = paramImageLoader.repeatCount;
      try
      {
        this.outputStream.write(33);
        this.outputStream.write(255);
        this.outputStream.write(NETSCAPE2_0.length);
        this.outputStream.write(NETSCAPE2_0);
        this.outputStream.write(3);
        this.outputStream.write(1);
        this.outputStream.writeShort((short)i4);
        this.outputStream.write(0);
      }
      catch (IOException localIOException3)
      {
        SWT.error(39, localIOException3);
      }
    }
    for (int i4 = 0; i4 < i; i4++)
    {
      if ((j != 0) || (arrayOfImageData[i4].transparentPixel != -1))
        writeGraphicsControlBlock(arrayOfImageData[i4]);
      int i5 = arrayOfImageData[i4].x;
      i6 = arrayOfImageData[i4].y;
      int i7 = arrayOfImageData[i4].width;
      int i8 = arrayOfImageData[i4].height;
      try
      {
        this.outputStream.write(44);
        byte[] arrayOfByte = new byte[9];
        arrayOfByte[0] = ((byte)(i5 & 0xFF));
        arrayOfByte[1] = ((byte)(i5 >> 8 & 0xFF));
        arrayOfByte[2] = ((byte)(i6 & 0xFF));
        arrayOfByte[3] = ((byte)(i6 >> 8 & 0xFF));
        arrayOfByte[4] = ((byte)(i7 & 0xFF));
        arrayOfByte[5] = ((byte)(i7 >> 8 & 0xFF));
        arrayOfByte[6] = ((byte)(i8 & 0xFF));
        arrayOfByte[7] = ((byte)(i8 >> 8 & 0xFF));
        arrayOfByte[8] = ((byte)(i2 == 0 ? i1 - 1 | 0x80 : 0));
        this.outputStream.write(arrayOfByte);
      }
      catch (IOException localIOException4)
      {
        SWT.error(39, localIOException4);
      }
      if (i2 == 0)
        writePalette(arrayOfImageData[i4].palette, i1);
      try
      {
        this.outputStream.write(i1);
      }
      catch (IOException localIOException5)
      {
        SWT.error(39, localIOException5);
      }
      new LZWCodec().encode(this.outputStream, arrayOfImageData[i4]);
    }
    try
    {
      this.outputStream.write(59);
    }
    catch (IOException localIOException2)
    {
      SWT.error(39, localIOException2);
    }
  }

  void writeGraphicsControlBlock(ImageData paramImageData)
  {
    try
    {
      this.outputStream.write(33);
      this.outputStream.write(249);
      byte[] arrayOfByte = new byte[4];
      arrayOfByte[0] = 0;
      arrayOfByte[1] = 0;
      arrayOfByte[2] = 0;
      arrayOfByte[3] = 0;
      if (paramImageData.transparentPixel != -1)
      {
        arrayOfByte[0] = 1;
        arrayOfByte[3] = ((byte)paramImageData.transparentPixel);
      }
      if (paramImageData.disposalMethod != 0)
      {
        int tmp68_67 = 0;
        byte[] tmp68_66 = arrayOfByte;
        tmp68_66[tmp68_67] = ((byte)(tmp68_66[tmp68_67] | (byte)((paramImageData.disposalMethod & 0x7) << 2)));
      }
      if (paramImageData.delayTime != 0)
      {
        arrayOfByte[1] = ((byte)(paramImageData.delayTime & 0xFF));
        arrayOfByte[2] = ((byte)(paramImageData.delayTime >> 8 & 0xFF));
      }
      this.outputStream.write((byte)arrayOfByte.length);
      this.outputStream.write(arrayOfByte);
      this.outputStream.write(0);
    }
    catch (IOException localIOException)
    {
      SWT.error(39, localIOException);
    }
  }

  void writePalette(PaletteData paramPaletteData, int paramInt)
  {
    byte[] arrayOfByte = new byte[(1 << paramInt) * 3];
    int i = 0;
    for (int j = 0; j < paramPaletteData.colors.length; j++)
    {
      RGB localRGB = paramPaletteData.colors[j];
      arrayOfByte[i] = ((byte)localRGB.red);
      arrayOfByte[(i + 1)] = ((byte)localRGB.green);
      arrayOfByte[(i + 2)] = ((byte)localRGB.blue);
      i += 3;
    }
    try
    {
      this.outputStream.write(arrayOfByte);
    }
    catch (IOException localIOException)
    {
      SWT.error(39, localIOException);
    }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.image.GIFFileFormat
 * JD-Core Version:    0.6.2
 */