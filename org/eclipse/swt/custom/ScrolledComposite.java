package org.eclipse.swt.custom;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Shell;

public class ScrolledComposite extends Composite
{
  Control content;
  Listener contentListener;
  Listener filter;
  int minHeight = 0;
  int minWidth = 0;
  boolean expandHorizontal = false;
  boolean expandVertical = false;
  boolean alwaysShowScroll = false;
  boolean showFocusedControl = false;

  public ScrolledComposite(Composite paramComposite, int paramInt)
  {
    super(paramComposite, checkStyle(paramInt));
    super.setLayout(new ScrolledCompositeLayout());
    ScrollBar localScrollBar1 = getHorizontalBar();
    if (localScrollBar1 != null)
    {
      localScrollBar1.setVisible(false);
      localScrollBar1.addListener(13, new Listener()
      {
        public void handleEvent(Event paramAnonymousEvent)
        {
          ScrolledComposite.this.hScroll();
        }
      });
    }
    ScrollBar localScrollBar2 = getVerticalBar();
    if (localScrollBar2 != null)
    {
      localScrollBar2.setVisible(false);
      localScrollBar2.addListener(13, new Listener()
      {
        public void handleEvent(Event paramAnonymousEvent)
        {
          ScrolledComposite.this.vScroll();
        }
      });
    }
    this.contentListener = new Listener()
    {
      public void handleEvent(Event paramAnonymousEvent)
      {
        if (paramAnonymousEvent.type != 11)
          return;
        ScrolledComposite.this.layout(false);
      }
    };
    this.filter = new Listener()
    {
      public void handleEvent(Event paramAnonymousEvent)
      {
        if ((paramAnonymousEvent.widget instanceof Control))
        {
          Control localControl = (Control)paramAnonymousEvent.widget;
          if (ScrolledComposite.this.contains(localControl))
            ScrolledComposite.this.showControl(localControl);
        }
      }
    };
    addDisposeListener(new DisposeListener()
    {
      public void widgetDisposed(DisposeEvent paramAnonymousDisposeEvent)
      {
        ScrolledComposite.this.getDisplay().removeFilter(15, ScrolledComposite.this.filter);
      }
    });
  }

  static int checkStyle(int paramInt)
  {
    int i = 100666112;
    return paramInt & i;
  }

  boolean contains(Control paramControl)
  {
    if ((paramControl == null) || (paramControl.isDisposed()))
      return false;
    for (Composite localComposite = paramControl.getParent(); (localComposite != null) && (!(localComposite instanceof Shell)); localComposite = localComposite.getParent())
      if (this == localComposite)
        return true;
    return false;
  }

  public boolean getAlwaysShowScrollBars()
  {
    return this.alwaysShowScroll;
  }

  public boolean getExpandHorizontal()
  {
    checkWidget();
    return this.expandHorizontal;
  }

  public boolean getExpandVertical()
  {
    checkWidget();
    return this.expandVertical;
  }

  public int getMinWidth()
  {
    checkWidget();
    return this.minWidth;
  }

  public int getMinHeight()
  {
    checkWidget();
    return this.minHeight;
  }

  public Control getContent()
  {
    return this.content;
  }

  public boolean getShowFocusedControl()
  {
    checkWidget();
    return this.showFocusedControl;
  }

  void hScroll()
  {
    if (this.content == null)
      return;
    Point localPoint = this.content.getLocation();
    ScrollBar localScrollBar = getHorizontalBar();
    int i = localScrollBar.getSelection();
    this.content.setLocation(-i, localPoint.y);
  }

  boolean needHScroll(Rectangle paramRectangle, boolean paramBoolean)
  {
    ScrollBar localScrollBar1 = getHorizontalBar();
    if (localScrollBar1 == null)
      return false;
    Rectangle localRectangle = getBounds();
    int i = getBorderWidth();
    localRectangle.width -= 2 * i;
    ScrollBar localScrollBar2 = getVerticalBar();
    if ((paramBoolean) && (localScrollBar2 != null))
      localRectangle.width -= localScrollBar2.getSize().x;
    if ((!this.expandHorizontal) && (paramRectangle.width > localRectangle.width))
      return true;
    return (this.expandHorizontal) && (this.minWidth > localRectangle.width);
  }

  boolean needVScroll(Rectangle paramRectangle, boolean paramBoolean)
  {
    ScrollBar localScrollBar1 = getVerticalBar();
    if (localScrollBar1 == null)
      return false;
    Rectangle localRectangle = getBounds();
    int i = getBorderWidth();
    localRectangle.height -= 2 * i;
    ScrollBar localScrollBar2 = getHorizontalBar();
    if ((paramBoolean) && (localScrollBar2 != null))
      localRectangle.height -= localScrollBar2.getSize().y;
    if ((!this.expandVertical) && (paramRectangle.height > localRectangle.height))
      return true;
    return (this.expandVertical) && (this.minHeight > localRectangle.height);
  }

  public Point getOrigin()
  {
    checkWidget();
    if (this.content == null)
      return new Point(0, 0);
    Point localPoint = this.content.getLocation();
    return new Point(-localPoint.x, -localPoint.y);
  }

  public void setOrigin(Point paramPoint)
  {
    setOrigin(paramPoint.x, paramPoint.y);
  }

