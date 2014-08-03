package org.eclipse.swt.events;

import org.eclipse.swt.internal.SWTEventListener;

public abstract interface MouseTrackListener extends SWTEventListener
{
  public abstract void mouseEnter(MouseEvent paramMouseEvent);

  public abstract void mouseExit(MouseEvent paramMouseEvent);

  public abstract void mouseHover(MouseEvent paramMouseEvent);
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.events.MouseTrackListener
 * JD-Core Version:    0.6.2
 */