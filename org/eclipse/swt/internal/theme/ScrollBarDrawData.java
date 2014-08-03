package org.eclipse.swt.internal.theme;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.internal.win32.RECT;

public class ScrollBarDrawData extends RangeDrawData
{
  public int thumb;
  public int increment;
  public int pageIncrement;

  public ScrollBarDrawData()
  {
    this.state = new int[6];
  }

  void draw(Theme paramTheme, GC paramGC, Rectangle paramRectangle)
  {
    if ((OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()))
    {
      int i = OS.OpenThemeData(0, getClassId());
      RECT localRECT = new RECT();
      int j;
      int[] arrayOfInt;
      int k;
      int m;
      int n;
      if ((this.style & 0x200) != 0)
      {
        j = OS.GetThemeSysSize(i, 2);
        localRECT.left = paramRectangle.x;
        localRECT.right = (localRECT.left + paramRectangle.width);
        localRECT.top = paramRectangle.y;
        localRECT.bottom = (localRECT.top + j);
        arrayOfInt = getPartId(1);
        OS.DrawThemeBackground(i, paramGC.handle, arrayOfInt[0], arrayOfInt[1], localRECT, null);
        localRECT.bottom = (paramRectangle.y + paramRectangle.height);
        localRECT.top = (localRECT.bottom - j);
        arrayOfInt = getPartId(2);
        OS.DrawThemeBackground(i, paramGC.handle, arrayOfInt[0], arrayOfInt[1], localRECT, null);
        k = paramRectangle.height - 2 * j;
        m = Math.max(j / 2, k * this.thumb / Math.max(1, this.maximum - this.minimum));
        n = paramRectangle.y + j + Math.max(0, k * this.selection / Math.max(1, this.maximum - this.minimum));
        localRECT.top = (paramRectangle.y + j);
        localRECT.bottom = n;
        arrayOfInt = getPartId(3);
        OS.DrawThemeBackground(i, paramGC.handle, arrayOfInt[0], arrayOfInt[1], localRECT, null);
        localRECT.top = localRECT.bottom;
        localRECT.bottom = (localRECT.top + m);
        arrayOfInt = getPartId(5);
        OS.DrawThemeBackground(i, paramGC.handle, arrayOfInt[0], arrayOfInt[1], localRECT, null);
        OS.DrawThemeBackground(i, paramGC.handle, 9, arrayOfInt[1], localRECT, null);
        localRECT.top = localRECT.bottom;
        localRECT.bottom = (paramRectangle.y + paramRectangle.height - j);
        arrayOfInt = getPartId(4);
        OS.DrawThemeBackground(i, paramGC.handle, arrayOfInt[0], arrayOfInt[1], localRECT, null);
      }
      else
      {
        j = OS.GetThemeSysSize(i, 2);
        localRECT.top = paramRectangle.y;
        localRECT.bottom = (localRECT.top + paramRectangle.height);
        localRECT.left = paramRectangle.x;
        localRECT.right = (localRECT.left + j);
        arrayOfInt = getPartId(1);
        OS.DrawThemeBackground(i, paramGC.handle, arrayOfInt[0], arrayOfInt[1], localRECT, null);
        localRECT.right = (paramRectangle.x + paramRectangle.width);
        localRECT.left = (localRECT.right - j);
        arrayOfInt = getPartId(2);
        OS.DrawThemeBackground(i, paramGC.handle, arrayOfInt[0], arrayOfInt[1], localRECT, null);
        k = paramRectangle.width - 2 * j;
        m = Math.max(j / 2, k * this.thumb / (this.maximum - this.minimum));
        n = paramRectangle.x + j + Math.max(0, k * this.selection / Math.max(1, this.maximum - this.minimum));
        localRECT.left = (paramRectangle.x + j);
        localRECT.right = n;
        arrayOfInt = getPartId(3);
        OS.DrawThemeBackground(i, paramGC.handle, arrayOfInt[0], arrayOfInt[1], localRECT, null);
        localRECT.left = localRECT.right;
        localRECT.right = (localRECT.left + m);
        arrayOfInt = getPartId(5);
        OS.DrawThemeBackground(i, paramGC.handle, arrayOfInt[0], arrayOfInt[1], localRECT, null);
        OS.DrawThemeBackground(i, paramGC.handle, 8, arrayOfInt[1], localRECT, null);
        localRECT.left = localRECT.right;
        localRECT.right = (paramRectangle.x + paramRectangle.width - j);
        arrayOfInt = getPartId(4);
        OS.DrawThemeBackground(i, paramGC.handle, arrayOfInt[0], arrayOfInt[1], localRECT, null);
      }
      OS.CloseThemeData(i);
    }
  }

