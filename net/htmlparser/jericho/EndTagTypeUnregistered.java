package net.htmlparser.jericho;

final class EndTagTypeUnregistered extends EndTagType
{
  static final EndTagTypeUnregistered INSTANCE = new EndTagTypeUnregistered();

  private EndTagTypeUnregistered()
  {
    super("/unregistered", "</", ">", false);
  }

  protected Tag constructTagAt(Source paramSource, int paramInt)
  {
    ParseText localParseText = paramSource.getParseText();
    int i = paramInt + getStartDelimiter().length();
    int j = localParseText.indexOf(getClosingDelimiter(), i);
    String str = paramSource.getName(i, j);
    EndTag localEndTag = constructEndTag(paramSource, paramInt, j + getClosingDelimiter().length(), str);
    if (paramSource.logger.isInfoEnabled())
      paramSource.logger.info(" whose content does not match a registered EndTagType");
    return localEndTag;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.EndTagTypeUnregistered
 * JD-Core Version:    0.6.2
 */