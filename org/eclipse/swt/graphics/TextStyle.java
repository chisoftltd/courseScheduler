package org.eclipse.swt.graphics;

import org.eclipse.swt.SWT;

public class TextStyle
{
  public Font font;
  public Color foreground;
  public Color background;
  public boolean underline;
  public Color underlineColor;
  public int underlineStyle;
  public boolean strikeout;
  public Color strikeoutColor;
  public int borderStyle;
  public Color borderColor;
  public GlyphMetrics metrics;
  public int rise;
  public Object data;

  public TextStyle()
  {
  }

  public TextStyle(Font paramFont, Color paramColor1, Color paramColor2)
  {
    if ((paramFont != null) && (paramFont.isDisposed()))
      SWT.error(5);
    if ((paramColor1 != null) && (paramColor1.isDisposed()))
      SWT.error(5);
    if ((paramColor2 != null) && (paramColor2.isDisposed()))
      SWT.error(5);
    this.font = paramFont;
    this.foreground = paramColor1;
    this.background = paramColor2;
  }

  public TextStyle(TextStyle paramTextStyle)
  {
    if (paramTextStyle == null)
      SWT.error(5);
    this.font = paramTextStyle.font;
    this.foreground = paramTextStyle.foreground;
    this.background = paramTextStyle.background;
    this.underline = paramTextStyle.underline;
    this.underlineColor = paramTextStyle.underlineColor;
    this.underlineStyle = paramTextStyle.underlineStyle;
    this.strikeout = paramTextStyle.strikeout;
    this.strikeoutColor = paramTextStyle.strikeoutColor;
    this.borderStyle = paramTextStyle.borderStyle;
    this.borderColor = paramTextStyle.borderColor;
    this.metrics = paramTextStyle.metrics;
    this.rise = paramTextStyle.rise;
    this.data = paramTextStyle.data;
  }

  public boolean equals(Object paramObject)
  {
    if (paramObject == this)
      return true;
    if (paramObject == null)
      return false;
    if (!(paramObject instanceof TextStyle))
      return false;
    TextStyle localTextStyle = (TextStyle)paramObject;
    if (this.foreground != null)
    {
      if (!this.foreground.equals(localTextStyle.foreground))
        return false;
    }
    else if (localTextStyle.foreground != null)
      return false;
    if (this.background != null)
    {
      if (!this.background.equals(localTextStyle.background))
        return false;
    }
    else if (localTextStyle.background != null)
      return false;
    if (this.font != null)
    {
      if (!this.font.equals(localTextStyle.font))
        return false;
    }
    else if (localTextStyle.font != null)
      return false;
    if ((this.metrics != null) || (localTextStyle.metrics != null))
      return false;
    if (this.underline != localTextStyle.underline)
      return false;
    if (this.underlineStyle != localTextStyle.underlineStyle)
      return false;
    if (this.borderStyle != localTextStyle.borderStyle)
      return false;
    if (this.strikeout != localTextStyle.strikeout)
      return false;
    if (this.rise != localTextStyle.rise)
      return false;
    if (this.underlineColor != null)
    {
      if (!this.underlineColor.equals(localTextStyle.underlineColor))
        return false;
    }
    else if (localTextStyle.underlineColor != null)
      return false;
    if (this.strikeoutColor != null)
    {
      if (!this.strikeoutColor.equals(localTextStyle.strikeoutColor))
        return false;
    }
    else if (localTextStyle.strikeoutColor != null)
      return false;
    if (this.underlineStyle != localTextStyle.underlineStyle)
      return false;
    if (this.borderColor != null)
    {
      if (!this.borderColor.equals(localTextStyle.borderColor))
        return false;
    }
    else if (localTextStyle.borderColor != null)
      return false;
    if (this.data != null)
    {
      if (!this.data.equals(localTextStyle.data))
        return false;
    }
    else if (localTextStyle.data != null)
      return false;
    return true;
  }

  public int hashCode()
  {
    int i = 0;
    if (this.foreground != null)
      i ^= this.foreground.hashCode();
    if (this.background != null)
      i ^= this.background.hashCode();
    if (this.font != null)
      i ^= this.font.hashCode();
    if (this.metrics != null)
      i ^= this.metrics.hashCode();
    if (this.underline)
      i ^= i;
    if (this.strikeout)
      i ^= i;
    i ^= this.rise;
    if (this.underlineColor != null)
      i ^= this.underlineColor.hashCode();
    if (this.strikeoutColor != null)
      i ^= this.strikeoutColor.hashCode();
    if (this.borderColor != null)
      i ^= this.borderColor.hashCode();
    i ^= this.underlineStyle;
    return i;
  }

