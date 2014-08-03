package org.eclipse.swt.widgets;

import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.internal.win32.CREATESTRUCT;
import org.eclipse.swt.internal.win32.LOGBRUSH;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.MINMAXINFO;
import org.eclipse.swt.internal.win32.MSG;
import org.eclipse.swt.internal.win32.NMTTDISPINFO;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.SHACTIVATEINFO;
import org.eclipse.swt.internal.win32.SIPINFO;
import org.eclipse.swt.internal.win32.STARTUPINFO;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.TOOLINFO;
import org.eclipse.swt.internal.win32.WINDOWPOS;
import org.eclipse.swt.internal.win32.WNDCLASS;

public class Shell extends Decorations
{
  Menu activeMenu;
  ToolTip[] toolTips;
  int hIMC;
  int hwndMDIClient;
  int lpstrTip;
  int toolTipHandle;
  int balloonTipHandle;
  int minWidth = -1;
  int minHeight = -1;
  int[] brushes;
  boolean showWithParent;
  boolean fullScreen;
  boolean wasMaximized;
  boolean modified;
  boolean center;
  String toolTitle;
  String balloonTitle;
  int toolIcon;
  int balloonIcon;
  int windowProc;
  Control lastActive;
  SHACTIVATEINFO psai;
  static int ToolTipProc;
  static final int DialogProc = localWNDCLASS.lpfnWndProc;
  static final TCHAR DialogClass = new TCHAR(0, OS.IsWinCE ? "Dialog" : "#32770", true);
  static final int[] SYSTEM_COLORS = { OS.COLOR_BTNFACE, OS.COLOR_WINDOW, OS.COLOR_BTNTEXT, OS.COLOR_WINDOWTEXT, OS.COLOR_HIGHLIGHT, OS.COLOR_SCROLLBAR };
  static final int BRUSHES_SIZE = 32;

  static
  {
    WNDCLASS localWNDCLASS = new WNDCLASS();
    OS.GetClassInfo(0, DialogClass, localWNDCLASS);
  }

  public Shell()
  {
    this(null);
  }

  public Shell(int paramInt)
  {
    this(null, paramInt);
  }

  public Shell(Display paramDisplay)
  {
    this(paramDisplay, OS.IsWinCE ? 0 : 1264);
  }

  public Shell(Display paramDisplay, int paramInt)
  {
    this(paramDisplay, null, paramInt, 0, false);
  }

  Shell(Display paramDisplay, Shell paramShell, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    checkSubclass();
    if (paramDisplay == null)
      paramDisplay = Display.getCurrent();
    if (paramDisplay == null)
      paramDisplay = Display.getDefault();
    if (!paramDisplay.isValidThread())
      error(22);
    if ((paramShell != null) && (paramShell.isDisposed()))
      error(5);
    this.center = ((paramShell != null) && ((paramInt1 & 0x10000000) != 0));
    this.style = checkStyle(paramShell, paramInt1);
    this.parent = paramShell;
    this.display = paramDisplay;
    this.handle = paramInt2;
    if ((paramInt2 != 0) && (!paramBoolean))
      this.state |= 16384;
    reskinWidget();
    createWidget();
  }

  public Shell(Shell paramShell)
  {
    this(paramShell, OS.IsWinCE ? 0 : 2144);
  }

  public Shell(Shell paramShell, int paramInt)
  {
    this(paramShell != null ? paramShell.display : null, paramShell, paramInt, 0, false);
  }

  public static Shell win32_new(Display paramDisplay, int paramInt)
  {
    return new Shell(paramDisplay, null, 8, paramInt, true);
  }

  public static Shell internal_new(Display paramDisplay, int paramInt)
  {
    return new Shell(paramDisplay, null, 8, paramInt, false);
  }

  static int checkStyle(Shell paramShell, int paramInt)
  {
    paramInt = Decorations.checkStyle(paramInt);
    paramInt &= -1073741825;
    int i = 229376;
    if ((paramInt & 0x10000000) != 0)
    {
      paramInt &= -268435457;
      paramInt |= (paramShell == null ? 1264 : 2144);
      if ((paramInt & i) == 0)
        paramInt |= (paramShell == null ? 65536 : 32768);
    }
    int j = paramInt & (i ^ 0xFFFFFFFF);
    if ((paramInt & 0x20000) != 0)
      return j | 0x20000;
    if ((paramInt & 0x10000) != 0)
      return j | 0x10000;
    if ((paramInt & 0x8000) != 0)
      return j | 0x8000;
    return j;
  }

  public void addShellListener(ShellListener paramShellListener)
  {
    checkWidget();
    if (paramShellListener == null)
      error(4);
    TypedListener localTypedListener = new TypedListener(paramShellListener);
    addListener(21, localTypedListener);
    addListener(19, localTypedListener);
    addListener(20, localTypedListener);
    addListener(26, localTypedListener);
    addListener(27, localTypedListener);
  }

  int balloonTipHandle()
  {
    if (this.balloonTipHandle == 0)
      createBalloonTipHandle();
    return this.balloonTipHandle;
  }

