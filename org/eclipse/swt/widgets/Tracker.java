package org.eclipse.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.Callback;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.MSG;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.PAINTSTRUCT;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.internal.win32.RECT;

public class Tracker extends Widget
{
  Control parent;
  boolean tracking;
  boolean cancelled;
  boolean stippled;
  Rectangle[] rectangles = new Rectangle[0];
  Rectangle[] proportions = this.rectangles;
  Rectangle bounds;
  int resizeCursor;
  Cursor clientCursor;
  int cursorOrientation = 0;
  boolean inEvent = false;
  int hwndTransparent;
  int hwndOpaque;
  int oldTransparentProc;
  int oldOpaqueProc;
  int oldX;
  int oldY;
  static boolean IsVista = (!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(6, 0));
  static final int STEPSIZE_SMALL = 1;
  static final int STEPSIZE_LARGE = 9;

  public Tracker(Composite paramComposite, int paramInt)
  {
    super(paramComposite, checkStyle(paramInt));
    this.parent = paramComposite;
  }

  public Tracker(Display paramDisplay, int paramInt)
  {
    if (paramDisplay == null)
      paramDisplay = Display.getCurrent();
    if (paramDisplay == null)
      paramDisplay = Display.getDefault();
    if (!paramDisplay.isValidThread())
      error(22);
    this.style = checkStyle(paramInt);
    this.display = paramDisplay;
    reskinWidget();
  }

  public void addControlListener(ControlListener paramControlListener)
  {
    checkWidget();
    if (paramControlListener == null)
      error(4);
    TypedListener localTypedListener = new TypedListener(paramControlListener);
    addListener(11, localTypedListener);
    addListener(10, localTypedListener);
  }

  public void addKeyListener(KeyListener paramKeyListener)
  {
    checkWidget();
    if (paramKeyListener == null)
      error(4);
    TypedListener localTypedListener = new TypedListener(paramKeyListener);
    addListener(2, localTypedListener);
    addListener(1, localTypedListener);
  }

  Point adjustMoveCursor()
  {
    if (this.bounds == null)
      return null;
    int i = this.bounds.x + this.bounds.width / 2;
    int j = this.bounds.y;
    POINT localPOINT = new POINT();
    localPOINT.x = i;
    localPOINT.y = j;
    if (this.parent != null)
      OS.ClientToScreen(this.parent.handle, localPOINT);
    OS.SetCursorPos(localPOINT.x, localPOINT.y);
    return new Point(localPOINT.x, localPOINT.y);
  }

  Point adjustResizeCursor()
  {
    if (this.bounds == null)
      return null;
    int i;
    if ((this.cursorOrientation & 0x4000) != 0)
      i = this.bounds.x;
    else if ((this.cursorOrientation & 0x20000) != 0)
      i = this.bounds.x + this.bounds.width;
    else
      i = this.bounds.x + this.bounds.width / 2;
    int j;
    if ((this.cursorOrientation & 0x80) != 0)
      j = this.bounds.y;
    else if ((this.cursorOrientation & 0x400) != 0)
      j = this.bounds.y + this.bounds.height;
    else
      j = this.bounds.y + this.bounds.height / 2;
    POINT localPOINT = new POINT();
    localPOINT.x = i;
    localPOINT.y = j;
    if (this.parent != null)
      OS.ClientToScreen(this.parent.handle, localPOINT);
    OS.SetCursorPos(localPOINT.x, localPOINT.y);
    if (this.clientCursor == null)
    {
      int k = 0;
      switch (this.cursorOrientation)
      {
      case 128:
        k = OS.LoadCursor(0, 32645);
        break;
      case 1024:
        k = OS.LoadCursor(0, 32645);
        break;
      case 16384:
        k = OS.LoadCursor(0, 32644);
        break;
      case 131072:
        k = OS.LoadCursor(0, 32644);
        break;
      case 16512:
        k = OS.LoadCursor(0, 32642);
        break;
      case 132096:
        k = OS.LoadCursor(0, 32642);
        break;
      case 17408:
        k = OS.LoadCursor(0, 32643);
        break;
      case 131200:
        k = OS.LoadCursor(0, 32643);
        break;
      default:
        k = OS.LoadCursor(0, 32646);
      }
      OS.SetCursor(k);
      if (this.resizeCursor != 0)
        OS.DestroyCursor(this.resizeCursor);
      this.resizeCursor = k;
    }
    return new Point(localPOINT.x, localPOINT.y);
  }

