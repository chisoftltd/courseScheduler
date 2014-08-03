package org.eclipse.swt.internal.theme;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;

public class ComboDrawData extends DrawData
{
  public ComboDrawData()
  {
    this.state = new int[2];
  }

  void draw(Theme paramTheme, GC paramGC, Rectangle paramRectangle)
  {
    if ((OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()))
    {
      int i = OS.OpenThemeData(0, EDIT);
      RECT localRECT1 = new RECT();
      localRECT1.left = paramRectangle.x;
      localRECT1.right = (paramRectangle.x + paramRectangle.width);
      localRECT1.top = paramRectangle.y;
      localRECT1.bottom = (paramRectangle.y + paramRectangle.height);
      int[] arrayOfInt = getPartId(0);
      OS.DrawThemeBackground(i, paramGC.handle, arrayOfInt[0], arrayOfInt[1], localRECT1, null);
      RECT localRECT2 = new RECT();
      OS.GetThemeBackgroundContentRect(i, paramGC.handle, arrayOfInt[0], arrayOfInt[1], localRECT1, localRECT2);
      Rectangle localRectangle = this.clientArea;
      if (localRectangle != null)
      {
        localRectangle.x = localRECT2.left;
        localRectangle.y = localRECT2.top;
        localRectangle.width = (localRECT2.right - localRECT2.left);
        localRectangle.height = (localRECT2.bottom - localRECT2.top);
      }
      OS.CloseThemeData(i);
      i = OS.OpenThemeData(0, getClassId());
      int j = OS.GetThemeSysSize(i, 2);
      localRECT1.left = (localRECT2.right - j);
      localRECT1.top = localRECT2.top;
      localRECT1.right = localRECT2.right;
      localRECT1.bottom = localRECT2.bottom;
      arrayOfInt = getPartId(1);
      OS.DrawThemeBackground(i, paramGC.handle, arrayOfInt[0], arrayOfInt[1], localRECT1, null);
      OS.CloseThemeData(i);
      if (localRectangle != null)
        localRectangle.width -= j;
    }
  }

  char[] getClassId()
  {
    return COMBOBOX;
  }

  int[] getPartId(int paramInt)
  {
    int i = this.state[paramInt];
    int j = 0;
    int k = 0;
    switch (paramInt)
    {
    case 0:
      j = 1;
      k = 1;
      if ((i & 0x20) != 0)
        k = 4;
      break;
    case 1:
      j = 1;
      k = 1;
      if ((i & 0x20) != 0)
        k = 4;
      if ((i & 0x40) != 0)
        k = 2;
      if ((i & 0x8) != 0)
        k = 3;
      break;
    }
    return new int[] { j, k };
  }

  int hit(Theme paramTheme, Point paramPoint, Rectangle paramRectangle)
  {
    if ((OS.COMCTL32_MAJOR < 6) || (!OS.IsAppThemed()))
      return -1;
    if (!paramRectangle.contains(paramPoint))
      return -1;
    int i = OS.OpenThemeData(0, EDIT);
    int[] arrayOfInt = getPartId(0);
    int j = arrayOfInt[0];
    int k = arrayOfInt[1];
    RECT localRECT1 = new RECT();
    localRECT1.left = paramRectangle.x;
    localRECT1.right = (paramRectangle.x + paramRectangle.width);
    localRECT1.top = paramRectangle.y;
    localRECT1.bottom = (paramRectangle.y + paramRectangle.height);
    RECT localRECT2 = new RECT();
    OS.GetThemeBackgroundContentRect(i, 0, j, k, localRECT1, localRECT2);
    OS.CloseThemeData(i);
    i = OS.OpenThemeData(0, getClassId());
    int m = OS.GetThemeSysSize(i, 2);
    OS.CloseThemeData(i);
    Rectangle localRectangle = new Rectangle(localRECT2.right - m, localRECT2.top, localRECT2.bottom - localRECT2.top, m);
    if (localRectangle.contains(paramPoint))
      return 1;
    return 0;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.theme.ComboDrawData
 * JD-Core Version:    0.6.2
 */