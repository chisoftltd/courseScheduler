package org.eclipse.swt.custom;

import org.eclipse.swt.events.TypedEvent;

public class CaretEvent extends TypedEvent
{
  public int caretOffset;
  static final long serialVersionUID = 3257846571587545489L;

  CaretEvent(StyledTextEvent paramStyledTextEvent)
  {
    super(paramStyledTextEvent);
    this.caretOffset = paramStyledTextEvent.end;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.custom.CaretEvent
 * JD-Core Version:    0.6.2
 */