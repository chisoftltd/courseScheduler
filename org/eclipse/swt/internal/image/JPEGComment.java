package org.eclipse.swt.internal.image;

final class JPEGComment extends JPEGVariableSizeSegment
{
  public JPEGComment(byte[] paramArrayOfByte)
  {
    super(paramArrayOfByte);
  }

  public JPEGComment(LEDataInputStream paramLEDataInputStream)
  {
    super(paramLEDataInputStream);
  }

  public int signature()
  {
    return 65534;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.image.JPEGComment
 * JD-Core Version:    0.6.2
 */