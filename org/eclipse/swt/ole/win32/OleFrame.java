package org.eclipse.swt.ole.win32;

import java.util.Vector;
import org.eclipse.swt.SWT;
import org.eclipse.swt.internal.Callback;
import org.eclipse.swt.internal.LONG;
import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.COMObject;
import org.eclipse.swt.internal.ole.win32.GUID;
import org.eclipse.swt.internal.ole.win32.IOleInPlaceActiveObject;
import org.eclipse.swt.internal.win32.GUITHREADINFO;
import org.eclipse.swt.internal.win32.MENUITEMINFO;
import org.eclipse.swt.internal.win32.MSG;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;

public final class OleFrame extends Composite
{
  private COMObject iUnknown;
  private COMObject iOleInPlaceFrame;
  private IOleInPlaceActiveObject objIOleInPlaceActiveObject;
  private OleClientSite currentdoc;
  private int refCount = 0;
  private MenuItem[] fileMenuItems;
  private MenuItem[] containerMenuItems;
  private MenuItem[] windowMenuItems;
  private Listener listener;
  private static String CHECK_FOCUS = "OLE_CHECK_FOCUS";
  private static String HHOOK = "OLE_HHOOK";
  private static String HHOOKMSG = "OLE_HHOOK_MSG";
  private static boolean ignoreNextKey;
  private static final short[] ACCENTS = { 126, 96, 39, 94, 34 };
  private static final String CONSUME_KEY = "org.eclipse.swt.OleFrame.ConsumeKey";
  private static final String ACCEL_KEY_HIT = "org.eclipse.swt.internal.win32.accelKeyHit";

  public OleFrame(Composite paramComposite, int paramInt)
  {
    super(paramComposite, paramInt);
    createCOMInterfaces();
    this.listener = new Listener()
    {
      public void handleEvent(Event paramAnonymousEvent)
      {
        switch (paramAnonymousEvent.type)
        {
        case 26:
          OleFrame.this.onActivate(paramAnonymousEvent);
          break;
        case 27:
          OleFrame.this.onDeactivate(paramAnonymousEvent);
          break;
        case 12:
          OleFrame.this.onDispose(paramAnonymousEvent);
          break;
        case 10:
        case 11:
          OleFrame.this.onResize(paramAnonymousEvent);
          break;
        default:
          OLE.error(20);
        }
      }
    };
    addListener(26, this.listener);
    addListener(27, this.listener);
    addListener(12, this.listener);
    addListener(11, this.listener);
    addListener(10, this.listener);
    AddRef();
    Display localDisplay = getDisplay();
    initCheckFocus(localDisplay);
    initMsgHook(localDisplay);
  }

  private static void initCheckFocus(Display paramDisplay)
  {
    if (paramDisplay.getData(CHECK_FOCUS) != null)
      return;
    paramDisplay.setData(CHECK_FOCUS, CHECK_FOCUS);
    Runnable[] arrayOfRunnable = new Runnable[1];
    Control[] arrayOfControl = new Control[1];
    arrayOfRunnable[0] = new Runnable()
    {
      private final Display val$display;
      private final Runnable[] val$timer;

      public void run()
      {
        if (((OleFrame.this[0] instanceof OleClientSite)) && (!OleFrame.this[0].isDisposed()))
          for (int i = OS.GetFocus(); i != 0; i = OS.GetParent(i))
          {
            int j = OS.GetWindow(i, 4);
            if (j != 0)
            {
              this.val$display.timerExec(50, this.val$timer[0]);
              return;
            }
          }
        if ((OleFrame.this[0] == null) || (OleFrame.this[0].isDisposed()) || (!OleFrame.this[0].isFocusControl()))
        {
          Object localObject1 = this.val$display.getFocusControl();
          Object localObject2;
          if ((localObject1 instanceof OleFrame))
          {
            localObject2 = (OleFrame)localObject1;
            localObject1 = ((OleFrame)localObject2).getCurrentDocument();
          }
          if (OleFrame.this[0] != localObject1)
          {
            localObject2 = new Event();
            if (((OleFrame.this[0] instanceof OleClientSite)) && (!OleFrame.this[0].isDisposed()))
              OleFrame.this[0].notifyListeners(16, (Event)localObject2);
            if (((localObject1 instanceof OleClientSite)) && (!((Control)localObject1).isDisposed()))
              ((Control)localObject1).notifyListeners(15, (Event)localObject2);
          }
          OleFrame.this[0] = localObject1;
        }
        this.val$display.timerExec(50, this.val$timer[0]);
      }
    };
    paramDisplay.timerExec(50, arrayOfRunnable[0]);
  }

