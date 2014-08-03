package org.eclipse.swt.accessibility;

import org.eclipse.swt.internal.SWTEventObject;

public class AccessibleAttributeEvent extends SWTEventObject
{
  public int topMargin;
  public int bottomMargin;
  public int leftMargin;
  public int rightMargin;
  public int[] tabStops;
  public boolean justify;
  public int alignment;
  public int indent;
  public String[] attributes;
  static final long serialVersionUID = 2237016128901566049L;

  public AccessibleAttributeEvent(Object paramObject)
  {
    super(paramObject);
  }

  public String toString()
  {
    return "AccessibleAttributeEvent { topMargin=" + this.topMargin + " bottomMargin=" + this.bottomMargin + " leftMargin=" + this.leftMargin + " rightMargin=" + this.rightMargin + " tabStops=" + this.tabStops + " justify=" + this.justify + " alignment=" + this.alignment + " indent=" + this.indent + "}";
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.accessibility.AccessibleAttributeEvent
 * JD-Core Version:    0.6.2
 */