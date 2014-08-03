package org.eclipse.swt.accessibility;

import org.eclipse.swt.internal.SWTEventListener;

public abstract interface AccessibleTableCellListener extends SWTEventListener
{
  public abstract void getColumnSpan(AccessibleTableCellEvent paramAccessibleTableCellEvent);

  public abstract void getColumnHeaders(AccessibleTableCellEvent paramAccessibleTableCellEvent);

  public abstract void getColumnIndex(AccessibleTableCellEvent paramAccessibleTableCellEvent);

  public abstract void getRowSpan(AccessibleTableCellEvent paramAccessibleTableCellEvent);

  public abstract void getRowHeaders(AccessibleTableCellEvent paramAccessibleTableCellEvent);

  public abstract void getRowIndex(AccessibleTableCellEvent paramAccessibleTableCellEvent);

  public abstract void getTable(AccessibleTableCellEvent paramAccessibleTableCellEvent);

  public abstract void isSelected(AccessibleTableCellEvent paramAccessibleTableCellEvent);
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.accessibility.AccessibleTableCellListener
 * JD-Core Version:    0.6.2
 */