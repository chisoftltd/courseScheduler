package net.htmlparser.jericho;

final class StartTagTypeNormal extends StartTagTypeGenericImplementation
{
  static final StartTagTypeNormal INSTANCE = new StartTagTypeNormal();

  private StartTagTypeNormal()
  {
    super("normal", "<", ">", EndTagType.NORMAL, false, true, true);
  }

  public boolean atEndOfAttributes(Source paramSource, int paramInt, boolean paramBoolean)
  {
    ParseText localParseText = paramSource.getParseText();
    return (localParseText.charAt(paramInt) == '>') || ((!paramBoolean) && (localParseText.containsAt("/>", paramInt)));
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.StartTagTypeNormal
 * JD-Core Version:    0.6.2
 */