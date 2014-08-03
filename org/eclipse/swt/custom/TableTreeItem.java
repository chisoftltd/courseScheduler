package org.eclipse.swt.custom;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

/** @deprecated */
public class TableTreeItem extends Item
{
  TableItem tableItem;
  TableTree parent;
  TableTreeItem parentItem;
  TableTreeItem[] items = TableTree.EMPTY_ITEMS;
  String[] texts = TableTree.EMPTY_TEXTS;
  Image[] images = TableTree.EMPTY_IMAGES;
  Color background;
  Color foreground;
  Font font;
  boolean expanded;
  boolean checked;
  boolean grayed;

  public TableTreeItem(TableTree paramTableTree, int paramInt)
  {
    this(paramTableTree, paramInt, paramTableTree.getItemCount());
  }

  public TableTreeItem(TableTree paramTableTree, int paramInt1, int paramInt2)
  {
    this(paramTableTree, null, paramInt1, paramInt2);
  }

  public TableTreeItem(TableTreeItem paramTableTreeItem, int paramInt)
  {
    this(paramTableTreeItem, paramInt, paramTableTreeItem.getItemCount());
  }

  public TableTreeItem(TableTreeItem paramTableTreeItem, int paramInt1, int paramInt2)
  {
    this(paramTableTreeItem.getParent(), paramTableTreeItem, paramInt1, paramInt2);
  }

  TableTreeItem(TableTree paramTableTree, TableTreeItem paramTableTreeItem, int paramInt1, int paramInt2)
  {
    super(paramTableTree, paramInt1);
    this.parent = paramTableTree;
    this.parentItem = paramTableTreeItem;
    if (paramTableTreeItem == null)
    {
      int i = paramTableTree.addItem(this, paramInt2);
      this.tableItem = new TableItem(paramTableTree.getTable(), paramInt1, i);
      this.tableItem.setData("TableTreeItemID", this);
      addCheck();
      if (paramTableTree.sizeImage == null)
      {
        int j = paramTableTree.getItemHeight();
        paramTableTree.sizeImage = new Image(paramTableTree.getDisplay(), j, j);
        GC localGC = new GC(paramTableTree.sizeImage);
        localGC.setBackground(paramTableTree.getBackground());
        localGC.fillRectangle(0, 0, j, j);
        localGC.dispose();
        this.tableItem.setImage(0, paramTableTree.sizeImage);
      }
    }
    else
    {
      paramTableTreeItem.addItem(this, paramInt2);
    }
  }

  void addCheck()
  {
    Table localTable = this.parent.getTable();
    if ((localTable.getStyle() & 0x20) == 0)
      return;
    this.tableItem.setChecked(this.checked);
    this.tableItem.setGrayed(this.grayed);
  }

  void addItem(TableTreeItem paramTableTreeItem, int paramInt)
  {
    if (paramTableTreeItem == null)
      SWT.error(4);
    if ((paramInt < 0) || (paramInt > this.items.length))
      SWT.error(5);
    if ((this.items.length == 0) && (paramInt == 0) && (this.tableItem != null))
    {
      localObject = this.expanded ? this.parent.getMinusImage() : this.parent.getPlusImage();
      this.tableItem.setImage(0, (Image)localObject);
    }
    Object localObject = new TableTreeItem[this.items.length + 1];
    System.arraycopy(this.items, 0, localObject, 0, paramInt);
    localObject[paramInt] = paramTableTreeItem;
    System.arraycopy(this.items, paramInt, localObject, paramInt + 1, this.items.length - paramInt);
    this.items = ((TableTreeItem[])localObject);
    if (this.expanded)
      paramTableTreeItem.setVisible(true);
  }

  public Color getBackground()
  {
    checkWidget();
    return this.background == null ? this.parent.getBackground() : this.background;
  }

  public Rectangle getBounds(int paramInt)
  {
    checkWidget();
    if (this.tableItem != null)
      return this.tableItem.getBounds(paramInt);
    return new Rectangle(0, 0, 0, 0);
  }

  public boolean getChecked()
  {
    checkWidget();
    if (this.tableItem == null)
      return this.checked;
    return this.tableItem.getChecked();
  }

  public boolean getGrayed()
  {
    checkWidget();
    if (this.tableItem == null)
      return this.grayed;
    return this.tableItem.getGrayed();
  }

  public boolean getExpanded()
  {
    return this.expanded;
  }

  public Font getFont()
  {
    checkWidget();
    return this.font == null ? this.parent.getFont() : this.font;
  }

  public Color getForeground()
  {
    checkWidget();
    return this.foreground == null ? this.parent.getForeground() : this.foreground;
  }

  public Image getImage()
  {
    checkWidget();
    return getImage(0);
  }

