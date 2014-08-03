package org.eclipse.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.SCROLLINFO;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.TVITEM;

public class TreeItem extends Item
{
  public int handle;
  Tree parent;
  String[] strings;
  Image[] images;
  Font font;
  Font[] cellFont;
  boolean cached;
  int background = -1;
  int foreground = -1;
  int[] cellBackground;
  int[] cellForeground;

  public TreeItem(Tree paramTree, int paramInt)
  {
    this(paramTree, paramInt, 0, -65534, 0);
  }

  public TreeItem(Tree paramTree, int paramInt1, int paramInt2)
  {
    this(paramTree, paramInt1, 0, findPrevious(paramTree, paramInt2), 0);
  }

  public TreeItem(TreeItem paramTreeItem, int paramInt)
  {
    this(checkNull(paramTreeItem).parent, paramInt, paramTreeItem.handle, -65534, 0);
  }

  public TreeItem(TreeItem paramTreeItem, int paramInt1, int paramInt2)
  {
    this(checkNull(paramTreeItem).parent, paramInt1, paramTreeItem.handle, findPrevious(paramTreeItem, paramInt2), 0);
  }

  TreeItem(Tree paramTree, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super(paramTree, paramInt1);
    this.parent = paramTree;
    paramTree.createItem(this, paramInt2, paramInt3, paramInt4);
  }

  static TreeItem checkNull(TreeItem paramTreeItem)
  {
    if (paramTreeItem == null)
      SWT.error(4);
    return paramTreeItem;
  }

  static int findPrevious(Tree paramTree, int paramInt)
  {
    if (paramTree == null)
      return 0;
    if (paramInt < 0)
      SWT.error(6);
    if (paramInt == 0)
      return -65535;
    int i = paramTree.handle;
    int j = OS.SendMessage(i, 4362, 0, 0);
    int k = paramTree.findItem(j, paramInt - 1);
    if (k == 0)
      SWT.error(6);
    return k;
  }

  static int findPrevious(TreeItem paramTreeItem, int paramInt)
  {
    if (paramTreeItem == null)
      return 0;
    if (paramInt < 0)
      SWT.error(6);
    if (paramInt == 0)
      return -65535;
    Tree localTree = paramTreeItem.parent;
    int i = localTree.handle;
    int j = paramTreeItem.handle;
    int k = OS.SendMessage(i, 4362, 4, j);
    int m = localTree.findItem(k, paramInt - 1);
    if (m == 0)
      SWT.error(6);
    return m;
  }

  protected void checkSubclass()
  {
    if (!isValidSubclass())
      error(43);
  }

  void clear()
  {
    this.text = "";
    this.image = null;
    this.strings = null;
    this.images = null;
    if ((this.parent.style & 0x20) != 0)
    {
      int i = this.parent.handle;
      TVITEM localTVITEM = new TVITEM();
      localTVITEM.mask = 24;
      localTVITEM.stateMask = 61440;
      localTVITEM.state = 4096;
      localTVITEM.hItem = this.handle;
      OS.SendMessage(i, OS.TVM_SETITEM, 0, localTVITEM);
    }
    this.background = (this.foreground = -1);
    this.font = null;
    this.cellBackground = (this.cellForeground = null);
    this.cellFont = null;
    if ((this.parent.style & 0x10000000) != 0)
      this.cached = false;
  }

  public void clear(int paramInt, boolean paramBoolean)
  {
    checkWidget();
    int i = this.parent.handle;
    int j = OS.SendMessage(i, 4362, 4, this.handle);
    if (j == 0)
      error(6);
    j = this.parent.findItem(j, paramInt);
    if (j == 0)
      error(6);
    TVITEM localTVITEM = new TVITEM();
    localTVITEM.mask = 20;
    this.parent.clear(j, localTVITEM);
    if (paramBoolean)
    {
      j = OS.SendMessage(i, 4362, 4, j);
      this.parent.clearAll(j, localTVITEM, paramBoolean);
    }
  }

  public void clearAll(boolean paramBoolean)
  {
    checkWidget();
    int i = this.parent.handle;
    int j = OS.SendMessage(i, 4362, 4, this.handle);
    if (j == 0)
      return;
    TVITEM localTVITEM = new TVITEM();
    localTVITEM.mask = 20;
    this.parent.clearAll(j, localTVITEM, paramBoolean);
  }

  void destroyWidget()
  {
    TVITEM localTVITEM = new TVITEM();
    localTVITEM.mask = 20;
    this.parent.releaseItem(this.handle, localTVITEM, false);
    this.parent.destroyItem(this, this.handle);
    releaseHandle();
  }

  int fontHandle(int paramInt)
  {
    if ((this.cellFont != null) && (this.cellFont[paramInt] != null))
      return this.cellFont[paramInt].handle;
    if (this.font != null)
      return this.font.handle;
    return -1;
  }

