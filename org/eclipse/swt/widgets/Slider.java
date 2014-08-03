package org.eclipse.swt.widgets;

import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.SCROLLINFO;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.WNDCLASS;

public class Slider extends Control
{
  int increment;
  int pageIncrement;
  boolean ignoreFocus;
  static final int ScrollBarProc = localWNDCLASS.lpfnWndProc;
  static final TCHAR ScrollBarClass = new TCHAR(0, "SCROLLBAR", true);

  static
  {
    WNDCLASS localWNDCLASS = new WNDCLASS();
    OS.GetClassInfo(0, ScrollBarClass, localWNDCLASS);
  }

  public Slider(Composite paramComposite, int paramInt)
  {
    super(paramComposite, checkStyle(paramInt));
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

  int callWindowProc(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.handle == 0)
      return 0;
    switch (paramInt2)
    {
    case 513:
    case 515:
      this.display.runDeferredEvents();
    case 514:
    }
    return OS.CallWindowProc(ScrollBarProc, paramInt1, paramInt2, paramInt3, paramInt4);
  }

  static int checkStyle(int paramInt)
  {
    return checkBits(paramInt, 256, 512, 0, 0, 0, 0);
  }

  public Point computeSize(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    checkWidget();
    int i = getBorderWidth();
    int j = i * 2;
    int k = i * 2;
    if ((this.style & 0x100) != 0)
    {
      j += OS.GetSystemMetrics(21) * 10;
      k += OS.GetSystemMetrics(3);
    }
    else
    {
      j += OS.GetSystemMetrics(2);
      k += OS.GetSystemMetrics(20) * 10;
    }
    if (paramInt1 != -1)
      j = paramInt1 + i * 2;
    if (paramInt2 != -1)
      k = paramInt2 + i * 2;
    return new Point(j, k);
  }

  void createWidget()
  {
    super.createWidget();
    this.increment = 1;
    this.pageIncrement = 10;
    SCROLLINFO localSCROLLINFO = new SCROLLINFO();
    localSCROLLINFO.cbSize = SCROLLINFO.sizeof;
    localSCROLLINFO.fMask = 23;
    localSCROLLINFO.nMax = 100;
    localSCROLLINFO.nPage = 11;
    OS.SetScrollInfo(this.handle, 2, localSCROLLINFO, true);
  }

  int defaultBackground()
  {
    return OS.GetSysColor(OS.COLOR_SCROLLBAR);
  }

  int defaultForeground()
  {
    return OS.GetSysColor(OS.COLOR_BTNFACE);
  }

  void enableWidget(boolean paramBoolean)
  {
    super.enableWidget(paramBoolean);
    if (!OS.IsWinCE)
    {
      int i = paramBoolean ? 0 : 3;
      OS.EnableScrollBar(this.handle, 2, i);
    }
    if (paramBoolean)
      this.state &= -9;
    else
      this.state |= 8;
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
    OS.GetScrollInfo(this.handle, 2, localSCROLLINFO);
    return localSCROLLINFO.nMax;
  }

  public int getMinimum()
  {
    checkWidget();
    SCROLLINFO localSCROLLINFO = new SCROLLINFO();
    localSCROLLINFO.cbSize = SCROLLINFO.sizeof;
    localSCROLLINFO.fMask = 1;
    OS.GetScrollInfo(this.handle, 2, localSCROLLINFO);
    return localSCROLLINFO.nMin;
  }

  public int getPageIncrement()
  {
    checkWidget();
    return this.pageIncrement;
  }

  public int getSelection()
  {
    checkWidget();
    SCROLLINFO localSCROLLINFO = new SCROLLINFO();
    localSCROLLINFO.cbSize = SCROLLINFO.sizeof;
    localSCROLLINFO.fMask = 4;
    OS.GetScrollInfo(this.handle, 2, localSCROLLINFO);
    return localSCROLLINFO.nPos;
  }