  private static void initMsgHook(Display paramDisplay)
  {
    if (paramDisplay.getData(HHOOK) != null)
      return;
    Callback localCallback = new Callback(OleFrame.class, "getMsgProc", 3);
    int i = localCallback.getAddress();
    if (i == 0)
      SWT.error(3);
    int j = OS.GetCurrentThreadId();
    int k = OS.SetWindowsHookEx(3, i, 0, j);
    if (k == 0)
    {
      localCallback.dispose();
      return;
    }
    paramDisplay.setData(HHOOK, new LONG(k));
    paramDisplay.setData(HHOOKMSG, new MSG());
    paramDisplay.disposeExec(new Runnable()
    {
      private final int val$hHook;
      private final Callback val$callback;

      public void run()
      {
        if (this.val$hHook != 0)
          OS.UnhookWindowsHookEx(this.val$hHook);
        this.val$callback.dispose();
      }
    });
  }

  static int getMsgProc(int paramInt1, int paramInt2, int paramInt3)
  {
    Display localDisplay = Display.getCurrent();
    if (localDisplay == null)
      return 0;
    LONG localLONG = (LONG)localDisplay.getData(HHOOK);
    if (localLONG == null)
      return 0;
    if ((paramInt1 < 0) || ((paramInt2 & 0x1) == 0))
      return OS.CallNextHookEx(localLONG.value, paramInt1, paramInt2, paramInt3);
    MSG localMSG = (MSG)localDisplay.getData(HHOOKMSG);
    OS.MoveMemory(localMSG, paramInt3, MSG.sizeof);
    int i = localMSG.message;
    if ((256 <= i) && (i <= 264))
    {
      Widget localWidget = null;
      for (int j = localMSG.hwnd; j != 0; j = OS.GetParent(j))
      {
        localWidget = localDisplay.findWidget(j);
        if (localWidget != null)
          break;
      }
      if ((localWidget != null) && ((localWidget instanceof OleClientSite)))
      {
        OleClientSite localOleClientSite = (OleClientSite)localWidget;
        if (localOleClientSite.handle == j)
        {
          boolean bool1 = false;
          int k = OS.GetWindowThreadProcessId(localMSG.hwnd, null);
          GUITHREADINFO localGUITHREADINFO = new GUITHREADINFO();
          localGUITHREADINFO.cbSize = GUITHREADINFO.sizeof;
          boolean bool2 = OS.GetGUIThreadInfo(k, localGUITHREADINFO);
          int m = 30;
          if ((!bool2) || ((localGUITHREADINFO.flags & m) == 0))
          {
            OleFrame localOleFrame = localOleClientSite.frame;
            localOleFrame.setData("org.eclipse.swt.OleFrame.ConsumeKey", null);
            localDisplay.setData("org.eclipse.swt.internal.win32.accelKeyHit", Boolean.TRUE);
            bool1 = localOleFrame.translateOleAccelerator(localMSG);
            localDisplay.setData("org.eclipse.swt.internal.win32.accelKeyHit", Boolean.FALSE);
            String str = (String)localOleFrame.getData("org.eclipse.swt.OleFrame.ConsumeKey");
            if (str != null)
              bool1 = str.equals("true");
            localOleFrame.setData("org.eclipse.swt.OleFrame.ConsumeKey", null);
          }
          boolean bool3 = false;
          int n;
          switch (localMSG.message)
          {
          case 256:
          case 260:
            if (!OS.IsWinCE)
              switch (localMSG.wParam)
              {
              case 16:
              case 17:
              case 18:
              case 20:
              case 144:
              case 145:
                break;
              default:
                n = OS.MapVirtualKey(localMSG.wParam, 2);
                if (n != 0)
                {
                  bool3 = (n & (OS.IsWinNT ? -2147483648 : 32768)) != 0;
                  if (!bool3)
                    for (int i1 = 0; i1 < ACCENTS.length; i1++)
                    {
                      int i2 = OS.VkKeyScan(ACCENTS[i1]);
                      if ((i2 != -1) && ((i2 & 0xFF) == localMSG.wParam))
                      {
                        int i3 = i2 >> 8;
                        if ((OS.GetKeyState(16) < 0 ? 1 : 0) == ((i3 & 0x1) != 0 ? 1 : 0))
                          if ((OS.GetKeyState(17) < 0 ? 1 : 0) == ((i3 & 0x2) != 0 ? 1 : 0))
                            if ((OS.GetKeyState(18) < 0 ? 1 : 0) == ((i3 & 0x4) != 0 ? 1 : 0))
                            {
                              if ((i3 & 0x7) == 0)
                                break;
                              bool3 = true;
                              break;
                            }
                      }
                    }
                }
                break;
              }
            break;
          case 257:
          case 258:
          case 259:
          }
          if ((!bool1) && (!bool3) && (!ignoreNextKey))
          {
            n = localMSG.hwnd;
            localMSG.hwnd = localOleClientSite.handle;
            bool1 = OS.DispatchMessage(localMSG) == 1;
            localMSG.hwnd = n;
          }
          switch (localMSG.message)
          {
          case 256:
          case 260:
            switch (localMSG.wParam)
            {
            case 16:
            case 17:
            case 18:
            case 20:
            case 144:
            case 145:
              break;
            default:
              ignoreNextKey = bool3;
            }
            break;
          case 257:
          case 258:
          case 259:
          }
          if (bool1)
          {
            localMSG.message = 0;
            localMSG.wParam = (localMSG.lParam = 0);
            OS.MoveMemory(paramInt3, localMSG, MSG.sizeof);
            return 0;
          }
        }
      }
    }
    return OS.CallNextHookEx(localLONG.value, paramInt1, paramInt2, paramInt3);
  }

