package org.eclipse.swt.widgets;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.win32.INITCOMMONCONTROLSEX;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.MARGINS;
import org.eclipse.swt.internal.win32.NMCUSTOMDRAW;
import org.eclipse.swt.internal.win32.NMHDR;
import org.eclipse.swt.internal.win32.NMREBARCHEVRON;
import org.eclipse.swt.internal.win32.NMREBARCHILDSIZE;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.internal.win32.REBARBANDINFO;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.WNDCLASS;

public class CoolBar extends Composite
{
  CoolItem[] items;
  CoolItem[] originalItems;
  boolean locked;
  boolean ignoreResize;
  static final int ReBarProc = localWNDCLASS.lpfnWndProc;
  static final TCHAR ReBarClass = new TCHAR(0, "ReBarWindow32", true);
  static final int SEPARATOR_WIDTH = 2;
  static final int MAX_WIDTH = 32767;
  static final int DEFAULT_COOLBAR_WIDTH = 0;
  static final int DEFAULT_COOLBAR_HEIGHT = 0;

  static
  {
    INITCOMMONCONTROLSEX localINITCOMMONCONTROLSEX = new INITCOMMONCONTROLSEX();
    localINITCOMMONCONTROLSEX.dwSize = INITCOMMONCONTROLSEX.sizeof;
    localINITCOMMONCONTROLSEX.dwICC = 1024;
    OS.InitCommonControlsEx(localINITCOMMONCONTROLSEX);
    WNDCLASS localWNDCLASS = new WNDCLASS();
    OS.GetClassInfo(0, ReBarClass, localWNDCLASS);
  }

  public CoolBar(Composite paramComposite, int paramInt)
  {
    super(paramComposite, checkStyle(paramInt));
    if ((paramInt & 0x200) != 0)
    {
      this.style |= 512;
      int i = OS.GetWindowLong(this.handle, -16);
      OS.SetWindowLong(this.handle, -16, i | 0x80);
    }
    else
    {
      this.style |= 256;
    }
  }