  static int checkStyle(int paramInt)
  {
    if ((paramInt & 0x24480) == 0)
      paramInt |= 148608;
    return paramInt;
  }

  public void close()
  {
    checkWidget();
    this.tracking = false;
  }

  Rectangle computeBounds()
  {
    if (this.rectangles.length == 0)
      return null;
    int i = this.rectangles[0].x;
    int j = this.rectangles[0].y;
    int k = this.rectangles[0].x + this.rectangles[0].width;
    int m = this.rectangles[0].y + this.rectangles[0].height;
    for (int n = 1; n < this.rectangles.length; n++)
    {
      if (this.rectangles[n].x < i)
        i = this.rectangles[n].x;
      if (this.rectangles[n].y < j)
        j = this.rectangles[n].y;
      int i1 = this.rectangles[n].x + this.rectangles[n].width;
      if (i1 > k)
        k = i1;
      int i2 = this.rectangles[n].y + this.rectangles[n].height;
      if (i2 > m)
        m = i2;
    }
    return new Rectangle(i, j, k - i, m - j);
  }

  Rectangle[] computeProportions(Rectangle[] paramArrayOfRectangle)
  {
    Rectangle[] arrayOfRectangle = new Rectangle[paramArrayOfRectangle.length];
    this.bounds = computeBounds();
    if (this.bounds != null)
      for (int i = 0; i < paramArrayOfRectangle.length; i++)
      {
        int j = 0;
        int k = 0;
        int m = 0;
        int n = 0;
        if (this.bounds.width != 0)
        {
          j = (paramArrayOfRectangle[i].x - this.bounds.x) * 100 / this.bounds.width;
          m = paramArrayOfRectangle[i].width * 100 / this.bounds.width;
        }
        else
        {
          m = 100;
        }
        if (this.bounds.height != 0)
        {
          k = (paramArrayOfRectangle[i].y - this.bounds.y) * 100 / this.bounds.height;
          n = paramArrayOfRectangle[i].height * 100 / this.bounds.height;
        }
        else
        {
          n = 100;
        }
        arrayOfRectangle[i] = new Rectangle(j, k, m, n);
      }
    return arrayOfRectangle;
  }

  void drawRectangles(Rectangle[] paramArrayOfRectangle, boolean paramBoolean)
  {
    if (this.hwndOpaque != 0)
    {
      RECT localRECT = new RECT();
      j = paramBoolean ? 3 : 1;
      for (k = 0; k < paramArrayOfRectangle.length; k++)
      {
        Rectangle localRectangle1 = paramArrayOfRectangle[k];
        localRECT.left = (localRectangle1.x - j);
        localRECT.top = (localRectangle1.y - j);
        localRECT.right = (localRectangle1.x + localRectangle1.width + j * 2);
        localRECT.bottom = (localRectangle1.y + localRectangle1.height + j * 2);
        OS.MapWindowPoints(0, this.hwndOpaque, localRECT, 2);
        OS.RedrawWindow(this.hwndOpaque, localRECT, 0, 1);
      }
      return;
    }
    int i = 1;
    int j = this.parent == null ? OS.GetDesktopWindow() : this.parent.handle;
    int k = OS.GetDCEx(j, 0, 2);
    int m = 0;
    int n = 0;
    int i1 = 0;
    if (paramBoolean)
    {
      i = 3;
      byte[] arrayOfByte = { -86, 0, 85, 0, -86, 0, 85, 0, -86, 0, 85, 0, -86, 0, 85 };
      m = OS.CreateBitmap(8, 8, 1, 1, arrayOfByte);
      n = OS.CreatePatternBrush(m);
      i1 = OS.SelectObject(k, n);
    }
    for (int i2 = 0; i2 < paramArrayOfRectangle.length; i2++)
    {
      Rectangle localRectangle2 = paramArrayOfRectangle[i2];
      OS.PatBlt(k, localRectangle2.x, localRectangle2.y, localRectangle2.width, i, 5898313);
      OS.PatBlt(k, localRectangle2.x, localRectangle2.y + i, i, localRectangle2.height - i * 2, 5898313);
      OS.PatBlt(k, localRectangle2.x + localRectangle2.width - i, localRectangle2.y + i, i, localRectangle2.height - i * 2, 5898313);
      OS.PatBlt(k, localRectangle2.x, localRectangle2.y + localRectangle2.height - i, localRectangle2.width, i, 5898313);
    }
    if (paramBoolean)
    {
      OS.SelectObject(k, i1);
      OS.DeleteObject(n);
      OS.DeleteObject(m);
    }
    OS.ReleaseDC(j, k);
  }

