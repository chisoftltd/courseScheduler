package org.eclipse.swt.widgets;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.GCData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.Callback;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.MSG;
import org.eclipse.swt.internal.win32.NMHDR;
import org.eclipse.swt.internal.win32.NMTTDISPINFO;
import org.eclipse.swt.internal.win32.NMTTDISPINFOA;
import org.eclipse.swt.internal.win32.NMTTDISPINFOW;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.PAINTSTRUCT;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.WINDOWPOS;

public class Composite extends Scrollable
{
  Layout layout;
  WINDOWPOS[] lpwp;
  Control[] tabList;
  int layoutCount;
  int backgroundMode;

  Composite()
  {
  }

  public Composite(Composite paramComposite, int paramInt)
  {
    super(paramComposite, paramInt);
  }

  Control[] _getChildren()
  {
    int i = 0;
    int j = OS.GetWindow(this.handle, 5);
    if (j == 0)
      return new Control[0];
    while (j != 0)
    {
      i++;
      j = OS.GetWindow(j, 2);
    }
    Control[] arrayOfControl = new Control[i];
    int k = 0;
    for (j = OS.GetWindow(this.handle, 5); j != 0; j = OS.GetWindow(j, 2))
    {
      localObject = this.display.getControl(j);
      if ((localObject != null) && (localObject != this))
        arrayOfControl[(k++)] = localObject;
    }
    if (i == k)
      return arrayOfControl;
    Object localObject = new Control[k];
    System.arraycopy(arrayOfControl, 0, localObject, 0, k);
    return localObject;
  }

  Control[] _getTabList()
  {
    if (this.tabList == null)
      return this.tabList;
    int i = 0;
    for (int j = 0; j < this.tabList.length; j++)
      if (!this.tabList[j].isDisposed())
        i++;
    if (i == this.tabList.length)
      return this.tabList;
    Control[] arrayOfControl = new Control[i];
    int k = 0;
    for (int m = 0; m < this.tabList.length; m++)
      if (!this.tabList[m].isDisposed())
        arrayOfControl[(k++)] = this.tabList[m];
    this.tabList = arrayOfControl;
    return this.tabList;
  }

  public void changed(Control[] paramArrayOfControl)
  {
    checkWidget();
    if (paramArrayOfControl == null)
      error(5);
    Object localObject;
    for (int i = 0; i < paramArrayOfControl.length; i++)
    {
      localObject = paramArrayOfControl[i];
      if (localObject == null)
        error(5);
      if (((Control)localObject).isDisposed())
        error(5);
      int j = 0;
      for (Composite localComposite2 = ((Control)localObject).parent; localComposite2 != null; localComposite2 = localComposite2.parent)
      {
        j = localComposite2 == this ? 1 : 0;
        if (j != 0)
          break;
      }
      if (j == 0)
        error(32);
    }
    for (i = 0; i < paramArrayOfControl.length; i++)
    {
      localObject = paramArrayOfControl[i];
      for (Composite localComposite1 = ((Control)localObject).parent; localObject != this; localComposite1 = ((Control)localObject).parent)
      {
        if ((localComposite1.layout == null) || (!localComposite1.layout.flushCache((Control)localObject)))
          localComposite1.state |= 64;
        localObject = localComposite1;
      }
    }
  }

  void checkBuffered()
  {
    if ((OS.IsWinCE) || ((this.state & 0x2) == 0))
      super.checkBuffered();
  }

  void checkComposited()
  {
    if (((this.state & 0x2) != 0) && ((this.style & 0x40000000) != 0))
    {
      int i = this.parent.handle;
      int j = OS.GetWindowLong(i, -20);
      j |= 33554432;
      OS.SetWindowLong(i, -20, j);
    }
  }

  protected void checkSubclass()
  {
  }

  Widget[] computeTabList()
  {
    Object localObject = super.computeTabList();
    if (localObject.length == 0)
      return localObject;
    Control[] arrayOfControl = this.tabList != null ? _getTabList() : _getChildren();
    for (int i = 0; i < arrayOfControl.length; i++)
    {
      Control localControl = arrayOfControl[i];
      Widget[] arrayOfWidget1 = localControl.computeTabList();
      if (arrayOfWidget1.length != 0)
      {
        Widget[] arrayOfWidget2 = new Widget[localObject.length + arrayOfWidget1.length];
        System.arraycopy(localObject, 0, arrayOfWidget2, 0, localObject.length);
        System.arraycopy(arrayOfWidget1, 0, arrayOfWidget2, localObject.length, arrayOfWidget1.length);
        localObject = arrayOfWidget2;
      }
    }
    return localObject;
  }

  public Point computeSize(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    checkWidget();
    this.display.runSkin();
    Point localPoint;
    if (this.layout != null)
    {
      if ((paramInt1 == -1) || (paramInt2 == -1))
      {
        paramBoolean |= (this.state & 0x40) != 0;
        this.state &= -65;
        localPoint = this.layout.computeSize(this, paramInt1, paramInt2, paramBoolean);
      }
      else
      {
        localPoint = new Point(paramInt1, paramInt2);
      }
    }
    else
      localPoint = minimumSize(paramInt1, paramInt2, paramBoolean);
    if (localPoint.x == 0)
      localPoint.x = 64;
    if (localPoint.y == 0)
      localPoint.y = 64;
    if (paramInt1 != -1)
      localPoint.x = paramInt1;
    if (paramInt2 != -1)
      localPoint.y = paramInt2;
    Rectangle localRectangle = computeTrim(0, 0, localPoint.x, localPoint.y);
    return new Point(localRectangle.width, localRectangle.height);
  }

  void copyArea(GC paramGC, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    checkWidget();
    if (paramGC == null)
      error(4);
    if (paramGC.isDisposed())
      error(5);
    int i = paramGC.handle;
    int j = OS.SaveDC(i);
    OS.IntersectClipRect(i, 0, 0, paramInt3, paramInt4);
    POINT localPOINT1 = new POINT();
    int k = OS.GetParent(this.handle);
    OS.MapWindowPoints(this.handle, k, localPOINT1, 1);
    RECT localRECT = new RECT();
    OS.GetWindowRect(this.handle, localRECT);
    POINT localPOINT2 = new POINT();
    POINT localPOINT3 = new POINT();
    paramInt1 += localPOINT1.x - localRECT.left;
    paramInt2 += localPOINT1.y - localRECT.top;
    OS.SetWindowOrgEx(i, paramInt1, paramInt2, localPOINT2);
    OS.SetBrushOrgEx(i, paramInt1, paramInt2, localPOINT3);
    int m = OS.GetWindowLong(this.handle, -16);
    if ((m & 0x10000000) == 0)
      OS.DefWindowProc(this.handle, 11, 1, 0);
    OS.RedrawWindow(this.handle, null, 0, 384);
    OS.PrintWindow(this.handle, i, 0);
    if ((m & 0x10000000) == 0)
      OS.DefWindowProc(this.handle, 11, 0, 0);
    OS.RestoreDC(i, j);
  }

