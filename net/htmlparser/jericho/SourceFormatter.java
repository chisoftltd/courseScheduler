package net.htmlparser.jericho;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class SourceFormatter
  implements CharStreamSource
{
  private final Segment segment;
  private String indentString = "\t";
  private boolean tidyTags = false;
  private boolean collapseWhiteSpace = false;
  private boolean removeLineBreaks = false;
  private boolean indentAllElements = false;
  private String newLine = null;

  public SourceFormatter(Segment paramSegment)
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
    new Processor(this.segment, getIndentString(), getTidyTags(), getCollapseWhiteSpace(), getRemoveLineBreaks(), getIndentAllElements(), getIndentAllElements(), getNewLine()).appendTo(paramAppendable);
  }

  public long getEstimatedMaximumOutputLength()
  {
    return this.segment.length() * 2;
  }

  public String toString()
  {
    return CharStreamSourceUtil.toString(this);
  }

  public SourceFormatter setIndentString(String paramString)
  {
    if (paramString == null)
      throw new IllegalArgumentException("indentString property must not be null");
    this.indentString = paramString;
    return this;
  }

  public String getIndentString()
  {
    return this.indentString;
  }

  public SourceFormatter setTidyTags(boolean paramBoolean)
  {
    this.tidyTags = paramBoolean;
    return this;
  }

  public boolean getTidyTags()
  {
    return this.tidyTags;
  }

  public SourceFormatter setCollapseWhiteSpace(boolean paramBoolean)
  {
    this.collapseWhiteSpace = paramBoolean;
    return this;
  }

  public boolean getCollapseWhiteSpace()
  {
    return this.collapseWhiteSpace;
  }

  SourceFormatter setRemoveLineBreaks(boolean paramBoolean)
  {
    this.removeLineBreaks = paramBoolean;
    return this;
  }

  boolean getRemoveLineBreaks()
  {
    return this.removeLineBreaks;
  }

  public SourceFormatter setIndentAllElements(boolean paramBoolean)
  {
    this.indentAllElements = paramBoolean;
    return this;
  }

  public boolean getIndentAllElements()
  {
    return this.indentAllElements;
  }

  public SourceFormatter setNewLine(String paramString)
  {
    this.newLine = paramString;
    return this;
  }

  public String getNewLine()
  {
    if (this.newLine == null)
      this.newLine = this.segment.source.getBestGuessNewLine();
    return this.newLine;
  }

  private static final class Processor
  {
    private final Segment segment;
    private final CharSequence sourceText;
    private final String indentString;
    private final boolean tidyTags;
    private final boolean collapseWhiteSpace;
    private final boolean removeLineBreaks;
    private final boolean indentAllElements;
    private final boolean indentScriptElements;
    private final String newLine;
    private Appendable appendable;
    private Tag nextTag;
    private int index;

    public Processor(Segment paramSegment, String paramString1, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, String paramString2)
    {
      this.segment = paramSegment;
      this.sourceText = paramSegment.source.toString();
      this.indentString = paramString1;
      this.tidyTags = paramBoolean1;
      this.collapseWhiteSpace = ((paramBoolean2) || (paramBoolean3));
      this.removeLineBreaks = paramBoolean3;
      this.indentAllElements = paramBoolean4;
      this.indentScriptElements = paramBoolean5;
      this.newLine = paramString2;
    }

    public void appendTo(Appendable paramAppendable)
      throws IOException
    {
      this.appendable = paramAppendable;
      if ((this.segment instanceof Source))
        ((Source)this.segment).fullSequentialParse();
      this.nextTag = this.segment.source.getNextTag(this.segment.begin);
      this.index = this.segment.begin;
      appendContent(this.segment.end, this.segment.getChildElements(), 0);
    }

    private void appendContent(int paramInt1, List<Element> paramList, int paramInt2)
      throws IOException
    {
      assert (this.index <= paramInt1);
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext())
      {
        Element localElement = (Element)localIterator.next();
        int i = localElement.begin;
        if (i >= paramInt1)
          break;
        if (this.indentAllElements)
        {
          appendText(i, paramInt2);
          appendElement(localElement, paramInt2, paramInt1, false, false);
        }
        else if (!inlinable(localElement))
        {
          appendText(i, paramInt2);
          String str = localElement.getName();
          if ((str == "pre") || (str == "textarea"))
            appendElement(localElement, paramInt2, paramInt1, true, true);
          else if (str == "script")
            appendElement(localElement, paramInt2, paramInt1, true, false);
          else
            appendElement(localElement, paramInt2, paramInt1, false, (!this.removeLineBreaks) && (containsOnlyInlineLevelChildElements(localElement)));
        }
      }
      appendText(paramInt1, paramInt2);
      assert (this.index == paramInt1);
    }

    private boolean inlinable(Element paramElement)
    {
      StartTagType localStartTagType = paramElement.getStartTag().getStartTagType();
      if (localStartTagType == StartTagType.DOCTYPE_DECLARATION)
        return false;
      if (localStartTagType != StartTagType.NORMAL)
        return true;
      String str = paramElement.getName();
      if (str == "script")
        return !this.indentScriptElements;
      if ((this.removeLineBreaks) && (!HTMLElements.getElementNames().contains(str)))
        return true;
      if (!HTMLElements.getInlineLevelElementNames().contains(str))
        return false;
      if (this.removeLineBreaks)
        return true;
      return containsOnlyInlineLevelChildElements(paramElement);
    }

    private void appendText(int paramInt1, int paramInt2)
      throws IOException
    {
      assert (this.index <= paramInt1);
      if (this.index == paramInt1)
        return;
      while (Segment.isWhiteSpace(this.sourceText.charAt(this.index)))
        if (++this.index == paramInt1)
          return;
      appendIndent(paramInt2);
      if (this.collapseWhiteSpace)
        appendTextCollapseWhiteSpace(paramInt1, paramInt2);
      else
        appendTextInline(paramInt1, paramInt2, false);
      appendFormattingNewLine();
      assert (this.index == paramInt1);
    }

    private void appendElement(Element paramElement, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
      throws IOException
    {
      assert (this.index == paramElement.begin);
      assert (this.index < paramInt2);
      StartTag localStartTag = paramElement.getStartTag();
      EndTag localEndTag = paramElement.getEndTag();
      appendIndent(paramInt1);
      appendTag(localStartTag, paramInt1, paramInt2);
      if (this.index == paramInt2)
      {
        appendFormattingNewLine();
        assert (this.index == Math.min(paramElement.end, paramInt2)) : this.index;
        return;
      }
      if (!paramBoolean2)
        appendFormattingNewLine();
      int i = paramElement.getContentEnd();
      if (paramInt2 < i)
        i = paramInt2;
      if (this.index < i)
        if (paramBoolean1)
        {
          if (paramBoolean2)
            appendContentPreformatted(i, paramInt1);
          else
            appendIndentedScriptContent(i, paramInt1 + 1);
        }
        else if (paramBoolean2)
        {
          if (this.collapseWhiteSpace)
          {
            appendTextCollapseWhiteSpace(i, paramInt1);
          }
          else if (!appendTextInline(i, paramInt1, true))
          {
            appendFormattingNewLine();
            paramBoolean2 = false;
          }
        }
        else
          appendContent(i, paramElement.getChildElements(), paramInt1 + 1);
      if ((localEndTag != null) && (paramInt2 > localEndTag.begin))
      {
        if (!paramBoolean2)
          appendIndent(paramInt1);
        assert (this.index == localEndTag.begin);
        appendTag(localEndTag, paramInt1, paramInt2);
        appendFormattingNewLine();
      }
      else if (paramBoolean2)
      {
        appendFormattingNewLine();
      }
      assert (this.index == Math.min(paramElement.end, paramInt2)) : this.index;
    }

    private void updateNextTag()
    {
      while (this.nextTag != null)
      {
        if (this.nextTag.begin >= this.index)
          return;
        this.nextTag = this.nextTag.getNextTag();
      }
    }

    private void appendIndentedScriptContent(int paramInt1, int paramInt2)
      throws IOException
    {
      assert (this.index < paramInt1);
      if (this.removeLineBreaks)
      {
        appendTextRemoveIndentation(paramInt1);
        assert (this.index == paramInt1);
        return;
      }
      int i = getStartOfLinePos(paramInt1, false);
      if (this.index == paramInt1)
        return;
      if (i == -1)
      {
        appendIndent(paramInt2);
        appendLineKeepWhiteSpace(paramInt1, paramInt2);
        appendEssentialNewLine();
        if (this.index == paramInt1)
          return;
        i = getStartOfLinePos(paramInt1, true);
        if (this.index == paramInt1)
          return;
      }
      appendTextPreserveIndentation(paramInt1, paramInt2, this.index - i);
      appendEssentialNewLine();
      assert (this.index == paramInt1);
    }

    private boolean appendTextPreserveIndentation(int paramInt1, int paramInt2)
      throws IOException
    {
      assert (this.index < paramInt1);
      if (this.removeLineBreaks)
        return appendTextRemoveIndentation(paramInt1);
      appendLineKeepWhiteSpace(paramInt1, paramInt2);
      if (this.index == paramInt1)
        return true;
      int i = getStartOfLinePos(paramInt1, true);
      if (this.index == paramInt1)
        return true;
      appendEssentialNewLine();
      appendTextPreserveIndentation(paramInt1, paramInt2 + 1, this.index - i);
      assert (this.index == paramInt1);
      return false;
    }

    private void appendTextPreserveIndentation(int paramInt1, int paramInt2, int paramInt3)
      throws IOException
    {
      assert (this.index < paramInt1);
      appendIndent(paramInt2);
      appendLineKeepWhiteSpace(paramInt1, paramInt2);
      while (this.index != paramInt1)
      {
        for (int i = 0; i < paramInt3; i++)
        {
          int j = this.sourceText.charAt(this.index);
          if ((j != 32) && (j != 9))
            break;
          if (++this.index == paramInt1)
            return;
        }
        appendEssentialNewLine();
        appendIndent(paramInt2);
        appendLineKeepWhiteSpace(paramInt1, paramInt2);
      }
      assert (this.index == paramInt1);
    }

    private boolean appendTextRemoveIndentation(int paramInt)
      throws IOException
    {
      assert (this.index < paramInt);
      appendLineKeepWhiteSpace(paramInt, 0);
      if (this.index == paramInt)
        return true;
      while (this.index != paramInt)
      {
        while (true)
        {
          int i = this.sourceText.charAt(this.index);
          if ((i != 32) && (i != 9))
            break;
          if (++this.index == paramInt)
            return false;
        }
        appendEssentialNewLine();
        appendLineKeepWhiteSpace(paramInt, 0);
      }
      assert (this.index == paramInt);
      return false;
    }

    private int getStartOfLinePos(int paramInt, boolean paramBoolean)
    {
      int i = paramBoolean ? this.index : -1;
      while (true)
      {
        int j = this.sourceText.charAt(this.index);
        if ((j == 10) || (j == 13))
          i = this.index + 1;
        else
          if ((j != 32) && (j != 9))
            break;
        if (++this.index == paramInt)
          break;
      }
      return i;
    }

    private void appendSpecifiedTextInline(CharSequence paramCharSequence, int paramInt)
      throws IOException
    {
      int i = paramCharSequence.length();
      int j = appendSpecifiedLine(paramCharSequence, 0);
      if (j < i)
      {
        int k = paramInt + 1;
        do
        {
          while (Segment.isWhiteSpace(paramCharSequence.charAt(j)))
          {
            j++;
            if (j >= i)
              return;
          }
          appendEssentialNewLine();
          appendIndent(k);
          j = appendSpecifiedLine(paramCharSequence, j);
        }
        while (j < i);
      }
    }

    private int appendSpecifiedLine(CharSequence paramCharSequence, int paramInt)
      throws IOException
    {
      int i = paramCharSequence.length();
      while (true)
      {
        char c = paramCharSequence.charAt(paramInt);
        if (c == '\r')
        {
          int j = paramInt + 1;
          if ((j < i) && (paramCharSequence.charAt(j) == '\n'))
            return paramInt + 2;
        }
        if (c == '\n')
          return paramInt + 1;
        this.appendable.append(c);
        paramInt++;
        if (paramInt >= i)
          return paramInt;
      }
    }

    private boolean appendTextInline(int paramInt1, int paramInt2, boolean paramBoolean)
      throws IOException
    {
      assert (this.index < paramInt1);
      appendLineKeepWhiteSpace(paramInt1, paramInt2);
      if (this.index == paramInt1)
        return true;
      int i = paramBoolean ? paramInt2 + 1 : paramInt2;
      do
      {
        while (Segment.isWhiteSpace(this.sourceText.charAt(this.index)))
          if (++this.index == paramInt1)
            return false;
        appendEssentialNewLine();
        appendIndent(i);
        appendLineKeepWhiteSpace(paramInt1, i);
      }
      while (this.index < paramInt1);
      assert (this.index == paramInt1);
      return false;
    }

    private void appendLineKeepWhiteSpace(int paramInt1, int paramInt2)
      throws IOException
    {
      assert (this.index < paramInt1);
      updateNextTag();
      while (true)
        if ((this.nextTag != null) && (this.index == this.nextTag.begin))
        {
          appendTag(this.nextTag, paramInt2, paramInt1);
          if (this.index != paramInt1);
        }
        else
        {
          char c = this.sourceText.charAt(this.index);
          if (c == '\r')
          {
            int i = this.index + 1;
            if ((i < paramInt1) && (this.sourceText.charAt(i) == '\n'))
            {
              this.index += 2;
              assert (this.index <= paramInt1);
              return;
            }
          }
          if (c == '\n')
          {
            this.index += 1;
            assert (this.index <= paramInt1);
            return;
          }
          this.appendable.append(c);
          if (++this.index == paramInt1)
            return;
        }
    }

    private void appendTextCollapseWhiteSpace(int paramInt1, int paramInt2)
      throws IOException
    {
      assert (this.index < paramInt1);
      int i = 0;
      updateNextTag();
      while (this.index < paramInt1)
      {
        while ((this.nextTag != null) && (this.index == this.nextTag.begin))
        {
          if (i != 0)
          {
            this.appendable.append(' ');
            i = 0;
          }
          appendTag(this.nextTag, paramInt2, paramInt1);
          if (this.index == paramInt1)
            return;
        }
        char c = this.sourceText.charAt(this.index++);
        if (Segment.isWhiteSpace(c))
        {
          i = 1;
        }
        else
        {
          if (i != 0)
          {
            this.appendable.append(' ');
            i = 0;
          }
          this.appendable.append(c);
        }
      }
      if (i != 0)
        this.appendable.append(' ');
      assert (this.index == paramInt1);
    }

    private void appendContentPreformatted(int paramInt1, int paramInt2)
      throws IOException
    {
      assert (this.index < paramInt1);
      updateNextTag();
      do
      {
        while ((this.nextTag != null) && (this.index == this.nextTag.begin))
        {
          appendTag(this.nextTag, paramInt2, paramInt1);
          if (this.index == paramInt1)
            return;
        }
        this.appendable.append(this.sourceText.charAt(this.index));
      }
      while (++this.index < paramInt1);
      assert (this.index == paramInt1);
    }

    private void appendTag(Tag paramTag, int paramInt1, int paramInt2)
      throws IOException
    {
      assert (this.index == paramTag.begin);
      assert (this.index < paramInt2);
      this.nextTag = paramTag.getNextTag();
      int i = paramTag.end < paramInt2 ? paramTag.end : paramInt2;
      assert (this.index < i);
      Object localObject;
      if ((paramTag.getTagType() == StartTagType.COMMENT) || (paramTag.getTagType() == StartTagType.CDATA_SECTION) || (paramTag.getTagType().isServerTag()))
      {
        appendTextPreserveIndentation(i, paramInt1);
      }
      else if (this.tidyTags)
      {
        localObject = paramTag.tidy();
        if (((paramTag instanceof StartTag)) && (((StartTag)paramTag).getAttributes() != null))
          this.appendable.append((CharSequence)localObject);
        else
          appendSpecifiedTextInline((CharSequence)localObject, paramInt1);
        this.index = i;
      }
      else
      {
        appendTextInline(i, paramInt1, true);
      }
      if ((paramInt2 <= paramTag.end) || (!(paramTag instanceof StartTag)))
      {
        assert (this.index <= paramInt2);
        return;
      }
      if (((paramTag.name == "script") && (!this.indentScriptElements)) || (paramTag.getTagType().isServerTag()))
      {
        localObject = paramTag.getElement();
        EndTag localEndTag = ((Element)localObject).getEndTag();
        if (localEndTag == null)
        {
          assert (this.index <= paramInt2);
          return;
        }
        int j = paramInt2 < localEndTag.begin ? paramInt2 : localEndTag.begin;
        boolean bool = true;
        if (this.index != j)
          bool = appendTextPreserveIndentation(j, paramInt1);
        if (localEndTag.begin >= paramInt2)
        {
          assert (this.index <= paramInt2);
          return;
        }
        if (!bool)
        {
          appendEssentialNewLine();
          appendIndent(paramInt1);
        }
        assert (this.index == localEndTag.begin);
        appendTag(localEndTag, paramInt1, paramInt2);
      }
      assert (this.index <= paramInt2);
    }

    private void appendIndent(int paramInt)
      throws IOException
    {
      if (!this.removeLineBreaks)
        for (int i = 0; i < paramInt; i++)
          this.appendable.append(this.indentString);
    }

    private void appendFormattingNewLine()
      throws IOException
    {
      if (!this.removeLineBreaks)
        this.appendable.append(this.newLine);
    }

    private void appendEssentialNewLine()
      throws IOException
    {
      this.appendable.append(this.newLine);
    }

    private boolean containsOnlyInlineLevelChildElements(Element paramElement)
    {
      List localList = paramElement.getChildElements();
      if (localList.isEmpty())
        return true;
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext())
      {
        Element localElement = (Element)localIterator.next();
        String str = localElement.getName();
        if ((str == "script") || (!HTMLElements.getInlineLevelElementNames().contains(str)))
          return false;
        if (!containsOnlyInlineLevelChildElements(localElement))
          return false;
      }
      return true;
    }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.SourceFormatter
 * JD-Core Version:    0.6.2
 */