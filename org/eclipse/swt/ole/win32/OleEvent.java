package org.eclipse.swt.ole.win32;

import org.eclipse.swt.widgets.Widget;

public class OleEvent
{
  public int type;
  public Widget widget;
  public int detail;
  public boolean doit = true;
  public Variant[] arguments;
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.ole.win32.OleEvent
 * JD-Core Version:    0.6.2
 */