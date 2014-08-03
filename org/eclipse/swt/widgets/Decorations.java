package org.eclipse.swt.widgets;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.win32.ACCEL;
import org.eclipse.swt.internal.win32.CREATESTRUCT;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.MENUITEMINFO;
import org.eclipse.swt.internal.win32.MSG;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.STARTUPINFO;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.WINDOWPLACEMENT;
import org.eclipse.swt.internal.win32.WINDOWPOS;

public class Decorations extends Canvas
{
  Image image;
  Image smallImage;
  Image largeImage;
  Image[] images;
  Menu menuBar;
  Menu[] menus;
  Control savedFocus;
  Button defaultButton;
  Button saveDefault;
  int swFlags;
  int nAccel;
  int hAccel;
  boolean moved;
  boolean resized;
  boolean opened;
  int oldX = -2147483648;
  int oldY = -2147483648;
  int oldWidth = -2147483648;
  int oldHeight = -2147483648;

  Decorations()
  {
  }

  public Decorations(Composite paramComposite, int paramInt)
  {
    super(paramComposite, checkStyle(paramInt));
  }

  void _setMaximized(boolean paramBoolean)
  {
    this.swFlags = (paramBoolean ? OS.SW_SHOWMAXIMIZED : OS.SW_RESTORE);
    if (OS.IsWinCE)
    {
      if (paramBoolean)
      {
        RECT localRECT1 = new RECT();
        OS.SystemParametersInfo(48, 0, localRECT1, 0);
        int i = localRECT1.right - localRECT1.left;
        int j = localRECT1.bottom - localRECT1.top;
        if ((OS.IsPPC) && (this.menuBar != null))
        {
          k = this.menuBar.hwndCB;
          RECT localRECT2 = new RECT();
          OS.GetWindowRect(k, localRECT2);
          j -= localRECT2.bottom - localRECT2.top;
        }
        int k = 52;
        SetWindowPos(this.handle, 0, localRECT1.left, localRECT1.top, i, j, k);
      }
    }
    else
    {
      if (!OS.IsWindowVisible(this.handle))
        return;
      if (paramBoolean == OS.IsZoomed(this.handle))
        return;
      OS.ShowWindow(this.handle, this.swFlags);
      OS.UpdateWindow(this.handle);
    }
  }

  void _setMinimized(boolean paramBoolean)
  {
    if (OS.IsWinCE)
      return;
    this.swFlags = (paramBoolean ? 7 : OS.SW_RESTORE);
    if (!OS.IsWindowVisible(this.handle))
      return;
    if (paramBoolean == OS.IsIconic(this.handle))
      return;
    int i = this.swFlags;
    if ((i == 7) && (this.handle == OS.GetActiveWindow()))
      i = 6;
    OS.ShowWindow(this.handle, i);
    OS.UpdateWindow(this.handle);
  }

  void addMenu(Menu paramMenu)
  {
    if (this.menus == null)
      this.menus = new Menu[4];
    for (int i = 0; i < this.menus.length; i++)
      if (this.menus[i] == null)
      {
        this.menus[i] = paramMenu;
        return;
      }
    Menu[] arrayOfMenu = new Menu[this.menus.length + 4];
    arrayOfMenu[this.menus.length] = paramMenu;
    System.arraycopy(this.menus, 0, arrayOfMenu, 0, this.menus.length);
    this.menus = arrayOfMenu;
  }

  void bringToTop()
  {
    OS.BringWindowToTop(this.handle);
  }

  static int checkStyle(int paramInt)
  {
    if ((paramInt & 0x8) != 0)
      paramInt &= -3313;
    if (OS.IsWinCE)
    {
      if ((paramInt & 0x80) != 0)
        paramInt &= -129;
      if ((paramInt & 0x400) != 0)
        paramInt &= -1025;
      return paramInt;
    }
    if ((paramInt & 0x4C0) != 0)
      paramInt |= 32;
    if ((paramInt & 0x480) != 0)
      paramInt |= 64;
    if ((paramInt & 0x40) != 0)
      paramInt |= 32;
    return paramInt;
  }

  void checkBorder()
  {
  }

  void checkComposited(Composite paramComposite)
  {
  }

  void checkOpened()
  {
    if (!this.opened)
      this.resized = false;
  }

  protected void checkSubclass()
  {
    if (!isValidSubclass())
      error(43);
  }

