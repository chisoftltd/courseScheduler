package net.htmlparser.jericho;

final class EndTagTypeMasonNamedBlock extends EndTagTypeGenericImplementation
{
  protected static final EndTagTypeMasonNamedBlock INSTANCE = new EndTagTypeMasonNamedBlock();

  private EndTagTypeMasonNamedBlock()
  {
    super("/mason named block", "</%", ">", true, false);
  }

  public StartTagType getCorrespondingStartTagType()
  {
    return MasonTagTypes.MASON_NAMED_BLOCK;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.EndTagTypeMasonNamedBlock
 * JD-Core Version:    0.6.2
 */