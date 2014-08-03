package org.eclipse.swt.events;

import org.eclipse.swt.widgets.Event;

public final class VerifyEvent extends KeyEvent
{
  public int start;
  public int end;
  public String text;
  static final long serialVersionUID = 3257003246269577014L;

  public VerifyEvent(Event paramEvent)
  {
    super(paramEvent);
    this.start = paramEvent.start;
    this.end = paramEvent.end;
    this.text = paramEvent.text;
  }

  public String toString()
  {
    String str = super.toString();
    return str.substring(0, str.length() - 1) + " start=" + this.start + " end=" + this.end + " text=" + this.text + "}";
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.events.VerifyEvent
 * JD-Core Version:    0.6.2
 */