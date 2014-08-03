package net.htmlparser.jericho;

import java.io.IOException;

public class NumericCharacterReference extends CharacterReference
{
  private boolean hex;

  private NumericCharacterReference(Source paramSource, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
  {
    super(paramSource, paramInt1, paramInt2, paramInt3);
    this.hex = paramBoolean;
  }

  public boolean isDecimal()
  {
    return !this.hex;
  }

  public boolean isHexadecimal()
  {
    return this.hex;
  }

  public static String encode(CharSequence paramCharSequence)
  {
    if (paramCharSequence == null)
      return null;
    StringBuilder localStringBuilder = new StringBuilder(paramCharSequence.length() * 2);
    for (int i = 0; i < paramCharSequence.length(); i++)
    {
      char c = paramCharSequence.charAt(i);
      if (requiresEncoding(c))
        try
        {
          appendDecimalCharacterReferenceString(localStringBuilder, c);
        }
        catch (IOException localIOException)
        {
          throw new RuntimeException(localIOException);
        }
      else
        localStringBuilder.append(c);
    }
    return localStringBuilder.toString();
  }

  public static String encodeDecimal(CharSequence paramCharSequence)
  {
    return encode(paramCharSequence);
  }

  public static String encodeHexadecimal(CharSequence paramCharSequence)
  {
    if (paramCharSequence == null)
      return null;
    StringBuilder localStringBuilder = new StringBuilder(paramCharSequence.length() * 2);
    for (int i = 0; i < paramCharSequence.length(); i++)
    {
      char c = paramCharSequence.charAt(i);
      if (requiresEncoding(c))
        try
        {
          appendHexadecimalCharacterReferenceString(localStringBuilder, c);
        }
        catch (IOException localIOException)
        {
          throw new RuntimeException(localIOException);
        }
      else
        localStringBuilder.append(c);
    }
    return localStringBuilder.toString();
  }

  public String getCharacterReferenceString()
  {
    return this.hex ? getHexadecimalCharacterReferenceString(this.codePoint) : getDecimalCharacterReferenceString(this.codePoint);
  }

  public static String getCharacterReferenceString(int paramInt)
  {
    return getDecimalCharacterReferenceString(paramInt);
  }

  static CharacterReference construct(Source paramSource, int paramInt, Config.UnterminatedCharacterReferenceSettings paramUnterminatedCharacterReferenceSettings)
  {
    ParseText localParseText = paramSource.getParseText();
    int i = paramInt + 2;
    boolean bool;
    if ((bool = localParseText.charAt(i) == 'x' ? 1 : 0) != 0)
      i++;
    int j = bool ? paramUnterminatedCharacterReferenceSettings.hexadecimalCharacterReferenceMaxCodePoint : paramUnterminatedCharacterReferenceSettings.decimalCharacterReferenceMaxCodePoint;
    int k = paramSource.end - 1;
    int n = i;
    int i1 = 0;
    int m;
    String str;
    while (true)
    {
      i2 = localParseText.charAt(n);
      if (i2 == 59)
      {
        m = n + 1;
        str = paramSource.substring(i, n);
        break;
      }
      if (((i2 >= 48) && (i2 <= 57)) || ((bool) && (((i2 >= 97) && (i2 <= 102)) || ((i2 >= 65) && (i2 <= 70)))))
      {
        if (n == k)
        {
          i1 = 1;
          n++;
        }
      }
      else
        i1 = 1;
      if (i1 != 0)
      {
        if (j == -1)
          return null;
        m = n;
        str = paramSource.substring(i, n);
        break;
      }
      n++;
    }
    if (str.length() == 0)
      return null;
    int i2 = -1;
    try
    {
      i2 = Integer.parseInt(str, bool ? 16 : 10);
      if ((i1 != 0) && (i2 > j))
        return null;
      if (i2 > 1114111)
        i2 = -1;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      if (i1 != 0)
        return null;
    }
    return new NumericCharacterReference(paramSource, paramInt, m, i2, bool);
  }

  public String getDebugInfo()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append('"');
    try
    {
      if (this.hex)
        appendHexadecimalCharacterReferenceString(localStringBuilder, this.codePoint);
      else
        appendDecimalCharacterReferenceString(localStringBuilder, this.codePoint);
      localStringBuilder.append("\" ");
      appendUnicodeText(localStringBuilder, this.codePoint);
    }
    catch (IOException localIOException)
    {
      throw new RuntimeException(localIOException);
    }
    localStringBuilder.append(' ').append(super.getDebugInfo());
    return localStringBuilder.toString();
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.NumericCharacterReference
 * JD-Core Version:    0.6.2
 */