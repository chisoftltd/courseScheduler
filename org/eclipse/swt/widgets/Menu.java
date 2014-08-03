package org.eclipse.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.HelpListener;
import org.eclipse.swt.events.MenuListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.ImageList;
import org.eclipse.swt.internal.win32.MENUBARINFO;
import org.eclipse.swt.internal.win32.MENUINFO;
import org.eclipse.swt.internal.win32.MENUITEMINFO;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.SHMENUBARINFO;
import org.eclipse.swt.internal.win32.TBBUTTON;
import org.eclipse.swt.internal.win32.TBBUTTONINFO;
import org.eclipse.swt.internal.win32.TCHAR;

public class Menu extends Widget
{
  public int handle;
  int x;
  int y;
  int hBrush;
  int hwndCB;
  int id0;
  int id1;
  int foreground = -1;
  int background = -1;
  Image backgroundImage;
  boolean hasLocation;
  MenuItem cascade;
  Decorations parent;
  ImageList imageList;
  static final int ID_PPC = 100;
  static final int ID_SPMM = 102;
  static final int ID_SPBM = 103;
  static final int ID_SPMB = 104;
  static final int ID_SPBB = 105;
  static final int ID_SPSOFTKEY0 = 106;
  static final int ID_SPSOFTKEY1 = 107;

  public Menu(Control paramControl)
  {
    this(checkNull(paramControl).menuShell(), 8);
  }

  public Menu(Decorations paramDecorations, int paramInt)
  {
    this(paramDecorations, checkStyle(paramInt), 0);
  }

  public Menu(Menu paramMenu)
  {
    this(checkNull(paramMenu).parent, 4);
  }

  public Menu(MenuItem paramMenuItem)
  {
    this(checkNull(paramMenuItem).parent);
  }

  Menu(Decorations paramDecorations, int paramInt1, int paramInt2)
  {
    super(paramDecorations, checkStyle(paramInt1));
    this.parent = paramDecorations;
    this.handle = paramInt2;
    checkOrientation(paramDecorations);
    createWidget();
  }

  void _setVisible(boolean paramBoolean)
  {
    if ((this.style & 0x6) != 0)
      return;
    int i = this.parent.handle;
    if (paramBoolean)
    {
      int j = 0;
      if (OS.GetKeyState(1) >= 0)
        j |= 2;
      if ((this.style & 0x4000000) != 0)
        j |= 8;
      if ((this.parent.style & 0x8000000) != 0)
      {
        j &= -9;
        if ((this.style & 0x2000000) != 0)
          j |= 8;
      }
      int k = this.x;
      int m = this.y;
      if (!this.hasLocation)
      {
        int n = OS.GetMessagePos();
        k = OS.GET_X_LPARAM(n);
        m = OS.GET_Y_LPARAM(n);
      }
      boolean bool = OS.TrackPopupMenu(this.handle, j, k, m, 0, i, null);
      if ((!bool) && (GetMenuItemCount(this.handle) == 0))
        OS.SendMessage(i, 287, OS.MAKEWPARAM(0, 65535), 0);
    }
    else
    {
      OS.SendMessage(i, 31, 0, 0);
    }
  }

  public void addHelpListener(HelpListener paramHelpListener)
  {
    checkWidget();
    if (paramHelpListener == null)
      error(4);
    TypedListener localTypedListener = new TypedListener(paramHelpListener);
    addListener(28, localTypedListener);
  }

  public void addMenuListener(MenuListener paramMenuListener)
  {
    checkWidget();
    if (paramMenuListener == null)
      error(4);
    TypedListener localTypedListener = new TypedListener(paramMenuListener);
    addListener(23, localTypedListener);
    addListener(22, localTypedListener);
  }

  static Control checkNull(Control paramControl)
  {
    if (paramControl == null)
      SWT.error(4);
    return paramControl;
  }

  static Menu checkNull(Menu paramMenu)
  {
    if (paramMenu == null)
      SWT.error(4);
    return paramMenu;
  }

  static MenuItem checkNull(MenuItem paramMenuItem)
  {
    if (paramMenuItem == null)
      SWT.error(4);
    return paramMenuItem;
  }

  static int checkStyle(int paramInt)
  {
    return checkBits(paramInt, 8, 2, 4, 0, 0, 0);
  }

