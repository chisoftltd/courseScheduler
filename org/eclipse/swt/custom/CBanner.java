package org.eclipse.swt.custom;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
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

public class CBanner extends Composite
{
  Control left;
  Control right;
  Control bottom;
  boolean simple = true;
  int[] curve = new int[0];
  int curveStart = 0;
  Rectangle curveRect = new Rectangle(0, 0, 0, 0);
  int curve_width = 5;
  int curve_indent = -2;
  int rightWidth = -1;
  int rightMinWidth = 0;
  int rightMinHeight = 0;
  Cursor resizeCursor;
  boolean dragging = false;
  int rightDragDisplacement = 0;
  Listener listener;
  static final int OFFSCREEN = -200;
  static final int BORDER_BOTTOM = 2;
  static final int BORDER_TOP = 3;
  static final int BORDER_STRIPE = 1;
  static final int CURVE_TAIL = 200;
  static final int BEZIER_RIGHT = 30;
  static final int BEZIER_LEFT = 30;
  static final int MIN_LEFT = 10;
  static int BORDER1 = 20;

  public CBanner(Composite paramComposite, int paramInt)
  {
    super(paramComposite, checkStyle(paramInt));
    super.setLayout(new CBannerLayout());
    this.resizeCursor = new Cursor(getDisplay(), 9);
    this.listener = new Listener()
    {
      public void handleEvent(Event paramAnonymousEvent)
      {
        switch (paramAnonymousEvent.type)
        {
        case 12:
          CBanner.this.onDispose(paramAnonymousEvent);
          break;
        case 3:
          CBanner.this.onMouseDown(paramAnonymousEvent.x, paramAnonymousEvent.y);
          break;
        case 7:
          CBanner.this.onMouseExit();
          break;
        case 5:
          CBanner.this.onMouseMove(paramAnonymousEvent.x, paramAnonymousEvent.y);
          break;
        case 4:
          CBanner.this.onMouseUp();
          break;
        case 9:
          CBanner.this.onPaint(paramAnonymousEvent.gc);
          break;
        case 11:
          CBanner.this.onResize();
        case 6:
        case 8:
        case 10:
        }
      }
    };
    int[] arrayOfInt = { 12, 3, 7, 5, 4, 9, 11 };
    for (int i = 0; i < arrayOfInt.length; i++)
      addListener(arrayOfInt[i], this.listener);
  }

  static int[] bezier(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9)
  {
    double d1 = paramInt1;
    double d2 = 3 * (paramInt3 - paramInt1);
    double d3 = 3 * (paramInt1 + paramInt5 - 2 * paramInt3);
    double d4 = paramInt7 - paramInt1 + 3 * paramInt3 - 3 * paramInt5;
    double d5 = paramInt2;
    double d6 = 3 * (paramInt4 - paramInt2);
    double d7 = 3 * (paramInt2 + paramInt6 - 2 * paramInt4);
    double d8 = paramInt8 - paramInt2 + 3 * paramInt4 - 3 * paramInt6;
    int[] arrayOfInt = new int[2 * paramInt9 + 2];
    for (int i = 0; i <= paramInt9; i++)
    {
      double d9 = i / paramInt9;
      arrayOfInt[(2 * i)] = ((int)(d1 + d2 * d9 + d3 * d9 * d9 + d4 * d9 * d9 * d9));
      arrayOfInt[(2 * i + 1)] = ((int)(d5 + d6 * d9 + d7 * d9 * d9 + d8 * d9 * d9 * d9));
    }
    return arrayOfInt;
  }

  static int checkStyle(int paramInt)
  {
    return 0;
  }

  public Control getBottom()
  {
    checkWidget();
    return this.bottom;
  }

  public Rectangle getClientArea()
  {
    return new Rectangle(0, 0, 0, 0);
  }

  public Control getLeft()
  {
    checkWidget();
    return this.left;
  }

  public Control getRight()
  {
    checkWidget();
    return this.right;
  }

  public Point getRightMinimumSize()
  {
    checkWidget();
    return new Point(this.rightMinWidth, this.rightMinHeight);
  }

