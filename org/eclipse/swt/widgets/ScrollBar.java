package org.eclipse.swt.widgets;

import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.SCROLLBARINFO;
import org.eclipse.swt.internal.win32.SCROLLINFO;

public class ScrollBar extends Widget
{
  Scrollable parent;
  int increment;
  int pageIncrement;

  ScrollBar(Scrollable paramScrollable, int paramInt)
  {
    super(paramScrollable, checkStyle(paramInt));
    this.parent = paramScrollable;
    createWidget();
  }

  public void addSelectionListener(SelectionListener paramSelectionListener)
  {
    checkWidget();
    if (paramSelectionListener == null)
      error(4);
    TypedListener localTypedListener = new TypedListener(paramSelectionListener);
    addListener(13, localTypedListener);
    addListener(14, localTypedListener);
  }

  static int checkStyle(int paramInt)
  {
    return checkBits(paramInt, 256, 512, 0, 0, 0, 0);
  }

  void createWidget()
  {
    this.increment = 1;
    this.pageIncrement = 10;
  }

  void destroyWidget()
  {
    int i = hwndScrollBar();
    int j = scrollBarType();
    if (OS.IsWinCE)
    {
      SCROLLINFO localSCROLLINFO = new SCROLLINFO();
      localSCROLLINFO.cbSize = SCROLLINFO.sizeof;
      localSCROLLINFO.fMask = 3;
      localSCROLLINFO.nPage = 101;
      localSCROLLINFO.nMax = 100;
      localSCROLLINFO.nMin = 0;
      OS.SetScrollInfo(i, j, localSCROLLINFO, true);
    }
    else
    {
      OS.ShowScrollBar(i, j, false);
    }
    this.parent.destroyScrollBar(this.style);
    releaseHandle();
  }

  Rectangle getBounds()
  {
    this.parent.forceResize();
    RECT localRECT = new RECT();
    OS.GetClientRect(this.parent.scrolledHandle(), localRECT);
    int i = 0;
    int j = 0;
    int k;
    int m;
    if ((this.style & 0x100) != 0)
    {
      j = localRECT.bottom - localRECT.top;
      k = localRECT.right - localRECT.left;
      m = OS.GetSystemMetrics(3);
    }
    else
    {
      i = localRECT.right - localRECT.left;
      k = OS.GetSystemMetrics(2);
      m = localRECT.bottom - localRECT.top;
    }
    return new Rectangle(i, j, k, m);
  }

  public boolean getEnabled()
  {
    checkWidget();
    return (this.state & 0x8) == 0;
  }

  public int getIncrement()
  {
    checkWidget();
    return this.increment;
  }

  public int getMaximum()
  {
    checkWidget();
    SCROLLINFO localSCROLLINFO = new SCROLLINFO();
    localSCROLLINFO.cbSize = SCROLLINFO.sizeof;
    localSCROLLINFO.fMask = 1;
    int i = hwndScrollBar();
    int j = scrollBarType();
    OS.GetScrollInfo(i, j, localSCROLLINFO);
    return localSCROLLINFO.nMax;
  }

  public int getMinimum()
  {
    checkWidget();
    SCROLLINFO localSCROLLINFO = new SCROLLINFO();
    localSCROLLINFO.cbSize = SCROLLINFO.sizeof;
    localSCROLLINFO.fMask = 1;
    int i = hwndScrollBar();
    int j = scrollBarType();
    OS.GetScrollInfo(i, j, localSCROLLINFO);
    return localSCROLLINFO.nMin;
  }

  public int getPageIncrement()
  {
    checkWidget();
    return this.pageIncrement;
  }

  public Scrollable getParent()
  {
    checkWidget();
    return this.parent;
  }

  public int getSelection()
  {
    checkWidget();
    SCROLLINFO localSCROLLINFO = new SCROLLINFO();
    localSCROLLINFO.cbSize = SCROLLINFO.sizeof;
    localSCROLLINFO.fMask = 4;
    int i = hwndScrollBar();
    int j = scrollBarType();
    OS.GetScrollInfo(i, j, localSCROLLINFO);
    return localSCROLLINFO.nPos;
  }

  public Point getSize()
  {
    checkWidget();
    this.parent.forceResize();
    RECT localRECT = new RECT();
    OS.GetClientRect(this.parent.scrolledHandle(), localRECT);
    int i;
    int j;
    if ((this.style & 0x100) != 0)
    {
      i = localRECT.right - localRECT.left;
      j = OS.GetSystemMetrics(3);
    }
    else
    {
      i = OS.GetSystemMetrics(2);
      j = localRECT.bottom - localRECT.top;
    }
    return new Point(i, j);
  }

