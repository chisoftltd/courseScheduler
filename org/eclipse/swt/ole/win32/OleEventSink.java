package org.eclipse.swt.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.COMObject;
import org.eclipse.swt.internal.ole.win32.DISPPARAMS;
import org.eclipse.swt.internal.ole.win32.GUID;
import org.eclipse.swt.internal.ole.win32.IConnectionPoint;
import org.eclipse.swt.internal.ole.win32.IConnectionPointContainer;
import org.eclipse.swt.internal.ole.win32.IUnknown;
import org.eclipse.swt.internal.ole.win32.VARIANT;
import org.eclipse.swt.internal.win32.OS;

final class OleEventSink
{
  private OleControlSite widget;
  private COMObject iDispatch;
  private int refCount;
  private IUnknown objIUnknown;
  private int eventCookie;
  private GUID eventGuid;
  private OleEventTable eventTable;

  OleEventSink(OleControlSite paramOleControlSite, int paramInt, GUID paramGUID)
  {
    this.widget = paramOleControlSite;
    this.eventGuid = paramGUID;
    this.objIUnknown = new IUnknown(paramInt);
    createCOMInterfaces();
  }

  void connect()
  {
    int[] arrayOfInt1 = new int[1];
    if (this.objIUnknown.QueryInterface(COM.IIDIConnectionPointContainer, arrayOfInt1) == 0)
    {
      IConnectionPointContainer localIConnectionPointContainer = new IConnectionPointContainer(arrayOfInt1[0]);
      int[] arrayOfInt2 = new int[1];
      if (localIConnectionPointContainer.FindConnectionPoint(this.eventGuid, arrayOfInt2) == 0)
      {
        IConnectionPoint localIConnectionPoint = new IConnectionPoint(arrayOfInt2[0]);
        int[] arrayOfInt3 = new int[1];
        if (localIConnectionPoint.Advise(this.iDispatch.getAddress(), arrayOfInt3) == 0)
          this.eventCookie = arrayOfInt3[0];
        localIConnectionPoint.Release();
      }
      localIConnectionPointContainer.Release();
    }
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

  private void createCOMInterfaces()
  {
    this.iDispatch = new COMObject(new int[] { 2, 0, 0, 1, 3, 4, 8 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return OleEventSink.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return OleEventSink.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return OleEventSink.this.Release();
      }

      public int method6(int[] paramAnonymousArrayOfInt)
      {
        return OleEventSink.this.Invoke(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4], paramAnonymousArrayOfInt[5], paramAnonymousArrayOfInt[6], paramAnonymousArrayOfInt[7]);
      }
    };
  }

  void disconnect()
  {
    if ((this.eventCookie != 0) && (this.objIUnknown != null))
    {
      int[] arrayOfInt = new int[1];
      if (this.objIUnknown.QueryInterface(COM.IIDIConnectionPointContainer, arrayOfInt) == 0)
      {
        IConnectionPointContainer localIConnectionPointContainer = new IConnectionPointContainer(arrayOfInt[0]);
        if (localIConnectionPointContainer.FindConnectionPoint(this.eventGuid, arrayOfInt) == 0)
        {
          IConnectionPoint localIConnectionPoint = new IConnectionPoint(arrayOfInt[0]);
          if (localIConnectionPoint.Unadvise(this.eventCookie) == 0)
            this.eventCookie = 0;
          localIConnectionPoint.Release();
        }
        localIConnectionPointContainer.Release();
      }
    }
  }

  private void disposeCOMInterfaces()
  {
    if (this.iDispatch != null)
      this.iDispatch.dispose();
    this.iDispatch = null;
  }

  private int Invoke(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8)
  {
    if ((this.eventTable == null) || (!this.eventTable.hooks(paramInt1)))
      return 0;
    Variant[] arrayOfVariant = (Variant[])null;
    if (paramInt5 != 0)
    {
      localObject = new DISPPARAMS();
      COM.MoveMemory((DISPPARAMS)localObject, paramInt5, DISPPARAMS.sizeof);
      arrayOfVariant = new Variant[((DISPPARAMS)localObject).cArgs];
      int i = VARIANT.sizeof;
      int j = (((DISPPARAMS)localObject).cArgs - 1) * i;
      for (int k = 0; k < ((DISPPARAMS)localObject).cArgs; k++)
      {
        arrayOfVariant[k] = new Variant();
        arrayOfVariant[k].setData(((DISPPARAMS)localObject).rgvarg + j);
        j -= i;
      }
    }
    Object localObject = new OleEvent();
    ((OleEvent)localObject).arguments = arrayOfVariant;
    notifyListener(paramInt1, (OleEvent)localObject);
    return 0;
  }

  private void notifyListener(int paramInt, OleEvent paramOleEvent)
  {
    if (paramOleEvent == null)
      OLE.error(4);
    if (this.eventTable == null)
      return;
    paramOleEvent.type = paramInt;
    paramOleEvent.widget = this.widget;
    this.eventTable.sendEvent(paramOleEvent);
  }

  private int QueryInterface(int paramInt1, int paramInt2)
  {
    if ((paramInt1 == 0) || (paramInt2 == 0))
      return -2147024809;
    GUID localGUID = new GUID();
    COM.MoveMemory(localGUID, paramInt1, GUID.sizeof);
    if ((COM.IsEqualGUID(localGUID, COM.IIDIUnknown)) || (COM.IsEqualGUID(localGUID, COM.IIDIDispatch)) || (COM.IsEqualGUID(localGUID, this.eventGuid)))
    {
      COM.MoveMemory(paramInt2, new int[] { this.iDispatch.getAddress() }, OS.PTR_SIZEOF);
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

  boolean hasListeners()
  {
    return this.eventTable.hasEntries();
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.ole.win32.OleEventSink
 * JD-Core Version:    0.6.2
 */