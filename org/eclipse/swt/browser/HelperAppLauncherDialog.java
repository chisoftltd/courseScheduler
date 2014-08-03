package org.eclipse.swt.browser;

import org.eclipse.swt.internal.C;
import org.eclipse.swt.internal.mozilla.XPCOM;
import org.eclipse.swt.internal.mozilla.XPCOMObject;
import org.eclipse.swt.internal.mozilla.nsEmbedString;
import org.eclipse.swt.internal.mozilla.nsID;
import org.eclipse.swt.internal.mozilla.nsIHelperAppLauncher;
import org.eclipse.swt.internal.mozilla.nsIHelperAppLauncherDialog;
import org.eclipse.swt.internal.mozilla.nsIHelperAppLauncher_1_8;
import org.eclipse.swt.internal.mozilla.nsISupports;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

class HelperAppLauncherDialog
{
  XPCOMObject supports;
  XPCOMObject helperAppLauncherDialog;
  int refCount = 0;

  HelperAppLauncherDialog()
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
        return HelperAppLauncherDialog.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return HelperAppLauncherDialog.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return HelperAppLauncherDialog.this.Release();
      }
    };
    this.helperAppLauncherDialog = new XPCOMObject(new int[] { 2, 0, 0, 3, 5 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return HelperAppLauncherDialog.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return HelperAppLauncherDialog.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return HelperAppLauncherDialog.this.Release();
      }

      public int method3(int[] paramAnonymousArrayOfInt)
      {
        return HelperAppLauncherDialog.this.Show(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }

      public int method4(int[] paramAnonymousArrayOfInt)
      {
        return HelperAppLauncherDialog.this.PromptForSaveToFile(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4]);
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
    if (this.helperAppLauncherDialog != null)
    {
      this.helperAppLauncherDialog.dispose();
      this.helperAppLauncherDialog = null;
    }
  }

  int getAddress()
  {
    return this.helperAppLauncherDialog.getAddress();
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
    if (localnsID.Equals(nsIHelperAppLauncherDialog.NS_IHELPERAPPLAUNCHERDIALOG_IID))
    {
      XPCOM.memmove(paramInt2, new int[] { this.helperAppLauncherDialog.getAddress() }, C.PTR_SIZEOF);
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

  int Show(int paramInt1, int paramInt2, int paramInt3)
  {
    nsISupports localnsISupports = new nsISupports(paramInt1);
    int[] arrayOfInt = new int[1];
    int i = localnsISupports.QueryInterface(nsIHelperAppLauncher_1_8.NS_IHELPERAPPLAUNCHER_IID, arrayOfInt);
    if (i == 0)
    {
      localObject = new nsIHelperAppLauncher_1_8(paramInt1);
      i = ((nsIHelperAppLauncher_1_8)localObject).SaveToDisk(0, 0);
      ((nsIHelperAppLauncher_1_8)localObject).Release();
      return i;
    }
    Object localObject = new nsIHelperAppLauncher(paramInt1);
    return ((nsIHelperAppLauncher)localObject).SaveToDisk(0, 0);
  }

  int PromptForSaveToFile(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    int m = 0;
    int n = 0;
    nsISupports localnsISupports = new nsISupports(paramInt1);
    int[] arrayOfInt = new int[1];
    int i1 = localnsISupports.QueryInterface(nsIHelperAppLauncher_1_8.NS_IHELPERAPPLAUNCHER_IID, arrayOfInt);
    if (i1 == 0)
    {
      n = 1;
      m = 1;
      new nsISupports(arrayOfInt[0]).Release();
    }
    else
    {
      arrayOfInt[0] = 0;
      i1 = localnsISupports.QueryInterface(nsIHelperAppLauncher.NS_IHELPERAPPLAUNCHER_IID, arrayOfInt);
      if (i1 == 0)
      {
        m = 1;
        new nsISupports(arrayOfInt[0]).Release();
      }
    }
    arrayOfInt[0] = 0;
    int i;
    int j;
    int k;
    if (m != 0)
    {
      i = paramInt3;
      j = paramInt4;
      k = paramInt5;
    }
    else
    {
      i = paramInt2;
      j = paramInt3;
      k = paramInt4;
    }
    int i2 = XPCOM.strlen_PRUnichar(i);
    char[] arrayOfChar = new char[i2];
    XPCOM.memmove(arrayOfChar, i, i2 * 2);
    String str1 = new String(arrayOfChar);
    i2 = XPCOM.strlen_PRUnichar(j);
    arrayOfChar = new char[i2];
    XPCOM.memmove(arrayOfChar, j, i2 * 2);
    String str2 = new String(arrayOfChar);
    Shell localShell = new Shell();
    FileDialog localFileDialog = new FileDialog(localShell, 8192);
    localFileDialog.setFileName(str1);
    localFileDialog.setFilterExtensions(new String[] { str2 });
    String str3 = localFileDialog.open();
    localShell.close();
    if (str3 == null)
    {
      if (m != 0)
      {
        if (n != 0)
        {
          localObject = new nsIHelperAppLauncher_1_8(paramInt1);
          i1 = ((nsIHelperAppLauncher_1_8)localObject).Cancel(-2142568446);
        }
        else
        {
          localObject = new nsIHelperAppLauncher(paramInt1);
          i1 = ((nsIHelperAppLauncher)localObject).Cancel();
        }
        if (i1 != 0)
          Mozilla.error(i1);
        return 0;
      }
      return -2147467259;
    }
    Object localObject = new nsEmbedString(str3);
    i1 = XPCOM.NS_NewLocalFile(((nsEmbedString)localObject).getAddress(), 1, arrayOfInt);
    ((nsEmbedString)localObject).dispose();
    if (i1 != 0)
      Mozilla.error(i1);
    if (arrayOfInt[0] == 0)
      Mozilla.error(-2147467261);
    XPCOM.memmove(k, arrayOfInt, C.PTR_SIZEOF);
    return 0;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.browser.HelperAppLauncherDialog
 * JD-Core Version:    0.6.2
 */