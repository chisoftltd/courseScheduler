package net.htmlparser.jericho;

import java.util.HashMap;
import java.util.HashSet;

public enum FormControlType
{
  BUTTON("button", true, true), CHECKBOX("input", true, false), FILE("input", false, false), HIDDEN("input", false, false), IMAGE("input", true, true), PASSWORD("input", false, false), RADIO("input", true, false), SELECT_MULTIPLE("select", true, false), SELECT_SINGLE("select", true, false), SUBMIT("input", true, true), TEXT("input", false, false), TEXTAREA("textarea", false, false);

  private String elementName;
  private boolean hasPredefinedValue;
  private boolean submit;
  private static final HashMap<String, FormControlType> INPUT_ELEMENT_TYPE_MAP;
  private static final HashSet<String> NON_FORM_CONTROL_TYPE_ATTRIBUTE_SET;

  private FormControlType(String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.elementName = paramString;
    this.hasPredefinedValue = paramBoolean1;
    this.submit = paramBoolean2;
  }

  public String getElementName()
  {
    return this.elementName;
  }

  public boolean hasPredefinedValue()
  {
    return this.hasPredefinedValue;
  }

  public boolean isSubmit()
  {
    return this.submit;
  }

  static FormControlType getFromInputElementType(String paramString)
  {
    return (FormControlType)INPUT_ELEMENT_TYPE_MAP.get(paramString.toLowerCase());
  }

  static boolean isNonFormControl(String paramString)
  {
    return NON_FORM_CONTROL_TYPE_ATTRIBUTE_SET.contains(paramString.toLowerCase());
  }

  static
  {
    INPUT_ELEMENT_TYPE_MAP = new HashMap(11, 1.0F);
    NON_FORM_CONTROL_TYPE_ATTRIBUTE_SET = new HashSet(3, 1.0F);
    INPUT_ELEMENT_TYPE_MAP.put("checkbox", CHECKBOX);
    INPUT_ELEMENT_TYPE_MAP.put("file", FILE);
    INPUT_ELEMENT_TYPE_MAP.put("hidden", HIDDEN);
    INPUT_ELEMENT_TYPE_MAP.put("image", IMAGE);
    INPUT_ELEMENT_TYPE_MAP.put("password", PASSWORD);
    INPUT_ELEMENT_TYPE_MAP.put("radio", RADIO);
    INPUT_ELEMENT_TYPE_MAP.put("submit", SUBMIT);
    INPUT_ELEMENT_TYPE_MAP.put("text", TEXT);
    NON_FORM_CONTROL_TYPE_ATTRIBUTE_SET.add("button");
    NON_FORM_CONTROL_TYPE_ATTRIBUTE_SET.add("reset");
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.FormControlType
 * JD-Core Version:    0.6.2
 */