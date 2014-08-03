package org.eclipse.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.win32.LVITEM;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.TCHAR;

public class TableItem extends Item
{
  Table parent;
  String[] strings;
  Image[] images;
  Font font;
  Font[] cellFont;
  boolean checked;
  boolean grayed;
  boolean cached;
  int imageIndent;
  int background = -1;
  int foreground = -1;
  int[] cellBackground;
  int[] cellForeground;

  public TableItem(Table paramTable, int paramInt)
  {
    this(paramTable, paramInt, checkNull(paramTable).getItemCount(), true);
  }

  public TableItem(Table paramTable, int paramInt1, int paramInt2)
  {
    this(paramTable, paramInt1, paramInt2, true);
  }

  TableItem(Table paramTable, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    super(paramTable, paramInt1);
    this.parent = paramTable;
    if (paramBoolean)
      paramTable.createItem(this, paramInt2);
  }

  static Table checkNull(Table paramTable)
  {
    if (paramTable == null)
      SWT.error(4);
    return paramTable;
  }

  protected void checkSubclass()
  {
    if (!isValidSubclass())
      error(43);
  }

  void clear()
  {
    this.text = "";
    this.image = null;
    this.strings = null;
    this.images = null;
    this.imageIndent = 0;
    this.checked = (this.grayed = 0);
    this.font = null;
    this.background = (this.foreground = -1);
    this.cellFont = null;
    this.cellBackground = (this.cellForeground = null);
    if ((this.parent.style & 0x10000000) != 0)
      this.cached = false;
  }

  void destroyWidget()
  {
    this.parent.destroyItem(this);
    releaseHandle();
  }

  int fontHandle(int paramInt)
  {
    if ((this.cellFont != null) && (this.cellFont[paramInt] != null))
      return this.cellFont[paramInt].handle;
    if (this.font != null)
      return this.font.handle;
    return -1;
  }

  public Color getBackground()
  {
    checkWidget();
    if (!this.parent.checkData(this, true))
      error(24);
    if (this.background == -1)
      return this.parent.getBackground();
    return Color.win32_new(this.display, this.background);
  }

  public Color getBackground(int paramInt)
  {
    checkWidget();
    if (!this.parent.checkData(this, true))
      error(24);
    int i = Math.max(1, this.parent.getColumnCount());
    if ((paramInt < 0) || (paramInt > i - 1))
      return getBackground();
    int j = this.cellBackground != null ? this.cellBackground[paramInt] : -1;
    return j == -1 ? getBackground() : Color.win32_new(this.display, j);
  }

  public Rectangle getBounds()
  {
    checkWidget();
    if (!this.parent.checkData(this, true))
      error(24);
    int i = this.parent.indexOf(this);
    if (i == -1)
      return new Rectangle(0, 0, 0, 0);
    RECT localRECT = getBounds(i, 0, true, false, false);
    int j = localRECT.right - localRECT.left;
    int k = localRECT.bottom - localRECT.top;
    return new Rectangle(localRECT.left, localRECT.top, j, k);
  }

  public Rectangle getBounds(int paramInt)
  {
    checkWidget();
    if (!this.parent.checkData(this, true))
      error(24);
    int i = this.parent.indexOf(this);
    if (i == -1)
      return new Rectangle(0, 0, 0, 0);
    RECT localRECT = getBounds(i, paramInt, true, true, true);
    int j = localRECT.right - localRECT.left;
    int k = localRECT.bottom - localRECT.top;
    return new Rectangle(localRECT.left, localRECT.top, j, k);
  }

  RECT getBounds(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    return getBounds(paramInt1, paramInt2, paramBoolean1, paramBoolean2, paramBoolean3, false, 0);
  }

