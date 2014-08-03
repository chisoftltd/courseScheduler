package org.eclipse.swt.graphics;

import org.eclipse.swt.SWT;
import org.eclipse.swt.internal.gdip.Gdip;
import org.eclipse.swt.internal.gdip.PointF;
import org.eclipse.swt.internal.gdip.RectF;
import org.eclipse.swt.internal.win32.OS;

public class Path extends Resource
{
  public int handle;
  PointF currentPoint = new PointF();
  PointF startPoint = new PointF();

  public Path(Device paramDevice)
  {
    super(paramDevice);
    this.device.checkGDIP();
    this.handle = Gdip.GraphicsPath_new(0);
    if (this.handle == 0)
      SWT.error(2);
    init();
  }

  public Path(Device paramDevice, Path paramPath, float paramFloat)
  {
    super(paramDevice);
    if (paramPath == null)
      SWT.error(4);
    if (paramPath.isDisposed())
      SWT.error(5);
    paramFloat = Math.max(0.0F, paramFloat);
    this.handle = Gdip.GraphicsPath_Clone(paramPath.handle);
    if (paramFloat != 0.0F)
      Gdip.GraphicsPath_Flatten(this.handle, 0, paramFloat);
    if (this.handle == 0)
      SWT.error(2);
    init();
  }

  public Path(Device paramDevice, PathData paramPathData)
  {
    this(paramDevice);
    if (paramPathData == null)
      SWT.error(4);
    init(paramPathData);
  }

  public void addArc(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    if (isDisposed())
      SWT.error(44);
    if (paramFloat3 < 0.0F)
    {
      paramFloat1 += paramFloat3;
      paramFloat3 = -paramFloat3;
    }
    if (paramFloat4 < 0.0F)
    {
      paramFloat2 += paramFloat4;
      paramFloat4 = -paramFloat4;
    }
    if ((paramFloat3 == 0.0F) || (paramFloat4 == 0.0F) || (paramFloat6 == 0.0F))
      return;
    if (paramFloat3 == paramFloat4)
    {
      Gdip.GraphicsPath_AddArc(this.handle, paramFloat1, paramFloat2, paramFloat3, paramFloat4, -paramFloat5, -paramFloat6);
    }
    else
    {
      int i = Gdip.GraphicsPath_new(0);
      if (i == 0)
        SWT.error(2);
      int j = Gdip.Matrix_new(paramFloat3, 0.0F, 0.0F, paramFloat4, paramFloat1, paramFloat2);
      if (j == 0)
        SWT.error(2);
      Gdip.GraphicsPath_AddArc(i, 0.0F, 0.0F, 1.0F, 1.0F, -paramFloat5, -paramFloat6);
      Gdip.GraphicsPath_Transform(i, j);
      Gdip.GraphicsPath_AddPath(this.handle, i, true);
      Gdip.Matrix_delete(j);
      Gdip.GraphicsPath_delete(i);
    }
    Gdip.GraphicsPath_GetLastPoint(this.handle, this.currentPoint);
  }

  public void addPath(Path paramPath)
  {
    if (isDisposed())
      SWT.error(44);
    if (paramPath == null)
      SWT.error(4);
    if (paramPath.isDisposed())
      SWT.error(5);
    Gdip.GraphicsPath_AddPath(this.handle, paramPath.handle, false);
    this.currentPoint.X = paramPath.currentPoint.X;
    this.currentPoint.Y = paramPath.currentPoint.Y;
  }

  public void addRectangle(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    if (isDisposed())
      SWT.error(44);
    RectF localRectF = new RectF();
    localRectF.X = paramFloat1;
    localRectF.Y = paramFloat2;
    localRectF.Width = paramFloat3;
    localRectF.Height = paramFloat4;
    Gdip.GraphicsPath_AddRectangle(this.handle, localRectF);
    this.currentPoint.X = paramFloat1;
    this.currentPoint.Y = paramFloat2;
  }

