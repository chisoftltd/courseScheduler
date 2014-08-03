package org.eclipse.swt.widgets;

import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.ImageList;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.NMCUSTOMDRAW;
import org.eclipse.swt.internal.win32.NMHDR;
import org.eclipse.swt.internal.win32.NMTBHOTITEM;
import org.eclipse.swt.internal.win32.NMTOOLBAR;
import org.eclipse.swt.internal.win32.NMTTDISPINFO;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.TBBUTTON;
import org.eclipse.swt.internal.win32.TBBUTTONINFO;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.TOOLINFO;
import org.eclipse.swt.internal.win32.WINDOWPOS;
import org.eclipse.swt.internal.win32.WNDCLASS;

public class ToolBar extends Composite
{
  int lastFocusId;
  int lastArrowId;
  int lastHotId;
  ToolItem[] items;
  ToolItem[] tabItemList;
  boolean ignoreResize;
  boolean ignoreMouse;
  ImageList imageList;
  ImageList disabledImageList;
  ImageList hotImageList;
  static final int ToolBarProc = localWNDCLASS.lpfnWndProc;
  static final TCHAR ToolBarClass = new TCHAR(0, "ToolbarWindow32", true);
  static final int DEFAULT_WIDTH = 24;
  static final int DEFAULT_HEIGHT = 22;

  static
  {
    WNDCLASS localWNDCLASS = new WNDCLASS();
    OS.GetClassInfo(0, ToolBarClass, localWNDCLASS);
  }

