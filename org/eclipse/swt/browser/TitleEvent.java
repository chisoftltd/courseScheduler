package org.eclipse.swt.browser;

import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.widgets.Widget;

public class TitleEvent extends TypedEvent
{
  public String title;
  static final long serialVersionUID = 4121132532906340919L;

  public TitleEvent(Widget paramWidget)
  {
    super(paramWidget);
  }

  public String toString()
  {
    String str = super.toString();
    return str.substring(0, str.length() - 1) + " title=" + this.title + "}";
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.browser.TitleEvent
 * JD-Core Version:    0.6.2
 */