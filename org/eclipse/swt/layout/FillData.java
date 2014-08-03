package org.eclipse.swt.layout;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;

class FillData
{
  int defaultWidth = -1;
  int defaultHeight = -1;
  int currentWhint;
  int currentHhint;
  int currentWidth = -1;
  int currentHeight = -1;

  Point computeSize(Control paramControl, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    if (paramBoolean)
      flushCache();
    Point localPoint;
    if ((paramInt1 == -1) && (paramInt2 == -1))
    {
      if ((this.defaultWidth == -1) || (this.defaultHeight == -1))
      {
        localPoint = paramControl.computeSize(paramInt1, paramInt2, paramBoolean);
        this.defaultWidth = localPoint.x;
        this.defaultHeight = localPoint.y;
      }
      return new Point(this.defaultWidth, this.defaultHeight);
    }
    if ((this.currentWidth == -1) || (this.currentHeight == -1) || (paramInt1 != this.currentWhint) || (paramInt2 != this.currentHhint))
    {
      localPoint = paramControl.computeSize(paramInt1, paramInt2, paramBoolean);
      this.currentWhint = paramInt1;
      this.currentHhint = paramInt2;
      this.currentWidth = localPoint.x;
      this.currentHeight = localPoint.y;
    }
    return new Point(this.currentWidth, this.currentHeight);
  }

  void flushCache()
  {
    this.defaultWidth = (this.defaultHeight = -1);
    this.currentWidth = (this.currentHeight = -1);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.layout.FillData
 * JD-Core Version:    0.6.2
 */