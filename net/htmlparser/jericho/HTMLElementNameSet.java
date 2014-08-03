package net.htmlparser.jericho;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

final class HTMLElementNameSet extends HashSet<String>
{
  public HTMLElementNameSet()
  {
    super(1);
  }

  public HTMLElementNameSet(String[] paramArrayOfString)
  {
    super(paramArrayOfString.length * 2);
    for (int i = 0; i < paramArrayOfString.length; i++)
      add(paramArrayOfString[i]);
  }

  public HTMLElementNameSet(Collection<String> paramCollection)
  {
    super(paramCollection.size() * 2);
    union(paramCollection);
  }

  public HTMLElementNameSet(String paramString)
  {
    super(2);
    add(paramString);
  }

  HTMLElementNameSet union(String paramString)
  {
    add(paramString);
    return this;
  }

  HTMLElementNameSet union(Collection<String> paramCollection)
  {
    Iterator localIterator = paramCollection.iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      add(str);
    }
    return this;
  }

  HTMLElementNameSet minus(String paramString)
  {
    remove(paramString);
    return this;
  }

  HTMLElementNameSet minus(Collection<String> paramCollection)
  {
    Iterator localIterator = paramCollection.iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      remove(str);
    }
    return this;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.HTMLElementNameSet
 * JD-Core Version:    0.6.2
 */