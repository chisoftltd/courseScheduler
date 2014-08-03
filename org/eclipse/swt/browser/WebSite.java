package org.eclipse.swt.browser;

import java.util.Hashtable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.internal.C;
import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.COMObject;
import org.eclipse.swt.internal.ole.win32.DISPPARAMS;
import org.eclipse.swt.internal.ole.win32.GUID;
import org.eclipse.swt.internal.ole.win32.IDispatch;
import org.eclipse.swt.internal.ole.win32.IDispatchEx;
import org.eclipse.swt.internal.ole.win32.TYPEATTR;
import org.eclipse.swt.internal.ole.win32.VARIANT;
import org.eclipse.swt.internal.win32.DOCHOSTUIINFO;
import org.eclipse.swt.internal.win32.MSG;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.ole.win32.OleAutomation;
import org.eclipse.swt.ole.win32.OleControlSite;
import org.eclipse.swt.ole.win32.OleFrame;
import org.eclipse.swt.ole.win32.Variant;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;

class WebSite extends OleControlSite
{
  COMObject iDocHostUIHandler;
  COMObject iDocHostShowUI;
  COMObject iServiceProvider;
  COMObject iInternetSecurityManager;
  COMObject iOleCommandTarget;
  COMObject iAuthenticate;
  COMObject iDispatch;
  boolean ignoreNextMessage;
  boolean ignoreAllMessages;
  Boolean canExecuteApplets;
  static final int OLECMDID_SHOWSCRIPTERROR = 40;
  static final short[] ACCENTS = { 126, 96, 39, 94, 34 };
  static final String CONSUME_KEY = "org.eclipse.swt.OleFrame.ConsumeKey";

  public WebSite(Composite paramComposite, int paramInt, String paramString)
  {
    super(paramComposite, paramInt, paramString);
  }

  protected void createCOMInterfaces()
  {
    super.createCOMInterfaces();
    this.iDocHostUIHandler = new COMObject(new int[] { 2, 0, 0, 4, 1, 5, 0, 0, 1, 1, 1, 3, 3, 2, 2, 1, 3, 2 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return WebSite.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return WebSite.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return WebSite.this.Release();
      }

      public int method3(int[] paramAnonymousArrayOfInt)
      {
        return WebSite.this.ShowContextMenu(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3]);
      }

      public int method4(int[] paramAnonymousArrayOfInt)
      {
        return WebSite.this.GetHostInfo(paramAnonymousArrayOfInt[0]);
      }

      public int method5(int[] paramAnonymousArrayOfInt)
      {
        return WebSite.this.ShowUI(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4]);
      }

      public int method6(int[] paramAnonymousArrayOfInt)
      {
        return WebSite.this.HideUI();
      }

      public int method7(int[] paramAnonymousArrayOfInt)
      {
        return WebSite.this.UpdateUI();
      }

      public int method8(int[] paramAnonymousArrayOfInt)
      {
        return WebSite.this.EnableModeless(paramAnonymousArrayOfInt[0]);
      }

      public int method9(int[] paramAnonymousArrayOfInt)
      {
        return WebSite.this.OnDocWindowActivate(paramAnonymousArrayOfInt[0]);
      }

      public int method10(int[] paramAnonymousArrayOfInt)
      {
        return WebSite.this.OnFrameWindowActivate(paramAnonymousArrayOfInt[0]);
      }

