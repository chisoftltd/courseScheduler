package net.htmlparser.jericho;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

final class TagTypeRegister
{
  private TagTypeRegister parent = null;
  private char ch = '\000';
  private TagTypeRegister[] children = null;
  private TagType[] tagTypes = null;
  private static final char NULL_CHAR = '\000';
  private static final TagType[] DEFAULT_TAG_TYPES = { StartTagType.UNREGISTERED, StartTagType.NORMAL, StartTagType.COMMENT, StartTagType.MARKUP_DECLARATION, StartTagType.DOCTYPE_DECLARATION, StartTagType.CDATA_SECTION, StartTagType.XML_PROCESSING_INSTRUCTION, StartTagType.XML_DECLARATION, StartTagType.SERVER_COMMON, StartTagType.SERVER_COMMON_ESCAPED, EndTagType.UNREGISTERED, EndTagType.NORMAL };
  private static TagTypeRegister root = new TagTypeRegister();

  private static synchronized void add(TagType[] paramArrayOfTagType)
  {
    for (int i = 0; i < paramArrayOfTagType.length; i++)
      add(paramArrayOfTagType[i]);
  }

  public static synchronized void add(TagType paramTagType)
  {
    Object localObject = root;
    String str = paramTagType.getStartDelimiter();
    for (int i = 0; i < str.length(); i++)
    {
      char c = str.charAt(i);
      TagTypeRegister localTagTypeRegister = ((TagTypeRegister)localObject).getChild(c);
      if (localTagTypeRegister == null)
      {
        localTagTypeRegister = new TagTypeRegister();
        localTagTypeRegister.parent = ((TagTypeRegister)localObject);
        localTagTypeRegister.ch = c;
        ((TagTypeRegister)localObject).addChild(localTagTypeRegister);
      }
      localObject = localTagTypeRegister;
    }
    ((TagTypeRegister)localObject).addTagType(paramTagType);
  }

  public static synchronized void remove(TagType paramTagType)
  {
    Object localObject = root;
    String str = paramTagType.getStartDelimiter();
    for (int i = 0; i < str.length(); i++)
    {
      char c = str.charAt(i);
      TagTypeRegister localTagTypeRegister = ((TagTypeRegister)localObject).getChild(c);
      if (localTagTypeRegister == null)
        return;
      localObject = localTagTypeRegister;
    }
    ((TagTypeRegister)localObject).removeTagType(paramTagType);
    while ((localObject != root) && (((TagTypeRegister)localObject).tagTypes == null) && (((TagTypeRegister)localObject).children == null))
    {
      ((TagTypeRegister)localObject).parent.removeChild((TagTypeRegister)localObject);
      localObject = ((TagTypeRegister)localObject).parent;
    }
  }

  public static List<TagType> getList()
  {
    ArrayList localArrayList = new ArrayList();
    root.addTagTypesToList(localArrayList);
    return localArrayList;
  }

  private void addTagTypesToList(List<TagType> paramList)
  {
    if (this.tagTypes != null)
      for (int i = this.tagTypes.length - 1; i >= 0; i--)
        paramList.add(this.tagTypes[i]);
    if (this.children != null)
      for (TagTypeRegister localTagTypeRegister : this.children)
        localTagTypeRegister.addTagTypesToList(paramList);
  }

  public static final String getDebugInfo()
  {
    return root.appendDebugInfo(new StringBuilder(), 0).toString();
  }

  public String toString()
  {
    return appendDebugInfo(new StringBuilder(), 0).toString();
  }

  private StringBuilder appendDebugInfo(StringBuilder paramStringBuilder, int paramInt)
  {
    for (int i = 0; i < paramInt; i++)
      paramStringBuilder.append(" ");
    if (this.ch != 0)
      paramStringBuilder.append(this.ch).append(' ');
    if (this.tagTypes != null)
    {
      paramStringBuilder.append('(');
      for (TagType localTagType : this.tagTypes)
        paramStringBuilder.append(localTagType.getDescription()).append(", ");
      paramStringBuilder.setLength(paramStringBuilder.length() - 2);
      paramStringBuilder.append(')');
    }
    paramStringBuilder.append(Config.NewLine);
    if (this.children != null)
    {
      int j = paramInt + 1;
      for (TagTypeRegister localTagTypeRegister : this.children)
        localTagTypeRegister.appendDebugInfo(paramStringBuilder, j);
    }
    return paramStringBuilder;
  }

  private TagTypeRegister getChild(char paramChar)
  {
    if (this.children == null)
      return null;
    if (this.children.length == 1)
      return this.children[0].ch == paramChar ? this.children[0] : null;
    int i = 0;
    int j = this.children.length - 1;
    while (i <= j)
    {
      int k = i + j >> 1;
      char c = this.children[k].ch;
      if (c < paramChar)
        i = k + 1;
      else if (c > paramChar)
        j = k - 1;
      else
        return this.children[k];
    }
    return null;
  }

