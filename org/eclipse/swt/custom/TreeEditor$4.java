package org.eclipse.swt.custom;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Tree;

class TreeEditor$4
  implements Runnable
{
  final TreeEditor.3 this$1;

  TreeEditor$4(TreeEditor.3 param3)
  {
    this.this$1 = param3;
  }

  public void run()
  {
    if ((TreeEditor.3.access$0(this.this$1).editor == null) || (TreeEditor.3.access$0(this.this$1).editor.isDisposed()))
      return;
    if (TreeEditor.3.access$0(this.this$1).tree.isDisposed())
      return;
    TreeEditor.3.access$0(this.this$1).layout();
    TreeEditor.3.access$0(this.this$1).editor.setVisible(true);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.custom.TreeEditor.4
 * JD-Core Version:    0.6.2
 */