package org.eclipse.swt.custom;

import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class TableEditor extends ControlEditor
{
  Table table;
  TableItem item;
  int column = -1;
  ControlListener columnListener;
  Runnable timer;
  static final int TIMEOUT = 1500;

  public TableEditor(Table paramTable)
  {
    super(paramTable);
    this.table = paramTable;
    this.columnListener = new ControlListener()
    {
      public void controlMoved(ControlEvent paramAnonymousControlEvent)
      {
        TableEditor.this.layout();
      }

      public void controlResized(ControlEvent paramAnonymousControlEvent)
      {
        TableEditor.this.layout();
      }
    };
    this.timer = new Runnable()
    {
      public void run()
      {
        TableEditor.this.layout();
      }
    };
    this.grabVertical = true;
  }

  Rectangle computeBounds()
  {
    if ((this.item == null) || (this.column == -1) || (this.item.isDisposed()))
      return new Rectangle(0, 0, 0, 0);
    Rectangle localRectangle1 = this.item.getBounds(this.column);
    Rectangle localRectangle2 = this.item.getImageBounds(this.column);
    localRectangle2.x += localRectangle2.width;
    localRectangle1.width -= localRectangle2.width;
    Rectangle localRectangle3 = this.table.getClientArea();
    if ((localRectangle1.x < localRectangle3.x + localRectangle3.width) && (localRectangle1.x + localRectangle1.width > localRectangle3.x + localRectangle3.width))
      localRectangle1.width = (localRectangle3.x + localRectangle3.width - localRectangle1.x);
    Rectangle localRectangle4 = new Rectangle(localRectangle1.x, localRectangle1.y, this.minimumWidth, this.minimumHeight);
    if (this.grabHorizontal)
      localRectangle4.width = Math.max(localRectangle1.width, this.minimumWidth);
    if (this.grabVertical)
      localRectangle4.height = Math.max(localRectangle1.height, this.minimumHeight);
    if (this.horizontalAlignment == 131072)
      localRectangle4.x += localRectangle1.width - localRectangle4.width;
    else if (this.horizontalAlignment != 16384)
      localRectangle4.x += (localRectangle1.width - localRectangle4.width) / 2;
    if (this.verticalAlignment == 1024)
      localRectangle4.y += localRectangle1.height - localRectangle4.height;
    else if (this.verticalAlignment != 128)
      localRectangle4.y += (localRectangle1.height - localRectangle4.height) / 2;
    return localRectangle4;
  }

  public void dispose()
  {
    if ((this.table != null) && (!this.table.isDisposed()) && (this.column > -1) && (this.column < this.table.getColumnCount()))
    {
      TableColumn localTableColumn = this.table.getColumn(this.column);
      localTableColumn.removeControlListener(this.columnListener);
    }
    this.columnListener = null;
    this.table = null;
    this.item = null;
    this.column = -1;
    this.timer = null;
    super.dispose();
  }

  public int getColumn()
  {
    return this.column;
  }

  public TableItem getItem()
  {
    return this.item;
  }

  void resize()
  {
    layout();
    if (this.table != null)
    {
      Display localDisplay = this.table.getDisplay();
      localDisplay.timerExec(-1, this.timer);
      localDisplay.timerExec(1500, this.timer);
    }
  }

  public void setColumn(int paramInt)
  {
    int i = this.table.getColumnCount();
    if (i == 0)
    {
      this.column = (paramInt == 0 ? 0 : -1);
      resize();
      return;
    }
    if ((this.column > -1) && (this.column < i))
    {
      localTableColumn = this.table.getColumn(this.column);
      localTableColumn.removeControlListener(this.columnListener);
      this.column = -1;
    }
    if ((paramInt < 0) || (paramInt >= this.table.getColumnCount()))
      return;
    this.column = paramInt;
    TableColumn localTableColumn = this.table.getColumn(this.column);
    localTableColumn.addControlListener(this.columnListener);
    resize();
  }

  public void setItem(TableItem paramTableItem)
  {
    this.item = paramTableItem;
    resize();
  }

  public void setEditor(Control paramControl)
  {
    super.setEditor(paramControl);
    resize();
  }

  public void setEditor(Control paramControl, TableItem paramTableItem, int paramInt)
  {
    setItem(paramTableItem);
    setColumn(paramInt);
    setEditor(paramControl);
  }

  public void layout()
  {
    if ((this.table == null) || (this.table.isDisposed()))
      return;
    if ((this.item == null) || (this.item.isDisposed()))
      return;
    int i = this.table.getColumnCount();
    if ((i == 0) && (this.column != 0))
      return;
    if ((i > 0) && ((this.column < 0) || (this.column >= i)))
      return;
    super.layout();
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.custom.TableEditor
 * JD-Core Version:    0.6.2
 */