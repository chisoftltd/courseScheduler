package org.eclipse.swt.events;

import org.eclipse.swt.widgets.Event;

public final class MenuDetectEvent extends TypedEvent
{
  public int x;
  public int y;
  public boolean doit;
  private static final long serialVersionUID = -3061660596590828941L;

  public MenuDetectEvent(Event paramEvent)
  {
    super(paramEvent);
    this.x = paramEvent.x;
    this.y = paramEvent.y;
    this.doit = paramEvent.doit;
  }

  public String toString()
  {
    String str = super.toString();
    return str.substring(0, str.length() - 1) + " x=" + this.x + " y=" + this.y + " doit=" + this.doit + "}";
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.events.MenuDetectEvent
 * JD-Core Version:    0.6.2
 */