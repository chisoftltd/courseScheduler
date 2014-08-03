package org.eclipse.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TreeListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.GCData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.Callback;
import org.eclipse.swt.internal.ImageList;
import org.eclipse.swt.internal.win32.BITMAPINFOHEADER;
import org.eclipse.swt.internal.win32.HDHITTESTINFO;
import org.eclipse.swt.internal.win32.HDITEM;
import org.eclipse.swt.internal.win32.HDLAYOUT;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.NMHDR;
import org.eclipse.swt.internal.win32.NMHEADER;
import org.eclipse.swt.internal.win32.NMRGINFO;
import org.eclipse.swt.internal.win32.NMTREEVIEW;
import org.eclipse.swt.internal.win32.NMTTCUSTOMDRAW;
import org.eclipse.swt.internal.win32.NMTTDISPINFO;
import org.eclipse.swt.internal.win32.NMTVCUSTOMDRAW;
import org.eclipse.swt.internal.win32.NMTVDISPINFO;
import org.eclipse.swt.internal.win32.NMTVITEMCHANGE;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.PAINTSTRUCT;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.SCROLLBARINFO;
import org.eclipse.swt.internal.win32.SCROLLINFO;
import org.eclipse.swt.internal.win32.SHDRAGIMAGE;
import org.eclipse.swt.internal.win32.SIZE;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.TEXTMETRIC;
import org.eclipse.swt.internal.win32.TEXTMETRICA;
import org.eclipse.swt.internal.win32.TEXTMETRICW;
import org.eclipse.swt.internal.win32.TOOLINFO;
import org.eclipse.swt.internal.win32.TVHITTESTINFO;
import org.eclipse.swt.internal.win32.TVINSERTSTRUCT;
import org.eclipse.swt.internal.win32.TVITEM;
import org.eclipse.swt.internal.win32.TVSORTCB;
import org.eclipse.swt.internal.win32.WINDOWPOS;
import org.eclipse.swt.internal.win32.WNDCLASS;

public class Tree extends Composite
{
  TreeItem[] items;
  TreeColumn[] columns;
  int columnCount;
  ImageList imageList;
  ImageList headerImageList;
  TreeItem currentItem;
  TreeColumn sortColumn;
  RECT focusRect;
  int hwndParent;
  int hwndHeader;
  int hAnchor;
  int hInsert;
  int hSelect;
  int lastID;
  int hFirstIndexOf;
  int hLastIndexOf;
  int lastIndexOf;
  int itemCount;
  int sortDirection;
  boolean dragStarted;
  boolean gestureCompleted;
  boolean insertAfter;
  boolean shrink;
  boolean ignoreShrink;
  boolean ignoreSelect;
  boolean ignoreExpand;
  boolean ignoreDeselect;
  boolean ignoreResize;
  boolean lockSelection;
  boolean oldSelected;
  boolean newSelected;
  boolean ignoreColumnMove;
  boolean linesVisible;
  boolean customDraw;
  boolean printClient;
  boolean painted;
  boolean ignoreItemHeight;
  boolean ignoreCustomDraw;
  boolean ignoreDrawForeground;
  boolean ignoreDrawBackground;
  boolean ignoreDrawFocus;
  boolean ignoreDrawSelection;
  boolean ignoreDrawHot;
  boolean ignoreFullSelection;
  boolean explorerTheme;
  int scrollWidth;
  int selectionForeground;
  int headerToolTipHandle;
  int itemToolTipHandle;
  int lastTimerID = -1;
  int lastTimerCount;
  static final int TIMER_MAX_COUNT = 8;
  static final int INSET = 3;
  static final int GRID_WIDTH = 1;
  static final int SORT_WIDTH = 10;
  static final int HEADER_MARGIN = 12;
  static final int HEADER_EXTRA = 3;
  static final int INCREMENT = 5;
  static final int EXPLORER_EXTRA = 2;
  static final int DRAG_IMAGE_SIZE = 301;
  static final boolean EXPLORER_THEME = true;
  static final int TreeProc;
  static final TCHAR TreeClass = new TCHAR(0, "SysTreeView32", true);
  static final int HeaderProc = localWNDCLASS.lpfnWndProc;
  static final TCHAR HeaderClass = new TCHAR(0, "SysHeader32", true);

  static
  {
    WNDCLASS localWNDCLASS = new WNDCLASS();
    OS.GetClassInfo(0, TreeClass, localWNDCLASS);
    TreeProc = localWNDCLASS.lpfnWndProc;
    OS.GetClassInfo(0, HeaderClass, localWNDCLASS);
  }

  public Tree(Composite paramComposite, int paramInt)
  {
    super(paramComposite, checkStyle(paramInt));
  }

  static int checkStyle(int paramInt)
  {
    if ((paramInt & 0x10) == 0)
      paramInt |= 768;
    if (((paramInt & 0x100) != 0) && ((paramInt & 0x200) == 0))
      paramInt |= 512;
    return checkBits(paramInt, 4, 2, 0, 0, 0, 0);
  }

  void _addListener(int paramInt, Listener paramListener)
  {
    super._addListener(paramInt, paramListener);
    int i;
    switch (paramInt)
    {
    case 29:
      if ((this.state & 0x8000) != 0)
      {
        i = OS.GetWindowLong(this.handle, -16);
        i &= -17;
        OS.SetWindowLong(this.handle, -16, i);
      }
      break;
    case 40:
    case 41:
    case 42:
      this.customDraw = true;
      this.style |= 536870912;
      if (isCustomToolTip())
        createItemToolTips();
      OS.SendMessage(this.handle, 4385, 0, 0);
      i = OS.GetWindowLong(this.handle, -16);
      if (paramInt == 41)
        i |= 32768;
      if (((this.style & 0x10000) != 0) && (paramInt != 41) && (!this.explorerTheme))
        i &= -4097;
      if (i != OS.GetWindowLong(this.handle, -16))
      {
        OS.SetWindowLong(this.handle, -16, i);
        OS.InvalidateRect(this.handle, null, true);
        int j = OS.SendMessage(this.handle, 4357, 0, 0);
        if ((j != 0) && ((i & 0x8000) != 0) && (!OS.IsWinCE))
          OS.ShowScrollBar(this.handle, 0, false);
      }
      break;
    }
  }

  TreeItem _getItem(int paramInt)
  {
    TVITEM localTVITEM = new TVITEM();
    localTVITEM.mask = 20;
    localTVITEM.hItem = paramInt;
    if (OS.SendMessage(this.handle, OS.TVM_GETITEM, 0, localTVITEM) != 0)
      return _getItem(localTVITEM.hItem, localTVITEM.lParam);
    return null;
  }

  TreeItem _getItem(int paramInt1, int paramInt2)
  {
    if ((this.style & 0x10000000) == 0)
      return this.items[paramInt2];
    return paramInt2 != -1 ? this.items[paramInt2] : new TreeItem(this, 0, -1, -1, paramInt1);
  }

  void _setBackgroundPixel(int paramInt)
  {
    int i = OS.SendMessage(this.handle, 4383, 0, 0);
    if (i != paramInt)
    {
      if (i != -1)
        OS.SendMessage(this.handle, 4381, 0, -1);
      OS.SendMessage(this.handle, 4381, 0, paramInt);
      if (this.explorerTheme)
      {
        int j = OS.SendMessage(this.handle, 4397, 0, 0);
        if ((paramInt == -1) && (findImageControl() == null))
          j |= 64;
        else
          j &= -65;
        OS.SendMessage(this.handle, 4396, 0, j);
      }
      if ((this.style & 0x20) != 0)
        setCheckboxImageList();
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

  public void addTreeListener(TreeListener paramTreeListener)
  {
    checkWidget();
    if (paramTreeListener == null)
      error(4);
    TypedListener localTypedListener = new TypedListener(paramTreeListener);
    addListener(17, localTypedListener);
    addListener(18, localTypedListener);
  }

  int borderHandle()
  {
    return this.hwndParent != 0 ? this.hwndParent : this.handle;
  }

  LRESULT CDDS_ITEMPOSTPAINT(NMTVCUSTOMDRAW paramNMTVCUSTOMDRAW, int paramInt1, int paramInt2)
  {
    if (this.ignoreCustomDraw)
      return null;
    if (paramNMTVCUSTOMDRAW.left == paramNMTVCUSTOMDRAW.right)
      return new LRESULT(0);
    int i = paramNMTVCUSTOMDRAW.hdc;
    OS.RestoreDC(i, -1);
    TreeItem localTreeItem = getItem(paramNMTVCUSTOMDRAW);
    if (localTreeItem == null)
      return null;
    if ((paramNMTVCUSTOMDRAW.left >= paramNMTVCUSTOMDRAW.right) || (paramNMTVCUSTOMDRAW.top >= paramNMTVCUSTOMDRAW.bottom))
      return null;
    if (!OS.IsWindowVisible(this.handle))
      return null;
    if (((this.style & 0x10000) != 0) || (findImageControl() != null) || (this.ignoreDrawSelection) || (this.explorerTheme))
      OS.SetBkMode(i, 1);
    boolean bool = isItemSelected(paramNMTVCUSTOMDRAW);
    int j = (this.explorerTheme) && ((paramNMTVCUSTOMDRAW.uItemState & 0x40) != 0) ? 1 : 0;
    if ((OS.IsWindowEnabled(this.handle)) && (this.explorerTheme))
    {
      int k = OS.GetWindowLong(this.handle, -16);
      if ((k & 0x200) != 0)
        if (((this.style & 0x10000) != 0) && ((bool) || (j != 0)))
          OS.SetTextColor(i, OS.GetSysColor(OS.COLOR_WINDOWTEXT));
        else
          OS.SetTextColor(i, getForegroundPixel());
    }
    int[] arrayOfInt = (int[])null;
    RECT localRECT1 = new RECT();
    OS.GetClientRect(scrolledHandle(), localRECT1);
    if (this.hwndHeader != 0)
    {
      OS.MapWindowPoints(this.hwndParent, this.handle, localRECT1, 2);
      if (this.columnCount != 0)
      {
        arrayOfInt = new int[this.columnCount];
        OS.SendMessage(this.hwndHeader, 4625, this.columnCount, arrayOfInt);
      }
    }
    int m = -1;
    int n = -1;
    if ((OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()) && (this.sortColumn != null) && (this.sortDirection != 0) && (findImageControl() == null))
    {
      m = indexOf(this.sortColumn);
      n = getSortColumnPixel();
    }
    int i1 = 0;
    Point localPoint = null;
    RECT localRECT4;
    Object localObject2;
    for (int i2 = 0; i2 < Math.max(1, this.columnCount); i2++)
    {
      int i4 = arrayOfInt == null ? i2 : arrayOfInt[i2];
      int i6 = paramNMTVCUSTOMDRAW.right - paramNMTVCUSTOMDRAW.left;
      if ((this.columnCount > 0) && (this.hwndHeader != 0))
      {
        HDITEM localHDITEM1 = new HDITEM();
        localHDITEM1.mask = 1;
        OS.SendMessage(this.hwndHeader, OS.HDM_GETITEM, i4, localHDITEM1);
        i6 = localHDITEM1.cxy;
      }
      int i12;
      int i13;
      if ((i2 == 0) && ((this.style & 0x10000) != 0))
      {
        int i8 = (!this.explorerTheme) && (!this.ignoreDrawSelection) && (findImageControl() == null) ? 1 : 0;
        if ((i8 != 0) || ((bool) && (!this.ignoreDrawSelection)) || ((j != 0) && (!this.ignoreDrawHot)))
        {
          int i9 = 1;
          RECT localRECT5 = new RECT();
          OS.SetRect(localRECT5, i6, paramNMTVCUSTOMDRAW.top, paramNMTVCUSTOMDRAW.right, paramNMTVCUSTOMDRAW.bottom);
          if (this.explorerTheme)
          {
            if (hooks(40))
            {
              localRECT6 = localTreeItem.getBounds(i4, true, true, false, false, true, i);
              localRECT6.left -= 2;
              localRECT6.right += 3;
              localRECT5.left = localRECT6.left;
              localRECT5.right = localRECT6.right;
              if ((this.columnCount > 0) && (this.hwndHeader != 0))
              {
                HDITEM localHDITEM2 = new HDITEM();
                localHDITEM2.mask = 1;
                OS.SendMessage(this.hwndHeader, OS.HDM_GETITEM, i4, localHDITEM2);
                localRECT5.right = Math.min(localRECT5.right, paramNMTVCUSTOMDRAW.left + localHDITEM2.cxy);
              }
            }
            RECT localRECT6 = new RECT();
            OS.SetRect(localRECT6, paramNMTVCUSTOMDRAW.left, paramNMTVCUSTOMDRAW.top, paramNMTVCUSTOMDRAW.right, paramNMTVCUSTOMDRAW.bottom);
            if ((this.columnCount > 0) && (this.hwndHeader != 0))
            {
              i12 = 0;
              HDITEM localHDITEM3 = new HDITEM();
              localHDITEM3.mask = 1;
              for (int i14 = 0; i14 < this.columnCount; i14++)
              {
                OS.SendMessage(this.hwndHeader, OS.HDM_GETITEM, i14, localHDITEM3);
                i12 += localHDITEM3.cxy;
              }
              if (i12 > localRECT1.right - localRECT1.left)
              {
                localRECT6.left = 0;
                localRECT6.right = i12;
              }
              else
              {
                localRECT6.left = localRECT1.left;
                localRECT6.right = localRECT1.right;
              }
            }
            i9 = 0;
            i12 = OS.OpenThemeData(this.handle, Display.TREEVIEW);
            i13 = bool ? 3 : 2;
            if ((OS.GetFocus() != this.handle) && (bool) && (j == 0))
              i13 = 5;
            OS.DrawThemeBackground(i12, i, 1, i13, localRECT6, localRECT5);
            OS.CloseThemeData(i12);
          }
          if (i9 != 0)
            fillBackground(i, OS.GetBkColor(i), localRECT5);
        }
      }
      if (i1 + i6 > localRECT1.left)
      {
        localRECT4 = new RECT();
        localObject2 = null;
        int i10 = 1;
        int i11 = 1;
        i12 = 1;
        i13 = 0;
        if (i2 == 0)
        {
          i10 = i12 = i11 = 0;
          if (findImageControl() != null)
            if (this.explorerTheme)
            {
              if ((OS.IsWindowEnabled(this.handle)) && (!hooks(40)))
              {
                Image localImage = null;
                Object localObject3;
                if (i4 == 0)
                {
                  localImage = localTreeItem.image;
                }
                else
                {
                  localObject3 = localTreeItem.images;
                  if (localObject3 != null)
                    localImage = localObject3[i4];
                }
                if (localImage != null)
                {
                  localObject3 = localImage.getBounds();
                  if (localPoint == null)
                    localPoint = getImageSize();
                  if (!this.ignoreDrawForeground)
                  {
                    GCData localGCData1 = new GCData();
                    localGCData1.device = this.display;
                    GC localGC1 = GC.win32_new(i, localGCData1);
                    RECT localRECT7 = localTreeItem.getBounds(i4, false, true, false, false, true, i);
                    localGC1.setClipping(localRECT7.left, localRECT7.top, localRECT7.right - localRECT7.left, localRECT7.bottom - localRECT7.top);
                    localGC1.drawImage(localImage, 0, 0, ((Rectangle)localObject3).width, ((Rectangle)localObject3).height, localRECT7.left, localRECT7.top, localPoint.x, localPoint.y);
                    OS.SelectClipRgn(i, 0);
                    localGC1.dispose();
                  }
                }
              }
            }
            else
            {
              i10 = i11 = i13 = 1;
              localRECT4 = localTreeItem.getBounds(i4, true, false, false, false, true, i);
              if (this.linesVisible)
              {
                localRECT4.right += 1;
                localRECT4.bottom += 1;
              }
            }
          if ((bool) && (!this.ignoreDrawSelection) && (!this.ignoreDrawBackground))
          {
            if (!this.explorerTheme)
              fillBackground(i, OS.GetBkColor(i), localRECT4);
            i13 = 0;
          }
          localObject2 = localRECT4;
          if (hooks(40))
          {
            i10 = i11 = i12 = 1;
            localRECT4 = localTreeItem.getBounds(i4, true, true, false, false, true, i);
            if ((this.style & 0x10000) != 0)
              localObject2 = localRECT4;
            else
              localObject2 = localTreeItem.getBounds(i4, true, false, false, false, true, i);
          }
        }
        else
        {
          this.selectionForeground = -1;
          this.ignoreDrawForeground = (this.ignoreDrawBackground = this.ignoreDrawSelection = this.ignoreDrawFocus = this.ignoreDrawHot = 0);
          OS.SetRect(localRECT4, i1, paramNMTVCUSTOMDRAW.top, i1 + i6, paramNMTVCUSTOMDRAW.bottom);
          localObject2 = localRECT4;
        }
        int i15 = -1;
        int i16 = -1;
        int i17 = localTreeItem.fontHandle(i4);
        if (this.selectionForeground != -1)
          i15 = this.selectionForeground;
        if (OS.IsWindowEnabled(this.handle))
        {
          int i18 = 0;
          if (bool)
          {
            if ((i2 != 0) && ((this.style & 0x10000) == 0))
            {
              OS.SetTextColor(i, getForegroundPixel());
              OS.SetBkColor(i, getBackgroundPixel());
              i18 = i13 = 1;
            }
          }
          else
            i18 = i13 = 1;
          if (i18 != 0)
          {
            i15 = localTreeItem.cellForeground != null ? localTreeItem.cellForeground[i4] : -1;
            if (i15 == -1)
              i15 = localTreeItem.foreground;
          }
          if (i13 != 0)
          {
            i16 = localTreeItem.cellBackground != null ? localTreeItem.cellBackground[i4] : -1;
            if (i16 == -1)
              i16 = localTreeItem.background;
            if ((i16 == -1) && (i4 == m))
              i16 = n;
          }
        }
        else if ((i16 == -1) && (i4 == m))
        {
          i13 = 1;
          i16 = n;
        }
        if ((this.explorerTheme) && ((bool) || ((paramNMTVCUSTOMDRAW.uItemState & 0x40) != 0)))
          if ((this.style & 0x10000) != 0)
          {
            i13 = 0;
          }
          else if (i2 == 0)
          {
            i13 = 0;
            if (!hooks(40))
              i11 = 0;
          }
        Object localObject4;
        Object localObject5;
        int i24;
        int i21;
        GCData localGCData3;
        if (i10 != 0)
        {
          int i19;
          Object localObject6;
          if (i2 != 0)
          {
            if (hooks(41))
            {
              sendMeasureItemEvent(localTreeItem, i4, i);
              if ((isDisposed()) || (localTreeItem.isDisposed()))
                break;
            }
            if (hooks(40))
            {
              localObject4 = localTreeItem.getBounds(i4, true, true, true, true, true, i);
              i19 = OS.SaveDC(i);
              GCData localGCData2 = new GCData();
              localGCData2.device = this.display;
              localGCData2.foreground = OS.GetTextColor(i);
              localGCData2.background = OS.GetBkColor(i);
              if ((!bool) || ((this.style & 0x10000) == 0))
              {
                if (i15 != -1)
                  localGCData2.foreground = i15;
                if (i16 != -1)
                  localGCData2.background = i16;
              }
              localGCData2.font = localTreeItem.getFont(i4);
              localGCData2.uiState = OS.SendMessage(this.handle, 297, 0, 0);
              localObject5 = GC.win32_new(i, localGCData2);
              Event localEvent1 = new Event();
              localEvent1.item = localTreeItem;
              localEvent1.index = i4;
              localEvent1.gc = ((GC)localObject5);
              localEvent1.detail |= 16;
              if (i16 != -1)
                localEvent1.detail |= 8;
              if ((this.style & 0x10000) != 0)
              {
                if (j != 0)
                  localEvent1.detail |= 32;
                if (bool)
                  localEvent1.detail |= 2;
                if ((!this.explorerTheme) && (OS.SendMessage(this.handle, 4362, 9, 0) == paramNMTVCUSTOMDRAW.dwItemSpec) && (this.handle == OS.GetFocus()))
                {
                  i24 = OS.SendMessage(this.handle, 297, 0, 0);
                  if ((i24 & 0x1) == 0)
                    localEvent1.detail |= 4;
                }
              }
              localEvent1.x = ((RECT)localObject4).left;
              localEvent1.y = ((RECT)localObject4).top;
              localEvent1.width = (((RECT)localObject4).right - ((RECT)localObject4).left);
              localEvent1.height = (((RECT)localObject4).bottom - ((RECT)localObject4).top);
              ((GC)localObject5).setClipping(localEvent1.x, localEvent1.y, localEvent1.width, localEvent1.height);
              sendEvent(40, localEvent1);
              localEvent1.gc = null;
              i24 = localGCData2.foreground;
              ((GC)localObject5).dispose();
              OS.RestoreDC(i, i19);
              if ((isDisposed()) || (localTreeItem.isDisposed()))
                break;
              if (localEvent1.doit)
              {
                this.ignoreDrawForeground = ((localEvent1.detail & 0x10) == 0);
                this.ignoreDrawBackground = ((localEvent1.detail & 0x8) == 0);
                if ((this.style & 0x10000) != 0)
                {
                  this.ignoreDrawSelection = ((localEvent1.detail & 0x2) == 0);
                  this.ignoreDrawFocus = ((localEvent1.detail & 0x4) == 0);
                  this.ignoreDrawHot = ((localEvent1.detail & 0x20) == 0);
                }
              }
              else
              {
                this.ignoreDrawForeground = (this.ignoreDrawBackground = this.ignoreDrawSelection = this.ignoreDrawFocus = this.ignoreDrawHot = 1);
              }
              if ((bool) && (this.ignoreDrawSelection))
                this.ignoreDrawHot = true;
              if ((this.style & 0x10000) != 0)
              {
                if (this.ignoreDrawSelection)
                  this.ignoreFullSelection = true;
                if ((!this.ignoreDrawSelection) || (!this.ignoreDrawHot))
                {
                  if ((!bool) && (j == 0))
                  {
                    this.selectionForeground = OS.GetSysColor(OS.COLOR_HIGHLIGHTTEXT);
                  }
                  else if (!this.explorerTheme)
                  {
                    i13 = 1;
                    this.ignoreDrawBackground = false;
                    if (((this.handle == OS.GetFocus()) || (this.display.getHighContrast())) && (OS.IsWindowEnabled(this.handle)))
                      i16 = OS.GetSysColor(OS.COLOR_HIGHLIGHT);
                    else
                      i16 = OS.GetSysColor(OS.COLOR_3DFACE);
                    if ((!this.ignoreFullSelection) && (i4 == this.columnCount - 1))
                    {
                      localObject6 = new RECT();
                      OS.SetRect((RECT)localObject6, ((RECT)localObject2).left, ((RECT)localObject2).top, paramNMTVCUSTOMDRAW.right, ((RECT)localObject2).bottom);
                      localObject2 = localObject6;
                    }
                  }
                  else
                  {
                    localObject6 = new RECT();
                    OS.SetRect((RECT)localObject6, paramNMTVCUSTOMDRAW.left, paramNMTVCUSTOMDRAW.top, paramNMTVCUSTOMDRAW.right, paramNMTVCUSTOMDRAW.bottom);
                    if ((this.columnCount > 0) && (this.hwndHeader != 0))
                    {
                      i26 = 0;
                      HDITEM localHDITEM4 = new HDITEM();
                      localHDITEM4.mask = 1;
                      for (int i29 = 0; i29 < this.columnCount; i29++)
                      {
                        OS.SendMessage(this.hwndHeader, OS.HDM_GETITEM, i29, localHDITEM4);
                        i26 += localHDITEM4.cxy;
                      }
                      if (i26 > localRECT1.right - localRECT1.left)
                      {
                        ((RECT)localObject6).left = 0;
                        ((RECT)localObject6).right = i26;
                      }
                      else
                      {
                        ((RECT)localObject6).left = localRECT1.left;
                        ((RECT)localObject6).right = localRECT1.right;
                      }
                      if (i4 == this.columnCount - 1)
                      {
                        RECT localRECT9 = new RECT();
                        OS.SetRect(localRECT9, ((RECT)localObject2).left, ((RECT)localObject2).top, ((RECT)localObject6).right, ((RECT)localObject2).bottom);
                        localObject2 = localRECT9;
                      }
                    }
                    int i26 = OS.OpenThemeData(this.handle, Display.TREEVIEW);
                    int i28 = bool ? 3 : 2;
                    if ((OS.GetFocus() != this.handle) && (bool) && (j == 0))
                      i28 = 5;
                    OS.DrawThemeBackground(i26, i, 1, i28, (RECT)localObject6, (RECT)localObject2);
                    OS.CloseThemeData(i26);
                  }
                }
                else if (bool)
                {
                  this.selectionForeground = i24;
                  if ((!this.explorerTheme) && (i16 == -1) && (OS.IsWindowEnabled(this.handle)))
                  {
                    localObject6 = findBackgroundControl();
                    if (localObject6 == null)
                      localObject6 = this;
                    i16 = ((Control)localObject6).getBackgroundPixel();
                  }
                }
              }
            }
            if (this.selectionForeground != -1)
              i15 = this.selectionForeground;
          }
          if (!this.ignoreDrawBackground)
            if (i16 != -1)
            {
              if (i13 != 0)
                fillBackground(i, i16, (RECT)localObject2);
            }
            else
            {
              localObject4 = findImageControl();
              if (localObject4 != null)
                if (i2 == 0)
                {
                  i19 = Math.min(localRECT4.right, i6);
                  OS.SetRect(localRECT4, localRECT4.left, localRECT4.top, i19, localRECT4.bottom);
                  if (i13 != 0)
                    fillImageBackground(i, (Control)localObject4, localRECT4, 0, 0);
                }
                else if (i13 != 0)
                {
                  fillImageBackground(i, (Control)localObject4, localRECT4, 0, 0);
                }
            }
          localRECT4.left += 2;
          if (i12 != 0)
          {
            localObject4 = null;
            if (i4 == 0)
            {
              localObject4 = localTreeItem.image;
            }
            else
            {
              Image[] arrayOfImage = localTreeItem.images;
              if (arrayOfImage != null)
                localObject4 = arrayOfImage[i4];
            }
            int i20 = i2 != 0 ? 3 : 0;
            int i22 = i2 != 0 ? 3 : 5;
            if (localObject4 != null)
            {
              localObject5 = ((Image)localObject4).getBounds();
              if (localPoint == null)
                localPoint = getImageSize();
              if (!this.ignoreDrawForeground)
              {
                int i23 = localRECT4.top;
                i24 = Math.max(localRECT4.left, localRECT4.left - i20 + 1);
                localObject6 = new GCData();
                ((GCData)localObject6).device = this.display;
                GC localGC2 = GC.win32_new(i, (GCData)localObject6);
                localGC2.setClipping(i24, localRECT4.top, localRECT4.right - i24, localRECT4.bottom - localRECT4.top);
                localGC2.drawImage((Image)localObject4, 0, 0, ((Rectangle)localObject5).width, ((Rectangle)localObject5).height, i24, i23, localPoint.x, localPoint.y);
                OS.SelectClipRgn(i, 0);
                localGC2.dispose();
              }
              OS.SetRect(localRECT4, localRECT4.left + localPoint.x + i22, localRECT4.top, localRECT4.right - i20, localRECT4.bottom);
            }
            else if (i2 == 0)
            {
              if (OS.SendMessage(this.handle, 4360, 0, 0) != 0)
              {
                if (localPoint == null)
                  localPoint = getImageSize();
                localRECT4.left = Math.min(localRECT4.left + localPoint.x + i22, localRECT4.right);
              }
            }
            else
            {
              OS.SetRect(localRECT4, localRECT4.left + i22, localRECT4.top, localRECT4.right - i20, localRECT4.bottom);
            }
          }
          if ((i11 != 0) && (localRECT4.left < localRECT4.right))
          {
            localObject4 = null;
            if (i4 == 0)
            {
              localObject4 = localTreeItem.text;
            }
            else
            {
              String[] arrayOfString = localTreeItem.strings;
              if (arrayOfString != null)
                localObject4 = arrayOfString[i4];
            }
            if (localObject4 != null)
            {
              if (i17 != -1)
                i17 = OS.SelectObject(i, i17);
              if (i15 != -1)
                i15 = OS.SetTextColor(i, i15);
              if (i16 != -1)
                i16 = OS.SetBkColor(i, i16);
              i21 = 2084;
              if (i2 != 0)
                i21 |= 32768;
              localGCData3 = this.columns != null ? this.columns[i4] : null;
              if (localGCData3 != null)
              {
                if ((localGCData3.style & 0x1000000) != 0)
                  i21 |= 1;
                if ((localGCData3.style & 0x20000) != 0)
                  i21 |= 2;
              }
              localObject5 = new TCHAR(getCodePage(), (String)localObject4, false);
              if (!this.ignoreDrawForeground)
                OS.DrawText(i, (TCHAR)localObject5, ((TCHAR)localObject5).length(), localRECT4, i21);
              OS.DrawText(i, (TCHAR)localObject5, ((TCHAR)localObject5).length(), localRECT4, i21 | 0x400);
              if (i17 != -1)
                i17 = OS.SelectObject(i, i17);
              if (i15 != -1)
                i15 = OS.SetTextColor(i, i15);
              if (i16 != -1)
                i16 = OS.SetBkColor(i, i16);
            }
          }
        }
        if (this.selectionForeground != -1)
          i15 = this.selectionForeground;
        if (hooks(42))
        {
          localObject4 = localTreeItem.getBounds(i4, true, true, false, false, false, i);
          i21 = OS.SaveDC(i);
          localGCData3 = new GCData();
          localGCData3.device = this.display;
          localGCData3.font = localTreeItem.getFont(i4);
          localGCData3.foreground = OS.GetTextColor(i);
          localGCData3.background = OS.GetBkColor(i);
          if ((bool) && ((this.style & 0x10000) != 0))
          {
            if (this.selectionForeground != -1)
              localGCData3.foreground = this.selectionForeground;
          }
          else
          {
            if (i15 != -1)
              localGCData3.foreground = i15;
            if (i16 != -1)
              localGCData3.background = i16;
          }
          localGCData3.uiState = OS.SendMessage(this.handle, 297, 0, 0);
          localObject5 = GC.win32_new(i, localGCData3);
          Event localEvent2 = new Event();
          localEvent2.item = localTreeItem;
          localEvent2.index = i4;
          localEvent2.gc = ((GC)localObject5);
          localEvent2.detail |= 16;
          if (i16 != -1)
            localEvent2.detail |= 8;
          if (j != 0)
            localEvent2.detail |= 32;
          if ((bool) && ((i2 == 0) || ((this.style & 0x10000) != 0)))
            localEvent2.detail |= 2;
          if ((!this.explorerTheme) && (OS.SendMessage(this.handle, 4362, 9, 0) == paramNMTVCUSTOMDRAW.dwItemSpec) && ((i2 == 0) || ((this.style & 0x10000) != 0)) && (this.handle == OS.GetFocus()))
          {
            i24 = OS.SendMessage(this.handle, 297, 0, 0);
            if ((i24 & 0x1) == 0)
              localEvent2.detail |= 4;
          }
          localEvent2.x = ((RECT)localObject4).left;
          localEvent2.y = ((RECT)localObject4).top;
          localEvent2.width = (((RECT)localObject4).right - ((RECT)localObject4).left);
          localEvent2.height = (((RECT)localObject4).bottom - ((RECT)localObject4).top);
          RECT localRECT8 = localTreeItem.getBounds(i4, true, true, true, true, true, i);
          int i25 = localRECT8.right - localRECT8.left;
          int i27 = localRECT8.bottom - localRECT8.top;
          ((GC)localObject5).setClipping(localRECT8.left, localRECT8.top, i25, i27);
          sendEvent(42, localEvent2);
          if (localGCData3.focusDrawn)
            this.focusRect = null;
          localEvent2.gc = null;
          ((GC)localObject5).dispose();
          OS.RestoreDC(i, i21);
          if ((isDisposed()) || (localTreeItem.isDisposed()))
            break;
        }
      }
      i1 += i6;
      if (i1 > localRECT1.right)
        break;
    }
    if (this.linesVisible)
    {
      if (((this.style & 0x10000) != 0) && (this.columnCount != 0))
      {
        localObject1 = new HDITEM();
        ((HDITEM)localObject1).mask = 1;
        OS.SendMessage(this.hwndHeader, OS.HDM_GETITEM, 0, (HDITEM)localObject1);
        RECT localRECT2 = new RECT();
        OS.SetRect(localRECT2, paramNMTVCUSTOMDRAW.left + ((HDITEM)localObject1).cxy, paramNMTVCUSTOMDRAW.top, paramNMTVCUSTOMDRAW.right, paramNMTVCUSTOMDRAW.bottom);
        OS.DrawEdge(i, localRECT2, 8, 8);
      }
      Object localObject1 = new RECT();
      OS.SetRect((RECT)localObject1, paramNMTVCUSTOMDRAW.left, paramNMTVCUSTOMDRAW.top, paramNMTVCUSTOMDRAW.right, paramNMTVCUSTOMDRAW.bottom);
      OS.DrawEdge(i, (RECT)localObject1, 8, 8);
    }
    if ((!this.ignoreDrawFocus) && (this.focusRect != null))
    {
      OS.DrawFocusRect(i, this.focusRect);
      this.focusRect = null;
    }
    else if ((!this.explorerTheme) && (this.handle == OS.GetFocus()))
    {
      int i3 = OS.SendMessage(this.handle, 297, 0, 0);
      if ((i3 & 0x1) == 0)
      {
        int i5 = OS.SendMessage(this.handle, 4362, 9, 0);
        if ((i5 == localTreeItem.handle) && (!this.ignoreDrawFocus) && (findImageControl() != null))
          if ((this.style & 0x10000) != 0)
          {
            RECT localRECT3 = new RECT();
            OS.SetRect(localRECT3, 0, paramNMTVCUSTOMDRAW.top, localRECT1.right + 1, paramNMTVCUSTOMDRAW.bottom);
            OS.DrawFocusRect(i, localRECT3);
          }
          else
          {
            int i7 = OS.SendMessage(this.hwndHeader, 4623, 0, 0);
            localRECT4 = localTreeItem.getBounds(i7, true, false, false, false, false, i);
            localObject2 = localTreeItem.getBounds(i7, true, false, false, false, true, i);
            OS.IntersectClipRect(i, ((RECT)localObject2).left, ((RECT)localObject2).top, ((RECT)localObject2).right, ((RECT)localObject2).bottom);
            OS.DrawFocusRect(i, localRECT4);
            OS.SelectClipRgn(i, 0);
          }
      }
    }
    return new LRESULT(0);
  }

  LRESULT CDDS_ITEMPREPAINT(NMTVCUSTOMDRAW paramNMTVCUSTOMDRAW, int paramInt1, int paramInt2)
  {
    TreeItem localTreeItem = getItem(paramNMTVCUSTOMDRAW);
    if (localTreeItem == null)
      return null;
    int i = paramNMTVCUSTOMDRAW.hdc;
    int j = this.hwndHeader != 0 ? OS.SendMessage(this.hwndHeader, 4623, 0, 0) : 0;
    int k = localTreeItem.fontHandle(j);
    if (k != -1)
      OS.SelectObject(i, k);
    if ((this.ignoreCustomDraw) || (paramNMTVCUSTOMDRAW.left == paramNMTVCUSTOMDRAW.right))
      return new LRESULT(k == -1 ? 0 : 2);
    RECT localRECT1 = null;
    if (this.columnCount != 0)
    {
      localRECT2 = this.printClient ? 0 : 1;
      if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(6, 0)))
        localRECT2 = 1;
      if (localRECT2 != 0)
      {
        localRECT1 = new RECT();
        HDITEM localHDITEM1 = new HDITEM();
        localHDITEM1.mask = 1;
        OS.SendMessage(this.hwndHeader, OS.HDM_GETITEM, j, localHDITEM1);
        OS.SetRect(localRECT1, paramNMTVCUSTOMDRAW.left, paramNMTVCUSTOMDRAW.top, paramNMTVCUSTOMDRAW.left + localHDITEM1.cxy, paramNMTVCUSTOMDRAW.bottom);
      }
    }
    RECT localRECT2 = -1;
    int m = -1;
    if (OS.IsWindowEnabled(this.handle))
    {
      localRECT2 = localTreeItem.cellForeground != null ? localTreeItem.cellForeground[j] : -1;
      if (localRECT2 == -1)
        localRECT2 = localTreeItem.foreground;
      m = localTreeItem.cellBackground != null ? localTreeItem.cellBackground[j] : -1;
      if (m == -1)
        m = localTreeItem.background;
    }
    int n = -1;
    if ((OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()) && (this.sortColumn != null) && (this.sortDirection != 0) && (findImageControl() == null) && (indexOf(this.sortColumn) == j))
    {
      n = getSortColumnPixel();
      if (m == -1)
        m = n;
    }
    boolean bool = isItemSelected(paramNMTVCUSTOMDRAW);
    int i1 = (this.explorerTheme) && ((paramNMTVCUSTOMDRAW.uItemState & 0x40) != 0) ? 1 : 0;
    int i2 = (this.explorerTheme) && ((paramNMTVCUSTOMDRAW.uItemState & 0x10) != 0) ? 1 : 0;
    Object localObject2;
    Object localObject3;
    int i3;
    if ((OS.IsWindowVisible(this.handle)) && (paramNMTVCUSTOMDRAW.left < paramNMTVCUSTOMDRAW.right) && (paramNMTVCUSTOMDRAW.top < paramNMTVCUSTOMDRAW.bottom))
    {
      if (k != -1)
        OS.SelectObject(i, k);
      if (this.linesVisible)
      {
        localObject1 = new RECT();
        OS.SetRect((RECT)localObject1, paramNMTVCUSTOMDRAW.left, paramNMTVCUSTOMDRAW.top, paramNMTVCUSTOMDRAW.right, paramNMTVCUSTOMDRAW.bottom);
        OS.DrawEdge(i, (RECT)localObject1, 8, 8);
      }
      localObject1 = null;
      if (hooks(41))
      {
        localObject1 = sendMeasureItemEvent(localTreeItem, j, i);
        if ((isDisposed()) || (localTreeItem.isDisposed()))
          return null;
      }
      this.selectionForeground = -1;
      this.ignoreDrawForeground = (this.ignoreDrawBackground = this.ignoreDrawSelection = this.ignoreDrawFocus = this.ignoreDrawHot = this.ignoreFullSelection = 0);
      if (hooks(40))
      {
        RECT localRECT3 = new RECT();
        OS.SetRect(localRECT3, paramNMTVCUSTOMDRAW.left, paramNMTVCUSTOMDRAW.top, paramNMTVCUSTOMDRAW.right, paramNMTVCUSTOMDRAW.bottom);
        localObject2 = localTreeItem.getBounds(j, true, true, true, true, true, i);
        if (n != -1)
          drawBackground(i, (RECT)localObject2, n, 0, 0);
        else if ((OS.IsWindowEnabled(this.handle)) || (findImageControl() != null))
          drawBackground(i, localRECT3);
        else
          fillBackground(i, OS.GetBkColor(i), localRECT3);
        int i5 = OS.SaveDC(i);
        localObject3 = new GCData();
        ((GCData)localObject3).device = this.display;
        if ((bool) && (this.explorerTheme))
          ((GCData)localObject3).foreground = OS.GetSysColor(OS.COLOR_WINDOWTEXT);
        else
          ((GCData)localObject3).foreground = OS.GetTextColor(i);
        ((GCData)localObject3).background = OS.GetBkColor(i);
        if (!bool)
        {
          if (localRECT2 != -1)
            ((GCData)localObject3).foreground = localRECT2;
          if (m != -1)
            ((GCData)localObject3).background = m;
        }
        ((GCData)localObject3).uiState = OS.SendMessage(this.handle, 297, 0, 0);
        ((GCData)localObject3).font = localTreeItem.getFont(j);
        GC localGC = GC.win32_new(i, (GCData)localObject3);
        Event localEvent = new Event();
        localEvent.index = j;
        localEvent.item = localTreeItem;
        localEvent.gc = localGC;
        localEvent.detail |= 16;
        if (m != -1)
          localEvent.detail |= 8;
        if (i1 != 0)
          localEvent.detail |= 32;
        if (bool)
          localEvent.detail |= 2;
        if ((!this.explorerTheme) && (OS.SendMessage(this.handle, 4362, 9, 0) == paramNMTVCUSTOMDRAW.dwItemSpec) && (this.handle == OS.GetFocus()))
        {
          i6 = OS.SendMessage(this.handle, 297, 0, 0);
          if ((i6 & 0x1) == 0)
          {
            i2 = 1;
            localEvent.detail |= 4;
          }
        }
        localEvent.x = ((RECT)localObject2).left;
        localEvent.y = ((RECT)localObject2).top;
        localEvent.width = (((RECT)localObject2).right - ((RECT)localObject2).left);
        localEvent.height = (((RECT)localObject2).bottom - ((RECT)localObject2).top);
        localGC.setClipping(localEvent.x, localEvent.y, localEvent.width, localEvent.height);
        sendEvent(40, localEvent);
        localEvent.gc = null;
        int i6 = ((GCData)localObject3).foreground;
        localGC.dispose();
        OS.RestoreDC(i, i5);
        if ((isDisposed()) || (localTreeItem.isDisposed()))
          return null;
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
        if ((bool) && (this.ignoreDrawSelection))
          this.ignoreDrawHot = true;
        RECT localRECT6;
        if ((!this.ignoreDrawBackground) && (m != -1))
        {
          int i7 = (!bool) && (i1 == 0) ? 1 : 0;
          if ((!this.explorerTheme) && (bool))
            i7 = this.ignoreDrawSelection ? 0 : 1;
          if (i7 != 0)
            if (this.columnCount == 0)
            {
              if ((this.style & 0x10000) != 0)
              {
                fillBackground(i, m, localRECT3);
              }
              else
              {
                localRECT6 = localTreeItem.getBounds(j, true, false, false, false, true, i);
                if (localObject1 != null)
                  localRECT6.right = Math.min(((RECT)localObject2).right, ((Event)localObject1).x + ((Event)localObject1).width);
                fillBackground(i, m, localRECT6);
              }
            }
            else
              fillBackground(i, m, (RECT)localObject2);
        }
        if (this.ignoreDrawSelection)
          this.ignoreFullSelection = true;
        if ((!this.ignoreDrawSelection) || (!this.ignoreDrawHot))
        {
          if ((!bool) && (i1 == 0))
            this.selectionForeground = (localRECT2 = OS.GetSysColor(OS.COLOR_HIGHLIGHTTEXT));
          if (this.explorerTheme)
          {
            if ((this.style & 0x10000) == 0)
            {
              localRECT5 = localTreeItem.getBounds(j, true, true, false, false, false, i);
              localRECT6 = localTreeItem.getBounds(j, true, true, true, false, true, i);
              if (localObject1 != null)
              {
                localRECT5.right = Math.min(localRECT6.right, ((Event)localObject1).x + ((Event)localObject1).width);
              }
              else
              {
                localRECT5.right += 2;
                localRECT6.right += 2;
              }
              localRECT5.left -= 2;
              localRECT6.left -= 2;
              int i8 = OS.OpenThemeData(this.handle, Display.TREEVIEW);
              int i9 = bool ? 3 : 2;
              if ((OS.GetFocus() != this.handle) && (bool) && (i1 == 0))
                i9 = 5;
              OS.DrawThemeBackground(i8, i, 1, i9, localRECT5, localRECT6);
              OS.CloseThemeData(i8);
            }
          }
          else if ((this.style & 0x10000) != 0)
          {
            if (((this.style & 0x10000) != 0) && (this.columnCount == 0))
              fillBackground(i, OS.GetBkColor(i), localRECT3);
            else
              fillBackground(i, OS.GetBkColor(i), (RECT)localObject2);
          }
          else
          {
            localRECT5 = localTreeItem.getBounds(j, true, false, false, false, true, i);
            if (localObject1 != null)
              localRECT5.right = Math.min(((RECT)localObject2).right, ((Event)localObject1).x + ((Event)localObject1).width);
            fillBackground(i, OS.GetBkColor(i), localRECT5);
          }
        }
        else
        {
          if ((bool) || (i1 != 0))
          {
            this.selectionForeground = (localRECT2 = i6);
            this.ignoreDrawSelection = (this.ignoreDrawHot = 1);
          }
          if (this.explorerTheme)
          {
            paramNMTVCUSTOMDRAW.uItemState |= 4;
            localRECT5 = localRECT2 == -1 ? getForegroundPixel() : localRECT2;
            if (paramNMTVCUSTOMDRAW.clrText == localRECT5)
            {
              paramNMTVCUSTOMDRAW.clrText |= 536870912;
              if (paramNMTVCUSTOMDRAW.clrText == localRECT5)
                paramNMTVCUSTOMDRAW.clrText &= -536870913;
            }
            else
            {
              paramNMTVCUSTOMDRAW.clrText = localRECT5;
            }
            OS.MoveMemory(paramInt2, paramNMTVCUSTOMDRAW, NMTVCUSTOMDRAW.sizeof);
          }
        }
        if ((i2 != 0) && (!this.ignoreDrawFocus) && ((this.style & 0x10000) == 0))
        {
          localRECT5 = localTreeItem.getBounds(j, true, this.explorerTheme, false, false, true, i);
          if (localObject1 != null)
            localRECT5.right = Math.min(((RECT)localObject2).right, ((Event)localObject1).x + ((Event)localObject1).width);
          paramNMTVCUSTOMDRAW.uItemState &= -17;
          OS.MoveMemory(paramInt2, paramNMTVCUSTOMDRAW, NMTVCUSTOMDRAW.sizeof);
          this.focusRect = localRECT5;
        }
        if (this.explorerTheme)
        {
          if ((bool) || ((i1 != 0) && (this.ignoreDrawHot)))
            paramNMTVCUSTOMDRAW.uItemState &= -65;
          OS.MoveMemory(paramInt2, paramNMTVCUSTOMDRAW, NMTVCUSTOMDRAW.sizeof);
        }
        RECT localRECT5 = localTreeItem.getBounds(j, true, true, false, false, false, i);
        OS.SaveDC(i);
        OS.SelectClipRgn(i, 0);
        if (this.explorerTheme)
        {
          localRECT5.left -= 2;
          localRECT5.right += 2;
        }
        localRECT5.right += 1;
        if (this.linesVisible)
          localRECT5.bottom += 1;
        if (localRECT1 != null)
          OS.IntersectClipRect(i, localRECT1.left, localRECT1.top, localRECT1.right, localRECT1.bottom);
        OS.ExcludeClipRect(i, localRECT5.left, localRECT5.top, localRECT5.right, localRECT5.bottom);
        return new LRESULT(16);
      }
      if ((this.style & 0x10000) != 0)
      {
        i3 = OS.GetWindowLong(this.handle, -16);
        if ((i3 & 0x1000) == 0)
        {
          localObject2 = new RECT();
          OS.SetRect((RECT)localObject2, paramNMTVCUSTOMDRAW.left, paramNMTVCUSTOMDRAW.top, paramNMTVCUSTOMDRAW.right, paramNMTVCUSTOMDRAW.bottom);
          if (bool)
            fillBackground(i, OS.GetBkColor(i), (RECT)localObject2);
          else if (OS.IsWindowEnabled(this.handle))
            drawBackground(i, (RECT)localObject2);
          paramNMTVCUSTOMDRAW.uItemState &= -17;
          OS.MoveMemory(paramInt2, paramNMTVCUSTOMDRAW, NMTVCUSTOMDRAW.sizeof);
        }
      }
    }
    Object localObject1 = null;
    HDITEM localHDITEM2;
    if ((localRECT2 == -1) && (m == -1) && (k == -1))
    {
      localObject1 = new LRESULT(16);
    }
    else
    {
      localObject1 = new LRESULT(18);
      if (k != -1)
        OS.SelectObject(i, k);
      if ((OS.IsWindowEnabled(this.handle)) && (OS.IsWindowVisible(this.handle)))
      {
        if (m != -1)
        {
          i3 = OS.GetWindowLong(this.handle, -16);
          if ((i3 & 0x1000) == 0)
            if ((this.columnCount != 0) && (this.hwndHeader != 0))
            {
              localObject2 = new RECT();
              localHDITEM2 = new HDITEM();
              localHDITEM2.mask = 1;
              OS.SendMessage(this.hwndHeader, OS.HDM_GETITEM, j, localHDITEM2);
              OS.SetRect((RECT)localObject2, paramNMTVCUSTOMDRAW.left, paramNMTVCUSTOMDRAW.top, paramNMTVCUSTOMDRAW.left + localHDITEM2.cxy, paramNMTVCUSTOMDRAW.bottom);
              if ((OS.COMCTL32_MAJOR < 6) || (!OS.IsAppThemed()))
              {
                localObject3 = new RECT();
                if (OS.TreeView_GetItemRect(this.handle, localTreeItem.handle, (RECT)localObject3, true))
                  ((RECT)localObject2).left = Math.min(((RECT)localObject3).left, ((RECT)localObject2).right);
              }
              if ((this.style & 0x10000) != 0)
              {
                if (!bool)
                  fillBackground(i, m, (RECT)localObject2);
              }
              else if (this.explorerTheme)
              {
                if ((!bool) && (i1 == 0))
                  fillBackground(i, m, (RECT)localObject2);
              }
              else
                fillBackground(i, m, (RECT)localObject2);
            }
            else if ((this.style & 0x10000) != 0)
            {
              localObject2 = new RECT();
              OS.SetRect((RECT)localObject2, paramNMTVCUSTOMDRAW.left, paramNMTVCUSTOMDRAW.top, paramNMTVCUSTOMDRAW.right, paramNMTVCUSTOMDRAW.bottom);
              if (!bool)
                fillBackground(i, m, (RECT)localObject2);
            }
        }
        if (!bool)
        {
          paramNMTVCUSTOMDRAW.clrText = (localRECT2 == -1 ? getForegroundPixel() : localRECT2);
          paramNMTVCUSTOMDRAW.clrTextBk = (m == -1 ? getBackgroundPixel() : m);
          OS.MoveMemory(paramInt2, paramNMTVCUSTOMDRAW, NMTVCUSTOMDRAW.sizeof);
        }
      }
    }
    if (OS.IsWindowEnabled(this.handle))
    {
      if ((this.explorerTheme) && (findImageControl() != null) && (!bool) && ((paramNMTVCUSTOMDRAW.uItemState & 0x41) == 0))
      {
        paramNMTVCUSTOMDRAW.uItemState |= 4;
        i3 = localRECT2 == -1 ? getForegroundPixel() : localRECT2;
        if (paramNMTVCUSTOMDRAW.clrText == i3)
        {
          paramNMTVCUSTOMDRAW.clrText |= 536870912;
          if (paramNMTVCUSTOMDRAW.clrText == i3)
            paramNMTVCUSTOMDRAW.clrText &= -536870913;
        }
        else
        {
          paramNMTVCUSTOMDRAW.clrText = i3;
        }
        OS.MoveMemory(paramInt2, paramNMTVCUSTOMDRAW, NMTVCUSTOMDRAW.sizeof);
        if (m != -1)
          if ((this.style & 0x10000) != 0)
          {
            localObject2 = new RECT();
            if (this.columnCount != 0)
            {
              localHDITEM2 = new HDITEM();
              localHDITEM2.mask = 1;
              OS.SendMessage(this.hwndHeader, OS.HDM_GETITEM, j, localHDITEM2);
              OS.SetRect((RECT)localObject2, paramNMTVCUSTOMDRAW.left, paramNMTVCUSTOMDRAW.top, paramNMTVCUSTOMDRAW.left + localHDITEM2.cxy, paramNMTVCUSTOMDRAW.bottom);
            }
            else
            {
              OS.SetRect((RECT)localObject2, paramNMTVCUSTOMDRAW.left, paramNMTVCUSTOMDRAW.top, paramNMTVCUSTOMDRAW.right, paramNMTVCUSTOMDRAW.bottom);
            }
            fillBackground(i, m, (RECT)localObject2);
          }
          else
          {
            localObject2 = localTreeItem.getBounds(j, true, false, true, false, true, i);
            fillBackground(i, m, (RECT)localObject2);
          }
      }
    }
    else if (n != -1)
    {
      RECT localRECT4 = new RECT();
      localObject2 = new HDITEM();
      ((HDITEM)localObject2).mask = 1;
      OS.SendMessage(this.hwndHeader, OS.HDM_GETITEM, j, (HDITEM)localObject2);
      OS.SetRect(localRECT4, paramNMTVCUSTOMDRAW.left, paramNMTVCUSTOMDRAW.top, paramNMTVCUSTOMDRAW.left + ((HDITEM)localObject2).cxy, paramNMTVCUSTOMDRAW.bottom);
      fillBackground(i, n, localRECT4);
    }
    OS.SaveDC(i);
    if (localRECT1 != null)
    {
      int i4 = OS.CreateRectRgn(localRECT1.left, localRECT1.top, localRECT1.right, localRECT1.bottom);
      localObject2 = new POINT();
      OS.GetWindowOrgEx(i, (POINT)localObject2);
      OS.OffsetRgn(i4, -((POINT)localObject2).x, -((POINT)localObject2).y);
      OS.SelectClipRgn(i, i4);
      OS.DeleteObject(i4);
    }
    return localObject1;
  }

