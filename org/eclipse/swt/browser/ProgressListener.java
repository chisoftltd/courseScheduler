package org.eclipse.swt.browser;

import org.eclipse.swt.internal.SWTEventListener;

public abstract interface ProgressListener extends SWTEventListener
{
  public abstract void changed(ProgressEvent paramProgressEvent);

  public abstract void completed(ProgressEvent paramProgressEvent);
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.browser.ProgressListener
 * JD-Core Version:    0.6.2
 */