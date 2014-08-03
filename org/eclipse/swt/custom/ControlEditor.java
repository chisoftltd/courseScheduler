package org.eclipse.swt.custom;

import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;

public class ControlEditor
{
  public int horizontalAlignment = 16777216;
  public boolean grabHorizontal = false;
  public int minimumWidth = 0;
  public int verticalAlignment = 16777216;
  public boolean grabVertical = false;
  public int minimumHeight = 0;
  Composite parent;
  Control editor;
  private boolean hadFocus;
  private Listener controlListener;
  private Listener scrollbarListener;
  private static final int[] EVENTS = { 1, 2, 3, 4, 11 };

  public ControlEditor(Composite paramComposite)
  {
    this.parent = paramComposite;
    this.controlListener = new Listener()
    {
      public void handleEvent(Event paramAnonymousEvent)
      {
        ControlEditor.this.layout();
      }
    };
    for (int i = 0; i < EVENTS.length; i++)
      paramComposite.addListener(EVENTS[i], this.controlListener);
    this.scrollbarListener = new Listener()
    {
      public void handleEvent(Event paramAnonymousEvent)
      {
        ControlEditor.this.scroll(paramAnonymousEvent);
      }
    };
    ScrollBar localScrollBar1 = paramComposite.getHorizontalBar();
    if (localScrollBar1 != null)
      localScrollBar1.addListener(13, this.scrollbarListener);
    ScrollBar localScrollBar2 = paramComposite.getVerticalBar();
    if (localScrollBar2 != null)
      localScrollBar2.addListener(13, this.scrollbarListener);
  }

  Rectangle computeBounds()
  {
    Rectangle localRectangle1 = this.parent.getClientArea();
    Rectangle localRectangle2 = new Rectangle(localRectangle1.x, localRectangle1.y, this.minimumWidth, this.minimumHeight);
    if (this.grabHorizontal)
      localRectangle2.width = Math.max(localRectangle1.width, this.minimumWidth);
    if (this.grabVertical)
      localRectangle2.height = Math.max(localRectangle1.height, this.minimumHeight);
    switch (this.horizontalAlignment)
    {
    case 131072:
      localRectangle2.x += localRectangle1.width - localRectangle2.width;
      break;
    case 16384:
      break;
    default:
      localRectangle2.x += (localRectangle1.width - localRectangle2.width) / 2;
    }
    switch (this.verticalAlignment)
    {
    case 1024:
      localRectangle2.y += localRectangle1.height - localRectangle2.height;
      break;
    case 128:
      break;
    default:
      localRectangle2.y += (localRectangle1.height - localRectangle2.height) / 2;
    }
    return localRectangle2;
  }

  public void dispose()
  {
    if ((this.parent != null) && (!this.parent.isDisposed()))
    {
      for (int i = 0; i < EVENTS.length; i++)
        this.parent.removeListener(EVENTS[i], this.controlListener);
      ScrollBar localScrollBar1 = this.parent.getHorizontalBar();
      if (localScrollBar1 != null)
        localScrollBar1.removeListener(13, this.scrollbarListener);
      ScrollBar localScrollBar2 = this.parent.getVerticalBar();
      if (localScrollBar2 != null)
        localScrollBar2.removeListener(13, this.scrollbarListener);
    }
    this.parent = null;
    this.editor = null;
    this.hadFocus = false;
    this.controlListener = null;
    this.scrollbarListener = null;
  }

  public Control getEditor()
  {
    return this.editor;
  }

  public void layout()
  {
    if ((this.editor == null) || (this.editor.isDisposed()))
      return;
    if (this.editor.getVisible())
      this.hadFocus = this.editor.isFocusControl();
    this.editor.setBounds(computeBounds());
    if (this.hadFocus)
    {
      if ((this.editor == null) || (this.editor.isDisposed()))
        return;
      this.editor.setFocus();
    }
  }

  void scroll(Event paramEvent)
  {
    if ((this.editor == null) || (this.editor.isDisposed()))
      return;
    layout();
  }

  public void setEditor(Control paramControl)
  {
    if (paramControl == null)
    {
      this.editor = null;
      return;
    }
    this.editor = paramControl;
    layout();
    if ((this.editor == null) || (this.editor.isDisposed()))
      return;
    paramControl.setVisible(true);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.custom.ControlEditor
 * JD-Core Version:    0.6.2
 */