  public Rectangle[] getRectangles()
  {
    checkWidget();
    Rectangle[] arrayOfRectangle = new Rectangle[this.rectangles.length];
    for (int i = 0; i < this.rectangles.length; i++)
    {
      Rectangle localRectangle = this.rectangles[i];
      arrayOfRectangle[i] = new Rectangle(localRectangle.x, localRectangle.y, localRectangle.width, localRectangle.height);
    }
    return arrayOfRectangle;
  }

  public boolean getStippled()
  {
    checkWidget();
    return this.stippled;
  }

  void moveRectangles(int paramInt1, int paramInt2)
  {
    if (this.bounds == null)
      return;
    if ((paramInt1 < 0) && ((this.style & 0x4000) == 0))
      paramInt1 = 0;
    if ((paramInt1 > 0) && ((this.style & 0x20000) == 0))
      paramInt1 = 0;
    if ((paramInt2 < 0) && ((this.style & 0x80) == 0))
      paramInt2 = 0;
    if ((paramInt2 > 0) && ((this.style & 0x400) == 0))
      paramInt2 = 0;
    if ((paramInt1 == 0) && (paramInt2 == 0))
      return;
    this.bounds.x += paramInt1;
    this.bounds.y += paramInt2;
    for (int i = 0; i < this.rectangles.length; i++)
    {
      this.rectangles[i].x += paramInt1;
      this.rectangles[i].y += paramInt2;
    }
  }

