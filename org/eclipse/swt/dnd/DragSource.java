package org.eclipse.swt.dnd;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.ImageList;
import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.COMObject;
import org.eclipse.swt.internal.ole.win32.FORMATETC;
import org.eclipse.swt.internal.ole.win32.GUID;
import org.eclipse.swt.internal.ole.win32.STGMEDIUM;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.Widget;

public class DragSource extends Widget
{
  Control control;
  Listener controlListener;
  Transfer[] transferAgents = new Transfer[0];
  DragSourceEffect dragEffect;
  Composite topControl;
  int hwndDrag;
  COMObject iDropSource;
  COMObject iDataObject;
  int refCount;
  int dataEffect = 0;
  static final String DEFAULT_DRAG_SOURCE_EFFECT = "DEFAULT_DRAG_SOURCE_EFFECT";
  static final int CFSTR_PERFORMEDDROPEFFECT = Transfer.registerType("Performed DropEffect");
  static final TCHAR WindowClass = new TCHAR(0, "#32770", true);

  public DragSource(Control paramControl, int paramInt)
  {
    super(paramControl, checkStyle(paramInt));
    this.control = paramControl;
    if (paramControl.getData("DragSource") != null)
      DND.error(2000);
    paramControl.setData("DragSource", this);
    createCOMInterfaces();
    AddRef();
    this.controlListener = new Listener()
    {
      public void handleEvent(Event paramAnonymousEvent)
      {
        if ((paramAnonymousEvent.type == 12) && (!DragSource.this.isDisposed()))
          DragSource.this.dispose();
        if ((paramAnonymousEvent.type == 29) && (!DragSource.this.isDisposed()))
          DragSource.this.drag(paramAnonymousEvent);
      }
    };
    paramControl.addListener(12, this.controlListener);
    paramControl.addListener(29, this.controlListener);
    addListener(12, new Listener()
    {
      public void handleEvent(Event paramAnonymousEvent)
      {
        DragSource.this.onDispose();
      }
    });
    Object localObject = paramControl.getData("DEFAULT_DRAG_SOURCE_EFFECT");
    if ((localObject instanceof DragSourceEffect))
      this.dragEffect = ((DragSourceEffect)localObject);
    else if ((paramControl instanceof Tree))
      this.dragEffect = new TreeDragSourceEffect((Tree)paramControl);
    else if ((paramControl instanceof Table))
      this.dragEffect = new TableDragSourceEffect((Table)paramControl);
  }

  static int checkStyle(int paramInt)
  {
    if (paramInt == 0)
      return 2;
    return paramInt;
  }

  public void addDragListener(DragSourceListener paramDragSourceListener)
  {
    if (paramDragSourceListener == null)
      DND.error(4);
    DNDListener localDNDListener = new DNDListener(paramDragSourceListener);
    localDNDListener.dndWidget = this;
    addListener(2008, localDNDListener);
    addListener(2001, localDNDListener);
    addListener(2000, localDNDListener);
  }

  private int AddRef()
  {
    this.refCount += 1;
    return this.refCount;
  }

