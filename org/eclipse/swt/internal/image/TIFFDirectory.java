package org.eclipse.swt.internal.image;

import java.io.IOException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.ImageLoaderEvent;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;

final class TIFFDirectory
{
  TIFFRandomFileAccess file;
  boolean isLittleEndian;
  ImageLoader loader;
  int depth;
  int subfileType;
  int imageWidth;
  int imageLength;
  int[] bitsPerSample;
  int compression;
  int photometricInterpretation;
  int[] stripOffsets;
  int samplesPerPixel;
  int rowsPerStrip;
  int[] stripByteCounts;
  int t4Options;
  int colorMapOffset;
  ImageData image;
  LEDataOutputStream out;
  static final int NO_VALUE = -1;
  static final short TAG_NewSubfileType = 254;
  static final short TAG_SubfileType = 255;
  static final short TAG_ImageWidth = 256;
  static final short TAG_ImageLength = 257;
  static final short TAG_BitsPerSample = 258;
  static final short TAG_Compression = 259;
  static final short TAG_PhotometricInterpretation = 262;
  static final short TAG_FillOrder = 266;
  static final short TAG_ImageDescription = 270;
  static final short TAG_StripOffsets = 273;
  static final short TAG_Orientation = 274;
  static final short TAG_SamplesPerPixel = 277;
  static final short TAG_RowsPerStrip = 278;
  static final short TAG_StripByteCounts = 279;
  static final short TAG_XResolution = 282;
  static final short TAG_YResolution = 283;
  static final short TAG_PlanarConfiguration = 284;
  static final short TAG_T4Options = 292;
  static final short TAG_ResolutionUnit = 296;
  static final short TAG_Software = 305;
  static final short TAG_DateTime = 306;
  static final short TAG_ColorMap = 320;
  static final int TYPE_BYTE = 1;
  static final int TYPE_ASCII = 2;
  static final int TYPE_SHORT = 3;
  static final int TYPE_LONG = 4;
  static final int TYPE_RATIONAL = 5;
  static final int FILETYPE_REDUCEDIMAGE = 1;
  static final int FILETYPE_PAGE = 2;
  static final int FILETYPE_MASK = 4;
  static final int OFILETYPE_IMAGE = 1;
  static final int OFILETYPE_REDUCEDIMAGE = 2;
  static final int OFILETYPE_PAGE = 3;
  static final int COMPRESSION_NONE = 1;
  static final int COMPRESSION_CCITT_3_1 = 2;
  static final int COMPRESSION_PACKBITS = 32773;
  static final int IFD_ENTRY_SIZE = 12;

  public TIFFDirectory(TIFFRandomFileAccess paramTIFFRandomFileAccess, boolean paramBoolean, ImageLoader paramImageLoader)
  {
    this.file = paramTIFFRandomFileAccess;
    this.isLittleEndian = paramBoolean;
    this.loader = paramImageLoader;
  }

  public TIFFDirectory(ImageData paramImageData)
  {
    this.image = paramImageData;
  }

  int decodePackBits(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt)
  {
    int i = paramInt;
    int j = 0;
    while (j < paramArrayOfByte1.length)
    {
      int k = paramArrayOfByte1[j];
      if (k >= 0)
      {
        System.arraycopy(paramArrayOfByte1, ++j, paramArrayOfByte2, i, k + 1);
        j += k + 1;
        i += k + 1;
      }
      else if (k >= -127)
      {
        int m = paramArrayOfByte1[(++j)];
        for (int n = 0; n < -k + 1; n++)
          paramArrayOfByte2[(i++)] = m;
        j++;
      }
      else
      {
        j++;
      }
    }
    return i - paramInt;
  }

  int getEntryValue(int paramInt1, byte[] paramArrayOfByte, int paramInt2)
  {
    return toInt(paramArrayOfByte, paramInt2 + 8, paramInt1);
  }

