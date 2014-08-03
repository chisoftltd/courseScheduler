package org.eclipse.swt.widgets;

import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.win32.MARGINS;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.REBARBANDINFO;
import org.eclipse.swt.internal.win32.RECT;

public class CoolItem extends Item
{
  CoolBar parent;
  Control control;
  int id;
  boolean ideal;
  boolean minimum;

  public CoolItem(CoolBar paramCoolBar, int paramInt)
  {
    super(paramCoolBar, paramInt);
    this.parent = paramCoolBar;
    paramCoolBar.createItem(this, paramCoolBar.getItemCount());
  }

  public CoolItem(CoolBar paramCoolBar, int paramInt1, int paramInt2)
  {
    super(paramCoolBar, paramInt1);
    this.parent = paramCoolBar;
    paramCoolBar.createItem(this, paramInt2);
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

  public Point computeSize(int paramInt1, int paramInt2)
  {
    checkWidget();
    int i = this.parent.indexOf(this);
    if (i == -1)
      return new Point(0, 0);
    int j = paramInt1;
    int k = paramInt2;
    if (paramInt1 == -1)
      j = 32;
    if (paramInt2 == -1)
      k = 32;
    if ((this.parent.style & 0x200) != 0)
      k += this.parent.getMargin(i);
    else
      j += this.parent.getMargin(i);
    return new Point(j, k);
  }

  void destroyWidget()
  {
    this.parent.destroyItem(this);
    releaseHandle();
  }

  public Rectangle getBounds()
  {
    checkWidget();
    int i = this.parent.indexOf(this);
    if (i == -1)
      return new Rectangle(0, 0, 0, 0);
    int j = this.parent.handle;
    RECT localRECT = new RECT();
    OS.SendMessage(j, 1033, i, localRECT);
    if (OS.COMCTL32_MAJOR >= 6)
    {
      MARGINS localMARGINS = new MARGINS();
      OS.SendMessage(j, 1064, 0, localMARGINS);
      localRECT.left -= localMARGINS.cxLeftWidth;
      localRECT.right += localMARGINS.cxRightWidth;
    }
    if (!this.parent.isLastItemOfRow(i))
      localRECT.right += ((this.parent.style & 0x800000) == 0 ? 2 : 0);
    int k = localRECT.right - localRECT.left;
    int m = localRECT.bottom - localRECT.top;
    if ((this.parent.style & 0x200) != 0)
      return new Rectangle(localRECT.top, localRECT.left, m, k);
    return new Rectangle(localRECT.left, localRECT.top, k, m);
  }

  Rectangle getClientArea()
  {
    checkWidget();
    int i = this.parent.indexOf(this);
    if (i == -1)
      return new Rectangle(0, 0, 0, 0);
    int j = this.parent.handle;
    RECT localRECT1 = new RECT();
    OS.SendMessage(j, 1058, i, localRECT1);
    RECT localRECT2 = new RECT();
    OS.SendMessage(j, 1033, i, localRECT2);
    int k = localRECT2.left + localRECT1.left;
    int m = localRECT2.top;
    int n = localRECT2.right - localRECT2.left - localRECT1.left;
    int i1 = localRECT2.bottom - localRECT2.top;
    if ((this.parent.style & 0x800000) == 0)
    {
      m += localRECT1.top;
      n -= localRECT1.right;
      i1 -= localRECT1.top + localRECT1.bottom;
    }
    if (i == 0)
    {
      REBARBANDINFO localREBARBANDINFO = new REBARBANDINFO();
      localREBARBANDINFO.cbSize = REBARBANDINFO.sizeof;
      localREBARBANDINFO.fMask = 2048;
      OS.SendMessage(j, OS.RB_GETBANDINFO, i, localREBARBANDINFO);
      n = n - localREBARBANDINFO.cxHeader + 1;
    }
    return new Rectangle(k, m, Math.max(0, n), Math.max(0, i1));
  }

  public Control getControl()
  {
    checkWidget();
    return this.control;
  }

  public CoolBar getParent()
  {
    checkWidget();
    return this.parent;
  }

  void releaseHandle()
  {
    super.releaseHandle();
    this.parent = null;
    this.id = -1;
    this.control = null;
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
    int i = this.parent.indexOf(this);
    if (i == -1)
      return;
    if ((this.control != null) && (this.control.isDisposed()))
      this.control = null;
    Control localControl1 = this.control;
    Control localControl2 = paramControl;
    int j = this.parent.handle;
    int k = localControl2 != null ? paramControl.topHandle() : 0;
    REBARBANDINFO localREBARBANDINFO = new REBARBANDINFO();
    localREBARBANDINFO.cbSize = REBARBANDINFO.sizeof;
    localREBARBANDINFO.fMask = 16;
    localREBARBANDINFO.hwndChild = k;
    this.control = localControl2;
    int m = 0;
    if (localControl2 != null)
      m = OS.GetWindow(k, 3);
    int n = (localControl2 != null) && (!localControl2.getVisible()) ? 1 : 0;
    int i1 = (localControl1 != null) && (localControl1.getVisible()) ? 1 : 0;
    OS.SendMessage(j, OS.RB_SETBANDINFO, i, localREBARBANDINFO);
    if (n != 0)
      localControl2.setVisible(false);
    if (i1 != 0)
      localControl1.setVisible(true);
    if ((m != 0) && (m != k))
    {
      int i2 = 19;
      SetWindowPos(k, m, 0, 0, 0, 0, i2);
    }
  }

  public Point getPreferredSize()
  {
    checkWidget();
    int i = this.parent.indexOf(this);
    if (i == -1)
      return new Point(0, 0);
    int j = this.parent.handle;
    REBARBANDINFO localREBARBANDINFO = new REBARBANDINFO();
    localREBARBANDINFO.cbSize = REBARBANDINFO.sizeof;
    localREBARBANDINFO.fMask = 544;
    OS.SendMessage(j, OS.RB_GETBANDINFO, i, localREBARBANDINFO);
    int k = localREBARBANDINFO.cxIdeal + this.parent.getMargin(i);
    if ((this.parent.style & 0x200) != 0)
      return new Point(localREBARBANDINFO.cyMaxChild, k);
    return new Point(k, localREBARBANDINFO.cyMaxChild);
  }

  public void setPreferredSize(int paramInt1, int paramInt2)
  {
    checkWidget();
    int i = this.parent.indexOf(this);
    if (i == -1)
      return;
    paramInt1 = Math.max(0, paramInt1);
    paramInt2 = Math.max(0, paramInt2);
    this.ideal = true;
    int j = this.parent.handle;
    int k;
    int m;
    if ((this.parent.style & 0x200) != 0)
    {
      k = Math.max(0, paramInt2 - this.parent.getMargin(i));
      m = paramInt1;
    }
    else
    {
      k = Math.max(0, paramInt1 - this.parent.getMargin(i));
      m = paramInt2;
    }
    REBARBANDINFO localREBARBANDINFO = new REBARBANDINFO();
    localREBARBANDINFO.cbSize = REBARBANDINFO.sizeof;
    localREBARBANDINFO.fMask = 32;
    OS.SendMessage(j, OS.RB_GETBANDINFO, i, localREBARBANDINFO);
    localREBARBANDINFO.fMask = 544;
    localREBARBANDINFO.cxIdeal = k;
    localREBARBANDINFO.cyMaxChild = m;
    if (!this.minimum)
      localREBARBANDINFO.cyMinChild = m;
    OS.SendMessage(j, OS.RB_SETBANDINFO, i, localREBARBANDINFO);
  }

  public void setPreferredSize(Point paramPoint)
  {
    checkWidget();
    if (paramPoint == null)
      error(4);
    setPreferredSize(paramPoint.x, paramPoint.y);
  }

  public Point getSize()
  {
    checkWidget();
    int i = this.parent.indexOf(this);
    if (i == -1)
      new Point(0, 0);
    int j = this.parent.handle;
    RECT localRECT = new RECT();
    OS.SendMessage(j, 1033, i, localRECT);
    if (OS.COMCTL32_MAJOR >= 6)
    {
      MARGINS localMARGINS = new MARGINS();
      OS.SendMessage(j, 1064, 0, localMARGINS);
      localRECT.left -= localMARGINS.cxLeftWidth;
      localRECT.right += localMARGINS.cxRightWidth;
    }
    if (!this.parent.isLastItemOfRow(i))
      localRECT.right += ((this.parent.style & 0x800000) == 0 ? 2 : 0);
    int k = localRECT.right - localRECT.left;
    int m = localRECT.bottom - localRECT.top;
    if ((this.parent.style & 0x200) != 0)
      return new Point(m, k);
    return new Point(k, m);
  }

  public void setSize(int paramInt1, int paramInt2)
  {
    checkWidget();
    int i = this.parent.indexOf(this);
    if (i == -1)
      return;
    paramInt1 = Math.max(0, paramInt1);
    paramInt2 = Math.max(0, paramInt2);
    int j = this.parent.handle;
    int k;
    int m;
    int n;
    if ((this.parent.style & 0x200) != 0)
    {
      k = paramInt2;
      m = paramInt1;
      n = Math.max(0, paramInt2 - this.parent.getMargin(i));
    }
    else
    {
      k = paramInt1;
      m = paramInt2;
      n = Math.max(0, paramInt1 - this.parent.getMargin(i));
    }
    REBARBANDINFO localREBARBANDINFO = new REBARBANDINFO();
    localREBARBANDINFO.cbSize = REBARBANDINFO.sizeof;
    localREBARBANDINFO.fMask = 544;
    OS.SendMessage(j, OS.RB_GETBANDINFO, i, localREBARBANDINFO);
    if (!this.ideal)
      localREBARBANDINFO.cxIdeal = n;
    if (!this.minimum)
      localREBARBANDINFO.cyMinChild = m;
    localREBARBANDINFO.cyChild = m;
    if (!this.parent.isLastItemOfRow(i))
    {
      if (OS.COMCTL32_MAJOR >= 6)
      {
        MARGINS localMARGINS = new MARGINS();
        OS.SendMessage(j, 1064, 0, localMARGINS);
        k -= localMARGINS.cxLeftWidth + localMARGINS.cxRightWidth;
      }
      int i1 = (this.parent.style & 0x800000) == 0 ? 2 : 0;
      localREBARBANDINFO.cx = (k - i1);
      localREBARBANDINFO.fMask |= 64;
    }
    OS.SendMessage(j, OS.RB_SETBANDINFO, i, localREBARBANDINFO);
  }

  public void setSize(Point paramPoint)
  {
    if (paramPoint == null)
      error(4);
    setSize(paramPoint.x, paramPoint.y);
  }

  public Point getMinimumSize()
  {
    checkWidget();
    int i = this.parent.indexOf(this);
    if (i == -1)
      return new Point(0, 0);
    int j = this.parent.handle;
    REBARBANDINFO localREBARBANDINFO = new REBARBANDINFO();
    localREBARBANDINFO.cbSize = REBARBANDINFO.sizeof;
    localREBARBANDINFO.fMask = 32;
    OS.SendMessage(j, OS.RB_GETBANDINFO, i, localREBARBANDINFO);
    if ((this.parent.style & 0x200) != 0)
      return new Point(localREBARBANDINFO.cyMinChild, localREBARBANDINFO.cxMinChild);
    return new Point(localREBARBANDINFO.cxMinChild, localREBARBANDINFO.cyMinChild);
  }

  public void setMinimumSize(int paramInt1, int paramInt2)
  {
    checkWidget();
    int i = this.parent.indexOf(this);
    if (i == -1)
      return;
    paramInt1 = Math.max(0, paramInt1);
    paramInt2 = Math.max(0, paramInt2);
    this.minimum = true;
    int j = this.parent.handle;
    int k;
    int m;
    if ((this.parent.style & 0x200) != 0)
    {
      k = paramInt2;
      m = paramInt1;
    }
    else
    {
      k = paramInt1;
      m = paramInt2;
    }
    REBARBANDINFO localREBARBANDINFO = new REBARBANDINFO();
    localREBARBANDINFO.cbSize = REBARBANDINFO.sizeof;
    localREBARBANDINFO.fMask = 32;
    OS.SendMessage(j, OS.RB_GETBANDINFO, i, localREBARBANDINFO);
    localREBARBANDINFO.cxMinChild = k;
    localREBARBANDINFO.cyMinChild = m;
    OS.SendMessage(j, OS.RB_SETBANDINFO, i, localREBARBANDINFO);
  }

  public void setMinimumSize(Point paramPoint)
  {
    checkWidget();
    if (paramPoint == null)
      error(4);
    setMinimumSize(paramPoint.x, paramPoint.y);
  }

  boolean getWrap()
  {
    int i = this.parent.indexOf(this);
    int j = this.parent.handle;
    REBARBANDINFO localREBARBANDINFO = new REBARBANDINFO();
    localREBARBANDINFO.cbSize = REBARBANDINFO.sizeof;
    localREBARBANDINFO.fMask = 1;
    OS.SendMessage(j, OS.RB_GETBANDINFO, i, localREBARBANDINFO);
    return (localREBARBANDINFO.fStyle & 0x1) != 0;
  }

  void setWrap(boolean paramBoolean)
  {
    int i = this.parent.indexOf(this);
    int j = this.parent.handle;
    REBARBANDINFO localREBARBANDINFO = new REBARBANDINFO();
    localREBARBANDINFO.cbSize = REBARBANDINFO.sizeof;
    localREBARBANDINFO.fMask = 1;
    OS.SendMessage(j, OS.RB_GETBANDINFO, i, localREBARBANDINFO);
    if (paramBoolean)
      localREBARBANDINFO.fStyle |= 1;
    else
      localREBARBANDINFO.fStyle &= -2;
    OS.SendMessage(j, OS.RB_SETBANDINFO, i, localREBARBANDINFO);
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
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.CoolItem
 * JD-Core Version:    0.6.2
 */