package org.eclipse.swt.custom;

import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.widgets.Widget;

public class CTabFolderEvent extends TypedEvent
{
  public Widget item;
  public boolean doit;
  public int x;
  public int y;
  public int width;
  public int height;
  static final long serialVersionUID = 3760566386225066807L;

  CTabFolderEvent(Widget paramWidget)
  {
    super(paramWidget);
  }

  public String toString()
  {
    String str = super.toString();
    return str.substring(0, str.length() - 1) + " item=" + this.item + " doit=" + this.doit + " x=" + this.x + " y=" + this.y + " width=" + this.width + " height=" + this.height + "}";
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.custom.CTabFolderEvent
 * JD-Core Version:    0.6.2
 */