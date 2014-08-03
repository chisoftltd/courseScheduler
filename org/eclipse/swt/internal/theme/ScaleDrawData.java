package org.eclipse.swt.internal.theme;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.SIZE;

public class ScaleDrawData extends RangeDrawData
{
  public int increment;
  public int pageIncrement;
  static final int TICS_MARGIN = 10;

  public ScaleDrawData()
  {
    this.state = new int[4];
  }

  void draw(Theme paramTheme, GC paramGC, Rectangle paramRectangle)
  {
    if ((OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()))
    {
      int i = this.style;
      int j = this.minimum;
      int k = this.maximum;
      int m = this.selection;
      int n = this.pageIncrement;
      int i1 = OS.OpenThemeData(0, getClassId());
      RECT localRECT = new RECT();
      localRECT.left = paramRectangle.x;
      localRECT.right = (localRECT.left + paramRectangle.width);
      localRECT.top = paramRectangle.y;
      localRECT.bottom = (localRECT.top + paramRectangle.height);
      SIZE localSIZE = new SIZE();
      if ((i & 0x200) != 0)
      {
        OS.GetThemePartSize(i1, paramGC.handle, 2, 0, null, 1, localSIZE);
        int i2 = localSIZE.cx - 1;
        OS.GetThemePartSize(i1, paramGC.handle, 6, 0, null, 1, localSIZE);
        int i3 = localSIZE.cx;
        int i4 = localSIZE.cy;
        OS.GetThemePartSize(i1, paramGC.handle, 9, 0, localRECT, 1, localSIZE);
        int i5 = localSIZE.cx;
        int i6 = (i3 - i2) / 2;
        int i7 = i6;
        i6 += 10;
        localRECT.left += i6;
        localRECT.top += i7;
        localRECT.right = (localRECT.left + i2);
        localRECT.bottom -= i7;
        int i8 = localRECT.bottom - localRECT.top;
        OS.DrawThemeBackground(i1, paramGC.handle, 2, 0, localRECT, null);
        localRECT.top += (i8 - i4) * (m - j) / Math.max(1, k - j);
        localRECT.left -= (i3 - i2) / 2;
        localRECT.right = (localRECT.left + i3);
        localRECT.bottom = (localRECT.top + i4);
        OS.DrawThemeBackground(i1, paramGC.handle, 6, 0, localRECT, null);
        localRECT.top = (paramRectangle.y + i7 + i4 / 2);
        localRECT.bottom = (localRECT.top + 1);
        int i9 = j;
        while (i9 <= k)
        {
          localRECT.left = (paramRectangle.x + 5);
          localRECT.right = (localRECT.left + i5);
          if ((i9 != j) && (i9 != k))
            localRECT.left += 1;
          localRECT.top = (paramRectangle.y + i7 + i4 / 2);
          localRECT.top += (i8 - i4) * (i9 - j) / Math.max(1, k - j);
          localRECT.bottom = (localRECT.top + 1);
          OS.DrawThemeBackground(i1, paramGC.handle, 10, 1, localRECT, null);
          paramGC.drawLine(localRECT.left, localRECT.top, localRECT.right, localRECT.top);
          localRECT.left = (paramRectangle.x + 10 + i3 + 1);
          localRECT.right = (localRECT.left + i5);
          if ((i9 != j) && (i9 != k))
            localRECT.right -= 1;
          OS.DrawThemeBackground(i1, paramGC.handle, 10, 1, localRECT, null);
          paramGC.drawLine(localRECT.left, localRECT.top, localRECT.right, localRECT.top);
          i9 += n;
        }
      }
      OS.CloseThemeData(i1);
    }
  }

  char[] getClassId()
  {
    return TRACKBAR;
  }

  int hit(Theme paramTheme, Point paramPoint, Rectangle paramRectangle)
  {
    return paramRectangle.contains(paramPoint) ? 0 : -1;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.theme.ScaleDrawData
 * JD-Core Version:    0.6.2
 */