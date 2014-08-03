package org.eclipse.swt.browser;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;

abstract class WebBrowser
{
  Browser browser;
  Hashtable functions = new Hashtable();
  AuthenticationListener[] authenticationListeners = new AuthenticationListener[0];
  CloseWindowListener[] closeWindowListeners = new CloseWindowListener[0];
  LocationListener[] locationListeners = new LocationListener[0];
  OpenWindowListener[] openWindowListeners = new OpenWindowListener[0];
  ProgressListener[] progressListeners = new ProgressListener[0];
  StatusTextListener[] statusTextListeners = new StatusTextListener[0];
  TitleListener[] titleListeners = new TitleListener[0];
  VisibilityWindowListener[] visibilityWindowListeners = new VisibilityWindowListener[0];
  boolean jsEnabledChanged;
  boolean jsEnabled = true;
  int nextFunctionIndex = 1;
  Object evaluateResult;
  static final String ERROR_ID = "org.eclipse.swt.browser.error";
  static final String EXECUTE_ID = "SWTExecuteTemporaryFunction";
  static Vector NativePendingCookies = new Vector();
  static Vector MozillaPendingCookies = new Vector();
  static String CookieName;
  static String CookieValue;
  static String CookieUrl;
  static boolean CookieResult;
  static Runnable MozillaClearSessions;
  static Runnable NativeClearSessions;
  static Runnable MozillaGetCookie;
  static Runnable NativeGetCookie;
  static Runnable MozillaSetCookie;
  static Runnable NativeSetCookie;
  static final int[][] KeyTable = { { 18, 65536 }, { 16, 131072 }, { 17, 262144 }, { 224, 4194304 }, { 65, 97 }, { 66, 98 }, { 67, 99 }, { 68, 100 }, { 69, 101 }, { 70, 102 }, { 71, 103 }, { 72, 104 }, { 73, 105 }, { 74, 106 }, { 75, 107 }, { 76, 108 }, { 77, 109 }, { 78, 110 }, { 79, 111 }, { 80, 112 }, { 81, 113 }, { 82, 114 }, { 83, 115 }, { 84, 116 }, { 85, 117 }, { 86, 118 }, { 87, 119 }, { 88, 120 }, { 89, 121 }, { 90, 122 }, { 48, 48 }, { 49, 49 }, { 50, 50 }, { 51, 51 }, { 52, 52 }, { 53, 53 }, { 54, 54 }, { 55, 55 }, { 56, 56 }, { 57, 57 }, { 32, 32 }, { 59, 59 }, { 61, 61 }, { 188, 44 }, { 190, 46 }, { 191, 47 }, { 219, 91 }, { 221, 93 }, { 222, 39 }, { 192, 96 }, { 220, 92 }, { 108, 124 }, { 37, 16777219 }, { 39, 16777220 }, { 38, 16777217 }, { 40, 16777218 }, { 45, 16777225 }, { 36, 16777223 }, { 35, 16777224 }, { 46, 127 }, { 33, 16777221 }, { 34, 16777222 }, { 8, 8 }, { 13, 13 }, { 9, 9 }, { 27, 27 }, { 12, 127 }, { 112, 16777226 }, { 113, 16777227 }, { 114, 16777228 }, { 115, 16777229 }, { 116, 16777230 }, { 117, 16777231 }, { 118, 16777232 }, { 119, 16777233 }, { 120, 16777234 }, { 121, 16777235 }, { 122, 16777236 }, { 123, 16777237 }, { 124, 16777238 }, { 125, 16777239 }, { 126, 16777240 }, { 127 }, { 128 }, { 129 }, { 130 }, { 131 }, { 132 }, { 133 }, { 134 }, { 135 }, { 96, 16777264 }, { 97, 16777265 }, { 98, 16777266 }, { 99, 16777267 }, { 100, 16777268 }, { 101, 16777269 }, { 102, 16777270 }, { 103, 16777271 }, { 104, 16777272 }, { 105, 16777273 }, { 14, 16777296 }, { 107, 16777259 }, { 109, 16777261 }, { 106, 16777258 }, { 111, 16777263 }, { 110, 16777262 }, { 20, 16777298 }, { 144, 16777299 }, { 145, 16777300 }, { 44, 16777303 }, { 6, 16777297 }, { 19, 16777301 }, { 3, 16777302 }, { 186, 59 }, { 187, 61 }, { 189, 45 } };

