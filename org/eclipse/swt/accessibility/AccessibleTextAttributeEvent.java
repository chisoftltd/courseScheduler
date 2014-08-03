package org.eclipse.swt.accessibility;

import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.swt.internal.SWTEventObject;

public class AccessibleTextAttributeEvent extends SWTEventObject
{
  public int offset;
  public int start;
  public int end;
  public TextStyle textStyle;
  public String[] attributes;
  static final long serialVersionUID = 0L;

  public AccessibleTextAttributeEvent(Object paramObject)
  {
    super(paramObject);
  }

  public String toString()
  {
    return "AccessibleAttributeEvent { offset=" + this.offset + " startOffset=" + this.start + " endOffset=" + this.end + " textStyle=" + this.textStyle + "}";
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.accessibility.AccessibleTextAttributeEvent
 * JD-Core Version:    0.6.2
 */