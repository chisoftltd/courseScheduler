package org.eclipse.swt.widgets;

import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.ImageList;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.NMHDR;
import org.eclipse.swt.internal.win32.NMTTDISPINFO;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.TCHITTESTINFO;
import org.eclipse.swt.internal.win32.TCITEM;
import org.eclipse.swt.internal.win32.TOOLINFO;
import org.eclipse.swt.internal.win32.WINDOWPOS;
import org.eclipse.swt.internal.win32.WNDCLASS;

public class TabFolder extends Composite
{
  TabItem[] items;
  ImageList imageList;
  static final int TabFolderProc;
  static final TCHAR TabFolderClass = new TCHAR(0, "SysTabControl32", true);
  static final int ID_UPDOWN = 1;

  static
  {
    WNDCLASS localWNDCLASS = new WNDCLASS();
    OS.GetClassInfo(0, TabFolderClass, localWNDCLASS);
    TabFolderProc = localWNDCLASS.lpfnWndProc;
    int i = OS.GetModuleHandle(null);
    int j = OS.GetProcessHeap();
    localWNDCLASS.hInstance = i;
    localWNDCLASS.style &= -16388;
    int k = TabFolderClass.length() * TCHAR.sizeof;
    int m = OS.HeapAlloc(j, 8, k);
    OS.MoveMemory(m, TabFolderClass, k);
    localWNDCLASS.lpszClassName = m;
    OS.RegisterClass(localWNDCLASS);
    OS.HeapFree(j, 0, m);
  }

  public TabFolder(Composite paramComposite, int paramInt)
  {
    super(paramComposite, checkStyle(paramInt));
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
    if (this.handle == 0)
      return 0;
    return OS.CallWindowProc(TabFolderProc, paramInt1, paramInt2, paramInt3, paramInt4);
  }

  static int checkStyle(int paramInt)
  {
    if ((OS.IsPPC) && ((paramInt & 0x80) == 0))
      paramInt |= 1024;
    paramInt = checkBits(paramInt, 128, 1024, 0, 0, 0, 0);
    return paramInt & 0xFFFFFCFF;
  }

  protected void checkSubclass()
  {
    if (!isValidSubclass())
      error(43);
  }

  public Point computeSize(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    checkWidget();
    Point localPoint = super.computeSize(paramInt1, paramInt2, paramBoolean);
    RECT localRECT1 = new RECT();
    RECT localRECT2 = new RECT();
    OS.SendMessage(this.handle, 4904, 0, localRECT1);
    int i = localRECT1.left - localRECT1.right;
    int j = OS.SendMessage(this.handle, 4868, 0, 0);
    if (j != 0)
    {
      OS.SendMessage(this.handle, 4874, j - 1, localRECT2);
      i = Math.max(i, localRECT2.right - localRECT1.right);
    }
    RECT localRECT3 = new RECT();
    OS.SetRect(localRECT3, 0, 0, i, localPoint.y);
    OS.SendMessage(this.handle, 4904, 1, localRECT3);
    int k = getBorderWidth();
    localRECT3.left -= k;
    localRECT3.right += k;
    i = localRECT3.right - localRECT3.left;
    localPoint.x = Math.max(i, localPoint.x);
    return localPoint;
  }

  public Rectangle computeTrim(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    checkWidget();
    RECT localRECT = new RECT();
    OS.SetRect(localRECT, paramInt1, paramInt2, paramInt1 + paramInt3, paramInt2 + paramInt4);
    OS.SendMessage(this.handle, 4904, 1, localRECT);
    int i = getBorderWidth();
    localRECT.left -= i;
    localRECT.right += i;
    localRECT.top -= i;
    localRECT.bottom += i;
    int j = localRECT.right - localRECT.left;
    int k = localRECT.bottom - localRECT.top;
    return new Rectangle(localRECT.left, localRECT.top, j, k);
  }

  void createItem(TabItem paramTabItem, int paramInt)
  {
    int i = OS.SendMessage(this.handle, 4868, 0, 0);
    if ((paramInt < 0) || (paramInt > i))
      error(6);
    if (i == this.items.length)
    {
      localObject = new TabItem[this.items.length + 4];
      System.arraycopy(this.items, 0, localObject, 0, this.items.length);
      this.items = ((TabItem[])localObject);
    }
    Object localObject = new TCITEM();
    if (OS.SendMessage(this.handle, OS.TCM_INSERTITEM, paramInt, (TCITEM)localObject) == -1)
      error(14);
    System.arraycopy(this.items, paramInt, this.items, paramInt + 1, i - paramInt);
    this.items[paramInt] = paramTabItem;
    if (i == 0)
    {
      Event localEvent = new Event();
      localEvent.item = this.items[0];
      sendSelectionEvent(13, localEvent, true);
    }
  }

