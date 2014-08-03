package org.eclipse.swt.events;

import org.eclipse.swt.internal.SWTEventListener;

public abstract interface FocusListener extends SWTEventListener
{
  public abstract void focusGained(FocusEvent paramFocusEvent);

  public abstract void focusLost(FocusEvent paramFocusEvent);
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.events.FocusListener
 * JD-Core Version:    0.6.2
 */