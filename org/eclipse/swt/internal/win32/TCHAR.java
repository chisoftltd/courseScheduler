package org.eclipse.swt.internal.win32;

public class TCHAR
{
  int codePage;
  public char[] chars;
  public byte[] bytes;
  int byteCount;
  public static final int sizeof = OS.IsUnicode ? 2 : 1;

  public TCHAR(int paramInt1, int paramInt2)
  {
    this.codePage = paramInt1;
    if (OS.IsUnicode)
      this.chars = new char[paramInt2];
    else
      this.bytes = new byte[this.byteCount = paramInt2];
  }

  public TCHAR(int paramInt, char paramChar, boolean paramBoolean)
  {
    this(paramInt, new char[] { paramBoolean ? new char[] { paramChar } : paramChar }, false);
  }

  public TCHAR(int paramInt, char[] paramArrayOfChar, boolean paramBoolean)
  {
    this.codePage = paramInt;
    int i = paramArrayOfChar.length;
    if (OS.IsUnicode)
    {
      if ((paramBoolean) && ((i == 0) || ((i > 0) && (paramArrayOfChar[(i - 1)] != 0))))
      {
        char[] arrayOfChar = new char[i + 1];
        System.arraycopy(paramArrayOfChar, 0, arrayOfChar, 0, i);
        paramArrayOfChar = arrayOfChar;
      }
      this.chars = paramArrayOfChar;
    }
    else
    {
      int j = paramInt != 0 ? paramInt : 0;
      this.bytes = new byte[this.byteCount = i * 2 + (paramBoolean ? 1 : 0)];
      this.byteCount = OS.WideCharToMultiByte(j, 0, paramArrayOfChar, i, this.bytes, this.byteCount, null, null);
      if (paramBoolean)
        this.byteCount += 1;
    }
  }

  public TCHAR(int paramInt, String paramString, boolean paramBoolean)
  {
    this(paramInt, getChars(paramString, paramBoolean), false);
  }

  static char[] getChars(String paramString, boolean paramBoolean)
  {
    int i = paramString.length();
    char[] arrayOfChar = new char[i + (paramBoolean ? 1 : 0)];
    paramString.getChars(0, i, arrayOfChar, 0);
    return arrayOfChar;
  }

  public int length()
  {
    if (OS.IsUnicode)
      return this.chars.length;
    return this.byteCount;
  }

  public int strlen()
  {
    if (OS.IsUnicode)
    {
      for (i = 0; i < this.chars.length; i++)
        if (this.chars[i] == 0)
          return i;
      return this.chars.length;
    }
    for (int i = 0; i < this.byteCount; i++)
      if (this.bytes[i] == 0)
        return i;
    return this.byteCount;
  }

  public int tcharAt(int paramInt)
  {
    if (OS.IsUnicode)
      return this.chars[paramInt];
    int i = this.bytes[paramInt] & 0xFF;
    if (OS.IsDBCSLeadByte((byte)i))
      i = i << 8 | this.bytes[(paramInt + 1)] & 0xFF;
    return i;
  }

  public String toString()
  {
    return toString(0, length());
  }

  public String toString(int paramInt1, int paramInt2)
  {
    if (OS.IsUnicode)
      return new String(this.chars, paramInt1, paramInt2);
    byte[] arrayOfByte = this.bytes;
    if (paramInt1 != 0)
    {
      arrayOfByte = new byte[paramInt2];
      System.arraycopy(this.bytes, paramInt1, arrayOfByte, 0, paramInt2);
    }
    char[] arrayOfChar = new char[paramInt2];
    int i = this.codePage != 0 ? this.codePage : 0;
    int j = OS.MultiByteToWideChar(i, 1, arrayOfByte, paramInt2, arrayOfChar, paramInt2);
    return new String(arrayOfChar, 0, j);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.TCHAR
 * JD-Core Version:    0.6.2
 */