package org.eclipse.swt.widgets;

import org.eclipse.swt.events.ExpandListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.GCData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.win32.LOGFONT;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.NONCLIENTMETRICS;
import org.eclipse.swt.internal.win32.NONCLIENTMETRICSA;
import org.eclipse.swt.internal.win32.NONCLIENTMETRICSW;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.PAINTSTRUCT;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.SCROLLINFO;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.TEXTMETRIC;
import org.eclipse.swt.internal.win32.TEXTMETRICA;
import org.eclipse.swt.internal.win32.TEXTMETRICW;

public class ExpandBar extends Composite
{
  ExpandItem[] items;
  int itemCount;
  ExpandItem focusItem;
  int spacing = 4;
  int yCurrentScroll;
  int hFont;

  public ExpandBar(Composite paramComposite, int paramInt)
  {
    super(paramComposite, checkStyle(paramInt));
  }

  public void addExpandListener(ExpandListener paramExpandListener)
  {
    checkWidget();
    if (paramExpandListener == null)
      error(4);
    TypedListener localTypedListener = new TypedListener(paramExpandListener);
    addListener(17, localTypedListener);
    addListener(18, localTypedListener);
  }

  int callWindowProc(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.handle == 0)
      return 0;
    return OS.DefWindowProc(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  protected void checkSubclass()
  {
    if (!isValidSubclass())
      error(43);
  }

  static int checkStyle(int paramInt)
  {
    paramInt &= -257;
    return paramInt | 0x40000;
  }

  public Point computeSize(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    checkWidget();
    int i = 0;
    int j = 0;
    if (((paramInt1 == -1) || (paramInt2 == -1)) && (this.itemCount > 0))
    {
      int k = OS.GetDC(this.handle);
      int m = 0;
      if (isAppThemed())
        m = this.display.hExplorerBarTheme();
      int n = 0;
      int i1 = 0;
      Object localObject;
      if (m == 0)
      {
        if (this.hFont != 0)
        {
          n = this.hFont;
        }
        else if (!OS.IsWinCE)
        {
          NONCLIENTMETRICSA localNONCLIENTMETRICSA = OS.IsUnicode ? new NONCLIENTMETRICSW() : new NONCLIENTMETRICSA();
          localNONCLIENTMETRICSA.cbSize = NONCLIENTMETRICS.sizeof;
          if (OS.SystemParametersInfo(41, 0, localNONCLIENTMETRICSA, 0))
          {
            localObject = OS.IsUnicode ? ((NONCLIENTMETRICSW)localNONCLIENTMETRICSA).lfCaptionFont : ((NONCLIENTMETRICSA)localNONCLIENTMETRICSA).lfCaptionFont;
            n = OS.CreateFontIndirect((LOGFONT)localObject);
          }
        }
        if (n != 0)
          i1 = OS.SelectObject(k, n);
      }
      i += this.spacing;
      for (int i2 = 0; i2 < this.itemCount; i2++)
      {
        localObject = this.items[i2];
        i += ((ExpandItem)localObject).getHeaderHeight();
        if (((ExpandItem)localObject).expanded)
          i += ((ExpandItem)localObject).height;
        i += this.spacing;
        j = Math.max(j, ((ExpandItem)localObject).getPreferredWidth(m, k));
      }
      if (n != 0)
      {
        OS.SelectObject(k, i1);
        if (n != this.hFont)
          OS.DeleteObject(n);
      }
      OS.ReleaseDC(this.handle, k);
    }
    if (j == 0)
      j = 64;
    if (i == 0)
      i = 64;
    if (paramInt1 != -1)
      j = paramInt1;
    if (paramInt2 != -1)
      i = paramInt2;
    Rectangle localRectangle = computeTrim(0, 0, j, i);
    return new Point(localRectangle.width, localRectangle.height);
  }

  void createHandle()
  {
    super.createHandle();
    this.state &= -3;
    this.state |= 8192;
  }

  void createItem(ExpandItem paramExpandItem, int paramInt1, int paramInt2)
  {
    if ((paramInt2 < 0) || (paramInt2 > this.itemCount))
      error(6);
    if (this.itemCount == this.items.length)
    {
      localObject = new ExpandItem[this.itemCount + 4];
      System.arraycopy(this.items, 0, localObject, 0, this.items.length);
      this.items = ((ExpandItem[])localObject);
    }
    System.arraycopy(this.items, paramInt2, this.items, paramInt2 + 1, this.itemCount - paramInt2);
    this.items[paramInt2] = paramExpandItem;
    this.itemCount += 1;
    if (this.focusItem == null)
      this.focusItem = paramExpandItem;
    Object localObject = new RECT();
    OS.GetWindowRect(this.handle, (RECT)localObject);
    paramExpandItem.width = Math.max(0, ((RECT)localObject).right - ((RECT)localObject).left - this.spacing * 2);
    layoutItems(paramInt2, true);
  }

  void createWidget()
  {
    super.createWidget();
    this.items = new ExpandItem[4];
    if (!isAppThemed())
      this.backgroundMode = 1;
  }

  int defaultBackground()
  {
    if (!isAppThemed())
      return OS.GetSysColor(OS.COLOR_WINDOW);
    return super.defaultBackground();
  }

  void destroyItem(ExpandItem paramExpandItem)
  {
    for (int i = 0; i < this.itemCount; i++)
      if (this.items[i] == paramExpandItem)
        break;
    if (i == this.itemCount)
      return;
    if (paramExpandItem == this.focusItem)
    {
      int j = i > 0 ? i - 1 : 1;
      if (j < this.itemCount)
      {
        this.focusItem = this.items[j];
        this.focusItem.redraw(true);
      }
      else
      {
        this.focusItem = null;
      }
    }
    System.arraycopy(this.items, i + 1, this.items, i, --this.itemCount - i);
    this.items[this.itemCount] = null;
    paramExpandItem.redraw(true);
    layoutItems(i, true);
  }

  void drawThemeBackground(int paramInt1, int paramInt2, RECT paramRECT)
  {
    RECT localRECT = new RECT();
    OS.GetClientRect(this.handle, localRECT);
    OS.MapWindowPoints(this.handle, paramInt2, localRECT, 2);
    OS.DrawThemeBackground(this.display.hExplorerBarTheme(), paramInt1, 5, 0, localRECT, null);
  }

  void drawWidget(GC paramGC, RECT paramRECT)
  {
    int i = 0;
    if (isAppThemed())
      i = this.display.hExplorerBarTheme();
    if (i != 0)
    {
      RECT localRECT = new RECT();
      OS.GetClientRect(this.handle, localRECT);
      OS.DrawThemeBackground(i, paramGC.handle, 1, 0, localRECT, paramRECT);
    }
    else
    {
      drawBackground(paramGC.handle);
    }
    int j = 0;
    if (this.handle == OS.GetFocus())
    {
      k = OS.SendMessage(this.handle, 297, 0, 0);
      j = (k & 0x1) == 0 ? 1 : 0;
    }
    int k = 0;
    int m = 0;
    Object localObject;
    if (i == 0)
    {
      if (this.hFont != 0)
      {
        k = this.hFont;
      }
      else if (!OS.IsWinCE)
      {
        NONCLIENTMETRICSA localNONCLIENTMETRICSA = OS.IsUnicode ? new NONCLIENTMETRICSW() : new NONCLIENTMETRICSA();
        localNONCLIENTMETRICSA.cbSize = NONCLIENTMETRICS.sizeof;
        if (OS.SystemParametersInfo(41, 0, localNONCLIENTMETRICSA, 0))
        {
          localObject = OS.IsUnicode ? ((NONCLIENTMETRICSW)localNONCLIENTMETRICSA).lfCaptionFont : ((NONCLIENTMETRICSA)localNONCLIENTMETRICSA).lfCaptionFont;
          k = OS.CreateFontIndirect((LOGFONT)localObject);
        }
      }
      if (k != 0)
        m = OS.SelectObject(paramGC.handle, k);
      if (this.foreground != -1)
        OS.SetTextColor(paramGC.handle, this.foreground);
    }
    for (int n = 0; n < this.itemCount; n++)
    {
      localObject = this.items[n];
      ((ExpandItem)localObject).drawItem(paramGC, i, paramRECT, (localObject == this.focusItem) && (j != 0));
    }
    if (k != 0)
    {
      OS.SelectObject(paramGC.handle, m);
      if (k != this.hFont)
        OS.DeleteObject(k);
    }
  }

  Control findBackgroundControl()
  {
    Object localObject = super.findBackgroundControl();
    if ((!isAppThemed()) && (localObject == null))
      localObject = this;
    return localObject;
  }

  Control findThemeControl()
  {
    return isAppThemed() ? this : super.findThemeControl();
  }

  int getBandHeight()
  {
    if (this.hFont == 0)
      return 24;
    int i = OS.GetDC(this.handle);
    int j = OS.SelectObject(i, this.hFont);
    TEXTMETRICA localTEXTMETRICA = OS.IsUnicode ? new TEXTMETRICW() : new TEXTMETRICA();
    OS.GetTextMetrics(i, localTEXTMETRICA);
    OS.SelectObject(i, j);
    OS.ReleaseDC(this.handle, i);
    return Math.max(24, localTEXTMETRICA.tmHeight + 4);
  }

  public ExpandItem getItem(int paramInt)
  {
    checkWidget();
    if ((paramInt < 0) || (paramInt >= this.itemCount))
      error(6);
    return this.items[paramInt];
  }

  public int getItemCount()
  {
    checkWidget();
    return this.itemCount;
  }

  public ExpandItem[] getItems()
  {
    checkWidget();
    ExpandItem[] arrayOfExpandItem = new ExpandItem[this.itemCount];
    System.arraycopy(this.items, 0, arrayOfExpandItem, 0, this.itemCount);
    return arrayOfExpandItem;
  }

  public int getSpacing()
  {
    checkWidget();
    return this.spacing;
  }

  public int indexOf(ExpandItem paramExpandItem)
  {
    checkWidget();
    if (paramExpandItem == null)
      error(4);
    for (int i = 0; i < this.itemCount; i++)
      if (this.items[i] == paramExpandItem)
        return i;
    return -1;
  }

  boolean isAppThemed()
  {
    if (this.background != -1)
      return false;
    if (this.foreground != -1)
      return false;
    if (this.hFont != 0)
      return false;
    return (OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed());
  }

  void layoutItems(int paramInt, boolean paramBoolean)
  {
    if (paramInt < this.itemCount)
    {
      int i = this.spacing - this.yCurrentScroll;
      ExpandItem localExpandItem;
      for (int j = 0; j < paramInt; j++)
      {
        localExpandItem = this.items[j];
        if (localExpandItem.expanded)
          i += localExpandItem.height;
        i += localExpandItem.getHeaderHeight() + this.spacing;
      }
      for (j = paramInt; j < this.itemCount; j++)
      {
        localExpandItem = this.items[j];
        localExpandItem.setBounds(this.spacing, i, 0, 0, true, false);
        if (localExpandItem.expanded)
          i += localExpandItem.height;
        i += localExpandItem.getHeaderHeight() + this.spacing;
      }
    }
    if (paramBoolean)
      setScrollbar();
  }

  void releaseChildren(boolean paramBoolean)
  {
    if (this.items != null)
    {
      for (int i = 0; i < this.items.length; i++)
      {
        ExpandItem localExpandItem = this.items[i];
        if ((localExpandItem != null) && (!localExpandItem.isDisposed()))
          localExpandItem.release(false);
      }
      this.items = null;
    }
    this.focusItem = null;
    super.releaseChildren(paramBoolean);
  }

  public void removeExpandListener(ExpandListener paramExpandListener)
  {
    checkWidget();
    if (paramExpandListener == null)
      error(4);
    if (this.eventTable == null)
      return;
    this.eventTable.unhook(17, paramExpandListener);
    this.eventTable.unhook(18, paramExpandListener);
  }

  void reskinChildren(int paramInt)
  {
    if (this.items != null)
      for (int i = 0; i < this.items.length; i++)
      {
        ExpandItem localExpandItem = this.items[i];
        if (localExpandItem != null)
          localExpandItem.reskin(paramInt);
      }
    super.reskinChildren(paramInt);
  }

  void setBackgroundPixel(int paramInt)
  {
    super.setBackgroundPixel(paramInt);
    if (!OS.IsWinCE)
    {
      int i = 1157;
      OS.RedrawWindow(this.handle, null, 0, i);
    }
  }

  public void setFont(Font paramFont)
  {
    super.setFont(paramFont);
    this.hFont = (paramFont != null ? paramFont.handle : 0);
    layoutItems(0, true);
  }

  void setForegroundPixel(int paramInt)
  {
    super.setForegroundPixel(paramInt);
    if (!OS.IsWinCE)
    {
      int i = 1157;
      OS.RedrawWindow(this.handle, null, 0, i);
    }
  }

  void setScrollbar()
  {
    if (this.itemCount == 0)
      return;
    if ((this.style & 0x200) == 0)
      return;
    RECT localRECT = new RECT();
    OS.GetClientRect(this.handle, localRECT);
    int i = localRECT.bottom - localRECT.top;
    ExpandItem localExpandItem = this.items[(this.itemCount - 1)];
    int j = localExpandItem.y + getBandHeight() + this.spacing;
    if (localExpandItem.expanded)
      j += localExpandItem.height;
    if ((this.yCurrentScroll > 0) && (i > j))
    {
      this.yCurrentScroll = Math.max(0, this.yCurrentScroll + j - i);
      layoutItems(0, false);
    }
    j += this.yCurrentScroll;
    SCROLLINFO localSCROLLINFO = new SCROLLINFO();
    localSCROLLINFO.cbSize = SCROLLINFO.sizeof;
    localSCROLLINFO.fMask = 7;
    localSCROLLINFO.nMin = 0;
    localSCROLLINFO.nMax = j;
    localSCROLLINFO.nPage = i;
    localSCROLLINFO.nPos = Math.min(this.yCurrentScroll, localSCROLLINFO.nMax);
    if (localSCROLLINFO.nPage != 0)
      localSCROLLINFO.nPage += 1;
    OS.SetScrollInfo(this.handle, 1, localSCROLLINFO, true);
  }

  public void setSpacing(int paramInt)
  {
    checkWidget();
    if (paramInt < 0)
      return;
    if (paramInt == this.spacing)
      return;
    this.spacing = paramInt;
    RECT localRECT = new RECT();
    OS.GetClientRect(this.handle, localRECT);
    int i = Math.max(0, localRECT.right - localRECT.left - paramInt * 2);
    for (int j = 0; j < this.itemCount; j++)
    {
      ExpandItem localExpandItem = this.items[j];
      if (localExpandItem.width != i)
        localExpandItem.setBounds(0, 0, i, localExpandItem.height, false, true);
    }
    layoutItems(0, true);
    OS.InvalidateRect(this.handle, null, true);
  }

  void showItem(ExpandItem paramExpandItem)
  {
    Control localControl = paramExpandItem.control;
    if ((localControl != null) && (!localControl.isDisposed()))
      localControl.setVisible(paramExpandItem.expanded);
    paramExpandItem.redraw(true);
    int i = indexOf(paramExpandItem);
    layoutItems(i + 1, true);
  }

  void showFocus(boolean paramBoolean)
  {
    RECT localRECT = new RECT();
    OS.GetClientRect(this.handle, localRECT);
    int i = localRECT.bottom - localRECT.top;
    int j = 0;
    if (paramBoolean)
    {
      if (this.focusItem.y < 0)
        j = Math.min(this.yCurrentScroll, -this.focusItem.y);
    }
    else
    {
      int k = this.focusItem.y + getBandHeight();
      if ((this.focusItem.expanded) && (i >= getBandHeight() + this.focusItem.height))
        k += this.focusItem.height;
      if (k > i)
        j = i - k;
    }
    if (j != 0)
    {
      this.yCurrentScroll = Math.max(0, this.yCurrentScroll - j);
      if ((this.style & 0x200) != 0)
      {
        SCROLLINFO localSCROLLINFO = new SCROLLINFO();
        localSCROLLINFO.cbSize = SCROLLINFO.sizeof;
        localSCROLLINFO.fMask = 4;
        localSCROLLINFO.nPos = this.yCurrentScroll;
        OS.SetScrollInfo(this.handle, 1, localSCROLLINFO, true);
      }
      OS.ScrollWindowEx(this.handle, 0, j, null, null, 0, null, 3);
      for (int m = 0; m < this.itemCount; m++)
        this.items[m].y += j;
    }
  }

  TCHAR windowClass()
  {
    return this.display.windowClass;
  }

  int windowProc()
  {
    return this.display.windowProc;
  }

  LRESULT WM_KEYDOWN(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_KEYDOWN(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    if (this.focusItem == null)
      return localLRESULT;
    int i;
    switch (paramInt1)
    {
    case 13:
    case 32:
      Event localEvent = new Event();
      localEvent.item = this.focusItem;
      sendEvent(this.focusItem.expanded ? 18 : 17, localEvent);
      this.focusItem.expanded = (!this.focusItem.expanded);
      showItem(this.focusItem);
      return LRESULT.ZERO;
    case 38:
      i = indexOf(this.focusItem);
      if (i > 0)
      {
        this.focusItem.redraw(true);
        this.focusItem = this.items[(i - 1)];
        this.focusItem.redraw(true);
        showFocus(true);
        return LRESULT.ZERO;
      }
      break;
    case 40:
      i = indexOf(this.focusItem);
      if (i < this.itemCount - 1)
      {
        this.focusItem.redraw(true);
        this.focusItem = this.items[(i + 1)];
        this.focusItem.redraw(true);
        showFocus(false);
        return LRESULT.ZERO;
      }
      break;
    }
    return localLRESULT;
  }

  LRESULT WM_KILLFOCUS(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_KILLFOCUS(paramInt1, paramInt2);
    if (this.focusItem != null)
      this.focusItem.redraw(true);
    return localLRESULT;
  }

  LRESULT WM_LBUTTONDOWN(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_LBUTTONDOWN(paramInt1, paramInt2);
    if (localLRESULT == LRESULT.ZERO)
      return localLRESULT;
    int i = OS.GET_X_LPARAM(paramInt2);
    int j = OS.GET_Y_LPARAM(paramInt2);
    for (int k = 0; k < this.itemCount; k++)
    {
      ExpandItem localExpandItem = this.items[k];
      boolean bool = localExpandItem.isHover(i, j);
      if ((bool) && (this.focusItem != localExpandItem))
      {
        this.focusItem.redraw(true);
        this.focusItem = localExpandItem;
        this.focusItem.redraw(true);
        forceFocus();
        break;
      }
    }
    return localLRESULT;
  }

  LRESULT WM_LBUTTONUP(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_LBUTTONUP(paramInt1, paramInt2);
    if (localLRESULT == LRESULT.ZERO)
      return localLRESULT;
    if (this.focusItem == null)
      return localLRESULT;
    int i = OS.GET_X_LPARAM(paramInt2);
    int j = OS.GET_Y_LPARAM(paramInt2);
    boolean bool = this.focusItem.isHover(i, j);
    if (bool)
    {
      Event localEvent = new Event();
      localEvent.item = this.focusItem;
      sendEvent(this.focusItem.expanded ? 18 : 17, localEvent);
      this.focusItem.expanded = (!this.focusItem.expanded);
      showItem(this.focusItem);
    }
    return localLRESULT;
  }

  LRESULT WM_MOUSELEAVE(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_MOUSELEAVE(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    for (int i = 0; i < this.itemCount; i++)
    {
      ExpandItem localExpandItem = this.items[i];
      if (localExpandItem.hover)
      {
        localExpandItem.hover = false;
        localExpandItem.redraw(false);
        break;
      }
    }
    return localLRESULT;
  }

  LRESULT WM_MOUSEMOVE(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_MOUSEMOVE(paramInt1, paramInt2);
    if (localLRESULT == LRESULT.ZERO)
      return localLRESULT;
    int i = OS.GET_X_LPARAM(paramInt2);
    int j = OS.GET_Y_LPARAM(paramInt2);
    for (int k = 0; k < this.itemCount; k++)
    {
      ExpandItem localExpandItem = this.items[k];
      boolean bool = localExpandItem.isHover(i, j);
      if (localExpandItem.hover != bool)
      {
        localExpandItem.hover = bool;
        localExpandItem.redraw(false);
      }
    }
    return localLRESULT;
  }

  LRESULT WM_MOUSEWHEEL(int paramInt1, int paramInt2)
  {
    return wmScrollWheel(true, paramInt1, paramInt2);
  }

  LRESULT WM_PAINT(int paramInt1, int paramInt2)
  {
    PAINTSTRUCT localPAINTSTRUCT = new PAINTSTRUCT();
    GCData localGCData = new GCData();
    localGCData.ps = localPAINTSTRUCT;
    localGCData.hwnd = this.handle;
    GC localGC = new_GC(localGCData);
    if (localGC != null)
    {
      int i = localPAINTSTRUCT.right - localPAINTSTRUCT.left;
      int j = localPAINTSTRUCT.bottom - localPAINTSTRUCT.top;
      if ((i != 0) && (j != 0))
      {
        RECT localRECT = new RECT();
        OS.SetRect(localRECT, localPAINTSTRUCT.left, localPAINTSTRUCT.top, localPAINTSTRUCT.right, localPAINTSTRUCT.bottom);
        drawWidget(localGC, localRECT);
        if ((hooks(9)) || (filters(9)))
        {
          Event localEvent = new Event();
          localEvent.gc = localGC;
          localEvent.x = localRECT.left;
          localEvent.y = localRECT.top;
          localEvent.width = i;
          localEvent.height = j;
          sendEvent(9, localEvent);
          localEvent.gc = null;
        }
      }
      localGC.dispose();
    }
    return LRESULT.ZERO;
  }

  LRESULT WM_PRINTCLIENT(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_PRINTCLIENT(paramInt1, paramInt2);
    RECT localRECT = new RECT();
    OS.GetClientRect(this.handle, localRECT);
    GCData localGCData = new GCData();
    localGCData.device = this.display;
    localGCData.foreground = getForegroundPixel();
    GC localGC = GC.win32_new(paramInt1, localGCData);
    drawWidget(localGC, localRECT);
    localGC.dispose();
    return localLRESULT;
  }

  LRESULT WM_SETCURSOR(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_SETCURSOR(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    int i = (short)OS.LOWORD(paramInt2);
    if (i == 1)
      for (int j = 0; j < this.itemCount; j++)
      {
        ExpandItem localExpandItem = this.items[j];
        if (localExpandItem.hover)
        {
          int k = OS.LoadCursor(0, 32649);
          OS.SetCursor(k);
          return LRESULT.ONE;
        }
      }
    return localLRESULT;
  }

  LRESULT WM_SETFOCUS(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_SETFOCUS(paramInt1, paramInt2);
    if (this.focusItem != null)
      this.focusItem.redraw(true);
    return localLRESULT;
  }

  LRESULT WM_SIZE(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_SIZE(paramInt1, paramInt2);
    RECT localRECT = new RECT();
    OS.GetClientRect(this.handle, localRECT);
    int i = Math.max(0, localRECT.right - localRECT.left - this.spacing * 2);
    for (int j = 0; j < this.itemCount; j++)
    {
      ExpandItem localExpandItem = this.items[j];
      if (localExpandItem.width != i)
        localExpandItem.setBounds(0, 0, i, localExpandItem.height, false, true);
    }
    setScrollbar();
    OS.InvalidateRect(this.handle, null, true);
    return localLRESULT;
  }

  LRESULT wmScroll(ScrollBar paramScrollBar, boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    LRESULT localLRESULT = super.wmScroll(paramScrollBar, true, paramInt1, paramInt2, paramInt3, paramInt4);
    SCROLLINFO localSCROLLINFO = new SCROLLINFO();
    localSCROLLINFO.cbSize = SCROLLINFO.sizeof;
    localSCROLLINFO.fMask = 4;
    OS.GetScrollInfo(this.handle, 1, localSCROLLINFO);
    int i = this.yCurrentScroll - localSCROLLINFO.nPos;
    OS.ScrollWindowEx(this.handle, 0, i, null, null, 0, null, 3);
    this.yCurrentScroll = localSCROLLINFO.nPos;
    if (i != 0)
      for (int j = 0; j < this.itemCount; j++)
        this.items[j].y += i;
    return localLRESULT;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.ExpandBar
 * JD-Core Version:    0.6.2
 */