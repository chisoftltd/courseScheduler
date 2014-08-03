package org.eclipse.swt.custom;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;

public class StackLayout extends Layout
{
  public int marginWidth = 0;
  public int marginHeight = 0;
  public Control topControl;

  protected Point computeSize(Composite paramComposite, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    Control[] arrayOfControl = paramComposite.getChildren();
    int i = 0;
    int j = 0;
    for (int k = 0; k < arrayOfControl.length; k++)
    {
      Point localPoint = arrayOfControl[k].computeSize(paramInt1, paramInt2, paramBoolean);
      i = Math.max(localPoint.x, i);
      j = Math.max(localPoint.y, j);
    }
    k = i + 2 * this.marginWidth;
    int m = j + 2 * this.marginHeight;
    if (paramInt1 != -1)
      k = paramInt1;
    if (paramInt2 != -1)
      m = paramInt2;
    return new Point(k, m);
  }

  protected boolean flushCache(Control paramControl)
  {
    return true;
  }

  protected void layout(Composite paramComposite, boolean paramBoolean)
  {
    Control[] arrayOfControl = paramComposite.getChildren();
    Rectangle localRectangle = paramComposite.getClientArea();
    localRectangle.x += this.marginWidth;
    localRectangle.y += this.marginHeight;
    localRectangle.width -= 2 * this.marginWidth;
    localRectangle.height -= 2 * this.marginHeight;
    for (int i = 0; i < arrayOfControl.length; i++)
    {
      arrayOfControl[i].setBounds(localRectangle);
      arrayOfControl[i].setVisible(arrayOfControl[i] == this.topControl);
    }
  }

  String getName()
  {
    String str = getClass().getName();
    int i = str.lastIndexOf('.');
    if (i == -1)
      return str;
    return str.substring(i + 1, str.length());
  }

  public String toString()
  {
    String str = getName() + " {";
    if (this.marginWidth != 0)
      str = str + "marginWidth=" + this.marginWidth + " ";
    if (this.marginHeight != 0)
      str = str + "marginHeight=" + this.marginHeight + " ";
    if (this.topControl != null)
      str = str + "topControl=" + this.topControl + " ";
    str = str.trim();
    str = str + "}";
    return str;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.custom.StackLayout
 * JD-Core Version:    0.6.2
 */