package net.htmlparser.jericho;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Renderer
  implements CharStreamSource
{
  private final Segment rootSegment;
  private int maxLineLength = 76;
  private String newLine = "\r\n";
  private boolean includeHyperlinkURLs = true;
  private boolean decorateFontStyles = false;
  private boolean convertNonBreakingSpaces = Config.ConvertNonBreakingSpaces;
  private int blockIndentSize = 4;
  private int listIndentSize = 6;
  private char[] listBullets = { '*', 'o', '+', '#' };
  private String tableCellSeparator = " \t";

  public Renderer(Segment paramSegment)
  {
    this.rootSegment = paramSegment;
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
    new Processor(this, this.rootSegment, getMaxLineLength(), getNewLine(), getIncludeHyperlinkURLs(), getDecorateFontStyles(), getConvertNonBreakingSpaces(), getBlockIndentSize(), getListIndentSize(), getListBullets(), getTableCellSeparator()).appendTo(paramAppendable);
  }

  public long getEstimatedMaximumOutputLength()
  {
    return this.rootSegment.length();
  }

  public String toString()
  {
    return CharStreamSourceUtil.toString(this);
  }

  public Renderer setMaxLineLength(int paramInt)
  {
    this.maxLineLength = paramInt;
    return this;
  }

  public int getMaxLineLength()
  {
    return this.maxLineLength;
  }

  public Renderer setNewLine(String paramString)
  {
    this.newLine = paramString;
    return this;
  }

  public String getNewLine()
  {
    if (this.newLine == null)
      this.newLine = this.rootSegment.source.getBestGuessNewLine();
    return this.newLine;
  }

  public Renderer setIncludeHyperlinkURLs(boolean paramBoolean)
  {
    this.includeHyperlinkURLs = paramBoolean;
    return this;
  }

  public boolean getIncludeHyperlinkURLs()
  {
    return this.includeHyperlinkURLs;
  }

  public String renderHyperlinkURL(StartTag paramStartTag)
  {
    String str = paramStartTag.getAttributeValue("href");
    if ((str == null) || (str.equals("#")) || (str.startsWith("javascript:")))
      return null;
    return '<' + str + '>';
  }

  public Renderer setDecorateFontStyles(boolean paramBoolean)
  {
    this.decorateFontStyles = paramBoolean;
    return this;
  }

  public boolean getDecorateFontStyles()
  {
    return this.decorateFontStyles;
  }

  public Renderer setConvertNonBreakingSpaces(boolean paramBoolean)
  {
    this.convertNonBreakingSpaces = paramBoolean;
    return this;
  }

  public boolean getConvertNonBreakingSpaces()
  {
    return this.convertNonBreakingSpaces;
  }

  public Renderer setBlockIndentSize(int paramInt)
  {
    this.blockIndentSize = paramInt;
    return this;
  }

  public int getBlockIndentSize()
  {
    return this.blockIndentSize;
  }

  public Renderer setListIndentSize(int paramInt)
  {
    this.listIndentSize = paramInt;
    return this;
  }

  public int getListIndentSize()
  {
    return this.listIndentSize;
  }

  public Renderer setListBullets(char[] paramArrayOfChar)
  {
    if ((paramArrayOfChar == null) || (paramArrayOfChar.length == 0))
      throw new IllegalArgumentException("listBullets argument must be an array of at least one character");
    this.listBullets = paramArrayOfChar;
    return this;
  }

  public char[] getListBullets()
  {
    return this.listBullets;
  }

  public Renderer setTableCellSeparator(String paramString)
  {
    this.tableCellSeparator = paramString;
    return this;
  }

  public String getTableCellSeparator()
  {
    return this.tableCellSeparator;
  }

  private static final class Processor
  {
    private final Renderer renderer;
    private final Segment rootSegment;
    private final Source source;
    private final int maxLineLength;
    private final String newLine;
    private final boolean includeHyperlinkURLs;
    private final boolean decorateFontStyles;
    private final boolean convertNonBreakingSpaces;
    private final int blockIndentSize;
    private final int listIndentSize;
    private final char[] listBullets;
    private final String tableCellSeparator;
    private Appendable appendable;
    private int renderedIndex;
    private boolean atStartOfLine;
    private int col;
    private int blockIndentLevel;
    private int listIndentLevel;
    private int blockVerticalMargin;
    private boolean preformatted;
    private boolean lastCharWhiteSpace;
    private boolean ignoreInitialWhitespace;
    private boolean bullet;
    private int listBulletNumber;
    private static final int NO_MARGIN = -1;
    private static final int UNORDERED_LIST = -1;
    private static Map<String, ElementHandler> ELEMENT_HANDLERS;

    public Processor(Renderer paramRenderer, Segment paramSegment, int paramInt1, String paramString1, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, int paramInt2, int paramInt3, char[] paramArrayOfChar, String paramString2)
    {
      this.renderer = paramRenderer;
      this.rootSegment = paramSegment;
      this.source = paramSegment.source;
      this.maxLineLength = paramInt1;
      this.newLine = paramString1;
      this.includeHyperlinkURLs = paramBoolean1;
      this.decorateFontStyles = paramBoolean2;
      this.convertNonBreakingSpaces = paramBoolean3;
      this.blockIndentSize = paramInt2;
      this.listIndentSize = paramInt3;
      this.listBullets = paramArrayOfChar;
      this.tableCellSeparator = paramString2;
    }

    public void appendTo(Appendable paramAppendable)
      throws IOException
    {
      reset();
      this.appendable = paramAppendable;
      appendSegmentProcessingChildElements(this.rootSegment.begin, this.rootSegment.end, this.rootSegment.getChildElements());
    }

    private void reset()
    {
      this.renderedIndex = 0;
      this.atStartOfLine = true;
      this.col = 0;
      this.blockIndentLevel = 0;
      this.listIndentLevel = 0;
      this.blockVerticalMargin = -1;
      this.preformatted = false;
      this.lastCharWhiteSpace = (this.ignoreInitialWhitespace = 0);
      this.bullet = false;
    }

    private void appendElementContent(Element paramElement)
      throws IOException
    {
      int i = paramElement.getContentEnd();
      if ((paramElement.isEmpty()) || (this.renderedIndex >= i))
        return;
      int j = paramElement.getStartTag().end;
      appendSegmentProcessingChildElements(Math.max(this.renderedIndex, j), i, paramElement.getChildElements());
    }

    private void appendSegmentProcessingChildElements(int paramInt1, int paramInt2, List<Element> paramList)
      throws IOException
    {
      int i = paramInt1;
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext())
      {
        Element localElement = (Element)localIterator.next();
        if (i < localElement.end)
        {
          if (i < localElement.begin)
            appendSegmentRemovingTags(i, localElement.begin);
          getElementHandler(localElement).process(this, localElement);
          i = Math.max(this.renderedIndex, localElement.end);
        }
      }
      if (i < paramInt2)
        appendSegmentRemovingTags(i, paramInt2);
    }

    private static ElementHandler getElementHandler(Element paramElement)
    {
      if (paramElement.getStartTag().getStartTagType().isServerTag())
        return RemoveElementHandler.INSTANCE;
      ElementHandler localElementHandler = (ElementHandler)ELEMENT_HANDLERS.get(paramElement.getName());
      return localElementHandler != null ? localElementHandler : StandardInlineElementHandler.INSTANCE;
    }

    private void appendSegmentRemovingTags(int paramInt1, int paramInt2)
      throws IOException
    {
      Tag localTag;
      for (int i = paramInt1; ; i = localTag.end)
      {
        localTag = this.source.getNextTag(i);
        if ((localTag == null) || (localTag.begin >= paramInt2))
          break;
        appendSegment(i, localTag.begin);
      }
      appendSegment(i, paramInt2);
    }

    private void appendSegment(int paramInt1, int paramInt2)
      throws IOException
    {
      assert (paramInt1 <= paramInt2);
      if (paramInt1 < this.renderedIndex)
        paramInt1 = this.renderedIndex;
      if (paramInt1 >= paramInt2)
        return;
      try
      {
        if (this.preformatted)
          appendPreformattedSegment(paramInt1, paramInt2);
        else
          appendNonPreformattedSegment(paramInt1, paramInt2);
        if (this.renderedIndex < paramInt2)
          this.renderedIndex = paramInt2;
      }
      finally
      {
        if (this.renderedIndex < paramInt2)
          this.renderedIndex = paramInt2;
      }
    }

    private void appendPreformattedSegment(int paramInt1, int paramInt2)
      throws IOException
    {
      assert (paramInt1 < paramInt2);
      assert (paramInt1 >= this.renderedIndex);
      if (isStartOfBlock())
        appendBlockVerticalMargin();
      String str = CharacterReference.decode(this.source.subSequence(paramInt1, paramInt2), false, this.convertNonBreakingSpaces);
      for (int i = 0; i < str.length(); i++)
      {
        char c = str.charAt(i);
        if (c == '\n')
        {
          newLine();
        }
        else if (c == '\r')
        {
          newLine();
          int j = i + 1;
          if (j == str.length())
            break;
          if (str.charAt(j) == '\n')
            i++;
        }
        else
        {
          append(c);
        }
      }
    }

    private void appendNonPreformattedSegment(int paramInt1, int paramInt2)
      throws IOException
    {
      assert (paramInt1 < paramInt2);
      assert (paramInt1 >= this.renderedIndex);
      String str = CharacterReference.decodeCollapseWhiteSpace(this.source.subSequence(paramInt1, paramInt2), this.convertNonBreakingSpaces);
      if (str.length() == 0)
      {
        if (!this.ignoreInitialWhitespace)
          this.lastCharWhiteSpace = true;
        return;
      }
      if (isStartOfBlock())
        appendBlockVerticalMargin();
      else if ((this.lastCharWhiteSpace) || ((Segment.isWhiteSpace(this.source.charAt(paramInt1))) && (!this.ignoreInitialWhitespace)))
        append(' ');
      int i = 0;
      int j = 0;
      this.lastCharWhiteSpace = (this.ignoreInitialWhitespace = 0);
      while (true)
        if ((j < str.length()) && ((str.charAt(j) != ' ') || ((j + 1 < str.length()) && (str.charAt(j + 1) == '>')) || ((j + 6 < str.length()) && (str.startsWith("From ", j + 1)))))
        {
          j++;
        }
        else
        {
          if (this.col + j - i + 1 >= this.maxLineLength)
          {
            if ((this.lastCharWhiteSpace) && ((this.blockIndentLevel | this.listIndentLevel) == 0))
              append(' ');
            startNewLine(0);
          }
          else if (this.lastCharWhiteSpace)
          {
            append(' ');
          }
          append(str, i, j);
          if (j == str.length())
            break;
          this.lastCharWhiteSpace = true;
          j++;
          i = j;
        }
      this.lastCharWhiteSpace = Segment.isWhiteSpace(this.source.charAt(paramInt2 - 1));
    }

    private boolean isStartOfBlock()
    {
      return this.blockVerticalMargin != -1;
    }

    private void appendBlockVerticalMargin()
      throws IOException
    {
      assert (this.blockVerticalMargin != -1);
      startNewLine(this.blockVerticalMargin);
      this.blockVerticalMargin = -1;
    }

    private void blockBoundary(int paramInt)
      throws IOException
    {
      if (this.blockVerticalMargin < paramInt)
        this.blockVerticalMargin = paramInt;
    }

    private void startNewLine(int paramInt)
      throws IOException
    {
      int i = paramInt + (this.atStartOfLine ? 0 : 1);
      for (int j = 0; j < i; j++)
        this.appendable.append(this.newLine);
      this.atStartOfLine = true;
      this.lastCharWhiteSpace = (this.ignoreInitialWhitespace = 0);
    }

    private void newLine()
      throws IOException
    {
      this.appendable.append(this.newLine);
      this.atStartOfLine = true;
      this.lastCharWhiteSpace = (this.ignoreInitialWhitespace = 0);
    }

    private void appendIndent()
      throws IOException
    {
      for (int i = this.blockIndentLevel * this.blockIndentSize; i > 0; i--)
        this.appendable.append(' ');
      if (this.bullet)
      {
        for (i = (this.listIndentLevel - 1) * this.listIndentSize; i > 0; i--)
          this.appendable.append(' ');
        if (this.listBulletNumber == -1)
        {
          for (i = this.listIndentSize - 2; i > 0; i--)
            this.appendable.append(' ');
          this.appendable.append(this.listBullets[((this.listIndentLevel - 1) % this.listBullets.length)]).append(' ');
        }
        else
        {
          String str = Integer.toString(this.listBulletNumber);
          for (int k = this.listIndentSize - str.length() - 2; k > 0; k--)
            this.appendable.append(' ');
          this.appendable.append(str).append(". ");
        }
        this.bullet = false;
      }
      else
      {
        for (int j = this.listIndentLevel * this.listIndentSize; j > 0; j--)
          this.appendable.append(' ');
      }
      this.col = (this.blockIndentLevel * this.blockIndentSize + this.listIndentLevel * this.listIndentSize);
      this.atStartOfLine = false;
    }

    private Processor append(char paramChar)
      throws IOException
    {
      if (this.atStartOfLine)
        appendIndent();
      this.appendable.append(paramChar);
      this.col += 1;
      return this;
    }

    private Processor append(String paramString)
      throws IOException
    {
      if (this.atStartOfLine)
        appendIndent();
      this.appendable.append(paramString);
      this.col += paramString.length();
      return this;
    }

    private void append(CharSequence paramCharSequence, int paramInt1, int paramInt2)
      throws IOException
    {
      if (this.atStartOfLine)
        appendIndent();
      for (int i = paramInt1; i < paramInt2; i++)
        this.appendable.append(paramCharSequence.charAt(i));
      this.col += paramInt2 - paramInt1;
    }

    static
    {
      ELEMENT_HANDLERS = new HashMap();
      ELEMENT_HANDLERS.put("a", A_ElementHandler.INSTANCE);
      ELEMENT_HANDLERS.put("address", StandardBlockElementHandler.INSTANCE_0_0);
      ELEMENT_HANDLERS.put("applet", RemoveElementHandler.INSTANCE);
      ELEMENT_HANDLERS.put("b", FontStyleElementHandler.INSTANCE_B);
      ELEMENT_HANDLERS.put("blockquote", StandardBlockElementHandler.INSTANCE_1_1_INDENT);
      ELEMENT_HANDLERS.put("br", BR_ElementHandler.INSTANCE);
      ELEMENT_HANDLERS.put("button", RemoveElementHandler.INSTANCE);
      ELEMENT_HANDLERS.put("caption", StandardBlockElementHandler.INSTANCE_0_0);
      ELEMENT_HANDLERS.put("center", StandardBlockElementHandler.INSTANCE_1_1);
      ELEMENT_HANDLERS.put("code", FontStyleElementHandler.INSTANCE_CODE);
      ELEMENT_HANDLERS.put("dd", StandardBlockElementHandler.INSTANCE_0_0_INDENT);
      ELEMENT_HANDLERS.put("dir", ListElementHandler.INSTANCE_UL);
      ELEMENT_HANDLERS.put("div", StandardBlockElementHandler.INSTANCE_0_0);
      ELEMENT_HANDLERS.put("dt", StandardBlockElementHandler.INSTANCE_0_0);
      ELEMENT_HANDLERS.put("em", FontStyleElementHandler.INSTANCE_I);
      ELEMENT_HANDLERS.put("fieldset", StandardBlockElementHandler.INSTANCE_1_1);
      ELEMENT_HANDLERS.put("form", StandardBlockElementHandler.INSTANCE_1_1);
      ELEMENT_HANDLERS.put("h1", StandardBlockElementHandler.INSTANCE_2_1);
      ELEMENT_HANDLERS.put("h2", StandardBlockElementHandler.INSTANCE_2_1);
      ELEMENT_HANDLERS.put("h3", StandardBlockElementHandler.INSTANCE_2_1);
      ELEMENT_HANDLERS.put("h4", StandardBlockElementHandler.INSTANCE_2_1);
      ELEMENT_HANDLERS.put("h5", StandardBlockElementHandler.INSTANCE_2_1);
      ELEMENT_HANDLERS.put("h6", StandardBlockElementHandler.INSTANCE_2_1);
      ELEMENT_HANDLERS.put("head", RemoveElementHandler.INSTANCE);
      ELEMENT_HANDLERS.put("hr", HR_ElementHandler.INSTANCE);
      ELEMENT_HANDLERS.put("i", FontStyleElementHandler.INSTANCE_I);
      ELEMENT_HANDLERS.put("legend", StandardBlockElementHandler.INSTANCE_0_0);
      ELEMENT_HANDLERS.put("li", LI_ElementHandler.INSTANCE);
      ELEMENT_HANDLERS.put("menu", ListElementHandler.INSTANCE_UL);
      ELEMENT_HANDLERS.put("map", RemoveElementHandler.INSTANCE);
      ELEMENT_HANDLERS.put("noframes", RemoveElementHandler.INSTANCE);
      ELEMENT_HANDLERS.put("noscript", RemoveElementHandler.INSTANCE);
      ELEMENT_HANDLERS.put("ol", ListElementHandler.INSTANCE_OL);
      ELEMENT_HANDLERS.put("p", StandardBlockElementHandler.INSTANCE_1_1);
      ELEMENT_HANDLERS.put("pre", PRE_ElementHandler.INSTANCE);
      ELEMENT_HANDLERS.put("script", RemoveElementHandler.INSTANCE);
      ELEMENT_HANDLERS.put("select", RemoveElementHandler.INSTANCE);
      ELEMENT_HANDLERS.put("strong", FontStyleElementHandler.INSTANCE_B);
      ELEMENT_HANDLERS.put("style", RemoveElementHandler.INSTANCE);
      ELEMENT_HANDLERS.put("textarea", RemoveElementHandler.INSTANCE);
      ELEMENT_HANDLERS.put("td", TD_ElementHandler.INSTANCE);
      ELEMENT_HANDLERS.put("th", TD_ElementHandler.INSTANCE);
      ELEMENT_HANDLERS.put("tr", TR_ElementHandler.INSTANCE);
      ELEMENT_HANDLERS.put("u", FontStyleElementHandler.INSTANCE_U);
      ELEMENT_HANDLERS.put("ul", ListElementHandler.INSTANCE_UL);
    }

    private static class TR_ElementHandler
      implements Renderer.Processor.ElementHandler
    {
      public static final Renderer.Processor.ElementHandler INSTANCE = new TR_ElementHandler();

      public void process(Renderer.Processor paramProcessor, Element paramElement)
        throws IOException
      {
        paramProcessor.blockBoundary(0);
        paramProcessor.appendElementContent(paramElement);
        paramProcessor.blockBoundary(0);
      }
    }

    private static class TD_ElementHandler
      implements Renderer.Processor.ElementHandler
    {
      public static final Renderer.Processor.ElementHandler INSTANCE = new TD_ElementHandler();

      public void process(Renderer.Processor paramProcessor, Element paramElement)
        throws IOException
      {
        if (!paramProcessor.isStartOfBlock())
          paramProcessor.append(paramProcessor.tableCellSeparator);
        paramProcessor.lastCharWhiteSpace = false;
        paramProcessor.appendElementContent(paramElement);
      }
    }

    private static class PRE_ElementHandler
      implements Renderer.Processor.ElementHandler
    {
      public static final Renderer.Processor.ElementHandler INSTANCE = new PRE_ElementHandler();

      public void process(Renderer.Processor paramProcessor, Element paramElement)
        throws IOException
      {
        paramProcessor.blockBoundary(1);
        boolean bool = paramProcessor.preformatted;
        paramProcessor.preformatted = true;
        paramProcessor.appendElementContent(paramElement);
        paramProcessor.preformatted = bool;
        paramProcessor.blockBoundary(1);
      }
    }

    private static class LI_ElementHandler
      implements Renderer.Processor.ElementHandler
    {
      public static final Renderer.Processor.ElementHandler INSTANCE = new LI_ElementHandler();

      public void process(Renderer.Processor paramProcessor, Element paramElement)
        throws IOException
      {
        if (paramProcessor.listBulletNumber != -1)
          Renderer.Processor.access$1408(paramProcessor);
        paramProcessor.bullet = true;
        paramProcessor.blockBoundary(0);
        paramProcessor.appendBlockVerticalMargin();
        paramProcessor.appendIndent();
        paramProcessor.ignoreInitialWhitespace = true;
        paramProcessor.appendElementContent(paramElement);
        paramProcessor.bullet = false;
        paramProcessor.blockBoundary(0);
      }
    }

    private static class ListElementHandler
      implements Renderer.Processor.ElementHandler
    {
      public static final Renderer.Processor.ElementHandler INSTANCE_OL = new ListElementHandler(0);
      public static final Renderer.Processor.ElementHandler INSTANCE_UL = new ListElementHandler(-1);
      private final int initialListBulletNumber;

      public ListElementHandler(int paramInt)
      {
        this.initialListBulletNumber = paramInt;
      }

      public void process(Renderer.Processor paramProcessor, Element paramElement)
        throws IOException
      {
        paramProcessor.blockBoundary(0);
        int i = paramProcessor.listBulletNumber;
        paramProcessor.listBulletNumber = this.initialListBulletNumber;
        Renderer.Processor.access$1508(paramProcessor);
        paramProcessor.appendElementContent(paramElement);
        Renderer.Processor.access$1510(paramProcessor);
        paramProcessor.listBulletNumber = i;
        paramProcessor.blockBoundary(0);
      }
    }

    private static class HR_ElementHandler
      implements Renderer.Processor.ElementHandler
    {
      public static final Renderer.Processor.ElementHandler INSTANCE = new HR_ElementHandler();

      public void process(Renderer.Processor paramProcessor, Element paramElement)
        throws IOException
      {
        paramProcessor.blockBoundary(0);
        paramProcessor.appendBlockVerticalMargin();
        for (int i = 0; i < 72; i++)
          paramProcessor.append('-');
        paramProcessor.blockBoundary(0);
      }
    }

    private static class BR_ElementHandler
      implements Renderer.Processor.ElementHandler
    {
      public static final Renderer.Processor.ElementHandler INSTANCE = new BR_ElementHandler();

      public void process(Renderer.Processor paramProcessor, Element paramElement)
        throws IOException
      {
        paramProcessor.newLine();
        paramProcessor.blockBoundary(0);
      }
    }

    private static class A_ElementHandler
      implements Renderer.Processor.ElementHandler
    {
      public static final Renderer.Processor.ElementHandler INSTANCE = new A_ElementHandler();

      public void process(Renderer.Processor paramProcessor, Element paramElement)
        throws IOException
      {
        paramProcessor.appendElementContent(paramElement);
        if (!paramProcessor.includeHyperlinkURLs)
          return;
        String str = paramProcessor.renderer.renderHyperlinkURL(paramElement.getStartTag());
        if (str == null)
          return;
        int i = str.length() + 1;
        if (paramProcessor.col + i >= paramProcessor.maxLineLength)
          paramProcessor.startNewLine(0);
        else
          paramProcessor.append(' ');
        paramProcessor.append(str);
        paramProcessor.lastCharWhiteSpace = true;
      }
    }

    private static class StandardBlockElementHandler
      implements Renderer.Processor.ElementHandler
    {
      public static final Renderer.Processor.ElementHandler INSTANCE_0_0 = new StandardBlockElementHandler(0, 0, false);
      public static final Renderer.Processor.ElementHandler INSTANCE_1_1 = new StandardBlockElementHandler(1, 1, false);
      public static final Renderer.Processor.ElementHandler INSTANCE_2_1 = new StandardBlockElementHandler(2, 1, false);
      public static final Renderer.Processor.ElementHandler INSTANCE_0_0_INDENT = new StandardBlockElementHandler(0, 0, true);
      public static final Renderer.Processor.ElementHandler INSTANCE_1_1_INDENT = new StandardBlockElementHandler(1, 1, true);
      private final int topMargin;
      private final int bottomMargin;
      private final boolean indent;

      public StandardBlockElementHandler(int paramInt1, int paramInt2, boolean paramBoolean)
      {
        this.topMargin = paramInt1;
        this.bottomMargin = paramInt2;
        this.indent = paramBoolean;
      }

      public void process(Renderer.Processor paramProcessor, Element paramElement)
        throws IOException
      {
        paramProcessor.blockBoundary(this.topMargin);
        if (this.indent)
          Renderer.Processor.access$508(paramProcessor);
        paramProcessor.appendElementContent(paramElement);
        if (this.indent)
          Renderer.Processor.access$510(paramProcessor);
        paramProcessor.blockBoundary(this.bottomMargin);
      }
    }

    private static class FontStyleElementHandler
      implements Renderer.Processor.ElementHandler
    {
      public static final Renderer.Processor.ElementHandler INSTANCE_B = new FontStyleElementHandler('*');
      public static final Renderer.Processor.ElementHandler INSTANCE_I = new FontStyleElementHandler('/');
      public static final Renderer.Processor.ElementHandler INSTANCE_U = new FontStyleElementHandler('_');
      public static final Renderer.Processor.ElementHandler INSTANCE_CODE = new FontStyleElementHandler('|');
      private final char decorationChar;

      public FontStyleElementHandler(char paramChar)
      {
        this.decorationChar = paramChar;
      }

      public void process(Renderer.Processor paramProcessor, Element paramElement)
        throws IOException
      {
        if (paramProcessor.decorateFontStyles)
        {
          if (paramProcessor.lastCharWhiteSpace)
          {
            paramProcessor.append(' ');
            paramProcessor.lastCharWhiteSpace = false;
          }
          paramProcessor.append(this.decorationChar);
          paramProcessor.appendElementContent(paramElement);
          if (paramProcessor.decorateFontStyles)
            paramProcessor.append(this.decorationChar);
        }
        else
        {
          paramProcessor.appendElementContent(paramElement);
        }
      }
    }

    private static class StandardInlineElementHandler
      implements Renderer.Processor.ElementHandler
    {
      public static final Renderer.Processor.ElementHandler INSTANCE = new StandardInlineElementHandler();

      public void process(Renderer.Processor paramProcessor, Element paramElement)
        throws IOException
      {
        paramProcessor.appendElementContent(paramElement);
      }
    }

    private static class RemoveElementHandler
      implements Renderer.Processor.ElementHandler
    {
      public static final Renderer.Processor.ElementHandler INSTANCE = new RemoveElementHandler();

      public void process(Renderer.Processor paramProcessor, Element paramElement)
      {
      }
    }

    private static abstract interface ElementHandler
    {
      public abstract void process(Renderer.Processor paramProcessor, Element paramElement)
        throws IOException;
    }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.Renderer
 * JD-Core Version:    0.6.2
 */