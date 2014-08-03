package org.eclipse.swt.widgets;

import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.TCHAR;

public class MessageBox extends Dialog
{
  String message = "";

  public MessageBox(Shell paramShell)
  {
    this(paramShell, 65570);
  }

  public MessageBox(Shell paramShell, int paramInt)
  {
    super(paramShell, checkStyle(paramShell, checkStyle(paramInt)));
    checkSubclass();
  }

  static int checkStyle(int paramInt)
  {
    int i = 4064;
    int j = paramInt & i;
    if ((j == 32) || (j == 256) || (j == 288))
      return paramInt;
    if ((j == 64) || (j == 128) || (j == 192) || (j == 448))
      return paramInt;
    if ((j == 1280) || (j == 3584))
      return paramInt;
    paramInt = paramInt & (i ^ 0xFFFFFFFF) | 0x20;
    return paramInt;
  }

  public String getMessage()
  {
    return this.message;
  }

  public int open()
  {
    int i = 0;
    if ((this.style & 0x20) == 32)
      i = 0;
    if ((this.style & 0x120) == 288)
      i = 1;
    if ((this.style & 0xC0) == 192)
      i = 4;
    if ((this.style & 0x1C0) == 448)
      i = 3;
    if ((this.style & 0x500) == 1280)
      i = 5;
    if ((this.style & 0xE00) == 3584)
      i = 2;
    if (i == 0)
      i = 0;
    int j = 0;
    if ((this.style & 0x1) != 0)
      j = 16;
    if ((this.style & 0x2) != 0)
      j = 64;
    if ((this.style & 0x4) != 0)
      j = 32;
    if ((this.style & 0x8) != 0)
      j = 48;
    if ((this.style & 0x10) != 0)
      j = 64;
    int k = 0;
    if (OS.IsWinCE)
    {
      if ((this.style & 0x38000) != 0)
        k = 0;
    }
    else
    {
      if ((this.style & 0x8000) != 0)
        k = 0;
      if ((this.style & 0x10000) != 0)
        k = 8192;
      if ((this.style & 0x20000) != 0)
        k = 4096;
    }
    int m = i | j | k;
    if ((this.style & 0x4000000) != 0)
      m |= 1572864;
    if (((this.style & 0x6000000) == 0) && (this.parent != null) && ((this.parent.style & 0x8000000) != 0))
      m |= 1572864;
    if ((m & 0x1000) != 0)
    {
      m |= 8192;
      m &= -4097;
      m |= 262144;
    }
    int n = this.parent != null ? this.parent.handle : 0;
    Dialog localDialog = null;
    Display localDisplay = null;
    if ((m & 0x2000) != 0)
    {
      localDisplay = this.parent.getDisplay();
      localDialog = localDisplay.getModalDialog();
      localDisplay.setModalDialog(this);
    }
    TCHAR localTCHAR1 = new TCHAR(0, this.message, true);
    TCHAR localTCHAR2 = new TCHAR(0, this.title, true);
    int i1 = OS.MessageBox(n, localTCHAR1, localTCHAR2, m);
    if ((m & 0x2000) != 0)
      localDisplay.setModalDialog(localDialog);
    if (i1 != 0)
    {
      int i2 = m & 0xF;
      if (i2 == 0)
        return 32;
      if (i2 == 1)
        return i1 == 1 ? 32 : 256;
      if (i2 == 4)
        return i1 == 6 ? 64 : 128;
      if (i2 == 3)
      {
        if (i1 == 6)
          return 64;
        if (i1 == 7)
          return 128;
        return 256;
      }
      if (i2 == 5)
        return i1 == 4 ? 1024 : 256;
      if (i2 == 2)
      {
        if (i1 == 4)
          return 1024;
        if (i1 == 3)
          return 512;
        return 2048;
      }
    }
    return 256;
  }

  public void setMessage(String paramString)
  {
    if (paramString == null)
      error(4);
    this.message = paramString;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.MessageBox
 * JD-Core Version:    0.6.2
 */