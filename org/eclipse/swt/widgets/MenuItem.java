package org.eclipse.swt.widgets;

import org.eclipse.swt.events.ArmListener;
import org.eclipse.swt.events.HelpListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.GCData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.win32.ACCEL;
import org.eclipse.swt.internal.win32.DRAWITEMSTRUCT;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.MEASUREITEMSTRUCT;
import org.eclipse.swt.internal.win32.MENUBARINFO;
import org.eclipse.swt.internal.win32.MENUINFO;
import org.eclipse.swt.internal.win32.MENUITEMINFO;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.TBBUTTONINFO;
import org.eclipse.swt.internal.win32.TCHAR;

public class MenuItem extends Item
{
  Menu parent;
  Menu menu;
  int hBitmap;
  int id;
  int accelerator;
  static final int MARGIN_WIDTH = OS.IsWin95 ? 2 : 1;
  static final int MARGIN_HEIGHT = OS.IsWin95 ? 2 : 1;

  public MenuItem(Menu paramMenu, int paramInt)
  {
    super(paramMenu, checkStyle(paramInt));
    this.parent = paramMenu;
    paramMenu.createItem(this, paramMenu.getItemCount());
  }

  public MenuItem(Menu paramMenu, int paramInt1, int paramInt2)
  {
    super(paramMenu, checkStyle(paramInt1));
    this.parent = paramMenu;
    paramMenu.createItem(this, paramInt2);
  }

  MenuItem(Menu paramMenu1, Menu paramMenu2, int paramInt1, int paramInt2)
  {
    super(paramMenu1, checkStyle(paramInt1));
    this.parent = paramMenu1;
    this.menu = paramMenu2;
    if (paramMenu2 != null)
      paramMenu2.cascade = this;
    this.display.addMenuItem(this);
  }

  public void addArmListener(ArmListener paramArmListener)
  {
    checkWidget();
    if (paramArmListener == null)
      error(4);
    TypedListener localTypedListener = new TypedListener(paramArmListener);
    addListener(30, localTypedListener);
  }

