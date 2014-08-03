package net.htmlparser.jericho;

final class StartTagTypePHPStandard extends StartTagTypeGenericImplementation
{
  protected static final StartTagTypePHPStandard INSTANCE = new StartTagTypePHPStandard();

  private StartTagTypePHPStandard()
  {
    super("PHP standard tag", "<?php", "?>", null, true);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.StartTagTypePHPStandard
 * JD-Core Version:    0.6.2
 */