  void getEntryValue(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int[] paramArrayOfInt)
    throws IOException
  {
    int i = paramInt2 + 8;
    int k = toInt(paramArrayOfByte, i, 4);
    int j;
    switch (paramInt1)
    {
    case 3:
      j = 2;
      break;
    case 4:
      j = 4;
      break;
    case 5:
      j = 8;
      break;
    case 1:
    case 2:
      j = 1;
      break;
    default:
      SWT.error(42);
      return;
    }
    if (paramArrayOfInt.length * j > 4)
    {
      paramArrayOfByte = new byte[paramArrayOfInt.length * j];
      this.file.seek(k);
      this.file.read(paramArrayOfByte);
      i = 0;
    }
    for (int m = 0; m < paramArrayOfInt.length; m++)
      paramArrayOfInt[m] = toInt(paramArrayOfByte, i + m * j, paramInt1);
  }

  void decodePixels(ImageData paramImageData)
    throws IOException
  {
    byte[] arrayOfByte1 = new byte[(this.imageWidth * this.depth + 7) / 8 * this.imageLength];
    paramImageData.data = arrayOfByte1;
    int i = 0;
    int j = this.stripOffsets.length;
    for (int k = 0; k < j; k++)
    {
      byte[] arrayOfByte2 = new byte[this.stripByteCounts[k]];
      this.file.seek(this.stripOffsets[k]);
      this.file.read(arrayOfByte2);
      if (this.compression == 1)
      {
        System.arraycopy(arrayOfByte2, 0, arrayOfByte1, i, arrayOfByte2.length);
        i += arrayOfByte2.length;
      }
      else if (this.compression == 32773)
      {
        i += decodePackBits(arrayOfByte2, arrayOfByte1, i);
      }
      else if ((this.compression == 2) || (this.compression == 3))
      {
        TIFFModifiedHuffmanCodec localTIFFModifiedHuffmanCodec = new TIFFModifiedHuffmanCodec();
        int m = this.rowsPerStrip;
        if (k == j - 1)
        {
          int n = this.imageLength % this.rowsPerStrip;
          if (n != 0)
            m = n;
        }
        i += localTIFFModifiedHuffmanCodec.decode(arrayOfByte2, arrayOfByte1, i, this.imageWidth, m);
      }
      if (this.loader.hasListeners())
        this.loader.notifyListeners(new ImageLoaderEvent(this.loader, paramImageData, k, k == j - 1));
    }
  }

  PaletteData getColorMap()
    throws IOException
  {
    int i = 1 << this.bitsPerSample[0];
    int j = 6 * i;
    byte[] arrayOfByte = new byte[j];
    this.file.seek(this.colorMapOffset);
    this.file.read(arrayOfByte);
    RGB[] arrayOfRGB = new RGB[i];
    int k = this.isLittleEndian ? 1 : 0;
    int m = 2 * i;
    int n = m + 2 * i;
    for (int i1 = 0; i1 < i; i1++)
    {
      int i2 = arrayOfByte[k] & 0xFF;
      int i3 = arrayOfByte[(m + k)] & 0xFF;
      int i4 = arrayOfByte[(n + k)] & 0xFF;
      arrayOfRGB[i1] = new RGB(i2, i3, i4);
      k += 2;
    }
    return new PaletteData(arrayOfRGB);
  }

  PaletteData getGrayPalette()
  {
    int i = 1 << this.bitsPerSample[0];
    RGB[] arrayOfRGB = new RGB[i];
    for (int j = 0; j < i; j++)
    {
      int k = j * 255 / (i - 1);
      if (this.photometricInterpretation == 0)
        k = 255 - k;
      arrayOfRGB[j] = new RGB(k, k, k);
    }
    return new PaletteData(arrayOfRGB);
  }

  PaletteData getRGBPalette(int paramInt1, int paramInt2, int paramInt3)
  {
    int i = 0;
    for (int j = 0; j < paramInt3; j++)
      i |= 1 << j;
    j = 0;
    for (int k = paramInt3; k < paramInt3 + paramInt2; k++)
      j |= 1 << k;
    k = 0;
    for (int m = paramInt3 + paramInt2; m < paramInt3 + paramInt2 + paramInt1; m++)
      k |= 1 << m;
    return new PaletteData(k, j, i);
  }

