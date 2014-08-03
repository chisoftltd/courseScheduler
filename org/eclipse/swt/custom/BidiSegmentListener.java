package org.eclipse.swt.custom;

import org.eclipse.swt.internal.SWTEventListener;

public abstract interface BidiSegmentListener extends SWTEventListener
{
  public abstract void lineGetSegments(BidiSegmentEvent paramBidiSegmentEvent);
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.custom.BidiSegmentListener
 * JD-Core Version:    0.6.2
 */