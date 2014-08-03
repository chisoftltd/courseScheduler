package org.eclipse.swt.layout;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Scrollable;

public final class FormLayout extends Layout
{
  public int marginWidth = 0;
  public int marginHeight = 0;
  public int marginLeft = 0;
  public int marginTop = 0;
  public int marginRight = 0;
  public int marginBottom = 0;
  public int spacing = 0;

  int computeHeight(Control paramControl, FormData paramFormData, boolean paramBoolean)
  {
    FormAttachment localFormAttachment1 = paramFormData.getTopAttachment(paramControl, this.spacing, paramBoolean);
    FormAttachment localFormAttachment2 = paramFormData.getBottomAttachment(paramControl, this.spacing, paramBoolean);
    FormAttachment localFormAttachment3 = localFormAttachment2.minus(localFormAttachment1);
    if (localFormAttachment3.numerator == 0)
    {
      if (localFormAttachment2.numerator == 0)
        return localFormAttachment2.offset;
      if (localFormAttachment2.numerator == localFormAttachment2.denominator)
        return -localFormAttachment1.offset;
      if (localFormAttachment2.offset <= 0)
        return -localFormAttachment1.offset * localFormAttachment1.denominator / localFormAttachment2.numerator;
      int i = localFormAttachment2.denominator - localFormAttachment2.numerator;
      return localFormAttachment2.denominator * localFormAttachment2.offset / i;
    }
    return localFormAttachment3.solveY(paramFormData.getHeight(paramControl, paramBoolean));
  }

  protected Point computeSize(Composite paramComposite, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    Point localPoint = layout(paramComposite, false, 0, 0, paramInt1, paramInt2, paramBoolean);
    if (paramInt1 != -1)
      localPoint.x = paramInt1;
    if (paramInt2 != -1)
      localPoint.y = paramInt2;
    return localPoint;
  }

