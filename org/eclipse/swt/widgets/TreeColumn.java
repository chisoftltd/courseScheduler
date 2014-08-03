package org.eclipse.swt.widgets;

import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.win32.HDITEM;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.TOOLINFO;
import org.eclipse.swt.internal.win32.TVITEM;

public class TreeColumn extends Item
{
  Tree parent;
  boolean resizable = true;
  boolean moveable;
  String toolTipText;
  int id;

  public TreeColumn(Tree paramTree, int paramInt)
  {
    super(paramTree, checkStyle(paramInt));
    this.parent = paramTree;
    paramTree.createItem(this, paramTree.getColumnCount());
  }

  public TreeColumn(Tree paramTree, int paramInt1, int paramInt2)
  {
    super(paramTree, checkStyle(paramInt1));
    this.parent = paramTree;
    paramTree.createItem(this, paramInt2);
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

  public boolean getMoveable()
  {
    checkWidget();
    return this.moveable;
  }

  String getNameText()
  {
    return getText();
  }

  public Tree getParent()
  {
    checkWidget();
    return this.parent;
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
    int j = this.parent.hwndHeader;
    if (j == 0)
      return 0;
    HDITEM localHDITEM = new HDITEM();
    localHDITEM.mask = 1;
    OS.SendMessage(j, OS.HDM_GETITEM, i, localHDITEM);
    return localHDITEM.cxy;
  }

  public void pack()
  {
    checkWidget();
    int i = this.parent.indexOf(this);
    if (i == -1)
      return;
    int j = 0;
    int k = this.parent.handle;
    int m = this.parent.hwndHeader;
    RECT localRECT1 = new RECT();
    OS.SendMessage(m, 4615, i, localRECT1);
    int n = OS.GetDC(k);
    int i1 = 0;
    int i2 = OS.SendMessage(k, 49, 0, 0);
    if (i2 != 0)
      i1 = OS.SelectObject(n, i2);
    TVITEM localTVITEM = new TVITEM();
    localTVITEM.mask = 20;
    for (localTVITEM.hItem = OS.SendMessage(k, 4362, 0, 0); localTVITEM.hItem != 0; localTVITEM.hItem = OS.SendMessage(k, 4362, 6, localTVITEM.hItem))
    {
      OS.SendMessage(k, OS.TVM_GETITEM, 0, localTVITEM);
      localObject = localTVITEM.lParam != -1 ? this.parent.items[localTVITEM.lParam] : null;
      if (localObject != null)
      {
        i3 = 0;
        if (this.parent.hooks(41))
        {
          Event localEvent = this.parent.sendMeasureItemEvent((TreeItem)localObject, i, n);
          if ((isDisposed()) || (this.parent.isDisposed()))
            break;
          i3 = localEvent.x + localEvent.width;
        }
        else
        {
          int i4 = ((TreeItem)localObject).fontHandle(i);
          if (i4 != -1)
            i4 = OS.SelectObject(n, i4);
          RECT localRECT2 = ((TreeItem)localObject).getBounds(i, true, true, false, false, false, n);
          if (i4 != -1)
            OS.SelectObject(n, i4);
          i3 = localRECT2.right;
        }
        j = Math.max(j, i3 - localRECT1.left);
      }
    }
    Object localObject = new RECT();
    int i3 = 3072;
    TCHAR localTCHAR = new TCHAR(this.parent.getCodePage(), this.text, false);
    OS.DrawText(n, localTCHAR, localTCHAR.length(), (RECT)localObject, i3);
    int i5 = ((RECT)localObject).right - ((RECT)localObject).left + 12;
    if ((OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()))
      i5 += 3;
    if ((this.image != null) || (this.parent.sortColumn == this))
    {
      Image localImage = null;
      if ((this.parent.sortColumn == this) && (this.parent.sortDirection != 0))
      {
        if (OS.COMCTL32_MAJOR < 6)
          localImage = this.display.getSortImage(this.parent.sortDirection);
        else
          i5 += 10;
      }
      else
        localImage = this.image;
      if (localImage != null)
      {
        Rectangle localRectangle = localImage.getBounds();
        i5 += localRectangle.width;
      }
      int i7 = 0;
      if ((m != 0) && (OS.COMCTL32_VERSION >= OS.VERSION(5, 80)))
        i7 = OS.SendMessage(m, 4629, 0, 0);
      else
        i7 = OS.GetSystemMetrics(45) * 3;
      i5 += i7 * 2;
    }
    if (i2 != 0)
      OS.SelectObject(n, i1);
    OS.ReleaseDC(k, n);
    int i6 = this.parent.linesVisible ? 1 : 0;
    setWidth(Math.max(i5, j + i6));
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
    int j = this.parent.hwndHeader;
    if (j == 0)
      return;
    HDITEM localHDITEM = new HDITEM();
    localHDITEM.mask = 4;
    OS.SendMessage(j, OS.HDM_GETITEM, i, localHDITEM);
    localHDITEM.fmt &= -4;
    if ((this.style & 0x4000) == 16384)
      localHDITEM.fmt |= 0;
    if ((this.style & 0x1000000) == 16777216)
      localHDITEM.fmt |= 2;
    if ((this.style & 0x20000) == 131072)
      localHDITEM.fmt |= 1;
    OS.SendMessage(j, OS.HDM_SETITEM, i, localHDITEM);
    if (i != 0)
    {
      int k = this.parent.handle;
      this.parent.forceResize();
      RECT localRECT1 = new RECT();
      RECT localRECT2 = new RECT();
      OS.GetClientRect(k, localRECT1);
      OS.SendMessage(j, 4615, i, localRECT2);
      localRECT1.left = localRECT2.left;
      localRECT1.right = localRECT2.right;
      OS.InvalidateRect(k, localRECT1, true);
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
    int j = this.parent.hwndHeader;
    if (j == 0)
      return;
    HDITEM localHDITEM = new HDITEM();
    localHDITEM.mask = 52;
    OS.SendMessage(j, OS.HDM_GETITEM, i, localHDITEM);
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
      localHDITEM.mask &= -49;
      localHDITEM.fmt &= -10241;
    }
    OS.SendMessage(j, OS.HDM_SETITEM, i, localHDITEM);
  }

  public void setMoveable(boolean paramBoolean)
  {
    checkWidget();
    this.moveable = paramBoolean;
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
      int i = this.parent.hwndHeader;
      if (i != 0)
      {
        int j = this.parent.indexOf(this);
        if (j == -1)
          return;
        HDITEM localHDITEM = new HDITEM();
        localHDITEM.mask = 36;
        OS.SendMessage(i, OS.HDM_GETITEM, j, localHDITEM);
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
        OS.SendMessage(i, OS.HDM_SETITEM, j, localHDITEM);
        if ((OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()))
        {
          int k = this.parent.handle;
          this.parent.forceResize();
          RECT localRECT1 = new RECT();
          RECT localRECT2 = new RECT();
          OS.GetClientRect(k, localRECT1);
          OS.SendMessage(i, 4615, j, localRECT2);
          localRECT1.left = localRECT2.left;
          localRECT1.right = localRECT2.right;
          OS.InvalidateRect(k, localRECT1, true);
        }
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
    int j = OS.GetProcessHeap();
    TCHAR localTCHAR = new TCHAR(this.parent.getCodePage(), fixMnemonic(paramString, true), true);
    int k = localTCHAR.length() * TCHAR.sizeof;
    int m = OS.HeapAlloc(j, 8, k);
    OS.MoveMemory(m, localTCHAR, k);
    int n = this.parent.hwndHeader;
    if (n == 0)
      return;
    HDITEM localHDITEM = new HDITEM();
    localHDITEM.mask = 2;
    localHDITEM.pszText = m;
    int i1 = OS.SendMessage(n, OS.HDM_SETITEM, i, localHDITEM);
    if (m != 0)
      OS.HeapFree(j, 0, m);
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
    int j = this.parent.hwndHeader;
    if (j == 0)
      return;
    HDITEM localHDITEM = new HDITEM();
    localHDITEM.mask = 1;
    localHDITEM.cxy = paramInt;
    OS.SendMessage(j, OS.HDM_SETITEM, i, localHDITEM);
    RECT localRECT1 = new RECT();
    OS.SendMessage(j, 4615, i, localRECT1);
    this.parent.forceResize();
    int k = this.parent.handle;
    RECT localRECT2 = new RECT();
    OS.GetClientRect(k, localRECT2);
    localRECT2.left = localRECT1.left;
    OS.InvalidateRect(k, localRECT2, true);
    this.parent.setScrollWidth();
  }

  void updateToolTip(int paramInt)
  {
    int i = this.parent.headerToolTipHandle;
    if (i != 0)
    {
      int j = this.parent.hwndHeader;
      RECT localRECT = new RECT();
      if (OS.SendMessage(j, 4615, paramInt, localRECT) != 0)
      {
        TOOLINFO localTOOLINFO = new TOOLINFO();
        localTOOLINFO.cbSize = TOOLINFO.sizeof;
        localTOOLINFO.hwnd = j;
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
 * Qualified Name:     org.eclipse.swt.widgets.TreeColumn
 * JD-Core Version:    0.6.2
 */