      public int method11(int[] paramAnonymousArrayOfInt)
      {
        return WebSite.this.ResizeBorder(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }

      public int method12(int[] paramAnonymousArrayOfInt)
      {
        return WebSite.this.TranslateAccelerator(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }

      public int method13(int[] paramAnonymousArrayOfInt)
      {
        return WebSite.this.GetOptionKeyPath(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method14(int[] paramAnonymousArrayOfInt)
      {
        return WebSite.this.GetDropTarget(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method15(int[] paramAnonymousArrayOfInt)
      {
        return WebSite.this.GetExternal(paramAnonymousArrayOfInt[0]);
      }

      public int method16(int[] paramAnonymousArrayOfInt)
      {
        return WebSite.this.TranslateUrl(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }

      public int method17(int[] paramAnonymousArrayOfInt)
      {
        return WebSite.this.FilterDataObject(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }
    };
    this.iDocHostShowUI = new COMObject(new int[] { 2, 0, 0, 7, C.PTR_SIZEOF == 4 ? 7 : 6 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return WebSite.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return WebSite.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return WebSite.this.Release();
      }

      public int method3(int[] paramAnonymousArrayOfInt)
      {
        return WebSite.this.ShowMessage(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4], paramAnonymousArrayOfInt[5], paramAnonymousArrayOfInt[6]);
      }

      public int method4(int[] paramAnonymousArrayOfInt)
      {
        if (paramAnonymousArrayOfInt.length == 7)
          return WebSite.this.ShowHelp(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4], paramAnonymousArrayOfInt[5], paramAnonymousArrayOfInt[6]);
        return WebSite.this.ShowHelp_64(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4], paramAnonymousArrayOfInt[5]);
      }
    };
    this.iServiceProvider = new COMObject(new int[] { 2, 0, 0, 3 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return WebSite.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return WebSite.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return WebSite.this.Release();
      }

      public int method3(int[] paramAnonymousArrayOfInt)
      {
        return WebSite.this.QueryService(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }
    };
    this.iInternetSecurityManager = new COMObject(new int[] { 2, 0, 0, 1, 1, 3, 4, 8, 7, 3, 3 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return WebSite.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return WebSite.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return WebSite.this.Release();
      }

      public int method3(int[] paramAnonymousArrayOfInt)
      {
        return WebSite.this.SetSecuritySite(paramAnonymousArrayOfInt[0]);
      }

      public int method4(int[] paramAnonymousArrayOfInt)
      {
        return WebSite.this.GetSecuritySite(paramAnonymousArrayOfInt[0]);
      }

      public int method5(int[] paramAnonymousArrayOfInt)
      {
        return WebSite.this.MapUrlToZone(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }

      public int method6(int[] paramAnonymousArrayOfInt)
      {
        return WebSite.this.GetSecurityId(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3]);
      }

      public int method7(int[] paramAnonymousArrayOfInt)
      {
        return WebSite.this.ProcessUrlAction(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4], paramAnonymousArrayOfInt[5], paramAnonymousArrayOfInt[6], paramAnonymousArrayOfInt[7]);
      }

      public int method8(int[] paramAnonymousArrayOfInt)
      {
        return WebSite.this.QueryCustomPolicy(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4], paramAnonymousArrayOfInt[5], paramAnonymousArrayOfInt[6]);
      }

      public int method9(int[] paramAnonymousArrayOfInt)
      {
        return WebSite.this.SetZoneMapping(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }

      public int method10(int[] paramAnonymousArrayOfInt)
      {
        return WebSite.this.GetZoneMappings(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }
    };
    this.iOleCommandTarget = new COMObject(new int[] { 2, 0, 0, 4, 5 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return WebSite.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return WebSite.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return WebSite.this.Release();
      }

      public int method3(int[] paramAnonymousArrayOfInt)
      {
        return WebSite.this.QueryStatus(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3]);
      }

      public int method4(int[] paramAnonymousArrayOfInt)
      {
        return WebSite.this.Exec(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4]);
      }
    };
    this.iAuthenticate = new COMObject(new int[] { 2, 0, 0, 3 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return WebSite.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return WebSite.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return WebSite.this.Release();
      }

      public int method3(int[] paramAnonymousArrayOfInt)
      {
        return WebSite.this.Authenticate(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }
    };
    this.iDispatch = new COMObject(new int[] { 2, 0, 0, 1, 3, 5, 8 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        GUID localGUID = new GUID();
        COM.MoveMemory(localGUID, paramAnonymousArrayOfInt[0], GUID.sizeof);
        if (COM.IsEqualGUID(localGUID, COM.IIDIDispatch))
        {
          COM.MoveMemory(paramAnonymousArrayOfInt[1], new int[] { WebSite.this.iDispatch.getAddress() }, OS.PTR_SIZEOF);
          WebSite.this.AddRef();
          return 0;
        }
        return WebSite.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return WebSite.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return WebSite.this.Release();
      }

      public int method3(int[] paramAnonymousArrayOfInt)
      {
        return WebSite.this.GetTypeInfoCount(paramAnonymousArrayOfInt[0]);
      }

      public int method4(int[] paramAnonymousArrayOfInt)
      {
        return WebSite.this.GetTypeInfo(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }

      public int method5(int[] paramAnonymousArrayOfInt)
      {
        return WebSite.this.GetIDsOfNames(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4]);
      }

      public int method6(int[] paramAnonymousArrayOfInt)
      {
        return WebSite.this.Invoke(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4], paramAnonymousArrayOfInt[5], paramAnonymousArrayOfInt[6], paramAnonymousArrayOfInt[7]);
      }
    };
  }

  protected void disposeCOMInterfaces()
  {
    super.disposeCOMInterfaces();
    if (this.iDocHostUIHandler != null)
    {
      this.iDocHostUIHandler.dispose();
      this.iDocHostUIHandler = null;
    }
    if (this.iDocHostShowUI != null)
    {
      this.iDocHostShowUI.dispose();
      this.iDocHostShowUI = null;
    }
    if (this.iServiceProvider != null)
    {
      this.iServiceProvider.dispose();
      this.iServiceProvider = null;
    }
    if (this.iInternetSecurityManager != null)
    {
      this.iInternetSecurityManager.dispose();
      this.iInternetSecurityManager = null;
    }
    if (this.iOleCommandTarget != null)
    {
      this.iOleCommandTarget.dispose();
      this.iOleCommandTarget = null;
    }
    if (this.iAuthenticate != null)
    {
      this.iAuthenticate.dispose();
      this.iAuthenticate = null;
    }
    if (this.iDispatch != null)
    {
      this.iDispatch.dispose();
      this.iDispatch = null;
    }
  }

  protected int AddRef()
  {
    return super.AddRef();
  }

  protected int QueryInterface(int paramInt1, int paramInt2)
  {
    int i = super.QueryInterface(paramInt1, paramInt2);
    if (i == 0)
      return i;
    if ((paramInt1 == 0) || (paramInt2 == 0))
      return -2147024809;
    GUID localGUID = new GUID();
    COM.MoveMemory(localGUID, paramInt1, GUID.sizeof);
    if (COM.IsEqualGUID(localGUID, COM.IIDIDocHostUIHandler))
    {
      COM.MoveMemory(paramInt2, new int[] { this.iDocHostUIHandler.getAddress() }, OS.PTR_SIZEOF);
      AddRef();
      return 0;
    }
    if (COM.IsEqualGUID(localGUID, COM.IIDIDocHostShowUI))
    {
      COM.MoveMemory(paramInt2, new int[] { this.iDocHostShowUI.getAddress() }, OS.PTR_SIZEOF);
      AddRef();
      return 0;
    }
    if (COM.IsEqualGUID(localGUID, COM.IIDIServiceProvider))
    {
      COM.MoveMemory(paramInt2, new int[] { this.iServiceProvider.getAddress() }, OS.PTR_SIZEOF);
      AddRef();
      return 0;
    }
    if (COM.IsEqualGUID(localGUID, COM.IIDIInternetSecurityManager))
    {
      COM.MoveMemory(paramInt2, new int[] { this.iInternetSecurityManager.getAddress() }, OS.PTR_SIZEOF);
      AddRef();
      return 0;
    }
    if (COM.IsEqualGUID(localGUID, COM.IIDIOleCommandTarget))
    {
      COM.MoveMemory(paramInt2, new int[] { this.iOleCommandTarget.getAddress() }, OS.PTR_SIZEOF);
      AddRef();
      return 0;
    }
    COM.MoveMemory(paramInt2, new int[1], OS.PTR_SIZEOF);
    return -2147467262;
  }

  int EnableModeless(int paramInt)
  {
    return -2147467263;
  }

  int FilterDataObject(int paramInt1, int paramInt2)
  {
    return -2147467263;
  }

  int GetDropTarget(int paramInt1, int paramInt2)
  {
    return -2147467263;
  }

  int GetExternal(int paramInt)
  {
    OS.MoveMemory(paramInt, new int[] { this.iDispatch.getAddress() }, C.PTR_SIZEOF);
    AddRef();
    return 0;
  }

  int GetHostInfo(int paramInt)
  {
    int i = 262144;
    IE localIE = (IE)((Browser)getParent().getParent()).webBrowser;
    if ((localIE.style & 0x800) == 0)
      i |= 2097152;
    DOCHOSTUIINFO localDOCHOSTUIINFO = new DOCHOSTUIINFO();
    OS.MoveMemory(localDOCHOSTUIINFO, paramInt, DOCHOSTUIINFO.sizeof);
    localDOCHOSTUIINFO.dwFlags = i;
    OS.MoveMemory(paramInt, localDOCHOSTUIINFO, DOCHOSTUIINFO.sizeof);
    return 0;
  }

  int GetOptionKeyPath(int paramInt1, int paramInt2)
  {
    return -2147467263;
  }

  int HideUI()
  {
    return -2147467263;
  }

  int OnDocWindowActivate(int paramInt)
  {
    return -2147467263;
  }

  int OnFrameWindowActivate(int paramInt)
  {
    return -2147467263;
  }

  protected int Release()
  {
    return super.Release();
  }

  int ResizeBorder(int paramInt1, int paramInt2, int paramInt3)
  {
    return -2147467263;
  }

  int ShowContextMenu(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    Browser localBrowser = (Browser)getParent().getParent();
    Event localEvent = new Event();
    POINT localPOINT = new POINT();
    OS.MoveMemory(localPOINT, paramInt2, POINT.sizeof);
    localEvent.x = localPOINT.x;
    localEvent.y = localPOINT.y;
    localBrowser.notifyListeners(35, localEvent);
    if (!localEvent.doit)
      return 0;
    Menu localMenu = localBrowser.getMenu();
    if ((localMenu != null) && (!localMenu.isDisposed()))
    {
      if ((localPOINT.x != localEvent.x) || (localPOINT.y != localEvent.y))
        localMenu.setLocation(localEvent.x, localEvent.y);
      localMenu.setVisible(true);
      return 0;
    }
    return 1;
  }

  int ShowUI(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    return 1;
  }

  int TranslateAccelerator(int paramInt1, int paramInt2, int paramInt3)
  {
    Menu localMenu = getShell().getMenuBar();
    Object localObject;
    if ((localMenu != null) && (!localMenu.isDisposed()) && (localMenu.isEnabled()))
    {
      Shell localShell = localMenu.getShell();
      int j = localShell.handle;
      int k = OS.SendMessage(j, 32769, 0, 0);
      if (k != 0)
      {
        localObject = new MSG();
        OS.MoveMemory((MSG)localObject, paramInt1, MSG.sizeof);
        if (OS.TranslateAccelerator(j, k, (MSG)localObject) != 0)
          return 0;
      }
    }
    int i = 1;
    MSG localMSG = new MSG();
    OS.MoveMemory(localMSG, paramInt1, MSG.sizeof);
    if (localMSG.message == 256)
    {
      switch (localMSG.wParam)
      {
      case 116:
        OleAutomation localOleAutomation = new OleAutomation(this);
        localObject = localOleAutomation.getIDsOfNames(new String[] { "LocationURL" });
        Variant localVariant = localOleAutomation.getProperty(localObject[0]);
        localOleAutomation.dispose();
        if (localVariant == null)
          break label359;
        if (localVariant.getType() == 8)
        {
          String str = localVariant.getString();
          if (str.equals("about:blank"))
            i = 0;
        }
        localVariant.dispose();
        break;
      case 9:
        break;
      case 8:
      case 13:
        break;
      case 76:
      case 78:
        if ((OS.GetKeyState(17) < 0) && (OS.GetKeyState(18) >= 0) && (OS.GetKeyState(16) >= 0) && ((localMSG.wParam == 78) || (IE.IEVersion >= 8)))
        {
          this.frame.setData("org.eclipse.swt.OleFrame.ConsumeKey", "false");
          i = 0;
        }
        break;
      }
      OS.TranslateMessage(localMSG);
      this.frame.setData("org.eclipse.swt.OleFrame.ConsumeKey", "true");
    }
    label359: switch (localMSG.message)
    {
    case 256:
    case 257:
      if (!OS.IsWinCE)
      {
        int m = 0;
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
          int n = OS.MapVirtualKey(localMSG.wParam, 2);
          if (n != 0)
          {
            m = (n & (OS.IsWinNT ? -2147483648 : 32768)) != 0 ? 1 : 0;
            if (m == 0)
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
                        m = 1;
                        break;
                      }
                }
              }
          }
          break;
        }
        if (m != 0)
          i = 0;
      }
      break;
    }
    return i;
  }

  int TranslateUrl(int paramInt1, int paramInt2, int paramInt3)
  {
    return -2147467263;
  }

  int UpdateUI()
  {
    return -2147467263;
  }

  int ShowMessage(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7)
  {
    int i = (!this.ignoreNextMessage) && (!this.ignoreAllMessages) ? 0 : 1;
    this.ignoreNextMessage = false;
    return i != 0 ? 0 : 1;
  }

  int ShowHelp_64(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong, int paramInt5)
  {
    POINT localPOINT = new POINT();
    OS.MoveMemory(localPOINT, new long[] { paramLong }, 8);
    return ShowHelp(paramInt1, paramInt2, paramInt3, paramInt4, localPOINT.x, localPOINT.y, paramInt5);
  }

  int ShowHelp(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7)
  {
    Browser localBrowser = (Browser)getParent().getParent();
    Event localEvent = new Event();
    localEvent.type = 28;
    localEvent.display = getDisplay();
    localEvent.widget = localBrowser;
    Shell localShell = localBrowser.getShell();
    for (Object localObject = localBrowser; ; localObject = ((Control)localObject).getParent())
      if (((Control)localObject).isListening(28))
        ((Control)localObject).notifyListeners(28, localEvent);
      else
        if (localObject == localShell)
          break;
    return 0;
  }

  int QueryService(int paramInt1, int paramInt2, int paramInt3)
  {
    if ((paramInt2 == 0) || (paramInt3 == 0))
      return -2147024809;
    GUID localGUID = new GUID();
    COM.MoveMemory(localGUID, paramInt2, GUID.sizeof);
    if (COM.IsEqualGUID(localGUID, COM.IIDIInternetSecurityManager))
    {
      COM.MoveMemory(paramInt3, new int[] { this.iInternetSecurityManager.getAddress() }, OS.PTR_SIZEOF);
      AddRef();
      return 0;
    }
    if (COM.IsEqualGUID(localGUID, COM.IIDIAuthenticate))
    {
      COM.MoveMemory(paramInt3, new int[] { this.iAuthenticate.getAddress() }, OS.PTR_SIZEOF);
      AddRef();
      return 0;
    }
    COM.MoveMemory(paramInt3, new int[1], OS.PTR_SIZEOF);
    return -2147467262;
  }

  int SetSecuritySite(int paramInt)
  {
    return -2146697199;
  }

  int GetSecuritySite(int paramInt)
  {
    return -2146697199;
  }

  int MapUrlToZone(int paramInt1, int paramInt2, int paramInt3)
  {
    IE localIE = (IE)((Browser)getParent().getParent()).webBrowser;
    if ((localIE.auto != null) && (localIE._getUrl().startsWith("about:blank")) && (!localIE.untrustedText))
    {
      COM.MoveMemory(paramInt2, new int[] { 1 }, 4);
      return 0;
    }
    return -2146697199;
  }

  int GetSecurityId(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return -2146697199;
  }

  int ProcessUrlAction(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8)
  {
    this.ignoreNextMessage = false;
    if (paramInt2 == 8449)
    {
      IE localIE = (IE)((Browser)getParent().getParent()).webBrowser;
      if ((localIE.auto != null) && (localIE._getUrl().startsWith("about:blank")) && (!localIE.untrustedText))
      {
        if (paramInt4 >= 4)
          COM.MoveMemory(paramInt3, new int[1], 4);
        return 0;
      }
    }
    int i = -2146697199;
    if ((paramInt2 >= 7168) && (paramInt2 <= 7423))
      if (canExecuteApplets())
      {
        i = 196608;
      }
      else
      {
        i = 0;
        this.ignoreNextMessage = true;
      }
    Object localObject;
    if (paramInt2 == 4608)
    {
      localObject = new GUID();
      COM.MoveMemory((GUID)localObject, paramInt5, GUID.sizeof);
      if ((COM.IsEqualGUID((GUID)localObject, COM.IIDJavaBeansBridge)) && (!canExecuteApplets()))
      {
        i = 3;
        this.ignoreNextMessage = true;
      }
      if (COM.IsEqualGUID((GUID)localObject, COM.IIDShockwaveActiveXControl))
      {
        i = 3;
        this.ignoreNextMessage = true;
      }
    }
    if (paramInt2 == 5120)
    {
      localObject = (IE)((Browser)getParent().getParent()).webBrowser;
      i = ((IE)localObject).jsEnabled ? 0 : 3;
    }
    if (i == -2146697199)
      return -2146697199;
    if (paramInt4 >= 4)
      COM.MoveMemory(paramInt3, new int[] { i }, 4);
    return i == 0 ? 0 : 1;
  }

  boolean canExecuteApplets()
  {
    if (IE.IEVersion < 7)
      return false;
    if (this.canExecuteApplets == null)
    {
      WebBrowser localWebBrowser = ((Browser)getParent().getParent()).webBrowser;
      String str = "try {var element = document.createElement('object');element.classid='clsid:CAFEEFAC-DEC7-0000-0000-ABCDEFFEDCBA';return element.object.isPlugin2();} catch (err) {};return false;";
      this.canExecuteApplets = ((Boolean)localWebBrowser.evaluate(str));
      if (this.canExecuteApplets.booleanValue())
        try
        {
          Class.forName("sun.plugin2.main.server.IExplorerPlugin");
          Class.forName("com.sun.deploy.services.Service");
          Class.forName("com.sun.javaws.Globals");
        }
        catch (ClassNotFoundException localClassNotFoundException)
        {
          this.canExecuteApplets = Boolean.FALSE;
        }
    }
    return this.canExecuteApplets.booleanValue();
  }

  int QueryCustomPolicy(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7)
  {
    return -2146697199;
  }

  int SetZoneMapping(int paramInt1, int paramInt2, int paramInt3)
  {
    return -2146697199;
  }

  int GetZoneMappings(int paramInt1, int paramInt2, int paramInt3)
  {
    return -2147467263;
  }

  int QueryStatus(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return -2147221248;
  }

  int Exec(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    if (paramInt1 != 0)
    {
      GUID localGUID = new GUID();
      COM.MoveMemory(localGUID, paramInt1, GUID.sizeof);
      if ((COM.IsEqualGUID(localGUID, COM.CGID_DocHostCommandHandler)) && (paramInt2 == 40))
        return 0;
      if ((paramInt2 == 1) && (COM.IsEqualGUID(localGUID, COM.CGID_Explorer)) && ((paramInt3 & 0xFFFF) == 10))
      {
        IE localIE = (IE)((Browser)getParent().getParent()).webBrowser;
        localIE.toolBar = ((paramInt3 & 0xFFFF0000) != 0);
      }
    }
    return -2147221248;
  }

  int Authenticate(int paramInt1, int paramInt2, int paramInt3)
  {
    IE localIE = (IE)((Browser)getParent().getParent()).webBrowser;
    for (int i = 0; i < localIE.authenticationListeners.length; i++)
    {
      AuthenticationEvent localAuthenticationEvent = new AuthenticationEvent(localIE.browser);
      localAuthenticationEvent.location = localIE.lastNavigateURL;
      localIE.authenticationListeners[i].authenticate(localAuthenticationEvent);
      if (!localAuthenticationEvent.doit)
        return -2147024891;
      if ((localAuthenticationEvent.user != null) && (localAuthenticationEvent.password != null))
      {
        TCHAR localTCHAR1 = new TCHAR(0, localAuthenticationEvent.user, true);
        int j = localTCHAR1.length() * TCHAR.sizeof;
        int k = COM.CoTaskMemAlloc(j);
        OS.MoveMemory(k, localTCHAR1, j);
        TCHAR localTCHAR2 = new TCHAR(0, localAuthenticationEvent.password, true);
        j = localTCHAR2.length() * TCHAR.sizeof;
        int m = COM.CoTaskMemAlloc(j);
        OS.MoveMemory(m, localTCHAR2, j);
        C.memmove(paramInt1, new int[1], C.PTR_SIZEOF);
        C.memmove(paramInt2, new int[] { k }, C.PTR_SIZEOF);
        C.memmove(paramInt3, new int[] { m }, C.PTR_SIZEOF);
        return 0;
      }
    }
    C.memmove(paramInt1, new int[] { getShell().handle }, C.PTR_SIZEOF);
    return 0;
  }

  int GetTypeInfoCount(int paramInt)
  {
    C.memmove(paramInt, new int[1], 4);
    return 0;
  }

  int GetTypeInfo(int paramInt1, int paramInt2, int paramInt3)
  {
    return 0;
  }

  int GetIDsOfNames(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    int[] arrayOfInt1 = new int[1];
    OS.MoveMemory(arrayOfInt1, paramInt2, C.PTR_SIZEOF);
    int i = OS.wcslen(arrayOfInt1[0]);
    char[] arrayOfChar = new char[i];
    OS.MoveMemory(arrayOfChar, arrayOfInt1[0], i * 2);
    String str = String.valueOf(arrayOfChar);
    int j = 0;
    int[] arrayOfInt2 = new int[paramInt3];
    int k;
    if (str.equals("callJava"))
    {
      for (k = 0; k < paramInt3; k++)
        arrayOfInt2[k] = (k + 1);
    }
    else
    {
      j = -2147352570;
      for (k = 0; k < paramInt3; k++)
        arrayOfInt2[k] = -1;
    }
    OS.MoveMemory(paramInt5, arrayOfInt2, paramInt3 * 4);
    return j;
  }

  int Invoke(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8)
  {
    IE localIE = (IE)((Browser)getParent().getParent()).webBrowser;
    Hashtable localHashtable = localIE.functions;
    if (localHashtable == null)
    {
      if (paramInt6 != 0)
        COM.MoveMemory(paramInt6, new int[1], C.PTR_SIZEOF);
      return 0;
    }
    DISPPARAMS localDISPPARAMS = new DISPPARAMS();
    COM.MoveMemory(localDISPPARAMS, paramInt5, DISPPARAMS.sizeof);
    if (localDISPPARAMS.cArgs != 2)
    {
      if (paramInt6 != 0)
        COM.MoveMemory(paramInt6, new int[1], C.PTR_SIZEOF);
      return 0;
    }
    int i = localDISPPARAMS.rgvarg + Variant.sizeof;
    Variant localVariant = Variant.win32_new(i);
    if (localVariant.getType() != 3)
    {
      localVariant.dispose();
      if (paramInt6 != 0)
        COM.MoveMemory(paramInt6, new int[1], C.PTR_SIZEOF);
      return 0;
    }
    int j = localVariant.getInt();
    localVariant.dispose();
    if (j <= 0)
    {
      if (paramInt6 != 0)
        COM.MoveMemory(paramInt6, new int[1], C.PTR_SIZEOF);
      return 0;
    }
    localVariant = Variant.win32_new(localDISPPARAMS.rgvarg);
    Integer localInteger = new Integer(j);
    BrowserFunction localBrowserFunction = (BrowserFunction)localHashtable.get(localInteger);
    Object localObject1 = null;
    if (localBrowserFunction != null)
      try
      {
        Object localObject2 = convertToJava(localVariant);
        if ((localObject2 instanceof Object[]))
        {
          Object[] arrayOfObject = (Object[])localObject2;
          try
          {
            localObject1 = localBrowserFunction.function(arrayOfObject);
          }
          catch (Exception localException)
          {
            localObject1 = WebBrowser.CreateErrorString(localException.getLocalizedMessage());
          }
        }
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        if (localBrowserFunction.isEvaluate)
          localBrowserFunction.function(new String[] { WebBrowser.CreateErrorString(new SWTException(51).getLocalizedMessage()) });
        localObject1 = WebBrowser.CreateErrorString(localIllegalArgumentException.getLocalizedMessage());
      }
    localVariant.dispose();
    if (paramInt6 != 0)
      if (localObject1 == null)
      {
        COM.MoveMemory(paramInt6, new int[1], C.PTR_SIZEOF);
      }
      else
      {
        try
        {
          localVariant = convertToJS(localObject1);
        }
        catch (SWTException localSWTException)
        {
          localVariant = convertToJS(WebBrowser.CreateErrorString(localSWTException.getLocalizedMessage()));
        }
        Variant.win32_copy(paramInt6, localVariant);
        localVariant.dispose();
      }
    return 0;
  }

  Object convertToJava(Variant paramVariant)
  {
    switch (paramVariant.getType())
    {
    case 1:
      return null;
    case 8:
      return paramVariant.getString();
    case 11:
      return new Boolean(paramVariant.getBoolean());
    case 2:
    case 3:
    case 4:
    case 5:
    case 20:
      return new Double(paramVariant.getDouble());
    case 9:
      Object[] arrayOfObject = (Object[])null;
      OleAutomation localOleAutomation = paramVariant.getAutomation();
      TYPEATTR localTYPEATTR = localOleAutomation.getTypeInfoAttributes();
      if (localTYPEATTR != null)
      {
        GUID localGUID = new GUID();
        localGUID.Data1 = localTYPEATTR.guid_Data1;
        localGUID.Data2 = localTYPEATTR.guid_Data2;
        localGUID.Data3 = localTYPEATTR.guid_Data3;
        localGUID.Data4 = localTYPEATTR.guid_Data4;
        if (COM.IsEqualGUID(localGUID, COM.IIDIJScriptTypeInfo))
        {
          int[] arrayOfInt = localOleAutomation.getIDsOfNames(new String[] { "length" });
          if (arrayOfInt != null)
          {
            Variant localVariant1 = localOleAutomation.getProperty(arrayOfInt[0]);
            int i = localVariant1.getInt();
            localVariant1.dispose();
            arrayOfObject = new Object[i];
            for (int j = 0; j < i; j++)
            {
              arrayOfInt = localOleAutomation.getIDsOfNames(new String[] { String.valueOf(j) });
              if (arrayOfInt != null)
              {
                Variant localVariant2 = localOleAutomation.getProperty(arrayOfInt[0]);
                try
                {
                  arrayOfObject[j] = convertToJava(localVariant2);
                  localVariant2.dispose();
                }
                catch (IllegalArgumentException localIllegalArgumentException)
                {
                  localVariant2.dispose();
                  localOleAutomation.dispose();
                  throw localIllegalArgumentException;
                }
              }
            }
          }
        }
        else
        {
          localOleAutomation.dispose();
          SWT.error(5);
        }
      }
      localOleAutomation.dispose();
      return arrayOfObject;
    case 6:
    case 7:
    case 10:
    case 12:
    case 13:
    case 14:
    case 15:
    case 16:
    case 17:
    case 18:
    case 19:
    }
    SWT.error(5);
    return null;
  }

  Variant convertToJS(Object paramObject)
  {
    if (paramObject == null)
      return new Variant();
    if ((paramObject instanceof String))
      return new Variant((String)paramObject);
    if ((paramObject instanceof Boolean))
      return new Variant(((Boolean)paramObject).booleanValue());
    if ((paramObject instanceof Number))
      return new Variant(((Number)paramObject).doubleValue());
    if ((paramObject instanceof Object[]))
    {
      IE localIE = (IE)((Browser)getParent().getParent()).webBrowser;
      OleAutomation localOleAutomation1 = localIE.auto;
      int[] arrayOfInt1 = localOleAutomation1.getIDsOfNames(new String[] { "Document" });
      if (arrayOfInt1 == null)
        return new Variant();
      Variant localVariant1 = localOleAutomation1.getProperty(arrayOfInt1[0]);
      if (localVariant1 == null)
        return new Variant();
      if (localVariant1.getType() == 0)
      {
        localVariant1.dispose();
        return new Variant();
      }
      OleAutomation localOleAutomation2 = localVariant1.getAutomation();
      localVariant1.dispose();
      arrayOfInt1 = localOleAutomation2.getIDsOfNames(new String[] { "parentWindow" });
      if (arrayOfInt1 == null)
      {
        localOleAutomation2.dispose();
        return new Variant();
      }
      localVariant1 = localOleAutomation2.getProperty(arrayOfInt1[0]);
      if ((localVariant1 == null) || (localVariant1.getType() == 0))
      {
        if (localVariant1 != null)
          localVariant1.dispose();
        localOleAutomation2.dispose();
        return new Variant();
      }
      OleAutomation localOleAutomation3 = localVariant1.getAutomation();
      localVariant1.dispose();
      localOleAutomation2.dispose();
      arrayOfInt1 = localOleAutomation3.getIDsOfNames(new String[] { "Array" });
      if (arrayOfInt1 == null)
      {
        localOleAutomation3.dispose();
        return new Variant();
      }
      Variant localVariant2 = localOleAutomation3.getProperty(arrayOfInt1[0]);
      localOleAutomation3.dispose();
      IDispatch localIDispatch = localVariant2.getDispatch();
      localVariant2.dispose();
      int[] arrayOfInt2 = new int[1];
      int i = localIDispatch.QueryInterface(COM.IIDIDispatchEx, arrayOfInt2);
      if (i != 0)
        return new Variant();
      IDispatchEx localIDispatchEx = new IDispatchEx(arrayOfInt2[0]);
      arrayOfInt2[0] = 0;
      int j = OS.GlobalAlloc(64, VARIANT.sizeof);
      DISPPARAMS localDISPPARAMS = new DISPPARAMS();
      i = localIDispatchEx.InvokeEx(0, 2048, 16384, localDISPPARAMS, j, null, 0);
      if (i != 0)
      {
        OS.GlobalFree(j);
        return new Variant();
      }
      Variant localVariant3 = Variant.win32_new(j);
      OS.GlobalFree(j);
      Object[] arrayOfObject = (Object[])paramObject;
      int k = arrayOfObject.length;
      localOleAutomation1 = localVariant3.getAutomation();
      int[] arrayOfInt3 = localOleAutomation1.getIDsOfNames(new String[] { "push" });
      if (arrayOfInt3 != null)
        for (int m = 0; m < k; m++)
        {
          Object localObject = arrayOfObject[m];
          try
          {
            Variant localVariant4 = convertToJS(localObject);
            localOleAutomation1.invoke(arrayOfInt3[0], new Variant[] { localVariant4 });
            localVariant4.dispose();
          }
          catch (SWTException localSWTException)
          {
            localOleAutomation1.dispose();
            localVariant3.dispose();
            throw localSWTException;
          }
        }
      localOleAutomation1.dispose();
      return localVariant3;
    }
    SWT.error(51);
    return null;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.browser.WebSite
 * JD-Core Version:    0.6.2
 */