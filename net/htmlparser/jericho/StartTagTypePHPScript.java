package net.htmlparser.jericho;

final class StartTagTypePHPScript extends StartTagTypeGenericImplementation
{
  protected static final StartTagTypePHPScript INSTANCE = new StartTagTypePHPScript();

  private StartTagTypePHPScript()
  {
    super("PHP script", "<script", ">", EndTagType.NORMAL, true, true, false);
  }

  protected Tag constructTagAt(Source paramSource, int paramInt)
  {
    StartTag localStartTag = (StartTag)super.constructTagAt(paramSource, paramInt);
    if (localStartTag == null)
      return null;
    if (!"php".equalsIgnoreCase(localStartTag.getAttributes().getValue("language")))
      return null;
    return localStartTag;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.StartTagTypePHPScript
 * JD-Core Version:    0.6.2
 */