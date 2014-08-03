package org.eclipse.swt.custom;

import org.eclipse.swt.dnd.DropTargetEffect;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class StyledTextDropTargetEffect extends DropTargetEffect
{
  static final int CARET_WIDTH = 2;
  static final int SCROLL_HYSTERESIS = 100;
  static final int SCROLL_TOLERANCE = 20;
  int currentOffset = -1;
  long scrollBeginTime;
  int scrollX = -1;
  int scrollY = -1;
  Listener paintListener = new Listener()
  {
    public void handleEvent(Event paramAnonymousEvent)
    {
      if (StyledTextDropTargetEffect.this.currentOffset != -1)
      {
        StyledText localStyledText = (StyledText)StyledTextDropTargetEffect.this.getControl();
        Point localPoint = localStyledText.getLocationAtOffset(StyledTextDropTargetEffect.this.currentOffset);
        int i = localStyledText.getLineHeight(StyledTextDropTargetEffect.this.currentOffset);
        paramAnonymousEvent.gc.setBackground(paramAnonymousEvent.display.getSystemColor(2));
        paramAnonymousEvent.gc.fillRectangle(localPoint.x, localPoint.y, 2, i);
      }
    }
  };

  public StyledTextDropTargetEffect(StyledText paramStyledText)
  {
    super(paramStyledText);
  }

  public void dragEnter(DropTargetEvent paramDropTargetEvent)
  {
    this.currentOffset = -1;
    this.scrollBeginTime = 0L;
    this.scrollX = -1;
    this.scrollY = -1;
    getControl().removeListener(9, this.paintListener);
    getControl().addListener(9, this.paintListener);
  }

  public void dragLeave(DropTargetEvent paramDropTargetEvent)
  {
    StyledText localStyledText = (StyledText)getControl();
    if (this.currentOffset != -1)
      refreshCaret(localStyledText, this.currentOffset, -1);
    localStyledText.removeListener(9, this.paintListener);
    this.scrollBeginTime = 0L;
    this.scrollX = -1;
    this.scrollY = -1;
  }

  public void dragOver(DropTargetEvent paramDropTargetEvent)
  {
    int i = paramDropTargetEvent.feedback;
    StyledText localStyledText = (StyledText)getControl();
    Point localPoint = localStyledText.getDisplay().map(null, localStyledText, paramDropTargetEvent.x, paramDropTargetEvent.y);
    Object localObject;
    if ((i & 0x8) == 0)
    {
      this.scrollBeginTime = 0L;
      this.scrollX = (this.scrollY = -1);
    }
    else if (localStyledText.getCharCount() == 0)
    {
      this.scrollBeginTime = 0L;
      this.scrollX = (this.scrollY = -1);
    }
    else if ((this.scrollX != -1) && (this.scrollY != -1) && (this.scrollBeginTime != 0L) && (((localPoint.x >= this.scrollX) && (localPoint.x <= this.scrollX + 20)) || ((localPoint.y >= this.scrollY) && (localPoint.y <= this.scrollY + 20))))
    {
      if (System.currentTimeMillis() >= this.scrollBeginTime)
      {
        localObject = localStyledText.getClientArea();
        GC localGC = new GC(localStyledText);
        FontMetrics localFontMetrics = localGC.getFontMetrics();
        localGC.dispose();
        int k = localFontMetrics.getAverageCharWidth();
        int m = 10 * k;
        if (localPoint.x < ((Rectangle)localObject).x + 3 * k)
        {
          n = localStyledText.getHorizontalPixel();
          localStyledText.setHorizontalPixel(n - m);
        }
        if (localPoint.x > ((Rectangle)localObject).width - 3 * k)
        {
          n = localStyledText.getHorizontalPixel();
          localStyledText.setHorizontalPixel(n + m);
        }
        int n = localStyledText.getLineHeight();
        int i1;
        if (localPoint.y < ((Rectangle)localObject).y + n)
        {
          i1 = localStyledText.getTopPixel();
          localStyledText.setTopPixel(i1 - n);
        }
        if (localPoint.y > ((Rectangle)localObject).height - n)
        {
          i1 = localStyledText.getTopPixel();
          localStyledText.setTopPixel(i1 + n);
        }
        this.scrollBeginTime = 0L;
        this.scrollX = (this.scrollY = -1);
      }
    }
    else
    {
      this.scrollBeginTime = (System.currentTimeMillis() + 100L);
      this.scrollX = localPoint.x;
      this.scrollY = localPoint.y;
    }
    if ((i & 0x1) != 0)
    {
      localObject = new int[1];
      int j = localStyledText.getOffsetAtPoint(localPoint.x, localPoint.y, (int[])localObject, false);
      j += localObject[0];
      if (j != this.currentOffset)
      {
        refreshCaret(localStyledText, this.currentOffset, j);
        this.currentOffset = j;
      }
    }
  }

  void refreshCaret(StyledText paramStyledText, int paramInt1, int paramInt2)
  {
    if (paramInt1 != paramInt2)
    {
      Point localPoint;
      int i;
      if (paramInt1 != -1)
      {
        localPoint = paramStyledText.getLocationAtOffset(paramInt1);
        i = paramStyledText.getLineHeight(paramInt1);
        paramStyledText.redraw(localPoint.x, localPoint.y, 2, i, false);
      }
      if (paramInt2 != -1)
      {
        localPoint = paramStyledText.getLocationAtOffset(paramInt2);
        i = paramStyledText.getLineHeight(paramInt2);
        paramStyledText.redraw(localPoint.x, localPoint.y, 2, i, false);
      }
    }
  }

  public void dropAccept(DropTargetEvent paramDropTargetEvent)
  {
    if (this.currentOffset != -1)
    {
      StyledText localStyledText = (StyledText)getControl();
      localStyledText.setSelection(this.currentOffset);
      this.currentOffset = -1;
    }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.custom.StyledTextDropTargetEffect
 * JD-Core Version:    0.6.2
 */