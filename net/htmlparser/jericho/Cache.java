package net.htmlparser.jericho;

import java.util.Iterator;
import java.util.List;

final class Cache
{
  public final Source source;
  private final SubCache allTagTypesSubCache;
  private final SubCache[] subCaches;
  static final Cache STREAMED_SOURCE_MARKER = new Cache();

  public Cache(Source paramSource)
  {
    this.source = paramSource;
    this.allTagTypesSubCache = new SubCache(this, null);
    TagType[] arrayOfTagType = getSeparatelyCachedTagTypes();
    this.subCaches = new SubCache[arrayOfTagType.length + 1];
    this.subCaches[0] = this.allTagTypesSubCache;
    for (int i = 0; i < arrayOfTagType.length; i++)
      this.subCaches[(i + 1)] = new SubCache(this, arrayOfTagType[i]);
  }

  private Cache()
  {
    this.source = null;
    this.allTagTypesSubCache = null;
    this.subCaches = null;
  }

  public void clear()
  {
    Iterator localIterator = this.allTagTypesSubCache.getTagIterator();
    while (localIterator.hasNext())
      ((Tag)localIterator.next()).orphan();
    for (int i = 0; i < this.subCaches.length; i++)
      this.subCaches[i].clear();
  }

  public Tag getTagAt(int paramInt, boolean paramBoolean)
  {
    return this.source.useAllTypesCache ? this.allTagTypesSubCache.getTagAt(paramInt, paramBoolean) : Tag.getTagAtUncached(this.source, paramInt, paramBoolean);
  }

  public Tag getPreviousTag(int paramInt)
  {
    return this.allTagTypesSubCache.getPreviousTag(paramInt);
  }

  public Tag getNextTag(int paramInt)
  {
    return this.allTagTypesSubCache.getNextTag(paramInt);
  }

  public Tag getPreviousTag(int paramInt, TagType paramTagType)
  {
    for (int i = this.source.useAllTypesCache ? 0 : 1; i < this.subCaches.length; i++)
      if (paramTagType == this.subCaches[i].tagType)
        return this.subCaches[i].getPreviousTag(paramInt);
    return Tag.getPreviousTagUncached(this.source, paramInt, paramTagType, -1);
  }

  public Tag getNextTag(int paramInt, TagType paramTagType)
  {
    for (int i = this.source.useAllTypesCache ? 0 : 1; i < this.subCaches.length; i++)
      if (paramTagType == this.subCaches[i].tagType)
        return this.subCaches[i].getNextTag(paramInt);
    return Tag.getNextTagUncached(this.source, paramInt, paramTagType, -1);
  }

  public Tag addTagAt(int paramInt, boolean paramBoolean)
  {
    Tag localTag = Tag.getTagAtUncached(this.source, paramInt, paramBoolean);
    if ((paramBoolean) && (localTag == null))
      return null;
    this.allTagTypesSubCache.addTagAt(paramInt, localTag);
    if (localTag == null)
      return null;
    TagType localTagType = localTag.getTagType();
    for (int i = 1; i < this.subCaches.length; i++)
      if (localTagType == this.subCaches[i].tagType)
      {
        this.subCaches[i].addTagAt(paramInt, localTag);
        return localTag;
      }
    return localTag;
  }

  public int getTagCount()
  {
    return this.allTagTypesSubCache.size() - 2;
  }

  public Iterator<Tag> getTagIterator()
  {
    return this.allTagTypesSubCache.getTagIterator();
  }

  public void loadAllTags(List<Tag> paramList, Tag[] paramArrayOfTag, StartTag[] paramArrayOfStartTag)
  {
    int i = paramList.size();
    this.allTagTypesSubCache.bulkLoad_Init(i);
    int j = 0;
    int k = 0;
    for (int m = 0; m < i; m++)
    {
      Tag localTag = (Tag)paramList.get(m);
      if (!localTag.isUnregistered())
      {
        paramArrayOfTag[(j++)] = localTag;
        if ((localTag instanceof StartTag))
          paramArrayOfStartTag[(k++)] = ((StartTag)localTag);
      }
      this.allTagTypesSubCache.bulkLoad_Set(m, localTag);
      for (int n = 1; n < this.subCaches.length; n++)
        if (localTag.getTagType() == this.subCaches[n].tagType)
        {
          this.subCaches[n].bulkLoad_AddToTypeSpecificCache(localTag);
          break;
        }
    }
    for (m = 1; m < this.subCaches.length; m++)
      this.subCaches[m].bulkLoad_FinaliseTypeSpecificCache();
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    for (int i = 0; i < this.subCaches.length; i++)
      this.subCaches[i].appendTo(localStringBuilder);
    return localStringBuilder.toString();
  }

  protected int getSourceLength()
  {
    return this.source.end;
  }

  private static TagType[] getSeparatelyCachedTagTypes()
  {
    return TagType.getTagTypesIgnoringEnclosedMarkup();
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.Cache
 * JD-Core Version:    0.6.2
 */