  int callWindowProc(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.handle == 0)
      return 0;
    return OS.DefMDIChildProc(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  void closeWidget()
  {
    Event localEvent = new Event();
    sendEvent(21, localEvent);
    if ((localEvent.doit) && (!isDisposed()))
      dispose();
  }

  int compare(ImageData paramImageData1, ImageData paramImageData2, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = Math.abs(paramImageData1.width - paramInt1);
    int j = Math.abs(paramImageData2.width - paramInt1);
    if (i == j)
    {
      int k = paramImageData1.getTransparencyType();
      int m = paramImageData2.getTransparencyType();
      if (k == m)
      {
        if (paramImageData1.depth == paramImageData2.depth)
          return 0;
        return (paramImageData1.depth > paramImageData2.depth) && (paramImageData1.depth <= paramInt3) ? -1 : 1;
      }
      if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(5, 1)))
      {
        if (k == 1)
          return -1;
        if (m == 1)
          return 1;
      }
      if (k == 2)
        return -1;
      if (m == 2)
        return 1;
      if (k == 4)
        return -1;
      if (m == 4)
        return 1;
      return 0;
    }
    return i < j ? -1 : 1;
  }

  Widget computeTabGroup()
  {
    return this;
  }

  Control computeTabRoot()
  {
    return this;
  }

  public Rectangle computeTrim(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    checkWidget();
    RECT localRECT1 = new RECT();
    OS.SetRect(localRECT1, paramInt1, paramInt2, paramInt1 + paramInt3, paramInt2 + paramInt4);
    int i = OS.GetWindowLong(this.handle, -16);
    int j = OS.GetWindowLong(this.handle, -20);
    boolean bool = !OS.IsWinCE;
    OS.AdjustWindowRectEx(localRECT1, i, bool, j);
    if (this.horizontalBar != null)
      localRECT1.bottom += OS.GetSystemMetrics(3);
    if (this.verticalBar != null)
      localRECT1.right += OS.GetSystemMetrics(2);
    if (bool)
    {
      RECT localRECT2 = new RECT();
      OS.SetRect(localRECT2, 0, 0, localRECT1.right - localRECT1.left, localRECT1.bottom - localRECT1.top);
      OS.SendMessage(this.handle, 131, 0, localRECT2);
      while (localRECT2.bottom - localRECT2.top < paramInt4)
      {
        if (localRECT2.bottom - localRECT2.top == 0)
          break;
        localRECT1.top -= OS.GetSystemMetrics(15) - OS.GetSystemMetrics(6);
        OS.SetRect(localRECT2, 0, 0, localRECT1.right - localRECT1.left, localRECT1.bottom - localRECT1.top);
        OS.SendMessage(this.handle, 131, 0, localRECT2);
      }
    }
    return new Rectangle(localRECT1.left, localRECT1.top, localRECT1.right - localRECT1.left, localRECT1.bottom - localRECT1.top);
  }

  void createAccelerators()
  {
    this.hAccel = (this.nAccel = 0);
    int i = 0;
    MenuItem[] arrayOfMenuItem = this.display.items;
    if ((this.menuBar == null) || (arrayOfMenuItem == null))
    {
      if (!OS.IsPPC)
        return;
      i = 1;
    }
    else
    {
      i = OS.IsPPC ? arrayOfMenuItem.length + 1 : arrayOfMenuItem.length;
    }
    ACCEL localACCEL = new ACCEL();
    byte[] arrayOfByte1 = new byte[ACCEL.sizeof];
    byte[] arrayOfByte2 = new byte[i * ACCEL.sizeof];
    if ((this.menuBar != null) && (arrayOfMenuItem != null))
      for (int j = 0; j < arrayOfMenuItem.length; j++)
      {
        MenuItem localMenuItem = arrayOfMenuItem[j];
        if ((localMenuItem != null) && (localMenuItem.accelerator != 0))
        {
          Menu localMenu = localMenuItem.parent;
          if (localMenu.parent == this)
          {
            while ((localMenu != null) && (localMenu != this.menuBar))
              localMenu = localMenu.getParentMenu();
            if ((localMenu == this.menuBar) && (localMenuItem.fillAccel(localACCEL)))
            {
              OS.MoveMemory(arrayOfByte1, localACCEL, ACCEL.sizeof);
              System.arraycopy(arrayOfByte1, 0, arrayOfByte2, this.nAccel * ACCEL.sizeof, ACCEL.sizeof);
              this.nAccel += 1;
            }
          }
        }
      }
    if (OS.IsPPC)
    {
      localACCEL.fVirt = 9;
      localACCEL.key = 81;
      localACCEL.cmd = 1;
      OS.MoveMemory(arrayOfByte1, localACCEL, ACCEL.sizeof);
      System.arraycopy(arrayOfByte1, 0, arrayOfByte2, this.nAccel * ACCEL.sizeof, ACCEL.sizeof);
      this.nAccel += 1;
    }
    if (this.nAccel != 0)
      this.hAccel = OS.CreateAcceleratorTable(arrayOfByte2, this.nAccel);
  }

  void createHandle()
  {
    super.createHandle();
    if ((this.parent != null) || ((this.style & 0x4) != 0))
    {
      setParent();
      setSystemMenu();
    }
  }

  void createWidget()
  {
    super.createWidget();
    this.swFlags = (OS.IsWinCE ? OS.SW_SHOWMAXIMIZED : 4);
    this.hAccel = -1;
  }

  void destroyAccelerators()
  {
    if ((this.hAccel != 0) && (this.hAccel != -1))
      OS.DestroyAcceleratorTable(this.hAccel);
    this.hAccel = -1;
  }

  public void dispose()
  {
    if (isDisposed())
      return;
    if (!isValidThread())
      error(22);
    if (!(this instanceof Shell))
    {
      if (!traverseDecorations(true))
      {
        Shell localShell = getShell();
        localShell.setFocus();
      }
      setVisible(false);
    }
    super.dispose();
  }

  Menu findMenu(int paramInt)
  {
    if (this.menus == null)
      return null;
    for (int i = 0; i < this.menus.length; i++)
    {
      Menu localMenu = this.menus[i];
      if ((localMenu != null) && (paramInt == localMenu.handle))
        return localMenu;
    }
    return null;
  }

  void fixDecorations(Decorations paramDecorations, Control paramControl, Menu[] paramArrayOfMenu)
  {
    if (this == paramDecorations)
      return;
    if (paramControl == this.savedFocus)
      this.savedFocus = null;
    if (paramControl == this.defaultButton)
      this.defaultButton = null;
    if (paramControl == this.saveDefault)
      this.saveDefault = null;
    if (paramArrayOfMenu == null)
      return;
    Menu localMenu = paramControl.menu;
    if (localMenu != null)
    {
      for (int i = 0; i < paramArrayOfMenu.length; i++)
        if (paramArrayOfMenu[i] == localMenu)
        {
          paramControl.setMenu(null);
          return;
        }
      localMenu.fixMenus(paramDecorations);
      destroyAccelerators();
      paramDecorations.destroyAccelerators();
    }
  }

  public Rectangle getBounds()
  {
    checkWidget();
    if ((!OS.IsWinCE) && (OS.IsIconic(this.handle)))
    {
      WINDOWPLACEMENT localWINDOWPLACEMENT = new WINDOWPLACEMENT();
      localWINDOWPLACEMENT.length = WINDOWPLACEMENT.sizeof;
      OS.GetWindowPlacement(this.handle, localWINDOWPLACEMENT);
      int i = localWINDOWPLACEMENT.right - localWINDOWPLACEMENT.left;
      int j = localWINDOWPLACEMENT.bottom - localWINDOWPLACEMENT.top;
      return new Rectangle(localWINDOWPLACEMENT.left, localWINDOWPLACEMENT.top, i, j);
    }
    return super.getBounds();
  }

  public Rectangle getClientArea()
  {
    checkWidget();
    Object localObject;
    int i;
    int j;
    if (OS.IsHPC)
    {
      localObject = super.getClientArea();
      if (this.menuBar != null)
      {
        i = this.menuBar.hwndCB;
        j = OS.CommandBar_Height(i);
        localObject.y += j;
        ((Rectangle)localObject).height = Math.max(0, ((Rectangle)localObject).height - j);
      }
      return localObject;
    }
    if ((!OS.IsWinCE) && (OS.IsIconic(this.handle)))
    {
      localObject = new WINDOWPLACEMENT();
      ((WINDOWPLACEMENT)localObject).length = WINDOWPLACEMENT.sizeof;
      OS.GetWindowPlacement(this.handle, (WINDOWPLACEMENT)localObject);
      i = ((WINDOWPLACEMENT)localObject).right - ((WINDOWPLACEMENT)localObject).left;
      j = ((WINDOWPLACEMENT)localObject).bottom - ((WINDOWPLACEMENT)localObject).top;
      if (this.horizontalBar != null)
        i -= OS.GetSystemMetrics(3);
      if (this.verticalBar != null)
        j -= OS.GetSystemMetrics(2);
      RECT localRECT = new RECT();
      int k = OS.GetWindowLong(this.handle, -16);
      int m = OS.GetWindowLong(this.handle, -20);
      boolean bool = !OS.IsWinCE;
      OS.AdjustWindowRectEx(localRECT, k, bool, m);
      i = Math.max(0, i - (localRECT.right - localRECT.left));
      j = Math.max(0, j - (localRECT.bottom - localRECT.top));
      return new Rectangle(0, 0, i, j);
    }
    return super.getClientArea();
  }

  public Button getDefaultButton()
  {
    checkWidget();
    if ((this.defaultButton != null) && (this.defaultButton.isDisposed()))
      return null;
    return this.defaultButton;
  }

  public Image getImage()
  {
    checkWidget();
    return this.image;
  }

  public Image[] getImages()
  {
    checkWidget();
    if (this.images == null)
      return new Image[0];
    Image[] arrayOfImage = new Image[this.images.length];
    System.arraycopy(this.images, 0, arrayOfImage, 0, this.images.length);
    return arrayOfImage;
  }

  public Point getLocation()
  {
    checkWidget();
    if ((!OS.IsWinCE) && (OS.IsIconic(this.handle)))
    {
      WINDOWPLACEMENT localWINDOWPLACEMENT = new WINDOWPLACEMENT();
      localWINDOWPLACEMENT.length = WINDOWPLACEMENT.sizeof;
      OS.GetWindowPlacement(this.handle, localWINDOWPLACEMENT);
      return new Point(localWINDOWPLACEMENT.left, localWINDOWPLACEMENT.top);
    }
    return super.getLocation();
  }

  public boolean getMaximized()
  {
    checkWidget();
    if (OS.IsWinCE)
      return this.swFlags == OS.SW_SHOWMAXIMIZED;
    if (OS.IsWindowVisible(this.handle))
      return OS.IsZoomed(this.handle);
    return this.swFlags == OS.SW_SHOWMAXIMIZED;
  }

  public Menu getMenuBar()
  {
    checkWidget();
    return this.menuBar;
  }

  public boolean getMinimized()
  {
    checkWidget();
    if (OS.IsWinCE)
      return false;
    if (OS.IsWindowVisible(this.handle))
      return OS.IsIconic(this.handle);
    return this.swFlags == 7;
  }

  String getNameText()
  {
    return getText();
  }

  public Point getSize()
  {
    checkWidget();
    if ((!OS.IsWinCE) && (OS.IsIconic(this.handle)))
    {
      WINDOWPLACEMENT localWINDOWPLACEMENT = new WINDOWPLACEMENT();
      localWINDOWPLACEMENT.length = WINDOWPLACEMENT.sizeof;
      OS.GetWindowPlacement(this.handle, localWINDOWPLACEMENT);
      int i = localWINDOWPLACEMENT.right - localWINDOWPLACEMENT.left;
      int j = localWINDOWPLACEMENT.bottom - localWINDOWPLACEMENT.top;
      return new Point(i, j);
    }
    return super.getSize();
  }

  public String getText()
  {
    checkWidget();
    int i = OS.GetWindowTextLength(this.handle);
    if (i == 0)
      return "";
    TCHAR localTCHAR = new TCHAR(0, i + 1);
    OS.GetWindowText(this.handle, localTCHAR, i + 1);
    return localTCHAR.toString(0, i);
  }

  public boolean isReparentable()
  {
    checkWidget();
    return false;
  }

  boolean isTabGroup()
  {
    return true;
  }

  boolean isTabItem()
  {
    return false;
  }

  Decorations menuShell()
  {
    return this;
  }

  void releaseChildren(boolean paramBoolean)
  {
    if (this.menuBar != null)
    {
      this.menuBar.release(false);
      this.menuBar = null;
    }
    super.releaseChildren(paramBoolean);
    if (this.menus != null)
    {
      for (int i = 0; i < this.menus.length; i++)
      {
        Menu localMenu = this.menus[i];
        if ((localMenu != null) && (!localMenu.isDisposed()))
          localMenu.dispose();
      }
      this.menus = null;
    }
  }

  void releaseWidget()
  {
    super.releaseWidget();
    if (this.smallImage != null)
      this.smallImage.dispose();
    if (this.largeImage != null)
      this.largeImage.dispose();
    this.smallImage = (this.largeImage = this.image = null);
    this.images = null;
    this.savedFocus = null;
    this.defaultButton = (this.saveDefault = null);
    if ((this.hAccel != 0) && (this.hAccel != -1))
      OS.DestroyAcceleratorTable(this.hAccel);
    this.hAccel = -1;
  }

  void removeMenu(Menu paramMenu)
  {
    if (this.menus == null)
      return;
    for (int i = 0; i < this.menus.length; i++)
      if (this.menus[i] == paramMenu)
      {
        this.menus[i] = null;
        return;
      }
  }

  void reskinChildren(int paramInt)
  {
    if (this.menuBar != null)
      this.menuBar.reskin(paramInt);
    if (this.menus != null)
      for (int i = 0; i < this.menus.length; i++)
      {
        Menu localMenu = this.menus[i];
        if (localMenu != null)
          localMenu.reskin(paramInt);
      }
    super.reskinChildren(paramInt);
  }

  boolean restoreFocus()
  {
    if (this.display.ignoreRestoreFocus)
      return true;
    if ((this.savedFocus != null) && (this.savedFocus.isDisposed()))
      this.savedFocus = null;
    return (this.savedFocus != null) && (this.savedFocus.setSavedFocus());
  }

  void saveFocus()
  {
    Control localControl = this.display._getFocusControl();
    if ((localControl != null) && (localControl != this) && (this == localControl.menuShell()))
      setSavedFocus(localControl);
  }

  void setBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, boolean paramBoolean)
  {
    this.swFlags = 4;
    if (OS.IsWinCE)
    {
      this.swFlags = OS.SW_RESTORE;
    }
    else if (OS.IsIconic(this.handle))
    {
      setPlacement(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
      return;
    }
    forceResize();
    RECT localRECT = new RECT();
    OS.GetWindowRect(this.handle, localRECT);
    int i = 1;
    if ((0x2 & paramInt5) == 0)
    {
      i = (localRECT.left == paramInt1) && (localRECT.top == paramInt2) ? 1 : 0;
      if (i == 0)
        this.moved = true;
    }
    int j = 1;
    if ((0x1 & paramInt5) == 0)
    {
      j = (localRECT.right - localRECT.left == paramInt3) && (localRECT.bottom - localRECT.top == paramInt4) ? 1 : 0;
      if (j == 0)
        this.resized = true;
    }
    if ((!OS.IsWinCE) && (OS.IsZoomed(this.handle)))
    {
      if ((i != 0) && (j != 0))
        return;
      setPlacement(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
      _setMaximized(false);
      return;
    }
    super.setBounds(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramBoolean);
  }

  public void setDefaultButton(Button paramButton)
  {
    checkWidget();
    if (paramButton != null)
    {
      if (paramButton.isDisposed())
        error(5);
      if (paramButton.menuShell() != this)
        error(32);
    }
    setDefaultButton(paramButton, true);
  }

  void setDefaultButton(Button paramButton, boolean paramBoolean)
  {
    if (paramButton == null)
    {
      if (this.defaultButton == this.saveDefault)
        if (paramBoolean)
          this.saveDefault = null;
    }
    else
    {
      if ((paramButton.style & 0x8) == 0)
        return;
      if (paramButton == this.defaultButton)
      {
        if (paramBoolean)
          this.saveDefault = this.defaultButton;
        return;
      }
    }
    if ((this.defaultButton != null) && (!this.defaultButton.isDisposed()))
      this.defaultButton.setDefault(false);
    if ((this.defaultButton = paramButton) == null)
      this.defaultButton = this.saveDefault;
    if ((this.defaultButton != null) && (!this.defaultButton.isDisposed()))
      this.defaultButton.setDefault(true);
    if (paramBoolean)
      this.saveDefault = this.defaultButton;
    if ((this.saveDefault != null) && (this.saveDefault.isDisposed()))
      this.saveDefault = null;
  }

  public void setImage(Image paramImage)
  {
    checkWidget();
    if ((paramImage != null) && (paramImage.isDisposed()))
      error(5);
    this.image = paramImage;
    setImages(paramImage, null);
  }

  void setImages(Image paramImage, Image[] paramArrayOfImage)
  {
    if (OS.IsWinCE)
      return;
    if (this.smallImage != null)
      this.smallImage.dispose();
    if (this.largeImage != null)
      this.largeImage.dispose();
    this.smallImage = (this.largeImage = null);
    int i = 0;
    int j = 0;
    Image localImage1 = null;
    Image localImage2 = null;
    int k;
    if (paramImage != null)
    {
      localImage1 = localImage2 = paramImage;
    }
    else if ((paramArrayOfImage != null) && (paramArrayOfImage.length > 0))
    {
      k = this.display.getIconDepth();
      ImageData[] arrayOfImageData = (ImageData[])null;
      if (paramArrayOfImage.length > 1)
      {
        Image[] arrayOfImage = new Image[paramArrayOfImage.length];
        System.arraycopy(paramArrayOfImage, 0, arrayOfImage, 0, paramArrayOfImage.length);
        arrayOfImageData = new ImageData[paramArrayOfImage.length];
        for (int m = 0; m < arrayOfImageData.length; m++)
          arrayOfImageData[m] = paramArrayOfImage[m].getImageData();
        paramArrayOfImage = arrayOfImage;
        sort(paramArrayOfImage, arrayOfImageData, OS.GetSystemMetrics(49), OS.GetSystemMetrics(50), k);
      }
      localImage1 = paramArrayOfImage[0];
      if (paramArrayOfImage.length > 1)
        sort(paramArrayOfImage, arrayOfImageData, OS.GetSystemMetrics(11), OS.GetSystemMetrics(12), k);
      localImage2 = paramArrayOfImage[0];
    }
    if (localImage1 != null)
      switch (localImage1.type)
      {
      case 0:
        this.smallImage = Display.createIcon(localImage1);
        i = this.smallImage.handle;
        break;
      case 1:
        i = localImage1.handle;
      }
    OS.SendMessage(this.handle, 128, 0, i);
    if (localImage2 != null)
      switch (localImage2.type)
      {
      case 0:
        this.largeImage = Display.createIcon(localImage2);
        j = this.largeImage.handle;
        break;
      case 1:
        j = localImage2.handle;
      }
    OS.SendMessage(this.handle, 128, 1, j);
    if ((!OS.IsWinCE) && (i == 0) && (j == 0) && ((this.style & 0x800) != 0))
    {
      k = 1025;
      OS.RedrawWindow(this.handle, null, 0, k);
    }
  }

  public void setImages(Image[] paramArrayOfImage)
  {
    checkWidget();
    if (paramArrayOfImage == null)
      error(5);
    for (int i = 0; i < paramArrayOfImage.length; i++)
      if ((paramArrayOfImage[i] == null) || (paramArrayOfImage[i].isDisposed()))
        error(5);
    this.images = paramArrayOfImage;
    setImages(null, paramArrayOfImage);
  }

  public void setMaximized(boolean paramBoolean)
  {
    checkWidget();
    Display.lpStartupInfo = null;
    _setMaximized(paramBoolean);
  }

  public void setMenuBar(Menu paramMenu)
  {
    checkWidget();
    if (this.menuBar == paramMenu)
      return;
    if (paramMenu != null)
    {
      if (paramMenu.isDisposed())
        error(5);
      if ((paramMenu.style & 0x2) == 0)
        error(33);
      if (paramMenu.parent != this)
        error(32);
    }
    int i;
    if (OS.IsWinCE)
    {
      if (OS.IsHPC)
      {
        i = this.menuBar != paramMenu ? 1 : 0;
        if (this.menuBar != null)
          OS.CommandBar_Show(this.menuBar.hwndCB, false);
        this.menuBar = paramMenu;
        if (this.menuBar != null)
          OS.CommandBar_Show(this.menuBar.hwndCB, true);
        if (i != 0)
        {
          sendEvent(11);
          if (isDisposed())
            return;
          if (this.layout != null)
          {
            markLayout(false, false);
            updateLayout(true, false);
          }
        }
      }
      else
      {
        if (OS.IsPPC)
        {
          i = (getMaximized()) && (this.menuBar != paramMenu) ? 1 : 0;
          if (this.menuBar != null)
            OS.ShowWindow(this.menuBar.hwndCB, 0);
          this.menuBar = paramMenu;
          if (this.menuBar != null)
            OS.ShowWindow(this.menuBar.hwndCB, 5);
          if (i != 0)
            _setMaximized(true);
        }
        if (OS.IsSP)
        {
          if (this.menuBar != null)
            OS.ShowWindow(this.menuBar.hwndCB, 0);
          this.menuBar = paramMenu;
          if (this.menuBar != null)
            OS.ShowWindow(this.menuBar.hwndCB, 5);
        }
      }
    }
    else
    {
      if (paramMenu != null)
        this.display.removeBar(paramMenu);
      this.menuBar = paramMenu;
      i = this.menuBar != null ? this.menuBar.handle : 0;
      OS.SetMenu(this.handle, i);
    }
    destroyAccelerators();
  }

  public void setMinimized(boolean paramBoolean)
  {
    checkWidget();
    Display.lpStartupInfo = null;
    _setMinimized(paramBoolean);
  }

  void setParent()
  {
    int i = this.parent.handle;
    this.display.lockActiveWindow = true;
    OS.SetParent(this.handle, i);
    if (!OS.IsWindowVisible(i))
      OS.ShowWindow(this.handle, 8);
    int j = OS.GetWindowLong(this.handle, -16);
    j &= -1073741825;
    OS.SetWindowLong(this.handle, -16, j | 0x80000000);
    OS.SetWindowLongPtr(this.handle, -12, 0);
    int k = 19;
    SetWindowPos(this.handle, 1, 0, 0, 0, 0, k);
    this.display.lockActiveWindow = false;
  }

  void setPlacement(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    WINDOWPLACEMENT localWINDOWPLACEMENT = new WINDOWPLACEMENT();
    localWINDOWPLACEMENT.length = WINDOWPLACEMENT.sizeof;
    OS.GetWindowPlacement(this.handle, localWINDOWPLACEMENT);
    localWINDOWPLACEMENT.showCmd = 8;
    if (OS.IsIconic(this.handle))
      localWINDOWPLACEMENT.showCmd = 7;
    else if (OS.IsZoomed(this.handle))
      localWINDOWPLACEMENT.showCmd = OS.SW_SHOWMAXIMIZED;
    int i = 1;
    if ((paramInt5 & 0x2) == 0)
    {
      i = (localWINDOWPLACEMENT.left == paramInt1) && (localWINDOWPLACEMENT.top == paramInt2) ? 0 : 1;
      localWINDOWPLACEMENT.right = (paramInt1 + (localWINDOWPLACEMENT.right - localWINDOWPLACEMENT.left));
      localWINDOWPLACEMENT.bottom = (paramInt2 + (localWINDOWPLACEMENT.bottom - localWINDOWPLACEMENT.top));
      localWINDOWPLACEMENT.left = paramInt1;
      localWINDOWPLACEMENT.top = paramInt2;
    }
    int j = 1;
    if ((paramInt5 & 0x1) == 0)
    {
      j = (localWINDOWPLACEMENT.right - localWINDOWPLACEMENT.left == paramInt3) && (localWINDOWPLACEMENT.bottom - localWINDOWPLACEMENT.top == paramInt4) ? 0 : 1;
      localWINDOWPLACEMENT.right = (localWINDOWPLACEMENT.left + paramInt3);
      localWINDOWPLACEMENT.bottom = (localWINDOWPLACEMENT.top + paramInt4);
    }
    OS.SetWindowPlacement(this.handle, localWINDOWPLACEMENT);
    if (OS.IsIconic(this.handle))
    {
      Object localObject;
      if (i != 0)
      {
        this.moved = true;
        localObject = getLocation();
        this.oldX = ((Point)localObject).x;
        this.oldY = ((Point)localObject).y;
        sendEvent(10);
        if (isDisposed())
          return;
      }
      if (j != 0)
      {
        this.resized = true;
        localObject = getClientArea();
        this.oldWidth = ((Rectangle)localObject).width;
        this.oldHeight = ((Rectangle)localObject).height;
        sendEvent(11);
        if (isDisposed())
          return;
        if (this.layout != null)
        {
          markLayout(false, false);
          updateLayout(true, false);
        }
      }
    }
  }

  void setSavedFocus(Control paramControl)
  {
    this.savedFocus = paramControl;
  }

  void setSystemMenu()
  {
    if (OS.IsWinCE)
      return;
    int i = OS.GetSystemMenu(this.handle, false);
    if (i == 0)
      return;
    int j = OS.GetMenuItemCount(i);
    if ((this.style & 0x10) == 0)
      OS.DeleteMenu(i, 61440, 0);
    if ((this.style & 0x80) == 0)
      OS.DeleteMenu(i, 61472, 0);
    if ((this.style & 0x400) == 0)
      OS.DeleteMenu(i, 61488, 0);
    if ((this.style & 0x480) == 0)
      OS.DeleteMenu(i, 61728, 0);
    int k = OS.GetMenuItemCount(i);
    if (((this.style & 0x40) == 0) || (k != j))
    {
      OS.DeleteMenu(i, 61744, 0);
      MENUITEMINFO localMENUITEMINFO = new MENUITEMINFO();
      localMENUITEMINFO.cbSize = MENUITEMINFO.sizeof;
      localMENUITEMINFO.fMask = 2;
      for (int m = 0; m < k; m++)
        if ((OS.GetMenuItemInfo(i, m, true, localMENUITEMINFO)) && (localMENUITEMINFO.wID == 61536))
          break;
      if (m != k)
      {
        OS.DeleteMenu(i, m - 1, 1024);
        if ((this.style & 0x40) == 0)
          OS.DeleteMenu(i, 61536, 0);
      }
    }
  }

  public void setText(String paramString)
  {
    checkWidget();
    if (paramString == null)
      error(4);
    TCHAR localTCHAR = new TCHAR(0, paramString, true);
    if ((this.state & 0x4000) != 0)
    {
      int i = OS.GetProcessHeap();
      int j = localTCHAR.length() * TCHAR.sizeof;
      int k = OS.HeapAlloc(i, 8, j);
      OS.MoveMemory(k, localTCHAR, j);
      OS.DefWindowProc(this.handle, 12, 0, k);
      if (k != 0)
        OS.HeapFree(i, 0, k);
    }
    else
    {
      OS.SetWindowText(this.handle, localTCHAR);
    }
  }

  public void setVisible(boolean paramBoolean)
  {
    checkWidget();
    if (!getDrawing())
    {
      if (((this.state & 0x10) == 0) != paramBoolean);
    }
    else if (paramBoolean == OS.IsWindowVisible(this.handle))
      return;
    if (paramBoolean)
    {
      sendEvent(22);
      if (isDisposed())
        return;
      if ((OS.IsHPC) && (this.menuBar != null))
      {
        int i = this.menuBar.hwndCB;
        OS.CommandBar_DrawMenuBar(i, 0);
      }
      if (!getDrawing())
      {
        this.state &= -17;
      }
      else
      {
        Object localObject;
        if (OS.IsWinCE)
        {
          OS.ShowWindow(this.handle, 5);
        }
        else
        {
          if (this.menuBar != null)
          {
            this.display.removeBar(this.menuBar);
            OS.DrawMenuBar(this.handle);
          }
          localObject = Display.lpStartupInfo;
          if ((localObject != null) && ((((STARTUPINFO)localObject).dwFlags & 0x1) != 0))
            OS.ShowWindow(this.handle, ((STARTUPINFO)localObject).wShowWindow);
          else
            OS.ShowWindow(this.handle, this.swFlags);
        }
        if (isDisposed())
          return;
        this.opened = true;
        if (!this.moved)
        {
          this.moved = true;
          localObject = getLocation();
          this.oldX = ((Point)localObject).x;
          this.oldY = ((Point)localObject).y;
        }
        if (!this.resized)
        {
          this.resized = true;
          localObject = getClientArea();
          this.oldWidth = ((Rectangle)localObject).width;
          this.oldHeight = ((Rectangle)localObject).height;
        }
        int j = 1;
        if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(6, 0)) && (!OS.IsAppThemed()))
          j = OS.IsHungAppWindow(this.handle) ? 0 : 1;
        if (j != 0)
          OS.UpdateWindow(this.handle);
      }
    }
    else
    {
      if (!OS.IsWinCE)
        if (OS.IsIconic(this.handle))
          this.swFlags = 7;
        else if (OS.IsZoomed(this.handle))
          this.swFlags = OS.SW_SHOWMAXIMIZED;
        else
          this.swFlags = 4;
      if (!getDrawing())
        this.state |= 16;
      else
        OS.ShowWindow(this.handle, 0);
      if (isDisposed())
        return;
      sendEvent(23);
    }
  }

  void sort(Image[] paramArrayOfImage, ImageData[] paramArrayOfImageData, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = paramArrayOfImage.length;
    if (i <= 1)
      return;
    int j = i / 2;
    while (j > 0)
    {
      for (int k = j; k < i; k++)
      {
        int m = k - j;
        while (m >= 0)
        {
          if (compare(paramArrayOfImageData[m], paramArrayOfImageData[(m + j)], paramInt1, paramInt2, paramInt3) >= 0)
          {
            Image localImage = paramArrayOfImage[m];
            paramArrayOfImage[m] = paramArrayOfImage[(m + j)];
            paramArrayOfImage[(m + j)] = localImage;
            ImageData localImageData = paramArrayOfImageData[m];
            paramArrayOfImageData[m] = paramArrayOfImageData[(m + j)];
            paramArrayOfImageData[(m + j)] = localImageData;
          }
          m -= j;
        }
      }
      j /= 2;
    }
  }

  boolean translateAccelerator(MSG paramMSG)
  {
    if ((!isEnabled()) || (!isActive()))
      return false;
    if ((this.menuBar != null) && (!this.menuBar.isEnabled()))
      return false;
    if ((translateMDIAccelerator(paramMSG)) || (translateMenuAccelerator(paramMSG)))
      return true;
    Decorations localDecorations = this.parent.menuShell();
    return localDecorations.translateAccelerator(paramMSG);
  }

  boolean translateMenuAccelerator(MSG paramMSG)
  {
    if (this.hAccel == -1)
      createAccelerators();
    return (this.hAccel != 0) && (OS.TranslateAccelerator(this.handle, this.hAccel, paramMSG) != 0);
  }

  boolean translateMDIAccelerator(MSG paramMSG)
  {
    if (!(this instanceof Shell))
    {
      Shell localShell = getShell();
      int i = localShell.hwndMDIClient;
      if ((i != 0) && (OS.TranslateMDISysAccel(i, paramMSG)))
        return true;
      if (paramMSG.message == 256)
      {
        if (OS.GetKeyState(17) >= 0)
          return false;
        switch (paramMSG.wParam)
        {
        case 115:
          OS.PostMessage(this.handle, 16, 0, 0);
          return true;
        case 117:
          if (traverseDecorations(true))
            return true;
          break;
        case 116:
        }
        return false;
      }
      if (paramMSG.message == 260)
      {
        switch (paramMSG.wParam)
        {
        case 115:
          OS.PostMessage(localShell.handle, 16, 0, 0);
          return true;
        }
        return false;
      }
    }
    return false;
  }

  boolean traverseDecorations(boolean paramBoolean)
  {
    Control[] arrayOfControl = this.parent._getChildren();
    int i = arrayOfControl.length;
    for (int j = 0; j < i; j++)
      if (arrayOfControl[j] == this)
        break;
    int k = j;
    int m = paramBoolean ? 1 : -1;
    while ((j = (j + m + i) % i) != k)
    {
      Control localControl = arrayOfControl[j];
      if ((!localControl.isDisposed()) && ((localControl instanceof Decorations)) && (localControl.setFocus()))
        return true;
    }
    return false;
  }

  boolean traverseItem(boolean paramBoolean)
  {
    return false;
  }

  boolean traverseReturn()
  {
    if ((this.defaultButton == null) || (this.defaultButton.isDisposed()))
      return false;
    if ((!this.defaultButton.isVisible()) || (!this.defaultButton.isEnabled()))
      return false;
    this.defaultButton.click();
    return true;
  }

  CREATESTRUCT widgetCreateStruct()
  {
    return new CREATESTRUCT();
  }

  int widgetExtStyle()
  {
    int i = super.widgetExtStyle() | 0x40;
    i &= -513;
    if ((this.style & 0x8) != 0)
      return i;
    if ((OS.IsPPC) && ((this.style & 0x40) != 0))
      i |= -2147483648;
    if ((this.style & 0x10) != 0)
      return i;
    if ((this.style & 0x800) != 0)
      i |= 1;
    return i;
  }

  int widgetParent()
  {
    Shell localShell = getShell();
    return localShell.hwndMDIClient();
  }

  int widgetStyle()
  {
    int i = super.widgetStyle() & 0xEFFEFFFF;
    i &= -8388609;
    if ((this.style & 0x8) != 0)
    {
      if (this.parent == null)
        i |= 524288;
      return i;
    }
    if ((this.style & 0x20) != 0)
      i |= 12582912;
    if ((this.style & 0x80) != 0)
      i |= OS.WS_MINIMIZEBOX;
    if ((this.style & 0x400) != 0)
      i |= OS.WS_MAXIMIZEBOX;
    if ((this.style & 0x10) != 0)
    {
      if (!OS.IsPPC)
        i |= 262144;
    }
    else if ((this.style & 0x800) == 0)
      i |= 8388608;
    if ((!OS.IsPPC) && (!OS.IsSP) && ((this.style & 0x40) != 0))
      i |= 524288;
    return i;
  }

  int windowProc(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    switch (paramInt2)
    {
    case 32768:
    case 32769:
      if (this.hAccel == -1)
        createAccelerators();
      return paramInt2 == 32768 ? this.nAccel : this.hAccel;
    }
    return super.windowProc(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  LRESULT WM_ACTIVATE(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_ACTIVATE(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    Object localObject;
    if (OS.GetParent(paramInt2) == this.handle)
    {
      localObject = new TCHAR(0, 128);
      OS.GetClassName(paramInt2, (TCHAR)localObject, ((TCHAR)localObject).length());
      String str = ((TCHAR)localObject).toString(0, ((TCHAR)localObject).strlen());
      if (str.equals("SunAwtWindow"))
        return LRESULT.ZERO;
    }
    if (OS.LOWORD(paramInt1) != 0)
    {
      if (OS.HIWORD(paramInt1) != 0)
        return localLRESULT;
      localObject = this.display.findControl(paramInt2);
      if (((localObject == null) || ((localObject instanceof Shell))) && ((this instanceof Shell)))
      {
        sendEvent(26);
        if (isDisposed())
          return LRESULT.ZERO;
      }
      if (restoreFocus())
        return LRESULT.ZERO;
    }
    else
    {
      localObject = this.display;
      boolean bool = ((Display)localObject).isXMouseActive();
      if (bool)
        ((Display)localObject).lockActiveWindow = true;
      Control localControl = ((Display)localObject).findControl(paramInt2);
      if (((localControl == null) || ((localControl instanceof Shell))) && ((this instanceof Shell)))
      {
        sendEvent(27);
        if (!isDisposed())
        {
          Shell localShell = getShell();
          localShell.setActiveControl(null);
        }
      }
      if (bool)
        ((Display)localObject).lockActiveWindow = false;
      if (isDisposed())
        return LRESULT.ZERO;
      saveFocus();
    }
    return localLRESULT;
  }

  LRESULT WM_CLOSE(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_CLOSE(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    if ((isEnabled()) && (isActive()))
      closeWidget();
    return LRESULT.ZERO;
  }

  LRESULT WM_HOTKEY(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_HOTKEY(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    if ((OS.IsSP) && (OS.HIWORD(paramInt2) == 27))
    {
      if ((this.style & 0x40) != 0)
        OS.PostMessage(this.handle, 16, 0, 0);
      else
        OS.SHSendBackToFocusWindow(786, paramInt1, paramInt2);
      return LRESULT.ZERO;
    }
    return localLRESULT;
  }

  LRESULT WM_KILLFOCUS(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_KILLFOCUS(paramInt1, paramInt2);
    saveFocus();
    return localLRESULT;
  }

  LRESULT WM_MOVE(int paramInt1, int paramInt2)
  {
    if (this.moved)
    {
      Point localPoint = getLocation();
      if ((localPoint.x == this.oldX) && (localPoint.y == this.oldY))
        return null;
      this.oldX = localPoint.x;
      this.oldY = localPoint.y;
    }
    return super.WM_MOVE(paramInt1, paramInt2);
  }

  LRESULT WM_NCACTIVATE(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_NCACTIVATE(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    if (paramInt1 == 0)
    {
      if (this.display.lockActiveWindow)
        return LRESULT.ZERO;
      Control localControl = this.display.findControl(paramInt2);
      if (localControl != null)
      {
        Shell localShell = getShell();
        Decorations localDecorations = localControl.menuShell();
        if (localDecorations.getShell() == localShell)
        {
          if ((this instanceof Shell))
            return LRESULT.ONE;
          if ((this.display.ignoreRestoreFocus) && (this.display.lastHittest != 1))
            localLRESULT = LRESULT.ONE;
        }
      }
    }
    if (!(this instanceof Shell))
    {
      int i = getShell().handle;
      OS.SendMessage(i, 134, paramInt1, paramInt2);
    }
    return localLRESULT;
  }

  LRESULT WM_QUERYOPEN(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_QUERYOPEN(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    sendEvent(20);
    return localLRESULT;
  }

  LRESULT WM_SETFOCUS(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_SETFOCUS(paramInt1, paramInt2);
    if (isDisposed())
      return localLRESULT;
    if (this.savedFocus != this)
      restoreFocus();
    return localLRESULT;
  }

  LRESULT WM_SIZE(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = null;
    int i = 1;
    if (this.resized)
    {
      int j = 0;
      int k = 0;
      switch (paramInt1)
      {
      case 0:
      case 2:
        j = OS.LOWORD(paramInt2);
        k = OS.HIWORD(paramInt2);
        break;
      case 1:
        Rectangle localRectangle = getClientArea();
        j = localRectangle.width;
        k = localRectangle.height;
      }
      i = (j == this.oldWidth) && (k == this.oldHeight) ? 0 : 1;
      if (i != 0)
      {
        this.oldWidth = j;
        this.oldHeight = k;
      }
    }
    if (i != 0)
    {
      localLRESULT = super.WM_SIZE(paramInt1, paramInt2);
      if (isDisposed())
        return localLRESULT;
    }
    if (paramInt1 == 1)
      sendEvent(19);
    return localLRESULT;
  }

  LRESULT WM_SYSCOMMAND(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_SYSCOMMAND(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    if (!(this instanceof Shell))
    {
      int i = paramInt1 & 0xFFF0;
      switch (i)
      {
      case 61536:
        OS.PostMessage(this.handle, 16, 0, 0);
        return LRESULT.ZERO;
      case 61504:
        traverseDecorations(true);
        return LRESULT.ZERO;
      }
    }
    return localLRESULT;
  }

  LRESULT WM_WINDOWPOSCHANGING(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_WINDOWPOSCHANGING(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    if (this.display.lockActiveWindow)
    {
      WINDOWPOS localWINDOWPOS = new WINDOWPOS();
      OS.MoveMemory(localWINDOWPOS, paramInt2, WINDOWPOS.sizeof);
      localWINDOWPOS.flags |= 4;
      OS.MoveMemory(paramInt2, localWINDOWPOS, WINDOWPOS.sizeof);
    }
    return localLRESULT;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.Decorations
 * JD-Core Version:    0.6.2
 */