package org.eclipse.swt.browser;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.internal.C;
import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.GUID;
import org.eclipse.swt.internal.ole.win32.IDispatch;
import org.eclipse.swt.internal.ole.win32.IPersistStreamInit;
import org.eclipse.swt.internal.ole.win32.IUnknown;
import org.eclipse.swt.internal.win32.MSG;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.SAFEARRAY;
import org.eclipse.swt.internal.win32.SAFEARRAYBOUND;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.ole.win32.OleAutomation;
import org.eclipse.swt.ole.win32.OleEvent;
import org.eclipse.swt.ole.win32.OleFrame;
import org.eclipse.swt.ole.win32.OleListener;
import org.eclipse.swt.ole.win32.Variant;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

class IE extends WebBrowser
{
  OleFrame frame;
  WebSite site;
  OleAutomation auto;
  OleListener domListener;
  OleAutomation[] documents = new OleAutomation[0];
  boolean back;
  boolean forward;
  boolean delaySetText;
  boolean ignoreDispose;
  boolean ignoreTraverse;
  boolean performingInitialNavigate;
  boolean installFunctionsOnDocumentComplete;
  boolean untrustedText;
  boolean isRefresh;
  Point location;
  Point size;
  boolean addressBar = true;
  boolean menuBar = true;
  boolean statusBar = true;
  boolean toolBar = true;
  int globalDispatch;
  String html;
  String lastNavigateURL;
  String uncRedirect;
  Object[] pendingText;
  Object[] pendingUrl;
  int style;
  int lastKeyCode;
  int lastCharCode;
  int lastMouseMoveX;
  int lastMouseMoveY;
  static int IEVersion;
  static int PDFCount;
  static String ProgId = "Shell.Explorer";
  static final int BeforeNavigate2 = 250;
  static final int CommandStateChange = 105;
  static final int DocumentComplete = 259;
  static final int DownloadComplete = 104;
  static final int NavigateComplete2 = 252;
  static final int NewWindow2 = 251;
  static final int OnMenuBar = 256;
  static final int OnStatusBar = 257;
  static final int OnToolBar = 255;
  static final int OnVisible = 254;
  static final int ProgressChange = 108;
  static final int RegisterAsBrowser = 552;
  static final int StatusTextChange = 102;
  static final int TitleChange = 113;
  static final int WindowClosing = 263;
  static final int WindowSetHeight = 267;
  static final int WindowSetLeft = 264;
  static final int WindowSetResizable = 262;
  static final int WindowSetTop = 265;
  static final int WindowSetWidth = 266;
  static final int NavigateError = 271;
  static final short CSC_NAVIGATEFORWARD = 1;
  static final short CSC_NAVIGATEBACK = 2;
  static final int INET_E_DEFAULT_ACTION = -2146697199;
  static final int INET_E_RESOURCE_NOT_FOUND = -2146697211;
  static final int READYSTATE_COMPLETE = 4;
  static final int URLPOLICY_ALLOW = 0;
  static final int URLPOLICY_DISALLOW = 3;
  static final int URLPOLICY_JAVA_PROHIBIT = 0;
  static final int URLPOLICY_JAVA_LOW = 196608;
  static final int URLZONE_LOCAL_MACHINE = 0;
  static final int URLZONE_INTRANET = 1;
  static final int URLACTION_ACTIVEX_MIN = 4608;
  static final int URLACTION_ACTIVEX_MAX = 5119;
  static final int URLACTION_ACTIVEX_RUN = 4608;
  static final int URLACTION_FEATURE_ZONE_ELEVATION = 8449;
  static final int URLACTION_JAVA_MIN = 7168;
  static final int URLACTION_JAVA_MAX = 7423;
  static final int URLACTION_SCRIPT_RUN = 5120;
  static final int DISPID_AMBIENT_DLCONTROL = -5512;
  static final int DLCTL_DLIMAGES = 16;
  static final int DLCTL_VIDEOS = 32;
  static final int DLCTL_BGSOUNDS = 64;
  static final int DLCTL_NO_SCRIPTS = 128;
  static final int DLCTL_NO_JAVA = 256;
  static final int DLCTL_NO_RUNACTIVEXCTLS = 512;
  static final int DLCTL_NO_DLACTIVEXCTLS = 1024;
  static final int DLCTL_DOWNLOADONLY = 2048;
  static final int DLCTL_NO_FRAMEDOWNLOAD = 4096;
  static final int DLCTL_RESYNCHRONIZE = 8192;
  static final int DLCTL_PRAGMA_NO_CACHE = 16384;
  static final int DLCTL_FORCEOFFLINE = 268435456;
  static final int DLCTL_NO_CLIENTPULL = 536870912;
  static final int DLCTL_SILENT = 1073741824;
  static final int DOCHOSTUIFLAG_THEME = 262144;
  static final int DOCHOSTUIFLAG_NO3DBORDER = 4;
  static final int DOCHOSTUIFLAG_NO3DOUTERBORDER = 2097152;
  static final String ABOUT_BLANK = "about:blank";
  static final String CLSID_SHELLEXPLORER1 = "{EAB22AC3-30C1-11CF-A7EB-0000C05BAE0B}";
  static final String EXTENSION_PDF = ".pdf";
  static final int MAX_PDF = 20;
  static final String EVENT_DOUBLECLICK = "dblclick";
  static final String EVENT_DRAGEND = "dragend";
  static final String EVENT_DRAGSTART = "dragstart";
  static final String EVENT_KEYDOWN = "keydown";
  static final String EVENT_KEYPRESS = "keypress";
  static final String EVENT_KEYUP = "keyup";
  static final String EVENT_MOUSEMOVE = "mousemove";
  static final String EVENT_MOUSEWHEEL = "mousewheel";
  static final String EVENT_MOUSEUP = "mouseup";
  static final String EVENT_MOUSEDOWN = "mousedown";
  static final String EVENT_MOUSEOUT = "mouseout";
  static final String EVENT_MOUSEOVER = "mouseover";
  static final String PROTOCOL_FILE = "file://";
  static final String PROPERTY_ALTKEY = "altKey";
  static final String PROPERTY_BUTTON = "button";
  static final String PROPERTY_CTRLKEY = "ctrlKey";
  static final String PROPERTY_DOCUMENT = "Document";
  static final String PROPERTY_FROMELEMENT = "fromElement";
  static final String PROPERTY_KEYCODE = "keyCode";
  static final String PROPERTY_REPEAT = "repeat";
  static final String PROPERTY_RETURNVALUE = "returnValue";
  static final String PROPERTY_SCREENX = "screenX";
  static final String PROPERTY_SCREENY = "screenY";
  static final String PROPERTY_SHIFTKEY = "shiftKey";
  static final String PROPERTY_TOELEMENT = "toElement";
  static final String PROPERTY_TYPE = "type";
  static final String PROPERTY_WHEELDELTA = "wheelDelta";

