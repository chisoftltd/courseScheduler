package org.eclipse.swt.widgets;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;

public class Event
{
  public Display display;
  public Widget widget;
  public int type;
  public int detail;
  public Widget item;
  public int index;
  public GC gc;
  public int x;
  public int y;
  public int width;
  public int height;
  public int count;
  public int time;
  public int button;
  public char character;
  public int keyCode;
  public int keyLocation;
  public int stateMask;
  public int start;
  public int end;
  public String text;
  public boolean doit = true;
  public Object data;

  public Rectangle getBounds()
  {
    return new Rectangle(this.x, this.y, this.width, this.height);
  }

  public void setBounds(Rectangle paramRectangle)
  {
    this.x = paramRectangle.x;
    this.y = paramRectangle.y;
    this.width = paramRectangle.width;
    this.height = paramRectangle.height;
  }

  public String toString()
  {
    return "Event {type=" + this.type + " " + this.widget + " time=" + this.time + " data=" + this.data + " x=" + this.x + " y=" + this.y + " width=" + this.width + " height=" + this.height + " detail=" + this.detail + "}";
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.Event
 * JD-Core Version:    0.6.2
 */