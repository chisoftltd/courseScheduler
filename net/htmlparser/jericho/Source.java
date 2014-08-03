package net.htmlparser.jericho;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public final class Source extends Segment
  implements Iterable<Segment>
{
  private final CharSequence sourceText;
  private String documentSpecifiedEncoding = "";
  private String encoding = "";
  private String encodingSpecificationInfo;
  private String preliminaryEncodingInfo = null;
  private String newLine = "";
  private ParseText parseText = null;
  private OutputDocument parseTextOutputDocument = null;
  Logger logger;
  private RowColumnVector[] rowColumnVectorCacheArray = null;
  final Cache cache;
  boolean useAllTypesCache = true;
  boolean useSpecialTypesCache = true;
  int[] fullSequentialParseData = null;
  Tag[] allTagsArray = null;
  List<Tag> allTags = null;
  List<StartTag> allStartTags = null;
  private List<Element> allElements = null;
  private List<Element> childElements = null;
  private static String lastNewLine = null;
  private static final String UNINITIALISED = "";
  private static final String CR = "\r";
  private static final String LF = "\n";
  private static final String CRLF = "\r\n";
  static final String PACKAGE_NAME = Source.class.getPackage().getName();

  @Deprecated
  public static boolean LegacyIteratorCompatabilityMode = false;

  public Source(CharSequence paramCharSequence)
  {
    super(paramCharSequence.length());
    this.sourceText = paramCharSequence.toString();
    setLogger(newLogger());
    this.cache = new Cache(this);
  }

  private Source(EncodingDetector paramEncodingDetector)
    throws IOException
  {
    this(getString(paramEncodingDetector));
    this.encoding = paramEncodingDetector.getEncoding();
    this.encodingSpecificationInfo = paramEncodingDetector.getEncodingSpecificationInfo();
    this.preliminaryEncodingInfo = (paramEncodingDetector.getPreliminaryEncoding() + ": " + paramEncodingDetector.getPreliminaryEncodingSpecificationInfo());
  }

  Source(Reader paramReader, String paramString)
    throws IOException
  {
    this(Util.getString(paramReader));
    if (paramString != null)
    {
      this.encoding = paramString;
      this.encodingSpecificationInfo = "InputStreamReader.getEncoding() of constructor argument";
    }
  }

  Source(CharSequence paramCharSequence, StreamedParseText paramStreamedParseText, String paramString1, String paramString2, String paramString3)
  {
    super(paramStreamedParseText.getEnd());
    this.cache = Cache.STREAMED_SOURCE_MARKER;
    this.useAllTypesCache = false;
    this.useSpecialTypesCache = false;
    this.fullSequentialParseData = new int[1];
    if (paramString1 != null)
      this.encoding = paramString1;
    this.encodingSpecificationInfo = paramString2;
    this.preliminaryEncodingInfo = paramString3;
    this.sourceText = paramCharSequence;
    this.parseText = paramStreamedParseText;
    setLogger(newLogger());
  }

  Source(CharSequence paramCharSequence, boolean paramBoolean)
  {
    super(paramCharSequence.length());
    this.sourceText = paramCharSequence;
    this.cache = null;
    this.useAllTypesCache = false;
    this.useSpecialTypesCache = false;
    setLogger(LoggerDisabled.INSTANCE);
  }

  public Source(Reader paramReader)
    throws IOException
  {
    this(paramReader, (paramReader instanceof InputStreamReader) ? ((InputStreamReader)paramReader).getEncoding() : null);
  }

  public Source(InputStream paramInputStream)
    throws IOException
  {
    this(new EncodingDetector(paramInputStream));
  }

  public Source(URL paramURL)
    throws IOException
  {
    this(new EncodingDetector(paramURL.openConnection()));
  }

  public Source(URLConnection paramURLConnection)
    throws IOException
  {
    this(new EncodingDetector(paramURLConnection));
  }

  private String setEncoding(String paramString1, String paramString2)
  {
    if (this.encoding == "")
    {
      this.encoding = paramString1;
      this.encodingSpecificationInfo = paramString2;
    }
    return paramString1;
  }

  public String getDocumentSpecifiedEncoding()
  {
    if (this.documentSpecifiedEncoding != "")
      return this.documentSpecifiedEncoding;
    Tag localTag = getTagAt(0);
    if ((localTag != null) && (localTag.getTagType() == StartTagType.XML_DECLARATION))
    {
      this.documentSpecifiedEncoding = ((StartTag)localTag).getAttributeValue("encoding");
      if (this.documentSpecifiedEncoding != null)
        return setEncoding(this.documentSpecifiedEncoding, localTag.toString());
    }
    StartTag localStartTag = getFirstStartTag("http-equiv", "content-type", false);
    if (localStartTag != null)
    {
      String str = localStartTag.getAttributeValue("content");
      if (str != null)
      {
        this.documentSpecifiedEncoding = getCharsetParameterFromHttpHeaderValue(str);
        if (this.documentSpecifiedEncoding != null)
          return setEncoding(this.documentSpecifiedEncoding, localStartTag.toString());
      }
    }
    return setEncoding(null, "No encoding specified in document");
  }

  public String getEncoding()
  {
    if (this.encoding == "")
      getDocumentSpecifiedEncoding();
    return this.encoding;
  }

  public String getEncodingSpecificationInfo()
  {
    if (this.encoding == "")
      getDocumentSpecifiedEncoding();
    return this.encodingSpecificationInfo;
  }

  public String getPreliminaryEncodingInfo()
  {
    return this.preliminaryEncodingInfo;
  }

  public boolean isXML()
  {
    Tag localTag1 = getTagAt(0);
    if ((localTag1 != null) && (localTag1.getTagType() == StartTagType.XML_DECLARATION))
      return true;
    Tag localTag2 = getNextTag(0, StartTagType.DOCTYPE_DECLARATION);
    return (localTag2 != null) && (getParseText().indexOf("xhtml", localTag2.begin, localTag2.end) != -1);
  }

  public String getNewLine()
  {
    if (this.newLine != "")
      return this.newLine;
    for (int i = 0; i < this.end; i++)
    {
      int j = this.sourceText.charAt(i);
      if (j == 10)
        return this.newLine = Source.lastNewLine = "\n";
      if (j == 13)
        return this.newLine = Source.lastNewLine = (++i < this.end) && (this.sourceText.charAt(i) == '\n') ? "\r\n" : "\r";
    }
    return this.newLine = null;
  }

  String getBestGuessNewLine()
  {
    String str = getNewLine();
    if (str != null)
      return str;
    if (lastNewLine != null)
      return lastNewLine;
    return Config.NewLine;
  }

  public int getRow(int paramInt)
  {
    return getRowColumnVector(paramInt).getRow();
  }

  public int getColumn(int paramInt)
  {
    return getRowColumnVector(paramInt).getColumn();
  }

  public RowColumnVector getRowColumnVector(int paramInt)
  {
    if (paramInt > this.end)
      throw new IndexOutOfBoundsException();
    if (this.rowColumnVectorCacheArray == null)
      this.rowColumnVectorCacheArray = RowColumnVector.getCacheArray(this);
    return RowColumnVector.get(this.rowColumnVectorCacheArray, paramInt);
  }

  public String toString()
  {
    return this.sourceText.toString();
  }

  public Tag[] fullSequentialParse()
  {
    if (this.allTagsArray != null)
      return this.allTagsArray;
    if (this.cache.getTagCount() != 0)
    {
      this.logger.warn("Full sequential parse clearing all tags from cache. Consider calling Source.fullSequentialParse() manually immediately after construction of Source.");
      this.cache.clear();
    }
    boolean bool = this.useAllTypesCache;
    try
    {
      this.useAllTypesCache = false;
      this.useSpecialTypesCache = false;
      Tag[] arrayOfTag = Tag.parseAll(this, false);
      return arrayOfTag;
    }
    finally
    {
      this.useAllTypesCache = bool;
      this.useSpecialTypesCache = true;
    }
  }

  public Iterator<Segment> iterator()
  {
    return getNodeIterator();
  }

  public List<Element> getChildElements()
  {
    if (this.childElements == null)
      if (length() == 0)
      {
        this.childElements = Collections.emptyList();
      }
      else
      {
        if (this.allTags == null)
          fullSequentialParse();
        this.childElements = new ArrayList();
        int i = 0;
        while (true)
        {
          StartTag localStartTag = this.source.getNextStartTag(i);
          if (localStartTag == null)
            break;
          if (localStartTag.getTagType().isServerTag())
          {
            i = localStartTag.end;
          }
          else
          {
            Element localElement = localStartTag.getElement();
            localElement.getChildElements(0);
            if (localElement.parentElement == Element.NOT_CACHED)
            {
              localElement.parentElement = null;
              this.childElements.add(localElement);
            }
            i = localElement.end;
          }
        }
      }
    return this.childElements;
  }

  public SourceFormatter getSourceFormatter()
  {
    return new SourceFormatter(this);
  }

  public List<Tag> getAllTags()
  {
    if (this.allTags == null)
      fullSequentialParse();
    return this.allTags;
  }

  public List<StartTag> getAllStartTags()
  {
    if (this.allStartTags == null)
    {
      List localList = getAllTags();
      this.allStartTags = new ArrayList(localList.size());
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext())
      {
        Tag localTag = (Tag)localIterator.next();
        if ((localTag instanceof StartTag))
          this.allStartTags.add((StartTag)localTag);
      }
    }
    return this.allStartTags;
  }

  public List<Element> getAllElements()
  {
    if (this.allElements == null)
    {
      List localList = getAllStartTags();
      if (localList.isEmpty())
        return Collections.emptyList();
      this.allElements = new ArrayList(localList.size());
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext())
      {
        StartTag localStartTag = (StartTag)localIterator.next();
        this.allElements.add(localStartTag.getElement());
      }
    }
    return this.allElements;
  }

  public Element getElementById(String paramString)
  {
    return getFirstElement("id", paramString, true);
  }

  public final Tag getTagAt(int paramInt)
  {
    return Tag.getTagAt(this, paramInt, false);
  }

  public Tag getPreviousTag(int paramInt)
  {
    return Tag.getPreviousTag(this, paramInt);
  }

  public Tag getPreviousTag(int paramInt, TagType paramTagType)
  {
    return Tag.getPreviousTag(this, paramInt, paramTagType);
  }

  public Tag getNextTag(int paramInt)
  {
    return Tag.getNextTag(this, paramInt);
  }

  Tag getNextNonServerTag(int paramInt)
  {
    while (true)
    {
      Tag localTag = getNextTag(paramInt);
      if (localTag == null)
        return null;
      if (!localTag.getTagType().isServerTag())
        return localTag;
      paramInt = localTag.end;
    }
  }

  Tag getPreviousNonServerTag(int paramInt)
  {
    while (true)
    {
      Tag localTag = getPreviousTag(paramInt - 1);
      if (localTag == null)
        return null;
      if (!localTag.getTagType().isServerTag())
        return localTag;
      paramInt = localTag.begin - 1;
    }
  }

  public Tag getNextTag(int paramInt, TagType paramTagType)
  {
    return Tag.getNextTag(this, paramInt, paramTagType);
  }

  public Tag getEnclosingTag(int paramInt)
  {
    return getEnclosingTag(paramInt, null);
  }

  public Tag getEnclosingTag(int paramInt, TagType paramTagType)
  {
    Tag localTag = getPreviousTag(paramInt, paramTagType);
    if ((localTag == null) || (localTag.end <= paramInt))
      return null;
    return localTag;
  }

  public Element getNextElement(int paramInt)
  {
    StartTag localStartTag = getNextStartTag(paramInt);
    return localStartTag == null ? null : localStartTag.getElement();
  }

  public Element getNextElement(int paramInt, String paramString)
  {
    StartTag localStartTag = getNextStartTag(paramInt, paramString);
    return localStartTag == null ? null : localStartTag.getElement();
  }

  public Element getNextElement(int paramInt, String paramString1, String paramString2, boolean paramBoolean)
  {
    StartTag localStartTag = getNextStartTag(paramInt, paramString1, paramString2, paramBoolean);
    return localStartTag == null ? null : localStartTag.getElement();
  }

  public Element getNextElement(int paramInt, String paramString, Pattern paramPattern)
  {
    StartTag localStartTag = getNextStartTag(paramInt, paramString, paramPattern);
    return localStartTag == null ? null : localStartTag.getElement();
  }

  public Element getNextElementByClass(int paramInt, String paramString)
  {
    StartTag localStartTag = getNextStartTagByClass(paramInt, paramString);
    return localStartTag == null ? null : localStartTag.getElement();
  }

  public StartTag getPreviousStartTag(int paramInt)
  {
    return StartTag.getPrevious(this, paramInt);
  }

  public StartTag getPreviousStartTag(int paramInt, StartTagType paramStartTagType)
  {
    return (StartTag)getPreviousTag(paramInt, paramStartTagType);
  }

  public StartTag getPreviousStartTag(int paramInt, String paramString)
  {
    return getPreviousStartTag(paramInt, paramString, StartTagType.NORMAL);
  }

  public StartTag getPreviousStartTag(int paramInt, String paramString, StartTagType paramStartTagType)
  {
    if (paramString != null)
      paramString = paramString.toLowerCase();
    return StartTag.getPrevious(this, paramInt, paramString, paramStartTagType);
  }

  public StartTag getNextStartTag(int paramInt)
  {
    return StartTag.getNext(this, paramInt);
  }

  public StartTag getNextStartTag(int paramInt, StartTagType paramStartTagType)
  {
    return (StartTag)getNextTag(paramInt, paramStartTagType);
  }

  public StartTag getNextStartTag(int paramInt, String paramString)
  {
    return getNextStartTag(paramInt, paramString, StartTagType.NORMAL);
  }

  public StartTag getNextStartTag(int paramInt, String paramString, StartTagType paramStartTagType)
  {
    if (paramString != null)
      paramString = paramString.toLowerCase();
    return StartTag.getNext(this, paramInt, paramString, paramStartTagType);
  }

  public StartTag getNextStartTag(int paramInt, String paramString1, String paramString2, boolean paramBoolean)
  {
    return StartTag.getNext(this, paramInt, paramString1, paramString2, paramBoolean);
  }

  public StartTag getNextStartTag(int paramInt, String paramString, Pattern paramPattern)
  {
    return StartTag.getNext(this, paramInt, paramString, paramPattern);
  }

  public StartTag getNextStartTagByClass(int paramInt, String paramString)
  {
    return getNextStartTag(paramInt, "class", getClassPattern(paramString));
  }

  public EndTag getPreviousEndTag(int paramInt)
  {
    return EndTag.getPrevious(this, paramInt);
  }

  public EndTag getPreviousEndTag(int paramInt, EndTagType paramEndTagType)
  {
    return (EndTag)getPreviousTag(paramInt, paramEndTagType);
  }

  public EndTag getPreviousEndTag(int paramInt, String paramString)
  {
    if (paramString == null)
      throw new IllegalArgumentException("name argument must not be null");
    return EndTag.getPrevious(this, paramInt, paramString.toLowerCase(), EndTagType.NORMAL);
  }

  public EndTag getNextEndTag(int paramInt)
  {
    return EndTag.getNext(this, paramInt);
  }

  public EndTag getNextEndTag(int paramInt, EndTagType paramEndTagType)
  {
    return (EndTag)getNextTag(paramInt, paramEndTagType);
  }

  public EndTag getNextEndTag(int paramInt, String paramString)
  {
    return getNextEndTag(paramInt, paramString, EndTagType.NORMAL);
  }

  public EndTag getNextEndTag(int paramInt, String paramString, EndTagType paramEndTagType)
  {
    if (paramString == null)
      throw new IllegalArgumentException("name argument must not be null");
    return EndTag.getNext(this, paramInt, paramString.toLowerCase(), paramEndTagType);
  }

  public Element getEnclosingElement(int paramInt)
  {
    return getEnclosingElement(paramInt, null);
  }

  public Element getEnclosingElement(int paramInt, String paramString)
  {
    int i = paramInt;
    if (paramString != null)
      paramString = paramString.toLowerCase();
    boolean bool = Tag.isXMLName(paramString);
    while (true)
    {
      StartTag localStartTag = StartTag.getPrevious(this, i, paramString, StartTagType.NORMAL, bool);
      if (localStartTag == null)
        return null;
      Element localElement = localStartTag.getElement();
      if (paramInt < localElement.end)
        return localElement;
      i = localStartTag.begin - 1;
    }
  }

  public CharacterReference getPreviousCharacterReference(int paramInt)
  {
    return CharacterReference.getPrevious(this, paramInt);
  }

  public CharacterReference getNextCharacterReference(int paramInt)
  {
    return CharacterReference.getNext(this, paramInt);
  }

  public int getNameEnd(int paramInt)
  {
    if (!Tag.isXMLNameStartChar(this.sourceText.charAt(paramInt++)))
      return -1;
    try
    {
      while (Tag.isXMLNameChar(this.sourceText.charAt(paramInt)))
        paramInt++;
    }
    catch (IndexOutOfBoundsException localIndexOutOfBoundsException)
    {
    }
    return paramInt;
  }

  public Attributes parseAttributes(int paramInt1, int paramInt2)
  {
    return parseAttributes(paramInt1, paramInt2, Attributes.getDefaultMaxErrorCount());
  }

  public Attributes parseAttributes(int paramInt1, int paramInt2, int paramInt3)
  {
    return Attributes.construct(this, paramInt1, paramInt2, paramInt3);
  }

  public void ignoreWhenParsing(int paramInt1, int paramInt2)
  {
    if (wasFullSequentialParseCalled())
      throw new IllegalStateException("ignoreWhenParsing can not be used after a full sequential parse has been performed");
    if (this.parseTextOutputDocument == null)
    {
      this.parseTextOutputDocument = new OutputDocument(getParseText());
      this.parseText = null;
    }
    this.parseTextOutputDocument.replaceWithSpaces(paramInt1, paramInt2);
  }

  public void ignoreWhenParsing(Collection<? extends Segment> paramCollection)
  {
    Iterator localIterator = paramCollection.iterator();
    while (localIterator.hasNext())
    {
      Segment localSegment = (Segment)localIterator.next();
      localSegment.ignoreWhenParsing();
    }
  }

  public void setLogger(Logger paramLogger)
  {
    this.logger = (paramLogger != null ? paramLogger : LoggerDisabled.INSTANCE);
  }

  public Logger getLogger()
  {
    return this.logger != LoggerDisabled.INSTANCE ? this.logger : null;
  }

  public void clearCache()
  {
    this.cache.clear();
    this.allTagsArray = null;
    this.allTags = null;
    this.allStartTags = null;
    this.allElements = null;
  }

  public String getCacheDebugInfo()
  {
    return this.cache.toString();
  }

  List<Tag> getParsedTags()
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = this.cache.getTagIterator();
    while (localIterator.hasNext())
      localArrayList.add(localIterator.next());
    return localArrayList;
  }

  public final ParseText getParseText()
  {
    if (this.parseText == null)
      if (this.parseTextOutputDocument != null)
      {
        this.parseText = new CharSequenceParseText(this.parseTextOutputDocument.toString());
        this.parseTextOutputDocument = null;
      }
      else
      {
        this.parseText = new CharSequenceParseText(this.sourceText);
      }
    return this.parseText;
  }

  public final CharSequence subSequence(int paramInt1, int paramInt2)
  {
    return this.sourceText.subSequence(paramInt1, paramInt2);
  }

  final String substring(int paramInt1, int paramInt2)
  {
    return subSequence(paramInt1, paramInt2).toString();
  }

  final String getName(int paramInt1, int paramInt2)
  {
    return substring(paramInt1, paramInt2).toLowerCase();
  }

  public final char charAt(int paramInt)
  {
    return this.sourceText.charAt(paramInt);
  }

  public final int length()
  {
    return this.sourceText.length();
  }

  boolean wasFullSequentialParseCalled()
  {
    return this.allTagsArray != null;
  }

  static String getCharsetParameterFromHttpHeaderValue(String paramString)
  {
    int i = paramString.toLowerCase().indexOf("charset=");
    if (i == -1)
      return null;
    int j = i + 8;
    int k = paramString.indexOf(';', j);
    String str = k == -1 ? paramString.substring(j) : paramString.substring(j, k);
    return str.trim();
  }

  static Logger newLogger()
  {
    return LoggerFactory.getLogger(PACKAGE_NAME);
  }

  private static String getString(EncodingDetector paramEncodingDetector)
    throws IOException
  {
    try
    {
      return Util.getString(paramEncodingDetector.openReader());
    }
    catch (IOException localIOException)
    {
      try
      {
        Logger localLogger = newLogger();
        if (localLogger.isInfoEnabled())
          localLogger.info("IOException constructing encoded source. Encoding: " + paramEncodingDetector.getEncoding() + " - " + paramEncodingDetector.getEncodingSpecificationInfo() + ". PreliminaryEncoding: " + paramEncodingDetector.getPreliminaryEncoding() + " - " + paramEncodingDetector.getPreliminaryEncodingSpecificationInfo());
      }
      catch (Exception localException)
      {
      }
      throw localIOException;
    }
  }

  final boolean isStreamed()
  {
    return this.cache == Cache.STREAMED_SOURCE_MARKER;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.Source
 * JD-Core Version:    0.6.2
 */