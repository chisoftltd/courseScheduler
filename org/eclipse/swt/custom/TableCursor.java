package org.eclipse.swt.custom;

import org.eclipse.swt.SWT;
import org.eclipse.swt.accessibility.Accessible;
import org.eclipse.swt.accessibility.AccessibleAdapter;
import org.eclipse.swt.accessibility.AccessibleControlAdapter;
import org.eclipse.swt.accessibility.AccessibleControlEvent;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.TypedListener;

public class TableCursor extends Canvas
{
  Table table;
  TableItem row = null;
  TableColumn column = null;
  Listener listener;
  Listener tableListener;
  Listener resizeListener;
  Listener disposeItemListener;
  Listener disposeColumnListener;
  Color background = null;
  Color foreground = null;
  static final int BACKGROUND = 27;
  static final int FOREGROUND = 26;

  public TableCursor(Table paramTable, int paramInt)
  {
    super(paramTable, paramInt);
    this.table = paramTable;
    setBackground(null);
    setForeground(null);
    this.listener = new Listener()
    {
      public void handleEvent(Event paramAnonymousEvent)
      {
        switch (paramAnonymousEvent.type)
        {
        case 12:
          TableCursor.this.onDispose(paramAnonymousEvent);
          break;
        case 15:
        case 16:
          TableCursor.this.redraw();
          break;
        case 1:
          TableCursor.this.keyDown(paramAnonymousEvent);
          break;
        case 9:
          TableCursor.this.paint(paramAnonymousEvent);
          break;
        case 31:
          paramAnonymousEvent.doit = true;
          switch (paramAnonymousEvent.detail)
          {
          case 4:
          case 32:
          case 64:
            paramAnonymousEvent.doit = false;
          }
          break;
        }
      }
    };
    int[] arrayOfInt = { 12, 15, 16, 1, 9, 31 };
    for (int i = 0; i < arrayOfInt.length; i++)
      addListener(arrayOfInt[i], this.listener);
    this.tableListener = new Listener()
    {
      public void handleEvent(Event paramAnonymousEvent)
      {
        switch (paramAnonymousEvent.type)
        {
        case 3:
          TableCursor.this.tableMouseDown(paramAnonymousEvent);
          break;
        case 15:
          TableCursor.this.tableFocusIn(paramAnonymousEvent);
        }
      }
    };
    this.table.addListener(15, this.tableListener);
    this.table.addListener(3, this.tableListener);
    this.disposeItemListener = new Listener()
    {
      public void handleEvent(Event paramAnonymousEvent)
      {
        TableCursor.this.unhookRowColumnListeners();
        TableCursor.this.row = null;
        TableCursor.this.column = null;
        TableCursor.this._resize();
      }
    };
    this.disposeColumnListener = new Listener()
    {
      public void handleEvent(Event paramAnonymousEvent)
      {
        TableCursor.this.unhookRowColumnListeners();
        TableCursor.this.row = null;
        TableCursor.this.column = null;
        TableCursor.this._resize();
      }
    };
    this.resizeListener = new Listener()
    {
      public void handleEvent(Event paramAnonymousEvent)
      {
        TableCursor.this._resize();
      }
    };
    ScrollBar localScrollBar1 = this.table.getHorizontalBar();
    if (localScrollBar1 != null)
      localScrollBar1.addListener(13, this.resizeListener);
    ScrollBar localScrollBar2 = this.table.getVerticalBar();
    if (localScrollBar2 != null)
      localScrollBar2.addListener(13, this.resizeListener);
    getAccessible().addAccessibleControlListener(new AccessibleControlAdapter()
    {
      public void getRole(AccessibleControlEvent paramAnonymousAccessibleControlEvent)
      {
        paramAnonymousAccessibleControlEvent.detail = 29;
      }
    });
    getAccessible().addAccessibleListener(new AccessibleAdapter()
    {
      public void getName(AccessibleEvent paramAnonymousAccessibleEvent)
      {
        if (TableCursor.this.row == null)
          return;
        int i = TableCursor.this.column == null ? 0 : TableCursor.this.table.indexOf(TableCursor.this.column);
        paramAnonymousAccessibleEvent.result = TableCursor.this.row.getText(i);
      }
    });
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

  void onDispose(Event paramEvent)
  {
    removeListener(12, this.listener);
    notifyListeners(12, paramEvent);
    paramEvent.type = 0;
    this.table.removeListener(15, this.tableListener);
    this.table.removeListener(3, this.tableListener);
    unhookRowColumnListeners();
    ScrollBar localScrollBar1 = this.table.getHorizontalBar();
    if (localScrollBar1 != null)
      localScrollBar1.removeListener(13, this.resizeListener);
    ScrollBar localScrollBar2 = this.table.getVerticalBar();
    if (localScrollBar2 != null)
      localScrollBar2.removeListener(13, this.resizeListener);
  }

  void keyDown(Event paramEvent)
  {
    if (this.row == null)
      return;
    switch (paramEvent.character)
    {
    case '\r':
      notifyListeners(14, new Event());
      return;
    }
    int i = this.table.indexOf(this.row);
    int j = this.column == null ? 0 : this.table.indexOf(this.column);
    int k;
    Object localObject;
    TableItem localTableItem;
    Rectangle localRectangle;
    int i1;
    int i2;
    switch (paramEvent.keyCode)
    {
    case 16777217:
      setRowColumn(Math.max(0, i - 1), j, true);
      break;
    case 16777218:
      setRowColumn(Math.min(i + 1, this.table.getItemCount() - 1), j, true);
      break;
    case 16777219:
    case 16777220:
      k = this.table.getColumnCount();
      if (k != 0)
      {
        localObject = this.table.getColumnOrder();
        for (int m = 0; m < localObject.length; m++)
          if (localObject[m] == j)
            break;
        if (m == localObject.length)
          m = 0;
        int n = (getStyle() & 0x4000000) != 0 ? 16777220 : 16777219;
        if (paramEvent.keyCode == n)
          setRowColumn(i, localObject[Math.max(0, m - 1)], true);
        else
          setRowColumn(i, localObject[Math.min(k - 1, m + 1)], true);
      }
      break;
    case 16777223:
      setRowColumn(0, j, true);
      break;
    case 16777224:
      k = this.table.getItemCount() - 1;
      setRowColumn(k, j, true);
      break;
    case 16777221:
      k = this.table.getTopIndex();
      if (k == i)
      {
        localObject = this.table.getClientArea();
        localTableItem = this.table.getItem(k);
        localRectangle = localTableItem.getBounds(0);
        localObject.height -= localRectangle.y;
        i1 = this.table.getItemHeight();
        i2 = Math.max(1, ((Rectangle)localObject).height / i1);
        k = Math.max(0, k - i2 + 1);
      }
      setRowColumn(k, j, true);
      break;
    case 16777222:
      k = this.table.getTopIndex();
      localObject = this.table.getClientArea();
      localTableItem = this.table.getItem(k);
      localRectangle = localTableItem.getBounds(0);
      localObject.height -= localRectangle.y;
      i1 = this.table.getItemHeight();
      i2 = Math.max(1, ((Rectangle)localObject).height / i1);
      int i3 = this.table.getItemCount() - 1;
      k = Math.min(i3, k + i2 - 1);
      if (k == i)
        k = Math.min(i3, k + i2 - 1);
      setRowColumn(k, j, true);
    }
  }

  void paint(Event paramEvent)
  {
    if (this.row == null)
      return;
    int i = this.column == null ? 0 : this.table.indexOf(this.column);
    GC localGC = paramEvent.gc;
    Display localDisplay = getDisplay();
    localGC.setBackground(getBackground());
    localGC.setForeground(getForeground());
    localGC.fillRectangle(paramEvent.x, paramEvent.y, paramEvent.width, paramEvent.height);
    int j = 0;
    Point localPoint1 = getSize();
    Image localImage = this.row.getImage(i);
    if (localImage != null)
    {
      localObject = localImage.getBounds();
      int k = (localPoint1.y - ((Rectangle)localObject).height) / 2;
      localGC.drawImage(localImage, j, k);
      j += ((Rectangle)localObject).width;
    }
    Object localObject = this.row.getText(i);
    if (((String)localObject).length() > 0)
    {
      Rectangle localRectangle = this.row.getBounds(i);
      Point localPoint2 = localGC.stringExtent((String)localObject);
      String str = SWT.getPlatform();
      if ("win32".equals(str))
      {
        if ((this.table.getColumnCount() == 0) || (i == 0))
        {
          j += 2;
        }
        else
        {
          m = this.column.getAlignment();
          switch (m)
          {
          case 16384:
            j += 6;
            break;
          case 131072:
            j = localRectangle.width - localPoint2.x - 6;
            break;
          case 16777216:
            j += (localRectangle.width - j - localPoint2.x) / 2;
          default:
            break;
          }
        }
      }
      else if (this.table.getColumnCount() == 0)
      {
        j += 5;
      }
      else
      {
        m = this.column.getAlignment();
        switch (m)
        {
        case 16384:
          j += 5;
          break;
        case 131072:
          j = localRectangle.width - localPoint2.x - 2;
          break;
        case 16777216:
          j += (localRectangle.width - j - localPoint2.x) / 2 + 2;
        }
      }
      int m = (localPoint1.y - localPoint2.y) / 2;
      localGC.drawString((String)localObject, j, m);
    }
    if (isFocusControl())
    {
      localGC.setBackground(localDisplay.getSystemColor(2));
      localGC.setForeground(localDisplay.getSystemColor(1));
      localGC.drawFocus(0, 0, localPoint1.x, localPoint1.y);
    }
  }

  void tableFocusIn(Event paramEvent)
  {
    if (isDisposed())
      return;
    if (isVisible())
    {
      if ((this.row == null) && (this.column == null))
        return;
      setFocus();
    }
  }

  void tableMouseDown(Event paramEvent)
  {
    if ((isDisposed()) || (!isVisible()))
      return;
    Point localPoint = new Point(paramEvent.x, paramEvent.y);
    int i = this.table.getLinesVisible() ? this.table.getGridLineWidth() : 0;
    Object localObject = this.table.getItem(localPoint);
    Rectangle localRectangle1;
    if ((this.table.getStyle() & 0x10000) != 0)
    {
      if (localObject != null);
    }
    else
    {
      int j = localObject != null ? this.table.indexOf((TableItem)localObject) : this.table.getTopIndex();
      k = this.table.getItemCount();
      localRectangle1 = this.table.getClientArea();
      for (int n = j; n < k; n++)
      {
        TableItem localTableItem = this.table.getItem(n);
        Rectangle localRectangle3 = localTableItem.getBounds(0);
        if ((localPoint.y >= localRectangle3.y) && (localPoint.y < localRectangle3.y + localRectangle3.height + i))
        {
          localObject = localTableItem;
          break;
        }
        if (localRectangle3.y > localRectangle1.y + localRectangle1.height)
          return;
      }
      if (localObject == null)
        return;
    }
    TableColumn localTableColumn = null;
    int k = this.table.getColumnCount();
    if (k == 0)
    {
      if ((this.table.getStyle() & 0x10000) == 0)
      {
        localRectangle1 = ((TableItem)localObject).getBounds(0);
        localRectangle1.width += i;
        localRectangle1.height += i;
        if (localRectangle1.contains(localPoint));
      }
    }
    else
    {
      for (int m = 0; m < k; m++)
      {
        Rectangle localRectangle2 = ((TableItem)localObject).getBounds(m);
        localRectangle2.width += i;
        localRectangle2.height += i;
        if (localRectangle2.contains(localPoint))
        {
          localTableColumn = this.table.getColumn(m);
          break;
        }
      }
      if (localTableColumn == null)
      {
        if ((this.table.getStyle() & 0x10000) == 0)
          return;
        localTableColumn = this.table.getColumn(0);
      }
    }
    setRowColumn((TableItem)localObject, localTableColumn, true);
    setFocus();
  }

  void setRowColumn(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    TableItem localTableItem = paramInt1 == -1 ? null : this.table.getItem(paramInt1);
    TableColumn localTableColumn = (paramInt2 == -1) || (this.table.getColumnCount() == 0) ? null : this.table.getColumn(paramInt2);
    setRowColumn(localTableItem, localTableColumn, paramBoolean);
  }

  void setRowColumn(TableItem paramTableItem, TableColumn paramTableColumn, boolean paramBoolean)
  {
    if ((this.row == paramTableItem) && (this.column == paramTableColumn))
      return;
    if ((this.row != null) && (this.row != paramTableItem))
    {
      this.row.removeListener(12, this.disposeItemListener);
      this.row = null;
    }
    if ((this.column != null) && (this.column != paramTableColumn))
    {
      this.column.removeListener(12, this.disposeColumnListener);
      this.column.removeListener(10, this.resizeListener);
      this.column.removeListener(11, this.resizeListener);
      this.column = null;
    }
    if (paramTableItem != null)
    {
      if (this.row != paramTableItem)
      {
        this.row = paramTableItem;
        paramTableItem.addListener(12, this.disposeItemListener);
        this.table.showItem(paramTableItem);
      }
      if ((this.column != paramTableColumn) && (paramTableColumn != null))
      {
        this.column = paramTableColumn;
        paramTableColumn.addListener(12, this.disposeColumnListener);
        paramTableColumn.addListener(10, this.resizeListener);
        paramTableColumn.addListener(11, this.resizeListener);
        this.table.showColumn(paramTableColumn);
      }
      int i = paramTableColumn == null ? 0 : this.table.indexOf(paramTableColumn);
      setBounds(paramTableItem.getBounds(i));
      redraw();
      if (paramBoolean)
        notifyListeners(13, new Event());
    }
    getAccessible().setFocus(-1);
  }

  public void setVisible(boolean paramBoolean)
  {
    checkWidget();
    if (paramBoolean)
      _resize();
    super.setVisible(paramBoolean);
  }

  public void removeSelectionListener(SelectionListener paramSelectionListener)
  {
    checkWidget();
    if (paramSelectionListener == null)
      SWT.error(4);
    removeListener(13, paramSelectionListener);
    removeListener(14, paramSelectionListener);
  }

  void _resize()
  {
    if (this.row == null)
    {
      setBounds(-200, -200, 0, 0);
    }
    else
    {
      int i = this.column == null ? 0 : this.table.indexOf(this.column);
      setBounds(this.row.getBounds(i));
    }
  }

  public int getColumn()
  {
    checkWidget();
    return this.column == null ? 0 : this.table.indexOf(this.column);
  }

  public Color getBackground()
  {
    checkWidget();
    if (this.background == null)
      return getDisplay().getSystemColor(27);
    return this.background;
  }

  public Color getForeground()
  {
    checkWidget();
    if (this.foreground == null)
      return getDisplay().getSystemColor(26);
    return this.foreground;
  }

  public TableItem getRow()
  {
    checkWidget();
    return this.row;
  }

  public void setBackground(Color paramColor)
  {
    this.background = paramColor;
    super.setBackground(getBackground());
    redraw();
  }

  public void setForeground(Color paramColor)
  {
    this.foreground = paramColor;
    super.setForeground(getForeground());
    redraw();
  }

  public void setSelection(int paramInt1, int paramInt2)
  {
    checkWidget();
    int i = this.table.getColumnCount();
    int j = i == 0 ? 0 : i - 1;
    if ((paramInt1 < 0) || (paramInt1 >= this.table.getItemCount()) || (paramInt2 < 0) || (paramInt2 > j))
      SWT.error(5);
    setRowColumn(paramInt1, paramInt2, false);
  }

  public void setSelection(TableItem paramTableItem, int paramInt)
  {
    checkWidget();
    int i = this.table.getColumnCount();
    int j = i == 0 ? 0 : i - 1;
    if ((paramTableItem == null) || (paramTableItem.isDisposed()) || (paramInt < 0) || (paramInt > j))
      SWT.error(5);
    setRowColumn(this.table.indexOf(paramTableItem), paramInt, false);
  }

  void unhookRowColumnListeners()
  {
    if (this.column != null)
    {
      this.column.removeListener(12, this.disposeColumnListener);
      this.column.removeListener(10, this.resizeListener);
      this.column.removeListener(11, this.resizeListener);
      this.column = null;
    }
    if (this.row != null)
    {
      this.row.removeListener(12, this.disposeItemListener);
      this.row = null;
    }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.custom.TableCursor
 * JD-Core Version:    0.6.2
 */