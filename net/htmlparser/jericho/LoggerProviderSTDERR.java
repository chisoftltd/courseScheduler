package net.htmlparser.jericho;

import java.io.OutputStreamWriter;

final class LoggerProviderSTDERR
  implements LoggerProvider
{
  public static final LoggerProvider INSTANCE = new LoggerProviderSTDERR();

  public Logger getLogger(String paramString)
  {
    return new WriterLogger(new OutputStreamWriter(System.err), paramString);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.LoggerProviderSTDERR
 * JD-Core Version:    0.6.2
 */