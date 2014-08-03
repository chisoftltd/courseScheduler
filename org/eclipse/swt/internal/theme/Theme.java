package org.eclipse.swt.internal.theme;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

public class Theme
{
  Device device;

  public Theme(Device paramDevice)
  {
    this.device = paramDevice;
  }

  public Rectangle computeTrim(GC paramGC, DrawData paramDrawData)
  {
    checkTheme();
    if (paramGC == null)
      SWT.error(4);
    if (paramDrawData == null)
      SWT.error(4);
    if (paramGC.isDisposed())
      SWT.error(5);
    return paramDrawData.computeTrim(this, paramGC);
  }

  void checkTheme()
  {
    if (isDisposed())
      SWT.error(44);
  }

  public void dispose()
  {
    this.device = null;
  }

  public void drawBackground(GC paramGC, Rectangle paramRectangle, DrawData paramDrawData)
  {
    checkTheme();
    if (paramGC == null)
      SWT.error(4);
    if (paramRectangle == null)
      SWT.error(4);
    if (paramDrawData == null)
      SWT.error(4);
    if (paramGC.isDisposed())
      SWT.error(5);
    paramDrawData.draw(this, paramGC, paramRectangle);
  }

  public void drawFocus(GC paramGC, Rectangle paramRectangle, DrawData paramDrawData)
  {
    checkTheme();
    if (paramGC == null)
      SWT.error(4);
    if (paramRectangle == null)
      SWT.error(4);
    if (paramDrawData == null)
      SWT.error(4);
    if (paramGC.isDisposed())
      SWT.error(5);
    paramGC.drawFocus(paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height);
  }

  public void drawImage(GC paramGC, Rectangle paramRectangle, DrawData paramDrawData, Image paramImage, int paramInt)
  {
    checkTheme();
    if (paramGC == null)
      SWT.error(4);
    if (paramRectangle == null)
      SWT.error(4);
    if (paramDrawData == null)
      SWT.error(4);
    if (paramImage == null)
      SWT.error(4);
    if (paramGC.isDisposed())
      SWT.error(5);
    paramDrawData.drawImage(this, paramImage, paramGC, paramRectangle);
  }

  public void drawText(GC paramGC, Rectangle paramRectangle, DrawData paramDrawData, String paramString, int paramInt)
  {
    checkTheme();
    if (paramGC == null)
      SWT.error(4);
    if (paramRectangle == null)
      SWT.error(4);
    if (paramDrawData == null)
      SWT.error(4);
    if (paramString == null)
      SWT.error(4);
    if (paramGC.isDisposed())
      SWT.error(5);
    paramDrawData.drawText(this, paramString, paramInt, paramGC, paramRectangle);
  }

  public Rectangle getBounds(int paramInt, Rectangle paramRectangle, DrawData paramDrawData)
  {
    checkTheme();
    if (paramRectangle == null)
      SWT.error(4);
    if (paramDrawData == null)
      SWT.error(4);
    return paramDrawData.getBounds(paramInt, paramRectangle);
  }

  public int getSelection(Point paramPoint, Rectangle paramRectangle, RangeDrawData paramRangeDrawData)
  {
    checkTheme();
    if (paramPoint == null)
      SWT.error(4);
    if (paramRectangle == null)
      SWT.error(4);
    if (paramRangeDrawData == null)
      SWT.error(4);
    return paramRangeDrawData.getSelection(paramPoint, paramRectangle);
  }

  public int hitBackground(Point paramPoint, Rectangle paramRectangle, DrawData paramDrawData)
  {
    checkTheme();
    if (paramPoint == null)
      SWT.error(4);
    if (paramRectangle == null)
      SWT.error(4);
    if (paramDrawData == null)
      SWT.error(4);
    return paramDrawData.hit(this, paramPoint, paramRectangle);
  }

  public boolean isDisposed()
  {
    return this.device == null;
  }

  public Rectangle measureText(GC paramGC, Rectangle paramRectangle, DrawData paramDrawData, String paramString, int paramInt)
  {
    checkTheme();
    if (paramGC == null)
      SWT.error(4);
    if (paramDrawData == null)
      SWT.error(4);
    if (paramString == null)
      SWT.error(4);
    if (paramGC.isDisposed())
      SWT.error(5);
    return paramDrawData.measureText(this, paramString, paramInt, paramGC, paramRectangle);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.theme.Theme
 * JD-Core Version:    0.6.2
 */