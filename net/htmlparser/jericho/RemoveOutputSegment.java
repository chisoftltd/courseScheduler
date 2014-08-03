package net.htmlparser.jericho;

import java.io.Writer;

final class RemoveOutputSegment
  implements OutputSegment
{
  private final int begin;
  private final int end;

  public RemoveOutputSegment(int paramInt1, int paramInt2)
  {
    this.begin = paramInt1;
    this.end = paramInt2;
  }

  public RemoveOutputSegment(Segment paramSegment)
  {
    this(paramSegment.begin, paramSegment.end);
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
  {
    appendTo(paramWriter);
  }

  public void appendTo(Appendable paramAppendable)
  {
  }

  public long getEstimatedMaximumOutputLength()
  {
    return 0L;
  }

  public String toString()
  {
    return "";
  }

  public String getDebugInfo()
  {
    return "Remove: (p" + this.begin + "-p" + this.end + ')';
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.RemoveOutputSegment
 * JD-Core Version:    0.6.2
 */