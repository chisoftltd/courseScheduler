package net.htmlparser.jericho;

final class StartTagTypeXMLProcessingInstruction extends StartTagTypeGenericImplementation
{
  static final StartTagTypeXMLProcessingInstruction INSTANCE = new StartTagTypeXMLProcessingInstruction();

  private StartTagTypeXMLProcessingInstruction()
  {
    super("XML processing instruction", "<?", "?>", null, false, false, true);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.StartTagTypeXMLProcessingInstruction
 * JD-Core Version:    0.6.2
 */