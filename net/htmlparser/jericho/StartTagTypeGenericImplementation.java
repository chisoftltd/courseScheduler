package net.htmlparser.jericho;

public class StartTagTypeGenericImplementation extends StartTagType
{
  final boolean nameCharAfterPrefixAllowed = (getNamePrefix().length() == 0) || (!Character.isLetter(getNamePrefix().charAt(getNamePrefix().length() - 1)));

  protected StartTagTypeGenericImplementation(String paramString1, String paramString2, String paramString3, EndTagType paramEndTagType, boolean paramBoolean)
  {
    this(paramString1, paramString2, paramString3, paramEndTagType, paramBoolean, false, false);
  }

  protected StartTagTypeGenericImplementation(String paramString1, String paramString2, String paramString3, EndTagType paramEndTagType, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    super(paramString1, paramString2, paramString3, paramEndTagType, paramBoolean1, paramBoolean2, paramBoolean3);
  }

  protected Tag constructTagAt(Source paramSource, int paramInt)
  {
    ParseText localParseText = paramSource.getParseText();
    int i = paramInt + 1;
    String str = getNamePrefix();
    int j = i + getNamePrefix().length();
    int k;
    if (isNameAfterPrefixRequired())
    {
      k = paramSource.getNameEnd(j);
      if (k == -1)
        return null;
      str = paramSource.getName(i, k);
      j = k;
    }
    else if ((!this.nameCharAfterPrefixAllowed) && (Tag.isXMLNameChar(localParseText.charAt(j))))
    {
      return null;
    }
    Attributes localAttributes = null;
    if (hasAttributes())
    {
      localAttributes = parseAttributes(paramSource, paramInt, str);
      if (localAttributes == null)
        return null;
      k = getEnd(paramSource, localAttributes.getEnd());
    }
    else
    {
      k = getEnd(paramSource, j);
      if (k == -1)
      {
        if (paramSource.logger.isInfoEnabled())
          paramSource.logger.info(" not recognised as type '" + getDescription() + "' because it has no closing delimiter");
        return null;
      }
    }
    return constructStartTag(paramSource, paramInt, k, str, localAttributes);
  }

  protected int getEnd(Source paramSource, int paramInt)
  {
    int i = paramSource.getParseText().indexOf(getClosingDelimiter(), paramInt);
    return i == -1 ? -1 : i + getClosingDelimiter().length();
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.StartTagTypeGenericImplementation
 * JD-Core Version:    0.6.2
 */