  boolean isAdherentBorder(TextStyle paramTextStyle)
  {
    if (this == paramTextStyle)
      return true;
    if (paramTextStyle == null)
      return false;
    if (this.borderStyle != paramTextStyle.borderStyle)
      return false;
    if (this.borderColor != null)
    {
      if (!this.borderColor.equals(paramTextStyle.borderColor))
        return false;
    }
    else
    {
      if (paramTextStyle.borderColor != null)
        return false;
      if (this.foreground != null)
      {
        if (!this.foreground.equals(paramTextStyle.foreground))
          return false;
      }
      else if (paramTextStyle.foreground != null)
        return false;
    }
    return true;
  }

  boolean isAdherentUnderline(TextStyle paramTextStyle)
  {
    if (this == paramTextStyle)
      return true;
    if (paramTextStyle == null)
      return false;
    if (this.underline != paramTextStyle.underline)
      return false;
    if (this.underlineStyle != paramTextStyle.underlineStyle)
      return false;
    if (this.underlineColor != null)
    {
      if (!this.underlineColor.equals(paramTextStyle.underlineColor))
        return false;
    }
    else
    {
      if (paramTextStyle.underlineColor != null)
        return false;
      if (this.foreground != null)
      {
        if (!this.foreground.equals(paramTextStyle.foreground))
          return false;
      }
      else if (paramTextStyle.foreground != null)
        return false;
    }
    return true;
  }

  boolean isAdherentStrikeout(TextStyle paramTextStyle)
  {
    if (this == paramTextStyle)
      return true;
    if (paramTextStyle == null)
      return false;
    if (this.strikeout != paramTextStyle.strikeout)
      return false;
    if (this.strikeoutColor != null)
    {
      if (!this.strikeoutColor.equals(paramTextStyle.strikeoutColor))
        return false;
    }
    else
    {
      if (paramTextStyle.strikeoutColor != null)
        return false;
      if (this.foreground != null)
      {
        if (!this.foreground.equals(paramTextStyle.foreground))
          return false;
      }
      else if (paramTextStyle.foreground != null)
        return false;
    }
    return true;
  }

  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer("TextStyle {");
    int i = localStringBuffer.length();
    if (this.font != null)
    {
      if (localStringBuffer.length() > i)
        localStringBuffer.append(", ");
      localStringBuffer.append("font=");
      localStringBuffer.append(this.font);
    }
    if (this.foreground != null)
    {
      if (localStringBuffer.length() > i)
        localStringBuffer.append(", ");
      localStringBuffer.append("foreground=");
      localStringBuffer.append(this.foreground);
    }
    if (this.background != null)
    {
      if (localStringBuffer.length() > i)
        localStringBuffer.append(", ");
      localStringBuffer.append("background=");
      localStringBuffer.append(this.background);
    }
    if (this.underline)
    {
      if (localStringBuffer.length() > i)
        localStringBuffer.append(", ");
      localStringBuffer.append("underline=");
      switch (this.underlineStyle)
      {
      case 0:
        localStringBuffer.append("single");
        break;
      case 1:
        localStringBuffer.append("double");
        break;
      case 3:
        localStringBuffer.append("squiggle");
        break;
      case 2:
        localStringBuffer.append("error");
        break;
      case 4:
        localStringBuffer.append("link");
      }
      if (this.underlineColor != null)
      {
        localStringBuffer.append(", underlineColor=");
        localStringBuffer.append(this.underlineColor);
      }
    }
    if (this.strikeout)
    {
      if (localStringBuffer.length() > i)
        localStringBuffer.append(", ");
      localStringBuffer.append("striked out");
      if (this.strikeoutColor != null)
      {
        localStringBuffer.append(", strikeoutColor=");
        localStringBuffer.append(this.strikeoutColor);
      }
    }
    if (this.borderStyle != 0)
    {
      if (localStringBuffer.length() > i)
        localStringBuffer.append(", ");
      localStringBuffer.append("border=");
      switch (this.borderStyle)
      {
      case 1:
        localStringBuffer.append("solid");
        break;
      case 4:
        localStringBuffer.append("dot");
        break;
      case 2:
        localStringBuffer.append("dash");
      case 3:
      }
      if (this.borderColor != null)
      {
        localStringBuffer.append(", borderColor=");
        localStringBuffer.append(this.borderColor);
      }
    }
    if (this.rise != 0)
    {
      if (localStringBuffer.length() > i)
        localStringBuffer.append(", ");
      localStringBuffer.append("rise=");
      localStringBuffer.append(this.rise);
    }
    if (this.metrics != null)
    {
      if (localStringBuffer.length() > i)
        localStringBuffer.append(", ");
      localStringBuffer.append("metrics=");
      localStringBuffer.append(this.metrics);
    }
    localStringBuffer.append("}");
    return localStringBuffer.toString();
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.graphics.TextStyle
 * JD-Core Version:    0.6.2
 */