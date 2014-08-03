package org.eclipse.swt.widgets;

import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.ImageList;
import org.eclipse.swt.internal.win32.BITMAP;
import org.eclipse.swt.internal.win32.BUTTON_IMAGELIST;
import org.eclipse.swt.internal.win32.DRAWITEMSTRUCT;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.SIZE;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.TEXTMETRIC;
import org.eclipse.swt.internal.win32.TEXTMETRICA;
import org.eclipse.swt.internal.win32.TEXTMETRICW;
import org.eclipse.swt.internal.win32.WNDCLASS;

public class Button extends Control
{
  String text = "";
  String message = "";
  Image image;
  Image image2;
  Image disabledImage;
  ImageList imageList;
  boolean ignoreMouse;
  boolean grayed;
  static final int MARGIN = 4;
  static final int CHECK_WIDTH;
  static final int CHECK_HEIGHT;
  static final int ICON_WIDTH = 128;
  static final int ICON_HEIGHT = 128;
  static boolean COMMAND_LINK = false;
  static final int ButtonProc = ((WNDCLASS)localObject).lpfnWndProc;
  static final TCHAR ButtonClass = new TCHAR(0, "BUTTON", true);

  static
  {
    int i = OS.LoadBitmap(0, 32759);
    if (i == 0)
    {
      CHECK_WIDTH = OS.GetSystemMetrics(OS.IsWinCE ? 49 : 2);
      CHECK_HEIGHT = OS.GetSystemMetrics(OS.IsWinCE ? 50 : 20);
    }
    else
    {
      localObject = new BITMAP();
      OS.GetObject(i, BITMAP.sizeof, (BITMAP)localObject);
      OS.DeleteObject(i);
      CHECK_WIDTH = ((BITMAP)localObject).bmWidth / 4;
      CHECK_HEIGHT = ((BITMAP)localObject).bmHeight / 3;
    }
    Object localObject = new WNDCLASS();
    OS.GetClassInfo(0, ButtonClass, (WNDCLASS)localObject);
  }

  public Button(Composite paramComposite, int paramInt)
  {
    super(paramComposite, checkStyle(paramInt));
  }

