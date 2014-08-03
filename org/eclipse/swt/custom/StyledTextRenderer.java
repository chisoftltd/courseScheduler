package org.eclipse.swt.custom;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.GlyphMetrics;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.TextLayout;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.IME;
import org.eclipse.swt.widgets.ScrollBar;

class StyledTextRenderer
{
  Device device;
  StyledText styledText;
  StyledTextContent content;
  Font regularFont;
  Font boldFont;
  Font italicFont;
  Font boldItalicFont;
  int tabWidth;
  int ascent;
  int descent;
  int averageCharWidth;
  int topIndex = -1;
  TextLayout[] layouts;
  int lineCount;
  int[] lineWidth;
  int[] lineHeight;
  LineInfo[] lines;
  int maxWidth;
  int maxWidthLineIndex;
  boolean idleRunning;
  Bullet[] bullets;
  int[] bulletsIndices;
  int[] redrawLines;
  int[] ranges;
  int styleCount;
  StyleRange[] styles;
  StyleRange[] stylesSet;
  int stylesSetCount = 0;
  boolean hasLinks;
  boolean fixedPitch;
  static final int BULLET_MARGIN = 8;
  static final boolean COMPACT_STYLES = true;
  static final boolean MERGE_STYLES = true;
  static final int GROW = 32;
  static final int IDLE_TIME = 50;
  static final int CACHE_SIZE = 128;
  static final int BACKGROUND = 1;
  static final int ALIGNMENT = 2;
  static final int INDENT = 4;
  static final int JUSTIFY = 8;
  static final int SEGMENTS = 32;
  static final int TABSTOPS = 64;
  static final int WRAP_INDENT = 128;
  static final int SEGMENT_CHARS = 256;

  StyledTextRenderer(Device paramDevice, StyledText paramStyledText)
  {
    this.device = paramDevice;
    this.styledText = paramStyledText;
  }

  int addMerge(int[] paramArrayOfInt, StyleRange[] paramArrayOfStyleRange, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = this.styleCount << 1;
    StyleRange localStyleRange = null;
    int j = 0;
    int k = 0;
    if (paramInt3 < i)
    {
      localStyleRange = this.styles[(paramInt3 >> 1)];
      j = this.ranges[paramInt3];
      k = this.ranges[(paramInt3 + 1)];
    }
    int m = paramInt1 - (paramInt3 - paramInt2);
    if (i + m >= this.ranges.length)
    {
      int[] arrayOfInt = new int[this.ranges.length + m + 64];
      System.arraycopy(this.ranges, 0, arrayOfInt, 0, paramInt2);
      StyleRange[] arrayOfStyleRange = new StyleRange[this.styles.length + (m >> 1) + 32];
      System.arraycopy(this.styles, 0, arrayOfStyleRange, 0, paramInt2 >> 1);
      if (i > paramInt3)
      {
        System.arraycopy(this.ranges, paramInt3, arrayOfInt, paramInt2 + paramInt1, i - paramInt3);
        System.arraycopy(this.styles, paramInt3 >> 1, arrayOfStyleRange, paramInt2 + paramInt1 >> 1, this.styleCount - (paramInt3 >> 1));
      }
      this.ranges = arrayOfInt;
      this.styles = arrayOfStyleRange;
    }
    else if (i > paramInt3)
    {
      System.arraycopy(this.ranges, paramInt3, this.ranges, paramInt2 + paramInt1, i - paramInt3);
      System.arraycopy(this.styles, paramInt3 >> 1, this.styles, paramInt2 + paramInt1 >> 1, this.styleCount - (paramInt3 >> 1));
    }
    int n = paramInt2;
    for (int i1 = 0; i1 < paramInt1; i1 += 2)
      if ((n > 0) && (this.ranges[(n - 2)] + this.ranges[(n - 1)] == paramArrayOfInt[i1]) && (paramArrayOfStyleRange[(i1 >> 1)].similarTo(this.styles[(n - 2 >> 1)])))
      {
        this.ranges[(n - 1)] += paramArrayOfInt[(i1 + 1)];
      }
      else
      {
        this.styles[(n >> 1)] = paramArrayOfStyleRange[(i1 >> 1)];
        this.ranges[(n++)] = paramArrayOfInt[i1];
        this.ranges[(n++)] = paramArrayOfInt[(i1 + 1)];
      }
    if ((localStyleRange != null) && (this.ranges[(n - 2)] + this.ranges[(n - 1)] == j) && (localStyleRange.similarTo(this.styles[(n - 2 >> 1)])))
    {
      this.ranges[(n - 1)] += k;
      paramInt3 += 2;
      paramInt1 += 2;
    }
    if (i > paramInt3)
    {
      System.arraycopy(this.ranges, paramInt2 + paramInt1, this.ranges, n, i - paramInt3);
      System.arraycopy(this.styles, paramInt2 + paramInt1 >> 1, this.styles, n >> 1, this.styleCount - (paramInt3 >> 1));
    }
    m = n - paramInt2 - (paramInt3 - paramInt2);
    this.styleCount += (m >> 1);
    return m;
  }

  int addMerge(StyleRange[] paramArrayOfStyleRange, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = paramInt1 - (paramInt3 - paramInt2);
    StyleRange localStyleRange1 = null;
    if (paramInt3 < this.styleCount)
      localStyleRange1 = this.styles[paramInt3];
    if (this.styleCount + i >= this.styles.length)
    {
      StyleRange[] arrayOfStyleRange = new StyleRange[this.styles.length + i + 32];
      System.arraycopy(this.styles, 0, arrayOfStyleRange, 0, paramInt2);
      if (this.styleCount > paramInt3)
        System.arraycopy(this.styles, paramInt3, arrayOfStyleRange, paramInt2 + paramInt1, this.styleCount - paramInt3);
      this.styles = arrayOfStyleRange;
    }
    else if (this.styleCount > paramInt3)
    {
      System.arraycopy(this.styles, paramInt3, this.styles, paramInt2 + paramInt1, this.styleCount - paramInt3);
    }
    int j = paramInt2;
    for (int k = 0; k < paramInt1; k++)
    {
      StyleRange localStyleRange3 = paramArrayOfStyleRange[k];
      StyleRange localStyleRange4;
      if ((j > 0) && ((localStyleRange4 = this.styles[(j - 1)]).start + localStyleRange4.length == localStyleRange3.start) && (localStyleRange3.similarTo(localStyleRange4)))
        localStyleRange4.length += localStyleRange3.length;
      else
        this.styles[(j++)] = localStyleRange3;
    }
    StyleRange localStyleRange2 = this.styles[(j - 1)];
    if ((localStyleRange1 != null) && (localStyleRange2.start + localStyleRange2.length == localStyleRange1.start) && (localStyleRange1.similarTo(localStyleRange2)))
    {
      localStyleRange2.length += localStyleRange1.length;
      paramInt3++;
      paramInt1++;
    }
    if (this.styleCount > paramInt3)
      System.arraycopy(this.styles, paramInt2 + paramInt1, this.styles, j, this.styleCount - paramInt3);
    i = j - paramInt2 - (paramInt3 - paramInt2);
    this.styleCount += i;
    return i;
  }

  void calculate(int paramInt1, int paramInt2)
  {
    int i = paramInt1 + paramInt2;
    if ((paramInt1 < 0) || (i > this.lineWidth.length))
      return;
    int j = this.styledText.leftMargin + this.styledText.rightMargin + this.styledText.getCaretWidth();
    for (int k = paramInt1; k < i; k++)
    {
      if ((this.lineWidth[k] == -1) || (this.lineHeight[k] == -1))
      {
        TextLayout localTextLayout = getTextLayout(k);
        Rectangle localRectangle = localTextLayout.getBounds();
        this.lineWidth[k] = (localRectangle.width + j);
        this.lineHeight[k] = localRectangle.height;
        disposeTextLayout(localTextLayout);
      }
      if (this.lineWidth[k] > this.maxWidth)
      {
        this.maxWidth = this.lineWidth[k];
        this.maxWidthLineIndex = k;
      }
    }
  }

  void calculateClientArea()
  {
    int i = this.styledText.getTopIndex();
    int j = this.content.getLineCount();
    int k = this.styledText.getClientArea().height;
    int m = 0;
    while ((k > m) && (j > i))
    {
      calculate(i, 1);
      m += this.lineHeight[(i++)];
    }
  }

  void calculateIdle()
  {
    if (this.idleRunning)
      return;
    Runnable local1 = new Runnable()
    {
      public void run()
      {
        if (StyledTextRenderer.this.styledText == null)
          return;
        long l = System.currentTimeMillis();
        for (int i = 0; i < StyledTextRenderer.this.lineCount; i++)
          if ((StyledTextRenderer.this.lineHeight[i] == -1) || (StyledTextRenderer.this.lineWidth[i] == -1))
          {
            StyledTextRenderer.this.calculate(i, 1);
            if (System.currentTimeMillis() - l > 50L)
              break;
          }
        Object localObject;
        if (i < StyledTextRenderer.this.lineCount)
        {
          localObject = StyledTextRenderer.this.styledText.getDisplay();
          ((Display)localObject).asyncExec(this);
        }
        else
        {
          StyledTextRenderer.this.idleRunning = false;
          StyledTextRenderer.this.styledText.setScrollBars(true);
          localObject = StyledTextRenderer.this.styledText.getVerticalBar();
          if (localObject != null)
            ((ScrollBar)localObject).setSelection(StyledTextRenderer.this.styledText.getVerticalScrollOffset());
        }
      }
    };
    Display localDisplay = this.styledText.getDisplay();
    localDisplay.asyncExec(local1);
    this.idleRunning = true;
  }

