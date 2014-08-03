package net.htmlparser.jericho;

public abstract interface Logger
{
  public abstract void error(String paramString);

  public abstract void warn(String paramString);

  public abstract void info(String paramString);

  public abstract void debug(String paramString);

  public abstract boolean isErrorEnabled();

  public abstract boolean isWarnEnabled();

  public abstract boolean isInfoEnabled();

  public abstract boolean isDebugEnabled();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.Logger
 * JD-Core Version:    0.6.2
 */