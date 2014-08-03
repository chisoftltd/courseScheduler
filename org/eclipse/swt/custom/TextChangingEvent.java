package org.eclipse.swt.custom;

import org.eclipse.swt.events.TypedEvent;

public class TextChangingEvent extends TypedEvent
{
  public int start;
  public String newText;
  public int replaceCharCount;
  public int newCharCount;
  public int replaceLineCount;
  public int newLineCount;
  static final long serialVersionUID = 3257290210114352439L;

  public TextChangingEvent(StyledTextContent paramStyledTextContent)
  {
    super(paramStyledTextContent);
  }

  TextChangingEvent(StyledTextContent paramStyledTextContent, StyledTextEvent paramStyledTextEvent)
  {
    super(paramStyledTextContent);
    this.start = paramStyledTextEvent.start;
    this.replaceCharCount = paramStyledTextEvent.replaceCharCount;
    this.newCharCount = paramStyledTextEvent.newCharCount;
    this.replaceLineCount = paramStyledTextEvent.replaceLineCount;
    this.newLineCount = paramStyledTextEvent.newLineCount;
    this.newText = paramStyledTextEvent.text;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.custom.TextChangingEvent
 * JD-Core Version:    0.6.2
 */