  public void addString(String paramString, float paramFloat1, float paramFloat2, Font paramFont)
  {
    if (isDisposed())
      SWT.error(44);
    if (paramFont == null)
      SWT.error(4);
    if (paramFont.isDisposed())
      SWT.error(5);
    int i = paramString.length();
    char[] arrayOfChar = new char[i];
    paramString.getChars(0, i, arrayOfChar, 0);
    int j = this.device.internal_new_GC(null);
    int[] arrayOfInt = new int[1];
    int k = GC.createGdipFont(j, paramFont.handle, 0, this.device.fontCollection, arrayOfInt, null);
    PointF localPointF = new PointF();
    localPointF.X = (paramFloat1 - Gdip.Font_GetSize(k) / 6.0F);
    localPointF.Y = paramFloat2;
    int m = Gdip.Font_GetStyle(k);
    float f = Gdip.Font_GetSize(k);
    Gdip.GraphicsPath_AddString(this.handle, arrayOfChar, i, arrayOfInt[0], m, f, localPointF, 0);
    Gdip.GraphicsPath_GetLastPoint(this.handle, this.currentPoint);
    Gdip.FontFamily_delete(arrayOfInt[0]);
    Gdip.Font_delete(k);
    this.device.internal_dispose_GC(j, null);
  }

  public void close()
  {
    if (isDisposed())
      SWT.error(44);
    Gdip.GraphicsPath_CloseFigure(this.handle);
    this.currentPoint.X = this.startPoint.X;
    this.currentPoint.Y = this.startPoint.Y;
  }

  public boolean contains(float paramFloat1, float paramFloat2, GC paramGC, boolean paramBoolean)
  {
    if (isDisposed())
      SWT.error(44);
    if (paramGC == null)
      SWT.error(4);
    if (paramGC.isDisposed())
      SWT.error(5);
    paramGC.initGdip();
    paramGC.checkGC(120);
    int i = OS.GetPolyFillMode(paramGC.handle) == 2 ? 1 : 0;
    Gdip.GraphicsPath_SetFillMode(this.handle, i);
    if (paramBoolean)
      return Gdip.GraphicsPath_IsOutlineVisible(this.handle, paramFloat1, paramFloat2, paramGC.data.gdipPen, paramGC.data.gdipGraphics);
    return Gdip.GraphicsPath_IsVisible(this.handle, paramFloat1, paramFloat2, paramGC.data.gdipGraphics);
  }

