package org.eclipse.swt.widgets;

import org.eclipse.swt.events.ArmEvent;
import org.eclipse.swt.events.ArmListener;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.DragDetectEvent;
import org.eclipse.swt.events.DragDetectListener;
import org.eclipse.swt.events.ExpandEvent;
import org.eclipse.swt.events.ExpandListener;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.HelpEvent;
import org.eclipse.swt.events.HelpListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.MenuListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.events.TreeEvent;
import org.eclipse.swt.events.TreeListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.internal.SWTEventListener;

public class TypedListener
  implements Listener
{
  protected SWTEventListener eventListener;

  public TypedListener(SWTEventListener paramSWTEventListener)
  {
    this.eventListener = paramSWTEventListener;
  }

  public SWTEventListener getEventListener()
  {
    return this.eventListener;
  }

  public void handleEvent(Event paramEvent)
  {
    Object localObject;
    switch (paramEvent.type)
    {
    case 26:
      ((ShellListener)this.eventListener).shellActivated(new ShellEvent(paramEvent));
      break;
    case 30:
      ((ArmListener)this.eventListener).widgetArmed(new ArmEvent(paramEvent));
      break;
    case 21:
      localObject = new ShellEvent(paramEvent);
      ((ShellListener)this.eventListener).shellClosed((ShellEvent)localObject);
      paramEvent.doit = ((ShellEvent)localObject).doit;
      break;
    case 18:
      if ((this.eventListener instanceof TreeListener))
        ((TreeListener)this.eventListener).treeCollapsed(new TreeEvent(paramEvent));
      else
        ((ExpandListener)this.eventListener).itemCollapsed(new ExpandEvent(paramEvent));
      break;
    case 27:
      ((ShellListener)this.eventListener).shellDeactivated(new ShellEvent(paramEvent));
      break;
    case 20:
      ((ShellListener)this.eventListener).shellDeiconified(new ShellEvent(paramEvent));
      break;
    case 14:
      ((SelectionListener)this.eventListener).widgetDefaultSelected(new SelectionEvent(paramEvent));
      break;
    case 12:
      ((DisposeListener)this.eventListener).widgetDisposed(new DisposeEvent(paramEvent));
      break;
    case 29:
      ((DragDetectListener)this.eventListener).dragDetected(new DragDetectEvent(paramEvent));
      break;
    case 17:
      if ((this.eventListener instanceof TreeListener))
        ((TreeListener)this.eventListener).treeExpanded(new TreeEvent(paramEvent));
      else
        ((ExpandListener)this.eventListener).itemExpanded(new ExpandEvent(paramEvent));
      break;
    case 15:
      ((FocusListener)this.eventListener).focusGained(new FocusEvent(paramEvent));
      break;
    case 16:
      ((FocusListener)this.eventListener).focusLost(new FocusEvent(paramEvent));
      break;
    case 28:
      ((HelpListener)this.eventListener).helpRequested(new HelpEvent(paramEvent));
      break;
    case 23:
      ((MenuListener)this.eventListener).menuHidden(new MenuEvent(paramEvent));
      break;
    case 19:
      ((ShellListener)this.eventListener).shellIconified(new ShellEvent(paramEvent));
      break;
    case 1:
      localObject = new KeyEvent(paramEvent);
      ((KeyListener)this.eventListener).keyPressed((KeyEvent)localObject);
      paramEvent.doit = ((KeyEvent)localObject).doit;
      break;
    case 2:
      localObject = new KeyEvent(paramEvent);
      ((KeyListener)this.eventListener).keyReleased((KeyEvent)localObject);
      paramEvent.doit = ((KeyEvent)localObject).doit;
      break;
    case 24:
      ((ModifyListener)this.eventListener).modifyText(new ModifyEvent(paramEvent));
      break;
    case 35:
      localObject = new MenuDetectEvent(paramEvent);
      ((MenuDetectListener)this.eventListener).menuDetected((MenuDetectEvent)localObject);
      paramEvent.x = ((MenuDetectEvent)localObject).x;
      paramEvent.y = ((MenuDetectEvent)localObject).y;
      paramEvent.doit = ((MenuDetectEvent)localObject).doit;
      break;
    case 3:
      ((MouseListener)this.eventListener).mouseDown(new MouseEvent(paramEvent));
      break;
    case 8:
      ((MouseListener)this.eventListener).mouseDoubleClick(new MouseEvent(paramEvent));
      break;
    case 6:
      ((MouseTrackListener)this.eventListener).mouseEnter(new MouseEvent(paramEvent));
      break;
    case 7:
      ((MouseTrackListener)this.eventListener).mouseExit(new MouseEvent(paramEvent));
      break;
    case 32:
      ((MouseTrackListener)this.eventListener).mouseHover(new MouseEvent(paramEvent));
      break;
    case 5:
      ((MouseMoveListener)this.eventListener).mouseMove(new MouseEvent(paramEvent));
      return;
    case 37:
      ((MouseWheelListener)this.eventListener).mouseScrolled(new MouseEvent(paramEvent));
      return;
    case 4:
      ((MouseListener)this.eventListener).mouseUp(new MouseEvent(paramEvent));
      break;
    case 10:
      ((ControlListener)this.eventListener).controlMoved(new ControlEvent(paramEvent));
      break;
    case 9:
      localObject = new PaintEvent(paramEvent);
      ((PaintListener)this.eventListener).paintControl((PaintEvent)localObject);
      paramEvent.gc = ((PaintEvent)localObject).gc;
      break;
    case 11:
      ((ControlListener)this.eventListener).controlResized(new ControlEvent(paramEvent));
      break;
    case 13:
      localObject = new SelectionEvent(paramEvent);
      ((SelectionListener)this.eventListener).widgetSelected((SelectionEvent)localObject);
      paramEvent.x = ((SelectionEvent)localObject).x;
      paramEvent.y = ((SelectionEvent)localObject).y;
      paramEvent.doit = ((SelectionEvent)localObject).doit;
      break;
    case 22:
      ((MenuListener)this.eventListener).menuShown(new MenuEvent(paramEvent));
      break;
    case 31:
      localObject = new TraverseEvent(paramEvent);
      ((TraverseListener)this.eventListener).keyTraversed((TraverseEvent)localObject);
      paramEvent.detail = ((TraverseEvent)localObject).detail;
      paramEvent.doit = ((TraverseEvent)localObject).doit;
      break;
    case 25:
      localObject = new VerifyEvent(paramEvent);
      ((VerifyListener)this.eventListener).verifyText((VerifyEvent)localObject);
      paramEvent.text = ((VerifyEvent)localObject).text;
      paramEvent.doit = ((VerifyEvent)localObject).doit;
    case 33:
    case 34:
    case 36:
    }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.TypedListener
 * JD-Core Version:    0.6.2
 */