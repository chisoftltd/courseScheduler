package net.htmlparser.jericho;

import java.io.IOException;
import java.io.Writer;

final class BlankOutputSegment
  implements OutputSegment
{
  private final int begin;
  private final int end;

  public BlankOutputSegment(int paramInt1, int paramInt2)
  {
    this.begin = paramInt1;
    this.end = paramInt2;
  }

  public BlankOutputSegment(Segment paramSegment)
  {
    this(paramSegment.getBegin(), paramSegment.getEnd());
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
    for (int i = this.begin; i < this.end; i++)
      paramAppendable.append(' ');
  }

  public long getEstimatedMaximumOutputLength()
  {
    return this.end - this.begin;
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder(this.end - this.begin);
    for (int i = this.begin; i < this.end; i++)
      localStringBuilder.append(' ');
    return localStringBuilder.toString();
  }

  public String getDebugInfo()
  {
    return "Replace with Spaces: (p" + this.begin + "-p" + this.end + ')';
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.BlankOutputSegment
 * JD-Core Version:    0.6.2
 */