  RECT getBounds(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, int paramInt3)
  {
    if ((!paramBoolean1) && (!paramBoolean2))
      return new RECT();
    int i = this.parent.getColumnCount();
    if ((paramInt2 < 0) || (paramInt2 >= Math.max(1, i)))
      return new RECT();
    if (this.parent.fixScrollWidth)
      this.parent.setScrollWidth(null, true);
    RECT localRECT1 = new RECT();
    int j = this.parent.handle;
    int k = OS.SendMessage(j, 4151, 0, 0);
    int i6;
    int i2;
    if ((paramInt2 == 0) && ((k & 0x20) == 0))
    {
      int m;
      if (this.parent.explorerTheme)
      {
        localRECT1.left = 1;
        this.parent.ignoreCustomDraw = true;
        m = OS.SendMessage(j, 4110, paramInt1, localRECT1);
        this.parent.ignoreCustomDraw = false;
        if (m == 0)
          return new RECT();
        if (paramBoolean1)
        {
          int i1 = 0;
          int i3 = fontHandle(paramInt2);
          TCHAR localTCHAR1;
          if ((i3 == -1) && (paramInt3 == 0))
          {
            localTCHAR1 = new TCHAR(this.parent.getCodePage(), this.text, true);
            i1 = OS.SendMessage(j, OS.LVM_GETSTRINGWIDTH, 0, localTCHAR1);
          }
          else
          {
            localTCHAR1 = new TCHAR(this.parent.getCodePage(), this.text, false);
            int i5 = paramInt3 != 0 ? paramInt3 : OS.GetDC(j);
            i6 = -1;
            if (paramInt3 == 0)
            {
              if (i3 == -1)
                i3 = OS.SendMessage(j, 49, 0, 0);
              i6 = OS.SelectObject(i5, i3);
            }
            RECT localRECT6 = new RECT();
            int i7 = 3104;
            OS.DrawText(i5, localTCHAR1, localTCHAR1.length(), localRECT6, i7);
            i1 = localRECT6.right - localRECT6.left;
            if (paramInt3 == 0)
            {
              if (i6 != -1)
                OS.SelectObject(i5, i6);
              OS.ReleaseDC(j, i5);
            }
          }
          if (!paramBoolean2)
            localRECT1.left = localRECT1.right;
          localRECT1.right += i1 + 8;
        }
      }
      else if (paramBoolean1)
      {
        localRECT1.left = 3;
        this.parent.ignoreCustomDraw = true;
        m = OS.SendMessage(j, 4110, paramInt1, localRECT1);
        this.parent.ignoreCustomDraw = false;
        if (m == 0)
          return new RECT();
        if (!paramBoolean2)
        {
          RECT localRECT3 = new RECT();
          localRECT3.left = 1;
          this.parent.ignoreCustomDraw = true;
          m = OS.SendMessage(j, 4110, paramInt1, localRECT3);
          this.parent.ignoreCustomDraw = false;
          if (m != 0)
            localRECT1.left = localRECT3.right;
        }
      }
      else
      {
        localRECT1.left = 1;
        this.parent.ignoreCustomDraw = true;
        m = OS.SendMessage(j, 4110, paramInt1, localRECT1);
        this.parent.ignoreCustomDraw = false;
        if (m == 0)
          return new RECT();
      }
      if ((paramBoolean3) || (paramBoolean4))
      {
        RECT localRECT2 = new RECT();
        i2 = OS.SendMessage(j, 4127, 0, 0);
        OS.SendMessage(i2, 4615, 0, localRECT2);
        OS.MapWindowPoints(i2, j, localRECT2, 2);
        if ((paramBoolean1) && (paramBoolean3))
          localRECT1.right = localRECT2.right;
        if ((paramBoolean2) && (paramBoolean4))
          localRECT1.left = localRECT2.left;
      }
    }
    else
    {
      n = ((paramInt2 != 0) || (this.image == null)) && ((this.images == null) || (this.images[paramInt2] == null)) ? 0 : 1;
      localRECT1.top = paramInt2;
      RECT localRECT4;
      if ((paramBoolean3) || (paramBoolean4) || (paramInt3 == 0))
      {
        localRECT1.left = (paramBoolean1 ? 2 : 1);
        this.parent.ignoreCustomDraw = true;
        i2 = OS.SendMessage(j, 4152, paramInt1, localRECT1);
        this.parent.ignoreCustomDraw = false;
        if (i2 == 0)
          return new RECT();
        if ((paramInt2 == 0) && (paramBoolean1) && (paramBoolean2))
        {
          localRECT4 = new RECT();
          localRECT4.left = 1;
          this.parent.ignoreCustomDraw = true;
          i2 = OS.SendMessage(j, 4152, paramInt1, localRECT4);
          this.parent.ignoreCustomDraw = false;
          if (i2 != 0)
            localRECT1.left = localRECT4.left;
        }
        if (n != 0)
        {
          if ((paramInt2 != 0) && (paramBoolean1) && (!paramBoolean2))
          {
            localRECT4 = new RECT();
            localRECT4.top = paramInt2;
            localRECT4.left = 1;
            if (OS.SendMessage(j, 4152, paramInt1, localRECT4) != 0)
              localRECT1.left = (localRECT4.right + 2);
          }
        }
        else if ((paramBoolean2) && (!paramBoolean1))
          localRECT1.right = localRECT1.left;
        if ((paramInt2 == 0) && (paramBoolean4))
        {
          localRECT4 = new RECT();
          int i4 = OS.SendMessage(j, 4127, 0, 0);
          OS.SendMessage(i4, 4615, 0, localRECT4);
          OS.MapWindowPoints(i4, j, localRECT4, 2);
          localRECT1.left = localRECT4.left;
        }
      }
      else
      {
        localRECT1.left = 1;
        this.parent.ignoreCustomDraw = true;
        i2 = OS.SendMessage(j, 4152, paramInt1, localRECT1);
        this.parent.ignoreCustomDraw = false;
        if (i2 == 0)
          return new RECT();
        if (n == 0)
          localRECT1.right = localRECT1.left;
        if (paramBoolean1)
        {
          localRECT4 = this.strings != null ? this.strings[paramInt2] : paramInt2 == 0 ? this.text : null;
          if (localRECT4 != null)
          {
            RECT localRECT5 = new RECT();
            TCHAR localTCHAR2 = new TCHAR(this.parent.getCodePage(), localRECT4, false);
            i6 = 3104;
            OS.DrawText(paramInt3, localTCHAR2, localTCHAR2.length(), localRECT5, i6);
            localRECT1.right += localRECT5.right - localRECT5.left + 12 + 1;
          }
        }
      }
    }
    int n = this.parent.getLinesVisible() ? 1 : 0;
    if (OS.COMCTL32_VERSION >= OS.VERSION(5, 80))
      localRECT1.top -= n;
    if (paramInt2 != 0)
      localRECT1.left += n;
    localRECT1.right = Math.max(localRECT1.right, localRECT1.left);
    localRECT1.top += n;
    localRECT1.bottom = Math.max(localRECT1.bottom - n, localRECT1.top);
    return localRECT1;
  }

