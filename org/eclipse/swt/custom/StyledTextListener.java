package org.eclipse.swt.custom;

import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.internal.SWTEventListener;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.TypedListener;

class StyledTextListener extends TypedListener
{
  StyledTextListener(SWTEventListener paramSWTEventListener)
  {
    super(paramSWTEventListener);
  }

  public void handleEvent(Event paramEvent)
  {
    Object localObject1;
    Object localObject2;
    switch (paramEvent.type)
    {
    case 3000:
      ExtendedModifyEvent localExtendedModifyEvent = new ExtendedModifyEvent((StyledTextEvent)paramEvent);
      ((ExtendedModifyListener)this.eventListener).modifyText(localExtendedModifyEvent);
      break;
    case 3001:
      LineBackgroundEvent localLineBackgroundEvent = new LineBackgroundEvent((StyledTextEvent)paramEvent);
      ((LineBackgroundListener)this.eventListener).lineGetBackground(localLineBackgroundEvent);
      ((StyledTextEvent)paramEvent).lineBackground = localLineBackgroundEvent.lineBackground;
      break;
    case 3007:
      BidiSegmentEvent localBidiSegmentEvent = new BidiSegmentEvent((StyledTextEvent)paramEvent);
      ((BidiSegmentListener)this.eventListener).lineGetSegments(localBidiSegmentEvent);
      ((StyledTextEvent)paramEvent).segments = localBidiSegmentEvent.segments;
      ((StyledTextEvent)paramEvent).segmentsChars = localBidiSegmentEvent.segmentsChars;
      break;
    case 3002:
      LineStyleEvent localLineStyleEvent = new LineStyleEvent((StyledTextEvent)paramEvent);
      ((LineStyleListener)this.eventListener).lineGetStyle(localLineStyleEvent);
      ((StyledTextEvent)paramEvent).ranges = localLineStyleEvent.ranges;
      ((StyledTextEvent)paramEvent).styles = localLineStyleEvent.styles;
      ((StyledTextEvent)paramEvent).alignment = localLineStyleEvent.alignment;
      ((StyledTextEvent)paramEvent).indent = localLineStyleEvent.indent;
      ((StyledTextEvent)paramEvent).wrapIndent = localLineStyleEvent.wrapIndent;
      ((StyledTextEvent)paramEvent).justify = localLineStyleEvent.justify;
      ((StyledTextEvent)paramEvent).bullet = localLineStyleEvent.bullet;
      ((StyledTextEvent)paramEvent).bulletIndex = localLineStyleEvent.bulletIndex;
      ((StyledTextEvent)paramEvent).tabStops = localLineStyleEvent.tabStops;
      break;
    case 3008:
      PaintObjectEvent localPaintObjectEvent = new PaintObjectEvent((StyledTextEvent)paramEvent);
      ((PaintObjectListener)this.eventListener).paintObject(localPaintObjectEvent);
      break;
    case 3005:
      VerifyEvent localVerifyEvent = new VerifyEvent(paramEvent);
      ((VerifyKeyListener)this.eventListener).verifyKey(localVerifyEvent);
      paramEvent.doit = localVerifyEvent.doit;
      break;
    case 3006:
      localObject1 = new TextChangedEvent((StyledTextContent)paramEvent.data);
      ((TextChangeListener)this.eventListener).textChanged((TextChangedEvent)localObject1);
      break;
    case 3003:
      localObject1 = new TextChangingEvent((StyledTextContent)paramEvent.data, (StyledTextEvent)paramEvent);
      ((TextChangeListener)this.eventListener).textChanging((TextChangingEvent)localObject1);
      break;
    case 3004:
      localObject2 = new TextChangedEvent((StyledTextContent)paramEvent.data);
      ((TextChangeListener)this.eventListener).textSet((TextChangedEvent)localObject2);
      break;
    case 3009:
      localObject2 = new MovementEvent((StyledTextEvent)paramEvent);
      ((MovementListener)this.eventListener).getNextOffset((MovementEvent)localObject2);
      ((StyledTextEvent)paramEvent).end = ((MovementEvent)localObject2).newOffset;
      break;
    case 3010:
      localObject2 = new MovementEvent((StyledTextEvent)paramEvent);
      ((MovementListener)this.eventListener).getPreviousOffset((MovementEvent)localObject2);
      ((StyledTextEvent)paramEvent).end = ((MovementEvent)localObject2).newOffset;
      break;
    case 3011:
      localObject2 = new CaretEvent((StyledTextEvent)paramEvent);
      ((CaretListener)this.eventListener).caretMoved((CaretEvent)localObject2);
      ((StyledTextEvent)paramEvent).end = ((CaretEvent)localObject2).caretOffset;
    }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.custom.StyledTextListener
 * JD-Core Version:    0.6.2
 */