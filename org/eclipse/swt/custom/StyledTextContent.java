package org.eclipse.swt.custom;

public abstract interface StyledTextContent
{
  public abstract void addTextChangeListener(TextChangeListener paramTextChangeListener);

  public abstract int getCharCount();

  public abstract String getLine(int paramInt);

  public abstract int getLineAtOffset(int paramInt);

  public abstract int getLineCount();

  public abstract String getLineDelimiter();

  public abstract int getOffsetAtLine(int paramInt);

  public abstract String getTextRange(int paramInt1, int paramInt2);

  public abstract void removeTextChangeListener(TextChangeListener paramTextChangeListener);

  public abstract void replaceTextRange(int paramInt1, int paramInt2, String paramString);

  public abstract void setText(String paramString);
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.custom.StyledTextContent
 * JD-Core Version:    0.6.2
 */