  public void addAuthenticationListener(AuthenticationListener paramAuthenticationListener)
  {
    AuthenticationListener[] arrayOfAuthenticationListener = new AuthenticationListener[this.authenticationListeners.length + 1];
    System.arraycopy(this.authenticationListeners, 0, arrayOfAuthenticationListener, 0, this.authenticationListeners.length);
    this.authenticationListeners = arrayOfAuthenticationListener;
    this.authenticationListeners[(this.authenticationListeners.length - 1)] = paramAuthenticationListener;
  }

  public void addCloseWindowListener(CloseWindowListener paramCloseWindowListener)
  {
    CloseWindowListener[] arrayOfCloseWindowListener = new CloseWindowListener[this.closeWindowListeners.length + 1];
    System.arraycopy(this.closeWindowListeners, 0, arrayOfCloseWindowListener, 0, this.closeWindowListeners.length);
    this.closeWindowListeners = arrayOfCloseWindowListener;
    this.closeWindowListeners[(this.closeWindowListeners.length - 1)] = paramCloseWindowListener;
  }

  public void addLocationListener(LocationListener paramLocationListener)
  {
    LocationListener[] arrayOfLocationListener = new LocationListener[this.locationListeners.length + 1];
    System.arraycopy(this.locationListeners, 0, arrayOfLocationListener, 0, this.locationListeners.length);
    this.locationListeners = arrayOfLocationListener;
    this.locationListeners[(this.locationListeners.length - 1)] = paramLocationListener;
  }

  public void addOpenWindowListener(OpenWindowListener paramOpenWindowListener)
  {
    OpenWindowListener[] arrayOfOpenWindowListener = new OpenWindowListener[this.openWindowListeners.length + 1];
    System.arraycopy(this.openWindowListeners, 0, arrayOfOpenWindowListener, 0, this.openWindowListeners.length);
    this.openWindowListeners = arrayOfOpenWindowListener;
    this.openWindowListeners[(this.openWindowListeners.length - 1)] = paramOpenWindowListener;
  }

  public void addProgressListener(ProgressListener paramProgressListener)
  {
    ProgressListener[] arrayOfProgressListener = new ProgressListener[this.progressListeners.length + 1];
    System.arraycopy(this.progressListeners, 0, arrayOfProgressListener, 0, this.progressListeners.length);
    this.progressListeners = arrayOfProgressListener;
    this.progressListeners[(this.progressListeners.length - 1)] = paramProgressListener;
  }

  public void addStatusTextListener(StatusTextListener paramStatusTextListener)
  {
    StatusTextListener[] arrayOfStatusTextListener = new StatusTextListener[this.statusTextListeners.length + 1];
    System.arraycopy(this.statusTextListeners, 0, arrayOfStatusTextListener, 0, this.statusTextListeners.length);
    this.statusTextListeners = arrayOfStatusTextListener;
    this.statusTextListeners[(this.statusTextListeners.length - 1)] = paramStatusTextListener;
  }

  public void addTitleListener(TitleListener paramTitleListener)
  {
    TitleListener[] arrayOfTitleListener = new TitleListener[this.titleListeners.length + 1];
    System.arraycopy(this.titleListeners, 0, arrayOfTitleListener, 0, this.titleListeners.length);
    this.titleListeners = arrayOfTitleListener;
    this.titleListeners[(this.titleListeners.length - 1)] = paramTitleListener;
  }

  public void addVisibilityWindowListener(VisibilityWindowListener paramVisibilityWindowListener)
  {
    VisibilityWindowListener[] arrayOfVisibilityWindowListener = new VisibilityWindowListener[this.visibilityWindowListeners.length + 1];
    System.arraycopy(this.visibilityWindowListeners, 0, arrayOfVisibilityWindowListener, 0, this.visibilityWindowListeners.length);
    this.visibilityWindowListeners = arrayOfVisibilityWindowListener;
    this.visibilityWindowListeners[(this.visibilityWindowListeners.length - 1)] = paramVisibilityWindowListener;
  }

  public abstract boolean back();

  public static void clearSessions()
  {
    if (NativeClearSessions != null)
      NativeClearSessions.run();
    if (MozillaClearSessions != null)
      MozillaClearSessions.run();
  }