  public void setOrigin(int paramInt1, int paramInt2)
  {
    checkWidget();
    if (this.content == null)
      return;
    ScrollBar localScrollBar1 = getHorizontalBar();
    if (localScrollBar1 != null)
    {
      localScrollBar1.setSelection(paramInt1);
      paramInt1 = -localScrollBar1.getSelection();
    }
    else
    {
      paramInt1 = 0;
    }
    ScrollBar localScrollBar2 = getVerticalBar();
    if (localScrollBar2 != null)
    {
      localScrollBar2.setSelection(paramInt2);
      paramInt2 = -localScrollBar2.getSelection();
    }
    else
    {
      paramInt2 = 0;
    }
    this.content.setLocation(paramInt1, paramInt2);
  }

  public void setAlwaysShowScrollBars(boolean paramBoolean)
  {
    checkWidget();
    if (paramBoolean == this.alwaysShowScroll)
      return;
    this.alwaysShowScroll = paramBoolean;
    ScrollBar localScrollBar1 = getHorizontalBar();
    if ((localScrollBar1 != null) && (this.alwaysShowScroll))
      localScrollBar1.setVisible(true);
    ScrollBar localScrollBar2 = getVerticalBar();
    if ((localScrollBar2 != null) && (this.alwaysShowScroll))
      localScrollBar2.setVisible(true);
    layout(false);
  }

  public void setContent(Control paramControl)
  {
    checkWidget();
    if ((this.content != null) && (!this.content.isDisposed()))
    {
      this.content.removeListener(11, this.contentListener);
      this.content.setBounds(new Rectangle(-200, -200, 0, 0));
    }
    this.content = paramControl;
    ScrollBar localScrollBar1 = getVerticalBar();
    ScrollBar localScrollBar2 = getHorizontalBar();
    if (this.content != null)
    {
      if (localScrollBar1 != null)
      {
        localScrollBar1.setMaximum(0);
        localScrollBar1.setThumb(0);
        localScrollBar1.setSelection(0);
      }
      if (localScrollBar2 != null)
      {
        localScrollBar2.setMaximum(0);
        localScrollBar2.setThumb(0);
        localScrollBar2.setSelection(0);
      }
      paramControl.setLocation(0, 0);
      layout(false);
      this.content.addListener(11, this.contentListener);
    }
    else
    {
      if (localScrollBar2 != null)
        localScrollBar2.setVisible(this.alwaysShowScroll);
      if (localScrollBar1 != null)
        localScrollBar1.setVisible(this.alwaysShowScroll);
    }
  }

  public void setExpandHorizontal(boolean paramBoolean)
  {
    checkWidget();
    if (paramBoolean == this.expandHorizontal)
      return;
    this.expandHorizontal = paramBoolean;
    layout(false);
  }

  public void setExpandVertical(boolean paramBoolean)
  {
    checkWidget();
    if (paramBoolean == this.expandVertical)
      return;
    this.expandVertical = paramBoolean;
    layout(false);
  }

  public void setLayout(Layout paramLayout)
  {
    checkWidget();
  }

  public void setMinHeight(int paramInt)
  {
    setMinSize(this.minWidth, paramInt);
  }

  public void setMinSize(Point paramPoint)
  {
    if (paramPoint == null)
      setMinSize(0, 0);
    else
      setMinSize(paramPoint.x, paramPoint.y);
  }

  public void setMinSize(int paramInt1, int paramInt2)
  {
    checkWidget();
    if ((paramInt1 == this.minWidth) && (paramInt2 == this.minHeight))
      return;
    this.minWidth = Math.max(0, paramInt1);
    this.minHeight = Math.max(0, paramInt2);
    layout(false);
  }

  public void setMinWidth(int paramInt)
  {
    setMinSize(paramInt, this.minHeight);
  }

  public void setShowFocusedControl(boolean paramBoolean)
  {
    checkWidget();
    if (this.showFocusedControl == paramBoolean)
      return;
    Display localDisplay = getDisplay();
    localDisplay.removeFilter(15, this.filter);
    this.showFocusedControl = paramBoolean;
    if (!this.showFocusedControl)
      return;
    localDisplay.addFilter(15, this.filter);
    Control localControl = localDisplay.getFocusControl();
    if (contains(localControl))
      showControl(localControl);
  }

  public void showControl(Control paramControl)
  {
    checkWidget();
    if (paramControl == null)
      SWT.error(4);
    if (paramControl.isDisposed())
      SWT.error(5);
    if (!contains(paramControl))
      SWT.error(5);
    Rectangle localRectangle1 = getDisplay().map(paramControl.getParent(), this, paramControl.getBounds());
    Rectangle localRectangle2 = getClientArea();
    Point localPoint = getOrigin();
    if (localRectangle1.x < 0)
      localPoint.x = Math.max(0, localPoint.x + localRectangle1.x);
    else if (localRectangle2.width < localRectangle1.x + localRectangle1.width)
      localPoint.x = Math.max(0, localPoint.x + localRectangle1.x + Math.min(localRectangle1.width, localRectangle2.width) - localRectangle2.width);
    if (localRectangle1.y < 0)
      localPoint.y = Math.max(0, localPoint.y + localRectangle1.y);
    else if (localRectangle2.height < localRectangle1.y + localRectangle1.height)
      localPoint.y = Math.max(0, localPoint.y + localRectangle1.y + Math.min(localRectangle1.height, localRectangle2.height) - localRectangle2.height);
    setOrigin(localPoint);
  }

  void vScroll()
  {
    if (this.content == null)
      return;
    Point localPoint = this.content.getLocation();
    ScrollBar localScrollBar = getVerticalBar();
    int i = localScrollBar.getSelection();
    this.content.setLocation(localPoint.x, -i);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.custom.ScrolledComposite
 * JD-Core Version:    0.6.2
 */