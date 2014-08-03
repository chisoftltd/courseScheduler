package org.eclipse.swt.custom;

import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.TreeEvent;
import org.eclipse.swt.events.TreeListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

public class TreeEditor extends ControlEditor
{
  Tree tree;
  TreeItem item;
  int column = 0;
  ControlListener columnListener;
  TreeListener treeListener;
  Runnable timer;
  static final int TIMEOUT = 1500;

  public TreeEditor(Tree paramTree)
  {
    super(paramTree);
    this.tree = paramTree;
    this.columnListener = new ControlListener()
    {
      public void controlMoved(ControlEvent paramAnonymousControlEvent)
      {
        TreeEditor.this.layout();
      }

      public void controlResized(ControlEvent paramAnonymousControlEvent)
      {
        TreeEditor.this.layout();
      }
    };
    this.timer = new Runnable()
    {
      public void run()
      {
        TreeEditor.this.layout();
      }
    };
    this.treeListener = new TreeListener()
    {
      final Runnable runnable = new TreeEditor.4(this);

      public void treeCollapsed(TreeEvent paramAnonymousTreeEvent)
      {
        if ((TreeEditor.this.editor == null) || (TreeEditor.this.editor.isDisposed()))
          return;
        TreeEditor.this.editor.setVisible(false);
        paramAnonymousTreeEvent.display.asyncExec(this.runnable);
      }

      public void treeExpanded(TreeEvent paramAnonymousTreeEvent)
      {
        if ((TreeEditor.this.editor == null) || (TreeEditor.this.editor.isDisposed()))
          return;
        TreeEditor.this.editor.setVisible(false);
        paramAnonymousTreeEvent.display.asyncExec(this.runnable);
      }
    };
    paramTree.addTreeListener(this.treeListener);
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
    Rectangle localRectangle3 = this.tree.getClientArea();
    if ((localRectangle1.x < localRectangle3.x + localRectangle3.width) && (localRectangle1.x + localRectangle1.width > localRectangle3.x + localRectangle3.width))
      localRectangle1.width = (localRectangle3.x + localRectangle3.width - localRectangle1.x);
    Rectangle localRectangle4 = new Rectangle(localRectangle1.x, localRectangle1.y, this.minimumWidth, this.minimumHeight);
    if (this.grabHorizontal)
    {
      if (this.tree.getColumnCount() == 0)
        localRectangle1.width = (localRectangle3.x + localRectangle3.width - localRectangle1.x);
      localRectangle4.width = Math.max(localRectangle1.width, this.minimumWidth);
    }
    if (this.grabVertical)
      localRectangle4.height = Math.max(localRectangle1.height, this.minimumHeight);
    if (this.horizontalAlignment == 131072)
      localRectangle4.x += localRectangle1.width - localRectangle4.width;
    else if (this.horizontalAlignment != 16384)
      localRectangle4.x += (localRectangle1.width - localRectangle4.width) / 2;
    localRectangle4.x = Math.max(localRectangle1.x, localRectangle4.x);
    if (this.verticalAlignment == 1024)
      localRectangle4.y += localRectangle1.height - localRectangle4.height;
    else if (this.verticalAlignment != 128)
      localRectangle4.y += (localRectangle1.height - localRectangle4.height) / 2;
    return localRectangle4;
  }

  public void dispose()
  {
    if ((this.tree != null) && (!this.tree.isDisposed()))
    {
      if ((this.column > -1) && (this.column < this.tree.getColumnCount()))
      {
        TreeColumn localTreeColumn = this.tree.getColumn(this.column);
        localTreeColumn.removeControlListener(this.columnListener);
      }
      if (this.treeListener != null)
        this.tree.removeTreeListener(this.treeListener);
    }
    this.columnListener = null;
    this.treeListener = null;
    this.tree = null;
    this.item = null;
    this.column = 0;
    this.timer = null;
    super.dispose();
  }

  public int getColumn()
  {
    return this.column;
  }

  public TreeItem getItem()
  {
    return this.item;
  }

  void resize()
  {
    layout();
    if (this.tree != null)
    {
      Display localDisplay = this.tree.getDisplay();
      localDisplay.timerExec(-1, this.timer);
      localDisplay.timerExec(1500, this.timer);
    }
  }

  public void setColumn(int paramInt)
  {
    int i = this.tree.getColumnCount();
    if (i == 0)
    {
      this.column = (paramInt == 0 ? 0 : -1);
      resize();
      return;
    }
    if ((this.column > -1) && (this.column < i))
    {
      localTreeColumn = this.tree.getColumn(this.column);
      localTreeColumn.removeControlListener(this.columnListener);
      this.column = -1;
    }
    if ((paramInt < 0) || (paramInt >= this.tree.getColumnCount()))
      return;
    this.column = paramInt;
    TreeColumn localTreeColumn = this.tree.getColumn(this.column);
    localTreeColumn.addControlListener(this.columnListener);
    resize();
  }

  public void setItem(TreeItem paramTreeItem)
  {
    this.item = paramTreeItem;
    resize();
  }

  public void setEditor(Control paramControl, TreeItem paramTreeItem, int paramInt)
  {
    setItem(paramTreeItem);
    setColumn(paramInt);
    setEditor(paramControl);
  }

  public void setEditor(Control paramControl)
  {
    super.setEditor(paramControl);
    resize();
  }

  public void setEditor(Control paramControl, TreeItem paramTreeItem)
  {
    setItem(paramTreeItem);
    setEditor(paramControl);
  }

  public void layout()
  {
    if ((this.tree == null) || (this.tree.isDisposed()))
      return;
    if ((this.item == null) || (this.item.isDisposed()))
      return;
    int i = this.tree.getColumnCount();
    if ((i == 0) && (this.column != 0))
      return;
    if ((i > 0) && ((this.column < 0) || (this.column >= i)))
      return;
    super.layout();
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.custom.TreeEditor
 * JD-Core Version:    0.6.2
 */