  int AddRef()
  {
    this.refCount += 1;
    return this.refCount;
  }

  private int ContextSensitiveHelp(int paramInt)
  {
    return 0;
  }

  private void createCOMInterfaces()
  {
    this.iUnknown = new COMObject(new int[] { 2 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return OleFrame.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return OleFrame.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return OleFrame.this.Release();
      }
    };
    this.iOleInPlaceFrame = new COMObject(new int[] { 2, 0, 0, 1, 1, 1, 1, 1, 2, 2, 3, 1, 1, 1, 2 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return OleFrame.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return OleFrame.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return OleFrame.this.Release();
      }

      public int method3(int[] paramAnonymousArrayOfInt)
      {
        return OleFrame.this.GetWindow(paramAnonymousArrayOfInt[0]);
      }

      public int method4(int[] paramAnonymousArrayOfInt)
      {
        return OleFrame.this.ContextSensitiveHelp(paramAnonymousArrayOfInt[0]);
      }

      public int method5(int[] paramAnonymousArrayOfInt)
      {
        return OleFrame.this.GetBorder(paramAnonymousArrayOfInt[0]);
      }

      public int method6(int[] paramAnonymousArrayOfInt)
      {
        return OleFrame.this.RequestBorderSpace(paramAnonymousArrayOfInt[0]);
      }

      public int method7(int[] paramAnonymousArrayOfInt)
      {
        return OleFrame.this.SetBorderSpace(paramAnonymousArrayOfInt[0]);
      }

      public int method8(int[] paramAnonymousArrayOfInt)
      {
        return OleFrame.this.SetActiveObject(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method9(int[] paramAnonymousArrayOfInt)
      {
        return OleFrame.this.InsertMenus(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method10(int[] paramAnonymousArrayOfInt)
      {
        return OleFrame.this.SetMenu(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }

      public int method11(int[] paramAnonymousArrayOfInt)
      {
        return OleFrame.this.RemoveMenus(paramAnonymousArrayOfInt[0]);
      }

      public int method14(int[] paramAnonymousArrayOfInt)
      {
        return OleFrame.this.TranslateAccelerator(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }
    };
  }

  private void disposeCOMInterfaces()
  {
    if (this.iUnknown != null)
      this.iUnknown.dispose();
    this.iUnknown = null;
    if (this.iOleInPlaceFrame != null)
      this.iOleInPlaceFrame.dispose();
    this.iOleInPlaceFrame = null;
  }

  private int GetBorder(int paramInt)
  {
    if (paramInt == 0)
      return -2147024809;
    RECT localRECT = new RECT();
    OS.GetClientRect(this.handle, localRECT);
    OS.MoveMemory(paramInt, localRECT, RECT.sizeof);
    return 0;
  }

  public MenuItem[] getContainerMenus()
  {
    return this.containerMenuItems;
  }

  public MenuItem[] getFileMenus()
  {
    return this.fileMenuItems;
  }

  int getIOleInPlaceFrame()
  {
    return this.iOleInPlaceFrame.getAddress();
  }

  private int getMenuItemID(int paramInt1, int paramInt2)
  {
    int i = 0;
    MENUITEMINFO localMENUITEMINFO = new MENUITEMINFO();
    localMENUITEMINFO.cbSize = MENUITEMINFO.sizeof;
    localMENUITEMINFO.fMask = 7;
    OS.GetMenuItemInfo(paramInt1, paramInt2, true, localMENUITEMINFO);
    if ((localMENUITEMINFO.fState & 0x10) == 16)
      i = localMENUITEMINFO.hSubMenu;
    else
      i = localMENUITEMINFO.wID;
    return i;
  }

  private int GetWindow(int paramInt)
  {
    if (paramInt != 0)
      COM.MoveMemory(paramInt, new int[] { this.handle }, OS.PTR_SIZEOF);
    return 0;
  }

  public MenuItem[] getWindowMenus()
  {
    return this.windowMenuItems;
  }

  private int InsertMenus(int paramInt1, int paramInt2)
  {
    Menu localMenu = getShell().getMenuBar();
    if ((localMenu == null) || (localMenu.isDisposed()))
    {
      COM.MoveMemory(paramInt2, new int[1], 4);
      return 0;
    }
    int i = localMenu.handle;
    MENUITEMINFO localMENUITEMINFO = new MENUITEMINFO();
    int j = OS.GetProcessHeap();
    int k = 128;
    int m = k * TCHAR.sizeof;
    int n = OS.HeapAlloc(j, 8, m);
    localMENUITEMINFO.cbSize = MENUITEMINFO.sizeof;
    localMENUITEMINFO.fMask = 55;
    localMENUITEMINFO.dwTypeData = n;
    localMENUITEMINFO.cch = k;
    int i1 = 0;
    int i2 = 0;
    if (this.fileMenuItems != null)
      for (i3 = 0; i3 < this.fileMenuItems.length; i3++)
      {
        MenuItem localMenuItem1 = this.fileMenuItems[i3];
        if (localMenuItem1 != null)
        {
          int i5 = localMenuItem1.getParent().indexOf(localMenuItem1);
          localMENUITEMINFO.cch = k;
          if ((OS.GetMenuItemInfo(i, i5, true, localMENUITEMINFO)) && (OS.InsertMenuItem(paramInt1, i2, true, localMENUITEMINFO)))
          {
            i1++;
            i2++;
          }
        }
      }
    COM.MoveMemory(paramInt2, new int[] { i1 }, 4);
    int i3 = 0;
    if (this.containerMenuItems != null)
      for (i4 = 0; i4 < this.containerMenuItems.length; i4++)
      {
        MenuItem localMenuItem2 = this.containerMenuItems[i4];
        if (localMenuItem2 != null)
        {
          int i7 = localMenuItem2.getParent().indexOf(localMenuItem2);
          localMENUITEMINFO.cch = k;
          if ((OS.GetMenuItemInfo(i, i7, true, localMENUITEMINFO)) && (OS.InsertMenuItem(paramInt1, i2, true, localMENUITEMINFO)))
          {
            i3++;
            i2++;
          }
        }
      }
    COM.MoveMemory(paramInt2 + 8, new int[] { i3 }, 4);
    int i4 = 0;
    if (this.windowMenuItems != null)
      for (int i6 = 0; i6 < this.windowMenuItems.length; i6++)
      {
        MenuItem localMenuItem3 = this.windowMenuItems[i6];
        if (localMenuItem3 != null)
        {
          int i8 = localMenuItem3.getParent().indexOf(localMenuItem3);
          localMENUITEMINFO.cch = k;
          if ((OS.GetMenuItemInfo(i, i8, true, localMENUITEMINFO)) && (OS.InsertMenuItem(paramInt1, i2, true, localMENUITEMINFO)))
          {
            i4++;
            i2++;
          }
        }
      }
    COM.MoveMemory(paramInt2 + 16, new int[] { i4 }, 4);
    if (n != 0)
      OS.HeapFree(j, 0, n);
    return 0;
  }

  void onActivate(Event paramEvent)
  {
    if (this.objIOleInPlaceActiveObject != null)
      this.objIOleInPlaceActiveObject.OnFrameWindowActivate(true);
  }

  void onDeactivate(Event paramEvent)
  {
    if (this.objIOleInPlaceActiveObject != null)
      this.objIOleInPlaceActiveObject.OnFrameWindowActivate(false);
  }

  private void onDispose(Event paramEvent)
  {
    releaseObjectInterfaces();
    this.currentdoc = null;
    Release();
    removeListener(26, this.listener);
    removeListener(27, this.listener);
    removeListener(12, this.listener);
    removeListener(11, this.listener);
    removeListener(10, this.listener);
  }

  private void onResize(Event paramEvent)
  {
    if (this.objIOleInPlaceActiveObject != null)
    {
      RECT localRECT = new RECT();
      OS.GetClientRect(this.handle, localRECT);
      this.objIOleInPlaceActiveObject.ResizeBorder(localRECT, this.iOleInPlaceFrame.getAddress(), true);
    }
  }

  private int QueryInterface(int paramInt1, int paramInt2)
  {
    if ((paramInt1 == 0) || (paramInt2 == 0))
      return -2147024809;
    GUID localGUID = new GUID();
    COM.MoveMemory(localGUID, paramInt1, GUID.sizeof);
    if ((COM.IsEqualGUID(localGUID, COM.IIDIUnknown)) || (COM.IsEqualGUID(localGUID, COM.IIDIOleInPlaceFrame)))
    {
      COM.MoveMemory(paramInt2, new int[] { this.iOleInPlaceFrame.getAddress() }, OS.PTR_SIZEOF);
      AddRef();
      return 0;
    }
    COM.MoveMemory(paramInt2, new int[1], OS.PTR_SIZEOF);
    return -2147467262;
  }

  int Release()
  {
    this.refCount -= 1;
    if (this.refCount == 0)
    {
      disposeCOMInterfaces();
      if (COM.FreeUnusedLibraries)
        COM.CoFreeUnusedLibraries();
    }
    return this.refCount;
  }

  private void releaseObjectInterfaces()
  {
    if (this.objIOleInPlaceActiveObject != null)
      this.objIOleInPlaceActiveObject.Release();
    this.objIOleInPlaceActiveObject = null;
  }

  private int RemoveMenus(int paramInt)
  {
    Menu localMenu = getShell().getMenuBar();
    if ((localMenu == null) || (localMenu.isDisposed()))
      return 1;
    int i = localMenu.handle;
    Vector localVector = new Vector();
    int j;
    int k;
    if (this.fileMenuItems != null)
      for (localMenuItem1 = 0; localMenuItem1 < this.fileMenuItems.length; localMenuItem1++)
      {
        localMenuItem2 = this.fileMenuItems[localMenuItem1];
        if ((localMenuItem2 != null) && (!localMenuItem2.isDisposed()))
        {
          j = localMenuItem2.getParent().indexOf(localMenuItem2);
          k = getMenuItemID(i, j);
          localVector.addElement(new LONG(k));
        }
      }
    if (this.containerMenuItems != null)
      for (localMenuItem1 = 0; localMenuItem1 < this.containerMenuItems.length; localMenuItem1++)
      {
        localMenuItem2 = this.containerMenuItems[localMenuItem1];
        if ((localMenuItem2 != null) && (!localMenuItem2.isDisposed()))
        {
          j = localMenuItem2.getParent().indexOf(localMenuItem2);
          k = getMenuItemID(i, j);
          localVector.addElement(new LONG(k));
        }
      }
    if (this.windowMenuItems != null)
      for (localMenuItem1 = 0; localMenuItem1 < this.windowMenuItems.length; localMenuItem1++)
      {
        localMenuItem2 = this.windowMenuItems[localMenuItem1];
        if ((localMenuItem2 != null) && (!localMenuItem2.isDisposed()))
        {
          j = localMenuItem2.getParent().indexOf(localMenuItem2);
          k = getMenuItemID(i, j);
          localVector.addElement(new LONG(k));
        }
      }
    MenuItem localMenuItem1 = OS.GetMenuItemCount(paramInt) - 1;
    for (MenuItem localMenuItem2 = localMenuItem1; localMenuItem2 >= 0; localMenuItem2--)
    {
      j = getMenuItemID(paramInt, localMenuItem2);
      if (localVector.contains(new LONG(j)))
        OS.RemoveMenu(paramInt, localMenuItem2, 1024);
    }
    return 0;
  }

  private int RequestBorderSpace(int paramInt)
  {
    return 0;
  }

  int SetActiveObject(int paramInt1, int paramInt2)
  {
    if (this.objIOleInPlaceActiveObject != null)
    {
      this.objIOleInPlaceActiveObject.Release();
      this.objIOleInPlaceActiveObject = null;
    }
    if (paramInt1 != 0)
    {
      this.objIOleInPlaceActiveObject = new IOleInPlaceActiveObject(paramInt1);
      this.objIOleInPlaceActiveObject.AddRef();
    }
    return 0;
  }

  private int SetBorderSpace(int paramInt)
  {
    if (this.objIOleInPlaceActiveObject == null)
      return 0;
    RECT localRECT = new RECT();
    if ((paramInt == 0) || (this.currentdoc == null))
      return 0;
    COM.MoveMemory(localRECT, paramInt, RECT.sizeof);
    this.currentdoc.setBorderSpace(localRECT);
    return 0;
  }

  public void setContainerMenus(MenuItem[] paramArrayOfMenuItem)
  {
    this.containerMenuItems = paramArrayOfMenuItem;
  }

  OleClientSite getCurrentDocument()
  {
    return this.currentdoc;
  }

  void setCurrentDocument(OleClientSite paramOleClientSite)
  {
    this.currentdoc = paramOleClientSite;
    if ((this.currentdoc != null) && (this.objIOleInPlaceActiveObject != null))
    {
      RECT localRECT = new RECT();
      OS.GetClientRect(this.handle, localRECT);
      this.objIOleInPlaceActiveObject.ResizeBorder(localRECT, this.iOleInPlaceFrame.getAddress(), true);
    }
  }

  public void setFileMenus(MenuItem[] paramArrayOfMenuItem)
  {
    this.fileMenuItems = paramArrayOfMenuItem;
  }

  private int SetMenu(int paramInt1, int paramInt2, int paramInt3)
  {
    int i = 0;
    if (this.objIOleInPlaceActiveObject != null)
      i = this.objIOleInPlaceActiveObject.getAddress();
    Menu localMenu = getShell().getMenuBar();
    if ((localMenu == null) || (localMenu.isDisposed()))
      return COM.OleSetMenuDescriptor(0, getShell().handle, paramInt3, this.iOleInPlaceFrame.getAddress(), i);
    int j = localMenu.getShell().handle;
    if ((paramInt1 == 0) && (paramInt2 == 0))
      paramInt1 = localMenu.handle;
    if (paramInt1 == 0)
      return -2147467259;
    OS.SetMenu(j, paramInt1);
    OS.DrawMenuBar(j);
    return COM.OleSetMenuDescriptor(paramInt2, j, paramInt3, this.iOleInPlaceFrame.getAddress(), i);
  }

  public void setWindowMenus(MenuItem[] paramArrayOfMenuItem)
  {
    this.windowMenuItems = paramArrayOfMenuItem;
  }

  private boolean translateOleAccelerator(MSG paramMSG)
  {
    if (this.objIOleInPlaceActiveObject == null)
      return false;
    int i = this.objIOleInPlaceActiveObject.TranslateAccelerator(paramMSG);
    return (i != 1) && (i != -2147467263);
  }

  private int TranslateAccelerator(int paramInt1, int paramInt2)
  {
    Menu localMenu = getShell().getMenuBar();
    if ((localMenu == null) || (localMenu.isDisposed()) || (!localMenu.isEnabled()))
      return 1;
    if (paramInt2 < 0)
      return 1;
    Shell localShell = localMenu.getShell();
    int i = localShell.handle;
    int j = OS.SendMessage(i, 32769, 0, 0);
    if (j == 0)
      return 1;
    MSG localMSG = new MSG();
    OS.MoveMemory(localMSG, paramInt1, MSG.sizeof);
    int k = OS.TranslateAccelerator(i, j, localMSG);
    return k == 0 ? 1 : 0;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.ole.win32.OleFrame
 * JD-Core Version:    0.6.2
 */