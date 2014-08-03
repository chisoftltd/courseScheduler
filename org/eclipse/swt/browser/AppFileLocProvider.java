package org.eclipse.swt.browser;

import java.util.Vector;
import org.eclipse.swt.internal.C;
import org.eclipse.swt.internal.Compatibility;
import org.eclipse.swt.internal.mozilla.XPCOM;
import org.eclipse.swt.internal.mozilla.XPCOMObject;
import org.eclipse.swt.internal.mozilla.nsEmbedString;
import org.eclipse.swt.internal.mozilla.nsID;
import org.eclipse.swt.internal.mozilla.nsIDirectoryServiceProvider;
import org.eclipse.swt.internal.mozilla.nsIDirectoryServiceProvider2;
import org.eclipse.swt.internal.mozilla.nsIFile;
import org.eclipse.swt.internal.mozilla.nsILocalFile;
import org.eclipse.swt.internal.mozilla.nsISupports;

class AppFileLocProvider
{
  XPCOMObject supports;
  XPCOMObject directoryServiceProvider;
  XPCOMObject directoryServiceProvider2;
  int refCount = 0;
  String mozillaPath;
  String profilePath;
  String[] pluginDirs;
  boolean isXULRunner;
  static final String SEPARATOR_OS = System.getProperty("file.separator");
  static final String CHROME_DIR = "chrome";
  static final String COMPONENTS_DIR = "components";
  static final String HISTORY_FILE = "history.dat";
  static final String LOCALSTORE_FILE = "localstore.rdf";
  static final String MIMETYPES_FILE = "mimeTypes.rdf";
  static final String PLUGINS_DIR = "plugins";
  static final String USER_PLUGINS_DIR = ".mozilla" + SEPARATOR_OS + "plugins";
  static final String PREFERENCES_FILE = "prefs.js";
  static boolean IsSparc = ((str1.startsWith("sunos")) || (str1.startsWith("solaris"))) && (str2.startsWith("sparc"));

  static
  {
    String str1 = System.getProperty("os.name").toLowerCase();
    String str2 = System.getProperty("os.arch").toLowerCase();
  }

