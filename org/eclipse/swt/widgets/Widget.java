package org.eclipse.swt.widgets;

import java.io.PrintStream;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.GCData;
import org.eclipse.swt.internal.SWTEventListener;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.MSG;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.PAINTSTRUCT;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.SHRGINFO;
import org.eclipse.swt.internal.win32.TRACKMOUSEEVENT;

public abstract class Widget
{
  int style;
  int state;
  Display display;
  EventTable eventTable;
  Object data;
  static final int DISPOSED = 1;
  static final int CANVAS = 2;
  static final int KEYED_DATA = 4;
  static final int DISABLED = 8;
  static final int HIDDEN = 16;
  static final int LAYOUT_NEEDED = 32;
  static final int LAYOUT_CHANGED = 64;
  static final int LAYOUT_CHILD = 128;
  static final int THEME_BACKGROUND = 256;
  static final int DRAW_BACKGROUND = 512;
  static final int PARENT_BACKGROUND = 1024;
  static final int RELEASED = 2048;
  static final int DISPOSE_SENT = 4096;
  static final int TRACK_MOUSE = 8192;
  static final int FOREIGN_HANDLE = 16384;
  static final int DRAG_DETECT = 32768;
  static final int MOVE_OCCURRED = 65536;
  static final int MOVE_DEFERRED = 131072;
  static final int RESIZE_OCCURRED = 262144;
  static final int RESIZE_DEFERRED = 524288;
  static final int IGNORE_WM_CHANGEUISTATE = 1048576;
  static final int SKIN_NEEDED = 2097152;
  static final int DEFAULT_WIDTH = 64;
  static final int DEFAULT_HEIGHT = 64;
  static final int MAJOR = 5;
  static final int MINOR = 80;

  static
  {
    if ((!OS.IsWinCE) && (OS.COMCTL32_VERSION < OS.VERSION(5, 80)))
    {
      System.out.println("***WARNING: SWT requires comctl32.dll version 5.80 or greater");
      System.out.println("***WARNING: Detected: " + OS.COMCTL32_MAJOR + "." + OS.COMCTL32_MINOR);
    }
    OS.InitCommonControls();
  }

  Widget()
  {
  }

  public Widget(Widget paramWidget, int paramInt)
  {
    checkSubclass();
    checkParent(paramWidget);
    this.style = paramInt;
    this.display = paramWidget.display;
    reskinWidget();
  }

  void _addListener(int paramInt, Listener paramListener)
  {
    if (this.eventTable == null)
      this.eventTable = new EventTable();
    this.eventTable.hook(paramInt, paramListener);
  }

  public void addListener(int paramInt, Listener paramListener)
  {
    checkWidget();
    if (paramListener == null)
      error(4);
    _addListener(paramInt, paramListener);
  }

  public void addDisposeListener(DisposeListener paramDisposeListener)
  {
    checkWidget();
    if (paramDisposeListener == null)
      error(4);
    TypedListener localTypedListener = new TypedListener(paramDisposeListener);
    addListener(12, localTypedListener);
  }

  int callWindowProc(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return 0;
  }

  static int checkBits(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7)
  {
    int i = paramInt2 | paramInt3 | paramInt4 | paramInt5 | paramInt6 | paramInt7;
    if ((paramInt1 & i) == 0)
      paramInt1 |= paramInt2;
    if ((paramInt1 & paramInt2) != 0)
      paramInt1 = paramInt1 & (i ^ 0xFFFFFFFF) | paramInt2;
    if ((paramInt1 & paramInt3) != 0)
      paramInt1 = paramInt1 & (i ^ 0xFFFFFFFF) | paramInt3;
    if ((paramInt1 & paramInt4) != 0)
      paramInt1 = paramInt1 & (i ^ 0xFFFFFFFF) | paramInt4;
    if ((paramInt1 & paramInt5) != 0)
      paramInt1 = paramInt1 & (i ^ 0xFFFFFFFF) | paramInt5;
    if ((paramInt1 & paramInt6) != 0)
      paramInt1 = paramInt1 & (i ^ 0xFFFFFFFF) | paramInt6;
    if ((paramInt1 & paramInt7) != 0)
      paramInt1 = paramInt1 & (i ^ 0xFFFFFFFF) | paramInt7;
    return paramInt1;
  }

  void checkOrientation(Widget paramWidget)
  {
    this.style &= -134217729;
    if (((this.style & 0x6000000) == 0) && (paramWidget != null))
    {
      if ((paramWidget.style & 0x2000000) != 0)
        this.style |= 33554432;
      if ((paramWidget.style & 0x4000000) != 0)
        this.style |= 67108864;
    }
    this.style = checkBits(this.style, 33554432, 67108864, 0, 0, 0, 0);
  }

  void checkOpened()
  {
  }

  void checkParent(Widget paramWidget)
  {
    if (paramWidget == null)
      error(4);
    if (paramWidget.isDisposed())
      error(5);
    paramWidget.checkWidget();
    paramWidget.checkOpened();
  }

  protected void checkSubclass()
  {
    if (!isValidSubclass())
      error(43);
  }

  protected void checkWidget()
  {
    Display localDisplay = this.display;
    if (localDisplay == null)
      error(24);
    if ((localDisplay.thread != Thread.currentThread()) && (localDisplay.threadId != OS.GetCurrentThreadId()))
      error(22);
    if ((this.state & 0x1) != 0)
      error(24);
  }

  void destroyWidget()
  {
    releaseHandle();
  }

  int DeferWindowPos(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8)
  {
    if ((OS.IsWinCE) && ((paramInt8 & 0x1) == 0))
    {
      RECT localRECT = new RECT();
      OS.GetWindowRect(paramInt2, localRECT);
      if ((paramInt7 == localRECT.bottom - localRECT.top) && (paramInt6 == localRECT.right - localRECT.left))
      {
        paramInt8 &= -33;
        paramInt8 |= 1;
      }
    }
    return OS.DeferWindowPos(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8);
  }

  public void dispose()
  {
    if (isDisposed())
      return;
    if (!isValidThread())
      error(22);
    release(true);
  }

  boolean dragDetect(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean, boolean[] paramArrayOfBoolean1, boolean[] paramArrayOfBoolean2)
  {
    if (paramArrayOfBoolean2 != null)
      paramArrayOfBoolean2[0] = false;
    if (paramArrayOfBoolean1 != null)
      paramArrayOfBoolean1[0] = true;
    POINT localPOINT = new POINT();
    localPOINT.x = paramInt2;
    localPOINT.y = paramInt3;
    OS.ClientToScreen(paramInt1, localPOINT);
    return OS.DragDetect(paramInt1, localPOINT);
  }

  void error(int paramInt)
  {
    SWT.error(paramInt);
  }

  boolean filters(int paramInt)
  {
    return this.display.filters(paramInt);
  }

  Widget findItem(int paramInt)
  {
    return null;
  }

  char[] fixMnemonic(String paramString)
  {
    return fixMnemonic(paramString, false);
  }

