package net.htmlparser.jericho;

import java.util.List;

public abstract class TagType
{
  private final String description;
  private final String startDelimiter;
  private final String closingDelimiter;
  private final boolean isServerTag;
  private final String namePrefix;
  final String startDelimiterPrefix;

  TagType(String paramString1, String paramString2, String paramString3, boolean paramBoolean, String paramString4)
  {
    this.description = paramString1;
    this.startDelimiter = paramString2;
    this.closingDelimiter = paramString3;
    this.isServerTag = paramBoolean;
    this.namePrefix = paramString2.substring(paramString4.length());
    this.startDelimiterPrefix = paramString4;
  }

  public final void register()
  {
    TagTypeRegister.add(this);
  }

  public final void deregister()
  {
    TagTypeRegister.remove(this);
  }

  public static final List<TagType> getRegisteredTagTypes()
  {
    return TagTypeRegister.getList();
  }

  public final String getDescription()
  {
    return this.description;
  }

  public final String getStartDelimiter()
  {
    return this.startDelimiter;
  }

  public final String getClosingDelimiter()
  {
    return this.closingDelimiter;
  }

  public final boolean isServerTag()
  {
    return this.isServerTag;
  }

  protected final String getNamePrefix()
  {
    return this.namePrefix;
  }

  protected boolean isValidPosition(Source paramSource, int paramInt, int[] paramArrayOfInt)
  {
    if (isServerTag())
      return true;
    if (paramArrayOfInt != null)
    {
      if (paramArrayOfInt[0] == 2147483647)
      {
        if ((this == EndTagType.NORMAL) && (paramSource.getParseText().containsAt("</script", paramInt)))
        {
          paramArrayOfInt[0] = paramInt;
          return true;
        }
        if (this == StartTagType.COMMENT)
        {
          paramArrayOfInt[0] = paramInt;
          return true;
        }
        return false;
      }
      return paramInt >= paramArrayOfInt[0];
    }
    TagType[] arrayOfTagType = getTagTypesIgnoringEnclosedMarkup();
    for (int i = 0; i < arrayOfTagType.length; i++)
    {
      TagType localTagType = arrayOfTagType[i];
      if (((this != StartTagType.COMMENT) || (localTagType != StartTagType.COMMENT)) && (localTagType.tagEncloses(paramSource, paramInt)))
        return false;
    }
    return true;
  }

  public static final TagType[] getTagTypesIgnoringEnclosedMarkup()
  {
    return TagTypesIgnoringEnclosedMarkup.array;
  }

  public static final void setTagTypesIgnoringEnclosedMarkup(TagType[] paramArrayOfTagType)
  {
    if (paramArrayOfTagType == null)
      throw new IllegalArgumentException();
    TagTypesIgnoringEnclosedMarkup.array = paramArrayOfTagType;
  }

  protected abstract Tag constructTagAt(Source paramSource, int paramInt);

  protected final boolean tagEncloses(Source paramSource, int paramInt)
  {
    if (paramInt == 0)
      return false;
    Tag localTag = paramSource.getEnclosingTag(paramInt - 1, this);
    return (localTag != null) && (paramInt != localTag.getEnd());
  }

  public String toString()
  {
    return getDescription();
  }

  static final Tag getTagAt(Source paramSource, int paramInt, boolean paramBoolean1, boolean paramBoolean2)
  {
    TagTypeRegister.ProspectiveTagTypeIterator localProspectiveTagTypeIterator = new TagTypeRegister.ProspectiveTagTypeIterator(paramSource, paramInt);
    while (localProspectiveTagTypeIterator.hasNext())
    {
      TagType localTagType = localProspectiveTagTypeIterator.next();
      if (((!paramBoolean1) || (localTagType.isServerTag())) && ((paramBoolean2) || (localTagType.isValidPosition(paramSource, paramInt, paramSource.fullSequentialParseData))))
        try
        {
          Tag localTag = localTagType.constructTagAt(paramSource, paramInt);
          if (localTag != null)
            return localTag;
        }
        catch (IndexOutOfBoundsException localIndexOutOfBoundsException)
        {
          if (paramSource.logger.isInfoEnabled())
            paramSource.logger.info(" not recognised as type '" + localTagType.getDescription() + "' because it has no end delimiter");
        }
    }
    return null;
  }

  private static final class TagTypesIgnoringEnclosedMarkup
  {
    public static TagType[] array = { StartTagType.COMMENT, StartTagType.CDATA_SECTION };
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.TagType
 * JD-Core Version:    0.6.2
 */