  public int getRightWidth()
  {
    checkWidget();
    if (this.right == null)
      return 0;
    if (this.rightWidth == -1)
    {
      Point localPoint = this.right.computeSize(-1, -1, false);
      return localPoint.x;
    }
    return this.rightWidth;
  }

  public boolean getSimple()
  {
    checkWidget();
    return this.simple;
  }

  void onDispose(Event paramEvent)
  {
    removeListener(12, this.listener);
    notifyListeners(12, paramEvent);
    paramEvent.type = 0;
    if (this.resizeCursor != null)
      this.resizeCursor.dispose();
    this.resizeCursor = null;
    this.left = null;
    this.right = null;
    this.bottom = null;
  }

  void onMouseDown(int paramInt1, int paramInt2)
  {
    if (this.curveRect.contains(paramInt1, paramInt2))
    {
      this.dragging = true;
      this.rightDragDisplacement = (this.curveStart - paramInt1 + this.curve_width - this.curve_indent);
    }
  }

  void onMouseExit()
  {
    if (!this.dragging)
      setCursor(null);
  }

  void onMouseMove(int paramInt1, int paramInt2)
  {
    if (this.dragging)
    {
      Point localPoint1 = getSize();
      if ((paramInt1 <= 0) || (paramInt1 >= localPoint1.x))
        return;
      this.rightWidth = Math.max(0, localPoint1.x - paramInt1 - this.rightDragDisplacement);
      if (this.rightMinWidth == -1)
      {
        Point localPoint2 = this.right.computeSize(this.rightMinWidth, this.rightMinHeight);
        this.rightWidth = Math.max(localPoint2.x, this.rightWidth);
      }
      else
      {
        this.rightWidth = Math.max(this.rightMinWidth, this.rightWidth);
      }
      layout(false);
      return;
    }
    if (this.curveRect.contains(paramInt1, paramInt2))
      setCursor(this.resizeCursor);
    else
      setCursor(null);
  }

  void onMouseUp()
  {
    this.dragging = false;
  }

  void onPaint(GC paramGC)
  {
    if ((this.left == null) && (this.right == null))
      return;
    Point localPoint = getSize();
    Color localColor1 = getDisplay().getSystemColor(BORDER1);
    if (this.bottom != null)
    {
      int i = this.bottom.getBounds().y - 1 - 1;
      paramGC.setForeground(localColor1);
      paramGC.drawLine(0, i, localPoint.x, i);
    }
    if ((this.left == null) || (this.right == null))
      return;
    int[] arrayOfInt1 = new int[this.curve.length + 6];
    int j = 0;
    int k = this.curveStart;
    arrayOfInt1[(j++)] = (k + 1);
    arrayOfInt1[(j++)] = (localPoint.y - 1);
    for (int m = 0; m < this.curve.length / 2; m++)
    {
      arrayOfInt1[(j++)] = (k + this.curve[(2 * m)]);
      arrayOfInt1[(j++)] = this.curve[(2 * m + 1)];
    }
    arrayOfInt1[(j++)] = (k + this.curve_width);
    arrayOfInt1[(j++)] = 0;
    arrayOfInt1[(j++)] = localPoint.x;
    arrayOfInt1[(j++)] = 0;
    Color localColor2 = getBackground();
    if (getDisplay().getDepth() >= 15)
    {
      int[] arrayOfInt2 = new int[arrayOfInt1.length];
      j = 0;
      for (int i1 = 0; i1 < arrayOfInt1.length / 2; i1++)
      {
        arrayOfInt2[j] = (arrayOfInt1[(j++)] - 1);
        arrayOfInt2[j] = arrayOfInt1[(j++)];
      }
      int[] arrayOfInt3 = new int[arrayOfInt1.length];
      j = 0;
      for (int i2 = 0; i2 < arrayOfInt1.length / 2; i2++)
      {
        arrayOfInt3[j] = (arrayOfInt1[(j++)] + 1);
        arrayOfInt3[j] = arrayOfInt1[(j++)];
      }
      RGB localRGB1 = localColor1.getRGB();
      RGB localRGB2 = localColor2.getRGB();
      int i3 = localRGB1.red + 3 * (localRGB2.red - localRGB1.red) / 4;
      int i4 = localRGB1.green + 3 * (localRGB2.green - localRGB1.green) / 4;
      int i5 = localRGB1.blue + 3 * (localRGB2.blue - localRGB1.blue) / 4;
      Color localColor3 = new Color(getDisplay(), i3, i4, i5);
      paramGC.setForeground(localColor3);
      paramGC.drawPolyline(arrayOfInt2);
      paramGC.drawPolyline(arrayOfInt3);
      localColor3.dispose();
      int i6 = Math.max(0, this.curveStart - 200);
      paramGC.setForeground(localColor2);
      paramGC.setBackground(localColor1);
      paramGC.fillGradientRectangle(i6, localPoint.y - 1, this.curveStart - i6 + 1, 1, false);
    }
    else
    {
      int n = Math.max(0, this.curveStart - 200);
      paramGC.setForeground(localColor1);
      paramGC.drawLine(n, localPoint.y - 1, this.curveStart + 1, localPoint.y - 1);
    }
    paramGC.setForeground(localColor1);
    paramGC.drawPolyline(arrayOfInt1);
  }

