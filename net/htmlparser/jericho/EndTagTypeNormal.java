package net.htmlparser.jericho;

final class EndTagTypeNormal extends EndTagTypeGenericImplementation
{
  static final EndTagTypeNormal INSTANCE = new EndTagTypeNormal();

  private EndTagTypeNormal()
  {
    super("/normal", "</", ">", false, false);
  }

  public StartTagType getCorrespondingStartTagType()
  {
    return StartTagType.NORMAL;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.EndTagTypeNormal
 * JD-Core Version:    0.6.2
 */