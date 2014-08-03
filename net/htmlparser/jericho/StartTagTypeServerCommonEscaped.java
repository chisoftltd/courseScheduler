package net.htmlparser.jericho;

final class StartTagTypeServerCommonEscaped extends StartTagTypeGenericImplementation
{
  static final StartTagTypeServerCommonEscaped INSTANCE = new StartTagTypeServerCommonEscaped();

  private StartTagTypeServerCommonEscaped()
  {
    super("escaped common server tag", "<\\%", "%>", null, true);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.StartTagTypeServerCommonEscaped
 * JD-Core Version:    0.6.2
 */