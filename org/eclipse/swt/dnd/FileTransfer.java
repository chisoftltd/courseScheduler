package org.eclipse.swt.dnd;

import org.eclipse.swt.internal.ole.win32.FORMATETC;
import org.eclipse.swt.internal.ole.win32.IDataObject;
import org.eclipse.swt.internal.ole.win32.STGMEDIUM;
import org.eclipse.swt.internal.win32.DROPFILES;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.TCHAR;

public class FileTransfer extends ByteArrayTransfer
{
  private static FileTransfer _instance = new FileTransfer();
  private static final String CF_HDROP = "CF_HDROP ";
  private static final int CF_HDROPID = 15;
  private static final String CF_HDROP_SEPARATOR = "";

  public static FileTransfer getInstance()
  {
    return _instance;
  }

  public void javaToNative(Object paramObject, TransferData paramTransferData)
  {
    if ((!checkFile(paramObject)) || (!isSupportedType(paramTransferData)))
      DND.error(2003);
    String[] arrayOfString = (String[])paramObject;
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; i < arrayOfString.length; i++)
    {
      localStringBuffer.append(arrayOfString[i]);
      localStringBuffer.append("");
    }
    TCHAR localTCHAR = new TCHAR(0, localStringBuffer.toString(), true);
    DROPFILES localDROPFILES = new DROPFILES();
    localDROPFILES.pFiles = DROPFILES.sizeof;
    localDROPFILES.pt_x = (localDROPFILES.pt_y = 0);
    localDROPFILES.fNC = 0;
    localDROPFILES.fWide = (OS.IsUnicode ? 1 : 0);
    int j = localTCHAR.length() * TCHAR.sizeof;
    int k = OS.GlobalAlloc(64, DROPFILES.sizeof + j);
    OS.MoveMemory(k, localDROPFILES, DROPFILES.sizeof);
    OS.MoveMemory(k + DROPFILES.sizeof, localTCHAR, j);
    paramTransferData.stgmedium = new STGMEDIUM();
    paramTransferData.stgmedium.tymed = 1;
    paramTransferData.stgmedium.unionField = k;
    paramTransferData.stgmedium.pUnkForRelease = 0;
    paramTransferData.result = 0;
  }

  public Object nativeToJava(TransferData paramTransferData)
  {
    if ((!isSupportedType(paramTransferData)) || (paramTransferData.pIDataObject == 0))
      return null;
    IDataObject localIDataObject = new IDataObject(paramTransferData.pIDataObject);
    localIDataObject.AddRef();
    FORMATETC localFORMATETC = new FORMATETC();
    localFORMATETC.cfFormat = 15;
    localFORMATETC.ptd = 0;
    localFORMATETC.dwAspect = 1;
    localFORMATETC.lindex = -1;
    localFORMATETC.tymed = 1;
    STGMEDIUM localSTGMEDIUM = new STGMEDIUM();
    localSTGMEDIUM.tymed = 1;
    paramTransferData.result = getData(localIDataObject, localFORMATETC, localSTGMEDIUM);
    localIDataObject.Release();
    if (paramTransferData.result != 0)
      return null;
    int i = OS.DragQueryFile(localSTGMEDIUM.unionField, -1, null, 0);
    String[] arrayOfString = new String[i];
    for (int j = 0; j < i; j++)
    {
      int k = OS.DragQueryFile(localSTGMEDIUM.unionField, j, null, 0) + 1;
      TCHAR localTCHAR = new TCHAR(0, k);
      OS.DragQueryFile(localSTGMEDIUM.unionField, j, localTCHAR, k);
      arrayOfString[j] = localTCHAR.toString(0, localTCHAR.strlen());
    }
    OS.DragFinish(localSTGMEDIUM.unionField);
    return arrayOfString;
  }

  protected int[] getTypeIds()
  {
    return new int[] { 15 };
  }

  protected String[] getTypeNames()
  {
    return new String[] { "CF_HDROP " };
  }

  boolean checkFile(Object paramObject)
  {
    if ((paramObject == null) || (!(paramObject instanceof String[])) || (((String[])paramObject).length == 0))
      return false;
    String[] arrayOfString = (String[])paramObject;
    for (int i = 0; i < arrayOfString.length; i++)
      if ((arrayOfString[i] == null) || (arrayOfString[i].length() == 0))
        return false;
    return true;
  }

  protected boolean validate(Object paramObject)
  {
    return checkFile(paramObject);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.dnd.FileTransfer
 * JD-Core Version:    0.6.2
 */