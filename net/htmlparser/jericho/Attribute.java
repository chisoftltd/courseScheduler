package net.htmlparser.jericho;

import java.io.IOException;

public final class Attribute extends Segment
{
  private final String key;
  private final Segment nameSegment;
  private final Segment valueSegment;
  private final Segment valueSegmentIncludingQuotes;
  static final String CHECKED = "checked";
  static final String CLASS = "class";
  static final String DISABLED = "disabled";
  static final String ID = "id";
  static final String MULTIPLE = "multiple";
  static final String NAME = "name";
  static final String SELECTED = "selected";
  static final String STYLE = "style";
  static final String TYPE = "type";
  static final String VALUE = "value";

  Attribute(Source paramSource, String paramString, Segment paramSegment)
  {
    this(paramSource, paramString, paramSegment, null, null);
  }

  Attribute(Source paramSource, String paramString, Segment paramSegment1, Segment paramSegment2, Segment paramSegment3)
  {
    super(paramSource, paramSegment1.getBegin(), paramSegment3 == null ? paramSegment1.getEnd() : paramSegment3.getEnd());
    this.key = paramString;
    this.nameSegment = paramSegment1;
    this.valueSegment = paramSegment2;
    this.valueSegmentIncludingQuotes = paramSegment3;
  }

  public String getKey()
  {
    return this.key;
  }

  public String getName()
  {
    return this.nameSegment.toString();
  }

  public Segment getNameSegment()
  {
    return this.nameSegment;
  }

  public boolean hasValue()
  {
    return this.valueSegment != null;
  }

  public String getValue()
  {
    return CharacterReference.decode(this.valueSegment, true);
  }

  public Segment getValueSegment()
  {
    return this.valueSegment;
  }

  public Segment getValueSegmentIncludingQuotes()
  {
    return this.valueSegmentIncludingQuotes;
  }

  public char getQuoteChar()
  {
    if (this.valueSegment == this.valueSegmentIncludingQuotes)
      return ' ';
    return this.source.charAt(this.valueSegmentIncludingQuotes.getBegin());
  }

  public String getDebugInfo()
  {
    StringBuilder localStringBuilder = new StringBuilder().append(this.key).append(super.getDebugInfo()).append(",name=").append(this.nameSegment.getDebugInfo());
    if (hasValue())
      localStringBuilder.append(",value=").append(this.valueSegment.getDebugInfo()).append('"').append(this.valueSegment).append('"').append(Config.NewLine);
    else
      localStringBuilder.append(",NO VALUE").append(Config.NewLine);
    return localStringBuilder.toString();
  }

  Tag appendTidy(Appendable paramAppendable, Tag paramTag)
    throws IOException
  {
    paramAppendable.append(' ').append(this.nameSegment);
    if (this.valueSegment != null)
    {
      paramAppendable.append("=\"");
      while ((paramTag != null) && (paramTag.begin < this.valueSegment.begin))
        paramTag = paramTag.getNextTag();
      if ((paramTag == null) || (paramTag.begin >= this.valueSegment.end))
      {
        appendTidyValue(paramAppendable, this.valueSegment);
      }
      else
      {
        int i = this.valueSegment.begin;
        while ((paramTag != null) && (paramTag.begin < this.valueSegment.end))
        {
          appendTidyValue(paramAppendable, new Segment(this.source, i, paramTag.begin));
          if (paramTag.end > this.valueSegment.end)
          {
            paramAppendable.append(new Segment(this.source, paramTag.begin, i = this.valueSegment.end));
            break;
          }
          paramAppendable.append(paramTag);
          i = paramTag.end;
          paramTag = paramTag.getNextTag();
        }
        if (i < this.valueSegment.end)
          appendTidyValue(paramAppendable, new Segment(this.source, i, this.valueSegment.end));
      }
      paramAppendable.append('"');
    }
    return paramTag;
  }

  private static void appendTidyValue(Appendable paramAppendable, CharSequence paramCharSequence)
    throws IOException
  {
    CharacterReference.appendEncode(paramAppendable, CharacterReference.decode(paramCharSequence, true), false);
  }

  static Appendable appendHTML(Appendable paramAppendable, CharSequence paramCharSequence1, CharSequence paramCharSequence2)
    throws IOException
  {
    paramAppendable.append(' ').append(paramCharSequence1);
    if (paramCharSequence2 != null)
    {
      paramAppendable.append("=\"");
      CharacterReference.appendEncode(paramAppendable, paramCharSequence2, false);
      paramAppendable.append('"');
    }
    return paramAppendable;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.Attribute
 * JD-Core Version:    0.6.2
 */