  public boolean open()
  {
    checkWidget();
    this.cancelled = false;
    this.tracking = true;
    int i = this.style & 0x480;
    if ((i == 128) || (i == 1024))
      this.cursorOrientation |= i;
    int j = this.style & 0x24000;
    if ((j == 16384) || (j == 131072))
      this.cursorOrientation |= j;
    Callback localCallback = null;
    int k = OS.GetKeyState(1) < 0 ? 1 : 0;
    int m;
    if ((IsVista) && (this.parent == null))
    {
      localObject1 = this.display.getBounds();
      this.hwndTransparent = OS.CreateWindowEx(134742016, this.display.windowClass, null, -2147483648, ((Rectangle)localObject1).x, ((Rectangle)localObject1).y, ((Rectangle)localObject1).width, ((Rectangle)localObject1).height, 0, 0, OS.GetModuleHandle(null), null);
      OS.SetLayeredWindowAttributes(this.hwndTransparent, 0, (byte)1, 2);
      this.hwndOpaque = OS.CreateWindowEx(134742016, this.display.windowClass, null, -2147483648, ((Rectangle)localObject1).x, ((Rectangle)localObject1).y, ((Rectangle)localObject1).width, ((Rectangle)localObject1).height, this.hwndTransparent, 0, OS.GetModuleHandle(null), null);
      OS.SetLayeredWindowAttributes(this.hwndOpaque, 16777215, (byte)-1, 3);
      localCallback = new Callback(this, "transparentProc", 4);
      m = localCallback.getAddress();
      if (m == 0)
        SWT.error(3);
      this.oldTransparentProc = OS.GetWindowLongPtr(this.hwndTransparent, -4);
      OS.SetWindowLongPtr(this.hwndTransparent, -4, m);
      this.oldOpaqueProc = OS.GetWindowLongPtr(this.hwndOpaque, -4);
      OS.SetWindowLongPtr(this.hwndOpaque, -4, m);
      OS.ShowWindow(this.hwndTransparent, 4);
      OS.ShowWindow(this.hwndOpaque, 4);
    }
    else if (k == 0)
    {
      localObject1 = this.display.getBounds();
      this.hwndTransparent = OS.CreateWindowEx(32, this.display.windowClass, null, -2147483648, ((Rectangle)localObject1).x, ((Rectangle)localObject1).y, ((Rectangle)localObject1).width, ((Rectangle)localObject1).height, 0, 0, OS.GetModuleHandle(null), null);
      localCallback = new Callback(this, "transparentProc", 4);
      m = localCallback.getAddress();
      if (m == 0)
        SWT.error(3);
      this.oldTransparentProc = OS.GetWindowLongPtr(this.hwndTransparent, -4);
      OS.SetWindowLongPtr(this.hwndTransparent, -4, m);
      OS.ShowWindow(this.hwndTransparent, 4);
    }
    update();
    drawRectangles(this.rectangles, this.stippled);
    Object localObject1 = null;
    Object localObject2;
    if (k != 0)
    {
      localObject2 = new POINT();
      OS.GetCursorPos((POINT)localObject2);
      localObject1 = new Point(((POINT)localObject2).x, ((POINT)localObject2).y);
    }
    else if ((this.style & 0x10) != 0)
    {
      localObject1 = adjustResizeCursor();
    }
    else
    {
      localObject1 = adjustMoveCursor();
    }
    if (localObject1 != null)
    {
      this.oldX = ((Point)localObject1).x;
      this.oldY = ((Point)localObject1).y;
    }
    try
    {
      localObject2 = new MSG();
      while ((this.tracking) && (!this.cancelled))
      {
        if ((this.parent != null) && (this.parent.isDisposed()))
          break;
        OS.GetMessage((MSG)localObject2, 0, 0, 0);
        OS.TranslateMessage((MSG)localObject2);
        switch (((MSG)localObject2).message)
        {
        case 512:
        case 514:
          wmMouse(((MSG)localObject2).message, ((MSG)localObject2).wParam, ((MSG)localObject2).lParam);
          break;
        case 646:
          wmIMEChar(((MSG)localObject2).hwnd, ((MSG)localObject2).wParam, ((MSG)localObject2).lParam);
          break;
        case 258:
          wmChar(((MSG)localObject2).hwnd, ((MSG)localObject2).wParam, ((MSG)localObject2).lParam);
          break;
        case 256:
          wmKeyDown(((MSG)localObject2).hwnd, ((MSG)localObject2).wParam, ((MSG)localObject2).lParam);
          break;
        case 257:
          wmKeyUp(((MSG)localObject2).hwnd, ((MSG)localObject2).wParam, ((MSG)localObject2).lParam);
          break;
        case 262:
          wmSysChar(((MSG)localObject2).hwnd, ((MSG)localObject2).wParam, ((MSG)localObject2).lParam);
          break;
        case 260:
          wmSysKeyDown(((MSG)localObject2).hwnd, ((MSG)localObject2).wParam, ((MSG)localObject2).lParam);
          break;
        case 261:
          wmSysKeyUp(((MSG)localObject2).hwnd, ((MSG)localObject2).wParam, ((MSG)localObject2).lParam);
        }
        if (((256 > ((MSG)localObject2).message) || (((MSG)localObject2).message > 264)) && ((512 > ((MSG)localObject2).message) || (((MSG)localObject2).message > 525)))
        {
          if ((this.hwndOpaque == 0) && (((MSG)localObject2).message == 15))
          {
            update();
            drawRectangles(this.rectangles, this.stippled);
          }
          OS.DispatchMessage((MSG)localObject2);
          if ((this.hwndOpaque == 0) && (((MSG)localObject2).message == 15))
            drawRectangles(this.rectangles, this.stippled);
        }
      }
      if (k != 0)
        OS.ReleaseCapture();
      if (!isDisposed())
      {
        update();
        drawRectangles(this.rectangles, this.stippled);
      }
    }
    finally
    {
      if (this.hwndTransparent != 0)
      {
        OS.DestroyWindow(this.hwndTransparent);
        this.hwndTransparent = 0;
      }
      this.hwndOpaque = 0;
      if (localCallback != null)
      {
        localCallback.dispose();
        this.oldTransparentProc = (this.oldOpaqueProc = 0);
      }
      if (this.resizeCursor != 0)
      {
        OS.DestroyCursor(this.resizeCursor);
        this.resizeCursor = 0;
      }
      ret;
    }
    return !this.cancelled;
  }

