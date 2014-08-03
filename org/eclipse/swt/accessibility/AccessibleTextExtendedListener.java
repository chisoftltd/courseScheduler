package org.eclipse.swt.accessibility;

public abstract interface AccessibleTextExtendedListener extends AccessibleTextListener
{
  public abstract void addSelection(AccessibleTextEvent paramAccessibleTextEvent);

  public abstract void getCharacterCount(AccessibleTextEvent paramAccessibleTextEvent);

  public abstract void getHyperlinkCount(AccessibleTextEvent paramAccessibleTextEvent);

  public abstract void getHyperlink(AccessibleTextEvent paramAccessibleTextEvent);

  public abstract void getHyperlinkIndex(AccessibleTextEvent paramAccessibleTextEvent);

  public abstract void getOffsetAtPoint(AccessibleTextEvent paramAccessibleTextEvent);

  public abstract void getRanges(AccessibleTextEvent paramAccessibleTextEvent);

  public abstract void getSelection(AccessibleTextEvent paramAccessibleTextEvent);

  public abstract void getSelectionCount(AccessibleTextEvent paramAccessibleTextEvent);

  public abstract void getText(AccessibleTextEvent paramAccessibleTextEvent);

  public abstract void getTextBounds(AccessibleTextEvent paramAccessibleTextEvent);

  public abstract void getVisibleRanges(AccessibleTextEvent paramAccessibleTextEvent);

  public abstract void removeSelection(AccessibleTextEvent paramAccessibleTextEvent);

  public abstract void scrollText(AccessibleTextEvent paramAccessibleTextEvent);

  public abstract void setCaretOffset(AccessibleTextEvent paramAccessibleTextEvent);

  public abstract void setSelection(AccessibleTextEvent paramAccessibleTextEvent);
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.accessibility.AccessibleTextExtendedListener
 * JD-Core Version:    0.6.2
 */