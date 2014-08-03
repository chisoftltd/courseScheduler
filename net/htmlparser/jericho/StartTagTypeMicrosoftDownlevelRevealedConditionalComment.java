package net.htmlparser.jericho;

final class StartTagTypeMicrosoftDownlevelRevealedConditionalComment extends StartTagTypeGenericImplementation
{
  static final StartTagTypeMicrosoftDownlevelRevealedConditionalComment INSTANCE = new StartTagTypeMicrosoftDownlevelRevealedConditionalComment();
  static final String IF = "![if";
  static final String ENDIF = "![endif";

  private StartTagTypeMicrosoftDownlevelRevealedConditionalComment()
  {
    super("Microsoft downlevel-revealed conditional comment", "<![", "]>", null, false, false, true);
  }

  protected Tag constructTagAt(Source paramSource, int paramInt)
  {
    Tag localTag = super.constructTagAt(paramSource, paramInt);
    if (localTag == null)
      return null;
    String str = localTag.getName();
    if ((str != "![if") && (str != "![endif"))
      return null;
    return localTag;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.StartTagTypeMicrosoftDownlevelRevealedConditionalComment
 * JD-Core Version:    0.6.2
 */