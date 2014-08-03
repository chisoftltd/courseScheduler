package net.htmlparser.jericho;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public abstract class FormControl extends Segment
{
  FormControlType formControlType;
  String name;
  ElementContainer elementContainer;
  FormControlOutputStyle outputStyle = FormControlOutputStyle.NORMAL;
  private static final String CHECKBOX_NULL_DEFAULT_VALUE = "on";
  private static Comparator<FormControl> COMPARATOR = new PositionComparator(null);

  static FormControl construct(Element paramElement)
  {
    String str1 = paramElement.getStartTag().getName();
    if (str1 == "input")
    {
      String str2 = paramElement.getAttributes().getRawValue("type");
      if (str2 == null)
        return new InputFormControl(paramElement, FormControlType.TEXT);
      FormControlType localFormControlType = FormControlType.getFromInputElementType(str2);
      if (localFormControlType == null)
      {
        if (FormControlType.isNonFormControl(str2))
          return null;
        if (paramElement.source.logger.isInfoEnabled())
          paramElement.source.logger.info(": INPUT control with unrecognised type \"" + str2 + "\" assumed to be type \"text\"");
        localFormControlType = FormControlType.TEXT;
      }
      switch (1.$SwitchMap$net$htmlparser$jericho$FormControlType[localFormControlType.ordinal()])
      {
      case 1:
        return new InputFormControl(paramElement, localFormControlType);
      case 2:
      case 3:
        return new RadioCheckboxFormControl(paramElement, localFormControlType);
      case 4:
        return new SubmitFormControl(paramElement, localFormControlType);
      case 5:
        return new ImageSubmitFormControl(paramElement);
      case 6:
      case 7:
      case 8:
        return new InputFormControl(paramElement, localFormControlType);
      }
      throw new AssertionError(localFormControlType);
    }
    if (str1 == "select")
      return new SelectFormControl(paramElement);
    if (str1 == "textarea")
      return new TextAreaFormControl(paramElement);
    if (str1 == "button")
      return "submit".equalsIgnoreCase(paramElement.getAttributes().getRawValue("type")) ? new SubmitFormControl(paramElement, FormControlType.BUTTON) : null;
    return null;
  }

  private FormControl(Element paramElement, FormControlType paramFormControlType, boolean paramBoolean)
  {
    super(paramElement.source, paramElement.begin, paramElement.end);
    this.elementContainer = new ElementContainer(paramElement, paramBoolean);
    this.formControlType = paramFormControlType;
    this.name = paramElement.getAttributes().getValue("name");
    verifyName();
  }

  public final FormControlType getFormControlType()
  {
    return this.formControlType;
  }

  public final String getName()
  {
    return this.name;
  }

  public final Element getElement()
  {
    return this.elementContainer.element;
  }

  public Iterator<Element> getOptionElementIterator()
  {
    throw new UnsupportedOperationException("Only SELECT controls contain OPTION elements");
  }

  public FormControlOutputStyle getOutputStyle()
  {
    return this.outputStyle;
  }

  public void setOutputStyle(FormControlOutputStyle paramFormControlOutputStyle)
  {
    this.outputStyle = paramFormControlOutputStyle;
  }

  public final Map<String, String> getAttributesMap()
  {
    return this.elementContainer.getAttributesMap();
  }

  public final boolean isDisabled()
  {
    return this.elementContainer.getBooleanAttribute("disabled");
  }

  public final void setDisabled(boolean paramBoolean)
  {
    this.elementContainer.setBooleanAttribute("disabled", paramBoolean);
  }

  public boolean isChecked()
  {
    throw new UnsupportedOperationException("This property is only relevant for CHECKBOX and RADIO controls");
  }

  public String getPredefinedValue()
  {
    return this.elementContainer.predefinedValue;
  }

  public Collection<String> getPredefinedValues()
  {
    if (getPredefinedValue() == null)
      Collections.emptySet();
    return Collections.singleton(getPredefinedValue());
  }

  public List<String> getValues()
  {
    ArrayList localArrayList = new ArrayList();
    addValuesTo(localArrayList);
    return localArrayList;
  }

  public final void clearValues()
  {
    setValue(null);
  }

  public abstract boolean setValue(String paramString);

  public boolean addValue(String paramString)
  {
    return setValue(paramString);
  }

  abstract void addValuesTo(Collection<String> paramCollection);

  abstract void addToFormFields(FormFields paramFormFields);

  abstract void replaceInOutputDocument(OutputDocument paramOutputDocument);

  public String getDebugInfo()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.formControlType).append(" name=\"").append(this.name).append('"');
    if (this.elementContainer.predefinedValue != null)
      localStringBuilder.append(" PredefinedValue=\"").append(this.elementContainer.predefinedValue).append('"');
    localStringBuilder.append(" - ").append(getElement().getDebugInfo());
    return localStringBuilder.toString();
  }

  final String getDisplayValueHTML(CharSequence paramCharSequence, boolean paramBoolean)
  {
    StringBuilder localStringBuilder = new StringBuilder((paramCharSequence == null ? 0 : paramCharSequence.length() * 2) + 50);
    localStringBuilder.append('<').append(FormControlOutputStyle.ConfigDisplayValue.ElementName);
    try
    {
      Iterator localIterator = FormControlOutputStyle.ConfigDisplayValue.AttributeNames.iterator();
      while (localIterator.hasNext())
      {
        String str1 = (String)localIterator.next();
        String str2 = this.elementContainer.getAttributeValue(str1);
        if (str2 != null)
          Attribute.appendHTML(localStringBuilder, str1, str2);
      }
      localStringBuilder.append('>');
      if ((paramCharSequence == null) || (paramCharSequence.length() == 0))
        localStringBuilder.append(FormControlOutputStyle.ConfigDisplayValue.EmptyHTML);
      else
        CharacterReference.appendEncode(localStringBuilder, paramCharSequence, paramBoolean);
    }
    catch (IOException localIOException)
    {
      throw new RuntimeException(localIOException);
    }
    localStringBuilder.append("</").append(FormControlOutputStyle.ConfigDisplayValue.ElementName).append('>');
    return localStringBuilder.toString();
  }

  final void replaceAttributesInOutputDocumentIfModified(OutputDocument paramOutputDocument)
  {
    this.elementContainer.replaceAttributesInOutputDocumentIfModified(paramOutputDocument);
  }

  static List<FormControl> getAll(Segment paramSegment)
  {
    ArrayList localArrayList = new ArrayList();
    getAll(paramSegment, localArrayList, "input");
    getAll(paramSegment, localArrayList, "textarea");
    getAll(paramSegment, localArrayList, "select");
    getAll(paramSegment, localArrayList, "button");
    Collections.sort(localArrayList, COMPARATOR);
    return localArrayList;
  }

  private static void getAll(Segment paramSegment, ArrayList<FormControl> paramArrayList, String paramString)
  {
    Iterator localIterator = paramSegment.getAllElements(paramString).iterator();
    while (localIterator.hasNext())
    {
      Element localElement = (Element)localIterator.next();
      FormControl localFormControl = localElement.getFormControl();
      if (localFormControl != null)
        paramArrayList.add(localFormControl);
    }
  }

  private static String getString(char paramChar, int paramInt)
  {
    if (paramInt == 0)
      return "";
    StringBuilder localStringBuilder = new StringBuilder(paramInt);
    for (int i = 0; i < paramInt; i++)
      localStringBuilder.append(paramChar);
    return localStringBuilder.toString();
  }

  private void verifyName()
  {
    if (this.formControlType.isSubmit())
      return;
    String str;
    if (this.name == null)
    {
      str = "missing";
    }
    else
    {
      if (this.name.length() != 0)
        return;
      str = "blank";
    }
    Source localSource = getElement().source;
    if (localSource.logger.isInfoEnabled())
      localSource.logger.info(": compulsory \"name\" attribute of " + this.formControlType + " control is " + str);
  }

  private static final void addValueTo(Collection<String> paramCollection, String paramString)
  {
    paramCollection.add(paramString != null ? paramString : "");
  }

  static final class ElementContainer
  {
    public final Element element;
    public Map<String, String> attributesMap = null;
    public String predefinedValue;

    public ElementContainer(Element paramElement, boolean paramBoolean)
    {
      this.element = paramElement;
      this.predefinedValue = (paramBoolean ? paramElement.getAttributes().getValue("value") : null);
    }

    public Map<String, String> getAttributesMap()
    {
      if (this.attributesMap == null)
        this.attributesMap = this.element.getAttributes().getMap(true);
      return this.attributesMap;
    }

    public boolean setSelected(String paramString1, String paramString2, boolean paramBoolean)
    {
      if ((paramString1 != null) && (this.predefinedValue.equals(paramString1.toString())))
      {
        setBooleanAttribute(paramString2, true);
        return true;
      }
      if (!paramBoolean)
        setBooleanAttribute(paramString2, false);
      return false;
    }

    public String getAttributeValue(String paramString)
    {
      if (this.attributesMap != null)
        return (String)this.attributesMap.get(paramString);
      return this.element.getAttributes().getValue(paramString);
    }

    public void setAttributeValue(String paramString1, String paramString2)
    {
      if (paramString2 == null)
      {
        setBooleanAttribute(paramString1, false);
        return;
      }
      if (this.attributesMap != null)
      {
        this.attributesMap.put(paramString1, paramString2);
        return;
      }
      String str = getAttributeValue(paramString1);
      if ((str != null) && (str.equals(paramString2)))
        return;
      getAttributesMap().put(paramString1, paramString2);
    }

    public boolean getBooleanAttribute(String paramString)
    {
      if (this.attributesMap != null)
        return this.attributesMap.containsKey(paramString);
      return this.element.getAttributes().get(paramString) != null;
    }

    public void setBooleanAttribute(String paramString, boolean paramBoolean)
    {
      boolean bool = getBooleanAttribute(paramString);
      if (paramBoolean == bool)
        return;
      if (paramBoolean)
        getAttributesMap().put(paramString, paramString);
      else
        getAttributesMap().remove(paramString);
    }

    public void replaceAttributesInOutputDocumentIfModified(OutputDocument paramOutputDocument)
    {
      if (this.attributesMap != null)
        paramOutputDocument.replace(this.element.getAttributes(), this.attributesMap);
    }
  }

  private static final class PositionComparator
    implements Comparator<FormControl>
  {
    public int compare(FormControl paramFormControl1, FormControl paramFormControl2)
    {
      int i = paramFormControl1.getElement().getBegin();
      int j = paramFormControl2.getElement().getBegin();
      if (i < j)
        return -1;
      if (i > j)
        return 1;
      return 0;
    }
  }

  static final class SelectFormControl extends FormControl
  {
    public FormControl.ElementContainer[] optionElementContainers;

    public SelectFormControl(Element paramElement)
    {
      super(paramElement.getAttributes().get("multiple") != null ? FormControlType.SELECT_MULTIPLE : FormControlType.SELECT_SINGLE, false, null);
      List localList = paramElement.getAllElements("option");
      this.optionElementContainers = new FormControl.ElementContainer[localList.size()];
      int i = 0;
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext())
      {
        Element localElement = (Element)localIterator.next();
        FormControl.ElementContainer localElementContainer = new FormControl.ElementContainer(localElement, true);
        if (localElementContainer.predefinedValue == null)
          localElementContainer.predefinedValue = CharacterReference.decodeCollapseWhiteSpace(localElementContainer.element.getContent());
        this.optionElementContainers[(i++)] = localElementContainer;
      }
    }

    public String getPredefinedValue()
    {
      throw new UnsupportedOperationException("Use getPredefinedValues() method instead on SELECT controls");
    }

    public Collection<String> getPredefinedValues()
    {
      LinkedHashSet localLinkedHashSet = new LinkedHashSet(this.optionElementContainers.length * 2, 1.0F);
      for (int i = 0; i < this.optionElementContainers.length; i++)
        localLinkedHashSet.add(this.optionElementContainers[i].predefinedValue);
      return localLinkedHashSet;
    }

    public Iterator<Element> getOptionElementIterator()
    {
      return new OptionElementIterator(null);
    }

    public boolean setValue(String paramString)
    {
      return addValue(paramString, false);
    }

    public boolean addValue(String paramString)
    {
      return addValue(paramString, this.formControlType == FormControlType.SELECT_MULTIPLE);
    }

    private boolean addValue(String paramString, boolean paramBoolean)
    {
      boolean bool = false;
      for (int i = 0; i < this.optionElementContainers.length; i++)
        if (this.optionElementContainers[i].setSelected(paramString, "selected", paramBoolean))
          bool = true;
      return bool;
    }

    void addValuesTo(Collection<String> paramCollection)
    {
      for (int i = 0; i < this.optionElementContainers.length; i++)
        if (this.optionElementContainers[i].getBooleanAttribute("selected"))
          FormControl.addValueTo(paramCollection, this.optionElementContainers[i].predefinedValue);
    }

    void addToFormFields(FormFields paramFormFields)
    {
      for (int i = 0; i < this.optionElementContainers.length; i++)
        paramFormFields.add(this, this.optionElementContainers[i].predefinedValue);
    }

    void replaceInOutputDocument(OutputDocument paramOutputDocument)
    {
      if (this.outputStyle == FormControlOutputStyle.REMOVE)
      {
        paramOutputDocument.remove(getElement());
      }
      else if (this.outputStyle == FormControlOutputStyle.DISPLAY_VALUE)
      {
        StringBuilder localStringBuilder = new StringBuilder(100);
        for (int j = 0; j < this.optionElementContainers.length; j++)
          if (this.optionElementContainers[j].getBooleanAttribute("selected"))
          {
            localStringBuilder.append(getOptionLabel(this.optionElementContainers[j].element));
            localStringBuilder.append(FormControlOutputStyle.ConfigDisplayValue.MultipleValueSeparator);
          }
        if (localStringBuilder.length() > 0)
          localStringBuilder.setLength(localStringBuilder.length() - FormControlOutputStyle.ConfigDisplayValue.MultipleValueSeparator.length());
        paramOutputDocument.replace(getElement(), getDisplayValueHTML(localStringBuilder, false));
      }
      else
      {
        replaceAttributesInOutputDocumentIfModified(paramOutputDocument);
        for (int i = 0; i < this.optionElementContainers.length; i++)
          this.optionElementContainers[i].replaceAttributesInOutputDocumentIfModified(paramOutputDocument);
      }
    }

    private static String getOptionLabel(Element paramElement)
    {
      String str = paramElement.getAttributeValue("label");
      if (str != null)
        return str;
      return CharacterReference.decodeCollapseWhiteSpace(paramElement.getContent());
    }

    private final class OptionElementIterator
      implements Iterator<Element>
    {
      private int i = 0;

      private OptionElementIterator()
      {
      }

      public boolean hasNext()
      {
        return this.i < FormControl.SelectFormControl.this.optionElementContainers.length;
      }

      public Element next()
      {
        if (!hasNext())
          throw new NoSuchElementException();
        return FormControl.SelectFormControl.this.optionElementContainers[(this.i++)].element;
      }

      public void remove()
      {
        throw new UnsupportedOperationException();
      }
    }
  }

  static final class ImageSubmitFormControl extends FormControl.SubmitFormControl
  {
    public ImageSubmitFormControl(Element paramElement)
    {
      super(FormControlType.IMAGE);
    }

    void addToFormFields(FormFields paramFormFields)
    {
      super.addToFormFields(paramFormFields);
      paramFormFields.addName(this, this.name + ".x");
      paramFormFields.addName(this, this.name + ".y");
    }
  }

  static class SubmitFormControl extends FormControl
  {
    public SubmitFormControl(Element paramElement, FormControlType paramFormControlType)
    {
      super(paramFormControlType, true, null);
    }

    public boolean setValue(String paramString)
    {
      return false;
    }

    void addValuesTo(Collection<String> paramCollection)
    {
    }

    void addToFormFields(FormFields paramFormFields)
    {
      if (getPredefinedValue() != null)
        paramFormFields.add(this);
    }

    void replaceInOutputDocument(OutputDocument paramOutputDocument)
    {
      if (this.outputStyle == FormControlOutputStyle.REMOVE)
      {
        paramOutputDocument.remove(getElement());
      }
      else
      {
        if (this.outputStyle == FormControlOutputStyle.DISPLAY_VALUE)
          setDisabled(true);
        replaceAttributesInOutputDocumentIfModified(paramOutputDocument);
      }
    }
  }

  static final class RadioCheckboxFormControl extends FormControl
  {
    public RadioCheckboxFormControl(Element paramElement, FormControlType paramFormControlType)
    {
      super(paramFormControlType, true, null);
      if (this.elementContainer.predefinedValue == null)
      {
        this.elementContainer.predefinedValue = "on";
        if (paramElement.source.logger.isInfoEnabled())
          paramElement.source.logger.info(": compulsory \"value\" attribute of " + paramFormControlType + " control \"" + this.name + "\" is missing, assuming the value \"" + "on" + '"');
      }
    }

    public boolean setValue(String paramString)
    {
      return this.elementContainer.setSelected(paramString, "checked", false);
    }

    public boolean addValue(String paramString)
    {
      return this.elementContainer.setSelected(paramString, "checked", this.formControlType == FormControlType.CHECKBOX);
    }

    void addValuesTo(Collection<String> paramCollection)
    {
      if (isChecked())
        FormControl.addValueTo(paramCollection, getPredefinedValue());
    }

    public boolean isChecked()
    {
      return this.elementContainer.getBooleanAttribute("checked");
    }

    void addToFormFields(FormFields paramFormFields)
    {
      paramFormFields.add(this);
    }

    void replaceInOutputDocument(OutputDocument paramOutputDocument)
    {
      if (this.outputStyle == FormControlOutputStyle.REMOVE)
      {
        paramOutputDocument.remove(getElement());
      }
      else
      {
        if (this.outputStyle == FormControlOutputStyle.DISPLAY_VALUE)
        {
          String str = isChecked() ? FormControlOutputStyle.ConfigDisplayValue.CheckedHTML : FormControlOutputStyle.ConfigDisplayValue.UncheckedHTML;
          if (str != null)
          {
            paramOutputDocument.replace(getElement(), str);
            return;
          }
          setDisabled(true);
        }
        replaceAttributesInOutputDocumentIfModified(paramOutputDocument);
      }
    }
  }

  static final class TextAreaFormControl extends FormControl
  {
    public String value = UNCHANGED;
    private static final String UNCHANGED = new String();

    public TextAreaFormControl(Element paramElement)
    {
      super(FormControlType.TEXTAREA, false, null);
    }

    public boolean setValue(String paramString)
    {
      this.value = paramString;
      return true;
    }

    void addValuesTo(Collection<String> paramCollection)
    {
      FormControl.addValueTo(paramCollection, getValue());
    }

    void addToFormFields(FormFields paramFormFields)
    {
      paramFormFields.add(this);
    }

    void replaceInOutputDocument(OutputDocument paramOutputDocument)
    {
      if (this.outputStyle == FormControlOutputStyle.REMOVE)
      {
        paramOutputDocument.remove(getElement());
      }
      else if (this.outputStyle == FormControlOutputStyle.DISPLAY_VALUE)
      {
        paramOutputDocument.replace(getElement(), getDisplayValueHTML(getValue(), true));
      }
      else
      {
        replaceAttributesInOutputDocumentIfModified(paramOutputDocument);
        if (this.value != UNCHANGED)
          paramOutputDocument.replace(getElement().getContent(), CharacterReference.encode(this.value));
      }
    }

    private String getValue()
    {
      return this.value == UNCHANGED ? CharacterReference.decode(getElement().getContent()) : this.value;
    }
  }

  static final class InputFormControl extends FormControl
  {
    public InputFormControl(Element paramElement, FormControlType paramFormControlType)
    {
      super(paramFormControlType, false, null);
    }

    public boolean setValue(String paramString)
    {
      this.elementContainer.setAttributeValue("value", paramString);
      return true;
    }

    void addValuesTo(Collection<String> paramCollection)
    {
      FormControl.addValueTo(paramCollection, this.elementContainer.getAttributeValue("value"));
    }

    void addToFormFields(FormFields paramFormFields)
    {
      paramFormFields.add(this);
    }

    void replaceInOutputDocument(OutputDocument paramOutputDocument)
    {
      if (this.outputStyle == FormControlOutputStyle.REMOVE)
      {
        paramOutputDocument.remove(getElement());
      }
      else if (this.outputStyle == FormControlOutputStyle.DISPLAY_VALUE)
      {
        String str1 = null;
        if (this.formControlType != FormControlType.HIDDEN)
        {
          String str2 = this.elementContainer.getAttributeValue("value");
          if ((this.formControlType == FormControlType.PASSWORD) && (str2 != null))
            str2 = FormControl.getString(FormControlOutputStyle.ConfigDisplayValue.PasswordChar, str2.length());
          str1 = getDisplayValueHTML(str2, false);
        }
        paramOutputDocument.replace(getElement(), str1);
      }
      else
      {
        replaceAttributesInOutputDocumentIfModified(paramOutputDocument);
      }
    }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.FormControl
 * JD-Core Version:    0.6.2
 */