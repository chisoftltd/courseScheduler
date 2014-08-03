package net.htmlparser.jericho;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

final class LoggerProviderJCL
  implements LoggerProvider
{
  public static final LoggerProvider INSTANCE = new LoggerProviderJCL();

  public Logger getLogger(String paramString)
  {
    return new JCLLogger(LogFactory.getLog(paramString));
  }

  private static class JCLLogger
    implements Logger
  {
    private final Log jclLog;

    public JCLLogger(Log paramLog)
    {
      this.jclLog = paramLog;
    }

    public void error(String paramString)
    {
      this.jclLog.error(paramString);
    }

    public void warn(String paramString)
    {
      this.jclLog.warn(paramString);
    }

    public void info(String paramString)
    {
      this.jclLog.info(paramString);
    }

    public void debug(String paramString)
    {
      this.jclLog.debug(paramString);
    }

    public boolean isErrorEnabled()
    {
      return this.jclLog.isErrorEnabled();
    }

    public boolean isWarnEnabled()
    {
      return this.jclLog.isWarnEnabled();
    }

    public boolean isInfoEnabled()
    {
      return this.jclLog.isInfoEnabled();
    }

    public boolean isDebugEnabled()
    {
      return this.jclLog.isDebugEnabled();
    }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.LoggerProviderJCL
 * JD-Core Version:    0.6.2
 */