  int callWindowProc(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.handle == 0)
      return 0;
    if ((paramInt1 == this.toolTipHandle) || (paramInt1 == this.balloonTipHandle))
      return OS.CallWindowProc(ToolTipProc, paramInt1, paramInt2, paramInt3, paramInt4);
    if (this.hwndMDIClient != 0)
      return OS.DefFrameProc(paramInt1, this.hwndMDIClient, paramInt2, paramInt3, paramInt4);
    if (this.windowProc != 0)
      return OS.CallWindowProc(this.windowProc, paramInt1, paramInt2, paramInt3, paramInt4);
    if ((this.style & 0x4) != 0)
    {
      int i = 3312;
      if ((this.style & i) == 0)
        return OS.DefWindowProc(paramInt1, paramInt2, paramInt3, paramInt4);
    }
    if (this.parent != null)
    {
      switch (paramInt2)
      {
      case 7:
      case 8:
        return OS.DefWindowProc(paramInt1, paramInt2, paramInt3, paramInt4);
      }
      return OS.CallWindowProc(DialogProc, paramInt1, paramInt2, paramInt3, paramInt4);
    }
    return OS.DefWindowProc(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  void center()
  {
    if (this.parent == null)
      return;
    Rectangle localRectangle1 = getBounds();
    Rectangle localRectangle2 = this.display.map(this.parent, null, this.parent.getClientArea());
    int i = Math.max(localRectangle2.x, localRectangle2.x + (localRectangle2.width - localRectangle1.width) / 2);
    int j = Math.max(localRectangle2.y, localRectangle2.y + (localRectangle2.height - localRectangle1.height) / 2);
    Rectangle localRectangle3 = this.parent.getMonitor().getClientArea();
    if (i + localRectangle1.width > localRectangle3.x + localRectangle3.width)
      i = Math.max(localRectangle3.x, localRectangle3.x + localRectangle3.width - localRectangle1.width);
    else
      i = Math.max(i, localRectangle3.x);
    if (j + localRectangle1.height > localRectangle3.y + localRectangle3.height)
      j = Math.max(localRectangle3.y, localRectangle3.y + localRectangle3.height - localRectangle1.height);
    else
      j = Math.max(j, localRectangle3.y);
    setLocation(i, j);
  }

  public void close()
  {
    checkWidget();
    closeWidget();
  }

  void createBalloonTipHandle()
  {
    this.balloonTipHandle = OS.CreateWindowEx(0, new TCHAR(0, "tooltips_class32", true), null, 67, -2147483648, 0, -2147483648, 0, this.handle, 0, OS.GetModuleHandle(null), null);
    if (this.balloonTipHandle == 0)
      error(2);
    if (ToolTipProc == 0)
      ToolTipProc = OS.GetWindowLongPtr(this.balloonTipHandle, -4);
    OS.SendMessage(this.balloonTipHandle, 1048, 0, 32767);
    this.display.addControl(this.balloonTipHandle, this);
    OS.SetWindowLongPtr(this.balloonTipHandle, -4, this.display.windowProc);
  }

  void createHandle()
  {
    int i = (this.handle != 0) && ((this.state & 0x4000) == 0) ? 1 : 0;
    if ((this.handle == 0) || (i != 0))
    {
      super.createHandle();
    }
    else
    {
      this.state |= 2;
      if ((this.style & 0x300) == 0)
        this.state |= 256;
      this.windowProc = OS.GetWindowLongPtr(this.handle, -4);
    }
    if (i == 0)
    {
      int j = OS.GetWindowLong(this.handle, -16);
      j &= ((OS.WS_OVERLAPPED | 0xC00000) ^ 0xFFFFFFFF);
      if (!OS.IsWinCE)
        j |= -2147483648;
      if ((this.style & 0x20) != 0)
        j |= 12582912;
      if (((this.style & 0x8) == 0) && ((this.style & 0x810) == 0))
        j |= 8388608;
      OS.SetWindowLong(this.handle, -16, j);
      int k = 55;
      SetWindowPos(this.handle, 0, 0, 0, 0, 0, k);
      if (OS.IsWinCE)
        _setMaximized(true);
      if (OS.IsPPC)
      {
        this.psai = new SHACTIVATEINFO();
        this.psai.cbSize = SHACTIVATEINFO.sizeof;
      }
    }
    if (OS.IsDBLocale)
    {
      this.hIMC = OS.ImmCreateContext();
      if (this.hIMC != 0)
        OS.ImmAssociateContext(this.handle, this.hIMC);
    }
  }

  void createToolTip(ToolTip paramToolTip)
  {
    int i = 0;
    if (this.toolTips == null)
      this.toolTips = new ToolTip[4];
    while ((i < this.toolTips.length) && (this.toolTips[i] != null))
      i++;
    if (i == this.toolTips.length)
    {
      localObject = new ToolTip[this.toolTips.length + 4];
      System.arraycopy(this.toolTips, 0, localObject, 0, this.toolTips.length);
      this.toolTips = ((ToolTip[])localObject);
    }
    this.toolTips[i] = paramToolTip;
    paramToolTip.id = (i + 108);
    if (OS.IsWinCE)
      return;
    Object localObject = new TOOLINFO();
    ((TOOLINFO)localObject).cbSize = TOOLINFO.sizeof;
    ((TOOLINFO)localObject).hwnd = this.handle;
    ((TOOLINFO)localObject).uId = paramToolTip.id;
    ((TOOLINFO)localObject).uFlags = 32;
    ((TOOLINFO)localObject).lpszText = -1;
    OS.SendMessage(paramToolTip.hwndToolTip(), OS.TTM_ADDTOOL, 0, (TOOLINFO)localObject);
  }

  void createToolTipHandle()
  {
    this.toolTipHandle = OS.CreateWindowEx(0, new TCHAR(0, "tooltips_class32", true), null, 3, -2147483648, 0, -2147483648, 0, this.handle, 0, OS.GetModuleHandle(null), null);
    if (this.toolTipHandle == 0)
      error(2);
    if (ToolTipProc == 0)
      ToolTipProc = OS.GetWindowLongPtr(this.toolTipHandle, -4);
    OS.SendMessage(this.toolTipHandle, 1048, 0, 32767);
    this.display.addControl(this.toolTipHandle, this);
    OS.SetWindowLongPtr(this.toolTipHandle, -4, this.display.windowProc);
  }

  void deregister()
  {
    super.deregister();
    if (this.toolTipHandle != 0)
      this.display.removeControl(this.toolTipHandle);
    if (this.balloonTipHandle != 0)
      this.display.removeControl(this.balloonTipHandle);
  }

  void destroyToolTip(ToolTip paramToolTip)
  {
    if (this.toolTips == null)
      return;
    this.toolTips[(paramToolTip.id - 108)] = null;
    if (OS.IsWinCE)
      return;
    if (this.balloonTipHandle != 0)
    {
      TOOLINFO localTOOLINFO = new TOOLINFO();
      localTOOLINFO.cbSize = TOOLINFO.sizeof;
      localTOOLINFO.uId = paramToolTip.id;
      localTOOLINFO.hwnd = this.handle;
      OS.SendMessage(this.balloonTipHandle, OS.TTM_DELTOOL, 0, localTOOLINFO);
    }
    paramToolTip.id = -1;
  }

  void destroyWidget()
  {
    fixActiveShell();
    super.destroyWidget();
  }

  public void dispose()
  {
    super.dispose();
  }

  void enableWidget(boolean paramBoolean)
  {
    if (paramBoolean)
      this.state &= -9;
    else
      this.state |= 8;
    if (Display.TrimEnabled)
    {
      if (isActive())
        setItemEnabled(61536, paramBoolean);
    }
    else
      OS.EnableWindow(this.handle, paramBoolean);
  }

  int findBrush(int paramInt1, int paramInt2)
  {
    if (paramInt2 == 0)
      for (int i = 0; i < SYSTEM_COLORS.length; i++)
        if (paramInt1 == OS.GetSysColor(SYSTEM_COLORS[i]))
          return OS.GetSysColorBrush(SYSTEM_COLORS[i]);
    if (this.brushes == null)
      this.brushes = new int[32];
    LOGBRUSH localLOGBRUSH = new LOGBRUSH();
    for (int j = 0; j < this.brushes.length; j++)
    {
      k = this.brushes[j];
      if (k == 0)
        break;
      OS.GetObject(k, LOGBRUSH.sizeof, localLOGBRUSH);
      switch (localLOGBRUSH.lbStyle)
      {
      case 0:
        if ((paramInt2 == 0) && (localLOGBRUSH.lbColor == paramInt1))
          return k;
        break;
      case 3:
        if ((paramInt2 == 3) && (localLOGBRUSH.lbHatch == paramInt1))
          return k;
        break;
      case 1:
      case 2:
      }
    }
    j = this.brushes.length;
    int k = this.brushes[(--j)];
    if (k != 0)
      OS.DeleteObject(k);
    System.arraycopy(this.brushes, 0, this.brushes, 1, j);
    switch (paramInt2)
    {
    case 0:
      k = OS.CreateSolidBrush(paramInt1);
      break;
    case 3:
      k = OS.CreatePatternBrush(paramInt1);
    case 1:
    case 2:
    }
    return this.brushes[0] = k;
  }

  Control findBackgroundControl()
  {
    return (this.background != -1) || (this.backgroundImage != null) ? this : null;
  }

  Cursor findCursor()
  {
    return this.cursor;
  }

  Control findThemeControl()
  {
    return null;
  }

  ToolTip findToolTip(int paramInt)
  {
    if (this.toolTips == null)
      return null;
    paramInt -= 108;
    return (paramInt >= 0) && (paramInt < this.toolTips.length) ? this.toolTips[paramInt] : null;
  }

  void fixActiveShell()
  {
    int i = OS.GetParent(this.handle);
    if ((i != 0) && (this.handle == OS.GetActiveWindow()) && (!OS.IsWindowEnabled(i)) && (OS.IsWindowVisible(i)))
      OS.SetActiveWindow(i);
  }

  void fixShell(Shell paramShell, Control paramControl)
  {
    if (this == paramShell)
      return;
    if (paramControl == this.lastActive)
      setActiveControl(null);
    String str = paramControl.toolTipText;
    if (str != null)
    {
      paramControl.setToolTipText(this, null);
      paramControl.setToolTipText(paramShell, str);
    }
  }

  void fixToolTip()
  {
    if (OS.COMCTL32_MAJOR >= 6)
    {
      if (this.toolTipHandle == 0)
        return;
      TOOLINFO localTOOLINFO = new TOOLINFO();
      localTOOLINFO.cbSize = TOOLINFO.sizeof;
      if ((OS.SendMessage(this.toolTipHandle, OS.TTM_GETCURRENTTOOL, 0, localTOOLINFO) != 0) && ((localTOOLINFO.uFlags & 0x1) != 0))
      {
        OS.SendMessage(this.toolTipHandle, OS.TTM_DELTOOL, 0, localTOOLINFO);
        OS.SendMessage(this.toolTipHandle, OS.TTM_ADDTOOL, 0, localTOOLINFO);
      }
    }
  }

  public void forceActive()
  {
    checkWidget();
    if (!isVisible())
      return;
    OS.SetForegroundWindow(this.handle);
  }

  void forceResize()
  {
  }

  public int getAlpha()
  {
    checkWidget();
    if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(5, 1)))
    {
      byte[] arrayOfByte = new byte[1];
      if (OS.GetLayeredWindowAttributes(this.handle, null, arrayOfByte, null))
        return arrayOfByte[0] & 0xFF;
    }
    return 255;
  }

  public Rectangle getBounds()
  {
    checkWidget();
    if ((!OS.IsWinCE) && (OS.IsIconic(this.handle)))
      return super.getBounds();
    RECT localRECT = new RECT();
    OS.GetWindowRect(this.handle, localRECT);
    int i = localRECT.right - localRECT.left;
    int j = localRECT.bottom - localRECT.top;
    return new Rectangle(localRECT.left, localRECT.top, i, j);
  }

  ToolTip getCurrentToolTip()
  {
    ToolTip localToolTip;
    if (this.toolTipHandle != 0)
    {
      localToolTip = getCurrentToolTip(this.toolTipHandle);
      if (localToolTip != null)
        return localToolTip;
    }
    if (this.balloonTipHandle != 0)
    {
      localToolTip = getCurrentToolTip(this.balloonTipHandle);
      if (localToolTip != null)
        return localToolTip;
    }
    return null;
  }

  ToolTip getCurrentToolTip(int paramInt)
  {
    if (paramInt == 0)
      return null;
    if (OS.SendMessage(paramInt, OS.TTM_GETCURRENTTOOL, 0, 0) != 0)
    {
      TOOLINFO localTOOLINFO = new TOOLINFO();
      localTOOLINFO.cbSize = TOOLINFO.sizeof;
      if ((OS.SendMessage(paramInt, OS.TTM_GETCURRENTTOOL, 0, localTOOLINFO) != 0) && ((localTOOLINFO.uFlags & 0x1) == 0))
        return findToolTip(localTOOLINFO.uId);
    }
    return null;
  }

  public boolean getEnabled()
  {
    checkWidget();
    return (this.state & 0x8) == 0;
  }

  public boolean getFullScreen()
  {
    checkWidget();
    return this.fullScreen;
  }

  public int getImeInputMode()
  {
    checkWidget();
    if (!OS.IsDBLocale)
      return 0;
    int i = OS.ImmGetContext(this.handle);
    int[] arrayOfInt1 = new int[1];
    int[] arrayOfInt2 = new int[1];
    boolean bool = OS.ImmGetOpenStatus(i);
    if (bool)
      bool = OS.ImmGetConversionStatus(i, arrayOfInt1, arrayOfInt2);
    OS.ImmReleaseContext(this.handle, i);
    if (!bool)
      return 0;
    int j = 0;
    if ((arrayOfInt1[0] & 0x10) != 0)
      j |= 32;
    if ((arrayOfInt1[0] & 0x8) != 0)
      j |= 2;
    if ((arrayOfInt1[0] & 0x2) != 0)
      return j | 0x10;
    if ((arrayOfInt1[0] & 0x1) != 0)
      return j | 0x8;
    return j | 0x4;
  }

  public Point getLocation()
  {
    checkWidget();
    if ((!OS.IsWinCE) && (OS.IsIconic(this.handle)))
      return super.getLocation();
    RECT localRECT = new RECT();
    OS.GetWindowRect(this.handle, localRECT);
    return new Point(localRECT.left, localRECT.top);
  }

  public boolean getMaximized()
  {
    checkWidget();
    return (!this.fullScreen) && (super.getMaximized());
  }

  public Point getMinimumSize()
  {
    checkWidget();
    int i = Math.max(0, this.minWidth);
    int j = 1248;
    if (((this.style & 0x8) == 0) && ((this.style & j) != 0))
      i = Math.max(i, OS.GetSystemMetrics(34));
    int k = Math.max(0, this.minHeight);
    if (((this.style & 0x8) == 0) && ((this.style & j) != 0))
      if ((this.style & 0x10) != 0)
      {
        k = Math.max(k, OS.GetSystemMetrics(35));
      }
      else
      {
        RECT localRECT = new RECT();
        int m = OS.GetWindowLong(this.handle, -16);
        int n = OS.GetWindowLong(this.handle, -20);
        OS.AdjustWindowRectEx(localRECT, m, false, n);
        k = Math.max(k, localRECT.bottom - localRECT.top);
      }
    return new Point(i, k);
  }

  public boolean getModified()
  {
    checkWidget();
    return this.modified;
  }

  public Region getRegion()
  {
    checkWidget();
    return this.region;
  }

  public Shell getShell()
  {
    checkWidget();
    return this;
  }

  public Point getSize()
  {
    checkWidget();
    if ((!OS.IsWinCE) && (OS.IsIconic(this.handle)))
      return super.getSize();
    RECT localRECT = new RECT();
    OS.GetWindowRect(this.handle, localRECT);
    int i = localRECT.right - localRECT.left;
    int j = localRECT.bottom - localRECT.top;
    return new Point(i, j);
  }

  public Shell[] getShells()
  {
    checkWidget();
    int i = 0;
    Shell[] arrayOfShell = this.display.getShells();
    for (int j = 0; j < arrayOfShell.length; j++)
    {
      localObject1 = arrayOfShell[j];
      do
        localObject1 = ((Control)localObject1).parent;
      while ((localObject1 != null) && (localObject1 != this));
      if (localObject1 == this)
        i++;
    }
    j = 0;
    Object localObject1 = new Shell[i];
    for (int k = 0; k < arrayOfShell.length; k++)
    {
      Object localObject2 = arrayOfShell[k];
      do
        localObject2 = ((Control)localObject2).parent;
      while ((localObject2 != null) && (localObject2 != this));
      if (localObject2 == this)
        localObject1[(j++)] = arrayOfShell[k];
    }
    return localObject1;
  }

  Composite findDeferredControl()
  {
    return this.layoutCount > 0 ? this : null;
  }

  public boolean isEnabled()
  {
    checkWidget();
    return getEnabled();
  }

  public boolean isVisible()
  {
    checkWidget();
    return getVisible();
  }

  int hwndMDIClient()
  {
    if (this.hwndMDIClient == 0)
    {
      int i = 1174405121;
      this.hwndMDIClient = OS.CreateWindowEx(0, new TCHAR(0, "MDICLIENT", true), null, i, 0, 0, 0, 0, this.handle, 0, OS.GetModuleHandle(null), new CREATESTRUCT());
    }
    return this.hwndMDIClient;
  }

  public void open()
  {
    checkWidget();
    STARTUPINFO localSTARTUPINFO = Display.lpStartupInfo;
    if ((localSTARTUPINFO == null) || ((localSTARTUPINFO.dwFlags & 0x1) == 0))
    {
      bringToTop();
      if (isDisposed())
        return;
    }
    if (OS.IsWinCE)
      OS.SetForegroundWindow(this.handle);
    OS.SendMessage(this.handle, 295, 3, 0);
    setVisible(true);
    if (isDisposed())
      return;
    MSG localMSG = new MSG();
    int i = 4194306;
    OS.PeekMessage(localMSG, 0, 0, 0, i);
    if ((!restoreFocus()) && (!traverseGroup(true)))
      setFocus();
  }

  public boolean print(GC paramGC)
  {
    checkWidget();
    if (paramGC == null)
      error(4);
    if (paramGC.isDisposed())
      error(5);
    return false;
  }

  void register()
  {
    super.register();
    if (this.toolTipHandle != 0)
      this.display.addControl(this.toolTipHandle, this);
    if (this.balloonTipHandle != 0)
      this.display.addControl(this.balloonTipHandle, this);
  }

  void releaseBrushes()
  {
    if (this.brushes != null)
      for (int i = 0; i < this.brushes.length; i++)
        if (this.brushes[i] != 0)
          OS.DeleteObject(this.brushes[i]);
    this.brushes = null;
  }

  void releaseChildren(boolean paramBoolean)
  {
    Shell[] arrayOfShell = getShells();
    Object localObject;
    for (int i = 0; i < arrayOfShell.length; i++)
    {
      localObject = arrayOfShell[i];
      if ((localObject != null) && (!((Shell)localObject).isDisposed()))
        ((Shell)localObject).release(false);
    }
    if (this.toolTips != null)
      for (i = 0; i < this.toolTips.length; i++)
      {
        localObject = this.toolTips[i];
        if ((localObject != null) && (!((ToolTip)localObject).isDisposed()))
          ((ToolTip)localObject).release(false);
      }
    this.toolTips = null;
    super.releaseChildren(paramBoolean);
  }

  void releaseHandle()
  {
    super.releaseHandle();
    this.hwndMDIClient = 0;
  }

  void releaseParent()
  {
  }

  void releaseWidget()
  {
    super.releaseWidget();
    releaseBrushes();
    this.activeMenu = null;
    this.display.clearModal(this);
    if (this.lpstrTip != 0)
    {
      int i = OS.GetProcessHeap();
      OS.HeapFree(i, 0, this.lpstrTip);
    }
    this.lpstrTip = 0;
    this.toolTipHandle = (this.balloonTipHandle = 0);
    if ((OS.IsDBLocale) && (this.hIMC != 0))
      OS.ImmDestroyContext(this.hIMC);
    this.lastActive = null;
    this.toolTitle = (this.balloonTitle = null);
  }

  void removeMenu(Menu paramMenu)
  {
    super.removeMenu(paramMenu);
    if (paramMenu == this.activeMenu)
      this.activeMenu = null;
  }

  public void removeShellListener(ShellListener paramShellListener)
  {
    checkWidget();
    if (paramShellListener == null)
      error(4);
    if (this.eventTable == null)
      return;
    this.eventTable.unhook(21, paramShellListener);
    this.eventTable.unhook(19, paramShellListener);
    this.eventTable.unhook(20, paramShellListener);
    this.eventTable.unhook(26, paramShellListener);
    this.eventTable.unhook(27, paramShellListener);
  }

  void reskinChildren(int paramInt)
  {
    Shell[] arrayOfShell = getShells();
    Object localObject;
    for (int i = 0; i < arrayOfShell.length; i++)
    {
      localObject = arrayOfShell[i];
      if (localObject != null)
        ((Shell)localObject).reskin(paramInt);
    }
    if (this.toolTips != null)
      for (i = 0; i < this.toolTips.length; i++)
      {
        localObject = this.toolTips[i];
        if (localObject != null)
          ((ToolTip)localObject).reskin(paramInt);
      }
    super.reskinChildren(paramInt);
  }

  LRESULT selectPalette(int paramInt)
  {
    int i = OS.GetDC(this.handle);
    int j = OS.SelectPalette(i, paramInt, false);
    int k = OS.RealizePalette(i);
    if (k > 0)
    {
      OS.InvalidateRect(this.handle, null, true);
    }
    else
    {
      OS.SelectPalette(i, j, true);
      OS.RealizePalette(i);
    }
    OS.ReleaseDC(this.handle, i);
    return k > 0 ? LRESULT.ONE : LRESULT.ZERO;
  }

  boolean sendKeyEvent(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Event paramEvent)
  {
    if ((!isEnabled()) || (!isActive()))
      return false;
    return super.sendKeyEvent(paramInt1, paramInt2, paramInt3, paramInt4, paramEvent);
  }

  public void setActive()
  {
    checkWidget();
    if (!isVisible())
      return;
    bringToTop();
  }

  void setActiveControl(Control paramControl)
  {
    if ((paramControl != null) && (paramControl.isDisposed()))
      paramControl = null;
    if ((this.lastActive != null) && (this.lastActive.isDisposed()))
      this.lastActive = null;
    if (this.lastActive == paramControl)
      return;
    Control[] arrayOfControl1 = paramControl == null ? new Control[0] : paramControl.getPath();
    Control[] arrayOfControl2 = this.lastActive == null ? new Control[0] : this.lastActive.getPath();
    this.lastActive = paramControl;
    int i = 0;
    int j = Math.min(arrayOfControl1.length, arrayOfControl2.length);
    while (i < j)
    {
      if (arrayOfControl1[i] != arrayOfControl2[i])
        break;
      i++;
    }
    for (int k = arrayOfControl2.length - 1; k >= i; k--)
      if (!arrayOfControl2[k].isDisposed())
        arrayOfControl2[k].sendEvent(27);
    for (k = arrayOfControl1.length - 1; k >= i; k--)
      if (!arrayOfControl1[k].isDisposed())
        arrayOfControl1[k].sendEvent(26);
  }

  public void setAlpha(int paramInt)
  {
    checkWidget();
    if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(5, 1)))
    {
      paramInt &= 255;
      int i = OS.GetWindowLong(this.handle, -20);
      if (paramInt == 255)
      {
        OS.SetWindowLong(this.handle, -20, i & 0xFFF7FFFF);
        int j = 1157;
        OS.RedrawWindow(this.handle, null, 0, j);
      }
      else
      {
        OS.SetWindowLong(this.handle, -20, i | 0x80000);
        OS.SetLayeredWindowAttributes(this.handle, 0, (byte)paramInt, 2);
      }
    }
  }

  void setBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, boolean paramBoolean)
  {
    if (this.fullScreen)
      setFullScreen(false);
    int i = OS.GetWindowLong(this.handle, -20);
    if ((i & 0x80000) != 0)
      paramInt5 &= -33;
    super.setBounds(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, false);
  }

  public void setEnabled(boolean paramBoolean)
  {
    checkWidget();
    if (((this.state & 0x8) == 0) == paramBoolean)
      return;
    super.setEnabled(paramBoolean);
    if ((paramBoolean) && (this.handle == OS.GetActiveWindow()) && (!restoreFocus()))
      traverseGroup(true);
  }

  public void setFullScreen(boolean paramBoolean)
  {
    checkWidget();
    if (this.fullScreen == paramBoolean)
      return;
    int i = paramBoolean ? OS.SW_SHOWMAXIMIZED : OS.SW_RESTORE;
    int j = OS.GetWindowLong(this.handle, -16);
    int k = 1248;
    if ((this.style & k) != 0)
      if (paramBoolean)
      {
        j &= ((0xC00000 | OS.WS_MAXIMIZEBOX | OS.WS_MINIMIZEBOX) ^ 0xFFFFFFFF);
      }
      else
      {
        j |= 12582912;
        if ((this.style & 0x400) != 0)
          j |= OS.WS_MAXIMIZEBOX;
        if ((this.style & 0x80) != 0)
          j |= OS.WS_MINIMIZEBOX;
      }
    if (paramBoolean)
      this.wasMaximized = getMaximized();
    boolean bool = isVisible();
    OS.SetWindowLong(this.handle, -16, j);
    if (this.wasMaximized)
    {
      OS.ShowWindow(this.handle, 0);
      i = OS.SW_SHOWMAXIMIZED;
    }
    if (bool)
      OS.ShowWindow(this.handle, i);
    OS.UpdateWindow(this.handle);
    this.fullScreen = paramBoolean;
  }

  public void setImeInputMode(int paramInt)
  {
    checkWidget();
    if (!OS.IsDBLocale)
      return;
    boolean bool = paramInt != 0;
    int i = OS.ImmGetContext(this.handle);
    OS.ImmSetOpenStatus(i, bool);
    if (bool)
    {
      int[] arrayOfInt1 = new int[1];
      int[] arrayOfInt2 = new int[1];
      if (OS.ImmGetConversionStatus(i, arrayOfInt1, arrayOfInt2))
      {
        int j = 0;
        int k = 3;
        if ((paramInt & 0x10) != 0)
        {
          j = 3;
          k = 0;
        }
        else if ((paramInt & 0x8) != 0)
        {
          j = 1;
          k = 2;
        }
        if ((paramInt & 0xA) != 0)
          j |= 8;
        else
          k |= 8;
        if ((paramInt & 0x20) != 0)
          j |= 16;
        else
          k |= 16;
        arrayOfInt1[0] |= j;
        arrayOfInt1[0] &= (k ^ 0xFFFFFFFF);
        OS.ImmSetConversionStatus(i, arrayOfInt1[0], arrayOfInt2[0]);
      }
    }
    OS.ImmReleaseContext(this.handle, i);
  }

  public void setMinimumSize(int paramInt1, int paramInt2)
  {
    checkWidget();
    int i = 0;
    int j = 0;
    int k = 1248;
    if (((this.style & 0x8) == 0) && ((this.style & k) != 0))
    {
      i = OS.GetSystemMetrics(34);
      if ((this.style & 0x10) != 0)
      {
        j = OS.GetSystemMetrics(35);
      }
      else
      {
        localObject = new RECT();
        m = OS.GetWindowLong(this.handle, -16);
        n = OS.GetWindowLong(this.handle, -20);
        OS.AdjustWindowRectEx((RECT)localObject, m, false, n);
        j = ((RECT)localObject).bottom - ((RECT)localObject).top;
      }
    }
    this.minWidth = Math.max(i, paramInt1);
    this.minHeight = Math.max(j, paramInt2);
    Object localObject = getSize();
    int m = Math.max(((Point)localObject).x, this.minWidth);
    int n = Math.max(((Point)localObject).y, this.minHeight);
    if (this.minWidth <= i)
      this.minWidth = -1;
    if (this.minHeight <= j)
      this.minHeight = -1;
    if ((m != ((Point)localObject).x) || (n != ((Point)localObject).y))
      setSize(m, n);
  }

  public void setMinimumSize(Point paramPoint)
  {
    checkWidget();
    if (paramPoint == null)
      error(4);
    setMinimumSize(paramPoint.x, paramPoint.y);
  }

  public void setModified(boolean paramBoolean)
  {
    checkWidget();
    this.modified = paramBoolean;
  }

  void setItemEnabled(int paramInt, boolean paramBoolean)
  {
    int i = OS.GetSystemMenu(this.handle, false);
    if (i == 0)
      return;
    int j = 0;
    if (!paramBoolean)
      j = 3;
    OS.EnableMenuItem(i, paramInt, j);
  }

  void setParent()
  {
  }

  public void setRegion(Region paramRegion)
  {
    checkWidget();
    if ((this.style & 0x8) == 0)
      return;
    super.setRegion(paramRegion);
  }

  void setToolTipText(int paramInt, String paramString)
  {
    if (OS.IsWinCE)
      return;
    TOOLINFO localTOOLINFO = new TOOLINFO();
    localTOOLINFO.cbSize = TOOLINFO.sizeof;
    localTOOLINFO.hwnd = this.handle;
    localTOOLINFO.uId = paramInt;
    int i = toolTipHandle();
    if (paramString == null)
    {
      OS.SendMessage(i, OS.TTM_DELTOOL, 0, localTOOLINFO);
    }
    else if (OS.SendMessage(i, OS.TTM_GETTOOLINFO, 0, localTOOLINFO) != 0)
    {
      OS.SendMessage(i, 1053, 0, 0);
    }
    else
    {
      localTOOLINFO.uFlags = 17;
      localTOOLINFO.lpszText = -1;
      OS.SendMessage(i, OS.TTM_ADDTOOL, 0, localTOOLINFO);
    }
  }

  void setToolTipText(NMTTDISPINFO paramNMTTDISPINFO, byte[] paramArrayOfByte)
  {
    if (!hasCursor())
      return;
    int i = OS.GetProcessHeap();
    if (this.lpstrTip != 0)
      OS.HeapFree(i, 0, this.lpstrTip);
    int j = paramArrayOfByte.length;
    this.lpstrTip = OS.HeapAlloc(i, 8, j);
    OS.MoveMemory(this.lpstrTip, paramArrayOfByte, j);
    paramNMTTDISPINFO.lpszText = this.lpstrTip;
  }

  void setToolTipText(NMTTDISPINFO paramNMTTDISPINFO, char[] paramArrayOfChar)
  {
    if (!hasCursor())
      return;
    int i = OS.GetProcessHeap();
    if (this.lpstrTip != 0)
      OS.HeapFree(i, 0, this.lpstrTip);
    int j = paramArrayOfChar.length * 2;
    this.lpstrTip = OS.HeapAlloc(i, 8, j);
    OS.MoveMemory(this.lpstrTip, paramArrayOfChar, j);
    paramNMTTDISPINFO.lpszText = this.lpstrTip;
  }

  void setToolTipTitle(int paramInt1, String paramString, int paramInt2)
  {
    if ((paramInt1 != this.toolTipHandle) && (paramInt1 != this.balloonTipHandle))
      return;
    if (paramInt1 == this.toolTipHandle)
    {
      if (((paramString == this.toolTitle) || ((this.toolTitle != null) && (this.toolTitle.equals(paramString)))) && (paramInt2 == this.toolIcon))
        return;
      this.toolTitle = paramString;
      this.toolIcon = paramInt2;
    }
    else if (paramInt1 == this.balloonTipHandle)
    {
      if (((paramString == this.balloonTitle) || ((this.balloonTitle != null) && (this.balloonTitle.equals(paramString)))) && (paramInt2 == this.toolIcon))
        return;
      this.balloonTitle = paramString;
      this.balloonIcon = paramInt2;
    }
    if (paramString != null)
    {
      if (paramString.length() > 99)
        paramString = paramString.substring(0, 99);
      TCHAR localTCHAR = new TCHAR(getCodePage(), paramString, true);
      OS.SendMessage(paramInt1, OS.TTM_SETTITLE, paramInt2, localTCHAR);
    }
    else
    {
      OS.SendMessage(paramInt1, OS.TTM_SETTITLE, 0, 0);
    }
  }

  public void setVisible(boolean paramBoolean)
  {
    checkWidget();
    int i = 229376;
    int k;
    if ((this.style & i) != 0)
    {
      if (paramBoolean)
      {
        this.display.setModalShell(this);
        if ((this.style & 0x30000) != 0)
          this.display.setModalDialog(null);
        Control localControl = this.display._getFocusControl();
        if ((localControl != null) && (!localControl.isActive()))
        {
          bringToTop();
          if (isDisposed())
            return;
        }
        k = OS.GetActiveWindow();
        if ((k == 0) && (this.parent != null))
          k = this.parent.handle;
        if (k != 0)
          OS.SendMessage(k, 31, 0, 0);
        OS.ReleaseCapture();
      }
      else
      {
        this.display.clearModal(this);
      }
    }
    else
      updateModal();
    if ((this.showWithParent) && (!paramBoolean) && (!OS.IsWinCE))
      OS.ShowOwnedPopups(this.handle, false);
    if (!paramBoolean)
      fixActiveShell();
    if ((paramBoolean) && (this.center) && (!this.moved))
    {
      center();
      if (isDisposed())
        return;
    }
    super.setVisible(paramBoolean);
    if (isDisposed())
      return;
    if (this.showWithParent != paramBoolean)
    {
      this.showWithParent = paramBoolean;
      if ((paramBoolean) && (!OS.IsWinCE))
        OS.ShowOwnedPopups(this.handle, true);
    }
    if ((paramBoolean) && (this.parent != null) && ((this.parent.state & 0x4000) != 0))
    {
      int j = this.parent.handle;
      k = OS.GetWindowLong(j, -20);
      if ((k & 0x80) != 0)
      {
        OS.SetWindowLong(j, -20, k & 0xFFFFFF7F);
        OS.ShowWindow(j, 0);
        OS.ShowWindow(j, OS.SW_RESTORE);
      }
    }
  }

  void subclass()
  {
    super.subclass();
    if (ToolTipProc != 0)
    {
      int i = this.display.windowProc;
      if (this.toolTipHandle != 0)
        OS.SetWindowLongPtr(this.toolTipHandle, -4, i);
      if (this.balloonTipHandle != 0)
        OS.SetWindowLongPtr(this.balloonTipHandle, -4, i);
    }
  }

  int toolTipHandle()
  {
    if (this.toolTipHandle == 0)
      createToolTipHandle();
    return this.toolTipHandle;
  }

  boolean translateAccelerator(MSG paramMSG)
  {
    if ((!isEnabled()) || (!isActive()))
      return false;
    if ((this.menuBar != null) && (!this.menuBar.isEnabled()))
      return false;
    return (translateMDIAccelerator(paramMSG)) || (translateMenuAccelerator(paramMSG));
  }

  boolean traverseEscape()
  {
    if (this.parent == null)
      return false;
    if ((!isVisible()) || (!isEnabled()))
      return false;
    close();
    return true;
  }

  void unsubclass()
  {
    super.unsubclass();
    if (ToolTipProc != 0)
    {
      if (this.toolTipHandle != 0)
        OS.SetWindowLongPtr(this.toolTipHandle, -4, ToolTipProc);
      if (this.toolTipHandle != 0)
        OS.SetWindowLongPtr(this.toolTipHandle, -4, ToolTipProc);
    }
  }

  void updateModal()
  {
    if (Display.TrimEnabled)
      setItemEnabled(61536, isActive());
    else
      OS.EnableWindow(this.handle, isActive());
  }

  CREATESTRUCT widgetCreateStruct()
  {
    return null;
  }

  int widgetParent()
  {
    if (this.handle != 0)
      return this.handle;
    return this.parent != null ? this.parent.handle : 0;
  }

  int widgetExtStyle()
  {
    int i = super.widgetExtStyle() & 0xFFFFFFBF;
    if ((this.style & 0x4) != 0)
      i |= 128;
    if ((!OS.IsWinCE) && (this.parent == null) && ((this.style & 0x4000) != 0))
    {
      int j = 1248;
      if (((this.style & 0x8) != 0) || ((this.style & j) == 0))
        i |= 128;
    }
    if (this.parent != null)
    {
      if (OS.IsWin95)
        return i;
      if (OS.WIN32_VERSION < OS.VERSION(4, 10))
        return i;
    }
    if ((this.style & 0x4000) != 0)
      i |= 8;
    return i;
  }

  TCHAR windowClass()
  {
    if (OS.IsSP)
      return DialogClass;
    if ((this.style & 0x4) != 0)
    {
      int i = 3312;
      if ((this.style & i) == 0)
        return this.display.windowShadowClass;
    }
    return this.parent != null ? DialogClass : super.windowClass();
  }

  int windowProc()
  {
    if (this.windowProc != 0)
      return this.windowProc;
    if (OS.IsSP)
      return DialogProc;
    if ((this.style & 0x4) != 0)
    {
      int i = 3312;
      if ((this.style & i) == 0)
        return super.windowProc();
    }
    return this.parent != null ? DialogProc : super.windowProc();
  }

  int windowProc(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.handle == 0)
      return 0;
    if ((paramInt1 == this.toolTipHandle) || (paramInt1 == this.balloonTipHandle))
    {
      ToolTip localToolTip;
      switch (paramInt2)
      {
      case 275:
        if (paramInt3 == 100)
        {
          localToolTip = getCurrentToolTip(paramInt1);
          if ((localToolTip != null) && (localToolTip.autoHide))
            localToolTip.setVisible(false);
        }
        break;
      case 513:
        localToolTip = getCurrentToolTip(paramInt1);
        if (localToolTip != null)
        {
          localToolTip.setVisible(false);
          localToolTip.sendSelectionEvent(13);
        }
        break;
      }
      return callWindowProc(paramInt1, paramInt2, paramInt3, paramInt4);
    }
    return super.windowProc(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  int widgetStyle()
  {
    int i = super.widgetStyle();
    if (this.handle != 0)
      return i | 0x40000000;
    i &= -1073741825;
    if (OS.IsWinCE)
    {
      if (OS.IsSP)
        return i | 0x80000000;
      return this.parent == null ? i : i | 0x80000000;
    }
    return i | OS.WS_OVERLAPPED | 0xC00000;
  }

  LRESULT WM_ACTIVATE(int paramInt1, int paramInt2)
  {
    if (OS.IsPPC)
    {
      if ((hooks(33)) || (hooks(34)))
      {
        int i = OS.LOWORD(paramInt1);
        int j = i != 0 ? this.handle : 0;
        for (int k = 193; k <= 198; k++)
          OS.SHSetAppKeyWndAssoc((byte)k, j);
      }
      if (OS.LOWORD(paramInt1) != 0)
        OS.SHSipPreference(this.handle, this.psai.fSipUp == 0 ? 1 : 0);
    }
    if ((OS.WIN32_VERSION >= OS.VERSION(5, 1)) && (OS.LOWORD(paramInt1) == 0) && (OS.IsDBLocale) && (this.hIMC != 0) && (OS.ImmGetOpenStatus(this.hIMC)))
      OS.ImmNotifyIME(this.hIMC, 21, 1, 0);
    LRESULT localLRESULT = super.WM_ACTIVATE(paramInt1, paramInt2);
    if ((OS.LOWORD(paramInt1) == 0) && ((paramInt2 == 0) || ((paramInt2 != this.toolTipHandle) && (paramInt2 != this.balloonTipHandle))))
    {
      ToolTip localToolTip = getCurrentToolTip();
      if (localToolTip != null)
        localToolTip.setVisible(false);
    }
    return this.parent != null ? LRESULT.ZERO : localLRESULT;
  }

  LRESULT WM_COMMAND(int paramInt1, int paramInt2)
  {
    int i;
    if (OS.IsPPC)
    {
      i = OS.LOWORD(paramInt1);
      if ((i == 1) && ((paramInt2 == 0) || (paramInt2 == this.handle)))
      {
        OS.PostMessage(this.handle, 16, 0, 0);
        return LRESULT.ZERO;
      }
    }
    if (((OS.IsPPC) || (OS.IsSP)) && (this.menuBar != null))
    {
      i = this.menuBar.hwndCB;
      if ((paramInt2 != 0) && (i != 0))
      {
        if (paramInt2 == i)
          return super.WM_COMMAND(paramInt1, 0);
        int j = OS.GetWindow(i, 5);
        if (paramInt2 == j)
          return super.WM_COMMAND(paramInt1, 0);
      }
    }
    return super.WM_COMMAND(paramInt1, paramInt2);
  }

  LRESULT WM_DESTROY(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_DESTROY(paramInt1, paramInt2);
    int i = OS.GetWindowLong(this.handle, -16);
    if ((i & 0x40000000) != 0)
    {
      releaseParent();
      release(false);
    }
    return localLRESULT;
  }

  LRESULT WM_ERASEBKGND(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_ERASEBKGND(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(6, 0)))
    {
      drawBackground(paramInt1);
      return LRESULT.ONE;
    }
    return localLRESULT;
  }

  LRESULT WM_ENTERIDLE(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_ENTERIDLE(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    Display localDisplay = this.display;
    if ((localDisplay.runMessages) && (localDisplay.runAsyncMessages(false)))
      localDisplay.wakeThread();
    return localLRESULT;
  }

  LRESULT WM_GETMINMAXINFO(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_GETMINMAXINFO(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    if ((this.minWidth != -1) || (this.minHeight != -1))
    {
      MINMAXINFO localMINMAXINFO = new MINMAXINFO();
      OS.MoveMemory(localMINMAXINFO, paramInt2, MINMAXINFO.sizeof);
      if (this.minWidth != -1)
        localMINMAXINFO.ptMinTrackSize_x = this.minWidth;
      if (this.minHeight != -1)
        localMINMAXINFO.ptMinTrackSize_y = this.minHeight;
      OS.MoveMemory(paramInt2, localMINMAXINFO, MINMAXINFO.sizeof);
      return LRESULT.ZERO;
    }
    return localLRESULT;
  }

  LRESULT WM_MOUSEACTIVATE(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_MOUSEACTIVATE(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    int i = (short)OS.LOWORD(paramInt2);
    switch (i)
    {
    case -2:
    case -1:
    case 0:
      break;
    default:
      localObject = this.display._getFocusControl();
      if (localObject != null)
      {
        Decorations localDecorations = ((Control)localObject).menuShell();
        if ((localDecorations.getShell() == this) && (localDecorations != this))
        {
          this.display.ignoreRestoreFocus = true;
          this.display.lastHittest = i;
          this.display.lastHittestControl = null;
          if ((i == 5) || (i == 3))
          {
            this.display.lastHittestControl = ((Control)localObject);
            return null;
          }
          if ((OS.IsWin95) && (i == 2))
            this.display.lastHittestControl = ((Control)localObject);
          return new LRESULT(3);
        }
      }
      break;
    }
    if (i == 5)
      return null;
    Object localObject = new POINT();
    if (!OS.GetCursorPos((POINT)localObject))
    {
      j = OS.GetMessagePos();
      OS.POINTSTOPOINT((POINT)localObject, j);
    }
    int j = OS.WindowFromPoint((POINT)localObject);
    if (j == 0)
      return null;
    Control localControl = this.display.findControl(j);
    if ((localControl != null) && ((localControl.state & 0x2) != 0) && ((localControl.style & 0x80000) != 0))
    {
      k = 540672;
      if (((this.style & k) == k) && ((i == 18) || (i == 1)))
        return new LRESULT(3);
    }
    int k = callWindowProc(this.handle, 33, paramInt1, paramInt2);
    setActiveControl(localControl);
    return new LRESULT(k);
  }

  LRESULT WM_MOVE(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_MOVE(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    ToolTip localToolTip = getCurrentToolTip();
    if (localToolTip != null)
      localToolTip.setVisible(false);
    return localLRESULT;
  }

  LRESULT WM_NCHITTEST(int paramInt1, int paramInt2)
  {
    if (!OS.IsWindowEnabled(this.handle))
      return null;
    int i;
    if ((!isEnabled()) || (!isActive()))
    {
      if (!Display.TrimEnabled)
        return new LRESULT(0);
      i = callWindowProc(this.handle, 132, paramInt1, paramInt2);
      if ((i == 1) || (i == 5))
        i = 18;
      return new LRESULT(i);
    }
    if ((this.menuBar != null) && (!this.menuBar.getEnabled()))
    {
      i = callWindowProc(this.handle, 132, paramInt1, paramInt2);
      if (i == 5)
        i = 18;
      return new LRESULT(i);
    }
    return null;
  }

  LRESULT WM_NCLBUTTONDOWN(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_NCLBUTTONDOWN(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    if (!this.display.ignoreRestoreFocus)
      return localLRESULT;
    Display localDisplay = this.display;
    int i = 0;
    int j = (OS.IsWin95) && (localDisplay.lastHittest == 2) ? 1 : 0;
    if (j != 0)
      i = OS.SetActiveWindow(this.handle);
    localDisplay.lockActiveWindow = true;
    int k = callWindowProc(this.handle, 161, paramInt1, paramInt2);
    localDisplay.lockActiveWindow = false;
    if (j != 0)
      OS.SetActiveWindow(i);
    Control localControl = localDisplay.lastHittestControl;
    if ((localControl != null) && (!localControl.isDisposed()))
      localControl.setFocus();
    localDisplay.lastHittestControl = null;
    localDisplay.ignoreRestoreFocus = false;
    return new LRESULT(k);
  }

  LRESULT WM_PALETTECHANGED(int paramInt1, int paramInt2)
  {
    if (paramInt1 != this.handle)
    {
      int i = this.display.hPalette;
      if (i != 0)
        return selectPalette(i);
    }
    return super.WM_PALETTECHANGED(paramInt1, paramInt2);
  }

  LRESULT WM_QUERYNEWPALETTE(int paramInt1, int paramInt2)
  {
    int i = this.display.hPalette;
    if (i != 0)
      return selectPalette(i);
    return super.WM_QUERYNEWPALETTE(paramInt1, paramInt2);
  }

  LRESULT WM_SETCURSOR(int paramInt1, int paramInt2)
  {
    int i = OS.HIWORD(paramInt2);
    if (i == 513)
    {
      if (!Display.TrimEnabled)
      {
        Shell localShell = this.display.getModalShell();
        if ((localShell != null) && (!isActive()))
        {
          int k = localShell.handle;
          if (OS.IsWindowEnabled(k))
            OS.SetActiveWindow(k);
        }
      }
      if ((!OS.IsWindowEnabled(this.handle)) && (!OS.IsWinCE))
      {
        j = OS.GetLastActivePopup(this.handle);
        if ((j != 0) && (j != this.handle) && (this.display.getControl(j) == null) && (OS.IsWindowEnabled(j)))
          OS.SetActiveWindow(j);
      }
    }
    int j = (short)OS.LOWORD(paramInt2);
    if ((j == -2) && (!getEnabled()))
    {
      Control localControl = this.display.getControl(paramInt1);
      if ((localControl == this) && (this.cursor != null))
      {
        POINT localPOINT = new POINT();
        int m = OS.GetMessagePos();
        OS.POINTSTOPOINT(localPOINT, m);
        OS.ScreenToClient(this.handle, localPOINT);
        RECT localRECT = new RECT();
        OS.GetClientRect(this.handle, localRECT);
        if (OS.PtInRect(localRECT, localPOINT))
        {
          OS.SetCursor(this.cursor.handle);
          switch (i)
          {
          case 513:
          case 516:
          case 519:
          case 523:
            OS.MessageBeep(0);
          }
          return LRESULT.ONE;
        }
      }
    }
    return super.WM_SETCURSOR(paramInt1, paramInt2);
  }

  LRESULT WM_SETTINGCHANGE(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_SETTINGCHANGE(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    if ((OS.IsPPC) && (paramInt1 == 224))
    {
      if ((this.style & 0x10) != 0)
      {
        OS.SHHandleWMSettingChange(this.handle, paramInt1, paramInt2, this.psai);
        return LRESULT.ZERO;
      }
      SIPINFO localSIPINFO = new SIPINFO();
      localSIPINFO.cbSize = SIPINFO.sizeof;
      OS.SipGetInfo(localSIPINFO);
      this.psai.fSipUp = (localSIPINFO.fdwFlags & 0x1);
    }
    return localLRESULT;
  }

  LRESULT WM_SHOWWINDOW(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_SHOWWINDOW(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    if (paramInt2 == 3)
      for (Object localObject = this; localObject != null; localObject = ((Control)localObject).parent)
      {
        Shell localShell = ((Control)localObject).getShell();
        if (!localShell.showWithParent)
          return LRESULT.ZERO;
      }
    return localLRESULT;
  }

  LRESULT WM_SYSCOMMAND(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_SYSCOMMAND(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    if (OS.IsWinNT)
    {
      int i = paramInt1 & 0xFFF0;
      switch (i)
      {
      case 61472:
        long l = Runtime.getRuntime().totalMemory();
        if (l >= 33554432L)
        {
          OS.ShowWindow(this.handle, 2);
          return LRESULT.ZERO;
        }
        break;
      }
    }
    return localLRESULT;
  }

  LRESULT WM_WINDOWPOSCHANGING(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_WINDOWPOSCHANGING(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    WINDOWPOS localWINDOWPOS = new WINDOWPOS();
    OS.MoveMemory(localWINDOWPOS, paramInt2, WINDOWPOS.sizeof);
    if ((localWINDOWPOS.flags & 0x1) == 0)
    {
      localWINDOWPOS.cx = Math.max(localWINDOWPOS.cx, this.minWidth);
      int i = 1248;
      if (((this.style & 0x8) == 0) && ((this.style & i) != 0))
        localWINDOWPOS.cx = Math.max(localWINDOWPOS.cx, OS.GetSystemMetrics(34));
      localWINDOWPOS.cy = Math.max(localWINDOWPOS.cy, this.minHeight);
      if (((this.style & 0x8) == 0) && ((this.style & i) != 0))
        if ((this.style & 0x10) != 0)
        {
          localWINDOWPOS.cy = Math.max(localWINDOWPOS.cy, OS.GetSystemMetrics(35));
        }
        else
        {
          RECT localRECT = new RECT();
          int j = OS.GetWindowLong(this.handle, -16);
          int k = OS.GetWindowLong(this.handle, -20);
          OS.AdjustWindowRectEx(localRECT, j, false, k);
          localWINDOWPOS.cy = Math.max(localWINDOWPOS.cy, localRECT.bottom - localRECT.top);
        }
      OS.MoveMemory(paramInt2, localWINDOWPOS, WINDOWPOS.sizeof);
    }
    return localLRESULT;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.Shell
 * JD-Core Version:    0.6.2
 */