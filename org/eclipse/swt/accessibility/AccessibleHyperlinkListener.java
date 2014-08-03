package org.eclipse.swt.accessibility;

import org.eclipse.swt.internal.SWTEventListener;

public abstract interface AccessibleHyperlinkListener extends SWTEventListener
{
  public abstract void getAnchor(AccessibleHyperlinkEvent paramAccessibleHyperlinkEvent);

  public abstract void getAnchorTarget(AccessibleHyperlinkEvent paramAccessibleHyperlinkEvent);

  public abstract void getStartIndex(AccessibleHyperlinkEvent paramAccessibleHyperlinkEvent);

  public abstract void getEndIndex(AccessibleHyperlinkEvent paramAccessibleHyperlinkEvent);
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.accessibility.AccessibleHyperlinkListener
 * JD-Core Version:    0.6.2
 */