package org.eclipse.swt.internal.image;

import java.io.IOException;
import java.io.InputStream;
import org.eclipse.swt.SWT;

public class PngDecodingDataStream extends InputStream
{
  InputStream stream;
  byte currentByte;
  int nextBitIndex;
  PngLzBlockReader lzBlockReader;
  int adlerValue;
  static final int PRIME = 65521;
  static final int MAX_BIT = 7;

  PngDecodingDataStream(InputStream paramInputStream)
    throws IOException
  {
    this.stream = paramInputStream;
    this.nextBitIndex = 8;
    this.adlerValue = 1;
    this.lzBlockReader = new PngLzBlockReader(this);
    readCompressedDataHeader();
    this.lzBlockReader.readNextBlockHeader();
  }

  void assertImageDataAtEnd()
    throws IOException
  {
    this.lzBlockReader.assertCompressedDataAtEnd();
  }

  public void close()
    throws IOException
  {
    assertImageDataAtEnd();
    checkAdler();
  }

  int getNextIdatBits(int paramInt)
    throws IOException
  {
    int i = 0;
    for (int j = 0; j < paramInt; j++)
      i |= getNextIdatBit() << j;
    return i;
  }

  int getNextIdatBit()
    throws IOException
  {
    if (this.nextBitIndex > 7)
    {
      this.currentByte = getNextIdatByte();
      this.nextBitIndex = 0;
    }
    return (this.currentByte & 1 << this.nextBitIndex) >> this.nextBitIndex++;
  }

  byte getNextIdatByte()
    throws IOException
  {
    byte b = (byte)this.stream.read();
    this.nextBitIndex = 8;
    return b;
  }

  void updateAdler(byte paramByte)
  {
    int i = this.adlerValue & 0xFFFF;
    int j = this.adlerValue >> 16 & 0xFFFF;
    int k = paramByte & 0xFF;
    i = (i + k) % 65521;
    j = (i + j) % 65521;
    this.adlerValue = (j << 16 | i);
  }

  public int read()
    throws IOException
  {
    int i = this.lzBlockReader.getNextByte();
    updateAdler(i);
    return i & 0xFF;
  }

  public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    for (int i = 0; i < paramInt2; i++)
    {
      int j = read();
      if (j == -1)
        return i;
      paramArrayOfByte[(paramInt1 + i)] = ((byte)j);
    }
    return paramInt2;
  }

  void error()
  {
    SWT.error(40);
  }

  private void readCompressedDataHeader()
    throws IOException
  {
    int i = getNextIdatByte();
    int j = getNextIdatByte();
    int k = (i & 0xFF) << 8 | j & 0xFF;
    if (k % 31 != 0)
      error();
    int m = i & 0xF;
    if (m != 8)
      error();
    int n = (i & 0xF0) >> 4;
    if (n > 7)
      error();
    int i1 = 1 << n + 8;
    this.lzBlockReader.setWindowSize(i1);
    int i2 = j & 0x20;
    if (i2 != 0)
      error();
  }

  void checkAdler()
    throws IOException
  {
    int i = (getNextIdatByte() & 0xFF) << 24 | (getNextIdatByte() & 0xFF) << 16 | (getNextIdatByte() & 0xFF) << 8 | getNextIdatByte() & 0xFF;
    if (i != this.adlerValue)
      error();
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.image.PngDecodingDataStream
 * JD-Core Version:    0.6.2
 */