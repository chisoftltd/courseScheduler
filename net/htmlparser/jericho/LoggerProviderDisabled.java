package net.htmlparser.jericho;

final class LoggerProviderDisabled
  implements LoggerProvider
{
  public static final LoggerProvider INSTANCE = new LoggerProviderDisabled();

  public Logger getLogger(String paramString)
  {
    return null;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.LoggerProviderDisabled
 * JD-Core Version:    0.6.2
 */