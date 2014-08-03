package org.eclipse.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.TCHAR;

public class ExpandItem extends Item
{
  ExpandBar parent;
  Control control;
  boolean expanded;
  boolean hover;
  int x;
  int y;
  int width;
  int height;
  int imageHeight;
  int imageWidth;
  static final int TEXT_INSET = 6;
  static final int BORDER = 1;
  static final int CHEVRON_SIZE = 24;

  public ExpandItem(ExpandBar paramExpandBar, int paramInt)
  {
    this(paramExpandBar, paramInt, checkNull(paramExpandBar).getItemCount());
  }

  public ExpandItem(ExpandBar paramExpandBar, int paramInt1, int paramInt2)
  {
    super(paramExpandBar, paramInt1);
    this.parent = paramExpandBar;
    paramExpandBar.createItem(this, paramInt1, paramInt2);
  }

  static ExpandBar checkNull(ExpandBar paramExpandBar)
  {
    if (paramExpandBar == null)
      SWT.error(4);
    return paramExpandBar;
  }

  private void drawChevron(int paramInt, RECT paramRECT)
  {
    int i = OS.SelectObject(paramInt, OS.GetSysColorBrush(OS.COLOR_BTNFACE));
    OS.PatBlt(paramInt, paramRECT.left, paramRECT.top, paramRECT.right - paramRECT.left, paramRECT.bottom - paramRECT.top, 15728673);
    OS.SelectObject(paramInt, i);
    paramRECT.left += 4;
    paramRECT.top += 4;
    paramRECT.right -= 4;
    paramRECT.bottom -= 4;
    int j = OS.CreatePen(0, 1, this.parent.getForegroundPixel());
    int k = OS.SelectObject(paramInt, j);
    int m;
    int n;
    int[] arrayOfInt1;
    int[] arrayOfInt2;
    if (this.expanded)
    {
      m = paramRECT.left + 5;
      n = paramRECT.top + 7;
      arrayOfInt1 = new int[] { m, n, m + 1, n, m + 1, n - 1, m + 2, n - 1, m + 2, n - 2, m + 3, n - 2, m + 3, n - 3, m + 3, n - 2, m + 4, n - 2, m + 4, n - 1, m + 5, n - 1, m + 5, n, m + 7, n };
      n += 4;
      arrayOfInt2 = new int[] { m, n, m + 1, n, m + 1, n - 1, m + 2, n - 1, m + 2, n - 2, m + 3, n - 2, m + 3, n - 3, m + 3, n - 2, m + 4, n - 2, m + 4, n - 1, m + 5, n - 1, m + 5, n, m + 7, n };
    }
    else
    {
      m = paramRECT.left + 5;
      n = paramRECT.top + 4;
      arrayOfInt1 = new int[] { m, n, m + 1, n, m + 1, n + 1, m + 2, n + 1, m + 2, n + 2, m + 3, n + 2, m + 3, n + 3, m + 3, n + 2, m + 4, n + 2, m + 4, n + 1, m + 5, n + 1, m + 5, n, m + 7, n };
      n += 4;
      arrayOfInt2 = new int[] { m, n, m + 1, n, m + 1, n + 1, m + 2, n + 1, m + 2, n + 2, m + 3, n + 2, m + 3, n + 3, m + 3, n + 2, m + 4, n + 2, m + 4, n + 1, m + 5, n + 1, m + 5, n, m + 7, n };
    }
    OS.Polyline(paramInt, arrayOfInt1, arrayOfInt1.length / 2);
    OS.Polyline(paramInt, arrayOfInt2, arrayOfInt2.length / 2);
    if (this.hover)
    {
      m = OS.CreatePen(0, 1, OS.GetSysColor(OS.COLOR_3DHILIGHT));
      n = OS.CreatePen(0, 1, OS.GetSysColor(OS.COLOR_3DSHADOW));
      OS.SelectObject(paramInt, m);
      int[] arrayOfInt3 = { paramRECT.left, paramRECT.bottom, paramRECT.left, paramRECT.top, paramRECT.right, paramRECT.top };
      OS.Polyline(paramInt, arrayOfInt3, arrayOfInt3.length / 2);
      OS.SelectObject(paramInt, n);
      int[] arrayOfInt4 = { paramRECT.right, paramRECT.top, paramRECT.right, paramRECT.bottom, paramRECT.left, paramRECT.bottom };
      OS.Polyline(paramInt, arrayOfInt4, arrayOfInt4.length / 2);
      OS.SelectObject(paramInt, k);
      OS.DeleteObject(m);
      OS.DeleteObject(n);
    }
    else
    {
      OS.SelectObject(paramInt, k);
    }
    OS.DeleteObject(j);
  }

