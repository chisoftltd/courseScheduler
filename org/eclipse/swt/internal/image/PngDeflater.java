package org.eclipse.swt.internal.image;

import java.io.ByteArrayOutputStream;

public class PngDeflater
{
  static final int BASE = 65521;
  static final int WINDOW = 32768;
  static final int MIN_LENGTH = 3;
  static final int MAX_MATCHES = 32;
  static final int HASH = 8209;
  byte[] in;
  int inLength;
  ByteArrayOutputStream bytes = new ByteArrayOutputStream(1024);
  int adler32 = 1;
  int buffer;
  int bitCount;
  Link[] hashtable = new Link[8209];
  Link[] window = new Link[32768];
  int nextWindow;
  static final short[] mirrorBytes = { 0, 128, 64, 192, 32, 160, 96, 224, 16, 144, 80, 208, 48, 176, 112, 240, 8, 136, 72, 200, 40, 168, 104, 232, 24, 152, 88, 216, 56, 184, 120, 248, 4, 132, 68, 196, 36, 164, 100, 228, 20, 148, 84, 212, 52, 180, 116, 244, 12, 140, 76, 204, 44, 172, 108, 236, 28, 156, 92, 220, 60, 188, 124, 252, 2, 130, 66, 194, 34, 162, 98, 226, 18, 146, 82, 210, 50, 178, 114, 242, 10, 138, 74, 202, 42, 170, 106, 234, 26, 154, 90, 218, 58, 186, 122, 250, 6, 134, 70, 198, 38, 166, 102, 230, 22, 150, 86, 214, 54, 182, 118, 246, 14, 142, 78, 206, 46, 174, 110, 238, 30, 158, 94, 222, 62, 190, 126, 254, 1, 129, 65, 193, 33, 161, 97, 225, 17, 145, 81, 209, 49, 177, 113, 241, 9, 137, 73, 201, 41, 169, 105, 233, 25, 153, 89, 217, 57, 185, 121, 249, 5, 133, 69, 197, 37, 165, 101, 229, 21, 149, 85, 213, 53, 181, 117, 245, 13, 141, 77, 205, 45, 173, 109, 237, 29, 157, 93, 221, 61, 189, 125, 253, 3, 131, 67, 195, 35, 163, 99, 227, 19, 147, 83, 211, 51, 179, 115, 243, 11, 139, 75, 203, 43, 171, 107, 235, 27, 155, 91, 219, 59, 187, 123, 251, 7, 135, 71, 199, 39, 167, 103, 231, 23, 151, 87, 215, 55, 183, 119, 247, 15, 143, 79, 207, 47, 175, 111, 239, 31, 159, 95, 223, 63, 191, 127, 255 };
  static final Code[] lengthCodes = { new Code(257, 0, 3, 3), new Code(258, 0, 4, 4), new Code(259, 0, 5, 5), new Code(260, 0, 6, 6), new Code(261, 0, 7, 7), new Code(262, 0, 8, 8), new Code(263, 0, 9, 9), new Code(264, 0, 10, 10), new Code(265, 1, 11, 12), new Code(266, 1, 13, 14), new Code(267, 1, 15, 16), new Code(268, 1, 17, 18), new Code(269, 2, 19, 22), new Code(270, 2, 23, 26), new Code(271, 2, 27, 30), new Code(272, 2, 31, 34), new Code(273, 3, 35, 42), new Code(274, 3, 43, 50), new Code(275, 3, 51, 58), new Code(276, 3, 59, 66), new Code(277, 4, 67, 82), new Code(278, 4, 83, 98), new Code(279, 4, 99, 114), new Code(280, 4, 115, 130), new Code(281, 5, 131, 162), new Code(282, 5, 163, 194), new Code(283, 5, 195, 226), new Code(284, 5, 227, 257), new Code(285, 0, 258, 258) };
  static final Code[] distanceCodes = { new Code(0, 0, 1, 1), new Code(1, 0, 2, 2), new Code(2, 0, 3, 3), new Code(3, 0, 4, 4), new Code(4, 1, 5, 6), new Code(5, 1, 7, 8), new Code(6, 2, 9, 12), new Code(7, 2, 13, 16), new Code(8, 3, 17, 24), new Code(9, 3, 25, 32), new Code(10, 4, 33, 48), new Code(11, 4, 49, 64), new Code(12, 5, 65, 96), new Code(13, 5, 97, 128), new Code(14, 6, 129, 192), new Code(15, 6, 193, 256), new Code(16, 7, 257, 384), new Code(17, 7, 385, 512), new Code(18, 8, 513, 768), new Code(19, 8, 769, 1024), new Code(20, 9, 1025, 1536), new Code(21, 9, 1537, 2048), new Code(22, 10, 2049, 3072), new Code(23, 10, 3073, 4096), new Code(24, 11, 4097, 6144), new Code(25, 11, 6145, 8192), new Code(26, 12, 8193, 12288), new Code(27, 12, 12289, 16384), new Code(28, 13, 16385, 24576), new Code(29, 13, 24577, 32768) };

