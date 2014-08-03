package org.eclipse.swt.internal.image;

import org.eclipse.swt.SWT;

class PngIendChunk extends PngChunk
{
  PngIendChunk()
  {
    super(0);
    setType(TYPE_IEND);
    setCRC(computeCRC());
  }

  PngIendChunk(byte[] paramArrayOfByte)
  {
    super(paramArrayOfByte);
  }

  int getChunkType()
  {
    return 3;
  }

  void validate(PngFileReadState paramPngFileReadState, PngIhdrChunk paramPngIhdrChunk)
  {
    if ((!paramPngFileReadState.readIHDR) || ((paramPngIhdrChunk.getMustHavePalette()) && (!paramPngFileReadState.readPLTE)) || (!paramPngFileReadState.readIDAT) || (paramPngFileReadState.readIEND))
      SWT.error(40);
    else
      paramPngFileReadState.readIEND = true;
    super.validate(paramPngFileReadState, paramPngIhdrChunk);
    if (getLength() > 0)
      SWT.error(40);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.image.PngIendChunk
 * JD-Core Version:    0.6.2
 */