package org.eclipse.swt.accessibility;

import org.eclipse.swt.internal.SWTEventListener;

public abstract interface AccessibleTableListener extends SWTEventListener
{
  public abstract void deselectColumn(AccessibleTableEvent paramAccessibleTableEvent);

  public abstract void deselectRow(AccessibleTableEvent paramAccessibleTableEvent);

  public abstract void getCaption(AccessibleTableEvent paramAccessibleTableEvent);

  public abstract void getCell(AccessibleTableEvent paramAccessibleTableEvent);

  public abstract void getColumn(AccessibleTableEvent paramAccessibleTableEvent);

  public abstract void getColumnCount(AccessibleTableEvent paramAccessibleTableEvent);

  public abstract void getColumnDescription(AccessibleTableEvent paramAccessibleTableEvent);

  public abstract void getColumnHeader(AccessibleTableEvent paramAccessibleTableEvent);

  public abstract void getColumnHeaderCells(AccessibleTableEvent paramAccessibleTableEvent);

  public abstract void getColumns(AccessibleTableEvent paramAccessibleTableEvent);

  public abstract void getRow(AccessibleTableEvent paramAccessibleTableEvent);

  public abstract void getRowCount(AccessibleTableEvent paramAccessibleTableEvent);

  public abstract void getRowDescription(AccessibleTableEvent paramAccessibleTableEvent);

  public abstract void getRowHeader(AccessibleTableEvent paramAccessibleTableEvent);

  public abstract void getRowHeaderCells(AccessibleTableEvent paramAccessibleTableEvent);

  public abstract void getRows(AccessibleTableEvent paramAccessibleTableEvent);

  public abstract void getSelectedCellCount(AccessibleTableEvent paramAccessibleTableEvent);

  public abstract void getSelectedCells(AccessibleTableEvent paramAccessibleTableEvent);

  public abstract void getSelectedColumnCount(AccessibleTableEvent paramAccessibleTableEvent);

  public abstract void getSelectedColumns(AccessibleTableEvent paramAccessibleTableEvent);

  public abstract void getSelectedRowCount(AccessibleTableEvent paramAccessibleTableEvent);

  public abstract void getSelectedRows(AccessibleTableEvent paramAccessibleTableEvent);

  public abstract void getSummary(AccessibleTableEvent paramAccessibleTableEvent);

  public abstract void getVisibleColumns(AccessibleTableEvent paramAccessibleTableEvent);

  public abstract void getVisibleRows(AccessibleTableEvent paramAccessibleTableEvent);

  public abstract void isColumnSelected(AccessibleTableEvent paramAccessibleTableEvent);

  public abstract void isRowSelected(AccessibleTableEvent paramAccessibleTableEvent);

  public abstract void selectColumn(AccessibleTableEvent paramAccessibleTableEvent);

  public abstract void selectRow(AccessibleTableEvent paramAccessibleTableEvent);

  public abstract void setSelectedColumn(AccessibleTableEvent paramAccessibleTableEvent);

  public abstract void setSelectedRow(AccessibleTableEvent paramAccessibleTableEvent);
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.accessibility.AccessibleTableListener
 * JD-Core Version:    0.6.2
 */