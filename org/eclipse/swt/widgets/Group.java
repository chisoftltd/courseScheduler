package org.eclipse.swt.widgets;

import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.TEXTMETRIC;
import org.eclipse.swt.internal.win32.TEXTMETRICA;
import org.eclipse.swt.internal.win32.TEXTMETRICW;
import org.eclipse.swt.internal.win32.WINDOWPOS;
import org.eclipse.swt.internal.win32.WNDCLASS;

public class Group extends Composite
{
  String text = "";
  static final int CLIENT_INSET = 3;
  static final int GroupProc;
  static final TCHAR GroupClass = new TCHAR(0, OS.IsWinCE ? "BUTTON" : "SWT_GROUP", true);

  static
  {
    WNDCLASS localWNDCLASS = new WNDCLASS();
    if (OS.IsWinCE)
    {
      OS.GetClassInfo(0, GroupClass, localWNDCLASS);
      GroupProc = localWNDCLASS.lpfnWndProc;
    }
    else
    {
      TCHAR localTCHAR = new TCHAR(0, "BUTTON", true);
      OS.GetClassInfo(0, localTCHAR, localWNDCLASS);
      GroupProc = localWNDCLASS.lpfnWndProc;
      int i = OS.GetModuleHandle(null);
      if (!OS.GetClassInfo(i, GroupClass, localWNDCLASS))
      {
        int j = OS.GetProcessHeap();
        localWNDCLASS.hInstance = i;
        localWNDCLASS.style &= -4;
        int k = GroupClass.length() * TCHAR.sizeof;
        int m = OS.HeapAlloc(j, 8, k);
        OS.MoveMemory(m, GroupClass, k);
        localWNDCLASS.lpszClassName = m;
        OS.RegisterClass(localWNDCLASS);
        OS.HeapFree(j, 0, m);
      }
    }
  }

  public Group(Composite paramComposite, int paramInt)
  {
    super(paramComposite, checkStyle(paramInt));
  }

