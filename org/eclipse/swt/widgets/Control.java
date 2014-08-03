package org.eclipse.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.accessibility.Accessible;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.DragDetectListener;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.HelpListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Drawable;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.GCData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.internal.gdip.Gdip;
import org.eclipse.swt.internal.win32.CREATESTRUCT;
import org.eclipse.swt.internal.win32.DRAWITEMSTRUCT;
import org.eclipse.swt.internal.win32.HELPINFO;
import org.eclipse.swt.internal.win32.LOGFONT;
import org.eclipse.swt.internal.win32.LOGFONTA;
import org.eclipse.swt.internal.win32.LOGFONTW;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.MEASUREITEMSTRUCT;
import org.eclipse.swt.internal.win32.MENUITEMINFO;
import org.eclipse.swt.internal.win32.MONITORINFO;
import org.eclipse.swt.internal.win32.MSG;
import org.eclipse.swt.internal.win32.NMHDR;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.WINDOWPOS;

public abstract class Control extends Widget
  implements Drawable
{
  public int handle;
  Composite parent;
  Cursor cursor;
  Menu menu;
  String toolTipText;
  Object layoutData;
  Accessible accessible;
  Image backgroundImage;
  Region region;
  Font font;
  int drawCount;
  int foreground;
  int background;

  Control()
  {
  }

  public Control(Composite paramComposite, int paramInt)
  {
    super(paramComposite, paramInt);
    this.parent = paramComposite;
    createWidget();
  }

  public void addControlListener(ControlListener paramControlListener)
  {
    checkWidget();
    if (paramControlListener == null)
      error(4);
    TypedListener localTypedListener = new TypedListener(paramControlListener);
    addListener(11, localTypedListener);
    addListener(10, localTypedListener);
  }

  public void addDragDetectListener(DragDetectListener paramDragDetectListener)
  {
    checkWidget();
    if (paramDragDetectListener == null)
      error(4);
    TypedListener localTypedListener = new TypedListener(paramDragDetectListener);
    addListener(29, localTypedListener);
  }

  public void addFocusListener(FocusListener paramFocusListener)
  {
    checkWidget();
    if (paramFocusListener == null)
      error(4);
    TypedListener localTypedListener = new TypedListener(paramFocusListener);
    addListener(15, localTypedListener);
    addListener(16, localTypedListener);
  }

  public void addHelpListener(HelpListener paramHelpListener)
  {
    checkWidget();
    if (paramHelpListener == null)
      error(4);
    TypedListener localTypedListener = new TypedListener(paramHelpListener);
    addListener(28, localTypedListener);
  }

  public void addKeyListener(KeyListener paramKeyListener)
  {
    checkWidget();
    if (paramKeyListener == null)
      error(4);
    TypedListener localTypedListener = new TypedListener(paramKeyListener);
    addListener(2, localTypedListener);
    addListener(1, localTypedListener);
  }

  public void addMenuDetectListener(MenuDetectListener paramMenuDetectListener)
  {
    checkWidget();
    if (paramMenuDetectListener == null)
      error(4);
    TypedListener localTypedListener = new TypedListener(paramMenuDetectListener);
    addListener(35, localTypedListener);
  }

  public void addMouseListener(MouseListener paramMouseListener)
  {
    checkWidget();
    if (paramMouseListener == null)
      error(4);
    TypedListener localTypedListener = new TypedListener(paramMouseListener);
    addListener(3, localTypedListener);
    addListener(4, localTypedListener);
    addListener(8, localTypedListener);
  }

  public void addMouseTrackListener(MouseTrackListener paramMouseTrackListener)
  {
    checkWidget();
    if (paramMouseTrackListener == null)
      error(4);
    TypedListener localTypedListener = new TypedListener(paramMouseTrackListener);
    addListener(6, localTypedListener);
    addListener(7, localTypedListener);
    addListener(32, localTypedListener);
  }

  public void addMouseMoveListener(MouseMoveListener paramMouseMoveListener)
  {
    checkWidget();
    if (paramMouseMoveListener == null)
      error(4);
    TypedListener localTypedListener = new TypedListener(paramMouseMoveListener);
    addListener(5, localTypedListener);
  }

  public void addMouseWheelListener(MouseWheelListener paramMouseWheelListener)
  {
    checkWidget();
    if (paramMouseWheelListener == null)
      error(4);
    TypedListener localTypedListener = new TypedListener(paramMouseWheelListener);
    addListener(37, localTypedListener);
  }

  public void addPaintListener(PaintListener paramPaintListener)
  {
    checkWidget();
    if (paramPaintListener == null)
      error(4);
    TypedListener localTypedListener = new TypedListener(paramPaintListener);
    addListener(9, localTypedListener);
  }

  public void addTraverseListener(TraverseListener paramTraverseListener)
  {
    checkWidget();
    if (paramTraverseListener == null)
      error(4);
    TypedListener localTypedListener = new TypedListener(paramTraverseListener);
    addListener(31, localTypedListener);
  }

  int binarySearch(int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = paramInt1;
    int j = paramInt2 - 1;
    while (i <= j)
    {
      int k = i + j >>> 1;
      if (paramArrayOfInt[k] == paramInt3)
        return k;
      if (paramArrayOfInt[k] < paramInt3)
        i = k + 1;
      else
        j = k - 1;
    }
    return -i - 1;
  }

  int borderHandle()
  {
    return this.handle;
  }

  void checkBackground()
  {
    Shell localShell = getShell();
    if (this == localShell)
      return;
    this.state &= -1025;
    for (Composite localComposite = this.parent; ; localComposite = localComposite.parent)
    {
      int i = localComposite.backgroundMode;
      if (i != 0)
      {
        if (i == 1)
        {
          Object localObject = this;
          do
          {
            if ((((Control)localObject).state & 0x100) == 0)
              return;
            localObject = ((Control)localObject).parent;
          }
          while (localObject != localComposite);
        }
        this.state |= 1024;
        return;
      }
      if (localComposite == localShell)
        break;
    }
  }

  void checkBorder()
  {
    if (getBorderWidth() == 0)
      this.style &= -2049;
  }

  void checkBuffered()
  {
    this.style &= -536870913;
  }

  void checkComposited()
  {
  }

  boolean checkHandle(int paramInt)
  {
    return paramInt == this.handle;
  }

  void checkMirrored()
  {
    if ((this.style & 0x4000000) != 0)
    {
      int i = OS.GetWindowLong(this.handle, -20);
      if ((i & 0x400000) != 0)
        this.style |= 134217728;
    }
  }

  public Point computeSize(int paramInt1, int paramInt2)
  {
    return computeSize(paramInt1, paramInt2, true);
  }

  public Point computeSize(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    checkWidget();
    int i = 64;
    int j = 64;
    if (paramInt1 != -1)
      i = paramInt1;
    if (paramInt2 != -1)
      j = paramInt2;
    int k = getBorderWidth();
    i += k * 2;
    j += k * 2;
    return new Point(i, j);
  }

  Widget computeTabGroup()
  {
    if (isTabGroup())
      return this;
    return this.parent.computeTabGroup();
  }

  Control computeTabRoot()
  {
    Control[] arrayOfControl = this.parent._getTabList();
    if (arrayOfControl != null)
    {
      for (int i = 0; i < arrayOfControl.length; i++)
        if (arrayOfControl[i] == this)
          break;
      if ((i == arrayOfControl.length) && (isTabGroup()))
        return this;
    }
    return this.parent.computeTabRoot();
  }

  Widget[] computeTabList()
  {
    if ((isTabGroup()) && (getVisible()) && (getEnabled()))
      return new Widget[] { this };
    return new Widget[0];
  }

  void createHandle()
  {
    int i = widgetParent();
    this.handle = OS.CreateWindowEx(widgetExtStyle(), windowClass(), null, widgetStyle(), -2147483648, 0, -2147483648, 0, i, 0, OS.GetModuleHandle(null), widgetCreateStruct());
    if (this.handle == 0)
      error(2);
    int j = OS.GetWindowLong(this.handle, -16);
    if ((j & 0x40000000) != 0)
      OS.SetWindowLongPtr(this.handle, -12, this.handle);
    if ((OS.IsDBLocale) && (i != 0))
    {
      int k = OS.ImmGetContext(i);
      OS.ImmAssociateContext(this.handle, k);
      OS.ImmReleaseContext(i, k);
    }
  }

  void createWidget()
  {
    this.state |= 32768;
    this.foreground = (this.background = -1);
    checkOrientation(this.parent);
    createHandle();
    checkBackground();
    checkBuffered();
    checkComposited();
    register();
    subclass();
    setDefaultFont();
    checkMirrored();
    checkBorder();
    if ((this.state & 0x400) != 0)
      setBackground();
  }

  int defaultBackground()
  {
    if (OS.IsWinCE)
      return OS.GetSysColor(OS.COLOR_WINDOW);
    return OS.GetSysColor(OS.COLOR_BTNFACE);
  }

  int defaultFont()
  {
    return this.display.getSystemFont().handle;
  }

  int defaultForeground()
  {
    return OS.GetSysColor(OS.COLOR_WINDOWTEXT);
  }

  void deregister()
  {
    this.display.removeControl(this.handle);
  }

  void destroyWidget()
  {
    int i = topHandle();
    releaseHandle();
    if (i != 0)
      OS.DestroyWindow(i);
  }

  public boolean dragDetect(Event paramEvent)
  {
    checkWidget();
    if (paramEvent == null)
      error(4);
    return dragDetect(paramEvent.button, paramEvent.count, paramEvent.stateMask, paramEvent.x, paramEvent.y);
  }

  public boolean dragDetect(MouseEvent paramMouseEvent)
  {
    checkWidget();
    if (paramMouseEvent == null)
      error(4);
    return dragDetect(paramMouseEvent.button, paramMouseEvent.count, paramMouseEvent.stateMask, paramMouseEvent.x, paramMouseEvent.y);
  }

  boolean dragDetect(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    if ((paramInt1 != 1) || (paramInt2 != 1))
      return false;
    boolean bool = dragDetect(this.handle, paramInt4, paramInt5, false, null, null);
    if ((OS.GetKeyState(1) < 0) && (OS.GetCapture() != this.handle))
      OS.SetCapture(this.handle);
    if (!bool)
    {
      if ((paramInt1 == 1) && (OS.GetKeyState(27) >= 0))
      {
        int i = 0;
        if ((paramInt3 & 0x40000) != 0)
          i |= 8;
        if ((paramInt3 & 0x20000) != 0)
          i |= 4;
        if ((paramInt3 & 0x10000) != 0)
          i |= 32;
        if ((paramInt3 & 0x80000) != 0)
          i |= 1;
        if ((paramInt3 & 0x100000) != 0)
          i |= 16;
        if ((paramInt3 & 0x200000) != 0)
          i |= 2;
        if ((paramInt3 & 0x800000) != 0)
          i |= 32;
        if ((paramInt3 & 0x2000000) != 0)
          i |= 64;
        int j = OS.MAKELPARAM(paramInt4, paramInt5);
        OS.SendMessage(this.handle, 514, i, j);
      }
      return false;
    }
    return sendDragEvent(paramInt1, paramInt3, paramInt4, paramInt5);
  }

  void drawBackground(int paramInt)
  {
    RECT localRECT = new RECT();
    OS.GetClientRect(this.handle, localRECT);
    drawBackground(paramInt, localRECT);
  }

  void drawBackground(int paramInt, RECT paramRECT)
  {
    drawBackground(paramInt, paramRECT, -1, 0, 0);
  }

  void drawBackground(int paramInt1, RECT paramRECT, int paramInt2, int paramInt3, int paramInt4)
  {
    Control localControl = findBackgroundControl();
    if (localControl != null)
    {
      if (localControl.backgroundImage != null)
      {
        fillImageBackground(paramInt1, localControl, paramRECT, paramInt3, paramInt4);
        return;
      }
      paramInt2 = localControl.getBackgroundPixel();
    }
    if ((paramInt2 == -1) && ((this.state & 0x100) != 0) && (OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()))
    {
      localControl = findThemeControl();
      if (localControl != null)
      {
        fillThemeBackground(paramInt1, localControl, paramRECT);
        return;
      }
    }
    if (paramInt2 == -1)
      paramInt2 = getBackgroundPixel();
    fillBackground(paramInt1, paramInt2, paramRECT);
  }

  void drawImageBackground(int paramInt1, int paramInt2, int paramInt3, RECT paramRECT, int paramInt4, int paramInt5)
  {
    RECT localRECT = new RECT();
    OS.GetClientRect(paramInt2, localRECT);
    OS.MapWindowPoints(paramInt2, this.handle, localRECT, 2);
    int i = findBrush(paramInt3, 3);
    POINT localPOINT = new POINT();
    OS.GetWindowOrgEx(paramInt1, localPOINT);
    OS.SetBrushOrgEx(paramInt1, -localRECT.left - localPOINT.x - paramInt4, -localRECT.top - localPOINT.y - paramInt5, localPOINT);
    int j = OS.SelectObject(paramInt1, i);
    OS.PatBlt(paramInt1, paramRECT.left, paramRECT.top, paramRECT.right - paramRECT.left, paramRECT.bottom - paramRECT.top, 15728673);
    OS.SetBrushOrgEx(paramInt1, localPOINT.x, localPOINT.y, null);
    OS.SelectObject(paramInt1, j);
  }

  void drawThemeBackground(int paramInt1, int paramInt2, RECT paramRECT)
  {
  }

  void enableDrag(boolean paramBoolean)
  {
  }

  void enableWidget(boolean paramBoolean)
  {
    OS.EnableWindow(this.handle, paramBoolean);
  }

  void fillBackground(int paramInt1, int paramInt2, RECT paramRECT)
  {
    if ((paramRECT.left > paramRECT.right) || (paramRECT.top > paramRECT.bottom))
      return;
    int i = this.display.hPalette;
    if (i != 0)
    {
      OS.SelectPalette(paramInt1, i, false);
      OS.RealizePalette(paramInt1);
    }
    OS.FillRect(paramInt1, paramRECT, findBrush(paramInt2, 0));
  }

  void fillImageBackground(int paramInt1, Control paramControl, RECT paramRECT, int paramInt2, int paramInt3)
  {
    if ((paramRECT.left > paramRECT.right) || (paramRECT.top > paramRECT.bottom))
      return;
    if (paramControl != null)
    {
      Image localImage = paramControl.backgroundImage;
      if (localImage != null)
        paramControl.drawImageBackground(paramInt1, this.handle, localImage.handle, paramRECT, paramInt2, paramInt3);
    }
  }

  void fillThemeBackground(int paramInt, Control paramControl, RECT paramRECT)
  {
    if ((paramRECT.left > paramRECT.right) || (paramRECT.top > paramRECT.bottom))
      return;
    if (paramControl != null)
      paramControl.drawThemeBackground(paramInt, this.handle, paramRECT);
  }

  Control findBackgroundControl()
  {
    if ((this.background != -1) || (this.backgroundImage != null))
      return this;
    return (this.state & 0x400) != 0 ? this.parent.findBackgroundControl() : null;
  }

  int findBrush(int paramInt1, int paramInt2)
  {
    return this.parent.findBrush(paramInt1, paramInt2);
  }

  Cursor findCursor()
  {
    if (this.cursor != null)
      return this.cursor;
    return this.parent.findCursor();
  }

  Control findImageControl()
  {
    Control localControl = findBackgroundControl();
    return (localControl != null) && (localControl.backgroundImage != null) ? localControl : null;
  }

  Control findThemeControl()
  {
    return (this.background == -1) && (this.backgroundImage == null) ? this.parent.findThemeControl() : null;
  }

  Menu[] findMenus(Control paramControl)
  {
    if ((this.menu != null) && (this != paramControl))
      return new Menu[] { this.menu };
    return new Menu[0];
  }

  char findMnemonic(String paramString)
  {
    int i = 0;
    int j = paramString.length();
    do
    {
      while ((i < j) && (paramString.charAt(i) != '&'))
        i++;
      i++;
      if (i >= j)
        return '\000';
      if (paramString.charAt(i) != '&')
        return paramString.charAt(i);
      i++;
    }
    while (i < j);
    return '\000';
  }

  void fixChildren(Shell paramShell1, Shell paramShell2, Decorations paramDecorations1, Decorations paramDecorations2, Menu[] paramArrayOfMenu)
  {
    paramShell2.fixShell(paramShell1, this);
    paramDecorations2.fixDecorations(paramDecorations1, this, paramArrayOfMenu);
  }

  void fixFocus(Control paramControl)
  {
    Shell localShell = getShell();
    Object localObject = this;
    while ((localObject != localShell) && ((localObject = ((Control)localObject).parent) != null))
      if (((Control)localObject).setFixedFocus())
        return;
    localShell.setSavedFocus(paramControl);
    OS.SetFocus(0);
  }

  public boolean forceFocus()
  {
    checkWidget();
    if (this.display.focusEvent == 16)
      return false;
    Decorations localDecorations = menuShell();
    localDecorations.setSavedFocus(this);
    if ((!isEnabled()) || (!isVisible()) || (!isActive()))
      return false;
    if (isFocusControl())
      return true;
    localDecorations.setSavedFocus(null);
    OS.SetFocus(this.handle);
    if (isDisposed())
      return false;
    localDecorations.setSavedFocus(this);
    return isFocusControl();
  }

  void forceResize()
  {
    if (this.parent == null)
      return;
    WINDOWPOS[] arrayOfWINDOWPOS = this.parent.lpwp;
    if (arrayOfWINDOWPOS == null)
      return;
    for (int i = 0; i < arrayOfWINDOWPOS.length; i++)
    {
      WINDOWPOS localWINDOWPOS = arrayOfWINDOWPOS[i];
      if ((localWINDOWPOS != null) && (localWINDOWPOS.hwnd == this.handle))
      {
        SetWindowPos(localWINDOWPOS.hwnd, 0, localWINDOWPOS.x, localWINDOWPOS.y, localWINDOWPOS.cx, localWINDOWPOS.cy, localWINDOWPOS.flags);
        arrayOfWINDOWPOS[i] = null;
        return;
      }
    }
  }

  public Accessible getAccessible()
  {
    checkWidget();
    if (this.accessible == null)
      this.accessible = new_Accessible(this);
    return this.accessible;
  }

  public Color getBackground()
  {
    checkWidget();
    Control localControl = findBackgroundControl();
    if (localControl == null)
      localControl = this;
    return Color.win32_new(this.display, localControl.getBackgroundPixel());
  }

  public Image getBackgroundImage()
  {
    checkWidget();
    Control localControl = findBackgroundControl();
    if (localControl == null)
      localControl = this;
    return localControl.backgroundImage;
  }

  int getBackgroundPixel()
  {
    return this.background != -1 ? this.background : defaultBackground();
  }

  public int getBorderWidth()
  {
    checkWidget();
    int i = borderHandle();
    int j = OS.GetWindowLong(i, -20);
    if ((j & 0x200) != 0)
      return OS.GetSystemMetrics(45);
    if ((j & 0x20000) != 0)
      return OS.GetSystemMetrics(5);
    int k = OS.GetWindowLong(i, -16);
    if ((k & 0x800000) != 0)
      return OS.GetSystemMetrics(5);
    return 0;
  }

  public Rectangle getBounds()
  {
    checkWidget();
    forceResize();
    RECT localRECT = new RECT();
    OS.GetWindowRect(topHandle(), localRECT);
    int i = this.parent == null ? 0 : this.parent.handle;
    OS.MapWindowPoints(0, i, localRECT, 2);
    int j = localRECT.right - localRECT.left;
    int k = localRECT.bottom - localRECT.top;
    return new Rectangle(localRECT.left, localRECT.top, j, k);
  }

  int getCodePage()
  {
    if (OS.IsUnicode)
      return 0;
    int i = OS.SendMessage(this.handle, 49, 0, 0);
    LOGFONTA localLOGFONTA = OS.IsUnicode ? new LOGFONTW() : new LOGFONTA();
    OS.GetObject(i, LOGFONT.sizeof, localLOGFONTA);
    int j = localLOGFONTA.lfCharSet & 0xFF;
    int[] arrayOfInt = new int[8];
    if (OS.TranslateCharsetInfo(j, arrayOfInt, 1))
      return arrayOfInt[1];
    return OS.GetACP();
  }

  String getClipboardText()
  {
    String str = "";
    if (OS.OpenClipboard(0))
    {
      int i = OS.GetClipboardData(OS.IsUnicode ? 13 : 1);
      if (i != 0)
      {
        int j = OS.GlobalSize(i) / TCHAR.sizeof * TCHAR.sizeof;
        int k = OS.GlobalLock(i);
        if (k != 0)
        {
          TCHAR localTCHAR = new TCHAR(0, j / TCHAR.sizeof);
          OS.MoveMemory(localTCHAR, k, j);
          str = localTCHAR.toString(0, localTCHAR.strlen());
          OS.GlobalUnlock(i);
        }
      }
      OS.CloseClipboard();
    }
    return str;
  }

  public Cursor getCursor()
  {
    checkWidget();
    return this.cursor;
  }

  public boolean getDragDetect()
  {
    checkWidget();
    return (this.state & 0x8000) != 0;
  }

  boolean getDrawing()
  {
    return this.drawCount <= 0;
  }

  public boolean getEnabled()
  {
    checkWidget();
    return OS.IsWindowEnabled(this.handle);
  }

  public Font getFont()
  {
    checkWidget();
    if (this.font != null)
      return this.font;
    int i = OS.SendMessage(this.handle, 49, 0, 0);
    if (i == 0)
      i = defaultFont();
    return Font.win32_new(this.display, i);
  }

  public Color getForeground()
  {
    checkWidget();
    return Color.win32_new(this.display, getForegroundPixel());
  }

  int getForegroundPixel()
  {
    return this.foreground != -1 ? this.foreground : defaultForeground();
  }

  public Object getLayoutData()
  {
    checkWidget();
    return this.layoutData;
  }

  public Point getLocation()
  {
    checkWidget();
    forceResize();
    RECT localRECT = new RECT();
    OS.GetWindowRect(topHandle(), localRECT);
    int i = this.parent == null ? 0 : this.parent.handle;
    OS.MapWindowPoints(0, i, localRECT, 2);
    return new Point(localRECT.left, localRECT.top);
  }

  public Menu getMenu()
  {
    checkWidget();
    return this.menu;
  }

  public Monitor getMonitor()
  {
    checkWidget();
    if ((OS.IsWinCE) || (OS.WIN32_VERSION < OS.VERSION(4, 10)))
      return this.display.getPrimaryMonitor();
    int i = OS.MonitorFromWindow(this.handle, 2);
    MONITORINFO localMONITORINFO = new MONITORINFO();
    localMONITORINFO.cbSize = MONITORINFO.sizeof;
    OS.GetMonitorInfo(i, localMONITORINFO);
    Monitor localMonitor = new Monitor();
    localMonitor.handle = i;
    localMonitor.x = localMONITORINFO.rcMonitor_left;
    localMonitor.y = localMONITORINFO.rcMonitor_top;
    localMonitor.width = (localMONITORINFO.rcMonitor_right - localMONITORINFO.rcMonitor_left);
    localMonitor.height = (localMONITORINFO.rcMonitor_bottom - localMONITORINFO.rcMonitor_top);
    localMonitor.clientX = localMONITORINFO.rcWork_left;
    localMonitor.clientY = localMONITORINFO.rcWork_top;
    localMonitor.clientWidth = (localMONITORINFO.rcWork_right - localMONITORINFO.rcWork_left);
    localMonitor.clientHeight = (localMONITORINFO.rcWork_bottom - localMONITORINFO.rcWork_top);
    return localMonitor;
  }

  public Composite getParent()
  {
    checkWidget();
    return this.parent;
  }

  Control[] getPath()
  {
    int i = 0;
    Shell localShell = getShell();
    for (Object localObject = this; localObject != localShell; localObject = ((Control)localObject).parent)
      i++;
    localObject = this;
    Control[] arrayOfControl = new Control[i];
    while (localObject != localShell)
    {
      arrayOfControl[(--i)] = localObject;
      localObject = ((Control)localObject).parent;
    }
    return arrayOfControl;
  }

  public Region getRegion()
  {
    checkWidget();
    return this.region;
  }

  public Shell getShell()
  {
    checkWidget();
    return this.parent.getShell();
  }

  public Point getSize()
  {
    checkWidget();
    forceResize();
    RECT localRECT = new RECT();
    OS.GetWindowRect(topHandle(), localRECT);
    int i = localRECT.right - localRECT.left;
    int j = localRECT.bottom - localRECT.top;
    return new Point(i, j);
  }

  public String getToolTipText()
  {
    checkWidget();
    return this.toolTipText;
  }

  public boolean getVisible()
  {
    checkWidget();
    if (!getDrawing())
      return (this.state & 0x10) == 0;
    int i = OS.GetWindowLong(this.handle, -16);
    return (i & 0x10000000) != 0;
  }

  boolean hasCursor()
  {
    RECT localRECT = new RECT();
    if (!OS.GetClientRect(this.handle, localRECT))
      return false;
    OS.MapWindowPoints(this.handle, 0, localRECT, 2);
    POINT localPOINT = new POINT();
    return (OS.GetCursorPos(localPOINT)) && (OS.PtInRect(localRECT, localPOINT));
  }

  boolean hasFocus()
  {
    for (int i = OS.GetFocus(); i != 0; i = OS.GetParent(i))
    {
      if (i == this.handle)
        return true;
      if (this.display.getControl(i) != null)
        return false;
    }
    return false;
  }

  public int internal_new_GC(GCData paramGCData)
  {
    checkWidget();
    int i = this.handle;
    if ((paramGCData != null) && (paramGCData.hwnd != 0))
      i = paramGCData.hwnd;
    if (paramGCData != null)
      paramGCData.hwnd = i;
    int j = 0;
    if ((paramGCData == null) || (paramGCData.ps == null))
      j = OS.GetDC(i);
    else
      j = OS.BeginPaint(i, paramGCData.ps);
    if (j == 0)
      SWT.error(2);
    if (paramGCData != null)
    {
      if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(4, 10)))
      {
        k = 100663296;
        if ((paramGCData.style & k) != 0)
        {
          paramGCData.layout = ((paramGCData.style & 0x4000000) != 0 ? 1 : 0);
        }
        else
        {
          int m = OS.GetLayout(j);
          if ((m & 0x1) != 0)
            paramGCData.style |= 201326592;
          else
            paramGCData.style |= 33554432;
        }
      }
      else
      {
        paramGCData.style |= 33554432;
      }
      paramGCData.device = this.display;
      int k = getForegroundPixel();
      if (k != OS.GetTextColor(j))
        paramGCData.foreground = k;
      Control localControl = findBackgroundControl();
      if (localControl == null)
        localControl = this;
      int n = localControl.getBackgroundPixel();
      if (n != OS.GetBkColor(j))
        paramGCData.background = n;
      paramGCData.font = (this.font != null ? this.font : Font.win32_new(this.display, OS.SendMessage(i, 49, 0, 0)));
      paramGCData.uiState = OS.SendMessage(i, 297, 0, 0);
    }
    return j;
  }

  public void internal_dispose_GC(int paramInt, GCData paramGCData)
  {
    checkWidget();
    int i = this.handle;
    if ((paramGCData != null) && (paramGCData.hwnd != 0))
      i = paramGCData.hwnd;
    if ((paramGCData == null) || (paramGCData.ps == null))
      OS.ReleaseDC(i, paramInt);
    else
      OS.EndPaint(i, paramGCData.ps);
  }

  boolean isActive()
  {
    Dialog localDialog = this.display.getModalDialog();
    if (localDialog != null)
    {
      localShell1 = localDialog.parent;
      if ((localShell1 != null) && (!localShell1.isDisposed()) && (localShell1 != getShell()))
        return false;
    }
    Shell localShell1 = null;
    Shell[] arrayOfShell = this.display.modalShells;
    if (arrayOfShell != null)
    {
      int i = 196608;
      int j = arrayOfShell.length;
      do
      {
        Shell localShell2 = arrayOfShell[j];
        if (localShell2 != null)
        {
          if ((localShell2.style & i) != 0)
          {
            for (Object localObject = this; localObject != null; localObject = ((Control)localObject).parent)
              if (localObject == localShell2)
                break;
            if (localObject == localShell2)
              break;
            return false;
          }
          if ((localShell2.style & 0x8000) != 0)
          {
            if (localShell1 == null)
              localShell1 = getShell();
            if (localShell2.parent == localShell1)
              return false;
          }
        }
        j--;
      }
      while (j >= 0);
    }
    if (localShell1 == null)
      localShell1 = getShell();
    return localShell1.getEnabled();
  }

  public boolean isEnabled()
  {
    checkWidget();
    return (getEnabled()) && (this.parent.isEnabled());
  }

  public boolean isFocusControl()
  {
    checkWidget();
    Control localControl = this.display.focusControl;
    if ((localControl != null) && (!localControl.isDisposed()))
      return this == localControl;
    return hasFocus();
  }

  boolean isFocusAncestor(Control paramControl)
  {
    while ((paramControl != null) && (paramControl != this) && (!(paramControl instanceof Shell)))
      paramControl = paramControl.parent;
    return paramControl == this;
  }

  public boolean isReparentable()
  {
    checkWidget();
    return true;
  }

  boolean isShowing()
  {
    if (!isVisible())
      return false;
    for (Object localObject = this; localObject != null; localObject = ((Control)localObject).parent)
    {
      Point localPoint = ((Control)localObject).getSize();
      if ((localPoint.x == 0) || (localPoint.y == 0))
        return false;
    }
    return true;
  }

  boolean isTabGroup()
  {
    Control[] arrayOfControl = this.parent._getTabList();
    if (arrayOfControl != null)
      for (i = 0; i < arrayOfControl.length; i++)
        if (arrayOfControl[i] == this)
          return true;
    int i = OS.GetWindowLong(this.handle, -16);
    return (i & 0x10000) != 0;
  }

  boolean isTabItem()
  {
    Control[] arrayOfControl = this.parent._getTabList();
    if (arrayOfControl != null)
      for (i = 0; i < arrayOfControl.length; i++)
        if (arrayOfControl[i] == this)
          return false;
    int i = OS.GetWindowLong(this.handle, -16);
    if ((i & 0x10000) != 0)
      return false;
    int j = OS.SendMessage(this.handle, 135, 0, 0);
    if ((j & 0x100) != 0)
      return false;
    if ((j & 0x4) != 0)
      return false;
    if ((j & 0x1) != 0)
      return false;
    return (j & 0x2) == 0;
  }

  public boolean isVisible()
  {
    checkWidget();
    if (OS.IsWindowVisible(this.handle))
      return true;
    return (getVisible()) && (this.parent.isVisible());
  }

  void mapEvent(int paramInt, Event paramEvent)
  {
    if (paramInt != this.handle)
    {
      POINT localPOINT = new POINT();
      localPOINT.x = paramEvent.x;
      localPOINT.y = paramEvent.y;
      OS.MapWindowPoints(paramInt, this.handle, localPOINT, 1);
      paramEvent.x = localPOINT.x;
      paramEvent.y = localPOINT.y;
    }
  }

  void markLayout(boolean paramBoolean1, boolean paramBoolean2)
  {
  }

  Decorations menuShell()
  {
    return this.parent.menuShell();
  }

  boolean mnemonicHit(char paramChar)
  {
    return false;
  }

  boolean mnemonicMatch(char paramChar)
  {
    return false;
  }

  public void moveAbove(Control paramControl)
  {
    checkWidget();
    int i = topHandle();
    int j = 0;
    if (paramControl != null)
    {
      if (paramControl.isDisposed())
        error(5);
      if (this.parent != paramControl.parent)
        return;
      k = paramControl.topHandle();
      if ((k == 0) || (k == i))
        return;
      j = OS.GetWindow(k, 3);
      if ((j == 0) || (j == k))
        j = 0;
    }
    int k = 19;
    SetWindowPos(i, j, 0, 0, 0, 0, k);
  }

  public void moveBelow(Control paramControl)
  {
    checkWidget();
    int i = topHandle();
    int j = 1;
    if (paramControl != null)
    {
      if (paramControl.isDisposed())
        error(5);
      if (this.parent != paramControl.parent)
        return;
      j = paramControl.topHandle();
    }
    else
    {
      Shell localShell = getShell();
      if ((this == localShell) && (this.parent != null))
      {
        int m = this.parent.handle;
        int n = m;
        for (j = OS.GetWindow(n, 3); (j != 0) && (j != n); j = OS.GetWindow(n = j, 3))
          if (OS.GetWindow(j, 4) == m)
            break;
        if (j == n)
          return;
      }
    }
    if ((j == 0) || (j == i))
      return;
    int k = 19;
    SetWindowPos(i, j, 0, 0, 0, 0, k);
  }

  Accessible new_Accessible(Control paramControl)
  {
    return Accessible.internal_new_Accessible(this);
  }

  GC new_GC(GCData paramGCData)
  {
    return GC.win32_new(this, paramGCData);
  }

  public void pack()
  {
    checkWidget();
    pack(true);
  }

  public void pack(boolean paramBoolean)
  {
    checkWidget();
    setSize(computeSize(-1, -1, paramBoolean));
  }

  public boolean print(GC paramGC)
  {
    checkWidget();
    if (paramGC == null)
      error(4);
    if (paramGC.isDisposed())
      error(5);
    if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(5, 1)))
    {
      int i = topHandle();
      int j = paramGC.handle;
      int k = 0;
      int m = paramGC.getGCData().gdipGraphics;
      int n;
      if (m != 0)
      {
        n = 0;
        Gdip.Graphics_SetPixelOffsetMode(m, 3);
        int i1 = Gdip.Region_new();
        if (i1 == 0)
          SWT.error(2);
        Gdip.Graphics_GetClip(m, i1);
        if (!Gdip.Region_IsInfinite(i1, m))
          n = Gdip.Region_GetHRGN(i1, m);
        Gdip.Region_delete(i1);
        Gdip.Graphics_SetPixelOffsetMode(m, 4);
        float[] arrayOfFloat = (float[])null;
        int i2 = Gdip.Matrix_new(1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F);
        if (i2 == 0)
          SWT.error(2);
        Gdip.Graphics_GetTransform(m, i2);
        if (!Gdip.Matrix_IsIdentity(i2))
        {
          arrayOfFloat = new float[6];
          Gdip.Matrix_GetElements(i2, arrayOfFloat);
        }
        Gdip.Matrix_delete(i2);
        j = Gdip.Graphics_GetHDC(m);
        k = OS.SaveDC(j);
        if (arrayOfFloat != null)
        {
          OS.SetGraphicsMode(j, 2);
          OS.SetWorldTransform(j, arrayOfFloat);
        }
        if (n != 0)
        {
          OS.SelectClipRgn(j, n);
          OS.DeleteObject(n);
        }
      }
      if (OS.IsWinCE)
      {
        OS.UpdateWindow(i);
      }
      else
      {
        n = 384;
        OS.RedrawWindow(i, null, 0, n);
      }
      printWidget(i, j, paramGC);
      if (m != 0)
      {
        OS.RestoreDC(j, k);
        Gdip.Graphics_ReleaseHDC(m, j);
      }
      return true;
    }
    return false;
  }

  void printWidget(int paramInt1, int paramInt2, GC paramGC)
  {
    boolean bool = false;
    int i;
    if (OS.GetDeviceCaps(paramGC.handle, 2) != 2)
    {
      i = OS.GetParent(paramInt1);
      for (int j = i; OS.GetParent(j) != 0; j = OS.GetParent(j))
        if (OS.GetWindow(j, 4) != 0)
          break;
      RECT localRECT1 = new RECT();
      OS.GetWindowRect(paramInt1, localRECT1);
      int k = OS.IsWindowVisible(paramInt1) ? 0 : 1;
      if (k == 0)
      {
        RECT localRECT2 = new RECT();
        OS.GetWindowRect(j, localRECT2);
        OS.IntersectRect(localRECT2, localRECT1, localRECT2);
        k = OS.EqualRect(localRECT2, localRECT1) ? 0 : 1;
      }
      if (k == 0)
      {
        m = OS.CreateRectRgn(0, 0, 0, 0);
        for (n = OS.GetParent(paramInt1); (n != j) && (k == 0); n = OS.GetParent(n))
          if (OS.GetWindowRgn(n, m) != 0)
            k = 1;
        OS.DeleteObject(m);
      }
      int m = OS.GetWindowLong(paramInt1, -16);
      int n = OS.GetWindowLong(paramInt1, -20);
      int i1 = OS.GetWindow(paramInt1, 3);
      if ((i1 == 0) || (i1 == paramInt1))
        i1 = 0;
      int i2;
      if (k != 0)
      {
        i2 = OS.GetSystemMetrics(76);
        int i3 = OS.GetSystemMetrics(77);
        int i4 = OS.GetSystemMetrics(78);
        int i5 = OS.GetSystemMetrics(79);
        int i6 = 53;
        if ((m & 0x10000000) != 0)
          OS.DefWindowProc(paramInt1, 11, 0, 0);
        SetWindowPos(paramInt1, 0, i2 + i4, i3 + i5, 0, 0, i6);
        if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(6, 0)))
        {
          OS.SetWindowLong(paramInt1, -16, m & 0xBFFFFFFF | 0x80000000);
          OS.SetWindowLong(paramInt1, -20, n | 0x80);
        }
        OS.SetParent(paramInt1, 0);
        if ((m & 0x10000000) != 0)
          OS.DefWindowProc(paramInt1, 11, 1, 0);
      }
      if ((m & 0x10000000) == 0)
        OS.ShowWindow(paramInt1, 5);
      bool = OS.PrintWindow(paramInt1, paramInt2, 0);
      if ((m & 0x10000000) == 0)
        OS.ShowWindow(paramInt1, 0);
      if (k != 0)
      {
        if ((m & 0x10000000) != 0)
          OS.DefWindowProc(paramInt1, 11, 0, 0);
        if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(6, 0)))
        {
          OS.SetWindowLong(paramInt1, -16, m);
          OS.SetWindowLong(paramInt1, -20, n);
        }
        OS.SetParent(paramInt1, i);
        OS.MapWindowPoints(0, i, localRECT1, 2);
        i2 = 49;
        SetWindowPos(paramInt1, i1, localRECT1.left, localRECT1.top, localRECT1.right - localRECT1.left, localRECT1.bottom - localRECT1.top, i2);
        if ((m & 0x10000000) != 0)
          OS.DefWindowProc(paramInt1, 11, 1, 0);
      }
    }
    if (!bool)
    {
      i = 30;
      OS.SendMessage(paramInt1, 791, paramInt2, i);
    }
  }

  public void redraw()
  {
    checkWidget();
    redraw(false);
  }

  void redraw(boolean paramBoolean)
  {
    if (!OS.IsWindowVisible(this.handle))
      return;
    if (OS.IsWinCE)
    {
      OS.InvalidateRect(this.handle, null, true);
    }
    else
    {
      int i = 1029;
      if (paramBoolean)
        i |= 128;
      OS.RedrawWindow(this.handle, null, 0, i);
    }
  }

  public void redraw(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean)
  {
    checkWidget();
    if ((paramInt3 <= 0) || (paramInt4 <= 0))
      return;
    if (!OS.IsWindowVisible(this.handle))
      return;
    RECT localRECT = new RECT();
    OS.SetRect(localRECT, paramInt1, paramInt2, paramInt1 + paramInt3, paramInt2 + paramInt4);
    if (OS.IsWinCE)
    {
      OS.InvalidateRect(this.handle, localRECT, true);
    }
    else
    {
      int i = 1029;
      if (paramBoolean)
        i |= 128;
      OS.RedrawWindow(this.handle, localRECT, 0, i);
    }
  }

  boolean redrawChildren()
  {
    if (!OS.IsWindowVisible(this.handle))
      return false;
    Control localControl = findBackgroundControl();
    if (localControl == null)
    {
      if (((this.state & 0x100) != 0) && (OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()))
      {
        OS.InvalidateRect(this.handle, null, true);
        return true;
      }
    }
    else if (localControl.backgroundImage != null)
    {
      OS.InvalidateRect(this.handle, null, true);
      return true;
    }
    return false;
  }

  void register()
  {
    this.display.addControl(this.handle, this);
  }

  void releaseHandle()
  {
    super.releaseHandle();
    this.handle = 0;
    this.parent = null;
  }

  void releaseParent()
  {
    this.parent.removeControl(this);
  }

  void releaseWidget()
  {
    super.releaseWidget();
    if (OS.IsDBLocale)
      OS.ImmAssociateContext(this.handle, 0);
    if (this.toolTipText != null)
      setToolTipText(getShell(), null);
    this.toolTipText = null;
    if ((this.menu != null) && (!this.menu.isDisposed()))
      this.menu.dispose();
    this.backgroundImage = null;
    this.menu = null;
    this.cursor = null;
    unsubclass();
    deregister();
    this.layoutData = null;
    if (this.accessible != null)
      this.accessible.internal_dispose_Accessible();
    this.accessible = null;
    this.region = null;
    this.font = null;
  }

  public void removeControlListener(ControlListener paramControlListener)
  {
    checkWidget();
    if (paramControlListener == null)
      error(4);
    if (this.eventTable == null)
      return;
    this.eventTable.unhook(10, paramControlListener);
    this.eventTable.unhook(11, paramControlListener);
  }

  public void removeDragDetectListener(DragDetectListener paramDragDetectListener)
  {
    checkWidget();
    if (paramDragDetectListener == null)
      error(4);
    if (this.eventTable == null)
      return;
    this.eventTable.unhook(29, paramDragDetectListener);
  }

  public void removeFocusListener(FocusListener paramFocusListener)
  {
    checkWidget();
    if (paramFocusListener == null)
      error(4);
    if (this.eventTable == null)
      return;
    this.eventTable.unhook(15, paramFocusListener);
    this.eventTable.unhook(16, paramFocusListener);
  }

  public void removeHelpListener(HelpListener paramHelpListener)
  {
    checkWidget();
    if (paramHelpListener == null)
      error(4);
    if (this.eventTable == null)
      return;
    this.eventTable.unhook(28, paramHelpListener);
  }

  public void removeKeyListener(KeyListener paramKeyListener)
  {
    checkWidget();
    if (paramKeyListener == null)
      error(4);
    if (this.eventTable == null)
      return;
    this.eventTable.unhook(2, paramKeyListener);
    this.eventTable.unhook(1, paramKeyListener);
  }

  public void removeMenuDetectListener(MenuDetectListener paramMenuDetectListener)
  {
    checkWidget();
    if (paramMenuDetectListener == null)
      error(4);
    if (this.eventTable == null)
      return;
    this.eventTable.unhook(35, paramMenuDetectListener);
  }

  public void removeMouseTrackListener(MouseTrackListener paramMouseTrackListener)
  {
    checkWidget();
    if (paramMouseTrackListener == null)
      error(4);
    if (this.eventTable == null)
      return;
    this.eventTable.unhook(6, paramMouseTrackListener);
    this.eventTable.unhook(7, paramMouseTrackListener);
    this.eventTable.unhook(32, paramMouseTrackListener);
  }

  public void removeMouseListener(MouseListener paramMouseListener)
  {
    checkWidget();
    if (paramMouseListener == null)
      error(4);
    if (this.eventTable == null)
      return;
    this.eventTable.unhook(3, paramMouseListener);
    this.eventTable.unhook(4, paramMouseListener);
    this.eventTable.unhook(8, paramMouseListener);
  }

  public void removeMouseMoveListener(MouseMoveListener paramMouseMoveListener)
  {
    checkWidget();
    if (paramMouseMoveListener == null)
      error(4);
    if (this.eventTable == null)
      return;
    this.eventTable.unhook(5, paramMouseMoveListener);
  }

  public void removeMouseWheelListener(MouseWheelListener paramMouseWheelListener)
  {
    checkWidget();
    if (paramMouseWheelListener == null)
      error(4);
    if (this.eventTable == null)
      return;
    this.eventTable.unhook(37, paramMouseWheelListener);
  }

  public void removePaintListener(PaintListener paramPaintListener)
  {
    checkWidget();
    if (paramPaintListener == null)
      error(4);
    if (this.eventTable == null)
      return;
    this.eventTable.unhook(9, paramPaintListener);
  }

  public void removeTraverseListener(TraverseListener paramTraverseListener)
  {
    checkWidget();
    if (paramTraverseListener == null)
      error(4);
    if (this.eventTable == null)
      return;
    this.eventTable.unhook(31, paramTraverseListener);
  }

  void showWidget(boolean paramBoolean)
  {
    int i = topHandle();
    OS.ShowWindow(i, paramBoolean ? 5 : 0);
    if (this.handle != i)
      OS.ShowWindow(this.handle, paramBoolean ? 5 : 0);
  }

  boolean sendFocusEvent(int paramInt)
  {
    Shell localShell = getShell();
    Display localDisplay = this.display;
    localDisplay.focusEvent = paramInt;
    localDisplay.focusControl = this;
    sendEvent(paramInt);
    localDisplay.focusEvent = 0;
    localDisplay.focusControl = null;
    if (!localShell.isDisposed())
      switch (paramInt)
      {
      case 15:
        localShell.setActiveControl(this);
        break;
      case 16:
        if (localShell != localDisplay.getActiveShell())
          localShell.setActiveControl(null);
        break;
      }
    return true;
  }

  void sendMove()
  {
    sendEvent(10);
  }

  void sendResize()
  {
    sendEvent(11);
  }

  void setBackground()
  {
    Control localControl = findBackgroundControl();
    if (localControl == null)
      localControl = this;
    if (localControl.backgroundImage != null)
    {
      Shell localShell = getShell();
      localShell.releaseBrushes();
      setBackgroundImage(localControl.backgroundImage.handle);
    }
    else
    {
      setBackgroundPixel(localControl.background == -1 ? localControl.defaultBackground() : localControl.background);
    }
  }

  public void setBackground(Color paramColor)
  {
    checkWidget();
    int i = -1;
    if (paramColor != null)
    {
      if (paramColor.isDisposed())
        SWT.error(5);
      i = paramColor.handle;
    }
    if (i == this.background)
      return;
    this.background = i;
    updateBackgroundColor();
  }

  public void setBackgroundImage(Image paramImage)
  {
    checkWidget();
    if (paramImage != null)
    {
      if (paramImage.isDisposed())
        error(5);
      if (paramImage.type != 0)
        error(5);
    }
    if (this.backgroundImage == paramImage)
      return;
    this.backgroundImage = paramImage;
    Shell localShell = getShell();
    localShell.releaseBrushes();
    updateBackgroundImage();
  }

  void setBackgroundImage(int paramInt)
  {
    if (OS.IsWinCE)
    {
      OS.InvalidateRect(this.handle, null, true);
    }
    else
    {
      int i = 1029;
      OS.RedrawWindow(this.handle, null, 0, i);
    }
  }

  void setBackgroundPixel(int paramInt)
  {
    if (OS.IsWinCE)
    {
      OS.InvalidateRect(this.handle, null, true);
    }
    else
    {
      int i = 1029;
      OS.RedrawWindow(this.handle, null, 0, i);
    }
  }

  public void setBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    checkWidget();
    int i = 52;
    setBounds(paramInt1, paramInt2, Math.max(0, paramInt3), Math.max(0, paramInt4), i);
  }

  void setBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    setBounds(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, true);
  }

  void setBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, boolean paramBoolean)
  {
    if (findImageControl() != null)
    {
      if (this.backgroundImage == null)
        paramInt5 |= 256;
    }
    else if ((OS.GetWindow(this.handle, 5) == 0) && (OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()) && (findThemeControl() != null))
      paramInt5 |= 256;
    int i = topHandle();
    if ((paramBoolean) && (this.parent != null))
    {
      forceResize();
      if (this.parent.lpwp != null)
      {
        int j = 0;
        Object localObject1 = this.parent.lpwp;
        while (j < localObject1.length)
        {
          if (localObject1[j] == null)
            break;
          j++;
        }
        if (j == localObject1.length)
        {
          localObject2 = new WINDOWPOS[localObject1.length + 4];
          System.arraycopy(localObject1, 0, localObject2, 0, localObject1.length);
          this.parent.lpwp = (localObject1 = localObject2);
        }
        Object localObject2 = new WINDOWPOS();
        ((WINDOWPOS)localObject2).hwnd = i;
        ((WINDOWPOS)localObject2).x = paramInt1;
        ((WINDOWPOS)localObject2).y = paramInt2;
        ((WINDOWPOS)localObject2).cx = paramInt3;
        ((WINDOWPOS)localObject2).cy = paramInt4;
        ((WINDOWPOS)localObject2).flags = paramInt5;
        localObject1[j] = localObject2;
        return;
      }
    }
    SetWindowPos(i, 0, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
  }

  public void setBounds(Rectangle paramRectangle)
  {
    checkWidget();
    if (paramRectangle == null)
      error(4);
    setBounds(paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height);
  }

  public void setCapture(boolean paramBoolean)
  {
    checkWidget();
    if (paramBoolean)
      OS.SetCapture(this.handle);
    else if (OS.GetCapture() == this.handle)
      OS.ReleaseCapture();
  }

  void setCursor()
  {
    int i = OS.MAKELPARAM(1, 512);
    OS.SendMessage(this.handle, 32, this.handle, i);
  }

  public void setCursor(Cursor paramCursor)
  {
    checkWidget();
    if ((paramCursor != null) && (paramCursor.isDisposed()))
      SWT.error(5);
    this.cursor = paramCursor;
    if (OS.IsWinCE)
    {
      i = paramCursor != null ? paramCursor.handle : 0;
      OS.SetCursor(i);
      return;
    }
    int i = OS.GetCapture();
    if (i == 0)
    {
      localObject = new POINT();
      if (!OS.GetCursorPos((POINT)localObject))
        return;
      for (int j = i = OS.WindowFromPoint((POINT)localObject); (j != 0) && (j != this.handle); j = OS.GetParent(j));
      if (j == 0)
        return;
    }
    Object localObject = this.display.getControl(i);
    if (localObject == null)
      localObject = this;
    ((Control)localObject).setCursor();
  }

  void setDefaultFont()
  {
    int i = this.display.getSystemFont().handle;
    OS.SendMessage(this.handle, 48, i, 0);
  }

  public void setDragDetect(boolean paramBoolean)
  {
    checkWidget();
    if (paramBoolean)
      this.state |= 32768;
    else
      this.state &= -32769;
    enableDrag(paramBoolean);
  }

  public void setEnabled(boolean paramBoolean)
  {
    checkWidget();
    Control localControl = null;
    boolean bool = false;
    if ((!paramBoolean) && (this.display.focusEvent != 16))
    {
      localControl = this.display.getFocusControl();
      bool = isFocusAncestor(localControl);
    }
    enableWidget(paramBoolean);
    if (bool)
      fixFocus(localControl);
  }

  boolean setFixedFocus()
  {
    if ((this.style & 0x80000) != 0)
      return false;
    return forceFocus();
  }

  public boolean setFocus()
  {
    checkWidget();
    if ((this.style & 0x80000) != 0)
      return false;
    return forceFocus();
  }

  public void setFont(Font paramFont)
  {
    checkWidget();
    int i = 0;
    if (paramFont != null)
    {
      if (paramFont.isDisposed())
        SWT.error(5);
      i = paramFont.handle;
    }
    this.font = paramFont;
    if (i == 0)
      i = defaultFont();
    OS.SendMessage(this.handle, 48, i, 1);
  }

  public void setForeground(Color paramColor)
  {
    checkWidget();
    int i = -1;
    if (paramColor != null)
    {
      if (paramColor.isDisposed())
        SWT.error(5);
      i = paramColor.handle;
    }
    if (i == this.foreground)
      return;
    this.foreground = i;
    setForegroundPixel(i);
  }

  void setForegroundPixel(int paramInt)
  {
    OS.InvalidateRect(this.handle, null, true);
  }

  public void setLayoutData(Object paramObject)
  {
    checkWidget();
    this.layoutData = paramObject;
  }

  public void setLocation(int paramInt1, int paramInt2)
  {
    checkWidget();
    int i = 21;
    if (!OS.IsWinCE)
      i |= 32;
    setBounds(paramInt1, paramInt2, 0, 0, i);
  }

  public void setLocation(Point paramPoint)
  {
    checkWidget();
    if (paramPoint == null)
      error(4);
    setLocation(paramPoint.x, paramPoint.y);
  }

  public void setMenu(Menu paramMenu)
  {
    checkWidget();
    if (paramMenu != null)
    {
      if (paramMenu.isDisposed())
        SWT.error(5);
      if ((paramMenu.style & 0x8) == 0)
        error(37);
      if (paramMenu.parent != menuShell())
        error(32);
    }
    this.menu = paramMenu;
  }

  boolean setRadioFocus(boolean paramBoolean)
  {
    return false;
  }

  boolean setRadioSelection(boolean paramBoolean)
  {
    return false;
  }

  public void setRedraw(boolean paramBoolean)
  {
    checkWidget();
    int i;
    if (this.drawCount == 0)
    {
      i = OS.GetWindowLong(this.handle, -16);
      if ((i & 0x10000000) == 0)
        this.state |= 16;
    }
    if (paramBoolean)
    {
      if (--this.drawCount == 0)
      {
        i = topHandle();
        OS.SendMessage(i, 11, 1, 0);
        if (this.handle != i)
          OS.SendMessage(this.handle, 11, 1, 0);
        if ((this.state & 0x10) != 0)
        {
          this.state &= -17;
          OS.ShowWindow(i, 0);
          if (this.handle != i)
            OS.ShowWindow(this.handle, 0);
        }
        else if (OS.IsWinCE)
        {
          OS.InvalidateRect(i, null, true);
          if (this.handle != i)
            OS.InvalidateRect(this.handle, null, true);
        }
        else
        {
          int j = 1157;
          OS.RedrawWindow(i, null, 0, j);
        }
      }
    }
    else if (this.drawCount++ == 0)
    {
      i = topHandle();
      OS.SendMessage(i, 11, 0, 0);
      if (this.handle != i)
        OS.SendMessage(this.handle, 11, 0, 0);
    }
  }

  public void setRegion(Region paramRegion)
  {
    checkWidget();
    if ((paramRegion != null) && (paramRegion.isDisposed()))
      error(5);
    int i = 0;
    if (paramRegion != null)
    {
      i = OS.CreateRectRgn(0, 0, 0, 0);
      OS.CombineRgn(i, paramRegion.handle, i, 2);
    }
    OS.SetWindowRgn(this.handle, i, true);
    this.region = paramRegion;
  }

  boolean setSavedFocus()
  {
    return forceFocus();
  }

  public void setSize(int paramInt1, int paramInt2)
  {
    checkWidget();
    int i = 54;
    setBounds(0, 0, Math.max(0, paramInt1), Math.max(0, paramInt2), i);
  }

  public void setSize(Point paramPoint)
  {
    checkWidget();
    if (paramPoint == null)
      error(4);
    setSize(paramPoint.x, paramPoint.y);
  }

  boolean setTabItemFocus()
  {
    if (!isShowing())
      return false;
    return forceFocus();
  }

  public void setToolTipText(String paramString)
  {
    checkWidget();
    this.toolTipText = paramString;
    setToolTipText(getShell(), paramString);
  }

  void setToolTipText(Shell paramShell, String paramString)
  {
    paramShell.setToolTipText(this.handle, paramString);
  }

  public void setVisible(boolean paramBoolean)
  {
    checkWidget();
    if (!getDrawing())
    {
      if (((this.state & 0x10) == 0) != paramBoolean);
    }
    else
    {
      int i = OS.GetWindowLong(this.handle, -16);
      if (((i & 0x10000000) != 0) == paramBoolean)
        return;
    }
    if (paramBoolean)
    {
      sendEvent(22);
      if (isDisposed())
        return;
    }
    Control localControl = null;
    boolean bool = false;
    if ((!paramBoolean) && (this.display.focusEvent != 16))
    {
      localControl = this.display.getFocusControl();
      bool = isFocusAncestor(localControl);
    }
    if (!getDrawing())
    {
      this.state = (paramBoolean ? this.state & 0xFFFFFFEF : this.state | 0x10);
    }
    else
    {
      showWidget(paramBoolean);
      if (isDisposed())
        return;
    }
    if (!paramBoolean)
    {
      sendEvent(23);
      if (isDisposed())
        return;
    }
    if (bool)
      fixFocus(localControl);
  }

  void sort(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt.length;
    int j = i / 2;
    while (j > 0)
    {
      for (int k = j; k < i; k++)
      {
        int m = k - j;
        while (m >= 0)
        {
          if (paramArrayOfInt[m] <= paramArrayOfInt[(m + j)])
          {
            int n = paramArrayOfInt[m];
            paramArrayOfInt[m] = paramArrayOfInt[(m + j)];
            paramArrayOfInt[(m + j)] = n;
          }
          m -= j;
        }
      }
      j /= 2;
    }
  }

  void subclass()
  {
    int i = windowProc();
    int j = this.display.windowProc;
    if (i == j)
      return;
    OS.SetWindowLongPtr(this.handle, -4, j);
  }

  public Point toControl(int paramInt1, int paramInt2)
  {
    checkWidget();
    POINT localPOINT = new POINT();
    localPOINT.x = paramInt1;
    localPOINT.y = paramInt2;
    OS.ScreenToClient(this.handle, localPOINT);
    return new Point(localPOINT.x, localPOINT.y);
  }

  public Point toControl(Point paramPoint)
  {
    checkWidget();
    if (paramPoint == null)
      error(4);
    return toControl(paramPoint.x, paramPoint.y);
  }

  public Point toDisplay(int paramInt1, int paramInt2)
  {
    checkWidget();
    POINT localPOINT = new POINT();
    localPOINT.x = paramInt1;
    localPOINT.y = paramInt2;
    OS.ClientToScreen(this.handle, localPOINT);
    return new Point(localPOINT.x, localPOINT.y);
  }

  public Point toDisplay(Point paramPoint)
  {
    checkWidget();
    if (paramPoint == null)
      error(4);
    return toDisplay(paramPoint.x, paramPoint.y);
  }

  int topHandle()
  {
    return this.handle;
  }

  boolean translateAccelerator(MSG paramMSG)
  {
    return menuShell().translateAccelerator(paramMSG);
  }

  boolean translateMnemonic(Event paramEvent, Control paramControl)
  {
    if (paramControl == this)
      return false;
    if ((!isVisible()) || (!isEnabled()))
      return false;
    paramEvent.doit = mnemonicMatch(paramEvent.character);
    return traverse(paramEvent);
  }

  boolean translateMnemonic(MSG paramMSG)
  {
    if (paramMSG.wParam < 32)
      return false;
    int i = paramMSG.hwnd;
    if (OS.GetKeyState(18) >= 0)
    {
      int j = OS.SendMessage(i, 135, 0, 0);
      if ((j & 0x4) != 0)
        return false;
      if ((j & 0x2000) == 0)
        return false;
    }
    Decorations localDecorations = menuShell();
    if ((localDecorations.isVisible()) && (localDecorations.isEnabled()))
    {
      this.display.lastAscii = paramMSG.wParam;
      this.display.lastNull = (this.display.lastDead = 0);
      Event localEvent = new Event();
      localEvent.detail = 128;
      if (setKeyState(localEvent, 31, paramMSG.wParam, paramMSG.lParam))
        return (translateMnemonic(localEvent, null)) || (localDecorations.translateMnemonic(localEvent, this));
    }
    return false;
  }

  boolean translateTraversal(MSG paramMSG)
  {
    int i = paramMSG.hwnd;
    int j = paramMSG.wParam;
    if (j == 18)
    {
      OS.SendMessage(i, 295, 3, 0);
      return false;
    }
    int k = 0;
    boolean bool1 = true;
    int m = 0;
    boolean bool2 = false;
    int n = j;
    int i1 = 0;
    int i2;
    int i3;
    switch (j)
    {
    case 27:
      m = 1;
      i1 = 27;
      i2 = OS.SendMessage(i, 135, 0, 0);
      if (((i2 & 0x4) != 0) && ((i2 & 0x8) == 0))
        bool1 = false;
      k = 2;
      break;
    case 13:
      m = 1;
      i1 = 13;
      i2 = OS.SendMessage(i, 135, 0, 0);
      if ((i2 & 0x4) != 0)
        bool1 = false;
      k = 4;
      break;
    case 9:
      i1 = 9;
      i2 = OS.GetKeyState(16) >= 0 ? 1 : 0;
      i3 = OS.SendMessage(i, 135, 0, 0);
      if ((i3 & 0x6) != 0)
        if ((i3 & 0x8) != 0)
        {
          if ((i2 != 0) && (OS.GetKeyState(17) >= 0))
            bool1 = false;
        }
        else
          bool1 = false;
      k = i2 != 0 ? 16 : 8;
      break;
    case 37:
    case 38:
    case 39:
    case 40:
      if ((OS.IsSP) && ((j == 37) || (j == 39)))
        return false;
      bool2 = true;
      i2 = OS.SendMessage(i, 135, 0, 0);
      if ((i2 & 0x1) != 0)
        bool1 = false;
      i3 = (j != 40) && (j != 39) ? 0 : 1;
      if ((this.parent != null) && ((this.parent.style & 0x8000000) != 0) && ((j == 37) || (j == 39)))
        i3 = i3 != 0 ? 0 : 1;
      k = i3 != 0 ? 64 : 32;
      break;
    case 33:
    case 34:
      m = 1;
      bool2 = true;
      if (OS.GetKeyState(17) >= 0)
        return false;
      i2 = OS.SendMessage(i, 135, 0, 0);
      if (((i2 & 0x4) != 0) && ((i2 & 0x8) == 0))
        bool1 = false;
      k = j == 33 ? 256 : 512;
      break;
    default:
      return false;
    }
    Event localEvent = new Event();
    localEvent.doit = bool1;
    localEvent.detail = k;
    this.display.lastKey = n;
    this.display.lastAscii = i1;
    this.display.lastVirtual = bool2;
    this.display.lastNull = (this.display.lastDead = 0);
    if (!setKeyState(localEvent, 31, paramMSG.wParam, paramMSG.lParam))
      return false;
    Shell localShell = getShell();
    Object localObject = this;
    do
    {
      if (((Control)localObject).traverse(localEvent))
      {
        OS.SendMessage(i, 295, 3, 0);
        return true;
      }
      if ((!localEvent.doit) && (((Control)localObject).hooks(31)))
        return false;
      if (localObject == localShell)
        return false;
      localObject = ((Control)localObject).parent;
    }
    while ((m != 0) && (localObject != null));
    return false;
  }

  boolean traverse(Event paramEvent)
  {
    sendEvent(31, paramEvent);
    if (isDisposed())
      return true;
    if (!paramEvent.doit)
      return false;
    switch (paramEvent.detail)
    {
    case 0:
      return true;
    case 2:
      return traverseEscape();
    case 4:
      return traverseReturn();
    case 16:
      return traverseGroup(true);
    case 8:
      return traverseGroup(false);
    case 64:
      return traverseItem(true);
    case 32:
      return traverseItem(false);
    case 128:
      return traverseMnemonic(paramEvent.character);
    case 512:
      return traversePage(true);
    case 256:
      return traversePage(false);
    }
    return false;
  }

  public boolean traverse(int paramInt)
  {
    checkWidget();
    Event localEvent = new Event();
    localEvent.doit = true;
    localEvent.detail = paramInt;
    return traverse(localEvent);
  }

  public boolean traverse(int paramInt, Event paramEvent)
  {
    checkWidget();
    if (paramEvent == null)
      error(4);
    return traverse(paramInt, paramEvent.character, paramEvent.keyCode, paramEvent.keyLocation, paramEvent.stateMask, paramEvent.doit);
  }

  public boolean traverse(int paramInt, KeyEvent paramKeyEvent)
  {
    checkWidget();
    if (paramKeyEvent == null)
      error(4);
    return traverse(paramInt, paramKeyEvent.character, paramKeyEvent.keyCode, paramKeyEvent.keyLocation, paramKeyEvent.stateMask, paramKeyEvent.doit);
  }

  boolean traverse(int paramInt1, char paramChar, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean)
  {
    if (paramInt1 == 0)
      switch (paramInt2)
      {
      case 27:
        paramInt1 = 2;
        paramBoolean = true;
        break;
      case 13:
        paramInt1 = 4;
        paramBoolean = true;
        break;
      case 16777218:
      case 16777220:
        paramInt1 = 64;
        paramBoolean = false;
        break;
      case 16777217:
      case 16777219:
        paramInt1 = 32;
        paramBoolean = false;
        break;
      case 9:
        paramInt1 = (paramInt4 & 0x20000) != 0 ? 8 : 16;
        paramBoolean = true;
        break;
      case 16777222:
        if ((paramInt4 & 0x40000) != 0)
        {
          paramInt1 = 512;
          paramBoolean = true;
        }
        break;
      case 16777221:
        if ((paramInt4 & 0x40000) != 0)
        {
          paramInt1 = 256;
          paramBoolean = true;
        }
        break;
      default:
        if ((paramChar != 0) && ((paramInt4 & 0x50000) == 65536))
        {
          paramInt1 = 128;
          paramBoolean = true;
        }
        break;
      }
    Event localEvent = new Event();
    localEvent.character = paramChar;
    localEvent.detail = paramInt1;
    localEvent.doit = paramBoolean;
    localEvent.keyCode = paramInt2;
    localEvent.keyLocation = paramInt3;
    localEvent.stateMask = paramInt4;
    Shell localShell = getShell();
    int i = 0;
    switch (paramInt1)
    {
    case 2:
    case 4:
    case 256:
    case 512:
      i = 1;
    case 8:
    case 16:
    case 32:
    case 64:
      break;
    case 128:
      return (translateMnemonic(localEvent, null)) || (localShell.translateMnemonic(localEvent, this));
    default:
      return false;
    }
    Object localObject = this;
    do
    {
      if (((Control)localObject).traverse(localEvent))
      {
        OS.SendMessage(this.handle, 295, 3, 0);
        return true;
      }
      if ((!localEvent.doit) && (((Control)localObject).hooks(31)))
        return false;
      if (localObject == localShell)
        return false;
      localObject = ((Control)localObject).parent;
    }
    while ((i != 0) && (localObject != null));
    return false;
  }

  boolean traverseEscape()
  {
    return false;
  }

  boolean traverseGroup(boolean paramBoolean)
  {
    Control localControl = computeTabRoot();
    Widget localWidget1 = computeTabGroup();
    Widget[] arrayOfWidget = localControl.computeTabList();
    int i = arrayOfWidget.length;
    for (int j = 0; j < i; j++)
      if (arrayOfWidget[j] == localWidget1)
        break;
    if (j == i)
      return false;
    int k = j;
    int m = paramBoolean ? 1 : -1;
    while ((j = (j + m + i) % i) != k)
    {
      Widget localWidget2 = arrayOfWidget[j];
      if ((!localWidget2.isDisposed()) && (localWidget2.setTabGroupFocus()))
        return true;
    }
    if (localWidget1.isDisposed())
      return false;
    return localWidget1.setTabGroupFocus();
  }

  boolean traverseItem(boolean paramBoolean)
  {
    Control[] arrayOfControl = this.parent._getChildren();
    int i = arrayOfControl.length;
    for (int j = 0; j < i; j++)
      if (arrayOfControl[j] == this)
        break;
    if (j == i)
      return false;
    int k = j;
    int m = paramBoolean ? 1 : -1;
    while ((j = (j + m + i) % i) != k)
    {
      Control localControl = arrayOfControl[j];
      if ((!localControl.isDisposed()) && (localControl.isTabItem()) && (localControl.setTabItemFocus()))
        return true;
    }
    return false;
  }

  boolean traverseMnemonic(char paramChar)
  {
    if (mnemonicHit(paramChar))
    {
      OS.SendMessage(this.handle, 295, 3, 0);
      return true;
    }
    return false;
  }

  boolean traversePage(boolean paramBoolean)
  {
    return false;
  }

  boolean traverseReturn()
  {
    return false;
  }

  void unsubclass()
  {
    int i = windowProc();
    int j = this.display.windowProc;
    if (j == i)
      return;
    OS.SetWindowLongPtr(this.handle, -4, i);
  }

  public void update()
  {
    checkWidget();
    update(false);
  }

  void update(boolean paramBoolean)
  {
    if (OS.IsWinCE)
    {
      OS.UpdateWindow(this.handle);
    }
    else
    {
      int i = 256;
      if (paramBoolean)
        i |= 128;
      OS.RedrawWindow(this.handle, null, 0, i);
    }
  }

  void updateBackgroundColor()
  {
    Control localControl = findBackgroundControl();
    if (localControl == null)
      localControl = this;
    setBackgroundPixel(localControl.background);
  }

  void updateBackgroundImage()
  {
    Control localControl = findBackgroundControl();
    Image localImage = localControl != null ? localControl.backgroundImage : this.backgroundImage;
    setBackgroundImage(localImage != null ? localImage.handle : 0);
  }

  void updateBackgroundMode()
  {
    int i = this.state & 0x400;
    checkBackground();
    if (i != (this.state & 0x400))
      setBackground();
  }

  void updateFont(Font paramFont1, Font paramFont2)
  {
    if (getFont().equals(paramFont1))
      setFont(paramFont2);
  }

  void updateImages()
  {
  }

  void updateLayout(boolean paramBoolean1, boolean paramBoolean2)
  {
  }

  CREATESTRUCT widgetCreateStruct()
  {
    return null;
  }

  int widgetExtStyle()
  {
    int i = 0;
    if ((!OS.IsPPC) && ((this.style & 0x800) != 0))
      i |= 512;
    if (OS.WIN32_VERSION < OS.VERSION(4, 10))
      return i;
    i |= 1048576;
    if ((this.style & 0x4000000) != 0)
      i |= 4194304;
    return i;
  }

  int widgetParent()
  {
    return this.parent.handle;
  }

  int widgetStyle()
  {
    int i = 1409286144;
    if ((OS.IsPPC) && ((this.style & 0x800) != 0))
      i |= 8388608;
    return i;
  }

  public boolean setParent(Composite paramComposite)
  {
    checkWidget();
    if (paramComposite == null)
      error(4);
    if (paramComposite.isDisposed())
      SWT.error(5);
    if (this.parent == paramComposite)
      return true;
    if (!isReparentable())
      return false;
    releaseParent();
    Shell localShell1 = paramComposite.getShell();
    Shell localShell2 = getShell();
    Decorations localDecorations1 = paramComposite.menuShell();
    Decorations localDecorations2 = menuShell();
    if ((localShell2 != localShell1) || (localDecorations2 != localDecorations1))
    {
      Menu[] arrayOfMenu = localShell2.findMenus(this);
      fixChildren(localShell1, localShell2, localDecorations1, localDecorations2, arrayOfMenu);
    }
    int i = topHandle();
    if (OS.SetParent(i, paramComposite.handle) == 0)
      return false;
    this.parent = paramComposite;
    int j = 19;
    SetWindowPos(i, 1, 0, 0, 0, 0, j);
    reskin(1);
    return true;
  }

  abstract TCHAR windowClass();

  abstract int windowProc();

  int windowProc(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    LRESULT localLRESULT = null;
    switch (paramInt2)
    {
    case 6:
      localLRESULT = WM_ACTIVATE(paramInt3, paramInt4);
      break;
    case 533:
      localLRESULT = WM_CAPTURECHANGED(paramInt3, paramInt4);
      break;
    case 295:
      localLRESULT = WM_CHANGEUISTATE(paramInt3, paramInt4);
      break;
    case 258:
      localLRESULT = WM_CHAR(paramInt3, paramInt4);
      break;
    case 771:
      localLRESULT = WM_CLEAR(paramInt3, paramInt4);
      break;
    case 16:
      localLRESULT = WM_CLOSE(paramInt3, paramInt4);
      break;
    case 273:
      localLRESULT = WM_COMMAND(paramInt3, paramInt4);
      break;
    case 123:
      localLRESULT = WM_CONTEXTMENU(paramInt3, paramInt4);
      break;
    case 306:
    case 307:
    case 308:
    case 309:
    case 310:
    case 311:
    case 312:
      localLRESULT = WM_CTLCOLOR(paramInt3, paramInt4);
      break;
    case 768:
      localLRESULT = WM_CUT(paramInt3, paramInt4);
      break;
    case 2:
      localLRESULT = WM_DESTROY(paramInt3, paramInt4);
      break;
    case 43:
      localLRESULT = WM_DRAWITEM(paramInt3, paramInt4);
      break;
    case 22:
      localLRESULT = WM_ENDSESSION(paramInt3, paramInt4);
      break;
    case 289:
      localLRESULT = WM_ENTERIDLE(paramInt3, paramInt4);
      break;
    case 20:
      localLRESULT = WM_ERASEBKGND(paramInt3, paramInt4);
      break;
    case 135:
      localLRESULT = WM_GETDLGCODE(paramInt3, paramInt4);
      break;
    case 49:
      localLRESULT = WM_GETFONT(paramInt3, paramInt4);
      break;
    case 61:
      localLRESULT = WM_GETOBJECT(paramInt3, paramInt4);
      break;
    case 36:
      localLRESULT = WM_GETMINMAXINFO(paramInt3, paramInt4);
      break;
    case 83:
      localLRESULT = WM_HELP(paramInt3, paramInt4);
      break;
    case 276:
      localLRESULT = WM_HSCROLL(paramInt3, paramInt4);
      break;
    case 646:
      localLRESULT = WM_IME_CHAR(paramInt3, paramInt4);
      break;
    case 271:
      localLRESULT = WM_IME_COMPOSITION(paramInt3, paramInt4);
      break;
    case 269:
      localLRESULT = WM_IME_COMPOSITION_START(paramInt3, paramInt4);
      break;
    case 270:
      localLRESULT = WM_IME_ENDCOMPOSITION(paramInt3, paramInt4);
      break;
    case 279:
      localLRESULT = WM_INITMENUPOPUP(paramInt3, paramInt4);
      break;
    case 81:
      localLRESULT = WM_INPUTLANGCHANGE(paramInt3, paramInt4);
      break;
    case 786:
      localLRESULT = WM_HOTKEY(paramInt3, paramInt4);
      break;
    case 256:
      localLRESULT = WM_KEYDOWN(paramInt3, paramInt4);
      break;
    case 257:
      localLRESULT = WM_KEYUP(paramInt3, paramInt4);
      break;
    case 8:
      localLRESULT = WM_KILLFOCUS(paramInt3, paramInt4);
      break;
    case 515:
      localLRESULT = WM_LBUTTONDBLCLK(paramInt3, paramInt4);
      break;
    case 513:
      localLRESULT = WM_LBUTTONDOWN(paramInt3, paramInt4);
      break;
    case 514:
      localLRESULT = WM_LBUTTONUP(paramInt3, paramInt4);
      break;
    case 521:
      localLRESULT = WM_MBUTTONDBLCLK(paramInt3, paramInt4);
      break;
    case 519:
      localLRESULT = WM_MBUTTONDOWN(paramInt3, paramInt4);
      break;
    case 520:
      localLRESULT = WM_MBUTTONUP(paramInt3, paramInt4);
      break;
    case 44:
      localLRESULT = WM_MEASUREITEM(paramInt3, paramInt4);
      break;
    case 288:
      localLRESULT = WM_MENUCHAR(paramInt3, paramInt4);
      break;
    case 287:
      localLRESULT = WM_MENUSELECT(paramInt3, paramInt4);
      break;
    case 33:
      localLRESULT = WM_MOUSEACTIVATE(paramInt3, paramInt4);
      break;
    case 673:
      localLRESULT = WM_MOUSEHOVER(paramInt3, paramInt4);
      break;
    case 675:
      localLRESULT = WM_MOUSELEAVE(paramInt3, paramInt4);
      break;
    case 512:
      localLRESULT = WM_MOUSEMOVE(paramInt3, paramInt4);
      break;
    case 522:
      localLRESULT = WM_MOUSEWHEEL(paramInt3, paramInt4);
      break;
    case 526:
      localLRESULT = WM_MOUSEHWHEEL(paramInt3, paramInt4);
      break;
    case 3:
      localLRESULT = WM_MOVE(paramInt3, paramInt4);
      break;
    case 134:
      localLRESULT = WM_NCACTIVATE(paramInt3, paramInt4);
      break;
    case 131:
      localLRESULT = WM_NCCALCSIZE(paramInt3, paramInt4);
      break;
    case 132:
      localLRESULT = WM_NCHITTEST(paramInt3, paramInt4);
      break;
    case 161:
      localLRESULT = WM_NCLBUTTONDOWN(paramInt3, paramInt4);
      break;
    case 133:
      localLRESULT = WM_NCPAINT(paramInt3, paramInt4);
      break;
    case 78:
      localLRESULT = WM_NOTIFY(paramInt3, paramInt4);
      break;
    case 15:
      localLRESULT = WM_PAINT(paramInt3, paramInt4);
      break;
    case 785:
      localLRESULT = WM_PALETTECHANGED(paramInt3, paramInt4);
      break;
    case 528:
      localLRESULT = WM_PARENTNOTIFY(paramInt3, paramInt4);
      break;
    case 770:
      localLRESULT = WM_PASTE(paramInt3, paramInt4);
      break;
    case 791:
      localLRESULT = WM_PRINT(paramInt3, paramInt4);
      break;
    case 792:
      localLRESULT = WM_PRINTCLIENT(paramInt3, paramInt4);
      break;
    case 17:
      localLRESULT = WM_QUERYENDSESSION(paramInt3, paramInt4);
      break;
    case 783:
      localLRESULT = WM_QUERYNEWPALETTE(paramInt3, paramInt4);
      break;
    case 19:
      localLRESULT = WM_QUERYOPEN(paramInt3, paramInt4);
      break;
    case 518:
      localLRESULT = WM_RBUTTONDBLCLK(paramInt3, paramInt4);
      break;
    case 516:
      localLRESULT = WM_RBUTTONDOWN(paramInt3, paramInt4);
      break;
    case 517:
      localLRESULT = WM_RBUTTONUP(paramInt3, paramInt4);
      break;
    case 32:
      localLRESULT = WM_SETCURSOR(paramInt3, paramInt4);
      break;
    case 7:
      localLRESULT = WM_SETFOCUS(paramInt3, paramInt4);
      break;
    case 48:
      localLRESULT = WM_SETFONT(paramInt3, paramInt4);
      break;
    case 26:
      localLRESULT = WM_SETTINGCHANGE(paramInt3, paramInt4);
      break;
    case 11:
      localLRESULT = WM_SETREDRAW(paramInt3, paramInt4);
      break;
    case 24:
      localLRESULT = WM_SHOWWINDOW(paramInt3, paramInt4);
      break;
    case 5:
      localLRESULT = WM_SIZE(paramInt3, paramInt4);
      break;
    case 262:
      localLRESULT = WM_SYSCHAR(paramInt3, paramInt4);
      break;
    case 21:
      localLRESULT = WM_SYSCOLORCHANGE(paramInt3, paramInt4);
      break;
    case 274:
      localLRESULT = WM_SYSCOMMAND(paramInt3, paramInt4);
      break;
    case 260:
      localLRESULT = WM_SYSKEYDOWN(paramInt3, paramInt4);
      break;
    case 261:
      localLRESULT = WM_SYSKEYUP(paramInt3, paramInt4);
      break;
    case 275:
      localLRESULT = WM_TIMER(paramInt3, paramInt4);
      break;
    case 772:
      localLRESULT = WM_UNDO(paramInt3, paramInt4);
      break;
    case 296:
      localLRESULT = WM_UPDATEUISTATE(paramInt3, paramInt4);
      break;
    case 277:
      localLRESULT = WM_VSCROLL(paramInt3, paramInt4);
      break;
    case 71:
      localLRESULT = WM_WINDOWPOSCHANGED(paramInt3, paramInt4);
      break;
    case 70:
      localLRESULT = WM_WINDOWPOSCHANGING(paramInt3, paramInt4);
      break;
    case 525:
      localLRESULT = WM_XBUTTONDBLCLK(paramInt3, paramInt4);
      break;
    case 523:
      localLRESULT = WM_XBUTTONDOWN(paramInt3, paramInt4);
      break;
    case 524:
      localLRESULT = WM_XBUTTONUP(paramInt3, paramInt4);
    }
    if (localLRESULT != null)
      return localLRESULT.value;
    return callWindowProc(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  LRESULT WM_ACTIVATE(int paramInt1, int paramInt2)
  {
    return null;
  }

  LRESULT WM_CAPTURECHANGED(int paramInt1, int paramInt2)
  {
    return wmCaptureChanged(this.handle, paramInt1, paramInt2);
  }

  LRESULT WM_CHANGEUISTATE(int paramInt1, int paramInt2)
  {
    if ((this.state & 0x100000) != 0)
      return LRESULT.ZERO;
    return null;
  }

  LRESULT WM_CHAR(int paramInt1, int paramInt2)
  {
    return wmChar(this.handle, paramInt1, paramInt2);
  }

  LRESULT WM_CLEAR(int paramInt1, int paramInt2)
  {
    return null;
  }

  LRESULT WM_CLOSE(int paramInt1, int paramInt2)
  {
    return null;
  }

  LRESULT WM_COMMAND(int paramInt1, int paramInt2)
  {
    if (paramInt2 == 0)
    {
      localObject = menuShell();
      if (((Decorations)localObject).isEnabled())
      {
        int i = OS.LOWORD(paramInt1);
        MenuItem localMenuItem = this.display.getMenuItem(i);
        if ((localMenuItem != null) && (localMenuItem.isEnabled()))
          return localMenuItem.wmCommandChild(paramInt1, paramInt2);
      }
      return null;
    }
    Object localObject = this.display.getControl(paramInt2);
    if (localObject == null)
      return null;
    return ((Control)localObject).wmCommandChild(paramInt1, paramInt2);
  }

  LRESULT WM_CONTEXTMENU(int paramInt1, int paramInt2)
  {
    return wmContextMenu(this.handle, paramInt1, paramInt2);
  }

  LRESULT WM_CTLCOLOR(int paramInt1, int paramInt2)
  {
    int i = this.display.hPalette;
    if (i != 0)
    {
      OS.SelectPalette(paramInt1, i, false);
      OS.RealizePalette(paramInt1);
    }
    Control localControl = this.display.getControl(paramInt2);
    if (localControl == null)
      return null;
    return localControl.wmColorChild(paramInt1, paramInt2);
  }

  LRESULT WM_CUT(int paramInt1, int paramInt2)
  {
    return null;
  }

  LRESULT WM_DESTROY(int paramInt1, int paramInt2)
  {
    return null;
  }

  LRESULT WM_DRAWITEM(int paramInt1, int paramInt2)
  {
    DRAWITEMSTRUCT localDRAWITEMSTRUCT = new DRAWITEMSTRUCT();
    OS.MoveMemory(localDRAWITEMSTRUCT, paramInt2, DRAWITEMSTRUCT.sizeof);
    if (localDRAWITEMSTRUCT.CtlType == 1)
    {
      localObject = this.display.getMenuItem(localDRAWITEMSTRUCT.itemID);
      if (localObject == null)
        return null;
      return ((MenuItem)localObject).wmDrawChild(paramInt1, paramInt2);
    }
    Object localObject = this.display.getControl(localDRAWITEMSTRUCT.hwndItem);
    if (localObject == null)
      return null;
    return ((Control)localObject).wmDrawChild(paramInt1, paramInt2);
  }

  LRESULT WM_ENDSESSION(int paramInt1, int paramInt2)
  {
    return null;
  }

  LRESULT WM_ENTERIDLE(int paramInt1, int paramInt2)
  {
    return null;
  }

  LRESULT WM_ERASEBKGND(int paramInt1, int paramInt2)
  {
    if (((this.state & 0x200) != 0) && (findImageControl() != null))
      return LRESULT.ONE;
    if (((this.state & 0x100) != 0) && (OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()) && (findThemeControl() != null))
      return LRESULT.ONE;
    return null;
  }

  LRESULT WM_GETDLGCODE(int paramInt1, int paramInt2)
  {
    return null;
  }

  LRESULT WM_GETFONT(int paramInt1, int paramInt2)
  {
    return null;
  }

  LRESULT WM_GETOBJECT(int paramInt1, int paramInt2)
  {
    if (this.accessible != null)
    {
      int i = this.accessible.internal_WM_GETOBJECT(paramInt1, paramInt2);
      if (i != 0)
        return new LRESULT(i);
    }
    return null;
  }

  LRESULT WM_GETMINMAXINFO(int paramInt1, int paramInt2)
  {
    return null;
  }

  LRESULT WM_HOTKEY(int paramInt1, int paramInt2)
  {
    return null;
  }

  LRESULT WM_HELP(int paramInt1, int paramInt2)
  {
    if (OS.IsWinCE)
      return null;
    HELPINFO localHELPINFO = new HELPINFO();
    OS.MoveMemory(localHELPINFO, paramInt2, HELPINFO.sizeof);
    Decorations localDecorations = menuShell();
    if (!localDecorations.isEnabled())
      return null;
    if (localHELPINFO.iContextType == 2)
    {
      MenuItem localMenuItem = this.display.getMenuItem(localHELPINFO.iCtrlId);
      if ((localMenuItem != null) && (localMenuItem.isEnabled()))
      {
        Object localObject = null;
        if (localMenuItem.hooks(28))
        {
          localObject = localMenuItem;
        }
        else
        {
          Menu localMenu = localMenuItem.parent;
          if (localMenu.hooks(28))
            localObject = localMenu;
        }
        if (localObject != null)
        {
          int i = localDecorations.handle;
          OS.SendMessage(i, 31, 0, 0);
          ((Widget)localObject).postEvent(28);
          return LRESULT.ONE;
        }
      }
      return null;
    }
    if (hooks(28))
    {
      postEvent(28);
      return LRESULT.ONE;
    }
    return null;
  }

  LRESULT WM_HSCROLL(int paramInt1, int paramInt2)
  {
    Control localControl = this.display.getControl(paramInt2);
    if (localControl == null)
      return null;
    return localControl.wmScrollChild(paramInt1, paramInt2);
  }

  LRESULT WM_IME_CHAR(int paramInt1, int paramInt2)
  {
    return wmIMEChar(this.handle, paramInt1, paramInt2);
  }

  LRESULT WM_IME_COMPOSITION(int paramInt1, int paramInt2)
  {
    return null;
  }

  LRESULT WM_IME_COMPOSITION_START(int paramInt1, int paramInt2)
  {
    return null;
  }

  LRESULT WM_IME_ENDCOMPOSITION(int paramInt1, int paramInt2)
  {
    return null;
  }

  LRESULT WM_INITMENUPOPUP(int paramInt1, int paramInt2)
  {
    if (this.display.accelKeyHit)
      return null;
    Shell localShell = getShell();
    Menu localMenu1 = localShell.activeMenu;
    Menu localMenu2 = null;
    if (OS.HIWORD(paramInt2) == 0)
    {
      localMenu2 = menuShell().findMenu(paramInt1);
      if (localMenu2 != null)
        localMenu2.update();
    }
    for (Menu localMenu3 = localMenu2; (localMenu3 != null) && (localMenu3 != localMenu1); localMenu3 = localMenu3.getParentMenu());
    if (localMenu3 == null)
    {
      localMenu3 = localShell.activeMenu;
      while (localMenu3 != null)
      {
        localMenu3.sendEvent(23);
        if (localMenu3.isDisposed())
          break;
        localMenu3 = localMenu3.getParentMenu();
        for (Menu localMenu4 = localMenu2; (localMenu4 != null) && (localMenu4 != localMenu3); localMenu4 = localMenu4.getParentMenu());
        if (localMenu4 != null)
          break;
      }
    }
    if ((localMenu2 != null) && (localMenu2.isDisposed()))
      localMenu2 = null;
    localShell.activeMenu = localMenu2;
    if ((localMenu2 != null) && (localMenu2 != localMenu1))
      localMenu2.sendEvent(22);
    return null;
  }

  LRESULT WM_INPUTLANGCHANGE(int paramInt1, int paramInt2)
  {
    return null;
  }

  LRESULT WM_KEYDOWN(int paramInt1, int paramInt2)
  {
    return wmKeyDown(this.handle, paramInt1, paramInt2);
  }

  LRESULT WM_KEYUP(int paramInt1, int paramInt2)
  {
    return wmKeyUp(this.handle, paramInt1, paramInt2);
  }

  LRESULT WM_KILLFOCUS(int paramInt1, int paramInt2)
  {
    return wmKillFocus(this.handle, paramInt1, paramInt2);
  }

  LRESULT WM_LBUTTONDBLCLK(int paramInt1, int paramInt2)
  {
    return wmLButtonDblClk(this.handle, paramInt1, paramInt2);
  }

  LRESULT WM_LBUTTONDOWN(int paramInt1, int paramInt2)
  {
    return wmLButtonDown(this.handle, paramInt1, paramInt2);
  }

  LRESULT WM_LBUTTONUP(int paramInt1, int paramInt2)
  {
    return wmLButtonUp(this.handle, paramInt1, paramInt2);
  }

  LRESULT WM_MBUTTONDBLCLK(int paramInt1, int paramInt2)
  {
    return wmMButtonDblClk(this.handle, paramInt1, paramInt2);
  }

  LRESULT WM_MBUTTONDOWN(int paramInt1, int paramInt2)
  {
    return wmMButtonDown(this.handle, paramInt1, paramInt2);
  }

  LRESULT WM_MBUTTONUP(int paramInt1, int paramInt2)
  {
    return wmMButtonUp(this.handle, paramInt1, paramInt2);
  }

  LRESULT WM_MEASUREITEM(int paramInt1, int paramInt2)
  {
    MEASUREITEMSTRUCT localMEASUREITEMSTRUCT = new MEASUREITEMSTRUCT();
    OS.MoveMemory(localMEASUREITEMSTRUCT, paramInt2, MEASUREITEMSTRUCT.sizeof);
    if (localMEASUREITEMSTRUCT.CtlType == 1)
    {
      MenuItem localMenuItem = this.display.getMenuItem(localMEASUREITEMSTRUCT.itemID);
      if (localMenuItem == null)
        return null;
      return localMenuItem.wmMeasureChild(paramInt1, paramInt2);
    }
    int i = OS.GetDlgItem(this.handle, localMEASUREITEMSTRUCT.CtlID);
    Control localControl = this.display.getControl(i);
    if (localControl == null)
      return null;
    return localControl.wmMeasureChild(paramInt1, paramInt2);
  }

  LRESULT WM_MENUCHAR(int paramInt1, int paramInt2)
  {
    int i = OS.HIWORD(paramInt1);
    if ((i == 0) || (i == 8192))
    {
      this.display.mnemonicKeyHit = false;
      return new LRESULT(OS.MAKELRESULT(0, 1));
    }
    return null;
  }

  LRESULT WM_MENUSELECT(int paramInt1, int paramInt2)
  {
    int i = OS.HIWORD(paramInt1);
    Shell localShell = getShell();
    Object localObject;
    if ((i == 65535) && (paramInt2 == 0))
    {
      for (localObject = localShell.activeMenu; localObject != null; localObject = ((Menu)localObject).getParentMenu())
      {
        this.display.mnemonicKeyHit = true;
        ((Menu)localObject).sendEvent(23);
        if (((Menu)localObject).isDisposed())
          break;
      }
      localShell.activeMenu = null;
      return null;
    }
    if ((i & 0x2000) != 0)
      return null;
    if ((i & 0x80) != 0)
    {
      localObject = null;
      Decorations localDecorations = menuShell();
      Menu localMenu3;
      if ((i & 0x10) != 0)
      {
        int j = OS.LOWORD(paramInt1);
        MENUITEMINFO localMENUITEMINFO = new MENUITEMINFO();
        localMENUITEMINFO.cbSize = MENUITEMINFO.sizeof;
        localMENUITEMINFO.fMask = 4;
        if (OS.GetMenuItemInfo(paramInt2, j, true, localMENUITEMINFO))
        {
          localMenu3 = localDecorations.findMenu(localMENUITEMINFO.hSubMenu);
          if (localMenu3 != null)
            localObject = localMenu3.cascade;
        }
      }
      else
      {
        Menu localMenu1 = localDecorations.findMenu(paramInt2);
        if (localMenu1 != null)
        {
          int k = OS.LOWORD(paramInt1);
          localObject = this.display.getMenuItem(k);
        }
        Menu localMenu2 = localShell.activeMenu;
        if (localMenu2 != null)
        {
          for (localMenu3 = localMenu2; (localMenu3 != null) && (localMenu3 != localMenu1); localMenu3 = localMenu3.getParentMenu());
          if (localMenu3 == localMenu1)
          {
            localMenu3 = localMenu2;
            while (localMenu3 != localMenu1)
            {
              localMenu3.sendEvent(23);
              if (localMenu3.isDisposed())
                break;
              localMenu3 = localMenu3.getParentMenu();
              if (localMenu3 == null)
                break;
            }
            if (!localShell.isDisposed())
            {
              if ((localMenu1 != null) && (localMenu1.isDisposed()))
                localMenu1 = null;
              localShell.activeMenu = localMenu1;
            }
            if ((localObject != null) && (((MenuItem)localObject).isDisposed()))
              localObject = null;
          }
        }
      }
      if (localObject != null)
        ((MenuItem)localObject).sendEvent(30);
    }
    return null;
  }

  LRESULT WM_MOUSEACTIVATE(int paramInt1, int paramInt2)
  {
    return null;
  }

  LRESULT WM_MOUSEHOVER(int paramInt1, int paramInt2)
  {
    return wmMouseHover(this.handle, paramInt1, paramInt2);
  }

  LRESULT WM_MOUSELEAVE(int paramInt1, int paramInt2)
  {
    if (OS.COMCTL32_MAJOR >= 6)
      getShell().fixToolTip();
    return wmMouseLeave(this.handle, paramInt1, paramInt2);
  }

  LRESULT WM_MOUSEMOVE(int paramInt1, int paramInt2)
  {
    return wmMouseMove(this.handle, paramInt1, paramInt2);
  }

  LRESULT WM_MOUSEWHEEL(int paramInt1, int paramInt2)
  {
    return wmMouseWheel(this.handle, paramInt1, paramInt2);
  }

  LRESULT WM_MOUSEHWHEEL(int paramInt1, int paramInt2)
  {
    return wmMouseHWheel(this.handle, paramInt1, paramInt2);
  }

  LRESULT WM_MOVE(int paramInt1, int paramInt2)
  {
    this.state |= 65536;
    if (findImageControl() != null)
    {
      if (this != getShell())
        redrawChildren();
    }
    else if (((this.state & 0x100) != 0) && (OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()) && (OS.IsWindowVisible(this.handle)) && (findThemeControl() != null))
      redrawChildren();
    if ((this.state & 0x20000) == 0)
      sendEvent(10);
    return null;
  }

  LRESULT WM_NCACTIVATE(int paramInt1, int paramInt2)
  {
    return null;
  }

  LRESULT WM_NCCALCSIZE(int paramInt1, int paramInt2)
  {
    return null;
  }

  LRESULT WM_NCHITTEST(int paramInt1, int paramInt2)
  {
    if (!OS.IsWindowEnabled(this.handle))
      return null;
    if (!isActive())
      return new LRESULT(-1);
    return null;
  }

  LRESULT WM_NCLBUTTONDOWN(int paramInt1, int paramInt2)
  {
    return null;
  }

  LRESULT WM_NCPAINT(int paramInt1, int paramInt2)
  {
    return wmNCPaint(this.handle, paramInt1, paramInt2);
  }

  LRESULT WM_NOTIFY(int paramInt1, int paramInt2)
  {
    NMHDR localNMHDR = new NMHDR();
    OS.MoveMemory(localNMHDR, paramInt2, NMHDR.sizeof);
    return wmNotify(localNMHDR, paramInt1, paramInt2);
  }

  LRESULT WM_PAINT(int paramInt1, int paramInt2)
  {
    return wmPaint(this.handle, paramInt1, paramInt2);
  }

  LRESULT WM_PALETTECHANGED(int paramInt1, int paramInt2)
  {
    return null;
  }

  LRESULT WM_PARENTNOTIFY(int paramInt1, int paramInt2)
  {
    return null;
  }

  LRESULT WM_PASTE(int paramInt1, int paramInt2)
  {
    return null;
  }

  LRESULT WM_PRINT(int paramInt1, int paramInt2)
  {
    return wmPrint(this.handle, paramInt1, paramInt2);
  }

  LRESULT WM_PRINTCLIENT(int paramInt1, int paramInt2)
  {
    return null;
  }

  LRESULT WM_QUERYENDSESSION(int paramInt1, int paramInt2)
  {
    return null;
  }

  LRESULT WM_QUERYNEWPALETTE(int paramInt1, int paramInt2)
  {
    return null;
  }

  LRESULT WM_QUERYOPEN(int paramInt1, int paramInt2)
  {
    return null;
  }

  LRESULT WM_RBUTTONDBLCLK(int paramInt1, int paramInt2)
  {
    return wmRButtonDblClk(this.handle, paramInt1, paramInt2);
  }

  LRESULT WM_RBUTTONDOWN(int paramInt1, int paramInt2)
  {
    return wmRButtonDown(this.handle, paramInt1, paramInt2);
  }

  LRESULT WM_RBUTTONUP(int paramInt1, int paramInt2)
  {
    return wmRButtonUp(this.handle, paramInt1, paramInt2);
  }

  LRESULT WM_SETCURSOR(int paramInt1, int paramInt2)
  {
    int i = (short)OS.LOWORD(paramInt2);
    if (i == 1)
    {
      Control localControl = this.display.getControl(paramInt1);
      if (localControl == null)
        return null;
      Cursor localCursor = localControl.findCursor();
      if (localCursor != null)
      {
        OS.SetCursor(localCursor.handle);
        return LRESULT.ONE;
      }
    }
    return null;
  }

  LRESULT WM_SETFOCUS(int paramInt1, int paramInt2)
  {
    return wmSetFocus(this.handle, paramInt1, paramInt2);
  }

  LRESULT WM_SETTINGCHANGE(int paramInt1, int paramInt2)
  {
    return null;
  }

  LRESULT WM_SETFONT(int paramInt1, int paramInt2)
  {
    return null;
  }

  LRESULT WM_SETREDRAW(int paramInt1, int paramInt2)
  {
    return null;
  }

  LRESULT WM_SHOWWINDOW(int paramInt1, int paramInt2)
  {
    return null;
  }

  LRESULT WM_SIZE(int paramInt1, int paramInt2)
  {
    this.state |= 262144;
    if ((this.state & 0x80000) == 0)
      sendEvent(11);
    return null;
  }

  LRESULT WM_SYSCHAR(int paramInt1, int paramInt2)
  {
    return wmSysChar(this.handle, paramInt1, paramInt2);
  }

  LRESULT WM_SYSCOLORCHANGE(int paramInt1, int paramInt2)
  {
    return null;
  }

  LRESULT WM_SYSCOMMAND(int paramInt1, int paramInt2)
  {
    if ((paramInt1 & 0xF000) == 0)
    {
      Decorations localDecorations1 = menuShell();
      if (localDecorations1.isEnabled())
      {
        MenuItem localMenuItem1 = this.display.getMenuItem(OS.LOWORD(paramInt1));
        if (localMenuItem1 != null)
          localMenuItem1.wmCommandChild(paramInt1, paramInt2);
      }
      return LRESULT.ZERO;
    }
    int i = paramInt1 & 0xFFF0;
    Decorations localDecorations2;
    switch (i)
    {
    case 61536:
      int j = menuShell().handle;
      int k = OS.GetWindowLong(j, -16);
      if ((k & 0x80000) == 0)
        return LRESULT.ZERO;
      break;
    case 61696:
      Menu localMenu;
      if (paramInt2 == 0)
      {
        localDecorations2 = menuShell();
        localMenu = localDecorations2.getMenuBar();
        if (localMenu == null)
        {
          Control localControl = this.display._getFocusControl();
          if ((localControl != null) && ((localControl.hooks(1)) || (localControl.hooks(2))))
          {
            this.display.mnemonicKeyHit = false;
            return LRESULT.ZERO;
          }
        }
      }
      else if (((hooks(1)) || (hooks(2))) && (paramInt2 != 32))
      {
        localDecorations2 = menuShell();
        localMenu = localDecorations2.getMenuBar();
        if (localMenu != null)
        {
          char c1 = Display.mbcsToWcs(paramInt2);
          if (c1 != 0)
          {
            c1 = Character.toUpperCase(c1);
            MenuItem[] arrayOfMenuItem = localMenu.getItems();
            for (int m = 0; m < arrayOfMenuItem.length; m++)
            {
              MenuItem localMenuItem2 = arrayOfMenuItem[m];
              String str = localMenuItem2.getText();
              int n = findMnemonic(str);
              if ((str.length() > 0) && (n == 0))
              {
                char c2 = str.charAt(0);
                if (Character.toUpperCase(c2) == c1)
                {
                  this.display.mnemonicKeyHit = false;
                  return LRESULT.ZERO;
                }
              }
            }
          }
        }
        else
        {
          this.display.mnemonicKeyHit = false;
        }
      }
    case 61552:
    case 61568:
      localDecorations2 = menuShell();
      if ((!localDecorations2.isEnabled()) || (!localDecorations2.isActive()))
        return LRESULT.ZERO;
      break;
    case 61472:
      menuShell().saveFocus();
    }
    return null;
  }

  LRESULT WM_SYSKEYDOWN(int paramInt1, int paramInt2)
  {
    return wmSysKeyDown(this.handle, paramInt1, paramInt2);
  }

  LRESULT WM_SYSKEYUP(int paramInt1, int paramInt2)
  {
    return wmSysKeyUp(this.handle, paramInt1, paramInt2);
  }

  LRESULT WM_TIMER(int paramInt1, int paramInt2)
  {
    return null;
  }

  LRESULT WM_UNDO(int paramInt1, int paramInt2)
  {
    return null;
  }

  LRESULT WM_UPDATEUISTATE(int paramInt1, int paramInt2)
  {
    return null;
  }

  LRESULT WM_VSCROLL(int paramInt1, int paramInt2)
  {
    Control localControl = this.display.getControl(paramInt2);
    if (localControl == null)
      return null;
    return localControl.wmScrollChild(paramInt1, paramInt2);
  }

  LRESULT WM_WINDOWPOSCHANGED(int paramInt1, int paramInt2)
  {
    try
    {
      this.display.resizeCount += 1;
      int i = callWindowProc(this.handle, 71, paramInt1, paramInt2);
      LRESULT localLRESULT = i == 0 ? LRESULT.ZERO : new LRESULT(i);
      return localLRESULT;
    }
    finally
    {
      this.display.resizeCount -= 1;
    }
  }

  LRESULT WM_WINDOWPOSCHANGING(int paramInt1, int paramInt2)
  {
    if (!getDrawing())
    {
      Shell localShell = getShell();
      if (localShell != this)
      {
        WINDOWPOS localWINDOWPOS = new WINDOWPOS();
        OS.MoveMemory(localWINDOWPOS, paramInt2, WINDOWPOS.sizeof);
        if (((localWINDOWPOS.flags & 0x2) == 0) || ((localWINDOWPOS.flags & 0x1) == 0))
        {
          RECT localRECT = new RECT();
          OS.GetWindowRect(topHandle(), localRECT);
          int i = localRECT.right - localRECT.left;
          int j = localRECT.bottom - localRECT.top;
          if ((i != 0) && (j != 0))
          {
            int k = this.parent == null ? 0 : this.parent.handle;
            OS.MapWindowPoints(0, k, localRECT, 2);
            if (OS.IsWinCE)
            {
              OS.InvalidateRect(k, localRECT, true);
            }
            else
            {
              int m = OS.CreateRectRgn(localRECT.left, localRECT.top, localRECT.right, localRECT.bottom);
              int n = OS.CreateRectRgn(localWINDOWPOS.x, localWINDOWPOS.y, localWINDOWPOS.x + localWINDOWPOS.cx, localWINDOWPOS.y + localWINDOWPOS.cy);
              OS.CombineRgn(m, m, n, 4);
              int i1 = 1157;
              OS.RedrawWindow(k, null, m, i1);
              OS.DeleteObject(m);
              OS.DeleteObject(n);
            }
          }
        }
      }
    }
    return null;
  }

  LRESULT WM_XBUTTONDBLCLK(int paramInt1, int paramInt2)
  {
    return wmXButtonDblClk(this.handle, paramInt1, paramInt2);
  }

  LRESULT WM_XBUTTONDOWN(int paramInt1, int paramInt2)
  {
    return wmXButtonDown(this.handle, paramInt1, paramInt2);
  }

  LRESULT WM_XBUTTONUP(int paramInt1, int paramInt2)
  {
    return wmXButtonUp(this.handle, paramInt1, paramInt2);
  }

  LRESULT wmColorChild(int paramInt1, int paramInt2)
  {
    Control localControl = findBackgroundControl();
    if (localControl == null)
    {
      if (((this.state & 0x100) != 0) && (OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()))
      {
        localControl = findThemeControl();
        if (localControl != null)
        {
          RECT localRECT1 = new RECT();
          OS.GetClientRect(this.handle, localRECT1);
          OS.SetTextColor(paramInt1, getForegroundPixel());
          OS.SetBkColor(paramInt1, getBackgroundPixel());
          fillThemeBackground(paramInt1, localControl, localRECT1);
          OS.SetBkMode(paramInt1, 1);
          return new LRESULT(OS.GetStockObject(5));
        }
      }
      if (this.foreground == -1)
        return null;
    }
    if (localControl == null)
      localControl = this;
    int i = getForegroundPixel();
    int j = localControl.getBackgroundPixel();
    OS.SetTextColor(paramInt1, i);
    OS.SetBkColor(paramInt1, j);
    int n;
    if (localControl.backgroundImage != null)
    {
      RECT localRECT2 = new RECT();
      OS.GetClientRect(this.handle, localRECT2);
      int m = localControl.handle;
      n = localControl.backgroundImage.handle;
      OS.MapWindowPoints(this.handle, m, localRECT2, 2);
      POINT localPOINT = new POINT();
      OS.GetWindowOrgEx(paramInt1, localPOINT);
      OS.SetBrushOrgEx(paramInt1, -localRECT2.left - localPOINT.x, -localRECT2.top - localPOINT.y, localPOINT);
      int i1 = findBrush(n, 3);
      if ((this.state & 0x200) != 0)
      {
        int i2 = OS.SelectObject(paramInt1, i1);
        OS.MapWindowPoints(m, this.handle, localRECT2, 2);
        OS.PatBlt(paramInt1, localRECT2.left, localRECT2.top, localRECT2.right - localRECT2.left, localRECT2.bottom - localRECT2.top, 15728673);
        OS.SelectObject(paramInt1, i2);
      }
      OS.SetBkMode(paramInt1, 1);
      return new LRESULT(i1);
    }
    int k = findBrush(j, 0);
    if ((this.state & 0x200) != 0)
    {
      RECT localRECT3 = new RECT();
      OS.GetClientRect(this.handle, localRECT3);
      n = OS.SelectObject(paramInt1, k);
      OS.PatBlt(paramInt1, localRECT3.left, localRECT3.top, localRECT3.right - localRECT3.left, localRECT3.bottom - localRECT3.top, 15728673);
      OS.SelectObject(paramInt1, n);
    }
    return new LRESULT(k);
  }

  LRESULT wmCommandChild(int paramInt1, int paramInt2)
  {
    return null;
  }

  LRESULT wmDrawChild(int paramInt1, int paramInt2)
  {
    return null;
  }

  LRESULT wmMeasureChild(int paramInt1, int paramInt2)
  {
    return null;
  }

  LRESULT wmNotify(NMHDR paramNMHDR, int paramInt1, int paramInt2)
  {
    Control localControl = this.display.getControl(paramNMHDR.hwndFrom);
    if (localControl == null)
      return null;
    return localControl.wmNotifyChild(paramNMHDR, paramInt1, paramInt2);
  }

  LRESULT wmNotifyChild(NMHDR paramNMHDR, int paramInt1, int paramInt2)
  {
    return null;
  }

  LRESULT wmScrollChild(int paramInt1, int paramInt2)
  {
    return null;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.Control
 * JD-Core Version:    0.6.2
 */