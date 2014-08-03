package org.eclipse.swt.browser;

import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.widgets.Widget;

public class StatusTextEvent extends TypedEvent
{
  public String text;
  static final long serialVersionUID = 3258407348371600439L;

  public StatusTextEvent(Widget paramWidget)
  {
    super(paramWidget);
  }

  public String toString()
  {
    String str = super.toString();
    return str.substring(0, str.length() - 1) + " text=" + this.text + "}";
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.browser.StatusTextEvent
 * JD-Core Version:    0.6.2
 */