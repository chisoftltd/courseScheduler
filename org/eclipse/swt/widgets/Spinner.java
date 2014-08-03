package org.eclipse.swt.widgets;

import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.MSG;
import org.eclipse.swt.internal.win32.NMHDR;
import org.eclipse.swt.internal.win32.NMUPDOWN;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.TEXTMETRIC;
import org.eclipse.swt.internal.win32.TEXTMETRICA;
import org.eclipse.swt.internal.win32.TEXTMETRICW;
import org.eclipse.swt.internal.win32.UDACCEL;
import org.eclipse.swt.internal.win32.WNDCLASS;

public class Spinner extends Composite
{
  int hwndText;
  int hwndUpDown;
  boolean ignoreModify;
  boolean ignoreCharacter;
  int pageIncrement;
  int digits;
  static final int EditProc;
  static final TCHAR EditClass = new TCHAR(0, "EDIT", true);
  static final int UpDownProc = localWNDCLASS.lpfnWndProc;
  static final TCHAR UpDownClass = new TCHAR(0, "msctls_updown32", true);
  public static final int LIMIT = OS.IsWinNT ? 2147483647 : 32767;

  static
  {
    WNDCLASS localWNDCLASS = new WNDCLASS();
    OS.GetClassInfo(0, EditClass, localWNDCLASS);
    EditProc = localWNDCLASS.lpfnWndProc;
    OS.GetClassInfo(0, UpDownClass, localWNDCLASS);
  }

  public Spinner(Composite paramComposite, int paramInt)
  {
    super(paramComposite, checkStyle(paramInt));
  }

