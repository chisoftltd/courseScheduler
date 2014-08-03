package org.eclipse.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.internal.Callback;
import org.eclipse.swt.internal.win32.BROWSEINFO;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.TCHAR;

public class DirectoryDialog extends Dialog
{
  String message = "";
  String filterPath = "";
  String directoryPath;

  public DirectoryDialog(Shell paramShell)
  {
    this(paramShell, 65536);
  }

  public DirectoryDialog(Shell paramShell, int paramInt)
  {
    super(paramShell, checkStyle(paramShell, paramInt));
    checkSubclass();
  }

  int BrowseCallbackProc(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    switch (paramInt2)
    {
    case 1:
      TCHAR localTCHAR1;
      if ((this.filterPath != null) && (this.filterPath.length() != 0))
      {
        localTCHAR1 = new TCHAR(0, this.filterPath.replace('/', '\\'), true);
        OS.SendMessage(paramInt1, OS.BFFM_SETSELECTION, 1, localTCHAR1);
      }
      if ((this.title != null) && (this.title.length() != 0))
      {
        localTCHAR1 = new TCHAR(0, this.title, true);
        OS.SetWindowText(paramInt1, localTCHAR1);
      }
      break;
    case 3:
    case 4:
      int i = OS.IsUnicode ? OS.wcslen(paramInt3) : OS.strlen(paramInt3);
      TCHAR localTCHAR2 = new TCHAR(0, i);
      int j = localTCHAR2.length() * TCHAR.sizeof;
      OS.MoveMemory(localTCHAR2, paramInt3, j);
      this.directoryPath = localTCHAR2.toString(0, i);
    case 2:
    }
    return 0;
  }

  public String getFilterPath()
  {
    return this.filterPath;
  }

  public String getMessage()
  {
    return this.message;
  }

  public String open()
  {
    if (OS.IsWinCE)
      SWT.error(20);
    int i = OS.GetProcessHeap();
    int j = 0;
    if (this.parent != null)
      j = this.parent.handle;
    int k = 0;
    if (this.message.length() != 0)
    {
      localObject1 = this.message;
      if (((String)localObject1).indexOf('&') != -1)
      {
        int m = ((String)localObject1).length();
        char[] arrayOfChar = new char[m * 2];
        int i2 = 0;
        for (int i3 = 0; i3 < m; i3++)
        {
          i4 = ((String)localObject1).charAt(i3);
          if (i4 == 38)
            arrayOfChar[(i2++)] = '&';
          arrayOfChar[(i2++)] = i4;
        }
        localObject1 = new String(arrayOfChar, 0, i2);
      }
      TCHAR localTCHAR = new TCHAR(0, (String)localObject1, true);
      int i1 = localTCHAR.length() * TCHAR.sizeof;
      k = OS.HeapAlloc(i, 8, i1);
      OS.MoveMemory(k, localTCHAR, i1);
    }
    Object localObject1 = new Callback(this, "BrowseCallbackProc", 4);
    int n = ((Callback)localObject1).getAddress();
    if (n == 0)
      SWT.error(3);
    Dialog localDialog = null;
    Display localDisplay = this.parent.getDisplay();
    if ((this.style & 0x30000) != 0)
    {
      localDialog = localDisplay.getModalDialog();
      localDisplay.setModalDialog(this);
    }
    this.directoryPath = null;
    BROWSEINFO localBROWSEINFO = new BROWSEINFO();
    localBROWSEINFO.hwndOwner = j;
    localBROWSEINFO.lpszTitle = k;
    localBROWSEINFO.ulFlags = 113;
    localBROWSEINFO.lpfn = n;
    int i4 = OS.SetErrorMode(1);
    boolean bool = localDisplay.runMessages;
    if (OS.COMCTL32_MAJOR < 6)
      localDisplay.runMessages = false;
    int i5 = OS.SHBrowseForFolder(localBROWSEINFO);
    if (OS.COMCTL32_MAJOR < 6)
      localDisplay.runMessages = bool;
    OS.SetErrorMode(i4);
    if ((this.style & 0x30000) != 0)
      localDisplay.setModalDialog(localDialog);
    int i6 = i5 != 0 ? 1 : 0;
    if (i6 != 0)
    {
      localObject2 = new TCHAR(0, 260);
      if (OS.SHGetPathFromIDList(i5, (TCHAR)localObject2))
      {
        this.directoryPath = ((TCHAR)localObject2).toString(0, ((TCHAR)localObject2).strlen());
        this.filterPath = this.directoryPath;
      }
    }
    ((Callback)localObject1).dispose();
    if (k != 0)
      OS.HeapFree(i, 0, k);
    Object localObject2 = new int[1];
    if (OS.SHGetMalloc((int[])localObject2) == 0)
      OS.VtblCall(5, localObject2[0], i5);
    if (i6 == 0)
      return null;
    return this.directoryPath;
  }

  public void setFilterPath(String paramString)
  {
    this.filterPath = paramString;
  }

  public void setMessage(String paramString)
  {
    if (paramString == null)
      error(4);
    this.message = paramString;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.DirectoryDialog
 * JD-Core Version:    0.6.2
 */