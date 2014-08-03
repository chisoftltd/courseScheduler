package org.eclipse.swt.events;

import org.eclipse.swt.internal.SWTEventListener;

public abstract interface SelectionListener extends SWTEventListener
{
  public abstract void widgetSelected(SelectionEvent paramSelectionEvent);

  public abstract void widgetDefaultSelected(SelectionEvent paramSelectionEvent);
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.events.SelectionListener
 * JD-Core Version:    0.6.2
 */