package org.eclipse.swt.dnd;

import org.eclipse.swt.internal.SWTEventListener;

public abstract interface DragSourceListener extends SWTEventListener
{
  public abstract void dragStart(DragSourceEvent paramDragSourceEvent);

  public abstract void dragSetData(DragSourceEvent paramDragSourceEvent);

  public abstract void dragFinished(DragSourceEvent paramDragSourceEvent);
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.dnd.DragSourceListener
 * JD-Core Version:    0.6.2
 */