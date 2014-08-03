package org.eclipse.swt.internal.theme;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;

public class GroupDrawData extends DrawData
{
  public int headerWidth;
  public int headerHeight;
  public Rectangle headerArea;
  static final int GROUP_HEADER_X = 9;
  static final int GROUP_HEADER_PAD = 2;

  public GroupDrawData()
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
      localRECT1.right = (paramRectangle.x + paramRectangle.width);
      localRECT1.top = (paramRectangle.y + this.headerHeight / 2);
      localRECT1.bottom = (paramRectangle.y + paramRectangle.height);
      int j = paramRectangle.x + 9;
      int k = paramRectangle.y;
      int m = OS.SaveDC(paramGC.handle);
      OS.ExcludeClipRect(paramGC.handle, j - 2, k, j + this.headerWidth + 2, k + this.headerHeight);
      int[] arrayOfInt = getPartId(0);
      OS.DrawThemeBackground(i, paramGC.handle, arrayOfInt[0], arrayOfInt[1], localRECT1, null);
      OS.RestoreDC(paramGC.handle, m);
      Rectangle localRectangle1 = this.headerArea;
      if (localRectangle1 != null)
      {
        localRectangle1.x = j;
        localRectangle1.y = k;
        localRectangle1.width = this.headerWidth;
        localRectangle1.height = this.headerHeight;
      }
      Rectangle localRectangle2 = this.clientArea;
      if (localRectangle2 != null)
      {
        RECT localRECT2 = new RECT();
        OS.GetThemeBackgroundContentRect(i, paramGC.handle, arrayOfInt[0], arrayOfInt[1], localRECT1, localRECT2);
        localRectangle2.x = localRECT2.left;
        localRectangle2.y = localRECT2.top;
        localRectangle2.width = (localRECT2.right - localRECT2.left);
        localRectangle2.height = (localRECT2.bottom - localRECT2.top);
      }
      OS.CloseThemeData(i);
    }
  }

  int[] getPartId(int paramInt)
  {
    int i = this.state[paramInt];
    int j = 4;
    int k = 1;
    if ((i & 0x20) != 0)
      k = 2;
    return new int[] { j, k };
  }

  int hit(Theme paramTheme, Point paramPoint, Rectangle paramRectangle)
  {
    return paramRectangle.contains(paramPoint) ? 0 : -1;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.theme.GroupDrawData
 * JD-Core Version:    0.6.2
 */