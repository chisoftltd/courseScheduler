package org.eclipse.swt.internal.image;

import java.io.IOException;
import java.io.InputStream;

final class LEDataInputStream extends InputStream
{
  int position;
  InputStream in;
  protected byte[] buf;
  protected int pos;

  public LEDataInputStream(InputStream paramInputStream)
  {
    this(paramInputStream, 512);
  }

  public LEDataInputStream(InputStream paramInputStream, int paramInt)
  {
    this.in = paramInputStream;
    if (paramInt > 0)
    {
      this.buf = new byte[paramInt];
      this.pos = paramInt;
    }
    else
    {
      throw new IllegalArgumentException();
    }
  }

  public void close()
    throws IOException
  {
    this.buf = null;
    if (this.in != null)
    {
      this.in.close();
      this.in = null;
    }
  }

  public int getPosition()
  {
    return this.position;
  }

  public int available()
    throws IOException
  {
    if (this.buf == null)
      throw new IOException();
    return this.buf.length - this.pos + this.in.available();
  }

  public int read()
    throws IOException
  {
    if (this.buf == null)
      throw new IOException();
    if (this.pos < this.buf.length)
    {
      this.position += 1;
      return this.buf[(this.pos++)] & 0xFF;
    }
    int i = this.in.read();
    if (i != -1)
      this.position += 1;
    return i;
  }

  public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    int i = 0;
    int j;
    while ((i != paramInt2) && ((j = readData(paramArrayOfByte, paramInt1, paramInt2 - i)) != -1))
    {
      paramInt1 += j;
      i += j;
    }
    this.position += i;
    if ((i == 0) && (i != paramInt2))
      return -1;
    return i;
  }

  private int readData(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    if (this.buf == null)
      throw new IOException();
    if ((paramInt1 < 0) || (paramInt1 > paramArrayOfByte.length) || (paramInt2 < 0) || (paramInt2 > paramArrayOfByte.length - paramInt1))
      throw new ArrayIndexOutOfBoundsException();
    int i = 0;
    int j = paramInt1;
    int k = this.buf.length - this.pos;
    if (k > 0)
    {
      i = k >= paramInt2 ? paramInt2 : k;
      System.arraycopy(this.buf, this.pos, paramArrayOfByte, j, i);
      j += i;
      this.pos += i;
    }
    if (i == paramInt2)
      return paramInt2;
    int m = this.in.read(paramArrayOfByte, j, paramInt2 - i);
    if (m > 0)
      return m + i;
    if (i == 0)
      return m;
    return i;
  }

  public int readInt()
    throws IOException
  {
    byte[] arrayOfByte = new byte[4];
    read(arrayOfByte);
    return (arrayOfByte[3] & 0xFF) << 24 | (arrayOfByte[2] & 0xFF) << 16 | (arrayOfByte[1] & 0xFF) << 8 | arrayOfByte[0] & 0xFF;
  }

  public short readShort()
    throws IOException
  {
    byte[] arrayOfByte = new byte[2];
    read(arrayOfByte);
    return (short)((arrayOfByte[1] & 0xFF) << 8 | arrayOfByte[0] & 0xFF);
  }

  public void unread(byte[] paramArrayOfByte)
    throws IOException
  {
    int i = paramArrayOfByte.length;
    if (i > this.pos)
      throw new IOException();
    this.position -= i;
    this.pos -= i;
    System.arraycopy(paramArrayOfByte, 0, this.buf, this.pos, i);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.image.LEDataInputStream
 * JD-Core Version:    0.6.2
 */