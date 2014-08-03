package net.htmlparser.jericho;

public final class PHPTagTypes
{
  public static final StartTagType PHP_STANDARD = StartTagTypePHPStandard.INSTANCE;
  public static final StartTagType PHP_SHORT = StartTagTypePHPShort.INSTANCE;
  public static final StartTagType PHP_SCRIPT = StartTagTypePHPScript.INSTANCE;
  private static final TagType[] TAG_TYPES = { PHP_STANDARD, PHP_SHORT, PHP_SCRIPT };

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

  public static boolean isParsedByPHP(TagType paramTagType)
  {
    return (paramTagType == StartTagType.SERVER_COMMON) || (defines(paramTagType));
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.PHPTagTypes
 * JD-Core Version:    0.6.2
 */