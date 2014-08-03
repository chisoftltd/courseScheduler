package org.eclipse.swt.widgets;

import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.ImageList;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.TBBUTTONINFO;
import org.eclipse.swt.internal.win32.TCHAR;

public class ToolItem extends Item
{
  ToolBar parent;
  Control control;
  String toolTipText;
  Image disabledImage;
  Image hotImage;
  Image disabledImage2;
  int id;
  short cx;

  public ToolItem(ToolBar paramToolBar, int paramInt)
  {
    super(paramToolBar, checkStyle(paramInt));
    this.parent = paramToolBar;
    paramToolBar.createItem(this, paramToolBar.getItemCount());
  }

  public ToolItem(ToolBar paramToolBar, int paramInt1, int paramInt2)
  {
    super(paramToolBar, checkStyle(paramInt1));
    this.parent = paramToolBar;
    paramToolBar.createItem(this, paramInt2);
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
    return checkBits(paramInt, 8, 32, 16, 2, 4, 0);
  }

  protected void checkSubclass()
  {
    if (!isValidSubclass())
      error(43);
  }

  void click(boolean paramBoolean)
  {
    int i = this.parent.handle;
    if (OS.GetKeyState(1) < 0)
      return;
    int j = OS.SendMessage(i, 1049, this.id, 0);
    RECT localRECT = new RECT();
    OS.SendMessage(i, 1053, j, localRECT);
    int k = OS.SendMessage(i, 1095, 0, 0);
    int m = localRECT.top + (localRECT.bottom - localRECT.top) / 2;
    int n = OS.MAKELPARAM(paramBoolean ? localRECT.right - 1 : localRECT.left, m);
    this.parent.ignoreMouse = true;
    OS.SendMessage(i, 513, 0, n);
    OS.SendMessage(i, 514, 0, n);
    this.parent.ignoreMouse = false;
    if (k != -1)
      OS.SendMessage(i, 1096, k, 0);
  }

  Widget[] computeTabList()
  {
    if ((isTabGroup()) && (getEnabled()))
      if ((this.style & 0x2) != 0)
      {
        if (this.control != null)
          return this.control.computeTabList();
      }
      else
        return new Widget[] { this };
    return new Widget[0];
  }

  void destroyWidget()
  {
    this.parent.destroyItem(this);
    releaseHandle();
  }

  public Rectangle getBounds()
  {
    checkWidget();
    int i = this.parent.handle;
    int j = OS.SendMessage(i, 1049, this.id, 0);
    RECT localRECT = new RECT();
    OS.SendMessage(i, 1053, j, localRECT);
    int k = localRECT.right - localRECT.left;
    int m = localRECT.bottom - localRECT.top;
    return new Rectangle(localRECT.left, localRECT.top, k, m);
  }

  public Control getControl()
  {
    checkWidget();
    return this.control;
  }

  public Image getDisabledImage()
  {
    checkWidget();
    return this.disabledImage;
  }

  public boolean getEnabled()
  {
    checkWidget();
    if ((this.style & 0x2) != 0)
      return (this.state & 0x8) == 0;
    int i = this.parent.handle;
    int j = OS.SendMessage(i, 1042, this.id, 0);
    return (j & 0x4) != 0;
  }

  public Image getHotImage()
  {
    checkWidget();
    return this.hotImage;
  }

  public ToolBar getParent()
  {
    checkWidget();
    return this.parent;
  }

  public boolean getSelection()
  {
    checkWidget();
    if ((this.style & 0x30) == 0)
      return false;
    int i = this.parent.handle;
    int j = OS.SendMessage(i, 1042, this.id, 0);
    return (j & 0x1) != 0;
  }

  public String getToolTipText()
  {
    checkWidget();
    return this.toolTipText;
  }

