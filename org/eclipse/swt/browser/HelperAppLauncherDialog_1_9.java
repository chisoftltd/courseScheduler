package org.eclipse.swt.browser;

import org.eclipse.swt.internal.C;
import org.eclipse.swt.internal.mozilla.XPCOM;
import org.eclipse.swt.internal.mozilla.XPCOMObject;
import org.eclipse.swt.internal.mozilla.nsEmbedString;
import org.eclipse.swt.internal.mozilla.nsID;
import org.eclipse.swt.internal.mozilla.nsIHelperAppLauncherDialog_1_9;
import org.eclipse.swt.internal.mozilla.nsIHelperAppLauncher_1_9;
import org.eclipse.swt.internal.mozilla.nsISupports;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

class HelperAppLauncherDialog_1_9
{
  XPCOMObject supports;
  XPCOMObject helperAppLauncherDialog;
  int refCount = 0;

  HelperAppLauncherDialog_1_9()
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
        return HelperAppLauncherDialog_1_9.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return HelperAppLauncherDialog_1_9.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return HelperAppLauncherDialog_1_9.this.Release();
      }
    };
    this.helperAppLauncherDialog = new XPCOMObject(new int[] { 2, 0, 0, 3, 6 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return HelperAppLauncherDialog_1_9.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return HelperAppLauncherDialog_1_9.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return HelperAppLauncherDialog_1_9.this.Release();
      }

      public int method3(int[] paramAnonymousArrayOfInt)
      {
        return HelperAppLauncherDialog_1_9.this.Show(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }

      public int method4(int[] paramAnonymousArrayOfInt)
      {
        return HelperAppLauncherDialog_1_9.this.PromptForSaveToFile(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4], paramAnonymousArrayOfInt[5]);
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
    if (localnsID.Equals(nsIHelperAppLauncherDialog_1_9.NS_IHELPERAPPLAUNCHERDIALOG_IID))
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
    nsIHelperAppLauncher_1_9 localnsIHelperAppLauncher_1_9 = new nsIHelperAppLauncher_1_9(paramInt1);
    return localnsIHelperAppLauncher_1_9.SaveToDisk(0, 0);
  }

  int PromptForSaveToFile(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    int i = XPCOM.strlen_PRUnichar(paramInt3);
    char[] arrayOfChar = new char[i];
    XPCOM.memmove(arrayOfChar, paramInt3, i * 2);
    String str1 = new String(arrayOfChar);
    i = XPCOM.strlen_PRUnichar(paramInt4);
    arrayOfChar = new char[i];
    XPCOM.memmove(arrayOfChar, paramInt4, i * 2);
    String str2 = new String(arrayOfChar);
    Shell localShell = new Shell();
    FileDialog localFileDialog = new FileDialog(localShell, 8192);
    localFileDialog.setFileName(str1);
    localFileDialog.setFilterExtensions(new String[] { str2 });
    String str3 = localFileDialog.open();
    localShell.close();
    if (str3 == null)
    {
      localObject = new nsIHelperAppLauncher_1_9(paramInt1);
      int j = ((nsIHelperAppLauncher_1_9)localObject).Cancel(-2142568446);
      if (j != 0)
        Mozilla.error(j);
      return -2147467259;
    }
    Object localObject = new nsEmbedString(str3);
    int[] arrayOfInt = new int[1];
    int k = XPCOM.NS_NewLocalFile(((nsEmbedString)localObject).getAddress(), 1, arrayOfInt);
    ((nsEmbedString)localObject).dispose();
    if (k != 0)
      Mozilla.error(k);
    if (arrayOfInt[0] == 0)
      Mozilla.error(-2147467261);
    XPCOM.memmove(paramInt6, arrayOfInt, C.PTR_SIZEOF);
    return 0;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.browser.HelperAppLauncherDialog_1_9
 * JD-Core Version:    0.6.2
 */