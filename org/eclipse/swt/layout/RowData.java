package org.eclipse.swt.layout;

import org.eclipse.swt.graphics.Point;

public final class RowData
{
  public int width = -1;
  public int height = -1;
  public boolean exclude = false;

  public RowData()
  {
  }

  public RowData(int paramInt1, int paramInt2)
  {
    this.width = paramInt1;
    this.height = paramInt2;
  }

  public RowData(Point paramPoint)
  {
    this(paramPoint.x, paramPoint.y);
  }

  String getName()
  {
    String str = getClass().getName();
    int i = str.lastIndexOf('.');
    if (i == -1)
      return str;
    return str.substring(i + 1, str.length());
  }

  public String toString()
  {
    String str = getName() + " {";
    if (this.width != -1)
      str = str + "width=" + this.width + " ";
    if (this.height != -1)
      str = str + "height=" + this.height + " ";
    if (this.exclude)
      str = str + "exclude=" + this.exclude + " ";
    str = str.trim();
    str = str + "}";
    return str;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.layout.RowData
 * JD-Core Version:    0.6.2
 */