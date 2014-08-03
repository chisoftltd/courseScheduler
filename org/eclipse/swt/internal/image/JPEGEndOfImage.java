package org.eclipse.swt.internal.image;

final class JPEGEndOfImage extends JPEGFixedSizeSegment
{
  public JPEGEndOfImage()
  {
  }

  public JPEGEndOfImage(byte[] paramArrayOfByte)
  {
    super(paramArrayOfByte);
  }

  public int signature()
  {
    return 65497;
  }

  public int fixedSize()
  {
    return 2;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.image.JPEGEndOfImage
 * JD-Core Version:    0.6.2
 */