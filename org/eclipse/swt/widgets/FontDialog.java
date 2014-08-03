package org.eclipse.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.internal.win32.CHOOSEFONT;
import org.eclipse.swt.internal.win32.LOGFONT;
import org.eclipse.swt.internal.win32.LOGFONTA;
import org.eclipse.swt.internal.win32.LOGFONTW;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.TEXTMETRIC;
import org.eclipse.swt.internal.win32.TEXTMETRICA;
import org.eclipse.swt.internal.win32.TEXTMETRICW;

public class FontDialog extends Dialog
{
  FontData fontData;
  RGB rgb;

  public FontDialog(Shell paramShell)
  {
    this(paramShell, 65536);
  }

  public FontDialog(Shell paramShell, int paramInt)
  {
    super(paramShell, checkStyle(paramShell, paramInt));
    checkSubclass();
  }

  /** @deprecated */
  public FontData getFontData()
  {
    return this.fontData;
  }

  public FontData[] getFontList()
  {
    if (this.fontData == null)
      return null;
    FontData[] arrayOfFontData = new FontData[1];
    arrayOfFontData[0] = this.fontData;
    return arrayOfFontData;
  }

  public RGB getRGB()
  {
    return this.rgb;
  }

  public FontData open()
  {
    if (OS.IsWinCE)
      SWT.error(20);
    int i = this.parent.handle;
    int j = this.parent.handle;
    boolean bool1 = false;
    if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(4, 10)))
    {
      k = this.style & 0x6000000;
      int m = this.parent.style & 0x6000000;
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
    int k = OS.GetProcessHeap();
    CHOOSEFONT localCHOOSEFONT = new CHOOSEFONT();
    localCHOOSEFONT.lStructSize = CHOOSEFONT.sizeof;
    localCHOOSEFONT.hwndOwner = i;
    localCHOOSEFONT.Flags = 257;
    int n = OS.HeapAlloc(k, 8, LOGFONT.sizeof);
    int i2;
    int i3;
    if ((this.fontData != null) && (this.fontData.data != null))
    {
      LOGFONT localLOGFONT = this.fontData.data;
      i2 = localLOGFONT.lfHeight;
      i3 = OS.GetDC(0);
      int i4 = -(int)(0.5F + this.fontData.height * OS.GetDeviceCaps(i3, 90) / 72.0F);
      OS.ReleaseDC(0, i3);
      localLOGFONT.lfHeight = i4;
      localCHOOSEFONT.Flags |= 64;
      OS.MoveMemory(n, localLOGFONT, LOGFONT.sizeof);
      localLOGFONT.lfHeight = i2;
    }
    localCHOOSEFONT.lpLogFont = n;
    if (this.rgb != null)
    {
      int i1 = this.rgb.red & 0xFF;
      i2 = this.rgb.green << 8 & 0xFF00;
      i3 = this.rgb.blue << 16 & 0xFF0000;
      localCHOOSEFONT.rgbColors = (i1 | i2 | i3);
    }
    Dialog localDialog = null;
    Display localDisplay = null;
    if ((this.style & 0x30000) != 0)
    {
      localDisplay = this.parent.getDisplay();
      localDialog = localDisplay.getModalDialog();
      localDisplay.setModalDialog(this);
    }
    boolean bool2 = OS.ChooseFont(localCHOOSEFONT);
    if ((this.style & 0x30000) != 0)
      localDisplay.setModalDialog(localDialog);
    if (bool2)
    {
      LOGFONTA localLOGFONTA = OS.IsUnicode ? new LOGFONTW() : new LOGFONTA();
      OS.MoveMemory(localLOGFONTA, n, LOGFONT.sizeof);
      int i5 = OS.GetDC(0);
      int i6 = OS.GetDeviceCaps(i5, 90);
      int i7 = 0;
      if (localLOGFONTA.lfHeight > 0)
      {
        int i8 = OS.CreateFontIndirect(localLOGFONTA);
        i9 = OS.SelectObject(i5, i8);
        TEXTMETRICA localTEXTMETRICA = OS.IsUnicode ? new TEXTMETRICW() : new TEXTMETRICA();
        OS.GetTextMetrics(i5, localTEXTMETRICA);
        OS.SelectObject(i5, i9);
        OS.DeleteObject(i8);
        i7 = localLOGFONTA.lfHeight - localTEXTMETRICA.tmInternalLeading;
      }
      else
      {
        i7 = -localLOGFONTA.lfHeight;
      }
      OS.ReleaseDC(0, i5);
      float f = i7 * 72.0F / i6;
      this.fontData = FontData.win32_new(localLOGFONTA, f);
      int i9 = localCHOOSEFONT.rgbColors & 0xFF;
      int i10 = localCHOOSEFONT.rgbColors >> 8 & 0xFF;
      int i11 = localCHOOSEFONT.rgbColors >> 16 & 0xFF;
      this.rgb = new RGB(i9, i10, i11);
    }
    if (n != 0)
      OS.HeapFree(k, 0, n);
    if (j != i)
    {
      if (bool1)
        OS.EnableWindow(j, true);
      OS.SetActiveWindow(j);
      OS.DestroyWindow(i);
    }
    if (!bool2)
      return null;
    return this.fontData;
  }

  /** @deprecated */
  public void setFontData(FontData paramFontData)
  {
    this.fontData = paramFontData;
  }

  public void setFontList(FontData[] paramArrayOfFontData)
  {
    if ((paramArrayOfFontData != null) && (paramArrayOfFontData.length > 0))
      this.fontData = paramArrayOfFontData[0];
    else
      this.fontData = null;
  }

  public void setRGB(RGB paramRGB)
  {
    this.rgb = paramRGB;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.FontDialog
 * JD-Core Version:    0.6.2
 */