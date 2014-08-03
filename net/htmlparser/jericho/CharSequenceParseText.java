package net.htmlparser.jericho;

class CharSequenceParseText
  implements ParseText
{
  private final CharSequence charSequence;

  CharSequenceParseText(CharSequence paramCharSequence)
  {
    this.charSequence = paramCharSequence;
  }

  public final char charAt(int paramInt)
  {
    char c = this.charSequence.charAt(paramInt);
    return (c >= 'A') && (c <= 'Z') ? (char)(c ^ 0x20) : c;
  }

  public final boolean containsAt(String paramString, int paramInt)
  {
    for (int i = 0; i < paramString.length(); i++)
      if (paramString.charAt(i) != charAt(paramInt + i))
        return false;
    return true;
  }

  public final int indexOf(char paramChar, int paramInt)
  {
    return indexOf(paramChar, paramInt, -1);
  }

  public final int indexOf(char paramChar, int paramInt1, int paramInt2)
  {
    int i = (paramInt2 == -1) || (paramInt2 > getEnd()) ? getEnd() : paramInt2;
    try
    {
      for (int j = paramInt1 < 0 ? 0 : paramInt1; j < i; j++)
        if (charAt(j) == paramChar)
          return j;
    }
    catch (IndexOutOfBoundsException localIndexOutOfBoundsException)
    {
    }
    return -1;
  }

  public final int indexOf(String paramString, int paramInt)
  {
    return indexOf(paramString, paramInt, -1);
  }

  public final int indexOf(String paramString, int paramInt1, int paramInt2)
  {
    if (paramString.length() == 1)
      return indexOf(paramString.charAt(0), paramInt1, paramInt2);
    if (paramString.length() == 0)
      return paramInt1;
    int i = paramString.charAt(0);
    int j = getEnd() - paramString.length() + 1;
    int k = (paramInt2 == -1) || (paramInt2 > j) ? j : paramInt2;
    label139: for (int m = paramInt1 < 0 ? 0 : paramInt1; m < k; m++)
      if (charAt(m) == i)
      {
        for (int n = 1; n < paramString.length(); n++)
          if (paramString.charAt(n) != charAt(n + m))
            break label139;
        return m;
      }
    return -1;
  }

  public final int lastIndexOf(char paramChar, int paramInt)
  {
    return lastIndexOf(paramChar, paramInt, -1);
  }

  public final int lastIndexOf(char paramChar, int paramInt1, int paramInt2)
  {
    for (int i = paramInt1 > getEnd() ? getEnd() : paramInt1; i > paramInt2; i--)
      if (charAt(i) == paramChar)
        return i;
    return -1;
  }

  public final int lastIndexOf(String paramString, int paramInt)
  {
    return lastIndexOf(paramString, paramInt, -1);
  }

  public final int lastIndexOf(String paramString, int paramInt1, int paramInt2)
  {
    if (paramString.length() == 1)
      return lastIndexOf(paramString.charAt(0), paramInt1, paramInt2);
    if (paramString.length() == 0)
      return paramInt1;
    int i = getEnd() - paramString.length();
    if (paramInt2 > i)
      return -1;
    if (paramInt1 > i)
      paramInt1 = i;
    int j = paramString.length() - 1;
    int k = paramString.charAt(j);
    int m = paramInt2 + j;
    label151: for (int n = paramInt1 + j; n > m; n--)
      if (charAt(n) == k)
      {
        int i1 = n - j;
        for (int i2 = j - 1; i2 >= 0; i2--)
          if (paramString.charAt(i2) != charAt(i2 + i1))
            break label151;
        return i1;
      }
    return -1;
  }

  public final int length()
  {
    return this.charSequence.length();
  }

  public final CharSequence subSequence(int paramInt1, int paramInt2)
  {
    return substring(paramInt1, paramInt2);
  }

  public final String toString()
  {
    return this.charSequence.toString();
  }

  protected int getEnd()
  {
    return this.charSequence.length();
  }

  protected String substring(int paramInt1, int paramInt2)
  {
    return this.charSequence.subSequence(paramInt1, paramInt2).toString().toLowerCase();
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.CharSequenceParseText
 * JD-Core Version:    0.6.2
 */