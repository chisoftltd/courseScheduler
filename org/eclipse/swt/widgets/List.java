package org.eclipse.swt.widgets;

import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.SCROLLINFO;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.WNDCLASS;

public class List extends Scrollable
{
  static final int INSET = 3;
  static final int ListProc = localWNDCLASS.lpfnWndProc;
  static final TCHAR ListClass = new TCHAR(0, "LISTBOX", true);

  static
  {
    WNDCLASS localWNDCLASS = new WNDCLASS();
    OS.GetClassInfo(0, ListClass, localWNDCLASS);
  }

  public List(Composite paramComposite, int paramInt)
  {
    super(paramComposite, checkStyle(paramInt));
  }

  public void add(String paramString)
  {
    checkWidget();
    if (paramString == null)
      error(4);
    TCHAR localTCHAR = new TCHAR(getCodePage(), paramString, true);
    int i = OS.SendMessage(this.handle, 384, 0, localTCHAR);
    if (i == -1)
      error(14);
    if (i == -2)
      error(14);
    if ((this.style & 0x100) != 0)
      setScrollWidth(localTCHAR, true);
  }

  public void add(String paramString, int paramInt)
  {
    checkWidget();
    if (paramString == null)
      error(4);
    if (paramInt == -1)
      error(6);
    TCHAR localTCHAR = new TCHAR(getCodePage(), paramString, true);
    int i = OS.SendMessage(this.handle, 385, paramInt, localTCHAR);
    if (i == -2)
      error(14);
    if (i == -1)
    {
      int j = OS.SendMessage(this.handle, 395, 0, 0);
      if ((paramInt >= 0) && (paramInt <= j))
        error(14);
      else
        error(6);
    }
    if ((this.style & 0x100) != 0)
      setScrollWidth(localTCHAR, true);
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
    int i = 0;
    switch (paramInt2)
    {
    case 276:
    case 277:
      i = (findImageControl() != null) && (getDrawing()) && (OS.IsWindowVisible(this.handle)) ? 1 : 0;
      if (i != 0)
        OS.DefWindowProc(this.handle, 11, 0, 0);
      break;
    }
    int j = OS.CallWindowProc(ListProc, paramInt1, paramInt2, paramInt3, paramInt4);
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

  static int checkStyle(int paramInt)
  {
    return checkBits(paramInt, 4, 2, 0, 0, 0, 0);
  }

  public Point computeSize(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    checkWidget();
    int i = 0;
    int j = 0;
    int m;
    if (paramInt1 == -1)
      if ((this.style & 0x100) != 0)
      {
        i = OS.SendMessage(this.handle, 403, 0, 0);
        i -= 3;
      }
      else
      {
        k = OS.SendMessage(this.handle, 395, 0, 0);
        int n = 0;
        int i1 = OS.GetDC(this.handle);
        m = OS.SendMessage(this.handle, 49, 0, 0);
        if (m != 0)
          n = OS.SelectObject(i1, m);
        RECT localRECT = new RECT();
        int i2 = 3104;
        int i3 = getCodePage();
        TCHAR localTCHAR = new TCHAR(i3, 65);
        for (int i4 = 0; i4 < k; i4++)
        {
          int i5 = OS.SendMessage(this.handle, 394, i4, 0);
          if (i5 != -1)
          {
            if (i5 + 1 > localTCHAR.length())
              localTCHAR = new TCHAR(i3, i5 + 1);
            int i6 = OS.SendMessage(this.handle, 393, i4, localTCHAR);
            if (i6 != -1)
            {
              OS.DrawText(i1, localTCHAR, i5, localRECT, i2);
              i = Math.max(i, localRECT.right - localRECT.left);
            }
          }
        }
        if (m != 0)
          OS.SelectObject(i1, n);
        OS.ReleaseDC(this.handle, i1);
      }
    if (paramInt2 == -1)
    {
      k = OS.SendMessage(this.handle, 395, 0, 0);
      m = OS.SendMessage(this.handle, 417, 0, 0);
      j = k * m;
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
    i += k * 2 + 3;
    j += k * 2;
    if ((this.style & 0x200) != 0)
      i += OS.GetSystemMetrics(2);
    if ((this.style & 0x100) != 0)
      j += OS.GetSystemMetrics(3);
    return new Point(i, j);
  }

  int defaultBackground()
  {
    return OS.GetSysColor(OS.COLOR_WINDOW);
  }

  public void deselect(int[] paramArrayOfInt)
  {
    checkWidget();
    if (paramArrayOfInt == null)
      error(4);
    if (paramArrayOfInt.length == 0)
      return;
    int j;
    if ((this.style & 0x4) != 0)
    {
      i = OS.SendMessage(this.handle, 392, 0, 0);
      if (i == -1)
        return;
      for (j = 0; j < paramArrayOfInt.length; j++)
        if (i == paramArrayOfInt[j])
        {
          OS.SendMessage(this.handle, 390, -1, 0);
          return;
        }
      return;
    }
    for (int i = 0; i < paramArrayOfInt.length; i++)
    {
      j = paramArrayOfInt[i];
      if (j != -1)
        OS.SendMessage(this.handle, 389, 0, j);
    }
  }

  public void deselect(int paramInt)
  {
    checkWidget();
    if (paramInt == -1)
      return;
    if ((this.style & 0x4) != 0)
    {
      int i = OS.SendMessage(this.handle, 392, 0, 0);
      if (i == -1)
        return;
      if (i == paramInt)
        OS.SendMessage(this.handle, 390, -1, 0);
      return;
    }
    OS.SendMessage(this.handle, 389, 0, paramInt);
  }

  public void deselect(int paramInt1, int paramInt2)
  {
    checkWidget();
    if (paramInt1 > paramInt2)
      return;
    if ((this.style & 0x4) != 0)
    {
      i = OS.SendMessage(this.handle, 392, 0, 0);
      if (i == -1)
        return;
      if ((paramInt1 <= i) && (i <= paramInt2))
        OS.SendMessage(this.handle, 390, -1, 0);
      return;
    }
    int i = OS.SendMessage(this.handle, 395, 0, 0);
    if ((paramInt1 < 0) && (paramInt2 < 0))
      return;
    if ((paramInt1 >= i) && (paramInt2 >= i))
      return;
    paramInt1 = Math.min(i - 1, Math.max(0, paramInt1));
    paramInt2 = Math.min(i - 1, Math.max(0, paramInt2));
    OS.SendMessage(this.handle, 387, paramInt2, paramInt1);
  }

  public void deselectAll()
  {
    checkWidget();
    if ((this.style & 0x4) != 0)
      OS.SendMessage(this.handle, 390, -1, 0);
    else
      OS.SendMessage(this.handle, 389, 0, -1);
  }

  public int getFocusIndex()
  {
    checkWidget();
    int i = OS.SendMessage(this.handle, 415, 0, 0);
    if (i == 0)
    {
      int j = OS.SendMessage(this.handle, 395, 0, 0);
      if (j == 0)
        return -1;
    }
    return i;
  }

  public String getItem(int paramInt)
  {
    checkWidget();
    int i = OS.SendMessage(this.handle, 394, paramInt, 0);
    if (i != -1)
    {
      TCHAR localTCHAR = new TCHAR(getCodePage(), i + 1);
      int k = OS.SendMessage(this.handle, 393, paramInt, localTCHAR);
      if (k != -1)
        return localTCHAR.toString(0, i);
    }
    int j = OS.SendMessage(this.handle, 395, 0, 0);
    if ((paramInt >= 0) && (paramInt < j))
      error(8);
    error(6);
    return "";
  }

  public int getItemCount()
  {
    checkWidget();
    int i = OS.SendMessage(this.handle, 395, 0, 0);
    if (i == -1)
      error(36);
    return i;
  }

  public int getItemHeight()
  {
    checkWidget();
    int i = OS.SendMessage(this.handle, 417, 0, 0);
    if (i == -1)
      error(11);
    return i;
  }

  public String[] getItems()
  {
    checkWidget();
    int i = getItemCount();
    String[] arrayOfString = new String[i];
    for (int j = 0; j < i; j++)
      arrayOfString[j] = getItem(j);
    return arrayOfString;
  }

  public String[] getSelection()
  {
    checkWidget();
    int[] arrayOfInt = getSelectionIndices();
    String[] arrayOfString = new String[arrayOfInt.length];
    for (int i = 0; i < arrayOfInt.length; i++)
      arrayOfString[i] = getItem(arrayOfInt[i]);
    return arrayOfString;
  }

  public int getSelectionCount()
  {
    checkWidget();
    if ((this.style & 0x4) != 0)
    {
      i = OS.SendMessage(this.handle, 392, 0, 0);
      if (i == -1)
        return 0;
      return 1;
    }
    int i = OS.SendMessage(this.handle, 400, 0, 0);
    if (i == -1)
      error(36);
    return i;
  }

  public int getSelectionIndex()
  {
    checkWidget();
    if ((this.style & 0x4) != 0)
      return OS.SendMessage(this.handle, 392, 0, 0);
    int i = OS.SendMessage(this.handle, 400, 0, 0);
    if (i == -1)
      error(9);
    if (i == 0)
      return -1;
    int j = OS.SendMessage(this.handle, 415, 0, 0);
    int k = OS.SendMessage(this.handle, 391, j, 0);
    if (k == -1)
      error(9);
    if (k != 0)
      return j;
    int[] arrayOfInt = new int[1];
    k = OS.SendMessage(this.handle, 401, 1, arrayOfInt);
    if (k != 1)
      error(9);
    return arrayOfInt[0];
  }

  public int[] getSelectionIndices()
  {
    checkWidget();
    if ((this.style & 0x4) != 0)
    {
      i = OS.SendMessage(this.handle, 392, 0, 0);
      if (i == -1)
        return new int[0];
      return new int[] { i };
    }
    int i = OS.SendMessage(this.handle, 400, 0, 0);
    if (i == -1)
      error(9);
    int[] arrayOfInt = new int[i];
    int j = OS.SendMessage(this.handle, 401, i, arrayOfInt);
    if (j != i)
      error(9);
    return arrayOfInt;
  }

  public int getTopIndex()
  {
    checkWidget();
    return OS.SendMessage(this.handle, 398, 0, 0);
  }

  public int indexOf(String paramString)
  {
    return indexOf(paramString, 0);
  }

  public int indexOf(String paramString, int paramInt)
  {
    checkWidget();
    if (paramString == null)
      error(4);
    if (paramString.length() == 0)
    {
      i = getItemCount();
      for (j = paramInt; j < i; j++)
        if (paramString.equals(getItem(j)))
          return j;
      return -1;
    }
    int i = OS.SendMessage(this.handle, 395, 0, 0);
    if ((paramInt < 0) || (paramInt >= i))
      return -1;
    int j = paramInt - 1;
    TCHAR localTCHAR = new TCHAR(getCodePage(), paramString, true);
    do
    {
      int k;
      j = OS.SendMessage(this.handle, 418, k = j, localTCHAR);
      if ((j == -1) || (j <= k))
        return -1;
    }
    while (!paramString.equals(getItem(j)));
    return j;
  }

  public boolean isSelected(int paramInt)
  {
    checkWidget();
    int i = OS.SendMessage(this.handle, 391, paramInt, 0);
    return (i != 0) && (i != -1);
  }

  public void remove(int[] paramArrayOfInt)
  {
    checkWidget();
    if (paramArrayOfInt == null)
      error(4);
    if (paramArrayOfInt.length == 0)
      return;
    int[] arrayOfInt = new int[paramArrayOfInt.length];
    System.arraycopy(paramArrayOfInt, 0, arrayOfInt, 0, paramArrayOfInt.length);
    sort(arrayOfInt);
    int i = arrayOfInt[(arrayOfInt.length - 1)];
    int j = arrayOfInt[0];
    int k = OS.SendMessage(this.handle, 395, 0, 0);
    if ((i < 0) || (i > j) || (j >= k))
      error(6);
    int m = OS.SendMessage(this.handle, 398, 0, 0);
    RECT localRECT = null;
    int n = 0;
    int i1 = 0;
    int i2 = 0;
    int i3 = 0;
    if ((this.style & 0x100) != 0)
    {
      localRECT = new RECT();
      n = OS.GetDC(this.handle);
      i2 = OS.SendMessage(this.handle, 49, 0, 0);
      if (i2 != 0)
        i1 = OS.SelectObject(n, i2);
    }
    int i4 = getCodePage();
    int i5 = 0;
    int i6 = 0;
    int i7 = -1;
    while (i5 < arrayOfInt.length)
    {
      int i8 = arrayOfInt[i5];
      if (i8 != i7)
      {
        TCHAR localTCHAR = null;
        int i10;
        if ((this.style & 0x100) != 0)
        {
          i9 = OS.SendMessage(this.handle, 394, i8, 0);
          if (i9 == -1)
            break;
          localTCHAR = new TCHAR(i4, i9 + 1);
          i10 = OS.SendMessage(this.handle, 393, i8, localTCHAR);
          if (i10 == -1)
            break;
        }
        int i9 = OS.SendMessage(this.handle, 386, i8, 0);
        if (i9 == -1)
          break;
        if ((this.style & 0x100) != 0)
        {
          i10 = 3104;
          OS.DrawText(n, localTCHAR, -1, localRECT, i10);
          i3 = Math.max(i3, localRECT.right - localRECT.left);
        }
        if (i8 < m)
          i6++;
        i7 = i8;
      }
      i5++;
    }
    if ((this.style & 0x100) != 0)
    {
      if (i2 != 0)
        OS.SelectObject(n, i1);
      OS.ReleaseDC(this.handle, n);
      setScrollWidth(i3, false);
    }
    if (i6 > 0)
      m -= i6;
    OS.SendMessage(this.handle, 407, m, 0);
    if (i5 < arrayOfInt.length)
      error(15);
  }

  public void remove(int paramInt)
  {
    checkWidget();
    TCHAR localTCHAR = null;
    int k;
    if ((this.style & 0x100) != 0)
    {
      i = OS.SendMessage(this.handle, 394, paramInt, 0);
      if (i == -1)
      {
        j = OS.SendMessage(this.handle, 395, 0, 0);
        if ((paramInt >= 0) && (paramInt < j))
          error(15);
        error(6);
      }
      localTCHAR = new TCHAR(getCodePage(), i + 1);
      j = OS.SendMessage(this.handle, 393, paramInt, localTCHAR);
      if (j == -1)
      {
        k = OS.SendMessage(this.handle, 395, 0, 0);
        if ((paramInt >= 0) && (paramInt < k))
          error(15);
        error(6);
      }
    }
    int i = OS.SendMessage(this.handle, 398, 0, 0);
    int j = OS.SendMessage(this.handle, 386, paramInt, 0);
    if (j == -1)
    {
      k = OS.SendMessage(this.handle, 395, 0, 0);
      if ((paramInt >= 0) && (paramInt < k))
        error(15);
      error(6);
    }
    if ((this.style & 0x100) != 0)
      setScrollWidth(localTCHAR, false);
    if (paramInt < i)
      i--;
    OS.SendMessage(this.handle, 407, i, 0);
  }

  public void remove(int paramInt1, int paramInt2)
  {
    checkWidget();
    if (paramInt1 > paramInt2)
      return;
    int i = OS.SendMessage(this.handle, 395, 0, 0);
    if ((paramInt1 < 0) || (paramInt1 > paramInt2) || (paramInt2 >= i))
      error(6);
    if ((paramInt1 == 0) && (paramInt2 == i - 1))
    {
      removeAll();
      return;
    }
    int j = OS.SendMessage(this.handle, 398, 0, 0);
    RECT localRECT = null;
    int k = 0;
    int m = 0;
    int n = 0;
    int i1 = 0;
    if ((this.style & 0x100) != 0)
    {
      localRECT = new RECT();
      k = OS.GetDC(this.handle);
      n = OS.SendMessage(this.handle, 49, 0, 0);
      if (n != 0)
        m = OS.SelectObject(k, n);
    }
    int i2 = getCodePage();
    int i3 = paramInt1;
    int i4 = 3104;
    while (i3 <= paramInt2)
    {
      TCHAR localTCHAR = null;
      if ((this.style & 0x100) != 0)
      {
        i5 = OS.SendMessage(this.handle, 394, paramInt1, 0);
        if (i5 == -1)
          break;
        localTCHAR = new TCHAR(i2, i5 + 1);
        int i6 = OS.SendMessage(this.handle, 393, paramInt1, localTCHAR);
        if (i6 == -1)
          break;
      }
      int i5 = OS.SendMessage(this.handle, 386, paramInt1, 0);
      if (i5 == -1)
        break;
      if ((this.style & 0x100) != 0)
      {
        OS.DrawText(k, localTCHAR, -1, localRECT, i4);
        i1 = Math.max(i1, localRECT.right - localRECT.left);
      }
      i3++;
    }
    if ((this.style & 0x100) != 0)
    {
      if (n != 0)
        OS.SelectObject(k, m);
      OS.ReleaseDC(this.handle, k);
      setScrollWidth(i1, false);
    }
    if (paramInt2 < j)
      j -= paramInt2 - paramInt1 + 1;
    OS.SendMessage(this.handle, 407, j, 0);
    if (i3 <= paramInt2)
      error(15);
  }

  public void remove(String paramString)
  {
    checkWidget();
    if (paramString == null)
      error(4);
    int i = indexOf(paramString, 0);
    if (i == -1)
      error(5);
    remove(i);
  }

  public void removeAll()
  {
    checkWidget();
    OS.SendMessage(this.handle, 388, 0, 0);
    if ((this.style & 0x100) != 0)
      OS.SendMessage(this.handle, 404, 0, 0);
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

  public void select(int[] paramArrayOfInt)
  {
    checkWidget();
    if (paramArrayOfInt == null)
      error(4);
    int i = paramArrayOfInt.length;
    if ((i == 0) || (((this.style & 0x4) != 0) && (i > 1)))
      return;
    select(paramArrayOfInt, false);
  }

  void select(int[] paramArrayOfInt, boolean paramBoolean)
  {
    for (int i = 0; i < paramArrayOfInt.length; i++)
    {
      int j = paramArrayOfInt[i];
      if (j != -1)
        select(j, false);
    }
    if (paramBoolean)
      showSelection();
  }

  public void select(int paramInt)
  {
    checkWidget();
    select(paramInt, false);
  }

  void select(int paramInt, boolean paramBoolean)
  {
    if (paramInt < 0)
      return;
    int i = OS.SendMessage(this.handle, 395, 0, 0);
    if (paramInt >= i)
      return;
    if (paramBoolean)
    {
      if ((this.style & 0x4) != 0)
        OS.SendMessage(this.handle, 390, paramInt, 0);
      else
        OS.SendMessage(this.handle, 389, 1, paramInt);
      return;
    }
    int j = OS.SendMessage(this.handle, 398, 0, 0);
    RECT localRECT1 = new RECT();
    RECT localRECT2 = null;
    OS.SendMessage(this.handle, 408, paramInt, localRECT1);
    int k = (getDrawing()) && (OS.IsWindowVisible(this.handle)) ? 1 : 0;
    if (k != 0)
    {
      OS.UpdateWindow(this.handle);
      OS.SendMessage(this.handle, 11, 0, 0);
    }
    int m = -1;
    if ((this.style & 0x4) != 0)
    {
      int n = OS.SendMessage(this.handle, 392, 0, 0);
      if (n != -1)
      {
        localRECT2 = new RECT();
        OS.SendMessage(this.handle, 408, n, localRECT2);
      }
      OS.SendMessage(this.handle, 390, paramInt, 0);
    }
    else
    {
      m = OS.SendMessage(this.handle, 415, 0, 0);
      OS.SendMessage(this.handle, 389, 1, paramInt);
    }
    if (((this.style & 0x2) != 0) && (m != -1))
      OS.SendMessage(this.handle, 414, m, 0);
    OS.SendMessage(this.handle, 407, j, 0);
    if (k != 0)
    {
      OS.SendMessage(this.handle, 11, 1, 0);
      OS.ValidateRect(this.handle, null);
      OS.InvalidateRect(this.handle, localRECT1, true);
      if (localRECT2 != null)
        OS.InvalidateRect(this.handle, localRECT2, true);
    }
  }

  public void select(int paramInt1, int paramInt2)
  {
    checkWidget();
    if ((paramInt2 < 0) || (paramInt1 > paramInt2) || (((this.style & 0x4) != 0) && (paramInt1 != paramInt2)))
      return;
    int i = OS.SendMessage(this.handle, 395, 0, 0);
    if ((i == 0) || (paramInt1 >= i))
      return;
    paramInt1 = Math.max(0, paramInt1);
    paramInt2 = Math.min(paramInt2, i - 1);
    if ((this.style & 0x4) != 0)
      select(paramInt1, false);
    else
      select(paramInt1, paramInt2, false);
  }

  void select(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    if (paramInt1 == paramInt2)
    {
      select(paramInt1, paramBoolean);
      return;
    }
    OS.SendMessage(this.handle, 387, paramInt1, paramInt2);
    if (paramBoolean)
      showSelection();
  }

  public void selectAll()
  {
    checkWidget();
    if ((this.style & 0x4) != 0)
      return;
    OS.SendMessage(this.handle, 389, 1, -1);
  }

  void setFocusIndex(int paramInt)
  {
    int i = OS.SendMessage(this.handle, 395, 0, 0);
    if ((paramInt < 0) || (paramInt >= i))
      return;
    OS.SendMessage(this.handle, 414, paramInt, 0);
  }

  public void setFont(Font paramFont)
  {
    checkWidget();
    super.setFont(paramFont);
    if ((this.style & 0x100) != 0)
      setScrollWidth();
  }

  public void setItem(int paramInt, String paramString)
  {
    checkWidget();
    if (paramString == null)
      error(4);
    int i = getTopIndex();
    boolean bool = isSelected(paramInt);
    remove(paramInt);
    add(paramString, paramInt);
    if (bool)
      select(paramInt, false);
    setTopIndex(i);
  }

  public void setItems(String[] paramArrayOfString)
  {
    checkWidget();
    if (paramArrayOfString == null)
      error(4);
    for (int i = 0; i < paramArrayOfString.length; i++)
      if (paramArrayOfString[i] == null)
        error(5);
    i = OS.GetWindowLongPtr(this.handle, -4);
    OS.SetWindowLongPtr(this.handle, -4, ListProc);
    int j = (getDrawing()) && (OS.IsWindowVisible(this.handle)) ? 1 : 0;
    if (j != 0)
      OS.SendMessage(this.handle, 11, 0, 0);
    RECT localRECT = null;
    int k = 0;
    int m = 0;
    int n = 0;
    int i1 = 0;
    if ((this.style & 0x100) != 0)
    {
      localRECT = new RECT();
      k = OS.GetDC(this.handle);
      n = OS.SendMessage(this.handle, 49, 0, 0);
      if (n != 0)
        m = OS.SelectObject(k, n);
      OS.SendMessage(this.handle, 404, 0, 0);
    }
    int i2 = paramArrayOfString.length;
    OS.SendMessage(this.handle, 388, 0, 0);
    OS.SendMessage(this.handle, 424, i2, i2 * 32);
    int i3 = 0;
    int i4 = getCodePage();
    while (i3 < i2)
    {
      String str = paramArrayOfString[i3];
      TCHAR localTCHAR = new TCHAR(i4, str, true);
      int i5 = OS.SendMessage(this.handle, 384, 0, localTCHAR);
      if ((i5 == -1) || (i5 == -2))
        break;
      if ((this.style & 0x100) != 0)
      {
        int i6 = 3104;
        OS.DrawText(k, localTCHAR, -1, localRECT, i6);
        i1 = Math.max(i1, localRECT.right - localRECT.left);
      }
      i3++;
    }
    if ((this.style & 0x100) != 0)
    {
      if (n != 0)
        OS.SelectObject(k, m);
      OS.ReleaseDC(this.handle, k);
      OS.SendMessage(this.handle, 404, i1 + 3, 0);
    }
    if (j != 0)
      OS.SendMessage(this.handle, 11, 1, 0);
    OS.SetWindowLongPtr(this.handle, -4, i);
    if (i3 < paramArrayOfString.length)
      error(14);
  }

  void setScrollWidth()
  {
    int i = 0;
    RECT localRECT = new RECT();
    int k = 0;
    int m = OS.GetDC(this.handle);
    int j = OS.SendMessage(this.handle, 49, 0, 0);
    if (j != 0)
      k = OS.SelectObject(m, j);
    int n = getCodePage();
    int i1 = OS.SendMessage(this.handle, 395, 0, 0);
    int i2 = 3104;
    for (int i3 = 0; i3 < i1; i3++)
    {
      int i4 = OS.SendMessage(this.handle, 394, i3, 0);
      if (i4 != -1)
      {
        TCHAR localTCHAR = new TCHAR(n, i4 + 1);
        int i5 = OS.SendMessage(this.handle, 393, i3, localTCHAR);
        if (i5 != -1)
        {
          OS.DrawText(m, localTCHAR, -1, localRECT, i2);
          i = Math.max(i, localRECT.right - localRECT.left);
        }
      }
    }
    if (j != 0)
      OS.SelectObject(m, k);
    OS.ReleaseDC(this.handle, m);
    OS.SendMessage(this.handle, 404, i + 3, 0);
  }

  void setScrollWidth(TCHAR paramTCHAR, boolean paramBoolean)
  {
    RECT localRECT = new RECT();
    int j = 0;
    int k = OS.GetDC(this.handle);
    int i = OS.SendMessage(this.handle, 49, 0, 0);
    if (i != 0)
      j = OS.SelectObject(k, i);
    int m = 3104;
    OS.DrawText(k, paramTCHAR, -1, localRECT, m);
    if (i != 0)
      OS.SelectObject(k, j);
    OS.ReleaseDC(this.handle, k);
    setScrollWidth(localRECT.right - localRECT.left, paramBoolean);
  }

  void setScrollWidth(int paramInt, boolean paramBoolean)
  {
    paramInt += 3;
    int i = OS.SendMessage(this.handle, 403, 0, 0);
    if (paramBoolean)
    {
      if (paramInt <= i)
        return;
      OS.SendMessage(this.handle, 404, paramInt, 0);
    }
    else
    {
      if (paramInt < i)
        return;
      setScrollWidth();
    }
  }

  public void setSelection(int[] paramArrayOfInt)
  {
    checkWidget();
    if (paramArrayOfInt == null)
      error(4);
    deselectAll();
    int i = paramArrayOfInt.length;
    if ((i == 0) || (((this.style & 0x4) != 0) && (i > 1)))
      return;
    select(paramArrayOfInt, true);
    if ((this.style & 0x2) != 0)
    {
      int j = paramArrayOfInt[0];
      if (j >= 0)
        setFocusIndex(j);
    }
  }

  public void setSelection(String[] paramArrayOfString)
  {
    checkWidget();
    if (paramArrayOfString == null)
      error(4);
    deselectAll();
    int i = paramArrayOfString.length;
    if ((i == 0) || (((this.style & 0x4) != 0) && (i > 1)))
      return;
    int j = -1;
    for (int k = i - 1; k >= 0; k--)
    {
      String str = paramArrayOfString[k];
      int m = 0;
      if (str != null)
      {
        int n = -1;
        while ((m = indexOf(str, m)) != -1)
        {
          if (n == -1)
            n = m;
          select(m, false);
          if (((this.style & 0x4) != 0) && (isSelected(m)))
          {
            showSelection();
            return;
          }
          m++;
        }
        if (n != -1)
          j = n;
      }
    }
    if (((this.style & 0x2) != 0) && (j >= 0))
      setFocusIndex(j);
  }

  public void setSelection(int paramInt)
  {
    checkWidget();
    deselectAll();
    select(paramInt, true);
    if (((this.style & 0x2) != 0) && (paramInt >= 0))
      setFocusIndex(paramInt);
  }

  public void setSelection(int paramInt1, int paramInt2)
  {
    checkWidget();
    deselectAll();
    if ((paramInt2 < 0) || (paramInt1 > paramInt2) || (((this.style & 0x4) != 0) && (paramInt1 != paramInt2)))
      return;
    int i = OS.SendMessage(this.handle, 395, 0, 0);
    if ((i == 0) || (paramInt1 >= i))
      return;
    paramInt1 = Math.max(0, paramInt1);
    paramInt2 = Math.min(paramInt2, i - 1);
    if ((this.style & 0x4) != 0)
    {
      select(paramInt1, true);
    }
    else
    {
      select(paramInt1, paramInt2, true);
      setFocusIndex(paramInt1);
    }
  }

  public void setTopIndex(int paramInt)
  {
    checkWidget();
    int i = OS.SendMessage(this.handle, 407, paramInt, 0);
    if (i == -1)
    {
      int j = OS.SendMessage(this.handle, 395, 0, 0);
      paramInt = Math.min(j - 1, Math.max(0, paramInt));
      OS.SendMessage(this.handle, 407, paramInt, 0);
    }
  }

  public void showSelection()
  {
    checkWidget();
    int i;
    if ((this.style & 0x4) != 0)
    {
      i = OS.SendMessage(this.handle, 392, 0, 0);
    }
    else
    {
      int[] arrayOfInt = new int[1];
      k = OS.SendMessage(this.handle, 401, 1, arrayOfInt);
      i = arrayOfInt[0];
      if (k != 1)
        i = -1;
    }
    if (i == -1)
      return;
    int j = OS.SendMessage(this.handle, 395, 0, 0);
    if (j == 0)
      return;
    int k = OS.SendMessage(this.handle, 417, 0, 0);
    forceResize();
    RECT localRECT = new RECT();
    OS.GetClientRect(this.handle, localRECT);
    int m = OS.SendMessage(this.handle, 398, 0, 0);
    int n = Math.max(localRECT.bottom / k, 1);
    int i1 = Math.min(m + n, j) - 1;
    if ((m <= i) && (i <= i1))
      return;
    int i2 = Math.min(Math.max(i - n / 2, 0), j - 1);
    OS.SendMessage(this.handle, 407, i2, 0);
  }

  int widgetStyle()
  {
    int i = super.widgetStyle() | 0x1 | 0x100;
    if ((this.style & 0x4) != 0)
      return i;
    if ((this.style & 0x2) != 0)
    {
      if ((this.style & 0x40) != 0)
        return i | 0x8;
      return i | 0x800;
    }
    return i;
  }

  TCHAR windowClass()
  {
    return ListClass;
  }

  int windowProc()
  {
    return ListProc;
  }

  LRESULT WM_CHAR(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_CHAR(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    if ((OS.GetKeyState(17) < 0) && (OS.GetKeyState(16) >= 0))
    {
      int i = OS.GetWindowLong(this.handle, -16);
      if ((i & 0x800) != 0)
        switch (paramInt1)
        {
        case 32:
          int j = OS.SendMessage(this.handle, 415, 0, 0);
          int k = OS.SendMessage(this.handle, 391, j, 0);
          if (k != -1)
          {
            OS.SendMessage(this.handle, 389, k != 0 ? 0 : 1, j);
            OS.SendMessage(this.handle, 61852, j, 0);
            sendSelectionEvent(13);
            return LRESULT.ZERO;
          }
          break;
        }
    }
    return localLRESULT;
  }

  LRESULT WM_KEYDOWN(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_KEYDOWN(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    if ((OS.GetKeyState(17) < 0) && (OS.GetKeyState(16) >= 0))
    {
      int i = OS.GetWindowLong(this.handle, -16);
      if ((i & 0x800) != 0)
      {
        int j = -1;
        int k;
        int m;
        RECT localRECT2;
        int i1;
        int i2;
        switch (paramInt1)
        {
        case 32:
          return LRESULT.ZERO;
        case 38:
        case 40:
          k = OS.SendMessage(this.handle, 415, 0, 0);
          j = Math.max(0, k + (paramInt1 == 38 ? -1 : 1));
          break;
        case 33:
          k = OS.SendMessage(this.handle, 398, 0, 0);
          m = OS.SendMessage(this.handle, 415, 0, 0);
          if (m != k)
          {
            j = k;
          }
          else
          {
            forceResize();
            localRECT2 = new RECT();
            OS.GetClientRect(this.handle, localRECT2);
            i1 = OS.SendMessage(this.handle, 417, 0, 0);
            i2 = Math.max(2, localRECT2.bottom / i1);
            j = Math.max(0, k - (i2 - 1));
          }
          break;
        case 34:
          k = OS.SendMessage(this.handle, 398, 0, 0);
          m = OS.SendMessage(this.handle, 415, 0, 0);
          forceResize();
          localRECT2 = new RECT();
          OS.GetClientRect(this.handle, localRECT2);
          i1 = OS.SendMessage(this.handle, 417, 0, 0);
          i2 = Math.max(2, localRECT2.bottom / i1);
          int i3 = k + i2 - 1;
          if (m != i3)
            j = i3;
          else
            j = i3 + i2 - 1;
          int i4 = OS.SendMessage(this.handle, 395, 0, 0);
          if (i4 != -1)
            j = Math.min(i4 - 1, j);
          break;
        case 36:
          j = 0;
          break;
        case 35:
          k = OS.SendMessage(this.handle, 395, 0, 0);
          if (k != -1)
            j = k - 1;
          break;
        case 37:
        case 39:
        }
        if (j != -1)
        {
          k = OS.SendMessage(this.handle, 297, 0, 0);
          if ((k & 0x1) != 0)
          {
            OS.SendMessage(this.handle, 295, 3, 0);
            RECT localRECT1 = new RECT();
            int n = OS.SendMessage(this.handle, 415, 0, 0);
            OS.SendMessage(this.handle, 408, n, localRECT1);
            OS.InvalidateRect(this.handle, localRECT1, true);
          }
          OS.SendMessage(this.handle, 414, j, 0);
          return LRESULT.ZERO;
        }
      }
    }
    return localLRESULT;
  }

  LRESULT WM_SETREDRAW(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_SETREDRAW(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    OS.DefWindowProc(this.handle, 11, paramInt1, paramInt2);
    return localLRESULT;
  }

  LRESULT WM_SIZE(int paramInt1, int paramInt2)
  {
    int i = OS.SendMessage(this.handle, 398, 0, 0);
    LRESULT localLRESULT = super.WM_SIZE(paramInt1, paramInt2);
    if (!isDisposed())
    {
      SCROLLINFO localSCROLLINFO = new SCROLLINFO();
      localSCROLLINFO.cbSize = SCROLLINFO.sizeof;
      localSCROLLINFO.fMask = 4;
      if ((OS.GetScrollInfo(this.handle, 0, localSCROLLINFO)) && (localSCROLLINFO.nPos != 0))
        OS.InvalidateRect(this.handle, null, true);
      int j = OS.SendMessage(this.handle, 398, 0, 0);
      if (i != j)
        OS.InvalidateRect(this.handle, null, true);
    }
    return localLRESULT;
  }

  LRESULT wmCommandChild(int paramInt1, int paramInt2)
  {
    int i = OS.HIWORD(paramInt1);
    switch (i)
    {
    case 1:
      sendSelectionEvent(13);
      break;
    case 2:
      sendSelectionEvent(14);
    }
    return super.wmCommandChild(paramInt1, paramInt2);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.List
 * JD-Core Version:    0.6.2
 */