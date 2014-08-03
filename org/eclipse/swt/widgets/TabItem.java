package org.eclipse.swt.widgets;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.TCITEM;

public class TabItem extends Item
{
  TabFolder parent;
  Control control;
  String toolTipText;

  public TabItem(TabFolder paramTabFolder, int paramInt)
  {
    super(paramTabFolder, paramInt);
    this.parent = paramTabFolder;
    paramTabFolder.createItem(this, paramTabFolder.getItemCount());
  }

  public TabItem(TabFolder paramTabFolder, int paramInt1, int paramInt2)
  {
    super(paramTabFolder, paramInt1);
    this.parent = paramTabFolder;
    paramTabFolder.createItem(this, paramInt2);
  }

  void _setText(int paramInt, String paramString)
  {
    if ((OS.COMCTL32_MAJOR >= 6) && (this.image != null) && (paramString.indexOf('&') != -1))
    {
      i = paramString.length();
      char[] arrayOfChar = new char[i];
      paramString.getChars(0, i, arrayOfChar, 0);
      int k = 0;
      m = 0;
      for (k = 0; k < i; k++)
        if (arrayOfChar[k] != '&')
          arrayOfChar[(m++)] = arrayOfChar[k];
      if (m < k)
        paramString = new String(arrayOfChar, 0, m);
    }
    int i = this.parent.handle;
    int j = OS.GetProcessHeap();
    TCHAR localTCHAR = new TCHAR(this.parent.getCodePage(), paramString, true);
    int m = localTCHAR.length() * TCHAR.sizeof;
    int n = OS.HeapAlloc(j, 8, m);
    OS.MoveMemory(n, localTCHAR, m);
    TCITEM localTCITEM = new TCITEM();
    localTCITEM.mask = 1;
    localTCITEM.pszText = n;
    OS.SendMessage(i, OS.TCM_SETITEM, paramInt, localTCITEM);
    OS.HeapFree(j, 0, n);
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

  public Control getControl()
  {
    checkWidget();
    return this.control;
  }

  public Rectangle getBounds()
  {
    checkWidget();
    int i = this.parent.indexOf(this);
    if (i == -1)
      return new Rectangle(0, 0, 0, 0);
    RECT localRECT = new RECT();
    OS.SendMessage(this.parent.handle, 4874, i, localRECT);
    return new Rectangle(localRECT.left, localRECT.top, localRECT.right - localRECT.left, localRECT.bottom - localRECT.top);
  }

  public TabFolder getParent()
  {
    checkWidget();
    return this.parent;
  }

  public String getToolTipText()
  {
    checkWidget();
    return this.toolTipText;
  }

  void releaseHandle()
  {
    super.releaseHandle();
    this.parent = null;
  }

  void releaseParent()
  {
    super.releaseParent();
    int i = this.parent.indexOf(this);
    if ((i == this.parent.getSelectionIndex()) && (this.control != null))
      this.control.setVisible(false);
  }

  void releaseWidget()
  {
    super.releaseWidget();
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
    if ((this.control != null) && (this.control.isDisposed()))
      this.control = null;
    Control localControl1 = this.control;
    Control localControl2 = paramControl;
    this.control = paramControl;
    int i = this.parent.indexOf(this);
    int j = this.parent.getSelectionIndex();
    if ((i != j) && (localControl2 != null))
    {
      if (j != -1)
      {
        Control localControl3 = this.parent.getItem(j).getControl();
        if (localControl3 == localControl2)
          return;
      }
      localControl2.setVisible(false);
      return;
    }
    if (localControl2 != null)
    {
      localControl2.setBounds(this.parent.getClientArea());
      localControl2.setVisible(true);
    }
    if (localControl1 != null)
      localControl1.setVisible(false);
  }

  public void setImage(Image paramImage)
  {
    checkWidget();
    int i = this.parent.indexOf(this);
    if (i == -1)
      return;
    super.setImage(paramImage);
    if ((OS.COMCTL32_MAJOR >= 6) && (this.text.indexOf('&') != -1))
      _setText(i, this.text);
    int j = this.parent.handle;
    TCITEM localTCITEM = new TCITEM();
    localTCITEM.mask = 2;
    localTCITEM.iImage = this.parent.imageIndex(paramImage);
    OS.SendMessage(j, OS.TCM_SETITEM, i, localTCITEM);
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
    _setText(i, paramString);
  }

  public void setToolTipText(String paramString)
  {
    checkWidget();
    this.toolTipText = paramString;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.TabItem
 * JD-Core Version:    0.6.2
 */