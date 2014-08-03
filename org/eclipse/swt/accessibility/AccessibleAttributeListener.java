package org.eclipse.swt.accessibility;

import org.eclipse.swt.internal.SWTEventListener;

public abstract interface AccessibleAttributeListener extends SWTEventListener
{
  public abstract void getAttributes(AccessibleAttributeEvent paramAccessibleAttributeEvent);

  public abstract void getTextAttributes(AccessibleTextAttributeEvent paramAccessibleTextAttributeEvent);
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.accessibility.AccessibleAttributeListener
 * JD-Core Version:    0.6.2
 */