package org.eclipse.swt.accessibility;

import org.eclipse.swt.internal.SWTEventListener;

public abstract interface AccessibleControlListener extends SWTEventListener
{
  public abstract void getChildAtPoint(AccessibleControlEvent paramAccessibleControlEvent);

  public abstract void getLocation(AccessibleControlEvent paramAccessibleControlEvent);

  public abstract void getChild(AccessibleControlEvent paramAccessibleControlEvent);

  public abstract void getChildCount(AccessibleControlEvent paramAccessibleControlEvent);

  public abstract void getDefaultAction(AccessibleControlEvent paramAccessibleControlEvent);

  public abstract void getFocus(AccessibleControlEvent paramAccessibleControlEvent);

  public abstract void getRole(AccessibleControlEvent paramAccessibleControlEvent);

  public abstract void getSelection(AccessibleControlEvent paramAccessibleControlEvent);

  public abstract void getState(AccessibleControlEvent paramAccessibleControlEvent);

  public abstract void getValue(AccessibleControlEvent paramAccessibleControlEvent);

  public abstract void getChildren(AccessibleControlEvent paramAccessibleControlEvent);
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.accessibility.AccessibleControlListener
 * JD-Core Version:    0.6.2
 */