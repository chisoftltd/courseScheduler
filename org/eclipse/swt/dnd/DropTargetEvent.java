package org.eclipse.swt.dnd;

import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.widgets.Widget;

public class DropTargetEvent extends TypedEvent
{
  public int x;
  public int y;
  public int detail;
  public int operations;
  public int feedback;
  public Widget item;
  public TransferData currentDataType;
  public TransferData[] dataTypes;
  static final long serialVersionUID = 3256727264573338678L;

  public DropTargetEvent(DNDEvent paramDNDEvent)
  {
    super(paramDNDEvent);
    this.data = paramDNDEvent.data;
    this.x = paramDNDEvent.x;
    this.y = paramDNDEvent.y;
    this.detail = paramDNDEvent.detail;
    this.currentDataType = paramDNDEvent.dataType;
    this.dataTypes = paramDNDEvent.dataTypes;
    this.operations = paramDNDEvent.operations;
    this.feedback = paramDNDEvent.feedback;
    this.item = paramDNDEvent.item;
  }

  void updateEvent(DNDEvent paramDNDEvent)
  {
    paramDNDEvent.widget = this.widget;
    paramDNDEvent.time = this.time;
    paramDNDEvent.data = this.data;
    paramDNDEvent.x = this.x;
    paramDNDEvent.y = this.y;
    paramDNDEvent.detail = this.detail;
    paramDNDEvent.dataType = this.currentDataType;
    paramDNDEvent.dataTypes = this.dataTypes;
    paramDNDEvent.operations = this.operations;
    paramDNDEvent.feedback = this.feedback;
    paramDNDEvent.item = this.item;
  }

  public String toString()
  {
    String str = super.toString();
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(str.substring(0, str.length() - 1));
    localStringBuffer.append(" x=");
    localStringBuffer.append(this.x);
    localStringBuffer.append(" y=");
    localStringBuffer.append(this.y);
    localStringBuffer.append(" item=");
    localStringBuffer.append(this.item);
    localStringBuffer.append(" operations=");
    localStringBuffer.append(this.operations);
    localStringBuffer.append(" operation=");
    localStringBuffer.append(this.detail);
    localStringBuffer.append(" feedback=");
    localStringBuffer.append(this.feedback);
    localStringBuffer.append(" dataTypes={ ");
    if (this.dataTypes != null)
      for (int i = 0; i < this.dataTypes.length; i++)
      {
        localStringBuffer.append(this.dataTypes[i].type);
        localStringBuffer.append(' ');
      }
    localStringBuffer.append('}');
    localStringBuffer.append(" currentDataType=");
    localStringBuffer.append(this.currentDataType != null ? this.currentDataType.type : 48);
    localStringBuffer.append('}');
    return localStringBuffer.toString();
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.dnd.DropTargetEvent
 * JD-Core Version:    0.6.2
 */