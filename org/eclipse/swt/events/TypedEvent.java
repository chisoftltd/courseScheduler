package org.eclipse.swt.events;

import org.eclipse.swt.internal.SWTEventObject;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Widget;

public class TypedEvent extends SWTEventObject
{
  public Display display;
  public Widget widget;
  public int time;
  public Object data;
  static final long serialVersionUID = 3257285846578377524L;

  public TypedEvent(Object paramObject)
  {
    super(paramObject);
  }

  public TypedEvent(Event paramEvent)
  {
    super(paramEvent.widget);
    this.display = paramEvent.display;
    this.widget = paramEvent.widget;
    this.time = paramEvent.time;
    this.data = paramEvent.data;
  }

  String getName()
  {
    String str = getClass().getName();
    int i = str.lastIndexOf('.');
    if (i == -1)
      return str;
    return str.substring(i + 1, str.length());
  }

  public String toString()
  {
    return getName() + "{" + this.widget + " time=" + this.time + " data=" + this.data + "}";
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.events.TypedEvent
 * JD-Core Version:    0.6.2
 */