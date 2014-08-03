package org.eclipse.swt.internal.image;

import java.io.IOException;
import java.io.InputStream;

public class PngInputStream extends InputStream
{
  PngChunkReader reader;
  PngChunk chunk;
  int offset;
  int length;
  static final int DATA_OFFSET = 8;

  public PngInputStream(PngIdatChunk paramPngIdatChunk, PngChunkReader paramPngChunkReader)
  {
    this.chunk = paramPngIdatChunk;
    this.reader = paramPngChunkReader;
    this.length = paramPngIdatChunk.getLength();
    this.offset = 0;
  }

  private boolean checkChunk()
    throws IOException
  {
    while (this.offset == this.length)
    {
      this.chunk = this.reader.readNextChunk();
      if (this.chunk == null)
        throw new IOException();
      if (this.chunk.getChunkType() == 3)
        return false;
      if (this.chunk.getChunkType() != 2)
        throw new IOException();
      this.length = this.chunk.getLength();
      this.offset = 0;
    }
    return true;
  }

  public void close()
    throws IOException
  {
    this.chunk = null;
  }

  public int read()
    throws IOException
  {
    if (this.chunk == null)
      throw new IOException();
    if ((this.offset == this.length) && (!checkChunk()))
      return -1;
    int i = this.chunk.reference[(8 + this.offset)] & 0xFF;
    this.offset += 1;
    return i;
  }

  public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    if (this.chunk == null)
      throw new IOException();
    if ((this.offset == this.length) && (!checkChunk()))
      return -1;
    paramInt2 = Math.min(paramInt2, this.length - this.offset);
    System.arraycopy(this.chunk.reference, 8 + this.offset, paramArrayOfByte, paramInt1, paramInt2);
    this.offset += paramInt2;
    return paramInt2;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.image.PngInputStream
 * JD-Core Version:    0.6.2
 */