  void onResize()
  {
    updateCurve(getSize().y);
  }

  public void setBottom(Control paramControl)
  {
    checkWidget();
    if ((paramControl != null) && (paramControl.getParent() != this))
      SWT.error(5);
    if ((this.bottom != null) && (!this.bottom.isDisposed()))
    {
      Point localPoint = this.bottom.getSize();
      this.bottom.setLocation(-200 - localPoint.x, -200 - localPoint.y);
    }
    this.bottom = paramControl;
    layout(false);
  }

  public void setLayout(Layout paramLayout)
  {
    checkWidget();
  }

  public void setLeft(Control paramControl)
  {
    checkWidget();
    if ((paramControl != null) && (paramControl.getParent() != this))
      SWT.error(5);
    if ((this.left != null) && (!this.left.isDisposed()))
    {
      Point localPoint = this.left.getSize();
      this.left.setLocation(-200 - localPoint.x, -200 - localPoint.y);
    }
    this.left = paramControl;
    layout(false);
  }

  public void setRight(Control paramControl)
  {
    checkWidget();
    if ((paramControl != null) && (paramControl.getParent() != this))
      SWT.error(5);
    if ((this.right != null) && (!this.right.isDisposed()))
    {
      Point localPoint = this.right.getSize();
      this.right.setLocation(-200 - localPoint.x, -200 - localPoint.y);
    }
    this.right = paramControl;
    layout(false);
  }

  public void setRightMinimumSize(Point paramPoint)
  {
    checkWidget();
    if ((paramPoint == null) || (paramPoint.x < -1) || (paramPoint.y < -1))
      SWT.error(5);
    this.rightMinWidth = paramPoint.x;
    this.rightMinHeight = paramPoint.y;
    layout(false);
  }

  public void setRightWidth(int paramInt)
  {
    checkWidget();
    if (paramInt < -1)
      SWT.error(5);
    this.rightWidth = paramInt;
    layout(false);
  }

  public void setSimple(boolean paramBoolean)
  {
    checkWidget();
    if (this.simple != paramBoolean)
    {
      this.simple = paramBoolean;
      if (paramBoolean)
      {
        this.curve_width = 5;
        this.curve_indent = -2;
      }
      else
      {
        this.curve_width = 50;
        this.curve_indent = 5;
      }
      updateCurve(getSize().y);
      layout(false);
      redraw();
    }
  }

  void updateCurve(int paramInt)
  {
    int i = paramInt - 1;
    if (this.simple)
      this.curve = new int[] { 0, i, 1, i, 2, i - 1, 3, i - 2, 3, 2, 4, 1, 5 };
    else
      this.curve = bezier(0, i + 1, 30, i + 1, this.curve_width - 30, 0, this.curve_width, 0, this.curve_width);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.custom.CBanner
 * JD-Core Version:    0.6.2
 */