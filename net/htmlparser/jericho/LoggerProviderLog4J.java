package net.htmlparser.jericho;

import org.apache.log4j.Level;

final class LoggerProviderLog4J
  implements LoggerProvider
{
  public static final LoggerProvider INSTANCE = new LoggerProviderLog4J();

  public Logger getLogger(String paramString)
  {
    return new Log4JLogger(org.apache.log4j.Logger.getLogger(paramString));
  }

  private static class Log4JLogger
    implements Logger
  {
    private final org.apache.log4j.Logger log4JLogger;

    public Log4JLogger(org.apache.log4j.Logger paramLogger)
    {
      this.log4JLogger = paramLogger;
    }

    public void error(String paramString)
    {
      this.log4JLogger.error(paramString);
    }

    public void warn(String paramString)
    {
      this.log4JLogger.warn(paramString);
    }

    public void info(String paramString)
    {
      this.log4JLogger.info(paramString);
    }

    public void debug(String paramString)
    {
      this.log4JLogger.debug(paramString);
    }

    public boolean isErrorEnabled()
    {
      return this.log4JLogger.isEnabledFor(Level.ERROR);
    }

    public boolean isWarnEnabled()
    {
      return this.log4JLogger.isEnabledFor(Level.WARN);
    }

    public boolean isInfoEnabled()
    {
      return this.log4JLogger.isEnabledFor(Level.INFO);
    }

    public boolean isDebugEnabled()
    {
      return this.log4JLogger.isEnabledFor(Level.DEBUG);
    }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.LoggerProviderLog4J
 * JD-Core Version:    0.6.2
 */