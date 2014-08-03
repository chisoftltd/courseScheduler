package org.eclipse.swt.events;

import org.eclipse.swt.widgets.Event;

public final class ShellEvent extends TypedEvent
{
  public boolean doit;
  static final long serialVersionUID = 3257569490479888441L;

  public ShellEvent(Event paramEvent)
  {
    super(paramEvent);
    this.doit = paramEvent.doit;
  }

  public String toString()
  {
    String str = super.toString();
    return str.substring(0, str.length() - 1) + " doit=" + this.doit + "}";
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.events.ShellEvent
 * JD-Core Version:    0.6.2
 */