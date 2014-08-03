package org.eclipse.swt.dnd;

import org.eclipse.swt.internal.C;
import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.COMObject;
import org.eclipse.swt.internal.ole.win32.FORMATETC;
import org.eclipse.swt.internal.ole.win32.GUID;
import org.eclipse.swt.internal.ole.win32.IDataObject;
import org.eclipse.swt.internal.ole.win32.IEnumFORMATETC;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.Widget;

public class DropTarget extends Widget
{
  Control control;
  Listener controlListener;
  Transfer[] transferAgents = new Transfer[0];
  DropTargetEffect dropEffect;
  TransferData selectedDataType;
  int selectedOperation;
  int keyOperation = -1;
  IDataObject iDataObject;
  COMObject iDropTarget;
  int refCount;
  static final String DEFAULT_DROP_TARGET_EFFECT = "DEFAULT_DROP_TARGET_EFFECT";

  public DropTarget(Control paramControl, int paramInt)
  {
    super(paramControl, checkStyle(paramInt));
    this.control = paramControl;
    if (paramControl.getData("DropTarget") != null)
      DND.error(2001);
    paramControl.setData("DropTarget", this);
    createCOMInterfaces();
    AddRef();
    if (COM.CoLockObjectExternal(this.iDropTarget.getAddress(), true, true) != 0)
      DND.error(2001);
    if (COM.RegisterDragDrop(paramControl.handle, this.iDropTarget.getAddress()) != 0)
      DND.error(2001);
    this.controlListener = new Listener()
    {
      public void handleEvent(Event paramAnonymousEvent)
      {
        if (!DropTarget.this.isDisposed())
          DropTarget.this.dispose();
      }
    };
    paramControl.addListener(12, this.controlListener);
    addListener(12, new Listener()
    {
      public void handleEvent(Event paramAnonymousEvent)
      {
        DropTarget.this.onDispose();
      }
    });
    Object localObject = paramControl.getData("DEFAULT_DROP_TARGET_EFFECT");
    if ((localObject instanceof DropTargetEffect))
      this.dropEffect = ((DropTargetEffect)localObject);
    else if ((paramControl instanceof Table))
      this.dropEffect = new TableDropTargetEffect((Table)paramControl);
    else if ((paramControl instanceof Tree))
      this.dropEffect = new TreeDropTargetEffect((Tree)paramControl);
  }

  static int checkStyle(int paramInt)
  {
    if (paramInt == 0)
      return 2;
    return paramInt;
  }

  public void addDropListener(DropTargetListener paramDropTargetListener)
  {
    if (paramDropTargetListener == null)
      DND.error(4);
    DNDListener localDNDListener = new DNDListener(paramDropTargetListener);
    localDNDListener.dndWidget = this;
    addListener(2002, localDNDListener);
    addListener(2003, localDNDListener);
    addListener(2004, localDNDListener);
    addListener(2005, localDNDListener);
    addListener(2006, localDNDListener);
    addListener(2007, localDNDListener);
  }

  int AddRef()
  {
    this.refCount += 1;
    return this.refCount;
  }

  protected void checkSubclass()
  {
    String str1 = getClass().getName();
    String str2 = DropTarget.class.getName();
    if (!str2.equals(str1))
      DND.error(43);
  }

  void createCOMInterfaces()
  {
    int i = C.PTR_SIZEOF == 4 ? 1 : 0;
    this.iDropTarget = new COMObject(new int[] { 2, 0, 0, i != 0 ? 5 : 4, i != 0 ? 4 : 3, 0, i != 0 ? 5 : 4 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return DropTarget.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return DropTarget.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return DropTarget.this.Release();
      }

      public int method3(int[] paramAnonymousArrayOfInt)
      {
        if (paramAnonymousArrayOfInt.length == 5)
          return DropTarget.this.DragEnter(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4]);
        return DropTarget.this.DragEnter_64(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3]);
      }

      public int method4(int[] paramAnonymousArrayOfInt)
      {
        if (paramAnonymousArrayOfInt.length == 4)
          return DropTarget.this.DragOver(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3]);
        return DropTarget.this.DragOver_64(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }

