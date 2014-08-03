package org.eclipse.swt.widgets;

import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.TCHAR;

public class Sash extends Control
{
  boolean dragging;
  int startX;
  int startY;
  int lastX;
  int lastY;
  static final int INCREMENT = 1;
  static final int PAGE_INCREMENT = 9;

  public Sash(Composite paramComposite, int paramInt)
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
    return OS.DefWindowProc(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  void createHandle()
  {
    super.createHandle();
    this.state |= 256;
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
      j += 64;
      k += 3;
    }
    else
    {
      j += 3;
      k += 64;
    }
    if (paramInt1 != -1)
      j = paramInt1 + i * 2;
    if (paramInt2 != -1)
      k = paramInt2 + i * 2;
    return new Point(j, k);
  }

  void drawBand(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((this.style & 0x10000) != 0)
      return;
    int i = this.parent.handle;
    byte[] arrayOfByte = { -86, 0, 85, 0, -86, 0, 85, 0, -86, 0, 85, 0, -86, 0, 85 };
    int j = OS.CreateBitmap(8, 8, 1, 1, arrayOfByte);
    int k = OS.CreatePatternBrush(j);
    int m = OS.GetDCEx(i, 0, 2);
    int n = OS.SelectObject(m, k);
    OS.PatBlt(m, paramInt1, paramInt2, paramInt3, paramInt4, 5898313);
    OS.SelectObject(m, n);
    OS.ReleaseDC(i, m);
    OS.DeleteObject(k);
    OS.DeleteObject(j);
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

  TCHAR windowClass()
  {
    return this.display.windowClass;
  }

  int windowProc()
  {
    return this.display.windowProc;
  }

  LRESULT WM_ERASEBKGND(int paramInt1, int paramInt2)
  {
    super.WM_ERASEBKGND(paramInt1, paramInt2);
    drawBackground(paramInt1);
    return LRESULT.ONE;
  }

  LRESULT WM_KEYDOWN(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_KEYDOWN(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    switch (paramInt1)
    {
    case 37:
    case 38:
    case 39:
    case 40:
      if (OS.GetKeyState(1) < 0)
        return localLRESULT;
      int i = OS.GetKeyState(17) < 0 ? 1 : 9;
      POINT localPOINT1 = new POINT();
      if ((this.style & 0x200) != 0)
      {
        if ((paramInt1 == 38) || (paramInt1 == 40))
          break;
        localPOINT1.x = (paramInt1 == 37 ? -i : i);
      }
      else
      {
        if ((paramInt1 == 37) || (paramInt1 == 39))
          break;
        localPOINT1.y = (paramInt1 == 38 ? -i : i);
      }
      int j = this.parent.handle;
      OS.MapWindowPoints(this.handle, j, localPOINT1, 1);
      RECT localRECT1 = new RECT();
      RECT localRECT2 = new RECT();
      OS.GetWindowRect(this.handle, localRECT1);
      int k = localRECT1.right - localRECT1.left;
      int m = localRECT1.bottom - localRECT1.top;
      OS.GetClientRect(j, localRECT2);
      int n = localRECT2.right - localRECT2.left;
      int i1 = localRECT2.bottom - localRECT2.top;
      int i2 = this.lastX;
      int i3 = this.lastY;
      if ((this.style & 0x200) != 0)
        i2 = Math.min(Math.max(0, localPOINT1.x - this.startX), n - k);
      else
        i3 = Math.min(Math.max(0, localPOINT1.y - this.startY), i1 - m);
      if ((i2 == this.lastX) && (i3 == this.lastY))
        return localLRESULT;
      POINT localPOINT2 = new POINT();
      localPOINT2.x = localPOINT1.x;
      localPOINT2.y = localPOINT1.y;
      OS.ClientToScreen(j, localPOINT2);
      if ((this.style & 0x200) != 0)
        localPOINT2.y += m / 2;
      else
        localPOINT2.x += k / 2;
      OS.SetCursorPos(localPOINT2.x, localPOINT2.y);
      Event localEvent = new Event();
      localEvent.x = i2;
      localEvent.y = i3;
      localEvent.width = k;
      localEvent.height = m;
      sendSelectionEvent(13, localEvent, true);
      if (isDisposed())
        return LRESULT.ZERO;
      if ((localEvent.doit) && ((this.style & 0x10000) != 0))
        setBounds(localEvent.x, localEvent.y, k, m);
      return localLRESULT;
    }
    return localLRESULT;
  }

  LRESULT WM_GETDLGCODE(int paramInt1, int paramInt2)
  {
    return new LRESULT(256);
  }

  LRESULT WM_LBUTTONDOWN(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_LBUTTONDOWN(paramInt1, paramInt2);
    if (localLRESULT == LRESULT.ZERO)
      return localLRESULT;
    int i = this.parent.handle;
    POINT localPOINT = new POINT();
    OS.POINTSTOPOINT(localPOINT, paramInt2);
    RECT localRECT = new RECT();
    OS.GetWindowRect(this.handle, localRECT);
    OS.MapWindowPoints(this.handle, 0, localPOINT, 1);
    this.startX = (localPOINT.x - localRECT.left);
    this.startY = (localPOINT.y - localRECT.top);
    OS.MapWindowPoints(0, i, localRECT, 2);
    this.lastX = localRECT.left;
    this.lastY = localRECT.top;
    int j = localRECT.right - localRECT.left;
    int k = localRECT.bottom - localRECT.top;
    Event localEvent = new Event();
    localEvent.x = this.lastX;
    localEvent.y = this.lastY;
    localEvent.width = j;
    localEvent.height = k;
    if ((this.style & 0x10000) == 0)
      localEvent.detail = 1;
    sendSelectionEvent(13, localEvent, true);
    if (isDisposed())
      return LRESULT.ZERO;
    if (localEvent.doit)
    {
      this.dragging = true;
      this.lastX = localEvent.x;
      this.lastY = localEvent.y;
      menuShell().bringToTop();
      if (isDisposed())
        return LRESULT.ZERO;
      if (OS.IsWinCE)
      {
        OS.UpdateWindow(i);
      }
      else
      {
        int m = 384;
        OS.RedrawWindow(i, null, 0, m);
      }
      drawBand(localEvent.x, localEvent.y, j, k);
      if ((this.style & 0x10000) != 0)
        setBounds(localEvent.x, localEvent.y, j, k);
    }
    return localLRESULT;
  }

  LRESULT WM_LBUTTONUP(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_LBUTTONUP(paramInt1, paramInt2);
    if (localLRESULT == LRESULT.ZERO)
      return localLRESULT;
    if (!this.dragging)
      return localLRESULT;
    this.dragging = false;
    RECT localRECT = new RECT();
    OS.GetWindowRect(this.handle, localRECT);
    int i = localRECT.right - localRECT.left;
    int j = localRECT.bottom - localRECT.top;
    Event localEvent = new Event();
    localEvent.x = this.lastX;
    localEvent.y = this.lastY;
    localEvent.width = i;
    localEvent.height = j;
    drawBand(localEvent.x, localEvent.y, i, j);
    sendSelectionEvent(13, localEvent, true);
    if (isDisposed())
      return localLRESULT;
    if ((localEvent.doit) && ((this.style & 0x10000) != 0))
      setBounds(localEvent.x, localEvent.y, i, j);
    return localLRESULT;
  }

  LRESULT WM_MOUSEMOVE(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_MOUSEMOVE(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    if ((!this.dragging) || ((paramInt1 & 0x1) == 0))
      return localLRESULT;
    POINT localPOINT = new POINT();
    OS.POINTSTOPOINT(localPOINT, paramInt2);
    int i = this.parent.handle;
    OS.MapWindowPoints(this.handle, i, localPOINT, 1);
    RECT localRECT1 = new RECT();
    RECT localRECT2 = new RECT();
    OS.GetWindowRect(this.handle, localRECT1);
    int j = localRECT1.right - localRECT1.left;
    int k = localRECT1.bottom - localRECT1.top;
    OS.GetClientRect(i, localRECT2);
    int m = this.lastX;
    int n = this.lastY;
    int i1;
    if ((this.style & 0x200) != 0)
    {
      i1 = localRECT2.right - localRECT2.left;
      m = Math.min(Math.max(0, localPOINT.x - this.startX), i1 - j);
    }
    else
    {
      i1 = localRECT2.bottom - localRECT2.top;
      n = Math.min(Math.max(0, localPOINT.y - this.startY), i1 - k);
    }
    if ((m == this.lastX) && (n == this.lastY))
      return localLRESULT;
    drawBand(this.lastX, this.lastY, j, k);
    Event localEvent = new Event();
    localEvent.x = m;
    localEvent.y = n;
    localEvent.width = j;
    localEvent.height = k;
    if ((this.style & 0x10000) == 0)
      localEvent.detail = 1;
    sendSelectionEvent(13, localEvent, true);
    if (isDisposed())
      return LRESULT.ZERO;
    if (localEvent.doit)
    {
      this.lastX = localEvent.x;
      this.lastY = localEvent.y;
    }
    if (OS.IsWinCE)
    {
      OS.UpdateWindow(i);
    }
    else
    {
      int i2 = 384;
      OS.RedrawWindow(i, null, 0, i2);
    }
    drawBand(this.lastX, this.lastY, j, k);
    if ((this.style & 0x10000) != 0)
      setBounds(this.lastX, this.lastY, j, k);
    return localLRESULT;
  }

  LRESULT WM_SETCURSOR(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_SETCURSOR(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    int i = (short)OS.LOWORD(paramInt2);
    if (i == 1)
    {
      int j = 0;
      if ((this.style & 0x100) != 0)
        j = OS.LoadCursor(0, 32645);
      else
        j = OS.LoadCursor(0, 32644);
      OS.SetCursor(j);
      return LRESULT.ONE;
    }
    return localLRESULT;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.Sash
 * JD-Core Version:    0.6.2
 */