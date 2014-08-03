package net.htmlparser.jericho;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

class AttributesOutputSegment
  implements OutputSegment
{
  private final int begin;
  private final int end;
  private final Map<String, String> map;

  public AttributesOutputSegment(Attributes paramAttributes, boolean paramBoolean)
  {
    this(paramAttributes, paramAttributes.getMap(paramBoolean));
  }

  public AttributesOutputSegment(Attributes paramAttributes, Map<String, String> paramMap)
  {
    if ((paramMap == null) || (paramAttributes == null))
      throw new IllegalArgumentException("both arguments must be non-null");
    this.begin = paramAttributes.getBegin();
    this.end = paramAttributes.getEnd();
    this.map = paramMap;
  }

  public int getBegin()
  {
    return this.begin;
  }

  public int getEnd()
  {
    return this.end;
  }

  public Map<String, String> getMap()
  {
    return this.map;
  }

  public void writeTo(Writer paramWriter)
    throws IOException
  {
    Attributes.appendHTML(paramWriter, this.map);
  }

  public void appendTo(Appendable paramAppendable)
    throws IOException
  {
    Attributes.appendHTML(paramAppendable, this.map);
  }

  public long getEstimatedMaximumOutputLength()
  {
    return (this.end - this.begin) * 2;
  }

  public String toString()
  {
    return Attributes.generateHTML(this.map);
  }

  public String getDebugInfo()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("(p").append(this.begin).append("-p").append(this.end).append("):");
    try
    {
      appendTo(localStringBuilder);
    }
    catch (IOException localIOException)
    {
      throw new RuntimeException(localIOException);
    }
    return localStringBuilder.toString();
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.AttributesOutputSegment
 * JD-Core Version:    0.6.2
 */