  static
  {
    NativeClearSessions = new Runnable()
    {
      public void run()
      {
        if (OS.IsPPC)
          return;
        OS.InternetSetOption(0, 42, 0, 0);
      }
    };
    NativeGetCookie = new Runnable()
    {
      public void run()
      {
        if (OS.IsPPC)
          return;
        TCHAR localTCHAR1 = new TCHAR(0, IE.CookieUrl, true);
        TCHAR localTCHAR2 = new TCHAR(0, 8192);
        int[] arrayOfInt = { localTCHAR2.length() };
        if (!OS.InternetGetCookie(localTCHAR1, null, localTCHAR2, arrayOfInt))
        {
          arrayOfInt[0] /= TCHAR.sizeof;
          localTCHAR2 = new TCHAR(0, arrayOfInt[0]);
          if (!OS.InternetGetCookie(localTCHAR1, null, localTCHAR2, arrayOfInt))
            return;
        }
        String str1 = localTCHAR2.toString(0, arrayOfInt[0]);
        StringTokenizer localStringTokenizer = new StringTokenizer(str1, ";");
        while (localStringTokenizer.hasMoreTokens())
        {
          String str2 = localStringTokenizer.nextToken();
          int i = str2.indexOf('=');
          if (i != -1)
          {
            String str3 = str2.substring(0, i).trim();
            if (str3.equals(IE.CookieName))
            {
              IE.CookieValue = str2.substring(i + 1).trim();
              return;
            }
          }
        }
      }
    };
    NativeSetCookie = new Runnable()
    {
      public void run()
      {
        if (OS.IsPPC)
          return;
        TCHAR localTCHAR1 = new TCHAR(0, IE.CookieUrl, true);
        TCHAR localTCHAR2 = new TCHAR(0, IE.CookieValue, true);
        IE.CookieResult = OS.InternetSetCookie(localTCHAR1, null, localTCHAR2);
      }
    };
    TCHAR localTCHAR1 = new TCHAR(0, "Software\\Microsoft\\Internet Explorer", true);
    int[] arrayOfInt1 = new int[1];
    int[] arrayOfInt2;
    Object localObject1;
    Object localObject2;
    if (OS.RegOpenKeyEx(-2147483646, localTCHAR1, 0, 131097, arrayOfInt1) == 0)
    {
      arrayOfInt2 = new int[1];
      TCHAR localTCHAR2 = new TCHAR(0, "Version", true);
      int j = OS.RegQueryValueEx(arrayOfInt1[0], localTCHAR2, 0, null, null, arrayOfInt2);
      if (j == 0)
      {
        localObject1 = new TCHAR(0, arrayOfInt2[0] / TCHAR.sizeof);
        j = OS.RegQueryValueEx(arrayOfInt1[0], localTCHAR2, 0, null, (TCHAR)localObject1, arrayOfInt2);
        if (j == 0)
        {
          localObject2 = ((TCHAR)localObject1).toString(0, ((TCHAR)localObject1).strlen());
          int k = ((String)localObject2).indexOf(".");
          if (k != -1)
          {
            String str = ((String)localObject2).substring(0, k);
            try
            {
              IEVersion = Integer.valueOf(str).intValue();
            }
            catch (NumberFormatException localNumberFormatException)
            {
            }
          }
        }
      }
      OS.RegCloseKey(arrayOfInt1[0]);
    }
    localTCHAR1 = new TCHAR(0, "Shell.Explorer\\CLSID", true);
    arrayOfInt1 = new int[1];
    if (OS.RegOpenKeyEx(-2147483648, localTCHAR1, 0, 131097, arrayOfInt1) == 0)
    {
      arrayOfInt2 = new int[1];
      int i = OS.RegQueryValueEx(arrayOfInt1[0], null, 0, null, null, arrayOfInt2);
      if (i == 0)
      {
        TCHAR localTCHAR3 = new TCHAR(0, arrayOfInt2[0] / TCHAR.sizeof);
        i = OS.RegQueryValueEx(arrayOfInt1[0], null, 0, null, localTCHAR3, arrayOfInt2);
        if (i == 0)
        {
          localObject1 = localTCHAR3.toString(0, localTCHAR3.strlen());
          if (((String)localObject1).equals("{EAB22AC3-30C1-11CF-A7EB-0000C05BAE0B}"))
          {
            localTCHAR1 = new TCHAR(0, "Shell.Explorer.2", true);
            localObject2 = new int[1];
            if (OS.RegOpenKeyEx(-2147483648, localTCHAR1, 0, 131097, (int[])localObject2) == 0)
            {
              OS.RegCloseKey(localObject2[0]);
              ProgId = "Shell.Explorer.2";
            }
          }
        }
      }
      OS.RegCloseKey(arrayOfInt1[0]);
    }
    if (NativePendingCookies != null)
      SetPendingCookies(NativePendingCookies);
    NativePendingCookies = null;
  }

