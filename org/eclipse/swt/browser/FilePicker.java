package org.eclipse.swt.browser;

import org.eclipse.swt.internal.C;
import org.eclipse.swt.internal.mozilla.XPCOM;
import org.eclipse.swt.internal.mozilla.XPCOMObject;
import org.eclipse.swt.internal.mozilla.nsEmbedString;
import org.eclipse.swt.internal.mozilla.nsID;
import org.eclipse.swt.internal.mozilla.nsIDOMWindow;
import org.eclipse.swt.internal.mozilla.nsIFilePicker;
import org.eclipse.swt.internal.mozilla.nsIFilePicker_1_8;
import org.eclipse.swt.internal.mozilla.nsILocalFile;
import org.eclipse.swt.internal.mozilla.nsISupports;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

class FilePicker
{
  XPCOMObject supports;
  XPCOMObject filePicker;
  int refCount = 0;
  short mode;
  int parentHandle;
  String[] files;
  String[] masks;
  String defaultFilename;
  String directory;
  String title;
  static final String SEPARATOR = System.getProperty("file.separator");

  FilePicker()
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
        return FilePicker.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return FilePicker.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return FilePicker.this.Release();
      }
    };
    this.filePicker = new XPCOMObject(new int[] { 2, 0, 0, 3, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return FilePicker.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return FilePicker.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return FilePicker.this.Release();
      }

      public int method3(int[] paramAnonymousArrayOfInt)
      {
        return FilePicker.this.Init(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], (short)paramAnonymousArrayOfInt[2]);
      }

      public int method4(int[] paramAnonymousArrayOfInt)
      {
        return FilePicker.this.AppendFilters(paramAnonymousArrayOfInt[0]);
      }

      public int method5(int[] paramAnonymousArrayOfInt)
      {
        return FilePicker.this.AppendFilter(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method6(int[] paramAnonymousArrayOfInt)
      {
        return FilePicker.this.GetDefaultString(paramAnonymousArrayOfInt[0]);
      }

      public int method7(int[] paramAnonymousArrayOfInt)
      {
        return FilePicker.this.SetDefaultString(paramAnonymousArrayOfInt[0]);
      }

      public int method8(int[] paramAnonymousArrayOfInt)
      {
        return FilePicker.this.GetDefaultExtension(paramAnonymousArrayOfInt[0]);
      }

      public int method9(int[] paramAnonymousArrayOfInt)
      {
        return FilePicker.this.SetDefaultExtension(paramAnonymousArrayOfInt[0]);
      }

      public int method10(int[] paramAnonymousArrayOfInt)
      {
        return FilePicker.this.GetFilterIndex(paramAnonymousArrayOfInt[0]);
      }

      public int method11(int[] paramAnonymousArrayOfInt)
      {
        return FilePicker.this.SetFilterIndex(paramAnonymousArrayOfInt[0]);
      }

      public int method12(int[] paramAnonymousArrayOfInt)
      {
        return FilePicker.this.GetDisplayDirectory(paramAnonymousArrayOfInt[0]);
      }

      public int method13(int[] paramAnonymousArrayOfInt)
      {
        return FilePicker.this.SetDisplayDirectory(paramAnonymousArrayOfInt[0]);
      }

      public int method14(int[] paramAnonymousArrayOfInt)
      {
        return FilePicker.this.GetFile(paramAnonymousArrayOfInt[0]);
      }

      public int method15(int[] paramAnonymousArrayOfInt)
      {
        return FilePicker.this.GetFileURL(paramAnonymousArrayOfInt[0]);
      }

      public int method16(int[] paramAnonymousArrayOfInt)
      {
        return FilePicker.this.GetFiles(paramAnonymousArrayOfInt[0]);
      }

      public int method17(int[] paramAnonymousArrayOfInt)
      {
        return FilePicker.this.Show(paramAnonymousArrayOfInt[0]);
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
    if (this.filePicker != null)
    {
      this.filePicker.dispose();
      this.filePicker = null;
    }
  }

  int getAddress()
  {
    return this.filePicker.getAddress();
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
    if (localnsID.Equals(nsIFilePicker.NS_IFILEPICKER_IID))
    {
      XPCOM.memmove(paramInt2, new int[] { this.filePicker.getAddress() }, C.PTR_SIZEOF);
      AddRef();
      return 0;
    }
    if (localnsID.Equals(nsIFilePicker_1_8.NS_IFILEPICKER_IID))
    {
      XPCOM.memmove(paramInt2, new int[] { this.filePicker.getAddress() }, C.PTR_SIZEOF);
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

  Browser getBrowser(int paramInt)
  {
    if (paramInt == 0)
      return null;
    nsIDOMWindow localnsIDOMWindow = new nsIDOMWindow(paramInt);
    return Mozilla.findBrowser(localnsIDOMWindow);
  }

  String parseAString(int paramInt)
  {
    return null;
  }

  int Init(int paramInt1, int paramInt2, short paramShort)
  {
    this.parentHandle = paramInt1;
    this.mode = paramShort;
    this.title = parseAString(paramInt2);
    return 0;
  }

  int Show(int paramInt)
  {
    if (this.mode == 2)
    {
      i = showDirectoryPicker();
      XPCOM.memmove(paramInt, new short[] { (short)i }, 2);
      return 0;
    }
    int i = this.mode == 1 ? 8192 : 4096;
    if (this.mode == 3)
      i |= 2;
    Browser localBrowser = getBrowser(this.parentHandle);
    Shell localShell = null;
    if (localBrowser != null)
      localShell = localBrowser.getShell();
    else
      localShell = new Shell();
    FileDialog localFileDialog = new FileDialog(localShell, i);
    if (this.title != null)
      localFileDialog.setText(this.title);
    if (this.directory != null)
      localFileDialog.setFilterPath(this.directory);
    if (this.masks != null)
      localFileDialog.setFilterExtensions(this.masks);
    if (this.defaultFilename != null)
      localFileDialog.setFileName(this.defaultFilename);
    String str = localFileDialog.open();
    this.files = localFileDialog.getFileNames();
    this.directory = localFileDialog.getFilterPath();
    this.title = (this.defaultFilename = null);
    this.masks = null;
    int j = str == null ? 1 : 0;
    XPCOM.memmove(paramInt, new short[] { (short)j }, 2);
    return 0;
  }

  int showDirectoryPicker()
  {
    Browser localBrowser = getBrowser(this.parentHandle);
    Shell localShell = null;
    if (localBrowser != null)
      localShell = localBrowser.getShell();
    else
      localShell = new Shell();
    DirectoryDialog localDirectoryDialog = new DirectoryDialog(localShell, 0);
    if (this.title != null)
      localDirectoryDialog.setText(this.title);
    if (this.directory != null)
      localDirectoryDialog.setFilterPath(this.directory);
    this.directory = localDirectoryDialog.open();
    this.title = (this.defaultFilename = null);
    this.files = (this.masks = null);
    return this.directory == null ? 1 : 0;
  }

  int GetFiles(int paramInt)
  {
    return -2147467263;
  }

  int GetFileURL(int paramInt)
  {
    return -2147467263;
  }

  int GetFile(int paramInt)
  {
    String str = "";
    if (this.directory != null)
      str = str + this.directory + SEPARATOR;
    if ((this.files != null) && (this.files.length > 0))
      str = str + this.files[0];
    nsEmbedString localnsEmbedString = new nsEmbedString(str);
    int[] arrayOfInt = new int[1];
    int i = XPCOM.NS_NewLocalFile(localnsEmbedString.getAddress(), 1, arrayOfInt);
    localnsEmbedString.dispose();
    if (i != 0)
      Mozilla.error(i);
    if (arrayOfInt[0] == 0)
      Mozilla.error(-2147467261);
    XPCOM.memmove(paramInt, arrayOfInt, C.PTR_SIZEOF);
    return 0;
  }

  int SetDisplayDirectory(int paramInt)
  {
    if (paramInt == 0)
      return 0;
    nsILocalFile localnsILocalFile = new nsILocalFile(paramInt);
    int i = XPCOM.nsEmbedCString_new();
    localnsILocalFile.GetNativePath(i);
    int j = XPCOM.nsEmbedCString_Length(i);
    int k = XPCOM.nsEmbedCString_get(i);
    byte[] arrayOfByte = new byte[j];
    XPCOM.memmove(arrayOfByte, k, j);
    XPCOM.nsEmbedCString_delete(i);
    char[] arrayOfChar = MozillaDelegate.mbcsToWcs(null, arrayOfByte);
    this.directory = new String(arrayOfChar);
    return 0;
  }

  int GetDisplayDirectory(int paramInt)
  {
    String str = this.directory != null ? this.directory : "";
    nsEmbedString localnsEmbedString = new nsEmbedString(str);
    int[] arrayOfInt = new int[1];
    int i = XPCOM.NS_NewLocalFile(localnsEmbedString.getAddress(), 1, arrayOfInt);
    localnsEmbedString.dispose();
    if (i != 0)
      Mozilla.error(i);
    if (arrayOfInt[0] == 0)
      Mozilla.error(-2147467261);
    XPCOM.memmove(paramInt, arrayOfInt, C.PTR_SIZEOF);
    return 0;
  }

  int SetFilterIndex(int paramInt)
  {
    return -2147467263;
  }

  int GetFilterIndex(int paramInt)
  {
    return -2147467263;
  }

  int SetDefaultExtension(int paramInt)
  {
    return -2147467263;
  }

  int GetDefaultExtension(int paramInt)
  {
    return -2147467263;
  }

  int SetDefaultString(int paramInt)
  {
    this.defaultFilename = parseAString(paramInt);
    return 0;
  }

  int GetDefaultString(int paramInt)
  {
    return -2147467263;
  }

  int AppendFilter(int paramInt1, int paramInt2)
  {
    return -2147467263;
  }

  int AppendFilters(int paramInt)
  {
    String[] arrayOfString1 = (String[])null;
    switch (paramInt)
    {
    case 1:
    case 64:
      this.masks = null;
      break;
    case 2:
      arrayOfString1 = new String[] { "*.htm;*.html" };
      break;
    case 8:
      arrayOfString1 = new String[] { "*.gif;*.jpeg;*.jpg;*.png" };
      break;
    case 4:
      arrayOfString1 = new String[] { "*.txt" };
      break;
    case 16:
      arrayOfString1 = new String[] { "*.xml" };
      break;
    case 32:
      arrayOfString1 = new String[] { "*.xul" };
    }
    if (this.masks == null)
    {
      this.masks = arrayOfString1;
    }
    else if (arrayOfString1 != null)
    {
      String[] arrayOfString2 = new String[this.masks.length + arrayOfString1.length];
      System.arraycopy(this.masks, 0, arrayOfString2, 0, this.masks.length);
      System.arraycopy(arrayOfString1, 0, arrayOfString2, this.masks.length, arrayOfString1.length);
      this.masks = arrayOfString2;
    }
    return 0;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.browser.FilePicker
 * JD-Core Version:    0.6.2
 */