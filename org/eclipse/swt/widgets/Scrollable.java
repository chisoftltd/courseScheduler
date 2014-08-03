package org.eclipse.swt.widgets;

import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.SCROLLBARINFO;
import org.eclipse.swt.internal.win32.SCROLLINFO;
import org.eclipse.swt.internal.win32.TCHAR;

public abstract class Scrollable extends Control
{
  ScrollBar horizontalBar;
  ScrollBar verticalBar;

  Scrollable()
  {
  }

  public Scrollable(Composite paramComposite, int paramInt)
  {
    super(paramComposite, paramInt);
  }

  int callWindowProc(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.handle == 0)
      return 0;
    return OS.DefWindowProc(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  public Rectangle computeTrim(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    checkWidget();
    int i = scrolledHandle();
    RECT localRECT = new RECT();
    OS.SetRect(localRECT, paramInt1, paramInt2, paramInt1 + paramInt3, paramInt2 + paramInt4);
    int j = OS.GetWindowLong(i, -16);
    int k = OS.GetWindowLong(i, -20);
    OS.AdjustWindowRectEx(localRECT, j, false, k);
    if (this.horizontalBar != null)
      localRECT.bottom += OS.GetSystemMetrics(3);
    if (this.verticalBar != null)
      localRECT.right += OS.GetSystemMetrics(2);
    int m = localRECT.right - localRECT.left;
    int n = localRECT.bottom - localRECT.top;
    return new Rectangle(localRECT.left, localRECT.top, m, n);
  }

  ScrollBar createScrollBar(int paramInt)
  {
    ScrollBar localScrollBar = new ScrollBar(this, paramInt);
    if ((this.state & 0x2) != 0)
    {
      localScrollBar.setMaximum(100);
      localScrollBar.setThumb(10);
    }
    return localScrollBar;
  }

  void createWidget()
  {
    super.createWidget();
    if ((this.style & 0x100) != 0)
      this.horizontalBar = createScrollBar(256);
    if ((this.style & 0x200) != 0)
      this.verticalBar = createScrollBar(512);
  }

  void destroyScrollBar(int paramInt)
  {
    int i = scrolledHandle();
    int j = OS.GetWindowLong(i, -16);
    if ((paramInt & 0x100) != 0)
    {
      this.style &= -257;
      j &= -1048577;
    }
    if ((paramInt & 0x200) != 0)
    {
      this.style &= -513;
      j &= -2097153;
    }
    OS.SetWindowLong(i, -16, j);
  }

  public Rectangle getClientArea()
  {
    checkWidget();
    forceResize();
    RECT localRECT = new RECT();
    int i = scrolledHandle();
    OS.GetClientRect(i, localRECT);
    int j = localRECT.left;
    int k = localRECT.top;
    int m = localRECT.right - localRECT.left;
    int n = localRECT.bottom - localRECT.top;
    if (i != this.handle)
    {
      OS.GetClientRect(this.handle, localRECT);
      OS.MapWindowPoints(this.handle, i, localRECT, 2);
      j = -localRECT.left;
      k = -localRECT.top;
    }
    return new Rectangle(j, k, m, n);
  }

  public ScrollBar getHorizontalBar()
  {
    checkWidget();
    return this.horizontalBar;
  }

  public ScrollBar getVerticalBar()
  {
    checkWidget();
    return this.verticalBar;
  }

  void releaseChildren(boolean paramBoolean)
  {
    if (this.horizontalBar != null)
    {
      this.horizontalBar.release(false);
      this.horizontalBar = null;
    }
    if (this.verticalBar != null)
    {
      this.verticalBar.release(false);
      this.verticalBar = null;
    }
    super.releaseChildren(paramBoolean);
  }

  void reskinChildren(int paramInt)
  {
    if (this.horizontalBar != null)
      this.horizontalBar.reskin(paramInt);
    if (this.verticalBar != null)
      this.verticalBar.reskin(paramInt);
    super.reskinChildren(paramInt);
  }

  int scrolledHandle()
  {
    return this.handle;
  }

  int widgetExtStyle()
  {
    return super.widgetExtStyle();
  }

  int widgetStyle()
  {
    int i = super.widgetStyle() | 0x10000;
    if ((this.style & 0x100) != 0)
      i |= 1048576;
    if ((this.style & 0x200) != 0)
      i |= 2097152;
    return i;
  }

  TCHAR windowClass()
  {
    return this.display.windowClass;
  }

  int windowProc()
  {
    return this.display.windowProc;
  }

  LRESULT WM_HSCROLL(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_HSCROLL(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    if ((this.horizontalBar != null) && ((paramInt2 == 0) || (paramInt2 == this.handle)))
      return wmScroll(this.horizontalBar, (this.state & 0x2) != 0, this.handle, 276, paramInt1, paramInt2);
    return localLRESULT;
  }

  LRESULT WM_MOUSEWHEEL(int paramInt1, int paramInt2)
  {
    return wmScrollWheel((this.state & 0x2) != 0, paramInt1, paramInt2);
  }

  LRESULT WM_SIZE(int paramInt1, int paramInt2)
  {
    int i = callWindowProc(this.handle, 5, paramInt1, paramInt2);
    super.WM_SIZE(paramInt1, paramInt2);
    if (i == 0)
      return LRESULT.ZERO;
    return new LRESULT(i);
  }

  LRESULT WM_VSCROLL(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_VSCROLL(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    if ((this.verticalBar != null) && ((paramInt2 == 0) || (paramInt2 == this.handle)))
      return wmScroll(this.verticalBar, (this.state & 0x2) != 0, this.handle, 277, paramInt1, paramInt2);
    return localLRESULT;
  }

  LRESULT wmNCPaint(int paramInt1, int paramInt2, int paramInt3)
  {
    LRESULT localLRESULT = super.wmNCPaint(paramInt1, paramInt2, paramInt3);
    if (localLRESULT != null)
      return localLRESULT;
    if ((OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()) && (!OS.IsWinCE) && (OS.WIN32_VERSION < OS.VERSION(6, 0)))
    {
      int i = OS.GetWindowLong(paramInt1, -16);
      if ((i & 0x300000) != 0)
      {
        RECT localRECT1 = new RECT();
        OS.GetWindowRect(paramInt1, localRECT1);
        RECT localRECT2 = new RECT();
        int j = OS.GetWindowLong(paramInt1, -20);
        OS.AdjustWindowRectEx(localRECT2, i, false, j);
        int k = 0;
        int m = 0;
        SCROLLBARINFO localSCROLLBARINFO = new SCROLLBARINFO();
        localSCROLLBARINFO.cbSize = SCROLLBARINFO.sizeof;
        if (OS.GetScrollBarInfo(paramInt1, -6, localSCROLLBARINFO))
          k = (localSCROLLBARINFO.rgstate[0] & 0x8000) == 0 ? 1 : 0;
        if (OS.GetScrollBarInfo(paramInt1, -5, localSCROLLBARINFO))
          m = (localSCROLLBARINFO.rgstate[0] & 0x8000) == 0 ? 1 : 0;
        RECT localRECT3 = new RECT();
        localRECT3.bottom = (localRECT1.bottom - localRECT1.top - localRECT2.bottom);
        localRECT3.top = (localRECT3.bottom - (m != 0 ? OS.GetSystemMetrics(3) : 0));
        if ((j & 0x4000) != 0)
        {
          localRECT3.left = localRECT2.left;
          localRECT3.right = (localRECT3.left + (k != 0 ? OS.GetSystemMetrics(2) : 0));
        }
        else
        {
          localRECT3.right = (localRECT1.right - localRECT1.left - localRECT2.right);
          localRECT3.left = (localRECT3.right - (k != 0 ? OS.GetSystemMetrics(2) : 0));
        }
        if ((localRECT3.left != localRECT3.right) && (localRECT3.top != localRECT3.bottom))
        {
          int n = OS.GetWindowDC(paramInt1);
          OS.FillRect(n, localRECT3, OS.COLOR_BTNFACE + 1);
          Decorations localDecorations = menuShell();
          if ((localDecorations.style & 0x10) != 0)
          {
            int i1 = localDecorations.scrolledHandle();
            int i2 = paramInt1 == i1 ? 1 : 0;
            if (i2 == 0)
            {
              RECT localRECT4 = new RECT();
              OS.GetClientRect(i1, localRECT4);
              OS.MapWindowPoints(i1, 0, localRECT4, 2);
              i2 = (localRECT4.right == localRECT1.right) && (localRECT4.bottom == localRECT1.bottom) ? 1 : 0;
            }
            if (i2 != 0)
              OS.DrawThemeBackground(this.display.hScrollBarTheme(), n, 10, 0, localRECT3, null);
          }
          OS.ReleaseDC(paramInt1, n);
        }
      }
    }
    return localLRESULT;
  }

  LRESULT wmScrollWheel(boolean paramBoolean, int paramInt1, int paramInt2)
  {
    int i = this.display.scrollRemainder;
    LRESULT localLRESULT = super.WM_MOUSEWHEEL(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    if (paramBoolean)
    {
      if ((paramInt1 & 0xC) != 0)
        return localLRESULT;
      j = (this.verticalBar != null) && (this.verticalBar.getEnabled()) ? 1 : 0;
      k = (this.horizontalBar != null) && (this.horizontalBar.getEnabled()) ? 1 : 0;
      m = k != 0 ? 276 : j != 0 ? 277 : 0;
      if (m == 0)
        return localLRESULT;
      int[] arrayOfInt = new int[1];
      OS.SystemParametersInfo(104, 0, arrayOfInt, 0);
      int i1 = OS.GET_WHEEL_DELTA_WPARAM(paramInt1);
      int i2 = arrayOfInt[0] == -1 ? 1 : 0;
      int i5;
      if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(5, 1)))
      {
        ScrollBar localScrollBar = j != 0 ? this.verticalBar : this.horizontalBar;
        SCROLLINFO localSCROLLINFO = new SCROLLINFO();
        localSCROLLINFO.cbSize = SCROLLINFO.sizeof;
        localSCROLLINFO.fMask = 4;
        OS.GetScrollInfo(this.handle, localScrollBar.scrollBarType(), localSCROLLINFO);
        if ((j != 0) && (i2 == 0))
          i1 *= arrayOfInt[0];
        i5 = i2 != 0 ? localScrollBar.getPageIncrement() : localScrollBar.getIncrement();
        localSCROLLINFO.nPos -= i5 * i1 / 120;
        OS.SetScrollInfo(this.handle, localScrollBar.scrollBarType(), localSCROLLINFO, true);
        OS.SendMessage(this.handle, m, 4, 0);
      }
      else
      {
        int i3 = 0;
        if (i2 != 0)
        {
          i3 = i1 < 0 ? 3 : 2;
        }
        else
        {
          i3 = i1 < 0 ? 1 : 0;
          if (m == 277)
            i1 *= arrayOfInt[0];
        }
        if ((i1 ^ i) >= 0)
          i1 += i;
        int i4 = Math.abs(i1) / 120;
        for (i5 = 0; i5 < i4; i5++)
          OS.SendMessage(this.handle, m, i3, 0);
      }
      return LRESULT.ZERO;
    }
    int j = this.verticalBar == null ? 0 : this.verticalBar.getSelection();
    int k = this.horizontalBar == null ? 0 : this.horizontalBar.getSelection();
    int m = callWindowProc(this.handle, 522, paramInt1, paramInt2);
    int n;
    Event localEvent;
    if (this.verticalBar != null)
    {
      n = this.verticalBar.getSelection();
      if (n != j)
      {
        localEvent = new Event();
        localEvent.detail = (n < j ? 16777221 : 16777222);
        this.verticalBar.sendSelectionEvent(13, localEvent, true);
      }
    }
    if (this.horizontalBar != null)
    {
      n = this.horizontalBar.getSelection();
      if (n != k)
      {
        localEvent = new Event();
        localEvent.detail = (n < k ? 16777221 : 16777222);
        this.horizontalBar.sendSelectionEvent(13, localEvent, true);
      }
    }
    return new LRESULT(m);
  }

  LRESULT wmScroll(ScrollBar paramScrollBar, boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    LRESULT localLRESULT = null;
    int i;
    if (paramBoolean)
    {
      i = paramInt2 == 276 ? 0 : 1;
      SCROLLINFO localSCROLLINFO = new SCROLLINFO();
      localSCROLLINFO.cbSize = SCROLLINFO.sizeof;
      localSCROLLINFO.fMask = 21;
      OS.GetScrollInfo(paramInt1, i, localSCROLLINFO);
      localSCROLLINFO.fMask = 4;
      int j = OS.LOWORD(paramInt3);
      switch (j)
      {
      case 8:
        return null;
      case 4:
      case 5:
        localSCROLLINFO.nPos = localSCROLLINFO.nTrackPos;
        break;
      case 6:
        localSCROLLINFO.nPos = localSCROLLINFO.nMin;
        break;
      case 7:
        localSCROLLINFO.nPos = localSCROLLINFO.nMax;
        break;
      case 1:
        localSCROLLINFO.nPos += paramScrollBar.getIncrement();
        break;
      case 0:
        int k = paramScrollBar.getIncrement();
        localSCROLLINFO.nPos = Math.max(localSCROLLINFO.nMin, localSCROLLINFO.nPos - k);
        break;
      case 3:
        localSCROLLINFO.nPos += paramScrollBar.getPageIncrement();
        break;
      case 2:
        int m = paramScrollBar.getPageIncrement();
        localSCROLLINFO.nPos = Math.max(localSCROLLINFO.nMin, localSCROLLINFO.nPos - m);
      }
      OS.SetScrollInfo(paramInt1, i, localSCROLLINFO, true);
    }
    else
    {
      i = callWindowProc(paramInt1, paramInt2, paramInt3, paramInt4);
      localLRESULT = i == 0 ? LRESULT.ZERO : new LRESULT(i);
    }
    paramScrollBar.wmScrollChild(paramInt3, paramInt4);
    return localLRESULT;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.Scrollable
 * JD-Core Version:    0.6.2
 */