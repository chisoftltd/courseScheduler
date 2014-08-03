package net.htmlparser.jericho;

public abstract class StartTagType extends TagType
{
  private final EndTagType correspondingEndTagType;
  private final boolean hasAttributes;
  private final boolean isNameAfterPrefixRequired;
  static final String START_DELIMITER_PREFIX = "<";
  public static final StartTagType UNREGISTERED = StartTagTypeUnregistered.INSTANCE;
  public static final StartTagType NORMAL = StartTagTypeNormal.INSTANCE;
  public static final StartTagType COMMENT = StartTagTypeComment.INSTANCE;
  public static final StartTagType XML_DECLARATION = StartTagTypeXMLDeclaration.INSTANCE;
  public static final StartTagType XML_PROCESSING_INSTRUCTION = StartTagTypeXMLProcessingInstruction.INSTANCE;
  public static final StartTagType DOCTYPE_DECLARATION = StartTagTypeDoctypeDeclaration.INSTANCE;
  public static final StartTagType MARKUP_DECLARATION = StartTagTypeMarkupDeclaration.INSTANCE;
  public static final StartTagType CDATA_SECTION = StartTagTypeCDATASection.INSTANCE;
  public static final StartTagType SERVER_COMMON = StartTagTypeServerCommon.INSTANCE;
  public static final StartTagType SERVER_COMMON_ESCAPED = StartTagTypeServerCommonEscaped.INSTANCE;

  protected StartTagType(String paramString1, String paramString2, String paramString3, EndTagType paramEndTagType, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    super(paramString1, paramString2.toLowerCase(), paramString3, paramBoolean1, "<");
    if (!getStartDelimiter().startsWith("<"))
      throw new IllegalArgumentException("startDelimiter of a start tag must start with \"<\"");
    this.correspondingEndTagType = paramEndTagType;
    this.hasAttributes = paramBoolean2;
    this.isNameAfterPrefixRequired = paramBoolean3;
  }

  public final EndTagType getCorrespondingEndTagType()
  {
    return this.correspondingEndTagType;
  }

  public final boolean hasAttributes()
  {
    return this.hasAttributes;
  }

  public final boolean isNameAfterPrefixRequired()
  {
    return this.isNameAfterPrefixRequired;
  }

  public boolean atEndOfAttributes(Source paramSource, int paramInt, boolean paramBoolean)
  {
    return paramSource.getParseText().containsAt(getClosingDelimiter(), paramInt);
  }

  protected final StartTag constructStartTag(Source paramSource, int paramInt1, int paramInt2, String paramString, Attributes paramAttributes)
  {
    return new StartTag(paramSource, paramInt1, paramInt2, this, paramString, paramAttributes);
  }

  protected final Attributes parseAttributes(Source paramSource, int paramInt, String paramString)
  {
    return Attributes.construct(paramSource, paramInt, this, paramString);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.StartTagType
 * JD-Core Version:    0.6.2
 */