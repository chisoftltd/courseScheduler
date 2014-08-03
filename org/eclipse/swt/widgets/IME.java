package org.eclipse.swt.widgets;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.TF_DA_COLOR;
import org.eclipse.swt.internal.win32.TF_DISPLAYATTRIBUTE;

public class IME extends Widget
{
  Canvas parent;
  int caretOffset;
  int startOffset;
  int commitCount;
  String text;
  int[] ranges;
  TextStyle[] styles;
  static final int WM_MSIME_MOUSE = OS.RegisterWindowMessage(new TCHAR(0, "MSIMEMouseOperation", true));
  static final byte[] IID_ITfInputProcessorProfiles = new byte[16];
  static final byte[] IID_ITfDisplayAttributeProvider = new byte[16];
  static final byte[] CLSID_TF_InputProcessorProfiles = new byte[16];
  static final byte[] GUID_TFCAT_TIP_KEYBOARD = new byte[16];
  static final int UNDERLINE_IME_DOT = 65536;
  static final int UNDERLINE_IME_DASH = 131072;
  static final int UNDERLINE_IME_THICK = 196608;

  static
  {
    OS.IIDFromString("".toCharArray(), IID_ITfInputProcessorProfiles);
    OS.IIDFromString("".toCharArray(), IID_ITfDisplayAttributeProvider);
    OS.IIDFromString("".toCharArray(), CLSID_TF_InputProcessorProfiles);
    OS.IIDFromString("".toCharArray(), GUID_TFCAT_TIP_KEYBOARD);
  }

  IME()
  {
  }

  public IME(Canvas paramCanvas, int paramInt)
  {
    super(paramCanvas, paramInt);
    this.parent = paramCanvas;
    createWidget();
  }

  void createWidget()
  {
    this.text = "";
    this.startOffset = -1;
    if (this.parent.getIME() == null)
      this.parent.setIME(this);
  }

  public int getCaretOffset()
  {
    checkWidget();
    return this.startOffset + this.caretOffset;
  }

  public int getCommitCount()
  {
    checkWidget();
    return this.commitCount;
  }

  public int getCompositionOffset()
  {
    checkWidget();
    return this.startOffset;
  }

  TF_DISPLAYATTRIBUTE getDisplayAttribute(short paramShort, int paramInt)
  {
    int[] arrayOfInt1 = new int[1];
    int i = OS.CoCreateInstance(CLSID_TF_InputProcessorProfiles, 0, 1, IID_ITfInputProcessorProfiles, arrayOfInt1);
    Object localObject = null;
    if (i == 0)
    {
      byte[] arrayOfByte1 = new byte[16];
      byte[] arrayOfByte2 = new byte[16];
      i = OS.VtblCall(8, arrayOfInt1[0], paramShort, GUID_TFCAT_TIP_KEYBOARD, arrayOfByte1, arrayOfByte2);
      if (i == 0)
      {
        int[] arrayOfInt2 = new int[1];
        i = OS.CoCreateInstance(arrayOfByte1, 0, 1, IID_ITfDisplayAttributeProvider, arrayOfInt2);
        if (i == 0)
        {
          int[] arrayOfInt3 = new int[1];
          i = OS.VtblCall(3, arrayOfInt2[0], arrayOfInt3);
          if (i == 0)
          {
            int[] arrayOfInt4 = new int[1];
            TF_DISPLAYATTRIBUTE localTF_DISPLAYATTRIBUTE = new TF_DISPLAYATTRIBUTE();
            while ((i = OS.VtblCall(4, arrayOfInt3[0], 1, arrayOfInt4, null)) == 0)
            {
              OS.VtblCall(5, arrayOfInt4[0], localTF_DISPLAYATTRIBUTE);
              OS.VtblCall(2, arrayOfInt4[0]);
              if (localTF_DISPLAYATTRIBUTE.bAttr == paramInt)
              {
                localObject = localTF_DISPLAYATTRIBUTE;
                break;
              }
            }
            i = OS.VtblCall(2, arrayOfInt3[0]);
          }
          i = OS.VtblCall(2, arrayOfInt2[0]);
        }
      }
      i = OS.VtblCall(2, arrayOfInt1[0]);
    }
    if (localObject == null)
    {
      localObject = new TF_DISPLAYATTRIBUTE();
      switch (paramInt)
      {
      case 0:
        ((TF_DISPLAYATTRIBUTE)localObject).lsStyle = 4;
        break;
      case 1:
      case 2:
        ((TF_DISPLAYATTRIBUTE)localObject).lsStyle = 1;
        ((TF_DISPLAYATTRIBUTE)localObject).fBoldLine = (paramInt == 1);
      }
    }
    return localObject;
  }

