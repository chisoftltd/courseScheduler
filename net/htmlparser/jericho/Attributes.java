package net.htmlparser.jericho;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import net.htmlparser.jericho.nodoc.SequentialListSegment;

public final class Attributes extends SequentialListSegment<Attribute>
{
  private final LinkedList<Attribute> attributeList;
  final boolean containsServerTagOutsideOfAttributeValue;
  private static int defaultMaxErrorCount = 2;

  private Attributes(Source paramSource, int paramInt1, int paramInt2, LinkedList<Attribute> paramLinkedList, boolean paramBoolean)
  {
    super(paramSource, paramInt1, paramInt2);
    this.attributeList = paramLinkedList;
    this.containsServerTagOutsideOfAttributeValue = paramBoolean;
  }

  static Attributes construct(Source paramSource, int paramInt, StartTagType paramStartTagType, String paramString)
  {
    return construct(paramSource, "StartTag", ParsingState.AFTER_TAG_NAME, paramInt, -1, -1, paramStartTagType, paramString, defaultMaxErrorCount);
  }

  static Attributes construct(Source paramSource, int paramInt1, int paramInt2, int paramInt3, StartTagType paramStartTagType, String paramString, int paramInt4)
  {
    return construct(paramSource, "Attributes for StartTag", ParsingState.BETWEEN_ATTRIBUTES, paramInt1, paramInt2, paramInt3, paramStartTagType, paramString, paramInt4);
  }

  static Attributes construct(Source paramSource, int paramInt1, int paramInt2, int paramInt3)
  {
    return construct(paramSource, "Attributes", ParsingState.BETWEEN_ATTRIBUTES, paramInt1, -1, paramInt2, StartTagType.NORMAL, null, paramInt3);
  }

