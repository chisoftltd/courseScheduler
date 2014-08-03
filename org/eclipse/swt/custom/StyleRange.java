package org.eclipse.swt.custom;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.swt.internal.CloneableCompatibility;

public class StyleRange extends TextStyle
  implements CloneableCompatibility
{
  public int start;
  public int length;
  public int fontStyle = 0;

  public StyleRange()
  {
  }

  public StyleRange(TextStyle paramTextStyle)
  {
    super(paramTextStyle);
  }

  public StyleRange(int paramInt1, int paramInt2, Color paramColor1, Color paramColor2)
  {
    super(null, paramColor1, paramColor2);
    this.start = paramInt1;
    this.length = paramInt2;
  }

  public StyleRange(int paramInt1, int paramInt2, Color paramColor1, Color paramColor2, int paramInt3)
  {
    this(paramInt1, paramInt2, paramColor1, paramColor2);
    this.fontStyle = paramInt3;
  }

  public boolean equals(Object paramObject)
  {
    if (paramObject == this)
      return true;
    if ((paramObject instanceof StyleRange))
    {
      StyleRange localStyleRange = (StyleRange)paramObject;
      if (this.start != localStyleRange.start)
        return false;
      if (this.length != localStyleRange.length)
        return false;
      return similarTo(localStyleRange);
    }
    return false;
  }

  public int hashCode()
  {
    return super.hashCode() ^ this.fontStyle;
  }

  boolean isVariableHeight()
  {
    return (this.font != null) || (this.metrics != null) || (this.rise != 0);
  }

  public boolean isUnstyled()
  {
    if (this.font != null)
      return false;
    if (this.rise != 0)
      return false;
    if (this.metrics != null)
      return false;
    if (this.foreground != null)
      return false;
    if (this.background != null)
      return false;
    if (this.fontStyle != 0)
      return false;
    if (this.underline)
      return false;
    if (this.strikeout)
      return false;
    return this.borderStyle == 0;
  }

  public boolean similarTo(StyleRange paramStyleRange)
  {
    if (!super.equals(paramStyleRange))
      return false;
    return this.fontStyle == paramStyleRange.fontStyle;
  }

  public Object clone()
  {
    try
    {
      return super.clone();
    }
    catch (CloneNotSupportedException localCloneNotSupportedException)
    {
    }
    return null;
  }

  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("StyleRange {");
    localStringBuffer.append(this.start);
    localStringBuffer.append(", ");
    localStringBuffer.append(this.length);
    localStringBuffer.append(", fontStyle=");
    switch (this.fontStyle)
    {
    case 1:
      localStringBuffer.append("bold");
      break;
    case 2:
      localStringBuffer.append("italic");
      break;
    case 3:
      localStringBuffer.append("bold-italic");
      break;
    default:
      localStringBuffer.append("normal");
    }
    String str = super.toString();
    int i = str.indexOf('{');
    str = str.substring(i + 1);
    if (str.length() > 1)
      localStringBuffer.append(", ");
    localStringBuffer.append(str);
    return localStringBuffer.toString();
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.custom.StyleRange
 * JD-Core Version:    0.6.2
 */