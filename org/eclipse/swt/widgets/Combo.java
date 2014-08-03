package org.eclipse.swt.widgets;

import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.internal.Callback;
import org.eclipse.swt.internal.win32.COMBOBOXINFO;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.MONITORINFO;
import org.eclipse.swt.internal.win32.MSG;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.WINDOWPOS;
import org.eclipse.swt.internal.win32.WNDCLASS;

public class Combo extends Composite
{
  boolean noSelection;
  boolean ignoreDefaultSelection;
  boolean ignoreCharacter;
  boolean ignoreModify;
  boolean ignoreResize;
  boolean lockText;
  int scrollWidth;
  int visibleCount;
  int cbtHook;
  static final int VISIBLE_COUNT = 5;
  public static final int LIMIT = OS.IsWinNT ? 2147483647 : 32767;
  static final int CBID_LIST = 1000;
  static final int CBID_EDIT = 1001;
  static int EditProc;
  static int ListProc;
  static final int ComboProc = localWNDCLASS.lpfnWndProc;
  static final TCHAR ComboClass = new TCHAR(0, "COMBOBOX", true);

  static
  {
    WNDCLASS localWNDCLASS = new WNDCLASS();
    OS.GetClassInfo(0, ComboClass, localWNDCLASS);
  }

  public Combo(Composite paramComposite, int paramInt)
  {
    super(paramComposite, checkStyle(paramInt));
    this.style |= 256;
  }

  public void add(String paramString)
  {
    checkWidget();
    if (paramString == null)
      error(4);
    TCHAR localTCHAR = new TCHAR(getCodePage(), paramString, true);
    int i = OS.SendMessage(this.handle, 323, 0, localTCHAR);
    if (i == -1)
      error(14);
    if (i == -2)
      error(14);
    if ((this.style & 0x100) != 0)
      setScrollWidth(localTCHAR, true);
  }

  public void add(String paramString, int paramInt)
  {
    checkWidget();
    if (paramString == null)
      error(4);
    int i = OS.SendMessage(this.handle, 326, 0, 0);
    if ((paramInt < 0) || (paramInt > i))
      error(6);
    TCHAR localTCHAR = new TCHAR(getCodePage(), paramString, true);
    int j = OS.SendMessage(this.handle, 330, paramInt, localTCHAR);
    if ((j == -2) || (j == -1))
      error(14);
    if ((this.style & 0x100) != 0)
      setScrollWidth(localTCHAR, true);
  }

