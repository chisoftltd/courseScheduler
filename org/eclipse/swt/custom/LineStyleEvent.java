package org.eclipse.swt.custom;

import org.eclipse.swt.events.TypedEvent;

public class LineStyleEvent extends TypedEvent
{
  public int lineOffset;
  public String lineText;
  public int[] ranges;
  public StyleRange[] styles;
  public int alignment;
  public int indent;
  public int wrapIndent;
  public boolean justify;
  public Bullet bullet;
  public int bulletIndex;
  public int[] tabStops;
  static final long serialVersionUID = 3906081274027192884L;

  public LineStyleEvent(StyledTextEvent paramStyledTextEvent)
  {
    super(paramStyledTextEvent);
    this.styles = paramStyledTextEvent.styles;
    this.ranges = paramStyledTextEvent.ranges;
    this.lineOffset = paramStyledTextEvent.detail;
    this.lineText = paramStyledTextEvent.text;
    this.alignment = paramStyledTextEvent.alignment;
    this.justify = paramStyledTextEvent.justify;
    this.indent = paramStyledTextEvent.indent;
    this.wrapIndent = paramStyledTextEvent.wrapIndent;
    this.bullet = paramStyledTextEvent.bullet;
    this.bulletIndex = paramStyledTextEvent.bulletIndex;
    this.tabStops = paramStyledTextEvent.tabStops;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.custom.LineStyleEvent
 * JD-Core Version:    0.6.2
 */