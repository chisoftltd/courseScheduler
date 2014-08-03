package org.eclipse.swt.custom;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Event;

class StyledTextEvent extends Event
{
  int[] ranges;
  StyleRange[] styles;
  int alignment;
  int indent;
  int wrapIndent;
  boolean justify;
  Bullet bullet;
  int bulletIndex;
  int[] tabStops;
  Color lineBackground;
  int[] segments;
  char[] segmentsChars;
  int replaceCharCount;
  int newCharCount;
  int replaceLineCount;
  int newLineCount;
  int x;
  int y;
  int ascent;
  int descent;
  StyleRange style;

  StyledTextEvent(StyledTextContent paramStyledTextContent)
  {
    this.data = paramStyledTextContent;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.custom.StyledTextEvent
 * JD-Core Version:    0.6.2
 */