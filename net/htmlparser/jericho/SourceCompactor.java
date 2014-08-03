package net.htmlparser.jericho;

import java.io.IOException;
import java.io.Writer;

public final class SourceCompactor
  implements CharStreamSource
{
  private final Segment segment;
  private String newLine = null;

  public SourceCompactor(Segment paramSegment)
  {
    this.segment = paramSegment;
  }

  public void writeTo(Writer paramWriter)
    throws IOException
  {
    appendTo(paramWriter);
    paramWriter.flush();
  }

  public void appendTo(Appendable paramAppendable)
    throws IOException
  {
    new SourceFormatter(this.segment).setTidyTags(true).setNewLine(this.newLine).setRemoveLineBreaks(true).appendTo(paramAppendable);
  }

  public long getEstimatedMaximumOutputLength()
  {
    return this.segment.length();
  }

  public String toString()
  {
    return CharStreamSourceUtil.toString(this);
  }

  public SourceCompactor setNewLine(String paramString)
  {
    this.newLine = paramString;
    return this;
  }

  public String getNewLine()
  {
    if (this.newLine == null)
      this.newLine = this.segment.source.getBestGuessNewLine();
    return this.newLine;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.SourceCompactor
 * JD-Core Version:    0.6.2
 */