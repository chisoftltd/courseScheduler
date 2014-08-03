package net.htmlparser.jericho;

import java.util.Iterator;

final class SubCache
{
  private final Cache cache;
  public final TagType tagType;
  private final CacheEntry bof;
  private final CacheEntry eof;
  private CacheEntry[] array = new CacheEntry[64];
  private static final int INITIAL_CAPACITY = 64;

  public SubCache(Cache paramCache, TagType paramTagType)
  {
    this.cache = paramCache;
    this.tagType = paramTagType;
    void tmp41_38 = new CacheEntry(0, -1, null, false, false);
    this.bof = tmp41_38;
    this.array[0] = tmp41_38;
    void tmp67_64 = new CacheEntry(1, paramCache.getSourceLength(), null, false, false);
    this.eof = tmp67_64;
    this.array[1] = tmp67_64;
  }

  public int size()
  {
    return this.eof.index + 1;
  }

  public void clear()
  {
    this.bof.nextCached = false;
    this.eof.index = 1;
    this.eof.previousCached = false;
    this.array[1] = this.eof;
  }

  public void bulkLoad_Init(int paramInt)
  {
    this.array = new CacheEntry[paramInt + 2];
    this.array[0] = this.bof;
    this.bof.nextCached = true;
    int tmp39_38 = (paramInt + 1);
    this.eof.index = tmp39_38;
    this.array[tmp39_38] = this.eof;
    this.eof.previousCached = true;
  }

  public void bulkLoad_Set(int paramInt, Tag paramTag)
  {
    int i = paramInt + 1;
    this.array[i] = new CacheEntry(i, paramTag.begin, paramTag, true, true);
  }

  public void bulkLoad_AddToTypeSpecificCache(Tag paramTag)
  {
    int i = this.eof.index;
    if (this.array.length == this.eof.index + 1)
      doubleCapacity();
    this.array[i] = new CacheEntry(i, paramTag.begin, paramTag, true, true);
    this.eof.index += 1;
  }

  public void bulkLoad_FinaliseTypeSpecificCache()
  {
    this.bof.nextCached = true;
    this.eof.previousCached = true;
    this.array[this.eof.index] = this.eof;
  }

  public Tag getTagAt(int paramInt, boolean paramBoolean)
  {
    if (this.cache.getSourceLength() == 0)
      return null;
    if ((paramInt < 0) || (paramInt >= this.cache.getSourceLength()))
      return null;
    int i = getIndexOfPos(paramInt);
    CacheEntry localCacheEntry = this.array[i];
    if (localCacheEntry.pos == paramInt)
    {
      if ((paramBoolean) && (!localCacheEntry.tag.getTagType().isServerTag()))
        return null;
      return localCacheEntry.tag;
    }
    if (localCacheEntry.previousCached)
      return null;
    return this.cache.addTagAt(paramInt, paramBoolean);
  }

  public void addTagAt(int paramInt, Tag paramTag)
  {
    int i = getIndexOfPos(paramInt);
    CacheEntry localCacheEntry1 = this.array[i];
    CacheEntry localCacheEntry2 = getPrevious(localCacheEntry1);
    add(localCacheEntry2, new CacheEntry(i, paramInt, paramTag, paramInt == localCacheEntry2.pos + 1, paramInt == localCacheEntry1.pos - 1), localCacheEntry1);
  }

  public Tag getPreviousTag(int paramInt)
  {
    if (this.cache.getSourceLength() == 0)
      return null;
    if ((paramInt < 0) || (paramInt >= this.cache.getSourceLength()))
      return null;
    int i = getIndexOfPos(paramInt);
    CacheEntry localCacheEntry = this.array[i];
    if ((localCacheEntry.pos == paramInt) && (localCacheEntry.tag != null) && (localCacheEntry.tag.includeInSearch()))
      return localCacheEntry.tag;
    Tag localTag = getPreviousTag(getPrevious(localCacheEntry), paramInt, localCacheEntry);
    addPreviousTag(paramInt, localTag);
    return localTag;
  }

