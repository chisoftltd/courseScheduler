package org.eclipse.swt.events;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Widget;

public class SelectionEvent extends TypedEvent
{
  public Widget item;
  public int detail;
  public int x;
  public int y;
  public int width;
  public int height;
  public int stateMask;
  public String text;
  public boolean doit;
  static final long serialVersionUID = 3976735856884987953L;

  public SelectionEvent(Event paramEvent)
  {
    super(paramEvent);
    this.item = paramEvent.item;
    this.x = paramEvent.x;
    this.y = paramEvent.y;
    this.width = paramEvent.width;
    this.height = paramEvent.height;
    this.detail = paramEvent.detail;
    this.stateMask = paramEvent.stateMask;
    this.text = paramEvent.text;
    this.doit = paramEvent.doit;
  }

  public String toString()
  {
    String str = super.toString();
    return str.substring(0, str.length() - 1) + " item=" + this.item + " detail=" + this.detail + " x=" + this.x + " y=" + this.y + " width=" + this.width + " height=" + this.height + " stateMask=" + this.stateMask + " text=" + this.text + " doit=" + this.doit + "}";
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.events.SelectionEvent
 * JD-Core Version:    0.6.2
 */