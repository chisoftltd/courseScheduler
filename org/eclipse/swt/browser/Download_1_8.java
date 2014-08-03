package org.eclipse.swt.browser;

import org.eclipse.swt.SWT;
import org.eclipse.swt.internal.C;
import org.eclipse.swt.internal.Compatibility;
import org.eclipse.swt.internal.mozilla.XPCOM;
import org.eclipse.swt.internal.mozilla.XPCOMObject;
import org.eclipse.swt.internal.mozilla.nsICancelable;
import org.eclipse.swt.internal.mozilla.nsID;
import org.eclipse.swt.internal.mozilla.nsIDownload_1_8;
import org.eclipse.swt.internal.mozilla.nsIProgressDialog_1_8;
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

class Download_1_8
{
  XPCOMObject supports;
  XPCOMObject download;
  XPCOMObject progressDialog;
  XPCOMObject webProgressListener;
  nsICancelable cancelable;
  int refCount = 0;
  Shell shell;
  Label status;
  Button cancel;
  static final boolean is32 = C.PTR_SIZEOF == 4;

  Download_1_8()
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
        return Download_1_8.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return Download_1_8.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return Download_1_8.this.Release();
      }
    };
    this.download = new XPCOMObject(new int[] { 2, 0, 0, 4, 6, 3, 4, 3, is32 ? 10 : 6, is32 ? 8 : 7, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return Download_1_8.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return Download_1_8.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return Download_1_8.this.Release();
      }

      public int method3(int[] paramAnonymousArrayOfInt)
      {
        return Download_1_8.this.OnStateChange(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3]);
      }

      public int method4(int[] paramAnonymousArrayOfInt)
      {
        return Download_1_8.this.OnProgressChange(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4], paramAnonymousArrayOfInt[5]);
      }

      public int method5(int[] paramAnonymousArrayOfInt)
      {
        return Download_1_8.this.OnLocationChange(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }

      public int method6(int[] paramAnonymousArrayOfInt)
      {
        return Download_1_8.this.OnStatusChange(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3]);
      }

      public int method7(int[] paramAnonymousArrayOfInt)
      {
        return Download_1_8.this.OnSecurityChange(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }

      public int method8(int[] paramAnonymousArrayOfInt)
      {
        if (paramAnonymousArrayOfInt.length == 10)
          return Download_1_8.this.OnProgressChange64_32(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4], paramAnonymousArrayOfInt[5], paramAnonymousArrayOfInt[6], paramAnonymousArrayOfInt[7], paramAnonymousArrayOfInt[8], paramAnonymousArrayOfInt[9]);
        return Download_1_8.this.OnProgressChange64(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4], paramAnonymousArrayOfInt[5]);
      }

      public int method9(int[] paramAnonymousArrayOfInt)
      {
        if (paramAnonymousArrayOfInt.length == 8)
          return Download_1_8.this.Init_32(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4], paramAnonymousArrayOfInt[5], paramAnonymousArrayOfInt[6], paramAnonymousArrayOfInt[7]);
        return Download_1_8.this.Init(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4], paramAnonymousArrayOfInt[5], paramAnonymousArrayOfInt[6]);
      }

      public int method10(int[] paramAnonymousArrayOfInt)
      {
        return Download_1_8.this.GetTargetFile(paramAnonymousArrayOfInt[0]);
      }

      public int method11(int[] paramAnonymousArrayOfInt)
      {
        return Download_1_8.this.GetPercentComplete(paramAnonymousArrayOfInt[0]);
      }

      public int method12(int[] paramAnonymousArrayOfInt)
      {
        return Download_1_8.this.GetAmountTransferred(paramAnonymousArrayOfInt[0]);
      }

      public int method13(int[] paramAnonymousArrayOfInt)
      {
        return Download_1_8.this.GetSize(paramAnonymousArrayOfInt[0]);
      }

      public int method14(int[] paramAnonymousArrayOfInt)
      {
        return Download_1_8.this.GetSource(paramAnonymousArrayOfInt[0]);
      }

      public int method15(int[] paramAnonymousArrayOfInt)
      {
        return Download_1_8.this.GetTarget(paramAnonymousArrayOfInt[0]);
      }

      public int method16(int[] paramAnonymousArrayOfInt)
      {
        return Download_1_8.this.GetCancelable(paramAnonymousArrayOfInt[0]);
      }

      public int method17(int[] paramAnonymousArrayOfInt)
      {
        return Download_1_8.this.GetDisplayName(paramAnonymousArrayOfInt[0]);
      }

      public int method18(int[] paramAnonymousArrayOfInt)
      {
        return Download_1_8.this.GetStartTime(paramAnonymousArrayOfInt[0]);
      }

      public int method19(int[] paramAnonymousArrayOfInt)
      {
        return Download_1_8.this.GetMIMEInfo(paramAnonymousArrayOfInt[0]);
      }
    };
    this.progressDialog = new XPCOMObject(new int[] { 2, 0, 0, 4, 6, 3, 4, 3, is32 ? 10 : 6, is32 ? 8 : 7, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return Download_1_8.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return Download_1_8.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return Download_1_8.this.Release();
      }

      public int method3(int[] paramAnonymousArrayOfInt)
      {
        return Download_1_8.this.OnStateChange(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3]);
      }

      public int method4(int[] paramAnonymousArrayOfInt)
      {
        return Download_1_8.this.OnProgressChange(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4], paramAnonymousArrayOfInt[5]);
      }

      public int method5(int[] paramAnonymousArrayOfInt)
      {
        return Download_1_8.this.OnLocationChange(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }

      public int method6(int[] paramAnonymousArrayOfInt)
      {
        return Download_1_8.this.OnStatusChange(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3]);
      }

      public int method7(int[] paramAnonymousArrayOfInt)
      {
        return Download_1_8.this.OnSecurityChange(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }

      public int method8(int[] paramAnonymousArrayOfInt)
      {
        if (paramAnonymousArrayOfInt.length == 10)
          return Download_1_8.this.OnProgressChange64_32(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4], paramAnonymousArrayOfInt[5], paramAnonymousArrayOfInt[6], paramAnonymousArrayOfInt[7], paramAnonymousArrayOfInt[8], paramAnonymousArrayOfInt[9]);
        return Download_1_8.this.OnProgressChange64(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4], paramAnonymousArrayOfInt[5]);
      }

      public int method9(int[] paramAnonymousArrayOfInt)
      {
        if (paramAnonymousArrayOfInt.length == 8)
          return Download_1_8.this.Init_32(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4], paramAnonymousArrayOfInt[5], paramAnonymousArrayOfInt[6], paramAnonymousArrayOfInt[7]);
        return Download_1_8.this.Init(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4], paramAnonymousArrayOfInt[5], paramAnonymousArrayOfInt[6]);
      }

      public int method10(int[] paramAnonymousArrayOfInt)
      {
        return Download_1_8.this.GetTargetFile(paramAnonymousArrayOfInt[0]);
      }

      public int method11(int[] paramAnonymousArrayOfInt)
      {
        return Download_1_8.this.GetPercentComplete(paramAnonymousArrayOfInt[0]);
      }

      public int method12(int[] paramAnonymousArrayOfInt)
      {
        return Download_1_8.this.GetAmountTransferred(paramAnonymousArrayOfInt[0]);
      }

      public int method13(int[] paramAnonymousArrayOfInt)
      {
        return Download_1_8.this.GetSize(paramAnonymousArrayOfInt[0]);
      }

      public int method14(int[] paramAnonymousArrayOfInt)
      {
        return Download_1_8.this.GetSource(paramAnonymousArrayOfInt[0]);
      }

      public int method15(int[] paramAnonymousArrayOfInt)
      {
        return Download_1_8.this.GetTarget(paramAnonymousArrayOfInt[0]);
      }

      public int method16(int[] paramAnonymousArrayOfInt)
      {
        return Download_1_8.this.GetCancelable(paramAnonymousArrayOfInt[0]);
      }

      public int method17(int[] paramAnonymousArrayOfInt)
      {
        return Download_1_8.this.GetDisplayName(paramAnonymousArrayOfInt[0]);
      }

      public int method18(int[] paramAnonymousArrayOfInt)
      {
        return Download_1_8.this.GetStartTime(paramAnonymousArrayOfInt[0]);
      }

      public int method19(int[] paramAnonymousArrayOfInt)
      {
        return Download_1_8.this.GetMIMEInfo(paramAnonymousArrayOfInt[0]);
      }

      public int method20(int[] paramAnonymousArrayOfInt)
      {
        return Download_1_8.this.Open(paramAnonymousArrayOfInt[0]);
      }

      public int method21(int[] paramAnonymousArrayOfInt)
      {
        return Download_1_8.this.GetCancelDownloadOnClose(paramAnonymousArrayOfInt[0]);
      }

      public int method22(int[] paramAnonymousArrayOfInt)
      {
        return Download_1_8.this.SetCancelDownloadOnClose(paramAnonymousArrayOfInt[0]);
      }

      public int method23(int[] paramAnonymousArrayOfInt)
      {
        return Download_1_8.this.GetObserver(paramAnonymousArrayOfInt[0]);
      }

      public int method24(int[] paramAnonymousArrayOfInt)
      {
        return Download_1_8.this.SetObserver(paramAnonymousArrayOfInt[0]);
      }

      public int method25(int[] paramAnonymousArrayOfInt)
      {
        return Download_1_8.this.GetDialog(paramAnonymousArrayOfInt[0]);
      }

      public int method26(int[] paramAnonymousArrayOfInt)
      {
        return Download_1_8.this.SetDialog(paramAnonymousArrayOfInt[0]);
      }
    };
    this.webProgressListener = new XPCOMObject(new int[] { 2, 0, 0, 4, 6, 3, 4, 3 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return Download_1_8.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return Download_1_8.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return Download_1_8.this.Release();
      }

      public int method3(int[] paramAnonymousArrayOfInt)
      {
        return Download_1_8.this.OnStateChange(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3]);
      }

      public int method4(int[] paramAnonymousArrayOfInt)
      {
        return Download_1_8.this.OnProgressChange(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4], paramAnonymousArrayOfInt[5]);
      }

      public int method5(int[] paramAnonymousArrayOfInt)
      {
        return Download_1_8.this.OnLocationChange(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }

      public int method6(int[] paramAnonymousArrayOfInt)
      {
        return Download_1_8.this.OnStatusChange(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3]);
      }

      public int method7(int[] paramAnonymousArrayOfInt)
      {
        return Download_1_8.this.OnSecurityChange(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
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
    if (localnsID.Equals(nsIDownload_1_8.NS_IDOWNLOAD_IID))
    {
      XPCOM.memmove(paramInt2, new int[] { this.download.getAddress() }, C.PTR_SIZEOF);
      AddRef();
      return 0;
    }
    if (localnsID.Equals(nsIProgressDialog_1_8.NS_IPROGRESSDIALOG_IID))
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

  int Init_32(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8)
  {
    long l = (paramInt6 << 32) + paramInt5;
    return Init(paramInt1, paramInt2, paramInt3, paramInt4, l, paramInt7, paramInt8);
  }

  int Init(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong, int paramInt5, int paramInt6)
  {
    this.cancelable = new nsICancelable(paramInt6);
    nsIURI localnsIURI1 = new nsIURI(paramInt1);
    int i = XPCOM.nsEmbedCString_new();
    int j = localnsIURI1.GetHost(i);
    if (j != 0)
      Mozilla.error(j);
    int k = XPCOM.nsEmbedCString_Length(i);
    int m = XPCOM.nsEmbedCString_get(i);
    byte[] arrayOfByte = new byte[k];
    XPCOM.memmove(arrayOfByte, m, k);
    XPCOM.nsEmbedCString_delete(i);
    String str1 = new String(arrayOfByte);
    nsIURI localnsIURI2 = new nsIURI(paramInt2);
    int n = XPCOM.nsEmbedCString_new();
    j = localnsIURI2.GetPath(n);
    if (j != 0)
      Mozilla.error(j);
    k = XPCOM.nsEmbedCString_Length(n);
    m = XPCOM.nsEmbedCString_get(n);
    arrayOfByte = new byte[k];
    XPCOM.memmove(arrayOfByte, m, k);
    XPCOM.nsEmbedCString_delete(n);
    String str2 = new String(arrayOfByte);
    int i1 = str2.lastIndexOf(System.getProperty("file.separator"));
    str2 = str2.substring(i1 + 1);
    Listener local5 = new Listener()
    {
      public void handleEvent(Event paramAnonymousEvent)
      {
        if (paramAnonymousEvent.widget == Download_1_8.this.cancel)
          Download_1_8.this.shell.close();
        if (Download_1_8.this.cancelable != null)
        {
          int i = Download_1_8.this.cancelable.Cancel(-2142568446);
          if (i != 0)
            Mozilla.error(i);
        }
        Download_1_8.this.shell = null;
        Download_1_8.this.cancelable = null;
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
    this.cancel.addListener(13, local5);
    this.shell.addListener(21, local5);
    this.shell.pack();
    this.shell.open();
    return 0;
  }

  int GetAmountTransferred(int paramInt)
  {
    return -2147467263;
  }

  int GetCancelable(int paramInt)
  {
    return -2147467263;
  }

  int GetDisplayName(int paramInt)
  {
    return -2147467263;
  }

  int GetMIMEInfo(int paramInt)
  {
    return -2147467263;
  }

  int GetPercentComplete(int paramInt)
  {
    return -2147467263;
  }

  int GetSize(int paramInt)
  {
    return -2147467263;
  }

  int GetSource(int paramInt)
  {
    return -2147467263;
  }

  int GetStartTime(int paramInt)
  {
    return -2147467263;
  }

  int GetTarget(int paramInt)
  {
    return -2147467263;
  }

  int GetTargetFile(int paramInt)
  {
    return -2147467263;
  }

  int GetCancelDownloadOnClose(int paramInt)
  {
    return -2147467263;
  }

  int GetDialog(int paramInt)
  {
    return -2147467263;
  }

  int GetObserver(int paramInt)
  {
    return -2147467263;
  }

  int Open(int paramInt)
  {
    return -2147467263;
  }

  int SetCancelDownloadOnClose(int paramInt)
  {
    return -2147467263;
  }

  int SetDialog(int paramInt)
  {
    return -2147467263;
  }

  int SetObserver(int paramInt)
  {
    return -2147467263;
  }

  int OnLocationChange(int paramInt1, int paramInt2, int paramInt3)
  {
    return 0;
  }

  int OnProgressChange(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    return OnProgressChange64(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
  }

  int OnProgressChange64_32(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10)
  {
    long l1 = (paramInt4 << 32) + paramInt3;
    long l2 = (paramInt6 << 32) + paramInt5;
    long l3 = (paramInt8 << 32) + paramInt7;
    long l4 = (paramInt10 << 32) + paramInt9;
    return OnProgressChange64(paramInt1, paramInt2, l1, l2, l3, l4);
  }

  int OnProgressChange64(int paramInt1, int paramInt2, long paramLong1, long paramLong2, long paramLong3, long paramLong4)
  {
    long l1 = paramLong3 / 1024L;
    long l2 = paramLong4 / 1024L;
    if ((this.shell != null) && (!this.shell.isDisposed()))
    {
      Object[] arrayOfObject = { new Long(l1), new Long(l2) };
      String str = Compatibility.getMessage("SWT_Download_Status", arrayOfObject);
      this.status.setText(str);
      this.shell.layout(true);
      this.shell.getDisplay().update();
    }
    return 0;
  }

  int OnSecurityChange(int paramInt1, int paramInt2, int paramInt3)
  {
    return 0;
  }

  int OnStateChange(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((paramInt3 & 0x10) != 0)
    {
      this.cancelable = null;
      if ((this.shell != null) && (!this.shell.isDisposed()))
        this.shell.dispose();
      this.shell = null;
    }
    return 0;
  }

  int OnStatusChange(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return 0;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.browser.Download_1_8
 * JD-Core Version:    0.6.2
 */