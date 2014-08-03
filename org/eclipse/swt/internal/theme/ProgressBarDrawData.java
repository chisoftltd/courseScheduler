package org.eclipse.swt.internal.theme;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;

public class ProgressBarDrawData extends RangeDrawData
{
  public ProgressBarDrawData()
  {
    this.state = new int[1];
  }

  void draw(Theme paramTheme, GC paramGC, Rectangle paramRectangle)
  {
    if ((OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()))
    {
      int i = OS.OpenThemeData(0, getClassId());
      RECT localRECT1 = new RECT();
      localRECT1.left = paramRectangle.x;
      localRECT1.right = (localRECT1.left + paramRectangle.width);
      localRECT1.top = paramRectangle.y;
      localRECT1.bottom = (localRECT1.top + paramRectangle.height);
      int[] arrayOfInt1 = new int[1];
      OS.GetThemeInt(i, 0, 0, 2411, arrayOfInt1);
      int j = arrayOfInt1[0];
      OS.GetThemeInt(i, 0, 0, 2412, arrayOfInt1);
      int k = arrayOfInt1[0];
      RECT localRECT2 = new RECT();
      int[] arrayOfInt2 = getPartId(0);
      int m;
      if ((this.style & 0x200) != 0)
      {
        OS.GetThemeBackgroundContentRect(i, paramGC.handle, arrayOfInt2[0], arrayOfInt2[1], localRECT1, localRECT2);
        OS.DrawThemeBackground(i, paramGC.handle, arrayOfInt2[0], arrayOfInt2[1], localRECT1, null);
        m = localRECT2.bottom - (localRECT2.bottom - localRECT2.top) * (this.selection - this.minimum) / (this.maximum - this.minimum);
        for (localRECT2.top = (localRECT2.bottom - j); localRECT2.top >= m; localRECT2.top = (localRECT2.bottom - j))
        {
          OS.DrawThemeBackground(i, paramGC.handle, 4, 0, localRECT2, null);
          localRECT2.bottom -= j + k;
        }
        if (this.selection != 0)
          OS.DrawThemeBackground(i, paramGC.handle, 4, 0, localRECT2, null);
      }
      else
      {
        OS.GetThemeBackgroundContentRect(i, paramGC.handle, arrayOfInt2[0], arrayOfInt2[1], localRECT1, localRECT2);
        OS.DrawThemeBackground(i, paramGC.handle, arrayOfInt2[0], arrayOfInt2[1], localRECT1, null);
        m = localRECT2.left + (localRECT2.right - localRECT2.left) * (this.selection - this.minimum) / (this.maximum - this.minimum);
        for (localRECT2.right = (localRECT2.left + j); localRECT2.right <= m; localRECT2.right = (localRECT2.left + j))
        {
          OS.DrawThemeBackground(i, paramGC.handle, 3, 0, localRECT2, null);
          localRECT2.left += j + k;
        }
        if (this.selection != 0)
          OS.DrawThemeBackground(i, paramGC.handle, 3, 0, localRECT2, null);
      }
      OS.CloseThemeData(i);
    }
  }

  char[] getClassId()
  {
    return PROGRESS;
  }

  int[] getPartId(int paramInt)
  {
    int i = 0;
    int j = 0;
    if ((this.style & 0x200) != 0)
      i = 2;
    else
      i = 1;
    return new int[] { i, j };
  }

  int hit(Theme paramTheme, Point paramPoint, Rectangle paramRectangle)
  {
    return paramRectangle.contains(paramPoint) ? 0 : -1;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.theme.ProgressBarDrawData
 * JD-Core Version:    0.6.2
 */