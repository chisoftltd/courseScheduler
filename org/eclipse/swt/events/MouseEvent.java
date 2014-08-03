package org.eclipse.swt.events;

import org.eclipse.swt.widgets.Event;

public class MouseEvent extends TypedEvent
{
  public int button;
  public int stateMask;
  public int x;
  public int y;
  public int count;
  static final long serialVersionUID = 3257288037011566898L;

  public MouseEvent(Event paramEvent)
  {
    super(paramEvent);
    this.x = paramEvent.x;
    this.y = paramEvent.y;
    this.button = paramEvent.button;
    this.stateMask = paramEvent.stateMask;
    this.count = paramEvent.count;
  }

  public String toString()
  {
    String str = super.toString();
    return str.substring(0, str.length() - 1) + " button=" + this.button + " stateMask=" + this.stateMask + " x=" + this.x + " y=" + this.y + " count=" + this.count + "}";
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.events.MouseEvent
 * JD-Core Version:    0.6.2
 */