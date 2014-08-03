package org.eclipse.swt.widgets;

import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.internal.win32.NOTIFYICONDATA;
import org.eclipse.swt.internal.win32.NOTIFYICONDATAA;
import org.eclipse.swt.internal.win32.NOTIFYICONDATAW;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.TCHAR;

public class TrayItem extends Item
{
  Tray parent;
  int id;
  Image image2;
  ToolTip toolTip;
  String toolTipText;
  boolean visible = true;

  public TrayItem(Tray paramTray, int paramInt)
  {
    super(paramTray, paramInt);
    this.parent = paramTray;
    paramTray.createItem(this, paramTray.getItemCount());
    createWidget();
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

  public void addMenuDetectListener(MenuDetectListener paramMenuDetectListener)
  {
    checkWidget();
    if (paramMenuDetectListener == null)
      error(4);
    TypedListener localTypedListener = new TypedListener(paramMenuDetectListener);
    addListener(35, localTypedListener);
  }

  protected void checkSubclass()
  {
    if (!isValidSubclass())
      error(43);
  }

  void createWidget()
  {
    NOTIFYICONDATAA localNOTIFYICONDATAA = OS.IsUnicode ? new NOTIFYICONDATAW() : new NOTIFYICONDATAA();
    localNOTIFYICONDATAA.cbSize = NOTIFYICONDATA.sizeof;
    localNOTIFYICONDATAA.uID = (this.id = this.display.nextTrayId++);
    localNOTIFYICONDATAA.hWnd = this.display.hwndMessage;
    localNOTIFYICONDATAA.uFlags = 1;
    localNOTIFYICONDATAA.uCallbackMessage = 32772;
    OS.Shell_NotifyIcon(0, localNOTIFYICONDATAA);
  }

  void destroyWidget()
  {
    this.parent.destroyItem(this);
    releaseHandle();
  }

  public Tray getParent()
  {
    checkWidget();
    return this.parent;
  }

  public ToolTip getToolTip()
  {
    checkWidget();
    return this.toolTip;
  }

  public String getToolTipText()
  {
    checkWidget();
    return this.toolTipText;
  }

  public boolean getVisible()
  {
    checkWidget();
    return this.visible;
  }

  int messageProc(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    switch (paramInt4)
    {
    case 513:
      if (hooks(13))
      {
        OS.SetForegroundWindow(paramInt1);
        sendSelectionEvent(13);
      }
      break;
    case 515:
    case 518:
      if (hooks(14))
      {
        OS.SetForegroundWindow(paramInt1);
        sendSelectionEvent(14);
      }
      break;
    case 517:
      if (hooks(35))
      {
        OS.SetForegroundWindow(paramInt1);
        sendEvent(35);
        if (isDisposed())
          return 0;
      }
      break;
    case 1026:
      if ((this.toolTip != null) && (!this.toolTip.visible))
      {
        this.toolTip.visible = true;
        if (this.toolTip.hooks(22))
        {
          OS.SetForegroundWindow(paramInt1);
          this.toolTip.sendEvent(22);
          if (isDisposed())
            return 0;
        }
      }
      break;
    case 1027:
    case 1028:
    case 1029:
      if (this.toolTip != null)
      {
        if (this.toolTip.visible)
        {
          this.toolTip.visible = false;
          if (this.toolTip.hooks(23))
          {
            OS.SetForegroundWindow(paramInt1);
            this.toolTip.sendEvent(23);
            if (isDisposed())
              return 0;
          }
        }
        if ((paramInt4 == 1029) && (this.toolTip.hooks(13)))
        {
          OS.SetForegroundWindow(paramInt1);
          this.toolTip.sendSelectionEvent(13);
          if (isDisposed())
            return 0;
        }
      }
      break;
    }
    this.display.wakeThread();
    return 0;
  }

  void recreate()
  {
    createWidget();
    if (!this.visible)
      setVisible(false);
    if (this.text.length() != 0)
      setText(this.text);
    if (this.image != null)
      setImage(this.image);
    if (this.toolTipText != null)
      setToolTipText(this.toolTipText);
  }

  void releaseHandle()
  {
    super.releaseHandle();
    this.parent = null;
  }

  void releaseWidget()
  {
    super.releaseWidget();
    if (this.toolTip != null)
      this.toolTip.item = null;
    this.toolTip = null;
    if (this.image2 != null)
      this.image2.dispose();
    this.image2 = null;
    this.toolTipText = null;
    NOTIFYICONDATAA localNOTIFYICONDATAA = OS.IsUnicode ? new NOTIFYICONDATAW() : new NOTIFYICONDATAA();
    localNOTIFYICONDATAA.cbSize = NOTIFYICONDATA.sizeof;
    localNOTIFYICONDATAA.uID = this.id;
    localNOTIFYICONDATAA.hWnd = this.display.hwndMessage;
    OS.Shell_NotifyIcon(2, localNOTIFYICONDATAA);
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

  public void removeMenuDetectListener(MenuDetectListener paramMenuDetectListener)
  {
    checkWidget();
    if (paramMenuDetectListener == null)
      error(4);
    if (this.eventTable == null)
      return;
    this.eventTable.unhook(35, paramMenuDetectListener);
  }

  public void setImage(Image paramImage)
  {
    checkWidget();
    if ((paramImage != null) && (paramImage.isDisposed()))
      error(5);
    super.setImage(paramImage);
    if (this.image2 != null)
      this.image2.dispose();
    this.image2 = null;
    int i = 0;
    Image localImage = paramImage;
    if (localImage != null)
      switch (localImage.type)
      {
      case 0:
        this.image2 = Display.createIcon(paramImage);
        i = this.image2.handle;
        break;
      case 1:
        i = localImage.handle;
      }
    NOTIFYICONDATAA localNOTIFYICONDATAA = OS.IsUnicode ? new NOTIFYICONDATAW() : new NOTIFYICONDATAA();
    localNOTIFYICONDATAA.cbSize = NOTIFYICONDATA.sizeof;
    localNOTIFYICONDATAA.uID = this.id;
    localNOTIFYICONDATAA.hWnd = this.display.hwndMessage;
    localNOTIFYICONDATAA.hIcon = i;
    localNOTIFYICONDATAA.uFlags = 2;
    OS.Shell_NotifyIcon(1, localNOTIFYICONDATAA);
  }

  public void setToolTip(ToolTip paramToolTip)
  {
    checkWidget();
    ToolTip localToolTip1 = this.toolTip;
    ToolTip localToolTip2 = paramToolTip;
    if (localToolTip1 != null)
      localToolTip1.item = null;
    this.toolTip = localToolTip2;
    if (localToolTip2 != null)
      localToolTip2.item = this;
  }

  public void setToolTipText(String paramString)
  {
    checkWidget();
    this.toolTipText = paramString;
    NOTIFYICONDATAA localNOTIFYICONDATAA = OS.IsUnicode ? new NOTIFYICONDATAW() : new NOTIFYICONDATAA();
    TCHAR localTCHAR = new TCHAR(0, this.toolTipText == null ? "" : this.toolTipText, true);
    int i = OS.SHELL32_MAJOR < 5 ? 64 : 128;
    Object localObject;
    if (OS.IsUnicode)
    {
      localObject = ((NOTIFYICONDATAW)localNOTIFYICONDATAA).szTip;
      i = Math.min(i - 1, localTCHAR.length());
      System.arraycopy(localTCHAR.chars, 0, localObject, 0, i);
    }
    else
    {
      localObject = ((NOTIFYICONDATAA)localNOTIFYICONDATAA).szTip;
      i = Math.min(i - 1, localTCHAR.length());
      System.arraycopy(localTCHAR.bytes, 0, localObject, 0, i);
    }
    localNOTIFYICONDATAA.cbSize = NOTIFYICONDATA.sizeof;
    localNOTIFYICONDATAA.uID = this.id;
    localNOTIFYICONDATAA.hWnd = this.display.hwndMessage;
    localNOTIFYICONDATAA.uFlags = 4;
    OS.Shell_NotifyIcon(1, localNOTIFYICONDATAA);
  }

  public void setVisible(boolean paramBoolean)
  {
    checkWidget();
    if (this.visible == paramBoolean)
      return;
    if (paramBoolean)
    {
      sendEvent(22);
      if (isDisposed())
        return;
    }
    this.visible = paramBoolean;
    NOTIFYICONDATAA localNOTIFYICONDATAA = OS.IsUnicode ? new NOTIFYICONDATAW() : new NOTIFYICONDATAA();
    localNOTIFYICONDATAA.cbSize = NOTIFYICONDATA.sizeof;
    localNOTIFYICONDATAA.uID = this.id;
    localNOTIFYICONDATAA.hWnd = this.display.hwndMessage;
    if (OS.SHELL32_MAJOR < 5)
    {
      if (paramBoolean)
      {
        localNOTIFYICONDATAA.uFlags = 1;
        localNOTIFYICONDATAA.uCallbackMessage = 32772;
        OS.Shell_NotifyIcon(0, localNOTIFYICONDATAA);
        setImage(this.image);
        setToolTipText(this.toolTipText);
      }
      else
      {
        OS.Shell_NotifyIcon(2, localNOTIFYICONDATAA);
      }
    }
    else
    {
      localNOTIFYICONDATAA.uFlags = 8;
      localNOTIFYICONDATAA.dwState = (paramBoolean ? 0 : 1);
      localNOTIFYICONDATAA.dwStateMask = 1;
      OS.Shell_NotifyIcon(1, localNOTIFYICONDATAA);
    }
    if (!paramBoolean)
      sendEvent(23);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.TrayItem
 * JD-Core Version:    0.6.2
 */