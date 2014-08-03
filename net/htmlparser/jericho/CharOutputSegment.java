package net.htmlparser.jericho;

import java.io.IOException;
import java.io.Writer;

final class CharOutputSegment
  implements OutputSegment
{
  private final int begin;
  private final int end;
  private final char ch;

  public CharOutputSegment(int paramInt1, int paramInt2, char paramChar)
  {
    this.begin = paramInt1;
    this.end = paramInt2;
    this.ch = paramChar;
  }

  public CharOutputSegment(Segment paramSegment, char paramChar)
  {
    this.begin = paramSegment.begin;
    this.end = paramSegment.end;
    this.ch = paramChar;
  }

  public int getBegin()
  {
    return this.begin;
  }

  public int getEnd()
  {
    return this.end;
  }

  public void writeTo(Writer paramWriter)
    throws IOException
  {
    appendTo(paramWriter);
  }

  public void appendTo(Appendable paramAppendable)
    throws IOException
  {
    paramAppendable.append(this.ch);
  }

  public long getEstimatedMaximumOutputLength()
  {
    return 1L;
  }

  public String toString()
  {
    return Character.toString(this.ch);
  }

  public String getDebugInfo()
  {
    return "Replace: (p" + this.begin + "-p" + this.end + ") " + this.ch;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.CharOutputSegment
 * JD-Core Version:    0.6.2
 */