  void writeShortLSB(ByteArrayOutputStream paramByteArrayOutputStream, int paramInt)
  {
    int i = (byte)(paramInt & 0xFF);
    int j = (byte)(paramInt >> 8 & 0xFF);
    byte[] arrayOfByte = { i, j };
    paramByteArrayOutputStream.write(arrayOfByte, 0, 2);
  }

  void writeInt(ByteArrayOutputStream paramByteArrayOutputStream, int paramInt)
  {
    int i = (byte)(paramInt >> 24 & 0xFF);
    int j = (byte)(paramInt >> 16 & 0xFF);
    int k = (byte)(paramInt >> 8 & 0xFF);
    int m = (byte)(paramInt & 0xFF);
    byte[] arrayOfByte = { i, j, k, m };
    paramByteArrayOutputStream.write(arrayOfByte, 0, 4);
  }

  void updateAdler(byte paramByte)
  {
    int i = this.adler32 & 0xFFFF;
    int j = this.adler32 >> 16 & 0xFFFF;
    int k = paramByte & 0xFF;
    i = (i + k) % 65521;
    j = (i + j) % 65521;
    this.adler32 = (j << 16 | i);
  }

  int hash(byte[] paramArrayOfByte)
  {
    int i = ((paramArrayOfByte[0] & 0xFF) << 24 | (paramArrayOfByte[1] & 0xFF) << 16 | (paramArrayOfByte[2] & 0xFF) << 8) % 8209;
    if (i < 0)
      i += 8209;
    return i;
  }

  void writeBits(int paramInt1, int paramInt2)
  {
    this.buffer |= paramInt1 << this.bitCount;
    this.bitCount += paramInt2;
    if (this.bitCount >= 16)
    {
      this.bytes.write((byte)this.buffer);
      this.bytes.write((byte)(this.buffer >>> 8));
      this.buffer >>>= 16;
      this.bitCount -= 16;
    }
  }

  void alignToByte()
  {
    if (this.bitCount > 0)
    {
      this.bytes.write((byte)this.buffer);
      if (this.bitCount > 8)
        this.bytes.write((byte)(this.buffer >>> 8));
    }
    this.buffer = 0;
    this.bitCount = 0;
  }

  void outputLiteral(byte paramByte)
  {
    int i = paramByte & 0xFF;
    if (i <= 143)
      writeBits(mirrorBytes[(48 + i)], 8);
    else
      writeBits(1 + 2 * mirrorBytes[(0 + i)], 9);
  }

  Code findCode(int paramInt, Code[] paramArrayOfCode)
  {
    int i = -1;
    int j = paramArrayOfCode.length;
    int k;
    while (true)
    {
      k = (j + i) / 2;
      if (paramInt < paramArrayOfCode[k].min)
      {
        j = k;
      }
      else
      {
        if (paramInt <= paramArrayOfCode[k].max)
          break;
        i = k;
      }
    }
    return paramArrayOfCode[k];
  }

  void outputMatch(int paramInt1, int paramInt2)
  {
    while (paramInt1 > 0)
    {
      int i;
      if (paramInt1 > 260)
        i = 258;
      else if (paramInt1 <= 258)
        i = paramInt1;
      else
        i = paramInt1 - 3;
      paramInt1 -= i;
      Code localCode2 = findCode(i, lengthCodes);
      if (localCode2.code <= 279)
        writeBits(mirrorBytes[((localCode2.code - 256) * 2)], 7);
      else
        writeBits(mirrorBytes[(-88 + localCode2.code)], 8);
      if (localCode2.extraBits != 0)
        writeBits(i - localCode2.min, localCode2.extraBits);
      Code localCode1 = findCode(paramInt2, distanceCodes);
      writeBits(mirrorBytes[(localCode1.code * 8)], 5);
      if (localCode1.extraBits != 0)
        writeBits(paramInt2 - localCode1.min, localCode1.extraBits);
    }
  }

  Match findLongestMatch(int paramInt, Link paramLink)
  {
    Link localLink = paramLink;
    int i = 0;
    Match localMatch = new Match(-1, -1);
    do
    {
      int j = localLink.value;
      if ((paramInt - j < 32768) && (j != 0))
      {
        for (int k = 1; paramInt + k < this.inLength; k++)
          if (this.in[(paramInt + k)] != this.in[(j + k)])
            break;
        if (k >= 3)
        {
          if (k > localMatch.length)
          {
            localMatch.length = k;
            localMatch.distance = (paramInt - j);
          }
          i++;
          if (i == 32)
            break;
        }
      }
      localLink = localLink.next;
    }
    while (localLink != null);
    if ((localMatch.length < 3) || (localMatch.distance < 1) || (localMatch.distance > 32768))
      return null;
    return localMatch;
  }