  void _setImage(Image paramImage)
  {
    if ((this.style & 0x400000) != 0)
      return;
    int j;
    int k;
    if (OS.COMCTL32_MAJOR >= 6)
    {
      if (this.imageList != null)
        this.imageList.dispose();
      this.imageList = null;
      if (paramImage != null)
      {
        this.imageList = new ImageList(this.style & 0x4000000);
        if (OS.IsWindowEnabled(this.handle))
        {
          this.imageList.add(paramImage);
        }
        else
        {
          if (this.disabledImage != null)
            this.disabledImage.dispose();
          this.disabledImage = new Image(this.display, paramImage, 1);
          this.imageList.add(this.disabledImage);
        }
        BUTTON_IMAGELIST localBUTTON_IMAGELIST = new BUTTON_IMAGELIST();
        localBUTTON_IMAGELIST.himl = this.imageList.getHandle();
        j = OS.GetWindowLong(this.handle, -16);
        k = j;
        k &= -769;
        if ((this.style & 0x4000) != 0)
          k |= 256;
        if ((this.style & 0x1000000) != 0)
          k |= 768;
        if ((this.style & 0x20000) != 0)
          k |= 512;
        if (this.text.length() == 0)
        {
          if ((this.style & 0x4000) != 0)
            localBUTTON_IMAGELIST.uAlign = 0;
          if ((this.style & 0x1000000) != 0)
            localBUTTON_IMAGELIST.uAlign = 4;
          if ((this.style & 0x20000) != 0)
            localBUTTON_IMAGELIST.uAlign = 1;
        }
        else
        {
          localBUTTON_IMAGELIST.uAlign = 0;
          localBUTTON_IMAGELIST.margin_left = computeLeftMargin();
          localBUTTON_IMAGELIST.margin_right = 4;
          k &= -769;
          k |= 256;
        }
        if (k != j)
        {
          OS.SetWindowLong(this.handle, -16, k);
          OS.InvalidateRect(this.handle, null, true);
        }
        OS.SendMessage(this.handle, 5634, 0, localBUTTON_IMAGELIST);
      }
      else
      {
        OS.SendMessage(this.handle, 5634, 0, 0);
      }
      OS.InvalidateRect(this.handle, null, true);
    }
    else
    {
      if (this.image2 != null)
        this.image2.dispose();
      this.image2 = null;
      int i = 0;
      j = 0;
      k = 0;
      if (paramImage != null)
      {
        Rectangle localRectangle;
        switch (paramImage.type)
        {
        case 0:
          localRectangle = paramImage.getBounds();
          ImageData localImageData = paramImage.getImageData();
          switch (localImageData.getTransparencyType())
          {
          case 4:
            if ((localRectangle.width <= 128) && (localRectangle.height <= 128))
            {
              this.image2 = new Image(this.display, localImageData, localImageData.getTransparencyMask());
              i = this.image2.handle;
              j = 64;
              k = 1;
            }
            break;
          case 1:
            this.image2 = new Image(this.display, localRectangle.width, localRectangle.height);
            GC localGC = new GC(this.image2);
            localGC.setBackground(getBackground());
            localGC.fillRectangle(localRectangle);
            localGC.drawImage(paramImage, 0, 0);
            localGC.dispose();
            i = this.image2.handle;
            j = 128;
            k = 0;
            break;
          case 0:
            i = paramImage.handle;
            j = 128;
            k = 0;
          case 2:
          case 3:
          }
          break;
        case 1:
          i = paramImage.handle;
          j = 64;
          k = 1;
        }
        if (((this.style & 0x4000000) != 0) && (!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(4, 10)))
        {
          localRectangle = paramImage.getBounds();
          n = OS.GetDC(this.handle);
          int i1 = OS.CreateCompatibleDC(n);
          int i2 = OS.CreateCompatibleBitmap(n, localRectangle.width, localRectangle.height);
          int i3 = OS.SelectObject(i1, i2);
          OS.SetLayout(i1, 1);
          int i5;
          if (k == 0)
          {
            int i4 = OS.CreateCompatibleDC(n);
            i5 = OS.SelectObject(i4, i);
            OS.SetLayout(i1, 0);
            OS.BitBlt(i1, 0, 0, localRectangle.width, localRectangle.height, i4, 0, 0, 13369376);
            OS.SelectObject(i4, i5);
            OS.DeleteDC(i4);
          }
          else
          {
            Object localObject = findBackgroundControl();
            if (localObject == null)
              localObject = this;
            i5 = OS.CreateSolidBrush(((Control)localObject).getBackgroundPixel());
            int i6 = OS.SelectObject(i1, i5);
            OS.PatBlt(i1, 0, 0, localRectangle.width, localRectangle.height, 15728673);
            OS.DrawIconEx(i1, 0, 0, i, 0, 0, 0, 0, 3);
            OS.SelectObject(i1, i6);
            OS.DeleteObject(i5);
          }
          OS.SelectObject(i1, i3);
          OS.DeleteDC(i1);
          OS.ReleaseDC(this.handle, n);
          if (this.image2 != null)
            this.image2.dispose();
          this.image2 = Image.win32_new(this.display, 0, i2);
          j = 128;
          k = 0;
          i = i2;
        }
      }
      int m = OS.GetWindowLong(this.handle, -16);
      int n = m;
      m &= -193;
      m |= j;
      if (m != n)
        OS.SetWindowLong(this.handle, -16, m);
      OS.SendMessage(this.handle, 247, k, i);
    }
  }

