package org.eclipse.swt.custom;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Scrollable;

class CBannerLayout extends Layout
{
  protected Point computeSize(Composite paramComposite, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    CBanner localCBanner = (CBanner)paramComposite;
    Control localControl1 = localCBanner.left;
    Control localControl2 = localCBanner.right;
    Control localControl3 = localCBanner.bottom;
    int i = (localControl1 != null) && (localControl2 != null) ? 1 : 0;
    int j = paramInt2;
    int k = paramInt1;
    Point localPoint1 = new Point(0, 0);
    int n;
    if (localControl3 != null)
    {
      int m = computeTrim(localControl3);
      n = paramInt1 == -1 ? -1 : Math.max(0, k - m);
      localPoint1 = computeChildSize(localControl3, n, -1, paramBoolean);
    }
    Point localPoint2 = new Point(0, 0);
    int i1;
    if (localControl2 != null)
    {
      n = computeTrim(localControl2);
      i1 = -1;
      if (localCBanner.rightWidth != -1)
      {
        i1 = localCBanner.rightWidth - n;
        if (localControl1 != null)
          i1 = Math.min(i1, k - localCBanner.curve_width + 2 * localCBanner.curve_indent - 10 - n);
        i1 = Math.max(0, i1);
      }
      localPoint2 = computeChildSize(localControl2, i1, -1, paramBoolean);
      if (paramInt1 != -1)
        k -= localPoint2.x + localCBanner.curve_width - 2 * localCBanner.curve_indent;
    }
    Point localPoint3 = new Point(0, 0);
    if (localControl1 != null)
    {
      i1 = computeTrim(localControl1);
      int i2 = paramInt1 == -1 ? -1 : Math.max(0, k - i1);
      localPoint3 = computeChildSize(localControl1, i2, -1, paramBoolean);
    }
    k = localPoint3.x + localPoint2.x;
    j = localPoint1.y;
    if ((localControl3 != null) && ((localControl1 != null) || (localControl2 != null)))
      j += 3;
    if (localControl1 != null)
    {
      if (localControl2 == null)
        j += localPoint3.y;
      else
        j += Math.max(localPoint3.y, localCBanner.rightMinHeight == -1 ? localPoint2.y : localCBanner.rightMinHeight);
    }
    else
      j += localPoint2.y;
    if (i != 0)
    {
      k += localCBanner.curve_width - 2 * localCBanner.curve_indent;
      j += 7;
    }
    if (paramInt1 != -1)
      k = paramInt1;
    if (paramInt2 != -1)
      j = paramInt2;
    return new Point(k, j);
  }

  Point computeChildSize(Control paramControl, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    Object localObject = paramControl.getLayoutData();
    if ((localObject == null) || (!(localObject instanceof CLayoutData)))
    {
      localObject = new CLayoutData();
      paramControl.setLayoutData(localObject);
    }
    return ((CLayoutData)localObject).computeSize(paramControl, paramInt1, paramInt2, paramBoolean);
  }

  int computeTrim(Control paramControl)
  {
    if ((paramControl instanceof Scrollable))
    {
      Rectangle localRectangle = ((Scrollable)paramControl).computeTrim(0, 0, 0, 0);
      return localRectangle.width;
    }
    return paramControl.getBorderWidth() * 2;
  }

  protected boolean flushCache(Control paramControl)
  {
    Object localObject = paramControl.getLayoutData();
    if ((localObject != null) && ((localObject instanceof CLayoutData)))
      ((CLayoutData)localObject).flushCache();
    return true;
  }

  protected void layout(Composite paramComposite, boolean paramBoolean)
  {
    CBanner localCBanner = (CBanner)paramComposite;
    Control localControl1 = localCBanner.left;
    Control localControl2 = localCBanner.right;
    Control localControl3 = localCBanner.bottom;
    Point localPoint1 = localCBanner.getSize();
    int i = (localControl1 != null) && (localControl2 != null) ? 1 : 0;
    int j = localPoint1.x - 2 * localCBanner.getBorderWidth();
    int k = localPoint1.y - 2 * localCBanner.getBorderWidth();
    Point localPoint2 = new Point(0, 0);
    int n;
    if (localControl3 != null)
    {
      int m = computeTrim(localControl3);
      n = Math.max(0, j - m);
      localPoint2 = computeChildSize(localControl3, n, -1, paramBoolean);
      k -= localPoint2.y + 1 + 2;
    }
    if (i != 0)
      k -= 7;
    k = Math.max(0, k);
    Point localPoint3 = new Point(0, 0);
    if (localControl2 != null)
    {
      n = computeTrim(localControl2);
      i1 = -1;
      if (localCBanner.rightWidth != -1)
      {
        i1 = localCBanner.rightWidth - n;
        if (localControl1 != null)
          i1 = Math.min(i1, j - localCBanner.curve_width + 2 * localCBanner.curve_indent - 10 - n);
        i1 = Math.max(0, i1);
      }
      localPoint3 = computeChildSize(localControl2, i1, -1, paramBoolean);
      j -= localPoint3.x - localCBanner.curve_indent + localCBanner.curve_width - localCBanner.curve_indent;
    }
    Point localPoint4 = new Point(0, 0);
    if (localControl1 != null)
    {
      i1 = computeTrim(localControl1);
      i2 = Math.max(0, j - i1);
      localPoint4 = computeChildSize(localControl1, i2, -1, paramBoolean);
    }
    int i1 = 0;
    int i2 = 0;
    int i3 = localCBanner.curveStart;
    Rectangle localRectangle1 = null;
    Rectangle localRectangle2 = null;
    Rectangle localRectangle3 = null;
    if (localControl3 != null)
      localRectangle3 = new Rectangle(i1, i2 + localPoint1.y - localPoint2.y, localPoint2.x, localPoint2.y);
    if (i != 0)
      i2 += 4;
    if (localControl1 != null)
    {
      localRectangle1 = new Rectangle(i1, i2, localPoint4.x, localPoint4.y);
      localCBanner.curveStart = (i1 + localPoint4.x - localCBanner.curve_indent);
      i1 += localPoint4.x - localCBanner.curve_indent + localCBanner.curve_width - localCBanner.curve_indent;
    }
    if (localControl2 != null)
    {
      if (localControl1 != null)
        localPoint3.y = Math.max(localPoint4.y, localCBanner.rightMinHeight == -1 ? localPoint3.y : localCBanner.rightMinHeight);
      localRectangle2 = new Rectangle(i1, i2, localPoint3.x, localPoint3.y);
    }
    if (localCBanner.curveStart < i3)
      localCBanner.redraw(localCBanner.curveStart - 200, 0, i3 + localCBanner.curve_width - localCBanner.curveStart + 200 + 5, localPoint1.y, false);
    if (localCBanner.curveStart > i3)
      localCBanner.redraw(i3 - 200, 0, localCBanner.curveStart + localCBanner.curve_width - i3 + 200 + 5, localPoint1.y, false);
    localCBanner.update();
    localCBanner.curveRect = new Rectangle(localCBanner.curveStart, 0, localCBanner.curve_width, localPoint1.y);
    if (localRectangle3 != null)
      localControl3.setBounds(localRectangle3);
    if (localRectangle2 != null)
      localControl2.setBounds(localRectangle2);
    if (localRectangle1 != null)
      localControl1.setBounds(localRectangle1);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.custom.CBannerLayout
 * JD-Core Version:    0.6.2
 */