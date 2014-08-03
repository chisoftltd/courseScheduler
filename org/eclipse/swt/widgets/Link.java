package org.eclipse.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.accessibility.Accessible;
import org.eclipse.swt.accessibility.AccessibleAdapter;
import org.eclipse.swt.accessibility.AccessibleControlAdapter;
import org.eclipse.swt.accessibility.AccessibleControlEvent;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.GCData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.TextLayout;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.swt.internal.win32.LITEM;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.NMHDR;
import org.eclipse.swt.internal.win32.NMLINK;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.PAINTSTRUCT;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.TEXTMETRIC;
import org.eclipse.swt.internal.win32.TEXTMETRICA;
import org.eclipse.swt.internal.win32.TEXTMETRICW;
import org.eclipse.swt.internal.win32.WNDCLASS;

public class Link extends Control
{
  String text;
  TextLayout layout;
  Color linkColor;
  Color disabledColor;
  Point[] offsets;
  Point selection;
  String[] ids;
  int[] mnemonics;
  int focusIndex;
  int mouseDownIndex;
  int font;
  static final RGB LINK_FOREGROUND = new RGB(0, 51, 153);
  static final int LinkProc;
  static final TCHAR LinkClass = new TCHAR(0, "SysLink", true);

  static
  {
    if (OS.COMCTL32_MAJOR >= 6)
    {
      WNDCLASS localWNDCLASS = new WNDCLASS();
      OS.GetClassInfo(0, LinkClass, localWNDCLASS);
      LinkProc = localWNDCLASS.lpfnWndProc;
      int i = OS.GetModuleHandle(null);
      int j = OS.GetProcessHeap();
      localWNDCLASS.hInstance = i;
      localWNDCLASS.style &= -16385;
      localWNDCLASS.style |= 8;
      int k = LinkClass.length() * TCHAR.sizeof;
      int m = OS.HeapAlloc(j, 8, k);
      OS.MoveMemory(m, LinkClass, k);
      localWNDCLASS.lpszClassName = m;
      OS.RegisterClass(localWNDCLASS);
      OS.HeapFree(j, 0, m);
    }
    else
    {
      LinkProc = 0;
    }
  }

  public Link(Composite paramComposite, int paramInt)
  {
    super(paramComposite, paramInt);
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
    if (LinkProc != 0)
    {
      switch (paramInt2)
      {
      case 15:
        if (paramInt3 != 0)
        {
          OS.SendMessage(paramInt1, 792, paramInt3, 0);
          return 0;
        }
        break;
      }
      return OS.CallWindowProc(LinkProc, paramInt1, paramInt2, paramInt3, paramInt4);
    }
    return OS.DefWindowProc(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  public Point computeSize(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    checkWidget();
    if ((paramInt1 != -1) && (paramInt1 < 0))
      paramInt1 = 0;
    if ((paramInt2 != -1) && (paramInt2 < 0))
      paramInt2 = 0;
    int i;
    int j;
    if (OS.COMCTL32_MAJOR >= 6)
    {
      k = OS.GetDC(this.handle);
      int m = OS.SendMessage(this.handle, 49, 0, 0);
      int n = OS.SelectObject(k, m);
      Object localObject;
      if (this.text.length() > 0)
      {
        localObject = new TCHAR(getCodePage(), parse(this.text), false);
        RECT localRECT = new RECT();
        int i1 = 3072;
        if (paramInt1 != -1)
        {
          i1 |= 16;
          localRECT.right = paramInt1;
        }
        OS.DrawText(k, (TCHAR)localObject, ((TCHAR)localObject).length(), localRECT, i1);
        i = localRECT.right - localRECT.left;
        j = localRECT.bottom;
      }
      else
      {
        localObject = OS.IsUnicode ? new TEXTMETRICW() : new TEXTMETRICA();
        OS.GetTextMetrics(k, (TEXTMETRIC)localObject);
        i = 0;
        j = ((TEXTMETRIC)localObject).tmHeight;
      }
      if (m != 0)
        OS.SelectObject(k, n);
      OS.ReleaseDC(this.handle, k);
    }
    else
    {
      k = this.layout.getWidth();
      Rectangle localRectangle;
      if (paramInt1 == 0)
      {
        this.layout.setWidth(1);
        localRectangle = this.layout.getBounds();
        i = 0;
        j = localRectangle.height;
      }
      else
      {
        this.layout.setWidth(paramInt1);
        localRectangle = this.layout.getBounds();
        i = localRectangle.width;
        j = localRectangle.height;
      }
      this.layout.setWidth(k);
    }
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
    this.state |= 256;
    if (OS.COMCTL32_MAJOR < 6)
    {
      this.layout = new TextLayout(this.display);
      if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(4, 10)))
        this.linkColor = Color.win32_new(this.display, OS.GetSysColor(OS.COLOR_HOTLIGHT));
      else
        this.linkColor = new Color(this.display, LINK_FOREGROUND);
      this.disabledColor = Color.win32_new(this.display, OS.GetSysColor(OS.COLOR_GRAYTEXT));
      this.offsets = new Point[0];
      this.ids = new String[0];
      this.mnemonics = new int[0];
      this.selection = new Point(-1, -1);
      this.focusIndex = (this.mouseDownIndex = -1);
    }
  }

