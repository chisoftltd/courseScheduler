package org.eclipse.swt.internal.image;

final class JPEGStartOfImage extends JPEGFixedSizeSegment
{
  public JPEGStartOfImage()
  {
  }

  public JPEGStartOfImage(byte[] paramArrayOfByte)
  {
    super(paramArrayOfByte);
  }

  public JPEGStartOfImage(LEDataInputStream paramLEDataInputStream)
  {
    super(paramLEDataInputStream);
  }

  public int signature()
  {
    return 65496;
  }

  public int fixedSize()
  {
    return 2;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.image.JPEGStartOfImage
 * JD-Core Version:    0.6.2
 */