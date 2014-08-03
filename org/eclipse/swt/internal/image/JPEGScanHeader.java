package org.eclipse.swt.internal.image;

import org.eclipse.swt.SWT;

final class JPEGScanHeader extends JPEGVariableSizeSegment
{
  public int[][] componentParameters;

  public JPEGScanHeader(byte[] paramArrayOfByte)
  {
    super(paramArrayOfByte);
  }

  public JPEGScanHeader(LEDataInputStream paramLEDataInputStream)
  {
    super(paramLEDataInputStream);
    initializeComponentParameters();
  }

  public int getApproxBitPositionHigh()
  {
    return this.reference[(2 * getNumberOfImageComponents() + 7)] >> 4;
  }

  public int getApproxBitPositionLow()
  {
    return this.reference[(2 * getNumberOfImageComponents() + 7)] & 0xF;
  }

  public int getEndOfSpectralSelection()
  {
    return this.reference[(2 * getNumberOfImageComponents() + 6)];
  }

  public int getNumberOfImageComponents()
  {
    return this.reference[4];
  }

  public int getStartOfSpectralSelection()
  {
    return this.reference[(2 * getNumberOfImageComponents() + 5)];
  }

  void initializeComponentParameters()
  {
    int i = getNumberOfImageComponents();
    this.componentParameters = new int[0][];
    for (int j = 0; j < i; j++)
    {
      int k = 5 + j * 2;
      int m = this.reference[k] & 0xFF;
      int n = (this.reference[(k + 1)] & 0xFF) >> 4;
      int i1 = this.reference[(k + 1)] & 0xF;
      if (this.componentParameters.length <= m)
      {
        int[][] arrayOfInt = new int[m + 1][];
        System.arraycopy(this.componentParameters, 0, arrayOfInt, 0, this.componentParameters.length);
        this.componentParameters = arrayOfInt;
      }
      this.componentParameters[m] = { n, i1 };
    }
  }

  public void initializeContents()
  {
    int i = getNumberOfImageComponents();
    int[][] arrayOfInt = this.componentParameters;
    if ((i == 0) || (i != arrayOfInt.length))
      SWT.error(40);
    for (int j = 0; j < i; j++)
    {
      int k = j * 2 + 5;
      int[] arrayOfInt1 = arrayOfInt[j];
      this.reference[k] = ((byte)(j + 1));
      this.reference[(k + 1)] = ((byte)(arrayOfInt1[0] * 16 + arrayOfInt1[1]));
    }
  }

  public void setEndOfSpectralSelection(int paramInt)
  {
    this.reference[(2 * getNumberOfImageComponents() + 6)] = ((byte)paramInt);
  }

  public void setNumberOfImageComponents(int paramInt)
  {
    this.reference[4] = ((byte)(paramInt & 0xFF));
  }

  public void setStartOfSpectralSelection(int paramInt)
  {
    this.reference[(2 * getNumberOfImageComponents() + 5)] = ((byte)paramInt);
  }

  public int signature()
  {
    return 65498;
  }

  public boolean verifyProgressiveScan()
  {
    int i = getStartOfSpectralSelection();
    int j = getEndOfSpectralSelection();
    int k = getApproxBitPositionLow();
    int m = getApproxBitPositionHigh();
    int n = getNumberOfImageComponents();
    if (((i == 0) && (j == 0)) || ((i <= j) && (j <= 63) && (k <= 13) && (m <= 13) && ((m == 0) || (m == k + 1))))
      return (i == 0) || ((i > 0) && (n == 1));
    return false;
  }

  public boolean isACProgressiveScan()
  {
    return (getStartOfSpectralSelection() != 0) && (getEndOfSpectralSelection() != 0);
  }

  public boolean isDCProgressiveScan()
  {
    return (getStartOfSpectralSelection() == 0) && (getEndOfSpectralSelection() == 0);
  }

  public boolean isFirstScan()
  {
    return getApproxBitPositionHigh() == 0;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.image.JPEGScanHeader
 * JD-Core Version:    0.6.2
 */