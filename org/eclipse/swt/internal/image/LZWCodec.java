package org.eclipse.swt.internal.image;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.ImageLoaderEvent;

final class LZWCodec
{
  int bitsPerPixel;
  int blockSize;
  int blockIndex;
  int currentByte;
  int bitsLeft;
  int codeSize;
  int clearCode;
  int endCode;
  int newCodes;
  int topSlot;
  int currentSlot;
  int imageWidth;
  int imageHeight;
  int imageX;
  int imageY;
  int pass;
  int line;
  int codeMask;
  byte[] block;
  byte[] lineArray;
  int[] stack;
  int[] suffix;
  int[] prefix;
  LZWNode[] nodeStack;
  LEDataInputStream inputStream;
  LEDataOutputStream outputStream;
  ImageData image;
  ImageLoader loader;
  boolean interlaced;
  static final int[] MASK_TABLE = { 1, 3, 7, 15, 31, 63, 127, 255, 511, 1023, 2047, 4095 };

  void decode()
  {
    int j = 0;
    int k = 0;
    byte[] arrayOfByte = new byte[this.imageWidth];
    int m = 0;
    int n = 0;
    int i1;
    while ((i1 = nextCode()) != this.endCode)
      if (i1 == this.clearCode)
      {
        this.codeSize = (this.bitsPerPixel + 1);
        this.codeMask = MASK_TABLE[this.bitsPerPixel];
        this.currentSlot = this.newCodes;
        this.topSlot = (1 << this.codeSize);
        while ((i1 = nextCode()) == this.clearCode);
        if (i1 != this.endCode)
        {
          j = k = i1;
          arrayOfByte[n] = ((byte)i1);
          n++;
          if (n == this.imageWidth)
          {
            nextPutPixels(arrayOfByte);
            n = 0;
          }
        }
      }
      else
      {
        int i = i1;
        if (i >= this.currentSlot)
        {
          i = j;
          this.stack[m] = k;
          m++;
        }
        while (i >= this.newCodes)
        {
          this.stack[m] = this.suffix[i];
          m++;
          i = this.prefix[i];
        }
        this.stack[m] = i;
        m++;
        if (this.currentSlot < this.topSlot)
        {
          k = i;
          this.suffix[this.currentSlot] = k;
          this.prefix[this.currentSlot] = j;
          this.currentSlot += 1;
          j = i1;
        }
        if ((this.currentSlot >= this.topSlot) && (this.codeSize < 12))
        {
          this.codeMask = MASK_TABLE[this.codeSize];
          this.codeSize += 1;
          this.topSlot += this.topSlot;
        }
        while (m > 0)
        {
          m--;
          arrayOfByte[n] = ((byte)this.stack[m]);
          n++;
          if (n == this.imageWidth)
          {
            nextPutPixels(arrayOfByte);
            n = 0;
          }
        }
      }
    if ((n != 0) && (this.line < this.imageHeight))
      nextPutPixels(arrayOfByte);
  }

  public void decode(LEDataInputStream paramLEDataInputStream, ImageLoader paramImageLoader, ImageData paramImageData, boolean paramBoolean, int paramInt)
  {
    this.inputStream = paramLEDataInputStream;
    this.loader = paramImageLoader;
    this.image = paramImageData;
    this.interlaced = paramBoolean;
    this.bitsPerPixel = paramInt;
    initializeForDecoding();
    decode();
  }

  void encode()
  {
    nextPutCode(this.clearCode);
    int i = encodeLoop();
    nextPutCode(i);
    nextPutCode(this.endCode);
    if (this.bitsLeft == 8)
      this.block[0] = ((byte)(this.blockIndex - 1));
    else
      this.block[0] = ((byte)this.blockIndex);
    writeBlock();
    if (this.block[0] != 0)
    {
      this.block[0] = 0;
      writeBlock();
    }
  }

  public void encode(LEDataOutputStream paramLEDataOutputStream, ImageData paramImageData)
  {
    this.outputStream = paramLEDataOutputStream;
    this.image = paramImageData;
    initializeForEncoding();
    encode();
  }

