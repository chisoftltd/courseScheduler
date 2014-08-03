package org.eclipse.swt.dnd;

import org.eclipse.swt.internal.ole.win32.FORMATETC;
import org.eclipse.swt.internal.ole.win32.STGMEDIUM;

public class TransferData
{
  public int type;
  public FORMATETC formatetc;
  public STGMEDIUM stgmedium;
  public int result = -2147467259;
  public int pIDataObject;

  static boolean sameType(TransferData paramTransferData1, TransferData paramTransferData2)
  {
    if (paramTransferData1 == paramTransferData2)
      return true;
    if ((paramTransferData1 == null) || (paramTransferData2 == null))
      return false;
    return (paramTransferData1.type == paramTransferData2.type) && (paramTransferData1.formatetc.cfFormat == paramTransferData2.formatetc.cfFormat) && (paramTransferData1.formatetc.dwAspect == paramTransferData2.formatetc.dwAspect) && (paramTransferData1.formatetc.tymed == paramTransferData2.formatetc.tymed);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.dnd.TransferData
 * JD-Core Version:    0.6.2
 */