  LRESULT CDDS_POSTPAINT(NMTVCUSTOMDRAW paramNMTVCUSTOMDRAW, int paramInt1, int paramInt2)
  {
    if (this.ignoreCustomDraw)
      return null;
    if (OS.IsWindowVisible(this.handle))
    {
      int i;
      int j;
      Object localObject;
      if ((OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()) && (this.sortColumn != null) && (this.sortDirection != 0) && (findImageControl() == null))
      {
        i = indexOf(this.sortColumn);
        if (i != -1)
        {
          j = paramNMTVCUSTOMDRAW.top;
          int k = 0;
          if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(6, 0)))
            k = getBottomItem();
          else
            k = OS.SendMessage(this.handle, 4362, 10, 0);
          if (k != 0)
          {
            localObject = new RECT();
            if (OS.TreeView_GetItemRect(this.handle, k, (RECT)localObject, false))
              j = ((RECT)localObject).bottom;
          }
          localObject = new RECT();
          OS.SetRect((RECT)localObject, paramNMTVCUSTOMDRAW.left, j, paramNMTVCUSTOMDRAW.right, paramNMTVCUSTOMDRAW.bottom);
          RECT localRECT2 = new RECT();
          OS.SendMessage(this.hwndHeader, 4615, i, localRECT2);
          ((RECT)localObject).left = localRECT2.left;
          ((RECT)localObject).right = localRECT2.right;
          fillBackground(paramNMTVCUSTOMDRAW.hdc, getSortColumnPixel(), (RECT)localObject);
        }
      }
      if (this.linesVisible)
      {
        i = paramNMTVCUSTOMDRAW.hdc;
        int n;
        if (this.hwndHeader != 0)
        {
          j = 0;
          localRECT1 = new RECT();
          localObject = new HDITEM();
          ((HDITEM)localObject).mask = 1;
          for (n = 0; n < this.columnCount; n++)
          {
            int i1 = OS.SendMessage(this.hwndHeader, 4623, n, 0);
            OS.SendMessage(this.hwndHeader, OS.HDM_GETITEM, i1, (HDITEM)localObject);
            OS.SetRect(localRECT1, j, paramNMTVCUSTOMDRAW.top, j + ((HDITEM)localObject).cxy, paramNMTVCUSTOMDRAW.bottom);
            OS.DrawEdge(i, localRECT1, 8, 4);
            j += ((HDITEM)localObject).cxy;
          }
        }
        j = 0;
        RECT localRECT1 = new RECT();
        int m = 0;
        if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(6, 0)))
          m = getBottomItem();
        else
          m = OS.SendMessage(this.handle, 4362, 10, 0);
        if ((m != 0) && (OS.TreeView_GetItemRect(this.handle, m, localRECT1, false)))
          j = localRECT1.bottom - localRECT1.top;
        if (j == 0)
        {
          j = OS.SendMessage(this.handle, 4380, 0, 0);
          OS.GetClientRect(this.handle, localRECT1);
          OS.SetRect(localRECT1, localRECT1.left, localRECT1.top, localRECT1.right, localRECT1.top + j);
          OS.DrawEdge(i, localRECT1, 8, 8);
        }
        if (j != 0)
          while (localRECT1.bottom < paramNMTVCUSTOMDRAW.bottom)
          {
            n = localRECT1.top + j;
            OS.SetRect(localRECT1, localRECT1.left, n, localRECT1.right, n + j);
            OS.DrawEdge(i, localRECT1, 8, 8);
          }
      }
    }
    return new LRESULT(0);
  }

  LRESULT CDDS_PREPAINT(NMTVCUSTOMDRAW paramNMTVCUSTOMDRAW, int paramInt1, int paramInt2)
  {
    if ((this.explorerTheme) && (((OS.IsWindowEnabled(this.handle)) && (hooks(40))) || (findImageControl() != null)))
    {
      RECT localRECT = new RECT();
      OS.SetRect(localRECT, paramNMTVCUSTOMDRAW.left, paramNMTVCUSTOMDRAW.top, paramNMTVCUSTOMDRAW.right, paramNMTVCUSTOMDRAW.bottom);
      drawBackground(paramNMTVCUSTOMDRAW.hdc, localRECT);
    }
    return new LRESULT(48);
  }

  int callWindowProc(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.handle == 0)
      return 0;
    if ((this.hwndParent != 0) && (paramInt1 == this.hwndParent))
      return OS.DefWindowProc(paramInt1, paramInt2, paramInt3, paramInt4);
    if ((this.hwndHeader != 0) && (paramInt1 == this.hwndHeader))
      return OS.CallWindowProc(HeaderProc, paramInt1, paramInt2, paramInt3, paramInt4);
    switch (paramInt2)
    {
    case 7:
      if ((this.style & 0x4) == 0)
      {
        i = OS.SendMessage(this.handle, 4362, 9, 0);
        if (i == 0)
        {
          i = OS.SendMessage(this.handle, 4362, 5, 0);
          if (i != 0)
          {
            TVITEM localTVITEM = new TVITEM();
            localTVITEM.mask = 24;
            localTVITEM.hItem = i;
            OS.SendMessage(this.handle, OS.TVM_GETITEM, 0, localTVITEM);
            this.hSelect = i;
            this.ignoreDeselect = (this.ignoreSelect = this.lockSelection = 1);
            OS.SendMessage(this.handle, 4363, 9, i);
            this.ignoreDeselect = (this.ignoreSelect = this.lockSelection = 0);
            this.hSelect = 0;
            if ((localTVITEM.state & 0x2) == 0)
              OS.SendMessage(this.handle, OS.TVM_SETITEM, 0, localTVITEM);
          }
        }
      }
      break;
    }
    int i = 0;
    int j = 0;
    switch (paramInt2)
    {
    case 256:
      if ((paramInt3 == 17) || (paramInt3 == 16));
      break;
    case 5:
    case 257:
    case 258:
    case 260:
    case 261:
    case 262:
    case 276:
    case 277:
    case 646:
      j = (findImageControl() != null) && (getDrawing()) && (OS.IsWindowVisible(this.handle)) ? 1 : 0;
      if (j != 0)
        OS.DefWindowProc(this.handle, 11, 0, 0);
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
      if (findImageControl() != null)
        i = OS.SendMessage(this.handle, 4362, 5, 0);
      break;
    }
    int k = OS.CallWindowProc(TreeProc, paramInt1, paramInt2, paramInt3, paramInt4);
    switch (paramInt2)
    {
    case 256:
      if ((paramInt3 == 17) || (paramInt3 == 16));
      break;
    case 5:
    case 257:
    case 258:
    case 260:
    case 261:
    case 262:
    case 276:
    case 277:
    case 646:
      if (j != 0)
      {
        OS.DefWindowProc(this.handle, 11, 1, 0);
        OS.InvalidateRect(this.handle, null, true);
        if (this.hwndHeader != 0)
          OS.InvalidateRect(this.hwndHeader, null, true);
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
      if ((findImageControl() != null) && (i != OS.SendMessage(this.handle, 4362, 5, 0)))
        OS.InvalidateRect(this.handle, null, true);
      updateScrollBar();
      break;
    case 15:
      this.painted = true;
    }
    return k;
  }

  void checkBuffered()
  {
    super.checkBuffered();
    if ((this.style & 0x10000000) != 0)
    {
      this.style |= 536870912;
      OS.SendMessage(this.handle, 4385, 0, 0);
    }
    if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(6, 0)) && (OS.IsAppThemed()))
    {
      int i = OS.SendMessage(this.handle, 4397, 0, 0);
      if ((i & 0x4) != 0)
        this.style |= 536870912;
    }
  }

  boolean checkData(TreeItem paramTreeItem, boolean paramBoolean)
  {
    if ((this.style & 0x10000000) == 0)
      return true;
    if (!paramTreeItem.cached)
    {
      TreeItem localTreeItem = paramTreeItem.getParentItem();
      return checkData(paramTreeItem, localTreeItem == null ? indexOf(paramTreeItem) : localTreeItem.indexOf(paramTreeItem), paramBoolean);
    }
    return true;
  }

  boolean checkData(TreeItem paramTreeItem, int paramInt, boolean paramBoolean)
  {
    if ((this.style & 0x10000000) == 0)
      return true;
    if (!paramTreeItem.cached)
    {
      paramTreeItem.cached = true;
      Event localEvent = new Event();
      localEvent.item = paramTreeItem;
      localEvent.index = paramInt;
      TreeItem localTreeItem = this.currentItem;
      this.currentItem = paramTreeItem;
      int i = OS.SendMessage(this.handle, 4362, 5, 0);
      sendEvent(36, localEvent);
      this.currentItem = localTreeItem;
      if ((isDisposed()) || (paramTreeItem.isDisposed()))
        return false;
      if (paramBoolean)
        paramTreeItem.redraw();
      if (i != OS.SendMessage(this.handle, 4362, 5, 0))
        OS.InvalidateRect(this.handle, null, true);
    }
    return true;
  }

  boolean checkHandle(int paramInt)
  {
    return (paramInt == this.handle) || ((this.hwndParent != 0) && (paramInt == this.hwndParent)) || ((this.hwndHeader != 0) && (paramInt == this.hwndHeader));
  }

  boolean checkScroll(int paramInt)
  {
    if (getDrawing())
      return false;
    int i = OS.SendMessage(this.handle, 4362, 0, 0);
    for (int j = OS.SendMessage(this.handle, 4362, 3, paramInt); (j != i) && (j != 0); j = OS.SendMessage(this.handle, 4362, 3, j));
    return j == 0;
  }

  protected void checkSubclass()
  {
    if (!isValidSubclass())
      error(43);
  }

  public void clear(int paramInt, boolean paramBoolean)
  {
    checkWidget();
    int i = OS.SendMessage(this.handle, 4362, 0, 0);
    if (i == 0)
      error(6);
    i = findItem(i, paramInt);
    if (i == 0)
      error(6);
    TVITEM localTVITEM = new TVITEM();
    localTVITEM.mask = 20;
    clear(i, localTVITEM);
    if (paramBoolean)
    {
      i = OS.SendMessage(this.handle, 4362, 4, i);
      clearAll(i, localTVITEM, paramBoolean);
    }
  }

  void clear(int paramInt, TVITEM paramTVITEM)
  {
    paramTVITEM.hItem = paramInt;
    Object localObject = null;
    if (OS.SendMessage(this.handle, OS.TVM_GETITEM, 0, paramTVITEM) != 0)
      localObject = paramTVITEM.lParam != -1 ? this.items[paramTVITEM.lParam] : null;
    if (localObject != null)
    {
      if (((this.style & 0x10000000) != 0) && (!localObject.cached))
        return;
      localObject.clear();
      localObject.redraw();
    }
  }

  public void clearAll(boolean paramBoolean)
  {
    checkWidget();
    int i = OS.SendMessage(this.handle, 4362, 0, 0);
    if (i == 0)
      return;
    if (paramBoolean)
    {
      int j = 0;
      for (int k = 0; k < this.items.length; k++)
      {
        TreeItem localTreeItem = this.items[k];
        if ((localTreeItem != null) && (localTreeItem != this.currentItem))
        {
          localTreeItem.clear();
          j = 1;
        }
      }
      if (j != 0)
        OS.InvalidateRect(this.handle, null, true);
    }
    else
    {
      TVITEM localTVITEM = new TVITEM();
      localTVITEM.mask = 20;
      clearAll(i, localTVITEM, paramBoolean);
    }
  }

  void clearAll(int paramInt, TVITEM paramTVITEM, boolean paramBoolean)
  {
    while (paramInt != 0)
    {
      clear(paramInt, paramTVITEM);
      if (paramBoolean)
      {
        int i = OS.SendMessage(this.handle, 4362, 4, paramInt);
        clearAll(i, paramTVITEM, paramBoolean);
      }
      paramInt = OS.SendMessage(this.handle, 4362, 1, paramInt);
    }
  }

  int CompareFunc(int paramInt1, int paramInt2, int paramInt3)
  {
    TreeItem localTreeItem1 = this.items[paramInt1];
    TreeItem localTreeItem2 = this.items[paramInt2];
    String str1 = localTreeItem1.getText(paramInt3);
    String str2 = localTreeItem2.getText(paramInt3);
    return this.sortDirection == 128 ? str1.compareTo(str2) : str2.compareTo(str1);
  }

  public Point computeSize(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    checkWidget();
    int i = 0;
    int j = 0;
    if (this.hwndHeader != 0)
    {
      localObject = new HDITEM();
      ((HDITEM)localObject).mask = 1;
      for (int k = 0; k < this.columnCount; k++)
      {
        OS.SendMessage(this.hwndHeader, OS.HDM_GETITEM, k, (HDITEM)localObject);
        i += ((HDITEM)localObject).cxy;
      }
      RECT localRECT = new RECT();
      OS.GetWindowRect(this.hwndHeader, localRECT);
      j += localRECT.bottom - localRECT.top;
    }
    Object localObject = new RECT();
    for (int m = OS.SendMessage(this.handle, 4362, 0, 0); m != 0; m = OS.SendMessage(this.handle, 4362, 6, m))
    {
      if (((this.style & 0x10000000) == 0) && (!this.painted))
      {
        TVITEM localTVITEM = new TVITEM();
        localTVITEM.mask = 17;
        localTVITEM.hItem = m;
        localTVITEM.pszText = -1;
        this.ignoreCustomDraw = true;
        OS.SendMessage(this.handle, OS.TVM_SETITEM, 0, localTVITEM);
        this.ignoreCustomDraw = false;
      }
      if (OS.TreeView_GetItemRect(this.handle, m, (RECT)localObject, true))
      {
        i = Math.max(i, ((RECT)localObject).right);
        j += ((RECT)localObject).bottom - ((RECT)localObject).top;
      }
    }
    if (i == 0)
      i = 64;
    if (j == 0)
      j = 64;
    if (paramInt1 != -1)
      i = paramInt1;
    if (paramInt2 != -1)
      j = paramInt2;
    int n = getBorderWidth();
    i += n * 2;
    j += n * 2;
    if ((this.style & 0x200) != 0)
      i += OS.GetSystemMetrics(2);
    if ((this.style & 0x100) != 0)
      j += OS.GetSystemMetrics(3);
    return new Point(i, j);
  }

  void createHandle()
  {
    super.createHandle();
    this.state &= -259;
    if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(6, 0)) && (OS.IsAppThemed()))
    {
      this.explorerTheme = true;
      OS.SetWindowTheme(this.handle, Display.EXPLORER, null);
      i = 84;
      OS.SendMessage(this.handle, 4396, 0, i);
      setForegroundPixel(-1);
    }
    if ((!OS.IsWinCE) && (OS.COMCTL32_MAJOR < 6))
      OS.SendMessage(this.handle, 8199, 5, 0);
    if ((this.style & 0x20) != 0)
      setCheckboxImageList();
    int i = OS.GetStockObject(13);
    OS.SendMessage(this.handle, 48, i, 0);
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

  void createItem(TreeColumn paramTreeColumn, int paramInt)
  {
    if (this.hwndHeader == 0)
      createParent();
    if ((paramInt < 0) || (paramInt > this.columnCount))
      error(6);
    if (this.columnCount == this.columns.length)
    {
      TreeColumn[] arrayOfTreeColumn = new TreeColumn[this.columns.length + 4];
      System.arraycopy(this.columns, 0, arrayOfTreeColumn, 0, this.columns.length);
      this.columns = arrayOfTreeColumn;
    }
    Object localObject3;
    for (int i = 0; i < this.items.length; i++)
    {
      TreeItem localTreeItem = this.items[i];
      if (localTreeItem != null)
      {
        localObject1 = localTreeItem.strings;
        if (localObject1 != null)
        {
          localObject2 = new String[this.columnCount + 1];
          System.arraycopy(localObject1, 0, localObject2, 0, paramInt);
          System.arraycopy(localObject1, paramInt, localObject2, paramInt + 1, this.columnCount - paramInt);
          localTreeItem.strings = ((String[])localObject2);
        }
        Object localObject2 = localTreeItem.images;
        if (localObject2 != null)
        {
          localObject3 = new Image[this.columnCount + 1];
          System.arraycopy(localObject2, 0, localObject3, 0, paramInt);
          System.arraycopy(localObject2, paramInt, localObject3, paramInt + 1, this.columnCount - paramInt);
          localTreeItem.images = ((Image[])localObject3);
        }
        if ((paramInt == 0) && (this.columnCount != 0))
        {
          if (localObject1 == null)
          {
            localTreeItem.strings = new String[this.columnCount + 1];
            localTreeItem.strings[1] = localTreeItem.text;
          }
          localTreeItem.text = "";
          if (localObject2 == null)
          {
            localTreeItem.images = new Image[this.columnCount + 1];
            localTreeItem.images[1] = localTreeItem.image;
          }
          localTreeItem.image = null;
        }
        Object localObject4;
        if (localTreeItem.cellBackground != null)
        {
          localObject3 = localTreeItem.cellBackground;
          localObject4 = new int[this.columnCount + 1];
          System.arraycopy(localObject3, 0, localObject4, 0, paramInt);
          System.arraycopy(localObject3, paramInt, localObject4, paramInt + 1, this.columnCount - paramInt);
          localObject4[paramInt] = -1;
          localTreeItem.cellBackground = ((int[])localObject4);
        }
        if (localTreeItem.cellForeground != null)
        {
          localObject3 = localTreeItem.cellForeground;
          localObject4 = new int[this.columnCount + 1];
          System.arraycopy(localObject3, 0, localObject4, 0, paramInt);
          System.arraycopy(localObject3, paramInt, localObject4, paramInt + 1, this.columnCount - paramInt);
          localObject4[paramInt] = -1;
          localTreeItem.cellForeground = ((int[])localObject4);
        }
        if (localTreeItem.cellFont != null)
        {
          localObject3 = localTreeItem.cellFont;
          localObject4 = new Font[this.columnCount + 1];
          System.arraycopy(localObject3, 0, localObject4, 0, paramInt);
          System.arraycopy(localObject3, paramInt, localObject4, paramInt + 1, this.columnCount - paramInt);
          localTreeItem.cellFont = ((Font[])localObject4);
        }
      }
    }
    System.arraycopy(this.columns, paramInt, this.columns, paramInt + 1, this.columnCount++ - paramInt);
    this.columns[paramInt] = paramTreeColumn;
    i = OS.GetProcessHeap();
    int j = OS.HeapAlloc(i, 8, TCHAR.sizeof);
    Object localObject1 = new HDITEM();
    ((HDITEM)localObject1).mask = 6;
    ((HDITEM)localObject1).pszText = j;
    if ((paramTreeColumn.style & 0x4000) == 16384)
      ((HDITEM)localObject1).fmt = 0;
    if ((paramTreeColumn.style & 0x1000000) == 16777216)
      ((HDITEM)localObject1).fmt = 2;
    if ((paramTreeColumn.style & 0x20000) == 131072)
      ((HDITEM)localObject1).fmt = 1;
    OS.SendMessage(this.hwndHeader, OS.HDM_INSERTITEM, paramInt, (HDITEM)localObject1);
    if (j != 0)
      OS.HeapFree(i, 0, j);
    if (this.columnCount == 1)
    {
      this.scrollWidth = 0;
      if ((this.style & 0x100) != 0)
      {
        k = OS.GetWindowLong(this.handle, -16);
        k |= 32768;
        OS.SetWindowLong(this.handle, -16, k);
      }
      int k = OS.SendMessage(this.handle, 4357, 0, 0);
      if ((k != 0) && (!OS.IsWinCE))
        OS.ShowScrollBar(this.handle, 0, false);
      createItemToolTips();
      if (this.itemToolTipHandle != 0)
        OS.SendMessage(this.itemToolTipHandle, 1027, 0, -1);
    }
    setScrollWidth();
    updateImageList();
    updateScrollBar();
    if ((this.columnCount == 1) && (OS.SendMessage(this.handle, 4357, 0, 0) != 0))
      OS.InvalidateRect(this.handle, null, true);
    if (this.headerToolTipHandle != 0)
    {
      RECT localRECT = new RECT();
      if (OS.SendMessage(this.hwndHeader, 4615, paramInt, localRECT) != 0)
      {
        localObject3 = new TOOLINFO();
        ((TOOLINFO)localObject3).cbSize = TOOLINFO.sizeof;
        ((TOOLINFO)localObject3).uFlags = 16;
        ((TOOLINFO)localObject3).hwnd = this.hwndHeader;
        ((TOOLINFO)localObject3).uId = (paramTreeColumn.id = this.display.nextToolTipId++);
        ((TOOLINFO)localObject3).left = localRECT.left;
        ((TOOLINFO)localObject3).top = localRECT.top;
        ((TOOLINFO)localObject3).right = localRECT.right;
        ((TOOLINFO)localObject3).bottom = localRECT.bottom;
        ((TOOLINFO)localObject3).lpszText = -1;
        OS.SendMessage(this.headerToolTipHandle, OS.TTM_ADDTOOL, 0, (TOOLINFO)localObject3);
      }
    }
  }

  void createItem(TreeItem paramTreeItem, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = -1;
    if (paramTreeItem != null)
    {
      for (i = this.lastID < this.items.length ? this.lastID : 0; (i < this.items.length) && (this.items[i] != null); i++);
      if (i == this.items.length)
      {
        j = 0;
        if ((getDrawing()) && (OS.IsWindowVisible(this.handle)))
        {
          j = this.items.length + 4;
        }
        else
        {
          this.shrink = true;
          j = Math.max(4, this.items.length * 3 / 2);
        }
        TreeItem[] arrayOfTreeItem = new TreeItem[j];
        System.arraycopy(this.items, 0, arrayOfTreeItem, 0, this.items.length);
        this.items = arrayOfTreeItem;
      }
      this.lastID = (i + 1);
    }
    int j = 0;
    int k = OS.SendMessage(this.handle, 4362, 4, paramInt1);
    int m = k == 0 ? 1 : 0;
    Object localObject;
    if (paramInt3 == 0)
    {
      localObject = new TVINSERTSTRUCT();
      ((TVINSERTSTRUCT)localObject).hParent = paramInt1;
      ((TVINSERTSTRUCT)localObject).hInsertAfter = paramInt2;
      ((TVINSERTSTRUCT)localObject).lParam = i;
      ((TVINSERTSTRUCT)localObject).pszText = -1;
      ((TVINSERTSTRUCT)localObject).iImage = (((TVINSERTSTRUCT)localObject).iSelectedImage = -1);
      ((TVINSERTSTRUCT)localObject).mask = 55;
      if ((this.style & 0x20) != 0)
      {
        ((TVINSERTSTRUCT)localObject).mask |= 8;
        ((TVINSERTSTRUCT)localObject).state = 4096;
        ((TVINSERTSTRUCT)localObject).stateMask = 61440;
      }
      this.ignoreCustomDraw = true;
      j = OS.SendMessage(this.handle, OS.TVM_INSERTITEM, 0, (TVINSERTSTRUCT)localObject);
      this.ignoreCustomDraw = false;
      if (j == 0)
        error(14);
    }
    else
    {
      localObject = new TVITEM();
      ((TVITEM)localObject).mask = 20;
      ((TVITEM)localObject).hItem = (j = paramInt3);
      ((TVITEM)localObject).lParam = i;
      OS.SendMessage(this.handle, OS.TVM_SETITEM, 0, (TVITEM)localObject);
    }
    if (paramTreeItem != null)
    {
      paramTreeItem.handle = j;
      this.items[i] = paramTreeItem;
    }
    if ((k == 0) && ((paramInt2 == -65535) || (paramInt2 == -65534)))
    {
      this.hFirstIndexOf = (this.hLastIndexOf = k = j);
      this.itemCount = (this.lastIndexOf = 0);
    }
    if ((k == this.hFirstIndexOf) && (this.itemCount != -1))
      this.itemCount += 1;
    if (paramInt3 == 0)
    {
      if ((m != 0) && (getDrawing()) && (OS.IsWindowVisible(this.handle)))
      {
        localObject = new RECT();
        if (OS.TreeView_GetItemRect(this.handle, paramInt1, (RECT)localObject, false))
          OS.InvalidateRect(this.handle, (RECT)localObject, true);
      }
      if (((this.style & 0x10000000) != 0) && (this.currentItem != null))
      {
        localObject = new RECT();
        if (OS.TreeView_GetItemRect(this.handle, j, (RECT)localObject, false))
        {
          RECT localRECT = new RECT();
          boolean bool = OS.GetUpdateRect(this.handle, localRECT, true);
          if ((bool) && (localRECT.top < ((RECT)localObject).bottom))
            if (OS.IsWinCE)
            {
              OS.OffsetRect(localRECT, 0, ((RECT)localObject).bottom - ((RECT)localObject).top);
              OS.InvalidateRect(this.handle, localRECT, true);
            }
            else
            {
              int n = OS.CreateRectRgn(0, 0, 0, 0);
              int i1 = OS.GetUpdateRgn(this.handle, n, true);
              if (i1 != 1)
              {
                OS.OffsetRgn(n, 0, ((RECT)localObject).bottom - ((RECT)localObject).top);
                OS.InvalidateRgn(this.handle, n, true);
              }
              OS.DeleteObject(n);
            }
        }
      }
      updateScrollBar();
    }
  }

  void createItemToolTips()
  {
    if (OS.IsWinCE)
      return;
    if (this.itemToolTipHandle != 0)
      return;
    int i = OS.GetWindowLong(this.handle, -16);
    i |= 128;
    OS.SetWindowLong(this.handle, -16, i);
    int j = 0;
    if ((OS.WIN32_VERSION >= OS.VERSION(4, 10)) && ((this.style & 0x4000000) != 0))
      j |= 4194304;
    if (OS.COMCTL32_MAJOR >= 6)
      j |= 32;
    this.itemToolTipHandle = OS.CreateWindowEx(j, new TCHAR(0, "tooltips_class32", true), null, 50, -2147483648, 0, -2147483648, 0, this.handle, 0, OS.GetModuleHandle(null), null);
    if (this.itemToolTipHandle == 0)
      error(2);
    OS.SendMessage(this.itemToolTipHandle, 1027, 3, 0);
    TOOLINFO localTOOLINFO = new TOOLINFO();
    localTOOLINFO.cbSize = TOOLINFO.sizeof;
    localTOOLINFO.hwnd = this.handle;
    localTOOLINFO.uId = this.handle;
    localTOOLINFO.uFlags = 272;
    localTOOLINFO.lpszText = -1;
    OS.SendMessage(this.itemToolTipHandle, OS.TTM_ADDTOOL, 0, localTOOLINFO);
  }

  void createParent()
  {
    forceResize();
    RECT localRECT = new RECT();
    OS.GetWindowRect(this.handle, localRECT);
    OS.MapWindowPoints(0, this.parent.handle, localRECT, 2);
    int i = OS.GetWindowLong(this.handle, -16);
    int j = super.widgetStyle() & 0xEFFFFFFF;
    if ((i & 0x4000000) != 0)
      j |= 67108864;
    this.hwndParent = OS.CreateWindowEx(super.widgetExtStyle(), super.windowClass(), null, j, localRECT.left, localRECT.top, localRECT.right - localRECT.left, localRECT.bottom - localRECT.top, this.parent.handle, 0, OS.GetModuleHandle(null), null);
    if (this.hwndParent == 0)
      error(2);
    OS.SetWindowLongPtr(this.hwndParent, -12, this.hwndParent);
    int k = 0;
    if (OS.WIN32_VERSION >= OS.VERSION(4, 10))
    {
      k |= 1048576;
      if ((this.style & 0x4000000) != 0)
        k |= 4194304;
    }
    this.hwndHeader = OS.CreateWindowEx(k, HeaderClass, null, 1140850890, 0, 0, 0, 0, this.hwndParent, 0, OS.GetModuleHandle(null), null);
    if (this.hwndHeader == 0)
      error(2);
    OS.SetWindowLongPtr(this.hwndHeader, -12, this.hwndHeader);
    if (OS.IsDBLocale)
    {
      m = OS.ImmGetContext(this.handle);
      OS.ImmAssociateContext(this.hwndParent, m);
      OS.ImmAssociateContext(this.hwndHeader, m);
      OS.ImmReleaseContext(this.handle, m);
    }
    int m = OS.SendMessage(this.handle, 49, 0, 0);
    if (m != 0)
      OS.SendMessage(this.hwndHeader, 48, m, 0);
    int n = OS.GetWindow(this.handle, 3);
    int i1 = 19;
    SetWindowPos(this.hwndParent, n, 0, 0, 0, 0, i1);
    SCROLLINFO localSCROLLINFO = new SCROLLINFO();
    localSCROLLINFO.cbSize = SCROLLINFO.sizeof;
    localSCROLLINFO.fMask = 3;
    OS.GetScrollInfo(this.hwndParent, 0, localSCROLLINFO);
    localSCROLLINFO.nPage = (localSCROLLINFO.nMax + 1);
    OS.SetScrollInfo(this.hwndParent, 0, localSCROLLINFO, true);
    OS.GetScrollInfo(this.hwndParent, 1, localSCROLLINFO);
    localSCROLLINFO.nPage = (localSCROLLINFO.nMax + 1);
    OS.SetScrollInfo(this.hwndParent, 1, localSCROLLINFO, true);
    this.customDraw = true;
    deregister();
    if ((i & 0x10000000) != 0)
      OS.ShowWindow(this.hwndParent, 5);
    int i2 = OS.GetFocus();
    if (i2 == this.handle)
      OS.SetFocus(this.hwndParent);
    OS.SetParent(this.handle, this.hwndParent);
    if (i2 == this.handle)
      OS.SetFocus(this.handle);
    register();
    subclass();
  }

  void createWidget()
  {
    super.createWidget();
    this.items = new TreeItem[4];
    this.columns = new TreeColumn[4];
    this.itemCount = -1;
  }

  int defaultBackground()
  {
    return OS.GetSysColor(OS.COLOR_WINDOW);
  }

  void deregister()
  {
    super.deregister();
    if (this.hwndParent != 0)
      this.display.removeControl(this.hwndParent);
    if (this.hwndHeader != 0)
      this.display.removeControl(this.hwndHeader);
  }

  void deselect(int paramInt1, TVITEM paramTVITEM, int paramInt2)
  {
    while (paramInt1 != 0)
    {
      if (paramInt1 != paramInt2)
      {
        paramTVITEM.hItem = paramInt1;
        OS.SendMessage(this.handle, OS.TVM_SETITEM, 0, paramTVITEM);
      }
      int i = OS.SendMessage(this.handle, 4362, 4, paramInt1);
      deselect(i, paramTVITEM, paramInt2);
      paramInt1 = OS.SendMessage(this.handle, 4362, 1, paramInt1);
    }
  }

  public void deselect(TreeItem paramTreeItem)
  {
    checkWidget();
    if (paramTreeItem == null)
      error(4);
    if (paramTreeItem.isDisposed())
      error(5);
    TVITEM localTVITEM = new TVITEM();
    localTVITEM.mask = 24;
    localTVITEM.stateMask = 2;
    localTVITEM.hItem = paramTreeItem.handle;
    OS.SendMessage(this.handle, OS.TVM_SETITEM, 0, localTVITEM);
  }

  public void deselectAll()
  {
    checkWidget();
    TVITEM localTVITEM = new TVITEM();
    localTVITEM.mask = 24;
    localTVITEM.stateMask = 2;
    int i;
    if ((this.style & 0x4) != 0)
    {
      i = OS.SendMessage(this.handle, 4362, 9, 0);
      if (i != 0)
      {
        localTVITEM.hItem = i;
        OS.SendMessage(this.handle, OS.TVM_SETITEM, 0, localTVITEM);
      }
    }
    else
    {
      i = OS.GetWindowLongPtr(this.handle, -4);
      OS.SetWindowLongPtr(this.handle, -4, TreeProc);
      int j;
      if ((this.style & 0x10000000) != 0)
      {
        j = OS.SendMessage(this.handle, 4362, 0, 0);
        deselect(j, localTVITEM, 0);
      }
      else
      {
        for (j = 0; j < this.items.length; j++)
        {
          TreeItem localTreeItem = this.items[j];
          if (localTreeItem != null)
          {
            localTVITEM.hItem = localTreeItem.handle;
            OS.SendMessage(this.handle, OS.TVM_SETITEM, 0, localTVITEM);
          }
        }
      }
      OS.SetWindowLongPtr(this.handle, -4, i);
    }
  }

  void destroyItem(TreeColumn paramTreeColumn)
  {
    if (this.hwndHeader == 0)
      error(15);
    for (int i = 0; i < this.columnCount; i++)
      if (this.columns[i] == paramTreeColumn)
        break;
    int[] arrayOfInt = new int[this.columnCount];
    OS.SendMessage(this.hwndHeader, 4625, this.columnCount, arrayOfInt);
    for (Object localObject1 = 0; localObject1 < this.columnCount; localObject1++)
      if (arrayOfInt[localObject1] == i)
        break;
    RECT localRECT = new RECT();
    OS.SendMessage(this.hwndHeader, 4615, i, localRECT);
    if (OS.SendMessage(this.hwndHeader, 4610, i, 0) == 0)
      error(15);
    System.arraycopy(this.columns, i + 1, this.columns, i, --this.columnCount - i);
    this.columns[this.columnCount] = null;
    Object localObject3;
    Object localObject4;
    for (int j = 0; j < this.items.length; j++)
    {
      localObject3 = this.items[j];
      if (localObject3 != null)
        if (this.columnCount == 0)
        {
          ((TreeItem)localObject3).strings = null;
          ((TreeItem)localObject3).images = null;
          ((TreeItem)localObject3).cellBackground = null;
          ((TreeItem)localObject3).cellForeground = null;
          ((TreeItem)localObject3).cellFont = null;
        }
        else
        {
          Object localObject5;
          if (((TreeItem)localObject3).strings != null)
          {
            localObject4 = ((TreeItem)localObject3).strings;
            if (i == 0)
              ((TreeItem)localObject3).text = (localObject4[1] != null ? localObject4[1] : "");
            localObject5 = new String[this.columnCount];
            System.arraycopy(localObject4, 0, localObject5, 0, i);
            System.arraycopy(localObject4, i + 1, localObject5, i, this.columnCount - i);
            ((TreeItem)localObject3).strings = ((String[])localObject5);
          }
          else if (i == 0)
          {
            ((TreeItem)localObject3).text = "";
          }
          if (((TreeItem)localObject3).images != null)
          {
            localObject4 = ((TreeItem)localObject3).images;
            if (i == 0)
              ((TreeItem)localObject3).image = localObject4[1];
            localObject5 = new Image[this.columnCount];
            System.arraycopy(localObject4, 0, localObject5, 0, i);
            System.arraycopy(localObject4, i + 1, localObject5, i, this.columnCount - i);
            ((TreeItem)localObject3).images = ((Image[])localObject5);
          }
          else if (i == 0)
          {
            ((TreeItem)localObject3).image = null;
          }
          if (((TreeItem)localObject3).cellBackground != null)
          {
            localObject4 = ((TreeItem)localObject3).cellBackground;
            localObject5 = new int[this.columnCount];
            System.arraycopy(localObject4, 0, localObject5, 0, i);
            System.arraycopy(localObject4, i + 1, localObject5, i, this.columnCount - i);
            ((TreeItem)localObject3).cellBackground = ((int[])localObject5);
          }
          if (((TreeItem)localObject3).cellForeground != null)
          {
            localObject4 = ((TreeItem)localObject3).cellForeground;
            localObject5 = new int[this.columnCount];
            System.arraycopy(localObject4, 0, localObject5, 0, i);
            System.arraycopy(localObject4, i + 1, localObject5, i, this.columnCount - i);
            ((TreeItem)localObject3).cellForeground = ((int[])localObject5);
          }
          if (((TreeItem)localObject3).cellFont != null)
          {
            localObject4 = ((TreeItem)localObject3).cellFont;
            localObject5 = new Font[this.columnCount];
            System.arraycopy(localObject4, 0, localObject5, 0, i);
            System.arraycopy(localObject4, i + 1, localObject5, i, this.columnCount - i);
            ((TreeItem)localObject3).cellFont = ((Font[])localObject5);
          }
        }
    }
    Object localObject2;
    if (this.columnCount == 0)
    {
      this.scrollWidth = 0;
      if (!hooks(41))
      {
        j = OS.GetWindowLong(this.handle, -16);
        if ((this.style & 0x100) != 0)
          j &= -32769;
        OS.SetWindowLong(this.handle, -16, j);
        OS.InvalidateRect(this.handle, null, true);
      }
      if (this.itemToolTipHandle != 0)
        OS.SendMessage(this.itemToolTipHandle, 1027, 3, 0);
    }
    else
    {
      if (i == 0)
      {
        this.columns[0].style &= -16924673;
        this.columns[0].style |= 16384;
        localObject2 = new HDITEM();
        ((HDITEM)localObject2).mask = 36;
        OS.SendMessage(this.hwndHeader, OS.HDM_GETITEM, i, (HDITEM)localObject2);
        localObject2.fmt &= -4;
        localObject2.fmt |= 0;
        OS.SendMessage(this.hwndHeader, OS.HDM_SETITEM, i, (HDITEM)localObject2);
      }
      localObject2 = new RECT();
      OS.GetClientRect(this.handle, (RECT)localObject2);
      ((RECT)localObject2).left = localRECT.left;
      OS.InvalidateRect(this.handle, (RECT)localObject2, true);
    }
    setScrollWidth();
    updateImageList();
    updateScrollBar();
    if (this.columnCount != 0)
    {
      localObject2 = new int[this.columnCount];
      OS.SendMessage(this.hwndHeader, 4625, this.columnCount, (int[])localObject2);
      localObject3 = new TreeColumn[this.columnCount - localObject1];
      for (localObject4 = localObject1; localObject4 < localObject2.length; localObject4++)
      {
        localObject3[(localObject4 - localObject1)] = this.columns[localObject2[localObject4]];
        localObject3[(localObject4 - localObject1)].updateToolTip(localObject2[localObject4]);
      }
      for (int k = 0; k < localObject3.length; k++)
        if (!localObject3[k].isDisposed())
          localObject3[k].sendEvent(10);
    }
    if (this.headerToolTipHandle != 0)
    {
      localObject2 = new TOOLINFO();
      ((TOOLINFO)localObject2).cbSize = TOOLINFO.sizeof;
      ((TOOLINFO)localObject2).uId = paramTreeColumn.id;
      ((TOOLINFO)localObject2).hwnd = this.hwndHeader;
      OS.SendMessage(this.headerToolTipHandle, OS.TTM_DELTOOL, 0, (TOOLINFO)localObject2);
    }
  }

  void destroyItem(TreeItem paramTreeItem, int paramInt)
  {
    this.hFirstIndexOf = (this.hLastIndexOf = 0);
    this.itemCount = -1;
    int i = 0;
    int j = 0;
    if (((this.style & 0x20000000) == 0) && (getDrawing()) && (OS.IsWindowVisible(this.handle)))
    {
      RECT localRECT1 = new RECT();
      j = OS.TreeView_GetItemRect(this.handle, paramInt, localRECT1, false) ? 0 : 1;
    }
    if (j != 0)
    {
      i = OS.SendMessage(this.handle, 4362, 3, paramInt);
      OS.UpdateWindow(this.handle);
      OS.DefWindowProc(this.handle, 11, 0, 0);
    }
    if ((this.style & 0x2) != 0)
      this.ignoreDeselect = (this.ignoreSelect = this.lockSelection = 1);
    if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(6, 0)))
    {
      int k = OS.SendMessage(this.handle, 4377, 0, 0);
      if (k != 0)
        OS.SendMessage(k, 1052, 0, 0);
    }
    this.shrink = (this.ignoreShrink = 1);
    OS.SendMessage(this.handle, 4353, 0, paramInt);
    this.ignoreShrink = false;
    if ((this.style & 0x2) != 0)
      this.ignoreDeselect = (this.ignoreSelect = this.lockSelection = 0);
    if (j != 0)
    {
      OS.DefWindowProc(this.handle, 11, 1, 0);
      OS.ValidateRect(this.handle, null);
      if (OS.SendMessage(this.handle, 4362, 4, i) == 0)
      {
        RECT localRECT2 = new RECT();
        if (OS.TreeView_GetItemRect(this.handle, i, localRECT2, false))
          OS.InvalidateRect(this.handle, localRECT2, true);
      }
    }
    int m = OS.SendMessage(this.handle, 4357, 0, 0);
    if (m == 0)
    {
      if (this.imageList != null)
      {
        OS.SendMessage(this.handle, 4361, 0, 0);
        this.display.releaseImageList(this.imageList);
      }
      this.imageList = null;
      if ((this.hwndParent == 0) && (!this.linesVisible) && (!hooks(41)) && (!hooks(40)) && (!hooks(42)))
        this.customDraw = false;
      this.items = new TreeItem[4];
      this.scrollWidth = 0;
      setScrollWidth();
    }
    updateScrollBar();
  }

  void destroyScrollBar(int paramInt)
  {
    super.destroyScrollBar(paramInt);
    int i = OS.GetWindowLong(this.handle, -16);
    if ((this.style & 0x300) == 0)
    {
      i &= -3145729;
      i |= 8192;
    }
    else if ((this.style & 0x100) == 0)
    {
      i &= -1048577;
      i |= 32768;
    }
    OS.SetWindowLong(this.handle, -16, i);
  }

  void enableDrag(boolean paramBoolean)
  {
    int i = OS.GetWindowLong(this.handle, -16);
    if ((paramBoolean) && (hooks(29)))
      i &= -17;
    else
      i |= 16;
    OS.SetWindowLong(this.handle, -16, i);
  }

  void enableWidget(boolean paramBoolean)
  {
    super.enableWidget(paramBoolean);
    Object localObject = findBackgroundControl();
    if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(6, 0)) && (localObject == null))
      localObject = this;
    if ((localObject != null) && (((Control)localObject).backgroundImage == null))
      _setBackgroundPixel(paramBoolean ? ((Control)localObject).getBackgroundPixel() : -1);
    if (this.hwndParent != 0)
      OS.EnableWindow(this.hwndParent, paramBoolean);
    updateFullSelection();
  }

  boolean findCell(int paramInt1, int paramInt2, TreeItem[] paramArrayOfTreeItem, int[] paramArrayOfInt, RECT[] paramArrayOfRECT1, RECT[] paramArrayOfRECT2)
  {
    boolean bool = false;
    TVHITTESTINFO localTVHITTESTINFO = new TVHITTESTINFO();
    localTVHITTESTINFO.x = paramInt1;
    localTVHITTESTINFO.y = paramInt2;
    OS.SendMessage(this.handle, 4369, 0, localTVHITTESTINFO);
    if (localTVHITTESTINFO.hItem != 0)
    {
      paramArrayOfTreeItem[0] = _getItem(localTVHITTESTINFO.hItem);
      POINT localPOINT = new POINT();
      localPOINT.x = paramInt1;
      localPOINT.y = paramInt2;
      int i = OS.GetDC(this.handle);
      int j = 0;
      int k = OS.SendMessage(this.handle, 49, 0, 0);
      if (k != 0)
        j = OS.SelectObject(i, k);
      RECT localRECT = new RECT();
      if (this.hwndParent != 0)
      {
        OS.GetClientRect(this.hwndParent, localRECT);
        OS.MapWindowPoints(this.hwndParent, this.handle, localRECT, 2);
      }
      else
      {
        OS.GetClientRect(this.handle, localRECT);
      }
      int m = Math.max(1, this.columnCount);
      int[] arrayOfInt = new int[m];
      if (this.hwndHeader != 0)
        OS.SendMessage(this.hwndHeader, 4625, m, arrayOfInt);
      paramArrayOfInt[0] = 0;
      int n = 0;
      while ((paramArrayOfInt[0] < m) && (n == 0))
      {
        int i1 = paramArrayOfTreeItem[0].fontHandle(arrayOfInt[paramArrayOfInt[0]]);
        if (i1 != -1)
          i1 = OS.SelectObject(i, i1);
        paramArrayOfRECT1[0] = paramArrayOfTreeItem[0].getBounds(arrayOfInt[paramArrayOfInt[0]], true, false, true, false, true, i);
        if (paramArrayOfRECT1[0].left > localRECT.right)
        {
          n = 1;
        }
        else
        {
          paramArrayOfRECT1[0].right = Math.min(paramArrayOfRECT1[0].right, localRECT.right);
          if (OS.PtInRect(paramArrayOfRECT1[0], localPOINT))
          {
            if (isCustomToolTip())
            {
              Event localEvent = sendMeasureItemEvent(paramArrayOfTreeItem[0], arrayOfInt[paramArrayOfInt[0]], i);
              if ((isDisposed()) || (paramArrayOfTreeItem[0].isDisposed()))
                break;
              paramArrayOfRECT2[0] = new RECT();
              paramArrayOfRECT2[0].left = localEvent.x;
              paramArrayOfRECT2[0].right = (localEvent.x + localEvent.width);
              paramArrayOfRECT2[0].top = localEvent.y;
              paramArrayOfRECT2[0].bottom = (localEvent.y + localEvent.height);
            }
            else
            {
              paramArrayOfRECT2[0] = paramArrayOfTreeItem[0].getBounds(arrayOfInt[paramArrayOfInt[0]], true, false, false, false, false, i);
            }
            if (paramArrayOfRECT2[0].right > paramArrayOfRECT1[0].right)
              bool = true;
            n = 1;
          }
        }
        if (i1 != -1)
          OS.SelectObject(i, i1);
        if (!bool)
          paramArrayOfInt[0] += 1;
      }
      if (k != 0)
        OS.SelectObject(i, j);
      OS.ReleaseDC(this.handle, i);
    }
    return bool;
  }

  int findIndex(int paramInt1, int paramInt2)
  {
    if (paramInt1 == 0)
      return -1;
    if (paramInt1 == this.hFirstIndexOf)
    {
      if (this.hFirstIndexOf == paramInt2)
      {
        this.hLastIndexOf = this.hFirstIndexOf;
        return this.lastIndexOf = 0;
      }
      if (this.hLastIndexOf == paramInt2)
        return this.lastIndexOf;
      i = OS.SendMessage(this.handle, 4362, 2, this.hLastIndexOf);
      if (i == paramInt2)
      {
        this.hLastIndexOf = i;
        return --this.lastIndexOf;
      }
      j = OS.SendMessage(this.handle, 4362, 1, this.hLastIndexOf);
      if (j == paramInt2)
      {
        this.hLastIndexOf = j;
        return ++this.lastIndexOf;
      }
      for (int k = this.lastIndexOf - 1; (i != 0) && (i != paramInt2); k--)
        i = OS.SendMessage(this.handle, 4362, 2, i);
      if (i == paramInt2)
      {
        this.hLastIndexOf = i;
        return this.lastIndexOf = k;
      }
      for (int m = this.lastIndexOf + 1; (j != 0) && (j != paramInt2); m++)
        j = OS.SendMessage(this.handle, 4362, 1, j);
      if (j == paramInt2)
      {
        this.hLastIndexOf = j;
        return this.lastIndexOf = m;
      }
      return -1;
    }
    int i = 0;
    int j = paramInt1;
    while ((j != 0) && (j != paramInt2))
    {
      j = OS.SendMessage(this.handle, 4362, 1, j);
      i++;
    }
    if (j == paramInt2)
    {
      this.itemCount = -1;
      this.hFirstIndexOf = paramInt1;
      this.hLastIndexOf = j;
      return this.lastIndexOf = i;
    }
    return -1;
  }

  Widget findItem(int paramInt)
  {
    return _getItem(paramInt);
  }

  int findItem(int paramInt1, int paramInt2)
  {
    if (paramInt1 == 0)
      return 0;
    if (paramInt1 == this.hFirstIndexOf)
    {
      if (paramInt2 == 0)
      {
        this.lastIndexOf = 0;
        return this.hLastIndexOf = this.hFirstIndexOf;
      }
      if (this.lastIndexOf == paramInt2)
        return this.hLastIndexOf;
      if (this.lastIndexOf - 1 == paramInt2)
      {
        this.lastIndexOf -= 1;
        return this.hLastIndexOf = OS.SendMessage(this.handle, 4362, 2, this.hLastIndexOf);
      }
      if (this.lastIndexOf + 1 == paramInt2)
      {
        this.lastIndexOf += 1;
        return this.hLastIndexOf = OS.SendMessage(this.handle, 4362, 1, this.hLastIndexOf);
      }
      if (paramInt2 < this.lastIndexOf)
      {
        i = this.lastIndexOf - 1;
        j = OS.SendMessage(this.handle, 4362, 2, this.hLastIndexOf);
        while ((j != 0) && (paramInt2 < i))
        {
          j = OS.SendMessage(this.handle, 4362, 2, j);
          i--;
        }
        if (paramInt2 == i)
        {
          this.lastIndexOf = i;
          return this.hLastIndexOf = j;
        }
      }
      else
      {
        i = this.lastIndexOf + 1;
        j = OS.SendMessage(this.handle, 4362, 1, this.hLastIndexOf);
        while ((j != 0) && (i < paramInt2))
        {
          j = OS.SendMessage(this.handle, 4362, 1, j);
          i++;
        }
        if (paramInt2 == i)
        {
          this.lastIndexOf = i;
          return this.hLastIndexOf = j;
        }
      }
      return 0;
    }
    int i = 0;
    int j = paramInt1;
    while ((j != 0) && (i < paramInt2))
    {
      j = OS.SendMessage(this.handle, 4362, 1, j);
      i++;
    }
    if (paramInt2 == i)
    {
      this.itemCount = -1;
      this.lastIndexOf = i;
      this.hFirstIndexOf = paramInt1;
      return this.hLastIndexOf = j;
    }
    return 0;
  }

  TreeItem getFocusItem()
  {
    int i = OS.SendMessage(this.handle, 4362, 9, 0);
    return i != 0 ? _getItem(i) : null;
  }

  public int getGridLineWidth()
  {
    checkWidget();
    return 1;
  }

  public int getHeaderHeight()
  {
    checkWidget();
    if (this.hwndHeader == 0)
      return 0;
    RECT localRECT = new RECT();
    OS.GetWindowRect(this.hwndHeader, localRECT);
    return localRECT.bottom - localRECT.top;
  }

  public boolean getHeaderVisible()
  {
    checkWidget();
    if (this.hwndHeader == 0)
      return false;
    int i = OS.GetWindowLong(this.hwndHeader, -16);
    return (i & 0x10000000) != 0;
  }

  Point getImageSize()
  {
    if (this.imageList != null)
      return this.imageList.getImageSize();
    return new Point(0, getItemHeight());
  }

  int getBottomItem()
  {
    int i = OS.SendMessage(this.handle, 4362, 5, 0);
    if (i == 0)
      return 0;
    int j = 0;
    int k = OS.SendMessage(this.handle, 4368, 0, 0);
    while (j <= k)
    {
      int m = OS.SendMessage(this.handle, 4362, 6, i);
      if (m == 0)
        return i;
      i = m;
      j++;
    }
    return i;
  }

  public TreeColumn getColumn(int paramInt)
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
    OS.SendMessage(this.hwndHeader, 4625, this.columnCount, arrayOfInt);
    return arrayOfInt;
  }

  public TreeColumn[] getColumns()
  {
    checkWidget();
    TreeColumn[] arrayOfTreeColumn = new TreeColumn[this.columnCount];
    System.arraycopy(this.columns, 0, arrayOfTreeColumn, 0, this.columnCount);
    return arrayOfTreeColumn;
  }

  public TreeItem getItem(int paramInt)
  {
    checkWidget();
    if (paramInt < 0)
      error(6);
    int i = OS.SendMessage(this.handle, 4362, 0, 0);
    if (i == 0)
      error(6);
    int j = findItem(i, paramInt);
    if (j == 0)
      error(6);
    return _getItem(j);
  }

  TreeItem getItem(NMTVCUSTOMDRAW paramNMTVCUSTOMDRAW)
  {
    int i = paramNMTVCUSTOMDRAW.lItemlParam;
    if (((this.style & 0x10000000) != 0) && (i == -1))
    {
      TVITEM localTVITEM = new TVITEM();
      localTVITEM.mask = 20;
      localTVITEM.hItem = paramNMTVCUSTOMDRAW.dwItemSpec;
      OS.SendMessage(this.handle, OS.TVM_GETITEM, 0, localTVITEM);
      i = localTVITEM.lParam;
    }
    return _getItem(paramNMTVCUSTOMDRAW.dwItemSpec, i);
  }

  public TreeItem getItem(Point paramPoint)
  {
    checkWidget();
    if (paramPoint == null)
      error(4);
    TVHITTESTINFO localTVHITTESTINFO = new TVHITTESTINFO();
    localTVHITTESTINFO.x = paramPoint.x;
    localTVHITTESTINFO.y = paramPoint.y;
    OS.SendMessage(this.handle, 4369, 0, localTVHITTESTINFO);
    if (localTVHITTESTINFO.hItem != 0)
    {
      int i = 70;
      if ((this.style & 0x10000) != 0)
      {
        i |= 40;
      }
      else if (hooks(41))
      {
        localTVHITTESTINFO.flags &= -7;
        if (hitTestSelection(localTVHITTESTINFO.hItem, localTVHITTESTINFO.x, localTVHITTESTINFO.y))
          localTVHITTESTINFO.flags |= 6;
      }
      if ((localTVHITTESTINFO.flags & i) != 0)
        return _getItem(localTVHITTESTINFO.hItem);
    }
    return null;
  }

  public int getItemCount()
  {
    checkWidget();
    int i = OS.SendMessage(this.handle, 4362, 0, 0);
    if (i == 0)
      return 0;
    return getItemCount(i);
  }

  int getItemCount(int paramInt)
  {
    int i = 0;
    int j = paramInt;
    if (paramInt == this.hFirstIndexOf)
    {
      if (this.itemCount != -1)
        return this.itemCount;
      j = this.hLastIndexOf;
    }
    for (i = this.lastIndexOf; j != 0; i++)
      j = OS.SendMessage(this.handle, 4362, 1, j);
    if (paramInt == this.hFirstIndexOf)
      this.itemCount = i;
    return i;
  }

  public int getItemHeight()
  {
    checkWidget();
    return OS.SendMessage(this.handle, 4380, 0, 0);
  }

  public TreeItem[] getItems()
  {
    checkWidget();
    int i = OS.SendMessage(this.handle, 4362, 0, 0);
    if (i == 0)
      return new TreeItem[0];
    return getItems(i);
  }

  TreeItem[] getItems(int paramInt)
  {
    int i = 0;
    int j = paramInt;
    while (j != 0)
    {
      j = OS.SendMessage(this.handle, 4362, 1, j);
      i++;
    }
    int k = 0;
    Object localObject1 = new TreeItem[i];
    TVITEM localTVITEM = new TVITEM();
    localTVITEM.mask = 20;
    Object localObject2;
    for (localTVITEM.hItem = paramInt; localTVITEM.hItem != 0; localTVITEM.hItem = OS.SendMessage(this.handle, 4362, 1, localTVITEM.hItem))
    {
      OS.SendMessage(this.handle, OS.TVM_GETITEM, 0, localTVITEM);
      localObject2 = _getItem(localTVITEM.hItem, localTVITEM.lParam);
      if (localObject2 != null)
        localObject1[(k++)] = localObject2;
    }
    if (k != i)
    {
      localObject2 = new TreeItem[k];
      System.arraycopy(localObject1, 0, localObject2, 0, k);
      localObject1 = localObject2;
    }
    return localObject1;
  }

  public boolean getLinesVisible()
  {
    checkWidget();
    return this.linesVisible;
  }

  int getNextSelection(int paramInt, TVITEM paramTVITEM)
  {
    while (paramInt != 0)
    {
      int i = 0;
      if (OS.IsWinCE)
      {
        paramTVITEM.hItem = paramInt;
        OS.SendMessage(this.handle, OS.TVM_GETITEM, 0, paramTVITEM);
        i = paramTVITEM.state;
      }
      else
      {
        i = OS.SendMessage(this.handle, 4391, paramInt, 2);
      }
      if ((i & 0x2) != 0)
        return paramInt;
      int j = OS.SendMessage(this.handle, 4362, 4, paramInt);
      int k = getNextSelection(j, paramTVITEM);
      if (k != 0)
        return k;
      paramInt = OS.SendMessage(this.handle, 4362, 1, paramInt);
    }
    return 0;
  }

  public TreeItem getParentItem()
  {
    checkWidget();
    return null;
  }

  int getSelection(int paramInt1, TVITEM paramTVITEM, TreeItem[] paramArrayOfTreeItem, int paramInt2, int paramInt3, boolean paramBoolean1, boolean paramBoolean2)
  {
    while (paramInt1 != 0)
    {
      int i;
      if ((OS.IsWinCE) || (paramBoolean1))
      {
        paramTVITEM.hItem = paramInt1;
        OS.SendMessage(this.handle, OS.TVM_GETITEM, 0, paramTVITEM);
        if ((paramTVITEM.state & 0x2) != 0)
        {
          if ((paramArrayOfTreeItem != null) && (paramInt2 < paramArrayOfTreeItem.length))
            paramArrayOfTreeItem[paramInt2] = _getItem(paramInt1, paramTVITEM.lParam);
          paramInt2++;
        }
      }
      else
      {
        i = OS.SendMessage(this.handle, 4391, paramInt1, 2);
        if ((i & 0x2) != 0)
        {
          if ((paramTVITEM != null) && (paramArrayOfTreeItem != null) && (paramInt2 < paramArrayOfTreeItem.length))
          {
            paramTVITEM.hItem = paramInt1;
            OS.SendMessage(this.handle, OS.TVM_GETITEM, 0, paramTVITEM);
            paramArrayOfTreeItem[paramInt2] = _getItem(paramInt1, paramTVITEM.lParam);
          }
          paramInt2++;
        }
      }
      if (paramInt2 == paramInt3)
        break;
      if (paramBoolean2)
      {
        i = OS.SendMessage(this.handle, 4362, 4, paramInt1);
        if ((paramInt2 = getSelection(i, paramTVITEM, paramArrayOfTreeItem, paramInt2, paramInt3, paramBoolean1, paramBoolean2)) == paramInt3)
          break;
        paramInt1 = OS.SendMessage(this.handle, 4362, 1, paramInt1);
      }
      else
      {
        paramInt1 = OS.SendMessage(this.handle, 4362, 6, paramInt1);
      }
    }
    return paramInt2;
  }

  public TreeItem[] getSelection()
  {
    checkWidget();
    if ((this.style & 0x4) != 0)
    {
      i = OS.SendMessage(this.handle, 4362, 9, 0);
      if (i == 0)
        return new TreeItem[0];
      localObject1 = new TVITEM();
      ((TVITEM)localObject1).mask = 28;
      ((TVITEM)localObject1).hItem = i;
      OS.SendMessage(this.handle, OS.TVM_GETITEM, 0, (TVITEM)localObject1);
      if ((((TVITEM)localObject1).state & 0x2) == 0)
        return new TreeItem[0];
      return new TreeItem[] { _getItem(((TVITEM)localObject1).hItem, ((TVITEM)localObject1).lParam) };
    }
    int i = 0;
    Object localObject1 = new TreeItem[(this.style & 0x10000000) != 0 ? 8 : 1];
    int j = OS.GetWindowLongPtr(this.handle, -4);
    OS.SetWindowLongPtr(this.handle, -4, TreeProc);
    int k;
    if ((this.style & 0x10000000) != 0)
    {
      localObject2 = new TVITEM();
      ((TVITEM)localObject2).mask = 28;
      k = OS.SendMessage(this.handle, 4362, 0, 0);
      i = getSelection(k, (TVITEM)localObject2, (TreeItem[])localObject1, 0, -1, false, true);
    }
    else
    {
      localObject2 = null;
      if (OS.IsWinCE)
      {
        localObject2 = new TVITEM();
        ((TVITEM)localObject2).mask = 8;
      }
      for (k = 0; k < this.items.length; k++)
      {
        TreeItem localTreeItem = this.items[k];
        if (localTreeItem != null)
        {
          n = localTreeItem.handle;
          i1 = 0;
          if (OS.IsWinCE)
          {
            ((TVITEM)localObject2).hItem = n;
            OS.SendMessage(this.handle, OS.TVM_GETITEM, 0, (TVITEM)localObject2);
            i1 = ((TVITEM)localObject2).state;
          }
          else
          {
            i1 = OS.SendMessage(this.handle, 4391, n, 2);
          }
          if ((i1 & 0x2) != 0)
          {
            if (i < localObject1.length)
              localObject1[i] = localTreeItem;
            i++;
          }
        }
      }
    }
    OS.SetWindowLongPtr(this.handle, -4, j);
    if (i == 0)
      return new TreeItem[0];
    if (i == localObject1.length)
      return localObject1;
    Object localObject2 = new TreeItem[i];
    if (i < localObject1.length)
    {
      System.arraycopy(localObject1, 0, localObject2, 0, i);
      return localObject2;
    }
    OS.SetWindowLongPtr(this.handle, -4, TreeProc);
    TVITEM localTVITEM = new TVITEM();
    localTVITEM.mask = 28;
    int m = OS.SendMessage(this.handle, 4362, 0, 0);
    int n = OS.SendMessage(this.handle, 4357, 0, 0);
    int i1 = localObject2.length > n / 2 ? 1 : 0;
    if (i != getSelection(m, localTVITEM, (TreeItem[])localObject2, 0, i, i1, false))
      getSelection(m, localTVITEM, (TreeItem[])localObject2, 0, i, i1, true);
    OS.SetWindowLongPtr(this.handle, -4, j);
    return localObject2;
  }

  public int getSelectionCount()
  {
    checkWidget();
    if ((this.style & 0x4) != 0)
    {
      i = OS.SendMessage(this.handle, 4362, 9, 0);
      if (i == 0)
        return 0;
      j = 0;
      if (OS.IsWinCE)
      {
        localTVITEM = new TVITEM();
        localTVITEM.hItem = i;
        localTVITEM.mask = 8;
        OS.SendMessage(this.handle, OS.TVM_GETITEM, 0, localTVITEM);
        j = localTVITEM.state;
      }
      else
      {
        j = OS.SendMessage(this.handle, 4391, i, 2);
      }
      return (j & 0x2) == 0 ? 0 : 1;
    }
    int i = 0;
    int j = OS.GetWindowLongPtr(this.handle, -4);
    TVITEM localTVITEM = null;
    if (OS.IsWinCE)
    {
      localTVITEM = new TVITEM();
      localTVITEM.mask = 8;
    }
    OS.SetWindowLongPtr(this.handle, -4, TreeProc);
    int k;
    if ((this.style & 0x10000000) != 0)
    {
      k = OS.SendMessage(this.handle, 4362, 0, 0);
      i = getSelection(k, localTVITEM, null, 0, -1, false, true);
    }
    else
    {
      for (k = 0; k < this.items.length; k++)
      {
        TreeItem localTreeItem = this.items[k];
        if (localTreeItem != null)
        {
          int m = localTreeItem.handle;
          int n = 0;
          if (OS.IsWinCE)
          {
            localTVITEM.hItem = m;
            OS.SendMessage(this.handle, OS.TVM_GETITEM, 0, localTVITEM);
            n = localTVITEM.state;
          }
          else
          {
            n = OS.SendMessage(this.handle, 4391, m, 2);
          }
          if ((n & 0x2) != 0)
            i++;
        }
      }
    }
    OS.SetWindowLongPtr(this.handle, -4, j);
    return i;
  }

  public TreeColumn getSortColumn()
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

  public TreeItem getTopItem()
  {
    checkWidget();
    int i = OS.SendMessage(this.handle, 4362, 5, 0);
    return i != 0 ? _getItem(i) : null;
  }

  boolean hitTestSelection(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt1 == 0)
      return false;
    TreeItem localTreeItem = _getItem(paramInt1);
    if (localTreeItem == null)
      return false;
    if (!hooks(41))
      return false;
    boolean bool = false;
    int[] arrayOfInt1 = new int[1];
    int[] arrayOfInt2 = new int[1];
    int i = OS.GetDC(this.handle);
    int j = 0;
    int k = OS.SendMessage(this.handle, 49, 0, 0);
    if (k != 0)
      j = OS.SelectObject(i, k);
    int m = localTreeItem.fontHandle(arrayOfInt1[arrayOfInt2[0]]);
    if (m != -1)
      m = OS.SelectObject(i, m);
    Event localEvent = sendMeasureItemEvent(localTreeItem, arrayOfInt1[arrayOfInt2[0]], i);
    if (localEvent.getBounds().contains(paramInt2, paramInt3))
      bool = true;
    if (k != 0)
      OS.SelectObject(i, j);
    OS.ReleaseDC(this.handle, i);
    return bool;
  }

  int imageIndex(Image paramImage, int paramInt)
  {
    if (paramImage == null)
      return -2;
    if (this.imageList == null)
    {
      Rectangle localRectangle = paramImage.getBounds();
      this.imageList = this.display.getImageList(this.style & 0x4000000, localRectangle.width, localRectangle.height);
    }
    int i = this.imageList.indexOf(paramImage);
    if (i == -1)
      i = this.imageList.add(paramImage);
    if ((this.hwndHeader == 0) || (OS.SendMessage(this.hwndHeader, 4623, 0, 0) == paramInt))
    {
      int j = this.imageList.getHandle();
      int k = OS.SendMessage(this.handle, 4360, 0, 0);
      if (k != j)
      {
        OS.SendMessage(this.handle, 4361, 0, j);
        updateScrollBar();
      }
    }
    return i;
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
      if (this.hwndHeader != 0)
        OS.SendMessage(this.hwndHeader, 4616, 0, k);
      updateScrollBar();
      return j;
    }
    int i = this.headerImageList.indexOf(paramImage);
    if (i != -1)
      return i;
    return this.headerImageList.add(paramImage);
  }

  public int indexOf(TreeColumn paramTreeColumn)
  {
    checkWidget();
    if (paramTreeColumn == null)
      error(4);
    if (paramTreeColumn.isDisposed())
      error(5);
    for (int i = 0; i < this.columnCount; i++)
      if (this.columns[i] == paramTreeColumn)
        return i;
    return -1;
  }

  public int indexOf(TreeItem paramTreeItem)
  {
    checkWidget();
    if (paramTreeItem == null)
      error(4);
    if (paramTreeItem.isDisposed())
      error(5);
    int i = OS.SendMessage(this.handle, 4362, 0, 0);
    return i == 0 ? -1 : findIndex(i, paramTreeItem.handle);
  }

  boolean isCustomToolTip()
  {
    return hooks(41);
  }

  boolean isItemSelected(NMTVCUSTOMDRAW paramNMTVCUSTOMDRAW)
  {
    boolean bool = false;
    if (OS.IsWindowEnabled(this.handle))
    {
      TVITEM localTVITEM = new TVITEM();
      localTVITEM.mask = 24;
      localTVITEM.hItem = paramNMTVCUSTOMDRAW.dwItemSpec;
      OS.SendMessage(this.handle, OS.TVM_GETITEM, 0, localTVITEM);
      if ((localTVITEM.state & 0xA) != 0)
      {
        bool = true;
        if (this.handle == OS.GetFocus())
          if (OS.GetTextColor(paramNMTVCUSTOMDRAW.hdc) != OS.GetSysColor(OS.COLOR_HIGHLIGHTTEXT))
            bool = false;
          else if (OS.GetBkColor(paramNMTVCUSTOMDRAW.hdc) != OS.GetSysColor(OS.COLOR_HIGHLIGHT))
            bool = false;
      }
      else if ((paramNMTVCUSTOMDRAW.dwDrawStage == 65538) && (OS.GetTextColor(paramNMTVCUSTOMDRAW.hdc) == OS.GetSysColor(OS.COLOR_HIGHLIGHTTEXT)) && (OS.GetBkColor(paramNMTVCUSTOMDRAW.hdc) == OS.GetSysColor(OS.COLOR_HIGHLIGHT)))
      {
        bool = true;
      }
    }
    return bool;
  }

  void redrawSelection()
  {
    int i;
    Object localObject;
    if ((this.style & 0x4) != 0)
    {
      i = OS.SendMessage(this.handle, 4362, 9, 0);
      if (i != 0)
      {
        localObject = new RECT();
        if (OS.TreeView_GetItemRect(this.handle, i, (RECT)localObject, false))
          OS.InvalidateRect(this.handle, (RECT)localObject, true);
      }
    }
    else
    {
      i = OS.SendMessage(this.handle, 4362, 5, 0);
      if (i != 0)
      {
        localObject = null;
        if (OS.IsWinCE)
        {
          localObject = new TVITEM();
          ((TVITEM)localObject).mask = 8;
        }
        RECT localRECT = new RECT();
        int j = 0;
        int k = OS.SendMessage(this.handle, 4368, 0, 0);
        while ((j <= k) && (i != 0))
        {
          int m = 0;
          if (OS.IsWinCE)
          {
            ((TVITEM)localObject).hItem = i;
            OS.SendMessage(this.handle, OS.TVM_GETITEM, 0, (TVITEM)localObject);
            m = ((TVITEM)localObject).state;
          }
          else
          {
            m = OS.SendMessage(this.handle, 4391, i, 2);
          }
          if (((m & 0x2) != 0) && (OS.TreeView_GetItemRect(this.handle, i, localRECT, false)))
            OS.InvalidateRect(this.handle, localRECT, true);
          i = OS.SendMessage(this.handle, 4362, 6, i);
          j++;
        }
      }
    }
  }

  void register()
  {
    super.register();
    if (this.hwndParent != 0)
      this.display.addControl(this.hwndParent, this);
    if (this.hwndHeader != 0)
      this.display.addControl(this.hwndHeader, this);
  }

  void releaseItem(int paramInt, TVITEM paramTVITEM, boolean paramBoolean)
  {
    if (paramInt == this.hAnchor)
      this.hAnchor = 0;
    if (paramInt == this.hInsert)
      this.hInsert = 0;
    paramTVITEM.hItem = paramInt;
    if ((OS.SendMessage(this.handle, OS.TVM_GETITEM, 0, paramTVITEM) != 0) && (paramTVITEM.lParam != -1))
    {
      if (paramTVITEM.lParam < this.lastID)
        this.lastID = paramTVITEM.lParam;
      if (paramBoolean)
      {
        TreeItem localTreeItem = this.items[paramTVITEM.lParam];
        if (localTreeItem != null)
          localTreeItem.release(false);
      }
      this.items[paramTVITEM.lParam] = null;
    }
  }

  void releaseItems(int paramInt, TVITEM paramTVITEM)
  {
    for (paramInt = OS.SendMessage(this.handle, 4362, 4, paramInt); paramInt != 0; paramInt = OS.SendMessage(this.handle, 4362, 1, paramInt))
    {
      releaseItems(paramInt, paramTVITEM);
      releaseItem(paramInt, paramTVITEM, true);
    }
  }

  void releaseHandle()
  {
    super.releaseHandle();
    this.hwndParent = (this.hwndHeader = 0);
  }

  void releaseChildren(boolean paramBoolean)
  {
    int i;
    Object localObject;
    if (this.items != null)
    {
      for (i = 0; i < this.items.length; i++)
      {
        localObject = this.items[i];
        if ((localObject != null) && (!((TreeItem)localObject).isDisposed()))
          ((TreeItem)localObject).release(false);
      }
      this.items = null;
    }
    if (this.columns != null)
    {
      for (i = 0; i < this.columns.length; i++)
      {
        localObject = this.columns[i];
        if ((localObject != null) && (!((TreeColumn)localObject).isDisposed()))
          ((TreeColumn)localObject).release(false);
      }
      this.columns = null;
    }
    super.releaseChildren(paramBoolean);
  }

  void releaseWidget()
  {
    super.releaseWidget();
    this.customDraw = false;
    if (this.imageList != null)
    {
      OS.SendMessage(this.handle, 4361, 0, 0);
      this.display.releaseImageList(this.imageList);
    }
    if (this.headerImageList != null)
    {
      if (this.hwndHeader != 0)
        OS.SendMessage(this.hwndHeader, 4616, 0, 0);
      this.display.releaseImageList(this.headerImageList);
    }
    this.imageList = (this.headerImageList = null);
    int i = OS.SendMessage(this.handle, 4360, 2, 0);
    OS.SendMessage(this.handle, 4361, 2, 0);
    if (i != 0)
      OS.ImageList_Destroy(i);
    if (this.itemToolTipHandle != 0)
      OS.DestroyWindow(this.itemToolTipHandle);
    if (this.headerToolTipHandle != 0)
      OS.DestroyWindow(this.headerToolTipHandle);
    this.itemToolTipHandle = (this.headerToolTipHandle = 0);
  }

  public void removeAll()
  {
    checkWidget();
    this.hFirstIndexOf = (this.hLastIndexOf = 0);
    this.itemCount = -1;
    for (int i = 0; i < this.items.length; i++)
    {
      TreeItem localTreeItem = this.items[i];
      if ((localTreeItem != null) && (!localTreeItem.isDisposed()))
        localTreeItem.release(false);
    }
    this.ignoreDeselect = (this.ignoreSelect = 1);
    i = (getDrawing()) && (OS.IsWindowVisible(this.handle)) ? 1 : 0;
    if (i != 0)
      OS.DefWindowProc(this.handle, 11, 0, 0);
    this.shrink = (this.ignoreShrink = 1);
    int j = OS.SendMessage(this.handle, 4353, 0, -65536);
    this.ignoreShrink = false;
    if (i != 0)
    {
      OS.DefWindowProc(this.handle, 11, 1, 0);
      OS.InvalidateRect(this.handle, null, true);
    }
    this.ignoreDeselect = (this.ignoreSelect = 0);
    if (j == 0)
      error(15);
    if (this.imageList != null)
    {
      OS.SendMessage(this.handle, 4361, 0, 0);
      this.display.releaseImageList(this.imageList);
    }
    this.imageList = null;
    if ((this.hwndParent == 0) && (!this.linesVisible) && (!hooks(41)) && (!hooks(40)) && (!hooks(42)))
      this.customDraw = false;
    this.hAnchor = (this.hInsert = this.hFirstIndexOf = this.hLastIndexOf = 0);
    this.itemCount = -1;
    this.items = new TreeItem[4];
    this.scrollWidth = 0;
    setScrollWidth();
    updateScrollBar();
  }

  public void removeSelectionListener(SelectionListener paramSelectionListener)
  {
    checkWidget();
    if (paramSelectionListener == null)
      error(4);
    this.eventTable.unhook(13, paramSelectionListener);
    this.eventTable.unhook(14, paramSelectionListener);
  }

  public void removeTreeListener(TreeListener paramTreeListener)
  {
    checkWidget();
    if (paramTreeListener == null)
      error(4);
    if (this.eventTable == null)
      return;
    this.eventTable.unhook(17, paramTreeListener);
    this.eventTable.unhook(18, paramTreeListener);
  }

  void reskinChildren(int paramInt)
  {
    int i;
    Object localObject;
    if (this.items != null)
      for (i = 0; i < this.items.length; i++)
      {
        localObject = this.items[i];
        if (localObject != null)
          ((TreeItem)localObject).reskinChildren(paramInt);
      }
    if (this.columns != null)
      for (i = 0; i < this.columns.length; i++)
      {
        localObject = this.columns[i];
        if (localObject != null)
          ((TreeColumn)localObject).reskinChildren(paramInt);
      }
    super.reskinChildren(paramInt);
  }

  public void setInsertMark(TreeItem paramTreeItem, boolean paramBoolean)
  {
    checkWidget();
    int i = 0;
    if (paramTreeItem != null)
    {
      if (paramTreeItem.isDisposed())
        error(5);
      i = paramTreeItem.handle;
    }
    this.hInsert = i;
    this.insertAfter = (!paramBoolean);
    OS.SendMessage(this.handle, 4378, this.insertAfter ? 1 : 0, this.hInsert);
  }

  public void setItemCount(int paramInt)
  {
    checkWidget();
    paramInt = Math.max(0, paramInt);
    int i = OS.SendMessage(this.handle, 4362, 0, 0);
    setItemCount(paramInt, 0, i);
  }

  void setItemCount(int paramInt1, int paramInt2, int paramInt3)
  {
    int i = 0;
    if (OS.SendMessage(this.handle, 4357, 0, 0) == 0)
    {
      i = (getDrawing()) && (OS.IsWindowVisible(this.handle)) ? 1 : 0;
      if (i != 0)
        OS.DefWindowProc(this.handle, 11, 0, 0);
    }
    for (int j = 0; (paramInt3 != 0) && (j < paramInt1); j++)
      paramInt3 = OS.SendMessage(this.handle, 4362, 1, paramInt3);
    int k = 0;
    TVITEM localTVITEM = new TVITEM();
    localTVITEM.mask = 20;
    if ((i == 0) && ((this.style & 0x10000000) != 0))
      if (OS.IsWinCE)
      {
        localTVITEM.hItem = paramInt2;
        localTVITEM.mask = 8;
        OS.SendMessage(this.handle, OS.TVM_GETITEM, 0, localTVITEM);
        k = (localTVITEM.state & 0x20) != 0 ? 1 : 0;
      }
      else
      {
        int m = OS.SendMessage(this.handle, 4391, paramInt2, 32);
        k = (m & 0x20) != 0 ? 1 : 0;
      }
    while (paramInt3 != 0)
    {
      localTVITEM.hItem = paramInt3;
      OS.SendMessage(this.handle, OS.TVM_GETITEM, 0, localTVITEM);
      paramInt3 = OS.SendMessage(this.handle, 4362, 1, paramInt3);
      Object localObject = localTVITEM.lParam != -1 ? this.items[localTVITEM.lParam] : null;
      if ((localObject != null) && (!localObject.isDisposed()))
      {
        localObject.dispose();
      }
      else
      {
        releaseItem(localTVITEM.hItem, localTVITEM, false);
        destroyItem(null, localTVITEM.hItem);
      }
    }
    int n;
    if ((this.style & 0x10000000) != 0)
    {
      for (n = j; n < paramInt1; n++)
      {
        if (k != 0)
          this.ignoreShrink = true;
        createItem(null, paramInt2, -65534, 0);
        if (k != 0)
          this.ignoreShrink = false;
      }
    }
    else
    {
      this.shrink = true;
      n = Math.max(4, (paramInt1 + 3) / 4 * 4);
      TreeItem[] arrayOfTreeItem = new TreeItem[this.items.length + n];
      System.arraycopy(this.items, 0, arrayOfTreeItem, 0, this.items.length);
      this.items = arrayOfTreeItem;
      for (int i1 = j; i1 < paramInt1; i1++)
        new TreeItem(this, 0, paramInt2, -65534, 0);
    }
    if (i != 0)
    {
      OS.DefWindowProc(this.handle, 11, 1, 0);
      OS.InvalidateRect(this.handle, null, true);
    }
  }

  void setItemHeight(int paramInt)
  {
    checkWidget();
    if (paramInt < -1)
      error(5);
    OS.SendMessage(this.handle, 4379, paramInt, 0);
  }

  public void setLinesVisible(boolean paramBoolean)
  {
    checkWidget();
    if (this.linesVisible == paramBoolean)
      return;
    this.linesVisible = paramBoolean;
    if ((this.hwndParent == 0) && (this.linesVisible))
      this.customDraw = true;
    OS.InvalidateRect(this.handle, null, true);
  }

  int scrolledHandle()
  {
    if (this.hwndHeader == 0)
      return this.handle;
    return (this.columnCount == 0) && (this.scrollWidth == 0) ? this.handle : this.hwndParent;
  }

  void select(int paramInt, TVITEM paramTVITEM)
  {
    while (paramInt != 0)
    {
      paramTVITEM.hItem = paramInt;
      OS.SendMessage(this.handle, OS.TVM_SETITEM, 0, paramTVITEM);
      int i = OS.SendMessage(this.handle, 4362, 4, paramInt);
      select(i, paramTVITEM);
      paramInt = OS.SendMessage(this.handle, 4362, 1, paramInt);
    }
  }

  public void select(TreeItem paramTreeItem)
  {
    checkWidget();
    if (paramTreeItem == null)
      error(4);
    if (paramTreeItem.isDisposed())
      error(5);
    if ((this.style & 0x4) != 0)
    {
      int i = paramTreeItem.handle;
      int j = 0;
      if (OS.IsWinCE)
      {
        localObject = new TVITEM();
        ((TVITEM)localObject).hItem = i;
        ((TVITEM)localObject).mask = 8;
        OS.SendMessage(this.handle, OS.TVM_GETITEM, 0, (TVITEM)localObject);
        j = ((TVITEM)localObject).state;
      }
      else
      {
        j = OS.SendMessage(this.handle, 4391, i, 2);
      }
      if ((j & 0x2) != 0)
        return;
      Object localObject = null;
      int k = OS.GetWindowLong(this.handle, -16);
      if ((k & 0xA000) == 0)
      {
        localObject = new SCROLLINFO();
        ((SCROLLINFO)localObject).cbSize = SCROLLINFO.sizeof;
        ((SCROLLINFO)localObject).fMask = 23;
        OS.GetScrollInfo(this.handle, 0, (SCROLLINFO)localObject);
      }
      SCROLLINFO localSCROLLINFO = new SCROLLINFO();
      localSCROLLINFO.cbSize = SCROLLINFO.sizeof;
      localSCROLLINFO.fMask = 23;
      OS.GetScrollInfo(this.handle, 1, localSCROLLINFO);
      int m = (getDrawing()) && (OS.IsWindowVisible(this.handle)) ? 1 : 0;
      if (m != 0)
      {
        OS.UpdateWindow(this.handle);
        OS.DefWindowProc(this.handle, 11, 0, 0);
      }
      setSelection(paramTreeItem);
      if (localObject != null)
      {
        n = OS.MAKELPARAM(4, ((SCROLLINFO)localObject).nPos);
        OS.SendMessage(this.handle, 276, n, 0);
      }
      if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(6, 0)))
        OS.SetScrollInfo(this.handle, 1, localSCROLLINFO, true);
      int n = OS.MAKELPARAM(4, localSCROLLINFO.nPos);
      OS.SendMessage(this.handle, 277, n, 0);
      if (m != 0)
      {
        OS.DefWindowProc(this.handle, 11, 1, 0);
        OS.InvalidateRect(this.handle, null, true);
        if ((this.style & 0x20000000) == 0)
        {
          int i1 = this.style;
          this.style |= 536870912;
          OS.UpdateWindow(this.handle);
          this.style = i1;
        }
      }
      return;
    }
    TVITEM localTVITEM = new TVITEM();
    localTVITEM.mask = 24;
    localTVITEM.stateMask = 2;
    localTVITEM.state = 2;
    localTVITEM.hItem = paramTreeItem.handle;
    OS.SendMessage(this.handle, OS.TVM_SETITEM, 0, localTVITEM);
  }

  public void selectAll()
  {
    checkWidget();
    if ((this.style & 0x4) != 0)
      return;
    TVITEM localTVITEM = new TVITEM();
    localTVITEM.mask = 24;
    localTVITEM.state = 2;
    localTVITEM.stateMask = 2;
    int i = OS.GetWindowLongPtr(this.handle, -4);
    OS.SetWindowLongPtr(this.handle, -4, TreeProc);
    int j;
    if ((this.style & 0x10000000) != 0)
    {
      j = OS.SendMessage(this.handle, 4362, 0, 0);
      select(j, localTVITEM);
    }
    else
    {
      for (j = 0; j < this.items.length; j++)
      {
        TreeItem localTreeItem = this.items[j];
        if (localTreeItem != null)
        {
          localTVITEM.hItem = localTreeItem.handle;
          OS.SendMessage(this.handle, OS.TVM_SETITEM, 0, localTVITEM);
        }
      }
    }
    OS.SetWindowLongPtr(this.handle, -4, i);
  }

  Event sendEraseItemEvent(TreeItem paramTreeItem, NMTTCUSTOMDRAW paramNMTTCUSTOMDRAW, int paramInt, RECT paramRECT)
  {
    int i = OS.SaveDC(paramNMTTCUSTOMDRAW.hdc);
    RECT localRECT = toolTipInset(paramRECT);
    OS.SetWindowOrgEx(paramNMTTCUSTOMDRAW.hdc, localRECT.left, localRECT.top, null);
    GCData localGCData = new GCData();
    localGCData.device = this.display;
    localGCData.foreground = OS.GetTextColor(paramNMTTCUSTOMDRAW.hdc);
    localGCData.background = OS.GetBkColor(paramNMTTCUSTOMDRAW.hdc);
    localGCData.font = paramTreeItem.getFont(paramInt);
    localGCData.uiState = OS.SendMessage(this.handle, 297, 0, 0);
    GC localGC = GC.win32_new(paramNMTTCUSTOMDRAW.hdc, localGCData);
    Event localEvent = new Event();
    localEvent.item = paramTreeItem;
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

  Event sendMeasureItemEvent(TreeItem paramTreeItem, int paramInt1, int paramInt2)
  {
    RECT localRECT = paramTreeItem.getBounds(paramInt1, true, true, false, false, false, paramInt2);
    int i = OS.SaveDC(paramInt2);
    GCData localGCData = new GCData();
    localGCData.device = this.display;
    localGCData.font = paramTreeItem.getFont(paramInt1);
    GC localGC = GC.win32_new(paramInt2, localGCData);
    Event localEvent = new Event();
    localEvent.item = paramTreeItem;
    localEvent.gc = localGC;
    localEvent.index = paramInt1;
    localEvent.x = localRECT.left;
    localEvent.y = localRECT.top;
    localEvent.width = (localRECT.right - localRECT.left);
    localEvent.height = (localRECT.bottom - localRECT.top);
    sendEvent(41, localEvent);
    localEvent.gc = null;
    localGC.dispose();
    OS.RestoreDC(paramInt2, i);
    if ((isDisposed()) || (paramTreeItem.isDisposed()))
      return null;
    if ((this.hwndHeader != 0) && (this.columnCount == 0) && (localEvent.x + localEvent.width > this.scrollWidth))
      setScrollWidth(this.scrollWidth = localEvent.x + localEvent.width);
    if (localEvent.height > getItemHeight())
      setItemHeight(localEvent.height);
    return localEvent;
  }

  Event sendPaintItemEvent(TreeItem paramTreeItem, NMTTCUSTOMDRAW paramNMTTCUSTOMDRAW, int paramInt, RECT paramRECT)
  {
    int i = OS.SaveDC(paramNMTTCUSTOMDRAW.hdc);
    RECT localRECT = toolTipInset(paramRECT);
    OS.SetWindowOrgEx(paramNMTTCUSTOMDRAW.hdc, localRECT.left, localRECT.top, null);
    GCData localGCData = new GCData();
    localGCData.device = this.display;
    localGCData.font = paramTreeItem.getFont(paramInt);
    localGCData.foreground = OS.GetTextColor(paramNMTTCUSTOMDRAW.hdc);
    localGCData.background = OS.GetBkColor(paramNMTTCUSTOMDRAW.hdc);
    localGCData.uiState = OS.SendMessage(this.handle, 297, 0, 0);
    GC localGC = GC.win32_new(paramNMTTCUSTOMDRAW.hdc, localGCData);
    Event localEvent = new Event();
    localEvent.item = paramTreeItem;
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
    {
      if (OS.SendMessage(this.handle, 4383, 0, 0) == -1)
        OS.SendMessage(this.handle, 4381, 0, -1);
      _setBackgroundPixel(-1);
    }
    else
    {
      Object localObject = findBackgroundControl();
      if (localObject == null)
        localObject = this;
      if (((Control)localObject).backgroundImage == null)
        setBackgroundPixel(((Control)localObject).getBackgroundPixel());
    }
    updateFullSelection();
  }

  void setBackgroundPixel(int paramInt)
  {
    Control localControl = findImageControl();
    if (localControl != null)
    {
      setBackgroundImage(localControl.backgroundImage);
      return;
    }
    if (OS.IsWindowEnabled(this.handle))
      _setBackgroundPixel(paramInt);
    updateFullSelection();
  }

  void setCursor()
  {
    Cursor localCursor = findCursor();
    int i = localCursor == null ? OS.LoadCursor(0, 32512) : localCursor.handle;
    OS.SetCursor(i);
  }

  public void setColumnOrder(int[] paramArrayOfInt)
  {
    checkWidget();
    if (paramArrayOfInt == null)
      error(4);
    if (this.columnCount == 0)
    {
      if (paramArrayOfInt.length != 0)
        error(5);
      return;
    }
    if (paramArrayOfInt.length != this.columnCount)
      error(5);
    int[] arrayOfInt = new int[this.columnCount];
    OS.SendMessage(this.hwndHeader, 4625, this.columnCount, arrayOfInt);
    int i = 0;
    boolean[] arrayOfBoolean = new boolean[this.columnCount];
    int k;
    for (int j = 0; j < paramArrayOfInt.length; j++)
    {
      k = paramArrayOfInt[j];
      if ((k < 0) || (k >= this.columnCount))
        error(6);
      if (arrayOfBoolean[k] != 0)
        error(5);
      arrayOfBoolean[k] = true;
      if (k != arrayOfInt[j])
        i = 1;
    }
    if (i != 0)
    {
      RECT[] arrayOfRECT = new RECT[this.columnCount];
      for (k = 0; k < this.columnCount; k++)
      {
        arrayOfRECT[k] = new RECT();
        OS.SendMessage(this.hwndHeader, 4615, k, arrayOfRECT[k]);
      }
      OS.SendMessage(this.hwndHeader, 4626, paramArrayOfInt.length, paramArrayOfInt);
      OS.InvalidateRect(this.handle, null, true);
      updateImageList();
      TreeColumn[] arrayOfTreeColumn = new TreeColumn[this.columnCount];
      System.arraycopy(this.columns, 0, arrayOfTreeColumn, 0, this.columnCount);
      RECT localRECT = new RECT();
      for (int m = 0; m < this.columnCount; m++)
      {
        TreeColumn localTreeColumn = arrayOfTreeColumn[m];
        if (!localTreeColumn.isDisposed())
        {
          OS.SendMessage(this.hwndHeader, 4615, m, localRECT);
          if (localRECT.left != arrayOfRECT[m].left)
          {
            localTreeColumn.updateToolTip(m);
            localTreeColumn.sendEvent(10);
          }
        }
      }
    }
  }

  void setCheckboxImageList()
  {
    if ((this.style & 0x20) == 0)
      return;
    int i = 5;
    int j = 0;
    if (OS.IsWinCE)
    {
      j |= 0;
    }
    else if ((OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()))
    {
      j |= 32;
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
      j |= 1;
    }
    if ((this.style & 0x4000000) != 0)
      j |= 8192;
    int k = OS.SendMessage(this.handle, 4380, 0, 0);
    int m = k;
    int n = OS.ImageList_Create(m, k, j, i, i);
    int i1 = OS.GetDC(this.handle);
    int i2 = OS.CreateCompatibleDC(i1);
    int i3 = OS.CreateCompatibleBitmap(i1, m * i, k);
    int i4 = OS.SelectObject(i2, i3);
    RECT localRECT = new RECT();
    OS.SetRect(localRECT, 0, 0, m * i, k);
    int i5 = 0;
    if ((OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()))
    {
      Object localObject = findBackgroundControl();
      if (localObject == null)
        localObject = this;
      i5 = ((Control)localObject).getBackgroundPixel();
    }
    else
    {
      i5 = 33554687;
      if ((i5 & 0xFFFFFF) == OS.GetSysColor(OS.COLOR_WINDOW))
        i5 = 33619712;
    }
    int i6 = OS.CreateSolidBrush(i5);
    OS.FillRect(i2, localRECT, i6);
    OS.DeleteObject(i6);
    int i7 = OS.SelectObject(i1, defaultFont());
    TEXTMETRICA localTEXTMETRICA = OS.IsUnicode ? new TEXTMETRICW() : new TEXTMETRICA();
    OS.GetTextMetrics(i1, localTEXTMETRICA);
    OS.SelectObject(i1, i7);
    int i8 = Math.min(localTEXTMETRICA.tmHeight, m);
    int i9 = Math.min(localTEXTMETRICA.tmHeight, k);
    int i10 = (m - i8) / 2;
    int i11 = (k - i9) / 2 + 1;
    OS.SetRect(localRECT, i10 + m, i11, i10 + m + i8, i11 + i9);
    if ((OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()))
    {
      i12 = this.display.hButtonTheme();
      OS.DrawThemeBackground(i12, i2, 3, 1, localRECT, null);
      localRECT.left += m;
      localRECT.right += m;
      OS.DrawThemeBackground(i12, i2, 3, 5, localRECT, null);
      localRECT.left += m;
      localRECT.right += m;
      OS.DrawThemeBackground(i12, i2, 3, 1, localRECT, null);
      localRECT.left += m;
      localRECT.right += m;
      OS.DrawThemeBackground(i12, i2, 3, 9, localRECT, null);
    }
    else
    {
      OS.DrawFrameControl(i2, localRECT, 4, 16384);
      localRECT.left += m;
      localRECT.right += m;
      OS.DrawFrameControl(i2, localRECT, 4, 17408);
      localRECT.left += m;
      localRECT.right += m;
      OS.DrawFrameControl(i2, localRECT, 4, 16640);
      localRECT.left += m;
      localRECT.right += m;
      OS.DrawFrameControl(i2, localRECT, 4, 17664);
    }
    OS.SelectObject(i2, i4);
    OS.DeleteDC(i2);
    OS.ReleaseDC(this.handle, i1);
    if ((OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()))
      OS.ImageList_Add(n, i3, 0);
    else
      OS.ImageList_AddMasked(n, i3, i5);
    OS.DeleteObject(i3);
    int i12 = OS.SendMessage(this.handle, 4360, 2, 0);
    OS.SendMessage(this.handle, 4361, 2, n);
    if (i12 != 0)
      OS.ImageList_Destroy(i12);
  }

  public void setFont(Font paramFont)
  {
    checkWidget();
    super.setFont(paramFont);
    if ((this.style & 0x20) != 0)
      setCheckboxImageList();
  }

  void setForegroundPixel(int paramInt)
  {
    if ((this.explorerTheme) && (paramInt == -1))
      paramInt = defaultForeground();
    OS.SendMessage(this.handle, 4382, 0, paramInt);
  }

  public void setHeaderVisible(boolean paramBoolean)
  {
    checkWidget();
    if (this.hwndHeader == 0)
    {
      if (!paramBoolean)
        return;
      createParent();
    }
    int i = OS.GetWindowLong(this.hwndHeader, -16);
    if (paramBoolean)
    {
      if ((i & 0x8) == 0)
        return;
      i &= -9;
      OS.SetWindowLong(this.hwndHeader, -16, i);
      OS.ShowWindow(this.hwndHeader, 5);
    }
    else
    {
      if ((i & 0x8) != 0)
        return;
      i |= 8;
      OS.SetWindowLong(this.hwndHeader, -16, i);
      OS.ShowWindow(this.hwndHeader, 0);
    }
    setScrollWidth();
    updateHeaderToolTips();
    updateScrollBar();
  }

  public void setRedraw(boolean paramBoolean)
  {
    checkWidget();
    int i = 0;
    if ((paramBoolean) && (this.drawCount == 1))
    {
      int j = OS.SendMessage(this.handle, 4357, 0, 0);
      if (j == 0)
      {
        TVINSERTSTRUCT localTVINSERTSTRUCT = new TVINSERTSTRUCT();
        localTVINSERTSTRUCT.hInsertAfter = -65535;
        i = OS.SendMessage(this.handle, OS.TVM_INSERTITEM, 0, localTVINSERTSTRUCT);
      }
      OS.DefWindowProc(this.handle, 11, 1, 0);
      updateScrollBar();
    }
    super.setRedraw(paramBoolean);
    if ((!paramBoolean) && (this.drawCount == 1))
      OS.DefWindowProc(this.handle, 11, 0, 0);
    if (i != 0)
    {
      this.ignoreShrink = true;
      OS.SendMessage(this.handle, 4353, 0, i);
      this.ignoreShrink = false;
    }
  }

  void setScrollWidth()
  {
    if ((this.hwndHeader == 0) || (this.hwndParent == 0))
      return;
    int i = 0;
    HDITEM localHDITEM = new HDITEM();
    for (int j = 0; j < this.columnCount; j++)
    {
      localHDITEM.mask = 1;
      OS.SendMessage(this.hwndHeader, OS.HDM_GETITEM, j, localHDITEM);
      i += localHDITEM.cxy;
    }
    setScrollWidth(Math.max(this.scrollWidth, i));
  }

  void setScrollWidth(int paramInt)
  {
    if ((this.hwndHeader == 0) || (this.hwndParent == 0))
      return;
    int i = 0;
    RECT localRECT = new RECT();
    SCROLLINFO localSCROLLINFO = new SCROLLINFO();
    localSCROLLINFO.cbSize = SCROLLINFO.sizeof;
    localSCROLLINFO.fMask = 3;
    if ((this.columnCount == 0) && (paramInt == 0))
    {
      OS.GetScrollInfo(this.hwndParent, 0, localSCROLLINFO);
      localSCROLLINFO.nPage = (localSCROLLINFO.nMax + 1);
      OS.SetScrollInfo(this.hwndParent, 0, localSCROLLINFO, true);
      OS.GetScrollInfo(this.hwndParent, 1, localSCROLLINFO);
      localSCROLLINFO.nPage = (localSCROLLINFO.nMax + 1);
      OS.SetScrollInfo(this.hwndParent, 1, localSCROLLINFO, true);
    }
    else if ((this.style & 0x100) != 0)
    {
      OS.GetClientRect(this.hwndParent, localRECT);
      OS.GetScrollInfo(this.hwndParent, 0, localSCROLLINFO);
      localSCROLLINFO.nMax = paramInt;
      localSCROLLINFO.nPage = (localRECT.right - localRECT.left + 1);
      OS.SetScrollInfo(this.hwndParent, 0, localSCROLLINFO, true);
      localSCROLLINFO.fMask = 4;
      OS.GetScrollInfo(this.hwndParent, 0, localSCROLLINFO);
      i = localSCROLLINFO.nPos;
    }
    if (this.horizontalBar != null)
    {
      this.horizontalBar.setIncrement(5);
      this.horizontalBar.setPageIncrement(localSCROLLINFO.nPage);
    }
    OS.GetClientRect(this.hwndParent, localRECT);
    int j = OS.GetProcessHeap();
    HDLAYOUT localHDLAYOUT = new HDLAYOUT();
    localHDLAYOUT.prc = OS.HeapAlloc(j, 8, RECT.sizeof);
    localHDLAYOUT.pwpos = OS.HeapAlloc(j, 8, WINDOWPOS.sizeof);
    OS.MoveMemory(localHDLAYOUT.prc, localRECT, RECT.sizeof);
    OS.SendMessage(this.hwndHeader, 4613, 0, localHDLAYOUT);
    WINDOWPOS localWINDOWPOS = new WINDOWPOS();
    OS.MoveMemory(localWINDOWPOS, localHDLAYOUT.pwpos, WINDOWPOS.sizeof);
    if (localHDLAYOUT.prc != 0)
      OS.HeapFree(j, 0, localHDLAYOUT.prc);
    if (localHDLAYOUT.pwpos != 0)
      OS.HeapFree(j, 0, localHDLAYOUT.pwpos);
    SetWindowPos(this.hwndHeader, 0, localWINDOWPOS.x - i, localWINDOWPOS.y, localWINDOWPOS.cx + i, localWINDOWPOS.cy, 16);
    int k = OS.GetWindowLong(this.handle, -20);
    int m = (k & 0x200) != 0 ? OS.GetSystemMetrics(45) : 0;
    int n = localWINDOWPOS.cx + ((this.columnCount == 0) && (paramInt == 0) ? 0 : OS.GetSystemMetrics(2));
    int i1 = localRECT.bottom - localRECT.top - localWINDOWPOS.cy;
    boolean bool = this.ignoreResize;
    this.ignoreResize = true;
    SetWindowPos(this.handle, 0, localWINDOWPOS.x - i - m, localWINDOWPOS.y + localWINDOWPOS.cy - m, n + i + m * 2, i1 + m * 2, 20);
    this.ignoreResize = bool;
  }

  void setSelection(int paramInt, TVITEM paramTVITEM, TreeItem[] paramArrayOfTreeItem)
  {
    while (paramInt != 0)
    {
      for (int i = 0; i < paramArrayOfTreeItem.length; i++)
      {
        TreeItem localTreeItem = paramArrayOfTreeItem[i];
        if ((localTreeItem != null) && (localTreeItem.handle == paramInt))
          break;
      }
      paramTVITEM.hItem = paramInt;
      OS.SendMessage(this.handle, OS.TVM_GETITEM, 0, paramTVITEM);
      if ((paramTVITEM.state & 0x2) != 0)
      {
        if (i == paramArrayOfTreeItem.length)
        {
          paramTVITEM.state = 0;
          OS.SendMessage(this.handle, OS.TVM_SETITEM, 0, paramTVITEM);
        }
      }
      else if (i != paramArrayOfTreeItem.length)
      {
        paramTVITEM.state = 2;
        OS.SendMessage(this.handle, OS.TVM_SETITEM, 0, paramTVITEM);
      }
      int j = OS.SendMessage(this.handle, 4362, 4, paramInt);
      setSelection(j, paramTVITEM, paramArrayOfTreeItem);
      paramInt = OS.SendMessage(this.handle, 4362, 1, paramInt);
    }
  }

  public void setSelection(TreeItem paramTreeItem)
  {
    checkWidget();
    if (paramTreeItem == null)
      error(4);
    setSelection(new TreeItem[] { paramTreeItem });
  }

  public void setSelection(TreeItem[] paramArrayOfTreeItem)
  {
    checkWidget();
    if (paramArrayOfTreeItem == null)
      error(4);
    int i = paramArrayOfTreeItem.length;
    if ((i == 0) || (((this.style & 0x4) != 0) && (i > 1)))
    {
      deselectAll();
      return;
    }
    TreeItem localTreeItem = paramArrayOfTreeItem[0];
    if (localTreeItem != null)
    {
      if (localTreeItem.isDisposed())
        error(5);
      int j = OS.SendMessage(this.handle, 4362, 9, 0);
      k = this.hAnchor = localTreeItem.handle;
      boolean bool = checkScroll(k);
      if (bool)
      {
        OS.SendMessage(this.handle, 11, 1, 0);
        OS.DefWindowProc(this.handle, 11, 0, 0);
      }
      this.ignoreSelect = true;
      OS.SendMessage(this.handle, 4363, 9, k);
      this.ignoreSelect = false;
      if (OS.SendMessage(this.handle, 4368, 0, 0) == 0)
      {
        OS.SendMessage(this.handle, 4363, 5, k);
        int n = OS.SendMessage(this.handle, 4362, 3, k);
        if (n == 0)
          OS.SendMessage(this.handle, 276, 6, 0);
      }
      if (bool)
      {
        OS.DefWindowProc(this.handle, 11, 1, 0);
        OS.SendMessage(this.handle, 11, 0, 0);
      }
      if (j == k)
      {
        TVITEM localTVITEM2 = new TVITEM();
        localTVITEM2.mask = 24;
        localTVITEM2.state = 2;
        localTVITEM2.stateMask = 2;
        localTVITEM2.hItem = k;
        OS.SendMessage(this.handle, OS.TVM_SETITEM, 0, localTVITEM2);
        showItem(k);
      }
    }
    if ((this.style & 0x4) != 0)
      return;
    TVITEM localTVITEM1 = new TVITEM();
    localTVITEM1.mask = 24;
    localTVITEM1.stateMask = 2;
    int k = OS.GetWindowLongPtr(this.handle, -4);
    OS.SetWindowLongPtr(this.handle, -4, TreeProc);
    int m;
    if ((this.style & 0x10000000) != 0)
    {
      m = OS.SendMessage(this.handle, 4362, 0, 0);
      setSelection(m, localTVITEM1, paramArrayOfTreeItem);
    }
    else
    {
      for (m = 0; m < this.items.length; m++)
      {
        localTreeItem = this.items[m];
        if (localTreeItem != null)
        {
          for (int i1 = 0; i1 < i; i1++)
            if (paramArrayOfTreeItem[i1] == localTreeItem)
              break;
          localTVITEM1.hItem = localTreeItem.handle;
          OS.SendMessage(this.handle, OS.TVM_GETITEM, 0, localTVITEM1);
          if ((localTVITEM1.state & 0x2) != 0)
          {
            if (i1 == i)
            {
              localTVITEM1.state = 0;
              OS.SendMessage(this.handle, OS.TVM_SETITEM, 0, localTVITEM1);
            }
          }
          else if (i1 != i)
          {
            localTVITEM1.state = 2;
            OS.SendMessage(this.handle, OS.TVM_SETITEM, 0, localTVITEM1);
          }
        }
      }
    }
    OS.SetWindowLongPtr(this.handle, -4, k);
  }

  public void setSortColumn(TreeColumn paramTreeColumn)
  {
    checkWidget();
    if ((paramTreeColumn != null) && (paramTreeColumn.isDisposed()))
      error(5);
    if ((this.sortColumn != null) && (!this.sortColumn.isDisposed()))
      this.sortColumn.setSortDirection(0);
    this.sortColumn = paramTreeColumn;
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

  public void setTopItem(TreeItem paramTreeItem)
  {
    checkWidget();
    if (paramTreeItem == null)
      SWT.error(4);
    if (paramTreeItem.isDisposed())
      SWT.error(5);
    int i = paramTreeItem.handle;
    int j = OS.SendMessage(this.handle, 4362, 5, 0);
    if (i == j)
      return;
    boolean bool = checkScroll(i);
    int k = 0;
    if (bool)
    {
      OS.SendMessage(this.handle, 11, 1, 0);
      OS.DefWindowProc(this.handle, 11, 0, 0);
    }
    else
    {
      k = (getDrawing()) && (OS.IsWindowVisible(this.handle)) ? 1 : 0;
      if (k != 0)
        OS.DefWindowProc(this.handle, 11, 0, 0);
    }
    OS.SendMessage(this.handle, 4363, 5, i);
    int m = OS.SendMessage(this.handle, 4362, 3, i);
    if (m == 0)
      OS.SendMessage(this.handle, 276, 6, 0);
    if (bool)
    {
      OS.DefWindowProc(this.handle, 11, 1, 0);
      OS.SendMessage(this.handle, 11, 0, 0);
    }
    else if (k != 0)
    {
      OS.DefWindowProc(this.handle, 11, 1, 0);
      OS.InvalidateRect(this.handle, null, true);
    }
    updateScrollBar();
  }

  void showItem(int paramInt)
  {
    boolean bool1;
    RECT localRECT2;
    Object localObject;
    if (OS.SendMessage(this.handle, 4368, 0, 0) == 0)
    {
      bool1 = checkScroll(paramInt);
      if (bool1)
      {
        OS.SendMessage(this.handle, 11, 1, 0);
        OS.DefWindowProc(this.handle, 11, 0, 0);
      }
      OS.SendMessage(this.handle, 4363, 5, paramInt);
      OS.SendMessage(this.handle, 276, 6, 0);
      if (bool1)
      {
        OS.DefWindowProc(this.handle, 11, 1, 0);
        OS.SendMessage(this.handle, 11, 0, 0);
      }
    }
    else
    {
      bool1 = true;
      localRECT2 = new RECT();
      if (OS.TreeView_GetItemRect(this.handle, paramInt, localRECT2, true))
      {
        forceResize();
        RECT localRECT3 = new RECT();
        OS.GetClientRect(this.handle, localRECT3);
        localObject = new POINT();
        ((POINT)localObject).x = localRECT2.left;
        ((POINT)localObject).y = localRECT2.top;
        if (OS.PtInRect(localRECT3, (POINT)localObject))
        {
          ((POINT)localObject).y = localRECT2.bottom;
          if (OS.PtInRect(localRECT3, (POINT)localObject))
            bool1 = false;
        }
      }
      if (bool1)
      {
        boolean bool2 = checkScroll(paramInt);
        if (bool2)
        {
          OS.SendMessage(this.handle, 11, 1, 0);
          OS.DefWindowProc(this.handle, 11, 0, 0);
        }
        OS.SendMessage(this.handle, 4372, 0, paramInt);
        if (bool2)
        {
          OS.DefWindowProc(this.handle, 11, 1, 0);
          OS.SendMessage(this.handle, 11, 0, 0);
        }
      }
    }
    if (this.hwndParent != 0)
    {
      RECT localRECT1 = new RECT();
      if (OS.TreeView_GetItemRect(this.handle, paramInt, localRECT1, true))
      {
        forceResize();
        localRECT2 = new RECT();
        OS.GetClientRect(this.hwndParent, localRECT2);
        OS.MapWindowPoints(this.hwndParent, this.handle, localRECT2, 2);
        POINT localPOINT = new POINT();
        localPOINT.x = localRECT1.left;
        localPOINT.y = localRECT1.top;
        if (!OS.PtInRect(localRECT2, localPOINT))
        {
          localPOINT.y = localRECT1.bottom;
          if (!OS.PtInRect(localRECT2, localPOINT))
          {
            localObject = new SCROLLINFO();
            ((SCROLLINFO)localObject).cbSize = SCROLLINFO.sizeof;
            ((SCROLLINFO)localObject).fMask = 4;
            ((SCROLLINFO)localObject).nPos = Math.max(0, localPOINT.x - 1);
            OS.SetScrollInfo(this.hwndParent, 0, (SCROLLINFO)localObject, true);
            setScrollWidth();
          }
        }
      }
    }
    updateScrollBar();
  }

  public void showColumn(TreeColumn paramTreeColumn)
  {
    checkWidget();
    if (paramTreeColumn == null)
      error(4);
    if (paramTreeColumn.isDisposed())
      error(5);
    if (paramTreeColumn.parent != this)
      return;
    int i = indexOf(paramTreeColumn);
    if (i == -1)
      return;
    if ((i >= 0) && (i < this.columnCount))
    {
      forceResize();
      RECT localRECT1 = new RECT();
      OS.GetClientRect(this.hwndParent, localRECT1);
      OS.MapWindowPoints(this.hwndParent, this.handle, localRECT1, 2);
      RECT localRECT2 = new RECT();
      OS.SendMessage(this.hwndHeader, 4615, i, localRECT2);
      int j = localRECT2.left < localRECT1.left ? 1 : 0;
      if (j == 0)
      {
        int k = Math.min(localRECT1.right - localRECT1.left, localRECT2.right - localRECT2.left);
        j = localRECT2.left + k > localRECT1.right ? 1 : 0;
      }
      if (j != 0)
      {
        SCROLLINFO localSCROLLINFO = new SCROLLINFO();
        localSCROLLINFO.cbSize = SCROLLINFO.sizeof;
        localSCROLLINFO.fMask = 4;
        localSCROLLINFO.nPos = Math.max(0, localRECT2.left - 1);
        OS.SetScrollInfo(this.hwndParent, 0, localSCROLLINFO, true);
        setScrollWidth();
      }
    }
  }

  public void showItem(TreeItem paramTreeItem)
  {
    checkWidget();
    if (paramTreeItem == null)
      error(4);
    if (paramTreeItem.isDisposed())
      error(5);
    showItem(paramTreeItem.handle);
  }

  public void showSelection()
  {
    checkWidget();
    int i = 0;
    int j;
    TVITEM localTVITEM;
    if ((this.style & 0x4) != 0)
    {
      i = OS.SendMessage(this.handle, 4362, 9, 0);
      if (i == 0)
        return;
      j = 0;
      if (OS.IsWinCE)
      {
        localTVITEM = new TVITEM();
        localTVITEM.hItem = i;
        localTVITEM.mask = 8;
        OS.SendMessage(this.handle, OS.TVM_GETITEM, 0, localTVITEM);
        j = localTVITEM.state;
      }
      else
      {
        j = OS.SendMessage(this.handle, 4391, i, 2);
      }
      if ((j & 0x2) != 0);
    }
    else
    {
      j = OS.GetWindowLongPtr(this.handle, -4);
      OS.SetWindowLongPtr(this.handle, -4, TreeProc);
      localTVITEM = null;
      if (OS.IsWinCE)
      {
        localTVITEM = new TVITEM();
        localTVITEM.mask = 8;
      }
      int k;
      if ((this.style & 0x10000000) != 0)
      {
        k = OS.SendMessage(this.handle, 4362, 0, 0);
        i = getNextSelection(k, localTVITEM);
      }
      else
      {
        for (k = 0; k < this.items.length; k++)
        {
          TreeItem localTreeItem = this.items[k];
          if (localTreeItem != null)
          {
            int m = 0;
            if (OS.IsWinCE)
            {
              localTVITEM.hItem = localTreeItem.handle;
              OS.SendMessage(this.handle, OS.TVM_GETITEM, 0, localTVITEM);
              m = localTVITEM.state;
            }
            else
            {
              m = OS.SendMessage(this.handle, 4391, localTreeItem.handle, 2);
            }
            if ((m & 0x2) != 0)
            {
              i = localTreeItem.handle;
              break;
            }
          }
        }
      }
      OS.SetWindowLongPtr(this.handle, -4, j);
    }
    if (i != 0)
      showItem(i);
  }

  void sort()
  {
    checkWidget();
    if ((this.style & 0x10000000) != 0)
      return;
    sort(-65536, false);
  }

  void sort(int paramInt, boolean paramBoolean)
  {
    int i = OS.SendMessage(this.handle, 4357, 0, 0);
    if ((i == 0) || (i == 1))
      return;
    this.hFirstIndexOf = (this.hLastIndexOf = 0);
    i = -1;
    if ((this.sortDirection == 128) || (this.sortDirection == 0))
    {
      OS.SendMessage(this.handle, 4371, paramBoolean ? 1 : 0, paramInt);
    }
    else
    {
      Callback localCallback = new Callback(this, "CompareFunc", 3);
      int j = localCallback.getAddress();
      TVSORTCB localTVSORTCB = new TVSORTCB();
      localTVSORTCB.hParent = paramInt;
      localTVSORTCB.lpfnCompare = j;
      localTVSORTCB.lParam = (this.sortColumn == null ? 0 : indexOf(this.sortColumn));
      OS.SendMessage(this.handle, 4373, paramBoolean ? 1 : 0, localTVSORTCB);
      localCallback.dispose();
    }
  }

  void subclass()
  {
    super.subclass();
    if (this.hwndHeader != 0)
      OS.SetWindowLongPtr(this.hwndHeader, -4, this.display.windowProc);
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
      OS.SetRect(localRECT, paramRECT.left, paramRECT.top, paramRECT.right, paramRECT.bottom);
      int i = OS.GetWindowLong(this.itemToolTipHandle, -16);
      int j = OS.GetWindowLong(this.itemToolTipHandle, -20);
      OS.AdjustWindowRectEx(localRECT, i, false, j);
    }
    return localRECT;
  }

  String toolTipText(NMTTDISPINFO paramNMTTDISPINFO)
  {
    int i = OS.SendMessage(this.handle, 4377, 0, 0);
    if ((i == paramNMTTDISPINFO.hwndFrom) && (this.toolTipText != null))
      return "";
    int j;
    Object localObject;
    if (this.headerToolTipHandle == paramNMTTDISPINFO.hwndFrom)
    {
      for (j = 0; j < this.columnCount; j++)
      {
        localObject = this.columns[j];
        if (((TreeColumn)localObject).id == paramNMTTDISPINFO.idFrom)
          return ((TreeColumn)localObject).toolTipText;
      }
      return super.toolTipText(paramNMTTDISPINFO);
    }
    if (this.itemToolTipHandle == paramNMTTDISPINFO.hwndFrom)
    {
      if (this.toolTipText != null)
        return "";
      j = OS.GetMessagePos();
      localObject = new POINT();
      OS.POINTSTOPOINT((POINT)localObject, j);
      OS.ScreenToClient(this.handle, (POINT)localObject);
      int[] arrayOfInt = new int[1];
      TreeItem[] arrayOfTreeItem = new TreeItem[1];
      RECT[] arrayOfRECT1 = new RECT[1];
      RECT[] arrayOfRECT2 = new RECT[1];
      if (findCell(((POINT)localObject).x, ((POINT)localObject).y, arrayOfTreeItem, arrayOfInt, arrayOfRECT1, arrayOfRECT2))
      {
        String str = null;
        if (arrayOfInt[0] == 0)
        {
          str = arrayOfTreeItem[0].text;
        }
        else
        {
          String[] arrayOfString = arrayOfTreeItem[0].strings;
          if (arrayOfString != null)
            str = arrayOfString[arrayOfInt[0]];
        }
        if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(6, 0)) && (isCustomToolTip()))
          str = " ";
        if (str != null)
          return str;
      }
    }
    return super.toolTipText(paramNMTTDISPINFO);
  }

  int topHandle()
  {
    return this.hwndParent != 0 ? this.hwndParent : this.handle;
  }

  void updateFullSelection()
  {
    if ((this.style & 0x10000) != 0)
    {
      int i = OS.GetWindowLong(this.handle, -16);
      int j = i;
      if ((j & 0x1000) != 0)
      {
        if (((!OS.IsWindowEnabled(this.handle)) || (findImageControl() != null)) && (!this.explorerTheme))
          j &= -4097;
      }
      else if ((OS.IsWindowEnabled(this.handle)) && (findImageControl() == null) && (!hooks(40)) && (!hooks(42)))
        j |= 4096;
      if (j != i)
      {
        OS.SetWindowLong(this.handle, -16, j);
        OS.InvalidateRect(this.handle, null, true);
      }
    }
  }

  void updateHeaderToolTips()
  {
    if (this.headerToolTipHandle == 0)
      return;
    RECT localRECT = new RECT();
    TOOLINFO localTOOLINFO = new TOOLINFO();
    localTOOLINFO.cbSize = TOOLINFO.sizeof;
    localTOOLINFO.uFlags = 16;
    localTOOLINFO.hwnd = this.hwndHeader;
    localTOOLINFO.lpszText = -1;
    for (int i = 0; i < this.columnCount; i++)
    {
      TreeColumn localTreeColumn = this.columns[i];
      if (OS.SendMessage(this.hwndHeader, 4615, i, localRECT) != 0)
      {
        localTOOLINFO.uId = (localTreeColumn.id = this.display.nextToolTipId++);
        localTOOLINFO.left = localRECT.left;
        localTOOLINFO.top = localRECT.top;
        localTOOLINFO.right = localRECT.right;
        localTOOLINFO.bottom = localRECT.bottom;
        OS.SendMessage(this.headerToolTipHandle, OS.TTM_ADDTOOL, 0, localTOOLINFO);
      }
    }
  }

  void updateImageList()
  {
    if (this.imageList == null)
      return;
    if (this.hwndHeader == 0)
      return;
    int i = 0;
    int j = OS.SendMessage(this.hwndHeader, 4623, 0, 0);
    while (i < this.items.length)
    {
      TreeItem localTreeItem = this.items[i];
      if (localTreeItem != null)
      {
        Image localImage = null;
        if (j == 0)
        {
          localImage = localTreeItem.image;
        }
        else
        {
          Image[] arrayOfImage = localTreeItem.images;
          if (arrayOfImage != null)
            localImage = arrayOfImage[j];
        }
        if (localImage != null)
          break;
      }
      i++;
    }
    int k = i == this.items.length ? 0 : this.imageList.getHandle();
    int m = OS.SendMessage(this.handle, 4360, 0, 0);
    if (k != m)
      OS.SendMessage(this.handle, 4361, 0, k);
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

  void updateScrollBar()
  {
    if ((this.hwndParent != 0) && ((this.columnCount != 0) || (this.scrollWidth != 0)))
    {
      SCROLLINFO localSCROLLINFO = new SCROLLINFO();
      localSCROLLINFO.cbSize = SCROLLINFO.sizeof;
      localSCROLLINFO.fMask = 23;
      int i = OS.SendMessage(this.handle, 4357, 0, 0);
      if (i == 0)
      {
        OS.GetScrollInfo(this.hwndParent, 1, localSCROLLINFO);
        localSCROLLINFO.nPage = (localSCROLLINFO.nMax + 1);
        OS.SetScrollInfo(this.hwndParent, 1, localSCROLLINFO, true);
      }
      else
      {
        OS.GetScrollInfo(this.handle, 1, localSCROLLINFO);
        if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(4, 10)) && (localSCROLLINFO.nPage == 0))
        {
          SCROLLBARINFO localSCROLLBARINFO = new SCROLLBARINFO();
          localSCROLLBARINFO.cbSize = SCROLLBARINFO.sizeof;
          OS.GetScrollBarInfo(this.handle, -5, localSCROLLBARINFO);
          if ((localSCROLLBARINFO.rgstate[0] & 0x8000) != 0)
            localSCROLLINFO.nPage = (localSCROLLINFO.nMax + 1);
        }
        OS.SetScrollInfo(this.hwndParent, 1, localSCROLLINFO, true);
      }
    }
  }

  void unsubclass()
  {
    super.unsubclass();
    if (this.hwndHeader != 0)
      OS.SetWindowLongPtr(this.hwndHeader, -4, HeaderProc);
  }

  int widgetStyle()
  {
    int i = super.widgetStyle() | 0x20 | 0x4 | 0x1 | 0x4000;
    if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(6, 0)) && (OS.IsAppThemed()))
    {
      i |= 512;
      if ((this.style & 0x10000) != 0)
        i |= 4096;
    }
    else if ((this.style & 0x10000) != 0)
    {
      i |= 4096;
    }
    else
    {
      i |= 2;
    }
    if ((this.style & 0x300) == 0)
    {
      i &= -3145729;
      i |= 8192;
    }
    else if ((this.style & 0x100) == 0)
    {
      i &= -1048577;
      i |= 32768;
    }
    return i | 0x10;
  }

  TCHAR windowClass()
  {
    return TreeClass;
  }

  int windowProc()
  {
    return TreeProc;
  }

  int windowProc(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i;
    if ((this.hwndHeader != 0) && (paramInt1 == this.hwndHeader))
    {
      Object localObject1;
      switch (paramInt2)
      {
      case 123:
        localObject1 = wmContextMenu(paramInt1, paramInt3, paramInt4);
        if (localObject1 != null)
          return ((LRESULT)localObject1).value;
        break;
      case 533:
        if ((OS.COMCTL32_MAJOR < 6) && (paramInt4 != 0) && (paramInt4 != this.hwndHeader))
          OS.InvalidateRect(this.hwndHeader, null, true);
        break;
      case 675:
        if (OS.COMCTL32_MAJOR >= 6)
          updateHeaderToolTips();
        updateHeaderToolTips();
        break;
      case 78:
        localObject1 = new NMHDR();
        OS.MoveMemory((NMHDR)localObject1, paramInt4, NMHDR.sizeof);
        switch (((NMHDR)localObject1).code)
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
          i = (short)OS.LOWORD(paramInt4);
          if (i == 1)
          {
            HDHITTESTINFO localHDHITTESTINFO = new HDHITTESTINFO();
            int m = OS.GetMessagePos();
            POINT localPOINT1 = new POINT();
            OS.POINTSTOPOINT(localPOINT1, m);
            OS.ScreenToClient(paramInt1, localPOINT1);
            localHDHITTESTINFO.x = localPOINT1.x;
            localHDHITTESTINFO.y = localPOINT1.y;
            int i1 = OS.SendMessage(this.hwndHeader, 4614, 0, localHDHITTESTINFO);
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
    if ((this.hwndParent != 0) && (paramInt1 == this.hwndParent))
    {
      Object localObject2;
      switch (paramInt2)
      {
      case 3:
        sendEvent(10);
        return 0;
      case 5:
        setScrollWidth();
        if (this.ignoreResize)
          return 0;
        setResizeChildren(false);
        i = callWindowProc(paramInt1, 5, paramInt3, paramInt4);
        sendEvent(11);
        if (isDisposed())
          return 0;
        if (this.layout != null)
        {
          markLayout(false, false);
          updateLayout(false, false);
        }
        setResizeChildren(true);
        updateScrollBar();
        return i;
      case 133:
        localObject2 = wmNCPaint(paramInt1, paramInt3, paramInt4);
        if (localObject2 != null)
          return ((LRESULT)localObject2).value;
        break;
      case 791:
        localObject2 = wmPrint(paramInt1, paramInt3, paramInt4);
        if (localObject2 != null)
          return ((LRESULT)localObject2).value;
        break;
      case 21:
      case 78:
      case 273:
        return OS.SendMessage(this.handle, paramInt2, paramInt3, paramInt4);
      case 276:
        if ((this.horizontalBar != null) && ((paramInt4 == 0) || (paramInt4 == this.hwndParent)))
          wmScroll(this.horizontalBar, true, this.hwndParent, 276, paramInt3, paramInt4);
        setScrollWidth();
        break;
      case 277:
        localObject2 = new SCROLLINFO();
        ((SCROLLINFO)localObject2).cbSize = SCROLLINFO.sizeof;
        ((SCROLLINFO)localObject2).fMask = 23;
        OS.GetScrollInfo(this.hwndParent, 1, (SCROLLINFO)localObject2);
        if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(6, 0)) && (OS.LOWORD(paramInt3) == 5))
          ((SCROLLINFO)localObject2).nPos = ((SCROLLINFO)localObject2).nTrackPos;
        OS.SetScrollInfo(this.handle, 1, (SCROLLINFO)localObject2, true);
        int k = OS.SendMessage(this.handle, 277, paramInt3, paramInt4);
        OS.GetScrollInfo(this.handle, 1, (SCROLLINFO)localObject2);
        OS.SetScrollInfo(this.hwndParent, 1, (SCROLLINFO)localObject2, true);
        return k;
      }
      return callWindowProc(paramInt1, paramInt2, paramInt3, paramInt4);
    }
    if ((paramInt2 == Display.DI_GETDRAGIMAGE) && (((this.style & 0x2) != 0) || (hooks(40)) || (hooks(42))))
    {
      int j = OS.SendMessage(this.handle, 4362, 5, 0);
      TreeItem[] arrayOfTreeItem = new TreeItem[10];
      TVITEM localTVITEM = new TVITEM();
      localTVITEM.mask = 28;
      int n = getSelection(j, localTVITEM, arrayOfTreeItem, 0, 10, false, true);
      if (n == 0)
        return 0;
      POINT localPOINT2 = new POINT();
      OS.POINTSTOPOINT(localPOINT2, OS.GetMessagePos());
      OS.MapWindowPoints(0, this.handle, localPOINT2, 1);
      RECT localRECT1 = new RECT();
      OS.GetClientRect(this.handle, localRECT1);
      RECT localRECT2 = arrayOfTreeItem[0].getBounds(0, true, true, false);
      if ((this.style & 0x10000) != 0)
      {
        i2 = 301;
        localRECT2.left = Math.max(localRECT1.left, localPOINT2.x - i2 / 2);
        if (localRECT1.right > localRECT2.left + i2)
        {
          localRECT2.right = (localRECT2.left + i2);
        }
        else
        {
          localRECT2.right = localRECT1.right;
          localRECT2.left = Math.max(localRECT1.left, localRECT2.right - i2);
        }
      }
      else
      {
        localRECT2.left = Math.max(localRECT2.left, localRECT1.left);
        localRECT2.right = Math.min(localRECT2.right, localRECT1.right);
      }
      int i2 = OS.CreateRectRgn(localRECT2.left, localRECT2.top, localRECT2.right, localRECT2.bottom);
      for (int i3 = 1; i3 < n; i3++)
      {
        if ((localRECT2.bottom - localRECT2.top > 301) || (localRECT2.bottom > localRECT1.bottom))
          break;
        RECT localRECT3 = arrayOfTreeItem[i3].getBounds(0, true, true, false);
        if ((this.style & 0x10000) != 0)
        {
          localRECT3.left = localRECT2.left;
          localRECT3.right = localRECT2.right;
        }
        else
        {
          localRECT3.left = Math.max(localRECT3.left, localRECT1.left);
          localRECT3.right = Math.min(localRECT3.right, localRECT1.right);
        }
        int i5 = OS.CreateRectRgn(localRECT3.left, localRECT3.top, localRECT3.right, localRECT3.bottom);
        OS.CombineRgn(i2, i2, i5, 2);
        OS.DeleteObject(i5);
        localRECT2.bottom = localRECT3.bottom;
      }
      OS.GetRgnBox(i2, localRECT2);
      i3 = OS.GetDC(this.handle);
      int i4 = OS.CreateCompatibleDC(i3);
      BITMAPINFOHEADER localBITMAPINFOHEADER = new BITMAPINFOHEADER();
      localBITMAPINFOHEADER.biSize = BITMAPINFOHEADER.sizeof;
      localBITMAPINFOHEADER.biWidth = (localRECT2.right - localRECT2.left);
      localBITMAPINFOHEADER.biHeight = (-(localRECT2.bottom - localRECT2.top));
      localBITMAPINFOHEADER.biPlanes = 1;
      localBITMAPINFOHEADER.biBitCount = 32;
      localBITMAPINFOHEADER.biCompression = 0;
      byte[] arrayOfByte = new byte[BITMAPINFOHEADER.sizeof];
      OS.MoveMemory(arrayOfByte, localBITMAPINFOHEADER, BITMAPINFOHEADER.sizeof);
      int[] arrayOfInt = new int[1];
      int i6 = OS.CreateDIBSection(0, arrayOfByte, 0, arrayOfInt, 0, 0);
      if (i6 == 0)
        SWT.error(2);
      int i7 = OS.SelectObject(i4, i6);
      int i8 = 253;
      POINT localPOINT3 = new POINT();
      OS.SetWindowOrgEx(i4, localRECT2.left, localRECT2.top, localPOINT3);
      OS.FillRect(i4, localRECT2, findBrush(i8, 0));
      OS.OffsetRgn(i2, -localRECT2.left, -localRECT2.top);
      OS.SelectClipRgn(i4, i2);
      OS.PrintWindow(this.handle, i4, 0);
      OS.SetWindowOrgEx(i4, localPOINT3.x, localPOINT3.y, null);
      OS.SelectObject(i4, i7);
      OS.DeleteDC(i4);
      OS.ReleaseDC(0, i3);
      OS.DeleteObject(i2);
      SHDRAGIMAGE localSHDRAGIMAGE = new SHDRAGIMAGE();
      localSHDRAGIMAGE.hbmpDragImage = i6;
      localSHDRAGIMAGE.crColorKey = i8;
      localSHDRAGIMAGE.sizeDragImage.cx = localBITMAPINFOHEADER.biWidth;
      localSHDRAGIMAGE.sizeDragImage.cy = (-localBITMAPINFOHEADER.biHeight);
      localSHDRAGIMAGE.ptOffset.x = (localPOINT2.x - localRECT2.left);
      localSHDRAGIMAGE.ptOffset.y = (localPOINT2.y - localRECT2.top);
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
      int i = OS.SendMessage(this.handle, 4362, 9, 0);
      if (i != 0)
      {
        this.hAnchor = i;
        OS.SendMessage(this.handle, 4372, 0, i);
        TVITEM localTVITEM = new TVITEM();
        localTVITEM.mask = 28;
        localTVITEM.hItem = i;
        if ((this.style & 0x20) != 0)
        {
          localTVITEM.stateMask = 61440;
          OS.SendMessage(this.handle, OS.TVM_GETITEM, 0, localTVITEM);
          int k = localTVITEM.state >> 12;
          if ((k & 0x1) != 0)
            k++;
          else
            k--;
          localTVITEM.state = (k << 12);
          OS.SendMessage(this.handle, OS.TVM_SETITEM, 0, localTVITEM);
          if (!OS.IsWinCE)
          {
            int m = i;
            if (OS.COMCTL32_MAJOR >= 6)
              m = OS.SendMessage(this.handle, 4395, i, 0);
            OS.NotifyWinEvent(32773, this.handle, -4, m);
          }
        }
        localTVITEM.stateMask = 2;
        OS.SendMessage(this.handle, OS.TVM_GETITEM, 0, localTVITEM);
        if (((this.style & 0x2) != 0) && (OS.GetKeyState(17) < 0))
        {
          if ((localTVITEM.state & 0x2) != 0)
            localTVITEM.state &= -3;
          else
            localTVITEM.state |= 2;
        }
        else
          localTVITEM.state |= 2;
        OS.SendMessage(this.handle, OS.TVM_SETITEM, 0, localTVITEM);
        TreeItem localTreeItem = _getItem(i, localTVITEM.lParam);
        Event localEvent2 = new Event();
        localEvent2.item = localTreeItem;
        sendSelectionEvent(13, localEvent2, false);
        if ((this.style & 0x20) != 0)
        {
          localEvent2 = new Event();
          localEvent2.item = localTreeItem;
          localEvent2.detail = 32;
          sendSelectionEvent(13, localEvent2, false);
        }
      }
      return LRESULT.ZERO;
    case 13:
      Event localEvent1 = new Event();
      int j = OS.SendMessage(this.handle, 4362, 9, 0);
      if (j != 0)
        localEvent1.item = _getItem(j);
      sendSelectionEvent(14, localEvent1, false);
      return LRESULT.ZERO;
    case 27:
      return LRESULT.ZERO;
    }
    return localLRESULT;
  }

  LRESULT WM_ERASEBKGND(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_ERASEBKGND(paramInt1, paramInt2);
    if ((this.style & 0x20000000) != 0)
      return LRESULT.ONE;
    if (findImageControl() != null)
      return LRESULT.ONE;
    return localLRESULT;
  }

  LRESULT WM_GETOBJECT(int paramInt1, int paramInt2)
  {
    if ((((this.style & 0x20) != 0) || (this.hwndParent != 0)) && (this.accessible == null))
      this.accessible = new_Accessible(this);
    return super.WM_GETOBJECT(paramInt1, paramInt2);
  }

  LRESULT WM_HSCROLL(int paramInt1, int paramInt2)
  {
    int i = 0;
    if ((this.style & 0x20000000) != 0)
      i = ((this.style & 0x10000000) == 0) && (!hooks(40)) && (!hooks(42)) ? 0 : 1;
    if (i != 0)
    {
      this.style &= -536870913;
      if (this.explorerTheme)
        OS.SendMessage(this.handle, 4396, 4, 0);
    }
    LRESULT localLRESULT = super.WM_HSCROLL(paramInt1, paramInt2);
    if (i != 0)
    {
      this.style |= 536870912;
      if (this.explorerTheme)
        OS.SendMessage(this.handle, 4396, 4, 4);
    }
    if (localLRESULT != null)
      return localLRESULT;
    return localLRESULT;
  }

  LRESULT WM_KEYDOWN(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_KEYDOWN(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    int j;
    switch (paramInt1)
    {
    case 32:
      return LRESULT.ZERO;
    case 107:
      if ((OS.GetKeyState(17) < 0) && (this.hwndHeader != 0))
      {
        TreeColumn[] arrayOfTreeColumn = new TreeColumn[this.columnCount];
        System.arraycopy(this.columns, 0, arrayOfTreeColumn, 0, this.columnCount);
        for (j = 0; j < this.columnCount; j++)
        {
          TreeColumn localTreeColumn = arrayOfTreeColumn[j];
          if ((!localTreeColumn.isDisposed()) && (localTreeColumn.getResizable()))
            localTreeColumn.pack();
        }
      }
      break;
    case 33:
    case 34:
    case 35:
    case 36:
    case 38:
    case 40:
      OS.SendMessage(this.handle, 295, 3, 0);
      if (this.itemToolTipHandle != 0)
        OS.ShowWindow(this.itemToolTipHandle, 0);
      if ((this.style & 0x4) == 0)
      {
        int k;
        RECT localRECT2;
        int i5;
        if (OS.GetKeyState(16) < 0)
        {
          i = OS.SendMessage(this.handle, 4362, 9, 0);
          if (i != 0)
          {
            if (this.hAnchor == 0)
              this.hAnchor = i;
            this.ignoreSelect = (this.ignoreDeselect = 1);
            j = callWindowProc(this.handle, 256, paramInt1, paramInt2);
            this.ignoreSelect = (this.ignoreDeselect = 0);
            k = OS.SendMessage(this.handle, 4362, 9, 0);
            TVITEM localTVITEM2 = new TVITEM();
            localTVITEM2.mask = 24;
            localTVITEM2.stateMask = 2;
            int n = i;
            localRECT2 = new RECT();
            if (!OS.TreeView_GetItemRect(this.handle, this.hAnchor, localRECT2, false))
            {
              this.hAnchor = i;
              OS.TreeView_GetItemRect(this.handle, this.hAnchor, localRECT2, false);
            }
            RECT localRECT3 = new RECT();
            OS.TreeView_GetItemRect(this.handle, n, localRECT3, false);
            int i4 = localRECT2.top < localRECT3.top ? 7 : 6;
            while (n != this.hAnchor)
            {
              localTVITEM2.hItem = n;
              OS.SendMessage(this.handle, OS.TVM_SETITEM, 0, localTVITEM2);
              n = OS.SendMessage(this.handle, 4362, i4, n);
            }
            i5 = this.hAnchor;
            OS.TreeView_GetItemRect(this.handle, k, localRECT2, false);
            OS.TreeView_GetItemRect(this.handle, i5, localRECT3, false);
            localTVITEM2.state = 2;
            i4 = localRECT2.top < localRECT3.top ? 7 : 6;
            while (i5 != k)
            {
              localTVITEM2.hItem = i5;
              OS.SendMessage(this.handle, OS.TVM_SETITEM, 0, localTVITEM2);
              i5 = OS.SendMessage(this.handle, 4362, i4, i5);
            }
            localTVITEM2.hItem = k;
            OS.SendMessage(this.handle, OS.TVM_SETITEM, 0, localTVITEM2);
            localTVITEM2.mask = 20;
            localTVITEM2.hItem = k;
            OS.SendMessage(this.handle, OS.TVM_GETITEM, 0, localTVITEM2);
            Event localEvent = new Event();
            localEvent.item = _getItem(k, localTVITEM2.lParam);
            sendSelectionEvent(13, localEvent, false);
            return new LRESULT(j);
          }
        }
        if (OS.GetKeyState(17) < 0)
        {
          i = OS.SendMessage(this.handle, 4362, 9, 0);
          if (i != 0)
          {
            TVITEM localTVITEM1 = new TVITEM();
            localTVITEM1.mask = 24;
            localTVITEM1.stateMask = 2;
            localTVITEM1.hItem = i;
            OS.SendMessage(this.handle, OS.TVM_GETITEM, 0, localTVITEM1);
            k = (localTVITEM1.state & 0x2) != 0 ? 1 : 0;
            int m = 0;
            switch (paramInt1)
            {
            case 38:
              m = OS.SendMessage(this.handle, 4362, 7, i);
              break;
            case 40:
              m = OS.SendMessage(this.handle, 4362, 6, i);
              break;
            case 36:
              m = OS.SendMessage(this.handle, 4362, 0, 0);
              break;
            case 33:
              m = OS.SendMessage(this.handle, 4362, 5, 0);
              if (m == i)
              {
                OS.SendMessage(this.handle, 277, 2, 0);
                m = OS.SendMessage(this.handle, 4362, 5, 0);
              }
              break;
            case 34:
              RECT localRECT1 = new RECT();
              localRECT2 = new RECT();
              OS.GetClientRect(this.handle, localRECT2);
              m = OS.SendMessage(this.handle, 4362, 5, 0);
              do
              {
                int i3 = OS.SendMessage(this.handle, 4362, 6, m);
                if ((i3 == 0) || (!OS.TreeView_GetItemRect(this.handle, i3, localRECT1, false)) || (localRECT1.bottom > localRECT2.bottom))
                  break;
                if ((m = i3) == i)
                  OS.SendMessage(this.handle, 277, 3, 0);
              }
              while (m != 0);
              break;
            case 35:
              m = OS.SendMessage(this.handle, 4362, 10, 0);
            case 37:
            case 39:
            }
            if (m != 0)
            {
              OS.SendMessage(this.handle, 4372, 0, m);
              localTVITEM1.hItem = m;
              OS.SendMessage(this.handle, OS.TVM_GETITEM, 0, localTVITEM1);
              int i1 = (localTVITEM1.state & 0x2) != 0 ? 1 : 0;
              int i2 = (i1 == 0) && (getDrawing()) && (OS.IsWindowVisible(this.handle)) ? 1 : 0;
              if (i2 != 0)
              {
                OS.UpdateWindow(this.handle);
                OS.DefWindowProc(this.handle, 11, 0, 0);
              }
              this.hSelect = m;
              this.ignoreSelect = true;
              OS.SendMessage(this.handle, 4363, 9, m);
              this.ignoreSelect = false;
              this.hSelect = 0;
              if (k != 0)
              {
                localTVITEM1.state = 2;
                localTVITEM1.hItem = i;
                OS.SendMessage(this.handle, OS.TVM_SETITEM, 0, localTVITEM1);
              }
              if (i1 == 0)
              {
                localTVITEM1.state = 0;
                localTVITEM1.hItem = m;
                OS.SendMessage(this.handle, OS.TVM_SETITEM, 0, localTVITEM1);
              }
              if (i2 != 0)
              {
                RECT localRECT4 = new RECT();
                RECT localRECT5 = new RECT();
                i5 = (this.style & 0x10000) == 0 ? 1 : 0;
                if ((hooks(40)) || (hooks(42)))
                  i5 = 0;
                if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(6, 0)))
                  i5 = 0;
                OS.TreeView_GetItemRect(this.handle, i, localRECT4, i5);
                OS.TreeView_GetItemRect(this.handle, m, localRECT5, i5);
                OS.DefWindowProc(this.handle, 11, 1, 0);
                OS.InvalidateRect(this.handle, localRECT4, true);
                OS.InvalidateRect(this.handle, localRECT5, true);
                OS.UpdateWindow(this.handle);
              }
              return LRESULT.ZERO;
            }
          }
        }
        int i = callWindowProc(this.handle, 256, paramInt1, paramInt2);
        this.hAnchor = OS.SendMessage(this.handle, 4362, 9, 0);
        return new LRESULT(i);
      }
      break;
    }
    return localLRESULT;
  }

  LRESULT WM_KILLFOCUS(int paramInt1, int paramInt2)
  {
    int i = (this.style & 0x2) != 0 ? 1 : 0;
    if ((i == 0) && (!OS.IsWinCE) && (OS.COMCTL32_MAJOR >= 6) && (this.imageList != null))
    {
      int j = OS.GetWindowLong(this.handle, -16);
      if ((j & 0x1000) == 0)
        i = 1;
    }
    if (i != 0)
      redrawSelection();
    return super.WM_KILLFOCUS(paramInt1, paramInt2);
  }

  LRESULT WM_LBUTTONDBLCLK(int paramInt1, int paramInt2)
  {
    TVHITTESTINFO localTVHITTESTINFO = new TVHITTESTINFO();
    localTVHITTESTINFO.x = OS.GET_X_LPARAM(paramInt2);
    localTVHITTESTINFO.y = OS.GET_Y_LPARAM(paramInt2);
    OS.SendMessage(this.handle, 4369, 0, localTVHITTESTINFO);
    if ((localTVHITTESTINFO.hItem != 0) && ((this.style & 0x20) != 0) && ((localTVHITTESTINFO.flags & 0x40) != 0))
    {
      localObject = this.display;
      ((Display)localObject).captureChanged = false;
      sendMouseEvent(3, 1, this.handle, 513, paramInt1, paramInt2);
      if (!sendMouseEvent(8, 1, this.handle, 515, paramInt1, paramInt2))
      {
        if ((!((Display)localObject).captureChanged) && (!isDisposed()) && (OS.GetCapture() != this.handle))
          OS.SetCapture(this.handle);
        return LRESULT.ZERO;
      }
      if ((!((Display)localObject).captureChanged) && (!isDisposed()) && (OS.GetCapture() != this.handle))
        OS.SetCapture(this.handle);
      OS.SetFocus(this.handle);
      TVITEM localTVITEM = new TVITEM();
      localTVITEM.hItem = localTVHITTESTINFO.hItem;
      localTVITEM.mask = 28;
      localTVITEM.stateMask = 61440;
      OS.SendMessage(this.handle, OS.TVM_GETITEM, 0, localTVITEM);
      int j = localTVITEM.state >> 12;
      if ((j & 0x1) != 0)
        j++;
      else
        j--;
      localTVITEM.state = (j << 12);
      OS.SendMessage(this.handle, OS.TVM_SETITEM, 0, localTVITEM);
      if (!OS.IsWinCE)
      {
        int k = localTVITEM.hItem;
        if (OS.COMCTL32_MAJOR >= 6)
          k = OS.SendMessage(this.handle, 4395, localTVITEM.hItem, 0);
        OS.NotifyWinEvent(32773, this.handle, -4, k);
      }
      Event localEvent2 = new Event();
      localEvent2.item = _getItem(localTVITEM.hItem, localTVITEM.lParam);
      localEvent2.detail = 32;
      sendSelectionEvent(13, localEvent2, false);
      return LRESULT.ZERO;
    }
    Object localObject = super.WM_LBUTTONDBLCLK(paramInt1, paramInt2);
    if (localObject == LRESULT.ZERO)
      return localObject;
    if (localTVHITTESTINFO.hItem != 0)
    {
      int i = 70;
      if ((this.style & 0x10000) != 0)
      {
        i |= 40;
      }
      else if (hooks(41))
      {
        localTVHITTESTINFO.flags &= -7;
        if (hitTestSelection(localTVHITTESTINFO.hItem, localTVHITTESTINFO.x, localTVHITTESTINFO.y))
          localTVHITTESTINFO.flags |= 6;
      }
      if ((localTVHITTESTINFO.flags & i) != 0)
      {
        Event localEvent1 = new Event();
        localEvent1.item = _getItem(localTVHITTESTINFO.hItem);
        sendSelectionEvent(14, localEvent1, false);
      }
    }
    return localObject;
  }

  LRESULT WM_LBUTTONDOWN(int paramInt1, int paramInt2)
  {
    TVHITTESTINFO localTVHITTESTINFO = new TVHITTESTINFO();
    localTVHITTESTINFO.x = OS.GET_X_LPARAM(paramInt2);
    localTVHITTESTINFO.y = OS.GET_Y_LPARAM(paramInt2);
    OS.SendMessage(this.handle, 4369, 0, localTVHITTESTINFO);
    Display localDisplay1;
    int k;
    int m;
    int i2;
    RECT localRECT1;
    if ((localTVHITTESTINFO.hItem == 0) || ((localTVHITTESTINFO.flags & 0x10) != 0))
    {
      localDisplay1 = this.display;
      localDisplay1.captureChanged = false;
      if (!sendMouseEvent(3, 1, this.handle, 513, paramInt1, paramInt2))
      {
        if ((!localDisplay1.captureChanged) && (!isDisposed()) && (OS.GetCapture() != this.handle))
          OS.SetCapture(this.handle);
        return LRESULT.ZERO;
      }
      int i = 0;
      k = 0;
      m = OS.SendMessage(this.handle, 4362, 9, 0);
      int i3;
      if ((localTVHITTESTINFO.hItem != 0) && ((this.style & 0x2) != 0) && (m != 0))
      {
        TVITEM localTVITEM2 = new TVITEM();
        localTVITEM2.mask = 24;
        localTVITEM2.hItem = localTVHITTESTINFO.hItem;
        OS.SendMessage(this.handle, OS.TVM_GETITEM, 0, localTVITEM2);
        if ((localTVITEM2.state & 0x20) != 0)
        {
          i = 1;
          localTVITEM2.stateMask = 2;
          i2 = OS.SendMessage(this.handle, 4362, 6, localTVHITTESTINFO.hItem);
          while (i2 != 0)
          {
            if (i2 == this.hAnchor)
              this.hAnchor = 0;
            localTVITEM2.hItem = i2;
            OS.SendMessage(this.handle, OS.TVM_GETITEM, 0, localTVITEM2);
            if ((localTVITEM2.state & 0x2) != 0)
              k = 1;
            localTVITEM2.state = 0;
            OS.SendMessage(this.handle, OS.TVM_SETITEM, 0, localTVITEM2);
            for (i3 = i2 = OS.SendMessage(this.handle, 4362, 6, i2); (i3 != 0) && (i3 != localTVHITTESTINFO.hItem); i3 = OS.SendMessage(this.handle, 4362, 3, i3));
            if (i3 == 0)
              break;
          }
        }
      }
      this.dragStarted = (this.gestureCompleted = 0);
      if (i != 0)
        this.ignoreDeselect = (this.ignoreSelect = this.lockSelection = 1);
      i1 = callWindowProc(this.handle, 513, paramInt1, paramInt2);
      if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(6, 0)) && (OS.GetFocus() != this.handle))
        OS.SetFocus(this.handle);
      if (i != 0)
        this.ignoreDeselect = (this.ignoreSelect = this.lockSelection = 0);
      i2 = OS.SendMessage(this.handle, 4362, 9, 0);
      if (m != i2)
        this.hAnchor = i2;
      if ((this.dragStarted) && (!localDisplay1.captureChanged) && (!isDisposed()) && (OS.GetCapture() != this.handle))
        OS.SetCapture(this.handle);
      if ((localTVHITTESTINFO.flags & 0x10) != 0)
      {
        i3 = OS.GetWindowLong(this.handle, -16);
        if (((i3 & 0x1000) == 0) && (OS.SendMessage(this.handle, 4360, 0, 0) == 0))
        {
          i5 = OS.SendMessage(this.handle, 4362, 9, 0);
          if (i5 != 0)
          {
            localRECT1 = new RECT();
            if (OS.TreeView_GetItemRect(this.handle, i5, localRECT1, false))
              OS.InvalidateRect(this.handle, localRECT1, true);
          }
        }
      }
      if (k != 0)
      {
        Event localEvent2 = new Event();
        localEvent2.item = _getItem(localTVHITTESTINFO.hItem);
        sendSelectionEvent(13, localEvent2, false);
      }
      return new LRESULT(i1);
    }
    if (((this.style & 0x20) != 0) && ((localTVHITTESTINFO.flags & 0x40) != 0))
    {
      localDisplay1 = this.display;
      localDisplay1.captureChanged = false;
      if (!sendMouseEvent(3, 1, this.handle, 513, paramInt1, paramInt2))
      {
        if ((!localDisplay1.captureChanged) && (!isDisposed()) && (OS.GetCapture() != this.handle))
          OS.SetCapture(this.handle);
        return LRESULT.ZERO;
      }
      if ((!localDisplay1.captureChanged) && (!isDisposed()) && (OS.GetCapture() != this.handle))
        OS.SetCapture(this.handle);
      OS.SetFocus(this.handle);
      TVITEM localTVITEM1 = new TVITEM();
      localTVITEM1.hItem = localTVHITTESTINFO.hItem;
      localTVITEM1.mask = 28;
      localTVITEM1.stateMask = 61440;
      OS.SendMessage(this.handle, OS.TVM_GETITEM, 0, localTVITEM1);
      k = localTVITEM1.state >> 12;
      if ((k & 0x1) != 0)
        k++;
      else
        k--;
      localTVITEM1.state = (k << 12);
      OS.SendMessage(this.handle, OS.TVM_SETITEM, 0, localTVITEM1);
      if (!OS.IsWinCE)
      {
        m = localTVITEM1.hItem;
        if (OS.COMCTL32_MAJOR >= 6)
          m = OS.SendMessage(this.handle, 4395, localTVITEM1.hItem, 0);
        OS.NotifyWinEvent(32773, this.handle, -4, m);
      }
      Event localEvent1 = new Event();
      localEvent1.item = _getItem(localTVITEM1.hItem, localTVITEM1.lParam);
      localEvent1.detail = 32;
      sendSelectionEvent(13, localEvent1, false);
      return LRESULT.ZERO;
    }
    boolean bool1 = false;
    int j = 0;
    if (localTVHITTESTINFO.hItem != 0)
      if ((this.style & 0x10000) != 0)
      {
        k = OS.GetWindowLong(this.handle, -16);
        if ((k & 0x1000) == 0)
          j = 1;
      }
      else if (hooks(41))
      {
        bool1 = hitTestSelection(localTVHITTESTINFO.hItem, localTVHITTESTINFO.x, localTVHITTESTINFO.y);
        if ((bool1) && ((localTVHITTESTINFO.flags & 0x46) == 0))
          j = 1;
      }
    if ((!bool1) && ((this.style & 0x10000) == 0) && ((localTVHITTESTINFO.flags & 0x46) == 0))
    {
      localObject1 = this.display;
      ((Display)localObject1).captureChanged = false;
      if (!sendMouseEvent(3, 1, this.handle, 513, paramInt1, paramInt2))
      {
        if ((!((Display)localObject1).captureChanged) && (!isDisposed()) && (OS.GetCapture() != this.handle))
          OS.SetCapture(this.handle);
        return LRESULT.ZERO;
      }
      n = callWindowProc(this.handle, 513, paramInt1, paramInt2);
      if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(6, 0)) && (OS.GetFocus() != this.handle))
        OS.SetFocus(this.handle);
      if ((!((Display)localObject1).captureChanged) && (!isDisposed()) && (OS.GetCapture() != this.handle))
        OS.SetCapture(this.handle);
      return new LRESULT(n);
    }
    Object localObject1 = new TVITEM();
    ((TVITEM)localObject1).mask = 24;
    ((TVITEM)localObject1).stateMask = 2;
    int n = 0;
    if ((this.style & 0x2) != 0)
    {
      ((TVITEM)localObject1).hItem = localTVHITTESTINFO.hItem;
      OS.SendMessage(this.handle, OS.TVM_GETITEM, 0, (TVITEM)localObject1);
      n = (((TVITEM)localObject1).state & 0x2) != 0 ? 1 : 0;
    }
    int i1 = OS.SendMessage(this.handle, 4362, 9, 0);
    if ((this.style & 0x2) != 0)
    {
      ((TVITEM)localObject1).hItem = i1;
      OS.SendMessage(this.handle, OS.TVM_GETITEM, 0, (TVITEM)localObject1);
      if ((n != 0) || ((paramInt1 & 0x8) != 0))
      {
        i2 = OS.SendMessage(this.handle, 297, 0, 0);
        if ((i2 & 0x1) != 0)
          OS.SendMessage(this.handle, 295, 3, 0);
        OS.UpdateWindow(this.handle);
        OS.DefWindowProc(this.handle, 11, 0, 0);
      }
      else
      {
        deselectAll();
      }
    }
    Display localDisplay2 = this.display;
    localDisplay2.captureChanged = false;
    if (!sendMouseEvent(3, 1, this.handle, 513, paramInt1, paramInt2))
    {
      if ((!localDisplay2.captureChanged) && (!isDisposed()) && (OS.GetCapture() != this.handle))
        OS.SetCapture(this.handle);
      return LRESULT.ZERO;
    }
    this.hSelect = localTVHITTESTINFO.hItem;
    this.dragStarted = (this.gestureCompleted = 0);
    this.ignoreDeselect = (this.ignoreSelect = 1);
    int i4 = callWindowProc(this.handle, 513, paramInt1, paramInt2);
    if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(6, 0)) && (OS.GetFocus() != this.handle))
      OS.SetFocus(this.handle);
    int i5 = OS.SendMessage(this.handle, 4362, 9, 0);
    if (j != 0)
    {
      if ((i1 == 0) || ((i5 == i1) && (localTVHITTESTINFO.hItem != i1)))
      {
        OS.SendMessage(this.handle, 4363, 9, localTVHITTESTINFO.hItem);
        i5 = OS.SendMessage(this.handle, 4362, 9, 0);
      }
      if ((!this.dragStarted) && ((this.state & 0x8000) != 0) && (hooks(29)))
        this.dragStarted = dragDetect(this.handle, localTVHITTESTINFO.x, localTVHITTESTINFO.y, false, null, null);
    }
    this.ignoreDeselect = (this.ignoreSelect = 0);
    this.hSelect = 0;
    if ((this.dragStarted) && (!localDisplay2.captureChanged) && (!isDisposed()) && (OS.GetCapture() != this.handle))
      OS.SetCapture(this.handle);
    if (((this.style & 0x4) != 0) && (i1 == i5))
    {
      ((TVITEM)localObject1).mask = 24;
      ((TVITEM)localObject1).state = 2;
      ((TVITEM)localObject1).stateMask = 2;
      ((TVITEM)localObject1).hItem = i5;
      OS.SendMessage(this.handle, OS.TVM_SETITEM, 0, (TVITEM)localObject1);
    }
    if ((this.style & 0x2) != 0)
    {
      if ((n != 0) || ((paramInt1 & 0x8) != 0))
      {
        if ((i1 == i5) && (i1 == localTVHITTESTINFO.hItem))
        {
          if ((paramInt1 & 0x8) != 0)
          {
            localObject1.state ^= 2;
            if (this.dragStarted)
              ((TVITEM)localObject1).state = 2;
            OS.SendMessage(this.handle, OS.TVM_SETITEM, 0, (TVITEM)localObject1);
          }
        }
        else
        {
          if ((((TVITEM)localObject1).state & 0x2) != 0)
          {
            ((TVITEM)localObject1).state = 2;
            OS.SendMessage(this.handle, OS.TVM_SETITEM, 0, (TVITEM)localObject1);
          }
          if (((paramInt1 & 0x8) != 0) && (!this.dragStarted) && (n != 0))
          {
            ((TVITEM)localObject1).state = 0;
            ((TVITEM)localObject1).hItem = localTVHITTESTINFO.hItem;
            OS.SendMessage(this.handle, OS.TVM_SETITEM, 0, (TVITEM)localObject1);
          }
        }
        localRECT1 = new RECT();
        RECT localRECT2 = new RECT();
        boolean bool2 = (this.style & 0x10000) == 0;
        if ((hooks(40)) || (hooks(42)))
          bool2 = false;
        if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(6, 0)))
          bool2 = false;
        OS.TreeView_GetItemRect(this.handle, i1, localRECT1, bool2);
        OS.TreeView_GetItemRect(this.handle, i5, localRECT2, bool2);
        OS.DefWindowProc(this.handle, 11, 1, 0);
        OS.InvalidateRect(this.handle, localRECT1, true);
        OS.InvalidateRect(this.handle, localRECT2, true);
        OS.UpdateWindow(this.handle);
      }
      if (((paramInt1 & 0x8) == 0) && ((n == 0) || (!this.dragStarted)))
      {
        ((TVITEM)localObject1).state = 0;
        int i6 = OS.GetWindowLongPtr(this.handle, -4);
        OS.SetWindowLongPtr(this.handle, -4, TreeProc);
        int i8;
        Object localObject2;
        if ((this.style & 0x10000000) != 0)
        {
          i8 = OS.SendMessage(this.handle, 4362, 0, 0);
          deselect(i8, (TVITEM)localObject1, i5);
        }
        else
        {
          for (i8 = 0; i8 < this.items.length; i8++)
          {
            localObject2 = this.items[i8];
            if ((localObject2 != null) && (((TreeItem)localObject2).handle != i5))
            {
              ((TVITEM)localObject1).hItem = ((TreeItem)localObject2).handle;
              OS.SendMessage(this.handle, OS.TVM_SETITEM, 0, (TVITEM)localObject1);
            }
          }
        }
        ((TVITEM)localObject1).hItem = i5;
        ((TVITEM)localObject1).state = 2;
        OS.SendMessage(this.handle, OS.TVM_SETITEM, 0, (TVITEM)localObject1);
        OS.SetWindowLongPtr(this.handle, -4, i6);
        if ((paramInt1 & 0x4) != 0)
        {
          RECT localRECT3 = new RECT();
          if (this.hAnchor == 0)
            this.hAnchor = i5;
          if (OS.TreeView_GetItemRect(this.handle, this.hAnchor, localRECT3, false))
          {
            localObject2 = new RECT();
            if (OS.TreeView_GetItemRect(this.handle, i5, (RECT)localObject2, false))
            {
              int i9 = localRECT3.top < ((RECT)localObject2).top ? 6 : 7;
              ((TVITEM)localObject1).state = 2;
              int i10 = ((TVITEM)localObject1).hItem = this.hAnchor;
              OS.SendMessage(this.handle, OS.TVM_SETITEM, 0, (TVITEM)localObject1);
              while (i10 != i5)
              {
                ((TVITEM)localObject1).hItem = i10;
                OS.SendMessage(this.handle, OS.TVM_SETITEM, 0, (TVITEM)localObject1);
                i10 = OS.SendMessage(this.handle, 4362, i9, i10);
              }
            }
          }
        }
      }
    }
    if ((paramInt1 & 0x4) == 0)
      this.hAnchor = i5;
    if (!this.gestureCompleted)
    {
      ((TVITEM)localObject1).hItem = i5;
      ((TVITEM)localObject1).mask = 20;
      OS.SendMessage(this.handle, OS.TVM_GETITEM, 0, (TVITEM)localObject1);
      Event localEvent3 = new Event();
      localEvent3.item = _getItem(((TVITEM)localObject1).hItem, ((TVITEM)localObject1).lParam);
      sendSelectionEvent(13, localEvent3, false);
    }
    this.gestureCompleted = false;
    if (this.dragStarted)
    {
      sendDragEvent(1, OS.GET_X_LPARAM(paramInt2), OS.GET_Y_LPARAM(paramInt2));
    }
    else
    {
      int i7 = OS.GetWindowLong(this.handle, -16);
      if ((i7 & 0x10) == 0)
        sendMouseEvent(4, 1, this.handle, 514, paramInt1, paramInt2);
    }
    this.dragStarted = false;
    return new LRESULT(i4);
  }

  LRESULT WM_MOUSEMOVE(int paramInt1, int paramInt2)
  {
    Display localDisplay = this.display;
    LRESULT localLRESULT = super.WM_MOUSEMOVE(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    if (this.itemToolTipHandle != 0)
    {
      int i = 19;
      if (localDisplay.xMouse)
        i |= 96;
      if ((paramInt1 & i) == 0)
      {
        int j = OS.GET_X_LPARAM(paramInt2);
        int k = OS.GET_Y_LPARAM(paramInt2);
        int[] arrayOfInt = new int[1];
        TreeItem[] arrayOfTreeItem = new TreeItem[1];
        RECT[] arrayOfRECT1 = new RECT[1];
        RECT[] arrayOfRECT2 = new RECT[1];
        if (findCell(j, k, arrayOfTreeItem, arrayOfInt, arrayOfRECT1, arrayOfRECT2))
        {
          if ((OS.SendMessage(this.itemToolTipHandle, OS.TTM_GETCURRENTTOOL, 0, 0) == 0) && (OS.IsWindowVisible(this.itemToolTipHandle)))
            OS.ShowWindow(this.itemToolTipHandle, 0);
          TOOLINFO localTOOLINFO = new TOOLINFO();
          localTOOLINFO.cbSize = TOOLINFO.sizeof;
          localTOOLINFO.hwnd = this.handle;
          localTOOLINFO.uId = this.handle;
          localTOOLINFO.uFlags = 272;
          localTOOLINFO.left = arrayOfRECT1[0].left;
          localTOOLINFO.top = arrayOfRECT1[0].top;
          localTOOLINFO.right = arrayOfRECT1[0].right;
          localTOOLINFO.bottom = arrayOfRECT1[0].bottom;
          OS.SendMessage(this.itemToolTipHandle, OS.TTM_NEWTOOLRECT, 0, localTOOLINFO);
        }
      }
    }
    return localLRESULT;
  }

  LRESULT WM_MOUSEWHEEL(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_MOUSEWHEEL(paramInt1, paramInt2);
    if (this.itemToolTipHandle != 0)
      OS.ShowWindow(this.itemToolTipHandle, 0);
    return localLRESULT;
  }

  LRESULT WM_MOVE(int paramInt1, int paramInt2)
  {
    if (this.itemToolTipHandle != 0)
      OS.ShowWindow(this.itemToolTipHandle, 0);
    if (this.ignoreResize)
      return null;
    return super.WM_MOVE(paramInt1, paramInt2);
  }

  LRESULT WM_RBUTTONDOWN(int paramInt1, int paramInt2)
  {
    Display localDisplay = this.display;
    localDisplay.captureChanged = false;
    if (!sendMouseEvent(3, 3, this.handle, 516, paramInt1, paramInt2))
    {
      if ((!localDisplay.captureChanged) && (!isDisposed()) && (OS.GetCapture() != this.handle))
        OS.SetCapture(this.handle);
      return LRESULT.ZERO;
    }
    if (OS.GetFocus() != this.handle)
      OS.SetFocus(this.handle);
    TVHITTESTINFO localTVHITTESTINFO = new TVHITTESTINFO();
    localTVHITTESTINFO.x = OS.GET_X_LPARAM(paramInt2);
    localTVHITTESTINFO.y = OS.GET_Y_LPARAM(paramInt2);
    OS.SendMessage(this.handle, 4369, 0, localTVHITTESTINFO);
    if (localTVHITTESTINFO.hItem != 0)
    {
      boolean bool = (this.style & 0x10000) != 0;
      if (!bool)
        if (hooks(41))
        {
          bool = hitTestSelection(localTVHITTESTINFO.hItem, localTVHITTESTINFO.x, localTVHITTESTINFO.y);
        }
        else
        {
          int i = 6;
          bool = (localTVHITTESTINFO.flags & i) != 0;
        }
      if ((bool) && ((paramInt1 & 0xC) == 0))
      {
        TVITEM localTVITEM = new TVITEM();
        localTVITEM.mask = 24;
        localTVITEM.stateMask = 2;
        localTVITEM.hItem = localTVHITTESTINFO.hItem;
        OS.SendMessage(this.handle, OS.TVM_GETITEM, 0, localTVITEM);
        if ((localTVITEM.state & 0x2) == 0)
        {
          this.ignoreSelect = true;
          OS.SendMessage(this.handle, 4363, 9, 0);
          this.ignoreSelect = false;
          OS.SendMessage(this.handle, 4363, 9, localTVHITTESTINFO.hItem);
        }
      }
    }
    return LRESULT.ZERO;
  }

  LRESULT WM_PAINT(int paramInt1, int paramInt2)
  {
    int i;
    int j;
    if ((this.shrink) && (!this.ignoreShrink))
    {
      for (i = this.items.length - 1; i >= 0; i--)
        if (this.items[i] != null)
          break;
      i++;
      if ((this.items.length > 4) && (this.items.length - i > 3))
      {
        j = Math.max(4, (i + 3) / 4 * 4);
        TreeItem[] arrayOfTreeItem = new TreeItem[j];
        System.arraycopy(this.items, 0, arrayOfTreeItem, 0, i);
        this.items = arrayOfTreeItem;
      }
      this.shrink = false;
    }
    if (((this.style & 0x20000000) != 0) || (findImageControl() != null))
    {
      i = 1;
      if (this.explorerTheme)
      {
        j = OS.SendMessage(this.handle, 4397, 0, 0);
        if ((j & 0x4) != 0)
          i = 0;
      }
      if (i != 0)
      {
        GC localGC = null;
        int k = 0;
        PAINTSTRUCT localPAINTSTRUCT = new PAINTSTRUCT();
        int m = (!hooks(9)) && (!filters(9)) ? 0 : 1;
        if (m != 0)
        {
          GCData localGCData = new GCData();
          localGCData.ps = localPAINTSTRUCT;
          localGCData.hwnd = this.handle;
          localGC = GC.win32_new(this, localGCData);
          k = localGC.handle;
        }
        else
        {
          k = OS.BeginPaint(this.handle, localPAINTSTRUCT);
        }
        int n = localPAINTSTRUCT.right - localPAINTSTRUCT.left;
        int i1 = localPAINTSTRUCT.bottom - localPAINTSTRUCT.top;
        if ((n != 0) && (i1 != 0))
        {
          int i2 = OS.CreateCompatibleDC(k);
          POINT localPOINT1 = new POINT();
          POINT localPOINT2 = new POINT();
          OS.SetWindowOrgEx(i2, localPAINTSTRUCT.left, localPAINTSTRUCT.top, localPOINT1);
          OS.SetBrushOrgEx(i2, localPAINTSTRUCT.left, localPAINTSTRUCT.top, localPOINT2);
          int i3 = OS.CreateCompatibleBitmap(k, n, i1);
          int i4 = OS.SelectObject(i2, i3);
          RECT localRECT = new RECT();
          OS.SetRect(localRECT, localPAINTSTRUCT.left, localPAINTSTRUCT.top, localPAINTSTRUCT.right, localPAINTSTRUCT.bottom);
          drawBackground(i2, localRECT);
          callWindowProc(this.handle, 15, i2, 0);
          OS.SetWindowOrgEx(i2, localPOINT1.x, localPOINT1.y, null);
          OS.SetBrushOrgEx(i2, localPOINT2.x, localPOINT2.y, null);
          OS.BitBlt(k, localPAINTSTRUCT.left, localPAINTSTRUCT.top, n, i1, i2, 0, 0, 13369376);
          OS.SelectObject(i2, i4);
          OS.DeleteObject(i3);
          OS.DeleteObject(i2);
          if (m != 0)
          {
            Event localEvent = new Event();
            localEvent.gc = localGC;
            localEvent.x = localPAINTSTRUCT.left;
            localEvent.y = localPAINTSTRUCT.top;
            localEvent.width = (localPAINTSTRUCT.right - localPAINTSTRUCT.left);
            localEvent.height = (localPAINTSTRUCT.bottom - localPAINTSTRUCT.top);
            sendEvent(9, localEvent);
            localEvent.gc = null;
          }
        }
        if (m != 0)
          localGC.dispose();
        else
          OS.EndPaint(this.handle, localPAINTSTRUCT);
        return LRESULT.ZERO;
      }
    }
    return super.WM_PAINT(paramInt1, paramInt2);
  }

  LRESULT WM_PRINTCLIENT(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_PRINTCLIENT(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    this.printClient = true;
    int i = callWindowProc(this.handle, 792, paramInt1, paramInt2);
    this.printClient = false;
    return new LRESULT(i);
  }

  LRESULT WM_SETFOCUS(int paramInt1, int paramInt2)
  {
    int i = (this.style & 0x2) != 0 ? 1 : 0;
    if ((i == 0) && (!OS.IsWinCE) && (OS.COMCTL32_MAJOR >= 6) && (this.imageList != null))
    {
      int j = OS.GetWindowLong(this.handle, -16);
      if ((j & 0x1000) == 0)
        i = 1;
    }
    if (i != 0)
      redrawSelection();
    return super.WM_SETFOCUS(paramInt1, paramInt2);
  }

  LRESULT WM_SETFONT(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_SETFONT(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    if (this.hwndHeader != 0)
    {
      OS.SendMessage(this.hwndHeader, 48, 0, paramInt2);
      OS.SendMessage(this.hwndHeader, 48, paramInt1, paramInt2);
    }
    if (this.itemToolTipHandle != 0)
    {
      OS.ShowWindow(this.itemToolTipHandle, 0);
      OS.SendMessage(this.itemToolTipHandle, 48, paramInt1, paramInt2);
    }
    if (this.headerToolTipHandle != 0)
    {
      OS.SendMessage(this.headerToolTipHandle, 48, paramInt1, paramInt2);
      updateHeaderToolTips();
    }
    return localLRESULT;
  }

  LRESULT WM_SETREDRAW(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_SETREDRAW(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    if (this.itemToolTipHandle != 0)
      OS.ShowWindow(this.itemToolTipHandle, 0);
    if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(6, 0)))
    {
      int i = OS.DefWindowProc(this.handle, 11, paramInt1, paramInt2);
      return i == 0 ? LRESULT.ZERO : new LRESULT(i);
    }
    return localLRESULT;
  }

  LRESULT WM_SIZE(int paramInt1, int paramInt2)
  {
    if (this.itemToolTipHandle != 0)
      OS.ShowWindow(this.itemToolTipHandle, 0);
    int i = OS.GetWindowLong(this.handle, -16);
    if (((i & 0x8000) != 0) && (!OS.IsWinCE))
      OS.ShowScrollBar(this.handle, 0, false);
    if ((this.explorerTheme) && ((this.style & 0x10000) != 0))
      OS.InvalidateRect(this.handle, null, false);
    if (this.ignoreResize)
      return null;
    return super.WM_SIZE(paramInt1, paramInt2);
  }

  LRESULT WM_SYSCOLORCHANGE(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_SYSCOLORCHANGE(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    if ((this.explorerTheme) && (this.foreground == -1))
      setForegroundPixel(-1);
    if ((this.style & 0x20) != 0)
      setCheckboxImageList();
    return localLRESULT;
  }

  LRESULT WM_VSCROLL(int paramInt1, int paramInt2)
  {
    int i = 0;
    if ((this.style & 0x20000000) != 0)
    {
      int j = OS.LOWORD(paramInt1);
      switch (j)
      {
      case 0:
      case 1:
      case 2:
      case 3:
      case 6:
      case 7:
        i = ((this.style & 0x10000000) == 0) && (!hooks(40)) && (!hooks(42)) ? 0 : 1;
      case 4:
      case 5:
      }
    }
    if (i != 0)
    {
      this.style &= -536870913;
      if (this.explorerTheme)
        OS.SendMessage(this.handle, 4396, 4, 0);
    }
    LRESULT localLRESULT = super.WM_VSCROLL(paramInt1, paramInt2);
    if (i != 0)
    {
      this.style |= 536870912;
      if (this.explorerTheme)
        OS.SendMessage(this.handle, 4396, 4, 4);
    }
    if (localLRESULT != null)
      return localLRESULT;
    return localLRESULT;
  }

  LRESULT WM_TIMER(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_TIMER(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    int i = OS.SendMessage(this.handle, 4397, 0, 0);
    if ((i & 0x40) != 0)
      if (!OS.IsWindowVisible(this.handle))
      {
        if (this.lastTimerID == paramInt1)
          this.lastTimerCount += 1;
        else
          this.lastTimerCount = 0;
        this.lastTimerID = paramInt1;
        if (this.lastTimerCount >= 8)
        {
          OS.CallWindowProc(TreeProc, this.handle, 512, 0, 0);
          this.lastTimerID = -1;
          this.lastTimerCount = 0;
        }
      }
      else
      {
        this.lastTimerID = -1;
        this.lastTimerCount = 0;
      }
    return localLRESULT;
  }

  LRESULT wmColorChild(int paramInt1, int paramInt2)
  {
    if (findImageControl() != null)
    {
      if (OS.COMCTL32_MAJOR < 6)
        return super.wmColorChild(paramInt1, paramInt2);
      return new LRESULT(OS.GetStockObject(5));
    }
    return null;
  }

  LRESULT wmNotify(NMHDR paramNMHDR, int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT;
    if (paramNMHDR.hwndFrom == this.itemToolTipHandle)
    {
      localLRESULT = wmNotifyToolTip(paramNMHDR, paramInt1, paramInt2);
      if (localLRESULT != null)
        return localLRESULT;
    }
    if (paramNMHDR.hwndFrom == this.hwndHeader)
    {
      localLRESULT = wmNotifyHeader(paramNMHDR, paramInt1, paramInt2);
      if (localLRESULT != null)
        return localLRESULT;
    }
    return super.wmNotify(paramNMHDR, paramInt1, paramInt2);
  }

  LRESULT wmNotifyChild(NMHDR paramNMHDR, int paramInt1, int paramInt2)
  {
    Object localObject1;
    int k;
    Object localObject3;
    Object localObject5;
    Object localObject2;
    Object localObject4;
    NMTREEVIEW localNMTREEVIEW;
    int j;
    switch (paramNMHDR.code)
    {
    case -452:
    case -403:
      localObject1 = new NMTVDISPINFO();
      OS.MoveMemory((NMTVDISPINFO)localObject1, paramInt2, NMTVDISPINFO.sizeof);
      if ((this.style & 0x10000000) != 0)
      {
        k = 1;
        if ((!this.ignoreShrink) && (this.items != null) && (((NMTVDISPINFO)localObject1).lParam != -1) && (this.items[localObject1.lParam] != null) && (this.items[localObject1.lParam].cached))
          k = 0;
        if (k != 0)
        {
          if ((!getDrawing()) || (!OS.IsWindowVisible(this.handle)))
            break;
          localObject3 = new RECT();
          if (!OS.TreeView_GetItemRect(this.handle, ((NMTVDISPINFO)localObject1).hItem, (RECT)localObject3, false))
            break;
          RECT localRECT = new RECT();
          OS.GetClientRect(this.handle, localRECT);
          if (!OS.IntersectRect(localRECT, localRECT, (RECT)localObject3))
            break;
          if (this.ignoreShrink)
          {
            OS.InvalidateRect(this.handle, localRECT, true);
            break;
          }
        }
      }
      if (this.items != null)
      {
        k = ((NMTVDISPINFO)localObject1).lParam;
        if (((this.style & 0x10000000) != 0) && (k == -1))
        {
          localObject3 = new TVITEM();
          ((TVITEM)localObject3).mask = 20;
          ((TVITEM)localObject3).hItem = ((NMTVDISPINFO)localObject1).hItem;
          OS.SendMessage(this.handle, OS.TVM_GETITEM, 0, (TVITEM)localObject3);
          k = ((TVITEM)localObject3).lParam;
        }
        localObject3 = _getItem(((NMTVDISPINFO)localObject1).hItem, k);
        if ((localObject3 != null) && (!((TreeItem)localObject3).isDisposed()))
        {
          if (!((TreeItem)localObject3).cached)
          {
            if (((this.style & 0x10000000) != 0) && (!checkData((TreeItem)localObject3, false)))
              break;
            if (this.painted)
              ((TreeItem)localObject3).cached = true;
          }
          int n = 0;
          if (this.hwndHeader != 0)
            n = OS.SendMessage(this.hwndHeader, 4623, 0, 0);
          Object localObject6;
          if ((((NMTVDISPINFO)localObject1).mask & 0x1) != 0)
          {
            localObject5 = null;
            if (n == 0)
            {
              localObject5 = ((TreeItem)localObject3).text;
            }
            else
            {
              localObject6 = ((TreeItem)localObject3).strings;
              if (localObject6 != null)
                localObject5 = localObject6[n];
            }
            if (localObject5 != null)
            {
              localObject6 = new TCHAR(getCodePage(), (String)localObject5, false);
              int i2 = Math.min(((TCHAR)localObject6).length(), ((NMTVDISPINFO)localObject1).cchTextMax - 1) * TCHAR.sizeof;
              OS.MoveMemory(((NMTVDISPINFO)localObject1).pszText, (TCHAR)localObject6, i2);
              OS.MoveMemory(((NMTVDISPINFO)localObject1).pszText + i2, new byte[TCHAR.sizeof], TCHAR.sizeof);
              ((NMTVDISPINFO)localObject1).cchTextMax = Math.min(((NMTVDISPINFO)localObject1).cchTextMax, ((String)localObject5).length() + 1);
            }
          }
          if ((((NMTVDISPINFO)localObject1).mask & 0x22) != 0)
          {
            localObject5 = null;
            if (n == 0)
            {
              localObject5 = ((TreeItem)localObject3).image;
            }
            else
            {
              localObject6 = ((TreeItem)localObject3).images;
              if (localObject6 != null)
                localObject5 = localObject6[n];
            }
            ((NMTVDISPINFO)localObject1).iImage = (((NMTVDISPINFO)localObject1).iSelectedImage = -2);
            if (localObject5 != null)
              ((NMTVDISPINFO)localObject1).iImage = (((NMTVDISPINFO)localObject1).iSelectedImage = imageIndex((Image)localObject5, n));
            if ((this.explorerTheme) && (OS.IsWindowEnabled(this.handle)) && (findImageControl() != null))
              ((NMTVDISPINFO)localObject1).iImage = (((NMTVDISPINFO)localObject1).iSelectedImage = -2);
          }
          OS.MoveMemory(paramInt2, (NMTVDISPINFO)localObject1, NMTVDISPINFO.sizeof);
        }
      }
      break;
    case -12:
      if (paramNMHDR.hwndFrom != this.hwndHeader)
      {
        if ((hooks(41)) && (this.hwndHeader == 0))
          createParent();
        if ((this.customDraw) || (findImageControl() != null) || (OS.COMCTL32_MAJOR < 6) || (!OS.IsAppThemed()) || ((this.sortColumn != null) && (this.sortDirection != 0)))
        {
          localObject1 = new NMTVCUSTOMDRAW();
          OS.MoveMemory((NMTVCUSTOMDRAW)localObject1, paramInt2, NMTVCUSTOMDRAW.sizeof);
          switch (((NMTVCUSTOMDRAW)localObject1).dwDrawStage)
          {
          case 1:
            return CDDS_PREPAINT((NMTVCUSTOMDRAW)localObject1, paramInt1, paramInt2);
          case 65537:
            return CDDS_ITEMPREPAINT((NMTVCUSTOMDRAW)localObject1, paramInt1, paramInt2);
          case 65538:
            return CDDS_ITEMPOSTPAINT((NMTVCUSTOMDRAW)localObject1, paramInt1, paramInt2);
          case 2:
            return CDDS_POSTPAINT((NMTVCUSTOMDRAW)localObject1, paramInt1, paramInt2);
          }
        }
      }
      break;
    case -3:
      if (hooks(41))
        return LRESULT.ONE;
      if (hooks(14))
      {
        localObject1 = new POINT();
        k = OS.GetMessagePos();
        OS.POINTSTOPOINT((POINT)localObject1, k);
        OS.ScreenToClient(this.handle, (POINT)localObject1);
        localObject3 = new TVHITTESTINFO();
        ((TVHITTESTINFO)localObject3).x = ((POINT)localObject1).x;
        ((TVHITTESTINFO)localObject3).y = ((POINT)localObject1).y;
        OS.SendMessage(this.handle, 4369, 0, (TVHITTESTINFO)localObject3);
        if ((((TVHITTESTINFO)localObject3).hItem != 0) && ((((TVHITTESTINFO)localObject3).flags & 0x46) != 0))
          return LRESULT.ONE;
      }
      break;
    case -417:
    case -416:
      if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(6, 0)) && ((this.style & 0x2) != 0) && (this.hSelect != 0))
      {
        localObject1 = new NMTVITEMCHANGE();
        OS.MoveMemory((NMTVITEMCHANGE)localObject1, paramInt2, NMTVITEMCHANGE.sizeof);
        if (this.hSelect != ((NMTVITEMCHANGE)localObject1).hItem)
          return LRESULT.ONE;
      }
      break;
    case -450:
    case -401:
      if (((this.style & 0x2) != 0) && (this.lockSelection))
      {
        localObject1 = new NMTREEVIEW();
        OS.MoveMemory((NMTREEVIEW)localObject1, paramInt2, NMTREEVIEW.sizeof);
        localObject2 = ((NMTREEVIEW)localObject1).itemOld;
        this.oldSelected = ((((TVITEM)localObject2).state & 0x2) != 0);
        localObject2 = ((NMTREEVIEW)localObject1).itemNew;
        this.newSelected = ((((TVITEM)localObject2).state & 0x2) != 0);
      }
      if ((!this.ignoreSelect) && (!this.ignoreDeselect))
      {
        this.hAnchor = 0;
        if ((this.style & 0x2) != 0)
          deselectAll();
      }
      break;
    case -451:
    case -402:
      localObject1 = null;
      if (((this.style & 0x2) != 0) && (this.lockSelection))
      {
        if (this.oldSelected)
        {
          localObject1 = new NMTREEVIEW();
          OS.MoveMemory((NMTREEVIEW)localObject1, paramInt2, NMTREEVIEW.sizeof);
          localObject2 = ((NMTREEVIEW)localObject1).itemOld;
          ((TVITEM)localObject2).mask = 8;
          ((TVITEM)localObject2).stateMask = 2;
          ((TVITEM)localObject2).state = 2;
          OS.SendMessage(this.handle, OS.TVM_SETITEM, 0, (TVITEM)localObject2);
        }
        if ((!this.newSelected) && (this.ignoreSelect))
        {
          if (localObject1 == null)
          {
            localObject1 = new NMTREEVIEW();
            OS.MoveMemory((NMTREEVIEW)localObject1, paramInt2, NMTREEVIEW.sizeof);
          }
          localObject2 = ((NMTREEVIEW)localObject1).itemNew;
          ((TVITEM)localObject2).mask = 8;
          ((TVITEM)localObject2).stateMask = 2;
          ((TVITEM)localObject2).state = 0;
          OS.SendMessage(this.handle, OS.TVM_SETITEM, 0, (TVITEM)localObject2);
        }
      }
      if (!this.ignoreSelect)
      {
        if (localObject1 == null)
        {
          localObject1 = new NMTREEVIEW();
          OS.MoveMemory((NMTREEVIEW)localObject1, paramInt2, NMTREEVIEW.sizeof);
        }
        localObject2 = ((NMTREEVIEW)localObject1).itemNew;
        this.hAnchor = ((TVITEM)localObject2).hItem;
        localObject3 = new Event();
        ((Event)localObject3).item = _getItem(((TVITEM)localObject2).hItem, ((TVITEM)localObject2).lParam);
        sendSelectionEvent(13, (Event)localObject3, false);
      }
      updateScrollBar();
      break;
    case -454:
    case -405:
      if (this.itemToolTipHandle != 0)
        OS.ShowWindow(this.itemToolTipHandle, 0);
      int i = 0;
      if ((this.style & 0x10000000) != 0)
        this.style &= -536870913;
      if ((hooks(40)) || (hooks(42)))
        this.style &= -536870913;
      if ((findImageControl() != null) && (getDrawing()) && (OS.IsWindowVisible(this.handle)))
        OS.DefWindowProc(this.handle, 11, 0, 0);
      if (this.hInsert != 0)
        OS.SendMessage(this.handle, 4378, 0, 0);
      if (!this.ignoreExpand)
      {
        localObject2 = new NMTREEVIEW();
        OS.MoveMemory((NMTREEVIEW)localObject2, paramInt2, NMTREEVIEW.sizeof);
        localObject3 = ((NMTREEVIEW)localObject2).itemNew;
        if (this.items != null)
        {
          localObject4 = _getItem(((TVITEM)localObject3).hItem, ((TVITEM)localObject3).lParam);
          if (localObject4 != null)
          {
            localObject5 = new Event();
            ((Event)localObject5).item = ((Widget)localObject4);
            switch (((NMTREEVIEW)localObject2).action)
            {
            case 2:
              if ((((TVITEM)localObject3).state & 0x20) == 0)
              {
                sendEvent(17, (Event)localObject5);
                if (isDisposed())
                  return LRESULT.ZERO;
              }
              break;
            case 1:
              sendEvent(18, (Event)localObject5);
              if (isDisposed())
                return LRESULT.ZERO;
              break;
            }
            int i1 = OS.SendMessage(this.handle, 4362, 4, ((TVITEM)localObject3).hItem);
            i = i1 == 0 ? 1 : 0;
          }
        }
      }
      else
      {
        if (i == 0)
          break;
      }
      break;
    case -455:
    case -406:
      if ((this.style & 0x10000000) != 0)
        this.style |= 536870912;
      if ((hooks(40)) || (hooks(42)))
        this.style |= 536870912;
      if ((findImageControl() != null) && (getDrawing()))
      {
        OS.DefWindowProc(this.handle, 11, 1, 0);
        OS.InvalidateRect(this.handle, null, true);
      }
      if (this.hInsert != 0)
        OS.SendMessage(this.handle, 4378, this.insertAfter ? 1 : 0, this.hInsert);
      if ((!OS.IsWinCE) && (OS.COMCTL32_MAJOR >= 6) && (this.imageList != null))
      {
        localNMTREEVIEW = new NMTREEVIEW();
        OS.MoveMemory(localNMTREEVIEW, paramInt2, NMTREEVIEW.sizeof);
        localObject2 = localNMTREEVIEW.itemNew;
        if (((TVITEM)localObject2).hItem != 0)
        {
          int m = OS.GetWindowLong(this.handle, -16);
          if ((m & 0x1000) == 0)
          {
            localObject4 = new RECT();
            if (OS.TreeView_GetItemRect(this.handle, ((TVITEM)localObject2).hItem, (RECT)localObject4, false))
              OS.InvalidateRect(this.handle, (RECT)localObject4, true);
          }
        }
      }
      updateScrollBar();
      break;
    case -456:
    case -407:
      if (OS.GetKeyState(1) >= 0)
        break;
    case -457:
    case -408:
      this.dragStarted = true;
      localNMTREEVIEW = new NMTREEVIEW();
      OS.MoveMemory(localNMTREEVIEW, paramInt2, NMTREEVIEW.sizeof);
      localObject2 = localNMTREEVIEW.itemNew;
      if ((((TVITEM)localObject2).hItem != 0) && ((((TVITEM)localObject2).state & 0x2) == 0))
      {
        this.hSelect = ((TVITEM)localObject2).hItem;
        this.ignoreSelect = (this.ignoreDeselect = 1);
        OS.SendMessage(this.handle, 4363, 9, ((TVITEM)localObject2).hItem);
        this.ignoreSelect = (this.ignoreDeselect = 0);
        this.hSelect = 0;
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
          localObject2 = new NMRGINFO();
          OS.MoveMemory((NMRGINFO)localObject2, paramInt2, NMRGINFO.sizeof);
          showMenu(((NMRGINFO)localObject2).x, ((NMRGINFO)localObject2).y);
          this.gestureCompleted = true;
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
    NMHEADER localNMHEADER2;
    Object localObject2;
    int m;
    int i4;
    Object localObject3;
    int i2;
    switch (paramNMHDR.code)
    {
    case -326:
    case -325:
    case -306:
    case -305:
      NMHEADER localNMHEADER1 = new NMHEADER();
      OS.MoveMemory(localNMHEADER1, paramInt2, NMHEADER.sizeof);
      localObject1 = this.columns[localNMHEADER1.iItem];
      if ((localObject1 != null) && (!((TreeColumn)localObject1).getResizable()))
        return LRESULT.ONE;
      this.ignoreColumnMove = true;
      switch (paramNMHDR.code)
      {
      case -325:
      case -305:
        if (localObject1 != null)
          ((TreeColumn)localObject1).pack();
        break;
      }
      break;
    case -16:
      if (!this.ignoreColumnMove)
      {
        for (int i = 0; i < this.columnCount; i++)
        {
          localObject1 = this.columns[i];
          ((TreeColumn)localObject1).updateToolTip(i);
        }
        updateImageList();
      }
      this.ignoreColumnMove = false;
      break;
    case -310:
      if (this.ignoreColumnMove)
        return LRESULT.ONE;
      localNMHEADER2 = new NMHEADER();
      OS.MoveMemory(localNMHEADER2, paramInt2, NMHEADER.sizeof);
      if (localNMHEADER2.iItem != -1)
      {
        localObject1 = this.columns[localNMHEADER2.iItem];
        if ((localObject1 != null) && (!((TreeColumn)localObject1).getMoveable()))
        {
          this.ignoreColumnMove = true;
          return LRESULT.ONE;
        }
      }
      break;
    case -311:
      localNMHEADER2 = new NMHEADER();
      OS.MoveMemory(localNMHEADER2, paramInt2, NMHEADER.sizeof);
      if ((localNMHEADER2.iItem != -1) && (localNMHEADER2.pitem != 0))
      {
        localObject1 = new HDITEM();
        OS.MoveMemory((HDITEM)localObject1, localNMHEADER2.pitem, HDITEM.sizeof);
        if (((((HDITEM)localObject1).mask & 0x80) != 0) && (((HDITEM)localObject1).iOrder != -1))
        {
          localObject2 = new int[this.columnCount];
          OS.SendMessage(this.hwndHeader, 4625, this.columnCount, (int[])localObject2);
          for (int k = 0; k < localObject2.length; k++)
            if (localObject2[k] == localNMHEADER2.iItem)
              break;
          if (k == localObject2.length)
            k = 0;
          if (k != ((HDITEM)localObject1).iOrder)
          {
            m = Math.min(k, ((HDITEM)localObject1).iOrder);
            int n = Math.max(k, ((HDITEM)localObject1).iOrder);
            RECT localRECT2 = new RECT();
            RECT localRECT3 = new RECT();
            OS.GetClientRect(this.handle, localRECT2);
            OS.SendMessage(this.hwndHeader, 4615, localObject2[m], localRECT3);
            localRECT2.left = Math.max(localRECT2.left, localRECT3.left);
            OS.SendMessage(this.hwndHeader, 4615, localObject2[n], localRECT3);
            localRECT2.right = Math.min(localRECT2.right, localRECT3.right);
            OS.InvalidateRect(this.handle, localRECT2, true);
            this.ignoreColumnMove = false;
            for (i4 = m; i4 <= n; i4++)
            {
              TreeColumn localTreeColumn2 = this.columns[localObject2[i4]];
              if (!localTreeColumn2.isDisposed())
                localTreeColumn2.postEvent(10);
            }
          }
        }
      }
      break;
    case -320:
    case -300:
      localNMHEADER2 = new NMHEADER();
      OS.MoveMemory(localNMHEADER2, paramInt2, NMHEADER.sizeof);
      if (localNMHEADER2.pitem != 0)
      {
        localObject1 = new HDITEM();
        OS.MoveMemory((HDITEM)localObject1, localNMHEADER2.pitem, HDITEM.sizeof);
        if ((((HDITEM)localObject1).mask & 0x1) != 0)
        {
          localObject2 = new RECT();
          OS.GetClientRect(this.handle, (RECT)localObject2);
          localObject3 = new HDITEM();
          ((HDITEM)localObject3).mask = 1;
          OS.SendMessage(this.hwndHeader, OS.HDM_GETITEM, localNMHEADER2.iItem, (HDITEM)localObject3);
          m = ((HDITEM)localObject1).cxy - ((HDITEM)localObject3).cxy;
          RECT localRECT1 = new RECT();
          OS.SendMessage(this.hwndHeader, 4615, localNMHEADER2.iItem, localRECT1);
          i2 = this.linesVisible ? 1 : 0;
          ((RECT)localObject2).left = (localRECT1.right - i2);
          int i3 = ((RECT)localObject2).left + m;
          ((RECT)localObject2).right = Math.max(((RECT)localObject2).right, ((RECT)localObject2).left + Math.abs(m));
          if ((this.explorerTheme) || (findImageControl() != null) || (hooks(41)) || (hooks(40)) || (hooks(42)))
          {
            localObject2.left -= OS.GetSystemMetrics(83);
            OS.InvalidateRect(this.handle, (RECT)localObject2, true);
            OS.OffsetRect((RECT)localObject2, m, 0);
            OS.InvalidateRect(this.handle, (RECT)localObject2, true);
          }
          else
          {
            i4 = 6;
            OS.ScrollWindowEx(this.handle, m, 0, (RECT)localObject2, null, 0, null, i4);
          }
          if (OS.SendMessage(this.hwndHeader, 4623, localNMHEADER2.iItem, 0) != 0)
          {
            ((RECT)localObject2).left = localRECT1.left;
            ((RECT)localObject2).right = i3;
            OS.InvalidateRect(this.handle, (RECT)localObject2, true);
          }
          setScrollWidth();
        }
      }
      break;
    case -321:
    case -301:
      localNMHEADER2 = new NMHEADER();
      OS.MoveMemory(localNMHEADER2, paramInt2, NMHEADER.sizeof);
      if (localNMHEADER2.pitem != 0)
      {
        localObject1 = new HDITEM();
        OS.MoveMemory((HDITEM)localObject1, localNMHEADER2.pitem, HDITEM.sizeof);
        if ((((HDITEM)localObject1).mask & 0x1) != 0)
        {
          if (this.ignoreColumnMove)
          {
            int j;
            if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(6, 0)))
            {
              j = 384;
              OS.RedrawWindow(this.handle, null, 0, j);
            }
            else if ((this.style & 0x20000000) == 0)
            {
              j = this.style;
              this.style |= 536870912;
              OS.UpdateWindow(this.handle);
              this.style = j;
            }
          }
          TreeColumn localTreeColumn1 = this.columns[localNMHEADER2.iItem];
          if (localTreeColumn1 != null)
          {
            localTreeColumn1.updateToolTip(localNMHEADER2.iItem);
            localTreeColumn1.sendEvent(11);
            if (isDisposed())
              return LRESULT.ZERO;
            localObject3 = new TreeColumn[this.columnCount];
            System.arraycopy(this.columns, 0, localObject3, 0, this.columnCount);
            int[] arrayOfInt = new int[this.columnCount];
            OS.SendMessage(this.hwndHeader, 4625, this.columnCount, arrayOfInt);
            int i1 = 0;
            for (i2 = 0; i2 < this.columnCount; i2++)
            {
              Object localObject4 = localObject3[arrayOfInt[i2]];
              if ((i1 != 0) && (!localObject4.isDisposed()))
              {
                localObject4.updateToolTip(arrayOfInt[i2]);
                localObject4.sendEvent(10);
              }
              if (localObject4 == localTreeColumn1)
                i1 = 1;
            }
          }
        }
        setScrollWidth();
      }
      break;
    case -322:
    case -302:
      localNMHEADER2 = new NMHEADER();
      OS.MoveMemory(localNMHEADER2, paramInt2, NMHEADER.sizeof);
      localObject1 = this.columns[localNMHEADER2.iItem];
      if (localObject1 != null)
        ((TreeColumn)localObject1).sendSelectionEvent(13);
      break;
    case -323:
    case -303:
      localNMHEADER2 = new NMHEADER();
      OS.MoveMemory(localNMHEADER2, paramInt2, NMHEADER.sizeof);
      localObject1 = this.columns[localNMHEADER2.iItem];
      if (localObject1 != null)
        ((TreeColumn)localObject1).sendSelectionEvent(14);
      break;
    }
    return null;
  }

  LRESULT wmNotifyToolTip(NMHDR paramNMHDR, int paramInt1, int paramInt2)
  {
    if (OS.IsWinCE)
      return null;
    Object localObject;
    switch (paramNMHDR.code)
    {
    case -12:
      localObject = new NMTTCUSTOMDRAW();
      OS.MoveMemory((NMTTCUSTOMDRAW)localObject, paramInt2, NMTTCUSTOMDRAW.sizeof);
      return wmNotifyToolTip((NMTTCUSTOMDRAW)localObject, paramInt2);
    case -521:
      localObject = super.wmNotify(paramNMHDR, paramInt1, paramInt2);
      if (localObject != null)
        return localObject;
      int i = OS.GetMessagePos();
      POINT localPOINT = new POINT();
      OS.POINTSTOPOINT(localPOINT, i);
      OS.ScreenToClient(this.handle, localPOINT);
      int[] arrayOfInt = new int[1];
      TreeItem[] arrayOfTreeItem = new TreeItem[1];
      RECT[] arrayOfRECT1 = new RECT[1];
      RECT[] arrayOfRECT2 = new RECT[1];
      if (findCell(localPOINT.x, localPOINT.y, arrayOfTreeItem, arrayOfInt, arrayOfRECT1, arrayOfRECT2))
      {
        RECT localRECT = toolTipRect(arrayOfRECT2[0]);
        OS.MapWindowPoints(this.handle, 0, localRECT, 2);
        int j = localRECT.right - localRECT.left;
        int k = localRECT.bottom - localRECT.top;
        int m = 21;
        if (isCustomToolTip())
          m &= -2;
        SetWindowPos(this.itemToolTipHandle, 0, localRECT.left, localRECT.top, j, k, m);
        return LRESULT.ONE;
      }
      return localObject;
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
      {
        if ((!OS.IsWinCE) && (OS.WIN32_VERSION < OS.VERSION(6, 0)))
          OS.SetTextColor(paramNMTTCUSTOMDRAW.hdc, OS.GetSysColor(OS.COLOR_INFOBK));
        return new LRESULT(18);
      }
      break;
    case 2:
      if ((!OS.IsWinCE) && (OS.WIN32_VERSION < OS.VERSION(6, 0)))
        OS.SetTextColor(paramNMTTCUSTOMDRAW.hdc, OS.GetSysColor(OS.COLOR_INFOTEXT));
      if (OS.SendMessage(this.itemToolTipHandle, OS.TTM_GETCURRENTTOOL, 0, 0) != 0)
      {
        TOOLINFO localTOOLINFO = new TOOLINFO();
        localTOOLINFO.cbSize = TOOLINFO.sizeof;
        if (OS.SendMessage(this.itemToolTipHandle, OS.TTM_GETCURRENTTOOL, 0, localTOOLINFO) != 0)
        {
          int[] arrayOfInt = new int[1];
          TreeItem[] arrayOfTreeItem = new TreeItem[1];
          RECT[] arrayOfRECT1 = new RECT[1];
          RECT[] arrayOfRECT2 = new RECT[1];
          int i = OS.GetMessagePos();
          POINT localPOINT = new POINT();
          OS.POINTSTOPOINT(localPOINT, i);
          OS.ScreenToClient(this.handle, localPOINT);
          if (findCell(localPOINT.x, localPOINT.y, arrayOfTreeItem, arrayOfInt, arrayOfRECT1, arrayOfRECT2))
          {
            int j = OS.GetDC(this.handle);
            int k = arrayOfTreeItem[0].fontHandle(arrayOfInt[0]);
            if (k == -1)
              k = OS.SendMessage(this.handle, 49, 0, 0);
            int m = OS.SelectObject(j, k);
            int n = 1;
            arrayOfRECT1[0] = arrayOfTreeItem[0].getBounds(arrayOfInt[0], true, true, false, false, false, j);
            if (hooks(40))
            {
              Event localEvent = sendEraseItemEvent(arrayOfTreeItem[0], paramNMTTCUSTOMDRAW, arrayOfInt[0], arrayOfRECT1[0]);
              if ((isDisposed()) || (arrayOfTreeItem[0].isDisposed()))
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
              RECT localRECT1 = toolTipInset(arrayOfRECT1[0]);
              OS.SetWindowOrgEx(paramNMTTCUSTOMDRAW.hdc, localRECT1.left, localRECT1.top, null);
              GCData localGCData = new GCData();
              localGCData.device = this.display;
              localGCData.foreground = OS.GetTextColor(paramNMTTCUSTOMDRAW.hdc);
              localGCData.background = OS.GetBkColor(paramNMTTCUSTOMDRAW.hdc);
              localGCData.font = Font.win32_new(this.display, k);
              GC localGC = GC.win32_new(paramNMTTCUSTOMDRAW.hdc, localGCData);
              int i3 = arrayOfRECT1[0].left + 3;
              if (arrayOfInt[0] != 0)
                i3 -= i2;
              Image localImage = arrayOfTreeItem[0].getImage(arrayOfInt[0]);
              Rectangle localRectangle;
              if ((localImage != null) || (arrayOfInt[0] == 0))
              {
                localObject = getImageSize();
                RECT localRECT2 = arrayOfTreeItem[0].getBounds(arrayOfInt[0], false, true, false, false, false, j);
                if (this.imageList == null)
                  ((Point)localObject).x = (localRECT2.right - localRECT2.left);
                if (localImage != null)
                {
                  localRectangle = localImage.getBounds();
                  localGC.drawImage(localImage, localRectangle.x, localRectangle.y, localRectangle.width, localRectangle.height, i3, localRECT2.top, ((Point)localObject).x, ((Point)localObject).y);
                  i3 += 3 + (arrayOfInt[0] == 0 ? 1 : 0);
                }
                i3 += ((Point)localObject).x;
              }
              else
              {
                i3 += 3;
              }
              Object localObject = arrayOfTreeItem[0].getText(arrayOfInt[0]);
              if (localObject != null)
              {
                int i4 = 2084;
                localRectangle = this.columns != null ? this.columns[arrayOfInt[0]] : null;
                if (localRectangle != null)
                {
                  if ((localRectangle.style & 0x1000000) != 0)
                    i4 |= 1;
                  if ((localRectangle.style & 0x20000) != 0)
                    i4 |= 2;
                }
                TCHAR localTCHAR = new TCHAR(getCodePage(), (String)localObject, false);
                RECT localRECT3 = new RECT();
                OS.SetRect(localRECT3, i3, arrayOfRECT1[0].top, arrayOfRECT1[0].right, arrayOfRECT1[0].bottom);
                OS.DrawText(paramNMTTCUSTOMDRAW.hdc, localTCHAR, localTCHAR.length(), localRECT3, i4);
              }
              localGC.dispose();
              OS.RestoreDC(paramNMTTCUSTOMDRAW.hdc, i1);
            }
            if (hooks(42))
            {
              arrayOfRECT2[0] = arrayOfTreeItem[0].getBounds(arrayOfInt[0], true, true, false, false, false, j);
              sendPaintItemEvent(arrayOfTreeItem[0], paramNMTTCUSTOMDRAW, arrayOfInt[0], arrayOfRECT2[0]);
            }
            OS.SelectObject(j, m);
            OS.ReleaseDC(this.handle, j);
          }
        }
      }
      break;
    }
    return null;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.Tree
 * JD-Core Version:    0.6.2
 */