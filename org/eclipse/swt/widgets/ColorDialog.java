package org.eclipse.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.internal.Callback;
import org.eclipse.swt.internal.win32.CHOOSECOLOR;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.TCHAR;

public class ColorDialog extends Dialog
{
  Display display;
  int width;
  int height;
  RGB rgb;

  public ColorDialog(Shell paramShell)
  {
    this(paramShell, 65536);
  }

  public ColorDialog(Shell paramShell, int paramInt)
  {
    super(paramShell, checkStyle(paramShell, paramInt));
    checkSubclass();
  }

  int CCHookProc(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    RECT localRECT;
    switch (paramInt2)
    {
    case 272:
      localRECT = new RECT();
      OS.GetWindowRect(paramInt1, localRECT);
      this.width = (localRECT.right - localRECT.left);
      this.height = (localRECT.bottom - localRECT.top);
      if ((this.title != null) && (this.title.length() != 0))
      {
        TCHAR localTCHAR = new TCHAR(0, this.title, true);
        OS.SetWindowText(paramInt1, localTCHAR);
      }
      break;
    case 2:
      localRECT = new RECT();
      OS.GetWindowRect(paramInt1, localRECT);
      int i = localRECT.right - localRECT.left;
      int j = localRECT.bottom - localRECT.top;
      if ((i < this.width) || (j < this.height) || (i > this.width))
        break;
    }
    return 0;
  }

  public RGB getRGB()
  {
    return this.rgb;
  }

  public RGB open()
  {
    int i = this.parent.handle;
    int j = this.parent.handle;
    boolean bool1 = false;
    int n;
    if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(4, 10)))
    {
      int k = this.style & 0x6000000;
      m = this.parent.style & 0x6000000;
      if (k != m)
      {
        n = 1048576;
        if (k == 67108864)
          n |= 4194304;
        i = OS.CreateWindowEx(n, Shell.DialogClass, null, 0, -2147483648, 0, -2147483648, 0, j, 0, OS.GetModuleHandle(null), null);
        bool1 = OS.IsWindowEnabled(j);
        if (bool1)
          OS.EnableWindow(j, false);
      }
    }
    Callback localCallback = new Callback(this, "CCHookProc", 4);
    int m = localCallback.getAddress();
    if (m == 0)
      SWT.error(3);
    this.display = this.parent.display;
    if (this.display.lpCustColors == 0)
    {
      n = OS.GetProcessHeap();
      this.display.lpCustColors = OS.HeapAlloc(n, 8, 64);
    }
    CHOOSECOLOR localCHOOSECOLOR = new CHOOSECOLOR();
    localCHOOSECOLOR.lStructSize = CHOOSECOLOR.sizeof;
    localCHOOSECOLOR.Flags = 272;
    localCHOOSECOLOR.lpfnHook = m;
    localCHOOSECOLOR.hwndOwner = i;
    localCHOOSECOLOR.lpCustColors = this.display.lpCustColors;
    int i3;
    if (this.rgb != null)
    {
      localCHOOSECOLOR.Flags |= 1;
      int i1 = this.rgb.red & 0xFF;
      int i2 = this.rgb.green << 8 & 0xFF00;
      i3 = this.rgb.blue << 16 & 0xFF0000;
      localCHOOSECOLOR.rgbResult = (i1 | i2 | i3);
    }
    Dialog localDialog = null;
    if ((this.style & 0x30000) != 0)
    {
      localDialog = this.display.getModalDialog();
      this.display.setModalDialog(this);
    }
    boolean bool2 = OS.ChooseColor(localCHOOSECOLOR);
    if ((this.style & 0x30000) != 0)
      this.display.setModalDialog(localDialog);
    if (bool2)
    {
      i3 = localCHOOSECOLOR.rgbResult & 0xFF;
      int i4 = localCHOOSECOLOR.rgbResult >> 8 & 0xFF;
      int i5 = localCHOOSECOLOR.rgbResult >> 16 & 0xFF;
      this.rgb = new RGB(i3, i4, i5);
    }
    localCallback.dispose();
    if (j != i)
    {
      if (bool1)
        OS.EnableWindow(j, true);
      OS.SetActiveWindow(j);
      OS.DestroyWindow(i);
    }
    this.display = null;
    if (!bool2)
      return null;
    return this.rgb;
  }

  public void setRGB(RGB paramRGB)
  {
    this.rgb = paramRGB;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.ColorDialog
 * JD-Core Version:    0.6.2
 */