  void releaseWidget()
  {
    super.releaseWidget();
    this.parent = null;
    this.rectangles = (this.proportions = null);
    this.bounds = null;
  }

  public void removeControlListener(ControlListener paramControlListener)
  {
    checkWidget();
    if (paramControlListener == null)
      error(4);
    if (this.eventTable == null)
      return;
    this.eventTable.unhook(11, paramControlListener);
    this.eventTable.unhook(10, paramControlListener);
  }

  public void removeKeyListener(KeyListener paramKeyListener)
  {
    checkWidget();
    if (paramKeyListener == null)
      error(4);
    if (this.eventTable == null)
      return;
    this.eventTable.unhook(2, paramKeyListener);
    this.eventTable.unhook(1, paramKeyListener);
  }

  void resizeRectangles(int paramInt1, int paramInt2)
  {
    if (this.bounds == null)
      return;
    if ((paramInt1 < 0) && ((this.style & 0x4000) != 0) && ((this.cursorOrientation & 0x20000) == 0))
      this.cursorOrientation |= 16384;
    if ((paramInt1 > 0) && ((this.style & 0x20000) != 0) && ((this.cursorOrientation & 0x4000) == 0))
      this.cursorOrientation |= 131072;
    if ((paramInt2 < 0) && ((this.style & 0x80) != 0) && ((this.cursorOrientation & 0x400) == 0))
      this.cursorOrientation |= 128;
    if ((paramInt2 > 0) && ((this.style & 0x400) != 0) && ((this.cursorOrientation & 0x80) == 0))
      this.cursorOrientation |= 1024;
    int i;
    Rectangle localRectangle1;
    if ((this.cursorOrientation & 0x4000) != 0)
    {
      if (paramInt1 > this.bounds.width)
      {
        if ((this.style & 0x20000) == 0)
          return;
        this.cursorOrientation |= 131072;
        this.cursorOrientation &= -16385;
        this.bounds.x += this.bounds.width;
        paramInt1 -= this.bounds.width;
        this.bounds.width = 0;
        if (this.proportions.length > 1)
          for (i = 0; i < this.proportions.length; i++)
          {
            localRectangle1 = this.proportions[i];
            localRectangle1.x = (100 - localRectangle1.x - localRectangle1.width);
          }
      }
    }
    else if (((this.cursorOrientation & 0x20000) != 0) && (this.bounds.width < -paramInt1))
    {
      if ((this.style & 0x4000) == 0)
        return;
      this.cursorOrientation |= 16384;
      this.cursorOrientation &= -131073;
      paramInt1 += this.bounds.width;
      this.bounds.width = 0;
      if (this.proportions.length > 1)
        for (i = 0; i < this.proportions.length; i++)
        {
          localRectangle1 = this.proportions[i];
          localRectangle1.x = (100 - localRectangle1.x - localRectangle1.width);
        }
    }
    if ((this.cursorOrientation & 0x80) != 0)
    {
      if (paramInt2 > this.bounds.height)
      {
        if ((this.style & 0x400) == 0)
          return;
        this.cursorOrientation |= 1024;
        this.cursorOrientation &= -129;
        this.bounds.y += this.bounds.height;
        paramInt2 -= this.bounds.height;
        this.bounds.height = 0;
        if (this.proportions.length > 1)
          for (i = 0; i < this.proportions.length; i++)
          {
            localRectangle1 = this.proportions[i];
            localRectangle1.y = (100 - localRectangle1.y - localRectangle1.height);
          }
      }
    }
    else if (((this.cursorOrientation & 0x400) != 0) && (this.bounds.height < -paramInt2))
    {
      if ((this.style & 0x80) == 0)
        return;
      this.cursorOrientation |= 128;
      this.cursorOrientation &= -1025;
      paramInt2 += this.bounds.height;
      this.bounds.height = 0;
      if (this.proportions.length > 1)
        for (i = 0; i < this.proportions.length; i++)
        {
          localRectangle1 = this.proportions[i];
          localRectangle1.y = (100 - localRectangle1.y - localRectangle1.height);
        }
    }
    if ((this.cursorOrientation & 0x4000) != 0)
    {
      this.bounds.x += paramInt1;
      this.bounds.width -= paramInt1;
    }
    else if ((this.cursorOrientation & 0x20000) != 0)
    {
      this.bounds.width += paramInt1;
    }
    if ((this.cursorOrientation & 0x80) != 0)
    {
      this.bounds.y += paramInt2;
      this.bounds.height -= paramInt2;
    }
    else if ((this.cursorOrientation & 0x400) != 0)
    {
      this.bounds.height += paramInt2;
    }
    Rectangle[] arrayOfRectangle = new Rectangle[this.rectangles.length];
    for (int j = 0; j < this.rectangles.length; j++)
    {
      Rectangle localRectangle2 = this.proportions[j];
      arrayOfRectangle[j] = new Rectangle(localRectangle2.x * this.bounds.width / 100 + this.bounds.x, localRectangle2.y * this.bounds.height / 100 + this.bounds.y, localRectangle2.width * this.bounds.width / 100, localRectangle2.height * this.bounds.height / 100);
    }
    this.rectangles = arrayOfRectangle;
  }

