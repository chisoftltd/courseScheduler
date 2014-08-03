package org.eclipse.swt.ole.win32;

class OleEventTable
{
  int[] types;
  OleListener[] handlers;

  void hook(int paramInt, OleListener paramOleListener)
  {
    if (this.types == null)
      this.types = new int[4];
    if (this.handlers == null)
      this.handlers = new OleListener[4];
    for (int i = 0; i < this.types.length; i++)
      if (this.types[i] == 0)
      {
        this.types[i] = paramInt;
        this.handlers[i] = paramOleListener;
        return;
      }
    i = this.types.length;
    int[] arrayOfInt = new int[i + 4];
    OleListener[] arrayOfOleListener = new OleListener[i + 4];
    System.arraycopy(this.types, 0, arrayOfInt, 0, i);
    System.arraycopy(this.handlers, 0, arrayOfOleListener, 0, i);
    this.types = arrayOfInt;
    this.handlers = arrayOfOleListener;
    this.types[i] = paramInt;
    this.handlers[i] = paramOleListener;
  }

  boolean hooks(int paramInt)
  {
    if (this.handlers == null)
      return false;
    for (int i = 0; i < this.types.length; i++)
      if (this.types[i] == paramInt)
        return true;
    return false;
  }

  void sendEvent(OleEvent paramOleEvent)
  {
    if (this.handlers == null)
      return;
    for (int i = 0; i < this.types.length; i++)
      if (this.types[i] == paramOleEvent.type)
      {
        OleListener localOleListener = this.handlers[i];
        if (localOleListener != null)
          localOleListener.handleEvent(paramOleEvent);
      }
  }

  void unhook(int paramInt, OleListener paramOleListener)
  {
    if (this.handlers == null)
      return;
    for (int i = 0; i < this.types.length; i++)
      if ((this.types[i] == paramInt) && (this.handlers[i] == paramOleListener))
      {
        this.types[i] = 0;
        this.handlers[i] = null;
        return;
      }
  }

  boolean hasEntries()
  {
    for (int i = 0; i < this.types.length; i++)
      if (this.types[i] != 0)
        return true;
    return false;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.ole.win32.OleEventTable
 * JD-Core Version:    0.6.2
 */