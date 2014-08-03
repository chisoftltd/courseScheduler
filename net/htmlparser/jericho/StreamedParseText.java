package net.htmlparser.jericho;

final class StreamedParseText extends CharSequenceParseText
{
  private final StreamedText streamedText;

  public StreamedParseText(StreamedText paramStreamedText)
  {
    super(paramStreamedText);
    this.streamedText = paramStreamedText;
  }

  protected int getEnd()
  {
    return this.streamedText.getEnd();
  }

  protected String substring(int paramInt1, int paramInt2)
  {
    return this.streamedText.substring(paramInt1, paramInt2).toLowerCase();
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.StreamedParseText
 * JD-Core Version:    0.6.2
 */