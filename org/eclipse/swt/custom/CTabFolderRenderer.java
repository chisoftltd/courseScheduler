package org.eclipse.swt.custom;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.graphics.TextLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class CTabFolderRenderer
{
  protected CTabFolder parent;
  int[] curve;
  int[] topCurveHighlightStart;
  int[] topCurveHighlightEnd;
  int curveWidth = 0;
  int curveIndent = 0;
  int lastTabHeight = -1;
  Color fillColor;
  Color selectionHighlightGradientBegin = null;
  Color[] selectionHighlightGradientColorsCache = null;
  Color selectedOuterColor = null;
  Color selectedInnerColor = null;
  Color tabAreaColor = null;
  Color lastBorderColor = null;
  static final int[] TOP_LEFT_CORNER_HILITE = { 5, 2, 4, 2, 3, 3, 2, 4, 2, 5, 1, 6 };
  static final int[] TOP_LEFT_CORNER = { 0, 6, 1, 5, 1, 4, 4, 1, 5, 1, 6 };
  static final int[] TOP_RIGHT_CORNER = { -6, 0, -5, 1, -4, 1, -1, 4, -1, 5, 0, 6 };
  static final int[] BOTTOM_LEFT_CORNER = { 0, -6, 1, -5, 1, -4, 4, -1, 5, -1, 6 };
  static final int[] BOTTOM_RIGHT_CORNER = { -6, 0, -5, -1, -4, -1, -1, -4, -1, -5, 0, -6 };
  static final int[] SIMPLE_TOP_LEFT_CORNER = { 0, 2, 1, 1, 2 };
  static final int[] SIMPLE_TOP_RIGHT_CORNER = { -2, 0, -1, 1, 0, 2 };
  static final int[] SIMPLE_BOTTOM_LEFT_CORNER = { 0, -2, 1, -1, 2 };
  static final int[] SIMPLE_BOTTOM_RIGHT_CORNER = { -2, 0, -1, -1, 0, -2 };
  static final int[] SIMPLE_UNSELECTED_INNER_CORNER = new int[2];
  static final int[] TOP_LEFT_CORNER_BORDERLESS = { 0, 6, 1, 5, 1, 4, 4, 1, 5, 1, 6 };
  static final int[] TOP_RIGHT_CORNER_BORDERLESS = { -7, 0, -6, 1, -5, 1, -2, 4, -2, 5, -1, 6 };
  static final int[] BOTTOM_LEFT_CORNER_BORDERLESS = { 0, -6, 1, -6, 1, -5, 2, -4, 4, -2, 5, -1, 6, -1, 6 };
  static final int[] BOTTOM_RIGHT_CORNER_BORDERLESS = { -7, 0, -7, -1, -6, -1, -5, -2, -3, -4, -2, -5, -2, -6, -1, -6 };
  static final int[] SIMPLE_TOP_LEFT_CORNER_BORDERLESS = { 0, 2, 1, 1, 2 };
  static final int[] SIMPLE_TOP_RIGHT_CORNER_BORDERLESS = { -3, 0, -2, 1, -1, 2 };
  static final int[] SIMPLE_BOTTOM_LEFT_CORNER_BORDERLESS = { 0, -3, 1, -2, 2, -1, 3 };
  static final int[] SIMPLE_BOTTOM_RIGHT_CORNER_BORDERLESS = { -4, 0, -3, -1, -2, -2, -1, -3 };
  static final RGB CLOSE_FILL = new RGB(252, 160, 160);
  static final int BUTTON_SIZE = 18;
  static final int BUTTON_BORDER = 17;
  static final int BUTTON_FILL = 25;
  static final int BORDER1_COLOR = 18;
  static final int ITEM_TOP_MARGIN = 2;
  static final int ITEM_BOTTOM_MARGIN = 2;
  static final int ITEM_LEFT_MARGIN = 4;
  static final int ITEM_RIGHT_MARGIN = 4;
  static final int INTERNAL_SPACING = 4;
  static final int FLAGS = 9;
  static final String ELLIPSIS = "...";
  public static final int PART_BODY = -1;
  public static final int PART_HEADER = -2;
  public static final int PART_BORDER = -3;
  public static final int PART_BACKGROUND = -4;
  public static final int PART_MAX_BUTTON = -5;
  public static final int PART_MIN_BUTTON = -6;
  public static final int PART_CHEVRON_BUTTON = -7;
  public static final int PART_CLOSE_BUTTON = -8;
  public static final int MINIMUM_SIZE = 16777216;

  protected CTabFolderRenderer(CTabFolder paramCTabFolder)
  {
    if (paramCTabFolder == null)
      return;
    if (paramCTabFolder.isDisposed())
      SWT.error(5);
    this.parent = paramCTabFolder;
  }

  void antialias(int[] paramArrayOfInt, Color paramColor1, Color paramColor2, GC paramGC)
  {
    if (this.parent.simple)
      return;
    String str = SWT.getPlatform();
    if ("cocoa".equals(str))
      return;
    if ("carbon".equals(str))
      return;
    if ("wpf".equals(str))
      return;
    if (this.parent.getDisplay().getDepth() < 15)
      return;
    int j;
    int k;
    int n;
    if (paramColor2 != null)
    {
      int i = 0;
      j = 1;
      k = this.parent.onBottom ? 0 : this.parent.getSize().y;
      int[] arrayOfInt2 = new int[paramArrayOfInt.length];
      for (n = 0; n < paramArrayOfInt.length / 2; n++)
      {
        if ((j != 0) && (i + 3 < paramArrayOfInt.length))
        {
          j = k >= paramArrayOfInt[(i + 3)] ? 1 : this.parent.onBottom ? 0 : k <= paramArrayOfInt[(i + 3)] ? 1 : 0;
          k = paramArrayOfInt[(i + 1)];
        }
        arrayOfInt2[i] = (paramArrayOfInt[(i++)] + (j != 0 ? -1 : 1));
        arrayOfInt2[i] = paramArrayOfInt[(i++)];
      }
      paramGC.setForeground(paramColor2);
      paramGC.drawPolyline(arrayOfInt2);
    }
    if (paramColor1 != null)
    {
      int[] arrayOfInt1 = new int[paramArrayOfInt.length];
      j = 0;
      k = 1;
      int m = this.parent.onBottom ? 0 : this.parent.getSize().y;
      for (n = 0; n < paramArrayOfInt.length / 2; n++)
      {
        if ((k != 0) && (j + 3 < paramArrayOfInt.length))
        {
          k = m >= paramArrayOfInt[(j + 3)] ? 1 : this.parent.onBottom ? 0 : m <= paramArrayOfInt[(j + 3)] ? 1 : 0;
          m = paramArrayOfInt[(j + 1)];
        }
        arrayOfInt1[j] = (paramArrayOfInt[(j++)] + (k != 0 ? 1 : -1));
        arrayOfInt1[j] = paramArrayOfInt[(j++)];
      }
      paramGC.setForeground(paramColor1);
      paramGC.drawPolyline(arrayOfInt1);
    }
  }

  protected Point computeSize(int paramInt1, int paramInt2, GC paramGC, int paramInt3, int paramInt4)
  {
    int i = 0;
    int j = 0;
    switch (paramInt1)
    {
    case -2:
      if (this.parent.fixedTabHeight != -1)
      {
        j = this.parent.fixedTabHeight == 0 ? 0 : this.parent.fixedTabHeight + 1;
      }
      else
      {
        localObject1 = this.parent.items;
        if (localObject1.length == 0)
          j = paramGC.textExtent("Default", 9).y + 2 + 2;
        else
          for (int k = 0; k < localObject1.length; k++)
            j = Math.max(j, computeSize(k, 0, paramGC, paramInt3, paramInt4).y);
        paramGC.dispose();
      }
      break;
    case -8:
    case -6:
    case -5:
      i = j = 18;
      break;
    case -7:
      i = 27;
      j = 18;
      break;
    case -4:
    case -3:
    default:
      if ((paramInt1 >= 0) && (paramInt1 < this.parent.getItemCount()))
      {
        updateCurves();
        localObject1 = this.parent.items[paramInt1];
        if (((CTabItem)localObject1).isDisposed())
          return new Point(0, 0);
        Image localImage = ((CTabItem)localObject1).getImage();
        if (localImage != null)
        {
          localObject2 = localImage.getBounds();
          if (((paramInt2 & 0x2) != 0) || (this.parent.showUnselectedImage))
            i += ((Rectangle)localObject2).width;
          j = ((Rectangle)localObject2).height;
        }
        Object localObject2 = null;
        if ((paramInt2 & 0x1000000) != 0)
        {
          int m = this.parent.minChars;
          localObject2 = m == 0 ? null : ((CTabItem)localObject1).getText();
          if ((localObject2 != null) && (((String)localObject2).length() > m))
          {
            int n;
            if (useEllipses())
            {
              n = m < "...".length() + 1 ? m : m - "...".length();
              localObject2 = ((String)localObject2).substring(0, n);
              if (m > "...".length() + 1)
                localObject2 = localObject2 + "...";
            }
            else
            {
              n = m;
              localObject2 = ((String)localObject2).substring(0, n);
            }
          }
        }
        else
        {
          localObject2 = ((CTabItem)localObject1).getText();
        }
        if (localObject2 != null)
        {
          if (i > 0)
            i += 4;
          Object localObject3;
          if (((CTabItem)localObject1).font == null)
          {
            localObject3 = paramGC.textExtent((String)localObject2, 9);
            i += ((Point)localObject3).x;
            j = Math.max(j, ((Point)localObject3).y);
          }
          else
          {
            localObject3 = paramGC.getFont();
            paramGC.setFont(((CTabItem)localObject1).font);
            Point localPoint = paramGC.textExtent((String)localObject2, 9);
            i += localPoint.x;
            j = Math.max(j, localPoint.y);
            paramGC.setFont((Font)localObject3);
          }
        }
        if (((this.parent.showClose) || (((CTabItem)localObject1).showClose)) && (((paramInt2 & 0x2) != 0) || (this.parent.showUnselectedClose)))
        {
          if (i > 0)
            i += 4;
          i += computeSize(-8, 0, paramGC, -1, -1).x;
        }
      }
      break;
    }
    Object localObject1 = computeTrim(paramInt1, paramInt2, 0, 0, i, j);
    i = ((Rectangle)localObject1).width;
    j = ((Rectangle)localObject1).height;
    return new Point(i, j);
  }

  protected Rectangle computeTrim(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    int i = this.parent.borderVisible ? 1 : 0;
    int j = i;
    int k = this.parent.onBottom ? i : 0;
    int m = this.parent.onBottom ? 0 : i;
    int n = this.parent.tabHeight;
    switch (paramInt1)
    {
    case -1:
      int i1 = this.parent.getStyle();
      int i2 = (i1 & 0x800000) != 0 ? 1 : 3;
      int i3 = (i1 & 0x800000) != 0 ? 0 : 2;
      if ((this.parent.fixedTabHeight == 0) && ((i1 & 0x800000) != 0) && ((i1 & 0x800) == 0))
        i2 = 0;
      int i4 = this.parent.marginWidth;
      int i5 = this.parent.marginHeight;
      paramInt3 = paramInt3 - i4 - i3 - i;
      paramInt5 = paramInt5 + i + j + 2 * i4 + 2 * i3;
      if (this.parent.minimized)
      {
        paramInt4 = this.parent.onBottom ? paramInt4 - k : paramInt4 - i2 - n - k;
        paramInt6 = k + m + n + i2;
      }
      else
      {
        paramInt4 = this.parent.onBottom ? paramInt4 - i5 - i3 - k : paramInt4 - i5 - i2 - n - k;
        paramInt6 = paramInt6 + k + m + 2 * i5 + n + i2 + i3;
      }
      break;
    case -2:
      break;
    case -3:
      paramInt3 -= i;
      paramInt5 = paramInt5 + i + j;
      paramInt4 -= k;
      paramInt6 = paramInt6 + k + m;
      break;
    default:
      if ((paramInt1 >= 0) && (paramInt1 < this.parent.getItemCount()))
      {
        updateCurves();
        paramInt3 -= 4;
        paramInt5 = paramInt5 + 4 + 4;
        if ((!this.parent.simple) && (!this.parent.single) && ((paramInt2 & 0x2) != 0))
          paramInt5 += this.curveWidth - this.curveIndent;
        paramInt4 -= 2;
        paramInt6 = paramInt6 + 2 + 2;
      }
      break;
    }
    return new Rectangle(paramInt3, paramInt4, paramInt5, paramInt6);
  }

  void createAntialiasColors()
  {
    disposeAntialiasColors();
    this.lastBorderColor = this.parent.getDisplay().getSystemColor(18);
    RGB localRGB1 = this.lastBorderColor.getRGB();
    RGB localRGB2 = this.parent.selectionBackground.getRGB();
    if ((this.parent.selectionBgImage != null) || ((this.parent.selectionGradientColors != null) && (this.parent.selectionGradientColors.length > 1)))
      localRGB2 = null;
    RGB localRGB3 = this.parent.getBackground().getRGB();
    if ((this.parent.gradientColors != null) && (this.parent.gradientColors.length > 1))
      localRGB3 = null;
    RGB localRGB4;
    RGB localRGB5;
    int i;
    int j;
    int k;
    if (localRGB3 != null)
    {
      localRGB4 = localRGB1;
      localRGB5 = localRGB3;
      i = localRGB4.red + 2 * (localRGB5.red - localRGB4.red) / 3;
      j = localRGB4.green + 2 * (localRGB5.green - localRGB4.green) / 3;
      k = localRGB4.blue + 2 * (localRGB5.blue - localRGB4.blue) / 3;
      this.selectedOuterColor = new Color(this.parent.getDisplay(), i, j, k);
    }
    if (localRGB2 != null)
    {
      localRGB4 = localRGB1;
      localRGB5 = localRGB2;
      i = localRGB4.red + 2 * (localRGB5.red - localRGB4.red) / 3;
      j = localRGB4.green + 2 * (localRGB5.green - localRGB4.green) / 3;
      k = localRGB4.blue + 2 * (localRGB5.blue - localRGB4.blue) / 3;
      this.selectedInnerColor = new Color(this.parent.getDisplay(), i, j, k);
    }
    localRGB3 = this.parent.getParent().getBackground().getRGB();
    if (localRGB3 != null)
    {
      localRGB4 = localRGB1;
      localRGB5 = localRGB3;
      i = localRGB4.red + 2 * (localRGB5.red - localRGB4.red) / 3;
      j = localRGB4.green + 2 * (localRGB5.green - localRGB4.green) / 3;
      k = localRGB4.blue + 2 * (localRGB5.blue - localRGB4.blue) / 3;
      this.tabAreaColor = new Color(this.parent.getDisplay(), i, j, k);
    }
  }

  void createSelectionHighlightGradientColors(Color paramColor)
  {
    disposeSelectionHighlightGradientColors();
    if (paramColor == null)
      return;
    int i = this.parent.tabHeight;
    RGB localRGB1 = paramColor.getRGB();
    RGB localRGB2 = this.parent.selectionBackground.getRGB();
    this.selectionHighlightGradientColorsCache = new Color[i];
    int j = i - 1;
    for (int k = 0; k < i; k++)
    {
      int m = j - k;
      int n = k;
      int i1 = (localRGB2.red * n + localRGB1.red * m) / j;
      int i2 = (localRGB2.green * n + localRGB1.green * m) / j;
      int i3 = (localRGB2.blue * n + localRGB1.blue * m) / j;
      this.selectionHighlightGradientColorsCache[k] = new Color(this.parent.getDisplay(), i1, i2, i3);
    }
  }

  protected void dispose()
  {
    disposeAntialiasColors();
    disposeSelectionHighlightGradientColors();
    if (this.fillColor != null)
    {
      this.fillColor.dispose();
      this.fillColor = null;
    }
  }

  void disposeAntialiasColors()
  {
    if (this.tabAreaColor != null)
      this.tabAreaColor.dispose();
    if (this.selectedInnerColor != null)
      this.selectedInnerColor.dispose();
    if (this.selectedOuterColor != null)
      this.selectedOuterColor.dispose();
    this.tabAreaColor = (this.selectedInnerColor = this.selectedOuterColor = null);
  }

  void disposeSelectionHighlightGradientColors()
  {
    if (this.selectionHighlightGradientColorsCache == null)
      return;
    for (int i = 0; i < this.selectionHighlightGradientColorsCache.length; i++)
      this.selectionHighlightGradientColorsCache[i].dispose();
    this.selectionHighlightGradientColorsCache = null;
  }

  protected void draw(int paramInt1, int paramInt2, Rectangle paramRectangle, GC paramGC)
  {
    switch (paramInt1)
    {
    case -4:
      drawBackground(paramGC, paramRectangle, paramInt2);
      break;
    case -1:
      drawBody(paramGC, paramRectangle, paramInt2);
      break;
    case -2:
      drawTabArea(paramGC, paramRectangle, paramInt2);
      break;
    case -5:
      drawMaximize(paramGC, paramRectangle, paramInt2);
      break;
    case -6:
      drawMinimize(paramGC, paramRectangle, paramInt2);
      break;
    case -7:
      drawChevron(paramGC, paramRectangle, paramInt2);
      break;
    case -3:
    default:
      if ((paramInt1 >= 0) && (paramInt1 < this.parent.getItemCount()))
      {
        if ((paramRectangle.width == 0) || (paramRectangle.height == 0))
          return;
        if ((paramInt2 & 0x2) != 0)
          drawSelected(paramInt1, paramGC, paramRectangle, paramInt2);
        else
          drawUnselected(paramInt1, paramGC, paramRectangle, paramInt2);
      }
      break;
    }
  }

  void drawBackground(GC paramGC, Rectangle paramRectangle, int paramInt)
  {
    int i = (paramInt & 0x2) != 0 ? 1 : 0;
    Color localColor = i != 0 ? this.parent.selectionBackground : this.parent.getBackground();
    Image localImage = i != 0 ? this.parent.selectionBgImage : null;
    Color[] arrayOfColor = i != 0 ? this.parent.selectionGradientColors : this.parent.gradientColors;
    int[] arrayOfInt = i != 0 ? this.parent.selectionGradientPercents : this.parent.gradientPercents;
    boolean bool = i != 0 ? this.parent.selectionGradientVertical : this.parent.gradientVertical;
    drawBackground(paramGC, null, paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height, localColor, localImage, arrayOfColor, arrayOfInt, bool);
  }

  void drawBackground(GC paramGC, int[] paramArrayOfInt, boolean paramBoolean)
  {
    Color localColor = paramBoolean ? this.parent.selectionBackground : this.parent.getBackground();
    Image localImage = paramBoolean ? this.parent.selectionBgImage : null;
    Color[] arrayOfColor = paramBoolean ? this.parent.selectionGradientColors : this.parent.gradientColors;
    int[] arrayOfInt = paramBoolean ? this.parent.selectionGradientPercents : this.parent.gradientPercents;
    boolean bool = paramBoolean ? this.parent.selectionGradientVertical : this.parent.gradientVertical;
    Point localPoint = this.parent.getSize();
    int i = localPoint.x;
    int j = this.parent.tabHeight + ((this.parent.getStyle() & 0x800000) != 0 ? 1 : 3);
    int k = 0;
    int m = this.parent.borderVisible ? 1 : 0;
    int n = this.parent.onBottom ? m : 0;
    int i1 = this.parent.onBottom ? 0 : m;
    if (m > 0)
    {
      k++;
      i -= 2;
    }
    int i2 = this.parent.onBottom ? localPoint.y - i1 - j : n;
    drawBackground(paramGC, paramArrayOfInt, k, i2, i, j, localColor, localImage, arrayOfColor, arrayOfInt, bool);
  }

  void drawBackground(GC paramGC, int[] paramArrayOfInt1, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Color paramColor, Image paramImage, Color[] paramArrayOfColor, int[] paramArrayOfInt2, boolean paramBoolean)
  {
    Region localRegion1 = null;
    Region localRegion2 = null;
    if (paramArrayOfInt1 != null)
    {
      localRegion1 = new Region();
      paramGC.getClipping(localRegion1);
      localRegion2 = new Region();
      localRegion2.add(paramArrayOfInt1);
      localRegion2.intersect(localRegion1);
      paramGC.setClipping(localRegion2);
    }
    Object localObject;
    if (paramImage != null)
    {
      paramGC.setBackground(paramColor);
      paramGC.fillRectangle(paramInt1, paramInt2, paramInt3, paramInt4);
      localObject = paramImage.getBounds();
      paramGC.drawImage(paramImage, ((Rectangle)localObject).x, ((Rectangle)localObject).y, ((Rectangle)localObject).width, ((Rectangle)localObject).height, paramInt1, paramInt2, paramInt3, paramInt4);
    }
    else if (paramArrayOfColor != null)
    {
      if (paramArrayOfColor.length == 1)
      {
        localObject = paramArrayOfColor[0] != null ? paramArrayOfColor[0] : paramColor;
        paramGC.setBackground((Color)localObject);
        paramGC.fillRectangle(paramInt1, paramInt2, paramInt3, paramInt4);
      }
      else
      {
        int k;
        int m;
        Color localColor1;
        int j;
        if (paramBoolean)
        {
          int n;
          if (this.parent.onBottom)
          {
            int i = 0;
            if (paramArrayOfInt2[(paramArrayOfInt2.length - 1)] < 100)
            {
              i = (100 - paramArrayOfInt2[(paramArrayOfInt2.length - 1)]) * paramInt4 / 100;
              paramGC.setBackground(paramColor);
              paramGC.fillRectangle(paramInt1, paramInt2, paramInt3, i);
            }
            Color localColor2 = paramArrayOfColor[(paramArrayOfColor.length - 1)];
            if (localColor2 == null)
              localColor2 = paramColor;
            for (k = paramArrayOfInt2.length - 1; k >= 0; k--)
            {
              paramGC.setForeground(localColor2);
              localColor2 = paramArrayOfColor[k];
              if (localColor2 == null)
                localColor2 = paramColor;
              paramGC.setBackground(localColor2);
              m = k > 0 ? paramArrayOfInt2[k] - paramArrayOfInt2[(k - 1)] : paramArrayOfInt2[k];
              n = m * paramInt4 / 100;
              paramGC.fillGradientRectangle(paramInt1, paramInt2 + i, paramInt3, n, true);
              i += n;
            }
          }
          else
          {
            localColor1 = paramArrayOfColor[0];
            if (localColor1 == null)
              localColor1 = paramColor;
            j = 0;
            for (k = 0; k < paramArrayOfInt2.length; k++)
            {
              paramGC.setForeground(localColor1);
              localColor1 = paramArrayOfColor[(k + 1)];
              if (localColor1 == null)
                localColor1 = paramColor;
              paramGC.setBackground(localColor1);
              m = k > 0 ? paramArrayOfInt2[k] - paramArrayOfInt2[(k - 1)] : paramArrayOfInt2[k];
              n = m * paramInt4 / 100;
              paramGC.fillGradientRectangle(paramInt1, paramInt2 + j, paramInt3, n, true);
              j += n;
            }
            if (j < paramInt4)
            {
              paramGC.setBackground(paramColor);
              paramGC.fillRectangle(paramInt1, j, paramInt3, paramInt4 - j + 1);
            }
          }
        }
        else
        {
          paramInt2 = 0;
          paramInt4 = this.parent.getSize().y;
          localColor1 = paramArrayOfColor[0];
          if (localColor1 == null)
            localColor1 = paramColor;
          j = 0;
          for (k = 0; k < paramArrayOfInt2.length; k++)
          {
            paramGC.setForeground(localColor1);
            localColor1 = paramArrayOfColor[(k + 1)];
            if (localColor1 == null)
              localColor1 = paramColor;
            paramGC.setBackground(localColor1);
            m = paramArrayOfInt2[k] * paramInt3 / 100 - j;
            paramGC.fillGradientRectangle(paramInt1 + j, paramInt2, m, paramInt4, false);
            j += m;
          }
          if (j < paramInt3)
          {
            paramGC.setBackground(paramColor);
            paramGC.fillRectangle(paramInt1 + j, paramInt2, paramInt3 - j, paramInt4);
          }
        }
      }
    }
    else if (((this.parent.getStyle() & 0x40000) != 0) || (!paramColor.equals(this.parent.getBackground())))
    {
      paramGC.setBackground(paramColor);
      paramGC.fillRectangle(paramInt1, paramInt2, paramInt3, paramInt4);
    }
    if (paramArrayOfInt1 != null)
    {
      paramGC.setClipping(localRegion1);
      localRegion1.dispose();
      localRegion2.dispose();
    }
  }

  void drawBorder(GC paramGC, int[] paramArrayOfInt)
  {
    paramGC.setForeground(this.parent.getDisplay().getSystemColor(18));
    paramGC.drawPolyline(paramArrayOfInt);
  }

  void drawBody(GC paramGC, Rectangle paramRectangle, int paramInt)
  {
    Point localPoint = new Point(paramRectangle.width, paramRectangle.height);
    int i = this.parent.selectedIndex;
    int j = this.parent.tabHeight;
    int k = this.parent.borderVisible ? 1 : 0;
    int m = k;
    int n = this.parent.onBottom ? k : 0;
    int i1 = this.parent.onBottom ? 0 : k;
    int i2 = this.parent.getStyle();
    int i3 = (i2 & 0x800000) != 0 ? 1 : 3;
    int i4 = (i2 & 0x800000) != 0 ? 0 : 2;
    int i5;
    int i6;
    int i8;
    int i7;
    if (!this.parent.minimized)
    {
      i5 = localPoint.x - k - m - 2 * i4;
      i6 = localPoint.y - n - i1 - j - i3 - i4;
      int i9;
      int i10;
      if (i4 > 0)
      {
        int[] arrayOfInt = (int[])null;
        int i11;
        if (this.parent.onBottom)
        {
          i8 = k;
          i9 = n;
          i10 = localPoint.x - m;
          i11 = localPoint.y - i1 - j - i3;
          arrayOfInt = new int[] { i8, i9, i10, i9, i10, i11, i10 - i4, i11, i10 - i4, i9 + i4, i8 + i4, i9 + i4, i8 + i4, i11, i8, i11 };
        }
        else
        {
          i8 = k;
          i9 = n + j + i3;
          i10 = localPoint.x - m;
          i11 = localPoint.y - i1;
          arrayOfInt = new int[] { i8, i9, i8 + i4, i9, i8 + i4, i11 - i4, i10 - i4, i11 - i4, i10 - i4, i9, i10, i9, i10, i11, i8, i11 };
        }
        if ((i != -1) && (this.parent.selectionGradientColors != null) && (this.parent.selectionGradientColors.length > 1) && (!this.parent.selectionGradientVertical))
        {
          drawBackground(paramGC, arrayOfInt, true);
        }
        else if ((i == -1) && (this.parent.gradientColors != null) && (this.parent.gradientColors.length > 1) && (!this.parent.gradientVertical))
        {
          drawBackground(paramGC, arrayOfInt, false);
        }
        else
        {
          paramGC.setBackground(i == -1 ? this.parent.getBackground() : this.parent.selectionBackground);
          paramGC.fillPolygon(arrayOfInt);
        }
      }
      if ((this.parent.getStyle() & 0x40000) != 0)
      {
        paramGC.setBackground(this.parent.getBackground());
        i7 = this.parent.marginWidth;
        i8 = this.parent.marginHeight;
        i9 = k + i7 + i4;
        if (this.parent.onBottom)
          i10 = n + i4 + i8;
        else
          i10 = n + j + i3 + i8;
        paramGC.fillRectangle(i9 - i7, i10 - i8, i5, i6);
      }
    }
    else if ((this.parent.getStyle() & 0x40000) != 0)
    {
      i5 = n + j + i3 + i1;
      if (localPoint.y > i5)
      {
        paramGC.setBackground(this.parent.getParent().getBackground());
        paramGC.fillRectangle(0, i5, localPoint.x, localPoint.y - i5);
      }
    }
    if (k > 0)
    {
      paramGC.setForeground(this.parent.getDisplay().getSystemColor(18));
      i5 = k - 1;
      i6 = localPoint.x - m;
      i7 = this.parent.onBottom ? n - 1 : n + j;
      i8 = this.parent.onBottom ? localPoint.y - j - i1 - 1 : localPoint.y - i1;
      paramGC.drawLine(i5, i7, i5, i8);
      paramGC.drawLine(i6, i7, i6, i8);
      if (this.parent.onBottom)
        paramGC.drawLine(i5, i7, i6, i7);
      else
        paramGC.drawLine(i5, i8, i6, i8);
    }
  }

  void drawClose(GC paramGC, Rectangle paramRectangle, int paramInt)
  {
    if ((paramRectangle.width == 0) || (paramRectangle.height == 0))
      return;
    Display localDisplay = this.parent.getDisplay();
    int i = paramRectangle.x + Math.max(1, (paramRectangle.width - 9) / 2);
    int j = paramRectangle.y + Math.max(1, (paramRectangle.height - 9) / 2);
    j += (this.parent.onBottom ? -1 : 1);
    Color localColor = localDisplay.getSystemColor(17);
    int[] arrayOfInt;
    switch (paramInt & 0x2A)
    {
    case 0:
      arrayOfInt = new int[] { i, j, i + 2, j, i + 4, j + 2, i + 5, j + 2, i + 7, j, i + 9, j, i + 9, j + 2, i + 7, j + 4, i + 7, j + 5, i + 9, j + 7, i + 9, j + 9, i + 7, j + 9, i + 5, j + 7, i + 4, j + 7, i + 2, j + 9, i, j + 9, i, j + 7, i + 2, j + 5, i + 2, j + 4, i, j + 2 };
      paramGC.setBackground(localDisplay.getSystemColor(25));
      paramGC.fillPolygon(arrayOfInt);
      paramGC.setForeground(localColor);
      paramGC.drawPolygon(arrayOfInt);
      break;
    case 32:
      arrayOfInt = new int[] { i, j, i + 2, j, i + 4, j + 2, i + 5, j + 2, i + 7, j, i + 9, j, i + 9, j + 2, i + 7, j + 4, i + 7, j + 5, i + 9, j + 7, i + 9, j + 9, i + 7, j + 9, i + 5, j + 7, i + 4, j + 7, i + 2, j + 9, i, j + 9, i, j + 7, i + 2, j + 5, i + 2, j + 4, i, j + 2 };
      paramGC.setBackground(getFillColor());
      paramGC.fillPolygon(arrayOfInt);
      paramGC.setForeground(localColor);
      paramGC.drawPolygon(arrayOfInt);
      break;
    case 2:
      arrayOfInt = new int[] { i + 1, j + 1, i + 3, j + 1, i + 5, j + 3, i + 6, j + 3, i + 8, j + 1, i + 10, j + 1, i + 10, j + 3, i + 8, j + 5, i + 8, j + 6, i + 10, j + 8, i + 10, j + 10, i + 8, j + 10, i + 6, j + 8, i + 5, j + 8, i + 3, j + 10, i + 1, j + 10, i + 1, j + 8, i + 3, j + 6, i + 3, j + 5, i + 1, j + 3 };
      paramGC.setBackground(getFillColor());
      paramGC.fillPolygon(arrayOfInt);
      paramGC.setForeground(localColor);
      paramGC.drawPolygon(arrayOfInt);
      break;
    case 8:
      arrayOfInt = new int[] { i, j, i + 10, j, i + 10, j + 10, i, j + 10 };
      drawBackground(paramGC, arrayOfInt, false);
    }
  }

  void drawChevron(GC paramGC, Rectangle paramRectangle, int paramInt)
  {
    if ((paramRectangle.width == 0) || (paramRectangle.height == 0))
      return;
    int i = this.parent.selectedIndex;
    Display localDisplay = this.parent.getDisplay();
    Point localPoint = localDisplay.getDPI();
    int j = 720 / localPoint.y;
    FontData localFontData = this.parent.getFont().getFontData()[0];
    localFontData.setHeight(j);
    Font localFont = new Font(localDisplay, localFontData);
    int k = localFont.getFontData()[0].getHeight() * localPoint.y / 72;
    int m = Math.max(2, (paramRectangle.height - k - 4) / 2);
    int n = paramRectangle.x + 2;
    int i1 = paramRectangle.y + m;
    int i3 = this.parent.getItemCount();
    int i2;
    if (this.parent.single)
    {
      i2 = i == -1 ? i3 : i3 - 1;
    }
    else
    {
      for (int i4 = 0; (i4 < this.parent.priority.length) && (this.parent.items[this.parent.priority[i4]].showing); i4++);
      i2 = i3 - i4;
    }
    String str = i2 > 99 ? "99+" : String.valueOf(i2);
    switch (paramInt & 0x22)
    {
    case 0:
      Color localColor = this.parent.single ? this.parent.getSelectionForeground() : this.parent.getForeground();
      paramGC.setForeground(localColor);
      paramGC.setFont(localFont);
      paramGC.drawLine(n, i1, n + 2, i1 + 2);
      paramGC.drawLine(n + 2, i1 + 2, n, i1 + 4);
      paramGC.drawLine(n + 1, i1, n + 3, i1 + 2);
      paramGC.drawLine(n + 3, i1 + 2, n + 1, i1 + 4);
      paramGC.drawLine(n + 4, i1, n + 6, i1 + 2);
      paramGC.drawLine(n + 6, i1 + 2, n + 5, i1 + 4);
      paramGC.drawLine(n + 5, i1, n + 7, i1 + 2);
      paramGC.drawLine(n + 7, i1 + 2, n + 4, i1 + 4);
      paramGC.drawString(str, n + 7, i1 + 3, true);
      break;
    case 32:
      paramGC.setForeground(localDisplay.getSystemColor(17));
      paramGC.setBackground(localDisplay.getSystemColor(25));
      paramGC.setFont(localFont);
      paramGC.fillRoundRectangle(paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height, 6, 6);
      paramGC.drawRoundRectangle(paramRectangle.x, paramRectangle.y, paramRectangle.width - 1, paramRectangle.height - 1, 6, 6);
      paramGC.drawLine(n, i1, n + 2, i1 + 2);
      paramGC.drawLine(n + 2, i1 + 2, n, i1 + 4);
      paramGC.drawLine(n + 1, i1, n + 3, i1 + 2);
      paramGC.drawLine(n + 3, i1 + 2, n + 1, i1 + 4);
      paramGC.drawLine(n + 4, i1, n + 6, i1 + 2);
      paramGC.drawLine(n + 6, i1 + 2, n + 5, i1 + 4);
      paramGC.drawLine(n + 5, i1, n + 7, i1 + 2);
      paramGC.drawLine(n + 7, i1 + 2, n + 4, i1 + 4);
      paramGC.drawString(str, n + 7, i1 + 3, true);
      break;
    case 2:
      paramGC.setForeground(localDisplay.getSystemColor(17));
      paramGC.setBackground(localDisplay.getSystemColor(25));
      paramGC.setFont(localFont);
      paramGC.fillRoundRectangle(paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height, 6, 6);
      paramGC.drawRoundRectangle(paramRectangle.x, paramRectangle.y, paramRectangle.width - 1, paramRectangle.height - 1, 6, 6);
      paramGC.drawLine(n + 1, i1 + 1, n + 3, i1 + 3);
      paramGC.drawLine(n + 3, i1 + 3, n + 1, i1 + 5);
      paramGC.drawLine(n + 2, i1 + 1, n + 4, i1 + 3);
      paramGC.drawLine(n + 4, i1 + 3, n + 2, i1 + 5);
      paramGC.drawLine(n + 5, i1 + 1, n + 7, i1 + 3);
      paramGC.drawLine(n + 7, i1 + 3, n + 6, i1 + 5);
      paramGC.drawLine(n + 6, i1 + 1, n + 8, i1 + 3);
      paramGC.drawLine(n + 8, i1 + 3, n + 5, i1 + 5);
      paramGC.drawString(str, n + 8, i1 + 4, true);
    }
    localFont.dispose();
  }

  void drawHighlight(GC paramGC, Rectangle paramRectangle, int paramInt1, int paramInt2)
  {
    if ((this.parent.simple) || (this.parent.onBottom))
      return;
    if (this.selectionHighlightGradientBegin == null)
      return;
    Color[] arrayOfColor = this.selectionHighlightGradientColorsCache;
    if (arrayOfColor == null)
      return;
    int i = arrayOfColor.length;
    if (i == 0)
      return;
    int j = paramRectangle.x;
    int k = paramRectangle.y;
    paramGC.setForeground(arrayOfColor[0]);
    paramGC.drawLine(TOP_LEFT_CORNER_HILITE[0] + j + 1, 1 + k, paramInt2 - this.curveIndent, 1 + k);
    int[] arrayOfInt = TOP_LEFT_CORNER_HILITE;
    int m = this.parent.tabHeight - this.topCurveHighlightEnd.length / 2;
    int n = 0;
    int i1 = 0;
    int i2 = 0;
    int i5;
    for (int i3 = 0; i3 < arrayOfInt.length / 2; i3++)
    {
      i4 = arrayOfInt[(i3 * 2)];
      i5 = arrayOfInt[(i3 * 2 + 1)];
      n = i4 + j;
      i1 = i5 + k;
      i2 = i5 - 1;
      paramGC.setForeground(arrayOfColor[i2]);
      paramGC.drawPoint(n, i1);
    }
    for (i3 = i2; i3 < i; i3++)
    {
      paramGC.setForeground(arrayOfColor[i3]);
      paramGC.drawPoint(n, 1 + i1++);
    }
    i3 = paramInt2 - this.curveIndent;
    int i6;
    for (int i4 = 0; i4 < this.topCurveHighlightStart.length / 2; i4++)
    {
      i5 = this.topCurveHighlightStart[(i4 * 2)];
      i6 = this.topCurveHighlightStart[(i4 * 2 + 1)];
      n = i5 + i3;
      i1 = i6 + k;
      i2 = i6 - 1;
      if (i2 >= i)
        break;
      paramGC.setForeground(arrayOfColor[i2]);
      paramGC.drawPoint(n, i1);
    }
    for (i4 = i2; i4 < i2 + m; i4++)
    {
      if (i4 >= i)
        break;
      paramGC.setForeground(arrayOfColor[i4]);
      paramGC.drawPoint(1 + n++, 1 + i1++);
    }
    for (i4 = 0; i4 < this.topCurveHighlightEnd.length / 2; i4++)
    {
      i5 = this.topCurveHighlightEnd[(i4 * 2)];
      i6 = this.topCurveHighlightEnd[(i4 * 2 + 1)];
      n = i5 + i3;
      i1 = i6 + k;
      i2 = i6 - 1;
      if (i2 >= i)
        break;
      paramGC.setForeground(arrayOfColor[i2]);
      paramGC.drawPoint(n, i1);
    }
  }

  void drawLeftUnselectedBorder(GC paramGC, Rectangle paramRectangle, int paramInt)
  {
    int i = paramRectangle.x;
    int j = paramRectangle.y;
    int k = paramRectangle.height;
    int[] arrayOfInt1 = (int[])null;
    int[] arrayOfInt2;
    int m;
    int n;
    if (this.parent.onBottom)
    {
      arrayOfInt2 = this.parent.simple ? SIMPLE_UNSELECTED_INNER_CORNER : BOTTOM_LEFT_CORNER;
      arrayOfInt1 = new int[arrayOfInt2.length + 2];
      m = 0;
      arrayOfInt1[(m++)] = i;
      arrayOfInt1[(m++)] = (j - 1);
      for (n = 0; n < arrayOfInt2.length / 2; n++)
      {
        arrayOfInt1[(m++)] = (i + arrayOfInt2[(2 * n)]);
        arrayOfInt1[(m++)] = (j + k + arrayOfInt2[(2 * n + 1)] - 1);
      }
    }
    else
    {
      arrayOfInt2 = this.parent.simple ? SIMPLE_UNSELECTED_INNER_CORNER : TOP_LEFT_CORNER;
      arrayOfInt1 = new int[arrayOfInt2.length + 2];
      m = 0;
      arrayOfInt1[(m++)] = i;
      arrayOfInt1[(m++)] = (j + k);
      for (n = 0; n < arrayOfInt2.length / 2; n++)
      {
        arrayOfInt1[(m++)] = (i + arrayOfInt2[(2 * n)]);
        arrayOfInt1[(m++)] = (j + arrayOfInt2[(2 * n + 1)]);
      }
    }
    drawBorder(paramGC, arrayOfInt1);
  }

  void drawMaximize(GC paramGC, Rectangle paramRectangle, int paramInt)
  {
    if ((paramRectangle.width == 0) || (paramRectangle.height == 0))
      return;
    Display localDisplay = this.parent.getDisplay();
    int i = paramRectangle.x + (paramRectangle.width - 10) / 2;
    int j = paramRectangle.y + 3;
    paramGC.setForeground(localDisplay.getSystemColor(17));
    paramGC.setBackground(localDisplay.getSystemColor(25));
    switch (paramInt & 0x22)
    {
    case 0:
      if (!this.parent.getMaximized())
      {
        paramGC.fillRectangle(i, j, 9, 9);
        paramGC.drawRectangle(i, j, 9, 9);
        paramGC.drawLine(i + 1, j + 2, i + 8, j + 2);
      }
      else
      {
        paramGC.fillRectangle(i, j + 3, 5, 4);
        paramGC.fillRectangle(i + 2, j, 5, 4);
        paramGC.drawRectangle(i, j + 3, 5, 4);
        paramGC.drawRectangle(i + 2, j, 5, 4);
        paramGC.drawLine(i + 3, j + 1, i + 6, j + 1);
        paramGC.drawLine(i + 1, j + 4, i + 4, j + 4);
      }
      break;
    case 32:
      paramGC.fillRoundRectangle(paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height, 6, 6);
      paramGC.drawRoundRectangle(paramRectangle.x, paramRectangle.y, paramRectangle.width - 1, paramRectangle.height - 1, 6, 6);
      if (!this.parent.getMaximized())
      {
        paramGC.fillRectangle(i, j, 9, 9);
        paramGC.drawRectangle(i, j, 9, 9);
        paramGC.drawLine(i + 1, j + 2, i + 8, j + 2);
      }
      else
      {
        paramGC.fillRectangle(i, j + 3, 5, 4);
        paramGC.fillRectangle(i + 2, j, 5, 4);
        paramGC.drawRectangle(i, j + 3, 5, 4);
        paramGC.drawRectangle(i + 2, j, 5, 4);
        paramGC.drawLine(i + 3, j + 1, i + 6, j + 1);
        paramGC.drawLine(i + 1, j + 4, i + 4, j + 4);
      }
      break;
    case 2:
      paramGC.fillRoundRectangle(paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height, 6, 6);
      paramGC.drawRoundRectangle(paramRectangle.x, paramRectangle.y, paramRectangle.width - 1, paramRectangle.height - 1, 6, 6);
      if (!this.parent.getMaximized())
      {
        paramGC.fillRectangle(i + 1, j + 1, 9, 9);
        paramGC.drawRectangle(i + 1, j + 1, 9, 9);
        paramGC.drawLine(i + 2, j + 3, i + 9, j + 3);
      }
      else
      {
        paramGC.fillRectangle(i + 1, j + 4, 5, 4);
        paramGC.fillRectangle(i + 3, j + 1, 5, 4);
        paramGC.drawRectangle(i + 1, j + 4, 5, 4);
        paramGC.drawRectangle(i + 3, j + 1, 5, 4);
        paramGC.drawLine(i + 4, j + 2, i + 7, j + 2);
        paramGC.drawLine(i + 2, j + 5, i + 5, j + 5);
      }
      break;
    }
  }

  void drawMinimize(GC paramGC, Rectangle paramRectangle, int paramInt)
  {
    if ((paramRectangle.width == 0) || (paramRectangle.height == 0))
      return;
    Display localDisplay = this.parent.getDisplay();
    int i = paramRectangle.x + (paramRectangle.width - 10) / 2;
    int j = paramRectangle.y + 3;
    paramGC.setForeground(localDisplay.getSystemColor(17));
    paramGC.setBackground(localDisplay.getSystemColor(25));
    switch (paramInt & 0x22)
    {
    case 0:
      if (!this.parent.getMinimized())
      {
        paramGC.fillRectangle(i, j, 9, 3);
        paramGC.drawRectangle(i, j, 9, 3);
      }
      else
      {
        paramGC.fillRectangle(i, j + 3, 5, 4);
        paramGC.fillRectangle(i + 2, j, 5, 4);
        paramGC.drawRectangle(i, j + 3, 5, 4);
        paramGC.drawRectangle(i + 2, j, 5, 4);
        paramGC.drawLine(i + 3, j + 1, i + 6, j + 1);
        paramGC.drawLine(i + 1, j + 4, i + 4, j + 4);
      }
      break;
    case 32:
      paramGC.fillRoundRectangle(paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height, 6, 6);
      paramGC.drawRoundRectangle(paramRectangle.x, paramRectangle.y, paramRectangle.width - 1, paramRectangle.height - 1, 6, 6);
      if (!this.parent.getMinimized())
      {
        paramGC.fillRectangle(i, j, 9, 3);
        paramGC.drawRectangle(i, j, 9, 3);
      }
      else
      {
        paramGC.fillRectangle(i, j + 3, 5, 4);
        paramGC.fillRectangle(i + 2, j, 5, 4);
        paramGC.drawRectangle(i, j + 3, 5, 4);
        paramGC.drawRectangle(i + 2, j, 5, 4);
        paramGC.drawLine(i + 3, j + 1, i + 6, j + 1);
        paramGC.drawLine(i + 1, j + 4, i + 4, j + 4);
      }
      break;
    case 2:
      paramGC.fillRoundRectangle(paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height, 6, 6);
      paramGC.drawRoundRectangle(paramRectangle.x, paramRectangle.y, paramRectangle.width - 1, paramRectangle.height - 1, 6, 6);
      if (!this.parent.getMinimized())
      {
        paramGC.fillRectangle(i + 1, j + 1, 9, 3);
        paramGC.drawRectangle(i + 1, j + 1, 9, 3);
      }
      else
      {
        paramGC.fillRectangle(i + 1, j + 4, 5, 4);
        paramGC.fillRectangle(i + 3, j + 1, 5, 4);
        paramGC.drawRectangle(i + 1, j + 4, 5, 4);
        paramGC.drawRectangle(i + 3, j + 1, 5, 4);
        paramGC.drawLine(i + 4, j + 2, i + 7, j + 2);
        paramGC.drawLine(i + 2, j + 5, i + 5, j + 5);
      }
      break;
    }
  }

  void drawRightUnselectedBorder(GC paramGC, Rectangle paramRectangle, int paramInt)
  {
    int i = paramRectangle.x;
    int j = paramRectangle.y;
    int k = paramRectangle.width;
    int m = paramRectangle.height;
    int[] arrayOfInt1 = (int[])null;
    int n = i + k - 1;
    int[] arrayOfInt2;
    int i1;
    int i2;
    if (this.parent.onBottom)
    {
      arrayOfInt2 = this.parent.simple ? SIMPLE_UNSELECTED_INNER_CORNER : BOTTOM_RIGHT_CORNER;
      arrayOfInt1 = new int[arrayOfInt2.length + 2];
      i1 = 0;
      for (i2 = 0; i2 < arrayOfInt2.length / 2; i2++)
      {
        arrayOfInt1[(i1++)] = (n + arrayOfInt2[(2 * i2)]);
        arrayOfInt1[(i1++)] = (j + m + arrayOfInt2[(2 * i2 + 1)] - 1);
      }
      arrayOfInt1[(i1++)] = n;
      arrayOfInt1[(i1++)] = (j - 1);
    }
    else
    {
      arrayOfInt2 = this.parent.simple ? SIMPLE_UNSELECTED_INNER_CORNER : TOP_RIGHT_CORNER;
      arrayOfInt1 = new int[arrayOfInt2.length + 2];
      i1 = 0;
      for (i2 = 0; i2 < arrayOfInt2.length / 2; i2++)
      {
        arrayOfInt1[(i1++)] = (n + arrayOfInt2[(2 * i2)]);
        arrayOfInt1[(i1++)] = (j + arrayOfInt2[(2 * i2 + 1)]);
      }
      arrayOfInt1[(i1++)] = n;
      arrayOfInt1[(i1++)] = (j + m);
    }
    drawBorder(paramGC, arrayOfInt1);
  }

  void drawSelected(int paramInt1, GC paramGC, Rectangle paramRectangle, int paramInt2)
  {
    CTabItem localCTabItem = this.parent.items[paramInt1];
    Object localObject1 = paramRectangle.x;
    int i = paramRectangle.y;
    int j = paramRectangle.height;
    int k = paramRectangle.width;
    if ((!this.parent.simple) && (!this.parent.single))
      k -= this.curveWidth - this.curveIndent;
    Object localObject2 = this.parent.borderVisible ? 1 : 0;
    int m = localObject2;
    int n = this.parent.onBottom ? localObject2 : 0;
    int i1 = this.parent.onBottom ? 0 : localObject2;
    Point localPoint = this.parent.getSize();
    int i2 = Math.min(localObject1 + k, this.parent.getRightItemEdge(paramGC));
    Object localObject3;
    int i7;
    Object localObject4;
    Object localObject6;
    if ((paramInt2 & 0x8) != 0)
    {
      int i3 = (this.parent.getStyle() & 0x800000) != 0 ? 1 : 3;
      localObject3 = localObject2;
      int i4 = this.parent.onBottom ? localPoint.y - i1 - this.parent.tabHeight - i3 : n + this.parent.tabHeight + 1;
      int i5 = localPoint.x - localObject2 - m;
      i7 = i3 - 1;
      localObject4 = new int[] { localObject3, i4, localObject3 + i5, i4, localObject3 + i5, i4 + i7, localObject3, i4 + i7 };
      if ((this.parent.selectionGradientColors != null) && (!this.parent.selectionGradientVertical))
      {
        drawBackground(paramGC, (int[])localObject4, true);
      }
      else
      {
        paramGC.setBackground(this.parent.selectionBackground);
        paramGC.fillRectangle(localObject3, i4, i5, i7);
      }
      if (this.parent.single)
      {
        if (localCTabItem.showing);
      }
      else
      {
        int i11;
        if (!localCTabItem.showing)
        {
          int i8 = Math.max(0, localObject2 - 1);
          int i10 = this.parent.onBottom ? i - 1 : i + j;
          i11 = localPoint.x - m;
          paramGC.setForeground(this.parent.getDisplay().getSystemColor(18));
          paramGC.drawLine(i8, i10, i11, i10);
          return;
        }
        localObject4 = (int[])null;
        int i12;
        if (this.parent.onBottom)
        {
          localObject5 = this.parent.simple ? SIMPLE_BOTTOM_LEFT_CORNER : BOTTOM_LEFT_CORNER;
          localObject6 = this.parent.simple ? SIMPLE_BOTTOM_RIGHT_CORNER : this.curve;
          if ((localObject2 == 0) && (paramInt1 == this.parent.firstIndex))
            localObject5 = new int[] { localObject1, i + j };
          localObject4 = new int[localObject5.length + localObject6.length + 8];
          i11 = 0;
          localObject4[(i11++)] = localObject1;
          localObject4[(i11++)] = (i - 1);
          localObject4[(i11++)] = localObject1;
          localObject4[(i11++)] = (i - 1);
          for (i12 = 0; i12 < localObject5.length / 2; i12++)
          {
            localObject4[(i11++)] = (localObject1 + localObject5[(2 * i12)]);
            localObject4[(i11++)] = (i + j + localObject5[(2 * i12 + 1)] - 1);
          }
          for (i12 = 0; i12 < localObject6.length / 2; i12++)
          {
            localObject4[(i11++)] = (this.parent.simple ? i2 - 1 + localObject6[(2 * i12)] : i2 - this.curveIndent + localObject6[(2 * i12)]);
            localObject4[(i11++)] = (this.parent.simple ? i + j + localObject6[(2 * i12 + 1)] - 1 : i + localObject6[(2 * i12 + 1)] - 2);
          }
          localObject4[(i11++)] = (this.parent.simple ? i2 - 1 : i2 + this.curveWidth - this.curveIndent);
          localObject4[(i11++)] = (i - 1);
          localObject4[(i11++)] = (this.parent.simple ? i2 - 1 : i2 + this.curveWidth - this.curveIndent);
          localObject4[(i11++)] = (i - 1);
        }
        else
        {
          localObject5 = this.parent.simple ? SIMPLE_TOP_LEFT_CORNER : TOP_LEFT_CORNER;
          localObject6 = this.parent.simple ? SIMPLE_TOP_RIGHT_CORNER : this.curve;
          if ((localObject2 == 0) && (paramInt1 == this.parent.firstIndex))
            localObject5 = new int[] { localObject1, i };
          localObject4 = new int[localObject5.length + localObject6.length + 8];
          i11 = 0;
          localObject4[(i11++)] = localObject1;
          localObject4[(i11++)] = (i + j + 1);
          localObject4[(i11++)] = localObject1;
          localObject4[(i11++)] = (i + j + 1);
          for (i12 = 0; i12 < localObject5.length / 2; i12++)
          {
            localObject4[(i11++)] = (localObject1 + localObject5[(2 * i12)]);
            localObject4[(i11++)] = (i + localObject5[(2 * i12 + 1)]);
          }
          for (i12 = 0; i12 < localObject6.length / 2; i12++)
          {
            localObject4[(i11++)] = (this.parent.simple ? i2 - 1 + localObject6[(2 * i12)] : i2 - this.curveIndent + localObject6[(2 * i12)]);
            localObject4[(i11++)] = (i + localObject6[(2 * i12 + 1)]);
          }
          localObject4[(i11++)] = (this.parent.simple ? i2 - 1 : i2 + this.curveWidth - this.curveIndent);
          localObject4[(i11++)] = (i + j + 1);
          localObject4[(i11++)] = (this.parent.simple ? i2 - 1 : i2 + this.curveWidth - this.curveIndent);
          localObject4[(i11++)] = (i + j + 1);
        }
        Object localObject5 = paramGC.getClipping();
        localObject6 = localCTabItem.getBounds();
        localObject6.height += 1;
        if (this.parent.onBottom)
          localObject6.y -= 1;
        boolean bool1 = ((Rectangle)localObject5).intersects((Rectangle)localObject6);
        if (bool1)
          if ((this.parent.selectionGradientColors != null) && (!this.parent.selectionGradientVertical))
          {
            drawBackground(paramGC, (int[])localObject4, true);
          }
          else
          {
            Color localColor1 = this.parent.selectionBackground;
            Image localImage2 = this.parent.selectionBgImage;
            Color[] arrayOfColor = this.parent.selectionGradientColors;
            int[] arrayOfInt = this.parent.selectionGradientPercents;
            boolean bool2 = this.parent.selectionGradientVertical;
            localObject3 = localObject1;
            i4 = this.parent.onBottom ? i - 1 : i + 1;
            i5 = k;
            i7 = j;
            if ((!this.parent.single) && (!this.parent.simple))
              i5 += this.curveWidth - this.curveIndent;
            drawBackground(paramGC, (int[])localObject4, localObject3, i4, i5, i7, localColor1, localImage2, arrayOfColor, arrayOfInt, bool2);
          }
        drawHighlight(paramGC, paramRectangle, paramInt2, i2);
        localObject4[0] = Math.max(0, localObject2 - 1);
        if ((localObject2 == 0) && (paramInt1 == this.parent.firstIndex))
        {
          localObject4[1] = (this.parent.onBottom ? i + j - 1 : i);
          int tmp1593_1592 = localObject4[1];
          localObject4[3] = tmp1593_1592;
          localObject4[5] = tmp1593_1592;
        }
        localObject4[(localObject4.length - 2)] = (localPoint.x - m + 1);
        for (int i13 = 0; i13 < localObject4.length / 2; i13++)
          if (localObject4[(2 * i13 + 1)] == i + j + 1)
            localObject4[(2 * i13 + 1)] -= 1;
        Color localColor2 = this.parent.getDisplay().getSystemColor(18);
        if (!localColor2.equals(this.lastBorderColor))
          createAntialiasColors();
        antialias((int[])localObject4, this.selectedInnerColor, this.selectedOuterColor, paramGC);
        paramGC.setForeground(localColor2);
        paramGC.drawPolyline((int[])localObject4);
        if (!bool1)
          return;
      }
    }
    if ((paramInt2 & 0x10) != 0)
    {
      Rectangle localRectangle1 = computeTrim(paramInt1, 0, 0, 0, 0, 0);
      localObject3 = localObject1 - localRectangle1.x;
      if ((this.parent.single) && ((this.parent.showClose) || (localCTabItem.showClose)))
        localObject3 += localCTabItem.closeRect.width;
      Image localImage1 = localCTabItem.getImage();
      int i9;
      if (localImage1 != null)
      {
        Rectangle localRectangle2 = localImage1.getBounds();
        i7 = i2 - localObject3 - (localRectangle1.width + localRectangle1.x);
        if ((!this.parent.single) && (localCTabItem.closeRect.width > 0))
          i7 -= localCTabItem.closeRect.width + 4;
        if (localRectangle2.width < i7)
        {
          localObject4 = localObject3;
          i9 = i + (j - localRectangle2.height) / 2;
          i9 += (this.parent.onBottom ? -1 : 1);
          paramGC.drawImage(localImage1, localObject4, i9);
          localObject3 += localRectangle2.width + 4;
        }
      }
      int i6 = i2 - localObject3 - (localRectangle1.width + localRectangle1.x);
      if ((!this.parent.single) && (localCTabItem.closeRect.width > 0))
        i6 -= localCTabItem.closeRect.width + 4;
      if (i6 > 0)
      {
        Font localFont = paramGC.getFont();
        paramGC.setFont(localCTabItem.font == null ? this.parent.getFont() : localCTabItem.font);
        if ((localCTabItem.shortenedText == null) || (localCTabItem.shortenedTextWidth != i6))
        {
          localCTabItem.shortenedText = shortenText(paramGC, localCTabItem.getText(), i6);
          localCTabItem.shortenedTextWidth = i6;
        }
        localObject4 = paramGC.textExtent(localCTabItem.shortenedText, 9);
        i9 = i + (j - ((Point)localObject4).y) / 2;
        i9 += (this.parent.onBottom ? -1 : 1);
        paramGC.setForeground(this.parent.selectionForeground);
        paramGC.drawText(localCTabItem.shortenedText, localObject3, i9, 9);
        paramGC.setFont(localFont);
        if (this.parent.isFocusControl())
        {
          localObject6 = this.parent.getDisplay();
          if ((this.parent.simple) || (this.parent.single))
          {
            paramGC.setBackground(((Display)localObject6).getSystemColor(2));
            paramGC.setForeground(((Display)localObject6).getSystemColor(1));
            paramGC.drawFocus(localObject3 - 1, i9 - 1, ((Point)localObject4).x + 2, ((Point)localObject4).y + 2);
          }
          else
          {
            paramGC.setForeground(((Display)localObject6).getSystemColor(17));
            paramGC.drawLine(localObject3, i9 + ((Point)localObject4).y + 1, localObject3 + ((Point)localObject4).x + 1, i9 + ((Point)localObject4).y + 1);
          }
        }
      }
      if ((this.parent.showClose) || (localCTabItem.showClose))
        drawClose(paramGC, localCTabItem.closeRect, localCTabItem.closeImageState);
    }
  }

  void drawTabArea(GC paramGC, Rectangle paramRectangle, int paramInt)
  {
    Point localPoint = this.parent.getSize();
    int[] arrayOfInt1 = (int[])null;
    Color localColor = this.parent.getDisplay().getSystemColor(18);
    int i = this.parent.tabHeight;
    int j = this.parent.getStyle();
    int k = this.parent.borderVisible ? 1 : 0;
    int m = k;
    int n = this.parent.onBottom ? k : 0;
    int i1 = this.parent.onBottom ? 0 : k;
    int i2 = this.parent.selectedIndex;
    int i3 = (j & 0x800000) != 0 ? 1 : 3;
    if (i == 0)
    {
      if (((j & 0x800000) != 0) && ((j & 0x800) == 0))
        return;
      i4 = k - 1;
      i5 = localPoint.x - m;
      i6 = this.parent.onBottom ? localPoint.y - i1 - i3 - 1 : n + i3;
      i7 = this.parent.onBottom ? localPoint.y - i1 : n;
      if ((k > 0) && (this.parent.onBottom))
        i7--;
      arrayOfInt1 = new int[] { i4, i6, i4, i7, i5, i7, i5, i6 };
      if ((i2 != -1) && (this.parent.selectionGradientColors != null) && (this.parent.selectionGradientColors.length > 1) && (!this.parent.selectionGradientVertical))
      {
        drawBackground(paramGC, arrayOfInt1, true);
      }
      else if ((i2 == -1) && (this.parent.gradientColors != null) && (this.parent.gradientColors.length > 1) && (!this.parent.gradientVertical))
      {
        drawBackground(paramGC, arrayOfInt1, false);
      }
      else
      {
        paramGC.setBackground(i2 == -1 ? this.parent.getBackground() : this.parent.selectionBackground);
        paramGC.fillPolygon(arrayOfInt1);
      }
      if (k > 0)
      {
        paramGC.setForeground(localColor);
        paramGC.drawPolyline(arrayOfInt1);
      }
      return;
    }
    int i4 = Math.max(0, k - 1);
    int i5 = this.parent.onBottom ? localPoint.y - i1 - i : n;
    int i6 = localPoint.x - k - m + 1;
    int i7 = i - 1;
    boolean bool1 = this.parent.simple;
    int[] arrayOfInt2;
    int[] arrayOfInt3;
    int i8;
    int i9;
    if (this.parent.onBottom)
    {
      if ((j & 0x800) != 0)
      {
        arrayOfInt2 = bool1 ? SIMPLE_BOTTOM_LEFT_CORNER : BOTTOM_LEFT_CORNER;
        arrayOfInt3 = bool1 ? SIMPLE_BOTTOM_RIGHT_CORNER : BOTTOM_RIGHT_CORNER;
      }
      else
      {
        arrayOfInt2 = bool1 ? SIMPLE_BOTTOM_LEFT_CORNER_BORDERLESS : BOTTOM_LEFT_CORNER_BORDERLESS;
        arrayOfInt3 = bool1 ? SIMPLE_BOTTOM_RIGHT_CORNER_BORDERLESS : BOTTOM_RIGHT_CORNER_BORDERLESS;
      }
      arrayOfInt1 = new int[arrayOfInt2.length + arrayOfInt3.length + 4];
      i8 = 0;
      arrayOfInt1[(i8++)] = i4;
      arrayOfInt1[(i8++)] = (i5 - i3);
      for (i9 = 0; i9 < arrayOfInt2.length / 2; i9++)
      {
        arrayOfInt1[(i8++)] = (i4 + arrayOfInt2[(2 * i9)]);
        arrayOfInt1[(i8++)] = (i5 + i7 + arrayOfInt2[(2 * i9 + 1)]);
        if (k == 0)
          arrayOfInt1[(i8 - 1)] += 1;
      }
      for (i9 = 0; i9 < arrayOfInt3.length / 2; i9++)
      {
        arrayOfInt1[(i8++)] = (i4 + i6 + arrayOfInt3[(2 * i9)]);
        arrayOfInt1[(i8++)] = (i5 + i7 + arrayOfInt3[(2 * i9 + 1)]);
        if (k == 0)
          arrayOfInt1[(i8 - 1)] += 1;
      }
      arrayOfInt1[(i8++)] = (i4 + i6);
      arrayOfInt1[(i8++)] = (i5 - i3);
    }
    else
    {
      if ((j & 0x800) != 0)
      {
        arrayOfInt2 = bool1 ? SIMPLE_TOP_LEFT_CORNER : TOP_LEFT_CORNER;
        arrayOfInt3 = bool1 ? SIMPLE_TOP_RIGHT_CORNER : TOP_RIGHT_CORNER;
      }
      else
      {
        arrayOfInt2 = bool1 ? SIMPLE_TOP_LEFT_CORNER_BORDERLESS : TOP_LEFT_CORNER_BORDERLESS;
        arrayOfInt3 = bool1 ? SIMPLE_TOP_RIGHT_CORNER_BORDERLESS : TOP_RIGHT_CORNER_BORDERLESS;
      }
      arrayOfInt1 = new int[arrayOfInt2.length + arrayOfInt3.length + 4];
      i8 = 0;
      arrayOfInt1[(i8++)] = i4;
      arrayOfInt1[(i8++)] = (i5 + i7 + i3 + 1);
      for (i9 = 0; i9 < arrayOfInt2.length / 2; i9++)
      {
        arrayOfInt1[(i8++)] = (i4 + arrayOfInt2[(2 * i9)]);
        arrayOfInt1[(i8++)] = (i5 + arrayOfInt2[(2 * i9 + 1)]);
      }
      for (i9 = 0; i9 < arrayOfInt3.length / 2; i9++)
      {
        arrayOfInt1[(i8++)] = (i4 + i6 + arrayOfInt3[(2 * i9)]);
        arrayOfInt1[(i8++)] = (i5 + arrayOfInt3[(2 * i9 + 1)]);
      }
      arrayOfInt1[(i8++)] = (i4 + i6);
      arrayOfInt1[(i8++)] = (i5 + i7 + i3 + 1);
    }
    boolean bool2 = this.parent.single;
    boolean bool3 = (bool2) && (i2 != -1);
    drawBackground(paramGC, arrayOfInt1, bool3);
    Region localRegion = new Region();
    localRegion.add(new Rectangle(i4, i5, i6 + 1, i7 + 1));
    localRegion.subtract(arrayOfInt1);
    paramGC.setBackground(this.parent.getParent().getBackground());
    fillRegion(paramGC, localRegion);
    localRegion.dispose();
    if (i2 == -1)
    {
      i9 = k;
      int i10 = this.parent.onBottom ? localPoint.y - i1 - i - 1 : n + i;
      int i11 = localPoint.x - m;
      paramGC.setForeground(localColor);
      paramGC.drawLine(i9, i10, i11, i10);
    }
    if (k > 0)
    {
      if (!localColor.equals(this.lastBorderColor))
        createAntialiasColors();
      antialias(arrayOfInt1, null, this.tabAreaColor, paramGC);
      paramGC.setForeground(localColor);
      paramGC.drawPolyline(arrayOfInt1);
    }
  }

  void drawUnselected(int paramInt1, GC paramGC, Rectangle paramRectangle, int paramInt2)
  {
    CTabItem localCTabItem = this.parent.items[paramInt1];
    int i = paramRectangle.x;
    int j = paramRectangle.y;
    int k = paramRectangle.height;
    int m = paramRectangle.width;
    if (!localCTabItem.showing)
      return;
    Rectangle localRectangle1 = paramGC.getClipping();
    if (!localRectangle1.intersects(paramRectangle))
      return;
    if ((paramInt2 & 0x8) != 0)
    {
      if ((paramInt1 > 0) && (paramInt1 < this.parent.selectedIndex))
        drawLeftUnselectedBorder(paramGC, paramRectangle, paramInt2);
      if (paramInt1 > this.parent.selectedIndex)
        drawRightUnselectedBorder(paramGC, paramRectangle, paramInt2);
    }
    if ((paramInt2 & 0x10) != 0)
    {
      Rectangle localRectangle2 = computeTrim(paramInt1, 0, 0, 0, 0, 0);
      int n = i - localRectangle2.x;
      Image localImage = localCTabItem.getImage();
      int i4;
      if ((localImage != null) && (this.parent.showUnselectedImage))
      {
        Rectangle localRectangle3 = localImage.getBounds();
        int i2 = i + m - n - (localRectangle2.width + localRectangle2.x);
        if ((this.parent.showUnselectedClose) && ((this.parent.showClose) || (localCTabItem.showClose)))
          i2 -= localCTabItem.closeRect.width + 4;
        if (localRectangle3.width < i2)
        {
          int i3 = n;
          i4 = localRectangle3.height;
          int i5 = j + (k - i4) / 2;
          i5 += (this.parent.onBottom ? -1 : 1);
          int i6 = localRectangle3.width * i4 / localRectangle3.height;
          paramGC.drawImage(localImage, localRectangle3.x, localRectangle3.y, localRectangle3.width, localRectangle3.height, i3, i5, i6, i4);
          n += i6 + 4;
        }
      }
      int i1 = i + m - n - (localRectangle2.width + localRectangle2.x);
      if ((this.parent.showUnselectedClose) && ((this.parent.showClose) || (localCTabItem.showClose)))
        i1 -= localCTabItem.closeRect.width + 4;
      if (i1 > 0)
      {
        Font localFont = paramGC.getFont();
        paramGC.setFont(localCTabItem.font == null ? this.parent.getFont() : localCTabItem.font);
        if ((localCTabItem.shortenedText == null) || (localCTabItem.shortenedTextWidth != i1))
        {
          localCTabItem.shortenedText = shortenText(paramGC, localCTabItem.getText(), i1);
          localCTabItem.shortenedTextWidth = i1;
        }
        Point localPoint = paramGC.textExtent(localCTabItem.shortenedText, 9);
        i4 = j + (k - localPoint.y) / 2;
        i4 += (this.parent.onBottom ? -1 : 1);
        paramGC.setForeground(this.parent.getForeground());
        paramGC.drawText(localCTabItem.shortenedText, n, i4, 9);
        paramGC.setFont(localFont);
      }
      if ((this.parent.showUnselectedClose) && ((this.parent.showClose) || (localCTabItem.showClose)))
        drawClose(paramGC, localCTabItem.closeRect, localCTabItem.closeImageState);
    }
  }

  void fillRegion(GC paramGC, Region paramRegion)
  {
    Region localRegion = new Region();
    paramGC.getClipping(localRegion);
    paramRegion.intersect(localRegion);
    paramGC.setClipping(paramRegion);
    paramGC.fillRectangle(paramRegion.getBounds());
    paramGC.setClipping(localRegion);
    localRegion.dispose();
  }

  Color getFillColor()
  {
    if (this.fillColor == null)
      this.fillColor = new Color(this.parent.getDisplay(), CLOSE_FILL);
    return this.fillColor;
  }

  boolean isSelectionHighlightColorsCacheHit(Color paramColor)
  {
    if (this.selectionHighlightGradientColorsCache == null)
      return false;
    if (this.selectionHighlightGradientColorsCache.length < 2)
      return false;
    Color localColor1 = this.selectionHighlightGradientColorsCache[0];
    Color localColor2 = this.selectionHighlightGradientColorsCache[(this.selectionHighlightGradientColorsCache.length - 1)];
    if (!localColor1.equals(paramColor))
      return false;
    if (this.selectionHighlightGradientColorsCache.length != this.parent.tabHeight)
      return false;
    return localColor2.equals(this.parent.selectionBackground);
  }

  void setSelectionHighlightGradientColor(Color paramColor)
  {
    this.selectionHighlightGradientBegin = null;
    if (paramColor == null)
      return;
    if (this.parent.getDisplay().getDepth() < 15)
      return;
    if (this.parent.selectionGradientColors.length < 2)
      return;
    this.selectionHighlightGradientBegin = paramColor;
    if (!isSelectionHighlightColorsCacheHit(paramColor))
      createSelectionHighlightGradientColors(paramColor);
  }

  String shortenText(GC paramGC, String paramString, int paramInt)
  {
    return useEllipses() ? shortenText(paramGC, paramString, paramInt, "...") : shortenText(paramGC, paramString, paramInt, "");
  }

  String shortenText(GC paramGC, String paramString1, int paramInt, String paramString2)
  {
    if (paramGC.textExtent(paramString1, 9).x <= paramInt)
      return paramString1;
    int i = paramGC.textExtent(paramString2, 9).x;
    int j = paramString1.length();
    TextLayout localTextLayout = new TextLayout(this.parent.getDisplay());
    localTextLayout.setText(paramString1);
    for (int k = localTextLayout.getPreviousOffset(j, 2); k > 0; k = localTextLayout.getPreviousOffset(k, 2))
    {
      paramString1 = paramString1.substring(0, k);
      int m = paramGC.textExtent(paramString1, 9).x;
      if (m + i <= paramInt)
        break;
    }
    localTextLayout.dispose();
    return paramString1 + paramString2;
  }

  void updateCurves()
  {
    int i = this.parent.tabHeight;
    if (i == this.lastTabHeight)
      return;
    int j;
    if (this.parent.onBottom)
    {
      j = i - 12;
      this.curve = new int[] { 0, 13 + j, 0, 12 + j, 2, 12 + j, 3, 11 + j, 5, 11 + j, 6, 10 + j, 7, 10 + j, 9, 8 + j, 10, 8 + j, 11, 7 + j, 11 + j, 7, 12 + j, 6, 13 + j, 6, 15 + j, 4, 16 + j, 4, 17 + j, 3, 19 + j, 3, 20 + j, 2, 22 + j, 2, 23 + j, 1 };
      this.curveWidth = (26 + j);
      this.curveIndent = (this.curveWidth / 3);
    }
    else
    {
      j = i - 12;
      this.curve = new int[] { 0, 0, 0, 1, 2, 1, 3, 2, 5, 2, 6, 3, 7, 3, 9, 5, 10, 5, 11, 6, 11 + j, 6 + j, 12 + j, 7 + j, 13 + j, 7 + j, 15 + j, 9 + j, 16 + j, 9 + j, 17 + j, 10 + j, 19 + j, 10 + j, 20 + j, 11 + j, 22 + j, 11 + j, 23 + j, 12 + j };
      this.curveWidth = (26 + j);
      this.curveIndent = (this.curveWidth / 3);
      this.topCurveHighlightStart = new int[] { 0, 2, 1, 2, 2, 2, 3, 3, 4, 3, 5, 3, 6, 4, 7, 4, 8, 5, 9, 6, 10, 6 };
      this.topCurveHighlightEnd = new int[] { 10 + j, 6 + j, 11 + j, 7 + j, 12 + j, 8 + j, 13 + j, 8 + j, 14 + j, 9 + j, 15 + j, 10 + j, 16 + j, 10 + j, 17 + j, 11 + j, 18 + j, 11 + j, 19 + j, 11 + j, 20 + j, 12 + j, 21 + j, 12 + j, 22 + j, 12 + j };
    }
  }

  boolean useEllipses()
  {
    return this.parent.simple;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.custom.CTabFolderRenderer
 * JD-Core Version:    0.6.2
 */