  public int getWidth()
  {
    checkWidget();
    int i = this.parent.handle;
    int j = OS.SendMessage(i, 1049, this.id, 0);
    RECT localRECT = new RECT();
    OS.SendMessage(i, 1053, j, localRECT);
    return localRECT.right - localRECT.left;
  }

  public boolean isEnabled()
  {
    checkWidget();
    return (getEnabled()) && (this.parent.isEnabled());
  }

  boolean isTabGroup()
  {
    ToolItem[] arrayOfToolItem = this.parent._getTabItemList();
    if (arrayOfToolItem != null)
      for (i = 0; i < arrayOfToolItem.length; i++)
        if (arrayOfToolItem[i] == this)
          return true;
    if ((this.style & 0x2) != 0)
      return true;
    int i = this.parent.indexOf(this);
    if (i == 0)
      return true;
    ToolItem localToolItem = this.parent.getItem(i - 1);
    return (localToolItem.getStyle() & 0x2) != 0;
  }

  void releaseWidget()
  {
    super.releaseWidget();
    releaseImages();
    this.control = null;
    this.toolTipText = null;
    this.disabledImage = (this.hotImage = null);
    if (this.disabledImage2 != null)
      this.disabledImage2.dispose();
    this.disabledImage2 = null;
  }

  void releaseHandle()
  {
    super.releaseHandle();
    this.parent = null;
    this.id = -1;
  }

