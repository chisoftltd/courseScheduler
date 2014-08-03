package org.eclipse.swt.widgets;

import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.WNDCLASS;

public class Scale extends Control
{
  boolean ignoreResize;
  boolean ignoreSelection;
  static final int TrackBarProc;
  static final TCHAR TrackBarClass = new TCHAR(0, "msctls_trackbar32", true);

  static
  {
    WNDCLASS localWNDCLASS = new WNDCLASS();
    OS.GetClassInfo(0, TrackBarClass, localWNDCLASS);
    TrackBarProc = localWNDCLASS.lpfnWndProc;
    int i = OS.GetModuleHandle(null);
    int j = OS.GetProcessHeap();
    localWNDCLASS.hInstance = i;
    localWNDCLASS.style &= -16385;
    localWNDCLASS.style |= 8;
    int k = TrackBarClass.length() * TCHAR.sizeof;
    int m = OS.HeapAlloc(j, 8, k);
    OS.MoveMemory(m, TrackBarClass, k);
    localWNDCLASS.lpszClassName = m;
    OS.RegisterClass(localWNDCLASS);
    OS.HeapFree(j, 0, m);
  }

  public Scale(Composite paramComposite, int paramInt)
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
    return OS.CallWindowProc(TrackBarProc, paramInt1, paramInt2, paramInt3, paramInt4);
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
    RECT localRECT = new RECT();
    OS.SendMessage(this.handle, 1049, 0, localRECT);
    int m;
    if ((this.style & 0x100) != 0)
    {
      j += OS.GetSystemMetrics(21) * 10;
      m = OS.GetSystemMetrics(3);
      k += localRECT.top * 2 + m + m / 3;
    }
    else
    {
      m = OS.GetSystemMetrics(2);
      j += localRECT.left * 2 + m + m / 3;
      k += OS.GetSystemMetrics(20) * 10;
    }
    if (paramInt1 != -1)
      j = paramInt1 + i * 2;
    if (paramInt2 != -1)
      k = paramInt2 + i * 2;
    return new Point(j, k);
  }

  void createHandle()
  {
    super.createHandle();
    this.state |= 768;
    OS.SendMessage(this.handle, 1032, 0, 100);
    OS.SendMessage(this.handle, 1045, 0, 10);
    OS.SendMessage(this.handle, 1044, 10, 0);
  }

  int defaultForeground()
  {
    return OS.GetSysColor(OS.COLOR_BTNFACE);
  }

  public int getIncrement()
  {
    checkWidget();
    return OS.SendMessage(this.handle, 1048, 0, 0);
  }

  public int getMaximum()
  {
    checkWidget();
    return OS.SendMessage(this.handle, 1026, 0, 0);
  }

  public int getMinimum()
  {
    checkWidget();
    return OS.SendMessage(this.handle, 1025, 0, 0);
  }

  public int getPageIncrement()
  {
    checkWidget();
    return OS.SendMessage(this.handle, 1046, 0, 0);
  }

  public int getSelection()
  {
    checkWidget();
    return OS.SendMessage(this.handle, 1024, 0, 0);
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

  void setBackgroundImage(int paramInt)
  {
    super.setBackgroundImage(paramInt);
    this.ignoreResize = true;
    OS.SendMessage(this.handle, 5, 0, 0);
    this.ignoreResize = false;
  }

  void setBackgroundPixel(int paramInt)
  {
    super.setBackgroundPixel(paramInt);
    this.ignoreResize = true;
    OS.SendMessage(this.handle, 5, 0, 0);
    this.ignoreResize = false;
  }

  void setBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, boolean paramBoolean)
  {
    paramInt5 &= -33;
    super.setBounds(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, true);
  }

  public void setIncrement(int paramInt)
  {
    checkWidget();
    if (paramInt < 1)
      return;
    int i = OS.SendMessage(this.handle, 1025, 0, 0);
    int j = OS.SendMessage(this.handle, 1026, 0, 0);
    if (paramInt > j - i)
      return;
    OS.SendMessage(this.handle, 1047, 0, paramInt);
  }

  public void setMaximum(int paramInt)
  {
    checkWidget();
    int i = OS.SendMessage(this.handle, 1025, 0, 0);
    if ((i >= 0) && (i < paramInt))
      OS.SendMessage(this.handle, 1032, 1, paramInt);
  }

  public void setMinimum(int paramInt)
  {
    checkWidget();
    int i = OS.SendMessage(this.handle, 1026, 0, 0);
    if ((paramInt >= 0) && (paramInt < i))
      OS.SendMessage(this.handle, 1031, 1, paramInt);
  }

  public void setPageIncrement(int paramInt)
  {
    checkWidget();
    if (paramInt < 1)
      return;
    int i = OS.SendMessage(this.handle, 1025, 0, 0);
    int j = OS.SendMessage(this.handle, 1026, 0, 0);
    if (paramInt > j - i)
      return;
    OS.SendMessage(this.handle, 1045, 0, paramInt);
    OS.SendMessage(this.handle, 1044, paramInt, 0);
  }

  public void setSelection(int paramInt)
  {
    checkWidget();
    OS.SendMessage(this.handle, 1029, 1, paramInt);
  }

  int widgetStyle()
  {
    int i = super.widgetStyle() | 0x10000 | 0x8 | 0x1;
    if ((this.style & 0x100) != 0)
      return i | 0x400;
    return i | 0x2;
  }

  TCHAR windowClass()
  {
    return TrackBarClass;
  }

  int windowProc()
  {
    return TrackBarProc;
  }

  LRESULT WM_MOUSEWHEEL(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_MOUSEWHEEL(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    int i = OS.SendMessage(this.handle, 1024, 0, 0);
    this.ignoreSelection = true;
    int j = callWindowProc(this.handle, 522, paramInt1, paramInt2);
    this.ignoreSelection = false;
    int k = OS.SendMessage(this.handle, 1024, 0, 0);
    if (i != k)
      sendSelectionEvent(13, null, true);
    return new LRESULT(j);
  }

  LRESULT WM_PAINT(int paramInt1, int paramInt2)
  {
    int i = findBackgroundControl() != null ? 1 : 0;
    if ((i == 0) && (OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()))
    {
      Control localControl = findThemeControl();
      i = localControl != null ? 1 : 0;
    }
    if (i != 0)
    {
      int j = (getDrawing()) && (OS.IsWindowVisible(this.handle)) ? 1 : 0;
      if (j != 0)
        OS.SendMessage(this.handle, 11, 0, 0);
      this.ignoreResize = true;
      OS.SendMessage(this.handle, 5, 0, 0);
      this.ignoreResize = false;
      if (j != 0)
      {
        OS.SendMessage(this.handle, 11, 1, 0);
        OS.InvalidateRect(this.handle, null, false);
      }
    }
    return super.WM_PAINT(paramInt1, paramInt2);
  }

  LRESULT WM_SIZE(int paramInt1, int paramInt2)
  {
    if (this.ignoreResize)
      return null;
    return super.WM_SIZE(paramInt1, paramInt2);
  }

  LRESULT wmScrollChild(int paramInt1, int paramInt2)
  {
    int i = OS.LOWORD(paramInt1);
    switch (i)
    {
    case 4:
    case 8:
      return null;
    case 5:
    case 6:
    case 7:
    }
    if (!this.ignoreSelection)
    {
      Event localEvent = new Event();
      sendSelectionEvent(13, localEvent, true);
    }
    return null;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.Scale
 * JD-Core Version:    0.6.2
 */