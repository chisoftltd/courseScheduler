package org.eclipse.swt.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.COMObject;
import org.eclipse.swt.internal.ole.win32.GUID;
import org.eclipse.swt.internal.ole.win32.IConnectionPoint;
import org.eclipse.swt.internal.ole.win32.IConnectionPointContainer;
import org.eclipse.swt.internal.ole.win32.IUnknown;
import org.eclipse.swt.internal.win32.OS;

final class OlePropertyChangeSink
{
  private OleControlSite controlSite;
  private COMObject iUnknown;
  private COMObject iPropertyNotifySink;
  private int refCount;
  private int propertyCookie;
  private OleEventTable eventTable;

  OlePropertyChangeSink(OleControlSite paramOleControlSite)
  {
    this.controlSite = paramOleControlSite;
    createCOMInterfaces();
  }

  void addListener(int paramInt, OleListener paramOleListener)
  {
    if (paramOleListener == null)
      OLE.error(4);
    if (this.eventTable == null)
      this.eventTable = new OleEventTable();
    this.eventTable.hook(paramInt, paramOleListener);
  }

  int AddRef()
  {
    this.refCount += 1;
    return this.refCount;
  }

  void connect(IUnknown paramIUnknown)
  {
    int[] arrayOfInt1 = new int[1];
    if (paramIUnknown.QueryInterface(COM.IIDIConnectionPointContainer, arrayOfInt1) == 0)
    {
      IConnectionPointContainer localIConnectionPointContainer = new IConnectionPointContainer(arrayOfInt1[0]);
      if (localIConnectionPointContainer.FindConnectionPoint(COM.IIDIPropertyNotifySink, arrayOfInt1) == 0)
      {
        IConnectionPoint localIConnectionPoint = new IConnectionPoint(arrayOfInt1[0]);
        int[] arrayOfInt2 = new int[1];
        if (localIConnectionPoint.Advise(this.iPropertyNotifySink.getAddress(), arrayOfInt2) == 0)
          this.propertyCookie = arrayOfInt2[0];
        localIConnectionPoint.Release();
      }
      localIConnectionPointContainer.Release();
    }
  }

  private void createCOMInterfaces()
  {
    this.iUnknown = new COMObject(new int[] { 2 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return OlePropertyChangeSink.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return OlePropertyChangeSink.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return OlePropertyChangeSink.this.Release();
      }
    };
    this.iPropertyNotifySink = new COMObject(new int[] { 2, 0, 0, 1, 1 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return OlePropertyChangeSink.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return OlePropertyChangeSink.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return OlePropertyChangeSink.this.Release();
      }

      public int method3(int[] paramAnonymousArrayOfInt)
      {
        return OlePropertyChangeSink.this.OnChanged(paramAnonymousArrayOfInt[0]);
      }

      public int method4(int[] paramAnonymousArrayOfInt)
      {
        return OlePropertyChangeSink.this.OnRequestEdit(paramAnonymousArrayOfInt[0]);
      }
    };
  }

  void disconnect(IUnknown paramIUnknown)
  {
    if ((this.propertyCookie != 0) && (paramIUnknown != null))
    {
      int[] arrayOfInt = new int[1];
      if (paramIUnknown.QueryInterface(COM.IIDIConnectionPointContainer, arrayOfInt) == 0)
      {
        IConnectionPointContainer localIConnectionPointContainer = new IConnectionPointContainer(arrayOfInt[0]);
        if (localIConnectionPointContainer.FindConnectionPoint(COM.IIDIPropertyNotifySink, arrayOfInt) == 0)
        {
          IConnectionPoint localIConnectionPoint = new IConnectionPoint(arrayOfInt[0]);
          if (localIConnectionPoint.Unadvise(this.propertyCookie) == 0)
            this.propertyCookie = 0;
          localIConnectionPoint.Release();
        }
        localIConnectionPointContainer.Release();
      }
    }
  }

  private void disposeCOMInterfaces()
  {
    if (this.iUnknown != null)
      this.iUnknown.dispose();
    this.iUnknown = null;
    if (this.iPropertyNotifySink != null)
      this.iPropertyNotifySink.dispose();
    this.iPropertyNotifySink = null;
  }

  private void notifyListener(int paramInt, OleEvent paramOleEvent)
  {
    if (paramOleEvent == null)
      OLE.error(4);
    if (this.eventTable == null)
      return;
    paramOleEvent.type = paramInt;
    paramOleEvent.widget = this.controlSite;
    this.eventTable.sendEvent(paramOleEvent);
  }

  private int OnChanged(int paramInt)
  {
    if ((this.eventTable == null) || (!this.eventTable.hooks(paramInt)))
      return 0;
    OleEvent localOleEvent = new OleEvent();
    localOleEvent.detail = 1;
    notifyListener(paramInt, localOleEvent);
    return 0;
  }

  private int OnRequestEdit(int paramInt)
  {
    if ((this.eventTable == null) || (!this.eventTable.hooks(paramInt)))
      return 0;
    OleEvent localOleEvent = new OleEvent();
    localOleEvent.doit = true;
    localOleEvent.detail = 0;
    notifyListener(paramInt, localOleEvent);
    return localOleEvent.doit ? 0 : 1;
  }

  private int QueryInterface(int paramInt1, int paramInt2)
  {
    if ((paramInt1 == 0) || (paramInt2 == 0))
      return -2147024809;
    GUID localGUID = new GUID();
    COM.MoveMemory(localGUID, paramInt1, GUID.sizeof);
    if (COM.IsEqualGUID(localGUID, COM.IIDIUnknown))
    {
      COM.MoveMemory(paramInt2, new int[] { this.iUnknown.getAddress() }, OS.PTR_SIZEOF);
      AddRef();
      return 0;
    }
    if (COM.IsEqualGUID(localGUID, COM.IIDIPropertyNotifySink))
    {
      COM.MoveMemory(paramInt2, new int[] { this.iPropertyNotifySink.getAddress() }, OS.PTR_SIZEOF);
      AddRef();
      return 0;
    }
    COM.MoveMemory(paramInt2, new int[1], OS.PTR_SIZEOF);
    return -2147467262;
  }

  int Release()
  {
    this.refCount -= 1;
    if (this.refCount == 0)
      disposeCOMInterfaces();
    return this.refCount;
  }

  void removeListener(int paramInt, OleListener paramOleListener)
  {
    if (paramOleListener == null)
      OLE.error(4);
    if (this.eventTable == null)
      return;
    this.eventTable.unhook(paramInt, paramOleListener);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.ole.win32.OlePropertyChangeSink
 * JD-Core Version:    0.6.2
 */