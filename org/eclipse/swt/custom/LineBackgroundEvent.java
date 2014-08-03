package org.eclipse.swt.custom;

import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.graphics.Color;

public class LineBackgroundEvent extends TypedEvent
{
  public int lineOffset;
  public String lineText;
  public Color lineBackground;
  static final long serialVersionUID = 3978711687853324342L;

  public LineBackgroundEvent(StyledTextEvent paramStyledTextEvent)
  {
    super(paramStyledTextEvent);
    this.lineOffset = paramStyledTextEvent.detail;
    this.lineText = paramStyledTextEvent.text;
    this.lineBackground = paramStyledTextEvent.lineBackground;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.custom.LineBackgroundEvent
 * JD-Core Version:    0.6.2
 */