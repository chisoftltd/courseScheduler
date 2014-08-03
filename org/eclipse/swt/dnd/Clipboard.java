package org.eclipse.swt.dnd;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.COMObject;
import org.eclipse.swt.internal.ole.win32.FORMATETC;
import org.eclipse.swt.internal.ole.win32.GUID;
import org.eclipse.swt.internal.ole.win32.IDataObject;
import org.eclipse.swt.internal.ole.win32.IEnumFORMATETC;
import org.eclipse.swt.internal.ole.win32.STGMEDIUM;
import org.eclipse.swt.internal.win32.MSG;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.widgets.Display;

public class Clipboard
{
  private static final int RETRY_LIMIT = 10;
  private Display display;
  private COMObject iDataObject;
  private int refCount;
  private Transfer[] transferAgents = new Transfer[0];
  private Object[] data = new Object[0];
  private int CFSTR_PREFERREDDROPEFFECT;

  public Clipboard(Display paramDisplay)
  {
    checkSubclass();
    if (paramDisplay == null)
    {
      paramDisplay = Display.getCurrent();
      if (paramDisplay == null)
        paramDisplay = Display.getDefault();
    }
    if (paramDisplay.getThread() != Thread.currentThread())
      DND.error(22);
    this.display = paramDisplay;
    TCHAR localTCHAR = new TCHAR(0, "Preferred DropEffect", true);
    this.CFSTR_PREFERREDDROPEFFECT = OS.RegisterClipboardFormat(localTCHAR);
    createCOMInterfaces();
    AddRef();
  }

  protected void checkSubclass()
  {
    String str1 = getClass().getName();
    String str2 = Clipboard.class.getName();
    if (!str2.equals(str1))
      DND.error(43);
  }

  protected void checkWidget()
  {
    Display localDisplay = this.display;
    if (localDisplay == null)
      DND.error(24);
    if (localDisplay.getThread() != Thread.currentThread())
      DND.error(22);
    if (localDisplay.isDisposed())
      DND.error(24);
  }

  public void clearContents()
  {
    clearContents(1);
  }

  public void clearContents(int paramInt)
  {
    checkWidget();
    if (((paramInt & 0x1) != 0) && (COM.OleIsCurrentClipboard(this.iDataObject.getAddress()) == 0))
      COM.OleSetClipboard(0);
  }

  public void dispose()
  {
    if (isDisposed())
      return;
    if (this.display.getThread() != Thread.currentThread())
      DND.error(22);
    if (COM.OleIsCurrentClipboard(this.iDataObject.getAddress()) == 0)
      COM.OleFlushClipboard();
    Release();
    this.display = null;
  }

  public Object getContents(Transfer paramTransfer)
  {
    return getContents(paramTransfer, 1);
  }

  public Object getContents(Transfer paramTransfer, int paramInt)
  {
    checkWidget();
    if (paramTransfer == null)
      DND.error(4);
    if ((paramInt & 0x1) == 0)
      return null;
    int[] arrayOfInt = new int[1];
    int i = 0;
    for (int j = COM.OleGetClipboard(arrayOfInt); (j != 0) && (i++ < 10); j = COM.OleGetClipboard(arrayOfInt))
    {
      try
      {
        Thread.sleep(50L);
      }
      catch (Throwable localThrowable)
      {
      }
      localObject1 = new MSG();
      OS.PeekMessage((MSG)localObject1, 0, 0, 0, 2);
    }
    if (j != 0)
      return null;
    Object localObject1 = new IDataObject(arrayOfInt[0]);
    try
    {
      TransferData[] arrayOfTransferData = paramTransfer.getSupportedTypes();
      for (int k = 0; k < arrayOfTransferData.length; k++)
        if (((IDataObject)localObject1).QueryGetData(arrayOfTransferData[k].formatetc) == 0)
        {
          TransferData localTransferData = arrayOfTransferData[k];
          localTransferData.pIDataObject = arrayOfInt[0];
          Object localObject4 = paramTransfer.nativeToJava(localTransferData);
          return localObject4;
        }
    }
    finally
    {
      ((IDataObject)localObject1).Release();
    }
    jsr -10;
    return null;
  }

  public boolean isDisposed()
  {
    return this.display == null;
  }

  public void setContents(Object[] paramArrayOfObject, Transfer[] paramArrayOfTransfer)
  {
    setContents(paramArrayOfObject, paramArrayOfTransfer, 1);
  }

  public void setContents(Object[] paramArrayOfObject, Transfer[] paramArrayOfTransfer, int paramInt)
  {
    checkWidget();
    if ((paramArrayOfObject == null) || (paramArrayOfTransfer == null) || (paramArrayOfObject.length != paramArrayOfTransfer.length) || (paramArrayOfObject.length == 0))
      DND.error(5);
    for (int i = 0; i < paramArrayOfObject.length; i++)
      if ((paramArrayOfObject[i] == null) || (paramArrayOfTransfer[i] == null) || (!paramArrayOfTransfer[i].validate(paramArrayOfObject[i])))
        DND.error(5);
    if ((paramInt & 0x1) == 0)
      return;
    this.data = paramArrayOfObject;
    this.transferAgents = paramArrayOfTransfer;
    i = COM.OleSetClipboard(this.iDataObject.getAddress());
    int j = 0;
    while ((i != 0) && (j++ < 10))
    {
      try
      {
        Thread.sleep(50L);
      }
      catch (Throwable localThrowable)
      {
      }
      MSG localMSG = new MSG();
      OS.PeekMessage(localMSG, 0, 0, 0, 2);
      i = COM.OleSetClipboard(this.iDataObject.getAddress());
    }
    if (i != 0)
      DND.error(2002);
  }

