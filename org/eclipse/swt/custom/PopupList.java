package org.eclipse.swt.custom;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class PopupList
{
  Shell shell;
  List list;
  int minimumWidth;

  public PopupList(Shell paramShell)
  {
    this(paramShell, 0);
  }

  public PopupList(Shell paramShell, int paramInt)
  {
    this.shell = new Shell(paramShell, checkStyle(paramInt));
    this.list = new List(this.shell, 516);
    this.shell.addListener(27, new Listener()
    {
      public void handleEvent(Event paramAnonymousEvent)
      {
        PopupList.this.shell.setVisible(false);
      }
    });
    this.shell.addControlListener(new ControlListener()
    {
      public void controlMoved(ControlEvent paramAnonymousControlEvent)
      {
      }

      public void controlResized(ControlEvent paramAnonymousControlEvent)
      {
        Rectangle localRectangle = PopupList.this.shell.getClientArea();
        PopupList.this.list.setSize(localRectangle.width, localRectangle.height);
      }
    });
    this.list.addMouseListener(new MouseListener()
    {
      public void mouseDoubleClick(MouseEvent paramAnonymousMouseEvent)
      {
      }

      public void mouseDown(MouseEvent paramAnonymousMouseEvent)
      {
      }

      public void mouseUp(MouseEvent paramAnonymousMouseEvent)
      {
        PopupList.this.shell.setVisible(false);
      }
    });
    this.list.addKeyListener(new KeyListener()
    {
      public void keyReleased(KeyEvent paramAnonymousKeyEvent)
      {
      }

      public void keyPressed(KeyEvent paramAnonymousKeyEvent)
      {
        if (paramAnonymousKeyEvent.character == '\r')
          PopupList.this.shell.setVisible(false);
      }
    });
  }

  private static int checkStyle(int paramInt)
  {
    int i = 100663296;
    return paramInt & i;
  }

  public Font getFont()
  {
    return this.list.getFont();
  }

  public String[] getItems()
  {
    return this.list.getItems();
  }

  public int getMinimumWidth()
  {
    return this.minimumWidth;
  }

  public String open(Rectangle paramRectangle)
  {
    Point localPoint = this.list.computeSize(paramRectangle.width, -1, false);
    Rectangle localRectangle = this.shell.getDisplay().getBounds();
    int i = localRectangle.height - (paramRectangle.y + paramRectangle.height) - 30;
    int j = paramRectangle.y - 30;
    int k = 0;
    if ((j > i) && (localPoint.y > i))
    {
      if (localPoint.y > j)
        localPoint.y = j;
      else
        localPoint.y += 2;
      k = paramRectangle.y - localPoint.y;
    }
    else
    {
      if (localPoint.y > i)
        localPoint.y = i;
      else
        localPoint.y += 2;
      k = paramRectangle.y + paramRectangle.height;
    }
    localPoint.x = paramRectangle.width;
    if (localPoint.x < this.minimumWidth)
      localPoint.x = this.minimumWidth;
    int m = paramRectangle.x + paramRectangle.width - localPoint.x;
    this.shell.setBounds(m, k, localPoint.x, localPoint.y);
    this.shell.open();
    this.list.setFocus();
    Display localDisplay = this.shell.getDisplay();
    while ((!this.shell.isDisposed()) && (this.shell.isVisible()))
      if (!localDisplay.readAndDispatch())
        localDisplay.sleep();
    String str = null;
    if (!this.shell.isDisposed())
    {
      String[] arrayOfString = this.list.getSelection();
      this.shell.dispose();
      if (arrayOfString.length != 0)
        str = arrayOfString[0];
    }
    return str;
  }

  public void select(String paramString)
  {
    String[] arrayOfString = this.list.getItems();
    if (paramString != null)
      for (int i = 0; i < arrayOfString.length; i++)
        if (arrayOfString[i].startsWith(paramString))
        {
          int j = this.list.indexOf(arrayOfString[i]);
          this.list.select(j);
          break;
        }
  }

  public void setFont(Font paramFont)
  {
    this.list.setFont(paramFont);
  }

  public void setItems(String[] paramArrayOfString)
  {
    this.list.setItems(paramArrayOfString);
  }

  public void setMinimumWidth(int paramInt)
  {
    if (paramInt < 0)
      SWT.error(5);
    this.minimumWidth = paramInt;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.custom.PopupList
 * JD-Core Version:    0.6.2
 */