package org.eclipse.swt.dnd;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;

public class DropTargetEffect extends DropTargetAdapter
{
  Control control;

  public DropTargetEffect(Control paramControl)
  {
    if (paramControl == null)
      SWT.error(4);
    this.control = paramControl;
  }

  public Control getControl()
  {
    return this.control;
  }

  public Widget getItem(int paramInt1, int paramInt2)
  {
    if ((this.control instanceof Table))
      return getItem((Table)this.control, paramInt1, paramInt2);
    if ((this.control instanceof Tree))
      return getItem((Tree)this.control, paramInt1, paramInt2);
    return null;
  }

  Widget getItem(Table paramTable, int paramInt1, int paramInt2)
  {
    Point localPoint = new Point(paramInt1, paramInt2);
    localPoint = paramTable.toControl(localPoint);
    TableItem localTableItem = paramTable.getItem(localPoint);
    if (localTableItem != null)
      return localTableItem;
    Rectangle localRectangle1 = paramTable.getClientArea();
    int i = localRectangle1.y + localRectangle1.height;
    int j = paramTable.getItemCount();
    for (int k = paramTable.getTopIndex(); k < j; k++)
    {
      localTableItem = paramTable.getItem(k);
      Rectangle localRectangle2 = localTableItem.getBounds();
      localRectangle2.x = localRectangle1.x;
      localRectangle2.width = localRectangle1.width;
      if (localRectangle2.contains(localPoint))
        return localTableItem;
      if (localRectangle2.y > i)
        break;
    }
    return null;
  }

  Widget getItem(Tree paramTree, int paramInt1, int paramInt2)
  {
    Point localPoint = new Point(paramInt1, paramInt2);
    localPoint = paramTree.toControl(localPoint);
    TreeItem localTreeItem = paramTree.getItem(localPoint);
    if (localTreeItem == null)
    {
      Rectangle localRectangle1 = paramTree.getClientArea();
      if (localRectangle1.contains(localPoint))
      {
        int i = localRectangle1.y + localRectangle1.height;
        for (localTreeItem = paramTree.getTopItem(); localTreeItem != null; localTreeItem = nextItem(paramTree, localTreeItem))
        {
          Rectangle localRectangle2 = localTreeItem.getBounds();
          int j = localRectangle2.y + localRectangle2.height;
          if ((localRectangle2.y <= localPoint.y) && (localPoint.y < j))
            return localTreeItem;
          if (j > i)
            break;
        }
        return null;
      }
    }
    return localTreeItem;
  }

  TreeItem nextItem(Tree paramTree, TreeItem paramTreeItem)
  {
    if (paramTreeItem == null)
      return null;
    if ((paramTreeItem.getExpanded()) && (paramTreeItem.getItemCount() > 0))
      return paramTreeItem.getItem(0);
    Object localObject = paramTreeItem;
    TreeItem localTreeItem = ((TreeItem)localObject).getParentItem();
    int i = localTreeItem == null ? paramTree.indexOf((TreeItem)localObject) : localTreeItem.indexOf((TreeItem)localObject);
    for (int j = localTreeItem == null ? paramTree.getItemCount() : localTreeItem.getItemCount(); ; j = localTreeItem == null ? paramTree.getItemCount() : localTreeItem.getItemCount())
    {
      if (i + 1 < j)
        return localTreeItem == null ? paramTree.getItem(i + 1) : localTreeItem.getItem(i + 1);
      if (localTreeItem == null)
        return null;
      localObject = localTreeItem;
      localTreeItem = ((TreeItem)localObject).getParentItem();
      i = localTreeItem == null ? paramTree.indexOf((TreeItem)localObject) : localTreeItem.indexOf((TreeItem)localObject);
    }
  }

  TreeItem previousItem(Tree paramTree, TreeItem paramTreeItem)
  {
    if (paramTreeItem == null)
      return null;
    TreeItem localTreeItem1 = paramTreeItem;
    TreeItem localTreeItem2 = localTreeItem1.getParentItem();
    int i = localTreeItem2 == null ? paramTree.indexOf(localTreeItem1) : localTreeItem2.indexOf(localTreeItem1);
    if (i == 0)
      return localTreeItem2;
    TreeItem localTreeItem3 = localTreeItem2 == null ? paramTree.getItem(i - 1) : localTreeItem2.getItem(i - 1);
    for (int j = localTreeItem3.getItemCount(); (j > 0) && (localTreeItem3.getExpanded()); j = localTreeItem3.getItemCount())
      localTreeItem3 = localTreeItem3.getItem(j - 1);
    return localTreeItem3;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.dnd.DropTargetEffect
 * JD-Core Version:    0.6.2
 */