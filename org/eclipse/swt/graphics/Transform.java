package org.eclipse.swt.graphics;

import org.eclipse.swt.SWT;
import org.eclipse.swt.internal.gdip.Gdip;

public class Transform extends Resource
{
  public int handle;

  public Transform(Device paramDevice)
  {
    this(paramDevice, 1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F);
  }

  public Transform(Device paramDevice, float[] paramArrayOfFloat)
  {
    this(paramDevice, checkTransform(paramArrayOfFloat)[0], paramArrayOfFloat[1], paramArrayOfFloat[2], paramArrayOfFloat[3], paramArrayOfFloat[4], paramArrayOfFloat[5]);
  }

  public Transform(Device paramDevice, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    super(paramDevice);
    this.device.checkGDIP();
    this.handle = Gdip.Matrix_new(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6);
    if (this.handle == 0)
      SWT.error(2);
    init();
  }

  static float[] checkTransform(float[] paramArrayOfFloat)
  {
    if (paramArrayOfFloat == null)
      SWT.error(4);
    if (paramArrayOfFloat.length < 6)
      SWT.error(5);
    return paramArrayOfFloat;
  }

  void destroy()
  {
    Gdip.Matrix_delete(this.handle);
    this.handle = 0;
  }

  public void getElements(float[] paramArrayOfFloat)
  {
    if (isDisposed())
      SWT.error(44);
    if (paramArrayOfFloat == null)
      SWT.error(4);
    if (paramArrayOfFloat.length < 6)
      SWT.error(5);
    Gdip.Matrix_GetElements(this.handle, paramArrayOfFloat);
  }

  public void identity()
  {
    if (isDisposed())
      SWT.error(44);
    Gdip.Matrix_SetElements(this.handle, 1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F);
  }

  public void invert()
  {
    if (isDisposed())
      SWT.error(44);
    if (Gdip.Matrix_Invert(this.handle) != 0)
      SWT.error(10);
  }

  public boolean isDisposed()
  {
    return this.handle == 0;
  }

  public boolean isIdentity()
  {
    if (isDisposed())
      SWT.error(44);
    return Gdip.Matrix_IsIdentity(this.handle);
  }

  public void multiply(Transform paramTransform)
  {
    if (isDisposed())
      SWT.error(44);
    if (paramTransform == null)
      SWT.error(4);
    if (paramTransform.isDisposed())
      SWT.error(5);
    Gdip.Matrix_Multiply(this.handle, paramTransform.handle, 0);
  }

  public void rotate(float paramFloat)
  {
    if (isDisposed())
      SWT.error(44);
    Gdip.Matrix_Rotate(this.handle, paramFloat, 0);
  }

  public void scale(float paramFloat1, float paramFloat2)
  {
    if (isDisposed())
      SWT.error(44);
    Gdip.Matrix_Scale(this.handle, paramFloat1, paramFloat2, 0);
  }

  public void setElements(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    if (isDisposed())
      SWT.error(44);
    Gdip.Matrix_SetElements(this.handle, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6);
  }

  public void shear(float paramFloat1, float paramFloat2)
  {
    if (isDisposed())
      SWT.error(44);
    Gdip.Matrix_Shear(this.handle, paramFloat1, paramFloat2, 0);
  }

  public void transform(float[] paramArrayOfFloat)
  {
    if (isDisposed())
      SWT.error(44);
    if (paramArrayOfFloat == null)
      SWT.error(4);
    Gdip.Matrix_TransformPoints(this.handle, paramArrayOfFloat, paramArrayOfFloat.length / 2);
  }

  public void translate(float paramFloat1, float paramFloat2)
  {
    if (isDisposed())
      SWT.error(44);
    Gdip.Matrix_Translate(this.handle, paramFloat1, paramFloat2, 0);
  }

  public String toString()
  {
    if (isDisposed())
      return "Transform {*DISPOSED*}";
    float[] arrayOfFloat = new float[6];
    getElements(arrayOfFloat);
    return "Transform {" + arrayOfFloat[0] + "," + arrayOfFloat[1] + "," + arrayOfFloat[2] + "," + arrayOfFloat[3] + "," + arrayOfFloat[4] + "," + arrayOfFloat[5] + "}";
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.graphics.Transform
 * JD-Core Version:    0.6.2
 */