package org.eclipse.swt.dnd;

import org.eclipse.swt.internal.SWTEventListener;

public abstract interface DropTargetListener extends SWTEventListener
{
  public abstract void dragEnter(DropTargetEvent paramDropTargetEvent);

  public abstract void dragLeave(DropTargetEvent paramDropTargetEvent);

  public abstract void dragOperationChanged(DropTargetEvent paramDropTargetEvent);

  public abstract void dragOver(DropTargetEvent paramDropTargetEvent);

  public abstract void drop(DropTargetEvent paramDropTargetEvent);

  public abstract void dropAccept(DropTargetEvent paramDropTargetEvent);
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.dnd.DropTargetListener
 * JD-Core Version:    0.6.2
 */