  public int[] getRanges()
  {
    checkWidget();
    if (this.ranges == null)
      return new int[0];
    int[] arrayOfInt = new int[this.ranges.length];
    for (int i = 0; i < arrayOfInt.length; i++)
      arrayOfInt[i] = (this.ranges[i] + this.startOffset);
    return arrayOfInt;
  }

  public TextStyle[] getStyles()
  {
    checkWidget();
    if (this.styles == null)
      return new TextStyle[0];
    TextStyle[] arrayOfTextStyle = new TextStyle[this.styles.length];
    System.arraycopy(this.styles, 0, arrayOfTextStyle, 0, this.styles.length);
    return arrayOfTextStyle;
  }

  public String getText()
  {
    checkWidget();
    return this.text;
  }

  public boolean getWideCaret()
  {
    checkWidget();
    int i = OS.GetKeyboardLayout(0);
    int j = (short)OS.LOWORD(i);
    return OS.PRIMARYLANGID(j) == 18;
  }

  boolean isInlineEnabled()
  {
    if ((OS.IsWinCE) || (OS.WIN32_VERSION < OS.VERSION(5, 1)))
      return false;
    return (OS.IsDBLocale) && (hooks(43));
  }

  void releaseParent()
  {
    super.releaseParent();
    if (this == this.parent.getIME())
      this.parent.setIME(null);
  }

  void releaseWidget()
  {
    super.releaseWidget();
    this.parent = null;
    this.text = null;
    this.styles = null;
    this.ranges = null;
  }

  public void setCompositionOffset(int paramInt)
  {
    checkWidget();
    if (paramInt < 0)
      return;
    if (this.startOffset != -1)
      this.startOffset = paramInt;
  }

