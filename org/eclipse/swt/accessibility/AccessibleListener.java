package org.eclipse.swt.accessibility;

import org.eclipse.swt.internal.SWTEventListener;

public abstract interface AccessibleListener extends SWTEventListener
{
  public abstract void getName(AccessibleEvent paramAccessibleEvent);

  public abstract void getHelp(AccessibleEvent paramAccessibleEvent);

  public abstract void getKeyboardShortcut(AccessibleEvent paramAccessibleEvent);

  public abstract void getDescription(AccessibleEvent paramAccessibleEvent);
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.accessibility.AccessibleListener
 * JD-Core Version:    0.6.2
 */