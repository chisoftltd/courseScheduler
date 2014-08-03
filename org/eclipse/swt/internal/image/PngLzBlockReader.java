package org.eclipse.swt.internal.image;

import java.io.IOException;

public class PngLzBlockReader
{
  boolean isLastBlock;
  byte compressionType;
  int uncompressedBytesRemaining;
  PngDecodingDataStream stream;
  PngHuffmanTables huffmanTables;
  byte[] window;
  int windowIndex;
  int copyIndex;
  int copyBytesRemaining;
  static final int UNCOMPRESSED = 0;
  static final int COMPRESSED_FIXED = 1;
  static final int COMPRESSED_DYNAMIC = 2;
  static final int END_OF_COMPRESSED_BLOCK = 256;
  static final int FIRST_LENGTH_CODE = 257;
  static final int LAST_LENGTH_CODE = 285;
  static final int FIRST_DISTANCE_CODE = 1;
  static final int LAST_DISTANCE_CODE = 29;
  static final int FIRST_CODE_LENGTH_CODE = 4;
  static final int LAST_CODE_LENGTH_CODE = 19;
  static final int[] lengthBases = { 3, 4, 5, 6, 7, 8, 9, 10, 11, 13, 15, 17, 19, 23, 27, 31, 35, 43, 51, 59, 67, 83, 99, 115, 131, 163, 195, 227, 258 };
  static final int[] extraLengthBits = { 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5 };
  static final int[] distanceBases = { 1, 2, 3, 4, 5, 7, 9, 13, 17, 25, 33, 49, 65, 97, 129, 193, 257, 385, 513, 769, 1025, 1537, 2049, 3073, 4097, 6145, 8193, 12289, 16385, 24577 };
  static final int[] extraDistanceBits = { 0, 0, 0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10, 11, 11, 12, 12, 13, 13 };

  PngLzBlockReader(PngDecodingDataStream paramPngDecodingDataStream)
  {
    this.stream = paramPngDecodingDataStream;
    this.isLastBlock = false;
  }

  void setWindowSize(int paramInt)
  {
    this.window = new byte[paramInt];
  }

  void readNextBlockHeader()
    throws IOException
  {
    this.isLastBlock = (this.stream.getNextIdatBit() != 0);
    this.compressionType = ((byte)this.stream.getNextIdatBits(2));
    if (this.compressionType > 2)
      this.stream.error();
    if (this.compressionType == 0)
    {
      int i = this.stream.getNextIdatByte();
      int j = this.stream.getNextIdatByte();
      int k = this.stream.getNextIdatByte();
      int m = this.stream.getNextIdatByte();
      if ((i != (k ^ 0xFFFFFFFF)) || (j != (m ^ 0xFFFFFFFF)))
        this.stream.error();
      this.uncompressedBytesRemaining = (i & 0xFF | (j & 0xFF) << 8);
    }
    else if (this.compressionType == 2)
    {
      this.huffmanTables = PngHuffmanTables.getDynamicTables(this.stream);
    }
    else
    {
      this.huffmanTables = PngHuffmanTables.getFixedTables();
    }
  }

  byte getNextByte()
    throws IOException
  {
    if (this.compressionType == 0)
    {
      if (this.uncompressedBytesRemaining == 0)
      {
        readNextBlockHeader();
        return getNextByte();
      }
      this.uncompressedBytesRemaining -= 1;
      return this.stream.getNextIdatByte();
    }
    return getNextCompressedByte();
  }

  private void assertBlockAtEnd()
    throws IOException
  {
    if (this.compressionType == 0)
    {
      if (this.uncompressedBytesRemaining > 0)
        this.stream.error();
    }
    else if ((this.copyBytesRemaining > 0) || (this.huffmanTables.getNextLiteralValue(this.stream) != 256))
      this.stream.error();
  }

  void assertCompressedDataAtEnd()
    throws IOException
  {
    assertBlockAtEnd();
    while (!this.isLastBlock)
    {
      readNextBlockHeader();
      assertBlockAtEnd();
    }
  }

  private byte getNextCompressedByte()
    throws IOException
  {
    if (this.copyBytesRemaining > 0)
    {
      byte b = this.window[this.copyIndex];
      this.window[this.windowIndex] = b;
      this.copyBytesRemaining -= 1;
      this.copyIndex += 1;
      this.windowIndex += 1;
      if (this.copyIndex == this.window.length)
        this.copyIndex = 0;
      if (this.windowIndex == this.window.length)
        this.windowIndex = 0;
      return b;
    }
    int i = this.huffmanTables.getNextLiteralValue(this.stream);
    if (i < 256)
    {
      this.window[this.windowIndex] = ((byte)i);
      this.windowIndex += 1;
      if (this.windowIndex >= this.window.length)
        this.windowIndex = 0;
      return (byte)i;
    }
    if (i == 256)
    {
      readNextBlockHeader();
      return getNextByte();
    }
    if (i <= 285)
    {
      int j = extraLengthBits[(i - 257)];
      int k = lengthBases[(i - 257)];
      if (j > 0)
        k += this.stream.getNextIdatBits(j);
      i = this.huffmanTables.getNextDistanceValue(this.stream);
      if (i > 29)
        this.stream.error();
      j = extraDistanceBits[i];
      int m = distanceBases[i];
      if (j > 0)
        m += this.stream.getNextIdatBits(j);
      this.copyIndex = (this.windowIndex - m);
      if (this.copyIndex < 0)
        this.copyIndex += this.window.length;
      this.copyBytesRemaining = k;
      return getNextCompressedByte();
    }
    this.stream.error();
    return 0;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.image.PngLzBlockReader
 * JD-Core Version:    0.6.2
 */