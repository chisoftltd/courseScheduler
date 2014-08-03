package org.eclipse.swt.widgets;

import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.win32.COMPOSITIONFORM;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.TCHAR;

public class Canvas extends Composite
{
  Caret caret;
  IME ime;

  Canvas()
  {
  }

  public Canvas(Composite paramComposite, int paramInt)
  {
    super(paramComposite, paramInt);
  }

  void clearArea(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    checkWidget();
    if (OS.IsWindowVisible(this.handle))
    {
      RECT localRECT = new RECT();
      OS.SetRect(localRECT, paramInt1, paramInt2, paramInt1 + paramInt3, paramInt2 + paramInt4);
      int i = OS.GetDCEx(this.handle, 0, 26);
      drawBackground(i, localRECT);
      OS.ReleaseDC(this.handle, i);
    }
  }

  public void drawBackground(GC paramGC, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    drawBackground(paramGC, paramInt1, paramInt2, paramInt3, paramInt4, 0, 0);
  }

  public Caret getCaret()
  {
    checkWidget();
    return this.caret;
  }

  public IME getIME()
  {
    checkWidget();
    return this.ime;
  }

  void releaseChildren(boolean paramBoolean)
  {
    if (this.caret != null)
    {
      this.caret.release(false);
      this.caret = null;
    }
    if (this.ime != null)
    {
      this.ime.release(false);
      this.ime = null;
    }
    super.releaseChildren(paramBoolean);
  }

  void reskinChildren(int paramInt)
  {
    if (this.caret != null)
      this.caret.reskin(paramInt);
    if (this.ime != null)
      this.ime.reskin(paramInt);
    super.reskinChildren(paramInt);
  }

  public void scroll(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, boolean paramBoolean)
  {
    checkWidget();
    forceResize();
    int i = (this.caret != null) && (this.caret.isFocusCaret()) ? 1 : 0;
    if (i != 0)
      this.caret.killFocus();
    RECT localRECT1 = new RECT();
    OS.SetRect(localRECT1, paramInt3, paramInt4, paramInt3 + paramInt5, paramInt4 + paramInt6);
    RECT localRECT2 = new RECT();
    OS.GetClientRect(this.handle, localRECT2);
    if (OS.IntersectRect(localRECT2, localRECT1, localRECT2))
      if (OS.IsWinCE)
      {
        OS.UpdateWindow(this.handle);
      }
      else
      {
        j = 384;
        OS.RedrawWindow(this.handle, null, 0, j);
      }
    int j = paramInt1 - paramInt3;
    int k = paramInt2 - paramInt4;
    int m;
    if (findImageControl() != null)
    {
      if (OS.IsWinCE)
      {
        OS.InvalidateRect(this.handle, localRECT1, true);
      }
      else
      {
        m = 1029;
        if (paramBoolean)
          m |= 128;
        OS.RedrawWindow(this.handle, localRECT1, 0, m);
      }
      OS.OffsetRect(localRECT1, j, k);
      if (OS.IsWinCE)
      {
        OS.InvalidateRect(this.handle, localRECT1, true);
      }
      else
      {
        m = 1029;
        if (paramBoolean)
          m |= 128;
        OS.RedrawWindow(this.handle, localRECT1, 0, m);
      }
    }
    else
    {
      m = 6;
      OS.ScrollWindowEx(this.handle, j, k, localRECT1, null, 0, null, m);
    }
    if (paramBoolean)
    {
      Control[] arrayOfControl = _getChildren();
      for (int n = 0; n < arrayOfControl.length; n++)
      {
        Control localControl = arrayOfControl[n];
        Rectangle localRectangle = localControl.getBounds();
        if ((Math.min(paramInt3 + paramInt5, localRectangle.x + localRectangle.width) >= Math.max(paramInt3, localRectangle.x)) && (Math.min(paramInt4 + paramInt6, localRectangle.y + localRectangle.height) >= Math.max(paramInt4, localRectangle.y)))
          localControl.setLocation(localRectangle.x + j, localRectangle.y + k);
      }
    }
    if (i != 0)
      this.caret.setFocus();
  }

  public void setCaret(Caret paramCaret)
  {
    checkWidget();
    Caret localCaret1 = paramCaret;
    Caret localCaret2 = this.caret;
    this.caret = localCaret1;
    if (hasFocus())
    {
      if (localCaret2 != null)
        localCaret2.killFocus();
      if (localCaret1 != null)
      {
        if (localCaret1.isDisposed())
          error(5);
        localCaret1.setFocus();
      }
    }
  }

  public void setFont(Font paramFont)
  {
    checkWidget();
    if (this.caret != null)
      this.caret.setFont(paramFont);
    super.setFont(paramFont);
  }

  public void setIME(IME paramIME)
  {
    checkWidget();
    if ((paramIME != null) && (paramIME.isDisposed()))
      error(5);
    this.ime = paramIME;
  }

  TCHAR windowClass()
  {
    if (this.display.useOwnDC)
      return this.display.windowOwnDCClass;
    return super.windowClass();
  }

