package net.htmlparser.jericho;

final class StartTagTypeMasonComponentCalledWithContent extends StartTagTypeGenericImplementation
{
  protected static final StartTagTypeMasonComponentCalledWithContent INSTANCE = new StartTagTypeMasonComponentCalledWithContent();

  private StartTagTypeMasonComponentCalledWithContent()
  {
    super("mason component called with content", "<&|", "&>", EndTagTypeMasonComponentCalledWithContent.INSTANCE, true);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.StartTagTypeMasonComponentCalledWithContent
 * JD-Core Version:    0.6.2
 */