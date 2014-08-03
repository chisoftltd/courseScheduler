package org.eclipse.swt.custom;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;

class CTabFolderLayout extends Layout
{
  protected Point computeSize(Composite paramComposite, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    CTabFolder localCTabFolder = (CTabFolder)paramComposite;
    CTabItem[] arrayOfCTabItem = localCTabFolder.items;
    CTabFolderRenderer localCTabFolderRenderer = localCTabFolder.renderer;
    int i = 0;
    int j = localCTabFolder.selectedIndex;
    if (j == -1)
      j = 0;
    GC localGC = new GC(localCTabFolder);
    for (int k = 0; k < arrayOfCTabItem.length; k++)
      if (localCTabFolder.single)
      {
        i = Math.max(i, localCTabFolderRenderer.computeSize(k, 2, localGC, -1, -1).x);
      }
      else
      {
        localControl1 = 0;
        if (k == j)
          localControl1 |= 2;
        i += localCTabFolderRenderer.computeSize(k, localControl1, localGC, -1, -1).x;
      }
    i += 3;
    if (localCTabFolder.showMax)
      i += localCTabFolderRenderer.computeSize(-5, 0, localGC, -1, -1).x;
    if (localCTabFolder.showMin)
      i += localCTabFolderRenderer.computeSize(-6, 0, localGC, -1, -1).x;
    if (localCTabFolder.single)
      i += localCTabFolderRenderer.computeSize(-7, 0, localGC, -1, -1).x;
    if (localCTabFolder.topRight != null)
    {
      Point localPoint1 = localCTabFolder.topRight.computeSize(-1, localCTabFolder.tabHeight, paramBoolean);
      i += 3 + localPoint1.x;
    }
    localGC.dispose();
    int m = 0;
    Control localControl1 = 0;
    for (int n = 0; n < arrayOfCTabItem.length; n++)
    {
      localControl2 = arrayOfCTabItem[n].getControl();
      if ((localControl2 != null) && (!localControl2.isDisposed()))
      {
        Point localPoint2 = localControl2.computeSize(paramInt1, paramInt2, paramBoolean);
        m = Math.max(m, localPoint2.x);
        localControl1 = Math.max(localControl1, localPoint2.y);
      }
    }
    n = Math.max(i, m);
    Control localControl2 = localCTabFolder.minimized ? 0 : localControl1;
    if (n == 0)
      n = 64;
    int i1;
    if (localControl2 == 0)
      i1 = 64;
    if (paramInt1 != -1)
      n = paramInt1;
    if (paramInt2 != -1)
      i1 = paramInt2;
    return new Point(n, i1);
  }

  protected boolean flushCache(Control paramControl)
  {
    return true;
  }

  protected void layout(Composite paramComposite, boolean paramBoolean)
  {
    CTabFolder localCTabFolder = (CTabFolder)paramComposite;
    if (localCTabFolder.selectedIndex != -1)
    {
      Control localControl = localCTabFolder.items[localCTabFolder.selectedIndex].getControl();
      if ((localControl != null) && (!localControl.isDisposed()))
        localControl.setBounds(localCTabFolder.getClientArea());
    }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.custom.CTabFolderLayout
 * JD-Core Version:    0.6.2
 */