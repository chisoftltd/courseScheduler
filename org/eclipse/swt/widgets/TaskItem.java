package org.eclipse.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.internal.win32.BITMAPINFOHEADER;
import org.eclipse.swt.internal.win32.ICONINFO;
import org.eclipse.swt.internal.win32.LOGFONTA;
import org.eclipse.swt.internal.win32.NONCLIENTMETRICS;
import org.eclipse.swt.internal.win32.NONCLIENTMETRICSA;
import org.eclipse.swt.internal.win32.NONCLIENTMETRICSW;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.TCHAR;

public class TaskItem extends Item
{
  TaskBar parent;
  Shell shell;
  int progress;
  int progressState = -1;
  Image overlayImage;
  String overlayText = "";
  boolean showingText = false;
  Menu menu;
  static final int PROGRESS_MAX = 100;

  TaskItem(TaskBar paramTaskBar, int paramInt)
  {
    super(paramTaskBar, paramInt);
    this.parent = paramTaskBar;
    paramTaskBar.createItem(this, -1);
  }

  protected void checkSubclass()
  {
    if (!isValidSubclass())
      error(43);
  }

  void destroyWidget()
  {
    this.parent.destroyItem(this);
    releaseHandle();
  }

  public Menu getMenu()
  {
    checkWidget();
    return this.menu;
  }

  public Image getOverlayImage()
  {
    checkWidget();
    return this.overlayImage;
  }

  public String getOverlayText()
  {
    checkWidget();
    return this.overlayText;
  }

  public TaskBar getParent()
  {
    checkWidget();
    return this.parent;
  }

  public int getProgress()
  {
    checkWidget();
    return this.progress;
  }

  public int getProgressState()
  {
    checkWidget();
    return this.progressState;
  }

  void recreate()
  {
    if (this.showingText)
    {
      if (this.overlayText.length() != 0)
        updateText();
    }
    else if (this.overlayImage != null)
      updateImage();
    if (this.progress != 0)
      updateProgress();
    if (this.progressState != -1)
      updateProgressState();
  }

  void releaseHandle()
  {
    super.releaseHandle();
    this.parent = null;
  }

  void releaseWidget()
  {
    super.releaseWidget();
    this.overlayImage = null;
    this.overlayText = null;
  }

  public void setMenu(Menu paramMenu)
  {
    checkWidget();
    if (paramMenu != null)
    {
      if (paramMenu.isDisposed())
        SWT.error(5);
      if ((paramMenu.style & 0x8) == 0)
        error(37);
    }
    if (this.shell != null)
      return;
    this.menu = paramMenu;
    this.parent.setMenu(paramMenu);
  }

  public void setOverlayImage(Image paramImage)
  {
    checkWidget();
    if ((paramImage != null) && (paramImage.isDisposed()))
      error(5);
    if (this.shell == null)
      return;
    this.overlayImage = paramImage;
    if (paramImage != null)
    {
      updateImage();
    }
    else if (this.overlayText.length() != 0)
    {
      updateText();
    }
    else
    {
      int i = this.parent.mTaskbarList3;
      int j = this.shell.handle;
      OS.VtblCall(18, i, j, 0, 0);
    }
  }

  public void setOverlayText(String paramString)
  {
    checkWidget();
    if (paramString == null)
      error(4);
    if (this.shell == null)
      return;
    this.overlayText = paramString;
    if (paramString.length() != 0)
    {
      updateText();
    }
    else if (this.overlayImage != null)
    {
      updateImage();
    }
    else
    {
      int i = this.parent.mTaskbarList3;
      int j = this.shell.handle;
      OS.VtblCall(18, i, j, 0, 0);
    }
  }

  public void setProgress(int paramInt)
  {
    checkWidget();
    if (this.shell == null)
      return;
    paramInt = Math.max(0, Math.min(paramInt, 100));
    if (this.progress == paramInt)
      return;
    this.progress = paramInt;
    updateProgress();
  }

  public void setProgressState(int paramInt)
  {
    checkWidget();
    if (this.shell == null)
      return;
    if (this.progressState == paramInt)
      return;
    this.progressState = paramInt;
    updateProgressState();
  }

  void setShell(Shell paramShell)
  {
    this.shell = paramShell;
    paramShell.addListener(12, new Listener()
    {
      public void handleEvent(Event paramAnonymousEvent)
      {
        if (TaskItem.this.isDisposed())
          return;
        TaskItem.this.dispose();
      }
    });
  }

  void updateImage()
  {
    this.showingText = false;
    Image localImage = null;
    int i = 0;
    switch (this.overlayImage.type)
    {
    case 0:
      localImage = Display.createIcon(this.overlayImage);
      i = localImage.handle;
      break;
    case 1:
      i = this.overlayImage.handle;
    }
    int j = this.parent.mTaskbarList3;
    int k = this.shell.handle;
    OS.VtblCall(18, j, k, i, 0);
    if (localImage != null)
      localImage.dispose();
  }

  void updateProgress()
  {
    if (this.progressState == 2)
      return;
    if (this.progressState == -1)
      return;
    int i = this.parent.mTaskbarList3;
    int j = this.shell.handle;
    OS.VtblCall(9, i, j, this.progress, 100L);
  }

