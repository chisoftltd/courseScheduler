package org.eclipse.swt.accessibility;

import org.eclipse.swt.internal.SWTEventListener;

public abstract interface AccessibleTextListener extends SWTEventListener
{
  public abstract void getCaretOffset(AccessibleTextEvent paramAccessibleTextEvent);

  public abstract void getSelectionRange(AccessibleTextEvent paramAccessibleTextEvent);
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.accessibility.AccessibleTextListener
 * JD-Core Version:    0.6.2
 */