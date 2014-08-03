package org.eclipse.swt.widgets;

import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.win32.GUITHREADINFO;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.MSG;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.PAINTSTRUCT;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.SHRGINFO;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.TEXTMETRIC;
import org.eclipse.swt.internal.win32.TEXTMETRICA;
import org.eclipse.swt.internal.win32.TEXTMETRICW;
import org.eclipse.swt.internal.win32.WNDCLASS;

public class Text extends Scrollable
{
  int tabs;
  int oldStart;
  int oldEnd;
  boolean doubleClick;
  boolean ignoreModify;
  boolean ignoreVerify;
  boolean ignoreCharacter;
  String message;
  public static final int LIMIT = OS.IsWinNT ? 2147483647 : 32767;
  public static final String DELIMITER = "\r\n";
  static final int EditProc = localWNDCLASS.lpfnWndProc;
  static final TCHAR EditClass = new TCHAR(0, "EDIT", true);

  static
  {
    WNDCLASS localWNDCLASS = new WNDCLASS();
    OS.GetClassInfo(0, EditClass, localWNDCLASS);
  }

  public Text(Composite paramComposite, int paramInt)
  {
    super(paramComposite, checkStyle(paramInt));
  }

  int callWindowProc(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.handle == 0)
      return 0;
    int i = 0;
    switch (paramInt2)
    {
    case 20:
      if (findImageControl() != null)
        return 0;
      break;
    case 276:
    case 277:
      i = (findImageControl() != null) && (getDrawing()) && (OS.IsWindowVisible(this.handle)) ? 1 : 0;
      if (i != 0)
        OS.DefWindowProc(this.handle, 11, 0, 0);
      break;
    case 15:
      j = findImageControl() != null ? 1 : 0;
      int k = 0;
      if (((this.style & 0x4) != 0) && (this.message.length() > 0) && (!OS.IsWinCE) && (OS.WIN32_VERSION < OS.VERSION(6, 0)))
        k = (paramInt1 != OS.GetFocus()) && (OS.GetWindowTextLength(this.handle) == 0) ? 1 : 0;
      if ((j != 0) || (k != 0))
      {
        int m = 0;
        PAINTSTRUCT localPAINTSTRUCT = new PAINTSTRUCT();
        m = OS.BeginPaint(this.handle, localPAINTSTRUCT);
        int n = localPAINTSTRUCT.right - localPAINTSTRUCT.left;
        int i1 = localPAINTSTRUCT.bottom - localPAINTSTRUCT.top;
        if ((n != 0) && (i1 != 0))
        {
          int i2 = m;
          int i3 = 0;
          int i4 = 0;
          POINT localPOINT1 = null;
          POINT localPOINT2 = null;
          RECT localRECT;
          if (j != 0)
          {
            i2 = OS.CreateCompatibleDC(m);
            localPOINT1 = new POINT();
            localPOINT2 = new POINT();
            OS.SetWindowOrgEx(i2, localPAINTSTRUCT.left, localPAINTSTRUCT.top, localPOINT1);
            OS.SetBrushOrgEx(i2, localPAINTSTRUCT.left, localPAINTSTRUCT.top, localPOINT2);
            i3 = OS.CreateCompatibleBitmap(m, n, i1);
            i4 = OS.SelectObject(i2, i3);
            localRECT = new RECT();
            OS.SetRect(localRECT, localPAINTSTRUCT.left, localPAINTSTRUCT.top, localPAINTSTRUCT.right, localPAINTSTRUCT.bottom);
            drawBackground(i2, localRECT);
          }
          OS.CallWindowProc(EditProc, paramInt1, 15, i2, paramInt4);
          if (k != 0)
          {
            localRECT = new RECT();
            OS.GetClientRect(this.handle, localRECT);
            int i5 = OS.SendMessage(this.handle, 212, 0, 0);
            localRECT.left += OS.LOWORD(i5);
            localRECT.right -= OS.HIWORD(i5);
            if ((this.style & 0x800) != 0)
            {
              localRECT.left += 1;
              localRECT.top += 1;
              localRECT.right -= 1;
              localRECT.bottom -= 1;
            }
            TCHAR localTCHAR = new TCHAR(getCodePage(), this.message, false);
            int i6 = 8192;
            int i7 = (this.style & 0x4000000) != 0 ? 1 : 0;
            if (i7 != 0)
              i6 |= 131072;
            int i8 = this.style & 0x1024000;
            switch (i8)
            {
            case 16384:
              i6 |= (i7 != 0 ? 2 : 0);
              break;
            case 16777216:
              i6 |= 1;
            case 131072:
              i6 |= (i7 != 0 ? 0 : 2);
            }
            int i9 = OS.SendMessage(paramInt1, 49, 0, 0);
            int i10 = OS.SelectObject(i2, i9);
            OS.SetTextColor(i2, OS.GetSysColor(OS.COLOR_GRAYTEXT));
            OS.SetBkMode(i2, 1);
            OS.DrawText(i2, localTCHAR, localTCHAR.length(), localRECT, i6);
            OS.SelectObject(i2, i10);
          }
          if (j != 0)
          {
            OS.SetWindowOrgEx(i2, localPOINT1.x, localPOINT1.y, null);
            OS.SetBrushOrgEx(i2, localPOINT2.x, localPOINT2.y, null);
            OS.BitBlt(m, localPAINTSTRUCT.left, localPAINTSTRUCT.top, n, i1, i2, 0, 0, 13369376);
            OS.SelectObject(i2, i4);
            OS.DeleteObject(i3);
            OS.DeleteObject(i2);
          }
        }
        OS.EndPaint(this.handle, localPAINTSTRUCT);
        return 0;
      }
      break;
    }
    int j = OS.CallWindowProc(EditProc, paramInt1, paramInt2, paramInt3, paramInt4);
    switch (paramInt2)
    {
    case 276:
    case 277:
      if (i != 0)
      {
        OS.DefWindowProc(this.handle, 11, 1, 0);
        OS.InvalidateRect(this.handle, null, true);
      }
      break;
    }
    return j;
  }

  void createHandle()
  {
    super.createHandle();
    OS.SendMessage(this.handle, 197, 0, 0);
    if (((this.style & 0x8) != 0) && ((this.style & 0xB00) == 0))
      this.state |= 256;
  }

  public void addModifyListener(ModifyListener paramModifyListener)
  {
    checkWidget();
    if (paramModifyListener == null)
      error(4);
    TypedListener localTypedListener = new TypedListener(paramModifyListener);
    addListener(24, localTypedListener);
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

  public void addVerifyListener(VerifyListener paramVerifyListener)
  {
    checkWidget();
    if (paramVerifyListener == null)
      error(4);
    TypedListener localTypedListener = new TypedListener(paramVerifyListener);
    addListener(25, localTypedListener);
  }

  public void append(String paramString)
  {
    checkWidget();
    if (paramString == null)
      error(4);
    paramString = Display.withCrLf(paramString);
    int i = OS.GetWindowTextLength(this.handle);
    if ((hooks(25)) || (filters(25)))
    {
      paramString = verifyText(paramString, i, i, null);
      if (paramString == null)
        return;
    }
    OS.SendMessage(this.handle, 177, i, i);
    TCHAR localTCHAR = new TCHAR(getCodePage(), paramString, true);
    this.ignoreCharacter = true;
    OS.SendMessage(this.handle, 194, 0, localTCHAR);
    this.ignoreCharacter = false;
    OS.SendMessage(this.handle, 183, 0, 0);
  }

  static int checkStyle(int paramInt)
  {
    if ((paramInt & 0x80) != 0)
    {
      paramInt |= 2052;
      paramInt &= -4194305;
    }
    if (((paramInt & 0x4) != 0) && ((paramInt & 0x2) != 0))
      paramInt &= -3;
    paramInt = checkBits(paramInt, 16384, 16777216, 131072, 0, 0, 0);
    if ((paramInt & 0x4) != 0)
      paramInt &= -833;
    if ((paramInt & 0x40) != 0)
    {
      paramInt |= 2;
      paramInt &= -257;
    }
    if ((paramInt & 0x2) != 0)
      paramInt &= -4194305;
    if ((paramInt & 0x6) != 0)
      return paramInt;
    if ((paramInt & 0x300) != 0)
      return paramInt | 0x2;
    return paramInt | 0x4;
  }

  public void clearSelection()
  {
    checkWidget();
    if (OS.IsWinCE)
    {
      int[] arrayOfInt = new int[1];
      OS.SendMessage(this.handle, 176, null, arrayOfInt);
      OS.SendMessage(this.handle, 177, arrayOfInt[0], arrayOfInt[0]);
    }
    else
    {
      OS.SendMessage(this.handle, 177, -1, 0);
    }
  }

  public Point computeSize(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    checkWidget();
    int i = 0;
    int j = 0;
    if ((paramInt1 == -1) || (paramInt2 == -1))
    {
      int m = 0;
      int n = OS.GetDC(this.handle);
      int k = OS.SendMessage(this.handle, 49, 0, 0);
      if (k != 0)
        m = OS.SelectObject(n, k);
      TEXTMETRICA localTEXTMETRICA = OS.IsUnicode ? new TEXTMETRICW() : new TEXTMETRICA();
      OS.GetTextMetrics(n, localTEXTMETRICA);
      int i1 = (this.style & 0x4) != 0 ? 1 : OS.SendMessage(this.handle, 186, 0, 0);
      i = i1 * localTEXTMETRICA.tmHeight;
      RECT localRECT = new RECT();
      int i2 = 11264;
      int i3 = ((this.style & 0x2) != 0) && ((this.style & 0x40) != 0) ? 1 : 0;
      if ((i3 != 0) && (paramInt1 != -1))
      {
        i2 |= 16;
        localRECT.right = paramInt1;
      }
      int i4 = OS.GetWindowTextLength(this.handle);
      if (i4 != 0)
      {
        TCHAR localTCHAR1 = new TCHAR(getCodePage(), i4 + 1);
        OS.GetWindowText(this.handle, localTCHAR1, i4 + 1);
        OS.DrawText(n, localTCHAR1, i4, localRECT, i2);
        j = localRECT.right - localRECT.left;
      }
      if ((i3 != 0) && (paramInt2 == -1))
      {
        int i5 = localRECT.bottom - localRECT.top;
        if (i5 != 0)
          i = i5;
      }
      if (((this.style & 0x4) != 0) && (this.message.length() > 0))
      {
        OS.SetRect(localRECT, 0, 0, 0, 0);
        TCHAR localTCHAR2 = new TCHAR(getCodePage(), this.message, false);
        OS.DrawText(n, localTCHAR2, localTCHAR2.length(), localRECT, i2);
        j = Math.max(j, localRECT.right - localRECT.left);
      }
      if (k != 0)
        OS.SelectObject(n, m);
      OS.ReleaseDC(this.handle, n);
    }
    if (j == 0)
      j = 64;
    if (i == 0)
      i = 64;
    if (paramInt1 != -1)
      j = paramInt1;
    if (paramInt2 != -1)
      i = paramInt2;
    Rectangle localRectangle = computeTrim(0, 0, j, i);
    return new Point(localRectangle.width, localRectangle.height);
  }

  public Rectangle computeTrim(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    checkWidget();
    Rectangle localRectangle = super.computeTrim(paramInt1, paramInt2, paramInt3, paramInt4);
    int i = OS.SendMessage(this.handle, 212, 0, 0);
    localRectangle.x -= OS.LOWORD(i);
    localRectangle.width += OS.LOWORD(i) + OS.HIWORD(i);
    if ((this.style & 0x100) != 0)
      localRectangle.width += 1;
    if ((this.style & 0x800) != 0)
    {
      localRectangle.x -= 1;
      localRectangle.y -= 1;
      localRectangle.width += 2;
      localRectangle.height += 2;
    }
    return localRectangle;
  }

  public void copy()
  {
    checkWidget();
    OS.SendMessage(this.handle, 769, 0, 0);
  }

  void createWidget()
  {
    super.createWidget();
    this.message = "";
    this.doubleClick = true;
    setTabStops(this.tabs = 8);
    fixAlignment();
  }

  public void cut()
  {
    checkWidget();
    if ((this.style & 0x8) != 0)
      return;
    OS.SendMessage(this.handle, 768, 0, 0);
  }

  int defaultBackground()
  {
    int i = OS.GetWindowLong(this.handle, -16);
    return OS.GetSysColor((i & 0x800) != 0 ? OS.COLOR_3DFACE : OS.COLOR_WINDOW);
  }

  boolean dragDetect(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean, boolean[] paramArrayOfBoolean1, boolean[] paramArrayOfBoolean2)
  {
    if (paramBoolean)
    {
      int[] arrayOfInt1 = new int[1];
      int[] arrayOfInt2 = new int[1];
      OS.SendMessage(this.handle, 176, arrayOfInt1, arrayOfInt2);
      if (arrayOfInt1[0] != arrayOfInt2[0])
      {
        int i = OS.MAKELPARAM(paramInt2, paramInt3);
        int j = OS.LOWORD(OS.SendMessage(this.handle, 215, 0, i));
        if ((arrayOfInt1[0] <= j) && (j < arrayOfInt2[0]) && (super.dragDetect(paramInt1, paramInt2, paramInt3, paramBoolean, paramArrayOfBoolean1, paramArrayOfBoolean2)))
        {
          if (paramArrayOfBoolean2 != null)
            paramArrayOfBoolean2[0] = true;
          return true;
        }
      }
      return false;
    }
    return super.dragDetect(paramInt1, paramInt2, paramInt3, paramBoolean, paramArrayOfBoolean1, paramArrayOfBoolean2);
  }

  void fixAlignment()
  {
    if ((this.style & 0x8000000) != 0)
      return;
    int i = OS.GetWindowLong(this.handle, -20);
    int j = OS.GetWindowLong(this.handle, -16);
    if ((this.style & 0x2000000) != 0)
    {
      i &= -16385;
      if ((this.style & 0x20000) != 0)
      {
        i |= 4096;
        j |= 2;
      }
      if ((this.style & 0x4000) != 0)
      {
        i &= -4097;
        j &= -3;
      }
    }
    else
    {
      if ((this.style & 0x20000) != 0)
      {
        i &= -4097;
        j &= -3;
      }
      if ((this.style & 0x4000) != 0)
      {
        i |= 4096;
        j |= 2;
      }
    }
    if ((this.style & 0x1000000) != 0)
      j |= 1;
    OS.SetWindowLong(this.handle, -20, i);
    OS.SetWindowLong(this.handle, -16, j);
  }

  public int getBorderWidth()
  {
    checkWidget();
    return super.getBorderWidth();
  }

  public int getCaretLineNumber()
  {
    checkWidget();
    return OS.SendMessage(this.handle, 201, -1, 0);
  }

  public Point getCaretLocation()
  {
    checkWidget();
    int i = getCaretPosition();
    int j = OS.SendMessage(this.handle, 214, i, 0);
    if (j == -1)
    {
      j = 0;
      if (i >= OS.GetWindowTextLength(this.handle))
      {
        int k = getCodePage();
        int[] arrayOfInt1 = new int[1];
        int[] arrayOfInt2 = new int[1];
        OS.SendMessage(this.handle, 176, arrayOfInt1, arrayOfInt2);
        OS.SendMessage(this.handle, 177, i, i);
        this.ignoreCharacter = (this.ignoreModify = 1);
        OS.SendMessage(this.handle, 194, 0, new TCHAR(k, " ", true));
        j = OS.SendMessage(this.handle, 214, i, 0);
        OS.SendMessage(this.handle, 177, i, i + 1);
        OS.SendMessage(this.handle, 194, 0, new TCHAR(k, "", true));
        this.ignoreCharacter = (this.ignoreModify = 0);
        OS.SendMessage(this.handle, 177, arrayOfInt1[0], arrayOfInt1[0]);
        OS.SendMessage(this.handle, 177, arrayOfInt1[0], arrayOfInt2[0]);
      }
    }
    return new Point(OS.GET_X_LPARAM(j), OS.GET_Y_LPARAM(j));
  }

  public int getCaretPosition()
  {
    checkWidget();
    int[] arrayOfInt1 = new int[1];
    int[] arrayOfInt2 = new int[1];
    OS.SendMessage(this.handle, 176, arrayOfInt1, arrayOfInt2);
    int i = arrayOfInt1[0];
    if (arrayOfInt1[0] != arrayOfInt2[0])
    {
      int j = OS.SendMessage(this.handle, 201, arrayOfInt1[0], 0);
      int k = OS.SendMessage(this.handle, 201, arrayOfInt2[0], 0);
      int m;
      if (j == k)
      {
        if (!OS.IsWinCE)
        {
          m = OS.GetWindowThreadProcessId(this.handle, null);
          GUITHREADINFO localGUITHREADINFO = new GUITHREADINFO();
          localGUITHREADINFO.cbSize = GUITHREADINFO.sizeof;
          if ((OS.GetGUIThreadInfo(m, localGUITHREADINFO)) && ((localGUITHREADINFO.hwndCaret == this.handle) || (localGUITHREADINFO.hwndCaret == 0)))
          {
            POINT localPOINT = new POINT();
            if (OS.GetCaretPos(localPOINT))
            {
              int i1 = OS.SendMessage(this.handle, 214, arrayOfInt2[0], 0);
              int i2;
              if (i1 == -1)
              {
                i2 = OS.SendMessage(this.handle, 214, arrayOfInt1[0], 0);
                int i3 = OS.GET_X_LPARAM(i2);
                if (localPOINT.x > i3)
                  i = arrayOfInt2[0];
              }
              else
              {
                i2 = OS.GET_X_LPARAM(i1);
                if (localPOINT.x >= i2)
                  i = arrayOfInt2[0];
              }
            }
          }
        }
      }
      else
      {
        m = OS.SendMessage(this.handle, 187, -1, 0);
        int n = OS.SendMessage(this.handle, 201, m, 0);
        if (n == k)
          i = arrayOfInt2[0];
      }
    }
    if ((!OS.IsUnicode) && (OS.IsDBLocale))
      i = mbcsToWcsPos(i);
    return i;
  }

  public int getCharCount()
  {
    checkWidget();
    int i = OS.GetWindowTextLength(this.handle);
    if ((!OS.IsUnicode) && (OS.IsDBLocale))
      i = mbcsToWcsPos(i);
    return i;
  }

  public boolean getDoubleClickEnabled()
  {
    checkWidget();
    return this.doubleClick;
  }

  public char getEchoChar()
  {
    checkWidget();
    char c = (char)OS.SendMessage(this.handle, 210, 0, 0);
    if ((c != 0) && ((c = Display.mbcsToWcs(c, getCodePage())) == 0))
      c = '*';
    return c;
  }

  public boolean getEditable()
  {
    checkWidget();
    int i = OS.GetWindowLong(this.handle, -16);
    return (i & 0x800) == 0;
  }

  public int getLineCount()
  {
    checkWidget();
    return OS.SendMessage(this.handle, 186, 0, 0);
  }

  public String getLineDelimiter()
  {
    checkWidget();
    return DELIMITER;
  }

  public int getLineHeight()
  {
    checkWidget();
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
    return localTEXTMETRICA.tmHeight;
  }

  public int getOrientation()
  {
    checkWidget();
    return this.style & 0x6000000;
  }

  public String getMessage()
  {
    checkWidget();
    return this.message;
  }

  int getPosition(Point paramPoint)
  {
    checkWidget();
    if (paramPoint == null)
      error(4);
    int i = OS.MAKELPARAM(paramPoint.x, paramPoint.y);
    int j = OS.LOWORD(OS.SendMessage(this.handle, 215, 0, i));
    if ((!OS.IsUnicode) && (OS.IsDBLocale))
      j = mbcsToWcsPos(j);
    return j;
  }

  public Point getSelection()
  {
    checkWidget();
    int[] arrayOfInt1 = new int[1];
    int[] arrayOfInt2 = new int[1];
    OS.SendMessage(this.handle, 176, arrayOfInt1, arrayOfInt2);
    if ((!OS.IsUnicode) && (OS.IsDBLocale))
    {
      arrayOfInt1[0] = mbcsToWcsPos(arrayOfInt1[0]);
      arrayOfInt2[0] = mbcsToWcsPos(arrayOfInt2[0]);
    }
    return new Point(arrayOfInt1[0], arrayOfInt2[0]);
  }

  public int getSelectionCount()
  {
    checkWidget();
    Point localPoint = getSelection();
    return localPoint.y - localPoint.x;
  }

  public String getSelectionText()
  {
    checkWidget();
    int i = OS.GetWindowTextLength(this.handle);
    if (i == 0)
      return "";
    int[] arrayOfInt1 = new int[1];
    int[] arrayOfInt2 = new int[1];
    OS.SendMessage(this.handle, 176, arrayOfInt1, arrayOfInt2);
    if (arrayOfInt1[0] == arrayOfInt2[0])
      return "";
    TCHAR localTCHAR = new TCHAR(getCodePage(), i + 1);
    OS.GetWindowText(this.handle, localTCHAR, i + 1);
    return localTCHAR.toString(arrayOfInt1[0], arrayOfInt2[0] - arrayOfInt1[0]);
  }

  public int getTabs()
  {
    checkWidget();
    return this.tabs;
  }

  int getTabWidth(int paramInt)
  {
    int i = 0;
    RECT localRECT = new RECT();
    int j = OS.GetDC(this.handle);
    int k = OS.SendMessage(this.handle, 49, 0, 0);
    if (k != 0)
      i = OS.SelectObject(j, k);
    int m = 3104;
    TCHAR localTCHAR = new TCHAR(getCodePage(), " ", false);
    OS.DrawText(j, localTCHAR, localTCHAR.length(), localRECT, m);
    if (k != 0)
      OS.SelectObject(j, i);
    OS.ReleaseDC(this.handle, j);
    return (localRECT.right - localRECT.left) * paramInt;
  }

  public String getText()
  {
    checkWidget();
    int i = OS.GetWindowTextLength(this.handle);
    if (i == 0)
      return "";
    TCHAR localTCHAR = new TCHAR(getCodePage(), i + 1);
    OS.GetWindowText(this.handle, localTCHAR, i + 1);
    return localTCHAR.toString(0, i);
  }

  public String getText(int paramInt1, int paramInt2)
  {
    checkWidget();
    if ((paramInt1 > paramInt2) || (paramInt2 < 0))
      return "";
    int i = OS.GetWindowTextLength(this.handle);
    if ((!OS.IsUnicode) && (OS.IsDBLocale))
      i = mbcsToWcsPos(i);
    paramInt2 = Math.min(paramInt2, i - 1);
    if (paramInt1 > paramInt2)
      return "";
    paramInt1 = Math.max(0, paramInt1);
    return getText().substring(paramInt1, paramInt2 + 1);
  }

  public int getTextLimit()
  {
    checkWidget();
    return OS.SendMessage(this.handle, 213, 0, 0) & 0x7FFFFFFF;
  }

  public int getTopIndex()
  {
    checkWidget();
    if ((this.style & 0x4) != 0)
      return 0;
    return OS.SendMessage(this.handle, 206, 0, 0);
  }

  public int getTopPixel()
  {
    checkWidget();
    int[] arrayOfInt = new int[2];
    int i = OS.SendMessage(this.handle, 1245, 0, arrayOfInt);
    if (i == 1)
      return arrayOfInt[1];
    return getTopIndex() * getLineHeight();
  }

  public void insert(String paramString)
  {
    checkWidget();
    if (paramString == null)
      error(4);
    paramString = Display.withCrLf(paramString);
    if ((hooks(25)) || (filters(25)))
    {
      localObject = new int[1];
      int[] arrayOfInt = new int[1];
      OS.SendMessage(this.handle, 176, (int[])localObject, arrayOfInt);
      paramString = verifyText(paramString, localObject[0], arrayOfInt[0], null);
      if (paramString == null)
        return;
    }
    Object localObject = new TCHAR(getCodePage(), paramString, true);
    this.ignoreCharacter = true;
    OS.SendMessage(this.handle, 194, 0, (TCHAR)localObject);
    this.ignoreCharacter = false;
  }

  int mbcsToWcsPos(int paramInt)
  {
    if (paramInt <= 0)
      return 0;
    if (OS.IsUnicode)
      return paramInt;
    int i = getCodePage();
    int j = 0;
    int k = 0;
    byte[] arrayOfByte = new byte[''];
    String str = getLineDelimiter();
    int m = str.length();
    int n = OS.SendMessageA(this.handle, 186, 0, 0);
    for (int i1 = 0; i1 < n; i1++)
    {
      int i2 = 0;
      int i3 = OS.SendMessageA(this.handle, 187, i1, 0);
      int i4 = OS.SendMessageA(this.handle, 193, i3, 0);
      if (i4 != 0)
      {
        if (i4 + m > arrayOfByte.length)
          arrayOfByte = new byte[i4 + m];
        arrayOfByte[0] = ((byte)(i4 & 0xFF));
        arrayOfByte[1] = ((byte)(i4 >> 8));
        i4 = OS.SendMessageA(this.handle, 196, i1, arrayOfByte);
        i2 = OS.MultiByteToWideChar(i, 1, arrayOfByte, i4, null, 0);
      }
      int i5;
      if (i1 - 1 != n)
      {
        for (i5 = 0; i5 < m; i5++)
          arrayOfByte[(i4++)] = ((byte)str.charAt(i5));
        i2 += m;
      }
      if (k + i4 >= paramInt)
      {
        i5 = paramInt - k;
        i2 = OS.MultiByteToWideChar(i, 1, arrayOfByte, i5, null, 0);
        return j + i2;
      }
      j += i2;
      k += i4;
    }
    return j;
  }

  public void paste()
  {
    checkWidget();
    if ((this.style & 0x8) != 0)
      return;
    OS.SendMessage(this.handle, 770, 0, 0);
  }

  void releaseWidget()
  {
    super.releaseWidget();
    this.message = null;
  }

  public void removeModifyListener(ModifyListener paramModifyListener)
  {
    checkWidget();
    if (paramModifyListener == null)
      error(4);
    if (this.eventTable == null)
      return;
    this.eventTable.unhook(24, paramModifyListener);
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

  public void removeVerifyListener(VerifyListener paramVerifyListener)
  {
    checkWidget();
    if (paramVerifyListener == null)
      error(4);
    if (this.eventTable == null)
      return;
    this.eventTable.unhook(25, paramVerifyListener);
  }

  public void selectAll()
  {
    checkWidget();
    OS.SendMessage(this.handle, 177, 0, -1);
  }

  boolean sendKeyEvent(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Event paramEvent)
  {
    if (!super.sendKeyEvent(paramInt1, paramInt2, paramInt3, paramInt4, paramEvent))
      return false;
    if ((this.style & 0x8) != 0)
      return true;
    if (this.ignoreVerify)
      return true;
    if (paramInt1 != 1)
      return true;
    if ((paramInt2 != 258) && (paramInt2 != 256) && (paramInt2 != 646))
      return true;
    if (paramEvent.character == 0)
      return true;
    if ((!hooks(25)) && (!filters(25)))
      return true;
    int i = paramEvent.character;
    int j = paramEvent.stateMask;
    switch (paramInt2)
    {
    case 258:
    case 256:
      if (((i == 8) || (i == 127) || (i == 13) || (i == 9) || (i == 10)) && ((j & 0x70000) != 0))
        return false;
      break;
    case 257:
    }
    if ((OS.GetKeyState(1) < 0) && (this.handle == OS.GetCapture()))
      return true;
    String str1 = "";
    int[] arrayOfInt1 = new int[1];
    int[] arrayOfInt2 = new int[1];
    OS.SendMessage(this.handle, 176, arrayOfInt1, arrayOfInt2);
    int k;
    switch (i)
    {
    case 8:
      if (arrayOfInt1[0] == arrayOfInt2[0])
      {
        if (arrayOfInt1[0] == 0)
          return true;
        k = OS.SendMessage(this.handle, 187, -1, 0);
        if (arrayOfInt1[0] == k)
        {
          arrayOfInt1[0] -= DELIMITER.length();
        }
        else
        {
          arrayOfInt1[0] -= 1;
          if ((!OS.IsUnicode) && (OS.IsDBLocale))
          {
            int[] arrayOfInt3 = new int[1];
            int[] arrayOfInt4 = new int[1];
            OS.SendMessage(this.handle, 177, arrayOfInt1[0], arrayOfInt2[0]);
            OS.SendMessage(this.handle, 176, arrayOfInt3, arrayOfInt4);
            if (arrayOfInt1[0] != arrayOfInt3[0])
              arrayOfInt1[0] -= 1;
          }
        }
        arrayOfInt1[0] = Math.max(arrayOfInt1[0], 0);
      }
      break;
    case 127:
      if (arrayOfInt1[0] == arrayOfInt2[0])
      {
        k = OS.GetWindowTextLength(this.handle);
        if (arrayOfInt1[0] == k)
          return true;
        int m = OS.SendMessage(this.handle, 201, arrayOfInt2[0], 0);
        int n = OS.SendMessage(this.handle, 187, m + 1, 0);
        if (arrayOfInt2[0] == n - DELIMITER.length())
        {
          arrayOfInt2[0] += DELIMITER.length();
        }
        else
        {
          arrayOfInt2[0] += 1;
          if ((!OS.IsUnicode) && (OS.IsDBLocale))
          {
            int[] arrayOfInt5 = new int[1];
            int[] arrayOfInt6 = new int[1];
            OS.SendMessage(this.handle, 177, arrayOfInt1[0], arrayOfInt2[0]);
            OS.SendMessage(this.handle, 176, arrayOfInt5, arrayOfInt6);
            if (arrayOfInt2[0] != arrayOfInt6[0])
              arrayOfInt2[0] += 1;
          }
        }
        arrayOfInt2[0] = Math.min(arrayOfInt2[0], k);
      }
      break;
    case 13:
      if ((this.style & 0x4) != 0)
        return true;
      str1 = DELIMITER;
      break;
    default:
      if ((i != 9) && (i < 32))
        return true;
      str1 = new String(new char[] { i });
    }
    String str2 = verifyText(str1, arrayOfInt1[0], arrayOfInt2[0], paramEvent);
    if (str2 == null)
      return false;
    if (str2 == str1)
      return true;
    str2 = Display.withCrLf(str2);
    TCHAR localTCHAR = new TCHAR(getCodePage(), str2, true);
    OS.SendMessage(this.handle, 177, arrayOfInt1[0], arrayOfInt2[0]);
    this.ignoreCharacter = true;
    OS.SendMessage(this.handle, 194, 0, localTCHAR);
    this.ignoreCharacter = false;
    return false;
  }

  void setBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    int j;
    int k;
    Object localObject;
    if (((paramInt5 & 0x1) == 0) && (paramInt3 != 0))
    {
      RECT localRECT1 = new RECT();
      OS.GetWindowRect(this.handle, localRECT1);
      j = OS.SendMessage(this.handle, 212, 0, 0);
      k = OS.LOWORD(j) + OS.HIWORD(j);
      if (localRECT1.right - localRECT1.left <= k)
      {
        int[] arrayOfInt = new int[1];
        localObject = new int[1];
        OS.SendMessage(this.handle, 176, arrayOfInt, (int[])localObject);
        if ((arrayOfInt[0] != 0) || (localObject[0] != 0))
        {
          SetWindowPos(this.handle, 0, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
          OS.SendMessage(this.handle, 177, 0, 0);
          OS.SendMessage(this.handle, 177, arrayOfInt[0], localObject[0]);
          return;
        }
      }
    }
    super.setBounds(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    if ((paramInt5 & 0x1) == 0)
    {
      int i = OS.GetWindowLong(this.handle, -16);
      if ((i & 0x4) != 0)
      {
        k = 0;
        int m = OS.GetDC(this.handle);
        j = OS.SendMessage(this.handle, 49, 0, 0);
        if (j != 0)
          k = OS.SelectObject(m, j);
        localObject = OS.IsUnicode ? new TEXTMETRICW() : new TEXTMETRICA();
        OS.GetTextMetrics(m, (TEXTMETRIC)localObject);
        if (j != 0)
          OS.SelectObject(m, k);
        OS.ReleaseDC(this.handle, m);
        RECT localRECT2 = new RECT();
        OS.GetClientRect(this.handle, localRECT2);
        if (localRECT2.bottom - localRECT2.top < ((TEXTMETRIC)localObject).tmHeight)
        {
          int n = OS.SendMessage(this.handle, 212, 0, 0);
          localRECT2.left += OS.LOWORD(n);
          localRECT2.right -= OS.HIWORD(n);
          localRECT2.top = 0;
          localRECT2.bottom = ((TEXTMETRIC)localObject).tmHeight;
          OS.SendMessage(this.handle, 179, 0, localRECT2);
        }
      }
    }
  }

  void setDefaultFont()
  {
    super.setDefaultFont();
    setMargins();
  }

  public void setDoubleClickEnabled(boolean paramBoolean)
  {
    checkWidget();
    this.doubleClick = paramBoolean;
  }

  public void setEchoChar(char paramChar)
  {
    checkWidget();
    if ((this.style & 0x2) != 0)
      return;
    if ((paramChar != 0) && ((paramChar = (char)Display.wcsToMbcs(paramChar, getCodePage())) == 0))
      paramChar = '*';
    OS.SendMessage(this.handle, 204, paramChar, 0);
    OS.InvalidateRect(this.handle, null, true);
  }

  public void setEditable(boolean paramBoolean)
  {
    checkWidget();
    this.style &= -9;
    if (!paramBoolean)
      this.style |= 8;
    OS.SendMessage(this.handle, 207, paramBoolean ? 0 : 1, 0);
  }

  public void setFont(Font paramFont)
  {
    checkWidget();
    super.setFont(paramFont);
    setTabStops(this.tabs);
    setMargins();
  }

  void setMargins()
  {
    if (((this.style & 0x80) != 0) && (!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(6, 0)))
      OS.SendMessage(this.handle, 211, 3, 0);
  }

  public void setMessage(String paramString)
  {
    checkWidget();
    if (paramString == null)
      error(4);
    this.message = paramString;
    if (!OS.IsWinCE)
      if (OS.WIN32_VERSION >= OS.VERSION(6, 0))
      {
        int i = OS.GetWindowLong(this.handle, -16);
        if ((i & 0x4) == 0)
        {
          int j = paramString.length();
          char[] arrayOfChar = new char[j + 1];
          paramString.getChars(0, j, arrayOfChar, 0);
          OS.SendMessage(this.handle, 5377, 0, arrayOfChar);
        }
      }
      else
      {
        OS.InvalidateRect(this.handle, null, true);
      }
  }

  public void setOrientation(int paramInt)
  {
    checkWidget();
    if (OS.IsWinCE)
      return;
    if (OS.WIN32_VERSION < OS.VERSION(4, 10))
      return;
    int i = 100663296;
    if (((paramInt & i) == 0) || ((paramInt & i) == i))
      return;
    this.style &= (i ^ 0xFFFFFFFF);
    this.style |= paramInt & i;
    int j = OS.GetWindowLong(this.handle, -20);
    if ((this.style & 0x4000000) != 0)
      j |= 24576;
    else
      j &= -24577;
    OS.SetWindowLong(this.handle, -20, j);
    fixAlignment();
  }

  public void setSelection(int paramInt)
  {
    checkWidget();
    if ((!OS.IsUnicode) && (OS.IsDBLocale))
      paramInt = wcsToMbcsPos(paramInt);
    OS.SendMessage(this.handle, 177, paramInt, paramInt);
    OS.SendMessage(this.handle, 183, 0, 0);
  }

  public void setSelection(int paramInt1, int paramInt2)
  {
    checkWidget();
    if ((!OS.IsUnicode) && (OS.IsDBLocale))
    {
      paramInt1 = wcsToMbcsPos(paramInt1);
      paramInt2 = wcsToMbcsPos(paramInt2);
    }
    OS.SendMessage(this.handle, 177, paramInt1, paramInt2);
    OS.SendMessage(this.handle, 183, 0, 0);
  }

  public void setRedraw(boolean paramBoolean)
  {
    checkWidget();
    super.setRedraw(paramBoolean);
    if (!getDrawing())
      return;
    int[] arrayOfInt1 = new int[1];
    int[] arrayOfInt2 = new int[1];
    OS.SendMessage(this.handle, 176, arrayOfInt1, arrayOfInt2);
    if (!paramBoolean)
    {
      this.oldStart = arrayOfInt1[0];
      this.oldEnd = arrayOfInt2[0];
    }
    else
    {
      if ((this.oldStart == arrayOfInt1[0]) && (this.oldEnd == arrayOfInt2[0]))
        return;
      OS.SendMessage(this.handle, 183, 0, 0);
    }
  }

  public void setSelection(Point paramPoint)
  {
    checkWidget();
    if (paramPoint == null)
      error(4);
    setSelection(paramPoint.x, paramPoint.y);
  }

  public void setTabs(int paramInt)
  {
    checkWidget();
    if (paramInt < 0)
      return;
    setTabStops(this.tabs = paramInt);
  }

  void setTabStops(int paramInt)
  {
    int i = getTabWidth(paramInt) * 4 / OS.LOWORD(OS.GetDialogBaseUnits());
    OS.SendMessage(this.handle, 203, 1, new int[] { i });
  }

  public void setText(String paramString)
  {
    checkWidget();
    if (paramString == null)
      error(4);
    paramString = Display.withCrLf(paramString);
    if ((hooks(25)) || (filters(25)))
    {
      i = OS.GetWindowTextLength(this.handle);
      paramString = verifyText(paramString, 0, i, null);
      if (paramString == null)
        return;
    }
    int i = OS.SendMessage(this.handle, 213, 0, 0) & 0x7FFFFFFF;
    if (paramString.length() > i)
      paramString = paramString.substring(0, i);
    TCHAR localTCHAR = new TCHAR(getCodePage(), paramString, true);
    OS.SetWindowText(this.handle, localTCHAR);
    int j = OS.GetWindowLong(this.handle, -16);
    if ((j & 0x4) != 0)
      sendEvent(24);
  }

  public void setTextLimit(int paramInt)
  {
    checkWidget();
    if (paramInt == 0)
      error(7);
    OS.SendMessage(this.handle, 197, paramInt, 0);
  }

  public void setTopIndex(int paramInt)
  {
    checkWidget();
    if ((this.style & 0x4) != 0)
      return;
    int i = OS.SendMessage(this.handle, 186, 0, 0);
    paramInt = Math.min(Math.max(paramInt, 0), i - 1);
    int j = OS.SendMessage(this.handle, 206, 0, 0);
    OS.SendMessage(this.handle, 182, 0, paramInt - j);
  }

  public void showSelection()
  {
    checkWidget();
    OS.SendMessage(this.handle, 183, 0, 0);
  }

  String verifyText(String paramString, int paramInt1, int paramInt2, Event paramEvent)
  {
    if (this.ignoreVerify)
      return paramString;
    Event localEvent = new Event();
    localEvent.text = paramString;
    localEvent.start = paramInt1;
    localEvent.end = paramInt2;
    if (paramEvent != null)
    {
      localEvent.character = paramEvent.character;
      localEvent.keyCode = paramEvent.keyCode;
      localEvent.stateMask = paramEvent.stateMask;
    }
    if ((!OS.IsUnicode) && (OS.IsDBLocale))
    {
      localEvent.start = mbcsToWcsPos(paramInt1);
      localEvent.end = mbcsToWcsPos(paramInt2);
    }
    sendEvent(25, localEvent);
    if ((!localEvent.doit) || (isDisposed()))
      return null;
    return localEvent.text;
  }

  int wcsToMbcsPos(int paramInt)
  {
    if (paramInt <= 0)
      return 0;
    if (OS.IsUnicode)
      return paramInt;
    int i = getCodePage();
    int j = 0;
    int k = 0;
    byte[] arrayOfByte = new byte[''];
    String str = getLineDelimiter();
    int m = str.length();
    int n = OS.SendMessageA(this.handle, 186, 0, 0);
    for (int i1 = 0; i1 < n; i1++)
    {
      int i2 = 0;
      int i3 = OS.SendMessageA(this.handle, 187, i1, 0);
      int i4 = OS.SendMessageA(this.handle, 193, i3, 0);
      if (i4 != 0)
      {
        if (i4 + m > arrayOfByte.length)
          arrayOfByte = new byte[i4 + m];
        arrayOfByte[0] = ((byte)(i4 & 0xFF));
        arrayOfByte[1] = ((byte)(i4 >> 8));
        i4 = OS.SendMessageA(this.handle, 196, i1, arrayOfByte);
        i2 = OS.MultiByteToWideChar(i, 1, arrayOfByte, i4, null, 0);
      }
      int i5;
      if (i1 - 1 != n)
      {
        for (i5 = 0; i5 < m; i5++)
          arrayOfByte[(i4++)] = ((byte)str.charAt(i5));
        i2 += m;
      }
      if (j + i2 >= paramInt)
      {
        i2 = 0;
        i5 = 0;
        while (i5 < i4)
        {
          if (j + i2 == paramInt)
            return k + i5;
          if (OS.IsDBCSLeadByte(arrayOfByte[(i5++)]))
            i5++;
          i2++;
        }
        return k + i4;
      }
      j += i2;
      k += i4;
    }
    return k;
  }

  int widgetStyle()
  {
    int i = super.widgetStyle() | 0x80;
    if ((this.style & 0x400000) != 0)
      i |= 32;
    if ((this.style & 0x1000000) != 0)
      i |= 1;
    if ((this.style & 0x20000) != 0)
      i |= 2;
    if ((this.style & 0x8) != 0)
      i |= 2048;
    if ((this.style & 0x4) != 0)
    {
      if (((this.style & 0x8) != 0) && ((this.style & 0xB00) == 0) && (OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()))
        i |= 4;
      return i;
    }
    i |= 324;
    if ((this.style & 0x40) != 0)
      i &= -1048705;
    return i;
  }

  TCHAR windowClass()
  {
    return EditClass;
  }

  int windowProc()
  {
    return EditProc;
  }

  int windowProc(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (paramInt2 == 199)
    {
      int i = OS.GetWindowLong(this.handle, -16);
      if ((i & 0x4) == 0)
      {
        LRESULT localLRESULT = wmClipboard(199, paramInt3, paramInt4);
        if (localLRESULT != null)
          return localLRESULT.value;
        return callWindowProc(paramInt1, 199, paramInt3, paramInt4);
      }
    }
    if (paramInt2 == Display.SWT_RESTORECARET)
    {
      callWindowProc(paramInt1, 8, 0, 0);
      callWindowProc(paramInt1, 7, 0, 0);
      return 1;
    }
    return super.windowProc(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  LRESULT WM_CHAR(int paramInt1, int paramInt2)
  {
    if (this.ignoreCharacter)
      return null;
    LRESULT localLRESULT = super.WM_CHAR(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    switch (paramInt1)
    {
    case 127:
      if (OS.GetKeyState(17) < 0)
        return LRESULT.ZERO;
      break;
    }
    if ((this.style & 0x4) != 0)
      switch (paramInt1)
      {
      case 13:
        sendSelectionEvent(14);
      case 9:
      case 27:
        return LRESULT.ZERO;
      }
    return localLRESULT;
  }

  LRESULT WM_CLEAR(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_CLEAR(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    return wmClipboard(771, paramInt1, paramInt2);
  }

  LRESULT WM_CUT(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_CUT(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    return wmClipboard(768, paramInt1, paramInt2);
  }

  LRESULT WM_ERASEBKGND(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_ERASEBKGND(paramInt1, paramInt2);
    if (((this.style & 0x8) != 0) && ((this.style & 0xB00) == 0))
    {
      int i = OS.GetWindowLong(this.handle, -16);
      if ((i & 0x4) != 0)
      {
        Control localControl = findBackgroundControl();
        if ((localControl == null) && (this.background == -1) && ((this.state & 0x100) != 0) && (OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()))
        {
          localControl = findThemeControl();
          if (localControl != null)
          {
            RECT localRECT = new RECT();
            OS.GetClientRect(this.handle, localRECT);
            fillThemeBackground(paramInt1, localControl, localRECT);
            return LRESULT.ONE;
          }
        }
      }
    }
    return localLRESULT;
  }

  LRESULT WM_GETDLGCODE(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_GETDLGCODE(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    if ((OS.IsPPC) && ((this.style & 0x2) != 0) && ((this.style & 0x8) == 0) && (paramInt2 == 0))
      return new LRESULT(140);
    if ((this.style & 0x8) != 0)
    {
      int i = callWindowProc(this.handle, 135, paramInt1, paramInt2);
      i &= -7;
      return new LRESULT(i);
    }
    return null;
  }

  LRESULT WM_IME_CHAR(int paramInt1, int paramInt2)
  {
    Display localDisplay = this.display;
    localDisplay.lastKey = 0;
    localDisplay.lastAscii = paramInt1;
    localDisplay.lastVirtual = (localDisplay.lastNull = localDisplay.lastDead = 0);
    if (!sendKeyEvent(1, 646, paramInt1, paramInt2))
      return LRESULT.ZERO;
    this.ignoreCharacter = true;
    int i = callWindowProc(this.handle, 646, paramInt1, paramInt2);
    MSG localMSG = new MSG();
    int j = 10420227;
    while (OS.PeekMessage(localMSG, this.handle, 258, 258, j))
    {
      OS.TranslateMessage(localMSG);
      OS.DispatchMessage(localMSG);
    }
    this.ignoreCharacter = false;
    sendKeyEvent(2, 646, paramInt1, paramInt2);
    localDisplay.lastKey = (localDisplay.lastAscii = 0);
    return new LRESULT(i);
  }

  LRESULT WM_LBUTTONDBLCLK(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = null;
    sendMouseEvent(3, 1, this.handle, 513, paramInt1, paramInt2);
    if (!sendMouseEvent(8, 1, this.handle, 515, paramInt1, paramInt2))
      localLRESULT = LRESULT.ZERO;
    if ((!this.display.captureChanged) && (!isDisposed()) && (OS.GetCapture() != this.handle))
      OS.SetCapture(this.handle);
    if (!this.doubleClick)
      return LRESULT.ZERO;
    int[] arrayOfInt1 = new int[1];
    int[] arrayOfInt2 = new int[1];
    OS.SendMessage(this.handle, 176, arrayOfInt1, arrayOfInt2);
    if (arrayOfInt1[0] == arrayOfInt2[0])
    {
      int i = OS.GetWindowTextLength(this.handle);
      if (i == arrayOfInt1[0])
      {
        int j = OS.SendMessage(this.handle, 193, i, 0);
        if (j == 0)
          return LRESULT.ZERO;
      }
    }
    return localLRESULT;
  }

  LRESULT WM_LBUTTONDOWN(int paramInt1, int paramInt2)
  {
    if (OS.IsPPC)
    {
      LRESULT localLRESULT = null;
      Display localDisplay = this.display;
      localDisplay.captureChanged = false;
      boolean bool = sendMouseEvent(3, 1, this.handle, 513, paramInt1, paramInt2);
      int i = (this.menu != null) && (!this.menu.isDisposed()) ? 1 : 0;
      if ((i != 0) || (hooks(35)))
      {
        int j = OS.GET_X_LPARAM(paramInt2);
        int k = OS.GET_Y_LPARAM(paramInt2);
        SHRGINFO localSHRGINFO = new SHRGINFO();
        localSHRGINFO.cbSize = SHRGINFO.sizeof;
        localSHRGINFO.hwndClient = this.handle;
        localSHRGINFO.ptDown_x = j;
        localSHRGINFO.ptDown_y = k;
        localSHRGINFO.dwFlags = 1;
        int m = OS.SHRecognizeGesture(localSHRGINFO);
        if (m == 1000)
        {
          showMenu(j, k);
          return LRESULT.ONE;
        }
      }
      if (bool)
        localLRESULT = new LRESULT(callWindowProc(this.handle, 513, paramInt1, paramInt2));
      else
        localLRESULT = LRESULT.ZERO;
      if ((!localDisplay.captureChanged) && (!isDisposed()) && (OS.GetCapture() != this.handle))
        OS.SetCapture(this.handle);
      return localLRESULT;
    }
    return super.WM_LBUTTONDOWN(paramInt1, paramInt2);
  }

  LRESULT WM_PASTE(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_PASTE(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    return wmClipboard(770, paramInt1, paramInt2);
  }

  LRESULT WM_UNDO(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_UNDO(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    return wmClipboard(772, paramInt1, paramInt2);
  }

  LRESULT wmClipboard(int paramInt1, int paramInt2, int paramInt3)
  {
    if ((this.style & 0x8) != 0)
      return null;
    if ((!hooks(25)) && (!filters(25)))
      return null;
    int i = 0;
    int[] arrayOfInt1 = new int[1];
    int[] arrayOfInt2 = new int[1];
    String str1 = null;
    Object localObject;
    switch (paramInt1)
    {
    case 768:
    case 771:
      OS.SendMessage(this.handle, 176, arrayOfInt1, arrayOfInt2);
      if (arrayOfInt1[0] != arrayOfInt2[0])
      {
        str1 = "";
        i = 1;
      }
      break;
    case 770:
      OS.SendMessage(this.handle, 176, arrayOfInt1, arrayOfInt2);
      str1 = getClipboardText();
      break;
    case 199:
    case 772:
      if (OS.SendMessage(this.handle, 198, 0, 0) != 0)
      {
        this.ignoreModify = (this.ignoreCharacter = 1);
        callWindowProc(this.handle, paramInt1, paramInt2, paramInt3);
        int j = OS.GetWindowTextLength(this.handle);
        localObject = new int[1];
        int[] arrayOfInt3 = new int[1];
        OS.SendMessage(this.handle, 176, (int[])localObject, arrayOfInt3);
        if ((j != 0) && (localObject[0] != arrayOfInt3[0]))
        {
          TCHAR localTCHAR = new TCHAR(getCodePage(), j + 1);
          OS.GetWindowText(this.handle, localTCHAR, j + 1);
          str1 = localTCHAR.toString(localObject[0], arrayOfInt3[0] - localObject[0]);
        }
        else
        {
          str1 = "";
        }
        callWindowProc(this.handle, paramInt1, paramInt2, paramInt3);
        OS.SendMessage(this.handle, 176, arrayOfInt1, arrayOfInt2);
        this.ignoreModify = (this.ignoreCharacter = 0);
      }
      break;
    }
    if (str1 != null)
    {
      String str2 = str1;
      str1 = verifyText(str1, arrayOfInt1[0], arrayOfInt2[0], null);
      if (str1 == null)
        return LRESULT.ZERO;
      if (!str1.equals(str2))
      {
        if (i != 0)
          callWindowProc(this.handle, paramInt1, paramInt2, paramInt3);
        str1 = Display.withCrLf(str1);
        localObject = new TCHAR(getCodePage(), str1, true);
        this.ignoreCharacter = true;
        OS.SendMessage(this.handle, 194, 0, (TCHAR)localObject);
        this.ignoreCharacter = false;
        return LRESULT.ZERO;
      }
    }
    if (paramInt1 == 772)
    {
      this.ignoreVerify = (this.ignoreCharacter = 1);
      callWindowProc(this.handle, 772, paramInt2, paramInt3);
      this.ignoreVerify = (this.ignoreCharacter = 0);
      return LRESULT.ONE;
    }
    return null;
  }

  LRESULT wmColorChild(int paramInt1, int paramInt2)
  {
    if (((this.style & 0x8) != 0) && ((this.style & 0xB00) == 0))
    {
      int i = OS.GetWindowLong(this.handle, -16);
      if ((i & 0x4) != 0)
      {
        Control localControl = findBackgroundControl();
        if ((localControl == null) && (this.background == -1) && ((this.state & 0x100) != 0) && (OS.COMCTL32_MAJOR >= 6) && (OS.IsAppThemed()))
        {
          localControl = findThemeControl();
          if (localControl != null)
          {
            OS.SetTextColor(paramInt1, getForegroundPixel());
            OS.SetBkColor(paramInt1, getBackgroundPixel());
            OS.SetBkMode(paramInt1, 1);
            return new LRESULT(OS.GetStockObject(5));
          }
        }
      }
    }
    return super.wmColorChild(paramInt1, paramInt2);
  }

  LRESULT wmCommandChild(int paramInt1, int paramInt2)
  {
    int i = OS.HIWORD(paramInt1);
    switch (i)
    {
    case 768:
      if (findImageControl() != null)
        OS.InvalidateRect(this.handle, null, true);
      if (!this.ignoreModify)
      {
        sendEvent(24);
        if (isDisposed())
          return LRESULT.ZERO;
      }
      break;
    case 1792:
    case 1793:
      int j = OS.GetWindowLong(this.handle, -20);
      if ((j & 0x2000) != 0)
      {
        this.style &= -33554433;
        this.style |= 67108864;
      }
      else
      {
        this.style &= -67108865;
        this.style |= 33554432;
      }
      Event localEvent = new Event();
      localEvent.doit = true;
      sendEvent(44, localEvent);
      if (!localEvent.doit)
      {
        if (i == 1792)
        {
          j |= 24576;
          this.style &= -33554433;
          this.style |= 67108864;
        }
        else
        {
          j &= -24577;
          this.style &= -67108865;
          this.style |= 33554432;
        }
        OS.SetWindowLong(this.handle, -20, j);
      }
      fixAlignment();
    }
    return super.wmCommandChild(paramInt1, paramInt2);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.Text
 * JD-Core Version:    0.6.2
 */