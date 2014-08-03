package org.eclipse.swt.accessibility;

import org.eclipse.swt.internal.SWTEventObject;

public class AccessibleActionEvent extends SWTEventObject
{
  public String result;
  public int count;
  public int index;
  public boolean localized;
  static final long serialVersionUID = 2849066792640153087L;

  public AccessibleActionEvent(Object paramObject)
  {
    super(paramObject);
  }

  public String toString()
  {
    return "AccessibleActionEvent {string=" + this.result + " count=" + this.count + " index=" + this.index + "}";
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.accessibility.AccessibleActionEvent
 * JD-Core Version:    0.6.2
 */