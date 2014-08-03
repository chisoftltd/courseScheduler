package org.eclipse.swt.internal.image;

import org.eclipse.swt.SWT;

abstract class JPEGVariableSizeSegment extends JPEGSegment
{
  public JPEGVariableSizeSegment(byte[] paramArrayOfByte)
  {
    super(paramArrayOfByte);
  }

  public JPEGVariableSizeSegment(LEDataInputStream paramLEDataInputStream)
  {
    try
    {
      byte[] arrayOfByte1 = new byte[4];
      paramLEDataInputStream.read(arrayOfByte1);
      this.reference = arrayOfByte1;
      byte[] arrayOfByte2 = new byte[getSegmentLength() + 2];
      arrayOfByte2[0] = arrayOfByte1[0];
      arrayOfByte2[1] = arrayOfByte1[1];
      arrayOfByte2[2] = arrayOfByte1[2];
      arrayOfByte2[3] = arrayOfByte1[3];
      paramLEDataInputStream.read(arrayOfByte2, 4, arrayOfByte2.length - 4);
      this.reference = arrayOfByte2;
    }
    catch (Exception localException)
    {
      SWT.error(39, localException);
    }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.image.JPEGVariableSizeSegment
 * JD-Core Version:    0.6.2
 */