  void createHandle()
  {
    super.createHandle();
    this.state |= 2;
    if ((this.style & 0x300) == 0)
      this.state |= 256;
    if ((this.style & 0x40000000) != 0)
    {
      int i = OS.GetWindowLong(this.handle, -20);
      i |= 32;
      OS.SetWindowLong(this.handle, -20, i);
    }
  }

  public void drawBackground(GC paramGC, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    checkWidget();
    if (paramGC == null)
      error(4);
    if (paramGC.isDisposed())
      error(5);
    RECT localRECT = new RECT();
    OS.SetRect(localRECT, paramInt1, paramInt2, paramInt1 + paramInt3, paramInt2 + paramInt4);
    int i = paramGC.handle;
    int j = this.background == -1 ? paramGC.getBackground().handle : -1;
    drawBackground(i, localRECT, j, paramInt5, paramInt6);
  }

  Composite findDeferredControl()
  {
    return this.layoutCount > 0 ? this : this.parent.findDeferredControl();
  }

  Menu[] findMenus(Control paramControl)
  {
    if (paramControl == this)
      return new Menu[0];
    Object localObject = super.findMenus(paramControl);
    Control[] arrayOfControl = _getChildren();
    for (int i = 0; i < arrayOfControl.length; i++)
    {
      Control localControl = arrayOfControl[i];
      Menu[] arrayOfMenu1 = localControl.findMenus(paramControl);
      if (arrayOfMenu1.length != 0)
      {
        Menu[] arrayOfMenu2 = new Menu[localObject.length + arrayOfMenu1.length];
        System.arraycopy(localObject, 0, arrayOfMenu2, 0, localObject.length);
        System.arraycopy(arrayOfMenu1, 0, arrayOfMenu2, localObject.length, arrayOfMenu1.length);
        localObject = arrayOfMenu2;
      }
    }
    return localObject;
  }

  void fixChildren(Shell paramShell1, Shell paramShell2, Decorations paramDecorations1, Decorations paramDecorations2, Menu[] paramArrayOfMenu)
  {
    super.fixChildren(paramShell1, paramShell2, paramDecorations1, paramDecorations2, paramArrayOfMenu);
    Control[] arrayOfControl = _getChildren();
    for (int i = 0; i < arrayOfControl.length; i++)
      arrayOfControl[i].fixChildren(paramShell1, paramShell2, paramDecorations1, paramDecorations2, paramArrayOfMenu);
  }

  void fixTabList(Control paramControl)
  {
    if (this.tabList == null)
      return;
    int i = 0;
    for (int j = 0; j < this.tabList.length; j++)
      if (this.tabList[j] == paramControl)
        i++;
    if (i == 0)
      return;
    Control[] arrayOfControl = (Control[])null;
    int k = this.tabList.length - i;
    if (k != 0)
    {
      arrayOfControl = new Control[k];
      int m = 0;
      for (int n = 0; n < this.tabList.length; n++)
        if (this.tabList[n] != paramControl)
          arrayOfControl[(m++)] = this.tabList[n];
    }
    this.tabList = arrayOfControl;
  }

  public int getBackgroundMode()
  {
    checkWidget();
    return this.backgroundMode;
  }

  public Control[] getChildren()
  {
    checkWidget();
    return _getChildren();
  }

  int getChildrenCount()
  {
    int i = 0;
    for (int j = OS.GetWindow(this.handle, 5); j != 0; j = OS.GetWindow(j, 2))
      i++;
    return i;
  }

  public Layout getLayout()
  {
    checkWidget();
    return this.layout;
  }

  public Control[] getTabList()
  {
    checkWidget();
    Control[] arrayOfControl1 = _getTabList();
    if (arrayOfControl1 == null)
    {
      int i = 0;
      Control[] arrayOfControl2 = _getChildren();
      for (int j = 0; j < arrayOfControl2.length; j++)
        if (arrayOfControl2[j].isTabGroup())
          i++;
      arrayOfControl1 = new Control[i];
      j = 0;
      for (int k = 0; k < arrayOfControl2.length; k++)
        if (arrayOfControl2[k].isTabGroup())
          arrayOfControl1[(j++)] = arrayOfControl2[k];
    }
    return arrayOfControl1;
  }

  boolean hooksKeys()
  {
    return (hooks(1)) || (hooks(2));
  }

  public boolean getLayoutDeferred()
  {
    checkWidget();
    return this.layoutCount > 0;
  }

  public boolean isLayoutDeferred()
  {
    checkWidget();
    return findDeferredControl() != null;
  }

  public void layout()
  {
    checkWidget();
    layout(true);
  }

  public void layout(boolean paramBoolean)
  {
    checkWidget();
    if (this.layout == null)
      return;
    layout(paramBoolean, false);
  }

  public void layout(boolean paramBoolean1, boolean paramBoolean2)
  {
    checkWidget();
    if ((this.layout == null) && (!paramBoolean2))
      return;
    markLayout(paramBoolean1, paramBoolean2);
    updateLayout(paramBoolean2);
  }

  public void layout(Control[] paramArrayOfControl)
  {
    checkWidget();
    if (paramArrayOfControl == null)
      error(5);
    layout(paramArrayOfControl, 0);
  }