  public Tag getNextTag(int paramInt)
  {
    if (this.cache.getSourceLength() == 0)
      return null;
    if ((paramInt < 0) || (paramInt >= this.cache.getSourceLength()))
      return null;
    int i = getIndexOfPos(paramInt);
    CacheEntry localCacheEntry = this.array[i];
    Tag localTag;
    if (localCacheEntry.pos == paramInt)
    {
      if ((localCacheEntry.tag != null) && (localCacheEntry.tag.includeInSearch()))
        return localCacheEntry.tag;
      localTag = getNextTag(localCacheEntry, paramInt, getNext(localCacheEntry));
    }
    else
    {
      localTag = getNextTag(getPrevious(localCacheEntry), paramInt, localCacheEntry);
    }
    addNextTag(paramInt, localTag);
    return localTag;
  }

  public Iterator<Tag> getTagIterator()
  {
    return new TagIterator();
  }

  public String toString()
  {
    return appendTo(new StringBuilder()).toString();
  }

  protected StringBuilder appendTo(StringBuilder paramStringBuilder)
  {
    paramStringBuilder.append("Cache for TagType : ").append(this.tagType).append(Config.NewLine);
    for (int i = 0; i <= lastIndex(); i++)
      paramStringBuilder.append(this.array[i]).append(Config.NewLine);
    return paramStringBuilder;
  }

  private Tag getPreviousTag(CacheEntry paramCacheEntry1, int paramInt, CacheEntry paramCacheEntry2)
  {
    while (true)
    {
      if (!paramCacheEntry2.previousCached)
      {
        Tag localTag = Tag.getPreviousTagUncached(this.cache.source, paramInt, this.tagType, paramCacheEntry1.pos);
        if (localTag != null)
        {
          if (!this.cache.source.useAllTypesCache)
            addTagAt(localTag.begin, localTag);
          return localTag;
        }
      }
      if (paramCacheEntry1 == this.bof)
        return null;
      if ((paramCacheEntry1.tag != null) && (paramCacheEntry1.tag.includeInSearch()))
        return paramCacheEntry1.tag;
      paramInt = paramCacheEntry1.pos - 1;
      paramCacheEntry1 = getPrevious(paramCacheEntry2 = paramCacheEntry1);
    }
  }

  private Tag getNextTag(CacheEntry paramCacheEntry1, int paramInt, CacheEntry paramCacheEntry2)
  {
    while (true)
    {
      if (!paramCacheEntry1.nextCached)
      {
        Tag localTag = Tag.getNextTagUncached(this.cache.source, paramInt, this.tagType, paramCacheEntry2.pos);
        if (localTag != null)
        {
          if (!this.cache.source.useAllTypesCache)
            addTagAt(localTag.begin, localTag);
          return localTag;
        }
      }
      if (paramCacheEntry2 == this.eof)
        return null;
      if ((paramCacheEntry2.tag != null) && (paramCacheEntry2.tag.includeInSearch()))
        return paramCacheEntry2.tag;
      paramInt = paramCacheEntry2.pos + 1;
      paramCacheEntry2 = getNext(paramCacheEntry1 = paramCacheEntry2);
    }
  }

  private void addPreviousTag(int paramInt, Tag paramTag)
  {
    int i = paramTag == null ? this.bof.pos : paramTag.begin;
    if (i == paramInt)
      return;
    int j = getIndexOfPos(paramInt);
    CacheEntry localCacheEntry = this.array[j];
    int k = 2147483647;
    if (localCacheEntry.pos == paramInt)
    {
      localCacheEntry.previousCached = true;
      if (localCacheEntry.isRedundant())
      {
        localCacheEntry.removed = true;
        k = Math.min(k, localCacheEntry.index);
      }
    }
    else if (!localCacheEntry.previousCached)
    {
      if (this.tagType == null)
        this.cache.addTagAt(paramInt, false);
      else
        addTagAt(paramInt, null);
      localCacheEntry = this.array[(j = getIndexOfPos(paramInt))];
      if (localCacheEntry.pos == paramInt)
      {
        localCacheEntry.previousCached = true;
        if (localCacheEntry.isRedundant())
        {
          localCacheEntry.removed = true;
          k = Math.min(k, localCacheEntry.index);
        }
      }
    }
    while (true)
    {
      localCacheEntry = this.array[(--j)];
      if (localCacheEntry.pos <= i)
        break;
      if (localCacheEntry.tag != null)
      {
        if (localCacheEntry.tag.includeInSearch())
          throw new SourceCacheEntryMissingInternalError(this.tagType, paramTag, this);
        localCacheEntry.previousCached = true;
        localCacheEntry.nextCached = true;
      }
      else
      {
        localCacheEntry.removed = true;
        k = Math.min(k, localCacheEntry.index);
      }
    }
    if (localCacheEntry.pos != i)
      throw new FoundCacheEntryMissingInternalError(this.tagType, paramTag, this);
    localCacheEntry.nextCached = true;
    compact(k);
  }