  void createHandle()
  {
    if (this.handle != 0)
      return;
    if ((this.style & 0x2) != 0)
    {
      int i;
      SHMENUBARINFO localSHMENUBARINFO;
      if (OS.IsPPC)
      {
        i = this.parent.handle;
        localSHMENUBARINFO = new SHMENUBARINFO();
        localSHMENUBARINFO.cbSize = SHMENUBARINFO.sizeof;
        localSHMENUBARINFO.hwndParent = i;
        localSHMENUBARINFO.dwFlags = 2;
        localSHMENUBARINFO.nToolBarId = 100;
        localSHMENUBARINFO.hInstRes = OS.GetLibraryHandle();
        boolean bool = OS.SHCreateMenuBar(localSHMENUBARINFO);
        this.hwndCB = localSHMENUBARINFO.hwndMB;
        if (!bool)
          error(2);
        OS.SendMessage(this.hwndCB, 1046, 0, 0);
        return;
      }
      if (OS.IsSP)
      {
        if ((this.style & 0x80000) != 0)
          i = (this.style & 0x100000) != 0 ? 105 : 103;
        else
          i = (this.style & 0x100000) != 0 ? 104 : 102;
        localSHMENUBARINFO = new SHMENUBARINFO();
        localSHMENUBARINFO.cbSize = SHMENUBARINFO.sizeof;
        localSHMENUBARINFO.hwndParent = this.parent.handle;
        localSHMENUBARINFO.dwFlags = 2;
        localSHMENUBARINFO.nToolBarId = i;
        localSHMENUBARINFO.hInstRes = OS.GetLibraryHandle();
        if (!OS.SHCreateMenuBar(localSHMENUBARINFO))
          error(2);
        this.hwndCB = localSHMENUBARINFO.hwndMB;
        OS.ShowWindow(this.hwndCB, 0);
        TBBUTTONINFO localTBBUTTONINFO = new TBBUTTONINFO();
        localTBBUTTONINFO.cbSize = TBBUTTONINFO.sizeof;
        localTBBUTTONINFO.dwMask = 32;
        Menu localMenu;
        MenuItem localMenuItem;
        if ((i == 102) || (i == 104))
        {
          j = OS.SendMessage(this.hwndCB, 1425, 0, 106);
          OS.RemoveMenu(j, 0, 1024);
          localMenu = new Menu(this.parent, 4, j);
          localMenuItem = new MenuItem(this, localMenu, 64, 0);
        }
        else
        {
          localMenuItem = new MenuItem(this, null, 8, 0);
        }
        localTBBUTTONINFO.idCommand = (this.id0 = localMenuItem.id);
        OS.SendMessage(this.hwndCB, OS.TB_SETBUTTONINFO, 106, localTBBUTTONINFO);
        if ((i == 102) || (i == 103))
        {
          j = OS.SendMessage(this.hwndCB, 1425, 0, 107);
          OS.RemoveMenu(j, 0, 1024);
          localMenu = new Menu(this.parent, 4, j);
          localMenuItem = new MenuItem(this, localMenu, 64, 1);
        }
        else
        {
          localMenuItem = new MenuItem(this, null, 8, 1);
        }
        localTBBUTTONINFO.idCommand = (this.id1 = localMenuItem.id);
        OS.SendMessage(this.hwndCB, OS.TB_SETBUTTONINFO, 107, localTBBUTTONINFO);
        int j = 3;
        int k = OS.MAKELPARAM(j, j);
        OS.SendMessage(this.hwndCB, 1427, 27, k);
        return;
      }
      this.handle = OS.CreateMenu();
      if (this.handle == 0)
        error(2);
      if (OS.IsHPC)
      {
        i = this.parent.handle;
        this.hwndCB = OS.CommandBar_Create(OS.GetModuleHandle(null), i, 1);
        if (this.hwndCB == 0)
          error(2);
        OS.CommandBar_Show(this.hwndCB, false);
        OS.CommandBar_InsertMenubarEx(this.hwndCB, 0, this.handle, 0);
        if (((this.parent.style & 0x40) != 0) && ((this.parent.style & 0x20) == 0))
          OS.CommandBar_AddAdornments(this.hwndCB, 0, 0);
      }
    }
    else
    {
      this.handle = OS.CreatePopupMenu();
      if (this.handle == 0)
        error(2);
    }
  }