  public boolean getChecked()
  {
    checkWidget();
    if (!this.parent.checkData(this, true))
      error(24);
    if ((this.parent.style & 0x20) == 0)
      return false;
    return this.checked;
  }

  public Font getFont()
  {
    checkWidget();
    if (!this.parent.checkData(this, true))
      error(24);
    return this.font != null ? this.font : this.parent.getFont();
  }

  public Font getFont(int paramInt)
  {
    checkWidget();
    if (!this.parent.checkData(this, true))
      error(24);
    int i = Math.max(1, this.parent.getColumnCount());
    if ((paramInt < 0) || (paramInt > i - 1))
      return getFont();
    if ((this.cellFont == null) || (this.cellFont[paramInt] == null))
      return getFont();
    return this.cellFont[paramInt];
  }

  public Color getForeground()
  {
    checkWidget();
    if (!this.parent.checkData(this, true))
      error(24);
    if (this.foreground == -1)
      return this.parent.getForeground();
    return Color.win32_new(this.display, this.foreground);
  }

  public Color getForeground(int paramInt)
  {
    checkWidget();
    if (!this.parent.checkData(this, true))
      error(24);
    int i = Math.max(1, this.parent.getColumnCount());
    if ((paramInt < 0) || (paramInt > i - 1))
      return getForeground();
    int j = this.cellForeground != null ? this.cellForeground[paramInt] : -1;
    return j == -1 ? getForeground() : Color.win32_new(this.display, j);
  }

  public boolean getGrayed()
  {
    checkWidget();
    if (!this.parent.checkData(this, true))
      error(24);
    if ((this.parent.style & 0x20) == 0)
      return false;
    return this.grayed;
  }

  public Image getImage()
  {
    checkWidget();
    if (!this.parent.checkData(this, true))
      error(24);
    return super.getImage();
  }

  public Image getImage(int paramInt)
  {
    checkWidget();
    if (!this.parent.checkData(this, true))
      error(24);
    if (paramInt == 0)
      return getImage();
    if ((this.images != null) && (paramInt >= 0) && (paramInt < this.images.length))
      return this.images[paramInt];
    return null;
  }

  public Rectangle getImageBounds(int paramInt)
  {
    checkWidget();
    if (!this.parent.checkData(this, true))
      error(24);
    int i = this.parent.indexOf(this);
    if (i == -1)
      return new Rectangle(0, 0, 0, 0);
    RECT localRECT = getBounds(i, paramInt, false, true, false);
    int j = localRECT.right - localRECT.left;
    int k = localRECT.bottom - localRECT.top;
    return new Rectangle(localRECT.left, localRECT.top, j, k);
  }

