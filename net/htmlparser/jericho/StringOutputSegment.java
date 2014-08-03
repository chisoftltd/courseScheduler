package net.htmlparser.jericho;

import java.io.IOException;
import java.io.Writer;

final class StringOutputSegment
  implements OutputSegment
{
  private final int begin;
  private final int end;
  private final CharSequence text;

  public StringOutputSegment(int paramInt1, int paramInt2, CharSequence paramCharSequence)
  {
    this.begin = paramInt1;
    this.end = paramInt2;
    this.text = (paramCharSequence == null ? "" : paramCharSequence);
  }

  public StringOutputSegment(Segment paramSegment, CharSequence paramCharSequence)
  {
    this(paramSegment.begin, paramSegment.end, paramCharSequence);
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
    paramAppendable.append(this.text);
  }

  public long getEstimatedMaximumOutputLength()
  {
    return this.text.length();
  }

  public String toString()
  {
    return this.text.toString();
  }

  public String getDebugInfo()
  {
    return "Replace: (p" + this.begin + "-p" + this.end + ") " + this.text;
  }

  public void output(Writer paramWriter)
    throws IOException
  {
    writeTo(paramWriter);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.StringOutputSegment
 * JD-Core Version:    0.6.2
 */