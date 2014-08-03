package org.eclipse.swt.dnd;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;

public class DragSourceEffect extends DragSourceAdapter
{
  Control control = null;

  public DragSourceEffect(Control paramControl)
  {
    if (paramControl == null)
      SWT.error(4);
    this.control = paramControl;
  }

  public Control getControl()
  {
    return this.control;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.dnd.DragSourceEffect
 * JD-Core Version:    0.6.2
 */