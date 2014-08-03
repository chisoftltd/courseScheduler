package net.htmlparser.jericho.nodoc;

import java.lang.reflect.Array;
import java.util.AbstractList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import net.htmlparser.jericho.Segment;
import net.htmlparser.jericho.Source;

public abstract class SequentialListSegment<E> extends Segment
  implements List<E>
{
  public SequentialListSegment(Source paramSource, int paramInt1, int paramInt2)
  {
    super(paramSource, paramInt1, paramInt2);
  }

  public abstract int getCount();

  public abstract ListIterator<E> listIterator(int paramInt);

  public E get(int paramInt)
  {
    ListIterator localListIterator = listIterator(paramInt);
    try
    {
      return localListIterator.next();
    }
    catch (NoSuchElementException localNoSuchElementException)
    {
    }
    throw new IndexOutOfBoundsException("index=" + paramInt);
  }

  public int size()
  {
    return getCount();
  }

  public boolean isEmpty()
  {
    return getCount() == 0;
  }

  public boolean contains(Object paramObject)
  {
    return indexOf(paramObject) >= 0;
  }

  public Object[] toArray()
  {
    Object[] arrayOfObject = new Object[getCount()];
    int i = 0;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Object localObject = localIterator.next();
      arrayOfObject[(i++)] = localObject;
    }
    return arrayOfObject;
  }

  public <T> T[] toArray(T[] paramArrayOfT)
  {
    int i = getCount();
    if (paramArrayOfT.length < i)
      paramArrayOfT = (Object[])Array.newInstance(paramArrayOfT.getClass().getComponentType(), i);
    int j = 0;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Object localObject = localIterator.next();
      paramArrayOfT[(j++)] = localObject;
    }
    if (paramArrayOfT.length > i)
      paramArrayOfT[i] = null;
    return paramArrayOfT;
  }

  public boolean remove(Object paramObject)
  {
    throw new UnsupportedOperationException();
  }

  public boolean containsAll(Collection<?> paramCollection)
  {
    Iterator localIterator = paramCollection.iterator();
    while (localIterator.hasNext())
    {
      Object localObject = localIterator.next();
      if (!contains(localObject))
        return false;
    }
    return true;
  }

  public boolean addAll(Collection<? extends E> paramCollection)
  {
    throw new UnsupportedOperationException();
  }

  public boolean removeAll(Collection<?> paramCollection)
  {
    throw new UnsupportedOperationException();
  }

  public boolean retainAll(Collection<?> paramCollection)
  {
    throw new UnsupportedOperationException();
  }

  public boolean add(E paramE)
  {
    throw new UnsupportedOperationException();
  }

  public E set(int paramInt, E paramE)
  {
    throw new UnsupportedOperationException();
  }

  public void add(int paramInt, E paramE)
  {
    throw new UnsupportedOperationException();
  }

  public E remove(int paramInt)
  {
    throw new UnsupportedOperationException();
  }

  public int indexOf(Object paramObject)
  {
    ListIterator localListIterator = listIterator(0);
    if (paramObject == null)
    {
      do
        if (!localListIterator.hasNext())
          break;
      while (localListIterator.next() != null);
      return localListIterator.previousIndex();
    }
    while (localListIterator.hasNext())
      if (paramObject.equals(localListIterator.next()))
        return localListIterator.previousIndex();
    return -1;
  }

  public int lastIndexOf(Object paramObject)
  {
    ListIterator localListIterator = listIterator(getCount());
    if (paramObject == null)
    {
      do
        if (!localListIterator.hasPrevious())
          break;
      while (localListIterator.previous() != null);
      return localListIterator.nextIndex();
    }
    while (localListIterator.hasPrevious())
      if (paramObject.equals(localListIterator.previous()))
        return localListIterator.nextIndex();
    return -1;
  }

  public void clear()
  {
    throw new UnsupportedOperationException();
  }

  public boolean addAll(int paramInt, Collection<? extends E> paramCollection)
  {
    throw new UnsupportedOperationException();
  }

  public Iterator<E> iterator()
  {
    return listIterator();
  }

  public ListIterator<E> listIterator()
  {
    return listIterator(0);
  }

  public List<E> subList(int paramInt1, int paramInt2)
  {
    return new SubList(this, paramInt1, paramInt2);
  }

  private static class SubList<E> extends AbstractList<E>
  {
    private final List<E> list;
    private final int offset;
    private final int size;

    SubList(List<E> paramList, int paramInt1, int paramInt2)
    {
      if (paramInt1 < 0)
        throw new IndexOutOfBoundsException("fromIndex=" + paramInt1);
      if (paramInt2 > paramList.size())
        throw new IndexOutOfBoundsException("toIndex=" + paramInt2);
      if (paramInt1 > paramInt2)
        throw new IllegalArgumentException("fromIndex(" + paramInt1 + ") > toIndex(" + paramInt2 + ")");
      this.list = paramList;
      this.offset = paramInt1;
      this.size = (paramInt2 - paramInt1);
    }

    public E get(int paramInt)
    {
      return this.list.get(getSuperListIndex(paramInt));
    }

    public int size()
    {
      return this.size;
    }

    public Iterator<E> iterator()
    {
      return listIterator();
    }

    public ListIterator<E> listIterator(final int paramInt)
    {
      return new ListIterator()
      {
        private final ListIterator<E> i = SequentialListSegment.SubList.this.list.listIterator(SequentialListSegment.SubList.this.getSuperListIndex(paramInt));

        public boolean hasNext()
        {
          return nextIndex() < SequentialListSegment.SubList.this.size;
        }

        public E next()
        {
          if (!hasNext())
            throw new NoSuchElementException();
          return this.i.next();
        }

        public boolean hasPrevious()
        {
          return previousIndex() >= 0;
        }

        public E previous()
        {
          if (!hasPrevious())
            throw new NoSuchElementException();
          return this.i.previous();
        }

        public int nextIndex()
        {
          return this.i.nextIndex() - SequentialListSegment.SubList.this.offset;
        }

        public int previousIndex()
        {
          return this.i.previousIndex() - SequentialListSegment.SubList.this.offset;
        }

        public void remove()
        {
          throw new UnsupportedOperationException();
        }

        public void set(E paramAnonymousE)
        {
          throw new UnsupportedOperationException();
        }

        public void add(E paramAnonymousE)
        {
          throw new UnsupportedOperationException();
        }
      };
    }

    public List<E> subList(int paramInt1, int paramInt2)
    {
      return new SubList(this, paramInt1, paramInt2);
    }

    private int getSuperListIndex(int paramInt)
    {
      if ((paramInt < 0) || (paramInt >= this.size))
        throw new IndexOutOfBoundsException("index=" + paramInt + ", size=" + this.size);
      return paramInt + this.offset;
    }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.nodoc.SequentialListSegment
 * JD-Core Version:    0.6.2
 */