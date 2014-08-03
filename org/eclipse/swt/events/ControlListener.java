package org.eclipse.swt.events;

import org.eclipse.swt.internal.SWTEventListener;

public abstract interface ControlListener extends SWTEventListener
{
  public abstract void controlMoved(ControlEvent paramControlEvent);

  public abstract void controlResized(ControlEvent paramControlEvent);
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.events.ControlListener
 * JD-Core Version:    0.6.2
 */