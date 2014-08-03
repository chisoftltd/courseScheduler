package org.eclipse.swt.widgets;

import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.internal.win32.INITCOMMONCONTROLSEX;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.MCHITTESTINFO;
import org.eclipse.swt.internal.win32.NMHDR;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.SYSTEMTIME;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.WNDCLASS;

public class DateTime extends Composite
{
  boolean doubleClick;
  boolean ignoreSelection;
  SYSTEMTIME lastSystemTime;
  SYSTEMTIME time = new SYSTEMTIME();
  static final int DateTimeProc;
  static final TCHAR DateTimeClass = new TCHAR(0, "SysDateTimePick32", true);
  static final int CalendarProc;
  static final TCHAR CalendarClass = new TCHAR(0, "SysMonthCal32", true);
  static final int MARGIN = 4;
  static final int MAX_DIGIT = 9;
  static final int MAX_DAY = 31;
  static final int MAX_12HOUR = 12;
  static final int MAX_24HOUR = 24;
  static final int MAX_MINUTE = 60;
  static final int MONTH_DAY_YEAR = 0;
  static final int DAY_MONTH_YEAR = 1;
  static final int YEAR_MONTH_DAY = 2;
  static final char SINGLE_QUOTE = '\'';
  static final char DAY_FORMAT_CONSTANT = 'd';
  static final char MONTH_FORMAT_CONSTANT = 'M';
  static final char YEAR_FORMAT_CONSTANT = 'y';
  static final char HOURS_FORMAT_CONSTANT = 'h';
  static final char MINUTES_FORMAT_CONSTANT = 'm';
  static final char SECONDS_FORMAT_CONSTANT = 's';
  static final char AMPM_FORMAT_CONSTANT = 't';
  static final int[] MONTH_NAMES = { 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67 };

  static
  {
    Object localObject = new INITCOMMONCONTROLSEX();
    ((INITCOMMONCONTROLSEX)localObject).dwSize = INITCOMMONCONTROLSEX.sizeof;
    ((INITCOMMONCONTROLSEX)localObject).dwICC = 256;
    OS.InitCommonControlsEx((INITCOMMONCONTROLSEX)localObject);
    localObject = new WNDCLASS();
    OS.GetClassInfo(0, DateTimeClass, (WNDCLASS)localObject);
    DateTimeProc = ((WNDCLASS)localObject).lpfnWndProc;
    int i = OS.GetModuleHandle(null);
    int j = OS.GetProcessHeap();
    ((WNDCLASS)localObject).hInstance = i;
    localObject.style &= -16385;
    localObject.style |= 8;
    int k = DateTimeClass.length() * TCHAR.sizeof;
    int m = OS.HeapAlloc(j, 8, k);
    OS.MoveMemory(m, DateTimeClass, k);
    ((WNDCLASS)localObject).lpszClassName = m;
    OS.RegisterClass((WNDCLASS)localObject);
    OS.HeapFree(j, 0, m);
    localObject = new WNDCLASS();
    OS.GetClassInfo(0, CalendarClass, (WNDCLASS)localObject);
    CalendarProc = ((WNDCLASS)localObject).lpfnWndProc;
    i = OS.GetModuleHandle(null);
    j = OS.GetProcessHeap();
    ((WNDCLASS)localObject).hInstance = i;
    localObject.style &= -16385;
    localObject.style |= 8;
    k = CalendarClass.length() * TCHAR.sizeof;
    m = OS.HeapAlloc(j, 8, k);
    OS.MoveMemory(m, CalendarClass, k);
    ((WNDCLASS)localObject).lpszClassName = m;
    OS.RegisterClass((WNDCLASS)localObject);
    OS.HeapFree(j, 0, m);
  }

  public DateTime(Composite paramComposite, int paramInt)
  {
    super(paramComposite, checkStyle(paramInt));
    if ((this.style & 0x8000) != 0)
    {
      String str = (this.style & 0x20) != 0 ? getCustomShortDateFormat() : getCustomShortTimeFormat();
      TCHAR localTCHAR = new TCHAR(0, str, true);
      OS.SendMessage(this.handle, OS.DTM_SETFORMAT, 0, localTCHAR);
    }
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
    return OS.CallWindowProc(windowProc(), paramInt1, paramInt2, paramInt3, paramInt4);
  }

