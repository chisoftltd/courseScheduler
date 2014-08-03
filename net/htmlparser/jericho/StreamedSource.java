package net.htmlparser.jericho;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.BufferOverflowException;
import java.nio.CharBuffer;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class StreamedSource
  implements Iterable<Segment>, Closeable
{
  private final StreamedText streamedText;
  private final StreamedParseText streamedParseText;
  private final Source source;
  private final Closeable closeable;
  private final boolean automaticClose;
  private boolean coalescing = false;
  private boolean handleTags = true;
  private boolean isInitialised = false;
  private Segment currentSegment = null;
  private Segment nextParsedSegment = START_SEGMENT;
  private boolean isXML;
  private static final boolean assumeNoNestedTags = false;
  private static final Segment START_SEGMENT = new Segment(-1, -1);

  private StreamedSource(Reader paramReader, boolean paramBoolean, String paramString1, String paramString2, String paramString3)
    throws IOException
  {
    this.closeable = paramReader;
    this.automaticClose = paramBoolean;
    this.streamedText = new StreamedText(paramReader);
    this.streamedParseText = new StreamedParseText(this.streamedText);
    this.source = new Source(this.streamedText, this.streamedParseText, paramString1, paramString2, paramString3);
  }

  private StreamedSource(EncodingDetector paramEncodingDetector, boolean paramBoolean)
    throws IOException
  {
    this(paramEncodingDetector.openReader(), paramBoolean, paramEncodingDetector.getEncoding(), paramEncodingDetector.getEncodingSpecificationInfo(), paramEncodingDetector.getPreliminaryEncoding() + ": " + paramEncodingDetector.getPreliminaryEncodingSpecificationInfo());
  }

  public StreamedSource(Reader paramReader)
    throws IOException
  {
    this(paramReader, false, (paramReader instanceof InputStreamReader) ? ((InputStreamReader)paramReader).getEncoding() : null, (paramReader instanceof InputStreamReader) ? "InputStreamReader.getEncoding() of constructor argument" : null, null);
  }

  public StreamedSource(InputStream paramInputStream)
    throws IOException
  {
    this(new EncodingDetector(paramInputStream), false);
  }

  public StreamedSource(URL paramURL)
    throws IOException
  {
    this(new EncodingDetector(paramURL.openConnection()), true);
  }

  public StreamedSource(URLConnection paramURLConnection)
    throws IOException
  {
    this(new EncodingDetector(paramURLConnection), true);
  }

  public StreamedSource(CharSequence paramCharSequence)
  {
    this.closeable = null;
    this.automaticClose = false;
    this.streamedText = new StreamedText(paramCharSequence);
    this.streamedParseText = new StreamedParseText(this.streamedText);
    this.source = new Source(paramCharSequence, this.streamedParseText, null, "Document specified encoding can not be determined automatically from a streamed source", null);
  }

  public StreamedSource setBuffer(char[] paramArrayOfChar)
  {
    if (this.isInitialised)
      throw new IllegalStateException("setBuffer() can only be called before iterator() is called");
    this.streamedText.setBuffer(paramArrayOfChar);
    return this;
  }

  public StreamedSource setCoalescing(boolean paramBoolean)
  {
    if (this.isInitialised)
      throw new IllegalStateException("setPlainTextWriter() can only be called before iterator() is called");
    this.coalescing = paramBoolean;
    return this;
  }

  public void close()
    throws IOException
  {
    if (this.closeable != null)
      this.closeable.close();
  }

  public String getEncoding()
  {
    return this.source.getEncoding();
  }

  public String getEncodingSpecificationInfo()
  {
    return this.source.getEncodingSpecificationInfo();
  }

  public String getPreliminaryEncodingInfo()
  {
    return this.source.getPreliminaryEncodingInfo();
  }

  public Iterator<Segment> iterator()
  {
    if (this.isInitialised)
      throw new IllegalStateException("iterator() can only be called once");
    this.isInitialised = true;
    return new StreamedSourceIterator();
  }

  public Segment getCurrentSegment()
  {
    return this.currentSegment;
  }

  public CharBuffer getCurrentSegmentCharBuffer()
  {
    return this.streamedText.getCharBuffer(this.currentSegment.getBegin(), this.currentSegment.end);
  }

  public boolean isXML()
  {
    if (!this.isInitialised)
      throw new IllegalStateException("isXML() method only available after iterator() has been called");
    return this.isXML;
  }

  public void setLogger(Logger paramLogger)
  {
    this.source.setLogger(paramLogger);
  }

  public Logger getLogger()
  {
    return this.source.getLogger();
  }

  public int getBufferSize()
  {
    return this.streamedText.getBuffer().length;
  }

  public String toString()
  {
    return super.toString();
  }

  protected void finalize()
  {
    automaticClose();
  }

  StreamedSource setHandleTags(boolean paramBoolean)
  {
    this.handleTags = paramBoolean;
    return this;
  }

  StreamedSource setSearchBegin(int paramInt)
  {
    if (this.isInitialised)
      throw new IllegalStateException("setSearchBegin() can only be called before iterator() is called");
    int i = paramInt - 1;
    this.nextParsedSegment = new Segment(i, i);
    return this;
  }

  private void automaticClose()
  {
    if (this.automaticClose)
      try
      {
        close();
      }
      catch (IOException localIOException)
      {
      }
  }

  private static boolean isXML(Segment paramSegment)
  {
    if ((paramSegment == null) || (!(paramSegment instanceof Tag)))
      return false;
    Tag localTag = (Tag)paramSegment;
    if (localTag.getTagType() == StartTagType.XML_DECLARATION)
      return true;
    return localTag.source.getParseText().indexOf("xhtml", localTag.begin, localTag.end) != -1;
  }

  private class StreamedSourceIterator
    implements Iterator<Segment>
  {
    private final boolean coalescing = StreamedSource.this.coalescing;
    private final boolean handleTags = StreamedSource.this.handleTags;
    private Segment nextSegment;
    private int plainTextSegmentBegin = 0;
    private final char[] charByRef = new char[1];

    public StreamedSourceIterator()
    {
      loadNextParsedSegment();
      StreamedSource.this.isXML = StreamedSource.isXML(StreamedSource.this.nextParsedSegment);
    }

    public boolean hasNext()
    {
      if (this.nextSegment == Tag.NOT_CACHED)
        loadNextParsedSegment();
      return this.nextSegment != null;
    }

    public Segment next()
    {
      if (!hasNext())
        throw new NoSuchElementException();
      Segment localSegment = this.nextSegment;
      this.nextSegment = (localSegment == StreamedSource.this.nextParsedSegment ? Tag.NOT_CACHED : StreamedSource.this.nextParsedSegment);
      StreamedSource.this.streamedText.setMinRequiredBufferBegin(localSegment.end);
      StreamedSource.this.currentSegment = localSegment;
      return localSegment;
    }

    public void remove()
    {
      throw new UnsupportedOperationException();
    }

    private final void loadNextParsedSegment()
    {
      StreamedSource.this.nextParsedSegment = findNextParsedSegment();
      int i = StreamedSource.this.nextParsedSegment != null ? StreamedSource.this.nextParsedSegment.begin : StreamedSource.this.streamedText.length();
      this.nextSegment = (this.plainTextSegmentBegin < i ? new Segment(StreamedSource.this.source, this.plainTextSegmentBegin, i) : StreamedSource.this.nextParsedSegment);
      if ((StreamedSource.this.nextParsedSegment != null) && (this.plainTextSegmentBegin < StreamedSource.this.nextParsedSegment.end))
        this.plainTextSegmentBegin = StreamedSource.this.nextParsedSegment.end;
    }

    private final Segment findNextParsedSegment()
    {
      try
      {
        int i = StreamedSource.this.nextParsedSegment.getBegin() + 1;
        int j = this.coalescing ? StreamedSource.this.streamedText.getEnd() : StreamedSource.this.streamedText.getBufferOverflowPosition();
        while (i < j)
        {
          int k = StreamedSource.this.streamedText.charAt(i);
          Object localObject;
          if (k == 38)
          {
            if (i >= StreamedSource.this.source.fullSequentialParseData[0])
            {
              localObject = CharacterReference.construct(StreamedSource.this.source, i, Config.UnterminatedCharacterReferenceSettings.ACCEPT_ALL);
              if (localObject != null)
                return localObject;
            }
          }
          else if ((this.handleTags) && (k == 60))
          {
            localObject = TagType.getTagAt(StreamedSource.this.source, i, false, false);
            if ((localObject != null) && (!((Tag)localObject).isUnregistered()))
            {
              TagType localTagType = ((Tag)localObject).getTagType();
              if ((((Tag)localObject).end > StreamedSource.this.source.fullSequentialParseData[0]) && (localTagType != StartTagType.DOCTYPE_DECLARATION))
                StreamedSource.this.source.fullSequentialParseData[0] = ((localTagType == StartTagType.NORMAL) && (((Tag)localObject).name == "script") ? 2147483647 : ((Tag)localObject).end);
              return localObject;
            }
          }
          i++;
        }
        if (i < StreamedSource.this.streamedText.getEnd())
          return new Segment(StreamedSource.this.source, this.plainTextSegmentBegin, i);
      }
      catch (BufferOverflowException localBufferOverflowException)
      {
        StreamedSource.this.automaticClose();
        throw localBufferOverflowException;
      }
      catch (IndexOutOfBoundsException localIndexOutOfBoundsException)
      {
      }
      StreamedSource.this.automaticClose();
      return null;
    }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.StreamedSource
 * JD-Core Version:    0.6.2
 */