  void createItem(MenuItem paramMenuItem, int paramInt)
  {
    int i = GetMenuItemCount(this.handle);
    if ((paramInt < 0) || (paramInt > i))
      error(6);
    this.display.addMenuItem(paramMenuItem);
    boolean bool = false;
    if (((OS.IsPPC) || (OS.IsSP)) && (this.hwndCB != 0))
    {
      if (OS.IsSP)
        return;
      TBBUTTON localTBBUTTON = new TBBUTTON();
      localTBBUTTON.idCommand = paramMenuItem.id;
      localTBBUTTON.fsStyle = 16;
      if ((paramMenuItem.style & 0x40) != 0)
      {
        TBBUTTON tmp98_96 = localTBBUTTON;
        tmp98_96.fsStyle = ((byte)(tmp98_96.fsStyle | 0x88));
      }
      if ((paramMenuItem.style & 0x2) != 0)
        localTBBUTTON.fsStyle = 1;
      localTBBUTTON.fsState = 4;
      localTBBUTTON.iBitmap = -2;
      bool = OS.SendMessage(this.hwndCB, OS.TB_INSERTBUTTON, paramInt, localTBBUTTON) != 0;
    }
    else
    {
      int j;
      TCHAR localTCHAR;
      if (OS.IsWinCE)
      {
        j = 1024;
        localTCHAR = null;
        if ((paramMenuItem.style & 0x2) != 0)
          j |= 2048;
        else
          localTCHAR = new TCHAR(0, " ", true);
        bool = OS.InsertMenu(this.handle, paramInt, j, paramMenuItem.id, localTCHAR);
        if (bool)
        {
          MENUITEMINFO localMENUITEMINFO1 = new MENUITEMINFO();
          localMENUITEMINFO1.cbSize = MENUITEMINFO.sizeof;
          localMENUITEMINFO1.fMask = 32;
          localMENUITEMINFO1.dwItemData = paramMenuItem.id;
          bool = OS.SetMenuItemInfo(this.handle, paramInt, true, localMENUITEMINFO1);
        }
      }
      else
      {
        j = OS.GetProcessHeap();
        localTCHAR = new TCHAR(0, " ", true);
        int k = localTCHAR.length() * TCHAR.sizeof;
        int m = OS.HeapAlloc(j, 8, k);
        OS.MoveMemory(m, localTCHAR, k);
        MENUITEMINFO localMENUITEMINFO2 = new MENUITEMINFO();
        localMENUITEMINFO2.cbSize = MENUITEMINFO.sizeof;
        localMENUITEMINFO2.fMask = 50;
        localMENUITEMINFO2.wID = paramMenuItem.id;
        localMENUITEMINFO2.dwItemData = paramMenuItem.id;
        localMENUITEMINFO2.fType = paramMenuItem.widgetStyle();
        localMENUITEMINFO2.dwTypeData = m;
        bool = OS.InsertMenuItem(this.handle, paramInt, true, localMENUITEMINFO2);
        if (m != 0)
          OS.HeapFree(j, 0, m);
      }
    }
    if (!bool)
    {
      this.display.removeMenuItem(paramMenuItem);
      error(14);
    }
    redraw();
  }

  void createWidget()
  {
    createHandle();
    this.parent.addMenu(this);
  }

  int defaultBackground()
  {
    return OS.GetSysColor(OS.COLOR_MENU);
  }

  int defaultForeground()
  {
    return OS.GetSysColor(OS.COLOR_MENUTEXT);
  }

  void destroyAccelerators()
  {
    this.parent.destroyAccelerators();
  }

