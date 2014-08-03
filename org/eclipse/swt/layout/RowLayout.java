package org.eclipse.swt.layout;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;

public final class RowLayout extends Layout
{
  public int type = 256;
  public int marginWidth = 0;
  public int marginHeight = 0;
  public int spacing = 3;
  public boolean wrap = true;
  public boolean pack = true;
  public boolean fill = false;
  public boolean center = false;
  public boolean justify = false;
  public int marginLeft = 3;
  public int marginTop = 3;
  public int marginRight = 3;
  public int marginBottom = 3;

  public RowLayout()
  {
  }

  public RowLayout(int paramInt)
  {
    this.type = paramInt;
  }

  protected Point computeSize(Composite paramComposite, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    Point localPoint;
    if (this.type == 256)
      localPoint = layoutHorizontal(paramComposite, false, (paramInt1 != -1) && (this.wrap), paramInt1, paramBoolean);
    else
      localPoint = layoutVertical(paramComposite, false, (paramInt2 != -1) && (this.wrap), paramInt2, paramBoolean);
    if (paramInt1 != -1)
      localPoint.x = paramInt1;
    if (paramInt2 != -1)
      localPoint.y = paramInt2;
    return localPoint;
  }

  Point computeSize(Control paramControl, boolean paramBoolean)
  {
    int i = -1;
    int j = -1;
    RowData localRowData = (RowData)paramControl.getLayoutData();
    if (localRowData != null)
    {
      i = localRowData.width;
      j = localRowData.height;
    }
    return paramControl.computeSize(i, j, paramBoolean);
  }

  protected boolean flushCache(Control paramControl)
  {
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
    if (this.type == 256)
      layoutHorizontal(paramComposite, true, this.wrap, localRectangle.width, paramBoolean);
    else
      layoutVertical(paramComposite, true, this.wrap, localRectangle.height, paramBoolean);
  }

  Point layoutHorizontal(Composite paramComposite, boolean paramBoolean1, boolean paramBoolean2, int paramInt, boolean paramBoolean3)
  {
    Control[] arrayOfControl = paramComposite.getChildren();
    int i = 0;
    for (int j = 0; j < arrayOfControl.length; j++)
    {
      Control localControl1 = arrayOfControl[j];
      RowData localRowData = (RowData)localControl1.getLayoutData();
      if ((localRowData == null) || (!localRowData.exclude))
        arrayOfControl[(i++)] = arrayOfControl[j];
    }
    if (i == 0)
      return new Point(this.marginLeft + this.marginWidth * 2 + this.marginRight, this.marginTop + this.marginHeight * 2 + this.marginBottom);
    j = 0;
    int k = 0;
    int m = 0;
    if (!this.pack)
    {
      for (n = 0; n < i; n++)
      {
        Control localControl2 = arrayOfControl[n];
        localObject = computeSize(localControl2, paramBoolean3);
        j = Math.max(j, ((Point)localObject).x);
        k = Math.max(k, ((Point)localObject).y);
      }
      m = k;
    }
    int n = 0;
    int i1 = 0;
    if (paramBoolean1)
    {
      localObject = paramComposite.getClientArea();
      n = ((Rectangle)localObject).x;
      i1 = ((Rectangle)localObject).y;
    }
    Object localObject = (int[])null;
    int i2 = 0;
    Rectangle[] arrayOfRectangle = (Rectangle[])null;
    if ((paramBoolean1) && ((this.justify) || (this.fill) || (this.center)))
    {
      arrayOfRectangle = new Rectangle[i];
      localObject = new int[i];
    }
    int i3 = 0;
    int i4 = this.marginLeft + this.marginWidth;
    int i5 = this.marginTop + this.marginHeight;
    int i8;
    int i9;
    for (int i6 = 0; i6 < i; i6++)
    {
      Control localControl3 = arrayOfControl[i6];
      if (this.pack)
      {
        Point localPoint = computeSize(localControl3, paramBoolean3);
        j = localPoint.x;
        k = localPoint.y;
      }
      if ((paramBoolean2) && (i6 != 0) && (i4 + j > paramInt))
      {
        i2 = 1;
        if ((paramBoolean1) && ((this.justify) || (this.fill) || (this.center)))
          localObject[(i6 - 1)] = m;
        i4 = this.marginLeft + this.marginWidth;
        i5 += this.spacing + m;
        if (this.pack)
          m = 0;
      }
      if ((this.pack) || (this.fill) || (this.center))
        m = Math.max(m, k);
      if (paramBoolean1)
      {
        i8 = i4 + n;
        i9 = i5 + i1;
        if ((this.justify) || (this.fill) || (this.center))
          arrayOfRectangle[i6] = new Rectangle(i8, i9, j, k);
        else
          localControl3.setBounds(i8, i9, j, k);
      }
      i4 += this.spacing + j;
      i3 = Math.max(i3, i4);
    }
    i3 = Math.max(n + this.marginLeft + this.marginWidth, i3 - this.spacing);
    if (i2 == 0)
      i3 += this.marginRight + this.marginWidth;
    if ((paramBoolean1) && ((this.justify) || (this.fill) || (this.center)))
    {
      i6 = 0;
      int i7 = 0;
      if (i2 == 0)
      {
        i6 = Math.max(0, (paramInt - i3) / (i + 1));
        i7 = Math.max(0, (paramInt - i3) % (i + 1) / 2);
      }
      else if ((this.fill) || (this.justify) || (this.center))
      {
        i8 = 0;
        if (i > 0)
          localObject[(i - 1)] = m;
        for (i9 = 0; i9 < i; i9++)
          if (localObject[i9] != 0)
          {
            int i10 = i9 - i8 + 1;
            if (this.justify)
            {
              i11 = 0;
              for (int i12 = i8; i12 <= i9; i12++)
                i11 += arrayOfRectangle[i12].width + this.spacing;
              i6 = Math.max(0, (paramInt - i11) / (i10 + 1));
              i7 = Math.max(0, (paramInt - i11) % (i10 + 1) / 2);
            }
            for (int i11 = i8; i11 <= i9; i11++)
            {
              if (this.justify)
                arrayOfRectangle[i11].x += i6 * (i11 - i8 + 1) + i7;
              if (this.fill)
                arrayOfRectangle[i11].height = localObject[i9];
              else if (this.center)
                arrayOfRectangle[i11].y += Math.max(0, (localObject[i9] - arrayOfRectangle[i11].height) / 2);
            }
            i8 = i9 + 1;
          }
      }
      for (i8 = 0; i8 < i; i8++)
      {
        if (i2 == 0)
        {
          if (this.justify)
            arrayOfRectangle[i8].x += i6 * (i8 + 1) + i7;
          if (this.fill)
            arrayOfRectangle[i8].height = m;
          else if (this.center)
            arrayOfRectangle[i8].y += Math.max(0, (m - arrayOfRectangle[i8].height) / 2);
        }
        arrayOfControl[i8].setBounds(arrayOfRectangle[i8]);
      }
    }
    return new Point(i3, i5 + m + this.marginBottom + this.marginHeight);
  }