  public boolean create(Composite paramComposite, int paramInt)
  {
    this.style = paramInt;
    this.frame = new OleFrame(this.browser, 0);
    try
    {
      this.site = new WebSite(this.frame, 0, ProgId);
    }
    catch (SWTException localSWTException)
    {
      this.browser.dispose();
      SWT.error(2);
    }
    this.site.doVerb(-5);
    this.auto = new OleAutomation(this.site);
    this.domListener = new OleListener()
    {
      public void handleEvent(OleEvent paramAnonymousOleEvent)
      {
        IE.this.handleDOMEvent(paramAnonymousOleEvent);
      }
    };
    Listener local5 = new Listener()
    {
      public void handleEvent(Event paramAnonymousEvent)
      {
        switch (paramAnonymousEvent.type)
        {
        case 12:
          if (IE.this.ignoreDispose)
          {
            IE.this.ignoreDispose = false;
          }
          else
          {
            IE.this.ignoreDispose = true;
            IE.this.browser.notifyListeners(paramAnonymousEvent.type, paramAnonymousEvent);
            paramAnonymousEvent.type = 0;
            if (!IE.this.browser.isClosing)
            {
              LocationListener[] arrayOfLocationListener = IE.this.locationListeners;
              IE.this.locationListeners = new LocationListener[0];
              IE.this.site.ignoreAllMessages = true;
              IE.this.execute("window.location.href='about:blank'");
              IE.this.site.ignoreAllMessages = false;
              IE.this.locationListeners = arrayOfLocationListener;
            }
            if (!IE.this.frame.isDisposed())
              IE.this.unhookDOMListeners(IE.this.documents);
            for (int i = 0; i < IE.this.documents.length; i++)
              IE.this.documents[i].dispose();
            IE.this.documents = null;
            Enumeration localEnumeration = IE.this.functions.elements();
            while (localEnumeration.hasMoreElements())
              ((BrowserFunction)localEnumeration.nextElement()).dispose(false);
            IE.this.functions = null;
            IE.this.lastNavigateURL = (IE.this.uncRedirect = null);
            IE.this.domListener = null;
            if (IE.this.auto != null)
              IE.this.auto.dispose();
            IE.this.auto = null;
          }
          break;
        case 11:
          IE.this.frame.setBounds(IE.this.browser.getClientArea());
          break;
        case 37:
          paramAnonymousEvent.doit = false;
          break;
        case 15:
          IE.this.site.setFocus();
          break;
        case 31:
          if ((paramAnonymousEvent.detail == 8) && ((paramAnonymousEvent.widget instanceof WebSite)))
          {
            IE.this.browser.traverse(8, paramAnonymousEvent);
            paramAnonymousEvent.doit = false;
          }
          if ((paramAnonymousEvent.detail == 16) && ((paramAnonymousEvent.widget instanceof Browser)))
          {
            IE.this.site.traverse(16, paramAnonymousEvent);
            paramAnonymousEvent.doit = false;
          }
          if ((paramAnonymousEvent.detail == 4) && (paramAnonymousEvent.doit) && ((paramAnonymousEvent.widget instanceof Browser)))
          {
            paramAnonymousEvent.type = 0;
            paramAnonymousEvent.doit = false;
          }
          break;
        }
      }
    };
    this.browser.addListener(12, local5);
    this.browser.addListener(15, local5);
    this.browser.addListener(11, local5);
    this.browser.addListener(31, local5);
    this.site.addListener(37, local5);
    this.site.addListener(31, local5);
    OleListener local6 = new OleListener()
    {
      public void handleEvent(OleEvent paramAnonymousOleEvent)
      {
        if (IE.this.auto != null)
        {
          Object localObject2;
          Object localObject7;
          Object localObject9;
          Object localObject11;
          Object localObject5;
          Object localObject10;
          int n;
          int i2;
          Object localObject4;
          Object localObject6;
          Object localObject8;
          int i3;
          String str1;
          switch (paramAnonymousOleEvent.type)
          {
          case 250:
            IE.this.isRefresh = false;
            if (!IE.this.performingInitialNavigate)
            {
              Variant localVariant1 = paramAnonymousOleEvent.arguments[1];
              localObject2 = localVariant1.getString();
              Object localObject3;
              int i1;
              if (IE.this.uncRedirect != null)
              {
                if ((IE.this.uncRedirect.equals(localObject2)) || ((IE.this.uncRedirect.startsWith((String)localObject2)) && (IE.this.uncRedirect.indexOf('\\', 2) == ((String)localObject2).length())))
                {
                  localObject3 = paramAnonymousOleEvent.arguments[6];
                  if (localObject3 != null)
                  {
                    i1 = ((Variant)localObject3).getByRef();
                    COM.MoveMemory(i1, new short[1], 2);
                  }
                }
                else
                {
                  IE.this.uncRedirect = null;
                }
              }
              else
              {
                if ((((String)localObject2).indexOf(":/") == -1) && (((String)localObject2).indexOf(":\\") != -1))
                  localObject2 = "file://" + ((String)localObject2).replace('\\', '/');
                if ((((String)localObject2).startsWith("file://")) && (IE.this._getUrl().startsWith("about:blank")) && (IE.this.untrustedText))
                {
                  localObject3 = paramAnonymousOleEvent.arguments[6];
                  if (localObject3 != null)
                  {
                    i1 = ((Variant)localObject3).getByRef();
                    COM.MoveMemory(i1, new short[] { -1 }, 2);
                  }
                }
                else
                {
                  localObject3 = new LocationEvent(IE.this.browser);
                  ((LocationEvent)localObject3).display = IE.this.browser.getDisplay();
                  ((LocationEvent)localObject3).widget = IE.this.browser;
                  ((LocationEvent)localObject3).location = ((String)localObject2);
                  ((LocationEvent)localObject3).doit = true;
                  for (i1 = 0; i1 < IE.this.locationListeners.length; i1++)
                    IE.this.locationListeners[i1].changing((LocationEvent)localObject3);
                  i1 = (((LocationEvent)localObject3).doit) && (!IE.this.browser.isDisposed()) ? 1 : 0;
                  localObject7 = paramAnonymousOleEvent.arguments[6];
                  if (localObject7 != null)
                  {
                    int i5 = ((Variant)localObject7).getByRef();
                    COM.MoveMemory(i5, new short[] { i1 != 0 ? 0 : -1 }, 2);
                  }
                  if (i1 != 0)
                  {
                    IE.this.lastNavigateURL = ((String)localObject2);
                    localVariant1 = paramAnonymousOleEvent.arguments[0];
                    localObject9 = localVariant1.getDispatch();
                    Variant localVariant2 = new Variant(IE.this.auto);
                    localObject11 = localVariant2.getDispatch();
                    int i9 = ((IDispatch)localObject11).getAddress() == ((IDispatch)localObject9).getAddress() ? 1 : 0;
                    if (i9 != 0)
                    {
                      IE.this.unhookDOMListeners(IE.this.documents);
                      for (int i12 = 0; i12 < IE.this.documents.length; i12++)
                        IE.this.documents[i12].dispose();
                      IE.this.documents = new OleAutomation[0];
                    }
                  }
                }
              }
            }
            break;
          case 105:
            boolean bool1 = false;
            localObject2 = paramAnonymousOleEvent.arguments[0];
            int m = ((Variant)localObject2).getInt();
            localObject2 = paramAnonymousOleEvent.arguments[1];
            bool1 = ((Variant)localObject2).getBoolean();
            switch (m)
            {
            case 2:
              IE.this.back = bool1;
              break;
            case 1:
              IE.this.forward = bool1;
            }
            break;
          case 259:
            if (IE.this.performingInitialNavigate)
            {
              IE.this.performingInitialNavigate = false;
              if (IE.this.pendingText != null)
                IE.this.setText((String)IE.this.pendingText[0], ((Boolean)IE.this.pendingText[1]).booleanValue());
              else if (IE.this.pendingUrl != null)
                IE.this.setUrl((String)IE.this.pendingUrl[0], (String)IE.this.pendingUrl[1], (String[])IE.this.pendingUrl[2]);
              IE.this.pendingText = (IE.this.pendingUrl = null);
            }
            else
            {
              localObject1 = paramAnonymousOleEvent.arguments[0];
              localObject2 = ((Variant)localObject1).getDispatch();
              localObject1 = paramAnonymousOleEvent.arguments[1];
              String str2 = ((Variant)localObject1).getString();
              if ((str2.indexOf(":/") == -1) && (str2.indexOf(":\\") != -1))
                str2 = "file://" + str2.replace('\\', '/');
              if ((IE.this.html != null) && (str2.equals("about:blank")))
              {
                if (IE.this.delaySetText)
                {
                  IE.this.delaySetText = false;
                  IE.this.browser.getDisplay().asyncExec(new IE.7(this));
                }
                else
                {
                  IE.this.setHTML(IE.this.html);
                  IE.this.html = null;
                }
              }
              else
              {
                localObject5 = new Variant(IE.this.auto);
                localObject7 = ((Variant)localObject5).getDispatch();
                localObject9 = new LocationEvent(IE.this.browser);
                ((LocationEvent)localObject9).display = IE.this.browser.getDisplay();
                ((LocationEvent)localObject9).widget = IE.this.browser;
                ((LocationEvent)localObject9).location = str2;
                ((LocationEvent)localObject9).top = (((IDispatch)localObject7).getAddress() == ((IDispatch)localObject2).getAddress());
                for (int i7 = 0; i7 < IE.this.locationListeners.length; i7++)
                  IE.this.locationListeners[i7].changed((LocationEvent)localObject9);
                if (IE.this.browser.isDisposed())
                  return;
                if ((IE.this.globalDispatch != 0) && (((IDispatch)localObject2).getAddress() == IE.this.globalDispatch))
                {
                  IE.this.globalDispatch = 0;
                  localObject10 = (IE)IE.this.browser.webBrowser;
                  if (((IE)localObject10).installFunctionsOnDocumentComplete)
                  {
                    ((IE)localObject10).installFunctionsOnDocumentComplete = false;
                    localObject11 = IE.this.functions.elements();
                    while (((Enumeration)localObject11).hasMoreElements())
                    {
                      BrowserFunction localBrowserFunction1 = (BrowserFunction)((Enumeration)localObject11).nextElement();
                      IE.this.execute(localBrowserFunction1.functionString);
                    }
                  }
                  localObject11 = new ProgressEvent(IE.this.browser);
                  ((ProgressEvent)localObject11).display = IE.this.browser.getDisplay();
                  ((ProgressEvent)localObject11).widget = IE.this.browser;
                  for (int i10 = 0; i10 < IE.this.progressListeners.length; i10++)
                    IE.this.progressListeners[i10].completed((ProgressEvent)localObject11);
                }
              }
            }
            break;
          case 104:
            if ((goto 3388) && (IE.this.isRefresh))
            {
              IE.this.isRefresh = false;
              localObject1 = IE.this.functions.elements();
              while (((Enumeration)localObject1).hasMoreElements())
              {
                localObject2 = (BrowserFunction)((Enumeration)localObject1).nextElement();
                IE.this.execute(((BrowserFunction)localObject2).functionString);
              }
              localObject2 = new ProgressEvent(IE.this.browser);
              ((ProgressEvent)localObject2).display = IE.this.browser.getDisplay();
              ((ProgressEvent)localObject2).widget = IE.this.browser;
              for (n = 0; n < IE.this.progressListeners.length; n++)
                IE.this.progressListeners[n].completed((ProgressEvent)localObject2);
            }
            break;
          case 252:
            localObject1 = paramAnonymousOleEvent.arguments[1];
            localObject2 = ((Variant)localObject1).getString();
            n = ((String)localObject2).lastIndexOf('.');
            if (n != -1)
            {
              localObject5 = ((String)localObject2).substring(n);
              if (((String)localObject5).equalsIgnoreCase(".pdf"))
              {
                IE.PDFCount += 1;
                if (IE.PDFCount > 20)
                  COM.FreeUnusedLibraries = false;
              }
            }
            if (IE.this.uncRedirect != null)
            {
              if (IE.this.uncRedirect.equals(localObject2))
                IE.this.uncRedirect = null;
              else if (IE.this.uncRedirect.startsWith((String)localObject2))
                IE.this.navigate(IE.this.uncRedirect, null, null, true);
              else
                IE.this.uncRedirect = null;
            }
            else
            {
              localObject1 = paramAnonymousOleEvent.arguments[0];
              localObject5 = ((Variant)localObject1).getDispatch();
              if (IE.this.globalDispatch == 0)
                IE.this.globalDispatch = ((IDispatch)localObject5).getAddress();
              localObject7 = ((Variant)localObject1).getAutomation();
              localObject9 = new Variant(IE.this.auto);
              localObject10 = ((Variant)localObject9).getDispatch();
              boolean bool4 = ((IDispatch)localObject10).getAddress() == ((IDispatch)localObject5).getAddress();
              if (bool4)
              {
                Enumeration localEnumeration = IE.this.functions.elements();
                while (localEnumeration.hasMoreElements())
                {
                  BrowserFunction localBrowserFunction2 = (BrowserFunction)localEnumeration.nextElement();
                  IE.this.execute(localBrowserFunction2.functionString);
                }
              }
              IE.this.hookDOMListeners((OleAutomation)localObject7, bool4);
              ((OleAutomation)localObject7).dispose();
            }
            break;
          case 271:
            if (IE.this.uncRedirect != null)
            {
              IE.this.uncRedirect = null;
            }
            else
            {
              localObject1 = paramAnonymousOleEvent.arguments[1];
              localObject2 = ((Variant)localObject1).getString();
              if (((String)localObject2).startsWith("\\\\"))
              {
                localObject1 = paramAnonymousOleEvent.arguments[3];
                n = ((Variant)localObject1).getInt();
                if (n == -2146697211)
                {
                  i2 = ((String)localObject2).indexOf('\\', 2);
                  if (i2 != -1)
                  {
                    localObject7 = ((String)localObject2).substring(0, i2);
                    localObject9 = paramAnonymousOleEvent.arguments[4];
                    if (localObject9 != null)
                    {
                      int i8 = ((Variant)localObject9).getByRef();
                      COM.MoveMemory(i8, new short[] { -1 }, 2);
                    }
                    IE.this.browser.getDisplay().asyncExec(new IE.8(this, (String)localObject2, (String)localObject7));
                  }
                }
              }
            }
            break;
          case 251:
            localObject1 = paramAnonymousOleEvent.arguments[1];
            int i = ((Variant)localObject1).getByRef();
            localObject4 = new WindowEvent(IE.this.browser);
            ((WindowEvent)localObject4).display = IE.this.browser.getDisplay();
            ((WindowEvent)localObject4).widget = IE.this.browser;
            ((WindowEvent)localObject4).required = false;
            for (i2 = 0; i2 < IE.this.openWindowListeners.length; i2++)
              IE.this.openWindowListeners[i2].open((WindowEvent)localObject4);
            localObject6 = null;
            if ((((WindowEvent)localObject4).browser != null) && ((((WindowEvent)localObject4).browser.webBrowser instanceof IE)))
              localObject6 = (IE)((WindowEvent)localObject4).browser.webBrowser;
            int i4 = (localObject6 != null) && (!((IE)localObject6).browser.isDisposed()) ? 1 : 0;
            if (i4 != 0)
            {
              ((IE)localObject6).installFunctionsOnDocumentComplete = true;
              localObject9 = new Variant(((IE)localObject6).auto);
              IDispatch localIDispatch = ((Variant)localObject9).getDispatch();
              Variant localVariant3 = paramAnonymousOleEvent.arguments[0];
              int i11 = localVariant3.getByRef();
              if (i11 != 0)
                COM.MoveMemory(i11, new int[] { localIDispatch.getAddress() }, OS.PTR_SIZEOF);
            }
            if (((WindowEvent)localObject4).required)
              COM.MoveMemory(i, new short[] { i4 != 0 ? 0 : -1 }, 2);
            break;
          case 256:
            localObject1 = paramAnonymousOleEvent.arguments[0];
            IE.this.menuBar = ((Variant)localObject1).getBoolean();
            break;
          case 257:
            localObject1 = paramAnonymousOleEvent.arguments[0];
            IE.this.statusBar = ((Variant)localObject1).getBoolean();
            break;
          case 255:
            localObject1 = paramAnonymousOleEvent.arguments[0];
            IE.this.toolBar = ((Variant)localObject1).getBoolean();
            if (!IE.this.toolBar)
            {
              IE.this.addressBar = false;
              IE.this.menuBar = false;
            }
            break;
          case 254:
            localObject1 = paramAnonymousOleEvent.arguments[0];
            boolean bool2 = ((Variant)localObject1).getBoolean();
            localObject4 = new WindowEvent(IE.this.browser);
            ((WindowEvent)localObject4).display = IE.this.browser.getDisplay();
            ((WindowEvent)localObject4).widget = IE.this.browser;
            if (bool2)
            {
              if (IE.this.addressBar)
              {
                localObject6 = IE.this.auto.getIDsOfNames(new String[] { "AddressBar" });
                localObject8 = IE.this.auto.getProperty(localObject6[0]);
                if (localObject8 != null)
                {
                  if (((Variant)localObject8).getType() == 11)
                    IE.this.addressBar = ((Variant)localObject8).getBoolean();
                  ((Variant)localObject8).dispose();
                }
              }
              ((WindowEvent)localObject4).addressBar = IE.this.addressBar;
              ((WindowEvent)localObject4).menuBar = IE.this.menuBar;
              ((WindowEvent)localObject4).statusBar = IE.this.statusBar;
              ((WindowEvent)localObject4).toolBar = IE.this.toolBar;
              ((WindowEvent)localObject4).location = IE.this.location;
              ((WindowEvent)localObject4).size = IE.this.size;
              for (i3 = 0; i3 < IE.this.visibilityWindowListeners.length; i3++)
                IE.this.visibilityWindowListeners[i3].show((WindowEvent)localObject4);
              IE.this.location = null;
              IE.this.size = null;
            }
            else
            {
              for (i3 = 0; i3 < IE.this.visibilityWindowListeners.length; i3++)
                IE.this.visibilityWindowListeners[i3].hide((WindowEvent)localObject4);
            }
            break;
          case 108:
            if ((goto 3388) && (!IE.this.performingInitialNavigate))
            {
              localObject1 = paramAnonymousOleEvent.arguments[0];
              int j = ((Variant)localObject1).getType() != 3 ? 0 : ((Variant)localObject1).getInt();
              localObject4 = paramAnonymousOleEvent.arguments[1];
              i3 = ((Variant)localObject4).getType() != 3 ? 0 : ((Variant)localObject4).getInt();
              localObject8 = new ProgressEvent(IE.this.browser);
              ((ProgressEvent)localObject8).display = IE.this.browser.getDisplay();
              ((ProgressEvent)localObject8).widget = IE.this.browser;
              ((ProgressEvent)localObject8).current = j;
              ((ProgressEvent)localObject8).total = i3;
              if (j != -1)
                for (int i6 = 0; i6 < IE.this.progressListeners.length; i6++)
                  IE.this.progressListeners[i6].changed((ProgressEvent)localObject8);
            }
            break;
          case 102:
            if ((goto 3388) && (!IE.this.performingInitialNavigate))
            {
              localObject1 = paramAnonymousOleEvent.arguments[0];
              if (((Variant)localObject1).getType() == 8)
              {
                str1 = ((Variant)localObject1).getString();
                localObject4 = new StatusTextEvent(IE.this.browser);
                ((StatusTextEvent)localObject4).display = IE.this.browser.getDisplay();
                ((StatusTextEvent)localObject4).widget = IE.this.browser;
                ((StatusTextEvent)localObject4).text = str1;
                for (i3 = 0; i3 < IE.this.statusTextListeners.length; i3++)
                  IE.this.statusTextListeners[i3].changed((StatusTextEvent)localObject4);
              }
            }
            break;
          case 113:
            if ((goto 3388) && (!IE.this.performingInitialNavigate))
            {
              localObject1 = paramAnonymousOleEvent.arguments[0];
              if (((Variant)localObject1).getType() == 8)
              {
                str1 = ((Variant)localObject1).getString();
                localObject4 = new TitleEvent(IE.this.browser);
                ((TitleEvent)localObject4).display = IE.this.browser.getDisplay();
                ((TitleEvent)localObject4).widget = IE.this.browser;
                ((TitleEvent)localObject4).title = str1;
                for (i3 = 0; i3 < IE.this.titleListeners.length; i3++)
                  IE.this.titleListeners[i3].changed((TitleEvent)localObject4);
              }
            }
            break;
          case 263:
            IE.this.browser.getDisplay().asyncExec(new IE.9(this));
            localObject1 = paramAnonymousOleEvent.arguments[1];
            k = ((Variant)localObject1).getByRef();
            localObject4 = paramAnonymousOleEvent.arguments[0];
            boolean bool3 = ((Variant)localObject4).getBoolean();
            COM.MoveMemory(k, new short[] { bool3 ? 0 : -1 }, 2);
            break;
          case 267:
            if (IE.this.size == null)
              IE.this.size = new Point(0, 0);
            localObject1 = paramAnonymousOleEvent.arguments[0];
            IE.this.size.y = ((Variant)localObject1).getInt();
            break;
          case 264:
            if (IE.this.location == null)
              IE.this.location = new Point(0, 0);
            localObject1 = paramAnonymousOleEvent.arguments[0];
            IE.this.location.x = ((Variant)localObject1).getInt();
            break;
          case 265:
            if (IE.this.location == null)
              IE.this.location = new Point(0, 0);
            localObject1 = paramAnonymousOleEvent.arguments[0];
            IE.this.location.y = ((Variant)localObject1).getInt();
            break;
          case 266:
            if (IE.this.size == null)
              IE.this.size = new Point(0, 0);
            localObject1 = paramAnonymousOleEvent.arguments[0];
            IE.this.size.x = ((Variant)localObject1).getInt();
          }
        }
        Object localObject1 = paramAnonymousOleEvent.arguments;
        for (int k = 0; k < localObject1.length; k++)
          localObject1[k].dispose();
      }
    };
    this.site.addEventListener(250, local6);
    this.site.addEventListener(105, local6);
    this.site.addEventListener(259, local6);
    this.site.addEventListener(104, local6);
    this.site.addEventListener(252, local6);
    this.site.addEventListener(271, local6);
    this.site.addEventListener(251, local6);
    this.site.addEventListener(256, local6);
    this.site.addEventListener(257, local6);
    this.site.addEventListener(255, local6);
    this.site.addEventListener(254, local6);
    this.site.addEventListener(108, local6);
    this.site.addEventListener(102, local6);
    this.site.addEventListener(113, local6);
    this.site.addEventListener(263, local6);
    this.site.addEventListener(267, local6);
    this.site.addEventListener(264, local6);
    this.site.addEventListener(265, local6);
    this.site.addEventListener(266, local6);
    Variant localVariant = new Variant(true);
    this.auto.setProperty(552, localVariant);
    localVariant.dispose();
    localVariant = new Variant(false);
    int[] arrayOfInt = this.auto.getIDsOfNames(new String[] { "RegisterAsDropTarget" });
    if (arrayOfInt != null)
      this.auto.setProperty(arrayOfInt[0], localVariant);
    localVariant.dispose();
    return true;
  }

