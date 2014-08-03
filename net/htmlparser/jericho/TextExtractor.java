package net.htmlparser.jericho;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TextExtractor
  implements CharStreamSource
{
  private final Segment segment;
  private boolean convertNonBreakingSpaces = Config.ConvertNonBreakingSpaces;
  private boolean includeAttributes = false;
  private boolean excludeNonHTMLElements = false;
  private static Map<String, AttributeIncludeChecker> map = initDefaultAttributeIncludeCheckerMap();
  private static AttributeIncludeChecker ALWAYS_INCLUDE = new AttributeIncludeChecker()
  {
    public boolean includeAttribute(StartTag paramAnonymousStartTag, Attribute paramAnonymousAttribute)
    {
      return true;
    }
  };
  private static AttributeIncludeChecker INCLUDE_IF_NAME_ATTRIBUTE_PRESENT = new AttributeIncludeChecker()
  {
    public boolean includeAttribute(StartTag paramAnonymousStartTag, Attribute paramAnonymousAttribute)
    {
      return paramAnonymousStartTag.getAttributes().get("name") != null;
    }
  };

  public TextExtractor(Segment paramSegment)
  {
    this.segment = paramSegment;
  }

  public void writeTo(Writer paramWriter)
    throws IOException
  {
    appendTo(paramWriter);
    paramWriter.flush();
  }

  public void appendTo(Appendable paramAppendable)
    throws IOException
  {
    paramAppendable.append(toString());
  }

  public long getEstimatedMaximumOutputLength()
  {
    return this.segment.length();
  }

  public String toString()
  {
    return new Processor(this.segment, getConvertNonBreakingSpaces(), getIncludeAttributes(), getExcludeNonHTMLElements()).toString();
  }

  public TextExtractor setConvertNonBreakingSpaces(boolean paramBoolean)
  {
    this.convertNonBreakingSpaces = paramBoolean;
    return this;
  }

  public boolean getConvertNonBreakingSpaces()
  {
    return this.convertNonBreakingSpaces;
  }

  public TextExtractor setIncludeAttributes(boolean paramBoolean)
  {
    this.includeAttributes = paramBoolean;
    return this;
  }

  public boolean getIncludeAttributes()
  {
    return this.includeAttributes;
  }

  public boolean includeAttribute(StartTag paramStartTag, Attribute paramAttribute)
  {
    AttributeIncludeChecker localAttributeIncludeChecker = (AttributeIncludeChecker)map.get(paramAttribute.getKey());
    if (localAttributeIncludeChecker == null)
      return false;
    return localAttributeIncludeChecker.includeAttribute(paramStartTag, paramAttribute);
  }

  public TextExtractor setExcludeNonHTMLElements(boolean paramBoolean)
  {
    this.excludeNonHTMLElements = paramBoolean;
    return this;
  }

  public boolean getExcludeNonHTMLElements()
  {
    return this.excludeNonHTMLElements;
  }

  public boolean excludeElement(StartTag paramStartTag)
  {
    return false;
  }

  private static Map<String, AttributeIncludeChecker> initDefaultAttributeIncludeCheckerMap()
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("title", ALWAYS_INCLUDE);
    localHashMap.put("alt", ALWAYS_INCLUDE);
    localHashMap.put("label", ALWAYS_INCLUDE);
    localHashMap.put("summary", ALWAYS_INCLUDE);
    localHashMap.put("content", INCLUDE_IF_NAME_ATTRIBUTE_PRESENT);
    localHashMap.put("href", ALWAYS_INCLUDE);
    return localHashMap;
  }

  private final class Processor
  {
    private final Segment segment;
    private final Source source;
    private final boolean convertNonBreakingSpaces;
    private final boolean includeAttributes;
    private final boolean excludeNonHTMLElements;

    public Processor(Segment paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean arg5)
    {
      this.segment = paramBoolean1;
      this.source = paramBoolean1.source;
      this.convertNonBreakingSpaces = paramBoolean2;
      this.includeAttributes = paramBoolean3;
      boolean bool;
      this.excludeNonHTMLElements = bool;
    }

    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder(this.segment.length());
      Object localObject1 = new NodeIterator(this.segment);
      while (((NodeIterator)localObject1).hasNext())
      {
        Segment localSegment = ((NodeIterator)localObject1).next();
        if ((localSegment instanceof Tag))
        {
          Tag localTag = (Tag)localSegment;
          Object localObject2;
          if (localTag.getTagType().isServerTag())
          {
            localObject2 = localTag.getElement();
            if ((localObject2 != null) && (((Element)localObject2).getEnd() > localTag.getEnd()))
              ((NodeIterator)localObject1).skipToPos(((Element)localObject2).getEnd());
          }
          else if (localTag.getTagType() == StartTagType.NORMAL)
          {
            StartTag localStartTag = (StartTag)localTag;
            if ((localTag.name == "script") || (localTag.name == "style") || (TextExtractor.this.excludeElement(localStartTag)) || ((this.excludeNonHTMLElements) && (!HTMLElements.getElementNames().contains(localTag.name))))
            {
              ((NodeIterator)localObject1).skipToPos(localStartTag.getElement().getEnd());
            }
            else if (this.includeAttributes)
            {
              localObject2 = localStartTag.getAttributes().iterator();
              while (((Iterator)localObject2).hasNext())
              {
                Attribute localAttribute = (Attribute)((Iterator)localObject2).next();
                if (TextExtractor.this.includeAttribute(localStartTag, localAttribute))
                  localStringBuilder.append(' ').append(localAttribute.getValueSegment()).append(' ');
              }
            }
          }
          else if ((localTag.getName() == "br") || (!HTMLElements.getInlineLevelElementNames().contains(localTag.getName())))
          {
            localStringBuilder.append(' ');
          }
        }
        else
        {
          localStringBuilder.append(localSegment);
        }
      }
      localObject1 = CharacterReference.decodeCollapseWhiteSpace(localStringBuilder, this.convertNonBreakingSpaces);
      return localObject1;
    }
  }

  private static abstract interface AttributeIncludeChecker
  {
    public abstract boolean includeAttribute(StartTag paramStartTag, Attribute paramAttribute);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.TextExtractor
 * JD-Core Version:    0.6.2
 */