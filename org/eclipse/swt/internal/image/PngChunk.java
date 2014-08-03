package org.eclipse.swt.internal.image;

import java.io.IOException;
import org.eclipse.swt.SWT;

class PngChunk
{
  byte[] reference;
  static final int LENGTH_OFFSET = 0;
  static final int TYPE_OFFSET = 4;
  static final int DATA_OFFSET = 8;
  static final int TYPE_FIELD_LENGTH = 4;
  static final int LENGTH_FIELD_LENGTH = 4;
  static final int MIN_LENGTH = 12;
  static final int CHUNK_UNKNOWN = -1;
  static final int CHUNK_IHDR = 0;
  static final int CHUNK_PLTE = 1;
  static final int CHUNK_IDAT = 2;
  static final int CHUNK_IEND = 3;
  static final int CHUNK_tRNS = 5;
  static final byte[] TYPE_IHDR = { 73, 72, 68, 82 };
  static final byte[] TYPE_PLTE = { 80, 76, 84, 69 };
  static final byte[] TYPE_IDAT = { 73, 68, 65, 84 };
  static final byte[] TYPE_IEND = { 73, 69, 78, 68 };
  static final byte[] TYPE_tRNS = { 116, 82, 78, 83 };
  static final int[] CRC_TABLE = new int[256];
  int length;

  static
  {
    for (int i = 0; i < 256; i++)
    {
      CRC_TABLE[i] = i;
      for (int j = 0; j < 8; j++)
        if ((CRC_TABLE[i] & 0x1) == 0)
          CRC_TABLE[i] = (CRC_TABLE[i] >> 1 & 0x7FFFFFFF);
        else
          CRC_TABLE[i] = (0xEDB88320 ^ CRC_TABLE[i] >> 1 & 0x7FFFFFFF);
    }
  }

  PngChunk(byte[] paramArrayOfByte)
  {
    setReference(paramArrayOfByte);
    if (paramArrayOfByte.length < 4)
      SWT.error(40);
    this.length = getInt32(0);
  }

  PngChunk(int paramInt)
  {
    this(new byte[12 + paramInt]);
    setLength(paramInt);
  }

  byte[] getReference()
  {
    return this.reference;
  }

  void setReference(byte[] paramArrayOfByte)
  {
    this.reference = paramArrayOfByte;
  }

  int getInt16(int paramInt)
  {
    int i = 0;
    i |= (this.reference[paramInt] & 0xFF) << 8;
    i |= this.reference[(paramInt + 1)] & 0xFF;
    return i;
  }

  void setInt16(int paramInt1, int paramInt2)
  {
    this.reference[paramInt1] = ((byte)(paramInt2 >> 8 & 0xFF));
    this.reference[(paramInt1 + 1)] = ((byte)(paramInt2 & 0xFF));
  }

  int getInt32(int paramInt)
  {
    int i = 0;
    i |= (this.reference[paramInt] & 0xFF) << 24;
    i |= (this.reference[(paramInt + 1)] & 0xFF) << 16;
    i |= (this.reference[(paramInt + 2)] & 0xFF) << 8;
    i |= this.reference[(paramInt + 3)] & 0xFF;
    return i;
  }

  void setInt32(int paramInt1, int paramInt2)
  {
    this.reference[paramInt1] = ((byte)(paramInt2 >> 24 & 0xFF));
    this.reference[(paramInt1 + 1)] = ((byte)(paramInt2 >> 16 & 0xFF));
    this.reference[(paramInt1 + 2)] = ((byte)(paramInt2 >> 8 & 0xFF));
    this.reference[(paramInt1 + 3)] = ((byte)(paramInt2 & 0xFF));
  }

  int getLength()
  {
    return this.length;
  }

  void setLength(int paramInt)
  {
    setInt32(0, paramInt);
    this.length = paramInt;
  }

  byte[] getTypeBytes()
  {
    byte[] arrayOfByte = new byte[4];
    System.arraycopy(this.reference, 4, arrayOfByte, 0, 4);
    return arrayOfByte;
  }