  public boolean back()
  {
    if (!this.back)
      return false;
    int[] arrayOfInt = this.auto.getIDsOfNames(new String[] { "GoBack" });
    Variant localVariant = this.auto.invoke(arrayOfInt[0]);
    return (localVariant != null) && (localVariant.getType() == 0);
  }

  public boolean close()
  {
    boolean bool = true;
    int[] arrayOfInt = this.auto.getIDsOfNames(new String[] { "Document" });
    int i = arrayOfInt[0];
    Variant localVariant = this.auto.getProperty(i);
    if ((localVariant == null) || (localVariant.getType() == 0))
    {
      if (localVariant != null)
        localVariant.dispose();
    }
    else
    {
      OleAutomation localOleAutomation1 = localVariant.getAutomation();
      localVariant.dispose();
      arrayOfInt = localOleAutomation1.getIDsOfNames(new String[] { "parentWindow" });
      if (arrayOfInt != null)
      {
        i = arrayOfInt[0];
        localVariant = localOleAutomation1.getProperty(i);
        if ((localVariant == null) || (localVariant.getType() == 0))
        {
          if (localVariant != null)
            localVariant.dispose();
        }
        else
        {
          OleAutomation localOleAutomation2 = localVariant.getAutomation();
          localVariant.dispose();
          arrayOfInt = localOleAutomation2.getIDsOfNames(new String[] { "location" });
          i = arrayOfInt[0];
          localVariant = localOleAutomation2.getProperty(i);
          if ((localVariant == null) || (localVariant.getType() == 0))
          {
            if (localVariant != null)
              localVariant.dispose();
          }
          else
          {
            OleAutomation localOleAutomation3 = localVariant.getAutomation();
            localVariant.dispose();
            LocationListener[] arrayOfLocationListener = this.locationListeners;
            this.locationListeners = new LocationListener[0];
            arrayOfInt = localOleAutomation3.getIDsOfNames(new String[] { "replace" });
            i = arrayOfInt[0];
            Variant[] arrayOfVariant = { new Variant("about:blank") };
            localVariant = localOleAutomation3.invoke(i, arrayOfVariant);
            if (localVariant == null)
              bool = false;
            else
              localVariant.dispose();
            arrayOfVariant[0].dispose();
            this.locationListeners = arrayOfLocationListener;
            localOleAutomation3.dispose();
          }
          localOleAutomation2.dispose();
        }
      }
      localOleAutomation1.dispose();
    }
    return bool;
  }