  public void setCursor(Cursor paramCursor)
  {
    checkWidget();
    this.clientCursor = paramCursor;
    if ((paramCursor != null) && (this.inEvent))
      OS.SetCursor(this.clientCursor.handle);
  }

  public void setRectangles(Rectangle[] paramArrayOfRectangle)
  {
    checkWidget();
    if (paramArrayOfRectangle == null)
      error(4);
    this.rectangles = new Rectangle[paramArrayOfRectangle.length];
    for (int i = 0; i < paramArrayOfRectangle.length; i++)
    {
      Rectangle localRectangle = paramArrayOfRectangle[i];
      if (localRectangle == null)
        error(4);
      this.rectangles[i] = new Rectangle(localRectangle.x, localRectangle.y, localRectangle.width, localRectangle.height);
    }
    this.proportions = computeProportions(paramArrayOfRectangle);
  }

  public void setStippled(boolean paramBoolean)
  {
    checkWidget();
    this.stippled = paramBoolean;
  }

  int transparentProc(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    switch (paramInt2)
    {
    case 132:
      if (this.inEvent)
        return -1;
      break;
    case 32:
      if (this.clientCursor != null)
      {
        OS.SetCursor(this.clientCursor.handle);
        return 1;
      }
      if (this.resizeCursor != 0)
      {
        OS.SetCursor(this.resizeCursor);
        return 1;
      }
      break;
    case 15:
      if (this.hwndOpaque == paramInt1)
      {
        PAINTSTRUCT localPAINTSTRUCT = new PAINTSTRUCT();
        int i = OS.BeginPaint(paramInt1, localPAINTSTRUCT);
        int j = 0;
        int k = 0;
        int m = 0;
        int n = OS.CreateSolidBrush(16777215);
        m = OS.SelectObject(i, n);
        OS.PatBlt(i, localPAINTSTRUCT.left, localPAINTSTRUCT.top, localPAINTSTRUCT.right - localPAINTSTRUCT.left, localPAINTSTRUCT.bottom - localPAINTSTRUCT.top, 15728673);
        OS.SelectObject(i, m);
        OS.DeleteObject(n);
        int i1 = 1;
        if (this.stippled)
        {
          i1 = 3;
          localObject1 = new byte[] { -86, 0, 85, 0, -86, 0, 85, 0, -86, 0, 85, 0, -86, 0, 85 };
          j = OS.CreateBitmap(8, 8, 1, 1, (byte[])localObject1);
          k = OS.CreatePatternBrush(j);
          m = OS.SelectObject(i, k);
          OS.SetBkColor(i, 15790320);
        }
        else
        {
          m = OS.SelectObject(i, OS.GetStockObject(4));
        }
        Object localObject1 = this.rectangles;
        RECT localRECT = new RECT();
        for (int i2 = 0; i2 < localObject1.length; i2++)
        {
          Object localObject2 = localObject1[i2];
          localRECT.left = localObject2.x;
          localRECT.top = localObject2.y;
          localRECT.right = (localObject2.x + localObject2.width);
          localRECT.bottom = (localObject2.y + localObject2.height);
          OS.MapWindowPoints(0, this.hwndOpaque, localRECT, 2);
          int i3 = localRECT.right - localRECT.left;
          int i4 = localRECT.bottom - localRECT.top;
          OS.PatBlt(i, localRECT.left, localRECT.top, i3, i1, 15728673);
          OS.PatBlt(i, localRECT.left, localRECT.top + i1, i1, i4 - i1 * 2, 15728673);
          OS.PatBlt(i, localRECT.right - i1, localRECT.top + i1, i1, i4 - i1 * 2, 15728673);
          OS.PatBlt(i, localRECT.left, localRECT.bottom - i1, i3, i1, 15728673);
        }
        OS.SelectObject(i, m);
        if (this.stippled)
        {
          OS.DeleteObject(k);
          OS.DeleteObject(j);
        }
        OS.EndPaint(paramInt1, localPAINTSTRUCT);
        return 0;
      }
      break;
    }
    return OS.CallWindowProc(paramInt1 == this.hwndTransparent ? this.oldTransparentProc : this.oldOpaqueProc, paramInt1, paramInt2, paramInt3, paramInt4);
  }