  void updateProgressState()
  {
    int i = 0;
    switch (this.progressState)
    {
    case 0:
      i = 2;
      break;
    case 1:
      i = 4;
      break;
    case 4:
      i = 8;
      break;
    case 2:
      i = 1;
    case 3:
    }
    int j = this.parent.mTaskbarList3;
    int k = this.shell.handle;
    OS.VtblCall(9, j, k, this.progress, 100L);
    OS.VtblCall(10, j, k, i);
  }

  void updateText()
  {
    this.showingText = true;
    int i = 16;
    int j = 16;
    int k = OS.GetDC(0);
    BITMAPINFOHEADER localBITMAPINFOHEADER = new BITMAPINFOHEADER();
    localBITMAPINFOHEADER.biSize = BITMAPINFOHEADER.sizeof;
    localBITMAPINFOHEADER.biWidth = i;
    localBITMAPINFOHEADER.biHeight = (-j);
    localBITMAPINFOHEADER.biPlanes = 1;
    localBITMAPINFOHEADER.biBitCount = 32;
    localBITMAPINFOHEADER.biCompression = 0;
    byte[] arrayOfByte = new byte[BITMAPINFOHEADER.sizeof];
    OS.MoveMemory(arrayOfByte, localBITMAPINFOHEADER, BITMAPINFOHEADER.sizeof);
    int[] arrayOfInt = new int[1];
    int m = OS.CreateDIBSection(0, arrayOfByte, 0, arrayOfInt, 0, 0);
    if (m == 0)
      SWT.error(2);
    int n = OS.CreateCompatibleDC(k);
    int i1 = OS.SelectObject(n, m);
    int i2 = OS.CreateBitmap(i, j, 1, 1, null);
    if (i2 == 0)
      SWT.error(2);
    int i3 = OS.CreateCompatibleDC(k);
    int i4 = OS.SelectObject(i3, i2);
    OS.PatBlt(i3, 0, 0, i, j, 16711778);
    int i5 = OS.SelectObject(i3, OS.GetStockObject(4));
    OS.RoundRect(i3, 0, 0, i, j, 8, 8);
    OS.SelectObject(i3, i5);
    int i6 = OS.CreateSolidBrush(OS.GetSysColor(OS.COLOR_HIGHLIGHT));
    i5 = OS.SelectObject(n, i6);
    OS.RoundRect(n, 0, 0, i, j, 8, 8);
    OS.SelectObject(n, i5);
    OS.DeleteObject(i6);
    int i7 = 2080;
    RECT localRECT = new RECT();
    TCHAR localTCHAR = new TCHAR(this.shell.getCodePage(), this.overlayText, false);
    int i8 = localTCHAR.length();
    int i9 = 0;
    int i10 = 0;
    NONCLIENTMETRICSA localNONCLIENTMETRICSA = OS.IsUnicode ? new NONCLIENTMETRICSW() : new NONCLIENTMETRICSA();
    localNONCLIENTMETRICSA.cbSize = NONCLIENTMETRICS.sizeof;
    if (OS.SystemParametersInfo(41, 0, localNONCLIENTMETRICSA, 0))
    {
      LOGFONTA localLOGFONTA = OS.IsUnicode ? ((NONCLIENTMETRICSW)localNONCLIENTMETRICSA).lfMessageFont : ((NONCLIENTMETRICSA)localNONCLIENTMETRICSA).lfMessageFont;
      localLOGFONTA.lfHeight = -10;
      i9 = OS.CreateFontIndirect(localLOGFONTA);
      i10 = OS.SelectObject(n, i9);
      OS.DrawText(n, localTCHAR, i8, localRECT, i7 | 0x400);
      if (localRECT.right > i - 2)
      {
        OS.SelectObject(n, i10);
        OS.DeleteObject(i9);
        localLOGFONTA.lfHeight = -8;
        i9 = OS.CreateFontIndirect(localLOGFONTA);
        OS.SelectObject(n, i9);
      }
    }
    OS.DrawText(n, localTCHAR, i8, localRECT, i7 | 0x400);
    OS.OffsetRect(localRECT, (i - localRECT.right) / 2, (j - localRECT.bottom) / 2);
    int i11 = OS.SetBkMode(n, 1);
    OS.SetTextColor(n, OS.GetSysColor(OS.COLOR_HIGHLIGHTTEXT));
    OS.DrawText(n, localTCHAR, i8, localRECT, i7);
    if (i9 != 0)
    {
      OS.SelectObject(n, i10);
      OS.DeleteObject(i9);
    }
    OS.SetBkMode(n, i11);
    OS.SelectObject(n, i1);
    OS.DeleteDC(n);
    OS.SelectObject(i3, i4);
    OS.DeleteDC(i3);
    OS.ReleaseDC(0, k);
    ICONINFO localICONINFO = new ICONINFO();
    localICONINFO.fIcon = true;
    localICONINFO.hbmColor = m;
    localICONINFO.hbmMask = i2;
    int i12 = OS.CreateIconIndirect(localICONINFO);
    if (i12 == 0)
      SWT.error(2);
    OS.DeleteObject(m);
    OS.DeleteObject(i2);
    int i13 = this.parent.mTaskbarList3;
    int i14 = this.shell.handle;
    OS.VtblCall(18, i13, i14, i12, 0);
    OS.DestroyIcon(i12);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.TaskItem
 * JD-Core Version:    0.6.2
 */