  public static String GetCookie(String paramString1, String paramString2)
  {
    CookieName = paramString1;
    CookieUrl = paramString2;
    CookieValue = null;
    if (NativeGetCookie != null)
      NativeGetCookie.run();
    if ((CookieValue == null) && (MozillaGetCookie != null))
      MozillaGetCookie.run();
    String str = CookieValue;
    CookieName = WebBrowser.CookieValue = WebBrowser.CookieUrl = null;
    return str;
  }

  public static boolean SetCookie(String paramString1, String paramString2, boolean paramBoolean)
  {
    CookieValue = paramString1;
    CookieUrl = paramString2;
    CookieResult = false;
    if (NativeSetCookie != null)
      NativeSetCookie.run();
    else if ((paramBoolean) && (NativePendingCookies != null))
      NativePendingCookies.add(new String[] { paramString1, paramString2 });
    if (MozillaSetCookie != null)
      MozillaSetCookie.run();
    else if ((paramBoolean) && (MozillaPendingCookies != null))
      MozillaPendingCookies.add(new String[] { paramString1, paramString2 });
    CookieValue = WebBrowser.CookieUrl = null;
    return CookieResult;
  }

  static void SetPendingCookies(Vector paramVector)
  {
    Enumeration localEnumeration = paramVector.elements();
    while (localEnumeration.hasMoreElements())
    {
      String[] arrayOfString = (String[])localEnumeration.nextElement();
      SetCookie(arrayOfString[0], arrayOfString[1], false);
    }
  }

  public abstract boolean create(Composite paramComposite, int paramInt);

  static String CreateErrorString(String paramString)
  {
    return "org.eclipse.swt.browser.error" + paramString;
  }

  static String ExtractError(String paramString)
  {
    return paramString.substring("org.eclipse.swt.browser.error".length());
  }

  public boolean close()
  {
    return true;
  }

  public void createFunction(BrowserFunction paramBrowserFunction)
  {
    Enumeration localEnumeration = this.functions.keys();
    while (localEnumeration.hasMoreElements())
    {
      localObject = localEnumeration.nextElement();
      BrowserFunction localBrowserFunction = (BrowserFunction)this.functions.get(localObject);
      if (localBrowserFunction.name.equals(paramBrowserFunction.name))
      {
        this.functions.remove(localObject);
        break;
      }
    }
    paramBrowserFunction.index = getNextFunctionIndex();
    registerFunction(paramBrowserFunction);
    Object localObject = new StringBuffer("window.");
    ((StringBuffer)localObject).append(paramBrowserFunction.name);
    ((StringBuffer)localObject).append(" = function ");
    ((StringBuffer)localObject).append(paramBrowserFunction.name);
    ((StringBuffer)localObject).append("() {var result = window.external.callJava(");
    ((StringBuffer)localObject).append(paramBrowserFunction.index);
    ((StringBuffer)localObject).append(",Array.prototype.slice.call(arguments)); if (typeof result == 'string' && result.indexOf('");
    ((StringBuffer)localObject).append("org.eclipse.swt.browser.error");
    ((StringBuffer)localObject).append("') == 0) {var error = new Error(result.substring(");
    ((StringBuffer)localObject).append("org.eclipse.swt.browser.error".length());
    ((StringBuffer)localObject).append(")); throw error;} return result;};");
    ((StringBuffer)localObject).append("for (var i = 0; i < frames.length; i++) {try { frames[i].");
    ((StringBuffer)localObject).append(paramBrowserFunction.name);
    ((StringBuffer)localObject).append(" = window.");
    ((StringBuffer)localObject).append(paramBrowserFunction.name);
    ((StringBuffer)localObject).append(";} catch (e) {} };");
    paramBrowserFunction.functionString = ((StringBuffer)localObject).toString();
    execute(paramBrowserFunction.functionString);
  }

  void deregisterFunction(BrowserFunction paramBrowserFunction)
  {
    this.functions.remove(new Integer(paramBrowserFunction.index));
  }

  public void destroyFunction(BrowserFunction paramBrowserFunction)
  {
    String str = getDeleteFunctionString(paramBrowserFunction.name);
    StringBuffer localStringBuffer = new StringBuffer("for (var i = 0; i < frames.length; i++) {try {frames[i].eval(\"");
    localStringBuffer.append(str);
    localStringBuffer.append("\");} catch (e) {}}");
    execute(localStringBuffer.toString());
    execute(str);
    deregisterFunction(paramBrowserFunction);
  }

