package net.htmlparser.jericho;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Element extends Segment
{
  private final StartTag startTag;
  private final EndTag endTag;
  private Segment content = null;
  Element parentElement = NOT_CACHED;
  private int depth = -1;
  private List<Element> childElements = null;
  static final Element NOT_CACHED = new Element();
  private static final boolean INCLUDE_INCORRECTLY_NESTED_CHILDREN_IN_HIERARCHY = true;

  Element(Source paramSource, StartTag paramStartTag, EndTag paramEndTag)
  {
    super(paramSource, paramStartTag.begin, paramEndTag == null ? paramStartTag.end : paramEndTag.end);
    if (paramSource.isStreamed())
      throw new UnsupportedOperationException("Elements are not supported when using StreamedSource");
    this.startTag = paramStartTag;
    this.endTag = ((paramEndTag == null) || (paramEndTag.length() == 0) ? null : paramEndTag);
  }

  private Element()
  {
    this.startTag = null;
    this.endTag = null;
  }

  public Element getParentElement()
  {
    if (this.parentElement == NOT_CACHED)
    {
      if (!this.source.wasFullSequentialParseCalled())
        throw new IllegalStateException("This operation is only possible after a full sequential parse has been performed");
      if (this.startTag.isOrphaned())
        throw new IllegalStateException("This operation is only possible if a full sequential parse was performed immediately after construction of the Source object");
      this.source.getChildElements();
      if (this.parentElement == NOT_CACHED)
        this.parentElement = null;
    }
    return this.parentElement;
  }

  public final List<Element> getChildElements()
  {
    return this.childElements != null ? this.childElements : getChildElements(-1);
  }

  final List<Element> getChildElements(int paramInt)
  {
    if (paramInt != -1)
      this.depth = paramInt;
    if (this.childElements == null)
      if (this.end == this.startTag.end)
      {
        this.childElements = Collections.emptyList();
      }
      else
      {
        int i = paramInt == -1 ? -1 : paramInt + 1;
        this.childElements = new ArrayList();
        int j = this.startTag.end;
        int k = this.endTag == null ? this.end : this.endTag.begin;
        while (true)
        {
          StartTag localStartTag = this.source.getNextStartTag(j);
          if ((localStartTag == null) || (localStartTag.begin >= k))
            break;
          if (localStartTag.getTagType().isServerTag())
          {
            j = localStartTag.end;
          }
          else
          {
            Element localElement = localStartTag.getElement();
            if ((localElement.end > this.end) && (this.source.logger.isInfoEnabled()))
              this.source.logger.info("Child " + localElement.getDebugInfo() + " extends beyond end of parent " + getDebugInfo());
            localElement.getChildElements(i);
            if (localElement.parentElement == NOT_CACHED)
            {
              localElement.parentElement = this;
              this.childElements.add(localElement);
            }
            j = localElement.end;
          }
        }
      }
    return this.childElements;
  }

  public int getDepth()
  {
    if (this.depth == -1)
    {
      getParentElement();
      if (this.depth == -1)
        this.depth = 0;
    }
    return this.depth;
  }

  public Segment getContent()
  {
    if (this.content == null)
      this.content = new Segment(this.source, this.startTag.end, getContentEnd());
    return this.content;
  }

  public StartTag getStartTag()
  {
    return this.startTag;
  }

  public EndTag getEndTag()
  {
    return this.endTag;
  }

  public String getName()
  {
    return this.startTag.getName();
  }

  public boolean isEmpty()
  {
    return this.startTag.end == getContentEnd();
  }

  public boolean isEmptyElementTag()
  {
    return this.startTag.isEmptyElementTag();
  }

  public Attributes getAttributes()
  {
    return getStartTag().getAttributes();
  }

  public String getAttributeValue(String paramString)
  {
    return getStartTag().getAttributeValue(paramString);
  }

  public FormControl getFormControl()
  {
    return FormControl.construct(this);
  }

  public String getDebugInfo()
  {
    if (this == NOT_CACHED)
      return "NOT_CACHED";
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Element ");
    this.startTag.appendDebugTag(localStringBuilder);
    if (!isEmpty())
      localStringBuilder.append('-');
    if (this.endTag != null)
      localStringBuilder.append(this.endTag);
    localStringBuilder.append(' ');
    this.startTag.appendDebugTagType(localStringBuilder);
    localStringBuilder.append(super.getDebugInfo());
    return localStringBuilder.toString();
  }

  int getContentEnd()
  {
    return this.endTag != null ? this.endTag.begin : this.end;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.Element
 * JD-Core Version:    0.6.2
 */