  int formatStrips(int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3, int paramInt4, int paramInt5, int[][] paramArrayOfInt)
  {
    int i;
    int j;
    if (paramInt1 > paramInt3)
    {
      i = paramArrayOfByte.length / paramInt1;
      j = 1;
    }
    else
    {
      k = (paramArrayOfByte.length + paramInt3 - 1) / paramInt3;
      j = paramInt2 / k;
      i = (paramInt2 + j - 1) / j;
    }
    int k = paramInt1 * j;
    int[] arrayOfInt1 = new int[i];
    int[] arrayOfInt2 = new int[i];
    int m = i == 1 ? 0 : i * 2 * 4;
    int n = paramInt4 + paramInt5 + m;
    int i1 = n;
    for (int i2 = 0; i2 < i; i2++)
    {
      arrayOfInt1[i2] = i1;
      arrayOfInt2[i2] = k;
      i1 += k;
    }
    i2 = paramArrayOfByte.length % k;
    if (i2 != 0)
      arrayOfInt2[(arrayOfInt2.length - 1)] = i2;
    paramArrayOfInt[0] = arrayOfInt1;
    paramArrayOfInt[1] = arrayOfInt2;
    return j;
  }

  int[] formatColorMap(RGB[] paramArrayOfRGB)
  {
    int[] arrayOfInt = new int[paramArrayOfRGB.length * 3];
    int i = paramArrayOfRGB.length;
    int j = paramArrayOfRGB.length * 2;
    for (int k = 0; k < paramArrayOfRGB.length; k++)
    {
      arrayOfInt[k] = (paramArrayOfRGB[k].red << 8 | paramArrayOfRGB[k].red);
      arrayOfInt[(k + i)] = (paramArrayOfRGB[k].green << 8 | paramArrayOfRGB[k].green);
      arrayOfInt[(k + j)] = (paramArrayOfRGB[k].blue << 8 | paramArrayOfRGB[k].blue);
    }
    return arrayOfInt;
  }

  void parseEntries(byte[] paramArrayOfByte)
    throws IOException
  {
    for (int i = 0; i < paramArrayOfByte.length; i += 12)
    {
      int j = toInt(paramArrayOfByte, i, 3);
      int k = toInt(paramArrayOfByte, i + 2, 3);
      int m = toInt(paramArrayOfByte, i + 4, 4);
      switch (j)
      {
      case 254:
        this.subfileType = getEntryValue(k, paramArrayOfByte, i);
        break;
      case 255:
        int n = getEntryValue(k, paramArrayOfByte, i);
        this.subfileType = (n == 3 ? 2 : n == 2 ? 1 : 0);
        break;
      case 256:
        this.imageWidth = getEntryValue(k, paramArrayOfByte, i);
        break;
      case 257:
        this.imageLength = getEntryValue(k, paramArrayOfByte, i);
        break;
      case 258:
        if (k != 3)
          SWT.error(40);
        this.bitsPerSample = new int[m];
        getEntryValue(k, paramArrayOfByte, i, this.bitsPerSample);
        break;
      case 259:
        this.compression = getEntryValue(k, paramArrayOfByte, i);
        break;
      case 266:
        break;
      case 270:
        break;
      case 262:
        this.photometricInterpretation = getEntryValue(k, paramArrayOfByte, i);
        break;
      case 273:
        if ((k != 4) && (k != 3))
          SWT.error(40);
        this.stripOffsets = new int[m];
        getEntryValue(k, paramArrayOfByte, i, this.stripOffsets);
        break;
      case 274:
        break;
      case 277:
        if (k != 3)
          SWT.error(40);
        this.samplesPerPixel = getEntryValue(k, paramArrayOfByte, i);
        if ((this.samplesPerPixel != 1) && (this.samplesPerPixel != 3))
          SWT.error(38);
        break;
      case 278:
        this.rowsPerStrip = getEntryValue(k, paramArrayOfByte, i);
        break;
      case 279:
        this.stripByteCounts = new int[m];
        getEntryValue(k, paramArrayOfByte, i, this.stripByteCounts);
        break;
      case 282:
        break;
      case 283:
        break;
      case 284:
        break;
      case 292:
        if (k != 4)
          SWT.error(40);
        this.t4Options = getEntryValue(k, paramArrayOfByte, i);
        if ((this.t4Options & 0x1) == 1)
          SWT.error(42);
        break;
      case 296:
        break;
      case 305:
        break;
      case 306:
        break;
      case 320:
        if (k != 3)
          SWT.error(40);
        this.colorMapOffset = getEntryValue(4, paramArrayOfByte, i);
      }
    }
  }

