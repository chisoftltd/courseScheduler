package org.eclipse.swt.custom;

import org.eclipse.swt.widgets.Control;

class TableTreeEditor$2
  implements Runnable
{
  final TableTreeEditor.1 this$1;

  TableTreeEditor$2(TableTreeEditor.1 param1)
  {
    this.this$1 = param1;
  }

  public void run()
  {
    if ((TableTreeEditor.1.access$0(this.this$1).editor == null) || (TableTreeEditor.1.access$0(this.this$1).editor.isDisposed()))
      return;
    if (TableTreeEditor.1.access$0(this.this$1).tableTree.isDisposed())
      return;
    TableTreeEditor.1.access$0(this.this$1).layout();
    TableTreeEditor.1.access$0(this.this$1).editor.setVisible(true);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.custom.TableTreeEditor.2
 * JD-Core Version:    0.6.2
 */