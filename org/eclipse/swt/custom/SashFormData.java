package org.eclipse.swt.custom;

class SashFormData
{
  long weight;

  String getName()
  {
    String str = getClass().getName();
    int i = str.lastIndexOf('.');
    if (i == -1)
      return str;
    return str.substring(i + 1, str.length());
  }

  public String toString()
  {
    return getName() + " {weight=" + this.weight + "}";
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.custom.SashFormData
 * JD-Core Version:    0.6.2
 */