  public ImageData read(int[] paramArrayOfInt)
    throws IOException
  {
    this.bitsPerSample = new int[] { 1 };
    this.colorMapOffset = -1;
    this.compression = 1;
    this.imageLength = -1;
    this.imageWidth = -1;
    this.photometricInterpretation = -1;
    this.rowsPerStrip = 2147483647;
    this.samplesPerPixel = 1;
    this.stripByteCounts = null;
    this.stripOffsets = null;
    byte[] arrayOfByte1 = new byte[2];
    this.file.read(arrayOfByte1);
    int i = toInt(arrayOfByte1, 0, 3);
    arrayOfByte1 = new byte[12 * i];
    this.file.read(arrayOfByte1);
    byte[] arrayOfByte2 = new byte[4];
    this.file.read(arrayOfByte2);
    paramArrayOfInt[0] = toInt(arrayOfByte2, 0, 4);
    parseEntries(arrayOfByte1);
    PaletteData localPaletteData = null;
    this.depth = 0;
    switch (this.photometricInterpretation)
    {
    case 0:
    case 1:
      localPaletteData = getGrayPalette();
      this.depth = this.bitsPerSample[0];
      break;
    case 2:
      if (this.colorMapOffset != -1)
        SWT.error(40);
      localPaletteData = getRGBPalette(this.bitsPerSample[0], this.bitsPerSample[1], this.bitsPerSample[2]);
      this.depth = (this.bitsPerSample[0] + this.bitsPerSample[1] + this.bitsPerSample[2]);
      break;
    case 3:
      if (this.colorMapOffset == -1)
        SWT.error(40);
      localPaletteData = getColorMap();
      this.depth = this.bitsPerSample[0];
      break;
    default:
      SWT.error(40);
    }
    ImageData localImageData = ImageData.internal_new(this.imageWidth, this.imageLength, this.depth, localPaletteData, 1, null, 0, null, null, -1, -1, 6, 0, 0, 0, 0);
    decodePixels(localImageData);
    return localImageData;
  }