  void _setText(String paramString)
  {
    int i = OS.GetWindowLong(this.handle, -16);
    int j = i;
    if (OS.COMCTL32_MAJOR >= 6)
    {
      j &= -769;
      if ((this.style & 0x4000) != 0)
        j |= 256;
      if ((this.style & 0x1000000) != 0)
        j |= 768;
      if ((this.style & 0x20000) != 0)
        j |= 512;
      if (this.imageList != null)
      {
        localObject = new BUTTON_IMAGELIST();
        ((BUTTON_IMAGELIST)localObject).himl = this.imageList.getHandle();
        if (paramString.length() == 0)
        {
          if ((this.style & 0x4000) != 0)
            ((BUTTON_IMAGELIST)localObject).uAlign = 0;
          if ((this.style & 0x1000000) != 0)
            ((BUTTON_IMAGELIST)localObject).uAlign = 4;
          if ((this.style & 0x20000) != 0)
            ((BUTTON_IMAGELIST)localObject).uAlign = 1;
        }
        else
        {
          ((BUTTON_IMAGELIST)localObject).uAlign = 0;
          ((BUTTON_IMAGELIST)localObject).margin_left = computeLeftMargin();
          ((BUTTON_IMAGELIST)localObject).margin_right = 4;
          j &= -769;
          j |= 256;
        }
        OS.SendMessage(this.handle, 5634, 0, (BUTTON_IMAGELIST)localObject);
      }
    }
    else
    {
      j &= -193;
    }
    if (j != i)
    {
      OS.SetWindowLong(this.handle, -16, j);
      OS.InvalidateRect(this.handle, null, true);
    }
    if (((this.style & 0x4000000) != 0) && ((OS.COMCTL32_MAJOR < 6) || (!OS.IsAppThemed())))
      paramString = " " + paramString + " ";
    Object localObject = new TCHAR(getCodePage(), paramString, true);
    OS.SetWindowText(this.handle, (TCHAR)localObject);
  }

  public void addSelectionListener(SelectionListener paramSelectionListener)
  {
    checkWidget();
    if (paramSelectionListener == null)
      error(4);
    TypedListener localTypedListener = new TypedListener(paramSelectionListener);
    addListener(13, localTypedListener);
    addListener(14, localTypedListener);
  }

