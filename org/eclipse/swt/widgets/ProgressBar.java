package org.eclipse.swt.widgets;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.WNDCLASS;

public class ProgressBar extends Control
{
  static final int DELAY = 100;
  static final int TIMER_ID = 100;
  static final int MINIMUM_WIDTH = 100;
  static final int ProgressBarProc;
  static final TCHAR ProgressBarClass = new TCHAR(0, "msctls_progress32", true);

  static
  {
    WNDCLASS localWNDCLASS = new WNDCLASS();
    OS.GetClassInfo(0, ProgressBarClass, localWNDCLASS);
    ProgressBarProc = localWNDCLASS.lpfnWndProc;
    int i = OS.GetModuleHandle(null);
    int j = OS.GetProcessHeap();
    localWNDCLASS.hInstance = i;
    localWNDCLASS.style &= -16385;
    localWNDCLASS.style |= 8;
    int k = ProgressBarClass.length() * TCHAR.sizeof;
    int m = OS.HeapAlloc(j, 8, k);
    OS.MoveMemory(m, ProgressBarClass, k);
    localWNDCLASS.lpszClassName = m;
    OS.RegisterClass(localWNDCLASS);
    OS.HeapFree(j, 0, m);
  }

  public ProgressBar(Composite paramComposite, int paramInt)
  {
    super(paramComposite, checkStyle(paramInt));
  }

  int callWindowProc(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.handle == 0)
      return 0;
    return OS.CallWindowProc(ProgressBarProc, paramInt1, paramInt2, paramInt3, paramInt4);
  }

  static int checkStyle(int paramInt)
  {
    paramInt |= 524288;
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

  void createHandle()
  {
    super.createHandle();
    startTimer();
  }

  int defaultForeground()
  {
    return OS.GetSysColor(OS.COLOR_HIGHLIGHT);
  }

  public int getMaximum()
  {
    checkWidget();
    return OS.SendMessage(this.handle, 1031, 0, 0);
  }

  public int getMinimum()
  {
    checkWidget();
    return OS.SendMessage(this.handle, 1031, 1, 0);
  }

  public int getSelection()
  {
    checkWidget();
    return OS.SendMessage(this.handle, 1032, 0, 0);
  }

  public int getState()
  {
    checkWidget();
    if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(6, 0)))
    {
      int i = OS.SendMessage(this.handle, 1041, 0, 0);
      switch (i)
      {
      case 1:
        return 0;
      case 2:
        return 1;
      case 3:
        return 4;
      }
    }
    return 0;
  }

  void releaseWidget()
  {
    super.releaseWidget();
    stopTimer();
  }

  void startTimer()
  {
    if ((this.style & 0x2) != 0)
    {
      int i = OS.GetWindowLong(this.handle, -16);
      if ((OS.COMCTL32_MAJOR < 6) || ((i & 0x8) == 0))
        OS.SetTimer(this.handle, 100, 100, 0);
      else
        OS.SendMessage(this.handle, 1034, 1, 100);
    }
  }

  void stopTimer()
  {
    if ((this.style & 0x2) != 0)
    {
      int i = OS.GetWindowLong(this.handle, -16);
      if ((OS.COMCTL32_MAJOR < 6) || ((i & 0x8) == 0))
        OS.KillTimer(this.handle, 100);
      else
        OS.SendMessage(this.handle, 1034, 0, 0);
    }
  }

  void setBackgroundPixel(int paramInt)
  {
    if (paramInt == -1)
      paramInt = -16777216;
    OS.SendMessage(this.handle, 8193, 0, paramInt);
  }

  void setForegroundPixel(int paramInt)
  {
    if (paramInt == -1)
      paramInt = -16777216;
    OS.SendMessage(this.handle, 1033, 0, paramInt);
  }

  public void setMaximum(int paramInt)
  {
    checkWidget();
    int i = OS.SendMessage(this.handle, 1031, 1, 0);
    if ((i >= 0) && (i < paramInt))
      OS.SendMessage(this.handle, 1030, i, paramInt);
  }

  public void setMinimum(int paramInt)
  {
    checkWidget();
    int i = OS.SendMessage(this.handle, 1031, 0, 0);
    if ((paramInt >= 0) && (paramInt < i))
      OS.SendMessage(this.handle, 1030, paramInt, i);
  }

  public void setSelection(int paramInt)
  {
    checkWidget();
    OS.SendMessage(this.handle, 1026, paramInt, 0);
    if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(6, 0)))
    {
      int i = OS.SendMessage(this.handle, 1041, 0, 0);
      if (i != 1)
        OS.SendMessage(this.handle, 1026, paramInt, 0);
    }
  }

  public void setState(int paramInt)
  {
    checkWidget();
    if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(6, 0)))
      switch (paramInt)
      {
      case 0:
        OS.SendMessage(this.handle, 1040, 1, 0);
        break;
      case 1:
        OS.SendMessage(this.handle, 1040, 2, 0);
        break;
      case 4:
        OS.SendMessage(this.handle, 1040, 3, 0);
      case 2:
      case 3:
      }
  }

  int widgetStyle()
  {
    int i = super.widgetStyle();
    if ((this.style & 0x10000) != 0)
      i |= 1;
    if ((this.style & 0x200) != 0)
      i |= 4;
    if ((this.style & 0x2) != 0)
      i |= 8;
    return i;
  }

  TCHAR windowClass()
  {
    return ProgressBarClass;
  }

  int windowProc()
  {
    return ProgressBarProc;
  }

  LRESULT WM_GETDLGCODE(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_GETDLGCODE(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    return new LRESULT(256);
  }

  LRESULT WM_SIZE(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_SIZE(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    if (((this.style & 0x2) != 0) && ((OS.WIN32_VERSION == OS.VERSION(5, 1)) || ((OS.COMCTL32_MAJOR >= 6) && (!OS.IsAppThemed()))))
    {
      forceResize();
      RECT localRECT = new RECT();
      OS.GetClientRect(this.handle, localRECT);
      int i = OS.GetWindowLong(this.handle, -16);
      int j = i;
      if (localRECT.right - localRECT.left < 100)
        j &= -9;
      else
        j |= 8;
      if (j != i)
      {
        stopTimer();
        OS.SetWindowLong(this.handle, -16, j);
        startTimer();
      }
    }
    return localLRESULT;
  }

  LRESULT WM_TIMER(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_TIMER(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    if ((this.style & 0x2) != 0)
    {
      int i = OS.GetWindowLong(this.handle, -16);
      if (((OS.COMCTL32_MAJOR < 6) || ((i & 0x8) == 0)) && (paramInt1 == 100))
        OS.SendMessage(this.handle, 1029, 0, 0);
    }
    return localLRESULT;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.ProgressBar
 * JD-Core Version:    0.6.2
 */