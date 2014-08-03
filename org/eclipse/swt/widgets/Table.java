package org.eclipse.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.GCData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.ImageList;
import org.eclipse.swt.internal.win32.BITMAPINFOHEADER;
import org.eclipse.swt.internal.win32.HDHITTESTINFO;
import org.eclipse.swt.internal.win32.HDITEM;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.LVCOLUMN;
import org.eclipse.swt.internal.win32.LVHITTESTINFO;
import org.eclipse.swt.internal.win32.LVITEM;
import org.eclipse.swt.internal.win32.MEASUREITEMSTRUCT;
import org.eclipse.swt.internal.win32.NMHDR;
import org.eclipse.swt.internal.win32.NMHEADER;
import org.eclipse.swt.internal.win32.NMLISTVIEW;
import org.eclipse.swt.internal.win32.NMLVCUSTOMDRAW;
import org.eclipse.swt.internal.win32.NMLVDISPINFO;
import org.eclipse.swt.internal.win32.NMLVODSTATECHANGE;
import org.eclipse.swt.internal.win32.NMRGINFO;
import org.eclipse.swt.internal.win32.NMTTCUSTOMDRAW;
import org.eclipse.swt.internal.win32.NMTTDISPINFO;
import org.eclipse.swt.internal.win32.NMTTDISPINFOA;
import org.eclipse.swt.internal.win32.NMTTDISPINFOW;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.PAINTSTRUCT;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.SCROLLINFO;
import org.eclipse.swt.internal.win32.SHDRAGIMAGE;
import org.eclipse.swt.internal.win32.SIZE;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.TEXTMETRIC;
import org.eclipse.swt.internal.win32.TEXTMETRICA;
import org.eclipse.swt.internal.win32.TEXTMETRICW;
import org.eclipse.swt.internal.win32.TOOLINFO;
import org.eclipse.swt.internal.win32.WNDCLASS;

public class Table extends Composite
{
  TableItem[] items;
  int[] keys;
  TableColumn[] columns;
  int columnCount;
  int customCount;
  int keyCount;
  ImageList imageList;
  ImageList headerImageList;
  TableItem currentItem;
  TableColumn sortColumn;
  RECT focusRect;
  int headerToolTipHandle;
  boolean ignoreCustomDraw;
  boolean ignoreDrawForeground;
  boolean ignoreDrawBackground;
  boolean ignoreDrawFocus;
  boolean ignoreDrawSelection;
  boolean ignoreDrawHot;
  boolean customDraw;
  boolean dragStarted;
  boolean explorerTheme;
  boolean firstColumnImage;
  boolean fixScrollWidth;
  boolean tipRequested;
  boolean wasSelected;
  boolean wasResized;
  boolean painted;
  boolean ignoreActivate;
  boolean ignoreSelect;
  boolean ignoreShrink;
  boolean ignoreResize;
  boolean ignoreColumnMove;
  boolean ignoreColumnResize;
  boolean fullRowSelect;
  int itemHeight;
  int lastIndexOf;
  int lastWidth;
  int sortDirection;
  int resizeCount;
  int selectionForeground;
  int hotIndex;
  static int HeaderProc;
  static final int INSET = 4;
  static final int GRID_WIDTH = 1;
  static final int SORT_WIDTH = 10;
  static final int HEADER_MARGIN = 12;
  static final int HEADER_EXTRA = 3;
  static final int VISTA_EXTRA = 2;
  static final int EXPLORER_EXTRA = 2;
  static final int H_SCROLL_LIMIT = 32;
  static final int V_SCROLL_LIMIT = 16;
  static final int DRAG_IMAGE_SIZE = 301;
  static final boolean EXPLORER_THEME = true;
  static boolean COMPRESS_ITEMS = true;
  static final int TableProc = localWNDCLASS.lpfnWndProc;
  static final TCHAR TableClass = new TCHAR(0, "SysListView32", true);

  static
  {
    WNDCLASS localWNDCLASS = new WNDCLASS();
    OS.GetClassInfo(0, TableClass, localWNDCLASS);
  }

  public Table(Composite paramComposite, int paramInt)
  {
    super(paramComposite, checkStyle(paramInt));
  }

  void _addListener(int paramInt, Listener paramListener)
  {
    super._addListener(paramInt, paramListener);
    switch (paramInt)
    {
    case 40:
    case 41:
    case 42:
      setCustomDraw(true);
      setBackgroundTransparent(true);
      if (OS.COMCTL32_MAJOR < 6)
        this.style |= 536870912;
      if (OS.IsWinCE)
        OS.SendMessage(this.handle, 4150, 16384, 0);
      break;
    }
  }

  boolean _checkGrow(int paramInt)
  {
    int i;
    int j;
    Object localObject;
    if (this.keys == null)
    {
      if (paramInt == this.items.length)
      {
        i = (getDrawing()) && (OS.IsWindowVisible(this.handle)) ? 1 : 0;
        j = i != 0 ? this.items.length + 4 : Math.max(4, this.items.length * 3 / 2);
        localObject = new TableItem[j];
        System.arraycopy(this.items, 0, localObject, 0, this.items.length);
        this.items = ((TableItem[])localObject);
      }
    }
    else
    {
      if ((!this.ignoreShrink) && (this.keyCount > paramInt / 2))
      {
        i = (getDrawing()) && (OS.IsWindowVisible(this.handle)) ? 1 : 0;
        j = i != 0 ? paramInt + 4 : Math.max(4, paramInt * 3 / 2);
        localObject = new TableItem[j];
        for (int k = 0; k < this.keyCount; k++)
          localObject[this.keys[k]] = this.items[k];
        this.items = ((TableItem[])localObject);
        this.keys = null;
        this.keyCount = 0;
        return true;
      }
      if (this.keyCount == this.keys.length)
      {
        i = (getDrawing()) && (OS.IsWindowVisible(this.handle)) ? 1 : 0;
        j = i != 0 ? this.keys.length + 4 : Math.max(4, this.keys.length * 3 / 2);
        localObject = new int[j];
        System.arraycopy(this.keys, 0, localObject, 0, this.keys.length);
        this.keys = ((int[])localObject);
        TableItem[] arrayOfTableItem = new TableItem[j];
        System.arraycopy(this.items, 0, arrayOfTableItem, 0, this.items.length);
        this.items = arrayOfTableItem;
      }
    }
    return false;
  }

  void _checkShrink()
  {
    int i;
    TableItem[] arrayOfTableItem;
    if (this.keys == null)
    {
      if (!this.ignoreShrink)
      {
        i = OS.SendMessage(this.handle, 4100, 0, 0);
        if ((this.items.length > 4) && (this.items.length - i > 3))
        {
          int j = Math.max(4, (i + 3) / 4 * 4);
          arrayOfTableItem = new TableItem[j];
          System.arraycopy(this.items, 0, arrayOfTableItem, 0, i);
          this.items = arrayOfTableItem;
        }
      }
    }
    else if ((!this.ignoreShrink) && (this.keys.length > 4) && (this.keys.length - this.keyCount > 3))
    {
      i = Math.max(4, (this.keyCount + 3) / 4 * 4);
      int[] arrayOfInt = new int[i];
      System.arraycopy(this.keys, 0, arrayOfInt, 0, this.keyCount);
      this.keys = arrayOfInt;
      arrayOfTableItem = new TableItem[i];
      System.arraycopy(this.items, 0, arrayOfTableItem, 0, this.keyCount);
      this.items = arrayOfTableItem;
    }
  }

  void _clearItems()
  {
    this.items = null;
    this.keys = null;
    this.keyCount = 0;
  }

  TableItem _getItem(int paramInt)
  {
    return _getItem(paramInt, true);
  }

  TableItem _getItem(int paramInt, boolean paramBoolean)
  {
    return _getItem(paramInt, paramBoolean, -1);
  }

  TableItem _getItem(int paramInt1, boolean paramBoolean, int paramInt2)
  {
    if (this.keys == null)
    {
      if (((this.style & 0x10000000) == 0) || (!paramBoolean))
        return this.items[paramInt1];
      if (this.items[paramInt1] != null)
        return this.items[paramInt1];
      return this.items[paramInt1] =  = new TableItem(this, 0, -1, false);
    }
    if (((this.style & 0x10000000) == 0) || (!paramBoolean))
    {
      if (this.keyCount == 0)
        return null;
      if (paramInt1 > this.keys[(this.keyCount - 1)])
        return null;
    }
    int i = binarySearch(this.keys, 0, this.keyCount, paramInt1);
    if (((this.style & 0x10000000) == 0) || (!paramBoolean))
      return i < 0 ? null : this.items[i];
    if (i < 0)
    {
      if (paramInt2 == -1)
        paramInt2 = OS.SendMessage(this.handle, 4100, 0, 0);
      if (_checkGrow(paramInt2))
      {
        if (this.items[paramInt1] != null)
          return this.items[paramInt1];
        return this.items[paramInt1] =  = new TableItem(this, 0, -1, false);
      }
      i = -i - 1;
      if (i < this.keyCount)
      {
        System.arraycopy(this.keys, i, this.keys, i + 1, this.keyCount - i);
        System.arraycopy(this.items, i, this.items, i + 1, this.keyCount - i);
      }
      this.keyCount += 1;
      this.keys[i] = paramInt1;
    }
    else if (this.items[i] != null)
    {
      return this.items[i];
    }
    return this.items[i] =  = new TableItem(this, 0, -1, false);
  }

  void _getItems(TableItem[] paramArrayOfTableItem, int paramInt)
  {
    if (this.keys == null)
      System.arraycopy(this.items, 0, paramArrayOfTableItem, 0, paramInt);
    else
      for (int i = 0; i < this.keyCount; i++)
      {
        if (this.keys[i] >= paramInt)
          break;
        paramArrayOfTableItem[this.keys[i]] = this.items[this.keys[i]];
      }
  }

  boolean _hasItems()
  {
    return this.items != null;
  }

  void _initItems()
  {
    this.items = new TableItem[4];
    if ((COMPRESS_ITEMS) && ((this.style & 0x10000000) != 0))
    {
      this.keyCount = 0;
      this.keys = new int[4];
    }
  }

  void _insertItem(int paramInt1, TableItem paramTableItem, int paramInt2)
  {
    if (this.keys == null)
    {
      System.arraycopy(this.items, paramInt1, this.items, paramInt1 + 1, paramInt2 - paramInt1);
      this.items[paramInt1] = paramTableItem;
    }
    else
    {
      int i = binarySearch(this.keys, 0, this.keyCount, paramInt1);
      if (i < 0)
        i = -i - 1;
      System.arraycopy(this.keys, i, this.keys, i + 1, this.keyCount - i);
      this.keys[i] = paramInt1;
      System.arraycopy(this.items, i, this.items, i + 1, this.keyCount - i);
      this.items[i] = paramTableItem;
      this.keyCount += 1;
      for (int j = i + 1; j < this.keyCount; j++)
        this.keys[j] += 1;
    }
  }

  void _removeItem(int paramInt1, int paramInt2)
  {
    if (this.keys == null)
    {
      System.arraycopy(this.items, paramInt1 + 1, this.items, paramInt1, --paramInt2 - paramInt1);
      this.items[paramInt2] = null;
    }
    else
    {
      int i = binarySearch(this.keys, 0, this.keyCount, paramInt1);
      if (i < 0)
      {
        i = -i - 1;
      }
      else
      {
        this.keyCount -= 1;
        System.arraycopy(this.keys, i + 1, this.keys, i, this.keyCount - i);
        this.keys[this.keyCount] = 0;
        System.arraycopy(this.items, i + 1, this.items, i, this.keyCount - i);
        this.items[this.keyCount] = null;
      }
      for (int j = i; j < this.keyCount; j++)
        this.keys[j] -= 1;
    }
  }

  void _removeItems(int paramInt1, int paramInt2, int paramInt3)
  {
    int i;
    if (this.keys == null)
    {
      System.arraycopy(this.items, paramInt2, this.items, paramInt1, paramInt3 - paramInt2);
      for (i = paramInt3 - (paramInt2 - paramInt1); i < paramInt3; i++)
        this.items[i] = null;
    }
    else
    {
      i = paramInt2;
      int j = binarySearch(this.keys, 0, this.keyCount, paramInt1);
      if (j < 0)
        j = -j - 1;
      int k = binarySearch(this.keys, j, this.keyCount, i);
      if (k < 0)
        k = -k - 1;
      System.arraycopy(this.keys, k, this.keys, j, this.keyCount - k);
      for (int m = this.keyCount - (k - j); m < this.keyCount; m++)
        this.keys[m] = 0;
      System.arraycopy(this.items, k, this.items, j, this.keyCount - k);
      for (m = this.keyCount - (k - j); m < this.keyCount; m++)
        this.items[m] = null;
      this.keyCount -= k - j;
      for (m = j; m < this.keyCount; m++)
        this.keys[m] -= k - j;
    }
  }

  void _setItemCount(int paramInt1, int paramInt2)
  {
    int i;
    if (this.keys == null)
    {
      i = Math.max(4, (paramInt1 + 3) / 4 * 4);
      TableItem[] arrayOfTableItem1 = new TableItem[i];
      System.arraycopy(this.items, 0, arrayOfTableItem1, 0, Math.min(paramInt1, paramInt2));
      this.items = arrayOfTableItem1;
    }
    else
    {
      i = Math.min(paramInt1, paramInt2);
      this.keyCount = binarySearch(this.keys, 0, this.keyCount, i);
      if (this.keyCount < 0)
        this.keyCount = (-this.keyCount - 1);
      int j = Math.max(4, (this.keyCount + 3) / 4 * 4);
      int[] arrayOfInt = new int[j];
      System.arraycopy(this.keys, 0, arrayOfInt, 0, this.keyCount);
      this.keys = arrayOfInt;
      TableItem[] arrayOfTableItem2 = new TableItem[j];
      System.arraycopy(this.items, 0, arrayOfTableItem2, 0, this.keyCount);
      this.items = arrayOfTableItem2;
    }
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
    return callWindowProc(paramInt1, paramInt2, paramInt3, paramInt4, false);
  }

  int callWindowProc(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean)
  {
    if (this.handle == 0)
      return 0;
    if (this.handle != paramInt1)
      return OS.CallWindowProc(HeaderProc, paramInt1, paramInt2, paramInt3, paramInt4);
    int i = 0;
    int j = 0;
    int k = 0;
    int m = 0;
    switch (paramInt2)
    {
    case 256:
      k = 1;
    case 71:
    case 257:
    case 258:
    case 260:
    case 261:
    case 262:
    case 276:
    case 277:
    case 646:
      m = (findImageControl() != null) && (getDrawing()) && (OS.IsWindowVisible(this.handle)) ? 1 : 0;
      if (m != 0)
      {
        OS.DefWindowProc(this.handle, 11, 0, 0);
        OS.SendMessage(this.handle, 4097, 0, 16777215);
      }
    case 512:
    case 513:
    case 514:
    case 515:
    case 516:
    case 517:
    case 518:
    case 519:
    case 520:
    case 521:
    case 522:
    case 523:
    case 524:
    case 525:
    case 673:
    case 675:
      j = 1;
    case 48:
    case 275:
      if (findImageControl() != null)
        i = OS.SendMessage(this.handle, 4135, 0, 0);
      break;
    }
    boolean bool = this.wasSelected;
    if (j != 0)
      this.wasSelected = false;
    if (k != 0)
      this.ignoreActivate = true;
    int n = 0;
    int i6;
    if (paramInt2 == 15)
    {
      i1 = OS.GetWindowLong(this.handle, -16);
      if ((i1 & 0x4000) == 0)
      {
        i2 = OS.GetParent(this.handle);
        int i3 = 0;
        while (i2 != 0)
        {
          i6 = OS.GetWindowLong(i2, -20);
          if ((i6 & 0x2000000) != 0)
          {
            n = 1;
            break;
          }
          i3 = OS.GetWindow(i2, 4);
          if (i3 != 0)
            break;
          i2 = OS.GetParent(i2);
        }
      }
    }
    int i1 = 0;
    if (((this.style & 0x100) == 0) || ((this.style & 0x200) == 0))
      switch (paramInt2)
      {
      case 15:
      case 70:
      case 133:
        i2 = OS.GetWindowLong(paramInt1, -16);
        if (((this.style & 0x100) == 0) && ((i2 & 0x100000) != 0))
        {
          i1 = 1;
          i2 &= -1048577;
        }
        if (((this.style & 0x200) == 0) && ((i2 & 0x200000) != 0))
        {
          i1 = 1;
          i2 &= -2097153;
        }
        if (i1 != 0)
          OS.SetWindowLong(this.handle, -16, i2);
        break;
      }
    int i2 = 0;
    if (n != 0)
    {
      PAINTSTRUCT localPAINTSTRUCT = new PAINTSTRUCT();
      i6 = OS.BeginPaint(paramInt1, localPAINTSTRUCT);
      i2 = OS.CallWindowProc(TableProc, paramInt1, 15, i6, paramInt4);
      OS.EndPaint(paramInt1, localPAINTSTRUCT);
    }
    else
    {
      i2 = OS.CallWindowProc(TableProc, paramInt1, paramInt2, paramInt3, paramInt4);
    }
    if (i1 != 0)
    {
      int i4 = 1025;
      OS.RedrawWindow(this.handle, null, 0, i4);
    }
    if (k != 0)
      this.ignoreActivate = false;
    if (j != 0)
    {
      if ((this.wasSelected) || (paramBoolean))
      {
        Event localEvent = new Event();
        i6 = OS.SendMessage(this.handle, 4108, -1, 1);
        if (i6 != -1)
          localEvent.item = _getItem(i6);
        sendSelectionEvent(13, localEvent, false);
      }
      this.wasSelected = bool;
    }
    switch (paramInt2)
    {
    case 71:
    case 256:
    case 257:
    case 258:
    case 260:
    case 261:
    case 262:
    case 276:
    case 277:
    case 646:
      if (m != 0)
      {
        OS.SendMessage(this.handle, 4097, 0, -1);
        OS.DefWindowProc(this.handle, 11, 1, 0);
        OS.InvalidateRect(this.handle, null, true);
        int i5 = OS.SendMessage(this.handle, 4127, 0, 0);
        if (i5 != 0)
          OS.InvalidateRect(i5, null, true);
      }
    case 48:
    case 275:
    case 512:
    case 513:
    case 514:
    case 515:
    case 516:
    case 517:
    case 518:
    case 519:
    case 520:
    case 521:
    case 522:
    case 523:
    case 524:
    case 525:
    case 673:
    case 675:
      if ((findImageControl() != null) && (i != OS.SendMessage(this.handle, 4135, 0, 0)))
        OS.InvalidateRect(this.handle, null, true);
      break;
    case 15:
      this.painted = true;
    }
    return i2;
  }

  static int checkStyle(int paramInt)
  {
    if ((paramInt & 0x10) == 0)
      paramInt |= 768;
    return checkBits(paramInt, 4, 2, 0, 0, 0, 0);
  }

  LRESULT CDDS_ITEMPOSTPAINT(NMLVCUSTOMDRAW paramNMLVCUSTOMDRAW, int paramInt1, int paramInt2)
  {
    int i = paramNMLVCUSTOMDRAW.hdc;
    if ((this.explorerTheme) && (!this.ignoreCustomDraw))
    {
      this.hotIndex = -1;
      if ((hooks(40)) && (paramNMLVCUSTOMDRAW.left != paramNMLVCUSTOMDRAW.right))
        OS.RestoreDC(i, -1);
    }
    if ((!this.ignoreCustomDraw) && (!this.ignoreDrawFocus) && (paramNMLVCUSTOMDRAW.left != paramNMLVCUSTOMDRAW.right) && (OS.IsWindowVisible(this.handle)) && (OS.IsWindowEnabled(this.handle)) && (!this.explorerTheme) && ((this.style & 0x10000) != 0) && (OS.SendMessage(this.handle, 4096, 0, 0) == -1))
    {
      int j = OS.SendMessage(this.handle, 4151, 0, 0);
      if (((j & 0x20) == 0) && (OS.SendMessage(this.handle, 4108, -1, 1) == paramNMLVCUSTOMDRAW.dwItemSpec) && (this.handle == OS.GetFocus()))
      {
        int k = OS.SendMessage(this.handle, 297, 0, 0);
        if ((k & 0x1) == 0)
        {
          RECT localRECT1 = new RECT();
          localRECT1.left = 0;
          boolean bool = this.ignoreCustomDraw;
          this.ignoreCustomDraw = true;
          OS.SendMessage(this.handle, 4110, paramNMLVCUSTOMDRAW.dwItemSpec, localRECT1);
          int m = OS.SendMessage(this.handle, 4127, 0, 0);
          int n = OS.SendMessage(m, 4623, 0, 0);
          RECT localRECT2 = new RECT();
          if (n == 0)
          {
            localRECT2.left = 2;
            OS.SendMessage(this.handle, 4110, n, localRECT2);
          }
          else
          {
            localRECT2.top = n;
            localRECT2.left = 1;
            OS.SendMessage(this.handle, 4152, paramNMLVCUSTOMDRAW.dwItemSpec, localRECT2);
          }
          this.ignoreCustomDraw = bool;
          localRECT1.left = localRECT2.left;
          OS.DrawFocusRect(paramNMLVCUSTOMDRAW.hdc, localRECT1);
        }
      }
    }
    return null;
  }

  LRESULT CDDS_ITEMPREPAINT(NMLVCUSTOMDRAW paramNMLVCUSTOMDRAW, int paramInt1, int paramInt2)
  {
    int i;
    if ((!this.ignoreCustomDraw) && (OS.IsWindowVisible(this.handle)) && (OS.IsWindowEnabled(this.handle)) && (!this.explorerTheme) && ((this.style & 0x10000) != 0) && (OS.SendMessage(this.handle, 4096, 0, 0) == -1))
    {
      i = OS.SendMessage(this.handle, 4151, 0, 0);
      if (((i & 0x20) == 0) && ((paramNMLVCUSTOMDRAW.uItemState & 0x10) != 0))
      {
        paramNMLVCUSTOMDRAW.uItemState &= -17;
        OS.MoveMemory(paramInt2, paramNMLVCUSTOMDRAW, NMLVCUSTOMDRAW.sizeof);
      }
    }
    if ((this.explorerTheme) && (!this.ignoreCustomDraw))
    {
      this.hotIndex = ((paramNMLVCUSTOMDRAW.uItemState & 0x40) != 0 ? paramNMLVCUSTOMDRAW.dwItemSpec : -1);
      if ((hooks(40)) && (paramNMLVCUSTOMDRAW.left != paramNMLVCUSTOMDRAW.right))
      {
        OS.SaveDC(paramNMLVCUSTOMDRAW.hdc);
        i = OS.CreateRectRgn(0, 0, 0, 0);
        OS.SelectClipRgn(paramNMLVCUSTOMDRAW.hdc, i);
        OS.DeleteObject(i);
      }
    }
    return new LRESULT(48);
  }

  LRESULT CDDS_POSTPAINT(NMLVCUSTOMDRAW paramNMLVCUSTOMDRAW, int paramInt1, int paramInt2)
  {
    if (this.ignoreCustomDraw)
      return null;
    if ((--this.customCount == 0) && (OS.IsWindowVisible(this.handle)) && (!this.explorerTheme) && ((this.style & 0x10000) != 0) && (OS.SendMessage(this.handle, 4096, 0, 0) == -1))
    {
      int i = OS.SendMessage(this.handle, 4151, 0, 0);
      if ((i & 0x20) == 0)
      {
        int j = 32;
        int k = OS.SendMessage(this.handle, 4170, 0, 0);
        if (OS.IsWinCE)
        {
          RECT localRECT = new RECT();
          boolean bool = OS.GetUpdateRect(this.handle, localRECT, true);
          OS.SendMessage(this.handle, 4150, j, j);
          OS.ValidateRect(this.handle, null);
          if (bool)
            OS.InvalidateRect(this.handle, localRECT, true);
        }
        else
        {
          int m = OS.CreateRectRgn(0, 0, 0, 0);
          int n = OS.GetUpdateRgn(this.handle, m, true);
          OS.SendMessage(this.handle, 4150, j, j);
          OS.ValidateRect(this.handle, null);
          if (n != 1)
            OS.InvalidateRgn(this.handle, m, true);
          OS.DeleteObject(m);
        }
        k = OS.SendMessage(this.handle, 4170, k, k);
      }
    }
    return null;
  }

  LRESULT CDDS_PREPAINT(NMLVCUSTOMDRAW paramNMLVCUSTOMDRAW, int paramInt1, int paramInt2)
  {
    if (this.ignoreCustomDraw)
      return new LRESULT(48);
    int i;
    int j;
    int m;
    int n;
    if ((this.customCount++ == 0) && (OS.IsWindowVisible(this.handle)) && (!this.explorerTheme) && ((this.style & 0x10000) != 0) && (OS.SendMessage(this.handle, 4096, 0, 0) == -1))
    {
      i = OS.SendMessage(this.handle, 4151, 0, 0);
      if ((i & 0x20) != 0)
      {
        j = 32;
        int k = OS.SendMessage(this.handle, 4170, 0, 0);
        if (OS.IsWinCE)
        {
          RECT localRECT2 = new RECT();
          boolean bool = OS.GetUpdateRect(this.handle, localRECT2, true);
          OS.SendMessage(this.handle, 4150, j, 0);
          OS.ValidateRect(this.handle, null);
          if (bool)
            OS.InvalidateRect(this.handle, localRECT2, true);
        }
        else
        {
          m = OS.CreateRectRgn(0, 0, 0, 0);
          n = OS.GetUpdateRgn(this.handle, m, true);
          OS.SendMessage(this.handle, 4150, j, 0);
          OS.ValidateRect(this.handle, null);
          if (n != 1)
            OS.InvalidateRgn(this.handle, m, true);
          OS.DeleteObject(m);
        }
        k = OS.SendMessage(this.handle, 4170, k, k);
      }
    }
    if (OS.IsWindowVisible(this.handle))
    {
      i = 1;
      RECT localRECT1;
      if ((this.explorerTheme) && (this.columnCount == 0))
      {
        j = paramNMLVCUSTOMDRAW.hdc;
        localRECT1 = new RECT();
        OS.SetRect(localRECT1, paramNMLVCUSTOMDRAW.left, paramNMLVCUSTOMDRAW.top, paramNMLVCUSTOMDRAW.right, paramNMLVCUSTOMDRAW.bottom);
        if ((OS.IsWindowEnabled(this.handle)) || (findImageControl() != null))
          drawBackground(j, localRECT1);
        else
          fillBackground(j, OS.GetSysColor(OS.COLOR_3DFACE), localRECT1);
        i = 0;
      }
      if (i != 0)
      {
        Object localObject = findBackgroundControl();
        if ((localObject != null) && (((Control)localObject).backgroundImage != null))
        {
          localRECT1 = new RECT();
          OS.SetRect(localRECT1, paramNMLVCUSTOMDRAW.left, paramNMLVCUSTOMDRAW.top, paramNMLVCUSTOMDRAW.right, paramNMLVCUSTOMDRAW.bottom);
          fillImageBackground(paramNMLVCUSTOMDRAW.hdc, (Control)localObject, localRECT1, 0, 0);
        }
        else if ((OS.SendMessage(this.handle, 4096, 0, 0) == -1) && (OS.IsWindowEnabled(this.handle)))
        {
          localRECT1 = new RECT();
          OS.SetRect(localRECT1, paramNMLVCUSTOMDRAW.left, paramNMLVCUSTOMDRAW.top, paramNMLVCUSTOMDRAW.right, paramNMLVCUSTOMDRAW.bottom);
          if (localObject == null)
            localObject = this;
          fillBackground(paramNMLVCUSTOMDRAW.hdc, ((Control)localObject).getBackgroundPixel(), localRECT1);
          if ((OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()) && (this.sortColumn != null) && (this.sortDirection != 0))
          {
            m = indexOf(this.sortColumn);
            if (m != -1)
            {
              this.parent.forceResize();
              n = getSortColumnPixel();
              RECT localRECT3 = new RECT();
              RECT localRECT4 = new RECT();
              OS.GetClientRect(this.handle, localRECT3);
              int i1 = OS.SendMessage(this.handle, 4127, 0, 0);
              if (OS.SendMessage(i1, 4615, m, localRECT4) != 0)
              {
                OS.MapWindowPoints(i1, this.handle, localRECT4, 2);
                localRECT3.left = localRECT4.left;
                localRECT3.right = localRECT4.right;
                if (OS.IntersectRect(localRECT3, localRECT3, localRECT1))
                  fillBackground(paramNMLVCUSTOMDRAW.hdc, n, localRECT3);
              }
            }
          }
        }
      }
    }
    return new LRESULT(48);
  }

