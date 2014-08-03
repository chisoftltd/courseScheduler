package org.eclipse.swt.internal.theme;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;

public class DrawData
{
  public int style;
  public int[] state = new int[1];
  public Rectangle clientArea;
  public static final int SELECTED = 2;
  public static final int FOCUSED = 4;
  public static final int PRESSED = 8;
  public static final int ACTIVE = 16;
  public static final int DISABLED = 32;
  public static final int HOT = 64;
  public static final int DEFAULTED = 128;
  public static final int GRAYED = 256;
  public static final int DRAW_LEFT = 16;
  public static final int DRAW_TOP = 32;
  public static final int DRAW_RIGHT = 64;
  public static final int DRAW_BOTTOM = 128;
  public static final int DRAW_HCENTER = 256;
  public static final int DRAW_VCENTER = 512;
  public static final int WIDGET_NOWHERE = -1;
  public static final int WIDGET_WHOLE = 0;
  public static final int SCROLLBAR_UP_ARROW = 1;
  public static final int SCROLLBAR_DOWN_ARROW = 2;
  public static final int SCROLLBAR_LEFT_ARROW = 1;
  public static final int SCROLLBAR_RIGHT_ARROW = 2;
  public static final int SCROLLBAR_UP_TRACK = 3;
  public static final int SCROLLBAR_DOWN_TRACK = 4;
  public static final int SCROLLBAR_LEFT_TRACK = 3;
  public static final int SCROLLBAR_RIGHT_TRACK = 4;
  public static final int SCROLLBAR_THUMB = 5;
  public static final int SCALE_UP_TRACK = 1;
  public static final int SCALE_LEFT_TRACK = 1;
  public static final int SCALE_DOWN_TRACK = 2;
  public static final int SCALE_RIGHT_TRACK = 2;
  public static final int SCALE_THUMB = 3;
  public static final int TOOLITEM_ARROW = 1;
  public static final int COMBO_ARROW = 1;
  static final char[] EDIT = { 'E', 'D', 'I', 'T' };
  static final char[] COMBOBOX = { 'C', 'O', 'M', 'B', 'O', 'B', 'O', 'X' };
  static final char[] BUTTON = { 'B', 'U', 'T', 'T', 'O', 'N' };
  static final char[] PROGRESS = { 'P', 'R', 'O', 'G', 'R', 'E', 'S', 'S' };
  static final char[] SCROLLBAR = { 'S', 'C', 'R', 'O', 'L', 'L', 'B', 'A', 'R' };
  static final char[] TAB = { 'T', 'A', 'B' };
  static final char[] TRACKBAR = { 'T', 'R', 'A', 'C', 'K', 'B', 'A', 'R' };
  static final char[] TOOLBAR = { 'T', 'O', 'O', 'L', 'B', 'A', 'R' };
  static final char[] TREEVIEW = { 'T', 'R', 'E', 'E', 'V', 'I', 'E', 'W' };

  Rectangle computeTrim(Theme paramTheme, GC paramGC)
  {
    return new Rectangle(this.clientArea.x, this.clientArea.y, this.clientArea.width, this.clientArea.height);
  }

  void draw(Theme paramTheme, GC paramGC, Rectangle paramRectangle)
  {
  }

  void drawImage(Theme paramTheme, Image paramImage, GC paramGC, Rectangle paramRectangle)
  {
    if ((OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()))
    {
      Rectangle localRectangle = paramImage.getBounds();
      paramGC.drawImage(paramImage, 0, 0, localRectangle.width, localRectangle.height, paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height);
    }
  }

  void drawText(Theme paramTheme, String paramString, int paramInt, GC paramGC, Rectangle paramRectangle)
  {
    if ((OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()))
    {
      int i = OS.OpenThemeData(0, getClassId());
      char[] arrayOfChar = new char[paramString.length()];
      paramString.getChars(0, arrayOfChar.length, arrayOfChar, 0);
      int j = 32;
      if ((paramInt & 0x10) != 0)
        j |= 0;
      if ((paramInt & 0x100) != 0)
        j |= 1;
      if ((paramInt & 0x40) != 0)
        j |= 2;
      if ((paramInt & 0x20) != 0)
        j |= 0;
      if ((paramInt & 0x80) != 0)
        j |= 8;
      if ((paramInt & 0x200) != 0)
        j |= 4;
      RECT localRECT = new RECT();
      localRECT.left = paramRectangle.x;
      localRECT.right = (paramRectangle.x + paramRectangle.width);
      localRECT.top = paramRectangle.y;
      localRECT.bottom = (paramRectangle.y + paramRectangle.height);
      int[] arrayOfInt = getPartId(0);
      int k = arrayOfInt[0];
      int m = arrayOfInt[1];
      OS.DrawThemeText(i, paramGC.handle, k, m, arrayOfChar, arrayOfChar.length, j, 0, localRECT);
      OS.CloseThemeData(i);
    }
  }

  char[] getClassId()
  {
    return BUTTON;
  }

  int[] getPartId(int paramInt)
  {
    return new int[2];
  }

  Rectangle getBounds(int paramInt, Rectangle paramRectangle)
  {
    return new Rectangle(paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height);
  }

  int hit(Theme paramTheme, Point paramPoint, Rectangle paramRectangle)
  {
    return -1;
  }

  Rectangle measureText(Theme paramTheme, String paramString, int paramInt, GC paramGC, Rectangle paramRectangle)
  {
    if ((OS.COMCTL32_MAJOR < 6) || (!OS.IsAppThemed()))
      return new Rectangle(0, 0, 0, 0);
    int i = OS.OpenThemeData(0, getClassId());
    char[] arrayOfChar = new char[paramString.length()];
    paramString.getChars(0, arrayOfChar.length, arrayOfChar, 0);
    int j = 0;
    if ((this.style & 0x4000) != 0)
      j |= 0;
    if ((this.style & 0x1000000) != 0)
      j |= 1;
    if ((this.style & 0x20000) != 0)
      j |= 2;
    RECT localRECT1 = new RECT();
    RECT localRECT2 = null;
    if (paramRectangle != null)
    {
      localRECT2 = new RECT();
      localRECT2.left = paramRectangle.x;
      localRECT2.right = (paramRectangle.x + paramRectangle.width);
      localRECT2.top = paramRectangle.y;
      localRECT2.bottom = (paramRectangle.y + paramRectangle.height);
    }
    int[] arrayOfInt = getPartId(0);
    int k = arrayOfInt[0];
    int m = arrayOfInt[1];
    OS.GetThemeTextExtent(i, paramGC.handle, k, m, arrayOfChar, arrayOfChar.length, j, localRECT2, localRECT1);
    OS.CloseThemeData(i);
    return new Rectangle(localRECT1.left, localRECT1.top, localRECT1.right - localRECT1.left, localRECT1.bottom - localRECT1.top);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.theme.DrawData
 * JD-Core Version:    0.6.2
 */