  int windowProc(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((paramInt2 == Display.SWT_RESTORECARET) && ((this.state & 0x2) != 0) && (this.caret != null))
    {
      this.caret.killFocus();
      this.caret.setFocus();
      return 1;
    }
    return super.windowProc(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  LRESULT WM_CHAR(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_CHAR(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    if (this.caret != null)
      switch (paramInt1)
      {
      case 8:
      case 27:
      case 127:
        break;
      default:
        if (OS.GetKeyState(17) >= 0)
        {
          int[] arrayOfInt = new int[1];
          if ((OS.SystemParametersInfo(4128, 0, arrayOfInt, 0)) && (arrayOfInt[0] != 0))
            OS.SetCursor(0);
        }
        break;
      }
    return localLRESULT;
  }

  LRESULT WM_IME_COMPOSITION(int paramInt1, int paramInt2)
  {
    if (this.ime != null)
    {
      LRESULT localLRESULT = this.ime.WM_IME_COMPOSITION(paramInt1, paramInt2);
      if (localLRESULT != null)
        return localLRESULT;
    }
    if ((!OS.IsWinCE) && (OS.WIN32_VERSION == OS.VERSION(5, 1)) && (OS.IsDBLocale))
    {
      int i = OS.GetSystemDefaultUILanguage();
      int j = OS.PRIMARYLANGID(i);
      if ((j == 18) && (this.caret != null) && (this.caret.isFocusCaret()))
      {
        POINT localPOINT = new POINT();
        if (OS.GetCaretPos(localPOINT))
        {
          COMPOSITIONFORM localCOMPOSITIONFORM = new COMPOSITIONFORM();
          localCOMPOSITIONFORM.dwStyle = 2;
          localCOMPOSITIONFORM.x = localPOINT.x;
          localCOMPOSITIONFORM.y = localPOINT.y;
          int k = OS.ImmGetContext(this.handle);
          OS.ImmSetCompositionWindow(k, localCOMPOSITIONFORM);
          OS.ImmReleaseContext(this.handle, k);
        }
      }
    }
    return super.WM_IME_COMPOSITION(paramInt1, paramInt2);
  }

  LRESULT WM_IME_COMPOSITION_START(int paramInt1, int paramInt2)
  {
    if (this.ime != null)
    {
      LRESULT localLRESULT = this.ime.WM_IME_COMPOSITION_START(paramInt1, paramInt2);
      if (localLRESULT != null)
        return localLRESULT;
    }
    return super.WM_IME_COMPOSITION_START(paramInt1, paramInt2);
  }

  LRESULT WM_IME_ENDCOMPOSITION(int paramInt1, int paramInt2)
  {
    if (this.ime != null)
    {
      LRESULT localLRESULT = this.ime.WM_IME_ENDCOMPOSITION(paramInt1, paramInt2);
      if (localLRESULT != null)
        return localLRESULT;
    }
    return super.WM_IME_ENDCOMPOSITION(paramInt1, paramInt2);
  }

  LRESULT WM_INPUTLANGCHANGE(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_INPUTLANGCHANGE(paramInt1, paramInt2);
    if ((this.caret != null) && (this.caret.isFocusCaret()))
    {
      this.caret.setIMEFont();
      this.caret.resizeIME();
    }
    return localLRESULT;
  }

  LRESULT WM_KILLFOCUS(int paramInt1, int paramInt2)
  {
    if (this.ime != null)
    {
      localObject = this.ime.WM_KILLFOCUS(paramInt1, paramInt2);
      if (localObject != null)
        return localObject;
    }
    Object localObject = this.caret;
    LRESULT localLRESULT = super.WM_KILLFOCUS(paramInt1, paramInt2);
    if (localObject != null)
      ((Caret)localObject).killFocus();
    return localLRESULT;
  }

  LRESULT WM_LBUTTONDOWN(int paramInt1, int paramInt2)
  {
    if (this.ime != null)
    {
      LRESULT localLRESULT = this.ime.WM_LBUTTONDOWN(paramInt1, paramInt2);
      if (localLRESULT != null)
        return localLRESULT;
    }
    return super.WM_LBUTTONDOWN(paramInt1, paramInt2);
  }

  LRESULT WM_SETFOCUS(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_SETFOCUS(paramInt1, paramInt2);
    if ((this.caret != null) && (this.caret.isFocusCaret()))
      this.caret.setFocus();
    return localLRESULT;
  }

  LRESULT WM_SIZE(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_SIZE(paramInt1, paramInt2);
    if ((this.caret != null) && (this.caret.isFocusCaret()))
      this.caret.resizeIME();
    return localLRESULT;
  }

  LRESULT WM_WINDOWPOSCHANGED(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_WINDOWPOSCHANGED(paramInt1, paramInt2);
    int i = ((this.style & 0x4000000) != 0) && (this.caret != null) && (this.caret.isFocusCaret()) ? 1 : 0;
    if (i != 0)
      this.caret.setFocus();
    return localLRESULT;
  }

  LRESULT WM_WINDOWPOSCHANGING(int paramInt1, int paramInt2)
  {
    LRESULT localLRESULT = super.WM_WINDOWPOSCHANGING(paramInt1, paramInt2);
    if (localLRESULT != null)
      return localLRESULT;
    int i = ((this.style & 0x4000000) != 0) && (this.caret != null) && (this.caret.isFocusCaret()) ? 1 : 0;
    if (i != 0)
      this.caret.killFocus();
    return localLRESULT;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.Canvas
 * JD-Core Version:    0.6.2
 */