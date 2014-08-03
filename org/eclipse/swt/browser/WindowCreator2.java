package org.eclipse.swt.browser;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.internal.C;
import org.eclipse.swt.internal.mozilla.XPCOM;
import org.eclipse.swt.internal.mozilla.XPCOMObject;
import org.eclipse.swt.internal.mozilla.nsIBaseWindow;
import org.eclipse.swt.internal.mozilla.nsID;
import org.eclipse.swt.internal.mozilla.nsISupports;
import org.eclipse.swt.internal.mozilla.nsIURI;
import org.eclipse.swt.internal.mozilla.nsIWebBrowser;
import org.eclipse.swt.internal.mozilla.nsIWebBrowserChrome;
import org.eclipse.swt.internal.mozilla.nsIWindowCreator;
import org.eclipse.swt.internal.mozilla.nsIWindowCreator2;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

class WindowCreator2
{
  XPCOMObject supports;
  XPCOMObject windowCreator;
  XPCOMObject windowCreator2;
  int refCount = 0;

  WindowCreator2()
  {
    createCOMInterfaces();
  }

  int AddRef()
  {
    this.refCount += 1;
    return this.refCount;
  }

  void createCOMInterfaces()
  {
    this.supports = new XPCOMObject(new int[] { 2 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return WindowCreator2.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return WindowCreator2.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return WindowCreator2.this.Release();
      }
    };
    this.windowCreator = new XPCOMObject(new int[] { 2, 0, 0, 3 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return WindowCreator2.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return WindowCreator2.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return WindowCreator2.this.Release();
      }

      public int method3(int[] paramAnonymousArrayOfInt)
      {
        return WindowCreator2.this.CreateChromeWindow(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }
    };
    this.windowCreator2 = new XPCOMObject(new int[] { 2, 0, 0, 3, 6 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return WindowCreator2.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return WindowCreator2.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return WindowCreator2.this.Release();
      }

      public int method3(int[] paramAnonymousArrayOfInt)
      {
        return WindowCreator2.this.CreateChromeWindow(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }

      public int method4(int[] paramAnonymousArrayOfInt)
      {
        return WindowCreator2.this.CreateChromeWindow2(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4], paramAnonymousArrayOfInt[5]);
      }
    };
  }

  void disposeCOMInterfaces()
  {
    if (this.supports != null)
    {
      this.supports.dispose();
      this.supports = null;
    }
    if (this.windowCreator != null)
    {
      this.windowCreator.dispose();
      this.windowCreator = null;
    }
    if (this.windowCreator2 != null)
    {
      this.windowCreator2.dispose();
      this.windowCreator2 = null;
    }
  }

  int getAddress()
  {
    return this.windowCreator.getAddress();
  }

  int QueryInterface(int paramInt1, int paramInt2)
  {
    if ((paramInt1 == 0) || (paramInt2 == 0))
      return -2147467262;
    nsID localnsID = new nsID();
    XPCOM.memmove(localnsID, paramInt1, 16);
    if (localnsID.Equals(nsISupports.NS_ISUPPORTS_IID))
    {
      XPCOM.memmove(paramInt2, new int[] { this.supports.getAddress() }, C.PTR_SIZEOF);
      AddRef();
      return 0;
    }
    if (localnsID.Equals(nsIWindowCreator.NS_IWINDOWCREATOR_IID))
    {
      XPCOM.memmove(paramInt2, new int[] { this.windowCreator.getAddress() }, C.PTR_SIZEOF);
      AddRef();
      return 0;
    }
    if (localnsID.Equals(nsIWindowCreator2.NS_IWINDOWCREATOR2_IID))
    {
      XPCOM.memmove(paramInt2, new int[] { this.windowCreator2.getAddress() }, C.PTR_SIZEOF);
      AddRef();
      return 0;
    }
    XPCOM.memmove(paramInt2, new int[1], C.PTR_SIZEOF);
    return -2147467262;
  }

  int Release()
  {
    this.refCount -= 1;
    if (this.refCount == 0)
      disposeCOMInterfaces();
    return this.refCount;
  }

  int CreateChromeWindow(int paramInt1, int paramInt2, int paramInt3)
  {
    return CreateChromeWindow2(paramInt1, paramInt2, 0, 0, 0, paramInt3);
  }

  int CreateChromeWindow2(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    if ((paramInt1 == 0) && ((paramInt2 & 0x80000000) == 0))
      return -2147467263;
    Browser localBrowser = null;
    Object localObject1;
    int j;
    Object localObject3;
    Object localObject4;
    if (paramInt1 != 0)
    {
      localObject1 = new nsIWebBrowserChrome(paramInt1);
      int[] arrayOfInt1 = new int[1];
      j = ((nsIWebBrowserChrome)localObject1).GetWebBrowser(arrayOfInt1);
      if (j != 0)
        Mozilla.error(j);
      if (arrayOfInt1[0] == 0)
        Mozilla.error(-2147467262);
      localObject3 = new nsIWebBrowser(arrayOfInt1[0]);
      localObject4 = new int[1];
      j = ((nsIWebBrowser)localObject3).QueryInterface(nsIBaseWindow.NS_IBASEWINDOW_IID, (int[])localObject4);
      if (j != 0)
        Mozilla.error(j);
      if (localObject4[0] == 0)
        Mozilla.error(-2147467262);
      ((nsIWebBrowser)localObject3).Release();
      nsIBaseWindow localnsIBaseWindow = new nsIBaseWindow(localObject4[0]);
      localObject4[0] = 0;
      int[] arrayOfInt2 = new int[1];
      j = localnsIBaseWindow.GetParentNativeWindow(arrayOfInt2);
      if (j != 0)
        Mozilla.error(j);
      if (arrayOfInt2[0] == 0)
        Mozilla.error(-2147467262);
      localnsIBaseWindow.Release();
      localBrowser = Mozilla.findBrowser(arrayOfInt2[0]);
    }
    int i = 1;
    Object localObject2;
    if ((paramInt2 & 0x80000000) != 0)
    {
      j = 0;
      if ((paramInt2 & 0x8000) == 0)
        j |= 2144;
      if ((paramInt2 & 0x20000000) != 0)
        j |= 65536;
      localObject3 = localBrowser == null ? new Shell(j) : new Shell(localBrowser.getShell(), j);
      ((Shell)localObject3).setLayout(new FillLayout());
      localObject1 = new Browser((Composite)localObject3, localBrowser == null ? 32768 : localBrowser.getStyle() & 0x8000);
      ((Browser)localObject1).addVisibilityWindowListener(new VisibilityWindowListener()
      {
        private final Shell val$shell;

        public void hide(WindowEvent paramAnonymousWindowEvent)
        {
        }

        public void show(WindowEvent paramAnonymousWindowEvent)
        {
          if (paramAnonymousWindowEvent.location != null)
            this.val$shell.setLocation(paramAnonymousWindowEvent.location);
          if (paramAnonymousWindowEvent.size != null)
          {
            Point localPoint = paramAnonymousWindowEvent.size;
            this.val$shell.setSize(this.val$shell.computeSize(localPoint.x, localPoint.y));
          }
          this.val$shell.open();
        }
      });
      ((Browser)localObject1).addCloseWindowListener(new CloseWindowListener()
      {
        private final Shell val$shell;

        public void close(WindowEvent paramAnonymousWindowEvent)
        {
          this.val$shell.close();
        }
      });
      if (paramInt4 != 0)
      {
        localObject4 = new nsIURI(paramInt4);
        int i1 = XPCOM.nsEmbedCString_new();
        if (((nsIURI)localObject4).GetSpec(i1) == 0)
        {
          int i2 = XPCOM.nsEmbedCString_Length(i1);
          if (i2 > 0)
          {
            int i3 = XPCOM.nsEmbedCString_get(i1);
            byte[] arrayOfByte = new byte[i2];
            XPCOM.memmove(arrayOfByte, i3, i2);
            ((Browser)localObject1).setUrl(new String(arrayOfByte));
          }
        }
        XPCOM.nsEmbedCString_delete(i1);
      }
    }
    else
    {
      localObject2 = new WindowEvent(localBrowser);
      ((WindowEvent)localObject2).display = localBrowser.getDisplay();
      ((WindowEvent)localObject2).widget = localBrowser;
      ((WindowEvent)localObject2).required = true;
      for (int k = 0; k < localBrowser.webBrowser.openWindowListeners.length; k++)
        localBrowser.webBrowser.openWindowListeners[k].open((WindowEvent)localObject2);
      localObject1 = ((WindowEvent)localObject2).browser;
      i = (localObject1 != null) && (!((Browser)localObject1).isDisposed()) ? 1 : 0;
      if (i != 0)
      {
        String str = "win32";
        int n = (!str.equals("gtk")) && (!str.equals("motif")) ? 0 : 1;
        i = (n == 0) && ((((Browser)localObject1).getStyle() & 0x8000) == 0) ? 0 : 1;
      }
    }
    if (i != 0)
    {
      localObject2 = (Mozilla)((Browser)localObject1).webBrowser;
      ((Mozilla)localObject2).isChild = true;
      int m = ((Mozilla)localObject2).webBrowserChrome.getAddress();
      nsIWebBrowserChrome localnsIWebBrowserChrome = new nsIWebBrowserChrome(m);
      localnsIWebBrowserChrome.SetChromeFlags(paramInt2);
      localnsIWebBrowserChrome.AddRef();
      XPCOM.memmove(paramInt6, new int[] { m }, C.PTR_SIZEOF);
    }
    else if (paramInt5 != 0)
    {
      C.memmove(paramInt5, new int[] { 1 }, 4);
    }
    return i != 0 ? 0 : -2147467263;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.browser.WindowCreator2
 * JD-Core Version:    0.6.2
 */