package net.htmlparser.jericho;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public final class Util
{
  private static final int BUFFER_SIZE = 2048;
  private static final String CSVNewLine = System.getProperty("line.separator");

  public static String getString(Reader paramReader)
    throws IOException
  {
    if (paramReader == null)
      return "";
    try
    {
      char[] arrayOfChar = new char[2048];
      StringBuilder localStringBuilder = new StringBuilder();
      int i;
      while ((i = paramReader.read(arrayOfChar, 0, 2048)) != -1)
        localStringBuilder.append(arrayOfChar, 0, i);
      String str = localStringBuilder.toString();
      return str;
    }
    finally
    {
      paramReader.close();
    }
  }

  public static void outputCSVLine(Writer paramWriter, String[] paramArrayOfString)
    throws IOException
  {
    int i = 0;
    while (i < paramArrayOfString.length)
    {
      String str = paramArrayOfString[i];
      if (str != null)
        if ((str == Config.ColumnValueTrue) || (str == Config.ColumnValueFalse))
        {
          paramWriter.write(str);
        }
        else
        {
          paramWriter.write(34);
          outputValueEscapeQuotes(paramWriter, str);
          paramWriter.write(34);
        }
      i++;
      if (i != paramArrayOfString.length)
        paramWriter.write(44);
    }
    paramWriter.write(CSVNewLine);
  }

  private static void outputValueEscapeQuotes(Writer paramWriter, String paramString)
    throws IOException
  {
    for (int i = 0; i < paramString.length(); i++)
    {
      int j = paramString.charAt(i);
      paramWriter.write(j);
      if (j == 34)
        paramWriter.write(j);
    }
  }

  static char[] getConcatenatedCharArray(String paramString1, String paramString2)
  {
    char[] arrayOfChar = new char[paramString1.length() + paramString2.length()];
    paramString1.getChars(0, paramString1.length(), arrayOfChar, 0);
    paramString2.getChars(0, paramString2.length(), arrayOfChar, paramString1.length());
    return arrayOfChar;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.Util
 * JD-Core Version:    0.6.2
 */