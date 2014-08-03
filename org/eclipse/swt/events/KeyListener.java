package org.eclipse.swt.events;

import org.eclipse.swt.internal.SWTEventListener;

public abstract interface KeyListener extends SWTEventListener
{
  public abstract void keyPressed(KeyEvent paramKeyEvent);

  public abstract void keyReleased(KeyEvent paramKeyEvent);
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.events.KeyListener
 * JD-Core Version:    0.6.2
 */