  private static Attributes construct(Source paramSource, String paramString1, ParsingState paramParsingState, int paramInt1, int paramInt2, int paramInt3, StartTagType paramStartTagType, String paramString2, int paramInt4)
  {
    boolean bool1 = false;
    if (paramString2 != null)
    {
      if (paramInt2 == -1)
        paramInt2 = paramInt1 + 1 + paramString2.length();
      if ((paramStartTagType == StartTagType.NORMAL) && (HTMLElements.isClosingSlashIgnored(paramString2)))
        bool1 = true;
    }
    else
    {
      paramInt2 = paramInt1;
    }
    int i = paramInt2;
    LinkedList localLinkedList = new LinkedList();
    boolean bool2 = false;
    ParseText localParseText = paramSource.getParseText();
    int j = paramInt2;
    int k = 32;
    Segment localSegment1 = null;
    String str = null;
    int m = -1;
    int n = 0;
    int i1 = 0;
    try
    {
      while (n == 0)
      {
        if ((j == paramInt3) || (paramStartTagType.atEndOfAttributes(paramSource, j, bool1)))
          n = 1;
        char c = localParseText.charAt(j);
        Object localObject;
        if (c == '<')
        {
          localObject = Tag.getTagAt(paramSource, j, true);
          if (localObject != null)
          {
            if (paramParsingState == ParsingState.START_VALUE)
            {
              m = j;
              k = 32;
              paramParsingState = ParsingState.IN_VALUE;
            }
            j = i = ((Tag)localObject).end;
            if (paramParsingState == ParsingState.IN_VALUE)
              continue;
            bool2 = true;
          }
        }
        else
        {
          switch (1.$SwitchMap$net$htmlparser$jericho$Attributes$ParsingState[paramParsingState.ordinal()])
          {
          case 1:
            if ((n != 0) || (c == k) || ((k == 32) && (isWhiteSpace(c))))
            {
              Segment localSegment2;
              if (k == 32)
              {
                localObject = localSegment2 = new Segment(paramSource, m, j);
              }
              else if (n != 0)
              {
                if (j == paramInt3)
                {
                  if (paramSource.logger.isInfoEnabled())
                    log(paramSource, paramString1, paramString2, paramInt1, "terminated in the middle of a quoted attribute value", j);
                  i1++;
                  if (reachedMaxErrorCount(i1, paramSource, paramString1, paramString2, paramInt1, paramInt4))
                    return null;
                  localObject = new Segment(paramSource, m, j);
                  localSegment2 = new Segment(paramSource, m - 1, j);
                }
                else
                {
                  n = 0;
                  break;
                }
              }
              else
              {
                localObject = new Segment(paramSource, m, j);
                localSegment2 = new Segment(paramSource, m - 1, j + 1);
              }
              localLinkedList.add(new Attribute(paramSource, str, localSegment1, (Segment)localObject, localSegment2));
              i = localSegment2.getEnd();
              paramParsingState = ParsingState.BETWEEN_ATTRIBUTES;
            }
            else if ((c == '<') && (k == 32))
            {
              if (paramSource.logger.isInfoEnabled())
                log(paramSource, paramString1, paramString2, paramInt1, "rejected because of '<' character in unquoted attribute value", j);
              return null;
            }
            break;
          case 2:
            if ((n != 0) || (c == '=') || (isWhiteSpace(c)))
            {
              localSegment1 = new Segment(paramSource, m, j);
              str = localSegment1.toString().toLowerCase();
              if (n != 0)
              {
                localLinkedList.add(new Attribute(paramSource, str, localSegment1));
                i = j;
              }
              else
              {
                paramParsingState = c == '=' ? ParsingState.START_VALUE : ParsingState.AFTER_NAME;
              }
            }
            else if (!Tag.isXMLNameChar(c))
            {
              if (c == '<')
              {
                if (paramSource.logger.isInfoEnabled())
                  log(paramSource, paramString1, paramString2, paramInt1, "rejected because of '<' character in attribute name", j);
                return null;
              }
              if (!isInvalidEmptyElementTag(paramStartTagType, paramSource, j, paramString1, paramString2, paramInt1))
              {
                if (paramSource.logger.isInfoEnabled())
                  log(paramSource, paramString1, paramString2, paramInt1, "contains attribute name with invalid character", j);
                i1++;
                if (reachedMaxErrorCount(i1, paramSource, paramString1, paramString2, paramInt1, paramInt4))
                  return null;
              }
            }
            break;
          case 3:
            if ((n != 0) || ((c != '=') && (!isWhiteSpace(c))))
            {
              localLinkedList.add(new Attribute(paramSource, str, localSegment1));
              i = localSegment1.getEnd();
              if (n == 0)
              {
                paramParsingState = ParsingState.BETWEEN_ATTRIBUTES;
                j--;
              }
            }
            else if (c == '=')
            {
              paramParsingState = ParsingState.START_VALUE;
            }
            else if (c == '<')
            {
              if (paramSource.logger.isInfoEnabled())
                log(paramSource, paramString1, paramString2, paramInt1, "rejected because of '<' character after attribute name", j);
              return null;
            }
            break;
          case 4:
            if (n == 0)
              if (isWhiteSpace(c))
              {
                k = 32;
              }
              else
              {
                if (k != 32)
                {
                  if (paramSource.logger.isInfoEnabled())
                    log(paramSource, paramString1, paramString2, paramInt1, "has missing whitespace after quoted attribute value", j);
                  if (i1 > 0)
                  {
                    i1++;
                    if (reachedMaxErrorCount(i1, paramSource, paramString1, paramString2, paramInt1, paramInt4))
                      return null;
                  }
                }
                if (!Tag.isXMLNameStartChar(c))
                {
                  if (c == '<')
                  {
                    if (paramSource.logger.isInfoEnabled())
                      log(paramSource, paramString1, paramString2, paramInt1, "rejected because of '<' character", j);
                    return null;
                  }
                  if (isInvalidEmptyElementTag(paramStartTagType, paramSource, j, paramString1, paramString2, paramInt1))
                    break;
                  if ((paramStartTagType == StartTagType.NORMAL) && (paramStartTagType.atEndOfAttributes(paramSource, j, false)))
                  {
                    if (!paramSource.logger.isInfoEnabled())
                      break;
                    log(paramSource, paramString1, paramString2, paramInt1, "contains a '/' character before the closing '>', which is ignored because tags of this name cannot be empty-element tags");
                    break;
                  }
                  if (paramSource.logger.isInfoEnabled())
                    log(paramSource, paramString1, paramString2, paramInt1, "contains attribute name with invalid first character", j);
                  i1++;
                  if (reachedMaxErrorCount(i1, paramSource, paramString1, paramString2, paramInt1, paramInt4))
                    return null;
                }
                paramParsingState = ParsingState.IN_NAME;
                m = j;
              }
            break;
          case 5:
            m = j;
            if (n != 0)
            {
              if (paramSource.logger.isInfoEnabled())
                log(paramSource, paramString1, paramString2, paramInt1, "has missing attribute value after '=' sign", j);
              if (i1 > 0)
              {
                i1++;
                if (reachedMaxErrorCount(i1, paramSource, paramString1, paramString2, paramInt1, paramInt4))
                  return null;
              }
              localObject = new Segment(paramSource, j, j);
              localLinkedList.add(new Attribute(paramSource, str, localSegment1, (Segment)localObject, (Segment)localObject));
              i = j;
              paramParsingState = ParsingState.BETWEEN_ATTRIBUTES;
            }
            else
            {
              if ((c == '\'') || (c == '"'))
              {
                k = c;
                m++;
              }
              else
              {
                if (isWhiteSpace(c))
                  break;
                if (c == '<')
                {
                  if (paramSource.logger.isInfoEnabled())
                    log(paramSource, paramString1, paramString2, paramInt1, "rejected because of '<' character at the start of an attribute value", j);
                  return null;
                }
                k = 32;
              }
              paramParsingState = ParsingState.IN_VALUE;
            }
            break;
          case 6:
            if (n == 0)
              if (!isWhiteSpace(c))
              {
                if (!isInvalidEmptyElementTag(paramStartTagType, paramSource, j, paramString1, paramString2, paramInt1))
                {
                  if (paramSource.logger.isInfoEnabled())
                    log(paramSource, paramString1, paramString2, paramInt1, "rejected because the name contains an invalid character", j);
                  return null;
                }
              }
              else
                paramParsingState = ParsingState.BETWEEN_ATTRIBUTES;
            break;
          }
          j++;
        }
      }
      return new Attributes(paramSource, paramInt2, i, localLinkedList, bool2);
    }
    catch (IndexOutOfBoundsException localIndexOutOfBoundsException)
    {
      if (paramSource.logger.isInfoEnabled())
        log(paramSource, paramString1, paramString2, paramInt1, "rejected because it has no closing '>' character");
    }
    return null;
  }