  public abstract boolean execute(String paramString);

  public Object evaluate(String paramString)
    throws SWTException
  {
    EvaluateFunction localEvaluateFunction = new EvaluateFunction(this.browser, "");
    int i = getNextFunctionIndex();
    localEvaluateFunction.index = i;
    localEvaluateFunction.isEvaluate = true;
    registerFunction(localEvaluateFunction);
    String str = "SWTExecuteTemporaryFunction" + i;
    StringBuffer localStringBuffer = new StringBuffer("window.");
    localStringBuffer.append(str);
    localStringBuffer.append(" = function ");
    localStringBuffer.append(str);
    localStringBuffer.append("() {\n");
    localStringBuffer.append(paramString);
    localStringBuffer.append("\n};");
    execute(localStringBuffer.toString());
    localStringBuffer = new StringBuffer("if (window.");
    localStringBuffer.append(str);
    localStringBuffer.append(" == undefined) {window.external.callJava(");
    localStringBuffer.append(i);
    localStringBuffer.append(", ['");
    localStringBuffer.append("org.eclipse.swt.browser.error");
    localStringBuffer.append("']);} else {try {var result = ");
    localStringBuffer.append(str);
    localStringBuffer.append("(); window.external.callJava(");
    localStringBuffer.append(i);
    localStringBuffer.append(", [result]);} catch (e) {window.external.callJava(");
    localStringBuffer.append(i);
    localStringBuffer.append(", ['");
    localStringBuffer.append("org.eclipse.swt.browser.error");
    localStringBuffer.append("' + e.message]);}}");
    execute(localStringBuffer.toString());
    execute(getDeleteFunctionString(str));
    deregisterFunction(localEvaluateFunction);
    Object localObject = this.evaluateResult;
    this.evaluateResult = null;
    if ((localObject instanceof SWTException))
      throw ((SWTException)localObject);
    return localObject;
  }

  public abstract boolean forward();

  public abstract String getBrowserType();

  String getDeleteFunctionString(String paramString)
  {
    return "delete window." + paramString;
  }

  int getNextFunctionIndex()
  {
    return this.nextFunctionIndex++;
  }

  public abstract String getText();

  public abstract String getUrl();

  public Object getWebBrowser()
  {
    return null;
  }

  public abstract boolean isBackEnabled();

  public boolean isFocusControl()
  {
    return false;
  }

  public abstract boolean isForwardEnabled();

  public abstract void refresh();

  void registerFunction(BrowserFunction paramBrowserFunction)
  {
    this.functions.put(new Integer(paramBrowserFunction.index), paramBrowserFunction);
  }

  public void removeAuthenticationListener(AuthenticationListener paramAuthenticationListener)
  {
    if (this.authenticationListeners.length == 0)
      return;
    int i = -1;
    for (int j = 0; j < this.authenticationListeners.length; j++)
      if (paramAuthenticationListener == this.authenticationListeners[j])
      {
        i = j;
        break;
      }
    if (i == -1)
      return;
    if (this.authenticationListeners.length == 1)
    {
      this.authenticationListeners = new AuthenticationListener[0];
      return;
    }
    AuthenticationListener[] arrayOfAuthenticationListener = new AuthenticationListener[this.authenticationListeners.length - 1];
    System.arraycopy(this.authenticationListeners, 0, arrayOfAuthenticationListener, 0, i);
    System.arraycopy(this.authenticationListeners, i + 1, arrayOfAuthenticationListener, i, this.authenticationListeners.length - i - 1);
    this.authenticationListeners = arrayOfAuthenticationListener;
  }

  public void removeCloseWindowListener(CloseWindowListener paramCloseWindowListener)
  {
    if (this.closeWindowListeners.length == 0)
      return;
    int i = -1;
    for (int j = 0; j < this.closeWindowListeners.length; j++)
      if (paramCloseWindowListener == this.closeWindowListeners[j])
      {
        i = j;
        break;
      }
    if (i == -1)
      return;
    if (this.closeWindowListeners.length == 1)
    {
      this.closeWindowListeners = new CloseWindowListener[0];
      return;
    }
    CloseWindowListener[] arrayOfCloseWindowListener = new CloseWindowListener[this.closeWindowListeners.length - 1];
    System.arraycopy(this.closeWindowListeners, 0, arrayOfCloseWindowListener, 0, i);
    System.arraycopy(this.closeWindowListeners, i + 1, arrayOfCloseWindowListener, i, this.closeWindowListeners.length - i - 1);
    this.closeWindowListeners = arrayOfCloseWindowListener;
  }

