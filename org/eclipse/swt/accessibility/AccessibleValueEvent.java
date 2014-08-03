package org.eclipse.swt.accessibility;

import org.eclipse.swt.internal.SWTEventObject;

public class AccessibleValueEvent extends SWTEventObject
{
  public Number value;
  static final long serialVersionUID = -465979079760740668L;

  public AccessibleValueEvent(Object paramObject)
  {
    super(paramObject);
  }

  public String toString()
  {
    return "AccessibleValueEvent {value=" + this.value + "}";
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.accessibility.AccessibleValueEvent
 * JD-Core Version:    0.6.2
 */