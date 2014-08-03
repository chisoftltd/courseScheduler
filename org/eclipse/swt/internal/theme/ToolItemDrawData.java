package org.eclipse.swt.internal.theme;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.SIZE;

public class ToolItemDrawData extends DrawData
{
  public ToolBarDrawData parent;
  static final int INSET = 1;

  public ToolItemDrawData()
  {
    this.state = new int[2];
  }

  Rectangle computeTrim(Theme paramTheme, GC paramGC)
  {
    if ((OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()))
    {
      int i = OS.OpenThemeData(0, getClassId());
      RECT localRECT1 = new RECT();
      localRECT1.left = this.clientArea.x;
      localRECT1.right = (this.clientArea.x + this.clientArea.width);
      localRECT1.top = this.clientArea.y;
      localRECT1.bottom = (this.clientArea.y + this.clientArea.height);
      RECT localRECT2 = new RECT();
      int[] arrayOfInt = getPartId(0);
      OS.GetThemeBackgroundExtent(i, paramGC.handle, arrayOfInt[0], arrayOfInt[1], localRECT1, localRECT2);
      OS.CloseThemeData(i);
      if ((this.style & 0x4) != 0)
      {
        SIZE localSIZE = new SIZE();
        arrayOfInt = getPartId(1);
        OS.GetThemePartSize(i, 0, arrayOfInt[0], arrayOfInt[1], null, 1, localSIZE);
        localRECT2.right = Math.max(localRECT2.left, localRECT2.right + localSIZE.cx);
      }
      else
      {
        localRECT2.left -= 1;
        localRECT2.top -= 1;
        localRECT2.right += 1;
        localRECT2.bottom += 1;
      }
      return new Rectangle(localRECT2.left, localRECT2.top, localRECT2.right - localRECT2.left, localRECT2.bottom - localRECT2.top);
    }
    return new Rectangle(0, 0, 0, 0);
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
      SIZE localSIZE = null;
      int[] arrayOfInt1 = (int[])null;
      if ((this.style & 0x4) != 0)
      {
        localSIZE = new SIZE();
        arrayOfInt1 = getPartId(1);
        OS.GetThemePartSize(i, paramGC.handle, arrayOfInt1[0], arrayOfInt1[1], localRECT1, 1, localSIZE);
        localRECT1.right -= localSIZE.cx;
        if (localRECT1.right < localRECT1.left)
          localRECT1.right = localRECT1.left;
      }
      int[] arrayOfInt2 = getPartId(0);
      OS.DrawThemeBackground(i, paramGC.handle, arrayOfInt2[0], arrayOfInt2[1], localRECT1, null);
      Rectangle localRectangle = this.clientArea;
      if (localRectangle != null)
      {
        RECT localRECT2 = new RECT();
        OS.GetThemeBackgroundContentRect(i, paramGC.handle, arrayOfInt2[0], arrayOfInt2[1], localRECT1, localRECT2);
        localRectangle.x = localRECT2.left;
        localRectangle.y = localRECT2.top;
        localRectangle.width = (localRECT2.right - localRECT2.left);
        localRectangle.height = (localRECT2.bottom - localRECT2.top);
      }
      if ((this.style & 0x4) != 0)
      {
        localRECT1.left = localRECT1.right;
        localRECT1.right = (localRECT1.left + localSIZE.cx);
        OS.DrawThemeBackground(i, paramGC.handle, arrayOfInt1[0], arrayOfInt1[1], localRECT1, null);
      }
      OS.CloseThemeData(i);
    }
  }

  char[] getClassId()
  {
    return TOOLBAR;
  }

  int[] getPartId(int paramInt)
  {
    int i = this.state[paramInt];
    int j = 0;
    int k = 0;
    switch (paramInt)
    {
    case 0:
      if ((this.style & 0x38) != 0)
        j = 1;
      else if ((this.style & 0x4) != 0)
        j = 3;
      else if ((this.style & 0x2) != 0)
        if ((this.parent.style & 0x200) != 0)
          j = 6;
        else
          j = 5;
      if ((this.style & 0x2) == 0)
      {
        if ((i & 0x40) != 0)
          if (((this.style & 0x30) != 0) && ((i & 0x2) != 0))
            k = 6;
          else
            k = 2;
        if (((this.style & 0x30) != 0) && ((i & 0x2) != 0))
          k = 5;
        if ((i & 0x8) != 0)
          k = 3;
        if ((i & 0x20) != 0)
          k = 4;
      }
      break;
    case 1:
      j = 4;
      if ((i & 0x40) != 0)
        k = 2;
      if ((i & 0x8) != 0)
        k = 3;
      if ((i & 0x20) != 0)
        k = 4;
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
    int i = OS.OpenThemeData(0, getClassId());
    try
    {
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
      if (arrayOfShort[0] == 0)
        return -1;
      int j = this.style;
      if ((j & 0x4) != 0)
      {
        SIZE localSIZE = new SIZE();
        arrayOfInt = getPartId(1);
        OS.GetThemePartSize(i, 0, arrayOfInt[0], arrayOfInt[1], localRECT, 1, localSIZE);
        localRECT.left = Math.max(localRECT.left, localRECT.right - localSIZE.cx);
        OS.HitTestThemeBackground(i, 0, arrayOfInt[0], arrayOfInt[1], 0, localRECT, 0, localPOINT, arrayOfShort);
        if (arrayOfShort[0] != 0)
          return 1;
      }
    }
    finally
    {
      OS.CloseThemeData(i);
    }
    jsr -10;
    return 0;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.theme.ToolItemDrawData
 * JD-Core Version:    0.6.2
 */