  int callWindowProc(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.handle == 0)
      return 0;
    switch (paramInt2)
    {
    case 513:
    case 515:
      return OS.DefWindowProc(paramInt1, paramInt2, paramInt3, paramInt4);
    case 514:
    }
    return OS.CallWindowProc(GroupProc, paramInt1, paramInt2, paramInt3, paramInt4);
  }

  static int checkStyle(int paramInt)
  {
    paramInt |= 524288;
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
    int i = this.text.length();
    if (i != 0)
    {
      String str = this.text;
      if (((this.style & 0x4000000) != 0) && ((OS.COMCTL32_MAJOR < 6) || (!OS.IsAppThemed())))
        str = " " + str + " ";
      TCHAR localTCHAR = new TCHAR(getCodePage(), str, true);
      int k = 0;
      int m = OS.GetDC(this.handle);
      int j = OS.SendMessage(this.handle, 49, 0, 0);
      if (j != 0)
        k = OS.SelectObject(m, j);
      RECT localRECT = new RECT();
      int n = 1056;
      OS.DrawText(m, localTCHAR, -1, localRECT, n);
      if (j != 0)
        OS.SelectObject(m, k);
      OS.ReleaseDC(this.handle, m);
      int i1 = (OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()) ? 0 : 1;
      localPoint.x = Math.max(localPoint.x, localRECT.right - localRECT.left + 18 + i1);
    }
    return localPoint;
  }

  public Rectangle computeTrim(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    checkWidget();
    Rectangle localRectangle = super.computeTrim(paramInt1, paramInt2, paramInt3, paramInt4);
    int j = 0;
    int k = OS.GetDC(this.handle);
    int i = OS.SendMessage(this.handle, 49, 0, 0);
    if (i != 0)
      j = OS.SelectObject(k, i);
    TEXTMETRICA localTEXTMETRICA = OS.IsUnicode ? new TEXTMETRICW() : new TEXTMETRICA();
    OS.GetTextMetrics(k, localTEXTMETRICA);
    if (i != 0)
      OS.SelectObject(k, j);
    OS.ReleaseDC(this.handle, k);
    int m = (OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()) ? 0 : 1;
    localRectangle.x -= 3;
    localRectangle.y -= localTEXTMETRICA.tmHeight + m;
    localRectangle.width += 6;
    localRectangle.height += localTEXTMETRICA.tmHeight + 3 + m;
    return localRectangle;
  }

  void createHandle()
  {
    this.parent.state |= 1048576;
    super.createHandle();
    this.parent.state &= -1048577;
    this.state |= 512;
    this.state &= -3;
  }

  void enableWidget(boolean paramBoolean)
  {
    super.enableWidget(paramBoolean);
    if (((this.style & 0x4000000) != 0) && ((OS.COMCTL32_MAJOR < 6) || (!OS.IsAppThemed())))
    {
      String str = " " + this.text + " ";
      TCHAR localTCHAR = new TCHAR(getCodePage(), str, true);
      OS.SetWindowText(this.handle, localTCHAR);
    }
  }

  public Rectangle getClientArea()
  {
    checkWidget();
    forceResize();
    RECT localRECT = new RECT();
    OS.GetClientRect(this.handle, localRECT);
    int j = 0;
    int k = OS.GetDC(this.handle);
    int i = OS.SendMessage(this.handle, 49, 0, 0);
    if (i != 0)
      j = OS.SelectObject(k, i);
    TEXTMETRICA localTEXTMETRICA = OS.IsUnicode ? new TEXTMETRICW() : new TEXTMETRICA();
    OS.GetTextMetrics(k, localTEXTMETRICA);
    if (i != 0)
      OS.SelectObject(k, j);
    OS.ReleaseDC(this.handle, k);
    int m = (OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()) ? 0 : 1;
    int n = 3;
    int i1 = localTEXTMETRICA.tmHeight + m;
    int i2 = Math.max(0, localRECT.right - 6);
    int i3 = Math.max(0, localRECT.bottom - i1 - 3);
    return new Rectangle(n, i1, i2, i3);
  }

  String getNameText()
  {
    return getText();
  }

  public String getText()
  {
    checkWidget();
    return this.text;
  }

  boolean mnemonicHit(char paramChar)
  {
    return setFocus();
  }

  boolean mnemonicMatch(char paramChar)
  {
    char c = findMnemonic(getText());
    if (c == 0)
      return false;
    return Character.toUpperCase(paramChar) == Character.toUpperCase(c);
  }

  void printWidget(int paramInt1, int paramInt2, GC paramGC)
  {
    boolean bool = false;
    int i;
    if (OS.GetDeviceCaps(paramGC.handle, 2) != 2)
    {
      i = OS.GetWindowLong(paramInt1, -16);
      if ((i & 0x10000000) == 0)
        OS.ShowWindow(paramInt1, 5);
      bool = OS.PrintWindow(paramInt1, paramInt2, 0);
      if ((i & 0x10000000) == 0)
        OS.ShowWindow(paramInt1, 0);
    }
    if (!bool)
    {
      i = 14;
      OS.SendMessage(paramInt1, 791, paramInt2, i);
      int j = OS.SaveDC(paramInt2);
      Control[] arrayOfControl = _getChildren();
      Rectangle localRectangle = getBounds();
      OS.IntersectClipRect(paramInt2, 0, 0, localRectangle.width, localRectangle.height);
      for (int k = arrayOfControl.length - 1; k >= 0; k--)
      {
        Point localPoint = arrayOfControl[k].getLocation();
        int m = OS.GetGraphicsMode(paramInt2);
        if (m == 2)
        {
          float[] arrayOfFloat1 = { 1.0F, 0.0F, 0.0F, 1.0F, localPoint.x, localPoint.y };
          OS.ModifyWorldTransform(paramInt2, arrayOfFloat1, 2);
        }
        else
        {
          OS.SetWindowOrgEx(paramInt2, -localPoint.x, -localPoint.y, null);
        }
        int n = arrayOfControl[k].topHandle();
        int i1 = OS.GetWindowLong(n, -16);
        if ((i1 & 0x10000000) != 0)
          arrayOfControl[k].printWidget(n, paramInt2, paramGC);
        if (m == 2)
        {
          float[] arrayOfFloat2 = { 1.0F, 0.0F, 0.0F, 1.0F, -localPoint.x, -localPoint.y };
          OS.ModifyWorldTransform(paramInt2, arrayOfFloat2, 2);
        }
      }
      OS.RestoreDC(paramInt2, j);
    }
  }

  void releaseWidget()
  {
    super.releaseWidget();
    this.text = null;
  }

  public void setFont(Font paramFont)
  {
    checkWidget();
    Rectangle localRectangle1 = getClientArea();
    super.setFont(paramFont);
    Rectangle localRectangle2 = getClientArea();
    if (!localRectangle1.equals(localRectangle2))
      sendResize();
  }

  public void setText(String paramString)
  {
    checkWidget();
    if (paramString == null)
      error(4);
    this.text = paramString;
    if (((this.style & 0x4000000) != 0) && ((OS.COMCTL32_MAJOR < 6) || (!OS.IsAppThemed())) && (!OS.IsWindowEnabled(this.handle)) && (paramString.length() != 0))
      paramString = " " + paramString + " ";
    TCHAR localTCHAR = new TCHAR(getCodePage(), paramString, true);
    OS.SetWindowText(this.handle, localTCHAR);
  }

  int widgetStyle()
  {
    return super.widgetStyle() | 0x7 | 0x2000000 | 0x4000000;
  }

  TCHAR windowClass()
  {
    return GroupClass;
  }

  int windowProc()
  {
    return GroupProc;
  }

  LRESULT WM_ERASEBKGND(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_ERASEBKGND(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    drawBackground(paramInt1);
    return LRESULT.ONE;
  }

  LRESULT WM_NCHITTEST(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_NCHITTEST(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    int i = callWindowProc(this.handle, 132, paramInt1, paramInt2);
    if (i == -1)
      i = 1;
    return new LRESULT(i);
  }

  LRESULT WM_MOUSEMOVE(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_MOUSEMOVE(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    return LRESULT.ZERO;
  }

  LRESULT WM_PRINTCLIENT(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_PRINTCLIENT(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    if ((OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()))
    {
      int i = OS.SaveDC(paramInt1);
      int j = callWindowProc(this.handle, 792, paramInt1, paramInt2);
      OS.RestoreDC(paramInt1, i);
      return new LRESULT(j);
    }
    return localLRESULT;
  }

  LRESULT WM_UPDATEUISTATE(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_UPDATEUISTATE(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    int i = findImageControl() != null ? 1 : 0;
    if (i == 0)
    {
      if (((this.state & 0x100) != 0) && (OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()))
        i = findThemeControl() != null ? 1 : 0;
      if (i == 0)
        i = findBackgroundControl() != null ? 1 : 0;
    }
    if (i != 0)
    {
      OS.InvalidateRect(this.handle, null, false);
      int j = OS.DefWindowProc(this.handle, 296, paramInt1, paramInt2);
      return new LRESULT(j);
    }
    return localLRESULT;
  }

  LRESULT WM_WINDOWPOSCHANGING(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_WINDOWPOSCHANGING(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    if (OS.IsWinCE)
      return localLRESULT;
    if (!OS.IsWindowVisible(this.handle))
      return localLRESULT;
    WINDOWPOS localWINDOWPOS = new WINDOWPOS();
    OS.MoveMemory(localWINDOWPOS, paramInt2, WINDOWPOS.sizeof);
    if ((localWINDOWPOS.flags & 0x9) != 0)
      return localLRESULT;
    RECT localRECT = new RECT();
    OS.SetRect(localRECT, 0, 0, localWINDOWPOS.cx, localWINDOWPOS.cy);
    OS.SendMessage(this.handle, 131, 0, localRECT);
    int i = localRECT.right - localRECT.left;
    int j = localRECT.bottom - localRECT.top;
    OS.GetClientRect(this.handle, localRECT);
    int k = localRECT.right - localRECT.left;
    int m = localRECT.bottom - localRECT.top;
    if ((i == k) && (j == m))
      return localLRESULT;
    int n;
    if (i != k)
    {
      n = k;
      if (i < k)
        n = i;
      OS.SetRect(localRECT, n - 3, 0, i, j);
      OS.InvalidateRect(this.handle, localRECT, true);
    }
    if (j != m)
    {
      n = m;
      if (j < m)
        n = j;
      if (i < k)
        k -= 3;
      OS.SetRect(localRECT, 0, n - 3, k, j);
      OS.InvalidateRect(this.handle, localRECT, true);
    }
    return localLRESULT;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.Group
 * JD-Core Version:    0.6.2
 */