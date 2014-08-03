package net.htmlparser.jericho;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StartTag extends Tag
{
  private final Attributes attributes;
  final StartTagType startTagType;

  StartTag(Source paramSource, int paramInt1, int paramInt2, StartTagType paramStartTagType, String paramString, Attributes paramAttributes)
  {
    super(paramSource, paramInt1, paramInt2, paramString);
    this.attributes = paramAttributes;
    this.startTagType = paramStartTagType;
  }

  StartTag()
  {
    this.attributes = null;
    this.startTagType = null;
  }

  public Element getElement()
  {
    if (this.element == Element.NOT_CACHED)
    {
      EndTag localEndTag = getEndTagInternal();
      this.element = new Element(this.source, this, localEndTag);
      if (localEndTag != null)
      {
        if ((localEndTag.element != Element.NOT_CACHED) && (this.source.logger.isInfoEnabled()) && (!this.element.equals(localEndTag.element)))
          this.source.logger.info(" terminates more than one element");
        localEndTag.element = this.element;
      }
    }
    return this.element;
  }

  public boolean isEmptyElementTag()
  {
    return (isSyntacticalEmptyElementTag()) && (!HTMLElements.isClosingSlashIgnored(this.name));
  }

  public boolean isSyntacticalEmptyElementTag()
  {
    return (this.startTagType == StartTagType.NORMAL) && (this.source.charAt(this.end - 2) == '/');
  }

  public StartTagType getStartTagType()
  {
    return this.startTagType;
  }

  public TagType getTagType()
  {
    return this.startTagType;
  }

  public Attributes getAttributes()
  {
    return this.attributes;
  }

  public String getAttributeValue(String paramString)
  {
    return this.attributes == null ? null : this.attributes.getValue(paramString);
  }

  public Attributes parseAttributes()
  {
    return parseAttributes(Attributes.getDefaultMaxErrorCount());
  }

  public Attributes parseAttributes(int paramInt)
  {
    if (this.attributes != null)
      return this.attributes;
    int i = this.end - this.startTagType.getClosingDelimiter().length();
    int j = this.begin + 1 + this.name.length();
    while (!isXMLNameStartChar(this.source.charAt(j)))
    {
      j++;
      if (j == i)
        return null;
    }
    return Attributes.construct(this.source, this.begin, j, i, this.startTagType, this.name, paramInt);
  }

  public Segment getTagContent()
  {
    return new Segment(this.source, this.begin + 1 + this.name.length(), this.end - this.startTagType.getClosingDelimiter().length());
  }

  public FormControl getFormControl()
  {
    return getElement().getFormControl();
  }

  public boolean isEndTagForbidden()
  {
    if (getStartTagType() != StartTagType.NORMAL)
      return getStartTagType().getCorrespondingEndTagType() == null;
    if (HTMLElements.getEndTagForbiddenElementNames().contains(this.name))
      return true;
    if (HTMLElements.getElementNames().contains(this.name))
      return false;
    return isSyntacticalEmptyElementTag();
  }

  public boolean isEndTagRequired()
  {
    if (getStartTagType() != StartTagType.NORMAL)
      return getStartTagType().getCorrespondingEndTagType() != null;
    if (HTMLElements.getEndTagRequiredElementNames().contains(this.name))
      return true;
    if (HTMLElements.getElementNames().contains(this.name))
      return false;
    return !isSyntacticalEmptyElementTag();
  }

  public boolean isUnregistered()
  {
    return this.startTagType == StartTagType.UNREGISTERED;
  }

  public String tidy()
  {
    return tidy(false);
  }

  public String tidy(boolean paramBoolean)
  {
    if ((this.attributes == null) || (this.attributes.containsServerTagOutsideOfAttributeValue))
      return toString();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append('<');
    if ((paramBoolean) && (this.startTagType == StartTagType.NORMAL))
    {
      localStringBuilder.append(this.name);
    }
    else
    {
      int i = this.begin + this.startTagType.startDelimiterPrefix.length();
      int j = i + this.name.length();
      while (i < j)
      {
        localStringBuilder.append(this.source.charAt(i));
        i++;
      }
    }
    try
    {
      this.attributes.appendTidy(localStringBuilder, getNextTag());
    }
    catch (IOException localIOException)
    {
      throw new RuntimeException(localIOException);
    }
    if ((this.startTagType == StartTagType.NORMAL) && (getElement().getEndTag() == null) && (!HTMLElements.getEndTagOptionalElementNames().contains(this.name)))
      localStringBuilder.append(" /");
    localStringBuilder.append(this.startTagType.getClosingDelimiter());
    return localStringBuilder.toString();
  }

  public static String generateHTML(String paramString, Map<String, String> paramMap, boolean paramBoolean)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append('<').append(paramString);
    try
    {
      Attributes.appendHTML(localStringBuilder, paramMap);
    }
    catch (IOException localIOException)
    {
      throw new RuntimeException(localIOException);
    }
    if (paramBoolean)
      localStringBuilder.append(" />");
    else
      localStringBuilder.append('>');
    return localStringBuilder.toString();
  }

  public String getDebugInfo()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    appendDebugTag(localStringBuilder);
    localStringBuilder.append(' ');
    appendDebugTagType(localStringBuilder);
    localStringBuilder.append(super.getDebugInfo());
    return localStringBuilder.toString();
  }

  StringBuilder appendDebugTag(StringBuilder paramStringBuilder)
  {
    if ((this.startTagType == StartTagType.NORMAL) && (getAttributes().isEmpty()))
    {
      paramStringBuilder.append(this);
    }
    else
    {
      paramStringBuilder.append('<').append(getNameSegment()).append(' ');
      if (isSyntacticalEmptyElementTag())
        paramStringBuilder.append('/');
      paramStringBuilder.append(this.startTagType.getClosingDelimiter());
    }
    return paramStringBuilder;
  }

  StringBuilder appendDebugTagType(StringBuilder paramStringBuilder)
  {
    if (this.startTagType != StartTagType.NORMAL)
      paramStringBuilder.append('(').append(this.startTagType.getDescription()).append(") ");
    return paramStringBuilder;
  }

  private EndTag getEndTagInternal()
  {
    boolean bool = true;
    EndTagType localEndTagType = this.startTagType.getCorrespondingEndTagType();
    if (this.startTagType == StartTagType.NORMAL)
    {
      localObject1 = HTMLElements.getTerminatingTagNameSets(this.name);
      if (localObject1 != null)
        return getOptionalEndTag((HTMLElementTerminatingTagNameSets)localObject1);
      if (HTMLElements.getEndTagForbiddenElementNames().contains(this.name))
        return null;
      bool = !HTMLElements.getEndTagRequiredElementNames().contains(this.name);
      if ((bool) && (isSyntacticalEmptyElementTag()))
        return null;
    }
    else if (localEndTagType == null)
    {
      return null;
    }
    Object localObject1 = this.source.getNextEndTag(this.end, localEndTagType.getEndTagName(this.name), localEndTagType);
    if (localObject1 != null)
    {
      if ((this.startTagType == StartTagType.NORMAL) && (HTMLElements.END_TAG_REQUIRED_NESTING_FORBIDDEN_SET.contains(this.name)))
      {
        localObject2 = this.source.getNextStartTag(this.end, this.name);
        if ((localObject2 == null) || (((StartTag)localObject2).begin > ((EndTag)localObject1).begin))
          return localObject1;
        if (this.source.logger.isInfoEnabled())
          this.source.logger.info(" missing required end tag - invalid nested start tag encountered before end tag");
        return new EndTag(this.source, ((StartTag)localObject2).begin, ((StartTag)localObject2).begin, EndTagType.NORMAL, this.name);
      }
      Object localObject2 = getEndTag((EndTag)localObject1, bool, Tag.isXMLName(this.name));
      if (localObject2 != null)
        return (EndTag)localObject2[0];
    }
    if (this.source.logger.isInfoEnabled())
      this.source.logger.info(" missing required end tag");
    return null;
  }

  private EndTag getOptionalEndTag(HTMLElementTerminatingTagNameSets paramHTMLElementTerminatingTagNameSets)
  {
    int i = this.end;
    while (i < this.source.end)
    {
      Tag localTag = Tag.getNextTag(this.source, i);
      if (localTag == null)
        break;
      Set localSet;
      if ((localTag instanceof EndTag))
      {
        if (localTag.name == this.name)
          return (EndTag)localTag;
        localSet = paramHTMLElementTerminatingTagNameSets.TerminatingEndTagNameSet;
      }
      else
      {
        localSet = paramHTMLElementTerminatingTagNameSets.NonterminatingElementNameSet;
        if ((localSet != null) && (localSet.contains(localTag.name)))
        {
          Element localElement = ((StartTag)localTag).getElement();
          i = localElement.end;
          continue;
        }
        localSet = paramHTMLElementTerminatingTagNameSets.TerminatingStartTagNameSet;
      }
      if ((localSet != null) && (localSet.contains(localTag.name)))
        return new EndTag(this.source, localTag.begin, localTag.begin, EndTagType.NORMAL, this.name);
      i = localTag.begin + 1;
    }
    return new EndTag(this.source, this.source.end, this.source.end, EndTagType.NORMAL, this.name);
  }

  static String getStartDelimiter(String paramString)
  {
    if (paramString.length() == 0)
      throw new IllegalArgumentException("searchName argument must not be zero length");
    String str = "<" + paramString;
    if (str.charAt("<".length()) == '/')
      throw new IllegalArgumentException("searchName argument \"" + paramString + "\" must not start with '/'");
    return str;
  }

  static StartTag getPrevious(Source paramSource, int paramInt, String paramString, StartTagType paramStartTagType)
  {
    return getPrevious(paramSource, paramInt, paramString, paramStartTagType, paramStartTagType == StartTagType.NORMAL ? Tag.isXMLName(paramString) : true);
  }

  static StartTag getPrevious(Source paramSource, int paramInt, String paramString, StartTagType paramStartTagType, boolean paramBoolean)
  {
    if (paramString == null)
      return (StartTag)paramSource.getPreviousTag(paramInt, paramStartTagType);
    String str = getStartDelimiter(paramString);
    try
    {
      ParseText localParseText = paramSource.getParseText();
      int i = paramInt;
      do
      {
        i = localParseText.lastIndexOf(str, i);
        if (i == -1)
          return null;
        StartTag localStartTag = (StartTag)Tag.getTagAt(paramSource, i, false);
        if ((localStartTag != null) && ((paramStartTagType == localStartTag.getStartTagType()) || ((paramStartTagType == StartTagType.NORMAL) && (!paramBoolean) && (localStartTag.isUnregistered()))))
          if ((localStartTag.getStartTagType().isNameAfterPrefixRequired()) && (localStartTag.getName().length() > paramString.length()))
          {
            char c = paramString.charAt(paramString.length() - 1);
            if ((c != ':') && (isXMLNameChar(c)));
          }
          else
          {
            return localStartTag;
          }
        i -= 2;
      }
      while (i >= 0);
    }
    catch (IndexOutOfBoundsException localIndexOutOfBoundsException)
    {
      throw localIndexOutOfBoundsException;
    }
    return null;
  }

  static StartTag getNext(Source paramSource, int paramInt, String paramString, StartTagType paramStartTagType)
  {
    return getNext(paramSource, paramInt, paramString, paramStartTagType, paramStartTagType == StartTagType.NORMAL ? Tag.isXMLName(paramString) : true);
  }

  static StartTag getNext(Source paramSource, int paramInt, String paramString, StartTagType paramStartTagType, boolean paramBoolean)
  {
    if (paramString == null)
      return (StartTag)paramSource.getNextTag(paramInt, paramStartTagType);
    String str = getStartDelimiter(paramString);
    try
    {
      ParseText localParseText = paramSource.getParseText();
      int i = paramInt;
      do
      {
        i = localParseText.indexOf(str, i);
        if (i == -1)
          return null;
        StartTag localStartTag = (StartTag)Tag.getTagAt(paramSource, i, false);
        if ((localStartTag != null) && ((paramStartTagType == localStartTag.getStartTagType()) || ((paramStartTagType == StartTagType.NORMAL) && (!paramBoolean) && (localStartTag.isUnregistered()))))
          if ((localStartTag.getStartTagType().isNameAfterPrefixRequired()) && (localStartTag.getName().length() > paramString.length()))
          {
            char c = paramString.charAt(paramString.length() - 1);
            if ((c != ':') && (isXMLNameChar(c)));
          }
          else
          {
            return localStartTag;
          }
        i++;
      }
      while (i < paramSource.end);
    }
    catch (IndexOutOfBoundsException localIndexOutOfBoundsException)
    {
    }
    return null;
  }

  static StartTag getPrevious(Source paramSource, int paramInt)
  {
    Tag localTag = Tag.getPreviousTag(paramSource, paramInt);
    if (localTag == null)
      return null;
    if ((localTag instanceof StartTag))
      return (StartTag)localTag;
    return localTag.getPreviousStartTag();
  }

  static StartTag getNext(Source paramSource, int paramInt)
  {
    Tag localTag = Tag.getNextTag(paramSource, paramInt);
    if (localTag == null)
      return null;
    if ((localTag instanceof StartTag))
      return (StartTag)localTag;
    return localTag.getNextStartTag();
  }

  static StartTag getNext(Source paramSource, int paramInt, String paramString1, String paramString2, boolean paramBoolean)
  {
    if ((paramString2 == null) || (paramString1.length() == 0))
      throw new IllegalArgumentException();
    String str1 = (paramString2.length() >= 3) || ((paramString2.length() > 0) && (paramString1.length() < 3)) ? paramString2 : paramString1;
    ParseText localParseText = paramSource.getParseText();
    int i = paramInt;
    while (i < paramSource.end)
    {
      i = localParseText.indexOf(str1.toLowerCase(), i);
      if (i == -1)
        return null;
      Tag localTag = paramSource.getEnclosingTag(i);
      if ((localTag == null) || (!(localTag instanceof StartTag)))
      {
        i++;
      }
      else
      {
        if (localTag.begin >= paramInt)
        {
          StartTag localStartTag = (StartTag)localTag;
          if (localStartTag.getAttributes() != null)
          {
            String str2 = localStartTag.getAttributes().getValue(paramString1);
            if (str2 != null)
            {
              if (paramString2.equals(str2))
                return localStartTag;
              if (paramString2.equalsIgnoreCase(str2))
              {
                if (!paramBoolean)
                  return localStartTag;
                if (paramSource.logger.isInfoEnabled())
                  paramSource.logger.info(": StartTag with attribute " + paramString1 + "=\"" + str2 + "\" ignored during search because its case does not match search value \"" + paramString2 + '"');
              }
            }
          }
        }
        i = localTag.end;
      }
    }
    return null;
  }

  static StartTag getNext(Source paramSource, int paramInt, String paramString, Pattern paramPattern)
  {
    if ((paramString == null) || (paramString.length() == 0))
      throw new IllegalArgumentException();
    String str1 = paramString;
    ParseText localParseText = paramSource.getParseText();
    int i = paramInt;
    while (i < paramSource.end)
    {
      i = localParseText.indexOf(str1.toLowerCase(), i);
      if (i == -1)
        return null;
      Tag localTag = paramSource.getEnclosingTag(i);
      if ((localTag == null) || (!(localTag instanceof StartTag)))
      {
        i++;
      }
      else
      {
        if (localTag.begin >= paramInt)
        {
          StartTag localStartTag = (StartTag)localTag;
          if (localStartTag.getAttributes() != null)
          {
            Attribute localAttribute = localStartTag.getAttributes().get(paramString);
            if (localAttribute != null)
            {
              if (paramPattern == null)
                return localStartTag;
              String str2 = localAttribute.getValue();
              if ((str2 != null) && (paramPattern.matcher(str2).matches()))
                return localStartTag;
            }
          }
        }
        i = localTag.end;
      }
    }
    return null;
  }

  private Segment[] getEndTag(EndTag paramEndTag, boolean paramBoolean1, boolean paramBoolean2)
  {
    assert (paramEndTag != null);
    StartTag localStartTag = getNext(this.source, this.end, this.name, this.startTagType, paramBoolean2);
    if (paramBoolean1)
      while ((localStartTag != null) && (localStartTag.isSyntacticalEmptyElementTag()))
        localStartTag = getNext(this.source, localStartTag.end, this.name, this.startTagType, paramBoolean2);
    return getEndTag(this.end, localStartTag, paramEndTag, paramBoolean1, paramBoolean2);
  }

  private Segment[] getEndTag(int paramInt, StartTag paramStartTag, EndTag paramEndTag, boolean paramBoolean1, boolean paramBoolean2)
  {
    if (paramEndTag == null)
      return null;
    Segment[] arrayOfSegment1 = { paramEndTag, paramStartTag };
    if ((paramStartTag == null) || (paramStartTag.begin > paramEndTag.begin))
      return arrayOfSegment1;
    Segment[] arrayOfSegment2 = paramStartTag.getEndTag(paramEndTag, paramBoolean1, paramBoolean2);
    if (arrayOfSegment2 == null)
      return null;
    EndTag localEndTag1 = (EndTag)arrayOfSegment2[0];
    EndTag localEndTag2 = EndTag.getNext(this.source, localEndTag1.end, paramEndTag.getName(), paramEndTag.getEndTagType());
    return getEndTag(localEndTag1.end, (StartTag)arrayOfSegment2[1], localEndTag2, paramBoolean1, paramBoolean2);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.StartTag
 * JD-Core Version:    0.6.2
 */