  public int getImageIndent()
  {
    checkWidget();
    if (!this.parent.checkData(this, true))
      error(24);
    return this.imageIndent;
  }

  String getNameText()
  {
    if (((this.parent.style & 0x10000000) != 0) && (!this.cached))
      return "*virtual*";
    return super.getNameText();
  }

  public Table getParent()
  {
    checkWidget();
    return this.parent;
  }

  public String getText()
  {
    checkWidget();
    if (!this.parent.checkData(this, true))
      error(24);
    return super.getText();
  }

  public String getText(int paramInt)
  {
    checkWidget();
    if (!this.parent.checkData(this, true))
      error(24);
    if (paramInt == 0)
      return getText();
    if ((this.strings != null) && (paramInt >= 0) && (paramInt < this.strings.length))
    {
      String str = this.strings[paramInt];
      return str != null ? str : "";
    }
    return "";
  }

  public Rectangle getTextBounds(int paramInt)
  {
    checkWidget();
    if (!this.parent.checkData(this, true))
      error(24);
    int i = this.parent.indexOf(this);
    if (i == -1)
      return new Rectangle(0, 0, 0, 0);
    RECT localRECT = getBounds(i, paramInt, true, false, true);
    localRECT.left += 2;
    if (paramInt != 0)
      localRECT.left += 4;
    localRECT.left = Math.min(localRECT.left, localRECT.right);
    localRECT.right -= 4;
    int j = Math.max(0, localRECT.right - localRECT.left);
    int k = Math.max(0, localRECT.bottom - localRECT.top);
    return new Rectangle(localRECT.left, localRECT.top, j, k);
  }

  void redraw()
  {
    if ((this.parent.currentItem == this) || (!this.parent.getDrawing()))
      return;
    int i = this.parent.handle;
    if (!OS.IsWindowVisible(i))
      return;
    int j = this.parent.indexOf(this);
    if (j == -1)
      return;
    OS.SendMessage(i, 4117, j, j);
  }

  void redraw(int paramInt, boolean paramBoolean1, boolean paramBoolean2)
  {
    if ((this.parent.currentItem == this) || (!this.parent.getDrawing()))
      return;
    int i = this.parent.handle;
    if (!OS.IsWindowVisible(i))
      return;
    int j = this.parent.indexOf(this);
    if (j == -1)
      return;
    RECT localRECT = getBounds(j, paramInt, paramBoolean1, paramBoolean2, true);
    OS.InvalidateRect(i, localRECT, true);
  }

  void releaseHandle()
  {
    super.releaseHandle();
    this.parent = null;
  }

  void releaseWidget()
  {
    super.releaseWidget();
    this.strings = null;
    this.images = null;
    this.cellFont = null;
    this.cellBackground = (this.cellForeground = null);
  }

  public void setBackground(Color paramColor)
  {
    checkWidget();
    if ((paramColor != null) && (paramColor.isDisposed()))
      SWT.error(5);
    int i = -1;
    if (paramColor != null)
    {
      this.parent.setCustomDraw(true);
      i = paramColor.handle;
    }
    if (this.background == i)
      return;
    this.background = i;
    if ((this.parent.style & 0x10000000) != 0)
      this.cached = true;
    redraw();
  }

  public void setBackground(int paramInt, Color paramColor)
  {
    checkWidget();
    if ((paramColor != null) && (paramColor.isDisposed()))
      SWT.error(5);
    int i = Math.max(1, this.parent.getColumnCount());
    if ((paramInt < 0) || (paramInt > i - 1))
      return;
    int j = -1;
    if (paramColor != null)
    {
      this.parent.setCustomDraw(true);
      j = paramColor.handle;
    }
    if (this.cellBackground == null)
    {
      this.cellBackground = new int[i];
      for (int k = 0; k < i; k++)
        this.cellBackground[k] = -1;
    }
    if (this.cellBackground[paramInt] == j)
      return;
    this.cellBackground[paramInt] = j;
    if ((this.parent.style & 0x10000000) != 0)
      this.cached = true;
    redraw(paramInt, true, true);
  }

  public void setChecked(boolean paramBoolean)
  {
    checkWidget();
    if ((this.parent.style & 0x20) == 0)
      return;
    if (this.checked == paramBoolean)
      return;
    setChecked(paramBoolean, false);
  }

