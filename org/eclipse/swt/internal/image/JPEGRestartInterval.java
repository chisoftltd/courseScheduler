package org.eclipse.swt.internal.image;

final class JPEGRestartInterval extends JPEGFixedSizeSegment
{
  public JPEGRestartInterval(LEDataInputStream paramLEDataInputStream)
  {
    super(paramLEDataInputStream);
  }

  public int signature()
  {
    return 65501;
  }

  public int getRestartInterval()
  {
    return (this.reference[4] & 0xFF) << 8 | this.reference[5] & 0xFF;
  }

  public int fixedSize()
  {
    return 6;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.image.JPEGRestartInterval
 * JD-Core Version:    0.6.2
 */