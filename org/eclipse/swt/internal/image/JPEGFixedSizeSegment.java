package org.eclipse.swt.internal.image;

import org.eclipse.swt.SWT;

abstract class JPEGFixedSizeSegment extends JPEGSegment
{
  public JPEGFixedSizeSegment()
  {
    this.reference = new byte[fixedSize()];
    setSegmentMarker(signature());
  }

  public JPEGFixedSizeSegment(byte[] paramArrayOfByte)
  {
    super(paramArrayOfByte);
  }

  public JPEGFixedSizeSegment(LEDataInputStream paramLEDataInputStream)
  {
    this.reference = new byte[fixedSize()];
    try
    {
      paramLEDataInputStream.read(this.reference);
    }
    catch (Exception localException)
    {
      SWT.error(39, localException);
    }
  }

  public abstract int fixedSize();

  public int getSegmentLength()
  {
    return fixedSize() - 2;
  }

  public void setSegmentLength(int paramInt)
  {
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.image.JPEGFixedSizeSegment
 * JD-Core Version:    0.6.2
 */