  void setChecked(boolean paramBoolean1, boolean paramBoolean2)
  {
    this.checked = paramBoolean1;
    if ((this.parent.style & 0x10000000) != 0)
      this.cached = true;
    if (paramBoolean2)
    {
      Event localEvent = new Event();
      localEvent.item = this;
      localEvent.detail = 32;
      this.parent.sendSelectionEvent(13, localEvent, false);
    }
    redraw();
  }

  public void setFont(Font paramFont)
  {
    checkWidget();
    if ((paramFont != null) && (paramFont.isDisposed()))
      SWT.error(5);
    Font localFont = this.font;
    if (localFont == paramFont)
      return;
    this.font = paramFont;
    if ((localFont != null) && (localFont.equals(paramFont)))
      return;
    if (paramFont != null)
      this.parent.setCustomDraw(true);
    if ((this.parent.style & 0x10000000) != 0)
      this.cached = true;
    if (((this.parent.style & 0x10000000) == 0) && (this.cached))
    {
      int i = this.parent.indexOf(this);
      if (i != -1)
      {
        int j = this.parent.handle;
        LVITEM localLVITEM = new LVITEM();
        localLVITEM.mask = 1;
        localLVITEM.iItem = i;
        localLVITEM.pszText = -1;
        OS.SendMessage(j, OS.LVM_SETITEM, 0, localLVITEM);
        this.cached = false;
      }
    }
    this.parent.setScrollWidth(this, false);
    redraw();
  }

  public void setFont(int paramInt, Font paramFont)
  {
    checkWidget();
    if ((paramFont != null) && (paramFont.isDisposed()))
      SWT.error(5);
    int i = Math.max(1, this.parent.getColumnCount());
    if ((paramInt < 0) || (paramInt > i - 1))
      return;
    if (this.cellFont == null)
    {
      if (paramFont == null)
        return;
      this.cellFont = new Font[i];
    }
    Font localFont = this.cellFont[paramInt];
    if (localFont == paramFont)
      return;
    this.cellFont[paramInt] = paramFont;
    if ((localFont != null) && (localFont.equals(paramFont)))
      return;
    if (paramFont != null)
      this.parent.setCustomDraw(true);
    if ((this.parent.style & 0x10000000) != 0)
      this.cached = true;
    if (paramInt == 0)
    {
      if (((this.parent.style & 0x10000000) == 0) && (this.cached))
      {
        int j = this.parent.indexOf(this);
        if (j != -1)
        {
          int k = this.parent.handle;
          LVITEM localLVITEM = new LVITEM();
          localLVITEM.mask = 1;
          localLVITEM.iItem = j;
          localLVITEM.pszText = -1;
          OS.SendMessage(k, OS.LVM_SETITEM, 0, localLVITEM);
          this.cached = false;
        }
      }
      this.parent.setScrollWidth(this, false);
    }
    redraw(paramInt, true, false);
  }

  public void setForeground(Color paramColor)
  {
    checkWidget();
    if ((paramColor != null) && (paramColor.isDisposed()))
      SWT.error(5);
    int i = -1;
    if (paramColor != null)
    {
      this.parent.setCustomDraw(true);
      i = paramColor.handle;
    }
    if (this.foreground == i)
      return;
    this.foreground = i;
    if ((this.parent.style & 0x10000000) != 0)
      this.cached = true;
    redraw();
  }

  public void setForeground(int paramInt, Color paramColor)
  {
    checkWidget();
    if ((paramColor != null) && (paramColor.isDisposed()))
      SWT.error(5);
    int i = Math.max(1, this.parent.getColumnCount());
    if ((paramInt < 0) || (paramInt > i - 1))
      return;
    int j = -1;
    if (paramColor != null)
    {
      this.parent.setCustomDraw(true);
      j = paramColor.handle;
    }
    if (this.cellForeground == null)
    {
      this.cellForeground = new int[i];
      for (int k = 0; k < i; k++)
        this.cellForeground[k] = -1;
    }
    if (this.cellForeground[paramInt] == j)
      return;
    this.cellForeground[paramInt] = j;
    if ((this.parent.style & 0x10000000) != 0)
      this.cached = true;
    redraw(paramInt, true, false);
  }

