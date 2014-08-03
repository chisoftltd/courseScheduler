package net.htmlparser.jericho;

public abstract class EndTagType extends TagType
{
  static final String START_DELIMITER_PREFIX = "</";
  public static final EndTagType UNREGISTERED = EndTagTypeUnregistered.INSTANCE;
  public static final EndTagType NORMAL = EndTagTypeNormal.INSTANCE;

  protected EndTagType(String paramString1, String paramString2, String paramString3, boolean paramBoolean)
  {
    super(paramString1, paramString2.toLowerCase(), paramString3, paramBoolean, "</");
    if (!getStartDelimiter().startsWith("</"))
      throw new IllegalArgumentException("startDelimiter of an end tag must start with \"</\"");
  }

  public StartTagType getCorrespondingStartTagType()
  {
    return null;
  }

  public String getEndTagName(String paramString)
  {
    return paramString;
  }

  public String generateHTML(String paramString)
  {
    return "</" + getEndTagName(paramString) + getClosingDelimiter();
  }

  protected final EndTag constructEndTag(Source paramSource, int paramInt1, int paramInt2, String paramString)
  {
    return new EndTag(paramSource, paramInt1, paramInt2, this, paramString);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.EndTagType
 * JD-Core Version:    0.6.2
 */