  public void addHelpListener(HelpListener paramHelpListener)
  {
    checkWidget();
    if (paramHelpListener == null)
      error(4);
    TypedListener localTypedListener = new TypedListener(paramHelpListener);
    addListener(28, localTypedListener);
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

  protected void checkSubclass()
  {
    if (!isValidSubclass())
      error(43);
  }

  static int checkStyle(int paramInt)
  {
    return checkBits(paramInt, 8, 32, 16, 2, 64, 0);
  }

  void destroyWidget()
  {
    this.parent.destroyItem(this);
    releaseHandle();
  }

  boolean fillAccel(ACCEL paramACCEL)
  {
    paramACCEL.cmd = (paramACCEL.key = paramACCEL.fVirt = 0);
    if ((this.accelerator == 0) || (!getEnabled()))
      return false;
    if ((this.accelerator & 0x400000) != 0)
      return false;
    int i = 1;
    int j = this.accelerator & 0x100FFFF;
    int k = Display.untranslateKey(j);
    if (k != 0)
      j = k;
    else
      switch (j)
      {
      case 27:
        j = 27;
        break;
      case 127:
        j = 46;
        break;
      default:
        j = Display.wcsToMbcs((char)j);
        if (j == 0)
          return false;
        if (OS.IsWinCE)
        {
          j = OS.CharUpper((short)j);
        }
        else
        {
          k = OS.VkKeyScan((short)j) & 0xFF;
          if (k == -1)
            i = 0;
          else
            j = k;
        }
        break;
      }
    paramACCEL.key = ((short)j);
    paramACCEL.cmd = ((short)this.id);
    paramACCEL.fVirt = ((byte)i);
    if ((this.accelerator & 0x10000) != 0)
    {
      ACCEL tmp192_191 = paramACCEL;
      tmp192_191.fVirt = ((byte)(tmp192_191.fVirt | 0x10));
    }
    if ((this.accelerator & 0x20000) != 0)
    {
      ACCEL tmp214_213 = paramACCEL;
      tmp214_213.fVirt = ((byte)(tmp214_213.fVirt | 0x4));
    }
    if ((this.accelerator & 0x40000) != 0)
    {
      ACCEL tmp235_234 = paramACCEL;
      tmp235_234.fVirt = ((byte)(tmp235_234.fVirt | 0x8));
    }
    return true;
  }

  void fixMenus(Decorations paramDecorations)
  {
    if (this.menu != null)
      this.menu.fixMenus(paramDecorations);
  }

  public int getAccelerator()
  {
    checkWidget();
    return this.accelerator;
  }

  Rectangle getBounds()
  {
    checkWidget();
    if (OS.IsWinCE)
      return new Rectangle(0, 0, 0, 0);
    int i = this.parent.indexOf(this);
    if (i == -1)
      return new Rectangle(0, 0, 0, 0);
    if ((this.parent.style & 0x2) != 0)
    {
      Decorations localDecorations = this.parent.parent;
      if (localDecorations.menuBar != this.parent)
        return new Rectangle(0, 0, 0, 0);
      int k = localDecorations.handle;
      localObject = new MENUBARINFO();
      ((MENUBARINFO)localObject).cbSize = MENUBARINFO.sizeof;
      if (!OS.GetMenuBarInfo(k, -3, 1, (MENUBARINFO)localObject))
        return new Rectangle(0, 0, 0, 0);
      MENUBARINFO localMENUBARINFO = new MENUBARINFO();
      localMENUBARINFO.cbSize = MENUBARINFO.sizeof;
      if (!OS.GetMenuBarInfo(k, -3, i + 1, localMENUBARINFO))
        return new Rectangle(0, 0, 0, 0);
      n = localMENUBARINFO.left - ((MENUBARINFO)localObject).left;
      i1 = localMENUBARINFO.top - ((MENUBARINFO)localObject).top;
      i2 = localMENUBARINFO.right - localMENUBARINFO.left;
      int i3 = localMENUBARINFO.bottom - localMENUBARINFO.top;
      return new Rectangle(n, i1, i2, i3);
    }
    int j = this.parent.handle;
    RECT localRECT = new RECT();
    if (!OS.GetMenuItemRect(0, j, 0, localRECT))
      return new Rectangle(0, 0, 0, 0);
    Object localObject = new RECT();
    if (!OS.GetMenuItemRect(0, j, i, (RECT)localObject))
      return new Rectangle(0, 0, 0, 0);
    int m = ((RECT)localObject).left - localRECT.left + 2;
    int n = ((RECT)localObject).top - localRECT.top + 2;
    int i1 = ((RECT)localObject).right - ((RECT)localObject).left;
    int i2 = ((RECT)localObject).bottom - ((RECT)localObject).top;
    return new Rectangle(m, n, i1, i2);
  }

  public boolean getEnabled()
  {
    checkWidget();
    if (((OS.IsPPC) || (OS.IsSP)) && (this.parent.hwndCB != 0))
    {
      i = this.parent.hwndCB;
      localObject = new TBBUTTONINFO();
      ((TBBUTTONINFO)localObject).cbSize = TBBUTTONINFO.sizeof;
      ((TBBUTTONINFO)localObject).dwMask = 4;
      OS.SendMessage(i, OS.TB_GETBUTTONINFO, this.id, (TBBUTTONINFO)localObject);
      return (((TBBUTTONINFO)localObject).fsState & 0x4) != 0;
    }
    if ((this.style & 0x2) != 0)
      return (this.state & 0x8) == 0;
    int i = this.parent.handle;
    Object localObject = new MENUITEMINFO();
    ((MENUITEMINFO)localObject).cbSize = MENUITEMINFO.sizeof;
    ((MENUITEMINFO)localObject).fMask = 1;
    boolean bool;
    if (OS.IsWinCE)
    {
      int j = this.parent.indexOf(this);
      if (j == -1)
        error(31);
      bool = OS.GetMenuItemInfo(i, j, true, (MENUITEMINFO)localObject);
    }
    else
    {
      bool = OS.GetMenuItemInfo(i, this.id, false, (MENUITEMINFO)localObject);
    }
    if (!bool)
      error(31);
    return (((MENUITEMINFO)localObject).fState & 0x3) == 0;
  }

  public Menu getMenu()
  {
    checkWidget();
    return this.menu;
  }

  String getNameText()
  {
    if ((this.style & 0x2) != 0)
      return "|";
    return super.getNameText();
  }

  public Menu getParent()
  {
    checkWidget();
    return this.parent;
  }

  public boolean getSelection()
  {
    checkWidget();
    if ((this.style & 0x30) == 0)
      return false;
    if (((OS.IsPPC) || (OS.IsSP)) && (this.parent.hwndCB != 0))
      return false;
    int i = this.parent.handle;
    MENUITEMINFO localMENUITEMINFO = new MENUITEMINFO();
    localMENUITEMINFO.cbSize = MENUITEMINFO.sizeof;
    localMENUITEMINFO.fMask = 1;
    boolean bool = OS.GetMenuItemInfo(i, this.id, false, localMENUITEMINFO);
    if (!bool)
      error(9);
    return (localMENUITEMINFO.fState & 0x8) != 0;
  }

  public boolean isEnabled()
  {
    return (getEnabled()) && (this.parent.isEnabled());
  }

  void releaseChildren(boolean paramBoolean)
  {
    if (this.menu != null)
    {
      this.menu.release(false);
      this.menu = null;
    }
    super.releaseChildren(paramBoolean);
  }

  void releaseHandle()
  {
    super.releaseHandle();
    this.parent = null;
    this.id = -1;
  }

  void releaseParent()
  {
    super.releaseParent();
    if (this.menu != null)
      this.menu.dispose();
    this.menu = null;
  }

  void releaseWidget()
  {
    super.releaseWidget();
    if (this.hBitmap != 0)
      OS.DeleteObject(this.hBitmap);
    this.hBitmap = 0;
    if (this.accelerator != 0)
      this.parent.destroyAccelerators();
    this.accelerator = 0;
    this.display.removeMenuItem(this);
  }

  public void removeArmListener(ArmListener paramArmListener)
  {
    checkWidget();
    if (paramArmListener == null)
      error(4);
    if (this.eventTable == null)
      return;
    this.eventTable.unhook(30, paramArmListener);
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

  void reskinChildren(int paramInt)
  {
    if (this.menu != null)
      this.menu.reskin(paramInt);
    super.reskinChildren(paramInt);
  }

  void selectRadio()
  {
    int i = 0;
    MenuItem[] arrayOfMenuItem = this.parent.getItems();
    while ((i < arrayOfMenuItem.length) && (arrayOfMenuItem[i] != this))
      i++;
    for (int j = i - 1; (j >= 0) && (arrayOfMenuItem[j].setRadioSelection(false)); j--);
    for (int k = i + 1; (k < arrayOfMenuItem.length) && (arrayOfMenuItem[k].setRadioSelection(false)); k++);
    setSelection(true);
  }

  public void setAccelerator(int paramInt)
  {
    checkWidget();
    if (this.accelerator == paramInt)
      return;
    this.accelerator = paramInt;
    this.parent.destroyAccelerators();
  }

  public void setEnabled(boolean paramBoolean)
  {
    checkWidget();
    int i;
    if (((OS.IsPPC) || (OS.IsSP)) && (this.parent.hwndCB != 0))
    {
      i = this.parent.hwndCB;
      TBBUTTONINFO localTBBUTTONINFO = new TBBUTTONINFO();
      localTBBUTTONINFO.cbSize = TBBUTTONINFO.sizeof;
      localTBBUTTONINFO.dwMask = 4;
      OS.SendMessage(i, OS.TB_GETBUTTONINFO, this.id, localTBBUTTONINFO);
      TBBUTTONINFO tmp68_67 = localTBBUTTONINFO;
      tmp68_67.fsState = ((byte)(tmp68_67.fsState & 0xFFFFFFFB));
      if (paramBoolean)
      {
        TBBUTTONINFO tmp84_83 = localTBBUTTONINFO;
        tmp84_83.fsState = ((byte)(tmp84_83.fsState | 0x4));
      }
      OS.SendMessage(i, OS.TB_SETBUTTONINFO, this.id, localTBBUTTONINFO);
    }
    else
    {
      if ((this.style & 0x2) != 0)
        if (paramBoolean)
          this.state &= -9;
        else
          this.state |= 8;
      i = this.parent.handle;
      if (OS.IsWinCE)
      {
        int j = this.parent.indexOf(this);
        if (j == -1)
          return;
        int k = 0x400 | (paramBoolean ? 0 : 1);
        OS.EnableMenuItem(i, j, k);
      }
      else
      {
        MENUITEMINFO localMENUITEMINFO = new MENUITEMINFO();
        localMENUITEMINFO.cbSize = MENUITEMINFO.sizeof;
        localMENUITEMINFO.fMask = 1;
        boolean bool = OS.GetMenuItemInfo(i, this.id, false, localMENUITEMINFO);
        if (!bool)
          error(30);
        int m = 3;
        if (paramBoolean)
        {
          if ((localMENUITEMINFO.fState & m) == 0)
            return;
          localMENUITEMINFO.fState &= (m ^ 0xFFFFFFFF);
        }
        else
        {
          if ((localMENUITEMINFO.fState & m) == m)
            return;
          localMENUITEMINFO.fState |= m;
        }
        bool = OS.SetMenuItemInfo(i, this.id, false, localMENUITEMINFO);
        if (!bool)
        {
          if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(6, 0)))
            bool = this.id == OS.GetMenuDefaultItem(i, 0, 1);
          if (!bool)
            error(30);
        }
      }
    }
    this.parent.destroyAccelerators();
    this.parent.redraw();
  }

