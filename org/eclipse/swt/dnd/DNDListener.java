package org.eclipse.swt.dnd;

import org.eclipse.swt.internal.SWTEventListener;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.TypedListener;
import org.eclipse.swt.widgets.Widget;

class DNDListener extends TypedListener
{
  Widget dndWidget;

  DNDListener(SWTEventListener paramSWTEventListener)
  {
    super(paramSWTEventListener);
  }

  public void handleEvent(Event paramEvent)
  {
    Object localObject1;
    Object localObject2;
    switch (paramEvent.type)
    {
    case 2008:
      localObject1 = new DragSourceEvent((DNDEvent)paramEvent);
      localObject2 = ((DragSource)this.dndWidget).getDragSourceEffect();
      if (localObject2 != null)
        ((DragSourceEffect)localObject2).dragStart((DragSourceEvent)localObject1);
      ((DragSourceListener)this.eventListener).dragStart((DragSourceEvent)localObject1);
      ((DragSourceEvent)localObject1).updateEvent((DNDEvent)paramEvent);
      break;
    case 2000:
      localObject1 = new DragSourceEvent((DNDEvent)paramEvent);
      localObject2 = ((DragSource)this.dndWidget).getDragSourceEffect();
      if (localObject2 != null)
        ((DragSourceEffect)localObject2).dragFinished((DragSourceEvent)localObject1);
      ((DragSourceListener)this.eventListener).dragFinished((DragSourceEvent)localObject1);
      ((DragSourceEvent)localObject1).updateEvent((DNDEvent)paramEvent);
      break;
    case 2001:
      localObject1 = new DragSourceEvent((DNDEvent)paramEvent);
      localObject2 = ((DragSource)this.dndWidget).getDragSourceEffect();
      if (localObject2 != null)
        ((DragSourceEffect)localObject2).dragSetData((DragSourceEvent)localObject1);
      ((DragSourceListener)this.eventListener).dragSetData((DragSourceEvent)localObject1);
      ((DragSourceEvent)localObject1).updateEvent((DNDEvent)paramEvent);
      break;
    case 2002:
      localObject1 = new DropTargetEvent((DNDEvent)paramEvent);
      ((DropTargetListener)this.eventListener).dragEnter((DropTargetEvent)localObject1);
      localObject2 = ((DropTarget)this.dndWidget).getDropTargetEffect();
      if (localObject2 != null)
        ((DropTargetEffect)localObject2).dragEnter((DropTargetEvent)localObject1);
      ((DropTargetEvent)localObject1).updateEvent((DNDEvent)paramEvent);
      break;
    case 2003:
      localObject1 = new DropTargetEvent((DNDEvent)paramEvent);
      ((DropTargetListener)this.eventListener).dragLeave((DropTargetEvent)localObject1);
      localObject2 = ((DropTarget)this.dndWidget).getDropTargetEffect();
      if (localObject2 != null)
        ((DropTargetEffect)localObject2).dragLeave((DropTargetEvent)localObject1);
      ((DropTargetEvent)localObject1).updateEvent((DNDEvent)paramEvent);
      break;
    case 2004:
      localObject1 = new DropTargetEvent((DNDEvent)paramEvent);
      ((DropTargetListener)this.eventListener).dragOver((DropTargetEvent)localObject1);
      localObject2 = ((DropTarget)this.dndWidget).getDropTargetEffect();
      if (localObject2 != null)
        ((DropTargetEffect)localObject2).dragOver((DropTargetEvent)localObject1);
      ((DropTargetEvent)localObject1).updateEvent((DNDEvent)paramEvent);
      break;
    case 2006:
      localObject1 = new DropTargetEvent((DNDEvent)paramEvent);
      ((DropTargetListener)this.eventListener).drop((DropTargetEvent)localObject1);
      localObject2 = ((DropTarget)this.dndWidget).getDropTargetEffect();
      if (localObject2 != null)
        ((DropTargetEffect)localObject2).drop((DropTargetEvent)localObject1);
      ((DropTargetEvent)localObject1).updateEvent((DNDEvent)paramEvent);
      break;
    case 2007:
      localObject1 = new DropTargetEvent((DNDEvent)paramEvent);
      ((DropTargetListener)this.eventListener).dropAccept((DropTargetEvent)localObject1);
      localObject2 = ((DropTarget)this.dndWidget).getDropTargetEffect();
      if (localObject2 != null)
        ((DropTargetEffect)localObject2).dropAccept((DropTargetEvent)localObject1);
      ((DropTargetEvent)localObject1).updateEvent((DNDEvent)paramEvent);
      break;
    case 2005:
      localObject1 = new DropTargetEvent((DNDEvent)paramEvent);
      ((DropTargetListener)this.eventListener).dragOperationChanged((DropTargetEvent)localObject1);
      localObject2 = ((DropTarget)this.dndWidget).getDropTargetEffect();
      if (localObject2 != null)
        ((DropTargetEffect)localObject2).dragOperationChanged((DropTargetEvent)localObject1);
      ((DropTargetEvent)localObject1).updateEvent((DNDEvent)paramEvent);
    }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.dnd.DNDListener
 * JD-Core Version:    0.6.2
 */