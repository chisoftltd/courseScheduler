package org.eclipse.swt;

import java.io.PrintStream;
import org.eclipse.swt.internal.Library;

public class SWTError extends Error
{
  public int code;
  public Throwable throwable;
  static final long serialVersionUID = 3833467327105808433L;

  public SWTError()
  {
    this(1);
  }

  public SWTError(String paramString)
  {
    this(1, paramString);
  }

  public SWTError(int paramInt)
  {
    this(paramInt, SWT.findErrorText(paramInt));
  }

  public SWTError(int paramInt, String paramString)
  {
    super(paramString);
    this.code = paramInt;
  }

  public Throwable getCause()
  {
    return this.throwable;
  }

  public String getMessage()
  {
    if (this.throwable == null)
      return super.getMessage();
    return super.getMessage() + " (" + this.throwable.toString() + ")";
  }

  public void printStackTrace()
  {
    super.printStackTrace();
    if ((Library.JAVA_VERSION < Library.JAVA_VERSION(1, 4, 0)) && (this.throwable != null))
    {
      System.err.println("*** Stack trace of contained error ***");
      this.throwable.printStackTrace();
    }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.SWTError
 * JD-Core Version:    0.6.2
 */