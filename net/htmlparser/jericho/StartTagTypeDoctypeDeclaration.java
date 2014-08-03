package net.htmlparser.jericho;

final class StartTagTypeDoctypeDeclaration extends StartTagTypeGenericImplementation
{
  static final StartTagTypeDoctypeDeclaration INSTANCE = new StartTagTypeDoctypeDeclaration();

  private StartTagTypeDoctypeDeclaration()
  {
    super("document type declaration", "<!doctype", ">", null, false, false, false);
  }

  protected int getEnd(Source paramSource, int paramInt)
  {
    ParseText localParseText = paramSource.getParseText();
    int i = 0;
    int j = 0;
    do
    {
      int k = localParseText.charAt(paramInt);
      if (i != 0)
      {
        if (k == 34)
          i = 0;
      }
      else
        switch (k)
        {
        case 62:
          if (j == 0)
            return paramInt + 1;
          break;
        case 34:
          i = 1;
          break;
        case 91:
          j = 1;
          break;
        case 93:
          j = 0;
        }
      paramInt++;
    }
    while (paramInt < paramSource.getEnd());
    return -1;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.StartTagTypeDoctypeDeclaration
 * JD-Core Version:    0.6.2
 */