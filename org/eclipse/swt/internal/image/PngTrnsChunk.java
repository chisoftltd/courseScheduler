package org.eclipse.swt.internal.image;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;

public class PngTrnsChunk extends PngChunk
{
  static final int TRANSPARENCY_TYPE_PIXEL = 0;
  static final int TRANSPARENCY_TYPE_ALPHAS = 1;
  static final int RGB_DATA_LENGTH = 6;

  PngTrnsChunk(RGB paramRGB)
  {
    super(6);
    setType(TYPE_tRNS);
    setInt16(8, paramRGB.red);
    setInt16(10, paramRGB.green);
    setInt16(12, paramRGB.blue);
    setCRC(computeCRC());
  }

  PngTrnsChunk(byte[] paramArrayOfByte)
  {
    super(paramArrayOfByte);
  }

  int getChunkType()
  {
    return 5;
  }

  void validateLength(PngIhdrChunk paramPngIhdrChunk, PngPlteChunk paramPngPlteChunk)
  {
    int i;
    switch (paramPngIhdrChunk.getColorType())
    {
    case 2:
      i = getLength() == 6 ? 1 : 0;
      break;
    case 3:
      i = getLength() <= paramPngPlteChunk.getLength() ? 1 : 0;
      break;
    case 0:
      i = getLength() == 2 ? 1 : 0;
      break;
    case 1:
    case 4:
    case 5:
    case 6:
    default:
      i = 0;
    }
    if (i == 0)
      SWT.error(40);
  }

  void validate(PngFileReadState paramPngFileReadState, PngIhdrChunk paramPngIhdrChunk, PngPlteChunk paramPngPlteChunk)
  {
    if ((!paramPngFileReadState.readIHDR) || ((paramPngIhdrChunk.getMustHavePalette()) && (!paramPngFileReadState.readPLTE)) || (paramPngFileReadState.readIDAT) || (paramPngFileReadState.readIEND))
      SWT.error(40);
    else
      paramPngFileReadState.readTRNS = true;
    validateLength(paramPngIhdrChunk, paramPngPlteChunk);
    super.validate(paramPngFileReadState, paramPngIhdrChunk);
  }

  int getTransparencyType(PngIhdrChunk paramPngIhdrChunk)
  {
    if (paramPngIhdrChunk.getColorType() == 3)
      return 1;
    return 0;
  }

  int getSwtTransparentPixel(PngIhdrChunk paramPngIhdrChunk)
  {
    switch (paramPngIhdrChunk.getColorType())
    {
    case 0:
      int i = ((this.reference[8] & 0xFF) << 8) + (this.reference[9] & 0xFF);
      if (paramPngIhdrChunk.getBitDepth() > 8)
        return PNGFileFormat.compress16BitDepthTo8BitDepth(i);
      return i & 0xFF;
    case 2:
      int j = (this.reference[8] & 0xFF) << 8 | this.reference[9] & 0xFF;
      int k = (this.reference[10] & 0xFF) << 8 | this.reference[11] & 0xFF;
      int m = (this.reference[12] & 0xFF) << 8 | this.reference[13] & 0xFF;
      if (paramPngIhdrChunk.getBitDepth() > 8)
      {
        j = PNGFileFormat.compress16BitDepthTo8BitDepth(j);
        k = PNGFileFormat.compress16BitDepthTo8BitDepth(k);
        m = PNGFileFormat.compress16BitDepthTo8BitDepth(m);
      }
      return j << 16 | k << 8 | m;
    case 1:
    }
    SWT.error(40);
    return -1;
  }

  byte[] getAlphaValues(PngIhdrChunk paramPngIhdrChunk, PngPlteChunk paramPngPlteChunk)
  {
    if (paramPngIhdrChunk.getColorType() != 3)
      SWT.error(40);
    byte[] arrayOfByte = new byte[paramPngPlteChunk.getPaletteSize()];
    int i = getLength();
    int j = 0;
    for (j = 0; j < i; j++)
      arrayOfByte[j] = this.reference[(8 + j)];
    for (int k = j; k < arrayOfByte.length; k++)
      arrayOfByte[k] = -1;
    return arrayOfByte;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.image.PngTrnsChunk
 * JD-Core Version:    0.6.2
 */