  private void addNextTag(int paramInt, Tag paramTag)
  {
    int i = paramTag == null ? this.eof.pos : paramTag.begin;
    if (i == paramInt)
      return;
    int j = getIndexOfPos(paramInt);
    CacheEntry localCacheEntry = this.array[j];
    int k = 2147483647;
    if (localCacheEntry.pos == paramInt)
    {
      localCacheEntry.nextCached = true;
      if (localCacheEntry.isRedundant())
      {
        localCacheEntry.removed = true;
        k = Math.min(k, localCacheEntry.index);
      }
    }
    else if (!getPrevious(localCacheEntry).nextCached)
    {
      if (this.tagType == null)
        this.cache.addTagAt(paramInt, false);
      else
        addTagAt(paramInt, null);
      localCacheEntry = this.array[(j = getIndexOfPos(paramInt))];
      if (localCacheEntry.pos == paramInt)
      {
        localCacheEntry.nextCached = true;
        if (localCacheEntry.isRedundant())
        {
          localCacheEntry.removed = true;
          k = Math.min(k, localCacheEntry.index);
        }
      }
    }
    if (localCacheEntry.pos < i)
    {
      while (true)
      {
        localCacheEntry = this.array[(++j)];
        if (localCacheEntry.pos >= i)
          break;
        if (localCacheEntry.tag != null)
        {
          if (localCacheEntry.tag.includeInSearch())
            throw new SourceCacheEntryMissingInternalError(this.tagType, paramTag, this);
          localCacheEntry.previousCached = true;
          localCacheEntry.nextCached = true;
        }
        else
        {
          localCacheEntry.removed = true;
          k = Math.min(k, localCacheEntry.index);
        }
      }
      if (localCacheEntry.pos != i)
        throw new FoundCacheEntryMissingInternalError(this.tagType, paramTag, this);
    }
    localCacheEntry.previousCached = true;
    compact(k);
  }

  private void compact(int paramInt)
  {
    int i = lastIndex();
    int j = 1;
    while (paramInt < i)
    {
      CacheEntry localCacheEntry = this.array[(++paramInt)];
      if (localCacheEntry.removed)
      {
        j++;
      }
      else
      {
        int tmp46_45 = (paramInt - j);
        localCacheEntry.index = tmp46_45;
        this.array[tmp46_45] = localCacheEntry;
      }
    }
  }

  private void add(CacheEntry paramCacheEntry1, CacheEntry paramCacheEntry2, CacheEntry paramCacheEntry3)
  {
    if (!paramCacheEntry2.isRedundant())
      insert(paramCacheEntry2);
    if (paramCacheEntry2.previousCached)
    {
      paramCacheEntry1.nextCached = true;
      if (paramCacheEntry1.isRedundant())
        remove(paramCacheEntry1);
    }
    if (paramCacheEntry2.nextCached)
    {
      paramCacheEntry3.previousCached = true;
      if (paramCacheEntry3.isRedundant())
        remove(paramCacheEntry3);
    }
  }

  private int getIndexOfPos(int paramInt)
  {
    int i = 0;
    int j = lastIndex();
    for (int k = j >> 1; ; k = i + j >> 1)
    {
      CacheEntry localCacheEntry1 = this.array[k];
      CacheEntry localCacheEntry2;
      if (paramInt > localCacheEntry1.pos)
      {
        localCacheEntry2 = getNext(localCacheEntry1);
        if (paramInt <= localCacheEntry2.pos)
          return localCacheEntry2.index;
        i = localCacheEntry2.index;
      }
      else if (paramInt < localCacheEntry1.pos)
      {
        localCacheEntry2 = getPrevious(localCacheEntry1);
        if (paramInt == localCacheEntry2.pos)
          return localCacheEntry2.index;
        if (paramInt > localCacheEntry2.pos)
          return k;
        j = localCacheEntry2.index;
      }
      else
      {
        return k;
      }
    }
  }

