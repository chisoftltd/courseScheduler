package org.eclipse.swt.layout;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Scrollable;

public final class FillLayout extends Layout
{
  public int type = 256;
  public int marginWidth = 0;
  public int marginHeight = 0;
  public int spacing = 0;

  public FillLayout()
  {
  }

  public FillLayout(int paramInt)
  {
    this.type = paramInt;
  }

  protected Point computeSize(Composite paramComposite, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    Control[] arrayOfControl = paramComposite.getChildren();
    int i = arrayOfControl.length;
    int j = 0;
    int k = 0;
    for (int m = 0; m < i; m++)
    {
      Control localControl = arrayOfControl[m];
      int i1 = paramInt1;
      int i2 = paramInt2;
      if (i > 0)
      {
        if ((this.type == 256) && (paramInt1 != -1))
          i1 = Math.max(0, (paramInt1 - (i - 1) * this.spacing) / i);
        if ((this.type == 512) && (paramInt2 != -1))
          i2 = Math.max(0, (paramInt2 - (i - 1) * this.spacing) / i);
      }
      Point localPoint = computeChildSize(localControl, i1, i2, paramBoolean);
      j = Math.max(j, localPoint.x);
      k = Math.max(k, localPoint.y);
    }
    m = 0;
    int n = 0;
    if (this.type == 256)
    {
      m = i * j;
      if (i != 0)
        m += (i - 1) * this.spacing;
      n = k;
    }
    else
    {
      m = j;
      n = i * k;
      if (i != 0)
        n += (i - 1) * this.spacing;
    }
    m += this.marginWidth * 2;
    n += this.marginHeight * 2;
    if (paramInt1 != -1)
      m = paramInt1;
    if (paramInt2 != -1)
      n = paramInt2;
    return new Point(m, n);
  }

  Point computeChildSize(Control paramControl, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    FillData localFillData = (FillData)paramControl.getLayoutData();
    if (localFillData == null)
    {
      localFillData = new FillData();
      paramControl.setLayoutData(localFillData);
    }
    Point localPoint = null;
    if ((paramInt1 == -1) && (paramInt2 == -1))
    {
      localPoint = localFillData.computeSize(paramControl, paramInt1, paramInt2, paramBoolean);
    }
    else
    {
      int i;
      int j;
      if ((paramControl instanceof Scrollable))
      {
        Rectangle localRectangle = ((Scrollable)paramControl).computeTrim(0, 0, 0, 0);
        i = localRectangle.width;
        j = localRectangle.height;
      }
      else
      {
        i = j = paramControl.getBorderWidth() * 2;
      }
      int k = paramInt1 == -1 ? paramInt1 : Math.max(0, paramInt1 - i);
      int m = paramInt2 == -1 ? paramInt2 : Math.max(0, paramInt2 - j);
      localPoint = localFillData.computeSize(paramControl, k, m, paramBoolean);
    }
    return localPoint;
  }

  protected boolean flushCache(Control paramControl)
  {
    Object localObject = paramControl.getLayoutData();
    if (localObject != null)
      ((FillData)localObject).flushCache();
    return true;
  }

  String getName()
  {
    String str = getClass().getName();
    int i = str.lastIndexOf('.');
    if (i == -1)
      return str;
    return str.substring(i + 1, str.length());
  }

  protected void layout(Composite paramComposite, boolean paramBoolean)
  {
    Rectangle localRectangle = paramComposite.getClientArea();
    Control[] arrayOfControl = paramComposite.getChildren();
    int i = arrayOfControl.length;
    if (i == 0)
      return;
    int j = localRectangle.width - this.marginWidth * 2;
    int k = localRectangle.height - this.marginHeight * 2;
    int m;
    int n;
    int i1;
    int i2;
    int i3;
    Control localControl;
    int i4;
    if (this.type == 256)
    {
      j -= (i - 1) * this.spacing;
      m = localRectangle.x + this.marginWidth;
      n = j % i;
      i1 = localRectangle.y + this.marginHeight;
      i2 = j / i;
      for (i3 = 0; i3 < i; i3++)
      {
        localControl = arrayOfControl[i3];
        i4 = i2;
        if (i3 == 0)
          i4 += n / 2;
        else if (i3 == i - 1)
          i4 += (n + 1) / 2;
        localControl.setBounds(m, i1, i4, k);
        m += i4 + this.spacing;
      }
    }
    else
    {
      k -= (i - 1) * this.spacing;
      m = localRectangle.x + this.marginWidth;
      n = k / i;
      i1 = localRectangle.y + this.marginHeight;
      i2 = k % i;
      for (i3 = 0; i3 < i; i3++)
      {
        localControl = arrayOfControl[i3];
        i4 = n;
        if (i3 == 0)
          i4 += i2 / 2;
        else if (i3 == i - 1)
          i4 += (i2 + 1) / 2;
        localControl.setBounds(m, i1, j, i4);
        i1 += i4 + this.spacing;
      }
    }
  }

  public String toString()
  {
    String str = getName() + " {";
    str = str + "type=" + (this.type == 512 ? "SWT.VERTICAL" : "SWT.HORIZONTAL") + " ";
    if (this.marginWidth != 0)
      str = str + "marginWidth=" + this.marginWidth + " ";
    if (this.marginHeight != 0)
      str = str + "marginHeight=" + this.marginHeight + " ";
    if (this.spacing != 0)
      str = str + "spacing=" + this.spacing + " ";
    str = str.trim();
    str = str + "}";
    return str;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.layout.FillLayout
 * JD-Core Version:    0.6.2
 */