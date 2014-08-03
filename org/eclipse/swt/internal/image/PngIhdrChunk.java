package org.eclipse.swt.internal.image;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;

class PngIhdrChunk extends PngChunk
{
  static final int IHDR_DATA_LENGTH = 13;
  static final int WIDTH_DATA_OFFSET = 8;
  static final int HEIGHT_DATA_OFFSET = 12;
  static final int BIT_DEPTH_OFFSET = 16;
  static final int COLOR_TYPE_OFFSET = 17;
  static final int COMPRESSION_METHOD_OFFSET = 18;
  static final int FILTER_METHOD_OFFSET = 19;
  static final int INTERLACE_METHOD_OFFSET = 20;
  static final byte COLOR_TYPE_GRAYSCALE = 0;
  static final byte COLOR_TYPE_RGB = 2;
  static final byte COLOR_TYPE_PALETTE = 3;
  static final byte COLOR_TYPE_GRAYSCALE_WITH_ALPHA = 4;
  static final byte COLOR_TYPE_RGB_WITH_ALPHA = 6;
  static final int INTERLACE_METHOD_NONE = 0;
  static final int INTERLACE_METHOD_ADAM7 = 1;
  static final int FILTER_NONE = 0;
  static final int FILTER_SUB = 1;
  static final int FILTER_UP = 2;
  static final int FILTER_AVERAGE = 3;
  static final int FILTER_PAETH = 4;
  static final byte[] ValidBitDepths = { 1, 2, 4, 8, 16 };
  static final byte[] ValidColorTypes = { 0, 2, 3, 4, 6 };
  int width;
  int height;
  byte bitDepth;
  byte colorType;
  byte compressionMethod;
  byte filterMethod;
  byte interlaceMethod;

  PngIhdrChunk(int paramInt1, int paramInt2, byte paramByte1, byte paramByte2, byte paramByte3, byte paramByte4, byte paramByte5)
  {
    super(13);
    setType(TYPE_IHDR);
    setWidth(paramInt1);
    setHeight(paramInt2);
    setBitDepth(paramByte1);
    setColorType(paramByte2);
    setCompressionMethod(paramByte3);
    setFilterMethod(paramByte4);
    setInterlaceMethod(paramByte5);
    setCRC(computeCRC());
  }

  PngIhdrChunk(byte[] paramArrayOfByte)
  {
    super(paramArrayOfByte);
    if (paramArrayOfByte.length <= 13)
      SWT.error(40);
    this.width = getInt32(8);
    this.height = getInt32(12);
    this.bitDepth = paramArrayOfByte[16];
    this.colorType = paramArrayOfByte[17];
    this.compressionMethod = paramArrayOfByte[18];
    this.filterMethod = paramArrayOfByte[19];
    this.interlaceMethod = paramArrayOfByte[20];
  }

  int getChunkType()
  {
    return 0;
  }

  int getWidth()
  {
    return this.width;
  }

  void setWidth(int paramInt)
  {
    setInt32(8, paramInt);
    this.width = paramInt;
  }

  int getHeight()
  {
    return this.height;
  }

  void setHeight(int paramInt)
  {
    setInt32(12, paramInt);
    this.height = paramInt;
  }

  byte getBitDepth()
  {
    return this.bitDepth;
  }

  void setBitDepth(byte paramByte)
  {
    this.reference[16] = paramByte;
    this.bitDepth = paramByte;
  }

  byte getColorType()
  {
    return this.colorType;
  }

  void setColorType(byte paramByte)
  {
    this.reference[17] = paramByte;
    this.colorType = paramByte;
  }

  byte getCompressionMethod()
  {
    return this.compressionMethod;
  }

  void setCompressionMethod(byte paramByte)
  {
    this.reference[18] = paramByte;
    this.compressionMethod = paramByte;
  }

  byte getFilterMethod()
  {
    return this.filterMethod;
  }

  void setFilterMethod(byte paramByte)
  {
    this.reference[19] = paramByte;
    this.filterMethod = paramByte;
  }

  byte getInterlaceMethod()
  {
    return this.interlaceMethod;
  }

  void setInterlaceMethod(byte paramByte)
  {
    this.reference[20] = paramByte;
    this.interlaceMethod = paramByte;
  }

