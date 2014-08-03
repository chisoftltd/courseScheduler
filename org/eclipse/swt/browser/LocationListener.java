package org.eclipse.swt.browser;

import org.eclipse.swt.internal.SWTEventListener;

public abstract interface LocationListener extends SWTEventListener
{
  public abstract void changing(LocationEvent paramLocationEvent);

  public abstract void changed(LocationEvent paramLocationEvent);
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.browser.LocationListener
 * JD-Core Version:    0.6.2
 */