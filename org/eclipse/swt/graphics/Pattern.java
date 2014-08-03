package org.eclipse.swt.graphics;

import org.eclipse.swt.SWT;
import org.eclipse.swt.internal.gdip.Gdip;
import org.eclipse.swt.internal.gdip.PointF;
import org.eclipse.swt.internal.win32.OS;

public class Pattern extends Resource
{
  public int handle;

  public Pattern(Device paramDevice, Image paramImage)
  {
    super(paramDevice);
    if (paramImage == null)
      SWT.error(4);
    if (paramImage.isDisposed())
      SWT.error(5);
    this.device.checkGDIP();
    int[] arrayOfInt = paramImage.createGdipImage();
    int i = arrayOfInt[0];
    int j = Gdip.Image_GetWidth(i);
    int k = Gdip.Image_GetHeight(i);
    this.handle = Gdip.TextureBrush_new(i, 0, 0.0F, 0.0F, j, k);
    Gdip.Bitmap_delete(i);
    if (arrayOfInt[1] != 0)
    {
      int m = OS.GetProcessHeap();
      OS.HeapFree(m, 0, arrayOfInt[1]);
    }
    if (this.handle == 0)
      SWT.error(2);
    init();
  }

  public Pattern(Device paramDevice, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, Color paramColor1, Color paramColor2)
  {
    this(paramDevice, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramColor1, 255, paramColor2, 255);
  }

  public Pattern(Device paramDevice, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, Color paramColor1, int paramInt1, Color paramColor2, int paramInt2)
  {
    super(paramDevice);
    if (paramColor1 == null)
      SWT.error(4);
    if (paramColor1.isDisposed())
      SWT.error(5);
    if (paramColor2 == null)
      SWT.error(4);
    if (paramColor2.isDisposed())
      SWT.error(5);
    this.device.checkGDIP();
    int i = paramColor1.handle;
    int j = i >> 16 & 0xFF | i & 0xFF00 | (i & 0xFF) << 16;
    int k = Gdip.Color_new((paramInt1 & 0xFF) << 24 | j);
    if ((paramFloat1 == paramFloat3) && (paramFloat2 == paramFloat4))
    {
      this.handle = Gdip.SolidBrush_new(k);
      if (this.handle == 0)
        SWT.error(2);
    }
    else
    {
      int m = paramColor2.handle;
      j = m >> 16 & 0xFF | m & 0xFF00 | (m & 0xFF) << 16;
      int n = Gdip.Color_new((paramInt2 & 0xFF) << 24 | j);
      PointF localPointF1 = new PointF();
      localPointF1.X = paramFloat1;
      localPointF1.Y = paramFloat2;
      PointF localPointF2 = new PointF();
      localPointF2.X = paramFloat3;
      localPointF2.Y = paramFloat4;
      this.handle = Gdip.LinearGradientBrush_new(localPointF1, localPointF2, k, n);
      if (this.handle == 0)
        SWT.error(2);
      if ((paramInt1 != 255) || (paramInt2 != 255))
      {
        int i1 = (int)((paramInt1 & 0xFF) * 0.5F + (paramInt2 & 0xFF) * 0.5F);
        int i2 = (int)(((i & 0xFF) >> 0) * 0.5F + ((m & 0xFF) >> 0) * 0.5F);
        int i3 = (int)(((i & 0xFF00) >> 8) * 0.5F + ((m & 0xFF00) >> 8) * 0.5F);
        int i4 = (int)(((i & 0xFF0000) >> 16) * 0.5F + ((m & 0xFF0000) >> 16) * 0.5F);
        int i5 = Gdip.Color_new(i1 << 24 | i2 << 16 | i3 << 8 | i4);
        Gdip.LinearGradientBrush_SetInterpolationColors(this.handle, new int[] { k, i5, n }, new float[] { 0.0F, 0.5F, 1.0F }, 3);
        Gdip.Color_delete(i5);
      }
      Gdip.Color_delete(n);
    }
    Gdip.Color_delete(k);
    init();
  }

  void destroy()
  {
    int i = Gdip.Brush_GetType(this.handle);
    switch (i)
    {
    case 0:
      Gdip.SolidBrush_delete(this.handle);
      break;
    case 1:
      Gdip.HatchBrush_delete(this.handle);
      break;
    case 4:
      Gdip.LinearGradientBrush_delete(this.handle);
      break;
    case 2:
      Gdip.TextureBrush_delete(this.handle);
    case 3:
    }
    this.handle = 0;
  }

  public boolean isDisposed()
  {
    return this.handle == 0;
  }

  public String toString()
  {
    if (isDisposed())
      return "Pattern {*DISPOSED*}";
    return "Pattern {" + this.handle + "}";
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.graphics.Pattern
 * JD-Core Version:    0.6.2
 */