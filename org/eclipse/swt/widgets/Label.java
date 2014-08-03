package org.eclipse.swt.widgets;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.GCData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.win32.DRAWITEMSTRUCT;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.PAINTSTRUCT;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.TEXTMETRIC;
import org.eclipse.swt.internal.win32.TEXTMETRICA;
import org.eclipse.swt.internal.win32.TEXTMETRICW;
import org.eclipse.swt.internal.win32.WNDCLASS;

public class Label extends Control
{
  String text = "";
  Image image;
  static final int MARGIN = 4;
  static boolean IMAGE_AND_TEXT = false;
  static final int LabelProc = localWNDCLASS.lpfnWndProc;
  static final TCHAR LabelClass = new TCHAR(0, "STATIC", true);

  static
  {
    WNDCLASS localWNDCLASS = new WNDCLASS();
    OS.GetClassInfo(0, LabelClass, localWNDCLASS);
  }

  public Label(Composite paramComposite, int paramInt)
  {
    super(paramComposite, checkStyle(paramInt));
  }

  int callWindowProc(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.handle == 0)
      return 0;
    if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(6, 1)))
      switch (paramInt2)
      {
      case 515:
        return OS.DefWindowProc(paramInt1, paramInt2, paramInt3, paramInt4);
      }
    return OS.CallWindowProc(LabelProc, paramInt1, paramInt2, paramInt3, paramInt4);
  }

  static int checkStyle(int paramInt)
  {
    paramInt |= 524288;
    if ((paramInt & 0x2) != 0)
    {
      paramInt = checkBits(paramInt, 512, 256, 0, 0, 0, 0);
      return checkBits(paramInt, 8, 4, 32, 0, 0, 0);
    }
    return checkBits(paramInt, 16384, 16777216, 131072, 0, 0, 0);
  }

  public Point computeSize(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    checkWidget();
    int i = 0;
    int j = 0;
    int k = getBorderWidth();
    if ((this.style & 0x2) != 0)
    {
      m = OS.GetSystemMetrics(5);
      if ((this.style & 0x100) != 0)
      {
        i = 64;
        j = m * 2;
      }
      else
      {
        i = m * 2;
        j = 64;
      }
      if (paramInt1 != -1)
        i = paramInt1;
      if (paramInt2 != -1)
        j = paramInt2;
      i += k * 2;
      j += k * 2;
      return new Point(i, j);
    }
    int m = OS.GetWindowLong(this.handle, -16);
    int n = 1;
    int i1 = (m & 0xD) == 13 ? 1 : 0;
    if ((i1 != 0) && (this.image != null))
    {
      Rectangle localRectangle = this.image.getBounds();
      i += localRectangle.width;
      j += localRectangle.height;
      if (IMAGE_AND_TEXT)
      {
        if (this.text.length() != 0)
          i += 4;
      }
      else
        n = 0;
    }
    if (n != 0)
    {
      int i2 = OS.GetDC(this.handle);
      int i3 = OS.SendMessage(this.handle, 49, 0, 0);
      int i4 = OS.SelectObject(i2, i3);
      int i5 = OS.GetWindowTextLength(this.handle);
      Object localObject;
      if (i5 == 0)
      {
        localObject = OS.IsUnicode ? new TEXTMETRICW() : new TEXTMETRICA();
        OS.GetTextMetrics(i2, (TEXTMETRIC)localObject);
        j = Math.max(j, ((TEXTMETRIC)localObject).tmHeight);
      }
      else
      {
        localObject = new RECT();
        int i6 = 9280;
        if (((this.style & 0x40) != 0) && (paramInt1 != -1))
        {
          i6 |= 16;
          ((RECT)localObject).right = Math.max(0, paramInt1 - i);
        }
        TCHAR localTCHAR = new TCHAR(getCodePage(), i5 + 1);
        OS.GetWindowText(this.handle, localTCHAR, i5 + 1);
        OS.DrawText(i2, localTCHAR, i5, (RECT)localObject, i6);
        i += ((RECT)localObject).right - ((RECT)localObject).left;
        j = Math.max(j, ((RECT)localObject).bottom - ((RECT)localObject).top);
      }
      if (i3 != 0)
        OS.SelectObject(i2, i4);
      OS.ReleaseDC(this.handle, i2);
    }
    if (paramInt1 != -1)
      i = paramInt1;
    if (paramInt2 != -1)
      j = paramInt2;
    i += k * 2;
    j += k * 2;
    if ((OS.IsWinCE) && (i1 == 0))
      i += 2;
    return new Point(i, j);
  }

  void createHandle()
  {
    super.createHandle();
    this.state |= 256;
  }

  public int getAlignment()
  {
    checkWidget();
    if ((this.style & 0x2) != 0)
      return 0;
    if ((this.style & 0x4000) != 0)
      return 16384;
    if ((this.style & 0x1000000) != 0)
      return 16777216;
    if ((this.style & 0x20000) != 0)
      return 131072;
    return 16384;
  }

  public Image getImage()
  {
    checkWidget();
    return this.image;
  }

  String getNameText()
  {
    return getText();
  }

  public String getText()
  {
    checkWidget();
    if ((this.style & 0x2) != 0)
      return "";
    return this.text;
  }

  boolean mnemonicHit(char paramChar)
  {
    for (Composite localComposite = this.parent; localComposite != null; localComposite = localComposite.parent)
    {
      Control[] arrayOfControl = localComposite._getChildren();
      for (int i = 0; i < arrayOfControl.length; i++)
        if (arrayOfControl[i] == this)
          break;
      i++;
      if ((i < arrayOfControl.length) && (arrayOfControl[i].setFocus()))
        return true;
    }
    return false;
  }

  boolean mnemonicMatch(char paramChar)
  {
    char c = findMnemonic(getText());
    if (c == 0)
      return false;
    return Character.toUpperCase(paramChar) == Character.toUpperCase(c);
  }

  void releaseWidget()
  {
    super.releaseWidget();
    this.text = null;
    this.image = null;
  }

  public void setAlignment(int paramInt)
  {
    checkWidget();
    if ((this.style & 0x2) != 0)
      return;
    if ((paramInt & 0x1024000) == 0)
      return;
    this.style &= -16924673;
    this.style |= paramInt & 0x1024000;
    int i = OS.GetWindowLong(this.handle, -16);
    if ((i & 0xD) != 13)
    {
      i &= -16;
      if ((this.style & 0x4000) != 0)
        if ((this.style & 0x40) != 0)
          i |= 0;
        else
          i |= 12;
      if ((this.style & 0x1000000) != 0)
        i |= 1;
      if ((this.style & 0x20000) != 0)
        i |= 2;
      OS.SetWindowLong(this.handle, -16, i);
    }
    OS.InvalidateRect(this.handle, null, true);
  }

  public void setImage(Image paramImage)
  {
    checkWidget();
    if ((this.style & 0x2) != 0)
      return;
    if ((paramImage != null) && (paramImage.isDisposed()))
      error(5);
    this.image = paramImage;
    int i = OS.GetWindowLong(this.handle, -16);
    if ((i & 0xD) != 13)
    {
      i &= -16;
      i |= 13;
      OS.SetWindowLong(this.handle, -16, i);
    }
    OS.InvalidateRect(this.handle, null, true);
  }

  public void setText(String paramString)
  {
    checkWidget();
    if (paramString == null)
      error(4);
    if ((this.style & 0x2) != 0)
      return;
    if (paramString.equals(this.text))
      return;
    this.text = paramString;
    if ((this.image == null) || (!IMAGE_AND_TEXT))
    {
      int i = OS.GetWindowLong(this.handle, -16);
      int j = i;
      j &= -14;
      if ((this.style & 0x4000) != 0)
        if ((this.style & 0x40) != 0)
          j |= 0;
        else
          j |= 12;
      if ((this.style & 0x1000000) != 0)
        j |= 1;
      if ((this.style & 0x20000) != 0)
        j |= 2;
      if (i != j)
        OS.SetWindowLong(this.handle, -16, j);
    }
    paramString = Display.withCrLf(paramString);
    TCHAR localTCHAR = new TCHAR(getCodePage(), paramString, true);
    OS.SetWindowText(this.handle, localTCHAR);
    if ((OS.COMCTL32_MAJOR < 6) && (findImageControl() != null))
      OS.InvalidateRect(this.handle, null, true);
  }

  int widgetExtStyle()
  {
    int i = super.widgetExtStyle() & 0xFFFFFDFF;
    if ((this.style & 0x800) != 0)
      return i | 0x20000;
    return i;
  }

  int widgetStyle()
  {
    int i = super.widgetStyle() | 0x100;
    if ((this.style & 0x2) != 0)
      return i | 0xD;
    if ((OS.WIN32_VERSION >= OS.VERSION(5, 0)) && ((this.style & 0x40) != 0))
      i |= 8192;
    if ((this.style & 0x1000000) != 0)
      return i | 0x1;
    if ((this.style & 0x20000) != 0)
      return i | 0x2;
    if ((this.style & 0x40) != 0)
      return i;
    return i | 0xC;
  }

  TCHAR windowClass()
  {
    return LabelClass;
  }

  int windowProc()
  {
    return LabelProc;
  }

  LRESULT WM_ERASEBKGND(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_ERASEBKGND(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    int i = OS.GetWindowLong(this.handle, -16);
    if ((i & 0xD) == 13)
      return LRESULT.ONE;
    if ((OS.COMCTL32_MAJOR < 6) && (findImageControl() != null))
    {
      drawBackground(paramInt1);
      return LRESULT.ONE;
    }
    return localLRESULT;
  }

  LRESULT WM_SIZE(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_SIZE(paramInt1, paramInt2);
    if (isDisposed())
      return localLRESULT;
    if ((this.style & 0x2) != 0)
    {
      OS.InvalidateRect(this.handle, null, true);
      return localLRESULT;
    }
    int i = OS.GetWindowLong(this.handle, -16);
    if ((i & 0xD) == 13)
    {
      OS.InvalidateRect(this.handle, null, true);
      return localLRESULT;
    }
    if ((i & 0xC) != 12)
    {
      OS.InvalidateRect(this.handle, null, true);
      return localLRESULT;
    }
    return localLRESULT;
  }

  LRESULT WM_UPDATEUISTATE(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_UPDATEUISTATE(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    int i = findImageControl() != null ? 1 : 0;
    if ((i == 0) && ((this.state & 0x100) != 0) && (OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()))
      i = findThemeControl() != null ? 1 : 0;
    if (i != 0)
    {
      OS.InvalidateRect(this.handle, null, false);
      int j = OS.DefWindowProc(this.handle, 296, paramInt1, paramInt2);
      return new LRESULT(j);
    }
    return localLRESULT;
  }

  LRESULT wmColorChild(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.wmColorChild(paramInt1, paramInt2);
    if (OS.COMCTL32_MAJOR < 6)
    {
      int i = OS.GetWindowLong(this.handle, -16);
      if (((i & 0xD) != 13) && (findImageControl() != null))
      {
        OS.SetBkMode(paramInt1, 1);
        return new LRESULT(OS.GetStockObject(5));
      }
    }
    return localLRESULT;
  }

  LRESULT WM_PAINT(int paramInt1, int paramInt2)
  {
    if (OS.IsWinCE)
    {
      int i = this.image != null ? 1 : 0;
      int j = ((this.style & 0x2) != 0) && ((this.style & 0x20) == 0) ? 1 : 0;
      if ((i != 0) || (j != 0))
      {
        LRESULT localLRESULT = null;
        PAINTSTRUCT localPAINTSTRUCT = new PAINTSTRUCT();
        GCData localGCData = new GCData();
        localGCData.ps = localPAINTSTRUCT;
        localGCData.hwnd = this.handle;
        GC localGC = new_GC(localGCData);
        if (localGC != null)
        {
          drawBackground(localGC.handle);
          RECT localRECT = new RECT();
          OS.GetClientRect(this.handle, localRECT);
          Object localObject;
          if (j != 0)
          {
            localObject = new RECT();
            m = OS.GetSystemMetrics(5);
            int n = (this.style & 0x4) != 0 ? 10 : 6;
            int i1;
            if ((this.style & 0x100) != 0)
            {
              i1 = localRECT.top + Math.max(m * 2, (localRECT.bottom - localRECT.top) / 2);
              OS.SetRect((RECT)localObject, localRECT.left, localRECT.top, localRECT.right, i1);
              OS.DrawEdge(localGC.handle, (RECT)localObject, n, 8);
            }
            else
            {
              i1 = localRECT.left + Math.max(m * 2, (localRECT.right - localRECT.left) / 2);
              OS.SetRect((RECT)localObject, localRECT.left, localRECT.top, i1, localRECT.bottom);
              OS.DrawEdge(localGC.handle, (RECT)localObject, n, 4);
            }
            localLRESULT = LRESULT.ONE;
          }
          if (i != 0)
          {
            localObject = this.image.getBounds();
            m = 0;
            if ((this.style & 0x1000000) != 0)
              m = Math.max(0, (localRECT.right - ((Rectangle)localObject).width) / 2);
            else if ((this.style & 0x20000) != 0)
              m = Math.max(0, localRECT.right - ((Rectangle)localObject).width);
            localGC.drawImage(this.image, m, Math.max(0, (localRECT.bottom - ((Rectangle)localObject).height) / 2));
            localLRESULT = LRESULT.ONE;
          }
          int k = localPAINTSTRUCT.right - localPAINTSTRUCT.left;
          int m = localPAINTSTRUCT.bottom - localPAINTSTRUCT.top;
          if ((k != 0) && (m != 0))
          {
            Event localEvent = new Event();
            localEvent.gc = localGC;
            localEvent.x = localPAINTSTRUCT.left;
            localEvent.y = localPAINTSTRUCT.top;
            localEvent.width = k;
            localEvent.height = m;
            sendEvent(9, localEvent);
            localEvent.gc = null;
          }
          localGC.dispose();
        }
        return localLRESULT;
      }
    }
    return super.WM_PAINT(paramInt1, paramInt2);
  }

  LRESULT wmDrawChild(int paramInt1, int paramInt2)
  {
    DRAWITEMSTRUCT localDRAWITEMSTRUCT = new DRAWITEMSTRUCT();
    OS.MoveMemory(localDRAWITEMSTRUCT, paramInt2, DRAWITEMSTRUCT.sizeof);
    drawBackground(localDRAWITEMSTRUCT.hDC);
    int j;
    int k;
    int m;
    if ((this.style & 0x2) != 0)
    {
      if ((this.style & 0x20) != 0)
        return null;
      RECT localRECT = new RECT();
      j = OS.GetSystemMetrics(5);
      k = (this.style & 0x4) != 0 ? 10 : 6;
      if ((this.style & 0x100) != 0)
      {
        m = localDRAWITEMSTRUCT.top + Math.max(j * 2, (localDRAWITEMSTRUCT.bottom - localDRAWITEMSTRUCT.top) / 2);
        OS.SetRect(localRECT, localDRAWITEMSTRUCT.left, localDRAWITEMSTRUCT.top, localDRAWITEMSTRUCT.right, m);
        OS.DrawEdge(localDRAWITEMSTRUCT.hDC, localRECT, k, 8);
      }
      else
      {
        m = localDRAWITEMSTRUCT.left + Math.max(j * 2, (localDRAWITEMSTRUCT.right - localDRAWITEMSTRUCT.left) / 2);
        OS.SetRect(localRECT, localDRAWITEMSTRUCT.left, localDRAWITEMSTRUCT.top, m, localDRAWITEMSTRUCT.bottom);
        OS.DrawEdge(localDRAWITEMSTRUCT.hDC, localRECT, k, 4);
      }
    }
    else
    {
      int i = localDRAWITEMSTRUCT.right - localDRAWITEMSTRUCT.left;
      j = localDRAWITEMSTRUCT.bottom - localDRAWITEMSTRUCT.top;
      if ((i != 0) && (j != 0))
      {
        k = this.image != null ? 1 : 0;
        m = (IMAGE_AND_TEXT) && (this.text.length() != 0) ? 1 : 0;
        int n = (m != 0) && (k != 0) ? 4 : 0;
        int i1 = 0;
        int i2 = 0;
        if (k != 0)
        {
          localObject = this.image.getBounds();
          i1 = ((Rectangle)localObject).width;
          i2 = ((Rectangle)localObject).height;
        }
        Object localObject = null;
        TCHAR localTCHAR = null;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        if (m != 0)
        {
          localObject = new RECT();
          i5 = 9280;
          if ((this.style & 0x4000) != 0)
            i5 |= 0;
          if ((this.style & 0x1000000) != 0)
            i5 |= 1;
          if ((this.style & 0x20000) != 0)
            i5 |= 2;
          if ((this.style & 0x40) != 0)
          {
            i5 |= 16;
            ((RECT)localObject).right = Math.max(0, i - i1 - n);
          }
          localTCHAR = new TCHAR(getCodePage(), this.text, true);
          OS.DrawText(localDRAWITEMSTRUCT.hDC, localTCHAR, -1, (RECT)localObject, i5);
          i3 = ((RECT)localObject).right - ((RECT)localObject).left;
          i4 = ((RECT)localObject).bottom - ((RECT)localObject).top;
        }
        int i6 = 0;
        if ((this.style & 0x1000000) != 0)
          i6 = Math.max(0, (i - i1 - i3 - n) / 2);
        else if ((this.style & 0x20000) != 0)
          i6 = i - i1 - i3 - n;
        if (k != 0)
        {
          GCData localGCData = new GCData();
          localGCData.device = this.display;
          GC localGC = GC.win32_new(localDRAWITEMSTRUCT.hDC, localGCData);
          Image localImage = getEnabled() ? this.image : new Image(this.display, this.image, 1);
          localGC.drawImage(localImage, i6, Math.max(0, (j - i2) / 2));
          if (localImage != this.image)
            localImage.dispose();
          localGC.dispose();
          i6 += i1 + n;
        }
        if (m != 0)
        {
          i5 &= -1025;
          ((RECT)localObject).left = i6;
          localObject.right += ((RECT)localObject).left;
          ((RECT)localObject).top = Math.max(0, (j - i4) / 2);
          localObject.bottom += ((RECT)localObject).top;
          OS.DrawText(localDRAWITEMSTRUCT.hDC, localTCHAR, -1, (RECT)localObject, i5);
        }
      }
    }
    return null;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.Label
 * JD-Core Version:    0.6.2
 */