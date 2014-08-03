package net.htmlparser.jericho;

public class EndTagTypeGenericImplementation extends EndTagType
{
  private final String staticString;

  protected EndTagTypeGenericImplementation(String paramString1, String paramString2, String paramString3, boolean paramBoolean1, boolean paramBoolean2)
  {
    super(paramString1, paramString2, paramString3, paramBoolean1);
    this.staticString = (paramBoolean2 ? paramString2 + paramString3 : null);
  }

  protected final boolean isStatic()
  {
    return this.staticString != null;
  }

  public String getEndTagName(String paramString)
  {
    return isStatic() ? getNamePrefix() : paramString;
  }

  public String generateHTML(String paramString)
  {
    return isStatic() ? this.staticString : super.generateHTML(paramString);
  }

  protected Tag constructTagAt(Source paramSource, int paramInt)
  {
    ParseText localParseText = paramSource.getParseText();
    int i = paramInt + "</".length();
    String str = null;
    int j = paramInt + getStartDelimiter().length();
    int k = -1;
    if (isStatic())
    {
      str = getNamePrefix();
      if (!localParseText.containsAt(getClosingDelimiter(), j))
      {
        if (paramSource.logger.isInfoEnabled())
          paramSource.logger.info(" not recognised as type '" + getDescription() + "' because it is missing the closing delimiter");
        return null;
      }
      k = j + getClosingDelimiter().length();
    }
    else
    {
      int m = paramSource.getNameEnd(j);
      if (m == -1)
        return null;
      str = paramSource.getName(i, m);
      for (int n = m; Segment.isWhiteSpace(localParseText.charAt(n)); n++);
      if (!localParseText.containsAt(getClosingDelimiter(), n))
      {
        if (paramSource.logger.isInfoEnabled())
          paramSource.logger.info(" not recognised as type '" + getDescription() + "' because its name and closing delimiter are separated by characters other than white space");
        return null;
      }
      k = n + getClosingDelimiter().length();
    }
    return constructEndTag(paramSource, paramInt, k, str);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.EndTagTypeGenericImplementation
 * JD-Core Version:    0.6.2
 */