package net.htmlparser.jericho;

final class EndTagTypeMasonComponentCalledWithContent extends EndTagTypeGenericImplementation
{
  protected static final EndTagTypeMasonComponentCalledWithContent INSTANCE = new EndTagTypeMasonComponentCalledWithContent();

  private EndTagTypeMasonComponentCalledWithContent()
  {
    super("/mason component called with content", "</&", ">", true, true);
  }

  public StartTagType getCorrespondingStartTagType()
  {
    return MasonTagTypes.MASON_COMPONENT_CALLED_WITH_CONTENT;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.EndTagTypeMasonComponentCalledWithContent
 * JD-Core Version:    0.6.2
 */