  char[] fixMnemonic(String paramString, boolean paramBoolean)
  {
    char[] arrayOfChar = new char[paramString.length() + 1];
    paramString.getChars(0, paramString.length(), arrayOfChar, 0);
    int i = 0;
    int j = 0;
    while (i < arrayOfChar.length)
      if (arrayOfChar[i] == '&')
      {
        if ((i + 1 < arrayOfChar.length) && (arrayOfChar[(i + 1)] == '&'))
        {
          arrayOfChar[(j++)] = (paramBoolean ? 32 : arrayOfChar[i]);
          i++;
        }
        i++;
      }
      else
      {
        arrayOfChar[(j++)] = arrayOfChar[(i++)];
      }
    while (j < arrayOfChar.length)
      arrayOfChar[(j++)] = '\000';
    return arrayOfChar;
  }

  public Object getData()
  {
    checkWidget();
    return (this.state & 0x4) != 0 ? ((Object[])this.data)[0] : this.data;
  }

  public Object getData(String paramString)
  {
    checkWidget();
    if (paramString == null)
      error(4);
    if ((this.state & 0x4) != 0)
    {
      Object[] arrayOfObject = (Object[])this.data;
      for (int i = 1; i < arrayOfObject.length; i += 2)
        if (paramString.equals(arrayOfObject[i]))
          return arrayOfObject[(i + 1)];
    }
    return null;
  }

  public Display getDisplay()
  {
    Display localDisplay = this.display;
    if (localDisplay == null)
      error(24);
    return localDisplay;
  }

  public Listener[] getListeners(int paramInt)
  {
    checkWidget();
    if (this.eventTable == null)
      return new Listener[0];
    return this.eventTable.getListeners(paramInt);
  }

  Menu getMenu()
  {
    return null;
  }

  String getName()
  {
    String str = getClass().getName();
    int i = str.lastIndexOf('.');
    if (i == -1)
      return str;
    return str.substring(i + 1, str.length());
  }

  String getNameText()
  {
    return "";
  }

  public int getStyle()
  {
    checkWidget();
    return this.style;
  }

  boolean hooks(int paramInt)
  {
    if (this.eventTable == null)
      return false;
    return this.eventTable.hooks(paramInt);
  }

  public boolean isDisposed()
  {
    return (this.state & 0x1) != 0;
  }

  public boolean isListening(int paramInt)
  {
    checkWidget();
    return hooks(paramInt);
  }

  boolean isValidSubclass()
  {
    return Display.isValidClass(getClass());
  }

  boolean isValidThread()
  {
    return getDisplay().isValidThread();
  }

  void mapEvent(int paramInt, Event paramEvent)
  {
  }

  GC new_GC(GCData paramGCData)
  {
    return null;
  }

  public void notifyListeners(int paramInt, Event paramEvent)
  {
    checkWidget();
    if (paramEvent == null)
      paramEvent = new Event();
    sendEvent(paramInt, paramEvent);
  }

  void postEvent(int paramInt)
  {
    sendEvent(paramInt, null, false);
  }

  void postEvent(int paramInt, Event paramEvent)
  {
    sendEvent(paramInt, paramEvent, false);
  }

  void release(boolean paramBoolean)
  {
    if ((this.state & 0x1000) == 0)
    {
      this.state |= 4096;
      sendEvent(12);
    }
    if ((this.state & 0x1) == 0)
      releaseChildren(paramBoolean);
    if ((this.state & 0x800) == 0)
    {
      this.state |= 2048;
      if (paramBoolean)
      {
        releaseParent();
        releaseWidget();
        destroyWidget();
      }
      else
      {
        releaseWidget();
        releaseHandle();
      }
    }
  }

  void releaseChildren(boolean paramBoolean)
  {
  }

  void releaseHandle()
  {
    this.state |= 1;
    this.display = null;
  }

  void releaseParent()
  {
  }

  void releaseWidget()
  {
    this.eventTable = null;
    this.data = null;
  }

  public void removeListener(int paramInt, Listener paramListener)
  {
    checkWidget();
    if (paramListener == null)
      error(4);
    if (this.eventTable == null)
      return;
    this.eventTable.unhook(paramInt, paramListener);
  }

  protected void removeListener(int paramInt, SWTEventListener paramSWTEventListener)
  {
    checkWidget();
    if (paramSWTEventListener == null)
      error(4);
    if (this.eventTable == null)
      return;
    this.eventTable.unhook(paramInt, paramSWTEventListener);
  }

  public void removeDisposeListener(DisposeListener paramDisposeListener)
  {
    checkWidget();
    if (paramDisposeListener == null)
      error(4);
    if (this.eventTable == null)
      return;
    this.eventTable.unhook(12, paramDisposeListener);
  }

  public void reskin(int paramInt)
  {
    checkWidget();
    reskinWidget();
    if ((paramInt & 0x1) != 0)
      reskinChildren(paramInt);
  }

  void reskinChildren(int paramInt)
  {
  }

  void reskinWidget()
  {
    if ((this.state & 0x200000) != 2097152)
    {
      this.state |= 2097152;
      this.display.addSkinnableWidget(this);
    }
  }

  boolean sendDragEvent(int paramInt1, int paramInt2, int paramInt3)
  {
    Event localEvent = new Event();
    localEvent.button = paramInt1;
    localEvent.x = paramInt2;
    localEvent.y = paramInt3;
    setInputState(localEvent, 29);
    postEvent(29, localEvent);
    if (isDisposed())
      return false;
    return localEvent.doit;
  }

  boolean sendDragEvent(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    Event localEvent = new Event();
    localEvent.button = paramInt1;
    localEvent.x = paramInt3;
    localEvent.y = paramInt4;
    localEvent.stateMask = paramInt2;
    postEvent(29, localEvent);
    if (isDisposed())
      return false;
    return localEvent.doit;
  }

  void sendEvent(Event paramEvent)
  {
    Display localDisplay = paramEvent.display;
    if ((!localDisplay.filterEvent(paramEvent)) && (this.eventTable != null))
      this.eventTable.sendEvent(paramEvent);
  }

  void sendEvent(int paramInt)
  {
    sendEvent(paramInt, null, true);
  }

  void sendEvent(int paramInt, Event paramEvent)
  {
    sendEvent(paramInt, paramEvent, true);
  }

  void sendEvent(int paramInt, Event paramEvent, boolean paramBoolean)
  {
    if ((this.eventTable == null) && (!this.display.filters(paramInt)))
      return;
    if (paramEvent == null)
      paramEvent = new Event();
    paramEvent.type = paramInt;
    paramEvent.display = this.display;
    paramEvent.widget = this;
    if (paramEvent.time == 0)
      paramEvent.time = this.display.getLastEventTime();
    if (paramBoolean)
      sendEvent(paramEvent);
    else
      this.display.postEvent(paramEvent);
  }

  void sendSelectionEvent(int paramInt)
  {
    sendSelectionEvent(paramInt, null, false);
  }

