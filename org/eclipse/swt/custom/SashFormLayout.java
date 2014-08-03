package org.eclipse.swt.custom;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Sash;

class SashFormLayout extends Layout
{
  protected Point computeSize(Composite paramComposite, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    SashForm localSashForm = (SashForm)paramComposite;
    Control[] arrayOfControl = localSashForm.getControls(true);
    int i = 0;
    int j = 0;
    if (arrayOfControl.length == 0)
    {
      if (paramInt1 != -1)
        i = paramInt1;
      if (paramInt2 != -1)
        j = paramInt2;
      return new Point(i, j);
    }
    int k = localSashForm.getOrientation() == 512 ? 1 : 0;
    int m = 0;
    int n = 0;
    for (int i1 = 0; i1 < arrayOfControl.length; i1++)
    {
      Point localPoint;
      if (k != 0)
      {
        localPoint = arrayOfControl[i1].computeSize(paramInt1, -1, paramBoolean);
        if (localPoint.y > n)
        {
          m = i1;
          n = localPoint.y;
        }
        i = Math.max(i, localPoint.x);
      }
      else
      {
        localPoint = arrayOfControl[i1].computeSize(-1, paramInt2, paramBoolean);
        if (localPoint.x > n)
        {
          m = i1;
          n = localPoint.x;
        }
        j = Math.max(j, localPoint.y);
      }
    }
    long[] arrayOfLong = new long[arrayOfControl.length];
    long l = 0L;
    for (int i2 = 0; i2 < arrayOfControl.length; i2++)
    {
      Object localObject = arrayOfControl[i2].getLayoutData();
      if ((localObject != null) && ((localObject instanceof SashFormData)))
      {
        arrayOfLong[i2] = ((SashFormData)localObject).weight;
      }
      else
      {
        localObject = new SashFormData();
        arrayOfControl[i2].setLayoutData(localObject);
        ((SashFormData)localObject).weight = (arrayOfLong[i2] = 13108L);
      }
      l += arrayOfLong[i2];
    }
    if (arrayOfLong[m] > 0L)
    {
      i2 = localSashForm.sashes.length > 0 ? localSashForm.SASH_WIDTH + localSashForm.sashes[0].getBorderWidth() * 2 : localSashForm.SASH_WIDTH;
      if (k != 0)
        j += (int)(l * n / arrayOfLong[m]) + (arrayOfControl.length - 1) * i2;
      else
        i += (int)(l * n / arrayOfLong[m]) + (arrayOfControl.length - 1) * i2;
    }
    i += localSashForm.getBorderWidth() * 2;
    j += localSashForm.getBorderWidth() * 2;
    if (paramInt1 != -1)
      i = paramInt1;
    if (paramInt2 != -1)
      j = paramInt2;
    return new Point(i, j);
  }

  protected boolean flushCache(Control paramControl)
  {
    return true;
  }