  static Variant createSafeArray(String paramString)
  {
    byte[] arrayOfByte = paramString.getBytes();
    int i = arrayOfByte.length;
    int j = OS.GlobalAlloc(64, i);
    C.memmove(j, arrayOfByte, i);
    int k = i;
    int m = OS.GlobalAlloc(64, SAFEARRAY.sizeof);
    SAFEARRAY localSAFEARRAY = new SAFEARRAY();
    localSAFEARRAY.cDims = 1;
    localSAFEARRAY.fFeatures = 144;
    localSAFEARRAY.cbElements = 1;
    localSAFEARRAY.pvData = j;
    SAFEARRAYBOUND localSAFEARRAYBOUND = new SAFEARRAYBOUND();
    localSAFEARRAY.rgsabound = localSAFEARRAYBOUND;
    localSAFEARRAYBOUND.cElements = k;
    OS.MoveMemory(m, localSAFEARRAY, SAFEARRAY.sizeof);
    int n = OS.GlobalAlloc(64, Variant.sizeof);
    int i1 = 8209;
    OS.MoveMemory(n, new short[] { i1 }, 2);
    OS.MoveMemory(n + 8, new int[] { m }, C.PTR_SIZEOF);
    return new Variant(n, (short)16396);
  }

  public boolean execute(String paramString)
  {
    int[] arrayOfInt1 = this.auto.getIDsOfNames(new String[] { "Document" });
    int i = arrayOfInt1[0];
    Variant localVariant = this.auto.getProperty(i);
    if ((localVariant == null) || (localVariant.getType() == 0))
    {
      if (localVariant != null)
        localVariant.dispose();
      return false;
    }
    OleAutomation localOleAutomation1 = localVariant.getAutomation();
    localVariant.dispose();
    arrayOfInt1 = localOleAutomation1.getIDsOfNames(new String[] { "parentWindow" });
    if (arrayOfInt1 == null)
    {
      localOleAutomation1.dispose();
      return false;
    }
    i = arrayOfInt1[0];
    localVariant = localOleAutomation1.getProperty(i);
    OleAutomation localOleAutomation2 = localVariant.getAutomation();
    localVariant.dispose();
    localOleAutomation1.dispose();
    arrayOfInt1 = localOleAutomation2.getIDsOfNames(new String[] { "execScript", "code" });
    Variant[] arrayOfVariant = new Variant[1];
    arrayOfVariant[0] = new Variant(paramString);
    int[] arrayOfInt2 = new int[1];
    arrayOfInt2[0] = arrayOfInt1[1];
    localVariant = localOleAutomation2.invoke(arrayOfInt1[0], arrayOfVariant, arrayOfInt2);
    arrayOfVariant[0].dispose();
    localOleAutomation2.dispose();
    if (localVariant == null)
      return false;
    localVariant.dispose();
    return true;
  }

  public boolean forward()
  {
    if (!this.forward)
      return false;
    int[] arrayOfInt = this.auto.getIDsOfNames(new String[] { "GoForward" });
    Variant localVariant = this.auto.invoke(arrayOfInt[0]);
    return (localVariant != null) && (localVariant.getType() == 0);
  }

  public String getBrowserType()
  {
    return "ie";
  }

  String getDeleteFunctionString(String paramString)
  {
    return "window." + paramString + "=undefined";
  }

  public String getText()
  {
    int[] arrayOfInt = this.auto.getIDsOfNames(new String[] { "Document" });
    Variant localVariant = this.auto.getProperty(arrayOfInt[0]);
    if ((localVariant == null) || (localVariant.getType() == 0))
    {
      if (localVariant != null)
        localVariant.dispose();
      return "";
    }
    OleAutomation localOleAutomation1 = localVariant.getAutomation();
    localVariant.dispose();
    arrayOfInt = localOleAutomation1.getIDsOfNames(new String[] { "documentElement" });
    if (arrayOfInt == null)
    {
      localOleAutomation1.dispose();
      return "";
    }
    localVariant = localOleAutomation1.getProperty(arrayOfInt[0]);
    localOleAutomation1.dispose();
    if ((localVariant == null) || (localVariant.getType() == 0))
    {
      if (localVariant != null)
        localVariant.dispose();
      return "";
    }
    OleAutomation localOleAutomation2 = localVariant.getAutomation();
    localVariant.dispose();
    arrayOfInt = localOleAutomation2.getIDsOfNames(new String[] { "outerHTML" });
    localVariant = localOleAutomation2.getProperty(arrayOfInt[0]);
    localOleAutomation2.dispose();
    if ((localVariant == null) || (localVariant.getType() == 0))
    {
      if (localVariant != null)
        localVariant.dispose();
      return "";
    }
    String str = localVariant.getString();
    localVariant.dispose();
    return str;
  }

  public String getUrl()
  {
    String str = _getUrl();
    return str.length() != 0 ? str : "about:blank";
  }

  String _getUrl()
  {
    int[] arrayOfInt = this.auto.getIDsOfNames(new String[] { "LocationURL" });
    Variant localVariant = this.auto.getProperty(arrayOfInt[0]);
    if ((localVariant == null) || (localVariant.getType() != 8))
      return "";
    String str = localVariant.getString();
    localVariant.dispose();
    return str;
  }

  public boolean isBackEnabled()
  {
    return this.back;
  }

  public boolean isForwardEnabled()
  {
    return this.forward;
  }

  public boolean isFocusControl()
  {
    return (this.site.isFocusControl()) || (this.frame.isFocusControl());
  }