  LRESULT WM_IME_COMPOSITION(int paramInt1, int paramInt2)
  {
    if (!isInlineEnabled())
      return null;
    this.ranges = null;
    this.styles = null;
    this.caretOffset = (this.commitCount = 0);
    int i = this.parent.handle;
    int j = OS.ImmGetContext(i);
    int k = this.parent.getCodePage();
    if (j != 0)
    {
      TCHAR localTCHAR = null;
      Object localObject2;
      int i1;
      if ((paramInt2 & 0x800) != 0)
      {
        m = OS.ImmGetCompositionString(j, 2048, null, 0);
        if (m > 0)
        {
          localTCHAR = new TCHAR(k, m / TCHAR.sizeof);
          OS.ImmGetCompositionString(j, 2048, localTCHAR, m);
          if (this.startOffset == -1)
          {
            localObject1 = new Event();
            ((Event)localObject1).detail = 3;
            sendEvent(43, (Event)localObject1);
            this.startOffset = ((Event)localObject1).start;
          }
          localObject1 = new Event();
          ((Event)localObject1).detail = 1;
          ((Event)localObject1).start = this.startOffset;
          ((Event)localObject1).end = (this.startOffset + this.text.length());
          ((Event)localObject1).text = (this.text = localTCHAR != null ? localTCHAR.toString() : "");
          this.commitCount = this.text.length();
          sendEvent(43, (Event)localObject1);
          localObject2 = this.text;
          this.text = "";
          this.startOffset = -1;
          this.commitCount = 0;
          if (((Event)localObject1).doit)
          {
            Display localDisplay = this.display;
            localDisplay.lastKey = 0;
            localDisplay.lastVirtual = (localDisplay.lastNull = localDisplay.lastDead = 0);
            m = ((String)localObject2).length();
            for (i1 = 0; i1 < m; i1++)
            {
              char c = ((String)localObject2).charAt(i1);
              localDisplay.lastAscii = c;
              localObject1 = new Event();
              ((Event)localObject1).character = c;
              this.parent.sendEvent(1, (Event)localObject1);
            }
          }
        }
        if ((paramInt2 & 0x8) == 0)
          return LRESULT.ONE;
      }
      localTCHAR = null;
      if ((paramInt2 & 0x8) != 0)
      {
        m = OS.ImmGetCompositionString(j, 8, null, 0);
        if (m > 0)
        {
          localTCHAR = new TCHAR(k, m / TCHAR.sizeof);
          OS.ImmGetCompositionString(j, 8, localTCHAR, m);
          if ((paramInt2 & 0x80) != 0)
            this.caretOffset = OS.ImmGetCompositionString(j, 128, null, 0);
          localObject1 = (int[])null;
          if ((paramInt2 & 0x20) != 0)
          {
            m = OS.ImmGetCompositionString(j, 32, null, 0);
            if (m > 0)
            {
              localObject1 = new int[m / 4];
              OS.ImmGetCompositionString(j, 32, (int[])localObject1, m);
            }
          }
          if (((paramInt2 & 0x10) != 0) && (localObject1 != null))
          {
            m = OS.ImmGetCompositionString(j, 16, null, 0);
            if (m > 0)
            {
              localObject2 = new byte[m];
              OS.ImmGetCompositionString(j, 16, (byte[])localObject2, m);
              m = localObject1.length - 1;
              this.ranges = new int[m * 2];
              this.styles = new TextStyle[m];
              int n = OS.GetKeyboardLayout(0);
              i1 = (short)OS.LOWORD(n);
              TF_DISPLAYATTRIBUTE localTF_DISPLAYATTRIBUTE = null;
              TextStyle localTextStyle = null;
              for (int i2 = 0; i2 < m; i2++)
              {
                this.ranges[(i2 * 2)] = localObject1[i2];
                this.ranges[(i2 * 2 + 1)] = (localObject1[(i2 + 1)] - 1);
                void tmp647_644 = new TextStyle();
                localTextStyle = tmp647_644;
                this.styles[i2] = tmp647_644;
                localTF_DISPLAYATTRIBUTE = getDisplayAttribute(i1, localObject2[localObject1[i2]]);
                if (localTF_DISPLAYATTRIBUTE != null)
                {
                  int i3;
                  switch (localTF_DISPLAYATTRIBUTE.crText.type)
                  {
                  case 2:
                    localTextStyle.foreground = Color.win32_new(this.display, localTF_DISPLAYATTRIBUTE.crText.cr);
                    break;
                  case 1:
                    i3 = OS.GetSysColor(localTF_DISPLAYATTRIBUTE.crText.cr);
                    localTextStyle.foreground = Color.win32_new(this.display, i3);
                  }
                  switch (localTF_DISPLAYATTRIBUTE.crBk.type)
                  {
                  case 2:
                    localTextStyle.background = Color.win32_new(this.display, localTF_DISPLAYATTRIBUTE.crBk.cr);
                    break;
                  case 1:
                    i3 = OS.GetSysColor(localTF_DISPLAYATTRIBUTE.crBk.cr);
                    localTextStyle.background = Color.win32_new(this.display, i3);
                  }
                  switch (localTF_DISPLAYATTRIBUTE.crLine.type)
                  {
                  case 2:
                    localTextStyle.underlineColor = Color.win32_new(this.display, localTF_DISPLAYATTRIBUTE.crLine.cr);
                    break;
                  case 1:
                    i3 = OS.GetSysColor(localTF_DISPLAYATTRIBUTE.crLine.cr);
                    localTextStyle.underlineColor = Color.win32_new(this.display, i3);
                  }
                  localTextStyle.underline = (localTF_DISPLAYATTRIBUTE.lsStyle != 0);
                  switch (localTF_DISPLAYATTRIBUTE.lsStyle)
                  {
                  case 4:
                    localTextStyle.underlineStyle = 3;
                    break;
                  case 3:
                    localTextStyle.underlineStyle = 131072;
                    break;
                  case 2:
                    localTextStyle.underlineStyle = 65536;
                    break;
                  case 1:
                    localTextStyle.underlineStyle = (localTF_DISPLAYATTRIBUTE.fBoldLine ? 196608 : 0);
                  }
                }
              }
            }
          }
        }
        OS.ImmReleaseContext(i, j);
      }
      int m = this.startOffset + this.text.length();
      if (this.startOffset == -1)
      {
        localObject1 = new Event();
        ((Event)localObject1).detail = 3;
        sendEvent(43, (Event)localObject1);
        this.startOffset = ((Event)localObject1).start;
        m = ((Event)localObject1).end;
      }
      Object localObject1 = new Event();
      ((Event)localObject1).detail = 1;
      ((Event)localObject1).start = this.startOffset;
      ((Event)localObject1).end = m;
      ((Event)localObject1).text = (this.text = localTCHAR != null ? localTCHAR.toString() : "");
      sendEvent(43, (Event)localObject1);
      if (this.text.length() == 0)
      {
        this.startOffset = -1;
        this.ranges = null;
        this.styles = null;
      }
    }
    return LRESULT.ONE;
  }

