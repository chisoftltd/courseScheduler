package net.htmlparser.jericho;

final class StartTagTypeUnregistered extends StartTagType
{
  static final StartTagTypeUnregistered INSTANCE = new StartTagTypeUnregistered();

  private StartTagTypeUnregistered()
  {
    super("unregistered", "<", ">", null, false, false, false);
  }

  protected Tag constructTagAt(Source paramSource, int paramInt)
  {
    int i = paramSource.getParseText().indexOf('>', paramInt + 1);
    if (i == -1)
      return null;
    StartTag localStartTag = constructStartTag(paramSource, paramInt, i + 1, "", null);
    if (paramSource.logger.isInfoEnabled())
      paramSource.logger.info(" whose content does not match a registered StartTagType");
    return localStartTag;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.StartTagTypeUnregistered
 * JD-Core Version:    0.6.2
 */