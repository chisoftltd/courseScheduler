package org.eclipse.swt.graphics;

import org.eclipse.swt.SWT;

public final class GlyphMetrics
{
  public int ascent;
  public int descent;
  public int width;

  public GlyphMetrics(int paramInt1, int paramInt2, int paramInt3)
  {
    if ((paramInt1 < 0) || (paramInt2 < 0) || (paramInt3 < 0))
      SWT.error(5);
    this.ascent = paramInt1;
    this.descent = paramInt2;
    this.width = paramInt3;
  }

  public boolean equals(Object paramObject)
  {
    if (paramObject == this)
      return true;
    if (!(paramObject instanceof GlyphMetrics))
      return false;
    GlyphMetrics localGlyphMetrics = (GlyphMetrics)paramObject;
    return (localGlyphMetrics.ascent == this.ascent) && (localGlyphMetrics.descent == this.descent) && (localGlyphMetrics.width == this.width);
  }

  public int hashCode()
  {
    return this.ascent ^ this.descent ^ this.width;
  }

  public String toString()
  {
    return "GlyphMetrics {" + this.ascent + ", " + this.descent + ", " + this.width + "}";
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.graphics.GlyphMetrics
 * JD-Core Version:    0.6.2
 */