  int callWindowProc(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.handle == 0)
      return 0;
    if (paramInt1 == this.hwndText)
      return OS.CallWindowProc(EditProc, paramInt1, paramInt2, paramInt3, paramInt4);
    if (paramInt1 == this.hwndUpDown)
      return OS.CallWindowProc(UpDownProc, paramInt1, paramInt2, paramInt3, paramInt4);
    return OS.DefWindowProc(this.handle, paramInt2, paramInt3, paramInt4);
  }

  static int checkStyle(int paramInt)
  {
    return paramInt & 0xFFFFFCFF;
  }

  boolean checkHandle(int paramInt)
  {
    return (paramInt == this.handle) || (paramInt == this.hwndText) || (paramInt == this.hwndUpDown);
  }

  protected void checkSubclass()
  {
    if (!isValidSubclass())
      error(43);
  }

  void createHandle()
  {
    super.createHandle();
    this.state &= -259;
    int i = OS.GetModuleHandle(null);
    int j = (this.style & 0x800) != 0 ? 512 : 0;
    int k = 1409286272;
    if ((this.style & 0x8) != 0)
      k |= 2048;
    if ((OS.WIN32_VERSION >= OS.VERSION(4, 10)) && ((this.style & 0x4000000) != 0))
      j |= 4194304;
    this.hwndText = OS.CreateWindowEx(j, EditClass, null, k, 0, 0, 0, 0, this.handle, 0, i, null);
    if (this.hwndText == 0)
      error(2);
    OS.SetWindowLongPtr(this.hwndText, -12, this.hwndText);
    int m = 1342177296;
    if ((this.style & 0x40) != 0)
      m |= 1;
    if ((this.style & 0x800) != 0)
      if ((this.style & 0x4000000) != 0)
        m |= 8;
      else
        m |= 4;
    this.hwndUpDown = OS.CreateWindowEx(0, UpDownClass, null, m, 0, 0, 0, 0, this.handle, 0, i, null);
    if (this.hwndUpDown == 0)
      error(2);
    int n = 19;
    SetWindowPos(this.hwndText, this.hwndUpDown, 0, 0, 0, 0, n);
    OS.SetWindowLongPtr(this.hwndUpDown, -12, this.hwndUpDown);
    if (OS.IsDBLocale)
    {
      int i1 = OS.ImmGetContext(this.handle);
      OS.ImmAssociateContext(this.hwndText, i1);
      OS.ImmAssociateContext(this.hwndUpDown, i1);
      OS.ImmReleaseContext(this.handle, i1);
    }
    OS.SendMessage(this.hwndUpDown, 1135, 0, 100);
    OS.SendMessage(this.hwndUpDown, OS.IsWinCE ? 1127 : 1137, 0, 0);
    this.pageIncrement = 10;
    this.digits = 0;
    TCHAR localTCHAR = new TCHAR(getCodePage(), "0", true);
    OS.SetWindowText(this.hwndText, localTCHAR);
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

  void addVerifyListener(VerifyListener paramVerifyListener)
  {
    checkWidget();
    if (paramVerifyListener == null)
      error(4);
    TypedListener localTypedListener = new TypedListener(paramVerifyListener);
    addListener(25, localTypedListener);
  }

  int borderHandle()
  {
    return this.hwndText;
  }

  public Point computeSize(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    checkWidget();
    int i = 0;
    int j = 0;
    int m;
    if ((paramInt1 == -1) || (paramInt2 == -1))
    {
      m = 0;
      int n = OS.GetDC(this.hwndText);
      int k = OS.SendMessage(this.hwndText, 49, 0, 0);
      if (k != 0)
        m = OS.SelectObject(n, k);
      TEXTMETRICA localTEXTMETRICA = OS.IsUnicode ? new TEXTMETRICW() : new TEXTMETRICA();
      OS.GetTextMetrics(n, localTEXTMETRICA);
      j = localTEXTMETRICA.tmHeight;
      RECT localRECT = new RECT();
      int[] arrayOfInt = new int[1];
      OS.SendMessage(this.hwndUpDown, 1136, null, arrayOfInt);
      String str = String.valueOf(arrayOfInt[0]);
      if (this.digits > 0)
      {
        localObject = new StringBuffer();
        ((StringBuffer)localObject).append(str);
        ((StringBuffer)localObject).append(getDecimalSeparator());
        for (i1 = this.digits - str.length(); i1 >= 0; i1--)
          ((StringBuffer)localObject).append("0");
        str = ((StringBuffer)localObject).toString();
      }
      Object localObject = new TCHAR(getCodePage(), str, false);
      int i1 = 11264;
      OS.DrawText(n, (TCHAR)localObject, ((TCHAR)localObject).length(), localRECT, i1);
      i = localRECT.right - localRECT.left;
      if (k != 0)
        OS.SelectObject(n, m);
      OS.ReleaseDC(this.hwndText, n);
    }
    if (i == 0)
      i = 64;
    if (j == 0)
      j = 64;
    if (paramInt1 != -1)
      i = paramInt1;
    if (paramInt2 != -1)
      j = paramInt2;
    Rectangle localRectangle = computeTrim(0, 0, i, j);
    if (paramInt2 == -1)
    {
      m = OS.GetSystemMetrics(20) + 2 * getBorderWidth();
      if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(6, 0)))
        m += ((this.style & 0x800) != 0 ? 1 : 3);
      localRectangle.height = Math.max(localRectangle.height, m);
    }
    return new Point(localRectangle.width, localRectangle.height);
  }

  public Rectangle computeTrim(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    checkWidget();
    RECT localRECT = new RECT();
    OS.SetRect(localRECT, paramInt1, paramInt2, paramInt1 + paramInt3, paramInt2 + paramInt4);
    int i = OS.GetWindowLong(this.hwndText, -16);
    int j = OS.GetWindowLong(this.hwndText, -20);
    OS.AdjustWindowRectEx(localRECT, i, false, j);
    paramInt3 = localRECT.right - localRECT.left;
    paramInt4 = localRECT.bottom - localRECT.top;
    int k = OS.SendMessage(this.hwndText, 212, 0, 0);
    paramInt1 -= OS.LOWORD(k);
    paramInt3 += OS.LOWORD(k) + OS.HIWORD(k);
    if ((this.style & 0x800) != 0)
    {
      paramInt1--;
      paramInt2--;
      paramInt3 += 2;
      paramInt4 += 2;
    }
    paramInt3 += OS.GetSystemMetrics(2);
    return new Rectangle(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  public void copy()
  {
    checkWidget();
    OS.SendMessage(this.hwndText, 769, 0, 0);
  }

  public void cut()
  {
    checkWidget();
    if ((this.style & 0x8) != 0)
      return;
    OS.SendMessage(this.hwndText, 768, 0, 0);
  }

  int defaultBackground()
  {
    return OS.GetSysColor(OS.COLOR_WINDOW);
  }

  void enableWidget(boolean paramBoolean)
  {
    super.enableWidget(paramBoolean);
    OS.EnableWindow(this.hwndText, paramBoolean);
    OS.EnableWindow(this.hwndUpDown, paramBoolean);
  }

  void deregister()
  {
    super.deregister();
    this.display.removeControl(this.hwndText);
    this.display.removeControl(this.hwndUpDown);
  }

  boolean hasFocus()
  {
    int i = OS.GetFocus();
    if (i == this.handle)
      return true;
    if (i == this.hwndText)
      return true;
    return i == this.hwndUpDown;
  }

  public int getDigits()
  {
    checkWidget();
    return this.digits;
  }

  String getDecimalSeparator()
  {
    TCHAR localTCHAR = new TCHAR(getCodePage(), 4);
    int i = OS.GetLocaleInfo(1024, 14, localTCHAR, 4);
    return i != 0 ? localTCHAR.toString(0, i - 1) : ".";
  }

  public int getIncrement()
  {
    checkWidget();
    UDACCEL localUDACCEL = new UDACCEL();
    OS.SendMessage(this.hwndUpDown, 1132, 1, localUDACCEL);
    return localUDACCEL.nInc;
  }

  public int getMaximum()
  {
    checkWidget();
    int[] arrayOfInt = new int[1];
    OS.SendMessage(this.hwndUpDown, 1136, null, arrayOfInt);
    return arrayOfInt[0];
  }

  public int getMinimum()
  {
    checkWidget();
    int[] arrayOfInt = new int[1];
    OS.SendMessage(this.hwndUpDown, 1136, arrayOfInt, null);
    return arrayOfInt[0];
  }

  public int getPageIncrement()
  {
    checkWidget();
    return this.pageIncrement;
  }

  public int getSelection()
  {
    checkWidget();
    if (OS.IsWinCE)
      return OS.LOWORD(OS.SendMessage(this.hwndUpDown, 1128, 0, 0));
    return OS.SendMessage(this.hwndUpDown, 1138, 0, 0);
  }

  int getSelectionText(boolean[] paramArrayOfBoolean)
  {
    int i = OS.GetWindowTextLength(this.hwndText);
    TCHAR localTCHAR = new TCHAR(getCodePage(), i + 1);
    OS.GetWindowText(this.hwndText, localTCHAR, i + 1);
    String str1 = localTCHAR.toString(0, i);
    try
    {
      int j;
      if (this.digits > 0)
      {
        localObject = getDecimalSeparator();
        int k = str1.indexOf((String)localObject);
        int m;
        if (k != -1)
        {
          m = (str1.startsWith("+")) || (str1.startsWith("-")) ? 1 : 0;
          String str2 = m != k ? str1.substring(m, k) : "0";
          String str3 = str1.substring(k + 1);
          if (str3.length() > this.digits)
          {
            str3 = str3.substring(0, this.digits);
          }
          else
          {
            n = this.digits - str3.length();
            for (i1 = 0; i1 < n; i1++)
              str3 = str3 + "0";
          }
          int n = Integer.parseInt(str2);
          int i1 = Integer.parseInt(str3);
          for (int i2 = 0; i2 < this.digits; i2++)
            n *= 10;
          j = n + i1;
          if (str1.startsWith("-"))
            j = -j;
        }
        else
        {
          j = Integer.parseInt(str1);
          for (m = 0; m < this.digits; m++)
            j *= 10;
        }
      }
      else
      {
        j = Integer.parseInt(str1);
      }
      Object localObject = new int[1];
      int[] arrayOfInt = new int[1];
      OS.SendMessage(this.hwndUpDown, 1136, arrayOfInt, (int[])localObject);
      if ((arrayOfInt[0] <= j) && (j <= localObject[0]))
        return j;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      paramArrayOfBoolean[0] = true;
    }
    return -1;
  }

  public String getText()
  {
    checkWidget();
    int i = OS.GetWindowTextLength(this.hwndText);
    if (i == 0)
      return "";
    TCHAR localTCHAR = new TCHAR(getCodePage(), i + 1);
    OS.GetWindowText(this.hwndText, localTCHAR, i + 1);
    return localTCHAR.toString(0, i);
  }

  public int getTextLimit()
  {
    checkWidget();
    return OS.SendMessage(this.hwndText, 213, 0, 0) & 0x7FFFFFFF;
  }

  int mbcsToWcsPos(int paramInt)
  {
    if (paramInt <= 0)
      return 0;
    if (OS.IsUnicode)
      return paramInt;
    int i = OS.GetWindowTextLengthA(this.hwndText);
    if (i == 0)
      return 0;
    if (paramInt >= i)
      return i;
    byte[] arrayOfByte = new byte[i + 1];
    OS.GetWindowTextA(this.hwndText, arrayOfByte, i + 1);
    return OS.MultiByteToWideChar(getCodePage(), 1, arrayOfByte, paramInt, null, 0);
  }

  public void paste()
  {
    checkWidget();
    if ((this.style & 0x8) != 0)
      return;
    OS.SendMessage(this.hwndText, 770, 0, 0);
  }

  void register()
  {
    super.register();
    this.display.addControl(this.hwndText, this);
    this.display.addControl(this.hwndUpDown, this);
  }

  void releaseHandle()
  {
    super.releaseHandle();
    this.hwndText = (this.hwndUpDown = 0);
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

  void removeVerifyListener(VerifyListener paramVerifyListener)
  {
    checkWidget();
    if (paramVerifyListener == null)
      error(4);
    if (this.eventTable == null)
      return;
    this.eventTable.unhook(25, paramVerifyListener);
  }

  boolean sendKeyEvent(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Event paramEvent)
  {
    if (!super.sendKeyEvent(paramInt1, paramInt2, paramInt3, paramInt4, paramEvent))
      return false;
    if ((this.style & 0x8) != 0)
      return true;
    if (paramInt1 != 1)
      return true;
    if ((paramInt2 != 258) && (paramInt2 != 256) && (paramInt2 != 646))
      return true;
    if (paramEvent.character == 0)
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
    if (OS.GetKeyState(1) < 0)
      return true;
    String str1 = "";
    int[] arrayOfInt1 = new int[1];
    int[] arrayOfInt2 = new int[1];
    OS.SendMessage(this.hwndText, 176, arrayOfInt1, arrayOfInt2);
    switch (i)
    {
    case 8:
      if (arrayOfInt1[0] == arrayOfInt2[0])
      {
        if (arrayOfInt1[0] == 0)
          return true;
        arrayOfInt1[0] -= 1;
        if ((!OS.IsUnicode) && (OS.IsDBLocale))
        {
          int[] arrayOfInt3 = new int[1];
          localObject = new int[1];
          OS.SendMessage(this.hwndText, 177, arrayOfInt1[0], arrayOfInt2[0]);
          OS.SendMessage(this.hwndText, 176, arrayOfInt3, (int[])localObject);
          if (arrayOfInt1[0] != arrayOfInt3[0])
            arrayOfInt1[0] -= 1;
        }
        arrayOfInt1[0] = Math.max(arrayOfInt1[0], 0);
      }
      break;
    case 127:
      if (arrayOfInt1[0] == arrayOfInt2[0])
      {
        int k = OS.GetWindowTextLength(this.hwndText);
        if (arrayOfInt1[0] == k)
          return true;
        arrayOfInt2[0] += 1;
        if ((!OS.IsUnicode) && (OS.IsDBLocale))
        {
          localObject = new int[1];
          int[] arrayOfInt4 = new int[1];
          OS.SendMessage(this.hwndText, 177, arrayOfInt1[0], arrayOfInt2[0]);
          OS.SendMessage(this.hwndText, 176, (int[])localObject, arrayOfInt4);
          if (arrayOfInt2[0] != arrayOfInt4[0])
            arrayOfInt2[0] += 1;
        }
        arrayOfInt2[0] = Math.min(arrayOfInt2[0], k);
      }
      break;
    case 13:
      return true;
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
    Object localObject = new TCHAR(getCodePage(), str2, true);
    OS.SendMessage(this.hwndText, 177, arrayOfInt1[0], arrayOfInt2[0]);
    OS.SendMessage(this.hwndText, 194, 0, (TCHAR)localObject);
    return false;
  }

  void setBackgroundImage(int paramInt)
  {
    super.setBackgroundImage(paramInt);
    OS.InvalidateRect(this.hwndText, null, true);
  }

  void setBackgroundPixel(int paramInt)
  {
    super.setBackgroundPixel(paramInt);
    OS.InvalidateRect(this.hwndText, null, true);
  }

  public void setDigits(int paramInt)
  {
    checkWidget();
    if (paramInt < 0)
      error(5);
    if (paramInt == this.digits)
      return;
    this.digits = paramInt;
    int i;
    if (OS.IsWinCE)
      i = OS.LOWORD(OS.SendMessage(this.hwndUpDown, 1128, 0, 0));
    else
      i = OS.SendMessage(this.hwndUpDown, 1138, 0, 0);
    setSelection(i, false, true, false);
  }

  void setForegroundPixel(int paramInt)
  {
    super.setForegroundPixel(paramInt);
    OS.InvalidateRect(this.hwndText, null, true);
  }

  public void setIncrement(int paramInt)
  {
    checkWidget();
    if (paramInt < 1)
      return;
    int i = OS.GetProcessHeap();
    int j = OS.SendMessage(this.hwndUpDown, 1132, 0, null);
    int k = OS.HeapAlloc(i, 8, UDACCEL.sizeof * j);
    OS.SendMessage(this.hwndUpDown, 1132, j, k);
    int m = -1;
    UDACCEL localUDACCEL = new UDACCEL();
    for (int n = 0; n < j; n++)
    {
      int i1 = k + n * UDACCEL.sizeof;
      OS.MoveMemory(localUDACCEL, i1, UDACCEL.sizeof);
      if (m == -1)
        m = localUDACCEL.nInc;
      localUDACCEL.nInc = (localUDACCEL.nInc / m * paramInt);
      OS.MoveMemory(i1, localUDACCEL, UDACCEL.sizeof);
    }
    OS.SendMessage(this.hwndUpDown, 1131, j, k);
    OS.HeapFree(i, 0, k);
  }

  public void setMaximum(int paramInt)
  {
    checkWidget();
    int[] arrayOfInt = new int[1];
    OS.SendMessage(this.hwndUpDown, 1136, arrayOfInt, null);
    if (paramInt < arrayOfInt[0])
      return;
    int i;
    if (OS.IsWinCE)
      i = OS.LOWORD(OS.SendMessage(this.hwndUpDown, 1128, 0, 0));
    else
      i = OS.SendMessage(this.hwndUpDown, 1138, 0, 0);
    OS.SendMessage(this.hwndUpDown, 1135, arrayOfInt[0], paramInt);
    if (i > paramInt)
      setSelection(paramInt, true, true, false);
  }

  public void setMinimum(int paramInt)
  {
    checkWidget();
    int[] arrayOfInt = new int[1];
    OS.SendMessage(this.hwndUpDown, 1136, null, arrayOfInt);
    if (paramInt > arrayOfInt[0])
      return;
    int i;
    if (OS.IsWinCE)
      i = OS.LOWORD(OS.SendMessage(this.hwndUpDown, 1128, 0, 0));
    else
      i = OS.SendMessage(this.hwndUpDown, 1138, 0, 0);
    OS.SendMessage(this.hwndUpDown, 1135, paramInt, arrayOfInt[0]);
    if (i < paramInt)
      setSelection(paramInt, true, true, false);
  }

  public void setPageIncrement(int paramInt)
  {
    checkWidget();
    if (paramInt < 1)
      return;
    this.pageIncrement = paramInt;
  }

  public void setSelection(int paramInt)
  {
    checkWidget();
    int[] arrayOfInt1 = new int[1];
    int[] arrayOfInt2 = new int[1];
    OS.SendMessage(this.hwndUpDown, 1136, arrayOfInt2, arrayOfInt1);
    paramInt = Math.min(Math.max(arrayOfInt2[0], paramInt), arrayOfInt1[0]);
    setSelection(paramInt, true, true, false);
  }

  void setSelection(int paramInt, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    if (paramBoolean1)
      OS.SendMessage(this.hwndUpDown, OS.IsWinCE ? 1127 : 1137, 0, paramInt);
    if (paramBoolean2)
    {
      String str1;
      if (this.digits == 0)
      {
        str1 = String.valueOf(paramInt);
      }
      else
      {
        str1 = String.valueOf(Math.abs(paramInt));
        String str2 = getDecimalSeparator();
        int j = str1.length() - this.digits;
        StringBuffer localStringBuffer = new StringBuffer();
        if (paramInt < 0)
          localStringBuffer.append("-");
        if (j > 0)
        {
          localStringBuffer.append(str1.substring(0, j));
          localStringBuffer.append(str2);
          localStringBuffer.append(str1.substring(j));
        }
        else
        {
          localStringBuffer.append("0");
          localStringBuffer.append(str2);
          while (j++ < 0)
            localStringBuffer.append("0");
          localStringBuffer.append(str1);
        }
        str1 = localStringBuffer.toString();
      }
      if ((hooks(25)) || (filters(25)))
      {
        int i = OS.GetWindowTextLength(this.hwndText);
        str1 = verifyText(str1, 0, i, null);
        if (str1 == null)
          return;
      }
      TCHAR localTCHAR = new TCHAR(getCodePage(), str1, true);
      OS.SetWindowText(this.hwndText, localTCHAR);
      OS.SendMessage(this.hwndText, 177, 0, -1);
      if (!OS.IsWinCE)
        OS.NotifyWinEvent(32773, this.hwndText, -4, 0);
    }
    if (paramBoolean3)
      sendSelectionEvent(13);
  }

  public void setTextLimit(int paramInt)
  {
    checkWidget();
    if (paramInt == 0)
      error(7);
    OS.SendMessage(this.hwndText, 197, paramInt, 0);
  }

  void setToolTipText(Shell paramShell, String paramString)
  {
    paramShell.setToolTipText(this.hwndText, paramString);
    paramShell.setToolTipText(this.hwndUpDown, paramString);
  }

  public void setValues(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    checkWidget();
    if (paramInt3 < paramInt2)
      return;
    if (paramInt4 < 0)
      return;
    if (paramInt5 < 1)
      return;
    if (paramInt6 < 1)
      return;
    paramInt1 = Math.min(Math.max(paramInt2, paramInt1), paramInt3);
    setIncrement(paramInt5);
    this.pageIncrement = paramInt6;
    this.digits = paramInt4;
    OS.SendMessage(this.hwndUpDown, 1135, paramInt2, paramInt3);
    setSelection(paramInt1, true, true, false);
  }

  void subclass()
  {
    super.subclass();
    int i = this.display.windowProc;
    OS.SetWindowLongPtr(this.hwndText, -4, i);
    OS.SetWindowLongPtr(this.hwndUpDown, -4, i);
  }

  void unsubclass()
  {
    super.unsubclass();
    OS.SetWindowLongPtr(this.hwndText, -4, EditProc);
    OS.SetWindowLongPtr(this.hwndUpDown, -4, UpDownProc);
  }

  String verifyText(String paramString, int paramInt1, int paramInt2, Event paramEvent)
  {
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
    int i = 0;
    Object localObject;
    if (this.digits > 0)
    {
      localObject = getDecimalSeparator();
      i = paramString.indexOf((String)localObject);
      if (i != -1)
        paramString = paramString.substring(0, i) + paramString.substring(i + 1);
      i = 0;
    }
    if (paramString.length() > 0)
    {
      localObject = new int[1];
      OS.SendMessage(this.hwndUpDown, 1136, (int[])localObject, null);
      if ((localObject[0] < 0) && (paramString.charAt(0) == '-'))
        i++;
    }
    while (i < paramString.length())
    {
      if (!Character.isDigit(paramString.charAt(i)))
        break;
      i++;
    }
    localEvent.doit = (i == paramString.length());
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

  int widgetExtStyle()
  {
    return super.widgetExtStyle() & 0xFFFFFDFF;
  }

  int windowProc(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((paramInt1 == this.hwndText) || (paramInt1 == this.hwndUpDown))
    {
      LRESULT localLRESULT = null;
      switch (paramInt2)
      {
      case 258:
        localLRESULT = wmChar(paramInt1, paramInt3, paramInt4);
        break;
      case 646:
        localLRESULT = wmIMEChar(paramInt1, paramInt3, paramInt4);
        break;
      case 256:
        localLRESULT = wmKeyDown(paramInt1, paramInt3, paramInt4);
        break;
      case 257:
        localLRESULT = wmKeyUp(paramInt1, paramInt3, paramInt4);
        break;
      case 262:
        localLRESULT = wmSysChar(paramInt1, paramInt3, paramInt4);
        break;
      case 260:
        localLRESULT = wmSysKeyDown(paramInt1, paramInt3, paramInt4);
        break;
      case 261:
        localLRESULT = wmSysKeyUp(paramInt1, paramInt3, paramInt4);
        break;
      case 533:
        localLRESULT = wmCaptureChanged(paramInt1, paramInt3, paramInt4);
        break;
      case 515:
        localLRESULT = wmLButtonDblClk(paramInt1, paramInt3, paramInt4);
        break;
      case 513:
        localLRESULT = wmLButtonDown(paramInt1, paramInt3, paramInt4);
        break;
      case 514:
        localLRESULT = wmLButtonUp(paramInt1, paramInt3, paramInt4);
        break;
      case 521:
        localLRESULT = wmMButtonDblClk(paramInt1, paramInt3, paramInt4);
        break;
      case 519:
        localLRESULT = wmMButtonDown(paramInt1, paramInt3, paramInt4);
        break;
      case 520:
        localLRESULT = wmMButtonUp(paramInt1, paramInt3, paramInt4);
        break;
      case 673:
        localLRESULT = wmMouseHover(paramInt1, paramInt3, paramInt4);
        break;
      case 675:
        localLRESULT = wmMouseLeave(paramInt1, paramInt3, paramInt4);
        break;
      case 512:
        localLRESULT = wmMouseMove(paramInt1, paramInt3, paramInt4);
        break;
      case 518:
        localLRESULT = wmRButtonDblClk(paramInt1, paramInt3, paramInt4);
        break;
      case 516:
        localLRESULT = wmRButtonDown(paramInt1, paramInt3, paramInt4);
        break;
      case 517:
        localLRESULT = wmRButtonUp(paramInt1, paramInt3, paramInt4);
        break;
      case 525:
        localLRESULT = wmXButtonDblClk(paramInt1, paramInt3, paramInt4);
        break;
      case 523:
        localLRESULT = wmXButtonDown(paramInt1, paramInt3, paramInt4);
        break;
      case 524:
        localLRESULT = wmXButtonUp(paramInt1, paramInt3, paramInt4);
        break;
      case 7:
        localLRESULT = wmSetFocus(paramInt1, paramInt3, paramInt4);
        break;
      case 8:
        localLRESULT = wmKillFocus(paramInt1, paramInt3, paramInt4);
        break;
      case 15:
        localLRESULT = wmPaint(paramInt1, paramInt3, paramInt4);
        break;
      case 791:
        localLRESULT = wmPrint(paramInt1, paramInt3, paramInt4);
        break;
      case 123:
        localLRESULT = wmContextMenu(paramInt1, paramInt3, paramInt4);
        break;
      case 199:
      case 768:
      case 770:
      case 771:
      case 772:
        if (paramInt1 == this.hwndText)
          localLRESULT = wmClipboard(paramInt1, paramInt2, paramInt3, paramInt4);
        break;
      }
      if (localLRESULT != null)
        return localLRESULT.value;
      return callWindowProc(paramInt1, paramInt2, paramInt3, paramInt4);
    }
    return super.windowProc(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  LRESULT WM_ERASEBKGND(int paramInt1, int paramInt2)
  {
    super.WM_ERASEBKGND(paramInt1, paramInt2);
    drawBackground(paramInt1);
    return LRESULT.ONE;
  }

  LRESULT WM_KILLFOCUS(int paramInt1, int paramInt2)
  {
    return null;
  }

  LRESULT WM_SETFOCUS(int paramInt1, int paramInt2)
  {
    OS.SetFocus(this.hwndText);
    OS.SendMessage(this.hwndText, 177, 0, -1);
    return null;
  }

  LRESULT WM_SETFONT(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_SETFONT(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    OS.SendMessage(this.hwndText, 48, paramInt1, paramInt2);
    return localLRESULT;
  }

  LRESULT WM_SIZE(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_SIZE(paramInt1, paramInt2);
    if (isDisposed())
      return localLRESULT;
    int i = OS.LOWORD(paramInt2);
    int j = OS.HIWORD(paramInt2);
    int k = OS.GetSystemMetrics(2);
    int m = i - k;
    int n = OS.GetSystemMetrics(45);
    int i1 = 52;
    SetWindowPos(this.hwndText, 0, 0, 0, m + n, j, i1);
    SetWindowPos(this.hwndUpDown, 0, m, 0, k, j, i1);
    return localLRESULT;
  }

  LRESULT wmIMEChar(int paramInt1, int paramInt2, int paramInt3)
  {
    Display localDisplay = this.display;
    localDisplay.lastKey = 0;
    localDisplay.lastAscii = paramInt2;
    localDisplay.lastVirtual = (localDisplay.lastNull = localDisplay.lastDead = 0);
    if (!sendKeyEvent(1, 646, paramInt2, paramInt3))
      return LRESULT.ZERO;
    this.ignoreCharacter = true;
    int i = callWindowProc(paramInt1, 646, paramInt2, paramInt3);
    MSG localMSG = new MSG();
    int j = 10420227;
    while (OS.PeekMessage(localMSG, paramInt1, 258, 258, j))
    {
      OS.TranslateMessage(localMSG);
      OS.DispatchMessage(localMSG);
    }
    this.ignoreCharacter = false;
    sendKeyEvent(2, 646, paramInt2, paramInt3);
    localDisplay.lastKey = (localDisplay.lastAscii = 0);
    return new LRESULT(i);
  }

  LRESULT wmChar(int paramInt1, int paramInt2, int paramInt3)
  {
    if (this.ignoreCharacter)
      return null;
    LRESULT localLRESULT = super.wmChar(paramInt1, paramInt2, paramInt3);
    if (localLRESULT != null)
      return localLRESULT;
    switch (paramInt2)
    {
    case 13:
      sendSelectionEvent(14);
    case 9:
    case 27:
      return LRESULT.ZERO;
    }
    return localLRESULT;
  }

  LRESULT wmClipboard(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((this.style & 0x8) != 0)
      return null;
    int i = 0;
    int[] arrayOfInt1 = new int[1];
    int[] arrayOfInt2 = new int[1];
    String str1 = null;
    Object localObject;
    switch (paramInt2)
    {
    case 768:
    case 771:
      OS.SendMessage(paramInt1, 176, arrayOfInt1, arrayOfInt2);
      if (arrayOfInt1[0] != arrayOfInt2[0])
      {
        str1 = "";
        i = 1;
      }
      break;
    case 770:
      OS.SendMessage(paramInt1, 176, arrayOfInt1, arrayOfInt2);
      str1 = getClipboardText();
      break;
    case 199:
    case 772:
      if (OS.SendMessage(paramInt1, 198, 0, 0) != 0)
      {
        this.ignoreModify = true;
        OS.CallWindowProc(EditProc, paramInt1, paramInt2, paramInt3, paramInt4);
        int j = OS.GetWindowTextLength(paramInt1);
        localObject = new int[1];
        int[] arrayOfInt3 = new int[1];
        OS.SendMessage(paramInt1, 176, (int[])localObject, arrayOfInt3);
        if ((j != 0) && (localObject[0] != arrayOfInt3[0]))
        {
          TCHAR localTCHAR = new TCHAR(getCodePage(), j + 1);
          OS.GetWindowText(paramInt1, localTCHAR, j + 1);
          str1 = localTCHAR.toString(localObject[0], arrayOfInt3[0] - localObject[0]);
        }
        else
        {
          str1 = "";
        }
        OS.CallWindowProc(EditProc, paramInt1, paramInt2, paramInt3, paramInt4);
        OS.SendMessage(paramInt1, 176, arrayOfInt1, arrayOfInt2);
        this.ignoreModify = false;
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
          OS.CallWindowProc(EditProc, paramInt1, paramInt2, paramInt3, paramInt4);
        localObject = new TCHAR(getCodePage(), str1, true);
        if (paramInt2 == 12)
        {
          int k = OS.GetProcessHeap();
          int m = ((TCHAR)localObject).length() * TCHAR.sizeof;
          int n = OS.HeapAlloc(k, 8, m);
          OS.MoveMemory(n, (TCHAR)localObject, m);
          int i1 = OS.CallWindowProc(EditProc, paramInt1, paramInt2, paramInt3, n);
          OS.HeapFree(k, 0, n);
          return new LRESULT(i1);
        }
        OS.SendMessage(paramInt1, 194, 0, (TCHAR)localObject);
        return LRESULT.ZERO;
      }
    }
    return null;
  }

  LRESULT wmCommandChild(int paramInt1, int paramInt2)
  {
    int i = OS.HIWORD(paramInt1);
    switch (i)
    {
    case 768:
      if (!this.ignoreModify)
      {
        boolean[] arrayOfBoolean = new boolean[1];
        int j = getSelectionText(arrayOfBoolean);
        if (arrayOfBoolean[0] == 0)
        {
          int k;
          if (OS.IsWinCE)
            k = OS.LOWORD(OS.SendMessage(this.hwndUpDown, 1128, 0, 0));
          else
            k = OS.SendMessage(this.hwndUpDown, 1138, 0, 0);
          if (k != j)
            setSelection(j, true, false, true);
        }
        sendEvent(24);
        if (isDisposed())
          return LRESULT.ZERO;
      }
      break;
    }
    return super.wmCommandChild(paramInt1, paramInt2);
  }

  LRESULT wmKeyDown(int paramInt1, int paramInt2, int paramInt3)
  {
    if (this.ignoreCharacter)
      return null;
    LRESULT localLRESULT = super.wmKeyDown(paramInt1, paramInt2, paramInt3);
    if (localLRESULT != null)
      return localLRESULT;
    UDACCEL localUDACCEL = new UDACCEL();
    OS.SendMessage(this.hwndUpDown, 1132, 1, localUDACCEL);
    int i = 0;
    switch (paramInt2)
    {
    case 38:
      i = localUDACCEL.nInc;
      break;
    case 40:
      i = -localUDACCEL.nInc;
      break;
    case 33:
      i = this.pageIncrement;
      break;
    case 34:
      i = -this.pageIncrement;
    case 35:
    case 36:
    case 37:
    case 39:
    }
    if (i != 0)
    {
      boolean[] arrayOfBoolean = new boolean[1];
      int j = getSelectionText(arrayOfBoolean);
      if (arrayOfBoolean[0] != 0)
        if (OS.IsWinCE)
          j = OS.LOWORD(OS.SendMessage(this.hwndUpDown, 1128, 0, 0));
        else
          j = OS.SendMessage(this.hwndUpDown, 1138, 0, 0);
      int k = j + i;
      int[] arrayOfInt1 = new int[1];
      int[] arrayOfInt2 = new int[1];
      OS.SendMessage(this.hwndUpDown, 1136, arrayOfInt2, arrayOfInt1);
      if ((this.style & 0x40) != 0)
      {
        if (k < arrayOfInt2[0])
          k = arrayOfInt1[0];
        if (k > arrayOfInt1[0])
          k = arrayOfInt2[0];
      }
      k = Math.min(Math.max(arrayOfInt2[0], k), arrayOfInt1[0]);
      if (j != k)
        setSelection(k, true, true, true);
    }
    switch (paramInt2)
    {
    case 38:
    case 40:
      return LRESULT.ZERO;
    case 39:
    }
    return localLRESULT;
  }

  LRESULT wmKillFocus(int paramInt1, int paramInt2, int paramInt3)
  {
    boolean[] arrayOfBoolean = new boolean[1];
    int i = getSelectionText(arrayOfBoolean);
    if (arrayOfBoolean[0] != 0)
    {
      if (OS.IsWinCE)
        i = OS.LOWORD(OS.SendMessage(this.hwndUpDown, 1128, 0, 0));
      else
        i = OS.SendMessage(this.hwndUpDown, 1138, 0, 0);
      setSelection(i, false, true, false);
    }
    return super.wmKillFocus(paramInt1, paramInt2, paramInt3);
  }

  LRESULT wmNotifyChild(NMHDR paramNMHDR, int paramInt1, int paramInt2)
  {
    switch (paramNMHDR.code)
    {
    case -722:
      NMUPDOWN localNMUPDOWN = new NMUPDOWN();
      OS.MoveMemory(localNMUPDOWN, paramInt2, NMUPDOWN.sizeof);
      int i = localNMUPDOWN.iPos + localNMUPDOWN.iDelta;
      int[] arrayOfInt1 = new int[1];
      int[] arrayOfInt2 = new int[1];
      OS.SendMessage(this.hwndUpDown, 1136, arrayOfInt2, arrayOfInt1);
      if ((this.style & 0x40) != 0)
      {
        if (i < arrayOfInt2[0])
          i = arrayOfInt1[0];
        if (i > arrayOfInt1[0])
          i = arrayOfInt2[0];
      }
      i = Math.min(Math.max(arrayOfInt2[0], i), arrayOfInt1[0]);
      if (i != localNMUPDOWN.iPos)
        setSelection(i, true, true, true);
      return LRESULT.ONE;
    }
    return super.wmNotifyChild(paramNMHDR, paramInt1, paramInt2);
  }

  LRESULT wmScrollChild(int paramInt1, int paramInt2)
  {
    int i = OS.LOWORD(paramInt1);
    switch (i)
    {
    case 4:
      sendSelectionEvent(13);
    }
    return super.wmScrollChild(paramInt1, paramInt2);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.Spinner
 * JD-Core Version:    0.6.2
 */