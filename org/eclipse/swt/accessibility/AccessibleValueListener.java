package org.eclipse.swt.accessibility;

import org.eclipse.swt.internal.SWTEventListener;

public abstract interface AccessibleValueListener extends SWTEventListener
{
  public abstract void getCurrentValue(AccessibleValueEvent paramAccessibleValueEvent);

  public abstract void setCurrentValue(AccessibleValueEvent paramAccessibleValueEvent);

  public abstract void getMaximumValue(AccessibleValueEvent paramAccessibleValueEvent);

  public abstract void getMinimumValue(AccessibleValueEvent paramAccessibleValueEvent);
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.accessibility.AccessibleValueListener
 * JD-Core Version:    0.6.2
 */