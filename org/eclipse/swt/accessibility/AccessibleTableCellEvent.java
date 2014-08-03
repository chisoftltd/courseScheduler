package org.eclipse.swt.accessibility;

import org.eclipse.swt.internal.SWTEventObject;

public class AccessibleTableCellEvent extends SWTEventObject
{
  public Accessible accessible;
  public Accessible[] accessibles;
  public boolean isSelected;
  public int count;
  public int index;
  static final long serialVersionUID = 7231059449172889781L;

  public AccessibleTableCellEvent(Object paramObject)
  {
    super(paramObject);
  }

  public String toString()
  {
    return "AccessibleTableCellEvent { accessibles=" + this.accessibles + " isSelected=" + this.isSelected + " count=" + this.count + " index=" + this.index + "}";
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.accessibility.AccessibleTableCellEvent
 * JD-Core Version:    0.6.2
 */