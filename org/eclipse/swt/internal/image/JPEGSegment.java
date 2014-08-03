package org.eclipse.swt.internal.image;

class JPEGSegment
{
  public byte[] reference;

  JPEGSegment()
  {
  }

  public JPEGSegment(byte[] paramArrayOfByte)
  {
    this.reference = paramArrayOfByte;
  }

  public int signature()
  {
    return 0;
  }

  public boolean verify()
  {
    return getSegmentMarker() == signature();
  }

  public int getSegmentMarker()
  {
    return (this.reference[0] & 0xFF) << 8 | this.reference[1] & 0xFF;
  }

  public void setSegmentMarker(int paramInt)
  {
    this.reference[0] = ((byte)((paramInt & 0xFF00) >> 8));
    this.reference[1] = ((byte)(paramInt & 0xFF));
  }

  public int getSegmentLength()
  {
    return (this.reference[2] & 0xFF) << 8 | this.reference[3] & 0xFF;
  }

  public void setSegmentLength(int paramInt)
  {
    this.reference[2] = ((byte)((paramInt & 0xFF00) >> 8));
    this.reference[3] = ((byte)(paramInt & 0xFF));
  }

  public boolean writeToStream(LEDataOutputStream paramLEDataOutputStream)
  {
    try
    {
      paramLEDataOutputStream.write(this.reference);
      return true;
    }
    catch (Exception localException)
    {
    }
    return false;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.image.JPEGSegment
 * JD-Core Version:    0.6.2
 */