  public void layout(Control[] paramArrayOfControl, int paramInt)
  {
    checkWidget();
    if (paramArrayOfControl != null)
    {
      Object localObject2;
      for (int i = 0; i < paramArrayOfControl.length; i++)
      {
        localObject1 = paramArrayOfControl[i];
        if (localObject1 == null)
          error(5);
        if (((Control)localObject1).isDisposed())
          error(5);
        j = 0;
        for (localObject2 = ((Control)localObject1).parent; localObject2 != null; localObject2 = ((Composite)localObject2).parent)
        {
          j = localObject2 == this ? 1 : 0;
          if (j != 0)
            break;
        }
        if (j == 0)
          error(32);
      }
      i = 0;
      Object localObject1 = new Composite[16];
      for (int j = 0; j < paramArrayOfControl.length; j++)
      {
        localObject2 = paramArrayOfControl[j];
        for (Composite localComposite = ((Control)localObject2).parent; localObject2 != this; localComposite = ((Control)localObject2).parent)
        {
          if (localComposite.layout != null)
          {
            localComposite.state |= 32;
            if (!localComposite.layout.flushCache((Control)localObject2))
              localComposite.state |= 64;
          }
          if (i == localObject1.length)
          {
            Composite[] arrayOfComposite = new Composite[localObject1.length + 16];
            System.arraycopy(localObject1, 0, arrayOfComposite, 0, localObject1.length);
            localObject1 = arrayOfComposite;
          }
          localObject2 = localObject1[(i++)] =  = localComposite;
        }
      }
      if ((paramInt & 0x4) != 0)
      {
        setLayoutDeferred(true);
        this.display.addLayoutDeferred(this);
      }
      for (j = i - 1; j >= 0; j--)
        localObject1[j].updateLayout(false);
    }
    else
    {
      if ((this.layout == null) && ((paramInt & 0x1) == 0))
        return;
      markLayout((paramInt & 0x2) != 0, (paramInt & 0x1) != 0);
      if ((paramInt & 0x4) != 0)
      {
        setLayoutDeferred(true);
        this.display.addLayoutDeferred(this);
      }
      updateLayout((paramInt & 0x1) != 0);
    }
  }

  void markLayout(boolean paramBoolean1, boolean paramBoolean2)
  {
    if (this.layout != null)
    {
      this.state |= 32;
      if (paramBoolean1)
        this.state |= 64;
    }
    if (paramBoolean2)
    {
      Control[] arrayOfControl = _getChildren();
      for (int i = 0; i < arrayOfControl.length; i++)
        arrayOfControl[i].markLayout(paramBoolean1, paramBoolean2);
    }
  }

  Point minimumSize(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    Control[] arrayOfControl = _getChildren();
    int i = 0;
    int j = 0;
    for (int k = 0; k < arrayOfControl.length; k++)
    {
      Rectangle localRectangle = arrayOfControl[k].getBounds();
      i = Math.max(i, localRectangle.x + localRectangle.width);
      j = Math.max(j, localRectangle.y + localRectangle.height);
    }
    return new Point(i, j);
  }

  boolean redrawChildren()
  {
    if (!super.redrawChildren())
      return false;
    Control[] arrayOfControl = _getChildren();
    for (int i = 0; i < arrayOfControl.length; i++)
      arrayOfControl[i].redrawChildren();
    return true;
  }

  void releaseParent()
  {
    super.releaseParent();
    if (((this.state & 0x2) != 0) && ((this.style & 0x40000000) != 0))
    {
      int i = this.parent.handle;
      for (int j = OS.GetWindow(i, 5); j != 0; j = OS.GetWindow(j, 2))
        if (j != this.handle)
        {
          k = OS.GetWindowLong(i, -20);
          if ((k & 0x20) != 0)
            return;
        }
      int k = OS.GetWindowLong(i, -20);
      k &= -33554433;
      OS.SetWindowLong(i, -20, k);
    }
  }

  void releaseChildren(boolean paramBoolean)
  {
    Control[] arrayOfControl = _getChildren();
    for (int i = 0; i < arrayOfControl.length; i++)
    {
      Control localControl = arrayOfControl[i];
      if ((localControl != null) && (!localControl.isDisposed()))
        localControl.release(false);
    }
    super.releaseChildren(paramBoolean);
  }

  void releaseWidget()
  {
    super.releaseWidget();
    if (((this.state & 0x2) != 0) && ((this.style & 0x1000000) != 0))
    {
      int i = OS.GetWindow(this.handle, 5);
      if (i != 0)
      {
        int j = OS.GetWindowThreadProcessId(i, null);
        if (j != OS.GetCurrentThreadId())
        {
          OS.ShowWindow(i, 0);
          OS.SetParent(i, 0);
        }
      }
    }
    this.layout = null;
    this.tabList = null;
    this.lpwp = null;
  }

  void removeControl(Control paramControl)
  {
    fixTabList(paramControl);
    resizeChildren();
  }

  void reskinChildren(int paramInt)
  {
    super.reskinChildren(paramInt);
    Control[] arrayOfControl = _getChildren();
    for (int i = 0; i < arrayOfControl.length; i++)
    {
      Control localControl = arrayOfControl[i];
      if (localControl != null)
        localControl.reskin(paramInt);
    }
  }

  void resizeChildren()
  {
    if (this.lpwp == null)
      return;
    do
    {
      WINDOWPOS[] arrayOfWINDOWPOS = this.lpwp;
      this.lpwp = null;
      if (!resizeChildren(true, arrayOfWINDOWPOS))
        resizeChildren(false, arrayOfWINDOWPOS);
    }
    while (this.lpwp != null);
  }

  boolean resizeChildren(boolean paramBoolean, WINDOWPOS[] paramArrayOfWINDOWPOS)
  {
    if (paramArrayOfWINDOWPOS == null)
      return true;
    int i = 0;
    if (paramBoolean)
    {
      i = OS.BeginDeferWindowPos(paramArrayOfWINDOWPOS.length);
      if (i == 0)
        return false;
    }
    for (int j = 0; j < paramArrayOfWINDOWPOS.length; j++)
    {
      WINDOWPOS localWINDOWPOS = paramArrayOfWINDOWPOS[j];
      if (localWINDOWPOS != null)
        if (paramBoolean)
        {
          i = DeferWindowPos(i, localWINDOWPOS.hwnd, 0, localWINDOWPOS.x, localWINDOWPOS.y, localWINDOWPOS.cx, localWINDOWPOS.cy, localWINDOWPOS.flags);
          if (i == 0)
            return false;
        }
        else
        {
          SetWindowPos(localWINDOWPOS.hwnd, 0, localWINDOWPOS.x, localWINDOWPOS.y, localWINDOWPOS.cx, localWINDOWPOS.cy, localWINDOWPOS.flags);
        }
    }
    if (paramBoolean)
      return OS.EndDeferWindowPos(i);
    return true;
  }

