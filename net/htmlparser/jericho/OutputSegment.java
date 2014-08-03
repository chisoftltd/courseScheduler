package net.htmlparser.jericho;

import java.io.IOException;
import java.io.Writer;
import java.util.Comparator;

public abstract interface OutputSegment extends CharStreamSource
{
  public static final Comparator<OutputSegment> COMPARATOR = new OutputSegmentComparator();

  public abstract int getBegin();

  public abstract int getEnd();

  public abstract void writeTo(Writer paramWriter)
    throws IOException;

  public abstract void appendTo(Appendable paramAppendable)
    throws IOException;

  public abstract String toString();

  public abstract String getDebugInfo();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.OutputSegment
 * JD-Core Version:    0.6.2
 */