  void clearLineBackground(int paramInt1, int paramInt2)
  {
    if (this.lines == null)
      return;
    for (int i = paramInt1; i < paramInt1 + paramInt2; i++)
    {
      LineInfo localLineInfo = this.lines[i];
      if (localLineInfo != null)
      {
        localLineInfo.flags &= -2;
        localLineInfo.background = null;
        if (localLineInfo.flags == 0)
          this.lines[i] = null;
      }
    }
  }

  void clearLineStyle(int paramInt1, int paramInt2)
  {
    if (this.lines == null)
      return;
    for (int i = paramInt1; i < paramInt1 + paramInt2; i++)
    {
      LineInfo localLineInfo = this.lines[i];
      if (localLineInfo != null)
      {
        localLineInfo.flags &= -207;
        if (localLineInfo.flags == 0)
          this.lines[i] = null;
      }
    }
  }

  void copyInto(StyledTextRenderer paramStyledTextRenderer)
  {
    Object localObject;
    if (this.ranges != null)
    {
      localObject = paramStyledTextRenderer.ranges = new int[this.styleCount << 1];
      System.arraycopy(this.ranges, 0, localObject, 0, localObject.length);
    }
    int i;
    if (this.styles != null)
    {
      localObject = paramStyledTextRenderer.styles = new StyleRange[this.styleCount];
      for (i = 0; i < localObject.length; i++)
        localObject[i] = ((StyleRange)this.styles[i].clone());
      paramStyledTextRenderer.styleCount = this.styleCount;
    }
    if (this.lines != null)
    {
      localObject = paramStyledTextRenderer.lines = new LineInfo[this.lineCount];
      for (i = 0; i < localObject.length; i++)
        localObject[i] = new LineInfo(this.lines[i]);
      paramStyledTextRenderer.lineCount = this.lineCount;
    }
  }

  void dispose()
  {
    if (this.boldFont != null)
      this.boldFont.dispose();
    if (this.italicFont != null)
      this.italicFont.dispose();
    if (this.boldItalicFont != null)
      this.boldItalicFont.dispose();
    this.boldFont = (this.italicFont = this.boldItalicFont = null);
    reset();
    this.content = null;
    this.device = null;
    this.styledText = null;
  }

  void disposeTextLayout(TextLayout paramTextLayout)
  {
    if (this.layouts != null)
      for (int i = 0; i < this.layouts.length; i++)
        if (this.layouts[i] == paramTextLayout)
          return;
    paramTextLayout.dispose();
  }

  void drawBullet(Bullet paramBullet, GC paramGC, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    StyleRange localStyleRange = paramBullet.style;
    GlyphMetrics localGlyphMetrics = localStyleRange.metrics;
    Color localColor = localStyleRange.foreground;
    if (localColor != null)
      paramGC.setForeground(localColor);
    if (((paramBullet.type & 0x1) != 0) && (StyledText.IS_MOTIF))
    {
      int i = Math.max(4, (paramInt4 + paramInt5) / 4);
      if ((i & 0x1) == 0)
        i++;
      if (localColor == null)
      {
        Display localDisplay1 = this.styledText.getDisplay();
        localColor = localDisplay1.getSystemColor(2);
      }
      paramGC.setBackground(localColor);
      int j = paramInt1 + Math.max(0, localGlyphMetrics.width - i - 8);
      paramGC.fillArc(j, paramInt2 + i, i + 1, i + 1, 0, 360);
      return;
    }
    Font localFont = localStyleRange.font;
    if (localFont != null)
      paramGC.setFont(localFont);
    String str = "";
    int k = paramBullet.type & 0xF;
    switch (k)
    {
    case 1:
      str = "•";
      break;
    case 2:
      str = String.valueOf(paramInt3 + 1);
      break;
    case 4:
      str = String.valueOf((char)(paramInt3 % 26 + 97));
      break;
    case 8:
      str = String.valueOf((char)(paramInt3 % 26 + 65));
    case 3:
    case 5:
    case 6:
    case 7:
    }
    if ((paramBullet.type & 0x10) != 0)
      str = str + paramBullet.text;
    Display localDisplay2 = this.styledText.getDisplay();
    TextLayout localTextLayout = new TextLayout(localDisplay2);
    localTextLayout.setText(str);
    localTextLayout.setAscent(paramInt4);
    localTextLayout.setDescent(paramInt5);
    localStyleRange = (StyleRange)localStyleRange.clone();
    localStyleRange.metrics = null;
    if (localStyleRange.font == null)
      localStyleRange.font = getFont(localStyleRange.fontStyle);
    localTextLayout.setStyle(localStyleRange, 0, str.length());
    int m = paramInt1 + Math.max(0, localGlyphMetrics.width - localTextLayout.getBounds().width - 8);
    localTextLayout.draw(paramGC, m, paramInt2);
    localTextLayout.dispose();
  }

  int drawLine(int paramInt1, int paramInt2, int paramInt3, GC paramGC, Color paramColor1, Color paramColor2)
  {
    TextLayout localTextLayout = getTextLayout(paramInt1);
    String str = this.content.getLine(paramInt1);
    int i = this.content.getOffsetAtLine(paramInt1);
    int j = str.length();
    Point localPoint1 = this.styledText.getSelection();
    int k = localPoint1.x - i;
    int m = localPoint1.y - i;
    if (this.styledText.getBlockSelection())
      k = m = 0;
    Rectangle localRectangle = this.styledText.getClientArea();
    Color localColor1 = getLineBackground(paramInt1, null);
    StyledTextEvent localStyledTextEvent = this.styledText.getLineBackgroundData(i, str);
    if ((localStyledTextEvent != null) && (localStyledTextEvent.lineBackground != null))
      localColor1 = localStyledTextEvent.lineBackground;
    int n = localTextLayout.getBounds().height;
    if (localColor1 != null)
    {
      paramGC.setBackground(localColor1);
      paramGC.fillRectangle(localRectangle.x, paramInt3, localRectangle.width, n);
    }
    else
    {
      paramGC.setBackground(paramColor1);
      this.styledText.drawBackground(paramGC, localRectangle.x, paramInt3, localRectangle.width, n);
    }
    paramGC.setForeground(paramColor2);
    if ((k == m) || ((m <= 0) && (k > j - 1)))
    {
      localTextLayout.draw(paramGC, paramInt2, paramInt3);
    }
    else
    {
      int i1 = Math.max(0, k);
      i2 = Math.min(j, m);
      Color localColor2 = this.styledText.getSelectionForeground();
      Color localColor3 = this.styledText.getSelectionBackground();
      if ((this.styledText.getStyle() & 0x10000) != 0)
        i5 = 65536;
      else
        i5 = 131072;
      if ((k <= j) && (j < m))
        i5 |= 1048576;
      localTextLayout.draw(paramGC, paramInt2, paramInt3, i1, i2 - 1, localColor2, localColor3, i5);
    }
    Bullet localBullet = null;
    int i2 = -1;
    if (this.bullets != null)
    {
      int i3;
      if (this.bulletsIndices != null)
      {
        i3 = paramInt1 - this.topIndex;
        if ((i3 >= 0) && (i3 < 128))
        {
          localBullet = this.bullets[i3];
          i2 = this.bulletsIndices[i3];
        }
      }
      else
      {
        for (i3 = 0; i3 < this.bullets.length; i3++)
        {
          localBullet = this.bullets[i3];
          i2 = localBullet.indexOf(paramInt1);
          if (i2 != -1)
            break;
        }
      }
    }
    if ((i2 != -1) && (localBullet != null))
    {
      localObject = localTextLayout.getLineMetrics(0);
      int i4 = ((FontMetrics)localObject).getAscent() + ((FontMetrics)localObject).getLeading();
      if (localBullet.type == 32)
      {
        localBullet.style.start = i;
        this.styledText.paintObject(paramGC, paramInt2, paramInt3, i4, ((FontMetrics)localObject).getDescent(), localBullet.style, localBullet, i2);
      }
      else
      {
        drawBullet(localBullet, paramGC, paramInt2, paramInt3, i2, i4, ((FontMetrics)localObject).getDescent());
      }
    }
    Object localObject = localTextLayout.getStyles();
    int[] arrayOfInt = (int[])null;
    for (int i5 = 0; i5 < localObject.length; i5++)
      if (localObject[i5].metrics != null)
      {
        if (arrayOfInt == null)
          arrayOfInt = localTextLayout.getRanges();
        int i6 = arrayOfInt[(i5 << 1)];
        int i7 = arrayOfInt[((i5 << 1) + 1)] - i6 + 1;
        Point localPoint2 = localTextLayout.getLocation(i6, false);
        FontMetrics localFontMetrics = localTextLayout.getLineMetrics(localTextLayout.getLineIndex(i6));
        StyleRange localStyleRange = (StyleRange)((StyleRange)localObject[i5]).clone();
        localStyleRange.start = (i6 + i);
        localStyleRange.length = i7;
        int i8 = localFontMetrics.getAscent() + localFontMetrics.getLeading();
        this.styledText.paintObject(paramGC, localPoint2.x + paramInt2, localPoint2.y + paramInt3, i8, localFontMetrics.getDescent(), localStyleRange, null, 0);
      }
    disposeTextLayout(localTextLayout);
    return n;
  }

  int getBaseline()
  {
    return this.ascent;
  }

  Font getFont(int paramInt)
  {
    switch (paramInt)
    {
    case 1:
      if (this.boldFont != null)
        return this.boldFont;
      return this.boldFont = new Font(this.device, getFontData(paramInt));
    case 2:
      if (this.italicFont != null)
        return this.italicFont;
      return this.italicFont = new Font(this.device, getFontData(paramInt));
    case 3:
      if (this.boldItalicFont != null)
        return this.boldItalicFont;
      return this.boldItalicFont = new Font(this.device, getFontData(paramInt));
    }
    return this.regularFont;
  }

