package org.eclipse.swt.widgets;

import org.eclipse.swt.graphics.Point;

public abstract class Layout
{
  protected abstract Point computeSize(Composite paramComposite, int paramInt1, int paramInt2, boolean paramBoolean);

  protected boolean flushCache(Control paramControl)
  {
    return false;
  }

  protected abstract void layout(Composite paramComposite, boolean paramBoolean);
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.Layout
 * JD-Core Version:    0.6.2
 */