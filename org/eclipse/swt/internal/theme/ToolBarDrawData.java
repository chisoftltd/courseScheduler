package org.eclipse.swt.internal.theme;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

public class ToolBarDrawData extends DrawData
{
  public ToolBarDrawData()
  {
    this.state = new int[1];
  }

  void draw(Theme paramTheme, GC paramGC, Rectangle paramRectangle)
  {
  }

  char[] getClassId()
  {
    return TOOLBAR;
  }

  int hit(Theme paramTheme, Point paramPoint, Rectangle paramRectangle)
  {
    if (!paramRectangle.contains(paramPoint))
      return -1;
    return 0;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.theme.ToolBarDrawData
 * JD-Core Version:    0.6.2
 */