  void destroyItem(MenuItem paramMenuItem)
  {
    if (OS.IsWinCE)
    {
      int i;
      if (((OS.IsPPC) || (OS.IsSP)) && (this.hwndCB != 0))
      {
        if (OS.IsSP)
        {
          redraw();
          return;
        }
        i = OS.SendMessage(this.hwndCB, 1049, paramMenuItem.id, 0);
        if (OS.SendMessage(this.hwndCB, 1046, i, 0) == 0)
          error(15);
        int j = OS.SendMessage(this.hwndCB, 1048, 0, 0);
        if ((j == 0) && (this.imageList != null))
        {
          OS.SendMessage(this.handle, 1072, 0, 0);
          this.display.releaseImageList(this.imageList);
          this.imageList = null;
        }
      }
      else
      {
        i = 0;
        MENUITEMINFO localMENUITEMINFO = new MENUITEMINFO();
        localMENUITEMINFO.cbSize = MENUITEMINFO.sizeof;
        localMENUITEMINFO.fMask = 32;
        while (OS.GetMenuItemInfo(this.handle, i, true, localMENUITEMINFO))
        {
          if (localMENUITEMINFO.dwItemData == paramMenuItem.id)
            break;
          i++;
        }
        if (localMENUITEMINFO.dwItemData != paramMenuItem.id)
          error(15);
        if (!OS.DeleteMenu(this.handle, i, 1024))
          error(15);
      }
    }
    else if (!OS.DeleteMenu(this.handle, paramMenuItem.id, 0))
    {
      error(15);
    }
    redraw();
  }

  void destroyWidget()
  {
    MenuItem localMenuItem = this.cascade;
    int i = this.handle;
    int j = this.hwndCB;
    releaseHandle();
    if ((OS.IsWinCE) && (j != 0))
      OS.CommandBar_Destroy(j);
    else if (localMenuItem != null)
    {
      if (!OS.IsSP)
        localMenuItem.setMenu(null, true);
    }
    else if (i != 0)
      OS.DestroyMenu(i);
  }

  void fixMenus(Decorations paramDecorations)
  {
    MenuItem[] arrayOfMenuItem = getItems();
    for (int i = 0; i < arrayOfMenuItem.length; i++)
      arrayOfMenuItem[i].fixMenus(paramDecorations);
    this.parent.removeMenu(this);
    paramDecorations.addMenu(this);
    this.parent = paramDecorations;
  }

  Color getBackground()
  {
    checkWidget();
    return Color.win32_new(this.display, this.background != -1 ? this.background : defaultBackground());
  }

  Image getBackgroundImage()
  {
    checkWidget();
    return this.backgroundImage;
  }

  Rectangle getBounds()
  {
    checkWidget();
    if (OS.IsWinCE)
      return new Rectangle(0, 0, 0, 0);
    int i;
    Object localObject;
    int k;
    if ((this.style & 0x2) != 0)
    {
      if (this.parent.menuBar != this)
        return new Rectangle(0, 0, 0, 0);
      i = this.parent.handle;
      localObject = new MENUBARINFO();
      ((MENUBARINFO)localObject).cbSize = MENUBARINFO.sizeof;
      if (OS.GetMenuBarInfo(i, -3, 0, (MENUBARINFO)localObject))
      {
        int j = ((MENUBARINFO)localObject).right - ((MENUBARINFO)localObject).left;
        k = ((MENUBARINFO)localObject).bottom - ((MENUBARINFO)localObject).top;
        return new Rectangle(((MENUBARINFO)localObject).left, ((MENUBARINFO)localObject).top, j, k);
      }
    }
    else
    {
      i = GetMenuItemCount(this.handle);
      if (i != 0)
      {
        localObject = new RECT();
        if (OS.GetMenuItemRect(0, this.handle, 0, (RECT)localObject))
        {
          RECT localRECT = new RECT();
          if (OS.GetMenuItemRect(0, this.handle, i - 1, localRECT))
          {
            k = ((RECT)localObject).left - 2;
            int m = ((RECT)localObject).top - 2;
            int n = localRECT.right - localRECT.left + 4;
            int i1 = localRECT.bottom - ((RECT)localObject).top + 4;
            return new Rectangle(k, m, n, i1);
          }
        }
      }
    }
    return new Rectangle(0, 0, 0, 0);
  }

  public MenuItem getDefaultItem()
  {
    checkWidget();
    if (OS.IsWinCE)
      return null;
    int i = OS.GetMenuDefaultItem(this.handle, 0, 1);
    if (i == -1)
      return null;
    MENUITEMINFO localMENUITEMINFO = new MENUITEMINFO();
    localMENUITEMINFO.cbSize = MENUITEMINFO.sizeof;
    localMENUITEMINFO.fMask = 2;
    if (OS.GetMenuItemInfo(this.handle, i, false, localMENUITEMINFO))
      return this.display.getMenuItem(localMENUITEMINFO.wID);
    return null;
  }

