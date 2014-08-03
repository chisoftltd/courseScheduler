package org.eclipse.swt.custom;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TreeListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.TypedListener;

/** @deprecated */
public class TableTree extends Composite
{
  Table table;
  TableTreeItem[] items = EMPTY_ITEMS;
  Image plusImage;
  Image minusImage;
  Image sizeImage;
  Listener listener;
  boolean inDispose = false;
  static final TableTreeItem[] EMPTY_ITEMS = new TableTreeItem[0];
  static final String[] EMPTY_TEXTS = new String[0];
  static final Image[] EMPTY_IMAGES = new Image[0];
  static final String ITEMID = "TableTreeItemID";

  public TableTree(Composite paramComposite, int paramInt)
  {
    super(paramComposite, checkStyle(paramInt));
    this.table = new Table(this, paramInt);
    Listener local1 = new Listener()
    {
      public void handleEvent(Event paramAnonymousEvent)
      {
        switch (paramAnonymousEvent.type)
        {
        case 3:
          TableTree.this.onMouseDown(paramAnonymousEvent);
          break;
        case 13:
          TableTree.this.onSelection(paramAnonymousEvent);
          break;
        case 14:
          TableTree.this.onSelection(paramAnonymousEvent);
          break;
        case 1:
          TableTree.this.onKeyDown(paramAnonymousEvent);
        }
      }
    };
    int[] arrayOfInt1 = { 3, 13, 14, 1 };
    for (int i = 0; i < arrayOfInt1.length; i++)
      this.table.addListener(arrayOfInt1[i], local1);
    this.listener = new Listener()
    {
      public void handleEvent(Event paramAnonymousEvent)
      {
        switch (paramAnonymousEvent.type)
        {
        case 12:
          TableTree.this.onDispose(paramAnonymousEvent);
          break;
        case 11:
          TableTree.this.onResize(paramAnonymousEvent);
          break;
        case 15:
          TableTree.this.onFocusIn(paramAnonymousEvent);
        case 13:
        case 14:
        }
      }
    };
    int[] arrayOfInt2 = { 12, 11, 15 };
    for (int j = 0; j < arrayOfInt2.length; j++)
      addListener(arrayOfInt2[j], this.listener);
  }

  int addItem(TableTreeItem paramTableTreeItem, int paramInt)
  {
    if ((paramInt < 0) || (paramInt > this.items.length))
      SWT.error(5);
    TableTreeItem[] arrayOfTableTreeItem = new TableTreeItem[this.items.length + 1];
    System.arraycopy(this.items, 0, arrayOfTableTreeItem, 0, paramInt);
    arrayOfTableTreeItem[paramInt] = paramTableTreeItem;
    System.arraycopy(this.items, paramInt, arrayOfTableTreeItem, paramInt + 1, this.items.length - paramInt);
    this.items = arrayOfTableTreeItem;
    if (paramInt == this.items.length - 1)
      return this.table.getItemCount();
    return this.table.indexOf(this.items[(paramInt + 1)].tableItem);
  }

  public void addSelectionListener(SelectionListener paramSelectionListener)
  {
    checkWidget();
    if (paramSelectionListener == null)
      SWT.error(4);
    TypedListener localTypedListener = new TypedListener(paramSelectionListener);
    addListener(13, localTypedListener);
    addListener(14, localTypedListener);
  }

  public void addTreeListener(TreeListener paramTreeListener)
  {
    checkWidget();
    if (paramTreeListener == null)
      SWT.error(4);
    TypedListener localTypedListener = new TypedListener(paramTreeListener);
    addListener(17, localTypedListener);
    addListener(18, localTypedListener);
  }

  private static int checkStyle(int paramInt)
  {
    int i = 100663296;
    paramInt &= i;
    return paramInt;
  }

  public Point computeSize(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    checkWidget();
    return this.table.computeSize(paramInt1, paramInt2, paramBoolean);
  }