  void releaseImages()
  {
    TBBUTTONINFO localTBBUTTONINFO = new TBBUTTONINFO();
    localTBBUTTONINFO.cbSize = TBBUTTONINFO.sizeof;
    localTBBUTTONINFO.dwMask = 9;
    int i = this.parent.handle;
    OS.SendMessage(i, OS.TB_GETBUTTONINFO, this.id, localTBBUTTONINFO);
    if (((localTBBUTTONINFO.fsStyle & 0x1) == 0) && (localTBBUTTONINFO.iImage != -2))
    {
      ImageList localImageList1 = this.parent.getImageList();
      ImageList localImageList2 = this.parent.getHotImageList();
      ImageList localImageList3 = this.parent.getDisabledImageList();
      if (localImageList1 != null)
        localImageList1.put(localTBBUTTONINFO.iImage, null);
      if (localImageList2 != null)
        localImageList2.put(localTBBUTTONINFO.iImage, null);
      if (localImageList3 != null)
        localImageList3.put(localTBBUTTONINFO.iImage, null);
    }
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

  void resizeControl()
  {
    if ((this.control != null) && (!this.control.isDisposed()))
    {
      Rectangle localRectangle1 = getBounds();
      this.control.setSize(localRectangle1.width, localRectangle1.height);
      Rectangle localRectangle2 = this.control.getBounds();
      localRectangle1.x += (localRectangle1.width - localRectangle2.width) / 2;
      localRectangle1.y += (localRectangle1.height - localRectangle2.height) / 2;
      this.control.setLocation(localRectangle2.x, localRectangle2.y);
    }
  }

  void selectRadio()
  {
    int i = 0;
    ToolItem[] arrayOfToolItem = this.parent.getItems();
    while ((i < arrayOfToolItem.length) && (arrayOfToolItem[i] != this))
      i++;
    for (int j = i - 1; (j >= 0) && (arrayOfToolItem[j].setRadioSelection(false)); j--);
    for (int k = i + 1; (k < arrayOfToolItem.length) && (arrayOfToolItem[k].setRadioSelection(false)); k++);
    setSelection(true);
  }

  public void setControl(Control paramControl)
  {
    checkWidget();
    if (paramControl != null)
    {
      if (paramControl.isDisposed())
        error(5);
      if (paramControl.parent != this.parent)
        error(32);
    }
    if ((this.style & 0x2) == 0)
      return;
    this.control = paramControl;
    if ((this.parent.style & 0x240) != 0)
    {
      int i = 0;
      int j = this.parent.handle;
      TBBUTTONINFO localTBBUTTONINFO = new TBBUTTONINFO();
      localTBBUTTONINFO.cbSize = TBBUTTONINFO.sizeof;
      localTBBUTTONINFO.dwMask = 12;
      OS.SendMessage(j, OS.TB_GETBUTTONINFO, this.id, localTBBUTTONINFO);
      if (paramControl == null)
      {
        if ((localTBBUTTONINFO.fsStyle & 0x1) == 0)
        {
          i = 1;
          TBBUTTONINFO tmp132_130 = localTBBUTTONINFO;
          tmp132_130.fsStyle = ((byte)(tmp132_130.fsStyle & 0xFFFFFFBF));
          TBBUTTONINFO tmp145_143 = localTBBUTTONINFO;
          tmp145_143.fsStyle = ((byte)(tmp145_143.fsStyle | 0x1));
          if ((this.state & 0x8) != 0)
          {
            TBBUTTONINFO tmp167_165 = localTBBUTTONINFO;
            tmp167_165.fsState = ((byte)(tmp167_165.fsState & 0xFFFFFFFB));
          }
          else
          {
            TBBUTTONINFO tmp183_181 = localTBBUTTONINFO;
            tmp183_181.fsState = ((byte)(tmp183_181.fsState | 0x4));
          }
        }
      }
      else if ((localTBBUTTONINFO.fsStyle & 0x1) != 0)
      {
        i = 1;
        TBBUTTONINFO tmp210_208 = localTBBUTTONINFO;
        tmp210_208.fsStyle = ((byte)(tmp210_208.fsStyle & 0xFFFFFFFE));
        TBBUTTONINFO tmp223_221 = localTBBUTTONINFO;
        tmp223_221.fsStyle = ((byte)(tmp223_221.fsStyle | 0x40));
        TBBUTTONINFO tmp236_234 = localTBBUTTONINFO;
        tmp236_234.fsState = ((byte)(tmp236_234.fsState & 0xFFFFFFFB));
        localTBBUTTONINFO.dwMask |= 1;
        localTBBUTTONINFO.iImage = -2;
      }
      if (i != 0)
      {
        OS.SendMessage(j, OS.TB_SETBUTTONINFO, this.id, localTBBUTTONINFO);
        if (OS.SendMessage(j, 1064, 0, 0) > 1)
          OS.InvalidateRect(j, null, true);
      }
    }
    resizeControl();
  }

  public void setEnabled(boolean paramBoolean)
  {
    checkWidget();
    int i = this.parent.handle;
    int j = OS.SendMessage(i, 1042, this.id, 0);
    if (((j & 0x4) != 0) == paramBoolean)
      return;
    if (paramBoolean)
    {
      j |= 4;
      this.state &= -9;
    }
    else
    {
      j &= -5;
      this.state |= 8;
    }
    OS.SendMessage(i, 1041, this.id, j);
    if (((this.style & 0x2) == 0) && (this.image != null))
      updateImages((paramBoolean) && (this.parent.getEnabled()));
  }

  public void setDisabledImage(Image paramImage)
  {
    checkWidget();
    if ((this.style & 0x2) != 0)
      return;
    if ((paramImage != null) && (paramImage.isDisposed()))
      error(5);
    this.disabledImage = paramImage;
    updateImages((getEnabled()) && (this.parent.getEnabled()));
  }

  public void setHotImage(Image paramImage)
  {
    checkWidget();
    if ((this.style & 0x2) != 0)
      return;
    if ((paramImage != null) && (paramImage.isDisposed()))
      error(5);
    this.hotImage = paramImage;
    updateImages((getEnabled()) && (this.parent.getEnabled()));
  }

  public void setImage(Image paramImage)
  {
    checkWidget();
    if ((this.style & 0x2) != 0)
      return;
    if ((paramImage != null) && (paramImage.isDisposed()))
      error(5);
    super.setImage(paramImage);
    updateImages((getEnabled()) && (this.parent.getEnabled()));
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
    int i = this.parent.handle;
    int j = OS.SendMessage(i, 1042, this.id, 0);
    if (((j & 0x1) != 0) == paramBoolean)
      return;
    if (paramBoolean)
      j |= 1;
    else
      j &= -2;
    OS.SendMessage(i, 1041, this.id, j);
    if (((this.style & 0x30) != 0) && ((!getEnabled()) || (!this.parent.getEnabled())))
      updateImages(false);
  }

  boolean setTabItemFocus()
  {
    if (this.parent.setTabItemFocus())
    {
      int i = this.parent.handle;
      int j = OS.SendMessage(i, 1049, this.id, 0);
      OS.SendMessage(i, 1096, j, 0);
      return true;
    }
    return false;
  }

  public void setText(String paramString)
  {
    checkWidget();
    if (paramString == null)
      error(4);
    if ((this.style & 0x2) != 0)
      return;
    if (paramString.equals(this.text))
      return;
    super.setText(paramString);
    int i = this.parent.handle;
    TBBUTTONINFO localTBBUTTONINFO = new TBBUTTONINFO();
    localTBBUTTONINFO.cbSize = TBBUTTONINFO.sizeof;
    localTBBUTTONINFO.dwMask = 10;
    localTBBUTTONINFO.fsStyle = ((byte)(widgetStyle() | 0x10));
    int j = OS.GetProcessHeap();
    int k = 0;
    if (paramString.length() != 0)
    {
      TBBUTTONINFO tmp97_96 = localTBBUTTONINFO;
      tmp97_96.fsStyle = ((byte)(tmp97_96.fsStyle | 0x40));
      TCHAR localTCHAR = new TCHAR(this.parent.getCodePage(), paramString, true);
      int n = localTCHAR.length() * TCHAR.sizeof;
      k = OS.HeapAlloc(j, 8, n);
      OS.MoveMemory(k, localTCHAR, n);
      localTBBUTTONINFO.pszText = k;
    }
    OS.SendMessage(i, OS.TB_SETBUTTONINFO, this.id, localTBBUTTONINFO);
    if (k != 0)
      OS.HeapFree(j, 0, k);
    this.parent.setDropDownItems(false);
    int m = OS.SendMessage(i, 49, 0, 0);
    OS.SendMessage(i, 48, m, 0);
    this.parent.setDropDownItems(true);
    this.parent.layoutItems();
  }

  public void setToolTipText(String paramString)
  {
    checkWidget();
    this.toolTipText = paramString;
  }

  public void setWidth(int paramInt)
  {
    checkWidget();
    if ((this.style & 0x2) == 0)
      return;
    if (paramInt < 0)
      return;
    int i = this.parent.handle;
    TBBUTTONINFO localTBBUTTONINFO = new TBBUTTONINFO();
    localTBBUTTONINFO.cbSize = TBBUTTONINFO.sizeof;
    localTBBUTTONINFO.dwMask = 64;
    localTBBUTTONINFO.cx = (this.cx = (short)paramInt);
    OS.SendMessage(i, OS.TB_SETBUTTONINFO, this.id, localTBBUTTONINFO);
    this.parent.layoutItems();
  }

  void updateImages(boolean paramBoolean)
  {
    if ((this.style & 0x2) != 0)
      return;
    int i = this.parent.handle;
    TBBUTTONINFO localTBBUTTONINFO = new TBBUTTONINFO();
    localTBBUTTONINFO.cbSize = TBBUTTONINFO.sizeof;
    localTBBUTTONINFO.dwMask = 1;
    OS.SendMessage(i, OS.TB_GETBUTTONINFO, this.id, localTBBUTTONINFO);
    if ((localTBBUTTONINFO.iImage == -2) && (this.image == null))
      return;
    ImageList localImageList1 = this.parent.getImageList();
    ImageList localImageList2 = this.parent.getHotImageList();
    ImageList localImageList3 = this.parent.getDisabledImageList();
    Object localObject1;
    Object localObject3;
    if (localTBBUTTONINFO.iImage == -2)
    {
      localObject1 = this.image.getBounds();
      int k = this.parent.style & 0x4000000;
      if (localImageList1 == null)
        localImageList1 = this.display.getImageListToolBar(k, ((Rectangle)localObject1).width, ((Rectangle)localObject1).height);
      if (localImageList3 == null)
        localImageList3 = this.display.getImageListToolBarDisabled(k, ((Rectangle)localObject1).width, ((Rectangle)localObject1).height);
      if (localImageList2 == null)
        localImageList2 = this.display.getImageListToolBarHot(k, ((Rectangle)localObject1).width, ((Rectangle)localObject1).height);
      localObject3 = this.disabledImage;
      if (this.disabledImage == null)
      {
        if (this.disabledImage2 != null)
          this.disabledImage2.dispose();
        this.disabledImage2 = null;
        localObject3 = this.image;
        if (!paramBoolean)
          localObject3 = this.disabledImage2 = new Image(this.display, this.image, 1);
      }
      Object localObject4 = this.image;
      Object localObject5 = this.hotImage;
      if (((this.style & 0x30) != 0) && (!paramBoolean))
        localObject4 = localObject5 = localObject3;
      localTBBUTTONINFO.iImage = localImageList1.add((Image)localObject4);
      localImageList3.add((Image)localObject3);
      localImageList2.add(localObject5 != null ? localObject5 : (Image)localObject4);
      this.parent.setImageList(localImageList1);
      this.parent.setDisabledImageList(localImageList3);
      this.parent.setHotImageList(localImageList2);
    }
    else
    {
      localObject1 = null;
      if (localImageList3 != null)
      {
        if (this.image != null)
        {
          if (this.disabledImage2 != null)
            this.disabledImage2.dispose();
          this.disabledImage2 = null;
          localObject1 = this.disabledImage;
          if (this.disabledImage == null)
          {
            localObject1 = this.image;
            if (!paramBoolean)
              localObject1 = this.disabledImage2 = new Image(this.display, this.image, 1);
          }
        }
        localImageList3.put(localTBBUTTONINFO.iImage, (Image)localObject1);
      }
      Object localObject2 = this.image;
      localObject3 = this.hotImage;
      if (((this.style & 0x30) != 0) && (!paramBoolean))
        localObject2 = localObject3 = localObject1;
      if (localImageList1 != null)
        localImageList1.put(localTBBUTTONINFO.iImage, (Image)localObject2);
      if (localImageList2 != null)
        localImageList2.put(localTBBUTTONINFO.iImage, localObject3 != null ? localObject3 : (Image)localObject2);
      if (this.image == null)
        localTBBUTTONINFO.iImage = -2;
    }
    localTBBUTTONINFO.dwMask |= 64;
    localTBBUTTONINFO.cx = 0;
    OS.SendMessage(i, OS.TB_SETBUTTONINFO, this.id, localTBBUTTONINFO);
    int j = OS.SendMessage(i, 49, 0, 0);
    OS.SendMessage(i, 48, j, 0);
    this.parent.layoutItems();
  }

  int widgetStyle()
  {
    if ((this.style & 0x4) != 0)
      return 8;
    if ((this.style & 0x8) != 0)
      return 0;
    if ((this.style & 0x20) != 0)
      return 2;
    if ((this.style & 0x10) != 0)
      return 2;
    if ((this.style & 0x2) != 0)
      return 1;
    return 0;
  }

  LRESULT wmCommandChild(int paramInt1, int paramInt2)
  {
    if (((this.style & 0x10) != 0) && ((this.parent.getStyle() & 0x400000) == 0))
      selectRadio();
    sendSelectionEvent(13);
    return null;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.ToolItem
 * JD-Core Version:    0.6.2
 */