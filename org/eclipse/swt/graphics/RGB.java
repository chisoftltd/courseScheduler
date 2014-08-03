package org.eclipse.swt.graphics;

import org.eclipse.swt.SWT;
import org.eclipse.swt.internal.SerializableCompatibility;

public final class RGB
  implements SerializableCompatibility
{
  public int red;
  public int green;
  public int blue;
  static final long serialVersionUID = 3258415023461249074L;

  public RGB(int paramInt1, int paramInt2, int paramInt3)
  {
    if ((paramInt1 > 255) || (paramInt1 < 0) || (paramInt2 > 255) || (paramInt2 < 0) || (paramInt3 > 255) || (paramInt3 < 0))
      SWT.error(5);
    this.red = paramInt1;
    this.green = paramInt2;
    this.blue = paramInt3;
  }

  public RGB(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if ((paramFloat1 < 0.0F) || (paramFloat1 > 360.0F) || (paramFloat2 < 0.0F) || (paramFloat2 > 1.0F) || (paramFloat3 < 0.0F) || (paramFloat3 > 1.0F))
      SWT.error(5);
    float f3;
    float f2;
    float f1;
    if (paramFloat2 == 0.0F)
    {
      f1 = f2 = f3 = paramFloat3;
    }
    else
    {
      if (paramFloat1 == 360.0F)
        paramFloat1 = 0.0F;
      paramFloat1 /= 60.0F;
      int i = (int)paramFloat1;
      float f4 = paramFloat1 - i;
      float f5 = paramFloat3 * (1.0F - paramFloat2);
      float f6 = paramFloat3 * (1.0F - paramFloat2 * f4);
      float f7 = paramFloat3 * (1.0F - paramFloat2 * (1.0F - f4));
      switch (i)
      {
      case 0:
        f1 = paramFloat3;
        f2 = f7;
        f3 = f5;
        break;
      case 1:
        f1 = f6;
        f2 = paramFloat3;
        f3 = f5;
        break;
      case 2:
        f1 = f5;
        f2 = paramFloat3;
        f3 = f7;
        break;
      case 3:
        f1 = f5;
        f2 = f6;
        f3 = paramFloat3;
        break;
      case 4:
        f1 = f7;
        f2 = f5;
        f3 = paramFloat3;
        break;
      case 5:
      default:
        f1 = paramFloat3;
        f2 = f5;
        f3 = f6;
      }
    }
    this.red = ((int)(f1 * 255.0F + 0.5D));
    this.green = ((int)(f2 * 255.0F + 0.5D));
    this.blue = ((int)(f3 * 255.0F + 0.5D));
  }

  public float[] getHSB()
  {
    float f1 = this.red / 255.0F;
    float f2 = this.green / 255.0F;
    float f3 = this.blue / 255.0F;
    float f4 = Math.max(Math.max(f1, f2), f3);
    float f5 = Math.min(Math.min(f1, f2), f3);
    float f6 = f4 - f5;
    float f7 = 0.0F;
    float f8 = f4;
    float f9 = f4 == 0.0F ? 0.0F : (f4 - f5) / f4;
    if (f6 != 0.0F)
    {
      if (f1 == f4)
        f7 = (f2 - f3) / f6;
      else if (f2 == f4)
        f7 = 2.0F + (f3 - f1) / f6;
      else
        f7 = 4.0F + (f1 - f2) / f6;
      f7 *= 60.0F;
      if (f7 < 0.0F)
        f7 += 360.0F;
    }
    return new float[] { f7, f9, f8 };
  }

  public boolean equals(Object paramObject)
  {
    if (paramObject == this)
      return true;
    if (!(paramObject instanceof RGB))
      return false;
    RGB localRGB = (RGB)paramObject;
    return (localRGB.red == this.red) && (localRGB.green == this.green) && (localRGB.blue == this.blue);
  }

  public int hashCode()
  {
    return this.blue << 16 | this.green << 8 | this.red;
  }

  public String toString()
  {
    return "RGB {" + this.red + ", " + this.green + ", " + this.blue + "}";
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.graphics.RGB
 * JD-Core Version:    0.6.2
 */