  int callWindowProc(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.handle == 0)
      return 0;
    return OS.CallWindowProc(ReBarProc, paramInt1, paramInt2, paramInt3, paramInt4);
  }

  static int checkStyle(int paramInt)
  {
    paramInt |= 524288;
    return paramInt & 0xFFFFFCFF;
  }

  protected void checkSubclass()
  {
    if (!isValidSubclass())
      error(43);
  }

  public Point computeSize(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    checkWidget();
    int i = 0;
    int j = 0;
    int k = getBorderWidth();
    int m = paramInt1 == -1 ? 16383 : paramInt1 + k * 2;
    int n = paramInt2 == -1 ? 16383 : paramInt2 + k * 2;
    int i1 = OS.SendMessage(this.handle, 1036, 0, 0);
    int i2;
    if (i1 != 0)
    {
      this.ignoreResize = true;
      i2 = 0;
      if (OS.IsWindowVisible(this.handle))
        if (OS.COMCTL32_MAJOR >= 6)
        {
          i2 = 1;
          OS.UpdateWindow(this.handle);
          OS.DefWindowProc(this.handle, 11, 0, 0);
        }
        else
        {
          i2 = getDrawing();
          if (i2 != 0)
          {
            OS.UpdateWindow(this.handle);
            OS.SendMessage(this.handle, 11, 0, 0);
          }
        }
      RECT localRECT1 = new RECT();
      OS.GetWindowRect(this.handle, localRECT1);
      int i3 = localRECT1.right - localRECT1.left;
      int i4 = localRECT1.bottom - localRECT1.top;
      int i5 = 30;
      SetWindowPos(this.handle, 0, 0, 0, m, n, i5);
      RECT localRECT2 = new RECT();
      OS.SendMessage(this.handle, 1033, i1 - 1, localRECT2);
      j = Math.max(j, localRECT2.bottom);
      SetWindowPos(this.handle, 0, 0, 0, i3, i4, i5);
      REBARBANDINFO localREBARBANDINFO = new REBARBANDINFO();
      localREBARBANDINFO.cbSize = REBARBANDINFO.sizeof;
      localREBARBANDINFO.fMask = 513;
      int i6 = 0;
      for (int i7 = 0; i7 < i1; i7++)
      {
        OS.SendMessage(this.handle, OS.RB_GETBANDINFO, i7, localREBARBANDINFO);
        if ((localREBARBANDINFO.fStyle & 0x1) != 0)
        {
          i = Math.max(i, i6);
          i6 = 0;
        }
        i6 += localREBARBANDINFO.cxIdeal + getMargin(i7);
      }
      i = Math.max(i, i6);
      if (i2 != 0)
        if (OS.COMCTL32_MAJOR >= 6)
          OS.DefWindowProc(this.handle, 11, 1, 0);
        else
          OS.SendMessage(this.handle, 11, 1, 0);
      this.ignoreResize = false;
    }
    if (i == 0)
      i = 0;
    if (j == 0)
      j = 0;
    if ((this.style & 0x200) != 0)
    {
      i2 = i;
      i = j;
      j = i2;
    }
    if (paramInt1 != -1)
      i = paramInt1;
    if (paramInt2 != -1)
      j = paramInt2;
    j += k * 2;
    i += k * 2;
    return new Point(i, j);
  }

  void createHandle()
  {
    super.createHandle();
    this.state &= -259;
    int i = OS.GetStockObject(13);
    OS.SendMessage(this.handle, 48, i, 0);
  }

  void createItem(CoolItem paramCoolItem, int paramInt)
  {
    int i = OS.SendMessage(this.handle, 1036, 0, 0);
    if ((paramInt < 0) || (paramInt > i))
      error(6);
    for (int j = 0; (j < this.items.length) && (this.items[j] != null); j++);
    if (j == this.items.length)
    {
      CoolItem[] arrayOfCoolItem1 = new CoolItem[this.items.length + 4];
      System.arraycopy(this.items, 0, arrayOfCoolItem1, 0, this.items.length);
      this.items = arrayOfCoolItem1;
    }
    int k = OS.GetProcessHeap();
    int m = OS.HeapAlloc(k, 8, TCHAR.sizeof);
    REBARBANDINFO localREBARBANDINFO = new REBARBANDINFO();
    localREBARBANDINFO.cbSize = REBARBANDINFO.sizeof;
    localREBARBANDINFO.fMask = 261;
    localREBARBANDINFO.fStyle = 192;
    if ((paramCoolItem.style & 0x4) != 0)
      localREBARBANDINFO.fStyle |= 512;
    localREBARBANDINFO.lpText = m;
    localREBARBANDINFO.wID = j;
    int n = getLastIndexOfRow(paramInt - 1);
    int i1 = paramInt == n + 1 ? 1 : 0;
    if (i1 != 0)
    {
      localREBARBANDINFO.fMask |= 64;
      localREBARBANDINFO.cx = 32767;
    }
    if ((paramInt == 0) && (i > 0))
      getItem(0).setWrap(false);
    if (OS.SendMessage(this.handle, OS.RB_INSERTBAND, paramInt, localREBARBANDINFO) == 0)
      error(14);
    if (i1 != 0)
      resizeToPreferredWidth(n);
    OS.HeapFree(k, 0, m);
    int tmp302_300 = j;
    paramCoolItem.id = tmp302_300;
    this.items[tmp302_300] = paramCoolItem;
    int i2 = this.originalItems.length;
    CoolItem[] arrayOfCoolItem2 = new CoolItem[i2 + 1];
    System.arraycopy(this.originalItems, 0, arrayOfCoolItem2, 0, paramInt);
    System.arraycopy(this.originalItems, paramInt, arrayOfCoolItem2, paramInt + 1, i2 - paramInt);
    arrayOfCoolItem2[paramInt] = paramCoolItem;
    this.originalItems = arrayOfCoolItem2;
  }

  void createWidget()
  {
    super.createWidget();
    this.items = new CoolItem[4];
    this.originalItems = new CoolItem[0];
  }

  void destroyItem(CoolItem paramCoolItem)
  {
    int i = OS.SendMessage(this.handle, 1040, paramCoolItem.id, 0);
    int j = OS.SendMessage(this.handle, 1036, 0, 0);
    if (j != 0)
    {
      int k = getLastIndexOfRow(i);
      if (i == k)
        resizeToMaximumWidth(k - 1);
    }
    Control localControl = paramCoolItem.control;
    int m = (localControl != null) && (!localControl.isDisposed()) && (localControl.getVisible()) ? 1 : 0;
    CoolItem localCoolItem = null;
    if ((paramCoolItem.getWrap()) && (i + 1 < j))
    {
      localCoolItem = getItem(i + 1);
      this.ignoreResize = (!localCoolItem.getWrap());
    }
    if (OS.SendMessage(this.handle, 1026, i, 0) == 0)
      error(15);
    this.items[paramCoolItem.id] = null;
    paramCoolItem.id = -1;
    if (this.ignoreResize)
    {
      localCoolItem.setWrap(true);
      this.ignoreResize = false;
    }
    if (m != 0)
      localControl.setVisible(true);
    for (i = 0; i < this.originalItems.length; i++)
      if (this.originalItems[i] == paramCoolItem)
        break;
    int n = this.originalItems.length - 1;
    CoolItem[] arrayOfCoolItem = new CoolItem[n];
    System.arraycopy(this.originalItems, 0, arrayOfCoolItem, 0, i);
    System.arraycopy(this.originalItems, i + 1, arrayOfCoolItem, i, n - i);
    this.originalItems = arrayOfCoolItem;
  }

  void drawThemeBackground(int paramInt1, int paramInt2, RECT paramRECT)
  {
    if ((OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()) && (this.background == -1) && ((this.style & 0x800000) != 0))
    {
      localObject = findBackgroundControl();
      if ((localObject != null) && (((Control)localObject).backgroundImage != null))
      {
        fillBackground(paramInt1, ((Control)localObject).getBackgroundPixel(), paramRECT);
        return;
      }
    }
    Object localObject = new RECT();
    OS.GetClientRect(this.handle, (RECT)localObject);
    OS.MapWindowPoints(this.handle, paramInt2, (RECT)localObject, 2);
    POINT localPOINT = new POINT();
    OS.SetWindowOrgEx(paramInt1, -((RECT)localObject).left, -((RECT)localObject).top, localPOINT);
    OS.SendMessage(this.handle, 791, paramInt1, 12);
    OS.SetWindowOrgEx(paramInt1, localPOINT.x, localPOINT.y, null);
  }

  Control findThemeControl()
  {
    if ((this.style & 0x800000) != 0)
      return this;
    return (this.background == -1) && (this.backgroundImage == null) ? this : super.findThemeControl();
  }

  int getMargin(int paramInt)
  {
    int i = 0;
    if (OS.COMCTL32_MAJOR >= 6)
    {
      localObject = new MARGINS();
      OS.SendMessage(this.handle, 1064, 0, (MARGINS)localObject);
      i += ((MARGINS)localObject).cxLeftWidth + ((MARGINS)localObject).cxRightWidth;
    }
    Object localObject = new RECT();
    OS.SendMessage(this.handle, 1058, paramInt, (RECT)localObject);
    if ((this.style & 0x800000) != 0)
    {
      if ((this.style & 0x200) != 0)
        i += ((RECT)localObject).top + 4;
      else
        i += ((RECT)localObject).left + 4;
    }
    else if ((this.style & 0x200) != 0)
      i += ((RECT)localObject).top + ((RECT)localObject).bottom;
    else
      i += ((RECT)localObject).left + ((RECT)localObject).right;
    if (((this.style & 0x800000) == 0) && (!isLastItemOfRow(paramInt)))
      i += 2;
    return i;
  }

  public CoolItem getItem(int paramInt)
  {
    checkWidget();
    int i = OS.SendMessage(this.handle, 1036, 0, 0);
    if ((paramInt < 0) || (paramInt >= i))
      error(6);
    REBARBANDINFO localREBARBANDINFO = new REBARBANDINFO();
    localREBARBANDINFO.cbSize = REBARBANDINFO.sizeof;
    localREBARBANDINFO.fMask = 256;
    OS.SendMessage(this.handle, OS.RB_GETBANDINFO, paramInt, localREBARBANDINFO);
    return this.items[localREBARBANDINFO.wID];
  }

  public int getItemCount()
  {
    checkWidget();
    return OS.SendMessage(this.handle, 1036, 0, 0);
  }

  public int[] getItemOrder()
  {
    checkWidget();
    int i = OS.SendMessage(this.handle, 1036, 0, 0);
    int[] arrayOfInt = new int[i];
    REBARBANDINFO localREBARBANDINFO = new REBARBANDINFO();
    localREBARBANDINFO.cbSize = REBARBANDINFO.sizeof;
    localREBARBANDINFO.fMask = 256;
    for (int j = 0; j < i; j++)
    {
      OS.SendMessage(this.handle, OS.RB_GETBANDINFO, j, localREBARBANDINFO);
      CoolItem localCoolItem = this.items[localREBARBANDINFO.wID];
      for (int k = 0; k < this.originalItems.length; k++)
        if (this.originalItems[k] == localCoolItem)
          break;
      if (k == this.originalItems.length)
        error(8);
      arrayOfInt[j] = k;
    }
    return arrayOfInt;
  }

  public CoolItem[] getItems()
  {
    checkWidget();
    int i = OS.SendMessage(this.handle, 1036, 0, 0);
    CoolItem[] arrayOfCoolItem = new CoolItem[i];
    REBARBANDINFO localREBARBANDINFO = new REBARBANDINFO();
    localREBARBANDINFO.cbSize = REBARBANDINFO.sizeof;
    localREBARBANDINFO.fMask = 256;
    for (int j = 0; j < i; j++)
    {
      OS.SendMessage(this.handle, OS.RB_GETBANDINFO, j, localREBARBANDINFO);
      arrayOfCoolItem[j] = this.items[localREBARBANDINFO.wID];
    }
    return arrayOfCoolItem;
  }

  public Point[] getItemSizes()
  {
    checkWidget();
    int i = OS.SendMessage(this.handle, 1036, 0, 0);
    Point[] arrayOfPoint = new Point[i];
    REBARBANDINFO localREBARBANDINFO = new REBARBANDINFO();
    localREBARBANDINFO.cbSize = REBARBANDINFO.sizeof;
    localREBARBANDINFO.fMask = 32;
    int j = (this.style & 0x800000) == 0 ? 2 : 0;
    MARGINS localMARGINS = new MARGINS();
    for (int k = 0; k < i; k++)
    {
      RECT localRECT = new RECT();
      OS.SendMessage(this.handle, 1033, k, localRECT);
      OS.SendMessage(this.handle, OS.RB_GETBANDINFO, k, localREBARBANDINFO);
      if (OS.COMCTL32_MAJOR >= 6)
      {
        OS.SendMessage(this.handle, 1064, 0, localMARGINS);
        localRECT.left -= localMARGINS.cxLeftWidth;
        localRECT.right += localMARGINS.cxRightWidth;
      }
      if (!isLastItemOfRow(k))
        localRECT.right += j;
      if ((this.style & 0x200) != 0)
        arrayOfPoint[k] = new Point(localREBARBANDINFO.cyChild, localRECT.right - localRECT.left);
      else
        arrayOfPoint[k] = new Point(localRECT.right - localRECT.left, localREBARBANDINFO.cyChild);
    }
    return arrayOfPoint;
  }

  int getLastIndexOfRow(int paramInt)
  {
    int i = OS.SendMessage(this.handle, 1036, 0, 0);
    if (i == 0)
      return -1;
    REBARBANDINFO localREBARBANDINFO = new REBARBANDINFO();
    localREBARBANDINFO.cbSize = REBARBANDINFO.sizeof;
    localREBARBANDINFO.fMask = 1;
    for (int j = paramInt + 1; j < i; j++)
    {
      OS.SendMessage(this.handle, OS.RB_GETBANDINFO, j, localREBARBANDINFO);
      if ((localREBARBANDINFO.fStyle & 0x1) != 0)
        return j - 1;
    }
    return i - 1;
  }

  boolean isLastItemOfRow(int paramInt)
  {
    int i = OS.SendMessage(this.handle, 1036, 0, 0);
    if (paramInt + 1 == i)
      return true;
    REBARBANDINFO localREBARBANDINFO = new REBARBANDINFO();
    localREBARBANDINFO.cbSize = REBARBANDINFO.sizeof;
    localREBARBANDINFO.fMask = 1;
    OS.SendMessage(this.handle, OS.RB_GETBANDINFO, paramInt + 1, localREBARBANDINFO);
    return (localREBARBANDINFO.fStyle & 0x1) != 0;
  }

  public boolean getLocked()
  {
    checkWidget();
    return this.locked;
  }

  public int[] getWrapIndices()
  {
    checkWidget();
    CoolItem[] arrayOfCoolItem = getItems();
    int[] arrayOfInt1 = new int[arrayOfCoolItem.length];
    int i = 0;
    for (int j = 0; j < arrayOfCoolItem.length; j++)
      if (arrayOfCoolItem[j].getWrap())
        arrayOfInt1[(i++)] = j;
    int[] arrayOfInt2 = new int[i];
    System.arraycopy(arrayOfInt1, 0, arrayOfInt2, 0, i);
    return arrayOfInt2;
  }

  public int indexOf(CoolItem paramCoolItem)
  {
    checkWidget();
    if (paramCoolItem == null)
      error(4);
    if (paramCoolItem.isDisposed())
      error(5);
    return OS.SendMessage(this.handle, 1040, paramCoolItem.id, 0);
  }

  void resizeToPreferredWidth(int paramInt)
  {
    int i = OS.SendMessage(this.handle, 1036, 0, 0);
    if ((paramInt >= 0) && (paramInt < i))
    {
      REBARBANDINFO localREBARBANDINFO = new REBARBANDINFO();
      localREBARBANDINFO.cbSize = REBARBANDINFO.sizeof;
      localREBARBANDINFO.fMask = 512;
      OS.SendMessage(this.handle, OS.RB_GETBANDINFO, paramInt, localREBARBANDINFO);
      RECT localRECT = new RECT();
      OS.SendMessage(this.handle, 1058, paramInt, localRECT);
      localREBARBANDINFO.cx = (localREBARBANDINFO.cxIdeal + localRECT.left);
      if ((this.style & 0x800000) == 0)
        localREBARBANDINFO.cx += localRECT.right;
      localREBARBANDINFO.fMask = 64;
      OS.SendMessage(this.handle, OS.RB_SETBANDINFO, paramInt, localREBARBANDINFO);
    }
  }

  void resizeToMaximumWidth(int paramInt)
  {
    REBARBANDINFO localREBARBANDINFO = new REBARBANDINFO();
    localREBARBANDINFO.cbSize = REBARBANDINFO.sizeof;
    localREBARBANDINFO.fMask = 64;
    localREBARBANDINFO.cx = 32767;
    OS.SendMessage(this.handle, OS.RB_SETBANDINFO, paramInt, localREBARBANDINFO);
  }

  void releaseChildren(boolean paramBoolean)
  {
    if (this.items != null)
    {
      for (int i = 0; i < this.items.length; i++)
      {
        CoolItem localCoolItem = this.items[i];
        if ((localCoolItem != null) && (!localCoolItem.isDisposed()))
          localCoolItem.release(false);
      }
      this.items = null;
    }
    super.releaseChildren(paramBoolean);
  }

  void removeControl(Control paramControl)
  {
    super.removeControl(paramControl);
    for (int i = 0; i < this.items.length; i++)
    {
      CoolItem localCoolItem = this.items[i];
      if ((localCoolItem != null) && (localCoolItem.control == paramControl))
        localCoolItem.setControl(null);
    }
  }

  void reskinChildren(int paramInt)
  {
    if (this.items != null)
      for (int i = 0; i < this.items.length; i++)
      {
        CoolItem localCoolItem = this.items[i];
        if (localCoolItem != null)
          localCoolItem.reskin(paramInt);
      }
    super.reskinChildren(paramInt);
  }

  void setBackgroundPixel(int paramInt)
  {
    if (paramInt == -1)
      paramInt = defaultBackground();
    OS.SendMessage(this.handle, 1043, 0, paramInt);
    setItemColors(OS.SendMessage(this.handle, 1046, 0, 0), paramInt);
    if (!OS.IsWindowVisible(this.handle))
      return;
    if (OS.IsWinCE)
    {
      OS.InvalidateRect(this.handle, null, true);
    }
    else
    {
      int i = 1157;
      OS.RedrawWindow(this.handle, null, 0, i);
    }
  }

  void setForegroundPixel(int paramInt)
  {
    if (paramInt == -1)
      paramInt = defaultForeground();
    OS.SendMessage(this.handle, 1045, 0, paramInt);
    setItemColors(paramInt, OS.SendMessage(this.handle, 1044, 0, 0));
  }

  void setItemColors(int paramInt1, int paramInt2)
  {
    int i = OS.SendMessage(this.handle, 1036, 0, 0);
    REBARBANDINFO localREBARBANDINFO = new REBARBANDINFO();
    localREBARBANDINFO.cbSize = REBARBANDINFO.sizeof;
    localREBARBANDINFO.fMask = 2;
    localREBARBANDINFO.clrFore = paramInt1;
    localREBARBANDINFO.clrBack = paramInt2;
    for (int j = 0; j < i; j++)
      OS.SendMessage(this.handle, OS.RB_SETBANDINFO, j, localREBARBANDINFO);
  }

  public void setItemLayout(int[] paramArrayOfInt1, int[] paramArrayOfInt2, Point[] paramArrayOfPoint)
  {
    checkWidget();
    setRedraw(false);
    setItemOrder(paramArrayOfInt1);
    setWrapIndices(paramArrayOfInt2);
    setItemSizes(paramArrayOfPoint);
    setRedraw(true);
  }

  void setItemOrder(int[] paramArrayOfInt)
  {
    if (paramArrayOfInt == null)
      error(4);
    int i = OS.SendMessage(this.handle, 1036, 0, 0);
    if (paramArrayOfInt.length != i)
      error(5);
    boolean[] arrayOfBoolean = new boolean[i];
    for (int j = 0; j < paramArrayOfInt.length; j++)
    {
      k = paramArrayOfInt[j];
      if ((k < 0) || (k >= i))
        error(6);
      if (arrayOfBoolean[k] != 0)
        error(5);
      arrayOfBoolean[k] = true;
    }
    REBARBANDINFO localREBARBANDINFO = new REBARBANDINFO();
    localREBARBANDINFO.cbSize = REBARBANDINFO.sizeof;
    for (int k = 0; k < paramArrayOfInt.length; k++)
    {
      int m = this.originalItems[paramArrayOfInt[k]].id;
      int n = OS.SendMessage(this.handle, 1040, m, 0);
      if (n != k)
      {
        int i1 = getLastIndexOfRow(n);
        int i2 = getLastIndexOfRow(k);
        if (n == i1)
          resizeToPreferredWidth(n);
        if (k == i2)
          resizeToPreferredWidth(k);
        OS.SendMessage(this.handle, 1063, n, k);
        if ((n == i1) && (n - 1 >= 0))
          resizeToMaximumWidth(n - 1);
        if (k == i2)
          resizeToMaximumWidth(k);
      }
    }
  }

  void setItemSizes(Point[] paramArrayOfPoint)
  {
    if (paramArrayOfPoint == null)
      error(4);
    int i = OS.SendMessage(this.handle, 1036, 0, 0);
    if (paramArrayOfPoint.length != i)
      error(5);
    REBARBANDINFO localREBARBANDINFO = new REBARBANDINFO();
    localREBARBANDINFO.cbSize = REBARBANDINFO.sizeof;
    localREBARBANDINFO.fMask = 256;
    for (int j = 0; j < i; j++)
    {
      OS.SendMessage(this.handle, OS.RB_GETBANDINFO, j, localREBARBANDINFO);
      this.items[localREBARBANDINFO.wID].setSize(paramArrayOfPoint[j].x, paramArrayOfPoint[j].y);
    }
  }

  public void setLocked(boolean paramBoolean)
  {
    checkWidget();
    this.locked = paramBoolean;
    int i = OS.SendMessage(this.handle, 1036, 0, 0);
    REBARBANDINFO localREBARBANDINFO = new REBARBANDINFO();
    localREBARBANDINFO.cbSize = REBARBANDINFO.sizeof;
    localREBARBANDINFO.fMask = 1;
    for (int j = 0; j < i; j++)
    {
      OS.SendMessage(this.handle, OS.RB_GETBANDINFO, j, localREBARBANDINFO);
      if (paramBoolean)
        localREBARBANDINFO.fStyle |= 256;
      else
        localREBARBANDINFO.fStyle &= -257;
      OS.SendMessage(this.handle, OS.RB_SETBANDINFO, j, localREBARBANDINFO);
    }
  }

  public void setWrapIndices(int[] paramArrayOfInt)
  {
    checkWidget();
    if (paramArrayOfInt == null)
      paramArrayOfInt = new int[0];
    int i = getItemCount();
    for (int j = 0; j < paramArrayOfInt.length; j++)
      if ((paramArrayOfInt[j] < 0) || (paramArrayOfInt[j] >= i))
        error(6);
    setRedraw(false);
    CoolItem[] arrayOfCoolItem = getItems();
    for (int k = 0; k < arrayOfCoolItem.length; k++)
    {
      CoolItem localCoolItem1 = arrayOfCoolItem[k];
      if (localCoolItem1.getWrap())
      {
        resizeToPreferredWidth(k - 1);
        localCoolItem1.setWrap(false);
      }
    }
    resizeToMaximumWidth(i - 1);
    for (k = 0; k < paramArrayOfInt.length; k++)
    {
      int m = paramArrayOfInt[k];
      if ((m >= 0) && (m < arrayOfCoolItem.length))
      {
        CoolItem localCoolItem2 = arrayOfCoolItem[m];
        localCoolItem2.setWrap(true);
        resizeToMaximumWidth(m - 1);
      }
    }
    setRedraw(true);
  }

  int widgetStyle()
  {
    int i = super.widgetStyle() | 0x40 | 0x4;
    i |= 33280;
    if ((this.style & 0x800000) == 0)
      i |= 1024;
    return i;
  }

  TCHAR windowClass()
  {
    return ReBarClass;
  }

  int windowProc()
  {
    return ReBarProc;
  }

  LRESULT WM_COMMAND(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_COMMAND(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    return LRESULT.ZERO;
  }

  LRESULT WM_ERASEBKGND(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_ERASEBKGND(paramInt1, paramInt2);
    if ((OS.COMCTL32_MAJOR < 6) || (!OS.IsAppThemed()))
    {
      drawBackground(paramInt1);
      return null;
    }
    return localLRESULT;
  }

  LRESULT WM_NOTIFY(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_NOTIFY(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    return LRESULT.ZERO;
  }

  LRESULT WM_SETREDRAW(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_SETREDRAW(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    if (OS.COMCTL32_MAJOR >= 6)
      return LRESULT.ZERO;
    Rectangle localRectangle = getBounds();
    int i = callWindowProc(this.handle, 11, paramInt1, paramInt2);
    OS.DefWindowProc(this.handle, 11, paramInt1, paramInt2);
    if (!localRectangle.equals(getBounds()))
      this.parent.redraw(localRectangle.x, localRectangle.y, localRectangle.width, localRectangle.height, true);
    return new LRESULT(i);
  }

  LRESULT WM_SIZE(int paramInt1, int paramInt2)
  {
    if (this.ignoreResize)
    {
      int i = callWindowProc(this.handle, 5, paramInt1, paramInt2);
      if (i == 0)
        return LRESULT.ZERO;
      return new LRESULT(i);
    }
    return super.WM_SIZE(paramInt1, paramInt2);
  }

  LRESULT wmNotifyChild(NMHDR paramNMHDR, int paramInt1, int paramInt2)
  {
    Object localObject2;
    Object localObject1;
    switch (paramNMHDR.code)
    {
    case -835:
      int i = OS.GetMessagePos();
      localObject2 = new POINT();
      OS.POINTSTOPOINT((POINT)localObject2, i);
      OS.ScreenToClient(this.handle, (POINT)localObject2);
      int k = this.display.lastButton != 0 ? this.display.lastButton : 1;
      if (!sendDragEvent(k, ((POINT)localObject2).x, ((POINT)localObject2).y))
        return LRESULT.ONE;
      break;
    case -839:
      localObject1 = new NMREBARCHILDSIZE();
      OS.MoveMemory((NMREBARCHILDSIZE)localObject1, paramInt2, NMREBARCHILDSIZE.sizeof);
      if (((NMREBARCHILDSIZE)localObject1).uBand != -1)
      {
        localObject2 = this.items[localObject1.wID];
        Control localControl = ((CoolItem)localObject2).control;
        if (localControl != null)
        {
          int n = ((NMREBARCHILDSIZE)localObject1).rcChild_right - ((NMREBARCHILDSIZE)localObject1).rcChild_left;
          int i1 = ((NMREBARCHILDSIZE)localObject1).rcChild_bottom - ((NMREBARCHILDSIZE)localObject1).rcChild_top;
          localControl.setBounds(((NMREBARCHILDSIZE)localObject1).rcChild_left, ((NMREBARCHILDSIZE)localObject1).rcChild_top, n, i1);
        }
      }
      break;
    case -831:
      if (!this.ignoreResize)
      {
        localObject1 = getSize();
        int j = getBorderWidth();
        int m = OS.SendMessage(this.handle, 1051, 0, 0);
        if ((this.style & 0x200) != 0)
          setSize(m + 2 * j, ((Point)localObject1).y);
        else
          setSize(((Point)localObject1).x, m + 2 * j);
      }
      break;
    case -841:
      localObject1 = new NMREBARCHEVRON();
      OS.MoveMemory((NMREBARCHEVRON)localObject1, paramInt2, NMREBARCHEVRON.sizeof);
      CoolItem localCoolItem = this.items[localObject1.wID];
      if (localCoolItem != null)
      {
        Event localEvent = new Event();
        localEvent.detail = 4;
        if ((this.style & 0x200) != 0)
        {
          localEvent.x = ((NMREBARCHEVRON)localObject1).right;
          localEvent.y = ((NMREBARCHEVRON)localObject1).top;
        }
        else
        {
          localEvent.x = ((NMREBARCHEVRON)localObject1).left;
          localEvent.y = ((NMREBARCHEVRON)localObject1).bottom;
        }
        localCoolItem.sendSelectionEvent(13, localEvent, false);
      }
      break;
    case -12:
      if ((OS.COMCTL32_MAJOR >= 6) && ((findBackgroundControl() != null) || ((this.style & 0x800000) != 0)))
      {
        localObject1 = new NMCUSTOMDRAW();
        OS.MoveMemory((NMCUSTOMDRAW)localObject1, paramInt2, NMCUSTOMDRAW.sizeof);
        switch (((NMCUSTOMDRAW)localObject1).dwDrawStage)
        {
        case 3:
          return new LRESULT(68);
        case 4:
          drawBackground(((NMCUSTOMDRAW)localObject1).hdc);
        }
      }
      break;
    }
    return super.wmNotifyChild(paramNMHDR, paramInt1, paramInt2);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.CoolBar
 * JD-Core Version:    0.6.2
 */