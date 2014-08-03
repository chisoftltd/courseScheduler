package org.eclipse.swt.internal.image;

import java.io.IOException;

final class TIFFRandomFileAccess
{
  LEDataInputStream inputStream;
  int start;
  int current;
  int next;
  byte[][] buffers;
  static final int CHUNK_SIZE = 8192;
  static final int LIST_SIZE = 128;

  public TIFFRandomFileAccess(LEDataInputStream paramLEDataInputStream)
  {
    this.inputStream = paramLEDataInputStream;
    this.start = (this.current = this.next = this.inputStream.getPosition());
    this.buffers = new byte[''][];
  }

  void seek(int paramInt)
    throws IOException
  {
    if (paramInt == this.current)
      return;
    if (paramInt < this.start)
      throw new IOException();
    this.current = paramInt;
    if (this.current > this.next)
    {
      int i = this.current - this.next;
      int j = this.next / 8192;
      for (int k = this.next % 8192; i > 0; k = 0)
      {
        if (j >= this.buffers.length)
        {
          byte[][] arrayOfByte = this.buffers;
          this.buffers = new byte[Math.max(j + 1, arrayOfByte.length + 128)][];
          System.arraycopy(arrayOfByte, 0, this.buffers, 0, arrayOfByte.length);
        }
        if (this.buffers[j] == null)
          this.buffers[j] = new byte[8192];
        int m = this.inputStream.read(this.buffers[j], k, Math.min(i, 8192 - k));
        i -= m;
        this.next += m;
        j++;
      }
    }
  }

  void read(byte[] paramArrayOfByte)
    throws IOException
  {
    int i = paramArrayOfByte.length;
    int j = Math.min(i, this.next - this.current);
    int k = i - this.next + this.current;
    int m = 0;
    int n;
    int i1;
    if (j > 0)
    {
      n = this.current / 8192;
      for (i1 = this.current % 8192; j > 0; i1 = 0)
      {
        int i2 = Math.min(j, 8192 - i1);
        System.arraycopy(this.buffers[n], i1, paramArrayOfByte, m, i2);
        j -= i2;
        m += i2;
        n++;
      }
    }
    if (k > 0)
    {
      n = this.next / 8192;
      for (i1 = this.next % 8192; k > 0; i1 = 0)
      {
        if (n >= this.buffers.length)
        {
          byte[][] arrayOfByte = this.buffers;
          this.buffers = new byte[Math.max(n, arrayOfByte.length + 128)][];
          System.arraycopy(arrayOfByte, 0, this.buffers, 0, arrayOfByte.length);
        }
        if (this.buffers[n] == null)
          this.buffers[n] = new byte[8192];
        int i3 = this.inputStream.read(this.buffers[n], i1, Math.min(k, 8192 - i1));
        System.arraycopy(this.buffers[n], i1, paramArrayOfByte, m, i3);
        k -= i3;
        this.next += i3;
        m += i3;
        n++;
      }
    }
    this.current += i;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.image.TIFFRandomFileAccess
 * JD-Core Version:    0.6.2
 */