  public ToolBar(Composite paramComposite, int paramInt)
  {
    super(paramComposite, checkStyle(paramInt));
    if ((paramInt & 0x200) != 0)
    {
      this.style |= 512;
      int i = OS.GetWindowLong(this.handle, -16);
      if ((OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()) && ((paramInt & 0x20000) != 0))
        i |= 4096;
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
    if (paramInt2 == 262)
      return OS.DefWindowProc(paramInt1, paramInt2, paramInt3, paramInt4);
    return OS.CallWindowProc(ToolBarProc, paramInt1, paramInt2, paramInt3, paramInt4);
  }

  static int checkStyle(int paramInt)
  {
    if ((paramInt & 0x800000) == 0)
      paramInt |= 524288;
    if ((paramInt & 0x200) != 0)
      paramInt &= -65;
    return paramInt & 0xFFFFFCFF;
  }

  void checkBuffered()
  {
    super.checkBuffered();
    if (OS.COMCTL32_MAJOR >= 6)
      this.style |= 536870912;
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
    int m;
    int n;
    if ((this.style & 0x200) != 0)
    {
      localObject = new RECT();
      TBBUTTON localTBBUTTON = new TBBUTTON();
      m = OS.SendMessage(this.handle, 1048, 0, 0);
      for (n = 0; n < m; n++)
      {
        OS.SendMessage(this.handle, 1053, n, (RECT)localObject);
        j = Math.max(j, ((RECT)localObject).bottom);
        OS.SendMessage(this.handle, 1047, n, localTBBUTTON);
        if ((localTBBUTTON.fsStyle & 0x1) != 0)
        {
          TBBUTTONINFO localTBBUTTONINFO = new TBBUTTONINFO();
          localTBBUTTONINFO.cbSize = TBBUTTONINFO.sizeof;
          localTBBUTTONINFO.dwMask = 64;
          OS.SendMessage(this.handle, OS.TB_GETBUTTONINFO, localTBBUTTON.idCommand, localTBBUTTONINFO);
          i = Math.max(i, localTBBUTTONINFO.cx);
        }
        else
        {
          i = Math.max(i, ((RECT)localObject).right);
        }
      }
    }
    else
    {
      localObject = new RECT();
      OS.GetWindowRect(this.handle, (RECT)localObject);
      int k = ((RECT)localObject).right - ((RECT)localObject).left;
      m = ((RECT)localObject).bottom - ((RECT)localObject).top;
      n = getBorderWidth();
      int i1 = paramInt1 == -1 ? 16383 : paramInt1 + n * 2;
      int i2 = paramInt2 == -1 ? 16383 : paramInt2 + n * 2;
      int i3 = (getDrawing()) && (OS.IsWindowVisible(this.handle)) ? 1 : 0;
      this.ignoreResize = true;
      if (i3 != 0)
        OS.UpdateWindow(this.handle);
      int i4 = 30;
      SetWindowPos(this.handle, 0, 0, 0, i1, i2, i4);
      int i5 = OS.SendMessage(this.handle, 1048, 0, 0);
      if (i5 != 0)
      {
        RECT localRECT = new RECT();
        OS.SendMessage(this.handle, 1053, i5 - 1, localRECT);
        i = Math.max(i, localRECT.right);
        j = Math.max(j, localRECT.bottom);
      }
      SetWindowPos(this.handle, 0, 0, 0, k, m, i4);
      if (i3 != 0)
        OS.ValidateRect(this.handle, null);
      this.ignoreResize = false;
    }
    if (i == 0)
      i = 24;
    if (j == 0)
      j = 22;
    if (paramInt1 != -1)
      i = paramInt1;
    if (paramInt2 != -1)
      j = paramInt2;
    Object localObject = computeTrim(0, 0, i, j);
    i = ((Rectangle)localObject).width;
    j = ((Rectangle)localObject).height;
    return new Point(i, j);
  }

  public Rectangle computeTrim(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    checkWidget();
    Rectangle localRectangle = super.computeTrim(paramInt1, paramInt2, paramInt3, paramInt4);
    int i = OS.GetWindowLong(this.handle, -16);
    if ((i & 0x40) == 0)
      localRectangle.height += 2;
    return localRectangle;
  }

  Widget computeTabGroup()
  {
    ToolItem[] arrayOfToolItem = _getItems();
    if (this.tabItemList == null)
    {
      for (i = 0; (i < arrayOfToolItem.length) && (arrayOfToolItem[i].control == null); i++);
      if (i == arrayOfToolItem.length)
        return super.computeTabGroup();
    }
    int i = OS.SendMessage(this.handle, 1095, 0, 0);
    if (i == -1);
    for (i = this.lastHotId; i >= 0; i--)
    {
      ToolItem localToolItem = arrayOfToolItem[i];
      if (localToolItem.isTabGroup())
        return localToolItem;
    }
    return super.computeTabGroup();
  }

  Widget[] computeTabList()
  {
    ToolItem[] arrayOfToolItem1 = _getItems();
    if (this.tabItemList == null)
    {
      for (int i = 0; (i < arrayOfToolItem1.length) && (arrayOfToolItem1[i].control == null); i++);
      if (i == arrayOfToolItem1.length)
        return super.computeTabList();
    }
    Object localObject = new Widget[0];
    if ((!isTabGroup()) || (!isEnabled()) || (!isVisible()))
      return localObject;
    ToolItem[] arrayOfToolItem2 = this.tabList != null ? _getTabItemList() : arrayOfToolItem1;
    for (int j = 0; j < arrayOfToolItem2.length; j++)
    {
      ToolItem localToolItem = arrayOfToolItem2[j];
      Widget[] arrayOfWidget1 = localToolItem.computeTabList();
      if (arrayOfWidget1.length != 0)
      {
        Widget[] arrayOfWidget2 = new Widget[localObject.length + arrayOfWidget1.length];
        System.arraycopy(localObject, 0, arrayOfWidget2, 0, localObject.length);
        System.arraycopy(arrayOfWidget1, 0, arrayOfWidget2, localObject.length, arrayOfWidget1.length);
        localObject = arrayOfWidget2;
      }
    }
    if (localObject.length == 0)
      localObject = new Widget[] { this };
    return localObject;
  }

  void createHandle()
  {
    super.createHandle();
    this.state &= -3;
    if (((this.style & 0x800000) != 0) && ((OS.COMCTL32_MAJOR < 6) || (!OS.IsAppThemed())))
    {
      i = OS.GetWindowLong(this.handle, -16);
      i &= -32769;
      OS.SetWindowLong(this.handle, -16, i);
    }
    int i = OS.GetStockObject(13);
    OS.SendMessage(this.handle, 48, i, 0);
    OS.SendMessage(this.handle, 1054, TBBUTTON.sizeof, 0);
    OS.SendMessage(this.handle, 1056, 0, 0);
    OS.SendMessage(this.handle, 1055, 0, 0);
    int j = 25;
    if (OS.COMCTL32_MAJOR >= 6)
      j |= 128;
    OS.SendMessage(this.handle, 1108, 0, j);
  }

  void createItem(ToolItem paramToolItem, int paramInt)
  {
    int i = OS.SendMessage(this.handle, 1048, 0, 0);
    if ((paramInt < 0) || (paramInt > i))
      error(6);
    for (int j = 0; (j < this.items.length) && (this.items[j] != null); j++);
    if (j == this.items.length)
    {
      ToolItem[] arrayOfToolItem = new ToolItem[this.items.length + 4];
      System.arraycopy(this.items, 0, arrayOfToolItem, 0, this.items.length);
      this.items = arrayOfToolItem;
    }
    int k = paramToolItem.widgetStyle();
    TBBUTTON localTBBUTTON = new TBBUTTON();
    localTBBUTTON.idCommand = j;
    localTBBUTTON.fsStyle = ((byte)k);
    localTBBUTTON.fsState = 4;
    if ((k & 0x1) == 0)
      localTBBUTTON.iBitmap = -2;
    if (OS.SendMessage(this.handle, OS.TB_INSERTBUTTON, paramInt, localTBBUTTON) == 0)
      error(14);
    int tmp180_178 = j;
    paramToolItem.id = tmp180_178;
    this.items[tmp180_178] = paramToolItem;
    if ((this.style & 0x200) != 0)
      setRowCount(i + 1);
    layoutItems();
  }

  void createWidget()
  {
    super.createWidget();
    this.items = new ToolItem[4];
    this.lastFocusId = (this.lastArrowId = this.lastHotId = -1);
  }

  int defaultBackground()
  {
    if (OS.IsWinCE)
      return OS.GetSysColor(OS.COLOR_BTNFACE);
    return super.defaultBackground();
  }

  void destroyItem(ToolItem paramToolItem)
  {
    TBBUTTONINFO localTBBUTTONINFO = new TBBUTTONINFO();
    localTBBUTTONINFO.cbSize = TBBUTTONINFO.sizeof;
    localTBBUTTONINFO.dwMask = 9;
    int i = OS.SendMessage(this.handle, OS.TB_GETBUTTONINFO, paramToolItem.id, localTBBUTTONINFO);
    if (((localTBBUTTONINFO.fsStyle & 0x1) == 0) && (localTBBUTTONINFO.iImage != -2))
    {
      if (this.imageList != null)
        this.imageList.put(localTBBUTTONINFO.iImage, null);
      if (this.hotImageList != null)
        this.hotImageList.put(localTBBUTTONINFO.iImage, null);
      if (this.disabledImageList != null)
        this.disabledImageList.put(localTBBUTTONINFO.iImage, null);
    }
    OS.SendMessage(this.handle, 1046, i, 0);
    if (paramToolItem.id == this.lastFocusId)
      this.lastFocusId = -1;
    if (paramToolItem.id == this.lastArrowId)
      this.lastArrowId = -1;
    if (paramToolItem.id == this.lastHotId)
      this.lastHotId = -1;
    this.items[paramToolItem.id] = null;
    paramToolItem.id = -1;
    int j = OS.SendMessage(this.handle, 1048, 0, 0);
    if (j == 0)
    {
      if (this.imageList != null)
      {
        OS.SendMessage(this.handle, 1072, 0, 0);
        this.display.releaseToolImageList(this.imageList);
      }
      if (this.hotImageList != null)
      {
        OS.SendMessage(this.handle, 1076, 0, 0);
        this.display.releaseToolHotImageList(this.hotImageList);
      }
      if (this.disabledImageList != null)
      {
        OS.SendMessage(this.handle, 1078, 0, 0);
        this.display.releaseToolDisabledImageList(this.disabledImageList);
      }
      this.imageList = (this.hotImageList = this.disabledImageList = null);
      this.items = new ToolItem[4];
    }
    if ((this.style & 0x200) != 0)
      setRowCount(j - 1);
    layoutItems();
  }

  void enableWidget(boolean paramBoolean)
  {
    super.enableWidget(paramBoolean);
    for (int i = 0; i < this.items.length; i++)
    {
      ToolItem localToolItem = this.items[i];
      if ((localToolItem != null) && ((localToolItem.style & 0x2) == 0))
        localToolItem.updateImages((paramBoolean) && (localToolItem.getEnabled()));
    }
  }

  ImageList getDisabledImageList()
  {
    return this.disabledImageList;
  }

  ImageList getHotImageList()
  {
    return this.hotImageList;
  }

  ImageList getImageList()
  {
    return this.imageList;
  }

  public ToolItem getItem(int paramInt)
  {
    checkWidget();
    int i = OS.SendMessage(this.handle, 1048, 0, 0);
    if ((paramInt < 0) || (paramInt >= i))
      error(6);
    TBBUTTON localTBBUTTON = new TBBUTTON();
    int j = OS.SendMessage(this.handle, 1047, paramInt, localTBBUTTON);
    if (j == 0)
      error(8);
    return this.items[localTBBUTTON.idCommand];
  }

  public ToolItem getItem(Point paramPoint)
  {
    checkWidget();
    if (paramPoint == null)
      error(4);
    ToolItem[] arrayOfToolItem = getItems();
    for (int i = 0; i < arrayOfToolItem.length; i++)
    {
      Rectangle localRectangle = arrayOfToolItem[i].getBounds();
      if (localRectangle.contains(paramPoint))
        return arrayOfToolItem[i];
    }
    return null;
  }

  public int getItemCount()
  {
    checkWidget();
    return OS.SendMessage(this.handle, 1048, 0, 0);
  }

  public ToolItem[] getItems()
  {
    checkWidget();
    return _getItems();
  }

  ToolItem[] _getItems()
  {
    int i = OS.SendMessage(this.handle, 1048, 0, 0);
    TBBUTTON localTBBUTTON = new TBBUTTON();
    ToolItem[] arrayOfToolItem = new ToolItem[i];
    for (int j = 0; j < i; j++)
    {
      OS.SendMessage(this.handle, 1047, j, localTBBUTTON);
      arrayOfToolItem[j] = this.items[localTBBUTTON.idCommand];
    }
    return arrayOfToolItem;
  }

  public int getRowCount()
  {
    checkWidget();
    if ((this.style & 0x200) != 0)
      return OS.SendMessage(this.handle, 1048, 0, 0);
    return OS.SendMessage(this.handle, 1064, 0, 0);
  }

  ToolItem[] _getTabItemList()
  {
    if (this.tabItemList == null)
      return this.tabItemList;
    int i = 0;
    for (int j = 0; j < this.tabItemList.length; j++)
      if (!this.tabItemList[j].isDisposed())
        i++;
    if (i == this.tabItemList.length)
      return this.tabItemList;
    ToolItem[] arrayOfToolItem = new ToolItem[i];
    int k = 0;
    for (int m = 0; m < this.tabItemList.length; m++)
      if (!this.tabItemList[m].isDisposed())
        arrayOfToolItem[(k++)] = this.tabItemList[m];
    this.tabItemList = arrayOfToolItem;
    return this.tabItemList;
  }

  public int indexOf(ToolItem paramToolItem)
  {
    checkWidget();
    if (paramToolItem == null)
      error(4);
    if (paramToolItem.isDisposed())
      error(5);
    return OS.SendMessage(this.handle, 1049, paramToolItem.id, 0);
  }

  void layoutItems()
  {
    ToolItem localToolItem1;
    int m;
    if ((OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()) && ((this.style & 0x20000) != 0) && ((this.style & 0x200) == 0))
    {
      i = 0;
      int j = 0;
      for (localToolItem1 = 0; localToolItem1 < this.items.length; localToolItem1++)
      {
        localToolItem2 = this.items[localToolItem1];
        if (localToolItem2 != null)
        {
          if (i == 0)
            i = localToolItem2.text.length() != 0 ? 1 : 0;
          if (j == 0)
            j = localToolItem2.image != null ? 1 : 0;
          if ((i != 0) && (j != 0))
            break;
        }
      }
      localToolItem1 = OS.GetWindowLong(this.handle, -16);
      ToolItem localToolItem2 = localToolItem1;
      int k;
      if ((i != 0) && (j != 0))
        localToolItem2 |= 4096;
      else
        k &= -4097;
      if (m != localToolItem1)
      {
        setDropDownItems(false);
        OS.SetWindowLong(this.handle, -16, m);
        int n = OS.SendMessage(this.handle, 49, 0, 0);
        OS.SendMessage(this.handle, 48, n, 0);
        setDropDownItems(true);
      }
    }
    if ((this.style & 0x40) != 0)
      OS.SendMessage(this.handle, 1057, 0, 0);
    Object localObject;
    if ((this.style & 0x200) != 0)
    {
      i = OS.SendMessage(this.handle, 1048, 0, 0);
      if (i > 1)
      {
        localObject = new TBBUTTONINFO();
        ((TBBUTTONINFO)localObject).cbSize = TBBUTTONINFO.sizeof;
        ((TBBUTTONINFO)localObject).dwMask = 64;
        localToolItem1 = OS.SendMessage(this.handle, 1082, 0, 0);
        ((TBBUTTONINFO)localObject).cx = ((short)OS.LOWORD(localToolItem1));
        for (m = 0; m < this.items.length; m++)
        {
          ToolItem localToolItem4 = this.items[m];
          if ((localToolItem4 != null) && ((localToolItem4.style & 0x4) != 0))
            break;
        }
        if (m < this.items.length)
        {
          i1 = OS.SendMessage(this.handle, 1110, 0, 0);
          Object tmp379_378 = localObject;
          tmp379_378.cx = ((short)(tmp379_378.cx + OS.LOWORD(i1) * 2));
        }
        for (int i1 = 0; i1 < this.items.length; i1++)
        {
          ToolItem localToolItem5 = this.items[i1];
          if ((localToolItem5 != null) && ((localToolItem5.style & 0x2) == 0))
            OS.SendMessage(this.handle, OS.TB_SETBUTTONINFO, localToolItem5.id, (TBBUTTONINFO)localObject);
        }
      }
    }
    if ((this.style & 0x240) != 0)
    {
      i = OS.GetWindowLong(this.handle, -16);
      if ((i & 0x1000) != 0)
      {
        localObject = new TBBUTTONINFO();
        ((TBBUTTONINFO)localObject).cbSize = TBBUTTONINFO.sizeof;
        ((TBBUTTONINFO)localObject).dwMask = 64;
        for (localToolItem1 = 0; localToolItem1 < this.items.length; localToolItem1++)
        {
          ToolItem localToolItem3 = this.items[localToolItem1];
          if ((localToolItem3 != null) && (localToolItem3.cx > 0))
          {
            ((TBBUTTONINFO)localObject).cx = localToolItem3.cx;
            OS.SendMessage(this.handle, OS.TB_SETBUTTONINFO, localToolItem3.id, (TBBUTTONINFO)localObject);
          }
        }
      }
    }
    for (int i = 0; i < this.items.length; i++)
    {
      localObject = this.items[i];
      if (localObject != null)
        ((ToolItem)localObject).resizeControl();
    }
  }

  boolean mnemonicHit(char paramChar)
  {
    int i = Display.wcsToMbcs(paramChar);
    int[] arrayOfInt = new int[1];
    if (OS.SendMessage(this.handle, OS.TB_MAPACCELERATOR, i, arrayOfInt) == 0)
      return false;
    if (((this.style & 0x800000) != 0) && (!setTabGroupFocus()))
      return false;
    int j = OS.SendMessage(this.handle, 1049, arrayOfInt[0], 0);
    if (j == -1)
      return false;
    OS.SendMessage(this.handle, 1096, j, 0);
    this.items[arrayOfInt[0]].click(false);
    return true;
  }

  boolean mnemonicMatch(char paramChar)
  {
    int i = Display.wcsToMbcs(paramChar);
    int[] arrayOfInt = new int[1];
    if (OS.SendMessage(this.handle, OS.TB_MAPACCELERATOR, i, arrayOfInt) == 0)
      return false;
    int j = OS.SendMessage(this.handle, 1049, arrayOfInt[0], 0);
    if (j == -1)
      return false;
    return findMnemonic(this.items[arrayOfInt[0]].text) != 0;
  }

  void releaseChildren(boolean paramBoolean)
  {
    if (this.items != null)
    {
      for (int i = 0; i < this.items.length; i++)
      {
        ToolItem localToolItem = this.items[i];
        if ((localToolItem != null) && (!localToolItem.isDisposed()))
          localToolItem.release(false);
      }
      this.items = null;
    }
    super.releaseChildren(paramBoolean);
  }

  void releaseWidget()
  {
    super.releaseWidget();
    if (this.imageList != null)
    {
      OS.SendMessage(this.handle, 1072, 0, 0);
      this.display.releaseToolImageList(this.imageList);
    }
    if (this.hotImageList != null)
    {
      OS.SendMessage(this.handle, 1076, 0, 0);
      this.display.releaseToolHotImageList(this.hotImageList);
    }
    if (this.disabledImageList != null)
    {
      OS.SendMessage(this.handle, 1078, 0, 0);
      this.display.releaseToolDisabledImageList(this.disabledImageList);
    }
    this.imageList = (this.hotImageList = this.disabledImageList = null);
  }

  void removeControl(Control paramControl)
  {
    super.removeControl(paramControl);
    for (int i = 0; i < this.items.length; i++)
    {
      ToolItem localToolItem = this.items[i];
      if ((localToolItem != null) && (localToolItem.control == paramControl))
        localToolItem.setControl(null);
    }
  }

  void reskinChildren(int paramInt)
  {
    if (this.items != null)
      for (int i = 0; i < this.items.length; i++)
      {
        ToolItem localToolItem = this.items[i];
        if (localToolItem != null)
          localToolItem.reskin(paramInt);
      }
    super.reskinChildren(paramInt);
  }

  void setBackgroundImage(int paramInt)
  {
    super.setBackgroundImage(paramInt);
    setBackgroundTransparent(paramInt != 0);
  }

  void setBackgroundPixel(int paramInt)
  {
    super.setBackgroundPixel(paramInt);
    setBackgroundTransparent(paramInt != -1);
  }

  void setBackgroundTransparent(boolean paramBoolean)
  {
    if (((this.style & 0x800000) != 0) && ((OS.COMCTL32_MAJOR < 6) || (!OS.IsAppThemed())))
    {
      int i = OS.GetWindowLong(this.handle, -16);
      if ((!paramBoolean) && (findBackgroundControl() == null))
        i &= -32769;
      else
        i |= 32768;
      OS.SetWindowLong(this.handle, -16, i);
    }
  }

  void setBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    if ((this.parent.lpwp != null) && (getDrawing()) && (OS.IsWindowVisible(this.handle)))
    {
      this.parent.setResizeChildren(false);
      this.parent.setResizeChildren(true);
    }
    super.setBounds(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
  }

  void setDefaultFont()
  {
    super.setDefaultFont();
    OS.SendMessage(this.handle, 1056, 0, 0);
    OS.SendMessage(this.handle, 1055, 0, 0);
  }

  void setDropDownItems(boolean paramBoolean)
  {
    if ((OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()))
    {
      int i = 0;
      int j = 0;
      ToolItem localToolItem;
      for (int k = 0; k < this.items.length; k++)
      {
        localToolItem = this.items[k];
        if (localToolItem != null)
        {
          if (i == 0)
            i = localToolItem.text.length() != 0 ? 1 : 0;
          if (j == 0)
            j = localToolItem.image != null ? 1 : 0;
          if ((i != 0) && (j != 0))
            break;
        }
      }
      if ((j != 0) && (i == 0))
        for (k = 0; k < this.items.length; k++)
        {
          localToolItem = this.items[k];
          if ((localToolItem != null) && ((localToolItem.style & 0x4) != 0))
          {
            TBBUTTONINFO localTBBUTTONINFO = new TBBUTTONINFO();
            localTBBUTTONINFO.cbSize = TBBUTTONINFO.sizeof;
            localTBBUTTONINFO.dwMask = 8;
            OS.SendMessage(this.handle, OS.TB_GETBUTTONINFO, localToolItem.id, localTBBUTTONINFO);
            if (paramBoolean)
            {
              TBBUTTONINFO tmp187_185 = localTBBUTTONINFO;
              tmp187_185.fsStyle = ((byte)(tmp187_185.fsStyle | 0x8));
            }
            else
            {
              TBBUTTONINFO tmp203_201 = localTBBUTTONINFO;
              tmp203_201.fsStyle = ((byte)(tmp203_201.fsStyle & 0xFFFFFFF7));
            }
            OS.SendMessage(this.handle, OS.TB_SETBUTTONINFO, localToolItem.id, localTBBUTTONINFO);
          }
        }
    }
  }

  void setDisabledImageList(ImageList paramImageList)
  {
    if (this.disabledImageList == paramImageList)
      return;
    int i = 0;
    if ((this.disabledImageList = paramImageList) != null)
      i = this.disabledImageList.getHandle();
    setDropDownItems(false);
    OS.SendMessage(this.handle, 1078, 0, i);
    setDropDownItems(true);
  }

  public void setFont(Font paramFont)
  {
    checkWidget();
    setDropDownItems(false);
    super.setFont(paramFont);
    setDropDownItems(true);
    int i = 0;
    int j = 60;
    while (i < this.items.length)
    {
      ToolItem localToolItem = this.items[i];
      if ((localToolItem != null) && ((localToolItem.style & j) != 0))
        break;
      i++;
    }
    if (i == this.items.length)
    {
      OS.SendMessage(this.handle, 1056, 0, 0);
      OS.SendMessage(this.handle, 1055, 0, 0);
    }
    layoutItems();
  }

  void setHotImageList(ImageList paramImageList)
  {
    if (this.hotImageList == paramImageList)
      return;
    int i = 0;
    if ((this.hotImageList = paramImageList) != null)
      i = this.hotImageList.getHandle();
    setDropDownItems(false);
    OS.SendMessage(this.handle, 1076, 0, i);
    setDropDownItems(true);
  }

  void setImageList(ImageList paramImageList)
  {
    if (this.imageList == paramImageList)
      return;
    int i = 0;
    if ((this.imageList = paramImageList) != null)
      i = paramImageList.getHandle();
    setDropDownItems(false);
    OS.SendMessage(this.handle, 1072, 0, i);
    setDropDownItems(true);
  }

  public boolean setParent(Composite paramComposite)
  {
    checkWidget();
    if (!super.setParent(paramComposite))
      return false;
    int i = paramComposite.handle;
    OS.SendMessage(this.handle, 1061, i, 0);
    int j = paramComposite.getShell().handle;
    int k = OS.SendMessage(this.handle, 1059, 0, 0);
    OS.SetWindowLongPtr(k, -8, j);
    return true;
  }

  public void setRedraw(boolean paramBoolean)
  {
    checkWidget();
    setDropDownItems(false);
    super.setRedraw(paramBoolean);
    setDropDownItems(true);
  }

  void setRowCount(int paramInt)
  {
    if ((this.style & 0x200) != 0)
    {
      RECT localRECT = new RECT();
      OS.GetWindowRect(this.handle, localRECT);
      OS.MapWindowPoints(0, this.parent.handle, localRECT, 2);
      this.ignoreResize = true;
      paramInt += 2;
      OS.SendMessage(this.handle, 1063, OS.MAKEWPARAM(paramInt, 1), 0);
      int i = 22;
      SetWindowPos(this.handle, 0, 0, 0, localRECT.right - localRECT.left, localRECT.bottom - localRECT.top, i);
      this.ignoreResize = false;
    }
  }

  void setTabItemList(ToolItem[] paramArrayOfToolItem)
  {
    checkWidget();
    if (paramArrayOfToolItem != null)
    {
      for (int i = 0; i < paramArrayOfToolItem.length; i++)
      {
        ToolItem localToolItem = paramArrayOfToolItem[i];
        if (localToolItem == null)
          error(5);
        if (localToolItem.isDisposed())
          error(5);
        if (localToolItem.parent != this)
          error(32);
      }
      ToolItem[] arrayOfToolItem = new ToolItem[paramArrayOfToolItem.length];
      System.arraycopy(paramArrayOfToolItem, 0, arrayOfToolItem, 0, paramArrayOfToolItem.length);
      paramArrayOfToolItem = arrayOfToolItem;
    }
    this.tabItemList = paramArrayOfToolItem;
  }

  boolean setTabItemFocus()
  {
    for (int i = 0; i < this.items.length; i++)
    {
      ToolItem localToolItem = this.items[i];
      if ((localToolItem != null) && ((localToolItem.style & 0x2) == 0) && (localToolItem.getEnabled()))
        break;
    }
    if (i == this.items.length)
      return false;
    return super.setTabItemFocus();
  }

  String toolTipText(NMTTDISPINFO paramNMTTDISPINFO)
  {
    if ((paramNMTTDISPINFO.uFlags & 0x1) != 0)
      return null;
    if (!hasCursor())
      return "";
    int i = paramNMTTDISPINFO.idFrom;
    int j = OS.SendMessage(this.handle, 1059, 0, 0);
    if (j == paramNMTTDISPINFO.hwndFrom)
    {
      if ((this.style & 0x4000000) != 0)
        paramNMTTDISPINFO.uFlags |= 4;
      else
        paramNMTTDISPINFO.uFlags &= -5;
      if (this.toolTipText != null)
        return "";
      if ((i >= 0) && (i < this.items.length))
      {
        ToolItem localToolItem = this.items[i];
        if (localToolItem != null)
        {
          if (this.lastArrowId != -1)
            return "";
          return localToolItem.toolTipText;
        }
      }
    }
    return super.toolTipText(paramNMTTDISPINFO);
  }

  int widgetStyle()
  {
    int i = super.widgetStyle() | 0x4 | 0x100 | 0x2000;
    if ((OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()))
      i |= 32768;
    if ((this.style & 0x8) == 0)
      i |= 64;
    if ((this.style & 0x40) != 0)
      i |= 512;
    if ((this.style & 0x800000) != 0)
      i |= 2048;
    if (((OS.COMCTL32_MAJOR < 6) || (!OS.IsAppThemed())) && ((this.style & 0x20000) != 0))
      i |= 4096;
    return i;
  }

  TCHAR windowClass()
  {
    return ToolBarClass;
  }

  int windowProc()
  {
    return ToolBarProc;
  }

  LRESULT WM_CAPTURECHANGED(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_CAPTURECHANGED(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    for (int i = 0; i < this.items.length; i++)
    {
      ToolItem localToolItem = this.items[i];
      if (localToolItem != null)
      {
        int j = OS.SendMessage(this.handle, 1042, localToolItem.id, 0);
        if ((j & 0x2) != 0)
        {
          j &= -3;
          OS.SendMessage(this.handle, 1041, localToolItem.id, j);
        }
      }
    }
    return localLRESULT;
  }

  LRESULT WM_CHAR(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_CHAR(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    switch (paramInt1)
    {
    case 32:
      int i = OS.SendMessage(this.handle, 1095, 0, 0);
      if (i != -1)
      {
        TBBUTTON localTBBUTTON = new TBBUTTON();
        int j = OS.SendMessage(this.handle, 1047, i, localTBBUTTON);
        if (j != 0)
        {
          this.items[localTBBUTTON.idCommand].click(false);
          return LRESULT.ZERO;
        }
      }
      break;
    }
    return localLRESULT;
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
    if ((findBackgroundControl() != null) && (OS.COMCTL32_MAJOR < 6))
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
    return new LRESULT(8193);
  }

  LRESULT WM_KEYDOWN(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_KEYDOWN(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    switch (paramInt1)
    {
    case 32:
      return LRESULT.ZERO;
    }
    return localLRESULT;
  }

  LRESULT WM_KILLFOCUS(int paramInt1, int paramInt2)
  {
    int i = OS.SendMessage(this.handle, 1095, 0, 0);
    TBBUTTON localTBBUTTON = new TBBUTTON();
    int j = OS.SendMessage(this.handle, 1047, i, localTBBUTTON);
    if (j != 0)
      this.lastFocusId = localTBBUTTON.idCommand;
    return super.WM_KILLFOCUS(paramInt1, paramInt2);
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

  LRESULT WM_MOUSELEAVE(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_MOUSELEAVE(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    if (OS.COMCTL32_MAJOR >= 6)
    {
      TOOLINFO localTOOLINFO = new TOOLINFO();
      localTOOLINFO.cbSize = TOOLINFO.sizeof;
      int i = OS.SendMessage(this.handle, 1059, 0, 0);
      if ((OS.SendMessage(i, OS.TTM_GETCURRENTTOOL, 0, localTOOLINFO) != 0) && ((localTOOLINFO.uFlags & 0x1) == 0))
      {
        OS.SendMessage(i, OS.TTM_DELTOOL, 0, localTOOLINFO);
        OS.SendMessage(i, OS.TTM_ADDTOOL, 0, localTOOLINFO);
      }
    }
    return localLRESULT;
  }

  LRESULT WM_MOUSEMOVE(int paramInt1, int paramInt2)
  {
    if (OS.GetMessagePos() != this.display.lastMouse)
      this.lastArrowId = -1;
    return super.WM_MOUSEMOVE(paramInt1, paramInt2);
  }

  LRESULT WM_NOTIFY(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_NOTIFY(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    return LRESULT.ZERO;
  }

  LRESULT WM_SETFOCUS(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_SETFOCUS(paramInt1, paramInt2);
    if ((this.lastFocusId != -1) && (this.handle == OS.GetFocus()))
    {
      int i = OS.SendMessage(this.handle, 1049, this.lastFocusId, 0);
      OS.SendMessage(this.handle, 1096, i, 0);
    }
    return localLRESULT;
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
    LRESULT localLRESULT = super.WM_SIZE(paramInt1, paramInt2);
    if (isDisposed())
      return localLRESULT;
    if (((this.style & 0x800) != 0) && ((this.style & 0x40) != 0))
    {
      RECT localRECT1 = new RECT();
      OS.GetWindowRect(this.handle, localRECT1);
      int j = 0;
      int k = getBorderWidth() * 2;
      RECT localRECT2 = new RECT();
      int m = OS.SendMessage(this.handle, 1048, 0, 0);
      while (j < m)
      {
        OS.SendMessage(this.handle, 1053, j, localRECT2);
        OS.MapWindowPoints(this.handle, 0, localRECT2, 2);
        if (localRECT2.right > localRECT1.right - k * 2)
          break;
        j++;
      }
      int n = OS.SendMessage(this.handle, 1109, 0, 0);
      if (j == m)
        n |= 16;
      else
        n &= -17;
      OS.SendMessage(this.handle, 1108, 0, n);
    }
    layoutItems();
    return localLRESULT;
  }

  LRESULT WM_WINDOWPOSCHANGING(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_WINDOWPOSCHANGING(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    if (this.ignoreResize)
      return localLRESULT;
    if (!getDrawing())
      return localLRESULT;
    if ((this.style & 0x40) == 0)
      return localLRESULT;
    if (!OS.IsWindowVisible(this.handle))
      return localLRESULT;
    if (OS.SendMessage(this.handle, 1064, 0, 0) == 1)
      return localLRESULT;
    WINDOWPOS localWINDOWPOS = new WINDOWPOS();
    OS.MoveMemory(localWINDOWPOS, paramInt2, WINDOWPOS.sizeof);
    if ((localWINDOWPOS.flags & 0x9) != 0)
      return localLRESULT;
    RECT localRECT1 = new RECT();
    OS.GetClientRect(this.handle, localRECT1);
    RECT localRECT2 = new RECT();
    OS.SetRect(localRECT2, 0, 0, localWINDOWPOS.cx, localWINDOWPOS.cy);
    OS.SendMessage(this.handle, 131, 0, localRECT2);
    int i = localRECT1.right - localRECT1.left;
    int j = localRECT2.right - localRECT2.left;
    if (j > i)
    {
      RECT localRECT3 = new RECT();
      int k = localRECT2.bottom - localRECT2.top;
      OS.SetRect(localRECT3, i - 2, 0, i, k);
      OS.InvalidateRect(this.handle, localRECT3, false);
    }
    return localLRESULT;
  }

  LRESULT wmCommandChild(int paramInt1, int paramInt2)
  {
    ToolItem localToolItem = this.items[OS.LOWORD(paramInt1)];
    if (localToolItem == null)
      return null;
    return localToolItem.wmCommandChild(paramInt1, paramInt2);
  }

  LRESULT wmNotifyChild(NMHDR paramNMHDR, int paramInt1, int paramInt2)
  {
    Object localObject;
    int i;
    RECT localRECT1;
    switch (paramNMHDR.code)
    {
    case -710:
      NMTOOLBAR localNMTOOLBAR = new NMTOOLBAR();
      OS.MoveMemory(localNMTOOLBAR, paramInt2, NMTOOLBAR.sizeof);
      ToolItem localToolItem = this.items[localNMTOOLBAR.iItem];
      if (localToolItem != null)
      {
        localObject = new Event();
        ((Event)localObject).detail = 4;
        i = OS.SendMessage(this.handle, 1049, localNMTOOLBAR.iItem, 0);
        localRECT1 = new RECT();
        OS.SendMessage(this.handle, 1053, i, localRECT1);
        ((Event)localObject).x = localRECT1.left;
        ((Event)localObject).y = localRECT1.bottom;
        localToolItem.sendSelectionEvent(13, (Event)localObject, false);
      }
      break;
    case -12:
      if (OS.COMCTL32_MAJOR >= 6)
      {
        localObject = new NMCUSTOMDRAW();
        OS.MoveMemory((NMCUSTOMDRAW)localObject, paramInt2, NMCUSTOMDRAW.sizeof);
        switch (((NMCUSTOMDRAW)localObject).dwDrawStage)
        {
        case 3:
          i = OS.GetWindowLong(this.handle, -16);
          if ((i & 0x800) == 0)
          {
            drawBackground(((NMCUSTOMDRAW)localObject).hdc);
          }
          else
          {
            localRECT1 = new RECT();
            OS.SetRect(localRECT1, ((NMCUSTOMDRAW)localObject).left, ((NMCUSTOMDRAW)localObject).top, ((NMCUSTOMDRAW)localObject).right, ((NMCUSTOMDRAW)localObject).bottom);
            drawBackground(((NMCUSTOMDRAW)localObject).hdc, localRECT1);
          }
          return new LRESULT(4);
        }
      }
      break;
    case -713:
      if (!OS.IsWinCE)
      {
        NMTBHOTITEM localNMTBHOTITEM = new NMTBHOTITEM();
        OS.MoveMemory(localNMTBHOTITEM, paramInt2, NMTBHOTITEM.sizeof);
        switch (localNMTBHOTITEM.dwFlags)
        {
        case 1:
          if (this.lastArrowId != -1)
            return LRESULT.ONE;
          break;
        case 2:
          localRECT1 = new RECT();
          OS.GetClientRect(this.handle, localRECT1);
          int j = OS.SendMessage(this.handle, 1049, localNMTBHOTITEM.idNew, 0);
          RECT localRECT2 = new RECT();
          OS.SendMessage(this.handle, 1053, j, localRECT2);
          if ((localRECT2.right > localRECT1.right) || (localRECT2.bottom > localRECT1.bottom))
            return LRESULT.ONE;
          this.lastArrowId = localNMTBHOTITEM.idNew;
          break;
        default:
          this.lastArrowId = -1;
        }
        if ((localNMTBHOTITEM.dwFlags & 0x20) == 0)
          this.lastHotId = localNMTBHOTITEM.idNew;
      }
      break;
    }
    return super.wmNotifyChild(paramNMHDR, paramInt1, paramInt2);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.ToolBar
 * JD-Core Version:    0.6.2
 */