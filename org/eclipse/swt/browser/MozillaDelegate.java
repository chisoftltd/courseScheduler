package org.eclipse.swt.browser;

import org.eclipse.swt.internal.Callback;
import org.eclipse.swt.internal.mozilla.nsIBaseWindow;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.widgets.Display;

class MozillaDelegate
{
  Browser browser;
  static int MozillaProc;
  static Callback SubclassProc;

  MozillaDelegate(Browser paramBrowser)
  {
    this.browser = paramBrowser;
  }

  static Browser findBrowser(int paramInt)
  {
    Display localDisplay = Display.getCurrent();
    return (Browser)localDisplay.findWidget(paramInt);
  }

  static String getLibraryName()
  {
    return "xpcom.dll";
  }

  static char[] mbcsToWcs(String paramString, byte[] paramArrayOfByte)
  {
    char[] arrayOfChar1 = new char[paramArrayOfByte.length];
    int i = OS.MultiByteToWideChar(0, 1, paramArrayOfByte, paramArrayOfByte.length, arrayOfChar1, arrayOfChar1.length);
    if (i == arrayOfChar1.length)
      return arrayOfChar1;
    char[] arrayOfChar2 = new char[i];
    System.arraycopy(arrayOfChar1, 0, arrayOfChar2, 0, i);
    return arrayOfChar2;
  }

  static byte[] wcsToMbcs(String paramString1, String paramString2, boolean paramBoolean)
  {
    char[] arrayOfChar = new char[paramString2.length()];
    paramString2.getChars(0, arrayOfChar.length, arrayOfChar, 0);
    Object localObject = new byte[i = arrayOfChar.length * 2 + (paramBoolean ? 1 : 0)];
    int i = OS.WideCharToMultiByte(0, 0, arrayOfChar, arrayOfChar.length, (byte[])localObject, i, null, null);
    if (paramBoolean)
    {
      i++;
    }
    else if (localObject.length != i)
    {
      byte[] arrayOfByte = new byte[i];
      System.arraycopy(localObject, 0, arrayOfByte, 0, i);
      localObject = arrayOfByte;
    }
    return localObject;
  }

  static int windowProc(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    switch (paramInt2)
    {
    case 20:
      RECT localRECT = new RECT();
      OS.GetClientRect(paramInt1, localRECT);
      OS.FillRect(paramInt3, localRECT, OS.GetSysColorBrush(OS.COLOR_WINDOW));
    }
    return OS.CallWindowProc(MozillaProc, paramInt1, paramInt2, paramInt3, paramInt4);
  }

  void addWindowSubclass()
  {
    int i = OS.GetWindow(this.browser.handle, 5);
    if (SubclassProc == null)
    {
      SubclassProc = new Callback(MozillaDelegate.class, "windowProc", 4);
      MozillaProc = OS.GetWindowLongPtr(i, -4);
    }
    OS.SetWindowLongPtr(i, -4, SubclassProc.getAddress());
  }

  int createBaseWindow(nsIBaseWindow paramnsIBaseWindow)
  {
    return paramnsIBaseWindow.Create();
  }

  int getHandle()
  {
    return this.browser.handle;
  }

  String getJSLibraryName()
  {
    return "js3250.dll";
  }

  String getProfilePath()
  {
    TCHAR localTCHAR = new TCHAR(0, 260);
    String str;
    if (OS.SHGetFolderPath(0, 26, 0, 0, localTCHAR) == 0)
      str = localTCHAR.toString(0, localTCHAR.strlen());
    else
      str = System.getProperty("user.home");
    return str + Mozilla.SEPARATOR_OS + "Mozilla" + Mozilla.SEPARATOR_OS + "eclipse";
  }

  String getSWTInitLibraryName()
  {
    return "swt-xulrunner";
  }

  void handleFocus()
  {
  }

  void handleMouseDown()
  {
  }

  boolean hookEnterExit()
  {
    return true;
  }

  void init()
  {
  }

  boolean needsSpinup()
  {
    return false;
  }

  void onDispose(int paramInt)
  {
    removeWindowSubclass();
    this.browser = null;
  }

  void removeWindowSubclass()
  {
    if (SubclassProc == null)
      return;
    int i = OS.GetWindow(this.browser.handle, 5);
    OS.SetWindowLongPtr(i, -4, MozillaProc);
  }

  boolean sendTraverse()
  {
    return false;
  }

  void setSize(int paramInt1, int paramInt2, int paramInt3)
  {
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.browser.MozillaDelegate
 * JD-Core Version:    0.6.2
 */