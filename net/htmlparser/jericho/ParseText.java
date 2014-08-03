package net.htmlparser.jericho;

public abstract interface ParseText extends CharSequence
{
  public static final int NO_BREAK = -1;

  public abstract char charAt(int paramInt);

  public abstract boolean containsAt(String paramString, int paramInt);

  public abstract int indexOf(char paramChar, int paramInt);

  public abstract int indexOf(char paramChar, int paramInt1, int paramInt2);

  public abstract int indexOf(String paramString, int paramInt);

  public abstract int indexOf(String paramString, int paramInt1, int paramInt2);

  public abstract int lastIndexOf(char paramChar, int paramInt);

  public abstract int lastIndexOf(char paramChar, int paramInt1, int paramInt2);

  public abstract int lastIndexOf(String paramString, int paramInt);

  public abstract int lastIndexOf(String paramString, int paramInt1, int paramInt2);

  public abstract int length();

  public abstract CharSequence subSequence(int paramInt1, int paramInt2);

  public abstract String toString();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.ParseText
 * JD-Core Version:    0.6.2
 */