  boolean navigate(String paramString1, String paramString2, String[] paramArrayOfString, boolean paramBoolean)
  {
    int i = 1;
    if (paramString2 != null)
      i++;
    if (paramArrayOfString != null)
      i++;
    Variant[] arrayOfVariant = new Variant[i];
    int[] arrayOfInt1 = new int[i];
    int[] arrayOfInt2 = this.auto.getIDsOfNames(new String[] { "Navigate", "URL", "PostData", "Headers" });
    int j = 0;
    arrayOfVariant[j] = new Variant(paramString1);
    arrayOfInt1[(j++)] = arrayOfInt2[1];
    if (paramString2 != null)
    {
      arrayOfVariant[j] = createSafeArray(paramString2);
      arrayOfInt1[(j++)] = arrayOfInt2[2];
    }
    int k;
    if (paramArrayOfString != null)
    {
      StringBuffer localStringBuffer = new StringBuffer();
      for (k = 0; k < paramArrayOfString.length; k++)
      {
        String str1 = paramArrayOfString[k];
        if (str1 != null)
        {
          int m = str1.indexOf(':');
          if (m != -1)
          {
            String str2 = str1.substring(0, m).trim();
            String str3 = str1.substring(m + 1).trim();
            if ((str2.length() > 0) && (str3.length() > 0))
            {
              localStringBuffer.append(str2);
              localStringBuffer.append(':');
              localStringBuffer.append(str3);
              localStringBuffer.append("\r\n");
            }
          }
        }
      }
      arrayOfVariant[j] = new Variant(localStringBuffer.toString());
      arrayOfInt1[(j++)] = arrayOfInt2[3];
    }
    boolean bool1 = false;
    if ((paramBoolean) && (!OS.IsWinCE) && (IEVersion >= 7))
    {
      k = OS.CoInternetIsFeatureEnabled(21, 2);
      bool1 = k == 0;
      OS.CoInternetSetFeatureEnabled(21, 2, true);
    }
    Variant localVariant = this.auto.invoke(arrayOfInt2[0], arrayOfVariant, arrayOfInt1);
    if ((paramBoolean) && (!OS.IsWinCE) && (IEVersion >= 7))
      OS.CoInternetSetFeatureEnabled(21, 2, bool1);
    for (boolean bool2 = false; bool2 < i; bool2++)
      arrayOfVariant[bool2].dispose();
    if (localVariant == null)
      return false;
    bool2 = localVariant.getType() == 0;
    localVariant.dispose();
    return bool2;
  }

  public void refresh()
  {
    this.uncRedirect = null;
    String str = _getUrl();
    int i = str.lastIndexOf('.');
    if (i != -1)
    {
      localObject = str.substring(i);
      if (((String)localObject).equalsIgnoreCase(".pdf"))
      {
        PDFCount += 1;
        if (PDFCount > 20)
          COM.FreeUnusedLibraries = false;
      }
    }
    this.isRefresh = true;
    Object localObject = this.auto.getIDsOfNames(new String[] { "Refresh" });
    this.auto.invoke(localObject[0]);
  }

  void setHTML(String paramString)
  {
    int i = paramString.length();
    char[] arrayOfChar = new char[i];
    paramString.getChars(0, i, arrayOfChar, 0);
    int j = OS.WideCharToMultiByte(65001, 0, arrayOfChar, i, null, 0, null, null);
    byte[] arrayOfByte = { -17, -69, -65 };
    int k = OS.GlobalAlloc(64, arrayOfByte.length + j);
    if (k != 0)
    {
      OS.MoveMemory(k, arrayOfByte, arrayOfByte.length);
      OS.WideCharToMultiByte(65001, 0, arrayOfChar, i, k + arrayOfByte.length, j, null, null);
      int[] arrayOfInt1 = new int[1];
      if (OS.CreateStreamOnHGlobal(k, true, arrayOfInt1) == 0)
      {
        int[] arrayOfInt2 = this.auto.getIDsOfNames(new String[] { "Document" });
        Variant localVariant = this.auto.getProperty(arrayOfInt2[0]);
        IDispatch localIDispatch = localVariant.getDispatch();
        int[] arrayOfInt3 = new int[1];
        int m = localIDispatch.QueryInterface(COM.IIDIPersistStreamInit, arrayOfInt3);
        if (m == 0)
        {
          localObject = new IPersistStreamInit(arrayOfInt3[0]);
          if (((IPersistStreamInit)localObject).InitNew() == 0)
            ((IPersistStreamInit)localObject).Load(arrayOfInt1[0]);
          ((IPersistStreamInit)localObject).Release();
        }
        localVariant.dispose();
        Object localObject = new IUnknown(arrayOfInt1[0]);
        ((IUnknown)localObject).Release();
      }
      else
      {
        OS.GlobalFree(k);
      }
    }
  }

  public boolean setText(String paramString, boolean paramBoolean)
  {
    if (this.performingInitialNavigate)
    {
      this.pendingText = new Object[] { paramString, new Boolean(paramBoolean) };
      this.pendingUrl = null;
      return true;
    }
    int i = this.html != null ? 1 : 0;
    this.html = paramString;
    this.untrustedText = (!paramBoolean);
    if (i != 0)
      return true;
    if (_getUrl().length() != 0)
    {
      arrayOfInt1 = this.auto.getIDsOfNames(new String[] { "ReadyState" });
      localObject = this.auto.getProperty(arrayOfInt1[0]);
      if (localObject == null)
        return false;
      this.delaySetText = (((Variant)localObject).getInt() != 4);
      ((Variant)localObject).dispose();
      arrayOfInt1 = this.auto.getIDsOfNames(new String[] { "Stop" });
      this.auto.invoke(arrayOfInt1[0]);
    }
    int[] arrayOfInt1 = this.auto.getIDsOfNames(new String[] { "Navigate", "URL" });
    Object localObject = new Variant[1];
    localObject[0] = new Variant("about:blank");
    int[] arrayOfInt2 = new int[1];
    arrayOfInt2[0] = arrayOfInt1[1];
    boolean bool1 = false;
    if ((!OS.IsWinCE) && (IEVersion >= 7))
    {
      int j = OS.CoInternetIsFeatureEnabled(21, 2);
      bool1 = j == 0;
      OS.CoInternetSetFeatureEnabled(21, 2, true);
    }
    Variant localVariant = this.auto.invoke(arrayOfInt1[0], (Variant[])localObject, arrayOfInt2);
    if ((!OS.IsWinCE) && (IEVersion >= 7))
      OS.CoInternetSetFeatureEnabled(21, 2, bool1);
    localObject[0].dispose();
    if (localVariant == null)
      return false;
    boolean bool2 = localVariant.getType() == 0;
    localVariant.dispose();
    return bool2;
  }

  public boolean setUrl(String paramString1, String paramString2, String[] paramArrayOfString)
  {
    this.html = (this.uncRedirect = null);
    if (_getUrl().length() == 0)
    {
      this.pendingText = null;
      this.pendingUrl = new Object[] { paramString1, paramString2, paramArrayOfString };
      this.performingInitialNavigate = true;
      navigate("about:blank", null, null, true);
      return true;
    }
    if (paramString1.endsWith(".xml"))
    {
      int[] arrayOfInt = this.auto.getIDsOfNames(new String[] { "Stop" });
      this.auto.invoke(arrayOfInt[0]);
    }
    return navigate(paramString1, paramString2, paramArrayOfString, false);
  }

  public void stop()
  {
    if (this.performingInitialNavigate)
    {
      this.pendingText = (this.pendingUrl = null);
      return;
    }
    if (_getUrl().length() == 0)
      return;
    this.uncRedirect = null;
    int[] arrayOfInt = this.auto.getIDsOfNames(new String[] { "Stop" });
    this.auto.invoke(arrayOfInt[0]);
  }

  boolean translateMnemonics()
  {
    return false;
  }