  public Image getImage(int paramInt)
  {
    if ((paramInt > 0) && (paramInt < this.images.length))
      return this.images[paramInt];
    return null;
  }

  int getIndent()
  {
    if (this.parentItem == null)
      return 0;
    return this.parentItem.getIndent() + 1;
  }

  public TableTreeItem getItem(int paramInt)
  {
    checkWidget();
    int i = this.items.length;
    if ((paramInt < 0) || (paramInt >= i))
      SWT.error(6);
    return this.items[paramInt];
  }

  public int getItemCount()
  {
    return this.items.length;
  }

  public TableTreeItem[] getItems()
  {
    TableTreeItem[] arrayOfTableTreeItem = new TableTreeItem[this.items.length];
    System.arraycopy(this.items, 0, arrayOfTableTreeItem, 0, this.items.length);
    return arrayOfTableTreeItem;
  }

  TableTreeItem getItem(TableItem paramTableItem)
  {
    if (paramTableItem == null)
      return null;
    if (this.tableItem == paramTableItem)
      return this;
    for (int i = 0; i < this.items.length; i++)
    {
      TableTreeItem localTableTreeItem = this.items[i].getItem(paramTableItem);
      if (localTableTreeItem != null)
        return localTableTreeItem;
    }
    return null;
  }

  public TableTree getParent()
  {
    return this.parent;
  }

  public TableTreeItem getParentItem()
  {
    return this.parentItem;
  }

  public String getText()
  {
    checkWidget();
    return getText(0);
  }

  public String getText(int paramInt)
  {
    if ((paramInt >= 0) && (paramInt < this.texts.length))
      return this.texts[paramInt];
    return null;
  }

  boolean getVisible()
  {
    return this.tableItem != null;
  }

  public int indexOf(TableTreeItem paramTableTreeItem)
  {
    for (int i = 0; i < this.items.length; i++)
      if (this.items[i] == paramTableTreeItem)
        return i;
    return -1;
  }

  void expandAll(boolean paramBoolean)
  {
    if (this.items.length == 0)
      return;
    if (!this.expanded)
    {
      setExpanded(true);
      if (paramBoolean)
      {
        Event localEvent = new Event();
        localEvent.item = this;
        this.parent.notifyListeners(17, localEvent);
      }
    }
    for (int i = 0; i < this.items.length; i++)
      this.items[i].expandAll(paramBoolean);
  }

  int expandedIndexOf(TableTreeItem paramTableTreeItem)
  {
    int i = 0;
    for (int j = 0; j < this.items.length; j++)
    {
      if (this.items[j] == paramTableTreeItem)
        return i;
      if (this.items[j].expanded)
        i += this.items[j].visibleChildrenCount();
      i++;
    }
    return -1;
  }

  int visibleChildrenCount()
  {
    int i = 0;
    for (int j = 0; j < this.items.length; j++)
      if (this.items[j].getVisible())
        i += 1 + this.items[j].visibleChildrenCount();
    return i;
  }

  public void dispose()
  {
    if (isDisposed())
      return;
    for (int i = this.items.length - 1; i >= 0; i--)
      this.items[i].dispose();
    super.dispose();
    if (!this.parent.inDispose)
    {
      if (this.parentItem != null)
        this.parentItem.removeItem(this);
      else
        this.parent.removeItem(this);
      if (this.tableItem != null)
        this.tableItem.dispose();
    }
    this.items = null;
    this.parentItem = null;
    this.parent = null;
    this.images = null;
    this.texts = null;
    this.tableItem = null;
    this.foreground = null;
    this.background = null;
    this.font = null;
  }

  void removeItem(TableTreeItem paramTableTreeItem)
  {
    for (int i = 0; (i < this.items.length) && (this.items[i] != paramTableTreeItem); i++);
    if (i == this.items.length)
      return;
    TableTreeItem[] arrayOfTableTreeItem = new TableTreeItem[this.items.length - 1];
    System.arraycopy(this.items, 0, arrayOfTableTreeItem, 0, i);
    System.arraycopy(this.items, i + 1, arrayOfTableTreeItem, i, this.items.length - i - 1);
    this.items = arrayOfTableTreeItem;
    if ((this.items.length == 0) && (this.tableItem != null))
      this.tableItem.setImage(0, null);
  }

  public void setBackground(Color paramColor)
  {
    checkWidget();
    if ((paramColor != null) && (paramColor.isDisposed()))
      SWT.error(5);
    if (this.tableItem != null)
      this.tableItem.setBackground(paramColor);
    this.background = paramColor;
  }

  public void setChecked(boolean paramBoolean)
  {
    checkWidget();
    Table localTable = this.parent.getTable();
    if ((localTable.getStyle() & 0x20) == 0)
      return;
    if (this.tableItem != null)
      this.tableItem.setChecked(paramBoolean);
    this.checked = paramBoolean;
  }

