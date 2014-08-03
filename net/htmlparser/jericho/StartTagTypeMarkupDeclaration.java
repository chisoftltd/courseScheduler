package net.htmlparser.jericho;

class StartTagTypeMarkupDeclaration extends StartTagTypeGenericImplementation
{
  static final StartTagTypeMarkupDeclaration INSTANCE = new StartTagTypeMarkupDeclaration();
  static final String ELEMENT = "!element";
  static final String ATTLIST = "!attlist";
  static final String ENTITY = "!entity";
  static final String NOTATION = "!notation";

  private StartTagTypeMarkupDeclaration()
  {
    super("markup declaration", "<!", ">", null, false, false, true);
  }

  protected Tag constructTagAt(Source paramSource, int paramInt)
  {
    Tag localTag = super.constructTagAt(paramSource, paramInt);
    if (localTag == null)
      return null;
    String str = localTag.getName();
    if ((str != "!element") && (str != "!attlist") && (str != "!entity") && (str != "!notation"))
      return null;
    return localTag;
  }

  protected int getEnd(Source paramSource, int paramInt)
  {
    ParseText localParseText = paramSource.getParseText();
    int i = 0;
    do
    {
      int j = localParseText.charAt(paramInt);
      if (j == 34)
        i = i == 0 ? 1 : 0;
      else if ((j == 62) && (i == 0))
        return paramInt + 1;
      paramInt++;
    }
    while (paramInt < paramSource.getEnd());
    return -1;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.StartTagTypeMarkupDeclaration
 * JD-Core Version:    0.6.2
 */