package org.eclipse.swt.custom;

import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.graphics.GC;

public class PaintObjectEvent extends TypedEvent
{
  public GC gc;
  public int x;
  public int y;
  public int ascent;
  public int descent;
  public StyleRange style;
  public Bullet bullet;
  public int bulletIndex;
  static final long serialVersionUID = 3906081274027192855L;

  public PaintObjectEvent(StyledTextEvent paramStyledTextEvent)
  {
    super(paramStyledTextEvent);
    this.gc = paramStyledTextEvent.gc;
    this.x = paramStyledTextEvent.x;
    this.y = paramStyledTextEvent.y;
    this.ascent = paramStyledTextEvent.ascent;
    this.descent = paramStyledTextEvent.descent;
    this.style = paramStyledTextEvent.style;
    this.bullet = paramStyledTextEvent.bullet;
    this.bulletIndex = paramStyledTextEvent.bulletIndex;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.custom.PaintObjectEvent
 * JD-Core Version:    0.6.2
 */