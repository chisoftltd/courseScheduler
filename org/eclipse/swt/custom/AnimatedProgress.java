package org.eclipse.swt.custom;

import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

/** @deprecated */
public class AnimatedProgress extends Canvas
{
  static final int SLEEP = 70;
  static final int DEFAULT_WIDTH = 160;
  static final int DEFAULT_HEIGHT = 18;
  boolean active = false;
  boolean showStripes = false;
  int value;
  int orientation = 256;
  boolean showBorder = false;

  public AnimatedProgress(Composite paramComposite, int paramInt)
  {
    super(paramComposite, checkStyle(paramInt));
    if ((paramInt & 0x200) != 0)
      this.orientation = 512;
    this.showBorder = ((paramInt & 0x800) != 0);
    addControlListener(new ControlAdapter()
    {
      public void controlResized(ControlEvent paramAnonymousControlEvent)
      {
        AnimatedProgress.this.redraw();
      }
    });
    addPaintListener(new PaintListener()
    {
      public void paintControl(PaintEvent paramAnonymousPaintEvent)
      {
        AnimatedProgress.this.paint(paramAnonymousPaintEvent);
      }
    });
    addDisposeListener(new DisposeListener()
    {
      public void widgetDisposed(DisposeEvent paramAnonymousDisposeEvent)
      {
        AnimatedProgress.this.stop();
      }
    });
  }

  private static int checkStyle(int paramInt)
  {
    int i = 0;
    return paramInt & i;
  }

  public synchronized void clear()
  {
    checkWidget();
    if (this.active)
      stop();
    this.showStripes = false;
    redraw();
  }

  public Point computeSize(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    checkWidget();
    Point localPoint = null;
    if (this.orientation == 256)
      localPoint = new Point(160, 18);
    else
      localPoint = new Point(18, 160);
    if (paramInt1 != -1)
      localPoint.x = paramInt1;
    if (paramInt2 != -1)
      localPoint.y = paramInt2;
    return localPoint;
  }

  private void drawBevelRect(GC paramGC, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Color paramColor1, Color paramColor2)
  {
    paramGC.setForeground(paramColor1);
    paramGC.drawLine(paramInt1, paramInt2, paramInt1 + paramInt3 - 1, paramInt2);
    paramGC.drawLine(paramInt1, paramInt2, paramInt1, paramInt2 + paramInt4 - 1);
    paramGC.setForeground(paramColor2);
    paramGC.drawLine(paramInt1 + paramInt3, paramInt2, paramInt1 + paramInt3, paramInt2 + paramInt4);
    paramGC.drawLine(paramInt1, paramInt2 + paramInt4, paramInt1 + paramInt3, paramInt2 + paramInt4);
  }

  void paint(PaintEvent paramPaintEvent)
  {
    GC localGC = paramPaintEvent.gc;
    Display localDisplay = getDisplay();
    Rectangle localRectangle = getClientArea();
    localGC.fillRectangle(localRectangle);
    if (this.showBorder)
      drawBevelRect(localGC, localRectangle.x, localRectangle.y, localRectangle.width - 1, localRectangle.height - 1, localDisplay.getSystemColor(18), localDisplay.getSystemColor(20));
    paintStripes(localGC);
  }

  void paintStripes(GC paramGC)
  {
    if (!this.showStripes)
      return;
    Rectangle localRectangle = getClientArea();
    localRectangle = new Rectangle(localRectangle.x + 2, localRectangle.y + 2, localRectangle.width - 4, localRectangle.height - 4);
    paramGC.setLineWidth(2);
    paramGC.setClipping(localRectangle);
    Color localColor = getDisplay().getSystemColor(26);
    paramGC.setBackground(localColor);
    paramGC.fillRectangle(localRectangle);
    paramGC.setForeground(getBackground());
    int i = 12;
    int j = this.value == 0 ? i - 2 : this.value - 2;
    int k;
    int m;
    int n;
    int i1;
    int i2;
    if (this.orientation == 256)
    {
      k = localRectangle.y - 1;
      m = localRectangle.width;
      n = localRectangle.height + 2;
      i1 = 0;
      while (i1 < m)
      {
        i2 = i1 + j;
        paramGC.drawLine(i2, k, i2, n);
        i1 += i;
      }
    }
    else
    {
      k = localRectangle.x - 1;
      m = localRectangle.width + 2;
      n = localRectangle.height;
      i1 = 0;
      while (i1 < n)
      {
        i2 = i1 + j;
        paramGC.drawLine(k, i2, m, i2);
        i1 += i;
      }
    }
    if (this.active)
      this.value = ((this.value + 2) % i);
  }

  public synchronized void start()
  {
    checkWidget();
    if (this.active)
      return;
    this.active = true;
    this.showStripes = true;
    Display localDisplay = getDisplay();
    Runnable[] arrayOfRunnable = new Runnable[1];
    arrayOfRunnable[0] = new Runnable()
    {
      private final Display val$display;
      private final Runnable[] val$timer;

      public void run()
      {
        if (!AnimatedProgress.this.active)
          return;
        GC localGC = new GC(AnimatedProgress.this);
        AnimatedProgress.this.paintStripes(localGC);
        localGC.dispose();
        this.val$display.timerExec(70, this.val$timer[0]);
      }
    };
    localDisplay.timerExec(70, arrayOfRunnable[0]);
  }

  public synchronized void stop()
  {
    this.active = false;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.custom.AnimatedProgress
 * JD-Core Version:    0.6.2
 */