  void validate(PngFileReadState paramPngFileReadState, PngIhdrChunk paramPngIhdrChunk)
  {
    if ((paramPngFileReadState.readIHDR) || (paramPngFileReadState.readPLTE) || (paramPngFileReadState.readIDAT) || (paramPngFileReadState.readIEND))
      SWT.error(40);
    else
      paramPngFileReadState.readIHDR = true;
    super.validate(paramPngFileReadState, paramPngIhdrChunk);
    if (this.length != 13)
      SWT.error(40);
    if (this.compressionMethod != 0)
      SWT.error(40);
    if ((this.interlaceMethod != 0) && (this.interlaceMethod != 1))
      SWT.error(40);
    int i = 0;
    for (int j = 0; j < ValidColorTypes.length; j++)
      if (ValidColorTypes[j] == this.colorType)
      {
        i = 1;
        break;
      }
    if (i == 0)
      SWT.error(40);
    j = 0;
    for (int k = 0; k < ValidBitDepths.length; k++)
      if (ValidBitDepths[k] == this.bitDepth)
      {
        j = 1;
        break;
      }
    if (j == 0)
      SWT.error(40);
    if (((this.colorType == 2) || (this.colorType == 6) || (this.colorType == 4)) && (this.bitDepth < 8))
      SWT.error(40);
    if ((this.colorType == 3) && (this.bitDepth > 8))
      SWT.error(40);
  }

  String getColorTypeString()
  {
    switch (this.colorType)
    {
    case 0:
      return "Grayscale";
    case 2:
      return "RGB";
    case 3:
      return "Palette";
    case 4:
      return "Grayscale with Alpha";
    case 6:
      return "RGB with Alpha";
    case 1:
    case 5:
    }
    return "Unknown - " + this.colorType;
  }

  String getFilterMethodString()
  {
    switch (this.filterMethod)
    {
    case 0:
      return "None";
    case 1:
      return "Sub";
    case 2:
      return "Up";
    case 3:
      return "Average";
    case 4:
      return "Paeth";
    }
    return "Unknown";
  }

  String getInterlaceMethodString()
  {
    switch (this.interlaceMethod)
    {
    case 0:
      return "Not Interlaced";
    case 1:
      return "Interlaced - ADAM7";
    }
    return "Unknown";
  }

  void contributeToString(StringBuffer paramStringBuffer)
  {
    paramStringBuffer.append("\n\tWidth: ");
    paramStringBuffer.append(this.width);
    paramStringBuffer.append("\n\tHeight: ");
    paramStringBuffer.append(this.height);
    paramStringBuffer.append("\n\tBit Depth: ");
    paramStringBuffer.append(this.bitDepth);
    paramStringBuffer.append("\n\tColor Type: ");
    paramStringBuffer.append(getColorTypeString());
    paramStringBuffer.append("\n\tCompression Method: ");
    paramStringBuffer.append(this.compressionMethod);
    paramStringBuffer.append("\n\tFilter Method: ");
    paramStringBuffer.append(getFilterMethodString());
    paramStringBuffer.append("\n\tInterlace Method: ");
    paramStringBuffer.append(getInterlaceMethodString());
  }

  boolean getMustHavePalette()
  {
    return this.colorType == 3;
  }

  boolean getCanHavePalette()
  {
    return (this.colorType != 0) && (this.colorType != 4);
  }

  int getBitsPerPixel()
  {
    switch (this.colorType)
    {
    case 6:
      return 4 * this.bitDepth;
    case 2:
      return 3 * this.bitDepth;
    case 4:
      return 2 * this.bitDepth;
    case 0:
    case 3:
      return this.bitDepth;
    case 1:
    case 5:
    }
    SWT.error(40);
    return 0;
  }

  int getSwtBitsPerPixel()
  {
    switch (this.colorType)
    {
    case 2:
    case 4:
    case 6:
      return 24;
    case 0:
    case 3:
      return Math.min(this.bitDepth, 8);
    case 1:
    case 5:
    }
    SWT.error(40);
    return 0;
  }

  int getFilterByteOffset()
  {
    if (this.bitDepth < 8)
      return 1;
    return getBitsPerPixel() / 8;
  }

  boolean usesDirectColor()
  {
    switch (this.colorType)
    {
    case 0:
    case 2:
    case 4:
    case 6:
      return true;
    case 1:
    case 3:
    case 5:
    }
    return false;
  }

  PaletteData createGrayscalePalette()
  {
    int i = Math.min(this.bitDepth, 8);
    int j = (1 << i) - 1;
    int k = 255 / j;
    int m = 0;
    RGB[] arrayOfRGB = new RGB[j + 1];
    for (int n = 0; n <= j; n++)
    {
      arrayOfRGB[n] = new RGB(m, m, m);
      m += k;
    }
    return new PaletteData(arrayOfRGB);
  }

  PaletteData getPaletteData()
  {
    switch (this.colorType)
    {
    case 0:
      return createGrayscalePalette();
    case 2:
    case 4:
    case 6:
      return new PaletteData(16711680, 65280, 255);
    case 1:
    case 3:
    case 5:
    }
    return null;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.image.PngIhdrChunk
 * JD-Core Version:    0.6.2
 */