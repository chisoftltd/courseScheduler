package org.eclipse.swt.custom;

import org.eclipse.swt.internal.SWTEventListener;

public abstract interface TextChangeListener extends SWTEventListener
{
  public abstract void textChanging(TextChangingEvent paramTextChangingEvent);

  public abstract void textChanged(TextChangedEvent paramTextChangedEvent);

  public abstract void textSet(TextChangedEvent paramTextChangedEvent);
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.custom.TextChangeListener
 * JD-Core Version:    0.6.2
 */