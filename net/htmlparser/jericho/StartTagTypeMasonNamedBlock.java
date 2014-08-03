package net.htmlparser.jericho;

final class StartTagTypeMasonNamedBlock extends StartTagTypeGenericImplementation
{
  protected static final StartTagTypeMasonNamedBlock INSTANCE = new StartTagTypeMasonNamedBlock();

  private StartTagTypeMasonNamedBlock()
  {
    super("mason named block", "<%", ">", EndTagTypeMasonNamedBlock.INSTANCE, true, false, true);
  }

  protected Tag constructTagAt(Source paramSource, int paramInt)
  {
    Tag localTag = super.constructTagAt(paramSource, paramInt);
    if (localTag == null)
      return null;
    if (paramSource.charAt(localTag.getEnd() - 2) == '%')
      return null;
    if (paramSource.getNextEndTag(localTag.getEnd(), localTag.getName(), getCorrespondingEndTagType()) == null)
      return null;
    return localTag;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.StartTagTypeMasonNamedBlock
 * JD-Core Version:    0.6.2
 */