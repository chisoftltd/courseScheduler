package org.eclipse.swt.custom;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.accessibility.Accessible;
import org.eclipse.swt.accessibility.AccessibleAdapter;
import org.eclipse.swt.accessibility.AccessibleControlAdapter;
import org.eclipse.swt.accessibility.AccessibleControlEvent;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.TextLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class CLabel extends Canvas
{
  private static final int GAP = 5;
  private static final int DEFAULT_MARGIN = 3;
  private static final String ELLIPSIS = "...";
  private int align = 16384;
  private int leftMargin = 3;
  private int topMargin = 3;
  private int rightMargin = 3;
  private int bottomMargin = 3;
  private String text;
  private Image image;
  private String appToolTipText;
  private boolean ignoreDispose;
  private Image backgroundImage;
  private Color[] gradientColors;
  private int[] gradientPercents;
  private boolean gradientVertical;
  private Color background;
  private static int DRAW_FLAGS = 15;

  public CLabel(Composite paramComposite, int paramInt)
  {
    super(paramComposite, checkStyle(paramInt));
    if ((paramInt & 0x1020000) == 0)
      paramInt |= 16384;
    if ((paramInt & 0x1000000) != 0)
      this.align = 16777216;
    if ((paramInt & 0x20000) != 0)
      this.align = 131072;
    if ((paramInt & 0x4000) != 0)
      this.align = 16384;
    addPaintListener(new PaintListener()
    {
      public void paintControl(PaintEvent paramAnonymousPaintEvent)
      {
        CLabel.this.onPaint(paramAnonymousPaintEvent);
      }
    });
    addTraverseListener(new TraverseListener()
    {
      public void keyTraversed(TraverseEvent paramAnonymousTraverseEvent)
      {
        if (paramAnonymousTraverseEvent.detail == 128)
          CLabel.this.onMnemonic(paramAnonymousTraverseEvent);
      }
    });
    addListener(12, new Listener()
    {
      public void handleEvent(Event paramAnonymousEvent)
      {
        CLabel.this.onDispose(paramAnonymousEvent);
      }
    });
    initAccessible();
  }

  private static int checkStyle(int paramInt)
  {
    if ((paramInt & 0x800) != 0)
      paramInt |= 4;
    int i = 100663340;
    paramInt &= i;
    return paramInt |= 537395200;
  }

  public Point computeSize(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    checkWidget();
    Point localPoint = getTotalSize(this.image, this.text);
    if (paramInt1 == -1)
      localPoint.x += this.leftMargin + this.rightMargin;
    else
      localPoint.x = paramInt1;
    if (paramInt2 == -1)
      localPoint.y += this.topMargin + this.bottomMargin;
    else
      localPoint.y = paramInt2;
    return localPoint;
  }

  private void drawBevelRect(GC paramGC, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Color paramColor1, Color paramColor2)
  {
    paramGC.setForeground(paramColor2);
    paramGC.drawLine(paramInt1 + paramInt3, paramInt2, paramInt1 + paramInt3, paramInt2 + paramInt4);
    paramGC.drawLine(paramInt1, paramInt2 + paramInt4, paramInt1 + paramInt3, paramInt2 + paramInt4);
    paramGC.setForeground(paramColor1);
    paramGC.drawLine(paramInt1, paramInt2, paramInt1 + paramInt3 - 1, paramInt2);
    paramGC.drawLine(paramInt1, paramInt2, paramInt1, paramInt2 + paramInt4 - 1);
  }

  char _findMnemonic(String paramString)
  {
    if (paramString == null)
      return '\000';
    int i = 0;
    int j = paramString.length();
    do
    {
      while ((i < j) && (paramString.charAt(i) != '&'))
        i++;
      i++;
      if (i >= j)
        return '\000';
      if (paramString.charAt(i) != '&')
        return Character.toLowerCase(paramString.charAt(i));
      i++;
    }
    while (i < j);
    return '\000';
  }

  public int getAlignment()
  {
    return this.align;
  }

  public int getBottomMargin()
  {
    return this.bottomMargin;
  }

  public Image getImage()
  {
    return this.image;
  }

  public int getLeftMargin()
  {
    return this.leftMargin;
  }

  public int getRightMargin()
  {
    return this.rightMargin;
  }

  private Point getTotalSize(Image paramImage, String paramString)
  {
    Point localPoint1 = new Point(0, 0);
    if (paramImage != null)
    {
      localObject = paramImage.getBounds();
      localPoint1.x += ((Rectangle)localObject).width;
      localPoint1.y += ((Rectangle)localObject).height;
    }
    Object localObject = new GC(this);
    if ((paramString != null) && (paramString.length() > 0))
    {
      Point localPoint2 = ((GC)localObject).textExtent(paramString, DRAW_FLAGS);
      localPoint1.x += localPoint2.x;
      localPoint1.y = Math.max(localPoint1.y, localPoint2.y);
      if (paramImage != null)
        localPoint1.x += 5;
    }
    else
    {
      localPoint1.y = Math.max(localPoint1.y, ((GC)localObject).getFontMetrics().getHeight());
    }
    ((GC)localObject).dispose();
    return localPoint1;
  }

  public int getStyle()
  {
    int i = super.getStyle();
    switch (this.align)
    {
    case 131072:
      i |= 131072;
      break;
    case 16777216:
      i |= 16777216;
      break;
    case 16384:
      i |= 16384;
    }
    return i;
  }

  public String getText()
  {
    return this.text;
  }

  public String getToolTipText()
  {
    checkWidget();
    return this.appToolTipText;
  }

  public int getTopMargin()
  {
    return this.topMargin;
  }

  private void initAccessible()
  {
    Accessible localAccessible = getAccessible();
    localAccessible.addAccessibleListener(new AccessibleAdapter()
    {
      public void getName(AccessibleEvent paramAnonymousAccessibleEvent)
      {
        paramAnonymousAccessibleEvent.result = CLabel.this.getText();
      }

      public void getHelp(AccessibleEvent paramAnonymousAccessibleEvent)
      {
        paramAnonymousAccessibleEvent.result = CLabel.this.getToolTipText();
      }

      public void getKeyboardShortcut(AccessibleEvent paramAnonymousAccessibleEvent)
      {
        char c = CLabel.this._findMnemonic(CLabel.this.text);
        if (c != 0)
          paramAnonymousAccessibleEvent.result = ("Alt+" + c);
      }
    });
    localAccessible.addAccessibleControlListener(new AccessibleControlAdapter()
    {
      public void getChildAtPoint(AccessibleControlEvent paramAnonymousAccessibleControlEvent)
      {
        paramAnonymousAccessibleControlEvent.childID = -1;
      }

      public void getLocation(AccessibleControlEvent paramAnonymousAccessibleControlEvent)
      {
        Rectangle localRectangle = CLabel.this.getDisplay().map(CLabel.this.getParent(), null, CLabel.this.getBounds());
        paramAnonymousAccessibleControlEvent.x = localRectangle.x;
        paramAnonymousAccessibleControlEvent.y = localRectangle.y;
        paramAnonymousAccessibleControlEvent.width = localRectangle.width;
        paramAnonymousAccessibleControlEvent.height = localRectangle.height;
      }

      public void getChildCount(AccessibleControlEvent paramAnonymousAccessibleControlEvent)
      {
        paramAnonymousAccessibleControlEvent.detail = 0;
      }

      public void getRole(AccessibleControlEvent paramAnonymousAccessibleControlEvent)
      {
        paramAnonymousAccessibleControlEvent.detail = 41;
      }

      public void getState(AccessibleControlEvent paramAnonymousAccessibleControlEvent)
      {
        paramAnonymousAccessibleControlEvent.detail = 64;
      }
    });
  }

  void onDispose(Event paramEvent)
  {
    if (this.ignoreDispose)
    {
      this.ignoreDispose = false;
      return;
    }
    this.ignoreDispose = true;
    notifyListeners(paramEvent.type, paramEvent);
    paramEvent.type = 0;
    this.gradientColors = null;
    this.gradientPercents = null;
    this.backgroundImage = null;
    this.text = null;
    this.image = null;
    this.appToolTipText = null;
  }

  void onMnemonic(TraverseEvent paramTraverseEvent)
  {
    int i = _findMnemonic(this.text);
    if (i == 0)
      return;
    if (Character.toLowerCase(paramTraverseEvent.character) != i)
      return;
    for (Composite localComposite = getParent(); localComposite != null; localComposite = localComposite.getParent())
    {
      Control[] arrayOfControl = localComposite.getChildren();
      for (int j = 0; j < arrayOfControl.length; j++)
        if (arrayOfControl[j] == this)
          break;
      j++;
      if ((j < arrayOfControl.length) && (arrayOfControl[j].setFocus()))
      {
        paramTraverseEvent.doit = true;
        paramTraverseEvent.detail = 0;
      }
    }
  }

  void onPaint(PaintEvent paramPaintEvent)
  {
    Rectangle localRectangle = getClientArea();
    if ((localRectangle.width == 0) || (localRectangle.height == 0))
      return;
    int i = 0;
    String str = this.text;
    Image localImage = this.image;
    int j = Math.max(0, localRectangle.width - (this.leftMargin + this.rightMargin));
    Point localPoint = getTotalSize(localImage, str);
    if (localPoint.x > j)
    {
      localImage = null;
      localPoint = getTotalSize(localImage, str);
      if (localPoint.x > j)
        i = 1;
    }
    GC localGC = paramPaintEvent.gc;
    String[] arrayOfString = this.text == null ? null : splitString(this.text);
    Object localObject1;
    if (i != 0)
    {
      localPoint.x = 0;
      for (k = 0; k < arrayOfString.length; k++)
      {
        localObject1 = localGC.textExtent(arrayOfString[k], DRAW_FLAGS);
        if (((Point)localObject1).x > j)
        {
          arrayOfString[k] = shortenText(localGC, arrayOfString[k], j);
          localPoint.x = Math.max(localPoint.x, getTotalSize(null, arrayOfString[k]).x);
        }
        else
        {
          localPoint.x = Math.max(localPoint.x, ((Point)localObject1).x);
        }
      }
      if (this.appToolTipText == null)
        super.setToolTipText(this.text);
    }
    else
    {
      super.setToolTipText(this.appToolTipText);
    }
    int k = localRectangle.x + this.leftMargin;
    if (this.align == 16777216)
      k = (localRectangle.width - localPoint.x) / 2;
    if (this.align == 131072)
      k = localRectangle.width - this.rightMargin - localPoint.x;
    try
    {
      if (this.backgroundImage != null)
      {
        localObject1 = this.backgroundImage.getBounds();
        localGC.setBackground(getBackground());
        localGC.fillRectangle(localRectangle);
        int n = 0;
        while (n < localRectangle.width)
        {
          int i1 = 0;
          while (i1 < localRectangle.height)
          {
            localGC.drawImage(this.backgroundImage, n, i1);
            i1 += ((Rectangle)localObject1).height;
          }
          n += ((Rectangle)localObject1).width;
        }
      }
      else if (this.gradientColors != null)
      {
        localObject1 = localGC.getBackground();
        if (this.gradientColors.length == 1)
        {
          if (this.gradientColors[0] != null)
            localGC.setBackground(this.gradientColors[0]);
          localGC.fillRectangle(0, 0, localRectangle.width, localRectangle.height);
        }
        else
        {
          localObject2 = localGC.getForeground();
          Object localObject3 = this.gradientColors[0];
          if (localObject3 == null)
            localObject3 = localObject1;
          i3 = 0;
          for (i4 = 0; i4 < this.gradientPercents.length; i4++)
          {
            localGC.setForeground((Color)localObject3);
            localObject3 = this.gradientColors[(i4 + 1)];
            if (localObject3 == null)
              localObject3 = localObject1;
            localGC.setBackground((Color)localObject3);
            if (this.gradientVertical)
            {
              i5 = this.gradientPercents[i4] * localRectangle.height / 100 - i3;
              localGC.fillGradientRectangle(0, i3, localRectangle.width, i5, true);
              i3 += i5;
            }
            else
            {
              i5 = this.gradientPercents[i4] * localRectangle.width / 100 - i3;
              localGC.fillGradientRectangle(i3, 0, i5, localRectangle.height, false);
              i3 += i5;
            }
          }
          if ((this.gradientVertical) && (i3 < localRectangle.height))
          {
            localGC.setBackground(getBackground());
            localGC.fillRectangle(0, i3, localRectangle.width, localRectangle.height - i3);
          }
          if ((!this.gradientVertical) && (i3 < localRectangle.width))
          {
            localGC.setBackground(getBackground());
            localGC.fillRectangle(i3, 0, localRectangle.width - i3, localRectangle.height);
          }
          localGC.setForeground((Color)localObject2);
        }
        localGC.setBackground((Color)localObject1);
      }
      else if ((this.background != null) || ((getStyle() & 0x20000000) == 0))
      {
        localGC.setBackground(getBackground());
        localGC.fillRectangle(localRectangle);
      }
    }
    catch (SWTException localSWTException)
    {
      if ((getStyle() & 0x20000000) == 0)
      {
        localGC.setBackground(getBackground());
        localGC.fillRectangle(localRectangle);
      }
    }
    int m = getStyle();
    if (((m & 0x4) != 0) || ((m & 0x8) != 0))
      paintBorder(localGC, localRectangle);
    Object localObject2 = null;
    int i2 = 0;
    int i3 = 0;
    int i4 = 0;
    if (localImage != null)
    {
      localObject2 = localImage.getBounds();
      i4 = ((Rectangle)localObject2).height;
    }
    if (arrayOfString != null)
    {
      i2 = localGC.getFontMetrics().getHeight();
      i3 = arrayOfString.length * i2;
    }
    int i5 = 0;
    int i6 = 0;
    int i7 = 0;
    if (i4 > i3)
    {
      if ((this.topMargin == 3) && (this.bottomMargin == 3))
        i5 = localRectangle.y + (localRectangle.height - i4) / 2;
      else
        i5 = this.topMargin;
      i6 = i5 + i4 / 2;
      i7 = i6 - i3 / 2;
    }
    else
    {
      if ((this.topMargin == 3) && (this.bottomMargin == 3))
        i7 = localRectangle.y + (localRectangle.height - i3) / 2;
      else
        i7 = this.topMargin;
      i6 = i7 + i3 / 2;
      i5 = i6 - i4 / 2;
    }
    if (localImage != null)
    {
      localGC.drawImage(localImage, 0, 0, ((Rectangle)localObject2).width, i4, k, i5, ((Rectangle)localObject2).width, i4);
      k += ((Rectangle)localObject2).width + 5;
      localPoint.x -= ((Rectangle)localObject2).width + 5;
    }
    if (arrayOfString != null)
    {
      localGC.setForeground(getForeground());
      for (int i8 = 0; i8 < arrayOfString.length; i8++)
      {
        int i9 = k;
        if (arrayOfString.length > 1)
        {
          int i10;
          if (this.align == 16777216)
          {
            i10 = localGC.textExtent(arrayOfString[i8], DRAW_FLAGS).x;
            i9 = k + Math.max(0, (localPoint.x - i10) / 2);
          }
          if (this.align == 131072)
          {
            i10 = localGC.textExtent(arrayOfString[i8], DRAW_FLAGS).x;
            i9 = Math.max(k, localRectangle.x + localRectangle.width - this.rightMargin - i10);
          }
        }
        localGC.drawText(arrayOfString[i8], i9, i7, DRAW_FLAGS);
        i7 += i2;
      }
    }
  }

  private void paintBorder(GC paramGC, Rectangle paramRectangle)
  {
    Display localDisplay = getDisplay();
    Color localColor1 = null;
    Color localColor2 = null;
    int i = getStyle();
    if ((i & 0x4) != 0)
    {
      localColor1 = localDisplay.getSystemColor(18);
      localColor2 = localDisplay.getSystemColor(20);
    }
    if ((i & 0x8) != 0)
    {
      localColor1 = localDisplay.getSystemColor(19);
      localColor2 = localDisplay.getSystemColor(18);
    }
    if ((localColor1 != null) && (localColor2 != null))
    {
      paramGC.setLineWidth(1);
      drawBevelRect(paramGC, paramRectangle.x, paramRectangle.y, paramRectangle.width - 1, paramRectangle.height - 1, localColor1, localColor2);
    }
  }

  public void setAlignment(int paramInt)
  {
    checkWidget();
    if ((paramInt != 16384) && (paramInt != 131072) && (paramInt != 16777216))
      SWT.error(5);
    if (this.align != paramInt)
    {
      this.align = paramInt;
      redraw();
    }
  }

  public void setBackground(Color paramColor)
  {
    super.setBackground(paramColor);
    if ((this.backgroundImage == null) && (this.gradientColors == null) && (this.gradientPercents == null))
      if (paramColor == null)
      {
        if (this.background != null);
      }
      else if (paramColor.equals(this.background))
        return;
    this.background = paramColor;
    this.backgroundImage = null;
    this.gradientColors = null;
    this.gradientPercents = null;
    redraw();
  }

  public void setBackground(Color[] paramArrayOfColor, int[] paramArrayOfInt)
  {
    setBackground(paramArrayOfColor, paramArrayOfInt, false);
  }

  public void setBackground(Color[] paramArrayOfColor, int[] paramArrayOfInt, boolean paramBoolean)
  {
    checkWidget();
    if (paramArrayOfColor != null)
    {
      if ((paramArrayOfInt == null) || (paramArrayOfInt.length != paramArrayOfColor.length - 1))
        SWT.error(5);
      if (getDisplay().getDepth() < 15)
      {
        paramArrayOfColor = new Color[] { paramArrayOfColor[(paramArrayOfColor.length - 1)] };
        paramArrayOfInt = new int[0];
      }
      for (int i = 0; i < paramArrayOfInt.length; i++)
      {
        if ((paramArrayOfInt[i] < 0) || (paramArrayOfInt[i] > 100))
          SWT.error(5);
        if ((i > 0) && (paramArrayOfInt[i] < paramArrayOfInt[(i - 1)]))
          SWT.error(5);
      }
    }
    Color localColor = getBackground();
    int j;
    if (this.backgroundImage == null)
    {
      if ((this.gradientColors != null) && (paramArrayOfColor != null) && (this.gradientColors.length == paramArrayOfColor.length))
      {
        j = 0;
        for (int k = 0; k < this.gradientColors.length; k++)
        {
          j = (this.gradientColors[k] != paramArrayOfColor[k]) && ((this.gradientColors[k] != null) || (paramArrayOfColor[k] != localColor)) && ((this.gradientColors[k] != localColor) || (paramArrayOfColor[k] != null)) ? 0 : 1;
          if (j == 0)
            break;
        }
        if (j != 0)
          for (k = 0; k < this.gradientPercents.length; k++)
          {
            j = this.gradientPercents[k] == paramArrayOfInt[k] ? 1 : 0;
            if (j == 0)
              break;
          }
        if ((j == 0) || (this.gradientVertical != paramBoolean));
      }
    }
    else
      this.backgroundImage = null;
    if (paramArrayOfColor == null)
    {
      this.gradientColors = null;
      this.gradientPercents = null;
      this.gradientVertical = false;
    }
    else
    {
      this.gradientColors = new Color[paramArrayOfColor.length];
      for (j = 0; j < paramArrayOfColor.length; j++)
        this.gradientColors[j] = (paramArrayOfColor[j] != null ? paramArrayOfColor[j] : localColor);
      this.gradientPercents = new int[paramArrayOfInt.length];
      for (j = 0; j < paramArrayOfInt.length; j++)
        this.gradientPercents[j] = paramArrayOfInt[j];
      this.gradientVertical = paramBoolean;
    }
    redraw();
  }

  public void setBackground(Image paramImage)
  {
    checkWidget();
    if (paramImage == this.backgroundImage)
      return;
    if (paramImage != null)
    {
      this.gradientColors = null;
      this.gradientPercents = null;
    }
    this.backgroundImage = paramImage;
    redraw();
  }

  public void setBottomMargin(int paramInt)
  {
    checkWidget();
    if ((this.bottomMargin == paramInt) || (paramInt < 0))
      return;
    this.bottomMargin = paramInt;
    redraw();
  }

  public void setFont(Font paramFont)
  {
    super.setFont(paramFont);
    redraw();
  }

  public void setImage(Image paramImage)
  {
    checkWidget();
    if (paramImage != this.image)
    {
      this.image = paramImage;
      redraw();
    }
  }

  public void setLeftMargin(int paramInt)
  {
    checkWidget();
    if ((this.leftMargin == paramInt) || (paramInt < 0))
      return;
    this.leftMargin = paramInt;
    redraw();
  }

  public void setMargins(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    checkWidget();
    this.leftMargin = Math.max(0, paramInt1);
    this.topMargin = Math.max(0, paramInt2);
    this.rightMargin = Math.max(0, paramInt3);
    this.bottomMargin = Math.max(0, paramInt4);
    redraw();
  }

  public void setRightMargin(int paramInt)
  {
    checkWidget();
    if ((this.rightMargin == paramInt) || (paramInt < 0))
      return;
    this.rightMargin = paramInt;
    redraw();
  }

  public void setText(String paramString)
  {
    checkWidget();
    if (paramString == null)
      paramString = "";
    if (!paramString.equals(this.text))
    {
      this.text = paramString;
      redraw();
    }
  }

  public void setToolTipText(String paramString)
  {
    super.setToolTipText(paramString);
    this.appToolTipText = super.getToolTipText();
  }

  public void setTopMargin(int paramInt)
  {
    checkWidget();
    if ((this.topMargin == paramInt) || (paramInt < 0))
      return;
    this.topMargin = paramInt;
    redraw();
  }

  protected String shortenText(GC paramGC, String paramString, int paramInt)
  {
    if (paramString == null)
      return null;
    int i = paramGC.textExtent("...", DRAW_FLAGS).x;
    if (paramInt <= i)
      return paramString;
    int j = paramString.length();
    int k = j / 2;
    int m = 0;
    int n = (k + m) / 2 - 1;
    if (n <= 0)
      return paramString;
    TextLayout localTextLayout = new TextLayout(getDisplay());
    localTextLayout.setText(paramString);
    n = validateOffset(localTextLayout, n);
    while ((m < n) && (n < k))
    {
      str1 = paramString.substring(0, n);
      String str2 = paramString.substring(validateOffset(localTextLayout, j - n), j);
      int i1 = paramGC.textExtent(str1, DRAW_FLAGS).x;
      int i2 = paramGC.textExtent(str2, DRAW_FLAGS).x;
      if (i1 + i + i2 > paramInt)
      {
        k = n;
        n = validateOffset(localTextLayout, (k + m) / 2);
      }
      else if (i1 + i + i2 < paramInt)
      {
        m = n;
        n = validateOffset(localTextLayout, (k + m) / 2);
      }
      else
      {
        m = k;
      }
    }
    String str1 = paramString.substring(0, n) + "..." + paramString.substring(validateOffset(localTextLayout, j - n), j);
    localTextLayout.dispose();
    return str1;
  }

  int validateOffset(TextLayout paramTextLayout, int paramInt)
  {
    int i = paramTextLayout.getNextOffset(paramInt, 2);
    if (i != paramInt)
      return paramTextLayout.getPreviousOffset(i, 2);
    return paramInt;
  }

  private String[] splitString(String paramString)
  {
    Object localObject = new String[1];
    int i = 0;
    int j;
    do
    {
      j = paramString.indexOf('\n', i);
      if (j == -1)
      {
        localObject[(localObject.length - 1)] = paramString.substring(i);
      }
      else
      {
        int k = (j > 0) && (paramString.charAt(j - 1) == '\r') ? 1 : 0;
        localObject[(localObject.length - 1)] = paramString.substring(i, j - (k != 0 ? 1 : 0));
        i = j + 1;
        String[] arrayOfString = new String[localObject.length + 1];
        System.arraycopy(localObject, 0, arrayOfString, 0, localObject.length);
        localObject = arrayOfString;
      }
    }
    while (j != -1);
    return localObject;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.custom.CLabel
 * JD-Core Version:    0.6.2
 */