package net.htmlparser.jericho;

final class LoggerDisabled
  implements Logger
{
  public static final LoggerDisabled INSTANCE = new LoggerDisabled();

  public void error(String paramString)
  {
  }

  public void warn(String paramString)
  {
  }

  public void info(String paramString)
  {
  }

  public void debug(String paramString)
  {
  }

  public boolean isErrorEnabled()
  {
    return false;
  }

  public boolean isWarnEnabled()
  {
    return false;
  }

  public boolean isInfoEnabled()
  {
    return false;
  }

  public boolean isDebugEnabled()
  {
    return false;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.LoggerDisabled
 * JD-Core Version:    0.6.2
 */