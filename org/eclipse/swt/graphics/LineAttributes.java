package org.eclipse.swt.graphics;

public class LineAttributes
{
  public float width;
  public int style;
  public int cap;
  public int join;
  public float[] dash;
  public float dashOffset;
  public float miterLimit;

  public LineAttributes(float paramFloat)
  {
    this(paramFloat, 1, 1, 1, null, 0.0F, 10.0F);
  }

  public LineAttributes(float paramFloat, int paramInt1, int paramInt2)
  {
    this(paramFloat, paramInt1, paramInt2, 1, null, 0.0F, 10.0F);
  }

  public LineAttributes(float paramFloat1, int paramInt1, int paramInt2, int paramInt3, float[] paramArrayOfFloat, float paramFloat2, float paramFloat3)
  {
    this.width = paramFloat1;
    this.cap = paramInt1;
    this.join = paramInt2;
    this.style = paramInt3;
    this.dash = paramArrayOfFloat;
    this.dashOffset = paramFloat2;
    this.miterLimit = paramFloat3;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.graphics.LineAttributes
 * JD-Core Version:    0.6.2
 */