  void createWidget()
  {
    super.createWidget();
    this.text = "";
    if (OS.COMCTL32_MAJOR < 6)
    {
      if ((this.style & 0x8000000) != 0)
        this.layout.setOrientation(67108864);
      initAccessible();
    }
  }

  void drawWidget(GC paramGC, RECT paramRECT)
  {
    drawBackground(paramGC.handle, paramRECT);
    int i = this.selection.x;
    int j = this.selection.y;
    if (i > j)
    {
      i = this.selection.y;
      j = this.selection.x;
    }
    i = j = -1;
    if (!OS.IsWindowEnabled(this.handle))
      paramGC.setForeground(this.disabledColor);
    this.layout.draw(paramGC, 0, 0, i, j, null, null);
    Object localObject1;
    if ((hasFocus()) && (this.focusIndex != -1))
    {
      localObject1 = getRectangles(this.focusIndex);
      for (int k = 0; k < localObject1.length; k++)
      {
        Object localObject2 = localObject1[k];
        paramGC.drawFocus(localObject2.x, localObject2.y, localObject2.width, localObject2.height);
      }
    }
    if ((hooks(9)) || (filters(9)))
    {
      localObject1 = new Event();
      ((Event)localObject1).gc = paramGC;
      ((Event)localObject1).x = paramRECT.left;
      ((Event)localObject1).y = paramRECT.top;
      ((Event)localObject1).width = (paramRECT.right - paramRECT.left);
      ((Event)localObject1).height = (paramRECT.bottom - paramRECT.top);
      sendEvent(9, (Event)localObject1);
      ((Event)localObject1).gc = null;
    }
  }

  void enableWidget(boolean paramBoolean)
  {
    Object localObject;
    if (OS.COMCTL32_MAJOR >= 6)
    {
      localObject = new LITEM();
      ((LITEM)localObject).mask = 3;
      ((LITEM)localObject).stateMask = 2;
      ((LITEM)localObject).state = (paramBoolean ? 2 : 0);
      while (OS.SendMessage(this.handle, 1794, 0, (LITEM)localObject) != 0)
        localObject.iLink += 1;
    }
    else
    {
      localObject = new TextStyle(null, paramBoolean ? this.linkColor : this.disabledColor, null);
      ((TextStyle)localObject).underline = true;
      for (int i = 0; i < this.offsets.length; i++)
      {
        Point localPoint = this.offsets[i];
        this.layout.setStyle((TextStyle)localObject, localPoint.x, localPoint.y);
      }
      redraw();
    }
    super.enableWidget(paramBoolean);
  }

