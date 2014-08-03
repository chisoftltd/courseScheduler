package org.eclipse.swt.graphics;

import org.eclipse.swt.SWT;
import org.eclipse.swt.internal.win32.OS;

public final class Color extends Resource
{
  public int handle;

  Color(Device paramDevice)
  {
    super(paramDevice);
  }

  public Color(Device paramDevice, int paramInt1, int paramInt2, int paramInt3)
  {
    super(paramDevice);
    init(paramInt1, paramInt2, paramInt3);
    init();
  }

  public Color(Device paramDevice, RGB paramRGB)
  {
    super(paramDevice);
    if (paramRGB == null)
      SWT.error(4);
    init(paramRGB.red, paramRGB.green, paramRGB.blue);
    init();
  }

  void destroy()
  {
    int i = this.device.hPalette;
    if (i != 0)
    {
      int j = OS.GetNearestPaletteIndex(i, this.handle);
      int[] arrayOfInt = this.device.colorRefCount;
      if (arrayOfInt[j] > 0)
        arrayOfInt[j] -= 1;
    }
    this.handle = -1;
  }

  public boolean equals(Object paramObject)
  {
    if (paramObject == this)
      return true;
    if (!(paramObject instanceof Color))
      return false;
    Color localColor = (Color)paramObject;
    return (this.device == localColor.device) && ((this.handle & 0xFFFFFF) == (localColor.handle & 0xFFFFFF));
  }

  public int getBlue()
  {
    if (isDisposed())
      SWT.error(44);
    return (this.handle & 0xFF0000) >> 16;
  }

  public int getGreen()
  {
    if (isDisposed())
      SWT.error(44);
    return (this.handle & 0xFF00) >> 8;
  }

  public int getRed()
  {
    if (isDisposed())
      SWT.error(44);
    return this.handle & 0xFF;
  }

  public RGB getRGB()
  {
    if (isDisposed())
      SWT.error(44);
    return new RGB(this.handle & 0xFF, (this.handle & 0xFF00) >> 8, (this.handle & 0xFF0000) >> 16);
  }

  public int hashCode()
  {
    return this.handle;
  }

  void init(int paramInt1, int paramInt2, int paramInt3)
  {
    if ((paramInt1 > 255) || (paramInt1 < 0) || (paramInt2 > 255) || (paramInt2 < 0) || (paramInt3 > 255) || (paramInt3 < 0))
      SWT.error(5);
    this.handle = (paramInt1 & 0xFF | (paramInt2 & 0xFF) << 8 | (paramInt3 & 0xFF) << 16);
    int i = this.device.hPalette;
    if (i == 0)
      return;
    int[] arrayOfInt = this.device.colorRefCount;
    int j = OS.GetNearestPaletteIndex(i, this.handle);
    byte[] arrayOfByte = new byte[4];
    OS.GetPaletteEntries(i, j, 1, arrayOfByte);
    if ((arrayOfByte[0] == (byte)paramInt1) && (arrayOfByte[1] == (byte)paramInt2) && (arrayOfByte[2] == (byte)paramInt3))
    {
      arrayOfInt[j] += 1;
      return;
    }
    for (int k = 0; k < arrayOfInt.length; k++)
      if (arrayOfInt[k] == 0)
      {
        j = k;
        break;
      }
    if (k == arrayOfInt.length)
    {
      this.handle = (arrayOfByte[0] & 0xFF | (arrayOfByte[1] & 0xFF) << 8 | (arrayOfByte[2] & 0xFF) << 16);
    }
    else
    {
      arrayOfByte = new byte[] { (byte)(paramInt1 & 0xFF), (byte)(paramInt2 & 0xFF), (byte)(paramInt3 & 0xFF) };
      OS.SetPaletteEntries(i, j, 1, arrayOfByte);
    }
    arrayOfInt[j] += 1;
  }

  public boolean isDisposed()
  {
    return this.handle == -1;
  }

  public String toString()
  {
    if (isDisposed())
      return "Color {*DISPOSED*}";
    return "Color {" + getRed() + ", " + getGreen() + ", " + getBlue() + "}";
  }

  public static Color win32_new(Device paramDevice, int paramInt)
  {
    Color localColor = new Color(paramDevice);
    localColor.handle = paramInt;
    return localColor;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.graphics.Color
 * JD-Core Version:    0.6.2
 */