  void createHandle()
  {
    super.createHandle();
    this.state &= -259;
    if (OS.IsPPC)
      OS.SendMessage(this.handle, 8199, 524, 0);
    int i = OS.SendMessage(this.handle, 4909, 0, 0);
    OS.SendMessage(i, 1048, 0, 32767);
  }

  void createWidget()
  {
    super.createWidget();
    this.items = new TabItem[4];
  }

  void destroyItem(TabItem paramTabItem)
  {
    int i = OS.SendMessage(this.handle, 4868, 0, 0);
    for (int j = 0; j < i; j++)
      if (this.items[j] == paramTabItem)
        break;
    if (j == i)
      return;
    int k = OS.SendMessage(this.handle, 4875, 0, 0);
    if (OS.SendMessage(this.handle, 4872, j, 0) == 0)
      error(15);
    System.arraycopy(this.items, j + 1, this.items, j, --i - j);
    this.items[i] = null;
    if (i == 0)
    {
      if (this.imageList != null)
      {
        OS.SendMessage(this.handle, 4867, 0, 0);
        this.display.releaseImageList(this.imageList);
      }
      this.imageList = null;
      this.items = new TabItem[4];
    }
    if ((i > 0) && (j == k))
      setSelection(Math.max(0, k - 1), true);
  }

  void drawThemeBackground(int paramInt1, int paramInt2, RECT paramRECT)
  {
    RECT localRECT = new RECT();
    OS.GetClientRect(this.handle, localRECT);
    OS.MapWindowPoints(this.handle, paramInt2, localRECT, 2);
    if (OS.IntersectRect(new RECT(), localRECT, paramRECT))
      OS.DrawThemeBackground(this.display.hTabTheme(), paramInt1, 10, 0, localRECT, null);
  }

  Control findThemeControl()
  {
    return this;
  }

  public Rectangle getClientArea()
  {
    checkWidget();
    forceResize();
    RECT localRECT = new RECT();
    OS.GetClientRect(this.handle, localRECT);
    OS.SendMessage(this.handle, 4904, 0, localRECT);
    int i = localRECT.right - localRECT.left;
    int j = localRECT.bottom - localRECT.top;
    return new Rectangle(localRECT.left, localRECT.top, i, j);
  }

  public TabItem getItem(int paramInt)
  {
    checkWidget();
    int i = OS.SendMessage(this.handle, 4868, 0, 0);
    if ((paramInt < 0) || (paramInt >= i))
      error(6);
    return this.items[paramInt];
  }

  public TabItem getItem(Point paramPoint)
  {
    checkWidget();
    if (paramPoint == null)
      error(4);
    TCHITTESTINFO localTCHITTESTINFO = new TCHITTESTINFO();
    localTCHITTESTINFO.x = paramPoint.x;
    localTCHITTESTINFO.y = paramPoint.y;
    int i = OS.SendMessage(this.handle, 4877, 0, localTCHITTESTINFO);
    if (i == -1)
      return null;
    return this.items[i];
  }

  public int getItemCount()
  {
    checkWidget();
    return OS.SendMessage(this.handle, 4868, 0, 0);
  }

  public TabItem[] getItems()
  {
    checkWidget();
    int i = OS.SendMessage(this.handle, 4868, 0, 0);
    TabItem[] arrayOfTabItem = new TabItem[i];
    System.arraycopy(this.items, 0, arrayOfTabItem, 0, i);
    return arrayOfTabItem;
  }

  public TabItem[] getSelection()
  {
    checkWidget();
    int i = OS.SendMessage(this.handle, 4875, 0, 0);
    if (i == -1)
      return new TabItem[0];
    return new TabItem[] { this.items[i] };
  }

  public int getSelectionIndex()
  {
    checkWidget();
    return OS.SendMessage(this.handle, 4875, 0, 0);
  }

