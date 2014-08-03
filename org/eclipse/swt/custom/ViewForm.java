package org.eclipse.swt.custom;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Listener;

public class ViewForm extends Composite
{
  public int marginWidth = 0;
  public int marginHeight = 0;
  public int horizontalSpacing = 1;
  public int verticalSpacing = 1;

  /** @deprecated */
  public static RGB borderInsideRGB = new RGB(132, 130, 132);

  /** @deprecated */
  public static RGB borderMiddleRGB = new RGB(143, 141, 138);

  /** @deprecated */
  public static RGB borderOutsideRGB = new RGB(171, 168, 165);
  Control topLeft;
  Control topCenter;
  Control topRight;
  Control content;
  boolean separateTopCenter = false;
  boolean showBorder = false;
  int separator = -1;
  int borderTop = 0;
  int borderBottom = 0;
  int borderLeft = 0;
  int borderRight = 0;
  int highlight = 0;
  Point oldSize;
  Color selectionBackground;
  Listener listener;
  static final int OFFSCREEN = -200;
  static final int BORDER1_COLOR = 18;
  static final int SELECTION_BACKGROUND = 25;

  public ViewForm(Composite paramComposite, int paramInt)
  {
    super(paramComposite, checkStyle(paramInt));
    super.setLayout(new ViewFormLayout());
    setBorderVisible((paramInt & 0x800) != 0);
    this.listener = new Listener()
    {
      public void handleEvent(Event paramAnonymousEvent)
      {
        switch (paramAnonymousEvent.type)
        {
        case 12:
          ViewForm.this.onDispose(paramAnonymousEvent);
          break;
        case 9:
          ViewForm.this.onPaint(paramAnonymousEvent.gc);
          break;
        case 11:
          ViewForm.this.onResize();
        case 10:
        }
      }
    };
    int[] arrayOfInt = { 12, 9, 11 };
    for (int i = 0; i < arrayOfInt.length; i++)
      addListener(arrayOfInt[i], this.listener);
  }

  static int checkStyle(int paramInt)
  {
    int i = 109051904;
    return paramInt & i | 0x100000;
  }

  public Rectangle computeTrim(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    checkWidget();
    int i = paramInt1 - this.borderLeft - this.highlight;
    int j = paramInt2 - this.borderTop - this.highlight;
    int k = paramInt3 + this.borderLeft + this.borderRight + 2 * this.highlight;
    int m = paramInt4 + this.borderTop + this.borderBottom + 2 * this.highlight;
    return new Rectangle(i, j, k, m);
  }

  public Rectangle getClientArea()
  {
    checkWidget();
    Rectangle localRectangle = super.getClientArea();
    localRectangle.x += this.borderLeft;
    localRectangle.y += this.borderTop;
    localRectangle.width -= this.borderLeft + this.borderRight;
    localRectangle.height -= this.borderTop + this.borderBottom;
    return localRectangle;
  }

  public Control getContent()
  {
    return this.content;
  }

  public Control getTopCenter()
  {
    return this.topCenter;
  }

  public Control getTopLeft()
  {
    return this.topLeft;
  }

  public Control getTopRight()
  {
    return this.topRight;
  }

  void onDispose(Event paramEvent)
  {
    removeListener(12, this.listener);
    notifyListeners(12, paramEvent);
    paramEvent.type = 0;
    this.topLeft = null;
    this.topCenter = null;
    this.topRight = null;
    this.content = null;
    this.oldSize = null;
    this.selectionBackground = null;
  }

  void onPaint(GC paramGC)
  {
    Color localColor1 = paramGC.getForeground();
    Point localPoint = getSize();
    Color localColor2 = getDisplay().getSystemColor(18);
    if (this.showBorder)
    {
      paramGC.setForeground(localColor2);
      paramGC.drawRectangle(0, 0, localPoint.x - 1, localPoint.y - 1);
      if (this.highlight > 0)
      {
        int i = 1;
        int j = 1;
        int k = localPoint.x - 1;
        int m = localPoint.y - 1;
        int[] arrayOfInt = { i, j, k, j, k, m, i, m, i, j + this.highlight, i + this.highlight, j + this.highlight, i + this.highlight, m - this.highlight, k - this.highlight, m - this.highlight, k - this.highlight, j + this.highlight, i, j + this.highlight };
        Color localColor3 = getDisplay().getSystemColor(26);
        paramGC.setBackground(localColor3);
        paramGC.fillPolygon(arrayOfInt);
      }
    }
    if (this.separator > -1)
    {
      paramGC.setForeground(localColor2);
      paramGC.drawLine(this.borderLeft + this.highlight, this.separator, localPoint.x - this.borderLeft - this.borderRight - this.highlight, this.separator);
    }
    paramGC.setForeground(localColor1);
  }

