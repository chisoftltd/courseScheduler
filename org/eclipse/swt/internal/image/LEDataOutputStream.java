package org.eclipse.swt.internal.image;

import java.io.IOException;
import java.io.OutputStream;

final class LEDataOutputStream extends OutputStream
{
  OutputStream out;

  public LEDataOutputStream(OutputStream paramOutputStream)
  {
    this.out = paramOutputStream;
  }

  public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    this.out.write(paramArrayOfByte, paramInt1, paramInt2);
  }

  public void write(int paramInt)
    throws IOException
  {
    this.out.write(paramInt);
  }

  public void writeByte(byte paramByte)
    throws IOException
  {
    this.out.write(paramByte & 0xFF);
  }

  public void writeInt(int paramInt)
    throws IOException
  {
    this.out.write(paramInt & 0xFF);
    this.out.write(paramInt >> 8 & 0xFF);
    this.out.write(paramInt >> 16 & 0xFF);
    this.out.write(paramInt >> 24 & 0xFF);
  }

  public void writeShort(int paramInt)
    throws IOException
  {
    this.out.write(paramInt & 0xFF);
    this.out.write(paramInt >> 8 & 0xFF);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.image.LEDataOutputStream
 * JD-Core Version:    0.6.2
 */