  void initAccessible()
  {
    Accessible localAccessible = getAccessible();
    localAccessible.addAccessibleListener(new AccessibleAdapter()
    {
      public void getName(AccessibleEvent paramAnonymousAccessibleEvent)
      {
        paramAnonymousAccessibleEvent.result = Link.this.parse(Link.this.text);
      }
    });
    localAccessible.addAccessibleControlListener(new AccessibleControlAdapter()
    {
      public void getChildAtPoint(AccessibleControlEvent paramAnonymousAccessibleControlEvent)
      {
        paramAnonymousAccessibleControlEvent.childID = -1;
      }

      public void getLocation(AccessibleControlEvent paramAnonymousAccessibleControlEvent)
      {
        Rectangle localRectangle = Link.this.display.map(Link.this.getParent(), null, Link.this.getBounds());
        paramAnonymousAccessibleControlEvent.x = localRectangle.x;
        paramAnonymousAccessibleControlEvent.y = localRectangle.y;
        paramAnonymousAccessibleControlEvent.width = localRectangle.width;
        paramAnonymousAccessibleControlEvent.height = localRectangle.height;
      }

      public void getChildCount(AccessibleControlEvent paramAnonymousAccessibleControlEvent)
      {
        paramAnonymousAccessibleControlEvent.detail = 0;
      }

      public void getRole(AccessibleControlEvent paramAnonymousAccessibleControlEvent)
      {
        paramAnonymousAccessibleControlEvent.detail = 30;
      }

      public void getState(AccessibleControlEvent paramAnonymousAccessibleControlEvent)
      {
        paramAnonymousAccessibleControlEvent.detail = 1048576;
        if (Link.this.hasFocus())
          paramAnonymousAccessibleControlEvent.detail |= 4;
      }

      public void getDefaultAction(AccessibleControlEvent paramAnonymousAccessibleControlEvent)
      {
        paramAnonymousAccessibleControlEvent.result = SWT.getMessage("SWT_Press");
      }

      public void getSelection(AccessibleControlEvent paramAnonymousAccessibleControlEvent)
      {
        if (Link.this.hasFocus())
          paramAnonymousAccessibleControlEvent.childID = -1;
      }

      public void getFocus(AccessibleControlEvent paramAnonymousAccessibleControlEvent)
      {
        if (Link.this.hasFocus())
          paramAnonymousAccessibleControlEvent.childID = -1;
      }
    });
  }

  String getNameText()
  {
    return getText();
  }

  Rectangle[] getRectangles(int paramInt)
  {
    int i = this.layout.getLineCount();
    Object localObject = new Rectangle[i];
    int[] arrayOfInt = this.layout.getLineOffsets();
    Point localPoint = this.offsets[paramInt];
    for (int j = 1; localPoint.x > arrayOfInt[j]; j++);
    for (int k = 1; localPoint.y > arrayOfInt[k]; k++);
    int m = 0;
    if (j == k)
    {
      localObject[(m++)] = this.layout.getBounds(localPoint.x, localPoint.y);
    }
    else
    {
      localObject[(m++)] = this.layout.getBounds(localPoint.x, arrayOfInt[j] - 1);
      localObject[(m++)] = this.layout.getBounds(arrayOfInt[(k - 1)], localPoint.y);
      if (k - j > 1)
        for (int n = j; n < k - 1; n++)
          localObject[(m++)] = this.layout.getLineBounds(n);
    }
    if (localObject.length != m)
    {
      Rectangle[] arrayOfRectangle = new Rectangle[m];
      System.arraycopy(localObject, 0, arrayOfRectangle, 0, m);
      localObject = arrayOfRectangle;
    }
    return localObject;
  }

  public String getText()
  {
    checkWidget();
    return this.text;
  }