  void drawItem(GC paramGC, int paramInt, RECT paramRECT, boolean paramBoolean)
  {
    int i = paramGC.handle;
    int j = this.parent.getBandHeight();
    RECT localRECT = new RECT();
    OS.SetRect(localRECT, this.x, this.y, this.x + this.width, this.y + j);
    if (paramInt != 0)
    {
      OS.DrawThemeBackground(paramInt, i, 8, 0, localRECT, paramRECT);
    }
    else
    {
      int k = OS.SelectObject(i, OS.GetSysColorBrush(OS.COLOR_BTNFACE));
      OS.PatBlt(i, localRECT.left, localRECT.top, localRECT.right - localRECT.left, localRECT.bottom - localRECT.top, 15728673);
      OS.SelectObject(i, k);
    }
    if (this.image != null)
    {
      localRECT.left += 6;
      if (this.imageHeight > j)
        paramGC.drawImage(this.image, localRECT.left, localRECT.top + j - this.imageHeight);
      else
        paramGC.drawImage(this.image, localRECT.left, localRECT.top + (j - this.imageHeight) / 2);
      localRECT.left += this.imageWidth;
    }
    int n;
    if (this.text.length() > 0)
    {
      localRECT.left += 6;
      TCHAR localTCHAR = new TCHAR(this.parent.getCodePage(), this.text, false);
      if (paramInt != 0)
      {
        OS.DrawThemeText(paramInt, i, 8, 0, localTCHAR.chars, localTCHAR.length(), 36, 0, localRECT);
      }
      else
      {
        n = OS.SetBkMode(i, 1);
        OS.DrawText(i, localTCHAR, localTCHAR.length(), localRECT, 36);
        OS.SetBkMode(i, n);
      }
    }
    int m = 24;
    localRECT.left = (localRECT.right - m);
    localRECT.top = (this.y + (j - m) / 2);
    localRECT.bottom = (localRECT.top + m);
    int i1;
    if (paramInt != 0)
    {
      n = this.expanded ? 6 : 7;
      i1 = this.hover ? 2 : 1;
      OS.DrawThemeBackground(paramInt, i, n, i1, localRECT, paramRECT);
    }
    else
    {
      drawChevron(i, localRECT);
    }
    if (paramBoolean)
    {
      OS.SetRect(localRECT, this.x + 1, this.y + 1, this.x + this.width - 2, this.y + j - 2);
      OS.DrawFocusRect(i, localRECT);
    }
    if ((this.expanded) && (!this.parent.isAppThemed()))
    {
      n = OS.CreatePen(0, 1, OS.GetSysColor(OS.COLOR_BTNFACE));
      i1 = OS.SelectObject(i, n);
      int[] arrayOfInt = { this.x, this.y + j, this.x, this.y + j + this.height, this.x + this.width - 1, this.y + j + this.height, this.x + this.width - 1, this.y + j - 1 };
      OS.Polyline(i, arrayOfInt, arrayOfInt.length / 2);
      OS.SelectObject(i, i1);
      OS.DeleteObject(n);
    }
  }

  void destroyWidget()
  {
    this.parent.destroyItem(this);
    releaseHandle();
  }

  public Control getControl()
  {
    checkWidget();
    return this.control;
  }

  public boolean getExpanded()
  {
    checkWidget();
    return this.expanded;
  }

  public int getHeaderHeight()
  {
    checkWidget();
    return Math.max(this.parent.getBandHeight(), this.imageHeight);
  }

  public int getHeight()
  {
    checkWidget();
    return this.height;
  }

  public ExpandBar getParent()
  {
    checkWidget();
    return this.parent;
  }

  int getPreferredWidth(int paramInt1, int paramInt2)
  {
    int i = 36;
    if (this.image != null)
      i += 6 + this.imageWidth;
    if (this.text.length() > 0)
    {
      RECT localRECT = new RECT();
      TCHAR localTCHAR = new TCHAR(this.parent.getCodePage(), this.text, false);
      if (paramInt1 != 0)
        OS.GetThemeTextExtent(paramInt1, paramInt2, 8, 0, localTCHAR.chars, localTCHAR.length(), 32, null, localRECT);
      else
        OS.DrawText(paramInt2, localTCHAR, localTCHAR.length(), localRECT, 1024);
      i += localRECT.right - localRECT.left;
    }
    return i;
  }

