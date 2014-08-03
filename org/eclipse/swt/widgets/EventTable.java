package org.eclipse.swt.widgets;

import org.eclipse.swt.internal.SWTEventListener;

class EventTable
{
  int[] types;
  Listener[] listeners;
  int level;
  static final int GROW_SIZE = 4;

  public Listener[] getListeners(int paramInt)
  {
    if (this.types == null)
      return new Listener[0];
    int i = 0;
    for (int j = 0; j < this.types.length; j++)
      if (this.types[j] == paramInt)
        i++;
    if (i == 0)
      return new Listener[0];
    Listener[] arrayOfListener = new Listener[i];
    i = 0;
    for (int k = 0; k < this.types.length; k++)
      if (this.types[k] == paramInt)
        arrayOfListener[(i++)] = this.listeners[k];
    return arrayOfListener;
  }

  public void hook(int paramInt, Listener paramListener)
  {
    if (this.types == null)
      this.types = new int[4];
    if (this.listeners == null)
      this.listeners = new Listener[4];
    int i = this.types.length;
    for (int j = i - 1; j >= 0; j--)
      if (this.types[j] != 0)
        break;
    j++;
    if (j == i)
    {
      int[] arrayOfInt = new int[i + 4];
      System.arraycopy(this.types, 0, arrayOfInt, 0, i);
      this.types = arrayOfInt;
      Listener[] arrayOfListener = new Listener[i + 4];
      System.arraycopy(this.listeners, 0, arrayOfListener, 0, i);
      this.listeners = arrayOfListener;
    }
    this.types[j] = paramInt;
    this.listeners[j] = paramListener;
  }

  public boolean hooks(int paramInt)
  {
    if (this.types == null)
      return false;
    for (int i = 0; i < this.types.length; i++)
      if (this.types[i] == paramInt)
        return true;
    return false;
  }

  public void sendEvent(Event paramEvent)
  {
    if (this.types == null)
      return;
    this.level += (this.level >= 0 ? 1 : -1);
    try
    {
      for (int i = 0; i < this.types.length; i++)
      {
        if (paramEvent.type == 0)
          return;
        if (this.types[i] == paramEvent.type)
        {
          Listener localListener = this.listeners[i];
          if (localListener != null)
            localListener.handleEvent(paramEvent);
        }
      }
    }
    finally
    {
      int j = this.level < 0 ? 1 : 0;
      this.level -= (this.level >= 0 ? 1 : -1);
      if ((j != 0) && (this.level == 0))
      {
        int k = 0;
        for (int m = 0; m < this.types.length; m++)
          if (this.types[m] != 0)
          {
            this.types[k] = this.types[m];
            this.listeners[k] = this.listeners[m];
            k++;
          }
        for (m = k; m < this.types.length; m++)
        {
          this.types[m] = 0;
          this.listeners[m] = null;
        }
      }
    }
    jsr -150;
  }

  public int size()
  {
    if (this.types == null)
      return 0;
    int i = 0;
    for (int j = 0; j < this.types.length; j++)
      if (this.types[j] != 0)
        i++;
    return i;
  }

  void remove(int paramInt)
  {
    if (this.level == 0)
    {
      int i = this.types.length - 1;
      System.arraycopy(this.types, paramInt + 1, this.types, paramInt, i - paramInt);
      System.arraycopy(this.listeners, paramInt + 1, this.listeners, paramInt, i - paramInt);
      paramInt = i;
    }
    else if (this.level > 0)
    {
      this.level = (-this.level);
    }
    this.types[paramInt] = 0;
    this.listeners[paramInt] = null;
  }

  public void unhook(int paramInt, Listener paramListener)
  {
    if (this.types == null)
      return;
    for (int i = 0; i < this.types.length; i++)
      if ((this.types[i] == paramInt) && (this.listeners[i] == paramListener))
      {
        remove(i);
        return;
      }
  }

  public void unhook(int paramInt, SWTEventListener paramSWTEventListener)
  {
    if (this.types == null)
      return;
    for (int i = 0; i < this.types.length; i++)
      if ((this.types[i] == paramInt) && ((this.listeners[i] instanceof TypedListener)))
      {
        TypedListener localTypedListener = (TypedListener)this.listeners[i];
        if (localTypedListener.getEventListener() == paramSWTEventListener)
        {
          remove(i);
          return;
        }
      }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.EventTable
 * JD-Core Version:    0.6.2
 */