  FontData[] getFontData(int paramInt)
  {
    FontData[] arrayOfFontData = this.regularFont.getFontData();
    for (int i = 0; i < arrayOfFontData.length; i++)
      arrayOfFontData[i].setStyle(paramInt);
    return arrayOfFontData;
  }

  int getHeight()
  {
    int i = getLineHeight();
    if (this.styledText.isFixedLineHeight())
      return this.lineCount * i + this.styledText.topMargin + this.styledText.bottomMargin;
    int j = 0;
    int k = this.styledText.getWrapWidth();
    for (int m = 0; m < this.lineCount; m++)
    {
      int n = this.lineHeight[m];
      if (n == -1)
        if (k > 0)
        {
          int i1 = this.content.getLine(m).length();
          n = (i1 * this.averageCharWidth / k + 1) * i;
        }
        else
        {
          n = i;
        }
      j += n;
    }
    return j + this.styledText.topMargin + this.styledText.bottomMargin;
  }

  boolean hasLink(int paramInt)
  {
    if (paramInt == -1)
      return false;
    int i = this.content.getLineAtOffset(paramInt);
    int j = this.content.getOffsetAtLine(i);
    String str = this.content.getLine(i);
    StyledTextEvent localStyledTextEvent = this.styledText.getLineStyleData(j, str);
    int n;
    if (localStyledTextEvent != null)
    {
      StyleRange[] arrayOfStyleRange = localStyledTextEvent.styles;
      if (arrayOfStyleRange != null)
      {
        int[] arrayOfInt = localStyledTextEvent.ranges;
        if (arrayOfInt != null)
          for (n = 0; n < arrayOfInt.length; n += 2)
            if ((arrayOfInt[n] <= paramInt) && (paramInt < arrayOfInt[n] + arrayOfInt[(n + 1)]) && (arrayOfStyleRange[(n >> 1)].underline) && (arrayOfStyleRange[(n >> 1)].underlineStyle == 4))
              return true;
        else
          for (n = 0; n < arrayOfStyleRange.length; n++)
            if ((arrayOfStyleRange[n].start <= paramInt) && (paramInt < arrayOfStyleRange[n].start + arrayOfStyleRange[n].length) && (arrayOfStyleRange[(n >> 1)].underline) && (arrayOfStyleRange[(n >> 1)].underlineStyle == 4))
              return true;
      }
    }
    else if (this.ranges != null)
    {
      int k = this.styleCount << 1;
      int m = getRangeIndex(paramInt, -1, k);
      if (m >= k)
        return false;
      n = this.ranges[m];
      int i1 = this.ranges[(m + 1)];
      StyleRange localStyleRange = this.styles[(m >> 1)];
      if ((n <= paramInt) && (paramInt < n + i1) && (localStyleRange.underline) && (localStyleRange.underlineStyle == 4))
        return true;
    }
    return false;
  }

  int getLineAlignment(int paramInt1, int paramInt2)
  {
    if (this.lines == null)
      return paramInt2;
    LineInfo localLineInfo = this.lines[paramInt1];
    if ((localLineInfo != null) && ((localLineInfo.flags & 0x2) != 0))
      return localLineInfo.alignment;
    return paramInt2;
  }

  Color getLineBackground(int paramInt, Color paramColor)
  {
    if (this.lines == null)
      return paramColor;
    LineInfo localLineInfo = this.lines[paramInt];
    if ((localLineInfo != null) && ((localLineInfo.flags & 0x1) != 0))
      return localLineInfo.background;
    return paramColor;
  }

  Bullet getLineBullet(int paramInt, Bullet paramBullet)
  {
    if (this.bullets == null)
      return paramBullet;
    if (this.bulletsIndices != null)
      return paramBullet;
    for (int i = 0; i < this.bullets.length; i++)
    {
      Bullet localBullet = this.bullets[i];
      if (localBullet.indexOf(paramInt) != -1)
        return localBullet;
    }
    return paramBullet;
  }

  int getLineHeight()
  {
    return this.ascent + this.descent;
  }

  int getLineHeight(int paramInt)
  {
    if (this.lineHeight[paramInt] == -1)
      calculate(paramInt, 1);
    return this.lineHeight[paramInt];
  }

  int getLineIndent(int paramInt1, int paramInt2)
  {
    if (this.lines == null)
      return paramInt2;
    LineInfo localLineInfo = this.lines[paramInt1];
    if ((localLineInfo != null) && ((localLineInfo.flags & 0x4) != 0))
      return localLineInfo.indent;
    return paramInt2;
  }

  int getLineWrapIndent(int paramInt1, int paramInt2)
  {
    if (this.lines == null)
      return paramInt2;
    LineInfo localLineInfo = this.lines[paramInt1];
    if ((localLineInfo != null) && ((localLineInfo.flags & 0x80) != 0))
      return localLineInfo.wrapIndent;
    return paramInt2;
  }

  boolean getLineJustify(int paramInt, boolean paramBoolean)
  {
    if (this.lines == null)
      return paramBoolean;
    LineInfo localLineInfo = this.lines[paramInt];
    if ((localLineInfo != null) && ((localLineInfo.flags & 0x8) != 0))
      return localLineInfo.justify;
    return paramBoolean;
  }

  int[] getLineTabStops(int paramInt, int[] paramArrayOfInt)
  {
    if (this.lines == null)
      return paramArrayOfInt;
    LineInfo localLineInfo = this.lines[paramInt];
    if ((localLineInfo != null) && ((localLineInfo.flags & 0x40) != 0))
      return localLineInfo.tabStops;
    return paramArrayOfInt;
  }

  int getRangeIndex(int paramInt1, int paramInt2, int paramInt3)
  {
    if (this.styleCount == 0)
      return 0;
    if (this.ranges != null)
      while (paramInt3 - paramInt2 > 2)
      {
        i = (paramInt3 + paramInt2) / 2 / 2 * 2;
        j = this.ranges[i] + this.ranges[(i + 1)];
        if (j > paramInt1)
          paramInt3 = i;
        else
          paramInt2 = i;
      }
    else
      while (paramInt3 - paramInt2 > 1)
      {
        int i = (paramInt3 + paramInt2) / 2;
        int j = this.styles[i].start + this.styles[i].length;
        if (j > paramInt1)
          paramInt3 = i;
        else
          paramInt2 = i;
      }
    return paramInt3;
  }

  int[] getRanges(int paramInt1, int paramInt2)
  {
    if (paramInt2 == 0)
      return null;
    int i = paramInt1 + paramInt2 - 1;
    int j;
    int k;
    int m;
    int[] arrayOfInt;
    if (this.ranges != null)
    {
      j = this.styleCount << 1;
      k = getRangeIndex(paramInt1, -1, j);
      if (k >= j)
        return null;
      if (this.ranges[k] > i)
        return null;
      m = Math.min(j - 2, getRangeIndex(i, k - 1, j));
      if (this.ranges[m] > i)
        m = Math.max(k, m - 2);
      arrayOfInt = new int[m - k + 2];
      System.arraycopy(this.ranges, k, arrayOfInt, 0, arrayOfInt.length);
    }
    else
    {
      j = getRangeIndex(paramInt1, -1, this.styleCount);
      if (j >= this.styleCount)
        return null;
      if (this.styles[j].start > i)
        return null;
      k = Math.min(this.styleCount - 1, getRangeIndex(i, j - 1, this.styleCount));
      if (this.styles[k].start > i)
        k = Math.max(j, k - 1);
      arrayOfInt = new int[k - j + 1 << 1];
      m = j;
      for (int n = 0; m <= k; n += 2)
      {
        StyleRange localStyleRange = this.styles[m];
        arrayOfInt[n] = localStyleRange.start;
        arrayOfInt[(n + 1)] = localStyleRange.length;
        m++;
      }
    }
    if (paramInt1 > arrayOfInt[0])
    {
      arrayOfInt[1] = (arrayOfInt[0] + arrayOfInt[1] - paramInt1);
      arrayOfInt[0] = paramInt1;
    }
    if (i < arrayOfInt[(arrayOfInt.length - 2)] + arrayOfInt[(arrayOfInt.length - 1)] - 1)
      arrayOfInt[(arrayOfInt.length - 1)] = (i - arrayOfInt[(arrayOfInt.length - 2)] + 1);
    return arrayOfInt;
  }

