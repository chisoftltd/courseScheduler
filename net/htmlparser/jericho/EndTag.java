package net.htmlparser.jericho;

public final class EndTag extends Tag
{
  private final EndTagType endTagType;

  EndTag(Source paramSource, int paramInt1, int paramInt2, EndTagType paramEndTagType, String paramString)
  {
    super(paramSource, paramInt1, paramInt2, paramString);
    this.endTagType = paramEndTagType;
  }

  public Element getElement()
  {
    if (this.element != Element.NOT_CACHED)
      return this.element;
    StartTag localStartTag;
    for (int i = this.begin; i != 0; i = localStartTag.begin)
    {
      localStartTag = this.source.getPreviousStartTag(i - 1);
      if (localStartTag == null)
        break;
      Element localElement = localStartTag.getElement();
      if (localElement.getEndTag() == this)
        return localElement;
    }
    return this.element = null;
  }

  public EndTagType getEndTagType()
  {
    return this.endTagType;
  }

  public TagType getTagType()
  {
    return this.endTagType;
  }

  public boolean isUnregistered()
  {
    return this.endTagType == EndTagType.UNREGISTERED;
  }

  public String tidy()
  {
    return toString();
  }

  public static String generateHTML(String paramString)
  {
    return EndTagType.NORMAL.generateHTML(paramString);
  }

  public String getDebugInfo()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this).append(' ');
    if (this.endTagType != EndTagType.NORMAL)
      localStringBuilder.append('(').append(this.endTagType.getDescription()).append(") ");
    localStringBuilder.append(super.getDebugInfo());
    return localStringBuilder.toString();
  }

  static EndTag getPrevious(Source paramSource, int paramInt, String paramString, EndTagType paramEndTagType)
  {
    if (paramString == null)
      return (EndTag)Tag.getPreviousTag(paramSource, paramInt, paramEndTagType);
    if (paramString.length() == 0)
      throw new IllegalArgumentException("name argument must not be zero length");
    String str = "</" + paramString;
    try
    {
      ParseText localParseText = paramSource.getParseText();
      int i = paramInt;
      do
      {
        i = localParseText.lastIndexOf(str, i);
        if (i == -1)
          return null;
        EndTag localEndTag = (EndTag)paramSource.getTagAt(i);
        if ((localEndTag != null) && (localEndTag.getEndTagType() == paramEndTagType) && (paramString.equals(localEndTag.getName())))
          return localEndTag;
        i--;
      }
      while (i >= 0);
    }
    catch (IndexOutOfBoundsException localIndexOutOfBoundsException)
    {
      throw localIndexOutOfBoundsException;
    }
    return null;
  }

  static EndTag getNext(Source paramSource, int paramInt, String paramString, EndTagType paramEndTagType)
  {
    if (paramString == null)
      return (EndTag)Tag.getNextTag(paramSource, paramInt, paramEndTagType);
    if (paramString.length() == 0)
      throw new IllegalArgumentException("name argument must not be zero length");
    String str = "</" + paramString;
    try
    {
      ParseText localParseText = paramSource.getParseText();
      int i = paramInt;
      do
      {
        i = localParseText.indexOf(str, i);
        if (i == -1)
          return null;
        EndTag localEndTag = (EndTag)paramSource.getTagAt(i);
        if ((localEndTag != null) && (localEndTag.getEndTagType() == paramEndTagType) && (paramString.equals(localEndTag.getName())))
          return localEndTag;
        i++;
      }
      while (i < paramSource.end);
    }
    catch (IndexOutOfBoundsException localIndexOutOfBoundsException)
    {
    }
    return null;
  }

  static EndTag getPrevious(Source paramSource, int paramInt)
  {
    while (true)
    {
      Tag localTag = Tag.getPreviousTag(paramSource, paramInt);
      if (localTag == null)
        return null;
      if ((localTag instanceof EndTag))
        return (EndTag)localTag;
      paramInt--;
    }
  }

  static EndTag getNext(Source paramSource, int paramInt)
  {
    while (true)
    {
      Tag localTag = Tag.getNextTag(paramSource, paramInt);
      if (localTag == null)
        return null;
      if ((localTag instanceof EndTag))
        return (EndTag)localTag;
      paramInt++;
    }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.EndTag
 * JD-Core Version:    0.6.2
 */