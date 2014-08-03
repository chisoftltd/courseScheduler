package org.eclipse.swt.internal.image;

import org.eclipse.swt.SWT;

final class JPEGFrameHeader extends JPEGVariableSizeSegment
{
  int maxVFactor;
  int maxHFactor;
  public int[] componentIdentifiers;
  public int[][] componentParameters;

  public JPEGFrameHeader(byte[] paramArrayOfByte)
  {
    super(paramArrayOfByte);
  }

  public JPEGFrameHeader(LEDataInputStream paramLEDataInputStream)
  {
    super(paramLEDataInputStream);
    initializeComponentParameters();
  }

  public int getSamplePrecision()
  {
    return this.reference[4] & 0xFF;
  }

  public int getNumberOfLines()
  {
    return (this.reference[5] & 0xFF) << 8 | this.reference[6] & 0xFF;
  }

  public int getSamplesPerLine()
  {
    return (this.reference[7] & 0xFF) << 8 | this.reference[8] & 0xFF;
  }

  public int getNumberOfImageComponents()
  {
    return this.reference[9] & 0xFF;
  }

  public void setSamplePrecision(int paramInt)
  {
    this.reference[4] = ((byte)(paramInt & 0xFF));
  }

  public void setNumberOfLines(int paramInt)
  {
    this.reference[5] = ((byte)((paramInt & 0xFF00) >> 8));
    this.reference[6] = ((byte)(paramInt & 0xFF));
  }

  public void setSamplesPerLine(int paramInt)
  {
    this.reference[7] = ((byte)((paramInt & 0xFF00) >> 8));
    this.reference[8] = ((byte)(paramInt & 0xFF));
  }

  public void setNumberOfImageComponents(int paramInt)
  {
    this.reference[9] = ((byte)(paramInt & 0xFF));
  }

  public int getMaxHFactor()
  {
    return this.maxHFactor;
  }

  public int getMaxVFactor()
  {
    return this.maxVFactor;
  }

  public void setMaxHFactor(int paramInt)
  {
    this.maxHFactor = paramInt;
  }

  public void setMaxVFactor(int paramInt)
  {
    this.maxVFactor = paramInt;
  }

  void initializeComponentParameters()
  {
    int i = getNumberOfImageComponents();
    this.componentIdentifiers = new int[i];
    Object localObject1 = new int[0][];
    int j = 1;
    int k = 1;
    int i4;
    for (int m = 0; m < i; m++)
    {
      n = m * 3 + 10;
      int i1 = this.reference[n] & 0xFF;
      this.componentIdentifiers[m] = i1;
      i2 = (this.reference[(n + 1)] & 0xFF) >> 4;
      int i3 = this.reference[(n + 1)] & 0xF;
      i4 = this.reference[(n + 2)] & 0xFF;
      if (i2 > j)
        j = i2;
      if (i3 > k)
        k = i3;
      int[] arrayOfInt2 = new int[5];
      arrayOfInt2[0] = i4;
      arrayOfInt2[1] = i2;
      arrayOfInt2[2] = i3;
      if (localObject1.length <= i1)
      {
        int[][] arrayOfInt = new int[i1 + 1][];
        System.arraycopy(localObject1, 0, arrayOfInt, 0, localObject1.length);
        localObject1 = arrayOfInt;
      }
      localObject1[i1] = arrayOfInt2;
    }
    m = getSamplesPerLine();
    int n = getNumberOfLines();
    int[] arrayOfInt1 = { 8, 16, 24, 32 };
    for (int i2 = 0; i2 < i; i2++)
    {
      Object localObject2 = localObject1[this.componentIdentifiers[i2]];
      i4 = localObject2[1];
      int i5 = localObject2[2];
      int i6 = (m * i4 + j - 1) / j;
      int i7 = (n * i5 + k - 1) / k;
      int i8 = roundUpToMultiple(i6, arrayOfInt1[(i4 - 1)]);
      int i9 = roundUpToMultiple(i7, arrayOfInt1[(i5 - 1)]);
      localObject2[3] = i8;
      localObject2[4] = i9;
    }
    setMaxHFactor(j);
    setMaxVFactor(k);
    this.componentParameters = ((int[][])localObject1);
  }

  public void initializeContents()
  {
    int i = getNumberOfImageComponents();
    if ((i == 0) || (i != this.componentParameters.length))
      SWT.error(40);
    int j = 0;
    int k = 0;
    int[][] arrayOfInt = this.componentParameters;
    for (int m = 0; m < i; m++)
    {
      n = m * 3 + 10;
      arrayOfInt1 = arrayOfInt[this.componentIdentifiers[m]];
      i1 = arrayOfInt1[1];
      int i2 = arrayOfInt1[2];
      if (i1 * i2 > 4)
        SWT.error(40);
      this.reference[n] = ((byte)(m + 1));
      this.reference[(n + 1)] = ((byte)(i1 * 16 + i2));
      this.reference[(n + 2)] = ((byte)arrayOfInt1[0]);
      if (i1 > j)
        j = i1;
      if (i2 > k)
        k = i2;
    }
    m = getSamplesPerLine();
    int n = getNumberOfLines();
    int[] arrayOfInt1 = { 8, 16, 24, 32 };
    for (int i1 = 0; i1 < i; i1++)
    {
      int[] arrayOfInt2 = arrayOfInt[this.componentIdentifiers[i1]];
      int i3 = arrayOfInt2[1];
      int i4 = arrayOfInt2[2];
      int i5 = (m * i3 + j - 1) / j;
      int i6 = (n * i4 + k - 1) / k;
      int i7 = roundUpToMultiple(i5, arrayOfInt1[(i3 - 1)]);
      int i8 = roundUpToMultiple(i6, arrayOfInt1[(i4 - 1)]);
      arrayOfInt2[3] = i7;
      arrayOfInt2[4] = i8;
    }
    setMaxHFactor(j);
    setMaxVFactor(k);
  }

  int roundUpToMultiple(int paramInt1, int paramInt2)
  {
    int i = paramInt1 + paramInt2 - 1;
    return i - i % paramInt2;
  }

  public boolean verify()
  {
    int i = getSegmentMarker();
    return ((i >= 65472) && (i <= 65475)) || ((i >= 65477) && (i <= 65479)) || ((i >= 65481) && (i <= 65483)) || ((i >= 65485) && (i <= 65487));
  }

  public boolean isProgressive()
  {
    int i = getSegmentMarker();
    return (i == 65474) || (i == 65478) || (i == 65482) || (i == 65486);
  }

  public boolean isArithmeticCoding()
  {
    return getSegmentMarker() >= 65481;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.image.JPEGFrameHeader
 * JD-Core Version:    0.6.2
 */