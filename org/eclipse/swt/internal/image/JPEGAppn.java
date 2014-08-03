package org.eclipse.swt.internal.image;

final class JPEGAppn extends JPEGVariableSizeSegment
{
  public JPEGAppn(byte[] paramArrayOfByte)
  {
    super(paramArrayOfByte);
  }

  public JPEGAppn(LEDataInputStream paramLEDataInputStream)
  {
    super(paramLEDataInputStream);
  }

  public boolean verify()
  {
    int i = getSegmentMarker();
    return (i >= 65504) && (i <= 65519);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.image.JPEGAppn
 * JD-Core Version:    0.6.2
 */