  StyleRange[] getStyleRanges(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    if (paramInt2 == 0)
      return null;
    int i = paramInt1 + paramInt2 - 1;
    int j;
    int k;
    StyleRange[] arrayOfStyleRange;
    if (this.ranges != null)
    {
      j = this.styleCount << 1;
      k = getRangeIndex(paramInt1, -1, j);
      if (k >= j)
        return null;
      if (this.ranges[k] > i)
        return null;
      int m = Math.min(j - 2, getRangeIndex(i, k - 1, j));
      if (this.ranges[m] > i)
        m = Math.max(k, m - 2);
      arrayOfStyleRange = new StyleRange[(m - k >> 1) + 1];
      if (paramBoolean)
      {
        int n = k;
        for (int i1 = 0; n <= m; i1++)
        {
          arrayOfStyleRange[i1] = ((StyleRange)this.styles[(n >> 1)].clone());
          arrayOfStyleRange[i1].start = this.ranges[n];
          arrayOfStyleRange[i1].length = this.ranges[(n + 1)];
          n += 2;
        }
      }
      else
      {
        System.arraycopy(this.styles, k >> 1, arrayOfStyleRange, 0, arrayOfStyleRange.length);
      }
    }
    else
    {
      j = getRangeIndex(paramInt1, -1, this.styleCount);
      if (j >= this.styleCount)
        return null;
      if (this.styles[j].start > i)
        return null;
      k = Math.min(this.styleCount - 1, getRangeIndex(i, j - 1, this.styleCount));
      if (this.styles[k].start > i)
        k = Math.max(j, k - 1);
      arrayOfStyleRange = new StyleRange[k - j + 1];
      System.arraycopy(this.styles, j, arrayOfStyleRange, 0, arrayOfStyleRange.length);
    }
    if ((paramBoolean) || (this.ranges == null))
    {
      StyleRange localStyleRange = arrayOfStyleRange[0];
      if (paramInt1 > localStyleRange.start)
      {
        StyleRange tmp376_373 = ((StyleRange)localStyleRange.clone());
        localStyleRange = tmp376_373;
        arrayOfStyleRange[0] = tmp376_373;
        localStyleRange.length = (localStyleRange.start + localStyleRange.length - paramInt1);
        localStyleRange.start = paramInt1;
      }
      localStyleRange = arrayOfStyleRange[(arrayOfStyleRange.length - 1)];
      if (i < localStyleRange.start + localStyleRange.length - 1)
      {
        StyleRange tmp447_444 = ((StyleRange)localStyleRange.clone());
        localStyleRange = tmp447_444;
        arrayOfStyleRange[(arrayOfStyleRange.length - 1)] = tmp447_444;
        localStyleRange.length = (i - localStyleRange.start + 1);
      }
    }
    return arrayOfStyleRange;
  }

  StyleRange getStyleRange(StyleRange paramStyleRange)
  {
    if ((paramStyleRange.underline) && (paramStyleRange.underlineStyle == 4))
      this.hasLinks = true;
    if ((paramStyleRange.start == 0) && (paramStyleRange.length == 0) && (paramStyleRange.fontStyle == 0))
      return paramStyleRange;
    StyleRange localStyleRange = (StyleRange)paramStyleRange.clone();
    localStyleRange.start = (localStyleRange.length = 0);
    localStyleRange.fontStyle = 0;
    if (localStyleRange.font == null)
      localStyleRange.font = getFont(paramStyleRange.fontStyle);
    return localStyleRange;
  }

  TextLayout getTextLayout(int paramInt)
  {
    return getTextLayout(paramInt, this.styledText.getOrientation(), this.styledText.getWrapWidth(), this.styledText.lineSpacing);
  }

  TextLayout getTextLayout(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    TextLayout localTextLayout = null;
    if (this.styledText != null)
    {
      int i = this.styledText.topIndex > 0 ? this.styledText.topIndex - 1 : 0;
      if ((this.layouts == null) || (i != this.topIndex))
      {
        TextLayout[] arrayOfTextLayout = new TextLayout[''];
        int k;
        int m;
        if (this.layouts != null)
          for (k = 0; k < this.layouts.length; k++)
            if (this.layouts[k] != null)
            {
              m = k + this.topIndex - i;
              if ((m >= 0) && (m < arrayOfTextLayout.length))
                arrayOfTextLayout[m] = this.layouts[k];
              else
                this.layouts[k].dispose();
            }
        if ((this.bullets != null) && (this.bulletsIndices != null) && (i != this.topIndex))
        {
          k = i - this.topIndex;
          if (k > 0)
          {
            if (k < this.bullets.length)
            {
              System.arraycopy(this.bullets, k, this.bullets, 0, this.bullets.length - k);
              System.arraycopy(this.bulletsIndices, k, this.bulletsIndices, 0, this.bulletsIndices.length - k);
            }
            m = Math.max(0, this.bullets.length - k);
            for (n = m; n < this.bullets.length; n++)
              this.bullets[n] = null;
          }
          else
          {
            if (-k < this.bullets.length)
            {
              System.arraycopy(this.bullets, 0, this.bullets, -k, this.bullets.length + k);
              System.arraycopy(this.bulletsIndices, 0, this.bulletsIndices, -k, this.bulletsIndices.length + k);
            }
            m = Math.min(this.bullets.length, -k);
            for (n = 0; n < m; n++)
              this.bullets[n] = null;
          }
        }
        this.topIndex = i;
        this.layouts = arrayOfTextLayout;
      }
      if (this.layouts != null)
      {
        j = paramInt1 - i;
        if ((j >= 0) && (j < this.layouts.length))
        {
          localTextLayout = this.layouts[j];
          if (localTextLayout != null)
          {
            if (this.lineWidth[paramInt1] != -1)
              return localTextLayout;
          }
          else
            localTextLayout = this.layouts[j] =  = new TextLayout(this.device);
        }
      }
    }
    if (localTextLayout == null)
      localTextLayout = new TextLayout(this.device);
    String str = this.content.getLine(paramInt1);
    int j = this.content.getOffsetAtLine(paramInt1);
    int[] arrayOfInt1 = (int[])null;
    char[] arrayOfChar = (char[])null;
    int n = 0;
    int i1 = 0;
    int i2 = 16384;
    boolean bool = false;
    int[] arrayOfInt2 = { this.tabWidth };
    Bullet localBullet = null;
    int[] arrayOfInt3 = (int[])null;
    StyleRange[] arrayOfStyleRange = (StyleRange[])null;
    int i3 = 0;
    int i4 = 0;
    StyledTextEvent localStyledTextEvent = null;
    if (this.styledText != null)
    {
      localStyledTextEvent = this.styledText.getBidiSegments(j, str);
      if (localStyledTextEvent != null)
      {
        arrayOfInt1 = localStyledTextEvent.segments;
        arrayOfChar = localStyledTextEvent.segmentsChars;
      }
      localStyledTextEvent = this.styledText.getLineStyleData(j, str);
      n = this.styledText.indent;
      i1 = this.styledText.wrapIndent;
      i2 = this.styledText.alignment;
      bool = this.styledText.justify;
      if (this.styledText.tabs != null)
        arrayOfInt2 = this.styledText.tabs;
    }
    if (localStyledTextEvent != null)
    {
      n = localStyledTextEvent.indent;
      i1 = localStyledTextEvent.wrapIndent;
      i2 = localStyledTextEvent.alignment;
      bool = localStyledTextEvent.justify;
      localBullet = localStyledTextEvent.bullet;
      arrayOfInt3 = localStyledTextEvent.ranges;
      arrayOfStyleRange = localStyledTextEvent.styles;
      if (localStyledTextEvent.tabStops != null)
        arrayOfInt2 = localStyledTextEvent.tabStops;
      if (arrayOfStyleRange != null)
      {
        i4 = arrayOfStyleRange.length;
        if (this.styledText.isFixedLineHeight())
          for (i5 = 0; i5 < i4; i5++)
            if (arrayOfStyleRange[i5].isVariableHeight())
            {
              this.styledText.verticalScrollOffset = -1;
              this.styledText.setVariableLineHeight();
              this.styledText.redraw();
              break;
            }
      }
      if ((this.bullets == null) || (this.bulletsIndices == null))
      {
        this.bullets = new Bullet[''];
        this.bulletsIndices = new int[''];
      }
      int i5 = paramInt1 - this.topIndex;
      if ((i5 >= 0) && (i5 < 128))
      {
        this.bullets[i5] = localBullet;
        this.bulletsIndices[i5] = localStyledTextEvent.bulletIndex;
      }
    }
    else
    {
      if (this.lines != null)
      {
        LineInfo localLineInfo = this.lines[paramInt1];
        if (localLineInfo != null)
        {
          if ((localLineInfo.flags & 0x4) != 0)
            n = localLineInfo.indent;
          if ((localLineInfo.flags & 0x80) != 0)
            i1 = localLineInfo.wrapIndent;
          if ((localLineInfo.flags & 0x2) != 0)
            i2 = localLineInfo.alignment;
          if ((localLineInfo.flags & 0x8) != 0)
            bool = localLineInfo.justify;
          if ((localLineInfo.flags & 0x20) != 0)
            arrayOfInt1 = localLineInfo.segments;
          if ((localLineInfo.flags & 0x100) != 0)
            arrayOfChar = localLineInfo.segmentsChars;
          if ((localLineInfo.flags & 0x40) != 0)
            arrayOfInt2 = localLineInfo.tabStops;
        }
      }
      if (this.bulletsIndices != null)
      {
        this.bullets = null;
        this.bulletsIndices = null;
      }
      if (this.bullets != null)
        for (int i6 = 0; i6 < this.bullets.length; i6++)
          if (this.bullets[i6].indexOf(paramInt1) != -1)
          {
            localBullet = this.bullets[i6];
            break;
          }
      arrayOfInt3 = this.ranges;
      arrayOfStyleRange = this.styles;
      i4 = this.styleCount;
      if (arrayOfInt3 != null)
        i3 = getRangeIndex(j, -1, i4 << 1);
      else
        i3 = getRangeIndex(j, -1, i4);
    }
    if (localBullet != null)
    {
      StyleRange localStyleRange = localBullet.style;
      GlyphMetrics localGlyphMetrics = localStyleRange.metrics;
      n += localGlyphMetrics.width;
    }
    localTextLayout.setFont(this.regularFont);
    localTextLayout.setAscent(this.ascent);
    localTextLayout.setDescent(this.descent);
    localTextLayout.setText(str);
    localTextLayout.setOrientation(paramInt2);
    localTextLayout.setSegments(arrayOfInt1);
    localTextLayout.setSegmentsChars(arrayOfChar);
    localTextLayout.setWidth(paramInt3);
    localTextLayout.setSpacing(paramInt4);
    localTextLayout.setTabs(arrayOfInt2);
    localTextLayout.setIndent(n);
    localTextLayout.setWrapIndent(i1);
    localTextLayout.setAlignment(i2);
    localTextLayout.setJustify(bool);
    int i7 = 0;
    int i8 = str.length();
    int i11;
    int i12;
    int i13;
    if (arrayOfStyleRange != null)
    {
      int i9;
      if (arrayOfInt3 != null)
      {
        i9 = i4 << 1;
        for (i11 = i3; i11 < i9; i11 += 2)
        {
          if (j > arrayOfInt3[i11])
          {
            i12 = 0;
            i13 = Math.min(i8, arrayOfInt3[(i11 + 1)] - j + arrayOfInt3[i11]);
          }
          else
          {
            i12 = arrayOfInt3[i11] - j;
            i13 = Math.min(i8, i12 + arrayOfInt3[(i11 + 1)]);
          }
          if (i12 >= i8)
            break;
          if (i7 < i12)
            localTextLayout.setStyle(null, i7, i12 - 1);
          localTextLayout.setStyle(getStyleRange(arrayOfStyleRange[(i11 >> 1)]), i12, i13);
          i7 = Math.max(i7, i13);
        }
      }
      else
      {
        for (i9 = i3; i9 < i4; i9++)
        {
          if (j > arrayOfStyleRange[i9].start)
          {
            i11 = 0;
            i12 = Math.min(i8, arrayOfStyleRange[i9].length - j + arrayOfStyleRange[i9].start);
          }
          else
          {
            i11 = arrayOfStyleRange[i9].start - j;
            i12 = Math.min(i8, i11 + arrayOfStyleRange[i9].length);
          }
          if (i11 >= i8)
            break;
          if (i7 < i11)
            localTextLayout.setStyle(null, i7, i11 - 1);
          localTextLayout.setStyle(getStyleRange(arrayOfStyleRange[i9]), i11, i12);
          i7 = Math.max(i7, i12);
        }
      }
    }
    if (i7 < i8)
      localTextLayout.setStyle(null, i7, i8);
    int i14;
    if ((this.styledText != null) && (this.styledText.ime != null))
    {
      IME localIME = this.styledText.ime;
      i11 = localIME.getCompositionOffset();
      if (i11 != -1)
      {
        i12 = localIME.getCommitCount();
        i13 = localIME.getText().length();
        if (i13 != i12)
        {
          i14 = this.content.getLineAtOffset(i11);
          if (i14 == paramInt1)
          {
            int[] arrayOfInt4 = localIME.getRanges();
            TextStyle[] arrayOfTextStyle = localIME.getStyles();
            int i17;
            int i18;
            TextStyle localTextStyle2;
            if (arrayOfInt4.length > 0)
            {
              for (i17 = 0; i17 < arrayOfTextStyle.length; i17++)
              {
                i18 = arrayOfInt4[(i17 * 2)] - j;
                int i19 = arrayOfInt4[(i17 * 2 + 1)] - j;
                localTextStyle2 = arrayOfTextStyle[i17];
                for (int i20 = i18; i20 <= i19; i20++)
                {
                  TextStyle localTextStyle3 = localTextLayout.getStyle(i20);
                  if ((localTextStyle3 == null) && (i20 > 0))
                    localTextStyle3 = localTextLayout.getStyle(i20 - 1);
                  if ((localTextStyle3 == null) && (i20 + 1 < i8))
                    localTextStyle3 = localTextLayout.getStyle(i20 + 1);
                  if (localTextStyle3 == null)
                  {
                    localTextLayout.setStyle(localTextStyle2, i20, i20);
                  }
                  else
                  {
                    TextStyle localTextStyle4 = new TextStyle(localTextStyle2);
                    if (localTextStyle4.font == null)
                      localTextStyle4.font = localTextStyle3.font;
                    if (localTextStyle4.foreground == null)
                      localTextStyle4.foreground = localTextStyle3.foreground;
                    if (localTextStyle4.background == null)
                      localTextStyle4.background = localTextStyle3.background;
                    localTextLayout.setStyle(localTextStyle4, i20, i20);
                  }
                }
              }
            }
            else
            {
              i17 = i11 - j;
              i18 = i17 + i13 - 1;
              TextStyle localTextStyle1 = localTextLayout.getStyle(i17);
              if (localTextStyle1 == null)
              {
                if (i17 > 0)
                  localTextStyle1 = localTextLayout.getStyle(i17 - 1);
                if ((localTextStyle1 == null) && (i18 + 1 < i8))
                  localTextStyle1 = localTextLayout.getStyle(i18 + 1);
                if (localTextStyle1 != null)
                {
                  localTextStyle2 = new TextStyle();
                  localTextStyle2.font = localTextStyle1.font;
                  localTextStyle2.foreground = localTextStyle1.foreground;
                  localTextStyle2.background = localTextStyle1.background;
                  localTextLayout.setStyle(localTextStyle2, i17, i18);
                }
              }
            }
          }
        }
      }
    }
    if ((this.styledText != null) && (this.styledText.isFixedLineHeight()))
    {
      int i10 = -1;
      i11 = localTextLayout.getLineCount();
      i12 = getLineHeight();
      for (i13 = 0; i13 < i11; i13++)
      {
        i14 = localTextLayout.getLineBounds(i13).height;
        if (i14 > i12)
        {
          i12 = i14;
          i10 = i13;
        }
      }
      if (i10 != -1)
      {
        FontMetrics localFontMetrics = localTextLayout.getLineMetrics(i10);
        this.ascent = (localFontMetrics.getAscent() + localFontMetrics.getLeading());
        this.descent = localFontMetrics.getDescent();
        if (this.layouts != null)
          for (i14 = 0; i14 < this.layouts.length; i14++)
            if ((this.layouts[i14] != null) && (this.layouts[i14] != localTextLayout))
            {
              this.layouts[i14].setAscent(this.ascent);
              this.layouts[i14].setDescent(this.descent);
            }
        if (this.styledText.verticalScrollOffset != 0)
        {
          i14 = this.styledText.topIndex;
          int i15 = this.styledText.topIndexY;
          int i16 = getLineHeight();
          if (i15 >= 0)
            this.styledText.verticalScrollOffset = ((i14 - 1) * i16 + i16 - i15);
          else
            this.styledText.verticalScrollOffset = (i14 * i16 - i15);
        }
        this.styledText.calculateScrollBars();
        if (this.styledText.isBidiCaret())
          this.styledText.createCaretBitmaps();
        this.styledText.caretDirection = 0;
        this.styledText.setCaretLocation();
        this.styledText.redraw();
      }
    }
    return localTextLayout;
  }