  public boolean getEnabled()
  {
    checkWidget();
    return (this.state & 0x8) == 0;
  }

  Color getForeground()
  {
    checkWidget();
    return Color.win32_new(this.display, this.foreground != -1 ? this.foreground : defaultForeground());
  }

  public MenuItem getItem(int paramInt)
  {
    checkWidget();
    int i = 0;
    Object localObject;
    if (((OS.IsPPC) || (OS.IsSP)) && (this.hwndCB != 0))
    {
      if (OS.IsPPC)
      {
        localObject = new TBBUTTON();
        int j = OS.SendMessage(this.hwndCB, 1047, paramInt, (TBBUTTON)localObject);
        if (j == 0)
          error(8);
        i = ((TBBUTTON)localObject).idCommand;
      }
      if (OS.IsSP)
      {
        if ((paramInt < 0) || (paramInt > 1))
          error(8);
        i = paramInt == 0 ? this.id0 : this.id1;
      }
    }
    else
    {
      localObject = new MENUITEMINFO();
      ((MENUITEMINFO)localObject).cbSize = MENUITEMINFO.sizeof;
      ((MENUITEMINFO)localObject).fMask = 32;
      if (!OS.GetMenuItemInfo(this.handle, paramInt, true, (MENUITEMINFO)localObject))
        error(6);
      i = ((MENUITEMINFO)localObject).dwItemData;
    }
    return this.display.getMenuItem(i);
  }

  public int getItemCount()
  {
    checkWidget();
    return GetMenuItemCount(this.handle);
  }

  public MenuItem[] getItems()
  {
    checkWidget();
    if (((OS.IsPPC) || (OS.IsSP)) && (this.hwndCB != 0))
    {
      if (OS.IsSP)
      {
        MenuItem[] arrayOfMenuItem1 = new MenuItem[2];
        arrayOfMenuItem1[0] = this.display.getMenuItem(this.id0);
        arrayOfMenuItem1[1] = this.display.getMenuItem(this.id1);
        return arrayOfMenuItem1;
      }
      i = OS.SendMessage(this.hwndCB, 1048, 0, 0);
      TBBUTTON localTBBUTTON = new TBBUTTON();
      MenuItem[] arrayOfMenuItem2 = new MenuItem[i];
      for (int m = 0; m < i; m++)
      {
        OS.SendMessage(this.hwndCB, 1047, m, localTBBUTTON);
        arrayOfMenuItem2[m] = this.display.getMenuItem(localTBBUTTON.idCommand);
      }
      return arrayOfMenuItem2;
    }
    int i = 0;
    int j = 0;
    int k = OS.IsWinCE ? 4 : OS.GetMenuItemCount(this.handle);
    Object localObject1 = new MenuItem[k];
    MENUITEMINFO localMENUITEMINFO = new MENUITEMINFO();
    localMENUITEMINFO.cbSize = MENUITEMINFO.sizeof;
    localMENUITEMINFO.fMask = 32;
    while (OS.GetMenuItemInfo(this.handle, i, true, localMENUITEMINFO))
    {
      if (j == localObject1.length)
      {
        localObject2 = new MenuItem[j + 4];
        System.arraycopy(localObject1, 0, localObject2, 0, j);
        localObject1 = localObject2;
      }
      localObject2 = this.display.getMenuItem(localMENUITEMINFO.dwItemData);
      if (localObject2 != null)
        localObject1[(j++)] = localObject2;
      i++;
    }
    if (j == localObject1.length)
      return localObject1;
    Object localObject2 = new MenuItem[j];
    System.arraycopy(localObject1, 0, localObject2, 0, j);
    return localObject2;
  }

  int GetMenuItemCount(int paramInt)
  {
    if (OS.IsWinCE)
    {
      if (((OS.IsPPC) || (OS.IsSP)) && (this.hwndCB != 0))
        return OS.IsSP ? 2 : OS.SendMessage(this.hwndCB, 1048, 0, 0);
      int i = 0;
      MENUITEMINFO localMENUITEMINFO = new MENUITEMINFO();
      localMENUITEMINFO.cbSize = MENUITEMINFO.sizeof;
      while (OS.GetMenuItemInfo(paramInt, i, true, localMENUITEMINFO))
        i++;
      return i;
    }
    return OS.GetMenuItemCount(paramInt);
  }