  boolean mnemonicHit(char paramChar)
  {
    if (this.mnemonics != null)
    {
      int i = Character.toUpperCase(paramChar);
      String str = parse(this.text);
      for (int j = 0; j < this.mnemonics.length - 1; j++)
        if (this.mnemonics[j] != -1)
        {
          char c = str.charAt(this.mnemonics[j]);
          if (i == Character.toUpperCase(c))
          {
            if (!setFocus())
              return false;
            if (OS.COMCTL32_MAJOR >= 6)
            {
              int k = OS.GetWindowLong(this.handle, -16);
              LITEM localLITEM = new LITEM();
              localLITEM.mask = 3;
              localLITEM.stateMask = 1;
              while (localLITEM.iLink < this.mnemonics.length)
              {
                if (localLITEM.iLink != j)
                  OS.SendMessage(this.handle, 1794, 0, localLITEM);
                localLITEM.iLink += 1;
              }
              localLITEM.iLink = j;
              localLITEM.state = 1;
              OS.SendMessage(this.handle, 1794, 0, localLITEM);
              OS.SetWindowLong(this.handle, -16, k);
            }
            else
            {
              this.focusIndex = j;
              redraw();
            }
            return true;
          }
        }
    }
    return false;
  }

  boolean mnemonicMatch(char paramChar)
  {
    if (this.mnemonics != null)
    {
      int i = Character.toUpperCase(paramChar);
      String str = parse(this.text);
      for (int j = 0; j < this.mnemonics.length - 1; j++)
        if (this.mnemonics[j] != -1)
        {
          char c = str.charAt(this.mnemonics[j]);
          if (i == Character.toUpperCase(c))
            return true;
        }
    }
    return false;
  }

  String parse(String paramString)
  {
    int i = paramString.length();
    this.offsets = new Point[i / 4];
    this.ids = new String[i / 4];
    this.mnemonics = new int[i / 4 + 1];
    StringBuffer localStringBuffer = new StringBuffer();
    char[] arrayOfChar = new char[i];
    paramString.getChars(0, paramString.length(), arrayOfChar, 0);
    int j = 0;
    int k = 0;
    int m = 0;
    int n = 0;
    int i1 = 0;
    int i2 = 0;
    int i3 = 0;
    int i4 = 0;
    int i6;
    while (j < i)
    {
      char c = Character.toLowerCase(arrayOfChar[j]);
      switch (k)
      {
      case 0:
        if (c == '<')
        {
          i1 = j;
          k++;
        }
        break;
      case 1:
        if (c == 'a')
          k++;
        break;
      case 2:
        switch (c)
        {
        case 'h':
          k = 7;
          break;
        case '>':
          i2 = j + 1;
          k++;
          break;
        default:
          if (!Character.isWhitespace(c))
            k = 13;
          break;
        }
        break;
      case 3:
        if (c == '<')
        {
          i3 = j;
          k++;
        }
        break;
      case 4:
        k = c == '/' ? k + 1 : 3;
        break;
      case 5:
        k = c == 'a' ? k + 1 : 3;
        break;
      case 6:
        if (c == '>')
        {
          this.mnemonics[m] = parseMnemonics(arrayOfChar, n, i1, localStringBuffer);
          i6 = localStringBuffer.length();
          parseMnemonics(arrayOfChar, i2, i3, localStringBuffer);
          this.offsets[m] = new Point(i6, localStringBuffer.length() - 1);
          if (this.ids[m] == null)
            this.ids[m] = new String(arrayOfChar, i2, i3 - i2);
          m++;
          n = i1 = i2 = i3 = i4 = j + 1;
          k = 0;
        }
        else
        {
          k = 3;
        }
        break;
      case 7:
        k = c == 'r' ? k + 1 : 0;
        break;
      case 8:
        k = c == 'e' ? k + 1 : 0;
        break;
      case 9:
        k = c == 'f' ? k + 1 : 0;
        break;
      case 10:
        k = c == '=' ? k + 1 : 0;
        break;
      case 11:
        if (c == '"')
        {
          k++;
          i4 = j + 1;
        }
        else
        {
          k = 0;
        }
        break;
      case 12:
        if (c == '"')
        {
          this.ids[m] = new String(arrayOfChar, i4, j - i4);
          k = 2;
        }
        break;
      case 13:
        if (Character.isWhitespace(c))
          k = 0;
        else if (c == '=')
          k++;
        break;
      case 14:
        k = c == '"' ? k + 1 : 0;
        break;
      case 15:
        if (c == '"')
          k = 2;
        break;
      default:
        k = 0;
      }
      j++;
    }
    if (n < i)
    {
      int i5 = parseMnemonics(arrayOfChar, n, i1, localStringBuffer);
      i6 = parseMnemonics(arrayOfChar, Math.max(i1, i2), i, localStringBuffer);
      if (i6 == -1)
        i6 = i5;
      this.mnemonics[m] = i6;
    }
    else
    {
      this.mnemonics[m] = -1;
    }
    if (this.offsets.length != m)
    {
      Point[] arrayOfPoint = new Point[m];
      System.arraycopy(this.offsets, 0, arrayOfPoint, 0, m);
      this.offsets = arrayOfPoint;
      String[] arrayOfString = new String[m];
      System.arraycopy(this.ids, 0, arrayOfString, 0, m);
      this.ids = arrayOfString;
      int[] arrayOfInt = new int[m + 1];
      System.arraycopy(this.mnemonics, 0, arrayOfInt, 0, m + 1);
      this.mnemonics = arrayOfInt;
    }
    return localStringBuffer.toString();
  }