  public int getThumb()
  {
    checkWidget();
    SCROLLINFO localSCROLLINFO = new SCROLLINFO();
    localSCROLLINFO.cbSize = SCROLLINFO.sizeof;
    localSCROLLINFO.fMask = 2;
    int i = hwndScrollBar();
    int j = scrollBarType();
    OS.GetScrollInfo(i, j, localSCROLLINFO);
    if (localSCROLLINFO.nPage != 0)
      localSCROLLINFO.nPage -= 1;
    return localSCROLLINFO.nPage;
  }

  public Rectangle getThumbBounds()
  {
    checkWidget();
    this.parent.forceResize();
    SCROLLBARINFO localSCROLLBARINFO = new SCROLLBARINFO();
    localSCROLLBARINFO.cbSize = SCROLLBARINFO.sizeof;
    int i;
    int j;
    int k;
    int m;
    if ((this.style & 0x100) != 0)
    {
      OS.GetScrollBarInfo(this.parent.handle, -6, localSCROLLBARINFO);
      i = localSCROLLBARINFO.rcScrollBar.left + localSCROLLBARINFO.xyThumbTop;
      j = localSCROLLBARINFO.rcScrollBar.top;
      k = localSCROLLBARINFO.xyThumbBottom - localSCROLLBARINFO.xyThumbTop;
      m = localSCROLLBARINFO.rcScrollBar.bottom - localSCROLLBARINFO.rcScrollBar.top;
    }
    else
    {
      OS.GetScrollBarInfo(this.parent.handle, -5, localSCROLLBARINFO);
      i = localSCROLLBARINFO.rcScrollBar.left;
      j = localSCROLLBARINFO.rcScrollBar.top + localSCROLLBARINFO.xyThumbTop;
      k = localSCROLLBARINFO.rcScrollBar.right - localSCROLLBARINFO.rcScrollBar.left;
      m = localSCROLLBARINFO.xyThumbBottom - localSCROLLBARINFO.xyThumbTop;
    }
    RECT localRECT = new RECT();
    localRECT.left = i;
    localRECT.top = j;
    localRECT.right = (i + k);
    localRECT.bottom = (j + m);
    OS.MapWindowPoints(0, this.parent.handle, localRECT, 2);
    return new Rectangle(localRECT.left, localRECT.top, localRECT.right - localRECT.left, localRECT.bottom - localRECT.top);
  }

  public Rectangle getThumbTrackBounds()
  {
    checkWidget();
    this.parent.forceResize();
    SCROLLBARINFO localSCROLLBARINFO = new SCROLLBARINFO();
    localSCROLLBARINFO.cbSize = SCROLLBARINFO.sizeof;
    int i = 0;
    int j = 0;
    int n;
    int k;
    int m;
    if ((this.style & 0x100) != 0)
    {
      OS.GetScrollBarInfo(this.parent.handle, -6, localSCROLLBARINFO);
      n = OS.GetSystemMetrics(3);
      j = localSCROLLBARINFO.rcScrollBar.top;
      k = localSCROLLBARINFO.rcScrollBar.right - localSCROLLBARINFO.rcScrollBar.left;
      m = n;
      if (k <= 2 * n)
      {
        i = localSCROLLBARINFO.rcScrollBar.left + k / 2;
        k = 0;
      }
      else
      {
        i = localSCROLLBARINFO.rcScrollBar.left + n;
        k -= 2 * n;
      }
    }
    else
    {
      OS.GetScrollBarInfo(this.parent.handle, -5, localSCROLLBARINFO);
      n = OS.GetSystemMetrics(20);
      i = localSCROLLBARINFO.rcScrollBar.left;
      k = n;
      m = localSCROLLBARINFO.rcScrollBar.bottom - localSCROLLBARINFO.rcScrollBar.top;
      if (m <= 2 * n)
      {
        j = localSCROLLBARINFO.rcScrollBar.top + m / 2;
        m = 0;
      }
      else
      {
        j = localSCROLLBARINFO.rcScrollBar.top + n;
        m -= 2 * n;
      }
    }
    RECT localRECT = new RECT();
    localRECT.left = i;
    localRECT.top = j;
    localRECT.right = (i + k);
    localRECT.bottom = (j + m);
    OS.MapWindowPoints(0, this.parent.handle, localRECT, 2);
    return new Rectangle(localRECT.left, localRECT.top, localRECT.right - localRECT.left, localRECT.bottom - localRECT.top);
  }

