package org.eclipse.swt.accessibility;

import org.eclipse.swt.internal.SWTEventListener;

public abstract interface AccessibleActionListener extends SWTEventListener
{
  public abstract void getActionCount(AccessibleActionEvent paramAccessibleActionEvent);

  public abstract void doAction(AccessibleActionEvent paramAccessibleActionEvent);

  public abstract void getDescription(AccessibleActionEvent paramAccessibleActionEvent);

  public abstract void getKeyBinding(AccessibleActionEvent paramAccessibleActionEvent);

  public abstract void getName(AccessibleActionEvent paramAccessibleActionEvent);
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.accessibility.AccessibleActionListener
 * JD-Core Version:    0.6.2
 */