  public void setGrayed(boolean paramBoolean)
  {
    checkWidget();
    if ((this.parent.style & 0x20) == 0)
      return;
    if (this.grayed == paramBoolean)
      return;
    this.grayed = paramBoolean;
    if ((this.parent.style & 0x10000000) != 0)
      this.cached = true;
    redraw();
  }

  public void setImage(Image[] paramArrayOfImage)
  {
    checkWidget();
    if (paramArrayOfImage == null)
      error(4);
    for (int i = 0; i < paramArrayOfImage.length; i++)
      setImage(i, paramArrayOfImage[i]);
  }

  public void setImage(int paramInt, Image paramImage)
  {
    checkWidget();
    if ((paramImage != null) && (paramImage.isDisposed()))
      error(5);
    Image localImage = null;
    if (paramInt == 0)
    {
      if ((paramImage != null) && (paramImage.type == 1) && (paramImage.equals(this.image)))
        return;
      localImage = this.image;
      super.setImage(paramImage);
    }
    int i = Math.max(1, this.parent.getColumnCount());
    if ((paramInt < 0) || (paramInt > i - 1))
      return;
    if ((this.images == null) && (paramInt != 0))
    {
      this.images = new Image[i];
      this.images[0] = paramImage;
    }
    if (this.images != null)
    {
      if ((paramImage != null) && (paramImage.type == 1) && (paramImage.equals(this.images[paramInt])))
        return;
      localImage = this.images[paramInt];
      this.images[paramInt] = paramImage;
    }
    if ((this.parent.style & 0x10000000) != 0)
      this.cached = true;
    this.parent.imageIndex(paramImage, paramInt);
    if (paramInt == 0)
      this.parent.setScrollWidth(this, false);
    boolean bool = ((paramImage == null) && (localImage != null)) || ((paramImage != null) && (localImage == null));
    redraw(paramInt, bool, true);
  }

  public void setImage(Image paramImage)
  {
    checkWidget();
    setImage(0, paramImage);
  }

  /** @deprecated */
  public void setImageIndent(int paramInt)
  {
    checkWidget();
    if (paramInt < 0)
      return;
    if (this.imageIndent == paramInt)
      return;
    this.imageIndent = paramInt;
    if ((this.parent.style & 0x10000000) != 0)
    {
      this.cached = true;
    }
    else
    {
      int i = this.parent.indexOf(this);
      if (i != -1)
      {
        int j = this.parent.handle;
        LVITEM localLVITEM = new LVITEM();
        localLVITEM.mask = 16;
        localLVITEM.iItem = i;
        localLVITEM.iIndent = paramInt;
        OS.SendMessage(j, OS.LVM_SETITEM, 0, localLVITEM);
      }
    }
    this.parent.setScrollWidth(this, false);
    redraw();
  }

  public void setText(String[] paramArrayOfString)
  {
    checkWidget();
    if (paramArrayOfString == null)
      error(4);
    for (int i = 0; i < paramArrayOfString.length; i++)
    {
      String str = paramArrayOfString[i];
      if (str != null)
        setText(i, str);
    }
  }

  public void setText(int paramInt, String paramString)
  {
    checkWidget();
    if (paramString == null)
      error(4);
    if (paramInt == 0)
    {
      if (paramString.equals(this.text))
        return;
      super.setText(paramString);
    }
    int i = Math.max(1, this.parent.getColumnCount());
    if ((paramInt < 0) || (paramInt > i - 1))
      return;
    if ((this.strings == null) && (paramInt != 0))
    {
      this.strings = new String[i];
      this.strings[0] = this.text;
    }
    if (this.strings != null)
    {
      if (paramString.equals(this.strings[paramInt]))
        return;
      this.strings[paramInt] = paramString;
    }
    if ((this.parent.style & 0x10000000) != 0)
      this.cached = true;
    if (paramInt == 0)
    {
      if (((this.parent.style & 0x10000000) == 0) && (this.cached))
      {
        int j = this.parent.indexOf(this);
        if (j != -1)
        {
          int k = this.parent.handle;
          LVITEM localLVITEM = new LVITEM();
          localLVITEM.mask = 1;
          localLVITEM.iItem = j;
          localLVITEM.pszText = -1;
          OS.SendMessage(k, OS.LVM_SETITEM, 0, localLVITEM);
          this.cached = false;
        }
      }
      this.parent.setScrollWidth(this, false);
    }
    redraw(paramInt, true, false);
  }

  public void setText(String paramString)
  {
    checkWidget();
    setText(0, paramString);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.TableItem
 * JD-Core Version:    0.6.2
 */