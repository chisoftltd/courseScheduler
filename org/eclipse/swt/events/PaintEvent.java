package org.eclipse.swt.events;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Event;

public final class PaintEvent extends TypedEvent
{
  public GC gc;
  public int x;
  public int y;
  public int width;
  public int height;
  public int count;
  static final long serialVersionUID = 3256446919205992497L;

  public PaintEvent(Event paramEvent)
  {
    super(paramEvent);
    this.gc = paramEvent.gc;
    this.x = paramEvent.x;
    this.y = paramEvent.y;
    this.width = paramEvent.width;
    this.height = paramEvent.height;
    this.count = paramEvent.count;
  }

  public String toString()
  {
    String str = super.toString();
    return str.substring(0, str.length() - 1) + " gc=" + this.gc + " x=" + this.x + " y=" + this.y + " width=" + this.width + " height=" + this.height + " count=" + this.count + "}";
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.events.PaintEvent
 * JD-Core Version:    0.6.2
 */