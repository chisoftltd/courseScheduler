package net.htmlparser.jericho;

public final class MasonTagTypes
{
  public static final StartTagType MASON_COMPONENT_CALL = StartTagTypeMasonComponentCall.INSTANCE;
  public static final StartTagType MASON_COMPONENT_CALLED_WITH_CONTENT = StartTagTypeMasonComponentCalledWithContent.INSTANCE;
  public static final EndTagType MASON_COMPONENT_CALLED_WITH_CONTENT_END = EndTagTypeMasonComponentCalledWithContent.INSTANCE;
  public static final StartTagType MASON_NAMED_BLOCK = StartTagTypeMasonNamedBlock.INSTANCE;
  public static final EndTagType MASON_NAMED_BLOCK_END = EndTagTypeMasonNamedBlock.INSTANCE;
  private static final TagType[] TAG_TYPES = { MASON_COMPONENT_CALL, MASON_COMPONENT_CALLED_WITH_CONTENT, MASON_COMPONENT_CALLED_WITH_CONTENT_END, MASON_NAMED_BLOCK, MASON_NAMED_BLOCK_END };

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

  public static boolean isParsedByMason(TagType paramTagType)
  {
    return (paramTagType == StartTagType.SERVER_COMMON) || (defines(paramTagType));
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.MasonTagTypes
 * JD-Core Version:    0.6.2
 */