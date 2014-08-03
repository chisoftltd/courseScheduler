package net.htmlparser.jericho;

import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class BasicLogFormatter extends Formatter
{
  public static boolean OutputLevel = true;
  public static boolean OutputName = false;
  static final Formatter INSTANCE = new BasicLogFormatter();

  public String format(LogRecord paramLogRecord)
  {
    return format(paramLogRecord.getLevel().getName(), paramLogRecord.getMessage(), paramLogRecord.getLoggerName());
  }

  public static String format(String paramString1, String paramString2, String paramString3)
  {
    StringBuilder localStringBuilder = new StringBuilder(paramString2.length() + 40);
    if (OutputLevel)
      localStringBuilder.append(paramString1).append(": ");
    if ((OutputName) && (paramString3 != null))
      localStringBuilder.append('[').append(paramString3).append("] ");
    localStringBuilder.append(paramString2);
    localStringBuilder.append(Config.NewLine);
    return localStringBuilder.toString();
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.BasicLogFormatter
 * JD-Core Version:    0.6.2
 */