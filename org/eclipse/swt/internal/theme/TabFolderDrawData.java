package org.eclipse.swt.internal.theme;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;

public class TabFolderDrawData extends DrawData
{
  public int tabsWidth;
  public int tabsHeight;
  public Rectangle tabsArea;
  public int selectedX;
  public int selectedWidth;
  public int spacing;

  public TabFolderDrawData()
  {
    this.state = new int[1];
    if (SWT.getPlatform().equals("gtk"))
      this.spacing = -2;
  }

  void draw(Theme paramTheme, GC paramGC, Rectangle paramRectangle)
  {
    if ((OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()))
    {
      int i = OS.OpenThemeData(0, getClassId());
      RECT localRECT = new RECT();
      localRECT.left = paramRectangle.x;
      localRECT.right = (paramRectangle.x + paramRectangle.width);
      localRECT.top = paramRectangle.y;
      if ((this.style & 0x400) != 0)
      {
        localRECT.bottom = (paramRectangle.y + paramRectangle.height - this.tabsHeight);
      }
      else
      {
        localRECT.top += this.tabsHeight;
        localRECT.bottom = (paramRectangle.y + paramRectangle.height);
      }
      int[] arrayOfInt = getPartId(0);
      OS.DrawThemeBackground(i, paramGC.handle, arrayOfInt[0], arrayOfInt[1], localRECT, null);
      OS.CloseThemeData(i);
      if (this.tabsArea != null)
      {
        this.tabsArea.x = paramRectangle.x;
        this.tabsArea.y = paramRectangle.y;
        this.tabsArea.width = paramRectangle.width;
        this.tabsArea.height = this.tabsHeight;
        if ((this.style & 0x400) != 0)
          this.tabsArea.y += paramRectangle.height - this.tabsHeight;
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
    int j = 9;
    int k = 1;
    if ((i & 0x20) != 0)
    {
      k = 4;
    }
    else
    {
      if ((i & 0x40) != 0)
        k = 2;
      if ((i & 0x2) != 0)
        k = 3;
    }
    return new int[] { j, k };
  }

  int hit(Theme paramTheme, Point paramPoint, Rectangle paramRectangle)
  {
    if (!paramRectangle.contains(paramPoint))
      return -1;
    return 0;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.theme.TabFolderDrawData
 * JD-Core Version:    0.6.2
 */