  int getWidth()
  {
    return this.maxWidth;
  }

  void reset()
  {
    if (this.layouts != null)
    {
      for (int i = 0; i < this.layouts.length; i++)
      {
        TextLayout localTextLayout = this.layouts[i];
        if (localTextLayout != null)
          localTextLayout.dispose();
      }
      this.layouts = null;
    }
    this.topIndex = -1;
    this.stylesSetCount = (this.styleCount = this.lineCount = 0);
    this.ranges = null;
    this.styles = null;
    this.stylesSet = null;
    this.lines = null;
    this.lineWidth = null;
    this.lineHeight = null;
    this.bullets = null;
    this.bulletsIndices = null;
    this.redrawLines = null;
    this.hasLinks = false;
  }

  void reset(int paramInt1, int paramInt2)
  {
    int i = paramInt1 + paramInt2;
    if ((paramInt1 < 0) || (i > this.lineWidth.length))
      return;
    for (int j = paramInt1; j < i; j++)
    {
      this.lineWidth[j] = -1;
      this.lineHeight[j] = -1;
    }
    if ((paramInt1 <= this.maxWidthLineIndex) && (this.maxWidthLineIndex < i))
    {
      this.maxWidth = 0;
      this.maxWidthLineIndex = -1;
      if (paramInt2 != this.lineCount)
        for (j = 0; j < this.lineCount; j++)
          if (this.lineWidth[j] > this.maxWidth)
          {
            this.maxWidth = this.lineWidth[j];
            this.maxWidthLineIndex = j;
          }
    }
  }

  void setContent(StyledTextContent paramStyledTextContent)
  {
    reset();
    this.content = paramStyledTextContent;
    this.lineCount = paramStyledTextContent.getLineCount();
    this.lineWidth = new int[this.lineCount];
    this.lineHeight = new int[this.lineCount];
    reset(0, this.lineCount);
  }

  void setFont(Font paramFont, int paramInt)
  {
    TextLayout localTextLayout = new TextLayout(this.device);
    localTextLayout.setFont(this.regularFont);
    if (paramFont != null)
    {
      if (this.boldFont != null)
        this.boldFont.dispose();
      if (this.italicFont != null)
        this.italicFont.dispose();
      if (this.boldItalicFont != null)
        this.boldItalicFont.dispose();
      this.boldFont = (this.italicFont = this.boldItalicFont = null);
      this.regularFont = paramFont;
      localTextLayout.setText("    ");
      localTextLayout.setFont(paramFont);
      localTextLayout.setStyle(new TextStyle(getFont(0), null, null), 0, 0);
      localTextLayout.setStyle(new TextStyle(getFont(1), null, null), 1, 1);
      localTextLayout.setStyle(new TextStyle(getFont(2), null, null), 2, 2);
      localTextLayout.setStyle(new TextStyle(getFont(3), null, null), 3, 3);
      localObject = localTextLayout.getLineMetrics(0);
      this.ascent = (((FontMetrics)localObject).getAscent() + ((FontMetrics)localObject).getLeading());
      this.descent = ((FontMetrics)localObject).getDescent();
      this.boldFont.dispose();
      this.italicFont.dispose();
      this.boldItalicFont.dispose();
      this.boldFont = (this.italicFont = this.boldItalicFont = null);
    }
    localTextLayout.dispose();
    localTextLayout = new TextLayout(this.device);
    localTextLayout.setFont(this.regularFont);
    Object localObject = new StringBuffer(paramInt);
    for (int i = 0; i < paramInt; i++)
      ((StringBuffer)localObject).append(' ');
    localTextLayout.setText(((StringBuffer)localObject).toString());
    this.tabWidth = localTextLayout.getBounds().width;
    localTextLayout.dispose();
    if (this.styledText != null)
    {
      GC localGC = new GC(this.styledText);
      this.averageCharWidth = localGC.getFontMetrics().getAverageCharWidth();
      this.fixedPitch = (localGC.stringExtent("l").x == localGC.stringExtent("W").x);
      localGC.dispose();
    }
  }