  void update()
  {
    if (this.hwndOpaque != 0)
      return;
    if (this.parent != null)
    {
      if (this.parent.isDisposed())
        return;
      Shell localShell = this.parent.getShell();
      localShell.update(true);
    }
    else
    {
      this.display.update();
    }
  }

  LRESULT wmKeyDown(int paramInt1, int paramInt2, int paramInt3)
  {
    LRESULT localLRESULT = super.wmKeyDown(paramInt1, paramInt2, paramInt3);
    if (localLRESULT != null)
      return localLRESULT;
    int i = (this.parent != null) && ((this.parent.style & 0x8000000) != 0) ? 1 : 0;
    int j = OS.GetKeyState(17) < 0 ? 1 : 9;
    int k = 0;
    int m = 0;
    switch (paramInt2)
    {
    case 27:
      this.cancelled = true;
      this.tracking = false;
      break;
    case 13:
      this.tracking = false;
      break;
    case 37:
      k = i != 0 ? j : -j;
      break;
    case 39:
      k = i != 0 ? -j : j;
      break;
    case 38:
      m = -j;
      break;
    case 40:
      m = j;
    }
    if ((k != 0) || (m != 0))
    {
      Rectangle[] arrayOfRectangle1 = this.rectangles;
      boolean bool = this.stippled;
      Rectangle[] arrayOfRectangle2 = new Rectangle[this.rectangles.length];
      Object localObject;
      for (int n = 0; n < this.rectangles.length; n++)
      {
        localObject = this.rectangles[n];
        arrayOfRectangle2[n] = new Rectangle(((Rectangle)localObject).x, ((Rectangle)localObject).y, ((Rectangle)localObject).width, ((Rectangle)localObject).height);
      }
      Event localEvent = new Event();
      localEvent.x = (this.oldX + k);
      localEvent.y = (this.oldY + m);
      int i1;
      int i2;
      int i3;
      if ((this.style & 0x10) != 0)
      {
        resizeRectangles(k, m);
        this.inEvent = true;
        sendEvent(11, localEvent);
        this.inEvent = false;
        if (isDisposed())
        {
          this.cancelled = true;
          return LRESULT.ONE;
        }
        i1 = 0;
        if (this.rectangles != arrayOfRectangle1)
        {
          i2 = this.rectangles.length;
          if (i2 != arrayOfRectangle2.length)
            i1 = 1;
          else
            for (i3 = 0; i3 < i2; i3++)
              if (!this.rectangles[i3].equals(arrayOfRectangle2[i3]))
              {
                i1 = 1;
                break;
              }
        }
        else
        {
          i1 = 1;
        }
        if (i1 != 0)
        {
          drawRectangles(arrayOfRectangle2, bool);
          update();
          drawRectangles(this.rectangles, this.stippled);
        }
        localObject = adjustResizeCursor();
      }
      else
      {
        moveRectangles(k, m);
        this.inEvent = true;
        sendEvent(10, localEvent);
        this.inEvent = false;
        if (isDisposed())
        {
          this.cancelled = true;
          return LRESULT.ONE;
        }
        i1 = 0;
        if (this.rectangles != arrayOfRectangle1)
        {
          i2 = this.rectangles.length;
          if (i2 != arrayOfRectangle2.length)
            i1 = 1;
          else
            for (i3 = 0; i3 < i2; i3++)
              if (!this.rectangles[i3].equals(arrayOfRectangle2[i3]))
              {
                i1 = 1;
                break;
              }
        }
        else
        {
          i1 = 1;
        }
        if (i1 != 0)
        {
          drawRectangles(arrayOfRectangle2, bool);
          update();
          drawRectangles(this.rectangles, this.stippled);
        }
        localObject = adjustMoveCursor();
      }
      if (localObject != null)
      {
        this.oldX = ((Point)localObject).x;
        this.oldY = ((Point)localObject).y;
      }
    }
    return localLRESULT;
  }