  Point layoutVertical(Composite paramComposite, boolean paramBoolean1, boolean paramBoolean2, int paramInt, boolean paramBoolean3)
  {
    Control[] arrayOfControl = paramComposite.getChildren();
    int i = 0;
    for (int j = 0; j < arrayOfControl.length; j++)
    {
      Control localControl1 = arrayOfControl[j];
      RowData localRowData = (RowData)localControl1.getLayoutData();
      if ((localRowData == null) || (!localRowData.exclude))
        arrayOfControl[(i++)] = arrayOfControl[j];
    }
    if (i == 0)
      return new Point(this.marginLeft + this.marginWidth * 2 + this.marginRight, this.marginTop + this.marginHeight * 2 + this.marginBottom);
    j = 0;
    int k = 0;
    int m = 0;
    if (!this.pack)
    {
      for (n = 0; n < i; n++)
      {
        Control localControl2 = arrayOfControl[n];
        localObject = computeSize(localControl2, paramBoolean3);
        j = Math.max(j, ((Point)localObject).x);
        k = Math.max(k, ((Point)localObject).y);
      }
      m = j;
    }
    int n = 0;
    int i1 = 0;
    if (paramBoolean1)
    {
      localObject = paramComposite.getClientArea();
      n = ((Rectangle)localObject).x;
      i1 = ((Rectangle)localObject).y;
    }
    Object localObject = (int[])null;
    int i2 = 0;
    Rectangle[] arrayOfRectangle = (Rectangle[])null;
    if ((paramBoolean1) && ((this.justify) || (this.fill) || (this.center)))
    {
      arrayOfRectangle = new Rectangle[i];
      localObject = new int[i];
    }
    int i3 = 0;
    int i4 = this.marginLeft + this.marginWidth;
    int i5 = this.marginTop + this.marginHeight;
    int i8;
    int i9;
    for (int i6 = 0; i6 < i; i6++)
    {
      Control localControl3 = arrayOfControl[i6];
      if (this.pack)
      {
        Point localPoint = computeSize(localControl3, paramBoolean3);
        j = localPoint.x;
        k = localPoint.y;
      }
      if ((paramBoolean2) && (i6 != 0) && (i5 + k > paramInt))
      {
        i2 = 1;
        if ((paramBoolean1) && ((this.justify) || (this.fill) || (this.center)))
          localObject[(i6 - 1)] = m;
        i4 += this.spacing + m;
        i5 = this.marginTop + this.marginHeight;
        if (this.pack)
          m = 0;
      }
      if ((this.pack) || (this.fill) || (this.center))
        m = Math.max(m, j);
      if (paramBoolean1)
      {
        i8 = i4 + n;
        i9 = i5 + i1;
        if ((this.justify) || (this.fill) || (this.center))
          arrayOfRectangle[i6] = new Rectangle(i8, i9, j, k);
        else
          localControl3.setBounds(i8, i9, j, k);
      }
      i5 += this.spacing + k;
      i3 = Math.max(i3, i5);
    }
    i3 = Math.max(i1 + this.marginTop + this.marginHeight, i3 - this.spacing);
    if (i2 == 0)
      i3 += this.marginBottom + this.marginHeight;
    if ((paramBoolean1) && ((this.justify) || (this.fill) || (this.center)))
    {
      i6 = 0;
      int i7 = 0;
      if (i2 == 0)
      {
        i6 = Math.max(0, (paramInt - i3) / (i + 1));
        i7 = Math.max(0, (paramInt - i3) % (i + 1) / 2);
      }
      else if ((this.fill) || (this.justify) || (this.center))
      {
        i8 = 0;
        if (i > 0)
          localObject[(i - 1)] = m;
        for (i9 = 0; i9 < i; i9++)
          if (localObject[i9] != 0)
          {
            int i10 = i9 - i8 + 1;
            if (this.justify)
            {
              i11 = 0;
              for (int i12 = i8; i12 <= i9; i12++)
                i11 += arrayOfRectangle[i12].height + this.spacing;
              i6 = Math.max(0, (paramInt - i11) / (i10 + 1));
              i7 = Math.max(0, (paramInt - i11) % (i10 + 1) / 2);
            }
            for (int i11 = i8; i11 <= i9; i11++)
            {
              if (this.justify)
                arrayOfRectangle[i11].y += i6 * (i11 - i8 + 1) + i7;
              if (this.fill)
                arrayOfRectangle[i11].width = localObject[i9];
              else if (this.center)
                arrayOfRectangle[i11].x += Math.max(0, (localObject[i9] - arrayOfRectangle[i11].width) / 2);
            }
            i8 = i9 + 1;
          }
      }
      for (i8 = 0; i8 < i; i8++)
      {
        if (i2 == 0)
        {
          if (this.justify)
            arrayOfRectangle[i8].y += i6 * (i8 + 1) + i7;
          if (this.fill)
            arrayOfRectangle[i8].width = m;
          else if (this.center)
            arrayOfRectangle[i8].x += Math.max(0, (m - arrayOfRectangle[i8].width) / 2);
        }
        arrayOfControl[i8].setBounds(arrayOfRectangle[i8]);
      }
    }
    return new Point(i4 + m + this.marginRight + this.marginWidth, i3);
  }

  public String toString()
  {
    String str = getName() + " {";
    str = str + "type=" + (this.type != 256 ? "SWT.VERTICAL" : "SWT.HORIZONTAL") + " ";
    if (this.marginWidth != 0)
      str = str + "marginWidth=" + this.marginWidth + " ";
    if (this.marginHeight != 0)
      str = str + "marginHeight=" + this.marginHeight + " ";
    if (this.marginLeft != 0)
      str = str + "marginLeft=" + this.marginLeft + " ";
    if (this.marginTop != 0)
      str = str + "marginTop=" + this.marginTop + " ";
    if (this.marginRight != 0)
      str = str + "marginRight=" + this.marginRight + " ";
    if (this.marginBottom != 0)
      str = str + "marginBottom=" + this.marginBottom + " ";
    if (this.spacing != 0)
      str = str + "spacing=" + this.spacing + " ";
    str = str + "wrap=" + this.wrap + " ";
    str = str + "pack=" + this.pack + " ";
    str = str + "fill=" + this.fill + " ";
    str = str + "justify=" + this.justify + " ";
    str = str.trim();
    str = str + "}";
    return str;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.layout.RowLayout
 * JD-Core Version:    0.6.2
 */