  LRESULT WM_IME_COMPOSITION_START(int paramInt1, int paramInt2)
  {
    return isInlineEnabled() ? LRESULT.ONE : null;
  }

  LRESULT WM_IME_ENDCOMPOSITION(int paramInt1, int paramInt2)
  {
    return isInlineEnabled() ? LRESULT.ONE : null;
  }

  LRESULT WM_KILLFOCUS(int paramInt1, int paramInt2)
  {
    if (!isInlineEnabled())
      return null;
    int i = this.parent.handle;
    int j = OS.ImmGetContext(i);
    if (j != 0)
    {
      if (OS.ImmGetOpenStatus(j))
        OS.ImmNotifyIME(j, 21, 1, 0);
      OS.ImmReleaseContext(i, j);
    }
    return null;
  }

  LRESULT WM_LBUTTONDOWN(int paramInt1, int paramInt2)
  {
    if (!isInlineEnabled())
      return null;
    int i = this.parent.handle;
    int j = OS.ImmGetContext(i);
    if (j != 0)
    {
      if ((OS.ImmGetOpenStatus(j)) && (OS.ImmGetCompositionString(j, 8, null, 0) > 0))
      {
        Event localEvent = new Event();
        localEvent.detail = 2;
        localEvent.x = OS.GET_X_LPARAM(paramInt2);
        localEvent.y = OS.GET_Y_LPARAM(paramInt2);
        sendEvent(43, localEvent);
        int k = localEvent.index;
        int m = this.text.length();
        if ((k != -1) && (this.startOffset != -1) && (this.startOffset <= k) && (k < this.startOffset + m))
        {
          int n = OS.ImmGetDefaultIMEWnd(i);
          k = localEvent.index + localEvent.count - this.startOffset;
          int i1 = localEvent.count > 0 ? 1 : 2;
          int i2 = OS.MAKEWPARAM(OS.MAKEWORD(1, i1), k);
          OS.SendMessage(n, WM_MSIME_MOUSE, i2, j);
        }
        else
        {
          OS.ImmNotifyIME(j, 21, 1, 0);
        }
      }
      OS.ImmReleaseContext(i, j);
    }
    return null;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.IME
 * JD-Core Version:    0.6.2
 */