  protected boolean flushCache(Control paramControl)
  {
    Object localObject = paramControl.getLayoutData();
    if (localObject != null)
      ((FormData)localObject).flushCache();
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

  int computeWidth(Control paramControl, FormData paramFormData, boolean paramBoolean)
  {
    FormAttachment localFormAttachment1 = paramFormData.getLeftAttachment(paramControl, this.spacing, paramBoolean);
    FormAttachment localFormAttachment2 = paramFormData.getRightAttachment(paramControl, this.spacing, paramBoolean);
    FormAttachment localFormAttachment3 = localFormAttachment2.minus(localFormAttachment1);
    if (localFormAttachment3.numerator == 0)
    {
      if (localFormAttachment2.numerator == 0)
        return localFormAttachment2.offset;
      if (localFormAttachment2.numerator == localFormAttachment2.denominator)
        return -localFormAttachment1.offset;
      if (localFormAttachment2.offset <= 0)
        return -localFormAttachment1.offset * localFormAttachment1.denominator / localFormAttachment1.numerator;
      int i = localFormAttachment2.denominator - localFormAttachment2.numerator;
      return localFormAttachment2.denominator * localFormAttachment2.offset / i;
    }
    return localFormAttachment3.solveY(paramFormData.getWidth(paramControl, paramBoolean));
  }

  protected void layout(Composite paramComposite, boolean paramBoolean)
  {
    Rectangle localRectangle = paramComposite.getClientArea();
    int i = localRectangle.x + this.marginLeft + this.marginWidth;
    int j = localRectangle.y + this.marginTop + this.marginHeight;
    int k = Math.max(0, localRectangle.width - this.marginLeft - 2 * this.marginWidth - this.marginRight);
    int m = Math.max(0, localRectangle.height - this.marginTop - 2 * this.marginHeight - this.marginBottom);
    layout(paramComposite, true, i, j, k, m, paramBoolean);
  }

  Point layout(Composite paramComposite, boolean paramBoolean1, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean2)
  {
    Control[] arrayOfControl = paramComposite.getChildren();
    for (int i = 0; i < arrayOfControl.length; i++)
    {
      localObject = arrayOfControl[i];
      FormData localFormData1 = (FormData)((Control)localObject).getLayoutData();
      if (localFormData1 == null)
        ((Control)localObject).setLayoutData(localFormData1 = new FormData());
      if (paramBoolean2)
        localFormData1.flushCache();
      localFormData1.cacheLeft = (localFormData1.cacheRight = localFormData1.cacheTop = localFormData1.cacheBottom = null);
    }
    boolean[] arrayOfBoolean = (boolean[])null;
    Object localObject = (Rectangle[])null;
    int j = 0;
    int k = 0;
    Control localControl;
    FormData localFormData2;
    for (int m = 0; m < arrayOfControl.length; m++)
    {
      localControl = arrayOfControl[m];
      localFormData2 = (FormData)localControl.getLayoutData();
      if (paramInt3 != -1)
      {
        localFormData2.needed = false;
        FormAttachment localFormAttachment1 = localFormData2.getLeftAttachment(localControl, this.spacing, paramBoolean2);
        FormAttachment localFormAttachment2 = localFormData2.getRightAttachment(localControl, this.spacing, paramBoolean2);
        int i2 = localFormAttachment1.solveX(paramInt3);
        int i3 = localFormAttachment2.solveX(paramInt3);
        if ((localFormData2.height == -1) && (!localFormData2.needed))
        {
          int i4 = 0;
          if ((localControl instanceof Scrollable))
          {
            Rectangle localRectangle = ((Scrollable)localControl).computeTrim(0, 0, 0, 0);
            i4 = localRectangle.width;
          }
          else
          {
            i4 = localControl.getBorderWidth() * 2;
          }
          localFormData2.cacheWidth = (localFormData2.cacheHeight = -1);
          int i5 = Math.max(0, i3 - i2 - i4);
          localFormData2.computeSize(localControl, i5, localFormData2.height, paramBoolean2);
          if (arrayOfBoolean == null)
            arrayOfBoolean = new boolean[arrayOfControl.length];
          arrayOfBoolean[m] = true;
        }
        j = Math.max(i3, j);
        if (paramBoolean1)
        {
          if (localObject == null)
            localObject = new Rectangle[arrayOfControl.length];
          localObject[m] = new Rectangle(0, 0, 0, 0);
          localObject[m].x = (paramInt1 + i2);
          localObject[m].width = (i3 - i2);
        }
      }
      else
      {
        j = Math.max(computeWidth(localControl, localFormData2, paramBoolean2), j);
      }
    }
    for (m = 0; m < arrayOfControl.length; m++)
    {
      localControl = arrayOfControl[m];
      localFormData2 = (FormData)localControl.getLayoutData();
      if (paramInt4 != -1)
      {
        int n = localFormData2.getTopAttachment(localControl, this.spacing, paramBoolean2).solveX(paramInt4);
        int i1 = localFormData2.getBottomAttachment(localControl, this.spacing, paramBoolean2).solveX(paramInt4);
        k = Math.max(i1, k);
        if (paramBoolean1)
        {
          localObject[m].y = (paramInt2 + n);
          localObject[m].height = (i1 - n);
        }
      }
      else
      {
        k = Math.max(computeHeight(localControl, localFormData2, paramBoolean2), k);
      }
    }
    for (m = 0; m < arrayOfControl.length; m++)
    {
      localControl = arrayOfControl[m];
      localFormData2 = (FormData)localControl.getLayoutData();
      if ((arrayOfBoolean != null) && (arrayOfBoolean[m] != 0))
        localFormData2.cacheWidth = (localFormData2.cacheHeight = -1);
      localFormData2.cacheLeft = (localFormData2.cacheRight = localFormData2.cacheTop = localFormData2.cacheBottom = null);
    }
    if (paramBoolean1)
      for (m = 0; m < arrayOfControl.length; m++)
        arrayOfControl[m].setBounds(localObject[m]);
    j += this.marginLeft + this.marginWidth * 2 + this.marginRight;
    k += this.marginTop + this.marginHeight * 2 + this.marginBottom;
    return new Point(j, k);
  }

  public String toString()
  {
    String str = getName() + " {";
    if (this.marginWidth != 0)
      str = str + "marginWidth=" + this.marginWidth + " ";
    if (this.marginHeight != 0)
      str = str + "marginHeight=" + this.marginHeight + " ";
    if (this.marginLeft != 0)
      str = str + "marginLeft=" + this.marginLeft + " ";
    if (this.marginRight != 0)
      str = str + "marginRight=" + this.marginRight + " ";
    if (this.marginTop != 0)
      str = str + "marginTop=" + this.marginTop + " ";
    if (this.marginBottom != 0)
      str = str + "marginBottom=" + this.marginBottom + " ";
    if (this.spacing != 0)
      str = str + "spacing=" + this.spacing + " ";
    str = str.trim();
    str = str + "}";
    return str;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.layout.FormLayout
 * JD-Core Version:    0.6.2
 */