  private CacheEntry getNext(CacheEntry paramCacheEntry)
  {
    return this.array[(paramCacheEntry.index + 1)];
  }

  private CacheEntry getPrevious(CacheEntry paramCacheEntry)
  {
    return this.array[(paramCacheEntry.index - 1)];
  }

  private int lastIndex()
  {
    return this.eof.index;
  }

  private void insert(CacheEntry paramCacheEntry)
  {
    int i = paramCacheEntry.index;
    if (this.array.length == size())
      doubleCapacity();
    for (int j = lastIndex(); j >= i; j--)
    {
      CacheEntry localCacheEntry = this.array[j];
      int tmp48_47 = (j + 1);
      localCacheEntry.index = tmp48_47;
      this.array[tmp48_47] = localCacheEntry;
    }
    this.array[i] = paramCacheEntry;
  }

  private void remove(CacheEntry paramCacheEntry)
  {
    int i = lastIndex();
    for (int j = paramCacheEntry.index; j < i; j++)
    {
      CacheEntry localCacheEntry = this.array[(j + 1)];
      int tmp32_31 = j;
      localCacheEntry.index = tmp32_31;
      this.array[tmp32_31] = localCacheEntry;
    }
  }

  private void doubleCapacity()
  {
    CacheEntry[] arrayOfCacheEntry = new CacheEntry[this.array.length << 1];
    for (int i = lastIndex(); i >= 0; i--)
      arrayOfCacheEntry[i] = this.array[i];
    this.array = arrayOfCacheEntry;
  }

  private static final class CacheEntry
  {
    public int index;
    public final int pos;
    public final Tag tag;
    public boolean previousCached;
    public boolean nextCached;
    public boolean removed = false;

    public CacheEntry(int paramInt1, int paramInt2, Tag paramTag, boolean paramBoolean1, boolean paramBoolean2)
    {
      this.index = paramInt1;
      this.pos = paramInt2;
      this.tag = paramTag;
      this.previousCached = paramBoolean1;
      this.nextCached = paramBoolean2;
    }

    public boolean isRedundant()
    {
      return (this.tag == null) && (this.previousCached) && (this.nextCached);
    }

    public String toString()
    {
      return pad(this.index, 4) + " " + pad(this.pos, 5) + " " + (this.previousCached ? '|' : '-') + ' ' + (this.nextCached ? '|' : '-') + ' ' + (this.tag == null ? "null" : this.tag.getDebugInfo());
    }

    private String pad(int paramInt1, int paramInt2)
    {
      String str = String.valueOf(paramInt1);
      StringBuilder localStringBuilder = new StringBuilder(paramInt2);
      for (int i = paramInt2 - str.length(); i > 0; i--)
        localStringBuilder.append(' ');
      localStringBuilder.append(str);
      return localStringBuilder.toString();
    }
  }

  private final class TagIterator
    implements Iterator<Tag>
  {
    private int i = 0;
    private Tag nextTag;

    public TagIterator()
    {
      loadNextTag();
    }

    public boolean hasNext()
    {
      return this.nextTag != null;
    }

    public Tag next()
    {
      Tag localTag = this.nextTag;
      loadNextTag();
      return localTag;
    }

    public void remove()
    {
      throw new UnsupportedOperationException();
    }

    private void loadNextTag()
    {
      while ((++this.i <= SubCache.this.lastIndex()) && ((this.nextTag = SubCache.this.array[this.i].tag) == null));
    }
  }

  private static class FoundCacheEntryMissingInternalError extends SubCache.CacheEntryMissingInternalError
  {
    public FoundCacheEntryMissingInternalError(TagType paramTagType, Tag paramTag, SubCache paramSubCache)
    {
      super(paramTag, paramSubCache, "missing cache entry for found tag");
    }
  }

  private static class SourceCacheEntryMissingInternalError extends SubCache.CacheEntryMissingInternalError
  {
    public SourceCacheEntryMissingInternalError(TagType paramTagType, Tag paramTag, SubCache paramSubCache)
    {
      super(paramTag, paramSubCache, "cache entry no longer found in source:");
    }
  }

  private static class CacheEntryMissingInternalError extends AssertionError
  {
    public CacheEntryMissingInternalError(TagType paramTagType, Tag paramTag, SubCache paramSubCache, String paramString)
    {
      super();
    }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.SubCache
 * JD-Core Version:    0.6.2
 */