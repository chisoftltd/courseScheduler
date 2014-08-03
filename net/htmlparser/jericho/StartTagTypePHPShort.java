package net.htmlparser.jericho;

final class StartTagTypePHPShort extends StartTagTypeGenericImplementation
{
  protected static final StartTagTypePHPShort INSTANCE = new StartTagTypePHPShort();

  private StartTagTypePHPShort()
  {
    super("PHP short tag", "<?", "?>", null, true);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.StartTagTypePHPShort
 * JD-Core Version:    0.6.2
 */