  String getNameText()
  {
    String str = "";
    MenuItem[] arrayOfMenuItem = getItems();
    int i = arrayOfMenuItem.length;
    if (i > 0)
    {
      for (int j = 0; j < i - 1; j++)
        str = str + arrayOfMenuItem[j].getNameText() + ", ";
      str = str + arrayOfMenuItem[(i - 1)].getNameText();
    }
    return str;
  }

  public Decorations getParent()
  {
    checkWidget();
    return this.parent;
  }

  public MenuItem getParentItem()
  {
    checkWidget();
    return this.cascade;
  }

  public Menu getParentMenu()
  {
    checkWidget();
    if (this.cascade != null)
      return this.cascade.parent;
    return null;
  }

  public Shell getShell()
  {
    checkWidget();
    return this.parent.getShell();
  }

  public boolean getVisible()
  {
    checkWidget();
    if ((this.style & 0x2) != 0)
      return this == this.parent.menuShell().menuBar;
    if ((this.style & 0x8) != 0)
    {
      localObject = this.display.popups;
      if (localObject == null)
        return false;
      for (int i = 0; i < localObject.length; i++)
        if (localObject[i] == this)
          return true;
    }
    Object localObject = getShell();
    for (Menu localMenu = ((Shell)localObject).activeMenu; (localMenu != null) && (localMenu != this); localMenu = localMenu.getParentMenu());
    return this == localMenu;
  }

  int imageIndex(Image paramImage)
  {
    if ((this.hwndCB == 0) || (paramImage == null))
      return -2;
    if (this.imageList == null)
    {
      Rectangle localRectangle = paramImage.getBounds();
      this.imageList = this.display.getImageList(this.style & 0x4000000, localRectangle.width, localRectangle.height);
      int j = this.imageList.add(paramImage);
      int k = this.imageList.getHandle();
      OS.SendMessage(this.hwndCB, 1072, 0, k);
      return j;
    }
    int i = this.imageList.indexOf(paramImage);
    if (i == -1)
      i = this.imageList.add(paramImage);
    else
      this.imageList.put(i, paramImage);
    return i;
  }

  public int indexOf(MenuItem paramMenuItem)
  {
    checkWidget();
    if (paramMenuItem == null)
      error(4);
    if (paramMenuItem.isDisposed())
      error(5);
    if (paramMenuItem.parent != this)
      return -1;
    if (((OS.IsPPC) || (OS.IsSP)) && (this.hwndCB != 0))
    {
      if (OS.IsPPC)
        return OS.SendMessage(this.hwndCB, 1049, paramMenuItem.id, 0);
      if (OS.IsSP)
      {
        if (paramMenuItem.id == this.id0)
          return 0;
        if (paramMenuItem.id == this.id1)
          return 1;
        return -1;
      }
    }
    int i = 0;
    MENUITEMINFO localMENUITEMINFO = new MENUITEMINFO();
    localMENUITEMINFO.cbSize = MENUITEMINFO.sizeof;
    localMENUITEMINFO.fMask = 32;
    while (OS.GetMenuItemInfo(this.handle, i, true, localMENUITEMINFO))
    {
      if (localMENUITEMINFO.dwItemData == paramMenuItem.id)
        return i;
      i++;
    }
    return -1;
  }

  public boolean isEnabled()
  {
    checkWidget();
    Menu localMenu = getParentMenu();
    if (localMenu == null)
      return (getEnabled()) && (this.parent.isEnabled());
    return (getEnabled()) && (localMenu.isEnabled());
  }

  public boolean isVisible()
  {
    checkWidget();
    return getVisible();
  }

  void redraw()
  {
    if (!isVisible())
      return;
    if ((this.style & 0x2) != 0)
      this.display.addBar(this);
    else
      update();
  }

  void releaseHandle()
  {
    super.releaseHandle();
    this.handle = (this.hwndCB = 0);
    this.cascade = null;
  }

  void releaseChildren(boolean paramBoolean)
  {
    MenuItem[] arrayOfMenuItem = getItems();
    for (int i = 0; i < arrayOfMenuItem.length; i++)
    {
      MenuItem localMenuItem = arrayOfMenuItem[i];
      if ((localMenuItem != null) && (!localMenuItem.isDisposed()))
        if ((OS.IsPPC) && (this.hwndCB != 0))
          localMenuItem.dispose();
        else
          localMenuItem.release(false);
    }
    super.releaseChildren(paramBoolean);
  }

