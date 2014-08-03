package org.eclipse.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.internal.Callback;
import org.eclipse.swt.internal.win32.OFNOTIFY;
import org.eclipse.swt.internal.win32.OPENFILENAME;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.TCHAR;

public class FileDialog extends Dialog
{
  String[] filterNames = new String[0];
  String[] filterExtensions = new String[0];
  String[] fileNames = new String[0];
  String filterPath = "";
  String fileName = "";
  int filterIndex = 0;
  boolean overwrite = false;
  static final String FILTER = "*.*";
  static int BUFFER_SIZE = 32768;
  static boolean USE_HOOK = true;

  static
  {
    if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(6, 0)))
      USE_HOOK = false;
  }

  public FileDialog(Shell paramShell)
  {
    this(paramShell, 65536);
  }

  public FileDialog(Shell paramShell, int paramInt)
  {
    super(paramShell, checkStyle(paramShell, paramInt));
    checkSubclass();
  }

  public String getFileName()
  {
    return this.fileName;
  }

  public String[] getFileNames()
  {
    return this.fileNames;
  }

  public String[] getFilterExtensions()
  {
    return this.filterExtensions;
  }

  public int getFilterIndex()
  {
    return this.filterIndex;
  }

  public String[] getFilterNames()
  {
    return this.filterNames;
  }

  public String getFilterPath()
  {
    return this.filterPath;
  }

  public boolean getOverwrite()
  {
    return this.overwrite;
  }

  int OFNHookProc(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    switch (paramInt2)
    {
    case 78:
      OFNOTIFY localOFNOTIFY = new OFNOTIFY();
      OS.MoveMemory(localOFNOTIFY, paramInt4, OFNOTIFY.sizeof);
      if (localOFNOTIFY.code == -602)
      {
        int i = OS.SendMessage(localOFNOTIFY.hwndFrom, 1124, 0, 0);
        if (i > 0)
        {
          i += 260;
          OPENFILENAME localOPENFILENAME = new OPENFILENAME();
          OS.MoveMemory(localOPENFILENAME, localOFNOTIFY.lpOFN, OPENFILENAME.sizeof);
          if (localOPENFILENAME.nMaxFile < i)
          {
            int j = OS.GetProcessHeap();
            int k = OS.HeapAlloc(j, 8, i * TCHAR.sizeof);
            if (k != 0)
            {
              if (localOPENFILENAME.lpstrFile != 0)
                OS.HeapFree(j, 0, localOPENFILENAME.lpstrFile);
              localOPENFILENAME.lpstrFile = k;
              localOPENFILENAME.nMaxFile = i;
              OS.MoveMemory(localOFNOTIFY.lpOFN, localOPENFILENAME, OPENFILENAME.sizeof);
            }
          }
        }
      }
      break;
    }
    return 0;
  }

  public String open()
  {
    int i = OS.GetProcessHeap();
    int j = this.parent.handle;
    int k = this.parent.handle;
    boolean bool1 = false;
    if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(4, 10)))
    {
      int m = this.style & 0x6000000;
      n = this.parent.style & 0x6000000;
      if (m != n)
      {
        i1 = 1048576;
        if (m == 67108864)
          i1 |= 4194304;
        j = OS.CreateWindowEx(i1, Shell.DialogClass, null, 0, -2147483648, 0, -2147483648, 0, k, 0, OS.GetModuleHandle(null), null);
        bool1 = OS.IsWindowEnabled(k);
        if (bool1)
          OS.EnableWindow(k, false);
      }
    }
    if (this.title == null)
      this.title = "";
    TCHAR localTCHAR1 = new TCHAR(0, this.title, true);
    int n = localTCHAR1.length() * TCHAR.sizeof;
    int i1 = OS.HeapAlloc(i, 8, n);
    OS.MoveMemory(i1, localTCHAR1, n);
    String str1 = "";
    if (this.filterNames == null)
      this.filterNames = new String[0];
    if (this.filterExtensions == null)
      this.filterExtensions = new String[0];
    for (int i2 = 0; i2 < this.filterExtensions.length; i2++)
    {
      String str2 = this.filterExtensions[i2];
      if (i2 < this.filterNames.length)
        str2 = this.filterNames[i2];
      str1 = str1 + str2 + '\000' + this.filterExtensions[i2] + '\000';
    }
    if (this.filterExtensions.length == 0)
      str1 = str1 + "*.*" + '\000' + "*.*" + '\000';
    TCHAR localTCHAR2 = new TCHAR(0, str1, true);
    int i3 = localTCHAR2.length() * TCHAR.sizeof;
    int i4 = OS.HeapAlloc(i, 8, i3);
    OS.MoveMemory(i4, localTCHAR2, i3);
    if (this.fileName == null)
      this.fileName = "";
    TCHAR localTCHAR3 = new TCHAR(0, this.fileName, true);
    int i5 = 260;
    if ((this.style & 0x2) != 0)
      i5 = Math.max(i5, BUFFER_SIZE);
    int i6 = i5 * TCHAR.sizeof;
    int i7 = OS.HeapAlloc(i, 8, i6);
    int i8 = Math.min(localTCHAR3.length() * TCHAR.sizeof, i6 - TCHAR.sizeof);
    OS.MoveMemory(i7, localTCHAR3, i8);
    if (this.filterPath == null)
      this.filterPath = "";
    TCHAR localTCHAR4 = new TCHAR(0, this.filterPath.replace('/', '\\'), true);
    int i9 = 260 * TCHAR.sizeof;
    int i10 = OS.HeapAlloc(i, 8, i9);
    int i11 = Math.min(localTCHAR4.length() * TCHAR.sizeof, i9 - TCHAR.sizeof);
    OS.MoveMemory(i10, localTCHAR4, i11);
    OPENFILENAME localOPENFILENAME = new OPENFILENAME();
    localOPENFILENAME.lStructSize = OPENFILENAME.sizeof;
    localOPENFILENAME.Flags = 12;
    int i12 = (this.style & 0x2000) != 0 ? 1 : 0;
    if ((i12 != 0) && (this.overwrite))
      localOPENFILENAME.Flags |= 2;
    Callback localCallback = null;
    if ((this.style & 0x2) != 0)
    {
      localOPENFILENAME.Flags |= 8913408;
      if ((!OS.IsWinCE) && (USE_HOOK))
      {
        localCallback = new Callback(this, "OFNHookProc", 4);
        i13 = localCallback.getAddress();
        if (i13 == 0)
          SWT.error(3);
        localOPENFILENAME.lpfnHook = i13;
        localOPENFILENAME.Flags |= 32;
      }
    }
    localOPENFILENAME.hwndOwner = j;
    localOPENFILENAME.lpstrTitle = i1;
    localOPENFILENAME.lpstrFile = i7;
    localOPENFILENAME.nMaxFile = i5;
    localOPENFILENAME.lpstrInitialDir = i10;
    localOPENFILENAME.lpstrFilter = i4;
    localOPENFILENAME.nFilterIndex = (this.filterIndex == 0 ? this.filterIndex : this.filterIndex + 1);
    int i13 = 0;
    if (i12 != 0)
    {
      i13 = OS.HeapAlloc(i, 8, TCHAR.sizeof);
      localOPENFILENAME.lpstrDefExt = i13;
    }
    Dialog localDialog = null;
    Display localDisplay = this.parent.getDisplay();
    if ((this.style & 0x30000) != 0)
    {
      localDialog = localDisplay.getModalDialog();
      localDisplay.setModalDialog(this);
    }
    boolean bool2 = localDisplay.runMessagesInIdle;
    localDisplay.runMessagesInIdle = (!OS.IsWin95);
    boolean bool3 = i12 != 0 ? OS.GetSaveFileName(localOPENFILENAME) : OS.GetOpenFileName(localOPENFILENAME);
    switch (OS.CommDlgExtendedError())
    {
    case 12290:
      OS.MoveMemory(i7, new TCHAR(0, "", true), TCHAR.sizeof);
      bool3 = i12 != 0 ? OS.GetSaveFileName(localOPENFILENAME) : OS.GetOpenFileName(localOPENFILENAME);
      break;
    case 12291:
      USE_HOOK = true;
    }
    localDisplay.runMessagesInIdle = bool2;
    if ((this.style & 0x30000) != 0)
      localDisplay.setModalDialog(localDialog);
    if (localCallback != null)
      localCallback.dispose();
    i7 = localOPENFILENAME.lpstrFile;
    this.fileNames = new String[0];
    String str3 = null;
    if (bool3)
    {
      TCHAR localTCHAR5 = new TCHAR(0, localOPENFILENAME.nMaxFile);
      int i14 = localTCHAR5.length() * TCHAR.sizeof;
      OS.MoveMemory(localTCHAR5, i7, i14);
      int i15 = localOPENFILENAME.nFileOffset;
      int i17;
      if ((OS.IsWinCE) && (i15 == 0))
        for (int i16 = 0; i16 < localTCHAR5.length(); i16++)
        {
          i17 = localTCHAR5.tcharAt(i16);
          if (i17 == 0)
            break;
          if (i17 == 92)
            i15 = i16 + 1;
        }
      if (i15 > 0)
      {
        TCHAR localTCHAR6 = new TCHAR(0, i15 - 1);
        i17 = localTCHAR6.length() * TCHAR.sizeof;
        OS.MoveMemory(localTCHAR6, i7, i17);
        this.filterPath = localTCHAR6.toString(0, localTCHAR6.length());
        int i18 = 0;
        this.fileNames = new String[(this.style & 0x2) != 0 ? 4 : 1];
        int i19 = i15;
        String[] arrayOfString;
        do
        {
          for (int i20 = i19; (i20 < localTCHAR5.length()) && (localTCHAR5.tcharAt(i20) != 0); i20++);
          String str5 = localTCHAR5.toString(i19, i20 - i19);
          i19 = i20;
          if (i18 == this.fileNames.length)
          {
            arrayOfString = new String[this.fileNames.length + 4];
            System.arraycopy(this.fileNames, 0, arrayOfString, 0, this.fileNames.length);
            this.fileNames = arrayOfString;
          }
          this.fileNames[(i18++)] = str5;
          if ((this.style & 0x2) == 0)
            break;
          i19++;
        }
        while ((i19 < localTCHAR5.length()) && (localTCHAR5.tcharAt(i19) != 0));
        if (this.fileNames.length > 0)
          this.fileName = this.fileNames[0];
        String str4 = "";
        int i21 = this.filterPath.length();
        if ((i21 > 0) && (this.filterPath.charAt(i21 - 1) != '\\'))
          str4 = "\\";
        str3 = this.filterPath + str4 + this.fileName;
        if (i18 < this.fileNames.length)
        {
          arrayOfString = new String[i18];
          System.arraycopy(this.fileNames, 0, arrayOfString, 0, i18);
          this.fileNames = arrayOfString;
        }
      }
      this.filterIndex = (localOPENFILENAME.nFilterIndex - 1);
    }
    OS.HeapFree(i, 0, i7);
    OS.HeapFree(i, 0, i4);
    OS.HeapFree(i, 0, i10);
    OS.HeapFree(i, 0, i1);
    if (i13 != 0)
      OS.HeapFree(i, 0, i13);
    if (k != j)
    {
      if (bool1)
        OS.EnableWindow(k, true);
      OS.SetActiveWindow(k);
      OS.DestroyWindow(j);
    }
    return str3;
  }

  public void setFileName(String paramString)
  {
    this.fileName = paramString;
  }

  public void setFilterExtensions(String[] paramArrayOfString)
  {
    this.filterExtensions = paramArrayOfString;
  }

  public void setFilterIndex(int paramInt)
  {
    this.filterIndex = paramInt;
  }

  public void setFilterNames(String[] paramArrayOfString)
  {
    this.filterNames = paramArrayOfString;
  }

  public void setFilterPath(String paramString)
  {
    this.filterPath = paramString;
  }

  public void setOverwrite(boolean paramBoolean)
  {
    this.overwrite = paramBoolean;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.FileDialog
 * JD-Core Version:    0.6.2
 */