  void sendSelectionEvent(int paramInt, Event paramEvent, boolean paramBoolean)
  {
    if ((this.eventTable == null) && (!this.display.filters(paramInt)))
      return;
    if (paramEvent == null)
      paramEvent = new Event();
    setInputState(paramEvent, paramInt);
    sendEvent(paramInt, paramEvent, paramBoolean);
  }

  boolean sendKeyEvent(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    Event localEvent = new Event();
    if (!setKeyState(localEvent, paramInt1, paramInt3, paramInt4))
      return true;
    return sendKeyEvent(paramInt1, paramInt2, paramInt3, paramInt4, localEvent);
  }

  boolean sendKeyEvent(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Event paramEvent)
  {
    sendEvent(paramInt1, paramEvent);
    if (isDisposed())
      return false;
    return paramEvent.doit;
  }

  boolean sendMouseEvent(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    return sendMouseEvent(paramInt1, paramInt2, this.display.getClickCount(paramInt1, paramInt2, paramInt3, paramInt6), 0, false, paramInt3, paramInt4, paramInt5, paramInt6);
  }

  boolean sendMouseEvent(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean, int paramInt5, int paramInt6, int paramInt7, int paramInt8)
  {
    if ((!hooks(paramInt1)) && (!filters(paramInt1)))
      return true;
    Event localEvent = new Event();
    localEvent.button = paramInt2;
    localEvent.detail = paramInt4;
    localEvent.count = paramInt3;
    localEvent.x = OS.GET_X_LPARAM(paramInt8);
    localEvent.y = OS.GET_Y_LPARAM(paramInt8);
    setInputState(localEvent, paramInt1);
    mapEvent(paramInt5, localEvent);
    if (paramBoolean)
    {
      sendEvent(paramInt1, localEvent);
      if (isDisposed())
        return false;
    }
    else
    {
      postEvent(paramInt1, localEvent);
    }
    return localEvent.doit;
  }

  boolean sendMouseWheelEvent(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = OS.GET_WHEEL_DELTA_WPARAM(paramInt3);
    int j = 0;
    if (paramInt1 == 37)
    {
      int[] arrayOfInt = new int[1];
      OS.SystemParametersInfo(104, 0, arrayOfInt, 0);
      if (arrayOfInt[0] == -1)
      {
        j = 2;
      }
      else
      {
        j = 1;
        i *= arrayOfInt[0];
      }
      if ((i ^ this.display.scrollRemainder) >= 0)
        i += this.display.scrollRemainder;
      this.display.scrollRemainder = (i % 120);
    }
    else
    {
      if ((i ^ this.display.scrollHRemainder) >= 0)
        i += this.display.scrollHRemainder;
      this.display.scrollHRemainder = (i % 120);
      i = -i;
    }
    if ((!hooks(paramInt1)) && (!filters(paramInt1)))
      return true;
    int k = i / 120;
    POINT localPOINT = new POINT();
    OS.POINTSTOPOINT(localPOINT, paramInt4);
    OS.ScreenToClient(paramInt2, localPOINT);
    paramInt4 = OS.MAKELPARAM(localPOINT.x, localPOINT.y);
    return sendMouseEvent(paramInt1, 0, k, j, true, paramInt2, 522, paramInt3, paramInt4);
  }

  public void setData(Object paramObject)
  {
    checkWidget();
    if ((this.state & 0x4) != 0)
      ((Object[])this.data)[0] = paramObject;
    else
      this.data = paramObject;
  }

  public void setData(String paramString, Object paramObject)
  {
    checkWidget();
    if (paramString == null)
      error(4);
    int i = 1;
    Object localObject = (Object[])null;
    if ((this.state & 0x4) != 0)
    {
      localObject = (Object[])this.data;
      while (i < localObject.length)
      {
        if (paramString.equals(localObject[i]))
          break;
        i += 2;
      }
    }
    if (paramObject != null)
    {
      if ((this.state & 0x4) != 0)
      {
        if (i == localObject.length)
        {
          Object[] arrayOfObject1 = new Object[localObject.length + 2];
          System.arraycopy(localObject, 0, arrayOfObject1, 0, localObject.length);
          this.data = (localObject = arrayOfObject1);
        }
      }
      else
      {
        localObject = new Object[3];
        localObject[0] = this.data;
        this.data = localObject;
        this.state |= 4;
      }
      localObject[i] = paramString;
      localObject[(i + 1)] = paramObject;
    }
    else if (((this.state & 0x4) != 0) && (i != localObject.length))
    {
      int j = localObject.length - 2;
      if (j == 1)
      {
        this.data = localObject[0];
        this.state &= -5;
      }
      else
      {
        Object[] arrayOfObject2 = new Object[j];
        System.arraycopy(localObject, 0, arrayOfObject2, 0, i);
        System.arraycopy(localObject, i + 2, arrayOfObject2, i, j - i);
        this.data = arrayOfObject2;
      }
    }
    if ((paramString.equals("org.eclipse.swt.skin.class")) || (paramString.equals("org.eclipse.swt.skin.id")))
      reskin(1);
  }

  boolean sendFocusEvent(int paramInt)
  {
    sendEvent(paramInt);
    return true;
  }

  boolean setInputState(Event paramEvent, int paramInt)
  {
    if (OS.GetKeyState(18) < 0)
      paramEvent.stateMask |= 65536;
    if (OS.GetKeyState(16) < 0)
      paramEvent.stateMask |= 131072;
    if (OS.GetKeyState(17) < 0)
      paramEvent.stateMask |= 262144;
    if (OS.GetKeyState(1) < 0)
      paramEvent.stateMask |= 524288;
    if (OS.GetKeyState(4) < 0)
      paramEvent.stateMask |= 1048576;
    if (OS.GetKeyState(2) < 0)
      paramEvent.stateMask |= 2097152;
    if (this.display.xMouse)
    {
      if (OS.GetKeyState(5) < 0)
        paramEvent.stateMask |= 8388608;
      if (OS.GetKeyState(6) < 0)
        paramEvent.stateMask |= 33554432;
    }
    switch (paramInt)
    {
    case 3:
    case 8:
      if (paramEvent.button == 1)
        paramEvent.stateMask &= -524289;
      if (paramEvent.button == 2)
        paramEvent.stateMask &= -1048577;
      if (paramEvent.button == 3)
        paramEvent.stateMask &= -2097153;
      if (paramEvent.button == 4)
        paramEvent.stateMask &= -8388609;
      if (paramEvent.button == 5)
        paramEvent.stateMask &= -33554433;
      break;
    case 4:
      if (paramEvent.button == 1)
        paramEvent.stateMask |= 524288;
      if (paramEvent.button == 2)
        paramEvent.stateMask |= 1048576;
      if (paramEvent.button == 3)
        paramEvent.stateMask |= 2097152;
      if (paramEvent.button == 4)
        paramEvent.stateMask |= 8388608;
      if (paramEvent.button == 5)
        paramEvent.stateMask |= 33554432;
      break;
    case 1:
    case 31:
      if (paramEvent.keyCode == 65536)
        paramEvent.stateMask &= -65537;
      if (paramEvent.keyCode == 131072)
        paramEvent.stateMask &= -131073;
      if (paramEvent.keyCode == 262144)
        paramEvent.stateMask &= -262145;
      break;
    case 2:
      if (paramEvent.keyCode == 65536)
        paramEvent.stateMask |= 65536;
      if (paramEvent.keyCode == 131072)
        paramEvent.stateMask |= 131072;
      if (paramEvent.keyCode == 262144)
        paramEvent.stateMask |= 262144;
      break;
    }
    return true;
  }