  int imageIndex(Image paramImage)
  {
    if (paramImage == null)
      return -2;
    if (this.imageList == null)
    {
      Rectangle localRectangle = paramImage.getBounds();
      this.imageList = this.display.getImageList(this.style & 0x4000000, localRectangle.width, localRectangle.height);
      int j = this.imageList.add(paramImage);
      int k = this.imageList.getHandle();
      OS.SendMessage(this.handle, 4867, 0, k);
      return j;
    }
    int i = this.imageList.indexOf(paramImage);
    if (i == -1)
      i = this.imageList.add(paramImage);
    else
      this.imageList.put(i, paramImage);
    return i;
  }

  public int indexOf(TabItem paramTabItem)
  {
    checkWidget();
    if (paramTabItem == null)
      error(4);
    int i = OS.SendMessage(this.handle, 4868, 0, 0);
    for (int j = 0; j < i; j++)
      if (this.items[j] == paramTabItem)
        return j;
    return -1;
  }

  Point minimumSize(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    Control[] arrayOfControl = _getChildren();
    int i = 0;
    int j = 0;
    for (int k = 0; k < arrayOfControl.length; k++)
    {
      Control localControl = arrayOfControl[k];
      int m = 0;
      int n = OS.SendMessage(this.handle, 4868, 0, 0);
      while (m < n)
      {
        if (this.items[m].control == localControl)
          break;
        m++;
      }
      Object localObject;
      if (m == n)
      {
        localObject = localControl.getBounds();
        i = Math.max(i, ((Rectangle)localObject).x + ((Rectangle)localObject).width);
        j = Math.max(j, ((Rectangle)localObject).y + ((Rectangle)localObject).height);
      }
      else
      {
        localObject = localControl.computeSize(paramInt1, paramInt2, paramBoolean);
        i = Math.max(i, ((Point)localObject).x);
        j = Math.max(j, ((Point)localObject).y);
      }
    }
    return new Point(i, j);
  }

  boolean mnemonicHit(char paramChar)
  {
    for (int i = 0; i < this.items.length; i++)
    {
      TabItem localTabItem = this.items[i];
      if (localTabItem != null)
      {
        char c = findMnemonic(localTabItem.getText());
        if ((Character.toUpperCase(paramChar) == Character.toUpperCase(c)) && (forceFocus()))
        {
          if (i != getSelectionIndex())
            setSelection(i, true);
          return true;
        }
      }
    }
    return false;
  }

  boolean mnemonicMatch(char paramChar)
  {
    for (int i = 0; i < this.items.length; i++)
    {
      TabItem localTabItem = this.items[i];
      if (localTabItem != null)
      {
        char c = findMnemonic(localTabItem.getText());
        if (Character.toUpperCase(paramChar) == Character.toUpperCase(c))
          return true;
      }
    }
    return false;
  }

