package net.htmlparser.jericho;

import org.apache.commons.logging.LogFactory;

final class LoggerFactory
{
  private static LoggerProvider defaultLoggerProvider = null;

  public static Logger getLogger(String paramString)
  {
    return getLoggerProvider().getLogger(paramString);
  }

  public static Logger getLogger(Class paramClass)
  {
    return getLogger(paramClass.getName());
  }

  public static LoggerProvider getLoggerProvider()
  {
    return Config.LoggerProvider != null ? Config.LoggerProvider : getDefaultLoggerProvider();
  }

  private static LoggerProvider getDefaultLoggerProvider()
  {
    if (defaultLoggerProvider == null)
      defaultLoggerProvider = determineDefaultLoggerProvider();
    return defaultLoggerProvider;
  }

  private static LoggerProvider determineDefaultLoggerProvider()
  {
    if (isClassAvailable("org.slf4j.impl.StaticLoggerBinder"))
    {
      if (isClassAvailable("org.slf4j.impl.JDK14LoggerFactory"))
        return LoggerProvider.JAVA;
      if (isClassAvailable("org.slf4j.impl.Log4jLoggerFactory"))
        return LoggerProvider.LOG4J;
      if (!isClassAvailable("org.slf4j.impl.JCLLoggerFactory"))
        return LoggerProvider.SLF4J;
    }
    if (isClassAvailable("org.apache.commons.logging.Log"))
    {
      String str = LogFactory.getLog("test").getClass().getName();
      if (str.equals("org.apache.commons.logging.impl.Jdk14Logger"))
        return LoggerProvider.JAVA;
      if (str.equals("org.apache.commons.logging.impl.Log4JLogger"))
        return LoggerProvider.LOG4J;
      return LoggerProvider.JCL;
    }
    if (isClassAvailable("org.apache.log4j.Logger"))
      return LoggerProvider.LOG4J;
    return LoggerProvider.JAVA;
  }

  private static boolean isClassAvailable(String paramString)
  {
    try
    {
      Class.forName(paramString);
      return true;
    }
    catch (Throwable localThrowable)
    {
    }
    return false;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.LoggerFactory
 * JD-Core Version:    0.6.2
 */