  boolean setKeyState(Event paramEvent, int paramInt1, int paramInt2, int paramInt3)
  {
    switch (this.display.lastAscii)
    {
    case 127:
      if (this.display.lastKey == 8)
        this.display.lastAscii = 8;
      break;
    case 10:
      if (this.display.lastKey == 13)
        this.display.lastAscii = 13;
      break;
    }
    if ((this.display.lastKey == 13) && (this.display.lastAscii == 13) && ((paramInt3 & 0x1000000) != 0))
      this.display.lastKey = 16777296;
    setLocationMask(paramEvent, paramInt1, paramInt2, paramInt3);
    if (this.display.lastVirtual)
    {
      if (this.display.lastKey == 46)
        this.display.lastAscii = 127;
      if (this.display.lastKey == 3)
        this.display.lastAscii = 0;
      paramEvent.keyCode = Display.translateKey(this.display.lastKey);
    }
    else
    {
      paramEvent.keyCode = this.display.lastKey;
    }
    if ((this.display.lastAscii != 0) || (this.display.lastNull))
      paramEvent.character = Display.mbcsToWcs((char)this.display.lastAscii);
    if ((paramEvent.keyCode == 0) && (paramEvent.character == 0) && (!this.display.lastNull))
      return false;
    return setInputState(paramEvent, paramInt1);
  }

  int setLocationMask(Event paramEvent, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = 0;
    if (this.display.lastVirtual)
    {
      switch (this.display.lastKey)
      {
      case 16:
        if (OS.GetKeyState(160) < 0)
          i = 16384;
        if (OS.GetKeyState(161) < 0)
          i = 131072;
        break;
      case 144:
        i = 2;
        break;
      case 17:
      case 18:
        i = (paramInt3 & 0x1000000) == 0 ? 16384 : 131072;
        break;
      case 33:
      case 34:
      case 35:
      case 36:
      case 37:
      case 38:
      case 39:
      case 40:
      case 45:
      case 46:
        if ((paramInt3 & 0x1000000) == 0)
          i = 2;
        break;
      }
      if (this.display.numpadKey(this.display.lastKey) != 0)
        i = 2;
    }
    else if (this.display.lastKey == 16777296)
    {
      i = 2;
    }
    paramEvent.keyLocation = i;
    return i;
  }

  boolean setTabGroupFocus()
  {
    return setTabItemFocus();
  }

  boolean setTabItemFocus()
  {
    return false;
  }

  boolean SetWindowPos(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7)
  {
    if ((OS.IsWinCE) && ((paramInt7 & 0x1) == 0))
    {
      RECT localRECT = new RECT();
      OS.GetWindowRect(paramInt1, localRECT);
      if ((paramInt6 == localRECT.bottom - localRECT.top) && (paramInt5 == localRECT.right - localRECT.left))
      {
        paramInt7 &= -33;
        paramInt7 |= 1;
      }
    }
    return OS.SetWindowPos(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7);
  }

  boolean showMenu(int paramInt1, int paramInt2)
  {
    Event localEvent = new Event();
    localEvent.x = paramInt1;
    localEvent.y = paramInt2;
    sendEvent(35, localEvent);
    if (isDisposed())
      return false;
    if (!localEvent.doit)
      return true;
    Menu localMenu = getMenu();
    if ((localMenu != null) && (!localMenu.isDisposed()))
    {
      if ((paramInt1 != localEvent.x) || (paramInt2 != localEvent.y))
        localMenu.setLocation(localEvent.x, localEvent.y);
      localMenu.setVisible(true);
      return true;
    }
    return false;
  }

  public String toString()
  {
    String str = "*Disposed*";
    if (!isDisposed())
    {
      str = "*Wrong Thread*";
      if (isValidThread())
        str = getNameText();
    }
    return getName() + " {" + str + "}";
  }

  LRESULT wmCaptureChanged(int paramInt1, int paramInt2, int paramInt3)
  {
    this.display.captureChanged = true;
    return null;
  }

  LRESULT wmChar(int paramInt1, int paramInt2, int paramInt3)
  {
    if ((!OS.IsUnicode) && (OS.IsDBLocale))
    {
      byte b = (byte)(paramInt2 & 0xFF);
      if (OS.IsDBCSLeadByte(b))
        return null;
    }
    this.display.lastAscii = paramInt2;
    this.display.lastNull = (paramInt2 == 0);
    if (!sendKeyEvent(1, 258, paramInt2, paramInt3))
      return LRESULT.ONE;
    return null;
  }

