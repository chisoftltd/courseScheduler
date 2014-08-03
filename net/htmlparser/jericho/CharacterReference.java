package net.htmlparser.jericho;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

public abstract class CharacterReference extends Segment
{
  int codePoint;
  public static final int INVALID_CODE_POINT = -1;
  static int MAX_ENTITY_REFERENCE_LENGTH;
  private static final int TAB_LENGTH = 4;

  CharacterReference(Source paramSource, int paramInt1, int paramInt2, int paramInt3)
  {
    super(paramSource, paramInt1, paramInt2);
    this.codePoint = paramInt3;
  }

  public int getCodePoint()
  {
    return this.codePoint;
  }

  public char getChar()
  {
    return (char)this.codePoint;
  }

  public final void appendCharTo(Appendable paramAppendable)
    throws IOException
  {
    appendCharTo(paramAppendable, Config.ConvertNonBreakingSpaces);
  }

  private void appendCharTo(Appendable paramAppendable, boolean paramBoolean)
    throws IOException
  {
    if (Character.isSupplementaryCodePoint(this.codePoint))
    {
      paramAppendable.append(getHighSurrogate(this.codePoint));
      paramAppendable.append(getLowSurrogate(this.codePoint));
    }
    else
    {
      char c = getChar();
      if ((c == ' ') && (paramBoolean))
        paramAppendable.append(' ');
      else
        paramAppendable.append(c);
    }
  }

  public boolean isTerminated()
  {
    return this.source.charAt(this.end - 1) == ';';
  }

  public static String encode(CharSequence paramCharSequence)
  {
    if (paramCharSequence == null)
      return null;
    try
    {
      return appendEncode(new StringBuilder(paramCharSequence.length() * 2), paramCharSequence, false).toString();
    }
    catch (IOException localIOException)
    {
      throw new RuntimeException(localIOException);
    }
  }

  public static String encode(char paramChar)
  {
    try
    {
      return appendEncode(new StringBuilder(MAX_ENTITY_REFERENCE_LENGTH), paramChar).toString();
    }
    catch (IOException localIOException)
    {
      throw new RuntimeException(localIOException);
    }
  }

  public static String encodeWithWhiteSpaceFormatting(CharSequence paramCharSequence)
  {
    if (paramCharSequence == null)
      return null;
    try
    {
      return appendEncode(new StringBuilder(paramCharSequence.length() * 2), paramCharSequence, true).toString();
    }
    catch (IOException localIOException)
    {
      throw new RuntimeException(localIOException);
    }
  }

  public static String decode(CharSequence paramCharSequence)
  {
    return decode(paramCharSequence, false, Config.ConvertNonBreakingSpaces);
  }

  public static String decode(CharSequence paramCharSequence, boolean paramBoolean)
  {
    return decode(paramCharSequence, paramBoolean, Config.ConvertNonBreakingSpaces);
  }

  static String decode(CharSequence paramCharSequence, boolean paramBoolean1, boolean paramBoolean2)
  {
    if (paramCharSequence == null)
      return null;
    for (int i = 0; i < paramCharSequence.length(); i++)
      if (paramCharSequence.charAt(i) == '&')
        try
        {
          return appendDecode(new StringBuilder(paramCharSequence.length()), paramCharSequence, i, paramBoolean1, paramBoolean2).toString();
        }
        catch (IOException localIOException)
        {
          throw new RuntimeException(localIOException);
        }
    return paramCharSequence.toString();
  }

  public static String decodeCollapseWhiteSpace(CharSequence paramCharSequence)
  {
    return decodeCollapseWhiteSpace(paramCharSequence, Config.ConvertNonBreakingSpaces);
  }

  static String decodeCollapseWhiteSpace(CharSequence paramCharSequence, boolean paramBoolean)
  {
    return decode(appendCollapseWhiteSpace(new StringBuilder(paramCharSequence.length()), paramCharSequence), false, paramBoolean);
  }

  public static String reencode(CharSequence paramCharSequence)
  {
    return encode(decode(paramCharSequence, true));
  }

  public abstract String getCharacterReferenceString();

  public static String getCharacterReferenceString(int paramInt)
  {
    String str = null;
    if (paramInt != 39)
      str = CharacterEntityReference.getCharacterReferenceString(paramInt);
    if (str == null)
      str = NumericCharacterReference.getCharacterReferenceString(paramInt);
    return str;
  }

  public String getDecimalCharacterReferenceString()
  {
    return getDecimalCharacterReferenceString(this.codePoint);
  }

  public static String getDecimalCharacterReferenceString(int paramInt)
  {
    try
    {
      return appendDecimalCharacterReferenceString(new StringBuilder(), paramInt).toString();
    }
    catch (IOException localIOException)
    {
      throw new RuntimeException(localIOException);
    }
  }

  public String getHexadecimalCharacterReferenceString()
  {
    return getHexadecimalCharacterReferenceString(this.codePoint);
  }