  public Color getBackground()
  {
    checkWidget();
    if (!this.parent.checkData(this, true))
      error(24);
    if (this.background == -1)
      return this.parent.getBackground();
    return Color.win32_new(this.display, this.background);
  }

  public Color getBackground(int paramInt)
  {
    checkWidget();
    if (!this.parent.checkData(this, true))
      error(24);
    int i = Math.max(1, this.parent.getColumnCount());
    if ((paramInt < 0) || (paramInt > i - 1))
      return getBackground();
    int j = this.cellBackground != null ? this.cellBackground[paramInt] : -1;
    return j == -1 ? getBackground() : Color.win32_new(this.display, j);
  }

  public Rectangle getBounds()
  {
    checkWidget();
    if (!this.parent.checkData(this, true))
      error(24);
    RECT localRECT = getBounds(0, true, false, false);
    int i = localRECT.right - localRECT.left;
    int j = localRECT.bottom - localRECT.top;
    return new Rectangle(localRECT.left, localRECT.top, i, j);
  }

  public Rectangle getBounds(int paramInt)
  {
    checkWidget();
    if (!this.parent.checkData(this, true))
      error(24);
    RECT localRECT = getBounds(paramInt, true, true, true);
    int i = localRECT.right - localRECT.left;
    int j = localRECT.bottom - localRECT.top;
    return new Rectangle(localRECT.left, localRECT.top, i, j);
  }

  RECT getBounds(int paramInt, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    return getBounds(paramInt, paramBoolean1, paramBoolean2, paramBoolean3, false, true, 0);
  }

  RECT getBounds(int paramInt1, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, int paramInt2)
  {
    if ((!paramBoolean1) && (!paramBoolean2))
      return new RECT();
    int i = this.parent.handle;
    if (((this.parent.style & 0x10000000) == 0) && (!this.cached) && (!this.parent.painted))
    {
      TVITEM localTVITEM = new TVITEM();
      localTVITEM.mask = 17;
      localTVITEM.hItem = this.handle;
      localTVITEM.pszText = -1;
      this.parent.ignoreCustomDraw = true;
      OS.SendMessage(i, OS.TVM_SETITEM, 0, localTVITEM);
      this.parent.ignoreCustomDraw = false;
    }
    int j = paramInt1 == 0 ? 1 : 0;
    int k = 0;
    int m = this.parent.hwndHeader;
    if (m != 0)
    {
      k = this.parent.columnCount;
      j = paramInt1 == OS.SendMessage(m, 4623, 0, 0) ? 1 : 0;
    }
    RECT localRECT1 = new RECT();
    Object localObject;
    if (j != 0)
    {
      int n = (k == 0) && (paramBoolean1) && (paramBoolean2) && (paramBoolean3) && (paramBoolean4) ? 1 : 0;
      if (!OS.TreeView_GetItemRect(i, this.handle, localRECT1, n == 0))
        return new RECT();
      if ((paramBoolean2) && (!paramBoolean4))
        if (OS.SendMessage(i, 4360, 0, 0) != 0)
        {
          localObject = this.parent.getImageSize();
          localRECT1.left -= ((Point)localObject).x + 3;
          if (!paramBoolean1)
            localRECT1.right = (localRECT1.left + ((Point)localObject).x);
        }
        else if (!paramBoolean1)
        {
          localRECT1.right = localRECT1.left;
        }
      if (((paramBoolean3) || (paramBoolean4) || (paramBoolean5)) && (m != 0))
      {
        localObject = new RECT();
        if (k != 0)
        {
          if (OS.SendMessage(m, 4615, paramInt1, (RECT)localObject) == 0)
            return new RECT();
        }
        else
        {
          ((RECT)localObject).right = this.parent.scrollWidth;
          if (((RECT)localObject).right == 0)
            localObject = localRECT1;
        }
        if ((paramBoolean3) && (paramBoolean5))
          localRECT1.right = ((RECT)localObject).right;
        if (paramBoolean4)
          localRECT1.left = ((RECT)localObject).left;
        if ((paramBoolean5) && (((RECT)localObject).right < localRECT1.right))
          localRECT1.right = ((RECT)localObject).right;
      }
    }
    else
    {
      if ((paramInt1 < 0) || (paramInt1 >= k))
        return new RECT();
      RECT localRECT2 = new RECT();
      if (OS.SendMessage(m, 4615, paramInt1, localRECT2) == 0)
        return new RECT();
      if (!OS.TreeView_GetItemRect(i, this.handle, localRECT1, false))
        return new RECT();
      localRECT1.left = localRECT2.left;
      if ((paramBoolean3) && (paramBoolean2) && (paramBoolean5))
      {
        localRECT1.right = localRECT2.right;
      }
      else
      {
        localRECT1.right = localRECT2.left;
        localObject = null;
        if (paramInt1 == 0)
          localObject = this.image;
        else if (this.images != null)
          localObject = this.images[paramInt1];
        Point localPoint;
        if (localObject != null)
        {
          localPoint = this.parent.getImageSize();
          localRECT1.right += localPoint.x;
        }
        if (paramBoolean1)
          if ((paramBoolean3) && (paramBoolean5))
          {
            localRECT1.left = (localRECT1.right + 3);
            localRECT1.right = localRECT2.right;
          }
          else
          {
            localPoint = this.strings != null ? this.strings[paramInt1] : paramInt1 == 0 ? this.text : null;
            if (localPoint != null)
            {
              RECT localRECT3 = new RECT();
              TCHAR localTCHAR = new TCHAR(this.parent.getCodePage(), localPoint, false);
              int i2 = 3104;
              int i3 = paramInt2;
              int i4 = 0;
              if (paramInt2 == 0)
              {
                i3 = OS.GetDC(i);
                i4 = fontHandle(paramInt1);
                if (i4 == -1)
                  i4 = OS.SendMessage(i, 49, 0, 0);
                i4 = OS.SelectObject(i3, i4);
              }
              OS.DrawText(i3, localTCHAR, localTCHAR.length(), localRECT3, i2);
              if (paramInt2 == 0)
              {
                OS.SelectObject(i3, i4);
                OS.ReleaseDC(i, i3);
              }
              if (paramBoolean2)
              {
                localRECT1.right += localRECT3.right - localRECT3.left + 9;
              }
              else
              {
                localRECT1.left = (localRECT1.right + 3);
                localRECT1.right = (localRECT1.left + (localRECT3.right - localRECT3.left) + 3);
              }
            }
          }
        if ((paramBoolean5) && (localRECT2.right < localRECT1.right))
          localRECT1.right = localRECT2.right;
      }
    }
    int i1 = (this.parent.linesVisible) && (k != 0) ? 1 : 0;
    if ((paramBoolean1) || (!paramBoolean2))
      localRECT1.right = Math.max(localRECT1.left, localRECT1.right - i1);
    localRECT1.bottom = Math.max(localRECT1.top, localRECT1.bottom - i1);
    return localRECT1;
  }

