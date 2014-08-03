package net.htmlparser.jericho;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Tag extends Segment
{
  String name = null;
  private Object userData = null;
  Element element = Element.NOT_CACHED;
  private Tag previousTag = NOT_CACHED;
  private Tag nextTag = NOT_CACHED;
  static final Tag NOT_CACHED = new StartTag();
  private static final boolean INCLUDE_UNREGISTERED_IN_SEARCH = false;

  Tag(Source paramSource, int paramInt1, int paramInt2, String paramString)
  {
    super(paramSource, paramInt1, paramInt2);
    this.name = HTMLElements.getConstantElementName(paramString.toLowerCase());
  }

  Tag()
  {
  }

  public abstract Element getElement();

  public final String getName()
  {
    return this.name;
  }

  public Segment getNameSegment()
  {
    int i = this.begin + getTagType().startDelimiterPrefix.length();
    return new Segment(this.source, i, i + this.name.length());
  }

  public abstract TagType getTagType();

  public Object getUserData()
  {
    return this.userData;
  }

  public void setUserData(Object paramObject)
  {
    this.userData = paramObject;
  }

  public Tag getNextTag()
  {
    if (this.nextTag == NOT_CACHED)
    {
      Tag localTag = getNextTag(this.source, this.begin + 1);
      if (this.source.wasFullSequentialParseCalled())
        return localTag;
      this.nextTag = localTag;
    }
    return this.nextTag;
  }

  public Tag getPreviousTag()
  {
    if (this.previousTag == NOT_CACHED)
      this.previousTag = getPreviousTag(this.source, this.begin - 1);
    return this.previousTag;
  }

  public abstract boolean isUnregistered();

  public abstract String tidy();

  public static final boolean isXMLName(CharSequence paramCharSequence)
  {
    if ((paramCharSequence == null) || (paramCharSequence.length() == 0) || (!isXMLNameStartChar(paramCharSequence.charAt(0))))
      return false;
    for (int i = 1; i < paramCharSequence.length(); i++)
      if (!isXMLNameChar(paramCharSequence.charAt(i)))
        return false;
    return true;
  }

  public static final boolean isXMLNameStartChar(char paramChar)
  {
    return (Character.isLetter(paramChar)) || (paramChar == '_') || (paramChar == ':');
  }

  public static final boolean isXMLNameChar(char paramChar)
  {
    return (Character.isLetterOrDigit(paramChar)) || (paramChar == '.') || (paramChar == '-') || (paramChar == '_') || (paramChar == ':');
  }

  StartTag getNextStartTag()
  {
    Tag localTag = this;
    do
    {
      localTag = localTag.getNextTag();
      if (localTag == null)
        return null;
    }
    while (!(localTag instanceof StartTag));
    return (StartTag)localTag;
  }

  StartTag getPreviousStartTag()
  {
    Tag localTag = this;
    do
    {
      localTag = localTag.getPreviousTag();
      if (localTag == null)
        return null;
    }
    while (!(localTag instanceof StartTag));
    return (StartTag)localTag;
  }

  Tag getNextTag(TagType paramTagType)
  {
    if (paramTagType == null)
      return getNextTag();
    if ((paramTagType == StartTagType.UNREGISTERED) || (paramTagType == EndTagType.UNREGISTERED))
      return getNextTag(this.source, this.begin + 1, paramTagType);
    Tag localTag = this;
    do
    {
      if (localTag.nextTag == NOT_CACHED)
        return getNextTag(this.source, localTag.begin + 1, paramTagType);
      localTag = localTag.nextTag;
      if (localTag == null)
        return null;
    }
    while (localTag.getTagType() != paramTagType);
    return localTag;
  }

  Tag getPreviousTag(TagType paramTagType)
  {
    if (paramTagType == null)
      return getPreviousTag();
    if ((paramTagType == StartTagType.UNREGISTERED) || (paramTagType == EndTagType.UNREGISTERED))
      return getPreviousTag(this.source, this.begin - 1, paramTagType);
    Tag localTag = this;
    do
    {
      if (localTag.previousTag == NOT_CACHED)
        return getPreviousTag(this.source, localTag.begin - 1, paramTagType);
      localTag = localTag.previousTag;
      if (localTag == null)
        return null;
    }
    while (localTag.getTagType() != paramTagType);
    return localTag;
  }

  final boolean includeInSearch()
  {
    return !isUnregistered();
  }

  static final Tag getPreviousTag(Source paramSource, int paramInt)
  {
    return paramSource.useAllTypesCache ? paramSource.cache.getPreviousTag(paramInt) : getPreviousTagUncached(paramSource, paramInt, -1);
  }

  static final Tag getNextTag(Source paramSource, int paramInt)
  {
    return paramSource.useAllTypesCache ? paramSource.cache.getNextTag(paramInt) : getNextTagUncached(paramSource, paramInt, -1);
  }

  static final Tag getPreviousTagUncached(Source paramSource, int paramInt1, int paramInt2)
  {
    try
    {
      ParseText localParseText = paramSource.getParseText();
      int i = paramInt1;
      do
      {
        i = localParseText.lastIndexOf('<', i, paramInt2);
        if (i == -1)
          return null;
        Tag localTag = getTagAt(paramSource, i, false);
        if ((localTag != null) && (localTag.includeInSearch()))
          return localTag;
        i--;
      }
      while (i >= 0);
    }
    catch (IndexOutOfBoundsException localIndexOutOfBoundsException)
    {
      throw new AssertionError("Unexpected internal exception");
    }
    return null;
  }

  static final Tag getNextTagUncached(Source paramSource, int paramInt1, int paramInt2)
  {
    try
    {
      ParseText localParseText = paramSource.getParseText();
      int i = paramInt1;
      do
      {
        i = localParseText.indexOf('<', i, paramInt2);
        if (i == -1)
          return null;
        Tag localTag = getTagAt(paramSource, i, false);
        if ((localTag != null) && (localTag.includeInSearch()))
          return localTag;
        i++;
      }
      while (i < paramSource.end);
    }
    catch (IndexOutOfBoundsException localIndexOutOfBoundsException)
    {
    }
    return null;
  }

  static final Tag getPreviousTag(Source paramSource, int paramInt, TagType paramTagType)
  {
    if (paramSource.useSpecialTypesCache)
      return paramSource.cache.getPreviousTag(paramInt, paramTagType);
    return getPreviousTagUncached(paramSource, paramInt, paramTagType, -1);
  }

  static final Tag getNextTag(Source paramSource, int paramInt, TagType paramTagType)
  {
    if (paramSource.useSpecialTypesCache)
      return paramSource.cache.getNextTag(paramInt, paramTagType);
    return getNextTagUncached(paramSource, paramInt, paramTagType, -1);
  }

  static final Tag getPreviousTagUncached(Source paramSource, int paramInt1, TagType paramTagType, int paramInt2)
  {
    if (paramTagType == null)
      return getPreviousTagUncached(paramSource, paramInt1, paramInt2);
    String str = paramTagType.getStartDelimiter();
    try
    {
      ParseText localParseText = paramSource.getParseText();
      int i = paramInt1;
      do
      {
        i = localParseText.lastIndexOf(str, i, paramInt2);
        if (i == -1)
          return null;
        Tag localTag = getTagAt(paramSource, i, false);
        if ((localTag != null) && (localTag.getTagType() == paramTagType))
          return localTag;
        i--;
      }
      while (i >= 0);
    }
    catch (IndexOutOfBoundsException localIndexOutOfBoundsException)
    {
      throw localIndexOutOfBoundsException;
    }
    return null;
  }

  static final Tag getNextTagUncached(Source paramSource, int paramInt1, TagType paramTagType, int paramInt2)
  {
    if (paramTagType == null)
      return getNextTagUncached(paramSource, paramInt1, paramInt2);
    String str = paramTagType.getStartDelimiter();
    try
    {
      ParseText localParseText = paramSource.getParseText();
      int i = paramInt1;
      do
      {
        i = localParseText.indexOf(str, i, paramInt2);
        if (i == -1)
          return null;
        Tag localTag = getTagAt(paramSource, i, false);
        if ((localTag != null) && (localTag.getTagType() == paramTagType))
          return localTag;
        i++;
      }
      while (i < paramSource.end);
    }
    catch (IndexOutOfBoundsException localIndexOutOfBoundsException)
    {
    }
    return null;
  }

  static final Tag getTagAt(Source paramSource, int paramInt, boolean paramBoolean)
  {
    return paramSource.useAllTypesCache ? paramSource.cache.getTagAt(paramInt, paramBoolean) : getTagAtUncached(paramSource, paramInt, paramBoolean);
  }

  static final Tag getTagAtUncached(Source paramSource, int paramInt, boolean paramBoolean)
  {
    return TagType.getTagAt(paramSource, paramInt, paramBoolean, false);
  }

  static final Tag[] parseAll(Source paramSource, boolean paramBoolean)
  {
    int i = 0;
    int j = 0;
    ArrayList localArrayList = new ArrayList();
    paramSource.fullSequentialParseData = new int[1];
    if (paramSource.end != 0)
    {
      localObject1 = paramSource.getParseText();
      for (localObject2 = parseAllgetNextTag(paramSource, (ParseText)localObject1, 0, paramBoolean); localObject2 != null; localObject2 = parseAllgetNextTag(paramSource, (ParseText)localObject1, k, paramBoolean))
      {
        localArrayList.add(localObject2);
        if (!((Tag)localObject2).isUnregistered())
        {
          i++;
          if ((localObject2 instanceof StartTag))
            j++;
        }
        k = (paramBoolean) && (!((Tag)localObject2).isUnregistered()) ? ((Tag)localObject2).end : ((Tag)localObject2).begin + 1;
        if (k == paramSource.end)
          break;
      }
    }
    Object localObject1 = new Tag[i];
    Object localObject2 = new StartTag[j];
    paramSource.cache.loadAllTags(localArrayList, (Tag[])localObject1, (StartTag[])localObject2);
    paramSource.allTagsArray = ((Tag[])localObject1);
    paramSource.allTags = Arrays.asList((Object[])localObject1);
    paramSource.allStartTags = Arrays.asList((Object[])localObject2);
    int k = localObject1.length - 1;
    for (int m = 0; m < localObject1.length; m++)
    {
      Object localObject3 = localObject1[m];
      localObject3.previousTag = (m > 0 ? localObject1[(m - 1)] : null);
      localObject3.nextTag = (m < k ? localObject1[(m + 1)] : null);
    }
    return localObject1;
  }

  private static final Tag parseAllgetNextTag(Source paramSource, ParseText paramParseText, int paramInt, boolean paramBoolean)
  {
    try
    {
      int i = paramInt;
      do
      {
        i = paramParseText.indexOf('<', i);
        if (i == -1)
          return null;
        Tag localTag = TagType.getTagAt(paramSource, i, false, paramBoolean);
        if (localTag != null)
        {
          if (!paramBoolean)
          {
            TagType localTagType = localTag.getTagType();
            if ((localTag.end > paramSource.fullSequentialParseData[0]) && (localTagType != StartTagType.DOCTYPE_DECLARATION) && (localTagType != StartTagType.UNREGISTERED) && (localTagType != EndTagType.UNREGISTERED))
              paramSource.fullSequentialParseData[0] = ((localTagType == StartTagType.NORMAL) && (localTag.name == "script") ? 2147483647 : localTag.end);
          }
          return localTag;
        }
        i++;
      }
      while (i < paramSource.end);
    }
    catch (IndexOutOfBoundsException localIndexOutOfBoundsException)
    {
    }
    return null;
  }

  void orphan()
  {
    this.nextTag = NOT_CACHED;
  }

  boolean isOrphaned()
  {
    return (this.source.wasFullSequentialParseCalled()) && (this.nextTag == NOT_CACHED);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.Tag
 * JD-Core Version:    0.6.2
 */