  int encodeLoop()
  {
    int i = nextPixel();
    while (true)
    {
      int k = i;
      LZWNode localLZWNode = this.nodeStack[k];
      int j = 1;
      i = nextPixel();
      if (i < 0)
        return k;
      while ((j != 0) && (localLZWNode.children != null))
      {
        localLZWNode = localLZWNode.children;
        while ((j != 0) && (localLZWNode.suffix != i))
          if (i < localLZWNode.suffix)
          {
            if (localLZWNode.left == null)
            {
              localLZWNode.left = new LZWNode();
              j = 0;
            }
            localLZWNode = localLZWNode.left;
          }
          else
          {
            if (localLZWNode.right == null)
            {
              localLZWNode.right = new LZWNode();
              j = 0;
            }
            localLZWNode = localLZWNode.right;
          }
        if (j != 0)
        {
          k = localLZWNode.code;
          i = nextPixel();
          if (i < 0)
            return k;
        }
      }
      if (j != 0)
      {
        localLZWNode.children = new LZWNode();
        localLZWNode = localLZWNode.children;
      }
      localLZWNode.children = null;
      localLZWNode.left = null;
      localLZWNode.right = null;
      localLZWNode.code = this.currentSlot;
      localLZWNode.prefix = k;
      localLZWNode.suffix = i;
      nextPutCode(k);
      this.currentSlot += 1;
      if (this.currentSlot < 4096)
      {
        if (this.currentSlot > this.topSlot)
        {
          this.codeSize += 1;
          this.codeMask = MASK_TABLE[(this.codeSize - 1)];
          this.topSlot *= 2;
        }
      }
      else
      {
        nextPutCode(this.clearCode);
        for (int m = 0; m < this.nodeStack.length; m++)
          this.nodeStack[m].children = null;
        this.codeSize = (this.bitsPerPixel + 1);
        this.codeMask = MASK_TABLE[(this.codeSize - 1)];
        this.currentSlot = this.newCodes;
        this.topSlot = (1 << this.codeSize);
      }
    }
  }

  void initializeForDecoding()
  {
    this.pass = 1;
    this.line = 0;
    this.codeSize = (this.bitsPerPixel + 1);
    this.topSlot = (1 << this.codeSize);
    this.clearCode = (1 << this.bitsPerPixel);
    this.endCode = (this.clearCode + 1);
    this.newCodes = (this.currentSlot = this.endCode + 1);
    this.currentByte = -1;
    this.blockSize = (this.bitsLeft = 0);
    this.blockIndex = 0;
    this.codeMask = MASK_TABLE[(this.codeSize - 1)];
    this.stack = new int[4096];
    this.suffix = new int[4096];
    this.prefix = new int[4096];
    this.block = new byte[256];
    this.imageWidth = this.image.width;
    this.imageHeight = this.image.height;
  }

  void initializeForEncoding()
  {
    this.interlaced = false;
    this.bitsPerPixel = this.image.depth;
    this.codeSize = (this.bitsPerPixel + 1);
    this.topSlot = (1 << this.codeSize);
    this.clearCode = (1 << this.bitsPerPixel);
    this.endCode = (this.clearCode + 1);
    this.newCodes = (this.currentSlot = this.endCode + 1);
    this.bitsLeft = 8;
    this.currentByte = 0;
    this.blockIndex = 1;
    this.blockSize = 255;
    this.block = new byte[this.blockSize];
    this.block[0] = ((byte)(this.blockSize - 1));
    this.nodeStack = new LZWNode[1 << this.bitsPerPixel];
    for (int i = 0; i < this.nodeStack.length; i++)
    {
      LZWNode localLZWNode = new LZWNode();
      localLZWNode.code = (i + 1);
      localLZWNode.prefix = -1;
      localLZWNode.suffix = (i + 1);
      this.nodeStack[i] = localLZWNode;
    }
    this.imageWidth = this.image.width;
    this.imageHeight = this.image.height;
    this.imageY = -1;
    this.lineArray = new byte[this.imageWidth];
    this.imageX = (this.imageWidth + 1);
  }

  int nextCode()
  {
    int i;
    if (this.bitsLeft == 0)
    {
      if (this.blockIndex >= this.blockSize)
      {
        this.blockSize = readBlock();
        this.blockIndex = 0;
        if (this.blockSize == 0)
          return this.endCode;
      }
      this.blockIndex += 1;
      this.currentByte = (this.block[this.blockIndex] & 0xFF);
      this.bitsLeft = 8;
      i = this.currentByte;
    }
    else
    {
      int j = this.bitsLeft - 8;
      if (j < 0)
        i = this.currentByte >> 0 - j;
      else
        i = this.currentByte << j;
    }
    while (this.codeSize > this.bitsLeft)
    {
      if (this.blockIndex >= this.blockSize)
      {
        this.blockSize = readBlock();
        this.blockIndex = 0;
        if (this.blockSize == 0)
          return this.endCode;
      }
      this.blockIndex += 1;
      this.currentByte = (this.block[this.blockIndex] & 0xFF);
      i += (this.currentByte << this.bitsLeft);
      this.bitsLeft += 8;
    }
    this.bitsLeft -= this.codeSize;
    return i & this.codeMask;
  }