  LRESULT CDDS_SUBITEMPOSTPAINT(NMLVCUSTOMDRAW paramNMLVCUSTOMDRAW, int paramInt1, int paramInt2)
  {
    if (this.ignoreCustomDraw)
      return null;
    if (paramNMLVCUSTOMDRAW.left == paramNMLVCUSTOMDRAW.right)
      return new LRESULT(0);
    int i = paramNMLVCUSTOMDRAW.hdc;
    if (this.ignoreDrawForeground)
      OS.RestoreDC(i, -1);
    if (OS.IsWindowVisible(this.handle))
    {
      if ((OS.SendMessage(this.handle, 4096, 0, 0) != -1) && ((this.sortDirection & 0x480) != 0) && (this.sortColumn != null) && (!this.sortColumn.isDisposed()))
      {
        int j = OS.SendMessage(this.handle, 4270, 0, 0);
        if (j == -1)
        {
          int k = indexOf(this.sortColumn);
          int m = 0;
          int n = 0;
          if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(6, 0)))
          {
            n = OS.CreateRectRgn(0, 0, 0, 0);
            m = OS.GetUpdateRgn(this.handle, n, true);
          }
          OS.SendMessage(this.handle, 4236, k, 0);
          if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(6, 0)))
          {
            OS.ValidateRect(this.handle, null);
            if (m != 1)
              OS.InvalidateRgn(this.handle, n, true);
            OS.DeleteObject(n);
          }
        }
      }
      if (hooks(42))
      {
        TableItem localTableItem = _getItem(paramNMLVCUSTOMDRAW.dwItemSpec);
        sendPaintItemEvent(localTableItem, paramNMLVCUSTOMDRAW);
      }
      if ((!this.ignoreDrawFocus) && (this.focusRect != null))
      {
        OS.SetTextColor(paramNMLVCUSTOMDRAW.hdc, 0);
        OS.SetBkColor(paramNMLVCUSTOMDRAW.hdc, 16777215);
        OS.DrawFocusRect(paramNMLVCUSTOMDRAW.hdc, this.focusRect);
        this.focusRect = null;
      }
    }
    return null;
  }

  LRESULT CDDS_SUBITEMPREPAINT(NMLVCUSTOMDRAW paramNMLVCUSTOMDRAW, int paramInt1, int paramInt2)
  {
    int i = paramNMLVCUSTOMDRAW.hdc;
    if ((this.explorerTheme) && (!this.ignoreCustomDraw) && (hooks(40)) && (paramNMLVCUSTOMDRAW.left != paramNMLVCUSTOMDRAW.right))
      OS.RestoreDC(i, -1);
    TableItem localTableItem = _getItem(paramNMLVCUSTOMDRAW.dwItemSpec);
    if ((localTableItem == null) || (localTableItem.isDisposed()))
      return null;
    int j = localTableItem.fontHandle(paramNMLVCUSTOMDRAW.iSubItem);
    if (j != -1)
      OS.SelectObject(i, j);
    if ((this.ignoreCustomDraw) || (paramNMLVCUSTOMDRAW.left == paramNMLVCUSTOMDRAW.right))
      return new LRESULT(j == -1 ? 0 : 2);
    int k = 0;
    this.selectionForeground = -1;
    this.ignoreDrawForeground = (this.ignoreDrawSelection = this.ignoreDrawFocus = this.ignoreDrawBackground = 0);
    if (OS.IsWindowVisible(this.handle))
    {
      Event localEvent = null;
      if (hooks(41))
      {
        localEvent = sendMeasureItemEvent(localTableItem, paramNMLVCUSTOMDRAW.dwItemSpec, paramNMLVCUSTOMDRAW.iSubItem, paramNMLVCUSTOMDRAW.hdc);
        if ((isDisposed()) || (localTableItem.isDisposed()))
          return null;
      }
      if (hooks(40))
      {
        sendEraseItemEvent(localTableItem, paramNMLVCUSTOMDRAW, paramInt2, localEvent);
        if ((isDisposed()) || (localTableItem.isDisposed()))
          return null;
        k |= 16;
      }
      if ((this.ignoreDrawForeground) || (hooks(42)))
        k |= 16;
    }
    int m = localTableItem.cellForeground != null ? localTableItem.cellForeground[paramNMLVCUSTOMDRAW.iSubItem] : -1;
    if (m == -1)
      m = localTableItem.foreground;
    int n = localTableItem.cellBackground != null ? localTableItem.cellBackground[paramNMLVCUSTOMDRAW.iSubItem] : -1;
    if (n == -1)
      n = localTableItem.background;
    if (this.selectionForeground != -1)
      m = this.selectionForeground;
    int i1;
    int i4;
    if ((OS.IsWindowVisible(this.handle)) && (OS.IsWindowEnabled(this.handle)) && (!this.explorerTheme) && (!this.ignoreDrawSelection) && ((this.style & 0x10000) != 0))
    {
      i1 = OS.SendMessage(this.handle, 4151, 0, 0);
      if ((i1 & 0x20) == 0)
      {
        LVITEM localLVITEM = new LVITEM();
        localLVITEM.mask = 8;
        localLVITEM.stateMask = 2;
        localLVITEM.iItem = paramNMLVCUSTOMDRAW.dwItemSpec;
        i4 = OS.SendMessage(this.handle, OS.LVM_GETITEM, 0, localLVITEM);
        if ((i4 != 0) && ((localLVITEM.state & 0x2) != 0))
        {
          int i5 = -1;
          if (paramNMLVCUSTOMDRAW.iSubItem == 0)
          {
            if ((OS.GetFocus() == this.handle) || (this.display.getHighContrast()))
              i5 = OS.GetSysColor(OS.COLOR_HIGHLIGHT);
            else if ((this.style & 0x8000) == 0)
              i5 = OS.GetSysColor(OS.COLOR_3DFACE);
          }
          else if ((OS.GetFocus() == this.handle) || (this.display.getHighContrast()))
          {
            m = OS.GetSysColor(OS.COLOR_HIGHLIGHTTEXT);
            n = i5 = OS.GetSysColor(OS.COLOR_HIGHLIGHT);
          }
          else if ((this.style & 0x8000) == 0)
          {
            n = i5 = OS.GetSysColor(OS.COLOR_3DFACE);
          }
          if (i5 != -1)
          {
            RECT localRECT = localTableItem.getBounds(paramNMLVCUSTOMDRAW.dwItemSpec, paramNMLVCUSTOMDRAW.iSubItem, true, paramNMLVCUSTOMDRAW.iSubItem != 0, true, false, i);
            fillBackground(i, i5, localRECT);
          }
        }
      }
    }
    if (!this.ignoreDrawForeground)
    {
      i1 = 1;
      if ((j == -1) && (m == -1) && (n == -1) && (localTableItem.cellForeground == null) && (localTableItem.cellBackground == null) && (localTableItem.cellFont == null))
      {
        int i2 = OS.SendMessage(this.handle, 4127, 0, 0);
        i4 = OS.SendMessage(i2, 4608, 0, 0);
        if (i4 == 1)
          i1 = 0;
      }
      if (i1 != 0)
      {
        if (j == -1)
          j = OS.SendMessage(this.handle, 49, 0, 0);
        OS.SelectObject(i, j);
        if (OS.IsWindowEnabled(this.handle))
        {
          paramNMLVCUSTOMDRAW.clrText = (m == -1 ? getForegroundPixel() : m);
          if (n == -1)
          {
            paramNMLVCUSTOMDRAW.clrTextBk = -1;
            if (this.selectionForeground == -1)
            {
              Object localObject = findBackgroundControl();
              if (localObject == null)
                localObject = this;
              if ((((Control)localObject).backgroundImage == null) && (OS.SendMessage(this.handle, 4096, 0, 0) != -1))
                paramNMLVCUSTOMDRAW.clrTextBk = ((Control)localObject).getBackgroundPixel();
            }
          }
          else
          {
            paramNMLVCUSTOMDRAW.clrTextBk = (this.selectionForeground != -1 ? -1 : n);
          }
          OS.MoveMemory(paramInt2, paramNMLVCUSTOMDRAW, NMLVCUSTOMDRAW.sizeof);
        }
        k |= 2;
      }
    }
    if (OS.IsWindowEnabled(this.handle))
    {
      if (n != -1)
      {
        i1 = OS.SendMessage(this.handle, 4270, 0, 0);
        if ((i1 != -1) && (i1 == paramNMLVCUSTOMDRAW.iSubItem))
        {
          int i3 = 0;
          i4 = 0;
          if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(6, 0)))
          {
            i4 = OS.CreateRectRgn(0, 0, 0, 0);
            i3 = OS.GetUpdateRgn(this.handle, i4, true);
          }
          OS.SendMessage(this.handle, 4236, -1, 0);
          if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(6, 0)))
          {
            OS.ValidateRect(this.handle, null);
            if (i3 != 1)
              OS.InvalidateRgn(this.handle, i4, true);
            OS.DeleteObject(i4);
          }
          k |= 16;
        }
      }
    }
    else
    {
      paramNMLVCUSTOMDRAW.clrText = OS.GetSysColor(OS.COLOR_GRAYTEXT);
      if (findImageControl() != null)
        paramNMLVCUSTOMDRAW.clrTextBk = -1;
      else
        paramNMLVCUSTOMDRAW.clrTextBk = OS.GetSysColor(OS.COLOR_3DFACE);
      paramNMLVCUSTOMDRAW.uItemState &= -2;
      OS.MoveMemory(paramInt2, paramNMLVCUSTOMDRAW, NMLVCUSTOMDRAW.sizeof);
      k |= 2;
    }
    return new LRESULT(k);
  }

  void checkBuffered()
  {
    super.checkBuffered();
    if (OS.COMCTL32_MAJOR >= 6)
      this.style |= 536870912;
    if ((this.style & 0x10000000) != 0)
      this.style |= 536870912;
  }

  boolean checkData(TableItem paramTableItem, boolean paramBoolean)
  {
    if ((this.style & 0x10000000) == 0)
      return true;
    return checkData(paramTableItem, indexOf(paramTableItem), paramBoolean);
  }

  boolean checkData(TableItem paramTableItem, int paramInt, boolean paramBoolean)
  {
    if ((this.style & 0x10000000) == 0)
      return true;
    if (!paramTableItem.cached)
    {
      paramTableItem.cached = true;
      Event localEvent = new Event();
      localEvent.item = paramTableItem;
      localEvent.index = paramInt;
      this.currentItem = paramTableItem;
      sendEvent(36, localEvent);
      this.currentItem = null;
      if ((isDisposed()) || (paramTableItem.isDisposed()))
        return false;
      if ((paramBoolean) && (!setScrollWidth(paramTableItem, false)))
        paramTableItem.redraw();
    }
    return true;
  }

  boolean checkHandle(int paramInt)
  {
    if (paramInt == this.handle)
      return true;
    return paramInt == OS.SendMessage(this.handle, 4127, 0, 0);
  }

  protected void checkSubclass()
  {
    if (!isValidSubclass())
      error(43);
  }

  public void clear(int paramInt)
  {
    checkWidget();
    int i = OS.SendMessage(this.handle, 4100, 0, 0);
    if ((paramInt < 0) || (paramInt >= i))
      error(6);
    TableItem localTableItem = _getItem(paramInt, false);
    if (localTableItem != null)
    {
      if (localTableItem != this.currentItem)
        localTableItem.clear();
      if (((this.style & 0x10000000) == 0) && (localTableItem.cached))
      {
        LVITEM localLVITEM = new LVITEM();
        localLVITEM.mask = 17;
        localLVITEM.pszText = -1;
        localLVITEM.iItem = paramInt;
        OS.SendMessage(this.handle, OS.LVM_SETITEM, 0, localLVITEM);
        localTableItem.cached = false;
      }
      if ((this.currentItem == null) && (getDrawing()) && (OS.IsWindowVisible(this.handle)))
        OS.SendMessage(this.handle, 4117, paramInt, paramInt);
      setScrollWidth(localTableItem, false);
    }
  }

  public void clear(int paramInt1, int paramInt2)
  {
    checkWidget();
    if (paramInt1 > paramInt2)
      return;
    int i = OS.SendMessage(this.handle, 4100, 0, 0);
    if ((paramInt1 < 0) || (paramInt1 > paramInt2) || (paramInt2 >= i))
      error(6);
    if ((paramInt1 == 0) && (paramInt2 == i - 1))
    {
      clearAll();
    }
    else
    {
      LVITEM localLVITEM = null;
      int j = 0;
      for (int k = paramInt1; k <= paramInt2; k++)
      {
        TableItem localTableItem2 = _getItem(k, false);
        if (localTableItem2 != null)
        {
          if (localTableItem2 != this.currentItem)
          {
            j = 1;
            localTableItem2.clear();
          }
          if (((this.style & 0x10000000) == 0) && (localTableItem2.cached))
          {
            if (localLVITEM == null)
            {
              localLVITEM = new LVITEM();
              localLVITEM.mask = 17;
              localLVITEM.pszText = -1;
            }
            localLVITEM.iItem = k;
            OS.SendMessage(this.handle, OS.LVM_SETITEM, 0, localLVITEM);
            localTableItem2.cached = false;
          }
        }
      }
      if (j != 0)
      {
        if ((this.currentItem == null) && (getDrawing()) && (OS.IsWindowVisible(this.handle)))
          OS.SendMessage(this.handle, 4117, paramInt1, paramInt2);
        TableItem localTableItem1 = paramInt1 == paramInt2 ? _getItem(paramInt1, false) : null;
        setScrollWidth(localTableItem1, false);
      }
    }
  }

  public void clear(int[] paramArrayOfInt)
  {
    checkWidget();
    if (paramArrayOfInt == null)
      error(4);
    if (paramArrayOfInt.length == 0)
      return;
    int i = OS.SendMessage(this.handle, 4100, 0, 0);
    for (int j = 0; j < paramArrayOfInt.length; j++)
      if ((paramArrayOfInt[j] < 0) || (paramArrayOfInt[j] >= i))
        error(6);
    LVITEM localLVITEM = null;
    int k = 0;
    for (int m = 0; m < paramArrayOfInt.length; m++)
    {
      int n = paramArrayOfInt[m];
      TableItem localTableItem = _getItem(n, false);
      if (localTableItem != null)
      {
        if (localTableItem != this.currentItem)
        {
          k = 1;
          localTableItem.clear();
        }
        if (((this.style & 0x10000000) == 0) && (localTableItem.cached))
        {
          if (localLVITEM == null)
          {
            localLVITEM = new LVITEM();
            localLVITEM.mask = 17;
            localLVITEM.pszText = -1;
          }
          localLVITEM.iItem = m;
          OS.SendMessage(this.handle, OS.LVM_SETITEM, 0, localLVITEM);
          localTableItem.cached = false;
        }
        if ((this.currentItem == null) && (getDrawing()) && (OS.IsWindowVisible(this.handle)))
          OS.SendMessage(this.handle, 4117, n, n);
      }
    }
    if (k != 0)
      setScrollWidth(null, false);
  }

  public void clearAll()
  {
    checkWidget();
    LVITEM localLVITEM = null;
    int i = 0;
    int j = OS.SendMessage(this.handle, 4100, 0, 0);
    for (int k = 0; k < j; k++)
    {
      TableItem localTableItem = _getItem(k, false);
      if (localTableItem != null)
      {
        if (localTableItem != this.currentItem)
        {
          i = 1;
          localTableItem.clear();
        }
        if (((this.style & 0x10000000) == 0) && (localTableItem.cached))
        {
          if (localLVITEM == null)
          {
            localLVITEM = new LVITEM();
            localLVITEM.mask = 17;
            localLVITEM.pszText = -1;
          }
          localLVITEM.iItem = k;
          OS.SendMessage(this.handle, OS.LVM_SETITEM, 0, localLVITEM);
          localTableItem.cached = false;
        }
      }
    }
    if (i != 0)
    {
      if ((this.currentItem == null) && (getDrawing()) && (OS.IsWindowVisible(this.handle)))
        OS.SendMessage(this.handle, 4117, 0, j - 1);
      setScrollWidth(null, false);
    }
  }

  public Point computeSize(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    checkWidget();
    if (this.fixScrollWidth)
      setScrollWidth(null, true);
    int i = OS.SendMessage(this.handle, 4127, 0, 0);
    RECT localRECT = new RECT();
    OS.GetWindowRect(i, localRECT);
    int j = localRECT.bottom - localRECT.top;
    int k = 0;
    if (paramInt1 != -1)
    {
      k |= paramInt1 & 0xFFFF;
    }
    else
    {
      m = 0;
      n = OS.SendMessage(i, 4608, 0, 0);
      for (i1 = 0; i1 < n; i1++)
        m += OS.SendMessage(this.handle, 4125, i1, 0);
      k |= m & 0xFFFF;
    }
    int m = OS.SendMessage(this.handle, 4160, -1, OS.MAKELPARAM(k, 65535));
    int n = OS.LOWORD(m);
    int i1 = OS.SendMessage(this.handle, 4160, 0, 0);
    int i2 = OS.SendMessage(this.handle, 4160, 1, 0);
    int i3 = OS.HIWORD(i2) - OS.HIWORD(i1);
    j += OS.SendMessage(this.handle, 4100, 0, 0) * i3;
    if (n == 0)
      n = 64;
    if (j == 0)
      j = 64;
    if (paramInt1 != -1)
      n = paramInt1;
    if (paramInt2 != -1)
      j = paramInt2;
    int i4 = getBorderWidth();
    n += i4 * 2;
    j += i4 * 2;
    if ((this.style & 0x200) != 0)
      n += OS.GetSystemMetrics(2);
    if ((this.style & 0x100) != 0)
      j += OS.GetSystemMetrics(3);
    return new Point(n, j);
  }

  void createHandle()
  {
    super.createHandle();
    this.state &= -259;
    if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(6, 0)) && (OS.IsAppThemed()))
    {
      this.explorerTheme = true;
      OS.SetWindowTheme(this.handle, Display.EXPLORER, null);
    }
    if (HeaderProc == 0)
    {
      i = OS.SendMessage(this.handle, 4127, 0, 0);
      HeaderProc = OS.GetWindowLongPtr(i, -4);
    }
    if ((!OS.IsWinCE) && (OS.COMCTL32_MAJOR < 6))
      OS.SendMessage(this.handle, 8199, 5, 0);
    if ((this.style & 0x20) != 0)
    {
      i = OS.SendMessage(this.handle, 4160, 0, 0);
      int j = OS.SendMessage(this.handle, 4160, 1, 0);
      k = OS.HIWORD(j) - OS.HIWORD(i);
      m = k;
      setCheckboxImageList(k, m, false);
      OS.SendMessage(this.handle, 4107, 61440, 0);
    }
    int i = OS.GetStockObject(13);
    OS.SendMessage(this.handle, 48, i, 0);
    LVCOLUMN localLVCOLUMN = new LVCOLUMN();
    localLVCOLUMN.mask = 6;
    int k = OS.GetProcessHeap();
    int m = OS.HeapAlloc(k, 8, TCHAR.sizeof);
    localLVCOLUMN.pszText = m;
    OS.SendMessage(this.handle, OS.LVM_INSERTCOLUMN, 0, localLVCOLUMN);
    OS.HeapFree(k, 0, m);
    int n = 16384;
    if ((this.style & 0x10000) != 0)
      n |= 32;
    if (OS.COMCTL32_MAJOR >= 6)
      n |= 65536;
    OS.SendMessage(this.handle, 4150, n, n);
    if ((OS.WIN32_VERSION >= OS.VERSION(4, 10)) && ((this.style & 0x4000000) != 0))
    {
      int i1 = OS.SendMessage(this.handle, 4127, 0, 0);
      int i2 = OS.GetWindowLong(i1, -20);
      OS.SetWindowLong(i1, -20, i2 | 0x400000);
      int i3 = OS.SendMessage(this.handle, 4174, 0, 0);
      int i4 = OS.GetWindowLong(i3, -20);
      OS.SetWindowLong(i3, -20, i4 | 0x400000);
    }
  }

  void createHeaderToolTips()
  {
    if (OS.IsWinCE)
      return;
    if (this.headerToolTipHandle != 0)
      return;
    int i = 0;
    if ((OS.WIN32_VERSION >= OS.VERSION(4, 10)) && ((this.style & 0x4000000) != 0))
      i |= 4194304;
    this.headerToolTipHandle = OS.CreateWindowEx(i, new TCHAR(0, "tooltips_class32", true), null, 2, -2147483648, 0, -2147483648, 0, this.handle, 0, OS.GetModuleHandle(null), null);
    if (this.headerToolTipHandle == 0)
      error(2);
    OS.SendMessage(this.headerToolTipHandle, 1048, 0, 32767);
  }

  void createItem(TableColumn paramTableColumn, int paramInt)
  {
    if ((paramInt < 0) || (paramInt > this.columnCount))
      error(6);
    int i = OS.SendMessage(this.handle, 4270, 0, 0);
    if (i >= paramInt)
      OS.SendMessage(this.handle, 4236, i + 1, 0);
    if (this.columnCount == this.columns.length)
    {
      TableColumn[] arrayOfTableColumn = new TableColumn[this.columns.length + 4];
      System.arraycopy(this.columns, 0, arrayOfTableColumn, 0, this.columns.length);
      this.columns = arrayOfTableColumn;
    }
    int j = OS.SendMessage(this.handle, 4100, 0, 0);
    for (int k = 0; k < j; k++)
    {
      TableItem localTableItem = _getItem(k, false);
      if (localTableItem != null)
      {
        String[] arrayOfString = localTableItem.strings;
        if (arrayOfString != null)
        {
          localObject2 = new String[this.columnCount + 1];
          System.arraycopy(arrayOfString, 0, localObject2, 0, paramInt);
          System.arraycopy(arrayOfString, paramInt, localObject2, paramInt + 1, this.columnCount - paramInt);
          localTableItem.strings = ((String[])localObject2);
        }
        Object localObject2 = localTableItem.images;
        Object localObject3;
        if (localObject2 != null)
        {
          localObject3 = new Image[this.columnCount + 1];
          System.arraycopy(localObject2, 0, localObject3, 0, paramInt);
          System.arraycopy(localObject2, paramInt, localObject3, paramInt + 1, this.columnCount - paramInt);
          localTableItem.images = ((Image[])localObject3);
        }
        if ((paramInt == 0) && (this.columnCount != 0))
        {
          if (arrayOfString == null)
          {
            localTableItem.strings = new String[this.columnCount + 1];
            localTableItem.strings[1] = localTableItem.text;
          }
          localTableItem.text = "";
          if (localObject2 == null)
          {
            localTableItem.images = new Image[this.columnCount + 1];
            localTableItem.images[1] = localTableItem.image;
          }
          localTableItem.image = null;
        }
        Object localObject4;
        if (localTableItem.cellBackground != null)
        {
          localObject3 = localTableItem.cellBackground;
          localObject4 = new int[this.columnCount + 1];
          System.arraycopy(localObject3, 0, localObject4, 0, paramInt);
          System.arraycopy(localObject3, paramInt, localObject4, paramInt + 1, this.columnCount - paramInt);
          localObject4[paramInt] = -1;
          localTableItem.cellBackground = ((int[])localObject4);
        }
        if (localTableItem.cellForeground != null)
        {
          localObject3 = localTableItem.cellForeground;
          localObject4 = new int[this.columnCount + 1];
          System.arraycopy(localObject3, 0, localObject4, 0, paramInt);
          System.arraycopy(localObject3, paramInt, localObject4, paramInt + 1, this.columnCount - paramInt);
          localObject4[paramInt] = -1;
          localTableItem.cellForeground = ((int[])localObject4);
        }
        if (localTableItem.cellFont != null)
        {
          localObject3 = localTableItem.cellFont;
          localObject4 = new Font[this.columnCount + 1];
          System.arraycopy(localObject3, 0, localObject4, 0, paramInt);
          System.arraycopy(localObject3, paramInt, localObject4, paramInt + 1, this.columnCount - paramInt);
          localTableItem.cellFont = ((Font[])localObject4);
        }
      }
    }
    System.arraycopy(this.columns, paramInt, this.columns, paramInt + 1, this.columnCount++ - paramInt);
    this.columns[paramInt] = paramTableColumn;
    this.ignoreColumnResize = true;
    if (paramInt == 0)
    {
      Object localObject1;
      int n;
      if (this.columnCount > 1)
      {
        localObject1 = new LVCOLUMN();
        ((LVCOLUMN)localObject1).mask = 2;
        OS.SendMessage(this.handle, OS.LVM_INSERTCOLUMN, 1, (LVCOLUMN)localObject1);
        OS.SendMessage(this.handle, OS.LVM_GETCOLUMN, 1, (LVCOLUMN)localObject1);
        n = ((LVCOLUMN)localObject1).cx;
        int i2 = 1024;
        int i3 = OS.GetProcessHeap();
        int i4 = i2 * TCHAR.sizeof;
        int i5 = OS.HeapAlloc(i3, 8, i4);
        ((LVCOLUMN)localObject1).mask = 23;
        ((LVCOLUMN)localObject1).pszText = i5;
        ((LVCOLUMN)localObject1).cchTextMax = i2;
        OS.SendMessage(this.handle, OS.LVM_GETCOLUMN, 0, (LVCOLUMN)localObject1);
        OS.SendMessage(this.handle, OS.LVM_SETCOLUMN, 1, (LVCOLUMN)localObject1);
        ((LVCOLUMN)localObject1).fmt = 2048;
        ((LVCOLUMN)localObject1).cx = n;
        ((LVCOLUMN)localObject1).iImage = -2;
        ((LVCOLUMN)localObject1).pszText = (((LVCOLUMN)localObject1).cchTextMax = 0);
        OS.SendMessage(this.handle, OS.LVM_SETCOLUMN, 0, (LVCOLUMN)localObject1);
        ((LVCOLUMN)localObject1).mask = 1;
        ((LVCOLUMN)localObject1).fmt = 0;
        OS.SendMessage(this.handle, OS.LVM_SETCOLUMN, 0, (LVCOLUMN)localObject1);
        if (i5 != 0)
          OS.HeapFree(i3, 0, i5);
      }
      else
      {
        OS.SendMessage(this.handle, 4126, 0, 0);
      }
      if ((this.style & 0x10000000) == 0)
      {
        localObject1 = new LVITEM();
        ((LVITEM)localObject1).mask = 3;
        ((LVITEM)localObject1).pszText = -1;
        ((LVITEM)localObject1).iImage = -1;
        for (n = 0; n < j; n++)
        {
          ((LVITEM)localObject1).iItem = n;
          OS.SendMessage(this.handle, OS.LVM_SETITEM, 0, (LVITEM)localObject1);
        }
      }
    }
    else
    {
      int m = 0;
      if ((paramTableColumn.style & 0x1000000) == 16777216)
        m = 2;
      if ((paramTableColumn.style & 0x20000) == 131072)
        m = 1;
      LVCOLUMN localLVCOLUMN = new LVCOLUMN();
      localLVCOLUMN.mask = 3;
      localLVCOLUMN.fmt = m;
      OS.SendMessage(this.handle, OS.LVM_INSERTCOLUMN, paramInt, localLVCOLUMN);
    }
    this.ignoreColumnResize = false;
    if (this.headerToolTipHandle != 0)
    {
      RECT localRECT = new RECT();
      int i1 = OS.SendMessage(this.handle, 4127, 0, 0);
      if (OS.SendMessage(i1, 4615, paramInt, localRECT) != 0)
      {
        TOOLINFO localTOOLINFO = new TOOLINFO();
        localTOOLINFO.cbSize = TOOLINFO.sizeof;
        localTOOLINFO.uFlags = 16;
        localTOOLINFO.hwnd = i1;
        localTOOLINFO.uId = (paramTableColumn.id = this.display.nextToolTipId++);
        localTOOLINFO.left = localRECT.left;
        localTOOLINFO.top = localRECT.top;
        localTOOLINFO.right = localRECT.right;
        localTOOLINFO.bottom = localRECT.bottom;
        localTOOLINFO.lpszText = -1;
        OS.SendMessage(this.headerToolTipHandle, OS.TTM_ADDTOOL, 0, localTOOLINFO);
      }
    }
  }

  void createItem(TableItem paramTableItem, int paramInt)
  {
    int i = OS.SendMessage(this.handle, 4100, 0, 0);
    if ((paramInt < 0) || (paramInt > i))
      error(6);
    _checkGrow(i);
    LVITEM localLVITEM = new LVITEM();
    localLVITEM.mask = 3;
    localLVITEM.iItem = paramInt;
    localLVITEM.pszText = -1;
    localLVITEM.iImage = -1;
    setDeferResize(true);
    this.ignoreSelect = (this.ignoreShrink = 1);
    int j = OS.SendMessage(this.handle, OS.LVM_INSERTITEM, 0, localLVITEM);
    this.ignoreSelect = (this.ignoreShrink = 0);
    if (j == -1)
      error(14);
    _insertItem(paramInt, paramTableItem, i);
    setDeferResize(false);
    if (i == 0)
      setScrollWidth(paramTableItem, false);
  }

  void createWidget()
  {
    super.createWidget();
    this.itemHeight = (this.hotIndex = -1);
    _initItems();
    this.columns = new TableColumn[4];
  }

  int defaultBackground()
  {
    return OS.GetSysColor(OS.COLOR_WINDOW);
  }

  void deregister()
  {
    super.deregister();
    int i = OS.SendMessage(this.handle, 4127, 0, 0);
    if (i != 0)
      this.display.removeControl(i);
  }

  public void deselect(int[] paramArrayOfInt)
  {
    checkWidget();
    if (paramArrayOfInt == null)
      error(4);
    if (paramArrayOfInt.length == 0)
      return;
    LVITEM localLVITEM = new LVITEM();
    localLVITEM.stateMask = 2;
    for (int i = 0; i < paramArrayOfInt.length; i++)
      if (paramArrayOfInt[i] >= 0)
      {
        this.ignoreSelect = true;
        OS.SendMessage(this.handle, 4139, paramArrayOfInt[i], localLVITEM);
        this.ignoreSelect = false;
      }
  }

  public void deselect(int paramInt)
  {
    checkWidget();
    if (paramInt < 0)
      return;
    LVITEM localLVITEM = new LVITEM();
    localLVITEM.stateMask = 2;
    this.ignoreSelect = true;
    OS.SendMessage(this.handle, 4139, paramInt, localLVITEM);
    this.ignoreSelect = false;
  }

  public void deselect(int paramInt1, int paramInt2)
  {
    checkWidget();
    int i = OS.SendMessage(this.handle, 4100, 0, 0);
    if ((paramInt1 == 0) && (paramInt2 == i - 1))
    {
      deselectAll();
    }
    else
    {
      LVITEM localLVITEM = new LVITEM();
      localLVITEM.stateMask = 2;
      paramInt1 = Math.max(0, paramInt1);
      for (int j = paramInt1; j <= paramInt2; j++)
      {
        this.ignoreSelect = true;
        OS.SendMessage(this.handle, 4139, j, localLVITEM);
        this.ignoreSelect = false;
      }
    }
  }

  public void deselectAll()
  {
    checkWidget();
    LVITEM localLVITEM = new LVITEM();
    localLVITEM.mask = 8;
    localLVITEM.stateMask = 2;
    this.ignoreSelect = true;
    OS.SendMessage(this.handle, 4139, -1, localLVITEM);
    this.ignoreSelect = false;
  }

  void destroyItem(TableColumn paramTableColumn)
  {
    for (int i = 0; i < this.columnCount; i++)
      if (this.columns[i] == paramTableColumn)
        break;
    int j = OS.SendMessage(this.handle, 4270, 0, 0);
    if (j == i)
      OS.SendMessage(this.handle, 4236, -1, 0);
    else if (j > i)
      OS.SendMessage(this.handle, 4236, j - 1, 0);
    int k = 0;
    int[] arrayOfInt = new int[this.columnCount];
    OS.SendMessage(this.handle, 4155, this.columnCount, arrayOfInt);
    while (k < this.columnCount)
    {
      if (arrayOfInt[k] == i)
        break;
      k++;
    }
    this.ignoreColumnResize = true;
    int m = 0;
    Object localObject1;
    if (i == 0)
    {
      m = 1;
      setRedraw(false);
      int n;
      if (this.columnCount > 1)
      {
        i = 1;
        n = 1024;
        i2 = OS.GetProcessHeap();
        int i3 = n * TCHAR.sizeof;
        int i6 = OS.HeapAlloc(i2, 8, i3);
        LVCOLUMN localLVCOLUMN2 = new LVCOLUMN();
        localLVCOLUMN2.mask = 23;
        localLVCOLUMN2.pszText = i6;
        localLVCOLUMN2.cchTextMax = n;
        OS.SendMessage(this.handle, OS.LVM_GETCOLUMN, 1, localLVCOLUMN2);
        localLVCOLUMN2.fmt &= -4;
        localLVCOLUMN2.fmt |= 0;
        OS.SendMessage(this.handle, OS.LVM_SETCOLUMN, 0, localLVCOLUMN2);
        if (i6 != 0)
          OS.HeapFree(i2, 0, i6);
      }
      else
      {
        n = OS.GetProcessHeap();
        i2 = OS.HeapAlloc(n, 8, TCHAR.sizeof);
        LVCOLUMN localLVCOLUMN1 = new LVCOLUMN();
        localLVCOLUMN1.mask = 23;
        localLVCOLUMN1.pszText = i2;
        localLVCOLUMN1.iImage = -2;
        localLVCOLUMN1.fmt = 0;
        OS.SendMessage(this.handle, OS.LVM_SETCOLUMN, 0, localLVCOLUMN1);
        if (i2 != 0)
          OS.HeapFree(n, 0, i2);
        if (OS.COMCTL32_MAJOR >= 6)
        {
          localObject1 = new HDITEM();
          ((HDITEM)localObject1).mask = 4;
          ((HDITEM)localObject1).fmt = 0;
          int i7 = OS.SendMessage(this.handle, 4127, 0, 0);
          OS.SendMessage(i7, OS.HDM_SETITEM, i, (HDITEM)localObject1);
        }
      }
      setRedraw(true);
      if ((this.style & 0x10000000) == 0)
      {
        LVITEM localLVITEM = new LVITEM();
        localLVITEM.mask = 3;
        localLVITEM.pszText = -1;
        localLVITEM.iImage = -1;
        i2 = OS.SendMessage(this.handle, 4100, 0, 0);
        for (int i4 = 0; i4 < i2; i4++)
        {
          localLVITEM.iItem = i4;
          OS.SendMessage(this.handle, OS.LVM_SETITEM, 0, localLVITEM);
        }
      }
    }
    if ((this.columnCount > 1) && (OS.SendMessage(this.handle, 4124, i, 0) == 0))
      error(15);
    if (m != 0)
      i = 0;
    System.arraycopy(this.columns, i + 1, this.columns, i, --this.columnCount - i);
    this.columns[this.columnCount] = null;
    int i1 = OS.SendMessage(this.handle, 4100, 0, 0);
    for (int i2 = 0; i2 < i1; i2++)
    {
      TableItem localTableItem = _getItem(i2, false);
      if (localTableItem != null)
        if (this.columnCount == 0)
        {
          localTableItem.strings = null;
          localTableItem.images = null;
          localTableItem.cellBackground = null;
          localTableItem.cellForeground = null;
          localTableItem.cellFont = null;
        }
        else
        {
          Object localObject2;
          if (localTableItem.strings != null)
          {
            localObject1 = localTableItem.strings;
            if (i == 0)
              localTableItem.text = (localObject1[1] != null ? localObject1[1] : "");
            localObject2 = new String[this.columnCount];
            System.arraycopy(localObject1, 0, localObject2, 0, i);
            System.arraycopy(localObject1, i + 1, localObject2, i, this.columnCount - i);
            localTableItem.strings = ((String[])localObject2);
          }
          else if (i == 0)
          {
            localTableItem.text = "";
          }
          if (localTableItem.images != null)
          {
            localObject1 = localTableItem.images;
            if (i == 0)
              localTableItem.image = localObject1[1];
            localObject2 = new Image[this.columnCount];
            System.arraycopy(localObject1, 0, localObject2, 0, i);
            System.arraycopy(localObject1, i + 1, localObject2, i, this.columnCount - i);
            localTableItem.images = ((Image[])localObject2);
          }
          else if (i == 0)
          {
            localTableItem.image = null;
          }
          if (localTableItem.cellBackground != null)
          {
            localObject1 = localTableItem.cellBackground;
            localObject2 = new int[this.columnCount];
            System.arraycopy(localObject1, 0, localObject2, 0, i);
            System.arraycopy(localObject1, i + 1, localObject2, i, this.columnCount - i);
            localTableItem.cellBackground = ((int[])localObject2);
          }
          if (localTableItem.cellForeground != null)
          {
            localObject1 = localTableItem.cellForeground;
            localObject2 = new int[this.columnCount];
            System.arraycopy(localObject1, 0, localObject2, 0, i);
            System.arraycopy(localObject1, i + 1, localObject2, i, this.columnCount - i);
            localTableItem.cellForeground = ((int[])localObject2);
          }
          if (localTableItem.cellFont != null)
          {
            localObject1 = localTableItem.cellFont;
            localObject2 = new Font[this.columnCount];
            System.arraycopy(localObject1, 0, localObject2, 0, i);
            System.arraycopy(localObject1, i + 1, localObject2, i, this.columnCount - i);
            localTableItem.cellFont = ((Font[])localObject2);
          }
        }
    }
    if (this.columnCount == 0)
      setScrollWidth(null, true);
    updateMoveable();
    this.ignoreColumnResize = false;
    if (this.columnCount != 0)
    {
      i2 = 0;
      int i5 = arrayOfInt[k];
      localObject1 = new int[this.columnCount];
      for (int i8 = 0; i8 < arrayOfInt.length; i8++)
        if (arrayOfInt[i8] != i5)
        {
          int i9 = arrayOfInt[i8] <= i5 ? arrayOfInt[i8] : arrayOfInt[i8] - 1;
          localObject1[(i2++)] = i9;
        }
      OS.SendMessage(this.handle, 4155, this.columnCount, arrayOfInt);
      for (i8 = 0; i8 < localObject1.length; i8++)
        if (arrayOfInt[i8] != localObject1[i8])
          break;
      if (i8 != localObject1.length)
      {
        OS.SendMessage(this.handle, 4154, localObject1.length, (int[])localObject1);
        OS.InvalidateRect(this.handle, null, true);
      }
      TableColumn[] arrayOfTableColumn = new TableColumn[this.columnCount - k];
      for (int i10 = k; i10 < localObject1.length; i10++)
      {
        arrayOfTableColumn[(i10 - k)] = this.columns[localObject1[i10]];
        arrayOfTableColumn[(i10 - k)].updateToolTip(localObject1[i10]);
      }
      for (i10 = 0; i10 < arrayOfTableColumn.length; i10++)
        if (!arrayOfTableColumn[i10].isDisposed())
          arrayOfTableColumn[i10].sendEvent(10);
    }
    if (this.headerToolTipHandle != 0)
    {
      TOOLINFO localTOOLINFO = new TOOLINFO();
      localTOOLINFO.cbSize = TOOLINFO.sizeof;
      localTOOLINFO.uId = paramTableColumn.id;
      localTOOLINFO.hwnd = OS.SendMessage(this.handle, 4127, 0, 0);
      OS.SendMessage(this.headerToolTipHandle, OS.TTM_DELTOOL, 0, localTOOLINFO);
    }
  }

  void destroyItem(TableItem paramTableItem)
  {
    int i = OS.SendMessage(this.handle, 4100, 0, 0);
    for (int j = 0; j < i; j++)
      if (_getItem(j, false) == paramTableItem)
        break;
    if (j == i)
      return;
    setDeferResize(true);
    this.ignoreSelect = (this.ignoreShrink = 1);
    int k = OS.SendMessage(this.handle, 4104, j, 0);
    this.ignoreSelect = (this.ignoreShrink = 0);
    if (k == 0)
      error(15);
    _removeItem(j, i);
    i--;
    if (i == 0)
      setTableEmpty();
    setDeferResize(false);
  }

  void fixCheckboxImageList(boolean paramBoolean)
  {
    if ((this.style & 0x20) == 0)
      return;
    int i = OS.SendMessage(this.handle, 4098, 1, 0);
    if (i == 0)
      return;
    int[] arrayOfInt1 = new int[1];
    int[] arrayOfInt2 = new int[1];
    OS.ImageList_GetIconSize(i, arrayOfInt1, arrayOfInt2);
    int j = OS.SendMessage(this.handle, 4098, 2, 0);
    if (j == 0)
      return;
    int[] arrayOfInt3 = new int[1];
    int[] arrayOfInt4 = new int[1];
    OS.ImageList_GetIconSize(j, arrayOfInt3, arrayOfInt4);
    if ((arrayOfInt1[0] == arrayOfInt3[0]) && (arrayOfInt2[0] == arrayOfInt4[0]))
      return;
    setCheckboxImageList(arrayOfInt1[0], arrayOfInt2[0], paramBoolean);
  }

  void fixCheckboxImageListColor(boolean paramBoolean)
  {
    if ((this.style & 0x20) == 0)
      return;
    int i = OS.SendMessage(this.handle, 4098, 2, 0);
    if (i == 0)
      return;
    int[] arrayOfInt1 = new int[1];
    int[] arrayOfInt2 = new int[1];
    OS.ImageList_GetIconSize(i, arrayOfInt1, arrayOfInt2);
    setCheckboxImageList(arrayOfInt1[0], arrayOfInt2[0], paramBoolean);
  }

  void fixItemHeight(boolean paramBoolean)
  {
    if (this.itemHeight != -1)
      return;
    if (OS.COMCTL32_VERSION >= OS.VERSION(5, 80))
      return;
    int i = OS.SendMessage(this.handle, 4151, 0, 0);
    if ((i & 0x1) == 0)
      return;
    i = OS.GetWindowLong(this.handle, -16);
    if ((i & 0x4000) != 0)
      return;
    int j = getTopIndex();
    if ((paramBoolean) && (j != 0))
    {
      setRedraw(false);
      setTopIndex(0);
    }
    int k = OS.SendMessage(this.handle, 4098, 1, 0);
    if (k != 0)
      return;
    int m = OS.SendMessage(this.handle, 4127, 0, 0);
    RECT localRECT = new RECT();
    OS.GetWindowRect(m, localRECT);
    int n = localRECT.bottom - localRECT.top - 1;
    int i1 = OS.ImageList_Create(1, n, 0, 0, 0);
    OS.SendMessage(this.handle, 4099, 1, i1);
    fixCheckboxImageList(false);
    OS.SendMessage(this.handle, 4099, 1, 0);
    if (this.headerImageList != null)
    {
      int i2 = this.headerImageList.getHandle();
      OS.SendMessage(m, 4616, 0, i2);
    }
    OS.ImageList_Destroy(i1);
    if ((paramBoolean) && (j != 0))
    {
      setTopIndex(j);
      setRedraw(true);
    }
  }

  public TableColumn getColumn(int paramInt)
  {
    checkWidget();
    if ((paramInt < 0) || (paramInt >= this.columnCount))
      error(6);
    return this.columns[paramInt];
  }

  public int getColumnCount()
  {
    checkWidget();
    return this.columnCount;
  }

  public int[] getColumnOrder()
  {
    checkWidget();
    if (this.columnCount == 0)
      return new int[0];
    int[] arrayOfInt = new int[this.columnCount];
    OS.SendMessage(this.handle, 4155, this.columnCount, arrayOfInt);
    return arrayOfInt;
  }

  public TableColumn[] getColumns()
  {
    checkWidget();
    TableColumn[] arrayOfTableColumn = new TableColumn[this.columnCount];
    System.arraycopy(this.columns, 0, arrayOfTableColumn, 0, this.columnCount);
    return arrayOfTableColumn;
  }

  int getFocusIndex()
  {
    return OS.SendMessage(this.handle, 4108, -1, 1);
  }

  public int getGridLineWidth()
  {
    checkWidget();
    return 1;
  }

  public int getHeaderHeight()
  {
    checkWidget();
    int i = OS.SendMessage(this.handle, 4127, 0, 0);
    if (i == 0)
      return 0;
    RECT localRECT = new RECT();
    OS.GetWindowRect(i, localRECT);
    return localRECT.bottom - localRECT.top;
  }

  public boolean getHeaderVisible()
  {
    checkWidget();
    int i = OS.GetWindowLong(this.handle, -16);
    return (i & 0x4000) == 0;
  }

  public TableItem getItem(int paramInt)
  {
    checkWidget();
    int i = OS.SendMessage(this.handle, 4100, 0, 0);
    if ((paramInt < 0) || (paramInt >= i))
      error(6);
    return _getItem(paramInt);
  }

  public TableItem getItem(Point paramPoint)
  {
    checkWidget();
    if (paramPoint == null)
      error(4);
    int i = OS.SendMessage(this.handle, 4100, 0, 0);
    if (i == 0)
      return null;
    LVHITTESTINFO localLVHITTESTINFO = new LVHITTESTINFO();
    localLVHITTESTINFO.x = paramPoint.x;
    localLVHITTESTINFO.y = paramPoint.y;
    int k;
    if (((this.style & 0x10000) == 0) && (hooks(41)))
    {
      if (OS.SendMessage(this.handle, 4153, 0, localLVHITTESTINFO) < 0)
      {
        RECT localRECT1 = new RECT();
        localRECT1.left = 1;
        this.ignoreCustomDraw = true;
        k = OS.SendMessage(this.handle, 4110, 0, localRECT1);
        this.ignoreCustomDraw = false;
        if (k != 0)
        {
          localLVHITTESTINFO.x = localRECT1.left;
          OS.SendMessage(this.handle, 4153, 0, localLVHITTESTINFO);
          if (localLVHITTESTINFO.iItem < 0)
            localLVHITTESTINFO.iItem = -1;
        }
      }
      if ((localLVHITTESTINFO.iItem != -1) && (localLVHITTESTINFO.iSubItem == 0) && (hitTestSelection(localLVHITTESTINFO.iItem, localLVHITTESTINFO.x, localLVHITTESTINFO.y)))
        return _getItem(localLVHITTESTINFO.iItem);
      return null;
    }
    OS.SendMessage(this.handle, 4114, 0, localLVHITTESTINFO);
    if (localLVHITTESTINFO.iItem != -1)
    {
      if (localLVHITTESTINFO.iItem == 0)
      {
        int j = OS.GetWindowLong(this.handle, -16);
        if ((j & 0x4000) == 0)
        {
          k = OS.SendMessage(this.handle, 4127, 0, 0);
          if (k != 0)
          {
            RECT localRECT2 = new RECT();
            OS.GetWindowRect(k, localRECT2);
            POINT localPOINT = new POINT();
            localPOINT.x = localLVHITTESTINFO.x;
            localPOINT.y = localLVHITTESTINFO.y;
            OS.MapWindowPoints(this.handle, 0, localPOINT, 1);
            if (OS.PtInRect(localRECT2, localPOINT))
              return null;
          }
        }
      }
      return _getItem(localLVHITTESTINFO.iItem);
    }
    return null;
  }

  public int getItemCount()
  {
    checkWidget();
    return OS.SendMessage(this.handle, 4100, 0, 0);
  }

  public int getItemHeight()
  {
    checkWidget();
    if ((!this.painted) && (hooks(41)))
      hitTestSelection(0, 0, 0);
    int i = OS.SendMessage(this.handle, 4160, 0, 0);
    int j = OS.SendMessage(this.handle, 4160, 1, 0);
    return OS.HIWORD(j) - OS.HIWORD(i);
  }

  public TableItem[] getItems()
  {
    checkWidget();
    int i = OS.SendMessage(this.handle, 4100, 0, 0);
    TableItem[] arrayOfTableItem = new TableItem[i];
    if ((this.style & 0x10000000) != 0)
      for (int j = 0; j < i; j++)
        arrayOfTableItem[j] = _getItem(j);
    else
      _getItems(arrayOfTableItem, i);
    return arrayOfTableItem;
  }

  public boolean getLinesVisible()
  {
    checkWidget();
    int i = OS.SendMessage(this.handle, 4151, 0, 0);
    return (i & 0x1) != 0;
  }

  public TableItem[] getSelection()
  {
    checkWidget();
    int i = -1;
    int j = 0;
    int k = OS.SendMessage(this.handle, 4146, 0, 0);
    TableItem[] arrayOfTableItem = new TableItem[k];
    while ((i = OS.SendMessage(this.handle, 4108, i, 2)) != -1)
      arrayOfTableItem[(j++)] = _getItem(i);
    return arrayOfTableItem;
  }

  public int getSelectionCount()
  {
    checkWidget();
    return OS.SendMessage(this.handle, 4146, 0, 0);
  }

  public int getSelectionIndex()
  {
    checkWidget();
    int i = OS.SendMessage(this.handle, 4108, -1, 1);
    int j = OS.SendMessage(this.handle, 4108, -1, 2);
    if (i == j)
      return j;
    int k = -1;
    while ((k = OS.SendMessage(this.handle, 4108, k, 2)) != -1)
      if (k == i)
        return k;
    return j;
  }

  public int[] getSelectionIndices()
  {
    checkWidget();
    int i = -1;
    int j = 0;
    int k = OS.SendMessage(this.handle, 4146, 0, 0);
    int[] arrayOfInt = new int[k];
    while ((i = OS.SendMessage(this.handle, 4108, i, 2)) != -1)
      arrayOfInt[(j++)] = i;
    return arrayOfInt;
  }

  public TableColumn getSortColumn()
  {
    checkWidget();
    return this.sortColumn;
  }

  int getSortColumnPixel()
  {
    int i = OS.IsWindowEnabled(this.handle) ? getBackgroundPixel() : OS.GetSysColor(OS.COLOR_3DFACE);
    int j = i & 0xFF;
    int k = (i & 0xFF00) >> 8;
    int m = (i & 0xFF0000) >> 16;
    if ((j > 240) && (k > 240) && (m > 240))
    {
      j -= 8;
      k -= 8;
      m -= 8;
    }
    else
    {
      j = Math.min(255, j / 10 + j);
      k = Math.min(255, k / 10 + k);
      m = Math.min(255, m / 10 + m);
    }
    return j & 0xFF | (k & 0xFF) << 8 | (m & 0xFF) << 16;
  }

  public int getSortDirection()
  {
    checkWidget();
    return this.sortDirection;
  }

  public int getTopIndex()
  {
    checkWidget();
    return Math.max(0, OS.SendMessage(this.handle, 4135, 0, 0));
  }

  boolean hasChildren()
  {
    int i = OS.SendMessage(this.handle, 4127, 0, 0);
    for (int j = OS.GetWindow(this.handle, 5); j != 0; j = OS.GetWindow(j, 2))
      if (j != i)
        return true;
    return false;
  }

  boolean hitTestSelection(int paramInt1, int paramInt2, int paramInt3)
  {
    int i = OS.SendMessage(this.handle, 4100, 0, 0);
    if (i == 0)
      return false;
    if (!hooks(41))
      return false;
    boolean bool = false;
    if ((paramInt1 >= 0) && (paramInt1 < i))
    {
      TableItem localTableItem = _getItem(paramInt1);
      int j = OS.GetDC(this.handle);
      int k = 0;
      int m = OS.SendMessage(this.handle, 49, 0, 0);
      if (m != 0)
        k = OS.SelectObject(j, m);
      int n = localTableItem.fontHandle(0);
      if (n != -1)
        n = OS.SelectObject(j, n);
      Event localEvent = sendMeasureItemEvent(localTableItem, paramInt1, 0, j);
      if (localEvent.getBounds().contains(paramInt2, paramInt3))
        bool = true;
      if (n != -1)
        n = OS.SelectObject(j, n);
      if (m != 0)
        OS.SelectObject(j, k);
      OS.ReleaseDC(this.handle, j);
    }
    return bool;
  }

  int imageIndex(Image paramImage, int paramInt)
  {
    if (paramImage == null)
      return -2;
    if (paramInt == 0)
      this.firstColumnImage = true;
    else
      setSubImagesVisible(true);
    if (this.imageList == null)
    {
      Rectangle localRectangle = paramImage.getBounds();
      this.imageList = this.display.getImageList(this.style & 0x4000000, localRectangle.width, localRectangle.height);
      int j = this.imageList.indexOf(paramImage);
      if (j == -1)
        j = this.imageList.add(paramImage);
      int k = this.imageList.getHandle();
      int m = getTopIndex();
      if (m != 0)
      {
        setRedraw(false);
        setTopIndex(0);
      }
      OS.SendMessage(this.handle, 4099, 1, k);
      if (this.headerImageList != null)
      {
        int n = OS.SendMessage(this.handle, 4127, 0, 0);
        int i1 = this.headerImageList.getHandle();
        OS.SendMessage(n, 4616, 0, i1);
      }
      fixCheckboxImageList(false);
      setItemHeight(false);
      if (m != 0)
      {
        setTopIndex(m);
        setRedraw(true);
      }
      return j;
    }
    int i = this.imageList.indexOf(paramImage);
    if (i != -1)
      return i;
    return this.imageList.add(paramImage);
  }

  int imageIndexHeader(Image paramImage)
  {
    if (paramImage == null)
      return -2;
    if (this.headerImageList == null)
    {
      Rectangle localRectangle = paramImage.getBounds();
      this.headerImageList = this.display.getImageList(this.style & 0x4000000, localRectangle.width, localRectangle.height);
      int j = this.headerImageList.indexOf(paramImage);
      if (j == -1)
        j = this.headerImageList.add(paramImage);
      int k = this.headerImageList.getHandle();
      int m = OS.SendMessage(this.handle, 4127, 0, 0);
      OS.SendMessage(m, 4616, 0, k);
      return j;
    }
    int i = this.headerImageList.indexOf(paramImage);
    if (i != -1)
      return i;
    return this.headerImageList.add(paramImage);
  }

  public int indexOf(TableColumn paramTableColumn)
  {
    checkWidget();
    if (paramTableColumn == null)
      error(4);
    for (int i = 0; i < this.columnCount; i++)
      if (this.columns[i] == paramTableColumn)
        return i;
    return -1;
  }

  public int indexOf(TableItem paramTableItem)
  {
    checkWidget();
    if (paramTableItem == null)
      error(4);
    int i;
    if (this.keys == null)
    {
      i = OS.SendMessage(this.handle, 4100, 0, 0);
      if ((1 <= this.lastIndexOf) && (this.lastIndexOf < i - 1))
      {
        if (_getItem(this.lastIndexOf, false) == paramTableItem)
          return this.lastIndexOf;
        if (_getItem(this.lastIndexOf + 1, false) == paramTableItem)
          return ++this.lastIndexOf;
        if (_getItem(this.lastIndexOf - 1, false) == paramTableItem)
          return --this.lastIndexOf;
      }
      int j;
      if (this.lastIndexOf < i / 2)
        for (j = 0; j < i; j++)
          if (_getItem(j, false) == paramTableItem)
            return this.lastIndexOf = j;
      else
        for (j = i - 1; j >= 0; j--)
          if (_getItem(j, false) == paramTableItem)
            return this.lastIndexOf = j;
    }
    else
    {
      for (i = 0; i < this.keyCount; i++)
        if (this.items[i] == paramTableItem)
          return this.keys[i];
    }
    return -1;
  }

  boolean isCustomToolTip()
  {
    return hooks(41);
  }

  boolean isOptimizedRedraw()
  {
    if (((this.style & 0x100) == 0) || ((this.style & 0x200) == 0))
      return false;
    return (!hasChildren()) && (!hooks(9)) && (!filters(9));
  }

  public boolean isSelected(int paramInt)
  {
    checkWidget();
    LVITEM localLVITEM = new LVITEM();
    localLVITEM.mask = 8;
    localLVITEM.stateMask = 2;
    localLVITEM.iItem = paramInt;
    int i = OS.SendMessage(this.handle, OS.LVM_GETITEM, 0, localLVITEM);
    return (i != 0) && ((localLVITEM.state & 0x2) != 0);
  }

  void register()
  {
    super.register();
    int i = OS.SendMessage(this.handle, 4127, 0, 0);
    if (i != 0)
      this.display.addControl(i, this);
  }

  void releaseChildren(boolean paramBoolean)
  {
    int i;
    if (_hasItems())
    {
      i = OS.SendMessage(this.handle, 4100, 0, 0);
      int j;
      TableItem localTableItem;
      if ((OS.IsWin95) && (this.columnCount > 1))
      {
        this.resizeCount = 1;
        OS.SendMessage(this.handle, 11, 0, 0);
        for (j = i - 1; j >= 0; j--)
        {
          localTableItem = _getItem(j, false);
          if ((localTableItem != null) && (!localTableItem.isDisposed()))
            localTableItem.release(false);
          this.ignoreSelect = (this.ignoreShrink = 1);
          OS.SendMessage(this.handle, 4104, j, 0);
          this.ignoreSelect = (this.ignoreShrink = 0);
        }
      }
      else if (this.keys == null)
      {
        for (j = 0; j < i; j++)
        {
          localTableItem = _getItem(j, false);
          if ((localTableItem != null) && (!localTableItem.isDisposed()))
            localTableItem.release(false);
        }
      }
      else
      {
        for (j = 0; j < this.keyCount; j++)
        {
          localTableItem = this.items[j];
          if ((localTableItem != null) && (!localTableItem.isDisposed()))
            localTableItem.release(false);
        }
      }
      _clearItems();
    }
    if (this.columns != null)
    {
      for (i = 0; i < this.columnCount; i++)
      {
        TableColumn localTableColumn = this.columns[i];
        if (!localTableColumn.isDisposed())
          localTableColumn.release(false);
      }
      this.columns = null;
    }
    super.releaseChildren(paramBoolean);
  }

  void releaseWidget()
  {
    super.releaseWidget();
    this.customDraw = false;
    this.currentItem = null;
    if (this.imageList != null)
    {
      OS.SendMessage(this.handle, 4099, 1, 0);
      this.display.releaseImageList(this.imageList);
    }
    if (this.headerImageList != null)
    {
      i = OS.SendMessage(this.handle, 4127, 0, 0);
      OS.SendMessage(i, 4616, 0, 0);
      this.display.releaseImageList(this.headerImageList);
    }
    this.imageList = (this.headerImageList = null);
    int i = OS.SendMessage(this.handle, 4098, 2, 0);
    OS.SendMessage(this.handle, 4099, 2, 0);
    if (i != 0)
      OS.ImageList_Destroy(i);
    if (this.headerToolTipHandle != 0)
      OS.DestroyWindow(this.headerToolTipHandle);
    this.headerToolTipHandle = 0;
  }

  public void remove(int[] paramArrayOfInt)
  {
    checkWidget();
    if (paramArrayOfInt == null)
      error(4);
    if (paramArrayOfInt.length == 0)
      return;
    int[] arrayOfInt = new int[paramArrayOfInt.length];
    System.arraycopy(paramArrayOfInt, 0, arrayOfInt, 0, paramArrayOfInt.length);
    sort(arrayOfInt);
    int i = arrayOfInt[(arrayOfInt.length - 1)];
    int j = arrayOfInt[0];
    int k = OS.SendMessage(this.handle, 4100, 0, 0);
    if ((i < 0) || (i > j) || (j >= k))
      error(6);
    setDeferResize(true);
    int m = -1;
    for (int n = 0; n < arrayOfInt.length; n++)
    {
      int i1 = arrayOfInt[n];
      if (i1 != m)
      {
        TableItem localTableItem = _getItem(i1, false);
        if ((localTableItem != null) && (!localTableItem.isDisposed()))
          localTableItem.release(false);
        this.ignoreSelect = (this.ignoreShrink = 1);
        int i2 = OS.SendMessage(this.handle, 4104, i1, 0);
        this.ignoreSelect = (this.ignoreShrink = 0);
        if (i2 == 0)
          error(15);
        _removeItem(i1, k);
        k--;
        m = i1;
      }
    }
    if (k == 0)
      setTableEmpty();
    setDeferResize(false);
  }

  public void remove(int paramInt)
  {
    checkWidget();
    int i = OS.SendMessage(this.handle, 4100, 0, 0);
    if ((paramInt < 0) || (paramInt >= i))
      error(6);
    TableItem localTableItem = _getItem(paramInt, false);
    if ((localTableItem != null) && (!localTableItem.isDisposed()))
      localTableItem.release(false);
    setDeferResize(true);
    this.ignoreSelect = (this.ignoreShrink = 1);
    int j = OS.SendMessage(this.handle, 4104, paramInt, 0);
    this.ignoreSelect = (this.ignoreShrink = 0);
    if (j == 0)
      error(15);
    _removeItem(paramInt, i);
    i--;
    if (i == 0)
      setTableEmpty();
    setDeferResize(false);
  }

  public void remove(int paramInt1, int paramInt2)
  {
    checkWidget();
    if (paramInt1 > paramInt2)
      return;
    int i = OS.SendMessage(this.handle, 4100, 0, 0);
    if ((paramInt1 < 0) || (paramInt1 > paramInt2) || (paramInt2 >= i))
      error(6);
    if ((paramInt1 == 0) && (paramInt2 == i - 1))
    {
      removeAll();
    }
    else
    {
      setDeferResize(true);
      for (int j = paramInt1; j <= paramInt2; j++)
      {
        TableItem localTableItem = _getItem(j, false);
        if ((localTableItem != null) && (!localTableItem.isDisposed()))
          localTableItem.release(false);
        this.ignoreSelect = (this.ignoreShrink = 1);
        int k = OS.SendMessage(this.handle, 4104, paramInt1, 0);
        this.ignoreSelect = (this.ignoreShrink = 0);
        if (k == 0)
          break;
      }
      _removeItems(paramInt1, j, i);
      if (j <= paramInt2)
        error(15);
      setDeferResize(false);
    }
  }

  public void removeAll()
  {
    checkWidget();
    int i = OS.SendMessage(this.handle, 4100, 0, 0);
    for (int j = 0; j < i; j++)
    {
      TableItem localTableItem = _getItem(j, false);
      if ((localTableItem != null) && (!localTableItem.isDisposed()))
        localTableItem.release(false);
    }
    setDeferResize(true);
    if ((OS.IsWin95) && (this.columnCount > 1))
    {
      j = (getDrawing()) && (OS.IsWindowVisible(this.handle)) ? 1 : 0;
      if (j != 0)
        OS.SendMessage(this.handle, 11, 0, 0);
      for (int k = i - 1; k >= 0; k--)
      {
        this.ignoreSelect = (this.ignoreShrink = 1);
        int m = OS.SendMessage(this.handle, 4104, k, 0);
        this.ignoreSelect = (this.ignoreShrink = 0);
        if (m == 0)
          break;
      }
      if (j != 0)
        OS.SendMessage(this.handle, 11, 1, 0);
      if (k != -1)
        error(15);
    }
    else
    {
      this.ignoreSelect = (this.ignoreShrink = 1);
      j = OS.SendMessage(this.handle, 4105, 0, 0);
      this.ignoreSelect = (this.ignoreShrink = 0);
      if (j == 0)
        error(15);
    }
    setTableEmpty();
    setDeferResize(false);
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

  public void select(int[] paramArrayOfInt)
  {
    checkWidget();
    if (paramArrayOfInt == null)
      error(4);
    int i = paramArrayOfInt.length;
    if ((i == 0) || (((this.style & 0x4) != 0) && (i > 1)))
      return;
    LVITEM localLVITEM = new LVITEM();
    localLVITEM.state = 2;
    localLVITEM.stateMask = 2;
    for (int j = i - 1; j >= 0; j--)
      if (paramArrayOfInt[j] >= 0)
      {
        this.ignoreSelect = true;
        OS.SendMessage(this.handle, 4139, paramArrayOfInt[j], localLVITEM);
        this.ignoreSelect = false;
      }
  }

  void reskinChildren(int paramInt)
  {
    int i;
    if (_hasItems())
    {
      i = OS.SendMessage(this.handle, 4100, 0, 0);
      for (int j = 0; j < i; j++)
      {
        TableItem localTableItem = _getItem(j, false);
        if (localTableItem != null)
          localTableItem.reskin(paramInt);
      }
    }
    if (this.columns != null)
      for (i = 0; i < this.columnCount; i++)
      {
        TableColumn localTableColumn = this.columns[i];
        if (!localTableColumn.isDisposed())
          localTableColumn.reskin(paramInt);
      }
    super.reskinChildren(paramInt);
  }

  public void select(int paramInt)
  {
    checkWidget();
    if (paramInt < 0)
      return;
    LVITEM localLVITEM = new LVITEM();
    localLVITEM.state = 2;
    localLVITEM.stateMask = 2;
    this.ignoreSelect = true;
    OS.SendMessage(this.handle, 4139, paramInt, localLVITEM);
    this.ignoreSelect = false;
  }

  public void select(int paramInt1, int paramInt2)
  {
    checkWidget();
    if ((paramInt2 < 0) || (paramInt1 > paramInt2) || (((this.style & 0x4) != 0) && (paramInt1 != paramInt2)))
      return;
    int i = OS.SendMessage(this.handle, 4100, 0, 0);
    if ((i == 0) || (paramInt1 >= i))
      return;
    paramInt1 = Math.max(0, paramInt1);
    paramInt2 = Math.min(paramInt2, i - 1);
    if ((paramInt1 == 0) && (paramInt2 == i - 1))
    {
      selectAll();
    }
    else
    {
      LVITEM localLVITEM = new LVITEM();
      localLVITEM.state = 2;
      localLVITEM.stateMask = 2;
      for (int j = paramInt1; j <= paramInt2; j++)
      {
        this.ignoreSelect = true;
        OS.SendMessage(this.handle, 4139, j, localLVITEM);
        this.ignoreSelect = false;
      }
    }
  }

  public void selectAll()
  {
    checkWidget();
    if ((this.style & 0x4) != 0)
      return;
    LVITEM localLVITEM = new LVITEM();
    localLVITEM.mask = 8;
    localLVITEM.state = 2;
    localLVITEM.stateMask = 2;
    this.ignoreSelect = true;
    OS.SendMessage(this.handle, 4139, -1, localLVITEM);
    this.ignoreSelect = false;
  }

  void sendEraseItemEvent(TableItem paramTableItem, NMLVCUSTOMDRAW paramNMLVCUSTOMDRAW, int paramInt, Event paramEvent)
  {
    int i = paramNMLVCUSTOMDRAW.hdc;
    int j = paramTableItem.cellForeground != null ? paramTableItem.cellForeground[paramNMLVCUSTOMDRAW.iSubItem] : -1;
    if (j == -1)
      j = paramTableItem.foreground;
    int k = -1;
    if ((OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()) && (this.sortColumn != null) && (this.sortDirection != 0) && (findImageControl() == null) && (indexOf(this.sortColumn) == paramNMLVCUSTOMDRAW.iSubItem))
      k = getSortColumnPixel();
    k = paramTableItem.cellBackground != null ? paramTableItem.cellBackground[paramNMLVCUSTOMDRAW.iSubItem] : -1;
    if (k == -1)
      k = paramTableItem.background;
    LVITEM localLVITEM = new LVITEM();
    localLVITEM.mask = 8;
    localLVITEM.stateMask = 2;
    localLVITEM.iItem = paramNMLVCUSTOMDRAW.dwItemSpec;
    int m = OS.SendMessage(this.handle, OS.LVM_GETITEM, 0, localLVITEM);
    int n = (m != 0) && ((localLVITEM.state & 0x2) != 0) ? 1 : 0;
    GCData localGCData = new GCData();
    localGCData.device = this.display;
    int i1 = -1;
    int i2 = 0;
    int i3 = 0;
    int i4 = 0;
    int i5 = 0;
    if ((paramNMLVCUSTOMDRAW.iSubItem == 0) || ((this.style & 0x10000) != 0))
    {
      i4 = this.hotIndex == paramNMLVCUSTOMDRAW.dwItemSpec ? 1 : 0;
      i5 = (paramNMLVCUSTOMDRAW.uItemState & 0x1000) != 0 ? 1 : 0;
    }
    if (OS.IsWindowEnabled(this.handle))
    {
      if ((n != 0) && ((paramNMLVCUSTOMDRAW.iSubItem == 0) || ((this.style & 0x10000) != 0)))
      {
        if ((OS.GetFocus() == this.handle) || (this.display.getHighContrast()))
        {
          i2 = 1;
          localGCData.foreground = OS.GetSysColor(OS.COLOR_HIGHLIGHTTEXT);
          localGCData.background = (i1 = OS.GetSysColor(OS.COLOR_HIGHLIGHT));
        }
        else
        {
          i2 = (this.style & 0x8000) == 0 ? 1 : 0;
          localGCData.foreground = OS.GetTextColor(i);
          localGCData.background = (i1 = OS.GetSysColor(OS.COLOR_3DFACE));
        }
        if (this.explorerTheme)
          localGCData.foreground = (j != -1 ? j : getForegroundPixel());
      }
      else
      {
        i3 = k != -1 ? 1 : 0;
        if ((j == -1) || (k == -1))
        {
          Object localObject = findBackgroundControl();
          if (localObject == null)
            localObject = this;
          if (j == -1)
            j = ((Control)localObject).getForegroundPixel();
          if (k == -1)
            k = ((Control)localObject).getBackgroundPixel();
        }
        localGCData.foreground = (j != -1 ? j : OS.GetTextColor(i));
        localGCData.background = (k != -1 ? k : OS.GetBkColor(i));
      }
    }
    else
    {
      localGCData.foreground = OS.GetSysColor(OS.COLOR_GRAYTEXT);
      localGCData.background = OS.GetSysColor(OS.COLOR_3DFACE);
      if (n != 0)
        i1 = localGCData.background;
    }
    localGCData.font = paramTableItem.getFont(paramNMLVCUSTOMDRAW.iSubItem);
    localGCData.uiState = OS.SendMessage(this.handle, 297, 0, 0);
    int i6 = OS.SaveDC(i);
    GC localGC = GC.win32_new(i, localGCData);
    RECT localRECT1 = paramTableItem.getBounds(paramNMLVCUSTOMDRAW.dwItemSpec, paramNMLVCUSTOMDRAW.iSubItem, true, true, true, true, i);
    Event localEvent = new Event();
    localEvent.item = paramTableItem;
    localEvent.gc = localGC;
    localEvent.index = paramNMLVCUSTOMDRAW.iSubItem;
    localEvent.detail |= 16;
    if ((OS.SendMessage(this.handle, 4108, -1, 1) == paramNMLVCUSTOMDRAW.dwItemSpec) && ((paramNMLVCUSTOMDRAW.iSubItem == 0) || ((this.style & 0x10000) != 0)) && (this.handle == OS.GetFocus()))
    {
      i7 = OS.SendMessage(this.handle, 297, 0, 0);
      if ((i7 & 0x1) == 0)
        localEvent.detail |= 4;
    }
    int i7 = (localEvent.detail & 0x4) != 0 ? 1 : 0;
    if (i4 != 0)
      localEvent.detail |= 32;
    if (i2 != 0)
      localEvent.detail |= 2;
    if (i3 != 0)
      localEvent.detail |= 8;
    localEvent.x = localRECT1.left;
    localEvent.y = localRECT1.top;
    localEvent.width = (localRECT1.right - localRECT1.left);
    localEvent.height = (localRECT1.bottom - localRECT1.top);
    localGC.setClipping(localEvent.x, localEvent.y, localEvent.width, localEvent.height);
    sendEvent(40, localEvent);
    localEvent.gc = null;
    int i8 = localGCData.foreground;
    localGC.dispose();
    OS.RestoreDC(i, i6);
    if ((isDisposed()) || (paramTableItem.isDisposed()))
      return;
    if (localEvent.doit)
    {
      this.ignoreDrawForeground = ((localEvent.detail & 0x10) == 0);
      this.ignoreDrawBackground = ((localEvent.detail & 0x8) == 0);
      this.ignoreDrawSelection = ((localEvent.detail & 0x2) == 0);
      this.ignoreDrawFocus = ((localEvent.detail & 0x4) == 0);
      this.ignoreDrawHot = ((localEvent.detail & 0x20) == 0);
    }
    else
    {
      this.ignoreDrawForeground = (this.ignoreDrawBackground = this.ignoreDrawSelection = this.ignoreDrawFocus = this.ignoreDrawHot = 1);
    }
    if (i2 != 0)
    {
      if (this.ignoreDrawSelection)
      {
        this.ignoreDrawHot = true;
        if ((paramNMLVCUSTOMDRAW.iSubItem == 0) || ((this.style & 0x10000) != 0))
          this.selectionForeground = i8;
        paramNMLVCUSTOMDRAW.uItemState &= -2;
        OS.MoveMemory(paramInt, paramNMLVCUSTOMDRAW, NMLVCUSTOMDRAW.sizeof);
      }
    }
    else if (this.ignoreDrawSelection)
    {
      paramNMLVCUSTOMDRAW.uItemState |= 1;
      OS.MoveMemory(paramInt, paramNMLVCUSTOMDRAW, NMLVCUSTOMDRAW.sizeof);
    }
    int i9 = OS.SendMessage(this.handle, 4127, 0, 0);
    int i10 = paramNMLVCUSTOMDRAW.iSubItem == OS.SendMessage(i9, 4623, 0, 0) ? 1 : 0;
    if ((this.ignoreDrawForeground) && (this.ignoreDrawHot) && (i5 == 0) && (!this.ignoreDrawBackground) && (i3 != 0))
    {
      RECT localRECT2 = paramTableItem.getBounds(paramNMLVCUSTOMDRAW.dwItemSpec, paramNMLVCUSTOMDRAW.iSubItem, true, false, true, false, i);
      fillBackground(i, k, localRECT2);
    }
    this.focusRect = null;
    if ((!this.ignoreDrawHot) || (!this.ignoreDrawSelection) || (!this.ignoreDrawFocus) || (i5 != 0))
    {
      boolean bool = ((this.style & 0x10000) != 0) || (i10 == 0);
      RECT localRECT4 = paramTableItem.getBounds(paramNMLVCUSTOMDRAW.dwItemSpec, paramNMLVCUSTOMDRAW.iSubItem, true, false, bool, false, i);
      if ((this.style & 0x10000) == 0)
      {
        if (paramEvent != null)
          localRECT4.right = Math.min(localRECT1.right, paramEvent.x + paramEvent.width);
        if (!this.ignoreDrawFocus)
        {
          paramNMLVCUSTOMDRAW.uItemState &= -17;
          OS.MoveMemory(paramInt, paramNMLVCUSTOMDRAW, NMLVCUSTOMDRAW.sizeof);
          this.focusRect = localRECT4;
        }
      }
      if (this.explorerTheme)
      {
        if ((!this.ignoreDrawHot) || (i5 != 0) || ((!this.ignoreDrawSelection) && (i1 != -1)))
        {
          RECT localRECT5 = new RECT();
          OS.SetRect(localRECT5, paramNMLVCUSTOMDRAW.left, paramNMLVCUSTOMDRAW.top, paramNMLVCUSTOMDRAW.right, paramNMLVCUSTOMDRAW.bottom);
          RECT localRECT6 = new RECT();
          OS.SetRect(localRECT6, paramNMLVCUSTOMDRAW.left, paramNMLVCUSTOMDRAW.top, paramNMLVCUSTOMDRAW.right, paramNMLVCUSTOMDRAW.bottom);
          if ((this.style & 0x10000) != 0)
          {
            i11 = OS.SendMessage(i9, 4608, 0, 0);
            i12 = OS.SendMessage(i9, 4623, i11 - 1, 0);
            RECT localRECT7 = new RECT();
            OS.SendMessage(i9, 4615, i12, localRECT7);
            OS.MapWindowPoints(i9, this.handle, localRECT7, 2);
            localRECT6.right = localRECT7.right;
            i12 = OS.SendMessage(i9, 4623, 0, 0);
            OS.SendMessage(i9, 4615, i12, localRECT7);
            OS.MapWindowPoints(i9, this.handle, localRECT7, 2);
            localRECT6.left = localRECT7.left;
            localRECT5.left = localRECT1.left;
            localRECT5.right += 2;
          }
          else
          {
            localRECT6.right += 2;
            localRECT5.right += 2;
          }
          int i11 = OS.OpenThemeData(this.handle, Display.LISTVIEW);
          int i12 = n != 0 ? 3 : 2;
          if ((OS.GetFocus() != this.handle) && (n != 0) && (i4 == 0))
            i12 = 5;
          if (i5 != 0)
            i12 = 3;
          OS.DrawThemeBackground(i11, i, 1, i12, localRECT6, localRECT5);
          OS.CloseThemeData(i11);
        }
      }
      else if ((!this.ignoreDrawSelection) && (i1 != -1))
        fillBackground(i, i1, localRECT4);
    }
    if ((i7 != 0) && (this.ignoreDrawFocus))
    {
      paramNMLVCUSTOMDRAW.uItemState &= -17;
      OS.MoveMemory(paramInt, paramNMLVCUSTOMDRAW, NMLVCUSTOMDRAW.sizeof);
    }
    if (this.ignoreDrawForeground)
    {
      RECT localRECT3 = paramTableItem.getBounds(paramNMLVCUSTOMDRAW.dwItemSpec, paramNMLVCUSTOMDRAW.iSubItem, true, true, true, false, i);
      OS.SaveDC(i);
      OS.SelectClipRgn(i, 0);
      OS.ExcludeClipRect(i, localRECT3.left, localRECT3.top, localRECT3.right, localRECT3.bottom);
    }
  }

  Event sendEraseItemEvent(TableItem paramTableItem, NMTTCUSTOMDRAW paramNMTTCUSTOMDRAW, int paramInt, RECT paramRECT)
  {
    int i = OS.SaveDC(paramNMTTCUSTOMDRAW.hdc);
    RECT localRECT = toolTipInset(paramRECT);
    OS.SetWindowOrgEx(paramNMTTCUSTOMDRAW.hdc, localRECT.left, localRECT.top, null);
    GCData localGCData = new GCData();
    localGCData.device = this.display;
    localGCData.foreground = OS.GetTextColor(paramNMTTCUSTOMDRAW.hdc);
    localGCData.background = OS.GetBkColor(paramNMTTCUSTOMDRAW.hdc);
    localGCData.font = paramTableItem.getFont(paramInt);
    localGCData.uiState = OS.SendMessage(this.handle, 297, 0, 0);
    GC localGC = GC.win32_new(paramNMTTCUSTOMDRAW.hdc, localGCData);
    Event localEvent = new Event();
    localEvent.item = paramTableItem;
    localEvent.index = paramInt;
    localEvent.gc = localGC;
    localEvent.detail |= 16;
    localEvent.x = paramRECT.left;
    localEvent.y = paramRECT.top;
    localEvent.width = (paramRECT.right - paramRECT.left);
    localEvent.height = (paramRECT.bottom - paramRECT.top);
    sendEvent(40, localEvent);
    localEvent.gc = null;
    localGC.dispose();
    OS.RestoreDC(paramNMTTCUSTOMDRAW.hdc, i);
    return localEvent;
  }

  Event sendMeasureItemEvent(TableItem paramTableItem, int paramInt1, int paramInt2, int paramInt3)
  {
    GCData localGCData = new GCData();
    localGCData.device = this.display;
    localGCData.font = paramTableItem.getFont(paramInt2);
    int i = OS.SaveDC(paramInt3);
    GC localGC = GC.win32_new(paramInt3, localGCData);
    RECT localRECT = paramTableItem.getBounds(paramInt1, paramInt2, true, true, false, false, paramInt3);
    Event localEvent = new Event();
    localEvent.item = paramTableItem;
    localEvent.gc = localGC;
    localEvent.index = paramInt2;
    localEvent.x = localRECT.left;
    localEvent.y = localRECT.top;
    localEvent.width = (localRECT.right - localRECT.left);
    localEvent.height = (localRECT.bottom - localRECT.top);
    sendEvent(41, localEvent);
    localEvent.gc = null;
    localGC.dispose();
    OS.RestoreDC(paramInt3, i);
    if ((!isDisposed()) && (!paramTableItem.isDisposed()))
    {
      if (this.columnCount == 0)
      {
        j = OS.SendMessage(this.handle, 4125, 0, 0);
        if (localEvent.x + localEvent.width > j)
          setScrollWidth(localEvent.x + localEvent.width);
      }
      int j = OS.SendMessage(this.handle, 4160, 0, 0);
      int k = OS.SendMessage(this.handle, 4160, 1, 0);
      int m = OS.HIWORD(k) - OS.HIWORD(j);
      if (localEvent.height > m)
        setItemHeight(localEvent.height);
    }
    return localEvent;
  }

  LRESULT sendMouseDownEvent(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    Display localDisplay = this.display;
    localDisplay.captureChanged = false;
    if (!sendMouseEvent(paramInt1, paramInt2, this.handle, paramInt3, paramInt4, paramInt5))
    {
      if ((!localDisplay.captureChanged) && (!isDisposed()) && (OS.GetCapture() != this.handle))
        OS.SetCapture(this.handle);
      return LRESULT.ZERO;
    }
    LVHITTESTINFO localLVHITTESTINFO = new LVHITTESTINFO();
    localLVHITTESTINFO.x = OS.GET_X_LPARAM(paramInt5);
    localLVHITTESTINFO.y = OS.GET_Y_LPARAM(paramInt5);
    OS.SendMessage(this.handle, 4114, 0, localLVHITTESTINFO);
    if (((this.style & 0x10000) == 0) && (hooks(41)))
      if (OS.SendMessage(this.handle, 4153, 0, localLVHITTESTINFO) < 0)
      {
        i = OS.SendMessage(this.handle, 4100, 0, 0);
        if (i != 0)
        {
          RECT localRECT = new RECT();
          localRECT.left = 1;
          this.ignoreCustomDraw = true;
          int k = OS.SendMessage(this.handle, 4110, 0, localRECT);
          this.ignoreCustomDraw = false;
          if (k != 0)
          {
            localLVHITTESTINFO.x = localRECT.left;
            OS.SendMessage(this.handle, 4153, 0, localLVHITTESTINFO);
            if (localLVHITTESTINFO.iItem < 0)
              localLVHITTESTINFO.iItem = -1;
            localLVHITTESTINFO.flags &= -7;
          }
        }
      }
      else if (localLVHITTESTINFO.iSubItem != 0)
      {
        localLVHITTESTINFO.iItem = -1;
      }
    OS.SetFocus(this.handle);
    if ((((this.style & 0x4) != 0) || (hooks(3)) || (hooks(4))) && (localLVHITTESTINFO.iItem == -1))
    {
      if ((!localDisplay.captureChanged) && (!isDisposed()) && (OS.GetCapture() != this.handle))
        OS.SetCapture(this.handle);
      return LRESULT.ZERO;
    }
    int i = 0;
    int j = OS.SendMessage(this.handle, 4146, 0, 0);
    if ((j == 1) && (localLVHITTESTINFO.iItem != -1))
    {
      LVITEM localLVITEM = new LVITEM();
      localLVITEM.mask = 8;
      localLVITEM.stateMask = 2;
      localLVITEM.iItem = localLVHITTESTINFO.iItem;
      OS.SendMessage(this.handle, OS.LVM_GETITEM, 0, localLVITEM);
      if ((localLVITEM.state & 0x2) != 0)
        i = 1;
    }
    this.fullRowSelect = false;
    if ((localLVHITTESTINFO.iItem != -1) && ((this.style & 0x10000) == 0) && (hooks(41)))
    {
      this.fullRowSelect = hitTestSelection(localLVHITTESTINFO.iItem, localLVHITTESTINFO.x, localLVHITTESTINFO.y);
      if (this.fullRowSelect)
      {
        m = 6;
        if ((localLVHITTESTINFO.flags & m) != 0)
          this.fullRowSelect = false;
      }
    }
    int m = ((this.state & 0x8000) != 0) && (hooks(29)) ? 1 : 0;
    if (m == 0)
    {
      n = 6;
      m = (localLVHITTESTINFO.iItem != -1) && ((localLVHITTESTINFO.flags & n) != 0) ? 0 : 1;
      if (this.fullRowSelect)
        m = 1;
    }
    if (this.fullRowSelect)
    {
      OS.UpdateWindow(this.handle);
      OS.DefWindowProc(this.handle, 11, 0, 0);
      OS.SendMessage(this.handle, 4150, 32, 32);
    }
    this.dragStarted = false;
    localDisplay.dragCancelled = false;
    if (m == 0)
      localDisplay.runDragDrop = false;
    int n = callWindowProc(this.handle, paramInt3, paramInt4, paramInt5, i);
    if (m == 0)
      localDisplay.runDragDrop = true;
    if (this.fullRowSelect)
    {
      this.fullRowSelect = false;
      OS.DefWindowProc(this.handle, 11, 1, 0);
      OS.SendMessage(this.handle, 4150, 32, 0);
    }
    if ((this.dragStarted) || (localDisplay.dragCancelled))
    {
      if ((!localDisplay.captureChanged) && (!isDisposed()) && (OS.GetCapture() != this.handle))
        OS.SetCapture(this.handle);
    }
    else
    {
      int i1 = 6;
      int i2 = (localLVHITTESTINFO.flags & i1) != 0 ? 1 : 0;
      if ((i2 == 0) && ((this.style & 0x2) != 0))
        i2 = (localLVHITTESTINFO.flags & 0x8) == 0 ? 1 : 0;
      if (i2 != 0)
        sendMouseEvent(4, paramInt2, this.handle, paramInt3, paramInt4, paramInt5);
    }
    return new LRESULT(n);
  }

  void sendPaintItemEvent(TableItem paramTableItem, NMLVCUSTOMDRAW paramNMLVCUSTOMDRAW)
  {
    int i = paramNMLVCUSTOMDRAW.hdc;
    GCData localGCData = new GCData();
    localGCData.device = this.display;
    localGCData.font = paramTableItem.getFont(paramNMLVCUSTOMDRAW.iSubItem);
    LVITEM localLVITEM = new LVITEM();
    localLVITEM.mask = 8;
    localLVITEM.stateMask = 2;
    localLVITEM.iItem = paramNMLVCUSTOMDRAW.dwItemSpec;
    int j = OS.SendMessage(this.handle, OS.LVM_GETITEM, 0, localLVITEM);
    int k = (j != 0) && ((localLVITEM.state & 0x2) != 0) ? 1 : 0;
    int m = 0;
    int n = 0;
    int i1 = 0;
    if ((paramNMLVCUSTOMDRAW.iSubItem == 0) || ((this.style & 0x10000) != 0))
      i1 = this.hotIndex == paramNMLVCUSTOMDRAW.dwItemSpec ? 1 : 0;
    if (OS.IsWindowEnabled(this.handle))
    {
      if ((k != 0) && ((paramNMLVCUSTOMDRAW.iSubItem == 0) || ((this.style & 0x10000) != 0)))
      {
        if ((OS.GetFocus() == this.handle) || (this.display.getHighContrast()))
        {
          m = 1;
          if (this.selectionForeground != -1)
            localGCData.foreground = this.selectionForeground;
          else
            localGCData.foreground = OS.GetSysColor(OS.COLOR_HIGHLIGHTTEXT);
          localGCData.background = OS.GetSysColor(OS.COLOR_HIGHLIGHT);
        }
        else
        {
          m = (this.style & 0x8000) == 0 ? 1 : 0;
          localGCData.foreground = OS.GetTextColor(i);
          localGCData.background = OS.GetSysColor(OS.COLOR_3DFACE);
        }
        if ((this.explorerTheme) && (this.selectionForeground == -1))
        {
          i2 = paramTableItem.cellForeground != null ? paramTableItem.cellForeground[paramNMLVCUSTOMDRAW.iSubItem] : -1;
          if (i2 == -1)
            i2 = paramTableItem.foreground;
          localGCData.foreground = (i2 != -1 ? i2 : getForegroundPixel());
        }
      }
      else
      {
        i2 = paramTableItem.cellForeground != null ? paramTableItem.cellForeground[paramNMLVCUSTOMDRAW.iSubItem] : -1;
        if (i2 == -1)
          i2 = paramTableItem.foreground;
        int i3 = paramTableItem.cellBackground != null ? paramTableItem.cellBackground[paramNMLVCUSTOMDRAW.iSubItem] : -1;
        if (i3 == -1)
          i3 = paramTableItem.background;
        n = i3 != -1 ? 1 : 0;
        if ((i2 == -1) || (i3 == -1))
        {
          localObject = findBackgroundControl();
          if (localObject == null)
            localObject = this;
          if (i2 == -1)
            i2 = ((Control)localObject).getForegroundPixel();
          if (i3 == -1)
            i3 = ((Control)localObject).getBackgroundPixel();
        }
        localGCData.foreground = (i2 != -1 ? i2 : OS.GetTextColor(i));
        localGCData.background = (i3 != -1 ? i3 : OS.GetBkColor(i));
      }
    }
    else
    {
      localGCData.foreground = OS.GetSysColor(OS.COLOR_GRAYTEXT);
      localGCData.background = OS.GetSysColor(OS.COLOR_3DFACE);
    }
    localGCData.uiState = OS.SendMessage(this.handle, 297, 0, 0);
    int i2 = OS.SaveDC(i);
    GC localGC = GC.win32_new(i, localGCData);
    Object localObject = paramTableItem.getBounds(paramNMLVCUSTOMDRAW.dwItemSpec, paramNMLVCUSTOMDRAW.iSubItem, true, true, false, false, i);
    Event localEvent = new Event();
    localEvent.item = paramTableItem;
    localEvent.gc = localGC;
    localEvent.index = paramNMLVCUSTOMDRAW.iSubItem;
    localEvent.detail |= 16;
    if ((OS.SendMessage(this.handle, 4108, -1, 1) == paramNMLVCUSTOMDRAW.dwItemSpec) && ((paramNMLVCUSTOMDRAW.iSubItem == 0) || ((this.style & 0x10000) != 0)) && (this.handle == OS.GetFocus()))
    {
      int i4 = OS.SendMessage(this.handle, 297, 0, 0);
      if ((i4 & 0x1) == 0)
        localEvent.detail |= 4;
    }
    if (i1 != 0)
      localEvent.detail |= 32;
    if (m != 0)
      localEvent.detail |= 2;
    if (n != 0)
      localEvent.detail |= 8;
    localEvent.x = ((RECT)localObject).left;
    localEvent.y = ((RECT)localObject).top;
    localEvent.width = (((RECT)localObject).right - ((RECT)localObject).left);
    localEvent.height = (((RECT)localObject).bottom - ((RECT)localObject).top);
    RECT localRECT = paramTableItem.getBounds(paramNMLVCUSTOMDRAW.dwItemSpec, paramNMLVCUSTOMDRAW.iSubItem, true, true, true, true, i);
    int i5 = localRECT.right - localRECT.left;
    int i6 = localRECT.bottom - localRECT.top;
    localGC.setClipping(localRECT.left, localRECT.top, i5, i6);
    sendEvent(42, localEvent);
    if (localGCData.focusDrawn)
      this.focusRect = null;
    localEvent.gc = null;
    localGC.dispose();
    OS.RestoreDC(i, i2);
  }

  Event sendPaintItemEvent(TableItem paramTableItem, NMTTCUSTOMDRAW paramNMTTCUSTOMDRAW, int paramInt, RECT paramRECT)
  {
    int i = OS.SaveDC(paramNMTTCUSTOMDRAW.hdc);
    RECT localRECT = toolTipInset(paramRECT);
    OS.SetWindowOrgEx(paramNMTTCUSTOMDRAW.hdc, localRECT.left, localRECT.top, null);
    GCData localGCData = new GCData();
    localGCData.device = this.display;
    localGCData.font = paramTableItem.getFont(paramInt);
    localGCData.foreground = OS.GetTextColor(paramNMTTCUSTOMDRAW.hdc);
    localGCData.background = OS.GetBkColor(paramNMTTCUSTOMDRAW.hdc);
    localGCData.uiState = OS.SendMessage(this.handle, 297, 0, 0);
    GC localGC = GC.win32_new(paramNMTTCUSTOMDRAW.hdc, localGCData);
    Event localEvent = new Event();
    localEvent.item = paramTableItem;
    localEvent.index = paramInt;
    localEvent.gc = localGC;
    localEvent.detail |= 16;
    localEvent.x = paramRECT.left;
    localEvent.y = paramRECT.top;
    localEvent.width = (paramRECT.right - paramRECT.left);
    localEvent.height = (paramRECT.bottom - paramRECT.top);
    sendEvent(42, localEvent);
    localEvent.gc = null;
    localGC.dispose();
    OS.RestoreDC(paramNMTTCUSTOMDRAW.hdc, i);
    return localEvent;
  }

  void setBackgroundImage(int paramInt)
  {
    super.setBackgroundImage(paramInt);
    if (paramInt != 0)
      setBackgroundTransparent(true);
    else if ((!hooks(41)) && (!hooks(40)) && (!hooks(42)))
      setBackgroundTransparent(false);
  }

  void setBackgroundPixel(int paramInt)
  {
    int i = OS.SendMessage(this.handle, 4096, 0, 0);
    if (i != -1)
    {
      if (findImageControl() != null)
        return;
      if (paramInt == -1)
        paramInt = defaultBackground();
      if (i != paramInt)
      {
        OS.SendMessage(this.handle, 4097, 0, paramInt);
        OS.SendMessage(this.handle, 4134, 0, paramInt);
        if ((this.style & 0x20) != 0)
          fixCheckboxImageListColor(true);
      }
    }
    OS.InvalidateRect(this.handle, null, true);
  }

  void setBackgroundTransparent(boolean paramBoolean)
  {
    int i = OS.SendMessage(this.handle, 4096, 0, 0);
    if (paramBoolean)
    {
      if (i != -1)
      {
        OS.SendMessage(this.handle, 4097, 0, -1);
        OS.SendMessage(this.handle, 4134, 0, -1);
        OS.InvalidateRect(this.handle, null, true);
        if ((!this.explorerTheme) && ((this.style & 0x10000) != 0))
        {
          int j = 32;
          OS.SendMessage(this.handle, 4150, j, 0);
        }
        if (((this.sortDirection & 0x480) != 0) && (this.sortColumn != null) && (!this.sortColumn.isDisposed()))
        {
          OS.SendMessage(this.handle, 4236, -1, 0);
          OS.InvalidateRect(this.handle, null, true);
        }
      }
    }
    else if (i == -1)
    {
      Object localObject = findBackgroundControl();
      if (localObject == null)
        localObject = this;
      int k;
      if (((Control)localObject).backgroundImage == null)
      {
        k = ((Control)localObject).getBackgroundPixel();
        OS.SendMessage(this.handle, 4097, 0, k);
        OS.SendMessage(this.handle, 4134, 0, k);
        if ((this.style & 0x20) != 0)
          fixCheckboxImageListColor(true);
        OS.InvalidateRect(this.handle, null, true);
      }
      if ((!this.explorerTheme) && ((this.style & 0x10000) != 0) && (!hooks(40)) && (!hooks(42)))
      {
        k = 32;
        OS.SendMessage(this.handle, 4150, k, k);
      }
      if (((this.sortDirection & 0x480) != 0) && (this.sortColumn != null) && (!this.sortColumn.isDisposed()))
      {
        k = indexOf(this.sortColumn);
        if (k != -1)
        {
          OS.SendMessage(this.handle, 4236, k, 0);
          OS.InvalidateRect(this.handle, null, true);
        }
      }
    }
  }

  void setBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, boolean paramBoolean)
  {
    setDeferResize(true);
    super.setBounds(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, false);
    setDeferResize(false);
  }

  public void setColumnOrder(int[] paramArrayOfInt)
  {
    checkWidget();
    if (paramArrayOfInt == null)
      error(4);
    int i = OS.SendMessage(this.handle, 4127, 0, 0);
    if (this.columnCount == 0)
    {
      if (paramArrayOfInt.length != 0)
        error(5);
      return;
    }
    if (paramArrayOfInt.length != this.columnCount)
      error(5);
    int[] arrayOfInt = new int[this.columnCount];
    OS.SendMessage(this.handle, 4155, this.columnCount, arrayOfInt);
    int j = 0;
    boolean[] arrayOfBoolean = new boolean[this.columnCount];
    int m;
    for (int k = 0; k < paramArrayOfInt.length; k++)
    {
      m = paramArrayOfInt[k];
      if ((m < 0) || (m >= this.columnCount))
        error(6);
      if (arrayOfBoolean[m] != 0)
        error(5);
      arrayOfBoolean[m] = true;
      if (m != arrayOfInt[k])
        j = 1;
    }
    if (j != 0)
    {
      RECT[] arrayOfRECT = new RECT[this.columnCount];
      for (m = 0; m < this.columnCount; m++)
      {
        arrayOfRECT[m] = new RECT();
        OS.SendMessage(i, 4615, m, arrayOfRECT[m]);
      }
      OS.SendMessage(this.handle, 4154, paramArrayOfInt.length, paramArrayOfInt);
      OS.InvalidateRect(this.handle, null, true);
      TableColumn[] arrayOfTableColumn = new TableColumn[this.columnCount];
      System.arraycopy(this.columns, 0, arrayOfTableColumn, 0, this.columnCount);
      RECT localRECT = new RECT();
      for (int n = 0; n < this.columnCount; n++)
      {
        TableColumn localTableColumn = arrayOfTableColumn[n];
        if (!localTableColumn.isDisposed())
        {
          OS.SendMessage(i, 4615, n, localRECT);
          if (localRECT.left != arrayOfRECT[n].left)
          {
            localTableColumn.updateToolTip(n);
            localTableColumn.sendEvent(10);
          }
        }
      }
    }
  }

  void setCustomDraw(boolean paramBoolean)
  {
    if (this.customDraw == paramBoolean)
      return;
    if ((!this.customDraw) && (paramBoolean) && (this.currentItem != null))
      OS.InvalidateRect(this.handle, null, true);
    this.customDraw = paramBoolean;
  }

  void setDeferResize(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      if (this.resizeCount++ == 0)
      {
        this.wasResized = false;
        if (((hooks(41)) || (hooks(40)) || (hooks(42))) && (this.drawCount++ == 0) && (OS.IsWindowVisible(this.handle)))
        {
          OS.DefWindowProc(this.handle, 11, 0, 0);
          OS.SendMessage(this.handle, 4097, 0, 16777215);
        }
      }
    }
    else if (--this.resizeCount == 0)
    {
      if (((hooks(41)) || (hooks(40)) || (hooks(42))) && (--this.drawCount == 0))
      {
        OS.SendMessage(this.handle, 4097, 0, -1);
        OS.DefWindowProc(this.handle, 11, 1, 0);
        int i;
        if (OS.IsWinCE)
        {
          i = OS.SendMessage(this.handle, 4127, 0, 0);
          if (i != 0)
            OS.InvalidateRect(i, null, true);
          OS.InvalidateRect(this.handle, null, true);
        }
        else
        {
          i = 1157;
          OS.RedrawWindow(this.handle, null, 0, i);
        }
      }
      if (this.wasResized)
      {
        this.wasResized = false;
        setResizeChildren(false);
        sendEvent(11);
        if (isDisposed())
          return;
        if (this.layout != null)
        {
          markLayout(false, false);
          updateLayout(false, false);
        }
        setResizeChildren(true);
      }
    }
  }

  void setCheckboxImageList(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    if ((this.style & 0x20) == 0)
      return;
    int i = 4;
    int j = 0;
    if (OS.IsWinCE)
    {
      j |= 0;
    }
    else
    {
      k = OS.GetDC(this.handle);
      m = OS.GetDeviceCaps(k, 12);
      n = OS.GetDeviceCaps(k, 14);
      OS.ReleaseDC(this.handle, k);
      i1 = m * n;
      switch (i1)
      {
      case 4:
        j |= 4;
        break;
      case 8:
        j |= 8;
        break;
      case 16:
        j |= 16;
        break;
      case 24:
        j |= 24;
        break;
      case 32:
        j |= 32;
        break;
      default:
        j |= 0;
      }
    }
    if ((this.style & 0x4000000) != 0)
      j |= 8192;
    if ((OS.COMCTL32_MAJOR < 6) || (!OS.IsAppThemed()))
      j |= 1;
    int k = OS.ImageList_Create(paramInt1, paramInt2, j, i, i);
    int m = OS.GetDC(this.handle);
    int n = OS.CreateCompatibleDC(m);
    int i1 = OS.CreateCompatibleBitmap(m, paramInt1 * i, paramInt2);
    int i2 = OS.SelectObject(n, i1);
    RECT localRECT = new RECT();
    OS.SetRect(localRECT, 0, 0, paramInt1 * i, paramInt2);
    int i3;
    if ((OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()))
    {
      Object localObject = findBackgroundControl();
      if (localObject == null)
        localObject = this;
      i3 = ((Control)localObject).getBackgroundPixel();
    }
    else
    {
      i3 = 33554687;
      if ((i3 & 0xFFFFFF) == OS.GetSysColor(OS.COLOR_WINDOW))
        i3 = 33619712;
    }
    int i4 = OS.CreateSolidBrush(i3);
    OS.FillRect(n, localRECT, i4);
    OS.DeleteObject(i4);
    int i5 = OS.SelectObject(m, defaultFont());
    TEXTMETRICA localTEXTMETRICA = OS.IsUnicode ? new TEXTMETRICW() : new TEXTMETRICA();
    OS.GetTextMetrics(m, localTEXTMETRICA);
    OS.SelectObject(m, i5);
    int i6 = Math.min(localTEXTMETRICA.tmHeight, paramInt1);
    int i7 = Math.min(localTEXTMETRICA.tmHeight, paramInt2);
    int i8 = (paramInt1 - i6) / 2;
    int i9 = (paramInt2 - i7) / 2 + 1;
    OS.SetRect(localRECT, i8, i9, i8 + i6, i9 + i7);
    if ((OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()))
    {
      i10 = this.display.hButtonTheme();
      OS.DrawThemeBackground(i10, n, 3, 1, localRECT, null);
      localRECT.left += paramInt1;
      localRECT.right += paramInt1;
      OS.DrawThemeBackground(i10, n, 3, 5, localRECT, null);
      localRECT.left += paramInt1;
      localRECT.right += paramInt1;
      OS.DrawThemeBackground(i10, n, 3, 1, localRECT, null);
      localRECT.left += paramInt1;
      localRECT.right += paramInt1;
      OS.DrawThemeBackground(i10, n, 3, 9, localRECT, null);
    }
    else
    {
      OS.DrawFrameControl(n, localRECT, 4, 16384);
      localRECT.left += paramInt1;
      localRECT.right += paramInt1;
      OS.DrawFrameControl(n, localRECT, 4, 17408);
      localRECT.left += paramInt1;
      localRECT.right += paramInt1;
      OS.DrawFrameControl(n, localRECT, 4, 16640);
      localRECT.left += paramInt1;
      localRECT.right += paramInt1;
      OS.DrawFrameControl(n, localRECT, 4, 17664);
    }
    OS.SelectObject(n, i2);
    OS.DeleteDC(n);
    OS.ReleaseDC(this.handle, m);
    if ((OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()))
      OS.ImageList_Add(k, i1, 0);
    else
      OS.ImageList_AddMasked(k, i1, i3);
    OS.DeleteObject(i1);
    int i10 = getTopIndex();
    if ((paramBoolean) && (i10 != 0))
    {
      setRedraw(false);
      setTopIndex(0);
    }
    int i11 = OS.SendMessage(this.handle, 4098, 2, 0);
    OS.SendMessage(this.handle, 4099, 2, k);
    if (i11 != 0)
      OS.ImageList_Destroy(i11);
    if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(6, 0)))
    {
      int i12 = OS.SendMessage(this.handle, 4098, 1, 0);
      OS.SendMessage(this.handle, 4099, 1, i12);
    }
    if ((paramBoolean) && (i10 != 0))
    {
      setTopIndex(i10);
      setRedraw(true);
    }
  }

  void setFocusIndex(int paramInt)
  {
    if (paramInt < 0)
      return;
    LVITEM localLVITEM = new LVITEM();
    localLVITEM.state = 1;
    localLVITEM.stateMask = 1;
    this.ignoreSelect = true;
    OS.SendMessage(this.handle, 4139, paramInt, localLVITEM);
    this.ignoreSelect = false;
    OS.SendMessage(this.handle, 4163, 0, paramInt);
  }

  public void setFont(Font paramFont)
  {
    checkWidget();
    int i = getTopIndex();
    if (i != 0)
    {
      setRedraw(false);
      setTopIndex(0);
    }
    if (this.itemHeight != -1)
    {
      j = OS.GetWindowLong(this.handle, -16);
      OS.SetWindowLong(this.handle, -16, j | 0x400);
    }
    super.setFont(paramFont);
    if (this.itemHeight != -1)
    {
      j = OS.GetWindowLong(this.handle, -16);
      OS.SetWindowLong(this.handle, -16, j & 0xFFFFFBFF);
    }
    setScrollWidth(null, true);
    if (i != 0)
    {
      setTopIndex(i);
      setRedraw(true);
    }
    int j = OS.SendMessage(this.handle, 4127, 0, 0);
    OS.InvalidateRect(j, null, true);
  }

  void setForegroundPixel(int paramInt)
  {
    if (paramInt == -1)
      paramInt = -16777216;
    OS.SendMessage(this.handle, 4132, 0, paramInt);
    OS.InvalidateRect(this.handle, null, true);
  }

  public void setHeaderVisible(boolean paramBoolean)
  {
    checkWidget();
    int i = OS.GetWindowLong(this.handle, -16);
    i &= -16385;
    if (!paramBoolean)
      i |= 16384;
    int j = getTopIndex();
    OS.SetWindowLong(this.handle, -16, i);
    int k = getTopIndex();
    if (k != 0)
    {
      setRedraw(false);
      setTopIndex(0);
    }
    if (paramBoolean)
    {
      int m = OS.SendMessage(this.handle, 4151, 0, 0);
      if ((m & 0x1) != 0)
        fixItemHeight(false);
    }
    setTopIndex(j);
    if (k != 0)
      setRedraw(true);
    updateHeaderToolTips();
  }

  public void setItemCount(int paramInt)
  {
    checkWidget();
    paramInt = Math.max(0, paramInt);
    int i = OS.SendMessage(this.handle, 4100, 0, 0);
    if (paramInt == i)
      return;
    setDeferResize(true);
    int j = (this.style & 0x10000000) != 0 ? 1 : 0;
    if (j == 0)
      setRedraw(false);
    for (int k = paramInt; k < i; k++)
    {
      TableItem localTableItem = _getItem(k, false);
      if ((localTableItem != null) && (!localTableItem.isDisposed()))
        localTableItem.release(false);
      if (j == 0)
      {
        this.ignoreSelect = (this.ignoreShrink = 1);
        int n = OS.SendMessage(this.handle, 4104, paramInt, 0);
        this.ignoreSelect = (this.ignoreShrink = 0);
        if (n == 0)
          break;
      }
    }
    if (k < i)
      error(15);
    _setItemCount(paramInt, i);
    int m;
    if (j != 0)
    {
      m = 3;
      OS.SendMessage(this.handle, 4143, paramInt, m);
      if ((paramInt == 0) && (i != 0))
        OS.InvalidateRect(this.handle, null, true);
    }
    else
    {
      for (m = i; m < paramInt; m++)
        new TableItem(this, 0, m, true);
    }
    if (j == 0)
      setRedraw(true);
    if (i == 0)
      setScrollWidth(null, false);
    setDeferResize(false);
  }

  void setItemHeight(boolean paramBoolean)
  {
    int i = getTopIndex();
    if ((paramBoolean) && (i != 0))
    {
      setRedraw(false);
      setTopIndex(0);
    }
    if (this.itemHeight == -1)
    {
      int j = OS.SendMessage(this.handle, 49, 0, 0);
      OS.SendMessage(this.handle, 48, j, 0);
    }
    else
    {
      forceResize();
      RECT localRECT = new RECT();
      OS.GetWindowRect(this.handle, localRECT);
      int k = localRECT.right - localRECT.left;
      int m = localRECT.bottom - localRECT.top;
      int n = OS.GetWindowLong(this.handle, -16);
      OS.SetWindowLong(this.handle, -16, n | 0x400);
      int i1 = 30;
      this.ignoreResize = true;
      SetWindowPos(this.handle, 0, 0, 0, k, m + 1, i1);
      SetWindowPos(this.handle, 0, 0, 0, k, m, i1);
      this.ignoreResize = false;
      OS.SetWindowLong(this.handle, -16, n);
    }
    if ((paramBoolean) && (i != 0))
    {
      setTopIndex(i);
      setRedraw(true);
    }
  }

  void setItemHeight(int paramInt)
  {
    checkWidget();
    if (paramInt < -1)
      error(5);
    this.itemHeight = paramInt;
    setItemHeight(true);
    setScrollWidth(null, true);
  }

  public void setLinesVisible(boolean paramBoolean)
  {
    checkWidget();
    int i = paramBoolean ? 1 : 0;
    OS.SendMessage(this.handle, 4150, 1, i);
    if (paramBoolean)
    {
      int j = OS.GetWindowLong(this.handle, -16);
      if ((j & 0x4000) == 0)
        fixItemHeight(true);
    }
  }

  public void setRedraw(boolean paramBoolean)
  {
    checkWidget();
    int i;
    if (this.drawCount == 0)
    {
      i = OS.GetWindowLong(this.handle, -16);
      if ((i & 0x10000000) == 0)
        this.state |= 16;
    }
    if (paramBoolean)
    {
      if (--this.drawCount == 0)
      {
        setScrollWidth(null, true);
        setDeferResize(true);
        OS.SendMessage(this.handle, 11, 1, 0);
        i = OS.SendMessage(this.handle, 4127, 0, 0);
        if (i != 0)
          OS.SendMessage(i, 11, 1, 0);
        if ((this.state & 0x10) != 0)
        {
          this.state &= -17;
          OS.ShowWindow(this.handle, 0);
        }
        else if (OS.IsWinCE)
        {
          OS.InvalidateRect(this.handle, null, false);
          if (i != 0)
            OS.InvalidateRect(i, null, false);
        }
        else
        {
          int j = 1157;
          OS.RedrawWindow(this.handle, null, 0, j);
        }
        setDeferResize(false);
      }
    }
    else if (this.drawCount++ == 0)
    {
      OS.SendMessage(this.handle, 11, 0, 0);
      i = OS.SendMessage(this.handle, 4127, 0, 0);
      if (i != 0)
        OS.SendMessage(i, 11, 0, 0);
    }
  }

  void setScrollWidth(int paramInt)
  {
    if (paramInt != OS.SendMessage(this.handle, 4125, 0, 0))
    {
      int i = 0;
      if (hooks(41))
        i = (getDrawing()) && (OS.IsWindowVisible(this.handle)) ? 1 : 0;
      if (i != 0)
        OS.DefWindowProc(this.handle, 11, 0, 0);
      OS.SendMessage(this.handle, 4126, 0, paramInt);
      if (i != 0)
      {
        OS.DefWindowProc(this.handle, 11, 1, 0);
        int j;
        if (OS.IsWinCE)
        {
          j = OS.SendMessage(this.handle, 4127, 0, 0);
          if (j != 0)
            OS.InvalidateRect(j, null, true);
          OS.InvalidateRect(this.handle, null, true);
        }
        else
        {
          j = 1157;
          OS.RedrawWindow(this.handle, null, 0, j);
        }
      }
    }
  }

  boolean setScrollWidth(TableItem paramTableItem, boolean paramBoolean)
  {
    if (this.currentItem != null)
    {
      if (this.currentItem != paramTableItem)
        this.fixScrollWidth = true;
      return false;
    }
    if ((!paramBoolean) && ((!getDrawing()) || (!OS.IsWindowVisible(this.handle))))
    {
      this.fixScrollWidth = true;
      return false;
    }
    this.fixScrollWidth = false;
    if (this.columnCount == 0)
    {
      int i = 0;
      int j = 0;
      int k = 0;
      int m = OS.SendMessage(this.handle, 4100, 0, 0);
      Object localObject1;
      Object localObject2;
      while (k < m)
      {
        localObject1 = null;
        int i1 = -1;
        if (paramTableItem != null)
        {
          localObject1 = paramTableItem.text;
          j = Math.max(j, paramTableItem.imageIndent);
          i1 = paramTableItem.fontHandle(0);
        }
        else
        {
          TableItem localTableItem = _getItem(k, false);
          if (localTableItem != null)
          {
            localObject1 = localTableItem.text;
            j = Math.max(j, localTableItem.imageIndent);
            i1 = localTableItem.fontHandle(0);
          }
        }
        if ((localObject1 != null) && (((String)localObject1).length() != 0))
          if (i1 != -1)
          {
            int i3 = OS.GetDC(this.handle);
            int i5 = OS.SelectObject(i3, i1);
            int i6 = 3104;
            TCHAR localTCHAR = new TCHAR(getCodePage(), (String)localObject1, false);
            RECT localRECT = new RECT();
            OS.DrawText(i3, localTCHAR, localTCHAR.length(), localRECT, i6);
            OS.SelectObject(i3, i5);
            OS.ReleaseDC(this.handle, i3);
            i = Math.max(i, localRECT.right - localRECT.left);
          }
          else
          {
            localObject2 = new TCHAR(getCodePage(), (String)localObject1, true);
            i = Math.max(i, OS.SendMessage(this.handle, OS.LVM_GETSTRINGWIDTH, 0, (TCHAR)localObject2));
          }
        if (paramTableItem != null)
          break;
        k++;
      }
      if (i == 0)
      {
        localObject1 = new TCHAR(getCodePage(), " ", true);
        i = Math.max(i, OS.SendMessage(this.handle, OS.LVM_GETSTRINGWIDTH, 0, (TCHAR)localObject1));
      }
      int n = OS.SendMessage(this.handle, 4098, 2, 0);
      if (n != 0)
      {
        int[] arrayOfInt1 = new int[1];
        localObject2 = new int[1];
        OS.ImageList_GetIconSize(n, arrayOfInt1, (int[])localObject2);
        i += arrayOfInt1[0] + 4;
      }
      int i2 = OS.SendMessage(this.handle, 4098, 1, 0);
      if (i2 != 0)
      {
        localObject2 = new int[1];
        int[] arrayOfInt2 = new int[1];
        OS.ImageList_GetIconSize(i2, (int[])localObject2, arrayOfInt2);
        i += (j + 1) * localObject2[0];
      }
      else
      {
        i++;
      }
      i += 8;
      int i4 = OS.SendMessage(this.handle, 4125, 0, 0);
      if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(6, 0)))
        i += 2;
      if (i > i4)
      {
        setScrollWidth(i);
        return true;
      }
    }
    return false;
  }

  public void setSelection(int[] paramArrayOfInt)
  {
    checkWidget();
    if (paramArrayOfInt == null)
      error(4);
    deselectAll();
    int i = paramArrayOfInt.length;
    if ((i == 0) || (((this.style & 0x4) != 0) && (i > 1)))
      return;
    select(paramArrayOfInt);
    int j = paramArrayOfInt[0];
    if (j != -1)
      setFocusIndex(j);
    showSelection();
  }

  public void setSelection(TableItem paramTableItem)
  {
    checkWidget();
    if (paramTableItem == null)
      error(4);
    setSelection(new TableItem[] { paramTableItem });
  }

  public void setSelection(TableItem[] paramArrayOfTableItem)
  {
    checkWidget();
    if (paramArrayOfTableItem == null)
      error(4);
    deselectAll();
    int i = paramArrayOfTableItem.length;
    if ((i == 0) || (((this.style & 0x4) != 0) && (i > 1)))
      return;
    int j = -1;
    for (int k = i - 1; k >= 0; k--)
    {
      int m = indexOf(paramArrayOfTableItem[k]);
      if (m != -1)
        select(j = m);
    }
    if (j != -1)
      setFocusIndex(j);
    showSelection();
  }

  public void setSelection(int paramInt)
  {
    checkWidget();
    deselectAll();
    select(paramInt);
    if (paramInt != -1)
      setFocusIndex(paramInt);
    showSelection();
  }

  public void setSelection(int paramInt1, int paramInt2)
  {
    checkWidget();
    deselectAll();
    if ((paramInt2 < 0) || (paramInt1 > paramInt2) || (((this.style & 0x4) != 0) && (paramInt1 != paramInt2)))
      return;
    int i = OS.SendMessage(this.handle, 4100, 0, 0);
    if ((i == 0) || (paramInt1 >= i))
      return;
    paramInt1 = Math.max(0, paramInt1);
    paramInt2 = Math.min(paramInt2, i - 1);
    select(paramInt1, paramInt2);
    setFocusIndex(paramInt1);
    showSelection();
  }

  public void setSortColumn(TableColumn paramTableColumn)
  {
    checkWidget();
    if ((paramTableColumn != null) && (paramTableColumn.isDisposed()))
      error(5);
    if ((this.sortColumn != null) && (!this.sortColumn.isDisposed()))
      this.sortColumn.setSortDirection(0);
    this.sortColumn = paramTableColumn;
    if ((this.sortColumn != null) && (this.sortDirection != 0))
      this.sortColumn.setSortDirection(this.sortDirection);
  }

  public void setSortDirection(int paramInt)
  {
    checkWidget();
    if (((paramInt & 0x480) == 0) && (paramInt != 0))
      return;
    this.sortDirection = paramInt;
    if ((this.sortColumn != null) && (!this.sortColumn.isDisposed()))
      this.sortColumn.setSortDirection(paramInt);
  }

  void setSubImagesVisible(boolean paramBoolean)
  {
    int i = OS.SendMessage(this.handle, 4151, 0, 0);
    if (((i & 0x2) != 0) == paramBoolean)
      return;
    int j = paramBoolean ? 2 : 0;
    OS.SendMessage(this.handle, 4150, 2, j);
  }

  void setTableEmpty()
  {
    if (this.imageList != null)
    {
      int i = OS.ImageList_Create(1, 1, 0, 0, 0);
      OS.SendMessage(this.handle, 4099, 1, i);
      OS.SendMessage(this.handle, 4099, 1, 0);
      if (this.headerImageList != null)
      {
        int j = this.headerImageList.getHandle();
        int k = OS.SendMessage(this.handle, 4127, 0, 0);
        OS.SendMessage(k, 4616, 0, j);
      }
      OS.ImageList_Destroy(i);
      this.display.releaseImageList(this.imageList);
      this.imageList = null;
      if (this.itemHeight != -1)
        setItemHeight(false);
    }
    if ((!hooks(41)) && (!hooks(40)) && (!hooks(42)))
    {
      Object localObject = findBackgroundControl();
      if (localObject == null)
        localObject = this;
      if (((Control)localObject).backgroundImage == null)
      {
        setCustomDraw(false);
        setBackgroundTransparent(false);
        if (OS.COMCTL32_MAJOR < 6)
          this.style &= -536870913;
      }
    }
    _initItems();
    if (this.columnCount == 0)
    {
      OS.SendMessage(this.handle, 4126, 0, 0);
      setScrollWidth(null, false);
    }
  }

  public void setTopIndex(int paramInt)
  {
    checkWidget();
    int i = OS.SendMessage(this.handle, 4135, 0, 0);
    if (paramInt == i)
      return;
    if ((!this.painted) && (hooks(41)))
      hitTestSelection(paramInt, 0, 0);
    if (OS.SendMessage(this.handle, 4136, 0, 0) <= 0)
    {
      OS.SendMessage(this.handle, 4115, paramInt, 1);
      if (paramInt != OS.SendMessage(this.handle, 4135, 0, 0))
        OS.SendMessage(this.handle, 4115, paramInt, 1);
      return;
    }
    RECT localRECT = new RECT();
    localRECT.left = 0;
    this.ignoreCustomDraw = true;
    OS.SendMessage(this.handle, 4110, 0, localRECT);
    this.ignoreCustomDraw = false;
    int j = (paramInt - i) * (localRECT.bottom - localRECT.top);
    OS.SendMessage(this.handle, 4116, 0, j);
  }

  public void showColumn(TableColumn paramTableColumn)
  {
    checkWidget();
    if (paramTableColumn == null)
      error(4);
    if (paramTableColumn.isDisposed())
      error(5);
    if (paramTableColumn.parent != this)
      return;
    int i = indexOf(paramTableColumn);
    if ((i < 0) || (i >= this.columnCount))
      return;
    RECT localRECT = new RECT();
    localRECT.left = 0;
    if (i == 0)
    {
      localRECT.top = 1;
      this.ignoreCustomDraw = true;
      OS.SendMessage(this.handle, 4152, -1, localRECT);
      this.ignoreCustomDraw = false;
      localRECT.right = localRECT.left;
      j = OS.SendMessage(this.handle, 4125, 0, 0);
      localRECT.left = (localRECT.right - j);
    }
    else
    {
      localRECT.top = i;
      this.ignoreCustomDraw = true;
      OS.SendMessage(this.handle, 4152, -1, localRECT);
      this.ignoreCustomDraw = false;
    }
    int j = 0;
    int k = OS.SendMessage(this.handle, 4151, 0, 0);
    if ((k & 0x1) != 0)
    {
      localObject = new SCROLLINFO();
      ((SCROLLINFO)localObject).cbSize = SCROLLINFO.sizeof;
      ((SCROLLINFO)localObject).fMask = 4;
      OS.GetScrollInfo(this.handle, 0, (SCROLLINFO)localObject);
      j = ((SCROLLINFO)localObject).nPos;
    }
    Object localObject = new RECT();
    OS.GetClientRect(this.handle, (RECT)localObject);
    int m;
    int n;
    if (localRECT.left < ((RECT)localObject).left)
    {
      m = localRECT.left - ((RECT)localObject).left;
      OS.SendMessage(this.handle, 4116, m, 0);
    }
    else
    {
      m = Math.min(((RECT)localObject).right - ((RECT)localObject).left, localRECT.right - localRECT.left);
      if (localRECT.left + m > ((RECT)localObject).right)
      {
        n = localRECT.left + m - ((RECT)localObject).right;
        OS.SendMessage(this.handle, 4116, n, 0);
      }
    }
    if ((k & 0x1) != 0)
    {
      SCROLLINFO localSCROLLINFO = new SCROLLINFO();
      localSCROLLINFO.cbSize = SCROLLINFO.sizeof;
      localSCROLLINFO.fMask = 4;
      OS.GetScrollInfo(this.handle, 0, localSCROLLINFO);
      n = localSCROLLINFO.nPos;
      if (n < j)
      {
        ((RECT)localObject).right = (j - n + 1);
        OS.InvalidateRect(this.handle, (RECT)localObject, true);
      }
    }
  }

  void showItem(int paramInt)
  {
    if ((!this.painted) && (hooks(41)))
      hitTestSelection(paramInt, 0, 0);
    if (OS.SendMessage(this.handle, 4136, 0, 0) <= 0)
    {
      OS.SendMessage(this.handle, 4115, paramInt, 1);
      if (paramInt != OS.SendMessage(this.handle, 4135, 0, 0))
        OS.SendMessage(this.handle, 4115, paramInt, 1);
    }
    else
    {
      OS.SendMessage(this.handle, 4115, paramInt, 0);
    }
  }

  public void showItem(TableItem paramTableItem)
  {
    checkWidget();
    if (paramTableItem == null)
      error(4);
    if (paramTableItem.isDisposed())
      error(5);
    int i = indexOf(paramTableItem);
    if (i != -1)
      showItem(i);
  }

  public void showSelection()
  {
    checkWidget();
    int i = OS.SendMessage(this.handle, 4108, -1, 2);
    if (i != -1)
      showItem(i);
  }

  void sort()
  {
    checkWidget();
  }

  void subclass()
  {
    super.subclass();
    if (HeaderProc != 0)
    {
      int i = OS.SendMessage(this.handle, 4127, 0, 0);
      OS.SetWindowLongPtr(i, -4, this.display.windowProc);
    }
  }

  RECT toolTipInset(RECT paramRECT)
  {
    if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(6, 0)))
    {
      RECT localRECT = new RECT();
      OS.SetRect(localRECT, paramRECT.left - 1, paramRECT.top - 1, paramRECT.right + 1, paramRECT.bottom + 1);
      return localRECT;
    }
    return paramRECT;
  }

  RECT toolTipRect(RECT paramRECT)
  {
    RECT localRECT = new RECT();
    if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(6, 0)))
    {
      OS.SetRect(localRECT, paramRECT.left - 1, paramRECT.top - 1, paramRECT.right + 1, paramRECT.bottom + 1);
    }
    else
    {
      int i = OS.SendMessage(this.handle, 4174, 0, 0);
      OS.SetRect(localRECT, paramRECT.left, paramRECT.top, paramRECT.right, paramRECT.bottom);
      int j = OS.GetWindowLong(i, -16);
      int k = OS.GetWindowLong(i, -20);
      OS.AdjustWindowRectEx(localRECT, j, false, k);
    }
    return localRECT;
  }

  String toolTipText(NMTTDISPINFO paramNMTTDISPINFO)
  {
    int i = OS.SendMessage(this.handle, 4174, 0, 0);
    if ((i == paramNMTTDISPINFO.hwndFrom) && (this.toolTipText != null))
      return "";
    if (this.headerToolTipHandle == paramNMTTDISPINFO.hwndFrom)
      for (int j = 0; j < this.columnCount; j++)
      {
        TableColumn localTableColumn = this.columns[j];
        if (localTableColumn.id == paramNMTTDISPINFO.idFrom)
          return localTableColumn.toolTipText;
      }
    return super.toolTipText(paramNMTTDISPINFO);
  }

  void unsubclass()
  {
    super.unsubclass();
    if (HeaderProc != 0)
    {
      int i = OS.SendMessage(this.handle, 4127, 0, 0);
      OS.SetWindowLongPtr(i, -4, HeaderProc);
    }
  }

  void update(boolean paramBoolean)
  {
    int i = 0;
    int j = 0;
    int k = OS.SendMessage(this.handle, 4127, 0, 0);
    boolean bool = isOptimizedRedraw();
    if (bool)
    {
      j = OS.SetWindowLongPtr(this.handle, -4, TableProc);
      i = OS.SetWindowLongPtr(k, -4, HeaderProc);
    }
    super.update(paramBoolean);
    if (bool)
    {
      OS.SetWindowLongPtr(this.handle, -4, j);
      OS.SetWindowLongPtr(k, -4, i);
    }
  }

  void updateHeaderToolTips()
  {
    if (this.headerToolTipHandle == 0)
      return;
    int i = OS.SendMessage(this.handle, 4127, 0, 0);
    RECT localRECT = new RECT();
    TOOLINFO localTOOLINFO = new TOOLINFO();
    localTOOLINFO.cbSize = TOOLINFO.sizeof;
    localTOOLINFO.uFlags = 16;
    localTOOLINFO.hwnd = i;
    localTOOLINFO.lpszText = -1;
    for (int j = 0; j < this.columnCount; j++)
    {
      TableColumn localTableColumn = this.columns[j];
      if (OS.SendMessage(i, 4615, j, localRECT) != 0)
      {
        localTOOLINFO.uId = (localTableColumn.id = this.display.nextToolTipId++);
        localTOOLINFO.left = localRECT.left;
        localTOOLINFO.top = localRECT.top;
        localTOOLINFO.right = localRECT.right;
        localTOOLINFO.bottom = localRECT.bottom;
        OS.SendMessage(this.headerToolTipHandle, OS.TTM_ADDTOOL, 0, localTOOLINFO);
      }
    }
  }

  void updateImages()
  {
    if ((this.sortColumn != null) && (!this.sortColumn.isDisposed()) && (OS.COMCTL32_MAJOR < 6))
      switch (this.sortDirection)
      {
      case 128:
      case 1024:
        this.sortColumn.setImage(this.display.getSortImage(this.sortDirection), true, true);
      }
  }

  void updateMoveable()
  {
    for (int i = 0; i < this.columnCount; i++)
      if (this.columns[i].moveable)
        break;
    int j = i < this.columnCount ? 16 : 0;
    OS.SendMessage(this.handle, 4150, 16, j);
  }

  int widgetStyle()
  {
    int i = super.widgetStyle() | 0x40;
    if ((this.style & 0x8000) == 0)
      i |= 8;
    if ((this.style & 0x4) != 0)
      i |= 4;
    i |= 16385;
    if ((this.style & 0x10000000) != 0)
      i |= 4096;
    return i;
  }

  TCHAR windowClass()
  {
    return TableClass;
  }

  int windowProc()
  {
    return TableProc;
  }

  int windowProc(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.handle == 0)
      return 0;
    int j;
    Object localObject;
    if (paramInt1 != this.handle)
    {
      switch (paramInt2)
      {
      case 123:
        LRESULT localLRESULT = wmContextMenu(paramInt1, paramInt3, paramInt4);
        if (localLRESULT != null)
          return localLRESULT.value;
        break;
      case 533:
        if ((OS.COMCTL32_MAJOR < 6) && (paramInt4 != 0))
        {
          int i = OS.SendMessage(this.handle, 4127, 0, 0);
          if (paramInt4 != i)
            OS.InvalidateRect(i, null, true);
        }
        break;
      case 675:
        if (OS.COMCTL32_MAJOR >= 6)
          updateHeaderToolTips();
        updateHeaderToolTips();
        break;
      case 78:
        NMHDR localNMHDR = new NMHDR();
        OS.MoveMemory(localNMHDR, paramInt4, NMHDR.sizeof);
        switch (localNMHDR.code)
        {
        case -530:
        case -522:
        case -521:
        case -520:
          return OS.SendMessage(this.handle, paramInt2, paramInt3, paramInt4);
        }
        break;
      case 32:
        if (paramInt3 == paramInt1)
        {
          j = (short)OS.LOWORD(paramInt4);
          if (j == 1)
          {
            HDHITTESTINFO localHDHITTESTINFO = new HDHITTESTINFO();
            int m = OS.GetMessagePos();
            localObject = new POINT();
            OS.POINTSTOPOINT((POINT)localObject, m);
            OS.ScreenToClient(paramInt1, (POINT)localObject);
            localHDHITTESTINFO.x = ((POINT)localObject).x;
            localHDHITTESTINFO.y = ((POINT)localObject).y;
            int n = OS.SendMessage(this.handle, 4127, 0, 0);
            int i1 = OS.SendMessage(n, 4614, 0, localHDHITTESTINFO);
            if ((i1 >= 0) && (i1 < this.columnCount) && (!this.columns[i1].resizable) && ((localHDHITTESTINFO.flags & 0xC) != 0))
            {
              OS.SetCursor(OS.LoadCursor(0, 32512));
              return 1;
            }
          }
        }
        break;
      }
      return callWindowProc(paramInt1, paramInt2, paramInt3, paramInt4);
    }
    if ((paramInt2 == Display.DI_GETDRAGIMAGE) && (((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(6, 0))) || ((this.style & 0x10000000) != 0) || (hooks(40)) || (hooks(42))))
    {
      j = OS.SendMessage(this.handle, 4135, 0, 0);
      int k = OS.SendMessage(this.handle, 4108, j - 1, 2);
      if (k == -1)
        return 0;
      POINT localPOINT1 = new POINT();
      OS.POINTSTOPOINT(localPOINT1, OS.GetMessagePos());
      OS.MapWindowPoints(0, this.handle, localPOINT1, 1);
      localObject = new RECT();
      OS.GetClientRect(this.handle, (RECT)localObject);
      TableItem localTableItem = _getItem(k);
      RECT localRECT1 = localTableItem.getBounds(k, 0, true, true, true);
      if ((this.style & 0x10000) != 0)
      {
        i2 = 301;
        localRECT1.left = Math.max(((RECT)localObject).left, localPOINT1.x - i2 / 2);
        if (((RECT)localObject).right > localRECT1.left + i2)
        {
          localRECT1.right = (localRECT1.left + i2);
        }
        else
        {
          localRECT1.right = ((RECT)localObject).right;
          localRECT1.left = Math.max(((RECT)localObject).left, localRECT1.right - i2);
        }
      }
      int i2 = OS.CreateRectRgn(localRECT1.left, localRECT1.top, localRECT1.right, localRECT1.bottom);
      while ((k = OS.SendMessage(this.handle, 4108, k, 2)) != -1)
      {
        if ((localRECT1.bottom - localRECT1.top > 301) || (localRECT1.bottom > ((RECT)localObject).bottom))
          break;
        RECT localRECT2 = localTableItem.getBounds(k, 0, true, true, true);
        i4 = OS.CreateRectRgn(localRECT1.left, localRECT2.top, localRECT1.right, localRECT2.bottom);
        OS.CombineRgn(i2, i2, i4, 2);
        OS.DeleteObject(i4);
        localRECT1.bottom = localRECT2.bottom;
      }
      OS.GetRgnBox(i2, localRECT1);
      int i3 = OS.GetDC(this.handle);
      int i4 = OS.CreateCompatibleDC(i3);
      BITMAPINFOHEADER localBITMAPINFOHEADER = new BITMAPINFOHEADER();
      localBITMAPINFOHEADER.biSize = BITMAPINFOHEADER.sizeof;
      localBITMAPINFOHEADER.biWidth = (localRECT1.right - localRECT1.left);
      localBITMAPINFOHEADER.biHeight = (-(localRECT1.bottom - localRECT1.top));
      localBITMAPINFOHEADER.biPlanes = 1;
      localBITMAPINFOHEADER.biBitCount = 32;
      localBITMAPINFOHEADER.biCompression = 0;
      byte[] arrayOfByte = new byte[BITMAPINFOHEADER.sizeof];
      OS.MoveMemory(arrayOfByte, localBITMAPINFOHEADER, BITMAPINFOHEADER.sizeof);
      int[] arrayOfInt = new int[1];
      int i5 = OS.CreateDIBSection(0, arrayOfByte, 0, arrayOfInt, 0, 0);
      if (i5 == 0)
        SWT.error(2);
      int i6 = OS.SelectObject(i4, i5);
      int i7 = 253;
      POINT localPOINT2 = new POINT();
      OS.SetWindowOrgEx(i4, localRECT1.left, localRECT1.top, localPOINT2);
      OS.FillRect(i4, localRECT1, findBrush(i7, 0));
      OS.OffsetRgn(i2, -localRECT1.left, -localRECT1.top);
      OS.SelectClipRgn(i4, i2);
      OS.PrintWindow(this.handle, i4, 0);
      OS.SetWindowOrgEx(i4, localPOINT2.x, localPOINT2.y, null);
      OS.SelectObject(i4, i6);
      OS.DeleteDC(i4);
      OS.ReleaseDC(0, i3);
      OS.DeleteObject(i2);
      SHDRAGIMAGE localSHDRAGIMAGE = new SHDRAGIMAGE();
      localSHDRAGIMAGE.hbmpDragImage = i5;
      localSHDRAGIMAGE.crColorKey = i7;
      localSHDRAGIMAGE.sizeDragImage.cx = localBITMAPINFOHEADER.biWidth;
      localSHDRAGIMAGE.sizeDragImage.cy = (-localBITMAPINFOHEADER.biHeight);
      localSHDRAGIMAGE.ptOffset.x = (localPOINT1.x - localRECT1.left);
      localSHDRAGIMAGE.ptOffset.y = (localPOINT1.y - localRECT1.top);
      if ((this.style & 0x8000000) != 0)
        localSHDRAGIMAGE.ptOffset.x = (localSHDRAGIMAGE.sizeDragImage.cx - localSHDRAGIMAGE.ptOffset.x);
      OS.MoveMemory(paramInt4, localSHDRAGIMAGE, SHDRAGIMAGE.sizeof);
      return 1;
    }
    return super.windowProc(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  LRESULT WM_CHAR(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_CHAR(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    switch (paramInt1)
    {
    case 32:
      if ((this.style & 0x20) != 0)
      {
        i = OS.SendMessage(this.handle, 4108, -1, 1);
        if (i != -1)
        {
          TableItem localTableItem = _getItem(i);
          localTableItem.setChecked(!localTableItem.getChecked(), true);
          if (!OS.IsWinCE)
            OS.NotifyWinEvent(32773, this.handle, -4, i + 1);
        }
      }
      int i = callWindowProc(this.handle, 256, paramInt1, paramInt2);
      return new LRESULT(i);
    case 13:
      int j = OS.SendMessage(this.handle, 4108, -1, 1);
      if (j != -1)
      {
        Event localEvent = new Event();
        localEvent.item = _getItem(j);
        sendSelectionEvent(14, localEvent, false);
      }
      return LRESULT.ZERO;
    }
    return localLRESULT;
  }

  LRESULT WM_CONTEXTMENU(int paramInt1, int paramInt2)
  {
    if (!this.display.runDragDrop)
      return LRESULT.ZERO;
    return super.WM_CONTEXTMENU(paramInt1, paramInt2);
  }

  LRESULT WM_ERASEBKGND(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_ERASEBKGND(paramInt1, paramInt2);
    if (findImageControl() != null)
      return LRESULT.ONE;
    if ((!OS.IsWinCE) && (OS.COMCTL32_MAJOR < 6) && ((this.style & 0x20000000) != 0))
    {
      int i = OS.SendMessage(this.handle, 4151, 0, 0);
      if ((i & 0x10000) == 0)
        return LRESULT.ONE;
    }
    return localLRESULT;
  }

  LRESULT WM_GETOBJECT(int paramInt1, int paramInt2)
  {
    if (((this.style & 0x20) != 0) && (this.accessible == null))
      this.accessible = new_Accessible(this);
    return super.WM_GETOBJECT(paramInt1, paramInt2);
  }

  LRESULT WM_KEYDOWN(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_KEYDOWN(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    int i;
    int k;
    switch (paramInt1)
    {
    case 32:
      return LRESULT.ZERO;
    case 107:
      if (OS.GetKeyState(17) < 0)
      {
        for (i = 0; i < this.columnCount; i++)
          if (!this.columns[i].getResizable())
            break;
        if ((i != this.columnCount) || (hooks(41)))
        {
          TableColumn[] arrayOfTableColumn = new TableColumn[this.columnCount];
          System.arraycopy(this.columns, 0, arrayOfTableColumn, 0, this.columnCount);
          for (k = 0; k < arrayOfTableColumn.length; k++)
          {
            TableColumn localTableColumn = arrayOfTableColumn[k];
            if ((!localTableColumn.isDisposed()) && (localTableColumn.getResizable()))
              localTableColumn.pack();
          }
          return LRESULT.ZERO;
        }
      }
      break;
    case 33:
    case 34:
    case 35:
    case 36:
      i = 0;
      int j = 0;
      k = OS.SendMessage(this.handle, 4127, 0, 0);
      boolean bool = isOptimizedRedraw();
      if (bool)
      {
        j = OS.SetWindowLongPtr(this.handle, -4, TableProc);
        i = OS.SetWindowLongPtr(k, -4, HeaderProc);
      }
      int m = callWindowProc(this.handle, 256, paramInt1, paramInt2);
      localLRESULT = m == 0 ? LRESULT.ZERO : new LRESULT(m);
      if (bool)
      {
        OS.SetWindowLongPtr(this.handle, -4, j);
        OS.SetWindowLongPtr(k, -4, i);
      }
    case 38:
    case 40:
      OS.SendMessage(this.handle, 295, 3, 0);
    }
    return localLRESULT;
  }

  LRESULT WM_KILLFOCUS(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_KILLFOCUS(paramInt1, paramInt2);
    if ((this.imageList != null) || ((this.style & 0x20) != 0))
      OS.InvalidateRect(this.handle, null, false);
    return localLRESULT;
  }

  LRESULT WM_LBUTTONDBLCLK(int paramInt1, int paramInt2)
  {
    LVHITTESTINFO localLVHITTESTINFO = new LVHITTESTINFO();
    localLVHITTESTINFO.x = OS.GET_X_LPARAM(paramInt2);
    localLVHITTESTINFO.y = OS.GET_Y_LPARAM(paramInt2);
    int i = OS.SendMessage(this.handle, 4114, 0, localLVHITTESTINFO);
    Display localDisplay = this.display;
    localDisplay.captureChanged = false;
    sendMouseEvent(3, 1, this.handle, 513, paramInt1, paramInt2);
    if (!sendMouseEvent(8, 1, this.handle, 515, paramInt1, paramInt2))
    {
      if ((!localDisplay.captureChanged) && (!isDisposed()) && (OS.GetCapture() != this.handle))
        OS.SetCapture(this.handle);
      return LRESULT.ZERO;
    }
    if (localLVHITTESTINFO.iItem != -1)
      callWindowProc(this.handle, 515, paramInt1, paramInt2);
    if ((!localDisplay.captureChanged) && (!isDisposed()) && (OS.GetCapture() != this.handle))
      OS.SetCapture(this.handle);
    if (((this.style & 0x20) != 0) && (i != -1) && (localLVHITTESTINFO.flags == 8))
    {
      TableItem localTableItem = _getItem(i);
      localTableItem.setChecked(!localTableItem.getChecked(), true);
      if (!OS.IsWinCE)
        OS.NotifyWinEvent(32773, this.handle, -4, i + 1);
    }
    return LRESULT.ZERO;
  }

  LRESULT WM_LBUTTONDOWN(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = sendMouseDownEvent(3, 1, 513, paramInt1, paramInt2);
    if (localLRESULT == LRESULT.ZERO)
      return localLRESULT;
    if ((this.style & 0x20) != 0)
    {
      LVHITTESTINFO localLVHITTESTINFO = new LVHITTESTINFO();
      localLVHITTESTINFO.x = OS.GET_X_LPARAM(paramInt2);
      localLVHITTESTINFO.y = OS.GET_Y_LPARAM(paramInt2);
      int i = OS.SendMessage(this.handle, 4114, 0, localLVHITTESTINFO);
      if ((i != -1) && (localLVHITTESTINFO.flags == 8))
      {
        TableItem localTableItem = _getItem(i);
        localTableItem.setChecked(!localTableItem.getChecked(), true);
        if (!OS.IsWinCE)
          OS.NotifyWinEvent(32773, this.handle, -4, i + 1);
      }
    }
    return localLRESULT;
  }

  LRESULT WM_MOUSEHOVER(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_MOUSEHOVER(paramInt1, paramInt2);
    int i = OS.SendMessage(this.handle, 4151, 0, 0);
    int j = 200;
    if ((i & j) != 0)
      return localLRESULT;
    return LRESULT.ZERO;
  }

  LRESULT WM_PAINT(int paramInt1, int paramInt2)
  {
    _checkShrink();
    if (this.fixScrollWidth)
      setScrollWidth(null, true);
    if ((!OS.IsWinCE) && (OS.COMCTL32_MAJOR < 6) && (((this.style & 0x20000000) != 0) || (findImageControl() != null)))
    {
      int i = OS.SendMessage(this.handle, 4151, 0, 0);
      if ((i & 0x10000) == 0)
      {
        GC localGC = null;
        int j = 0;
        PAINTSTRUCT localPAINTSTRUCT = new PAINTSTRUCT();
        int k = (!hooks(9)) && (!filters(9)) ? 0 : 1;
        if (k != 0)
        {
          GCData localGCData = new GCData();
          localGCData.ps = localPAINTSTRUCT;
          localGCData.hwnd = this.handle;
          localGC = GC.win32_new(this, localGCData);
          j = localGC.handle;
        }
        else
        {
          j = OS.BeginPaint(this.handle, localPAINTSTRUCT);
        }
        int m = localPAINTSTRUCT.right - localPAINTSTRUCT.left;
        int n = localPAINTSTRUCT.bottom - localPAINTSTRUCT.top;
        if ((m != 0) && (n != 0))
        {
          int i1 = OS.CreateCompatibleDC(j);
          POINT localPOINT1 = new POINT();
          POINT localPOINT2 = new POINT();
          OS.SetWindowOrgEx(i1, localPAINTSTRUCT.left, localPAINTSTRUCT.top, localPOINT1);
          OS.SetBrushOrgEx(i1, localPAINTSTRUCT.left, localPAINTSTRUCT.top, localPOINT2);
          int i2 = OS.CreateCompatibleBitmap(j, m, n);
          int i3 = OS.SelectObject(i1, i2);
          Object localObject;
          if (OS.SendMessage(this.handle, 4096, 0, 0) != -1)
          {
            localObject = new RECT();
            OS.SetRect((RECT)localObject, localPAINTSTRUCT.left, localPAINTSTRUCT.top, localPAINTSTRUCT.right, localPAINTSTRUCT.bottom);
            drawBackground(i1, (RECT)localObject);
          }
          callWindowProc(this.handle, 15, i1, 0);
          OS.SetWindowOrgEx(i1, localPOINT1.x, localPOINT1.y, null);
          OS.SetBrushOrgEx(i1, localPOINT2.x, localPOINT2.y, null);
          OS.BitBlt(j, localPAINTSTRUCT.left, localPAINTSTRUCT.top, m, n, i1, 0, 0, 13369376);
          OS.SelectObject(i1, i3);
          OS.DeleteObject(i2);
          OS.DeleteObject(i1);
          if (k != 0)
          {
            localObject = new Event();
            ((Event)localObject).gc = localGC;
            ((Event)localObject).x = localPAINTSTRUCT.left;
            ((Event)localObject).y = localPAINTSTRUCT.top;
            ((Event)localObject).width = (localPAINTSTRUCT.right - localPAINTSTRUCT.left);
            ((Event)localObject).height = (localPAINTSTRUCT.bottom - localPAINTSTRUCT.top);
            sendEvent(9, (Event)localObject);
            ((Event)localObject).gc = null;
          }
        }
        if (k != 0)
          localGC.dispose();
        else
          OS.EndPaint(this.handle, localPAINTSTRUCT);
        return LRESULT.ZERO;
      }
    }
    return super.WM_PAINT(paramInt1, paramInt2);
  }

  LRESULT WM_RBUTTONDBLCLK(int paramInt1, int paramInt2)
  {
    LVHITTESTINFO localLVHITTESTINFO = new LVHITTESTINFO();
    localLVHITTESTINFO.x = OS.GET_X_LPARAM(paramInt2);
    localLVHITTESTINFO.y = OS.GET_Y_LPARAM(paramInt2);
    OS.SendMessage(this.handle, 4114, 0, localLVHITTESTINFO);
    Display localDisplay = this.display;
    localDisplay.captureChanged = false;
    sendMouseEvent(3, 3, this.handle, 516, paramInt1, paramInt2);
    if ((sendMouseEvent(8, 3, this.handle, 518, paramInt1, paramInt2)) && (localLVHITTESTINFO.iItem != -1))
      callWindowProc(this.handle, 518, paramInt1, paramInt2);
    if ((!localDisplay.captureChanged) && (!isDisposed()) && (OS.GetCapture() != this.handle))
      OS.SetCapture(this.handle);
    return LRESULT.ZERO;
  }

  LRESULT WM_RBUTTONDOWN(int paramInt1, int paramInt2)
  {
    return sendMouseDownEvent(3, 3, 516, paramInt1, paramInt2);
  }

  LRESULT WM_SETFOCUS(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_SETFOCUS(paramInt1, paramInt2);
    if ((this.imageList != null) || ((this.style & 0x20) != 0))
      OS.InvalidateRect(this.handle, null, false);
    int i = OS.SendMessage(this.handle, 4100, 0, 0);
    if (i == 0)
      return localLRESULT;
    int j = OS.SendMessage(this.handle, 4108, -1, 1);
    if (j == -1)
    {
      LVITEM localLVITEM = new LVITEM();
      localLVITEM.state = 1;
      localLVITEM.stateMask = 1;
      this.ignoreSelect = true;
      OS.SendMessage(this.handle, 4139, 0, localLVITEM);
      this.ignoreSelect = false;
    }
    return localLRESULT;
  }

  LRESULT WM_SETFONT(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_SETFONT(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    int i = OS.SendMessage(this.handle, 4127, 0, 0);
    OS.SendMessage(i, 48, 0, paramInt2);
    if (this.headerToolTipHandle != 0)
      OS.SendMessage(this.headerToolTipHandle, 48, paramInt1, paramInt2);
    return localLRESULT;
  }

  LRESULT WM_SETREDRAW(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_SETREDRAW(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    if ((paramInt1 == 1) && (OS.SendMessage(this.handle, 4096, 0, 0) != -1) && ((hooks(41)) || (hooks(40)) || (hooks(42))))
      OS.SendMessage(this.handle, 4097, 0, -1);
    OS.DefWindowProc(this.handle, 11, paramInt1, paramInt2);
    int i = callWindowProc(this.handle, 11, paramInt1, paramInt2);
    if ((paramInt1 == 0) && (OS.SendMessage(this.handle, 4096, 0, 0) == -1))
      OS.SendMessage(this.handle, 4097, 0, 16777215);
    return i == 0 ? LRESULT.ZERO : new LRESULT(i);
  }

  LRESULT WM_SIZE(int paramInt1, int paramInt2)
  {
    if (this.ignoreResize)
      return null;
    if ((hooks(40)) || (hooks(42)))
      OS.InvalidateRect(this.handle, null, true);
    if (this.resizeCount != 0)
    {
      this.wasResized = true;
      return null;
    }
    return super.WM_SIZE(paramInt1, paramInt2);
  }

  LRESULT WM_SYSCOLORCHANGE(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_SYSCOLORCHANGE(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    if (findBackgroundControl() == null)
    {
      setBackgroundPixel(defaultBackground());
    }
    else
    {
      int i = OS.SendMessage(this.handle, 4096, 0, 0);
      if ((i != -1) && (findImageControl() == null) && ((this.style & 0x20) != 0))
        fixCheckboxImageListColor(true);
    }
    return localLRESULT;
  }

  LRESULT WM_HSCROLL(int paramInt1, int paramInt2)
  {
    int i = 0;
    int j = OS.SendMessage(this.handle, 4151, 0, 0);
    if ((j & 0x1) != 0)
    {
      SCROLLINFO localSCROLLINFO1 = new SCROLLINFO();
      localSCROLLINFO1.cbSize = SCROLLINFO.sizeof;
      localSCROLLINFO1.fMask = 4;
      OS.GetScrollInfo(this.handle, 0, localSCROLLINFO1);
      i = localSCROLLINFO1.nPos;
    }
    int k = 0;
    int m = 0;
    int n = OS.SendMessage(this.handle, 4127, 0, 0);
    boolean bool = isOptimizedRedraw();
    if (bool)
    {
      m = OS.SetWindowLongPtr(this.handle, -4, TableProc);
      k = OS.SetWindowLongPtr(n, -4, HeaderProc);
    }
    int i1 = 0;
    if ((OS.LOWORD(paramInt1) != 8) && (OS.COMCTL32_MAJOR >= 6) && (this.columnCount > 32))
    {
      int i2 = OS.SendMessage(this.handle, 4136, 0, 0);
      if (i2 > 16)
        i1 = (getDrawing()) && (OS.IsWindowVisible(this.handle)) ? 1 : 0;
    }
    if (i1 != 0)
      OS.DefWindowProc(this.handle, 11, 0, 0);
    LRESULT localLRESULT = super.WM_HSCROLL(paramInt1, paramInt2);
    RECT localRECT2;
    boolean[] arrayOfBoolean;
    if (i1 != 0)
    {
      OS.DefWindowProc(this.handle, 11, 1, 0);
      int i3 = 1157;
      OS.RedrawWindow(this.handle, null, 0, i3);
      if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(6, 0)))
      {
        RECT localRECT1 = new RECT();
        localRECT2 = new RECT();
        OS.GetClientRect(this.handle, localRECT2);
        arrayOfBoolean = new boolean[this.columnCount];
        for (int i5 = 0; i5 < this.columnCount; i5++)
        {
          arrayOfBoolean[i5] = true;
          if (OS.SendMessage(n, 4615, i5, localRECT1) != 0)
          {
            OS.MapWindowPoints(n, this.handle, localRECT1, 2);
            arrayOfBoolean[i5] = OS.IntersectRect(localRECT1, localRECT2, localRECT1);
          }
        }
      }
    }
    try
    {
      this.display.hwndParent = OS.GetParent(this.handle);
      this.display.columnVisible = arrayOfBoolean;
      OS.UpdateWindow(this.handle);
    }
    finally
    {
      this.display.columnVisible = null;
      ret;
    }
    OS.SetWindowLongPtr(this.handle, -4, m);
    OS.SetWindowLongPtr(n, -4, k);
    if ((j & 0x1) != 0)
    {
      SCROLLINFO localSCROLLINFO2 = new SCROLLINFO();
      localSCROLLINFO2.cbSize = SCROLLINFO.sizeof;
      localSCROLLINFO2.fMask = 4;
      OS.GetScrollInfo(this.handle, 0, localSCROLLINFO2);
      int i4 = localSCROLLINFO2.nPos;
      if (i4 < i)
      {
        localRECT2 = new RECT();
        OS.GetClientRect(this.handle, localRECT2);
        localRECT2.right = (i - i4 + 1);
        OS.InvalidateRect(this.handle, localRECT2, true);
      }
    }
    return localLRESULT;
  }

  LRESULT WM_VSCROLL(int paramInt1, int paramInt2)
  {
    int i = 0;
    int j = 0;
    int k = OS.SendMessage(this.handle, 4127, 0, 0);
    boolean bool = isOptimizedRedraw();
    if (bool)
    {
      j = OS.SetWindowLongPtr(this.handle, -4, TableProc);
      i = OS.SetWindowLongPtr(k, -4, HeaderProc);
    }
    int m = 0;
    if ((OS.LOWORD(paramInt1) != 8) && (OS.COMCTL32_MAJOR >= 6) && (this.columnCount > 32))
    {
      int n = OS.SendMessage(this.handle, 4136, 0, 0);
      if (n > 16)
        m = (getDrawing()) && (OS.IsWindowVisible(this.handle)) ? 1 : 0;
    }
    if (m != 0)
      OS.DefWindowProc(this.handle, 11, 0, 0);
    LRESULT localLRESULT = super.WM_VSCROLL(paramInt1, paramInt2);
    RECT localRECT2;
    boolean[] arrayOfBoolean;
    if (m != 0)
    {
      OS.DefWindowProc(this.handle, 11, 1, 0);
      i1 = 1157;
      OS.RedrawWindow(this.handle, null, 0, i1);
      if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(6, 0)))
      {
        RECT localRECT1 = new RECT();
        localRECT2 = new RECT();
        OS.GetClientRect(this.handle, localRECT2);
        arrayOfBoolean = new boolean[this.columnCount];
        for (int i4 = 0; i4 < this.columnCount; i4++)
        {
          arrayOfBoolean[i4] = true;
          if (OS.SendMessage(k, 4615, i4, localRECT1) != 0)
          {
            OS.MapWindowPoints(k, this.handle, localRECT1, 2);
            arrayOfBoolean[i4] = OS.IntersectRect(localRECT1, localRECT2, localRECT1);
          }
        }
      }
    }
    try
    {
      this.display.hwndParent = OS.GetParent(this.handle);
      this.display.columnVisible = arrayOfBoolean;
      OS.UpdateWindow(this.handle);
    }
    finally
    {
      this.display.columnVisible = null;
      ret;
    }
    OS.SetWindowLongPtr(this.handle, -4, j);
    OS.SetWindowLongPtr(k, -4, i);
    int i1 = OS.SendMessage(this.handle, 4151, 0, 0);
    if ((i1 & 0x1) != 0)
    {
      int i2 = OS.LOWORD(paramInt1);
      switch (i2)
      {
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
        break;
      case 0:
      case 1:
        localRECT2 = new RECT();
        OS.GetWindowRect(k, localRECT2);
        int i3 = localRECT2.bottom - localRECT2.top;
        RECT localRECT3 = new RECT();
        OS.GetClientRect(this.handle, localRECT3);
        localRECT3.top += i3;
        int i5 = OS.SendMessage(this.handle, 4160, 0, 0);
        int i6 = OS.SendMessage(this.handle, 4160, 1, 0);
        int i7 = OS.HIWORD(i6) - OS.HIWORD(i5);
        if (i2 == 1)
          localRECT3.top = (localRECT3.bottom - i7 - 1);
        else
          localRECT3.bottom = (localRECT3.top + i7 + 1);
        OS.InvalidateRect(this.handle, localRECT3, true);
        break;
      case 2:
      case 3:
        OS.InvalidateRect(this.handle, null, true);
      }
    }
    return localLRESULT;
  }

  LRESULT wmMeasureChild(int paramInt1, int paramInt2)
  {
    MEASUREITEMSTRUCT localMEASUREITEMSTRUCT = new MEASUREITEMSTRUCT();
    OS.MoveMemory(localMEASUREITEMSTRUCT, paramInt2, MEASUREITEMSTRUCT.sizeof);
    if (this.itemHeight == -1)
    {
      int i = OS.SendMessage(this.handle, 4160, 0, 0);
      int j = OS.SendMessage(this.handle, 4160, 1, 0);
      localMEASUREITEMSTRUCT.itemHeight = (OS.HIWORD(j) - OS.HIWORD(i));
    }
    else
    {
      localMEASUREITEMSTRUCT.itemHeight = this.itemHeight;
    }
    OS.MoveMemory(paramInt2, localMEASUREITEMSTRUCT, MEASUREITEMSTRUCT.sizeof);
    return null;
  }

  LRESULT wmNotify(NMHDR paramNMHDR, int paramInt1, int paramInt2)
  {
    int i = OS.SendMessage(this.handle, 4174, 0, 0);
    if (paramNMHDR.hwndFrom == i)
    {
      LRESULT localLRESULT1 = wmNotifyToolTip(paramNMHDR, paramInt1, paramInt2);
      if (localLRESULT1 != null)
        return localLRESULT1;
    }
    int j = OS.SendMessage(this.handle, 4127, 0, 0);
    if (paramNMHDR.hwndFrom == j)
    {
      LRESULT localLRESULT2 = wmNotifyHeader(paramNMHDR, paramInt1, paramInt2);
      if (localLRESULT2 != null)
        return localLRESULT2;
    }
    return super.wmNotify(paramNMHDR, paramInt1, paramInt2);
  }

  LRESULT wmNotifyChild(NMHDR paramNMHDR, int paramInt1, int paramInt2)
  {
    Object localObject1;
    Object localObject2;
    Object localObject4;
    int i;
    NMLISTVIEW localNMLISTVIEW1;
    int j;
    switch (paramNMHDR.code)
    {
    case -179:
    case -152:
      if ((this.style & 0x10000000) != 0)
        return new LRESULT(-1);
      break;
    case -115:
      if (((this.style & 0x10000000) != 0) && (!this.ignoreSelect))
      {
        localObject1 = new NMLVODSTATECHANGE();
        OS.MoveMemory((NMLVODSTATECHANGE)localObject1, paramInt2, NMLVODSTATECHANGE.sizeof);
        int k = (((NMLVODSTATECHANGE)localObject1).uOldState & 0x2) != 0 ? 1 : 0;
        int n = (((NMLVODSTATECHANGE)localObject1).uNewState & 0x2) != 0 ? 1 : 0;
        if (k != n)
          this.wasSelected = true;
      }
      break;
    case -177:
    case -150:
      localObject1 = new NMLVDISPINFO();
      OS.MoveMemory((NMLVDISPINFO)localObject1, paramInt2, NMLVDISPINFO.sizeof);
      localObject2 = this.display.columnVisible;
      if ((localObject2 == null) || (localObject2[localObject1.iSubItem] != 0))
      {
        TableItem localTableItem = _getItem(((NMLVDISPINFO)localObject1).iItem);
        if (localTableItem != null)
          if (((this.style & 0x10000000) != 0) && (!localTableItem.cached) && (this.ignoreShrink))
          {
            OS.SendMessage(this.handle, 4117, ((NMLVDISPINFO)localObject1).iItem, ((NMLVDISPINFO)localObject1).iItem);
          }
          else
          {
            Object localObject3;
            if (!localTableItem.cached)
            {
              if ((this.style & 0x10000000) != 0)
              {
                this.lastIndexOf = ((NMLVDISPINFO)localObject1).iItem;
                if (!checkData(localTableItem, this.lastIndexOf, false))
                  break;
                localObject3 = this.fixScrollWidth ? null : localTableItem;
                if (setScrollWidth((TableItem)localObject3, true))
                  OS.InvalidateRect(this.handle, null, true);
              }
              localTableItem.cached = true;
            }
            else
            {
              if ((((NMLVDISPINFO)localObject1).mask & 0x1) != 0)
              {
                localObject3 = null;
                if (((NMLVDISPINFO)localObject1).iSubItem == 0)
                {
                  localObject3 = localTableItem.text;
                }
                else
                {
                  String[] arrayOfString = localTableItem.strings;
                  if (arrayOfString != null)
                    localObject3 = arrayOfString[localObject1.iSubItem];
                }
                if (localObject3 != null)
                {
                  int i3 = Math.min(((String)localObject3).length(), ((NMLVDISPINFO)localObject1).cchTextMax - 1);
                  if ((!this.tipRequested) && (((NMLVDISPINFO)localObject1).iSubItem == 0) && (i3 == 0))
                  {
                    localObject3 = " ";
                    i3 = 1;
                  }
                  localObject4 = this.display.tableBuffer;
                  if ((localObject4 == null) || (((NMLVDISPINFO)localObject1).cchTextMax > localObject4.length))
                    localObject4 = this.display.tableBuffer = new char[((NMLVDISPINFO)localObject1).cchTextMax];
                  ((String)localObject3).getChars(0, i3, (char[])localObject4, 0);
                  localObject4[(i3++)] = 0;
                  if (OS.IsUnicode)
                  {
                    OS.MoveMemory(((NMLVDISPINFO)localObject1).pszText, (char[])localObject4, i3 * 2);
                  }
                  else
                  {
                    OS.WideCharToMultiByte(getCodePage(), 0, (char[])localObject4, i3, ((NMLVDISPINFO)localObject1).pszText, ((NMLVDISPINFO)localObject1).cchTextMax, null, null);
                    OS.MoveMemory(((NMLVDISPINFO)localObject1).pszText + ((NMLVDISPINFO)localObject1).cchTextMax - 1, new byte[1], 1);
                  }
                }
              }
              int i2 = 0;
              if ((((NMLVDISPINFO)localObject1).mask & 0x2) != 0)
              {
                Image localImage = null;
                if (((NMLVDISPINFO)localObject1).iSubItem == 0)
                {
                  localImage = localTableItem.image;
                }
                else
                {
                  localObject4 = localTableItem.images;
                  if (localObject4 != null)
                    localImage = localObject4[localObject1.iSubItem];
                }
                if (localImage != null)
                {
                  ((NMLVDISPINFO)localObject1).iImage = imageIndex(localImage, ((NMLVDISPINFO)localObject1).iSubItem);
                  i2 = 1;
                }
              }
              if (((((NMLVDISPINFO)localObject1).mask & 0x8) != 0) && (((NMLVDISPINFO)localObject1).iSubItem == 0))
              {
                int i4 = 1;
                if (localTableItem.checked)
                  i4++;
                if (localTableItem.grayed)
                  i4 += 2;
                ((NMLVDISPINFO)localObject1).state = (i4 << 12);
                ((NMLVDISPINFO)localObject1).stateMask = 61440;
                i2 = 1;
              }
              if (((((NMLVDISPINFO)localObject1).mask & 0x10) != 0) && (((NMLVDISPINFO)localObject1).iSubItem == 0))
              {
                ((NMLVDISPINFO)localObject1).iIndent = localTableItem.imageIndent;
                i2 = 1;
              }
              if (i2 != 0)
                OS.MoveMemory(paramInt2, (NMLVDISPINFO)localObject1, NMLVDISPINFO.sizeof);
            }
          }
      }
      break;
    case -12:
      i = OS.SendMessage(this.handle, 4127, 0, 0);
      if ((paramNMHDR.hwndFrom != i) && ((this.customDraw) || (findImageControl() != null) || (!OS.IsWindowEnabled(this.handle)) || ((this.explorerTheme) && (this.columnCount == 0))))
      {
        localObject2 = new NMLVCUSTOMDRAW();
        OS.MoveMemory((NMLVCUSTOMDRAW)localObject2, paramInt2, NMLVCUSTOMDRAW.sizeof);
        switch (((NMLVCUSTOMDRAW)localObject2).dwDrawStage)
        {
        case 1:
          return CDDS_PREPAINT((NMLVCUSTOMDRAW)localObject2, paramInt1, paramInt2);
        case 65537:
          return CDDS_ITEMPREPAINT((NMLVCUSTOMDRAW)localObject2, paramInt1, paramInt2);
        case 65538:
          return CDDS_ITEMPOSTPAINT((NMLVCUSTOMDRAW)localObject2, paramInt1, paramInt2);
        case 196609:
          return CDDS_SUBITEMPREPAINT((NMLVCUSTOMDRAW)localObject2, paramInt1, paramInt2);
        case 196610:
          return CDDS_SUBITEMPOSTPAINT((NMLVCUSTOMDRAW)localObject2, paramInt1, paramInt2);
        case 2:
          return CDDS_POSTPAINT((NMLVCUSTOMDRAW)localObject2, paramInt1, paramInt2);
        }
      }
      break;
    case -156:
      if ((this.style & 0x4) != 0)
        return LRESULT.ONE;
      if ((hooks(3)) || (hooks(4)))
        return LRESULT.ONE;
      if (((this.style & 0x4000000) != 0) && (findImageControl() != null))
        return LRESULT.ONE;
      break;
    case -111:
    case -109:
      if (OS.GetKeyState(1) < 0)
      {
        this.dragStarted = true;
        if (paramNMHDR.code == -109)
        {
          i = OS.GetMessagePos();
          localObject2 = new POINT();
          OS.POINTSTOPOINT((POINT)localObject2, i);
          OS.ScreenToClient(this.handle, (POINT)localObject2);
          sendDragEvent(1, ((POINT)localObject2).x, ((POINT)localObject2).y);
        }
      }
      break;
    case -108:
      localNMLISTVIEW1 = new NMLISTVIEW();
      OS.MoveMemory(localNMLISTVIEW1, paramInt2, NMLISTVIEW.sizeof);
      localObject2 = this.columns[localNMLISTVIEW1.iSubItem];
      if (localObject2 != null)
        ((TableColumn)localObject2).sendSelectionEvent(13);
      break;
    case -114:
      if (!this.ignoreActivate)
      {
        localNMLISTVIEW1 = new NMLISTVIEW();
        OS.MoveMemory(localNMLISTVIEW1, paramInt2, NMLISTVIEW.sizeof);
        if (localNMLISTVIEW1.iItem != -1)
        {
          localObject2 = new Event();
          ((Event)localObject2).item = _getItem(localNMLISTVIEW1.iItem);
          sendSelectionEvent(14, (Event)localObject2, false);
        }
      }
      break;
    case -101:
      if (this.fullRowSelect)
      {
        this.fullRowSelect = false;
        OS.DefWindowProc(this.handle, 11, 1, 0);
        OS.SendMessage(this.handle, 4150, 32, 0);
      }
      int m;
      if (!this.ignoreSelect)
      {
        localNMLISTVIEW1 = new NMLISTVIEW();
        OS.MoveMemory(localNMLISTVIEW1, paramInt2, NMLISTVIEW.sizeof);
        if ((localNMLISTVIEW1.uChanged & 0x8) != 0)
          if (localNMLISTVIEW1.iItem == -1)
          {
            this.wasSelected = true;
          }
          else
          {
            m = (localNMLISTVIEW1.uOldState & 0x2) != 0 ? 1 : 0;
            int i1 = (localNMLISTVIEW1.uNewState & 0x2) != 0 ? 1 : 0;
            if (m != i1)
              this.wasSelected = true;
          }
      }
      if ((hooks(40)) || (hooks(42)))
      {
        j = OS.SendMessage(this.handle, 4127, 0, 0);
        m = OS.SendMessage(j, 4608, 0, 0);
        if (m != 0)
        {
          forceResize();
          RECT localRECT1 = new RECT();
          OS.GetClientRect(this.handle, localRECT1);
          NMLISTVIEW localNMLISTVIEW2 = new NMLISTVIEW();
          OS.MoveMemory(localNMLISTVIEW2, paramInt2, NMLISTVIEW.sizeof);
          if (localNMLISTVIEW2.iItem != -1)
          {
            RECT localRECT2 = new RECT();
            localRECT2.left = 0;
            this.ignoreCustomDraw = true;
            OS.SendMessage(this.handle, 4110, localNMLISTVIEW2.iItem, localRECT2);
            this.ignoreCustomDraw = false;
            localObject4 = new RECT();
            int i5 = OS.SendMessage(j, 4623, m - 1, 0);
            OS.SendMessage(j, 4615, i5, (RECT)localObject4);
            OS.MapWindowPoints(j, this.handle, (RECT)localObject4, 2);
            localRECT1.left = ((RECT)localObject4).right;
            localRECT1.top = localRECT2.top;
            localRECT1.bottom = localRECT2.bottom;
            OS.InvalidateRect(this.handle, localRECT1, true);
          }
        }
      }
      break;
    case -16:
      if (OS.IsPPC)
      {
        j = (this.menu != null) && (!this.menu.isDisposed()) ? 1 : 0;
        if ((j == 0) && (!hooks(35)))
          return LRESULT.ONE;
      }
      break;
    case 1000:
      if (OS.IsPPC)
      {
        j = (this.menu != null) && (!this.menu.isDisposed()) ? 1 : 0;
        if ((j != 0) || (hooks(35)))
        {
          NMRGINFO localNMRGINFO = new NMRGINFO();
          OS.MoveMemory(localNMRGINFO, paramInt2, NMRGINFO.sizeof);
          showMenu(localNMRGINFO.x, localNMRGINFO.y);
          return LRESULT.ONE;
        }
      }
      break;
    }
    return super.wmNotifyChild(paramNMHDR, paramInt1, paramInt2);
  }

  LRESULT wmNotifyHeader(NMHDR paramNMHDR, int paramInt1, int paramInt2)
  {
    Object localObject1;
    int i;
    Object localObject3;
    Object localObject4;
    int i1;
    int i2;
    TableColumn localTableColumn;
    Object localObject2;
    switch (paramNMHDR.code)
    {
    case -326:
    case -325:
    case -306:
    case -305:
      if (this.columnCount == 0)
        return LRESULT.ONE;
      NMHEADER localNMHEADER1 = new NMHEADER();
      OS.MoveMemory(localNMHEADER1, paramInt2, NMHEADER.sizeof);
      localObject1 = this.columns[localNMHEADER1.iItem];
      if ((localObject1 != null) && (!((TableColumn)localObject1).getResizable()))
        return LRESULT.ONE;
      this.ignoreColumnMove = true;
      switch (paramNMHDR.code)
      {
      case -325:
      case -305:
        int k = 0;
        if ((!OS.IsWinCE) && (OS.WIN32_VERSION < OS.VERSION(6, 0)))
          k = (localNMHEADER1.iItem == 0) && (!this.firstColumnImage) ? 1 : 0;
        if ((localObject1 != null) && ((k != 0) || (hooks(41))))
        {
          ((TableColumn)localObject1).pack();
          return LRESULT.ONE;
        }
        break;
      }
      break;
    case -16:
      if (!this.ignoreColumnMove)
        for (i = 0; i < this.columnCount; i++)
        {
          localObject1 = this.columns[i];
          ((TableColumn)localObject1).updateToolTip(i);
        }
      this.ignoreColumnMove = false;
      break;
    case -310:
      if (this.ignoreColumnMove)
        return LRESULT.ONE;
      i = OS.SendMessage(this.handle, 4151, 0, 0);
      if ((i & 0x10) != 0)
      {
        if (this.columnCount == 0)
          return LRESULT.ONE;
        localObject1 = new NMHEADER();
        OS.MoveMemory((NMHEADER)localObject1, paramInt2, NMHEADER.sizeof);
        if (((NMHEADER)localObject1).iItem != -1)
        {
          localObject3 = this.columns[localObject1.iItem];
          if ((localObject3 != null) && (!((TableColumn)localObject3).getMoveable()))
          {
            this.ignoreColumnMove = true;
            return LRESULT.ONE;
          }
        }
      }
      break;
    case -311:
      i = OS.SendMessage(this.handle, 4151, 0, 0);
      if ((i & 0x10) != 0)
      {
        localObject1 = new NMHEADER();
        OS.MoveMemory((NMHEADER)localObject1, paramInt2, NMHEADER.sizeof);
        if ((((NMHEADER)localObject1).iItem != -1) && (((NMHEADER)localObject1).pitem != 0))
        {
          localObject3 = new HDITEM();
          OS.MoveMemory((HDITEM)localObject3, ((NMHEADER)localObject1).pitem, HDITEM.sizeof);
          if (((((HDITEM)localObject3).mask & 0x80) != 0) && (((HDITEM)localObject3).iOrder != -1) && (this.columnCount != 0))
          {
            localObject4 = new int[this.columnCount];
            OS.SendMessage(this.handle, 4155, this.columnCount, (int[])localObject4);
            for (int m = 0; m < localObject4.length; m++)
              if (localObject4[m] == ((NMHEADER)localObject1).iItem)
                break;
            if (m == localObject4.length)
              m = 0;
            if (m != ((HDITEM)localObject3).iOrder)
            {
              int n = Math.min(m, ((HDITEM)localObject3).iOrder);
              i1 = Math.max(m, ((HDITEM)localObject3).iOrder);
              this.ignoreColumnMove = false;
              for (i2 = n; i2 <= i1; i2++)
              {
                localTableColumn = this.columns[localObject4[i2]];
                if (!localTableColumn.isDisposed())
                  localTableColumn.postEvent(10);
              }
            }
          }
        }
      }
      break;
    case -321:
    case -301:
      i = OS.SendMessage(this.handle, 4125, 0, 0);
      if ((this.lastWidth == 0) && (i > 0))
      {
        int j = OS.SendMessage(this.handle, 4151, 0, 0);
        if ((j & 0x1) != 0)
        {
          localObject3 = new RECT();
          OS.GetClientRect(this.handle, (RECT)localObject3);
          ((RECT)localObject3).right = (((RECT)localObject3).left + i);
          OS.InvalidateRect(this.handle, (RECT)localObject3, true);
        }
      }
      this.lastWidth = i;
      if (!this.ignoreColumnResize)
      {
        localObject2 = new NMHEADER();
        OS.MoveMemory((NMHEADER)localObject2, paramInt2, NMHEADER.sizeof);
        if (((NMHEADER)localObject2).pitem != 0)
        {
          localObject3 = new HDITEM();
          OS.MoveMemory((HDITEM)localObject3, ((NMHEADER)localObject2).pitem, HDITEM.sizeof);
          if ((((HDITEM)localObject3).mask & 0x1) != 0)
          {
            localObject4 = this.columns[localObject2.iItem];
            if (localObject4 != null)
            {
              ((TableColumn)localObject4).updateToolTip(((NMHEADER)localObject2).iItem);
              ((TableColumn)localObject4).sendEvent(11);
              if (isDisposed())
                return LRESULT.ZERO;
              TableColumn[] arrayOfTableColumn = new TableColumn[this.columnCount];
              System.arraycopy(this.columns, 0, arrayOfTableColumn, 0, this.columnCount);
              int[] arrayOfInt = new int[this.columnCount];
              OS.SendMessage(this.handle, 4155, this.columnCount, arrayOfInt);
              i1 = 0;
              for (i2 = 0; i2 < this.columnCount; i2++)
              {
                localTableColumn = arrayOfTableColumn[arrayOfInt[i2]];
                if ((i1 != 0) && (!localTableColumn.isDisposed()))
                {
                  localTableColumn.updateToolTip(arrayOfInt[i2]);
                  localTableColumn.sendEvent(10);
                }
                if (localTableColumn == localObject4)
                  i1 = 1;
              }
            }
          }
        }
      }
      break;
    case -323:
    case -303:
      NMHEADER localNMHEADER2 = new NMHEADER();
      OS.MoveMemory(localNMHEADER2, paramInt2, NMHEADER.sizeof);
      localObject2 = this.columns[localNMHEADER2.iItem];
      if (localObject2 != null)
        ((TableColumn)localObject2).sendSelectionEvent(14);
      break;
    }
    return null;
  }

  LRESULT wmNotifyToolTip(NMHDR paramNMHDR, int paramInt1, int paramInt2)
  {
    if (OS.IsWinCE)
      return null;
    Object localObject1;
    switch (paramNMHDR.code)
    {
    case -12:
      if ((this.toolTipText == null) && (isCustomToolTip()))
      {
        localObject1 = new NMTTCUSTOMDRAW();
        OS.MoveMemory((NMTTCUSTOMDRAW)localObject1, paramInt2, NMTTCUSTOMDRAW.sizeof);
        return wmNotifyToolTip((NMTTCUSTOMDRAW)localObject1, paramInt2);
      }
      break;
    case -530:
    case -521:
    case -520:
      localObject1 = super.wmNotify(paramNMHDR, paramInt1, paramInt2);
      if (localObject1 != null)
        return localObject1;
      if (paramNMHDR.code != -521)
        this.tipRequested = true;
      int i = callWindowProc(this.handle, 78, paramInt1, paramInt2);
      if (paramNMHDR.code != -521)
        this.tipRequested = false;
      if (this.toolTipText == null)
      {
        if (isCustomToolTip())
        {
          LVHITTESTINFO localLVHITTESTINFO = new LVHITTESTINFO();
          int j = OS.GetMessagePos();
          POINT localPOINT = new POINT();
          OS.POINTSTOPOINT(localPOINT, j);
          OS.ScreenToClient(this.handle, localPOINT);
          localLVHITTESTINFO.x = localPOINT.x;
          localLVHITTESTINFO.y = localPOINT.y;
          if (OS.SendMessage(this.handle, 4153, 0, localLVHITTESTINFO) >= 0)
          {
            TableItem localTableItem = _getItem(localLVHITTESTINFO.iItem);
            int k = OS.GetDC(this.handle);
            int m = 0;
            int n = OS.SendMessage(this.handle, 49, 0, 0);
            if (n != 0)
              m = OS.SelectObject(k, n);
            int i1 = localTableItem.fontHandle(localLVHITTESTINFO.iSubItem);
            if (i1 != -1)
              i1 = OS.SelectObject(k, i1);
            Event localEvent = sendMeasureItemEvent(localTableItem, localLVHITTESTINFO.iItem, localLVHITTESTINFO.iSubItem, k);
            if ((!isDisposed()) && (!localTableItem.isDisposed()))
            {
              RECT localRECT1 = new RECT();
              OS.SetRect(localRECT1, localEvent.x, localEvent.y, localEvent.x + localEvent.width, localEvent.y + localEvent.height);
              Object localObject2;
              if (paramNMHDR.code == -521)
              {
                localObject2 = toolTipRect(localRECT1);
                OS.MapWindowPoints(this.handle, 0, (RECT)localObject2, 2);
                int i2 = OS.SendMessage(this.handle, 4174, 0, 0);
                int i3 = 20;
                int i4 = ((RECT)localObject2).right - ((RECT)localObject2).left;
                int i5 = ((RECT)localObject2).bottom - ((RECT)localObject2).top;
                SetWindowPos(i2, 0, ((RECT)localObject2).left, ((RECT)localObject2).top, i4, i5, i3);
              }
              else
              {
                localObject2 = null;
                if (paramNMHDR.code == -520)
                {
                  localObject2 = new NMTTDISPINFOA();
                  OS.MoveMemory((NMTTDISPINFOA)localObject2, paramInt2, NMTTDISPINFOA.sizeof);
                  if (((NMTTDISPINFO)localObject2).lpszText != 0)
                  {
                    OS.MoveMemory(((NMTTDISPINFO)localObject2).lpszText, new byte[1], 1);
                    OS.MoveMemory(paramInt2, (NMTTDISPINFOA)localObject2, NMTTDISPINFOA.sizeof);
                  }
                }
                else
                {
                  localObject2 = new NMTTDISPINFOW();
                  OS.MoveMemory((NMTTDISPINFOW)localObject2, paramInt2, NMTTDISPINFOW.sizeof);
                  if (((NMTTDISPINFO)localObject2).lpszText != 0)
                  {
                    OS.MoveMemory(((NMTTDISPINFO)localObject2).lpszText, new char[1], 2);
                    OS.MoveMemory(paramInt2, (NMTTDISPINFOW)localObject2, NMTTDISPINFOW.sizeof);
                  }
                }
                RECT localRECT2 = localTableItem.getBounds(localLVHITTESTINFO.iItem, localLVHITTESTINFO.iSubItem, true, true, true, true, k);
                RECT localRECT3 = new RECT();
                OS.GetClientRect(this.handle, localRECT3);
                if ((localRECT1.right > localRECT2.right) || (localRECT1.right > localRECT3.right))
                {
                  String str = " ";
                  Shell localShell = getShell();
                  char[] arrayOfChar = new char[str.length() + 1];
                  str.getChars(0, str.length(), arrayOfChar, 0);
                  if (paramNMHDR.code == -520)
                  {
                    byte[] arrayOfByte = new byte[arrayOfChar.length * 2];
                    OS.WideCharToMultiByte(getCodePage(), 0, arrayOfChar, arrayOfChar.length, arrayOfByte, arrayOfByte.length, null, null);
                    localShell.setToolTipText((NMTTDISPINFO)localObject2, arrayOfByte);
                    OS.MoveMemory(paramInt2, (NMTTDISPINFOA)localObject2, NMTTDISPINFOA.sizeof);
                  }
                  else
                  {
                    localShell.setToolTipText((NMTTDISPINFO)localObject2, arrayOfChar);
                    OS.MoveMemory(paramInt2, (NMTTDISPINFOW)localObject2, NMTTDISPINFOW.sizeof);
                  }
                }
              }
            }
            if (i1 != -1)
              i1 = OS.SelectObject(k, i1);
            if (n != 0)
              OS.SelectObject(k, m);
            OS.ReleaseDC(this.handle, k);
          }
        }
        return new LRESULT(i);
      }
      break;
    }
    return null;
  }

  LRESULT wmNotifyToolTip(NMTTCUSTOMDRAW paramNMTTCUSTOMDRAW, int paramInt)
  {
    if (OS.IsWinCE)
      return null;
    switch (paramNMTTCUSTOMDRAW.dwDrawStage)
    {
    case 1:
      if (isCustomToolTip())
        return new LRESULT(18);
      break;
    case 2:
      LVHITTESTINFO localLVHITTESTINFO = new LVHITTESTINFO();
      int i = OS.GetMessagePos();
      POINT localPOINT = new POINT();
      OS.POINTSTOPOINT(localPOINT, i);
      OS.ScreenToClient(this.handle, localPOINT);
      localLVHITTESTINFO.x = localPOINT.x;
      localLVHITTESTINFO.y = localPOINT.y;
      if (OS.SendMessage(this.handle, 4153, 0, localLVHITTESTINFO) >= 0)
      {
        TableItem localTableItem = _getItem(localLVHITTESTINFO.iItem);
        int j = OS.GetDC(this.handle);
        int k = localTableItem.fontHandle(localLVHITTESTINFO.iSubItem);
        if (k == -1)
          k = OS.SendMessage(this.handle, 49, 0, 0);
        int m = OS.SelectObject(j, k);
        int n = 1;
        RECT localRECT1 = localTableItem.getBounds(localLVHITTESTINFO.iItem, localLVHITTESTINFO.iSubItem, true, true, false, false, j);
        if (hooks(40))
        {
          Event localEvent = sendEraseItemEvent(localTableItem, paramNMTTCUSTOMDRAW, localLVHITTESTINFO.iSubItem, localRECT1);
          if ((isDisposed()) || (localTableItem.isDisposed()))
            break;
          if (localEvent.doit)
            n = (localEvent.detail & 0x10) != 0 ? 1 : 0;
          else
            n = 0;
        }
        if (n != 0)
        {
          int i1 = OS.SaveDC(paramNMTTCUSTOMDRAW.hdc);
          int i2 = getLinesVisible() ? 1 : 0;
          RECT localRECT3 = toolTipInset(localRECT1);
          OS.SetWindowOrgEx(paramNMTTCUSTOMDRAW.hdc, localRECT3.left, localRECT3.top, null);
          GCData localGCData = new GCData();
          localGCData.device = this.display;
          localGCData.foreground = OS.GetTextColor(paramNMTTCUSTOMDRAW.hdc);
          localGCData.background = OS.GetBkColor(paramNMTTCUSTOMDRAW.hdc);
          localGCData.font = Font.win32_new(this.display, k);
          GC localGC = GC.win32_new(paramNMTTCUSTOMDRAW.hdc, localGCData);
          int i3 = localRECT1.left;
          if (localLVHITTESTINFO.iSubItem != 0)
            i3 -= i2;
          Image localImage = localTableItem.getImage(localLVHITTESTINFO.iSubItem);
          Point localPoint;
          if (localImage != null)
          {
            localObject = localImage.getBounds();
            RECT localRECT4 = localTableItem.getBounds(localLVHITTESTINFO.iItem, localLVHITTESTINFO.iSubItem, false, true, false, false, j);
            localPoint = this.imageList == null ? new Point(((Rectangle)localObject).width, ((Rectangle)localObject).height) : this.imageList.getImageSize();
            int i5 = localRECT4.top;
            if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(6, 0)))
              i5 += Math.max(0, (localRECT4.bottom - localRECT4.top - localPoint.y) / 2);
            localGC.drawImage(localImage, ((Rectangle)localObject).x, ((Rectangle)localObject).y, ((Rectangle)localObject).width, ((Rectangle)localObject).height, i3, i5, localPoint.x, localPoint.y);
            i3 += localPoint.x + 4 + (localLVHITTESTINFO.iSubItem == 0 ? -2 : 4);
          }
          else
          {
            i3 += 6;
          }
          Object localObject = localTableItem.getText(localLVHITTESTINFO.iSubItem);
          if (localObject != null)
          {
            int i4 = 2084;
            localPoint = this.columns != null ? this.columns[localLVHITTESTINFO.iSubItem] : null;
            if (localPoint != null)
            {
              if ((localPoint.style & 0x1000000) != 0)
                i4 |= 1;
              if ((localPoint.style & 0x20000) != 0)
                i4 |= 2;
            }
            TCHAR localTCHAR = new TCHAR(getCodePage(), (String)localObject, false);
            RECT localRECT5 = new RECT();
            OS.SetRect(localRECT5, i3, localRECT1.top, localRECT1.right, localRECT1.bottom);
            OS.DrawText(paramNMTTCUSTOMDRAW.hdc, localTCHAR, localTCHAR.length(), localRECT5, i4);
          }
          localGC.dispose();
          OS.RestoreDC(paramNMTTCUSTOMDRAW.hdc, i1);
        }
        if (hooks(42))
        {
          RECT localRECT2 = localTableItem.getBounds(localLVHITTESTINFO.iItem, localLVHITTESTINFO.iSubItem, true, true, false, false, j);
          sendPaintItemEvent(localTableItem, paramNMTTCUSTOMDRAW, localLVHITTESTINFO.iSubItem, localRECT2);
        }
        OS.SelectObject(j, m);
        OS.ReleaseDC(this.handle, j);
      }
      break;
    }
    return null;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.Table
 * JD-Core Version:    0.6.2
 */