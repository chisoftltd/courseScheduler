package net.htmlparser.jericho;

public final class MicrosoftTagTypes
{
  public static final StartTagType DOWNLEVEL_REVEALED_CONDITIONAL_COMMENT = StartTagTypeMicrosoftDownlevelRevealedConditionalComment.INSTANCE;
  private static final TagType[] TAG_TYPES = { DOWNLEVEL_REVEALED_CONDITIONAL_COMMENT };

  public static boolean isConditionalCommentIfTag(Tag paramTag)
  {
    return paramTag.getName() == "![if";
  }

  public static boolean isConditionalCommentEndifTag(Tag paramTag)
  {
    return paramTag.getName() == "![endif";
  }

  public static void register()
  {
    for (TagType localTagType : TAG_TYPES)
      localTagType.register();
  }

  public static boolean defines(TagType paramTagType)
  {
    for (TagType localTagType : TAG_TYPES)
      if (paramTagType == localTagType)
        return true;
    return false;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.MicrosoftTagTypes
 * JD-Core Version:    0.6.2
 */