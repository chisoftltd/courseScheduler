package org.eclipse.swt.custom;

import org.eclipse.swt.internal.SWTEventListener;

public abstract interface MovementListener extends SWTEventListener
{
  public abstract void getNextOffset(MovementEvent paramMovementEvent);

  public abstract void getPreviousOffset(MovementEvent paramMovementEvent);
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.custom.MovementListener
 * JD-Core Version:    0.6.2
 */