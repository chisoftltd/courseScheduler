package net.htmlparser.jericho;

import org.slf4j.LoggerFactory;

final class LoggerProviderSLF4J
  implements LoggerProvider
{
  public static final LoggerProvider INSTANCE = new LoggerProviderSLF4J();

  public Logger getLogger(String paramString)
  {
    return new SLF4JLogger(LoggerFactory.getLogger(paramString));
  }

  private static class SLF4JLogger
    implements Logger
  {
    private final org.slf4j.Logger slf4jLogger;

    public SLF4JLogger(org.slf4j.Logger paramLogger)
    {
      this.slf4jLogger = paramLogger;
    }

    public void error(String paramString)
    {
      this.slf4jLogger.error(paramString);
    }

    public void warn(String paramString)
    {
      this.slf4jLogger.warn(paramString);
    }

    public void info(String paramString)
    {
      this.slf4jLogger.info(paramString);
    }

    public void debug(String paramString)
    {
      this.slf4jLogger.debug(paramString);
    }

    public boolean isErrorEnabled()
    {
      return this.slf4jLogger.isErrorEnabled();
    }

    public boolean isWarnEnabled()
    {
      return this.slf4jLogger.isWarnEnabled();
    }

    public boolean isInfoEnabled()
    {
      return this.slf4jLogger.isInfoEnabled();
    }

    public boolean isDebugEnabled()
    {
      return this.slf4jLogger.isDebugEnabled();
    }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.LoggerProviderSLF4J
 * JD-Core Version:    0.6.2
 */