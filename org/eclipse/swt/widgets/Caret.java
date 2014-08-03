package org.eclipse.swt.widgets;

import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.win32.CANDIDATEFORM;
import org.eclipse.swt.internal.win32.COMPOSITIONFORM;
import org.eclipse.swt.internal.win32.LOGFONT;
import org.eclipse.swt.internal.win32.LOGFONTA;
import org.eclipse.swt.internal.win32.LOGFONTW;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.internal.win32.RECT;

public class Caret extends Widget
{
  Canvas parent;
  int x;
  int y;
  int width;
  int height;
  boolean moved;
  boolean resized;
  boolean isVisible;
  Image image;
  Font font;
  LOGFONT oldFont;

  public Caret(Canvas paramCanvas, int paramInt)
  {
    super(paramCanvas, paramInt);
    this.parent = paramCanvas;
    createWidget();
  }

  void createWidget()
  {
    this.isVisible = true;
    if (this.parent.getCaret() == null)
      this.parent.setCaret(this);
  }

  int defaultFont()
  {
    int i = this.parent.handle;
    int j = OS.ImmGetDefaultIMEWnd(i);
    int k = 0;
    if (j != 0)
      k = OS.SendMessage(j, 49, 0, 0);
    if (k == 0)
      k = OS.SendMessage(i, 49, 0, 0);
    if (k == 0)
      return this.parent.defaultFont();
    return k;
  }

  public Rectangle getBounds()
  {
    checkWidget();
    Object localObject;
    if (this.image != null)
    {
      localObject = this.image.getBounds();
      return new Rectangle(this.x, this.y, ((Rectangle)localObject).width, ((Rectangle)localObject).height);
    }
    if ((!OS.IsWinCE) && (this.width == 0))
    {
      localObject = new int[1];
      if (OS.SystemParametersInfo(8198, 0, (int[])localObject, 0))
        return new Rectangle(this.x, this.y, localObject[0], this.height);
    }
    return new Rectangle(this.x, this.y, this.width, this.height);
  }

  public Font getFont()
  {
    checkWidget();
    if (this.font == null)
    {
      int i = defaultFont();
      return Font.win32_new(this.display, i);
    }
    return this.font;
  }

  public Image getImage()
  {
    checkWidget();
    return this.image;
  }

  public Point getLocation()
  {
    checkWidget();
    return new Point(this.x, this.y);
  }

  public Canvas getParent()
  {
    checkWidget();
    return this.parent;
  }

  public Point getSize()
  {
    checkWidget();
    Object localObject;
    if (this.image != null)
    {
      localObject = this.image.getBounds();
      return new Point(((Rectangle)localObject).width, ((Rectangle)localObject).height);
    }
    if ((!OS.IsWinCE) && (this.width == 0))
    {
      localObject = new int[1];
      if (OS.SystemParametersInfo(8198, 0, (int[])localObject, 0))
        return new Point(localObject[0], this.height);
    }
    return new Point(this.width, this.height);
  }

  public boolean getVisible()
  {
    checkWidget();
    return this.isVisible;
  }

  boolean hasFocus()
  {
    return this.parent.handle == OS.GetFocus();
  }

  boolean isFocusCaret()
  {
    return (this.parent.caret == this) && (hasFocus());
  }

  public boolean isVisible()
  {
    checkWidget();
    return (this.isVisible) && (this.parent.isVisible()) && (hasFocus());
  }

  void killFocus()
  {
    OS.DestroyCaret();
    restoreIMEFont();
  }

  void move()
  {
    this.moved = false;
    if (!OS.SetCaretPos(this.x, this.y))
      return;
    resizeIME();
  }

  void resizeIME()
  {
    if (!OS.IsDBLocale)
      return;
    POINT localPOINT = new POINT();
    if (!OS.GetCaretPos(localPOINT))
      return;
    int i = this.parent.handle;
    int j = OS.ImmGetContext(i);
    IME localIME = this.parent.getIME();
    Object localObject1;
    Object localObject2;
    if ((localIME != null) && (localIME.isInlineEnabled()))
    {
      localObject1 = getSize();
      localObject2 = new CANDIDATEFORM();
      ((CANDIDATEFORM)localObject2).dwStyle = 128;
      ((CANDIDATEFORM)localObject2).ptCurrentPos = localPOINT;
      ((CANDIDATEFORM)localObject2).rcArea = new RECT();
      OS.SetRect(((CANDIDATEFORM)localObject2).rcArea, localPOINT.x, localPOINT.y, localPOINT.x + ((Point)localObject1).x, localPOINT.y + ((Point)localObject1).y);
      OS.ImmSetCandidateWindow(j, (CANDIDATEFORM)localObject2);
    }
    else
    {
      localObject1 = new RECT();
      OS.GetClientRect(i, (RECT)localObject1);
      localObject2 = new COMPOSITIONFORM();
      ((COMPOSITIONFORM)localObject2).dwStyle = 1;
      ((COMPOSITIONFORM)localObject2).x = localPOINT.x;
      ((COMPOSITIONFORM)localObject2).y = localPOINT.y;
      ((COMPOSITIONFORM)localObject2).left = ((RECT)localObject1).left;
      ((COMPOSITIONFORM)localObject2).right = ((RECT)localObject1).right;
      ((COMPOSITIONFORM)localObject2).top = ((RECT)localObject1).top;
      ((COMPOSITIONFORM)localObject2).bottom = ((RECT)localObject1).bottom;
      OS.ImmSetCompositionWindow(j, (COMPOSITIONFORM)localObject2);
    }
    OS.ImmReleaseContext(i, j);
  }

