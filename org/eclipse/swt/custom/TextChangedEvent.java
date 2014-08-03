package org.eclipse.swt.custom;

import org.eclipse.swt.events.TypedEvent;

public class TextChangedEvent extends TypedEvent
{
  static final long serialVersionUID = 3258696524257835065L;

  public TextChangedEvent(StyledTextContent paramStyledTextContent)
  {
    super(paramStyledTextContent);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.custom.TextChangedEvent
 * JD-Core Version:    0.6.2
 */