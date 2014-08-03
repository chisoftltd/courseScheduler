package org.eclipse.swt.custom;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Item;

public class CTabItem extends Item
{
  CTabFolder parent;
  int x;
  int y;
  int width;
  int height = 0;
  Control control;
  String toolTipText;
  String shortenedText;
  int shortenedTextWidth;
  Font font;
  Image disabledImage;
  Rectangle closeRect = new Rectangle(0, 0, 0, 0);
  int closeImageState = 8;
  int state = 0;
  boolean showClose = false;
  boolean showing = false;

  public CTabItem(CTabFolder paramCTabFolder, int paramInt)
  {
    this(paramCTabFolder, paramInt, paramCTabFolder.getItemCount());
  }

  public CTabItem(CTabFolder paramCTabFolder, int paramInt1, int paramInt2)
  {
    super(paramCTabFolder, paramInt1);
    this.showClose = ((paramInt1 & 0x40) != 0);
    paramCTabFolder.createItem(this, paramInt2);
  }

  public void dispose()
  {
    if (isDisposed())
      return;
    this.parent.destroyItem(this);
    super.dispose();
    this.parent = null;
    this.control = null;
    this.toolTipText = null;
    this.shortenedText = null;
    this.font = null;
  }

  public Rectangle getBounds()
  {
    return new Rectangle(this.x, this.y, this.width, this.height);
  }

  public Control getControl()
  {
    checkWidget();
    return this.control;
  }

  /** @deprecated */
  public Image getDisabledImage()
  {
    checkWidget();
    return this.disabledImage;
  }

  public Font getFont()
  {
    checkWidget();
    if (this.font != null)
      return this.font;
    return this.parent.getFont();
  }

  public CTabFolder getParent()
  {
    return this.parent;
  }

  public boolean getShowClose()
  {
    checkWidget();
    return this.showClose;
  }

  public String getToolTipText()
  {
    checkWidget();
    if ((this.toolTipText == null) && (this.shortenedText != null))
    {
      String str = getText();
      if (!this.shortenedText.equals(str))
        return str;
    }
    return this.toolTipText;
  }

  public boolean isShowing()
  {
    checkWidget();
    return this.showing;
  }

  public void setControl(Control paramControl)
  {
    checkWidget();
    if (paramControl != null)
    {
      if (paramControl.isDisposed())
        SWT.error(5);
      if (paramControl.getParent() != this.parent)
        SWT.error(32);
    }
    if ((this.control != null) && (!this.control.isDisposed()))
      this.control.setVisible(false);
    this.control = paramControl;
    if (this.control != null)
    {
      int i = this.parent.indexOf(this);
      if (i == this.parent.getSelectionIndex())
      {
        this.control.setBounds(this.parent.getClientArea());
        this.control.setVisible(true);
      }
      else
      {
        int j = this.parent.getSelectionIndex();
        Control localControl = null;
        if (j != -1)
          localControl = this.parent.getItem(j).getControl();
        if (this.control != localControl)
          this.control.setVisible(false);
      }
    }
  }

  /** @deprecated */
  public void setDisabledImage(Image paramImage)
  {
    checkWidget();
    if ((paramImage != null) && (paramImage.isDisposed()))
      SWT.error(5);
    this.disabledImage = paramImage;
  }

  public void setFont(Font paramFont)
  {
    checkWidget();
    if ((paramFont != null) && (paramFont.isDisposed()))
      SWT.error(5);
    if ((paramFont == null) && (this.font == null))
      return;
    if ((paramFont != null) && (paramFont.equals(this.font)))
      return;
    this.font = paramFont;
    if (!this.parent.updateTabHeight(false))
    {
      this.parent.updateItems();
      this.parent.redrawTabs();
    }
  }

  public void setImage(Image paramImage)
  {
    checkWidget();
    if ((paramImage != null) && (paramImage.isDisposed()))
      SWT.error(5);
    Image localImage = getImage();
    if ((paramImage == null) && (localImage == null))
      return;
    if ((paramImage != null) && (paramImage.equals(localImage)))
      return;
    super.setImage(paramImage);
    if (!this.parent.updateTabHeight(false))
    {
      if ((localImage != null) && (paramImage != null))
      {
        Rectangle localRectangle1 = localImage.getBounds();
        Rectangle localRectangle2 = paramImage.getBounds();
        if ((localRectangle2.width == localRectangle1.width) && (localRectangle2.height == localRectangle1.height))
        {
          if (this.showing)
          {
            int i = this.parent.indexOf(this);
            int j = i == this.parent.selectedIndex ? 1 : 0;
            if ((j != 0) || (this.parent.showUnselectedImage))
            {
              CTabFolderRenderer localCTabFolderRenderer = this.parent.renderer;
              Rectangle localRectangle3 = localCTabFolderRenderer.computeTrim(i, 0, 0, 0, 0, 0);
              int k = this.x - localRectangle3.x;
              int m;
              if (j != 0)
              {
                GC localGC = new GC(this.parent);
                if ((this.parent.single) && ((this.parent.showClose) || (this.showClose)))
                  k += localCTabFolderRenderer.computeSize(-8, 0, localGC, -1, -1).x;
                int i1 = Math.min(this.x + this.width, this.parent.getRightItemEdge(localGC));
                localGC.dispose();
                m = i1 - k - (localRectangle3.width + localRectangle3.x);
                if ((!this.parent.single) && (this.closeRect.width > 0))
                  m -= this.closeRect.width + 4;
              }
              else
              {
                m = this.x + this.width - k - (localRectangle3.width + localRectangle3.x);
                if ((this.parent.showUnselectedClose) && ((this.parent.showClose) || (this.showClose)))
                  m -= this.closeRect.width + 4;
              }
              if (localRectangle2.width < m)
              {
                int n = this.y + (this.height - localRectangle2.height) / 2 + (this.parent.onBottom ? -1 : 1);
                this.parent.redraw(k, n, localRectangle2.width, localRectangle2.height, false);
              }
            }
          }
          return;
        }
      }
      this.parent.updateItems();
      this.parent.redrawTabs();
    }
  }

  public void setShowClose(boolean paramBoolean)
  {
    checkWidget();
    if (this.showClose == paramBoolean)
      return;
    this.showClose = paramBoolean;
    this.parent.updateItems();
    this.parent.redrawTabs();
  }

  public void setText(String paramString)
  {
    checkWidget();
    if (paramString == null)
      SWT.error(4);
    if (paramString.equals(getText()))
      return;
    super.setText(paramString);
    this.shortenedText = null;
    this.shortenedTextWidth = 0;
    if (!this.parent.updateTabHeight(false))
    {
      this.parent.updateItems();
      this.parent.redrawTabs();
    }
  }

  public void setToolTipText(String paramString)
  {
    checkWidget();
    this.toolTipText = paramString;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.custom.CTabItem
 * JD-Core Version:    0.6.2
 */