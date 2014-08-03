package org.eclipse.swt.internal.theme;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.SIZE;

public class ExpanderDrawData extends DrawData
{
  public ExpanderDrawData()
  {
    this.state = new int[1];
  }

  void draw(Theme paramTheme, GC paramGC, Rectangle paramRectangle)
  {
    if ((OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()))
    {
      int i = OS.OpenThemeData(0, getClassId());
      int j = 1;
      if ((this.style & 0x400) != 0)
        j = 2;
      SIZE localSIZE = new SIZE();
      OS.GetThemePartSize(i, paramGC.handle, 2, j, null, 1, localSIZE);
      RECT localRECT = new RECT();
      localRECT.left = paramRectangle.x;
      localRECT.right = (localRECT.left + localSIZE.cx);
      localRECT.top = paramRectangle.y;
      localRECT.bottom = (localRECT.top + localSIZE.cy);
      OS.DrawThemeBackground(i, paramGC.handle, 2, j, localRECT, null);
      OS.CloseThemeData(i);
    }
  }

  char[] getClassId()
  {
    return TREEVIEW;
  }

  int[] getPartId(int paramInt)
  {
    int i = 2;
    int j = 1;
    if ((this.style & 0x400) != 0)
      j = 2;
    return new int[] { i, j };
  }

  int hit(Theme paramTheme, Point paramPoint, Rectangle paramRectangle)
  {
    if ((OS.COMCTL32_MAJOR < 6) || (!OS.IsAppThemed()))
      return -1;
    if (!paramRectangle.contains(paramPoint))
      return -1;
    int i = OS.OpenThemeData(0, getClassId());
    SIZE localSIZE = new SIZE();
    int[] arrayOfInt = getPartId(0);
    OS.GetThemePartSize(i, 0, arrayOfInt[0], arrayOfInt[1], null, 1, localSIZE);
    OS.CloseThemeData(i);
    if (new Rectangle(paramRectangle.x, paramRectangle.y, localSIZE.cx, localSIZE.cy).contains(paramPoint))
      return 0;
    return -1;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.theme.ExpanderDrawData
 * JD-Core Version:    0.6.2
 */