  static int checkStyle(int paramInt)
  {
    paramInt &= -769;
    paramInt = checkBits(paramInt, 32, 128, 1024, 0, 0, 0);
    paramInt = checkBits(paramInt, 65536, 32768, 268435456, 0, 0, 0);
    if ((paramInt & 0x20) == 0)
      paramInt &= -5;
    return paramInt;
  }

  protected void checkSubclass()
  {
    if (!isValidSubclass())
      error(43);
  }

  public Point computeSize(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    checkWidget();
    int i = 0;
    int j = 0;
    if ((paramInt1 == -1) || (paramInt2 == -1))
    {
      Object localObject;
      if ((this.style & 0x400) != 0)
      {
        localObject = new RECT();
        OS.SendMessage(this.handle, 4105, 0, (RECT)localObject);
        i = ((RECT)localObject).right;
        j = ((RECT)localObject).bottom;
      }
      else
      {
        localObject = new TCHAR(getCodePage(), 128);
        int n = 0;
        int i1 = OS.GetDC(this.handle);
        int m = OS.SendMessage(this.handle, 49, 0, 0);
        if (m != 0)
          n = OS.SelectObject(i1, m);
        RECT localRECT = new RECT();
        int i2 = 11264;
        SYSTEMTIME localSYSTEMTIME = new SYSTEMTIME();
        int i5;
        int i6;
        int i7;
        if ((this.style & 0x20) != 0)
        {
          localSYSTEMTIME.wMonth = 1;
          localSYSTEMTIME.wDay = 1;
          i3 = 0;
          i4 = 0;
          i5 = 0;
          for (i6 = 0; i6 <= 9; i6++)
          {
            localSYSTEMTIME.wYear = ((short)(2000 + i6));
            i7 = OS.GetDateFormat(1024, 1, localSYSTEMTIME, null, (TCHAR)localObject, ((TCHAR)localObject).length());
            if (i7 == 0)
            {
              localObject = new TCHAR(getCodePage(), i7);
              OS.GetDateFormat(1024, 1, localSYSTEMTIME, null, (TCHAR)localObject, ((TCHAR)localObject).length());
            }
            localRECT.left = (localRECT.top = localRECT.right = localRECT.bottom = 0);
            OS.DrawText(i1, (TCHAR)localObject, i7, localRECT, i2);
            if (localRECT.right - localRECT.left >= i)
            {
              i = localRECT.right - localRECT.left;
              i5 = i4;
              i4 = i3;
              i3 = i6;
            }
            j = Math.max(j, localRECT.bottom - localRECT.top);
          }
          if (i3 > 1)
            i3 = i3 * 1000 + i3 * 100 + i3 * 10 + i3;
          else if (i4 > 1)
            i3 = i4 * 1000 + i3 * 100 + i3 * 10 + i3;
          else
            i3 = i5 * 1000 + i3 * 100 + i3 * 10 + i3;
          localSYSTEMTIME.wYear = ((short)i3);
          i = i3 = 0;
          int i8;
          for (i6 = 0; i6 < MONTH_NAMES.length; i6 = (short)(i6 + 1))
          {
            i7 = MONTH_NAMES[i6];
            i8 = OS.GetLocaleInfo(1024, i7, (TCHAR)localObject, ((TCHAR)localObject).length());
            if (i8 == 0)
            {
              localObject = new TCHAR(getCodePage(), i8);
              OS.GetLocaleInfo(1024, i7, (TCHAR)localObject, ((TCHAR)localObject).length());
            }
            localRECT.left = (localRECT.top = localRECT.right = localRECT.bottom = 0);
            OS.DrawText(i1, (TCHAR)localObject, i8, localRECT, i2);
            if (localRECT.right - localRECT.left > i)
            {
              i = localRECT.right - localRECT.left;
              i3 = i6;
            }
            j = Math.max(j, localRECT.bottom - localRECT.top);
          }
          localSYSTEMTIME.wMonth = ((short)(i3 + 1));
          i6 = (this.style & 0x8000) != 0 ? 8 : (this.style & 0x10000) != 0 ? 1 : 2;
          i = 0;
          for (i7 = 1; i7 <= 31; i7 = (short)(i7 + 1))
          {
            localSYSTEMTIME.wDay = i7;
            i8 = OS.GetDateFormat(1024, i6, localSYSTEMTIME, null, (TCHAR)localObject, ((TCHAR)localObject).length());
            if (i8 == 0)
            {
              localObject = new TCHAR(getCodePage(), i8);
              OS.GetDateFormat(1024, i6, localSYSTEMTIME, null, (TCHAR)localObject, ((TCHAR)localObject).length());
            }
            localRECT.left = (localRECT.top = localRECT.right = localRECT.bottom = 0);
            OS.DrawText(i1, (TCHAR)localObject, i8, localRECT, i2);
            i = Math.max(i, localRECT.right - localRECT.left);
            j = Math.max(j, localRECT.bottom - localRECT.top);
            if ((this.style & 0x8000) != 0)
              break;
          }
        }
        else if ((this.style & 0x80) != 0)
        {
          i3 = (this.style & 0x8000) != 0 ? 2 : 0;
          i4 = 0;
          i5 = is24HourTime() ? 24 : 12;
          for (i6 = 0; i6 < i5; i6 = (short)(i6 + 1))
          {
            localSYSTEMTIME.wHour = i6;
            i7 = OS.GetTimeFormat(1024, i3, localSYSTEMTIME, null, (TCHAR)localObject, ((TCHAR)localObject).length());
            if (i7 == 0)
            {
              localObject = new TCHAR(getCodePage(), i7);
              OS.GetTimeFormat(1024, i3, localSYSTEMTIME, null, (TCHAR)localObject, ((TCHAR)localObject).length());
            }
            localRECT.left = (localRECT.top = localRECT.right = localRECT.bottom = 0);
            OS.DrawText(i1, (TCHAR)localObject, i7, localRECT, i2);
            if (localRECT.right - localRECT.left > i)
            {
              i = localRECT.right - localRECT.left;
              i4 = i6;
            }
            j = Math.max(j, localRECT.bottom - localRECT.top);
          }
          localSYSTEMTIME.wHour = i4;
          i = i4 = 0;
          for (i6 = 0; i6 < 60; i6 = (short)(i6 + 1))
          {
            localSYSTEMTIME.wMinute = i6;
            i7 = OS.GetTimeFormat(1024, i3, localSYSTEMTIME, null, (TCHAR)localObject, ((TCHAR)localObject).length());
            if (i7 == 0)
            {
              localObject = new TCHAR(getCodePage(), i7);
              OS.GetTimeFormat(1024, i3, localSYSTEMTIME, null, (TCHAR)localObject, ((TCHAR)localObject).length());
            }
            localRECT.left = (localRECT.top = localRECT.right = localRECT.bottom = 0);
            OS.DrawText(i1, (TCHAR)localObject, i7, localRECT, i2);
            if (localRECT.right - localRECT.left > i)
            {
              i = localRECT.right - localRECT.left;
              i4 = i6;
            }
            j = Math.max(j, localRECT.bottom - localRECT.top);
          }
          localSYSTEMTIME.wMinute = i4;
          localSYSTEMTIME.wSecond = i4;
          i6 = OS.GetTimeFormat(1024, i3, localSYSTEMTIME, null, (TCHAR)localObject, ((TCHAR)localObject).length());
          if (i6 == 0)
          {
            localObject = new TCHAR(getCodePage(), i6);
            OS.GetTimeFormat(1024, i3, localSYSTEMTIME, null, (TCHAR)localObject, ((TCHAR)localObject).length());
          }
          localRECT.left = (localRECT.top = localRECT.right = localRECT.bottom = 0);
          OS.DrawText(i1, (TCHAR)localObject, i6, localRECT, i2);
          i = localRECT.right - localRECT.left;
          j = Math.max(j, localRECT.bottom - localRECT.top);
        }
        if (m != 0)
          OS.SelectObject(i1, n);
        OS.ReleaseDC(this.handle, i1);
        int i3 = OS.GetSystemMetrics(2);
        int i4 = OS.GetSystemMetrics(20);
        if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(6, 0)))
        {
          i4 += 7;
          if ((this.style & 0x4) != 0)
            i3 += 16;
        }
        i += i3 + 4;
        j = Math.max(j, i4);
      }
    }
    if (i == 0)
      i = 64;
    if (j == 0)
      j = 64;
    if (paramInt1 != -1)
      i = paramInt1;
    if (paramInt2 != -1)
      j = paramInt2;
    int k = getBorderWidth();
    i += k * 2;
    j += k * 2;
    return new Point(i, j);
  }

  void createHandle()
  {
    super.createHandle();
    this.state &= -259;
    if ((this.style & 0x800) == 0)
    {
      int i = OS.GetWindowLong(this.handle, -20);
      i &= -131585;
      OS.SetWindowLong(this.handle, -20, i);
    }
  }

  int defaultBackground()
  {
    return OS.GetSysColor(OS.COLOR_WINDOW);
  }

  String getComputeSizeString()
  {
    if ((this.style & 0x20) != 0)
    {
      if ((this.style & 0x8000) != 0)
        return getCustomShortDateFormat();
      if ((this.style & 0x10000) != 0)
        return getShortDateFormat();
      if ((this.style & 0x10000000) != 0)
        return getLongDateFormat();
    }
    if ((this.style & 0x80) != 0)
    {
      if ((this.style & 0x8000) != 0)
        return getCustomShortTimeFormat();
      return getTimeFormat();
    }
    return "";
  }

  String getCustomShortDateFormat()
  {
    TCHAR localTCHAR = new TCHAR(getCodePage(), 80);
    int i = OS.GetLocaleInfo(1024, 4102, localTCHAR, 80);
    return i != 0 ? localTCHAR.toString(0, i - 1) : "M/yyyy";
  }

  String getCustomShortTimeFormat()
  {
    StringBuffer localStringBuffer = new StringBuffer(getTimeFormat());
    int i = localStringBuffer.length();
    int j = 0;
    int k = 0;
    int m = 0;
    while (k < i)
    {
      int n = localStringBuffer.charAt(k);
      if (n == 39)
      {
        j = j != 0 ? 0 : 1;
      }
      else if ((n == 115) && (j == 0))
      {
        m = k + 1;
        do
        {
          m++;
          if (m >= i)
            break;
        }
        while (localStringBuffer.charAt(m) == 's');
        while ((k > 0) && (localStringBuffer.charAt(k) != 'm'))
          k--;
        k++;
        break;
      }
      k++;
    }
    if (k < m)
      localStringBuffer.delete(k, m);
    return localStringBuffer.toString();
  }

  String getLongDateFormat()
  {
    TCHAR localTCHAR = new TCHAR(getCodePage(), 80);
    int i = OS.GetLocaleInfo(1024, 32, localTCHAR, 80);
    return i > 0 ? localTCHAR.toString(0, i - 1) : "dddd, MMMM dd, yyyy";
  }

  String getShortDateFormat()
  {
    TCHAR localTCHAR = new TCHAR(getCodePage(), 80);
    int i = OS.GetLocaleInfo(1024, 31, localTCHAR, 80);
    return i > 0 ? localTCHAR.toString(0, i - 1) : "M/d/yyyy";
  }

  int getShortDateFormatOrdering()
  {
    TCHAR localTCHAR = new TCHAR(getCodePage(), 4);
    int i = OS.GetLocaleInfo(1024, 33, localTCHAR, 4);
    if (i > 0)
    {
      String str = localTCHAR.toString(0, i - 1);
      return Integer.parseInt(str);
    }
    return 0;
  }

  String getTimeFormat()
  {
    TCHAR localTCHAR = new TCHAR(getCodePage(), 80);
    int i = OS.GetLocaleInfo(1024, 4099, localTCHAR, 80);
    return i > 0 ? localTCHAR.toString(0, i - 1) : "h:mm:ss tt";
  }

  boolean is24HourTime()
  {
    TCHAR localTCHAR = new TCHAR(getCodePage(), 4);
    int i = OS.GetLocaleInfo(1024, 35, localTCHAR, 4);
    if (i > 0)
    {
      String str = localTCHAR.toString(0, i - 1);
      return Integer.parseInt(str) != 0;
    }
    return true;
  }

  public int getDay()
  {
    checkWidget();
    SYSTEMTIME localSYSTEMTIME = new SYSTEMTIME();
    int i = (this.style & 0x400) != 0 ? 4097 : 4097;
    OS.SendMessage(this.handle, i, 0, localSYSTEMTIME);
    return localSYSTEMTIME.wDay;
  }

  public int getHours()
  {
    checkWidget();
    if ((this.style & 0x400) != 0)
      return this.time.wHour;
    SYSTEMTIME localSYSTEMTIME = new SYSTEMTIME();
    int i = (this.style & 0x400) != 0 ? 4097 : 4097;
    OS.SendMessage(this.handle, i, 0, localSYSTEMTIME);
    return localSYSTEMTIME.wHour;
  }

  public int getMinutes()
  {
    checkWidget();
    if ((this.style & 0x400) != 0)
      return this.time.wMinute;
    SYSTEMTIME localSYSTEMTIME = new SYSTEMTIME();
    int i = (this.style & 0x400) != 0 ? 4097 : 4097;
    OS.SendMessage(this.handle, i, 0, localSYSTEMTIME);
    return localSYSTEMTIME.wMinute;
  }

  public int getMonth()
  {
    checkWidget();
    SYSTEMTIME localSYSTEMTIME = new SYSTEMTIME();
    int i = (this.style & 0x400) != 0 ? 4097 : 4097;
    OS.SendMessage(this.handle, i, 0, localSYSTEMTIME);
    return localSYSTEMTIME.wMonth - 1;
  }

  String getNameText()
  {
    return getMonth() + 1 + "/" + getDay() + "/" + getYear();
  }

  public int getSeconds()
  {
    checkWidget();
    if ((this.style & 0x400) != 0)
      return this.time.wSecond;
    SYSTEMTIME localSYSTEMTIME = new SYSTEMTIME();
    int i = (this.style & 0x400) != 0 ? 4097 : 4097;
    OS.SendMessage(this.handle, i, 0, localSYSTEMTIME);
    return localSYSTEMTIME.wSecond;
  }

  public int getYear()
  {
    checkWidget();
    SYSTEMTIME localSYSTEMTIME = new SYSTEMTIME();
    int i = (this.style & 0x400) != 0 ? 4097 : 4097;
    OS.SendMessage(this.handle, i, 0, localSYSTEMTIME);
    return localSYSTEMTIME.wYear;
  }

  void releaseWidget()
  {
    super.releaseWidget();
    this.lastSystemTime = null;
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

  public void setDate(int paramInt1, int paramInt2, int paramInt3)
  {
    checkWidget();
    SYSTEMTIME localSYSTEMTIME = new SYSTEMTIME();
    int i = (this.style & 0x400) != 0 ? 4097 : 4097;
    OS.SendMessage(this.handle, i, 0, localSYSTEMTIME);
    i = (this.style & 0x400) != 0 ? 4098 : 4098;
    localSYSTEMTIME.wYear = ((short)paramInt1);
    localSYSTEMTIME.wMonth = ((short)(paramInt2 + 1));
    localSYSTEMTIME.wDay = ((short)paramInt3);
    OS.SendMessage(this.handle, i, 0, localSYSTEMTIME);
    this.lastSystemTime = null;
  }

  public void setDay(int paramInt)
  {
    checkWidget();
    SYSTEMTIME localSYSTEMTIME = new SYSTEMTIME();
    int i = (this.style & 0x400) != 0 ? 4097 : 4097;
    OS.SendMessage(this.handle, i, 0, localSYSTEMTIME);
    i = (this.style & 0x400) != 0 ? 4098 : 4098;
    localSYSTEMTIME.wDay = ((short)paramInt);
    OS.SendMessage(this.handle, i, 0, localSYSTEMTIME);
    this.lastSystemTime = null;
  }

  public void setHours(int paramInt)
  {
    checkWidget();
    SYSTEMTIME localSYSTEMTIME = new SYSTEMTIME();
    int i = (this.style & 0x400) != 0 ? 4097 : 4097;
    OS.SendMessage(this.handle, i, 0, localSYSTEMTIME);
    i = (this.style & 0x400) != 0 ? 4098 : 4098;
    localSYSTEMTIME.wHour = ((short)paramInt);
    OS.SendMessage(this.handle, i, 0, localSYSTEMTIME);
    if (((this.style & 0x400) != 0) && (paramInt >= 0) && (paramInt <= 23))
      this.time.wHour = ((short)paramInt);
  }

  public void setMinutes(int paramInt)
  {
    checkWidget();
    SYSTEMTIME localSYSTEMTIME = new SYSTEMTIME();
    int i = (this.style & 0x400) != 0 ? 4097 : 4097;
    OS.SendMessage(this.handle, i, 0, localSYSTEMTIME);
    i = (this.style & 0x400) != 0 ? 4098 : 4098;
    localSYSTEMTIME.wMinute = ((short)paramInt);
    OS.SendMessage(this.handle, i, 0, localSYSTEMTIME);
    if (((this.style & 0x400) != 0) && (paramInt >= 0) && (paramInt <= 59))
      this.time.wMinute = ((short)paramInt);
  }

  public void setMonth(int paramInt)
  {
    checkWidget();
    SYSTEMTIME localSYSTEMTIME = new SYSTEMTIME();
    int i = (this.style & 0x400) != 0 ? 4097 : 4097;
    OS.SendMessage(this.handle, i, 0, localSYSTEMTIME);
    i = (this.style & 0x400) != 0 ? 4098 : 4098;
    localSYSTEMTIME.wMonth = ((short)(paramInt + 1));
    OS.SendMessage(this.handle, i, 0, localSYSTEMTIME);
    this.lastSystemTime = null;
  }

  public void setSeconds(int paramInt)
  {
    checkWidget();
    SYSTEMTIME localSYSTEMTIME = new SYSTEMTIME();
    int i = (this.style & 0x400) != 0 ? 4097 : 4097;
    OS.SendMessage(this.handle, i, 0, localSYSTEMTIME);
    i = (this.style & 0x400) != 0 ? 4098 : 4098;
    localSYSTEMTIME.wSecond = ((short)paramInt);
    OS.SendMessage(this.handle, i, 0, localSYSTEMTIME);
    if (((this.style & 0x400) != 0) && (paramInt >= 0) && (paramInt <= 59))
      this.time.wSecond = ((short)paramInt);
  }

  public void setTime(int paramInt1, int paramInt2, int paramInt3)
  {
    checkWidget();
    SYSTEMTIME localSYSTEMTIME = new SYSTEMTIME();
    int i = (this.style & 0x400) != 0 ? 4097 : 4097;
    OS.SendMessage(this.handle, i, 0, localSYSTEMTIME);
    i = (this.style & 0x400) != 0 ? 4098 : 4098;
    localSYSTEMTIME.wHour = ((short)paramInt1);
    localSYSTEMTIME.wMinute = ((short)paramInt2);
    localSYSTEMTIME.wSecond = ((short)paramInt3);
    OS.SendMessage(this.handle, i, 0, localSYSTEMTIME);
    if (((this.style & 0x400) != 0) && (paramInt1 >= 0) && (paramInt1 <= 23) && (paramInt2 >= 0) && (paramInt2 <= 59) && (paramInt3 >= 0) && (paramInt3 <= 59))
    {
      this.time.wHour = ((short)paramInt1);
      this.time.wMinute = ((short)paramInt2);
      this.time.wSecond = ((short)paramInt3);
    }
  }

  public void setYear(int paramInt)
  {
    checkWidget();
    SYSTEMTIME localSYSTEMTIME = new SYSTEMTIME();
    int i = (this.style & 0x400) != 0 ? 4097 : 4097;
    OS.SendMessage(this.handle, i, 0, localSYSTEMTIME);
    i = (this.style & 0x400) != 0 ? 4098 : 4098;
    localSYSTEMTIME.wYear = ((short)paramInt);
    OS.SendMessage(this.handle, i, 0, localSYSTEMTIME);
    this.lastSystemTime = null;
  }

  int widgetStyle()
  {
    int i = super.widgetStyle() | 0x10000;
    if ((this.style & 0x400) != 0)
      return i | 0x10;
    i &= -33554433;
    if ((this.style & 0x80) != 0)
      i |= 9;
    if ((this.style & 0x20) != 0)
    {
      i |= ((this.style & 0x10000) != 0 ? 12 : 4);
      if ((this.style & 0x4) == 0)
        i |= 1;
    }
    return i;
  }

  TCHAR windowClass()
  {
    return (this.style & 0x400) != 0 ? CalendarClass : DateTimeClass;
  }

  int windowProc()
  {
    return (this.style & 0x400) != 0 ? CalendarProc : DateTimeProc;
  }

  LRESULT wmNotifyChild(NMHDR paramNMHDR, int paramInt1, int paramInt2)
  {
    SYSTEMTIME localSYSTEMTIME;
    switch (paramNMHDR.code)
    {
    case -753:
      this.display.captureChanged = true;
      break;
    case -749:
      if (!this.ignoreSelection)
      {
        localSYSTEMTIME = new SYSTEMTIME();
        OS.SendMessage(this.handle, 4097, 0, localSYSTEMTIME);
        sendSelectionEvent(13);
      }
      break;
    case -759:
      localSYSTEMTIME = new SYSTEMTIME();
      OS.SendMessage(this.handle, 4097, 0, localSYSTEMTIME);
      if ((this.lastSystemTime == null) || (localSYSTEMTIME.wDay != this.lastSystemTime.wDay) || (localSYSTEMTIME.wMonth != this.lastSystemTime.wMonth) || (localSYSTEMTIME.wYear != this.lastSystemTime.wYear))
      {
        sendSelectionEvent(13);
        if ((this.style & 0x80) == 0)
          this.lastSystemTime = localSYSTEMTIME;
      }
      break;
    }
    return super.wmNotifyChild(paramNMHDR, paramInt1, paramInt2);
  }

  LRESULT WM_CHAR(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_CHAR(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
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

  LRESULT WM_LBUTTONDBLCLK(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_LBUTTONDBLCLK(paramInt1, paramInt2);
    if (isDisposed())
      return LRESULT.ZERO;
    if ((this.style & 0x400) != 0)
    {
      MCHITTESTINFO localMCHITTESTINFO = new MCHITTESTINFO();
      localMCHITTESTINFO.cbSize = MCHITTESTINFO.sizeof;
      POINT localPOINT = new POINT();
      localPOINT.x = OS.GET_X_LPARAM(paramInt2);
      localPOINT.y = OS.GET_Y_LPARAM(paramInt2);
      localMCHITTESTINFO.pt = localPOINT;
      int i = OS.SendMessage(this.handle, 4110, 0, localMCHITTESTINFO);
      if ((i & 0x20001) == 131073)
        this.doubleClick = true;
    }
    return localLRESULT;
  }

  LRESULT WM_LBUTTONDOWN(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_LBUTTONDOWN(paramInt1, paramInt2);
    if (localLRESULT == LRESULT.ZERO)
      return localLRESULT;
    this.doubleClick = false;
    if (((this.style & 0x400) != 0) && ((this.style & 0x80000) == 0))
      OS.SetFocus(this.handle);
    return localLRESULT;
  }

  LRESULT WM_LBUTTONUP(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_LBUTTONUP(paramInt1, paramInt2);
    if (isDisposed())
      return LRESULT.ZERO;
    if (this.doubleClick)
      sendSelectionEvent(14);
    this.doubleClick = false;
    return localLRESULT;
  }

  LRESULT WM_TIMER(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_TIMER(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    this.ignoreSelection = true;
    int i = callWindowProc(this.handle, 275, paramInt1, paramInt2);
    this.ignoreSelection = false;
    return i == 0 ? LRESULT.ZERO : new LRESULT(i);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.DateTime
 * JD-Core Version:    0.6.2
 */