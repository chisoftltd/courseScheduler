package org.eclipse.swt.accessibility;

import org.eclipse.swt.internal.SWTEventObject;

public class AccessibleEvent extends SWTEventObject
{
  public int childID;
  public String result;
  static final long serialVersionUID = 3257567304224026934L;

  public AccessibleEvent(Object paramObject)
  {
    super(paramObject);
  }

  public String toString()
  {
    return "AccessibleEvent {childID=" + this.childID + " result=" + this.result + "}";
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.accessibility.AccessibleEvent
 * JD-Core Version:    0.6.2
 */