  private static boolean reachedMaxErrorCount(int paramInt1, Source paramSource, String paramString1, String paramString2, int paramInt2, int paramInt3)
  {
    if (paramInt1 <= paramInt3)
      return false;
    if (paramSource.logger.isInfoEnabled())
      log(paramSource, paramString1, paramString2, paramInt2, "rejected because it contains too many errors");
    return true;
  }

  private static boolean isInvalidEmptyElementTag(StartTagType paramStartTagType, Source paramSource, int paramInt1, String paramString1, String paramString2, int paramInt2)
  {
    if ((paramStartTagType != StartTagType.NORMAL) || (!paramStartTagType.atEndOfAttributes(paramSource, paramInt1, false)))
      return false;
    if (paramSource.logger.isInfoEnabled())
      log(paramSource, paramString1, paramString2, paramInt2, "contains a '/' character before the closing '>', which is ignored because tags of this name cannot be empty-element tags");
    return true;
  }

  public Attribute get(String paramString)
  {
    if (size() == 0)
      return null;
    for (int i = 0; i < size(); i++)
    {
      Attribute localAttribute = (Attribute)get(i);
      if (localAttribute.getKey().equalsIgnoreCase(paramString))
        return localAttribute;
    }
    return null;
  }

  public String getValue(String paramString)
  {
    Attribute localAttribute = get(paramString);
    return localAttribute == null ? null : localAttribute.getValue();
  }