  void handleDOMEvent(OleEvent paramOleEvent)
  {
    if ((paramOleEvent.arguments == null) || (paramOleEvent.arguments.length == 0))
      return;
    Variant localVariant1 = paramOleEvent.arguments[0];
    OleAutomation localOleAutomation = localVariant1.getAutomation();
    int[] arrayOfInt = localOleAutomation.getIDsOfNames(new String[] { "type" });
    int i = arrayOfInt[0];
    Variant localVariant2 = localOleAutomation.getProperty(i);
    String str = localVariant2.getString();
    localVariant2.dispose();
    Object localObject1;
    if (str.equals("keydown"))
    {
      arrayOfInt = localOleAutomation.getIDsOfNames(new String[] { "keyCode" });
      i = arrayOfInt[0];
      localVariant2 = localOleAutomation.getProperty(i);
      this.lastKeyCode = translateKey(localVariant2.getInt());
      localVariant2.dispose();
      arrayOfInt = localOleAutomation.getIDsOfNames(new String[] { "returnValue" });
      localVariant2 = localOleAutomation.getProperty(arrayOfInt[0]);
      j = (localVariant2 != null) && (localVariant2.getType() == 11) && (!localVariant2.getBoolean()) ? 1 : 0;
      localVariant2.dispose();
      localObject1 = new MSG();
      int m = 0x2 | (j != 0 ? 1 : 0);
      if (OS.PeekMessage((MSG)localObject1, this.frame.handle, 258, 258, m))
      {
        localOleAutomation.dispose();
        return;
      }
      if (j != 0)
      {
        localOleAutomation.dispose();
        return;
      }
      arrayOfInt = localOleAutomation.getIDsOfNames(new String[] { "repeat" });
      i = arrayOfInt[0];
      localVariant2 = localOleAutomation.getProperty(i);
      boolean bool = localVariant2.getBoolean();
      localVariant2.dispose();
      if (bool)
      {
        localOleAutomation.dispose();
        return;
      }
      int i2 = 0;
      arrayOfInt = localOleAutomation.getIDsOfNames(new String[] { "altKey" });
      i = arrayOfInt[0];
      localVariant2 = localOleAutomation.getProperty(i);
      if (localVariant2.getBoolean())
        i2 |= 65536;
      localVariant2.dispose();
      arrayOfInt = localOleAutomation.getIDsOfNames(new String[] { "ctrlKey" });
      i = arrayOfInt[0];
      localVariant2 = localOleAutomation.getProperty(i);
      if (localVariant2.getBoolean())
        i2 |= 262144;
      localVariant2.dispose();
      arrayOfInt = localOleAutomation.getIDsOfNames(new String[] { "shiftKey" });
      i = arrayOfInt[0];
      localVariant2 = localOleAutomation.getProperty(i);
      if (localVariant2.getBoolean())
        i2 |= 131072;
      localVariant2.dispose();
      Event localEvent2 = new Event();
      localEvent2.widget = this.browser;
      localEvent2.type = 1;
      localEvent2.keyCode = this.lastKeyCode;
      localEvent2.stateMask = i2;
      localEvent2.stateMask &= (this.lastKeyCode ^ 0xFFFFFFFF);
      switch (this.lastKeyCode)
      {
      case 8:
        this.lastCharCode = (localEvent2.character = 8);
        break;
      case 13:
        this.lastCharCode = (localEvent2.character = 13);
        break;
      case 127:
        this.lastCharCode = (localEvent2.character = 127);
        break;
      case 9:
        this.lastCharCode = (localEvent2.character = 9);
      }
      if (!sendKeyEvent(localEvent2))
      {
        arrayOfInt = localOleAutomation.getIDsOfNames(new String[] { "returnValue" });
        i = arrayOfInt[0];
        Variant localVariant4 = new Variant(false);
        localOleAutomation.setProperty(i, localVariant4);
        localVariant4.dispose();
      }
      if (this.lastKeyCode == 16777230)
        this.isRefresh = true;
      localOleAutomation.dispose();
      return;
    }
    Object localObject2;
    if (str.equals("keypress"))
    {
      j = 0;
      arrayOfInt = localOleAutomation.getIDsOfNames(new String[] { "ctrlKey" });
      i = arrayOfInt[0];
      localVariant2 = localOleAutomation.getProperty(i);
      if (localVariant2.getBoolean())
        j |= 262144;
      localVariant2.dispose();
      arrayOfInt = localOleAutomation.getIDsOfNames(new String[] { "shiftKey" });
      i = arrayOfInt[0];
      localVariant2 = localOleAutomation.getProperty(i);
      if (localVariant2.getBoolean())
        j |= 131072;
      localVariant2.dispose();
      arrayOfInt = localOleAutomation.getIDsOfNames(new String[] { "altKey" });
      i = arrayOfInt[0];
      localVariant2 = localOleAutomation.getProperty(i);
      if (localVariant2.getBoolean())
        j |= 65536;
      localVariant2.dispose();
      arrayOfInt = localOleAutomation.getIDsOfNames(new String[] { "keyCode" });
      i = arrayOfInt[0];
      localVariant2 = localOleAutomation.getProperty(i);
      this.lastCharCode = localVariant2.getInt();
      localVariant2.dispose();
      if ((this.lastCharCode == 13) || (this.lastCharCode == 10))
      {
        localOleAutomation.dispose();
        return;
      }
      localObject1 = new Event();
      ((Event)localObject1).widget = this.browser;
      ((Event)localObject1).type = 1;
      ((Event)localObject1).keyCode = this.lastKeyCode;
      ((Event)localObject1).character = ((char)this.lastCharCode);
      ((Event)localObject1).stateMask = j;
      if (!sendKeyEvent((Event)localObject1))
      {
        arrayOfInt = localOleAutomation.getIDsOfNames(new String[] { "returnValue" });
        i = arrayOfInt[0];
        localObject2 = new Variant(false);
        localOleAutomation.setProperty(i, (Variant)localObject2);
        ((Variant)localObject2).dispose();
      }
      localOleAutomation.dispose();
      return;
    }
    if (str.equals("keyup"))
    {
      arrayOfInt = localOleAutomation.getIDsOfNames(new String[] { "keyCode" });
      i = arrayOfInt[0];
      localVariant2 = localOleAutomation.getProperty(i);
      j = translateKey(localVariant2.getInt());
      localVariant2.dispose();
      if (j == 0)
      {
        this.lastKeyCode = (this.lastCharCode = 0);
        localOleAutomation.dispose();
        return;
      }
      if (j != this.lastKeyCode)
      {
        this.lastKeyCode = j;
        this.lastCharCode = 0;
      }
      int k = 0;
      arrayOfInt = localOleAutomation.getIDsOfNames(new String[] { "ctrlKey" });
      i = arrayOfInt[0];
      localVariant2 = localOleAutomation.getProperty(i);
      if (localVariant2.getBoolean())
        k |= 262144;
      localVariant2.dispose();
      arrayOfInt = localOleAutomation.getIDsOfNames(new String[] { "altKey" });
      i = arrayOfInt[0];
      localVariant2 = localOleAutomation.getProperty(i);
      if (localVariant2.getBoolean())
        k |= 65536;
      localVariant2.dispose();
      arrayOfInt = localOleAutomation.getIDsOfNames(new String[] { "shiftKey" });
      i = arrayOfInt[0];
      localVariant2 = localOleAutomation.getProperty(i);
      if (localVariant2.getBoolean())
        k |= 131072;
      localVariant2.dispose();
      localObject2 = new Event();
      ((Event)localObject2).widget = this.browser;
      ((Event)localObject2).type = 2;
      ((Event)localObject2).keyCode = this.lastKeyCode;
      ((Event)localObject2).character = ((char)this.lastCharCode);
      ((Event)localObject2).stateMask = k;
      switch (this.lastKeyCode)
      {
      case 65536:
      case 131072:
      case 262144:
      case 4194304:
        localObject2.stateMask |= this.lastKeyCode;
      }
      this.browser.notifyListeners(((Event)localObject2).type, (Event)localObject2);
      if (!((Event)localObject2).doit)
      {
        arrayOfInt = localOleAutomation.getIDsOfNames(new String[] { "returnValue" });
        i = arrayOfInt[0];
        Variant localVariant3 = new Variant(false);
        localOleAutomation.setProperty(i, localVariant3);
        localVariant3.dispose();
      }
      this.lastKeyCode = (this.lastCharCode = 0);
      localOleAutomation.dispose();
      return;
    }
    if (str.equals("mouseover"))
    {
      arrayOfInt = localOleAutomation.getIDsOfNames(new String[] { "fromElement" });
      i = arrayOfInt[0];
      localVariant2 = localOleAutomation.getProperty(i);
      j = localVariant2.getType() != 0 ? 1 : 0;
      localVariant2.dispose();
      if (j != 0)
      {
        localOleAutomation.dispose();
        return;
      }
    }
    if (str.equals("mouseout"))
    {
      arrayOfInt = localOleAutomation.getIDsOfNames(new String[] { "toElement" });
      i = arrayOfInt[0];
      localVariant2 = localOleAutomation.getProperty(i);
      j = localVariant2.getType() != 0 ? 1 : 0;
      localVariant2.dispose();
      if (j != 0)
      {
        localOleAutomation.dispose();
        return;
      }
    }
    int j = 0;
    Event localEvent1 = new Event();
    localEvent1.widget = this.browser;
    arrayOfInt = localOleAutomation.getIDsOfNames(new String[] { "screenX" });
    i = arrayOfInt[0];
    localVariant2 = localOleAutomation.getProperty(i);
    int n = localVariant2.getInt();
    localVariant2.dispose();
    arrayOfInt = localOleAutomation.getIDsOfNames(new String[] { "screenY" });
    i = arrayOfInt[0];
    localVariant2 = localOleAutomation.getProperty(i);
    int i1 = localVariant2.getInt();
    localVariant2.dispose();
    Point localPoint = new Point(n, i1);
    localPoint = this.browser.getDisplay().map(null, this.browser, localPoint);
    localEvent1.x = localPoint.x;
    localEvent1.y = localPoint.y;
    arrayOfInt = localOleAutomation.getIDsOfNames(new String[] { "ctrlKey" });
    i = arrayOfInt[0];
    localVariant2 = localOleAutomation.getProperty(i);
    if (localVariant2.getBoolean())
      j |= 262144;
    localVariant2.dispose();
    arrayOfInt = localOleAutomation.getIDsOfNames(new String[] { "altKey" });
    i = arrayOfInt[0];
    localVariant2 = localOleAutomation.getProperty(i);
    if (localVariant2.getBoolean())
      j |= 65536;
    localVariant2.dispose();
    arrayOfInt = localOleAutomation.getIDsOfNames(new String[] { "shiftKey" });
    i = arrayOfInt[0];
    localVariant2 = localOleAutomation.getProperty(i);
    if (localVariant2.getBoolean())
      j |= 131072;
    localVariant2.dispose();
    localEvent1.stateMask = j;
    arrayOfInt = localOleAutomation.getIDsOfNames(new String[] { "button" });
    i = arrayOfInt[0];
    localVariant2 = localOleAutomation.getProperty(i);
    int i3 = localVariant2.getInt();
    localVariant2.dispose();
    switch (i3)
    {
    case 1:
      i3 = 1;
      break;
    case 2:
      i3 = 3;
      break;
    case 4:
      i3 = 2;
    case 3:
    }
    if (str.equals("mousedown"))
    {
      localEvent1.type = 3;
      localEvent1.button = i3;
      localEvent1.count = 1;
    }
    else
    {
      if ((str.equals("mouseup")) || (str.equals("dragend")))
      {
        localEvent1.type = 4;
        localEvent1.button = (i3 != 0 ? i3 : 1);
        localEvent1.count = 1;
      }
      switch (localEvent1.button)
      {
      case 1:
        localEvent1.stateMask |= 524288;
        break;
      case 2:
        localEvent1.stateMask |= 1048576;
        break;
      case 3:
        localEvent1.stateMask |= 2097152;
        break;
      case 4:
        localEvent1.stateMask |= 8388608;
        break;
      case 5:
        localEvent1.stateMask |= 33554432;
      default:
        break;
        if (str.equals("mousewheel"))
        {
          localEvent1.type = 37;
          arrayOfInt = localOleAutomation.getIDsOfNames(new String[] { "wheelDelta" });
          i = arrayOfInt[0];
          localVariant2 = localOleAutomation.getProperty(i);
          localEvent1.count = (localVariant2.getInt() / 120 * 3);
          localVariant2.dispose();
        }
        else if (str.equals("mousemove"))
        {
          if ((localEvent1.x == this.lastMouseMoveX) && (localEvent1.y == this.lastMouseMoveY))
            return;
          localEvent1.type = 5;
          this.lastMouseMoveX = localEvent1.x;
          this.lastMouseMoveY = localEvent1.y;
        }
        else if (str.equals("mouseover"))
        {
          localEvent1.type = 6;
        }
        else if (str.equals("mouseout"))
        {
          localEvent1.type = 7;
        }
        else if (str.equals("dragstart"))
        {
          localEvent1.type = 29;
          localEvent1.button = 1;
          localEvent1.stateMask |= 524288;
        }
        break;
      }
    }
    localOleAutomation.dispose();
    this.browser.notifyListeners(localEvent1.type, localEvent1);
    if (str.equals("dblclick"))
    {
      localEvent1 = new Event();
      localEvent1.widget = this.browser;
      localEvent1.type = 8;
      localEvent1.x = localPoint.x;
      localEvent1.y = localPoint.y;
      localEvent1.stateMask = j;
      localEvent1.type = 8;
      localEvent1.button = 1;
      localEvent1.count = 2;
      this.browser.notifyListeners(localEvent1.type, localEvent1);
    }
  }