  AppFileLocProvider(String paramString1, String paramString2, boolean paramBoolean)
  {
    this.mozillaPath = (paramString1 + SEPARATOR_OS);
    this.profilePath = (paramString2 + SEPARATOR_OS);
    this.isXULRunner = paramBoolean;
    if (!Compatibility.fileExists(paramString2, ""))
    {
      int[] arrayOfInt = new int[1];
      nsEmbedString localnsEmbedString = new nsEmbedString(paramString2);
      int i = XPCOM.NS_NewLocalFile(localnsEmbedString.getAddress(), 1, arrayOfInt);
      if (i != 0)
        Mozilla.error(i);
      if (arrayOfInt[0] == 0)
        Mozilla.error(-2147467261);
      localnsEmbedString.dispose();
      nsILocalFile localnsILocalFile = new nsILocalFile(arrayOfInt[0]);
      i = localnsILocalFile.Create(1, 448);
      if (i != 0)
        Mozilla.error(i);
      localnsILocalFile.Release();
    }
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
        return AppFileLocProvider.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return AppFileLocProvider.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return AppFileLocProvider.this.Release();
      }
    };
    this.directoryServiceProvider = new XPCOMObject(new int[] { 2, 0, 0, 3 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return AppFileLocProvider.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return AppFileLocProvider.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return AppFileLocProvider.this.Release();
      }

      public int method3(int[] paramAnonymousArrayOfInt)
      {
        return AppFileLocProvider.this.getFile(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }
    };
    this.directoryServiceProvider2 = new XPCOMObject(new int[] { 2, 0, 0, 3, 2 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return AppFileLocProvider.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return AppFileLocProvider.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return AppFileLocProvider.this.Release();
      }

      public int method3(int[] paramAnonymousArrayOfInt)
      {
        return AppFileLocProvider.this.getFile(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }

      public int method4(int[] paramAnonymousArrayOfInt)
      {
        return AppFileLocProvider.this.getFiles(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
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
    if (this.directoryServiceProvider != null)
    {
      this.directoryServiceProvider.dispose();
      this.directoryServiceProvider = null;
    }
    if (this.directoryServiceProvider2 != null)
    {
      this.directoryServiceProvider2.dispose();
      this.directoryServiceProvider2 = null;
    }
  }

  int getAddress()
  {
    return this.directoryServiceProvider.getAddress();
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
    if (localnsID.Equals(nsIDirectoryServiceProvider.NS_IDIRECTORYSERVICEPROVIDER_IID))
    {
      XPCOM.memmove(paramInt2, new int[] { this.directoryServiceProvider.getAddress() }, C.PTR_SIZEOF);
      AddRef();
      return 0;
    }
    if (localnsID.Equals(nsIDirectoryServiceProvider2.NS_IDIRECTORYSERVICEPROVIDER2_IID))
    {
      XPCOM.memmove(paramInt2, new int[] { this.directoryServiceProvider2.getAddress() }, C.PTR_SIZEOF);
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

  int getFiles(int paramInt1, int paramInt2)
  {
    int i = XPCOM.strlen(paramInt1);
    byte[] arrayOfByte1 = new byte[i];
    XPCOM.memmove(arrayOfByte1, paramInt1, i);
    String str1 = new String(MozillaDelegate.mbcsToWcs(null, arrayOfByte1));
    String[] arrayOfString = (String[])null;
    int m;
    Object localObject3;
    Object localObject4;
    if (str1.equals("APluginsDL"))
    {
      if (this.pluginDirs == null)
      {
        int j = 0;
        int k = C.getenv(MozillaDelegate.wcsToMbcs(null, "MOZ_PLUGIN_PATH", true));
        if (k != 0)
        {
          m = C.strlen(k);
          byte[] arrayOfByte2 = new byte[m];
          C.memmove(arrayOfByte2, k, m);
          localObject3 = new String(MozillaDelegate.mbcsToWcs(null, arrayOfByte2));
          if (((String)localObject3).length() > 0)
          {
            String str2 = System.getProperty("path.separator");
            localObject4 = new Vector();
            int i3 = -1;
            do
            {
              int i2 = i3 + 1;
              i3 = ((String)localObject3).indexOf(str2, i2);
              String str3;
              if (i3 == -1)
                str3 = ((String)localObject3).substring(i2);
              else
                str3 = ((String)localObject3).substring(i2, i3);
              if (str3.length() > 0)
                ((Vector)localObject4).addElement(str3);
            }
            while (i3 != -1);
            int i4 = ((Vector)localObject4).size();
            this.pluginDirs = new String[i4 + (IsSparc ? 1 : 2)];
            for (j = 0; j < i4; j++)
              this.pluginDirs[j] = ((String)((Vector)localObject4).elementAt(j));
          }
        }
        if (this.pluginDirs == null)
          this.pluginDirs = new String[IsSparc ? 1 : 2];
        if (!IsSparc)
          this.pluginDirs[(j++)] = (this.mozillaPath + "plugins");
        this.pluginDirs[(j++)] = (System.getProperty("user.home") + SEPARATOR_OS + USER_PLUGINS_DIR);
      }
      arrayOfString = this.pluginDirs;
    }
    XPCOM.memmove(paramInt2, new int[1], C.PTR_SIZEOF);
    if (arrayOfString != null)
    {
      int[] arrayOfInt = new int[1];
      Object localObject1 = new nsISupports[arrayOfString.length];
      m = 0;
      for (int n = 0; n < arrayOfString.length; n++)
      {
        localObject3 = new nsEmbedString(arrayOfString[n]);
        int i1 = XPCOM.NS_NewLocalFile(((nsEmbedString)localObject3).getAddress(), 1, arrayOfInt);
        if (i1 != -2142109695)
        {
          if (i1 != 0)
            Mozilla.error(i1);
          if (arrayOfInt[0] == 0)
            Mozilla.error(-2147467261);
          localObject4 = new nsILocalFile(arrayOfInt[0]);
          arrayOfInt[0] = 0;
          i1 = ((nsILocalFile)localObject4).QueryInterface(nsIFile.NS_IFILE_IID, arrayOfInt);
          if (i1 != 0)
            Mozilla.error(i1);
          if (arrayOfInt[0] == 0)
            Mozilla.error(-2147467262);
          ((nsILocalFile)localObject4).Release();
          nsIFile localnsIFile = new nsIFile(arrayOfInt[0]);
          localObject1[(m++)] = localnsIFile;
        }
        ((nsEmbedString)localObject3).dispose();
        arrayOfInt[0] = 0;
      }
      if (m < arrayOfString.length)
      {
        localObject2 = new nsISupports[m];
        System.arraycopy(localObject1, 0, localObject2, 0, m);
        localObject1 = localObject2;
      }
      Object localObject2 = new SimpleEnumerator((nsISupports[])localObject1);
      ((SimpleEnumerator)localObject2).AddRef();
      XPCOM.memmove(paramInt2, new int[] { ((SimpleEnumerator)localObject2).getAddress() }, C.PTR_SIZEOF);
      return 0;
    }
    return -2147467259;
  }

  int getFile(int paramInt1, int paramInt2, int paramInt3)
  {
    int i = XPCOM.strlen(paramInt1);
    byte[] arrayOfByte = new byte[i];
    XPCOM.memmove(arrayOfByte, paramInt1, i);
    String str1 = new String(MozillaDelegate.mbcsToWcs(null, arrayOfByte));
    String str2 = null;
    if (str1.equals("UHist"))
      str2 = this.profilePath + "history.dat";
    else if (str1.equals("UMimTyp"))
      str2 = this.profilePath + "mimeTypes.rdf";
    else if (str1.equals("PrefF"))
      str2 = this.profilePath + "prefs.js";
    else if (str1.equals("PrefD"))
      str2 = this.profilePath;
    else if (str1.equals("UChrm"))
      str2 = this.profilePath + "chrome";
    else if (str1.equals("ProfD"))
      str2 = this.profilePath;
    else if (str1.equals("LclSt"))
      str2 = this.profilePath + "localstore.rdf";
    else if (str1.equals("cachePDir"))
      str2 = this.profilePath;
    else if (str1.equals("Home"))
      str2 = System.getProperty("user.home");
    else if (str1.equals("TmpD"))
      str2 = System.getProperty("java.io.tmpdir");
    else if (str1.equals("GreD"))
      str2 = this.mozillaPath;
    else if (str1.equals("GreComsD"))
      str2 = this.profilePath + "components";
    else if (str1.equals("MozBinD"))
      str2 = this.mozillaPath;
    else if (str1.equals("CurProcD"))
      str2 = this.mozillaPath;
    else if (str1.equals("ComsD"))
      str2 = this.mozillaPath + "components";
    else if (str1.equals("XCurProcD"))
      str2 = this.mozillaPath;
    else if ((str1.equals("PrfDef")) && (this.isXULRunner))
      str2 = this.profilePath;
    XPCOM.memmove(paramInt2, new int[] { 1 }, 4);
    XPCOM.memmove(paramInt3, new int[1], C.PTR_SIZEOF);
    if ((str2 != null) && (str2.length() > 0))
    {
      int[] arrayOfInt = new int[1];
      nsEmbedString localnsEmbedString = new nsEmbedString(str2);
      int j = XPCOM.NS_NewLocalFile(localnsEmbedString.getAddress(), 1, arrayOfInt);
      if (j != 0)
        Mozilla.error(j);
      if (arrayOfInt[0] == 0)
        Mozilla.error(-2147467261);
      localnsEmbedString.dispose();
      nsILocalFile localnsILocalFile = new nsILocalFile(arrayOfInt[0]);
      arrayOfInt[0] = 0;
      j = localnsILocalFile.QueryInterface(nsIFile.NS_IFILE_IID, arrayOfInt);
      if (j != 0)
        Mozilla.error(j);
      if (arrayOfInt[0] == 0)
        Mozilla.error(-2147467262);
      XPCOM.memmove(paramInt3, new int[] { arrayOfInt[0] }, C.PTR_SIZEOF);
      localnsILocalFile.Release();
      return 0;
    }
    return -2147467259;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.browser.AppFileLocProvider
 * JD-Core Version:    0.6.2
 */