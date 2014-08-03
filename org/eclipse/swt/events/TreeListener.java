package org.eclipse.swt.events;

import org.eclipse.swt.internal.SWTEventListener;

public abstract interface TreeListener extends SWTEventListener
{
  public abstract void treeCollapsed(TreeEvent paramTreeEvent);

  public abstract void treeExpanded(TreeEvent paramTreeEvent);
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.events.TreeListener
 * JD-Core Version:    0.6.2
 */