  int toInt(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    if (paramInt2 == 4)
      return this.isLittleEndian ? paramArrayOfByte[paramInt1] & 0xFF | (paramArrayOfByte[(paramInt1 + 1)] & 0xFF) << 8 | (paramArrayOfByte[(paramInt1 + 2)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt1 + 3)] & 0xFF) << 24 : paramArrayOfByte[(paramInt1 + 3)] & 0xFF | (paramArrayOfByte[(paramInt1 + 2)] & 0xFF) << 8 | (paramArrayOfByte[(paramInt1 + 1)] & 0xFF) << 16 | (paramArrayOfByte[paramInt1] & 0xFF) << 24;
    if (paramInt2 == 3)
      return this.isLittleEndian ? paramArrayOfByte[paramInt1] & 0xFF | (paramArrayOfByte[(paramInt1 + 1)] & 0xFF) << 8 : paramArrayOfByte[(paramInt1 + 1)] & 0xFF | (paramArrayOfByte[paramInt1] & 0xFF) << 8;
    SWT.error(40);
    return -1;
  }

  void write(int paramInt)
    throws IOException
  {
    int i = paramInt == 2 ? 1 : 0;
    int j = paramInt == 3 ? 1 : 0;
    int k = (paramInt != 0) && (paramInt != 1) ? 0 : 1;
    int m = this.image.width;
    int n = this.image.height;
    int i1 = this.image.bytesPerLine;
    int i2 = k != 0 ? 9 : 11;
    int i3 = 2 + 12 * i2 + 4;
    int i4 = 8 + i3;
    int i5 = 16;
    int[] arrayOfInt = (int[])null;
    if (j != 0)
    {
      localObject1 = this.image.palette;
      localObject2 = ((PaletteData)localObject1).getRGBs();
      arrayOfInt = formatColorMap((RGB[])localObject2);
      if (arrayOfInt.length != 3 << this.image.depth)
        SWT.error(42);
      i5 += arrayOfInt.length * 2;
    }
    if (i != 0)
      i5 += 6;
    Object localObject1 = this.image.data;
    Object localObject2 = new int[2][];
    int i6 = formatStrips(i1, n, (byte[])localObject1, 8192, i4, i5, (int[][])localObject2);
    Object localObject3 = localObject2[0];
    Object localObject4 = localObject2[1];
    int i7 = -1;
    if (i != 0)
    {
      i7 = i4;
      i4 += 6;
    }
    int i8 = -1;
    int i9 = -1;
    int i12 = -1;
    int i13 = localObject3.length;
    if (i13 > 1)
    {
      i8 = i4;
      i4 += 4 * i13;
      i9 = i4;
      i4 += 4 * i13;
    }
    int i10 = i4;
    i4 += 8;
    int i11 = i4;
    i4 += 8;
    if (j != 0)
    {
      i12 = i4;
      i4 += arrayOfInt.length * 2;
    }
    writeHeader();
    this.out.writeShort(i2);
    writeEntry((short)256, 4, 1, m);
    writeEntry((short)257, 4, 1, n);
    if (j != 0)
      writeEntry((short)258, 3, 1, this.image.depth);
    if (i != 0)
      writeEntry((short)258, 3, 3, i7);
    writeEntry((short)259, 3, 1, 1);
    writeEntry((short)262, 3, 1, paramInt);
    writeEntry((short)273, 4, i13, i13 > 1 ? i8 : localObject3[0]);
    if (i != 0)
      writeEntry((short)277, 3, 1, 3);
    writeEntry((short)278, 4, 1, i6);
    writeEntry((short)279, 4, i13, i13 > 1 ? i9 : localObject4[0]);
    writeEntry((short)282, 5, 1, i10);
    writeEntry((short)283, 5, 1, i11);
    if (j != 0)
      writeEntry((short)320, 3, arrayOfInt.length, i12);
    this.out.writeInt(0);
    if (i != 0)
      for (i14 = 0; i14 < 3; i14++)
        this.out.writeShort(8);
    if (i13 > 1)
    {
      for (i14 = 0; i14 < i13; i14++)
        this.out.writeInt(localObject3[i14]);
      for (i14 = 0; i14 < i13; i14++)
        this.out.writeInt(localObject4[i14]);
    }
    for (int i14 = 0; i14 < 2; i14++)
    {
      this.out.writeInt(300);
      this.out.writeInt(1);
    }
    if (j != 0)
      for (i14 = 0; i14 < arrayOfInt.length; i14++)
        this.out.writeShort(arrayOfInt[i14]);
    this.out.write((byte[])localObject1);
  }

  void writeEntry(short paramShort, int paramInt1, int paramInt2, int paramInt3)
    throws IOException
  {
    this.out.writeShort(paramShort);
    this.out.writeShort(paramInt1);
    this.out.writeInt(paramInt2);
    this.out.writeInt(paramInt3);
  }

  void writeHeader()
    throws IOException
  {
    this.out.write(73);
    this.out.write(73);
    this.out.writeShort(42);
    this.out.writeInt(8);
  }

  void writeToStream(LEDataOutputStream paramLEDataOutputStream)
    throws IOException
  {
    this.out = paramLEDataOutputStream;
    int i = -1;
    if (this.image.scanlinePad != 1)
      SWT.error(42);
    switch (this.image.depth)
    {
    case 1:
      PaletteData localPaletteData = this.image.palette;
      RGB[] arrayOfRGB = localPaletteData.colors;
      if ((localPaletteData.isDirect) || (arrayOfRGB == null) || (arrayOfRGB.length != 2))
        SWT.error(42);
      RGB localRGB1 = arrayOfRGB[0];
      RGB localRGB2 = arrayOfRGB[1];
      if ((localRGB1.red != localRGB1.green) || (localRGB1.green != localRGB1.blue) || (localRGB2.red != localRGB2.green) || (localRGB2.green != localRGB2.blue) || (((localRGB1.red != 0) || (localRGB2.red != 255)) && ((localRGB1.red != 255) || (localRGB2.red != 0))))
        SWT.error(42);
      i = this.image.palette.colors[0].red == 255 ? 0 : 1;
      break;
    case 4:
    case 8:
      i = 3;
      break;
    case 24:
      i = 2;
      break;
    default:
      SWT.error(42);
    }
    write(i);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.image.TIFFDirectory
 * JD-Core Version:    0.6.2
 */