  void resizeEmbeddedHandle(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt1 == 0)
      return;
    int[] arrayOfInt = new int[1];
    int i = OS.GetWindowThreadProcessId(paramInt1, arrayOfInt);
    if (i != OS.GetCurrentThreadId())
    {
      if ((arrayOfInt[0] == OS.GetCurrentProcessId()) && (this.display.msgHook == 0) && (!OS.IsWinCE))
      {
        this.display.getMsgCallback = new Callback(this.display, "getMsgProc", 3);
        this.display.getMsgProc = this.display.getMsgCallback.getAddress();
        if (this.display.getMsgProc == 0)
          error(3);
        this.display.msgHook = OS.SetWindowsHookEx(3, this.display.getMsgProc, OS.GetLibraryHandle(), i);
        OS.PostThreadMessage(i, 0, 0, 0);
      }
      int j = 16436;
      OS.SetWindowPos(paramInt1, 0, 0, 0, paramInt2, paramInt3, j);
    }
  }

  void sendResize()
  {
    setResizeChildren(false);
    super.sendResize();
    if (isDisposed())
      return;
    if (this.layout != null)
    {
      markLayout(false, false);
      updateLayout(false, false);
    }
    setResizeChildren(true);
  }

  public void setBackgroundMode(int paramInt)
  {
    checkWidget();
    this.backgroundMode = paramInt;
    Control[] arrayOfControl = _getChildren();
    for (int i = 0; i < arrayOfControl.length; i++)
      arrayOfControl[i].updateBackgroundMode();
  }

  void setBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, boolean paramBoolean)
  {
    if (this.display.resizeCount > 4)
      paramBoolean = false;
    if ((!paramBoolean) && ((this.state & 0x2) != 0))
    {
      this.state &= -327681;
      this.state |= 655360;
    }
    super.setBounds(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramBoolean);
    if ((!paramBoolean) && ((this.state & 0x2) != 0))
    {
      int i = (this.state & 0x10000) != 0 ? 1 : 0;
      int j = (this.state & 0x40000) != 0 ? 1 : 0;
      this.state &= -655361;
      if ((i != 0) && (!isDisposed()))
        sendMove();
      if ((j != 0) && (!isDisposed()))
        sendResize();
    }
  }

  boolean setFixedFocus()
  {
    checkWidget();
    Control[] arrayOfControl = _getChildren();
    Control localControl;
    for (int i = 0; i < arrayOfControl.length; i++)
    {
      localControl = arrayOfControl[i];
      if (localControl.setRadioFocus(false))
        return true;
    }
    for (i = 0; i < arrayOfControl.length; i++)
    {
      localControl = arrayOfControl[i];
      if (localControl.setFixedFocus())
        return true;
    }
    return super.setFixedFocus();
  }

  public boolean setFocus()
  {
    checkWidget();
    Control[] arrayOfControl = _getChildren();
    Control localControl;
    for (int i = 0; i < arrayOfControl.length; i++)
    {
      localControl = arrayOfControl[i];
      if (localControl.setRadioFocus(false))
        return true;
    }
    for (i = 0; i < arrayOfControl.length; i++)
    {
      localControl = arrayOfControl[i];
      if (localControl.setFocus())
        return true;
    }
    return super.setFocus();
  }

  public void setLayout(Layout paramLayout)
  {
    checkWidget();
    this.layout = paramLayout;
  }

  public void setLayoutDeferred(boolean paramBoolean)
  {
    if (!paramBoolean)
    {
      if ((--this.layoutCount == 0) && (((this.state & 0x80) != 0) || ((this.state & 0x20) != 0)))
        updateLayout(true);
    }
    else
      this.layoutCount += 1;
  }

  public void setTabList(Control[] paramArrayOfControl)
  {
    checkWidget();
    if (paramArrayOfControl != null)
    {
      for (int i = 0; i < paramArrayOfControl.length; i++)
      {
        Control localControl = paramArrayOfControl[i];
        if (localControl == null)
          error(5);
        if (localControl.isDisposed())
          error(5);
        if (localControl.parent != this)
          error(32);
      }
      Control[] arrayOfControl = new Control[paramArrayOfControl.length];
      System.arraycopy(paramArrayOfControl, 0, arrayOfControl, 0, paramArrayOfControl.length);
      paramArrayOfControl = arrayOfControl;
    }
    this.tabList = paramArrayOfControl;
  }

  void setResizeChildren(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      resizeChildren();
    }
    else
    {
      if (this.display.resizeCount > 4)
        return;
      int i = getChildrenCount();
      if ((i > 1) && (this.lpwp == null))
        this.lpwp = new WINDOWPOS[i];
    }
  }

  boolean setTabGroupFocus()
  {
    if (isTabItem())
      return setTabItemFocus();
    boolean bool = (this.style & 0x80000) == 0;
    if ((this.state & 0x2) != 0)
    {
      bool = hooksKeys();
      if ((this.style & 0x1000000) != 0)
        bool = true;
    }
    if ((bool) && (setTabItemFocus()))
      return true;
    Control[] arrayOfControl = _getChildren();
    Control localControl;
    for (int i = 0; i < arrayOfControl.length; i++)
    {
      localControl = arrayOfControl[i];
      if ((localControl.isTabItem()) && (localControl.setRadioFocus(true)))
        return true;
    }
    for (i = 0; i < arrayOfControl.length; i++)
    {
      localControl = arrayOfControl[i];
      if ((localControl.isTabItem()) && (!localControl.isTabGroup()) && (localControl.setTabItemFocus()))
        return true;
    }
    return false;
  }

  String toolTipText(NMTTDISPINFO paramNMTTDISPINFO)
  {
    Shell localShell = getShell();
    if ((paramNMTTDISPINFO.uFlags & 0x1) == 0)
    {
      localObject = null;
      ToolTip localToolTip = localShell.findToolTip(paramNMTTDISPINFO.idFrom);
      if (localToolTip != null)
      {
        localObject = localToolTip.message;
        if ((localObject == null) || (((String)localObject).length() == 0))
          localObject = " ";
      }
      return localObject;
    }
    localShell.setToolTipTitle(paramNMTTDISPINFO.hwndFrom, null, 0);
    OS.SendMessage(paramNMTTDISPINFO.hwndFrom, 1048, 0, 32767);
    Object localObject = this.display.getControl(paramNMTTDISPINFO.idFrom);
    return localObject != null ? ((Control)localObject).toolTipText : null;
  }

  boolean translateMnemonic(Event paramEvent, Control paramControl)
  {
    if (super.translateMnemonic(paramEvent, paramControl))
      return true;
    if (paramControl != null)
    {
      Control[] arrayOfControl = _getChildren();
      for (int i = 0; i < arrayOfControl.length; i++)
      {
        Control localControl = arrayOfControl[i];
        if (localControl.translateMnemonic(paramEvent, paramControl))
          return true;
      }
    }
    return false;
  }

  boolean translateTraversal(MSG paramMSG)
  {
    if ((this.state & 0x2) != 0)
    {
      if ((this.style & 0x1000000) != 0)
        return false;
      switch (paramMSG.wParam)
      {
      case 33:
      case 34:
      case 37:
      case 38:
      case 39:
      case 40:
        int i = OS.SendMessage(paramMSG.hwnd, 297, 0, 0);
        if ((i & 0x1) != 0)
          OS.SendMessage(paramMSG.hwnd, 296, OS.MAKEWPARAM(2, 1), 0);
        break;
      case 35:
      case 36:
      }
    }
    return super.translateTraversal(paramMSG);
  }

  void updateBackgroundColor()
  {
    super.updateBackgroundColor();
    Control[] arrayOfControl = _getChildren();
    for (int i = 0; i < arrayOfControl.length; i++)
      if ((arrayOfControl[i].state & 0x400) != 0)
        arrayOfControl[i].updateBackgroundColor();
  }

  void updateBackgroundImage()
  {
    super.updateBackgroundImage();
    Control[] arrayOfControl = _getChildren();
    for (int i = 0; i < arrayOfControl.length; i++)
      if ((arrayOfControl[i].state & 0x400) != 0)
        arrayOfControl[i].updateBackgroundImage();
  }

  void updateBackgroundMode()
  {
    super.updateBackgroundMode();
    Control[] arrayOfControl = _getChildren();
    for (int i = 0; i < arrayOfControl.length; i++)
      arrayOfControl[i].updateBackgroundMode();
  }

  void updateFont(Font paramFont1, Font paramFont2)
  {
    super.updateFont(paramFont1, paramFont2);
    Control[] arrayOfControl = _getChildren();
    for (int i = 0; i < arrayOfControl.length; i++)
    {
      Control localControl = arrayOfControl[i];
      if (!localControl.isDisposed())
        localControl.updateFont(paramFont1, paramFont2);
    }
  }

  void updateLayout(boolean paramBoolean)
  {
    updateLayout(true, paramBoolean);
  }

  void updateLayout(boolean paramBoolean1, boolean paramBoolean2)
  {
    Composite localComposite = findDeferredControl();
    if (localComposite != null)
    {
      localComposite.state |= 128;
      return;
    }
    if ((this.state & 0x20) != 0)
    {
      boolean bool = (this.state & 0x40) != 0;
      this.state &= -97;
      this.display.runSkin();
      if (paramBoolean1)
        setResizeChildren(false);
      this.layout.layout(this, bool);
      if (paramBoolean1)
        setResizeChildren(true);
    }
    if (paramBoolean2)
    {
      this.state &= -129;
      Control[] arrayOfControl = _getChildren();
      for (int i = 0; i < arrayOfControl.length; i++)
        arrayOfControl[i].updateLayout(paramBoolean1, paramBoolean2);
    }
  }

  void updateUIState()
  {
    int i = getShell().handle;
    int j = OS.SendMessage(i, 297, 0, 0);
    if ((j & 0x1) != 0)
      OS.SendMessage(i, 295, OS.MAKEWPARAM(2, 1), 0);
  }

  int widgetStyle()
  {
    return super.widgetStyle() | 0x2000000;
  }

  LRESULT WM_ERASEBKGND(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_ERASEBKGND(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    if (((this.state & 0x2) != 0) && ((this.style & 0x40040000) != 0))
      return LRESULT.ZERO;
    return localLRESULT;
  }

  LRESULT WM_GETDLGCODE(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_GETDLGCODE(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    if ((this.state & 0x2) != 0)
    {
      int i = 0;
      if (hooksKeys())
        i |= 7;
      if ((this.style & 0x80000) != 0)
        i |= 256;
      if (OS.GetWindow(this.handle, 5) != 0)
        i |= 256;
      if (i != 0)
        return new LRESULT(i);
    }
    return localLRESULT;
  }

  LRESULT WM_GETFONT(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_GETFONT(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    int i = callWindowProc(this.handle, 49, paramInt1, paramInt2);
    if (i != 0)
      return new LRESULT(i);
    return new LRESULT(this.font != null ? this.font.handle : defaultFont());
  }

  LRESULT WM_LBUTTONDOWN(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_LBUTTONDOWN(paramInt1, paramInt2);
    if (localLRESULT == LRESULT.ZERO)
      return localLRESULT;
    if (((this.state & 0x2) != 0) && ((this.style & 0x80000) == 0) && (hooksKeys()) && (OS.GetWindow(this.handle, 5) == 0))
      setFocus();
    return localLRESULT;
  }

  LRESULT WM_NCHITTEST(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_NCHITTEST(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    if ((!OS.IsWinCE) && (OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()) && ((this.state & 0x2) != 0))
    {
      int i = callWindowProc(this.handle, 132, paramInt1, paramInt2);
      if (i == 1)
      {
        RECT localRECT = new RECT();
        OS.GetClientRect(this.handle, localRECT);
        POINT localPOINT = new POINT();
        localPOINT.x = OS.GET_X_LPARAM(paramInt2);
        localPOINT.y = OS.GET_Y_LPARAM(paramInt2);
        OS.MapWindowPoints(0, this.handle, localPOINT, 1);
        if (!OS.PtInRect(localRECT, localPOINT))
        {
          int j = 1025;
          OS.RedrawWindow(this.handle, null, 0, j);
        }
      }
      return new LRESULT(i);
    }
    return localLRESULT;
  }

  LRESULT WM_PARENTNOTIFY(int paramInt1, int paramInt2)
  {
    if (((this.state & 0x2) != 0) && ((this.style & 0x1000000) != 0) && (OS.LOWORD(paramInt1) == 1))
    {
      RECT localRECT = new RECT();
      OS.GetClientRect(this.handle, localRECT);
      resizeEmbeddedHandle(paramInt2, localRECT.right - localRECT.left, localRECT.bottom - localRECT.top);
    }
    return super.WM_PARENTNOTIFY(paramInt1, paramInt2);
  }

  LRESULT WM_PAINT(int paramInt1, int paramInt2)
  {
    if (((this.state & 0x2) == 0) || ((this.state & 0x4000) != 0))
      return super.WM_PAINT(paramInt1, paramInt2);
    int i = 0;
    int j = 0;
    if (!OS.IsWinCE)
    {
      i = OS.GetWindowLong(this.handle, -16);
      j = i | 0x4000000 | 0x2000000;
      if (j != i)
        OS.SetWindowLong(this.handle, -16, j);
    }
    PAINTSTRUCT localPAINTSTRUCT = new PAINTSTRUCT();
    int k;
    Object localObject1;
    if ((hooks(9)) || (filters(9)))
    {
      k = 0;
      if (((this.style & 0x20000000) != 0) && (!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(6, 0)) && ((this.style & 0x4200000) == 0) && ((this.style & 0x40000000) == 0))
        k = 1;
      int i1;
      Object localObject4;
      Object localObject5;
      Object localObject7;
      if (k != 0)
      {
        int m = OS.BeginPaint(this.handle, localPAINTSTRUCT);
        int n = localPAINTSTRUCT.right - localPAINTSTRUCT.left;
        i1 = localPAINTSTRUCT.bottom - localPAINTSTRUCT.top;
        if ((n != 0) && (i1 != 0))
        {
          int[] arrayOfInt1 = new int[1];
          int i4 = 0;
          RECT localRECT = new RECT();
          OS.SetRect(localRECT, localPAINTSTRUCT.left, localPAINTSTRUCT.top, localPAINTSTRUCT.right, localPAINTSTRUCT.bottom);
          int i7 = OS.BeginBufferedPaint(m, localRECT, i4, null, arrayOfInt1);
          localObject4 = new GCData();
          ((GCData)localObject4).device = this.display;
          ((GCData)localObject4).foreground = getForegroundPixel();
          localObject5 = findBackgroundControl();
          if (localObject5 == null)
            localObject5 = this;
          ((GCData)localObject4).background = ((Control)localObject5).getBackgroundPixel();
          ((GCData)localObject4).font = Font.win32_new(this.display, OS.SendMessage(this.handle, 49, 0, 0));
          ((GCData)localObject4).uiState = OS.SendMessage(this.handle, 297, 0, 0);
          if ((this.style & 0x40000) == 0)
          {
            localObject6 = new RECT();
            OS.SetRect((RECT)localObject6, localPAINTSTRUCT.left, localPAINTSTRUCT.top, localPAINTSTRUCT.right, localPAINTSTRUCT.bottom);
            drawBackground(arrayOfInt1[0], (RECT)localObject6);
          }
          Object localObject6 = GC.win32_new(arrayOfInt1[0], (GCData)localObject4);
          localObject7 = new Event();
          ((Event)localObject7).gc = ((GC)localObject6);
          ((Event)localObject7).x = localPAINTSTRUCT.left;
          ((Event)localObject7).y = localPAINTSTRUCT.top;
          ((Event)localObject7).width = n;
          ((Event)localObject7).height = i1;
          sendEvent(9, (Event)localObject7);
          if ((((GCData)localObject4).focusDrawn) && (!isDisposed()))
            updateUIState();
          ((GC)localObject6).dispose();
          OS.EndBufferedPaint(i7, true);
        }
        OS.EndPaint(this.handle, localPAINTSTRUCT);
      }
      else
      {
        localObject1 = new GCData();
        ((GCData)localObject1).ps = localPAINTSTRUCT;
        ((GCData)localObject1).hwnd = this.handle;
        Object localObject2 = GC.win32_new(this, (GCData)localObject1);
        i1 = 0;
        if (((this.style & 0x60000000) != 0) || ((this.style & 0x200000) != 0))
        {
          i1 = OS.CreateRectRgn(0, 0, 0, 0);
          if (OS.GetRandomRgn(((GC)localObject2).handle, i1, 4) == 1)
          {
            if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(4, 10)) && ((OS.GetLayout(((GC)localObject2).handle) & 0x1) != 0))
            {
              int i2 = OS.GetRegionData(i1, 0, null);
              int[] arrayOfInt2 = new int[i2 / 4];
              OS.GetRegionData(i1, i2, arrayOfInt2);
              int i6 = OS.ExtCreateRegion(new float[] { -1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F }, i2, arrayOfInt2);
              OS.DeleteObject(i1);
              i1 = i6;
            }
            if (OS.IsWinNT)
            {
              POINT localPOINT = new POINT();
              OS.MapWindowPoints(0, this.handle, localPOINT, 1);
              OS.OffsetRgn(i1, localPOINT.x, localPOINT.y);
            }
          }
        }
        int i3 = localPAINTSTRUCT.right - localPAINTSTRUCT.left;
        int i5 = localPAINTSTRUCT.bottom - localPAINTSTRUCT.top;
        if ((i3 != 0) && (i5 != 0))
        {
          Object localObject3 = null;
          Image localImage = null;
          if ((this.style & 0x60000000) != 0)
          {
            localImage = new Image(this.display, i3, i5);
            localObject3 = localObject2;
            localObject2 = new GC(localImage, localObject3.getStyle() & 0x4000000);
            localObject4 = ((GC)localObject2).getGCData();
            ((GCData)localObject4).uiState = ((GCData)localObject1).uiState;
            ((GC)localObject2).setForeground(getForeground());
            ((GC)localObject2).setBackground(getBackground());
            ((GC)localObject2).setFont(getFont());
            if ((this.style & 0x40000000) != 0)
              OS.BitBlt(((GC)localObject2).handle, 0, 0, i3, i5, localObject3.handle, localPAINTSTRUCT.left, localPAINTSTRUCT.top, 13369376);
            OS.OffsetRgn(i1, -localPAINTSTRUCT.left, -localPAINTSTRUCT.top);
            OS.SelectClipRgn(((GC)localObject2).handle, i1);
            OS.OffsetRgn(i1, localPAINTSTRUCT.left, localPAINTSTRUCT.top);
            OS.SetMetaRgn(((GC)localObject2).handle);
            OS.SetWindowOrgEx(((GC)localObject2).handle, localPAINTSTRUCT.left, localPAINTSTRUCT.top, null);
            OS.SetBrushOrgEx(((GC)localObject2).handle, localPAINTSTRUCT.left, localPAINTSTRUCT.top, null);
            if ((this.style & 0x40040000) == 0)
            {
              localObject5 = new RECT();
              OS.SetRect((RECT)localObject5, localPAINTSTRUCT.left, localPAINTSTRUCT.top, localPAINTSTRUCT.right, localPAINTSTRUCT.bottom);
              drawBackground(((GC)localObject2).handle, (RECT)localObject5);
            }
          }
          localObject4 = new Event();
          ((Event)localObject4).gc = ((GC)localObject2);
          localObject5 = null;
          if (((this.style & 0x200000) != 0) && (OS.GetRgnBox(i1, localObject5 = new RECT()) == 3))
          {
            int i8 = OS.GetRegionData(i1, 0, null);
            localObject7 = new int[i8 / 4];
            OS.GetRegionData(i1, i8, (int[])localObject7);
            int i9 = localObject7[2];
            for (int i10 = 0; i10 < i9; i10++)
            {
              int i11 = 8 + (i10 << 2);
              OS.SetRect((RECT)localObject5, localObject7[i11], localObject7[(i11 + 1)], localObject7[(i11 + 2)], localObject7[(i11 + 3)]);
              if ((this.style & 0x60040000) == 0)
                drawBackground(((GC)localObject2).handle, (RECT)localObject5);
              ((Event)localObject4).x = ((RECT)localObject5).left;
              ((Event)localObject4).y = ((RECT)localObject5).top;
              ((Event)localObject4).width = (((RECT)localObject5).right - ((RECT)localObject5).left);
              ((Event)localObject4).height = (((RECT)localObject5).bottom - ((RECT)localObject5).top);
              ((Event)localObject4).count = (i9 - 1 - i10);
              sendEvent(9, (Event)localObject4);
            }
          }
          else
          {
            if ((this.style & 0x60040000) == 0)
            {
              if (localObject5 == null)
                localObject5 = new RECT();
              OS.SetRect((RECT)localObject5, localPAINTSTRUCT.left, localPAINTSTRUCT.top, localPAINTSTRUCT.right, localPAINTSTRUCT.bottom);
              drawBackground(((GC)localObject2).handle, (RECT)localObject5);
            }
            ((Event)localObject4).x = localPAINTSTRUCT.left;
            ((Event)localObject4).y = localPAINTSTRUCT.top;
            ((Event)localObject4).width = i3;
            ((Event)localObject4).height = i5;
            sendEvent(9, (Event)localObject4);
          }
          ((Event)localObject4).gc = null;
          if ((this.style & 0x60000000) != 0)
          {
            if (!((GC)localObject2).isDisposed())
            {
              GCData localGCData = ((GC)localObject2).getGCData();
              if ((localGCData.focusDrawn) && (!isDisposed()))
                updateUIState();
            }
            ((GC)localObject2).dispose();
            if (!isDisposed())
              localObject3.drawImage(localImage, localPAINTSTRUCT.left, localPAINTSTRUCT.top);
            localImage.dispose();
            localObject2 = localObject3;
          }
        }
        if (i1 != 0)
          OS.DeleteObject(i1);
        if ((((GCData)localObject1).focusDrawn) && (!isDisposed()))
          updateUIState();
        ((GC)localObject2).dispose();
      }
    }
    else
    {
      k = OS.BeginPaint(this.handle, localPAINTSTRUCT);
      if ((this.style & 0x40040000) == 0)
      {
        localObject1 = new RECT();
        OS.SetRect((RECT)localObject1, localPAINTSTRUCT.left, localPAINTSTRUCT.top, localPAINTSTRUCT.right, localPAINTSTRUCT.bottom);
        drawBackground(k, (RECT)localObject1);
      }
      OS.EndPaint(this.handle, localPAINTSTRUCT);
    }
    if ((!OS.IsWinCE) && (!isDisposed()) && (j != i) && (!isDisposed()))
      OS.SetWindowLong(this.handle, -16, i);
    return LRESULT.ZERO;
  }

  LRESULT WM_PRINTCLIENT(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_PRINTCLIENT(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    if ((this.state & 0x2) != 0)
    {
      forceResize();
      int i = OS.SaveDC(paramInt1);
      RECT localRECT = new RECT();
      OS.GetClientRect(this.handle, localRECT);
      if ((this.style & 0x40040000) == 0)
        drawBackground(paramInt1, localRECT);
      if ((hooks(9)) || (filters(9)))
      {
        GCData localGCData = new GCData();
        localGCData.device = this.display;
        localGCData.foreground = getForegroundPixel();
        Object localObject = findBackgroundControl();
        if (localObject == null)
          localObject = this;
        localGCData.background = ((Control)localObject).getBackgroundPixel();
        localGCData.font = Font.win32_new(this.display, OS.SendMessage(this.handle, 49, 0, 0));
        localGCData.uiState = OS.SendMessage(this.handle, 297, 0, 0);
        GC localGC = GC.win32_new(paramInt1, localGCData);
        Event localEvent = new Event();
        localEvent.gc = localGC;
        localEvent.x = localRECT.left;
        localEvent.y = localRECT.top;
        localEvent.width = (localRECT.right - localRECT.left);
        localEvent.height = (localRECT.bottom - localRECT.top);
        sendEvent(9, localEvent);
        localEvent.gc = null;
        localGC.dispose();
      }
      OS.RestoreDC(paramInt1, i);
    }
    return localLRESULT;
  }

  LRESULT WM_SETFONT(int paramInt1, int paramInt2)
  {
    if (paramInt2 != 0)
      OS.InvalidateRect(this.handle, null, true);
    return super.WM_SETFONT(paramInt1, paramInt2);
  }

  LRESULT WM_SIZE(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = null;
    if ((this.state & 0x80000) != 0)
    {
      localLRESULT = super.WM_SIZE(paramInt1, paramInt2);
    }
    else
    {
      setResizeChildren(false);
      localLRESULT = super.WM_SIZE(paramInt1, paramInt2);
      if (isDisposed())
        return localLRESULT;
      if (this.layout != null)
      {
        markLayout(false, false);
        updateLayout(false, false);
      }
      setResizeChildren(true);
    }
    if (OS.IsWindowVisible(this.handle))
    {
      if (((this.state & 0x2) != 0) && ((this.style & 0x100000) == 0) && (hooks(9)))
        OS.InvalidateRect(this.handle, null, true);
      if ((OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()) && (findThemeControl() != null))
        redrawChildren();
    }
    if (((this.state & 0x2) != 0) && ((this.style & 0x1000000) != 0))
      resizeEmbeddedHandle(OS.GetWindow(this.handle, 5), OS.LOWORD(paramInt2), OS.HIWORD(paramInt2));
    return localLRESULT;
  }

  LRESULT WM_SYSCOLORCHANGE(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_SYSCOLORCHANGE(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    for (int i = OS.GetWindow(this.handle, 5); i != 0; i = OS.GetWindow(i, 2))
      OS.SendMessage(i, 21, 0, 0);
    return localLRESULT;
  }

  LRESULT WM_SYSCOMMAND(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_SYSCOMMAND(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    if ((paramInt1 & 0xF000) == 0)
      return localLRESULT;
    if (!OS.IsWinCE)
    {
      int i = paramInt1 & 0xFFF0;
      switch (i)
      {
      case 61552:
      case 61568:
        int j = (this.horizontalBar != null) && (this.horizontalBar.getVisible()) ? 1 : 0;
        int k = (this.verticalBar != null) && (this.verticalBar.getVisible()) ? 1 : 0;
        int m = callWindowProc(this.handle, 274, paramInt1, paramInt2);
        if (j == ((this.horizontalBar != null) && (this.horizontalBar.getVisible()) ? 1 : 0))
        {
          if (k == ((this.verticalBar != null) && (this.verticalBar.getVisible()) ? 1 : 0));
        }
        else
        {
          int n = 1281;
          OS.RedrawWindow(this.handle, null, 0, n);
        }
        if (m == 0)
          return LRESULT.ZERO;
        return new LRESULT(m);
      }
    }
    return localLRESULT;
  }

  LRESULT WM_UPDATEUISTATE(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_UPDATEUISTATE(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    if (((this.state & 0x2) != 0) && (hooks(9)))
      OS.InvalidateRect(this.handle, null, true);
    return localLRESULT;
  }

  LRESULT wmNCPaint(int paramInt1, int paramInt2, int paramInt3)
  {
    LRESULT localLRESULT = super.wmNCPaint(paramInt1, paramInt2, paramInt3);
    if (localLRESULT != null)
      return localLRESULT;
    int i = borderHandle();
    if ((((this.state & 0x2) != 0) || ((paramInt1 == i) && (this.handle != i))) && (OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()))
    {
      int j = OS.GetWindowLong(paramInt1, -20);
      if ((j & 0x200) != 0)
      {
        int k = 0;
        int m = OS.GetWindowLong(paramInt1, -16);
        if ((m & 0x300000) != 0)
          k = callWindowProc(paramInt1, 133, paramInt2, paramInt3);
        int n = OS.GetWindowDC(paramInt1);
        RECT localRECT = new RECT();
        OS.GetWindowRect(paramInt1, localRECT);
        localRECT.right -= localRECT.left;
        localRECT.bottom -= localRECT.top;
        localRECT.left = (localRECT.top = 0);
        int i1 = OS.GetSystemMetrics(45);
        OS.ExcludeClipRect(n, i1, i1, localRECT.right - i1, localRECT.bottom - i1);
        OS.DrawThemeBackground(this.display.hEditTheme(), n, 1, 1, localRECT, null);
        OS.ReleaseDC(paramInt1, n);
        return new LRESULT(k);
      }
    }
    return localLRESULT;
  }

  LRESULT wmNotify(NMHDR paramNMHDR, int paramInt1, int paramInt2)
  {
    if (!OS.IsWinCE)
      switch (paramNMHDR.code)
      {
      case -522:
      case -521:
        int i = paramNMHDR.hwndFrom;
        int j;
        do
        {
          i = OS.GetParent(i);
          if (i == 0)
            break;
          j = OS.GetWindowLong(i, -20);
        }
        while ((j & 0x8) == 0);
        if (i == 0)
        {
          this.display.lockActiveWindow = true;
          j = 19;
          int k = paramNMHDR.code == -521 ? -1 : -2;
          SetWindowPos(paramNMHDR.hwndFrom, k, 0, 0, 0, 0, j);
          this.display.lockActiveWindow = false;
        }
        break;
      case -530:
      case -520:
        Object localObject1;
        if (paramNMHDR.code == -520)
        {
          localObject1 = new NMTTDISPINFOA();
          OS.MoveMemory((NMTTDISPINFOA)localObject1, paramInt2, NMTTDISPINFOA.sizeof);
        }
        else
        {
          localObject1 = new NMTTDISPINFOW();
          OS.MoveMemory((NMTTDISPINFOW)localObject1, paramInt2, NMTTDISPINFOW.sizeof);
        }
        String str = toolTipText((NMTTDISPINFO)localObject1);
        if (str != null)
        {
          Shell localShell = getShell();
          str = Display.withCrLf(str);
          char[] arrayOfChar = fixMnemonic(str);
          Object localObject2 = null;
          int m = paramNMHDR.idFrom;
          if ((((NMTTDISPINFO)localObject1).uFlags & 0x1) != 0)
            localObject2 = this.display.getControl(m);
          else if ((paramNMHDR.hwndFrom == localShell.toolTipHandle) || (paramNMHDR.hwndFrom == localShell.balloonTipHandle))
            localObject2 = localShell.findToolTip(paramNMHDR.idFrom);
          if (localObject2 != null)
            if ((((Widget)localObject2).getStyle() & 0x4000000) != 0)
              localObject1.uFlags |= 4;
            else
              localObject1.uFlags &= -5;
          if (paramNMHDR.code == -520)
          {
            byte[] arrayOfByte = new byte[arrayOfChar.length * 2];
            OS.WideCharToMultiByte(getCodePage(), 0, arrayOfChar, arrayOfChar.length, arrayOfByte, arrayOfByte.length, null, null);
            localShell.setToolTipText((NMTTDISPINFO)localObject1, arrayOfByte);
            OS.MoveMemory(paramInt2, (NMTTDISPINFOA)localObject1, NMTTDISPINFOA.sizeof);
          }
          else
          {
            localShell.setToolTipText((NMTTDISPINFO)localObject1, arrayOfChar);
            OS.MoveMemory(paramInt2, (NMTTDISPINFOW)localObject1, NMTTDISPINFOW.sizeof);
          }
          return LRESULT.ZERO;
        }
        break;
      }
    return super.wmNotify(paramNMHDR, paramInt1, paramInt2);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.Composite
 * JD-Core Version:    0.6.2
 */