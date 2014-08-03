package org.eclipse.swt.internal.theme;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.SIZE;

public class ButtonDrawData extends DrawData
{
  public ButtonDrawData()
  {
    this.state = new int[1];
  }

  int[] getPartId(int paramInt)
  {
    int i = this.state[paramInt];
    int j = this.style;
    int k = 0;
    int m = 0;
    if ((j & 0x8) != 0)
    {
      k = 1;
      m = 1;
      if (((i & 0x80) != 0) && ((i & 0x10) != 0))
        m = 5;
      if ((i & 0x40) != 0)
        m = 2;
      if ((i & 0x8) != 0)
        m = 3;
      if ((i & 0x20) != 0)
        m = 4;
    }
    if ((j & 0x10) != 0)
      k = 2;
    if ((j & 0x20) != 0)
      k = 3;
    if ((j & 0x30) != 0)
      if ((i & 0x2) != 0)
      {
        m = 5;
        if ((i & 0x40) != 0)
          m = 6;
        if ((i & 0x8) != 0)
          m = 7;
        if ((i & 0x20) != 0)
          m = 8;
      }
      else
      {
        m = 1;
        if ((i & 0x40) != 0)
          m = 2;
        if ((i & 0x8) != 0)
          m = 3;
        if ((i & 0x20) != 0)
          m = 4;
      }
    return new int[] { k, m };
  }

  void draw(Theme paramTheme, GC paramGC, Rectangle paramRectangle)
  {
    if ((OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()))
    {
      int i = OS.OpenThemeData(0, getClassId());
      RECT localRECT1 = new RECT();
      localRECT1.left = paramRectangle.x;
      localRECT1.right = (paramRectangle.x + paramRectangle.width);
      localRECT1.top = paramRectangle.y;
      localRECT1.bottom = (paramRectangle.y + paramRectangle.height);
      int[] arrayOfInt = getPartId(0);
      if ((this.style & 0x30) != 0)
      {
        localObject = new SIZE();
        OS.GetThemePartSize(i, paramGC.handle, arrayOfInt[0], arrayOfInt[1], localRECT1, 2, (SIZE)localObject);
        localRECT1.right = (localRECT1.left + ((SIZE)localObject).cx);
        OS.DrawThemeBackground(i, paramGC.handle, arrayOfInt[0], arrayOfInt[1], localRECT1, null);
        localRECT1.left = (localRECT1.right + 3);
        localRECT1.right = (localRECT1.left + paramRectangle.width - ((SIZE)localObject).cx - 3);
      }
      else
      {
        OS.DrawThemeBackground(i, paramGC.handle, arrayOfInt[0], arrayOfInt[1], localRECT1, null);
      }
      Object localObject = this.clientArea;
      if (localObject != null)
      {
        RECT localRECT2 = new RECT();
        OS.GetThemeBackgroundContentRect(i, paramGC.handle, arrayOfInt[0], arrayOfInt[1], localRECT1, localRECT2);
        ((Rectangle)localObject).x = localRECT2.left;
        ((Rectangle)localObject).y = localRECT2.top;
        ((Rectangle)localObject).width = (localRECT2.right - localRECT2.left);
        ((Rectangle)localObject).height = (localRECT2.bottom - localRECT2.top);
      }
      OS.CloseThemeData(i);
    }
  }

  int hit(Theme paramTheme, Point paramPoint, Rectangle paramRectangle)
  {
    if ((OS.COMCTL32_MAJOR < 6) || (!OS.IsAppThemed()))
      return -1;
    if (!paramRectangle.contains(paramPoint))
      return -1;
    int i = OS.OpenThemeData(0, getClassId());
    RECT localRECT = new RECT();
    localRECT.left = paramRectangle.x;
    localRECT.right = (paramRectangle.x + paramRectangle.width);
    localRECT.top = paramRectangle.y;
    localRECT.bottom = (paramRectangle.y + paramRectangle.height);
    POINT localPOINT = new POINT();
    localPOINT.x = paramPoint.x;
    localPOINT.y = paramPoint.y;
    short[] arrayOfShort = new short[1];
    int[] arrayOfInt = getPartId(0);
    OS.HitTestThemeBackground(i, 0, arrayOfInt[0], arrayOfInt[1], 0, localRECT, 0, localPOINT, arrayOfShort);
    OS.CloseThemeData(i);
    return arrayOfShort[0] == 0 ? -1 : 0;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.theme.ButtonDrawData
 * JD-Core Version:    0.6.2
 */