package net.htmlparser.jericho;

import java.io.IOException;
import java.io.Writer;

public abstract interface CharStreamSource
{
  public abstract void writeTo(Writer paramWriter)
    throws IOException;

  public abstract void appendTo(Appendable paramAppendable)
    throws IOException;

  public abstract long getEstimatedMaximumOutputLength();

  public abstract String toString();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.CharStreamSource
 * JD-Core Version:    0.6.2
 */