  public Rectangle computeTrim(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    checkWidget();
    return this.table.computeTrim(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  public void deselectAll()
  {
    checkWidget();
    this.table.deselectAll();
  }

  void expandItem(TableTreeItem paramTableTreeItem)
  {
    if (paramTableTreeItem == null)
      return;
    expandItem(paramTableTreeItem.parentItem);
    if (!paramTableTreeItem.getVisible())
      paramTableTreeItem.setVisible(true);
    if ((!paramTableTreeItem.expanded) && (paramTableTreeItem.items.length > 0))
    {
      paramTableTreeItem.setExpanded(true);
      Event localEvent = new Event();
      localEvent.item = paramTableTreeItem;
      notifyListeners(17, localEvent);
    }
  }

  public Color getBackground()
  {
    return this.table.getBackground();
  }

  public Rectangle getClientArea()
  {
    return this.table.getClientArea();
  }

  public Color getForeground()
  {
    return this.table.getForeground();
  }

  public Font getFont()
  {
    return this.table.getFont();
  }

  public int getItemCount()
  {
    return this.items.length;
  }

  public int getItemHeight()
  {
    checkWidget();
    return this.table.getItemHeight();
  }

  public TableTreeItem[] getItems()
  {
    TableTreeItem[] arrayOfTableTreeItem = new TableTreeItem[this.items.length];
    System.arraycopy(this.items, 0, arrayOfTableTreeItem, 0, this.items.length);
    return arrayOfTableTreeItem;
  }

  public TableTreeItem[] getSelection()
  {
    checkWidget();
    TableItem[] arrayOfTableItem = this.table.getSelection();
    TableTreeItem[] arrayOfTableTreeItem = new TableTreeItem[arrayOfTableItem.length];
    for (int i = 0; i < arrayOfTableItem.length; i++)
      arrayOfTableTreeItem[i] = ((TableTreeItem)arrayOfTableItem[i].getData("TableTreeItemID"));
    return arrayOfTableTreeItem;
  }

  public int getSelectionCount()
  {
    checkWidget();
    return this.table.getSelectionCount();
  }

  public int getStyle()
  {
    checkWidget();
    return this.table.getStyle();
  }

  public Table getTable()
  {
    return this.table;
  }

  void createImages()
  {
    int i = this.sizeImage.getBounds().height;
    int j = Math.min(6, (i - 9) / 2);
    j = Math.max(0, j);
    int k = Math.max(10, i - 2 * j);
    k = (k + 1) / 2 * 2;
    int m = j + k / 2;
    Color localColor1 = getForeground();
    Color localColor2 = getDisplay().getSystemColor(18);
    Color localColor3 = getBackground();
    PaletteData localPaletteData = new PaletteData(new RGB[] { localColor1.getRGB(), localColor3.getRGB(), localColor2.getRGB() });
    ImageData localImageData = new ImageData(i, i, 4, localPaletteData);
    localImageData.transparentPixel = 1;
    this.plusImage = new Image(getDisplay(), localImageData);
    GC localGC = new GC(this.plusImage);
    localGC.setBackground(localColor3);
    localGC.fillRectangle(0, 0, i, i);
    localGC.setForeground(localColor2);
    localGC.drawRectangle(j, j, k, k);
    localGC.setForeground(localColor1);
    localGC.drawLine(m, j + 2, m, j + k - 2);
    localGC.drawLine(j + 2, m, j + k - 2, m);
    localGC.dispose();
    localPaletteData = new PaletteData(new RGB[] { localColor1.getRGB(), localColor3.getRGB(), localColor2.getRGB() });
    localImageData = new ImageData(i, i, 4, localPaletteData);
    localImageData.transparentPixel = 1;
    this.minusImage = new Image(getDisplay(), localImageData);
    localGC = new GC(this.minusImage);
    localGC.setBackground(localColor3);
    localGC.fillRectangle(0, 0, i, i);
    localGC.setForeground(localColor2);
    localGC.drawRectangle(j, j, k, k);
    localGC.setForeground(localColor1);
    localGC.drawLine(j + 2, m, j + k - 2, m);
    localGC.dispose();
  }

  Image getPlusImage()
  {
    if (this.plusImage == null)
      createImages();
    return this.plusImage;
  }

  Image getMinusImage()
  {
    if (this.minusImage == null)
      createImages();
    return this.minusImage;
  }

  public int indexOf(TableTreeItem paramTableTreeItem)
  {
    for (int i = 0; i < this.items.length; i++)
      if (paramTableTreeItem == this.items[i])
        return i;
    return -1;
  }

  void onDispose(Event paramEvent)
  {
    removeListener(12, this.listener);
    notifyListeners(12, paramEvent);
    paramEvent.type = 0;
    this.inDispose = true;
    for (int i = 0; i < this.items.length; i++)
      this.items[i].dispose();
    this.inDispose = false;
    if (this.plusImage != null)
      this.plusImage.dispose();
    if (this.minusImage != null)
      this.minusImage.dispose();
    if (this.sizeImage != null)
      this.sizeImage.dispose();
    this.plusImage = (this.minusImage = this.sizeImage = null);
  }

  void onResize(Event paramEvent)
  {
    Point localPoint = getSize();
    this.table.setBounds(0, 0, localPoint.x, localPoint.y);
  }

  void onSelection(Event paramEvent)
  {
    Event localEvent = new Event();
    TableItem localTableItem = (TableItem)paramEvent.item;
    TableTreeItem localTableTreeItem = getItem(localTableItem);
    localEvent.item = localTableTreeItem;
    if ((paramEvent.type == 13) && (paramEvent.detail == 32) && (localTableTreeItem != null))
    {
      localEvent.detail = 32;
      localTableTreeItem.checked = localTableItem.getChecked();
    }
    notifyListeners(paramEvent.type, localEvent);
  }

  public TableTreeItem getItem(int paramInt)
  {
    checkWidget();
    int i = this.items.length;
    if ((paramInt < 0) || (paramInt >= i))
      SWT.error(6);
    return this.items[paramInt];
  }

  public TableTreeItem getItem(Point paramPoint)
  {
    checkWidget();
    TableItem localTableItem = this.table.getItem(paramPoint);
    if (localTableItem == null)
      return null;
    return getItem(localTableItem);
  }

  TableTreeItem getItem(TableItem paramTableItem)
  {
    if (paramTableItem == null)
      return null;
    for (int i = 0; i < this.items.length; i++)
    {
      TableTreeItem localTableTreeItem = this.items[i].getItem(paramTableItem);
      if (localTableTreeItem != null)
        return localTableTreeItem;
    }
    return null;
  }

  void onFocusIn(Event paramEvent)
  {
    this.table.setFocus();
  }

  void onKeyDown(Event paramEvent)
  {
    TableTreeItem[] arrayOfTableTreeItem = getSelection();
    if (arrayOfTableTreeItem.length == 0)
      return;
    TableTreeItem localTableTreeItem1 = arrayOfTableTreeItem[0];
    int i = 0;
    if ((paramEvent.keyCode == 16777220) || (paramEvent.keyCode == 16777219))
    {
      int j = (getStyle() & 0x8000000) != 0 ? 16777219 : 16777220;
      TableTreeItem localTableTreeItem2;
      if (paramEvent.keyCode == j)
      {
        if (localTableTreeItem1.getItemCount() == 0)
          return;
        if (localTableTreeItem1.getExpanded())
        {
          localTableTreeItem2 = localTableTreeItem1.getItems()[0];
          this.table.setSelection(new TableItem[] { localTableTreeItem2.tableItem });
          showItem(localTableTreeItem2);
          i = 13;
        }
        else
        {
          localTableTreeItem1.setExpanded(true);
          i = 17;
        }
      }
      else if (localTableTreeItem1.getExpanded())
      {
        localTableTreeItem1.setExpanded(false);
        i = 18;
      }
      else
      {
        localTableTreeItem2 = localTableTreeItem1.getParentItem();
        if (localTableTreeItem2 != null)
        {
          int k = localTableTreeItem2.indexOf(localTableTreeItem1);
          if (k != 0)
            return;
          this.table.setSelection(new TableItem[] { localTableTreeItem2.tableItem });
          i = 13;
        }
      }
    }
    if (paramEvent.character == '*')
      localTableTreeItem1.expandAll(true);
    if ((paramEvent.character == '-') && (localTableTreeItem1.getExpanded()))
    {
      localTableTreeItem1.setExpanded(false);
      i = 18;
    }
    if ((paramEvent.character == '+') && (localTableTreeItem1.getItemCount() > 0) && (!localTableTreeItem1.getExpanded()))
    {
      localTableTreeItem1.setExpanded(true);
      i = 17;
    }
    if (i == 0)
      return;
    Event localEvent = new Event();
    localEvent.item = localTableTreeItem1;
    notifyListeners(i, localEvent);
  }

  void onMouseDown(Event paramEvent)
  {
    TableItem[] arrayOfTableItem = this.table.getItems();
    for (int i = 0; i < arrayOfTableItem.length; i++)
    {
      Rectangle localRectangle = arrayOfTableItem[i].getImageBounds(0);
      if (localRectangle.contains(paramEvent.x, paramEvent.y))
      {
        TableTreeItem localTableTreeItem = (TableTreeItem)arrayOfTableItem[i].getData("TableTreeItemID");
        paramEvent = new Event();
        paramEvent.item = localTableTreeItem;
        localTableTreeItem.setExpanded(!localTableTreeItem.getExpanded());
        if (localTableTreeItem.getExpanded())
          notifyListeners(17, paramEvent);
        else
          notifyListeners(18, paramEvent);
        return;
      }
    }
  }

  public void removeAll()
  {
    checkWidget();
    setRedraw(false);
    for (int i = this.items.length - 1; i >= 0; i--)
      this.items[i].dispose();
    this.items = EMPTY_ITEMS;
    setRedraw(true);
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
  }

  public void removeSelectionListener(SelectionListener paramSelectionListener)
  {
    checkWidget();
    if (paramSelectionListener == null)
      SWT.error(4);
    removeListener(13, paramSelectionListener);
    removeListener(14, paramSelectionListener);
  }

  public void removeTreeListener(TreeListener paramTreeListener)
  {
    checkWidget();
    if (paramTreeListener == null)
      SWT.error(4);
    removeListener(17, paramTreeListener);
    removeListener(18, paramTreeListener);
  }

  public void selectAll()
  {
    checkWidget();
    this.table.selectAll();
  }

  public void setBackground(Color paramColor)
  {
    super.setBackground(paramColor);
    this.table.setBackground(paramColor);
    if (this.sizeImage != null)
    {
      GC localGC = new GC(this.sizeImage);
      localGC.setBackground(getBackground());
      Rectangle localRectangle = this.sizeImage.getBounds();
      localGC.fillRectangle(localRectangle);
      localGC.dispose();
    }
  }

  public void setEnabled(boolean paramBoolean)
  {
    super.setEnabled(paramBoolean);
    this.table.setEnabled(paramBoolean);
  }

  public void setFont(Font paramFont)
  {
    super.setFont(paramFont);
    this.table.setFont(paramFont);
  }

  public void setForeground(Color paramColor)
  {
    super.setForeground(paramColor);
    this.table.setForeground(paramColor);
  }

  public void setMenu(Menu paramMenu)
  {
    super.setMenu(paramMenu);
    this.table.setMenu(paramMenu);
  }

  public void setSelection(TableTreeItem[] paramArrayOfTableTreeItem)
  {
    checkWidget();
    if (paramArrayOfTableTreeItem == null)
      SWT.error(4);
    int i = paramArrayOfTableTreeItem.length;
    if ((i == 0) || (((this.table.getStyle() & 0x4) != 0) && (i > 1)))
    {
      deselectAll();
      return;
    }
    TableItem[] arrayOfTableItem = new TableItem[i];
    for (int j = 0; j < i; j++)
    {
      if (paramArrayOfTableTreeItem[j] == null)
        SWT.error(4);
      if (!paramArrayOfTableTreeItem[j].getVisible())
        expandItem(paramArrayOfTableTreeItem[j]);
      arrayOfTableItem[j] = paramArrayOfTableTreeItem[j].tableItem;
    }
    this.table.setSelection(arrayOfTableItem);
  }

  public void setToolTipText(String paramString)
  {
    super.setToolTipText(paramString);
    this.table.setToolTipText(paramString);
  }

  public void showItem(TableTreeItem paramTableTreeItem)
  {
    checkWidget();
    if (paramTableTreeItem == null)
      SWT.error(4);
    if (!paramTableTreeItem.getVisible())
      expandItem(paramTableTreeItem);
    TableItem localTableItem = paramTableTreeItem.tableItem;
    this.table.showItem(localTableItem);
  }

  public void showSelection()
  {
    checkWidget();
    this.table.showSelection();
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.custom.TableTree
 * JD-Core Version:    0.6.2
 */