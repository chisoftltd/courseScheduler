package net.htmlparser.jericho;

final class StartTagTypeServerCommon extends StartTagTypeGenericImplementation
{
  static final StartTagTypeServerCommon INSTANCE = new StartTagTypeServerCommon();

  private StartTagTypeServerCommon()
  {
    super("common server tag", "<%", "%>", null, true);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.StartTagTypeServerCommon
 * JD-Core Version:    0.6.2
 */