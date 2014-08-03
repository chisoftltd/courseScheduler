package org.eclipse.swt.graphics;

import org.eclipse.swt.SWT;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;

public final class Region extends Resource
{
  public int handle;

  public Region()
  {
    this(null);
  }

  public Region(Device paramDevice)
  {
    super(paramDevice);
    this.handle = OS.CreateRectRgn(0, 0, 0, 0);
    if (this.handle == 0)
      SWT.error(2);
    init();
  }

  Region(Device paramDevice, int paramInt)
  {
    super(paramDevice);
    this.handle = paramInt;
  }

  public void add(int[] paramArrayOfInt)
  {
    if (isDisposed())
      SWT.error(44);
    if (paramArrayOfInt == null)
      SWT.error(4);
    if (OS.IsWinCE)
      SWT.error(20);
    int i = OS.CreatePolygonRgn(paramArrayOfInt, paramArrayOfInt.length / 2, 1);
    OS.CombineRgn(this.handle, this.handle, i, 2);
    OS.DeleteObject(i);
  }

  public void add(Rectangle paramRectangle)
  {
    if (isDisposed())
      SWT.error(44);
    if (paramRectangle == null)
      SWT.error(4);
    add(paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height);
  }

  public void add(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (isDisposed())
      SWT.error(44);
    if ((paramInt3 < 0) || (paramInt4 < 0))
      SWT.error(5);
    int i = OS.CreateRectRgn(paramInt1, paramInt2, paramInt1 + paramInt3, paramInt2 + paramInt4);
    OS.CombineRgn(this.handle, this.handle, i, 2);
    OS.DeleteObject(i);
  }

  public void add(Region paramRegion)
  {
    if (isDisposed())
      SWT.error(44);
    if (paramRegion == null)
      SWT.error(4);
    if (paramRegion.isDisposed())
      SWT.error(5);
    OS.CombineRgn(this.handle, this.handle, paramRegion.handle, 2);
  }

  public boolean contains(int paramInt1, int paramInt2)
  {
    if (isDisposed())
      SWT.error(44);
    return OS.PtInRegion(this.handle, paramInt1, paramInt2);
  }

  public boolean contains(Point paramPoint)
  {
    if (isDisposed())
      SWT.error(44);
    if (paramPoint == null)
      SWT.error(4);
    return contains(paramPoint.x, paramPoint.y);
  }

  void destroy()
  {
    OS.DeleteObject(this.handle);
    this.handle = 0;
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject)
      return true;
    if (!(paramObject instanceof Region))
      return false;
    Region localRegion = (Region)paramObject;
    return this.handle == localRegion.handle;
  }

  public Rectangle getBounds()
  {
    if (isDisposed())
      SWT.error(44);
    RECT localRECT = new RECT();
    OS.GetRgnBox(this.handle, localRECT);
    return new Rectangle(localRECT.left, localRECT.top, localRECT.right - localRECT.left, localRECT.bottom - localRECT.top);
  }

  public int hashCode()
  {
    return this.handle;
  }

  public void intersect(Rectangle paramRectangle)
  {
    if (isDisposed())
      SWT.error(44);
    if (paramRectangle == null)
      SWT.error(4);
    intersect(paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height);
  }

  public void intersect(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (isDisposed())
      SWT.error(44);
    if ((paramInt3 < 0) || (paramInt4 < 0))
      SWT.error(5);
    int i = OS.CreateRectRgn(paramInt1, paramInt2, paramInt1 + paramInt3, paramInt2 + paramInt4);
    OS.CombineRgn(this.handle, this.handle, i, 1);
    OS.DeleteObject(i);
  }

  public void intersect(Region paramRegion)
  {
    if (isDisposed())
      SWT.error(44);
    if (paramRegion == null)
      SWT.error(4);
    if (paramRegion.isDisposed())
      SWT.error(5);
    OS.CombineRgn(this.handle, this.handle, paramRegion.handle, 1);
  }

  public boolean intersects(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (isDisposed())
      SWT.error(44);
    RECT localRECT = new RECT();
    OS.SetRect(localRECT, paramInt1, paramInt2, paramInt1 + paramInt3, paramInt2 + paramInt4);
    return OS.RectInRegion(this.handle, localRECT);
  }

  public boolean intersects(Rectangle paramRectangle)
  {
    if (isDisposed())
      SWT.error(44);
    if (paramRectangle == null)
      SWT.error(4);
    return intersects(paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height);
  }

  public boolean isDisposed()
  {
    return this.handle == 0;
  }

  public boolean isEmpty()
  {
    if (isDisposed())
      SWT.error(44);
    RECT localRECT = new RECT();
    int i = OS.GetRgnBox(this.handle, localRECT);
    if (i == 1)
      return true;
    return (localRECT.right - localRECT.left <= 0) || (localRECT.bottom - localRECT.top <= 0);
  }

  public void subtract(int[] paramArrayOfInt)
  {
    if (isDisposed())
      SWT.error(44);
    if (paramArrayOfInt == null)
      SWT.error(4);
    if (OS.IsWinCE)
      SWT.error(20);
    int i = OS.CreatePolygonRgn(paramArrayOfInt, paramArrayOfInt.length / 2, 1);
    OS.CombineRgn(this.handle, this.handle, i, 4);
    OS.DeleteObject(i);
  }

  public void subtract(Rectangle paramRectangle)
  {
    if (isDisposed())
      SWT.error(44);
    if (paramRectangle == null)
      SWT.error(4);
    subtract(paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height);
  }

  public void subtract(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (isDisposed())
      SWT.error(44);
    if ((paramInt3 < 0) || (paramInt4 < 0))
      SWT.error(5);
    int i = OS.CreateRectRgn(paramInt1, paramInt2, paramInt1 + paramInt3, paramInt2 + paramInt4);
    OS.CombineRgn(this.handle, this.handle, i, 4);
    OS.DeleteObject(i);
  }

  public void subtract(Region paramRegion)
  {
    if (isDisposed())
      SWT.error(44);
    if (paramRegion == null)
      SWT.error(4);
    if (paramRegion.isDisposed())
      SWT.error(5);
    OS.CombineRgn(this.handle, this.handle, paramRegion.handle, 4);
  }

  public void translate(int paramInt1, int paramInt2)
  {
    if (isDisposed())
      SWT.error(44);
    OS.OffsetRgn(this.handle, paramInt1, paramInt2);
  }

  public void translate(Point paramPoint)
  {
    if (isDisposed())
      SWT.error(44);
    if (paramPoint == null)
      SWT.error(4);
    translate(paramPoint.x, paramPoint.y);
  }

  public String toString()
  {
    if (isDisposed())
      return "Region {*DISPOSED*}";
    return "Region {" + this.handle + "}";
  }

  public static Region win32_new(Device paramDevice, int paramInt)
  {
    return new Region(paramDevice, paramInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.graphics.Region
 * JD-Core Version:    0.6.2
 */