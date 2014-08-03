package org.eclipse.swt.browser;

import org.eclipse.swt.SWT;
import org.eclipse.swt.internal.C;
import org.eclipse.swt.internal.Compatibility;
import org.eclipse.swt.internal.mozilla.XPCOM;
import org.eclipse.swt.internal.mozilla.XPCOMObject;
import org.eclipse.swt.internal.mozilla.nsID;
import org.eclipse.swt.internal.mozilla.nsIDownload;
import org.eclipse.swt.internal.mozilla.nsIHelperAppLauncher;
import org.eclipse.swt.internal.mozilla.nsILocalFile;
import org.eclipse.swt.internal.mozilla.nsIProgressDialog;
import org.eclipse.swt.internal.mozilla.nsISupports;
import org.eclipse.swt.internal.mozilla.nsIURI;
import org.eclipse.swt.internal.mozilla.nsIWebProgressListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

class Download
{
  XPCOMObject supports;
  XPCOMObject download;
  XPCOMObject progressDialog;
  XPCOMObject webProgressListener;
  nsIHelperAppLauncher helperAppLauncher;
  int refCount = 0;
  Shell shell;
  Label status;
  Button cancel;

  Download()
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
        return Download.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return Download.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return Download.this.Release();
      }
    };
    this.download = new XPCOMObject(new int[] { 2, 0, 0, 7, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return Download.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return Download.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return Download.this.Release();
      }

      public int method3(int[] paramAnonymousArrayOfInt)
      {
        return Download.this.Init(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4], paramAnonymousArrayOfInt[5], paramAnonymousArrayOfInt[6]);
      }

      public int method4(int[] paramAnonymousArrayOfInt)
      {
        return Download.this.GetSource(paramAnonymousArrayOfInt[0]);
      }

      public int method5(int[] paramAnonymousArrayOfInt)
      {
        return Download.this.GetTarget(paramAnonymousArrayOfInt[0]);
      }

      public int method6(int[] paramAnonymousArrayOfInt)
      {
        return Download.this.GetPersist(paramAnonymousArrayOfInt[0]);
      }

      public int method7(int[] paramAnonymousArrayOfInt)
      {
        return Download.this.GetPercentComplete(paramAnonymousArrayOfInt[0]);
      }

      public int method8(int[] paramAnonymousArrayOfInt)
      {
        return Download.this.GetDisplayName(paramAnonymousArrayOfInt[0]);
      }

      public int method9(int[] paramAnonymousArrayOfInt)
      {
        return Download.this.SetDisplayName(paramAnonymousArrayOfInt[0]);
      }

      public int method10(int[] paramAnonymousArrayOfInt)
      {
        return Download.this.GetStartTime(paramAnonymousArrayOfInt[0]);
      }

      public int method11(int[] paramAnonymousArrayOfInt)
      {
        return Download.this.GetMIMEInfo(paramAnonymousArrayOfInt[0]);
      }

      public int method12(int[] paramAnonymousArrayOfInt)
      {
        return Download.this.GetListener(paramAnonymousArrayOfInt[0]);
      }

      public int method13(int[] paramAnonymousArrayOfInt)
      {
        return Download.this.SetListener(paramAnonymousArrayOfInt[0]);
      }

      public int method14(int[] paramAnonymousArrayOfInt)
      {
        return Download.this.GetObserver(paramAnonymousArrayOfInt[0]);
      }

      public int method15(int[] paramAnonymousArrayOfInt)
      {
        return Download.this.SetObserver(paramAnonymousArrayOfInt[0]);
      }
    };
    this.progressDialog = new XPCOMObject(new int[] { 2, 0, 0, 7, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return Download.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return Download.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return Download.this.Release();
      }

      public int method3(int[] paramAnonymousArrayOfInt)
      {
        return Download.this.Init(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4], paramAnonymousArrayOfInt[5], paramAnonymousArrayOfInt[6]);
      }

      public int method4(int[] paramAnonymousArrayOfInt)
      {
        return Download.this.GetSource(paramAnonymousArrayOfInt[0]);
      }

      public int method5(int[] paramAnonymousArrayOfInt)
      {
        return Download.this.GetTarget(paramAnonymousArrayOfInt[0]);
      }

      public int method6(int[] paramAnonymousArrayOfInt)
      {
        return Download.this.GetPersist(paramAnonymousArrayOfInt[0]);
      }

      public int method7(int[] paramAnonymousArrayOfInt)
      {
        return Download.this.GetPercentComplete(paramAnonymousArrayOfInt[0]);
      }

      public int method8(int[] paramAnonymousArrayOfInt)
      {
        return Download.this.GetDisplayName(paramAnonymousArrayOfInt[0]);
      }

      public int method9(int[] paramAnonymousArrayOfInt)
      {
        return Download.this.SetDisplayName(paramAnonymousArrayOfInt[0]);
      }

      public int method10(int[] paramAnonymousArrayOfInt)
      {
        return Download.this.GetStartTime(paramAnonymousArrayOfInt[0]);
      }

      public int method11(int[] paramAnonymousArrayOfInt)
      {
        return Download.this.GetMIMEInfo(paramAnonymousArrayOfInt[0]);
      }

      public int method12(int[] paramAnonymousArrayOfInt)
      {
        return Download.this.GetListener(paramAnonymousArrayOfInt[0]);
      }

      public int method13(int[] paramAnonymousArrayOfInt)
      {
        return Download.this.SetListener(paramAnonymousArrayOfInt[0]);
      }

      public int method14(int[] paramAnonymousArrayOfInt)
      {
        return Download.this.GetObserver(paramAnonymousArrayOfInt[0]);
      }

      public int method15(int[] paramAnonymousArrayOfInt)
      {
        return Download.this.SetObserver(paramAnonymousArrayOfInt[0]);
      }

      public int method16(int[] paramAnonymousArrayOfInt)
      {
        return Download.this.Open(paramAnonymousArrayOfInt[0]);
      }

      public int method17(int[] paramAnonymousArrayOfInt)
      {
        return Download.this.GetCancelDownloadOnClose(paramAnonymousArrayOfInt[0]);
      }

      public int method18(int[] paramAnonymousArrayOfInt)
      {
        return Download.this.SetCancelDownloadOnClose(paramAnonymousArrayOfInt[0]);
      }

      public int method19(int[] paramAnonymousArrayOfInt)
      {
        return Download.this.GetDialog(paramAnonymousArrayOfInt[0]);
      }

      public int method20(int[] paramAnonymousArrayOfInt)
      {
        return Download.this.SetDialog(paramAnonymousArrayOfInt[0]);
      }
    };
    this.webProgressListener = new XPCOMObject(new int[] { 2, 0, 0, 4, 6, 3, 4, 3 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return Download.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return Download.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return Download.this.Release();
      }

      public int method3(int[] paramAnonymousArrayOfInt)
      {
        return Download.this.OnStateChange(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3]);
      }

      public int method4(int[] paramAnonymousArrayOfInt)
      {
        return Download.this.OnProgressChange(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4], paramAnonymousArrayOfInt[5]);
      }

      public int method5(int[] paramAnonymousArrayOfInt)
      {
        return Download.this.OnLocationChange(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }

      public int method6(int[] paramAnonymousArrayOfInt)
      {
        return Download.this.OnStatusChange(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3]);
      }

      public int method7(int[] paramAnonymousArrayOfInt)
      {
        return Download.this.OnSecurityChange(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
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
    if (this.download != null)
    {
      this.download.dispose();
      this.download = null;
    }
    if (this.progressDialog != null)
    {
      this.progressDialog.dispose();
      this.progressDialog = null;
    }
    if (this.webProgressListener != null)
    {
      this.webProgressListener.dispose();
      this.webProgressListener = null;
    }
  }

  int getAddress()
  {
    return this.progressDialog.getAddress();
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
    if (localnsID.Equals(nsIDownload.NS_IDOWNLOAD_IID))
    {
      XPCOM.memmove(paramInt2, new int[] { this.download.getAddress() }, C.PTR_SIZEOF);
      AddRef();
      return 0;
    }
    if (localnsID.Equals(nsIProgressDialog.NS_IPROGRESSDIALOG_IID))
    {
      XPCOM.memmove(paramInt2, new int[] { this.progressDialog.getAddress() }, C.PTR_SIZEOF);
      AddRef();
      return 0;
    }
    if (localnsID.Equals(nsIWebProgressListener.NS_IWEBPROGRESSLISTENER_IID))
    {
      XPCOM.memmove(paramInt2, new int[] { this.webProgressListener.getAddress() }, C.PTR_SIZEOF);
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

  int Init(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7)
  {
    nsIURI localnsIURI = new nsIURI(paramInt1);
    int i = XPCOM.nsEmbedCString_new();
    int j = localnsIURI.GetHost(i);
    if (j != 0)
      Mozilla.error(j);
    int k = XPCOM.nsEmbedCString_Length(i);
    int m = XPCOM.nsEmbedCString_get(i);
    byte[] arrayOfByte = new byte[k];
    XPCOM.memmove(arrayOfByte, m, k);
    XPCOM.nsEmbedCString_delete(i);
    String str1 = new String(arrayOfByte);
    String str2 = null;
    nsISupports localnsISupports = new nsISupports(paramInt2);
    int[] arrayOfInt = new int[1];
    j = localnsISupports.QueryInterface(nsIURI.NS_IURI_IID, arrayOfInt);
    int n;
    if (j == 0)
    {
      localObject = new nsIURI(arrayOfInt[0]);
      arrayOfInt[0] = 0;
      n = XPCOM.nsEmbedCString_new();
      j = ((nsIURI)localObject).GetPath(n);
      if (j != 0)
        Mozilla.error(j);
      k = XPCOM.nsEmbedCString_Length(n);
      m = XPCOM.nsEmbedCString_get(n);
      arrayOfByte = new byte[k];
      XPCOM.memmove(arrayOfByte, m, k);
      XPCOM.nsEmbedCString_delete(n);
      str2 = new String(arrayOfByte);
      int i1 = str2.lastIndexOf(System.getProperty("file.separator"));
      str2 = str2.substring(i1 + 1);
      ((nsIURI)localObject).Release();
    }
    else
    {
      localObject = new nsILocalFile(paramInt2);
      n = XPCOM.nsEmbedCString_new();
      j = ((nsILocalFile)localObject).GetNativeLeafName(n);
      if (j != 0)
        Mozilla.error(j);
      k = XPCOM.nsEmbedCString_Length(n);
      m = XPCOM.nsEmbedCString_get(n);
      arrayOfByte = new byte[k];
      XPCOM.memmove(arrayOfByte, m, k);
      XPCOM.nsEmbedCString_delete(n);
      str2 = new String(arrayOfByte);
    }
    Object localObject = new Listener()
    {
      public void handleEvent(Event paramAnonymousEvent)
      {
        if (paramAnonymousEvent.widget == Download.this.cancel)
          Download.this.shell.close();
        if (Download.this.helperAppLauncher != null)
        {
          Download.this.helperAppLauncher.Cancel();
          Download.this.helperAppLauncher.Release();
        }
        Download.this.shell = null;
        Download.this.helperAppLauncher = null;
      }
    };
    this.shell = new Shell(2144);
    String str3 = Compatibility.getMessage("SWT_Download_File", new Object[] { str2 });
    this.shell.setText(str3);
    GridLayout localGridLayout = new GridLayout();
    localGridLayout.marginHeight = 15;
    localGridLayout.marginWidth = 15;
    localGridLayout.verticalSpacing = 20;
    this.shell.setLayout(localGridLayout);
    str3 = Compatibility.getMessage("SWT_Download_Location", new Object[] { str2, str1 });
    new Label(this.shell, 64).setText(str3);
    this.status = new Label(this.shell, 64);
    str3 = Compatibility.getMessage("SWT_Download_Started");
    this.status.setText(str3);
    GridData localGridData = new GridData();
    localGridData.grabExcessHorizontalSpace = true;
    localGridData.grabExcessVerticalSpace = true;
    this.status.setLayoutData(localGridData);
    this.cancel = new Button(this.shell, 8);
    this.cancel.setText(SWT.getMessage("SWT_Cancel"));
    localGridData = new GridData();
    localGridData.horizontalAlignment = 2;
    this.cancel.setLayoutData(localGridData);
    this.cancel.addListener(13, (Listener)localObject);
    this.shell.addListener(21, (Listener)localObject);
    this.shell.pack();
    this.shell.open();
    return 0;
  }

  int GetSource(int paramInt)
  {
    return -2147467263;
  }

  int GetTarget(int paramInt)
  {
    return -2147467263;
  }

  int GetPersist(int paramInt)
  {
    return -2147467263;
  }

  int GetPercentComplete(int paramInt)
  {
    return -2147467263;
  }

  int GetDisplayName(int paramInt)
  {
    return -2147467263;
  }

  int SetDisplayName(int paramInt)
  {
    return -2147467263;
  }

  int GetStartTime(int paramInt)
  {
    return -2147467263;
  }

  int GetMIMEInfo(int paramInt)
  {
    return -2147467263;
  }

  int GetListener(int paramInt)
  {
    return -2147467263;
  }

  int SetListener(int paramInt)
  {
    return -2147467263;
  }

  int GetObserver(int paramInt)
  {
    return -2147467263;
  }

  int SetObserver(int paramInt)
  {
    if (paramInt != 0)
    {
      nsISupports localnsISupports = new nsISupports(paramInt);
      int[] arrayOfInt = new int[1];
      int i = localnsISupports.QueryInterface(nsIHelperAppLauncher.NS_IHELPERAPPLAUNCHER_IID, arrayOfInt);
      if (i != 0)
        Mozilla.error(i);
      if (arrayOfInt[0] == 0)
        Mozilla.error(-2147467262);
      this.helperAppLauncher = new nsIHelperAppLauncher(arrayOfInt[0]);
    }
    return 0;
  }

  int Open(int paramInt)
  {
    return -2147467263;
  }

  int GetCancelDownloadOnClose(int paramInt)
  {
    return -2147467263;
  }

  int SetCancelDownloadOnClose(int paramInt)
  {
    return -2147467263;
  }

  int GetDialog(int paramInt)
  {
    return -2147467263;
  }

  int SetDialog(int paramInt)
  {
    return -2147467263;
  }

  int OnStateChange(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((paramInt3 & 0x10) != 0)
    {
      if (this.helperAppLauncher != null)
        this.helperAppLauncher.Release();
      this.helperAppLauncher = null;
      if ((this.shell != null) && (!this.shell.isDisposed()))
        this.shell.dispose();
      this.shell = null;
    }
    return 0;
  }

  int OnProgressChange(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    int i = paramInt5 / 1024;
    int j = paramInt6 / 1024;
    if ((this.shell != null) && (!this.shell.isDisposed()))
    {
      Object[] arrayOfObject = { new Integer(i), new Integer(j) };
      String str = Compatibility.getMessage("SWT_Download_Status", arrayOfObject);
      this.status.setText(str);
      this.shell.layout(true);
      this.shell.getDisplay().update();
    }
    return 0;
  }

  int OnLocationChange(int paramInt1, int paramInt2, int paramInt3)
  {
    return 0;
  }

  int OnStatusChange(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return 0;
  }

  int OnSecurityChange(int paramInt1, int paramInt2, int paramInt3)
  {
    return 0;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.browser.Download
 * JD-Core Version:    0.6.2
 */