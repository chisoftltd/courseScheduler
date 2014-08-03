package org.eclipse.swt.internal.image;

import org.eclipse.swt.SWT;

class PngIdatChunk extends PngChunk
{
  static final int HEADER_BYTES_LENGTH = 2;
  static final int ADLER_FIELD_LENGTH = 4;
  static final int HEADER_BYTE1_DATA_OFFSET = 8;
  static final int HEADER_BYTE2_DATA_OFFSET = 9;
  static final int ADLER_DATA_OFFSET = 10;

  PngIdatChunk(byte paramByte1, byte paramByte2, byte[] paramArrayOfByte, int paramInt)
  {
    super(paramArrayOfByte.length + 2 + 4);
    setType(TYPE_IDAT);
    this.reference[8] = paramByte1;
    this.reference[9] = paramByte2;
    System.arraycopy(paramArrayOfByte, 0, this.reference, 8, paramArrayOfByte.length);
    setInt32(10, paramInt);
    setCRC(computeCRC());
  }

  PngIdatChunk(byte[] paramArrayOfByte)
  {
    super(paramArrayOfByte);
  }

  int getChunkType()
  {
    return 2;
  }

  void validate(PngFileReadState paramPngFileReadState, PngIhdrChunk paramPngIhdrChunk)
  {
    if ((!paramPngFileReadState.readIHDR) || ((paramPngIhdrChunk.getMustHavePalette()) && (!paramPngFileReadState.readPLTE)) || (paramPngFileReadState.readIEND))
      SWT.error(40);
    else
      paramPngFileReadState.readIDAT = true;
    super.validate(paramPngFileReadState, paramPngIhdrChunk);
  }

  byte getDataByteAtOffset(int paramInt)
  {
    return this.reference[(8 + paramInt)];
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.image.PngIdatChunk
 * JD-Core Version:    0.6.2
 */