  int parseMnemonics(char[] paramArrayOfChar, int paramInt1, int paramInt2, StringBuffer paramStringBuffer)
  {
    int i = -1;
    for (int j = paramInt1; j < paramInt2; j++)
      if (paramArrayOfChar[j] == '&')
      {
        if ((j + 1 < paramInt2) && (paramArrayOfChar[(j + 1)] == '&'))
        {
          paramStringBuffer.append(paramArrayOfChar[j]);
          j++;
        }
        else
        {
          i = paramStringBuffer.length();
        }
      }
      else
        paramStringBuffer.append(paramArrayOfChar[j]);
    return i;
  }

  void releaseWidget()
  {
    super.releaseWidget();
    if (this.layout != null)
      this.layout.dispose();
    this.layout = null;
    if (this.linkColor != null)
      this.linkColor.dispose();
    this.linkColor = null;
    this.disabledColor = null;
    this.offsets = null;
    this.ids = null;
    this.mnemonics = null;
    this.text = null;
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

  public void setText(String paramString)
  {
    checkWidget();
    if (paramString == null)
      error(4);
    if (paramString.equals(this.text))
      return;
    this.text = paramString;
    if (OS.COMCTL32_MAJOR >= 6)
    {
      boolean bool1 = OS.IsWindowEnabled(this.handle);
      if (paramString.length() == 0)
        paramString = " ";
      TCHAR localTCHAR = new TCHAR(getCodePage(), paramString, true);
      OS.SetWindowText(this.handle, localTCHAR);
      parse(this.text);
      enableWidget(bool1);
    }
    else
    {
      this.layout.setText(parse(this.text));
      this.focusIndex = (this.offsets.length > 0 ? 0 : -1);
      this.selection.x = (this.selection.y = -1);
      int i = OS.GetWindowLong(this.handle, -16);
      if (this.offsets.length > 0)
        i |= 65536;
      else
        i &= -65537;
      OS.SetWindowLong(this.handle, -16, i);
      boolean bool2 = OS.IsWindowEnabled(this.handle);
      TextStyle localTextStyle1 = new TextStyle(null, bool2 ? this.linkColor : this.disabledColor, null);
      localTextStyle1.underline = true;
      for (int j = 0; j < this.offsets.length; j++)
      {
        Point localPoint = this.offsets[j];
        this.layout.setStyle(localTextStyle1, localPoint.x, localPoint.y);
      }
      TextStyle localTextStyle2 = new TextStyle(null, null, null);
      localTextStyle2.underline = true;
      for (int k = 0; k < this.mnemonics.length; k++)
      {
        int m = this.mnemonics[k];
        if (m != -1)
          this.layout.setStyle(localTextStyle2, m, m);
      }
      redraw();
    }
  }

  int widgetStyle()
  {
    int i = super.widgetStyle();
    return i | 0x10000;
  }

  TCHAR windowClass()
  {
    return OS.COMCTL32_MAJOR >= 6 ? LinkClass : this.display.windowClass;
  }

  int windowProc()
  {
    return LinkProc != 0 ? LinkProc : this.display.windowProc;
  }

  LRESULT WM_CHAR(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_CHAR(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    if (OS.COMCTL32_MAJOR < 6)
    {
      if (this.focusIndex == -1)
        return localLRESULT;
      switch (paramInt1)
      {
      case 13:
      case 32:
        Event localEvent = new Event();
        localEvent.text = this.ids[this.focusIndex];
        sendSelectionEvent(13, localEvent, true);
        break;
      case 9:
        int j = OS.GetKeyState(16) >= 0 ? 1 : 0;
        if (j != 0)
        {
          if (this.focusIndex < this.offsets.length - 1)
          {
            this.focusIndex += 1;
            redraw();
          }
        }
        else if (this.focusIndex > 0)
        {
          this.focusIndex -= 1;
          redraw();
        }
        break;
      default:
        break;
      }
    }
    else
    {
      switch (paramInt1)
      {
      case 9:
      case 13:
      case 32:
        int i = callWindowProc(this.handle, 256, paramInt1, paramInt2);
        return new LRESULT(i);
      }
    }
    return localLRESULT;
  }

  LRESULT WM_GETDLGCODE(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_GETDLGCODE(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    int k = 0;
    int i;
    int j;
    if (OS.COMCTL32_MAJOR >= 6)
    {
      LITEM localLITEM = new LITEM();
      localLITEM.mask = 3;
      localLITEM.stateMask = 1;
      i = 0;
      while (OS.SendMessage(this.handle, 1795, 0, localLITEM) != 0)
      {
        if ((localLITEM.state & 0x1) != 0)
          i = localLITEM.iLink;
        localLITEM.iLink += 1;
      }
      j = localLITEM.iLink;
      k = callWindowProc(this.handle, 135, paramInt1, paramInt2);
    }
    else
    {
      i = this.focusIndex;
      j = this.offsets.length;
    }
    if (j == 0)
      return new LRESULT(k | 0x100);
    int m = OS.GetKeyState(16) >= 0 ? 1 : 0;
    if ((m != 0) && (i < j - 1))
      return new LRESULT(k | 0x2);
    if ((m == 0) && (i > 0))
      return new LRESULT(k | 0x2);
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
    if (this.font == 0)
      this.font = defaultFont();
    return new LRESULT(this.font);
  }

  LRESULT WM_KEYDOWN(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_KEYDOWN(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    if (OS.COMCTL32_MAJOR >= 6)
      switch (paramInt1)
      {
      case 9:
      case 13:
      case 32:
        return LRESULT.ZERO;
      }
    return localLRESULT;
  }

  LRESULT WM_KILLFOCUS(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_KILLFOCUS(paramInt1, paramInt2);
    if (OS.COMCTL32_MAJOR < 6)
      redraw();
    return localLRESULT;
  }

  LRESULT WM_LBUTTONDOWN(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_LBUTTONDOWN(paramInt1, paramInt2);
    if (localLRESULT == LRESULT.ZERO)
      return localLRESULT;
    if (OS.COMCTL32_MAJOR < 6)
    {
      if (this.focusIndex != -1)
        setFocus();
      int i = OS.GET_X_LPARAM(paramInt2);
      int j = OS.GET_Y_LPARAM(paramInt2);
      int k = this.layout.getOffset(i, j, null);
      int m = this.selection.x;
      int n = this.selection.y;
      this.selection.x = k;
      this.selection.y = -1;
      if ((m != -1) && (n != -1))
      {
        if (m > n)
        {
          int i1 = m;
          m = n;
          n = i1;
        }
        Rectangle localRectangle1 = this.layout.getBounds(m, n);
        redraw(localRectangle1.x, localRectangle1.y, localRectangle1.width, localRectangle1.height, false);
      }
      for (int i2 = 0; i2 < this.offsets.length; i2++)
      {
        Rectangle[] arrayOfRectangle = getRectangles(i2);
        for (int i3 = 0; i3 < arrayOfRectangle.length; i3++)
        {
          Rectangle localRectangle2 = arrayOfRectangle[i3];
          if (localRectangle2.contains(i, j))
          {
            if (i2 != this.focusIndex)
              redraw();
            this.focusIndex = (this.mouseDownIndex = i2);
            return localLRESULT;
          }
        }
      }
    }
    return localLRESULT;
  }

  LRESULT WM_LBUTTONUP(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_LBUTTONUP(paramInt1, paramInt2);
    if (localLRESULT == LRESULT.ZERO)
      return localLRESULT;
    if (OS.COMCTL32_MAJOR < 6)
    {
      if (this.mouseDownIndex == -1)
        return localLRESULT;
      int i = OS.GET_X_LPARAM(paramInt2);
      int j = OS.GET_Y_LPARAM(paramInt2);
      Rectangle[] arrayOfRectangle = getRectangles(this.mouseDownIndex);
      for (int k = 0; k < arrayOfRectangle.length; k++)
      {
        Rectangle localRectangle = arrayOfRectangle[k];
        if (localRectangle.contains(i, j))
        {
          Event localEvent = new Event();
          localEvent.text = this.ids[this.mouseDownIndex];
          sendSelectionEvent(13, localEvent, true);
          break;
        }
      }
    }
    this.mouseDownIndex = -1;
    return localLRESULT;
  }

  LRESULT WM_NCHITTEST(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_NCHITTEST(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    if (OS.COMCTL32_MAJOR >= 6)
      return new LRESULT(1);
    return localLRESULT;
  }

  LRESULT WM_MOUSEMOVE(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_MOUSEMOVE(paramInt1, paramInt2);
    if (OS.COMCTL32_MAJOR < 6)
    {
      int i = OS.GET_X_LPARAM(paramInt2);
      int j = OS.GET_Y_LPARAM(paramInt2);
      int k;
      if (OS.GetKeyState(1) < 0)
      {
        k = this.selection.y;
        this.selection.y = this.layout.getOffset(i, j, null);
        if (this.selection.y != k)
        {
          int m = this.selection.y;
          if (k > m)
          {
            int n = k;
            k = m;
            m = n;
          }
          Rectangle localRectangle1 = this.layout.getBounds(k, m);
          redraw(localRectangle1.x, localRectangle1.y, localRectangle1.width, localRectangle1.height, false);
        }
      }
      else
      {
        for (k = 0; k < this.offsets.length; k++)
        {
          Rectangle[] arrayOfRectangle = getRectangles(k);
          for (int i1 = 0; i1 < arrayOfRectangle.length; i1++)
          {
            Rectangle localRectangle2 = arrayOfRectangle[i1];
            if (localRectangle2.contains(i, j))
            {
              setCursor(this.display.getSystemCursor(21));
              return localLRESULT;
            }
          }
        }
        setCursor(null);
      }
    }
    return localLRESULT;
  }

  LRESULT WM_PAINT(int paramInt1, int paramInt2)
  {
    if (OS.COMCTL32_MAJOR >= 6)
      return super.WM_PAINT(paramInt1, paramInt2);
    PAINTSTRUCT localPAINTSTRUCT = new PAINTSTRUCT();
    GCData localGCData = new GCData();
    localGCData.ps = localPAINTSTRUCT;
    localGCData.hwnd = this.handle;
    GC localGC = new_GC(localGCData);
    if (localGC != null)
    {
      int i = localPAINTSTRUCT.right - localPAINTSTRUCT.left;
      int j = localPAINTSTRUCT.bottom - localPAINTSTRUCT.top;
      if ((i != 0) && (j != 0))
      {
        RECT localRECT = new RECT();
        OS.SetRect(localRECT, localPAINTSTRUCT.left, localPAINTSTRUCT.top, localPAINTSTRUCT.right, localPAINTSTRUCT.bottom);
        drawWidget(localGC, localRECT);
      }
      localGC.dispose();
    }
    return LRESULT.ZERO;
  }

  LRESULT WM_PRINTCLIENT(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_PRINTCLIENT(paramInt1, paramInt2);
    if (OS.COMCTL32_MAJOR < 6)
    {
      RECT localRECT = new RECT();
      OS.GetClientRect(this.handle, localRECT);
      GCData localGCData = new GCData();
      localGCData.device = this.display;
      localGCData.foreground = getForegroundPixel();
      GC localGC = GC.win32_new(paramInt1, localGCData);
      drawWidget(localGC, localRECT);
      localGC.dispose();
    }
    return localLRESULT;
  }

  LRESULT WM_SETFOCUS(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_SETFOCUS(paramInt1, paramInt2);
    if (OS.COMCTL32_MAJOR < 6)
      redraw();
    return localLRESULT;
  }

  LRESULT WM_SETFONT(int paramInt1, int paramInt2)
  {
    if (OS.COMCTL32_MAJOR < 6)
      this.layout.setFont(Font.win32_new(this.display, paramInt1));
    if (paramInt2 != 0)
      OS.InvalidateRect(this.handle, null, true);
    return super.WM_SETFONT(this.font = paramInt1, paramInt2);
  }

  LRESULT WM_SIZE(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_SIZE(paramInt1, paramInt2);
    if (OS.COMCTL32_MAJOR < 6)
    {
      RECT localRECT = new RECT();
      OS.GetClientRect(this.handle, localRECT);
      this.layout.setWidth(localRECT.right > 0 ? localRECT.right : -1);
      redraw();
    }
    return localLRESULT;
  }

  LRESULT wmColorChild(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.wmColorChild(paramInt1, paramInt2);
    if ((OS.COMCTL32_MAJOR >= 6) && (!OS.IsWindowEnabled(this.handle)))
    {
      OS.SetTextColor(paramInt1, OS.GetSysColor(OS.COLOR_GRAYTEXT));
      if (localLRESULT == null)
      {
        int i = getBackgroundPixel();
        OS.SetBkColor(paramInt1, i);
        int j = findBrush(i, 0);
        return new LRESULT(j);
      }
    }
    return localLRESULT;
  }

  LRESULT wmNotifyChild(NMHDR paramNMHDR, int paramInt1, int paramInt2)
  {
    if (OS.COMCTL32_MAJOR >= 6)
      switch (paramNMHDR.code)
      {
      case -4:
      case -2:
        NMLINK localNMLINK = new NMLINK();
        OS.MoveMemory(localNMLINK, paramInt2, NMLINK.sizeof);
        Event localEvent = new Event();
        localEvent.text = this.ids[localNMLINK.iLink];
        sendSelectionEvent(13, localEvent, true);
      case -3:
      }
    return super.wmNotifyChild(paramNMHDR, paramInt1, paramInt2);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.Link
 * JD-Core Version:    0.6.2
 */