  void onResize()
  {
    Point localPoint = getSize();
    if ((this.oldSize == null) || (this.oldSize.x == 0) || (this.oldSize.y == 0))
    {
      redraw();
    }
    else
    {
      int i = 0;
      if (this.oldSize.x < localPoint.x)
        i = localPoint.x - this.oldSize.x + this.borderRight + this.highlight;
      else if (this.oldSize.x > localPoint.x)
        i = this.borderRight + this.highlight;
      redraw(localPoint.x - i, 0, i, localPoint.y, false);
      int j = 0;
      if (this.oldSize.y < localPoint.y)
        j = localPoint.y - this.oldSize.y + this.borderBottom + this.highlight;
      if (this.oldSize.y > localPoint.y)
        j = this.borderBottom + this.highlight;
      redraw(0, localPoint.y - j, localPoint.x, j, false);
    }
    this.oldSize = localPoint;
  }

  public void setContent(Control paramControl)
  {
    checkWidget();
    if ((paramControl != null) && (paramControl.getParent() != this))
      SWT.error(5);
    if ((this.content != null) && (!this.content.isDisposed()))
      this.content.setBounds(-200, -200, 0, 0);
    this.content = paramControl;
    layout(false);
  }

  public void setLayout(Layout paramLayout)
  {
    checkWidget();
  }

  void setSelectionBackground(Color paramColor)
  {
    checkWidget();
    if (this.selectionBackground == paramColor)
      return;
    if (paramColor == null)
      paramColor = getDisplay().getSystemColor(25);
    this.selectionBackground = paramColor;
    redraw();
  }

  public void setTopCenter(Control paramControl)
  {
    checkWidget();
    if ((paramControl != null) && (paramControl.getParent() != this))
      SWT.error(5);
    if ((this.topCenter != null) && (!this.topCenter.isDisposed()))
    {
      Point localPoint = this.topCenter.getSize();
      this.topCenter.setLocation(-200 - localPoint.x, -200 - localPoint.y);
    }
    this.topCenter = paramControl;
    layout(false);
  }

  public void setTopLeft(Control paramControl)
  {
    checkWidget();
    if ((paramControl != null) && (paramControl.getParent() != this))
      SWT.error(5);
    if ((this.topLeft != null) && (!this.topLeft.isDisposed()))
    {
      Point localPoint = this.topLeft.getSize();
      this.topLeft.setLocation(-200 - localPoint.x, -200 - localPoint.y);
    }
    this.topLeft = paramControl;
    layout(false);
  }

  public void setTopRight(Control paramControl)
  {
    checkWidget();
    if ((paramControl != null) && (paramControl.getParent() != this))
      SWT.error(5);
    if ((this.topRight != null) && (!this.topRight.isDisposed()))
    {
      Point localPoint = this.topRight.getSize();
      this.topRight.setLocation(-200 - localPoint.x, -200 - localPoint.y);
    }
    this.topRight = paramControl;
    layout(false);
  }

  public void setBorderVisible(boolean paramBoolean)
  {
    checkWidget();
    if (this.showBorder == paramBoolean)
      return;
    this.showBorder = paramBoolean;
    if (this.showBorder)
    {
      this.borderLeft = (this.borderTop = this.borderRight = this.borderBottom = 1);
      if ((getStyle() & 0x800000) == 0)
        this.highlight = 2;
    }
    else
    {
      this.borderBottom = (this.borderTop = this.borderLeft = this.borderRight = 0);
      this.highlight = 0;
    }
    layout(false);
    redraw();
  }

  public void setTopCenterSeparate(boolean paramBoolean)
  {
    checkWidget();
    this.separateTopCenter = paramBoolean;
    layout(false);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.custom.ViewForm
 * JD-Core Version:    0.6.2
 */