  void setLineAlignment(int paramInt1, int paramInt2, int paramInt3)
  {
    if (this.lines == null)
      this.lines = new LineInfo[this.lineCount];
    for (int i = paramInt1; i < paramInt1 + paramInt2; i++)
    {
      if (this.lines[i] == null)
        this.lines[i] = new LineInfo();
      this.lines[i].flags |= 2;
      this.lines[i].alignment = paramInt3;
    }
  }

  void setLineBackground(int paramInt1, int paramInt2, Color paramColor)
  {
    if (this.lines == null)
      this.lines = new LineInfo[this.lineCount];
    for (int i = paramInt1; i < paramInt1 + paramInt2; i++)
    {
      if (this.lines[i] == null)
        this.lines[i] = new LineInfo();
      this.lines[i].flags |= 1;
      this.lines[i].background = paramColor;
    }
  }

  void setLineBullet(int paramInt1, int paramInt2, Bullet paramBullet)
  {
    if (this.bulletsIndices != null)
    {
      this.bulletsIndices = null;
      this.bullets = null;
    }
    if (this.bullets == null)
    {
      if (paramBullet == null)
        return;
      this.bullets = new Bullet[1];
      this.bullets[0] = paramBullet;
    }
    for (int i = 0; i < this.bullets.length; i++)
      if (paramBullet == this.bullets[i])
        break;
    if (paramBullet != null)
    {
      if (i == this.bullets.length)
      {
        Bullet[] arrayOfBullet = new Bullet[this.bullets.length + 1];
        System.arraycopy(this.bullets, 0, arrayOfBullet, 0, this.bullets.length);
        arrayOfBullet[i] = paramBullet;
        this.bullets = arrayOfBullet;
      }
      paramBullet.addIndices(paramInt1, paramInt2);
    }
    else
    {
      updateBullets(paramInt1, paramInt2, 0, false);
      this.styledText.redrawLinesBullet(this.redrawLines);
      this.redrawLines = null;
    }
  }

  void setLineIndent(int paramInt1, int paramInt2, int paramInt3)
  {
    if (this.lines == null)
      this.lines = new LineInfo[this.lineCount];
    for (int i = paramInt1; i < paramInt1 + paramInt2; i++)
    {
      if (this.lines[i] == null)
        this.lines[i] = new LineInfo();
      this.lines[i].flags |= 4;
      this.lines[i].indent = paramInt3;
    }
  }

  void setLineWrapIndent(int paramInt1, int paramInt2, int paramInt3)
  {
    if (this.lines == null)
      this.lines = new LineInfo[this.lineCount];
    for (int i = paramInt1; i < paramInt1 + paramInt2; i++)
    {
      if (this.lines[i] == null)
        this.lines[i] = new LineInfo();
      this.lines[i].flags |= 128;
      this.lines[i].wrapIndent = paramInt3;
    }
  }

  void setLineJustify(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    if (this.lines == null)
      this.lines = new LineInfo[this.lineCount];
    for (int i = paramInt1; i < paramInt1 + paramInt2; i++)
    {
      if (this.lines[i] == null)
        this.lines[i] = new LineInfo();
      this.lines[i].flags |= 8;
      this.lines[i].justify = paramBoolean;
    }
  }

  void setLineSegments(int paramInt1, int paramInt2, int[] paramArrayOfInt)
  {
    if (this.lines == null)
      this.lines = new LineInfo[this.lineCount];
    for (int i = paramInt1; i < paramInt1 + paramInt2; i++)
    {
      if (this.lines[i] == null)
        this.lines[i] = new LineInfo();
      this.lines[i].flags |= 32;
      this.lines[i].segments = paramArrayOfInt;
    }
  }

  void setLineSegmentChars(int paramInt1, int paramInt2, char[] paramArrayOfChar)
  {
    if (this.lines == null)
      this.lines = new LineInfo[this.lineCount];
    for (int i = paramInt1; i < paramInt1 + paramInt2; i++)
    {
      if (this.lines[i] == null)
        this.lines[i] = new LineInfo();
      this.lines[i].flags |= 256;
      this.lines[i].segmentsChars = paramArrayOfChar;
    }
  }

  void setLineTabStops(int paramInt1, int paramInt2, int[] paramArrayOfInt)
  {
    if (this.lines == null)
      this.lines = new LineInfo[this.lineCount];
    for (int i = paramInt1; i < paramInt1 + paramInt2; i++)
    {
      if (this.lines[i] == null)
        this.lines[i] = new LineInfo();
      this.lines[i].flags |= 64;
      this.lines[i].tabStops = paramArrayOfInt;
    }
  }

  void setStyleRanges(int[] paramArrayOfInt, StyleRange[] paramArrayOfStyleRange)
  {
    if (paramArrayOfStyleRange == null)
    {
      this.stylesSetCount = (this.styleCount = 0);
      this.ranges = null;
      this.styles = null;
      this.stylesSet = null;
      this.hasLinks = false;
      return;
    }
    int j;
    int k;
    int n;
    if (paramArrayOfInt == null)
    {
      paramArrayOfInt = new int[paramArrayOfStyleRange.length << 1];
      StyleRange[] arrayOfStyleRange1 = new StyleRange[paramArrayOfStyleRange.length];
      if (this.stylesSet == null)
        this.stylesSet = new StyleRange[4];
      j = 0;
      k = 0;
      while (j < paramArrayOfStyleRange.length)
      {
        StyleRange localStyleRange1 = paramArrayOfStyleRange[j];
        paramArrayOfInt[(k++)] = localStyleRange1.start;
        paramArrayOfInt[(k++)] = localStyleRange1.length;
        for (n = 0; n < this.stylesSetCount; n++)
          if (this.stylesSet[n].similarTo(localStyleRange1))
            break;
        if (n == this.stylesSetCount)
        {
          if (this.stylesSetCount == this.stylesSet.length)
          {
            StyleRange[] arrayOfStyleRange3 = new StyleRange[this.stylesSetCount + 4];
            System.arraycopy(this.stylesSet, 0, arrayOfStyleRange3, 0, this.stylesSetCount);
            this.stylesSet = arrayOfStyleRange3;
          }
          this.stylesSet[(this.stylesSetCount++)] = localStyleRange1;
        }
        arrayOfStyleRange1[j] = this.stylesSet[n];
        j++;
      }
      paramArrayOfStyleRange = arrayOfStyleRange1;
    }
    if (this.styleCount == 0)
    {
      if (paramArrayOfInt != null)
      {
        this.ranges = new int[paramArrayOfInt.length];
        System.arraycopy(paramArrayOfInt, 0, this.ranges, 0, this.ranges.length);
      }
      this.styles = new StyleRange[paramArrayOfStyleRange.length];
      System.arraycopy(paramArrayOfStyleRange, 0, this.styles, 0, this.styles.length);
      this.styleCount = paramArrayOfStyleRange.length;
      return;
    }
    int i;
    if ((paramArrayOfInt != null) && (this.ranges == null))
    {
      this.ranges = new int[this.styles.length << 1];
      i = 0;
      j = 0;
      while (i < this.styleCount)
      {
        this.ranges[(j++)] = this.styles[i].start;
        this.ranges[(j++)] = this.styles[i].length;
        i++;
      }
    }
    if ((paramArrayOfInt == null) && (this.ranges != null))
    {
      paramArrayOfInt = new int[paramArrayOfStyleRange.length << 1];
      i = 0;
      j = 0;
      while (i < paramArrayOfStyleRange.length)
      {
        paramArrayOfInt[(j++)] = paramArrayOfStyleRange[i].start;
        paramArrayOfInt[(j++)] = paramArrayOfStyleRange[i].length;
        i++;
      }
    }
    int m;
    Object localObject;
    int i4;
    int i5;
    int i6;
    int i7;
    int i8;
    if (this.ranges != null)
    {
      i = this.styleCount << 1;
      j = paramArrayOfInt[0];
      k = getRangeIndex(j, -1, i);
      n = k == i ? 1 : 0;
      if (n == 0)
      {
        int i1 = paramArrayOfInt[(paramArrayOfInt.length - 2)] + paramArrayOfInt[(paramArrayOfInt.length - 1)];
        m = getRangeIndex(i1, k - 1, i);
        n = (k == m) && (this.ranges[k] >= i1) ? 1 : 0;
      }
      if (n != 0)
      {
        addMerge(paramArrayOfInt, paramArrayOfStyleRange, paramArrayOfInt.length, k, k);
        return;
      }
      m = k;
      int[] arrayOfInt = new int[6];
      localObject = new StyleRange[3];
      for (int i3 = 0; i3 < paramArrayOfInt.length; i3 += 2)
      {
        i4 = paramArrayOfInt[i3];
        i5 = i4 + paramArrayOfInt[(i3 + 1)];
        if (i4 != i5)
        {
          i6 = 0;
          i7 = 0;
          while (m < i)
          {
            if (i4 >= this.ranges[k] + this.ranges[(k + 1)])
              k += 2;
            if (this.ranges[m] + this.ranges[(m + 1)] > i5)
              break;
            m += 2;
          }
          if ((this.ranges[k] < i4) && (i4 < this.ranges[k] + this.ranges[(k + 1)]))
          {
            localObject[(i7 >> 1)] = this.styles[(k >> 1)];
            arrayOfInt[i7] = this.ranges[k];
            arrayOfInt[(i7 + 1)] = (i4 - this.ranges[k]);
            i7 += 2;
          }
          localObject[(i7 >> 1)] = paramArrayOfStyleRange[(i3 >> 1)];
          arrayOfInt[i7] = i4;
          arrayOfInt[(i7 + 1)] = paramArrayOfInt[(i3 + 1)];
          i7 += 2;
          if ((m < i) && (this.ranges[m] < i5) && (i5 < this.ranges[m] + this.ranges[(m + 1)]))
          {
            localObject[(i7 >> 1)] = this.styles[(m >> 1)];
            arrayOfInt[i7] = i5;
            arrayOfInt[(i7 + 1)] = (this.ranges[m] + this.ranges[(m + 1)] - i5);
            i7 += 2;
            i6 = 2;
          }
          i8 = addMerge(arrayOfInt, (StyleRange[])localObject, i7, k, m + i6);
          i += i8;
          k = m += i8;
        }
      }
    }
    else
    {
      i = paramArrayOfStyleRange[0].start;
      j = getRangeIndex(i, -1, this.styleCount);
      m = j == this.styleCount ? 1 : 0;
      if (m == 0)
      {
        n = paramArrayOfStyleRange[(paramArrayOfStyleRange.length - 1)].start + paramArrayOfStyleRange[(paramArrayOfStyleRange.length - 1)].length;
        k = getRangeIndex(n, j - 1, this.styleCount);
        m = (j == k) && (this.styles[j].start >= n) ? 1 : 0;
      }
      if (m != 0)
      {
        addMerge(paramArrayOfStyleRange, paramArrayOfStyleRange.length, j, j);
        return;
      }
      k = j;
      StyleRange[] arrayOfStyleRange2 = new StyleRange[3];
      for (int i2 = 0; i2 < paramArrayOfStyleRange.length; i2++)
      {
        localObject = paramArrayOfStyleRange[i2];
        i4 = ((StyleRange)localObject).start;
        i5 = i4 + ((StyleRange)localObject).length;
        if (i4 != i5)
        {
          i6 = 0;
          i7 = 0;
          while (k < this.styleCount)
          {
            if (i4 >= this.styles[j].start + this.styles[j].length)
              j++;
            if (this.styles[k].start + this.styles[k].length > i5)
              break;
            k++;
          }
          StyleRange localStyleRange2 = this.styles[j];
          if ((localStyleRange2.start < i4) && (i4 < localStyleRange2.start + localStyleRange2.length))
          {
            localStyleRange2 = arrayOfStyleRange2[(i7++)] =  = (StyleRange)localStyleRange2.clone();
            localStyleRange2.length = (i4 - localStyleRange2.start);
          }
          arrayOfStyleRange2[(i7++)] = localObject;
          if (k < this.styleCount)
          {
            localStyleRange2 = this.styles[k];
            if ((localStyleRange2.start < i5) && (i5 < localStyleRange2.start + localStyleRange2.length))
            {
              localStyleRange2 = arrayOfStyleRange2[(i7++)] =  = (StyleRange)localStyleRange2.clone();
              localStyleRange2.length += localStyleRange2.start - i5;
              localStyleRange2.start = i5;
              i6 = 1;
            }
          }
          i8 = addMerge(arrayOfStyleRange2, i7, j, k + i6);
          j = k += i8;
        }
      }
    }
  }

