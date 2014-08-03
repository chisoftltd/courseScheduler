package org.eclipse.swt.custom;

import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.TreeEvent;
import org.eclipse.swt.events.TreeListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

/** @deprecated */
public class TableTreeEditor extends ControlEditor
{
  TableTree tableTree;
  TableTreeItem item;
  int column = -1;
  ControlListener columnListener;
  TreeListener treeListener;

  public TableTreeEditor(TableTree paramTableTree)
  {
    super(paramTableTree.getTable());
    this.tableTree = paramTableTree;
    this.treeListener = new TreeListener()
    {
      final Runnable runnable = new TableTreeEditor.2(this);

      public void treeCollapsed(TreeEvent paramAnonymousTreeEvent)
      {
        if ((TableTreeEditor.this.editor == null) || (TableTreeEditor.this.editor.isDisposed()))
          return;
        TableTreeEditor.this.editor.setVisible(false);
        paramAnonymousTreeEvent.display.asyncExec(this.runnable);
      }

      public void treeExpanded(TreeEvent paramAnonymousTreeEvent)
      {
        if ((TableTreeEditor.this.editor == null) || (TableTreeEditor.this.editor.isDisposed()))
          return;
        TableTreeEditor.this.editor.setVisible(false);
        paramAnonymousTreeEvent.display.asyncExec(this.runnable);
      }
    };
    paramTableTree.addTreeListener(this.treeListener);
    this.columnListener = new ControlListener()
    {
      public void controlMoved(ControlEvent paramAnonymousControlEvent)
      {
        TableTreeEditor.this.layout();
      }

      public void controlResized(ControlEvent paramAnonymousControlEvent)
      {
        TableTreeEditor.this.layout();
      }
    };
    this.grabVertical = true;
  }

  Rectangle computeBounds()
  {
    if ((this.item == null) || (this.column == -1) || (this.item.isDisposed()) || (this.item.tableItem == null))
      return new Rectangle(0, 0, 0, 0);
    Rectangle localRectangle1 = this.item.getBounds(this.column);
    Rectangle localRectangle2 = this.tableTree.getClientArea();
    if ((localRectangle1.x < localRectangle2.x + localRectangle2.width) && (localRectangle1.x + localRectangle1.width > localRectangle2.x + localRectangle2.width))
      localRectangle1.width = (localRectangle2.x + localRectangle2.width - localRectangle1.x);
    Rectangle localRectangle3 = new Rectangle(localRectangle1.x, localRectangle1.y, this.minimumWidth, this.minimumHeight);
    if (this.grabHorizontal)
      localRectangle3.width = Math.max(localRectangle1.width, this.minimumWidth);
    if (this.grabVertical)
      localRectangle3.height = Math.max(localRectangle1.height, this.minimumHeight);
    if (this.horizontalAlignment == 131072)
      localRectangle3.x += localRectangle1.width - localRectangle3.width;
    else if (this.horizontalAlignment != 16384)
      localRectangle3.x += (localRectangle1.width - localRectangle3.width) / 2;
    if (this.verticalAlignment == 1024)
      localRectangle3.y += localRectangle1.height - localRectangle3.height;
    else if (this.verticalAlignment != 128)
      localRectangle3.y += (localRectangle1.height - localRectangle3.height) / 2;
    return localRectangle3;
  }

  public void dispose()
  {
    if ((this.tableTree != null) && (!this.tableTree.isDisposed()))
    {
      Table localTable = this.tableTree.getTable();
      if ((localTable != null) && (!localTable.isDisposed()) && (this.column > -1) && (this.column < localTable.getColumnCount()))
      {
        TableColumn localTableColumn = localTable.getColumn(this.column);
        localTableColumn.removeControlListener(this.columnListener);
      }
      if (this.treeListener != null)
        this.tableTree.removeTreeListener(this.treeListener);
    }
    this.treeListener = null;
    this.columnListener = null;
    this.tableTree = null;
    this.item = null;
    this.column = -1;
    super.dispose();
  }

  public int getColumn()
  {
    return this.column;
  }

  public TableTreeItem getItem()
  {
    return this.item;
  }

  public void setColumn(int paramInt)
  {
    Table localTable = this.tableTree.getTable();
    int i = localTable.getColumnCount();
    if (i == 0)
    {
      this.column = (paramInt == 0 ? 0 : -1);
      layout();
      return;
    }
    if ((this.column > -1) && (this.column < i))
    {
      localTableColumn = localTable.getColumn(this.column);
      localTableColumn.removeControlListener(this.columnListener);
      this.column = -1;
    }
    if ((paramInt < 0) || (paramInt >= localTable.getColumnCount()))
      return;
    this.column = paramInt;
    TableColumn localTableColumn = localTable.getColumn(this.column);
    localTableColumn.addControlListener(this.columnListener);
    layout();
  }

  public void setItem(TableTreeItem paramTableTreeItem)
  {
    this.item = paramTableTreeItem;
    layout();
  }

  public void setEditor(Control paramControl, TableTreeItem paramTableTreeItem, int paramInt)
  {
    setItem(paramTableTreeItem);
    setColumn(paramInt);
    setEditor(paramControl);
  }

  public void layout()
  {
    if ((this.tableTree == null) || (this.tableTree.isDisposed()))
      return;
    if ((this.item == null) || (this.item.isDisposed()))
      return;
    Table localTable = this.tableTree.getTable();
    int i = localTable.getColumnCount();
    if ((i == 0) && (this.column != 0))
      return;
    if ((i > 0) && ((this.column < 0) || (this.column >= i)))
      return;
    super.layout();
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.custom.TableTreeEditor
 * JD-Core Version:    0.6.2
 */