  public void addModifyListener(ModifyListener paramModifyListener)
  {
    checkWidget();
    if (paramModifyListener == null)
      error(4);
    TypedListener localTypedListener = new TypedListener(paramModifyListener);
    addListener(24, localTypedListener);
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

  public void addVerifyListener(VerifyListener paramVerifyListener)
  {
    checkWidget();
    if (paramVerifyListener == null)
      error(4);
    TypedListener localTypedListener = new TypedListener(paramVerifyListener);
    addListener(25, localTypedListener);
  }

  int callWindowProc(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.handle == 0)
      return 0;
    if (paramInt1 == this.handle)
    {
      switch (paramInt2)
      {
      case 5:
        this.ignoreResize = true;
        boolean bool = this.lockText;
        if ((this.style & 0x8) == 0)
          this.lockText = true;
        j = OS.CallWindowProc(ComboProc, paramInt1, paramInt2, paramInt3, paramInt4);
        if ((this.style & 0x8) == 0)
          this.lockText = bool;
        this.ignoreResize = false;
        return j;
      }
      return OS.CallWindowProc(ComboProc, paramInt1, paramInt2, paramInt3, paramInt4);
    }
    int i = OS.GetDlgItem(this.handle, 1001);
    if (paramInt1 == i)
    {
      if ((this.lockText) && (paramInt2 == 12))
        return 0;
      return OS.CallWindowProc(EditProc, paramInt1, paramInt2, paramInt3, paramInt4);
    }
    int j = OS.GetDlgItem(this.handle, 1000);
    if (paramInt1 == j)
      return OS.CallWindowProc(ListProc, paramInt1, paramInt2, paramInt3, paramInt4);
    return OS.DefWindowProc(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  int CBTProc(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt1 == 3)
    {
      TCHAR localTCHAR = new TCHAR(0, 128);
      OS.GetClassName(paramInt2, localTCHAR, localTCHAR.length());
      String str = localTCHAR.toString(0, localTCHAR.strlen());
      if ((str.equals("Edit")) || (str.equals("EDIT")))
      {
        int i = OS.GetWindowLong(paramInt2, -16);
        OS.SetWindowLong(paramInt2, -16, i & 0xFFFFFEFF);
      }
    }
    return OS.CallNextHookEx(this.cbtHook, paramInt1, paramInt2, paramInt3);
  }

  boolean checkHandle(int paramInt)
  {
    return (paramInt == this.handle) || (paramInt == OS.GetDlgItem(this.handle, 1001)) || (paramInt == OS.GetDlgItem(this.handle, 1000));
  }

  protected void checkSubclass()
  {
    if (!isValidSubclass())
      error(43);
  }

  static int checkStyle(int paramInt)
  {
    paramInt &= -2049;
    paramInt &= -769;
    paramInt = checkBits(paramInt, 4, 64, 0, 0, 0, 0);
    if ((paramInt & 0x40) != 0)
      return paramInt & 0xFFFFFFF7;
    return paramInt;
  }

  public void clearSelection()
  {
    checkWidget();
    OS.SendMessage(this.handle, 322, 0, -1);
  }

  public Point computeSize(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    checkWidget();
    int i = 0;
    int j = 0;
    int m;
    int n;
    int k;
    if (paramInt1 == -1)
    {
      m = 0;
      n = OS.GetDC(this.handle);
      k = OS.SendMessage(this.handle, 49, 0, 0);
      if (k != 0)
        m = OS.SelectObject(n, k);
      int i1 = OS.SendMessage(this.handle, 326, 0, 0);
      RECT localRECT = new RECT();
      int i2 = 3072;
      if ((this.style & 0x8) == 0)
        i2 |= 8192;
      int i3 = OS.GetWindowTextLength(this.handle);
      int i4 = getCodePage();
      TCHAR localTCHAR = new TCHAR(i4, i3 + 1);
      OS.GetWindowText(this.handle, localTCHAR, i3 + 1);
      OS.DrawText(n, localTCHAR, i3, localRECT, i2);
      i = Math.max(i, localRECT.right - localRECT.left);
      if ((this.style & 0x100) != 0)
        i = Math.max(i, this.scrollWidth);
      else
        for (int i5 = 0; i5 < i1; i5++)
        {
          i3 = OS.SendMessage(this.handle, 329, i5, 0);
          if (i3 != -1)
          {
            if (i3 + 1 > localTCHAR.length())
              localTCHAR = new TCHAR(i4, i3 + 1);
            int i6 = OS.SendMessage(this.handle, 328, i5, localTCHAR);
            if (i6 != -1)
            {
              OS.DrawText(n, localTCHAR, i3, localRECT, i2);
              i = Math.max(i, localRECT.right - localRECT.left);
            }
          }
        }
      if (k != 0)
        OS.SelectObject(n, m);
      OS.ReleaseDC(this.handle, n);
    }
    if ((paramInt2 == -1) && ((this.style & 0x40) != 0))
    {
      k = OS.SendMessage(this.handle, 326, 0, 0);
      m = OS.SendMessage(this.handle, 340, 0, 0);
      j = k * m;
    }
    if (i == 0)
      i = 64;
    if (j == 0)
      j = 64;
    if (paramInt1 != -1)
      i = paramInt1;
    if (paramInt2 != -1)
      j = paramInt2;
    if ((this.style & 0x8) != 0)
    {
      i += 8;
    }
    else
    {
      k = OS.GetDlgItem(this.handle, 1001);
      if (k != 0)
      {
        m = OS.SendMessage(k, 212, 0, 0);
        n = OS.LOWORD(m) + OS.HIWORD(m);
        i += n + 3;
      }
    }
    COMBOBOXINFO localCOMBOBOXINFO = new COMBOBOXINFO();
    localCOMBOBOXINFO.cbSize = COMBOBOXINFO.sizeof;
    if (((this.style & 0x40) == 0) && (!OS.IsWinCE) && (OS.GetComboBoxInfo(this.handle, localCOMBOBOXINFO)))
    {
      i += localCOMBOBOXINFO.itemLeft + (localCOMBOBOXINFO.buttonRight - localCOMBOBOXINFO.buttonLeft);
      j = localCOMBOBOXINFO.buttonBottom - localCOMBOBOXINFO.buttonTop + localCOMBOBOXINFO.buttonTop * 2;
    }
    else
    {
      m = OS.GetSystemMetrics(45);
      i += OS.GetSystemMetrics(2) + m * 2;
      n = OS.SendMessage(this.handle, 340, -1, 0);
      if ((this.style & 0x4) != 0)
        j = n + 6;
      else
        j += n + 10;
    }
    if (((this.style & 0x40) != 0) && ((this.style & 0x100) != 0))
      j += OS.GetSystemMetrics(3);
    return new Point(i, j);
  }

  public void copy()
  {
    checkWidget();
    OS.SendMessage(this.handle, 769, 0, 0);
  }

  void createHandle()
  {
    int k;
    if ((OS.IsWinCE) || ((this.style & 0x48) != 0))
    {
      super.createHandle();
    }
    else
    {
      i = OS.GetCurrentThreadId();
      Callback localCallback = new Callback(this, "CBTProc", 3);
      k = localCallback.getAddress();
      if (k == 0)
        error(3);
      this.cbtHook = OS.SetWindowsHookEx(5, k, 0, i);
      super.createHandle();
      if (this.cbtHook != 0)
        OS.UnhookWindowsHookEx(this.cbtHook);
      this.cbtHook = 0;
      localCallback.dispose();
    }
    this.state &= -259;
    int i = OS.GetDlgItem(this.handle, 1001);
    if ((i != 0) && (EditProc == 0))
      EditProc = OS.GetWindowLongPtr(i, -4);
    int j = OS.GetDlgItem(this.handle, 1000);
    if ((j != 0) && (ListProc == 0))
      ListProc = OS.GetWindowLongPtr(j, -4);
    if ((this.style & 0x40) != 0)
    {
      k = 52;
      SetWindowPos(this.handle, 0, 0, 0, 16383, 16383, k);
      SetWindowPos(this.handle, 0, 0, 0, 0, 0, k);
    }
  }

  void createWidget()
  {
    super.createWidget();
    this.visibleCount = 5;
    if ((this.style & 0x40) == 0)
    {
      int i = OS.SendMessage(this.handle, 340, 0, 0);
      if ((i != -1) && (i != 0))
      {
        int j = 0;
        if ((OS.IsWinCE) || (OS.WIN32_VERSION < OS.VERSION(4, 10)))
        {
          RECT localRECT = new RECT();
          OS.SystemParametersInfo(48, 0, localRECT, 0);
          j = (localRECT.bottom - localRECT.top) / 3;
        }
        else
        {
          int k = OS.MonitorFromWindow(this.handle, 2);
          MONITORINFO localMONITORINFO = new MONITORINFO();
          localMONITORINFO.cbSize = MONITORINFO.sizeof;
          OS.GetMonitorInfo(k, localMONITORINFO);
          j = (localMONITORINFO.rcWork_bottom - localMONITORINFO.rcWork_top) / 3;
        }
        this.visibleCount = Math.max(this.visibleCount, j / i);
      }
    }
  }

  public void cut()
  {
    checkWidget();
    if ((this.style & 0x8) != 0)
      return;
    OS.SendMessage(this.handle, 768, 0, 0);
  }

  int defaultBackground()
  {
    return OS.GetSysColor(OS.COLOR_WINDOW);
  }

  void deregister()
  {
    super.deregister();
    int i = OS.GetDlgItem(this.handle, 1001);
    if (i != 0)
      this.display.removeControl(i);
    int j = OS.GetDlgItem(this.handle, 1000);
    if (j != 0)
      this.display.removeControl(j);
  }

  public void deselect(int paramInt)
  {
    checkWidget();
    int i = OS.SendMessage(this.handle, 327, 0, 0);
    if (paramInt != i)
      return;
    OS.SendMessage(this.handle, 334, -1, 0);
    sendEvent(24);
  }

  public void deselectAll()
  {
    checkWidget();
    OS.SendMessage(this.handle, 334, -1, 0);
    sendEvent(24);
  }

  boolean dragDetect(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean, boolean[] paramArrayOfBoolean1, boolean[] paramArrayOfBoolean2)
  {
    if ((paramBoolean) && ((this.style & 0x8) == 0))
    {
      int i = OS.GetDlgItem(this.handle, 1001);
      if (i != 0)
      {
        int[] arrayOfInt1 = new int[1];
        int[] arrayOfInt2 = new int[1];
        OS.SendMessage(this.handle, 320, arrayOfInt1, arrayOfInt2);
        if (arrayOfInt1[0] != arrayOfInt2[0])
        {
          int j = OS.MAKELPARAM(paramInt2, paramInt3);
          int k = OS.LOWORD(OS.SendMessage(i, 215, 0, j));
          if ((arrayOfInt1[0] <= k) && (k < arrayOfInt2[0]) && (super.dragDetect(paramInt1, paramInt2, paramInt3, paramBoolean, paramArrayOfBoolean1, paramArrayOfBoolean2)))
          {
            if (paramArrayOfBoolean2 != null)
              paramArrayOfBoolean2[0] = true;
            return true;
          }
        }
        return false;
      }
    }
    return super.dragDetect(paramInt1, paramInt2, paramInt3, paramBoolean, paramArrayOfBoolean1, paramArrayOfBoolean2);
  }

  public String getItem(int paramInt)
  {
    checkWidget();
    int i = OS.SendMessage(this.handle, 329, paramInt, 0);
    if (i != -1)
    {
      TCHAR localTCHAR = new TCHAR(getCodePage(), i + 1);
      int k = OS.SendMessage(this.handle, 328, paramInt, localTCHAR);
      if (k != -1)
        return localTCHAR.toString(0, i);
    }
    int j = OS.SendMessage(this.handle, 326, 0, 0);
    if ((paramInt >= 0) && (paramInt < j))
      error(8);
    error(6);
    return "";
  }

  public int getItemCount()
  {
    checkWidget();
    int i = OS.SendMessage(this.handle, 326, 0, 0);
    if (i == -1)
      error(36);
    return i;
  }

  public int getItemHeight()
  {
    checkWidget();
    int i = OS.SendMessage(this.handle, 340, 0, 0);
    if (i == -1)
      error(11);
    return i;
  }

  public String[] getItems()
  {
    checkWidget();
    int i = getItemCount();
    String[] arrayOfString = new String[i];
    for (int j = 0; j < i; j++)
      arrayOfString[j] = getItem(j);
    return arrayOfString;
  }

  public boolean getListVisible()
  {
    checkWidget();
    if ((this.style & 0x4) != 0)
      return OS.SendMessage(this.handle, 343, 0, 0) != 0;
    return true;
  }

  String getNameText()
  {
    return getText();
  }

  public void setListVisible(boolean paramBoolean)
  {
    checkWidget();
    OS.SendMessage(this.handle, 335, paramBoolean ? 1 : 0, 0);
  }

  public int getOrientation()
  {
    checkWidget();
    return this.style & 0x6000000;
  }

  public Point getSelection()
  {
    checkWidget();
    if (((this.style & 0x4) != 0) && ((this.style & 0x8) != 0))
      return new Point(0, OS.GetWindowTextLength(this.handle));
    int[] arrayOfInt1 = new int[1];
    int[] arrayOfInt2 = new int[1];
    OS.SendMessage(this.handle, 320, arrayOfInt1, arrayOfInt2);
    if ((!OS.IsUnicode) && (OS.IsDBLocale))
    {
      arrayOfInt1[0] = mbcsToWcsPos(arrayOfInt1[0]);
      arrayOfInt2[0] = mbcsToWcsPos(arrayOfInt2[0]);
    }
    return new Point(arrayOfInt1[0], arrayOfInt2[0]);
  }

  public int getSelectionIndex()
  {
    checkWidget();
    if (this.noSelection)
      return -1;
    return OS.SendMessage(this.handle, 327, 0, 0);
  }

  public String getText()
  {
    checkWidget();
    int i = OS.GetWindowTextLength(this.handle);
    if (i == 0)
      return "";
    TCHAR localTCHAR = new TCHAR(getCodePage(), i + 1);
    OS.GetWindowText(this.handle, localTCHAR, i + 1);
    return localTCHAR.toString(0, i);
  }

  public int getTextHeight()
  {
    checkWidget();
    COMBOBOXINFO localCOMBOBOXINFO = new COMBOBOXINFO();
    localCOMBOBOXINFO.cbSize = COMBOBOXINFO.sizeof;
    if (((this.style & 0x40) == 0) && (!OS.IsWinCE) && (OS.GetComboBoxInfo(this.handle, localCOMBOBOXINFO)))
      return localCOMBOBOXINFO.buttonBottom - localCOMBOBOXINFO.buttonTop + localCOMBOBOXINFO.buttonTop * 2;
    int i = OS.SendMessage(this.handle, 340, -1, 0);
    if (i == -1)
      error(11);
    return (this.style & 0x4) != 0 ? i + 6 : i + 10;
  }

  public int getTextLimit()
  {
    checkWidget();
    int i = OS.GetDlgItem(this.handle, 1001);
    if (i == 0)
      return LIMIT;
    return OS.SendMessage(i, 213, 0, 0) & 0x7FFFFFFF;
  }

  public int getVisibleItemCount()
  {
    checkWidget();
    return this.visibleCount;
  }

  boolean hasFocus()
  {
    int i = OS.GetFocus();
    if (i == this.handle)
      return true;
    if (i == 0)
      return false;
    int j = OS.GetDlgItem(this.handle, 1001);
    if (i == j)
      return true;
    int k = OS.GetDlgItem(this.handle, 1000);
    return i == k;
  }

  public int indexOf(String paramString)
  {
    return indexOf(paramString, 0);
  }

  public int indexOf(String paramString, int paramInt)
  {
    checkWidget();
    if (paramString == null)
      error(4);
    if (paramString.length() == 0)
    {
      i = getItemCount();
      for (j = paramInt; j < i; j++)
        if (paramString.equals(getItem(j)))
          return j;
      return -1;
    }
    int i = OS.SendMessage(this.handle, 326, 0, 0);
    if ((paramInt < 0) || (paramInt >= i))
      return -1;
    int j = paramInt - 1;
    int k = 0;
    TCHAR localTCHAR = new TCHAR(getCodePage(), paramString, true);
    do
    {
      j = OS.SendMessage(this.handle, 344, k = j, localTCHAR);
      if ((j == -1) || (j <= k))
        return -1;
    }
    while (!paramString.equals(getItem(j)));
    return j;
  }

  int mbcsToWcsPos(int paramInt)
  {
    if (paramInt <= 0)
      return 0;
    if (OS.IsUnicode)
      return paramInt;
    int i = OS.GetDlgItem(this.handle, 1001);
    if (i == 0)
      return paramInt;
    int j = OS.GetWindowTextLengthA(i);
    if (j == 0)
      return 0;
    if (paramInt >= j)
      return j;
    byte[] arrayOfByte = new byte[j + 1];
    OS.GetWindowTextA(i, arrayOfByte, j + 1);
    return OS.MultiByteToWideChar(getCodePage(), 1, arrayOfByte, paramInt, null, 0);
  }

  public void paste()
  {
    checkWidget();
    if ((this.style & 0x8) != 0)
      return;
    OS.SendMessage(this.handle, 770, 0, 0);
  }

  void register()
  {
    super.register();
    int i = OS.GetDlgItem(this.handle, 1001);
    if (i != 0)
      this.display.addControl(i, this);
    int j = OS.GetDlgItem(this.handle, 1000);
    if (j != 0)
      this.display.addControl(j, this);
  }

  public void remove(int paramInt)
  {
    checkWidget();
    remove(paramInt, true);
  }

  void remove(int paramInt, boolean paramBoolean)
  {
    TCHAR localTCHAR = null;
    int k;
    if ((this.style & 0x100) != 0)
    {
      i = OS.SendMessage(this.handle, 329, paramInt, 0);
      if (i == -1)
      {
        j = OS.SendMessage(this.handle, 326, 0, 0);
        if ((paramInt >= 0) && (paramInt < j))
          error(15);
        error(6);
      }
      localTCHAR = new TCHAR(getCodePage(), i + 1);
      j = OS.SendMessage(this.handle, 328, paramInt, localTCHAR);
      if (j == -1)
      {
        k = OS.SendMessage(this.handle, 326, 0, 0);
        if ((paramInt >= 0) && (paramInt < k))
          error(15);
        error(6);
      }
    }
    int i = OS.GetWindowTextLength(this.handle);
    int j = OS.SendMessage(this.handle, 324, paramInt, 0);
    if (j == -1)
    {
      k = OS.SendMessage(this.handle, 326, 0, 0);
      if ((paramInt >= 0) && (paramInt < k))
        error(15);
      error(6);
    }
    if ((this.style & 0x100) != 0)
      setScrollWidth(localTCHAR, true);
    if ((paramBoolean) && (i != OS.GetWindowTextLength(this.handle)))
    {
      sendEvent(24);
      if (isDisposed())
        return;
    }
    if ((this.style & 0x8) != 0)
    {
      k = OS.SendMessage(this.handle, 326, 0, 0);
      if (k == 0)
        OS.InvalidateRect(this.handle, null, true);
    }
  }

  public void remove(int paramInt1, int paramInt2)
  {
    checkWidget();
    if (paramInt1 > paramInt2)
      return;
    int i = OS.SendMessage(this.handle, 326, 0, 0);
    if ((paramInt1 < 0) || (paramInt1 > paramInt2) || (paramInt2 >= i))
      error(6);
    int j = OS.GetWindowTextLength(this.handle);
    RECT localRECT = null;
    int k = 0;
    int m = 0;
    int n = 0;
    int i1 = 0;
    if ((this.style & 0x100) != 0)
    {
      localRECT = new RECT();
      k = OS.GetDC(this.handle);
      n = OS.SendMessage(this.handle, 49, 0, 0);
      if (n != 0)
        m = OS.SelectObject(k, n);
    }
    int i2 = getCodePage();
    int i3 = 3104;
    for (int i4 = paramInt1; i4 <= paramInt2; i4++)
    {
      TCHAR localTCHAR = null;
      if ((this.style & 0x100) != 0)
      {
        i5 = OS.SendMessage(this.handle, 329, paramInt1, 0);
        if (i5 == -1)
          break;
        localTCHAR = new TCHAR(i2, i5 + 1);
        int i6 = OS.SendMessage(this.handle, 328, paramInt1, localTCHAR);
        if (i6 == -1)
          break;
      }
      int i5 = OS.SendMessage(this.handle, 324, paramInt1, 0);
      if (i5 == -1)
        error(15);
      if ((this.style & 0x100) != 0)
      {
        OS.DrawText(k, localTCHAR, -1, localRECT, i3);
        i1 = Math.max(i1, localRECT.right - localRECT.left);
      }
    }
    if ((this.style & 0x100) != 0)
    {
      if (n != 0)
        OS.SelectObject(k, m);
      OS.ReleaseDC(this.handle, k);
      setScrollWidth(i1, false);
    }
    if (j != OS.GetWindowTextLength(this.handle))
    {
      sendEvent(24);
      if (isDisposed())
        return;
    }
    if ((this.style & 0x8) != 0)
    {
      i = OS.SendMessage(this.handle, 326, 0, 0);
      if (i == 0)
        OS.InvalidateRect(this.handle, null, true);
    }
  }

  public void remove(String paramString)
  {
    checkWidget();
    if (paramString == null)
      error(4);
    int i = indexOf(paramString, 0);
    if (i == -1)
      error(5);
    remove(i);
  }

  public void removeAll()
  {
    checkWidget();
    OS.SendMessage(this.handle, 331, 0, 0);
    sendEvent(24);
    if (isDisposed())
      return;
    if ((this.style & 0x100) != 0)
      setScrollWidth(0);
  }

  public void removeModifyListener(ModifyListener paramModifyListener)
  {
    checkWidget();
    if (paramModifyListener == null)
      error(4);
    if (this.eventTable == null)
      return;
    this.eventTable.unhook(24, paramModifyListener);
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

  public void removeVerifyListener(VerifyListener paramVerifyListener)
  {
    checkWidget();
    if (paramVerifyListener == null)
      error(4);
    if (this.eventTable == null)
      return;
    this.eventTable.unhook(25, paramVerifyListener);
  }

  boolean sendKeyEvent(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Event paramEvent)
  {
    if (!super.sendKeyEvent(paramInt1, paramInt2, paramInt3, paramInt4, paramEvent))
      return false;
    if ((this.style & 0x8) != 0)
      return true;
    if (paramInt1 != 1)
      return true;
    if ((paramInt2 != 258) && (paramInt2 != 256) && (paramInt2 != 646))
      return true;
    if (paramEvent.character == 0)
      return true;
    if ((!hooks(25)) && (!filters(25)))
      return true;
    int i = paramEvent.character;
    int j = paramEvent.stateMask;
    switch (paramInt2)
    {
    case 258:
    case 256:
      if (((i == 8) || (i == 127) || (i == 13) || (i == 9) || (i == 10)) && ((j & 0x70000) != 0))
        return false;
      break;
    case 257:
    }
    if ((OS.GetKeyState(1) < 0) && (OS.GetDlgItem(this.handle, 1001) == OS.GetCapture()))
      return true;
    String str1 = "";
    int[] arrayOfInt1 = new int[1];
    int[] arrayOfInt2 = new int[1];
    int k = OS.GetDlgItem(this.handle, 1001);
    if (k == 0)
      return true;
    OS.SendMessage(k, 176, arrayOfInt1, arrayOfInt2);
    switch (i)
    {
    case 8:
      if (arrayOfInt1[0] == arrayOfInt2[0])
      {
        if (arrayOfInt1[0] == 0)
          return true;
        arrayOfInt1[0] -= 1;
        if ((!OS.IsUnicode) && (OS.IsDBLocale))
        {
          int[] arrayOfInt3 = new int[1];
          localObject = new int[1];
          OS.SendMessage(k, 177, arrayOfInt1[0], arrayOfInt2[0]);
          OS.SendMessage(k, 176, arrayOfInt3, (int[])localObject);
          if (arrayOfInt1[0] != arrayOfInt3[0])
            arrayOfInt1[0] -= 1;
        }
        arrayOfInt1[0] = Math.max(arrayOfInt1[0], 0);
      }
      break;
    case 127:
      if (arrayOfInt1[0] == arrayOfInt2[0])
      {
        int m = OS.GetWindowTextLength(k);
        if (arrayOfInt1[0] == m)
          return true;
        arrayOfInt2[0] += 1;
        if ((!OS.IsUnicode) && (OS.IsDBLocale))
        {
          localObject = new int[1];
          int[] arrayOfInt4 = new int[1];
          OS.SendMessage(k, 177, arrayOfInt1[0], arrayOfInt2[0]);
          OS.SendMessage(k, 176, (int[])localObject, arrayOfInt4);
          if (arrayOfInt2[0] != arrayOfInt4[0])
            arrayOfInt2[0] += 1;
        }
        arrayOfInt2[0] = Math.min(arrayOfInt2[0], m);
      }
      break;
    case 13:
      return true;
    default:
      if ((i != 9) && (i < 32))
        return true;
      str1 = new String(new char[] { i });
    }
    String str2 = verifyText(str1, arrayOfInt1[0], arrayOfInt2[0], paramEvent);
    if (str2 == null)
      return false;
    if (str2 == str1)
      return true;
    Object localObject = new TCHAR(getCodePage(), str2, true);
    OS.SendMessage(k, 177, arrayOfInt1[0], arrayOfInt2[0]);
    OS.SendMessage(k, 194, 0, (TCHAR)localObject);
    return false;
  }

  public void select(int paramInt)
  {
    checkWidget();
    int i = OS.SendMessage(this.handle, 326, 0, 0);
    if ((paramInt >= 0) && (paramInt < i))
    {
      int j = OS.SendMessage(this.handle, 327, 0, 0);
      int k = OS.SendMessage(this.handle, 334, paramInt, 0);
      if ((k != -1) && (k != j))
        sendEvent(24);
    }
  }

  void setBackgroundImage(int paramInt)
  {
    super.setBackgroundImage(paramInt);
    int i = OS.GetDlgItem(this.handle, 1001);
    if (i != 0)
      OS.InvalidateRect(i, null, true);
    int j = OS.GetDlgItem(this.handle, 1000);
    if (j != 0)
      OS.InvalidateRect(j, null, true);
  }

  void setBackgroundPixel(int paramInt)
  {
    super.setBackgroundPixel(paramInt);
    int i = OS.GetDlgItem(this.handle, 1001);
    if (i != 0)
      OS.InvalidateRect(i, null, true);
    int j = OS.GetDlgItem(this.handle, 1000);
    if (j != 0)
      OS.InvalidateRect(j, null, true);
  }

  void setBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    if ((this.style & 0x4) != 0)
    {
      int i = getItemCount() == 0 ? 5 : this.visibleCount;
      paramInt4 = getTextHeight() + getItemHeight() * i + 2;
      RECT localRECT = new RECT();
      OS.GetWindowRect(this.handle, localRECT);
      if ((localRECT.right - localRECT.left != 0) && (OS.SendMessage(this.handle, 338, 0, localRECT) != 0))
      {
        int j = localRECT.right - localRECT.left;
        int k = localRECT.bottom - localRECT.top;
        if ((j == paramInt3) && (k == paramInt4))
          paramInt5 |= 1;
      }
      SetWindowPos(this.handle, 0, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    }
    else
    {
      super.setBounds(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    }
  }

  public void setFont(Font paramFont)
  {
    checkWidget();
    super.setFont(paramFont);
    if ((this.style & 0x100) != 0)
      setScrollWidth();
  }

  void setForegroundPixel(int paramInt)
  {
    super.setForegroundPixel(paramInt);
    int i = OS.GetDlgItem(this.handle, 1001);
    if (i != 0)
      OS.InvalidateRect(i, null, true);
    int j = OS.GetDlgItem(this.handle, 1000);
    if (j != 0)
      OS.InvalidateRect(j, null, true);
  }

  public void setItem(int paramInt, String paramString)
  {
    checkWidget();
    if (paramString == null)
      error(4);
    int i = getSelectionIndex();
    remove(paramInt, false);
    if (isDisposed())
      return;
    add(paramString, paramInt);
    if (i != -1)
      select(i);
  }

  public void setItems(String[] paramArrayOfString)
  {
    checkWidget();
    if (paramArrayOfString == null)
      error(4);
    for (int i = 0; i < paramArrayOfString.length; i++)
      if (paramArrayOfString[i] == null)
        error(5);
    RECT localRECT = null;
    int j = 0;
    int k = 0;
    int m = 0;
    int n = 0;
    if ((this.style & 0x100) != 0)
    {
      localRECT = new RECT();
      j = OS.GetDC(this.handle);
      m = OS.SendMessage(this.handle, 49, 0, 0);
      if (m != 0)
        k = OS.SelectObject(j, m);
      setScrollWidth(0);
    }
    OS.SendMessage(this.handle, 331, 0, 0);
    int i1 = getCodePage();
    for (int i2 = 0; i2 < paramArrayOfString.length; i2++)
    {
      String str = paramArrayOfString[i2];
      TCHAR localTCHAR = new TCHAR(i1, str, true);
      int i3 = OS.SendMessage(this.handle, 323, 0, localTCHAR);
      if (i3 == -1)
        error(14);
      if (i3 == -2)
        error(14);
      if ((this.style & 0x100) != 0)
      {
        int i4 = 3104;
        OS.DrawText(j, localTCHAR, -1, localRECT, i4);
        n = Math.max(n, localRECT.right - localRECT.left);
      }
    }
    if ((this.style & 0x100) != 0)
    {
      if (m != 0)
        OS.SelectObject(j, k);
      OS.ReleaseDC(this.handle, j);
      setScrollWidth(n + 3);
    }
    sendEvent(24);
  }

  public void setOrientation(int paramInt)
  {
    checkWidget();
    if (OS.IsWinCE)
      return;
    if (OS.WIN32_VERSION < OS.VERSION(4, 10))
      return;
    int i = 100663296;
    if (((paramInt & i) == 0) || ((paramInt & i) == i))
      return;
    this.style &= (i ^ 0xFFFFFFFF);
    this.style |= paramInt & i;
    int j = OS.GetWindowLong(this.handle, -20);
    if ((this.style & 0x4000000) != 0)
    {
      this.style |= 134217728;
      j |= 4194304;
    }
    else
    {
      this.style &= -134217729;
      j &= -4194305;
    }
    OS.SetWindowLong(this.handle, -20, j);
    int k = 0;
    int m = 0;
    COMBOBOXINFO localCOMBOBOXINFO = new COMBOBOXINFO();
    localCOMBOBOXINFO.cbSize = COMBOBOXINFO.sizeof;
    if (OS.GetComboBoxInfo(this.handle, localCOMBOBOXINFO))
    {
      k = localCOMBOBOXINFO.hwndItem;
      m = localCOMBOBOXINFO.hwndList;
    }
    int n;
    if (k != 0)
    {
      n = OS.GetWindowLong(k, -20);
      int i1 = OS.GetWindowLong(k, -16);
      if ((this.style & 0x4000000) != 0)
      {
        n |= 12288;
        i1 |= 2;
      }
      else
      {
        n &= -12289;
        i1 &= -3;
      }
      OS.SetWindowLong(k, -20, n);
      OS.SetWindowLong(k, -16, i1);
      RECT localRECT = new RECT();
      OS.GetWindowRect(k, localRECT);
      int i2 = localRECT.right - localRECT.left;
      int i3 = localRECT.bottom - localRECT.top;
      OS.GetWindowRect(this.handle, localRECT);
      int i4 = localRECT.right - localRECT.left;
      int i5 = localRECT.bottom - localRECT.top;
      int i6 = 22;
      SetWindowPos(k, 0, 0, 0, i2 - 1, i3 - 1, i6);
      SetWindowPos(this.handle, 0, 0, 0, i4 - 1, i5 - 1, i6);
      SetWindowPos(k, 0, 0, 0, i2, i3, i6);
      SetWindowPos(this.handle, 0, 0, 0, i4, i5, i6);
      OS.InvalidateRect(this.handle, null, true);
    }
    if (m != 0)
    {
      n = OS.GetWindowLong(m, -20);
      if ((this.style & 0x4000000) != 0)
        n |= 4194304;
      else
        n &= -4194305;
      OS.SetWindowLong(m, -20, n);
    }
  }

  void setScrollWidth()
  {
    int i = 0;
    RECT localRECT = new RECT();
    int k = 0;
    int m = OS.GetDC(this.handle);
    int j = OS.SendMessage(this.handle, 49, 0, 0);
    if (j != 0)
      k = OS.SelectObject(m, j);
    int n = getCodePage();
    int i1 = OS.SendMessage(this.handle, 326, 0, 0);
    int i2 = 3104;
    for (int i3 = 0; i3 < i1; i3++)
    {
      int i4 = OS.SendMessage(this.handle, 329, i3, 0);
      if (i4 != -1)
      {
        TCHAR localTCHAR = new TCHAR(n, i4 + 1);
        int i5 = OS.SendMessage(this.handle, 328, i3, localTCHAR);
        if (i5 != -1)
        {
          OS.DrawText(m, localTCHAR, -1, localRECT, i2);
          i = Math.max(i, localRECT.right - localRECT.left);
        }
      }
    }
    if (j != 0)
      OS.SelectObject(m, k);
    OS.ReleaseDC(this.handle, m);
    setScrollWidth(i + 3);
  }

  void setScrollWidth(int paramInt)
  {
    this.scrollWidth = paramInt;
    if ((this.style & 0x40) != 0)
    {
      OS.SendMessage(this.handle, 350, paramInt, 0);
      return;
    }
    int i = 0;
    int j = OS.SendMessage(this.handle, 326, 0, 0);
    if (j > 3)
    {
      int k = 0;
      if ((OS.IsWinCE) || (OS.WIN32_VERSION < OS.VERSION(4, 10)))
      {
        RECT localRECT = new RECT();
        OS.SystemParametersInfo(48, 0, localRECT, 0);
        k = (localRECT.right - localRECT.left) / 4;
      }
      else
      {
        int m = OS.MonitorFromWindow(this.handle, 2);
        MONITORINFO localMONITORINFO = new MONITORINFO();
        localMONITORINFO.cbSize = MONITORINFO.sizeof;
        OS.GetMonitorInfo(m, localMONITORINFO);
        k = (localMONITORINFO.rcWork_right - localMONITORINFO.rcWork_left) / 4;
      }
      i = paramInt > k ? 1 : 0;
    }
    boolean bool = this.lockText;
    if ((this.style & 0x8) == 0)
      this.lockText = true;
    if (i != 0)
    {
      OS.SendMessage(this.handle, 352, 0, 0);
      OS.SendMessage(this.handle, 350, paramInt, 0);
    }
    else
    {
      paramInt += OS.GetSystemMetrics(3);
      OS.SendMessage(this.handle, 352, paramInt, 0);
      OS.SendMessage(this.handle, 350, 0, 0);
    }
    if ((this.style & 0x8) == 0)
      this.lockText = bool;
  }

  void setScrollWidth(TCHAR paramTCHAR, boolean paramBoolean)
  {
    RECT localRECT = new RECT();
    int j = 0;
    int k = OS.GetDC(this.handle);
    int i = OS.SendMessage(this.handle, 49, 0, 0);
    if (i != 0)
      j = OS.SelectObject(k, i);
    int m = 3104;
    OS.DrawText(k, paramTCHAR, -1, localRECT, m);
    if (i != 0)
      OS.SelectObject(k, j);
    OS.ReleaseDC(this.handle, k);
    setScrollWidth(localRECT.right - localRECT.left, paramBoolean);
  }

  void setScrollWidth(int paramInt, boolean paramBoolean)
  {
    if (paramBoolean)
    {
      if (paramInt <= this.scrollWidth)
        return;
      setScrollWidth(paramInt + 3);
    }
    else
    {
      if (paramInt < this.scrollWidth)
        return;
      setScrollWidth();
    }
  }

  public void setSelection(Point paramPoint)
  {
    checkWidget();
    if (paramPoint == null)
      error(4);
    int i = paramPoint.x;
    int j = paramPoint.y;
    if ((!OS.IsUnicode) && (OS.IsDBLocale))
    {
      i = wcsToMbcsPos(i);
      j = wcsToMbcsPos(j);
    }
    int k = OS.MAKELPARAM(i, j);
    OS.SendMessage(this.handle, 322, 0, k);
  }

  public void setText(String paramString)
  {
    checkWidget();
    if (paramString == null)
      error(4);
    if ((this.style & 0x8) != 0)
    {
      i = indexOf(paramString);
      if (i != -1)
        select(i);
      return;
    }
    int i = LIMIT;
    int j = OS.GetDlgItem(this.handle, 1001);
    if (j != 0)
      i = OS.SendMessage(j, 213, 0, 0) & 0x7FFFFFFF;
    if (paramString.length() > i)
      paramString = paramString.substring(0, i);
    TCHAR localTCHAR = new TCHAR(getCodePage(), paramString, true);
    if (OS.SetWindowText(this.handle, localTCHAR))
      sendEvent(24);
  }

  public void setTextLimit(int paramInt)
  {
    checkWidget();
    if (paramInt == 0)
      error(7);
    OS.SendMessage(this.handle, 321, paramInt, 0);
  }

  void setToolTipText(Shell paramShell, String paramString)
  {
    int i = OS.GetDlgItem(this.handle, 1001);
    int j = OS.GetDlgItem(this.handle, 1000);
    if (i != 0)
      paramShell.setToolTipText(i, paramString);
    if (j != 0)
      paramShell.setToolTipText(j, paramString);
    paramShell.setToolTipText(this.handle, paramString);
  }

  public void setVisibleItemCount(int paramInt)
  {
    checkWidget();
    if (paramInt < 0)
      return;
    this.visibleCount = paramInt;
    updateDropDownHeight();
  }

  void subclass()
  {
    super.subclass();
    int i = this.display.windowProc;
    int j = OS.GetDlgItem(this.handle, 1001);
    if (j != 0)
      OS.SetWindowLongPtr(j, -4, i);
    int k = OS.GetDlgItem(this.handle, 1000);
    if (k != 0)
      OS.SetWindowLongPtr(k, -4, i);
  }

  boolean translateTraversal(MSG paramMSG)
  {
    switch (paramMSG.wParam)
    {
    case 13:
    case 27:
      if (((this.style & 0x4) != 0) && (OS.SendMessage(this.handle, 343, 0, 0) != 0))
        return false;
      break;
    }
    return super.translateTraversal(paramMSG);
  }

  boolean traverseEscape()
  {
    if (((this.style & 0x4) != 0) && (OS.SendMessage(this.handle, 343, 0, 0) != 0))
    {
      OS.SendMessage(this.handle, 335, 0, 0);
      return true;
    }
    return super.traverseEscape();
  }

  boolean traverseReturn()
  {
    if (((this.style & 0x4) != 0) && (OS.SendMessage(this.handle, 343, 0, 0) != 0))
    {
      OS.SendMessage(this.handle, 335, 0, 0);
      return true;
    }
    return super.traverseReturn();
  }

  void unsubclass()
  {
    super.unsubclass();
    int i = OS.GetDlgItem(this.handle, 1001);
    if ((i != 0) && (EditProc != 0))
      OS.SetWindowLongPtr(i, -4, EditProc);
    int j = OS.GetDlgItem(this.handle, 1000);
    if ((j != 0) && (ListProc != 0))
      OS.SetWindowLongPtr(j, -4, ListProc);
  }

  void updateDropDownHeight()
  {
    if ((this.style & 0x4) != 0)
    {
      RECT localRECT = new RECT();
      OS.SendMessage(this.handle, 338, 0, localRECT);
      int i = getItemCount() == 0 ? 5 : this.visibleCount;
      int j = getTextHeight() + getItemHeight() * i + 2;
      if (j != localRECT.bottom - localRECT.top)
      {
        forceResize();
        OS.GetWindowRect(this.handle, localRECT);
        int k = 54;
        SetWindowPos(this.handle, 0, 0, 0, localRECT.right - localRECT.left, j, k);
      }
    }
  }

  String verifyText(String paramString, int paramInt1, int paramInt2, Event paramEvent)
  {
    Event localEvent = new Event();
    localEvent.text = paramString;
    localEvent.start = paramInt1;
    localEvent.end = paramInt2;
    if (paramEvent != null)
    {
      localEvent.character = paramEvent.character;
      localEvent.keyCode = paramEvent.keyCode;
      localEvent.stateMask = paramEvent.stateMask;
    }
    if ((!OS.IsUnicode) && (OS.IsDBLocale))
    {
      localEvent.start = mbcsToWcsPos(paramInt1);
      localEvent.end = mbcsToWcsPos(paramInt2);
    }
    sendEvent(25, localEvent);
    if ((!localEvent.doit) || (isDisposed()))
      return null;
    return localEvent.text;
  }

  int wcsToMbcsPos(int paramInt)
  {
    if (paramInt <= 0)
      return 0;
    if (OS.IsUnicode)
      return paramInt;
    int i = OS.GetDlgItem(this.handle, 1001);
    if (i == 0)
      return paramInt;
    int j = OS.GetWindowTextLengthA(i);
    if (j == 0)
      return 0;
    byte[] arrayOfByte = new byte[j + 1];
    OS.GetWindowTextA(i, arrayOfByte, j + 1);
    int k = 0;
    for (int m = 0; k < j; m++)
    {
      if (paramInt == m)
        break;
      if (OS.IsDBCSLeadByte(arrayOfByte[(k++)]))
        k++;
    }
    return k;
  }

  int widgetExtStyle()
  {
    return super.widgetExtStyle() & 0xFFEFFFFF;
  }

  int widgetStyle()
  {
    int i = super.widgetStyle() | 0x40 | 0x400 | 0x100000 | 0x200000;
    if ((this.style & 0x40) != 0)
      return i | 0x1;
    if ((this.style & 0x8) != 0)
      return i | 0x3;
    return i | 0x2;
  }

  TCHAR windowClass()
  {
    return ComboClass;
  }

  int windowProc()
  {
    return ComboProc;
  }

  int windowProc(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.handle == 0)
      return 0;
    if (paramInt1 != this.handle)
    {
      int i = OS.GetDlgItem(this.handle, 1001);
      int j = OS.GetDlgItem(this.handle, 1000);
      if (((i != 0) && (paramInt1 == i)) || ((j != 0) && (paramInt1 == j)))
      {
        LRESULT localLRESULT = null;
        switch (paramInt2)
        {
        case 258:
          localLRESULT = wmChar(paramInt1, paramInt3, paramInt4);
          break;
        case 646:
          localLRESULT = wmIMEChar(paramInt1, paramInt3, paramInt4);
          break;
        case 256:
          localLRESULT = wmKeyDown(paramInt1, paramInt3, paramInt4);
          break;
        case 257:
          localLRESULT = wmKeyUp(paramInt1, paramInt3, paramInt4);
          break;
        case 262:
          localLRESULT = wmSysChar(paramInt1, paramInt3, paramInt4);
          break;
        case 260:
          localLRESULT = wmSysKeyDown(paramInt1, paramInt3, paramInt4);
          break;
        case 261:
          localLRESULT = wmSysKeyUp(paramInt1, paramInt3, paramInt4);
          break;
        case 533:
          localLRESULT = wmCaptureChanged(paramInt1, paramInt3, paramInt4);
          break;
        case 515:
          localLRESULT = wmLButtonDblClk(paramInt1, paramInt3, paramInt4);
          break;
        case 513:
          localLRESULT = wmLButtonDown(paramInt1, paramInt3, paramInt4);
          break;
        case 514:
          localLRESULT = wmLButtonUp(paramInt1, paramInt3, paramInt4);
          break;
        case 521:
          localLRESULT = wmMButtonDblClk(paramInt1, paramInt3, paramInt4);
          break;
        case 519:
          localLRESULT = wmMButtonDown(paramInt1, paramInt3, paramInt4);
          break;
        case 520:
          localLRESULT = wmMButtonUp(paramInt1, paramInt3, paramInt4);
          break;
        case 673:
          localLRESULT = wmMouseHover(paramInt1, paramInt3, paramInt4);
          break;
        case 675:
          localLRESULT = wmMouseLeave(paramInt1, paramInt3, paramInt4);
          break;
        case 512:
          localLRESULT = wmMouseMove(paramInt1, paramInt3, paramInt4);
          break;
        case 518:
          localLRESULT = wmRButtonDblClk(paramInt1, paramInt3, paramInt4);
          break;
        case 516:
          localLRESULT = wmRButtonDown(paramInt1, paramInt3, paramInt4);
          break;
        case 517:
          localLRESULT = wmRButtonUp(paramInt1, paramInt3, paramInt4);
          break;
        case 525:
          localLRESULT = wmXButtonDblClk(paramInt1, paramInt3, paramInt4);
          break;
        case 523:
          localLRESULT = wmXButtonDown(paramInt1, paramInt3, paramInt4);
          break;
        case 524:
          localLRESULT = wmXButtonUp(paramInt1, paramInt3, paramInt4);
          break;
        case 15:
          localLRESULT = wmPaint(paramInt1, paramInt3, paramInt4);
          break;
        case 123:
          localLRESULT = wmContextMenu(paramInt1, paramInt3, paramInt4);
          break;
        case 12:
        case 199:
        case 768:
        case 770:
        case 771:
        case 772:
          if (paramInt1 == i)
            localLRESULT = wmClipboard(paramInt1, paramInt2, paramInt3, paramInt4);
          break;
        }
        if (localLRESULT != null)
          return localLRESULT.value;
        return callWindowProc(paramInt1, paramInt2, paramInt3, paramInt4);
      }
    }
    if ((paramInt2 == 334) && ((this.style & 0x8) != 0) && ((hooks(25)) || (filters(25))))
    {
      Object localObject = getText();
      String str = null;
      if (paramInt3 == -1)
        str = "";
      else if ((paramInt3 >= 0) && (paramInt3 < getItemCount()))
        str = getItem(paramInt3);
      if ((str != null) && (!str.equals(localObject)))
      {
        int k = OS.GetWindowTextLength(this.handle);
        localObject = str;
        str = verifyText(str, 0, k, null);
        if (str == null)
          return 0;
        if (!str.equals(localObject))
        {
          int m = indexOf(str);
          if ((m != -1) && (m != paramInt3))
            return callWindowProc(this.handle, 334, m, paramInt4);
        }
      }
    }
    return super.windowProc(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  LRESULT WM_CTLCOLOR(int paramInt1, int paramInt2)
  {
    return wmColorChild(paramInt1, paramInt2);
  }

  LRESULT WM_GETDLGCODE(int paramInt1, int paramInt2)
  {
    int i = callWindowProc(this.handle, 135, paramInt1, paramInt2);
    return new LRESULT(i | 0x1);
  }

  LRESULT WM_KILLFOCUS(int paramInt1, int paramInt2)
  {
    if ((this.style & 0x8) != 0)
      return super.WM_KILLFOCUS(paramInt1, paramInt2);
    return null;
  }

  LRESULT WM_LBUTTONDOWN(int paramInt1, int paramInt2)
  {
    int i = OS.SendMessage(this.handle, 327, 0, 0);
    LRESULT localLRESULT = super.WM_LBUTTONDOWN(paramInt1, paramInt2);
    if (localLRESULT == LRESULT.ZERO)
      return localLRESULT;
    if ((this.style & 0x8) == 0)
    {
      int j = OS.SendMessage(this.handle, 327, 0, 0);
      if (i != j)
      {
        sendEvent(24);
        if (isDisposed())
          return LRESULT.ZERO;
        sendSelectionEvent(13, null, true);
        if (isDisposed())
          return LRESULT.ZERO;
      }
    }
    return localLRESULT;
  }

  LRESULT WM_SETFOCUS(int paramInt1, int paramInt2)
  {
    return null;
  }

  LRESULT WM_SIZE(int paramInt1, int paramInt2)
  {
    if (this.ignoreResize)
      return null;
    if ((this.style & 0x40) != 0)
    {
      LRESULT localLRESULT1 = super.WM_SIZE(paramInt1, paramInt2);
      if (OS.IsWindowVisible(this.handle))
      {
        int i;
        if (OS.IsWinCE)
        {
          i = OS.GetDlgItem(this.handle, 1001);
          if (i != 0)
            OS.InvalidateRect(i, null, true);
          int j = OS.GetDlgItem(this.handle, 1000);
          if (j != 0)
            OS.InvalidateRect(j, null, true);
        }
        else
        {
          i = 133;
          OS.RedrawWindow(this.handle, null, 0, i);
        }
      }
      return localLRESULT1;
    }
    boolean bool = this.lockText;
    if ((this.style & 0x8) == 0)
      this.lockText = true;
    LRESULT localLRESULT2 = super.WM_SIZE(paramInt1, paramInt2);
    if ((this.style & 0x8) == 0)
      this.lockText = bool;
    if ((this.style & 0x100) != 0)
      setScrollWidth(this.scrollWidth);
    return localLRESULT2;
  }

  LRESULT WM_WINDOWPOSCHANGING(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_WINDOWPOSCHANGING(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    if (OS.IsWinCE)
      return localLRESULT;
    if (!getDrawing())
      return localLRESULT;
    if (!OS.IsWindowVisible(this.handle))
      return localLRESULT;
    if (this.ignoreResize)
    {
      WINDOWPOS localWINDOWPOS = new WINDOWPOS();
      OS.MoveMemory(localWINDOWPOS, paramInt2, WINDOWPOS.sizeof);
      if ((localWINDOWPOS.flags & 0x1) == 0)
      {
        localWINDOWPOS.flags |= 8;
        OS.MoveMemory(paramInt2, localWINDOWPOS, WINDOWPOS.sizeof);
        OS.InvalidateRect(this.handle, null, true);
        RECT localRECT = new RECT();
        OS.GetWindowRect(this.handle, localRECT);
        int i = localRECT.right - localRECT.left;
        int j = localRECT.bottom - localRECT.top;
        if ((i != 0) && (j != 0))
        {
          int k = this.parent.handle;
          int m = OS.GetWindow(k, 5);
          OS.MapWindowPoints(0, k, localRECT, 2);
          int n = OS.CreateRectRgn(localRECT.left, localRECT.top, localRECT.right, localRECT.bottom);
          while (m != 0)
          {
            if (m != this.handle)
            {
              OS.GetWindowRect(m, localRECT);
              OS.MapWindowPoints(0, k, localRECT, 2);
              i1 = OS.CreateRectRgn(localRECT.left, localRECT.top, localRECT.right, localRECT.bottom);
              OS.CombineRgn(n, n, i1, 4);
              OS.DeleteObject(i1);
            }
            m = OS.GetWindow(m, 2);
          }
          int i1 = 1029;
          OS.RedrawWindow(k, null, n, i1);
          OS.DeleteObject(n);
        }
      }
    }
    return localLRESULT;
  }

  LRESULT wmChar(int paramInt1, int paramInt2, int paramInt3)
  {
    if (this.ignoreCharacter)
      return null;
    LRESULT localLRESULT = super.wmChar(paramInt1, paramInt2, paramInt3);
    if (localLRESULT != null)
      return localLRESULT;
    switch (paramInt2)
    {
    case 9:
      return LRESULT.ZERO;
    case 13:
      if (!this.ignoreDefaultSelection)
        sendSelectionEvent(14);
      this.ignoreDefaultSelection = false;
    case 27:
      if (((this.style & 0x4) != 0) && (OS.SendMessage(this.handle, 343, 0, 0) == 0))
        return LRESULT.ZERO;
      break;
    }
    return localLRESULT;
  }

  LRESULT wmClipboard(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((this.style & 0x8) != 0)
      return null;
    if ((!hooks(25)) && (!filters(25)))
      return null;
    int i = 0;
    int[] arrayOfInt1 = new int[1];
    int[] arrayOfInt2 = new int[1];
    String str1 = null;
    int j;
    Object localObject;
    int k;
    switch (paramInt2)
    {
    case 768:
    case 771:
      OS.SendMessage(paramInt1, 176, arrayOfInt1, arrayOfInt2);
      if (arrayOfInt1[0] != arrayOfInt2[0])
      {
        str1 = "";
        i = 1;
      }
      break;
    case 770:
      OS.SendMessage(paramInt1, 176, arrayOfInt1, arrayOfInt2);
      str1 = getClipboardText();
      break;
    case 199:
    case 772:
      if (OS.SendMessage(paramInt1, 198, 0, 0) != 0)
      {
        this.ignoreModify = true;
        OS.CallWindowProc(EditProc, paramInt1, paramInt2, paramInt3, paramInt4);
        j = OS.GetWindowTextLength(paramInt1);
        localObject = new int[1];
        int[] arrayOfInt3 = new int[1];
        OS.SendMessage(paramInt1, 176, (int[])localObject, arrayOfInt3);
        if ((j != 0) && (localObject[0] != arrayOfInt3[0]))
        {
          TCHAR localTCHAR = new TCHAR(getCodePage(), j + 1);
          OS.GetWindowText(paramInt1, localTCHAR, j + 1);
          str1 = localTCHAR.toString(localObject[0], arrayOfInt3[0] - localObject[0]);
        }
        else
        {
          str1 = "";
        }
        OS.CallWindowProc(EditProc, paramInt1, paramInt2, paramInt3, paramInt4);
        OS.SendMessage(paramInt1, 176, arrayOfInt1, arrayOfInt2);
        this.ignoreModify = false;
      }
      break;
    case 12:
      arrayOfInt2[0] = OS.GetWindowTextLength(paramInt1);
      j = OS.IsUnicode ? OS.wcslen(paramInt4) : OS.strlen(paramInt4);
      localObject = new TCHAR(getCodePage(), j);
      k = ((TCHAR)localObject).length() * TCHAR.sizeof;
      OS.MoveMemory((TCHAR)localObject, paramInt4, k);
      str1 = ((TCHAR)localObject).toString(0, j);
    }
    if (str1 != null)
    {
      String str2 = str1;
      str1 = verifyText(str1, arrayOfInt1[0], arrayOfInt2[0], null);
      if (str1 == null)
        return LRESULT.ZERO;
      if (!str1.equals(str2))
      {
        if (i != 0)
          OS.CallWindowProc(EditProc, paramInt1, paramInt2, paramInt3, paramInt4);
        localObject = new TCHAR(getCodePage(), str1, true);
        if (paramInt2 == 12)
        {
          k = OS.GetProcessHeap();
          int m = ((TCHAR)localObject).length() * TCHAR.sizeof;
          int n = OS.HeapAlloc(k, 8, m);
          OS.MoveMemory(n, (TCHAR)localObject, m);
          int i1 = OS.CallWindowProc(EditProc, paramInt1, paramInt2, paramInt3, n);
          OS.HeapFree(k, 0, n);
          return new LRESULT(i1);
        }
        OS.SendMessage(paramInt1, 194, 0, (TCHAR)localObject);
        return LRESULT.ZERO;
      }
    }
    return null;
  }

  LRESULT wmCommandChild(int paramInt1, int paramInt2)
  {
    int i = OS.HIWORD(paramInt1);
    switch (i)
    {
    case 5:
      if (!this.ignoreModify)
      {
        this.noSelection = true;
        sendEvent(24);
        if (isDisposed())
          return LRESULT.ZERO;
        this.noSelection = false;
      }
      break;
    case 1:
      int j = OS.SendMessage(this.handle, 327, 0, 0);
      if (j != -1)
        OS.SendMessage(this.handle, 334, j, 0);
      sendEvent(24);
      if (isDisposed())
        return LRESULT.ZERO;
      sendSelectionEvent(13);
      break;
    case 3:
      sendFocusEvent(15);
      if (isDisposed())
        return LRESULT.ZERO;
      break;
    case 7:
      setCursor();
      updateDropDownHeight();
      break;
    case 4:
      if ((this.style & 0x8) == 0)
      {
        sendFocusEvent(16);
        if (isDisposed())
          return LRESULT.ZERO;
      }
      break;
    case 1792:
    case 1793:
      Event localEvent = new Event();
      localEvent.doit = true;
      sendEvent(44, localEvent);
      if (!localEvent.doit)
      {
        int k = paramInt2;
        int m = OS.GetWindowLong(k, -20);
        int n = OS.GetWindowLong(k, -16);
        if (i == 1792)
        {
          m |= 12288;
          n |= 2;
        }
        else
        {
          m &= -12289;
          n &= -3;
        }
        OS.SetWindowLong(k, -20, m);
        OS.SetWindowLong(k, -16, n);
      }
      break;
    }
    return super.wmCommandChild(paramInt1, paramInt2);
  }

  LRESULT wmIMEChar(int paramInt1, int paramInt2, int paramInt3)
  {
    Display localDisplay = this.display;
    localDisplay.lastKey = 0;
    localDisplay.lastAscii = paramInt2;
    localDisplay.lastVirtual = (localDisplay.lastNull = localDisplay.lastDead = 0);
    if (!sendKeyEvent(1, 646, paramInt2, paramInt3))
      return LRESULT.ZERO;
    this.ignoreCharacter = true;
    int i = callWindowProc(paramInt1, 646, paramInt2, paramInt3);
    MSG localMSG = new MSG();
    int j = 10420227;
    while (OS.PeekMessage(localMSG, paramInt1, 258, 258, j))
    {
      OS.TranslateMessage(localMSG);
      OS.DispatchMessage(localMSG);
    }
    this.ignoreCharacter = false;
    sendKeyEvent(2, 646, paramInt2, paramInt3);
    localDisplay.lastKey = (localDisplay.lastAscii = 0);
    return new LRESULT(i);
  }

  LRESULT wmKeyDown(int paramInt1, int paramInt2, int paramInt3)
  {
    if (this.ignoreCharacter)
      return null;
    LRESULT localLRESULT = super.wmKeyDown(paramInt1, paramInt2, paramInt3);
    if (localLRESULT != null)
      return localLRESULT;
    this.ignoreDefaultSelection = false;
    if ((paramInt2 == 13) && ((this.style & 0x4) != 0) && (OS.SendMessage(this.handle, 343, 0, 0) != 0))
      this.ignoreDefaultSelection = true;
    return localLRESULT;
  }

  LRESULT wmSysKeyDown(int paramInt1, int paramInt2, int paramInt3)
  {
    int i = OS.SendMessage(this.handle, 327, 0, 0);
    LRESULT localLRESULT = super.wmSysKeyDown(paramInt1, paramInt2, paramInt3);
    if (localLRESULT != null)
      return localLRESULT;
    if (((this.style & 0x8) == 0) && (paramInt2 == 40))
    {
      int j = callWindowProc(paramInt1, 260, paramInt2, paramInt3);
      int k = OS.SendMessage(this.handle, 327, 0, 0);
      if (i != k)
      {
        sendEvent(24);
        if (isDisposed())
          return LRESULT.ZERO;
        sendSelectionEvent(13, null, true);
        if (isDisposed())
          return LRESULT.ZERO;
      }
      return new LRESULT(j);
    }
    return localLRESULT;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.Combo
 * JD-Core Version:    0.6.2
 */