  void textChanging(TextChangingEvent paramTextChangingEvent)
  {
    int i = paramTextChangingEvent.start;
    int j = paramTextChangingEvent.newCharCount;
    int k = paramTextChangingEvent.replaceCharCount;
    int m = paramTextChangingEvent.newLineCount;
    int n = paramTextChangingEvent.replaceLineCount;
    updateRanges(i, k, j);
    int i1 = this.content.getLineAtOffset(i);
    if (k == this.content.getCharCount())
      this.lines = null;
    if (n == this.lineCount)
    {
      this.lineCount = m;
      this.lineWidth = new int[this.lineCount];
      this.lineHeight = new int[this.lineCount];
      reset(0, this.lineCount);
    }
    else
    {
      int i2 = m - n;
      Object localObject;
      if (this.lineCount + i2 > this.lineWidth.length)
      {
        localObject = new int[this.lineCount + i2 + 32];
        System.arraycopy(this.lineWidth, 0, localObject, 0, this.lineCount);
        this.lineWidth = ((int[])localObject);
        int[] arrayOfInt = new int[this.lineCount + i2 + 32];
        System.arraycopy(this.lineHeight, 0, arrayOfInt, 0, this.lineCount);
        this.lineHeight = arrayOfInt;
      }
      if ((this.lines != null) && (this.lineCount + i2 > this.lines.length))
      {
        localObject = new LineInfo[this.lineCount + i2 + 32];
        System.arraycopy(this.lines, 0, localObject, 0, this.lineCount);
        this.lines = ((LineInfo[])localObject);
      }
      int i3 = i1 + n + 1;
      int i4 = i1 + m + 1;
      System.arraycopy(this.lineWidth, i3, this.lineWidth, i4, this.lineCount - i3);
      System.arraycopy(this.lineHeight, i3, this.lineHeight, i4, this.lineCount - i3);
      for (int i5 = i1; i5 < i4; i5++)
      {
        byte tmp346_345 = -1;
        this.lineHeight[i5] = tmp346_345;
        this.lineWidth[i5] = tmp346_345;
      }
      for (i5 = this.lineCount + i2; i5 < this.lineCount; i5++)
      {
        byte tmp384_383 = -1;
        this.lineHeight[i5] = tmp384_383;
        this.lineWidth[i5] = tmp384_383;
      }
      int i6;
      if (this.layouts != null)
      {
        i5 = i1 - this.topIndex;
        i6 = i5 + n + 1;
        for (int i7 = i5; i7 < i6; i7++)
          if ((i7 >= 0) && (i7 < this.layouts.length))
          {
            if (this.layouts[i7] != null)
              this.layouts[i7].dispose();
            this.layouts[i7] = null;
            if ((this.bullets != null) && (this.bulletsIndices != null))
              this.bullets[i7] = null;
          }
        if (i2 > 0)
          for (i7 = this.layouts.length - 1; i7 >= i6; i7--)
            if ((i7 >= 0) && (i7 < this.layouts.length))
            {
              i4 = i7 + i2;
              if ((i4 >= 0) && (i4 < this.layouts.length))
              {
                this.layouts[i4] = this.layouts[i7];
                this.layouts[i7] = null;
                if ((this.bullets != null) && (this.bulletsIndices != null))
                {
                  this.bullets[i4] = this.bullets[i7];
                  this.bulletsIndices[i4] = this.bulletsIndices[i7];
                  this.bullets[i7] = null;
                }
              }
              else
              {
                if (this.layouts[i7] != null)
                  this.layouts[i7].dispose();
                this.layouts[i7] = null;
                if ((this.bullets != null) && (this.bulletsIndices != null))
                  this.bullets[i7] = null;
              }
            }
        else if (i2 < 0)
          for (i7 = i6; i7 < this.layouts.length; i7++)
            if ((i7 >= 0) && (i7 < this.layouts.length))
            {
              i4 = i7 + i2;
              if ((i4 >= 0) && (i4 < this.layouts.length))
              {
                this.layouts[i4] = this.layouts[i7];
                this.layouts[i7] = null;
                if ((this.bullets != null) && (this.bulletsIndices != null))
                {
                  this.bullets[i4] = this.bullets[i7];
                  this.bulletsIndices[i4] = this.bulletsIndices[i7];
                  this.bullets[i7] = null;
                }
              }
              else
              {
                if (this.layouts[i7] != null)
                  this.layouts[i7].dispose();
                this.layouts[i7] = null;
                if ((this.bullets != null) && (this.bulletsIndices != null))
                  this.bullets[i7] = null;
              }
            }
      }
      if ((n != 0) || (m != 0))
      {
        i5 = this.content.getOffsetAtLine(i1);
        if (i5 != i)
          i1++;
        updateBullets(i1, n, m, true);
        if (this.lines != null)
        {
          i3 = i1 + n;
          i4 = i1 + m;
          System.arraycopy(this.lines, i3, this.lines, i4, this.lineCount - i3);
          for (i6 = i1; i6 < i4; i6++)
            this.lines[i6] = null;
          for (i6 = this.lineCount + i2; i6 < this.lineCount; i6++)
            this.lines[i6] = null;
        }
      }
      this.lineCount += i2;
      if ((this.maxWidthLineIndex != -1) && (i1 <= this.maxWidthLineIndex) && (this.maxWidthLineIndex <= i1 + n))
      {
        this.maxWidth = 0;
        this.maxWidthLineIndex = -1;
        for (i5 = 0; i5 < this.lineCount; i5++)
          if (this.lineWidth[i5] > this.maxWidth)
          {
            this.maxWidth = this.lineWidth[i5];
            this.maxWidthLineIndex = i5;
          }
      }
    }
  }

