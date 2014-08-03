package org.eclipse.swt.browser;

import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.widgets.Widget;

public class LocationEvent extends TypedEvent
{
  public String location;
  public boolean top;
  public boolean doit;
  static final long serialVersionUID = 3906644198244299574L;

  public LocationEvent(Widget paramWidget)
  {
    super(paramWidget);
  }

  public String toString()
  {
    String str = super.toString();
    return str.substring(0, str.length() - 1) + " location=" + this.location + " top=" + this.top + " doit=" + this.doit + "}";
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.browser.LocationEvent
 * JD-Core Version:    0.6.2
 */