      public int method5(int[] paramAnonymousArrayOfInt)
      {
        return DropTarget.this.DragLeave();
      }

      public int method6(int[] paramAnonymousArrayOfInt)
      {
        if (paramAnonymousArrayOfInt.length == 5)
          return DropTarget.this.Drop(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4]);
        return DropTarget.this.Drop_64(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3]);
      }
    };
  }

  void disposeCOMInterfaces()
  {
    if (this.iDropTarget != null)
      this.iDropTarget.dispose();
    this.iDropTarget = null;
  }

  int DragEnter_64(int paramInt1, int paramInt2, long paramLong, int paramInt3)
  {
    POINT localPOINT = new POINT();
    OS.MoveMemory(localPOINT, new long[] { paramLong }, 8);
    return DragEnter(paramInt1, paramInt2, localPOINT.x, localPOINT.y, paramInt3);
  }

  int DragEnter(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    this.selectedDataType = null;
    this.selectedOperation = 0;
    if (this.iDataObject != null)
      this.iDataObject.Release();
    this.iDataObject = null;
    DNDEvent localDNDEvent = new DNDEvent();
    if (!setEventData(localDNDEvent, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5))
    {
      OS.MoveMemory(paramInt5, new int[1], 4);
      return 1;
    }
    this.iDataObject = new IDataObject(paramInt1);
    this.iDataObject.AddRef();
    int i = localDNDEvent.operations;
    TransferData[] arrayOfTransferData = new TransferData[localDNDEvent.dataTypes.length];
    System.arraycopy(localDNDEvent.dataTypes, 0, arrayOfTransferData, 0, arrayOfTransferData.length);
    notifyListeners(2002, localDNDEvent);
    refresh();
    if (localDNDEvent.detail == 16)
      localDNDEvent.detail = ((i & 0x2) != 0 ? 2 : 0);
    this.selectedDataType = null;
    for (int j = 0; j < arrayOfTransferData.length; j++)
      if (TransferData.sameType(arrayOfTransferData[j], localDNDEvent.dataType))
      {
        this.selectedDataType = arrayOfTransferData[j];
        break;
      }
    this.selectedOperation = 0;
    if ((this.selectedDataType != null) && ((i & localDNDEvent.detail) != 0))
      this.selectedOperation = localDNDEvent.detail;
    OS.MoveMemory(paramInt5, new int[] { opToOs(this.selectedOperation) }, 4);
    return 0;
  }

  int DragLeave()
  {
    this.keyOperation = -1;
    if (this.iDataObject == null)
      return 1;
    DNDEvent localDNDEvent = new DNDEvent();
    localDNDEvent.widget = this;
    localDNDEvent.time = OS.GetMessageTime();
    localDNDEvent.detail = 0;
    notifyListeners(2003, localDNDEvent);
    refresh();
    this.iDataObject.Release();
    this.iDataObject = null;
    return 0;
  }

  int DragOver_64(int paramInt1, long paramLong, int paramInt2)
  {
    POINT localPOINT = new POINT();
    OS.MoveMemory(localPOINT, new long[] { paramLong }, 8);
    return DragOver(paramInt1, localPOINT.x, localPOINT.y, paramInt2);
  }

  int DragOver(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.iDataObject == null)
      return 1;
    int i = this.keyOperation;
    DNDEvent localDNDEvent = new DNDEvent();
    if (!setEventData(localDNDEvent, this.iDataObject.getAddress(), paramInt1, paramInt2, paramInt3, paramInt4))
    {
      this.keyOperation = -1;
      OS.MoveMemory(paramInt4, new int[1], 4);
      return 1;
    }
    int j = localDNDEvent.operations;
    TransferData[] arrayOfTransferData = new TransferData[localDNDEvent.dataTypes.length];
    System.arraycopy(localDNDEvent.dataTypes, 0, arrayOfTransferData, 0, arrayOfTransferData.length);
    if (this.keyOperation == i)
    {
      localDNDEvent.type = 2004;
      localDNDEvent.dataType = this.selectedDataType;
      localDNDEvent.detail = this.selectedOperation;
    }
    else
    {
      localDNDEvent.type = 2005;
      localDNDEvent.dataType = this.selectedDataType;
    }
    notifyListeners(localDNDEvent.type, localDNDEvent);
    refresh();
    if (localDNDEvent.detail == 16)
      localDNDEvent.detail = ((j & 0x2) != 0 ? 2 : 0);
    this.selectedDataType = null;
    for (int k = 0; k < arrayOfTransferData.length; k++)
      if (TransferData.sameType(arrayOfTransferData[k], localDNDEvent.dataType))
      {
        this.selectedDataType = arrayOfTransferData[k];
        break;
      }
    this.selectedOperation = 0;
    if ((this.selectedDataType != null) && ((j & localDNDEvent.detail) == localDNDEvent.detail))
      this.selectedOperation = localDNDEvent.detail;
    OS.MoveMemory(paramInt4, new int[] { opToOs(this.selectedOperation) }, 4);
    return 0;
  }

  int Drop_64(int paramInt1, int paramInt2, long paramLong, int paramInt3)
  {
    POINT localPOINT = new POINT();
    OS.MoveMemory(localPOINT, new long[] { paramLong }, 8);
    return Drop(paramInt1, paramInt2, localPOINT.x, localPOINT.y, paramInt3);
  }

  int Drop(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    DNDEvent localDNDEvent = new DNDEvent();
    localDNDEvent.widget = this;
    localDNDEvent.time = OS.GetMessageTime();
    if (this.dropEffect != null)
      localDNDEvent.item = this.dropEffect.getItem(paramInt3, paramInt4);
    localDNDEvent.detail = 0;
    notifyListeners(2003, localDNDEvent);
    refresh();
    localDNDEvent = new DNDEvent();
    if (!setEventData(localDNDEvent, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5))
    {
      this.keyOperation = -1;
      OS.MoveMemory(paramInt5, new int[1], 4);
      return 1;
    }
    this.keyOperation = -1;
    int i = localDNDEvent.operations;
    TransferData[] arrayOfTransferData = new TransferData[localDNDEvent.dataTypes.length];
    System.arraycopy(localDNDEvent.dataTypes, 0, arrayOfTransferData, 0, arrayOfTransferData.length);
    localDNDEvent.dataType = this.selectedDataType;
    localDNDEvent.detail = this.selectedOperation;
    notifyListeners(2007, localDNDEvent);
    refresh();
    this.selectedDataType = null;
    for (int j = 0; j < arrayOfTransferData.length; j++)
      if (TransferData.sameType(arrayOfTransferData[j], localDNDEvent.dataType))
      {
        this.selectedDataType = arrayOfTransferData[j];
        break;
      }
    this.selectedOperation = 0;
    if ((this.selectedDataType != null) && ((i & localDNDEvent.detail) == localDNDEvent.detail))
      this.selectedOperation = localDNDEvent.detail;
    if (this.selectedOperation == 0)
    {
      OS.MoveMemory(paramInt5, new int[1], 4);
      return 0;
    }
    Object localObject1 = null;
    for (int k = 0; k < this.transferAgents.length; k++)
    {
      Transfer localTransfer = this.transferAgents[k];
      if ((localTransfer != null) && (localTransfer.isSupportedType(this.selectedDataType)))
      {
        localObject1 = localTransfer.nativeToJava(this.selectedDataType);
        break;
      }
    }
    if (localObject1 == null)
      this.selectedOperation = 0;
    localDNDEvent.detail = this.selectedOperation;
    localDNDEvent.dataType = this.selectedDataType;
    localDNDEvent.data = localObject1;
    OS.ImageList_DragShowNolock(false);
    try
    {
      notifyListeners(2006, localDNDEvent);
    }
    finally
    {
      OS.ImageList_DragShowNolock(true);
      ret;
    }
    this.selectedOperation = 0;
    if ((i & localDNDEvent.detail) == localDNDEvent.detail)
      this.selectedOperation = localDNDEvent.detail;
    OS.MoveMemory(paramInt5, new int[] { opToOs(this.selectedOperation) }, 4);
    return 0;
  }

  public Control getControl()
  {
    return this.control;
  }

  public DropTargetListener[] getDropListeners()
  {
    Listener[] arrayOfListener = getListeners(2002);
    int i = arrayOfListener.length;
    DropTargetListener[] arrayOfDropTargetListener1 = new DropTargetListener[i];
    int j = 0;
    for (int k = 0; k < i; k++)
    {
      Listener localListener = arrayOfListener[k];
      if ((localListener instanceof DNDListener))
      {
        arrayOfDropTargetListener1[j] = ((DropTargetListener)((DNDListener)localListener).getEventListener());
        j++;
      }
    }
    if (j == i)
      return arrayOfDropTargetListener1;
    DropTargetListener[] arrayOfDropTargetListener2 = new DropTargetListener[j];
    System.arraycopy(arrayOfDropTargetListener1, 0, arrayOfDropTargetListener2, 0, j);
    return arrayOfDropTargetListener2;
  }

  public DropTargetEffect getDropTargetEffect()
  {
    return this.dropEffect;
  }

  int getOperationFromKeyState(int paramInt)
  {
    int i = (paramInt & 0x8) != 0 ? 1 : 0;
    int j = (paramInt & 0x4) != 0 ? 1 : 0;
    int k = (paramInt & 0x20) != 0 ? 1 : 0;
    if (k != 0)
    {
      if ((i != 0) || (j != 0))
        return 16;
      return 4;
    }
    if ((i != 0) && (j != 0))
      return 4;
    if (i != 0)
      return 1;
    if (j != 0)
      return 2;
    return 16;
  }

  public Transfer[] getTransfer()
  {
    return this.transferAgents;
  }

  void onDispose()
  {
    if (this.control == null)
      return;
    COM.RevokeDragDrop(this.control.handle);
    if (this.controlListener != null)
      this.control.removeListener(12, this.controlListener);
    this.controlListener = null;
    this.control.setData("DropTarget", null);
    this.transferAgents = null;
    this.control = null;
    COM.CoLockObjectExternal(this.iDropTarget.getAddress(), false, true);
    Release();
    if (this.iDataObject != null)
      this.iDataObject.Release();
    this.iDataObject = null;
    if (COM.FreeUnusedLibraries)
      COM.CoFreeUnusedLibraries();
  }

  int opToOs(int paramInt)
  {
    int i = 0;
    if ((paramInt & 0x1) != 0)
      i |= 1;
    if ((paramInt & 0x4) != 0)
      i |= 4;
    if ((paramInt & 0x2) != 0)
      i |= 2;
    return i;
  }

  int osToOp(int paramInt)
  {
    int i = 0;
    if ((paramInt & 0x1) != 0)
      i |= 1;
    if ((paramInt & 0x4) != 0)
      i |= 4;
    if ((paramInt & 0x2) != 0)
      i |= 2;
    return i;
  }

  int QueryInterface(int paramInt1, int paramInt2)
  {
    if ((paramInt1 == 0) || (paramInt2 == 0))
      return -2147024809;
    GUID localGUID = new GUID();
    COM.MoveMemory(localGUID, paramInt1, GUID.sizeof);
    if ((COM.IsEqualGUID(localGUID, COM.IIDIUnknown)) || (COM.IsEqualGUID(localGUID, COM.IIDIDropTarget)))
    {
      COM.MoveMemory(paramInt2, new int[] { this.iDropTarget.getAddress() }, OS.PTR_SIZEOF);
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
    {
      disposeCOMInterfaces();
      if (COM.FreeUnusedLibraries)
        COM.CoFreeUnusedLibraries();
    }
    return this.refCount;
  }

  void refresh()
  {
    if ((this.control == null) || (this.control.isDisposed()))
      return;
    int i = this.control.handle;
    RECT localRECT = new RECT();
    if (OS.GetUpdateRect(i, localRECT, false))
    {
      OS.ImageList_DragShowNolock(false);
      OS.RedrawWindow(i, localRECT, 0, 257);
      OS.ImageList_DragShowNolock(true);
    }
  }

  public void removeDropListener(DropTargetListener paramDropTargetListener)
  {
    if (paramDropTargetListener == null)
      DND.error(4);
    removeListener(2002, paramDropTargetListener);
    removeListener(2003, paramDropTargetListener);
    removeListener(2004, paramDropTargetListener);
    removeListener(2005, paramDropTargetListener);
    removeListener(2006, paramDropTargetListener);
    removeListener(2007, paramDropTargetListener);
  }

  public void setDropTargetEffect(DropTargetEffect paramDropTargetEffect)
  {
    this.dropEffect = paramDropTargetEffect;
  }

  boolean setEventData(DNDEvent paramDNDEvent, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    if ((paramInt1 == 0) || (paramInt5 == 0))
      return false;
    int i = getStyle();
    int[] arrayOfInt1 = new int[1];
    OS.MoveMemory(arrayOfInt1, paramInt5, 4);
    arrayOfInt1[0] = (osToOp(arrayOfInt1[0]) & i);
    if (arrayOfInt1[0] == 0)
      return false;
    int j = getOperationFromKeyState(paramInt2);
    this.keyOperation = j;
    if (j == 16)
    {
      if ((i & 0x10) == 0)
        j = (arrayOfInt1[0] & 0x2) != 0 ? 2 : 0;
    }
    else if ((j & arrayOfInt1[0]) == 0)
      j = 0;
    Object localObject1 = new TransferData[0];
    IDataObject localIDataObject = new IDataObject(paramInt1);
    localIDataObject.AddRef();
    try
    {
      int[] arrayOfInt2 = new int[1];
      if (localIDataObject.EnumFormatEtc(1, arrayOfInt2) != 0)
        return false;
      IEnumFORMATETC localIEnumFORMATETC = new IEnumFORMATETC(arrayOfInt2[0]);
      try
      {
        int k = OS.GlobalAlloc(64, FORMATETC.sizeof);
        try
        {
          int[] arrayOfInt3 = new int[1];
          localIEnumFORMATETC.Reset();
          do
          {
            TransferData localTransferData = new TransferData();
            localTransferData.formatetc = new FORMATETC();
            COM.MoveMemory(localTransferData.formatetc, k, FORMATETC.sizeof);
            localTransferData.type = localTransferData.formatetc.cfFormat;
            localTransferData.pIDataObject = paramInt1;
            for (int m = 0; m < this.transferAgents.length; m++)
            {
              Transfer localTransfer = this.transferAgents[m];
              if ((localTransfer != null) && (localTransfer.isSupportedType(localTransferData)))
              {
                TransferData[] arrayOfTransferData = new TransferData[localObject1.length + 1];
                System.arraycopy(localObject1, 0, arrayOfTransferData, 0, localObject1.length);
                arrayOfTransferData[localObject1.length] = localTransferData;
                localObject1 = arrayOfTransferData;
                break;
              }
            }
            if (localIEnumFORMATETC.Next(1, k, arrayOfInt3) != 0)
              break;
          }
          while (arrayOfInt3[0] == 1);
        }
        finally
        {
          OS.GlobalFree(k);
          ret;
        }
      }
      finally
      {
        localIEnumFORMATETC.Release();
        ret;
      }
    }
    finally
    {
      localIDataObject.Release();
    }
    jsr -10;
    if (localObject1.length == 0)
      return false;
    paramDNDEvent.widget = this;
    paramDNDEvent.x = paramInt3;
    paramDNDEvent.y = paramInt4;
    paramDNDEvent.time = OS.GetMessageTime();
    paramDNDEvent.feedback = 1;
    paramDNDEvent.dataTypes = ((TransferData[])localObject1);
    paramDNDEvent.dataType = localObject1[0];
    if (this.dropEffect != null)
      paramDNDEvent.item = this.dropEffect.getItem(paramInt3, paramInt4);
    paramDNDEvent.operations = arrayOfInt1[0];
    paramDNDEvent.detail = j;
    return true;
  }

  public void setTransfer(Transfer[] paramArrayOfTransfer)
  {
    if (paramArrayOfTransfer == null)
      DND.error(4);
    this.transferAgents = paramArrayOfTransfer;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.dnd.DropTarget
 * JD-Core Version:    0.6.2
 */