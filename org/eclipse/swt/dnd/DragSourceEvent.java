package org.eclipse.swt.dnd;

import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.graphics.Image;

public class DragSourceEvent extends TypedEvent
{
  public int detail;
  public boolean doit;
  public int x;
  public int y;
  public TransferData dataType;
  public Image image;
  public int offsetX;
  public int offsetY;
  static final long serialVersionUID = 3257002142513770808L;

  public DragSourceEvent(DNDEvent paramDNDEvent)
  {
    super(paramDNDEvent);
    this.data = paramDNDEvent.data;
    this.detail = paramDNDEvent.detail;
    this.doit = paramDNDEvent.doit;
    this.dataType = paramDNDEvent.dataType;
    this.x = paramDNDEvent.x;
    this.y = paramDNDEvent.y;
    this.image = paramDNDEvent.image;
    this.offsetX = paramDNDEvent.offsetX;
    this.offsetY = paramDNDEvent.offsetY;
  }

  void updateEvent(DNDEvent paramDNDEvent)
  {
    paramDNDEvent.widget = this.widget;
    paramDNDEvent.time = this.time;
    paramDNDEvent.data = this.data;
    paramDNDEvent.detail = this.detail;
    paramDNDEvent.doit = this.doit;
    paramDNDEvent.dataType = this.dataType;
    paramDNDEvent.x = this.x;
    paramDNDEvent.y = this.y;
    paramDNDEvent.image = this.image;
    paramDNDEvent.offsetX = this.offsetX;
    paramDNDEvent.offsetY = this.offsetY;
  }

  public String toString()
  {
    String str = super.toString();
    return str.substring(0, str.length() - 1) + " operation=" + this.detail + " type=" + (this.dataType != null ? this.dataType.type : 0) + " doit=" + this.doit + "}";
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.dnd.DragSourceEvent
 * JD-Core Version:    0.6.2
 */