  LRESULT wmContextMenu(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt2 != paramInt1)
      return null;
    if (OS.IsWinCE)
      return null;
    int i = 0;
    int j = 0;
    if (paramInt3 != -1)
    {
      POINT localPOINT = new POINT();
      OS.POINTSTOPOINT(localPOINT, paramInt3);
      i = localPOINT.x;
      j = localPOINT.y;
      OS.ScreenToClient(paramInt1, localPOINT);
      RECT localRECT = new RECT();
      OS.GetClientRect(paramInt1, localRECT);
      if (!OS.PtInRect(localRECT, localPOINT))
        return null;
    }
    else
    {
      int k = OS.GetMessagePos();
      i = OS.GET_X_LPARAM(k);
      j = OS.GET_Y_LPARAM(k);
    }
    return showMenu(i, j) ? LRESULT.ZERO : null;
  }

  LRESULT wmIMEChar(int paramInt1, int paramInt2, int paramInt3)
  {
    Display localDisplay = this.display;
    localDisplay.lastKey = 0;
    localDisplay.lastAscii = paramInt2;
    localDisplay.lastVirtual = (localDisplay.lastNull = localDisplay.lastDead = 0);
    if (!sendKeyEvent(1, 646, paramInt2, paramInt3))
      return LRESULT.ONE;
    sendKeyEvent(2, 646, paramInt2, paramInt3);
    localDisplay.lastKey = (localDisplay.lastAscii = 0);
    return LRESULT.ONE;
  }

  LRESULT wmKeyDown(int paramInt1, int paramInt2, int paramInt3)
  {
    switch (paramInt2)
    {
    case 16:
    case 17:
    case 18:
    case 20:
    case 144:
    case 145:
      if ((paramInt3 & 0x40000000) != 0)
        return null;
      break;
    }
    this.display.lastAscii = (this.display.lastKey = 0);
    this.display.lastVirtual = (this.display.lastNull = this.display.lastDead = 0);
    if ((!OS.IsUnicode) && (OS.IsDBLocale))
    {
      b = (byte)(paramInt2 & 0xFF);
      if (OS.IsDBCSLeadByte(b))
        return null;
    }
    byte b = 0;
    int i;
    if (OS.IsWinCE)
    {
      switch (paramInt2)
      {
      case 8:
        b = 8;
        break;
      case 13:
        b = 13;
        break;
      case 46:
        b = 127;
        break;
      case 27:
        b = 27;
        break;
      case 9:
        b = 9;
      default:
        break;
      }
    }
    else
    {
      i = OS.MapVirtualKey(paramInt2, 2);
      if (((2534 <= i) && (i <= 2543)) || ((2406 <= i) && (i <= 2415)))
        i = paramInt2;
    }
    if (OS.IsWinNT)
    {
      if ((i & 0x80000000) != 0)
        return null;
    }
    else if ((i & 0x8000) != 0)
      return null;
    MSG localMSG = new MSG();
    int j = 10420226;
    if (OS.PeekMessage(localMSG, paramInt1, 259, 259, j))
    {
      this.display.lastDead = true;
      this.display.lastVirtual = (i == 0);
      this.display.lastKey = (this.display.lastVirtual ? paramInt2 : i);
      return null;
    }
    if (isDisposed())
      return LRESULT.ONE;
    this.display.lastVirtual = ((i == 0) || (this.display.numpadKey(paramInt2) != 0));
    if (this.display.lastVirtual)
    {
      this.display.lastKey = paramInt2;
      if (this.display.lastKey == 46)
        this.display.lastAscii = 127;
      if ((96 <= this.display.lastKey) && (this.display.lastKey <= 111))
      {
        if (this.display.asciiKey(this.display.lastKey) != 0)
          return null;
        this.display.lastAscii = this.display.numpadKey(this.display.lastKey);
      }
    }
    else
    {
      this.display.lastKey = OS.CharLower((short)i);
      if (paramInt2 == 3)
        this.display.lastVirtual = true;
      int k = this.display.asciiKey(paramInt2);
      if (k != 0)
      {
        if (k == 32)
          return null;
        if (k != paramInt2)
          return null;
        if (paramInt2 == 3)
          return null;
      }
      if (OS.GetKeyState(17) >= 0)
        return null;
      if (OS.GetKeyState(16) < 0)
      {
        this.display.lastAscii = this.display.shiftedKey(paramInt2);
        if (this.display.lastAscii == 0)
          this.display.lastAscii = i;
      }
      else
      {
        this.display.lastAscii = OS.CharLower((short)i);
      }
      if (this.display.lastAscii == 64)
        return null;
      this.display.lastAscii = this.display.controlKey(this.display.lastAscii);
    }
    if (!sendKeyEvent(1, 256, paramInt2, paramInt3))
      return LRESULT.ONE;
    return null;
  }

  LRESULT wmKeyUp(int paramInt1, int paramInt2, int paramInt3)
  {
    Display localDisplay = this.display;
    if ((OS.IsWinCE) && (193 <= paramInt2) && (paramInt2 <= 198))
    {
      localDisplay.lastKey = (localDisplay.lastAscii = 0);
      localDisplay.lastVirtual = (localDisplay.lastNull = localDisplay.lastDead = 0);
      Event localEvent = new Event();
      localEvent.detail = (paramInt2 - 193 + 1);
      int j = (paramInt3 & 0x40000000) != 0 ? 34 : 33;
      if (setInputState(localEvent, j))
        sendEvent(j, localEvent);
      return null;
    }
    if ((!hooks(2)) && (!localDisplay.filters(2)))
    {
      localDisplay.lastKey = (localDisplay.lastAscii = 0);
      localDisplay.lastVirtual = (localDisplay.lastNull = localDisplay.lastDead = 0);
      return null;
    }
    int i = 0;
    if (OS.IsWinCE)
      switch (paramInt2)
      {
      case 8:
        i = 8;
        break;
      case 13:
        i = 13;
        break;
      case 46:
        i = 127;
        break;
      case 27:
        i = 27;
        break;
      case 9:
        i = 9;
      default:
        break;
      }
    else
      i = OS.MapVirtualKey(paramInt2, 2);
    if (OS.IsWinNT)
    {
      if ((i & 0x80000000) != 0)
        return null;
    }
    else if ((i & 0x8000) != 0)
      return null;
    if (localDisplay.lastDead)
      return null;
    localDisplay.lastVirtual = ((i == 0) || (localDisplay.numpadKey(paramInt2) != 0));
    if (localDisplay.lastVirtual)
    {
      localDisplay.lastKey = paramInt2;
    }
    else
    {
      if (paramInt2 == 3)
        localDisplay.lastVirtual = true;
      if (localDisplay.lastKey == 0)
      {
        localDisplay.lastAscii = 0;
        localDisplay.lastNull = (localDisplay.lastDead = 0);
        return null;
      }
    }
    LRESULT localLRESULT = null;
    if (!sendKeyEvent(2, 257, paramInt2, paramInt3))
      localLRESULT = LRESULT.ONE;
    localDisplay.lastKey = (localDisplay.lastAscii = 0);
    localDisplay.lastVirtual = (localDisplay.lastNull = localDisplay.lastDead = 0);
    return localLRESULT;
  }

  LRESULT wmKillFocus(int paramInt1, int paramInt2, int paramInt3)
  {
    this.display.scrollRemainder = (this.display.scrollHRemainder = 0);
    int i = callWindowProc(paramInt1, 8, paramInt2, paramInt3);
    sendFocusEvent(16);
    if (isDisposed())
      return LRESULT.ZERO;
    if (i == 0)
      return LRESULT.ZERO;
    return new LRESULT(i);
  }

  LRESULT wmLButtonDblClk(int paramInt1, int paramInt2, int paramInt3)
  {
    LRESULT localLRESULT = null;
    Display localDisplay = this.display;
    localDisplay.captureChanged = false;
    sendMouseEvent(3, 1, paramInt1, 513, paramInt2, paramInt3);
    if (sendMouseEvent(8, 1, paramInt1, 515, paramInt2, paramInt3))
      localLRESULT = new LRESULT(callWindowProc(paramInt1, 515, paramInt2, paramInt3));
    else
      localLRESULT = LRESULT.ZERO;
    if ((!localDisplay.captureChanged) && (!isDisposed()) && (OS.GetCapture() != paramInt1))
      OS.SetCapture(paramInt1);
    return localLRESULT;
  }

  LRESULT wmLButtonDown(int paramInt1, int paramInt2, int paramInt3)
  {
    Display localDisplay = this.display;
    LRESULT localLRESULT = null;
    int i = OS.GET_X_LPARAM(paramInt3);
    int j = OS.GET_Y_LPARAM(paramInt3);
    boolean[] arrayOfBoolean1 = (boolean[])null;
    boolean[] arrayOfBoolean2 = (boolean[])null;
    boolean bool1 = false;
    int k = 1;
    int m = localDisplay.getClickCount(3, 1, paramInt1, paramInt3);
    if ((m == 1) && ((this.state & 0x8000) != 0) && (hooks(29)) && (!OS.IsWinCE))
    {
      arrayOfBoolean2 = new boolean[1];
      arrayOfBoolean1 = new boolean[1];
      bool1 = dragDetect(paramInt1, i, j, true, arrayOfBoolean2, arrayOfBoolean1);
      if (isDisposed())
        return LRESULT.ZERO;
      k = OS.GetKeyState(1) < 0 ? 1 : 0;
    }
    localDisplay.captureChanged = false;
    boolean bool2 = sendMouseEvent(3, 1, m, 0, false, paramInt1, 513, paramInt2, paramInt3);
    if ((bool2) && ((arrayOfBoolean1 == null) || (arrayOfBoolean1[0] == 0)))
      localLRESULT = new LRESULT(callWindowProc(paramInt1, 513, paramInt2, paramInt3));
    else
      localLRESULT = LRESULT.ZERO;
    if (OS.IsPPC)
    {
      Menu localMenu = getMenu();
      int n = (localMenu != null) && (!localMenu.isDisposed()) ? 1 : 0;
      if ((n != 0) || (hooks(35)))
      {
        SHRGINFO localSHRGINFO = new SHRGINFO();
        localSHRGINFO.cbSize = SHRGINFO.sizeof;
        localSHRGINFO.hwndClient = paramInt1;
        localSHRGINFO.ptDown_x = i;
        localSHRGINFO.ptDown_y = j;
        localSHRGINFO.dwFlags = 1;
        int i1 = OS.SHRecognizeGesture(localSHRGINFO);
        if (i1 == 1000)
          showMenu(i, j);
      }
    }
    if ((k != 0) && (!localDisplay.captureChanged) && (!isDisposed()) && (OS.GetCapture() != paramInt1))
      OS.SetCapture(paramInt1);
    if (bool1)
      sendDragEvent(1, i, j);
    else if ((arrayOfBoolean2 != null) && (arrayOfBoolean2[0] != 0) && (OS.GetKeyState(27) >= 0))
      OS.SendMessage(paramInt1, 514, paramInt2, paramInt3);
    return localLRESULT;
  }

  LRESULT wmLButtonUp(int paramInt1, int paramInt2, int paramInt3)
  {
    Display localDisplay = this.display;
    LRESULT localLRESULT = null;
    if (sendMouseEvent(4, 1, paramInt1, 514, paramInt2, paramInt3))
      localLRESULT = new LRESULT(callWindowProc(paramInt1, 514, paramInt2, paramInt3));
    else
      localLRESULT = LRESULT.ZERO;
    int i = 19;
    if (localDisplay.xMouse)
      i |= 96;
    if (((paramInt2 & i) == 0) && (OS.GetCapture() == paramInt1))
      OS.ReleaseCapture();
    return localLRESULT;
  }

  LRESULT wmMButtonDblClk(int paramInt1, int paramInt2, int paramInt3)
  {
    LRESULT localLRESULT = null;
    Display localDisplay = this.display;
    localDisplay.captureChanged = false;
    sendMouseEvent(3, 2, paramInt1, 519, paramInt2, paramInt3);
    if (sendMouseEvent(8, 2, paramInt1, 521, paramInt2, paramInt3))
      localLRESULT = new LRESULT(callWindowProc(paramInt1, 521, paramInt2, paramInt3));
    else
      localLRESULT = LRESULT.ZERO;
    if ((!localDisplay.captureChanged) && (!isDisposed()) && (OS.GetCapture() != paramInt1))
      OS.SetCapture(paramInt1);
    return localLRESULT;
  }

  LRESULT wmMButtonDown(int paramInt1, int paramInt2, int paramInt3)
  {
    LRESULT localLRESULT = null;
    Display localDisplay = this.display;
    localDisplay.captureChanged = false;
    if (sendMouseEvent(3, 2, paramInt1, 519, paramInt2, paramInt3))
      localLRESULT = new LRESULT(callWindowProc(paramInt1, 519, paramInt2, paramInt3));
    else
      localLRESULT = LRESULT.ZERO;
    if ((!localDisplay.captureChanged) && (!isDisposed()) && (OS.GetCapture() != paramInt1))
      OS.SetCapture(paramInt1);
    return localLRESULT;
  }

  LRESULT wmMButtonUp(int paramInt1, int paramInt2, int paramInt3)
  {
    Display localDisplay = this.display;
    LRESULT localLRESULT = null;
    if (sendMouseEvent(4, 2, paramInt1, 520, paramInt2, paramInt3))
      localLRESULT = new LRESULT(callWindowProc(paramInt1, 520, paramInt2, paramInt3));
    else
      localLRESULT = LRESULT.ZERO;
    int i = 19;
    if (localDisplay.xMouse)
      i |= 96;
    if (((paramInt2 & i) == 0) && (OS.GetCapture() == paramInt1))
      OS.ReleaseCapture();
    return localLRESULT;
  }

  LRESULT wmMouseHover(int paramInt1, int paramInt2, int paramInt3)
  {
    if (!sendMouseEvent(32, 0, paramInt1, 673, paramInt2, paramInt3))
      return LRESULT.ZERO;
    return null;
  }

  LRESULT wmMouseLeave(int paramInt1, int paramInt2, int paramInt3)
  {
    if ((!hooks(7)) && (!filters(7)))
      return null;
    int i = OS.GetMessagePos();
    POINT localPOINT = new POINT();
    OS.POINTSTOPOINT(localPOINT, i);
    OS.ScreenToClient(paramInt1, localPOINT);
    paramInt3 = OS.MAKELPARAM(localPOINT.x, localPOINT.y);
    if (!sendMouseEvent(7, 0, paramInt1, 675, paramInt2, paramInt3))
      return LRESULT.ZERO;
    return null;
  }

  LRESULT wmMouseMove(int paramInt1, int paramInt2, int paramInt3)
  {
    LRESULT localLRESULT = null;
    Display localDisplay = this.display;
    int i = OS.GetMessagePos();
    if ((i != localDisplay.lastMouse) || (localDisplay.captureChanged))
    {
      if (!OS.IsWinCE)
      {
        int j = (this.state & 0x2000) != 0 ? 1 : 0;
        int k = (!hooks(6)) && (!localDisplay.filters(6)) ? 0 : 1;
        int m = (!hooks(7)) && (!localDisplay.filters(7)) ? 0 : 1;
        int n = (!hooks(32)) && (!localDisplay.filters(32)) ? 0 : 1;
        if ((j != 0) || (k != 0) || (m != 0) || (n != 0))
        {
          TRACKMOUSEEVENT localTRACKMOUSEEVENT = new TRACKMOUSEEVENT();
          localTRACKMOUSEEVENT.cbSize = TRACKMOUSEEVENT.sizeof;
          localTRACKMOUSEEVENT.dwFlags = 1073741824;
          localTRACKMOUSEEVENT.hwndTrack = paramInt1;
          OS.TrackMouseEvent(localTRACKMOUSEEVENT);
          if (localTRACKMOUSEEVENT.dwFlags == 0)
          {
            localTRACKMOUSEEVENT.dwFlags = 3;
            localTRACKMOUSEEVENT.hwndTrack = paramInt1;
            OS.TrackMouseEvent(localTRACKMOUSEEVENT);
            if (k != 0)
            {
              MSG localMSG = new MSG();
              int i1 = 10420227;
              while (OS.PeekMessage(localMSG, 0, 675, 675, i1))
              {
                OS.TranslateMessage(localMSG);
                OS.DispatchMessage(localMSG);
              }
              sendMouseEvent(6, 0, paramInt1, 512, paramInt2, paramInt3);
            }
          }
          else
          {
            localTRACKMOUSEEVENT.dwFlags = 1;
            OS.TrackMouseEvent(localTRACKMOUSEEVENT);
          }
        }
      }
      if (i != localDisplay.lastMouse)
      {
        localDisplay.lastMouse = i;
        if (!sendMouseEvent(5, 0, paramInt1, 512, paramInt2, paramInt3))
          localLRESULT = LRESULT.ZERO;
      }
    }
    localDisplay.captureChanged = false;
    return localLRESULT;
  }

  LRESULT wmMouseWheel(int paramInt1, int paramInt2, int paramInt3)
  {
    return sendMouseWheelEvent(37, paramInt1, paramInt2, paramInt3) ? null : LRESULT.ZERO;
  }

  LRESULT wmMouseHWheel(int paramInt1, int paramInt2, int paramInt3)
  {
    return sendMouseWheelEvent(38, paramInt1, paramInt2, paramInt3) ? null : LRESULT.ZERO;
  }

  LRESULT wmNCPaint(int paramInt1, int paramInt2, int paramInt3)
  {
    return null;
  }

  LRESULT wmPaint(int paramInt1, int paramInt2, int paramInt3)
  {
    if ((!hooks(9)) && (!filters(9)))
      return null;
    int i = 0;
    Object localObject1;
    Object localObject2;
    Object localObject3;
    int k;
    int m;
    if (OS.IsWinCE)
    {
      RECT localRECT = new RECT();
      OS.GetUpdateRect(paramInt1, localRECT, false);
      i = callWindowProc(paramInt1, 15, paramInt2, paramInt3);
      OS.HideCaret(paramInt1);
      OS.InvalidateRect(paramInt1, localRECT, false);
      OS.ShowCaret(paramInt1);
      localObject1 = new PAINTSTRUCT();
      localObject2 = new GCData();
      ((GCData)localObject2).ps = ((PAINTSTRUCT)localObject1);
      ((GCData)localObject2).hwnd = paramInt1;
      localObject3 = new_GC((GCData)localObject2);
      if (localObject3 != null)
      {
        k = ((PAINTSTRUCT)localObject1).right - ((PAINTSTRUCT)localObject1).left;
        m = ((PAINTSTRUCT)localObject1).bottom - ((PAINTSTRUCT)localObject1).top;
        if ((k != 0) && (m != 0))
        {
          Event localEvent1 = new Event();
          localEvent1.gc = ((GC)localObject3);
          localEvent1.x = ((PAINTSTRUCT)localObject1).left;
          localEvent1.y = ((PAINTSTRUCT)localObject1).top;
          localEvent1.width = k;
          localEvent1.height = m;
          sendEvent(9, localEvent1);
          localEvent1.gc = null;
        }
        ((GC)localObject3).dispose();
      }
    }
    else
    {
      int j = OS.CreateRectRgn(0, 0, 0, 0);
      OS.GetUpdateRgn(paramInt1, j, false);
      i = callWindowProc(paramInt1, 15, paramInt2, paramInt3);
      localObject1 = new GCData();
      ((GCData)localObject1).hwnd = paramInt1;
      localObject2 = new_GC((GCData)localObject1);
      if (localObject2 != null)
      {
        OS.HideCaret(paramInt1);
        localObject3 = new RECT();
        OS.GetRgnBox(j, (RECT)localObject3);
        k = ((RECT)localObject3).right - ((RECT)localObject3).left;
        m = ((RECT)localObject3).bottom - ((RECT)localObject3).top;
        if ((k != 0) && (m != 0))
        {
          int n = ((GC)localObject2).handle;
          OS.SelectClipRgn(n, j);
          OS.SetMetaRgn(n);
          Event localEvent2 = new Event();
          localEvent2.gc = ((GC)localObject2);
          localEvent2.x = ((RECT)localObject3).left;
          localEvent2.y = ((RECT)localObject3).top;
          localEvent2.width = k;
          localEvent2.height = m;
          sendEvent(9, localEvent2);
          localEvent2.gc = null;
        }
        ((GC)localObject2).dispose();
        OS.ShowCaret(paramInt1);
      }
      OS.DeleteObject(j);
    }
    if (i == 0)
      return LRESULT.ZERO;
    return new LRESULT(i);
  }

  LRESULT wmPrint(int paramInt1, int paramInt2, int paramInt3)
  {
    if (((paramInt3 & 0x2) != 0) && (OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()))
    {
      int i = OS.GetWindowLong(paramInt1, -20);
      if ((i & 0x200) != 0)
      {
        int j = callWindowProc(paramInt1, 791, paramInt2, paramInt3);
        RECT localRECT = new RECT();
        OS.GetWindowRect(paramInt1, localRECT);
        localRECT.right -= localRECT.left;
        localRECT.bottom -= localRECT.top;
        localRECT.left = (localRECT.top = 0);
        int k = OS.GetSystemMetrics(45);
        OS.ExcludeClipRect(paramInt2, k, k, localRECT.right - k, localRECT.bottom - k);
        OS.DrawThemeBackground(this.display.hEditTheme(), paramInt2, 1, 1, localRECT, null);
        return new LRESULT(j);
      }
    }
    return null;
  }

  LRESULT wmRButtonDblClk(int paramInt1, int paramInt2, int paramInt3)
  {
    LRESULT localLRESULT = null;
    Display localDisplay = this.display;
    localDisplay.captureChanged = false;
    sendMouseEvent(3, 3, paramInt1, 516, paramInt2, paramInt3);
    if (sendMouseEvent(8, 3, paramInt1, 518, paramInt2, paramInt3))
      localLRESULT = new LRESULT(callWindowProc(paramInt1, 518, paramInt2, paramInt3));
    else
      localLRESULT = LRESULT.ZERO;
    if ((!localDisplay.captureChanged) && (!isDisposed()) && (OS.GetCapture() != paramInt1))
      OS.SetCapture(paramInt1);
    return localLRESULT;
  }

  LRESULT wmRButtonDown(int paramInt1, int paramInt2, int paramInt3)
  {
    LRESULT localLRESULT = null;
    Display localDisplay = this.display;
    localDisplay.captureChanged = false;
    if (sendMouseEvent(3, 3, paramInt1, 516, paramInt2, paramInt3))
      localLRESULT = new LRESULT(callWindowProc(paramInt1, 516, paramInt2, paramInt3));
    else
      localLRESULT = LRESULT.ZERO;
    if ((!localDisplay.captureChanged) && (!isDisposed()) && (OS.GetCapture() != paramInt1))
      OS.SetCapture(paramInt1);
    return localLRESULT;
  }

  LRESULT wmRButtonUp(int paramInt1, int paramInt2, int paramInt3)
  {
    Display localDisplay = this.display;
    LRESULT localLRESULT = null;
    if (sendMouseEvent(4, 3, paramInt1, 517, paramInt2, paramInt3))
    {
      localLRESULT = new LRESULT(callWindowProc(paramInt1, 517, paramInt2, paramInt3));
    }
    else
    {
      OS.DefWindowProc(paramInt1, 517, paramInt2, paramInt3);
      localLRESULT = LRESULT.ZERO;
    }
    int i = 19;
    if (localDisplay.xMouse)
      i |= 96;
    if (((paramInt2 & i) == 0) && (OS.GetCapture() == paramInt1))
      OS.ReleaseCapture();
    return localLRESULT;
  }

  LRESULT wmSetFocus(int paramInt1, int paramInt2, int paramInt3)
  {
    int i = callWindowProc(paramInt1, 7, paramInt2, paramInt3);
    sendFocusEvent(15);
    if (isDisposed())
      return LRESULT.ZERO;
    if (i == 0)
      return LRESULT.ZERO;
    return new LRESULT(i);
  }

  LRESULT wmSysChar(int paramInt1, int paramInt2, int paramInt3)
  {
    Display localDisplay = this.display;
    localDisplay.lastAscii = paramInt2;
    localDisplay.lastNull = (paramInt2 == 0);
    if ((!hooks(1)) && (!localDisplay.filters(1)))
      return null;
    boolean bool1 = localDisplay.mnemonicKeyHit;
    localDisplay.mnemonicKeyHit = true;
    int i = callWindowProc(paramInt1, 262, paramInt2, paramInt3);
    boolean bool2 = false;
    if (!localDisplay.mnemonicKeyHit)
      bool2 = !sendKeyEvent(1, 262, paramInt2, paramInt3);
    bool2 |= localDisplay.mnemonicKeyHit;
    localDisplay.mnemonicKeyHit = bool1;
    return bool2 ? LRESULT.ONE : new LRESULT(i);
  }

  LRESULT wmSysKeyDown(int paramInt1, int paramInt2, int paramInt3)
  {
    if ((paramInt2 != 121) && ((paramInt3 & 0x20000000) == 0))
      return null;
    switch (paramInt2)
    {
    case 115:
      for (i = paramInt1; OS.GetParent(i) != 0; i = OS.GetParent(i))
        if (OS.GetWindow(i, 4) != 0)
          break;
      int j = OS.GetWindowLong(i, -16);
      if ((j & 0x80000) != 0)
        return null;
      break;
    }
    switch (paramInt2)
    {
    case 16:
    case 17:
    case 18:
    case 20:
    case 144:
    case 145:
      if ((paramInt3 & 0x40000000) != 0)
        return null;
      break;
    }
    this.display.lastAscii = (this.display.lastKey = 0);
    this.display.lastVirtual = (this.display.lastNull = this.display.lastDead = 0);
    int i = 0;
    if (OS.IsWinCE)
      switch (paramInt2)
      {
      case 8:
        i = 8;
        break;
      case 13:
        i = 13;
        break;
      case 46:
        i = 127;
        break;
      case 27:
        i = 27;
        break;
      case 9:
        i = 9;
      default:
        break;
      }
    else
      i = OS.MapVirtualKey(paramInt2, 2);
    this.display.lastVirtual = ((i == 0) || (this.display.numpadKey(paramInt2) != 0));
    if (this.display.lastVirtual)
    {
      this.display.lastKey = paramInt2;
      if (this.display.lastKey == 46)
        this.display.lastAscii = 127;
      if ((96 <= this.display.lastKey) && (this.display.lastKey <= 111))
      {
        switch (this.display.lastKey)
        {
        case 106:
        case 107:
        case 109:
        case 110:
        case 111:
          return null;
        case 108:
        }
        this.display.lastAscii = this.display.numpadKey(this.display.lastKey);
      }
    }
    else
    {
      this.display.lastKey = OS.CharLower((short)i);
      if (OS.IsWinNT)
        return null;
      if (paramInt2 != 13)
        return null;
      this.display.lastAscii = 13;
    }
    if (!sendKeyEvent(1, 260, paramInt2, paramInt3))
      return LRESULT.ONE;
    return null;
  }

  LRESULT wmSysKeyUp(int paramInt1, int paramInt2, int paramInt3)
  {
    return wmKeyUp(paramInt1, paramInt2, paramInt3);
  }

  LRESULT wmXButtonDblClk(int paramInt1, int paramInt2, int paramInt3)
  {
    LRESULT localLRESULT = null;
    Display localDisplay = this.display;
    localDisplay.captureChanged = false;
    int i = OS.HIWORD(paramInt2) == 1 ? 4 : 5;
    sendMouseEvent(3, i, paramInt1, 523, paramInt2, paramInt3);
    if (sendMouseEvent(8, i, paramInt1, 525, paramInt2, paramInt3))
      localLRESULT = new LRESULT(callWindowProc(paramInt1, 525, paramInt2, paramInt3));
    else
      localLRESULT = LRESULT.ZERO;
    if ((!localDisplay.captureChanged) && (!isDisposed()) && (OS.GetCapture() != paramInt1))
      OS.SetCapture(paramInt1);
    return localLRESULT;
  }

  LRESULT wmXButtonDown(int paramInt1, int paramInt2, int paramInt3)
  {
    LRESULT localLRESULT = null;
    Display localDisplay = this.display;
    localDisplay.captureChanged = false;
    localDisplay.xMouse = true;
    int i = OS.HIWORD(paramInt2) == 1 ? 4 : 5;
    if (sendMouseEvent(3, i, paramInt1, 523, paramInt2, paramInt3))
      localLRESULT = new LRESULT(callWindowProc(paramInt1, 523, paramInt2, paramInt3));
    else
      localLRESULT = LRESULT.ZERO;
    if ((!localDisplay.captureChanged) && (!isDisposed()) && (OS.GetCapture() != paramInt1))
      OS.SetCapture(paramInt1);
    return localLRESULT;
  }

  LRESULT wmXButtonUp(int paramInt1, int paramInt2, int paramInt3)
  {
    Display localDisplay = this.display;
    LRESULT localLRESULT = null;
    int i = OS.HIWORD(paramInt2) == 1 ? 4 : 5;
    if (sendMouseEvent(4, i, paramInt1, 524, paramInt2, paramInt3))
      localLRESULT = new LRESULT(callWindowProc(paramInt1, 524, paramInt2, paramInt3));
    else
      localLRESULT = LRESULT.ZERO;
    int j = 19;
    if (localDisplay.xMouse)
      j |= 96;
    if (((paramInt2 & j) == 0) && (OS.GetCapture() == paramInt1))
      OS.ReleaseCapture();
    return localLRESULT;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.Widget
 * JD-Core Version:    0.6.2
 */