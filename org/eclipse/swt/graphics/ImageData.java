package org.eclipse.swt.graphics;

import java.io.InputStream;
import org.eclipse.swt.SWT;
import org.eclipse.swt.internal.CloneableCompatibility;

public final class ImageData
  implements CloneableCompatibility
{
  public int width;
  public int height;
  public int depth;
  public int scanlinePad;
  public int bytesPerLine;
  public byte[] data;
  public PaletteData palette;
  public int transparentPixel;
  public byte[] maskData;
  public int maskPad;
  public byte[] alphaData;
  public int alpha;
  public int type;
  public int x;
  public int y;
  public int disposalMethod;
  public int delayTime;
  static final byte[][] ANY_TO_EIGHT = new byte[9][];
  static final byte[] ONE_TO_ONE_MAPPING = ANY_TO_EIGHT[8];
  static final int[][] DITHER_MATRIX = { { 16515072, 8126464, 14417920, 6029312, 15990784, 7602176, 13893632, 5505024 }, { 3932160, 12320768, 1835008, 10223616, 3407872, 11796480, 1310720, 9699328 }, { 13369344, 4980736, 15466496, 7077888, 12845056, 4456448, 14942208, 6553600 }, { 786432, 9175040, 2883584, 11272192, 262144, 8650752, 2359296, 10747904 }, { 15728640, 7340032, 13631488, 5242880, 16252928, 7864320, 14155776, 5767168 }, { 3145728, 11534336, 1048576, 9437184, 3670016, 12058624, 1572864, 9961472 }, { 12582912, 4194304, 14680064, 6291456, 13107200, 4718592, 15204352, 6815744 }, { 0, 8388608, 2097152, 10485760, 524288, 8912896, 2621440, 11010048 } };
  static final int BLIT_SRC = 1;
  static final int BLIT_ALPHA = 2;
  static final int BLIT_DITHER = 4;
  static final int ALPHA_OPAQUE = 255;
  static final int ALPHA_TRANSPARENT = 0;
  static final int ALPHA_CHANNEL_SEPARATE = -1;
  static final int ALPHA_CHANNEL_SOURCE = -2;
  static final int ALPHA_MASK_UNPACKED = -3;
  static final int ALPHA_MASK_PACKED = -4;
  static final int ALPHA_MASK_INDEX = -5;
  static final int ALPHA_MASK_RGB = -6;
  static final int LSB_FIRST = 0;
  static final int MSB_FIRST = 1;
  private static final int TYPE_GENERIC_8 = 0;
  private static final int TYPE_GENERIC_16_MSB = 1;
  private static final int TYPE_GENERIC_16_LSB = 2;
  private static final int TYPE_GENERIC_24 = 3;
  private static final int TYPE_GENERIC_32_MSB = 4;
  private static final int TYPE_GENERIC_32_LSB = 5;
  private static final int TYPE_INDEX_8 = 6;
  private static final int TYPE_INDEX_4 = 7;
  private static final int TYPE_INDEX_2 = 8;
  private static final int TYPE_INDEX_1_MSB = 9;
  private static final int TYPE_INDEX_1_LSB = 10;

  static
  {
    for (int i = 0; i < 9; i++)
    {
      byte[] arrayOfByte = ANY_TO_EIGHT[i] =  = new byte[1 << i];
      if (i != 0)
      {
        int j = 0;
        int k = 65536;
        while (k >>= i != 0)
          j |= k;
        k = 0;
        int m = 0;
        while (k < 65536)
        {
          arrayOfByte[(m++)] = ((byte)(k >> 8));
          k += j;
        }
      }
    }
  }

  public ImageData(int paramInt1, int paramInt2, int paramInt3, PaletteData paramPaletteData)
  {
    this(paramInt1, paramInt2, paramInt3, paramPaletteData, 4, null, 0, null, null, -1, -1, -1, 0, 0, 0, 0);
  }

  public ImageData(int paramInt1, int paramInt2, int paramInt3, PaletteData paramPaletteData, int paramInt4, byte[] paramArrayOfByte)
  {
    this(paramInt1, paramInt2, paramInt3, paramPaletteData, paramInt4, checkData(paramArrayOfByte), 0, null, null, -1, -1, -1, 0, 0, 0, 0);
  }

  public ImageData(InputStream paramInputStream)
  {
    ImageData[] arrayOfImageData = ImageDataLoader.load(paramInputStream);
    if (arrayOfImageData.length < 1)
      SWT.error(40);
    ImageData localImageData = arrayOfImageData[0];
    setAllFields(localImageData.width, localImageData.height, localImageData.depth, localImageData.scanlinePad, localImageData.bytesPerLine, localImageData.data, localImageData.palette, localImageData.transparentPixel, localImageData.maskData, localImageData.maskPad, localImageData.alphaData, localImageData.alpha, localImageData.type, localImageData.x, localImageData.y, localImageData.disposalMethod, localImageData.delayTime);
  }

  public ImageData(String paramString)
  {
    ImageData[] arrayOfImageData = ImageDataLoader.load(paramString);
    if (arrayOfImageData.length < 1)
      SWT.error(40);
    ImageData localImageData = arrayOfImageData[0];
    setAllFields(localImageData.width, localImageData.height, localImageData.depth, localImageData.scanlinePad, localImageData.bytesPerLine, localImageData.data, localImageData.palette, localImageData.transparentPixel, localImageData.maskData, localImageData.maskPad, localImageData.alphaData, localImageData.alpha, localImageData.type, localImageData.x, localImageData.y, localImageData.disposalMethod, localImageData.delayTime);
  }

  ImageData()
  {
  }

  ImageData(int paramInt1, int paramInt2, int paramInt3, PaletteData paramPaletteData, int paramInt4, byte[] paramArrayOfByte1, int paramInt5, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11, int paramInt12)
  {
    if (paramPaletteData == null)
      SWT.error(4);
    if ((paramInt3 != 1) && (paramInt3 != 2) && (paramInt3 != 4) && (paramInt3 != 8) && (paramInt3 != 16) && (paramInt3 != 24) && (paramInt3 != 32))
      SWT.error(5);
    if ((paramInt1 <= 0) || (paramInt2 <= 0))
      SWT.error(5);
    if (paramInt4 == 0)
      SWT.error(7);
    int i = ((paramInt1 * paramInt3 + 7) / 8 + (paramInt4 - 1)) / paramInt4 * paramInt4;
    int j = paramInt8 == 5 ? ((paramInt1 + 7) / 8 + 3) / 4 * 4 : i;
    if ((paramArrayOfByte1 != null) && (paramArrayOfByte1.length < j * paramInt2))
      SWT.error(5);
    setAllFields(paramInt1, paramInt2, paramInt3, paramInt4, i, paramArrayOfByte1 != null ? paramArrayOfByte1 : new byte[i * paramInt2], paramPaletteData, paramInt7, paramArrayOfByte2, paramInt5, paramArrayOfByte3, paramInt6, paramInt8, paramInt9, paramInt10, paramInt11, paramInt12);
  }

  void setAllFields(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, byte[] paramArrayOfByte1, PaletteData paramPaletteData, int paramInt6, byte[] paramArrayOfByte2, int paramInt7, byte[] paramArrayOfByte3, int paramInt8, int paramInt9, int paramInt10, int paramInt11, int paramInt12, int paramInt13)
  {
    this.width = paramInt1;
    this.height = paramInt2;
    this.depth = paramInt3;
    this.scanlinePad = paramInt4;
    this.bytesPerLine = paramInt5;
    this.data = paramArrayOfByte1;
    this.palette = paramPaletteData;
    this.transparentPixel = paramInt6;
    this.maskData = paramArrayOfByte2;
    this.maskPad = paramInt7;
    this.alphaData = paramArrayOfByte3;
    this.alpha = paramInt8;
    this.type = paramInt9;
    this.x = paramInt10;
    this.y = paramInt11;
    this.disposalMethod = paramInt12;
    this.delayTime = paramInt13;
  }

  public static ImageData internal_new(int paramInt1, int paramInt2, int paramInt3, PaletteData paramPaletteData, int paramInt4, byte[] paramArrayOfByte1, int paramInt5, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11, int paramInt12)
  {
    return new ImageData(paramInt1, paramInt2, paramInt3, paramPaletteData, paramInt4, paramArrayOfByte1, paramInt5, paramArrayOfByte2, paramArrayOfByte3, paramInt6, paramInt7, paramInt8, paramInt9, paramInt10, paramInt11, paramInt12);
  }

  ImageData colorMaskImage(int paramInt)
  {
    ImageData localImageData = new ImageData(this.width, this.height, 1, bwPalette(), 2, null, 0, null, null, -1, -1, -1, 0, 0, 0, 0);
    int[] arrayOfInt = new int[this.width];
    for (int i = 0; i < this.height; i++)
    {
      getPixels(0, i, this.width, arrayOfInt, 0);
      for (int j = 0; j < this.width; j++)
        if ((paramInt != -1) && (arrayOfInt[j] == paramInt))
          arrayOfInt[j] = 0;
        else
          arrayOfInt[j] = 1;
      localImageData.setPixels(0, i, this.width, arrayOfInt, 0);
    }
    return localImageData;
  }

  static byte[] checkData(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null)
      SWT.error(4);
    return paramArrayOfByte;
  }

  public Object clone()
  {
    byte[] arrayOfByte1 = new byte[this.data.length];
    System.arraycopy(this.data, 0, arrayOfByte1, 0, this.data.length);
    byte[] arrayOfByte2 = (byte[])null;
    if (this.maskData != null)
    {
      arrayOfByte2 = new byte[this.maskData.length];
      System.arraycopy(this.maskData, 0, arrayOfByte2, 0, this.maskData.length);
    }
    byte[] arrayOfByte3 = (byte[])null;
    if (this.alphaData != null)
    {
      arrayOfByte3 = new byte[this.alphaData.length];
      System.arraycopy(this.alphaData, 0, arrayOfByte3, 0, this.alphaData.length);
    }
    return new ImageData(this.width, this.height, this.depth, this.palette, this.scanlinePad, arrayOfByte1, this.maskPad, arrayOfByte2, arrayOfByte3, this.alpha, this.transparentPixel, this.type, this.x, this.y, this.disposalMethod, this.delayTime);
  }

  public int getAlpha(int paramInt1, int paramInt2)
  {
    if ((paramInt1 >= this.width) || (paramInt2 >= this.height) || (paramInt1 < 0) || (paramInt2 < 0))
      SWT.error(5);
    if (this.alphaData == null)
      return 255;
    return this.alphaData[(paramInt2 * this.width + paramInt1)] & 0xFF;
  }

  public void getAlphas(int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, int paramInt4)
  {
    if (paramArrayOfByte == null)
      SWT.error(4);
    if ((paramInt3 < 0) || (paramInt1 >= this.width) || (paramInt2 >= this.height) || (paramInt1 < 0) || (paramInt2 < 0))
      SWT.error(5);
    if (paramInt3 == 0)
      return;
    if (this.alphaData == null)
    {
      int i = paramInt4 + paramInt3;
      for (int j = paramInt4; j < i; j++)
        paramArrayOfByte[j] = -1;
      return;
    }
    System.arraycopy(this.alphaData, paramInt2 * this.width + paramInt1, paramArrayOfByte, paramInt4, paramInt3);
  }

  public int getPixel(int paramInt1, int paramInt2)
  {
    if ((paramInt1 >= this.width) || (paramInt2 >= this.height) || (paramInt1 < 0) || (paramInt2 < 0))
      SWT.error(5);
    int i;
    int j;
    int k;
    switch (this.depth)
    {
    case 32:
      i = paramInt2 * this.bytesPerLine + paramInt1 * 4;
      return ((this.data[i] & 0xFF) << 24) + ((this.data[(i + 1)] & 0xFF) << 16) + ((this.data[(i + 2)] & 0xFF) << 8) + (this.data[(i + 3)] & 0xFF);
    case 24:
      i = paramInt2 * this.bytesPerLine + paramInt1 * 3;
      return ((this.data[i] & 0xFF) << 16) + ((this.data[(i + 1)] & 0xFF) << 8) + (this.data[(i + 2)] & 0xFF);
    case 16:
      i = paramInt2 * this.bytesPerLine + paramInt1 * 2;
      return ((this.data[(i + 1)] & 0xFF) << 8) + (this.data[i] & 0xFF);
    case 8:
      i = paramInt2 * this.bytesPerLine + paramInt1;
      return this.data[i] & 0xFF;
    case 4:
      i = paramInt2 * this.bytesPerLine + (paramInt1 >> 1);
      j = this.data[i] & 0xFF;
      if ((paramInt1 & 0x1) == 0)
        return j >> 4;
      return j & 0xF;
    case 2:
      i = paramInt2 * this.bytesPerLine + (paramInt1 >> 2);
      j = this.data[i] & 0xFF;
      int m = 3 - paramInt1 % 4;
      k = 3 << m * 2;
      return (j & k) >> m * 2;
    case 1:
      i = paramInt2 * this.bytesPerLine + (paramInt1 >> 3);
      j = this.data[i] & 0xFF;
      k = 1 << 7 - (paramInt1 & 0x7);
      if ((j & k) == 0)
        return 0;
      return 1;
    }
    SWT.error(38);
    return 0;
  }

  public void getPixels(int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, int paramInt4)
  {
    if (paramArrayOfByte == null)
      SWT.error(4);
    if ((paramInt3 < 0) || (paramInt1 >= this.width) || (paramInt2 >= this.height) || (paramInt1 < 0) || (paramInt2 < 0))
      SWT.error(5);
    if (paramInt3 == 0)
      return;
    int k = 0;
    int m = paramInt3;
    int n = paramInt4;
    int i1 = paramInt1;
    int i2 = paramInt2;
    int i;
    int i3;
    int j;
    switch (this.depth)
    {
    case 8:
      i = paramInt2 * this.bytesPerLine + paramInt1;
      for (i3 = 0; i3 < paramInt3; i3++)
      {
        paramArrayOfByte[n] = this.data[i];
        n++;
        i1++;
        if (i1 >= this.width)
        {
          i2++;
          i = i2 * this.bytesPerLine;
          i1 = 0;
        }
        else
        {
          i++;
        }
      }
      return;
    case 4:
      i = paramInt2 * this.bytesPerLine + (paramInt1 >> 1);
      if ((paramInt1 & 0x1) == 1)
      {
        j = this.data[i] & 0xFF;
        paramArrayOfByte[n] = ((byte)(j & 0xF));
        n++;
        m--;
        i1++;
        if (i1 >= this.width)
        {
          i2++;
          i = i2 * this.bytesPerLine;
          i1 = 0;
        }
        else
        {
          i++;
        }
      }
      while (m > 1)
      {
        j = this.data[i] & 0xFF;
        paramArrayOfByte[n] = ((byte)(j >> 4));
        n++;
        m--;
        i1++;
        if (i1 >= this.width)
        {
          i2++;
          i = i2 * this.bytesPerLine;
          i1 = 0;
        }
        else
        {
          paramArrayOfByte[n] = ((byte)(j & 0xF));
          n++;
          m--;
          i1++;
          if (i1 >= this.width)
          {
            i2++;
            i = i2 * this.bytesPerLine;
            i1 = 0;
          }
          else
          {
            i++;
          }
        }
      }
      if (m > 0)
      {
        j = this.data[i] & 0xFF;
        paramArrayOfByte[n] = ((byte)(j >> 4));
      }
      return;
    case 2:
      i = paramInt2 * this.bytesPerLine + (paramInt1 >> 2);
      j = this.data[i] & 0xFF;
      while (m > 0)
      {
        i3 = 3 - i1 % 4;
        k = 3 << i3 * 2;
        paramArrayOfByte[n] = ((byte)((j & k) >> i3 * 2));
        n++;
        m--;
        i1++;
        if (i1 >= this.width)
        {
          i2++;
          i = i2 * this.bytesPerLine;
          if (m > 0)
            j = this.data[i] & 0xFF;
          i1 = 0;
        }
        else if (i3 == 0)
        {
          i++;
          j = this.data[i] & 0xFF;
        }
      }
      return;
    case 1:
      i = paramInt2 * this.bytesPerLine + (paramInt1 >> 3);
      j = this.data[i] & 0xFF;
      while (m > 0)
      {
        k = 1 << 7 - (i1 & 0x7);
        if ((j & k) == 0)
          paramArrayOfByte[n] = 0;
        else
          paramArrayOfByte[n] = 1;
        n++;
        m--;
        i1++;
        if (i1 >= this.width)
        {
          i2++;
          i = i2 * this.bytesPerLine;
          if (m > 0)
            j = this.data[i] & 0xFF;
          i1 = 0;
        }
        else if (k == 1)
        {
          i++;
          if (m > 0)
            j = this.data[i] & 0xFF;
        }
      }
      return;
    case 3:
    case 5:
    case 6:
    case 7:
    }
    SWT.error(38);
  }

  public void getPixels(int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt, int paramInt4)
  {
    if (paramArrayOfInt == null)
      SWT.error(4);
    if ((paramInt3 < 0) || (paramInt1 >= this.width) || (paramInt2 >= this.height) || (paramInt1 < 0) || (paramInt2 < 0))
      SWT.error(5);
    if (paramInt3 == 0)
      return;
    int m = paramInt3;
    int n = paramInt4;
    int i1 = paramInt1;
    int i2 = paramInt2;
    int i;
    int i3;
    int j;
    int k;
    switch (this.depth)
    {
    case 32:
      i = paramInt2 * this.bytesPerLine + paramInt1 * 4;
      n = paramInt4;
      for (i3 = 0; i3 < paramInt3; i3++)
      {
        paramArrayOfInt[n] = ((this.data[i] & 0xFF) << 24 | (this.data[(i + 1)] & 0xFF) << 16 | (this.data[(i + 2)] & 0xFF) << 8 | this.data[(i + 3)] & 0xFF);
        n++;
        i1++;
        if (i1 >= this.width)
        {
          i2++;
          i = i2 * this.bytesPerLine;
          i1 = 0;
        }
        else
        {
          i += 4;
        }
      }
      return;
    case 24:
      i = paramInt2 * this.bytesPerLine + paramInt1 * 3;
      for (i3 = 0; i3 < paramInt3; i3++)
      {
        paramArrayOfInt[n] = ((this.data[i] & 0xFF) << 16 | (this.data[(i + 1)] & 0xFF) << 8 | this.data[(i + 2)] & 0xFF);
        n++;
        i1++;
        if (i1 >= this.width)
        {
          i2++;
          i = i2 * this.bytesPerLine;
          i1 = 0;
        }
        else
        {
          i += 3;
        }
      }
      return;
    case 16:
      i = paramInt2 * this.bytesPerLine + paramInt1 * 2;
      for (i3 = 0; i3 < paramInt3; i3++)
      {
        paramArrayOfInt[n] = (((this.data[(i + 1)] & 0xFF) << 8) + (this.data[i] & 0xFF));
        n++;
        i1++;
        if (i1 >= this.width)
        {
          i2++;
          i = i2 * this.bytesPerLine;
          i1 = 0;
        }
        else
        {
          i += 2;
        }
      }
      return;
    case 8:
      i = paramInt2 * this.bytesPerLine + paramInt1;
      for (i3 = 0; i3 < paramInt3; i3++)
      {
        paramArrayOfInt[n] = (this.data[i] & 0xFF);
        n++;
        i1++;
        if (i1 >= this.width)
        {
          i2++;
          i = i2 * this.bytesPerLine;
          i1 = 0;
        }
        else
        {
          i++;
        }
      }
      return;
    case 4:
      i = paramInt2 * this.bytesPerLine + (paramInt1 >> 1);
      if ((paramInt1 & 0x1) == 1)
      {
        j = this.data[i] & 0xFF;
        paramArrayOfInt[n] = (j & 0xF);
        n++;
        m--;
        i1++;
        if (i1 >= this.width)
        {
          i2++;
          i = i2 * this.bytesPerLine;
          i1 = 0;
        }
        else
        {
          i++;
        }
      }
      while (m > 1)
      {
        j = this.data[i] & 0xFF;
        paramArrayOfInt[n] = (j >> 4);
        n++;
        m--;
        i1++;
        if (i1 >= this.width)
        {
          i2++;
          i = i2 * this.bytesPerLine;
          i1 = 0;
        }
        else
        {
          paramArrayOfInt[n] = (j & 0xF);
          n++;
          m--;
          i1++;
          if (i1 >= this.width)
          {
            i2++;
            i = i2 * this.bytesPerLine;
            i1 = 0;
          }
          else
          {
            i++;
          }
        }
      }
      if (m > 0)
      {
        j = this.data[i] & 0xFF;
        paramArrayOfInt[n] = (j >> 4);
      }
      return;
    case 2:
      i = paramInt2 * this.bytesPerLine + (paramInt1 >> 2);
      j = this.data[i] & 0xFF;
      while (m > 0)
      {
        i3 = 3 - i1 % 4;
        k = 3 << i3 * 2;
        paramArrayOfInt[n] = ((byte)((j & k) >> i3 * 2));
        n++;
        m--;
        i1++;
        if (i1 >= this.width)
        {
          i2++;
          i = i2 * this.bytesPerLine;
          if (m > 0)
            j = this.data[i] & 0xFF;
          i1 = 0;
        }
        else if (i3 == 0)
        {
          i++;
          j = this.data[i] & 0xFF;
        }
      }
      return;
    case 1:
      i = paramInt2 * this.bytesPerLine + (paramInt1 >> 3);
      j = this.data[i] & 0xFF;
      while (m > 0)
      {
        k = 1 << 7 - (i1 & 0x7);
        if ((j & k) == 0)
          paramArrayOfInt[n] = 0;
        else
          paramArrayOfInt[n] = 1;
        n++;
        m--;
        i1++;
        if (i1 >= this.width)
        {
          i2++;
          i = i2 * this.bytesPerLine;
          if (m > 0)
            j = this.data[i] & 0xFF;
          i1 = 0;
        }
        else if (k == 1)
        {
          i++;
          if (m > 0)
            j = this.data[i] & 0xFF;
        }
      }
      return;
    }
    SWT.error(38);
  }

  public RGB[] getRGBs()
  {
    return this.palette.getRGBs();
  }

  public ImageData getTransparencyMask()
  {
    if (getTransparencyType() == 2)
      return new ImageData(this.width, this.height, 1, bwPalette(), this.maskPad, this.maskData);
    return colorMaskImage(this.transparentPixel);
  }

  public int getTransparencyType()
  {
    if (this.maskData != null)
      return 2;
    if (this.transparentPixel != -1)
      return 4;
    if (this.alphaData != null)
      return 1;
    return 0;
  }

  int getByteOrder()
  {
    return this.depth != 16 ? 1 : 0;
  }

  public ImageData scaledTo(int paramInt1, int paramInt2)
  {
    boolean bool1 = paramInt1 < 0;
    if (bool1)
      paramInt1 = -paramInt1;
    boolean bool2 = paramInt2 < 0;
    if (bool2)
      paramInt2 = -paramInt2;
    ImageData localImageData = new ImageData(paramInt1, paramInt2, this.depth, this.palette, this.scanlinePad, null, 0, null, null, -1, this.transparentPixel, this.type, this.x, this.y, this.disposalMethod, this.delayTime);
    if (this.palette.isDirect)
      blit(1, this.data, this.depth, this.bytesPerLine, getByteOrder(), 0, 0, this.width, this.height, 0, 0, 0, 255, null, 0, 0, 0, localImageData.data, localImageData.depth, localImageData.bytesPerLine, localImageData.getByteOrder(), 0, 0, localImageData.width, localImageData.height, 0, 0, 0, bool1, bool2);
    else
      blit(1, this.data, this.depth, this.bytesPerLine, getByteOrder(), 0, 0, this.width, this.height, null, null, null, 255, null, 0, 0, 0, localImageData.data, localImageData.depth, localImageData.bytesPerLine, localImageData.getByteOrder(), 0, 0, localImageData.width, localImageData.height, null, null, null, bool1, bool2);
    if (this.maskData != null)
    {
      localImageData.maskPad = this.maskPad;
      int i = (localImageData.width + 7) / 8;
      i = (i + (localImageData.maskPad - 1)) / localImageData.maskPad * localImageData.maskPad;
      localImageData.maskData = new byte[i * localImageData.height];
      int j = (this.width + 7) / 8;
      j = (j + (this.maskPad - 1)) / this.maskPad * this.maskPad;
      blit(1, this.maskData, 1, j, 1, 0, 0, this.width, this.height, null, null, null, 255, null, 0, 0, 0, localImageData.maskData, 1, i, 1, 0, 0, localImageData.width, localImageData.height, null, null, null, bool1, bool2);
    }
    else if (this.alpha != -1)
    {
      localImageData.alpha = this.alpha;
    }
    else if (this.alphaData != null)
    {
      localImageData.alphaData = new byte[localImageData.width * localImageData.height];
      blit(1, this.alphaData, 8, this.width, 1, 0, 0, this.width, this.height, null, null, null, 255, null, 0, 0, 0, localImageData.alphaData, 8, localImageData.width, 1, 0, 0, localImageData.width, localImageData.height, null, null, null, bool1, bool2);
    }
    return localImageData;
  }

  public void setAlpha(int paramInt1, int paramInt2, int paramInt3)
  {
    if ((paramInt1 >= this.width) || (paramInt2 >= this.height) || (paramInt1 < 0) || (paramInt2 < 0) || (paramInt3 < 0) || (paramInt3 > 255))
      SWT.error(5);
    if (this.alphaData == null)
      this.alphaData = new byte[this.width * this.height];
    this.alphaData[(paramInt2 * this.width + paramInt1)] = ((byte)paramInt3);
  }

  public void setAlphas(int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, int paramInt4)
  {
    if (paramArrayOfByte == null)
      SWT.error(4);
    if ((paramInt3 < 0) || (paramInt1 >= this.width) || (paramInt2 >= this.height) || (paramInt1 < 0) || (paramInt2 < 0))
      SWT.error(5);
    if (paramInt3 == 0)
      return;
    if (this.alphaData == null)
      this.alphaData = new byte[this.width * this.height];
    System.arraycopy(paramArrayOfByte, paramInt4, this.alphaData, paramInt2 * this.width + paramInt1, paramInt3);
  }

  public void setPixel(int paramInt1, int paramInt2, int paramInt3)
  {
    if ((paramInt1 >= this.width) || (paramInt2 >= this.height) || (paramInt1 < 0) || (paramInt2 < 0))
      SWT.error(5);
    int i;
    int j;
    int k;
    switch (this.depth)
    {
    case 32:
      i = paramInt2 * this.bytesPerLine + paramInt1 * 4;
      this.data[i] = ((byte)(paramInt3 >> 24 & 0xFF));
      this.data[(i + 1)] = ((byte)(paramInt3 >> 16 & 0xFF));
      this.data[(i + 2)] = ((byte)(paramInt3 >> 8 & 0xFF));
      this.data[(i + 3)] = ((byte)(paramInt3 & 0xFF));
      return;
    case 24:
      i = paramInt2 * this.bytesPerLine + paramInt1 * 3;
      this.data[i] = ((byte)(paramInt3 >> 16 & 0xFF));
      this.data[(i + 1)] = ((byte)(paramInt3 >> 8 & 0xFF));
      this.data[(i + 2)] = ((byte)(paramInt3 & 0xFF));
      return;
    case 16:
      i = paramInt2 * this.bytesPerLine + paramInt1 * 2;
      this.data[(i + 1)] = ((byte)(paramInt3 >> 8 & 0xFF));
      this.data[i] = ((byte)(paramInt3 & 0xFF));
      return;
    case 8:
      i = paramInt2 * this.bytesPerLine + paramInt1;
      this.data[i] = ((byte)(paramInt3 & 0xFF));
      return;
    case 4:
      i = paramInt2 * this.bytesPerLine + (paramInt1 >> 1);
      if ((paramInt1 & 0x1) == 0)
        this.data[i] = ((byte)(this.data[i] & 0xF | (paramInt3 & 0xF) << 4));
      else
        this.data[i] = ((byte)(this.data[i] & 0xF0 | paramInt3 & 0xF));
      return;
    case 2:
      i = paramInt2 * this.bytesPerLine + (paramInt1 >> 2);
      j = this.data[i];
      int m = 3 - paramInt1 % 4;
      k = 0xFF ^ 3 << m * 2;
      this.data[i] = ((byte)(this.data[i] & k | paramInt3 << m * 2));
      return;
    case 1:
      i = paramInt2 * this.bytesPerLine + (paramInt1 >> 3);
      j = this.data[i];
      k = 1 << 7 - (paramInt1 & 0x7);
      if ((paramInt3 & 0x1) == 1)
        this.data[i] = ((byte)(j | k));
      else
        this.data[i] = ((byte)(j & (k ^ 0xFFFFFFFF)));
      return;
    }
    SWT.error(38);
  }

  public void setPixels(int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, int paramInt4)
  {
    if (paramArrayOfByte == null)
      SWT.error(4);
    if ((paramInt3 < 0) || (paramInt1 >= this.width) || (paramInt2 >= this.height) || (paramInt1 < 0) || (paramInt2 < 0))
      SWT.error(5);
    if (paramInt3 == 0)
      return;
    int m = paramInt3;
    int n = paramInt4;
    int i1 = paramInt1;
    int i2 = paramInt2;
    int i;
    int i3;
    int j;
    switch (this.depth)
    {
    case 8:
      i = paramInt2 * this.bytesPerLine + paramInt1;
      for (i3 = 0; i3 < paramInt3; i3++)
      {
        this.data[i] = ((byte)(paramArrayOfByte[n] & 0xFF));
        n++;
        i1++;
        if (i1 >= this.width)
        {
          i2++;
          i = i2 * this.bytesPerLine;
          i1 = 0;
        }
        else
        {
          i++;
        }
      }
      return;
    case 4:
      i = paramInt2 * this.bytesPerLine + (paramInt1 >> 1);
      i3 = (paramInt1 & 0x1) == 0 ? 1 : 0;
      while (m > 0)
      {
        j = paramArrayOfByte[n] & 0xF;
        if (i3 != 0)
          this.data[i] = ((byte)(this.data[i] & 0xF | j << 4));
        else
          this.data[i] = ((byte)(this.data[i] & 0xF0 | j));
        n++;
        m--;
        i1++;
        if (i1 >= this.width)
        {
          i2++;
          i = i2 * this.bytesPerLine;
          i3 = 1;
          i1 = 0;
        }
        else
        {
          if (i3 == 0)
            i++;
          i3 = i3 != 0 ? 0 : 1;
        }
      }
      return;
    case 2:
      byte[] arrayOfByte = { -4, -13, -49, 63 };
      i = paramInt2 * this.bytesPerLine + (paramInt1 >> 2);
      int i4 = 3 - paramInt1 % 4;
      while (m > 0)
      {
        j = paramArrayOfByte[n] & 0x3;
        this.data[i] = ((byte)(this.data[i] & arrayOfByte[i4] | j << i4 * 2));
        n++;
        m--;
        i1++;
        if (i1 >= this.width)
        {
          i2++;
          i = i2 * this.bytesPerLine;
          i4 = 0;
          i1 = 0;
        }
        else if (i4 == 0)
        {
          i++;
          i4 = 3;
        }
        else
        {
          i4--;
        }
      }
      return;
    case 1:
      i = paramInt2 * this.bytesPerLine + (paramInt1 >> 3);
      while (m > 0)
      {
        int k = 1 << 7 - (i1 & 0x7);
        if ((paramArrayOfByte[n] & 0x1) == 1)
          this.data[i] = ((byte)(this.data[i] & 0xFF | k));
        else
          this.data[i] = ((byte)(this.data[i] & 0xFF & (k ^ 0xFFFFFFFF)));
        n++;
        m--;
        i1++;
        if (i1 >= this.width)
        {
          i2++;
          i = i2 * this.bytesPerLine;
          i1 = 0;
        }
        else if (k == 1)
        {
          i++;
        }
      }
      return;
    case 3:
    case 5:
    case 6:
    case 7:
    }
    SWT.error(38);
  }

  public void setPixels(int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt, int paramInt4)
  {
    if (paramArrayOfInt == null)
      SWT.error(4);
    if ((paramInt3 < 0) || (paramInt1 >= this.width) || (paramInt2 >= this.height) || (paramInt1 < 0) || (paramInt2 < 0))
      SWT.error(5);
    if (paramInt3 == 0)
      return;
    int m = paramInt3;
    int n = paramInt4;
    int i2 = paramInt1;
    int i3 = paramInt2;
    int i;
    int i4;
    int i1;
    int j;
    switch (this.depth)
    {
    case 32:
      i = paramInt2 * this.bytesPerLine + paramInt1 * 4;
      for (i4 = 0; i4 < paramInt3; i4++)
      {
        i1 = paramArrayOfInt[n];
        this.data[i] = ((byte)(i1 >> 24 & 0xFF));
        this.data[(i + 1)] = ((byte)(i1 >> 16 & 0xFF));
        this.data[(i + 2)] = ((byte)(i1 >> 8 & 0xFF));
        this.data[(i + 3)] = ((byte)(i1 & 0xFF));
        n++;
        i2++;
        if (i2 >= this.width)
        {
          i3++;
          i = i3 * this.bytesPerLine;
          i2 = 0;
        }
        else
        {
          i += 4;
        }
      }
      return;
    case 24:
      i = paramInt2 * this.bytesPerLine + paramInt1 * 3;
      for (i4 = 0; i4 < paramInt3; i4++)
      {
        i1 = paramArrayOfInt[n];
        this.data[i] = ((byte)(i1 >> 16 & 0xFF));
        this.data[(i + 1)] = ((byte)(i1 >> 8 & 0xFF));
        this.data[(i + 2)] = ((byte)(i1 & 0xFF));
        n++;
        i2++;
        if (i2 >= this.width)
        {
          i3++;
          i = i3 * this.bytesPerLine;
          i2 = 0;
        }
        else
        {
          i += 3;
        }
      }
      return;
    case 16:
      i = paramInt2 * this.bytesPerLine + paramInt1 * 2;
      for (i4 = 0; i4 < paramInt3; i4++)
      {
        i1 = paramArrayOfInt[n];
        this.data[i] = ((byte)(i1 & 0xFF));
        this.data[(i + 1)] = ((byte)(i1 >> 8 & 0xFF));
        n++;
        i2++;
        if (i2 >= this.width)
        {
          i3++;
          i = i3 * this.bytesPerLine;
          i2 = 0;
        }
        else
        {
          i += 2;
        }
      }
      return;
    case 8:
      i = paramInt2 * this.bytesPerLine + paramInt1;
      for (i4 = 0; i4 < paramInt3; i4++)
      {
        this.data[i] = ((byte)(paramArrayOfInt[n] & 0xFF));
        n++;
        i2++;
        if (i2 >= this.width)
        {
          i3++;
          i = i3 * this.bytesPerLine;
          i2 = 0;
        }
        else
        {
          i++;
        }
      }
      return;
    case 4:
      i = paramInt2 * this.bytesPerLine + (paramInt1 >> 1);
      i4 = (paramInt1 & 0x1) == 0 ? 1 : 0;
      while (m > 0)
      {
        j = paramArrayOfInt[n] & 0xF;
        if (i4 != 0)
          this.data[i] = ((byte)(this.data[i] & 0xF | j << 4));
        else
          this.data[i] = ((byte)(this.data[i] & 0xF0 | j));
        n++;
        m--;
        i2++;
        if (i2 >= this.width)
        {
          i3++;
          i = i3 * this.bytesPerLine;
          i4 = 1;
          i2 = 0;
        }
        else
        {
          if (i4 == 0)
            i++;
          i4 = i4 != 0 ? 0 : 1;
        }
      }
      return;
    case 2:
      byte[] arrayOfByte = { -4, -13, -49, 63 };
      i = paramInt2 * this.bytesPerLine + (paramInt1 >> 2);
      int i5 = 3 - paramInt1 % 4;
      while (m > 0)
      {
        j = paramArrayOfInt[n] & 0x3;
        this.data[i] = ((byte)(this.data[i] & arrayOfByte[i5] | j << i5 * 2));
        n++;
        m--;
        i2++;
        if (i2 >= this.width)
        {
          i3++;
          i = i3 * this.bytesPerLine;
          i5 = 3;
          i2 = 0;
        }
        else if (i5 == 0)
        {
          i++;
          i5 = 3;
        }
        else
        {
          i5--;
        }
      }
      return;
    case 1:
      i = paramInt2 * this.bytesPerLine + (paramInt1 >> 3);
      while (m > 0)
      {
        int k = 1 << 7 - (i2 & 0x7);
        if ((paramArrayOfInt[n] & 0x1) == 1)
          this.data[i] = ((byte)(this.data[i] & 0xFF | k));
        else
          this.data[i] = ((byte)(this.data[i] & 0xFF & (k ^ 0xFFFFFFFF)));
        n++;
        m--;
        i2++;
        if (i2 >= this.width)
        {
          i3++;
          i = i3 * this.bytesPerLine;
          i2 = 0;
        }
        else if (k == 1)
        {
          i++;
        }
      }
      return;
    }
    SWT.error(38);
  }

  static PaletteData bwPalette()
  {
    return new PaletteData(new RGB[] { new RGB(0, 0, 0), new RGB(255, 255, 255) });
  }

  static int getMSBOffset(int paramInt)
  {
    for (int i = 31; i >= 0; i--)
      if ((paramInt >> i & 0x1) != 0)
        return i + 1;
    return 0;
  }

  static int closestMatch(int paramInt1, byte paramByte1, byte paramByte2, byte paramByte3, int paramInt2, int paramInt3, int paramInt4, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3)
  {
    int i;
    int j;
    int k;
    if (paramInt1 > 8)
    {
      i = 32 - getMSBOffset(paramInt2);
      j = 32 - getMSBOffset(paramInt3);
      k = 32 - getMSBOffset(paramInt4);
      return paramByte1 << 24 >>> i & paramInt2 | paramByte2 << 24 >>> j & paramInt3 | paramByte3 << 24 >>> k & paramInt4;
    }
    int m = 2147483647;
    int n = 0;
    int i1 = paramArrayOfByte1.length;
    for (int i2 = 0; i2 < i1; i2++)
    {
      i = (paramArrayOfByte1[i2] & 0xFF) - (paramByte1 & 0xFF);
      j = (paramArrayOfByte2[i2] & 0xFF) - (paramByte2 & 0xFF);
      k = (paramArrayOfByte3[i2] & 0xFF) - (paramByte3 & 0xFF);
      int i3 = i * i + j * j + k * k;
      if (i3 < m)
      {
        n = i2;
        if (i3 == 0)
          break;
        m = i3;
      }
    }
    return n;
  }

  static final ImageData convertMask(ImageData paramImageData)
  {
    if (paramImageData.depth == 1)
      return paramImageData;
    PaletteData localPaletteData = new PaletteData(new RGB[] { new RGB(0, 0, 0), new RGB(255, 255, 255) });
    ImageData localImageData = new ImageData(paramImageData.width, paramImageData.height, 1, localPaletteData);
    int i = 0;
    RGB[] arrayOfRGB = paramImageData.getRGBs();
    if (arrayOfRGB != null)
      while (i < arrayOfRGB.length)
      {
        if (arrayOfRGB[i].equals(localPaletteData.colors[0]))
          break;
        i++;
      }
    int[] arrayOfInt = new int[paramImageData.width];
    for (int j = 0; j < paramImageData.height; j++)
    {
      paramImageData.getPixels(0, j, paramImageData.width, arrayOfInt, 0);
      for (int k = 0; k < arrayOfInt.length; k++)
        if (arrayOfInt[k] == i)
          arrayOfInt[k] = 0;
        else
          arrayOfInt[k] = 1;
      localImageData.setPixels(0, j, paramImageData.width, arrayOfInt, 0);
    }
    return localImageData;
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
      System.arraycopy(paramArrayOfByte, m, arrayOfByte, n, i);
      m += j;
      n += k;
    }
    return arrayOfByte;
  }

  static void blit(int paramInt1, byte[] paramArrayOfByte1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11, int paramInt12, byte[] paramArrayOfByte2, int paramInt13, int paramInt14, int paramInt15, byte[] paramArrayOfByte3, int paramInt16, int paramInt17, int paramInt18, int paramInt19, int paramInt20, int paramInt21, int paramInt22, int paramInt23, int paramInt24, int paramInt25, boolean paramBoolean1, boolean paramBoolean2)
  {
    if ((paramInt21 <= 0) || (paramInt22 <= 0) || (paramInt12 == 0))
      return;
    int i = 0;
    int j = 0;
    int k = paramInt21 - 1;
    int m = k != 0 ? (int)(((paramInt7 << 16) - 1L) / k) : 0;
    int n = paramInt22 - 1;
    int i1 = n != 0 ? (int)(((paramInt8 << 16) - 1L) / n) : 0;
    int i2;
    int i3;
    switch (paramInt2)
    {
    case 8:
      i2 = 1;
      i3 = 0;
      break;
    case 16:
      i2 = 2;
      i3 = paramInt4 == 1 ? 1 : 2;
      break;
    case 24:
      i2 = 3;
      i3 = 3;
      break;
    case 32:
      i2 = 4;
      i3 = paramInt4 == 1 ? 4 : 5;
      break;
    default:
      return;
    }
    int i4 = paramInt6 * paramInt3 + paramInt5 * i2;
    int i5;
    int i6;
    switch (paramInt16)
    {
    case 8:
      i5 = 1;
      i6 = 0;
      break;
    case 16:
      i5 = 2;
      i6 = paramInt18 == 1 ? 1 : 2;
      break;
    case 24:
      i5 = 3;
      i6 = 3;
      break;
    case 32:
      i5 = 4;
      i6 = paramInt18 == 1 ? 4 : 5;
      break;
    default:
      return;
    }
    int i7 = (paramBoolean2 ? paramInt20 + n : paramInt20) * paramInt17 + (paramBoolean1 ? paramInt19 + k : paramInt19) * i5;
    int i8 = paramBoolean1 ? -i5 : i5;
    int i9 = paramBoolean2 ? -paramInt17 : paramInt17;
    int i10;
    if ((paramInt1 & 0x2) != 0)
    {
      switch (paramInt12)
      {
      case -3:
      case -1:
        if (paramArrayOfByte2 == null)
          paramInt12 = 65536;
        i10 = paramInt15 * paramInt13 + paramInt14;
        break;
      case -4:
        if (paramArrayOfByte2 == null)
          paramInt12 = 65536;
        paramInt13 <<= 3;
        i10 = paramInt15 * paramInt13 + paramInt14;
        break;
      case -5:
        return;
      case -6:
        if (paramArrayOfByte2 == null)
          paramInt12 = 65536;
        i10 = 0;
        break;
      default:
        paramInt12 = (paramInt12 << 16) / 255;
      case -2:
        i10 = 0;
        break;
      }
    }
    else
    {
      paramInt12 = 65536;
      i10 = 0;
    }
    int i11 = i7;
    int i12 = i4;
    int i14;
    int i16;
    if ((paramInt12 == 65536) && (i3 == i6) && (paramInt9 == paramInt23) && (paramInt10 == paramInt24) && (paramInt11 == paramInt25) && (i == j))
    {
      switch (i2)
      {
      case 1:
        i13 = paramInt22;
        i14 = i1;
        while (i13 > 0)
        {
          i15 = paramInt21;
          for (i16 = m; i15 > 0; i16 = (i16 & 0xFFFF) + m)
          {
            paramArrayOfByte3[i11] = paramArrayOfByte1[i12];
            i12 += (i16 >>> 16);
            i15--;
            i11 += i8;
          }
          i13--;
          i12 = i4 += (i14 >>> 16) * paramInt3;
          i14 = (i14 & 0xFFFF) + i1;
          i11 = i7 += i9;
        }
        break;
      case 2:
        i13 = paramInt22;
        i14 = i1;
        while (i13 > 0)
        {
          i15 = paramInt21;
          for (i16 = m; i15 > 0; i16 = (i16 & 0xFFFF) + m)
          {
            paramArrayOfByte3[i11] = paramArrayOfByte1[i12];
            paramArrayOfByte3[(i11 + 1)] = paramArrayOfByte1[(i12 + 1)];
            i12 += (i16 >>> 16) * 2;
            i15--;
            i11 += i8;
          }
          i13--;
          i12 = i4 += (i14 >>> 16) * paramInt3;
          i14 = (i14 & 0xFFFF) + i1;
          i11 = i7 += i9;
        }
        break;
      case 3:
        i13 = paramInt22;
        i14 = i1;
        while (i13 > 0)
        {
          i15 = paramInt21;
          for (i16 = m; i15 > 0; i16 = (i16 & 0xFFFF) + m)
          {
            paramArrayOfByte3[i11] = paramArrayOfByte1[i12];
            paramArrayOfByte3[(i11 + 1)] = paramArrayOfByte1[(i12 + 1)];
            paramArrayOfByte3[(i11 + 2)] = paramArrayOfByte1[(i12 + 2)];
            i12 += (i16 >>> 16) * 3;
            i15--;
            i11 += i8;
          }
          i13--;
          i12 = i4 += (i14 >>> 16) * paramInt3;
          i14 = (i14 & 0xFFFF) + i1;
          i11 = i7 += i9;
        }
        break;
      case 4:
        i13 = paramInt22;
        i14 = i1;
        while (i13 > 0)
        {
          i15 = paramInt21;
          for (i16 = m; i15 > 0; i16 = (i16 & 0xFFFF) + m)
          {
            paramArrayOfByte3[i11] = paramArrayOfByte1[i12];
            paramArrayOfByte3[(i11 + 1)] = paramArrayOfByte1[(i12 + 1)];
            paramArrayOfByte3[(i11 + 2)] = paramArrayOfByte1[(i12 + 2)];
            paramArrayOfByte3[(i11 + 3)] = paramArrayOfByte1[(i12 + 3)];
            i12 += (i16 >>> 16) * 4;
            i15--;
            i11 += i8;
          }
          i13--;
          i12 = i4 += (i14 >>> 16) * paramInt3;
          i14 = (i14 & 0xFFFF) + i1;
          i11 = i7 += i9;
        }
      }
      return;
    }
    if ((paramInt12 == 65536) && (i3 == 4) && (i6 == 4) && (paramInt9 == 65280) && (paramInt10 == 16711680) && (paramInt11 == -16777216) && (paramInt23 == 16711680) && (paramInt24 == 65280) && (paramInt25 == 255))
    {
      i13 = paramInt22;
      i14 = i1;
      while (i13 > 0)
      {
        i15 = paramInt21;
        for (i16 = m; i15 > 0; i16 = (i16 & 0xFFFF) + m)
        {
          paramArrayOfByte3[i11] = paramArrayOfByte1[(i12 + 3)];
          paramArrayOfByte3[(i11 + 1)] = paramArrayOfByte1[(i12 + 2)];
          paramArrayOfByte3[(i11 + 2)] = paramArrayOfByte1[(i12 + 1)];
          paramArrayOfByte3[(i11 + 3)] = paramArrayOfByte1[i12];
          i12 += (i16 >>> 16) * 4;
          i15--;
          i11 += i8;
        }
        i13--;
        i12 = i4 += (i14 >>> 16) * paramInt3;
        i14 = (i14 & 0xFFFF) + i1;
        i11 = i7 += i9;
      }
      return;
    }
    if ((paramInt12 == 65536) && (i3 == 3) && (i6 == 4) && (paramInt9 == 255) && (paramInt10 == 65280) && (paramInt11 == 16711680) && (paramInt23 == 16711680) && (paramInt24 == 65280) && (paramInt25 == 255))
    {
      i13 = paramInt22;
      i14 = i1;
      while (i13 > 0)
      {
        i15 = paramInt21;
        for (i16 = m; i15 > 0; i16 = (i16 & 0xFFFF) + m)
        {
          paramArrayOfByte3[i11] = 0;
          paramArrayOfByte3[(i11 + 1)] = paramArrayOfByte1[(i12 + 2)];
          paramArrayOfByte3[(i11 + 2)] = paramArrayOfByte1[(i12 + 1)];
          paramArrayOfByte3[(i11 + 3)] = paramArrayOfByte1[i12];
          i12 += (i16 >>> 16) * 3;
          i15--;
          i11 += i8;
        }
        i13--;
        i12 = i4 += (i14 >>> 16) * paramInt3;
        i14 = (i14 & 0xFFFF) + i1;
        i11 = i7 += i9;
      }
      return;
    }
    int i13 = getChannelShift(paramInt9);
    byte[] arrayOfByte1 = ANY_TO_EIGHT[getChannelWidth(paramInt9, i13)];
    int i15 = getChannelShift(paramInt10);
    byte[] arrayOfByte2 = ANY_TO_EIGHT[getChannelWidth(paramInt10, i15)];
    int i17 = getChannelShift(paramInt11);
    byte[] arrayOfByte3 = ANY_TO_EIGHT[getChannelWidth(paramInt11, i17)];
    int i18 = getChannelShift(i);
    byte[] arrayOfByte4 = ANY_TO_EIGHT[getChannelWidth(i, i18)];
    int i19 = getChannelShift(paramInt23);
    int i20 = getChannelWidth(paramInt23, i19);
    byte[] arrayOfByte5 = ANY_TO_EIGHT[i20];
    int i21 = 8 - i20;
    int i22 = getChannelShift(paramInt24);
    int i23 = getChannelWidth(paramInt24, i22);
    byte[] arrayOfByte6 = ANY_TO_EIGHT[i23];
    int i24 = 8 - i23;
    int i25 = getChannelShift(paramInt25);
    int i26 = getChannelWidth(paramInt25, i25);
    byte[] arrayOfByte7 = ANY_TO_EIGHT[i26];
    int i27 = 8 - i26;
    int i28 = getChannelShift(j);
    int i29 = getChannelWidth(j, i28);
    byte[] arrayOfByte8 = ANY_TO_EIGHT[i29];
    int i30 = 8 - i29;
    int i31 = i10;
    int i32 = paramInt12;
    int i33 = 0;
    int i34 = 0;
    int i35 = 0;
    int i36 = 0;
    int i37 = 0;
    int i38 = 0;
    int i39 = 0;
    int i40 = 0;
    int i41 = paramInt22;
    int i42 = i1;
    while (i41 > 0)
    {
      int i43 = paramInt21;
      for (int i44 = m; i43 > 0; i44 = (i44 & 0xFFFF) + m)
      {
        int i45;
        switch (i3)
        {
        case 0:
          i45 = paramArrayOfByte1[i12] & 0xFF;
          i12 += (i44 >>> 16);
          i33 = arrayOfByte1[((i45 & paramInt9) >>> i13)] & 0xFF;
          i34 = arrayOfByte2[((i45 & paramInt10) >>> i15)] & 0xFF;
          i35 = arrayOfByte3[((i45 & paramInt11) >>> i17)] & 0xFF;
          i36 = arrayOfByte4[((i45 & i) >>> i18)] & 0xFF;
          break;
        case 1:
          i45 = (paramArrayOfByte1[i12] & 0xFF) << 8 | paramArrayOfByte1[(i12 + 1)] & 0xFF;
          i12 += (i44 >>> 16) * 2;
          i33 = arrayOfByte1[((i45 & paramInt9) >>> i13)] & 0xFF;
          i34 = arrayOfByte2[((i45 & paramInt10) >>> i15)] & 0xFF;
          i35 = arrayOfByte3[((i45 & paramInt11) >>> i17)] & 0xFF;
          i36 = arrayOfByte4[((i45 & i) >>> i18)] & 0xFF;
          break;
        case 2:
          i45 = (paramArrayOfByte1[(i12 + 1)] & 0xFF) << 8 | paramArrayOfByte1[i12] & 0xFF;
          i12 += (i44 >>> 16) * 2;
          i33 = arrayOfByte1[((i45 & paramInt9) >>> i13)] & 0xFF;
          i34 = arrayOfByte2[((i45 & paramInt10) >>> i15)] & 0xFF;
          i35 = arrayOfByte3[((i45 & paramInt11) >>> i17)] & 0xFF;
          i36 = arrayOfByte4[((i45 & i) >>> i18)] & 0xFF;
          break;
        case 3:
          i45 = ((paramArrayOfByte1[i12] & 0xFF) << 8 | paramArrayOfByte1[(i12 + 1)] & 0xFF) << 8 | paramArrayOfByte1[(i12 + 2)] & 0xFF;
          i12 += (i44 >>> 16) * 3;
          i33 = arrayOfByte1[((i45 & paramInt9) >>> i13)] & 0xFF;
          i34 = arrayOfByte2[((i45 & paramInt10) >>> i15)] & 0xFF;
          i35 = arrayOfByte3[((i45 & paramInt11) >>> i17)] & 0xFF;
          i36 = arrayOfByte4[((i45 & i) >>> i18)] & 0xFF;
          break;
        case 4:
          i45 = (((paramArrayOfByte1[i12] & 0xFF) << 8 | paramArrayOfByte1[(i12 + 1)] & 0xFF) << 8 | paramArrayOfByte1[(i12 + 2)] & 0xFF) << 8 | paramArrayOfByte1[(i12 + 3)] & 0xFF;
          i12 += (i44 >>> 16) * 4;
          i33 = arrayOfByte1[((i45 & paramInt9) >>> i13)] & 0xFF;
          i34 = arrayOfByte2[((i45 & paramInt10) >>> i15)] & 0xFF;
          i35 = arrayOfByte3[((i45 & paramInt11) >>> i17)] & 0xFF;
          i36 = arrayOfByte4[((i45 & i) >>> i18)] & 0xFF;
          break;
        case 5:
          i45 = (((paramArrayOfByte1[(i12 + 3)] & 0xFF) << 8 | paramArrayOfByte1[(i12 + 2)] & 0xFF) << 8 | paramArrayOfByte1[(i12 + 1)] & 0xFF) << 8 | paramArrayOfByte1[i12] & 0xFF;
          i12 += (i44 >>> 16) * 4;
          i33 = arrayOfByte1[((i45 & paramInt9) >>> i13)] & 0xFF;
          i34 = arrayOfByte2[((i45 & paramInt10) >>> i15)] & 0xFF;
          i35 = arrayOfByte3[((i45 & paramInt11) >>> i17)] & 0xFF;
          i36 = arrayOfByte4[((i45 & i) >>> i18)] & 0xFF;
        }
        switch (paramInt12)
        {
        case -1:
          i32 = ((paramArrayOfByte2[i31] & 0xFF) << 16) / 255;
          i31 += (i44 >> 16);
          break;
        case -2:
          i32 = (i36 << 16) / 255;
          break;
        case -3:
          i32 = paramArrayOfByte2[i31] != 0 ? 65536 : 0;
          i31 += (i44 >> 16);
          break;
        case -4:
          i32 = paramArrayOfByte2[(i31 >> 3)] << (i31 & 0x7) + 9 & 0x10000;
          i31 += (i44 >> 16);
          break;
        case -6:
          i32 = 65536;
          for (i45 = 0; i45 < paramArrayOfByte2.length; i45 += 3)
            if ((i33 == paramArrayOfByte2[i45]) && (i34 == paramArrayOfByte2[(i45 + 1)]) && (i35 == paramArrayOfByte2[(i45 + 2)]))
            {
              i32 = 0;
              break;
            }
        case -5:
        }
        if (i32 != 65536)
        {
          if (i32 != 0)
          {
            switch (i6)
            {
            case 0:
              i45 = paramArrayOfByte3[i11] & 0xFF;
              i37 = arrayOfByte5[((i45 & paramInt23) >>> i19)] & 0xFF;
              i38 = arrayOfByte6[((i45 & paramInt24) >>> i22)] & 0xFF;
              i39 = arrayOfByte7[((i45 & paramInt25) >>> i25)] & 0xFF;
              i40 = arrayOfByte8[((i45 & j) >>> i28)] & 0xFF;
              break;
            case 1:
              i45 = (paramArrayOfByte3[i11] & 0xFF) << 8 | paramArrayOfByte3[(i11 + 1)] & 0xFF;
              i37 = arrayOfByte5[((i45 & paramInt23) >>> i19)] & 0xFF;
              i38 = arrayOfByte6[((i45 & paramInt24) >>> i22)] & 0xFF;
              i39 = arrayOfByte7[((i45 & paramInt25) >>> i25)] & 0xFF;
              i40 = arrayOfByte8[((i45 & j) >>> i28)] & 0xFF;
              break;
            case 2:
              i45 = (paramArrayOfByte3[(i11 + 1)] & 0xFF) << 8 | paramArrayOfByte3[i11] & 0xFF;
              i37 = arrayOfByte5[((i45 & paramInt23) >>> i19)] & 0xFF;
              i38 = arrayOfByte6[((i45 & paramInt24) >>> i22)] & 0xFF;
              i39 = arrayOfByte7[((i45 & paramInt25) >>> i25)] & 0xFF;
              i40 = arrayOfByte8[((i45 & j) >>> i28)] & 0xFF;
              break;
            case 3:
              i45 = ((paramArrayOfByte3[i11] & 0xFF) << 8 | paramArrayOfByte3[(i11 + 1)] & 0xFF) << 8 | paramArrayOfByte3[(i11 + 2)] & 0xFF;
              i37 = arrayOfByte5[((i45 & paramInt23) >>> i19)] & 0xFF;
              i38 = arrayOfByte6[((i45 & paramInt24) >>> i22)] & 0xFF;
              i39 = arrayOfByte7[((i45 & paramInt25) >>> i25)] & 0xFF;
              i40 = arrayOfByte8[((i45 & j) >>> i28)] & 0xFF;
              break;
            case 4:
              i45 = (((paramArrayOfByte3[i11] & 0xFF) << 8 | paramArrayOfByte3[(i11 + 1)] & 0xFF) << 8 | paramArrayOfByte3[(i11 + 2)] & 0xFF) << 8 | paramArrayOfByte3[(i11 + 3)] & 0xFF;
              i37 = arrayOfByte5[((i45 & paramInt23) >>> i19)] & 0xFF;
              i38 = arrayOfByte6[((i45 & paramInt24) >>> i22)] & 0xFF;
              i39 = arrayOfByte7[((i45 & paramInt25) >>> i25)] & 0xFF;
              i40 = arrayOfByte8[((i45 & j) >>> i28)] & 0xFF;
              break;
            case 5:
              i45 = (((paramArrayOfByte3[(i11 + 3)] & 0xFF) << 8 | paramArrayOfByte3[(i11 + 2)] & 0xFF) << 8 | paramArrayOfByte3[(i11 + 1)] & 0xFF) << 8 | paramArrayOfByte3[i11] & 0xFF;
              i37 = arrayOfByte5[((i45 & paramInt23) >>> i19)] & 0xFF;
              i38 = arrayOfByte6[((i45 & paramInt24) >>> i22)] & 0xFF;
              i39 = arrayOfByte7[((i45 & paramInt25) >>> i25)] & 0xFF;
              i40 = arrayOfByte8[((i45 & j) >>> i28)] & 0xFF;
            }
            i36 = i40 + ((i36 - i40) * i32 >> 16);
            i33 = i37 + ((i33 - i37) * i32 >> 16);
            i34 = i38 + ((i34 - i38) * i32 >> 16);
            i35 = i39 + ((i35 - i39) * i32 >> 16);
          }
        }
        else
        {
          i45 = i33 >>> i21 << i19 | i34 >>> i24 << i22 | i35 >>> i27 << i25 | i36 >>> i30 << i28;
          switch (i6)
          {
          case 0:
            paramArrayOfByte3[i11] = ((byte)i45);
            break;
          case 1:
            paramArrayOfByte3[i11] = ((byte)(i45 >>> 8));
            paramArrayOfByte3[(i11 + 1)] = ((byte)(i45 & 0xFF));
            break;
          case 2:
            paramArrayOfByte3[i11] = ((byte)(i45 & 0xFF));
            paramArrayOfByte3[(i11 + 1)] = ((byte)(i45 >>> 8));
            break;
          case 3:
            paramArrayOfByte3[i11] = ((byte)(i45 >>> 16));
            paramArrayOfByte3[(i11 + 1)] = ((byte)(i45 >>> 8));
            paramArrayOfByte3[(i11 + 2)] = ((byte)(i45 & 0xFF));
            break;
          case 4:
            paramArrayOfByte3[i11] = ((byte)(i45 >>> 24));
            paramArrayOfByte3[(i11 + 1)] = ((byte)(i45 >>> 16));
            paramArrayOfByte3[(i11 + 2)] = ((byte)(i45 >>> 8));
            paramArrayOfByte3[(i11 + 3)] = ((byte)(i45 & 0xFF));
            break;
          case 5:
            paramArrayOfByte3[i11] = ((byte)(i45 & 0xFF));
            paramArrayOfByte3[(i11 + 1)] = ((byte)(i45 >>> 8));
            paramArrayOfByte3[(i11 + 2)] = ((byte)(i45 >>> 16));
            paramArrayOfByte3[(i11 + 3)] = ((byte)(i45 >>> 24));
          }
        }
        i43--;
        i11 += i8;
      }
      i41--;
      i12 = i4 += (i42 >>> 16) * paramInt3;
      i31 = i10 += (i42 >>> 16) * paramInt13;
      i42 = (i42 & 0xFFFF) + i1;
      i11 = i7 += i9;
    }
  }

  static void blit(int paramInt1, byte[] paramArrayOfByte1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, byte[] paramArrayOfByte4, int paramInt9, byte[] paramArrayOfByte5, int paramInt10, int paramInt11, int paramInt12, byte[] paramArrayOfByte6, int paramInt13, int paramInt14, int paramInt15, int paramInt16, int paramInt17, int paramInt18, int paramInt19, byte[] paramArrayOfByte7, byte[] paramArrayOfByte8, byte[] paramArrayOfByte9, boolean paramBoolean1, boolean paramBoolean2)
  {
    if ((paramInt18 <= 0) || (paramInt19 <= 0) || (paramInt9 == 0))
      return;
    int i = paramInt18 - 1;
    int j = i != 0 ? (int)(((paramInt7 << 16) - 1L) / i) : 0;
    int k = paramInt19 - 1;
    int m = k != 0 ? (int)(((paramInt8 << 16) - 1L) / k) : 0;
    int n;
    switch (paramInt2)
    {
    case 8:
      n = 6;
      break;
    case 4:
      paramInt3 <<= 1;
      n = 7;
      break;
    case 2:
      paramInt3 <<= 2;
      n = 8;
      break;
    case 1:
      paramInt3 <<= 3;
      n = paramInt4 == 1 ? 9 : 10;
      break;
    case 3:
    case 5:
    case 6:
    case 7:
    default:
      return;
    }
    int i1 = paramInt6 * paramInt3 + paramInt5;
    int i2;
    switch (paramInt13)
    {
    case 8:
      i2 = 6;
      break;
    case 4:
      paramInt14 <<= 1;
      i2 = 7;
      break;
    case 2:
      paramInt14 <<= 2;
      i2 = 8;
      break;
    case 1:
      paramInt14 <<= 3;
      i2 = paramInt15 == 1 ? 9 : 10;
      break;
    case 3:
    case 5:
    case 6:
    case 7:
    default:
      return;
    }
    int i3 = (paramBoolean2 ? paramInt17 + k : paramInt17) * paramInt14 + (paramBoolean1 ? paramInt16 + i : paramInt16);
    int i4 = paramBoolean1 ? -1 : 1;
    int i5 = paramBoolean2 ? -paramInt14 : paramInt14;
    int i6;
    if ((paramInt1 & 0x2) != 0)
    {
      switch (paramInt9)
      {
      case -3:
      case -1:
        if (paramArrayOfByte5 == null)
          paramInt9 = 65536;
        i6 = paramInt12 * paramInt10 + paramInt11;
        break;
      case -4:
        if (paramArrayOfByte5 == null)
          paramInt9 = 65536;
        paramInt10 <<= 3;
        i6 = paramInt12 * paramInt10 + paramInt11;
        break;
      case -6:
      case -5:
        if (paramArrayOfByte5 == null)
          paramInt9 = 65536;
        i6 = 0;
        break;
      default:
        paramInt9 = (paramInt9 << 16) / 255;
      case -2:
        i6 = 0;
        break;
      }
    }
    else
    {
      paramInt9 = 65536;
      i6 = 0;
    }
    int i7 = (paramInt1 & 0x4) != 0 ? 1 : 0;
    int i8 = i3;
    int i9 = i1;
    int i10 = i6;
    int i11 = 1 << paramInt13;
    if ((paramArrayOfByte7 != null) && (paramArrayOfByte7.length < i11))
      i11 = paramArrayOfByte7.length;
    byte[] arrayOfByte = (byte[])null;
    int i12 = 1;
    int i20;
    int i21;
    switch (paramInt9)
    {
    case 65536:
      if ((n == i2) && (paramArrayOfByte2 == paramArrayOfByte7) && (paramArrayOfByte3 == paramArrayOfByte8) && (paramArrayOfByte4 == paramArrayOfByte9))
        arrayOfByte = ONE_TO_ONE_MAPPING;
      else if ((paramArrayOfByte2 == null) || (paramArrayOfByte7 == null))
        if (paramInt2 <= paramInt13)
        {
          arrayOfByte = ONE_TO_ONE_MAPPING;
        }
        else
        {
          arrayOfByte = new byte[1 << paramInt2];
          i13 = 255 << paramInt13 >>> 8;
          for (i14 = 0; i14 < arrayOfByte.length; i14++)
            arrayOfByte[i14] = ((byte)(i14 & i13));
        }
      break;
    case -6:
    case -5:
    case -4:
    case -3:
      i13 = 1 << paramInt2;
      arrayOfByte = new byte[i13];
      if ((paramArrayOfByte2 != null) && (paramArrayOfByte2.length < i13))
        i13 = paramArrayOfByte2.length;
      for (i14 = 0; i14 < i13; i14++)
      {
        i15 = paramArrayOfByte2[i14] & 0xFF;
        i16 = paramArrayOfByte3[i14] & 0xFF;
        i17 = paramArrayOfByte4[i14] & 0xFF;
        i18 = 0;
        i19 = 2147483647;
        for (i20 = 0; i20 < i11; i20++)
        {
          i21 = (paramArrayOfByte7[i20] & 0xFF) - i15;
          int i22 = (paramArrayOfByte8[i20] & 0xFF) - i16;
          i23 = (paramArrayOfByte9[i20] & 0xFF) - i17;
          i24 = i21 * i21 + i22 * i22 + i23 * i23;
          if (i24 < i19)
          {
            i18 = i20;
            if (i24 == 0)
              break;
            i19 = i24;
          }
        }
        arrayOfByte[i14] = ((byte)i18);
        if (i19 != 0)
          i12 = 0;
      }
    }
    if ((arrayOfByte != null) && ((i12 != 0) || (i7 == 0)))
    {
      if ((n == i2) && (paramInt9 == 65536));
      switch (n)
      {
      case 6:
        i13 = paramInt19;
        i14 = m;
        while (i13 > 0)
        {
          i15 = paramInt18;
          for (i16 = j; i15 > 0; i16 = (i16 & 0xFFFF) + j)
          {
            paramArrayOfByte6[i8] = arrayOfByte[(paramArrayOfByte1[i9] & 0xFF)];
            i9 += (i16 >>> 16);
            i15--;
            i8 += i4;
          }
          i13--;
          i9 = i1 += (i14 >>> 16) * paramInt3;
          i14 = (i14 & 0xFFFF) + m;
          i8 = i3 += i5;
        }
        break;
      case 7:
        i13 = paramInt19;
        i14 = m;
        while (i13 > 0)
        {
          i15 = paramInt18;
          for (i16 = j; i15 > 0; i16 = (i16 & 0xFFFF) + j)
          {
            if ((i9 & 0x1) != 0)
              i17 = arrayOfByte[(paramArrayOfByte1[(i9 >> 1)] & 0xF)];
            else
              i17 = paramArrayOfByte1[(i9 >> 1)] >>> 4 & 0xF;
            i9 += (i16 >>> 16);
            if ((i8 & 0x1) != 0)
              paramArrayOfByte6[(i8 >> 1)] = ((byte)(paramArrayOfByte6[(i8 >> 1)] & 0xF0 | i17));
            else
              paramArrayOfByte6[(i8 >> 1)] = ((byte)(paramArrayOfByte6[(i8 >> 1)] & 0xF | i17 << 4));
            i15--;
            i8 += i4;
          }
          i13--;
          i9 = i1 += (i14 >>> 16) * paramInt3;
          i14 = (i14 & 0xFFFF) + m;
          i8 = i3 += i5;
        }
        break;
      case 8:
        i13 = paramInt19;
        i14 = m;
        while (i13 > 0)
        {
          i15 = paramInt18;
          for (i16 = j; i15 > 0; i16 = (i16 & 0xFFFF) + j)
          {
            i17 = arrayOfByte[(paramArrayOfByte1[(i9 >> 2)] >>> 6 - (i9 & 0x3) * 2 & 0x3)];
            i9 += (i16 >>> 16);
            i18 = 6 - (i8 & 0x3) * 2;
            paramArrayOfByte6[(i8 >> 2)] = ((byte)(paramArrayOfByte6[(i8 >> 2)] & (3 << i18 ^ 0xFFFFFFFF) | i17 << i18));
            i15--;
            i8 += i4;
          }
          i13--;
          i9 = i1 += (i14 >>> 16) * paramInt3;
          i14 = (i14 & 0xFFFF) + m;
          i8 = i3 += i5;
        }
        break;
      case 9:
        i13 = paramInt19;
        i14 = m;
        while (i13 > 0)
        {
          i15 = paramInt18;
          for (i16 = j; i15 > 0; i16 = (i16 & 0xFFFF) + j)
          {
            i17 = arrayOfByte[(paramArrayOfByte1[(i9 >> 3)] >>> 7 - (i9 & 0x7) & 0x1)];
            i9 += (i16 >>> 16);
            i18 = 7 - (i8 & 0x7);
            paramArrayOfByte6[(i8 >> 3)] = ((byte)(paramArrayOfByte6[(i8 >> 3)] & (1 << i18 ^ 0xFFFFFFFF) | i17 << i18));
            i15--;
            i8 += i4;
          }
          i13--;
          i9 = i1 += (i14 >>> 16) * paramInt3;
          i14 = (i14 & 0xFFFF) + m;
          i8 = i3 += i5;
        }
        break;
      case 10:
        i13 = paramInt19;
        i14 = m;
        while (i13 > 0)
        {
          i15 = paramInt18;
          for (i16 = j; i15 > 0; i16 = (i16 & 0xFFFF) + j)
          {
            i17 = arrayOfByte[(paramArrayOfByte1[(i9 >> 3)] >>> (i9 & 0x7) & 0x1)];
            i9 += (i16 >>> 16);
            i18 = i8 & 0x7;
            paramArrayOfByte6[(i8 >> 3)] = ((byte)(paramArrayOfByte6[(i8 >> 3)] & (1 << i18 ^ 0xFFFFFFFF) | i17 << i18));
            i15--;
            i8 += i4;
          }
          i13--;
          i9 = i1 += (i14 >>> 16) * paramInt3;
          i14 = (i14 & 0xFFFF) + m;
          i8 = i3 += i5;
        }
      default:
        break;
        i13 = paramInt19;
        i14 = m;
        label2463: 
        while (i13 > 0)
        {
          i15 = paramInt18;
          for (i16 = j; i15 > 0; i16 = (i16 & 0xFFFF) + j)
          {
            switch (n)
            {
            case 6:
              i17 = paramArrayOfByte1[i9] & 0xFF;
              i9 += (i16 >>> 16);
              break;
            case 7:
              if ((i9 & 0x1) != 0)
                i17 = paramArrayOfByte1[(i9 >> 1)] & 0xF;
              else
                i17 = paramArrayOfByte1[(i9 >> 1)] >>> 4 & 0xF;
              i9 += (i16 >>> 16);
              break;
            case 8:
              i17 = paramArrayOfByte1[(i9 >> 2)] >>> 6 - (i9 & 0x3) * 2 & 0x3;
              i9 += (i16 >>> 16);
              break;
            case 9:
              i17 = paramArrayOfByte1[(i9 >> 3)] >>> 7 - (i9 & 0x7) & 0x1;
              i9 += (i16 >>> 16);
              break;
            case 10:
              i17 = paramArrayOfByte1[(i9 >> 3)] >>> (i9 & 0x7) & 0x1;
              i9 += (i16 >>> 16);
              break;
            default:
              return;
            }
            switch (paramInt9)
            {
            case -3:
              i18 = paramArrayOfByte5[i10];
              i10 += (i16 >> 16);
              if (i18 != 0)
                break;
              break;
            case -4:
              i18 = paramArrayOfByte5[(i10 >> 3)] & 1 << (i10 & 0x7);
              i10 += (i16 >> 16);
              if (i18 != 0)
                break;
              break;
            case -5:
              i18 = 0;
              while (i18 < paramArrayOfByte5.length)
                if (i17 == (paramArrayOfByte5[i18] & 0xFF))
                  break;
              if (i18 >= paramArrayOfByte5.length)
                break;
              break;
            case -6:
              i18 = paramArrayOfByte2[i17];
              i19 = paramArrayOfByte3[i17];
              i20 = paramArrayOfByte4[i17];
              for (i21 = 0; i21 < paramArrayOfByte5.length; i21 += 3)
                if ((i18 == paramArrayOfByte5[i21]) && (i19 == paramArrayOfByte5[(i21 + 1)]) && (i20 == paramArrayOfByte5[(i21 + 2)]))
                  break;
              if (i21 < paramArrayOfByte5.length)
                break label2463;
            }
            i17 = arrayOfByte[i17] & 0xFF;
            switch (i2)
            {
            case 6:
              paramArrayOfByte6[i8] = ((byte)i17);
              break;
            case 7:
              if ((i8 & 0x1) != 0)
                paramArrayOfByte6[(i8 >> 1)] = ((byte)(paramArrayOfByte6[(i8 >> 1)] & 0xF0 | i17));
              else
                paramArrayOfByte6[(i8 >> 1)] = ((byte)(paramArrayOfByte6[(i8 >> 1)] & 0xF | i17 << 4));
              break;
            case 8:
              i18 = 6 - (i8 & 0x3) * 2;
              paramArrayOfByte6[(i8 >> 2)] = ((byte)(paramArrayOfByte6[(i8 >> 2)] & (3 << i18 ^ 0xFFFFFFFF) | i17 << i18));
              break;
            case 9:
              i18 = 7 - (i8 & 0x7);
              paramArrayOfByte6[(i8 >> 3)] = ((byte)(paramArrayOfByte6[(i8 >> 3)] & (1 << i18 ^ 0xFFFFFFFF) | i17 << i18));
              break;
            case 10:
              i18 = i8 & 0x7;
              paramArrayOfByte6[(i8 >> 3)] = ((byte)(paramArrayOfByte6[(i8 >> 3)] & (1 << i18 ^ 0xFFFFFFFF) | i17 << i18));
            }
            i15--;
            i8 += i4;
          }
          i13--;
          i9 = i1 += (i14 >>> 16) * paramInt3;
          i14 = (i14 & 0xFFFF) + m;
          i8 = i3 += i5;
        }
      }
      return;
    }
    int i13 = paramInt9;
    int i14 = 0;
    int i15 = 0;
    int i16 = 0;
    int i17 = -1;
    int i18 = -1;
    int i19 = -1;
    int[] arrayOfInt1;
    int[] arrayOfInt2;
    int[] arrayOfInt3;
    if (i7 != 0)
    {
      arrayOfInt1 = new int[paramInt18 + 2];
      arrayOfInt2 = new int[paramInt18 + 2];
      arrayOfInt3 = new int[paramInt18 + 2];
    }
    else
    {
      arrayOfInt1 = (int[])null;
      arrayOfInt2 = (int[])null;
      arrayOfInt3 = (int[])null;
    }
    int i23 = paramInt19;
    int i24 = m;
    label4083: 
    while (i23 > 0)
    {
      int i25 = 0;
      int i26 = 0;
      int i27 = 0;
      int i28 = paramInt18;
      for (int i29 = j; i28 > 0; i29 = (i29 & 0xFFFF) + j)
      {
        switch (n)
        {
        case 6:
          i14 = paramArrayOfByte1[i9] & 0xFF;
          i9 += (i29 >>> 16);
          break;
        case 7:
          if ((i9 & 0x1) != 0)
            i14 = paramArrayOfByte1[(i9 >> 1)] & 0xF;
          else
            i14 = paramArrayOfByte1[(i9 >> 1)] >>> 4 & 0xF;
          i9 += (i29 >>> 16);
          break;
        case 8:
          i14 = paramArrayOfByte1[(i9 >> 2)] >>> 6 - (i9 & 0x3) * 2 & 0x3;
          i9 += (i29 >>> 16);
          break;
        case 9:
          i14 = paramArrayOfByte1[(i9 >> 3)] >>> 7 - (i9 & 0x7) & 0x1;
          i9 += (i29 >>> 16);
          break;
        case 10:
          i14 = paramArrayOfByte1[(i9 >> 3)] >>> (i9 & 0x7) & 0x1;
          i9 += (i29 >>> 16);
        }
        int i30 = paramArrayOfByte2[i14] & 0xFF;
        int i31 = paramArrayOfByte3[i14] & 0xFF;
        int i32 = paramArrayOfByte4[i14] & 0xFF;
        int i33;
        switch (paramInt9)
        {
        case -1:
          i13 = ((paramArrayOfByte5[i10] & 0xFF) << 16) / 255;
          i10 += (i29 >> 16);
          break;
        case -3:
          i13 = paramArrayOfByte5[i10] != 0 ? 65536 : 0;
          i10 += (i29 >> 16);
          break;
        case -4:
          i13 = paramArrayOfByte5[(i10 >> 3)] << (i10 & 0x7) + 9 & 0x10000;
          i10 += (i29 >> 16);
          break;
        case -5:
          i33 = 0;
          while (i33 < paramArrayOfByte5.length)
            if (i14 == (paramArrayOfByte5[i33] & 0xFF))
              break;
          if (i33 >= paramArrayOfByte5.length)
            break;
          break;
        case -6:
          for (i33 = 0; i33 < paramArrayOfByte5.length; i33 += 3)
            if ((i30 == (paramArrayOfByte5[i33] & 0xFF)) && (i31 == (paramArrayOfByte5[(i33 + 1)] & 0xFF)) && (i32 == (paramArrayOfByte5[(i33 + 2)] & 0xFF)))
              break;
          if (i33 < paramArrayOfByte5.length)
            break label4083;
        case -2:
        }
        int i34;
        int i35;
        if (i13 != 65536)
        {
          if (i13 != 0)
          {
            switch (i2)
            {
            case 6:
              i15 = paramArrayOfByte6[i8] & 0xFF;
              break;
            case 7:
              if ((i8 & 0x1) != 0)
                i15 = paramArrayOfByte6[(i8 >> 1)] & 0xF;
              else
                i15 = paramArrayOfByte6[(i8 >> 1)] >>> 4 & 0xF;
              break;
            case 8:
              i15 = paramArrayOfByte6[(i8 >> 2)] >>> 6 - (i8 & 0x3) * 2 & 0x3;
              break;
            case 9:
              i15 = paramArrayOfByte6[(i8 >> 3)] >>> 7 - (i8 & 0x7) & 0x1;
              break;
            case 10:
              i15 = paramArrayOfByte6[(i8 >> 3)] >>> (i8 & 0x7) & 0x1;
            }
            i33 = paramArrayOfByte7[i15] & 0xFF;
            i34 = paramArrayOfByte8[i15] & 0xFF;
            i35 = paramArrayOfByte9[i15] & 0xFF;
            i30 = i33 + ((i30 - i33) * i13 >> 16);
            i31 = i34 + ((i31 - i34) * i13 >> 16);
            i32 = i35 + ((i32 - i35) * i13 >> 16);
          }
        }
        else
        {
          if (i7 != 0)
          {
            i30 += (arrayOfInt1[i28] >> 4);
            if (i30 < 0)
              i30 = 0;
            else if (i30 > 255)
              i30 = 255;
            i31 += (arrayOfInt2[i28] >> 4);
            if (i31 < 0)
              i31 = 0;
            else if (i31 > 255)
              i31 = 255;
            i32 += (arrayOfInt3[i28] >> 4);
            if (i32 < 0)
              i32 = 0;
            else if (i32 > 255)
              i32 = 255;
            arrayOfInt1[i28] = i25;
            arrayOfInt2[i28] = i26;
            arrayOfInt3[i28] = i27;
          }
          if ((i30 != i17) || (i31 != i18) || (i32 != i19))
          {
            i33 = 0;
            int i38 = 2147483647;
            while (i33 < i11)
            {
              i34 = (paramArrayOfByte7[i33] & 0xFF) - i30;
              i35 = (paramArrayOfByte8[i33] & 0xFF) - i31;
              int i36 = (paramArrayOfByte9[i33] & 0xFF) - i32;
              int i37 = i34 * i34 + i35 * i35 + i36 * i36;
              if (i37 < i38)
              {
                i16 = i33;
                if (i37 == 0)
                  break;
                i38 = i37;
              }
              i33++;
            }
            i17 = i30;
            i18 = i31;
            i19 = i32;
          }
          if (i7 != 0)
          {
            i33 = i28 - 1;
            i34 = i28 + 1;
            tmp3686_3685 = ((i25 = i30 - (paramArrayOfByte7[i16] & 0xFF)) + i25 + i25);
            i35 = tmp3686_3685;
            arrayOfInt1[i34] += tmp3686_3685;
            tmp3705_3704 = (i35 + (i25 + i25));
            i35 = tmp3705_3704;
            arrayOfInt1[i28] += tmp3705_3704;
            arrayOfInt1[i33] += i35 + i25 + i25;
            tmp3753_3752 = ((i26 = i31 - (paramArrayOfByte8[i16] & 0xFF)) + i26 + i26);
            i35 = tmp3753_3752;
            arrayOfInt2[i34] += tmp3753_3752;
            tmp3772_3771 = (i35 + (i26 + i26));
            i35 = tmp3772_3771;
            arrayOfInt2[i28] += tmp3772_3771;
            arrayOfInt2[i33] += i35 + i26 + i26;
            tmp3820_3819 = ((i27 = i32 - (paramArrayOfByte9[i16] & 0xFF)) + i27 + i27);
            i35 = tmp3820_3819;
            arrayOfInt3[i34] += tmp3820_3819;
            tmp3839_3838 = (i35 + (i27 + i27));
            i35 = tmp3839_3838;
            arrayOfInt3[i28] += tmp3839_3838;
            arrayOfInt3[i33] += i35 + i27 + i27;
          }
          switch (i2)
          {
          case 6:
            paramArrayOfByte6[i8] = ((byte)i16);
            break;
          case 7:
            if ((i8 & 0x1) != 0)
              paramArrayOfByte6[(i8 >> 1)] = ((byte)(paramArrayOfByte6[(i8 >> 1)] & 0xF0 | i16));
            else
              paramArrayOfByte6[(i8 >> 1)] = ((byte)(paramArrayOfByte6[(i8 >> 1)] & 0xF | i16 << 4));
            break;
          case 8:
            i33 = 6 - (i8 & 0x3) * 2;
            paramArrayOfByte6[(i8 >> 2)] = ((byte)(paramArrayOfByte6[(i8 >> 2)] & (3 << i33 ^ 0xFFFFFFFF) | i16 << i33));
            break;
          case 9:
            i33 = 7 - (i8 & 0x7);
            paramArrayOfByte6[(i8 >> 3)] = ((byte)(paramArrayOfByte6[(i8 >> 3)] & (1 << i33 ^ 0xFFFFFFFF) | i16 << i33));
            break;
          case 10:
            i33 = i8 & 0x7;
            paramArrayOfByte6[(i8 >> 3)] = ((byte)(paramArrayOfByte6[(i8 >> 3)] & (1 << i33 ^ 0xFFFFFFFF) | i16 << i33));
          }
        }
        i28--;
        i8 += i4;
      }
      i23--;
      i9 = i1 += (i24 >>> 16) * paramInt3;
      i10 = i6 += (i24 >>> 16) * paramInt10;
      i24 = (i24 & 0xFFFF) + m;
      i8 = i3 += i5;
    }
  }

  static void blit(int paramInt1, byte[] paramArrayOfByte1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, byte[] paramArrayOfByte4, int paramInt9, byte[] paramArrayOfByte5, int paramInt10, int paramInt11, int paramInt12, byte[] paramArrayOfByte6, int paramInt13, int paramInt14, int paramInt15, int paramInt16, int paramInt17, int paramInt18, int paramInt19, int paramInt20, int paramInt21, int paramInt22, boolean paramBoolean1, boolean paramBoolean2)
  {
    if ((paramInt18 <= 0) || (paramInt19 <= 0) || (paramInt9 == 0))
      return;
    int n;
    int i2;
    if ((paramInt5 == 0) && (paramInt6 == 0) && (paramInt16 == 0) && (paramInt17 == 0) && (paramInt18 == paramInt7) && (paramInt19 == paramInt8))
    {
      if ((paramInt13 == 24) && (paramInt2 == 8) && ((paramInt1 & 0x2) == 0) && (paramInt20 == 16711680) && (paramInt21 == 65280) && (paramInt22 == 255))
      {
        i = 0;
        j = 0;
        k = 0;
        m = paramInt3 - paramInt7;
        n = paramInt14 - paramInt18 * 3;
        while (i < paramInt19)
        {
          for (i1 = 0; i1 < paramInt18; i1++)
          {
            i2 = paramArrayOfByte1[(j++)] & 0xFF;
            paramArrayOfByte6[(k++)] = paramArrayOfByte2[i2];
            paramArrayOfByte6[(k++)] = paramArrayOfByte3[i2];
            paramArrayOfByte6[(k++)] = paramArrayOfByte4[i2];
          }
          i++;
          j += m;
          k += n;
        }
        return;
      }
      if ((paramInt13 == 32) && (paramInt15 == 1) && (paramInt2 == 8) && ((paramInt1 & 0x2) == 0) && (paramInt20 == 16711680) && (paramInt21 == 65280) && (paramInt22 == 255))
      {
        i = 0;
        j = 0;
        k = 0;
        m = paramInt3 - paramInt7;
        n = paramInt14 - paramInt18 * 4;
        while (i < paramInt19)
        {
          for (i1 = 0; i1 < paramInt18; i1++)
          {
            i2 = paramArrayOfByte1[(j++)] & 0xFF;
            k++;
            paramArrayOfByte6[(k++)] = paramArrayOfByte2[i2];
            paramArrayOfByte6[(k++)] = paramArrayOfByte3[i2];
            paramArrayOfByte6[(k++)] = paramArrayOfByte4[i2];
          }
          i++;
          j += m;
          k += n;
        }
        return;
      }
    }
    int i = paramInt18 - 1;
    int j = i != 0 ? (int)(((paramInt7 << 16) - 1L) / i) : 0;
    int k = paramInt19 - 1;
    int m = k != 0 ? (int)(((paramInt8 << 16) - 1L) / k) : 0;
    switch (paramInt2)
    {
    case 8:
      n = 6;
      break;
    case 4:
      paramInt3 <<= 1;
      n = 7;
      break;
    case 2:
      paramInt3 <<= 2;
      n = 8;
      break;
    case 1:
      paramInt3 <<= 3;
      n = paramInt4 == 1 ? 9 : 10;
      break;
    case 3:
    case 5:
    case 6:
    case 7:
    default:
      return;
    }
    int i1 = paramInt6 * paramInt3 + paramInt5;
    int i3;
    switch (paramInt13)
    {
    case 8:
      i2 = 1;
      i3 = 0;
      break;
    case 16:
      i2 = 2;
      i3 = paramInt15 == 1 ? 1 : 2;
      break;
    case 24:
      i2 = 3;
      i3 = 3;
      break;
    case 32:
      i2 = 4;
      i3 = paramInt15 == 1 ? 4 : 5;
      break;
    default:
      return;
    }
    int i4 = (paramBoolean2 ? paramInt17 + k : paramInt17) * paramInt14 + (paramBoolean1 ? paramInt16 + i : paramInt16) * i2;
    int i5 = paramBoolean1 ? -i2 : i2;
    int i6 = paramBoolean2 ? -paramInt14 : paramInt14;
    int i7;
    if ((paramInt1 & 0x2) != 0)
    {
      switch (paramInt9)
      {
      case -3:
      case -1:
        if (paramArrayOfByte5 == null)
          paramInt9 = 65536;
        i7 = paramInt12 * paramInt10 + paramInt11;
        break;
      case -4:
        if (paramArrayOfByte5 == null)
          paramInt9 = 65536;
        paramInt10 <<= 3;
        i7 = paramInt12 * paramInt10 + paramInt11;
        break;
      case -6:
      case -5:
        if (paramArrayOfByte5 == null)
          paramInt9 = 65536;
        i7 = 0;
        break;
      default:
        paramInt9 = (paramInt9 << 16) / 255;
      case -2:
        i7 = 0;
        break;
      }
    }
    else
    {
      paramInt9 = 65536;
      i7 = 0;
    }
    int i8 = getChannelShift(paramInt20);
    int i9 = getChannelWidth(paramInt20, i8);
    byte[] arrayOfByte1 = ANY_TO_EIGHT[i9];
    int i10 = 8 - i9;
    int i11 = getChannelShift(paramInt21);
    int i12 = getChannelWidth(paramInt21, i11);
    byte[] arrayOfByte2 = ANY_TO_EIGHT[i12];
    int i13 = 8 - i12;
    int i14 = getChannelShift(paramInt22);
    int i15 = getChannelWidth(paramInt22, i14);
    byte[] arrayOfByte3 = ANY_TO_EIGHT[i15];
    int i16 = 8 - i15;
    int i17 = getChannelShift(0);
    int i18 = getChannelWidth(0, i17);
    byte[] arrayOfByte4 = ANY_TO_EIGHT[i18];
    int i19 = 8 - i18;
    int i20 = i4;
    int i21 = i1;
    int i22 = i7;
    int i23 = paramInt9;
    int i24 = 0;
    int i25 = 0;
    int i26 = 0;
    int i27 = 0;
    int i28 = 0;
    int i29 = 0;
    int i30 = 0;
    int i31 = 0;
    int i32 = 0;
    int i33 = paramInt19;
    int i34 = m;
    label2553: 
    while (i33 > 0)
    {
      int i35 = paramInt18;
      for (int i36 = j; i35 > 0; i36 = (i36 & 0xFFFF) + j)
      {
        switch (n)
        {
        case 6:
          i28 = paramArrayOfByte1[i21] & 0xFF;
          i21 += (i36 >>> 16);
          break;
        case 7:
          if ((i21 & 0x1) != 0)
            i28 = paramArrayOfByte1[(i21 >> 1)] & 0xF;
          else
            i28 = paramArrayOfByte1[(i21 >> 1)] >>> 4 & 0xF;
          i21 += (i36 >>> 16);
          break;
        case 8:
          i28 = paramArrayOfByte1[(i21 >> 2)] >>> 6 - (i21 & 0x3) * 2 & 0x3;
          i21 += (i36 >>> 16);
          break;
        case 9:
          i28 = paramArrayOfByte1[(i21 >> 3)] >>> 7 - (i21 & 0x7) & 0x1;
          i21 += (i36 >>> 16);
          break;
        case 10:
          i28 = paramArrayOfByte1[(i21 >> 3)] >>> (i21 & 0x7) & 0x1;
          i21 += (i36 >>> 16);
        }
        i24 = paramArrayOfByte2[i28] & 0xFF;
        i25 = paramArrayOfByte3[i28] & 0xFF;
        i26 = paramArrayOfByte4[i28] & 0xFF;
        int i37;
        switch (paramInt9)
        {
        case -1:
          i23 = ((paramArrayOfByte5[i22] & 0xFF) << 16) / 255;
          i22 += (i36 >> 16);
          break;
        case -3:
          i23 = paramArrayOfByte5[i22] != 0 ? 65536 : 0;
          i22 += (i36 >> 16);
          break;
        case -4:
          i23 = paramArrayOfByte5[(i22 >> 3)] << (i22 & 0x7) + 9 & 0x10000;
          i22 += (i36 >> 16);
          break;
        case -5:
          i37 = 0;
          while (i37 < paramArrayOfByte5.length)
            if (i28 == (paramArrayOfByte5[i37] & 0xFF))
              break;
          if (i37 >= paramArrayOfByte5.length)
            break;
          break;
        case -6:
          for (i37 = 0; i37 < paramArrayOfByte5.length; i37 += 3)
            if ((i24 == (paramArrayOfByte5[i37] & 0xFF)) && (i25 == (paramArrayOfByte5[(i37 + 1)] & 0xFF)) && (i26 == (paramArrayOfByte5[(i37 + 2)] & 0xFF)))
              break;
          if (i37 < paramArrayOfByte5.length)
            break label2553;
        case -2:
        }
        if (i23 != 65536)
        {
          if (i23 != 0)
          {
            switch (i3)
            {
            case 0:
              i37 = paramArrayOfByte6[i20] & 0xFF;
              i29 = arrayOfByte1[((i37 & paramInt20) >>> i8)] & 0xFF;
              i30 = arrayOfByte2[((i37 & paramInt21) >>> i11)] & 0xFF;
              i31 = arrayOfByte3[((i37 & paramInt22) >>> i14)] & 0xFF;
              i32 = arrayOfByte4[(0 >>> i17)] & 0xFF;
              break;
            case 1:
              i37 = (paramArrayOfByte6[i20] & 0xFF) << 8 | paramArrayOfByte6[(i20 + 1)] & 0xFF;
              i29 = arrayOfByte1[((i37 & paramInt20) >>> i8)] & 0xFF;
              i30 = arrayOfByte2[((i37 & paramInt21) >>> i11)] & 0xFF;
              i31 = arrayOfByte3[((i37 & paramInt22) >>> i14)] & 0xFF;
              i32 = arrayOfByte4[(0 >>> i17)] & 0xFF;
              break;
            case 2:
              i37 = (paramArrayOfByte6[(i20 + 1)] & 0xFF) << 8 | paramArrayOfByte6[i20] & 0xFF;
              i29 = arrayOfByte1[((i37 & paramInt20) >>> i8)] & 0xFF;
              i30 = arrayOfByte2[((i37 & paramInt21) >>> i11)] & 0xFF;
              i31 = arrayOfByte3[((i37 & paramInt22) >>> i14)] & 0xFF;
              i32 = arrayOfByte4[(0 >>> i17)] & 0xFF;
              break;
            case 3:
              i37 = ((paramArrayOfByte6[i20] & 0xFF) << 8 | paramArrayOfByte6[(i20 + 1)] & 0xFF) << 8 | paramArrayOfByte6[(i20 + 2)] & 0xFF;
              i29 = arrayOfByte1[((i37 & paramInt20) >>> i8)] & 0xFF;
              i30 = arrayOfByte2[((i37 & paramInt21) >>> i11)] & 0xFF;
              i31 = arrayOfByte3[((i37 & paramInt22) >>> i14)] & 0xFF;
              i32 = arrayOfByte4[(0 >>> i17)] & 0xFF;
              break;
            case 4:
              i37 = (((paramArrayOfByte6[i20] & 0xFF) << 8 | paramArrayOfByte6[(i20 + 1)] & 0xFF) << 8 | paramArrayOfByte6[(i20 + 2)] & 0xFF) << 8 | paramArrayOfByte6[(i20 + 3)] & 0xFF;
              i29 = arrayOfByte1[((i37 & paramInt20) >>> i8)] & 0xFF;
              i30 = arrayOfByte2[((i37 & paramInt21) >>> i11)] & 0xFF;
              i31 = arrayOfByte3[((i37 & paramInt22) >>> i14)] & 0xFF;
              i32 = arrayOfByte4[(0 >>> i17)] & 0xFF;
              break;
            case 5:
              i37 = (((paramArrayOfByte6[(i20 + 3)] & 0xFF) << 8 | paramArrayOfByte6[(i20 + 2)] & 0xFF) << 8 | paramArrayOfByte6[(i20 + 1)] & 0xFF) << 8 | paramArrayOfByte6[i20] & 0xFF;
              i29 = arrayOfByte1[((i37 & paramInt20) >>> i8)] & 0xFF;
              i30 = arrayOfByte2[((i37 & paramInt21) >>> i11)] & 0xFF;
              i31 = arrayOfByte3[((i37 & paramInt22) >>> i14)] & 0xFF;
              i32 = arrayOfByte4[(0 >>> i17)] & 0xFF;
            }
            i27 = i32 + ((i27 - i32) * i23 >> 16);
            i24 = i29 + ((i24 - i29) * i23 >> 16);
            i25 = i30 + ((i25 - i30) * i23 >> 16);
            i26 = i31 + ((i26 - i31) * i23 >> 16);
          }
        }
        else
        {
          i37 = i24 >>> i10 << i8 | i25 >>> i13 << i11 | i26 >>> i16 << i14 | i27 >>> i19 << i17;
          switch (i3)
          {
          case 0:
            paramArrayOfByte6[i20] = ((byte)i37);
            break;
          case 1:
            paramArrayOfByte6[i20] = ((byte)(i37 >>> 8));
            paramArrayOfByte6[(i20 + 1)] = ((byte)(i37 & 0xFF));
            break;
          case 2:
            paramArrayOfByte6[i20] = ((byte)(i37 & 0xFF));
            paramArrayOfByte6[(i20 + 1)] = ((byte)(i37 >>> 8));
            break;
          case 3:
            paramArrayOfByte6[i20] = ((byte)(i37 >>> 16));
            paramArrayOfByte6[(i20 + 1)] = ((byte)(i37 >>> 8));
            paramArrayOfByte6[(i20 + 2)] = ((byte)(i37 & 0xFF));
            break;
          case 4:
            paramArrayOfByte6[i20] = ((byte)(i37 >>> 24));
            paramArrayOfByte6[(i20 + 1)] = ((byte)(i37 >>> 16));
            paramArrayOfByte6[(i20 + 2)] = ((byte)(i37 >>> 8));
            paramArrayOfByte6[(i20 + 3)] = ((byte)(i37 & 0xFF));
            break;
          case 5:
            paramArrayOfByte6[i20] = ((byte)(i37 & 0xFF));
            paramArrayOfByte6[(i20 + 1)] = ((byte)(i37 >>> 8));
            paramArrayOfByte6[(i20 + 2)] = ((byte)(i37 >>> 16));
            paramArrayOfByte6[(i20 + 3)] = ((byte)(i37 >>> 24));
          }
        }
        i35--;
        i20 += i5;
      }
      i33--;
      i21 = i1 += (i34 >>> 16) * paramInt3;
      i22 = i7 += (i34 >>> 16) * paramInt10;
      i34 = (i34 & 0xFFFF) + m;
      i20 = i4 += i6;
    }
  }

  static void blit(int paramInt1, byte[] paramArrayOfByte1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11, int paramInt12, byte[] paramArrayOfByte2, int paramInt13, int paramInt14, int paramInt15, byte[] paramArrayOfByte3, int paramInt16, int paramInt17, int paramInt18, int paramInt19, int paramInt20, int paramInt21, int paramInt22, byte[] paramArrayOfByte4, byte[] paramArrayOfByte5, byte[] paramArrayOfByte6, boolean paramBoolean1, boolean paramBoolean2)
  {
    if ((paramInt21 <= 0) || (paramInt22 <= 0) || (paramInt12 == 0))
      return;
    int i = paramInt21 - 1;
    int j = i != 0 ? (int)(((paramInt7 << 16) - 1L) / i) : 0;
    int k = paramInt22 - 1;
    int m = k != 0 ? (int)(((paramInt8 << 16) - 1L) / k) : 0;
    int n;
    int i1;
    switch (paramInt2)
    {
    case 8:
      n = 1;
      i1 = 0;
      break;
    case 16:
      n = 2;
      i1 = paramInt4 == 1 ? 1 : 2;
      break;
    case 24:
      n = 3;
      i1 = 3;
      break;
    case 32:
      n = 4;
      i1 = paramInt4 == 1 ? 4 : 5;
      break;
    default:
      return;
    }
    int i2 = paramInt6 * paramInt3 + paramInt5 * n;
    int i3;
    switch (paramInt16)
    {
    case 8:
      i3 = 6;
      break;
    case 4:
      paramInt17 <<= 1;
      i3 = 7;
      break;
    case 2:
      paramInt17 <<= 2;
      i3 = 8;
      break;
    case 1:
      paramInt17 <<= 3;
      i3 = paramInt18 == 1 ? 9 : 10;
      break;
    case 3:
    case 5:
    case 6:
    case 7:
    default:
      return;
    }
    int i4 = (paramBoolean2 ? paramInt20 + k : paramInt20) * paramInt17 + (paramBoolean1 ? paramInt19 + i : paramInt19);
    int i5 = paramBoolean1 ? -1 : 1;
    int i6 = paramBoolean2 ? -paramInt17 : paramInt17;
    int i7;
    if ((paramInt1 & 0x2) != 0)
    {
      switch (paramInt12)
      {
      case -3:
      case -1:
        if (paramArrayOfByte2 == null)
          paramInt12 = 65536;
        i7 = paramInt15 * paramInt13 + paramInt14;
        break;
      case -4:
        if (paramArrayOfByte2 == null)
          paramInt12 = 65536;
        paramInt13 <<= 3;
        i7 = paramInt15 * paramInt13 + paramInt14;
        break;
      case -5:
        return;
      case -6:
        if (paramArrayOfByte2 == null)
          paramInt12 = 65536;
        i7 = 0;
        break;
      default:
        paramInt12 = (paramInt12 << 16) / 255;
      case -2:
        i7 = 0;
        break;
      }
    }
    else
    {
      paramInt12 = 65536;
      i7 = 0;
    }
    int i8 = (paramInt1 & 0x4) != 0 ? 1 : 0;
    int i9 = getChannelShift(paramInt9);
    byte[] arrayOfByte1 = ANY_TO_EIGHT[getChannelWidth(paramInt9, i9)];
    int i10 = getChannelShift(paramInt10);
    byte[] arrayOfByte2 = ANY_TO_EIGHT[getChannelWidth(paramInt10, i10)];
    int i11 = getChannelShift(paramInt11);
    byte[] arrayOfByte3 = ANY_TO_EIGHT[getChannelWidth(paramInt11, i11)];
    int i12 = getChannelShift(0);
    byte[] arrayOfByte4 = ANY_TO_EIGHT[getChannelWidth(0, i12)];
    int i13 = i4;
    int i14 = i2;
    int i15 = i7;
    int i16 = paramInt12;
    int i17 = 0;
    int i18 = 0;
    int i19 = 0;
    int i20 = 0;
    int i21 = 0;
    int i22 = 0;
    int i23 = -1;
    int i24 = -1;
    int i25 = -1;
    int i26 = 1 << paramInt16;
    if ((paramArrayOfByte4 != null) && (paramArrayOfByte4.length < i26))
      i26 = paramArrayOfByte4.length;
    int[] arrayOfInt1;
    int[] arrayOfInt2;
    int[] arrayOfInt3;
    if (i8 != 0)
    {
      arrayOfInt1 = new int[paramInt21 + 2];
      arrayOfInt2 = new int[paramInt21 + 2];
      arrayOfInt3 = new int[paramInt21 + 2];
    }
    else
    {
      arrayOfInt1 = (int[])null;
      arrayOfInt2 = (int[])null;
      arrayOfInt3 = (int[])null;
    }
    int i27 = paramInt22;
    int i28 = m;
    while (i27 > 0)
    {
      int i29 = 0;
      int i30 = 0;
      int i31 = 0;
      int i32 = paramInt21;
      for (int i33 = j; i32 > 0; i33 = (i33 & 0xFFFF) + j)
      {
        int i34;
        switch (i1)
        {
        case 0:
          i34 = paramArrayOfByte1[i14] & 0xFF;
          i14 += (i33 >>> 16);
          i17 = arrayOfByte1[((i34 & paramInt9) >>> i9)] & 0xFF;
          i18 = arrayOfByte2[((i34 & paramInt10) >>> i10)] & 0xFF;
          i19 = arrayOfByte3[((i34 & paramInt11) >>> i11)] & 0xFF;
          i20 = arrayOfByte4[(0 >>> i12)] & 0xFF;
          break;
        case 1:
          i34 = (paramArrayOfByte1[i14] & 0xFF) << 8 | paramArrayOfByte1[(i14 + 1)] & 0xFF;
          i14 += (i33 >>> 16) * 2;
          i17 = arrayOfByte1[((i34 & paramInt9) >>> i9)] & 0xFF;
          i18 = arrayOfByte2[((i34 & paramInt10) >>> i10)] & 0xFF;
          i19 = arrayOfByte3[((i34 & paramInt11) >>> i11)] & 0xFF;
          i20 = arrayOfByte4[(0 >>> i12)] & 0xFF;
          break;
        case 2:
          i34 = (paramArrayOfByte1[(i14 + 1)] & 0xFF) << 8 | paramArrayOfByte1[i14] & 0xFF;
          i14 += (i33 >>> 16) * 2;
          i17 = arrayOfByte1[((i34 & paramInt9) >>> i9)] & 0xFF;
          i18 = arrayOfByte2[((i34 & paramInt10) >>> i10)] & 0xFF;
          i19 = arrayOfByte3[((i34 & paramInt11) >>> i11)] & 0xFF;
          i20 = arrayOfByte4[(0 >>> i12)] & 0xFF;
          break;
        case 3:
          i34 = ((paramArrayOfByte1[i14] & 0xFF) << 8 | paramArrayOfByte1[(i14 + 1)] & 0xFF) << 8 | paramArrayOfByte1[(i14 + 2)] & 0xFF;
          i14 += (i33 >>> 16) * 3;
          i17 = arrayOfByte1[((i34 & paramInt9) >>> i9)] & 0xFF;
          i18 = arrayOfByte2[((i34 & paramInt10) >>> i10)] & 0xFF;
          i19 = arrayOfByte3[((i34 & paramInt11) >>> i11)] & 0xFF;
          i20 = arrayOfByte4[(0 >>> i12)] & 0xFF;
          break;
        case 4:
          i34 = (((paramArrayOfByte1[i14] & 0xFF) << 8 | paramArrayOfByte1[(i14 + 1)] & 0xFF) << 8 | paramArrayOfByte1[(i14 + 2)] & 0xFF) << 8 | paramArrayOfByte1[(i14 + 3)] & 0xFF;
          i14 += (i33 >>> 16) * 4;
          i17 = arrayOfByte1[((i34 & paramInt9) >>> i9)] & 0xFF;
          i18 = arrayOfByte2[((i34 & paramInt10) >>> i10)] & 0xFF;
          i19 = arrayOfByte3[((i34 & paramInt11) >>> i11)] & 0xFF;
          i20 = arrayOfByte4[(0 >>> i12)] & 0xFF;
          break;
        case 5:
          i34 = (((paramArrayOfByte1[(i14 + 3)] & 0xFF) << 8 | paramArrayOfByte1[(i14 + 2)] & 0xFF) << 8 | paramArrayOfByte1[(i14 + 1)] & 0xFF) << 8 | paramArrayOfByte1[i14] & 0xFF;
          i14 += (i33 >>> 16) * 4;
          i17 = arrayOfByte1[((i34 & paramInt9) >>> i9)] & 0xFF;
          i18 = arrayOfByte2[((i34 & paramInt10) >>> i10)] & 0xFF;
          i19 = arrayOfByte3[((i34 & paramInt11) >>> i11)] & 0xFF;
          i20 = arrayOfByte4[(0 >>> i12)] & 0xFF;
        }
        switch (paramInt12)
        {
        case -1:
          i16 = ((paramArrayOfByte2[i15] & 0xFF) << 16) / 255;
          i15 += (i33 >> 16);
          break;
        case -2:
          i16 = (i20 << 16) / 255;
          break;
        case -3:
          i16 = paramArrayOfByte2[i15] != 0 ? 65536 : 0;
          i15 += (i33 >> 16);
          break;
        case -4:
          i16 = paramArrayOfByte2[(i15 >> 3)] << (i15 & 0x7) + 9 & 0x10000;
          i15 += (i33 >> 16);
          break;
        case -6:
          i16 = 65536;
          for (i34 = 0; i34 < paramArrayOfByte2.length; i34 += 3)
            if ((i17 == paramArrayOfByte2[i34]) && (i18 == paramArrayOfByte2[(i34 + 1)]) && (i19 == paramArrayOfByte2[(i34 + 2)]))
            {
              i16 = 0;
              break;
            }
        case -5:
        }
        int i35;
        int i36;
        if (i16 != 65536)
        {
          if (i16 != 0)
          {
            switch (i3)
            {
            case 6:
              i21 = paramArrayOfByte3[i13] & 0xFF;
              break;
            case 7:
              if ((i13 & 0x1) != 0)
                i21 = paramArrayOfByte3[(i13 >> 1)] & 0xF;
              else
                i21 = paramArrayOfByte3[(i13 >> 1)] >>> 4 & 0xF;
              break;
            case 8:
              i21 = paramArrayOfByte3[(i13 >> 2)] >>> 6 - (i13 & 0x3) * 2 & 0x3;
              break;
            case 9:
              i21 = paramArrayOfByte3[(i13 >> 3)] >>> 7 - (i13 & 0x7) & 0x1;
              break;
            case 10:
              i21 = paramArrayOfByte3[(i13 >> 3)] >>> (i13 & 0x7) & 0x1;
            }
            i34 = paramArrayOfByte4[i21] & 0xFF;
            i35 = paramArrayOfByte5[i21] & 0xFF;
            i36 = paramArrayOfByte6[i21] & 0xFF;
            i17 = i34 + ((i17 - i34) * i16 >> 16);
            i18 = i35 + ((i18 - i35) * i16 >> 16);
            i19 = i36 + ((i19 - i36) * i16 >> 16);
          }
        }
        else
        {
          if (i8 != 0)
          {
            i17 += (arrayOfInt1[i32] >> 4);
            if (i17 < 0)
              i17 = 0;
            else if (i17 > 255)
              i17 = 255;
            i18 += (arrayOfInt2[i32] >> 4);
            if (i18 < 0)
              i18 = 0;
            else if (i18 > 255)
              i18 = 255;
            i19 += (arrayOfInt3[i32] >> 4);
            if (i19 < 0)
              i19 = 0;
            else if (i19 > 255)
              i19 = 255;
            arrayOfInt1[i32] = i29;
            arrayOfInt2[i32] = i30;
            arrayOfInt3[i32] = i31;
          }
          if ((i17 != i23) || (i18 != i24) || (i19 != i25))
          {
            i34 = 0;
            int i39 = 2147483647;
            while (i34 < i26)
            {
              i35 = (paramArrayOfByte4[i34] & 0xFF) - i17;
              i36 = (paramArrayOfByte5[i34] & 0xFF) - i18;
              int i37 = (paramArrayOfByte6[i34] & 0xFF) - i19;
              int i38 = i35 * i35 + i36 * i36 + i37 * i37;
              if (i38 < i39)
              {
                i22 = i34;
                if (i38 == 0)
                  break;
                i39 = i38;
              }
              i34++;
            }
            i23 = i17;
            i24 = i18;
            i25 = i19;
          }
          if (i8 != 0)
          {
            i34 = i32 - 1;
            i35 = i32 + 1;
            int tmp2214_2213 = ((i29 = i17 - (paramArrayOfByte4[i22] & 0xFF)) + i29 + i29);
            i36 = tmp2214_2213;
            arrayOfInt1[i35] += tmp2214_2213;
            int tmp2233_2232 = (i36 + (i29 + i29));
            i36 = tmp2233_2232;
            arrayOfInt1[i32] += tmp2233_2232;
            arrayOfInt1[i34] += i36 + i29 + i29;
            int tmp2281_2280 = ((i30 = i18 - (paramArrayOfByte5[i22] & 0xFF)) + i30 + i30);
            i36 = tmp2281_2280;
            arrayOfInt2[i35] += tmp2281_2280;
            int tmp2300_2299 = (i36 + (i30 + i30));
            i36 = tmp2300_2299;
            arrayOfInt2[i32] += tmp2300_2299;
            arrayOfInt2[i34] += i36 + i30 + i30;
            int tmp2348_2347 = ((i31 = i19 - (paramArrayOfByte6[i22] & 0xFF)) + i31 + i31);
            i36 = tmp2348_2347;
            arrayOfInt3[i35] += tmp2348_2347;
            int tmp2367_2366 = (i36 + (i31 + i31));
            i36 = tmp2367_2366;
            arrayOfInt3[i32] += tmp2367_2366;
            arrayOfInt3[i34] += i36 + i31 + i31;
          }
          switch (i3)
          {
          case 6:
            paramArrayOfByte3[i13] = ((byte)i22);
            break;
          case 7:
            if ((i13 & 0x1) != 0)
              paramArrayOfByte3[(i13 >> 1)] = ((byte)(paramArrayOfByte3[(i13 >> 1)] & 0xF0 | i22));
            else
              paramArrayOfByte3[(i13 >> 1)] = ((byte)(paramArrayOfByte3[(i13 >> 1)] & 0xF | i22 << 4));
            break;
          case 8:
            i34 = 6 - (i13 & 0x3) * 2;
            paramArrayOfByte3[(i13 >> 2)] = ((byte)(paramArrayOfByte3[(i13 >> 2)] & (3 << i34 ^ 0xFFFFFFFF) | i22 << i34));
            break;
          case 9:
            i34 = 7 - (i13 & 0x7);
            paramArrayOfByte3[(i13 >> 3)] = ((byte)(paramArrayOfByte3[(i13 >> 3)] & (1 << i34 ^ 0xFFFFFFFF) | i22 << i34));
            break;
          case 10:
            i34 = i13 & 0x7;
            paramArrayOfByte3[(i13 >> 3)] = ((byte)(paramArrayOfByte3[(i13 >> 3)] & (1 << i34 ^ 0xFFFFFFFF) | i22 << i34));
          }
        }
        i32--;
        i13 += i5;
      }
      i27--;
      i14 = i2 += (i28 >>> 16) * paramInt3;
      i15 = i7 += (i28 >>> 16) * paramInt13;
      i28 = (i28 & 0xFFFF) + m;
      i13 = i4 += i6;
    }
  }

  static int getChannelShift(int paramInt)
  {
    if (paramInt == 0)
      return 0;
    for (int i = 0; ((paramInt & 0x1) == 0) && (i < 32); i++)
      paramInt >>>= 1;
    return i;
  }

  static int getChannelWidth(int paramInt1, int paramInt2)
  {
    if (paramInt1 == 0)
      return 0;
    paramInt1 >>>= paramInt2;
    for (int i = paramInt2; ((paramInt1 & 0x1) != 0) && (i < 32); i++)
      paramInt1 >>>= 1;
    return i - paramInt2;
  }

  static byte getChannelField(int paramInt1, int paramInt2)
  {
    int i = getChannelShift(paramInt2);
    return ANY_TO_EIGHT[getChannelWidth(paramInt2, i)][((paramInt1 & paramInt2) >>> i)];
  }

  static ImageData createGradientBand(int paramInt1, int paramInt2, boolean paramBoolean, RGB paramRGB1, RGB paramRGB2, int paramInt3, int paramInt4, int paramInt5)
  {
    PaletteData localPaletteData;
    int k;
    int i;
    int j;
    int m;
    int n;
    byte[] arrayOfByte;
    if ((paramInt3 != 0) && (paramInt4 != 0) && (paramInt5 != 0))
    {
      localPaletteData = new PaletteData(65280, 16711680, -16777216);
      k = 32;
      if ((paramInt3 >= 8) && (paramInt4 >= 8) && (paramInt5 >= 8))
      {
        if (paramBoolean)
        {
          i = 1;
          j = paramInt2;
          m = j > 1 ? j - 1 : 1;
        }
        else
        {
          i = paramInt1;
          j = 1;
          m = i > 1 ? i - 1 : 1;
        }
        n = i * 4;
        arrayOfByte = new byte[j * n];
        buildPreciseGradientChannel(paramRGB1.blue, paramRGB2.blue, m, i, j, paramBoolean, arrayOfByte, 0, n);
        buildPreciseGradientChannel(paramRGB1.green, paramRGB2.green, m, i, j, paramBoolean, arrayOfByte, 1, n);
        buildPreciseGradientChannel(paramRGB1.red, paramRGB2.red, m, i, j, paramBoolean, arrayOfByte, 2, n);
      }
      else
      {
        if (paramBoolean)
        {
          i = paramInt1 < 8 ? paramInt1 : 8;
          j = paramInt2;
          m = j > 1 ? j - 1 : 1;
        }
        else
        {
          i = paramInt1;
          j = paramInt2 < 8 ? paramInt2 : 8;
          m = i > 1 ? i - 1 : 1;
        }
        n = i * 4;
        arrayOfByte = new byte[j * n];
        buildDitheredGradientChannel(paramRGB1.blue, paramRGB2.blue, m, i, j, paramBoolean, arrayOfByte, 0, n, paramInt5);
        buildDitheredGradientChannel(paramRGB1.green, paramRGB2.green, m, i, j, paramBoolean, arrayOfByte, 1, n, paramInt4);
        buildDitheredGradientChannel(paramRGB1.red, paramRGB2.red, m, i, j, paramBoolean, arrayOfByte, 2, n, paramInt3);
      }
    }
    else
    {
      localPaletteData = new PaletteData(new RGB[] { paramRGB1, paramRGB2 });
      k = 8;
      if (paramBoolean)
      {
        i = paramInt1 < 8 ? paramInt1 : 8;
        j = paramInt2;
        m = j > 1 ? 17039360 / (j - 1) + 1 : 1;
      }
      else
      {
        i = paramInt1;
        j = paramInt2 < 8 ? paramInt2 : 8;
        m = i > 1 ? 17039360 / (i - 1) + 1 : 1;
      }
      n = i + 3 & 0xFFFFFFFC;
      arrayOfByte = new byte[j * n];
      int i1;
      int i2;
      int i3;
      int i4;
      if (paramBoolean)
      {
        i1 = 0;
        i2 = 0;
        i3 = 0;
        while (i1 < j)
        {
          for (i4 = 0; i4 < i; i4++)
            arrayOfByte[(i3 + i4)] = (i2 + DITHER_MATRIX[(i1 & 0x7)][i4] < 16777216 ? 0 : 1);
          i1++;
          i2 += m;
          i3 += n;
        }
      }
      else
      {
        i1 = 0;
        i2 = 0;
        while (i1 < i)
        {
          i3 = 0;
          i4 = i1;
          while (i3 < j)
          {
            arrayOfByte[i4] = (i2 + DITHER_MATRIX[i3][(i1 & 0x7)] < 16777216 ? 0 : 1);
            i3++;
            i4 += n;
          }
          i1++;
          i2 += m;
        }
      }
    }
    return new ImageData(i, j, k, localPaletteData, 4, arrayOfByte);
  }

  static final void buildPreciseGradientChannel(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, boolean paramBoolean, byte[] paramArrayOfByte, int paramInt6, int paramInt7)
  {
    int i = paramInt1 << 16;
    int j = ((paramInt2 << 16) - i) / paramInt3 + 1;
    int k;
    if (paramBoolean)
    {
      k = 0;
      while (k < paramInt5)
      {
        paramArrayOfByte[paramInt6] = ((byte)(i >>> 16));
        i += j;
        k++;
        paramInt6 += paramInt7;
      }
    }
    else
    {
      k = 0;
      while (k < paramInt4)
      {
        paramArrayOfByte[paramInt6] = ((byte)(i >>> 16));
        i += j;
        k++;
        paramInt6 += 4;
      }
    }
  }

  static final void buildDitheredGradientChannel(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, boolean paramBoolean, byte[] paramArrayOfByte, int paramInt6, int paramInt7, int paramInt8)
  {
    int i = 65280 >>> paramInt8;
    int j = paramInt1 << 16;
    int k = ((paramInt2 << 16) - j) / paramInt3 + 1;
    int m;
    int n;
    int i1;
    int i2;
    int i3;
    if (paramBoolean)
    {
      m = 0;
      while (m < paramInt5)
      {
        n = 0;
        for (i1 = paramInt6; n < paramInt4; i1 += 4)
        {
          i2 = DITHER_MATRIX[(m & 0x7)][n] >>> paramInt8;
          i3 = j + i2;
          if (i3 > 16777215)
            paramArrayOfByte[i1] = -1;
          else
            paramArrayOfByte[i1] = ((byte)(i3 >>> 16 & i));
          n++;
        }
        j += k;
        m++;
        paramInt6 += paramInt7;
      }
    }
    else
    {
      m = 0;
      while (m < paramInt4)
      {
        n = 0;
        i1 = paramInt6;
        while (n < paramInt5)
        {
          i2 = DITHER_MATRIX[n][(m & 0x7)] >>> paramInt8;
          i3 = j + i2;
          if (i3 > 16777215)
            paramArrayOfByte[i1] = -1;
          else
            paramArrayOfByte[i1] = ((byte)(i3 >>> 16 & i));
          n++;
          i1 += paramInt7;
        }
        j += k;
        m++;
        paramInt6 += 4;
      }
    }
  }

  static void fillGradientRectangle(GC paramGC, Device paramDevice, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean, RGB paramRGB1, RGB paramRGB2, int paramInt5, int paramInt6, int paramInt7)
  {
    ImageData localImageData = createGradientBand(paramInt3, paramInt4, paramBoolean, paramRGB1, paramRGB2, paramInt5, paramInt6, paramInt7);
    Image localImage = new Image(paramDevice, localImageData);
    if ((localImageData.width == 1) || (localImageData.height == 1))
    {
      paramGC.drawImage(localImage, 0, 0, localImageData.width, localImageData.height, paramInt1, paramInt2, paramInt3, paramInt4);
    }
    else
    {
      int i;
      int j;
      if (paramBoolean)
      {
        i = 0;
        while (i < paramInt3)
        {
          j = paramInt3 - i;
          if (j > localImageData.width)
            j = localImageData.width;
          paramGC.drawImage(localImage, 0, 0, j, localImageData.height, i + paramInt1, paramInt2, j, localImageData.height);
          i += localImageData.width;
        }
      }
      else
      {
        i = 0;
        while (i < paramInt4)
        {
          j = paramInt4 - i;
          if (j > localImageData.height)
            j = localImageData.height;
          paramGC.drawImage(localImage, 0, 0, localImageData.width, j, paramInt1, i + paramInt2, localImageData.width, j);
          i += localImageData.height;
        }
      }
    }
    localImage.dispose();
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.graphics.ImageData
 * JD-Core Version:    0.6.2
 */