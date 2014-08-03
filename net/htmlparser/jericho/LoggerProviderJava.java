package net.htmlparser.jericho;

import java.util.logging.Level;

final class LoggerProviderJava
  implements LoggerProvider
{
  public static final LoggerProvider INSTANCE = new LoggerProviderJava();

  public Logger getLogger(String paramString)
  {
    return new JavaLogger(java.util.logging.Logger.getLogger(paramString));
  }

  private class JavaLogger
    implements Logger
  {
    private final java.util.logging.Logger javaLogger;

    public JavaLogger(java.util.logging.Logger arg2)
    {
      Object localObject;
      this.javaLogger = localObject;
    }

    public void error(String paramString)
    {
      this.javaLogger.severe(paramString);
    }

    public void warn(String paramString)
    {
      this.javaLogger.warning(paramString);
    }

    public void info(String paramString)
    {
      this.javaLogger.info(paramString);
    }

    public void debug(String paramString)
    {
      this.javaLogger.fine(paramString);
    }

    public boolean isErrorEnabled()
    {
      return this.javaLogger.isLoggable(Level.SEVERE);
    }

    public boolean isWarnEnabled()
    {
      return this.javaLogger.isLoggable(Level.WARNING);
    }

    public boolean isInfoEnabled()
    {
      return this.javaLogger.isLoggable(Level.INFO);
    }

    public boolean isDebugEnabled()
    {
      return this.javaLogger.isLoggable(Level.FINE);
    }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.LoggerProviderJava
 * JD-Core Version:    0.6.2
 */