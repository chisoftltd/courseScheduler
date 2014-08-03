package net.htmlparser.jericho;

import java.io.IOException;
import java.io.Writer;

public class WriterLogger
  implements Logger
{
  private final Writer writer;
  private final String name;
  private boolean errorEnabled = true;
  private boolean warnEnabled = true;
  private boolean infoEnabled = true;
  private boolean debugEnabled = true;

  public WriterLogger(Writer paramWriter)
  {
    this(paramWriter, Source.PACKAGE_NAME);
  }

  public WriterLogger(Writer paramWriter, String paramString)
  {
    this.writer = paramWriter;
    this.name = paramString;
  }

  public Writer getWriter()
  {
    return this.writer;
  }

  public String getName()
  {
    return this.name;
  }

  public void error(String paramString)
  {
    if (isErrorEnabled())
      log("ERROR", paramString);
  }

  public void warn(String paramString)
  {
    if (isWarnEnabled())
      log("WARN", paramString);
  }

  public void info(String paramString)
  {
    if (isInfoEnabled())
      log("INFO", paramString);
  }

  public void debug(String paramString)
  {
    if (isDebugEnabled())
      log("DEBUG", paramString);
  }

  public boolean isErrorEnabled()
  {
    return this.errorEnabled;
  }

  public void setErrorEnabled(boolean paramBoolean)
  {
    this.errorEnabled = paramBoolean;
  }

  public boolean isWarnEnabled()
  {
    return this.warnEnabled;
  }

  public void setWarnEnabled(boolean paramBoolean)
  {
    this.warnEnabled = paramBoolean;
  }

  public boolean isInfoEnabled()
  {
    return this.infoEnabled;
  }

  public void setInfoEnabled(boolean paramBoolean)
  {
    this.infoEnabled = paramBoolean;
  }

  public boolean isDebugEnabled()
  {
    return this.debugEnabled;
  }

  public void setDebugEnabled(boolean paramBoolean)
  {
    this.debugEnabled = paramBoolean;
  }

  protected void log(String paramString1, String paramString2)
  {
    try
    {
      this.writer.write(BasicLogFormatter.format(paramString1, paramString2, this.name));
      this.writer.flush();
    }
    catch (IOException localIOException)
    {
      throw new RuntimeException(localIOException);
    }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.WriterLogger
 * JD-Core Version:    0.6.2
 */