  LRESULT wmSysKeyDown(int paramInt1, int paramInt2, int paramInt3)
  {
    LRESULT localLRESULT = super.wmSysKeyDown(paramInt1, paramInt2, paramInt3);
    if (localLRESULT != null)
      return localLRESULT;
    this.cancelled = true;
    this.tracking = false;
    return localLRESULT;
  }

  LRESULT wmMouse(int paramInt1, int paramInt2, int paramInt3)
  {
    int i = (this.parent != null) && ((this.parent.style & 0x8000000) != 0) ? 1 : 0;
    int j = OS.GetMessagePos();
    int k = OS.GET_X_LPARAM(j);
    int m = OS.GET_Y_LPARAM(j);
    if ((k != this.oldX) || (m != this.oldY))
    {
      Rectangle[] arrayOfRectangle1 = this.rectangles;
      boolean bool = this.stippled;
      Rectangle[] arrayOfRectangle2 = new Rectangle[this.rectangles.length];
      for (int n = 0; n < this.rectangles.length; n++)
      {
        Rectangle localRectangle = this.rectangles[n];
        arrayOfRectangle2[n] = new Rectangle(localRectangle.x, localRectangle.y, localRectangle.width, localRectangle.height);
      }
      Event localEvent = new Event();
      localEvent.x = k;
      localEvent.y = m;
      int i1;
      int i4;
      if ((this.style & 0x10) != 0)
      {
        if (i != 0)
          resizeRectangles(this.oldX - k, m - this.oldY);
        else
          resizeRectangles(k - this.oldX, m - this.oldY);
        this.inEvent = true;
        sendEvent(11, localEvent);
        this.inEvent = false;
        if (isDisposed())
        {
          this.cancelled = true;
          return LRESULT.ONE;
        }
        i1 = 0;
        if (this.rectangles != arrayOfRectangle1)
        {
          int i2 = this.rectangles.length;
          if (i2 != arrayOfRectangle2.length)
            i1 = 1;
          else
            for (i4 = 0; i4 < i2; i4++)
              if (!this.rectangles[i4].equals(arrayOfRectangle2[i4]))
              {
                i1 = 1;
                break;
              }
        }
        else
        {
          i1 = 1;
        }
        if (i1 != 0)
        {
          drawRectangles(arrayOfRectangle2, bool);
          update();
          drawRectangles(this.rectangles, this.stippled);
        }
        Point localPoint = adjustResizeCursor();
        if (localPoint != null)
        {
          k = localPoint.x;
          m = localPoint.y;
        }
      }
      else
      {
        if (i != 0)
          moveRectangles(this.oldX - k, m - this.oldY);
        else
          moveRectangles(k - this.oldX, m - this.oldY);
        this.inEvent = true;
        sendEvent(10, localEvent);
        this.inEvent = false;
        if (isDisposed())
        {
          this.cancelled = true;
          return LRESULT.ONE;
        }
        i1 = 0;
        if (this.rectangles != arrayOfRectangle1)
        {
          int i3 = this.rectangles.length;
          if (i3 != arrayOfRectangle2.length)
            i1 = 1;
          else
            for (i4 = 0; i4 < i3; i4++)
              if (!this.rectangles[i4].equals(arrayOfRectangle2[i4]))
              {
                i1 = 1;
                break;
              }
        }
        else
        {
          i1 = 1;
        }
        if (i1 != 0)
        {
          drawRectangles(arrayOfRectangle2, bool);
          update();
          drawRectangles(this.rectangles, this.stippled);
        }
      }
      this.oldX = k;
      this.oldY = m;
    }
    this.tracking = (paramInt1 != 514);
    return null;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.Tracker
 * JD-Core Version:    0.6.2
 */