package org.eclipse.swt.events;

import org.eclipse.swt.widgets.Event;

public final class TraverseEvent extends KeyEvent
{
  public int detail;
  static final long serialVersionUID = 3257565105301239349L;

  public TraverseEvent(Event paramEvent)
  {
    super(paramEvent);
    this.detail = paramEvent.detail;
  }

  public String toString()
  {
    String str = super.toString();
    return str.substring(0, str.length() - 1) + " detail=" + this.detail + "}";
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.events.TraverseEvent
 * JD-Core Version:    0.6.2
 */