package net.htmlparser.jericho;

final class StartTagTypeComment extends StartTagTypeGenericImplementation
{
  static final StartTagTypeComment INSTANCE = new StartTagTypeComment();

  private StartTagTypeComment()
  {
    super("comment", "<!--", "-->", null, false);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.StartTagTypeComment
 * JD-Core Version:    0.6.2
 */