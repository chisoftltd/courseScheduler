package net.htmlparser.jericho;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum FormControlOutputStyle
{
  NORMAL, REMOVE, DISPLAY_VALUE;

  public String getDebugInfo()
  {
    return toString();
  }

  public static final class ConfigDisplayValue
  {
    public static volatile String MultipleValueSeparator = ", ";
    public static volatile String ElementName = "div";
    public static volatile List<String> AttributeNames = new ArrayList(Arrays.asList(new String[] { "id", "class", "style" }));
    public static volatile String EmptyHTML = "&nbsp;";
    public static volatile char PasswordChar = '*';
    public static volatile String CheckedHTML = null;
    public static volatile String UncheckedHTML = null;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.FormControlOutputStyle
 * JD-Core Version:    0.6.2
 */