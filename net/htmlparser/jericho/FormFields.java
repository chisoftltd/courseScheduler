package net.htmlparser.jericho;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class FormFields extends AbstractCollection<FormField>
{
  private final LinkedHashMap<String, FormField> map = new LinkedHashMap();
  private final ArrayList<FormControl> formControls = new ArrayList();
  private Column[] columns = null;

  public FormFields(Collection<FormControl> paramCollection)
  {
    Iterator localIterator = paramCollection.iterator();
    while (localIterator.hasNext())
    {
      FormControl localFormControl = (FormControl)localIterator.next();
      if ((localFormControl.getName() != null) && (localFormControl.getName().length() != 0))
      {
        localFormControl.addToFormFields(this);
        this.formControls.add(localFormControl);
      }
    }
  }

  public int getCount()
  {
    return this.map.size();
  }

  public int size()
  {
    return getCount();
  }

  public FormField get(String paramString)
  {
    if (Config.CurrentCompatibilityMode.isFormFieldNameCaseInsensitive())
      paramString = paramString.toLowerCase();
    return (FormField)this.map.get(paramString);
  }

  public Iterator<FormField> iterator()
  {
    return this.map.values().iterator();
  }

  public List<String> getValues(String paramString)
  {
    FormField localFormField = get(paramString);
    return localFormField == null ? null : localFormField.getValues();
  }

  public Map<String, String[]> getDataSet()
  {
    LinkedHashMap localLinkedHashMap = new LinkedHashMap((int)(getCount() / 0.7D));
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      FormField localFormField = (FormField)localIterator.next();
      List localList = localFormField.getValues();
      if (!localList.isEmpty())
        localLinkedHashMap.put(localFormField.getName(), localList.toArray(new String[localList.size()]));
    }
    return localLinkedHashMap;
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

  public void setDataSet(Map<String, String[]> paramMap)
  {
    clearValues();
    if (this.map == null)
      return;
    Iterator localIterator = paramMap.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      String str = (String)localEntry.getKey();
      FormField localFormField = get(str);
      if (localFormField != null)
        localFormField.addValues((String[])localEntry.getValue());
    }
  }

  public boolean setValue(String paramString1, String paramString2)
  {
    FormField localFormField = get(paramString1);
    return localFormField == null ? false : localFormField.setValue(paramString2);
  }

  public boolean addValue(String paramString1, String paramString2)
  {
    FormField localFormField = get(paramString1);
    return localFormField == null ? false : localFormField.addValue(paramString2);
  }

  public String[] getColumnLabels()
  {
    initColumns();
    String[] arrayOfString = new String[this.columns.length];
    for (int i = 0; i < this.columns.length; i++)
    {
      Column localColumn = this.columns[i];
      String str = localColumn.formField.getFirstFormControl().getName();
      arrayOfString[i] = (localColumn.predefinedValue != null ? str + '.' + localColumn.predefinedValue : str);
    }
    return arrayOfString;
  }

  public String[] getColumnValues(Map<String, String[]> paramMap)
  {
    initColumns();
    String[] arrayOfString1 = new String[this.columns.length];
    if (Config.ColumnValueFalse != null)
      for (int i = 0; i < this.columns.length; i++)
        if (this.columns[i].isBoolean)
          arrayOfString1[i] = Config.ColumnValueFalse;
    Iterator localIterator = paramMap.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      String str1 = (String)localEntry.getKey();
      FormField localFormField = get(str1);
      if (localFormField != null)
      {
        int j = localFormField.columnIndex;
        for (String str2 : (String[])localEntry.getValue())
          for (int n = j; n < this.columns.length; n++)
          {
            Column localColumn = this.columns[n];
            if (localColumn.formField != localFormField)
              break;
            if (localColumn.predefinedValue != null)
            {
              if (localColumn.predefinedValue.equals(str2))
              {
                arrayOfString1[n] = Config.ColumnValueTrue;
                break;
              }
            }
            else
            {
              if (localColumn.isBoolean)
              {
                if (str2 == null)
                  break;
                arrayOfString1[n] = Config.ColumnValueTrue;
                break;
              }
              if (arrayOfString1[n] == null)
              {
                arrayOfString1[n] = str2;
                break;
              }
              arrayOfString1[n] = (arrayOfString1[n] + Config.ColumnMultipleValueSeparator + str2);
              break;
            }
          }
      }
    }
    return arrayOfString1;
  }

  public String[] getColumnValues()
  {
    return getColumnValues(getDataSet());
  }

  private void initColumns()
  {
    if (this.columns != null)
      return;
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator1 = iterator();
    while (localIterator1.hasNext())
    {
      FormField localFormField = (FormField)localIterator1.next();
      localFormField.columnIndex = localArrayList.size();
      if ((!localFormField.allowsMultipleValues()) || (localFormField.getPredefinedValues().isEmpty()))
      {
        localArrayList.add(new Column(localFormField, localFormField.getPredefinedValues().size() == 1, null));
      }
      else
      {
        Iterator localIterator2 = localFormField.getPredefinedValues().iterator();
        while (localIterator2.hasNext())
        {
          String str = (String)localIterator2.next();
          localArrayList.add(new Column(localFormField, true, str));
        }
        if (localFormField.getUserValueCount() > 0)
          localArrayList.add(new Column(localFormField, false, null));
      }
    }
    this.columns = ((Column[])localArrayList.toArray(new Column[localArrayList.size()]));
  }

  public List getFormControls()
  {
    return this.formControls;
  }

  public void merge(FormFields paramFormFields)
  {
    Iterator localIterator = paramFormFields.iterator();
    while (localIterator.hasNext())
    {
      FormField localFormField1 = (FormField)localIterator.next();
      String str = localFormField1.getName();
      FormField localFormField2 = get(str);
      if (localFormField2 == null)
        this.map.put(localFormField1.getName(), localFormField1);
      else
        localFormField2.merge(localFormField1);
    }
  }

  public String getDebugInfo()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      FormField localFormField = (FormField)localIterator.next();
      localStringBuilder.append(localFormField);
    }
    return localStringBuilder.toString();
  }

  public String toString()
  {
    return getDebugInfo();
  }

  void add(FormControl paramFormControl)
  {
    add(paramFormControl, paramFormControl.getPredefinedValue());
  }

  void add(FormControl paramFormControl, String paramString)
  {
    add(paramFormControl, paramString, paramFormControl.name);
  }

  void addName(FormControl paramFormControl, String paramString)
  {
    add(paramFormControl, null, paramString);
  }

  void add(FormControl paramFormControl, String paramString1, String paramString2)
  {
    if (Config.CurrentCompatibilityMode.isFormFieldNameCaseInsensitive())
      paramString2 = paramString2.toLowerCase();
    FormField localFormField = (FormField)this.map.get(paramString2);
    if (localFormField == null)
    {
      localFormField = new FormField(paramString2);
      this.map.put(localFormField.getName(), localFormField);
    }
    localFormField.addFormControl(paramFormControl, paramString1);
  }

  void replaceInOutputDocument(OutputDocument paramOutputDocument)
  {
    Iterator localIterator = this.formControls.iterator();
    while (localIterator.hasNext())
    {
      FormControl localFormControl = (FormControl)localIterator.next();
      paramOutputDocument.replace(localFormControl);
    }
  }

  private static class Column
  {
    public FormField formField;
    public boolean isBoolean;
    public String predefinedValue;

    public Column(FormField paramFormField, boolean paramBoolean, String paramString)
    {
      this.formField = paramFormField;
      this.isBoolean = paramBoolean;
      this.predefinedValue = paramString;
    }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.FormFields
 * JD-Core Version:    0.6.2
 */