  public boolean getChecked()
  {
    checkWidget();
    if (!this.parent.checkData(this, true))
      error(24);
    if ((this.parent.style & 0x20) == 0)
      return false;
    int i = this.parent.handle;
    TVITEM localTVITEM = new TVITEM();
    localTVITEM.mask = 24;
    localTVITEM.stateMask = 61440;
    localTVITEM.hItem = this.handle;
    int j = OS.SendMessage(i, OS.TVM_GETITEM, 0, localTVITEM);
    return (j != 0) && ((localTVITEM.state >> 12 & 0x1) == 0);
  }

  public boolean getExpanded()
  {
    checkWidget();
    int i = this.parent.handle;
    int j = 0;
    if (OS.IsWinCE)
    {
      TVITEM localTVITEM = new TVITEM();
      localTVITEM.hItem = this.handle;
      localTVITEM.mask = 8;
      OS.SendMessage(i, OS.TVM_GETITEM, 0, localTVITEM);
      j = localTVITEM.state;
    }
    else
    {
      j = OS.SendMessage(i, 4391, this.handle, 32);
    }
    return (j & 0x20) != 0;
  }

  public Font getFont()
  {
    checkWidget();
    if (!this.parent.checkData(this, true))
      error(24);
    return this.font != null ? this.font : this.parent.getFont();
  }

  public Font getFont(int paramInt)
  {
    checkWidget();
    if (!this.parent.checkData(this, true))
      error(24);
    int i = Math.max(1, this.parent.getColumnCount());
    if ((paramInt < 0) || (paramInt > i - 1))
      return getFont();
    if ((this.cellFont == null) || (this.cellFont[paramInt] == null))
      return getFont();
    return this.cellFont[paramInt];
  }

  public Color getForeground()
  {
    checkWidget();
    if (!this.parent.checkData(this, true))
      error(24);
    if (this.foreground == -1)
      return this.parent.getForeground();
    return Color.win32_new(this.display, this.foreground);
  }

  public Color getForeground(int paramInt)
  {
    checkWidget();
    if (!this.parent.checkData(this, true))
      error(24);
    int i = Math.max(1, this.parent.getColumnCount());
    if ((paramInt < 0) || (paramInt > i - 1))
      return getForeground();
    int j = this.cellForeground != null ? this.cellForeground[paramInt] : -1;
    return j == -1 ? getForeground() : Color.win32_new(this.display, j);
  }

  public boolean getGrayed()
  {
    checkWidget();
    if (!this.parent.checkData(this, true))
      error(24);
    if ((this.parent.style & 0x20) == 0)
      return false;
    int i = this.parent.handle;
    TVITEM localTVITEM = new TVITEM();
    localTVITEM.mask = 24;
    localTVITEM.stateMask = 61440;
    localTVITEM.hItem = this.handle;
    int j = OS.SendMessage(i, OS.TVM_GETITEM, 0, localTVITEM);
    return (j != 0) && (localTVITEM.state >> 12 > 2);
  }

