package net.htmlparser.jericho;

final class StartTagTypeMasonComponentCall extends StartTagTypeGenericImplementation
{
  protected static final StartTagTypeMasonComponentCall INSTANCE = new StartTagTypeMasonComponentCall();

  private StartTagTypeMasonComponentCall()
  {
    super("mason component call", "<&", "&>", null, true);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.StartTagTypeMasonComponentCall
 * JD-Core Version:    0.6.2
 */