  protected void layout(Composite paramComposite, boolean paramBoolean)
  {
    SashForm localSashForm = (SashForm)paramComposite;
    Rectangle localRectangle = localSashForm.getClientArea();
    if ((localRectangle.width <= 1) || (localRectangle.height <= 1))
      return;
    Control[] arrayOfControl1 = localSashForm.getControls(true);
    if ((localSashForm.controls.length == 0) && (arrayOfControl1.length == 0))
      return;
    localSashForm.controls = arrayOfControl1;
    Control[] arrayOfControl2 = localSashForm.controls;
    if ((localSashForm.maxControl != null) && (!localSashForm.maxControl.isDisposed()))
    {
      for (int i = 0; i < arrayOfControl2.length; i++)
        if (arrayOfControl2[i] != localSashForm.maxControl)
          arrayOfControl2[i].setBounds(-200, -200, 0, 0);
        else
          arrayOfControl2[i].setBounds(localRectangle);
      return;
    }
    int k;
    if (localSashForm.sashes.length < arrayOfControl2.length - 1)
    {
      Sash[] arrayOfSash1 = new Sash[arrayOfControl2.length - 1];
      System.arraycopy(localSashForm.sashes, 0, arrayOfSash1, 0, localSashForm.sashes.length);
      for (k = localSashForm.sashes.length; k < arrayOfSash1.length; k++)
        arrayOfSash1[k] = localSashForm.createSash();
      localSashForm.sashes = arrayOfSash1;
    }
    if (localSashForm.sashes.length > arrayOfControl2.length - 1)
      if (arrayOfControl2.length == 0)
      {
        for (int j = 0; j < localSashForm.sashes.length; j++)
          localSashForm.sashes[j].dispose();
        localSashForm.sashes = new Sash[0];
      }
      else
      {
        arrayOfSash2 = new Sash[arrayOfControl2.length - 1];
        System.arraycopy(localSashForm.sashes, 0, arrayOfSash2, 0, arrayOfSash2.length);
        for (k = arrayOfControl2.length - 1; k < localSashForm.sashes.length; k++)
          localSashForm.sashes[k].dispose();
        localSashForm.sashes = arrayOfSash2;
      }
    if (arrayOfControl2.length == 0)
      return;
    Sash[] arrayOfSash2 = localSashForm.sashes;
    long[] arrayOfLong = new long[arrayOfControl2.length];
    long l = 0L;
    for (int m = 0; m < arrayOfControl2.length; m++)
    {
      Object localObject = arrayOfControl2[m].getLayoutData();
      if ((localObject != null) && ((localObject instanceof SashFormData)))
      {
        arrayOfLong[m] = ((SashFormData)localObject).weight;
      }
      else
      {
        localObject = new SashFormData();
        arrayOfControl2[m].setLayoutData(localObject);
        ((SashFormData)localObject).weight = (arrayOfLong[m] = 13108L);
      }
      l += arrayOfLong[m];
    }
    m = arrayOfSash2.length > 0 ? localSashForm.SASH_WIDTH + arrayOfSash2[0].getBorderWidth() * 2 : localSashForm.SASH_WIDTH;
    int n;
    int i1;
    int i2;
    if (localSashForm.getOrientation() == 256)
    {
      n = (int)(arrayOfLong[0] * (localRectangle.width - arrayOfSash2.length * m) / l);
      i1 = localRectangle.x;
      arrayOfControl2[0].setBounds(i1, localRectangle.y, n, localRectangle.height);
      i1 += n;
      for (i2 = 1; i2 < arrayOfControl2.length - 1; i2++)
      {
        arrayOfSash2[(i2 - 1)].setBounds(i1, localRectangle.y, m, localRectangle.height);
        i1 += m;
        n = (int)(arrayOfLong[i2] * (localRectangle.width - arrayOfSash2.length * m) / l);
        arrayOfControl2[i2].setBounds(i1, localRectangle.y, n, localRectangle.height);
        i1 += n;
      }
      if (arrayOfControl2.length > 1)
      {
        arrayOfSash2[(arrayOfSash2.length - 1)].setBounds(i1, localRectangle.y, m, localRectangle.height);
        i1 += m;
        n = localRectangle.width - i1;
        arrayOfControl2[(arrayOfControl2.length - 1)].setBounds(i1, localRectangle.y, n, localRectangle.height);
      }
    }
    else
    {
      n = (int)(arrayOfLong[0] * (localRectangle.height - arrayOfSash2.length * m) / l);
      i1 = localRectangle.y;
      arrayOfControl2[0].setBounds(localRectangle.x, i1, localRectangle.width, n);
      i1 += n;
      for (i2 = 1; i2 < arrayOfControl2.length - 1; i2++)
      {
        arrayOfSash2[(i2 - 1)].setBounds(localRectangle.x, i1, localRectangle.width, m);
        i1 += m;
        n = (int)(arrayOfLong[i2] * (localRectangle.height - arrayOfSash2.length * m) / l);
        arrayOfControl2[i2].setBounds(localRectangle.x, i1, localRectangle.width, n);
        i1 += n;
      }
      if (arrayOfControl2.length > 1)
      {
        arrayOfSash2[(arrayOfSash2.length - 1)].setBounds(localRectangle.x, i1, localRectangle.width, m);
        i1 += m;
        n = localRectangle.height - i1;
        arrayOfControl2[(arrayOfControl2.length - 1)].setBounds(localRectangle.x, i1, localRectangle.width, n);
      }
    }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.custom.SashFormLayout
 * JD-Core Version:    0.6.2
 */