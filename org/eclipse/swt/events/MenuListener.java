package org.eclipse.swt.events;

import org.eclipse.swt.internal.SWTEventListener;

public abstract interface MenuListener extends SWTEventListener
{
  public abstract void menuHidden(MenuEvent paramMenuEvent);

  public abstract void menuShown(MenuEvent paramMenuEvent);
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.events.MenuListener
 * JD-Core Version:    0.6.2
 */