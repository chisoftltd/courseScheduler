package org.eclipse.swt.custom;

import org.eclipse.swt.events.TypedEvent;

public class BidiSegmentEvent extends TypedEvent
{
  public int lineOffset;
  public String lineText;
  public int[] segments;
  public char[] segmentsChars;
  static final long serialVersionUID = 3257846571587547957L;

  BidiSegmentEvent(StyledTextEvent paramStyledTextEvent)
  {
    super(paramStyledTextEvent);
    this.lineOffset = paramStyledTextEvent.detail;
    this.lineText = paramStyledTextEvent.text;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.custom.BidiSegmentEvent
 * JD-Core Version:    0.6.2
 */