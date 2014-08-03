package net.htmlparser.jericho;

public final class Config
{
  public static String ColumnMultipleValueSeparator = ",";
  public static String ColumnValueTrue = Boolean.toString(true);
  public static String ColumnValueFalse = null;
  public static boolean ConvertNonBreakingSpaces = true;
  public static CompatibilityMode CurrentCompatibilityMode = CompatibilityMode.IE;
  public static boolean IsApostropheEncoded = false;
  public static LoggerProvider LoggerProvider = null;
  public static String NewLine = System.getProperty("line.separator");
  static final boolean IncludeServerTagsInElementHierarchy = false;

  public static final class CompatibilityMode
  {
    private String name;
    private volatile boolean formFieldNameCaseInsensitive;
    volatile Config.UnterminatedCharacterReferenceSettings unterminatedCharacterReferenceSettingsInsideAttributeValue;
    volatile Config.UnterminatedCharacterReferenceSettings unterminatedCharacterReferenceSettingsOutsideAttributeValue;
    public static final int CODE_POINTS_ALL = 1114111;
    public static final int CODE_POINTS_NONE = -1;
    public static final CompatibilityMode IE = new CompatibilityMode("IE", true, new Config.UnterminatedCharacterReferenceSettings(255, 1114111, 1114111), new Config.UnterminatedCharacterReferenceSettings(255, 1114111, -1));
    public static final CompatibilityMode MOZILLA = new CompatibilityMode("Mozilla", false, new Config.UnterminatedCharacterReferenceSettings(255, 1114111, 1114111), new Config.UnterminatedCharacterReferenceSettings(1114111, 1114111, 1114111));
    public static final CompatibilityMode OPERA = new CompatibilityMode("Opera", true, new Config.UnterminatedCharacterReferenceSettings(62, 1114111, 1114111), new Config.UnterminatedCharacterReferenceSettings(1114111, 1114111, 1114111));
    public static final CompatibilityMode XHTML = new CompatibilityMode("XHTML");

    public CompatibilityMode(String paramString)
    {
      this(paramString, false, new Config.UnterminatedCharacterReferenceSettings(), new Config.UnterminatedCharacterReferenceSettings());
    }

    private CompatibilityMode(String paramString, boolean paramBoolean, Config.UnterminatedCharacterReferenceSettings paramUnterminatedCharacterReferenceSettings1, Config.UnterminatedCharacterReferenceSettings paramUnterminatedCharacterReferenceSettings2)
    {
      this.name = paramString;
      this.formFieldNameCaseInsensitive = paramBoolean;
      this.unterminatedCharacterReferenceSettingsInsideAttributeValue = paramUnterminatedCharacterReferenceSettings1;
      this.unterminatedCharacterReferenceSettingsOutsideAttributeValue = paramUnterminatedCharacterReferenceSettings2;
    }

    public String getName()
    {
      return this.name;
    }

    public boolean isFormFieldNameCaseInsensitive()
    {
      return this.formFieldNameCaseInsensitive;
    }

    public void setFormFieldNameCaseInsensitive(boolean paramBoolean)
    {
      this.formFieldNameCaseInsensitive = paramBoolean;
    }

    public int getUnterminatedCharacterEntityReferenceMaxCodePoint(boolean paramBoolean)
    {
      return getUnterminatedCharacterReferenceSettings(paramBoolean).characterEntityReferenceMaxCodePoint;
    }

    public void setUnterminatedCharacterEntityReferenceMaxCodePoint(boolean paramBoolean, int paramInt)
    {
      getUnterminatedCharacterReferenceSettings(paramBoolean).characterEntityReferenceMaxCodePoint = paramInt;
    }

    public int getUnterminatedDecimalCharacterReferenceMaxCodePoint(boolean paramBoolean)
    {
      return getUnterminatedCharacterReferenceSettings(paramBoolean).decimalCharacterReferenceMaxCodePoint;
    }

    public void setUnterminatedDecimalCharacterReferenceMaxCodePoint(boolean paramBoolean, int paramInt)
    {
      getUnterminatedCharacterReferenceSettings(paramBoolean).decimalCharacterReferenceMaxCodePoint = paramInt;
    }

    public int getUnterminatedHexadecimalCharacterReferenceMaxCodePoint(boolean paramBoolean)
    {
      return getUnterminatedCharacterReferenceSettings(paramBoolean).hexadecimalCharacterReferenceMaxCodePoint;
    }

    public void setUnterminatedHexadecimalCharacterReferenceMaxCodePoint(boolean paramBoolean, int paramInt)
    {
      getUnterminatedCharacterReferenceSettings(paramBoolean).hexadecimalCharacterReferenceMaxCodePoint = paramInt;
    }

    public String getDebugInfo()
    {
      return "Form field name case insensitive: " + this.formFieldNameCaseInsensitive + Config.NewLine + "Maximum codepoints in unterminated character references:" + Config.NewLine + "  Inside attribute values:" + this.unterminatedCharacterReferenceSettingsInsideAttributeValue + Config.NewLine + "  Outside attribute values:" + this.unterminatedCharacterReferenceSettingsOutsideAttributeValue;
    }

    public String toString()
    {
      return getName();
    }

    Config.UnterminatedCharacterReferenceSettings getUnterminatedCharacterReferenceSettings(boolean paramBoolean)
    {
      return paramBoolean ? this.unterminatedCharacterReferenceSettingsInsideAttributeValue : this.unterminatedCharacterReferenceSettingsOutsideAttributeValue;
    }
  }

  static class UnterminatedCharacterReferenceSettings
  {
    public volatile int characterEntityReferenceMaxCodePoint;
    public volatile int decimalCharacterReferenceMaxCodePoint;
    public volatile int hexadecimalCharacterReferenceMaxCodePoint;
    public static UnterminatedCharacterReferenceSettings ACCEPT_ALL = new UnterminatedCharacterReferenceSettings(1114111, 1114111, 1114111);

    public UnterminatedCharacterReferenceSettings()
    {
      this(-1, -1, -1);
    }

    public UnterminatedCharacterReferenceSettings(int paramInt1, int paramInt2, int paramInt3)
    {
      this.characterEntityReferenceMaxCodePoint = paramInt1;
      this.decimalCharacterReferenceMaxCodePoint = paramInt2;
      this.hexadecimalCharacterReferenceMaxCodePoint = paramInt3;
    }

    public String toString()
    {
      return Config.NewLine + "    Character entity reference: " + getDescription(this.characterEntityReferenceMaxCodePoint) + Config.NewLine + "    Decimal character reference: " + getDescription(this.decimalCharacterReferenceMaxCodePoint) + Config.NewLine + "    Haxadecimal character reference: " + getDescription(this.hexadecimalCharacterReferenceMaxCodePoint);
    }

    private String getDescription(int paramInt)
    {
      if (paramInt == -1)
        return "None";
      if (paramInt == 1114111)
        return "All";
      return "0x" + Integer.toString(paramInt, 16);
    }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.Config
 * JD-Core Version:    0.6.2
 */