  void setType(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte.length != 4)
      SWT.error(5);
    System.arraycopy(paramArrayOfByte, 0, this.reference, 4, 4);
  }

  byte[] getData()
  {
    int i = getLength();
    if (this.reference.length < 12 + i)
      SWT.error(6);
    byte[] arrayOfByte = new byte[i];
    System.arraycopy(this.reference, 8, arrayOfByte, 0, i);
    return arrayOfByte;
  }

  void setData(byte[] paramArrayOfByte)
  {
    setLength(paramArrayOfByte.length);
    System.arraycopy(paramArrayOfByte, 0, this.reference, 8, paramArrayOfByte.length);
    setCRC(computeCRC());
  }

  int getCRC()
  {
    int i = 8 + getLength();
    return getInt32(i);
  }

  void setCRC(int paramInt)
  {
    int i = 8 + getLength();
    setInt32(i, paramInt);
  }

  int getSize()
  {
    return 12 + getLength();
  }

  boolean checkCRC()
  {
    int i = computeCRC();
    int j = getCRC();
    return i == j;
  }

  int computeCRC()
  {
    int i = -1;
    int j = 4;
    int k = 8 + getLength();
    for (int m = j; m < k; m++)
    {
      int n = (i ^ this.reference[m]) & 0xFF;
      i = CRC_TABLE[n] ^ i >> 8 & 0xFFFFFF;
    }
    return i ^ 0xFFFFFFFF;
  }

  boolean typeMatchesArray(byte[] paramArrayOfByte)
  {
    for (int i = 0; i < 4; i++)
      if (this.reference[(4 + i)] != paramArrayOfByte[i])
        return false;
    return true;
  }

  boolean isCritical()
  {
    int i = (char)getTypeBytes()[0];
    return (65 <= i) && (i <= 90);
  }

  int getChunkType()
  {
    if (typeMatchesArray(TYPE_IHDR))
      return 0;
    if (typeMatchesArray(TYPE_PLTE))
      return 1;
    if (typeMatchesArray(TYPE_IDAT))
      return 2;
    if (typeMatchesArray(TYPE_IEND))
      return 3;
    if (typeMatchesArray(TYPE_tRNS))
      return 5;
    return -1;
  }

  static PngChunk readNextFromStream(LEDataInputStream paramLEDataInputStream)
  {
    try
    {
      int i = 8;
      byte[] arrayOfByte1 = new byte[i];
      int j = paramLEDataInputStream.read(arrayOfByte1, 0, i);
      paramLEDataInputStream.unread(arrayOfByte1);
      if (j != i)
        return null;
      PngChunk localPngChunk = new PngChunk(arrayOfByte1);
      int k = localPngChunk.getSize();
      byte[] arrayOfByte2 = new byte[k];
      j = paramLEDataInputStream.read(arrayOfByte2, 0, k);
      if (j != k)
        return null;
      switch (localPngChunk.getChunkType())
      {
      case 0:
        return new PngIhdrChunk(arrayOfByte2);
      case 1:
        return new PngPlteChunk(arrayOfByte2);
      case 2:
        return new PngIdatChunk(arrayOfByte2);
      case 3:
        return new PngIendChunk(arrayOfByte2);
      case 5:
        return new PngTrnsChunk(arrayOfByte2);
      case 4:
      }
      return new PngChunk(arrayOfByte2);
    }
    catch (IOException localIOException)
    {
    }
    return null;
  }

  void validate(PngFileReadState paramPngFileReadState, PngIhdrChunk paramPngIhdrChunk)
  {
    if (this.reference.length < 12)
      SWT.error(40);
    byte[] arrayOfByte = getTypeBytes();
    int i = (char)arrayOfByte[2];
    if ((65 > i) || (i > 90))
      SWT.error(40);
    for (int j = 0; j < 4; j++)
    {
      i = (char)arrayOfByte[j];
      if (((97 > i) || (i > 122)) && ((65 > i) || (i > 90)))
        SWT.error(40);
    }
    if (!checkCRC())
      SWT.error(40);
  }

  void contributeToString(StringBuffer paramStringBuffer)
  {
  }

  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("{");
    localStringBuffer.append("\n\tLength: ");
    localStringBuffer.append(getLength());
    localStringBuffer.append("\n\tType: ");
    byte[] arrayOfByte = getTypeBytes();
    for (int i = 0; i < arrayOfByte.length; i++)
      localStringBuffer.append((char)arrayOfByte[i]);
    contributeToString(localStringBuffer);
    localStringBuffer.append("\n\tCRC: ");
    localStringBuffer.append(Integer.toHexString(getCRC()));
    localStringBuffer.append("\n}");
    return localStringBuffer.toString();
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.image.PngChunk
 * JD-Core Version:    0.6.2
 */