  char[] getClassId()
  {
    return SCROLLBAR;
  }

  int[] getPartId(int paramInt)
  {
    int i = 0;
    int j = 0;
    int k = this.state[paramInt];
    switch (paramInt)
    {
    case 1:
      i = 1;
      if ((this.style & 0x200) != 0)
      {
        j = 1;
        if ((k & 0x40) != 0)
          j = 2;
        if ((k & 0x8) != 0)
          j = 3;
        if ((k & 0x20) != 0)
          j = 4;
      }
      else
      {
        j = 9;
        if ((k & 0x40) != 0)
          j = 10;
        if ((k & 0x8) != 0)
          j = 11;
        if ((k & 0x20) != 0)
          j = 12;
      }
      break;
    case 2:
      i = 1;
      if ((this.style & 0x200) != 0)
      {
        j = 5;
        if ((k & 0x40) != 0)
          j = 6;
        if ((k & 0x8) != 0)
          j = 7;
        if ((k & 0x20) != 0)
          j = 8;
      }
      else
      {
        j = 13;
        if ((k & 0x40) != 0)
          j = 14;
        if ((k & 0x8) != 0)
          j = 15;
        if ((k & 0x20) != 0)
          j = 16;
      }
      break;
    case 0:
    case 5:
      if ((this.style & 0x200) != 0)
        i = 3;
      else
        i = 2;
      break;
    case 3:
      if ((this.style & 0x200) != 0)
        i = 7;
      else
        i = 5;
      break;
    case 4:
      if ((this.style & 0x200) != 0)
        i = 6;
      else
        i = 4;
      break;
    }
    if ((paramInt != 2) && (paramInt != 1))
    {
      j = 1;
      if ((k & 0x40) != 0)
        j = 2;
      if ((k & 0x8) != 0)
        j = 3;
      if ((k & 0x20) != 0)
        j = 4;
    }
    return new int[] { i, j };
  }

  Rectangle getBounds(int paramInt, Rectangle paramRectangle)
  {
    if ((OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()))
    {
      int i = OS.OpenThemeData(0, getClassId());
      if ((this.style & 0x200) != 0)
      {
        int j = OS.GetThemeSysSize(i, 2);
        int k = paramRectangle.height - 2 * j;
        int m = Math.max(j / 2, k * this.thumb / Math.max(1, this.maximum - this.minimum));
        int n = paramRectangle.y + j + Math.max(0, k * this.selection / Math.max(1, this.maximum - this.minimum));
        switch (paramInt)
        {
        case 2:
          return new Rectangle(paramRectangle.x, paramRectangle.y + paramRectangle.height - j, paramRectangle.width, j);
        case 1:
          return new Rectangle(paramRectangle.x, paramRectangle.y, paramRectangle.width, j);
        case 3:
          return new Rectangle(paramRectangle.x, paramRectangle.y + j, paramRectangle.width, n - paramRectangle.y - j);
        case 5:
          return new Rectangle(paramRectangle.x, n, paramRectangle.width, m);
        case 4:
          return new Rectangle(paramRectangle.x, n + m, paramRectangle.width, paramRectangle.y + paramRectangle.height - j - n - m);
        }
      }
      OS.CloseThemeData(i);
    }
    return super.getBounds(paramInt, paramRectangle);
  }

  int getSelection(Point paramPoint, Rectangle paramRectangle)
  {
    if ((OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()))
    {
      int i = OS.OpenThemeData(0, getClassId());
      if ((this.style & 0x200) != 0)
      {
        int j = OS.GetThemeSysSize(i, 2);
        int k = paramRectangle.height - 2 * j;
        int m = paramRectangle.y + j + Math.max(0, k * this.selection / Math.max(1, this.maximum - this.minimum));
        m += paramPoint.y;
        int n = (m - paramRectangle.y - j) * (this.maximum - this.minimum) / k;
        return Math.max(0, Math.min(n, this.maximum - this.thumb));
      }
      OS.CloseThemeData(i);
    }
    return 0;
  }