  public TreeItem getItem(int paramInt)
  {
    checkWidget();
    if (paramInt < 0)
      error(6);
    if (!this.parent.checkData(this, true))
      error(24);
    int i = this.parent.handle;
    int j = OS.SendMessage(i, 4362, 4, this.handle);
    if (j == 0)
      error(6);
    int k = this.parent.findItem(j, paramInt);
    if (k == 0)
      error(6);
    return this.parent._getItem(k);
  }

  public int getItemCount()
  {
    checkWidget();
    if (!this.parent.checkData(this, true))
      error(24);
    int i = this.parent.handle;
    int j = OS.SendMessage(i, 4362, 4, this.handle);
    if (j == 0)
      return 0;
    return this.parent.getItemCount(j);
  }

  public TreeItem[] getItems()
  {
    checkWidget();
    if (!this.parent.checkData(this, true))
      error(24);
    int i = this.parent.handle;
    int j = OS.SendMessage(i, 4362, 4, this.handle);
    if (j == 0)
      return new TreeItem[0];
    return this.parent.getItems(j);
  }

  public Image getImage()
  {
    checkWidget();
    if (!this.parent.checkData(this, true))
      error(24);
    return super.getImage();
  }

  public Image getImage(int paramInt)
  {
    checkWidget();
    if (!this.parent.checkData(this, true))
      error(24);
    if (paramInt == 0)
      return getImage();
    if ((this.images != null) && (paramInt >= 0) && (paramInt < this.images.length))
      return this.images[paramInt];
    return null;
  }

  public Rectangle getImageBounds(int paramInt)
  {
    checkWidget();
    if (!this.parent.checkData(this, true))
      error(24);
    RECT localRECT = getBounds(paramInt, false, true, false);
    int i = localRECT.right - localRECT.left;
    int j = localRECT.bottom - localRECT.top;
    return new Rectangle(localRECT.left, localRECT.top, i, j);
  }

  public Tree getParent()
  {
    checkWidget();
    return this.parent;
  }

  public TreeItem getParentItem()
  {
    checkWidget();
    int i = this.parent.handle;
    int j = OS.SendMessage(i, 4362, 3, this.handle);
    return j != 0 ? this.parent._getItem(j) : null;
  }

  public String getText()
  {
    checkWidget();
    if (!this.parent.checkData(this, true))
      error(24);
    return super.getText();
  }

  public String getText(int paramInt)
  {
    checkWidget();
    if (!this.parent.checkData(this, true))
      error(24);
    if (paramInt == 0)
      return getText();
    if ((this.strings != null) && (paramInt >= 0) && (paramInt < this.strings.length))
    {
      String str = this.strings[paramInt];
      return str != null ? str : "";
    }
    return "";
  }

  public Rectangle getTextBounds(int paramInt)
  {
    checkWidget();
    if (!this.parent.checkData(this, true))
      error(24);
    RECT localRECT = getBounds(paramInt, true, false, true);
    if (paramInt == 0)
      localRECT.left += 2;
    localRECT.left = Math.min(localRECT.left, localRECT.right);
    localRECT.right -= 3;
    int i = Math.max(0, localRECT.right - localRECT.left);
    int j = Math.max(0, localRECT.bottom - localRECT.top);
    return new Rectangle(localRECT.left, localRECT.top, i, j);
  }

  public int indexOf(TreeItem paramTreeItem)
  {
    checkWidget();
    if (paramTreeItem == null)
      error(4);
    if (paramTreeItem.isDisposed())
      error(5);
    int i = this.parent.handle;
    int j = OS.SendMessage(i, 4362, 4, this.handle);
    return j == 0 ? -1 : this.parent.findIndex(j, paramTreeItem.handle);
  }

  void redraw()
  {
    if ((this.parent.currentItem == this) || (!this.parent.getDrawing()))
      return;
    int i = this.parent.handle;
    if (!OS.IsWindowVisible(i))
      return;
    int j = (this.parent.style & 0x10010000) != 0 ? 1 : 0;
    if (j == 0)
    {
      j = this.parent.columnCount != 0 ? 1 : 0;
      if ((j == 0) && ((this.parent.hooks(40)) || (this.parent.hooks(42))))
        j = 1;
    }
    RECT localRECT = new RECT();
    if (OS.TreeView_GetItemRect(i, this.handle, localRECT, j == 0))
      OS.InvalidateRect(i, localRECT, true);
  }

  void redraw(int paramInt, boolean paramBoolean1, boolean paramBoolean2)
  {
    if ((this.parent.currentItem == this) || (!this.parent.getDrawing()))
      return;
    int i = this.parent.handle;
    if (!OS.IsWindowVisible(i))
      return;
    boolean bool = (paramInt == 0) && (paramBoolean1) && (paramBoolean2);
    RECT localRECT = getBounds(paramInt, paramBoolean1, paramBoolean2, true, bool, true, 0);
    OS.InvalidateRect(i, localRECT, true);
  }