  void releaseParent()
  {
    super.releaseParent();
    if ((this.style & 0x2) != 0)
    {
      this.display.removeBar(this);
      if (this == this.parent.menuBar)
        this.parent.setMenuBar(null);
    }
    else if ((this.style & 0x8) != 0)
    {
      this.display.removePopup(this);
    }
  }

  void releaseWidget()
  {
    super.releaseWidget();
    this.backgroundImage = null;
    if (this.hBrush == 0)
      OS.DeleteObject(this.hBrush);
    this.hBrush = 0;
    if ((OS.IsPPC) && (this.hwndCB != 0) && (this.imageList != null))
    {
      OS.SendMessage(this.hwndCB, 1072, 0, 0);
      this.display.releaseToolImageList(this.imageList);
      this.imageList = null;
    }
    if (this.parent != null)
      this.parent.removeMenu(this);
    this.parent = null;
  }

  public void removeHelpListener(HelpListener paramHelpListener)
  {
    checkWidget();
    if (paramHelpListener == null)
      error(4);
    if (this.eventTable == null)
      return;
    this.eventTable.unhook(28, paramHelpListener);
  }

  public void removeMenuListener(MenuListener paramMenuListener)
  {
    checkWidget();
    if (paramMenuListener == null)
      error(4);
    if (this.eventTable == null)
      return;
    this.eventTable.unhook(23, paramMenuListener);
    this.eventTable.unhook(22, paramMenuListener);
  }

  void reskinChildren(int paramInt)
  {
    MenuItem[] arrayOfMenuItem = getItems();
    for (int i = 0; i < arrayOfMenuItem.length; i++)
    {
      MenuItem localMenuItem = arrayOfMenuItem[i];
      localMenuItem.reskin(paramInt);
    }
    super.reskinChildren(paramInt);
  }

  void setBackground(Color paramColor)
  {
    checkWidget();
    int i = -1;
    if (paramColor != null)
    {
      if (paramColor.isDisposed())
        SWT.error(5);
      i = paramColor.handle;
    }
    if (i == this.background)
      return;
    this.background = i;
    updateBackground();
  }

  void setBackgroundImage(Image paramImage)
  {
    checkWidget();
    if (paramImage != null)
    {
      if (paramImage.isDisposed())
        error(5);
      if (paramImage.type != 0)
        error(5);
    }
    if (this.backgroundImage == paramImage)
      return;
    this.backgroundImage = paramImage;
    updateBackground();
  }

  void setForeground(Color paramColor)
  {
    checkWidget();
    int i = -1;
    if (paramColor != null)
    {
      if (paramColor.isDisposed())
        SWT.error(5);
      i = paramColor.handle;
    }
    if (i == this.foreground)
      return;
    this.foreground = i;
    updateForeground();
  }

  public void setDefaultItem(MenuItem paramMenuItem)
  {
    checkWidget();
    int i = -1;
    if (paramMenuItem != null)
    {
      if (paramMenuItem.isDisposed())
        error(5);
      if (paramMenuItem.parent != this)
        return;
      i = paramMenuItem.id;
    }
    if (OS.IsWinCE)
      return;
    int j = OS.GetMenuDefaultItem(this.handle, 0, 1);
    if (i == j)
      return;
    OS.SetMenuDefaultItem(this.handle, i, 0);
    redraw();
  }

  public void setEnabled(boolean paramBoolean)
  {
    checkWidget();
    this.state &= -9;
    if (!paramBoolean)
      this.state |= 8;
  }

  public void setLocation(int paramInt1, int paramInt2)
  {
    checkWidget();
    if ((this.style & 0x6) != 0)
      return;
    this.x = paramInt1;
    this.y = paramInt2;
    this.hasLocation = true;
  }

  public void setLocation(Point paramPoint)
  {
    checkWidget();
    if (paramPoint == null)
      SWT.error(4);
    setLocation(paramPoint.x, paramPoint.y);
  }