  boolean isHover(int paramInt1, int paramInt2)
  {
    int i = this.parent.getBandHeight();
    return (this.x < paramInt1) && (paramInt1 < this.x + this.width) && (this.y < paramInt2) && (paramInt2 < this.y + i);
  }

  void redraw(boolean paramBoolean)
  {
    int i = this.parent.handle;
    int j = this.parent.getBandHeight();
    RECT localRECT = new RECT();
    int k = paramBoolean ? this.x : this.x + this.width - j;
    OS.SetRect(localRECT, k, this.y, this.x + this.width, this.y + j);
    OS.InvalidateRect(i, localRECT, true);
    if (this.imageHeight > j)
    {
      OS.SetRect(localRECT, this.x + 6, this.y + j - this.imageHeight, this.x + 6 + this.imageWidth, this.y);
      OS.InvalidateRect(i, localRECT, true);
    }
    if (!this.parent.isAppThemed())
    {
      OS.SetRect(localRECT, this.x, this.y + j, this.x + this.width, this.y + j + this.height + 1);
      OS.InvalidateRect(i, localRECT, true);
    }
  }

  void releaseHandle()
  {
    super.releaseHandle();
    this.parent = null;
  }

  void releaseWidget()
  {
    super.releaseWidget();
    this.control = null;
  }

  void setBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean1, boolean paramBoolean2)
  {
    redraw(true);
    int i = this.parent.getBandHeight();
    if (paramBoolean1)
    {
      if (this.imageHeight > i)
        paramInt2 += this.imageHeight - i;
      this.x = paramInt1;
      this.y = paramInt2;
      redraw(true);
    }
    if (paramBoolean2)
    {
      this.width = paramInt3;
      this.height = paramInt4;
      redraw(true);
    }
    if ((this.control != null) && (!this.control.isDisposed()))
    {
      if (!this.parent.isAppThemed())
      {
        paramInt1++;
        paramInt3 = Math.max(0, paramInt3 - 2);
        paramInt4 = Math.max(0, paramInt4 - 1);
      }
      if ((paramBoolean1) && (paramBoolean2))
        this.control.setBounds(paramInt1, paramInt2 + i, paramInt3, paramInt4);
      if ((paramBoolean1) && (!paramBoolean2))
        this.control.setLocation(paramInt1, paramInt2 + i);
      if ((!paramBoolean1) && (paramBoolean2))
        this.control.setSize(paramInt3, paramInt4);
    }
  }

  public void setControl(Control paramControl)
  {
    checkWidget();
    if (paramControl != null)
    {
      if (paramControl.isDisposed())
        error(5);
      if (paramControl.parent != this.parent)
        error(32);
    }
    this.control = paramControl;
    if (paramControl != null)
    {
      int i = this.parent.getBandHeight();
      paramControl.setVisible(this.expanded);
      if (!this.parent.isAppThemed())
      {
        int j = Math.max(0, this.width - 2);
        int k = Math.max(0, this.height - 1);
        paramControl.setBounds(this.x + 1, this.y + i, j, k);
      }
      else
      {
        paramControl.setBounds(this.x, this.y + i, this.width, this.height);
      }
    }
  }

  public void setExpanded(boolean paramBoolean)
  {
    checkWidget();
    this.expanded = paramBoolean;
    this.parent.showItem(this);
  }

  public void setHeight(int paramInt)
  {
    checkWidget();
    if (paramInt < 0)
      return;
    setBounds(0, 0, this.width, paramInt, false, true);
    if (this.expanded)
      this.parent.layoutItems(this.parent.indexOf(this) + 1, true);
  }

  public void setImage(Image paramImage)
  {
    super.setImage(paramImage);
    int i = this.imageHeight;
    if (paramImage != null)
    {
      Rectangle localRectangle = paramImage.getBounds();
      this.imageHeight = localRectangle.height;
      this.imageWidth = localRectangle.width;
    }
    else
    {
      this.imageHeight = (this.imageWidth = 0);
    }
    if (i != this.imageHeight)
      this.parent.layoutItems(this.parent.indexOf(this), true);
    else
      redraw(true);
  }

  public void setText(String paramString)
  {
    super.setText(paramString);
    redraw(true);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.ExpandItem
 * JD-Core Version:    0.6.2
 */