  int nextPixel()
  {
    this.imageX += 1;
    if (this.imageX > this.imageWidth)
    {
      this.imageY += 1;
      if (this.imageY >= this.imageHeight)
        return -1;
      nextPixels(this.lineArray, this.imageWidth);
      this.imageX = 1;
    }
    return this.lineArray[(this.imageX - 1)] & 0xFF;
  }

  void nextPixels(byte[] paramArrayOfByte, int paramInt)
  {
    if (this.image.depth == 8)
      System.arraycopy(this.image.data, this.imageY * this.image.bytesPerLine, paramArrayOfByte, 0, paramInt);
    else
      this.image.getPixels(0, this.imageY, paramInt, paramArrayOfByte, 0);
  }

  void nextPutCode(int paramInt)
  {
    int i = paramInt;
    int j = this.codeSize;
    int k = i & MASK_TABLE[(this.bitsLeft - 1)];
    this.currentByte |= k << 8 - this.bitsLeft;
    this.block[this.blockIndex] = ((byte)this.currentByte);
    j -= this.bitsLeft;
    if (j < 1)
    {
      this.bitsLeft -= this.codeSize;
      if (this.bitsLeft == 0)
      {
        this.bitsLeft = 8;
        this.blockIndex += 1;
        if (this.blockIndex >= this.blockSize)
        {
          writeBlock();
          this.blockIndex = 1;
        }
        this.currentByte = 0;
      }
      return;
    }
    i >>= this.bitsLeft;
    this.blockIndex += 1;
    if (this.blockIndex >= this.blockSize)
    {
      writeBlock();
      this.blockIndex = 1;
    }
    while (j >= 8)
    {
      this.currentByte = (i & 0xFF);
      this.block[this.blockIndex] = ((byte)this.currentByte);
      i >>= 8;
      j -= 8;
      this.blockIndex += 1;
      if (this.blockIndex >= this.blockSize)
      {
        writeBlock();
        this.blockIndex = 1;
      }
    }
    this.bitsLeft = (8 - j);
    this.currentByte = i;
    this.block[this.blockIndex] = ((byte)this.currentByte);
  }

  void nextPutPixels(byte[] paramArrayOfByte)
  {
    if (this.image.depth == 8)
    {
      int i = this.line * this.image.bytesPerLine;
      for (int j = 0; j < this.imageWidth; j++)
        this.image.data[(i + j)] = paramArrayOfByte[j];
    }
    else
    {
      this.image.setPixels(0, this.line, this.imageWidth, paramArrayOfByte, 0);
    }
    if (this.interlaced)
    {
      if (this.pass == 1)
      {
        copyRow(paramArrayOfByte, 7);
        this.line += 8;
      }
      else if (this.pass == 2)
      {
        copyRow(paramArrayOfByte, 3);
        this.line += 8;
      }
      else if (this.pass == 3)
      {
        copyRow(paramArrayOfByte, 1);
        this.line += 4;
      }
      else if (this.pass == 4)
      {
        this.line += 2;
      }
      else if (this.pass == 5)
      {
        this.line += 0;
      }
      if (this.line >= this.imageHeight)
      {
        this.pass += 1;
        if (this.pass == 2)
          this.line = 4;
        else if (this.pass == 3)
          this.line = 2;
        else if (this.pass == 4)
          this.line = 1;
        else if (this.pass == 5)
          this.line = 0;
        if ((this.pass < 5) && (this.loader.hasListeners()))
        {
          ImageData localImageData = (ImageData)this.image.clone();
          this.loader.notifyListeners(new ImageLoaderEvent(this.loader, localImageData, this.pass - 2, false));
        }
      }
      if (this.line >= this.imageHeight)
        this.line = 0;
    }
    else
    {
      this.line += 1;
    }
  }

  void copyRow(byte[] paramArrayOfByte, int paramInt)
  {
    for (int i = 1; i <= paramInt; i++)
      if (this.line + i < this.imageHeight)
        this.image.setPixels(0, this.line + i, this.imageWidth, paramArrayOfByte, 0);
  }

  int readBlock()
  {
    int i = -1;
    try
    {
      i = this.inputStream.read();
      if (i == -1)
        SWT.error(40);
      this.block[0] = ((byte)i);
      i = this.inputStream.read(this.block, 1, i);
      if (i == -1)
        SWT.error(40);
    }
    catch (Exception localException)
    {
      SWT.error(39, localException);
    }
    return i;
  }

  void writeBlock()
  {
    try
    {
      this.outputStream.write(this.block, 0, (this.block[0] & 0xFF) + 1);
    }
    catch (Exception localException)
    {
      SWT.error(39, localException);
    }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.image.LZWCodec
 * JD-Core Version:    0.6.2
 */