  private void createCOMInterfaces()
  {
    this.iDropSource = new COMObject(new int[] { 2, 0, 0, 2, 1 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return DragSource.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return DragSource.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return DragSource.this.Release();
      }

      public int method3(int[] paramAnonymousArrayOfInt)
      {
        return DragSource.this.QueryContinueDrag(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method4(int[] paramAnonymousArrayOfInt)
      {
        return DragSource.this.GiveFeedback(paramAnonymousArrayOfInt[0]);
      }
    };
    this.iDataObject = new COMObject(new int[] { 2, 0, 0, 2, 2, 1, 2, 3, 2, 4, 1, 1 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return DragSource.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return DragSource.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return DragSource.this.Release();
      }

      public int method3(int[] paramAnonymousArrayOfInt)
      {
        return DragSource.this.GetData(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method5(int[] paramAnonymousArrayOfInt)
      {
        return DragSource.this.QueryGetData(paramAnonymousArrayOfInt[0]);
      }

      public int method7(int[] paramAnonymousArrayOfInt)
      {
        return DragSource.this.SetData(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }

      public int method8(int[] paramAnonymousArrayOfInt)
      {
        return DragSource.this.EnumFormatEtc(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }
    };
  }

  protected void checkSubclass()
  {
    String str1 = getClass().getName();
    String str2 = DragSource.class.getName();
    if (!str2.equals(str1))
      DND.error(43);
  }

  private void disposeCOMInterfaces()
  {
    if (this.iDropSource != null)
      this.iDropSource.dispose();
    this.iDropSource = null;
    if (this.iDataObject != null)
      this.iDataObject.dispose();
    this.iDataObject = null;
  }

  private void drag(Event paramEvent)
  {
    DNDEvent localDNDEvent = new DNDEvent();
    localDNDEvent.widget = this;
    localDNDEvent.x = paramEvent.x;
    localDNDEvent.y = paramEvent.y;
    localDNDEvent.time = OS.GetMessageTime();
    localDNDEvent.doit = true;
    notifyListeners(2008, localDNDEvent);
    if ((!localDNDEvent.doit) || (this.transferAgents == null) || (this.transferAgents.length == 0))
      return;
    int[] arrayOfInt = new int[1];
    int i = opToOs(getStyle());
    Display localDisplay = this.control.getDisplay();
    String str = "org.eclipse.swt.internal.win32.runMessagesInIdle";
    Object localObject1 = localDisplay.getData(str);
    localDisplay.setData(str, new Boolean(true));
    ImageList localImageList = null;
    Image localImage = localDNDEvent.image;
    this.hwndDrag = 0;
    this.topControl = null;
    if (localImage != null)
    {
      localImageList = new ImageList(0);
      localImageList.add(localImage);
      this.topControl = this.control.getShell();
      j = localDNDEvent.offsetX;
      this.hwndDrag = this.topControl.handle;
      if ((this.topControl.getStyle() & 0x4000000) != 0)
      {
        j = localImage.getBounds().width - j;
        RECT localRECT1 = new RECT();
        OS.GetClientRect(this.topControl.handle, localRECT1);
        this.hwndDrag = OS.CreateWindowEx(1048608, WindowClass, null, 1140850688, 0, 0, localRECT1.right - localRECT1.left, localRECT1.bottom - localRECT1.top, this.topControl.handle, 0, OS.GetModuleHandle(null), null);
        OS.ShowWindow(this.hwndDrag, 5);
      }
      OS.ImageList_BeginDrag(localImageList.getHandle(), 0, j, localDNDEvent.offsetY);
      if (OS.IsWinCE)
      {
        OS.UpdateWindow(this.topControl.handle);
      }
      else
      {
        int k = 384;
        OS.RedrawWindow(this.topControl.handle, null, 0, k);
      }
      POINT localPOINT = new POINT();
      localPOINT.x = paramEvent.x;
      localPOINT.y = paramEvent.y;
      OS.MapWindowPoints(this.control.handle, 0, localPOINT, 1);
      RECT localRECT2 = new RECT();
      OS.GetWindowRect(this.hwndDrag, localRECT2);
      OS.ImageList_DragEnter(this.hwndDrag, localPOINT.x - localRECT2.left, localPOINT.y - localRECT2.top);
    }
    int j = 262401;
    int m;
    try
    {
      j = COM.DoDragDrop(this.iDataObject.getAddress(), this.iDropSource.getAddress(), i, arrayOfInt);
    }
    finally
    {
      if (this.hwndDrag != 0)
      {
        OS.ImageList_DragLeave(this.hwndDrag);
        OS.ImageList_EndDrag();
        localImageList.dispose();
        if (this.hwndDrag != this.topControl.handle)
          OS.DestroyWindow(this.hwndDrag);
        this.hwndDrag = 0;
        this.topControl = null;
      }
      localDisplay.setData(str, localObject1);
      ret;
    }
    if (this.dataEffect == 2)
      m = (m == 0) || (m == 1) ? 8 : 2;
    else if (this.dataEffect != 0)
      m = this.dataEffect;
    localDNDEvent = new DNDEvent();
    localDNDEvent.widget = this;
    localDNDEvent.time = OS.GetMessageTime();
    localDNDEvent.doit = (j == 262400);
    localDNDEvent.detail = m;
    notifyListeners(2000, localDNDEvent);
    this.dataEffect = 0;
  }

  private int EnumFormatEtc(int paramInt1, int paramInt2)
  {
    if (paramInt1 == 2)
      return -2147467263;
    Object localObject1 = new TransferData[0];
    for (int i = 0; i < this.transferAgents.length; i++)
    {
      localObject2 = this.transferAgents[i];
      if (localObject2 != null)
      {
        TransferData[] arrayOfTransferData1 = ((Transfer)localObject2).getSupportedTypes();
        TransferData[] arrayOfTransferData2 = new TransferData[localObject1.length + arrayOfTransferData1.length];
        System.arraycopy(localObject1, 0, arrayOfTransferData2, 0, localObject1.length);
        System.arraycopy(arrayOfTransferData1, 0, arrayOfTransferData2, localObject1.length, arrayOfTransferData1.length);
        localObject1 = arrayOfTransferData2;
      }
    }
    OleEnumFORMATETC localOleEnumFORMATETC = new OleEnumFORMATETC();
    localOleEnumFORMATETC.AddRef();
    Object localObject2 = new FORMATETC[localObject1.length];
    for (int j = 0; j < localObject2.length; j++)
      localObject2[j] = localObject1[j].formatetc;
    localOleEnumFORMATETC.setFormats((FORMATETC[])localObject2);
    OS.MoveMemory(paramInt2, new int[] { localOleEnumFORMATETC.getAddress() }, OS.PTR_SIZEOF);
    return 0;
  }

  public Control getControl()
  {
    return this.control;
  }

  private int GetData(int paramInt1, int paramInt2)
  {
    if ((paramInt1 == 0) || (paramInt2 == 0))
      return -2147024809;
    if (QueryGetData(paramInt1) != 0)
      return -2147221404;
    TransferData localTransferData = new TransferData();
    localTransferData.formatetc = new FORMATETC();
    COM.MoveMemory(localTransferData.formatetc, paramInt1, FORMATETC.sizeof);
    localTransferData.type = localTransferData.formatetc.cfFormat;
    localTransferData.stgmedium = new STGMEDIUM();
    localTransferData.result = -2147467259;
    DNDEvent localDNDEvent = new DNDEvent();
    localDNDEvent.widget = this;
    localDNDEvent.time = OS.GetMessageTime();
    localDNDEvent.dataType = localTransferData;
    notifyListeners(2001, localDNDEvent);
    if (!localDNDEvent.doit)
      return -2147467259;
    Object localObject = null;
    for (int i = 0; i < this.transferAgents.length; i++)
    {
      Transfer localTransfer = this.transferAgents[i];
      if ((localTransfer != null) && (localTransfer.isSupportedType(localTransferData)))
      {
        localObject = localTransfer;
        break;
      }
    }
    if (localObject == null)
      return -2147221404;
    localObject.javaToNative(localDNDEvent.data, localTransferData);
    if (localTransferData.result != 0)
      return localTransferData.result;
    COM.MoveMemory(paramInt2, localTransferData.stgmedium, STGMEDIUM.sizeof);
    return localTransferData.result;
  }

  public DragSourceListener[] getDragListeners()
  {
    Listener[] arrayOfListener = getListeners(2008);
    int i = arrayOfListener.length;
    DragSourceListener[] arrayOfDragSourceListener1 = new DragSourceListener[i];
    int j = 0;
    for (int k = 0; k < i; k++)
    {
      Listener localListener = arrayOfListener[k];
      if ((localListener instanceof DNDListener))
      {
        arrayOfDragSourceListener1[j] = ((DragSourceListener)((DNDListener)localListener).getEventListener());
        j++;
      }
    }
    if (j == i)
      return arrayOfDragSourceListener1;
    DragSourceListener[] arrayOfDragSourceListener2 = new DragSourceListener[j];
    System.arraycopy(arrayOfDragSourceListener1, 0, arrayOfDragSourceListener2, 0, j);
    return arrayOfDragSourceListener2;
  }

  public DragSourceEffect getDragSourceEffect()
  {
    return this.dragEffect;
  }

  public Transfer[] getTransfer()
  {
    return this.transferAgents;
  }

  private int GiveFeedback(int paramInt)
  {
    return 262402;
  }

  private int QueryContinueDrag(int paramInt1, int paramInt2)
  {
    if ((this.topControl != null) && (this.topControl.isDisposed()))
      return 262401;
    if (paramInt1 != 0)
    {
      if (this.hwndDrag != 0)
        OS.ImageList_DragLeave(this.hwndDrag);
      return 262401;
    }
    int i = 19;
    if ((paramInt2 & i) == 0)
    {
      if (this.hwndDrag != 0)
        OS.ImageList_DragLeave(this.hwndDrag);
      return 262400;
    }
    if (this.hwndDrag != 0)
    {
      POINT localPOINT = new POINT();
      OS.GetCursorPos(localPOINT);
      RECT localRECT = new RECT();
      OS.GetWindowRect(this.hwndDrag, localRECT);
      OS.ImageList_DragMove(localPOINT.x - localRECT.left, localPOINT.y - localRECT.top);
    }
    return 0;
  }

  private void onDispose()
  {
    if (this.control == null)
      return;
    Release();
    if (this.controlListener != null)
    {
      this.control.removeListener(12, this.controlListener);
      this.control.removeListener(29, this.controlListener);
    }
    this.controlListener = null;
    this.control.setData("DragSource", null);
    this.control = null;
    this.transferAgents = null;
  }

  private int opToOs(int paramInt)
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

  private int osToOp(int paramInt)
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

  private int QueryGetData(int paramInt)
  {
    if (this.transferAgents == null)
      return -2147467259;
    TransferData localTransferData = new TransferData();
    localTransferData.formatetc = new FORMATETC();
    COM.MoveMemory(localTransferData.formatetc, paramInt, FORMATETC.sizeof);
    localTransferData.type = localTransferData.formatetc.cfFormat;
    for (int i = 0; i < this.transferAgents.length; i++)
    {
      Transfer localTransfer = this.transferAgents[i];
      if ((localTransfer != null) && (localTransfer.isSupportedType(localTransferData)))
        return 0;
    }
    return -2147221404;
  }

  private int QueryInterface(int paramInt1, int paramInt2)
  {
    if ((paramInt1 == 0) || (paramInt2 == 0))
      return -2147024809;
    GUID localGUID = new GUID();
    COM.MoveMemory(localGUID, paramInt1, GUID.sizeof);
    if ((COM.IsEqualGUID(localGUID, COM.IIDIUnknown)) || (COM.IsEqualGUID(localGUID, COM.IIDIDropSource)))
    {
      OS.MoveMemory(paramInt2, new int[] { this.iDropSource.getAddress() }, OS.PTR_SIZEOF);
      AddRef();
      return 0;
    }
    if (COM.IsEqualGUID(localGUID, COM.IIDIDataObject))
    {
      OS.MoveMemory(paramInt2, new int[] { this.iDataObject.getAddress() }, OS.PTR_SIZEOF);
      AddRef();
      return 0;
    }
    OS.MoveMemory(paramInt2, new int[1], OS.PTR_SIZEOF);
    return -2147467262;
  }

  private int Release()
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

  public void removeDragListener(DragSourceListener paramDragSourceListener)
  {
    if (paramDragSourceListener == null)
      DND.error(4);
    removeListener(2008, paramDragSourceListener);
    removeListener(2001, paramDragSourceListener);
    removeListener(2000, paramDragSourceListener);
  }

  private int SetData(int paramInt1, int paramInt2, int paramInt3)
  {
    if ((paramInt1 == 0) || (paramInt2 == 0))
      return -2147024809;
    FORMATETC localFORMATETC = new FORMATETC();
    COM.MoveMemory(localFORMATETC, paramInt1, FORMATETC.sizeof);
    if ((localFORMATETC.cfFormat == CFSTR_PERFORMEDDROPEFFECT) && (localFORMATETC.tymed == 1))
    {
      STGMEDIUM localSTGMEDIUM = new STGMEDIUM();
      COM.MoveMemory(localSTGMEDIUM, paramInt2, STGMEDIUM.sizeof);
      int[] arrayOfInt1 = new int[1];
      OS.MoveMemory(arrayOfInt1, localSTGMEDIUM.unionField, OS.PTR_SIZEOF);
      int[] arrayOfInt2 = new int[1];
      OS.MoveMemory(arrayOfInt2, arrayOfInt1[0], 4);
      this.dataEffect = osToOp(arrayOfInt2[0]);
    }
    if (paramInt3 == 1)
      COM.ReleaseStgMedium(paramInt2);
    return 0;
  }

  public void setDragSourceEffect(DragSourceEffect paramDragSourceEffect)
  {
    this.dragEffect = paramDragSourceEffect;
  }

  public void setTransfer(Transfer[] paramArrayOfTransfer)
  {
    this.transferAgents = paramArrayOfTransfer;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.dnd.DragSource
 * JD-Core Version:    0.6.2
 */