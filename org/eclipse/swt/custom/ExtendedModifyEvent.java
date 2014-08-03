package org.eclipse.swt.custom;

import org.eclipse.swt.events.TypedEvent;

public final class ExtendedModifyEvent extends TypedEvent
{
  public int start;
  public int length;
  public String replacedText;
  static final long serialVersionUID = 3258696507027830832L;

  public ExtendedModifyEvent(StyledTextEvent paramStyledTextEvent)
  {
    super(paramStyledTextEvent);
    this.start = paramStyledTextEvent.start;
    this.length = (paramStyledTextEvent.end - paramStyledTextEvent.start);
    this.replacedText = paramStyledTextEvent.text;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.custom.ExtendedModifyEvent
 * JD-Core Version:    0.6.2
 */