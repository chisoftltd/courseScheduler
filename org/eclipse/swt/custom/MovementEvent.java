package org.eclipse.swt.custom;

import org.eclipse.swt.events.TypedEvent;

public class MovementEvent extends TypedEvent
{
  public int lineOffset;
  public String lineText;
  public int offset;
  public int newOffset;
  public int movement;
  static final long serialVersionUID = 3978765487853324342L;

  public MovementEvent(StyledTextEvent paramStyledTextEvent)
  {
    super(paramStyledTextEvent);
    this.lineOffset = paramStyledTextEvent.detail;
    this.lineText = paramStyledTextEvent.text;
    this.movement = paramStyledTextEvent.count;
    this.offset = paramStyledTextEvent.start;
    this.newOffset = paramStyledTextEvent.end;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.custom.MovementEvent
 * JD-Core Version:    0.6.2
 */