  public boolean getVisible()
  {
    checkWidget();
    if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(4, 10)))
    {
      SCROLLBARINFO localSCROLLBARINFO = new SCROLLBARINFO();
      localSCROLLBARINFO.cbSize = SCROLLBARINFO.sizeof;
      int i = (this.style & 0x200) != 0 ? -5 : -6;
      OS.GetScrollBarInfo(hwndScrollBar(), i, localSCROLLBARINFO);
      return (localSCROLLBARINFO.rgstate[0] & 0x8000) == 0;
    }
    return (this.state & 0x10) == 0;
  }

  int hwndScrollBar()
  {
    return this.parent.scrolledHandle();
  }

  public boolean isEnabled()
  {
    checkWidget();
    return (getEnabled()) && (this.parent.isEnabled());
  }

  public boolean isVisible()
  {
    checkWidget();
    return (getVisible()) && (this.parent.isVisible());
  }

  void releaseHandle()
  {
    super.releaseHandle();
    this.parent = null;
  }

  void releaseParent()
  {
    super.releaseParent();
    if (this.parent.horizontalBar == this)
      this.parent.horizontalBar = null;
    if (this.parent.verticalBar == this)
      this.parent.verticalBar = null;
  }

  public void removeSelectionListener(SelectionListener paramSelectionListener)
  {
    checkWidget();
    if (paramSelectionListener == null)
      error(4);
    if (this.eventTable == null)
      return;
    this.eventTable.unhook(13, paramSelectionListener);
    this.eventTable.unhook(14, paramSelectionListener);
  }

  int scrollBarType()
  {
    return (this.style & 0x200) != 0 ? 1 : 0;
  }

  public void setEnabled(boolean paramBoolean)
  {
    checkWidget();
    if (!OS.IsWinCE)
    {
      int i = hwndScrollBar();
      int j = scrollBarType();
      int k = paramBoolean ? 0 : 3;
      OS.EnableScrollBar(i, j, k);
      if (paramBoolean)
        this.state &= -9;
      else
        this.state |= 8;
    }
  }

  public void setIncrement(int paramInt)
  {
    checkWidget();
    if (paramInt < 1)
      return;
    this.increment = paramInt;
  }

  public void setMaximum(int paramInt)
  {
    checkWidget();
    if (paramInt < 0)
      return;
    SCROLLINFO localSCROLLINFO = new SCROLLINFO();
    localSCROLLINFO.cbSize = SCROLLINFO.sizeof;
    int i = hwndScrollBar();
    int j = scrollBarType();
    localSCROLLINFO.fMask = 9;
    OS.GetScrollInfo(i, j, localSCROLLINFO);
    if (paramInt - localSCROLLINFO.nMin - localSCROLLINFO.nPage < 1)
      return;
    localSCROLLINFO.nMax = paramInt;
    SetScrollInfo(i, j, localSCROLLINFO, true);
  }

  public void setMinimum(int paramInt)
  {
    checkWidget();
    if (paramInt < 0)
      return;
    SCROLLINFO localSCROLLINFO = new SCROLLINFO();
    localSCROLLINFO.cbSize = SCROLLINFO.sizeof;
    int i = hwndScrollBar();
    int j = scrollBarType();
    localSCROLLINFO.fMask = 9;
    OS.GetScrollInfo(i, j, localSCROLLINFO);
    if (localSCROLLINFO.nMax - paramInt - localSCROLLINFO.nPage < 1)
      return;
    localSCROLLINFO.nMin = paramInt;
    SetScrollInfo(i, j, localSCROLLINFO, true);
  }

  public void setPageIncrement(int paramInt)
  {
    checkWidget();
    if (paramInt < 1)
      return;
    this.pageIncrement = paramInt;
  }

  boolean SetScrollInfo(int paramInt1, int paramInt2, SCROLLINFO paramSCROLLINFO, boolean paramBoolean)
  {
    int i = 0;
    boolean bool1 = getVisible();
    ScrollBar localScrollBar = null;
    if (!OS.IsWinCE)
    {
      switch (paramInt2)
      {
      case 0:
        localScrollBar = this.parent.getVerticalBar();
        break;
      case 1:
        localScrollBar = this.parent.getHorizontalBar();
      }
      i = (localScrollBar != null) && (localScrollBar.getVisible()) ? 1 : 0;
    }
    if ((!bool1) || ((this.state & 0x8) != 0))
      paramBoolean = false;
    boolean bool2 = OS.SetScrollInfo(paramInt1, paramInt2, paramSCROLLINFO, paramBoolean);
    if ((!bool1) && (!OS.IsWinCE))
      OS.ShowScrollBar(paramInt1, i == 0 ? 3 : paramInt2, false);
    if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(6, 0)) && (bool1) && (localScrollBar != null) && (i == 0))
      OS.ShowScrollBar(paramInt1, paramInt2 == 0 ? 1 : 0, false);
    if (((this.state & 0x8) != 0) && (!OS.IsWinCE))
      OS.EnableScrollBar(paramInt1, paramInt2, 3);
    return bool2;
  }

  public void setSelection(int paramInt)
  {
    checkWidget();
    SCROLLINFO localSCROLLINFO = new SCROLLINFO();
    localSCROLLINFO.cbSize = SCROLLINFO.sizeof;
    int i = hwndScrollBar();
    int j = scrollBarType();
    localSCROLLINFO.fMask = 4;
    localSCROLLINFO.nPos = paramInt;
    SetScrollInfo(i, j, localSCROLLINFO, true);
  }

  public void setThumb(int paramInt)
  {
    checkWidget();
    if (paramInt < 1)
      return;
    SCROLLINFO localSCROLLINFO = new SCROLLINFO();
    localSCROLLINFO.cbSize = SCROLLINFO.sizeof;
    int i = hwndScrollBar();
    int j = scrollBarType();
    localSCROLLINFO.fMask = 11;
    OS.GetScrollInfo(i, j, localSCROLLINFO);
    localSCROLLINFO.nPage = paramInt;
    if (localSCROLLINFO.nPage != 0)
      localSCROLLINFO.nPage += 1;
    SetScrollInfo(i, j, localSCROLLINFO, true);
  }

  public void setValues(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    checkWidget();
    if (paramInt2 < 0)
      return;
    if (paramInt3 < 0)
      return;
    if (paramInt4 < 1)
      return;
    if (paramInt5 < 1)
      return;
    if (paramInt6 < 1)
      return;
    this.increment = paramInt5;
    this.pageIncrement = paramInt6;
    SCROLLINFO localSCROLLINFO = new SCROLLINFO();
    localSCROLLINFO.cbSize = SCROLLINFO.sizeof;
    localSCROLLINFO.fMask = 15;
    localSCROLLINFO.nPos = paramInt1;
    localSCROLLINFO.nMin = paramInt2;
    localSCROLLINFO.nMax = paramInt3;
    localSCROLLINFO.nPage = paramInt4;
    if (localSCROLLINFO.nPage != 0)
      localSCROLLINFO.nPage += 1;
    int i = hwndScrollBar();
    int j = scrollBarType();
    SetScrollInfo(i, j, localSCROLLINFO, true);
  }

  public void setVisible(boolean paramBoolean)
  {
    checkWidget();
    if (paramBoolean == getVisible())
      return;
    if (OS.IsWinCE)
    {
      SCROLLINFO localSCROLLINFO1 = new SCROLLINFO();
      localSCROLLINFO1.cbSize = SCROLLINFO.sizeof;
      j = hwndScrollBar();
      int k = scrollBarType();
      localSCROLLINFO1.fMask = 3;
      if (paramBoolean)
        localSCROLLINFO1.fMask |= 8;
      OS.GetScrollInfo(j, k, localSCROLLINFO1);
      if (localSCROLLINFO1.nPage == localSCROLLINFO1.nMax - localSCROLLINFO1.nMin + 1)
      {
        int m = localSCROLLINFO1.nMax;
        localSCROLLINFO1.nMax += 1;
        OS.SetScrollInfo(j, k, localSCROLLINFO1, false);
        localSCROLLINFO1.nMax = m;
        OS.SetScrollInfo(j, k, localSCROLLINFO1, true);
      }
      return;
    }
    this.state = (paramBoolean ? this.state & 0xFFFFFFEF : this.state | 0x10);
    int i = hwndScrollBar();
    int j = scrollBarType();
    if (OS.ShowScrollBar(i, j, paramBoolean))
    {
      if ((this.state & 0x8) == 0)
      {
        SCROLLINFO localSCROLLINFO2 = new SCROLLINFO();
        localSCROLLINFO2.cbSize = SCROLLINFO.sizeof;
        localSCROLLINFO2.fMask = 3;
        OS.GetScrollInfo(i, j, localSCROLLINFO2);
        if (localSCROLLINFO2.nMax - localSCROLLINFO2.nMin - localSCROLLINFO2.nPage >= 0)
          OS.EnableScrollBar(i, j, 0);
      }
      sendEvent(paramBoolean ? 22 : 23);
    }
  }

  LRESULT wmScrollChild(int paramInt1, int paramInt2)
  {
    int i = OS.LOWORD(paramInt1);
    if (i == 8)
      return null;
    Event localEvent = new Event();
    switch (i)
    {
    case 4:
      localEvent.detail = 0;
      break;
    case 5:
      localEvent.detail = 1;
      break;
    case 6:
      localEvent.detail = 16777223;
      break;
    case 7:
      localEvent.detail = 16777224;
      break;
    case 1:
      localEvent.detail = 16777218;
      break;
    case 0:
      localEvent.detail = 16777217;
      break;
    case 3:
      localEvent.detail = 16777222;
      break;
    case 2:
      localEvent.detail = 16777221;
    }
    sendSelectionEvent(13, localEvent, true);
    return null;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.ScrollBar
 * JD-Core Version:    0.6.2
 */