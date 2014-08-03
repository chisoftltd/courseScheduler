package org.eclipse.swt.dnd;

import org.eclipse.swt.internal.ole.win32.FORMATETC;
import org.eclipse.swt.internal.ole.win32.IDataObject;
import org.eclipse.swt.internal.ole.win32.STGMEDIUM;
import org.eclipse.swt.internal.win32.OS;

public abstract class ByteArrayTransfer extends Transfer
{
  public TransferData[] getSupportedTypes()
  {
    int[] arrayOfInt = getTypeIds();
    TransferData[] arrayOfTransferData = new TransferData[arrayOfInt.length];
    for (int i = 0; i < arrayOfInt.length; i++)
    {
      arrayOfTransferData[i] = new TransferData();
      arrayOfTransferData[i].type = arrayOfInt[i];
      arrayOfTransferData[i].formatetc = new FORMATETC();
      arrayOfTransferData[i].formatetc.cfFormat = arrayOfInt[i];
      arrayOfTransferData[i].formatetc.dwAspect = 1;
      arrayOfTransferData[i].formatetc.lindex = -1;
      arrayOfTransferData[i].formatetc.tymed = 1;
    }
    return arrayOfTransferData;
  }

  public boolean isSupportedType(TransferData paramTransferData)
  {
    if (paramTransferData == null)
      return false;
    int[] arrayOfInt = getTypeIds();
    for (int i = 0; i < arrayOfInt.length; i++)
    {
      FORMATETC localFORMATETC = paramTransferData.formatetc;
      if ((localFORMATETC.cfFormat == arrayOfInt[i]) && ((localFORMATETC.dwAspect & 0x1) == 1) && ((localFORMATETC.tymed & 0x1) == 1))
        return true;
    }
    return false;
  }

  protected void javaToNative(Object paramObject, TransferData paramTransferData)
  {
    if ((!checkByteArray(paramObject)) || (!isSupportedType(paramTransferData)))
      DND.error(2003);
    byte[] arrayOfByte = (byte[])paramObject;
    int i = arrayOfByte.length;
    int j = OS.GlobalAlloc(64, i);
    OS.MoveMemory(j, arrayOfByte, i);
    paramTransferData.stgmedium = new STGMEDIUM();
    paramTransferData.stgmedium.tymed = 1;
    paramTransferData.stgmedium.unionField = j;
    paramTransferData.stgmedium.pUnkForRelease = 0;
    paramTransferData.result = 0;
  }

  protected Object nativeToJava(TransferData paramTransferData)
  {
    if ((!isSupportedType(paramTransferData)) || (paramTransferData.pIDataObject == 0))
      return null;
    IDataObject localIDataObject = new IDataObject(paramTransferData.pIDataObject);
    localIDataObject.AddRef();
    FORMATETC localFORMATETC = paramTransferData.formatetc;
    STGMEDIUM localSTGMEDIUM = new STGMEDIUM();
    localSTGMEDIUM.tymed = 1;
    paramTransferData.result = getData(localIDataObject, localFORMATETC, localSTGMEDIUM);
    localIDataObject.Release();
    if (paramTransferData.result != 0)
      return null;
    int i = localSTGMEDIUM.unionField;
    int j = OS.GlobalSize(i);
    byte[] arrayOfByte = new byte[j];
    int k = OS.GlobalLock(i);
    OS.MoveMemory(arrayOfByte, k, j);
    OS.GlobalUnlock(i);
    OS.GlobalFree(i);
    return arrayOfByte;
  }

  boolean checkByteArray(Object paramObject)
  {
    return (paramObject != null) && ((paramObject instanceof byte[])) && (((byte[])paramObject).length > 0);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.dnd.ByteArrayTransfer
 * JD-Core Version:    0.6.2
 */