  void updateHashtable(int paramInt1, int paramInt2)
  {
    byte[] arrayOfByte = new byte[3];
    for (int j = paramInt1; j < paramInt2; j++)
    {
      if (j + 3 > this.inLength)
        break;
      arrayOfByte[0] = this.in[j];
      arrayOfByte[1] = this.in[(j + 1)];
      arrayOfByte[2] = this.in[(j + 2)];
      int i = hash(arrayOfByte);
      if (this.window[this.nextWindow].previous != null)
        this.window[this.nextWindow].previous.next = null;
      else if (this.window[this.nextWindow].hash != 0)
        this.hashtable[this.window[this.nextWindow].hash].next = null;
      this.window[this.nextWindow].hash = i;
      this.window[this.nextWindow].value = j;
      this.window[this.nextWindow].previous = null;
      Link localLink = this.window[this.nextWindow].next = this.hashtable[i].next;
      this.hashtable[i].next = this.window[this.nextWindow];
      if (localLink != null)
        localLink.previous = this.window[this.nextWindow];
      this.nextWindow += 1;
      if (this.nextWindow == 32768)
        this.nextWindow = 0;
    }
  }

  void compress()
  {
    byte[] arrayOfByte = new byte[3];
    for (int m = 0; m < 8209; m++)
      this.hashtable[m] = new Link();
    for (m = 0; m < 32768; m++)
      this.window[m] = new Link();
    this.nextWindow = 0;
    int n = -1;
    Object localObject = null;
    writeBits(1, 1);
    writeBits(1, 2);
    outputLiteral(this.in[0]);
    int i = 1;
    while (i < this.inLength)
      if (this.inLength - i < 3)
      {
        outputLiteral(this.in[i]);
        i++;
      }
      else
      {
        arrayOfByte[0] = this.in[i];
        arrayOfByte[1] = this.in[(i + 1)];
        arrayOfByte[2] = this.in[(i + 2)];
        int k = hash(arrayOfByte);
        Link localLink = this.hashtable[k];
        Match localMatch = findLongestMatch(i, localLink);
        updateHashtable(i, i + 1);
        int j;
        if (localMatch != null)
        {
          if (localObject != null)
          {
            if (localMatch.length > localObject.length + 1)
            {
              outputLiteral(this.in[n]);
              n = i;
              localObject = localMatch;
              i++;
            }
            else
            {
              outputMatch(localObject.length, localObject.distance);
              j = n + localObject.length;
              n = -1;
              localObject = null;
              updateHashtable(i + 1, j);
              i = j;
            }
          }
          else
          {
            n = i;
            localObject = localMatch;
            i++;
          }
        }
        else if (localObject != null)
        {
          outputMatch(localObject.length, localObject.distance);
          j = n + localObject.length;
          n = -1;
          localObject = null;
          updateHashtable(i + 1, j);
          i = j;
        }
        else
        {
          outputLiteral(this.in[i]);
          i++;
        }
      }
    writeBits(0, 7);
    alignToByte();
  }

  void compressHuffmanOnly()
  {
    writeBits(1, 1);
    writeBits(1, 2);
    for (int i = 0; i < this.inLength; i++)
      outputLiteral(this.in[i]);
    writeBits(0, 7);
    alignToByte();
  }

  void store()
  {
    int i = 0;
    int j = this.inLength;
    int m = 0;
    while (j > 0)
    {
      int k;
      if (j < 65535)
      {
        k = j;
        m = 1;
      }
      else
      {
        k = 65535;
        m = 0;
      }
      this.bytes.write((byte)m);
      writeShortLSB(this.bytes, k);
      writeShortLSB(this.bytes, k ^ 0xFFFF);
      this.bytes.write(this.in, i, k);
      j -= k;
      i += k;
    }
  }

  public byte[] deflate(byte[] paramArrayOfByte)
  {
    this.in = paramArrayOfByte;
    this.inLength = paramArrayOfByte.length;
    this.bytes.write(120);
    this.bytes.write(-100);
    for (int i = 0; i < this.inLength; i++)
      updateAdler(this.in[i]);
    compress();
    writeInt(this.bytes, this.adler32);
    return this.bytes.toByteArray();
  }

  static class Code
  {
    int code;
    int extraBits;
    int min;
    int max;

    Code(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      this.code = paramInt1;
      this.extraBits = paramInt2;
      this.min = paramInt3;
      this.max = paramInt4;
    }
  }

  static class Link
  {
    int hash = 0;
    int value = 0;
    Link previous = null;
    Link next = null;
  }

  static class Match
  {
    int length;
    int distance;

    Match(int paramInt1, int paramInt2)
    {
      this.length = paramInt1;
      this.distance = paramInt2;
    }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.image.PngDeflater
 * JD-Core Version:    0.6.2
 */