  void updateBullets(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
  {
    if (this.bullets == null)
      return;
    if (this.bulletsIndices != null)
      return;
    for (int i = 0; i < this.bullets.length; i++)
    {
      Bullet localBullet1 = this.bullets[i];
      int[] arrayOfInt1 = localBullet1.removeIndices(paramInt1, paramInt2, paramInt3, paramBoolean);
      if (arrayOfInt1 != null)
        if (this.redrawLines == null)
        {
          this.redrawLines = arrayOfInt1;
        }
        else
        {
          int[] arrayOfInt2 = new int[this.redrawLines.length + arrayOfInt1.length];
          System.arraycopy(this.redrawLines, 0, arrayOfInt2, 0, this.redrawLines.length);
          System.arraycopy(arrayOfInt1, 0, arrayOfInt2, this.redrawLines.length, arrayOfInt1.length);
          this.redrawLines = arrayOfInt2;
        }
    }
    i = 0;
    for (int j = 0; j < this.bullets.length; j++)
      if (this.bullets[j].size() == 0)
        i++;
    if (i > 0)
      if (i == this.bullets.length)
      {
        this.bullets = null;
      }
      else
      {
        Bullet[] arrayOfBullet = new Bullet[this.bullets.length - i];
        int k = 0;
        int m = 0;
        while (k < this.bullets.length)
        {
          Bullet localBullet2 = this.bullets[k];
          if (localBullet2.size() > 0)
            arrayOfBullet[(m++)] = localBullet2;
          k++;
        }
        this.bullets = arrayOfBullet;
      }
  }

  void updateRanges(int paramInt1, int paramInt2, int paramInt3)
  {
    if ((this.styleCount == 0) || ((paramInt2 == 0) && (paramInt3 == 0)))
      return;
    int[] arrayOfInt1;
    int i;
    StyleRange[] arrayOfStyleRange1;
    int[] arrayOfInt2;
    if (this.ranges != null)
    {
      arrayOfInt1 = this.styleCount << 1;
      i = getRangeIndex(paramInt1, -1, arrayOfInt1);
      if (i == arrayOfInt1)
        return;
      arrayOfStyleRange1 = paramInt1 + paramInt2;
      arrayOfInt2 = getRangeIndex(arrayOfStyleRange1, i - 1, arrayOfInt1);
      int j = paramInt3 - paramInt2;
      int[] arrayOfInt3;
      if ((i == arrayOfInt2) && (this.ranges[i] < paramInt1) && (arrayOfStyleRange1 < this.ranges[arrayOfInt2] + this.ranges[(arrayOfInt2 + 1)]))
      {
        if (paramInt3 == 0)
        {
          this.ranges[(i + 1)] -= paramInt2;
          arrayOfInt2 += 2;
        }
        else
        {
          if (arrayOfInt1 + 2 > this.ranges.length)
          {
            arrayOfInt3 = new int[this.ranges.length + 64];
            System.arraycopy(this.ranges, 0, arrayOfInt3, 0, arrayOfInt1);
            this.ranges = arrayOfInt3;
            StyleRange[] arrayOfStyleRange3 = new StyleRange[this.styles.length + 32];
            System.arraycopy(this.styles, 0, arrayOfStyleRange3, 0, this.styleCount);
            this.styles = arrayOfStyleRange3;
          }
          System.arraycopy(this.ranges, i + 2, this.ranges, i + 4, arrayOfInt1 - (i + 2));
          System.arraycopy(this.styles, i + 2 >> 1, this.styles, i + 4 >> 1, this.styleCount - (i + 2 >> 1));
          this.ranges[(i + 3)] = (this.ranges[i] + this.ranges[(i + 1)] - arrayOfStyleRange1);
          this.ranges[(i + 2)] = (paramInt1 + paramInt3);
          this.ranges[(i + 1)] = (paramInt1 - this.ranges[i]);
          this.styles[((i >> 1) + 1)] = this.styles[(i >> 1)];
          arrayOfInt1 += 2;
          this.styleCount += 1;
          arrayOfInt2 += 4;
        }
        if (j != 0)
          for (arrayOfInt3 = arrayOfInt2; arrayOfInt3 < arrayOfInt1; arrayOfInt3 += 2)
            this.ranges[arrayOfInt3] += j;
      }
      else
      {
        if ((this.ranges[i] < paramInt1) && (paramInt1 < this.ranges[i] + this.ranges[(i + 1)]))
        {
          this.ranges[(i + 1)] = (paramInt1 - this.ranges[i]);
          i += 2;
        }
        if ((arrayOfInt2 < arrayOfInt1) && (this.ranges[arrayOfInt2] < arrayOfStyleRange1) && (arrayOfStyleRange1 < this.ranges[arrayOfInt2] + this.ranges[(arrayOfInt2 + 1)]))
        {
          this.ranges[(arrayOfInt2 + 1)] = (this.ranges[arrayOfInt2] + this.ranges[(arrayOfInt2 + 1)] - arrayOfStyleRange1);
          this.ranges[arrayOfInt2] = arrayOfStyleRange1;
        }
        if (j != 0)
          for (arrayOfInt3 = arrayOfInt2; arrayOfInt3 < arrayOfInt1; arrayOfInt3 += 2)
            this.ranges[arrayOfInt3] += j;
        System.arraycopy(this.ranges, arrayOfInt2, this.ranges, i, arrayOfInt1 - arrayOfInt2);
        System.arraycopy(this.styles, arrayOfInt2 >> 1, this.styles, i >> 1, this.styleCount - (arrayOfInt2 >> 1));
        this.styleCount -= (arrayOfInt2 - i >> 1);
      }
    }
    else
    {
      arrayOfInt1 = getRangeIndex(paramInt1, -1, this.styleCount);
      if (arrayOfInt1 == this.styleCount)
        return;
      i = paramInt1 + paramInt2;
      arrayOfStyleRange1 = getRangeIndex(i, arrayOfInt1 - 1, this.styleCount);
      arrayOfInt2 = paramInt3 - paramInt2;
      StyleRange[] arrayOfStyleRange2;
      if ((arrayOfInt1 == arrayOfStyleRange1) && (this.styles[arrayOfInt1].start < paramInt1) && (i < this.styles[arrayOfStyleRange1].start + this.styles[arrayOfStyleRange1].length))
      {
        if (paramInt3 == 0)
        {
          this.styles[arrayOfInt1].length -= paramInt2;
          arrayOfStyleRange1++;
        }
        else
        {
          if (this.styleCount + 1 > this.styles.length)
          {
            arrayOfStyleRange2 = new StyleRange[this.styles.length + 32];
            System.arraycopy(this.styles, 0, arrayOfStyleRange2, 0, this.styleCount);
            this.styles = arrayOfStyleRange2;
          }
          System.arraycopy(this.styles, arrayOfInt1 + 1, this.styles, arrayOfInt1 + 2, this.styleCount - (arrayOfInt1 + 1));
          this.styles[(arrayOfInt1 + 1)] = ((StyleRange)this.styles[arrayOfInt1].clone());
          this.styles[(arrayOfInt1 + 1)].length = (this.styles[arrayOfInt1].start + this.styles[arrayOfInt1].length - i);
          this.styles[(arrayOfInt1 + 1)].start = (paramInt1 + paramInt3);
          this.styles[arrayOfInt1].length = (paramInt1 - this.styles[arrayOfInt1].start);
          this.styleCount += 1;
          arrayOfStyleRange1 += 2;
        }
        if (arrayOfInt2 != 0)
          for (arrayOfStyleRange2 = arrayOfStyleRange1; arrayOfStyleRange2 < this.styleCount; arrayOfStyleRange2++)
            this.styles[arrayOfStyleRange2].start += arrayOfInt2;
      }
      else
      {
        if ((this.styles[arrayOfInt1].start < paramInt1) && (paramInt1 < this.styles[arrayOfInt1].start + this.styles[arrayOfInt1].length))
        {
          this.styles[arrayOfInt1].length = (paramInt1 - this.styles[arrayOfInt1].start);
          arrayOfInt1++;
        }
        if ((arrayOfStyleRange1 < this.styleCount) && (this.styles[arrayOfStyleRange1].start < i) && (i < this.styles[arrayOfStyleRange1].start + this.styles[arrayOfStyleRange1].length))
        {
          this.styles[arrayOfStyleRange1].length = (this.styles[arrayOfStyleRange1].start + this.styles[arrayOfStyleRange1].length - i);
          this.styles[arrayOfStyleRange1].start = i;
        }
        if (arrayOfInt2 != 0)
          for (arrayOfStyleRange2 = arrayOfStyleRange1; arrayOfStyleRange2 < this.styleCount; arrayOfStyleRange2++)
            this.styles[arrayOfStyleRange2].start += arrayOfInt2;
        System.arraycopy(this.styles, arrayOfStyleRange1, this.styles, arrayOfInt1, this.styleCount - arrayOfStyleRange1);
        this.styleCount -= arrayOfStyleRange1 - arrayOfInt1;
      }
    }
  }

  static class LineInfo
  {
    int flags;
    Color background;
    int alignment;
    int indent;
    int wrapIndent;
    boolean justify;
    int[] segments;
    char[] segmentsChars;
    int[] tabStops;

    public LineInfo()
    {
    }

    public LineInfo(LineInfo paramLineInfo)
    {
      if (paramLineInfo != null)
      {
        this.flags = paramLineInfo.flags;
        this.background = paramLineInfo.background;
        this.alignment = paramLineInfo.alignment;
        this.indent = paramLineInfo.indent;
        this.wrapIndent = paramLineInfo.wrapIndent;
        this.justify = paramLineInfo.justify;
        this.segments = paramLineInfo.segments;
        this.segmentsChars = paramLineInfo.segmentsChars;
        this.tabStops = paramLineInfo.tabStops;
      }
    }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.custom.StyledTextRenderer
 * JD-Core Version:    0.6.2
 */