  public void setImage(Image paramImage)
  {
    checkWidget();
    if ((this.style & 0x2) != 0)
      return;
    super.setImage(paramImage);
    if (OS.IsWinCE)
    {
      if (((OS.IsPPC) || (OS.IsSP)) && (this.parent.hwndCB != 0))
      {
        int i = this.parent.hwndCB;
        TBBUTTONINFO localTBBUTTONINFO = new TBBUTTONINFO();
        localTBBUTTONINFO.cbSize = TBBUTTONINFO.sizeof;
        localTBBUTTONINFO.dwMask = 1;
        localTBBUTTONINFO.iImage = this.parent.imageIndex(paramImage);
        OS.SendMessage(i, OS.TB_SETBUTTONINFO, this.id, localTBBUTTONINFO);
      }
      return;
    }
    if (OS.WIN32_VERSION < OS.VERSION(4, 10))
      return;
    MENUITEMINFO localMENUITEMINFO = new MENUITEMINFO();
    localMENUITEMINFO.cbSize = MENUITEMINFO.sizeof;
    localMENUITEMINFO.fMask = 128;
    if (this.parent.foreground != -1)
    {
      localMENUITEMINFO.hbmpItem = -1;
    }
    else if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(6, 0)))
    {
      if (this.hBitmap != 0)
        OS.DeleteObject(this.hBitmap);
      localMENUITEMINFO.hbmpItem = (this.hBitmap = paramImage != null ? Display.create32bitDIB(paramImage) : 0);
    }
    else
    {
      localMENUITEMINFO.hbmpItem = (paramImage != null ? -1 : 0);
    }
    int j = this.parent.handle;
    OS.SetMenuItemInfo(j, this.id, false, localMENUITEMINFO);
    this.parent.redraw();
  }

  public void setMenu(Menu paramMenu)
  {
    checkWidget();
    if ((this.style & 0x40) == 0)
      error(27);
    if (paramMenu != null)
    {
      if (paramMenu.isDisposed())
        error(5);
      if ((paramMenu.style & 0x4) == 0)
        error(21);
      if (paramMenu.parent != this.parent.parent)
        error(32);
    }
    setMenu(paramMenu, false);
  }

  void setMenu(Menu paramMenu, boolean paramBoolean)
  {
    Menu localMenu = this.menu;
    if (localMenu == paramMenu)
      return;
    if (localMenu != null)
      localMenu.cascade = null;
    this.menu = paramMenu;
    int i;
    if (((OS.IsPPC) || (OS.IsSP)) && (this.parent.hwndCB != 0))
    {
      if (OS.IsPPC)
      {
        i = this.parent.hwndCB;
        int j = paramMenu == null ? 0 : paramMenu.handle;
        OS.SendMessage(i, 1424, this.id, j);
      }
      if (OS.IsSP)
        error(29);
    }
    else
    {
      i = this.parent.handle;
      MENUITEMINFO localMENUITEMINFO = new MENUITEMINFO();
      localMENUITEMINFO.cbSize = MENUITEMINFO.sizeof;
      localMENUITEMINFO.fMask = 32;
      for (int k = 0; OS.GetMenuItemInfo(i, k, true, localMENUITEMINFO); k++)
        if (localMENUITEMINFO.dwItemData == this.id)
          break;
      if (localMENUITEMINFO.dwItemData != this.id)
        return;
      int m = 128;
      int n = OS.GetProcessHeap();
      int i1 = m * TCHAR.sizeof;
      int i2 = OS.HeapAlloc(n, 8, i1);
      localMENUITEMINFO.fMask = 35;
      if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(4, 10)))
        localMENUITEMINFO.fMask |= 192;
      else
        localMENUITEMINFO.fMask |= 16;
      localMENUITEMINFO.dwTypeData = i2;
      localMENUITEMINFO.cch = m;
      boolean bool = OS.GetMenuItemInfo(i, k, true, localMENUITEMINFO);
      if (paramMenu != null)
      {
        paramMenu.cascade = this;
        localMENUITEMINFO.fMask |= 4;
        localMENUITEMINFO.hSubMenu = paramMenu.handle;
      }
      if (OS.IsWinCE)
      {
        OS.RemoveMenu(i, k, 1024);
        int i3 = this.id;
        int i4 = 1024;
        if (paramMenu != null)
        {
          i4 |= 16;
          i3 = paramMenu.handle;
        }
        TCHAR localTCHAR = new TCHAR(0, " ", true);
        bool = OS.InsertMenu(i, k, i4, i3, localTCHAR);
        if (bool)
        {
          localMENUITEMINFO.fMask = 48;
          bool = OS.SetMenuItemInfo(i, k, true, localMENUITEMINFO);
          if ((localMENUITEMINFO.fState & 0x3) != 0)
            OS.EnableMenuItem(i, k, 1025);
          if ((localMENUITEMINFO.fState & 0x8) != 0)
            OS.CheckMenuItem(i, k, 1032);
        }
      }
      else if ((paramBoolean) || (localMenu == null))
      {
        bool = OS.SetMenuItemInfo(i, k, true, localMENUITEMINFO);
      }
      else
      {
        OS.RemoveMenu(i, k, 1024);
        bool = OS.InsertMenuItem(i, k, true, localMENUITEMINFO);
      }
      if (i2 != 0)
        OS.HeapFree(n, 0, i2);
      if (!bool)
        error(29);
    }
    this.parent.destroyAccelerators();
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

  public void setSelection(boolean paramBoolean)
  {
    checkWidget();
    if ((this.style & 0x30) == 0)
      return;
    if (((OS.IsPPC) || (OS.IsSP)) && (this.parent.hwndCB != 0))
      return;
    int i = this.parent.handle;
    if (OS.IsWinCE)
    {
      int j = this.parent.indexOf(this);
      if (j == -1)
        return;
      int k = 0x400 | (paramBoolean ? 8 : 0);
      OS.CheckMenuItem(i, j, k);
    }
    else
    {
      MENUITEMINFO localMENUITEMINFO = new MENUITEMINFO();
      localMENUITEMINFO.cbSize = MENUITEMINFO.sizeof;
      localMENUITEMINFO.fMask = 1;
      boolean bool = OS.GetMenuItemInfo(i, this.id, false, localMENUITEMINFO);
      if (!bool)
        error(28);
      localMENUITEMINFO.fState &= -9;
      if (paramBoolean)
        localMENUITEMINFO.fState |= 8;
      bool = OS.SetMenuItemInfo(i, this.id, false, localMENUITEMINFO);
      if (!bool)
      {
        if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(6, 0)))
          bool = this.id == OS.GetMenuDefaultItem(i, 0, 1);
        if (!bool)
          error(28);
      }
    }
    this.parent.redraw();
  }

  public void setText(String paramString)
  {
    checkWidget();
    if (paramString == null)
      error(4);
    if ((this.style & 0x2) != 0)
      return;
    if (this.text.equals(paramString))
      return;
    super.setText(paramString);
    int i = OS.GetProcessHeap();
    int j = 0;
    boolean bool = false;
    Object localObject;
    int m;
    if (((OS.IsPPC) || (OS.IsSP)) && (this.parent.hwndCB != 0))
    {
      if (paramString.indexOf('&') != -1)
      {
        int k = paramString.length();
        char[] arrayOfChar = new char[k];
        paramString.getChars(0, k, arrayOfChar, 0);
        n = 0;
        int i1 = 0;
        for (n = 0; n < k; n++)
          if (arrayOfChar[n] != '&')
            arrayOfChar[(i1++)] = arrayOfChar[n];
        if (i1 < n)
          paramString = new String(arrayOfChar, 0, i1);
      }
      localObject = new TCHAR(0, paramString, true);
      m = ((TCHAR)localObject).length() * TCHAR.sizeof;
      j = OS.HeapAlloc(i, 8, m);
      OS.MoveMemory(j, (TCHAR)localObject, m);
      int n = this.parent.hwndCB;
      TBBUTTONINFO localTBBUTTONINFO = new TBBUTTONINFO();
      localTBBUTTONINFO.cbSize = TBBUTTONINFO.sizeof;
      localTBBUTTONINFO.dwMask = 2;
      localTBBUTTONINFO.pszText = j;
      bool = OS.SendMessage(n, OS.TB_SETBUTTONINFO, this.id, localTBBUTTONINFO) != 0;
    }
    else
    {
      localObject = new MENUITEMINFO();
      ((MENUITEMINFO)localObject).cbSize = MENUITEMINFO.sizeof;
      m = this.parent.handle;
      TCHAR localTCHAR = new TCHAR(0, paramString, true);
      int i2 = localTCHAR.length() * TCHAR.sizeof;
      j = OS.HeapAlloc(i, 8, i2);
      OS.MoveMemory(j, localTCHAR, i2);
      if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(4, 10)))
      {
        ((MENUITEMINFO)localObject).fMask = 64;
      }
      else
      {
        ((MENUITEMINFO)localObject).fMask = 16;
        ((MENUITEMINFO)localObject).fType = widgetStyle();
      }
      ((MENUITEMINFO)localObject).dwTypeData = j;
      bool = OS.SetMenuItemInfo(m, this.id, false, (MENUITEMINFO)localObject);
    }
    if (j != 0)
      OS.HeapFree(i, 0, j);
    if (!bool)
      error(13);
    this.parent.redraw();
  }

  int widgetStyle()
  {
    int i = 0;
    Decorations localDecorations = this.parent.parent;
    if ((localDecorations.style & 0x8000000) != 0)
    {
      if ((this.parent.style & 0x2000000) != 0)
        i |= 24576;
    }
    else if ((this.parent.style & 0x4000000) != 0)
      i |= 24576;
    if ((this.style & 0x2) != 0)
      return i | 0x800;
    if ((this.style & 0x10) != 0)
      return i | 0x200;
    return i;
  }

  LRESULT wmCommandChild(int paramInt1, int paramInt2)
  {
    if ((this.style & 0x20) != 0)
      setSelection(!getSelection());
    else if ((this.style & 0x10) != 0)
      if ((this.parent.getStyle() & 0x400000) != 0)
        setSelection(!getSelection());
      else
        selectRadio();
    sendSelectionEvent(13);
    return null;
  }

  LRESULT wmDrawChild(int paramInt1, int paramInt2)
  {
    DRAWITEMSTRUCT localDRAWITEMSTRUCT = new DRAWITEMSTRUCT();
    OS.MoveMemory(localDRAWITEMSTRUCT, paramInt2, DRAWITEMSTRUCT.sizeof);
    if (this.image != null)
    {
      GCData localGCData = new GCData();
      localGCData.device = this.display;
      GC localGC = GC.win32_new(localDRAWITEMSTRUCT.hDC, localGCData);
      int i = (this.parent.style & 0x2) != 0 ? MARGIN_WIDTH * 2 : localDRAWITEMSTRUCT.left;
      Image localImage = getEnabled() ? this.image : new Image(this.display, this.image, 1);
      localGC.drawImage(localImage, i, localDRAWITEMSTRUCT.top + MARGIN_HEIGHT);
      if (this.image != localImage)
        localImage.dispose();
      localGC.dispose();
    }
    if (this.parent.foreground != -1)
      OS.SetTextColor(localDRAWITEMSTRUCT.hDC, this.parent.foreground);
    return null;
  }

  LRESULT wmMeasureChild(int paramInt1, int paramInt2)
  {
    MEASUREITEMSTRUCT localMEASUREITEMSTRUCT = new MEASUREITEMSTRUCT();
    OS.MoveMemory(localMEASUREITEMSTRUCT, paramInt2, MEASUREITEMSTRUCT.sizeof);
    int i = 0;
    int j = 0;
    Object localObject;
    if (this.image != null)
    {
      localObject = this.image.getBounds();
      i = ((Rectangle)localObject).width;
      j = ((Rectangle)localObject).height;
    }
    else
    {
      localObject = new MENUINFO();
      ((MENUINFO)localObject).cbSize = MENUINFO.sizeof;
      ((MENUINFO)localObject).fMask = 16;
      int k = this.parent.handle;
      OS.GetMenuInfo(k, (MENUINFO)localObject);
      if ((((MENUINFO)localObject).dwStyle & 0x4000000) == 0)
      {
        MenuItem[] arrayOfMenuItem = this.parent.getItems();
        for (int m = 0; m < arrayOfMenuItem.length; m++)
        {
          MenuItem localMenuItem = arrayOfMenuItem[m];
          if (localMenuItem.image != null)
          {
            Rectangle localRectangle = localMenuItem.image.getBounds();
            i = Math.max(i, localRectangle.width);
          }
        }
      }
    }
    if ((i != 0) || (j != 0))
    {
      localMEASUREITEMSTRUCT.itemWidth = (i + MARGIN_WIDTH * 2);
      localMEASUREITEMSTRUCT.itemHeight = (j + MARGIN_HEIGHT * 2);
      OS.MoveMemory(paramInt2, localMEASUREITEMSTRUCT, MEASUREITEMSTRUCT.sizeof);
    }
    return null;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.MenuItem
 * JD-Core Version:    0.6.2
 */