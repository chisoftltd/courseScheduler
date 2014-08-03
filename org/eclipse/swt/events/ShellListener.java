package org.eclipse.swt.events;

import org.eclipse.swt.internal.SWTEventListener;

public abstract interface ShellListener extends SWTEventListener
{
  public abstract void shellActivated(ShellEvent paramShellEvent);

  public abstract void shellClosed(ShellEvent paramShellEvent);

  public abstract void shellDeactivated(ShellEvent paramShellEvent);

  public abstract void shellDeiconified(ShellEvent paramShellEvent);

  public abstract void shellIconified(ShellEvent paramShellEvent);
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.events.ShellListener
 * JD-Core Version:    0.6.2
 */