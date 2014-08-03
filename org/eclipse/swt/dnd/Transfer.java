package org.eclipse.swt.dnd;

import org.eclipse.swt.internal.ole.win32.FORMATETC;
import org.eclipse.swt.internal.ole.win32.IDataObject;
import org.eclipse.swt.internal.ole.win32.STGMEDIUM;
import org.eclipse.swt.internal.win32.MSG;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.TCHAR;

public abstract class Transfer
{
  private static final int RETRY_LIMIT = 10;

  int getData(IDataObject paramIDataObject, FORMATETC paramFORMATETC, STGMEDIUM paramSTGMEDIUM)
  {
    if (paramIDataObject.GetData(paramFORMATETC, paramSTGMEDIUM) == 0)
      return 0;
    try
    {
      Thread.sleep(50L);
    }
    catch (Throwable localThrowable1)
    {
    }
    int i = paramIDataObject.GetData(paramFORMATETC, paramSTGMEDIUM);
    int j = 0;
    while ((i != 0) && (j++ < 10))
    {
      MSG localMSG = new MSG();
      OS.PeekMessage(localMSG, 0, 0, 0, 2);
      try
      {
        Thread.sleep(50L);
      }
      catch (Throwable localThrowable2)
      {
      }
      i = paramIDataObject.GetData(paramFORMATETC, paramSTGMEDIUM);
    }
    return i;
  }

  public abstract TransferData[] getSupportedTypes();

  public abstract boolean isSupportedType(TransferData paramTransferData);

  protected abstract int[] getTypeIds();

  protected abstract String[] getTypeNames();

  protected abstract void javaToNative(Object paramObject, TransferData paramTransferData);

  protected abstract Object nativeToJava(TransferData paramTransferData);

  public static int registerType(String paramString)
  {
    TCHAR localTCHAR = new TCHAR(0, paramString, true);
    return OS.RegisterClipboardFormat(localTCHAR);
  }

  protected boolean validate(Object paramObject)
  {
    return true;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.dnd.Transfer
 * JD-Core Version:    0.6.2
 */