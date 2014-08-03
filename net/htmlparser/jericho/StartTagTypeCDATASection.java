package net.htmlparser.jericho;

class StartTagTypeCDATASection extends StartTagTypeGenericImplementation
{
  static final StartTagTypeCDATASection INSTANCE = new StartTagTypeCDATASection();

  private StartTagTypeCDATASection()
  {
    super("CDATA section", "<![cdata[", "]]>", null, false);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.StartTagTypeCDATASection
 * JD-Core Version:    0.6.2
 */