  void releaseChildren(boolean paramBoolean)
  {
    if (this.items != null)
    {
      int i = OS.SendMessage(this.handle, 4868, 0, 0);
      for (int j = 0; j < i; j++)
      {
        TabItem localTabItem = this.items[j];
        if ((localTabItem != null) && (!localTabItem.isDisposed()))
          localTabItem.release(false);
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
      OS.SendMessage(this.handle, 4867, 0, 0);
      this.display.releaseImageList(this.imageList);
    }
    this.imageList = null;
  }

  void removeControl(Control paramControl)
  {
    super.removeControl(paramControl);
    int i = OS.SendMessage(this.handle, 4868, 0, 0);
    for (int j = 0; j < i; j++)
    {
      TabItem localTabItem = this.items[j];
      if (localTabItem.control == paramControl)
        localTabItem.setControl(null);
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

  void reskinChildren(int paramInt)
  {
    if (this.items != null)
    {
      int i = OS.SendMessage(this.handle, 4868, 0, 0);
      for (int j = 0; j < i; j++)
      {
        TabItem localTabItem = this.items[j];
        if (localTabItem != null)
          localTabItem.reskin(paramInt);
      }
    }
    super.reskinChildren(paramInt);
  }

  public void setSelection(TabItem paramTabItem)
  {
    checkWidget();
    if (paramTabItem == null)
      error(4);
    setSelection(new TabItem[] { paramTabItem });
  }

  public void setSelection(TabItem[] paramArrayOfTabItem)
  {
    checkWidget();
    if (paramArrayOfTabItem == null)
      error(4);
    if (paramArrayOfTabItem.length == 0)
      setSelection(-1, false);
    else
      for (int i = paramArrayOfTabItem.length - 1; i >= 0; i--)
      {
        int j = indexOf(paramArrayOfTabItem[i]);
        if (j != -1)
          setSelection(j, false);
      }
  }

  public void setFont(Font paramFont)
  {
    checkWidget();
    Rectangle localRectangle1 = getClientArea();
    super.setFont(paramFont);
    Rectangle localRectangle2 = getClientArea();
    if (!localRectangle1.equals(localRectangle2))
    {
      sendResize();
      int i = OS.SendMessage(this.handle, 4875, 0, 0);
      if (i != -1)
      {
        TabItem localTabItem = this.items[i];
        Control localControl = localTabItem.control;
        if ((localControl != null) && (!localControl.isDisposed()))
          localControl.setBounds(getClientArea());
      }
    }
  }

  public void setSelection(int paramInt)
  {
    checkWidget();
    int i = OS.SendMessage(this.handle, 4868, 0, 0);
    if ((paramInt < 0) || (paramInt >= i))
      return;
    setSelection(paramInt, false);
  }

  void setSelection(int paramInt, boolean paramBoolean)
  {
    int i = OS.SendMessage(this.handle, 4875, 0, 0);
    if (i == paramInt)
      return;
    Object localObject;
    if (i != -1)
    {
      TabItem localTabItem = this.items[i];
      localObject = localTabItem.control;
      if ((localObject != null) && (!((Control)localObject).isDisposed()))
        ((Control)localObject).setVisible(false);
    }
    OS.SendMessage(this.handle, 4876, paramInt, 0);
    int j = OS.SendMessage(this.handle, 4875, 0, 0);
    if (j != -1)
    {
      localObject = this.items[j];
      Control localControl = ((TabItem)localObject).control;
      if ((localControl != null) && (!localControl.isDisposed()))
      {
        localControl.setBounds(getClientArea());
        localControl.setVisible(true);
      }
      if (paramBoolean)
      {
        Event localEvent = new Event();
        localEvent.item = ((Widget)localObject);
        sendSelectionEvent(13, localEvent, true);
      }
    }
  }

  String toolTipText(NMTTDISPINFO paramNMTTDISPINFO)
  {
    if ((paramNMTTDISPINFO.uFlags & 0x1) != 0)
      return null;
    int i = paramNMTTDISPINFO.idFrom;
    int j = OS.SendMessage(this.handle, 4909, 0, 0);
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
        TabItem localTabItem = this.items[i];
        if (localTabItem != null)
          return localTabItem.toolTipText;
      }
    }
    return super.toolTipText(paramNMTTDISPINFO);
  }

  boolean traversePage(boolean paramBoolean)
  {
    int i = getItemCount();
    if (i <= 1)
      return false;
    int j = getSelectionIndex();
    if (j == -1)
    {
      j = 0;
    }
    else
    {
      int k = paramBoolean ? 1 : -1;
      j = (j + k + i) % i;
    }
    setSelection(j, true);
    if (j == getSelectionIndex())
    {
      OS.SendMessage(this.handle, 295, 3, 0);
      return true;
    }
    return false;
  }

  int widgetStyle()
  {
    int i = super.widgetStyle() | 0x2000000;
    if ((this.style & 0x80000) != 0)
      i |= 32768;
    if ((this.style & 0x400) != 0)
      i |= 2;
    return i | 0x4000;
  }

  TCHAR windowClass()
  {
    return TabFolderClass;
  }

  int windowProc()
  {
    return TabFolderProc;
  }

  LRESULT WM_GETDLGCODE(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_GETDLGCODE(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    return new LRESULT(8193);
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
      int i = OS.SendMessage(this.handle, 4909, 0, 0);
      if ((OS.SendMessage(i, OS.TTM_GETCURRENTTOOL, 0, localTOOLINFO) != 0) && ((localTOOLINFO.uFlags & 0x1) == 0))
      {
        OS.SendMessage(i, OS.TTM_DELTOOL, 0, localTOOLINFO);
        OS.SendMessage(i, OS.TTM_ADDTOOL, 0, localTOOLINFO);
      }
    }
    return localLRESULT;
  }

  LRESULT WM_NCHITTEST(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_NCHITTEST(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    int i = OS.DefWindowProc(this.handle, 132, paramInt1, paramInt2);
    return new LRESULT(i);
  }

  LRESULT WM_NOTIFY(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_NOTIFY(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    return LRESULT.ZERO;
  }

  LRESULT WM_PARENTNOTIFY(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_PARENTNOTIFY(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    if (OS.WIN32_VERSION < OS.VERSION(4, 10))
      return localLRESULT;
    if ((this.style & 0x4000000) != 0)
    {
      int i = OS.LOWORD(paramInt1);
      switch (i)
      {
      case 1:
        int j = OS.HIWORD(paramInt1);
        int k = paramInt2;
        if (j == 1)
        {
          int m = OS.GetWindowLong(k, -20);
          OS.SetWindowLong(k, -20, m | 0x400000);
        }
        break;
      }
    }
    return localLRESULT;
  }

  LRESULT WM_SIZE(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_SIZE(paramInt1, paramInt2);
    if (isDisposed())
      return localLRESULT;
    int i = OS.SendMessage(this.handle, 4875, 0, 0);
    if (i != -1)
    {
      TabItem localTabItem = this.items[i];
      Control localControl = localTabItem.control;
      if ((localControl != null) && (!localControl.isDisposed()))
        localControl.setBounds(getClientArea());
    }
    return localLRESULT;
  }

  LRESULT WM_WINDOWPOSCHANGING(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_WINDOWPOSCHANGING(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    if (!OS.IsWindowVisible(this.handle))
      return localLRESULT;
    WINDOWPOS localWINDOWPOS = new WINDOWPOS();
    OS.MoveMemory(localWINDOWPOS, paramInt2, WINDOWPOS.sizeof);
    if ((localWINDOWPOS.flags & 0x9) != 0)
      return localLRESULT;
    int i = OS.GetWindowLong(this.handle, -16);
    if ((i & 0x200) != 0)
    {
      OS.InvalidateRect(this.handle, null, true);
      return localLRESULT;
    }
    RECT localRECT1 = new RECT();
    OS.SetRect(localRECT1, 0, 0, localWINDOWPOS.cx, localWINDOWPOS.cy);
    OS.SendMessage(this.handle, 131, 0, localRECT1);
    int j = localRECT1.right - localRECT1.left;
    int k = localRECT1.bottom - localRECT1.top;
    OS.GetClientRect(this.handle, localRECT1);
    int m = localRECT1.right - localRECT1.left;
    int n = localRECT1.bottom - localRECT1.top;
    if ((j == m) && (k == n))
      return localLRESULT;
    RECT localRECT2 = new RECT();
    OS.SendMessage(this.handle, 4904, 0, localRECT2);
    int i1 = -localRECT2.right;
    int i2 = -localRECT2.bottom;
    int i3;
    if (j != m)
    {
      i3 = m;
      if (j < m)
        i3 = j;
      OS.SetRect(localRECT1, i3 - i1, 0, j, k);
      OS.InvalidateRect(this.handle, localRECT1, true);
    }
    if (k != n)
    {
      i3 = n;
      if (k < n)
        i3 = k;
      if (j < m)
        m -= i1;
      OS.SetRect(localRECT1, 0, i3 - i2, m, k);
      OS.InvalidateRect(this.handle, localRECT1, true);
    }
    return localLRESULT;
  }

  LRESULT wmNotifyChild(NMHDR paramNMHDR, int paramInt1, int paramInt2)
  {
    int i = paramNMHDR.code;
    switch (i)
    {
    case -552:
    case -551:
      TabItem localTabItem = null;
      int j = OS.SendMessage(this.handle, 4875, 0, 0);
      if (j != -1)
        localTabItem = this.items[j];
      Object localObject;
      if (localTabItem != null)
      {
        localObject = localTabItem.control;
        if ((localObject != null) && (!((Control)localObject).isDisposed()))
        {
          if (i == -551)
            ((Control)localObject).setBounds(getClientArea());
          ((Control)localObject).setVisible(i == -551);
        }
      }
      if (i == -551)
      {
        localObject = new Event();
        ((Event)localObject).item = localTabItem;
        sendSelectionEvent(13, (Event)localObject, false);
      }
      break;
    }
    return super.wmNotifyChild(paramNMHDR, paramInt1, paramInt2);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.TabFolder
 * JD-Core Version:    0.6.2
 */