  private int AddRef()
  {
    this.refCount += 1;
    return this.refCount;
  }

  private void createCOMInterfaces()
  {
    this.iDataObject = new COMObject(new int[] { 2, 0, 0, 2, 2, 1, 2, 3, 2, 4, 1, 1 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return Clipboard.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return Clipboard.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return Clipboard.this.Release();
      }

      public int method3(int[] paramAnonymousArrayOfInt)
      {
        return Clipboard.this.GetData(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method5(int[] paramAnonymousArrayOfInt)
      {
        return Clipboard.this.QueryGetData(paramAnonymousArrayOfInt[0]);
      }

      public int method8(int[] paramAnonymousArrayOfInt)
      {
        return Clipboard.this.EnumFormatEtc(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }
    };
  }

  private void disposeCOMInterfaces()
  {
    if (this.iDataObject != null)
      this.iDataObject.dispose();
    this.iDataObject = null;
  }

  private int EnumFormatEtc(int paramInt1, int paramInt2)
  {
    if (paramInt1 == 2)
      return -2147467263;
    Object localObject1 = new TransferData[0];
    for (int i = 0; i < this.transferAgents.length; i++)
    {
      localObject2 = this.transferAgents[i].getSupportedTypes();
      TransferData[] arrayOfTransferData = new TransferData[localObject1.length + localObject2.length];
      System.arraycopy(localObject1, 0, arrayOfTransferData, 0, localObject1.length);
      System.arraycopy(localObject2, 0, arrayOfTransferData, localObject1.length, localObject2.length);
      localObject1 = arrayOfTransferData;
    }
    OleEnumFORMATETC localOleEnumFORMATETC = new OleEnumFORMATETC();
    localOleEnumFORMATETC.AddRef();
    Object localObject2 = new FORMATETC[localObject1.length + 1];
    for (int j = 0; j < localObject1.length; j++)
      localObject2[j] = localObject1[j].formatetc;
    FORMATETC localFORMATETC = new FORMATETC();
    localFORMATETC.cfFormat = this.CFSTR_PREFERREDDROPEFFECT;
    localFORMATETC.dwAspect = 1;
    localFORMATETC.lindex = -1;
    localFORMATETC.tymed = 1;
    localObject2[(localObject2.length - 1)] = localFORMATETC;
    localOleEnumFORMATETC.setFormats((FORMATETC[])localObject2);
    OS.MoveMemory(paramInt2, new int[] { localOleEnumFORMATETC.getAddress() }, OS.PTR_SIZEOF);
    return 0;
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
    if (localTransferData.type == this.CFSTR_PREFERREDDROPEFFECT)
    {
      STGMEDIUM localSTGMEDIUM = new STGMEDIUM();
      localSTGMEDIUM.tymed = 1;
      localSTGMEDIUM.unionField = OS.GlobalAlloc(64, 4);
      OS.MoveMemory(localSTGMEDIUM.unionField, new int[] { 1 }, 4);
      localSTGMEDIUM.pUnkForRelease = 0;
      COM.MoveMemory(paramInt2, localSTGMEDIUM, STGMEDIUM.sizeof);
      return 0;
    }
    int i = -1;
    for (int j = 0; j < this.transferAgents.length; j++)
      if (this.transferAgents[j].isSupportedType(localTransferData))
      {
        i = j;
        break;
      }
    if (i == -1)
      return -2147221404;
    this.transferAgents[i].javaToNative(this.data[i], localTransferData);
    COM.MoveMemory(paramInt2, localTransferData.stgmedium, STGMEDIUM.sizeof);
    return localTransferData.result;
  }

  private int QueryGetData(int paramInt)
  {
    if (this.transferAgents == null)
      return -2147467259;
    TransferData localTransferData = new TransferData();
    localTransferData.formatetc = new FORMATETC();
    COM.MoveMemory(localTransferData.formatetc, paramInt, FORMATETC.sizeof);
    localTransferData.type = localTransferData.formatetc.cfFormat;
    if (localTransferData.type == this.CFSTR_PREFERREDDROPEFFECT)
      return 0;
    for (int i = 0; i < this.transferAgents.length; i++)
      if (this.transferAgents[i].isSupportedType(localTransferData))
        return 0;
    return -2147221404;
  }

  private int QueryInterface(int paramInt1, int paramInt2)
  {
    if ((paramInt1 == 0) || (paramInt2 == 0))
      return -2147024809;
    GUID localGUID = new GUID();
    COM.MoveMemory(localGUID, paramInt1, GUID.sizeof);
    if ((COM.IsEqualGUID(localGUID, COM.IIDIUnknown)) || (COM.IsEqualGUID(localGUID, COM.IIDIDataObject)))
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
      this.data = new Object[0];
      this.transferAgents = new Transfer[0];
      disposeCOMInterfaces();
      if (COM.FreeUnusedLibraries)
        COM.CoFreeUnusedLibraries();
    }
    return this.refCount;
  }

  public TransferData[] getAvailableTypes()
  {
    return getAvailableTypes(1);
  }

  public TransferData[] getAvailableTypes(int paramInt)
  {
    checkWidget();
    if ((paramInt & 0x1) == 0)
      return new TransferData[0];
    FORMATETC[] arrayOfFORMATETC = _getAvailableTypes();
    TransferData[] arrayOfTransferData = new TransferData[arrayOfFORMATETC.length];
    for (int i = 0; i < arrayOfFORMATETC.length; i++)
    {
      arrayOfTransferData[i] = new TransferData();
      arrayOfTransferData[i].type = arrayOfFORMATETC[i].cfFormat;
      arrayOfTransferData[i].formatetc = arrayOfFORMATETC[i];
    }
    return arrayOfTransferData;
  }

  public String[] getAvailableTypeNames()
  {
    checkWidget();
    FORMATETC[] arrayOfFORMATETC = _getAvailableTypes();
    String[] arrayOfString = new String[arrayOfFORMATETC.length];
    int i = 128;
    for (int j = 0; j < arrayOfFORMATETC.length; j++)
    {
      TCHAR localTCHAR = new TCHAR(0, i);
      int k = OS.GetClipboardFormatName(arrayOfFORMATETC[j].cfFormat, localTCHAR, i);
      if (k != 0)
        arrayOfString[j] = localTCHAR.toString(0, k);
      else
        switch (arrayOfFORMATETC[j].cfFormat)
        {
        case 15:
          arrayOfString[j] = "CF_HDROP";
          break;
        case 1:
          arrayOfString[j] = "CF_TEXT";
          break;
        case 2:
          arrayOfString[j] = "CF_BITMAP";
          break;
        case 3:
          arrayOfString[j] = "CF_METAFILEPICT";
          break;
        case 4:
          arrayOfString[j] = "CF_SYLK";
          break;
        case 5:
          arrayOfString[j] = "CF_DIF";
          break;
        case 6:
          arrayOfString[j] = "CF_TIFF";
          break;
        case 7:
          arrayOfString[j] = "CF_OEMTEXT";
          break;
        case 8:
          arrayOfString[j] = "CF_DIB";
          break;
        case 9:
          arrayOfString[j] = "CF_PALETTE";
          break;
        case 10:
          arrayOfString[j] = "CF_PENDATA";
          break;
        case 11:
          arrayOfString[j] = "CF_RIFF";
          break;
        case 12:
          arrayOfString[j] = "CF_WAVE";
          break;
        case 13:
          arrayOfString[j] = "CF_UNICODETEXT";
          break;
        case 14:
          arrayOfString[j] = "CF_ENHMETAFILE";
          break;
        case 16:
          arrayOfString[j] = "CF_LOCALE";
          break;
        case 17:
          arrayOfString[j] = "CF_MAX";
          break;
        default:
          arrayOfString[j] = "UNKNOWN";
        }
    }
    return arrayOfString;
  }

  private FORMATETC[] _getAvailableTypes()
  {
    Object localObject = new FORMATETC[0];
    int[] arrayOfInt1 = new int[1];
    if (COM.OleGetClipboard(arrayOfInt1) != 0)
      return localObject;
    IDataObject localIDataObject = new IDataObject(arrayOfInt1[0]);
    int[] arrayOfInt2 = new int[1];
    int i = localIDataObject.EnumFormatEtc(1, arrayOfInt2);
    localIDataObject.Release();
    if (i != 0)
      return localObject;
    IEnumFORMATETC localIEnumFORMATETC = new IEnumFORMATETC(arrayOfInt2[0]);
    int j = OS.GlobalAlloc(64, FORMATETC.sizeof);
    int[] arrayOfInt3 = new int[1];
    localIEnumFORMATETC.Reset();
    while ((localIEnumFORMATETC.Next(1, j, arrayOfInt3) == 0) && (arrayOfInt3[0] == 1))
    {
      FORMATETC localFORMATETC = new FORMATETC();
      COM.MoveMemory(localFORMATETC, j, FORMATETC.sizeof);
      FORMATETC[] arrayOfFORMATETC = new FORMATETC[localObject.length + 1];
      System.arraycopy(localObject, 0, arrayOfFORMATETC, 0, localObject.length);
      arrayOfFORMATETC[localObject.length] = localFORMATETC;
      localObject = arrayOfFORMATETC;
    }
    OS.GlobalFree(j);
    localIEnumFORMATETC.Release();
    return localObject;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.dnd.Clipboard
 * JD-Core Version:    0.6.2
 */