  String getRawValue(String paramString)
  {
    Attribute localAttribute = get(paramString);
    return (localAttribute == null) || (!localAttribute.hasValue()) ? null : localAttribute.getValueSegment().toString();
  }

  public int getCount()
  {
    return this.attributeList.size();
  }

  public Iterator<Attribute> iterator()
  {
    return listIterator();
  }

  public ListIterator<Attribute> listIterator(int paramInt)
  {
    return this.attributeList.listIterator(paramInt);
  }

  public Map<String, String> populateMap(Map<String, String> paramMap, boolean paramBoolean)
  {
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Attribute localAttribute = (Attribute)localIterator.next();
      paramMap.put(paramBoolean ? localAttribute.getKey() : localAttribute.getName(), localAttribute.getValue());
    }
    return paramMap;
  }

  public String getDebugInfo()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Attributes ").append(super.getDebugInfo()).append(": ");
    if (isEmpty())
    {
      localStringBuilder.append("EMPTY");
    }
    else
    {
      localStringBuilder.append(Config.NewLine);
      Iterator localIterator = iterator();
      while (localIterator.hasNext())
      {
        Attribute localAttribute = (Attribute)localIterator.next();
        localStringBuilder.append("  ").append(localAttribute.getDebugInfo());
      }
    }
    return localStringBuilder.toString();
  }

  public static int getDefaultMaxErrorCount()
  {
    return defaultMaxErrorCount;
  }

  public static void setDefaultMaxErrorCount(int paramInt)
  {
    defaultMaxErrorCount = paramInt;
  }

  public static String generateHTML(Map<String, String> paramMap)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    try
    {
      appendHTML(localStringBuilder, paramMap);
    }
    catch (IOException localIOException)
    {
    }
    return localStringBuilder.toString();
  }

  static void appendHTML(Appendable paramAppendable, Map<String, String> paramMap)
    throws IOException
  {
    Iterator localIterator = paramMap.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      Attribute.appendHTML(paramAppendable, (CharSequence)localEntry.getKey(), (CharSequence)localEntry.getValue());
    }
  }

  Appendable appendTidy(Appendable paramAppendable, Tag paramTag)
    throws IOException
  {
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Attribute localAttribute = (Attribute)localIterator.next();
      paramTag = localAttribute.appendTidy(paramAppendable, paramTag);
    }
    return paramAppendable;
  }

  Map<String, String> getMap(boolean paramBoolean)
  {
    return populateMap(new LinkedHashMap(getCount() * 2, 1.0F), paramBoolean);
  }

  private static void log(Source paramSource, String paramString1, CharSequence paramCharSequence, int paramInt1, String paramString2, int paramInt2)
  {
    paramSource.logger.info(paramSource.getRowColumnVector(paramInt2).appendTo(paramSource.getRowColumnVector(paramInt1).appendTo(new StringBuilder(200).append(paramString1).append(' ').append(paramCharSequence).append(" at ")).append(' ').append(paramString2).append(" at position ")).toString());
  }

  private static void log(Source paramSource, String paramString1, CharSequence paramCharSequence, int paramInt, String paramString2)
  {
    paramSource.logger.info(' ' + paramString2);
  }

  private static enum ParsingState
  {
    AFTER_TAG_NAME, BETWEEN_ATTRIBUTES, IN_NAME, AFTER_NAME, START_VALUE, IN_VALUE, AFTER_VALUE_FINAL_QUOTE;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.Attributes
 * JD-Core Version:    0.6.2
 */