  void releaseChildren(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      TVITEM localTVITEM = new TVITEM();
      localTVITEM.mask = 20;
      this.parent.releaseItems(this.handle, localTVITEM);
    }
    super.releaseChildren(paramBoolean);
  }

  void releaseHandle()
  {
    super.releaseHandle();
    this.handle = 0;
    this.parent = null;
  }

  void releaseWidget()
  {
    super.releaseWidget();
    this.strings = null;
    this.images = null;
    this.cellBackground = (this.cellForeground = null);
    this.cellFont = null;
  }

  public void removeAll()
  {
    checkWidget();
    int i = this.parent.handle;
    TVITEM localTVITEM = new TVITEM();
    localTVITEM.mask = 20;
    for (localTVITEM.hItem = OS.SendMessage(i, 4362, 4, this.handle); localTVITEM.hItem != 0; localTVITEM.hItem = OS.SendMessage(i, 4362, 4, this.handle))
    {
      OS.SendMessage(i, OS.TVM_GETITEM, 0, localTVITEM);
      Object localObject = localTVITEM.lParam != -1 ? this.parent.items[localTVITEM.lParam] : null;
      if ((localObject != null) && (!localObject.isDisposed()))
      {
        localObject.dispose();
      }
      else
      {
        this.parent.releaseItem(localTVITEM.hItem, localTVITEM, false);
        this.parent.destroyItem(null, localTVITEM.hItem);
      }
    }
  }

  public void setBackground(Color paramColor)
  {
    checkWidget();
    if ((paramColor != null) && (paramColor.isDisposed()))
      SWT.error(5);
    int i = -1;
    if (paramColor != null)
    {
      this.parent.customDraw = true;
      i = paramColor.handle;
    }
    if (this.background == i)
      return;
    this.background = i;
    if ((this.parent.style & 0x10000000) != 0)
      this.cached = true;
    redraw();
  }

  public void setBackground(int paramInt, Color paramColor)
  {
    checkWidget();
    if ((paramColor != null) && (paramColor.isDisposed()))
      SWT.error(5);
    int i = Math.max(1, this.parent.getColumnCount());
    if ((paramInt < 0) || (paramInt > i - 1))
      return;
    int j = -1;
    if (paramColor != null)
    {
      this.parent.customDraw = true;
      j = paramColor.handle;
    }
    if (this.cellBackground == null)
    {
      this.cellBackground = new int[i];
      for (int k = 0; k < i; k++)
        this.cellBackground[k] = -1;
    }
    if (this.cellBackground[paramInt] == j)
      return;
    this.cellBackground[paramInt] = j;
    if ((this.parent.style & 0x10000000) != 0)
      this.cached = true;
    redraw(paramInt, true, true);
  }

  public void setChecked(boolean paramBoolean)
  {
    checkWidget();
    if ((this.parent.style & 0x20) == 0)
      return;
    int i = this.parent.handle;
    TVITEM localTVITEM = new TVITEM();
    localTVITEM.mask = 24;
    localTVITEM.stateMask = 61440;
    localTVITEM.hItem = this.handle;
    OS.SendMessage(i, OS.TVM_GETITEM, 0, localTVITEM);
    int j = localTVITEM.state >> 12;
    if (paramBoolean)
    {
      if ((j & 0x1) != 0)
        j++;
    }
    else if ((j & 0x1) == 0)
      j--;
    j <<= 12;
    if (localTVITEM.state == j)
      return;
    if ((this.parent.style & 0x10000000) != 0)
      this.cached = true;
    localTVITEM.state = j;
    OS.SendMessage(i, OS.TVM_SETITEM, 0, localTVITEM);
    if (((this.parent.style & 0x10000000) != 0) && (this.parent.currentItem == this) && (OS.IsWindowVisible(i)))
    {
      RECT localRECT = new RECT();
      if (OS.TreeView_GetItemRect(i, this.handle, localRECT, false))
        OS.InvalidateRect(i, localRECT, true);
    }
  }

  public void setExpanded(boolean paramBoolean)
  {
    checkWidget();
    int i = this.parent.handle;
    if (OS.SendMessage(i, 4362, 4, this.handle) == 0)
      return;
    int j = 0;
    if (OS.IsWinCE)
    {
      localObject = new TVITEM();
      ((TVITEM)localObject).hItem = this.handle;
      ((TVITEM)localObject).mask = 8;
      OS.SendMessage(i, OS.TVM_GETITEM, 0, (TVITEM)localObject);
      j = ((TVITEM)localObject).state;
    }
    else
    {
      j = OS.SendMessage(i, 4391, this.handle, 32);
    }
    if (((j & 0x20) != 0) == paramBoolean)
      return;
    Object localObject = null;
    RECT[] arrayOfRECT = (RECT[])null;
    SCROLLINFO localSCROLLINFO1 = null;
    int k = 0;
    int m = 0;
    int n = 0;
    int i1 = 1;
    int i2 = OS.SendMessage(i, 4362, 5, 0);
    if ((i1 != 0) && (i2 != 0))
    {
      localSCROLLINFO1 = new SCROLLINFO();
      localSCROLLINFO1.cbSize = SCROLLINFO.sizeof;
      localSCROLLINFO1.fMask = 23;
      if (!OS.GetScrollInfo(i, 0, localSCROLLINFO1))
        localSCROLLINFO1 = null;
      if ((this.parent.getDrawing()) && (OS.IsWindowVisible(i)))
      {
        i3 = 1;
        k = OS.SendMessage(i, 4368, 0, 0);
        arrayOfRECT = new RECT[k + 1];
        i4 = i2;
        int i5 = 0;
        while ((i4 != 0) && ((i3 != 0) || (i4 != this.handle)) && (i5 < k))
        {
          RECT localRECT2 = new RECT();
          if (OS.TreeView_GetItemRect(i, i4, localRECT2, true))
            arrayOfRECT[(i5++)] = localRECT2;
          i4 = OS.SendMessage(i, 4362, 6, i4);
        }
        if ((i3 != 0) || (i4 != this.handle))
        {
          n = 1;
          k = i5;
          m = i4;
          localObject = new RECT();
          OS.GetClientRect(i, (RECT)localObject);
          int i7 = this.parent.topHandle();
          OS.UpdateWindow(i7);
          OS.DefWindowProc(i7, 11, 0, 0);
          if (i != i7)
          {
            OS.UpdateWindow(i);
            OS.DefWindowProc(i, 11, 0, 0);
          }
        }
      }
    }
    int i3 = OS.SendMessage(i, 4362, 9, 0);
    this.parent.ignoreExpand = true;
    OS.SendMessage(i, 4354, paramBoolean ? 2 : 1, this.handle);
    this.parent.ignoreExpand = false;
    if ((i1 != 0) && (i2 != 0))
    {
      i4 = 0;
      if (!paramBoolean)
      {
        RECT localRECT1 = new RECT();
        while ((i2 != 0) && (!OS.TreeView_GetItemRect(i, i2, localRECT1, false)))
        {
          i2 = OS.SendMessage(i, 4362, 3, i2);
          i4 = 1;
        }
      }
      int i6 = 1;
      if (i2 != 0)
      {
        OS.SendMessage(i, 4363, 5, i2);
        i6 = i2 != OS.SendMessage(i, 4362, 5, 0) ? 1 : 0;
      }
      if ((i4 == 0) && (i6 == 0) && (localSCROLLINFO1 != null))
      {
        SCROLLINFO localSCROLLINFO2 = new SCROLLINFO();
        localSCROLLINFO2.cbSize = SCROLLINFO.sizeof;
        localSCROLLINFO2.fMask = 23;
        if ((OS.GetScrollInfo(i, 0, localSCROLLINFO2)) && (localSCROLLINFO1.nPos != localSCROLLINFO2.nPos))
        {
          int i9 = OS.MAKELPARAM(4, localSCROLLINFO1.nPos);
          OS.SendMessage(i, 276, i9, 0);
        }
      }
      if (n != 0)
      {
        int i8 = 0;
        if ((i4 == 0) && (i6 == 0))
        {
          RECT localRECT3 = new RECT();
          OS.GetClientRect(i, localRECT3);
          if (OS.EqualRect((RECT)localObject, localRECT3))
          {
            int i11 = i2;
            for (int i13 = 0; (i11 != 0) && (i13 < k); i13++)
            {
              RECT localRECT5 = new RECT();
              if ((OS.TreeView_GetItemRect(i, i11, localRECT5, true)) && (!OS.EqualRect(localRECT5, arrayOfRECT[i13])))
                break;
              i11 = OS.SendMessage(i, 4362, 6, i11);
            }
            i8 = (i13 == k) && (i11 == m) ? 1 : 0;
          }
        }
        int i10 = this.parent.topHandle();
        OS.DefWindowProc(i10, 11, 1, 0);
        if (i != i10)
          OS.DefWindowProc(i, 11, 1, 0);
        if (i8 != 0)
        {
          this.parent.updateScrollBar();
          SCROLLINFO localSCROLLINFO3 = new SCROLLINFO();
          localSCROLLINFO3.cbSize = SCROLLINFO.sizeof;
          localSCROLLINFO3.fMask = 23;
          if (OS.GetScrollInfo(i, 1, localSCROLLINFO3))
            OS.SetScrollInfo(i, 1, localSCROLLINFO3, true);
          if (this.handle == m)
          {
            RECT localRECT4 = new RECT();
            if (OS.TreeView_GetItemRect(i, m, localRECT4, false))
              OS.InvalidateRect(i, localRECT4, true);
          }
        }
        else if (OS.IsWinCE)
        {
          OS.InvalidateRect(i10, null, true);
          if (i != i10)
            OS.InvalidateRect(i, null, true);
        }
        else
        {
          int i12 = 1157;
          OS.RedrawWindow(i10, null, 0, i12);
        }
      }
    }
    int i4 = OS.SendMessage(i, 4362, 9, 0);
    if (i4 != i3)
    {
      Event localEvent = new Event();
      if (i4 != 0)
      {
        localEvent.item = this.parent._getItem(i4);
        this.parent.hAnchor = i4;
      }
      this.parent.sendSelectionEvent(13, localEvent, true);
    }
  }

  public void setFont(Font paramFont)
  {
    checkWidget();
    if ((paramFont != null) && (paramFont.isDisposed()))
      SWT.error(5);
    Font localFont = this.font;
    if (localFont == paramFont)
      return;
    this.font = paramFont;
    if ((localFont != null) && (localFont.equals(paramFont)))
      return;
    if (paramFont != null)
      this.parent.customDraw = true;
    if ((this.parent.style & 0x10000000) != 0)
      this.cached = true;
    if (((this.parent.style & 0x10000000) == 0) && (!this.cached) && (!this.parent.painted))
      return;
    int i = this.parent.handle;
    TVITEM localTVITEM = new TVITEM();
    localTVITEM.mask = 17;
    localTVITEM.hItem = this.handle;
    localTVITEM.pszText = -1;
    OS.SendMessage(i, OS.TVM_SETITEM, 0, localTVITEM);
  }

  public void setFont(int paramInt, Font paramFont)
  {
    checkWidget();
    if ((paramFont != null) && (paramFont.isDisposed()))
      SWT.error(5);
    int i = Math.max(1, this.parent.getColumnCount());
    if ((paramInt < 0) || (paramInt > i - 1))
      return;
    if (this.cellFont == null)
    {
      if (paramFont == null)
        return;
      this.cellFont = new Font[i];
    }
    Font localFont = this.cellFont[paramInt];
    if (localFont == paramFont)
      return;
    this.cellFont[paramInt] = paramFont;
    if ((localFont != null) && (localFont.equals(paramFont)))
      return;
    if (paramFont != null)
      this.parent.customDraw = true;
    if ((this.parent.style & 0x10000000) != 0)
      this.cached = true;
    if (paramInt == 0)
    {
      if (((this.parent.style & 0x10000000) == 0) && (!this.cached) && (!this.parent.painted))
        return;
      int j = this.parent.handle;
      TVITEM localTVITEM = new TVITEM();
      localTVITEM.mask = 17;
      localTVITEM.hItem = this.handle;
      localTVITEM.pszText = -1;
      OS.SendMessage(j, OS.TVM_SETITEM, 0, localTVITEM);
    }
    else
    {
      redraw(paramInt, true, false);
    }
  }

  public void setForeground(Color paramColor)
  {
    checkWidget();
    if ((paramColor != null) && (paramColor.isDisposed()))
      SWT.error(5);
    int i = -1;
    if (paramColor != null)
    {
      this.parent.customDraw = true;
      i = paramColor.handle;
    }
    if (this.foreground == i)
      return;
    this.foreground = i;
    if ((this.parent.style & 0x10000000) != 0)
      this.cached = true;
    redraw();
  }

  public void setForeground(int paramInt, Color paramColor)
  {
    checkWidget();
    if ((paramColor != null) && (paramColor.isDisposed()))
      SWT.error(5);
    int i = Math.max(1, this.parent.getColumnCount());
    if ((paramInt < 0) || (paramInt > i - 1))
      return;
    int j = -1;
    if (paramColor != null)
    {
      this.parent.customDraw = true;
      j = paramColor.handle;
    }
    if (this.cellForeground == null)
    {
      this.cellForeground = new int[i];
      for (int k = 0; k < i; k++)
        this.cellForeground[k] = -1;
    }
    if (this.cellForeground[paramInt] == j)
      return;
    this.cellForeground[paramInt] = j;
    if ((this.parent.style & 0x10000000) != 0)
      this.cached = true;
    redraw(paramInt, true, false);
  }

  public void setGrayed(boolean paramBoolean)
  {
    checkWidget();
    if ((this.parent.style & 0x20) == 0)
      return;
    int i = this.parent.handle;
    TVITEM localTVITEM = new TVITEM();
    localTVITEM.mask = 24;
    localTVITEM.stateMask = 61440;
    localTVITEM.hItem = this.handle;
    OS.SendMessage(i, OS.TVM_GETITEM, 0, localTVITEM);
    int j = localTVITEM.state >> 12;
    if (paramBoolean)
    {
      if (j <= 2)
        j += 2;
    }
    else if (j > 2)
      j -= 2;
    j <<= 12;
    if (localTVITEM.state == j)
      return;
    if ((this.parent.style & 0x10000000) != 0)
      this.cached = true;
    localTVITEM.state = j;
    OS.SendMessage(i, OS.TVM_SETITEM, 0, localTVITEM);
    if (((this.parent.style & 0x10000000) != 0) && (this.parent.currentItem == this) && (OS.IsWindowVisible(i)))
    {
      RECT localRECT = new RECT();
      if (OS.TreeView_GetItemRect(i, this.handle, localRECT, false))
        OS.InvalidateRect(i, localRECT, true);
    }
  }

  public void setImage(Image[] paramArrayOfImage)
  {
    checkWidget();
    if (paramArrayOfImage == null)
      error(4);
    for (int i = 0; i < paramArrayOfImage.length; i++)
      setImage(i, paramArrayOfImage[i]);
  }

  public void setImage(int paramInt, Image paramImage)
  {
    checkWidget();
    if ((paramImage != null) && (paramImage.isDisposed()))
      error(5);
    Image localImage = null;
    if (paramInt == 0)
    {
      if ((paramImage != null) && (paramImage.type == 1) && (paramImage.equals(this.image)))
        return;
      localImage = this.image;
      super.setImage(paramImage);
    }
    int i = Math.max(1, this.parent.getColumnCount());
    if ((paramInt < 0) || (paramInt > i - 1))
      return;
    if ((this.images == null) && (paramInt != 0))
    {
      this.images = new Image[i];
      this.images[0] = paramImage;
    }
    if (this.images != null)
    {
      if ((paramImage != null) && (paramImage.type == 1) && (paramImage.equals(this.images[paramInt])))
        return;
      localImage = this.images[paramInt];
      this.images[paramInt] = paramImage;
    }
    if ((this.parent.style & 0x10000000) != 0)
      this.cached = true;
    this.parent.imageIndex(paramImage, paramInt);
    int j;
    if (paramInt == 0)
    {
      if (((this.parent.style & 0x10000000) == 0) && (!this.cached) && (!this.parent.painted))
        return;
      j = this.parent.handle;
      TVITEM localTVITEM = new TVITEM();
      localTVITEM.mask = 50;
      localTVITEM.hItem = this.handle;
      localTVITEM.iImage = (localTVITEM.iSelectedImage = -1);
      localTVITEM.mask |= 1;
      localTVITEM.pszText = -1;
      OS.SendMessage(j, OS.TVM_SETITEM, 0, localTVITEM);
    }
    else
    {
      j = ((paramImage != null) || (localImage == null)) && ((paramImage == null) || (localImage != null)) ? 0 : 1;
      redraw(paramInt, j, true);
    }
  }

  public void setImage(Image paramImage)
  {
    checkWidget();
    setImage(0, paramImage);
  }

  public void setItemCount(int paramInt)
  {
    checkWidget();
    paramInt = Math.max(0, paramInt);
    int i = this.parent.handle;
    int j = OS.SendMessage(i, 4362, 4, this.handle);
    this.parent.setItemCount(paramInt, this.handle, j);
  }

  public void setText(String[] paramArrayOfString)
  {
    checkWidget();
    if (paramArrayOfString == null)
      error(4);
    for (int i = 0; i < paramArrayOfString.length; i++)
    {
      String str = paramArrayOfString[i];
      if (str != null)
        setText(i, str);
    }
  }

  public void setText(int paramInt, String paramString)
  {
    checkWidget();
    if (paramString == null)
      error(4);
    if (paramInt == 0)
    {
      if (paramString.equals(this.text))
        return;
      super.setText(paramString);
    }
    int i = Math.max(1, this.parent.getColumnCount());
    if ((paramInt < 0) || (paramInt > i - 1))
      return;
    if ((this.strings == null) && (paramInt != 0))
    {
      this.strings = new String[i];
      this.strings[0] = this.text;
    }
    if (this.strings != null)
    {
      if (paramString.equals(this.strings[paramInt]))
        return;
      this.strings[paramInt] = paramString;
    }
    if ((this.parent.style & 0x10000000) != 0)
      this.cached = true;
    if (paramInt == 0)
    {
      if (((this.parent.style & 0x10000000) == 0) && (!this.cached) && (!this.parent.painted))
        return;
      int j = this.parent.handle;
      TVITEM localTVITEM = new TVITEM();
      localTVITEM.mask = 17;
      localTVITEM.hItem = this.handle;
      localTVITEM.pszText = -1;
      OS.SendMessage(j, OS.TVM_SETITEM, 0, localTVITEM);
    }
    else
    {
      redraw(paramInt, true, false);
    }
  }

  public void setText(String paramString)
  {
    checkWidget();
    setText(0, paramString);
  }

  void sort()
  {
    checkWidget();
    if ((this.parent.style & 0x10000000) != 0)
      return;
    this.parent.sort(this.handle, false);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.TreeItem
 * JD-Core Version:    0.6.2
 */