  public void removeLocationListener(LocationListener paramLocationListener)
  {
    if (this.locationListeners.length == 0)
      return;
    int i = -1;
    for (int j = 0; j < this.locationListeners.length; j++)
      if (paramLocationListener == this.locationListeners[j])
      {
        i = j;
        break;
      }
    if (i == -1)
      return;
    if (this.locationListeners.length == 1)
    {
      this.locationListeners = new LocationListener[0];
      return;
    }
    LocationListener[] arrayOfLocationListener = new LocationListener[this.locationListeners.length - 1];
    System.arraycopy(this.locationListeners, 0, arrayOfLocationListener, 0, i);
    System.arraycopy(this.locationListeners, i + 1, arrayOfLocationListener, i, this.locationListeners.length - i - 1);
    this.locationListeners = arrayOfLocationListener;
  }

  public void removeOpenWindowListener(OpenWindowListener paramOpenWindowListener)
  {
    if (this.openWindowListeners.length == 0)
      return;
    int i = -1;
    for (int j = 0; j < this.openWindowListeners.length; j++)
      if (paramOpenWindowListener == this.openWindowListeners[j])
      {
        i = j;
        break;
      }
    if (i == -1)
      return;
    if (this.openWindowListeners.length == 1)
    {
      this.openWindowListeners = new OpenWindowListener[0];
      return;
    }
    OpenWindowListener[] arrayOfOpenWindowListener = new OpenWindowListener[this.openWindowListeners.length - 1];
    System.arraycopy(this.openWindowListeners, 0, arrayOfOpenWindowListener, 0, i);
    System.arraycopy(this.openWindowListeners, i + 1, arrayOfOpenWindowListener, i, this.openWindowListeners.length - i - 1);
    this.openWindowListeners = arrayOfOpenWindowListener;
  }

  public void removeProgressListener(ProgressListener paramProgressListener)
  {
    if (this.progressListeners.length == 0)
      return;
    int i = -1;
    for (int j = 0; j < this.progressListeners.length; j++)
      if (paramProgressListener == this.progressListeners[j])
      {
        i = j;
        break;
      }
    if (i == -1)
      return;
    if (this.progressListeners.length == 1)
    {
      this.progressListeners = new ProgressListener[0];
      return;
    }
    ProgressListener[] arrayOfProgressListener = new ProgressListener[this.progressListeners.length - 1];
    System.arraycopy(this.progressListeners, 0, arrayOfProgressListener, 0, i);
    System.arraycopy(this.progressListeners, i + 1, arrayOfProgressListener, i, this.progressListeners.length - i - 1);
    this.progressListeners = arrayOfProgressListener;
  }

  public void removeStatusTextListener(StatusTextListener paramStatusTextListener)
  {
    if (this.statusTextListeners.length == 0)
      return;
    int i = -1;
    for (int j = 0; j < this.statusTextListeners.length; j++)
      if (paramStatusTextListener == this.statusTextListeners[j])
      {
        i = j;
        break;
      }
    if (i == -1)
      return;
    if (this.statusTextListeners.length == 1)
    {
      this.statusTextListeners = new StatusTextListener[0];
      return;
    }
    StatusTextListener[] arrayOfStatusTextListener = new StatusTextListener[this.statusTextListeners.length - 1];
    System.arraycopy(this.statusTextListeners, 0, arrayOfStatusTextListener, 0, i);
    System.arraycopy(this.statusTextListeners, i + 1, arrayOfStatusTextListener, i, this.statusTextListeners.length - i - 1);
    this.statusTextListeners = arrayOfStatusTextListener;
  }

  public void removeTitleListener(TitleListener paramTitleListener)
  {
    if (this.titleListeners.length == 0)
      return;
    int i = -1;
    for (int j = 0; j < this.titleListeners.length; j++)
      if (paramTitleListener == this.titleListeners[j])
      {
        i = j;
        break;
      }
    if (i == -1)
      return;
    if (this.titleListeners.length == 1)
    {
      this.titleListeners = new TitleListener[0];
      return;
    }
    TitleListener[] arrayOfTitleListener = new TitleListener[this.titleListeners.length - 1];
    System.arraycopy(this.titleListeners, 0, arrayOfTitleListener, 0, i);
    System.arraycopy(this.titleListeners, i + 1, arrayOfTitleListener, i, this.titleListeners.length - i - 1);
    this.titleListeners = arrayOfTitleListener;
  }

