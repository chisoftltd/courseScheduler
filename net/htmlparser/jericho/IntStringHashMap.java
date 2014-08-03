package net.htmlparser.jericho;

final class IntStringHashMap
{
  private static final int DEFAULT_INITIAL_CAPACITY = 15;
  private static final float DEFAULT_LOAD_FACTOR = 0.75F;
  private transient Entry[] entries;
  private transient int size;
  private int threshold;
  private float loadFactor;
  private int bitmask;

  public IntStringHashMap(int paramInt, float paramFloat)
  {
    this.loadFactor = paramFloat;
    int i = 1;
    while (i < paramInt)
      i <<= 1;
    this.threshold = ((int)(i * paramFloat));
    this.entries = new Entry[i];
    this.bitmask = (i - 1);
  }

  public IntStringHashMap(int paramInt)
  {
    this(paramInt, 0.75F);
  }

  public IntStringHashMap()
  {
    this(15, 0.75F);
  }

  public int size()
  {
    return this.size;
  }

  public boolean isEmpty()
  {
    return this.size == 0;
  }

  private int getIndex(int paramInt)
  {
    return paramInt & this.bitmask;
  }

  public String get(int paramInt)
  {
    for (Entry localEntry = this.entries[getIndex(paramInt)]; localEntry != null; localEntry = localEntry.next)
      if (paramInt == localEntry.key)
        return localEntry.value;
    return null;
  }

  private Entry getEntry(int paramInt)
  {
    for (Entry localEntry = this.entries[getIndex(paramInt)]; (localEntry != null) && (paramInt != localEntry.key); localEntry = localEntry.next);
    return localEntry;
  }

  public boolean containsKey(int paramInt)
  {
    return getEntry(paramInt) != null;
  }

  public String put(int paramInt, String paramString)
  {
    int i = getIndex(paramInt);
    for (Entry localEntry = this.entries[i]; localEntry != null; localEntry = localEntry.next)
      if (paramInt == localEntry.key)
      {
        String str = localEntry.value;
        localEntry.value = paramString;
        return str;
      }
    this.entries[i] = new Entry(paramInt, paramString, this.entries[i]);
    if (this.size++ >= this.threshold)
      increaseCapacity();
    return null;
  }

  private void increaseCapacity()
  {
    int i = this.entries.length;
    Entry[] arrayOfEntry1 = this.entries;
    this.entries = new Entry[i << 1];
    this.bitmask = (this.entries.length - 1);
    Entry[] arrayOfEntry2 = arrayOfEntry1;
    int j = arrayOfEntry2.length;
    for (int k = 0; k < j; k++)
    {
      Entry localEntry;
      for (Object localObject = arrayOfEntry2[k]; localObject != null; localObject = localEntry)
      {
        localEntry = ((Entry)localObject).next;
        int m = getIndex(((Entry)localObject).key);
        ((Entry)localObject).next = this.entries[m];
        this.entries[m] = localObject;
      }
    }
    this.threshold = ((int)(this.entries.length * this.loadFactor));
  }

  public String remove(int paramInt)
  {
    int i = getIndex(paramInt);
    Object localObject = null;
    for (Entry localEntry = this.entries[i]; localEntry != null; localEntry = (localObject = localEntry).next)
      if (paramInt == localEntry.key)
      {
        if (localObject == null)
          this.entries[i] = localEntry.next;
        else
          localObject.next = localEntry.next;
        this.size -= 1;
        return localEntry.value;
      }
    return null;
  }

  public void clear()
  {
    for (int i = this.bitmask; i >= 0; i--)
      this.entries[i] = null;
    this.size = 0;
  }

  public boolean containsValue(String paramString)
  {
    int i;
    Entry localEntry;
    if (paramString == null)
      for (i = this.bitmask; i >= 0; i--)
        for (localEntry = this.entries[i]; localEntry != null; localEntry = localEntry.next)
          if (localEntry.value == null)
            return true;
    else
      for (i = this.bitmask; i >= 0; i--)
        for (localEntry = this.entries[i]; localEntry != null; localEntry = localEntry.next)
          if (paramString.equals(localEntry.value))
            return true;
    return false;
  }

  private static final class Entry
  {
    final int key;
    String value;
    Entry next;

    public Entry(int paramInt, String paramString, Entry paramEntry)
    {
      this.key = paramInt;
      this.value = paramString;
      this.next = paramEntry;
    }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.IntStringHashMap
 * JD-Core Version:    0.6.2
 */