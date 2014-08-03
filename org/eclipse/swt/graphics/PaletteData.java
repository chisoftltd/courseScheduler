package org.eclipse.swt.graphics;

import org.eclipse.swt.SWT;

public final class PaletteData
{
  public boolean isDirect;
  public RGB[] colors;
  public int redMask;
  public int greenMask;
  public int blueMask;
  public int redShift;
  public int greenShift;
  public int blueShift;

  public PaletteData(RGB[] paramArrayOfRGB)
  {
    if (paramArrayOfRGB == null)
      SWT.error(4);
    this.colors = paramArrayOfRGB;
    this.isDirect = false;
  }

  public PaletteData(int paramInt1, int paramInt2, int paramInt3)
  {
    this.redMask = paramInt1;
    this.greenMask = paramInt2;
    this.blueMask = paramInt3;
    this.isDirect = true;
    this.redShift = shiftForMask(paramInt1);
    this.greenShift = shiftForMask(paramInt2);
    this.blueShift = shiftForMask(paramInt3);
  }

  public int getPixel(RGB paramRGB)
  {
    if (paramRGB == null)
      SWT.error(4);
    if (this.isDirect)
    {
      i = 0;
      i |= (this.redShift < 0 ? paramRGB.red << -this.redShift : paramRGB.red >>> this.redShift) & this.redMask;
      i |= (this.greenShift < 0 ? paramRGB.green << -this.greenShift : paramRGB.green >>> this.greenShift) & this.greenMask;
      i |= (this.blueShift < 0 ? paramRGB.blue << -this.blueShift : paramRGB.blue >>> this.blueShift) & this.blueMask;
      return i;
    }
    for (int i = 0; i < this.colors.length; i++)
      if (this.colors[i].equals(paramRGB))
        return i;
    SWT.error(5);
    return 0;
  }

  public RGB getRGB(int paramInt)
  {
    if (this.isDirect)
    {
      int i = paramInt & this.redMask;
      i = this.redShift < 0 ? i >>> -this.redShift : i << this.redShift;
      int j = paramInt & this.greenMask;
      j = this.greenShift < 0 ? j >>> -this.greenShift : j << this.greenShift;
      int k = paramInt & this.blueMask;
      k = this.blueShift < 0 ? k >>> -this.blueShift : k << this.blueShift;
      return new RGB(i, j, k);
    }
    if ((paramInt < 0) || (paramInt >= this.colors.length))
      SWT.error(5);
    return this.colors[paramInt];
  }

  public RGB[] getRGBs()
  {
    return this.colors;
  }

  int shiftForMask(int paramInt)
  {
    for (int i = 31; i >= 0; i--)
      if ((paramInt >> i & 0x1) != 0)
        return 7 - i;
    return 32;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.graphics.PaletteData
 * JD-Core Version:    0.6.2
 */