  void releaseParent()
  {
    super.releaseParent();
    if (this == this.parent.getCaret())
      this.parent.setCaret(null);
  }

  void releaseWidget()
  {
    super.releaseWidget();
    this.parent = null;
    this.image = null;
    this.font = null;
    this.oldFont = null;
  }

  void resize()
  {
    this.resized = false;
    int i = this.parent.handle;
    OS.DestroyCaret();
    int j = this.image != null ? this.image.handle : 0;
    int k = this.width;
    if ((!OS.IsWinCE) && (this.image == null) && (k == 0))
    {
      int[] arrayOfInt = new int[1];
      if (OS.SystemParametersInfo(8198, 0, arrayOfInt, 0))
        k = arrayOfInt[0];
    }
    OS.CreateCaret(i, j, k, this.height);
    OS.SetCaretPos(this.x, this.y);
    OS.ShowCaret(i);
    move();
  }

  void restoreIMEFont()
  {
    if (!OS.IsDBLocale)
      return;
    if (this.oldFont == null)
      return;
    int i = this.parent.handle;
    int j = OS.ImmGetContext(i);
    OS.ImmSetCompositionFont(j, this.oldFont);
    OS.ImmReleaseContext(i, j);
    this.oldFont = null;
  }

  public void setBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    checkWidget();
    int i = (this.x == paramInt1) && (this.y == paramInt2) ? 1 : 0;
    int j = (this.width == paramInt3) && (this.height == paramInt4) ? 1 : 0;
    if ((i != 0) && (j != 0))
      return;
    this.x = paramInt1;
    this.y = paramInt2;
    this.width = paramInt3;
    this.height = paramInt4;
    if (j != 0)
    {
      this.moved = true;
      if ((this.isVisible) && (hasFocus()))
        move();
    }
    else
    {
      this.resized = true;
      if ((this.isVisible) && (hasFocus()))
        resize();
    }
  }

  public void setBounds(Rectangle paramRectangle)
  {
    if (paramRectangle == null)
      error(4);
    setBounds(paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height);
  }

  void setFocus()
  {
    int i = this.parent.handle;
    int j = 0;
    if (this.image != null)
      j = this.image.handle;
    int k = this.width;
    if ((!OS.IsWinCE) && (this.image == null) && (k == 0))
    {
      int[] arrayOfInt = new int[1];
      if (OS.SystemParametersInfo(8198, 0, arrayOfInt, 0))
        k = arrayOfInt[0];
    }
    OS.CreateCaret(i, j, k, this.height);
    move();
    setIMEFont();
    if (this.isVisible)
      OS.ShowCaret(i);
  }

  public void setFont(Font paramFont)
  {
    checkWidget();
    if ((paramFont != null) && (paramFont.isDisposed()))
      error(5);
    this.font = paramFont;
    if (hasFocus())
      setIMEFont();
  }

  public void setImage(Image paramImage)
  {
    checkWidget();
    if ((paramImage != null) && (paramImage.isDisposed()))
      error(5);
    this.image = paramImage;
    if ((this.isVisible) && (hasFocus()))
      resize();
  }

  void setIMEFont()
  {
    if (!OS.IsDBLocale)
      return;
    int i = 0;
    if (this.font != null)
      i = this.font.handle;
    if (i == 0)
      i = defaultFont();
    int j = this.parent.handle;
    int k = OS.ImmGetContext(j);
    if (this.oldFont == null)
    {
      this.oldFont = (OS.IsUnicode ? new LOGFONTW() : new LOGFONTA());
      if (!OS.ImmGetCompositionFont(k, this.oldFont))
        this.oldFont = null;
    }
    LOGFONTA localLOGFONTA = OS.IsUnicode ? new LOGFONTW() : new LOGFONTA();
    if (OS.GetObject(i, LOGFONT.sizeof, localLOGFONTA) != 0)
      OS.ImmSetCompositionFont(k, localLOGFONTA);
    OS.ImmReleaseContext(j, k);
  }

  public void setLocation(int paramInt1, int paramInt2)
  {
    checkWidget();
    if ((this.x == paramInt1) && (this.y == paramInt2))
      return;
    this.x = paramInt1;
    this.y = paramInt2;
    this.moved = true;
    if ((this.isVisible) && (hasFocus()))
      move();
  }

  public void setLocation(Point paramPoint)
  {
    checkWidget();
    if (paramPoint == null)
      error(4);
    setLocation(paramPoint.x, paramPoint.y);
  }

  public void setSize(int paramInt1, int paramInt2)
  {
    checkWidget();
    if ((this.width == paramInt1) && (this.height == paramInt2))
      return;
    this.width = paramInt1;
    this.height = paramInt2;
    this.resized = true;
    if ((this.isVisible) && (hasFocus()))
      resize();
  }

  public void setSize(Point paramPoint)
  {
    checkWidget();
    if (paramPoint == null)
      error(4);
    setSize(paramPoint.x, paramPoint.y);
  }

  public void setVisible(boolean paramBoolean)
  {
    checkWidget();
    if (paramBoolean == this.isVisible)
      return;
    this.isVisible = paramBoolean;
    int i = this.parent.handle;
    if (OS.GetFocus() != i)
      return;
    if (!this.isVisible)
    {
      OS.HideCaret(i);
    }
    else
    {
      if (this.resized)
        resize();
      else if (this.moved)
        move();
      OS.ShowCaret(i);
    }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.Caret
 * JD-Core Version:    0.6.2
 */