  public void setVisible(boolean paramBoolean)
  {
    checkWidget();
    if ((this.style & 0x6) != 0)
      return;
    if (paramBoolean)
    {
      this.display.addPopup(this);
    }
    else
    {
      this.display.removePopup(this);
      _setVisible(false);
    }
  }

  void update()
  {
    if ((OS.IsPPC) || (OS.IsSP))
      return;
    if (OS.IsHPC)
    {
      Menu localMenu1 = this.parent.menuBar;
      if (localMenu1 != null)
      {
        for (Menu localMenu2 = this; (localMenu2 != null) && (localMenu2 != localMenu1); localMenu2 = localMenu2.getParentMenu());
        if (localMenu2 == localMenu1)
        {
          OS.CommandBar_DrawMenuBar(localMenu1.hwndCB, 0);
          OS.CommandBar_Show(localMenu1.hwndCB, true);
        }
      }
      return;
    }
    if (OS.IsWinCE)
      return;
    if ((this.style & 0x2) != 0)
    {
      if (this == this.parent.menuBar)
        OS.DrawMenuBar(this.parent.handle);
      return;
    }
    if (OS.WIN32_VERSION < OS.VERSION(4, 10))
      return;
    int i = 0;
    int j = 0;
    MenuItem[] arrayOfMenuItem = getItems();
    for (int k = 0; k < arrayOfMenuItem.length; k++)
    {
      MenuItem localMenuItem1 = arrayOfMenuItem[k];
      if (((localMenuItem1.image != null) && ((j = 1) != 0) && (i != 0)) || (((localMenuItem1.style & 0x30) != 0) && ((i = 1) != 0) && (j != 0)))
        break;
    }
    if ((!OS.IsWin95) && (OS.WIN32_VERSION < OS.VERSION(6, 0)))
    {
      localObject = new MENUITEMINFO();
      ((MENUITEMINFO)localObject).cbSize = MENUITEMINFO.sizeof;
      ((MENUITEMINFO)localObject).fMask = 128;
      for (int m = 0; m < arrayOfMenuItem.length; m++)
      {
        MenuItem localMenuItem2 = arrayOfMenuItem[m];
        if (((this.style & 0x2) == 0) && ((localMenuItem2.image == null) || (this.foreground != -1)))
        {
          ((MENUITEMINFO)localObject).hbmpItem = ((j != 0) || (this.foreground != -1) ? -1 : 0);
          OS.SetMenuItemInfo(this.handle, localMenuItem2.id, false, (MENUITEMINFO)localObject);
        }
      }
    }
    Object localObject = new MENUINFO();
    ((MENUINFO)localObject).cbSize = MENUINFO.sizeof;
    ((MENUINFO)localObject).fMask = 16;
    OS.GetMenuInfo(this.handle, (MENUINFO)localObject);
    if ((j != 0) && (i == 0))
      localObject.dwStyle |= 67108864;
    else
      localObject.dwStyle &= -67108865;
    OS.SetMenuInfo(this.handle, (MENUINFO)localObject);
  }

  void updateBackground()
  {
    if (this.hBrush == 0)
      OS.DeleteObject(this.hBrush);
    this.hBrush = 0;
    if (this.backgroundImage != null)
      this.hBrush = OS.CreatePatternBrush(this.backgroundImage.handle);
    else if (this.background != -1)
      this.hBrush = OS.CreateSolidBrush(this.background);
    MENUINFO localMENUINFO = new MENUINFO();
    localMENUINFO.cbSize = MENUINFO.sizeof;
    localMENUINFO.fMask = 2;
    localMENUINFO.hbrBack = this.hBrush;
    OS.SetMenuInfo(this.handle, localMENUINFO);
  }

  void updateForeground()
  {
    if (OS.WIN32_VERSION < OS.VERSION(4, 10))
      return;
    MENUITEMINFO localMENUITEMINFO = new MENUITEMINFO();
    localMENUITEMINFO.cbSize = MENUITEMINFO.sizeof;
    for (int i = 0; OS.GetMenuItemInfo(this.handle, i, true, localMENUITEMINFO); i++)
    {
      localMENUITEMINFO.fMask = 128;
      localMENUITEMINFO.hbmpItem = -1;
      OS.SetMenuItemInfo(this.handle, i, true, localMENUITEMINFO);
    }
    redraw();
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.Menu
 * JD-Core Version:    0.6.2
 */