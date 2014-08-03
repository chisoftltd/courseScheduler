package net.htmlparser.jericho;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public class Segment
  implements Comparable<Segment>, CharSequence
{
  final int begin;
  final int end;
  final Source source;
  private static final char[] WHITESPACE = { ' ', '\n', '\r', '\t', '\f', '​' };

  public Segment(Source paramSource, int paramInt1, int paramInt2)
  {
    if ((paramInt1 == -1) || (paramInt2 == -1) || (paramInt1 > paramInt2))
      throw new IllegalArgumentException();
    this.begin = paramInt1;
    this.end = paramInt2;
    if (paramSource == null)
      throw new IllegalArgumentException("source argument must not be null");
    this.source = paramSource;
  }

  Segment(int paramInt)
  {
    this.begin = 0;
    this.end = paramInt;
    this.source = ((Source)this);
  }

  Segment()
  {
    this(0, 0);
  }

  Segment(int paramInt1, int paramInt2)
  {
    this.begin = paramInt1;
    this.end = paramInt2;
    this.source = null;
  }

  public final Source getSource()
  {
    if (this.source.isStreamed())
      throw new UnsupportedOperationException("Source object is not available when using StreamedSource");
    return this.source;
  }

  public final int getBegin()
  {
    return this.begin;
  }

  public final int getEnd()
  {
    return this.end;
  }

  public final boolean equals(Object paramObject)
  {
    if (this == paramObject)
      return true;
    if ((paramObject == null) || (!(paramObject instanceof Segment)))
      return false;
    Segment localSegment = (Segment)paramObject;
    return (localSegment.begin == this.begin) && (localSegment.end == this.end) && (localSegment.source == this.source);
  }

  public int hashCode()
  {
    return this.begin + this.end;
  }

  public int length()
  {
    return this.end - this.begin;
  }

  public final boolean encloses(Segment paramSegment)
  {
    return (this.begin <= paramSegment.begin) && (this.end >= paramSegment.end);
  }

  public final boolean encloses(int paramInt)
  {
    return (this.begin <= paramInt) && (paramInt < this.end);
  }

  public String toString()
  {
    return this.source.subSequence(this.begin, this.end).toString();
  }

  public Renderer getRenderer()
  {
    return new Renderer(this);
  }

  public TextExtractor getTextExtractor()
  {
    return new TextExtractor(this);
  }

  public Iterator<Segment> getNodeIterator()
  {
    return new NodeIterator(this);
  }

  public List<Tag> getAllTags()
  {
    return getAllTags(null);
  }

  public List<Tag> getAllTags(TagType paramTagType)
  {
    Tag localTag = checkTagEnclosure(Tag.getNextTag(this.source, this.begin, paramTagType));
    if (localTag == null)
      return Collections.emptyList();
    ArrayList localArrayList = new ArrayList();
    do
    {
      localArrayList.add(localTag);
      localTag = checkTagEnclosure(localTag.getNextTag(paramTagType));
    }
    while (localTag != null);
    return localArrayList;
  }

  public List<StartTag> getAllStartTags()
  {
    StartTag localStartTag = checkEnclosure(StartTag.getNext(this.source, this.begin));
    if (localStartTag == null)
      return Collections.emptyList();
    ArrayList localArrayList = new ArrayList();
    do
    {
      localArrayList.add(localStartTag);
      localStartTag = checkEnclosure(localStartTag.getNextStartTag());
    }
    while (localStartTag != null);
    return localArrayList;
  }

  public List<StartTag> getAllStartTags(StartTagType paramStartTagType)
  {
    if (paramStartTagType == null)
      return getAllStartTags();
    StartTag localStartTag = (StartTag)checkTagEnclosure(Tag.getNextTag(this.source, this.begin, paramStartTagType));
    if (localStartTag == null)
      return Collections.emptyList();
    ArrayList localArrayList = new ArrayList();
    do
    {
      localArrayList.add(localStartTag);
      localStartTag = (StartTag)checkTagEnclosure(localStartTag.getNextTag(paramStartTagType));
    }
    while (localStartTag != null);
    return localArrayList;
  }

  public List<StartTag> getAllStartTags(String paramString)
  {
    if (paramString == null)
      return getAllStartTags();
    boolean bool = Tag.isXMLName(paramString);
    StartTag localStartTag = checkEnclosure(StartTag.getNext(this.source, this.begin, paramString, StartTagType.NORMAL, bool));
    if (localStartTag == null)
      return Collections.emptyList();
    ArrayList localArrayList = new ArrayList();
    do
    {
      localArrayList.add(localStartTag);
      localStartTag = checkEnclosure(StartTag.getNext(this.source, localStartTag.begin + 1, paramString, StartTagType.NORMAL, bool));
    }
    while (localStartTag != null);
    return localArrayList;
  }

  public List<StartTag> getAllStartTags(String paramString1, String paramString2, boolean paramBoolean)
  {
    StartTag localStartTag = checkEnclosure(this.source.getNextStartTag(this.begin, paramString1, paramString2, paramBoolean));
    if (localStartTag == null)
      return Collections.emptyList();
    ArrayList localArrayList = new ArrayList();
    do
    {
      localArrayList.add(localStartTag);
      localStartTag = checkEnclosure(this.source.getNextStartTag(localStartTag.begin + 1, paramString1, paramString2, paramBoolean));
    }
    while (localStartTag != null);
    return localArrayList;
  }

  public List<StartTag> getAllStartTags(String paramString, Pattern paramPattern)
  {
    StartTag localStartTag = checkEnclosure(this.source.getNextStartTag(this.begin, paramString, paramPattern));
    if (localStartTag == null)
      return Collections.emptyList();
    ArrayList localArrayList = new ArrayList();
    do
    {
      localArrayList.add(localStartTag);
      localStartTag = checkEnclosure(this.source.getNextStartTag(localStartTag.begin + 1, paramString, paramPattern));
    }
    while (localStartTag != null);
    return localArrayList;
  }

  public List<StartTag> getAllStartTagsByClass(String paramString)
  {
    return getAllStartTags("class", getClassPattern(paramString));
  }

  public List<Element> getChildElements()
  {
    if (length() == 0)
      return Collections.emptyList();
    ArrayList localArrayList = new ArrayList();
    int i = this.begin;
    while (true)
    {
      StartTag localStartTag = this.source.getNextStartTag(i);
      if ((localStartTag == null) || (localStartTag.begin >= this.end))
        break;
      if (localStartTag.getTagType().isServerTag())
      {
        i = localStartTag.end;
      }
      else
      {
        Element localElement = localStartTag.getElement();
        localArrayList.add(localElement);
        localElement.getChildElements();
        i = localElement.end;
      }
    }
    return localArrayList;
  }

  public List<Element> getAllElements()
  {
    return getAllElements(getAllStartTags());
  }

  public List<Element> getAllElements(String paramString)
  {
    return getAllElements(getAllStartTags(paramString));
  }

  public List<Element> getAllElements(StartTagType paramStartTagType)
  {
    if (paramStartTagType == null)
      throw new IllegalArgumentException("startTagType argument must not be null");
    return getAllElements(getAllStartTags(paramStartTagType));
  }

  public List<Element> getAllElements(String paramString1, String paramString2, boolean paramBoolean)
  {
    return getAllElements(getAllStartTags(paramString1, paramString2, paramBoolean));
  }

  public List<Element> getAllElements(String paramString, Pattern paramPattern)
  {
    return getAllElements(getAllStartTags(paramString, paramPattern));
  }

  public List<Element> getAllElementsByClass(String paramString)
  {
    return getAllElements(getAllStartTagsByClass(paramString));
  }

  public List<CharacterReference> getAllCharacterReferences()
  {
    CharacterReference localCharacterReference = getNextCharacterReference(this.begin);
    if (localCharacterReference == null)
      return Collections.emptyList();
    ArrayList localArrayList = new ArrayList();
    do
    {
      localArrayList.add(localCharacterReference);
      localCharacterReference = getNextCharacterReference(localCharacterReference.end);
    }
    while (localCharacterReference != null);
    return localArrayList;
  }

  public final StartTag getFirstStartTag()
  {
    return checkEnclosure(this.source.getNextStartTag(this.begin));
  }

  public final StartTag getFirstStartTag(StartTagType paramStartTagType)
  {
    return checkEnclosure(this.source.getNextStartTag(this.begin, paramStartTagType));
  }

  public final StartTag getFirstStartTag(String paramString)
  {
    return checkEnclosure(this.source.getNextStartTag(this.begin, paramString));
  }

  public final StartTag getFirstStartTag(String paramString1, String paramString2, boolean paramBoolean)
  {
    return checkEnclosure(this.source.getNextStartTag(this.begin, paramString1, paramString2, paramBoolean));
  }

  public final StartTag getFirstStartTag(String paramString, Pattern paramPattern)
  {
    return checkEnclosure(this.source.getNextStartTag(this.begin, paramString, paramPattern));
  }

  public final StartTag getFirstStartTagByClass(String paramString)
  {
    return checkEnclosure(this.source.getNextStartTagByClass(this.begin, paramString));
  }

  public final Element getFirstElement()
  {
    for (StartTag localStartTag = checkEnclosure(StartTag.getNext(this.source, this.begin)); localStartTag != null; localStartTag = checkEnclosure(localStartTag.getNextStartTag()))
    {
      Element localElement = localStartTag.getElement();
      if (localElement.end <= this.end)
        return localElement;
    }
    return null;
  }

  public final Element getFirstElement(String paramString)
  {
    if (paramString == null)
      return getFirstElement();
    boolean bool = Tag.isXMLName(paramString);
    for (StartTag localStartTag = checkEnclosure(StartTag.getNext(this.source, this.begin, paramString, StartTagType.NORMAL, bool)); localStartTag != null; localStartTag = checkEnclosure(StartTag.getNext(this.source, localStartTag.begin + 1, paramString, StartTagType.NORMAL, bool)))
    {
      Element localElement = localStartTag.getElement();
      if (localElement.end <= this.end)
        return localElement;
    }
    return null;
  }

  public final Element getFirstElement(String paramString1, String paramString2, boolean paramBoolean)
  {
    for (StartTag localStartTag = checkEnclosure(this.source.getNextStartTag(this.begin, paramString1, paramString2, paramBoolean)); localStartTag != null; localStartTag = checkEnclosure(this.source.getNextStartTag(localStartTag.begin + 1, paramString1, paramString2, paramBoolean)))
    {
      Element localElement = localStartTag.getElement();
      if (localElement.end <= this.end)
        return localElement;
    }
    return null;
  }

  public final Element getFirstElement(String paramString, Pattern paramPattern)
  {
    for (StartTag localStartTag = checkEnclosure(this.source.getNextStartTag(this.begin, paramString, paramPattern)); localStartTag != null; localStartTag = checkEnclosure(this.source.getNextStartTag(localStartTag.begin + 1, paramString, paramPattern)))
    {
      Element localElement = localStartTag.getElement();
      if (localElement.end <= this.end)
        return localElement;
    }
    return null;
  }

  public final Element getFirstElementByClass(String paramString)
  {
    for (StartTag localStartTag = checkEnclosure(this.source.getNextStartTagByClass(this.begin, paramString)); localStartTag != null; localStartTag = checkEnclosure(this.source.getNextStartTagByClass(localStartTag.begin + 1, paramString)))
    {
      Element localElement = localStartTag.getElement();
      if (localElement.end <= this.end)
        return localElement;
    }
    return null;
  }

  public List<FormControl> getFormControls()
  {
    return FormControl.getAll(this);
  }

  public FormFields getFormFields()
  {
    return new FormFields(getFormControls());
  }

  public Attributes parseAttributes()
  {
    return this.source.parseAttributes(this.begin, this.end);
  }

  public void ignoreWhenParsing()
  {
    this.source.ignoreWhenParsing(this.begin, this.end);
  }

  public int compareTo(Segment paramSegment)
  {
    if (this == paramSegment)
      return 0;
    if (this.begin < paramSegment.begin)
      return -1;
    if (this.begin > paramSegment.begin)
      return 1;
    if (this.end < paramSegment.end)
      return -1;
    if (this.end > paramSegment.end)
      return 1;
    return 0;
  }

  public final boolean isWhiteSpace()
  {
    for (int i = this.begin; i < this.end; i++)
      if (!isWhiteSpace(this.source.charAt(i)))
        return false;
    return true;
  }

  public static final boolean isWhiteSpace(char paramChar)
  {
    for (char c : WHITESPACE)
      if (paramChar == c)
        return true;
    return false;
  }

  public String getDebugInfo()
  {
    StringBuilder localStringBuilder = new StringBuilder(50);
    localStringBuilder.append('(');
    this.source.getRowColumnVector(this.begin).appendTo(localStringBuilder);
    localStringBuilder.append('-');
    this.source.getRowColumnVector(this.end).appendTo(localStringBuilder);
    localStringBuilder.append(')');
    return localStringBuilder.toString();
  }

  public char charAt(int paramInt)
  {
    return this.source.charAt(this.begin + paramInt);
  }

  public CharSequence subSequence(int paramInt1, int paramInt2)
  {
    return this.source.subSequence(this.begin + paramInt1, this.begin + paramInt2);
  }

  static final StringBuilder appendCollapseWhiteSpace(StringBuilder paramStringBuilder, CharSequence paramCharSequence)
  {
    int i = paramCharSequence.length();
    int j = 0;
    int k = 0;
    while (true)
    {
      if (j >= i)
        return paramStringBuilder;
      if (!isWhiteSpace(paramCharSequence.charAt(j)))
        break;
      j++;
    }
    do
    {
      char c = paramCharSequence.charAt(j++);
      if (isWhiteSpace(c))
      {
        k = 1;
      }
      else
      {
        if (k != 0)
        {
          paramStringBuilder.append(' ');
          k = 0;
        }
        paramStringBuilder.append(c);
      }
    }
    while (j < i);
    return paramStringBuilder;
  }

  static final Pattern getClassPattern(String paramString)
  {
    return Pattern.compile(".*(\\s|^)" + paramString + "(\\s|$).*", 32);
  }

  private List<Element> getAllElements(List<StartTag> paramList)
  {
    if (paramList.isEmpty())
      return Collections.emptyList();
    ArrayList localArrayList = new ArrayList(paramList.size());
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      StartTag localStartTag = (StartTag)localIterator.next();
      Element localElement = localStartTag.getElement();
      if (localElement.end <= this.end)
        localArrayList.add(localElement);
    }
    return localArrayList;
  }

  private StartTag checkEnclosure(StartTag paramStartTag)
  {
    if ((paramStartTag == null) || (paramStartTag.end > this.end))
      return null;
    return paramStartTag;
  }

  private Tag checkTagEnclosure(Tag paramTag)
  {
    if ((paramTag == null) || (paramTag.end > this.end))
      return null;
    return paramTag;
  }

  private CharacterReference getNextCharacterReference(int paramInt)
  {
    CharacterReference localCharacterReference = this.source.getNextCharacterReference(paramInt);
    if ((localCharacterReference == null) || (localCharacterReference.end > this.end))
      return null;
    return localCharacterReference;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.Segment
 * JD-Core Version:    0.6.2
 */