package org.eclipse.swt.custom;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.ScrollBar;

class ScrolledCompositeLayout extends Layout
{
  boolean inLayout = false;
  static final int DEFAULT_WIDTH = 64;
  static final int DEFAULT_HEIGHT = 64;

  protected Point computeSize(Composite paramComposite, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    ScrolledComposite localScrolledComposite = (ScrolledComposite)paramComposite;
    Point localPoint1 = new Point(64, 64);
    if (localScrolledComposite.content != null)
    {
      Point localPoint2 = localScrolledComposite.content.computeSize(paramInt1, paramInt2, paramBoolean);
      Point localPoint3 = localScrolledComposite.content.getSize();
      localPoint1.x = (localScrolledComposite.getExpandHorizontal() ? localPoint2.x : localPoint3.x);
      localPoint1.y = (localScrolledComposite.getExpandVertical() ? localPoint2.y : localPoint3.y);
    }
    localPoint1.x = Math.max(localPoint1.x, localScrolledComposite.minWidth);
    localPoint1.y = Math.max(localPoint1.y, localScrolledComposite.minHeight);
    if (paramInt1 != -1)
      localPoint1.x = paramInt1;
    if (paramInt2 != -1)
      localPoint1.y = paramInt2;
    return localPoint1;
  }

  protected boolean flushCache(Control paramControl)
  {
    return true;
  }

  protected void layout(Composite paramComposite, boolean paramBoolean)
  {
    if (this.inLayout)
      return;
    ScrolledComposite localScrolledComposite = (ScrolledComposite)paramComposite;
    if (localScrolledComposite.content == null)
      return;
    ScrollBar localScrollBar1 = localScrolledComposite.getHorizontalBar();
    ScrollBar localScrollBar2 = localScrolledComposite.getVerticalBar();
    if ((localScrollBar1 != null) && (localScrollBar1.getSize().y >= localScrolledComposite.getSize().y))
      return;
    if ((localScrollBar2 != null) && (localScrollBar2.getSize().x >= localScrolledComposite.getSize().x))
      return;
    this.inLayout = true;
    Rectangle localRectangle1 = localScrolledComposite.content.getBounds();
    if (!localScrolledComposite.alwaysShowScroll)
    {
      boolean bool1 = localScrolledComposite.needHScroll(localRectangle1, false);
      boolean bool2 = localScrolledComposite.needVScroll(localRectangle1, bool1);
      if ((!bool1) && (bool2))
        bool1 = localScrolledComposite.needHScroll(localRectangle1, bool2);
      if (localScrollBar1 != null)
        localScrollBar1.setVisible(bool1);
      if (localScrollBar2 != null)
        localScrollBar2.setVisible(bool2);
    }
    Rectangle localRectangle2 = localScrolledComposite.getClientArea();
    if (localScrolledComposite.expandHorizontal)
      localRectangle1.width = Math.max(localScrolledComposite.minWidth, localRectangle2.width);
    if (localScrolledComposite.expandVertical)
      localRectangle1.height = Math.max(localScrolledComposite.minHeight, localRectangle2.height);
    int i;
    int j;
    if (localScrollBar1 != null)
    {
      localScrollBar1.setMaximum(localRectangle1.width);
      localScrollBar1.setThumb(Math.min(localRectangle1.width, localRectangle2.width));
      i = localRectangle1.width - localRectangle2.width;
      j = localScrollBar1.getSelection();
      if (j >= i)
      {
        if (i <= 0)
        {
          j = 0;
          localScrollBar1.setSelection(0);
        }
        localRectangle1.x = (-j);
      }
    }
    if (localScrollBar2 != null)
    {
      localScrollBar2.setMaximum(localRectangle1.height);
      localScrollBar2.setThumb(Math.min(localRectangle1.height, localRectangle2.height));
      i = localRectangle1.height - localRectangle2.height;
      j = localScrollBar2.getSelection();
      if (j >= i)
      {
        if (i <= 0)
        {
          j = 0;
          localScrollBar2.setSelection(0);
        }
        localRectangle1.y = (-j);
      }
    }
    localScrolledComposite.content.setBounds(localRectangle1);
    this.inLayout = false;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.custom.ScrolledCompositeLayout
 * JD-Core Version:    0.6.2
 */