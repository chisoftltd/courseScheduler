package org.eclipse.swt.graphics;

import org.eclipse.swt.internal.win32.TEXTMETRIC;

public final class FontMetrics
{
  public TEXTMETRIC handle;

  public boolean equals(Object paramObject)
  {
    if (paramObject == this)
      return true;
    if (!(paramObject instanceof FontMetrics))
      return false;
    TEXTMETRIC localTEXTMETRIC = ((FontMetrics)paramObject).handle;
    return (this.handle.tmHeight == localTEXTMETRIC.tmHeight) && (this.handle.tmAscent == localTEXTMETRIC.tmAscent) && (this.handle.tmDescent == localTEXTMETRIC.tmDescent) && (this.handle.tmInternalLeading == localTEXTMETRIC.tmInternalLeading) && (this.handle.tmExternalLeading == localTEXTMETRIC.tmExternalLeading) && (this.handle.tmAveCharWidth == localTEXTMETRIC.tmAveCharWidth) && (this.handle.tmMaxCharWidth == localTEXTMETRIC.tmMaxCharWidth) && (this.handle.tmWeight == localTEXTMETRIC.tmWeight) && (this.handle.tmOverhang == localTEXTMETRIC.tmOverhang) && (this.handle.tmDigitizedAspectX == localTEXTMETRIC.tmDigitizedAspectX) && (this.handle.tmDigitizedAspectY == localTEXTMETRIC.tmDigitizedAspectY) && (this.handle.tmItalic == localTEXTMETRIC.tmItalic) && (this.handle.tmUnderlined == localTEXTMETRIC.tmUnderlined) && (this.handle.tmStruckOut == localTEXTMETRIC.tmStruckOut) && (this.handle.tmPitchAndFamily == localTEXTMETRIC.tmPitchAndFamily) && (this.handle.tmCharSet == localTEXTMETRIC.tmCharSet);
  }

  public int getAscent()
  {
    return this.handle.tmAscent - this.handle.tmInternalLeading;
  }

  public int getAverageCharWidth()
  {
    return this.handle.tmAveCharWidth;
  }

  public int getDescent()
  {
    return this.handle.tmDescent;
  }

  public int getHeight()
  {
    return this.handle.tmHeight;
  }

  public int getLeading()
  {
    return this.handle.tmInternalLeading;
  }

  public int hashCode()
  {
    return this.handle.tmHeight ^ this.handle.tmAscent ^ this.handle.tmDescent ^ this.handle.tmInternalLeading ^ this.handle.tmExternalLeading ^ this.handle.tmAveCharWidth ^ this.handle.tmMaxCharWidth ^ this.handle.tmWeight ^ this.handle.tmOverhang ^ this.handle.tmDigitizedAspectX ^ this.handle.tmDigitizedAspectY ^ this.handle.tmItalic ^ this.handle.tmUnderlined ^ this.handle.tmStruckOut ^ this.handle.tmPitchAndFamily ^ this.handle.tmCharSet;
  }

  public static FontMetrics win32_new(TEXTMETRIC paramTEXTMETRIC)
  {
    FontMetrics localFontMetrics = new FontMetrics();
    localFontMetrics.handle = paramTEXTMETRIC;
    return localFontMetrics;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.graphics.FontMetrics
 * JD-Core Version:    0.6.2
 */