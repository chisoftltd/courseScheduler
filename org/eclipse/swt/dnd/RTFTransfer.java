package org.eclipse.swt.dnd;

import org.eclipse.swt.internal.ole.win32.FORMATETC;
import org.eclipse.swt.internal.ole.win32.IDataObject;
import org.eclipse.swt.internal.ole.win32.STGMEDIUM;
import org.eclipse.swt.internal.win32.OS;

public class RTFTransfer extends ByteArrayTransfer
{
  private static RTFTransfer _instance = new RTFTransfer();
  private static final String CF_RTF = "Rich Text Format";
  private static final int CF_RTFID = registerType("Rich Text Format");

  public static RTFTransfer getInstance()
  {
    return _instance;
  }

  public void javaToNative(Object paramObject, TransferData paramTransferData)
  {
    if ((!checkRTF(paramObject)) || (!isSupportedType(paramTransferData)))
      DND.error(2003);
    String str = (String)paramObject;
    int i = str.length();
    char[] arrayOfChar = new char[i + 1];
    str.getChars(0, i, arrayOfChar, 0);
    int j = OS.GetACP();
    int k = OS.WideCharToMultiByte(j, 0, arrayOfChar, -1, null, 0, null, null);
    if (k == 0)
    {
      paramTransferData.stgmedium = new STGMEDIUM();
      paramTransferData.result = -2147221402;
      return;
    }
    int m = OS.GlobalAlloc(64, k);
    OS.WideCharToMultiByte(j, 0, arrayOfChar, -1, m, k, null, null);
    paramTransferData.stgmedium = new STGMEDIUM();
    paramTransferData.stgmedium.tymed = 1;
    paramTransferData.stgmedium.unionField = m;
    paramTransferData.stgmedium.pUnkForRelease = 0;
    paramTransferData.result = 0;
  }

  public Object nativeToJava(TransferData paramTransferData)
  {
    if ((!isSupportedType(paramTransferData)) || (paramTransferData.pIDataObject == 0))
      return null;
    IDataObject localIDataObject = new IDataObject(paramTransferData.pIDataObject);
    localIDataObject.AddRef();
    STGMEDIUM localSTGMEDIUM = new STGMEDIUM();
    FORMATETC localFORMATETC = paramTransferData.formatetc;
    localSTGMEDIUM.tymed = 1;
    paramTransferData.result = getData(localIDataObject, localFORMATETC, localSTGMEDIUM);
    localIDataObject.Release();
    if (paramTransferData.result != 0)
      return null;
    int i = localSTGMEDIUM.unionField;
    try
    {
      int j = OS.GlobalLock(i);
      if (j == 0);
      while (true)
      {
        return null;
        try
        {
          int k = OS.GetACP();
          int m = OS.MultiByteToWideChar(k, 1, j, -1, null, 0);
          if (m == 0)
            continue;
          char[] arrayOfChar = new char[m - 1];
          OS.MultiByteToWideChar(k, 1, j, -1, arrayOfChar, arrayOfChar.length);
          String str = new String(arrayOfChar);
          return str;
        }
        finally
        {
          OS.GlobalUnlock(i);
        }
      }
    }
    finally
    {
      OS.GlobalFree(i);
    }
  }

  protected int[] getTypeIds()
  {
    return new int[] { CF_RTFID };
  }

  protected String[] getTypeNames()
  {
    return new String[] { "Rich Text Format" };
  }

  boolean checkRTF(Object paramObject)
  {
    return (paramObject != null) && ((paramObject instanceof String)) && (((String)paramObject).length() > 0);
  }

  protected boolean validate(Object paramObject)
  {
    return checkRTF(paramObject);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.dnd.RTFTransfer
 * JD-Core Version:    0.6.2
 */