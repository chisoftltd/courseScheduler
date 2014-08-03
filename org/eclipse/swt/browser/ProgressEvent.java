package org.eclipse.swt.browser;

import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.widgets.Widget;

public class ProgressEvent extends TypedEvent
{
  public int current;
  public int total;
  static final long serialVersionUID = 3977018427045393972L;

  public ProgressEvent(Widget paramWidget)
  {
    super(paramWidget);
  }

  public String toString()
  {
    String str = super.toString();
    return str.substring(0, str.length() - 1) + " current=" + this.current + " total=" + this.total + "}";
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.browser.ProgressEvent
 * JD-Core Version:    0.6.2
 */