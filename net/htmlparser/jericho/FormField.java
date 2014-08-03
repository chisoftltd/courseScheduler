package net.htmlparser.jericho;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

public final class FormField
{
  private final String name;
  private int userValueCount = 0;
  private boolean allowsMultipleValues = false;
  private LinkedHashSet<String> predefinedValues = null;
  private final LinkedHashSet<FormControl> formControls = new LinkedHashSet();
  private transient FormControl firstFormControl = null;
  int columnIndex;

  FormField(String paramString)
  {
    this.name = paramString;
  }

  public String getName()
  {
    return this.name;
  }

  public Collection<FormControl> getFormControls()
  {
    return this.formControls;
  }

  public FormControl getFormControl(String paramString)
  {
    Iterator localIterator;
    FormControl localFormControl;
    if (paramString == null)
    {
      localIterator = this.formControls.iterator();
      while (localIterator.hasNext())
      {
        localFormControl = (FormControl)localIterator.next();
        if (!localFormControl.getFormControlType().hasPredefinedValue())
          return localFormControl;
        if ((localFormControl.getFormControlType().getElementName() != "select") && (localFormControl.getPredefinedValue() == null))
          return localFormControl;
      }
    }
    else
    {
      localIterator = this.formControls.iterator();
      while (localIterator.hasNext())
      {
        localFormControl = (FormControl)localIterator.next();
        if (localFormControl.getFormControlType().getElementName() == "select")
        {
          if (localFormControl.getPredefinedValues().contains(paramString))
            return localFormControl;
        }
        else if (paramString.equals(localFormControl.getPredefinedValue()))
          return localFormControl;
      }
    }
    return null;
  }

  public FormControl getFormControl()
  {
    return (FormControl)this.formControls.iterator().next();
  }

  public boolean allowsMultipleValues()
  {
    return this.allowsMultipleValues;
  }

  public int getUserValueCount()
  {
    return this.userValueCount;
  }

  public Collection<String> getPredefinedValues()
  {
    if (this.predefinedValues == null)
      return Collections.emptySet();
    return this.predefinedValues;
  }

  public List<String> getValues()
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = this.formControls.iterator();
    while (localIterator.hasNext())
    {
      FormControl localFormControl = (FormControl)localIterator.next();
      localFormControl.addValuesTo(localArrayList);
    }
    return localArrayList;
  }

  public void clearValues()
  {
    Iterator localIterator = this.formControls.iterator();
    while (localIterator.hasNext())
    {
      FormControl localFormControl = (FormControl)localIterator.next();
      localFormControl.clearValues();
    }
  }

  public void setValues(Collection<String> paramCollection)
  {
    clearValues();
    addValues(paramCollection);
  }

  public boolean setValue(String paramString)
  {
    clearValues();
    return paramString != null ? addValue(paramString) : true;
  }

  public boolean addValue(String paramString)
  {
    if (paramString == null)
      throw new IllegalArgumentException("value argument must not be null");
    if (this.formControls.size() == 1)
      return getFirstFormControl().addValue(paramString);
    LinkedList localLinkedList = null;
    Iterator localIterator = this.formControls.iterator();
    FormControl localFormControl;
    while (localIterator.hasNext())
    {
      localFormControl = (FormControl)localIterator.next();
      if (!localFormControl.getFormControlType().hasPredefinedValue())
      {
        if (localLinkedList == null)
          localLinkedList = new LinkedList();
        localLinkedList.add(localFormControl);
      }
      else if (localFormControl.addValue(paramString))
      {
        return true;
      }
    }
    if (localLinkedList == null)
      return false;
    localIterator = localLinkedList.iterator();
    while (localIterator.hasNext())
    {
      localFormControl = (FormControl)localIterator.next();
      if (localFormControl.addValue(paramString))
        return true;
    }
    return false;
  }

  public String getDebugInfo()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Field: ").append(this.name).append(", UserValueCount=").append(this.userValueCount).append(", AllowsMultipleValues=").append(this.allowsMultipleValues);
    Object localObject;
    if (this.predefinedValues != null)
    {
      localIterator = this.predefinedValues.iterator();
      while (localIterator.hasNext())
      {
        localObject = (String)localIterator.next();
        localStringBuilder.append(Config.NewLine).append("PredefinedValue: ").append((String)localObject);
      }
    }
    Iterator localIterator = this.formControls.iterator();
    while (localIterator.hasNext())
    {
      localObject = (FormControl)localIterator.next();
      localStringBuilder.append(Config.NewLine).append("FormControl: ").append(((FormControl)localObject).getDebugInfo());
    }
    localStringBuilder.append(Config.NewLine).append(Config.NewLine);
    return localStringBuilder.toString();
  }

  public String toString()
  {
    return getDebugInfo();
  }

  void addValues(Collection<String> paramCollection)
  {
    if (paramCollection != null)
    {
      Iterator localIterator = paramCollection.iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        addValue(str);
      }
    }
  }

  void addValues(String[] paramArrayOfString)
  {
    if (paramArrayOfString != null)
      for (String str : paramArrayOfString)
        addValue(str);
  }

  void addFormControl(FormControl paramFormControl, String paramString)
  {
    if (paramString == null)
    {
      this.userValueCount += 1;
    }
    else
    {
      if (this.predefinedValues == null)
        this.predefinedValues = new LinkedHashSet();
      this.predefinedValues.add(paramString);
    }
    this.formControls.add(paramFormControl);
    this.allowsMultipleValues = calculateAllowsMultipleValues(paramFormControl);
  }

  private boolean calculateAllowsMultipleValues(FormControl paramFormControl)
  {
    if ((this.allowsMultipleValues) || (this.userValueCount > 1))
      return true;
    if (this.userValueCount == 1)
      return this.predefinedValues != null;
    if (this.predefinedValues.size() == 1)
      return false;
    FormControlType localFormControlType1 = paramFormControl.getFormControlType();
    if (this.formControls.size() == 1)
      return localFormControlType1 == FormControlType.SELECT_MULTIPLE;
    FormControlType localFormControlType2 = getFirstFormControl().getFormControlType();
    if ((localFormControlType1 == FormControlType.RADIO) && (localFormControlType2 == FormControlType.RADIO))
      return false;
    return (!localFormControlType1.isSubmit()) || (!localFormControlType2.isSubmit());
  }

  FormControl getFirstFormControl()
  {
    if (this.firstFormControl == null)
      this.firstFormControl = ((FormControl)this.formControls.iterator().next());
    return this.firstFormControl;
  }

  void merge(FormField paramFormField)
  {
    if (paramFormField.userValueCount > this.userValueCount)
      this.userValueCount = paramFormField.userValueCount;
    this.allowsMultipleValues = ((this.allowsMultipleValues) || (paramFormField.allowsMultipleValues));
    Object localObject;
    if (this.predefinedValues == null)
    {
      this.predefinedValues = paramFormField.predefinedValues;
    }
    else if (paramFormField.predefinedValues != null)
    {
      localIterator = this.predefinedValues.iterator();
      while (localIterator.hasNext())
      {
        localObject = (String)localIterator.next();
        this.predefinedValues.add(localObject);
      }
    }
    Iterator localIterator = paramFormField.getFormControls().iterator();
    while (localIterator.hasNext())
    {
      localObject = (FormControl)localIterator.next();
      this.formControls.add(localObject);
    }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.FormField
 * JD-Core Version:    0.6.2
 */