package org.eclipse.swt.internal.theme;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;

public class TabItemDrawData extends DrawData
{
  public TabFolderDrawData parent;
  public int position;
  static final int TABITEM_INSET = 2;
  static final int TABITEM_INSET2 = 6;

  public TabItemDrawData()
  {
    this.state = new int[1];
  }

  Rectangle computeTrim(Theme paramTheme, GC paramGC)
  {
    if ((OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()))
    {
      int i = OS.OpenThemeData(0, getClassId());
      int j = this.clientArea.x;
      int k = this.clientArea.y;
      int m = this.clientArea.width;
      int n = this.clientArea.height;
      if ((this.style & 0x4000) != 0)
      {
        j -= 2;
        m += 2;
      }
      k -= 2;
      n += 2;
      RECT localRECT1 = new RECT();
      localRECT1.left = j;
      localRECT1.right = (j + m);
      localRECT1.top = k;
      localRECT1.bottom = (k + n);
      RECT localRECT2 = new RECT();
      int[] arrayOfInt = getPartId(0);
      OS.GetThemeBackgroundExtent(i, paramGC.handle, arrayOfInt[0], arrayOfInt[1], localRECT1, localRECT2);
      localRECT2.left -= 6;
      localRECT2.top -= 6;
      localRECT2.right += 6;
      OS.CloseThemeData(i);
      return new Rectangle(localRECT2.left, localRECT2.top, localRECT2.right - localRECT2.left, localRECT2.bottom - localRECT2.top);
    }
    return new Rectangle(0, 0, 0, 0);
  }

  void draw(Theme paramTheme, GC paramGC, Rectangle paramRectangle)
  {
    if ((OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()))
    {
      int i = this.state[0];
      int j = OS.OpenThemeData(0, getClassId());
      int k = paramRectangle.x;
      int m = paramRectangle.y;
      int n = paramRectangle.width;
      int i1 = paramRectangle.height;
      if ((this.position & 0x4000) != 0)
      {
        k += 2;
        n -= 2;
      }
      m += 2;
      i1 -= 2;
      if ((i & 0x2) != 0)
      {
        k -= 2;
        m -= 2;
        n += 4;
        i1 += 4;
      }
      RECT localRECT1 = new RECT();
      localRECT1.left = k;
      localRECT1.right = (k + n);
      localRECT1.top = m;
      localRECT1.bottom = (m + i1);
      int[] arrayOfInt = getPartId(0);
      OS.DrawThemeBackground(j, paramGC.handle, arrayOfInt[0], arrayOfInt[1], localRECT1, null);
      OS.CloseThemeData(j);
      Rectangle localRectangle = this.clientArea;
      if (localRectangle != null)
      {
        RECT localRECT2 = new RECT();
        OS.GetThemeBackgroundContentRect(j, paramGC.handle, arrayOfInt[0], arrayOfInt[1], localRECT1, localRECT2);
        localRectangle.x = localRECT2.left;
        localRectangle.y = localRECT2.top;
        localRectangle.width = (localRECT2.right - localRECT2.left);
        localRectangle.height = (localRECT2.bottom - localRECT2.top);
      }
    }
  }

  char[] getClassId()
  {
    return TAB;
  }

  int[] getPartId(int paramInt)
  {
    int i = this.state[paramInt];
    int j = 1;
    int k = 1;
    if (((this.style & 0x4000) != 0) && ((this.style & 0x20000) != 0))
      j = 2;
    else if ((this.style & 0x4000) != 0)
      j = 2;
    if ((i & 0x40) != 0)
      k = 2;
    if ((i & 0x4) != 0)
      k = 5;
    if ((i & 0x2) != 0)
      k = 3;
    if ((i & 0x20) != 0)
      k = 4;
    return new int[] { j, k };
  }

  int hit(Theme paramTheme, Point paramPoint, Rectangle paramRectangle)
  {
    if (!paramRectangle.contains(paramPoint))
      return -1;
    int i = this.style;
    int j = paramRectangle.x;
    int k = paramRectangle.y;
    int m = paramRectangle.width;
    int n = paramRectangle.height;
    if ((i & 0x4000) != 0)
    {
      j += 2;
      m -= 2;
    }
    k += 2;
    n -= 2;
    Rectangle localRectangle = new Rectangle(j, k, m, n);
    if (!localRectangle.contains(paramPoint))
      return -1;
    return 0;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.theme.TabItemDrawData
 * JD-Core Version:    0.6.2
 */