  int callWindowProc(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.handle == 0)
      return 0;
    return OS.CallWindowProc(ButtonProc, paramInt1, paramInt2, paramInt3, paramInt4);
  }

  static int checkStyle(int paramInt)
  {
    paramInt = checkBits(paramInt, 8, 4, 32, 16, 2, COMMAND_LINK ? 4194304 : 0);
    if ((paramInt & 0xA) != 0)
      return checkBits(paramInt, 16777216, 16384, 131072, 0, 0, 0);
    if ((paramInt & 0x30) != 0)
      return checkBits(paramInt, 16384, 131072, 16777216, 0, 0, 0);
    if ((paramInt & 0x4) != 0)
    {
      paramInt |= 524288;
      return checkBits(paramInt, 128, 1024, 16384, 131072, 0, 0);
    }
    return paramInt;
  }

  void click()
  {
    this.ignoreMouse = true;
    OS.SendMessage(this.handle, 245, 0, 0);
    this.ignoreMouse = false;
  }

  int computeLeftMargin()
  {
    if (OS.COMCTL32_MAJOR < 6)
      return 4;
    if ((this.style & 0xA) == 0)
      return 4;
    int i = 0;
    if ((this.image != null) && (this.text.length() != 0))
    {
      Rectangle localRectangle = this.image.getBounds();
      i += localRectangle.width + 8;
      int j = 0;
      int k = OS.GetDC(this.handle);
      int m = OS.SendMessage(this.handle, 49, 0, 0);
      if (m != 0)
        j = OS.SelectObject(k, m);
      TCHAR localTCHAR = new TCHAR(getCodePage(), this.text, true);
      RECT localRECT = new RECT();
      int n = 1056;
      OS.DrawText(k, localTCHAR, -1, localRECT, n);
      i += localRECT.right - localRECT.left;
      if (m != 0)
        OS.SelectObject(k, j);
      OS.ReleaseDC(this.handle, k);
      OS.GetClientRect(this.handle, localRECT);
      i = Math.max(4, (localRECT.right - localRECT.left - i) / 2);
    }
    return i;
  }

  public Point computeSize(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    checkWidget();
    int i = 0;
    int j = 0;
    int k = getBorderWidth();
    if ((this.style & 0x4) != 0)
    {
      if ((this.style & 0x480) != 0)
      {
        i += OS.GetSystemMetrics(2);
        j += OS.GetSystemMetrics(20);
      }
      else
      {
        i += OS.GetSystemMetrics(21);
        j += OS.GetSystemMetrics(3);
      }
    }
    else if ((this.style & 0x400000) != 0)
    {
      SIZE localSIZE = new SIZE();
      if (paramInt1 != -1)
      {
        localSIZE.cx = paramInt1;
        OS.SendMessage(this.handle, 5633, 0, localSIZE);
        i = localSIZE.cx;
        j = localSIZE.cy;
      }
      else
      {
        OS.SendMessage(this.handle, 5633, 0, localSIZE);
        i = localSIZE.cy;
        j = localSIZE.cy;
        localSIZE.cy = 0;
        while (localSIZE.cy != j)
        {
          localSIZE.cx = (i++);
          localSIZE.cy = 0;
          OS.SendMessage(this.handle, 5633, 0, localSIZE);
        }
      }
    }
    else
    {
      int m = 0;
      int n = this.image != null ? 1 : 0;
      int i1 = 1;
      if ((OS.COMCTL32_MAJOR < 6) && ((this.style & 0x8) == 0))
      {
        int i2 = OS.GetWindowLong(this.handle, -16);
        n = (i2 & 0xC0) != 0 ? 1 : 0;
        if (n != 0)
          i1 = 0;
      }
      if ((n != 0) && (this.image != null))
      {
        Rectangle localRectangle = this.image.getBounds();
        i = localRectangle.width;
        if ((i1 != 0) && (this.text.length() != 0))
          i += 8;
        j = localRectangle.height;
        m = 8;
      }
      if (i1 != 0)
      {
        int i3 = 0;
        int i4 = OS.GetDC(this.handle);
        int i5 = OS.SendMessage(this.handle, 49, 0, 0);
        if (i5 != 0)
          i3 = OS.SelectObject(i4, i5);
        TEXTMETRICA localTEXTMETRICA = OS.IsUnicode ? new TEXTMETRICW() : new TEXTMETRICA();
        OS.GetTextMetrics(i4, localTEXTMETRICA);
        int i6 = this.text.length();
        if (i6 == 0)
        {
          j = Math.max(j, localTEXTMETRICA.tmHeight);
        }
        else
        {
          m = Math.max(8, localTEXTMETRICA.tmAveCharWidth);
          TCHAR localTCHAR = new TCHAR(getCodePage(), this.text, true);
          RECT localRECT = new RECT();
          int i7 = 1056;
          OS.DrawText(i4, localTCHAR, -1, localRECT, i7);
          i += localRECT.right - localRECT.left;
          j = Math.max(j, localRECT.bottom - localRECT.top);
        }
        if (i5 != 0)
          OS.SelectObject(i4, i3);
        OS.ReleaseDC(this.handle, i4);
      }
      if ((this.style & 0x30) != 0)
      {
        i += CHECK_WIDTH + m;
        j = Math.max(j, CHECK_HEIGHT + 3);
      }
      if ((this.style & 0xA) != 0)
      {
        i += 12;
        j += 10;
      }
    }
    if (paramInt1 != -1)
      i = paramInt1;
    if (paramInt2 != -1)
      j = paramInt2;
    i += k * 2;
    j += k * 2;
    return new Point(i, j);
  }

  void createHandle()
  {
    this.parent.state |= 1048576;
    super.createHandle();
    this.parent.state &= -1048577;
    if ((OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()))
      if ((!OS.IsWinCE) && (OS.WIN32_VERSION < OS.VERSION(6, 0)))
        this.state |= 256;
      else if ((this.style & 0xA) == 0)
        this.state |= 256;
    if ((OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()) && ((this.style & 0x10) != 0))
      this.state |= 512;
    if ((OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()) && (!OS.IsWinCE) && (OS.WIN32_VERSION < OS.VERSION(6, 0)) && ((this.style & 0xA) != 0))
      this.state |= 512;
  }

  int defaultBackground()
  {
    if ((this.style & 0xA) != 0)
      return OS.GetSysColor(OS.COLOR_BTNFACE);
    return super.defaultBackground();
  }

  int defaultForeground()
  {
    return OS.GetSysColor(OS.COLOR_BTNTEXT);
  }

  void enableWidget(boolean paramBoolean)
  {
    super.enableWidget(paramBoolean);
    if (((this.style & 0x4000000) != 0) && ((OS.COMCTL32_MAJOR < 6) || (!OS.IsAppThemed())))
    {
      int i = OS.GetWindowLong(this.handle, -16);
      int j = (i & 0xC0) != 0 ? 1 : 0;
      if (j == 0)
      {
        String str = " " + this.text + " ";
        TCHAR localTCHAR = new TCHAR(getCodePage(), str, true);
        OS.SetWindowText(this.handle, localTCHAR);
      }
    }
    if ((OS.COMCTL32_MAJOR >= 6) && (this.imageList != null))
    {
      BUTTON_IMAGELIST localBUTTON_IMAGELIST = new BUTTON_IMAGELIST();
      OS.SendMessage(this.handle, 5635, 0, localBUTTON_IMAGELIST);
      if (this.imageList != null)
        this.imageList.dispose();
      this.imageList = new ImageList(this.style & 0x4000000);
      if (OS.IsWindowEnabled(this.handle))
      {
        this.imageList.add(this.image);
      }
      else
      {
        if (this.disabledImage != null)
          this.disabledImage.dispose();
        this.disabledImage = new Image(this.display, this.image, 1);
        this.imageList.add(this.disabledImage);
      }
      localBUTTON_IMAGELIST.himl = this.imageList.getHandle();
      OS.SendMessage(this.handle, 5634, 0, localBUTTON_IMAGELIST);
      OS.InvalidateRect(this.handle, null, true);
    }
  }

  public int getAlignment()
  {
    checkWidget();
    if ((this.style & 0x4) != 0)
    {
      if ((this.style & 0x80) != 0)
        return 128;
      if ((this.style & 0x400) != 0)
        return 1024;
      if ((this.style & 0x4000) != 0)
        return 16384;
      if ((this.style & 0x20000) != 0)
        return 131072;
      return 128;
    }
    if ((this.style & 0x4000) != 0)
      return 16384;
    if ((this.style & 0x1000000) != 0)
      return 16777216;
    if ((this.style & 0x20000) != 0)
      return 131072;
    return 16384;
  }

  boolean getDefault()
  {
    if ((this.style & 0x8) == 0)
      return false;
    int i = OS.GetWindowLong(this.handle, -16);
    return (i & 0x1) != 0;
  }

  public boolean getGrayed()
  {
    checkWidget();
    if ((this.style & 0x20) == 0)
      return false;
    return this.grayed;
  }

  public Image getImage()
  {
    checkWidget();
    return this.image;
  }

  String getMessage()
  {
    checkWidget();
    return this.message;
  }

  String getNameText()
  {
    return getText();
  }

  public boolean getSelection()
  {
    checkWidget();
    if ((this.style & 0x32) == 0)
      return false;
    int i = OS.SendMessage(this.handle, 240, 0, 0);
    return i != 0;
  }

  public String getText()
  {
    checkWidget();
    if ((this.style & 0x4) != 0)
      return "";
    return this.text;
  }

  boolean isTabItem()
  {
    if ((this.style & 0x8) != 0)
      return isTabGroup();
    return super.isTabItem();
  }

  boolean mnemonicHit(char paramChar)
  {
    if (!setFocus())
      return false;
    if ((this.style & 0x10) == 0)
      click();
    return true;
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
    if (this.imageList != null)
      this.imageList.dispose();
    this.imageList = null;
    if (this.disabledImage != null)
      this.disabledImage.dispose();
    this.disabledImage = null;
    if (this.image2 != null)
      this.image2.dispose();
    this.image2 = null;
    this.text = null;
    this.image = null;
  }

  public void removeSelectionListener(SelectionListener paramSelectionListener)
  {
    checkWidget();
    if (paramSelectionListener == null)
      error(4);
    if (this.eventTable == null)
      return;
    this.eventTable.unhook(13, paramSelectionListener);
    this.eventTable.unhook(14, paramSelectionListener);
  }

  void selectRadio()
  {
    Control[] arrayOfControl = this.parent._getChildren();
    for (int i = 0; i < arrayOfControl.length; i++)
    {
      Control localControl = arrayOfControl[i];
      if (this != localControl)
        localControl.setRadioSelection(false);
    }
    setSelection(true);
  }

  public void setAlignment(int paramInt)
  {
    checkWidget();
    if ((this.style & 0x4) != 0)
    {
      if ((this.style & 0x24480) == 0)
        return;
      this.style &= -148609;
      this.style |= paramInt & 0x24480;
      OS.InvalidateRect(this.handle, null, true);
      return;
    }
    if ((paramInt & 0x1024000) == 0)
      return;
    this.style &= -16924673;
    this.style |= paramInt & 0x1024000;
    int i = OS.GetWindowLong(this.handle, -16);
    int j = i;
    j &= -769;
    if ((this.style & 0x4000) != 0)
      j |= 256;
    if ((this.style & 0x1000000) != 0)
      j |= 768;
    if ((this.style & 0x20000) != 0)
      j |= 512;
    if ((OS.COMCTL32_MAJOR >= 6) && (this.imageList != null))
    {
      BUTTON_IMAGELIST localBUTTON_IMAGELIST = new BUTTON_IMAGELIST();
      localBUTTON_IMAGELIST.himl = this.imageList.getHandle();
      if (this.text.length() == 0)
      {
        if ((this.style & 0x4000) != 0)
          localBUTTON_IMAGELIST.uAlign = 0;
        if ((this.style & 0x1000000) != 0)
          localBUTTON_IMAGELIST.uAlign = 4;
        if ((this.style & 0x20000) != 0)
          localBUTTON_IMAGELIST.uAlign = 1;
      }
      else
      {
        localBUTTON_IMAGELIST.uAlign = 0;
        localBUTTON_IMAGELIST.margin_left = computeLeftMargin();
        localBUTTON_IMAGELIST.margin_right = 4;
        j &= -769;
        j |= 256;
      }
      OS.SendMessage(this.handle, 5634, 0, localBUTTON_IMAGELIST);
    }
    if (j != i)
    {
      OS.SetWindowLong(this.handle, -16, j);
      OS.InvalidateRect(this.handle, null, true);
    }
  }

  void setDefault(boolean paramBoolean)
  {
    if ((this.style & 0x8) == 0)
      return;
    int i = menuShell().handle;
    int j = OS.GetWindowLong(this.handle, -16);
    if (paramBoolean)
    {
      j |= 1;
      OS.SendMessage(i, 1025, this.handle, 0);
    }
    else
    {
      j &= -2;
      OS.SendMessage(i, 1025, 0, 0);
    }
    OS.SendMessage(this.handle, 244, j, 1);
  }

  boolean setFixedFocus()
  {
    if (((this.style & 0x10) != 0) && (!getSelection()))
      return false;
    return super.setFixedFocus();
  }

  public void setImage(Image paramImage)
  {
    checkWidget();
    if ((paramImage != null) && (paramImage.isDisposed()))
      error(5);
    if ((this.style & 0x4) != 0)
      return;
    this.image = paramImage;
    _setImage(paramImage);
  }

  public void setGrayed(boolean paramBoolean)
  {
    checkWidget();
    if ((this.style & 0x20) == 0)
      return;
    this.grayed = paramBoolean;
    int i = OS.SendMessage(this.handle, 240, 0, 0);
    if (paramBoolean)
    {
      if (i == 1)
        updateSelection(2);
    }
    else if (i == 2)
      updateSelection(1);
  }

  void setMessage(String paramString)
  {
    checkWidget();
    if (paramString == null)
      error(4);
    this.message = paramString;
    if ((OS.COMCTL32_VERSION >= OS.VERSION(6, 1)) && ((this.style & 0x400000) != 0))
    {
      int i = paramString.length();
      char[] arrayOfChar = new char[i + 1];
      paramString.getChars(0, i, arrayOfChar, 0);
      OS.SendMessage(this.handle, 5641, 0, arrayOfChar);
    }
  }

  boolean setRadioFocus(boolean paramBoolean)
  {
    if (((this.style & 0x10) == 0) || (!getSelection()))
      return false;
    return paramBoolean ? setTabItemFocus() : setFocus();
  }

  boolean setRadioSelection(boolean paramBoolean)
  {
    if ((this.style & 0x10) == 0)
      return false;
    if (getSelection() != paramBoolean)
    {
      setSelection(paramBoolean);
      sendSelectionEvent(13);
    }
    return true;
  }

  boolean setSavedFocus()
  {
    if (((this.style & 0x10) != 0) && (!getSelection()))
      return false;
    return super.setSavedFocus();
  }

  public void setSelection(boolean paramBoolean)
  {
    checkWidget();
    if ((this.style & 0x32) == 0)
      return;
    int i = paramBoolean ? 1 : 0;
    if (((this.style & 0x20) != 0) && (paramBoolean) && (this.grayed))
      i = 2;
    updateSelection(i);
  }

  public void setText(String paramString)
  {
    checkWidget();
    if (paramString == null)
      error(4);
    if ((this.style & 0x4) != 0)
      return;
    this.text = paramString;
    _setText(paramString);
  }

  void updateSelection(int paramInt)
  {
    if (paramInt != OS.SendMessage(this.handle, 240, 0, 0))
    {
      int i = OS.GetWindowLong(this.handle, -16);
      if ((this.style & 0x20) != 0)
      {
        if (paramInt == 2)
        {
          i &= -3;
          i |= 5;
        }
        else
        {
          i |= 2;
          i &= -6;
        }
        if (i != OS.GetWindowLong(this.handle, -16))
          OS.SetWindowLong(this.handle, -16, i);
      }
      OS.SendMessage(this.handle, 241, paramInt, 0);
      if (i != OS.GetWindowLong(this.handle, -16))
        OS.SetWindowLong(this.handle, -16, i);
    }
  }

  int widgetStyle()
  {
    int i = super.widgetStyle();
    if ((this.style & 0x800000) != 0)
      i |= 32768;
    if ((this.style & 0x4) != 0)
      return i | 0xB;
    if ((this.style & 0x4000) != 0)
      i |= 256;
    if ((this.style & 0x1000000) != 0)
      i |= 768;
    if ((this.style & 0x20000) != 0)
      i |= 512;
    if ((this.style & 0x8) != 0)
      return i | 0x10000;
    if ((this.style & 0x20) != 0)
      return i | 0x2 | 0x10000;
    if ((this.style & 0x10) != 0)
      return i | 0x4;
    if ((this.style & 0x2) != 0)
      return i | 0x1000 | 0x2 | 0x10000;
    if ((this.style & 0x400000) != 0)
      return i | 0xE | 0x10000;
    return i | 0x10000;
  }

  TCHAR windowClass()
  {
    return ButtonClass;
  }

  int windowProc()
  {
    return ButtonProc;
  }

  LRESULT WM_ERASEBKGND(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_ERASEBKGND(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    if ((OS.COMCTL32_MAJOR < 6) && ((this.style & 0x30) != 0) && (findImageControl() != null))
    {
      drawBackground(paramInt1);
      return LRESULT.ONE;
    }
    return localLRESULT;
  }

  LRESULT WM_GETDLGCODE(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_GETDLGCODE(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    if ((this.style & 0x4) != 0)
      return new LRESULT(256);
    return localLRESULT;
  }

  LRESULT WM_KILLFOCUS(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_KILLFOCUS(paramInt1, paramInt2);
    if (((this.style & 0x8) != 0) && (getDefault()))
      menuShell().setDefaultButton(null, false);
    return localLRESULT;
  }

  LRESULT WM_LBUTTONDOWN(int paramInt1, int paramInt2)
  {
    if (this.ignoreMouse)
      return null;
    return super.WM_LBUTTONDOWN(paramInt1, paramInt2);
  }

  LRESULT WM_LBUTTONUP(int paramInt1, int paramInt2)
  {
    if (this.ignoreMouse)
      return null;
    return super.WM_LBUTTONUP(paramInt1, paramInt2);
  }

  LRESULT WM_SETFOCUS(int paramInt1, int paramInt2)
  {
    int i = 0;
    if ((this.style & 0x10) != 0)
      i = OS.GetWindowLong(this.handle, -16);
    LRESULT localLRESULT = super.WM_SETFOCUS(paramInt1, paramInt2);
    if ((this.style & 0x10) != 0)
      OS.SetWindowLong(this.handle, -16, i);
    if ((this.style & 0x8) != 0)
      menuShell().setDefaultButton(this, false);
    return localLRESULT;
  }

  LRESULT WM_SIZE(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_SIZE(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    if ((OS.COMCTL32_MAJOR >= 6) && ((this.style & 0xA) != 0) && (this.imageList != null) && (this.text.length() != 0))
    {
      BUTTON_IMAGELIST localBUTTON_IMAGELIST = new BUTTON_IMAGELIST();
      OS.SendMessage(this.handle, 5635, 0, localBUTTON_IMAGELIST);
      localBUTTON_IMAGELIST.uAlign = 0;
      localBUTTON_IMAGELIST.margin_left = computeLeftMargin();
      localBUTTON_IMAGELIST.margin_right = 4;
      OS.SendMessage(this.handle, 5634, 0, localBUTTON_IMAGELIST);
    }
    return localLRESULT;
  }

  LRESULT WM_SYSCOLORCHANGE(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_SYSCOLORCHANGE(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    if (this.image2 != null)
      _setImage(this.image);
    return localLRESULT;
  }

  LRESULT WM_UPDATEUISTATE(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_UPDATEUISTATE(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(6, 0)) && ((this.style & 0x32) != 0))
    {
      int i = findImageControl() != null ? 1 : 0;
      if (i == 0)
      {
        if (((this.state & 0x100) != 0) && (OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()))
          i = findThemeControl() != null ? 1 : 0;
        if (i == 0)
          i = findBackgroundControl() != null ? 1 : 0;
      }
      if (i != 0)
      {
        OS.InvalidateRect(this.handle, null, false);
        int j = OS.DefWindowProc(this.handle, 296, paramInt1, paramInt2);
        return new LRESULT(j);
      }
    }
    if (((this.style & 0xA) != 0) && ((hooks(9)) || (filters(9))))
      OS.InvalidateRect(this.handle, null, true);
    return localLRESULT;
  }

  LRESULT wmCommandChild(int paramInt1, int paramInt2)
  {
    int i = OS.HIWORD(paramInt1);
    switch (i)
    {
    case 0:
    case 5:
      if ((this.style & 0x22) != 0)
        setSelection(!getSelection());
      else if ((this.style & 0x10) != 0)
        if ((this.parent.getStyle() & 0x400000) != 0)
          setSelection(!getSelection());
        else
          selectRadio();
      sendSelectionEvent(13);
    }
    return super.wmCommandChild(paramInt1, paramInt2);
  }

  LRESULT wmColorChild(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.wmColorChild(paramInt1, paramInt2);
    if ((OS.COMCTL32_MAJOR < 6) && ((this.style & 0x30) != 0) && (findImageControl() != null))
    {
      OS.SetBkMode(paramInt1, 1);
      return new LRESULT(OS.GetStockObject(5));
    }
    return localLRESULT;
  }

  LRESULT wmDrawChild(int paramInt1, int paramInt2)
  {
    if ((this.style & 0x4) == 0)
      return super.wmDrawChild(paramInt1, paramInt2);
    DRAWITEMSTRUCT localDRAWITEMSTRUCT = new DRAWITEMSTRUCT();
    OS.MoveMemory(localDRAWITEMSTRUCT, paramInt2, DRAWITEMSTRUCT.sizeof);
    RECT localRECT = new RECT();
    OS.SetRect(localRECT, localDRAWITEMSTRUCT.left, localDRAWITEMSTRUCT.top, localDRAWITEMSTRUCT.right, localDRAWITEMSTRUCT.bottom);
    int i;
    if ((OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()))
    {
      i = 9;
      switch (this.style & 0x24480)
      {
      case 128:
        i = 1;
        break;
      case 1024:
        i = 5;
        break;
      case 16384:
        i = 9;
        break;
      case 131072:
        i = 13;
      }
      if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(6, 0)) && ((this.style & 0x8000000) != 0) && ((this.style & 0x24000) != 0))
        i = i == 13 ? 9 : 13;
      if (!getEnabled())
        i += 3;
      if ((localDRAWITEMSTRUCT.itemState & 0x1) != 0)
        i += 2;
      OS.DrawThemeBackground(this.display.hScrollBarTheme(), localDRAWITEMSTRUCT.hDC, 1, i, localRECT, null);
    }
    else
    {
      i = 2;
      switch (this.style & 0x24480)
      {
      case 128:
        i = 0;
        break;
      case 1024:
        i = 1;
        break;
      case 16384:
        i = 2;
        break;
      case 131072:
        i = 3;
      }
      if (!getEnabled())
        i |= 256;
      if ((this.style & 0x800000) == 8388608)
        i |= 16384;
      if ((localDRAWITEMSTRUCT.itemState & 0x1) != 0)
        i |= 512;
      OS.DrawFrameControl(localDRAWITEMSTRUCT.hDC, localRECT, 3, i);
    }
    return null;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.Button
 * JD-Core Version:    0.6.2
 */