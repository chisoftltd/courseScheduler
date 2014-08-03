package org.eclipse.swt.browser;

import org.eclipse.swt.internal.SWTEventListener;

public abstract interface VisibilityWindowListener extends SWTEventListener
{
  public abstract void hide(WindowEvent paramWindowEvent);

  public abstract void show(WindowEvent paramWindowEvent);
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.browser.VisibilityWindowListener
 * JD-Core Version:    0.6.2
 */