  void hookDOMListeners(OleAutomation paramOleAutomation, boolean paramBoolean)
  {
    int[] arrayOfInt = paramOleAutomation.getIDsOfNames(new String[] { "Document" });
    int i = arrayOfInt[0];
    Variant localVariant = paramOleAutomation.getProperty(i);
    if (localVariant == null)
      return;
    if (localVariant.getType() == 0)
    {
      localVariant.dispose();
      return;
    }
    OleAutomation localOleAutomation = localVariant.getAutomation();
    localVariant.dispose();
    unhookDOMListeners(new OleAutomation[] { localOleAutomation });
    this.site.addEventListener(localOleAutomation, "{3050F613-98B5-11CF-BB82-00AA00BDCE0B}", -602, this.domListener);
    this.site.addEventListener(localOleAutomation, "{3050F613-98B5-11CF-BB82-00AA00BDCE0B}", -603, this.domListener);
    this.site.addEventListener(localOleAutomation, "{3050F613-98B5-11CF-BB82-00AA00BDCE0B}", -604, this.domListener);
    this.site.addEventListener(localOleAutomation, "{3050F613-98B5-11CF-BB82-00AA00BDCE0B}", -605, this.domListener);
    this.site.addEventListener(localOleAutomation, "{3050F613-98B5-11CF-BB82-00AA00BDCE0B}", -607, this.domListener);
    this.site.addEventListener(localOleAutomation, "{3050F613-98B5-11CF-BB82-00AA00BDCE0B}", 1033, this.domListener);
    this.site.addEventListener(localOleAutomation, "{3050F613-98B5-11CF-BB82-00AA00BDCE0B}", -601, this.domListener);
    this.site.addEventListener(localOleAutomation, "{3050F613-98B5-11CF-BB82-00AA00BDCE0B}", -606, this.domListener);
    this.site.addEventListener(localOleAutomation, "{3050F613-98B5-11CF-BB82-00AA00BDCE0B}", -2147418101, this.domListener);
    this.site.addEventListener(localOleAutomation, "{3050F613-98B5-11CF-BB82-00AA00BDCE0B}", -2147418091, this.domListener);
    if (paramBoolean)
    {
      this.site.addEventListener(localOleAutomation, "{3050F613-98B5-11CF-BB82-00AA00BDCE0B}", -2147418104, this.domListener);
      this.site.addEventListener(localOleAutomation, "{3050F613-98B5-11CF-BB82-00AA00BDCE0B}", -2147418103, this.domListener);
    }
    OleAutomation[] arrayOfOleAutomation = new OleAutomation[this.documents.length + 1];
    System.arraycopy(this.documents, 0, arrayOfOleAutomation, 0, this.documents.length);
    arrayOfOleAutomation[this.documents.length] = localOleAutomation;
    this.documents = arrayOfOleAutomation;
  }

  void unhookDOMListeners(OleAutomation[] paramArrayOfOleAutomation)
  {
    char[] arrayOfChar = "".toCharArray();
    GUID localGUID = new GUID();
    if (COM.IIDFromString(arrayOfChar, localGUID) == 0)
      for (int i = 0; i < paramArrayOfOleAutomation.length; i++)
      {
        OleAutomation localOleAutomation = paramArrayOfOleAutomation[i];
        this.site.removeEventListener(localOleAutomation, localGUID, -602, this.domListener);
        this.site.removeEventListener(localOleAutomation, localGUID, -603, this.domListener);
        this.site.removeEventListener(localOleAutomation, localGUID, -604, this.domListener);
        this.site.removeEventListener(localOleAutomation, localGUID, -605, this.domListener);
        this.site.removeEventListener(localOleAutomation, localGUID, -607, this.domListener);
        this.site.removeEventListener(localOleAutomation, localGUID, 1033, this.domListener);
        this.site.removeEventListener(localOleAutomation, localGUID, -601, this.domListener);
        this.site.removeEventListener(localOleAutomation, localGUID, -606, this.domListener);
        this.site.removeEventListener(localOleAutomation, localGUID, -2147418101, this.domListener);
        this.site.removeEventListener(localOleAutomation, localGUID, -2147418091, this.domListener);
        this.site.removeEventListener(localOleAutomation, localGUID, -2147418104, this.domListener);
        this.site.removeEventListener(localOleAutomation, localGUID, -2147418103, this.domListener);
      }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.browser.IE
 * JD-Core Version:    0.6.2
 */