  int hit(Theme paramTheme, Point paramPoint, Rectangle paramRectangle)
  {
    if ((OS.COMCTL32_MAJOR < 6) || (!OS.IsAppThemed()))
      return -1;
    int i = OS.OpenThemeData(0, getClassId());
    int j = 0;
    RECT localRECT = new RECT();
    POINT localPOINT = new POINT();
    localPOINT.x = paramPoint.x;
    localPOINT.y = paramPoint.y;
    short[] arrayOfShort = new short[1];
    try
    {
      int k;
      label243: int m;
      int n;
      int i1;
      if ((this.style & 0x200) != 0)
      {
        k = OS.GetThemeSysSize(i, 2);
        localRECT.left = paramRectangle.x;
        localRECT.right = (localRECT.left + paramRectangle.width);
        localRECT.top = paramRectangle.y;
        localRECT.bottom = (localRECT.top + k);
        arrayOfInt = getPartId(1);
        OS.HitTestThemeBackground(i, j, arrayOfInt[0], arrayOfInt[1], 0, localRECT, 0, localPOINT, arrayOfShort);
        if (arrayOfShort[0] != 0)
          return 1;
        localRECT.bottom = (paramRectangle.y + paramRectangle.height);
        localRECT.top = (localRECT.bottom - k);
        arrayOfInt = getPartId(2);
        OS.HitTestThemeBackground(i, j, arrayOfInt[0], arrayOfInt[1], 0, localRECT, 0, localPOINT, arrayOfShort);
        if (arrayOfShort[0] != 0)
          return 2;
        m = paramRectangle.height - 2 * k;
        n = Math.max(k / 2, m * this.thumb / Math.max(1, this.maximum - this.minimum));
        i1 = paramRectangle.y + k + Math.max(0, m * this.selection / Math.max(1, this.maximum - this.minimum));
        localRECT.top = (paramRectangle.y + k);
        localRECT.bottom = i1;
        arrayOfInt = getPartId(5);
        OS.HitTestThemeBackground(i, j, arrayOfInt[0], arrayOfInt[1], 0, localRECT, 0, localPOINT, arrayOfShort);
        if (arrayOfShort[0] != 0)
          label381: return 3;
        localRECT.top = localRECT.bottom;
        localRECT.bottom = (localRECT.top + n);
        arrayOfInt = getPartId(3);
        OS.HitTestThemeBackground(i, j, arrayOfInt[0], arrayOfInt[1], 0, localRECT, 0, localPOINT, arrayOfShort);
        if (arrayOfShort[0] == 0);
      }
      do
      {
        return 5;
        localRECT.top = localRECT.bottom;
        localRECT.bottom = (paramRectangle.y + paramRectangle.height - k);
        arrayOfInt = getPartId(4);
        OS.HitTestThemeBackground(i, j, arrayOfInt[0], arrayOfInt[1], 0, localRECT, 0, localPOINT, arrayOfShort);
        if (arrayOfShort[0] == 0)
          break label967;
        return 4;
        k = OS.GetThemeSysSize(i, 2);
        localRECT.top = paramRectangle.y;
        localRECT.bottom = (localRECT.top + paramRectangle.height);
        localRECT.left = paramRectangle.x;
        localRECT.right = (localRECT.left + k);
        arrayOfInt = getPartId(1);
        OS.HitTestThemeBackground(i, j, arrayOfInt[0], arrayOfInt[1], 0, localRECT, 0, localPOINT, arrayOfShort);
        if (arrayOfShort[0] != 0)
          break;
        localRECT.right = (paramRectangle.x + paramRectangle.width);
        localRECT.left = (localRECT.right - k);
        arrayOfInt = getPartId(2);
        OS.HitTestThemeBackground(i, j, arrayOfInt[0], arrayOfInt[1], 0, localRECT, 0, localPOINT, arrayOfShort);
        if (arrayOfShort[0] != 0)
          break label243;
        m = paramRectangle.width - 2 * k;
        n = Math.max(k / 2, m * this.thumb / (this.maximum - this.minimum));
        i1 = paramRectangle.x + k + Math.max(0, m * this.selection / Math.max(1, this.maximum - this.minimum));
        localRECT.left = (paramRectangle.x + k);
        localRECT.right = i1;
        arrayOfInt = getPartId(3);
        OS.HitTestThemeBackground(i, j, arrayOfInt[0], arrayOfInt[1], 0, localRECT, 0, localPOINT, arrayOfShort);
        if (arrayOfShort[0] != 0)
          break label381;
        localRECT.left = localRECT.right;
        localRECT.right = (localRECT.left + n);
        arrayOfInt = getPartId(5);
        OS.HitTestThemeBackground(i, j, arrayOfInt[0], arrayOfInt[1], 0, localRECT, 0, localPOINT, arrayOfShort);
      }
      while (arrayOfShort[0] != 0);
      localRECT.left = localRECT.right;
      localRECT.right = (paramRectangle.x + paramRectangle.width - k);
      int[] arrayOfInt = getPartId(4);
      OS.HitTestThemeBackground(i, j, arrayOfInt[0], arrayOfInt[1], 0, localRECT, 0, localPOINT, arrayOfShort);
      if (arrayOfShort[0] == 0);
    }
    finally
    {
      OS.CloseThemeData(i);
    }
    label967: jsr -10;
    return -1;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.theme.ScrollBarDrawData
 * JD-Core Version:    0.6.2
 */