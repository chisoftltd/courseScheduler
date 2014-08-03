package net.htmlparser.jericho;

final class StartTagTypeXMLDeclaration extends StartTagTypeGenericImplementation
{
  static final StartTagTypeXMLDeclaration INSTANCE = new StartTagTypeXMLDeclaration();

  private StartTagTypeXMLDeclaration()
  {
    super("XML declaration", "<?xml", "?>", null, false, true, false);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.StartTagTypeXMLDeclaration
 * JD-Core Version:    0.6.2
 */