  public void cubicTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    if (isDisposed())
      SWT.error(44);
    Gdip.GraphicsPath_AddBezier(this.handle, this.currentPoint.X, this.currentPoint.Y, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6);
    Gdip.GraphicsPath_GetLastPoint(this.handle, this.currentPoint);
  }

  void destroy()
  {
    Gdip.GraphicsPath_delete(this.handle);
    this.handle = 0;
  }

  public void getBounds(float[] paramArrayOfFloat)
  {
    if (isDisposed())
      SWT.error(44);
    if (paramArrayOfFloat == null)
      SWT.error(4);
    if (paramArrayOfFloat.length < 4)
      SWT.error(5);
    RectF localRectF = new RectF();
    Gdip.GraphicsPath_GetBounds(this.handle, localRectF, 0, 0);
    paramArrayOfFloat[0] = localRectF.X;
    paramArrayOfFloat[1] = localRectF.Y;
    paramArrayOfFloat[2] = localRectF.Width;
    paramArrayOfFloat[3] = localRectF.Height;
  }

  public void getCurrentPoint(float[] paramArrayOfFloat)
  {
    if (isDisposed())
      SWT.error(44);
    if (paramArrayOfFloat == null)
      SWT.error(4);
    if (paramArrayOfFloat.length < 2)
      SWT.error(5);
    paramArrayOfFloat[0] = this.currentPoint.X;
    paramArrayOfFloat[1] = this.currentPoint.Y;
  }

  public PathData getPathData()
  {
    if (isDisposed())
      SWT.error(44);
    int i = Gdip.GraphicsPath_GetPointCount(this.handle);
    byte[] arrayOfByte = new byte[i];
    float[] arrayOfFloat = new float[i * 2];
    Gdip.GraphicsPath_GetPathTypes(this.handle, arrayOfByte, i);
    Gdip.GraphicsPath_GetPathPoints(this.handle, arrayOfFloat, i);
    Object localObject1 = new byte[i * 2];
    int j = 0;
    int k = 0;
    while (j < i)
    {
      int m = arrayOfByte[j];
      int n = 0;
      switch (m & 0x7)
      {
      case 0:
        localObject1[(k++)] = 1;
        n = (m & 0x80) != 0 ? 1 : 0;
        j++;
        break;
      case 1:
        localObject1[(k++)] = 2;
        n = (m & 0x80) != 0 ? 1 : 0;
        j++;
        break;
      case 3:
        localObject1[(k++)] = 4;
        n = (arrayOfByte[(j + 2)] & 0x80) != 0 ? 1 : 0;
        j += 3;
        break;
      case 2:
      default:
        j++;
      }
      if (n != 0)
        localObject1[(k++)] = 5;
    }
    if (k != localObject1.length)
    {
      localObject2 = new byte[k];
      System.arraycopy(localObject1, 0, localObject2, 0, k);
      localObject1 = localObject2;
    }
    Object localObject2 = new PathData();
    ((PathData)localObject2).types = ((byte[])localObject1);
    ((PathData)localObject2).points = arrayOfFloat;
    return localObject2;
  }

  public void lineTo(float paramFloat1, float paramFloat2)
  {
    if (isDisposed())
      SWT.error(44);
    Gdip.GraphicsPath_AddLine(this.handle, this.currentPoint.X, this.currentPoint.Y, paramFloat1, paramFloat2);
    Gdip.GraphicsPath_GetLastPoint(this.handle, this.currentPoint);
  }

  void init(PathData paramPathData)
  {
    byte[] arrayOfByte = paramPathData.types;
    float[] arrayOfFloat = paramPathData.points;
    int i = 0;
    int j = 0;
    while (i < arrayOfByte.length)
    {
      switch (arrayOfByte[i])
      {
      case 1:
        moveTo(arrayOfFloat[(j++)], arrayOfFloat[(j++)]);
        break;
      case 2:
        lineTo(arrayOfFloat[(j++)], arrayOfFloat[(j++)]);
        break;
      case 4:
        cubicTo(arrayOfFloat[(j++)], arrayOfFloat[(j++)], arrayOfFloat[(j++)], arrayOfFloat[(j++)], arrayOfFloat[(j++)], arrayOfFloat[(j++)]);
        break;
      case 3:
        quadTo(arrayOfFloat[(j++)], arrayOfFloat[(j++)], arrayOfFloat[(j++)], arrayOfFloat[(j++)]);
        break;
      case 5:
        close();
        break;
      default:
        dispose();
        SWT.error(5);
      }
      i++;
    }
  }

  public boolean isDisposed()
  {
    return this.handle == 0;
  }

  public void moveTo(float paramFloat1, float paramFloat2)
  {
    if (isDisposed())
      SWT.error(44);
    Gdip.GraphicsPath_StartFigure(this.handle);
    this.currentPoint.X = (this.startPoint.X = paramFloat1);
    this.currentPoint.Y = (this.startPoint.Y = paramFloat2);
  }

  public void quadTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    if (isDisposed())
      SWT.error(44);
    float f1 = this.currentPoint.X + 2.0F * (paramFloat1 - this.currentPoint.X) / 3.0F;
    float f2 = this.currentPoint.Y + 2.0F * (paramFloat2 - this.currentPoint.Y) / 3.0F;
    float f3 = f1 + (paramFloat3 - this.currentPoint.X) / 3.0F;
    float f4 = f2 + (paramFloat4 - this.currentPoint.Y) / 3.0F;
    Gdip.GraphicsPath_AddBezier(this.handle, this.currentPoint.X, this.currentPoint.Y, f1, f2, f3, f4, paramFloat3, paramFloat4);
    Gdip.GraphicsPath_GetLastPoint(this.handle, this.currentPoint);
  }

  public String toString()
  {
    if (isDisposed())
      return "Path {*DISPOSED*}";
    return "Path {" + this.handle + "}";
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.graphics.Path
 * JD-Core Version:    0.6.2
 */