  public static String getHexadecimalCharacterReferenceString(int paramInt)
  {
    try
    {
      return appendHexadecimalCharacterReferenceString(new StringBuilder(), paramInt).toString();
    }
    catch (IOException localIOException)
    {
      throw new RuntimeException(localIOException);
    }
  }

  public String getUnicodeText()
  {
    return getUnicodeText(this.codePoint);
  }

  public static String getUnicodeText(int paramInt)
  {
    try
    {
      return appendUnicodeText(new StringBuilder(), paramInt).toString();
    }
    catch (IOException localIOException)
    {
      throw new RuntimeException(localIOException);
    }
  }

  static final Appendable appendUnicodeText(Appendable paramAppendable, int paramInt)
    throws IOException
  {
    paramAppendable.append("U+");
    String str = Integer.toString(paramInt, 16).toUpperCase();
    for (int i = 4 - str.length(); i > 0; i--)
      paramAppendable.append('0');
    paramAppendable.append(str);
    return paramAppendable;
  }

  public static CharacterReference parse(CharSequence paramCharSequence)
  {
    return construct(new Source(paramCharSequence, true), 0, Config.UnterminatedCharacterReferenceSettings.ACCEPT_ALL);
  }

  public static int getCodePointFromCharacterReferenceString(CharSequence paramCharSequence)
  {
    CharacterReference localCharacterReference = parse(paramCharSequence);
    return localCharacterReference != null ? localCharacterReference.getCodePoint() : -1;
  }

  public static final boolean requiresEncoding(char paramChar)
  {
    return (paramChar > '') || ((CharacterEntityReference.getName(paramChar) != null) && ((paramChar != '\'') || (Config.IsApostropheEncoded)));
  }

  public static Writer getEncodingFilterWriter(Writer paramWriter)
  {
    return new EncodingFilterWriter(paramWriter);
  }

  private static Appendable appendEncode(Appendable paramAppendable, char paramChar)
    throws IOException
  {
    if (appendEncodeCheckForWhiteSpaceFormatting(paramAppendable, paramChar, false))
      return paramAppendable;
    return paramAppendable.append(paramChar);
  }

  static Appendable appendEncode(Appendable paramAppendable, CharSequence paramCharSequence, boolean paramBoolean)
    throws IOException
  {
    if (paramCharSequence == null)
      return paramAppendable;
    int i = 0;
    int j = paramCharSequence.length();
    if ((paramCharSequence instanceof Segment))
    {
      Segment localSegment = (Segment)paramCharSequence;
      k = localSegment.getBegin();
      i = k;
      j += k;
      paramCharSequence = localSegment.source;
    }
    boolean bool = Config.IsApostropheEncoded;
    for (int k = i; k < j; k++)
    {
      char c = paramCharSequence.charAt(k);
      if (!appendEncodeCheckForWhiteSpaceFormatting(paramAppendable, c, paramBoolean))
      {
        int n = k + 1;
        int m;
        if (c != ' ')
        {
          if (c != '\t')
          {
            if ((c == '\r') && (n < j) && (paramCharSequence.charAt(n) == '\n'))
              k++;
            paramAppendable.append("<br />");
            continue;
          }
          m = 4;
        }
        else
        {
          m = 1;
        }
        while (n < j)
        {
          c = paramCharSequence.charAt(n);
          if (c == ' ')
          {
            m++;
          }
          else
          {
            if (c != '\t')
              break;
            m += 4;
          }
          n++;
        }
        if (m == 1)
        {
          paramAppendable.append(' ');
        }
        else
        {
          if (m % 2 == 1)
            paramAppendable.append(' ');
          while (m >= 2)
          {
            paramAppendable.append("&nbsp; ");
            m -= 2;
          }
          k = n - 1;
        }
      }
    }
    return paramAppendable;
  }

  private static final boolean appendEncodeCheckForWhiteSpaceFormatting(Appendable paramAppendable, char paramChar, boolean paramBoolean)
    throws IOException
  {
    String str = CharacterEntityReference.getName(paramChar);
    if (str != null)
    {
      if (paramChar == '\'')
      {
        if (Config.IsApostropheEncoded)
          paramAppendable.append("&#39;");
        else
          paramAppendable.append(paramChar);
      }
      else
        CharacterEntityReference.appendCharacterReferenceString(paramAppendable, str);
    }
    else if (paramChar > '')
      appendDecimalCharacterReferenceString(paramAppendable, paramChar);
    else if ((!paramBoolean) || (!isWhiteSpace(paramChar)))
      paramAppendable.append(paramChar);
    else
      return false;
    return true;
  }

  static CharacterReference getPrevious(Source paramSource, int paramInt)
  {
    return getPrevious(paramSource, paramInt, Config.UnterminatedCharacterReferenceSettings.ACCEPT_ALL);
  }

