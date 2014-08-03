package org.eclipse.swt.events;

import org.eclipse.swt.internal.SWTEventListener;

public abstract interface ExpandListener extends SWTEventListener
{
  public abstract void itemCollapsed(ExpandEvent paramExpandEvent);

  public abstract void itemExpanded(ExpandEvent paramExpandEvent);
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.events.ExpandListener
 * JD-Core Version:    0.6.2
 */