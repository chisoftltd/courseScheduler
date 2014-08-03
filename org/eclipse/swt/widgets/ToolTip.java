package org.eclipse.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.internal.win32.MONITORINFO;
import org.eclipse.swt.internal.win32.NOTIFYICONDATA;
import org.eclipse.swt.internal.win32.NOTIFYICONDATAA;
import org.eclipse.swt.internal.win32.NOTIFYICONDATAW;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.TOOLINFO;

public class ToolTip extends Widget
{
  Shell parent;
  TrayItem item;
  String text = "";
  String message = "";
  int id;
  int x;
  int y;
  boolean autoHide = true;
  boolean hasLocation;
  boolean visible;
  static final int TIMER_ID = 100;

  public ToolTip(Shell paramShell, int paramInt)
  {
    super(paramShell, checkStyle(paramInt));
    this.parent = paramShell;
    checkOrientation(paramShell);
    paramShell.createToolTip(this);
  }

  static int checkStyle(int paramInt)
  {
    int i = 11;
    if ((paramInt & i) == 0)
      return paramInt;
    return checkBits(paramInt, 2, 8, 1, 0, 0, 0);
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

  void destroyWidget()
  {
    if (this.parent != null)
      this.parent.destroyToolTip(this);
    releaseHandle();
  }

  public boolean getAutoHide()
  {
    checkWidget();
    return this.autoHide;
  }

  public String getMessage()
  {
    checkWidget();
    return this.message;
  }

  public Shell getParent()
  {
    checkWidget();
    return this.parent;
  }

  public String getText()
  {
    checkWidget();
    return this.text;
  }

  public boolean getVisible()
  {
    checkWidget();
    if (OS.IsWinCE)
      return false;
    if (this.item != null)
      return this.visible;
    int i = hwndToolTip();
    if (OS.SendMessage(i, OS.TTM_GETCURRENTTOOL, 0, 0) != 0)
    {
      TOOLINFO localTOOLINFO = new TOOLINFO();
      localTOOLINFO.cbSize = TOOLINFO.sizeof;
      if (OS.SendMessage(i, OS.TTM_GETCURRENTTOOL, 0, localTOOLINFO) != 0)
        return ((localTOOLINFO.uFlags & 0x1) == 0) && (localTOOLINFO.uId == this.id);
    }
    return false;
  }

  int hwndToolTip()
  {
    return (this.style & 0x1000) != 0 ? this.parent.balloonTipHandle() : this.parent.toolTipHandle();
  }

  public boolean isVisible()
  {
    checkWidget();
    if (this.item != null)
      return (getVisible()) && (this.item.getVisible());
    return getVisible();
  }

  void releaseHandle()
  {
    super.releaseHandle();
    this.parent = null;
    this.item = null;
    this.id = -1;
  }

  void releaseWidget()
  {
    super.releaseWidget();
    if ((this.item == null) && (this.autoHide))
    {
      int i = hwndToolTip();
      if (OS.SendMessage(i, OS.TTM_GETCURRENTTOOL, 0, 0) != 0)
      {
        TOOLINFO localTOOLINFO = new TOOLINFO();
        localTOOLINFO.cbSize = TOOLINFO.sizeof;
        if ((OS.SendMessage(i, OS.TTM_GETCURRENTTOOL, 0, localTOOLINFO) != 0) && ((localTOOLINFO.uFlags & 0x1) == 0) && (localTOOLINFO.uId == this.id))
        {
          OS.SendMessage(i, 1041, 0, localTOOLINFO);
          OS.SendMessage(i, 1052, 0, 0);
          OS.KillTimer(i, 100);
        }
      }
    }
    if ((this.item != null) && (this.item.toolTip == this))
      this.item.toolTip = null;
    this.item = null;
    this.text = (this.message = null);
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

  public void setAutoHide(boolean paramBoolean)
  {
    checkWidget();
    this.autoHide = paramBoolean;
  }

  public void setLocation(int paramInt1, int paramInt2)
  {
    checkWidget();
    this.x = paramInt1;
    this.y = paramInt2;
    this.hasLocation = true;
  }

  public void setLocation(Point paramPoint)
  {
    checkWidget();
    if (paramPoint == null)
      SWT.error(4);
    setLocation(paramPoint.x, paramPoint.y);
  }

  public void setMessage(String paramString)
  {
    checkWidget();
    if (paramString == null)
      error(4);
    this.message = paramString;
  }

  public void setText(String paramString)
  {
    checkWidget();
    if (paramString == null)
      error(4);
    this.text = paramString;
  }

  public void setVisible(boolean paramBoolean)
  {
    checkWidget();
    if (OS.IsWinCE)
      return;
    if (paramBoolean == getVisible())
      return;
    Object localObject1;
    Object localObject2;
    int k;
    int n;
    if (this.item == null)
    {
      int i = this.parent.handle;
      localObject1 = new TOOLINFO();
      ((TOOLINFO)localObject1).cbSize = TOOLINFO.sizeof;
      ((TOOLINFO)localObject1).uId = this.id;
      ((TOOLINFO)localObject1).hwnd = i;
      int j = hwndToolTip();
      localObject2 = this.parent.getShell();
      if (this.text.length() != 0)
      {
        k = 0;
        if ((this.style & 0x2) != 0)
          k = 1;
        if ((this.style & 0x8) != 0)
          k = 2;
        if ((this.style & 0x1) != 0)
          k = 3;
        ((Shell)localObject2).setToolTipTitle(j, this.text, k);
      }
      else
      {
        ((Shell)localObject2).setToolTipTitle(j, null, 0);
      }
      k = 0;
      int m;
      if ((OS.IsWinCE) || (OS.WIN32_VERSION < OS.VERSION(4, 10)))
      {
        RECT localRECT1 = new RECT();
        OS.SystemParametersInfo(48, 0, localRECT1, 0);
        k = (localRECT1.right - localRECT1.left) / 4;
      }
      else
      {
        m = OS.MonitorFromWindow(i, 2);
        MONITORINFO localMONITORINFO = new MONITORINFO();
        localMONITORINFO.cbSize = MONITORINFO.sizeof;
        OS.GetMonitorInfo(m, localMONITORINFO);
        k = (localMONITORINFO.rcWork_right - localMONITORINFO.rcWork_left) / 4;
      }
      OS.SendMessage(j, 1048, 0, k);
      if (paramBoolean)
      {
        m = this.x;
        n = this.y;
        if (!this.hasLocation)
        {
          POINT localPOINT1 = new POINT();
          if (OS.GetCursorPos(localPOINT1))
          {
            m = localPOINT1.x;
            n = localPOINT1.y;
          }
        }
        int i1 = OS.MAKELPARAM(m, n);
        OS.SendMessage(j, 1042, 0, i1);
        POINT localPOINT2 = new POINT();
        OS.GetCursorPos(localPOINT2);
        RECT localRECT2 = new RECT();
        OS.GetClientRect(i, localRECT2);
        OS.MapWindowPoints(i, 0, localRECT2, 2);
        if (!OS.PtInRect(localRECT2, localPOINT2))
        {
          i2 = OS.GetCursor();
          OS.SetCursor(0);
          OS.SetCursorPos(localRECT2.left, localRECT2.top);
          OS.SendMessage(j, 1041, 1, (TOOLINFO)localObject1);
          OS.SetCursorPos(localPOINT2.x, localPOINT2.y);
          OS.SetCursor(i2);
        }
        else
        {
          OS.SendMessage(j, 1041, 1, (TOOLINFO)localObject1);
        }
        int i2 = OS.SendMessage(j, 1045, 2, 0);
        OS.SetTimer(j, 100, i2, 0);
      }
      else
      {
        OS.SendMessage(j, 1041, 0, (TOOLINFO)localObject1);
        OS.SendMessage(j, 1052, 0, 0);
        OS.KillTimer(j, 100);
      }
      return;
    }
    if ((this.item != null) && (OS.SHELL32_MAJOR >= 5) && (paramBoolean))
    {
      NOTIFYICONDATAA localNOTIFYICONDATAA = OS.IsUnicode ? new NOTIFYICONDATAW() : new NOTIFYICONDATAA();
      localObject1 = new TCHAR(0, this.text, true);
      TCHAR localTCHAR = new TCHAR(0, this.message, true);
      Object localObject3;
      if (OS.IsUnicode)
      {
        localObject2 = ((NOTIFYICONDATAW)localNOTIFYICONDATAA).szInfoTitle;
        k = Math.min(localObject2.length - 1, ((TCHAR)localObject1).length());
        System.arraycopy(((TCHAR)localObject1).chars, 0, localObject2, 0, k);
        localObject3 = ((NOTIFYICONDATAW)localNOTIFYICONDATAA).szInfo;
        n = Math.min(localObject3.length - 1, localTCHAR.length());
        System.arraycopy(localTCHAR.chars, 0, localObject3, 0, n);
      }
      else
      {
        localObject2 = ((NOTIFYICONDATAA)localNOTIFYICONDATAA).szInfoTitle;
        k = Math.min(localObject2.length - 1, ((TCHAR)localObject1).length());
        System.arraycopy(((TCHAR)localObject1).bytes, 0, localObject2, 0, k);
        localObject3 = ((NOTIFYICONDATAA)localNOTIFYICONDATAA).szInfo;
        n = Math.min(localObject3.length - 1, localTCHAR.length());
        System.arraycopy(localTCHAR.bytes, 0, localObject3, 0, n);
      }
      localObject2 = this.item.getDisplay();
      localNOTIFYICONDATAA.cbSize = NOTIFYICONDATA.sizeof;
      localNOTIFYICONDATAA.uID = this.item.id;
      localNOTIFYICONDATAA.hWnd = ((Display)localObject2).hwndMessage;
      localNOTIFYICONDATAA.uFlags = 16;
      if ((this.style & 0x2) != 0)
        localNOTIFYICONDATAA.dwInfoFlags = 1;
      if ((this.style & 0x8) != 0)
        localNOTIFYICONDATAA.dwInfoFlags = 2;
      if ((this.style & 0x1) != 0)
        localNOTIFYICONDATAA.dwInfoFlags = 3;
      sendEvent(22);
      this.visible = OS.Shell_NotifyIcon(1, localNOTIFYICONDATAA);
    }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.ToolTip
 * JD-Core Version:    0.6.2
 */