  private void addChild(TagTypeRegister paramTagTypeRegister)
  {
    if (this.children == null)
    {
      this.children = new TagTypeRegister[] { paramTagTypeRegister };
    }
    else
    {
      TagTypeRegister[] arrayOfTagTypeRegister = new TagTypeRegister[this.children.length + 1];
      for (int i = 0; (i < this.children.length) && (this.children[i].ch <= paramTagTypeRegister.ch); i++)
        arrayOfTagTypeRegister[i] = this.children[i];
      arrayOfTagTypeRegister[(i++)] = paramTagTypeRegister;
      while (i < arrayOfTagTypeRegister.length)
      {
        arrayOfTagTypeRegister[i] = this.children[(i - 1)];
        i++;
      }
      this.children = arrayOfTagTypeRegister;
    }
  }

  private void removeChild(TagTypeRegister paramTagTypeRegister)
  {
    if (this.children.length == 1)
    {
      this.children = null;
      return;
    }
    TagTypeRegister[] arrayOfTagTypeRegister = new TagTypeRegister[this.children.length - 1];
    int i = 0;
    for (int j = 0; j < this.children.length; j++)
      if (this.children[j] == paramTagTypeRegister)
        i = -1;
      else
        arrayOfTagTypeRegister[(j + i)] = this.children[j];
    this.children = arrayOfTagTypeRegister;
  }

  private int indexOfTagType(TagType paramTagType)
  {
    if (this.tagTypes == null)
      return -1;
    for (int i = 0; i < this.tagTypes.length; i++)
      if (this.tagTypes[i] == paramTagType)
        return i;
    return -1;
  }

  private void addTagType(TagType paramTagType)
  {
    TagType[] arrayOfTagType1 = indexOfTagType(paramTagType);
    TagType[] arrayOfTagType2;
    if (arrayOfTagType1 == -1)
    {
      if (this.tagTypes == null)
      {
        this.tagTypes = new TagType[] { paramTagType };
      }
      else
      {
        arrayOfTagType2 = new TagType[this.tagTypes.length + 1];
        arrayOfTagType2[0] = paramTagType;
        for (int i = 0; i < this.tagTypes.length; i++)
          arrayOfTagType2[(i + 1)] = this.tagTypes[i];
        this.tagTypes = arrayOfTagType2;
      }
    }
    else
    {
      for (arrayOfTagType2 = arrayOfTagType1; arrayOfTagType2 > 0; arrayOfTagType2--)
        this.tagTypes[arrayOfTagType2] = this.tagTypes[(arrayOfTagType2 - 1)];
      this.tagTypes[0] = paramTagType;
    }
  }

  private void removeTagType(TagType paramTagType)
  {
    int i = indexOfTagType(paramTagType);
    if (i == -1)
      return;
    if (this.tagTypes.length == 1)
    {
      this.tagTypes = null;
      return;
    }
    TagType[] arrayOfTagType = new TagType[this.tagTypes.length - 1];
    for (int j = 0; j < i; j++)
      arrayOfTagType[j] = this.tagTypes[j];
    for (j = i; j < arrayOfTagType.length; j++)
      arrayOfTagType[j] = this.tagTypes[(j + 1)];
    this.tagTypes = arrayOfTagType;
  }

  static
  {
    add(DEFAULT_TAG_TYPES);
  }

  static final class ProspectiveTagTypeIterator
    implements Iterator<TagType>
  {
    private TagTypeRegister cursor;
    private int tagTypeIndex = 0;

    public ProspectiveTagTypeIterator(Source paramSource, int paramInt)
    {
      ParseText localParseText = paramSource.getParseText();
      this.cursor = TagTypeRegister.root;
      int i = 0;
      try
      {
        while (true)
        {
          TagTypeRegister localTagTypeRegister = this.cursor.getChild(localParseText.charAt(paramInt + i++));
          if (localTagTypeRegister == null)
            break;
          this.cursor = localTagTypeRegister;
        }
      }
      catch (IndexOutOfBoundsException localIndexOutOfBoundsException)
      {
      }
      while (this.cursor.tagTypes == null)
        if ((this.cursor = this.cursor.parent) == null)
          break;
    }

    public boolean hasNext()
    {
      return this.cursor != null;
    }

    public TagType next()
    {
      TagType[] arrayOfTagType = this.cursor.tagTypes;
      TagType localTagType = arrayOfTagType[this.tagTypeIndex];
      if (++this.tagTypeIndex == arrayOfTagType.length)
      {
        this.tagTypeIndex = 0;
        do
          this.cursor = this.cursor.parent;
        while ((this.cursor != null) && (this.cursor.tagTypes == null));
      }
      return localTagType;
    }

    public void remove()
    {
      throw new UnsupportedOperationException();
    }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.TagTypeRegister
 * JD-Core Version:    0.6.2
 */