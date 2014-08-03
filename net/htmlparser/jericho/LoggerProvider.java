package net.htmlparser.jericho;

public abstract interface LoggerProvider
{
  public static final LoggerProvider DISABLED = LoggerProviderDisabled.INSTANCE;
  public static final LoggerProvider STDERR = LoggerProviderSTDERR.INSTANCE;
  public static final LoggerProvider JAVA = LoggerProviderJava.INSTANCE;
  public static final LoggerProvider JCL = LoggerProviderJCL.INSTANCE;
  public static final LoggerProvider LOG4J = LoggerProviderLog4J.INSTANCE;
  public static final LoggerProvider SLF4J = LoggerProviderSLF4J.INSTANCE;

  public abstract Logger getLogger(String paramString);
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.LoggerProvider
 * JD-Core Version:    0.6.2
 */