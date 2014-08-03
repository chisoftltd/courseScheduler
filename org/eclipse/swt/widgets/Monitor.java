package org.eclipse.swt.widgets;

import org.eclipse.swt.graphics.Rectangle;

public final class Monitor
{
  int handle;
  int x;
  int y;
  int width;
  int height;
  int clientX;
  int clientY;
  int clientWidth;
  int clientHeight;

  public boolean equals(Object paramObject)
  {
    if (paramObject == this)
      return true;
    if (!(paramObject instanceof Monitor))
      return false;
    Monitor localMonitor = (Monitor)paramObject;
    return this.handle == localMonitor.handle;
  }

  public Rectangle getBounds()
  {
    return new Rectangle(this.x, this.y, this.width, this.height);
  }

  public Rectangle getClientArea()
  {
    return new Rectangle(this.clientX, this.clientY, this.clientWidth, this.clientHeight);
  }

  public int hashCode()
  {
    return this.handle;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.Monitor
 * JD-Core Version:    0.6.2
 */