  static CharacterReference getNext(Source paramSource, int paramInt)
  {
    return getNext(paramSource, paramInt, Config.UnterminatedCharacterReferenceSettings.ACCEPT_ALL);
  }

  private static CharacterReference getPrevious(Source paramSource, int paramInt, Config.UnterminatedCharacterReferenceSettings paramUnterminatedCharacterReferenceSettings)
  {
    ParseText localParseText = paramSource.getParseText();
    for (paramInt = localParseText.lastIndexOf('&', paramInt); paramInt != -1; paramInt = localParseText.lastIndexOf('&', paramInt - 1))
    {
      CharacterReference localCharacterReference = construct(paramSource, paramInt, paramUnterminatedCharacterReferenceSettings);
      if (localCharacterReference != null)
        return localCharacterReference;
    }
    return null;
  }

  private static CharacterReference getNext(Source paramSource, int paramInt, Config.UnterminatedCharacterReferenceSettings paramUnterminatedCharacterReferenceSettings)
  {
    ParseText localParseText = paramSource.getParseText();
    for (paramInt = localParseText.indexOf('&', paramInt); paramInt != -1; paramInt = localParseText.indexOf('&', paramInt + 1))
    {
      CharacterReference localCharacterReference = construct(paramSource, paramInt, paramUnterminatedCharacterReferenceSettings);
      if (localCharacterReference != null)
        return localCharacterReference;
    }
    return null;
  }

  static final Appendable appendHexadecimalCharacterReferenceString(Appendable paramAppendable, int paramInt)
    throws IOException
  {
    return paramAppendable.append("&#x").append(Integer.toString(paramInt, 16)).append(';');
  }

  static final Appendable appendDecimalCharacterReferenceString(Appendable paramAppendable, int paramInt)
    throws IOException
  {
    return paramAppendable.append("&#").append(Integer.toString(paramInt)).append(';');
  }

  static CharacterReference construct(Source paramSource, int paramInt, Config.UnterminatedCharacterReferenceSettings paramUnterminatedCharacterReferenceSettings)
  {
    try
    {
      if (paramSource.getParseText().charAt(paramInt) != '&')
        return null;
      return paramSource.getParseText().charAt(paramInt + 1) == '#' ? NumericCharacterReference.construct(paramSource, paramInt, paramUnterminatedCharacterReferenceSettings) : CharacterEntityReference.construct(paramSource, paramInt, paramUnterminatedCharacterReferenceSettings.characterEntityReferenceMaxCodePoint);
    }
    catch (IndexOutOfBoundsException localIndexOutOfBoundsException)
    {
    }
    return null;
  }

  private static Appendable appendDecode(Appendable paramAppendable, CharSequence paramCharSequence, int paramInt, boolean paramBoolean1, boolean paramBoolean2)
    throws IOException
  {
    Config.UnterminatedCharacterReferenceSettings localUnterminatedCharacterReferenceSettings = Config.CurrentCompatibilityMode.getUnterminatedCharacterReferenceSettings(paramBoolean1);
    int i = 0;
    StreamedSource localStreamedSource = new StreamedSource(paramCharSequence).setHandleTags(false).setSearchBegin(paramInt);
    Iterator localIterator = localStreamedSource.iterator();
    while (localIterator.hasNext())
    {
      Segment localSegment = (Segment)localIterator.next();
      if ((localSegment instanceof CharacterReference))
        ((CharacterReference)localSegment).appendCharTo(paramAppendable, paramBoolean2);
      else
        paramAppendable.append(localSegment.toString());
    }
    return paramAppendable;
  }

  private static char getHighSurrogate(int paramInt)
  {
    return (char)(55232 + (paramInt >> 10));
  }

  private static char getLowSurrogate(int paramInt)
  {
    return (char)(56320 + (paramInt & 0x3FF));
  }

  private static final class EncodingFilterWriter extends FilterWriter
  {
    StringBuilder sb = new StringBuilder(CharacterReference.MAX_ENTITY_REFERENCE_LENGTH);

    public EncodingFilterWriter(Writer paramWriter)
    {
      super();
    }

    public void write(char paramChar)
      throws IOException
    {
      this.sb.setLength(0);
      CharacterReference.appendEncode(this.sb, paramChar);
      if (this.sb.length() == 1)
        this.out.write(this.sb.charAt(0));
      else
        this.out.append(this.sb);
    }

    public void write(int paramInt)
      throws IOException
    {
      write((char)paramInt);
    }

    public void write(char[] paramArrayOfChar, int paramInt1, int paramInt2)
      throws IOException
    {
      int i = paramInt1 + paramInt2;
      for (int j = paramInt1; j < i; j++)
        write(paramArrayOfChar[j]);
    }

    public void write(String paramString, int paramInt1, int paramInt2)
      throws IOException
    {
      int i = paramInt1 + paramInt2;
      for (int j = paramInt1; j < i; j++)
        write(paramString.charAt(j));
    }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.CharacterReference
 * JD-Core Version:    0.6.2
 */