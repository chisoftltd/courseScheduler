package org.eclipse.swt.custom;

class CCombo$2
  implements Runnable
{
  final CCombo.1 this$1;

  CCombo$2(CCombo.1 param1)
  {
    this.this$1 = param1;
  }

  public void run()
  {
    if (CCombo.1.access$0(this.this$1).isDisposed())
      return;
    CCombo.1.access$0(this.this$1).handleFocus(16);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.custom.CCombo.2
 * JD-Core Version:    0.6.2
 */