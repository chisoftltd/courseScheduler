package org.eclipse.swt.custom;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Scrollable;

class ViewFormLayout extends Layout
{
  protected Point computeSize(Composite paramComposite, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    ViewForm localViewForm = (ViewForm)paramComposite;
    Control localControl1 = localViewForm.topLeft;
    Control localControl2 = localViewForm.topCenter;
    Control localControl3 = localViewForm.topRight;
    Control localControl4 = localViewForm.content;
    Point localPoint1 = new Point(0, 0);
    if (localControl1 != null)
      localPoint1 = computeChildSize(localControl1, -1, -1, paramBoolean);
    Point localPoint2 = new Point(0, 0);
    if (localControl2 != null)
      localPoint2 = computeChildSize(localControl2, -1, -1, paramBoolean);
    Point localPoint3 = new Point(0, 0);
    if (localControl3 != null)
      localPoint3 = computeChildSize(localControl3, -1, -1, paramBoolean);
    Point localPoint4 = new Point(0, 0);
    if ((localViewForm.separateTopCenter) || ((paramInt1 != -1) && (localPoint1.x + localPoint2.x + localPoint3.x > paramInt1)))
    {
      localPoint1.x += localPoint3.x;
      if ((localPoint1.x > 0) && (localPoint3.x > 0))
        localPoint4.x += localViewForm.horizontalSpacing;
      localPoint4.x = Math.max(localPoint2.x, localPoint4.x);
      localPoint4.y = Math.max(localPoint1.y, localPoint3.y);
      if (localControl2 != null)
      {
        localPoint4.y += localPoint2.y;
        if ((localControl1 != null) || (localControl3 != null))
          localPoint4.y += localViewForm.verticalSpacing;
      }
    }
    else
    {
      localPoint4.x = (localPoint1.x + localPoint2.x + localPoint3.x);
      int i = -1;
      if (localPoint1.x > 0)
        i++;
      if (localPoint2.x > 0)
        i++;
      if (localPoint3.x > 0)
        i++;
      if (i > 0)
        localPoint4.x += i * localViewForm.horizontalSpacing;
      localPoint4.y = Math.max(localPoint1.y, Math.max(localPoint2.y, localPoint3.y));
    }
    if (localControl4 != null)
    {
      if ((localControl1 != null) || (localControl3 != null) || (localControl2 != null))
        localPoint4.y += 1;
      Point localPoint5 = new Point(0, 0);
      localPoint5 = computeChildSize(localControl4, -1, -1, paramBoolean);
      localPoint4.x = Math.max(localPoint4.x, localPoint5.x);
      localPoint4.y += localPoint5.y;
      if (localPoint4.y > localPoint5.y)
        localPoint4.y += localViewForm.verticalSpacing;
    }
    localPoint4.x += 2 * localViewForm.marginWidth;
    localPoint4.y += 2 * localViewForm.marginHeight;
    if (paramInt1 != -1)
      localPoint4.x = paramInt1;
    if (paramInt2 != -1)
      localPoint4.y = paramInt2;
    return localPoint4;
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
    ViewForm localViewForm = (ViewForm)paramComposite;
    Control localControl1 = localViewForm.topLeft;
    Control localControl2 = localViewForm.topCenter;
    Control localControl3 = localViewForm.topRight;
    Control localControl4 = localViewForm.content;
    Rectangle localRectangle1 = paramComposite.getClientArea();
    Point localPoint1 = new Point(0, 0);
    if ((localControl1 != null) && (!localControl1.isDisposed()))
      localPoint1 = computeChildSize(localControl1, -1, -1, paramBoolean);
    Object localObject = new Point(0, 0);
    if ((localControl2 != null) && (!localControl2.isDisposed()))
      localObject = computeChildSize(localControl2, -1, -1, paramBoolean);
    Point localPoint2 = new Point(0, 0);
    if ((localControl3 != null) && (!localControl3.isDisposed()))
      localPoint2 = computeChildSize(localControl3, -1, -1, paramBoolean);
    int i = localPoint1.x + ((Point)localObject).x + localPoint2.x + 2 * localViewForm.marginWidth + 2 * localViewForm.highlight;
    int j = -1;
    if (localPoint1.x > 0)
      j++;
    if (((Point)localObject).x > 0)
      j++;
    if (localPoint2.x > 0)
      j++;
    if (j > 0)
      i += j * localViewForm.horizontalSpacing;
    int k = localRectangle1.x + localRectangle1.width - localViewForm.marginWidth - localViewForm.highlight;
    int m = localRectangle1.y + localViewForm.marginHeight + localViewForm.highlight;
    int n = 0;
    int i4;
    if ((localViewForm.separateTopCenter) || (i > localRectangle1.width))
    {
      i1 = Math.max(localPoint2.y, localPoint1.y);
      if ((localControl3 != null) && (!localControl3.isDisposed()))
      {
        n = 1;
        k -= localPoint2.x;
        localControl3.setBounds(k, m, localPoint2.x, i1);
        k -= localViewForm.horizontalSpacing;
      }
      int i2;
      if ((localControl1 != null) && (!localControl1.isDisposed()))
      {
        n = 1;
        i2 = computeTrim(localControl1);
        i4 = k - localRectangle1.x - localViewForm.marginWidth - localViewForm.highlight - i2;
        localPoint1 = computeChildSize(localControl1, i4, -1, false);
        localControl1.setBounds(localRectangle1.x + localViewForm.marginWidth + localViewForm.highlight, m, localPoint1.x, i1);
      }
      if (n != 0)
        m += i1 + localViewForm.verticalSpacing;
      if ((localControl2 != null) && (!localControl2.isDisposed()))
      {
        n = 1;
        i2 = computeTrim(localControl2);
        i4 = localRectangle1.width - 2 * localViewForm.marginWidth - 2 * localViewForm.highlight - i2;
        Point localPoint3 = computeChildSize(localControl2, i4, -1, false);
        if (localPoint3.x < ((Point)localObject).x)
          localObject = localPoint3;
        localControl2.setBounds(localRectangle1.x + localRectangle1.width - localViewForm.marginWidth - localViewForm.highlight - ((Point)localObject).x, m, ((Point)localObject).x, ((Point)localObject).y);
        m += ((Point)localObject).y + localViewForm.verticalSpacing;
      }
    }
    else
    {
      i1 = Math.max(localPoint2.y, Math.max(((Point)localObject).y, localPoint1.y));
      if ((localControl3 != null) && (!localControl3.isDisposed()))
      {
        n = 1;
        k -= localPoint2.x;
        localControl3.setBounds(k, m, localPoint2.x, i1);
        k -= localViewForm.horizontalSpacing;
      }
      if ((localControl2 != null) && (!localControl2.isDisposed()))
      {
        n = 1;
        k -= ((Point)localObject).x;
        localControl2.setBounds(k, m, ((Point)localObject).x, i1);
        k -= localViewForm.horizontalSpacing;
      }
      if ((localControl1 != null) && (!localControl1.isDisposed()))
      {
        n = 1;
        Rectangle localRectangle2 = (localControl1 instanceof Composite) ? ((Composite)localControl1).computeTrim(0, 0, 0, 0) : new Rectangle(0, 0, 0, 0);
        i4 = k - localRectangle1.x - localViewForm.marginWidth - localViewForm.highlight - localRectangle2.width;
        int i5 = i1 - localRectangle2.height;
        localPoint1 = computeChildSize(localControl1, i4, i5, false);
        localControl1.setBounds(localRectangle1.x + localViewForm.marginWidth + localViewForm.highlight, m, localPoint1.x, i1);
      }
      if (n != 0)
        m += i1 + localViewForm.verticalSpacing;
    }
    int i1 = localViewForm.separator;
    localViewForm.separator = -1;
    if ((localControl4 != null) && (!localControl4.isDisposed()))
    {
      if ((localControl1 != null) || (localControl3 != null) || (localControl2 != null))
      {
        localViewForm.separator = m;
        m++;
      }
      localControl4.setBounds(localRectangle1.x + localViewForm.marginWidth + localViewForm.highlight, m, localRectangle1.width - 2 * localViewForm.marginWidth - 2 * localViewForm.highlight, localRectangle1.y + localRectangle1.height - m - localViewForm.marginHeight - localViewForm.highlight);
    }
    if (i1 != localViewForm.separator)
    {
      int i3;
      if (i1 == -1)
      {
        i3 = localViewForm.separator;
        i4 = localViewForm.separator + 1;
      }
      else if (localViewForm.separator == -1)
      {
        i3 = i1;
        i4 = i1 + 1;
      }
      else
      {
        i3 = Math.min(localViewForm.separator, i1);
        i4 = Math.max(localViewForm.separator, i1);
      }
      localViewForm.redraw(localViewForm.borderLeft, i3, localViewForm.getSize().x - localViewForm.borderLeft - localViewForm.borderRight, i4 - i3, false);
    }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.custom.ViewFormLayout
 * JD-Core Version:    0.6.2
 */