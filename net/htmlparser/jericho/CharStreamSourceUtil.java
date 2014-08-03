package net.htmlparser.jericho;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public final class CharStreamSourceUtil
{
  private static final int DEFAULT_ESTIMATED_MAXIMUM_OUTPUT_LENGTH = 2048;

  public static Reader getReader(CharStreamSource paramCharStreamSource)
  {
    return new StringReader(toString(paramCharStreamSource));
  }

  public static String toString(CharStreamSource paramCharStreamSource)
  {
    long l = paramCharStreamSource.getEstimatedMaximumOutputLength();
    if (l <= -1L)
      l = 2048L;
    StringBuilder localStringBuilder = new StringBuilder((int)l);
    try
    {
      paramCharStreamSource.appendTo(localStringBuilder);
    }
    catch (IOException localIOException)
    {
      throw new RuntimeException(localIOException);
    }
    return localStringBuilder.toString();
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.CharStreamSourceUtil
 * JD-Core Version:    0.6.2
 */