  public int getThumb()
  {
    checkWidget();
    SCROLLINFO localSCROLLINFO = new SCROLLINFO();
    localSCROLLINFO.cbSize = SCROLLINFO.sizeof;
    localSCROLLINFO.fMask = 2;
    OS.GetScrollInfo(this.handle, 2, localSCROLLINFO);
    if (localSCROLLINFO.nPage != 0)
      localSCROLLINFO.nPage -= 1;
    return localSCROLLINFO.nPage;
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

  void setBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    super.setBounds(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    if (OS.GetFocus() == this.handle)
    {
      this.ignoreFocus = true;
      OS.SendMessage(this.handle, 7, 0, 0);
      this.ignoreFocus = false;
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
    localSCROLLINFO.fMask = 9;
    OS.GetScrollInfo(this.handle, 2, localSCROLLINFO);
    if (paramInt - localSCROLLINFO.nMin - localSCROLLINFO.nPage < 1)
      return;
    localSCROLLINFO.nMax = paramInt;
    SetScrollInfo(this.handle, 2, localSCROLLINFO, true);
  }

  public void setMinimum(int paramInt)
  {
    checkWidget();
    if (paramInt < 0)
      return;
    SCROLLINFO localSCROLLINFO = new SCROLLINFO();
    localSCROLLINFO.cbSize = SCROLLINFO.sizeof;
    localSCROLLINFO.fMask = 9;
    OS.GetScrollInfo(this.handle, 2, localSCROLLINFO);
    if (localSCROLLINFO.nMax - paramInt - localSCROLLINFO.nPage < 1)
      return;
    localSCROLLINFO.nMin = paramInt;
    SetScrollInfo(this.handle, 2, localSCROLLINFO, true);
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
    if ((this.state & 0x8) != 0)
      paramBoolean = false;
    boolean bool = OS.SetScrollInfo(paramInt1, paramInt2, paramSCROLLINFO, paramBoolean);
    if ((this.state & 0x8) != 0)
    {
      OS.EnableWindow(this.handle, false);
      if (!OS.IsWinCE)
        OS.EnableScrollBar(this.handle, 2, 3);
    }
    if (OS.GetFocus() == this.handle)
    {
      this.ignoreFocus = true;
      OS.SendMessage(this.handle, 7, 0, 0);
      this.ignoreFocus = false;
    }
    return bool;
  }

  public void setSelection(int paramInt)
  {
    checkWidget();
    SCROLLINFO localSCROLLINFO = new SCROLLINFO();
    localSCROLLINFO.cbSize = SCROLLINFO.sizeof;
    localSCROLLINFO.fMask = 4;
    localSCROLLINFO.nPos = paramInt;
    SetScrollInfo(this.handle, 2, localSCROLLINFO, true);
  }

  public void setThumb(int paramInt)
  {
    checkWidget();
    if (paramInt < 1)
      return;
    SCROLLINFO localSCROLLINFO = new SCROLLINFO();
    localSCROLLINFO.cbSize = SCROLLINFO.sizeof;
    localSCROLLINFO.fMask = 11;
    OS.GetScrollInfo(this.handle, 2, localSCROLLINFO);
    localSCROLLINFO.nPage = paramInt;
    if (localSCROLLINFO.nPage != 0)
      localSCROLLINFO.nPage += 1;
    SetScrollInfo(this.handle, 2, localSCROLLINFO, true);
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
    SetScrollInfo(this.handle, 2, localSCROLLINFO, true);
  }

  int widgetExtStyle()
  {
    int i = super.widgetExtStyle();
    if ((this.style & 0x800) != 0)
      i &= -513;
    return i;
  }

  int widgetStyle()
  {
    int i = super.widgetStyle() | 0x10000;
    if ((this.style & 0x800) != 0)
      i &= -8388609;
    if ((this.style & 0x100) != 0)
      return i;
    return i | 0x1;
  }

  TCHAR windowClass()
  {
    return ScrollBarClass;
  }

  int windowProc()
  {
    return ScrollBarProc;
  }

  LRESULT WM_KEYDOWN(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_KEYDOWN(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    if ((this.style & 0x200) != 0)
      return localLRESULT;
    if ((this.style & 0x8000000) != 0)
      switch (paramInt1)
      {
      case 37:
      case 39:
        int i = paramInt1 == 37 ? 39 : 37;
        int j = callWindowProc(this.handle, 256, i, paramInt2);
        return new LRESULT(j);
      case 38:
      }
    return localLRESULT;
  }

  LRESULT WM_LBUTTONDBLCLK(int paramInt1, int paramInt2)
  {
    int i = OS.GetWindowLong(this.handle, -16);
    int j = i & 0xFFFEFFFF;
    OS.SetWindowLong(this.handle, -16, j);
    LRESULT localLRESULT = super.WM_LBUTTONDBLCLK(paramInt1, paramInt2);
    if (isDisposed())
      return LRESULT.ZERO;
    OS.SetWindowLong(this.handle, -16, i);
    if (localLRESULT == LRESULT.ZERO)
      return localLRESULT;
    if (!OS.IsWinCE)
    {
      if (OS.GetCapture() == this.handle)
        OS.ReleaseCapture();
      if (!sendMouseEvent(4, 1, this.handle, 514, paramInt1, paramInt2))
        return LRESULT.ZERO;
    }
    return localLRESULT;
  }

  LRESULT WM_LBUTTONDOWN(int paramInt1, int paramInt2)
  {
    int i = OS.GetWindowLong(this.handle, -16);
    int j = i & 0xFFFEFFFF;
    OS.SetWindowLong(this.handle, -16, j);
    LRESULT localLRESULT = super.WM_LBUTTONDOWN(paramInt1, paramInt2);
    if (isDisposed())
      return LRESULT.ZERO;
    OS.SetWindowLong(this.handle, -16, i);
    if (localLRESULT == LRESULT.ZERO)
      return localLRESULT;
    if (!OS.IsWinCE)
    {
      if (OS.GetCapture() == this.handle)
        OS.ReleaseCapture();
      if (!sendMouseEvent(4, 1, this.handle, 514, paramInt1, paramInt2))
        return LRESULT.ONE;
    }
    return localLRESULT;
  }

  LRESULT WM_SETFOCUS(int paramInt1, int paramInt2)
  {
    if (this.ignoreFocus)
      return null;
    return super.WM_SETFOCUS(paramInt1, paramInt2);
  }

  LRESULT wmScrollChild(int paramInt1, int paramInt2)
  {
    int i = OS.LOWORD(paramInt1);
    if (i == 8)
      return null;
    Event localEvent = new Event();
    SCROLLINFO localSCROLLINFO = new SCROLLINFO();
    localSCROLLINFO.cbSize = SCROLLINFO.sizeof;
    localSCROLLINFO.fMask = 21;
    OS.GetScrollInfo(this.handle, 2, localSCROLLINFO);
    localSCROLLINFO.fMask = 4;
    switch (i)
    {
    case 4:
      localEvent.detail = 0;
      localSCROLLINFO.nPos = localSCROLLINFO.nTrackPos;
      break;
    case 5:
      localEvent.detail = 1;
      localSCROLLINFO.nPos = localSCROLLINFO.nTrackPos;
      break;
    case 6:
      localEvent.detail = 16777223;
      localSCROLLINFO.nPos = localSCROLLINFO.nMin;
      break;
    case 7:
      localEvent.detail = 16777224;
      localSCROLLINFO.nPos = localSCROLLINFO.nMax;
      break;
    case 1:
      localEvent.detail = 16777218;
      localSCROLLINFO.nPos += this.increment;
      break;
    case 0:
      localEvent.detail = 16777217;
      localSCROLLINFO.nPos = Math.max(localSCROLLINFO.nMin, localSCROLLINFO.nPos - this.increment);
      break;
    case 3:
      localEvent.detail = 16777222;
      localSCROLLINFO.nPos += this.pageIncrement;
      break;
    case 2:
      localEvent.detail = 16777221;
      localSCROLLINFO.nPos = Math.max(localSCROLLINFO.nMin, localSCROLLINFO.nPos - this.pageIncrement);
    }
    OS.SetScrollInfo(this.handle, 2, localSCROLLINFO, true);
    sendSelectionEvent(13, localEvent, true);
    return null;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.Slider
 * JD-Core Version:    0.6.2
 */