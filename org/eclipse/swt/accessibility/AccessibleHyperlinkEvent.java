package org.eclipse.swt.accessibility;

import org.eclipse.swt.internal.SWTEventObject;

public class AccessibleHyperlinkEvent extends SWTEventObject
{
  public Accessible accessible;
  public String result;
  public int index;
  static final long serialVersionUID = 6253098373844074544L;

  public AccessibleHyperlinkEvent(Object paramObject)
  {
    super(paramObject);
  }

  public String toString()
  {
    return "AccessibleHyperlinkEvent {accessible=" + this.accessible + " string=" + this.result + " index=" + this.index + "}";
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.accessibility.AccessibleHyperlinkEvent
 * JD-Core Version:    0.6.2
 */