  public void setGrayed(boolean paramBoolean)
  {
    checkWidget();
    Table localTable = this.parent.getTable();
    if ((localTable.getStyle() & 0x20) == 0)
      return;
    if (this.tableItem != null)
      this.tableItem.setGrayed(paramBoolean);
    this.grayed = paramBoolean;
  }

  public void setExpanded(boolean paramBoolean)
  {
    checkWidget();
    if (this.items.length == 0)
      return;
    if (this.expanded == paramBoolean)
      return;
    this.expanded = paramBoolean;
    if (this.tableItem == null)
      return;
    this.parent.setRedraw(false);
    for (int i = 0; i < this.items.length; i++)
      this.items[i].setVisible(paramBoolean);
    Image localImage = paramBoolean ? this.parent.getMinusImage() : this.parent.getPlusImage();
    this.tableItem.setImage(0, localImage);
    this.parent.setRedraw(true);
  }

  public void setFont(Font paramFont)
  {
    checkWidget();
    if ((paramFont != null) && (paramFont.isDisposed()))
      SWT.error(5);
    if (this.tableItem != null)
      this.tableItem.setFont(paramFont);
    this.font = paramFont;
  }

  public void setForeground(Color paramColor)
  {
    checkWidget();
    if ((paramColor != null) && (paramColor.isDisposed()))
      SWT.error(5);
    if (this.tableItem != null)
      this.tableItem.setForeground(paramColor);
    this.foreground = paramColor;
  }

  public void setImage(int paramInt, Image paramImage)
  {
    checkWidget();
    int i = Math.max(this.parent.getTable().getColumnCount(), 1);
    if ((paramInt <= 0) || (paramInt >= i))
      return;
    if (this.images.length < i)
    {
      Image[] arrayOfImage = new Image[i];
      System.arraycopy(this.images, 0, arrayOfImage, 0, this.images.length);
      this.images = arrayOfImage;
    }
    this.images[paramInt] = paramImage;
    if (this.tableItem != null)
      this.tableItem.setImage(paramInt, paramImage);
  }

  public void setImage(Image paramImage)
  {
    setImage(0, paramImage);
  }

  public void setText(int paramInt, String paramString)
  {
    checkWidget();
    if (paramString == null)
      SWT.error(4);
    int i = Math.max(this.parent.getTable().getColumnCount(), 1);
    if ((paramInt < 0) || (paramInt >= i))
      return;
    if (this.texts.length < i)
    {
      String[] arrayOfString = new String[i];
      System.arraycopy(this.texts, 0, arrayOfString, 0, this.texts.length);
      this.texts = arrayOfString;
    }
    this.texts[paramInt] = paramString;
    if (this.tableItem != null)
      this.tableItem.setText(paramInt, paramString);
  }

  public void setText(String paramString)
  {
    setText(0, paramString);
  }

  void setVisible(boolean paramBoolean)
  {
    if (this.parentItem == null)
      return;
    if (getVisible() == paramBoolean)
      return;
    int j;
    if (paramBoolean)
    {
      if (!this.parentItem.getVisible())
        return;
      Table localTable = this.parent.getTable();
      j = localTable.indexOf(this.parentItem.tableItem);
      int k = this.parentItem.expandedIndexOf(this) + j + 1;
      if (k < 0)
        return;
      this.tableItem = new TableItem(localTable, getStyle(), k);
      this.tableItem.setData("TableTreeItemID", this);
      this.tableItem.setImageIndent(getIndent());
      if (this.background != null)
        this.tableItem.setBackground(this.background);
      if (this.foreground != null)
        this.tableItem.setForeground(this.foreground);
      if (this.font != null)
        this.tableItem.setFont(this.font);
      addCheck();
      int m = Math.max(localTable.getColumnCount(), 1);
      for (int n = 0; n < m; n++)
      {
        if ((n < this.texts.length) && (this.texts[n] != null))
          setText(n, this.texts[n]);
        if ((n < this.images.length) && (this.images[n] != null))
          setImage(n, this.images[n]);
      }
      if (this.items.length != 0)
        if (this.expanded)
        {
          this.tableItem.setImage(0, this.parent.getMinusImage());
          n = 0;
          int i1 = this.items.length;
          while (n < i1)
          {
            this.items[n].setVisible(true);
            n++;
          }
        }
        else
        {
          this.tableItem.setImage(0, this.parent.getPlusImage());
        }
    }
    else
    {
      int i = 0;
      j = this.items.length;
      while (i < j)
      {
        this.items[i].setVisible(false);
        i++;
      }
      this.tableItem.dispose();
      this.tableItem = null;
    }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.custom.TableTreeItem
 * JD-Core Version:    0.6.2
 */