  public void removeVisibilityWindowListener(VisibilityWindowListener paramVisibilityWindowListener)
  {
    if (this.visibilityWindowListeners.length == 0)
      return;
    int i = -1;
    for (int j = 0; j < this.visibilityWindowListeners.length; j++)
      if (paramVisibilityWindowListener == this.visibilityWindowListeners[j])
      {
        i = j;
        break;
      }
    if (i == -1)
      return;
    if (this.visibilityWindowListeners.length == 1)
    {
      this.visibilityWindowListeners = new VisibilityWindowListener[0];
      return;
    }
    VisibilityWindowListener[] arrayOfVisibilityWindowListener = new VisibilityWindowListener[this.visibilityWindowListeners.length - 1];
    System.arraycopy(this.visibilityWindowListeners, 0, arrayOfVisibilityWindowListener, 0, i);
    System.arraycopy(this.visibilityWindowListeners, i + 1, arrayOfVisibilityWindowListener, i, this.visibilityWindowListeners.length - i - 1);
    this.visibilityWindowListeners = arrayOfVisibilityWindowListener;
  }

  boolean sendKeyEvent(Event paramEvent)
  {
    int i = 0;
    boolean bool1 = true;
    switch (paramEvent.keyCode)
    {
    case 27:
      i = 2;
      bool1 = true;
      break;
    case 13:
      i = 4;
      bool1 = false;
      break;
    case 16777218:
    case 16777220:
      i = 64;
      bool1 = false;
      break;
    case 16777217:
    case 16777219:
      i = 32;
      bool1 = false;
      break;
    case 9:
      i = (paramEvent.stateMask & 0x20000) != 0 ? 8 : 16;
      bool1 = (paramEvent.stateMask & 0x40000) != 0;
      break;
    case 16777222:
      if ((paramEvent.stateMask & 0x40000) != 0)
      {
        i = 512;
        bool1 = true;
      }
      break;
    case 16777221:
      if ((paramEvent.stateMask & 0x40000) != 0)
      {
        i = 256;
        bool1 = true;
      }
      break;
    default:
      if ((translateMnemonics()) && (paramEvent.character != 0) && ((paramEvent.stateMask & 0x50000) == 65536))
      {
        i = 128;
        bool1 = true;
      }
      break;
    }
    boolean bool2 = true;
    if (i != 0)
    {
      boolean bool3 = paramEvent.doit;
      paramEvent.doit = bool1;
      bool2 = !this.browser.traverse(i, paramEvent);
      paramEvent.doit = bool3;
    }
    if (bool2)
    {
      this.browser.notifyListeners(paramEvent.type, paramEvent);
      bool2 = paramEvent.doit;
    }
    return bool2;
  }

  public void setBrowser(Browser paramBrowser)
  {
    this.browser = paramBrowser;
  }

  public abstract boolean setText(String paramString, boolean paramBoolean);

  public abstract boolean setUrl(String paramString1, String paramString2, String[] paramArrayOfString);

  public abstract void stop();

  int translateKey(int paramInt)
  {
    for (int i = 0; i < KeyTable.length; i++)
      if (KeyTable[i][0] == paramInt)
        return KeyTable[i][1];
    return 0;
  }

  boolean translateMnemonics()
  {
    return true;
  }

  public class EvaluateFunction extends BrowserFunction
  {
    public EvaluateFunction(Browser paramString, String arg3)
    {
      super(str, false);
    }

    public Object function(Object[] paramArrayOfObject)
    {
      if ((paramArrayOfObject[0] instanceof String))
      {
        String str1 = (String)paramArrayOfObject[0];
        if (str1.startsWith("org.eclipse.swt.browser.error"))
        {
          String str2 = WebBrowser.ExtractError(str1);
          if (str2.length() > 0)
            WebBrowser.this.evaluateResult = new SWTException(50, str2);
          else
            WebBrowser.this.evaluateResult = new SWTException(50);
          return null;
        }
      }
      WebBrowser.this.evaluateResult = paramArrayOfObject[0];
      return null;
    }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.browser.WebBrowser
 * JD-Core Version:    0.6.2
 */