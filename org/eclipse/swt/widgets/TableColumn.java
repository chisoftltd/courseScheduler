package org.eclipse.swt.widgets;

import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.win32.HDITEM;
import org.eclipse.swt.internal.win32.LVCOLUMN;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.TOOLINFO;

public class TableColumn extends Item
{
  Table parent;
  boolean resizable = true;
  boolean moveable;
  String toolTipText;
  int id;

  public TableColumn(Table paramTable, int paramInt)
  {
    super(paramTable, checkStyle(paramInt));
    this.parent = paramTable;
    paramTable.createItem(this, paramTable.getColumnCount());
  }

  public TableColumn(Table paramTable, int paramInt1, int paramInt2)
  {
    super(paramTable, checkStyle(paramInt1));
    this.parent = paramTable;
    paramTable.createItem(this, paramInt2);
  }

  public void addControlListener(ControlListener paramControlListener)
  {
    checkWidget();
    if (paramControlListener == null)
      error(4);
    TypedListener localTypedListener = new TypedListener(paramControlListener);
    addListener(11, localTypedListener);
    addListener(10, localTypedListener);
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

  static int checkStyle(int paramInt)
  {
    return checkBits(paramInt, 16384, 16777216, 131072, 0, 0, 0);
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

  public int getAlignment()
  {
    checkWidget();
    if ((this.style & 0x4000) != 0)
      return 16384;
    if ((this.style & 0x1000000) != 0)
      return 16777216;
    if ((this.style & 0x20000) != 0)
      return 131072;
    return 16384;
  }

  String getNameText()
  {
    return getText();
  }

  public Table getParent()
  {
    checkWidget();
    return this.parent;
  }

  public boolean getMoveable()
  {
    checkWidget();
    return this.moveable;
  }

  public boolean getResizable()
  {
    checkWidget();
    return this.resizable;
  }

  public String getToolTipText()
  {
    checkWidget();
    return this.toolTipText;
  }

  public int getWidth()
  {
    checkWidget();
    int i = this.parent.indexOf(this);
    if (i == -1)
      return 0;
    int j = this.parent.handle;
    return OS.SendMessage(j, 4125, i, 0);
  }

  public void pack()
  {
    checkWidget();
    int i = this.parent.indexOf(this);
    if (i == -1)
      return;
    int j = this.parent.handle;
    int k = OS.SendMessage(j, 4125, i, 0);
    TCHAR localTCHAR = new TCHAR(this.parent.getCodePage(), this.text, true);
    int m = OS.SendMessage(j, OS.LVM_GETSTRINGWIDTH, 0, localTCHAR) + 12;
    if ((OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()))
      m += 3;
    int n = 0;
    int i5;
    if ((this.image != null) || (this.parent.sortColumn == this))
    {
      n = 1;
      Image localImage = null;
      if ((this.parent.sortColumn == this) && (this.parent.sortDirection != 0))
      {
        if (OS.COMCTL32_MAJOR < 6)
          localImage = this.display.getSortImage(this.parent.sortDirection);
        else
          m += 10;
      }
      else
        localImage = this.image;
      if (localImage != null)
      {
        Rectangle localRectangle = localImage.getBounds();
        m += localRectangle.width;
      }
      int i2 = 0;
      if (OS.COMCTL32_VERSION >= OS.VERSION(5, 80))
      {
        i5 = OS.SendMessage(j, 4127, 0, 0);
        i2 = OS.SendMessage(i5, 4629, 0, 0);
      }
      else
      {
        i2 = OS.GetSystemMetrics(45) * 3;
      }
      m += i2 * 4;
    }
    this.parent.ignoreColumnResize = true;
    int i1 = 0;
    int i10;
    if (this.parent.hooks(41))
    {
      RECT localRECT1 = new RECT();
      i5 = OS.SendMessage(j, 4127, 0, 0);
      OS.SendMessage(i5, 4615, i, localRECT1);
      OS.MapWindowPoints(i5, j, localRECT1, 2);
      int i7 = OS.GetDC(j);
      int i9 = 0;
      i10 = OS.SendMessage(j, 49, 0, 0);
      if (i10 != 0)
        i9 = OS.SelectObject(i7, i10);
      int i11 = OS.SendMessage(j, 4100, 0, 0);
      for (int i12 = 0; i12 < i11; i12++)
      {
        TableItem localTableItem = this.parent._getItem(i12, false);
        if (localTableItem != null)
        {
          int i13 = localTableItem.fontHandle(i);
          if (i13 != -1)
            i13 = OS.SelectObject(i7, i13);
          Event localEvent = this.parent.sendMeasureItemEvent(localTableItem, i12, i, i7);
          if (i13 != -1)
            i13 = OS.SelectObject(i7, i13);
          if ((isDisposed()) || (this.parent.isDisposed()))
            break;
          i1 = Math.max(i1, localEvent.x + localEvent.width - localRECT1.left);
        }
      }
      if (i10 != 0)
        OS.SelectObject(i7, i9);
      OS.ReleaseDC(j, i7);
      OS.SendMessage(j, 4126, i, i1);
    }
    else
    {
      OS.SendMessage(j, 4126, i, -1);
      i1 = OS.SendMessage(j, 4125, i, 0);
      if (i == 0)
      {
        if (this.parent.imageList == null)
          i1 += 2;
        int i3;
        int[] arrayOfInt1;
        int[] arrayOfInt2;
        if ((!OS.IsWinCE) && (OS.WIN32_VERSION < OS.VERSION(6, 0)) && (!this.parent.firstColumnImage))
        {
          i3 = OS.SendMessage(j, 4098, 1, 0);
          if (i3 != 0)
          {
            arrayOfInt1 = new int[1];
            arrayOfInt2 = new int[1];
            OS.ImageList_GetIconSize(i3, arrayOfInt1, arrayOfInt2);
            i1 += arrayOfInt1[0];
          }
        }
        if ((this.parent.style & 0x20) != 0)
        {
          i3 = OS.SendMessage(j, 4098, 2, 0);
          if (i3 != 0)
          {
            arrayOfInt1 = new int[1];
            arrayOfInt2 = new int[1];
            OS.ImageList_GetIconSize(i3, arrayOfInt1, arrayOfInt2);
            i1 += arrayOfInt1[0];
          }
        }
      }
    }
    int i6;
    if (m > i1)
    {
      if (n == 0)
      {
        RECT localRECT2 = null;
        i6 = i == this.parent.getColumnCount() - 1 ? 1 : 0;
        int i8;
        if (i6 != 0)
        {
          localRECT2 = new RECT();
          OS.GetWindowRect(j, localRECT2);
          OS.UpdateWindow(j);
          i8 = 30;
          SetWindowPos(j, 0, 0, 0, 0, localRECT2.bottom - localRECT2.top, i8);
        }
        OS.SendMessage(j, 4126, i, -2);
        if (i6 != 0)
        {
          i8 = 22;
          SetWindowPos(j, 0, 0, 0, localRECT2.right - localRECT2.left, localRECT2.bottom - localRECT2.top, i8);
        }
      }
      else
      {
        OS.SendMessage(j, 4126, i, m);
      }
    }
    else if (i == 0)
      OS.SendMessage(j, 4126, i, i1);
    this.parent.ignoreColumnResize = false;
    int i4 = OS.SendMessage(j, 4125, i, 0);
    if (k != i4)
    {
      updateToolTip(i);
      sendEvent(11);
      if (isDisposed())
        return;
      i6 = 0;
      int[] arrayOfInt3 = this.parent.getColumnOrder();
      TableColumn[] arrayOfTableColumn = this.parent.getColumns();
      for (i10 = 0; i10 < arrayOfInt3.length; i10++)
      {
        TableColumn localTableColumn = arrayOfTableColumn[arrayOfInt3[i10]];
        if ((i6 != 0) && (!localTableColumn.isDisposed()))
        {
          localTableColumn.updateToolTip(arrayOfInt3[i10]);
          localTableColumn.sendEvent(10);
        }
        if (localTableColumn == this)
          i6 = 1;
      }
    }
  }

  void releaseHandle()
  {
    super.releaseHandle();
    this.parent = null;
  }

  void releaseParent()
  {
    super.releaseParent();
    if (this.parent.sortColumn == this)
      this.parent.sortColumn = null;
  }

  public void removeControlListener(ControlListener paramControlListener)
  {
    checkWidget();
    if (paramControlListener == null)
      error(4);
    if (this.eventTable == null)
      return;
    this.eventTable.unhook(10, paramControlListener);
    this.eventTable.unhook(11, paramControlListener);
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

  public void setAlignment(int paramInt)
  {
    checkWidget();
    if ((paramInt & 0x1024000) == 0)
      return;
    int i = this.parent.indexOf(this);
    if ((i == -1) || (i == 0))
      return;
    this.style &= -16924673;
    this.style |= paramInt & 0x1024000;
    int j = this.parent.handle;
    LVCOLUMN localLVCOLUMN = new LVCOLUMN();
    localLVCOLUMN.mask = 1;
    OS.SendMessage(j, OS.LVM_GETCOLUMN, i, localLVCOLUMN);
    localLVCOLUMN.fmt &= -4;
    int k = 0;
    if ((this.style & 0x4000) == 16384)
      k = 0;
    if ((this.style & 0x1000000) == 16777216)
      k = 2;
    if ((this.style & 0x20000) == 131072)
      k = 1;
    localLVCOLUMN.fmt |= k;
    OS.SendMessage(j, OS.LVM_SETCOLUMN, i, localLVCOLUMN);
    if (i != 0)
    {
      this.parent.forceResize();
      RECT localRECT1 = new RECT();
      RECT localRECT2 = new RECT();
      OS.GetClientRect(j, localRECT1);
      int m = OS.SendMessage(j, 4127, 0, 0);
      OS.SendMessage(m, 4615, i, localRECT2);
      OS.MapWindowPoints(m, j, localRECT2, 2);
      localRECT1.left = localRECT2.left;
      localRECT1.right = localRECT2.right;
      OS.InvalidateRect(j, localRECT1, true);
    }
  }

  public void setImage(Image paramImage)
  {
    checkWidget();
    if ((paramImage != null) && (paramImage.isDisposed()))
      error(5);
    super.setImage(paramImage);
    if ((this.parent.sortColumn != this) || (this.parent.sortDirection != 0))
      setImage(paramImage, false, false);
  }

  void setImage(Image paramImage, boolean paramBoolean1, boolean paramBoolean2)
  {
    int i = this.parent.indexOf(this);
    if (i == -1)
      return;
    int j = this.parent.handle;
    if (OS.COMCTL32_MAJOR < 6)
    {
      int k = OS.SendMessage(j, 4127, 0, 0);
      HDITEM localHDITEM = new HDITEM();
      localHDITEM.mask = 52;
      OS.SendMessage(k, OS.HDM_GETITEM, i, localHDITEM);
      localHDITEM.fmt &= -4097;
      if (paramImage != null)
      {
        if (paramBoolean1)
        {
          localHDITEM.mask &= -33;
          localHDITEM.fmt &= -2049;
          localHDITEM.fmt |= 8192;
          localHDITEM.hbm = paramImage.handle;
        }
        else
        {
          localHDITEM.mask &= -17;
          localHDITEM.fmt &= -8193;
          localHDITEM.fmt |= 2048;
          localHDITEM.iImage = this.parent.imageIndexHeader(paramImage);
        }
        if (paramBoolean2)
          localHDITEM.fmt |= 4096;
      }
      else
      {
        localHDITEM.fmt &= -10241;
      }
      OS.SendMessage(k, OS.HDM_SETITEM, i, localHDITEM);
    }
    else
    {
      LVCOLUMN localLVCOLUMN = new LVCOLUMN();
      localLVCOLUMN.mask = 17;
      OS.SendMessage(j, OS.LVM_GETCOLUMN, i, localLVCOLUMN);
      if (paramImage != null)
      {
        localLVCOLUMN.fmt |= 2048;
        localLVCOLUMN.iImage = this.parent.imageIndexHeader(paramImage);
        if (paramBoolean2)
          localLVCOLUMN.fmt |= 4096;
      }
      else
      {
        localLVCOLUMN.mask &= -17;
        localLVCOLUMN.fmt &= -6145;
      }
      OS.SendMessage(j, OS.LVM_SETCOLUMN, i, localLVCOLUMN);
    }
  }

  public void setMoveable(boolean paramBoolean)
  {
    checkWidget();
    this.moveable = paramBoolean;
    this.parent.updateMoveable();
  }

  public void setResizable(boolean paramBoolean)
  {
    checkWidget();
    this.resizable = paramBoolean;
  }

  void setSortDirection(int paramInt)
  {
    if (OS.COMCTL32_MAJOR >= 6)
    {
      int i = this.parent.indexOf(this);
      if (i == -1)
        return;
      int j = this.parent.handle;
      int k = OS.SendMessage(j, 4127, 0, 0);
      HDITEM localHDITEM = new HDITEM();
      localHDITEM.mask = 36;
      OS.SendMessage(k, OS.HDM_GETITEM, i, localHDITEM);
      switch (paramInt)
      {
      case 128:
        localHDITEM.fmt &= -2561;
        localHDITEM.fmt |= 1024;
        if (this.image == null)
          localHDITEM.mask &= -33;
        break;
      case 1024:
        localHDITEM.fmt &= -3073;
        localHDITEM.fmt |= 512;
        if (this.image == null)
          localHDITEM.mask &= -33;
        break;
      case 0:
        localHDITEM.fmt &= -1537;
        if (this.image != null)
        {
          localHDITEM.fmt |= 2048;
          localHDITEM.iImage = this.parent.imageIndexHeader(this.image);
        }
        else
        {
          localHDITEM.fmt &= -2049;
          localHDITEM.mask &= -33;
        }
        break;
      }
      OS.SendMessage(k, OS.HDM_SETITEM, i, localHDITEM);
      this.parent.forceResize();
      RECT localRECT1 = new RECT();
      OS.GetClientRect(j, localRECT1);
      if (OS.SendMessage(j, 4096, 0, 0) != -1)
      {
        int m = OS.SendMessage(j, 4270, 0, 0);
        int n = paramInt == 0 ? -1 : i;
        OS.SendMessage(j, 4236, n, 0);
        RECT localRECT3 = new RECT();
        if ((m != -1) && (OS.SendMessage(k, 4615, m, localRECT3) != 0))
        {
          OS.MapWindowPoints(k, j, localRECT3, 2);
          localRECT1.left = localRECT3.left;
          localRECT1.right = localRECT3.right;
          OS.InvalidateRect(j, localRECT1, true);
        }
      }
      RECT localRECT2 = new RECT();
      if (OS.SendMessage(k, 4615, i, localRECT2) != 0)
      {
        OS.MapWindowPoints(k, j, localRECT2, 2);
        localRECT1.left = localRECT2.left;
        localRECT1.right = localRECT2.right;
        OS.InvalidateRect(j, localRECT1, true);
      }
    }
    else
    {
      switch (paramInt)
      {
      case 128:
      case 1024:
        setImage(this.display.getSortImage(paramInt), true, true);
        break;
      case 0:
        setImage(this.image, false, false);
      }
    }
  }

  public void setText(String paramString)
  {
    checkWidget();
    if (paramString == null)
      error(4);
    if (paramString.equals(this.text))
      return;
    int i = this.parent.indexOf(this);
    if (i == -1)
      return;
    super.setText(paramString);
    int j = this.parent.handle;
    LVCOLUMN localLVCOLUMN = new LVCOLUMN();
    localLVCOLUMN.mask = 1;
    OS.SendMessage(j, OS.LVM_GETCOLUMN, i, localLVCOLUMN);
    int k = OS.GetProcessHeap();
    TCHAR localTCHAR = new TCHAR(this.parent.getCodePage(), fixMnemonic(paramString, true), true);
    int m = localTCHAR.length() * TCHAR.sizeof;
    int n = OS.HeapAlloc(k, 8, m);
    OS.MoveMemory(n, localTCHAR, m);
    localLVCOLUMN.mask |= 4;
    localLVCOLUMN.pszText = n;
    int i1 = OS.SendMessage(j, OS.LVM_SETCOLUMN, i, localLVCOLUMN);
    if (n != 0)
      OS.HeapFree(k, 0, n);
    if (i1 == 0)
      error(13);
  }

  public void setToolTipText(String paramString)
  {
    checkWidget();
    this.toolTipText = paramString;
    int i = this.parent.headerToolTipHandle;
    if (i == 0)
    {
      this.parent.createHeaderToolTips();
      this.parent.updateHeaderToolTips();
    }
  }

  public void setWidth(int paramInt)
  {
    checkWidget();
    if (paramInt < 0)
      return;
    int i = this.parent.indexOf(this);
    if (i == -1)
      return;
    int j = this.parent.handle;
    if (paramInt != OS.SendMessage(j, 4125, i, 0))
      OS.SendMessage(j, 4126, i, paramInt);
  }

  void updateToolTip(int paramInt)
  {
    int i = this.parent.headerToolTipHandle;
    if (i != 0)
    {
      int j = this.parent.handle;
      int k = OS.SendMessage(j, 4127, 0, 0);
      RECT localRECT = new RECT();
      if (OS.SendMessage(k, 4615, paramInt, localRECT) != 0)
      {
        TOOLINFO localTOOLINFO = new TOOLINFO();
        localTOOLINFO.cbSize = TOOLINFO.sizeof;
        localTOOLINFO.hwnd = k;
        localTOOLINFO.uId = this.id;
        localTOOLINFO.left = localRECT.left;
        localTOOLINFO.top = localRECT.top;
        localTOOLINFO.right = localRECT.right;
        localTOOLINFO.bottom = localRECT.bottom;
        OS.SendMessage(i, OS.TTM_NEWTOOLRECT, 0, localTOOLINFO);
      }
    }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.TableColumn
 * JD-Core Version:    0.6.2
 */